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

package com.appnativa.rare.ui.tabpane;

import com.appnativa.rare.platform.EventHelper;
import com.appnativa.rare.platform.apple.ui.iAppleLayoutManager;
import com.appnativa.rare.platform.apple.ui.util.AppleGraphics;
import com.appnativa.rare.platform.apple.ui.view.ParentView;
import com.appnativa.rare.platform.apple.ui.view.View;
import com.appnativa.rare.ui.Component;
import com.appnativa.rare.ui.Container;
import com.appnativa.rare.ui.Location;
import com.appnativa.rare.ui.UIAction;
import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.UIInsets;
import com.appnativa.rare.ui.UIRectangle;
import com.appnativa.rare.ui.UIScreen;
import com.appnativa.rare.ui.event.ActionEvent;
import com.appnativa.rare.ui.event.MouseEvent;
import com.appnativa.rare.ui.iParentComponent;
import com.appnativa.rare.ui.listener.iMouseListener;
/*-[
#import "RAREAPView.h"
]-*/

public class TabStripComponent extends aTabStripComponent {
  public TabStripComponent() {
    super(new TabStripView());
  }

  @Override
  public void setTabPainter(aPlatformTabPainter painter) {
    ((TabStripView) view).setTabPainter(painter);
  }

  @Override
  public aPlatformTabPainter getTabPainter() {
    return ((TabStripView) view).getTabPainter();
  }

  static public class TabStripView extends ParentView implements iAppleLayoutManager, iMouseListener {
    private aPlatformTabPainter tabPainter;

    public TabStripView() {
      super(createAPView());
      setLayoutManager(this);
      setUsePainterBackgroundColor(true);
      setUseMainLayerForPainter(false);
      setPaintHandlerEnabled(true);
    }

    @Override
    public void layout(ParentView view, float width, float height) {
      tabPainter.layout(0, 0, UIScreen.snapToSize(width), UIScreen.snapToSize(height));
      setUseFlipTransform((tabPainter.getPosition() == Location.BOTTOM) &&!tabPainter.isHandlesBottomRotation());
      repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
      Component c = (Component) component.getParent();

      if ((c != null) && c.hasMouseListeners()) {
        c.mouseEntered(EventHelper.retarget(e, c.getView()));
      }
    }

    @Override
    public void mouseExited(MouseEvent e) {
      Component c = (Component) component.getParent();

      if ((c != null) && c.hasMouseListeners()) {
        c.mouseExited(EventHelper.retarget(e, c.getView()));
      }
    }

    @Override
    public void mousePressed(MouseEvent event) {
      int      height = (int) getHeight();
      int      width  = (int) getWidth();
      UIInsets in     = ((Container) component).getInsets(null);
//      if (TabStripComponent.handleMousePressed(event, tabPainter, in, width, height)) {
//        revalidate();
//      }
      int x       = (int) event.getX();
      int y       = (int) event.getY();
      int padding = tabPainter.getTabsPadding();

      switch(tabPainter.getPosition()) {
        case BOTTOM :
          if (!tabPainter.isHandlesBottomRotation()) {
            y = height - y;
          }

          y += padding;

          break;

        case RIGHT :
          break;

        case LEFT :
          x -= padding;

          break;

        default :
          y -= padding;

          break;
      }

      width  -= (in.left + in.right);
      height -= (in.top + in.bottom);

      int n = tabPainter.findTab(x, y, in.left, in.top, width, height);

      if ((n != -1) && (n != tabPainter.getSelectedTab())) {
        UIAction a = tabPainter.getTab(n);

        if (a.isEnabled()) {
          a.actionPerformed(new ActionEvent(a));
          revalidate();
        }
      }

      Component c = (Component) component.getParent();

      if ((c != null) && c.hasMouseListeners()) {
        c.mousePressed(EventHelper.retarget(event, c.getView()));
      }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
      Component c = (Component) component.getParent();

      if ((c != null) && c.hasMouseListeners()) {
        c.mouseReleased(EventHelper.retarget(e, c.getView()));
      }
    }

    @Override
    public void paintBackground(AppleGraphics g, View v, UIRectangle rect) {
      super.paintBackground(g, v, rect);

      if (!tabPainter.isHorizontal() &&!tabPainter.isHandlesRightLeftRotation()) {
        tabPainter.paint(g, (int) rect.x, (int) rect.y, (int) rect.height, (int) rect.width);
      } else {
        tabPainter.paint(g, (int) rect.x, (int) rect.y, (int) rect.width, (int) rect.height);
      }
    }

    @Override
    public void pressCanceled(MouseEvent e) {
      Component c = (Component) component.getParent();

      if ((c != null) && c.hasMouseListeners()) {
        c.pressCanceled(EventHelper.retarget(e, c.getView()));
      }
    }

    @Override
    public boolean wantsLongPress() {
      return false;
    }

    @Override
    public void setComponent(Component component) {
      super.setComponent(component);

      if (component != null) {
        component.addMouseListener(this);
      }
    }

    public void setTabPainter(aPlatformTabPainter tabPainter) {
      if (this.tabPainter != null) {
        this.tabPainter.setHeader(null);
      }

      this.tabPainter = tabPainter;

      if (this.tabPainter != null) {
        this.tabPainter.setHeader((iParentComponent) component);
      }
    }

    @Override
    public void getMinimumSize(UIDimension size) {
      tabPainter.getMinimumSize(size);
    }

    @Override
    public void getPreferredSize(UIDimension size, float maxWidth) {
      tabPainter.getPreferredSize(size);
    }

    public aPlatformTabPainter getTabPainter() {
      return tabPainter;
    }

    @Override
    protected void disposeEx() {
      super.disposeEx();
      tabPainter = null;
    }
  }
}
