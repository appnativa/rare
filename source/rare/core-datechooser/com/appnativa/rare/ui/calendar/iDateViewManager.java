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

import com.appnativa.rare.converters.iDataConverter;
import com.appnativa.rare.ui.event.iActionListener;
import com.appnativa.rare.ui.event.iChangeListener;
import com.appnativa.rare.widget.iWidget;

import java.util.Calendar;
import java.util.Date;

public interface iDateViewManager {
  void addActionListener(iActionListener l);

  /**
   * Adds a change listener to listen for date value changes
   *
   * @param l
   *          the listener to add
   */
  void addChangeListener(iChangeListener l);

  void removeActionListener(iActionListener l);

  /**
   * Removes a previously added change listener
   *
   * @param l
   *          the listener to remove
   */
  void removeChangeListener(iChangeListener l);

  void setConverter(iDataConverter converter);

  void setConverterContext(Object context);

  void setDate(Calendar date);

  void setDate(Date date);

  void setMaxDate(Calendar cal);

  void setMinDate(Calendar cal);

  void dispose();

  void setShowCalendar(boolean showCalendar);

  void setShowNoneButton(boolean show);

  void setShowOKButton(boolean show);

  void setShowTime(boolean show);

  void setShowTimeOnly(boolean showTimeOnly);

  void setShowTodayButton(boolean show);

  void setShowWeekNumbers(boolean show);

  void setUseAmPmTimeFormat(boolean useAmPmTimeFormat);

  Date getValue();

  CharSequence getValueAsString();

  boolean isShowCalendar();

  boolean isShowTimeOnly();

  boolean isUseAmPmTimeFormat();

  void finishConfiguring(iWidget context);
}
