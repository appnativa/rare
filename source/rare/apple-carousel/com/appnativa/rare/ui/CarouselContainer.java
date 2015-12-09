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
import com.appnativa.rare.platform.apple.ui.iAppleLayoutManager;
import com.appnativa.rare.platform.apple.ui.view.ParentView;
import com.appnativa.rare.platform.apple.ui.view.ScrollBarView;
import com.appnativa.rare.platform.apple.ui.view.View;
import com.appnativa.rare.ui.CarouselPanel.ContentView;
import com.appnativa.rare.ui.carousel.MotionHandler;
import com.appnativa.rare.ui.carousel.MotionHandler.iScrollingParent;
import com.appnativa.rare.ui.carousel.aCarouselPanel;
import com.appnativa.rare.ui.carousel.aCarouselPanel.DataType;
import com.appnativa.rare.ui.event.EventListenerList;

import com.google.j2objc.annotations.Weak;

public class CarouselContainer extends BorderPanel {
  CarouselPanel panel;
  ScrollBarView scrollbar;
  ScrollView    scrollView;

  public CarouselContainer(DataType dataType) {
    super();
    panel = new CarouselPanel(dataType);
    panel.setAdjustable(new Adjustable(this));
    scrollView = new ScrollView(panel);

    Container c = new ScrollPanel(scrollView);

    c.add(panel);
    setCenterView(c);
  }

  @Override
  public void dispose() {
    super.dispose();
    panel      = null;
    scrollbar  = null;
    scrollView = null;
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
          scrollbar = createScrollbar();
          scrollbar.setTheme(true);
          panel.setAdjustable((iAdjustable) scrollbar);

          Component c = new Component(scrollbar);

          c.putClientProperty(iConstants.RARE_HALIGN_PROPERTY, RenderableDataItem.HorizontalAlign.FILL);
          c.putClientProperty(iConstants.RARE_VALIGN_PROPERTY, RenderableDataItem.VerticalAlign.FILL);
          setBottomView(c);
          revalidate();
          repaint();
        }
      } else {
        if (scrollbar != null) {
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

  protected ScrollBarView createScrollbar() {
    ScrollBarView sb = new ScrollBarView(true);

    return sb;
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
      return container.scrollView.isScrolling();
    }
  }


  static class ScrollPanel extends Container implements iScrollingParent {
    public ScrollPanel(ScrollView view) {
      super(view);
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
          c.getPreferredSize(size);
        }
      }
    }

    @Override
    public int getIndexOfComponentAt(float x, float y) {
      View v = ((ScrollView) view).getViewAt(x, y, true);

      while(v != null) {
        if (v instanceof ContentView) {
          iPlatformComponent c     = v.getComponent();
          Integer            index = (Integer) c.getClientProperty(aCarouselPanel.RARE_CAROUSEL_ITEM);

          return (index == null)
                 ? -1
                 : index.intValue();
        }

        v = v.getParent();
      }

      return -1;
    }
  }


  static class ScrollView extends ParentView implements iAppleLayoutManager {
    MotionHandler motionHandler;

    public ScrollView(CarouselPanel panel) {
      super(createAPView());
      motionHandler = new MotionHandler(panel);
      setLayoutManager(this);
    }

    @Override
    public void setComponent(Component component) {
      super.setComponent(component);
      motionHandler.setScrollingComponent((iScrollingParent) component);
      setMouseListener(motionHandler);
      setMouseMotionListener(motionHandler);
      setMouseListenerEnabled(true);
      setMouseMotionListenerEnabled(true);
    }

    @Override
    public void layout(ParentView view, float width, float height) {
      motionHandler.layoutChild(width, height);
    }

    public boolean isScrolling() {
      return motionHandler.isScrolling();
    }

    @Override
    protected void disposeEx() {
      super.disposeEx();

      if (motionHandler != null) {
        motionHandler.dispose();
      }

      motionHandler = null;
    }
  }
}
