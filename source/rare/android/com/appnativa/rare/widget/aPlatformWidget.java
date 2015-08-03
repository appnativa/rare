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

package com.appnativa.rare.widget;

import android.content.Context;

import android.graphics.drawable.Drawable;

import android.view.FocusFinder;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import android.widget.PopupWindow;

import com.appnativa.rare.Platform;
import com.appnativa.rare.iConstants;
import com.appnativa.rare.platform.android.AppContext;
import com.appnativa.rare.platform.android.ui.DrawableIcon;
import com.appnativa.rare.platform.android.ui.util.ImageUtils;
import com.appnativa.rare.scripting.iScriptHandler;
import com.appnativa.rare.spot.Widget;
import com.appnativa.rare.ui.Component;
import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.ui.UIImage;
import com.appnativa.rare.ui.UIPopupMenu;
import com.appnativa.rare.ui.WidgetListener;
import com.appnativa.rare.ui.aWidgetListener;
import com.appnativa.rare.ui.event.KeyEvent;
import com.appnativa.rare.ui.iActionable;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.iPlatformIcon;
import com.appnativa.rare.ui.painter.iPainterSupport;
import com.appnativa.rare.viewer.iContainer;
import com.appnativa.spot.SPOTSet;

import java.util.ArrayList;
import java.util.Map;

/**
 * Base class for all widgets
 *
 * @author Don DeCoteau
 */
@SuppressWarnings("unchecked")
public abstract class aPlatformWidget extends aWidget implements android.text.Html.ImageGetter {

  /**
   * Constructs a new instance
   *
   * @param parent
   *          the widget's parent
   */
  protected aPlatformWidget(iContainer parent) {
    super(parent);
  }

  /**
   * Performs Haptic Feedback on capable devices
   */
  public void buzz() {
    getDataView().performHapticFeedback(HapticFeedbackConstants.LONG_PRESS,
            HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING);
  }

  /**
   * Captures an image of the widget
   *
   * @return an image of the widget
   */
  public UIImage capture() {
    return ImageUtils.createImage(getContainerView());
  }

  /**
   * Focuses the next widget in the navigation sequence
   */
  public void focusNextWidget() {
    Component  c = this.getContainerComponentEx();
    iContainer p = getParent();

    if (p != null) {
      View v = FocusFinder.getInstance().findNextFocus((ViewGroup) p.getDataComponent().getView(), c.getView(),
                 View.FOCUS_RIGHT);

      if (v != null) {
        v.requestFocus();
      }
    }
  }

  /**
   * Focuses the previous widget in the navigation sequence
   */
  public void focusPreviousWidget() {
    Component  c = this.getContainerComponentEx();
    iContainer p = getParent();

    if (p != null) {
      View v = FocusFinder.getInstance().findNextFocus((ViewGroup) p.getDataComponent().getView(), c.getView(),
                 View.FOCUS_LEFT);

      if (v != null) {
        v.requestFocus();
      }
    }
  }

  public void handleKeyboardActions(KeyEvent e) {
    getDataComponent().dispatchEvent(e);
  }

  public void hidePopupContainer() {
    if (isDisposed()) {
      return;
    }

    if (popupContainer != null) {
      popupContainer.hidePopup();
    } else {
      View       v  = getContainerView();
      ViewParent vp = v.getParent();

      while(vp != null) {
        if (vp instanceof PopupWindow) {
          ((PopupWindow) vp).dismiss();

          return;
        }

        vp = vp.getParent();
      }
    }
  }

  public boolean requestFocus() {
    return getDataView().requestFocus();
  }

  /**
   * Shows the popup menu for the widget
   */
  public void showPopupMenu(int x, int y) {
    UIPopupMenu menu = getPopupMenu();

    if (menu != null) {
      menu.show(this, Platform.isTouchDevice());
    }
  }

//  public void setEventHandler(String event, Object code, boolean append) {
//    String se = aWidgetListener.fromWebEventEx(event);
//
//    if (se != null) {
//      event = se;
//    }
//
//    WidgetListener l = (WidgetListener) this.getWidgetListener();
//
//    if (l == null) {
//      l = (WidgetListener) createWidgetListener(this, new HashMap(), getScriptHandler());
//      setWidgetListener(l);
//    }
//
//    l.setEventHandler(event, code, append);
//    getDataComponentEx().removeMouseListenerEx(l);
//
//    if (l.isMouseEventsEnabled() || l.isMouseMotionEventsEnabled() || l.isEnabled(iConstants.EVENT_SCROLL)) {
//      getDataComponentEx().addMouseListenerEx(l);
//    }
//
//    getDataComponentEx().removeKeyListenerEx(l);
//
//    if (l.isKeyEventsEnabled()) {
//      getDataComponentEx().addKeyListenerEx(l);
//    }
//
//    getDataComponentEx().removeFocusListenerEx(l);
//
//    if (l.isEnabled(iConstants.EVENT_FOCUS) || l.isEnabled(iConstants.EVENT_BLUR)) {
//      getDataComponentEx().addFocusListenerEx(l);
//    }
//
//    getContainerComponentEx().removeViewListenerEx(l);
//
//    if (l.isEnabled(iConstants.EVENT_RESIZE) || l.isEnabled(iConstants.EVENT_MOVED)
//        || l.isEnabled(iConstants.EVENT_SHOWN) || l.isEnabled(iConstants.EVENT_HIDDEN)) {
//      getContainerComponentEx().addViewListener(l);
//    }
//
//    if (l.isEnabled(iConstants.EVENT_CHANGE) && (this instanceof iListHandler)) {
//      ((iListHandler) this).removeItemChangeListener(l);
//      ((iListHandler) this).addItemChangeListener(l);
//    }
//
//    if (l.isEnabled(iConstants.EVENT_ACTION) && (this instanceof iActionable)) {
//      ((iActionable) this).removeActionListener(l);
//      ((iActionable) this).addActionListener(l);
//    }
//
//    if ((this instanceof iTreeHandler) && (event.startsWith("onWill") || event.startsWith("onHas"))) {
//      ((iTreeHandler) this).removeExpandedListener(l);
//      ((iTreeHandler) this).removeExpansionListener(l);
//
//      if (l.isExpandedEventsEnabled()) {
//        ((iTreeHandler) this).addExpandedListener(l);
//      }
//
//      if (l.isExpansionEventsEnabled()) {
//        ((iTreeHandler) this).addExpansionListener(l);
//      }
//    }
//  }
  public Context getAndroidContext() {
    if (formComponent != null) {
      return getContainerView().getContext();
    }

    if (dataComponent != null) {
      return getDataView().getContext();
    }

    return AppContext.getRootActivity();
  }

  public Component getContainerComponentEx() {
    return (Component) getContainerComponent();
  }

  public View getContainerView() {
    return (View) getContainerComponentEx().getView();
  }

  public View getDataView() {
    return (View) getDataComponentEx().getView();
  }

  public Drawable getDrawable(String source) {
    iPlatformIcon icon = getIcon(source, source);

    return (icon == null)
           ? null
           : new DrawableIcon(icon);
  }

  public boolean getHasPainterSupport() {
    if (getDataView() instanceof iPainterSupport) {
      return true;
    }

    return getContainerView() instanceof iPainterSupport;
  }

  /**
   * Gets the widget that specified view belongs to
   *
   * @param view
   *          the view
   *
   * @return the widget that specified component belongs to
   */
  public static iWidget getWidgetForView(View view) {
    iPlatformComponent c = (view == null)
                           ? null
                           : Component.fromView(view);

    if (c != null) {
      return c.getWidget();
    }

    return null;
  }

  public boolean isFocusOwner() {
    Component fc = getContainerComponentEx();

    if (fc == null) {
      return false;
    }

    return fc.getView().isFocused();
  }

  public boolean isInPopup() {
    if (isDisposed()) {
      return false;
    }

    View       v  = getContainerView();
    ViewParent vp = v.getParent();

    while(vp != null) {
      if (vp instanceof PopupWindow) {
        return true;
      }

      if (vp instanceof View) {
        vp = ((View) vp).getParent();
      } else {
        vp = null;
      }
    }

    return false;
  }

  @Override
  protected void handleCustomProperties(Widget cfg, Map<String, Object> properties) {
    super.handleCustomProperties(cfg, properties);

    if ("false".equals(properties.get("android:hardwareAccelerated"))) {
      dataComponent.getView().setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }

    if ("true".equals(properties.get("android:focusable"))) {
      dataComponent.getView().setFocusableInTouchMode(true);
    }
  }

  /**
   * Configures the popup menu for a widget
   *
   * @param comp
   *          the component to attach the menu to
   * @param cfg
   *          the widget configuration
   * @param textMenus
   *          true to create text menus (cut/copy/paste/) for the widget; false
   *          otherwise
   */
  protected void configureMenus(iPlatformComponent comp, Widget cfg, boolean textMenus) {
    SPOTSet menu = cfg.getPopupMenu();

    if (menu != null) {
      popupMenu = new UIPopupMenu(this, menu);
      getAppContext().getActivity().registerForContextMenu(getDataView());
    }
  }

  protected aWidgetListener createWidgetListener(iWidget widget, Map map, iScriptHandler scriptHandler) {
    return new WidgetListener(widget, map, scriptHandler);
  }

  protected void initializeListeners(aWidgetListener listener) {
    WidgetListener l = (WidgetListener) listener;

    if (this instanceof iActionable) {
      final boolean eventEnabled = (l != null) && l.isActionEventEnabled();

      if (eventEnabled) {
        ((iActionable) this).addActionListener(l);
      } else {
        ((iActionable) this).addActionListener(this);
      }
    }

    if (l == null) {
      return;
    }

    if (l.isKeyEventsEnabled()) {
      getDataComponentEx().addKeyListenerEx(l);
    }

    if (l.isMouseEventsEnabled()) {
      getDataComponentEx().addMouseListenerEx(l);
    }

    if (l.isMouseMotionEventsEnabled() || l.isEnabled(iConstants.EVENT_SCALE)) {
      getDataComponentEx().addMouseMotionListenerEx(l);
    }

    if (l.isEnabled(iConstants.EVENT_FOCUS) || l.isEnabled(iConstants.EVENT_BLUR)) {
      getDataComponentEx().addFocusListenerEx(l);
    }

    if (l.isEnabled(iConstants.EVENT_RESIZE) || l.isEnabled(iConstants.EVENT_MOVED)
        || l.isEnabled(iConstants.EVENT_SHOWN) || l.isEnabled(iConstants.EVENT_HIDDEN)) {
      getContainerComponentEx().addViewListener(l);
    }

    if (l.isEnabled(iConstants.EVENT_CONTEXTMENU)) {
      getDataComponentEx().getView().setOnLongClickListener(l);
    }
  }

  protected Component getDataComponentEx() {
    return (Component) getDataComponent();
  }

  protected iPainterSupport getPainterSupport(boolean outer) {
    return outer
           ? getContainerComponentEx()
           : getDataComponentEx();
  }

  /**
   * This class provides a container for storing a list of expanded and or
   * selected items This is to store items that have been flagged as expanded or
   * selected during the parsing and loading or data items.
   *
   * It is the widget's responsibility to process these once data has been
   * loaded
   */
  public static class ItemAttributes {

    /** a holder for a single checked item */
    public RenderableDataItem check;

    /** a holder for a set of checked items */
    public ArrayList<RenderableDataItem> checked;

    /** a holder for a single expanded item */
    public RenderableDataItem expand;

    /** a holder for a set of expanded items */
    public ArrayList<RenderableDataItem> expanded;

    /** a holder for a single selected item */
    public RenderableDataItem select;

    /** a holder for a set of selected items */
    public ArrayList<RenderableDataItem> selected;

    /**
     * Adds an item that has a selected attribute set
     *
     * @param item
     *          the item
     */
    public void addChecked(RenderableDataItem item) {
      if (check != null) {
        if (checked == null) {
          checked = new ArrayList<RenderableDataItem>(5);
        }

        checked.add(item);
      } else {
        check = item;
      }
    }

    /**
     * Adds an item that has an expanded attribute set
     *
     * @param item
     *          the item
     */
    public void addExpanded(RenderableDataItem item) {
      if (expand != null) {
        if (expanded == null) {
          expanded = new ArrayList<RenderableDataItem>(5);
        }

        expanded.add(item);
      } else {
        expand = item;
      }
    }

    /**
     * Adds an item that has a selected attribute set
     *
     * @param item
     *          the item
     */
    public void addSelected(RenderableDataItem item) {
      if (select != null) {
        if (selected == null) {
          selected = new ArrayList<RenderableDataItem>(5);
        }

        selected.add(item);
      } else {
        select = item;
      }
    }
  }
}
