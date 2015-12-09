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

import com.appnativa.rare.ui.RenderableDataItem.HorizontalAlign;
import com.appnativa.rare.ui.RenderableDataItem.IconPosition;
import com.appnativa.rare.ui.RenderableDataItem.Orientation;
import com.appnativa.rare.ui.RenderableDataItem.VerticalAlign;
import com.appnativa.rare.ui.iPaintedButton.ButtonState;

public interface iActionComponent extends iPlatformComponent, iActionable, iChangeable {
  void fireActionEvent();

  void setAction(UIAction a);
  void setAutoAdjustSize(boolean adjustSize);
  void setAlignment(HorizontalAlign hal, VerticalAlign val);

  void setDisabledIcon(iPlatformIcon icon);

  void setDisabledSelectedIcon(iPlatformIcon icon);

  void setIcon(iPlatformIcon icon);

  /**
   * @param iconGap the iconGap to set
   */
  void setIconGap(int iconGap);

  /**
   * @param iconPosition the iconPosition to set
   */
  void setIconPosition(IconPosition iconPosition);

  void setMnemonic(char mn);

  /**
   * @param pressedIcon the pressedIcon to set
   */
  void setPressedIcon(iPlatformIcon pressedIcon);

  @Override
  void setSelected(boolean selected);

  /**
   * @param selectedIcon the selectedIcon to set
   */
  void setSelectedIcon(iPlatformIcon selectedIcon);

  void setText(CharSequence buttonText);

  void setToolTipText(CharSequence text);

  void setWordWrap(boolean wrap);

  UIAction getAction();

  iPlatformIcon getDisabledIcon();

  iPlatformIcon getIcon();

  /**
   * @return the iconGap
   */
  int getIconGap();

  /**
   *   @return the iconPosition
   */
  IconPosition getIconPosition();

  /**
   * @return the pressedIcon
   */
  iPlatformIcon getPressedIcon();

  /**
   * @return the selectedIcon
   */
  iPlatformIcon getSelectedIcon();

  CharSequence getText();

  @Override
  boolean isSelected();

  boolean isWordWrap();

  void doClick();

  void setMargin(UIInsets insets);

  void setMargin(float top, float right, float bottom, float left);

  void setOrientation(Orientation orientation);

  ButtonState getButtonState();

  /**
   * Returns the margin for the component
   * @return the margin for the component
   */
  UIInsets getMargin();
}
