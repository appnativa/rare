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

package com.appnativa.rare.ui.painter;

import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.iPlatformGraphics;

public abstract class aUICompoundPainter extends aUIPlatformPainter {
  protected iPlatformPainter firstPainter;
  protected iPlatformPainter secondPainter;

  public aUICompoundPainter(iPlatformPainter firstPainter, iPlatformPainter secondPainter) {
    setFirstPainter(firstPainter);
    setSecondPainter(secondPainter);
  }

  @Override
  public Object clone() {
    aUICompoundPainter cp = (aUICompoundPainter) super.clone();

    if (cp.firstPainter != null) {
      cp.firstPainter = (iPlatformPainter) ((aUIPainter) cp.firstPainter).clone();
      cp.firstPainter.reference();
    }

    if (cp.secondPainter != null) {
      cp.secondPainter = (iPlatformPainter) ((aUIPainter) cp.firstPainter).clone();
      cp.secondPainter.reference();
    }

    return cp;
  }

  @Override
  public void paint(iPlatformGraphics g, float x, float y, float width, float height, int orientation) {
    if ((firstPainter != null) && firstPainter.isEnabled()) {
      firstPainter.paint(g, x, y, width, height, orientation);
    }

    if ((secondPainter != null) && secondPainter.isEnabled()) {
      secondPainter.paint(g, x, y, width, height, orientation);
    }
  }

  /**
   * Sets the first painter
   *
   * @param p
   *          the painter
   */
  public void setFirstPainter(iPlatformPainter p) {
    if (p != firstPainter) {
      iPlatformPainter op = firstPainter;

      this.firstPainter = (p == null)
                          ? null
                          : p.reference();
      modCount++;

      if (op != null) {
        op.dispose();
      }
    }
  }

  /**
   * Sets the second painter
   *
   * @param p
   *          the painter
   */
  public void setSecondPainter(iPlatformPainter p) {
    if (p != secondPainter) {
      iPlatformPainter op = secondPainter;

      this.secondPainter = (p == null)
                           ? null
                           : p.reference();
      modCount++;

      if (op != null) {
        op.dispose();
      }
    }
  }

  @Override
  public UIColor getBackgroundColor() {
    if (firstPainter instanceof iBackgroundPainter) {
      if (((iBackgroundPainter) firstPainter).getBackgroundColor() != null) {
        return ((iBackgroundPainter) firstPainter).getBackgroundColor();
      }
    }

    if (secondPainter instanceof iBackgroundPainter) {
      return ((iBackgroundPainter) secondPainter).getBackgroundColor();
    }

    return null;
  }

  /**
   * Gets the first painter
   *
   * @return the painter
   */
  public iPlatformPainter getFirstPainter() {
    return firstPainter;
  }

  /**
   * Gets the second painter
   *
   * @return the painter
   */
  public iPlatformPainter getSecondPainter() {
    return secondPainter;
  }

  @Override
  public boolean isEnabled() {
    if (!super.isEnabled()) {
      return false;
    }

    if ((firstPainter != null) && firstPainter.isEnabled()) {
      return true;
    }

    if ((secondPainter != null) && secondPainter.isEnabled()) {
      return true;
    }

    return false;
  }

  @Override
  public boolean isSingleColorPainter() {
    if (firstPainter instanceof iBackgroundPainter) {
      if (((iBackgroundPainter) firstPainter).isSingleColorPainter()) {
        return true;
      }
    }

    if (secondPainter instanceof iBackgroundPainter) {
      return ((iBackgroundPainter) secondPainter).isSingleColorPainter();
    }

    return true;
  }

  @Override
  protected void disposeEx() {
    if (firstPainter != null) {
      firstPainter.dispose();
    }

    firstPainter = null;

    if (secondPainter != null) {
      secondPainter.dispose();
    }

    secondPainter = null;
    super.disposeEx();
  }
}
