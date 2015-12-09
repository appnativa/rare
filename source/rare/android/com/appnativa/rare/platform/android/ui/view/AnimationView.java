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

package com.appnativa.rare.platform.android.ui.view;

import android.content.Context;

import android.graphics.Canvas;

import com.appnativa.rare.ui.Component;
import com.appnativa.rare.ui.UIImage;
import com.appnativa.rare.ui.effects.iAnimationImageView;
import com.appnativa.rare.ui.iPlatformComponent;

public class AnimationView extends ViewGroupEx implements iAnimationImageView {
  Component       component;
  private UIImage image;

  public AnimationView(Context context) {
    super(context);
    nullLayout = true;
  }

  @Override
  public void dispose() {
    if (image != null) {
      image.dispose();
      image = null;
    }

    iPlatformComponent c = component;

    component = null;

    if (c != null) {
      c.dispose();
    }
  }

  @Override
  public void setImage(UIImage image) {
    this.image = image;
  }

  @Override
  public iPlatformComponent getComponent() {
    if (component == null) {
      component = new Component(this);
    }

    return component;
  }

  @Override
  public UIImage getImage() {
    return image;
  }

  @Override
  protected void onDraw(Canvas canvas) {
    if (image != null) {
      canvas.drawBitmap(image.getBitmap(), 0, 0, null);
    }
  }
}
