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

import android.annotation.TargetApi;

import android.content.Context;

import com.appnativa.rare.Platform;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIFont;
import com.appnativa.rare.ui.calendar.DatePanel;
import com.appnativa.rare.ui.calendar.DateViewManager;
import com.appnativa.rare.ui.iPlatformComponent;

import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Don DeCoteau
 */
@TargetApi(11)
public class DatePickerEditor implements iSpinnerEditor {
  protected DatePanel        editorView;
  protected SpinnerDateModel spinnerModel;
  Date                       minDate;
  Date                       maxDate;

  public DatePickerEditor(Context context, SpinnerDateModel model, iSpinner spinner) {
    spinnerModel = model;
    editorView   = new DatePanel(Platform.getWindowViewer(), new DateViewManager());
  }

  public void clearFocus() {
    editorView.getView().clearFocus();
  }

  public void commitEdit() {
    Date d = ((Date) spinnerModel.getValue());

    if (!d.equals(editorView.getDateViewManager().getValue())) {
      spinnerModel.setValue(d);
    }
  }

  public void modelChanged() {
//      
//    Date  n = (Date)spinnerModel.getMinimum();
//
//    if (n != minDate) {
//      editorView.setMinDate(Cal)(n);
//    }
//
//    n = Math.min(spinnerModel.getMaximum().intValue(), Integer.MAX_VALUE);
//
//    if (n != editorView.getMaxValue()) {
//      editorView.setMaxValue(n);
//    }
//
//    n = ((SNumber) spinnerModel.getValue()).intValue();
//    n = Math.max(n, Integer.MIN_VALUE);
//    n = Math.min(n, Integer.MAX_VALUE);
//
//    if (n != editorView.getValue()) {
//      editorView.setValue(n);
//    }
  }

  public void requestFocus() {
    editorView.requestFocus();
  }

  public void setEditable(boolean editable) {}

  public void setFont(UIFont font) {}

  public void setForeground(UIColor color) {}

  public void setValue(Object value) {
    if (value instanceof Date) {
      editorView.getDateViewManager().setDate((Date) value);
    } else if (value instanceof Calendar) {
      editorView.getDateViewManager().setDate((Calendar) value);
    }
  }

  public Object getValue() {
    return editorView.getDateViewManager().getValue();
  }

  public iPlatformComponent getComponent() {
    return editorView;
  }

  public boolean isEditable() {
    return false;
  }

  public boolean isEditorDirty() {
    return false;
  }

  public void dispose() {
    if (editorView != null) {
      editorView.dispose();
    }

    editorView   = null;
    spinnerModel = null;
  }

  public boolean isTextField() {
    return false;
  }

  @Override
  public Object removeSelectedData(boolean returnData) {
    return null;
  }
}
