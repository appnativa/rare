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

import com.appnativa.rare.iConstants;
import com.appnativa.rare.net.ActionLink;
import com.appnativa.rare.net.iURLConnection;
import com.appnativa.rare.platform.PlatformHelper;
import com.appnativa.rare.spot.Bean;
import com.appnativa.rare.spot.Widget;
import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.ui.aWidgetListener;
import com.appnativa.rare.ui.event.DataEvent;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.viewer.iContainer;

import java.io.IOException;

import java.util.EventObject;

/**
 * This class represents the configuration information for a
 * bean (arbitrary/component) that can be specified as a class
 * name or the url for the bean
 * <p>
 * The specified class must be an instance of <CODE>Component</CODE> or <CODE>iBeanIntegrator</CODE>
 * </p>
 *
 * @author Don DeCoteau
 */
public class BeanWidget extends aPlatformWidget {

  /** handle to the Bean integrator */
  private iBeanIntegrator beanIntegrator;
  private DataEvent       dataEvent;
  private EventObject     event;

  /**
   * Constructs a new instance
   *
   * @param parent the widget's parent
   */
  public BeanWidget(iContainer parent) {
    super(parent);
    widgetType = WidgetType.Bean;
  }

  /**
   * Creates a new instance
   *
   * @param parent the widget's parent
   * @param comp the component
   */
  public BeanWidget(iContainer parent, iPlatformComponent comp) {
    this(null, parent, comp, comp, null);
  }

  /**
   * Creates a new instance
   *
   * @param parent the widget's parent
   * @param fcomp the form component
   * @param dcomp the data component
   * @param wc widget configuration to use to configure the bean
   */
  public BeanWidget(iContainer parent, iPlatformComponent fcomp, iPlatformComponent dcomp, Widget wc) {
    this(null, parent, fcomp, dcomp, wc);
  }

  /**
   * Creates a new instance
   *
   * @param name the name of the widget
   * @param parent the widget's parent
   * @param fcomp the form component
   * @param dcomp the data component
   * @param wc widget configuration to use to configure the bean
   */
  public BeanWidget(String name, iContainer parent, iPlatformComponent fcomp, iPlatformComponent dcomp, Widget wc) {
    super(parent);

    if (wc == null) {
      wc = new Widget();
      wc.name.setValue(name);
    }

    if (fcomp instanceof iBeanIntegrator) {
      beanIntegrator = ((iBeanIntegrator) fcomp);
    }

    if (dcomp instanceof iBeanIntegrator) {
      beanIntegrator = ((iBeanIntegrator) dcomp);
    }

    dataComponent = dcomp;
    formComponent = (fcomp == null)
                    ? dcomp
                    : fcomp;
    widgetType    = WidgetType.Bean;
    configure(wc, true, false, true, true);
  }

  @Override
  public void addParsedRow(RenderableDataItem row) {
    aWidgetListener wl = getWidgetListener();

    if ((wl != null) && wl.isEnabled(iConstants.EVENT_ITEM_ADDED)) {
      if (dataEvent == null) {
        dataEvent = new DataEvent(getContainerComponent(), row);
      } else {
        dataEvent.setData(row);
      }

      wl.execute(iConstants.EVENT_ITEM_ADDED, dataEvent);
    }
  }

  @Override
  public void configure(Widget cfg) {
    configure((Bean) cfg);
  }

  @Override
  public void dispose() {
    if (beanIntegrator != null) {
      beanIntegrator.disposing();
    }

    super.dispose();
    beanIntegrator = null;
  }

  @Override
  public void finishedParsing() {
    aWidgetListener wl = getWidgetListener();

    if ((wl != null) && wl.isEnabled(iConstants.EVENT_FINISHED_LOADING)) {
      if (event == null) {
        event = new EventObject(getContainerComponent());
      }

      wl.execute(iConstants.EVENT_FINISHED_LOADING, event);
    }
  }

  @Override
  public void handleActionLink(ActionLink link, boolean deferred) {
    if (isDesignMode()) {
      return;
    }

    if ((beanIntegrator == null) ||!beanIntegrator.wantsURLConnection()) {
      super.handleActionLink(link, deferred);
    } else {
      try {
        sourceURL = null;

        iURLConnection conn = link.getConnection();

        widgetDataLink = link;
        sourceURL      = link.getURL(this);
        beanIntegrator.handleConnection(conn);
      } catch(IOException ex) {
        handleException(ex);
      } finally {
        link.close();
      }
    }
  }

  /**
   * Sets the integrator for the bean
   *
   * @param bi the integrator for the bean
   */
  public void setBeanIntegrator(iBeanIntegrator bi) {
    this.beanIntegrator = bi;
  }

  @Override
  public void setValue(Object value) {
    if (beanIntegrator != null) {
      beanIntegrator.setValue(value);
    } else {
      PlatformHelper.setText(dataComponent, (value == null)
              ? ""
              : value.toString());
    }
  }

  /**
   * Gets the integrator for the bean
   *
   * @return the integrator for the bean
   */
  public iBeanIntegrator getBeanIntegrator() {
    return beanIntegrator;
  }

  @Override
  public iPlatformComponent getDataComponent() {
    if (beanIntegrator != null) {
      return beanIntegrator.getDataComponent();
    }

    return super.getDataComponent();
  }

  /**
   * Returns the selected items or <code>null</code> if no
   * items are selected.
   *
   * @return the selected objects
   */
  public Object[] getSelectedObjects() {
    if (beanIntegrator != null) {
      return beanIntegrator.getSelectedObjects();
    }

    return null;
  }

  @Override
  public Object getSelection() {
    if (beanIntegrator != null) {
      return beanIntegrator.getValue();
    }

    return null;
  }

  /**
   * Configures the bean
   *
   * @param cfg the bean configuration
   */
  protected void configure(Bean cfg) {
    iPlatformComponent fcomp, dcomp;
    Object             bean = PlatformHelper.createBean(this, cfg);

    if (bean instanceof iBeanIntegrator) {
      beanIntegrator = (iBeanIntegrator) bean;
      dcomp          = beanIntegrator.getDataComponent();
      fcomp          = beanIntegrator.getContainer();
    } else {
      fcomp = dcomp = PlatformHelper.resolveBeanComponent(bean);
    }

    formComponent = fcomp;
    dataComponent = dcomp;

    String name = cfg.name.getValue();

    if ((name != null) && name.startsWith("Rare.bean")) {
      cfg.name.setValue((String) null);
    }

    try {
      configure(cfg, true, false, true, true);
    } finally {
      cfg.name.setValue(name);
    }

    if (beanIntegrator != null) {
      beanIntegrator.configure(this, cfg);
    }

    if ((beanIntegrator != null) && beanIntegrator.wantsURLConnection()) {
      handleDataURL(cfg);
    }

    fireConfigureEvent(cfg, iConstants.EVENT_CONFIGURE);
  }

  @Override
  protected void initializeListeners(aWidgetListener l) {
    super.initializeListeners(l);

    if (beanIntegrator != null) {
      beanIntegrator.initializeListeners(l);
    }
  }
}
