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

import android.widget.NumberPicker;

import com.appnativa.rare.ui.ActionComponent;
import com.appnativa.rare.ui.SpinnerComponent;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIFont;
import com.appnativa.rare.ui.event.iChangeListener;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.util.SNumber;

import java.util.EventObject;

/**
 *
 * @author Don DeCoteau
 */
public class NumberPickerEditor implements iSpinnerEditor, NumberPicker.OnValueChangeListener, iChangeListener {
  protected NumberPicker       editorView;
  protected SpinnerNumberModel spinnerModel;
  private iPlatformComponent   editorComponent;
  private SpinnerComponent     spinner;

  public NumberPickerEditor(Context context, SpinnerNumberModel model, SpinnerComponent spinner) {
    spinnerModel = model;
    editorView   = new NumberPicker(context);
    editorView.setOnValueChangedListener(this);
    editorView.setWrapSelectorWheel(spinnerModel.isCircular());
    this.spinner = spinner;
    model.addChangeListener(this);
    modelChanged();
  }

  public void clearFocus() {
    editorView.clearFocus();
  }

  public void commitEdit() {
    int n = ((SNumber) spinnerModel.getValue()).intValue();

    if (n != editorView.getValue()) {
      spinnerModel.setValue(n);
    }
  }

  public void dispose() {
    if (editorComponent != null) {
      editorComponent.dispose();
    }

    editorComponent = null;
    editorView      = null;
    spinnerModel    = null;
  }

  public void modelChanged() {
    int n = Math.max(spinnerModel.getMinimum().intValue(), 0);

    if (n != editorView.getMinValue()) {
      editorView.setMinValue(n);
    }

    n = Math.min(spinnerModel.getMaximum().intValue(), Integer.MAX_VALUE);

    if (n != editorView.getMaxValue()) {
      editorView.setMaxValue(n);
    }

    n = ((SNumber) spinnerModel.getValue()).intValue();
    n = Math.max(n, Integer.MIN_VALUE);
    n = Math.min(n, Integer.MAX_VALUE);

    if (n != editorView.getValue()) {
      editorView.setValue(n);
    }
  }

  public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
    spinner.valueChanged(newVal);
  }

  public void requestFocus() {
    editorView.requestFocus();
  }

  public void setEditable(boolean editable) {}

  public void setFont(UIFont font) {}

  public void setForeground(UIColor color) {}

  public void setValue(Object value) {
    if (value instanceof Number) {
      editorView.setValue(((Number) value).intValue());
    } else {
      editorView.setValue((value == null)
                          ? 0
                          : SNumber.intValue(value.toString()));
    }
  }

  public iPlatformComponent getComponent() {
    if (editorComponent == null) {
      editorComponent = new ActionComponent(editorView);
    }

    return editorComponent;
  }

  public Object getValue() {
    return editorView.getValue();
  }

  public boolean isEditable() {
    return false;
  }

  public boolean isEditorDirty() {
    return false;
  }

  public void stateChanged(EventObject e) {
    modelChanged();
  }

  public boolean isTextField() {
    return false;
  }

  @Override
  public Object removeSelectedData(boolean returnData) {
    return null;
  }
}
