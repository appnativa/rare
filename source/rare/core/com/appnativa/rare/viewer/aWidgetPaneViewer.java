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
import com.appnativa.rare.scripting.iScriptHandler;
import com.appnativa.rare.spot.DataItem;
import com.appnativa.rare.spot.Viewer;
import com.appnativa.rare.spot.Widget;
import com.appnativa.rare.spot.WidgetPane;
import com.appnativa.rare.ui.RenderType;
import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.ui.UITarget;
import com.appnativa.rare.ui.Utils;
import com.appnativa.rare.ui.ViewerCreator;
import com.appnativa.rare.ui.aWidgetListener;
import com.appnativa.rare.ui.effects.TransitionAnimator;
import com.appnativa.rare.ui.effects.iPlatformAnimator;
import com.appnativa.rare.ui.effects.iTransitionAnimator;
import com.appnativa.rare.ui.event.DataEvent;
import com.appnativa.rare.ui.iParentComponent;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.iWindowManager;
import com.appnativa.rare.widget.BeanWidget;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.util.Helper;

import java.net.MalformedURLException;

/**
 * A viewer that wraps a widget allowing it to be placed directly into a target
 *
 * @version    0.3, 2007-05-14
 * @author     Don DeCoteau
 */
public abstract class aWidgetPaneViewer extends aContainer {

  /** the render type for the pane */
  protected RenderType widgetRenderType = RenderType.CENTERED;
  protected boolean    autoDisposeViewers;
  protected boolean    autoResizeWidgets;
  protected boolean    manualUpdate;

  /** the target for the widget */
  protected UITarget paneTarget;

  /** function to use to configure the viewer */
  protected String scriptFunctionPrefix;

  /** the wrapped widget */
  protected iWidget theWidget;

  /** the panel containing the widget */
  protected iParentComponent  widgetPanel;
  private iTransitionAnimator transitionAnimator;

  /**
   * Creates a new instance of WidgetPaneViewer
   */
  public aWidgetPaneViewer() {
    this((iContainer) null);
  }

  /**
   * Creates a new instance of WidgetPaneViewer
   *
   * @param parent the widget's parent
   */
  public aWidgetPaneViewer(iContainer parent) {
    super(parent);
    widgetType = WidgetType.WidgetPane;
  }

  /**
   * Constructs a new instance
   *
   * @param comp the component to wrap
   */
  public aWidgetPaneViewer(iPlatformComponent comp) {
    this((iContainer) null);
    configure(new WidgetPane());
    setWidget(new BeanWidget(this, comp));
  }

  /**
   *   Constructs a new instance
   *
   *  @param parent the parent
   *  @param comp the component to wrap
   */
  public aWidgetPaneViewer(iContainer parent, iPlatformComponent comp) {
    this(parent);
    setParent(parent);
    widgetPanel   = createWidgetPanel();
    formComponent = dataComponent = widgetPanel;
    setWidget(new BeanWidget(this, comp));
  }

  @Override
  public void clearContents() {
    setWidget((iWidget) null);
  }

  /**
   * Configures the pane to hold the specified component
   *
   * @param comp the component
   * @param cfg optional configuration object to use
   */
  public void configueForComponent(iPlatformComponent comp, Viewer cfg) {
    clearConfiguration(true);

    if (cfg == null) {
      cfg = new Viewer();
    }

    formComponent = dataComponent = widgetPanel = createWidgetPanel();
    configureEx(cfg, true, false, true);
    setWidget(new BeanWidget(this, comp));
  }

  /**
   * Configures the pane for a component created via a script
   *
   * @param functionPrefix the prefix for function call to create/configure the widget
   * @param cfg optional configuration object to use
   */
  public void configueViaScript(String functionPrefix, Viewer cfg) {
    iScriptHandler     sh   = this.getScriptHandler();
    String             s    = sh.getFunctionCall(functionPrefix + "Create", null);
    iPlatformComponent comp = (iPlatformComponent) aWidgetListener.evaluate(this, sh, s, null);

    if (comp != null) {
      configueForComponent(comp, cfg);
      scriptFunctionPrefix = functionPrefix;
    } else {
      iPlatformAppContext app = getAppContext();

      throw new ApplicationException(
          Helper.expandString(app.getResourceAsString("Rare.runtime.text.invalidComponentFunction"), s));
    }
  }

  /**
   * Configures the widget pane
   *
   * @param vcfg the widget pane's configuration
   */
  @Override
  public void configure(Viewer vcfg) {
    configureEx((WidgetPane) vcfg);
    fireConfigureEvent(vcfg, iConstants.EVENT_CONFIGURE);
  }

  @Override
  public void handleActionLink(ActionLink link, boolean deferred) {
    if (isDesignMode()) {
      return;
    }

    if (theWidget == null) {
      try {
        setWidget(link);
      } catch(Exception ex) {
        handleException(ex);
      }
    } else {
      if (scriptFunctionPrefix != null) {
        iScriptHandler sh = this.getScriptHandler();
        String         s  = sh.getFunctionCall(scriptFunctionPrefix + "Load", null);

        if (deferred) {
          aWidgetListener.execute(this, sh, s, iConstants.EVENT_FUNCTION_EVAL, new DataEvent(this, link));
        } else {
          aWidgetListener.evaluate(this, sh, s, new DataEvent(this, link));
        }
      } else {
        theWidget.setDataLink(link);
      }
    }
  }

  @Override
  public void register() {
    callaViewerRegister();

    if (paneTarget != null) {
      iViewer v = paneTarget.getViewer();

      if (v != null) {
        v.targetAcquired(paneTarget);
        v.setParent(this);
      }
    }

    registerWidgets();
  }

  @Override
  public void reload(boolean context) {
    wasReset = false;

    if (theWidget != null) {
      theWidget.reload(context);
    }
  }

  @Override
  public boolean requestFocus() {
    if (isDesignMode()) {
      return true;
    }

    if (theWidget != null) {
      return theWidget.requestFocus();
    }

    return super.requestFocus();
  }

  @Override
  public int size() {
    if (theWidget instanceof RenderableDataItem) {
      return 1;
    }

    return 0;
  }

  @Override
  public void unregister(boolean disposing) {
    super.unregister(disposing);

    if (isDisposed()) {
      return;
    }

    if (paneTarget != null) {
      iWindowManager wm = (getAppContext() == null)
                          ? null
                          : getAppContext().getWindowManager();

      if (wm != null) {
        wm.unRegisterTarget(paneTarget.getName());

        iViewer v = paneTarget.getViewer();

        if (v != null) {
          v.targetLost(paneTarget);
          v.setParent(null);
        }
      }
    }
  }

  /**
   * Sets whether the pane automatically resizes widgets
   * to fit its size.
   *
   * @param autoResizeWidgets true to resize to to use the widgets alignment to decide
   */
  public void setAutoResizeWidgets(boolean autoResizeWidgets) {
    if (autoResizeWidgets) {
      this.autoResizeWidgets = true;
      setWidgetRenderTypeEx(RenderType.STRETCHED);
    } else {
      this.autoResizeWidgets = true;
      setWidgetRenderTypeEx(widgetRenderType);
    }
  }

  /**
   * Sets the component that the pane will host.
   * The current component will be replaced
   *
   * @param comp the component to host
   */
  public void setComponent(iPlatformComponent comp) {
    setWidget(new BeanWidget(this, comp));
  }

  /**
   * Sets the component to be used for the container panel
   * The component is set as the new container and widget panel. No other action is taken.
   * It is the responsibility of the caller to remove any the existing widget proir
   * to calling this method. This method must be called prior to the pane being added to its
   * parent pane.
   *
   * @param component the component
   */
  public void setContainerPanel(iPlatformComponent component) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void setDataItems(DataItem items) {
    if (theWidget == null) {
      super.setDataItems(items);
    } else {
      theWidget.setDataItems(items);
    }
  }

  /**
   * Sets whether the pane is in manual update mode
   * In manual update mode a call to update() must be made after a new widget has been set,
   * in order for the widget to be instantly sized and shown. If manual mode is false (the default)
   * then the update method is automatically called when a new widget is set.
   *
   * @param manual true if the pane is in manual update mode; false otherwise
   */
  public void setManualUpdate(boolean manual) {
    this.manualUpdate = manual;
  }

  @Override
  public void setOpaque(boolean opaque) {
    if (getContainerComponent() instanceof iPlatformComponent) {
      getContainerComponent().setOpaque(opaque);
    }
  }

  /**
   * Sets the prefix for the name of the function to use to create and configure the viewers data
   * component.
   * <p>
   * The prefix will be prepended to the word "Create" or "Configure" and the function created by that
   * combination will be invoked to create and configure the widget.
   *
   * @param prefix the function prefix
   */
  public void setScriptFunctionPrefix(String prefix) {
    scriptFunctionPrefix = prefix;
  }

  public void setTransitionAnimator(iTransitionAnimator transitionAnimator) {
    this.transitionAnimator = transitionAnimator;
  }

  public void setTransitionAnimator(String inAnimation) {
    iPlatformAnimator ia = Platform.getWindowViewer().createAnimator(inAnimation);

    if (ia == null) {
      Platform.debugLog("Unknown annimation:" + ia);

      return;
    }

    setTransitionAnimator(new TransitionAnimator(ia, null));
  }

  @Override
  public void setValue(Object value) {
    if (theWidget == null) {
      super.setValue(value);
    } else {
      theWidget.setValue(value);
    }
  }

  /**
   * Sets the specified widget into the pane
   *
   * @param link the link for the widget
   */
  public void setWidget(ActionLink link) throws Exception {
    setWidgetConfig((Widget) getAppContext().getWindowViewer().createConfigurationObject(link));
  }

  /**
   * Sets the specified widget into the pane
   *
   * @param w the widget  to set
   */
  public void setWidget(iWidget w) {
    setWidget(w, true);
  }

  /**
   *  Sets the specified widget into the pane
   *
   *  @param url the url for the widget
   */
  public void setWidget(String url) throws Exception {
    setWidget(getAppContext().getWindowViewer().createActionLink(this, url));
  }

  /**
   * Sets the specified widget into the pane
   *
   * @param w the widget  to set
   * @param disposeCurrent true to dispose the current widget; false other wise
   *                                                                                             if the current widget is a viewer then
   *                                                                                                    this parameter is ignored
   */
  public void setWidget(iWidget w, boolean disposeCurrent) {
    if (w == theWidget) {
      if ((w != null) && (w.getContainerComponent().getParent() == widgetPanel)) {
        if (!isManualUpdate()) {
          update();
        }

        return;
      }
    }

    String      s;
    iFormViewer fv = getFormViewer();

    if (theWidget != null) {
      s = theWidget.getName();
      unregisterNamedItem(s);

      if ((fv != null) && (fv != this)) {
        fv.unregisterFormWidget(theWidget);
      }

      if (theWidget instanceof iViewer) {
        removeViewerEx(disposeCurrent);
      } else {
        widgetPanel.remove(theWidget.getContainerComponent());

        if (disposeCurrent) {
          theWidget.dispose();
        }
      }
    }

    widgetList.clear();
    theWidget = w;

    if (w != null) {
      s = w.getName();
      adjustWidgetForPlatform(w);
      registerNamedItem(s, w);

      if (w instanceof iViewer) {
        setViewer((iViewer) w);
      } else {
        widgetPanel.add(w.getContainerComponent());

        if ((fv != null) && (fv != this)) {
          registerFormWidget(w);
        }

        if (!isManualUpdate()) {
          widgetPanel.revalidate();
          widgetPanel.repaint();
        }
      }

      w.setParent(this);
      widgetList.add(w);
    } else if (!isManualUpdate()) {
      widgetPanel.revalidate();
      widgetPanel.repaint();
    }
  }

  /**
   * Sets the specified widget into the pane.
   * The widget is created from the specified configuration.
   *
   * @param cfg the configuration object for widget  to set
   */
  public void setWidgetConfig(Widget cfg) {
    setupRenderType(cfg);

    iWidget w = createWidget(cfg);

    setWidget(w);
  }

  /**
   * Sets the component to be used for the widget panel
   * The component is set as the new widget panel. No other action is taken.
   * It is the responsibility of the caller to remove any the existing widget prior
   * to calling this method
   *
   * @param component the component
   */
  public void setWidgetPanel(iPlatformComponent component) {
    throw new UnsupportedOperationException();
  }

  /**
   * Sets the render type for the pane.
   * This determines how the hosted component is laid out within the pane
   *
   * @param  type the render type for the pane
   */
  public void setWidgetRenderType(RenderType type) {
    widgetRenderType = type;
    setWidgetRenderTypeEx(type);
  }

  @Override
  public RenderableDataItem get(int index) {
    if ((theWidget == null) || (index > 0)) {
      return super.get(index);
    }

    if (theWidget instanceof RenderableDataItem) {
      return (RenderableDataItem) theWidget;
    }

    return null;
  }

  @Override
  public Object getAttribute(String key) {
    Object o = super.getAttribute(key);

    if ((o == null) && (theWidget != null)) {
      {
        String n1 = getName();
        String n2 = theWidget.getName();

        if ((n1 != null) && (n2 != null)) {
          if ((n1 == n2) || n1.equals(n2)) {
            o = theWidget.getAttribute(key);
          }
        }
      }
    }

    return o;
  }

  /**
   * Gets the component that the pane is hosting
   *
   * @return  the component that the pane is hosting
   */
  public iPlatformComponent getComponent() {
    return (theWidget == null)
           ? dataComponent
           : theWidget.getContainerComponent();
  }

  @Override
  public Object getHTTPFormValue() {
    if (theWidget != null) {
      return theWidget.getHTTPFormValue();
    }

    return super.getHTTPFormValue();
  }

  @Override
  public Object getSelection() {
    return (theWidget == null)
           ? null
           : theWidget.getSelection();
  }

  @Override
  public Object getSelectionData() {
    return (theWidget == null)
           ? null
           : theWidget.getSelectionData();
  }

  public iTransitionAnimator getTransitionAnimator() {
    return transitionAnimator;
  }

  @Override
  public Object getValue() {
    return (theWidget == null)
           ? null
           : theWidget.getValue();
  }

  @Override
  public iWidget getWidget() {
    return theWidget;
  }

  /**
   * Returns the panel that will hold the widget
   *
   * @return the panel that will hold the widget
   */
  public iParentComponent getWidgetPanel() {
    return widgetPanel;
  }

  /**
   * @return the autoDisposeViewers
   */
  public boolean isAutoDisposeViewers() {
    return autoDisposeViewers;
  }

  /**
   * Gets whether the pane automatically resizes widgets
   * to fit its size.
   *
   * @return true if it auto resizes; false if it uses the widgets alignment to decide
   */
  public boolean isAutoResizeWidgets() {
    return autoResizeWidgets;
  }

  /**
   * Returns whether the pane is in manual update mode
   *
   * @return true if the pane is in manual update mode; false otherwise
   */
  public boolean isManualUpdate() {
    return manualUpdate;
  }

  @Override
  public boolean isSubmittable() {
    return (theWidget == null)
           ? false
           : theWidget.isSubmittable();
  }

  @Override
  public boolean isValidForSubmission(boolean showerror) {
    return (theWidget == null)
           ? false
           : theWidget.isValidForSubmission(showerror);
  }

  protected iWidget addWidgetEx(iPlatformComponent panel, Widget cfg, Layout layout) {
    iWidget w = createWidget(cfg);

    setWidget(w);
    setupRenderType(cfg);

    return w;
  }

  protected abstract void adjustWidgetForPlatform(iWidget w);

  @Override
  protected void clearConfiguration(boolean dispose) {
    if (!dispose) {
      setWidget((iWidget) null);
    } else {
      if (paneTarget != null) {
        paneTarget.dispose(true);
      }

      if ((transitionAnimator != null) && transitionAnimator.isAutoDispose()) {
        transitionAnimator.dispose();
      }

      transitionAnimator = null;
      paneTarget         = null;
      widgetPanel        = null;
      theWidget          = null;
      widgetRenderType   = null;
    }
  }

  /**
   * Configures the widget pane
   *
   * @param cfg the widget pane's configuration
   */
  protected void configureEx(WidgetPane cfg) {
    iParentComponent cp = createWidgetPanel();

    configureEx(cp, cp, cfg);

    if (scriptFunctionPrefix != null) {
      iScriptHandler sh = this.getScriptHandler();
      String         s  = sh.getFunctionCall(scriptFunctionPrefix + "Configure", null);

      aWidgetListener.evaluate(this, sh, s, new DataEvent(this, cfg));
    }

    this.configureGenericDnD(dataComponent, cfg);
  }

  /**
   * Configures the pane
   *
   * @param container the pane's container
   * @param wpanel the panel that will hold the widget
   * @param cfg the configuration
   */
  protected void configureEx(iParentComponent container, iParentComponent wpanel, WidgetPane cfg) {
    widgetPanel   = wpanel;
    dataComponent = wpanel;
    formComponent = container;
    configureEx(cfg, true, false, true);

    if (cfg.autoResizeWidget.booleanValue()) {
      autoResizeWidgets = true;
      setWidgetRenderType(RenderType.STRETCHED);
    } else {
      setWidgetRenderType(widgetRenderType);
    }

    if (cfg.getScrollPane() != null) {
      formComponent = PlatformHelper.makeScrollPane(this, cfg.getScrollPane(), dataComponent);
    }

    Widget  wc = (Widget) cfg.widget.getValue();
    iWidget w  = null;

    if ((wc == null) && cfg.dataURL.spot_valueWasSet()) {
      ActionLink link = ActionLink.getActionLink(this, cfg.dataURL, 0);

      if (link.getViewerConfiguration() != null) {
        wc = link.getViewerConfiguration();
      } else {
        try {
          ViewerCreator.createConfiguration(this, link, new iFunctionCallback() {
            @Override
            public void finished(boolean canceled, Object returnValue) {
              if (canceled) {
                if (!isDisposed() && (returnValue instanceof Throwable)) {
                  handleException((Throwable) returnValue);
                }

                return;
              }

              if (!isDisposed() && (returnValue != null)) {
                Widget  wc = (Widget) returnValue;
                iWidget w  = createWidget(aWidgetPaneViewer.this, wc);

                if (wc.horizontalAlign.spot_valueWasSet() || wc.verticalAlign.spot_valueWasSet()) {
                  setWidgetRenderType(Utils.getRenderType(wc.horizontalAlign.intValue(), wc.verticalAlign.intValue()));
                }

                setWidget(w);
              }
            }
          });
        } catch(MalformedURLException ex) {
          handleException(ex);
        }
      }
    }

    if (wc != null) {
      w = createWidget(this, wc);

      if (wc.horizontalAlign.spot_valueWasSet() || wc.verticalAlign.spot_valueWasSet()) {
        setWidgetRenderType(Utils.getRenderType(wc.horizontalAlign.intValue(), wc.verticalAlign.intValue()));
      }
    }

    if (w != null) {
      setWidget(w);
    }
  }

  /**
   * Removes the current widget from the pane
   */
  public iWidget removeWidget() {
    iWidget w = theWidget;

    setWidget((iWidget) null, false);

    return w;
  }

  protected abstract iParentComponent createWidgetPanel();

  /**
   * Removes the current viewer from the pane
   */
  protected void removeViewerEx(boolean dispose) {
    if (paneTarget != null) {
      iViewer v = paneTarget.removeViewer();

      if (v != null) {
        if (dispose) {
          v.dispose();
        } else {
          v.setParent(null);
        }
      }
    }
  }

  /**
   * Sets the specified viewer into the pane
   *
   * @param v the viewer to set
   */
  protected void setViewer(iViewer v) {
    if (paneTarget == null) {
      String name = getName();

      if ((name != null) &&!name.startsWith("$")) {
        name = name + "_" + hashCode();
      }

      paneTarget = new UITarget(getAppContext(), name, widgetPanel, false);
      paneTarget.setIgnoreViewerRenderType(true);
    }

    paneTarget.setManualUpdate(isManualUpdate());
    v.setParent(this);
    paneTarget.setViewer(v, transitionAnimator, null);
  }

  protected abstract void setWidgetRenderTypeEx(RenderType type);

  private void setupRenderType(Widget cfg) {
    if (!autoResizeWidgets) {
      setWidgetRenderType(Utils.getRenderType(cfg.horizontalAlign.intValue(), cfg.verticalAlign.intValue()));
    }
  }
}
