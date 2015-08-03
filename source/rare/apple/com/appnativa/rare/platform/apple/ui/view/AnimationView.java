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

package com.appnativa.rare.platform.apple.ui.view;

import com.appnativa.rare.ui.Component;
import com.appnativa.rare.ui.Container;
import com.appnativa.rare.ui.UIImage;
import com.appnativa.rare.ui.effects.iAnimationImageView;

public class AnimationView extends ParentView implements iAnimationImageView {
  ImageView imageView;
  Component imageViewComponent;

  public AnimationView() {
    super(createAPView());
  }

  @Override
  protected void disposeEx() {
    UIImage img = getImage();

    super.disposeEx();
    imageView          = null;
    imageViewComponent = null;

    if (img != null) {
      img.dispose();
    }
  }

  @Override
  public void setImage(UIImage image) {
    if (image == null) {
      if (imageView != null) {
        imageView.setImage(null);
        imageViewComponent.removeFromParent();
      }

      return;
    }

    if (imageView == null) {
      imageView          = new ImageView();
      imageViewComponent = new Component(imageView);
    }

    if (component instanceof Container) {
      Container p = (Container) component;

      p.removeAll();
      p.add(imageViewComponent);
    }

    imageView.setImage(image);
    imageView.setBounds(0, 0, image.getWidth(), image.getHeight());
  }

  @Override
  public UIImage getImage() {
    return (imageView == null)
           ? null
           : imageView.getImage();
  }
}
