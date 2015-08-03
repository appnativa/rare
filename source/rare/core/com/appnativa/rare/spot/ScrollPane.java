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
 * scroll pane that can be used
 * <p>
 * as part of a widget or viewer that inherently supports
 * scrolling
 * </p>
 *
 * @author Don DeCoteau
 * @version 2.0
 */
public class ScrollPane extends SPOTSequence {
  //GENERATED_MEMBERS{
//GENERATED_COMMENT{}

  /** horizontal scrollbar policy */
  public CHorizontalScrollbar horizontalScrollbar = new CHorizontalScrollbar(null, null,
                                                      CHorizontalScrollbar.show_as_needed, "show_as_needed", false);
//GENERATED_COMMENT{}

  /** vertical scrollbar policy */
  public CVerticalScrollbar verticalScrollbar = new CVerticalScrollbar(null, null, CVerticalScrollbar.show_as_needed,
                                                  "show_as_needed", false);
//GENERATED_COMMENT{}

  /** widget to use as the column header for the scroll pane it scrolls with when the main widget scrolls */
  public SPOTAny columnHeader = new SPOTAny("com.appnativa.rare.spot.Widget", true);
//GENERATED_COMMENT{}

  /** widget to use as the row header for the scroll pane it scrolls with when the main widget scrolls */
  public SPOTAny rowHeader = new SPOTAny("com.appnativa.rare.spot.Widget", true);
//GENERATED_COMMENT{}

  /** widget to use as a fixed column footer */
  public SPOTAny columnFooter = new SPOTAny("com.appnativa.rare.spot.Widget", true);
//GENERATED_COMMENT{}

  /** widget to use as a fixed row footer (does not necessarily scroll when the main widget scrolls) */
  public SPOTAny rowFooter = new SPOTAny("com.appnativa.rare.spot.Widget", true);
//GENERATED_COMMENT{}

  /** upper right corner widget (when a corner is needed) */
  public SPOTAny urCorner = new SPOTAny("com.appnativa.rare.spot.Widget", true);
//GENERATED_COMMENT{}

  /** lower right corner widget (when a corner is needed) */
  public SPOTAny lrCorner = new SPOTAny("com.appnativa.rare.spot.Widget", true);
//GENERATED_COMMENT{}

  /** whether scroll wheel scrolling is enabled */
  public SPOTBoolean wheelScrollingAllowed = new SPOTBoolean(null, true, false);
//GENERATED_COMMENT{}

  /** whether the vertical scrollbar should be extended to encompass column headers */
  public SPOTBoolean extendVerticalScrollbar = new SPOTBoolean();
//GENERATED_COMMENT{}

  /** whether the horizontal scrollbar should be extended to encompass row headers and footers */
  public SPOTBoolean extendHorizontalScrollbar = new SPOTBoolean();
//GENERATED_COMMENT{}

  /** Design~~reload: name of an object template to use to customize the scrollpane */
  public SPOTPrintableString templateName = new SPOTPrintableString(null, 0, 64, true);

  //}GENERATED_MEMBERS
  //GENERATED_METHODS{

  /**
   * Creates a new optional <code>ScrollPane</code> object.
   */
  public ScrollPane() {
    this(true);
  }

  /**
   * Creates a new <code>ScrollPane</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   */
  public ScrollPane(boolean optional) {
    super(optional, false);
    spot_setElements();
  }

  /**
   * Creates a new <code>ScrollPane</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   * @param setElements  <code>true</code> if a call to setElements should be made; <code>false</code> otherwise)
   */
  protected ScrollPane(boolean optional, boolean setElements) {
    super(optional, setElements);
  }

  /**
   * Adds elements to the object elements map
   *
   */
  protected void spot_setElements() {
    this.elementsSizeHint += 12;
    super.spot_setElements();
    spot_addElement("horizontalScrollbar", horizontalScrollbar);
    horizontalScrollbar.spot_defineAttribute("onChange", null);
    horizontalScrollbar.spot_defineAttribute("onConfigure", null);
    horizontalScrollbar.spot_defineAttribute("onOverScroll", null);
    horizontalScrollbar.spot_defineAttribute("ui", null);
    horizontalScrollbar.spot_defineAttribute("adjPrefSizeForHidden", null);
    horizontalScrollbar.spot_defineAttribute("opaque", "true");
    spot_addElement("verticalScrollbar", verticalScrollbar);
    verticalScrollbar.spot_defineAttribute("onChange", null);
    verticalScrollbar.spot_defineAttribute("onConfigure", null);
    verticalScrollbar.spot_defineAttribute("onOverScroll", null);
    verticalScrollbar.spot_defineAttribute("ui", null);
    verticalScrollbar.spot_defineAttribute("adjPrefSizeForHidden", null);
    verticalScrollbar.spot_defineAttribute("opaque", "true");
    spot_addElement("columnHeader", columnHeader);
    spot_addElement("rowHeader", rowHeader);
    spot_addElement("columnFooter", columnFooter);
    spot_addElement("rowFooter", rowFooter);
    spot_addElement("urCorner", urCorner);
    urCorner.spot_defineAttribute("always", null);
    spot_addElement("lrCorner", lrCorner);
    lrCorner.spot_defineAttribute("always", null);
    spot_addElement("wheelScrollingAllowed", wheelScrollingAllowed);
    spot_addElement("extendVerticalScrollbar", extendVerticalScrollbar);
    spot_addElement("extendHorizontalScrollbar", extendHorizontalScrollbar);
    spot_addElement("templateName", templateName);
    templateName.spot_defineAttribute("context", null);
  }

  //}GENERATED_METHODS
  public boolean isAlwaysHidden() {
    return (verticalScrollbar.intValue() == CVerticalScrollbar.hidden)
           && (horizontalScrollbar.intValue() == CHorizontalScrollbar.hidden);
  }

  public boolean isAlwaysVisible() {
    return (verticalScrollbar.intValue() == CVerticalScrollbar.show_always)
           && (horizontalScrollbar.intValue() == CHorizontalScrollbar.show_always);
  }

  public boolean isHorizontalScrollEnabled() {
    return horizontalScrollbar.intValue() != CHorizontalScrollbar.hidden;
  }

  public boolean isVerticalScrollEnabled() {
    return verticalScrollbar.intValue() != CVerticalScrollbar.hidden;
  }

  //GENERATED_INNER_CLASSES{

  /**
   * Class that defines the valid set of choices for
   * the <code>ScrollPane.horizontalScrollbar</code> ENUMERATED object
   */
  public static class CHorizontalScrollbar extends SPOTEnumerated {
    public final static int hidden         = 0;
    public final static int show_as_needed = 1;
    public final static int show_always    = 2;

    /**
     * Creates a new <code>CHorizontalScrollbar</code> object
     */
    public CHorizontalScrollbar() {
      this(null, null, null, null, true);
    }

    /**
     * Creates a new <code>CHorizontalScrollbar</code> object
     *
     * @param val the value
     */
    public CHorizontalScrollbar(int val) {
      super();
      _sChoices = _schoices;
      _nChoices = _nchoices;
      spot_setAttributes();
      setValue(val);
    }

    /**
     * Creates a new <code>horizontalScrollbar</code> object
     * the <code>ScrollPane.horizontalScrollbar</code> ENUMERATED object
     *
     * @param ival         the integer value
     * @param sval         the string  value
     * @param idefaultval  the integer default value
     * @param sdefaultval  the string default value
     * @param optional   <code>true</code> if the node the object represents is optional
     */
    public CHorizontalScrollbar(Integer ival, String sval, Integer idefaultval, String sdefaultval, boolean optional) {
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
      return "{" + "hidden(0), " + "show_as_needed(1), " + "show_always(2) }";
    }

    private void spot_setAttributes() {
      this.attributeSizeHint += 6;
      spot_defineAttribute("onChange", null);
      spot_defineAttribute("onConfigure", null);
      spot_defineAttribute("onOverScroll", null);
      spot_defineAttribute("ui", null);
      spot_defineAttribute("adjPrefSizeForHidden", null);
      spot_defineAttribute("opaque", "true");
    }

    private final static int    _nchoices[] = { 0, 1, 2 };
    private final static String _schoices[] = { "hidden", "show_as_needed", "show_always" };
  }


  /**
   * Class that defines the valid set of choices for
   * the <code>ScrollPane.verticalScrollbar</code> ENUMERATED object
   */
  public static class CVerticalScrollbar extends SPOTEnumerated {
    public final static int hidden         = 0;
    public final static int show_as_needed = 1;
    public final static int show_always    = 2;

    /**
     * Creates a new <code>CVerticalScrollbar</code> object
     */
    public CVerticalScrollbar() {
      this(null, null, null, null, true);
    }

    /**
     * Creates a new <code>CVerticalScrollbar</code> object
     *
     * @param val the value
     */
    public CVerticalScrollbar(int val) {
      super();
      _sChoices = _schoices;
      _nChoices = _nchoices;
      spot_setAttributes();
      setValue(val);
    }

    /**
     * Creates a new <code>verticalScrollbar</code> object
     * the <code>ScrollPane.verticalScrollbar</code> ENUMERATED object
     *
     * @param ival         the integer value
     * @param sval         the string  value
     * @param idefaultval  the integer default value
     * @param sdefaultval  the string default value
     * @param optional   <code>true</code> if the node the object represents is optional
     */
    public CVerticalScrollbar(Integer ival, String sval, Integer idefaultval, String sdefaultval, boolean optional) {
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
      return "{" + "hidden(0), " + "show_as_needed(1), " + "show_always(2) }";
    }

    private void spot_setAttributes() {
      this.attributeSizeHint += 6;
      spot_defineAttribute("onChange", null);
      spot_defineAttribute("onConfigure", null);
      spot_defineAttribute("onOverScroll", null);
      spot_defineAttribute("ui", null);
      spot_defineAttribute("adjPrefSizeForHidden", null);
      spot_defineAttribute("opaque", "true");
    }

    private final static int    _nchoices[] = { 0, 1, 2 };
    private final static String _schoices[] = { "hidden", "show_as_needed", "show_always" };
  }
  //}GENERATED_INNER_CLASSES
}
