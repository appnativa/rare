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

package com.appnativa.rare.util;

import com.appnativa.rare.Platform;
import com.appnativa.rare.spot.EmptyText;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIColorHelper;
import com.appnativa.rare.ui.UIFont;
import com.appnativa.rare.widget.aWidget;
import com.appnativa.rare.widget.iWidget;

public class EmptyTextItem {
  public EmptyTextItem(iWidget context, EmptyText et) {
    if (et != null) {
      text = et.value.getValue();

      if (context != null) {
        context = Platform.getContextRootViewer();
      }

      if (et.getFont() != null) {
        font = ((aWidget) context).getFont(et.getFont());
      }

      if (et.fgColor.getValue() != null) {
        foreground = UIColorHelper.getColor(et.fgColor.getValue());
      }
    }
  }

  public String  text;
  public UIFont  font;
  public UIColor foreground;
}
