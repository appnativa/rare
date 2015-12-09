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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import java.util.EventObject;

import com.appnativa.rare.Platform;
import com.appnativa.rare.spot.TimeSpinner;
import com.appnativa.rare.spot.Widget;
import com.appnativa.rare.ui.LinearPanel;
import com.appnativa.rare.ui.SpinnerComponent;
import com.appnativa.rare.ui.UIScreen;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.event.iChangeListener;
import com.appnativa.rare.widget.SpinnerWidget;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.CellConstraints.Alignment;

public class DateViewManager extends aDateViewManager implements iChangeListener {
  DatePicker       datePicker;
  SpinnerComponent timePicker;
  LinearPanel      content;
  Calendar         tempCal;

  public DateViewManager() {}

  @Override
  public iPlatformComponent getDatePickerComponent() {
    Alignment hAlign=CellConstraints.CENTER;
    if (showTime) {
      if (timePicker == null) {
        TimeSpinner cfg = (TimeSpinner) Platform.getWindowViewer().createConfigurationObject("TimeSpinner",
                            "Rare.dateChooser.timeSpinner");
        SpinnerWidget sw = new SpinnerWidget(Platform.getWindowViewer());
        sw.configure(cfg);
        timePicker = (SpinnerComponent) sw.getContainerComponent();
        timePicker.addChangeListener(this);
        if(cfg.horizontalAlign.spot_valueWasSet()) {
          switch(cfg.horizontalAlign.intValue()) {
            case Widget.CHorizontalAlign.center :
              hAlign = CellConstraints.CENTER;

              break;

            case Widget.CHorizontalAlign.left :
              hAlign = CellConstraints.LEFT;

              break;

            case Widget.CHorizontalAlign.right :
              hAlign = CellConstraints.RIGHT;

              break;

            case Widget.CHorizontalAlign.full :
              hAlign = CellConstraints.FILL;

              break;

            default :
              break;
          }          
        }
      }
    }

    if (!showTimeOnly) {
      if (datePicker == null) {
        datePicker = new DatePicker();
        datePicker.getMonthView().addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            onDateChanged(datePicker.getMonthView().getSelectionDate());
          }
        });
      }
    }

    if ((datePicker == null) || (timePicker == null)) {
      return (datePicker == null)
             ? timePicker
             : datePicker;
    }

    if (content == null) {
      content = new LinearPanel(Platform.getWindowViewer(),false);
      content.addRowSpacing(UIScreen.PLATFORM_PIXELS_4);
    } else {
      content.removeAll();
    }

    content.addComponent(datePicker, "F:D:G");
    content.addComponent(timePicker);
    CellConstraints cc = content.getCellConstraints(timePicker);
    if(cc!=null) {
      cc.hAlign=hAlign;
    }

    return content;
  }
   @Override
  protected void setValueEx(Calendar cal) {
    super.setValueEx(cal);
    Date d=cal==null ? null : cal.getTime();
    if(datePicker!=null) {
     datePicker.getMonthView().setSelectionDate(d); 
    }
    if(timePicker!=null) {
      timePicker.setValue(d);
    }
  }
  protected void onDateChanged(Date date) {
    if (ignoreChangeEvent) {
      ignoreChangeEvent = false;

      return;
    }

    updateValue(date);
  }

  protected void updateValue(Date date) {
    if (timePicker != null) {
      Date     d2  = (Date) timePicker.getModel().getValue();
      Calendar cal = tempCal;

      if (cal == null) {
        cal = tempCal = Calendar.getInstance();
      }

      cal.setTime(d2);

      int h = cal.get(Calendar.HOUR_OF_DAY);
      int m = cal.get(Calendar.MINUTE);
      int s = cal.get(Calendar.SECOND);

      cal.setTime(date);
      cal.set(Calendar.HOUR_OF_DAY, h);
      cal.set(Calendar.MINUTE, m);
      cal.set(Calendar.SECOND, s);
      date = cal.getTime();
    }

    this.date.setTime(date);
    stringValue = null;

    if (!isShowingDialog() && (okButton == null)) {
      fireEvent();
    }

    valueSet = true;
  }

  @Override
  public void stateChanged(EventObject e) {
    if (ignoreChangeEvent) {
      ignoreChangeEvent = false;

      return;
    }

    updateValue(date.getTime());
  }
}
