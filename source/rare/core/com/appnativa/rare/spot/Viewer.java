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
 * The class provides the base configuration for viewer A
 * viewer is a high level widget that can occupy screen regions
 * <p>
 * and can serves as a container for widgets or other viewers.
 * Viewers also support more complex functionality such as
 * printing and saving.
 * </p>
 *
 * @author Don DeCoteau
 * @version 2.0
 */
public class Viewer extends Widget {
  //GENERATED_MEMBERS{
//GENERATED_COMMENT{}

  /** the URL to use when resolving relative URL paths */
  public SPOTPrintableString contextURL = new SPOTPrintableString(null, 0, 255, true);
//GENERATED_COMMENT{}

  /** Design~url~reload: url for a object template */
  public SPOTPrintableString templateURL = new SPOTPrintableString();
//GENERATED_COMMENT{}

  /** Appearance: title to use if the viewer container is collapsed */
  public SPOTPrintableString collapsedTitle = new SPOTPrintableString(null, 0, 255, true);
//GENERATED_COMMENT{}

  /** Appearance~icon: url to use to retrieve an icon for the viewer */
  public SPOTPrintableString icon = new SPOTPrintableString(null, 0, 255, true);
//GENERATED_COMMENT{}

  /** Behavior: whether this viewer can only be embed as the main component in a new window */
  public SPOTBoolean windowOnly = new SPOTBoolean(null, false, false);
//GENERATED_COMMENT{}

  /** Design~url: the URL for a script for the viewer. Only loads an runs a non-inline script once based on the script's URL */
  public SPOTPrintableString scriptURL = new SPOTPrintableString();
//GENERATED_COMMENT{}

  /** Behavior: Whether the viewer is local or global, local viewers are not registered (cannot be accessed by name outside of thier container). */
  public SPOTBoolean local = new SPOTBoolean();
//GENERATED_COMMENT{}

  /** Behavior: the behavior for a disabled viewer */
  public CDisableBehavior disableBehavior = new CDisableBehavior(null, null, CDisableBehavior.disable_widgets,
                                              "disable_widgets", false);
  //}GENERATED_MEMBERS

  /** linked data for the configuration; will be passed to the widget when it is created */
  public Object linkedData;

  //GENERATED_METHODS{

  /**
   * Creates a new optional <code>Viewer</code> object.
   */
  public Viewer() {
    this(true);
  }

  /**
   * Creates a new <code>Viewer</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   */
  public Viewer(boolean optional) {
    super(optional, false);
    spot_setElements();
  }

  /**
   * Creates a new <code>Viewer</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   * @param setElements  <code>true</code> if a call to setElements should be made; <code>false</code> otherwise)
   */
  protected Viewer(boolean optional, boolean setElements) {
    super(optional, setElements);
  }

  /**
   * Adds elements to the object elements map
   *
   */
  protected void spot_setElements() {
    this.elementsSizeHint  += 8;
    this.attributeSizeHint += 6;
    super.spot_setElements();
    spot_defineAttribute("onLoad", null);
    spot_defineAttribute("onUnload", null);
    spot_defineAttribute("onDispose", null);
    spot_defineAttribute("viewerClass", null);
    spot_defineAttribute("language", null);
    spot_defineAttribute("lockable", null);
    spot_addElement("contextURL", contextURL);
    spot_addElement("templateURL", templateURL);
    templateURL.spot_defineAttribute("inline", null);
    templateURL.spot_defineAttribute("cache", null);
    spot_addElement("collapsedTitle", collapsedTitle);
    spot_addElement("icon", icon);
    icon.spot_defineAttribute("alt", null);
    icon.spot_defineAttribute("slice", null);
    icon.spot_defineAttribute("size", null);
    icon.spot_defineAttribute("scaling", null);
    icon.spot_defineAttribute("density", null);
    spot_addElement("windowOnly", windowOnly);
    spot_addElement("scriptURL", scriptURL);
    scriptURL.spot_defineAttribute("mimeType", null);
    scriptURL.spot_defineAttribute("inline", null);
    scriptURL.spot_defineAttribute("runOnce", "true");
    scriptURL.spot_defineAttribute("shared", "true");
    spot_addElement("local", local);
    spot_addElement("disableBehavior", disableBehavior);
    disableBehavior.spot_defineAttribute("disableOverlayColor", null);
  }

  //}GENERATED_METHODS
  public MenuItem addPopupMenu(MenuItem item) {
    getPopupMenuReference();
    popupMenu.add(item);

    return item;
  }

  public MenuItem addPopupMenu(String value, String code) {
    return addPopupMenu(new MenuItem(value, code));
  }

  public MenuItem addPopupMenu(String value, String url, int target) {
    return addPopupMenu(new MenuItem(value, url, target));
  }

  public MenuItem addPopupMenu(String value, String url, String target) {
    return addPopupMenu(new MenuItem(value, url, target));
  }

  public void setScriptURL(String url) {
    scriptURL.setValue(url);
  }

  public void setContextURL(String url) {
    contextURL.setValue(url);
  }

  //GENERATED_INNER_CLASSES{

  /**
   * Class that defines the valid set of choices for
   * the <code>Viewer.disableBehavior</code> ENUMERATED object
   */
  public static class CDisableBehavior extends SPOTEnumerated {
    public final static int disable_container = 0;
    public final static int disable_widgets   = 1;
    public final static int disable_both      = 2;

    /**
     * Creates a new <code>CDisableBehavior</code> object
     */
    public CDisableBehavior() {
      this(null, null, null, null, true);
    }

    /**
     * Creates a new <code>CDisableBehavior</code> object
     *
     * @param val the value
     */
    public CDisableBehavior(int val) {
      super();
      _sChoices = _schoices;
      _nChoices = _nchoices;
      spot_setAttributes();
      setValue(val);
    }

    /**
     * Creates a new <code>disableBehavior</code> object
     * the <code>Viewer.disableBehavior</code> ENUMERATED object
     *
     * @param ival         the integer value
     * @param sval         the string  value
     * @param idefaultval  the integer default value
     * @param sdefaultval  the string default value
     * @param optional   <code>true</code> if the node the object represents is optional
     */
    public CDisableBehavior(Integer ival, String sval, Integer idefaultval, String sdefaultval, boolean optional) {
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
      return "{" + "disable_container(0), " + "disable_widgets(1), " + "disable_both(2) }";
    }

    private void spot_setAttributes() {
      this.attributeSizeHint += 1;
      spot_defineAttribute("disableOverlayColor", null);
    }

    private final static int    _nchoices[] = { 0, 1, 2 };
    private final static String _schoices[] = { "disable_container", "disable_widgets", "disable_both" };
  }
  //}GENERATED_INNER_CLASSES
}
