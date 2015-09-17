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

import java.awt.Cursor;

import java.util.HashMap;
import java.util.Locale;

public class UICursor {
  final String                    name;
  final Cursor                    cursor;
  static HashMap<String, Integer> cursorMap = new HashMap<String, Integer>();

  static {
    cursorMap.put("row-resize", Cursor.S_RESIZE_CURSOR);
    cursorMap.put("col-resize", Cursor.E_RESIZE_CURSOR);
    cursorMap.put("nw-resize", Cursor.NW_RESIZE_CURSOR);
    cursorMap.put("ne-resize", Cursor.NE_RESIZE_CURSOR);
    cursorMap.put("sw-resize", Cursor.SW_RESIZE_CURSOR);
    cursorMap.put("se-resize", Cursor.SE_RESIZE_CURSOR);
    cursorMap.put("n-resize", Cursor.N_RESIZE_CURSOR);
    cursorMap.put("e-resize", Cursor.E_RESIZE_CURSOR);
    cursorMap.put("s-resize", Cursor.S_RESIZE_CURSOR);
    cursorMap.put("w-resize", Cursor.W_RESIZE_CURSOR);
    cursorMap.put("pointer", Cursor.DEFAULT_CURSOR);
    cursorMap.put("hand", Cursor.HAND_CURSOR);
    cursorMap.put("wait", Cursor.WAIT_CURSOR);
    cursorMap.put("progress", Cursor.WAIT_CURSOR);
    cursorMap.put("crosshair", Cursor.E_RESIZE_CURSOR);
    cursorMap.put("move", Cursor.MOVE_CURSOR);
    cursorMap.put("text", Cursor.TEXT_CURSOR);
  }

  public UICursor(String name, Cursor cursor) {
    this.name   = name;
    this.cursor = cursor;
  }

  public Cursor getCursor() {
    return cursor;
  }

  public static UICursor getCursor(String name) {
    name = name.toLowerCase(Locale.US);

    Cursor c = null;

    try {
      Integer n = cursorMap.get(name);

      if (n != null) {
        c = Cursor.getPredefinedCursor(n);
      } else {
        c = Cursor.getSystemCustomCursor(name);
      }
    } catch(Exception ignore) {}

    return (c == null)
           ? null
           : new UICursor(name, c);
  }

  public String getName() {
    return name;
  }
}
