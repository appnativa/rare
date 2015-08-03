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

package com.appnativa.rare;

import com.appnativa.rare.spot.Widget;
import com.appnativa.rare.ui.iParentComponent;
import com.appnativa.rare.widget.iWidget;

/**
 * An interface for a customizer that can be used globally to customize widgets.
 * Can be set by calling
 * {@link iAppContext#setWidgetCustomizer(iWidgetCustomizer)} or by setting the
 * UI property "Rare.WidgetCustomizer".
 *
 * @author Don DeCoteau
 *
 */
public interface iWidgetCustomizer {

  /**
   * Called when the widget has been configured just prior to the onConfigure
   * event being fired.
   *
   * @param widget
   *          the widget that can be customized
   * @param cfg
   *          the configuration for the widget
   * @return an optional new component to use as the parent container for the
   *         widget. if specified the existing parent container will be added to
   *         the returned container.
   */
  iParentComponent widgetConfigured(iWidget widget, Widget cfg);

  /**
   * Called when the widget is disposed
   *
   * @param widget
   *          the widget being disposed
   */
  void widgetDisposed(iWidget widget);
}
