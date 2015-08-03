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

import com.appnativa.rare.ui.UIPoint;
import com.appnativa.rare.ui.iPlatformComponent;

import java.util.Map;

public class aPullBackAnimation extends aPlatformAnimator {
  protected SlideAnimation slide;
  protected SlideAnimation slideBack;
  protected UIPoint        location;

  public aPullBackAnimation() {
    this(true, true);
  }

  public aPullBackAnimation(boolean horizontal) {
    this(horizontal, true);
  }

  public aPullBackAnimation(boolean horizontal, boolean fromLeftOrTop) {
    super();
    slide     = new SlideAnimation(horizontal, fromLeftOrTop);
    slideBack = new SlideAnimation(horizontal, !fromLeftOrTop);
    slide.setDuration(150);
    slideBack.setDuration(150);
    slide.setPercent(.25f);
    slideBack.setPercent(.25f);
    slide.setAcceleration(.9f);
    slideBack.setDeceleration(.9f);
    super.setDirection(fromLeftOrTop
                       ? Direction.BACKWARD
                       : Direction.FORWARD);
  }

  @Override
  protected void notifyListeners(iPlatformAnimator animator, boolean ended) {
    if (ended && (location != null) &&!location.equals(component.getLocation())) {
      component.setLocation(location.x, location.y);
    }

    super.notifyListeners(animator, ended);
  }

  @Override
  public void animate(iPlatformComponent c, Object postAnimateAction) {
    this.postAnimateAction = postAnimateAction;
    this.component         = c;
    location               = c.getLocation();
    slide.animate(c, null);
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
  public void setDirection(Direction direction) {
    super.setDirection(direction);
    slide.setDirection(direction);
    slideBack.setDirection((direction == Direction.FORWARD)
                           ? Direction.BACKWARD
                           : Direction.FORWARD);
  }

  @Override
  public void setDuration(int duration) {
    super.setDuration(duration);
    slide.setDuration((int) (duration * .75));
    slideBack.setDuration((int) (duration * .25));
  }

  public void setHorizontal(boolean horizontal) {
    slide.setHorizontal(horizontal);
    slideBack.setHorizontal(horizontal);
  }
}
