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

package com.appnativa.rare.ui.spinner;

import com.appnativa.rare.platform.PlatformHelper;
import com.appnativa.rare.platform.apple.ui.view.TextFieldView;
import com.appnativa.rare.ui.RenderableDataItem.HorizontalAlign;
import com.appnativa.rare.ui.RenderableDataItem.VerticalAlign;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIInsets;
import com.appnativa.rare.ui.listener.iTextChangeListener;

/**
 *
 * @author Don DeCoteau
 */
public class DefaultEditor extends aSpinnerEditor {
  public DefaultEditor(iSpinnerModel model) {
    super(model);
    editorView = createEditorView();
    customizeEditor();
  }

  @Override
  public void commitEdit() {
    try {
      String s = editorView.getText().toString();
      Object v = spinnerModel.fromString(s);

      if (v == null) {
        PlatformHelper.performHapticFeedback(editorView);
        editorView.setText(spinnerModel.toString(spinnerModel.getValue()));

        return;
      }

      spinnerModel.setValue(v);
    } catch(IllegalArgumentException ex) {
      PlatformHelper.performHapticFeedback(editorView);
      editorView.setText(spinnerModel.toString(spinnerModel.getValue()));
    } finally {
      setEditorDirty(false);
    }
  }

  @Override
  public void modelChanged() {
    TextFieldView v = (TextFieldView) editorView;

    if (spinnerModel.isEditable() != v.isEditable()) {
      v.setEditable(spinnerModel.isEditable());
    }
  }

  @Override
  public void setEditable(boolean editable) {
    ((TextFieldView) editorView).setEditable(editable);
  }

  public void setTextChangeListener(iTextChangeListener textChangeListener) {
    TextFieldView v = (TextFieldView) editorView;

    if (v.getChangeListener() == null) {
      v.setTextChangeListener(textChangeListener);
    } else {
      this.appTextChangeListener = textChangeListener;
    }
  }

  @Override
  public void setValue(Object value) {
    String        s = spinnerModel.toString(value);
    TextFieldView v = (TextFieldView) editorView;

    v.setText(s);
    v.selectAll();
  }

  @Override
  public Object getValue() {
    return editorView.getText().toString();
  }

  @Override
  public boolean isEditable() {
    return ((TextFieldView) editorView).isEditable();
  }

  @Override
  public boolean isTextField() {
    return true;
  }

  protected TextFieldView createEditorView() {
    return new TextFieldView();
  }

  @Override
  public Object removeSelectedData(boolean returnData) {
    TextFieldView v = (TextFieldView) editorView;
    Object        o = returnData
                      ? v.getSelectionString()
                      : null;

    v.deleteSelection();

    return o;
  }

  protected void customizeEditor() {
    TextFieldView v = (TextFieldView) editorView;

    v.setBackgroundColorEx(UIColor.TRANSPARENT);
    v.setMargin(new UIInsets(0));
    v.setPrefColumnCount(3);
    v.removeNativeBorder();
    v.makeTransparent();
    v.setEditable(spinnerModel.isEditable());
    v.setTextAlignment(HorizontalAlign.RIGHT, VerticalAlign.CENTER);
    v.setActionListener(this);
    v.setFocusListener(this);
  }

  @Override
  public void selectAll() {
    TextFieldView v = (TextFieldView) editorView;

    v.selectAll();
  }

  @Override
  public void selectField() {
    selectAll();
  }
}
