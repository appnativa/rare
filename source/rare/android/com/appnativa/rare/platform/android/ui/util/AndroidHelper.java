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

package com.appnativa.rare.platform.android.ui.util;

import android.annotation.SuppressLint;

import android.app.Activity;
import android.app.AlertDialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.res.Resources;

import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import android.widget.AbsListView;
import android.widget.HorizontalScrollView;
import android.widget.ListView;
import android.widget.ScrollView;

import com.appnativa.rare.Platform;
import com.appnativa.rare.iConstants;
import com.appnativa.rare.iFunctionCallback;
import com.appnativa.rare.iPlatformAppContext;
import com.appnativa.rare.platform.PlatformHelper;
import com.appnativa.rare.platform.android.AppContext;
import com.appnativa.rare.platform.android.ui.view.HorizontalScrollViewEx;
import com.appnativa.rare.platform.android.ui.view.ScrollViewEx;
import com.appnativa.rare.spot.ScrollPane;
import com.appnativa.rare.spot.Widget;
import com.appnativa.rare.ui.BorderPanel;
import com.appnativa.rare.ui.Component;
import com.appnativa.rare.ui.FontUtils;
import com.appnativa.rare.ui.GraphicsComposite;
import com.appnativa.rare.ui.RenderableDataItem.HorizontalAlign;
import com.appnativa.rare.ui.RenderableDataItem.IconPosition;
import com.appnativa.rare.ui.RenderableDataItem.VerticalAlign;
import com.appnativa.rare.ui.ScreenUtils;
import com.appnativa.rare.ui.ScrollPanePanel;
import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.UIFont;
import com.appnativa.rare.ui.UIInsets;
import com.appnativa.rare.ui.UIStroke;
import com.appnativa.rare.ui.aAdjustable;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.iPlatformIcon;
import com.appnativa.rare.ui.listener.WidgetScriptChangeListener;
import com.appnativa.rare.viewer.WindowViewer;
import com.appnativa.rare.viewer.aViewer;
import com.appnativa.rare.viewer.iContainer;
import com.appnativa.rare.widget.ComboBoxWidget;
import com.appnativa.rare.widget.PushButtonWidget;
import com.appnativa.rare.widget.aPlatformWidget;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.spot.SPOTEnumerated;

import java.lang.reflect.Constructor;

import java.util.List;
import java.util.Locale;

/**
 *
 * @author Don DeCoteau
 */
@SuppressLint("ClickableViewAccessibility")
public class AndroidHelper {
  private static Drawable[] drawables = new Drawable[4];

  public static void addRectToInsets(UIInsets in, Rect r) {
    in.addInsets(r.top, r.right, r.bottom, r.left);
  }

  public static PorterDuffXfermode resolveComposite(GraphicsComposite gc) {
    PorterDuffXfermode pdm = (PorterDuffXfermode) gc.getPlatformComposite();

    if (pdm == null) {
      pdm = new PorterDuffXfermode(PainterUtils.getPorterDuffMode(gc.getCompositeType()));
    }

    gc.setPlatformComposite(pdm);

    return pdm;
  }

  public static void adjustFontSizeForScreen() {
    try {
      if (!ScreenUtils.isSmallScreen()) {
        return;
      }

      UIDimension d      = ScreenUtils.getSize();
      float       pixels = Math.max(d.width, d.height);

      if (pixels < 680) {
        return;
      }

      float dfs = FontUtils.getDefaultFontSize();

      if (FontUtils.getRelativeFontSize() == 1) {
        UIFont f = FontUtils.getDefaultFont();

        f = f.deriveFontSize(f.getSize2D() - 4);
        Platform.getUIDefaults().put("Rare.Chart.font", f);
        FontUtils.setRelativeFontSize(10 / dfs);
      } else {
        FontUtils.setRelativeFontSize(1);
      }
    } catch(Throwable e) {
      Platform.ignoreException(null, e);
    }
  }

  public static iPlatformComponent configureScrollPane(final aViewer lv, iPlatformComponent fc, final View view,
          ScrollPane sp) {
    if (sp == null) {
      return fc;
    }

    SPOTEnumerated cfg = sp.verticalScrollbar;
    int            val = cfg.intValue();

    if ((cfg.spot_valueWasSet() && (val == ScrollPane.CVerticalScrollbar.show_as_needed))
        || (val == ScrollPane.CVerticalScrollbar.show_always)) {
      view.setScrollbarFadingEnabled(false);
      view.setVerticalFadingEdgeEnabled(false);
    }

    String code = cfg.spot_getAttribute("onChange");

    if ((code != null) && (view instanceof AbsListView)) {
      final ScrollBarAdjustable sm = new ScrollBarAdjustable();

      sm.addChangeListener(new WidgetScriptChangeListener(lv, code));

      AbsListView.OnScrollListener sl = new AbsListView.OnScrollListener() {
        public void onScrollStateChanged(AbsListView alv, int scrollState) {}
        public void onScroll(AbsListView alv, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
          sm.setMaximum(totalItemCount);
          sm.setVisibleAmount(visibleItemCount);
          sm.setValue(firstVisibleItem);
        }
      };

      ((AbsListView) view).setOnScrollListener(sl);
    }

    BorderPanel bp = null;
    Widget      wc = null;
    iWidget     w;

    if (view instanceof ListView) {
      ListView v = (ListView) view;

      wc = (Widget) sp.columnFooter.getValue();
      w  = PlatformHelper.getWidget(lv, wc);

      if (w != null) {
        lv.registerNamedItem(w.getName(), w);
        v.addFooterView(w.getContainerComponent().getView());
      }

      wc = (Widget) sp.columnHeader.getValue();
      w  = PlatformHelper.getWidget(lv, wc);

      if (w != null) {
        lv.registerNamedItem(w.getName(), w);
        v.addHeaderView(w.getContainerComponent().getView());
      }
    } else {
      bp = new BorderPanel(lv);
      wc = (Widget) sp.columnHeader.getValue();
      w  = PlatformHelper.getWidget(lv, wc);

      if (w != null) {
        lv.registerNamedItem(w.getName(), w);
        bp.setTopView(w.getContainerComponent());
      }

      wc = (Widget) sp.columnFooter.getValue();
      w  = PlatformHelper.getWidget(lv, wc);

      if (w != null) {
        lv.registerNamedItem(w.getName(), w);
        bp.setBottomView(w.getContainerComponent());
      }
    }

    wc = (Widget) sp.rowFooter.getValue();
    w  = PlatformHelper.getWidget(lv, wc);

    if (w != null) {
      if (bp == null) {
        bp = new BorderPanel(lv);
      }

      lv.registerNamedItem(w.getName(), w);
      bp.setRightView(w.getContainerComponent());
    }

    if (bp != null) {
      bp.setCenterView(Component.fromView(view));
      fc = bp;
    }

    return fc;
  }

  public static void confirmAction(iPlatformAppContext app, String title, String message, boolean yesno,
                                   final iFunctionCallback cb) {
    final String[] options = new String[2];

    if (yesno) {
      options[0] = app.getResourceAsString("Rare.text.yes");
      options[1] = app.getResourceAsString("Rare.text.no");
    } else {
      options[0] = app.getResourceAsString("Rare.text.ok");
      options[1] = app.getResourceAsString("Rare.text.cancel");
    }

    AlertDialog.Builder alertDialog = new AlertDialog.Builder(app.getActivity());

    alertDialog.setTitle((title == null)
                         ? app.getResourceAsString("Rare.text.info")
                         : title);
    alertDialog.setMessage(message);
    alertDialog.setPositiveButton(options[0], new DialogInterface.OnClickListener() {
      public void onClick(DialogInterface dialog, int which) {
        dialog.dismiss();
        cb.finished(false, true);

        return;
      }
    });
    alertDialog.setNegativeButton(options[1], new DialogInterface.OnClickListener() {
      public void onClick(DialogInterface dialog, int which) {
        dialog.dismiss();
        cb.finished(false, false);
      }
    });
    alertDialog.setOnCancelListener(new OnCancelListener() {
      public void onCancel(DialogInterface dialog) {
        cb.finished(true, false);
      }
    });
    alertDialog.create().show();
  }

  public static boolean containsPopup(iWidget w) {
    if (w instanceof ComboBoxWidget) {
      return true;
    }

    if (w instanceof PushButtonWidget) {
      PushButtonWidget bw = (PushButtonWidget) w;

      return bw.hasPopupWidget();
    }

    if (w instanceof iContainer) {
      List<iWidget> widgets = ((iContainer) w).getWidgetList();
      int           len     = widgets.size();

      for (int i = 0; i < len; i++) {
        if (containsPopup(widgets.get(i))) {
          return true;
        }
      }
    }

    return false;
  }

  public static Path createCommentShape(int x, int y, int width, int height, boolean no_bottom) {
    Path gp  = new Path();
    int  arc = 4;

    if (no_bottom == true) {
      gp.moveTo(0, height);
      gp.lineTo(0, 5 + arc);
    } else {
      gp.moveTo(0, 5 + arc);
    }

    gp.quadTo(0, 5, arc, 5);
    gp.lineTo(15, 5);
    gp.lineTo(20, 0);
    gp.lineTo(25, 5);
    gp.lineTo(width - arc, 5);
    gp.quadTo(width, 5, width, 5 + arc);
    gp.lineTo(width, height - arc);

    if (no_bottom) {
      gp.lineTo(width, height);
    } else {
      gp.quadTo(width, height, width - arc, height);
      gp.lineTo(arc, height);
      gp.quadTo(0, height, 0, height - arc);
      gp.lineTo(0, 5 + arc);
    }

    return gp;
  }

  public static void errorMessage(iPlatformAppContext app, String title, String message) {
    errorMessage(app, title, message, null);
  }

  public static void errorMessage(iPlatformAppContext app, String title, String message, final iFunctionCallback cb) {
    AlertDialog alertDialog = new AlertDialog.Builder(app.getActivity()).create();

    alertDialog.setTitle((title == null)
                         ? app.getResourceAsString("Rare.text.error")
                         : title);
    alertDialog.setMessage(message);
    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, app.getResourceAsString("Rare.text.ok"),
                          new DialogInterface.OnClickListener() {
      public void onClick(DialogInterface dialog, int which) {
        if (cb != null) {
          cb.finished(false, null);
        }

        return;
      }
    });
    alertDialog.show();
  }

  public static void infoMessage(iPlatformAppContext app, String title, String message) {
    infoMessage(app, title, message, null);
  }

  public static void infoMessage(iPlatformAppContext app, String title, String message, final iFunctionCallback cb) {
    AlertDialog alertDialog = new AlertDialog.Builder(app.getActivity()).create();

    alertDialog.setTitle((title == null)
                         ? app.getResourceAsString("Rare.text.info")
                         : title);
    alertDialog.setMessage(message);
    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, app.getResourceAsString("Rare.text.ok"),
                          new DialogInterface.OnClickListener() {
      public void onClick(DialogInterface dialog, int which) {
        if (cb != null) {
          cb.finished(false, null);
        }

        return;
      }
    });
    alertDialog.show();
  }

  public static Rect insetsToRect(UIInsets in, Rect r) {
    if (r == null) {
      r = new Rect(in.intLeft(), in.intTop(), in.intRight(), in.intBottom());
    } else {
      r.set(in.intLeft(), in.intTop(), in.intRight(), in.intBottom());
    }

    return r;
  }

  public static iPlatformComponent makeScrollPane(iWidget context, ScrollPane cfg) {
    return makeScrollPane(context, cfg, null);
  }

  public static iPlatformComponent makeScrollPane(iWidget context, ScrollPane cfg, iPlatformComponent comp) {
    ScrollView           sv   = null;
    HorizontalScrollView hsv  = null;
    View                 view = (View) ((comp == null)
            ? null
            : comp.getView());
    Context              ctx  = ((aPlatformWidget) context).getAndroidContext();

    if (cfg.verticalScrollbar.intValue() != ScrollPane.CVerticalScrollbar.hidden) {
      sv = new ScrollViewEx(ctx);

      if ((view != null) && (cfg.verticalScrollbar.intValue() == ScrollPane.CVerticalScrollbar.show_always)) {
        view.setScrollbarFadingEnabled(false);
        view.setVerticalFadingEdgeEnabled(false);
      }
    }

    if ((sv == null) || (cfg.horizontalScrollbar.intValue() != ScrollPane.CHorizontalScrollbar.hidden)) {
      hsv = new HorizontalScrollViewEx(ctx);

      if ((view != null) && (cfg.horizontalScrollbar.intValue() == ScrollPane.CHorizontalScrollbar.show_always)) {
        view.setScrollbarFadingEnabled(false);
        view.setHorizontalFadingEdgeEnabled(false);
      }
    }

    if (sv == null) {
      if ((view != null) && (hsv != null)) {
        hsv.addView(view);
      }
    } else {
      if (view != null) {
        sv.addView(view);
      }

      if (hsv != null) {
        hsv.addView(sv);
      }
    }

    if ((hsv == null) && (sv == null)) {
      return comp;
    }

    return new ScrollPanePanel((hsv != null)
                               ? hsv
                               : sv);
  }

  public static UIInsets rectToInsets(Rect r, UIInsets in) {
    if (in == null) {
      in = new UIInsets(r.top, r.right, r.bottom, r.left);
    } else {
      in.set(r.top, r.right, r.bottom, r.left);
    }

    return in;
  }

  public static void setLineStroke(UIStroke stroke, Paint paint) {
    if (stroke == null) {
      paint.setStrokeWidth(ScreenUtils.PLATFORM_PIXELS_1);
      paint.setStrokeJoin(Paint.Join.MITER);
      paint.setStrokeCap(Paint.Cap.BUTT);
      paint.setStrokeMiter(ScreenUtils.PLATFORM_PIXELS_1);

      if (paint.getPathEffect() instanceof DashPathEffect) {
        paint.setPathEffect(null);
      }
    } else {
      paint.setStrokeWidth(stroke.width);
      paint.setStrokeMiter(stroke.miterLimit);

      if (stroke.dashIntervals != null) {
        paint.setPathEffect(new DashPathEffect(stroke.dashIntervals, stroke.dashPhase));
      } else if (paint.getPathEffect() instanceof DashPathEffect) {
        paint.setPathEffect(null);
      }

      switch(stroke.cap) {
        case BUTT :
          paint.setStrokeCap(Paint.Cap.BUTT);

          break;

        case ROUND :
          paint.setStrokeCap(Paint.Cap.ROUND);

          break;

        case SQUARE :
          paint.setStrokeCap(Paint.Cap.SQUARE);

          break;
      }

      switch(stroke.join) {
        case BEVEL :
          paint.setStrokeJoin(Paint.Join.BEVEL);

          break;

        case MITER :
          paint.setStrokeJoin(Paint.Join.MITER);

          break;

        case ROUND :
          paint.setStrokeJoin(Paint.Join.ROUND);

          break;
      }
    }
  }

  public static void setLineStyle(String style, Paint p) {
    if ((style == null) || style.equals("solid")) {
      return;
    }

    if ("dotted".equalsIgnoreCase(style)) {
      p.setStrokeCap(Paint.Cap.BUTT);
      p.setPathEffect(new DashPathEffect(new float[] { ScreenUtils.PLATFORM_PIXELS_1, ScreenUtils.PLATFORM_PIXELS_1 },
                                         0f));
    } else if ("dashed".equalsIgnoreCase(style)) {
      p.setStrokeCap(Paint.Cap.BUTT);
      p.setPathEffect(new DashPathEffect(new float[] { ScreenUtils.PLATFORM_PIXELS_2, ScreenUtils.PLATFORM_PIXELS_1 },
                                         0f));
    }
  }

  public static int getGravity(HorizontalAlign align) {
    int alignment;

    switch(align) {
      case CENTER :
        alignment = Gravity.CENTER_HORIZONTAL;

        break;

      case RIGHT :
      case TRAILING :
        alignment = Gravity.RIGHT;

        break;

      default :
        alignment = Gravity.LEFT;

        break;
    }

    return alignment;
  }

  public static int getGravity(VerticalAlign align) {
    int alignment;

    switch(align) {
      case TOP :
        alignment = Gravity.TOP;

        break;

      case BOTTOM :
        alignment = Gravity.BOTTOM;

        break;

      default :
        alignment = Gravity.CENTER_VERTICAL;
    }

    return alignment;
  }

  public static int getGravity(HorizontalAlign hal, VerticalAlign val) {
    if (((hal == HorizontalAlign.AUTO) || (hal == HorizontalAlign.LEFT))
        && ((val == VerticalAlign.AUTO) || (val == VerticalAlign.CENTER))) {
      return Gravity.CENTER_VERTICAL | Gravity.LEFT;
    }

    if ((hal == HorizontalAlign.CENTER) && (val == VerticalAlign.CENTER)) {
      return Gravity.CENTER;
    }

    int sh = getGravity(hal);
    int sv = getGravity(val);

    if ((sh == Gravity.CENTER_HORIZONTAL) && (sv == Gravity.CENTER_VERTICAL)) {
      return Gravity.CENTER;
    }

    return sh | sv;
  }

  public static Drawable[] getIconDrawables(IconPosition position, iPlatformIcon icon, Drawable d) {
    drawables[0] = null;    // left
    drawables[1] = null;    // top
    drawables[2] = null;    // right
    drawables[3] = null;    // bottom

    if ((icon == null) && (d == null)) {
      return drawables;
    }

    if (d == null) {
      d = icon.getDrawable(null);
    }

    switch(position) {
      case RIGHT :
      case TRAILING :
      case TOP_RIGHT :
      case RIGHT_JUSTIFIED :
      case BOTTOM_RIGHT :
        drawables[2] = d;

        return drawables;

      case TOP_CENTER :
        drawables[1] = d;

        return drawables;

      case BOTTOM_CENTER :
        drawables[3] = d;

        return drawables;

      default :
        drawables[0] = d;

        return drawables;
    }
  }

  public static View getResourceComponentView(int id) {
    Activity a = AppContext.getRootActivity();

    return LayoutInflater.from(a).inflate(id, null);
  }

  public static View getResourceComponentView(String name) {
    Activity  a = AppContext.getRootActivity();
    int       id;
    Resources res = a.getResources();

    if (name.contains(":")) {
      id = res.getIdentifier(name, null, null);
    } else {
      id = res.getIdentifier(name, "layout", a.getPackageName());
    }

    if (id == 0) {
      return null;
    }

    return LayoutInflater.from(a).inflate(id, null);
  }

  public static View getView(String clazz) throws Exception {
    Class       cls = Platform.loadClass(clazz);
    Constructor con = cls.getConstructor(new Class[] { Context.class });

    return (View) con.newInstance(Platform.getAppContext().getActivity());
  }

  public static boolean isFocusPainted(iPlatformComponent list) {
    return false;
  }

  public static class DragHandler implements OnTouchListener {
    protected int        startX = 0;
    protected int        startY = 0;
    protected int        winX   = 0;
    protected int        winY   = 0;
    private WindowViewer window;

    public DragHandler() {}

    public void dispose() {
      window = null;
    }

    public boolean onTouch(View v, MotionEvent e) {
      int xCoord = Math.round(e.getRawX());
      int yCoord = Math.round(e.getRawY());

      switch(e.getAction()) {
        case MotionEvent.ACTION_MOVE :
          if (window != null) {
            window.moveTo(winX + (xCoord - startX), winY + (yCoord - startY));
          }

          break;

        case MotionEvent.ACTION_DOWN :
          startX = xCoord;
          startY = yCoord;
          setWindow(v);

          break;
      }

      return true;
    }

    void setWindow(View v) {
      iWidget w = Platform.findWidgetForComponent(v);

      if (w != null) {
        try {
          window = (WindowViewer) w.getViewer().getWindow().getViewer();
          winX   = window.getScreenX();
          winY   = window.getScreenY();
        } catch(Exception ignore) {}
      }
    }
  }


  public static class Navigator {
    public String getAppCodeName() {
      return iConstants.APPLICATION_NAME_STRING;
    }

    public String getAppName() {
      return iConstants.APPLICATION_NAME_STRING;
    }

    public String getAppVersion() {
      String version = iConstants.APPLICATION_VERSION_STRING;

      try {
        version += " (android; " + getLanguage() + ")";
      } catch(Exception ignore) {}

      return version;
    }

    public boolean getCookieEnabled() {
      return true;
    }

    public String getLanguage() {
      return Locale.getDefault().toString();
    }

    public String getPlatform() {
      return "android";
    }

    public String getUserAgent() {
      return Platform.getUserAgent();
    }
  }


  static class ScrollBarAdjustable extends aAdjustable {}
}
