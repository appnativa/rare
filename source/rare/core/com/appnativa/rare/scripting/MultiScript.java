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

package com.appnativa.rare.scripting;

import java.util.ArrayList;

/**
 *
 * @author Don DeCoteau
 */
public class MultiScript {
  private final ArrayList passedIncode = new ArrayList(2);
  private final ArrayList compiledCode = new ArrayList(2);
  String                  event;

  public MultiScript(String event) {
    this.event = event;
  }

  public void add(Object code) {
    passedIncode.add(code);
    compiledCode.add(code);
  }

  public void remove(Object code) {
    int           len    = passedIncode.size();
    final boolean string = code instanceof String;
    Object        o;

    for (int i = 0; i < len; i++) {
      o = passedIncode.get(i);

      if ((o == code) || (string && (o instanceof String) && o.equals(code))) {
        passedIncode.remove(i);
        compiledCode.remove(i);

        break;
      }
    }
  }

  @Override
  public String toString() {
    return event;
  }

  public int size() {
    return passedIncode.size();
  }

  public String getEvent() {
    return event;
  }

  public ArrayList getCompiledCode() {
    return compiledCode;
  }

  public ArrayList getPassedIncode() {
    return passedIncode;
  }
}
