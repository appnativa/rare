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
import com.appnativa.rare.spot.Margin;
import com.appnativa.rare.spot.Widget;
import com.appnativa.rare.ui.border.UIEmptyBorder;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.spot.SPOTSet;

public class UIBorderHelper {
  public static final UIEmptyBorder EMPTY_BORDER                  = BorderUtils.EMPTY_BORDER;
  public static final UIEmptyBorder ONE_PIXEL_EMPTY_BORDER        = BorderUtils.ONE_POINT_EMPTY_BORDER;
  public static final UIEmptyBorder TWO_PIXEL_EMPTY_BORDER        = BorderUtils.TWO_POINT_EMPTY_BORDER;
  public static final UIEmptyBorder TWO_LEFT_PIXEL_EMPTY_BORDER   = BorderUtils.TWO_LEFT_POINT_EMPTY_BORDER;
  public static final UIEmptyBorder THREE_LEFT_PIXEL_EMPTY_BORDER = BorderUtils.THREE_LEFT_POINT_EMPTY_BORDER;
  public static final UIEmptyBorder ONE_TOP_PIXEL_EMPTY_BORDER    = BorderUtils.ONE_TOP_POINT_EMPTY_BORDER;
  public static final UIEmptyBorder ONE_LEFT_PIXEL_EMPTY_BORDER   = BorderUtils.ONE_LEFT_POINT_EMPTY_BORDER;
  public static final UIEmptyBorder ONE_BOTTOM_PIXEL_EMPTY_BORDER = BorderUtils.ONE_BOTTOM_POINT_EMPTY_BORDER;

  public static iPlatformBorder createBorder(SPOTSet borders) {
    return BorderUtils.createBorder(Platform.getContextRootViewer(), borders, null);
  }

  public static iPlatformBorder createBorder(String border) {
    if ((border == null) || (border.length() == 0)) {
      return null;
    }

    return BorderUtils.createBorder(Platform.getContextRootViewer(), border, null);
  }

  public static iPlatformBorder createBorder(SPOTSet borders, iPlatformBorder standard) {
    return BorderUtils.createBorder(Platform.getContextRootViewer(), borders, standard);
  }

  public static iPlatformBorder createEmptyBorder(Margin m) {
    return BorderUtils.createEmptyBorder(Platform.getContextRootViewer(), m);
  }

  public static UIColor findBorderColor(iPlatformBorder border) {
    return BorderUtils.findBorderColor(border);
  }

  public static iPlatformComponent setBorderType(iWidget context, iPlatformComponent comp, Widget cfg, boolean title,
          boolean margin) {
    return BorderUtils.setBorderType(context, comp, cfg, title, margin);
  }

  /**
   * Sets the component's border from the specified set of borders The specified
   * border/title combination may require the component to be embedded in
   * another component. If this is the case the new component is returned
   * otherwise the original component is returned.
   *
   * @param context
   *          the context
   * @param comp
   *          the component
   * @param borders
   *          the set of borders
   * @param title
   *          the component's title
   * @param tdisplay
   *          the title display option
   * @param margin
   *          the margin between the component and its border
   *
   * @return the passed in component or a new component that has the passed in
   *         component embedded in it
   */
  public static iPlatformComponent setBorderType(iWidget context, iPlatformComponent comp, SPOTSet borders,
          String title, int tdisplay, iPlatformBorder margin, boolean lockable) {
    return BorderUtils.setBorderType(context, comp, borders, title, tdisplay, margin, lockable);
  }

  public static Object getEmptyBorder() {
    return BorderUtils.EMPTY_BORDER;
  }
}
