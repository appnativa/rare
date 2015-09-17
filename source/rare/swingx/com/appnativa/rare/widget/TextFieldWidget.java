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

import com.appnativa.rare.platform.ActionHelper;
import com.appnativa.rare.platform.swing.ui.text.TextEditorComponent;
import com.appnativa.rare.platform.swing.ui.text.UndoManagerEx;
import com.appnativa.rare.platform.swing.ui.view.TextAreaView;
import com.appnativa.rare.platform.swing.ui.view.TextFieldView;
import com.appnativa.rare.spot.PasswordField;
import com.appnativa.rare.spot.TextField;
import com.appnativa.rare.ui.BorderUtils;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.aWidgetListener;
import com.appnativa.rare.ui.iActionable;
import com.appnativa.rare.ui.text.iPlatformTextEditor;
import com.appnativa.rare.viewer.iContainer;
import com.appnativa.rare.viewer.iViewer;

import javax.swing.JComponent;
import javax.swing.text.JTextComponent;

/**
 *  A widget that allows for the displaying and or editing
 *  of one or more lines of text.
 *
 *  @author Don DeCoteau
 */
public class TextFieldWidget extends aTextFieldWidget implements iActionable {

  /**
   * Constructs a new instance
   *
   * @param parent the widget's parent
   */
  public TextFieldWidget(iContainer parent) {
    super(parent);
  }

  /**
   * Sets number of characters columns to size the field for (use zero to let the widget decide)
   *
   * @param characters the number of characters
   */
  @Override
  public void setVisibleCharacters(int characters) {
    visibleCharacters = characters;

    JComponent c = getDataView();

    if (c instanceof TextFieldView) {
      ((TextFieldView) c).setColumns(visibleCharacters);
    } else if (c instanceof TextAreaView) {
      ((TextAreaView) c).setColumns(visibleCharacters);
    }
  }

  @Override
  protected iPlatformTextEditor createEditorAndComponents(iViewer viewer, PasswordField cfg) {
    TextEditorComponent e =
      new TextEditorComponent(getAppContext().getComponentCreator().getPasswordTextField(getViewer(), cfg));

    e.setBorder(BorderUtils.getTextFieldBorder());
    e.setBackground(UIColor.WHITE);
    formComponent = dataComponent = e;

    return e;
  }

  @Override
  protected iPlatformTextEditor createEditorAndComponents(iViewer viewer, TextField cfg) {
    TextEditorComponent e = new TextEditorComponent(getAppContext().getComponentCreator().getTextField(getViewer(),
                              cfg));

    formComponent = dataComponent = e;
    e.setBorder(BorderUtils.getTextFieldBorder());
    e.setBackground(UIColor.WHITE);

    int limit = cfg.undoLimit.intValue();

    if (limit != 0) {
      UndoManagerEx um = new UndoManagerEx(this);

      if (limit == -1) {
        um.setLimit(0);
      } else {
        um.setLimit(limit);
      }

      ((JTextComponent) e.getView()).getDocument().addUndoableEditListener(um);
      ActionHelper.registerUndoManager(e, um);
    }

    return e;
  }

  @Override
  protected void initializeListeners(aWidgetListener l) {
    super.initializeListeners(l);

    if ((l != null) && l.isChangeEventEnabled()) {
      JComponent c = getDataView();

      if (c instanceof TextFieldView) {
        ((TextFieldView) c).addTextChangeListener(l);
      }
    }
  }
}
