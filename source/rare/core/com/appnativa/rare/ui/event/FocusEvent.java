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

import com.appnativa.rare.ui.iPlatformComponent;

import com.google.j2objc.annotations.Weak;

/**
 *
 * @author Don DeCoteau
 */
public class FocusEvent extends EventBase {
  @Weak
  private iPlatformComponent opposite;
  private boolean            temporary;
  private boolean            gained;

  public FocusEvent(iPlatformComponent source, boolean gained) {
    this(source, gained, false);
  }

  public FocusEvent(iPlatformComponent source, boolean gained, boolean temporary) {
    this(source, gained, temporary, null);
  }

  public FocusEvent(iPlatformComponent source, boolean gained, boolean temporary, iPlatformComponent opposite) {
    super(source);
    this.temporary = temporary;
    this.opposite  = opposite;
  }

  public iPlatformComponent getOppositeComponent() {
    return opposite;
  }

  public boolean isTemporary() {
    return temporary;
  }

  public boolean wasFocusLost() {
    return !gained;
  }

  public boolean wasFocusGained() {
    return gained;
  }
}
