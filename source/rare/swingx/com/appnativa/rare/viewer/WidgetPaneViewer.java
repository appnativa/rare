/*
 * @(#)WidgetPaneViewer.java   2009-01-21
 *
 * Copyright (c) appNativa Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.viewer;


import com.appnativa.rare.platform.swing.ui.view.FrameView;
import com.appnativa.rare.ui.Container;
import com.appnativa.rare.ui.RenderType;
import com.appnativa.rare.ui.iParentComponent;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.widget.iWidget;

/**
 * A viewer that wraps a widget allowing it to be placed directly into a target
 *
 * @version    0.3, 2007-05-14
 * @author     Don DeCoteau
 */
public class WidgetPaneViewer extends aWidgetPaneViewer {

  /**
   * Creates a new instance of WidgetPaneViewer
   */
  public WidgetPaneViewer() {
    super();
  }

  /**
   * Creates a new instance of WidgetPaneViewer
   *
   * @param parent the widget's parent
   */
  public WidgetPaneViewer(iContainer parent) {
    super(parent);
  }

/**
   *  Constructs a new instance
   *
   * @param parent the parent
   * @param comp the component to wrap
   */
  public WidgetPaneViewer(aContainer parent, iPlatformComponent comp) {
    super(parent,comp);
  }

  /**
   * Constructs a new instance
   *
   * @param comp the component to wrap
   */
  public WidgetPaneViewer(iPlatformComponent comp) {
    super(comp);
  }
  
  @Override
  protected void setWidgetRenderTypeEx(RenderType type) {
    if(widgetPanel.getView() instanceof FrameView) {
    	((FrameView)widgetPanel.getView()).setViewRenderType(type);
    }
  }
  @Override
  protected iParentComponent createWidgetPanel() {
    Container p=new Container(new FrameView());
    if (isDesignMode()) {
      p.setDefaultMinimumSize(100, 100, true);
    }
    return p;
  }
  @Override
  protected void adjustWidgetForPlatform(iWidget w) {
  }

}
