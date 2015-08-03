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

package com.appnativa.rare.platform.apple.ui.util;

import com.appnativa.rare.platform.apple.ui.view.ScrollBarView;
import com.appnativa.rare.platform.apple.ui.view.ScrollView;
import com.appnativa.rare.platform.apple.ui.view.View;
import com.appnativa.rare.spot.ScrollPane;
import com.appnativa.rare.ui.Container;
import com.appnativa.rare.ui.ScrollPanePanel;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.iListHandler;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.listener.WidgetScriptChangeListener;
import com.appnativa.rare.ui.painter.UIScrollingEdgePainter;
import com.appnativa.rare.viewer.aViewer;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.spot.SPOTEnumerated;

public class AppleHelper {
  static ScrollPane DEFAULT_SCROLLPANE_CONFIG = new ScrollPane();

  public static void configureScrollBarView(iWidget w, ScrollView sv, SPOTEnumerated cfg, boolean vertical) {
    int           val = cfg.intValue();
    ScrollBarView sb  = null;

    if ((val == ScrollPane.CVerticalScrollbar.show_as_needed) || (val == ScrollPane.CVerticalScrollbar.show_always)) {
      if (vertical) {
        sv.setHasVerticalScrollBar(true);
        sb = sv.getVerticalScrollBar();
      } else {
        sv.setHasHorizontalScrollBar(true);
        sb = sv.getHorizontalScrollBar();
      }

      if (val == ScrollPane.CVerticalScrollbar.show_always) {
        sb.setShowAlways(true);
      }

      String code = cfg.spot_getAttribute("onChange");

      if ((code != null)) {
        sb.addChangeListener(new WidgetScriptChangeListener(w, code));
      }
    } else {
      if (vertical) {
        sv.setHasVerticalScrollBar(false);
      } else {
        sv.setHasHorizontalScrollBar(false);
      }
    }
  }

  public static iPlatformComponent configureScrollPane(iWidget lv, iPlatformComponent fc, final View view,
          ScrollPane sp) {
    boolean scrollview = view.isScrollView();

    if ((sp == null) && (lv instanceof iListHandler)) {
      sp = DEFAULT_SCROLLPANE_CONFIG;
    }

    if (sp == null) {
      return fc;
    }

    ScrollView sv;
    boolean    wrapped = false;

    if (scrollview) {
      sv      = ScrollView.wrap(view.getProxy());
      wrapped = true;
    } else {
      sv = new ScrollView();
    }

    configureScrollBarView(lv, sv, sp.verticalScrollbar, true);
    configureScrollBarView(lv, sv, sp.horizontalScrollbar, false);

    if (sp.isAlwaysHidden()) {
      sv.setScrollEnabled(false);
    }

    iPlatformComponent c;

    if (scrollview) {
      c = fc;
      sv.unwrap();
    } else {
      Container cc = new ScrollPanePanel(sv);

      cc.add(fc, 0);
      fc.getView().makeTransparent();
      cc.setBackground(UIColor.WHITE);
      c = cc;
    }

    if (!wrapped && sv.hasHorizontalScrollBar()) {
      sv.setScrollingEdgePainter(UIScrollingEdgePainter.getInstance());
    }

    return c;
  }

  public static ScrollBarView createScrollBarView(iWidget w, SPOTEnumerated cfg, boolean horizontal) {
    int           val = cfg.intValue();
    ScrollBarView sb  = null;

    if ((val == ScrollPane.CVerticalScrollbar.show_as_needed) || (val == ScrollPane.CVerticalScrollbar.show_always)) {
      sb = new ScrollBarView(horizontal);

      if (val == ScrollPane.CVerticalScrollbar.show_always) {
        sb.setShowAlways(true);
      }

      String code = cfg.spot_getAttribute("onChange");

      if ((code != null)) {
        sb.addChangeListener(new WidgetScriptChangeListener(w, code));
      }
    }

    return sb;
  }

  public static iPlatformComponent makeScrollPane(aViewer context, ScrollPane sp, iPlatformComponent content) {
    if (sp == null) {
      sp = DEFAULT_SCROLLPANE_CONFIG;
    }

    return configureScrollPane(context, content, content.getView(), sp);
  }
}
