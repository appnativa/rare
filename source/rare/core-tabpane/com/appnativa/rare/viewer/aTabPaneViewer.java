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
import com.appnativa.rare.exception.ApplicationException;
import com.appnativa.rare.iConstants;
import com.appnativa.rare.iFunctionCallback;
import com.appnativa.rare.net.ActionLink;
import com.appnativa.rare.spot.Tab;
import com.appnativa.rare.spot.TabPane;
import com.appnativa.rare.spot.Viewer;
import com.appnativa.rare.spot.Widget;
import com.appnativa.rare.ui.ColorUtils;
import com.appnativa.rare.ui.Location;
import com.appnativa.rare.ui.RenderType;
import com.appnativa.rare.ui.ScreenUtils;
import com.appnativa.rare.ui.UIColorHelper;
import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.UIFont;
import com.appnativa.rare.ui.UIFontHelper;
import com.appnativa.rare.ui.UIInsets;
import com.appnativa.rare.ui.UIRectangle;
import com.appnativa.rare.ui.Utils;
import com.appnativa.rare.ui.aWidgetListener;
import com.appnativa.rare.ui.effects.aAnimator;
import com.appnativa.rare.ui.effects.aSizeAnimation;
import com.appnativa.rare.ui.effects.iTransitionAnimator;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.iPlatformIcon;
import com.appnativa.rare.ui.iTabDocument;
import com.appnativa.rare.ui.iTabPaneComponent;
import com.appnativa.rare.ui.iWindowManager;
import com.appnativa.rare.ui.listener.iTabPaneListener;
import com.appnativa.rare.ui.painter.PaintBucket;
import com.appnativa.rare.ui.tabpane.TabDocument;
import com.appnativa.rare.ui.tabpane.TabPaneComponent;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.spot.SPOTSet;
import com.appnativa.util.MutableInteger;
import com.appnativa.util.SNumber;

import java.net.URL;

import java.util.List;

/**
 * A viewer that arranges a section of the screen into a set of regions only one
 * of which can be selected at a given time. A region can be selected by
 * clicking on its tab.
 *
 * @author Don DeCoteau
 */
public abstract class aTabPaneViewer extends aContainer implements iTabPaneViewer {

  /** the initially selected tab */
  protected int initiallySelectedIndex;

  /** the currently selected tab document */
  protected iTabDocument selectedDocument;

  /** generic tab configuration options */
  protected TabOptions tabOptions;

  /** tabbed pane component */
  protected iTabPaneComponent tabPane;
  private MutableInteger      unique = new MutableInteger(1);
  private int                 minimumHeight;
  private int                 minimumWidth;
  private boolean             tabChangeCancelable;
  private boolean             targetsRegistered;
  private boolean             useMinimumVarsOnlyWhenEmpty;

  /**
   * Constructs a new instance
   */
  public aTabPaneViewer() {
    this(null);
  }

  /**
   * Constructs a new instance
   *
   * @param parent
   *          the widget's parent
   */
  public aTabPaneViewer(iContainer parent) {
    super(parent);
    widgetType = WidgetType.TabPane;
  }

  @Override
  public int addTab(String name, String title, iPlatformIcon icon) {
    return addTab(name, title, icon, (iViewer) null);
  }

  @Override
  public int addTab(String name, String title, iPlatformIcon icon, iPlatformComponent comp) {
    createNewDocument(name, title, icon);
    getAppContext().getWindowManager().setViewer(name, this, new WidgetPaneViewer(this, comp), null);

    return tabPane.getTabCount() - 1;
  }

  @Override
  public int addTab(String name, String title, iPlatformIcon icon, iViewer v) {
    createNewDocument(name, title, icon);

    if (v != null) {
      getAppContext().getWindowManager().setViewer(name, this, v, null);
    }

    return tabPane.getTabCount() - 1;
  }

  @Override
  public int addTab(String name, String title, iPlatformIcon icon, URL url, boolean load) {
    iTabDocument doc = createNewDocument(name, title, icon);

    doc.setActionLink(new ActionLink(this, url, null));

    if (load) {
      doc.reload(null);
    }

    return tabPane.getTabCount() - 1;
  }

  @Override
  public void addTabPaneListener(iTabPaneListener l) {
    tabPane.addTabPaneListener(l);
  }

  @Override
  public void clearContents() {
    if (tabPane != null) {
      closeAll();
    }

    initiallySelectedIndex = -1;
    selectedDocument       = null;
  }

  @Override
  public void closeAll() {
    tabPane.closeAll();
  }

  @Override
  public void closeAllBut(int pos) {
    tabPane.closeAllBut(pos);
  }

  @Override
  public void closeAllBut(String name) {
    int pos = indexForName(name);

    if (pos != -1) {
      closeAllBut(pos);
    }
  }

  @Override
  public void closeTab(int pos) {
    tabPane.closeTab(pos);
  }

  @Override
  public void closeTab(String name) {
    int pos = indexForName(name);

    if (pos != -1) {
      closeTab(pos);
    }
  }

  @Override
  public void configure(Viewer vcfg) {
    vcfg = checkForURLConfig(vcfg);
    configureEx((TabPane) vcfg);
    handleInitialSelection(((TabPane) vcfg).selectedIndex.intValue());
    fireConfigureEvent(vcfg, iConstants.EVENT_CONFIGURE);
  }

  /**
   * Configures tab options from a tab pane configuration
   *
   * @param tc
   *          the configuration
   * @param tabOptions
   *          the options holder
   * @param context
   *          the context viewer
   * @param container
   *          the container for the tab pane
   */
  public static void configureTabOptions(TabPane tc, TabOptions tabOptions, iViewer context,
          iPlatformComponent container) {
    tabOptions.tabClosePosition = tc.closeButton.intValue();
    tabOptions.tabAreaPainter   = ColorUtils.configure(context, tc.getTabAreaPainter(), null);
    tabOptions.boldSelectedTab  = tc.boldSelectedTab.booleanValue();
    tabOptions.editingAllowed   = tc.tabEditingAllowed.booleanValue();
    tabOptions.showIconsOnTab   = tc.showIconsOnTab.booleanValue();
    tabOptions.canCloseTabs     = tc.closeButton.intValue() != TabPane.CCloseButton.none;
    tabOptions.tabStyle         = tc.tabStyle.intValue();

    if (tc.tabPosition.spot_valueWasSet()) {
      tabOptions.tabPosition = tc.tabPosition.intValue();
    }

    if (tc.getContentPadding() != null) {
      tabOptions.tabAreaMargin = tc.getContentPadding().getInsets();
    } else if ((tabOptions.tabAreaPainter == null) || (tabOptions.tabAreaPainter.getBorder() == null)) {
      tabOptions.tabAreaMargin = Platform.getUIDefaults().getInsets("Rare.TabPane.tabAreaMargin");

      if (tabOptions.tabAreaMargin == null) {
        tabOptions.tabAreaMargin = new UIInsets(3, 2, 2, 2);
      }
    }

    tabOptions.tabColorHolder         = UIColorHelper.configure(context, tc.getTabPainter(), null);
    tabOptions.selectedTabColorHolder = UIColorHelper.configure(context, tc.getSelectedTabPainter(), null);

    if (tc.font.spot_hasValue()) {
      tabOptions.tabFont = UIFontHelper.getFont(context, tc.font);
    }
  }

  /**
   * Sets the amount of time before a document is considered stale and should be
   * reloaded
   *
   * @param reloadTimeout
   *          the reload time out or zero for no timeout
   */
  public void setReloadTimeout(long reloadTimeout) {
    tabPane.setReloadTimeout(reloadTimeout);
  }

  /**
   * Gets the amount of time before a document is considered stale and should be
   * reloaded
   *
   * @return the reload time out or zero for no time out
   */
  public long getReloadTimeout() {
    return tabPane.getReloadTimeout();
  }

  /**
   * Configures a tab document using the specified tab configuration and some
   * generic visual tab options
   *
   * @param context
   *          the context
   * @param doc
   *          the tab document to configure
   * @param tab
   *          the tab configuration
   * @param tabOptions
   *          generic tab options
   */
  public static void configureTabVisuals(iViewer context, iTabDocument doc, Tab tab, TabOptions tabOptions) {
    String      s;
    PaintBucket colorHolder = null;

    if (tab != null) {
      s = tab.fgColor.getValue();

      if (s != null) {
        colorHolder = newColorHolder(tabOptions);
        colorHolder.setForegroundColor(ColorUtils.getColor(s));
      }

      if (tab.bgColor.getValue() != null) {
        if (colorHolder == null) {
          colorHolder = newColorHolder(tabOptions);
          ;
        }

        colorHolder = UIColorHelper.configureBackgroundColor(tab.bgColor, colorHolder);
      }
    }

    if (colorHolder != null) {
      doc.setTabColors(colorHolder);
    }
  }

  @Override
  public void dispose() {
    super.dispose();
    tabPane = null;
  }

  @Override
  public int indexForIcon(iPlatformIcon icon) {
    return tabPane.indexOfTab(icon);
  }

  @Override
  public int indexForName(String name) {
    int len = tabPane.getTabCount();

    for (int i = 0; i < len; i++) {
      iTabDocument doc = tabPane.getDocumentAt(i);

      if (doc.getTabName().equals(name)) {
        return i;
      }
    }

    return -1;
  }

  @Override
  public int indexOf(Object o) {
    if (o instanceof iViewer) {
      return indexForTabViewer((iViewer) o);
    }

    return super.indexOf(o);
  }

  @Override
  public int indexForTabViewer(iViewer v) {
    int len = tabPane.getTabCount();

    for (int i = 0; i < len; i++) {
      if (this.getTabViewer(i) == v) {
        return i;
      }
    }

    return -1;
  }

  @Override
  public int indexForTitle(String title) {
    return tabPane.indexOfTab(title);
  }

  @Override
  public int indexOf(iTabDocument doc) {
    if ((doc == null) || (tabPane == null)) {
      return -1;
    }

    return tabPane.indexOfDocument(doc);
  }

  @Override
  public void onConfigurationChanged(boolean reset) {
    tabPane.checkOrientation(null);

    if (reset) {
      this.reset();
    } else {
      int len = tabPane.getTabCount();

      for (int i = 0; i < len; i++) {
        iTabDocument doc = tabPane.getDocumentAt(i);

        if ((doc != null) && (doc.getViewer() != null)) {
          doc.getViewer().onConfigurationChanged(false);
        }
      }
    }

    handleViewerConfigurationChanged(reset);
  }

  @Override
  public void register() {
    callaViewerRegister();

    if (!targetsRegistered) {
      targetsRegistered = true;

      int len = tabPane.getTabCount();

      for (int i = 0; i < len; i++) {
        iTabDocument doc = tabPane.getDocumentAt(i);
        iTarget      t   = doc.getTarget();

        getAppContext().getWindowManager().registerTarget(t.getName(), t);

        iViewer v = t.getViewer();

        if (v != null) {
          v.setParent(this);
          v.targetAcquired(t);
        }
      }
    }

    registerWidgets();
  }

  @Override
  public void reload(boolean context) {
    wasReset = false;

    if (!context && (viewerActionLink != null)) {
      super.reload(context);

      return;
    }

    iTabDocument adoc = tabPane.getSelectedDocument();

    if (!context) {
      int len = tabPane.getTabCount();

      for (int i = 0; i < len; i++) {
        iTabDocument doc = tabPane.getDocumentAt(i);

        if ((doc != null) && (doc != adoc)) {
          doc.reset();
        }
      }

      if ((initiallySelectedIndex > -1) && (initiallySelectedIndex < len)) {
        setSelectedTab(initiallySelectedIndex);
      } else {
        if (adoc != null) {
          adoc.reload(null);
        }
      }
    }
  }

  @Override
  public void removeTabPaneListener(iTabPaneListener l) {
    tabPane.removeTabPaneListener(l);
  }

  @Override
  public void unregister(boolean disposing) {
    super.unregister(disposing);

    if (isDisposed()) {
      return;
    }

    iWindowManager wm = (getAppContext() == null)
                        ? null
                        : getAppContext().getWindowManager();

    if ((wm != null) && targetsRegistered) {
      targetsRegistered = false;

      if (tabPane != null) {
        int len = tabPane.getTabCount();

        for (int i = 0; i < len; i++) {
          iTabDocument doc = tabPane.getDocumentAt(i);
          iTarget      t   = doc.getTarget();

          wm.unRegisterTarget(t.getName());

          iViewer v = t.getViewer();

          if (v != null) {
            v.targetLost(t);
            v.setParent(null);
          }
        }
      }
    }
  }

  /**
   * Sets the location of the close button for tabs
   *
   * @param location
   *          the location: 0=none, 1=on_tab, 2=corner
   */
  public void setCloseButton(int location) {
    // TabOptions.setCloseButton(tabPane, location);
  }

  /**
   * Sets the selected tab
   *
   * @param index
   *          the index of the tab to select
   */
  @Override
  public void setSelectedTab(int index) {
    if (tabPane.getSelectedIndex() != index) {
      tabPane.setSelectedIndex(index);
    }
  }

  /**
   * Sets the selected tab
   *
   * @param name
   *          the name of the tab to select
   */
  public void setSelectedTabName(String name) {
    int index = indexForName(name);

    if (index == -1) {
      throw new ApplicationException("Invalid name (" + name + ")");
    }

    setSelectedTab(index);
  }

  public void setTabChangeCancelable(boolean tabChangeCancelable) {
    this.tabChangeCancelable = tabChangeCancelable;
  }

  @Override
  public void setTabEnabled(int pos, boolean enable) {
    if ((pos > -1) && (pos < tabPane.getTabCount())) {
      tabPane.setEnabledAt(pos, enable);
    }
  }

  @Override
  public void setTabIcon(int pos, iPlatformIcon icon) {
    tabPane.setIconAt(pos, icon);
  }

  @Override
  public void setTabName(int pos, String name) {
    iTabDocument doc = getTabDocument(pos);

    if (doc != null) {
      doc.setTabName(name);
    }
  }

  @Override
  public void setTabPlacement(Location location) {
    tabPane.setTabPlacement(location);
  }

  /**
   * Sets the tab style. The styles are as follows
   *
   * <pre>
   *   0=auto
   *   1=box
   *   2=eclipse
   *   3=eclipse3x
   *   4=excel
   *   5=flat
   *   6=office2003
   *   7=rounded_flat
   *   8=windows
   *   9=windows_selected
   * </pre>
   *
   * @param style
   *          the style
   */
  public void setTabStyle(int style) {
    TabOptions.setTabStyle(tabPane, style);
  }

  @Override
  public void setTabTitle(int pos, String title) {
    tabPane.setTitleAt(pos, title);
  }

  @Override
  public void setTabToolTip(int pos, String tooltip) {}

  @Override
  public void setTabViewer(int pos, iViewer v) {
    iTabDocument doc = getTabDocument(pos);

    if (doc != null) {
      iViewer ov = getAppContext().getWindowManager().setViewer(doc.getTarget().getName(), this, v, null);

      if ((ov != null) && ov.isAutoDispose()) {
        ov.dispose();
      }
    }
  }

  public void setTransitionAnimator(iTransitionAnimator ca) {
    iTabPaneComponent c = (iTabPaneComponent) getDataComponent();

    c.setTransitionAnimator(ca);
  }

  public iTransitionAnimator getTransitionAnimator() {
    iTabPaneComponent c = (iTabPaneComponent) getDataComponent();

    return c.getTransitionAnimator();
  }

  @Override
  public iPlatformIcon getIcon() {
    iPlatformIcon icon = null;

    if (tabPane != null) {
      iTabDocument doc = getSelectedTabDocument();

      if (doc != null) {
        icon = doc.getIcon();

        if (icon == null) {
          iViewer v = doc.getTarget().getViewer();

          if (v != null) {
            icon = v.getIcon();
          }
        }
      }
    }

    if (icon == null) {
      icon = super.getIcon();
    }

    return icon;
  }

  @Override
  public int getItemCount() {
    return tabPane.getTabCount();
  }

  @Override
  public int getSelectedTab() {
    return (tabPane == null)
           ? -1
           : tabPane.getSelectedIndex();
  }

  @Override
  public iTabDocument getSelectedTabDocument() {
    return (iTabDocument) getSelection();
  }

  /**
   * Gets the name of the selected tab
   *
   * @return the name of the selected tab
   */
  public String getSelectedTabName() {
    iTabDocument doc = this.getSelectedTabDocument();

    return (doc == null)
           ? null
           : doc.getTabName();
  }

  @Override
  public iViewer getSelectedTabViewer() {
    iTabDocument doc = this.getSelectedTabDocument();

    if (doc != null) {
      return doc.getTarget().getViewer();
    }

    return null;
  }

  /**
   * Gets the currently selected viewer. If the viewer is not yet loaded the
   * load will start and the callback function called when the viewer is loaded.
   *
   * @param cb
   *          the callback to use (cannot be null)
   *
   * @return the viewer selected viewer null if the viewer is not yet loaded or
   *         there is no selection
   */
  @Override
  public iViewer getSelectedTabViewer(iFunctionCallback cb) {
    iTabDocument doc;
    int          changeIndex = tabPane.getChangeIndex();

    if ((changeIndex < 0) || (changeIndex >= getTabCount())) {
      doc = this.getSelectedTabDocument();

      return (doc == null)
             ? null
             : doc.getViewer();
    }

    doc = this.getTabDocument(changeIndex);

    return doc.getViewer(cb);
  }

  @Override
  public Object getSelection() {
    return (tabPane == null)
           ? null
           : tabPane.getSelectedDocument();
  }

  @Override
  public String getSelectionAsString() {
    iTabDocument doc = this.getSelectedTabDocument();

    return (doc == null)
           ? null
           : doc.getTitle();
  }

  @Override
  public Object getSelectionData() {
    iTabDocument doc = this.getSelectedTabDocument();

    return (doc == null)
           ? null
           : doc.getLinkedData();
  }

  @Override
  public int getTabCount() {
    return (tabPane == null)
           ? 0
           : tabPane.getTabCount();
  }

  @Override
  public iTabDocument getTabDocument(int pos) {
    return tabPane.getDocumentAt(pos);
  }

  @Override
  public iPlatformIcon getTabIcon(int pos) {
    return tabPane.getIconAt(pos);
  }

  @Override
  public Location getTabPlacement() {
    return tabPane.getTabPlacement();
  }

  @Override
  public UIRectangle getTabStripBounds() {
    return tabPane.getTabStripBounds();
  }

  @Override
  public String getTabTitle(int pos) {
    return tabPane.getTitleAt(pos);
  }

  @Override
  public iViewer getTabViewer(int pos) {
    iTabDocument doc = this.getTabDocument(pos);

    return (doc == null)
           ? null
           : doc.getTarget().getViewer();
  }

  /**
   * Gets the viewer at the specified index. If the viewer is not yet loaded the
   * load will start and the callback function called when the viewer is loaded.
   *
   * @param index
   *          the index of the viewer to retrieve
   *
   * @param cb
   *          the callback to use (cannot be null)
   *
   * @return the viewer at the specified index or null if the viewer is not yet
   *         loaded
   */
  @Override
  public iViewer getTabViewer(int index, iFunctionCallback cb) {
    iTabDocument doc = this.getTabDocument(index);

    return doc.getViewer(cb);
  }

  @Override
  public iTabPaneComponent getTabPaneComponent() {
    return tabPane;
  }

  @Override
  public Object getValue() {
    return this.getSelectedTabViewer();
  }

  @Override
  public int getWidgetCount() {
    return Utils.getWidgetCount(this);
  }

  @Override
  protected List<iWidget> getWidgetListEx() {
    int len = getTabCount();

    widgetList.clear();

    iViewer v;

    for (int i = 0; i < len; i++) {
      v = getTabDocument(i).getTarget().getViewer();

      if (v != null) {
        widgetList.add(v);
      }
    }

    return widgetList;
  }

  @Override
  public boolean hasPopupMenu() {
    return super.hasPopupMenu();
  }

  @Override
  public boolean hasSelection() {
    return (tabPane != null) && (tabPane.getSelectedIndex() != -1);
  }

  @Override
  public boolean isTabChangeCancelable() {
    return tabChangeCancelable;
  }

  /**
   * Adjusts the viewers preferred size based on any set minimum size
   * requirements
   *
   * @param d
   *          the current preferred size
   * @return the adjusted preferred size
   */
  protected UIDimension adjustPreferredSize(UIDimension d) {
    if ((d.width < minimumWidth) || (d.height < minimumHeight)) {
      if (!useMinimumVarsOnlyWhenEmpty || (getWidgetCount() == 0)) {
        if (d.width < minimumWidth) {
          d.width = minimumWidth;
        }

        if (d.height < minimumHeight) {
          d.height = minimumHeight;
        }
      }
    }

    return d;
  }

  @Override
  protected void clearConfiguration(boolean dispose) {
    if (tabPane != null) {
      closeAll();
    }

    super.clearConfiguration(dispose);

    if (dispose) {
      if (tabOptions != null) {
        tabOptions.clear();
      }

      tabPane                = null;
      initiallySelectedIndex = -1;
      selectedDocument       = null;
      unique                 = null;
      tabOptions             = null;
    }
  }

  /**
   * Configures the viewer (doe not fire the configure event)
   *
   * @param cfg
   *          the viewer configuration
   */
  protected void configureEx(TabPane cfg) {
    tabOptions    = new TabOptions();
    tabPane       = createTabPaneComponent(cfg);
    dataComponent = tabPane.getComponent();
    formComponent = dataComponent;
    configureEx(cfg, true, false, true);
    actAsFormViewer = cfg.actAsFormViewer.booleanValue();
    displayIcon     = getIcon(cfg.defaultTabIcon);

    if (cfg.tabHeight.getValue() != null) {
      if (!SNumber.booleanValue(cfg.tabHeight.spot_getAttribute("touchOnly")) || Platform.isTouchDevice()) {
        tabPane.setTabMinHeight(ScreenUtils.toPlatformPixelHeight(cfg.tabHeight.getValue(), dataComponent, 1000));
      }
    }

    configureTabOptions(cfg, tabOptions, this, formComponent);

    if (tabOptions.tabStyle == TabPane.CTabStyle.auto) {
      tabOptions.tabStyle = getDefaultTabStyle();
    }

    iPlatformIcon icon = null;
    int           type = cfg.moreIconType.intValue();

    if (!cfg.moreIconType.spot_valueWasSet()) {
      String s = Platform.getUIDefaults().getString("Rare.TabePane.moreIconType");

      if (s != null) {
        if (s.equalsIgnoreCase("dots")) {
          type = TabPane.CMoreIconType.dots;
        } else if (s.equalsIgnoreCase("menu")) {
          type = TabPane.CMoreIconType.menu;
        }
      }
    }

    switch(type) {
      case TabPane.CMoreIconType.custom :
        icon = getIcon(cfg.moreIconType.spot_getAttribute("icon"));

        break;

      case TabPane.CMoreIconType.dots :
        icon = Platform.getResourceAsIcon("Rare.icon.more");

        break;

      default :
        icon = Platform.getResourceAsIcon("Rare.icon.menu");

        break;
    }

    tabPane.setMoreTabsIcon(icon);
    tabOptions.customizePane(this, tabPane);

    if (!isDesignMode() && (cfg.transitionAnimator.getValue() != null)) {
      iTransitionAnimator ta = aAnimator.createTransitionAnimator(this, cfg.transitionAnimator.getValue(),
                                 cfg.transitionAnimator.spot_getAttributesEx());

      if (ta != null) {
        if (ta.getInAnimator() instanceof aSizeAnimation) {
          String s = cfg.transitionAnimator.spot_getAttribute("diagonalAnchor");

          if (s == null) {
            ((aSizeAnimation) ta.getInAnimator()).setDiagonalAnchor(RenderType.LOWER_MIDDLE);
          }
        }

        tabPane.setTransitionAnimator(ta);
      }
    }

    tabPane.beginTabsUpdate();
    configureTabs(cfg, cfg.tabs);
    tabPane.endTabsUpdate();

    Widget wc = (Widget) cfg.leadingHeaderWidget.getValue();

    if (wc != null) {
      iWidget w = this.createWidget(wc);

      registerWidget(w);
      tabPane.setTabLeadingComponent(w.getContainerComponent());
    }

    wc = (Widget) cfg.trailingHeaderWidget.getValue();

    if (wc != null) {
      iWidget w = this.createWidget(wc);

      registerWidget(w);
      tabPane.setTabTrailingComponent(w.getContainerComponent());
    }

    tabPane.checkOrientation(null);

    aWidgetListener l = this.getWidgetListener();

    if (l != null) {
      addTabPaneListener(l);

      if (l.isEnabled(iConstants.EVENT_CHANGE) && tabOptions.editingAllowed) {
        // listenerList.add(iItemChangeListener.class, l);
      }
    }
  }

  /**
   * Configures the tabs from the given set of tab configurations
   *
   * @param cfg
   *          the view configuration
   * @param tabs
   *          the set of tab configurations
   */
  protected void configureTabs(Viewer cfg, SPOTSet tabs) {
    Tab          tab;
    iTabDocument doc;
    int          n = tabs.getCount();

    for (int i = 0; i < n; i++) {
      tab = (Tab) tabs.get(i);
      doc = createDocument(this, tab, unique);
      doc.setTabPaneViewer(this);

      int pos = tabPane.addTab(doc);

      if (!tab.enabled.booleanValue()) {
        tabPane.setEnabledAt(pos, false);
      }

      doc.setReloadOnActivation(tab.reloadOnActivation.booleanValue());

      if (!tab.loadOnActivation.booleanValue()) {
        doc.reload(null);
      }
    }
  }

  protected iTabDocument createDocument(iTabPaneViewer tpv, Tab tab, MutableInteger count) {
    iTabDocument  doc;
    iPlatformIcon icon;
    String        name;
    String        s;
    final iViewer v = tpv.getViewer();

    name = tab.name.getValue();

    if (name == null) {
      name = v.getName();

      if (name == null) {
        name = "TabPane_" + Integer.toHexString((int)tpv.hashCode());
      }

      name = name + ".tab." + Integer.toString(count.getAndIncrement());
    }

    doc = createNewDocument(name, null, null);
    doc.setTabPaneViewer(tpv);
    s = tab.title.getValue();

    if (s == null) {
      s = "";
    } else {
      s = v.expandString(s, false);
    }

    doc.setTitle(s);
    icon = v.getIcon(tab.icon);

    if (icon == null) {
      icon = v.getIcon();
    }

    doc.setIcon(icon);
    icon = v.getIcon(tab.disabledIcon);

    if (icon != null) {
      doc.setDisabledIcon(icon);
    }

    icon = v.getIcon(tab.alternateIcon);

    if (icon != null) {
      doc.setAlternateIcon(icon);
    }

    configureTabVisuals(this, doc, tab, tabOptions);
    doc.setLinkedData(tab.linkedData.getValue());
    doc.setActionLink(aPlatformRegionViewer.createLink(v, name, tab));

    return doc;
  }

  /**
   * Creates a new tab document
   *
   * @param name
   *          the name of the tab
   * @param title
   *          the title for the tab
   * @param icon
   *          the icon for the tab
   *
   * @return the new document
   */
  protected iTabDocument createNewDocument(String name, String title, iPlatformIcon icon) {
    TabDocument doc = new TabDocument(getAppContext(), name, (iTabDocument.iDocumentListener) tabPane);

    doc.setTitle(title);
    doc.setIcon(icon);

    return doc;
  }

  protected iTabPaneComponent createTabPaneComponent(TabPane cfg) {
    return new TabPaneComponent(this);
  }

  /**
   * Handles initially selecting a tab
   *
   * @param index
   *          the tab to select
   */
  protected void handleInitialSelection(int index) {
    initiallySelectedIndex = index;

    if (index == -1) {
      index = 0;
    }

    if (tabPane != null) {
      tabPane.setSelectedIndex(index);
    }
  }

  protected static PaintBucket newColorHolder(TabOptions tabOptions) {
    if ((tabOptions != null) && (tabOptions.tabColorHolder != null)) {
      return (PaintBucket) tabOptions.tabColorHolder.clone();
    } else {
      return new PaintBucket();
    }
  }

  protected int getDefaultTabStyle() {
    return TabPane.CTabStyle.office2003;
  }

  protected static Shape getTabShapeFromStyle(int style) {
    switch(style) {
      case TabPane.CTabStyle.box :
        return Shape.BOX;

      case TabPane.CTabStyle.stacked :
        return Shape.BOX_STACKED;

      case TabPane.CTabStyle.chrome :
        return Shape.CHROME;

      case TabPane.CTabStyle.flat :
        return Shape.FLAT;

      case TabPane.CTabStyle.office2003 :
        return Shape.OFFICE2003;

      case TabPane.CTabStyle.rounded_flat :
        return Shape.ROUNDED_FLAT;

      case TabPane.CTabStyle.windows :
        return Shape.WINDOWS;

      default :
        return Shape.DEFAULT;
    }
  }

  /**
   * Class for holding tab options
   */
  public static class TabOptions {
    public int         tabPosition = -1;
    public boolean     boldSelectedTab;
    public boolean     canCloseTabs;
    public boolean     editingAllowed;
    public PaintBucket selectedTabColorHolder;
    public boolean     showIconsOnTab;
    public UIInsets    tabAreaMargin;
    public PaintBucket tabAreaPainter;
    public int         tabClosePosition;
    public PaintBucket tabColorHolder;
    public UIFont      tabFont;
    public int         tabStyle;

    public void clear() {
      tabAreaPainter         = null;
      tabColorHolder         = null;
      tabAreaMargin          = null;
      selectedTabColorHolder = null;
    }

    public void customizePane(iTabPaneViewer tv, iTabPaneComponent tabPane) {
      if (tv != null) {
        Platform.registerWithWidget(tabPane.getComponent(), tv.getViewer());
      }

      setTabStyle(tabPane, tabStyle);

      if (tabPosition != -1) {
        setTabPosition(tv, tabPane, tabPosition);
      }

      tabPane.setBoldSelectedTab(boldSelectedTab);

      if (tabAreaPainter != null) {
        tabPane.setTabAreaPainter(tabAreaPainter);
      }

      if (tabColorHolder != null) {
        tabPane.setTabPainter(tabColorHolder);
      }

      if (selectedTabColorHolder != null) {
        tabPane.setSelectedPainter(selectedTabColorHolder);
      }

      if (tabAreaMargin != null) {
        tabPane.setTabAreaMargin(tabAreaMargin);
      }

      tabPane.setBoldSelectedTab(boldSelectedTab);
      tabPane.setShowIconsOnTab(showIconsOnTab);
      setCloseButton(tabPane, tabClosePosition);
      setTabPosition(tv, tabPane, tabPosition);

      if (tabFont != null) {
        tabPane.setFont(tabFont);
      }
    }

    public static void setCloseButton(iTabPaneComponent tabPane, int location) {
      switch(location) {
        case TabPane.CCloseButton.on_tab :
          tabPane.setShowCloseButtonOnTab(true);
          tabPane.setShowCloseButton(true);

          break;

        case TabPane.CCloseButton.corner :
          tabPane.setShowCloseButton(true);

          break;

        default :
          tabPane.setShowCloseButton(false);
      }
    }

    public static void setTabPosition(iTabPaneViewer v, iTabPaneComponent tabPane, int position) {
      switch(position) {
        case TabPane.CTabPosition.top :
          tabPane.setTabPlacement(Location.TOP);

          break;

        case TabPane.CTabPosition.bottom :
          tabPane.setTabPlacement(Location.BOTTOM);

          break;

        case TabPane.CTabPosition.left :
          tabPane.setTabPlacement(Location.LEFT);

          break;

        case TabPane.CTabPosition.right :
          tabPane.setTabPlacement(Location.RIGHT);

          break;

        default :
      }
    }

    public static void setTabStyle(iTabPaneComponent tabPane, int style) {
      switch(style) {
        case TabPane.CTabStyle.box :
          tabPane.setTabShape(Shape.BOX);

          break;

        case TabPane.CTabStyle.stacked :
          tabPane.setTabShape(Shape.BOX_STACKED);

          break;

        case TabPane.CTabStyle.chrome :
          tabPane.setTabShape(Shape.CHROME);

          break;

        case TabPane.CTabStyle.flat :
          tabPane.setTabShape(Shape.FLAT);

          break;

        case TabPane.CTabStyle.office2003 :
          tabPane.setTabShape(Shape.OFFICE2003);

          break;

        case TabPane.CTabStyle.rounded_flat :
          tabPane.setTabShape(Shape.ROUNDED_FLAT);

          break;

        case TabPane.CTabStyle.windows :
          tabPane.setTabShape(Shape.WINDOWS);

          break;

        default :
          tabPane.setTabShape(Shape.DEFAULT);

          break;
      }
    }
  }
}
