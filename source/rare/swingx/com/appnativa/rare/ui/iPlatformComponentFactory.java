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

import com.appnativa.rare.iPlatformAppContext;
import com.appnativa.rare.platform.swing.ui.view.CheckBoxView;
import com.appnativa.rare.platform.swing.ui.view.LabelView;
import com.appnativa.rare.platform.swing.ui.view.LineView;
import com.appnativa.rare.platform.swing.ui.view.ListView;
import com.appnativa.rare.platform.swing.ui.view.RadioButtonView;
import com.appnativa.rare.platform.swing.ui.view.SliderView;
import com.appnativa.rare.platform.swing.ui.view.TextAreaView;
import com.appnativa.rare.platform.swing.ui.view.TextFieldView;
import com.appnativa.rare.platform.swing.ui.view.TreeView;
import com.appnativa.rare.spot.Bean;
import com.appnativa.rare.spot.CheckBox;
import com.appnativa.rare.spot.CollapsibleInfo;
import com.appnativa.rare.spot.DocumentPane;
import com.appnativa.rare.spot.Label;
import com.appnativa.rare.spot.Line;
import com.appnativa.rare.spot.ListBox;
import com.appnativa.rare.spot.PasswordField;
import com.appnativa.rare.spot.ProgressBar;
import com.appnativa.rare.spot.PushButton;
import com.appnativa.rare.spot.RadioButton;
import com.appnativa.rare.spot.ScrollPane;
import com.appnativa.rare.spot.Slider;
import com.appnativa.rare.spot.Table;
import com.appnativa.rare.spot.TextField;
import com.appnativa.rare.spot.Tree;
import com.appnativa.rare.ui.table.TableView;
import com.appnativa.rare.ui.text.iPlatformTextEditor;
import com.appnativa.rare.widget.iWidget;

import javax.swing.AbstractButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public interface iPlatformComponentFactory extends iComponentFactory {

  /**
   * Displays a system alert that animates into view at the specified location, stays visible for 5 seconds
   * and then fades away. The property "Rare.systemAlert.duration" (in milliseconds) controls the duration
   * The valid locations are: <ul>
   *  <li>CENTER</li>
   *  <li>EAST</li>
   *  <li>NORTH</li>
   *  <li>NORTH_EAST</li>
   *  <li>NORTH_WEST</li>
   *  <li>SOUTH</li>
   *  <li>SOUTH_EAST</li>
   *  <li>SOUTH_WEST</li>
   *  <li>WEST</li>
   * </ul>
   *  <p> These locations are global constants in the scripting environment
   *
   * @param context the context
   * @param message the message can be a string, a widget, or a swing
   * @param displayLocation the display location.
   * @param icon a icon for the alert
   * @param actionable an actionable that will be used to close hide the alert
   * @param monitor the monitor on which to display the alert (1 -relative)
   */
  void systemAlert(iWidget context, Object message, int displayLocation, iPlatformIcon icon, iActionable actionable,
                   int monitor);

  /**
   * Set the application context that the factory was created for
   *
   * @param app the application instance
   */
  @Override
  void setAppContext(iPlatformAppContext app);

  /**
   * Gets a bean object. The object must be an instance of <code>iBeanIntegrator</code>
   * or <code>iPlatformComponent</code>
   *
   * @param context the widget context
   * @param cfg the configuration
   *
   * @return the view
   */
  Object getBean(iWidget context, Bean cfg);

  /**
   * Gets a button
   *
   * @param context the widget context
   * @param cfg the configuration
   *
   * @return the view
   */
  AbstractButton getButton(iWidget context, PushButton cfg);

  /**
   * Gets a checkbox
   *
   * @param context the widget context
   * @param cfg the configuration
   *
   * @return the view
   */
  CheckBoxView getCheckBox(iWidget context, CheckBox cfg);

  /**
   * Gets a collapsible
   *
   * @param context the widget context
   * @param cfg the configuration
   *
   * @return the view
   */
  @Override
  iCollapsible getCollapsible(iWidget context, CollapsibleInfo cfg);

  /**
   * Gets a label
   *
   * @param context the widget context
   * @param cfg the configuration
   *
   * @return the view
   */
  LabelView getLabel(iWidget context, Label cfg);

  /**
   * Gets a line
   *
   * @param context the widget context
   * @param cfg the configuration
   *
   * @return the view
   */
  LineView getLine(iWidget context, Line cfg);

  /**
   * Gets a list
   *
   * @param context the widget context
   * @param cfg the configuration
   *
   * @return the view
   */
  ListView getList(iWidget context, ListBox cfg);

  /**
   * Gets a password text field
   *
   * @param context the widget context
   * @param cfg the configuration
   *
   * @return the view
   */
  JTextField getPasswordTextField(iWidget context, PasswordField cfg);

  /**
   * Gets a progress bar
   *
   * @param context the widget context
   * @param cfg the configuration
   *
   * @return the view
   */
  iProgressBar getProgressBar(iWidget context, ProgressBar cfg);

  /**
   * Gets a radio button
   *
   * @param context the widget context
   * @param cfg the configuration
   *
   * @return the view
   */
  RadioButtonView getRadioButton(iWidget context, RadioButton cfg);

  /**
   * Gets a table view
   *
   * @param context the widget context
   * @param cfg the configuration
   *
   * @return the view
   */
  TableView getTable(iWidget context, Table cfg);

  /**
   * Gets a slider
   *
   * @param context the widget context
   * @param cfg the configuration
   *
   * @return the view
   */
  SliderView getSlider(iWidget context, Slider cfg);

  /**
   * Gets a text field
   *
   * @param context the widget context
   * @param cfg the configuration
   *
   * @return the view
   */
  TextFieldView getTextField(iWidget context, TextField cfg);

  /**
   * Gets a text area
   *
   * @param context the widget context
   * @param cfg the configuration
   *
   * @return the view
   */
  TextAreaView getTextArea(iWidget context, TextField cfg);

  /**
   * Gets a toolbar button
   *
   * @param context the widget context
   * @param cfg the configuration
   *
   * @return the view
   */
  AbstractButton getToolbarButton(iWidget context, PushButton cfg);

  TreeView getTree(iWidget context, Tree cfg);

  /**
   * Resolves a rendering .
   * This method will handle the unwrapping of rendering components
   * that may be done be special rendering classes
   * @param widget the widget the  was rendering on
   * @param rc the rendering  returned by a get*RenderingComponent method
   * @return the unwrapped
   */
  iPlatformComponent resolveRenderingComponent(iWidget widget, iPlatformComponent rc);

  iPlatformTextEditor getDocumentPane(iWidget context, DocumentPane cfg);

  JScrollPane getScrollPane(iWidget context, ScrollPane cfg);
}
