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
import com.appnativa.rare.iConstants;
import com.appnativa.rare.platform.swing.ui.view.JPanelEx;
import com.appnativa.rare.platform.swing.ui.view.ScrollBarView;
import com.appnativa.rare.ui.CarouselPanel.ContentView;
import com.appnativa.rare.ui.carousel.MotionHandler;
import com.appnativa.rare.ui.carousel.MotionHandler.iScrollingParent;
import com.appnativa.rare.ui.carousel.aCarouselPanel;
import com.appnativa.rare.ui.carousel.aCarouselPanel.DataType;
import com.appnativa.rare.ui.event.EventListenerList;

import com.google.j2objc.annotations.Weak;

import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

import javax.swing.JComponent;

public class CarouselContainer extends BorderPanel {
  static int          xThreashold = 5;
  static int          yThreashold = 50;
  CarouselPanel       panel;
  AdjustableScrollBar scrollbar;
  ScrollPanel         scrollPanel;
  int                 scrollbarHeight;

  public CarouselContainer(DataType dataType) {
    super();
    panel       = new CarouselPanel(dataType);
    scrollPanel = new ScrollPanel(panel);
    setCenterView(scrollPanel);
    panel.setAdjustable(new Adjustable(this));
  }

  public void refreshItems() {
    panel.refreshItems();
    revalidate();
  }

  public void setScrollBarOpacity(float opacity) {}

  public void setShowScrollBar(boolean show) {
    if (!Platform.isTouchDevice()) {
      if (show) {
        if (scrollbar == null) {
          scrollbar = new AdjustableScrollBar(panel);
          panel.setAdjustable(scrollbar);
          scrollbarHeight = scrollbar.getScrollBarView().getPreferredSize().height + 4;

          Component c = new Component(scrollbar.getScrollBarView());

          c.putClientProperty(iConstants.RARE_HALIGN_PROPERTY, RenderableDataItem.HorizontalAlign.FILL);
          c.putClientProperty(iConstants.RARE_VALIGN_PROPERTY, RenderableDataItem.VerticalAlign.FILL);
          setBottomView(c);
          revalidate();
          repaint();
        }
      } else {
        if (scrollbar != null) {
          scrollbarHeight = 0;
          panel.setAdjustable(new Adjustable(this));
          setBottomView(null);
          scrollbar = null;
          revalidate();
          repaint();
        }
      }
    }
  }

  public CarouselPanel getCarouselPanel() {
    return panel;
  }

  @Override
  protected void getMinimumSizeEx(UIDimension size, float maxWidth) {
    super.getMinimumSizeEx(size,maxWidth);
    size.height += scrollbarHeight;
  }

  @Override
  protected void getPreferredSizeEx(UIDimension size, float maxWidth) {
    super.getPreferredSizeEx(size, maxWidth);
    size.height += scrollbarHeight;
  }

  static class ScrollView extends JPanelEx {
    @Override
    protected void layoutContainerEx(int width, int height) {
      ScrollPanel panel = (ScrollPanel) Component.fromView(this);

      panel.motionHandler.layoutChild(width, height);
    }
  }


  static class ScrollPanel extends Container implements iScrollingParent {
    private MotionHandler motionHandler;

    public ScrollPanel(CarouselPanel panel) {
      super(new ScrollView());
      add(panel);
      motionHandler = new MotionHandler(panel, this);
      this.addMouseListener(motionHandler);
      this.addMouseMotionListener(motionHandler);
    }

    public boolean isScrolling() {
      return motionHandler.isScrolling();
    }

    @Override
    protected void getMinimumSizeEx(UIDimension size,float maxWidth) {
      size.setSize(0, 0);

      if (getComponentCount() == 1) {
        iPlatformComponent c = getComponentAt(0);

        if (c.isVisible()) {
          c.getMinimumSize(size,maxWidth);
        }
      }
    }

    @Override
    protected void getPreferredSizeEx(UIDimension size, float maxWidth) {
      size.setSize(0, 0);

      if (getComponentCount() == 1) {
        iPlatformComponent c = getComponentAt(0);

        if (c.isVisible()) {
          c.getPreferredSize(size,maxWidth);
        }
      }
    }

    @Override
    public int getIndexOfComponentAt(float x, float y) {
      JComponent v = (JComponent) view.findComponentAt(UIScreen.snapToPosition(x), UIScreen.snapToPosition(y));

      while(v != null) {
        if (v instanceof ContentView) {
          iPlatformComponent c     = Component.fromView(v);
          Integer            index = (Integer) c.getClientProperty(aCarouselPanel.RARE_CAROUSEL_ITEM);

          return (index == null)
                 ? -1
                 : index.intValue();
        }

        if (v.getParent() instanceof JComponent) {
          v = (JComponent) v.getParent();
        } else {
          break;
        }
      }

      return -1;
    }
  }


  static class Adjustable extends aAdjustable {
    @Weak
    CarouselContainer container;

    public Adjustable(CarouselContainer container) {
      super();
      this.container = container;
    }

    @Override
    protected EventListenerList getEventListenerList() {
      return container.getEventListenerList();
    }

    @Override
    protected boolean hasListener() {
      return container.listenerList != null;
    }

    @Override
    public boolean isAdjusting() {
      return container.scrollPanel.isScrolling();
    }
  }


  static class AdjustableScrollBar extends aAdjustable implements AdjustmentListener {
    private static final double MULTIPLIER = 1;
    ScrollBarView               sb         = new ScrollBarView(true);
    boolean                     wasAdjusting;
    CarouselPanel               panel;

    public AdjustableScrollBar(CarouselPanel panel) {
      sb.addAdjustmentListener(this);
      sb.setFocusable(false);
      this.panel = panel;
    }

    @Override
    public void adjustmentValueChanged(AdjustmentEvent e) {
      super.setValue((e.getValue() / MULTIPLIER));

      if (e.getValueIsAdjusting()) {
        wasAdjusting = true;
      } else {
        if (wasAdjusting) {    // notify that we have stopped scrolling
          Platform.invokeLater(new Runnable() {
            @Override
            public void run() {
              panel.fireChangeEvent();
            }
          });
        }

        wasAdjusting = false;
      }
    }

    @Override
    public void setBlockIncrement(double inc) {
      super.setBlockIncrement(inc);
      sb.setBlockIncrement((int) (inc * MULTIPLIER));
    }

    @Override
    public void setMaximum(double max) {
      super.setMaximum(max);
      sb.setMaximum((int) (max * MULTIPLIER));
    }

    @Override
    public void setMinimum(double min) {
      super.setMinimum(min);
      sb.setMinimum((int) (min * MULTIPLIER));
    }

    @Override
    public void setUnitIncrement(double inc) {
      super.setUnitIncrement(inc);
      sb.setUnitIncrement((int) (inc * MULTIPLIER));
    }

    @Override
    public void setValue(double v) {
      super.setValue(v);
      sb.setValue((int) (v * MULTIPLIER));
    }

    @Override
    public void setVisibleAmount(double v) {
      super.setVisibleAmount(v);
      sb.setVisibleAmount((int) (v * MULTIPLIER));
    }

    public JComponent getScrollBarView() {
      return sb;
    }

    @Override
    public boolean isAdjusting() {
      return sb.getModel().getValueIsAdjusting();
    }
  }
  // class ScrollView extends JPanelEx implements MouseMotionListener,
  // MouseListener {
  // int mouseDownX = 0;
  // int overFlow = 50;
  // int scrollX = overFlow / -2;
  // int scrollXBase = scrollX;
  // JComponent childView;
  // boolean dragged;
  // float threshold;
  //
  // public ScrollView() {
  // super();
  // addMouseListener(this);
  // addMouseMotionListener(this);
  // threshold = Platform.isTouchDevice()
  // ? 5
  // : 20;
  // }
  //
  // public void mouseClicked(MouseEvent e) {}
  //
  // @Override
  // public void mouseDragged(MouseEvent e) {
  // int distanceX = mouseDownX - e.getX();
  //
  // if (Math.abs(distanceX) < threshold) {
  // return;
  // }
  //
  // dragged = true;
  //
  // int nx = scrollX - distanceX;
  //
  // if (nx < (scrollXBase * 2)) {
  // if (panel.getSelectedIndex() + 1 < panel.getItemCount()) {
  // scrollX = scrollXBase;
  // childView.setBounds(scrollX, 0, getWidth() + overFlow, getHeight());
  // panel.scrollLeft();
  // repaint();
  // }
  // } else if (scrollX > (0 - scrollXBase)) {
  // if (panel.getSelectedIndex() > 0) {
  // scrollX = scrollXBase;
  // childView.setBounds(scrollX, 0, getWidth() + overFlow, getHeight());
  // panel.scrollRight();
  // repaint();
  // }
  // } else {
  // scrollX = nx;
  // childView.setBounds(scrollX, 0, getWidth() + overFlow, getHeight());
  // repaint();
  // }
  // }
  //
  // @Override
  // public void mouseEntered(MouseEvent e) {}
  //
  // @Override
  // public void mouseExited(MouseEvent e) {}
  //
  // public void mouseMoved(MouseEvent e) {}
  //
  // @Override
  // public void mousePressed(MouseEvent e) {
  // mouseDownX = e.getX();
  // dragged = false;
  // }
  //
  // @Override
  // public void mouseReleased(MouseEvent e) {
  // if ((scrollX != scrollXBase) && (childView != null)) {
  // scrollX = scrollXBase;
  // childView.setBounds(scrollX, 0, getWidth() + overFlow, getHeight());
  // repaint();
  // } else if (!dragged) {
  // Point p = e.getPoint();
  // JComponent v = (JComponent) getComponentAt(p);
  // int index = (v == null)
  // ? -1
  // : panel.getSelectionFromView(v);
  //
  // if ((index != -1) && (index != panel.getSelectedIndex())) {
  // panel.animateSelect(index);
  // }
  // }
  // }
  //
  // protected void addImpl(java.awt.Component comp, Object constraints, int
  // index) {
  // super.addImpl(comp, constraints, index);
  // childView = (JComponent) comp;
  // }
  //
  // protected void layoutContainerEx(int width, int height) {
  // if (childView != null) {
  // childView.setBounds(scrollX, 0, width + overFlow, height);
  // }
  // }
  // }
}
