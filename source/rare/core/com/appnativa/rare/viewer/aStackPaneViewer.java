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

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;

import com.appnativa.rare.Platform;
import com.appnativa.rare.iConstants;
import com.appnativa.rare.iFunctionCallback;
import com.appnativa.rare.iPlatformAppContext;
import com.appnativa.rare.exception.ApplicationException;
import com.appnativa.rare.net.ActionLink;
import com.appnativa.rare.spot.StackPane;
import com.appnativa.rare.spot.Viewer;
import com.appnativa.rare.ui.ContainerPanel;
import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.ui.UITarget;
import com.appnativa.rare.ui.ViewerCreator;
import com.appnativa.rare.ui.aWidgetListener;
import com.appnativa.rare.ui.iParentComponent;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.effects.TransitionAnimator;
import com.appnativa.rare.ui.effects.aAnimator;
import com.appnativa.rare.ui.effects.iAnimator.Direction;
import com.appnativa.rare.ui.effects.iPlatformAnimator;
import com.appnativa.rare.ui.effects.iTransitionAnimator;
import com.appnativa.rare.ui.event.ChangeEvent;
import com.appnativa.rare.ui.event.EventListenerList;
import com.appnativa.rare.ui.event.iChangeListener;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.spot.SPOTPrintableString;
import com.appnativa.spot.SPOTSet;
import com.appnativa.util.IdentityArrayList;

/**
 * A viewer that treats a set of viewers as a stack (like cards). Only one
 * viewer can be active at any given time. The viewers can be referenced by name
 * or index
 *
 * @author Don DeCoteau
 */
public abstract class aStackPaneViewer extends aContainer {
  protected iFunctionCallback switchToCallback;
  protected int               changeIndex = -1;

  /** index of selected viewer */
  protected int viewerIndex = -1;

  /** name map for entries */
  protected HashMap<String, StackEntry> namedEntries = new HashMap<String, StackEntry>();

  /** list of entries */
  protected IdentityArrayList<StackEntry> entries = new IdentityArrayList<StackEntry>();

  /** target for viewers */
  protected UITarget paneTarget;
  protected boolean  reloadOnActivation;

  /**  */
  private ChangeEvent changeEvent = new ChangeEvent(this);

  /** event listener list */
  protected EventListenerList   listenerList = new EventListenerList();
  protected iPlatformAnimator   orientingAnimation;
  protected iTransitionAnimator transitionAnimator;

  /**
   * Constructs a new instance
   */
  public aStackPaneViewer() {
    this(null);
  }

  /**
   * Constructs a new instance
   *
   * @param parent
   *          the widget's parent
   */
  public aStackPaneViewer(iContainer parent) {
    super(parent);
    widgetType = WidgetType.StackPane;
  }

  /**
   * Adds a change listener to listen for active viewer changes
   *
   * @param l
   *          the listener to add
   */
  public void addChangeListener(iChangeListener l) {
    listenerList.add(iChangeListener.class, l);
  }

  /**
   * Adds a viewer to the stack
   *
   * @param name
   *          the name to associate with the viewer
   * @param link
   *          the link to use to create the viewer
   *
   * @return the index of the viewer that was added
   */
  public int addViewer(String name, ActionLink link) {
    StackEntry e = new StackEntry(name, link);

    entries.add(e);

    if (name != null) {
      this.namedEntries.put(name, e);
    }

    return entries.size() - 1;
  }

  /**
   * Adds a viewer to the stack
   *
   * @param name
   *          the name to associate with the viewer
   * @param v
   *          the viewer to add
   *
   * @return the index of the viewer that was added
   */
  public int addViewer(String name, iViewer v) {
    StackEntry e = new StackEntry(name, v);

    entries.add(e);

    if (name != null) {
      this.namedEntries.put(name, e);
    }

    return entries.size() - 1;
  }

  /**
   * Adds a viewer to the stack
   *
   * @param index
   *          the position to add the viewer
   * @param name
   *          the name to associate with the viewer
   * @param link
   *          the link to use to create the viewer
   *
   */
  public void addViewer(int index, String name, ActionLink link) {
    StackEntry e = new StackEntry(name, link);
    iViewer    v = getActiveViewer();

    entries.add(index, e);

    if (v != null) {
      viewerIndex = indexOf(v);
    }

    if (name != null) {
      this.namedEntries.put(name, e);
    }
  }

  /**
   * Adds a viewer to the stack at the specified position
   *
   * @param index
   *          the position to add the viewer
   *
   * @param name
   *          the name to associate with the viewer
   * @param v
   *          the viewer to add
   *
   */
  public void addViewer(int index, String name, iViewer v) {
    StackEntry e  = new StackEntry(name, v);
    iViewer    av = getActiveViewer();

    entries.add(index, e);

    if (v != null) {
      viewerIndex = indexOf(av);
    }

    if (name != null) {
      this.namedEntries.put(name, e);
    }
  }

  @Override
  public void clearContents() {
    int len = entries.size();

    for (int i = 0; i < len; i++) {
      entries.get(i).clearContents();
    }

    paneTarget.removeViewer();
  }

  /**
   * Swaps the position of 2 viewers.
   * <p>
   * The method is can to be used in a scenario where you want to use a fix
   * number of viewers to handle a longer list of items to be displayed (flipped
   * thorough)
   * </p>
   *
   * @param index1
   *          the first index
   * @param index2
   *          the second index
   *
   */
  @Override
  public void swap(int index1, int index2) {
    StackEntry e1 = entries.get(index1);
    StackEntry e2 = entries.get(index2);
    ;

    entries.set(index1, e2);
    entries.set(index2, e1);

    if (viewerIndex == index1) {
      viewerIndex = index2;
    } else if (viewerIndex == index2) {
      viewerIndex = index1;
    }
  }

  /**
   * Configures the stack pane viewer
   *
   * @param vcfg
   *          the viewer configuration
   */
  @Override
  public void configure(Viewer vcfg) {
    configureEx((StackPane) vcfg);
    fireConfigureEvent(vcfg, iConstants.EVENT_CONFIGURE);
  }

  @Override
  public void handleActionLink(ActionLink link, boolean deferred) {
    iViewer v = paneTarget.getViewer();

    if (v != null) {
      v.setDataLink(link);
    }
  }

  @Override
  public int indexOf(Object viewer) {
    int len = entries.size();

    for (int i = 0; i < len; i++) {
      iViewer fv = entries.get(i).theViewer;

      if (viewer == fv) {
        return i;
      }
    }

    return -1;
  }

  @Override
  public void onConfigurationChanged(boolean reset) {
    int        len = entries.size();
    StackEntry e   = null;

    for (int i = 0; i < len; i++) {
      e = entries.get(i);

      if (e.httpLink != null) {}

      if (e.theViewer != null) {
        e.theViewer.onConfigurationChanged(reset);
      }
    }

    handleViewerConfigurationChanged(reset);
  }

  @Override
  public void reload(boolean context) {
    wasReset = false;

    if (!context) {
      int len = entries.size();

      paneTarget.reloadViewer();

      for (int i = 0; i < len; i++) {
        if (i != viewerIndex) {
          entries.get(i).reset();
        }
      }
    } else {
      super.reload(false);
    }
  }

  /**
   * Reloads the viewer at the specified index
   *
   * @param index
   *          the index of the viewer to reload
   */
  public void reload(int index) {
    int len = entries.size();

    if (index >= len) {
      throw new IndexOutOfBoundsException(String.valueOf(index) + ">=" + String.valueOf(len));
    }

    if (index < 0) {
      throw new IndexOutOfBoundsException(String.valueOf(index) + "<0");
    }

    StackEntry e = entries.get(index);

    if (e.theViewer == null) {
      e.getViewer(null);    // force a first load
    } else {
      e.getViewer(null).reload(false);
    }
  }

  /**
   * Reloads the viewer with the associated name
   *
   * @param name
   *          the name of the viewer to reload
   */
  public void reload(String name) {
    StackEntry e = namedEntries.get(name);

    if (e != null) {
      if (e.theViewer == null) {
        e.getViewer(null);    // force a first load
      } else {
        e.getViewer(null).reload(false);
      }
    }
  }

  /**
   * Removes all viewers from the pane
   *
   * @param disposeViewers
   *          true to dispose removed viewers; false otherwise
   */
  public void removeAllViewers(boolean disposeViewers) {
    int     len = entries.size();
    iViewer v   = paneTarget.removeViewer();

    if ((v != null) && disposeViewers) {
      v.dispose();
    }

    for (int i = 0; i < len; i++) {
      v = entries.get(i).theViewer;

      if ((v != null) && disposeViewers) {
        v.dispose();
      }
    }

    entries.clear();
    viewerIndex = -1;
    namedEntries.clear();
  }

  /**
   * Removes a previously added change listener
   *
   * @param l
   *          the listener to remove
   */
  public void removeChangeListener(iChangeListener l) {
    listenerList.remove(iChangeListener.class, l);
  }

  /**
   * Removes the viewer at the specified index
   *
   * @param index
   *          the index of the viewer to remove
   *
   * @return the viewer at the specified index or null
   */
  public iViewer removeViewer(int index) {
    int        len = entries.size();
    StackEntry e   = null;

    if ((index > -1) && (index < len)) {
      e = entries.remove(index);

      if (e != null) {
        String name = e.getName();

        if (name != null) {
          namedEntries.remove(e.getName());
        }
      }
    }

    if (e != null) {
      if (reloadOnActivation) {
        e.reset();
      }

      return viewerRemovalCheck(e.theViewer);
    }

    return null;
  }

  /**
   * Removes the viewer with the associated name
   *
   * @param name
   *          the name of the viewer to remove
   * @return the viewer with the associated name or null
   */
  public iViewer removeViewer(String name) {
    StackEntry e = namedEntries.remove(name);

    if (e != null) {
      return removeViewer(entries.indexOf(e));
    }

    return null;
  }

  @Override
  public boolean requestFocus() {
    if (isDesignMode()) {
      return true;
    }

    iViewer v = paneTarget.getViewer();

    if (v != null) {
      return v.requestFocus();
    }

    return super.requestFocus();
  }

  /**
   * Makes the viewer at the specified index the active viewer
   *
   * @param index
   *          the index of the viewer to switch to
   */
  public void switchTo(int index) {
    switchTo(index, null);
  }

  /**
   * Makes the viewer with the associated name the active viewer
   *
   * @param name
   *          the name of the viewer to switch to
   */
  public void switchTo(String name) {
    switchTo(name, null);
  }

  /**
   * Makes the viewer at the specified index the active viewer
   *
   * @param index
   *          the index of the viewer to switch to
   * @param cb
   *          callback to invoke when the switch is complete
   */
  public void switchTo(int index, iFunctionCallback cb) {
    int len = entries.size();

    if (index >= len) {
      throw new IndexOutOfBoundsException(String.valueOf(index) + ">=" + String.valueOf(len));
    }

    if (index < 0) {
      viewerIndex = -1;
      paneTarget.removeViewer();
      fireChangeEvent();

      if (cb != null) {
        cb.finished(false, null);
      }
    } else {
      switchTo(index, entries.get(index), cb);
    }
  }

  /**
   * Makes the viewer with the associated name the active viewer
   *
   * @param name
   *          the name of the viewer to switch to
   * @param cb
   *          callback to invoke when the switch is complete
   */
  public void switchTo(String name, iFunctionCallback cb) {
    StackEntry e = namedEntries.get(name);

    if (e == null) {
      throw new RuntimeException("No such viewer: " + name);
    }

    switchTo(-1, e, cb);
  }

  @Override
  public RenderableDataItem set(int index, RenderableDataItem element) {
    if (!(element instanceof iViewer) && (element != null)) {
      throw new IllegalArgumentException("element must be a viewer");
    }

    return (RenderableDataItem) setViewer(index, (iViewer) element);
  }

  /**
   * Makes the viewer at the specified index the active viewer Note
   *
   * @param index
   *          the index of the viewer to switch to
   */
  public void setSelectedIndex(int index) {
    switchTo(index);
  }

  public void setTransitionAnimator(iTransitionAnimator ta) {
    transitionAnimator = ta;

    if (ta != null) {
      ta.setOutgoingPersists(true);
    }
  }

  public void setTransitionAnimator(String inAnimation) {
    iPlatformAnimator ia = Platform.getWindowViewer().createAnimator(inAnimation);

    if (ia == null) {
      Platform.debugLog("Unknown annimation:" + ia);

      return;
    }

    setTransitionAnimator(new TransitionAnimator(ia, null));
  }

  /**
   * Replaces the viewer at the specified index with a new one
   *
   * @param index
   *          the index
   * @param v
   *          the new viewer
   *
   * @return the old viewer
   */
  public iViewer setViewer(int index, iViewer v) {
    if (index == entries.size()) {
      addViewer(null, v);

      return null;
    }

    StackEntry e  = entries.get(index);
    iViewer    ov = e.theViewer;

    e.theViewer = v;

    if (index == viewerIndex) {
      paneTarget.setViewer(v);
    }

    return ov;
  }

  @Override
  public RenderableDataItem get(int index) {
    iViewer v = getViewer(index);

    if (v instanceof RenderableDataItem) {
      return (RenderableDataItem) v;
    }

    return null;
  }

  /**
   * Gets the active viewer(pending changes are ignored).
   *
   * @return the active viewer or null if no viewer has been activated
   */
  public iViewer getActiveViewer() {
    return (paneTarget == null)
           ? null
           : paneTarget.getViewer();
  }

  /**
   * Gets the view that is currently active or will become active once it is
   * finished loading. If the view is not yet loaded the callback will be
   * invoked when the view is loaded.
   *
   * If the viewer is already loaded then the callback function is no called.
   *
   * @param cb
   *          the callback to use (cannot be null)
   *
   * @return the viewer active viewer or null if the viewer is not yet loaded
   */
  public iViewer getActiveViewer(iFunctionCallback cb) {
    if ((changeIndex < 0) || (changeIndex >= size()) || (changeIndex == viewerIndex)) {
      return getActiveViewer();
    }

    StackEntry e = entries.get(changeIndex);

    return e.getViewer(cb);
  }

  /**
   * Gets the alias of the active viewer (pending changes are ignored). This the
   * name that the stack pane associates with the viewer
   *
   * @return the alias of the active viewer. This the name
   */
  public String getActiveViewerAlias() {
    return (viewerIndex == -1)
           ? null
           : entries.get(viewerIndex).getName();
  }

  /**
   * Gets the HREF of the active viewer (pending changes are ignored).
   *
   * @return the alias of the active viewer or null if the viewer was not
   *         created via a url
   */
  public String getActiveViewerHREF() {
    ActionLink l = (viewerIndex == -1)
                   ? null
                   : entries.get(viewerIndex).httpLink;

    try {
      return (l == null)
             ? null
             : l.getStringURL(this);
    } catch(MalformedURLException e) {
      Platform.ignoreException(null, e);

      return null;
    }
  }

  /**
   * Gets the index of the active viewer (pending changes are ignored).
   *
   * @return the index of the active viewer or -1 if no viewer has been
   *         activated
   */
  public int getActiveViewerIndex() {
    return viewerIndex;
  }

  /**
   * Returns the number of entries in the stack pane. This can differ from
   * getWidgetCount() if there are entries whose viewers have not yet been
   * instantiated.
   *
   * @return the number of entries in the stack pane
   *
   * @see #getWidgetCount()
   */
  public int getEntryCount() {
    return entries.size();
  }

  @Override
  public int size() {
    return entries.size();
  }

  @Override
  public Object getNamedItem(String name) {
    StackEntry e = namedEntries.get(name);

    return (e == null)
           ? super.getNamedItem(name)
           : e.getViewer(null);
  }

  /**
   * Gets the index of the active viewer (pending changes are ignored).
   *
   * @return the index of the active viewer or -1 if no viewer has been
   *         activated
   */
  public int getSelectedIndex() {
    return getActiveViewerIndex();
  }

  /**
   * Returns the active viewer (as the selection)
   *
   * @return the active viewer or null if no viewer has been activated
   */
  @Override
  public Object getSelection() {
    return paneTarget.getViewer();
  }

  public iTransitionAnimator getTransitionAnimator() {
    return transitionAnimator;
  }

  /**
   * Gets the viewer at the specified index. If the viewer is not loaded, it is
   * loaded in-line.
   *
   * @param index
   *          the index of the viewer to retrieve
   *
   * @return the viewer at the specified index
   */
  public iViewer getViewer(int index) {
    int len = entries.size();

    if (index >= len) {
      throw new IndexOutOfBoundsException(String.valueOf(index) + ">=" + String.valueOf(len));
    }

    if (index < 0) {
      throw new IndexOutOfBoundsException(String.valueOf(index) + "<0");
    }

    return entries.get(index).getViewer(null);
  }

  /**
   * Gets the viewer with the associated name If the viewer is not loaded, it is
   * loaded in-line.
   *
   * @param name
   *          the name of the viewer
   *
   * @return the viewer with the associated name or null
   */
  public iViewer getViewer(String name) {
    StackEntry e = namedEntries.get(name);

    if (e != null) {
      return e.getViewer(null);
    }

    return null;
  }
  
   @Override
  public iWidget getWidget(String name) {
     if (entries != null) {    // null if we are disposing
       for (StackEntry e : entries) {
         if (e.theViewer != null && name.equals(e.theViewer.getName())) {
           return e.theViewer;
         }
       }
     }
    return super.getWidget(name);
  }
   
  /**
   * Gets whether or not the viewer with the specified name has been
   * loaded/created.
   *
   * @param name
   *          the name of the viewer
   * @return true if the viewer was loaded and created; false otherwise
   */
  public boolean isViewerLoaded(String name) {
    StackEntry e = namedEntries.get(name);

    return (e == null)
           ? false
           : e.theViewer != null;
  }

  /**
   * Gets whether or not the viewer ate the specified index has been
   * loaded/created.
   *
   * @param index
   *          the index of the viewer
   *
   * @return true if the viewer was loaded and created; false otherwise
   */
  public boolean isViewerLoaded(int index) {
    int len = entries.size();

    if (index >= len) {
      throw new IndexOutOfBoundsException(String.valueOf(index) + ">=" + String.valueOf(len));
    }

    if (index < 0) {
      throw new IndexOutOfBoundsException(String.valueOf(index) + "<0");
    }

    StackEntry e = entries.get(index);

    return (e == null)
           ? false
           : e.theViewer != null;
  }

  /**
   * Gets the viewer at the specified index. If the viewer is not yet loaded the
   * load will start and the callback function called when the viewer is loaded.
   * If the viewer is already loaded then the callback function is no called.
   * <p>
   * If you call one of the swithTo methods after a call to this method then the
   * callback function will not get called.
   * </p>
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
  public iViewer getViewer(int index, iFunctionCallback cb) {
    if (cb == null) {
      throw new NullPointerException("callback cannot be null");
    }

    StackEntry e = entries.get(index);

    return e.getViewer(cb);
  }

  /**
   * Gets the viewer at the specified index. If the viewer is not yet loaded,
   * the load will start and the callback function called when the viewer is
   * loaded. If the viewer is already loaded then the callback function is no
   * called.
   * <p>
   * If you call one of the swithTo methods after a call to this method then the
   * callback function will not get called.
   * </p>
   *
   * @param name
   *          the name of the viewer
   *
   * @param cb
   *          the callback to use (cannot be null)
   *
   * @return the viewer at the specified index or null if the viewer is not yet
   *         loaded
   * @throws NoSuchElementException
   *           if the name is invalid
   */
  public iViewer getViewer(String name, iFunctionCallback cb) {
    if (cb == null) {
      throw new NullPointerException("callback cannot be null");
    }

    StackEntry e = namedEntries.get(name);

    if (e == null) {
      throw new NoSuchElementException(name);
    }

    return e.getViewer(cb);
  }

  @Override
  public int getWidgetCount() {
    int cnt = 0;

    for (StackEntry e : entries) {
      if (e.theViewer != null) {
        cnt++;
      }
    }

    return cnt;
  }

  @Override
  protected void clearConfiguration(boolean dispose) {
    if (entries != null) {
      if (paneTarget != null) {
        paneTarget.dispose(dispose);
      }

      int len = entries.size();

      for (int i = 0; i < len; i++) {
        entries.get(i).dispose(dispose);
      }

      entries.clear();
    }

    if (namedEntries != null) {
      namedEntries.clear();
    }

    if (dispose) {
      if (listenerList != null) {
        listenerList.clear();
      }

      changeEvent        = null;
      listenerList       = null;
      namedEntries       = null;
      entries            = null;
      paneTarget         = null;
      transitionAnimator = null;
      orientingAnimation = null;
    }

    switchToCallback = null;
    super.clearConfiguration(true);
  }

  /**
   * Configures the viewer (doe not fire the configure event)
   *
   * @param cfg
   *          the viewer configuration
   */
  protected void configureEx(StackPane cfg) {
    formComponent = dataComponent = createPanel();
    configureEx(cfg, true, false, true);
    actAsFormViewer    = cfg.actAsFormViewer.booleanValue();
    reloadOnActivation = cfg.reloadOnActivation.booleanValue();

    String name = getName();

    if ((name != null) &&!name.startsWith("$")) {
      name = name + "_" + hashCode();
    }

    paneTarget = new Target(getAppContext(), name, (iParentComponent) dataComponent);
    createViewerEntries(cfg);

    if (!cfg.bgColor.spot_valueWasSet()) {
      dataComponent.setOpaque(false);
    }

    aWidgetListener l = this.getWidgetListener();

    if (l != null) {
      addChangeListener(l);
    }

    if (!isDesignMode() && (cfg.transitionAnimator.getValue() != null)) {
      iTransitionAnimator ta = aAnimator.createTransitionAnimator(this, cfg.transitionAnimator.getValue(),
                                 cfg.transitionAnimator.spot_getAttributesEx());

      setTransitionAnimator(ta);
    }

    int n = cfg.selectedIndex.intValue();

    if ((n > -1) && (n < entries.size())) {
      switchTo(n);
    }
  }

  protected iPlatformComponent createPanel() {
    return new ContainerPanel();
  }

  protected void createViewerEntries(StackPane cfg) {
    int        i;
    int        len;
    StackEntry e;
    SPOTSet    set;
    String     name;

    set = cfg.getViewers();
    len = (set == null)
          ? 0
          : set.getCount();

    for (i = 0; i < len; i++) {
      Viewer v = (Viewer) set.getEx(i);

      name = v.name.getValue();
      e    = new StackEntry(name, (iViewer) createWidget(v));
      entries.add(e);

      if (name != null) {
        namedEntries.put(name, e);
      }
    }

    set = cfg.getViewerURLs();

    SPOTPrintableString aURL;

    len = (set == null)
          ? 0
          : set.getCount();

    for (i = 0; i < len; i++) {
      aURL = (SPOTPrintableString) set.getEx(i);

      ActionLink link = ActionLink.getActionLink(this, aURL, 0);

      name = aURL.spot_getAttribute("name");
      e    = new StackEntry(name, link);
      entries.add(e);

      if (name != null) {
        namedEntries.put(name, e);
      }
    }
  }

  /**
   * Fires change events when the active viewer changes
   */
  protected void fireChangeEvent() {
    Object[] listeners = listenerList.getListenerList();

    // Process the listeners last to first, notifying
    // those that are interested in this event
    for (int i = listeners.length - 2; i >= 0; i -= 2) {
      if (listeners[i] == iChangeListener.class) {
        // Lazily create the event:
        ((iChangeListener) listeners[i + 1]).stateChanged(changeEvent);
      }
    }
  }

  protected void switchTo(int index, final StackEntry e, final iFunctionCallback cb) {
    final int         idx = (index == -1)
                            ? entries.indexOf(e)
                            : index;
    iFunctionCallback scb = new iFunctionCallback() {
      @Override
      public void finished(boolean canceled, Object returnValue) {
        switchToEx(idx, e, cb);
      }
    };

    changeIndex      = idx;
    switchToCallback = scb;

    iViewer v = e.getViewer(scb);

    if (v == null) {
      if (cb != null) {
        cb.finished(false, null);
      }

      return;
    }

    switchToCallback = null;
    changeIndex      = -1;

    if (getActiveViewer() == v) {
      return;
    }

    switchToEx(idx, e, cb);
  }

  protected void switchToEx(int index, final StackEntry e, iFunctionCallback cb) {
    changeIndex = -1;

    iViewer             v  = e.getViewer(null);
    iTransitionAnimator ta = (viewerIndex == -1)
                             ? null
                             : transitionAnimator;

    try {
      if (getActiveViewer() == v) {
        ta = null;
        cb = null;

        return;
      }

      StackEntry oe = (viewerIndex < 0)
                      ? null
                      : entries.get(viewerIndex);

      index = entries.indexOf(e);

      if (index >= entries.size()) {
        return;
      }

      paneTarget.setTransitionAnimator(ta);

      if (ta != null) {
        if (ta.getInAnimator().isAutoOrient()) {
          ta.getInAnimator().setDirection((viewerIndex > index)
                                          ? Direction.BACKWARD
                                          : Direction.FORWARD);
        }

        if ((ta.getOutAnimator() != null) && ta.getOutAnimator().isAutoOrient()) {
          ta.getOutAnimator().setDirection((viewerIndex > index)
                                           ? Direction.BACKWARD
                                           : Direction.FORWARD);
        }

        ta.setCallback(cb);
      }

      viewerIndex = index;
      v.setParent(this);
      setViewerAndFireEvent(v, oe);
    } finally {
      if ((cb != null) && (ta == null)) {
        cb.finished(false, v);
      }
    }
  }

  protected iViewer viewerRemovalCheck(iViewer v) {
    if ((v != null) && (v == getActiveViewer())) {
      viewerIndex = -1;
      paneTarget.removeViewer();
    } else {
      iViewer vv = paneTarget.getViewer();

      if (vv != null) {
        int len = entries.size();

        for (int i = 0; i < len; i++) {
          if (entries.get(i).theViewer == vv) {
            viewerIndex = i;

            break;
          }
        }
      } else {
        viewerIndex = -1;
      }
    }

    return v;
  }

  /**
   * Called to set the viewer into the target and fire the appropriate events
   *
   * @param v
   *          the viewer
   * @param oldEntry
   *          the previous entry
   */
  protected void setViewerAndFireEvent(iViewer v, StackEntry oldEntry) {
    ((Target) paneTarget).setViewerAndFireEvent(v, oldEntry);
  }

  @Override
  protected List<iWidget> getWidgetListEx() {
    widgetList.clear();

    if (entries != null) {    // null if we are disposing
      for (StackEntry e : entries) {
        if (e.theViewer != null) {
          widgetList.add(e.theViewer);
        }
      }
    }

    return widgetList;
  }

  /**
   * StackEntry class
   */
  protected class StackEntry {
    String            entryName;
    ActionLink        httpLink;
    volatile boolean  loading;
    boolean           showingCursor;
    iViewer           theViewer;
    iFunctionCallback waitingCallback;
    private boolean   entryWasReset;

    /**
     * Constructs a new instance
     *
     * @param name
     *          the name
     * @param link
     *          the line
     */
    StackEntry(String name, ActionLink link) {
      this.entryName = name;
      httpLink       = link;
    }

    /**
     * Constructs a new instance
     *
     * @param name
     *          the name
     * @param v
     *          the viewer
     */
    StackEntry(String name, iViewer v) {
      theViewer      = v;
      this.entryName = name;
    }

    public boolean reload(final iFunctionCallback cb) {
      if (httpLink != null) {
        if (!httpLink.isDeferred(aStackPaneViewer.this)) {
          reloadEx(getAppContext().getWindowViewer().createViewer(httpLink));
          httpLink.setViewerConfiguration(null);

          return true;
        } else {
          ViewerCreator.iCallback mcb = new ViewerCreator.iCallback() {
            @Override
            public void viewerCreated(iWidget context, ActionLink link, iViewer viewer) {
              try {
                reloadEx(viewer);
              } finally {
                loading = false;
                loadCompleted(context, null);

                iFunctionCallback c = switchToCallback;

                switchToCallback = null;

                if ((c != null) && (c != cb)) {
                  c.finished(false, viewer);
                  // we ignore callbacks that were requested prior to switch-to
                  // being called
                } else {
                  cb.finished(false, viewer);
                }

                c               = waitingCallback;
                waitingCallback = null;

                if ((c != null) && (c != cb)) {
                  c.finished(false, viewer);
                }
              }
            }
            @Override
            public void errorHappened(iWidget context, ActionLink link, Exception e) {
              loading = false;
              loadCompleted(context, e);
              handleException(e);
            }
            @Override
            public void configCreated(iWidget context, ActionLink link, Viewer cfg) {}
            @Override
            public void startingOperation(iWidget context, ActionLink link) {
              loadStarted(aStackPaneViewer.this);
            }
          };

          loading = true;

          try {
            ViewerCreator.createViewer(aStackPaneViewer.this, httpLink, mcb);
          } catch(MalformedURLException e1) {
            throw new ApplicationException(e1);
          }

          return false;
        }
      } else {
        reloadEx(null);

        return true;
      }
    }

    void clearContents() {
      if (theViewer != null) {
        theViewer.clearContents();
      }
    }

    void dispose(boolean dispose) {
      if ((theViewer != null) && dispose) {
        theViewer.dispose();
      }
    }

    void reset() {
      entryWasReset = true;

      if (theViewer != null) {
        theViewer.dispose();
        theViewer = null;
      }
    }

    String getName() {
      return entryName;
    }

    iViewer getViewer(final iFunctionCallback cb) {
      if ((entryWasReset || (theViewer == null)) &&!loading) {
        entryWasReset = false;

        if (!reload(cb)) {
          return null;
        }
      } else if (loading && (cb != switchToCallback)) {
        waitingCallback = cb;
      }

      if ((cb != null) && (theViewer != null)) {
        Platform.invokeLater(new Runnable() {
          @Override
          public void run() {
            cb.finished(false, theViewer);
          }
        });
      }

      return theViewer;
    }

    protected void loadCompleted(iWidget context, Throwable error) {
      if (showingCursor) {
        showingCursor = false;

        if (error != null) {
          context.getAppContext().getAsyncLoadStatusHandler().errorOccured(context, httpLink, error);
        } else {
          context.getAppContext().getAsyncLoadStatusHandler().loadCompleted(context, httpLink);
        }
      }
    }

    protected void loadStarted(iWidget context) {
      if (!showingCursor) {
        showingCursor = true;
        context.getAppContext().getAsyncLoadStatusHandler().loadStarted(context, httpLink, null);
      }
    }

    protected void reloadEx(iViewer v) {
      if (v != null) {
        if (theViewer != null) {
          theViewer.dispose();
        }

        theViewer = v;

        if (theViewer != null) {
          String name = theViewer.getName();

          if (name != null) {
            registerNamedItem(name, theViewer);
          }
        }
      } else if (theViewer != null) {
        theViewer.reload(true);
      }
    }
  }


  protected class Target extends UITarget implements iFunctionCallback {
    StackEntry oldEntry;

    public Target(iPlatformAppContext app, String name, iParentComponent container) {
      super(app, name, container, false);
    }

    @Override
    public void dispose(boolean disposeviewer) {
      if (!isDisposed()) {
        try {
          iViewer v = removeViewer();

          if (v != null) {
            if (disposeviewer || v.isAutoDispose()) {
              v.dispose();
            }
          }
        } catch(Throwable e) {}

        super.dispose(false);
      }
    }

    @Override
    public void finished(boolean canceled, Object returnValue) {
      if (!canceled) {
        fireChangeEvent();

        if ((oldEntry != null) && reloadOnActivation) {
          oldEntry.reset();
        }
      }

      oldEntry = null;
    }

    public void setViewerAndFireEvent(iViewer v, StackEntry oldEntry) {
      this.oldEntry = oldEntry;
      setViewer(v, transitionAnimator, this);
    }
  }
}
