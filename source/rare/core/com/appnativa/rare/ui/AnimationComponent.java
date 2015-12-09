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

import com.appnativa.rare.ui.effects.iAnimationImageView;

public class AnimationComponent extends XPContainer {
  UIDimension                size = new UIDimension();
  boolean                    horizontal;
  iAnimationImageView        imageView;
  protected boolean          singleAnimator;
  protected boolean          stacked;
  private boolean            forward = true;
  private iPlatformComponent inComponent;
  private UIDimension        minSize;
  private iPlatformComponent outComponent;
  private boolean            useBoundsForPreferredSize;
  private boolean            useIncommingPreferredSize;

  public AnimationComponent(iAnimationImageView view) {
    super(view);
    imageView = view;
  }

  public void layoutContainer(float width, float height) {
    setInitialLayout(size.width, size.height);
  }

  @Override
  public void removeAll() {
    super.removeAll();
    inComponent  = null;
    outComponent = null;
  }

  public void setComponent(iPlatformComponent inComponent, float width, float height) {
    removeAll();
    stacked          = true;
    this.inComponent = inComponent;
    size.setSize(width, height);

    if (minSize != null) {
      minSize.setSize(0, 0);
    }

    add(inComponent);
    inComponent.setBounds(0, 0, width, height);
  }

  public void setImage(UIImage image) {
    imageView.setImage(image);

    if (minSize != null) {
      minSize.setSize(0, 0);
    }

    if (image != null) {
      size.setSize(image.getWidth(), image.getHeight());
    }
  }

  public void setMinimumSize(UIDimension minimumSize) {
    if (!useBoundsForPreferredSize) {
      if (minSize == null) {
        minSize = new UIDimension();
      }

      minSize.setSize(minimumSize);
    }
  }

  public void setSideBySideComponents(boolean forward, boolean horizontal, iPlatformComponent outComponent,
          iPlatformComponent inComponent, int width, int height, boolean singleAnimator) {
    removeAll();
    stacked             = false;
    this.forward        = forward;
    this.singleAnimator = singleAnimator;
    this.horizontal     = horizontal;
    this.outComponent   = outComponent;
    this.inComponent    = inComponent;
    size.setSize(width, height);

    if (inComponent != null) {
      add(inComponent);
    }

    if (outComponent != null) {
      add(outComponent);
    }

    setInitialLayout(width, height);
  }

  public void setStackedComponents(boolean forward, iPlatformComponent outComponent, iPlatformComponent inComponent,
                                   int width, int height) {
    removeAll();
    stacked           = true;
    this.forward      = forward;
    this.outComponent = outComponent;
    this.inComponent  = inComponent;
    size.setSize(width, height);

    if (inComponent != null) {
      add(inComponent);
    }

    if (outComponent != null) {
      add(outComponent);
    }

    if (forward) {
      if (inComponent != null) {
        inComponent.bringToFront();
      }
    } else {
      if (outComponent != null) {
        outComponent.bringToFront();
      }
    }

    setInitialLayout(width, height);
  }

  public void setUseBoundsForPreferredSize(boolean useBoundsForPreferredSize) {
    this.useBoundsForPreferredSize = useBoundsForPreferredSize;
  }

  public UIImage getImage() {
    return imageView.getImage();
  }

  public iPlatformComponent getInComponentAnClear() {
    iPlatformComponent c = inComponent;

    removeAll();

    return c;
  }

  public boolean isUseBoundsForPreferredSize() {
    return useBoundsForPreferredSize;
  }

  protected void setInitialLayout(float width, float height) {
    if (minSize != null) {
      minSize.setSize(0, 0);
    }

    if (stacked) {
      if (inComponent != null) {
        inComponent.setBounds(0, 0, width, height);
      }

      if (outComponent != null) {
        outComponent.setBounds(0, 0, width, height);
      }
    } else {
      UIDimension d = null;

      if (isUseIncommingPreferredSize() && (inComponent != null)) {
        d        = inComponent.getPreferredSize();
        d.width  = Math.min(d.width, width);
        d.height = Math.min(d.width, height);
      }

      if (horizontal) {
        if (outComponent != null) {
          if (forward) {
            outComponent.setBounds(0, 0, width, height);
          } else {
            outComponent.setBounds(width, 0, width, height);
          }
        }

        if (inComponent != null) {
          if (forward) {
            inComponent.setBounds(width, 0, (d == null)
                                            ? width
                                            : d.width, height);
          } else {
            inComponent.setBounds((d == null)
                                  ? 0
                                  : width - d.width, 0, (d == null)
                    ? width
                    : d.width, height);
          }
        }
      } else {
        if (outComponent != null) {
          if (forward) {
            outComponent.setBounds(0, 0, width, height);
          } else {
            outComponent.setBounds(0, height, width, height);
          }
        }

        if (inComponent != null) {
          if (forward) {
            inComponent.setBounds(0, height, width, (d == null)
                    ? height
                    : d.height);
          } else {
            inComponent.setBounds(0, (d == null)
                                     ? 0
                                     : height - d.height, width, (d == null)
                    ? height
                    : d.height);
          }
        }
      }
    }
  }

  @Override
  protected void getMinimumSizeEx(UIDimension size, float maxWidth) {
    if (!useBoundsForPreferredSize && (minSize != null)) {
      size.setSize(minSize);
    } else {
      size.width  = 0;
      size.height = 0;
    }
  }

  @Override
  protected void getPreferredSizeEx(UIDimension size, float maxWidth) {
    if (useBoundsForPreferredSize) {
      getSize(size);
    } else {
      size.setSize(this.size);
    }
  }

  public boolean isUseIncommingPreferredSize() {
    return useIncommingPreferredSize;
  }

  public void setUseIncommingPreferredSize(boolean useIncommingPreferredSize) {
    this.useIncommingPreferredSize = useIncommingPreferredSize;
  }
}
