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
   *    Constructs a new instance
   *  
   *   @param parent the parent
   *   @param comp the component to wrap
   */
  public WidgetPaneViewer(aContainer parent, iPlatformComponent comp) {
    super(parent, comp);
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
    if (widgetPanel.getView() instanceof FrameView) {
      ((FrameView) widgetPanel.getView()).setViewRenderType(type);
    }
  }

  @Override
  protected iParentComponent createWidgetPanel() {
    Container p = new Container(new FrameView());

    if (isDesignMode()) {
      p.setDefaultMinimumSize(100, 100, true);
    }

    return p;
  }

  @Override
  protected void adjustWidgetForPlatform(iWidget w) {}
}
