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

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;

import android.view.View;
import android.view.ViewGroup;

import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;

import com.appnativa.rare.platform.android.ui.view.LinearLayoutEx;
import com.appnativa.rare.ui.Component;
import com.appnativa.rare.ui.Utils;
import com.appnativa.rare.ui.event.ActionEvent;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.widget.iWidget;

import java.util.Calendar;

public class DateViewManager extends aDateViewManager
        implements OnDateChangedListener, OnDateSetListener, OnTimeChangedListener {
  boolean              showSpinners = true;
  Component            container;
  protected int        year = 1900;
  protected DatePicker datePicker;
  protected int        dayOfMonth;
  protected int        hourOfDay;
  protected int        minutes;
  protected int        month;
  protected TimePicker timePicker;

  public DateViewManager() {
    valueSet = true;
    setValueEx(Calendar.getInstance());
  }

  public void dispose() {
    datePicker = null;
    timePicker = null;
  }

  public void finishConfiguring(iWidget context) {
    updateDateFields();

    Context ctx = context.getAppContext().getActivity();

    initializeViews(ctx, false);
    container = new Component(createContentView(datePicker, timePicker, ctx));
  }

  public void initializeViews(Context context, boolean reset) {
    if ((datePicker != null) || (timePicker != null)) {
      if (!reset) {
        return;
      }
    }

    if (showTime) {
      if (timePicker == null) {
        timePicker = new TimePicker(context);
        timePicker.setOnTimeChangedListener(this);
      }

      initializePicker(timePicker);
    }

    if (!showTimeOnly) {
      if (datePicker == null) {
        datePicker = new DatePicker(context);
        datePicker.init(year, month, dayOfMonth, this);
      }

      initializePicker(datePicker);
    }
  }

  public void onDateChanged(DatePicker view, int year, int month, int dayOfMonth) {
    if (ignoreChangeEvent) {
      ignoreChangeEvent = false;

      return;
    }

    this.year       = year;
    this.month      = month;
    this.dayOfMonth = dayOfMonth;
    updateCalendar();

    if (okButton == null && !isShowingDialog()) {
      fireEvent();
    }

    valueSet = true;
  }

  public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
    onDateChanged(view, year, monthOfYear, dayOfMonth);
  }

  public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
    this.hourOfDay = hourOfDay;
    this.minutes   = minute;

    if (showTimeOnly) {
      updateCalendar();
      valueSet = true;

      if (okButton == null && !isShowingDialog()) {
        fireEvent();
      }
    }
  }

  public void showDialog(iPlatformComponent owner) {
    ChooserDialogHandler dh = new ChooserDialogHandler(this, null);

    dh.owner = owner;
    dh.showDialog(owner.getView().getContext());
  }

  public void setMaxDate(Calendar cal) {
    super.setMaxDate(cal);

    if ((datePicker != null) && (cal != null)) {
      datePicker.setMaxDate(cal.getTimeInMillis());
    }
  }

  public void setMinDate(Calendar cal) {
    super.setMinDate(cal);

    if ((datePicker != null) && (cal != null)) {
      datePicker.setMinDate(cal.getTimeInMillis());
    }
  }

  public void setShowCalendar(boolean showCalendar) {
    super.setShowCalendar(showCalendar);

    if (datePicker != null) {
      datePicker.setCalendarViewShown(showCalendar);
    }
  }

  public void setShowSpinners(boolean showSpinners) {
    this.showSpinners = showSpinners;

    if (datePicker != null) {
      datePicker.setSpinnersShown(showSpinners);
    }
  }

  public void setUseAmPmTimeFormat(boolean useAmPmTimeFormat) {
    super.setUseAmPmTimeFormat(useAmPmTimeFormat);

    if (timePicker != null) {
      timePicker.setIs24HourView(!useAmPmTimeFormat);
    }
  }

  public DatePicker getDatePicker() {
    return datePicker;
  }

  public iPlatformComponent getDatePickerComponent() {
    return container;
  }

  public TimePicker getTimePicker() {
    return timePicker;
  }

  public boolean isShowSpinners() {
    return showSpinners;
  }

  boolean isValidDate(int year, int monthOfYear, int dayOfMonth) {
    if ((minDate == null) && (maxDate == null)) {
      return true;
    }

    if (validationDate == null) {
      validationDate = Calendar.getInstance();
    }

    Calendar d = validationDate;

    date.set(year, month, dayOfMonth);

    if ((minDate != null) && d.before(minDate)) {
      return false;
    }

    if ((maxDate != null) && d.after(maxDate)) {
      return false;
    }

    return true;
  }

  protected View createContentView(DatePicker datePicker, TimePicker timePicker, Context context) {
    if (timePicker == null) {
      return datePicker;
    }

    if (datePicker == null) {
      return timePicker;
    }

    ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                  ViewGroup.LayoutParams.MATCH_PARENT);
    LinearLayoutEx l = new LinearLayoutEx(context);

    l.setOrientation(LinearLayoutEx.VERTICAL);
    l.setLayoutParams(lp);

    if (datePicker != null) {
      l.addView(datePicker);
    }

    if (timePicker != null) {
      l.addView(timePicker);
    }

    if (showTimeOnly && (datePicker != null)) {
      datePicker.setVisibility(View.GONE);
    }

    return l;
  }

  protected void initializePicker(DatePicker picker) {
    if (date != null) {
      picker.updateDate(year, month, dayOfMonth);
    }

    picker.setSpinnersShown(showSpinners);
    picker.setCalendarViewShown(showCalendar);

    if (minDate != null) {
      picker.setMinDate(minDate.getTimeInMillis());
    }

    if (maxDate != null) {
      picker.setMaxDate(maxDate.getTimeInMillis());
    }
  }

  protected void initializePicker(TimePicker picker) {
    picker.setIs24HourView(!useAmPmTimeFormat);

    if (date != null) {
      picker.setCurrentHour(hourOfDay);
      picker.setCurrentMinute(minutes);
    }
  }

  protected void updateCalendar() {
    date.set(year, month, dayOfMonth, hourOfDay, minutes);
    stringValue = null;
  }

  protected void updateDateFields() {
    year       = date.get(Calendar.YEAR);
    month      = date.get(Calendar.MONTH);
    dayOfMonth = date.get(Calendar.DAY_OF_MONTH);
    hourOfDay  = date.get(Calendar.HOUR_OF_DAY);
    minutes    = date.get(Calendar.MINUTE);
  }

  protected void setValueEx(Calendar cal) {
    super.setValueEx(cal);
    updateDateFields();

    if (datePicker != null) {
      datePicker.updateDate(year, month, dayOfMonth);
    }

    if (timePicker != null) {
      timePicker.setCurrentHour(hourOfDay);
      timePicker.setCurrentMinute(minutes);
    }
  }

  public static class ChooserDialogHandler implements OnDismissListener {
    public DatePickerDialog   dialog;
    public DateViewManager    dvm;
    public OnDismissListener  onDismissListener;
    public iPlatformComponent owner;

    public ChooserDialogHandler(DateViewManager dvm, OnDismissListener l) {
      this.dvm          = dvm;
      onDismissListener = l;
    }

    public void hide() {
      if (dialog != null) {
        dialog.dismiss();
      }
    }

    public void onDismiss(DialogInterface dialog) {
      if (onDismissListener != null) {
        onDismissListener.onDismiss(dialog);
      }

      this.dialog = null;

      ActionEvent ae = new ActionEvent(owner);

      Utils.fireActionEvent(dvm.listenerList, ae);
    }

    public void showDialog(Context context) {
      dvm.updateDateFields();

      DatePickerDialog d = new DatePickerDialog(context, dvm, dvm.year, dvm.month, dvm.dayOfMonth);

      d.setOnDismissListener(this);
      createDialogViews(context, d);
      dialog = d;

      if (dvm.showTimeOnly) {
        updateTitle(dvm.hourOfDay, dvm.minutes);
      }

      d.show();
    }

    public void updateTitle(int hourOfDay, int minute) {
      if (dialog != null) {
        String s;

        if (hourOfDay > 11) {
          s         = "%d:%02d PM";
          hourOfDay -= 12;
        } else {
          s = "%d:%02d AM";
        }

        dialog.setTitle(String.format(s, hourOfDay, minute));
      }
    }

    public void setTitle(String title) {
      if (dialog != null) {
        dialog.setTitle(title);
      }
    }

    public boolean isShowing() {
      return (dialog == null)
             ? false
             : dialog.isShowing();
    }

    protected void createDialogViews(Context context, DatePickerDialog d) {
      DatePicker datePicker = null;
      TimePicker timePicker = null;

      datePicker = d.getDatePicker();
      dvm.initializePicker(datePicker);

      if (dvm.showTime) {
        timePicker = new TimePicker(context);
        timePicker.setOnTimeChangedListener(dvm);
        dvm.initializePicker(timePicker);

        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                      ViewGroup.LayoutParams.MATCH_PARENT);
        LinearLayoutEx l = new LinearLayoutEx(context);

        l.setOrientation(LinearLayoutEx.VERTICAL);
        l.setLayoutParams(lp);
        d.setView(l);
        l.addView(datePicker);
        l.addView(timePicker);

        if (dvm.showTimeOnly) {
          datePicker.setVisibility(View.GONE);
        }
      }
    }
  }
}
