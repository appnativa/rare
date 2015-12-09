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

import com.appnativa.rare.platform.PlatformHelper;

public class UISound {
  Object platformSound;

  public UISound(Object platformSound) {
    this.platformSound = platformSound;
  }

  public Object getPlatformSound() {
    return platformSound;
  }

  public void play() {
    if(platformSound!=null) {
      PlatformHelper.playSound(platformSound);
    }
  }

  public void pause() {
    if(platformSound!=null) {
      PlatformHelper.pauseSound(platformSound);
    }
  }

  public void resume() {
    if(platformSound!=null) {
      PlatformHelper.resumeSound(platformSound);
    }
  }
  
  public void dispose() {
    if(platformSound!=null) {
      PlatformHelper.disposeOfSound(platformSound);
    }
    platformSound=null;
  }

  public void setVolume(int percent) {
    if(platformSound!=null) {
      platformSound = PlatformHelper.setVolume(platformSound, percent);
    }
  }
}
