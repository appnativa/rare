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

package com.appnativa.rare.ui.effects;

import com.appnativa.rare.Platform;
import com.appnativa.rare.platform.PlatformHelper;
import com.appnativa.rare.ui.AnimationComponent;
import com.appnativa.rare.ui.Location;
import com.appnativa.rare.ui.RenderType;
import com.appnativa.rare.ui.UIImage;
import com.appnativa.rare.ui.UIRectangle;
import com.appnativa.rare.ui.iPlatformComponent;

import com.google.j2objc.annotations.Weak;

import java.util.Locale;
import java.util.Map;

public class aSizeAnimation extends aPlatformAnimator implements iTransitionSupport {
  protected boolean            horizontal     = true;
  protected float              percent        = 1f;
  protected RenderType         diagonalAnchor = RenderType.CENTERED;
  protected boolean            diagonal       = true;
  protected AnimationComponent animationComponent;
  protected float              height;
  @Weak
  protected iPlatformComponent inComponent;
  protected float              originalSize;
  @Weak
  protected iPlatformComponent outComponent;
  protected float              width;
  protected BoundsChanger      boundsChanger;

  public aSizeAnimation() {
    this(true, false, RenderType.CENTERED);
  }

  public aSizeAnimation(boolean horizontal) {
    this(horizontal, false, RenderType.CENTERED);
  }

  public aSizeAnimation(boolean horizontal, boolean diagonal, RenderType anchor) {
    this.horizontal = horizontal;
    this.diagonal   = diagonal;
    setDuration(100);

    if (anchor != null) {
      diagonalAnchor = anchor;
    }
  }

  public static RenderType getComplementarySideAnchor(RenderType anchor, Location parentLocation) {
    // bottom is the default so we don't check for that
    switch(anchor) {
      case UPPER_RIGHT :
        switch(parentLocation) {
          case TOP :
            return RenderType.LOWER_RIGHT;

          case RIGHT :
            return RenderType.LOWER_LEFT;

          default :
            return anchor;
        }
      case LOWER_LEFT :
        switch(parentLocation) {
          case TOP :
            return RenderType.UPPER_LEFT;

          case RIGHT :
            return RenderType.UPPER_RIGHT;

          default :
            return anchor;
        }
      case UPPER_LEFT :
        switch(parentLocation) {
          case TOP :
            return RenderType.LOWER_LEFT;

          case LEFT :
            return RenderType.LOWER_RIGHT;

          default :
            return anchor;
        }
      case LOWER_RIGHT :
        switch(parentLocation) {
          case TOP :
            return RenderType.UPPER_LEFT;

          case LEFT :
            return RenderType.UPPER_RIGHT;

          default :
            return anchor;
        }
      case LOWER_MIDDLE :
        switch(parentLocation) {
          case TOP :
            return RenderType.UPPER_MIDDLE;

          case LEFT :
            return RenderType.LEFT_MIDDLE;

          case RIGHT :
            return RenderType.RIGHT_MIDDLE;

          default :
            return anchor;
        }
      case UPPER_MIDDLE :
        switch(parentLocation) {
          case TOP :
            return RenderType.LOWER_MIDDLE;

          case LEFT :
            return RenderType.RIGHT_MIDDLE;

          case RIGHT :
            return RenderType.LEFT_MIDDLE;

          default :
            return anchor;
        }
      case RIGHT_MIDDLE :
        switch(parentLocation) {
          case TOP :
            return RenderType.LEFT_MIDDLE;

          case LEFT :
            return RenderType.LOWER_MIDDLE;

          case RIGHT :
            return RenderType.UPPER_MIDDLE;

          default :
            return anchor;
        }
      case LEFT_MIDDLE :
        switch(parentLocation) {
          case TOP :
            return RenderType.RIGHT_MIDDLE;

          case LEFT :
            return RenderType.UPPER_MIDDLE;

          case RIGHT :
            return RenderType.LOWER_MIDDLE;

          default :
            return anchor;
        }
      default :
        return anchor;
    }
  }

  @Override
  public void animate(iPlatformComponent c, Object postAnimateAction) {
    setSizingValues(c);
    super.animate(c, postAnimateAction);
  }

  public void animateTo(iPlatformComponent c, UIRectangle to) {
    UIRectangle from = c.getBounds();

    boundsChanger = new BoundsChanger(from, to);
    width         = to.width;
    height        = to.height;
    super.animate(c, null);
  }

  @Override
  public void dispose() {
    if (outComponent != null) {
      setAnimatingProperty(outComponent, false);
    }

    if (inComponent != null) {
      setAnimatingProperty(inComponent, false);
    }

    if (animationComponent != null) {
      setAnimatingProperty(animationComponent, false);
      animationComponent.dispose();
      animationComponent = null;
    }

    outComponent = null;
    inComponent  = null;
    super.dispose();
  }

  @Override
  public void handleProperties(Map map) {
    super.handleProperties(map);

    if (map == null) {
      return;
    }

    Object o = map.get("horizontal");

    if (o instanceof Boolean) {
      setHorizontal(((Boolean) o).booleanValue());
    } else if (o instanceof String) {
      setHorizontal("true".equalsIgnoreCase((String) o));
    }

    o = map.get("diagonal");

    if (o instanceof Boolean) {
      diagonal = ((Boolean) o).booleanValue();
    } else if (o instanceof String) {
      diagonal = "true".equalsIgnoreCase((String) o);
    }

    o = map.get("diagonalAnchor");

    if (o != null) {
      setDiagonalAnchor(o);
    }
  }

  public void reset() {
    outComponent = null;
    inComponent  = null;
  }

  public void setDiagonal(boolean diagonal) {
    this.diagonal = diagonal;
  }

  public void setDiagonalAnchor(Object anchor) {
    RenderType a = null;

    if (anchor instanceof RenderType) {
      a = (RenderType) anchor;
    } else if (anchor instanceof String) {
      try {
        a = RenderType.valueOf(((String) anchor).toUpperCase(Locale.US));
      } catch(Exception e) {
        Platform.ignoreException(null, e);
      }
    }

    if (a != null) {
      this.diagonalAnchor = a;
    }
  }

  @Override
  public void setFadeIn(boolean fade) {
    this.fadeIn = fade;
  }

  @Override
  public void setFadeOut(boolean fade) {
    this.fadeOut = fade;
  }

  public void setHorizontal(boolean horizontal) {
    this.horizontal = horizontal;
  }

  @Override
  public iPlatformComponent setIncommingComponent(iPlatformComponent inComponent) {
    this.inComponent = inComponent;

    if (direction == Direction.FORWARD) {
      setViewEventsEnabled(inComponent, false);
      inComponent.removeFromParent();
      animationComponent.setComponent(inComponent, (int) width, (int) height);
      this.inComponent = animationComponent;
    }

    return this.inComponent;
  }

  @Override
  public void setOutgoingComponent(iPlatformComponent outComponent, UIImage outImage) {
    super.setOutgoingComponent(outComponent, outImage);

    if (direction == Direction.FORWARD) {
      if (animationComponent == null) {
        animationComponent = PlatformHelper.createAnimationComponent(outComponent.getWidget());
        setAnimatingProperty(animationComponent, true);
      }
    }

    width             = outImage.getWidth();
    height            = outImage.getHeight();
    this.outComponent = outComponent;
  }

  public void setPercent(float percent) {
    this.percent = percent;
  }

  @Override
  public void setTransitionType(TransitionType type) {
    horizontal = type == TransitionType.HORIZONTAL;
  }

  public RenderType getDiagonalAnchor() {
    return diagonal
           ? diagonalAnchor
           : null;
  }

  @Override
  public TransitionType getTransitionType() {
    return TransitionType.STACK;
  }

  public boolean isDiagonal() {
    return diagonal;
  }

  public boolean isHorizontal() {
    return horizontal;
  }

  @Override
  public boolean isSizingAnimation() {
    return true;
  }

  @Override
  protected void clear() {
    super.clear();

    if ((inComponent != null) &&!inComponent.isDisposed()) {
      PlatformHelper.setComponentAlpha(inComponent, 1);
      inComponent.repaint();
    }

    outComponent = null;
    inComponent  = null;
  }

  public static void updateBoundsForAnchor(BoundsChanger bc, RenderType da) {
    float       width;
    float       height;
    UIRectangle rect;
    boolean     small = bc.isSmaller();

    if (small) {
      width  = bc.from.width;
      height = bc.from.height;
      rect   = bc.to;
    } else {
      rect   = bc.from;
      width  = bc.to.width;
      height = bc.to.height;
    }

    switch(da) {
      case UPPER_RIGHT :
        rect.x += width;

        break;

      case LOWER_LEFT :
        rect.y += height;

        break;

      case LOWER_RIGHT :
        rect.x += width;
        rect.y += height;

        break;

      case LOWER_MIDDLE :
        rect.x += width / 2 + rect.x;
        rect.y += height;

        break;

      case UPPER_MIDDLE :
        rect.x += width / 2 + rect.x;

        break;

      case RIGHT_MIDDLE :
        rect.x += width;
        rect.y += height / 2 + rect.y;

        break;

      case LEFT_MIDDLE :
        rect.y += height / 2 + rect.y;

        break;

      case UPPER_LEFT :
        break;

      default :
        rect.x += width / 2 + rect.x;
        rect.y += height / 2 + rect.y;

        break;
    }

    bc.updateDiffs();
  }

  protected void update(float fraction) {
    float   width  = boundsChanger.getWidth(fraction);
    float   height = boundsChanger.getHeight(fraction);
    float   x      = boundsChanger.getX(fraction);
    float   y      = boundsChanger.getY(fraction);
    boolean small  = direction == Direction.BACKWARD;

    updateComponent(x, y, width, height, small, fraction);
  }

  protected void updateComponent(float x, float y, float width, float height, boolean small, float fraction) {
    if (outComponent != null) {
      if (small) {
        outComponent.setBounds(x, y, width, height);
        outComponent.repaint();
      } else {
        inComponent.setBounds(x, y, width, height);
      }

      if (fadeIn) {
        PlatformHelper.setComponentAlpha(inComponent, small
                ? 1 - fraction
                : fraction);
        inComponent.repaint();
      }

      if (fadeOut && (outComponent != null)) {
        PlatformHelper.setComponentAlpha(outComponent, 1 - fraction);
        outComponent.repaint();
      }
    } else {
      component.setBounds(x, y, width, height);
      component.repaint();

      boolean fade = (fadeIn &&!small) || (fadeOut && small);

      if (fade) {
        PlatformHelper.setComponentAlpha(component, small
                ? 1 - fraction
                : fraction);
      }
    }
  }

  protected boolean isComponentDisposed() {
    if (((component != null) && component.isDisposed())) {
      return true;
    }

    if (((inComponent != null) && inComponent.isDisposed())) {
      return true;
    }

    if (((outComponent != null) && outComponent.isDisposed())) {
      return true;
    }

    return false;
  }

  protected void setSizingValues(iPlatformComponent c) {
    UIRectangle from  = c.getBounds();
    UIRectangle to    = new UIRectangle(from);
    boolean     small = direction == Direction.BACKWARD;

    if (percent < 1) {
      to.width  *= percent;
      to.height *= percent;
    } else {
      to.width  = 0;
      to.height = 0;
    }

    if (small) {
      boundsChanger = new BoundsChanger(from, to);
    } else {
      boundsChanger = new BoundsChanger(to, from);
    }

    if (diagonal && (diagonalAnchor != null)) {
      updateBoundsForAnchor(boundsChanger, diagonalAnchor);
    }

    width  = c.getWidth();
    height = c.getHeight();
  }
}
