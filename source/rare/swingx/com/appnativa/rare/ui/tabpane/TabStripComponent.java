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
import com.appnativa.rare.platform.swing.ui.util.SwingGraphics;
import com.appnativa.rare.platform.swing.ui.util.SwingHelper;
import com.appnativa.rare.platform.swing.ui.view.JPanelEx;
import com.appnativa.rare.ui.Component;
import com.appnativa.rare.ui.Location;
import com.appnativa.rare.ui.RenderableDataItem.Orientation;
import com.appnativa.rare.ui.UIInsets;
import com.appnativa.rare.ui.event.MouseEvent;
import com.appnativa.rare.ui.iParentComponent;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;

public class TabStripComponent extends aTabStripComponent {
  public TabStripComponent() {
    super(new TabStripView());
  }

  @Override
  public void setTabPainter(aPlatformTabPainter painter) {
    ((TabStripView) view).setTabPainter(painter);
  }

  @Override
  public Orientation getOrientation() {
    aPlatformTabPainter tp = getTabPainter();

    if (!tp.isHandlesRightLeftRotation()) {
      switch(tp.getPosition()) {
        case RIGHT :
          return Orientation.VERTICAL_DOWN;

        case LEFT :
          return Orientation.VERTICAL_UP;

        default :
          break;
      }
    }

    return Orientation.HORIZONTAL;
  }

  @Override
  public aPlatformTabPainter getTabPainter() {
    return ((TabStripView) view).getTabPainter();
  }

  static class TabStripView extends JPanelEx {
    private aPlatformTabPainter tabPainter;

    public TabStripView() {
      setOpaque(false);
      addMouseListener(new MouseAdapter() {
        @Override
        public void mousePressed(java.awt.event.MouseEvent e) {
          handleMousePressed(e);
        }
      });
    }

    @Override
    public void paint(Graphics g) {
      Graphics2D g2 = null;

      if (!tabPainter.isHandlesRightLeftRotation()) {
        switch(tabPainter.getPosition()) {
          case RIGHT :
            g2 = (Graphics2D) g.create();
            g2.rotate(Math.PI / 2);
            g2.translate(0, -getWidth());
            g = g2;

            break;

          case LEFT :
            g2 = (Graphics2D) g.create();
            g2.rotate(-Math.PI / 2);
            g2.translate(-getHeight(), 0);
            g = g2;

            break;

          default :
            break;
        }
      }

      super.paint(g);

      if (g2 != null) {
        g2.dispose();
      }
    }

    public void setTabPainter(aPlatformTabPainter tabPainter) {
      if (this.tabPainter != null) {
        this.tabPainter.setHeader(null);
      }

      this.tabPainter = tabPainter;

      if (this.tabPainter != null) {
        this.tabPainter.setHeader((iParentComponent) Component.fromView(this));
      }
    }

    public aPlatformTabPainter getTabPainter() {
      return tabPainter;
    }

    protected void handleMousePressed(java.awt.event.MouseEvent event) {
      UIInsets   in = SwingHelper.setUIInsets(null, getInsets());
      MouseEvent e  = EventHelper.createMouseEvent(this, event);

      if (aTabStripComponent.handleMousePressed(e, tabPainter, in, getWidth(), getHeight())) {
        revalidate();
      }
    }

    @Override
    protected void layoutContainerEx(int width, int height) {
      if (!tabPainter.isHorizontal() &&!tabPainter.isHandlesRightLeftRotation()) {
        tabPainter.layout(0, 0, height, width);
      } else {
        tabPainter.layout(0, 0, width, height);
      }
    }

    @Override
    protected void paintComponent(Graphics g) {
      Graphics2D g2 = null;

      if (!tabPainter.isHandlesBottomRotation() && (tabPainter.getPosition() == Location.BOTTOM)) {
        g2 = (Graphics2D) g.create();
        g2.scale(1.0, -1.0);
        g2.translate(0, -getHeight());
        graphics = SwingGraphics.fromGraphics(g2, this, graphics);
        super.paintComponent(g);
        tabPainter.paint(graphics, 0, 0, getWidth(), getHeight());
        graphics = SwingGraphics.fromGraphics(g, this, graphics);
      } else {
        super.paintComponent(g);
        tabPainter.paint(graphics, 0, 0, getWidth(), getHeight());
      }
    }
  }
}
