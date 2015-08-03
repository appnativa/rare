/*
 * @(#)DatePicker.java
 * 
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.ui.calendar;

import org.jdesktop.swingx.JXMonthView;

import com.appnativa.rare.ui.ColorUtils;
import com.appnativa.rare.ui.Component;
import com.appnativa.rare.ui.FontUtils;

public class DatePicker extends Component {
  JXMonthView monthView;

  public DatePicker() {
    super(new JXMonthView());
    JXMonthView mv=(JXMonthView)view;
    mv.setMonthStringBackground(ColorUtils.getBackground());
    mv.setMonthStringForeground(ColorUtils.getForeground());
    mv.setForeground(ColorUtils.getForeground());
    mv.setFont(FontUtils.getDefaultFont());
  }

  public JXMonthView getMonthView() {
    return ((JXMonthView) view);
  }
 
}