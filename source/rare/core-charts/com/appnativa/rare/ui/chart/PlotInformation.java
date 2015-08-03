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

import com.appnativa.rare.ui.ScreenUtils;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIFont;
import com.appnativa.rare.ui.UIImage;
import com.appnativa.rare.ui.UIInsets;
import com.appnativa.rare.ui.UIPoint;
import com.appnativa.rare.ui.UIStroke;
import com.appnativa.rare.ui.iBorder;

/**
 * This class represents the plot information for a chart
 *
 * @author Don DeCoteau
 */
public class PlotInformation {
  private float      foregroundAlpha  = 1.0f;
  private float      lineThickness    = ScreenUtils.PLATFORM_PIXELS_1;
  private float      outlineThickness = ScreenUtils.PLATFORM_PIXELS_1;
  private LabelType  labelType        = LabelType.VALUES;
  private ShapeStyle shapeStyle       = null;
  private boolean    showGridLines    = true;
  private UIColor    bgColor;
  private UIImage    bgImage;
  private UIColor    gridColor;
  private UIStroke   gridStroke;
  private UIColor    labelsBackground;
  private iBorder    labelsBorder;
  private UIFont     labelsFont;
  private UIColor    labelsForeground;
  private String     labelsFormat;
  private UIPoint    labelsOffset;
  private UIInsets   margin;
  private UIColor    outlineColor;
  private UIColor    fillColor;
  private UIColor    borderColor;

  /**
   * Label types for the plot points
   */
  public static enum LabelType {

    /** use the item values as the label */
    VALUES,

    /** use the item tooltips as the label */
    TOOLTIPS,

    /** use the item linked datas as the label */
    LINKED_DATA
  }

  /**
   * UIShape style for plot points
   */
  public static enum ShapeStyle {

    /* no shape */
    NONE,

    /** shape is filled */
    FILLED,

    /** shape is outlined */
    OUTLINED,

    /** shape is filled and outlined */
    FILLED_AND_OUTLINED
  }

  public PlotInformation() {}

  /**
   * Constructs a new instance.
   *
   * @param bg the background color of the plot
   * @param gc the grid line color of the plot
   * @param margin the margin separating the plot area from the char's border
   * @param stroke the line stroke for plot lines
   * @param bgimage the background image for the plot area
   * @param ss the shape style of plot shapes
   */
  public PlotInformation(UIColor bg, UIColor gc, UIInsets margin, UIStroke stroke, UIImage bgimage, ShapeStyle ss) {
    bgColor     = bg;
    gridColor   = gc;
    gridStroke  = stroke;
    this.margin = margin;
    bgImage     = bgimage;
    shapeStyle  = ss;
  }

  /**
   * Gets the background color for the plot area.
   *
   * @param color the background color for the plot area
   */
  public void setBackgroundColor(UIColor color) {
    this.bgColor = color;
  }

  /**
   * Sets the background image for the plot area.
   *
   * @param bgImage the background image for the plot area
   */
  public void setBackgroundImage(UIImage bgImage) {
    this.bgImage = bgImage;
  }

  /**
   * Set the foreground color alpha component for the plot
   * @param foregroundAlpha the foreground color alpha component for the plot
   */
  public void setForegroundAlpha(float foregroundAlpha) {
    this.foregroundAlpha = foregroundAlpha;
  }

  /**
   * Sets the grid line color for the plot area.
   *
   * @param color the grid line color for the plot area
   */
  public void setGridColor(UIColor color) {
    this.gridColor = color;
  }

  /**
   * Sets the grid line stroke for the plot area.
   *
   * @param stroke the grid line stroke for the plot area
   */
  public void setGridStroke(UIStroke stroke) {
    this.gridStroke = stroke;
  }

  /**
   * Sets the type of labels for plot points.
   *
   * @param type the type of labels for plot points
   */
  public void setLabelType(LabelType type) {
    this.labelType = type;
  }

  /**
   * @param labelsBackground the labelsBackground to set
   */
  public void setLabelsBackground(UIColor labelsBackground) {
    this.labelsBackground = labelsBackground;
  }

  /**
   * @param labelsBorder the labelsBorder to set
   */
  public void setLabelsBorder(iBorder labelsBorder) {
    this.labelsBorder = labelsBorder;
  }

  /**
   * @param labelsFont the labelsFont to set
   */
  public void setLabelsFont(UIFont labelsFont) {
    this.labelsFont = labelsFont;
  }

  /**
   * @param labelsForeground the labelsForeground to set
   */
  public void setLabelsForeground(UIColor labelsForeground) {
    this.labelsForeground = labelsForeground;
  }

  /**
   * @param labelsFormat the labelsFormat to set
   */
  public void setLabelsFormat(String labelsFormat) {
    this.labelsFormat = labelsFormat;
  }

  /**
   * @param labelsOffset the labelsOffset to set
   */
  public void setLabelsOffset(UIPoint labelsOffset) {
    this.labelsOffset = labelsOffset;
  }

  /**
   * Sets the plot line thickness
   * @param lineThickness the plot line thickness
   */
  public void setLineThickness(float lineThickness) {
    this.lineThickness = lineThickness;
  }

  /**
   * Sets the margin separating the plot area from the chart's border.
   *
   * @param margin the margin
   */
  public void setMargin(UIInsets margin) {
    this.margin = margin;
  }

  /**
   * @param outlineColor the outlineColor to set
   */
  public void setOutlineColor(UIColor outlineColor) {
    this.outlineColor = outlineColor;
  }

  /**
   * Sets the outline thickness for a plot point
   * @param outlineThickness the outline thickness for a plot point
   */
  public void setOutlineThickness(float outlineThickness) {
    this.outlineThickness = outlineThickness;
  }

  /**
   * Sets the fill color for filled shapes
   * @param fillColor the fillColor to set
   */
  public void setFillColor(UIColor fillColor) {
    this.fillColor = fillColor;
  }

  /**
   * Sets the shape style for plot points.
   *
   * @param style the shape style for plot points
   */
  public void setShapeStyle(ShapeStyle style) {
    this.shapeStyle = style;
  }

  /**
   * @param showGridLines the showGridLines to set
   */
  public void setShowGridLines(boolean showGridLines) {
    this.showGridLines = showGridLines;
  }

  /**
   * Gets the background color for the plot area.
   *
   * @return the background color for the plot area
   */
  public UIColor getBackgroundColor() {
    return bgColor;
  }

  /**
   * Gets the background image for the plot area.
   *
   * @return the background image for the plot area
   */
  public UIImage getBackgroundImage() {
    return bgImage;
  }

  /**
   * Gets the foreground color alpha component for the plot
   * @return the foreground color alpha component for the plot
   */
  public float getForegroundAlpha() {
    return foregroundAlpha;
  }

  /**
   * Gets the grid line color for the plot area.
   *
   * @return the grid line color for the plot area
   */
  public UIColor getGridColor() {
    return gridColor;
  }

  /**
   * Gets the grid line stroke for the plot area.
   *
   * @return the grid line stroke for the plot area
   */
  public UIStroke getGridStroke() {
    return gridStroke;
  }

  /**
   * Gets type of labels for plot points.
   *
   * @return type of labels for plot points
   */
  public LabelType getLabelType() {
    return (labelType == null)
           ? LabelType.VALUES
           : labelType;
  }

  /**
   * @return the labelsBackground
   */
  public UIColor getLabelsBackground() {
    return labelsBackground;
  }

  /**
   * @return the labelsBorder
   */
  public iBorder getLabelsBorder() {
    return labelsBorder;
  }

  /**
   * @return the labelsFont
   */
  public UIFont getLabelsFont() {
    return labelsFont;
  }

  /**
   * @return the labelsForeground
   */
  public UIColor getLabelsForeground() {
    return labelsForeground;
  }

  /**
   * @return the labelsFormat
   */
  public String getLabelsFormat() {
    return labelsFormat;
  }

  /**
   * @return the labelsOffset
   */
  public UIPoint getLabelsOffset() {
    return labelsOffset;
  }

  /**
   * Gets the plot line thickness
   * @return the plot line thickness
   */
  public float getLineThickness() {
    return lineThickness;
  }

  /**
   * Gets the margin separating the plot area from the chart's border.
   *
   * @return the margin
   */
  public UIInsets getMargin() {
    return margin;
  }

  /**
   * @return the outlineColor
   */
  public UIColor getOutlineColor() {
    return outlineColor;
  }

  /**
   * Gets the outline thickness for a plot point
   * @return the outline thickness for a plot point
   */
  public float getOutlineThickness() {
    return outlineThickness;
  }

  /**
   * Gets the fill color for filled shapes
   * @return the color
   */
  public UIColor getFillColor() {
    return fillColor;
  }

  /**
   * Gets the shape style for plot points.
   * Return FILLED as the default
   *
   * @return the shape style for plot points
   */
  public ShapeStyle getShapeStyle() {
    return (shapeStyle == null)
           ? ShapeStyle.FILLED
           : shapeStyle;
  }

  /**
   * Gets the shape style for plot points.
   * Return NONE as the default
   *
   * @return the shape style for plot points or null if none  was set
   */
  public ShapeStyle getShapeStyleEx() {
    return (shapeStyle == null)
           ? ShapeStyle.NONE
           : shapeStyle;
  }

  /**
   * @return the showGridLines
   */
  public boolean isShowGridLines() {
    return showGridLines;
  }

  /**
   * Gets the border color for the plot
   * @return the border color
   */
  public UIColor getBorderColor() {
    return borderColor;
  }

  /**
   * Sets the border color for the plot
   * @param borderColor the border color
   */
  public void setBorderColor(UIColor borderColor) {
    this.borderColor = borderColor;
  }
}
