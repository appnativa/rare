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

package com.appnativa.rare.platform.swing.ui.view.spinner;

import javax.swing.JTextField;

import com.appnativa.rare.iConstants;
import com.appnativa.rare.platform.PlatformHelper;
import com.appnativa.rare.platform.swing.ui.text.TextEditorComponent;
import com.appnativa.rare.platform.swing.ui.view.FormattedTextFieldView;
import com.appnativa.rare.platform.swing.ui.view.TextFieldView;
import com.appnativa.rare.ui.ActionComponent;
import com.appnativa.rare.ui.BorderUtils;
import com.appnativa.rare.ui.RenderableDataItem.HorizontalAlign;
import com.appnativa.rare.ui.RenderableDataItem.VerticalAlign;
import com.appnativa.rare.ui.listener.iTextChangeListener;
import com.appnativa.rare.ui.spinner.iSpinnerModel;

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
    JTextField v = getTextField();

    if (spinnerModel.isEditable() != v.isEditable()) {
      v.setEditable(spinnerModel.isEditable());
    }
  }

  @Override
  public void setEditable(boolean editable) {
    (getTextField()).setEditable(editable);
  }

  protected void updateTextChangeListener(iTextChangeListener l) {
    JTextField tf = getTextField();
    if(tf instanceof TextFieldView) {
      TextFieldView v=(TextFieldView)tf;
      v.removeTextChangeListener(l);
      v.addTextChangeListener(l);
    }
    else if(tf instanceof FormattedTextFieldView) {
      FormattedTextFieldView v=(FormattedTextFieldView)tf;
      v.removeTextChangeListener(l);
      v.addTextChangeListener(l);
    }
  }

  public void setTextChangeListener(iTextChangeListener l) {
    JTextField tf = getTextField();
    if(tf instanceof TextFieldView) {
      TextFieldView v=(TextFieldView)tf;
      v.removeTextChangeListener(l);
      v.addTextChangeListener(l);
    }
    else if(tf instanceof FormattedTextFieldView) {
      FormattedTextFieldView v=(FormattedTextFieldView)tf;
      v.removeTextChangeListener(l);
      v.addTextChangeListener(l);
    }
  }
  @Override
  public void setValue(Object value) {
    String     s = spinnerModel.toString(value);
    JTextField v = getTextField();

    v.setText(s);
    v.selectAll();
  }

  @Override
  public Object getValue() {
    return editorView.getText().toString();
  }

  @Override
  public boolean isEditable() {
    return (getTextField()).isEditable();
  }

  @Override
  public boolean isTextField() {
    return true;
  }

  protected ActionComponent createEditorView() {
    return new ActionComponent(new TextFieldView());
  }

  protected void customizeEditor() {
    ActionComponent v = editorView;

    v.putClientProperty(iConstants.RARE_MIN_WIDTH_PROPERTY, "2ch");
    v.setBorder(BorderUtils.EMPTY_BORDER);
    v.setOpaque(false);
    getTextField().setEditable(spinnerModel.isEditable());
    v.setAlignment(HorizontalAlign.RIGHT, VerticalAlign.CENTER);
    v.addActionListener(this);
    v.addFocusListener(this);
  }
  
  public void selectAll() {
    getTextField().selectAll();
  }

  protected JTextField getTextField() {
    return (JTextField) editorView.getView();
  }

  @Override
  public Object removeSelectedData(boolean returnData) {
    Object o = returnData
               ? getTextField().getSelectedText()
               : null;

    TextEditorComponent.deleteSelection(getTextField());

    return o;
  }

  @Override
  public void selectField() {
   selectAll();
    
  }
}
