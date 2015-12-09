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

import com.appnativa.rare.Platform;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIFont;
import com.appnativa.rare.ui.calendar.DatePanel;
import com.appnativa.rare.ui.calendar.DateViewManager;
import com.appnativa.rare.ui.event.iChangeListener;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.widget.iWidget;

import java.util.Calendar;
import java.util.Date;
import java.util.EventObject;

/**
 *
 * @author Don DeCoteau
 */
public class DatePickerEditor implements iSpinnerEditor, iChangeListener{
  protected DatePanel        datePanel;
  protected DateViewManager  dateViewManager;
  protected SpinnerDateModel spinnerModel;
  Date                       minDate;
  Date                       maxDate;

  public DatePickerEditor(Context context, SpinnerDateModel model, iSpinner spinner) {
    spinnerModel    = model;
    dateViewManager = new DateViewManager();
    dateViewManager.addChangeListener(this);
    datePanel       = new DatePanel(Platform.getWindowViewer(), dateViewManager);
  }

  @Override
  public void clearFocus() {
    datePanel.getView().clearFocus();
  }

  public void finishConfiguring(iWidget context) {
    updateFromModel(false);
    dateViewManager.finishConfiguring(context);
  }

  @Override
  public void commitEdit() {
    Date d = ((Date) spinnerModel.getValue());

    if (!d.equals(datePanel.getDateViewManager().getValue())) {
      spinnerModel.setValue(d);
    }
  }

  @Override
  public void dispose() {
    if (datePanel != null) {
      datePanel.dispose();
    }

    datePanel    = null;
    spinnerModel = null;
  }

  @Override
  public iPlatformComponent getComponent() {
    return datePanel;
  }

  @Override
  public Object getValue() {
    return datePanel.getDateViewManager().getValue();
  }

  @Override
  public boolean isEditable() {
    return false;
  }

  @Override
  public boolean isEditorDirty() {
    return false;
  }

  @Override
  public boolean isTextField() {
    return false;
  }

  @Override
  public void modelChanged() {
    updateFromModel(true);
  }

  public void updateFromModel(boolean reset) {
    dateViewManager.setShowTime(spinnerModel.isShowTime());
    dateViewManager.setShowTimeOnly(spinnerModel.isShowTimeOnly());
    dateViewManager.setMinDate(getCalendar(spinnerModel.getMinimum()));
    dateViewManager.setMaxDate(getCalendar(spinnerModel.getMaximum()));
    dateViewManager.setDate((Date) spinnerModel.getValue());
    dateViewManager.initializeViews(datePanel.getView().getContext(), reset);
    datePanel.setContent();
  }

  @Override
  public Object removeSelectedData(boolean returnData) {
    return null;
  }

  @Override
  public void requestFocus() {
    datePanel.requestFocus();
  }

  @Override
  public void selectAll() {}

  @Override
  public void selectField() {}

  @Override
  public void setEditable(boolean editable) {}

  @Override
  public void setFont(UIFont font) {}

  @Override
  public void setForeground(UIColor color) {}

  @Override
  public void setValue(Object value) {
    if (value instanceof Date) {
      datePanel.getDateViewManager().setDate((Date) value);
    } else if (value instanceof Calendar) {
      datePanel.getDateViewManager().setDate((Calendar) value);
    }
  }

  protected Calendar getCalendar(Date date) {
    Calendar cal = null;

    if (date != null) {
      cal = Calendar.getInstance();
      cal.setTime(date);
    }

    return cal;
  }

  @Override
  public void stateChanged(EventObject e) {
    spinnerModel.setValue(dateViewManager.getValue());
  }
}
