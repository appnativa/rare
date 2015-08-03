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

import com.appnativa.rare.ui.event.ChangeEvent;
import com.appnativa.rare.ui.event.EventListenerList;
import com.appnativa.rare.ui.event.iChangeListener;
import com.appnativa.util.Helper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Date selection model class that just stores the selection.
 * The enforcement to the selection type is handle by the class using the model
 *
 * @author Don DeCoteau
 */
public class DateSelectionModel {

  /**
   * Used to support multiple Date intevals.
   */
  public static final int MULTIPLE_INTERVAL_SELECTION = 2;

  /**
   * Used to support only one Date interval.
   */
  public static final int SINGLE_INTERVAL_SELECTION = 1;

  /**
   *    Used to support only one Date selected.
   */
  public static final int     SINGLE_SELECTION = 0;
  protected EventListenerList listenerList     = new EventListenerList();
  protected Date              anchorDate;
  protected Date              leadDate;
  protected Date              selectedDate;
  protected ArrayList<Date>   selectedDates;
  private int                 selectionMode = SINGLE_SELECTION;
  private Calendar            calendar;
  private ChangeEvent         changeEvent;

  public void addChangeListener(iChangeListener l) {
    if (listenerList == null) {
      listenerList = new EventListenerList();
      listenerList.add(iChangeListener.class, l);
    } else {
      listenerList.remove(iChangeListener.class, l);
      listenerList.add(iChangeListener.class, l);
    }
  }

  public void addSelectedDate(Date date) {
    if ((selectionMode == SINGLE_SELECTION)
        || ((selectedDate == null) && ((selectedDates == null) || selectedDates.isEmpty()))) {
      setSelectedDate(date);
    } else if (addSelectedDateEx(date)) {
      fireChangeEvent();
    }
  }

  public void clearSelection() {
    if (!isSelectionEmpty()) {
      clearSelectionEx();
      fireChangeEvent();
    }
  }

  public void removeChangeListener(iChangeListener l) {
    if (listenerList != null) {
      listenerList.remove(iChangeListener.class, l);
    }
  }

  public void setLeadSelectionDate(Date date) {
    if (anchorDate == null) {
      setSelectedDate(date);
    } else {
      Date a = anchorDate;

      clearSelectionEx();
      anchorDate   = a;
      selectedDate = date;
      leadDate     = date;
      fireChangeEvent();
    }
  }

  public void setSelectedDate(Date date) {
    if (selectedDates != null) {
      selectedDates.clear();
    }

    if ((selectedDate == null) ||!selectedDate.equals(date)) {
      clearSelectionEx();
      selectedDate = date;
      anchorDate   = date;
      leadDate     = date;
    }

    fireChangeEvent();
  }

  public void setSelectedDates(Date... date) {
    final int len = (date == null)
                    ? 0
                    : date.length;

    if (len > 0) {
      if ((selectionMode == SINGLE_SELECTION) || ((len == 1) && (selectedDates == null))) {
        setSelectedDate(date[0]);

        return;
      }

      clearSelectionEx();

      if (selectedDates == null) {
        selectedDates = new ArrayList<Date>((len > 5)
                ? len
                : 5);
      }

      for (int i = 0; i < len; i++) {
        addSelectedDateEx(date[i]);
      }

      if (!selectedDates.isEmpty()) {
        selectedDate = selectedDates.get(selectedDates.size() - 1);
        anchorDate   = selectedDates.get(0);
        leadDate     = selectedDate;
        fireChangeEvent();
      }
    } else {
      clearSelection();
    }
  }

  public void setSelectionInterval(Date date0, Date date1) {
    anchorDate = date0;
    setLeadSelectionDate(date1);
  }

  public void setSelectionMode(int selectionMode) {
    if (this.selectionMode != selectionMode) {
      if ((selectedDates != null) &&!selectedDates.isEmpty()) {
        if (selectedDate == null) {
          setSelectedDate(selectedDates.get(0));
        }
      }

      this.selectionMode = selectionMode;
    }
  }

  public Date getAnchorDate() {
    return anchorDate;
  }

  public Date getLeadSelectionDate() {
    return leadDate;
  }

  public Date getSelectedDate() {
    return selectedDate;
  }

  public Date[] getSelectedDates() {
    int count = getSelectionCount();

    if (count == 0) {
      return null;
    }

    Date[] dates = new Date[count];

    if ((selectedDates != null) &&!selectedDates.isEmpty()) {
      dates = selectedDates.toArray(dates);
    } else if (count == 1) {
      dates[0] = selectedDate;
    } else {
      Date d0 = anchorDate;
      Date d1 = leadDate;

      if (d1.before(d0)) {
        d1 = anchorDate;
        d0 = leadDate;
      }

      Calendar cal = getCalendar();

      count--;
      dates[0]     = d0;
      dates[count] = d1;
      cal.setTime(d0);

      for (int i = 1; i < count; i++) {
        cal.add(Calendar.DAY_OF_YEAR, 1);
        dates[i] = cal.getTime();
      }
    }

    return dates;
  }

  public int getSelectionCount() {
    final int size = (selectedDates == null)
                     ? 0
                     : selectedDates.size();

    if (size == 0) {
      if ((leadDate == null) || (anchorDate == null)) {
        return 0;
      }

      if (leadDate.equals(anchorDate)) {
        return 1;
      }

      return Math.abs(Helper.daysBetween(anchorDate, leadDate));
    }

    return size;
  }

  public int getSelectionMode() {
    return selectionMode;
  }

  public boolean isSameDay(Date date0, Date date1) {
    if ((date0 == null) || (date1 == null)) {
      return date0 == date1;
    }

    Calendar cal = getCalendar();

    cal.setTime(date0);

    final int y  = cal.get(Calendar.YEAR);
    final int dd = cal.get(Calendar.DAY_OF_YEAR);

    cal.setTime(date1);

    return ((cal.get(Calendar.YEAR) == y) && (cal.get(Calendar.DAY_OF_YEAR) == dd));
  }

  public boolean isSelectedDate(Date date) {
    if ((selectedDate == null) && ((selectedDates == null) || selectedDates.isEmpty())) {
      return false;
    }

    Calendar cal = getCalendar();

    cal.setTime(date);

    final int y  = cal.get(Calendar.YEAR);
    final int dd = cal.get(Calendar.DAY_OF_YEAR);

    if ((selectedDate != null)) {
      cal.setTime(selectedDate);

      if (((cal.get(Calendar.YEAR) == y) && (cal.get(Calendar.DAY_OF_YEAR) == dd))) {
        return true;
      }
    }

    final ArrayList<Date> dates = selectedDates;
    final int             len   = (selectedDates == null)
                                  ? 0
                                  : selectedDates.size();

    for (int i = 0; i < len; i++) {
      cal.setTime(dates.get(i));

      if (((cal.get(Calendar.YEAR) == y) && (cal.get(Calendar.DAY_OF_YEAR) == dd))) {
        return true;
      }
    }

    return false;
  }

  public boolean isSelectionEmpty() {
    return (selectedDate == null) && ((selectedDates == null) || selectedDates.isEmpty());
  }

  protected void clearSelectionEx() {
    if (selectedDates != null) {
      selectedDates.clear();
    }

    anchorDate   = null;
    leadDate     = null;
    selectedDate = null;
  }

  protected void fireChangeEvent() {
    if (listenerList != null) {
      if (changeEvent == null) {
        changeEvent = new ChangeEvent(this);
      }

      Object[] listeners = listenerList.getListenerList();

      // Process the listeners last to first, notifying
      // those that are interested in this event
      for (int i = listeners.length - 2; i >= 0; i -= 2) {
        if (listeners[i] == iChangeListener.class) {
          ((iChangeListener) listeners[i + 1]).stateChanged(changeEvent);
        }
      }
    }
  }

  protected final Calendar getCalendar() {
    if (calendar == null) {
      calendar = Calendar.getInstance();
    }

    return calendar;
  }

  protected Date[] getSelectedDatesEx() {
    if (selectedDates != null) {
      return selectedDates.toArray(new Date[selectedDates.size()]);
    }

    return (selectedDate == null)
           ? null
           : new Date[] { selectedDate };
  }

  private boolean addSelectedDateEx(Date date) {
    if (isSameDay(date, selectedDate)) {
      return false;
    }

    if (selectedDates == null) {
      selectedDates = new ArrayList<Date>(5);
      selectedDates.add(selectedDate);
      selectedDates.add(date);

      return true;
    }

    final int len = selectedDates.size();

    for (int i = 0; i < len; i++) {
      Date d = selectedDates.get(i);

      if (date.before(d)) {
        if (isSameDay(d, date)) {
          return false;
        }

        selectedDates.add(i, date);

        return true;
      }

      if (isSameDay(d, date)) {
        return false;
      }
    }

    selectedDates.add(date);

    return true;
  }
}
