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
 * This class represents the configuration for an application
 * instance.
 *
 * @author Don DeCoteau
 * @version 2.0
 */
public class Application extends SPOTSequence {
  //GENERATED_MEMBERS{
//GENERATED_COMMENT{}

  /** Design: the name of the application */
  public SPOTPrintableString name = new SPOTPrintableString(null, 0, 32, true);
//GENERATED_COMMENT{}

  /** Design: the URL to use when resolving relative link paths; if redirect is true then this app object is ignored and the contextURl is used to retrieve a new app object (this can be done only once) */
  public SPOTPrintableString contextURL = new SPOTPrintableString(null, 0, 255, true);
//GENERATED_COMMENT{}

  /** Design~~: A string to use to prefix URLs that start with a forward slash */
  public SPOTPrintableString applicationRoot = new SPOTPrintableString(null, 0, 255, true);
//GENERATED_COMMENT{}

  /** Design~url: url for a properties file formatted list of UI properties these properties will override any system properties with the same name */
  public SPOTPrintableString lookAndFeelPropertiesURL = new SPOTPrintableString();
//GENERATED_COMMENT{}

  /** Design~url: url for a java properties file formatted list of strings these strings will override any predefined resources string with the same name */
  public SPOTPrintableString resourceStringsURL = new SPOTPrintableString();
//GENERATED_COMMENT{}

  /** Design~url: url for a java properties file formatted list of icons */
  public SPOTPrintableString resourceIconsURL = new SPOTPrintableString();
//GENERATED_COMMENT{}

  /** Design~url: url for a set of named set of application-wide ActionItems that can be later referenced throughout the application */
  public SPOTPrintableString actionItemsURL = new SPOTPrintableString();
//GENERATED_COMMENT{}

  /** Design~url: url for a set of application-specific name/value pairs. The default format is a set of name/value pairs separated by a semi-colon */
  public SPOTPrintableString attributesURL = new SPOTPrintableString();
//GENERATED_COMMENT{}

  /** Design~url: url for a set DataCollection objects */
  public SPOTPrintableString dataCollectionsURL = new SPOTPrintableString();
//GENERATED_COMMENT{}

  /** a set language class name/mime-types pairs for widgets/viewers to add to/override in the application's environment */
  protected SPOTSet widgetHandlers = null;
//GENERATED_COMMENT{}

  /** Design: a set of name/language class name pairs for data collection handlers */
  protected SPOTSet collectionHandlers = null;
//GENERATED_COMMENT{}

  /** Design: a set of name/url pairs defining custom fonts */
  protected SPOTSet supplementalFonts = null;
//GENERATED_COMMENT{}

  /** Design~urlset: supplemental Jar file that can be used to resolve referenced classes */
  protected SPOTSet supplementalJars = null;
//GENERATED_COMMENT{}

  /** Design: whether popup URL's should be automatically managed */
  public SPOTBoolean managePopupURLs = new SPOTBoolean(null, true, false);
//GENERATED_COMMENT{}

  /** Design: the MIME type for default scripting language for the application */
  public SPOTPrintableString defaultScriptingLanguage = new SPOTPrintableString(null, "text/javascript", false);
//GENERATED_COMMENT{}

  /** Design: the default date/time format for date/time items */
  public SPOTPrintableString defaultItemDateTimeFormat = new SPOTPrintableString(null, "yyyy-MM-dd HH:mm", false);
//GENERATED_COMMENT{}

  /** Design: the default date format for date items */
  public SPOTPrintableString defaultItemDateFormat = new SPOTPrintableString(null, "yyyy-MM-dd", false);
//GENERATED_COMMENT{}

  /** Design: the default time format for time items */
  public SPOTPrintableString defaultItemTimeFormat = new SPOTPrintableString(null, "HH:mm", false);
//GENERATED_COMMENT{}

  /** Design: the default display date/time format (this format will be localized if necessary) */
  public SPOTPrintableString defaultDisplayDateTimeFormat = new SPOTPrintableString(null, "MM/dd/yyyy hh:mm a", false);
//GENERATED_COMMENT{}

  /** Design: the default display date format (this format will be localized if necessary) */
  public SPOTPrintableString defaultDisplayDateFormat = new SPOTPrintableString(null, "MM/dd/yyyy", false);
//GENERATED_COMMENT{}

  /** Design: the default display time format */
  public SPOTPrintableString defaultDisplayTimeFormat = new SPOTPrintableString(null, "hh:mm a", false);
//GENERATED_COMMENT{}

  /** Design: whether display date/time formats should be automatically localized */
  public SPOTBoolean autoLocalizeDateFormats = new SPOTBoolean(null, true, false);
//GENERATED_COMMENT{}

  /** Design: whether number formats should be automatically localized */
  public SPOTBoolean autoLocalizeNumberFormats = new SPOTBoolean(null, true, false);
//GENERATED_COMMENT{}

  /** Design: whether to automatically adjust the default platform size when necessary */
  public SPOTBoolean autoAdjustFontSize = new SPOTBoolean(null, false, false);
//GENERATED_COMMENT{}

  /** Design: whether auto-generated tooltips overlap existing text */
  public SPOTBoolean overlapAutoToolTips = new SPOTBoolean(null, false, false);
//GENERATED_COMMENT{}

  /** Design: whether focused actions should be automatically managed */
  public SPOTBoolean manageFocusedActions = new SPOTBoolean(null, true, false);
//GENERATED_COMMENT{}

  /** Design: whether unresolved script variable names will be automatically matched to widget names */
  public SPOTBoolean dynamicNameLookup = new SPOTBoolean(null, true, false);
//GENERATED_COMMENT{}

  /** Design: whether to populate the environment with useful global constants */
  public SPOTBoolean populateGlobalConstants = new SPOTBoolean(null, true, false);
//GENERATED_COMMENT{}

  /** Design: whether viewers should be made local by default */
  public SPOTBoolean viewersLocalByDefault = new SPOTBoolean(null, false, false);
//GENERATED_COMMENT{}

  /** whether external component URLs should be redirected */
  public CComURLRedirection comURLRedirection = new CComURLRedirection(null, null, CComURLRedirection.none, "none",
                                                  false);
//GENERATED_COMMENT{}

  /** whether the redirect proxy server should be started on demand (or at startup) */
  public SPOTBoolean startRedirectorOnDemand = new SPOTBoolean(null, true, false);
//GENERATED_COMMENT{}

  /** Design: whether number and date format exceptions should be ignored */
  public SPOTBoolean ignoreFormatExceptions = new SPOTBoolean(null, false, false);
//GENERATED_COMMENT{}

  /** Design: whether all widgets should be draggable by default */
  public SPOTBoolean allWidgetsDraggable = new SPOTBoolean(null, false, false);
//GENERATED_COMMENT{}

  /** Design: whether all label widgets should be draggable by default */
  public SPOTBoolean allLabelsDraggable = new SPOTBoolean(null, false, false);
//GENERATED_COMMENT{}

  /** Design: whether all textfield widgets should be draggable by default */
  public SPOTBoolean allTextFieldsDraggable = new SPOTBoolean(null, false, false);
//GENERATED_COMMENT{}

  /** Design: whether all textfield widgets should be able to be dropped onto by default */
  public SPOTBoolean allTextFieldsDroppable = new SPOTBoolean(null, true, false);
//GENERATED_COMMENT{}

  /** Design: the monospaced font to use as the default */
  protected Font defaultMonospaceFont = null;
//GENERATED_COMMENT{}

  /** Design: whether the selection color on list type items should change when the widget looses focus */
  public SPOTBoolean changeSelColorOnLostFocus = new SPOTBoolean(null, true, false);
//GENERATED_COMMENT{}

  /** Design:a cell definition to use to paint selections in list */
  protected GridCell selectionPainter = null;
//GENERATED_COMMENT{}

  /** a cell definition to use to paint selections in lists */
  protected GridCell lostFocusSelectionPainter = null;
//GENERATED_COMMENT{}

  /** a cell definition to use to paint auto hilighted lists */
  protected GridCell autoHilightPainter = null;
//GENERATED_COMMENT{}

  /** Design:a cell definition to use to paint pressed items in a lists */
  protected GridCell pressedPainter = null;
//GENERATED_COMMENT{}

  /** Design:a cell definition to use to paint tooltips */
  protected GridCell tooltipPainter = null;
//GENERATED_COMMENT{}

  /** Design:a cell definition to use to paint focused widgets */
  protected GridCell widgetFocusPainter = null;
//GENERATED_COMMENT{}

  /** Design:a cell definition to use to paint list type items */
  protected GridCell      listItemFocusPainter = null;
  public CHttpAuthHandler httpAuthHandler      = new CHttpAuthHandler(null, null, CHttpAuthHandler.standard,
                                                   "standard", false);
//GENERATED_COMMENT{}

  /** Design:whether icons should be scaled when the font is scaled */
  public SPOTBoolean scaleIconsWithFont = new SPOTBoolean(null, false, false);
//GENERATED_COMMENT{}

  /** Design: the size to print at relative to the screen font size (0 - prints at the normal size, 1 - prints at the screen font size) */
  public SPOTReal    relativePrintSize          = new SPOTReal(null, "0", "4", "0", false);
  public SPOTBoolean enableHTTPResponseCacheing = new SPOTBoolean(null, false, false);
//GENERATED_COMMENT{}

  /** Design: whether to use the custom file preferences factory for preferences instead of the system default */
  public SPOTBoolean useCustomFilePreferencesFactory = new SPOTBoolean(null, false, false);
//GENERATED_COMMENT{}

  /** Design: whether to have sage automatically manage scroll wheel behavior */
  public SPOTBoolean manageScrollWheel = new SPOTBoolean(null, true, false);
//GENERATED_COMMENT{}

  /** whether to automatically provide support for multiple screen sizes */
  public CManagedScreenSizes managedScreenSizes = new CManagedScreenSizes(null, null, CManagedScreenSizes.none, "none",
                                                    false);
//GENERATED_COMMENT{}

  /** the density use for managed icons when running on desktops/non-touchable devices */
  public SPOTPrintableString desktopIconDensity = new SPOTPrintableString(null, "ldpi", false);
//GENERATED_COMMENT{}

  /** Hidden: the configuration for the application's main window */
  protected MainWindow mainWindow = null;
//GENERATED_COMMENT{}

  /** Design~url: the default icon to use when image loading is deferred */
  public SPOTPrintableString deferredImageIcon = new SPOTPrintableString(null, 0, 255, true);
//GENERATED_COMMENT{}

  /** Design: prefix to use to restrict custom properties on Widgets and RenderableDataItems */
  public SPOTPrintableString customPropertyPrefix = new SPOTPrintableString(null, 0, 16, "my_", false);
//GENERATED_COMMENT{}

  /** Design: the name of a class to instantiate and add as an application listener */
  public SPOTPrintableString applicationListenerClass = new SPOTPrintableString(null, 0, 255, true);

  //}GENERATED_MEMBERS
  //GENERATED_METHODS{

  /**
   * Creates a new optional <code>Application</code> object.
   */
  public Application() {
    this(true);
  }

  /**
   * Creates a new <code>Application</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   */
  public Application(boolean optional) {
    super(optional, false);
    spot_setElements();
  }

  /**
   * Creates a new <code>Application</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   * @param setElements  <code>true</code> if a call to setElements should be made; <code>false</code> otherwise)
   */
  protected Application(boolean optional, boolean setElements) {
    super(optional, setElements);
  }

  /**
   * Adds elements to the object elements map
   *
   */
  protected void spot_setElements() {
    this.elementsSizeHint  += 57;
    this.attributeSizeHint += 6;
    super.spot_setElements();
    spot_defineAttribute("onAuthFailure", null);
    spot_defineAttribute("onFocusChange", null);
    spot_defineAttribute("onPermanentFocusChange", null);
    spot_defineAttribute("onChange", null);
    spot_defineAttribute("onPreExecute", null);
    spot_defineAttribute("onPostExecute", null);
    spot_addElement("name", name);
    spot_addElement("contextURL", contextURL);
    contextURL.spot_defineAttribute("redirect", null);
    spot_addElement("applicationRoot", applicationRoot);
    spot_addElement("lookAndFeelPropertiesURL", lookAndFeelPropertiesURL);
    lookAndFeelPropertiesURL.spot_defineAttribute("inline", null);
    lookAndFeelPropertiesURL.spot_defineAttribute("keep_color_keys", null);
    spot_addElement("resourceStringsURL", resourceStringsURL);
    resourceStringsURL.spot_defineAttribute("inline", null);
    resourceStringsURL.spot_defineAttribute("locale", "en_US");
    spot_addElement("resourceIconsURL", resourceIconsURL);
    resourceIconsURL.spot_defineAttribute("inline", null);
    resourceIconsURL.spot_defineAttribute("locale", "en_US");
    resourceIconsURL.spot_defineAttribute("localeSensitive", "false");
    spot_addElement("actionItemsURL", actionItemsURL);
    actionItemsURL.spot_defineAttribute("inline", null);
    actionItemsURL.spot_defineAttribute("locale", "en_US");
    actionItemsURL.spot_defineAttribute("localeSensitive", "false");
    spot_addElement("attributesURL", attributesURL);
    attributesURL.spot_defineAttribute("inline", null);
    attributesURL.spot_defineAttribute("locale", "en_US");
    attributesURL.spot_defineAttribute("localeSensitive", "false");
    spot_addElement("dataCollectionsURL", dataCollectionsURL);
    dataCollectionsURL.spot_defineAttribute("inline", null);
    dataCollectionsURL.spot_defineAttribute("locale", "en_US");
    dataCollectionsURL.spot_defineAttribute("localeSensitive", "false");
    spot_addElement("widgetHandlers", widgetHandlers);
    spot_addElement("collectionHandlers", collectionHandlers);
    spot_addElement("supplementalFonts", supplementalFonts);
    spot_addElement("supplementalJars", supplementalJars);
    spot_addElement("managePopupURLs", managePopupURLs);
    spot_addElement("defaultScriptingLanguage", defaultScriptingLanguage);
    spot_addElement("defaultItemDateTimeFormat", defaultItemDateTimeFormat);
    spot_addElement("defaultItemDateFormat", defaultItemDateFormat);
    spot_addElement("defaultItemTimeFormat", defaultItemTimeFormat);
    spot_addElement("defaultDisplayDateTimeFormat", defaultDisplayDateTimeFormat);
    spot_addElement("defaultDisplayDateFormat", defaultDisplayDateFormat);
    spot_addElement("defaultDisplayTimeFormat", defaultDisplayTimeFormat);
    spot_addElement("autoLocalizeDateFormats", autoLocalizeDateFormats);
    spot_addElement("autoLocalizeNumberFormats", autoLocalizeNumberFormats);
    spot_addElement("autoAdjustFontSize", autoAdjustFontSize);
    spot_addElement("overlapAutoToolTips", overlapAutoToolTips);
    spot_addElement("manageFocusedActions", manageFocusedActions);
    spot_addElement("dynamicNameLookup", dynamicNameLookup);
    spot_addElement("populateGlobalConstants", populateGlobalConstants);
    spot_addElement("viewersLocalByDefault", viewersLocalByDefault);
    spot_addElement("comURLRedirection", comURLRedirection);
    spot_addElement("startRedirectorOnDemand", startRedirectorOnDemand);
    spot_addElement("ignoreFormatExceptions", ignoreFormatExceptions);
    spot_addElement("allWidgetsDraggable", allWidgetsDraggable);
    spot_addElement("allLabelsDraggable", allLabelsDraggable);
    spot_addElement("allTextFieldsDraggable", allTextFieldsDraggable);
    spot_addElement("allTextFieldsDroppable", allTextFieldsDroppable);
    spot_addElement("defaultMonospaceFont", defaultMonospaceFont);
    spot_addElement("changeSelColorOnLostFocus", changeSelColorOnLostFocus);
    spot_addElement("selectionPainter", selectionPainter);
    spot_addElement("lostFocusSelectionPainter", lostFocusSelectionPainter);
    spot_addElement("autoHilightPainter", autoHilightPainter);
    spot_addElement("pressedPainter", pressedPainter);
    spot_addElement("tooltipPainter", tooltipPainter);
    spot_addElement("widgetFocusPainter", widgetFocusPainter);
    spot_addElement("listItemFocusPainter", listItemFocusPainter);
    spot_addElement("httpAuthHandler", httpAuthHandler);
    httpAuthHandler.spot_defineAttribute("authFunction", null);
    httpAuthHandler.spot_defineAttribute("forceLowerCasePassword", "false");
    httpAuthHandler.spot_defineAttribute("keepPasswordHash", "false");
    httpAuthHandler.spot_defineAttribute("keepPassword", "false");
    spot_addElement("scaleIconsWithFont", scaleIconsWithFont);
    scaleIconsWithFont.spot_defineAttribute("defaultBaseSize", null);
    scaleIconsWithFont.spot_defineAttribute("defaultBaseScaleFactor", null);
    scaleIconsWithFont.spot_defineAttribute("scaling", null);
    spot_addElement("relativePrintSize", relativePrintSize);
    spot_addElement("enableHTTPResponseCacheing", enableHTTPResponseCacheing);
    enableHTTPResponseCacheing.spot_defineAttribute("mbMaxCacheSize", "50");
    enableHTTPResponseCacheing.spot_defineAttribute("deleteOnExit", "false");
    enableHTTPResponseCacheing.spot_defineAttribute("usePlatformCache", "true");
    enableHTTPResponseCacheing.spot_defineAttribute("cacheName", null);
    spot_addElement("useCustomFilePreferencesFactory", useCustomFilePreferencesFactory);
    spot_addElement("manageScrollWheel", manageScrollWheel);
    spot_addElement("managedScreenSizes", managedScreenSizes);
    managedScreenSizes.spot_defineAttribute("xsmallScreenPointSize", null);
    managedScreenSizes.spot_defineAttribute("smallScreenPointSize", null);
    managedScreenSizes.spot_defineAttribute("mediumScreenPointSize", null);
    managedScreenSizes.spot_defineAttribute("autoFallback", null);
    spot_addElement("desktopIconDensity", desktopIconDensity);
    spot_addElement("mainWindow", mainWindow);
    spot_addElement("deferredImageIcon", deferredImageIcon);
    deferredImageIcon.spot_defineAttribute("size", null);
    deferredImageIcon.spot_defineAttribute("scaling", null);
    deferredImageIcon.spot_defineAttribute("density", null);
    spot_addElement("customPropertyPrefix", customPropertyPrefix);
    spot_addElement("applicationListenerClass", applicationListenerClass);
  }

  /**
   * Gets the widgetHandlers element
   *
   * @return the widgetHandlers element or null if a reference was never created
   */
  public SPOTSet getWidgetHandlers() {
    return widgetHandlers;
  }

  /**
   * Gets the reference to the widgetHandlers element
   * A reference is created if necessary
   *
   * @return the reference to the widgetHandlers element
   */
  public SPOTSet getWidgetHandlersReference() {
    if (widgetHandlers == null) {
      widgetHandlers = new SPOTSet("widget", new NameValuePair(), -1, -1, true);
      super.spot_setReference("widgetHandlers", widgetHandlers);
    }

    return widgetHandlers;
  }

  /**
   * Sets the reference to the widgetHandlers element
   * @param reference the reference ( can be null)
   *
   * @throws ClassCastException if the parameter is not valid
   */
  public void setWidgetHandlers(iSPOTElement reference) throws ClassCastException {
    widgetHandlers = (SPOTSet) reference;
    spot_setReference("widgetHandlers", reference);
  }

  /**
   * Gets the collectionHandlers element
   *
   * @return the collectionHandlers element or null if a reference was never created
   */
  public SPOTSet getCollectionHandlers() {
    return collectionHandlers;
  }

  /**
   * Gets the reference to the collectionHandlers element
   * A reference is created if necessary
   *
   * @return the reference to the collectionHandlers element
   */
  public SPOTSet getCollectionHandlersReference() {
    if (collectionHandlers == null) {
      collectionHandlers = new SPOTSet("handler", new NameValuePair(), -1, -1, true);
      super.spot_setReference("collectionHandlers", collectionHandlers);
    }

    return collectionHandlers;
  }

  /**
   * Sets the reference to the collectionHandlers element
   * @param reference the reference ( can be null)
   *
   * @throws ClassCastException if the parameter is not valid
   */
  public void setCollectionHandlers(iSPOTElement reference) throws ClassCastException {
    collectionHandlers = (SPOTSet) reference;
    spot_setReference("collectionHandlers", reference);
  }

  /**
   * Gets the supplementalFonts element
   *
   * @return the supplementalFonts element or null if a reference was never created
   */
  public SPOTSet getSupplementalFonts() {
    return supplementalFonts;
  }

  /**
   * Gets the reference to the supplementalFonts element
   * A reference is created if necessary
   *
   * @return the reference to the supplementalFonts element
   */
  public SPOTSet getSupplementalFontsReference() {
    if (supplementalFonts == null) {
      supplementalFonts = new SPOTSet("font", new NameValuePair(), -1, -1, true);
      super.spot_setReference("supplementalFonts", supplementalFonts);
    }

    return supplementalFonts;
  }

  /**
   * Sets the reference to the supplementalFonts element
   * @param reference the reference ( can be null)
   *
   * @throws ClassCastException if the parameter is not valid
   */
  public void setSupplementalFonts(iSPOTElement reference) throws ClassCastException {
    supplementalFonts = (SPOTSet) reference;
    spot_setReference("supplementalFonts", reference);
  }

  /**
   * Gets the supplementalJars element
   *
   * @return the supplementalJars element or null if a reference was never created
   */
  public SPOTSet getSupplementalJars() {
    return supplementalJars;
  }

  /**
   * Gets the reference to the supplementalJars element
   * A reference is created if necessary
   *
   * @return the reference to the supplementalJars element
   */
  public SPOTSet getSupplementalJarsReference() {
    if (supplementalJars == null) {
      supplementalJars = new SPOTSet("url", new SPOTPrintableString(null, 0, 255, false), -1, -1, true);
      super.spot_setReference("supplementalJars", supplementalJars);
      supplementalJars.spot_defineAttribute("downloadMessage", null);
    }

    return supplementalJars;
  }

  /**
   * Sets the reference to the supplementalJars element
   * @param reference the reference ( can be null)
   *
   * @throws ClassCastException if the parameter is not valid
   */
  public void setSupplementalJars(iSPOTElement reference) throws ClassCastException {
    supplementalJars = (SPOTSet) reference;
    spot_setReference("supplementalJars", reference);
  }

  /**
   * Gets the defaultMonospaceFont element
   *
   * @return the defaultMonospaceFont element or null if a reference was never created
   */
  public Font getDefaultMonospaceFont() {
    return defaultMonospaceFont;
  }

  /**
   * Gets the reference to the defaultMonospaceFont element
   * A reference is created if necessary
   *
   * @return the reference to the defaultMonospaceFont element
   */
  public Font getDefaultMonospaceFontReference() {
    if (defaultMonospaceFont == null) {
      defaultMonospaceFont = new Font(true);
      super.spot_setReference("defaultMonospaceFont", defaultMonospaceFont);
    }

    return defaultMonospaceFont;
  }

  /**
   * Sets the reference to the defaultMonospaceFont element
   * @param reference the reference ( can be null)
   *
   * @throws ClassCastException if the parameter is not valid
   */
  public void setDefaultMonospaceFont(iSPOTElement reference) throws ClassCastException {
    defaultMonospaceFont = (Font) reference;
    spot_setReference("defaultMonospaceFont", reference);
  }

  /**
   * Gets the selectionPainter element
   *
   * @return the selectionPainter element or null if a reference was never created
   */
  public GridCell getSelectionPainter() {
    return selectionPainter;
  }

  /**
   * Gets the reference to the selectionPainter element
   * A reference is created if necessary
   *
   * @return the reference to the selectionPainter element
   */
  public GridCell getSelectionPainterReference() {
    if (selectionPainter == null) {
      selectionPainter = new GridCell(true);
      super.spot_setReference("selectionPainter", selectionPainter);
      selectionPainter.spot_defineAttribute("foreground", null);
    }

    return selectionPainter;
  }

  /**
   * Sets the reference to the selectionPainter element
   * @param reference the reference ( can be null)
   *
   * @throws ClassCastException if the parameter is not valid
   */
  public void setSelectionPainter(iSPOTElement reference) throws ClassCastException {
    selectionPainter = (GridCell) reference;
    spot_setReference("selectionPainter", reference);
  }

  /**
   * Gets the lostFocusSelectionPainter element
   *
   * @return the lostFocusSelectionPainter element or null if a reference was never created
   */
  public GridCell getLostFocusSelectionPainter() {
    return lostFocusSelectionPainter;
  }

  /**
   * Gets the reference to the lostFocusSelectionPainter element
   * A reference is created if necessary
   *
   * @return the reference to the lostFocusSelectionPainter element
   */
  public GridCell getLostFocusSelectionPainterReference() {
    if (lostFocusSelectionPainter == null) {
      lostFocusSelectionPainter = new GridCell(true);
      super.spot_setReference("lostFocusSelectionPainter", lostFocusSelectionPainter);
      lostFocusSelectionPainter.spot_defineAttribute("foreground", null);
      lostFocusSelectionPainter.spot_defineAttribute("os", null);
    }

    return lostFocusSelectionPainter;
  }

  /**
   * Sets the reference to the lostFocusSelectionPainter element
   * @param reference the reference ( can be null)
   *
   * @throws ClassCastException if the parameter is not valid
   */
  public void setLostFocusSelectionPainter(iSPOTElement reference) throws ClassCastException {
    lostFocusSelectionPainter = (GridCell) reference;
    spot_setReference("lostFocusSelectionPainter", reference);
  }

  /**
   * Gets the autoHilightPainter element
   *
   * @return the autoHilightPainter element or null if a reference was never created
   */
  public GridCell getAutoHilightPainter() {
    return autoHilightPainter;
  }

  /**
   * Gets the reference to the autoHilightPainter element
   * A reference is created if necessary
   *
   * @return the reference to the autoHilightPainter element
   */
  public GridCell getAutoHilightPainterReference() {
    if (autoHilightPainter == null) {
      autoHilightPainter = new GridCell(true);
      super.spot_setReference("autoHilightPainter", autoHilightPainter);
    }

    return autoHilightPainter;
  }

  /**
   * Sets the reference to the autoHilightPainter element
   * @param reference the reference ( can be null)
   *
   * @throws ClassCastException if the parameter is not valid
   */
  public void setAutoHilightPainter(iSPOTElement reference) throws ClassCastException {
    autoHilightPainter = (GridCell) reference;
    spot_setReference("autoHilightPainter", reference);
  }

  /**
   * Gets the pressedPainter element
   *
   * @return the pressedPainter element or null if a reference was never created
   */
  public GridCell getPressedPainter() {
    return pressedPainter;
  }

  /**
   * Gets the reference to the pressedPainter element
   * A reference is created if necessary
   *
   * @return the reference to the pressedPainter element
   */
  public GridCell getPressedPainterReference() {
    if (pressedPainter == null) {
      pressedPainter = new GridCell(true);
      super.spot_setReference("pressedPainter", pressedPainter);
    }

    return pressedPainter;
  }

  /**
   * Sets the reference to the pressedPainter element
   * @param reference the reference ( can be null)
   *
   * @throws ClassCastException if the parameter is not valid
   */
  public void setPressedPainter(iSPOTElement reference) throws ClassCastException {
    pressedPainter = (GridCell) reference;
    spot_setReference("pressedPainter", reference);
  }

  /**
   * Gets the tooltipPainter element
   *
   * @return the tooltipPainter element or null if a reference was never created
   */
  public GridCell getTooltipPainter() {
    return tooltipPainter;
  }

  /**
   * Gets the reference to the tooltipPainter element
   * A reference is created if necessary
   *
   * @return the reference to the tooltipPainter element
   */
  public GridCell getTooltipPainterReference() {
    if (tooltipPainter == null) {
      tooltipPainter = new GridCell(true);
      super.spot_setReference("tooltipPainter", tooltipPainter);
      tooltipPainter.spot_defineAttribute("foreground", null);
    }

    return tooltipPainter;
  }

  /**
   * Sets the reference to the tooltipPainter element
   * @param reference the reference ( can be null)
   *
   * @throws ClassCastException if the parameter is not valid
   */
  public void setTooltipPainter(iSPOTElement reference) throws ClassCastException {
    tooltipPainter = (GridCell) reference;
    spot_setReference("tooltipPainter", reference);
  }

  /**
   * Gets the widgetFocusPainter element
   *
   * @return the widgetFocusPainter element or null if a reference was never created
   */
  public GridCell getWidgetFocusPainter() {
    return widgetFocusPainter;
  }

  /**
   * Gets the reference to the widgetFocusPainter element
   * A reference is created if necessary
   *
   * @return the reference to the widgetFocusPainter element
   */
  public GridCell getWidgetFocusPainterReference() {
    if (widgetFocusPainter == null) {
      widgetFocusPainter = new GridCell(true);
      super.spot_setReference("widgetFocusPainter", widgetFocusPainter);
    }

    return widgetFocusPainter;
  }

  /**
   * Sets the reference to the widgetFocusPainter element
   * @param reference the reference ( can be null)
   *
   * @throws ClassCastException if the parameter is not valid
   */
  public void setWidgetFocusPainter(iSPOTElement reference) throws ClassCastException {
    widgetFocusPainter = (GridCell) reference;
    spot_setReference("widgetFocusPainter", reference);
  }

  /**
   * Gets the listItemFocusPainter element
   *
   * @return the listItemFocusPainter element or null if a reference was never created
   */
  public GridCell getListItemFocusPainter() {
    return listItemFocusPainter;
  }

  /**
   * Gets the reference to the listItemFocusPainter element
   * A reference is created if necessary
   *
   * @return the reference to the listItemFocusPainter element
   */
  public GridCell getListItemFocusPainterReference() {
    if (listItemFocusPainter == null) {
      listItemFocusPainter = new GridCell(true);
      super.spot_setReference("listItemFocusPainter", listItemFocusPainter);
    }

    return listItemFocusPainter;
  }

  /**
   * Sets the reference to the listItemFocusPainter element
   * @param reference the reference ( can be null)
   *
   * @throws ClassCastException if the parameter is not valid
   */
  public void setListItemFocusPainter(iSPOTElement reference) throws ClassCastException {
    listItemFocusPainter = (GridCell) reference;
    spot_setReference("listItemFocusPainter", reference);
  }

  /**
   * Gets the mainWindow element
   *
   * @return the mainWindow element or null if a reference was never created
   */
  public MainWindow getMainWindow() {
    return mainWindow;
  }

  /**
   * Gets the reference to the mainWindow element
   * A reference is created if necessary
   *
   * @return the reference to the mainWindow element
   */
  public MainWindow getMainWindowReference() {
    if (mainWindow == null) {
      mainWindow = new MainWindow(true);
      super.spot_setReference("mainWindow", mainWindow);
    }

    return mainWindow;
  }

  /**
   * Sets the reference to the mainWindow element
   * @param reference the reference ( can be null)
   *
   * @throws ClassCastException if the parameter is not valid
   */
  public void setMainWindow(iSPOTElement reference) throws ClassCastException {
    mainWindow = (MainWindow) reference;
    spot_setReference("mainWindow", reference);
  }

  //}GENERATED_METHODS
  //GENERATED_INNER_CLASSES{

  /**
   * Class that defines the valid set of choices for
   * the <code>Application.comURLRedirection</code> ENUMERATED object
   */
  public static class CComURLRedirection extends SPOTEnumerated {

    /** do not redirect */
    public final static int none = 0;

    /** redirect all */
    public final static int all = 1;

    /** redirect when within the same context */
    public final static int same_context = 2;

    /** redirect when accessing the same server */
    public final static int same_server = 3;

    /** redirect when accessing the same server and port */
    public final static int same_server_and_port = 4;

    /**
     * Creates a new <code>CComURLRedirection</code> object
     */
    public CComURLRedirection() {
      this(null, null, null, null, true);
    }

    /**
     * Creates a new <code>CComURLRedirection</code> object
     *
     * @param val the value
     */
    public CComURLRedirection(int val) {
      super();
      _sChoices = _schoices;
      _nChoices = _nchoices;
      setValue(val);
    }

    /**
     * Creates a new <code>comURLRedirection</code> object
     * the <code>Application.comURLRedirection</code> ENUMERATED object
     *
     * @param ival         the integer value
     * @param sval         the string  value
     * @param idefaultval  the integer default value
     * @param sdefaultval  the string default value
     * @param optional   <code>true</code> if the node the object represents is optional
     */
    public CComURLRedirection(Integer ival, String sval, Integer idefaultval, String sdefaultval, boolean optional) {
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
      return "{" + "none(0), " + "all(1), " + "same_context(2), " + "same_server(3), " + "same_server_and_port(4) }";
    }

    private final static int    _nchoices[] = { 0, 1, 2, 3, 4 };
    private final static String _schoices[] = { "none", "all", "same_context", "same_server", "same_server_and_port" };
  }


  /**
   * Class that defines the valid set of choices for
   * the <code>Application.httpAuthHandler</code> ENUMERATED object
   */
  public static class CHttpAuthHandler extends SPOTEnumerated {

    /** let the runtime decide bases on how the runtime was launched */
    public final static int auto = 0;

    /** authentication is handled transparently */
    public final static int standard = 1;

    /** application  handles authentication */
    public final static int none = 3;

    /**
     * Creates a new <code>CHttpAuthHandler</code> object
     */
    public CHttpAuthHandler() {
      this(null, null, null, null, true);
    }

    /**
     * Creates a new <code>CHttpAuthHandler</code> object
     *
     * @param val the value
     */
    public CHttpAuthHandler(int val) {
      super();
      _sChoices = _schoices;
      _nChoices = _nchoices;
      spot_setAttributes();
      setValue(val);
    }

    /**
     * Creates a new <code>httpAuthHandler</code> object
     * the <code>Application.httpAuthHandler</code> ENUMERATED object
     *
     * @param ival         the integer value
     * @param sval         the string  value
     * @param idefaultval  the integer default value
     * @param sdefaultval  the string default value
     * @param optional   <code>true</code> if the node the object represents is optional
     */
    public CHttpAuthHandler(Integer ival, String sval, Integer idefaultval, String sdefaultval, boolean optional) {
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
      return "{" + "auto(0), " + "standard(1), " + "none(3) }";
    }

    private void spot_setAttributes() {
      this.attributeSizeHint += 4;
      spot_defineAttribute("authFunction", null);
      spot_defineAttribute("forceLowerCasePassword", "false");
      spot_defineAttribute("keepPasswordHash", "false");
      spot_defineAttribute("keepPassword", "false");
    }

    private final static int    _nchoices[] = { 0, 1, 3 };
    private final static String _schoices[] = { "auto", "standard", "none" };
  }


  /**
   * Class that defines the valid set of choices for
   * the <code>Application.managedScreenSizes</code> ENUMERATED object
   */
  public static class CManagedScreenSizes extends SPOTEnumerated {

    /** let the application manage */
    public final static int none = 0;

    /** provide support for small, and large screens */
    public final static int small_large = 1;

    /** provide support for small, medium, and large screens */
    public final static int small_medium_large = 3;

    /** provide support for extra small, small, and large screens */
    public final static int xsmall_small_large = 4;

    /** provide support for extra small, small, medium, and large screens */
    public final static int xsmall_small_medium_large = 5;

    /**
     * Creates a new <code>CManagedScreenSizes</code> object
     */
    public CManagedScreenSizes() {
      this(null, null, null, null, true);
    }

    /**
     * Creates a new <code>CManagedScreenSizes</code> object
     *
     * @param val the value
     */
    public CManagedScreenSizes(int val) {
      super();
      _sChoices = _schoices;
      _nChoices = _nchoices;
      spot_setAttributes();
      setValue(val);
    }

    /**
     * Creates a new <code>managedScreenSizes</code> object
     * the <code>Application.managedScreenSizes</code> ENUMERATED object
     *
     * @param ival         the integer value
     * @param sval         the string  value
     * @param idefaultval  the integer default value
     * @param sdefaultval  the string default value
     * @param optional   <code>true</code> if the node the object represents is optional
     */
    public CManagedScreenSizes(Integer ival, String sval, Integer idefaultval, String sdefaultval, boolean optional) {
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
      return "{" + "none(0), " + "small_large(1), " + "small_medium_large(3), " + "xsmall_small_large(4), "
             + "xsmall_small_medium_large(5) }";
    }

    private void spot_setAttributes() {
      this.attributeSizeHint += 4;
      spot_defineAttribute("xsmallScreenPointSize", null);
      spot_defineAttribute("smallScreenPointSize", null);
      spot_defineAttribute("mediumScreenPointSize", null);
      spot_defineAttribute("autoFallback", null);
    }

    private final static int    _nchoices[] = { 0, 1, 3, 4, 5 };
    private final static String _schoices[] = { "none", "small_large", "small_medium_large", "xsmall_small_large",
            "xsmall_small_medium_large" };
  }
  //}GENERATED_INNER_CLASSES
}