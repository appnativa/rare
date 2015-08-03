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

public class SimpleIconStateList {
  private iPlatformIcon defaultIcon;
  private iPlatformIcon disabledIcon;
  private iPlatformIcon selectedIcon;
  private iPlatformIcon selectedDisabledIcon;

  public SimpleIconStateList() {}

  public SimpleIconStateList(iPlatformIcon defaultIcon, iPlatformIcon disabledIcon) {
    this.defaultIcon  = defaultIcon;
    this.disabledIcon = disabledIcon;
  }

  public SimpleIconStateList(iPlatformIcon defaultIcon, iPlatformIcon disabledIcon, iPlatformIcon selectedIcon,
                             iPlatformIcon selectedDisabledIcon) {
    this.defaultIcon          = defaultIcon;
    this.disabledIcon         = disabledIcon;
    this.selectedIcon         = selectedIcon;
    this.selectedDisabledIcon = selectedDisabledIcon;
  }

  public iPlatformIcon getDefaultIcon() {
    return defaultIcon;
  }

  public iPlatformIcon getDisabledIcon() {
    return (disabledIcon == null)
           ? defaultIcon
           : disabledIcon;
  }

  public iPlatformIcon getSelectedIcon() {
    return (selectedIcon == null)
           ? defaultIcon
           : selectedIcon;
  }

  public iPlatformIcon getSelectedDisabledIcon() {
    return (selectedDisabledIcon == null)
           ? getDisabledIcon()
           : selectedDisabledIcon;
  }

  public void setSelectedDisabledIcon(iPlatformIcon selectedDisabledIcon) {
    this.selectedDisabledIcon = selectedDisabledIcon;
  }

  public void setSelectedIcon(iPlatformIcon selectedIcon) {
    this.selectedIcon = selectedIcon;
  }

  public void setDisabledIcon(iPlatformIcon disabledIcon) {
    this.disabledIcon = disabledIcon;
  }

  public void setDefaultIcon(iPlatformIcon defaultIcon) {
    this.defaultIcon = defaultIcon;
  }
}
