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
 * This class represents the configuration for a button widget
 *
 * @author Don DeCoteau
 * @version 2.0
 */
public class PushButton extends Button {
  //GENERATED_MEMBERS{
//GENERATED_COMMENT{}

  /** Behavior: the type of action for the button */
  public CActionType actionType = new CActionType(null, null, CActionType.scripted, "scripted", false);
//GENERATED_COMMENT{}

  /** Appearance~~reload: the style of button */
  public CButtonStyle buttonStyle = new CButtonStyle(null, null, CButtonStyle.standard, "standard", false);
//GENERATED_COMMENT{}

  /** Appearance~~reload: how to scale the widget's icon */
  public COrientation orientation = new COrientation(null, null, COrientation.auto, "auto", false);
//GENERATED_COMMENT{}

  /** default menu index for popup menu action types */
  public SPOTInteger defaultMenuIndex = new SPOTInteger(null, -1, 100, -1, false);
//GENERATED_COMMENT{}

  /** Appearance: the definition of the widget for a popup_widget button */
  public SPOTAny popupWidget = new SPOTAny("com.appnativa.rare.spot.Widget", true);
//GENERATED_COMMENT{}

  /** Behavior: class name of the animator to use to animate transitions */
  public SPOTPrintableString popupAnimator = new SPOTPrintableString();
//GENERATED_COMMENT{}

  /** Behavior: whether the button represents a focused action (will only be enabled when a supported component is focused) */
  public SPOTBoolean focusedAction = new SPOTBoolean(null, false, false);
//GENERATED_COMMENT{}

  /** Behavior: whether the action is only enabled when there is a selection is a supported component */
  public SPOTBoolean enabledOnSelectionOnly = new SPOTBoolean(null, false, false);
//GENERATED_COMMENT{}

  /** Behavior: whether the button's action repeats when the button is held down */
  public SPOTBoolean actionRepeats = new SPOTBoolean(null, false, false);
//GENERATED_COMMENT{}

  /** Behavior: whether a shared border will be use when a popup widget is displayed (the border will encompass both the button and the popup widget) */
  public SPOTBoolean useSharedBorderForPopup = new SPOTBoolean(null, true, false);

  //}GENERATED_MEMBERS
  //GENERATED_METHODS{

  /**
   * Creates a new optional <code>PushButton</code> object.
   */
  public PushButton() {
    this(true);
  }

  /**
   * Creates a new <code>PushButton</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   */
  public PushButton(boolean optional) {
    super(optional, false);
    spot_setElements();
  }

  /**
   * Creates a new <code>PushButton</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   * @param setElements  <code>true</code> if a call to setElements should be made; <code>false</code> otherwise)
   */
  protected PushButton(boolean optional, boolean setElements) {
    super(optional, setElements);
  }

  /**
   * Adds elements to the object elements map
   *
   */
  protected void spot_setElements() {
    this.elementsSizeHint  += 10;
    this.attributeSizeHint += 2;
    super.spot_setElements();
    spot_defineAttribute("onWillExpand", null);
    spot_defineAttribute("onWillCollapse", null);
    spot_addElement("actionType", actionType);
    spot_addElement("buttonStyle", buttonStyle);
    spot_addElement("orientation", orientation);
    spot_addElement("defaultMenuIndex", defaultMenuIndex);
    spot_addElement("popupWidget", popupWidget);
    popupWidget.spot_defineAttribute("transient", "true");
    popupWidget.spot_defineAttribute("resizable", "false");
    popupWidget.spot_defineAttribute("closeOnAction", "true");
    popupWidget.spot_defineAttribute("matchBackground", "true");
    popupWidget.spot_defineAttribute("okWidget", null);
    popupWidget.spot_defineAttribute("cancelWidget", null);
    popupWidget.spot_defineAttribute("contentPadding", null);
    popupWidget.spot_defineAttribute("url", null);
    popupWidget.spot_defineAttribute("scrollable", null);
    spot_addElement("popupAnimator", popupAnimator);
    popupAnimator.spot_defineAttribute("duration", null);
    popupAnimator.spot_defineAttribute("direction", null);
    popupAnimator.spot_defineAttribute("acceleration", null);
    popupAnimator.spot_defineAttribute("deceleration", null);
    popupAnimator.spot_defineAttribute("horizontal", null);
    popupAnimator.spot_defineAttribute("fadeIn", null);
    popupAnimator.spot_defineAttribute("fadeOut", null);
    popupAnimator.spot_defineAttribute("customProperties", null);
    spot_addElement("focusedAction", focusedAction);
    spot_addElement("enabledOnSelectionOnly", enabledOnSelectionOnly);
    spot_addElement("actionRepeats", actionRepeats);
    actionRepeats.spot_defineAttribute("delay", "300");
    spot_addElement("useSharedBorderForPopup", useSharedBorderForPopup);
    useSharedBorderForPopup.spot_defineAttribute("color", null);
    useSharedBorderForPopup.spot_defineAttribute("thickness", null);
    useSharedBorderForPopup.spot_defineAttribute("cornerArc", "4");
    submitValue.spot_setDefaultValue(3, "text");
    submitValue.spot_setOptional(false);
  }

  //}GENERATED_METHODS
  //GENERATED_INNER_CLASSES{

  /**
   * Class that defines the valid set of choices for
   * the <code>PushButton.actionType</code> ENUMERATED object
   */
  public static class CActionType extends SPOTEnumerated {

    /** the button action will be handled by a script */
    public final static int scripted = 0;

    /** the button will submit the enclosed form */
    public final static int submit_form = 1;

    /** the button will clear the enclosed form */
    public final static int clear_form = 2;

    /** the button will popup a menu */
    public final static int popup_menu = 3;

    /** the button will popup a widget */
    public final static int popup_widget = 4;

    /** the button will activate a link */
    public final static int link = 5;

    /** the button will toggle its set of list values eveny time it is clicked */
    public final static int list_toggle = 6;

    /**
     * Creates a new <code>CActionType</code> object
     */
    public CActionType() {
      this(null, null, null, null, true);
    }

    /**
     * Creates a new <code>CActionType</code> object
     *
     * @param val the value
     */
    public CActionType(int val) {
      super();
      _sChoices = _schoices;
      _nChoices = _nchoices;
      setValue(val);
    }

    /**
     * Creates a new <code>actionType</code> object
     * the <code>PushButton.actionType</code> ENUMERATED object
     *
     * @param ival         the integer value
     * @param sval         the string  value
     * @param idefaultval  the integer default value
     * @param sdefaultval  the string default value
     * @param optional   <code>true</code> if the node the object represents is optional
     */
    public CActionType(Integer ival, String sval, Integer idefaultval, String sdefaultval, boolean optional) {
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
      return "{" + "scripted(0), " + "submit_form(1), " + "clear_form(2), " + "popup_menu(3), " + "popup_widget(4), "
             + "link(5), " + "list_toggle(6) }";
    }

    private final static int    _nchoices[] = {
      0, 1, 2, 3, 4, 5, 6
    };
    private final static String _schoices[] = {
      "scripted", "submit_form", "clear_form", "popup_menu", "popup_widget", "link", "list_toggle"
    };
  }


  /**
   * Class that defines the valid set of choices for
   * the <code>PushButton.buttonStyle</code> ENUMERATED object
   */
  public static class CButtonStyle extends SPOTEnumerated {

    /** a standard button */
    public final static int standard = 0;

    /** a toolbar style button */
    public final static int toolbar = 1;

    /** a hyperlink button that is underlined when the mouse hovers above it */
    public final static int hyperlink = 2;

    /** a hyperlink button that is always underlined */
    public final static int hyperlink_always_underline = 4;

    /** a button that supports a two states */
    public final static int toggle = 6;

    /** a button that supports a pull-down menu */
    public final static int split_toolbar = 7;

    /** a toolbar style button that supports a two states */
    public final static int toggle_toolbar = 8;

    /** a button in the platform's default style */
    public final static int platform = 9;

    /**
     * Creates a new <code>CButtonStyle</code> object
     */
    public CButtonStyle() {
      this(null, null, null, null, true);
    }

    /**
     * Creates a new <code>CButtonStyle</code> object
     *
     * @param val the value
     */
    public CButtonStyle(int val) {
      super();
      _sChoices = _schoices;
      _nChoices = _nchoices;
      setValue(val);
    }

    /**
     * Creates a new <code>buttonStyle</code> object
     * the <code>PushButton.buttonStyle</code> ENUMERATED object
     *
     * @param ival         the integer value
     * @param sval         the string  value
     * @param idefaultval  the integer default value
     * @param sdefaultval  the string default value
     * @param optional   <code>true</code> if the node the object represents is optional
     */
    public CButtonStyle(Integer ival, String sval, Integer idefaultval, String sdefaultval, boolean optional) {
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
      return "{" + "standard(0), " + "toolbar(1), " + "hyperlink(2), " + "hyperlink_always_underline(4), "
             + "toggle(6), " + "split_toolbar(7), " + "toggle_toolbar(8), " + "platform(9) }";
    }

    private final static int    _nchoices[] = {
      0, 1, 2, 4, 6, 7, 8, 9
    };
    private final static String _schoices[] = {
      "standard", "toolbar", "hyperlink", "hyperlink_always_underline", "toggle", "split_toolbar", "toggle_toolbar",
      "platform"
    };
  }


  /**
   * Class that defines the valid set of choices for
   * the <code>PushButton.orientation</code> ENUMERATED object
   */
  public static class COrientation extends SPOTEnumerated {
    public final static int auto          = 0;
    public final static int horizontal    = 1;
    public final static int vertical_up   = 2;
    public final static int vertical_down = 3;

    /**
     * Creates a new <code>COrientation</code> object
     */
    public COrientation() {
      this(null, null, null, null, true);
    }

    /**
     * Creates a new <code>COrientation</code> object
     *
     * @param val the value
     */
    public COrientation(int val) {
      super();
      _sChoices = _schoices;
      _nChoices = _nchoices;
      setValue(val);
    }

    /**
     * Creates a new <code>orientation</code> object
     * the <code>PushButton.orientation</code> ENUMERATED object
     *
     * @param ival         the integer value
     * @param sval         the string  value
     * @param idefaultval  the integer default value
     * @param sdefaultval  the string default value
     * @param optional   <code>true</code> if the node the object represents is optional
     */
    public COrientation(Integer ival, String sval, Integer idefaultval, String sdefaultval, boolean optional) {
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
      return "{" + "auto(0), " + "horizontal(1), " + "vertical_up(2), " + "vertical_down(3) }";
    }

    private final static int    _nchoices[] = { 0, 1, 2, 3 };
    private final static String _schoices[] = { "auto", "horizontal", "vertical_up", "vertical_down" };
  }
  //}GENERATED_INNER_CLASSES
}
