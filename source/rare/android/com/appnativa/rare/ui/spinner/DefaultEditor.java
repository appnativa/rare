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

import android.content.Context;
import android.content.res.ColorStateList;

import android.graphics.Color;

import android.view.Gravity;
import android.view.HapticFeedbackConstants;
import android.view.KeyEvent;
import android.view.View;

import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.appnativa.rare.Platform;
import com.appnativa.rare.platform.android.ui.NullDrawable;
import com.appnativa.rare.platform.android.ui.util.AndroidHelper;
import com.appnativa.rare.platform.android.ui.view.EditTextEx;
import com.appnativa.rare.ui.ActionComponent;
import com.appnativa.rare.ui.FontUtils;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIFont;
import com.appnativa.rare.ui.iPlatformComponent;

/**
 *
 * @author Don DeCoteau
 */
public class DefaultEditor implements iSpinnerEditor, View.OnFocusChangeListener, OnEditorActionListener {
  protected EditTextEx       editorView;
  protected iSpinnerModel    spinnerModel;
  private boolean            editorDirty;
  private iPlatformComponent editorComponent;

  public DefaultEditor(Context context, iSpinnerModel model) {
    spinnerModel = model;
    editorView   = createEditorView(context);
    customizeEditor();
  }

  public void clearFocus() {
    editorView.clearFocus();
  }

  public void commitEdit() {
    try {
      String s = editorView.getText().toString();
      Object v = spinnerModel.fromString(s);

      if (v == null) {
        editorView.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP);
        editorView.setText(spinnerModel.toString(spinnerModel.getValue()));

        return;
      }

      spinnerModel.setValue(v);
    } catch(IllegalArgumentException ex) {
      editorView.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP);
      editorView.setText(spinnerModel.toString(spinnerModel.getValue()));
    } finally {
      setEditorDirty(false);
    }
  }

  public void modelChanged() {
    if (spinnerModel.isEditable() != editorView.isEditable()) {
      editorView.setEditable(spinnerModel.isEditable());
    }
  }

  public void onFocusChange(View view, boolean hasFocus) {
    if (!hasFocus && (view == editorView)) {
      commitEdit();
    }
  }

  public void requestFocus() {
    editorView.requestFocus();
  }

  public void setEditable(boolean editable) {
    editorView.setEditable(editable);
  }

  public void setEditorDirty(boolean editorDirty) {
    this.editorDirty = editorDirty;
  }

  public void setFont(UIFont font) {
    if (font != null) {
      font.setupTextView(editorView);
    }
  }

  public void setForeground(UIColor color) {
    if ((editorView != null) && (color != null)) {
      color.setTextColor(editorView);
    }
  }

  public void setValue(Object value) {
    String v = spinnerModel.toString(value);

    editorView.setText(v);
    editorView.selectAll();
  }

  public ColorStateList getTextColors() {
    return editorView.getTextColors();
  }

  public float getTextSize() {
    return editorView.getTextSize();
  }

  public String getValue() {
    return editorView.getText().toString();
  }

  public iPlatformComponent getComponent() {
    if (editorComponent == null) {
      editorComponent = new ActionComponent(editorView);
    }

    return editorComponent;
  }

  public boolean isEditable() {
    return editorView.isEditable();
  }

  public boolean isEditorDirty() {
    return editorDirty;
  }

  protected EditTextEx createEditorView(Context context) {
    EditText e      = null;
    Integer  layout = Platform.getUIDefaults().getInteger("Rare.EditText.layout");

    if (layout != null) {
      e = (EditText) AndroidHelper.getResourceComponentView(layout);
    }

    if (e instanceof EditTextEx) {
      return (EditTextEx) e;
    }

    return EditTextEx.createEditText(context);
  }

  protected void customizeEditor() {
    editorView.setBackgroundDrawable(NullDrawable.getInstance());

    int[][] states = new int[2][];

    states[0] = new int[] { -android.R.attr.state_enabled };
    states[1] = new int[0];
    editorView.setTextColor(new ColorStateList(states, new int[] { Color.LTGRAY, Color.BLACK }));
    editorView.setMinimumWidth(FontUtils.getCharacterWidth(FontUtils.getDefaultFont()) * 2);
    editorView.setPadding(0, 0, 0, 0);
    editorView.setEditable(spinnerModel.isEditable());
    editorView.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
    editorView.setOnFocusChangeListener(this);
    editorView.setOnEditorActionListener(this);
  }

  public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2) {
    commitEdit();

    return false;
  }

  public void dispose() {
    if (editorComponent != null) {
      editorComponent.dispose();
    }

    editorComponent = null;
    editorView      = null;
    spinnerModel    = null;
  }

  public boolean isTextField() {
    return true;
  }

  @Override
  public Object removeSelectedData(boolean returnData) {
    Object o = returnData
               ? editorView.getSelectionString()
               : null;

    editorView.deleteSelection();

    return o;
  }
}
