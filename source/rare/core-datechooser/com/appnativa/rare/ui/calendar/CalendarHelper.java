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

import java.util.Calendar;
import java.util.Date;

public class CalendarHelper {
  protected Calendar           mCalendar;
  protected MonthDisplayHelper mHelper;
  protected Calendar           mToday;
  protected DateSelectionModel selectionModel;

  public CalendarHelper() {
    createModel();
    mToday    = Calendar.getInstance();
    mCalendar = Calendar.getInstance();
    mHelper   = new MonthDisplayHelper(mToday.get(Calendar.YEAR), mToday.get(Calendar.MONTH));
  }

  public iDayCell configureDayCell(int row, int column, iDayCell cell) {
    int day = mHelper.getDayAt(row, column);

    cell.setDay(day);

    if (mHelper.isWithinCurrentMonth(row, column)) {
      mCalendar.set(mHelper.getYear(), mHelper.getMonth(), day);
      cell.setToday(selectionModel.isSameDay(mToday.getTime(), mCalendar.getTime()));
      cell.setCurrentMonth(true);
    } else {
      cell.setCurrentMonth(false);

      if (day > 15) {
        mCalendar.set(mHelper.getYear(), mHelper.getMonth() + 1, day);
      } else {
        mCalendar.set(mHelper.getYear(), mHelper.getMonth() - 1, day);
      }

      cell.setToday(selectionModel.isSameDay(mToday.getTime(), mCalendar.getTime()));
    }

    cell.setSelected(selectionModel.isSelectedDate(mCalendar.getTime()));

    return cell;
  }

  public static iDateFilter createWeekDayOnlyFilter(final Calendar max, final Calendar min) {
    return new iDateFilter() {
      @Override
      public Calendar getMaxDate() {
        return max;
      }
      @Override
      public Calendar getMinDate() {
        return min;
      }
      @Override
      public boolean isDateValid(Calendar date) {
        if ((date.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)
            || (date.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)) {
          return false;
        }

        return true;
      }
    };
  }

  public static iDateFilter createWeekendOnlyFilter(final Calendar max, final Calendar min) {
    return new iDateFilter() {
      @Override
      public Calendar getMaxDate() {
        return max;
      }
      @Override
      public Calendar getMinDate() {
        return min;
      }
      @Override
      public boolean isDateValid(Calendar date) {
        if ((date.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)
            || (date.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)) {
          return true;
        }

        return false;
      }
    };
  }

  public Date gotoDateAt(double px, double py, double cw, double ch) {
    double       x   = 0;
    double       y   = 0;
    final double ox  = x;
    double       ex  = x + cw;
    double       ey  = y + ch;
    int          day = -1;
    int          row = 0;
    int          col = 0;

    for (int r = 0; r < 6; r++) {
      if ((py >= y) && (py <= ey)) {
        for (int c = 0; c < 7; c++) {
          if ((px >= x) && (px <= ex)) {
            day = getDay(r, c);
            row = r;
            col = c;

            break;
          }

          x  += cw;
          ex += cw;
        }
      }

      x  = ox;
      y  += ch;
      ey += ch;
    }

    if (day > -1) {
      if (mHelper.isWithinCurrentMonth(row, col)) {
        mCalendar.set(mHelper.getYear(), mHelper.getMonth(), day);
      } else {
        if (day > 15) {
          mCalendar.set(mHelper.getYear(), mHelper.getMonth() + 1, day);
        } else {
          mCalendar.set(mHelper.getYear(), mHelper.getMonth() - 1, day);
        }
      }

      return mCalendar.getTime();
    }

    return null;
  }

  public void nextDay() {
    mHelper.nextDay();
    mCalendar.setTimeInMillis(mHelper.getTimeInMillis());
  }

  public void nextMonth() {
    mHelper.nextMonth();
    mCalendar.setTimeInMillis(mHelper.getTimeInMillis());
  }

  public void nextWeek() {
    mHelper.nextWeek();
    mCalendar.setTimeInMillis(mHelper.getTimeInMillis());
  }

  public void nextYear() {
    mHelper.nextYear();
    mCalendar.setTimeInMillis(mHelper.getTimeInMillis());
  }

  public void previousDay() {
    mHelper.previousDay();
    mCalendar.setTimeInMillis(mHelper.getTimeInMillis());
  }

  public void previousMonth() {
    mHelper.previousMonth();
    mCalendar.setTimeInMillis(mHelper.getTimeInMillis());
  }

  public void previousWeek() {
    mHelper.previousWeek();
    mCalendar.setTimeInMillis(mHelper.getTimeInMillis());
  }

  public void previousYear() {
    mHelper.previousYear();
    mCalendar.setTimeInMillis(mHelper.getTimeInMillis());
  }

  public void today() {
    mToday.setTimeInMillis(System.currentTimeMillis());
    setTimeInMillis(System.currentTimeMillis());
  }

  /**
   * @param selectionModel the selectionModel to set
   */
  public void setSelectionModel(DateSelectionModel selectionModel) {
    this.selectionModel = selectionModel;
  }

  public void setShowNoneButton(boolean booleanValue) {}

  public void setShowOKButton(boolean booleanValue) {}

  public void setShowTodayButton(boolean booleanValue) {}

  public void setShowWeekNumbers(boolean booleanValue) {}

  public void setTimeInMillis(long milliseconds) {
    mCalendar.setTimeInMillis(milliseconds);
    mHelper.setTimeInMillis(milliseconds);
  }

  public Date getDate() {
    return mCalendar.getTime();
  }

  public int getDay(int row, int column) {
    return mHelper.getDayAt(row, column);
  }

  public int getMonth() {
    return mHelper.getMonth();
  }

  /**
   * @return the selectionModel
   */
  public DateSelectionModel getSelectionModel() {
    return selectionModel;
  }

  public int getYear() {
    return mHelper.getYear();
  }

  public boolean isLastDay(int day) {
    return mHelper.getNumberOfDaysInMonth() == day;
  }

  protected void createModel() {
    selectionModel = new DateSelectionModel();
  }

  /*
   * Copyright (C) 2007 The Android Open Source Project
   *
   * Licensed under the Apache License, Version 2.0 (the "License");
   * you may not use this file except in compliance with the License.
   * You may obtain a copy of the License at
   *
   *      http://www.apache.org/licenses/LICENSE-2.0
   *
   * Unless required by applicable law or agreed to in writing, software
   * distributed under the License is distributed on an "AS IS" BASIS,
   * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   * See the License for the specific language governing permissions and
   * limitations under the License.
   *
   *
   * Helps answer common questions that come up when displaying a month in a
   * 6 row calendar grid format.
   *
   * Not thread safe.
   */
  public static class MonthDisplayHelper {
    // holds current month, year, helps compute display
    private Calendar mCalendar;
    // cached computed stuff that helps with display
    private int mNumDaysInMonth;
    private int mNumDaysInPrevMonth;
    private int mOffset;
    // display pref
    private final int mWeekStartDay;

    public MonthDisplayHelper(int year, int month) {
      this(year, month, Calendar.SUNDAY);
    }

    /**
     * @param year The year.
     * @param month The month.
     * @param weekStartDay What day of the week the week should start.
     */
    public MonthDisplayHelper(int year, int month, int weekStartDay) {
      if ((weekStartDay < Calendar.SUNDAY) || (weekStartDay > Calendar.SATURDAY)) {
        throw new IllegalArgumentException();
      }

      mWeekStartDay = weekStartDay;
      mCalendar     = Calendar.getInstance();
      mCalendar.set(Calendar.YEAR, year);
      mCalendar.set(Calendar.MONTH, month);
      mCalendar.set(Calendar.DAY_OF_MONTH, 1);
      mCalendar.set(Calendar.HOUR_OF_DAY, 0);
      mCalendar.set(Calendar.MINUTE, 0);
      mCalendar.set(Calendar.SECOND, 0);
      mCalendar.getTimeInMillis();
      recalculate();
    }

    public void nextDay() {
      mCalendar.add(Calendar.DAY_OF_YEAR, 1);
      recalculate();
    }

    /**
     * Increment the month.
     */
    public void nextMonth() {
      mCalendar.add(Calendar.MONTH, 1);
      recalculate();
    }

    public void nextWeek() {
      mCalendar.add(Calendar.WEEK_OF_YEAR, 1);
      recalculate();
    }

    public void nextYear() {
      mCalendar.add(Calendar.YEAR, 1);
      recalculate();
    }

    public void previousDay() {
      mCalendar.add(Calendar.DAY_OF_YEAR, -1);
      recalculate();
    }

    /**
     * Decrement the month.
     */
    public void previousMonth() {
      mCalendar.add(Calendar.MONTH, -1);
      recalculate();
    }

    public void previousWeek() {
      mCalendar.add(Calendar.WEEK_OF_YEAR, -1);
      recalculate();
    }

    public void previousYear() {
      mCalendar.add(Calendar.YEAR, -1);
      recalculate();
    }

    public void setTimeInMillis(long milliseconds) {
      mCalendar.setTimeInMillis(milliseconds);
      recalculate();
    }

    /**
     * @return Which column day is in.
     */
    public int getColumnOf(int day) {
      return (day + mOffset - 1) % 7;
    }

    /**
     * @param row The row, 0-5, starting from the top.
     * @param column The column, 0-6, starting from the left.
     * @return The day at a particular row, column
     */
    public int getDayAt(int row, int column) {
      if ((row == 0) && (column < mOffset)) {
        return mNumDaysInPrevMonth + column - mOffset + 1;
      }

      int day = 7 * row + column - mOffset + 1;

      return (day > mNumDaysInMonth)
             ? day - mNumDaysInMonth
             : day;
    }

    /**
     * @param row Which row (0-5).
     * @return the digits of the month to display in one
     * of the 6 rows of a calendar month display.
     */
    public int[] getDigitsForRow(int row) {
      if ((row < 0) || (row > 5)) {
        throw new IllegalArgumentException("row " + row + " out of range (0-5)");
      }

      int[] result = new int[7];

      for (int column = 0; column < 7; column++) {
        result[column] = getDayAt(row, column);
      }

      return result;
    }

    /**
     * @return The first day of the month using a constants such as
     *   {@link java.util.Calendar#SUNDAY}.
     */
    public int getFirstDayOfMonth() {
      return mCalendar.get(Calendar.DAY_OF_WEEK);
    }

    public int getMonth() {
      return mCalendar.get(Calendar.MONTH);
    }

    /**
     * @return The number of days in the month.
     */
    public int getNumberOfDaysInMonth() {
      return mNumDaysInMonth;
    }

    /**
     * @return The offset from displaying everything starting on the very first
     *   box.  For example, if the calendar is set to display the first day of
     *   the week as Sunday, and the month starts on a Wednesday, the offset is 3.
     */
    public int getOffset() {
      return mOffset;
    }

    /**
     * @return Which row day is in.
     */
    public int getRowOf(int day) {
      return (day + mOffset - 1) / 7;
    }

    public long getTimeInMillis() {
      return mCalendar.getTimeInMillis();
    }

    public int getWeekStartDay() {
      return mWeekStartDay;
    }

    public int getYear() {
      return mCalendar.get(Calendar.YEAR);
    }

    /**
     * @return Whether the row and column fall within the month.
     */
    public boolean isWithinCurrentMonth(int row, int column) {
      if ((row < 0) || (column < 0) || (row > 5) || (column > 6)) {
        return false;
      }

      if ((row == 0) && (column < mOffset)) {
        return false;
      }

      int day = 7 * row + column - mOffset + 1;

      if (day > mNumDaysInMonth) {
        return false;
      }

      return true;
    }

    // helper method that recalculates cached values based on current month / year
    private void recalculate() {
      mNumDaysInMonth = mCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
      mCalendar.add(Calendar.MONTH, -1);
      mNumDaysInPrevMonth = mCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
      mCalendar.add(Calendar.MONTH, 1);

      int firstDayOfMonth = getFirstDayOfMonth();
      int offset          = firstDayOfMonth - mWeekStartDay;

      if (offset < 0) {
        offset += 7;
      }

      mOffset = offset;
    }
  }
}
