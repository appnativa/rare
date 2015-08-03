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
import com.appnativa.rare.platform.apple.ui.view.BorderLayoutView;
import com.appnativa.rare.platform.apple.ui.view.LabelView;
import com.appnativa.rare.ui.PainterUtils.ChevronIcon;
import com.appnativa.rare.ui.PainterUtils.TwistyIcon;
import com.appnativa.rare.ui.event.ExpansionEvent;
import com.appnativa.rare.ui.event.MouseEvent;
import com.appnativa.rare.ui.listener.aMouseAdapter;
import com.appnativa.rare.ui.listener.iMouseListener;

import com.google.j2objc.annotations.Weak;

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
    return new ChevronIcon(this, up);
  }

  @Override
  protected iPlatformIcon createTwistyIcon(boolean up) {
    return new TwistyIcon(this, up);
  }

  static class MouseListener extends aMouseAdapter {
    @Weak
    TitleComponent title;

    public MouseListener(TitleComponent title) {
      this.title = title;
    }

    @Override
    public void mousePressed(MouseEvent e) {
      if (!title.pane.userControllable) {
        return;
      }

      iPlatformComponent n = title.iconOnLeft
                             ? title.getRightView()
                             : title.getLeftView();

      if (n != null) {
        UIRectangle r = new UIRectangle(n.getLocationOnScreen(), n.getSize());

        if (r.contains((int) e.getScreenX(), (int) e.getScreenY())) {
          title.pane.togglePane();

          return;
        }
      }

      if ((title.pane.toggleOnTitleSingleClick || (e.getClickCount() > 1))) {
        title.pane.togglePane();
      }

      super.mousePressed(e);
    }
  }


  static class TitleComponent extends aTitleComponent implements iMouseListener {
    @Weak
    CollapsiblePane pane;

    TitleComponent(CollapsiblePane pane) {
      super(new BorderLayoutView());

      LabelView title = new LabelView();

      this.pane  = pane;
      titleLabel = new ActionComponent(title);
      setCenterView(titleLabel);
      addMouseListener(new MouseListener(this));
    }

    @Override
    protected iActionComponent createIconComponent() {
      return new ActionComponent(new LabelView());
    }
  }
}
