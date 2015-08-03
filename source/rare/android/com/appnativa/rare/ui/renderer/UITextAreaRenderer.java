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

package com.appnativa.rare.ui.renderer;

import android.content.Context;

import android.widget.TextView;

public class UITextAreaRenderer extends UILabelRenderer {
  protected int     columnWidth;
  protected boolean linefeedWrap;

  public UITextAreaRenderer(Context context) {
    super(context);
    setWordWrap(true);
  }

  public void setColumnWidth(int columnWidth) {
    this.columnWidth = columnWidth;
    ((TextView) view).setEms(columnWidth);
  }

  /**
   * Sets linefeed wrapping
   *
   * @param linefeedWrap true to wrap on line feeds false otherwise
   */
  public void setLinefeedWrap(boolean linefeedWrap) {
    this.linefeedWrap = linefeedWrap;
  }
}
