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

import java.util.Timer;
import java.util.TimerTask;


public class ValueRangeAnimator extends aPlatformAnimator {
  float   delta;
  float   endValue;
  boolean intValue;
  float   startValue;
  Timer   timer;

  public ValueRangeAnimator(float start, float end) {
    super();
    startValue = start;
    endValue   = end;
    delta      = end - start;
  }

  public ValueRangeAnimator(int start, int end) {
    super();
    startValue = start;
    endValue   = end;
    delta      = end - start;
    intValue   = true;
  }

  @Override
  public void addValueListener(iAnimatorValueListener l) {
    super.addValueListener(l);
  }

  @Override
  public void removeValueListener(iAnimatorValueListener l) {
    super.removeValueListener(l);
  }

  @Override
  public void cancel() {
    if (timer != null) {
      timer.cancel();
      timer = null;
    }
  }

  public static ValueRangeAnimator ofFloat(float start, float end) {
    return new ValueRangeAnimator(start, end);
  }

  public static ValueRangeAnimator ofInt(int start, int end) {
    return new ValueRangeAnimator(start, end);
  }

  public void start() {
    Timer       t  = timer;
    final Timer tt = new Timer("ValueRangeTimer");

    timer = tt;

    if (t != null) {
      t.cancel();
    }

    final long startTime = System.currentTimeMillis();
    TimerTask r=new TimerTask() {
      @Override
      public void run() {
        long  elapsed  = System.currentTimeMillis() - startTime;
        float fraction = Math.min((float) elapsed / (float) duration, 1);
        timingEvent(tt, fraction);

        if (fraction >= 1) {
          stop();
        }
      }
    };

    timer.schedule(r, 0, 30);
    notifyListeners(this, false);
  }

  @Override
  public void stop() {
    if (timer != null) {
      timer.cancel();
      timer = null;
      notifyListeners(this, true);
    }
  }

  public void setRange(float start, float end) {
    startValue = start;
    endValue   = end;
    delta      = end - start;
  }

  public void setRange(int start, int end) {
    startValue = start;
    endValue   = end;
    delta      = end - start;
    intValue   = true;
  }

  protected void timingEvent(Timer timer, float fraction) {
    if (this.timer == timer) {
      float value = startValue + (delta * fraction);

      if (intValue) {
        value = (float) Math.floor(value);
      }

      notifyValueListeners(this, value);
    }
  }
}
