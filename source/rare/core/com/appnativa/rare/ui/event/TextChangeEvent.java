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

package com.appnativa.rare.ui.event;

public class TextChangeEvent extends ChangeEvent {
  private boolean      changed;
  private int          endIndex;
  private CharSequence replacementString;
  private int          startIndex;

  public TextChangeEvent(Object source) {
    super(source);
    changed = true;
  }

  public TextChangeEvent(Object source, int startIndex, int endIndex, CharSequence replacementString) {
    super(source);
    this.replacementString = replacementString;
    this.startIndex        = startIndex;
    this.endIndex          = endIndex;
  }

  public int getEndIndex() {
    return endIndex;
  }

  public CharSequence getReplacementString() {
    return replacementString;
  }

  public int getStartIndex() {
    return startIndex;
  }

  public boolean isChangedEvent() {
    return changed;
  }
}
