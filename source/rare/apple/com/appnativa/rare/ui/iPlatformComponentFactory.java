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

import com.appnativa.rare.platform.apple.ui.view.CheckBoxView;
import com.appnativa.rare.platform.apple.ui.view.LabelView;
import com.appnativa.rare.platform.apple.ui.view.LineView;
import com.appnativa.rare.platform.apple.ui.view.ListView;
import com.appnativa.rare.platform.apple.ui.view.RadioButtonView;
import com.appnativa.rare.platform.apple.ui.view.TextAreaView;
import com.appnativa.rare.platform.apple.ui.view.TextFieldView;
import com.appnativa.rare.platform.apple.ui.view.View;
import com.appnativa.rare.spot.Bean;
import com.appnativa.rare.spot.CheckBox;
import com.appnativa.rare.spot.DocumentPane;
import com.appnativa.rare.spot.Label;
import com.appnativa.rare.spot.Line;
import com.appnativa.rare.spot.ListBox;
import com.appnativa.rare.spot.PasswordField;
import com.appnativa.rare.spot.PushButton;
import com.appnativa.rare.spot.RadioButton;
import com.appnativa.rare.spot.TextField;
import com.appnativa.rare.ui.text.iPlatformTextEditor;
import com.appnativa.rare.widget.iWidget;

/**
 * This interface defines the methods for creating components for the standard widgets.
 * Generally the methods are only responsible for creating the component. The caller is
 * responsible for configuring the component based on the specified configuration .
 * However, if the object created is a sub-type of the returned component then the method
 * is responsible for configuring all configurations options that are specific to the sub-type.
 * For example, the <code>getTextField()</code> method is responsible for configuring
 * <code>JTextArea</code> or <code>JTextField</code> specific options because a
 * <code>JTextComponent</code> is being returned.<p>
 *
 * The creator is also responsible for creating and setting and data model that that is specified
 * by the configuration.
 *
 * @author Don DeCoteau
 */
public interface iPlatformComponentFactory extends iComponentFactory {

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
   * Gets a button view
   *
   * @param context the widget context
   * @param cfg the configuration
   *
   * @return the view
   */
  View getButton(iWidget context, PushButton cfg);

  /**
   * Gets a checkbox view
   *
   * @param context the widget context
   * @param cfg the configuration
   *
   * @return the view
   */
  CheckBoxView getCheckBox(iWidget context, CheckBox cfg);

  iPlatformTextEditor getDocumentPane(iWidget context, DocumentPane cfg);

  /**
   * Gets a label view
   *
   * @param context the widget context
   * @param cfg the configuration
   *
   * @return the view
   */
  LabelView getLabel(iWidget context, Label cfg);

  /**
   * Gets a line view
   *
   * @param context the widget context
   * @param cfg the configuration
   *
   * @return the view
   */
  LineView getLine(iWidget context, Line cfg);

  /**
   * Gets a list view
   *
   * @param context the widget context
   * @param cfg the configuration
   *
   * @return the view
   */
  ListView getList(iWidget context, ListBox cfg);

  /**
   * Gets a password text field view
   *
   * @param context the widget context
   * @param cfg the configuration
   *
   * @return the view
   */
  TextFieldView getPasswordTextField(iWidget context, PasswordField cfg);

  /**
   * Gets a radio button view
   *
   * @param context the widget context
   * @param cfg the configuration
   *
   * @return the view
   */
  RadioButtonView getRadioButton(iWidget context, RadioButton cfg);

  /**
   * Gets a text area view
   *
   * @param context the widget context
   * @param cfg the configuration
   *
   * @return the view
   */
  TextAreaView getTextArea(iWidget context, TextField cfg);

  /**
   * Gets a text field view
   *
   * @param context the widget context
   * @param cfg the configuration
   *
   * @return the view
   */
  TextFieldView getTextField(iWidget context, TextField cfg);

  /**
   * Gets a toolbar button view
   *
   * @param context the widget context
   * @param cfg the configuration
   *
   * @return the view
   */
  View getToolbarButton(iWidget context, PushButton cfg);
}
