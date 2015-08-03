/*
 * @(#)DefaultEditor.java   2011-03-16
 *
 * Copyright (c) 2007-2009 appNativa Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.platform.swing.ui.view.spinner;

import javax.swing.JTextField;

import com.appnativa.rare.iConstants;
import com.appnativa.rare.platform.PlatformHelper;
import com.appnativa.rare.platform.swing.ui.text.TextEditorComponent;
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

  public void setTextChangeListener(iTextChangeListener l) {
    TextFieldView v = getTextField();

    v.removeTextChangeListener(l);
    v.addTextChangeListener(l);
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

  protected TextFieldView getTextField() {
    return (TextFieldView) editorView.getView();
  }

  @Override
  public Object removeSelectedData(boolean returnData) {
    Object o=returnData ? getTextField().getSelectedText() : null;
    TextEditorComponent.deleteSelection(getTextField());
    return o;
  }
}
