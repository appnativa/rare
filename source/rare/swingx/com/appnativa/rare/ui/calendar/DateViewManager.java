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

package com.appnativa.rare.ui.calendar;

import com.appnativa.rare.ui.iPlatformComponent;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.Date;

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
