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

import com.appnativa.rare.Platform;
import com.appnativa.rare.platform.PlatformHelper;
import com.appnativa.rare.ui.effects.ValueRangeAnimator;
import com.appnativa.rare.ui.effects.iAnimatorListener;
import com.appnativa.rare.ui.effects.iAnimatorValueListener;
import com.appnativa.rare.ui.effects.iPlatformAnimator;
import com.appnativa.rare.ui.event.ChangeEvent;
import com.appnativa.rare.ui.listener.iViewListener;

import com.google.j2objc.annotations.Weak;

/**
 * This class represents an icon that animates a series of icons or an image
 * that represents a series of sprites
 *
 * @author Don DeCoteau
 *
 */
public class UISpriteIcon extends aPlatformIcon
        implements iAnimatorListener, iAnimatorValueListener, iViewListener, Cloneable {
  protected iPlatformIcon[]    icons;
  protected ValueRangeAnimator animator;
  protected boolean            reverse;
  protected int                duration;
  protected int                position;
  protected int                iconWidth;
  protected int                iconHeight;
  protected int                iconCount;
  @Weak
  protected iPlatformComponent component;
  protected UIImage            sprite;
  protected UIRectangle        srcRect;
  protected UIRectangle        destRect;
  protected boolean            horizontal;
  protected boolean            frozen;

  /**
   * Creates a new instance that has a 2 second duration per cycle
   *
   * @param sprite
   *          the strip strip (assumed to be horizontal)
   */
  public UISpriteIcon(UIImage sprite) {
    this(sprite, true, sprite.getHeight(), 2000, false, true);
  }

  /**
   * Disposes of the icon
   */
  public void dispose() {
    if (animator != null) {
      animator.removeListener(this);
      animator.removeValueListener(this);
      animator.stop();
    }

    iPlatformComponent c = component;

    if ((c != null) &&!c.isDisposed()) {
      c.removeViewListener(this);
    }

    component = null;
    sprite    = null;
    icons     = null;
  }

  /**
   *
   * Creates a new instance
   *
   * @param sprite
   *          the strip strip
   * @param horizontal
   *          true for a horizontal strip; false for a vertical
   */
  public UISpriteIcon(UIImage sprite, boolean horizontal) {
    this(sprite, horizontal, horizontal
                             ? sprite.getHeight()
                             : sprite.getWidth(), 200, false, true);
  }

  /**
   *
   * Creates a new instance
   *
   * @param sprite
   *          the strip strip
   * @param horizontal
   *          true for a horizontal strip; false for a vertical
   * @param size
   *          the size of each sprite
   * @param duration
   *          the duration per cycle (in milliseconds)
   * @param reverse
   *          true of the sprites should reverse their order at the end of each
   *          cycle; false for start from the beginning
   * @param adjustForDensity
   *          true of have the sprite adjust the passed in information for
   *          screen density; false to use as specified
   */
  public UISpriteIcon(UIImage sprite, boolean horizontal, int size, int duration, boolean reverse,
                      boolean adjustForDensity) {
    this.horizontal = horizontal;
    this.sprite     = sprite;

    if (horizontal) {
      iconWidth  = size;
      iconHeight = sprite.getHeight();
      iconCount  = sprite.getWidth() / size;
      destRect   = new UIRectangle(0, 0, size, iconHeight);
    } else {
      iconHeight = size;
      iconWidth  = sprite.getWidth();
      iconCount  = sprite.getHeight() / size;
      destRect   = new UIRectangle(0, 0, iconWidth, size);
    }

    srcRect = new UIRectangle(destRect);

    if (adjustForDensity && Platform.isIOS()) {
      float density = ScreenUtils.getDensity();

      srcRect.width  *= density;
      srcRect.height *= density;
    }

    createAnimator(duration, reverse);
  }

  /**
   * Creates a new instance
   *
   * @param icons
   *          the icons to animate
   * @param duration
   *          the duration per cycle (in milliseconds)
   * @param reverse
   *          true of the sprites should reverse their order at the end of each
   *          cycle; false for start from the beginning
   */
  public UISpriteIcon(iPlatformIcon[] icons, int duration, boolean reverse) {
    this.icons = icons;

    for (iPlatformIcon ic : icons) {
      iconWidth  = Math.max(iconWidth, ic.getIconWidth());
      iconHeight = Math.max(iconHeight, ic.getIconHeight());
    }

    createAnimator(duration, reverse);
  }

  public ValueRangeAnimator getAnimator() {
    return animator;
  }

  /**
   * Creates the animator used to animate the sprite
   *
   * @param duration
   *          the duration of the animation in milliseconds
   * @param reverse
   *          true if at the end of the duration the aiumation should reverse;
   *          false to have the animation start from the beginning
   */
  protected void createAnimator(int duration, boolean reverse) {
    this.duration = duration;
    this.reverse  = reverse;
    animator      = new ValueRangeAnimator(0, iconCount);
    animator.setDuration(duration);
    animator.setAutoReverse(reverse);
    animator.setRepeatCount(-1);
  }

  /**
   * Returns a sprite icon for the default spinner
   *
   * @return a sprite icon for the default spinner
   */
  public static UISpriteIcon getDefaultSpinner() {
    UIImageIcon icon = (UIImageIcon) Platform.getResourceAsIcon("Rare.icon.spinner");

    return new UISpriteIcon(icon.getImage(), true, icon.getIconHeight(), 1000, false, true);
  }

  public Object clone() {
    UISpriteIcon icon;

    try {
      icon           = (UISpriteIcon) super.clone();
      icon.component = null;
      icon.createAnimator(duration, reverse);

      return icon;
    } catch(CloneNotSupportedException e) {
      throw new InternalError();
    }
  }

  /**
   * Gets the sprite's owner
   *
   * @return the owner;
   */
  public iPlatformComponent getOwner() {
    return component;
  }

  /**
   * Sets the sprite's owner
   *
   * @param comp
   *          the owner
   */
  public void setOwner(iPlatformComponent comp) {
    if (this.component != comp) {
      if (this.component != null) {
        iPlatformComponent c = component;

        component = null;    // set so that the animation wont automatically
        // restart if the component is still showing.
        c.removeViewListener(this);
        this.animator.stop();
      }

      this.component = comp;

      if (comp != null) {
        animator.addListener(this);
        animator.addValueListener(this);
        comp.addViewListener(this);

        if (comp.isShowing()) {
          this.animator.start();
        }
      } else {
        //we add add remove ourselves as a listener to prevent circular references problems in non GC platforms
        animator.removeListener(this);
        animator.removeValueListener(this);
      }
    }
  }

  @Override
  public int getIconHeight() {
    return iconHeight;
  }

  @Override
  public int getIconWidth() {
    return iconWidth;
  }

  @Override
  public iPlatformIcon getDisabledVersion() {
    int op = position;

    position = iconCount - 1;

    iPlatformIcon ic = PlatformHelper.createDisabledIcon(this);

    position = op;

    return ic;
  }

  @Override
  public void paint(iPlatformGraphics g, float x, float y, float width, float height) {
    if (sprite != null) {
      destRect.x = x;
      destRect.y = y;

      if (horizontal) {
        srcRect.x = position * srcRect.width;
      } else {
        srcRect.y = position * srcRect.height;
      }

      g.drawImage(sprite, srcRect, destRect, null, null);
    } else if (icons != null) {
      iPlatformIcon ic = icons[position];

      x += (iconWidth - ic.getIconWidth()) / 2;
      y += (iconHeight - ic.getIconHeight()) / 2;
      ic.paint(g, x, y, iconWidth, iconHeight);
    }
  }

  @Override
  public void valueChanged(iPlatformAnimator animator, float value) {
    position = (int) value;

    if (position >= iconCount) {
      position = iconCount;
    }

    iPlatformComponent c = component;

    if ((c != null) &&!c.isDisposed() && c.isShowing()) {
      if (c.isEnabled()) {
        c.repaint();
      }
    } else {
      this.animator.stop();
    }
  }

  @Override
  public void animationEnded(iPlatformAnimator animator) {
    if ((animator != null) && (component != null) && component.isShowing()) {
      if (!frozen) {
        if (!reverse) {
          position = 0;
        }

        this.animator.start();
      }
    }
  }

  @Override
  public void animationStarted(iPlatformAnimator animator) {}

  @Override
  public void viewResized(ChangeEvent e) {}

  @Override
  public void viewHidden(ChangeEvent e) {
    if (animator != null) {
      this.animator.stop();
    }
  }

  @Override
  public void viewShown(ChangeEvent e) {
    if (!frozen) {
      position = 0;

      if (animator != null) {
        this.animator.start();
      }
    }
  }

  @Override
  public boolean wantsResizeEvent() {
    return false;
  }

  /**
   * returns whether or not the sprite is frozen
   *
   * @return true if it is; false otherwise
   */
  public boolean isFrozen() {
    return frozen;
  }

  /**
   * Sets whether or not the sprite is frozen
   *
   * @param frozen
   *          true to free the sprite in is current position; false to unfreeze
   */
  public void setFrozen(boolean frozen) {
    this.frozen = frozen;

    if (animator != null) {
      if (frozen) {
        this.animator.stop();
      } else if (!animator.isRunning()) {
        if ((component != null) && component.isShowing()) {
          this.animator.start();
        }
      }
    }
  }
}
