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

package com.appnativa.rare.platform.android.ui.util;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;

import android.util.StateSet;

import com.appnativa.rare.ui.painter.PainterHolder;

public class PainterHolderDrawable extends Drawable {
  private PainterHolder painterHolder;

  public PainterHolderDrawable(PainterHolder ph) {
    this.painterHolder = ph;
  }

  public void draw(Canvas canvas) {}

  public void setAlpha(int alpha) {}

  public void setColorFilter(ColorFilter cf) {}

  public int getOpacity() {
    return 0;
  }

  public boolean isStateful() {
    return true;
  }

  protected boolean onStateChange(int[] state) {
    if (state == null) {
      return false;
    }

    boolean       changed = false;
    PainterHolder ph      = painterHolder;

    if (ph != null) {
      if (StateSet.stateSetMatches(PainterUtils.STATE_DISABLED, state)
          || StateSet.stateSetMatches(PainterUtils.STATE_ENABLED, state)) {
        changed = true;
      } else {
        if ((ph.pressedIcon != null) || (ph.pressedPainter != null)) {
          changed = StateSet.stateSetMatches(PainterUtils.STATE_PRESSED, state)
                    || StateSet.stateSetMatches(PainterUtils.STATE_UNPRESSED, state);
        }

        if (!changed && ((ph.selectedIcon != null) || (ph.selectedPainter != null))) {
          changed = StateSet.stateSetMatches(PainterUtils.STATE_SELECTED, state)
                    || StateSet.stateSetMatches(PainterUtils.STATE_DESELECTED, state);
        }
      }

      if (changed) {
        invalidateSelf();
      }

      setState(state);
    }

    return changed;
  }

  public void dispose() {
    painterHolder = null;
  }

  public PainterHolder getPainterHolder() {
    return painterHolder;
  }

  public void setPainterHolder(PainterHolder painterHolder) {
    this.painterHolder = painterHolder;
  }
}
