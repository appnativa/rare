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
 * viewer that arranges a section of the screen into a set of
 * regions,
 * <p>
 * only one of which can be selected at a given time. A region
 * can be selected by clicking on its tab
 * </p>
 *
 * @author Don DeCoteau
 * @version 2.0
 */
public class TabPane extends Viewer {
  //GENERATED_MEMBERS{
//GENERATED_COMMENT{}

  /** Appearance~~reload: the position of the tabs in relation to the tab area */
  public CTabPosition tabPosition = new CTabPosition(null, null, CTabPosition.bottom, "bottom", true);
//GENERATED_COMMENT{}

  /** Appearance~~reload: where the tab close button should be placed */
  public CCloseButton closeButton = new CCloseButton(null, null, CCloseButton.none, "none", true);
//GENERATED_COMMENT{}

  /** Appearance~~reload: the style of tab to use */
  public CTabStyle tabStyle = new CTabStyle(null, null, CTabStyle.auto, "auto", false);
//GENERATED_COMMENT{}

  /** Appearance: the tyupe of icon to use for the more table item */
  public CMoreIconType moreIconType = new CMoreIconType(null, null, CMoreIconType.menu, "menu", false);
//GENERATED_COMMENT{}

  /** Appearance~~reload: a painter for the tab buttons */
  protected GridCell tabPainter = null;
//GENERATED_COMMENT{}

  /** Appearance~~reload: a painter for the tab area */
  protected GridCell tabAreaPainter = null;
//GENERATED_COMMENT{}

  /** Appearance~~reload: a painter for the tab buttons */
  protected GridCell selectedTabPainter = null;
//GENERATED_COMMENT{}

  /** Appearance~~reload: whether the selected tab should be bolded */
  public SPOTBoolean boldSelectedTab = new SPOTBoolean(null, true, false);
//GENERATED_COMMENT{}

  /** Appearance~~reload: whether icons should be shown on the tabs */
  public SPOTBoolean showIconsOnTab = new SPOTBoolean(null, true, false);
//GENERATED_COMMENT{}

  /** Design: whether editing of tab titles is allowed */
  public SPOTBoolean tabEditingAllowed = new SPOTBoolean(null, false, false);
//GENERATED_COMMENT{}

  /** Appearance: the height to use for each tab */
  public SPOTPrintableString tabHeight = new SPOTPrintableString(null, 0, 32, true);
//GENERATED_COMMENT{}

  /** Appearance~~selectedTab: the index of the tab that is to be initially selected */
  public SPOTInteger selectedIndex = new SPOTInteger(null, -1, null, 0, false);
//GENERATED_COMMENT{}

  /** Design: the set of tabs */
  public SPOTSet tabs = new SPOTSet("tab", new Tab(), -1, -1, true);
//GENERATED_COMMENT{}

  /** Design~url: URL for an icon to use for tab's that don't define their own */
  public SPOTPrintableString defaultTabIcon = new SPOTPrintableString(null, 0, 255, true);
//GENERATED_COMMENT{}

  /** Behavior: whether the pane should act as a form viewer (if false widgets will be registered with the next higher up form viewer) */
  public SPOTBoolean actAsFormViewer = new SPOTBoolean(null, true, false);
//GENERATED_COMMENT{}

  /** a leading widget for the tab area header */
  public SPOTAny leadingHeaderWidget = new SPOTAny("com.appnativa.rare.spot.Widget", true);
//GENERATED_COMMENT{}

  /** a trailing widget for the tab area header */
  public SPOTAny trailingHeaderWidget = new SPOTAny("com.appnativa.rare.spot.Widget", true);
//GENERATED_COMMENT{}

  /** Behavior: name of the animator to use to animate transitions */
  public SPOTPrintableString transitionAnimator = new SPOTPrintableString();
//GENERATED_COMMENT{}

  /** Appearance: the horizontal alignment of the text */
  public CTextHAlignment textHAlignment = new CTextHAlignment(null, null, CTextHAlignment.auto, "auto", false);
//GENERATED_COMMENT{}

  /** Appearance: the number of visible tabs (when present determines the number of visible tabs when stacking) */
  public SPOTInteger visibleCount = new SPOTInteger(null, 1, 32, true);
//GENERATED_COMMENT{}

  /** Appearance: determines whether the tab strip is resizable when tabs are stacked */
  public SPOTBoolean resizable = new SPOTBoolean(null, false, false);
//GENERATED_COMMENT{}

  /** Appearance: the fraction of the size of a tab icon to use to display the small icon for stacked tabs */
  public SPOTReal smallIconFraction = new SPOTReal(null, "0.1", "1", ".5", false);
//GENERATED_COMMENT{}

  /** Appearance~~reload: a painter for the floor buttons during rollover */
  protected GridCell rolloverTabPainter = null;

  //}GENERATED_MEMBERS
  //GENERATED_METHODS{

  /**
   * Creates a new optional <code>TabPane</code> object.
   */
  public TabPane() {
    this(true);
  }

  /**
   * Creates a new <code>TabPane</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   */
  public TabPane(boolean optional) {
    super(optional, false);
    spot_setElements();
  }

  /**
   * Creates a new <code>TabPane</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   * @param setElements  <code>true</code> if a call to setElements should be made; <code>false</code> otherwise)
   */
  protected TabPane(boolean optional, boolean setElements) {
    super(optional, setElements);
  }

  /**
   * Adds elements to the object elements map
   *
   */
  protected void spot_setElements() {
    this.elementsSizeHint  += 23;
    this.attributeSizeHint += 5;
    super.spot_setElements();
    spot_defineAttribute("onChange", null);
    spot_defineAttribute("onOpened", null);
    spot_defineAttribute("onClosed", null);
    spot_defineAttribute("onWillClose", null);
    spot_defineAttribute("onItemChanged", null);
    spot_addElement("tabPosition", tabPosition);
    spot_addElement("closeButton", closeButton);
    spot_addElement("tabStyle", tabStyle);
    spot_addElement("moreIconType", moreIconType);
    moreIconType.spot_defineAttribute("icon", null);
    spot_addElement("tabPainter", tabPainter);
    spot_addElement("tabAreaPainter", tabAreaPainter);
    spot_addElement("selectedTabPainter", selectedTabPainter);
    spot_addElement("boldSelectedTab", boldSelectedTab);
    spot_addElement("showIconsOnTab", showIconsOnTab);
    spot_addElement("tabEditingAllowed", tabEditingAllowed);
    spot_addElement("tabHeight", tabHeight);
    tabHeight.spot_defineAttribute("touchOnly", null);
    spot_addElement("selectedIndex", selectedIndex);
    spot_addElement("tabs", tabs);
    spot_addElement("defaultTabIcon", defaultTabIcon);
    defaultTabIcon.spot_defineAttribute("alt", null);
    defaultTabIcon.spot_defineAttribute("slice", null);
    defaultTabIcon.spot_defineAttribute("size", null);
    defaultTabIcon.spot_defineAttribute("scaling", null);
    defaultTabIcon.spot_defineAttribute("density", null);
    spot_addElement("actAsFormViewer", actAsFormViewer);
    spot_addElement("leadingHeaderWidget", leadingHeaderWidget);
    spot_addElement("trailingHeaderWidget", trailingHeaderWidget);
    disableBehavior.spot_setDefaultValue(0, "disable_container");
    disableBehavior.spot_setOptional(false);
    spot_addElement("transitionAnimator", transitionAnimator);
    transitionAnimator.spot_defineAttribute("duration", null);
    transitionAnimator.spot_defineAttribute("direction", null);
    transitionAnimator.spot_defineAttribute("acceleration", null);
    transitionAnimator.spot_defineAttribute("deceleration", null);
    transitionAnimator.spot_defineAttribute("horizontal", null);
    transitionAnimator.spot_defineAttribute("fadeIn", null);
    transitionAnimator.spot_defineAttribute("fadeOut", null);
    transitionAnimator.spot_defineAttribute("diagonal", null);
    transitionAnimator.spot_defineAttribute("diagonalAnchor", null);
    transitionAnimator.spot_defineAttribute("autoOrient", null);
    transitionAnimator.spot_defineAttribute("customProperties", null);
    spot_addElement("textHAlignment", textHAlignment);
    spot_addElement("visibleCount", visibleCount);
    spot_addElement("resizable", resizable);
    spot_addElement("smallIconFraction", smallIconFraction);
    spot_addElement("rolloverTabPainter", rolloverTabPainter);
  }

  /**
   * Gets the tabPainter element
   *
   * @return the tabPainter element or null if a reference was never created
   */
  public GridCell getTabPainter() {
    return tabPainter;
  }

  /**
   * Gets the reference to the tabPainter element
   * A reference is created if necessary
   *
   * @return the reference to the tabPainter element
   */
  public GridCell getTabPainterReference() {
    if (tabPainter == null) {
      tabPainter = new GridCell(true);
      super.spot_setReference("tabPainter", tabPainter);
      tabPainter.spot_defineAttribute("foreground", null);
      tabPainter.spot_defineAttribute("font", null);
    }

    return tabPainter;
  }

  /**
   * Sets the reference to the tabPainter element
   * @param reference the reference ( can be null)
   *
   * @throws ClassCastException if the parameter is not valid
   */
  public void setTabPainter(iSPOTElement reference) throws ClassCastException {
    tabPainter = (GridCell) reference;
    spot_setReference("tabPainter", reference);
  }

  /**
   * Gets the tabAreaPainter element
   *
   * @return the tabAreaPainter element or null if a reference was never created
   */
  public GridCell getTabAreaPainter() {
    return tabAreaPainter;
  }

  /**
   * Gets the reference to the tabAreaPainter element
   * A reference is created if necessary
   *
   * @return the reference to the tabAreaPainter element
   */
  public GridCell getTabAreaPainterReference() {
    if (tabAreaPainter == null) {
      tabAreaPainter = new GridCell(true);
      super.spot_setReference("tabAreaPainter", tabAreaPainter);
    }

    return tabAreaPainter;
  }

  /**
   * Sets the reference to the tabAreaPainter element
   * @param reference the reference ( can be null)
   *
   * @throws ClassCastException if the parameter is not valid
   */
  public void setTabAreaPainter(iSPOTElement reference) throws ClassCastException {
    tabAreaPainter = (GridCell) reference;
    spot_setReference("tabAreaPainter", reference);
  }

  /**
   * Gets the selectedTabPainter element
   *
   * @return the selectedTabPainter element or null if a reference was never created
   */
  public GridCell getSelectedTabPainter() {
    return selectedTabPainter;
  }

  /**
   * Gets the reference to the selectedTabPainter element
   * A reference is created if necessary
   *
   * @return the reference to the selectedTabPainter element
   */
  public GridCell getSelectedTabPainterReference() {
    if (selectedTabPainter == null) {
      selectedTabPainter = new GridCell(true);
      super.spot_setReference("selectedTabPainter", selectedTabPainter);
      selectedTabPainter.spot_defineAttribute("foreground", null);
      selectedTabPainter.spot_defineAttribute("font", null);
    }

    return selectedTabPainter;
  }

  /**
   * Sets the reference to the selectedTabPainter element
   * @param reference the reference ( can be null)
   *
   * @throws ClassCastException if the parameter is not valid
   */
  public void setSelectedTabPainter(iSPOTElement reference) throws ClassCastException {
    selectedTabPainter = (GridCell) reference;
    spot_setReference("selectedTabPainter", reference);
  }

  /**
   * Gets the rolloverTabPainter element
   *
   * @return the rolloverTabPainter element or null if a reference was never created
   */
  public GridCell getRolloverTabPainter() {
    return rolloverTabPainter;
  }

  /**
   * Gets the reference to the rolloverTabPainter element
   * A reference is created if necessary
   *
   * @return the reference to the rolloverTabPainter element
   */
  public GridCell getRolloverTabPainterReference() {
    if (rolloverTabPainter == null) {
      rolloverTabPainter = new GridCell(true);
      super.spot_setReference("rolloverTabPainter", rolloverTabPainter);
    }

    return rolloverTabPainter;
  }

  /**
   * Sets the reference to the rolloverTabPainter element
   * @param reference the reference ( can be null)
   *
   * @throws ClassCastException if the parameter is not valid
   */
  public void setRolloverTabPainter(iSPOTElement reference) throws ClassCastException {
    rolloverTabPainter = (GridCell) reference;
    spot_setReference("rolloverTabPainter", reference);
  }

  //}GENERATED_METHODS

  /**
   * Get the index of the named tab
   * @param name the name
   *
   * @return the index of -1
   */
  public int indexOfTab(String name) {
    final int len = tabs.size();
    Tab       tab;

    for (int i = 0; i < len; i++) {
      tab = (Tab) tabs.getEx(i);

      if (tab.name.equals(name)) {
        return i;
      }
    }

    return -1;
  }

  /**
   * Get the named tab
   * @param name the name
   *
   * @return the tab or null
   */
  public Tab getTab(String name) {
    final int len = tabs.size();
    Tab       tab;

    for (int i = 0; i < len; i++) {
      tab = (Tab) tabs.getEx(i);

      if (tab.name.equals(name)) {
        return tab;
      }
    }

    return null;
  }

  //GENERATED_INNER_CLASSES{

  /**
   * Class that defines the valid set of choices for
   * the <code>TabPane.tabPosition</code> ENUMERATED object
   */
  public static class CTabPosition extends SPOTEnumerated {
    public final static int top    = 1;
    public final static int bottom = 2;
    public final static int left   = 3;
    public final static int right  = 4;

    /**
     * Creates a new <code>CTabPosition</code> object
     */
    public CTabPosition() {
      this(null, null, null, null, true);
    }

    /**
     * Creates a new <code>CTabPosition</code> object
     *
     * @param val the value
     */
    public CTabPosition(int val) {
      super();
      _sChoices = _schoices;
      _nChoices = _nchoices;
      setValue(val);
    }

    /**
     * Creates a new <code>tabPosition</code> object
     * the <code>TabPane.tabPosition</code> ENUMERATED object
     *
     * @param ival         the integer value
     * @param sval         the string  value
     * @param idefaultval  the integer default value
     * @param sdefaultval  the string default value
     * @param optional   <code>true</code> if the node the object represents is optional
     */
    public CTabPosition(Integer ival, String sval, Integer idefaultval, String sdefaultval, boolean optional) {
      super(ival, sval, idefaultval, sdefaultval, optional);
      _sChoices = _schoices;
      _nChoices = _nchoices;
    }

    /**
     * Retrieves the range of valid values for the object
     *
     * @return the valid range as a displayable string
     */
    public String spot_getValidityRange() {
      return "{" + "top(1), " + "bottom(2), " + "left(3), " + "right(4) }";
    }

    private final static int    _nchoices[] = { 1, 2, 3, 4 };
    private final static String _schoices[] = { "top", "bottom", "left", "right" };
  }


  /**
   * Class that defines the valid set of choices for
   * the <code>TabPane.closeButton</code> ENUMERATED object
   */
  public static class CCloseButton extends SPOTEnumerated {
    public final static int none   = 0;
    public final static int on_tab = 1;
    public final static int corner = 2;

    /**
     * Creates a new <code>CCloseButton</code> object
     */
    public CCloseButton() {
      this(null, null, null, null, true);
    }

    /**
     * Creates a new <code>CCloseButton</code> object
     *
     * @param val the value
     */
    public CCloseButton(int val) {
      super();
      _sChoices = _schoices;
      _nChoices = _nchoices;
      setValue(val);
    }

    /**
     * Creates a new <code>closeButton</code> object
     * the <code>TabPane.closeButton</code> ENUMERATED object
     *
     * @param ival         the integer value
     * @param sval         the string  value
     * @param idefaultval  the integer default value
     * @param sdefaultval  the string default value
     * @param optional   <code>true</code> if the node the object represents is optional
     */
    public CCloseButton(Integer ival, String sval, Integer idefaultval, String sdefaultval, boolean optional) {
      super(ival, sval, idefaultval, sdefaultval, optional);
      _sChoices = _schoices;
      _nChoices = _nchoices;
    }

    /**
     * Retrieves the range of valid values for the object
     *
     * @return the valid range as a displayable string
     */
    public String spot_getValidityRange() {
      return "{" + "none(0), " + "on_tab(1), " + "corner(2) }";
    }

    private final static int    _nchoices[] = { 0, 1, 2 };
    private final static String _schoices[] = { "none", "on_tab", "corner" };
  }


  /**
   * Class that defines the valid set of choices for
   * the <code>TabPane.tabStyle</code> ENUMERATED object
   */
  public static class CTabStyle extends SPOTEnumerated {
    public final static int auto         = 0;
    public final static int box          = 1;
    public final static int chrome       = 2;
    public final static int flat         = 3;
    public final static int office2003   = 4;
    public final static int rounded_flat = 5;
    public final static int windows      = 6;
    public final static int stacked      = 7;

    /**
     * Creates a new <code>CTabStyle</code> object
     */
    public CTabStyle() {
      this(null, null, null, null, true);
    }

    /**
     * Creates a new <code>CTabStyle</code> object
     *
     * @param val the value
     */
    public CTabStyle(int val) {
      super();
      _sChoices = _schoices;
      _nChoices = _nchoices;
      setValue(val);
    }

    /**
     * Creates a new <code>tabStyle</code> object
     * the <code>TabPane.tabStyle</code> ENUMERATED object
     *
     * @param ival         the integer value
     * @param sval         the string  value
     * @param idefaultval  the integer default value
     * @param sdefaultval  the string default value
     * @param optional   <code>true</code> if the node the object represents is optional
     */
    public CTabStyle(Integer ival, String sval, Integer idefaultval, String sdefaultval, boolean optional) {
      super(ival, sval, idefaultval, sdefaultval, optional);
      _sChoices = _schoices;
      _nChoices = _nchoices;
    }

    /**
     * Retrieves the range of valid values for the object
     *
     * @return the valid range as a displayable string
     */
    public String spot_getValidityRange() {
      return "{" + "auto(0), " + "box(1), " + "chrome(2), " + "flat(3), " + "office2003(4), " + "rounded_flat(5), "
             + "windows(6), " + "stacked(7) }";
    }

    private final static int    _nchoices[] = {
      0, 1, 2, 3, 4, 5, 6, 7
    };
    private final static String _schoices[] = {
      "auto", "box", "chrome", "flat", "office2003", "rounded_flat", "windows", "stacked"
    };
  }


  /**
   * Class that defines the valid set of choices for
   * the <code>TabPane.moreIconType</code> ENUMERATED object
   */
  public static class CMoreIconType extends SPOTEnumerated {
    public final static int menu   = 1;
    public final static int dots   = 2;
    public final static int custom = 3;

    /**
     * Creates a new <code>CMoreIconType</code> object
     */
    public CMoreIconType() {
      this(null, null, null, null, true);
    }

    /**
     * Creates a new <code>CMoreIconType</code> object
     *
     * @param val the value
     */
    public CMoreIconType(int val) {
      super();
      _sChoices = _schoices;
      _nChoices = _nchoices;
      spot_setAttributes();
      setValue(val);
    }

    /**
     * Creates a new <code>moreIconType</code> object
     * the <code>TabPane.moreIconType</code> ENUMERATED object
     *
     * @param ival         the integer value
     * @param sval         the string  value
     * @param idefaultval  the integer default value
     * @param sdefaultval  the string default value
     * @param optional   <code>true</code> if the node the object represents is optional
     */
    public CMoreIconType(Integer ival, String sval, Integer idefaultval, String sdefaultval, boolean optional) {
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
      return "{" + "menu(1), " + "dots(2), " + "custom(3) }";
    }

    private void spot_setAttributes() {
      this.attributeSizeHint += 1;
      spot_defineAttribute("icon", null);
    }

    private final static int    _nchoices[] = { 1, 2, 3 };
    private final static String _schoices[] = { "menu", "dots", "custom" };
  }


  /**
   * Class that defines the valid set of choices for
   * the <code>TabPane.textHAlignment</code> ENUMERATED object
   */
  public static class CTextHAlignment extends SPOTEnumerated {

    /** let the widget decide */
    public final static int auto = 0;

    /** the text is left aligned */
    public final static int left = 1;

    /** the text is right aligned */
    public final static int right = 2;

    /** the text is aligned along the leading edge */
    public final static int leading = 3;

    /** the text is aligned along the trailing edge */
    public final static int trailing = 4;

    /** the text is centered */
    public final static int center = 5;

    /**
     * Creates a new <code>CTextHAlignment</code> object
     */
    public CTextHAlignment() {
      this(null, null, null, null, true);
    }

    /**
     * Creates a new <code>CTextHAlignment</code> object
     *
     * @param val the value
     */
    public CTextHAlignment(int val) {
      super();
      _sChoices = _schoices;
      _nChoices = _nchoices;
      setValue(val);
    }

    /**
     * Creates a new <code>textHAlignment</code> object
     * the <code>TabPane.textHAlignment</code> ENUMERATED object
     *
     * @param ival         the integer value
     * @param sval         the string  value
     * @param idefaultval  the integer default value
     * @param sdefaultval  the string default value
     * @param optional   <code>true</code> if the node the object represents is optional
     */
    public CTextHAlignment(Integer ival, String sval, Integer idefaultval, String sdefaultval, boolean optional) {
      super(ival, sval, idefaultval, sdefaultval, optional);
      _sChoices = _schoices;
      _nChoices = _nchoices;
    }

    /**
     * Retrieves the range of valid values for the object
     *
     * @return the valid range as a displayable string
     */
    public String spot_getValidityRange() {
      return "{" + "auto(0), " + "left(1), " + "right(2), " + "leading(3), " + "trailing(4), " + "center(5) }";
    }

    private final static int    _nchoices[] = {
      0, 1, 2, 3, 4, 5
    };
    private final static String _schoices[] = {
      "auto", "left", "right", "leading", "trailing", "center"
    };
  }
  //}GENERATED_INNER_CLASSES
}
