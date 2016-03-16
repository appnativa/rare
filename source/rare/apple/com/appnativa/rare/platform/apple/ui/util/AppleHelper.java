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
import com.appnativa.rare.platform.apple.ui.view.aPlatformTableBasedView;
import com.appnativa.rare.spot.ScrollPane;
import com.appnativa.rare.spot.Widget;
import com.appnativa.rare.ui.BorderPanel;
import com.appnativa.rare.ui.Container;
import com.appnativa.rare.ui.Location;
import com.appnativa.rare.ui.ScrollPanePanel;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.iListHandler;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.iScrollerSupport;
import com.appnativa.rare.ui.listener.WidgetScriptChangeListener;
import com.appnativa.rare.ui.painter.UIScrollingEdgePainter;
import com.appnativa.rare.ui.table.TableComponent;
import com.appnativa.rare.viewer.FormViewer;
import com.appnativa.rare.viewer.aViewer;
import com.appnativa.rare.widget.aPlatformWidget;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.spot.SPOTEnumerated;

public class AppleHelper {
  static ScrollPane DEFAULT_SCROLLPANE_CONFIG = new ScrollPane();

  public static void configureScrollBarView(iWidget w, ScrollView sv, SPOTEnumerated cfg, boolean vertical) {
    int           val = cfg.intValue();
    ScrollBarView sb  = null;

    if ((val == ScrollPane.CVerticalScrollbar.show_as_needed) || (val == ScrollPane.CVerticalScrollbar.show_always)) {
      if (vertical) {
        sb = sv.getVerticalScrollBar();
      } else {
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
        sv.setShowsVerticalScrollIndicator(false);
      } else {
        sv.setShowsHorizontalScrollIndicator(false);
      }
    }
  }

  public static iPlatformComponent configureScrollPane(iWidget widget, iPlatformComponent fc, final View view,
          ScrollPane sp) {
    boolean scrollview = view.isScrollView();

    if ((sp == null) && (widget instanceof iListHandler)) {
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

    configureScrollBarView(widget, sv, sp.verticalScrollbar, true);
    configureScrollBarView(widget, sv, sp.horizontalScrollbar, false);

    if (sp.isAlwaysHidden()) {
      sv.setScrollEnabled(false);
    }

    iPlatformComponent c;

    if (scrollview) {
      c = fc;

      if ((view instanceof aPlatformTableBasedView)) {
        sv.setScrollBarListeners((aPlatformTableBasedView) view);
      }

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

    aViewer v = (aViewer) widget.getViewer();

    if ((v != null) && (sp.hasColumnWidgets() || sp.hasRowWidgets())) {
      Widget             wc;
      iWidget            w;
      BorderPanel        bp = new BorderPanel();
      iPlatformComponent pc;

      bp.setUseCrossPattern(true);
      bp.setCenterView(c);
      wc = (Widget) sp.columnHeader.getValue();
      w  = getWidget(widget, wc);

      if (w != null) {
        v.registerOrphanWidget(w);
        pc = getScrollComponent(w);
        bp.setTopView(pc);
        setScrollComponentView(fc, view, pc, Location.TOP);
      }

      wc = (Widget) sp.columnFooter.getValue();
      w  = getWidget(widget, wc);

      if (w != null) {
        v.registerOrphanWidget(w);
        pc = getScrollComponent(w);
        bp.setBottomView(pc);
        setScrollComponentView(fc, view, pc, Location.BOTTOM);
      }

      wc = (Widget) sp.rowFooter.getValue();
      w  = getWidget(widget, wc);

      if (w != null) {
        v.registerOrphanWidget(w);
        pc = getScrollComponent(w);
        bp.setRightView(pc);
        setScrollComponentView(fc, view, pc, Location.RIGHT);
      }

      wc = (Widget) sp.rowHeader.getValue();
      w  = getWidget(widget, wc);

      if (w != null) {
        v.registerOrphanWidget(w);
        pc = getScrollComponent(w);
        bp.setLeftView(pc);
        setScrollComponentView(fc, view, pc, Location.LEFT);
      }

      bp.setScrollPaneCorners(v, sp);
      c = bp;
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

  public static iWidget getWidget(iWidget context, Widget wc) {
    if (wc == null) {
      return null;
    }

    return FormViewer.createWidget(context.getContainerViewer(), wc);
  }

  public static iPlatformComponent makeScrollPane(aViewer context, ScrollPane sp, iPlatformComponent content) {
    if (sp == null) {
      sp = DEFAULT_SCROLLPANE_CONFIG;
    }

    return configureScrollPane(context, content, content.getView(), sp);
  }

  private static iScrollerSupport getScrollView(iPlatformComponent pc) {
    View             view = pc.getView();
    iScrollerSupport c;

    if (view instanceof iScrollerSupport) {
      c = (iScrollerSupport) view;
    } else {
      c = new ScrollView();

      Container cc = new ScrollPanePanel(c);

      cc.add(pc, 0);
    }

    return c;
  }

  private static iPlatformComponent getScrollComponent(iWidget w) {
    iPlatformComponent pc   = w.getContainerComponent();
    View               view = pc.getView();
    iPlatformComponent c;

    if (view instanceof iScrollerSupport) {
      c = pc;
    } else {
      c = new ScrollPanePanel(new ScrollView());
      c.setVisible(pc.isVisible());
      c.setEnabled(pc.isEnabled());
      pc.setEnabled(true);
      pc.setVisible(true);
      addComponentAsWrapper((aPlatformWidget) w, (ScrollPanePanel) c);
    }

    return c;
  }

  private static native void addComponentAsWrapper(aPlatformWidget w, ScrollPanePanel c)
  /*-[
    [c  addWithRAREiPlatformComponent:w->formComponent_];
    c->scollPaneWidget_=w;
    w->formComponent_= c;
  ]-*/
  ;

  private static void setScrollComponentView(iPlatformComponent fc, View parent, iPlatformComponent child,
          Location loc) {
    switch(loc) {
      case TOP :
        if (fc instanceof TableComponent) {
          ((TableComponent) fc).setHeaderView(getScrollView(child));
        } else if (parent instanceof ScrollView) {
          ((ScrollView) parent).setHeaderView(getScrollView(child));
        } else if (parent instanceof aPlatformTableBasedView) {
          ((aPlatformTableBasedView) parent).setHeaderView(getScrollView(child));
        }

        break;

      case BOTTOM :
        if (fc instanceof TableComponent) {
          ((TableComponent) fc).setFooterView(getScrollView(child));
        } else if (parent instanceof ScrollView) {
          ((ScrollView) parent).setFooterView(getScrollView(child));
        } else if (parent instanceof aPlatformTableBasedView) {
          ((aPlatformTableBasedView) parent).setFooterView(getScrollView(child));
        }

        break;

      case LEFT :
        if (parent instanceof ScrollView) {
          ((ScrollView) parent).setRowHeaderView(getScrollView(child));
        } else if (parent instanceof aPlatformTableBasedView) {
          ((aPlatformTableBasedView) parent).setRowHeaderView(getScrollView(child));
        }

        break;

      case RIGHT :
        if (parent instanceof ScrollView) {
          ((ScrollView) parent).setRowFooterView(getScrollView(child));
        } else if (parent instanceof aPlatformTableBasedView) {
          ((aPlatformTableBasedView) parent).setRowFooterView(getScrollView(child));
        }

        break;

      default :
        break;
    }
  }
}
