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

package com.appnativa.rare.ui.chart;

import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIColorHelper;
import com.appnativa.rare.ui.UIFont;
import com.appnativa.rare.ui.UIFontHelper;
import com.appnativa.rare.ui.iPlatformIcon;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.spot.iSPOTElement;
import com.appnativa.util.SNumber;

import java.text.ParseException;

import java.util.Locale;

/**
 * This class represents an axis on a chart
 *
 * @author Don DeCoteau
 */
public class ChartAxis extends ChartDataItem {
  private double             increment;
  private RenderableDataItem lowerBounds;
  private TimeUnit           timeUnit;
  private RenderableDataItem upperBounds;
  private boolean            rangeAxis;
  private int                angle;
  private UIFont             labelFont;
  private UIColor            labelColor;
  private boolean            labelsVisible;
  private boolean            showGridLine   = true;
  private boolean            showMinorTicks = true;
  private int                ticksPerDataPoint;

  /**
   * @return the rangeAxis
   */
  public boolean isRangeAxis() {
    return rangeAxis;
  }

  /**
   * @param rangeAxis the rangeAxis to set
   */
  public void setRangeAxis(boolean rangeAxis) {
    this.rangeAxis = rangeAxis;
  }

  /**
   * @return the angle
   */
  public int getAngle() {
    return angle;
  }

  /**
   * @param angle the angle to set
   */
  public void setAngle(int angle) {
    this.angle = angle;
  }

  /**
   * @return the labelFont
   */
  public UIFont getLabelFont() {
    return labelFont;
  }

  /**
   * @param labelFont the labelFont to set
   */
  public void setLabelFont(UIFont labelFont) {
    this.labelFont = labelFont;
  }

  /**
   * @return the labelColor
   */
  public UIColor getLabelColor() {
    return labelColor;
  }

  /**
   * @param labelColor the labelColor to set
   */
  public void setLabelColor(UIColor labelColor) {
    this.labelColor = labelColor;
  }

  /**
   * @return the labelsVisible
   */
  public boolean isLabelsVisible() {
    return labelsVisible;
  }

  /**
   * @param labelsVisible the labelsVisible to set
   */
  public void setLabelsVisible(boolean labelsVisible) {
    this.labelsVisible = labelsVisible;
  }

  /**
   * Time unit definitions
   */
  public static enum TimeUnit {
    NONE, MILLISECOND, SECOND, MINUTE, HOUR, DAY, WEEK, MONTH, YEAR
  }

  /**
   * Constructs a new instance
   */
  public ChartAxis() {
    super();
  }

  /**
   * Constructs a new instance
   *
   * @param label the axis label
   * @param type the axis value type
   * @param data data associated with the axis
   * @param icon the icon for the axis
   */
  public ChartAxis(String label, int type, Object data, iPlatformIcon icon) {
    super(label, type, data, icon);
  }

  /**
   * Returns the value type for the axis
   *
   * @return the value type for the axis
   */
  public int getAxisType() {
    return getDomainType();
  }

  /**
   * Gets the increment for axis values
   *
   * @return the increment for axis values
   */
  public double getIncrement() {
    return increment;
  }

  /**
   * Get the axis label
   *
   * @return the axis label
   */
  public String getLabel() {
    return (theValue == null)
           ? ""
           : theValue.toString();
  }

  @Override
  public String toString() {
    return (theValue == null)
           ? ""
           : theValue.toString();
  }

  /**
   * Gets the item representing the lower bounds of the axis.
   *
   * @return the lower bounds of the axis
   */
  public RenderableDataItem getLowerBounds() {
    return lowerBounds;
  }

  /**
   * Gets the time unit for axis values (if the axis is time-based)
   *
   * @return the time unit for axis values
   */
  public TimeUnit getTimeUnit() {
    return timeUnit;
  }

  /**
   * Gets the item representing the upper bounds of the axis.
   *
   * @return the upper bounds of the axis
   */
  public RenderableDataItem getUpperBounds() {
    return upperBounds;
  }

  /**
   * Handles the extraction of attributes from
   * an axis configuration element.
   *
   * @param context the context
   * @param elem the configuration element
   * @throws ParseException
   */
  public void handleAttributes(iWidget context, iSPOTElement elem) throws ParseException {
    String s = elem.spot_getAttribute("timeUnit");

    if ((s != null) && (s.length() == 0)) {
      s = null;
    }

    if (s != null) {
      s = s.toUpperCase(Locale.ENGLISH);

      if (s.equals("SEC") || s.equals("S")) {
        s = "SECOND";
      } else if (s.equals("MS")) {
        s = "MILLISECOND";
      }

      timeUnit = TimeUnit.valueOf(s);
    } else {
      timeUnit = null;
    }

    angle = SNumber.intValue(elem.spot_getAttribute("labelAngle"));

    String l = elem.spot_getAttribute("lowerBound");
    String u = elem.spot_getAttribute("upperBound");

    s = elem.spot_getAttribute("tickIncrement");

    double inc = 0;

    if (s != null) {
      inc = SNumber.doubleValue(s);
    }

    setBounds(l, u, inc);
    s = elem.spot_getAttribute("labelFont");

    UIFont f = UIFontHelper.parseFont(context, s);

    if (f != null) {
      labelFont = f;
    }

    s = elem.spot_getAttribute("labelColor");

    if (s != null) {
      labelColor = UIColorHelper.getColor(s);
    }

    s = elem.spot_getAttribute("ticksPerDataPoint");

    if (s != null) {
      ticksPerDataPoint = SNumber.intValue(s);
    }

    s = elem.spot_getAttribute("labelsVisible");
    setLabelsVisible(!"false".equalsIgnoreCase(s));
    s = elem.spot_getAttribute("showGridLine");
    setShowGridLine(!"false".equalsIgnoreCase(s));
    s = elem.spot_getAttribute("showMinorTicks");
    setShowMinorTicks(!"false".equalsIgnoreCase(s));
  }

  /**
   * Sets the lower and upper bounds as well as the increment
   * value to use to create intermediate values
   *
   * @param lower the lower bounds
   * @param upper the upper bounds
   * @param increment the increment
   */
  public void setBounds(Object lower, Object upper, Double increment) {
    Object l;
    Object u;

    if (lower instanceof RenderableDataItem) {
      l = ((RenderableDataItem) lower).getValue();
    } else {
      l = lower;
    }

    if (upper instanceof RenderableDataItem) {
      u = ((RenderableDataItem) upper).getValue();
    } else {
      u = upper;
    }

    if (l != null) {
      lowerBounds = new RenderableDataItem(l);
      lowerBounds.setType(getDomainType());
      lowerBounds.setDataConverter(getDomainDataConverter());
      lowerBounds.setValueContext(getDomainContext());
      lowerBounds.setConverterClass(getDomainConverterClass());
    }

    if (u != null) {
      upperBounds = new RenderableDataItem(u);
      upperBounds.setType(getDomainType());
      upperBounds.setDataConverter(getDomainDataConverter());
      upperBounds.setValueContext(getDomainContext());
      upperBounds.setConverterClass(getDomainConverterClass());
    }

    if (increment != null) {
      this.increment = increment;
    }
  }

  /**
   * Sets the increment value to use to create intermediate
   * axis values
   *
   * @param increment the increment value
   */
  public void setIncrement(double increment) {
    this.increment = increment;
  }

  /**
   * Sets the axis label
   *
   * @param label the axis label
   */
  public void setLabel(String label) {
    theValue = label;
    setConverted(true);
  }

  /**
   * Sets the time unit for the axis
   *
   * @param unit the time unit as a string
   */
  public void setTimeUnit(String unit) {
    if ((unit != null) && (unit.length() == 0)) {
      unit = null;
    }

    if (unit != null) {
      unit = unit.toUpperCase(Locale.ENGLISH);

      if (unit.equals("SEC") || unit.equals("S")) {
        unit = "SECOND";
      } else if (unit.equals("MS")) {
        unit = "MILLISECOND";
      }

      timeUnit = TimeUnit.valueOf(unit);
    } else {
      timeUnit = TimeUnit.DAY;
    }
  }

  /**
   * Sets the time unit for the axis
   *
   * @param unit the time unit
   */
  public void setTimeUnit(TimeUnit unit) {
    this.timeUnit = unit;
  }

  public boolean isShowGridLine() {
    return showGridLine;
  }

  public void setShowGridLine(boolean showGridLine) {
    this.showGridLine = showGridLine;
  }

  public boolean isShowMinorTicks() {
    return showMinorTicks;
  }

  public void setShowMinorTicks(boolean showMinorTicks) {
    this.showMinorTicks = showMinorTicks;
  }

  public int getTicksPerDataPoint() {
    return ticksPerDataPoint;
  }

  public void setTicksPerDataPoint(int ticksPerDataPoint) {
    this.ticksPerDataPoint = ticksPerDataPoint;
  }
}
