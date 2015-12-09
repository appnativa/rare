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
import com.appnativa.rare.platform.EventHelper;
import com.appnativa.rare.platform.swing.ui.util.SwingGraphics;
import com.appnativa.rare.platform.swing.ui.util.SwingHelper;
import com.appnativa.rare.platform.swing.ui.view.JPanelEx;
import com.appnativa.rare.platform.swing.ui.view.UtilityPanel;
import com.appnativa.rare.ui.event.iChangeListener;
import com.appnativa.rare.ui.painter.iPainter;
import com.appnativa.rare.viewer.WindowViewer;
import com.appnativa.rare.widget.iWidget;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class SplitPanePanel extends aSplitPanePanel {
  Boolean               oldWider = null;
  Rectangle             dragLocation;
  private SplitPaneView splitPaneView;

  public SplitPanePanel() {
    super();
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
  public void checkOrientation(Object newConfig) {
    if (newConfig == null) {
      WindowViewer w = Platform.getWindowViewer(this);

      if (w != null) {
        newConfig = w.getWidth() > w.getHeight();
      }
    }

    super.checkOrientation(newConfig);
  }

  @Override
  public void componentShown(ComponentEvent e) {}

  @Override
  public Divider createDivider() {
    return new Divider(new DividerPane());
  }

  @Override
  public void removeChangeListener(iChangeListener l) {
    if (listenerList != null) {
      listenerList.remove(iChangeListener.class, l);
    }
  }

  @Override
  protected void disposeEx() {
    super.disposeEx();
    splitPaneView = null;
  }

  class SplitPaneView extends UtilityPanel implements LayoutManager {
    UIDimension size = new UIDimension();

    public SplitPaneView() {
      super();
      setLayout(this);
    }

    @Override
    public void addLayoutComponent(String name, java.awt.Component comp) {}

    @Override
    public void layoutContainer(java.awt.Container parent) {
      UIInsets in = SplitPanePanel.this.getInsets(computeInsets);

      size.setSize(getWidth(), getHeight());
      SplitPanePanel.this.layoutContainer(size, in);
    }

    @Override
    public Dimension minimumLayoutSize(java.awt.Container parent) {
      UIDimension size = new UIDimension();

      getMinimumSize(size, 0);

      return SwingHelper.setDimension(null, size);
    }

    @Override
    public Dimension preferredLayoutSize(java.awt.Container parent) {
      UIDimension size = new UIDimension();

      getPreferredSize(size, 0);

      return SwingHelper.setDimension(null, size);
    }

    @Override
    public void removeLayoutComponent(java.awt.Component comp) {}

    protected void dividerDrag(MouseEvent me) {
      if (!draggingInited) {
        if (!continuousLayout) {
          Rectangle r = dragDivider.getView().getBounds();

          if (dragLocation == null) {
            dragLocation = new Rectangle();
          }

          dragLocation.setBounds(r.x, r.y, r.width, r.height);
        }

        draggingInited = true;
      }

      int d = leftToRight
              ? ((int) EventHelper.getMouseXInWindow(dragDivider, me) - dragMarkX)
              : ((int) EventHelper.getMouseYInWindow(dragDivider, me) - dragMarkY);

      if ((Math.abs(d) > (dividerSize / 2f))) {
        if (continuousLayout) {
          resizeViaDivider(dragDivider, d);
        } else {
          Rectangle b = dragDivider.getView().getBounds();

          if (leftToRight) {
            dragLocation.setLocation(b.x + d, b.y);
          } else {
            dragLocation.setLocation(b.x, b.y + d);
          }

          repaint();
        }
      }
    }

    protected void dividerDragFinished(MouseEvent me) {
      dragging       = false;
      draggingInited = false;

      if (dragDivider == null) {
        return;
      }

      dragDivider.draggingStopped();

      int d = leftToRight
              ? ((int) EventHelper.getMouseXInWindow(dragDivider, me) - dragMarkX)
              : ((int) EventHelper.getMouseYInWindow(dragDivider, me) - dragMarkY);

      if (!continuousLayout && (Math.abs(d) > (dividerSize / 2f))) {
        resizeViaDivider(dragDivider, d);
      }

      dragDivider  = null;
      dragLocation = null;
      revalidate();
      repaint();
    }

    protected void dividerDragStarted(Divider v, MouseEvent me) {
      dragging    = true;
      dragDivider = v;
      dragMarkX   = (int) EventHelper.getMouseXInWindow(dragDivider, me);
      dragMarkY   = (int) EventHelper.getMouseYInWindow(dragDivider, me);
      setupDrag(v);
      dividerDrag(me);
    }

    @Override
    protected void paintBorder(Graphics g) {
      super.paintBorder(g);

      if (!continuousLayout && dragging && (dragLocation != null)) {
        int orientation = leftToRight
                          ? iPainter.VERTICAL
                          : iPainter.HORIZONTAL;

        dividerDragPainter.paint(this, (Graphics2D) g, dragLocation.x, dragLocation.y, dragLocation.width,
                                 dragLocation.height, false, orientation);
      }
    }

    protected boolean resizeViaDivider(Divider d, int delta) {
      boolean ret = SplitPanePanel.this.resizeViaDivider(d, delta);

      if (ret) {
        revalidate();
        fireChangeEvent();
      }

      return ret;
    }
  }


  protected class DividerPane extends JPanelEx implements MouseMotionListener, MouseListener {
    public DividerPane() {
      super();
      setBackground(ColorUtils.getBackground());
      addMouseListener(this);
      addMouseMotionListener(this);
      setComponentPainter(dividerPainter);

      if (gripperIcon == null) {
        gripperIcon = new SplitPaneGripperIcon() {
          @Override
          public void paintIcon(Component c, Graphics g, int x, int y) {
            graphics = SwingGraphics.fromGraphics(g, c, graphics);
            super.paint(graphics, x, y, getWidth(), getHeight());
          }
        };
      }
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

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
        splitPaneView.dividerDragStarted((Divider) com.appnativa.rare.ui.Component.fromView(this), e);
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

    @Override
    protected void paintComponent(Graphics g) {
      super.paintComponent(g);

      float width  = getWidth();
      float height = getHeight();

      gripperIcon.paint(graphics, 0, 0, width, height);
    }
  }
}
