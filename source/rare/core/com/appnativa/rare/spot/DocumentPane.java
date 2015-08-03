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
 * viewer that can display and edit documents
 *
 * @author Don DeCoteau
 * @version 2.0
 */
public class DocumentPane extends Viewer {
  //GENERATED_MEMBERS{
//GENERATED_COMMENT{}

  /** Behavior: whether the pane will support editing */
  public SPOTBoolean editable = new SPOTBoolean(null, false, false);
//GENERATED_COMMENT{}

  /** Behavior: whether word wrap should be enabled */
  public SPOTBoolean wordWrap = new SPOTBoolean(null, true, false);
//GENERATED_COMMENT{}

  /** Behavior: whether the speech input is supported */
  public SPOTBoolean speechInputSupported = new SPOTBoolean(null, true, false);
//GENERATED_COMMENT{}

  /** the style of viewer to create */
  public CStyle style = new CStyle(null, null, CStyle.text_editor, "text_editor", false);
//GENERATED_COMMENT{}

  /** Behavior: when a cursor should be shown */
  public CCursorShown cursorShown = new CCursorShown(null, null, CCursorShown.always_for_non_html,
                                      "always_for_non_html", false);
//GENERATED_COMMENT{}

  /** Design: the type of keyboard to use for input. spellCheck is true/false; autoCapatilize is none/words/sentences/all */
  public CKeyboardType keyboardType = new CKeyboardType(null, null, CKeyboardType.default_type, "default_type", false);
//GENERATED_COMMENT{}

  /** Design: the type of keyboard to use for input; text is custom text for the button if supported by the platform */
  public CKeyboardReturnButtonType keyboardReturnButtonType = new CKeyboardReturnButtonType(null, null,
                                                                CKeyboardReturnButtonType.default_type, "default_type",
                                                                false);
//GENERATED_COMMENT{}

  /** Behavior~~reload: information specifying if the viewer is scrollable and how the associated scroll pane should be configured */
  protected ScrollPane scrollPane = null;

  //}GENERATED_MEMBERS
  //GENERATED_METHODS{

  /**
   * Creates a new optional <code>DocumentPane</code> object.
   */
  public DocumentPane() {
    this(true);
  }

  /**
   * Creates a new <code>DocumentPane</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   */
  public DocumentPane(boolean optional) {
    super(optional, false);
    spot_setElements();
  }

  /**
   * Creates a new <code>DocumentPane</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   * @param setElements  <code>true</code> if a call to setElements should be made; <code>false</code> otherwise)
   */
  protected DocumentPane(boolean optional, boolean setElements) {
    super(optional, setElements);
  }

  /**
   * Adds elements to the object elements map
   *
   */
  protected void spot_setElements() {
    this.elementsSizeHint  += 8;
    this.attributeSizeHint += 1;
    super.spot_setElements();
    spot_defineAttribute("onChange", null);
    spot_addElement("editable", editable);
    spot_addElement("wordWrap", wordWrap);
    spot_addElement("speechInputSupported", speechInputSupported);
    spot_addElement("style", style);
    spot_addElement("cursorShown", cursorShown);
    spot_addElement("keyboardType", keyboardType);
    keyboardType.spot_defineAttribute("autoCorrect", null);
    keyboardType.spot_defineAttribute("autoComplete", null);
    keyboardType.spot_defineAttribute("autoShow", "true");
    keyboardType.spot_defineAttribute("autoCapatilize", "sentences");
    keyboardType.spot_defineAttribute("spellCheck", null);
    spot_addElement("keyboardReturnButtonType", keyboardReturnButtonType);
    keyboardReturnButtonType.spot_defineAttribute("autoEnable", "true");
    keyboardReturnButtonType.spot_defineAttribute("text", null);
    spot_addElement("scrollPane", scrollPane);
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
   * the <code>DocumentPane.style</code> ENUMERATED object
   */
  public static class CStyle extends SPOTEnumerated {
    public final static int text_editor = 0;
    public final static int html_editor = 1;

    /**
     * Creates a new <code>CStyle</code> object
     */
    public CStyle() {
      this(null, null, null, null, true);
    }

    /**
     * Creates a new <code>CStyle</code> object
     *
     * @param val the value
     */
    public CStyle(int val) {
      super();
      _sChoices = _schoices;
      _nChoices = _nchoices;
      setValue(val);
    }

    /**
     * Creates a new <code>style</code> object
     * the <code>DocumentPane.style</code> ENUMERATED object
     *
     * @param ival         the integer value
     * @param sval         the string  value
     * @param idefaultval  the integer default value
     * @param sdefaultval  the string default value
     * @param optional   <code>true</code> if the node the object represents is optional
     */
    public CStyle(Integer ival, String sval, Integer idefaultval, String sdefaultval, boolean optional) {
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
      return "{" + "text_editor(0), " + "html_editor(1) }";
    }

    private final static int    _nchoices[] = { 0, 1 };
    private final static String _schoices[] = { "text_editor", "html_editor" };
  }


  /**
   * Class that defines the valid set of choices for
   * the <code>DocumentPane.cursorShown</code> ENUMERATED object
   */
  public static class CCursorShown extends SPOTEnumerated {
    public final static int when_editable       = 0;
    public final static int always              = 1;
    public final static int always_for_non_html = 2;
    public final static int never               = 3;

    /**
     * Creates a new <code>CCursorShown</code> object
     */
    public CCursorShown() {
      this(null, null, null, null, true);
    }

    /**
     * Creates a new <code>CCursorShown</code> object
     *
     * @param val the value
     */
    public CCursorShown(int val) {
      super();
      _sChoices = _schoices;
      _nChoices = _nchoices;
      setValue(val);
    }

    /**
     * Creates a new <code>cursorShown</code> object
     * the <code>DocumentPane.cursorShown</code> ENUMERATED object
     *
     * @param ival         the integer value
     * @param sval         the string  value
     * @param idefaultval  the integer default value
     * @param sdefaultval  the string default value
     * @param optional   <code>true</code> if the node the object represents is optional
     */
    public CCursorShown(Integer ival, String sval, Integer idefaultval, String sdefaultval, boolean optional) {
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
      return "{" + "when_editable(0), " + "always(1), " + "always_for_non_html(2), " + "never(3) }";
    }

    private final static int    _nchoices[] = { 0, 1, 2, 3 };
    private final static String _schoices[] = { "when_editable", "always", "always_for_non_html", "never" };
  }


  /**
   * Class that defines the valid set of choices for
   * the <code>DocumentPane.keyboardType</code> ENUMERATED object
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
     * the <code>DocumentPane.keyboardType</code> ENUMERATED object
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
   * the <code>DocumentPane.keyboardReturnButtonType</code> ENUMERATED object
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
     * the <code>DocumentPane.keyboardReturnButtonType</code> ENUMERATED object
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
