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

package com.appnativa.rare.ui;

import com.appnativa.rare.iConstants;
import com.appnativa.rare.ui.event.EventBase;
import com.appnativa.rare.widget.iWidget;

import java.util.EventObject;

/**
 * This class represents an interface that can be used as an event handler for a
 * widget.
 *
 * <p>
 * It is used in circumstances where you want to have a class that handles
 * events for one of more widgets.
 * </p>
 * <p>
 * It is also useful in, performance sensitive, circumstances where you don't
 * want to have JavaScript code being called.
 * </p>
 * </p> In the markup file you would use a 'class:' prefix followed by the name
 * of the class that implements this interface (e.g.
 * <code>onAction="class:com.appnativa.demo.FormEventHandler" </code> ).
 * <p>
 * You can also append a method name to the class specifier and the named method
 * taking the same arguments as the onEvent method will be invoked (e.g.
 * <code>onAction="class:com.appnativa.demo.FormEventHandler#okPressed" </code>
 * ). <br/>
 * If you append the hash (#) but omit the method name then the name of the
 * widget will be used ad the method name <br/>
 * You can also embed "{widget}" and "{event}" in the method name and the name
 * of the widget and the name of the event will be used to construct the method
 * name. <br/>
 * <br/>
 * You can also append query parameters in the same format as URL query
 * parameters are specified. These parameters can be accessed via the
 * {@link EventBase#getQueryMap()} method on the event object.
 * </p>
 *
 * <p>
 * There is a runtime property string called 'Rare.class.defaultPackage' that
 * can be defined in the {@code lookAndFeelPropertiesURL} section of the
 * application. When specified, all class names without a package will be
 * assumed to be in this package.
 * <p>
 *
 * @author Don DeCoteau
 */
public interface iEventHandler {

  /**
   * Called to handle the event
   *
   * @param eventName
   *          the name of the event. This vale can be tested for equality
   *          against values in {@link iConstants} EVENT_* values
   * @param widget
   *          the widget the event is for
   * @param event
   *          the event object associated with the event
   */
  void onEvent(String eventName, iWidget widget, EventObject event);
}
