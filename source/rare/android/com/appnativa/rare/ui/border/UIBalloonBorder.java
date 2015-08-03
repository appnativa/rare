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

package com.appnativa.rare.ui.border;

import com.appnativa.rare.ui.UIColor;

/**
 *
 *  @author Don DeCoteau
 */
public class UIBalloonBorder extends aUIBalloonBorder {
  public UIBalloonBorder(UIColor color) {
    super(color);
  }

  public UIBalloonBorder(UIColor color, float thickness) {
    super(color, thickness);
  }

  public UIBalloonBorder(UIColor color, float thickness, float arc) {
    super(color, thickness, arc);
  }

  public UIBalloonBorder(UIColor color, float thickness, float arc, PeakLocation pl) {
    super(color, thickness, arc, pl);
  }

  public UIBalloonBorder(UIColor color, float thickness, float arc, PeakLocation pl, float peakSize) {
    super(color, thickness, arc, pl, peakSize);
  }
}
