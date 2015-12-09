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

package com.appnativa.rare.platform.swing.ui.view;

import java.awt.Color;
import java.awt.Rectangle;

import com.appnativa.rare.Platform;
import com.appnativa.rare.platform.swing.plaf.LabelExUI;
import com.appnativa.rare.ui.ColorUtils;

public class LabelRenderer extends LabelView {
  private boolean _opaque;

  public LabelRenderer() {
    super();

    if (Platform.getUIDefaults().getBoolean("Rare.LabelRenderer.useCustomEditorKit", true)) {
      setUI(new LabelExUI());
    }
  }

  public void callSuperInvalidate() {
    super.invalidate();
  }

  public void callSuperRepaint() {
    super.repaint();
  }

  public void callSuperRevalidate() {
    super.revalidate();
  }

  public void callSuperValidate() {
    super.validate();
  }

  @Override
  public final void firePropertyChange(String propertyName, boolean oldValue, boolean newValue) {}

  @Override
  public final void firePropertyChange(String propertyName, byte oldValue, byte newValue) {}

  @Override
  public final void firePropertyChange(String propertyName, char oldValue, char newValue) {}

  @Override
  public final void firePropertyChange(String propertyName, double oldValue, double newValue) {}

  @Override
  public final void firePropertyChange(String propertyName, float oldValue, float newValue) {}

  @Override
  public final void firePropertyChange(String propertyName, int oldValue, int newValue) {}

  @Override
  public final void firePropertyChange(String propertyName, long oldValue, long newValue) {}

  @Override
  public final void firePropertyChange(String propertyName, short oldValue, short newValue) {}

  @Override
  public final void invalidate() {}

  @Override
  public final void repaint() {}

  @Override
  public final void repaint(Rectangle r) {}

  @Override
  public final void repaint(long tm, int x, int y, int width, int height) {}

  @Override
  public final void revalidate() {}

  @Override
  public final void validate() {}

  @Override
  public void setOpaque(boolean opaque) {
    _opaque = opaque;
  }

  @Override
  public Color getBackground() {
    return (_opaque || (componentPainter != null) || isBackgroundSet())
           ? super.getBackground()
           : ColorUtils.TRANSPARENT_COLOR;
  }

  @Override
  public void setBackground(Color bg) {
    super.setBackground(bg);
    _opaque = bg != null;
  }

  @Override
  public boolean isOpaque() {
    return _opaque;
  }

  @Override
  protected void firePropertyChange(final String propertyName, final Object oldValue, final Object newValue) {
    if (oldValue == newValue) {
      return;
    }

    if ((propertyName == "text") || (propertyName == "labelFor") || (propertyName == "displayedMnemonic")
        || (((propertyName == "font") || (propertyName == "foreground")) && (oldValue != newValue))) {
      super.firePropertyChange(propertyName, oldValue, newValue);
    }
  }
}
