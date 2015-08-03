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

import com.appnativa.rare.ui.event.iPopupMenuListener;
import com.appnativa.rare.ui.painter.iPlatformComponentPainter;
import com.appnativa.rare.viewer.iViewer;

import java.util.Map;

public abstract class aPopup implements iPopup {
  public aPopup() {}

  @Override
  public void addPopupMenuListener(iPopupMenuListener l) {}

  public void cancelPopup() {}

  @Override
  public void dispose() {}

  @Override
  public void hidePopup() {}

  @Override
  public void removePopupMenuListener(iPopupMenuListener l) {}

  @Override
  public void showPopup() {
    showPopup(null, 0, 0);
  }

  @Override
  public void showPopup(float x, float y) {
    showPopup(null, x, y);
  }

  @Override
  public abstract void showPopup(iPlatformComponent ref, float x, float y);

  @Override
  public void setComponentPainter(iPlatformComponentPainter painter) {}

  @Override
  public void setContent(iPlatformComponent component) {}

  @Override
  public void setFocusable(boolean focusable) {}

  @Override
  public void setMovable(boolean moveble) {}

  @Override
  public void setOptions(Map<String, String> options) {}

  @Override
  public void setPopupLocation(float x, float y) {}

  @Override
  public void setPopupOwner(iPlatformComponent component) {}

  @Override
  public void setSize(float width, float heigth) {}

  @Override
  public void setTimeout(int timeout) {}

  @Override
  public void setTitle(String title) {}

  @Override
  public void setTransient(boolean istransient) {}

  @Override
  public iViewer setViewer(iViewer viewer) {
    return null;
  }

  @Override
  public boolean isShowing() {
    return false;
  }

  @Override
  public boolean isTransient() {
    return false;
  }
}
