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

import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SeekBar;

import com.appnativa.rare.platform.android.ui.view.LabelView;
import com.appnativa.rare.platform.android.ui.view.LineView;
import com.appnativa.rare.platform.android.ui.view.ListViewEx;
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
import com.appnativa.rare.spot.Slider;
import com.appnativa.rare.spot.Table;
import com.appnativa.rare.spot.TextField;
import com.appnativa.rare.spot.Tree;
import com.appnativa.rare.ui.text.iPlatformTextEditor;
import com.appnativa.rare.ui.tree.TreeViewEx;
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
   * Gets a button component
   *
   * @param context the widget context
   * @param cfg the configuration
   *
   * @return the view
   */
  View getButton(iWidget context, PushButton cfg);

  /**
   * Gets a checkbox component
   *
   * @param context the widget context
   * @param cfg the configuration
   *
   * @return the view
   */
  CompoundButton getCheckBox(iWidget context, CheckBox cfg);

  /**
   * Gets a collapsible component
   *
   * @param context the widget context
   * @param cfg the configuration
   *
   * @return the view
   */
  iCollapsible getCollapsible(iWidget context, CollapsibleInfo cfg);

  iPlatformTextEditor getDocumentPane(iWidget context, DocumentPane cfg);

  /**
   * Gets a label component
   *
   * @param context the widget context
   * @param cfg the configuration
   *
   * @return the view
   */
  LabelView getLabel(iWidget context, Label cfg);

  /**
   * Gets a line component
   *
   * @param context the widget context
   * @param cfg the configuration
   *
   * @return the view
   */
  LineView getLine(iWidget context, Line cfg);

  /**
   * Gets a list component
   *
   * @param context the widget context
   * @param cfg the configuration
   *
   * @return the view
   */
  ListViewEx getList(iWidget context, ListBox cfg);

  /**
   * Gets a password text field component
   *
   * @param context the widget context
   * @param cfg the configuration
   *
   * @return the view
   */
  EditText getPasswordTextField(iWidget context, PasswordField cfg);

  /**
   * Gets a progress bar component
   *
   * @param context the widget context
   * @param cfg the configuration
   *
   * @return the view
   */
  iProgressBar getProgressBar(iWidget context, ProgressBar cfg);

  /**
   * Gets a radio button component
   *
   * @param context the widget context
   * @param cfg the configuration
   *
   * @return the view
   */
  android.widget.RadioButton getRadioButton(iWidget context, RadioButton cfg);

  /**
   * Gets a slider component
   *
   * @param context the widget context
   * @param cfg the configuration
   *
   * @return the view
   */
  SeekBar getSlider(iWidget context, Slider cfg);

  /**
   * Gets a text field component
   *
   * @param context the widget context
   * @param cfg the configuration
   *
   * @return the view
   */
  EditText getTextField(iWidget context, TextField cfg);

  /**
   * Gets a toolbar button component
   *
   * @param context the widget context
   * @param cfg the configuration
   *
   * @return the view
   */
  View getToolbarButton(iWidget context, PushButton cfg);

  TreeViewEx getTree(iWidget context, Tree cfg);

  ListViewEx getTable(iWidget context, Table cfg);

  /**
   * Gets a tree component
   *
   * @param context the widget context
   * @param cfg the configuration
   *
   * @return the view
   */
}
