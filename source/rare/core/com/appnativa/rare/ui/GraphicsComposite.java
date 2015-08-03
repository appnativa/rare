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

import com.appnativa.util.SNumber;

import java.util.Locale;

public class GraphicsComposite implements iComposite {
  public static final GraphicsComposite DEFAULT_COMPOSITE = new GraphicsComposite(iComposite.CompositeType.SRC_OVER, 1);
  protected float                       alpha             = 1;
  protected iComposite.CompositeType    compositeType;
  protected String                      name;
  private Object                        platformComposite;

  public GraphicsComposite(iComposite.CompositeType compositeType, float alpha) {
    this.compositeType = compositeType;

    if (alpha > 1) {
      alpha = alpha / 255;
    }

    this.alpha = alpha;
  }

  public GraphicsComposite(String name, iComposite.CompositeType compositeType, float alpha) {
    if (alpha > 1) {
      alpha = alpha / 255;
    }

    this.compositeType = compositeType;
    this.alpha         = alpha;
    this.name          = name;
  }

  /*
   *  (non-Javadoc)
   * @see com.appnativa.rare.ui.iComposite#derive(float)
   */
  @Override
  public iComposite derive(float alpha) {
    return new GraphicsComposite(compositeType, alpha);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }

    if (obj instanceof GraphicsComposite) {
      GraphicsComposite gc = (GraphicsComposite) obj;

      if ((gc.compositeType != compositeType) ||!SNumber.isEqual(gc.alpha, alpha)) {
        return false;
      }

      if (gc.name == name) {
        return true;
      }

      return getName().equals(gc.getName());
    }

    return false;
  }

  @Override
  public int hashCode() {
    return compositeType.hashCode() + (int) (alpha * 255);
  }

  public void setPlatformComposite(Object platformComposite) {
    this.platformComposite = platformComposite;
  }

  /*
   *  (non-Javadoc)
   * @see com.appnativa.rare.ui.iComposite#getAlpha()
   */
  @Override
  public float getAlpha() {
    return alpha;
  }

  /*
   *  (non-Javadoc)
   * @see com.appnativa.rare.ui.iComposite#getCompositeType()
   */
  @Override
  public iComposite.CompositeType getCompositeType() {
    return compositeType;
  }

  /*
   *  (non-Javadoc)
   * @see com.appnativa.rare.ui.iComposite#getName()
   */
  @Override
  public String getName() {
    if (name == null) {
      name = compositeType.name().toLowerCase(Locale.US);
    }

    return name;
  }

  public Object getPlatformComposite() {
    return platformComposite;
  }
}
