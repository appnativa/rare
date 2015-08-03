/*
 * @(#)DateViewManager.java
 * 
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.ui.calendar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import com.appnativa.rare.ui.iPlatformComponent;

public class DateViewManager extends aDateViewManager {
  DatePicker datePicker;

  public DateViewManager() {}

  @Override
  public iPlatformComponent getDatePickerComponent() {
    if (datePicker == null) {
      datePicker = new DatePicker();
      datePicker.getMonthView().addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          onDateChanged(datePicker.getMonthView().getSelectionDate());
        }
      });
    }

    return datePicker;
  }

  protected void onDateChanged(Date date) {
    if (ignoreChangeEvent) {
      ignoreChangeEvent = false;

      return;
    }

    this.date.setTime(date);
    stringValue = null;
    if (okButton == null) {
      fireEvent();
    }

    valueSet = true;
  }
}
