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
import com.appnativa.rare.iPlatformAppContext;
import com.appnativa.rare.net.ActionLink;
import com.appnativa.rare.platform.PlatformHelper;
import com.appnativa.rare.spot.Viewer;
import com.appnativa.rare.spot.Widget;
import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.ui.UIPoint;
import com.appnativa.rare.ui.UISelectionModelGroup;
import com.appnativa.rare.ui.iListHandler;
import com.appnativa.rare.ui.iParentComponent;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.util.DataParser;
import com.appnativa.rare.widget.aWidget;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.util.FilterableList;
import com.appnativa.util.Helper;
import com.appnativa.util.IdentityArrayList;

import java.net.URL;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * A base container for container type viewers
 *
 * @author Don DeCoteau
 */
public abstract class aContainer extends aPlatformViewer implements iFormViewer {
  WidgetLocationComparator comparator;

  /** whether the toolbar widgets should be registered with its containing form */
  protected boolean actAsFormViewer;

  /** the list of widgets in the form */

  /** the list of widgets in the form */
  protected IdentityArrayList<iWidget>           widgetList;
  private boolean                                _enabled               = true;
  private boolean                                sortedFormWidgetsDirty = true;
  private HashMap<String, iWidget>               formWidgets;
  private boolean                                hogFocus;
  private HashMap<String, Object>                labelWdgets;
  private HashMap<String, UISelectionModelGroup> selectionGroupMap;
  private boolean                                showWaitCursorForChildCreation;
  private IdentityArrayList<iWidget>             sortedFormWidgetsList;

  /**
   * Constructs a new instance
   *
   * @param parent
   *          the parent
   */
  public aContainer(iContainer parent) {
    super(parent);
    widgetList = new IdentityArrayList<iWidget>();
  }

  @Override
  public boolean add(RenderableDataItem item) {
    if (item instanceof iWidget) {
      addWidget((iWidget) item, null, -1);

      return true;
    } else {
      throw new IllegalArgumentException("Not a widget");
    }
  }

  /**
   * Adds the specified widget to this container
   *
   * @param widget
   *          the widget to add
   */
  public void addWidget(iWidget widget) {
    addWidget(widget, null, -1);
  }

  @Override
  public void add(int index, RenderableDataItem item) {
    if (item instanceof iWidget) {
      addWidget((iWidget) item, null, index);

      List<iWidget> list = getWidgetListEx();

      if ((index > -1) && (index < list.size())) {
        int n = list.indexOf(item);

        if ((n != -1) && (n != index)) {
          list.remove(n);
          list.add(index, (iWidget) item);
        }
      }
    } else {
      throw new IllegalArgumentException("Not a widget");
    }
  }

  @Override
  public UISelectionModelGroup addToSelectionGroup(String name, iListHandler comp, Object model, int position) {
    if (selectionGroupMap == null) {
      selectionGroupMap = new HashMap<String, UISelectionModelGroup>();
    }

    UISelectionModelGroup g = selectionGroupMap.get(name);

    if (g == null) {
      g = new UISelectionModelGroup();
      selectionGroupMap.put(name, g);
    }

    g.add(comp, model, position);

    return g;
  }

  @Override
  public iWidget addWidget(Widget cfg) {
    iWidget w = createWidget(this, cfg);

    addWidget(w, createConstraints((iParentComponent) dataComponent, cfg), -1);

    return w;
  }

  @Override
  public iWidget addWidget(iParentComponent panel, Widget cfg, Layout fm) {
    iWidget a = addWidgetEx(panel, cfg, fm);

    if (a == null) {
      throw DataParser.invalidConfigurationException(getAppContext(), sourceURL, cfg.toString());
    }

    registerWidget(a);

    return a;
  }

  @Override
  public void addWidget(iWidget widget, Object constraints, int position) {
    ((iParentComponent) dataComponent).add(widget.getContainerComponent(), constraints, position);
    widget.setParent(this);
    registerWidget(widget);

    if (isRegistered() && (widget instanceof iViewer)) {
      iViewer v = (iViewer) widget;

      if (!v.isRegistered()) {
        v.register();
      }
    }
  }

  @Override
  public void clearContents() {
    super.clearContents();

    List<iWidget> list = getWidgetListEx();
    int           len  = list.size();

    for (int i = 0; i < len; i++) {
      list.get(i).clearContents();
    }
  }

  public void removeAllWidgets() {
    List<iWidget> list = getWidgetListEx();
    int           len  = list.size();

    if (len > 0) {
      iWidget[] a = list.toArray(new iWidget[len]);

      for (iWidget w : a) {
        removeWidget(w);
      }
    }
  }

  @Override
  public void clearForm() {
    this.clearContents();
  }

  /**
   * Creates a new widget from a widget configuration
   *
   * @param cfg
   *          the configuration
   *
   * @return the newly created widget
   */
  @Override
  public iWidget createWidget(Widget cfg) {
    return createWidget(this, cfg);
  }

  /**
   * Creates a new widget from a widget configuration
   *
   * @param parent
   *          the parent;
   * @param link
   *          the action link to use to create the widget
   * @return the newly created widget
   */
  public static iWidget createWidget(iContainer parent, ActionLink link) {
    try {
      iWidget ctx = link.getContext();

      if (ctx == null) {
        ctx = parent;
      }

      Widget  cfg = (Widget) DataParser.loadSPOTObject(parent, link.getConnection(), null);
      iWidget w   = createWidget(parent, cfg, link.getURL(parent));

      if (w instanceof iViewer) {
        ((iViewer) w).setViewerActionLink(link);
      }

      return w;
    } catch(Exception ex) {
      throw ApplicationException.runtimeException(ex);
    } finally {
      link.close();
    }
  }

  /**
   * Creates a new widget from a widget configuration
   *
   * @param parent
   *          the parent;
   * @param cfg
   *          the configuration
   *
   * @return the newly created widget
   */
  public static iWidget createWidget(iContainer parent, Widget cfg) {
    return createWidget(parent, cfg, null);
  }

  /**
   * Creates a new widget from a widget configuration
   *
   * @param parent
   *          the parent;
   * @param cfg
   *          the configuration
   *
   * @param context
   *          the context url for the configuration
   * @return the newly created widget
   */
  public static iWidget createWidget(iContainer parent, Widget cfg, URL context) {
    if (cfg == null) {
      return null;
    }

    iPlatformAppContext app = Platform.getAppContext();

    if (parent == null) {
      parent = app.getWindowViewer();
    }

    if (cfg instanceof Viewer) {
      return app.getWindowManager().createViewer(parent, (Viewer) cfg, context);
    }

    String name = Platform.getSPOTName(cfg.getClass());
    Class  cls  = app.getWidgetHandler(name);

    if (cls == null) {
      cls = app.getWidgetHandler(name);
    }

    if (cls == null) {
      throw new ApplicationException("Unknown object:" + name);
    }

    try {
      iWidget w = PlatformHelper.createWidget(cls, parent);

      w.setParent(parent);
      w.configure(cfg);

      return w;
    } catch(Exception ex) {
      throw ApplicationException.runtimeException(ex);
    }
  }

  @Override
  public boolean handleFocus(iWidget from, boolean next) {
    List<iWidget> list = getSortedFormWidgetList();

    if (list == null) {
      list = getWidgetListEx();
    }

    int n = (from == null)
            ? -1
            : list.indexOf(from);

    if ((n < 1) &&!next) {
      return false;
    }

    int inc;
    int len;

    if (next) {
      len = list.size() - (n + 1);
      inc = 1;
    } else {
      len = n;
      inc = -1;
    }

    for (int i = 0; i < len; i++) {
      n += inc;

      aWidget w = (aWidget) list.get(n);

      if (w instanceof iContainer) {
        if (!hogFocus && w.isEnabled() && w.isVisible() && ((iContainer) w).handleFocus(null, next)) {
          return true;
        }
      } else {
        if (w.isFocusableInCurrentState()) {
          w.focus();

          return true;
        }
      }
    }

    return false;
  }

  @Override
  public void onConfigurationChanged(boolean reset) {
    if (isDisposed()) {
      return;
    }

    List<iWidget> list = getWidgetListEx();
    int           len  = list.size();

    for (int i = 0; i < len; i++) {
      iWidget w = list.get(i);

      if (w instanceof iViewer) {
        ((iViewer) w).onConfigurationChanged(reset);
      }
    }

    handleViewerConfigurationChanged(reset);
  }

  @Override
  public void onConfigurationWillChange(Object newConfig) {
    if (isDisposed()) {
      return;
    }

    List<iWidget> list = getWidgetListEx();
    int           len  = list.size();

    for (int i = 0; i < len; i++) {
      iWidget w = list.get(i);

      if (w instanceof iViewer) {
        ((iViewer) w).onConfigurationWillChange(newConfig);
      }
    }

    handleViewerConfigurationWillChange(newConfig);
  }

  @Override
  public void register() {
    if (!registered) {
      super.register();
      labelWdgets = null;

      List<iWidget> list = getWidgetListEx();

      for (iWidget w : list) {
        if (w instanceof iViewer) {
          ((iViewer) w).register();
        }
      }
    }
  }

  @Override
  public void unregister(boolean disposing) {
    if (registered) {
      super.unregister(disposing);

      if (!disposing) {
        List<iWidget> list = getWidgetListEx();

        for (iWidget w : list) {
          if (w instanceof iViewer) {
            ((iViewer) w).unregister(false);
          }
        }
      }
    }
  }

  @Override
  public void targetAcquired(iTarget target) {
    super.targetAcquired(target);

    iFormViewer fv = getFormViewerEx();

    registerWidgets(fv);
  }

  @Override
  public void targetLost(iTarget target) {
    iFormViewer fv = getFormViewer();

    super.targetLost(target);
    unregisterWidgets(fv);
  }

  @Override
  public iWidget registerFormWidget(iWidget w) {
    return registerFormWidget(w.getName(), w);
  }

  @Override
  public iWidget registerFormWidget(String name, iWidget w) {
    if (w == this) {
      return null;
    }

    sortedFormWidgetsDirty = true;

    if (formWidgets == null) {
      formWidgets = new HashMap<String, iWidget>();
    }

    if (labelWdgets != null) {
      Object l = labelWdgets.remove(name);

      if (l != null) {
        PlatformHelper.setLabelForComponent(w.getDataComponent(), l);
      }
    }

    return formWidgets.put(name, w);
  }

  @Override
  public void registerLabelForWidget(Object label, String widgetName) {
    iWidget w = getWidget(widgetName);

    if (w != null) {
      PlatformHelper.setLabelForComponent(w.getDataComponent(), label);
    } else {
      if (labelWdgets == null) {
        labelWdgets = new HashMap<String, Object>();
      }

      labelWdgets.put(widgetName, label);
    }
  }

  @Override
  public void reload(boolean context) {
    boolean al = getViewerActionLink() != null;

    super.reload(false);

    if (!al) {
      List<iWidget> list = getWidgetListEx();
      int           len  = list.size();

      for (int i = 0; i < len; i++) {
        iWidget w = list.get(i);

        w.reload(context);
      }
    }
  }

  @Override
  public void reloadForm() {
    this.reload(false);
  }

  @Override
  public RenderableDataItem remove(int index) {
    List<iWidget> list = getWidgetListEx();
    iWidget       w    = list.get(index);

    removeWidget(w);

    if (w instanceof RenderableDataItem) {
      return (RenderableDataItem) w;
    }

    return null;
  }

  @Override
  public void removeFromSelectionGroup(String name, Object model) {
    if (selectionGroupMap != null) {
      UISelectionModelGroup g = selectionGroupMap.get(name);

      if (g != null) {
        g.remove(model);
      }
    }
  }

  @Override
  public void removeWidget(iWidget widget) {
    widgetList.remove(widget);

    iPlatformComponent c = widget.getContainerComponent();
    iPlatformComponent p = (c == null)
                           ? null
                           : c.getParent();

    if (p != null) {
      ((iParentComponent) p).remove(c);
    }

    this.unregisterWidget(widget);
    widget.setParent(null);

    if (widget instanceof iViewer) {
      iViewer v = (iViewer) widget;

      if (v.isRegistered() && v.isAutoUnregister()) {
        v.unregister(false);
      }
    }
  }

  @Override
  public boolean requestFocus() {
    if (isDesignMode()) {
      return true;
    }

    return handleFocus(null, true);
  }

  @Override
  public void resetForm() {}

  @Override
  public int size() {
    List<iWidget> list = getWidgetListEx();

    return list.size();
  }

  @Override
  public void submitForm(iFunctionCallback cb) {}

  @Override
  public void unregisterFormWidget(iWidget widget) {
    if (formWidgets != null) {
      iWidget w = formWidgets.remove(widget.getName());

      if (w != null) {
        sortedFormWidgetsDirty = true;
      }
    }
  }

  @Override
  public iWidget unregisterFormWidget(String name) {
    if (formWidgets != null) {
      iWidget w = formWidgets.remove(name);

      if (w != null) {
        sortedFormWidgetsDirty = true;
      }

      return w;
    }

    return null;
  }

  @Override
  public RenderableDataItem set(int index, RenderableDataItem item) {
    if (item instanceof iWidget) {
      RenderableDataItem oitem = remove(index);

      add(index, item);

      return oitem;
    } else {
      throw new IllegalArgumentException("Not a widget");
    }
  }

  /**
   * Whether the toolbar widgets should be registered with its containing form
   *
   * @param act
   *          true to act as a form viewer false otherwise
   */
  public void setActAsFormViewer(boolean act) {
    this.actAsFormViewer = act;
  }

  @Override
  public void setEnabled(boolean enabled) {
    _enabled = enabled;

    switch(disableBehavior) {
      case DISABLE_CONTAINER :
        getContainerComponent().setEnabled(enabled);

        break;

      case DISABLE_WIDGETS :
        this.setWidgetsEnabled(enabled, false);

        break;

      case DISABLE_BOTH :
        getContainerComponent().setEnabled(enabled);
        setWidgetsEnabled(enabled, false);

        break;
    }
  }

  @Override
  public void setHogFocus(boolean hogFocus) {
    this.hogFocus = hogFocus;
  }

  public void setShowWaitCursorForChildCreation(boolean show) {
    this.showWaitCursorForChildCreation = show;
  }

  @Override
  public void setSubmittAttribute(String name, Object value) {
    Platform.ignoreException("setSubmittAttribute", new UnsupportedOperationException());
  }

  /**
   * Sets the enabled status of the widgets in this container
   *
   * @param enabled
   *          true for enabled; false otherwise
   * @param all
   *          true for all widgets or false for only widgets that allow the
   *          container to enable/disable them automatically
   */
  public void setWidgetsEnabled(boolean enabled, boolean all) {
    List<iWidget> list = getWidgetListEx();
    int           len  = list.size();

    for (int i = 0; i < len; i++) {
      iWidget w = list.get(i);

      if (all || w.isAllowContainerToDisable()) {
        w.setEnabled(enabled);
      }
    }
  }

  @Override
  public RenderableDataItem get(int index) {
    List<iWidget> list = getWidgetListEx();
    iWidget       w    = list.get(index);

    if (w instanceof RenderableDataItem) {
      return (RenderableDataItem) w;
    }

    return null;
  }

  @Override
  public iContainer getContainerViewer() {
    return this;
  }

  /**
   * Get the widget with the specified name
   *
   * @param name
   *          the name of the widget
   *
   * @return the widget with the specified name
   */
  public Object getElementById(String name) {
    return getNamedItem(name);
  }

  @Override
  public iFormViewer getFormViewer() {
    if (actAsFormViewer) {
      return this;
    }

    iFormViewer fv = super.getFormViewer();

    return (fv == null)
           ? this
           : fv;
  }

  @Override
  protected iFormViewer getFormViewerEx() {
    if (actAsFormViewer) {
      return this;
    }

    iFormViewer fv = super.getFormViewerEx();

    return (fv == null)
           ? this
           : fv;
  }

  /**
   * Gets the list of widgets registered with the form. That is the widgets that
   * has the viewer registered as their form viewer
   *
   * @return the list of widgets registered with the form.
   */
  @Override
  public List<iWidget> getFormWidgets() {
    if (formWidgets == null) {
      return Collections.EMPTY_LIST;
    }

    ArrayList<iWidget> list = new ArrayList<iWidget>(formWidgets.size());

    list.addAll(formWidgets.values());

    return list;
  }

  /**
   * Gets a hash map of the values that would be submitted as part of an HTTP
   * POST form submission
   *
   * @return the hash widget name and values
   */
  public HashMap getHTTPValuesHash() {
    if ((formWidgets == null) || (formWidgets.size() == 0)) {
      return null;
    }

    HashMap           map = new HashMap(formWidgets.size());
    Iterator<iWidget> it  = formWidgets.values().iterator();

    while(it.hasNext()) {
      iWidget w = it.next();

      if ((w != this) && (w != null) && w.isSubmittable() && w.isValidForSubmission(true)) {
        map.put(w.getHTTPFormName(), w.getHTTPFormValue());
      }
    }

    return map;
  }

  @Override
  public Object getValue() {
    return theValue;
  }

  @Override
  public String getValueAsString() {
    return (_toString == null)
           ? super.getValueAsString()
           : _toString.toString();
  }

  @Override
  public Object getSubmittAttribute(String name) {
    return null;
  }

  @Override
  public iContainer getViewer() {
    return this;
  }

  /**
   * Gets the widget associated with this viewer. If this viewer contains a
   * single widget, then the handle to that widget is returned. Otherwise, the
   * handle to this viewer is returned
   *
   * @return the widget associated with this viewer
   */
  public iWidget getWidget() {
    return this;
  }

  @Override
  public iWidget getWidget(int index) {
    List<iWidget> list = getWidgetListEx();

    return list.get(index);
  }

  @Override
  public iWidget getWidget(String name) {
    if (disposed) {
      return null;
    }

    Object o = this.getNamedItem(name);

    if (o instanceof iWidget) {
      return (iWidget) o;
    }

    return (formWidgets == null)
           ? null
           : formWidgets.get(name);
  }

  @Override
  public int getWidgetCount() {
    List<iWidget> list = getWidgetListEx();

    return list.size();
  }

  @Override
  public iWidget getWidgetFromPath(String path) {
    if (path == null) {
      return null;
    }

    int n = path.indexOf('/');

    if (n == -1) {
      return getWidget(path);
    }

    int start = 0;

    if (n == 0) {
      n     = path.indexOf('/', 1);
      start = 1;
    }

    if (n == -1) {
      return getWidget(path.substring(start));
    }

    String  name = path.substring(n + 1);
    iWidget v    = getWidget(path.substring(0, n));

    if (!(v instanceof iContainer)) {
      return null;
    }

    return ((iContainer) v).getWidgetFromPath(name);
  }

  @Override
  public List<iWidget> getWidgetList() {
    List<iWidget> list = getWidgetListEx();
    int           len  = list.size();

    if (len == 0) {
      return Collections.emptyList();
    }

    return Collections.unmodifiableList(list);
  }

  @Override
  public List<String> getWidgetNames() {
    List<iWidget> list = getWidgetListEx();
    int           len  = list.size();

    if (len == 0) {
      return Collections.emptyList();
    }

    FilterableList<String> flist = new FilterableList<String>(len);

    for (int i = 0; i < len; i++) {
      iWidget w = list.get(i);

      flist.add(w.getName());
    }

    return flist;
  }

  @Override
  public Map<String, iWidget> getWidgets() {
    List<iWidget> list = getWidgetListEx();

    if (list.size() == 0) {
      return Collections.EMPTY_MAP;
    }

    HashMap map = new HashMap<String, iWidget>();
    int     len = list.size();

    for (int i = 0; i < len; i++) {
      iWidget w = list.get(i);

      map.put(w.getName(), w);
    }

    return map;
  }

  /**
   * Whether the toolbar widgets should be registered with its containing form
   *
   * @return true to act as a form viewer false otherwise
   */
  public boolean isActAsFormViewer() {
    return actAsFormViewer;
  }

  /**
   * Returns whether this viewer is an ancestor of the specified widget
   *
   * @param w
   *          the widget
   * @return true if it is; false otherwise
   */
  public boolean isAncestorOf(iWidget w) {
    iContainer p = w.getParent();

    while(p != null) {
      if (p == this) {
        return true;
      }

      p = p.getParent();
    }

    return false;
  }

  @Override
  public boolean isBackPressedHandled() {
    List<iWidget> list = getWidgetListEx();
    int           len  = list.size();

    for (int i = 0; i < len; i++) {
      iWidget w = list.get(i);

      if ((w instanceof iViewer) && ((iViewer) w).isBackPressedHandled()) {
        return true;
      }
    }

    return super.isBackPressedHandled();
  }

  @Override
  public boolean isContainer() {
    return true;
  }

  /**
   * Returns the enabled status of the container
   *
   * @return the enabled status of the container
   */
  public boolean isContainerEnabled() {
    return getContainerComponent().isEnabled();
  }

  @Override
  public boolean isEnabled() {
    return _enabled;
  }

  @Override
  public boolean isFocusableInCurrentState() {
    List<iWidget> list = getWidgetListEx();
    int           len  = (list == null)
                         ? 0
                         : list.size();

    for (int i = 0; i < len; i++) {
      aWidget w = (aWidget) list.get(i);

      if (w.isFocusableInCurrentState()) {
        return true;
      }
    }

    return false;
  }

  @Override
  public boolean isHogFocus() {
    return hogFocus;
  }

  @Override
  public boolean isRetainInitialWidgetValues() {
    return false;
  }

  public boolean isShowWaitCursorForChildCreation() {
    return showWaitCursorForChildCreation;
  }

  @Override
  public boolean isTextDirectionSet() {
    return false;
  }

  /**
   * Adds a widget to the form
   *
   * @param panel
   *          the panes that the widget should be added to
   * @param cfg
   *          the widget's configuration
   * @param layout
   *          the layout
   *
   * @return the widget that was added
   */
  protected iWidget addWidgetEx(iParentComponent panel, Widget cfg, Layout layout) {
    throw new UnsupportedOperationException("Not supported for " + getClass().getName());
  }

  /**
   * Checks if the viewer has a URL specified from which to load the actual
   * configuration
   *
   * @param vcfg
   *          the current configuration
   * @return the new configuration or the passed in config if no url was
   *         specified
   */
  protected Viewer checkForURLConfig(Viewer vcfg) {
    if (vcfg.dataURL.getValue() != null) {
      try {
        ActionLink link = ActionLink.getActionLink(this, vcfg.dataURL, 0);

        this.viewerActionLink = link;
        vcfg                  = (Viewer) DataParser.loadSPOTObject(this, link.getConnection(), (Viewer) vcfg.clone());
      } catch(Exception ex) {
        handleException(ex);
      }
    }

    return vcfg;
  }

  @Override
  protected void clearConfiguration(boolean dispose) {
    super.clearConfiguration(dispose);

    if (widgetList != null) {
      List<iWidget> list = getWidgetListEx();
      iWidget[]     a    = list.toArray(new iWidget[list.size()]);
      int           len  = a.length;

      for (int i = 0; i < len; i++) {
        iWidget w = a[i];

        if (w != null) {
          iPlatformComponent c = w.getContainerComponent();
          iPlatformComponent p = (c == null)
                                 ? null
                                 : c.getParent();

          if (p != null) {
            ((iParentComponent) p).remove(c);
          }

          if (w instanceof iViewer) {
            if (dispose || ((iViewer) w).isAutoDispose()) {
              w.dispose();
            }
          } else {
            w.dispose();
          }
        }
      }

      list.clear();
    }

    if (labelWdgets != null) {
      labelWdgets.clear();
    }

    if (formWidgets != null) {
      formWidgets.clear();
    }

    if (sortedFormWidgetsList != null) {
      sortedFormWidgetsList.clear();
    }

    if (selectionGroupMap != null) {
      selectionGroupMap.clear();
    }

    if (dispose) {
      formWidgets           = null;
      widgetList            = null;
      labelWdgets           = null;
      selectionGroupMap     = null;
      sortedFormWidgetsList = null;
    }
  }

  protected Object createConstraints(iParentComponent panel, Widget cfg) {
    return null;
  }

  /**
   * Gets the layout constraints for the component within the container
   *
   * @param widget
   *          the widget
   * @return the layout constraints for the component
   */
  public Object getConsraints(iWidget widget) {
    return widget.getContainerComponent().getClientProperty(iConstants.RARE_CONSTRAINTS_PROPERTY);
  }

  /**
   * Registers the widget as being part of the container
   *
   * @param w
   *          the widget to register
   */
  protected void registerWidget(iWidget w) {
    String        name = w.getName();
    List<iWidget> list = getWidgetListEx();

    if (name != null) {
      registerNamedItem(name, w);
      getFormViewer().registerFormWidget(name, w);
    }

    if (list.indexOf(w) == -1) {
      if (!_enabled && w.isEnabled() && w.isAllowContainerToDisable()
          && (disableBehavior != DisableBehavior.DISABLE_CONTAINER)) {
        if ((w instanceof aContainer) && ((aContainer) w).disableBehavior == DisableBehavior.DISABLE_CONTAINER) {
          ((aContainer) w).setWidgetsEnabled(true, false);
          w.setEnabled(false);
        } else {
          w.setEnabled(false);
        }
      }

      list.add(w);
    }
  }

  /**
   * Registers widgets
   */
  protected void registerWidgets(iFormViewer fv) {
    List<iWidget> list = getWidgetListEx();

    for (iWidget w : list) {
      if (w instanceof iViewer) {
        ((iViewer) w).register();
      }

      if (fv != this) {
        iWidget ow = fv.registerFormWidget(w.getName(), w);

        if ((ow != null) && (ow != w)) {
          String s = Helper.expandString(getAppContext().getResourceAsString("Rare.runtime.text.widgetExists"),
                                         w.getName());

          Platform.ignoreException(s, null);
        }

        if (w instanceof aContainer) {
          aContainer c = (aContainer) w;

          if (c.actAsFormViewer == false) {
            c.registerWidgets(fv);
          }
        }
      }
    }

    if ((fv != this) && (fv != null) &&!actAsFormViewer && (formWidgets != null)) {
      for (iWidget fw : formWidgets.values()) {
        fv.registerFormWidget(fw);
      }
    }
  }

  /**
   * Registers widgets
   */
  protected void unregisterWidgets(iFormViewer fv) {
    List<iWidget> list = getWidgetListEx();

    if (list != null) {
      for (iWidget w : list) {
        if (w instanceof iViewer) {
          ((iViewer) w).unregister(false);
        }

        if ((fv != this) && (fv != null)) {
          fv.unregisterFormWidget(w);

          if (w instanceof aContainer) {
            aContainer c = (aContainer) w;

            if (c.actAsFormViewer == false) {
              c.unregisterWidgets(fv);
            }
          }

          if (!actAsFormViewer && (formWidgets != null)) {
            for (iWidget fw : formWidgets.values()) {
              fv.unregisterFormWidget(fw);
            }
          }
        }
      }
    }
  }

  /**
   * Unregisters the widget as being part of the container;
   *
   * @param w
   *          the widget to unregister
   *
   */
  protected void unregisterWidget(iWidget w) {
    String name = w.getName();

    if (name != null) {
      unregisterNamedItem(name);

      iFormViewer fv = getFormViewer();

      if (fv != null) {
        getFormViewer().unregisterFormWidget(name);
      }
    }

    if (widgetList != null) {
      widgetList.remove(w);
    }
  }

  @Override
  protected void updateEx() {
    getContainerComponent().revalidate();
    getContainerComponent().repaint();
  }

  protected List<iWidget> getSortedFormWidgetList() {
    if ((formWidgets == null) || formWidgets.isEmpty()) {
      return null;
    }

    if (!sortedFormWidgetsDirty) {
      return sortedFormWidgetsList;
    }

    if (comparator == null) {
      comparator = new WidgetLocationComparator();
    }

    sortedFormWidgetsList = new IdentityArrayList<iWidget>(formWidgets.values());
    Collections.sort(sortedFormWidgetsList, comparator);

    return sortedFormWidgetsList;
  }

  protected List<iWidget> getWidgetListEx() {
    return widgetList;
  }

  static class WidgetLocationComparator implements Comparator<iWidget> {
    UIPoint p = new UIPoint();

    @Override
    public int compare(iWidget o1, iWidget o2) {
      if ((o1 == null) || (o2 == null)) {
        return 0;
      }

      int f1 = 0;
      int f2 = 0;

      o1.getContainerComponent().getLocationOnScreen(p);
      f1 = (int) (p.y * Short.MAX_VALUE + p.x);
      o2.getContainerComponent().getLocationOnScreen(p);
      f2 = (int) (p.y * Short.MAX_VALUE + p.x);

      return f1 - f2;
    }
  }
}
