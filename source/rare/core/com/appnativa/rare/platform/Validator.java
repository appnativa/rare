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

package com.appnativa.rare.platform;

import com.appnativa.util.CharArray;
import com.appnativa.util.CharScanner;
import com.appnativa.util.OrderedProperties.iValidator;

import java.util.HashMap;
import java.util.Map;

class Validator implements iValidator {
  public final CharScanner         scanner = new CharScanner();
  public final Map<String, Object> options = new HashMap<String, Object>();
  final aRare                      rare;

  public Validator(aRare rare) {
    this.rare = rare;
  }

  public void clear() {
    options.clear();
    scanner.clear();
  }

  @Override
  public boolean isValid(CharArray ca, int start) {
    if (ca == null) {
      return true;
    }

    final CharScanner         sc  = scanner;
    final Map<String, Object> map = options;

    sc.reset(ca.A, start, ca._length - start, false);

    if (sc.getLastChar() == ']') {
      sc.chop(1);
    }

    if (sc.getCurrentChar() == '[') {
      sc.consume(1);
    }

    sc.trim();

    if (sc.getLength() == 0) {
      return true;
    }

    map.clear();
    CharScanner.parseOptionStringEx(sc, options, ',', true);

    String s = (String) map.get("os");

    if ((s == null) || (s.length() == 0)) {
      return true;
    }

    return rare.okForOS(s, sc);
  }
}
