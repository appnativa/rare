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

package com.appnativa.rare.spot;

import com.appnativa.spot.*;

//USER_IMPORTS_AND_COMMENTS_MARK{}
//GENERATED_COMMENT{}

/**
 * This class represents the configuration information for a
 * widget that provides charting functionality
 *
 * @author Don DeCoteau
 * @version 2.0
 */
public class Chart extends Viewer {
  //GENERATED_MEMBERS{
//GENERATED_COMMENT{}

  /** Design~~reload: the type of chart */
  public CChartType chartType = new CChartType(null, null, CChartType.line, "line", false);
//GENERATED_COMMENT{}

  /** whether the chart is to be horizontally oriented */
  public SPOTBoolean horizontal = new SPOTBoolean(null, false, false);
//GENERATED_COMMENT{}

  /** whether legends are to be shown */
  public SPOTBoolean showLegends = new SPOTBoolean(null, true, false);
//GENERATED_COMMENT{}

  /** whether tooltips are to be shown */
  public SPOTBoolean showTooltips = new SPOTBoolean(null, true, false);
//GENERATED_COMMENT{}

  /** Appearance~~reload: whether plot labels are shown */
  public SPOTBoolean showPlotLabels = new SPOTBoolean(null, false, false);
//GENERATED_COMMENT{}

  /** whether the chart should be drawn in 3d (if supported) */
  public SPOTBoolean draw3D = new SPOTBoolean(null, false, false);
//GENERATED_COMMENT{}

  /** whether zooming is allowed */
  public SPOTBoolean zoomingAllowed = new SPOTBoolean(null, true, false);
//GENERATED_COMMENT{}

  /** data item representing the domain axis. The time unit attribute is comprised of millisecond second minute hour day week month and year. */
  public DataItem domainAxis = new DataItem();
//GENERATED_COMMENT{}

  /** data item representing the range axis. */
  public DataItem rangeAxis = new DataItem();
//GENERATED_COMMENT{}

  /** data item representing the chart title */
  protected DataItem chartTitle = null;
  public SPOTBoolean autoSort   = new SPOTBoolean(null, false, false);
//GENERATED_COMMENT{}

  /** Appearance~font~reload: text to show when the widget is empty */
  protected EmptyText emptyText = null;
//GENERATED_COMMENT{}

  /** a reference to the plotting information */
  protected Plot plot = null;
//GENERATED_COMMENT{}

  /** a set of sub titles for the chart */
  protected SPOTSet subTitles = null;
//GENERATED_COMMENT{}

  /** a set of annotations for the chart */
  protected SPOTSet annotations = null;
//GENERATED_COMMENT{}

  /** a set of range markers for the chart */
  protected SPOTSet rangeMarkers = null;
//GENERATED_COMMENT{}

  /** a set of domain markers for the chart */
  protected SPOTSet domainMarkers = null;
//GENERATED_COMMENT{}

  /** Behavior~~reload: information specifying if the widget is scrollable and how the associated scroll pane should be configured */
  protected ScrollPane scrollPane = null;

  //}GENERATED_MEMBERS
  //GENERATED_METHODS{

  /**
   * Creates a new optional <code>Chart</code> object.
   */
  public Chart() {
    this(true);
  }

  /**
   * Creates a new <code>Chart</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   */
  public Chart(boolean optional) {
    super(optional, false);
    spot_setElements();
  }

  /**
   * Creates a new <code>Chart</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   * @param setElements  <code>true</code> if a call to setElements should be made; <code>false</code> otherwise)
   */
  protected Chart(boolean optional, boolean setElements) {
    super(optional, setElements);
  }

  /**
   * Adds elements to the object elements map
   *
   */
  protected void spot_setElements() {
    this.elementsSizeHint += 18;
    super.spot_setElements();
    spot_addElement("chartType", chartType);
    chartType.spot_defineAttribute("renderer", null);
    spot_addElement("horizontal", horizontal);
    spot_addElement("showLegends", showLegends);
    showLegends.spot_defineAttribute("location", null);
    spot_addElement("showTooltips", showTooltips);
    spot_addElement("showPlotLabels", showPlotLabels);
    spot_addElement("draw3D", draw3D);
    spot_addElement("zoomingAllowed", zoomingAllowed);
    spot_addElement("domainAxis", domainAxis);
    domainAxis.spot_defineAttribute("timeUnit", null);
    domainAxis.spot_defineAttribute("tickIncrement", null);
    domainAxis.spot_defineAttribute("lowerBound", null);
    domainAxis.spot_defineAttribute("upperBound", null);
    domainAxis.spot_defineAttribute("label", null);
    domainAxis.spot_defineAttribute("labelAngle", null);
    domainAxis.spot_defineAttribute("labelColor", null);
    domainAxis.spot_defineAttribute("labelFont", null);
    domainAxis.spot_defineAttribute("labelsVisible", null);
    domainAxis.spot_defineAttribute("showGridLine", null);
    domainAxis.spot_defineAttribute("showMinorTicks", null);
    spot_addElement("rangeAxis", rangeAxis);
    rangeAxis.spot_defineAttribute("tickIncrement", null);
    rangeAxis.spot_defineAttribute("lowerBound", null);
    rangeAxis.spot_defineAttribute("upperBound", null);
    rangeAxis.spot_defineAttribute("label", null);
    rangeAxis.spot_defineAttribute("labelAngle", null);
    rangeAxis.spot_defineAttribute("labelColor", null);
    rangeAxis.spot_defineAttribute("labelFont", null);
    rangeAxis.spot_defineAttribute("labelsVisible", null);
    rangeAxis.spot_defineAttribute("showGridLine", null);
    rangeAxis.spot_defineAttribute("showMinorTicks", null);
    spot_addElement("chartTitle", chartTitle);
    spot_addElement("autoSort", autoSort);
    spot_addElement("emptyText", emptyText);
    spot_addElement("plot", plot);
    spot_addElement("subTitles", subTitles);
    spot_addElement("annotations", annotations);
    spot_addElement("rangeMarkers", rangeMarkers);
    spot_addElement("domainMarkers", domainMarkers);
    spot_addElement("scrollPane", scrollPane);
  }

  /**
   * Gets the chartTitle element
   *
   * @return the chartTitle element or null if a reference was never created
   */
  public DataItem getChartTitle() {
    return chartTitle;
  }

  /**
   * Gets the reference to the chartTitle element
   * A reference is created if necessary
   *
   * @return the reference to the chartTitle element
   */
  public DataItem getChartTitleReference() {
    if (chartTitle == null) {
      chartTitle = new DataItem(true);
      super.spot_setReference("chartTitle", chartTitle);
      chartTitle.spot_defineAttribute("location", null);
    }

    return chartTitle;
  }

  /**
   * Sets the reference to the chartTitle element
   * @param reference the reference ( can be null)
   *
   * @throws ClassCastException if the parameter is not valid
   */
  public void setChartTitle(iSPOTElement reference) throws ClassCastException {
    chartTitle = (DataItem) reference;
    spot_setReference("chartTitle", reference);
  }

  /**
   * Gets the emptyText element
   *
   * @return the emptyText element or null if a reference was never created
   */
  public EmptyText getEmptyText() {
    return emptyText;
  }

  /**
   * Gets the reference to the emptyText element
   * A reference is created if necessary
   *
   * @return the reference to the emptyText element
   */
  public EmptyText getEmptyTextReference() {
    if (emptyText == null) {
      emptyText = new EmptyText(true);
      super.spot_setReference("emptyText", emptyText);
    }

    return emptyText;
  }

  /**
   * Sets the reference to the emptyText element
   * @param reference the reference ( can be null)
   *
   * @throws ClassCastException if the parameter is not valid
   */
  public void setEmptyText(iSPOTElement reference) throws ClassCastException {
    emptyText = (EmptyText) reference;
    spot_setReference("emptyText", reference);
  }

  /**
   * Gets the plot element
   *
   * @return the plot element or null if a reference was never created
   */
  public Plot getPlot() {
    return plot;
  }

  /**
   * Gets the reference to the plot element
   * A reference is created if necessary
   *
   * @return the reference to the plot element
   */
  public Plot getPlotReference() {
    if (plot == null) {
      plot = new Plot(true);
      super.spot_setReference("plot", plot);
    }

    return plot;
  }

  /**
   * Sets the reference to the plot element
   * @param reference the reference ( can be null)
   *
   * @throws ClassCastException if the parameter is not valid
   */
  public void setPlot(iSPOTElement reference) throws ClassCastException {
    plot = (Plot) reference;
    spot_setReference("plot", reference);
  }

  /**
   * Gets the subTitles element
   *
   * @return the subTitles element or null if a reference was never created
   */
  public SPOTSet getSubTitles() {
    return subTitles;
  }

  /**
   * Gets the reference to the subTitles element
   * A reference is created if necessary
   *
   * @return the reference to the subTitles element
   */
  public SPOTSet getSubTitlesReference() {
    if (subTitles == null) {
      subTitles = new SPOTSet("subTitle", new DataItem(), -1, -1, true);
      super.spot_setReference("subTitles", subTitles);
    }

    return subTitles;
  }

  /**
   * Sets the reference to the subTitles element
   * @param reference the reference ( can be null)
   *
   * @throws ClassCastException if the parameter is not valid
   */
  public void setSubTitles(iSPOTElement reference) throws ClassCastException {
    subTitles = (SPOTSet) reference;
    spot_setReference("subTitles", reference);
  }

  /**
   * Gets the annotations element
   *
   * @return the annotations element or null if a reference was never created
   */
  public SPOTSet getAnnotations() {
    return annotations;
  }

  /**
   * Gets the reference to the annotations element
   * A reference is created if necessary
   *
   * @return the reference to the annotations element
   */
  public SPOTSet getAnnotationsReference() {
    if (annotations == null) {
      annotations = new SPOTSet("annotation", new DataItem(), -1, -1, true);
      super.spot_setReference("annotations", annotations);
    }

    return annotations;
  }

  /**
   * Sets the reference to the annotations element
   * @param reference the reference ( can be null)
   *
   * @throws ClassCastException if the parameter is not valid
   */
  public void setAnnotations(iSPOTElement reference) throws ClassCastException {
    annotations = (SPOTSet) reference;
    spot_setReference("annotations", reference);
  }

  /**
   * Gets the rangeMarkers element
   *
   * @return the rangeMarkers element or null if a reference was never created
   */
  public SPOTSet getRangeMarkers() {
    return rangeMarkers;
  }

  /**
   * Gets the reference to the rangeMarkers element
   * A reference is created if necessary
   *
   * @return the reference to the rangeMarkers element
   */
  public SPOTSet getRangeMarkersReference() {
    if (rangeMarkers == null) {
      rangeMarkers = new SPOTSet("marker", new ItemDescription(), -1, -1, true);
      super.spot_setReference("rangeMarkers", rangeMarkers);
    }

    return rangeMarkers;
  }

  /**
   * Sets the reference to the rangeMarkers element
   * @param reference the reference ( can be null)
   *
   * @throws ClassCastException if the parameter is not valid
   */
  public void setRangeMarkers(iSPOTElement reference) throws ClassCastException {
    rangeMarkers = (SPOTSet) reference;
    spot_setReference("rangeMarkers", reference);
  }

  /**
   * Gets the domainMarkers element
   *
   * @return the domainMarkers element or null if a reference was never created
   */
  public SPOTSet getDomainMarkers() {
    return domainMarkers;
  }

  /**
   * Gets the reference to the domainMarkers element
   * A reference is created if necessary
   *
   * @return the reference to the domainMarkers element
   */
  public SPOTSet getDomainMarkersReference() {
    if (domainMarkers == null) {
      domainMarkers = new SPOTSet("marker", new ItemDescription(), -1, -1, true);
      super.spot_setReference("domainMarkers", domainMarkers);
    }

    return domainMarkers;
  }

  /**
   * Sets the reference to the domainMarkers element
   * @param reference the reference ( can be null)
   *
   * @throws ClassCastException if the parameter is not valid
   */
  public void setDomainMarkers(iSPOTElement reference) throws ClassCastException {
    domainMarkers = (SPOTSet) reference;
    spot_setReference("domainMarkers", reference);
  }

  /**
   * Gets the scrollPane element
   *
   * @return the scrollPane element or null if a reference was never created
   */
  public ScrollPane getScrollPane() {
    return scrollPane;
  }

  /**
   * Gets the reference to the scrollPane element
   * A reference is created if necessary
   *
   * @return the reference to the scrollPane element
   */
  public ScrollPane getScrollPaneReference() {
    if (scrollPane == null) {
      scrollPane = new ScrollPane(true);
      super.spot_setReference("scrollPane", scrollPane);
    }

    return scrollPane;
  }

  /**
   * Sets the reference to the scrollPane element
   * @param reference the reference ( can be null)
   *
   * @throws ClassCastException if the parameter is not valid
   */
  public void setScrollPane(iSPOTElement reference) throws ClassCastException {
    scrollPane = (ScrollPane) reference;
    spot_setReference("scrollPane", reference);
  }

  //}GENERATED_METHODS
  //GENERATED_INNER_CLASSES{

  /**
   * Class that defines the valid set of choices for
   * the <code>Chart.chartType</code> ENUMERATED object
   */
  public static class CChartType extends SPOTEnumerated {
    public final static int line             = 0;
    public final static int bar              = 1;
    public final static int stacked_bar      = 2;
    public final static int range_bar        = 3;
    public final static int pie              = 4;
    public final static int area             = 5;
    public final static int stacked_area     = 6;
    public final static int range_area       = 7;
    public final static int step_area        = 8;
    public final static int step_line        = 9;
    public final static int bubble           = 10;
    public final static int hi_lo            = 11;
    public final static int hi_lo_open_close = 12;
    public final static int candlestick      = 13;
    public final static int funnel           = 14;
    public final static int spline           = 15;
    public final static int spline_area      = 16;
    public final static int point            = 17;
    public final static int rose             = 18;
    public final static int pyramid          = 19;

    /**
     * Creates a new <code>CChartType</code> object
     */
    public CChartType() {
      this(null, null, null, null, true);
    }

    /**
     * Creates a new <code>CChartType</code> object
     *
     * @param val the value
     */
    public CChartType(int val) {
      super();
      _sChoices = _schoices;
      _nChoices = _nchoices;
      spot_setAttributes();
      setValue(val);
    }

    /**
     * Creates a new <code>chartType</code> object
     * the <code>Chart.chartType</code> ENUMERATED object
     *
     * @param ival         the integer value
     * @param sval         the string  value
     * @param idefaultval  the integer default value
     * @param sdefaultval  the string default value
     * @param optional   <code>true</code> if the node the object represents is optional
     */
    public CChartType(Integer ival, String sval, Integer idefaultval, String sdefaultval, boolean optional) {
      super(ival, sval, idefaultval, sdefaultval, optional);
      _sChoices = _schoices;
      _nChoices = _nchoices;
      spot_setAttributes();
    }

    /**
     * Retrieves the range of valid values for the object
     *
     * @return the valid range as a displayable string
     */
    public String spot_getValidityRange() {
      return "{" + "line(0), " + "bar(1), " + "stacked_bar(2), " + "range_bar(3), " + "pie(4), " + "area(5), "
             + "stacked_area(6), " + "range_area(7), " + "step_area(8), " + "step_line(9), " + "bubble(10), "
             + "hi_lo(11), " + "hi_lo_open_close(12), " + "candlestick(13), " + "funnel(14), " + "spline(15), "
             + "spline_area(16), " + "point(17), " + "rose(18), " + "pyramid(19) }";
    }

    private void spot_setAttributes() {
      this.attributeSizeHint += 1;
      spot_defineAttribute("renderer", null);
    }

    private final static int    _nchoices[] = {
      0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19
    };
    private final static String _schoices[] = {
      "line", "bar", "stacked_bar", "range_bar", "pie", "area", "stacked_area", "range_area", "step_area", "step_line",
      "bubble", "hi_lo", "hi_lo_open_close", "candlestick", "funnel", "spline", "spline_area", "point", "rose",
      "pyramid"
    };
  }
  //}GENERATED_INNER_CLASSES
}
