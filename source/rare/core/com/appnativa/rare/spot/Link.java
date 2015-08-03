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
 * This class represents the configuration information of a
 * structure for holding static and dynamic links to data
 * sources.
 * <p>
 * Named data items (viewer attributes, properties, etc.) can
 * be embedded in the URL by surrounding them with curly
 * braces.
 * </p>
 * <p>
 * The value of these items will be resolved and substituted in
 * prior to submission.
 * </p>
 *
 * @author Don DeCoteau
 * @version 2.0
 */
public class Link extends SPOTSequence {
  //GENERATED_MEMBERS{
//GENERATED_COMMENT{}

  /** Design~url: the URL for the link */
  public SPOTPrintableString url = new SPOTPrintableString();
//GENERATED_COMMENT{}

  /** the target for the returned content (type can be frame, popup, dialog, popup_orphan) */
  public CTarget target = new CTarget(null, null, CTarget._new_window, "_new_window", true);
//GENERATED_COMMENT{}

  /** the name of a region that is the target of the viewer (if target is not specified) */
  public SPOTPrintableString regionName = new SPOTPrintableString(null, 0, 64, true);
//GENERATED_COMMENT{}

  /** the type of HTTP request to use to activate the link */
  public CRequestType     requestType     = new CRequestType(null, null, CRequestType.get, "get", false);
  public CRequestEncoding requestEncoding = new CRequestEncoding(null, null, CRequestEncoding.http_form, "http_form",
                                              false);
//GENERATED_COMMENT{}

  /** attributes for the link. These values will be encoded with the URL based on the request type. The default format is a set of name/value pairs separated by a semi-colon */
  public SPOTPrintableString attributes = new SPOTPrintableString();
//GENERATED_COMMENT{}

  /** HTTP header for the link. The default format is a set of name/value pairs separated by a semi-colon */
  public SPOTPrintableString headers = new SPOTPrintableString();
//GENERATED_COMMENT{}

  /** message to display on the status bar while the link is being accessed */
  public SPOTPrintableString statusMessage = new SPOTPrintableString(null, 0, 255, true);

  //}GENERATED_MEMBERS
  //GENERATED_METHODS{

  /**
   * Creates a new optional <code>Link</code> object.
   */
  public Link() {
    this(true);
  }

  /**
   * Creates a new <code>Link</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   */
  public Link(boolean optional) {
    super(optional, false);
    spot_setElements();
  }

  /**
   * Creates a new <code>Link</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   * @param setElements  <code>true</code> if a call to setElements should be made; <code>false</code> otherwise)
   */
  protected Link(boolean optional, boolean setElements) {
    super(optional, setElements);
  }

  /**
   * Adds elements to the object elements map
   *
   */
  protected void spot_setElements() {
    this.elementsSizeHint += 8;
    super.spot_setElements();
    spot_addElement("url", url);
    url.spot_defineAttribute("mimeType", null);
    url.spot_defineAttribute("inline", null);
    url.spot_defineAttribute("unescape", null);
    spot_addElement("target", target);
    target.spot_defineAttribute("windowType", "frame");
    target.spot_defineAttribute("resizable", "true");
    target.spot_defineAttribute("movable", "true");
    target.spot_defineAttribute("top", null);
    target.spot_defineAttribute("left", null);
    target.spot_defineAttribute("title", null);
    target.spot_defineAttribute("status", null);
    target.spot_defineAttribute("bgColor", null);
    target.spot_defineAttribute("icon", null);
    target.spot_defineAttribute("width", null);
    target.spot_defineAttribute("height", null);
    target.spot_defineAttribute("border", null);
    target.spot_defineAttribute("onOpened", null);
    target.spot_defineAttribute("onWillClose", null);
    target.spot_defineAttribute("onDrop", null);
    target.spot_defineAttribute("onWillExpand", null);
    target.spot_defineAttribute("onWillCollapse", null);
    target.spot_defineAttribute("onHasCollapsed", null);
    target.spot_defineAttribute("onHasExpanded", null);
    target.spot_defineAttribute("onFocus", null);
    target.spot_defineAttribute("onBlur", null);
    target.spot_defineAttribute("contentPadding", null);
    target.spot_defineAttribute("modal", null);
    target.spot_defineAttribute("timeout", null);
    spot_addElement("regionName", regionName);
    spot_addElement("requestType", requestType);
    spot_addElement("requestEncoding", requestEncoding);
    spot_addElement("attributes", attributes);
    attributes.spot_defineAttribute("mimeType", null);
    spot_addElement("headers", headers);
    spot_addElement("statusMessage", statusMessage);
  }

  //}GENERATED_METHODS
  //GENERATED_INNER_CLASSES{

  /**
   * Class that defines the valid set of choices for
   * the <code>Link.target</code> ENUMERATED object
   */
  public static class CTarget extends SPOTEnumerated {

    /** link viewer replaces the current view */
    public final static int _self = 0;

    /** link viewer is opened in a new window */
    public final static int _new_window = 1;

    /** link viewer is opened in a new modeless window */
    public final static int _new_popup = 2;

    /** link viewer replaces the application workspace */
    public final static int _workspace = 3;

    /** link viewer replaces the parent's content */
    public final static int _parent = 4;

    /** link viewer replaces the toolbar's content */
    public final static int _toolbar = 5;

    /** link viewer replaces the menu bar's content */
    public final static int _menubar = 6;

    /** link viewer is opened in a new window (html compatability) */
    public final static int _blank = 7;

    /** no data is expected */
    public final static int _null = 255;

    /**
     * Creates a new <code>CTarget</code> object
     */
    public CTarget() {
      this(null, null, null, null, true);
    }

    /**
     * Creates a new <code>CTarget</code> object
     *
     * @param val the value
     */
    public CTarget(int val) {
      super();
      _sChoices = _schoices;
      _nChoices = _nchoices;
      spot_setAttributes();
      setValue(val);
    }

    /**
     * Creates a new <code>target</code> object
     * the <code>Link.target</code> ENUMERATED object
     *
     * @param ival         the integer value
     * @param sval         the string  value
     * @param idefaultval  the integer default value
     * @param sdefaultval  the string default value
     * @param optional   <code>true</code> if the node the object represents is optional
     */
    public CTarget(Integer ival, String sval, Integer idefaultval, String sdefaultval, boolean optional) {
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
      return "{" + "_self(0), " + "_new_window(1), " + "_new_popup(2), " + "_workspace(3), " + "_parent(4), "
             + "_toolbar(5), " + "_menubar(6), " + "_blank(7), " + "_null(255) }";
    }

    private void spot_setAttributes() {
      this.attributeSizeHint += 24;
      spot_defineAttribute("windowType", "frame");
      spot_defineAttribute("resizable", "true");
      spot_defineAttribute("movable", "true");
      spot_defineAttribute("top", null);
      spot_defineAttribute("left", null);
      spot_defineAttribute("title", null);
      spot_defineAttribute("status", null);
      spot_defineAttribute("bgColor", null);
      spot_defineAttribute("icon", null);
      spot_defineAttribute("width", null);
      spot_defineAttribute("height", null);
      spot_defineAttribute("border", null);
      spot_defineAttribute("onOpened", null);
      spot_defineAttribute("onWillClose", null);
      spot_defineAttribute("onDrop", null);
      spot_defineAttribute("onWillExpand", null);
      spot_defineAttribute("onWillCollapse", null);
      spot_defineAttribute("onHasCollapsed", null);
      spot_defineAttribute("onHasExpanded", null);
      spot_defineAttribute("onFocus", null);
      spot_defineAttribute("onBlur", null);
      spot_defineAttribute("contentPadding", null);
      spot_defineAttribute("modal", null);
      spot_defineAttribute("timeout", null);
    }

    private final static int    _nchoices[] = {
      0, 1, 2, 3, 4, 5, 6, 7, 255
    };
    private final static String _schoices[] = {
      "_self", "_new_window", "_new_popup", "_workspace", "_parent", "_toolbar", "_menubar", "_blank", "_null"
    };
  }


  /**
   * Class that defines the valid set of choices for
   * the <code>Link.requestType</code> ENUMERATED object
   */
  public static class CRequestType extends SPOTEnumerated {

    /** create */
    public final static int post = 0;

    /** read */
    public final static int get = 1;

    /** update */
    public final static int put = 2;

    /** delete */
    public final static int delete = 3;

    /**
     * Creates a new <code>CRequestType</code> object
     */
    public CRequestType() {
      this(null, null, null, null, true);
    }

    /**
     * Creates a new <code>CRequestType</code> object
     *
     * @param val the value
     */
    public CRequestType(int val) {
      super();
      _sChoices = _schoices;
      _nChoices = _nchoices;
      setValue(val);
    }

    /**
     * Creates a new <code>requestType</code> object
     * the <code>Link.requestType</code> ENUMERATED object
     *
     * @param ival         the integer value
     * @param sval         the string  value
     * @param idefaultval  the integer default value
     * @param sdefaultval  the string default value
     * @param optional   <code>true</code> if the node the object represents is optional
     */
    public CRequestType(Integer ival, String sval, Integer idefaultval, String sdefaultval, boolean optional) {
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
      return "{" + "post(0), " + "get(1), " + "put(2), " + "delete(3) }";
    }

    private final static int    _nchoices[] = { 0, 1, 2, 3 };
    private final static String _schoices[] = { "post", "get", "put", "delete" };
  }


  /**
   * Class that defines the valid set of choices for
   * the <code>Link.requestEncoding</code> ENUMERATED object
   */
  public static class CRequestEncoding extends SPOTEnumerated {
    public final static int http_form = 0;
    public final static int json      = 1;

    /**
     * Creates a new <code>CRequestEncoding</code> object
     */
    public CRequestEncoding() {
      this(null, null, null, null, true);
    }

    /**
     * Creates a new <code>CRequestEncoding</code> object
     *
     * @param val the value
     */
    public CRequestEncoding(int val) {
      super();
      _sChoices = _schoices;
      _nChoices = _nchoices;
      setValue(val);
    }

    /**
     * Creates a new <code>requestEncoding</code> object
     * the <code>Link.requestEncoding</code> ENUMERATED object
     *
     * @param ival         the integer value
     * @param sval         the string  value
     * @param idefaultval  the integer default value
     * @param sdefaultval  the string default value
     * @param optional   <code>true</code> if the node the object represents is optional
     */
    public CRequestEncoding(Integer ival, String sval, Integer idefaultval, String sdefaultval, boolean optional) {
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
      return "{" + "http_form(0), " + "json(1) }";
    }

    private final static int    _nchoices[] = { 0, 1 };
    private final static String _schoices[] = { "http_form", "json" };
  }
  //}GENERATED_INNER_CLASSES
}
