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

import com.appnativa.rare.platform.apple.ui.view.DatePickerView;
import com.appnativa.rare.platform.apple.ui.view.DatePickerView.iUpdateListener;
import com.appnativa.rare.ui.Container;
import com.appnativa.rare.ui.event.WindowEvent;
import com.appnativa.rare.ui.event.iWindowListener;
import com.appnativa.rare.ui.iPlatformComponent;

import java.util.Calendar;

public class DateViewManager extends aDateViewManager implements iUpdateListener, iWindowListener {
  protected DatePickerView  picker;
  protected iUpdateListener updateListener;

  @Override
  public void dispose() {
    super.dispose();
    updateListener = null;
    picker         = null;
  }

  @Override
  public void updateRange(long startTime, long endTime) {
    date.setTimeInMillis(startTime);

    if (endDate == null) {
      endDate = Calendar.getInstance();
    }

    endDate.setTimeInMillis(startTime);

    if (okButton == null) {
      fireEvent();
    }
  }

  @Override
  public void updateTime(long time) {
    date.setTimeInMillis(time);
    stringValue = null;

    if (okButton == null) {
      fireEvent();
    }
  }

  @Override
  public void windowEvent(WindowEvent e) {
    if (e.getType() == WindowEvent.Type.Closed) {
      if (dialog != null) {
        dialog.disposeOfWindow();
        dialog = null;
      }
    }
  }

  @Override
  public void setDate(Calendar cal) {
    super.setMinDate(cal);

    if (picker != null) {
      picker.setMinDate(cal);
    }
  }

  @Override
  public void setEndDate(Calendar cal) {
    super.setEndDate(cal);

    if (picker != null) {
      picker.setEndDate((cal == null)
                        ? date.getTimeInMillis()
                        : cal.getTimeInMillis());
    }
  }

  @Override
  public void setMaxDate(Calendar cal) {
    super.setMaxDate(cal);

    if (picker != null) {
      picker.setMaxDate(cal);
    }
  }

  @Override
  public void setMinDate(Calendar cal) {
    super.setMinDate(cal);

    if (picker != null) {
      picker.setMinDate(cal);
    }
  }

  @Override
  public void setShowTime(boolean show) {
    if (show != showTime) {
      super.setShowTime(show);

      if (picker != null) {
        picker.setShowTime(show);
      }
    }
  }

  @Override
  public void setShowTimeOnly(boolean show) {
    if (show != showTimeOnly) {
      super.setShowTimeOnly(show);

      if (picker != null) {
        picker.setShowTimeOnly(show);
      }
    }
  }

  @Override
  public iPlatformComponent getDatePickerComponent() {
    iPlatformComponent c;

    if (picker == null) {
      picker = new DatePickerView(this, showTime, showTimeOnly);
      c      = new Container(picker);
    } else {
      c = picker.getComponent();
    }

    ignoreChangeEvent = true;
    picker.setDateInMillis(date.getTimeInMillis());
    ignoreChangeEvent = false;

    return c;
  }

  @Override
  protected void setValueEx(Calendar cal) {
    super.setValueEx(cal);

    if (picker != null) {
      picker.setDateInMillis(date.getTimeInMillis());
    }
  }
}
