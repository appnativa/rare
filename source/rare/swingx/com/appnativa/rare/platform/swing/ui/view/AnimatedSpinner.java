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

import com.appnativa.rare.ui.UISpriteIcon;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JComponent;

public class AnimatedSpinner extends JComponent {
  UISpriteIcon spinnerIcon;

  public AnimatedSpinner(UISpriteIcon icon) {
    spinnerIcon = icon;
  }

  @Override
  public Dimension getMinimumSize() {
    return new Dimension(spinnerIcon.getIconWidth(), spinnerIcon.getIconHeight());
  }

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(spinnerIcon.getIconWidth(), spinnerIcon.getIconHeight());
  }

  @Override
  protected void paintComponent(Graphics g) {
    spinnerIcon.paintIcon(this, g, (getWidth() - spinnerIcon.getIconWidth()),
                          (getHeight() - spinnerIcon.getIconHeight()) / 2);
  }

  public UISpriteIcon getSpinner() {
    return spinnerIcon;
  }
}
