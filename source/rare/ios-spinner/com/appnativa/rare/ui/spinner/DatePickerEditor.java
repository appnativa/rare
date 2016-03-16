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

import com.appnativa.rare.platform.apple.ui.view.DatePickerView;
import com.appnativa.rare.platform.apple.ui.view.DatePickerView.iUpdateListener;
import com.appnativa.rare.ui.event.iChangeListener;
import com.appnativa.rare.ui.spinner.SpinnerDateModel;
import com.appnativa.rare.ui.spinner.aSpinnerEditor;
import com.appnativa.rare.ui.spinner.iSpinnerModel;

import java.util.Calendar;
import java.util.Date;
import java.util.EventObject;

public class DatePickerEditor extends aSpinnerEditor implements iUpdateListener, iChangeListener {
  Calendar date;
  Date     max, min;

  public DatePickerEditor(iSpinnerModel model) {
    super(model);
    date = Calendar.getInstance();

    SpinnerDateModel dm = (SpinnerDateModel) model;

    max = dm.getMaximum();
    min = dm.getMinimum();

    DatePickerView pv = new DatePickerView(this, dm.isShowTime(), dm.isShowTimeOnly());

    if (max != null) {
      date.setTime(max);
      pv.setMaxDate(date);
    }

    if (min != null) {
      date.setTime(min);
      pv.setMinDate(date);
    }

    editorView = pv;
    model.addChangeListener(this);
  }

  @Override
  public void setValue(Object value) {
    Date d=null;
    if(value instanceof Calendar) {
      d=((Calendar)value).getTime();
    }
    else if(value instanceof Date) {
      d=(Date) value;
    }
    if(d==null) {
      if(min!=null) {
        d=min;
      }
      else {
        d=new Date();
      }
    }
    date.setTime(d);
    ((DatePickerView)editorView).setDate(date);
    spinnerModel.setValue(date);
  }

  @Override
  public Object getValue() {
    return date.getTime();
  }

  @Override
  public void updateTime(long time) {
    date.setTimeInMillis(time);
    spinnerModel.setValue(date);
  }

  @Override
  public void updateRange(long startTime, long endTime) {}

  @Override
  public void stateChanged(EventObject e) {
    SpinnerDateModel dm = (SpinnerDateModel) spinnerModel;

    max = dm.getMaximum();
    min = dm.getMinimum();

    DatePickerView pv = (DatePickerView) editorView;

    if (max == null) {
      pv.setMaxDate(null);
    } else {
      date.setTime(max);
      pv.setMaxDate(date);
    }

    if (min == null) {
      pv.setMinDate(null);
    } else {
      date.setTime(min);
      pv.setMinDate(date);
    }
  }

  @Override
  public boolean isTextField() {
    return false;
  }

  @Override
  public Object removeSelectedData(boolean returnData) {
    return null;
  }

  @Override
  public void selectField() {
  }
}
