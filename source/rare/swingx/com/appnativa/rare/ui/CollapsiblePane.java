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

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;

import com.appnativa.rare.Platform;
import com.appnativa.rare.platform.swing.ui.util.SwingGraphics;
import com.appnativa.rare.platform.swing.ui.view.BorderLayoutView;
import com.appnativa.rare.platform.swing.ui.view.LabelView;
import com.appnativa.rare.ui.PainterUtils.ChevronIcon;
import com.appnativa.rare.ui.PainterUtils.TwistyIcon;
import com.appnativa.rare.ui.listener.iMouseListener;

/**
 *
 * @author Don DeCoteau
 */
public class CollapsiblePane extends aCollapsiblePane {

  /**
   * Creates a new instance of CollapsiblePane
   */
  public CollapsiblePane() {
    this(null, null);
  }

  /**
   * Creates a new instance of CollapsiblePane
   *
   * @param title the title
   * @param c the component
   */
  public CollapsiblePane(String title, iPlatformComponent c) {
    super();
    toggleOnTitleSingleClick = Platform.isTouchDevice();
    initComponents();

    if (title != null) {
      setTitleText(title);
    }

    if (c != null) {
      add(c);
    }
  }

  @Override
  protected void createAndAddTitleLabel() {
    titleComponent = new TitleComponent(this);
    setTopView(titleComponent);
  }

  @Override
  protected iPlatformIcon createChevronIcon(boolean up) {
    return new ChevronIcon(this, up) {
      SwingGraphics graphics;
      @Override
      public void paintIcon(Component c, Graphics g, int x, int y) {
        graphics = SwingGraphics.fromGraphics(g, c, graphics);
        paint(graphics, x, y, -1, -1);
        graphics.clear();
      }
    };
  }

  @Override
  protected iPlatformIcon createTwistyIcon(boolean up) {
    return new TwistyIcon(this, up) {
      SwingGraphics graphics;
      @Override
      public void paintIcon(Component c, Graphics g, int x, int y) {
        graphics = SwingGraphics.fromGraphics(g, c, graphics);
        paint(graphics, x, y, c.getWidth(), c.getHeight());
        graphics.clear();
      }
    };
  }

  @Override
  protected boolean isEmpty() {
    return getCenterView() == null;
  }

  static class TitleComponent extends aTitleComponent implements iMouseListener {
    CollapsiblePane pane;

    TitleComponent(CollapsiblePane pane) {
      super(new BorderLayoutView());

      LabelView title = new LabelView();

      this.pane  = pane;
      titleLabel = new ActionComponent(title);
      titleLabel.setForeground(ColorUtils.getForeground());
      setCenterView(titleLabel);
      view.addMouseListener(new MouseAdapter() {
        @Override
        public void mousePressed(java.awt.event.MouseEvent e) {
          CollapsiblePane pane = TitleComponent.this.pane;

          if (!pane.userControllable) {
            return;
          }

          iPlatformComponent n = iconOnLeft
                                 ? getRightView()
                                 : getLeftView();

          if (n != null) {
            UIRectangle r = new UIRectangle(n.getLocationOnScreen(), n.getSize());
            Point       p = e.getLocationOnScreen();

            if (r.contains(p.x, p.y)) {
              pane.togglePane();

              return;
            }
          }

          if ((pane.toggleOnTitleSingleClick || (e.getClickCount() > 1))) {
            pane.togglePane();
          }
        }
      });
    }

    @Override
    protected iActionComponent createIconComponent() {
      return new ActionComponent(new LabelView());
    }
  }
}
