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

package com.appnativa.rare.widget;

import com.appnativa.rare.spot.Widget;
import com.appnativa.rare.ui.calendar.DateViewManager;
import com.appnativa.rare.viewer.iContainer;

import java.util.Map;

/**
 * A widget that allows a date and optionally a time to be selected. Unlike a spinner
 * this widget utilizes a calendar metaphor. Multiply calendar can be display
 * by choosing a multiple display type and specifying the number of columns
 * and or rows that the chooser will use.
 *
 * @author Don DeCoteau
 */
public class DateChooserWidget extends aDateChooserWidget {

  /**
   * Constructs a new instance
   *
   * @param parent the widget's parent
   */
  public DateChooserWidget(iContainer parent) {
    super(parent);
  }

  /**
   * Sets the month that is displayed for a calendar panel view
   *
   * @param year the year
   * @param month the month (1-12)
   * @param index the index for the panel when multiple panels ares displayed
   *              (use 0 for the first panel, -1 for the last panel, and -2 for the middle panel)
   *
   * @return true if the new month is displayed successfully; false if the new month is out of the valid date range
   */
  public boolean setDisplayedMonth(int year, int month, int index) {
    return false;
  }

  protected void handleCustomProperties(Widget cfg, Map<String, Object> properties) {
    super.handleCustomProperties(cfg, properties);

    Object o = properties.get("android:showSpinners");

    if ((o != null) && (Boolean.FALSE.equals(o) || o.toString().equals("false"))) {
      ((DateViewManager) dateViewManager).setShowSpinners(false);
    }

    o = properties.get("android:showCalendar");

    if ((o != null) && (Boolean.TRUE.equals(o) || o.toString().equals("true"))) {
      dateViewManager.setShowCalendar(true);
    }
  }
}
