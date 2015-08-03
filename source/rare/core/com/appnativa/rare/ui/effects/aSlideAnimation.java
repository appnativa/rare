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

import com.appnativa.rare.platform.PlatformHelper;
import com.appnativa.rare.ui.AnimationComponent;
import com.appnativa.rare.ui.UIImage;
import com.appnativa.rare.ui.UIRectangle;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.util.SNumber;

import com.google.j2objc.annotations.Weak;

import java.util.Map;

public abstract class aSlideAnimation extends aPlatformAnimator implements iTransitionSupport {
  protected boolean            horizontal = true;
  protected float              percent    = 1f;
  @Weak
  protected iPlatformComponent outComponent;
  BoundsChanger                locationChanger;
  protected iPlatformComponent inComponent;

  public aSlideAnimation() {
    this(true, true);
  }

  public aSlideAnimation(boolean horizontal) {
    this(horizontal, true);
  }

  public aSlideAnimation(boolean horizontal, boolean fromLeftOrTop) {
    this.horizontal = horizontal;
    setDirection(fromLeftOrTop
                 ? Direction.BACKWARD
                 : Direction.FORWARD);
  }

  @Override
  protected void clear() {
    super.clear();
    outComponent = null;
    inComponent  = null;
  }

  public void animateTo(iPlatformComponent c, float x, float y) {
    UIRectangle from = c.getBounds();
    UIRectangle to   = new UIRectangle(x, y, from.width, from.height);

    locationChanger = new BoundsChanger(from, to);
    super.animate(c, postAnimateAction);
  }

  @Override
  public void animate(iPlatformComponent c, Object postAnimateAction) {
    UIRectangle from = c.getBounds();
    float       size = horizontal
                       ? from.width
                       : from.height;
    float       x    = from.x;
    float       y    = from.y;

    if (c instanceof AnimationComponent) {
      size /= 2;
    }

    if (percent < 1) {
      size = size * percent;
    }

    boolean fromLeftOrTop = direction == Direction.BACKWARD;

    if (horizontal) {
      if (fromLeftOrTop) {
        x += size;
      } else {
        x -= size;
      }
    } else {
      if (fromLeftOrTop) {
        y += size;
      } else {
        y -= size;
      }
    }

    UIRectangle to = new UIRectangle(x, y, from.width, from.height);

    locationChanger = new BoundsChanger(from, to);
    super.animate(c, postAnimateAction);
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
  public void setOutgoingComponent(iPlatformComponent outComponent, UIImage outImage) {
    super.setOutgoingComponent(outComponent, outImage);
    this.outComponent = outComponent;
  }

  @Override
  public iPlatformComponent setIncommingComponent(iPlatformComponent inComponent) {
    this.inComponent = inComponent;

    return inComponent;
  }

  public void setPercent(float percent) {
    this.percent = percent;
  }

  @Override
  public void setTransitionType(TransitionType type) {
    horizontal = type == TransitionType.HORIZONTAL;
  }

  @Override
  public TransitionType getTransitionType() {
    return horizontal
           ? TransitionType.HORIZONTAL
           : TransitionType.VERTICAL;
  }

  public boolean isHorizontal() {
    return horizontal;
  }

  @Override
  protected void handleCustomProperties(Map map) {
    super.handleCustomProperties(map);

    Object o = map.get("percent");
    float  p = 0;

    if (o instanceof Number) {
      p = ((Number) o).floatValue();
    } else if (o instanceof String) {
      p = SNumber.floatValue((String) o);
    }

    if (p > 1) {
      p = p / 100f;

      if (p > 1) {
        p = 1;
      }
    }

    if (p > 0) {
      percent = p;
    }
  }

  @Override
  public void restoreComponent(iPlatformComponent comp) {
    PlatformHelper.setComponentAlpha(comp, 1f);
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

  protected void update(float fraction) {
    float x;
    float y;

    if (horizontal) {
      y = locationChanger.to.y;
      x = locationChanger.getX(fraction);
    } else {
      x = locationChanger.to.x;
      y = locationChanger.getY(fraction);
    }

    if (outComponent == component) {
      outComponent.setLocation(x, y);

      if (direction == Direction.FORWARD) {
        x += horizontal
             ? outComponent.getWidth()
             : 0;
        y += horizontal
             ? 0
             : outComponent.getHeight();
        inComponent.setLocation(x, y);
      } else {
        x -= horizontal
             ? outComponent.getWidth()
             : 0;
        y -= horizontal
             ? y
             : outComponent.getHeight();
        inComponent.setLocation(x, y);
      }
    } else {
      component.setLocation(x, y);
    }

    if (fadeOut && (outComponent != null)) {
      PlatformHelper.setComponentAlpha(outComponent, 1 - fraction);
      outComponent.repaint();
    }

    if (fadeIn && (inComponent != null)) {
      PlatformHelper.setComponentAlpha(inComponent, fraction);
      inComponent.repaint();
    }
  }
}
