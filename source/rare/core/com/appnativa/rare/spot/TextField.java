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
 * This class represents the configuration for a widget that
 * allows a single line of text to be entered and/or edited
 *
 * @author Don DeCoteau
 * @version 2.0
 */
public class TextField extends Widget {
  //GENERATED_MEMBERS{
//GENERATED_COMMENT{}

  /** Appearance: number of characters columns to size the field for (use zero to let the widget decide) */
  public SPOTInteger visibleCharacters = new SPOTInteger(null, 0, null, true);
//GENERATED_COMMENT{}

  /** Appearance: the value for the field */
  public SPOTPrintableString value = new SPOTPrintableString();
//GENERATED_COMMENT{}

  /** Appearance~~reload: text to show when the widget is empty */
  protected EmptyText emptyText = null;
//GENERATED_COMMENT{}

  /** Behavior: error message to display for invalid input */
  public SPOTPrintableString errorMessage = new SPOTPrintableString(null, 0, 80, true);
//GENERATED_COMMENT{}

  /** Behavior: format specifier for controlling the format of text input */
  public SPOTPrintableString inputMask = new SPOTPrintableString(null, 0, 64, true);
//GENERATED_COMMENT{}

  /** Behavior: format specifier for validating input text input valueType can be string date or number */
  public SPOTPrintableString inputValidator = new SPOTPrintableString(null, 0, 64, true);
//GENERATED_COMMENT{}

  /** Behavior: a list of valid characters */
  public SPOTPrintableString validCharacters = new SPOTPrintableString();
//GENERATED_COMMENT{}

  /** Behavior: whether the field is editable */
  public SPOTBoolean editable = new SPOTBoolean(null, true, false);
//GENERATED_COMMENT{}

  /** Behavior: the minimum characters to allow (use zero for no minimum) */
  public SPOTInteger minCharacters = new SPOTInteger(null, 0, null, true);
//GENERATED_COMMENT{}

  /** Behavior: the maximum characters to allow (use zero for no maximum) */
  public SPOTInteger maxCharacters = new SPOTInteger(null, 0, null, true);
//GENERATED_COMMENT{}

  /** Behavior: the maximum number of undos to support */
  public SPOTInteger undoLimit = new SPOTInteger(null, -1, null, 100, false);
//GENERATED_COMMENT{}

  /** Behavior: whether the speech input is supported */
  public SPOTBoolean speechInputSupported = new SPOTBoolean(null, true, false);
//GENERATED_COMMENT{}

  /** Design: the type of keyboard to use for input. spellCheck is true/false; autoCapatilize is none/words/sentences/all */
  public CKeyboardType keyboardType = new CKeyboardType(null, null, CKeyboardType.default_type, "default_type", false);
//GENERATED_COMMENT{}

  /** Design: the type of keyboard to use for input; text is custom text for the button if supported by the platform */
  public CKeyboardReturnButtonType keyboardReturnButtonType = new CKeyboardReturnButtonType(null, null,
                                                                CKeyboardReturnButtonType.default_type, "default_type",
                                                                false);

  //}GENERATED_MEMBERS
  //GENERATED_METHODS{

  /**
   * Creates a new optional <code>TextField</code> object.
   */
  public TextField() {
    this(true);
  }

  /**
   * Creates a new <code>TextField</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   */
  public TextField(boolean optional) {
    super(optional, false);
    spot_setElements();
  }

  /**
   * Creates a new <code>TextField</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   * @param setElements  <code>true</code> if a call to setElements should be made; <code>false</code> otherwise)
   */
  protected TextField(boolean optional, boolean setElements) {
    super(optional, setElements);
  }

  /**
   * Adds elements to the object elements map
   *
   */
  protected void spot_setElements() {
    this.elementsSizeHint  += 14;
    this.attributeSizeHint += 2;
    super.spot_setElements();
    spot_defineAttribute("onAction", null);
    spot_defineAttribute("onChange", null);
    spot_addElement("visibleCharacters", visibleCharacters);
    spot_addElement("value", value);
    spot_addElement("emptyText", emptyText);
    spot_addElement("errorMessage", errorMessage);
    errorMessage.spot_defineAttribute("displayWidget", null);
    spot_addElement("inputMask", inputMask);
    inputMask.spot_defineAttribute("placeHolder", null);
    inputMask.spot_defineAttribute("preserveLiteralCharacters", "false");
    spot_addElement("inputValidator", inputValidator);
    inputValidator.spot_defineAttribute("valueType", "number");
    inputValidator.spot_defineAttribute("reformat", null);
    inputValidator.spot_defineAttribute("validateOnLostFocus", "true");
    inputValidator.spot_defineAttribute("submitRawValue", null);
    inputValidator.spot_defineAttribute("maximum", null);
    inputValidator.spot_defineAttribute("minimum", null);
    spot_addElement("validCharacters", validCharacters);
    spot_addElement("editable", editable);
    spot_addElement("minCharacters", minCharacters);
    spot_addElement("maxCharacters", maxCharacters);
    spot_addElement("undoLimit", undoLimit);
    spot_addElement("speechInputSupported", speechInputSupported);
    spot_addElement("keyboardType", keyboardType);
    keyboardType.spot_defineAttribute("autoCorrect", null);
    keyboardType.spot_defineAttribute("autoComplete", null);
    keyboardType.spot_defineAttribute("autoShow", "true");
    keyboardType.spot_defineAttribute("autoCapatilize", "sentences");
    keyboardType.spot_defineAttribute("spellCheck", null);
    spot_addElement("keyboardReturnButtonType", keyboardReturnButtonType);
    keyboardReturnButtonType.spot_defineAttribute("autoEnable", "true");
    keyboardReturnButtonType.spot_defineAttribute("text", null);
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

  //}GENERATED_METHODS
  //GENERATED_INNER_CLASSES{

  /**
   * Class that defines the valid set of choices for
   * the <code>TextField.keyboardType</code> ENUMERATED object
   */
  public static class CKeyboardType extends SPOTEnumerated {
    public final static int default_type             = 0;
    public final static int text_type                = 1;
    public final static int number_type              = 2;
    public final static int number_punctuation_type  = 3;
    public final static int decimal_type             = 4;
    public final static int decimal_punctuation_type = 5;
    public final static int phone_number_type        = 6;
    public final static int name_phone_number_type   = 7;
    public final static int email_address_type       = 8;
    public final static int url_type                 = 9;

    /**
     * Creates a new <code>CKeyboardType</code> object
     */
    public CKeyboardType() {
      this(null, null, null, null, true);
    }

    /**
     * Creates a new <code>CKeyboardType</code> object
     *
     * @param val the value
     */
    public CKeyboardType(int val) {
      super();
      _sChoices = _schoices;
      _nChoices = _nchoices;
      spot_setAttributes();
      setValue(val);
    }

    /**
     * Creates a new <code>keyboardType</code> object
     * the <code>TextField.keyboardType</code> ENUMERATED object
     *
     * @param ival         the integer value
     * @param sval         the string  value
     * @param idefaultval  the integer default value
     * @param sdefaultval  the string default value
     * @param optional   <code>true</code> if the node the object represents is optional
     */
    public CKeyboardType(Integer ival, String sval, Integer idefaultval, String sdefaultval, boolean optional) {
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
      return "{" + "default_type(0), " + "text_type(1), " + "number_type(2), " + "number_punctuation_type(3), "
             + "decimal_type(4), " + "decimal_punctuation_type(5), " + "phone_number_type(6), "
             + "name_phone_number_type(7), " + "email_address_type(8), " + "url_type(9) }";
    }

    private void spot_setAttributes() {
      this.attributeSizeHint += 5;
      spot_defineAttribute("autoCorrect", null);
      spot_defineAttribute("autoComplete", null);
      spot_defineAttribute("autoShow", "true");
      spot_defineAttribute("autoCapatilize", "sentences");
      spot_defineAttribute("spellCheck", null);
    }

    private final static int    _nchoices[] = {
      0, 1, 2, 3, 4, 5, 6, 7, 8, 9
    };
    private final static String _schoices[] = {
      "default_type", "text_type", "number_type", "number_punctuation_type", "decimal_type", "decimal_punctuation_type",
      "phone_number_type", "name_phone_number_type", "email_address_type", "url_type"
    };
  }


  /**
   * Class that defines the valid set of choices for
   * the <code>TextField.keyboardReturnButtonType</code> ENUMERATED object
   */
  public static class CKeyboardReturnButtonType extends SPOTEnumerated {
    public final static int default_type = 0;
    public final static int go_type      = 1;
    public final static int join_type    = 2;
    public final static int next_type    = 3;
    public final static int search_type  = 4;
    public final static int send_type    = 5;
    public final static int none_type    = 6;
    public final static int done_type    = 7;

    /**
     * Creates a new <code>CKeyboardReturnButtonType</code> object
     */
    public CKeyboardReturnButtonType() {
      this(null, null, null, null, true);
    }

    /**
     * Creates a new <code>CKeyboardReturnButtonType</code> object
     *
     * @param val the value
     */
    public CKeyboardReturnButtonType(int val) {
      super();
      _sChoices = _schoices;
      _nChoices = _nchoices;
      spot_setAttributes();
      setValue(val);
    }

    /**
     * Creates a new <code>keyboardReturnButtonType</code> object
     * the <code>TextField.keyboardReturnButtonType</code> ENUMERATED object
     *
     * @param ival         the integer value
     * @param sval         the string  value
     * @param idefaultval  the integer default value
     * @param sdefaultval  the string default value
     * @param optional   <code>true</code> if the node the object represents is optional
     */
    public CKeyboardReturnButtonType(Integer ival, String sval, Integer idefaultval, String sdefaultval,
                                     boolean optional) {
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
      return "{" + "default_type(0), " + "go_type(1), " + "join_type(2), " + "next_type(3), " + "search_type(4), "
             + "send_type(5), " + "none_type(6), " + "done_type(7) }";
    }

    private void spot_setAttributes() {
      this.attributeSizeHint += 2;
      spot_defineAttribute("autoEnable", "true");
      spot_defineAttribute("text", null);
    }

    private final static int    _nchoices[] = {
      0, 1, 2, 3, 4, 5, 6, 7
    };
    private final static String _schoices[] = {
      "default_type", "go_type", "join_type", "next_type", "search_type", "send_type", "none_type", "done_type"
    };
  }
  //}GENERATED_INNER_CLASSES
}
