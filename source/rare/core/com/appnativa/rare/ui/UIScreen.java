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

package com.appnativa.rare.ui;

import com.appnativa.rare.Platform;
import com.appnativa.rare.platform.PlatformHelper;
import com.appnativa.rare.ui.ScreenUtils.Unit;

/**
 * A class they that provides platform agnostic way to access information about
 * the device's screen
 *
 * @author Don DeCoteau
 *
 */
public final class UIScreen {
  // Updated at runtime
  public static int PLATFORM_PIXELS_1  = 1;
  public static int PLATFORM_PIXELS_10 = 10;
  public static int PLATFORM_PIXELS_16 = 16;
  public static int PLATFORM_PIXELS_2  = 2;
  public static int PLATFORM_PIXELS_3  = 3;
  public static int PLATFORM_PIXELS_4  = 4;
  public static int PLATFORM_PIXELS_5  = 5;
  public static int PLATFORM_PIXELS_6  = 6;
  public static int PLATFORM_PIXELS_7  = 7;
  public static int PLATFORM_PIXELS_8  = 8;
  public static int PLATFORM_PIXELS_9  = 9;

  public static enum ScreenSize {
    xsmall, small, medium, large
  }

  /**
   * Adjusts the specified dimensions to ensure that at a minimum they will
   * accommodate a line of text
   *
   * @param c
   *          the component the dimensions are for
   * @param d
   *          the dimensions
   * @return the adjusted dimsnsions
   */
  public static UIDimension adjustForLineHeight(iPlatformComponent c, UIDimension d) {
    return ScreenUtils.adjustForLineHeight(c, d);
  }

  /**
   * Converts the specified size to a rare pixel (logical pixel)
   *
   * @param size the size to be converted
   *
   * @return the platform relative pixels
   */
  public static float fromPlatformPixels(float size) {
    return fromPlatformPixels(size, Unit.RARE_PIXELS, true);
  }

  /**
   * Converts the specified size to platform relative pixels
   *
   * @param size the size to be converted
   * @param unit
   *          the unit the size represents
   *
   * @return the platform relative pixels
   */
  public static float fromPlatformPixels(float size, Unit unit) {
    return fromPlatformPixels(size, unit, true);
  }

  /**
   * Converts the specified size to platform relative pixels
   *
   * @param size the size to be converted
   * @param unit
   *          the unit the size represents
   * @param forWidth
   *          true if the size represents a width; false otherwise
   *
   * @return the platform relative pixels
   */
  public static float fromPlatformPixels(float size, Unit unit, boolean forWidth) {
    return ScreenUtils.fromPlatformPixels(size, unit, forWidth);
  }

  /**
   * Gets the height that represents a line for text for the specified component
   *
   * @param c
   *          the component
   * @return the line height
   */
  public static int lineHeight(iPlatformComponent c) {
    return ScreenUtils.lineHeight(c);
  }

  /**
   * Locks the devices into a specific orientation preventing the user from
   * changing the orientation.
   *
   * @param landscape
   *          true for landscape; false or portrait and null for the current
   *          orientation
   */
  public static void lockOrientation(Boolean landscape) {
    Platform.getAppContext().lockOrientation(landscape);
  }

  /**
   * Converts the RARE pixels to platform relative pixels (i.e. whatever is
   * needed by the platform specific APIs)
   *
   * @param pixels
   *          the RARE pixels
   * @return the platform relative pixels
   */
  public static int platformPixels(int pixels) {
    return ScreenUtils.platformPixels(pixels);
  }

  /**
   * Converts the RARE pixels to platform relative pixels (i.e. whatever is
   * needed by the platform specific APIs)
   *
   * @param pixels
   *          the RARE pixels
   * @return the platform relative pixels
   */
  public static float platformPixelsf(float pixels) {
    return ScreenUtils.platformPixelsf(pixels);
  }

  /**
   * Snaps the value to an integer pixel position
   *
   * @param value
   *          the value
   * @return the integer pixel position
   */
  public static int snapToPosition(double value) {
    return (int) Math.round(value);
  }

  /**
   * Snaps the value to an integer pixel position
   *
   * @param value
   *          the value
   * @return the integer pixel position
   */
  public static int snapToPosition(float value) {
    return Math.round(value);
  }

  /**
   * Snaps the value to an integer pixel representing the width or height of an
   * entity
   *
   * @param value
   *          the value
   * @return the integer pixel size
   */
  public static int snapToSize(double value) {
    return (int) Math.ceil(value);
  }

  /**
   * Snaps the value to an integer pixel representing the width or height of an
   * entity
   *
   * @param value
   *          the value
   * @return the integer pixel size
   */
  public static int snapToSize(float value) {
    return (int) Math.ceil(value);
  }

  /**
   * Converts the specified value to platform relative pixels
   *
   * @param value
   *          the value which may contain a unit prefix (e.g. "10ln")
   * @param c
   *          the component the conversion is for
   * @param heightForPercent
   *          if the unit is percent then the height then the percentage is
   *          based on
   * @param charAdjust
   *          true to add an adjustment to compensate for when rendering with
   *          strings; false otherwise
   *
   * @return the platform relative pixels
   */
  public static int toPlatformPixelHeight(String value, iPlatformComponent c, float heightForPercent,
          boolean charAdjust) {
    return ScreenUtils.toPlatformPixelHeight(value, c, heightForPercent, charAdjust);
  }

  /**
   * Converts the specified height to platform relative pixels
   *
   * @param height
   *          the height
   * @param unit
   *          the unit of the height
   * @param c
   *          the component the conversion is for
   * @param heightForPercent
   *          if the unit is percent then the height then the percentage is
   *          based on
   * @param charAdjust
   *          true to add an adjustment to compensate for when rendering with
   *          strings; false otherwise
   *
   * @return the platform relative pixels
   */
  public static int toPlatformPixelHeight(float height, Unit unit, iPlatformComponent c, float heightForPercent,
          boolean charAdjust) {
    return ScreenUtils.toPlatformPixelHeight(height, unit, c, heightForPercent, charAdjust);
  }

  /**
   * Converts the specified value to platform relative pixels
   *
   * @param value
   *          the value which may contain a unit prefix (e.g. "10ch")
   * @param c
   *          the component the conversion is for
   * @param widthForPercent
   *          if the unit is percent then the width then the percentage is based
   *          on
   * @param charAdjust
   *          true to add an adjustment to compensate for when rendering with
   *          strings; false otherwise
   *
   * @return the platform relative pixels
   */
  public static int toPlatformPixelWidth(String value, iPlatformComponent c, float widthForPercent,
          boolean charAdjust) {
    return ScreenUtils.toPlatformPixelWidth(value, c, widthForPercent, charAdjust);
  }

  /**
   * Converts the specified width to platform relative pixels
   *
   * @param width
   *          the width
   * @param unit
   *          the unit of the width
   * @param c
   *          the component the conversion is for
   * @param widthForPercent
   *          if the unit is percent then the width then the percentage is based
   *          on
   * @param charAdjust
   *          true to add an adjustment to compensate for when rendering with
   *          strings; false otherwise
   *
   * @return the platform relative pixels
   */
  public static int toPlatformPixelWidth(float width, Unit unit, iPlatformComponent c, float widthForPercent,
          boolean charAdjust) {
    return ScreenUtils.toPlatformPixelWidth(width, unit, c, widthForPercent, charAdjust);
  }

  /**
   * Converts the specified size to platform relative pixels
   *
   * @param size the size to be converted
   * @param unit
   *          the unit the size represents
   * @param forWidth
   *          true if the size represents a width; false otherwise
   *
   * @return the platform relative pixels
   */
  public static float toPlatformPixels(float size, Unit unit, boolean forWidth) {
    return ScreenUtils.toPlatformPixels(size, unit, forWidth);
  }

  /**
   * All the user to changes the orientation of the device.
   *
   * @see #lockOrientation(Boolean)
   */
  public static void unlockOrientation() {
    Platform.getAppContext().unlockOrientation();
  }

  /**
   * Changes the device's current orientation to the specified orientation.
   *
   * @param orientation
   *          an object that represents a device orientation retrieved via
   *          {@link #getOrientation()}
   *
   * @see #getOrientation
   */
  public static void setOrientation(Object orientation) {
    ScreenUtils.setOrientation(orientation);
  }

  /**
   * Sets the relative screen size to xsmall. This forces the runtime to report
   * the screen as as extra small. If multi-screen supports has been enabled
   * then the runtime will render accordingly.
   */
  public static void setRelativeScreenSizeAsExtraSmall() {
    ScreenUtils.setScreenSize(ScreenSize.xsmall);
  }

  /**
   * Sets the relative screen size to large. This forces the runtime to report
   * the screen as as large screen. If multi-screen supports has been enabled
   * then the runtime will render accordingly.
   */
  public static void setRelativeScreenSizeAsLarge() {
    ScreenUtils.setScreenSize(ScreenSize.large);
  }

  /**
   * Sets the relative screen size to medium. This forces the runtime to report
   * the screen as as medium screen. If multi-screen supports has been enabled
   * then the runtime will render accordingly.
   */
  public static void setRelativeScreenSizeAsMedium() {
    ScreenUtils.setScreenSize(ScreenSize.medium);
  }

  /**
   * Sets the relative screen size to small. This forces the runtime to report
   * the screen as as small screen. If multi-screen supports has been enabled
   * then the runtime will render accordingly.
   */
  public static void setRelativeScreenSizeAsSmall() {
    ScreenUtils.setScreenSize(ScreenSize.small);
  }

  /**
   * Sets the status bar text (on supported platforms) to either light or dark.
   * Note: On iOS UIViewControllerBasedStatusBarAppearance must be set to true
   * in you plist file
   *
   * @param dark
   *          true for dark; false for light
   */
  public static void setUseDarkStatusBarText(boolean dark) {
    PlatformHelper.setUseDarkStatusBarText(dark);
  }

  /**
   * Gets the bounds of the screen
   *
   * @return the bounds of the screen
   */
  public static UIRectangle getBounds() {
    return ScreenUtils.getBounds();
  }

  /**
   * Gets the bounds of a screen
   *
   * @param screen
   *          the screen to get the bounds for
   *
   * @return the bounds of the screen
   */
  public static UIRectangle getBounds(int screen) {
    return ScreenUtils.getBounds(screen);
  }

  /**
   * Gets the padding used to pad widths during conversions when the charAdjust
   * parameter is true
   *
   * @return the padding used to pad widths during conversions
   */
  public static float getCharWidthPadding() {
    return ScreenUtils.getCharWidthPadding();
  }

  /**
   * Gets the screen density. The value CAN be used to convert from RARE pixels
   * to device pixels
   *
   * @return the screen density
   */
  public static float getDensity() {
    return ScreenUtils.getDensity();
  }

  /**
   * Gets the screen density name. On android the name maps to the android
   * density names. On Applle "mdpi" is for no-retina and "xhdpi" is for retina
   * display. On SWING it's always "mdpi".
   *
   * @return the screen density name
   */
  public static String getDensityName() {
    return ScreenUtils.getDensityName();
  }

  /**
   * Gets the height of the screen
   *
   * @return the height of the screen
   */
  public static int getHeight() {
    return ScreenUtils.getHeight();
  }

  /**
   * Gets an object that represents the device's current orientation
   *
   * @return an object that represents the device's current orientation
   *
   * @see #setOrientation(Object)
   */
  public static Object getOrientation() {
    return ScreenUtils.getOrientation();
  }

  public static String getOrientationName() {
    return isWider()
           ? "landscape"
           : "portrait";
  }

  /**
   * Gets the multiplier used to convert RARE pixels to platform pixels. This
   * multiplier is NOT for converting to device pixels
   *
   * @see #getDensity()
   * @return the multiplier
   */
  public static float getPixelMultiplier() {
    return ScreenUtils.getPixelMultiplier();
  }

  /**
   * Get a font appropriate for printing the specified screen font
   *
   * @param f
   *          the screen font
   * @return the printer font
   */
  public static UIFont getPrinterFont(UIFont f) {
    return f.deriveFontSize(getPrinterScaleFactor() * f.getSize2D());
  }

  /**
   * Gets the scale factor used to convert a screen font to a printer font
   *
   * @return the scale factor
   */
  public static float getPrinterScaleFactor() {
    return ScreenUtils.getPrinterScaleFactor();
  }

  /**
   * Gets the relative screen size
   *
   * @return the relative screen size
   */
  public static ScreenSize getRelativeScreenSize() {
    return ScreenUtils.getRelativeScreenSize();
  }

  /**
   * Gets the relative screen size name
   *
   * @return the relative screen size name
   *
   * @see #getRelativeScreenSize()
   */
  public static String getRelativeScreenSizeName() {
    return ScreenUtils.getRelativeScreenSizeName();
  }

  /**
   * Gets the devices current rotation in degrees
   *
   * @return the devices current rotation in degrees
   */
  public static int getRotation() {
    return ScreenUtils.getRotation();
  }

  /**
   * Gets the device rotation for the specified configuration. Meant to be use
   * during pending orientation changes to get the pending rotation
   *
   * @param configuration
   *          the configuration
   * @return the rotation in degrees for the configuration
   */
  public static int getRotationForConfiguration(Object configuration) {
    if (configuration == null) {
      return PlatformHelper.getScreenRotation();
    }

    return PlatformHelper.getScreenRotation(configuration);
  }

  /**
   * Gets the screen where the component is currently rendered
   *
   * @param c
   *          the component
   * @return the number of the screen or -1 if the component is not on screen
   */
  public static int getScreen(iPlatformComponent c) {
    return ScreenUtils.getScreen(c);
  }

  /**
   * Get the number of available screens
   *
   * @return the number of available screens
   */
  public static int getScreenCount() {
    return ScreenUtils.getScreenCount();
  }

  /**
   * Gets the DPI of the screen
   *
   * @return the DPI of the screen
   */
  public static float getScreenDPI() {
    return ScreenUtils.getScreenDPI();
  }

  /**
   * Given a standard java font size, returns a font size based on the screens
   * DPI
   *
   * @param javasize
   *          the java font size
   * @return the screen font size
   */
  public static float getScreenFontSize(float javasize) {
    return ScreenUtils.getScreenFontSize(javasize);
  }

  /**
   * Gets the current size of the screen
   *
   * @return the current size of the screen
   */
  public static UIDimension getScreenSize() {
    return ScreenUtils.getSize();
  }

  /**
   * Gets the size represented by the specified string values
   *
   * @param width
   *          the width (may contain a unit suffix)
   * @param height
   *          the height (may contain a unit suffix)
   * @param comp
   *          the component the dimensions are for
   * @return the size
   */
  public static UIDimension getSize(String width, String height, iPlatformComponent comp) {
    return new UIDimension(toPlatformPixelWidth(width, comp, -1, true), toPlatformPixelHeight(width, comp, -1, true));
  }

  /**
   * Gets the dimensions of screen for the specified configuration. Meant to be
   * use during pending orientation changes to get the pending oriented size
   *
   * @param configuration
   *          the configuration
   * @return the dimensions for the configuration
   */
  public static UIDimension getSizeForConfiguration(Object configuration) {
    if (configuration == null) {
      return PlatformHelper.getScreenSize();
    }

    return PlatformHelper.getScreenSizeForConfiguration(configuration);
  }

  /**
   * Gets screen (or window when on desktops) configuration where the specified
   * component is located in. Meant to be outside of orientation change handlers
   * to get oriented size in a way the is consistent with orientation changes
   *
   * @return configuration the configuration
   * @return the dimensions for the configuration
   */
  public static Object getConfiguration(iPlatformComponent comp) {
    return PlatformHelper.getConfiguration(comp);
  }

  /**
   * Gets the use-able screen space This is the space available for application
   * UI elements
   *
   * @return the use-able screen space
   */
  public static UIDimension getUsableSize() {
    return ScreenUtils.getUsableScreenSize();
  }

  /**
   * Gets the use-able screen bounds for the specified screen This is the space
   * available for application UI elements
   *
   * @param screen
   *          the screen (use zero for the main screen)
   * @return the use-able screen space
   */
  public static UIRectangle getUsableScreenBounds(int screen) {
    return PlatformHelper.getUsableScreenBounds(screen);
  }

  /**
   * Gets the use-able screen bounds for the screen when the specified component
   * is displayed. This is the space available for application UI elements
   *
   * @param c
   *          the component
   * @return the use-able screen space
   */
  public static UIRectangle getUsableScreenBounds(iPlatformComponent c) {
    return PlatformHelper.getUsableScreenBounds(c);
  }

  /**
   * Gets the width of the screen
   *
   * @return the width of the screen
   */
  public static int getWidth() {
    return ScreenUtils.getWidth();
  }

  /**
   * Gets whether the runtime treats the screen as extra small
   *
   * @return true if the runtime treats the screen as extra small; false
   *         otherwise
   */
  public static boolean isExtraSmallScreen() {
    return ScreenUtils.isExtraSmallScreen();
  }

  /**
   * Gets whether the screen is a high density screen
   *
   * @return true if the screen is a high density screen; false otherwise
   */
  public static boolean isHighDensity() {
    return ScreenUtils.isHighDensity();
  }

  /**
   * Gets whether the runtime treats the screen as large
   *
   * @return true if the runtime treats the screen as large; false otherwise
   */
  public static boolean isLargeScreen() {
    return ScreenUtils.isLargeScreen();
  }

  /**
   * Gets whether the screen is a low density screen
   *
   * @return true if the screen is a low density screen; false otherwise
   */
  public static boolean isLowDensity() {
    return ScreenUtils.isLowDensity();
  }

  /**
   * Gets whether the screen is a medium density screen
   *
   * @return true if the screen is a medium density screen; false otherwise
   */
  public static boolean isMediumDensity() {
    return ScreenUtils.isMediumDensity();
  }

  /**
   * Gets whether the runtime treats the screen as medium
   *
   * @return true if the runtime treats the screen as medium; false otherwise
   */
  public static boolean isMediumScreen() {
    return ScreenUtils.isMediumScreen();
  }

  /**
   * Get whether the device's orientation is currently locked
   *
   * @return true if the device's orientation is currently locked; false
   *         otherwise
   */
  public static boolean isOrientationLocked() {
    return Platform.getAppContext().isOrientationLocked();
  }

  /**
   * Gets whether the runtime treats the screen as small
   *
   * @return true if the runtime treats the screen as small; false otherwise
   */
  public static boolean isSmallScreen() {
    return ScreenUtils.isSmallScreen();
  }

  /**
   * Checks if the screen is wider that is is tall
   *
   * @return true if it will be wider; false otherwise
   */
  public static boolean isWider() {
    return ScreenUtils.isWider();
  }

  /**
   * Checks if the screen will be wider that is is tall for the specified
   * configuration. Meant to be use during pending orientation changes to check
   * the pending orientation
   *
   * @param configuration
   *          the configuration
   * @return true if it will be wider; false otherwise
   */
  public static boolean isWiderForConfiguration(Object configuration) {
    if (configuration == null) {
      return ScreenUtils.isWider();
    }

    return PlatformHelper.isLandscapeOrientation(configuration);
  }
}
