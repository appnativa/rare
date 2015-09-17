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

package com.appnativa.rare.viewer;

import com.appnativa.rare.Platform;
import com.appnativa.rare.iConstants;
import com.appnativa.rare.iPlatformAppContext;
import com.appnativa.rare.net.ActionLink;
import com.appnativa.rare.platform.PlatformHelper;
import com.appnativa.rare.platform.aAppContext;
import com.appnativa.rare.platform.iConfigurationListener;
import com.appnativa.rare.scripting.iScriptHandler;
import com.appnativa.rare.spot.Viewer;
import com.appnativa.rare.spot.Widget;
import com.appnativa.rare.ui.ColorUtils;
import com.appnativa.rare.ui.RenderType;
import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.ui.ScreenUtils;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIColorHelper;
import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.UIRectangle;
import com.appnativa.rare.ui.UIScreen;
import com.appnativa.rare.ui.Utils;
import com.appnativa.rare.ui.aWidgetListener;
import com.appnativa.rare.ui.border.UIBalloonBorder;
import com.appnativa.rare.ui.dnd.DropInformation;
import com.appnativa.rare.ui.effects.aAnimator;
import com.appnativa.rare.ui.effects.iAnimator;
import com.appnativa.rare.ui.effects.iPlatformAnimator;
import com.appnativa.rare.ui.event.DataEvent;
import com.appnativa.rare.ui.event.ExpansionEvent;
import com.appnativa.rare.ui.event.iExpandedListener;
import com.appnativa.rare.ui.event.iPopupMenuListener;
import com.appnativa.rare.ui.iActionComponent;
import com.appnativa.rare.ui.iCollapsible;
import com.appnativa.rare.ui.iCollapsible.iTitleProvider;
import com.appnativa.rare.ui.iParentComponent;
import com.appnativa.rare.ui.iPlatformBorder;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.iPlatformIcon;
import com.appnativa.rare.ui.iPopup;
import com.appnativa.rare.ui.iScrollerSupport;
import com.appnativa.rare.ui.iWindow;
import com.appnativa.rare.ui.iWindowManager;
import com.appnativa.rare.ui.print.iPageSetup;
import com.appnativa.rare.util.DataParser;
import com.appnativa.rare.widget.aPlatformWidget;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.util.Helper;
import com.appnativa.util.ObjectHolder;

import java.beans.PropertyChangeEvent;

import java.io.Writer;

import java.net.URL;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EventObject;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Base class for viewers
 *
 * @version 0.3, 2007-05-14
 * @author Don DeCoteau
 */
public abstract class aViewer extends aPlatformWidget implements iViewer {

  /** Listener for component and expansion events */
  protected static final ViewerListener viewerListener = new ViewerListener();

  /** the application context for the viewer */
  protected iPlatformAppContext appContext;

  /** the viewer's context URL */
  protected URL contextURL;

  /** whether the viewer is local */
  protected boolean local;

  /** the registered name of the viewer */
  protected String registeredName;

  /** the action link that created the viewer */
  protected ActionLink viewerActionLink;

  /** whether the viewer was reset */
  protected boolean wasReset;
  private boolean   autoUnregister = true;
  private boolean   disposable     = true;

  /** the disable behavior for the viewer */
  protected DisableBehavior disableBehavior = DisableBehavior.DISABLE_WIDGETS;
  private boolean           autoDispose     = true;
  private String            collapsedTitle;
  private boolean           loadFired;
  private boolean           localSet;
  private iTarget           myTarget;

  /** name map for holding registered named objects */
  private HashMap<String, Object> nameMap;
  private boolean                 registered;
  private RenderType              renderType;
  private boolean                 unloadFired;

  /**
   * Constructs a new instance
   *
   * @param parent
   *          the parent
   */
  public aViewer(iContainer parent) {
    super(parent);
    widgetType = WidgetType.Custom;
  }

  @Override
  public boolean canPrint() {
    return false;
  }

  @Override
  public boolean canSave() {
    return false;
  }

  /**
   * Scrolls down a unit in the viewer
   */
  @Override
  public void downArrow() {
    iScrollerSupport ss = getScrollerSupport();

    if (ss != null) {
      ss.moveUpDown(true, false);
    }
  }

  /**
   * Scrolls left a unit in the viewer
   */
  @Override
  public void leftArrow() {
    iScrollerSupport ss = getScrollerSupport();

    if (ss != null) {
      ss.moveLeftRight(false, false);
    }
  }

  /**
   * Scrolls down a page in the viewer
   */
  @Override
  public void pageDown() {
    iScrollerSupport ss = getScrollerSupport();

    if (ss != null) {
      ss.moveUpDown(true, true);
    }
  }

  @Override
  public void pageEnd() {
    iScrollerSupport ss = getScrollerSupport();

    if (ss != null) {
      ss.scrollToBottomEdge();
    }
  }

  @Override
  public void pageHome() {
    iScrollerSupport ss = getScrollerSupport();

    if (ss != null) {
      ss.scrollToTopEdge();
    }
  }

  @Override
  public void pageHomeHorizontal() {
    iScrollerSupport ss = getScrollerSupport();

    if (ss != null) {
      ss.scrollToLeftEdge();
    }
  }

  @Override
  public void pageEndHorizontal() {
    iScrollerSupport ss = getScrollerSupport();

    if (ss != null) {
      ss.scrollToRightEdge();
    }
  }

  @Override
  public void pageLeft() {
    iScrollerSupport ss = getScrollerSupport();

    if (ss != null) {
      ss.moveLeftRight(false, true);
    }
  }

  @Override
  public void pageRight() {
    iScrollerSupport ss = getScrollerSupport();

    if (ss != null) {
      ss.moveLeftRight(true, true);
    }
  }

  @Override
  public void pageSetup(iPageSetup ps) {}

  @Override
  public void pageUp() {
    iScrollerSupport ss = getScrollerSupport();

    if (ss != null) {
      ss.moveUpDown(false, true);
    }
  }

  @Override
  public void rightArrow() {
    iScrollerSupport ss = getScrollerSupport();

    if (ss != null) {
      ss.moveLeftRight(true, false);
    }
  }

  @Override
  public void upArrow() {
    iScrollerSupport ss = getScrollerSupport();

    if (ss != null) {
      ss.moveUpDown(false, false);
    }
  }

  @Override
  public abstract void configure(Viewer vcfg);

  @Override
  public void configure(Widget cfg) {
    if (widgetName == null) {    // set here so load scripts will have the name
      widgetName = cfg.name.getValue();

      if ((widgetName == null) || (widgetName.length() == 0)) {
        widgetName    = Utils.makeWidgetName(cfg, this);
        isSubmittable = false;
      }
    }

    configure((Viewer) cfg);
  }

  /**
   * Returns whether this viewer contains the specified named item
   *
   * @param name
   *          the name to check
   *
   * @return true if the viewer contains the named item; false otherwise
   */
  public boolean containsNamedItem(String name) {
    return (nameMap == null)
           ? false
           : nameMap.containsKey(name);
  }

  @Override
  public void dispose() {
    if (!isDisposable()) {
      return;
    }

    synchronized(widgetType) {
      if (!isDisposed()) {
        if (viewerActionLink != null) {
          viewerActionLink.clear();
        }

        try {
          aWidgetListener wl = getWidgetListener();

          if ((wl != null) && wl.isEnabled(iConstants.EVENT_DISPOSE)) {
            wl.evaluate(iConstants.EVENT_DISPOSE, new EventObject(this), true);
          }
        } catch(Throwable e) {
          Platform.ignoreException("calling onDispose exception", e);
        }

        try {
          unregister(true);
          clearConfiguration(true);
          sourceURL = null;

          if (nameMap != null) {
            nameMap.clear();
          }
        } catch(Throwable e) {
          Platform.ignoreException("dispose exception", e);
        } finally {
          disableBehavior  = null;
          nameMap          = null;
          contextURL       = null;
          registeredName   = null;
          viewerActionLink = null;
          renderType       = null;
          myTarget         = null;
          super.dispose();
        }
      }
    }
  }

  @Override
  public void onConfigurationChanged(boolean reset) {
    handleViewerConfigurationChanged(reset);
  }

  @Override
  public void onConfigurationWillChange(Object newConfig) {
    handleViewerConfigurationWillChange(newConfig);
  }

  @Override
  public void register() {
    registered = true;

    if ((local && localSet) || isDesignMode()) {
      return;
    }

    if (!localSet && local) {
      iContainer p = getParent();

      if (!(p instanceof WindowViewer)) {
        return;
      }
    }

    String s = getName();

    if (registeredName != null) {
      if (registeredName.equals(s)) {
        return;
      }

      getAppContext().getWindowManager().unRegisterViewer(registeredName);
    }

    registeredName = s;
    getAppContext().getWindowManager().registerViewer(registeredName, this);
  }

  @Override
  public Object registerNamedItem(String name, Object object) {
    if (name != null) {
      if (nameMap == null) {
        nameMap = new HashMap<String, Object>();
      }

      return nameMap.put(name, object);
    }

    return null;
  }

  @Override
  public void reload(boolean context) {
    wasReset = false;

    ActionLink link = getViewerActionLink();

    if (context || (link == null)) {
      if (widgetDataLink != null) {
        this.clearContents();
        handleActionLink(widgetDataLink, false);

        return;
      } else if (sourceURL != null) {
        setDataLink(new ActionLink(getParent(), sourceURL));

        return;
      }
    }

    if (link != null) {
      iTarget t = this.getTarget();

      if (t != null) {
        iContainer p = getParent();

        link.setTargetName(t.getName());
        link.setContext(p);
        viewerActionLink = null;
        Platform.invokeLater(link);
        t.removeViewer();
        dispose();
      }
    }
  }

  @Override
  public void reset() {
    if (getContainerComponent().isShowing()) {
      reload(false);
    } else {
      wasReset = true;
    }
  }

  @Override
  public void save(Writer w) {
    throw new UnsupportedOperationException("Save");
  }

  @Override
  public WindowViewer showAsDialog(Map winoptions) {
    return showAsWindowOrDialog(winoptions, true);
  }

  @Override
  public WindowViewer showAsDialog(String title, boolean modal) {
    HashMap winoptions = new HashMap(1);

    if (title != null) {
      winoptions.put("title", title);
    }

    winoptions.put("modal", modal);

    return showAsWindowOrDialog(winoptions, true);
  }

  @Override
  public iPopup showAsPopup(iPlatformComponent owner, Map options) {
    return showAsPopup(owner, -1, -1, null, options);
  }

  public iPopup showAsPopup(iPlatformComponent owner, float x, float y, iPlatformAnimator animator, Map options) {
    boolean trans   = true;
    float   left    = -1;
    float   top     = -1;
    float   width   = -1;
    float   height  = -1;
    float   swidth  = getAppContext().getWindowManager().getUsableScreenWidth();
    float   sheight = getAppContext().getWindowManager().getUsableScreenHeight();
    UIColor bg      = null;

    setParent(null);

    iPlatformComponent comp = getDataComponent();

    if (options != null) {
      Object o = options.get("transient");

      if (o instanceof Boolean) {
        trans = (Boolean) o;
      } else if (o != null) {
        trans = !"false".equals(o);
      }

      if (animator == null) {
        o = options.get("animator");

        if (o instanceof iPlatformAnimator) {
          animator = (iPlatformAnimator) o;
        } else if (o instanceof String) {
          animator = aAnimator.createAnimator(this, (String) o, null);
        }
      }

      o = options.get("bgColor");

      if (o != null) {
        if (o instanceof UIColor) {
          bg = (UIColor) o;
        } else if (o instanceof String) {
          bg = UIColorHelper.getBackgroundColor((String) o);
        }
      }

      o = options.get("left");

      if (o instanceof String) {
        left = ScreenUtils.toPlatformPixelWidth((String) o, comp, swidth);
      } else if (o instanceof Number) {
        left = ((Number) o).intValue();
      }

      o = options.get("top");

      if (o instanceof String) {
        top = ScreenUtils.toPlatformPixelHeight((String) o, comp, sheight);
      } else if (o instanceof Number) {
        top = ((Number) o).intValue();
      }

      o = options.get("height");

      if (o instanceof String) {
        height = ScreenUtils.toPlatformPixelHeight((String) o, comp, sheight);
      } else if (o instanceof Number) {
        height = ((Number) o).intValue();
      }

      o = options.get("width");

      if (o instanceof String) {
        width = ScreenUtils.toPlatformPixelWidth((String) o, comp, swidth);
      } else if (o instanceof Number) {
        width = ((Number) o).intValue();
      }
    }

    UIDimension d = getContainerComponent().getPreferredSize();

    if (swidth < d.width) {
      d.width = swidth;
    }

    if (sheight < d.height) {
      d.height = sheight;
    }

    if (width < 0) {
      width = d.width;
    }

    if (height < 0) {
      height = d.height;
    }

    d.width  = width;
    d.height = height;

    UIRectangle r = new UIRectangle();

    if (owner != null) {
      getProposedPopupBounds(owner, d, r, true);
    }

    iPopup popup = createPopup(UIScreen.snapToSize(r.width), UIScreen.snapToSize(r.height));

    popup.setAnimator(animator);
    popup.setViewer(this);

    if (top < 0) {
      y = top;
    }

    if (left < 0) {
      x = left;
    }

    if (bg != null) {
      popup.setBackgroundColor(bg);
    }

    popup.setTransient(trans);

    if (options != null) {
      addPopupMenuListener(popup, options);
    }

    if (owner != null) {
      if (x == -1) {
        x = UIScreen.snapToPosition(r.x);
      }

      if (y == -1) {
        y = UIScreen.snapToPosition(r.y);
      }

      popup.showPopup(owner, x, y);
    } else {
      if (x == -1) {
        x = Math.max((swidth - width) / 2, 0);
      }

      if (y == -1) {
        y = Math.max((sheight - height) / 2, 0);
      }

      popup.showPopup(getAppContext().getWindowManager().getComponent(), x, y);
    }

    return popup;
  }

  @Override
  public WindowViewer showAsWindow(Map winoptions) {
    boolean dialog = Platform.isTouchDevice();

    if (winoptions != null) {
      Object o = winoptions.get("windowtype");

      if (o instanceof String) {
        dialog = "dialog".equals(o);
      }
    }

    return showAsWindowOrDialog(winoptions, dialog);
  }

  /**
   * Shows the viewer as a window. A new window is created and the viewer
   * becomes the main component for the window
   *
   * @param title
   *          the title for the window
   *
   * @return the associated window viewer
   */
  public WindowViewer showAsWindow(String title) {
    HashMap winoptions = new HashMap(1);

    if (title != null) {
      winoptions.put("title", title);
    }

    return showAsWindowOrDialog(winoptions, Platform.isTouchDevice());
  }

  @Override
  public void targetAcquired(iTarget target) {
    if (target == null) {
      throw new NullPointerException();
    }

    if (target != myTarget) {
      iContainer cp = parentContainer;
      iContainer p  = null;
      iWidget    w  = Platform.findWidgetForComponent(target.getContainerComponent());

      if (w instanceof iContainer) {
        p = (iContainer) w;
      }

      if (p != cp) {
        if (p != null) {
          setParent(p);
        } else {
          p = cp;
        }
      }

      if (p != null) {
        p.getFormViewer().registerFormWidget(this);
      }

      myTarget = target;
      register();
      loaded();
    }
  }

  @Override
  public void targetLost(iTarget target) {
    myTarget = null;

    iContainer p = getParent();

    if (p == null) {
      iWidget w = Platform.findWidgetForComponent(target.getContainerComponent());

      if (w instanceof iContainer) {
        p = (iContainer) w;
      }
    }

    if (p != null) {
      p.getFormViewer().unregisterFormWidget(this);
    }

    try {
      unloaded();
    } finally {
      setParent(null);

      if (isAutoUnregister()) {
        unregister(false);
      }
    }
  }

  @Override
  public String toString() {
    String s = getValueAsString();

    if (s == null) {
      return widgetType + "::" + getName();
    }

    return s;
  }

  @Override
  public void unregister(boolean disposing) {
    registered = false;

    iWindowManager wm = (getAppContext() == null)
                        ? null
                        : getAppContext().getWindowManager();

    if (wm != null) {
      if (registeredName != null) {
        wm.unRegisterViewer(registeredName);
      }

      registeredName = null;
    }
  }

  @Override
  public Object unregisterNamedItem(String name) {
    if ((name != null) && (nameMap != null)) {
      return nameMap.remove(name);
    }

    return null;
  }

  @Override
  public void setAppContext(iPlatformAppContext context) {
    appContext = context;
  }

  @Override
  public void setAutoDispose(boolean autoDispose) {
    this.autoDispose = autoDispose;
  }

  @Override
  public void setAutoUnregister(boolean autoUnregister) {
    this.autoUnregister = autoUnregister;
  }

  @Override
  public void setCollapsedTitle(String title) {
    this.collapsedTitle = title;
  }

  /**
   * Sets the icon to use as the titlebar's icon when the viewer in in a
   * collapsible pane
   *
   * @param icon
   *          the icon
   */
  public void setCollapsiblePaneTitleIcon(iPlatformIcon icon) {
    getCollapsible().setTitleIcon(icon);
  }

  /**
   * Sets the text as the titlebar's icon when the viewer in in a collapsible
   * pane
   *
   * @param text
   *          the text
   */
  public void setCollapsiblePaneTitleText(String text) {
    getCollapsible().setTitleText(text);
  }

  @Override
  public void setContextURL(URL url) {
    contextURL = url;
  }

  /**
   * Gets the disable behavior for the viewer
   *
   * @param behavior
   *          the disable behavior for the viewer
   */
  public void setDisableBehavior(DisableBehavior behavior) {
    disableBehavior = behavior;
  }

  /**
   * Sets the paint use to designate the viewer as disabled
   *
   * @param color
   *          the color use to designate the viewer as disabled
   */
  @Override
  public void setDisabledColor(UIColor color) {
    getContainerComponent().setDisabledColor(color);
  }

  @Override
  public void setDisposable(boolean disposable) {
    this.disposable = disposable;
  }

  @Override
  public void setFocusable(boolean focusable) {
    super.setFocusable(focusable);
    getDataComponent().setFocusable(focusable);
    getContainerComponent().setFocusable(focusable);

    if (nameMap != null) {
      Iterator it = nameMap.values().iterator();
      Object   o;

      while(it.hasNext()) {
        o = it.next();

        if (o instanceof iWidget) {
          ((iWidget) o).setFocusable(focusable);
        }
      }
    }
  }

  /**
   * Sets the linked data of a named item
   *
   * @param name
   *          the name of the item
   * @param value
   *          the value for the data
   */
  public void setItemData(String name, Object value) {
    Object o = this.getNamedItem(name);

    if (o == null) {
      return;
    }

    if (o instanceof RenderableDataItem) {
      ((RenderableDataItem) o).setLinkedData(value);
    }
  }

  /**
   * Changes the enabled state of the named item
   *
   * @param name
   *          the name of the item to register
   * @param enabled
   *          true to enable the item; false otherwise
   */
  public void setItemEnabled(String name, boolean enabled) {
    Object o = this.getNamedItem(name);

    if (o == null) {
      return;
    }

    if (o instanceof RenderableDataItem) {
      ((RenderableDataItem) o).setEnabled(enabled);
    } else if (o instanceof iPlatformComponent) {
      ((iPlatformComponent) o).setEnabled(enabled);
    }
  }

  /**
   * Sets the icon of a named item
   *
   * @param name
   *          the name of the item
   * @param icon
   *          the icon for the item
   */
  public void setItemIcon(String name, iPlatformIcon icon) {
    Object o = this.getNamedItem(name);

    if (o == null) {
      return;
    }

    if (o instanceof RenderableDataItem) {
      ((RenderableDataItem) o).setIcon(icon);
    } else if (o instanceof iActionComponent) {
      ((iActionComponent) o).setIcon(icon);
    }
  }

  /**
   * Changes the selected state of the named item
   *
   * @param name
   *          the name of the item to register
   * @param selected
   *          true to select the item; false otherwise
   */
  public void setItemSelected(String name, boolean selected) {
    Object o = this.getNamedItem(name);

    if (o == null) {
      return;
    }

    if (o instanceof iWidget) {
      iWidget di = (iWidget) o;

      di.setSelected(selected);
    }
  }

  /**
   * Sets the tooltip of a named item
   *
   * @param name
   *          the name of the item
   * @param tooltip
   *          the tooltip for the item
   */
  public void setItemTooltip(String name, String tooltip) {}

  /**
   * Sets the value of a named item
   *
   * @param name
   *          the name of the item
   * @param value
   *          the value for the item
   */
  public void setItemValue(String name, Object value) {
    Object o = this.getNamedItem(name);

    if (o == null) {
      return;
    }

    if (o instanceof RenderableDataItem) {
      ((RenderableDataItem) o).setValue(value);
    } else if (value instanceof String) {
      if (o instanceof iActionComponent) {
        ((iActionComponent) o).setText((String) value);
      }
    }
  }

  /**
   * Changes the visible state of the named item
   *
   * @param name
   *          the name of the item
   * @param visible
   *          true to make the item visible; false otherwise
   */
  public void setItemVisible(String name, boolean visible) {
    Object o = this.getNamedItem(name);

    if (o == null) {
      return;
    }

    if (o instanceof RenderableDataItem) {
      ((RenderableDataItem) o).setVisible(visible);
    } else if (o instanceof iPlatformComponent) {
      ((iPlatformComponent) o).setVisible(visible);
    }
  }

  @Override
  public void setLinkedData(Object data) {
    super.setLinkedData(data);
  }

  @Override
  public void setLoadAnimator(iAnimator wa) {}

  @Override
  public void setName(String name) {
    widgetName = name;
  }

  @Override
  public void setRenderType(RenderType renderType) {
    this.renderType = renderType;
  }

  /**
   * Sets the source URL for the viewer
   *
   * @param url
   *          the source URL
   */
  public void setSourceURL(URL url) {
    sourceURL = url;
  }

  @Override
  public void setViewerActionLink(ActionLink link) {
    if ((link != null) &&!link.hasURL()) {
      link = null;
    }

    this.viewerActionLink = link;
  }

  /**
   * Get the item with the specified name
   *
   * @param name
   *          the name of the item to get
   *
   * @return the item with the specified name
   */
  public Object get(String name) {
    return getNamedItem(name);
  }

  @Override
  public iPlatformAppContext getAppContext() {
    if (appContext != null) {
      return appContext;
    }

    final iContainer p = getParent();

    return (p == null)
           ? Platform.getAppContext()
           : p.getAppContext();
  }

  /**
   * Get the base URL for the viewer. This is the source URL or context URL if
   * there is no source URL. If the viewer does not have a source or context URL
   * then the viewer chain is walked until a valid base URL is found
   *
   * @return the base URL
   */
  @Override
  public URL getBaseURL() {
    if ((contextURL != null) && Utils.isValidBaseURL(contextURL)) {
      return contextURL;
    }

    if ((sourceURL != null) && Utils.isValidBaseURL(sourceURL)) {
      return sourceURL;
    }

    iContainer p = getParent();

    if (p != null) {
      return p.getBaseURL();
    }

    return getAppContext().getContextURL();
  }

  @Override
  public String getCollapsedTitle() {
    return (collapsedTitle == null)
           ? null
           : expandString(collapsedTitle, false);
  }

  @Override
  public iCollapsible getCollapsiblePane() {
    iTarget          t = this.getTarget();
    iParentComponent c = (t == null)
                         ? null
                         : t.getContainerComponent();

    if (c instanceof iCollapsible) {
      return (iCollapsible) c;
    }

    if ((c != null) && (c.getComponentCount() == 1) && (c.getComponentAt(0) instanceof iCollapsible)) {
      return (iCollapsible) c.getComponentAt(0);
    }

    c = (c == null)
        ? null
        : c.getParent();

    if (c instanceof iCollapsible) {
      return (iCollapsible) c;
    }

    return null;
  }

  @Override
  public iContainer getContainerViewer() {
    return getParent();
  }

  @Override
  public URL getContextURL() {
    if (contextURL != null) {
      return contextURL;
    }

    iContainer parent = getParent();

    return (parent == null)
           ? null
           : parent.getContextURL();
  }

  @Override
  public iPlatformComponent getDataComponent() {
    if (dataComponent != null) {
      return dataComponent;
    }

    return getContainerComponent();
  }

  /**
   * Gets the disable behavior for the viewer
   *
   * @return the disable behavior for the viewer
   */
  public DisableBehavior getDisableBehavior() {
    return disableBehavior;
  }

  @Override
  public iExpandedListener getExpandedListener() {
    return viewerListener;
  }

  /**
   * Gets the link data for a named item within the viewer
   *
   * @param name
   *          the named item
   *
   * @return the item's linked data
   */
  public Object getItemData(String name) {
    Object o = getNamedItemEx(name);

    if (o instanceof RenderableDataItem) {
      RenderableDataItem di = (RenderableDataItem) o;

      return di.getLinkedData();
    }

    return null;
  }

  /**
   * Gets the link data for a named item within the viewer
   *
   * @param name
   *          the named item
   *
   * @return a string representation of the item's linked data
   */
  public String getItemDataString(String name) {
    Object o = getNamedItemEx(name);

    if (o instanceof RenderableDataItem) {
      RenderableDataItem di = (RenderableDataItem) o;

      o = di.getLinkedData();

      return (o == null)
             ? null
             : o.toString();
    }

    return null;
  }

  /**
   * Gets the value or a named item within the viewer
   *
   * @param name
   *          the named item
   *
   * @return the item's value
   */
  public Object getItemValue(String name) {
    Object o = getNamedItemEx(name);

    if (o instanceof RenderableDataItem) {
      RenderableDataItem di = (RenderableDataItem) o;

      return di.getValue();
    } else if (o instanceof iActionComponent) {
      return ((iActionComponent) o).getText();
    }

    return null;
  }

  @Override
  public Object getNamedItem(String name) {
    if (nameMap == null) {
      return null;
    }

    return nameMap.get(name);
  }

  /**
   * Gets a named item within the viewer. Throws an exception if an item with
   * that name does not exist
   *
   * @param name
   *          the name of the item
   *
   * @return the item with the corresponding name
   *
   * @throws NoSuchElementException
   *           if an item with that name does not exist
   */
  public Object getNamedItemEx(String name) throws NoSuchElementException {
    Object o = getNamedItem(name);

    if (o == null) {
      throw new NoSuchElementException(
          PlatformHelper.format(getAppContext().getResourceAsString("Rare.runtime.text.noSuchItem"), name));
    }

    return o;
  }

  @Override
  public List<String> getNames() {
    if (nameMap == null) {
      return Collections.EMPTY_LIST;
    }

    ArrayList<String> list = new ArrayList<String>();

    list.addAll(nameMap.keySet());

    return list;
  }

  /**
   * Get the parent target for the viewer This is the target that contains this
   * viewers target.
   *
   * @return the target
   */
  public iTarget getParentTarget() {
    iContainer p = getParent();

    return (p == null)
           ? null
           : p.getTarget();
  }

  @Override
  public iPlatformComponent getPrintComponent() {
    return getDataComponent();
  }

  @Override
  public RenderType getRenderType() {
    return renderType;
  }

  /**
   * Returns the named widget that was added to the scroll pane for the viewer
   *
   * @param name
   *          the name of the widget
   *
   * @return the widget
   */
  public iWidget getScrollPaneWidget(String name) {
    return getContainerViewer().getWidget(name);
  }

  @Override
  public Object getSelection() {
    return theValue;
  }

  @Override
  public URL getSourceURL() {
    return sourceURL;
  }

  @Override
  public iTarget getTarget() {
    return myTarget;
  }

  @Override
  public String getTitle() {
    String s = (widgetTitle == null)
               ? null
               : expandString(widgetTitle, false);

    if (s == null) {
      return "";
    }

    return s;
  }

  @Override
  public iViewer getViewer() {
    return this;
  }

  @Override
  public ActionLink getViewerActionLink() {
    return viewerActionLink;
  }

  @Override
  public URL getViewerURL() {
    return contextURL;
  }

  @Override
  public boolean hasSelection() {
    return getSelection() != null;
  }

  @Override
  public boolean isAutoDispose() {
    return autoDispose;
  }

  @Override
  public boolean isAutoUnregister() {
    return autoUnregister;
  }

  @Override
  public boolean isBackPressedHandled() {
    aWidgetListener l = getWidgetListener();

    if ((l != null) && l.isEnabled(iConstants.EVENT_BACK_PRESSED)) {
      DataEvent e = new DataEvent(this, null);

      l.evaluate(iConstants.EVENT_BACK_PRESSED, e, true);

      return e.isConsumed();
    }

    return false;
  }

  @Override
  public boolean isDisposable() {
    return disposable;
  }

  @Override
  public boolean isExternalViewer() {
    return false;
  }

  /**
   * Returns whether the named item is enabled
   *
   * @param name
   *          the name of the item
   *
   * @return true if it is enabled; false otherwise
   */
  public boolean isItemEnabled(String name) {
    Object o = this.getNamedItem(name);

    if (o == null) {
      return false;
    }

    if (o instanceof RenderableDataItem) {
      RenderableDataItem di = (RenderableDataItem) o;

      return di.isEnabled();
    } else if (o instanceof iPlatformComponent) {
      iPlatformComponent di = (iPlatformComponent) o;

      return di.isEnabled();
    }

    return false;
  }

  /**
   * Returns whether the named item is selected
   *
   * @param name
   *          the name of the item
   *
   * @return true if it is selected; false otherwise
   */
  public boolean isItemSelected(String name) {
    Object o = this.getNamedItem(name);

    if (o == null) {
      return false;
    }

    if (o instanceof iWidget) {
      iWidget di = (iWidget) o;

      return di.isSelected();
    }

    return false;
  }

  /**
   * Returns whether the named item is visible
   *
   * @param name
   *          the name of the item
   *
   * @return true if it is visible; false otherwise
   */
  public boolean isItemVisible(String name) {
    Object o = this.getNamedItem(name);

    if (o == null) {
      return false;
    }

    if (o instanceof RenderableDataItem) {
      return ((RenderableDataItem) o).isVisible();
    } else if (o instanceof iPlatformComponent) {
      return ((iPlatformComponent) o).isVisible();
    }

    return false;
  }

  @Override
  public boolean isRegistered() {
    return registered;
  }

  @Override
  public boolean isTabPaneViewer() {
    return false;
  }

  @Override
  public boolean isViewer() {
    return true;
  }

  @Override
  public boolean isWindowOnlyViewer() {
    return false;
  }

  protected void addPopupMenuListener(final iPopup popup, Map options) {
    Object o = options.get("onWillExpand");

    if (o == null) {
      o = options.get("onwillexpand");
    }

    final Object expand = o;

    o = options.get("onWillCollapse");

    if (o == null) {
      o = options.get("onwillcollapse");
    }

    final Object       collapse = o;
    iPopupMenuListener pml      = new iPopupMenuListener() {
      @Override
      public void popupMenuWillBecomeVisible(ExpansionEvent e) {
        if (expand != null) {
          getAppContext().getWindowViewer().eval(expand);
        }
      }
      @Override
      public void popupMenuWillBecomeInvisible(ExpansionEvent e) {
        if (collapse != null) {
          getAppContext().getWindowViewer().eval(collapse);
        }

        popup.dispose();
      }
      @Override
      public void popupMenuCanceled(ExpansionEvent e) {}
    };

    popup.addPopupMenuListener(pml);
  }

  /**
   * Clears the viewers configuration an removes all widgets
   *
   * @param dispose
   *          true to dispose of the child viewers; false otherwise
   */
  protected void clearConfiguration(boolean dispose) {
    clearNameMap();
    clearContextData();
    showDefaultMenu = false;

    if (dispose) {
      viewerActionLink = null;
      displayIcon      = null;
    }
  }

  /**
   * Clears the name map containing all registered named items
   */
  protected void clearNameMap() {
    if (nameMap != null) {
      nameMap.clear();
    }
  }

  /**
   * Configures generic viewer options
   *
   * @param cfg
   *          the configuration
   * @param border
   *          true to configure the border; false otherwise
   * @param textMenus
   *          true to configure text menus; false to configure menus only if the
   *          defaultContextMenu property has been explicitly set
   * @param margin
   *          true to configure the margin; false otherwise
   */
  protected void configureEx(Viewer cfg, boolean border, boolean textMenus, boolean margin) {
    ActionLink          link = null;
    iPlatformAppContext app  = getAppContext();
    String              s    = cfg.contextURL.getValue();

    if ((s != null) && (s.length() > 0)) {
      try {
        setContextURL(getAppContext().getURL(s));
      } catch(Exception e) {
        handleException(e);
      }
    }

    setRenderType(Utils.getRenderType(cfg.horizontalAlign.intValue(), cfg.verticalAlign.intValue()));

    if (!textMenus && cfg.defaultContextMenu.spot_valueWasSet()) {
      textMenus = true;
    }

    if (!isDesignMode() && cfg.scriptURL.spot_valueWasSet()) {
      try {
        ObjectHolder h = null;

        link = ActionLink.getActionLink(this, cfg.scriptURL, 0);

        if (cfg.scriptURL.spot_getLinkedData() instanceof ObjectHolder) {
          h = (ObjectHolder) cfg.scriptURL.spot_getLinkedData();
          cfg.scriptURL.spot_setLinkedData(null);
        } else {
          h = DataParser.loadScriptURL(this, cfg, link.getURL(this));
        }

        if (h.value instanceof Exception) {
          handleException((Exception) h.value);
        } else if (h.value != null) {    // will be null for a runOnce script that
          // was already ran
          String type = (String) h.type;
          String code = (String) h.value;
          String location;
          URL    url;

          if (code != null) {
            String name = cfg.name.getValue();

            if (link.isInlineURL()) {
              location = ((name == null)
                          ? Utils.makeWidgetName(cfg, this)
                          : name);
              url      = this.getContextURL();

              if (url != null) {
                location = "[" + ((aAppContext) getAppContext()).getApplicationRelativeLocation(url) + "]" + location;
              }
            } else {
              url      = link.getURL(this);
              location = ((aAppContext) getAppContext()).getApplicationRelativeLocation(url);
            }

            iScriptHandler sh     = getScriptHandler();
            boolean        shared = true;

            if ("false".equalsIgnoreCase(cfg.scriptURL.spot_getAttribute("shared"))) {
              shared = false;
            }

            sh.setScriptingContext(this, type, location, code, shared);
          }
        }
      } catch(Exception e) {
        handleException(e);
      }
    } else {
      String type = cfg.spot_getAttribute("language");

      if ((type != null) && (type.length() > 0) &&!type.equals(app.getDefaultScrptingLanguage())) {
        iScriptHandler sh = getScriptHandler();

        sh.setScriptingContext(this, type, Utils.makeWidgetName(cfg, this), null, false);
      }
    }

    this.fireConfigureEvent(cfg, iConstants.EVENT_CREATED);

    if (isDesignMode()) {
      disableBehavior = DisableBehavior.DISABLE_BOTH;

      UIColor c = Platform.getUIDefaults().getColor("Rare.disabledBackgroundColor");

      if (c == null) {
        c = ColorUtils.DISABLED_TRANSPARENT_COLOR;
      }

      setDisabledColor(c);
    } else {
      disableBehavior = DisableBehavior.valueOf(cfg.disableBehavior.stringValue().toUpperCase(Locale.US));
      s               = cfg.disableBehavior.spot_getAttribute("disableOverlayColor");

      if (s != null) {
        UIColor c = UIColorHelper.getBackgroundColor(s);

        if (c != null) {
          setDisabledColor(c);
        }
      }
    }

    unloadFired    = false;
    loadFired      = false;
    collapsedTitle = cfg.collapsedTitle.getValue();

    if (cfg.local.spot_valueWasSet()) {
      local    = cfg.local.booleanValue();
      localSet = true;
    } else {
      local = getAppContext().areViewersLocalByDefault();
    }

    if (cfg.icon.spot_hasValue()) {
      displayIcon = getIcon(cfg.icon);
    }

    s = cfg.name.getValue();

    if ((s != null) && (s.length() == 0)) {
      s = null;
    }

    if (s == null) {
      s = cfg.getClass().getName();

      int n = s.lastIndexOf('.');

      if (n != -1) {
        s = s.substring(n + 1);
      }

      s = "a" + s + "_" + Integer.toHexString((int)hashCode());
    }

    iViewer v = isDesignMode()
                ? null
                : getAppContext().getViewer(s);

    if ((v != null) && (v != this)) {
      if (getAppContext().isDebugEnabled()) {
        Platform.ignoreException(
            Helper.expandString(getAppContext().getResourceAsString("Rare.runtime.text.viewerDisposeExisting"), s),
            null);
      }

      if (v.getTarget() != null) {
        v.getTarget().removeViewer();
      }

      v.dispose();
    }

    widgetName = s;
    super.configure(cfg, border, textMenus, margin, false);
  }

  /**
   * Creates the listener for events that the viewer need to track internally
   *
   * @return the listener
   */
  protected ViewerListener createViewerListener() {
    return new ViewerListener();
  }

  protected void fireLoadEvent(boolean loaded) {
    aWidgetListener l = getWidgetListener();

    if (l == null) {
      return;
    }

    final String event = loaded
                         ? iConstants.EVENT_LOAD
                         : iConstants.EVENT_UNLOAD;

    if (!l.isEnabled(event)) {
      return;
    }

    EventObject e = new EventObject(this);

    try {
      if (loaded) {
        getWidgetListener().loadEvent(e);
      } else {
        getWidgetListener().unloadEvent(e);
      }
    } catch(Exception ex) {
      getAppContext().getDefaultExceptionHandler().handleScriptException(ex);
    }
  }

  /**
   * Generates a target name
   *
   * @return a unique target name
   */
  protected String generateTargetName() {
    return "_target." + Integer.toHexString(System.identityHashCode(this));
  }

  /**
   * Generates a target name
   *
   * @param prefix
   *          the target name prefix
   *
   * @return a unique target name
   */
  protected String generateTargetName(String prefix) {
    return "_target." + prefix + Integer.toHexString(System.identityHashCode(this));
  }

  /**
   * Generates a viewer name
   *
   * @return a unique viewer name
   */
  protected String generateViewerName() {
    return "_viewer." + Integer.toHexString(System.identityHashCode(this));
  }

  /**
   * Generates a viewer name
   *
   * @param prefix
   *          the target name prefix
   *
   * @return a unique viewer name
   */
  protected String generateViewerName(String prefix) {
    return "_viewer." + prefix + Integer.toHexString(System.identityHashCode(this));
  }

  protected void handleItemHasCollapsed(ExpansionEvent event) {
    iPlatformComponent c = (iPlatformComponent) event.getSource();

    if ((collapsedTitle != null) && (c instanceof iCollapsible)) {
      iCollapsible pane = (iCollapsible) c;

      pane.setTitleText(expandString(collapsedTitle, false));
    }
  }

  /**
   * Handles item has expanded event
   *
   * @param event
   *          the event
   */
  protected void handleItemHasExpanded(ExpansionEvent event) {
    iPlatformComponent c = (iPlatformComponent) event.getSource();

    if (c instanceof iCollapsible) {
      iCollapsible pane = (iCollapsible) c;
      CharSequence s    = pane.getTitle();

      if ((s == null) || (s.length() == 0) || (collapsedTitle != null)) {
        pane.setTitleText(expandString(widgetTitle, false));
      }
    }
  }

  protected void handleViewerConfigurationChanged(boolean reset) {
    if ((getWidgetListener() != null) && getWidgetListener().isPropertyChangeEventEnabled()) {
      getWidgetListener().propertyChange(new PropertyChangeEvent(this,
              iConfigurationListener.CONFIGURATION_CHANGED_PROPERTY, null, null));
    } else if (reset) {
      reset();
    }
  }

  protected void handleViewerConfigurationWillChange(Object newConfig) {
    if ((getWidgetListener() != null) && getWidgetListener().isPropertyChangeEventEnabled()) {
      getWidgetListener().propertyChange(new PropertyChangeEvent(this,
              iConfigurationListener.CONFIGURATION_WILLCHANGE_PROPERTY, PlatformHelper.getDeviceConfiguration(),
              newConfig));
    }
  }

  /**
   * Handles the importing of a URL list of URLS that was dropped on the
   * component. The default is to call the setDataURL method with the first URL
   * in the list
   *
   * @param urls
   *          the urls to import
   * @param drop
   *          the drop information
   *
   * @return true if data was imported; false otherwise
   */
  @Override
  protected boolean importURL(List<URL> urls, DropInformation drop) {
    if ((urls != null) && (urls.size() > 0)) {
      setDataURL(urls.get(0));

      return true;
    }

    return false;
  }

  /**
   * Handles the firing of onLoad events if necessary
   */
  protected void loaded() {
    if (!loadFired) {
      unloadFired = false;
      loadFired   = true;
      fireLoadEvent(true);
    }
  }

  protected WindowViewer showAsWindowOrDialog(Map winoptions, boolean dialog) {
    iWindowManager wm = getAppContext().getWindowManager();
    iWindow        win;

    if (winoptions == null) {
      winoptions = new HashMap(1);
      winoptions.put("modal", dialog);
    }

    if (dialog) {
      win = wm.createDialog(this, winoptions);
    } else {
      win = wm.createWindow(this, winoptions);
    }

    iContainer winviewer = win.getViewer();

    winviewer.setContextURL(contextURL);
    setParent(winviewer);
    wm.setViewerEx(win.getTargetName(), this.getContainerViewer(), this);

    Object show = (winoptions == null)
                  ? null
                  : winoptions.get("visible");

    if (show instanceof Boolean) {
      if (Boolean.TRUE.equals(show)) {
        win.showWindow();
      }
    } else if (show instanceof String) {
      if ("true".equals(show)) {
        win.showWindow();
      }
    } else {
      win.showWindow();
    }

    return (WindowViewer) winviewer;
  }

  /**
   * Handles the firing of onUnload events if necessary
   */
  protected void unloaded() {
    if (!unloadFired) {
      unloadFired = true;
      loadFired   = false;
      fireLoadEvent(false);
    }
  }

  @Override
  protected Object getAttributeEx(char firstChar, String key) {
    Object o;
    int    n;

    if (firstChar == '/') {
      n = key.indexOf('/', 1);

      iViewer v;

      if (n == -1) {
        n = key.lastIndexOf('?');

        if (n == -1) {
          v = getAppContext().getViewer(key.substring(1));

          return (v == null)
                 ? null
                 : v.getValueAsString();
        } else {
          v = getAppContext().getViewer(key.substring(1, n));

          return (v == null)
                 ? null
                 : v.getAttribute(key.substring(n + 1));
        }
      }

      v = getAppContext().getViewer(key.substring(1, n));

      if (v != null) {
        return v.getAttribute(key.substring(n + 1));
      }

      return null;
    }

    n = key.indexOf('/');

    if (n != -1) {
      int p = key.lastIndexOf('/');

      if (p == n) {
        iWidget w = isContainer()
                    ? ((iContainer) this).getWidget(key.substring(0, n))
                    : null;

        if (w != null) {
          return w.getAttribute(key.substring(n + 1));
        }
      }
    }

    String prop = null;

    n = key.lastIndexOf('?');

    if (n != -1) {
      prop = key.substring(n + 1);
      key  = key.substring(0, n);
    }

    iWidget w = isContainer()
                ? ((iContainer) this).getWidget(key)
                : null;

    if (w != null) {
      o = (prop == null)
          ? w.getHTTPFormValue()
          : w.getAttribute(prop);
    } else {
      o = super.getAttributeEx(firstChar, key);
    }

    return o;
  }

  /**
   * Returns whether this viewer is a container
   *
   * @return true if the viewer is a container; false otherwise
   */
  protected boolean isContainer() {
    return this instanceof iContainer;
  }

  /**
   * Creates a popup with the specified dimensions
   *
   * @param width
   *          the width
   * @param height
   *          the height
   * @return the new popup
   */
  protected abstract iPopup createPopup(int width, int height);

  /**
   * Gets the proposed popup bounds for this viewer given the specified owner
   *
   * @param owner
   *          the owner for the popup
   * @param contentSize the size of the content (can be null)
   * @param outBounds
   *          the rectangle to populate the bounds with
   * @param adjustBallonBorder
   *          true to adjust the widgets balloon border (if it has one) based on
   *          the proposed location
   */
  protected void getProposedPopupBounds(iPlatformComponent owner, UIDimension contentSize, UIRectangle outBounds,
          boolean adjustBallonBorder) {
    iPlatformBorder b = adjustBallonBorder
                        ? getBorder()
                        : null;

    if (contentSize == null) {
      contentSize = formComponent.getPreferredSize();
    }

    UIBalloonBorder balloonBorder = (UIBalloonBorder) ((b instanceof UIBalloonBorder)
            ? b
            : null);

    Utils.getProposedPopupBounds(outBounds, owner, contentSize, 0, HorizontalAlign.AUTO, b, false);

    if ((balloonBorder != null) && balloonBorder.isAutoLocatePeak()) {
      int mc = balloonBorder.getModCount();

      balloonBorder.autoLocatePeakForProposedBounds(owner, outBounds);

      if (mc != balloonBorder.getModCount()) {
        setBorder(balloonBorder);
      }
    }
  }

  /**
   * Gets a handle to the iCollapsible
   *
   * @return a handle to the iCollapsible
   */
  private iCollapsible getCollapsible() {
    iCollapsible c = getCollapsiblePane();

    if (c == null) {
      throw new UnsupportedOperationException("No collapsible");
    }

    return c;
  }

  protected iScrollerSupport getScrollerSupport() {
    if (dataComponent instanceof iScrollerSupport) {
      return (iScrollerSupport) dataComponent;
    }

    if (formComponent instanceof iScrollerSupport) {
      return (iScrollerSupport) formComponent;
    }

    return null;
  }

  /**
   * Listener for events that the viewer need to track internally
   *
   * @version 0.3, 2007-05-16
   * @author Don DeCoteau
   */
  protected static class ViewerListener implements iExpandedListener, iTitleProvider {
    @Override
    public void itemHasCollapsed(ExpansionEvent event) {
      aPlatformViewer v = getViewer(event.getItem());

      if (v != null) {
        v.handleItemHasCollapsed(event);
      }
    }

    @Override
    public void itemHasExpanded(ExpansionEvent event) {
      aPlatformViewer v = getViewer(event.getItem());

      if (v != null) {
        v.handleItemHasExpanded(event);
      }
    }

    @Override
    public String getCollapsedTitle(iCollapsible pane, iPlatformComponent c) {
      aPlatformViewer v = getViewer(c);

      if (v != null) {
        String s = v.getCollapsedTitle();

        if (s == null) {
          s = v.getTitle();
        }

        return ((s != null) && (s.length() == 0))
               ? null
               : s;
      }

      return null;
    }

    @Override
    public String getExpandedTitle(iCollapsible pane, iPlatformComponent c) {
      aPlatformViewer v = getViewer(c);

      if (v != null) {
        String s = v.getTitle();

        return ((s != null) && (s.length() == 0))
               ? null
               : s;
      }

      return null;
    }

    private aPlatformViewer getViewer(Object o) {
      if (o instanceof iPlatformComponent) {
        iWidget w = ((iPlatformComponent) o).getWidget();

        return (aPlatformViewer) ((w == null)
                                  ? null
                                  : w.getViewer());
      }

      return null;
    }
  }
}
