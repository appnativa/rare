/*
 * Copyright appNativa Inc. All Rights Reserved.
 *
 * This file is part of the Real-time Application Rendering Engine (RARE).
 *
 * RARE is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */

package com.appnativa.rare.ui;

import com.appnativa.rare.Platform;
import com.appnativa.rare.platform.apple.ui.iAppleLayoutManager;
import com.appnativa.rare.platform.apple.ui.util.AppleGraphics;
import com.appnativa.rare.platform.apple.ui.view.ParentView;
import com.appnativa.rare.platform.apple.ui.view.View;
import com.appnativa.rare.ui.event.ChangeEvent;
import com.appnativa.rare.ui.event.MouseEvent;
import com.appnativa.rare.ui.event.iChangeListener;
import com.appnativa.rare.ui.listener.iMouseListener;
import com.appnativa.rare.ui.listener.iMouseMotionListener;
import com.appnativa.rare.widget.iWidget;

public class SplitPanePanel extends aSplitPanePanel {
  private SplitPaneView splitPaneView;

  public SplitPanePanel() {
    super();
    setNeedsHiearachyInvalidated(false);
    setView(new SplitPaneView());
    splitPaneView = (SplitPaneView) view;

    if (Platform.isTouchDevice()) {
      dividerSize = UIScreen.platformPixels(16);
    }

    continuousLayout = !Platform.isIOS();
  }

  public SplitPanePanel(iWidget context) {
    this();
  }

  @Override
  public void addChangeListener(iChangeListener l) {
    getEventListenerList().add(iChangeListener.class, l);
  }

  @Override
  public Divider createDivider() {
    return new Divider(new DividerPane());
  }

  @Override
  public void dispose() {
    super.dispose();
    splitPaneView = null;
  }

  @Override
  public void removeChangeListener(iChangeListener l) {
    if (listenerList != null) {
      listenerList.remove(iChangeListener.class, l);
    }
  }

  class SplitPaneView extends ParentView implements iAppleLayoutManager {
    UIDimension        size = new UIDimension();
    OverlayDividerPane overlayDragPane;

    public SplitPaneView() {
      super(View.createAPView());
      setLayoutManager(this);
    }

    @Override
    public void layout(ParentView view, float width, float height) {
      UIInsets in = getInsets(computeInsets);

      size.setSize(width, height);
      layoutContainer(size, in);
    }

    @Override
    protected void disposeEx() {
      super.disposeEx();

      if (overlayDragPane != null) {
        overlayDragPane.dispose();
        overlayDragPane = null;
      }
    }

    protected void dividerDrag(MouseEvent me) {
      if (!draggingInited) {
        if (!continuousLayout) {
          if (overlayDragPane == null) {
            overlayDragPane = new OverlayDividerPane();
            add(-1, overlayDragPane);    // we are adding directly to view so the  it wont get laid out
          } else {
            overlayDragPane.setVisible(true);
            overlayDragPane.bringToFront();
          }

          UIRectangle r = dragDivider.getBounds();

          overlayDragPane.setBounds(r.x, r.y, r.width, r.height);
        }

        draggingInited = true;
      }

      int d = leftToRight
              ? ((int) me.getWindowX() - dragMarkX)
              : ((int) me.getWindowY() - dragMarkY);

      if ((Math.abs(d) > (dividerSize / 2f))) {
        if (continuousLayout) {
          resizeViaDivider(dragDivider, d);
        } else {
          UIRectangle b = dragDivider.getBounds();

          if (leftToRight) {
            overlayDragPane.setLocation(b.x + d, b.y);
          } else {
            overlayDragPane.setLocation(b.x, b.y + d);
          }

          overlayDragPane.repaint();
        }
      }
    }

    protected void dividerDragFinished(MouseEvent me) {
      dragging       = false;
      draggingInited = false;

      if (overlayDragPane != null) {
        overlayDragPane.setVisible(false);
      }

      if (dragDivider == null) {
        return;
      }

      dragDivider.draggingStopped();

      int d = leftToRight
              ? ((int) me.getWindowX() - dragMarkX)
              : ((int) me.getWindowY() - dragMarkY);

      if (!continuousLayout && (Math.abs(d) > (dividerSize / 2f))) {
        resizeViaDivider(dragDivider, d);
      }

      dragDivider = null;
      revalidate();
    }

    protected void dividerDragStarted(Divider v, MouseEvent me) {
      dragging    = true;
      dragDivider = v;
      dragMarkX   = (int) me.getWindowX();
      dragMarkY   = (int) me.getWindowY();
      setupDrag(v);
      dividerDrag(me);
    }

    protected boolean resizeViaDivider(Divider d, int delta) {
      boolean ret = SplitPanePanel.this.resizeViaDivider(d, delta);

      if (ret) {
        revalidate();

        if (listenerList != null) {
          if (changeEvent == null) {
            changeEvent = new ChangeEvent(this);
          }

          Utils.fireChangeEvent(listenerList, changeEvent);
        }
      }

      return ret;
    }

    protected iPlatformComponent getLayoutComponentAt(int index) {
      return getComponentAt(index);
    }

    protected int getLayoutComponentCount() {
      int n = getComponentCount();

      if (overlayDragPane != null) {
        n--;
      }

      return n;
    }
  }


  protected class DividerPane extends View implements iMouseMotionListener, iMouseListener {
    public DividerPane() {
      super(View.createAPView());
      setType(leftToRight);
      setMouseListener(this);
      setMouseMotionListener(this);
      setComponentPainter(dividerPainter);
      setPaintHandlerEnabled(true);

      if (gripperIcon == null) {
        gripperIcon = new SplitPaneGripperIcon();
      }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
      splitPaneView.dividerDrag(e);
      e.consume();
    }

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseMoved(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {
      if (userResizeable) {
        splitPaneView.dividerDragStarted((Divider) Component.fromView(this), e);
        e.consume();
      }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
      if (!dragging) {
        return;
      }

      dragging = false;
      splitPaneView.dividerDragFinished(e);
    }

    @Override
    public void paintBackground(AppleGraphics g, View v, UIRectangle rect) {
      super.paintBackground(g, v, rect);
      gripperIcon.paint(g, 0, 0, rect.width, rect.height);
    }

    @Override
    public void pressCanceled(MouseEvent e) {
      mouseReleased(e);
    }

    @Override
    public boolean wantsLongPress() {
      return false;
    }

    @Override
    public boolean wantsMouseMovedEvents() {
      return false;
    }

    public void setType(boolean leftToRight) {
      if (leftToRight) {
        setCursor(UICursor.getCursor("COL-RESIZE"));
      } else {
        setCursor(UICursor.getCursor("ROW-RESIZE"));
      }
    }

    public void getPreferredSize(UIDimension size) {
      size.width  = dividerSize;
      size.height = dividerSize;

      if (gripperIcon != null) {
        if (dividerBorder != null) {
          computeInsets = dividerBorder.getBorderInsets(computeInsets);
        } else {
          computeInsets.set(0, 0, 0, 0);
        }

        size.width  = Math.max(gripperIcon.getIconWidth() + computeInsets.left + computeInsets.right, dividerSize);
        size.height = Math.max(gripperIcon.getIconHeight() + computeInsets.top + computeInsets.bottom, dividerSize);
      }
    }
  }


  protected class OverlayDividerPane extends View {
    public OverlayDividerPane() {
      super(View.createAPView());
      setComponentPainter(dividerDragPainter);
      setPaintHandlerEnabled(true);

      if (gripperIcon == null) {
        gripperIcon = new SplitPaneGripperIcon();
      }
    }

    @Override
    public void paintBackground(AppleGraphics g, View v, UIRectangle rect) {
      super.paintBackground(g, v, rect);
      gripperIcon.paint(g, 0, 0, rect.width, rect.height);
    }
  }
}
