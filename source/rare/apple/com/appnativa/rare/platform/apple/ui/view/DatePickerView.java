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

package com.appnativa.rare.platform.apple.ui.view;

import com.appnativa.rare.Platform;
import com.appnativa.rare.ui.ActionComponent;
import com.appnativa.rare.ui.ColorUtils;
import com.appnativa.rare.ui.UIColor;

import java.util.Calendar;

/*-[
 #import "RAREAPDatePicker.h"
 #import "APView+Component.h"
 ]-*/
public class DatePickerView extends ParentView {
  boolean                   showTime;
  boolean                   showTimeOnly;
  protected ActionComponent noneButton;
  protected ActionComponent okButton;
  protected ActionComponent todayButton;
  protected iUpdateListener updateListener;

  public DatePickerView(iUpdateListener updateListener, boolean showTime, boolean showTimeOnly) {
    super(createProxy(showTime, showTimeOnly));
    this.updateListener = updateListener;
    this.showTime       = showTime;
    this.showTimeOnly   = showTimeOnly;

    UIColor c = Platform.getUIDefaults().getColor("Rare.DateChooser.background");

    if (c == null) {
      c = ColorUtils.getBackground();
    }

    setBackgroundColor(c);
  }

  @Override
  public native boolean setBackgroundColorEx(UIColor bg)
  /*-[
  ((RAREAPDatePicker*)proxy_).backgroundColor=(UIColor*)[bg getAPColor];
  return YES;
  ]-*/
  ;

  public native void setDate(Calendar date)
  /*-[
    [((RAREAPDatePicker*)proxy_) setDateValue:[NSDate fromJavaCalendar: date ]];
  ]-*/
  ;

  public native void setDateInMillis(long date)
  /*-[
    [((RAREAPDatePicker*)proxy_) setDateInMillis: date];
  ]-*/
  ;

  public native void setEndDate(long date)
  /*-[
   if(date) {
    [((RAREAPDatePicker*)proxy_) setEndDateInMillis: date];
   }
  ]-*/
  ;

  public native void setMaxDate(Calendar date)
  /*-[
   if(date) {
    [((RAREAPDatePicker*)proxy_) setMaxDate:[NSDate fromJavaCalendar: date ]];
   }
   else {
    [((RAREAPDatePicker*)proxy_) setMinDate: nil];
   }
  ]-*/
  ;

  public native void setMinDate(Calendar date)
  /*-[
   if(date) {
    [((RAREAPDatePicker*)proxy_) setMinDate:[NSDate fromJavaCalendar: date ]];
   }
   else {
    [((RAREAPDatePicker*)proxy_) setMinDate: nil];
   }
  ]-*/
  ;

  public native void setShowTime(boolean show)
  /*-[
    [((RAREAPDatePicker*)proxy_) setShowTime: show];
  ]-*/
  ;

  public native void setShowTimeOnly(boolean show)
  /*-[
    [((RAREAPDatePicker*)proxy_) setShowTimeOnly: show];
  ]-*/
  ;

  public native void setWeekdaysOnly(boolean weekdaysOnly)
  /*-[
    ((RAREAPDatePicker*)proxy_).weekdaysOnly=weekdaysOnly;
   ]-*/
  ;

  public native void setWeekendsOnly(boolean weekendsOnly)
  /*-[
     ((RAREAPDatePicker*)proxy_).weekendsOnly=weekendsOnly;
   ]-*/
  ;

  static native Object createProxy(boolean showTime, boolean showTimeOnly)
  /*-[
     return [[RAREAPDatePicker alloc] initWithShowTime: showTime andShowTimeOnly: showTimeOnly];
   ]-*/
  ;

  @Override
  protected void disposeEx() {
    super.disposeEx();
    updateListener = null;
  }

  protected void rangeChanged(long startTime, long endTime) {
    if (updateListener != null) {
      updateListener.updateRange(startTime, endTime);
    }
  }

  protected void timeChanged(long time) {
    if (updateListener != null) {
      updateListener.updateTime(time);
    }
  }

  public interface iUpdateListener {
    void updateRange(long startTime, long endTime);

    void updateTime(long time);
  }
}
