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

import com.appnativa.rare.platform.apple.ui.view.View;
import com.appnativa.rare.ui.event.KeyEvent;
import com.appnativa.rare.ui.event.MouseEvent;
import com.appnativa.rare.ui.painter.PaintBucket;
import com.appnativa.rare.widget.iWidget;

public interface iPlatformComponent extends iComponent {
  public iPlatformComponent copy();

  public UIColor getDisabledColor();

  @Override
  void dispatchEvent(KeyEvent keyEvent);

  @Override
  void dispatchEvent(MouseEvent mouseEvent);

  @Override
  void updateUI();

  @Override
  void setDisabledColor(UIColor color);

  void setLocked(boolean lock);

  @Override
  void setSelected(boolean selected);

  @Override
  void setWidget(iWidget w);

  UIInsets getFocusInsets(UIInsets insets);

  PaintBucket getFocusPaint(iPlatformGraphics g, PaintBucket defFocusPaint);

  @Override
  iParentComponent getParent();

  Object getProxy();

  View getView();

  @Override
  iWidget getWidget();

  boolean hasBeenFocused();

  boolean hasHadInteraction();

  boolean isLocked();

  boolean isMouseOver();

  boolean isPressed();

  @Override
  boolean isSelectable();

  @Override
  boolean isSelected();

  public UIImage capture();
}
