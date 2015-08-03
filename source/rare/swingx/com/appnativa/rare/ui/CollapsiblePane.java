/*
 * @(#)CollapsiblePane.java   2012-03-17
 *
 * Copyright (c) 2007-2009 appNativa Inc. All rights reserved.
 *
 * Use is subject to license terms.
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
import com.appnativa.rare.ui.event.ExpansionEvent;
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
    eventObject              = new ExpansionEvent(this);
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
