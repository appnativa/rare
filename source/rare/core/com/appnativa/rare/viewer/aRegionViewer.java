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
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import com.appnativa.rare.Platform;
import com.appnativa.rare.iConstants;
import com.appnativa.rare.exception.ApplicationException;
import com.appnativa.rare.exception.ExpandVetoException;
import com.appnativa.rare.net.ActionLink;
import com.appnativa.rare.spot.CollapsibleInfo;
import com.appnativa.rare.spot.Region;
import com.appnativa.rare.spot.Viewer;
import com.appnativa.rare.spot.Widget;
import com.appnativa.rare.spot.WidgetPane;
import com.appnativa.rare.ui.ColorUtils;
import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.ui.UIBorderHelper;
import com.appnativa.rare.ui.UIImageHelper;
import com.appnativa.rare.ui.Utils;
import com.appnativa.rare.ui.ViewerCreator;
import com.appnativa.rare.ui.ViewerCreator.iCallback;
import com.appnativa.rare.ui.aWidgetListener;
import com.appnativa.rare.ui.aWidgetListener.MiniWidgetListener;
import com.appnativa.rare.ui.iCollapsible;
import com.appnativa.rare.ui.iParentComponent;
import com.appnativa.rare.ui.iPlatformBorder;
import com.appnativa.rare.ui.iWindowManager;
import com.appnativa.rare.ui.border.UICompoundBorder;
import com.appnativa.rare.ui.event.ExpansionEvent;
import com.appnativa.rare.ui.event.iExpandedListener;
import com.appnativa.rare.ui.event.iExpansionListener;
import com.appnativa.rare.ui.painter.iPainterSupport;
import com.appnativa.rare.ui.painter.iPlatformPainter;
import com.appnativa.rare.widget.iWidget;

/**
 * Base class for region viewers
 *
 * @author Don DeCoteau
 */
public abstract class aRegionViewer extends aContainer {
  private static CreatorCallback _creatorCallback = new CreatorCallback();

  /** list of targets */
  private ArrayList<iTarget> theTargets;

  /**
   * Constructs a new instance
   */
  public aRegionViewer() {
    this(null);
  }

  /**
   * Constructs a new instance
   *
   * @param parent
   *          the widget's parent
   */
  public aRegionViewer(iContainer parent) {
    super(parent);
    actAsFormViewer = true;
  }

  @Override
  public void clearContents() {
    clearContents(true);
  }

  /**
   * Creates a link for the regions configuration and the specified target
   *
   * @param context
   *          the widget context
   * @param target
   *          the target
   * @param region
   *          the region
   *
   * @return an Action link that will target the specified region
   */
  public static ActionLink createLink(iWidget context, String target, Region region) {
    ActionLink link = null;
    Widget     w    = (Widget) region.viewer.getValue();

    if (w != null) {
      Viewer v = (w instanceof Viewer)
                 ? (Viewer) w
                 : new WidgetPane(w);

      if (region.dataURL.spot_valueWasSet()) {
        link = ActionLink.getActionLink(context, region.dataURL, 0);
        link.setViewerConfiguration(v);
      } else {
        link = new ActionLink(context, v);
      }
    } else if (region.dataURL.spot_valueWasSet()) {
      link = ActionLink.getActionLink(context, region.dataURL, 0);
    }

    if (link != null) {
      link.setTargetName(target);
    }

    return link;
  }

  /**
   * Hides a region
   *
   * @param index
   *          the index of the region
   */
  public void hideRegion(int index) {
    iTarget t = getRegion(index);

    if (t != null) {
      t.setVisible(false);
      update();
    }
  }

  /**
   * Hides a region
   *
   * @param name
   *          the name of the region to hide
   */
  public void hideRegion(String name) {
    iTarget t = getRegion(name);

    if (t != null) {
      t.setVisible(false);
      update();
    }
  }

  @Override
  public void register() {
    super.register();

    if (theTargets != null) {
      int     len = theTargets.size();
      iTarget t;

      for (int i = 0; i < len; i++) {
        t = theTargets.get(i);
        getAppContext().getWindowManager().registerTarget(t.getName(), t);

        iViewer v = t.getViewer();

        if (v != null) {
          v.register();
        }
      }
    }
  }

  @Override
  public void reload(boolean context) {
    if (!context && (viewerActionLink != null)) {
      viewerActionLink.setContext(this);
      viewerActionLink.setTargetName(getTarget().getName());
      viewerActionLink.run();

      return;
    }

    wasReset = false;

    int len = (theTargets == null)
              ? 0
              : theTargets.size();

    for (int i = 0; i < len; i++) {
      iViewer v = theTargets.get(i).getViewer();

      if (v != null) {
        v.reload(context);
      }
    }
  }

  /**
   * Removes all viewers from regions
   *
   * @param disposeViewers
   *          true to dispose removed viewers; false otherwise
   */
  public void removeAllViewers(boolean disposeViewers) {
    if (theTargets != null) {
      int     len = theTargets.size();
      iViewer v;
      iTarget t;

      for (int i = 0; i < len; i++) {
        t = theTargets.get(i);
        v = t.removeViewer();

        if ((v != null) && disposeViewers) {
          v.dispose();
        }
      }
    }
  }

  @Override
  public void removeWidget(iWidget widget) {
    int r = getRegionIndex(widget);

    if (r > -1) {
      iViewer v = getRegion(r).removeViewer();

      if ((v != null) && v.isAutoDispose()) {
        v.dispose();
      }
    }
  }

  @Override
  public boolean requestFocus() {
    if (isDesignMode()) {
      return true;
    }

    int len = this.getRegionCount();

    for (int i = 0; i < len; i++) {
      iTarget t = getRegion(i);
      iViewer v = (t == null)
                  ? null
                  : t.getViewer();

      if ((v != null) && v.requestFocus()) {
        return true;
      }
    }

    return false;
  }

  /**
   * Shows a region
   *
   * @param index
   *          the index of the region
   */
  public void showRegion(int index) {
    iTarget t = getRegion(index);

    if (t != null) {
      t.setVisible(true);
      update();
    }
  }

  /**
   * Shows a region
   *
   * @param name
   *          the name of the region to show
   */
  public void showRegion(String name) {
    iTarget t = getRegion(name);

    if (t != null) {
      t.setVisible(true);
      update();
    }
  }

  @Override
  public void unregister(boolean disposing) {
    if (isDisposed()) {
      return;
    }

    super.unregister(disposing);

    iWindowManager wm = (getAppContext() == null)
                        ? null
                        : getAppContext().getWindowManager();

    if ((wm != null) && (theTargets != null)) {
      int     len = theTargets.size();
      iTarget t;

      for (int i = 0; i < len; i++) {
        t = theTargets.get(i);

        if (!disposing) {
         wm.unRegisterTarget(t.getName());
        }

        iViewer v = t.getViewer();

        if (v != null) {
          v.unregister(disposing);
        }
      }
    }
  }

  /**
   * Sets the visibility of a region
   *
   * @param index
   *          the index of the region
   * @param visible
   *          true if the region is visible; false otherwise
   */
  public void setRegionVisible(int index, boolean visible) {
    iTarget t = getRegion(index);

    if (t != null) {
      t.setVisible(visible);
    }
  }

  @Override
  public RenderableDataItem get(int index) {
    iViewer v = getRegion(index).getViewer();

    if (v instanceof RenderableDataItem) {
      return (RenderableDataItem) v;
    }

    return null;
  }

  @Override
  public void swap(int index1, int index2) {
    iTarget t1 = getRegion(index1);
    iTarget t2 = getRegion(index2);
    iViewer v1 = t1.removeViewer();
    iViewer v2 = t2.removeViewer();

    if (v1 != null) {
      t2.setViewer(v1);
    }

    if (v2 != null) {
      t1.setViewer(v2);
    }
  }

  public void swapViewers(int index1, int index2) {
    swap(index1, index2);
  }

  /**
   * Gets the target at the specified position
   *
   * @param pos
   *          the position
   * @return the target at the specified position
   */
  public iTarget getRegion(int pos) {
    if ((theTargets != null) && (pos > -1) && (pos < theTargets.size())) {
      return theTargets.get(pos);
    }

    return null;
  }

  /**
   * Gets the viewer at the specified position
   *
   * @param pos
   *          the position
   * @return the viewer at the specified position
   */
  public iViewer getViewer(int pos) {
    return getOrRemoveViewer(pos, false);
  }

  /**
   * Gets or removes the viewer at the specified position
   *
   * @param pos
   *          the position
   * @return the viewer at the specified position
   */
  public iViewer removeViewer(int pos) {
    return getOrRemoveViewer(pos, true);
  }

  /**
   * Gets or removes the viewer at the specified position
   *
   * @param pos
   *          the position
   * @return the viewer at the specified position
   */
  protected iViewer getOrRemoveViewer(int pos, boolean remove) {
    if ((theTargets != null) && (pos > -1) && (pos < theTargets.size())) {
      iTarget t = theTargets.get(pos);

      return (t == null)
             ? null
             : (remove
                ? t.removeViewer()
                : t.getViewer());
    }

    return null;
  }

  @Override
  public RenderableDataItem set(int index, RenderableDataItem item) {
    if (item instanceof iViewer) {
      return (RenderableDataItem) setViewer(index, (iViewer) item);
    } else {
      return super.set(index, item);
    }
  }

  /**
   * Sets the viewer at the specified position
   *
   * @param pos the position
   * @param viewer the viewer to set
   * @return the viewer that was at the specified position
   */
  public iViewer setViewer(int pos, iViewer viewer) {
    iTarget t = theTargets.get(pos);

    return (t == null)
           ? null
           : t.setViewer(viewer);
  }

  /**
   * Gets the target for the named region
   *
   * @param name
   *          the name of the region
   * @return the target with the specified name
   */
  public iTarget getRegion(String name) {
    iTarget t = getAppContext().getWindowManager().getTarget(name);

    if ((t != null) && (theTargets != null) && (theTargets.indexOf(t) != -1)) {
      return t;
    }

    return null;
  }

  /**
   * Returns the number of regions this viewer contains
   *
   * @return the number of regions this viewer contains
   */
  public int getRegionCount() {
    return (theTargets == null)
           ? 0
           : theTargets.size();
  }

  /**
   * Returns the index of the specified target
   *
   * @param t
   *          the target
   * @return the index of the specified target or -1 if the viewer does not
   *         contain the specified target
   */
  public int getRegionIndex(iTarget t) {
    if ((theTargets != null) && (t != null)) {
      return theTargets.indexOf(t);
    }

    return -1;
  }

  /**
   * Returns the region index of the specified widget
   *
   * @param w
   *          the widget
   * @return the index of the target or -1 if the none of the viewer's regions
   *         contain the specified widget
   */
  public int getRegionIndex(iWidget w) {
    if ((theTargets != null) && (w != null)) {
      int     len = theTargets.size();
      iTarget t;

      for (int i = 0; i < len; i++) {
        t = theTargets.get(i);

        iViewer v = t.getViewer();

        if (v == w) {
          return i;
        }
      }
    }

    return -1;
  }

  @Override
  public iWidget getWidget(String name) {
    iWidget w = super.getWidget(name);

    if (w != null) {
      return w;
    }

    if ((theTargets != null) && (name != null)) {
      int     len = theTargets.size();
      iTarget t;

      for (int i = 0; i < len; i++) {
        t = theTargets.get(i);

        iViewer v = t.getViewer();

        if ((v != null) && name.equals(v.getName())) {
          return v;
        }
      }
    }

    return null;
  }

  /**
   * Returns whether the region is visible
   *
   * @param index
   *          the index of the region
   * @return true if the region is visible; false otherwise
   */
  public boolean isRegionVisible(int index) {
    iTarget t = getRegion(index);

    return (t == null)
           ? false
           : t.isVisible();
  }

  /**
   * Adds a target
   *
   * @param t
   *          the target to add
   */
  protected void addTarget(iTarget t) {
    if (theTargets == null) {
      theTargets = new ArrayList<iTarget>(5);
    }

    theTargets.add(t);
  }

  @Override
  protected void clearConfiguration(boolean dispose) {
    this.clearConfigurationEx(dispose);
  }

  /**
   * Clears the viewers configuration an removes all widgets
   *
   * @param dispose
   *          true to dispose of the child viewers; false otherwise
   */
  protected void clearConfigurationEx(boolean dispose) {
    if (theTargets != null) {
      int len = theTargets.size();

      for (int i = 0; i < len; i++) {
        theTargets.get(i).dispose(dispose);
      }

      theTargets.clear();
    }

    if (dispose) {
      theTargets = null;
    }
  }

  /**
   * Clears the region viewers contents
   *
   * @param removeViewer
   *          true to remove of the child viewers; false otherwise
   */
  protected void clearContents(boolean removeViewer) {
    if (theTargets != null) {
      int     len = theTargets.size();
      iViewer v;
      iTarget t;

      for (int i = 0; i < len; i++) {
        t = theTargets.get(i);

        if (removeViewer) {
          v = t.removeViewer();

          if ((v != null) && v.isAutoDispose()) {
            v.dispose();
          }
        } else {
          v = t.getViewer();

          if (v != null) {
            v.clearContents();
          }
        }
      }
    }
  }

  /**
   * Configures events configured via a CollapsibleInfo object
   *
   * @param pane
   *          the collapsible pane
   * @param info
   *          the collapsible info
   */
  protected void configureCollapsibleEvents(iCollapsible pane, CollapsibleInfo info) {
    Map map = aWidgetListener.createEventMap(info.spot_getAttributesEx());

    if ((map != null) && (map.size() > 0)) {
      CollapsibleListener l = new CollapsibleListener(this, map);

      pane.addExpandedListener(l);
      pane.addExpansionListener(l);
    }
  }

  protected abstract iParentComponent createPanel(CollapsibleInfo cinfo);

  /**
   * Creates a target for the specified region configuration
   *
   * @param name
   *          the name for the target
   * @param container
   *          the container for the target
   *
   * @return the new target
   */
  protected abstract iTarget createTarget(String name, iParentComponent container);

  /**
   * Creates a target for the specified region configuration
   *
   * @param name
   *          the name for the target
   * @param container
   *          the container for the target
   * @param region
   *          the region configuration
   *
   * @return the new target
   */
  protected iTarget createTarget(String name, iParentComponent container, Region region) {
    iTarget t = createTarget(name, container);

    if (isDesignMode()) {
      t.setLinkedData(region);
    }

    iPlatformPainter painter = UIImageHelper.configureImage(this, null, region.bgImageURL, false);

    if ((painter != null) && (container instanceof iPainterSupport)) {
      Utils.setBackgroundOverlayPainter(container, painter);
    }

    ActionLink link = createLink(this, name, region);

    if (link != null) {
      if ((region.viewer.getValue() == null) && link.isDeferred(this)) {
        try {
          ViewerCreator.createViewer(this, link, _creatorCallback);
        } catch(MalformedURLException e) {
          throw new ApplicationException(e);
        }
      } else {
        final iViewer v = getAppContext().getWindowManager().createViewer(link);

        link.setViewerConfiguration(null);
        t.setViewer(v);
        v.setViewerActionLink(link);
      }
    }

    iPlatformBorder margin = (region.getContentPadding() == null)
                             ? null
                             : UIBorderHelper.createEmptyBorder(region.getContentPadding());
    iPlatformBorder b      = UIBorderHelper.createBorder(region.getBorders());

    if (b == null) {
      b = margin;
    } else {
      if (margin != null) {
        b = new UICompoundBorder(b, margin);
      }
    }

    if (b != null) {
      container.setBorder(b);
    }

    configureSize(container, region);

    if (region.bgColor.getValue() != null) {
      ColorUtils.configureBackgroundPainter(container, region.bgColor);
    }

    if (!region.visible.booleanValue()) {    // don't call target.setVisible let
      // the viewer do it thing once the
      // target creation is done
      container.setVisible(false);
    }

    return t;
  }

  /**
   * Removes the specified target from the viewer
   *
   * @param t
   *          the target to remove
   */
  protected void removeTarget(iTarget t) {
    int n = (theTargets == null)
            ? -1
            : theTargets.indexOf(t);

    if (n != -1) {
      theTargets.remove(n);
      ((iParentComponent) dataComponent).remove(t.getContainerComponent());
    }
  }

  protected void targetVisibilityChanged(iTarget t, boolean visibile) {}

  @Override
  protected void unregisterWidget(iWidget w) {
    int r = getRegionIndex(w);

    if (r > -1) {
      String name = w.getName();

      if (name != null) {
        unregisterNamedItem(name);
      }

      getRegion(r).removeViewer();
    }
  }

  @Override
  protected List<iWidget> getWidgetListEx() {
    int len = this.getRegionCount();

    widgetList.clear();

    for (int i = 0; i < len; i++) {
      iTarget t = getRegion(i);
      iViewer v = (t == null)
                  ? null
                  : t.getViewer();

      if (v != null) {
        widgetList.add(v);
      }
    }

    return widgetList;
  }

  static class CreatorCallback implements iCallback {
    @Override
    public void configCreated(iWidget context, ActionLink link, Viewer config) {}

    @Override
    public void errorHappened(iWidget context, ActionLink link, Exception e) {
      Platform.getAppContext().getAsyncLoadStatusHandler().loadCompleted(context, link);
      context.handleException(e);
    }

    @Override
    public void startingOperation(iWidget context, ActionLink link) {
      Platform.getAppContext().getAsyncLoadStatusHandler().loadStarted(context, link, null);
    }

    @Override
    public void viewerCreated(iWidget context, ActionLink link, iViewer viewer) {
      Platform.getAppContext().getAsyncLoadStatusHandler().loadCompleted(context, link);

      aPlatformRegionViewer rv = (aPlatformRegionViewer) context;

      if (rv.isDisposed()) {
        return;
      }

      iTarget t = rv.getAppContext().getWindowManager().getTarget(link.getTargetName());

      if (t != null) {
        t.setViewer(viewer);
        rv.update();
      }
    }
  }


  protected static class CollapsibleListener extends MiniWidgetListener
          implements iExpansionListener, iExpandedListener {
    CollapsibleListener(iWidget w, Map events) {
      super(w, events);
    }

    @Override
    public void itemHasCollapsed(ExpansionEvent event) {
      fireEvent(iConstants.EVENT_HAS_COLLAPSED, event, false);
    }

    @Override
    public void itemHasExpanded(ExpansionEvent event) {
      fireEvent(iConstants.EVENT_HAS_EXPANDED, event, false);
    }

    @Override
    public void itemWillCollapse(ExpansionEvent event) throws ExpandVetoException {
      fireEvent(iConstants.EVENT_WILL_COLLAPSE, event, true);
    }

    @Override
    public void itemWillExpand(ExpansionEvent event) throws ExpandVetoException {
      fireEvent(iConstants.EVENT_WILL_EXPAND, event, true);
    }
  }


  protected static class RegionSorter implements Comparator {
    @Override
    public int compare(Object o1, Object o2) {
      if ((o1 == null) || (o2 == null)) {
        return 0;    // should never happen
      }

      int n1 = ((Region) o1).getY() * 1000 + ((Region) o1).getX();
      int n2 = ((Region) o2).getY() * 1000 + ((Region) o2).getX();

      return n1 - n2;
    }
  }
}
