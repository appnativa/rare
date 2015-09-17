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

import com.appnativa.rare.Platform;
import com.appnativa.rare.iConstants;
import com.appnativa.rare.platform.swing.ui.ScaleGestureDetector;
import com.appnativa.rare.platform.swing.ui.util.ImageUtils;
import com.appnativa.rare.platform.swing.ui.view.PopupWindow;
import com.appnativa.rare.scripting.iScriptHandler;
import com.appnativa.rare.spot.Widget;
import com.appnativa.rare.ui.Component;
import com.appnativa.rare.ui.ContainerPanel;
import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.ui.UIImage;
import com.appnativa.rare.ui.UIPopupMenu;
import com.appnativa.rare.ui.WidgetListener;
import com.appnativa.rare.ui.aWidgetListener;
import com.appnativa.rare.ui.event.KeyEvent;
import com.appnativa.rare.ui.event.MouseEvent;
import com.appnativa.rare.ui.iActionable;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.listener.aMouseAdapter;
import com.appnativa.rare.ui.painter.iPainterSupport;
import com.appnativa.rare.viewer.iContainer;
import com.appnativa.spot.SPOTSet;

import java.awt.Window;

import java.util.ArrayList;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;

/**
 *  Base class for all widgets
 *
 *  @author Don DeCoteau
 */
@java.lang.SuppressWarnings("unchecked")
public abstract class aPlatformWidget extends aWidget {
  private static MenuMouseListener menuMouseListener_;
  protected boolean                designMode;
  protected Object                 jsObject;
  private ScaleGestureDetector     scaleGestureDetector;

  /**
   *  Constructs a new instance
   *
   *  @param parent
   *           the widget's parent
   */
  protected aPlatformWidget(iContainer parent) {
    super(parent);
  }

  /**
   *  Performs Haptic Feedback on capable devices
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
    return ImageUtils.createImage(getContainerView());
  }

  /**
   * Focuses the next widget in the navigation sequence
   */
  @Override
  public void focusNextWidget() {
    if (isFocusOwner()) {
      getContainerView().transferFocus();
    }
  }

  /**
   * Focuses the previous widget in the navigation sequence
   */
  @Override
  public void focusPreviousWidget() {
    if (isFocusOwner()) {
      getContainerView().transferFocusBackward();
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
      Window w = SwingUtilities.windowForComponent(getContainerView());

      if (w instanceof PopupWindow) {
        ((PopupWindow) w).hidePopup();
      }
    }
  }

  @Override
  public boolean requestFocus() {
    getDataComponent().requestFocus();

    return isFocusOwner();
  }

  /**
   * Shows the popup menu for the widget
   */
  @Override
  public void showPopupMenu(int x, int y) {
    UIPopupMenu menu = getPopupMenu();

    if (menu != null) {
      menu.show(this.getDataComponent(), x, y);
    }
  }

  public void setDesignMode(boolean designMode) {
    this.designMode = designMode;
  }

  @Override
  public void setEventHandler(String event, Object code, boolean append) {
    super.setEventHandler(event, code, append);

    WidgetListener l = (WidgetListener) this.getWidgetListener();

    if (l != null) {
      if (l.isEnabled(iConstants.EVENT_SCALE)) {
        if (scaleGestureDetector == null) {
          getDataComponentEx().getView().addMouseWheelListener(new ScaleGestureDetector(this));
        }
      } else if (scaleGestureDetector != null) {
        getDataComponentEx().getView().removeMouseWheelListener(scaleGestureDetector);
      }
    }
  }

  @Override
  public void setParent(iContainer parent) {
    super.setParent(parent);

    if (!designMode && (parent != null) && parent.isDesignMode()) {
      setDesignMode(true);
    }
  }

  public Component getContainerComponentEx() {
    return (Component) getContainerComponent();
  }

  public JComponent getContainerView() {
    return getContainerComponentEx().getView();
  }

  public JComponent getDataView() {
    return getDataComponentEx().getView();
  }

  @Override
  public boolean getHasPainterSupport() {
    if (getDataComponent() instanceof iPainterSupport) {
      return true;
    }

    return getContainerComponent() instanceof iPainterSupport;
  }

  @Override
  public boolean isDesignMode() {
    return designMode;
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

    return SwingUtilities.isDescendingFrom(fc.getView(), cc.getView());
  }

  @Override
  public boolean isInPopup() {
    if (isDisposed()) {
      return false;
    }

    if (popupContainer != null) {
      return true;
    }

    Window w = SwingUtilities.windowForComponent(getContainerView());

    if (w instanceof PopupWindow) {
      return true;
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
    if (!isDesignMode()) {
      SPOTSet menu = cfg.getPopupMenu();

      if (menu != null) {
        popupMenu = new UIPopupMenu(this, menu);
      }

      if (textMenus) {
        showDefaultMenu = cfg.defaultContextMenu.booleanValue();
      }
    }
  }

  @Override
  protected aWidgetListener createWidgetListener(iWidget widget, Map map, iScriptHandler scriptHandler) {
    return new WidgetListener(widget, map, scriptHandler);
  }

  @Override
  protected void initializeListeners(aWidgetListener listener) {
    WidgetListener l = (WidgetListener) listener;

    initializePoupuMenuListener(l);

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
      getDataComponentEx().addKeyListener(l);
    }

    if (l.isMouseEventsEnabled()) {
      getDataComponentEx().addMouseListener(l);
    }

    if (l.isMouseMotionEventsEnabled()) {
      getDataComponentEx().addMouseMotionListener(l);
    }

    if (l.isEnabled(iConstants.EVENT_SCALE)) {
      getDataComponentEx().getView().addMouseWheelListener(scaleGestureDetector = new ScaleGestureDetector(this));
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

    if (c1.getView() instanceof iPainterSupport) {
      return c1;
    }

    return c2;
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
    public void mousePressed(MouseEvent e) {
      mouseReleased(e);
    }

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
            w.showPopupMenu((int) e.getX(), (int) e.getY());
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
