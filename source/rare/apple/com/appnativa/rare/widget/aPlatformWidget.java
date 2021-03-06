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

import java.util.ArrayList;
import java.util.Map;

import com.appnativa.rare.Platform;
import com.appnativa.rare.iConstants;
import com.appnativa.rare.platform.apple.ui.util.ImageUtils;
import com.appnativa.rare.platform.apple.ui.view.PopupWindow;
import com.appnativa.rare.platform.apple.ui.view.View;
import com.appnativa.rare.scripting.iScriptHandler;
import com.appnativa.rare.spot.Widget;
import com.appnativa.rare.ui.Component;
import com.appnativa.rare.ui.ContainerPanel;
import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.ui.UIImage;
import com.appnativa.rare.ui.UIPopupMenu;
import com.appnativa.rare.ui.WidgetListener;
import com.appnativa.rare.ui.aWidgetListener;
import com.appnativa.rare.ui.iActionable;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.event.KeyEvent;
import com.appnativa.rare.ui.event.MouseEvent;
import com.appnativa.rare.ui.listener.aMouseAdapter;
import com.appnativa.rare.ui.painter.iPainterSupport;
import com.appnativa.rare.viewer.iContainer;
import com.appnativa.spot.SPOTSet;

/**
 * Base class for all widgets
 *
 * @author Don DeCoteau
 */
@java.lang.SuppressWarnings("unchecked")
public abstract class aPlatformWidget extends aWidget {
  private static MenuMouseListener menuMouseListener_;
  protected boolean                designMode;

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
  @Override
  public void buzz() {}

  /**
   * Captures an image of the widget
   *
   * @return an image of the widget
   */
  @Override
  public UIImage capture() {
    return ImageUtils.createImage(getContainerComponent());
  }

  /**
   * Focuses the next widget in the navigation sequence
   */
  @Override
  public void focusNextWidget() {
    if (isFocusOwner()) {
      getDataView().focusNextView();
    }
  }

  /**
   * Focuses the previous widget in the navigation sequence
   */
  @Override
  public void focusPreviousWidget() {
    if (isFocusOwner()) {
      getDataView().focusPreviousView();
    }
  }

  @Override
  public void handleKeyboardActions(KeyEvent e) {
    getDataComponent().dispatchEvent(e);
  }

  @Override
  public void hidePopupContainer() {
    if (isDisposed()) {
      return;
    }

    if (popupContainer != null) {
      popupContainer.hidePopup();
    } else {
      View v  = getContainerView();
      View vp = v.getParent();

      while(vp != null) {
        if (vp instanceof PopupWindow) {
          ((PopupWindow) vp).hidePopup();

          return;
        }

        vp = vp.getParent();
      }
    }
  }

  @Override
  public boolean requestFocus() {
    return getDataView().requestFocus();
  }

  public Component getContainerComponentEx() {
    return (Component) getContainerComponent();
  }

  public View getContainerView() {
    return getContainerComponentEx().getView();
  }

  public View getDataView() {
    return getDataComponentEx().getView();
  }

  @Override
  public boolean getHasPainterSupport() {
    if (getDataComponent() instanceof iPainterSupport) {
      return true;
    }

    return getContainerComponent() instanceof iPainterSupport;
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

  @Override
  public boolean isFocusOwner() {
    iPlatformComponent fc = Platform.getAppContext().getFocusOwner();

    if (fc == null) {
      return false;
    }

    iPlatformComponent cc = getContainerComponent();

    if ((cc == fc) || (fc == getDataComponent())) {
      return true;
    }

    return fc.getView().isDescendantOf(cc.getView());
  }

  @Override
  public boolean isInPopup() {
    if (isDisposed()) {
      return false;
    }

    View v  = getContainerView();
    View vp = v.getParent();

    while(vp != null) {
      if (vp instanceof PopupWindow) {
        return true;
      }

      if (vp instanceof View) {
        vp = vp.getParent();
      } else {
        vp = null;
      }
    }

    return false;
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
  @Override
  protected void configureMenus(iPlatformComponent comp, Widget cfg, boolean textMenus) {
    SPOTSet menu = cfg.getPopupMenu();

    if (menu != null) {
      popupMenu = new UIPopupMenu(this, menu);
    }
  }

  @Override
  protected aWidgetListener createWidgetListener(iWidget widget, Map map, iScriptHandler scriptHandler) {
    return new WidgetListener(widget, map, scriptHandler);
  }

  @Override
  public void setEventHandler(String event, Object code, boolean append) {
    super.setEventHandler(event, code, append);

    String se = aWidgetListener.fromWebEventEx(event);

    if (se != null) {
      event = se;
    }

    WidgetListener l = (WidgetListener) getWidgetListener();

    if (Platform.isTouchableDevice()) {
      if (l.isEnabled(iConstants.EVENT_SCALE)) {
        getDataComponentEx().addScaleListener(l);
      }

      if (l.isEnabled(iConstants.EVENT_FLING) || l.isEnabled(iConstants.EVENT_SCROLL)) {
        getDataComponentEx().addFlingListener(l);
      }

      if (l.isEnabled(iConstants.EVENT_ROTATE)) {
        getDataComponentEx().addRotateListener(l);
      }
    }

    if (l.isMouseMotionEventsEnabled() || (l.isEnabled(iConstants.EVENT_SCALE) &&!Platform.isTouchDevice())) {
      getDataComponentEx().addMouseMotionListener(l);
    }
  }

  @Override
  protected void initializeListeners(aWidgetListener listener) {
    WidgetListener l = (WidgetListener) listener;

    initializePoupuMenuListener(l);

    if (this instanceof iActionable) {
      final boolean eventEnabled = (l != null) && l.isActionEventEnabled();

      if (eventEnabled) {
        ((iActionable) this).addActionListener(l);
      }
    }

    if (l == null) {
      return;
    }

    if (l.isKeyEventsEnabled()) {
      getDataComponentEx().addKeyListener(l);
    }

    if (Platform.isTouchableDevice()) {
      if (l.isEnabled(iConstants.EVENT_SCALE)) {
        getDataComponentEx().addScaleListener(l);
      }

      if (l.isEnabled(iConstants.EVENT_FLING) || l.isEnabled(iConstants.EVENT_SCROLL)) {
        getDataComponentEx().addFlingListener(l);
      }

      if (l.isEnabled(iConstants.EVENT_ROTATE)) {
        getDataComponentEx().addRotateListener(l);
      }
    }

    if (l.isMouseEventsEnabled()) {
      getDataComponentEx().addMouseListener(l);
    }

    if (l.isMouseMotionEventsEnabled() || (l.isEnabled(iConstants.EVENT_SCALE) &&!Platform.isTouchDevice())) {
      getDataComponentEx().addMouseMotionListener(l);
    }

    if (l.isEnabled(iConstants.EVENT_FOCUS) || l.isEnabled(iConstants.EVENT_BLUR)) {
      getDataComponentEx().addFocusListener(l);
    }

    if (l.isEnabled(iConstants.EVENT_RESIZE) || l.isEnabled(iConstants.EVENT_MOVED)
        || l.isEnabled(iConstants.EVENT_SHOWN) || l.isEnabled(iConstants.EVENT_HIDDEN)) {
      getContainerComponentEx().addViewListener(l);
    }
  }

  protected void initializePoupuMenuListener(WidgetListener l) {
    if (hasPopupMenu() || ((l != null) && l.isEnabled(iConstants.EVENT_CONTEXTMENU))) {
      if (menuMouseListener_ == null) {
        menuMouseListener_ = new MenuMouseListener();
      }

      getDataComponentEx().addMouseListener(menuMouseListener_);
    }
  }

  protected Component getDataComponentEx() {
    return (Component) getDataComponent();
  }

  @Override
  protected iPainterSupport getPainterSupport(boolean outer) {
    Component c1 = outer
                   ? getContainerComponentEx()
                   : getDataComponentEx();
    Component c2 = outer
                   ? getDataComponentEx()
                   : getContainerComponentEx();

    if (outer && (c1 instanceof ContainerPanel) && ((ContainerPanel) c1).isBorderPanel()) {
      c1 = c2;
    }

    if (c1 instanceof iPainterSupport) {
      return c1;
    }

    if (c2 instanceof iPainterSupport) {
      return c2;
    }

    return null;
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


  static class MenuMouseListener extends aMouseAdapter {
    @Override
    public void mouseReleased(MouseEvent e) {
      if (e.isPopupTrigger()) {
        aWidget w = (aWidget) e.getWidget();

        if (w != null) {
          WidgetListener l = (WidgetListener) w.getWidgetListener();

          if ((l != null) && l.isEnabled(iConstants.EVENT_CONTEXTMENU)) {
            l.evaluate(iConstants.EVENT_CONTEXTMENU, e, false);
          }

          if (!e.isConsumed() && w.hasPopupMenu()) {
            e.consume();
            w.showPopupMenu(0, 0);
          }
        }
      }
    }

    @Override
    public boolean wantsLongPress() {
      return true;
    }
  }
  
}
