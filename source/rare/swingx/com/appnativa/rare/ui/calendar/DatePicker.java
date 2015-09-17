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

import com.appnativa.rare.ui.ColorUtils;
import com.appnativa.rare.ui.Component;
import com.appnativa.rare.ui.FontUtils;

import org.jdesktop.swingx.JXMonthView;

public class DatePicker extends Component {
  JXMonthView monthView;

  public DatePicker() {
    super(new JXMonthView());

    JXMonthView mv = (JXMonthView) view;

    mv.setMonthStringBackground(ColorUtils.getBackground());
    mv.setMonthStringForeground(ColorUtils.getForeground());
    mv.setForeground(ColorUtils.getForeground());
    mv.setFont(FontUtils.getDefaultFont());
  }

  public JXMonthView getMonthView() {
    return ((JXMonthView) view);
  }
}
