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

package com.appnativa.rare.platform.swing.ui.view;

import com.appnativa.rare.Platform;
import com.appnativa.rare.platform.swing.ui.util.SwingGraphics;
import com.appnativa.rare.platform.swing.ui.util.SwingHelper;
import com.appnativa.rare.ui.BorderPanel;
import com.appnativa.rare.ui.ScreenUtils;
import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.UIRectangle;
import com.appnativa.rare.ui.effects.aAnimator;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.painter.iPainter;
import com.appnativa.rare.ui.painter.iPainterSupport;
import com.appnativa.rare.ui.painter.iPlatformComponentPainter;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Rectangle;

import javax.swing.JComponent;
import javax.swing.JLayeredPane;
import javax.swing.JRootPane;

/**
 * A JRootPane that supports a custom title pane for use in an undecorated frame
 *
 * @author Don DeCoteau
 */
public class JRootPaneEx extends JRootPane implements iPainterSupport {
  private iPlatformComponentPainter componentPainter;
  private SwingGraphics             graphics;
  private JComponent                titlePane;

  public JRootPaneEx() {
    super();
    setOpaque(true);
  }

  @Override
  public void setComponentPainter(iPlatformComponentPainter cp) {
    componentPainter = cp;

    if (cp != null) {
      if (cp.getBorder() != null) {
        setBorder(cp.getBorder());
      }
    }
  }

  /**
   * @param c
   *          the title pane component
   */
  public void setTitlePane(JComponent c) {
    if ((titlePane != null) && (titlePane.getParent() == layeredPane)) {
      layeredPane.remove(titlePane);
    }

    titlePane = c;

    if (titlePane != null) {
      layeredPane.add(titlePane, JLayeredPane.FRAME_CONTENT_LAYER);
    }
  }

  @Override
  public iPlatformComponentPainter getComponentPainter() {
    return componentPainter;
  }

  public void getInnerBounds(UIRectangle rect) {
    rect.width  = 0;
    rect.height = 0;

    Dimension d = getSize();

    rect.width  = d.width;
    rect.height = d.height;

    if ((titlePane != null) && titlePane.isVisible()) {
      titlePane.getSize(d);
      rect.height -= d.height;
      rect.y      += d.height;
    }

    if ((menuBar != null) && menuBar.isVisible()) {
      menuBar.getSize(d);
      rect.height -= d.height;
      rect.y      += d.height;
    }
  }

  @Override
  public Dimension getMaximumSize() {
    UIDimension d = ScreenUtils.getSize();

    return new Dimension(d.intWidth(), d.intHeight());
  }

  /**
   * @return the titlePane
   */
  public JComponent getTitlePane() {
    return titlePane;
  }

  @Override
  protected Container createContentPane() {
    BorderPanel bp = new BorderPanel(new BorderLayoutViewEx());
    JComponent  c  = bp.getJComponent();

    c.setOpaque(true);
    c.setName(this.getName() + ".contentPane");

    return c;
  }

  @Override
  protected LayoutManager createRootLayout() {
    return new RootLayoutEx();
  }

  @Override
  protected void paintBorder(Graphics g) {
    if (componentPainter == null) {
      super.paintBorder(g);
    }
  }

  @Override
  protected void paintChildren(Graphics g) {
    iPlatformComponentPainter cp     = getComponentPainter();
    float                     height = getHeight();
    float                     width  = getWidth();

    graphics = SwingGraphics.fromGraphics(g, this, graphics);

    if (cp != null) {
      cp.paint(graphics, 0, 0, width, height, iPainter.HORIZONTAL, false);
    }

    super.paintChildren(g);

    if (cp != null) {
      cp.paint(graphics, 0, 0, width, height, iPainter.HORIZONTAL, true);
    }
  }

  static class BorderLayoutViewEx extends BorderLayoutView {
    public BorderLayoutViewEx() {
      super();
    }
  }


  protected class RootLayoutEx extends RootLayout {
    @Override
    public void layoutContainer(Container parent) {
      if (titlePane == null) {
        super.layoutContainer(parent);

        return;
      }

      Rectangle b        = parent.getBounds();
      Insets    i        = getInsets();
      int       contentY = 0;
      int       w        = b.width - i.right - i.left;
      int       h        = b.height - i.top - i.bottom;

      if (layeredPane != null) {
        layeredPane.setBounds(i.left, i.top, w, h);
      }

      if (glassPane != null) {
        glassPane.setBounds(i.left, i.top, w, h);
      }

      // Note: This is laying out the children in the layeredPane,
      // technically, these are not our children.
      if ((titlePane != null) && titlePane.isVisible()) {
        iPlatformComponent pc  = Platform.createPlatformComponent(titlePane);
        UIDimension        mbd = pc.getPreferredSize();

        pc.setBounds(0, contentY, w, mbd.height);
        contentY += mbd.height;
      }

      if ((menuBar != null) && menuBar.isVisible()) {
        Dimension mbd = menuBar.getPreferredSize();

        menuBar.setBounds(0, contentY, w, mbd.height);
        contentY += mbd.height;
      }

      if (contentPane != null) {
        iPlatformComponent c = com.appnativa.rare.ui.Component.fromView((JComponent) contentPane);

        if ((c == null) ||!aAnimator.isAnimating(c)) {
          contentPane.setBounds(0, contentY, w, h - contentY);
        }
      }
    }

    /**
     * Returns the minimum amount of space the layout needs.
     *
     * @param parent
     *          the Container for which this layout manager is being used
     * @return a Dimension object containing the layout's minimum size
     */
    @Override
    public Dimension minimumLayoutSize(Container parent) {
      Dimension rd = null, mbd;
      Insets    i  = getInsets();

      if (contentPane != null) {
        iPlatformComponent pc = Platform.createPlatformComponent(contentPane);

        rd = SwingHelper.setDimension(rd, pc.getMinimumSize());
      } else {
        rd = parent.getSize();
      }

      if ((menuBar != null) && menuBar.isVisible()) {
        mbd = menuBar.getMinimumSize();
      } else {
        mbd = new Dimension(0, 0);
      }

      UIDimension dd = ((titlePane == null) ||!titlePane.isVisible())
                       ? null
                       : Platform.createPlatformComponent(titlePane).getMinimumSize();

      if (dd != null) {
        rd.width  = Math.max(rd.width, dd.intWidth());
        rd.height += dd.height;
      }

      return new Dimension(Math.max(rd.width, mbd.width) + i.left + i.right, rd.height + mbd.height + i.top + i.bottom);
    }

    /**
     * Returns the amount of space the layout would like to have.
     *
     * @param parent
     *          the Container for which this layout manager is being used
     * @return a Dimension object containing the layout's preferred size
     */
    @Override
    public Dimension preferredLayoutSize(Container parent) {
      Dimension rd = null, mbd;
      Insets    i  = getInsets();

      if (contentPane != null) {
        iPlatformComponent pc = Platform.createPlatformComponent(contentPane);

        rd = SwingHelper.setDimension(rd, pc.getPreferredSize());
      } else {
        rd = parent.getSize();
      }

      if ((menuBar != null) && menuBar.isVisible()) {
        mbd = menuBar.getPreferredSize();
      } else {
        mbd = new Dimension(0, 0);
      }

      UIDimension dd = ((titlePane == null) ||!titlePane.isVisible())
                       ? null
                       : Platform.createPlatformComponent(titlePane).getPreferredSize();

      if (dd != null) {
        rd.width  = Math.max(rd.width, dd.intWidth());
        rd.height += dd.height;
      }

      return new Dimension(Math.max(rd.width, mbd.width) + i.left + i.right, rd.height + mbd.height + i.top + i.bottom);
    }
  }


  public void disposeOfPane() {
    try {
      if (componentPainter != null) {
        componentPainter.dispose();
      }

      if (contentPane != null) {
        iPlatformComponent pc = Platform.createPlatformComponent(contentPane);

        if (pc != null) {
          pc.dispose();
        }
      }
    } catch(Exception ignore) {}
  }
}
