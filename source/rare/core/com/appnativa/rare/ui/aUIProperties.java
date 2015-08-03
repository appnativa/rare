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

import com.appnativa.rare.ui.painter.PaintBucket;
import com.appnativa.rare.ui.painter.UISimpleBackgroundPainter;
import com.appnativa.rare.ui.painter.iBackgroundPainter;
import com.appnativa.rare.ui.painter.iPlatformPainter;

import java.util.HashMap;
import java.util.Map;

public abstract class aUIProperties {
  protected Map map;

  public aUIProperties() {
    this(new HashMap());
  }

  public aUIProperties(Map map) {
    this.map = map;
  }

  public void clear() {
    map.clear();
  }

  public java.util.Set entrySet() {
    return map.entrySet();
  }

  public void put(String key, Object value) {
    map.put(key, value);
  }

  public void putAll(UIProperties defs) {
    map.putAll(defs.map);
  }

  public Object get(String key) {
    return map.get(key);
  }

  public iBackgroundPainter getBackgroundPainter(String key) {
    Object o = getPainter(key);

    if (o instanceof iBackgroundPainter) {
      return (iBackgroundPainter) o;
    }

    if (o instanceof UIColorShade) {
      iBackgroundPainter p = ((UIColorShade) o).getBackgroundPainter();

      if (p != null) {
        return p;
      }
    }

    return null;
  }

  public boolean getBoolean(String key, boolean def) {
    final Boolean b = (Boolean) map.get(key);

    return (b == null)
           ? def
           : b.booleanValue();
  }

  public iPlatformBorder getBorder(String key) {
    return (iPlatformBorder) map.get(key);
  }

  public UIColor getColor(String key) {
    return (UIColor) map.get(key);
  }

  public UIFont getFont(String key) {
    return (UIFont) map.get(key);
  }

  public iPlatformIcon getIcon(String key) {
    Object o = map.get(key);

    if (o instanceof iPlatformIcon) {
      return (iPlatformIcon) o;
    }

    return null;
  }

  public UIImage getImage(String key) {
    Object o = map.get(key);

    if (o instanceof UIImage) {
      return (UIImage) o;
    }

    if (o instanceof UIImageIcon) {
      return ((UIImageIcon) o).getUIImage();
    }

    return null;
  }

  public UIImageIcon getImageIcon(String key) {
    Object o = map.get(key);

    if (o instanceof UIImageIcon) {
      return (UIImageIcon) o;
    }

    if (o instanceof UIImage) {
      return new UIImageIcon((UIImage) o);
    }

    return null;
  }

  public UIInsets getInsets(String key) {
    Object o = map.get(key);

    return (o instanceof UIInsets)
           ? (UIInsets) o
           : null;
  }

  public int getInt(String key, int def) {
    Object o = map.get(key);

    if (o instanceof Integer) {
      return (Integer) o;
    }

    if (o instanceof String) {
      return Integer.valueOf((String) o);
    }

    return def;
  }

  public Integer getInteger(String key) {
    Object o = map.get(key);

    if (o instanceof Integer) {
      return (Integer) o;
    }

    if (o instanceof String) {
      int n = ScreenUtils.toPlatformPixelWidth((String) o, null, 100);

      return Integer.valueOf(n);
    }

    return null;
  }

  public Integer getPixels(String key) {
    Object o = map.get(key);

    if (o instanceof Integer) {
      float f = ((Integer) o).floatValue();

      f *= ScreenUtils.getPixelMultiplier();

      return Integer.valueOf((int) f);
    }

    if (o instanceof String) {
      int n = ScreenUtils.toPlatformPixelWidth((String) o, null, 100);

      return Integer.valueOf(n);
    }

    return null;
  }

  public float getFloat(String key, float def) {
    Object o = map.get(key);

    if (o instanceof Float) {
      return ((Float) o).intValue();
    }

    if (o instanceof Integer) {
      return ((Integer) o).floatValue();
    }

    if (o instanceof String) {
      return ScreenUtils.toPlatformPixelWidth((String) o, null, 100);
    }

    return def;
  }

  public PaintBucket getPaintBucket(String key) {
    Object o = map.get(key);

    if (o instanceof PaintBucket) {
      return (PaintBucket) o;
    }

    if (o instanceof iBackgroundPainter) {
      return new PaintBucket((iBackgroundPainter) o);
    }

    if (o instanceof UIColor) {
      return new PaintBucket(null, (UIColor) o);
    }

    return null;
  }

  public iPlatformPainter getPainter(String key) {
    Object o = map.get(key);

    if (o instanceof iPlatformPainter) {
      return (iPlatformPainter) o;
    }

    if (o instanceof UIColorShade) {
      iPlatformPainter p = ((UIColorShade) o).getBackgroundPainter();

      if (p != null) {
        return p;
      }
    }

    if (o instanceof UIColor) {
      return new UISimpleBackgroundPainter((UIColor) o);
    }

    return null;
  }

  public String getString(String key) {
    return (String) map.get(key);
  }

  public boolean isEmpty() {
    return map.isEmpty();
  }
}
