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

import java.util.Map;

import com.appnativa.rare.ui.UIImage;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.util.SNumber;

public abstract class aFadeAnimation extends aPlatformAnimator implements iTransitionSupport {
  protected iPlatformComponent inComponent;
  protected iPlatformComponent outComponent;
  protected float              startingAlpha;
  protected float              endingAlpha = 1.0f;

  public aFadeAnimation() {
    setDuration(500);
  }

  @Override
  public void setFadeIn(boolean fade) {
    this.fadeIn = fade;
  }

  @Override
  public void setFadeOut(boolean fade) {
    this.fadeOut = fade;
  }

  @Override
  public iPlatformComponent setIncommingComponent(iPlatformComponent inComponent) {
    this.inComponent = inComponent;

    return inComponent;
  }

  @Override
  public void setOutgoingComponent(iPlatformComponent outComponent, UIImage outImage) {
    this.outComponent = outComponent;
  }

  @Override
  public void setTransitionType(TransitionType type) {}

  @Override
  public TransitionType getTransitionType() {
    return TransitionType.STACK;
  }

  @Override
  protected void clear() {
    if (inComponent != null) {
      inComponent.setAlpha(1);
    } else if (component != null) {
      component.setAlpha(1);
    }

    outComponent = null;
    outComponent = null;
  }

  @Override
  protected void handleCustomProperties(Map map) {
    super.handleCustomProperties(map);

    Object o     = map.get("startAlpha");
    float  start = 0;

    if (o instanceof Number) {
      start = ((Number) o).floatValue();
    } else if (o instanceof String) {
      start = SNumber.floatValue((String) o);
    }

    if (start > 1) {
      start = start / 100f;

      if (start > 1) {
        start = 1;
      }
    }

    o = map.get("endAlpha");

    float end = 1;

    if (o instanceof Number) {
      end = ((Number) o).floatValue();
    } else if (o instanceof String) {
      end = SNumber.floatValue((String) o);
    }

    if (end > 1) {
      end = end / 100f;

      if (end > 1) {
        end = 1;
      }
    }

    startingAlpha = start;
    endingAlpha   = end;
  }

  protected void update(float value) {
    value = startingAlpha + ((endingAlpha - startingAlpha) * value);

    if ((component == null) || component.isDisposed() || (component.getParent() == null)
        || ((inComponent != null) && inComponent.isDisposed())) {
      cancel();

      return;
    }

    if ((inComponent != null) && (outComponent != null)) {
      if (!inComponent.setAlpha(value)) {
        cancel();

        return;
      }

      inComponent.repaint();

      if(outComponent.setAlpha(endingAlpha - value)) {
        outComponent.repaint();
      }
    } else {
      if (direction == Direction.FORWARD) {
        component.setAlpha(value);
      } else {
        component.setAlpha(endingAlpha - value);
      }

      component.repaint();
    }

    component.repaint();
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

  @Override
  public void restoreComponent(iPlatformComponent comp) {
    comp.setAlpha(1f);
  }

  public float getStartingAlpha() {
    return startingAlpha;
  }

  public void setStartingAlpha(float alpha) {
    this.startingAlpha = alpha;
  }

  public float getEndingAlpha() {
    return endingAlpha;
  }

  public void setEndingAlpha(float endingAlpha) {
    this.endingAlpha = endingAlpha;
  }
}
