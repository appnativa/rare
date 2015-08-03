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

package com.appnativa.rare.platform.android.ui;

import android.view.MotionEvent;
import android.view.VelocityTracker;

/**
 * Rotation gesture listener copied from:
 * http://stackoverflow.com/questions/10682019/android-two-finger-rotation
 *
 * Added velocity tracking support
 *
 * @author Don DeCoteau
 */
public class RotationGestureDetector {
  private static final int          INVALID_POINTER_ID = -1;
  private float                     fX, fY, sX, sY, focalX, focalY;
  private int                       ptrID1, ptrID2;
  private float                     mAngle;
  private float                     mVelocity;
  private boolean                   firstTouch;
  VelocityTracker                   velocityTracker;
  private OnRotationGestureListener mListener;

  public float getRotation() {
    return mAngle;
  }

  public float getVelocity() {
    return mVelocity;
  }

  public RotationGestureDetector(OnRotationGestureListener listener) {
    mListener = listener;
    ptrID1    = INVALID_POINTER_ID;
    ptrID2    = INVALID_POINTER_ID;
  }

  public float getFocalX() {
    return focalX;
  }

  public float getFocalY() {
    return focalY;
  }

  public boolean onTouchEvent(MotionEvent event) {
    switch(event.getActionMasked()) {
      case MotionEvent.ACTION_DOWN :
        if (velocityTracker == null) {
          velocityTracker = VelocityTracker.obtain();
        }

        velocityTracker.clear();
        velocityTracker.addMovement(event);
        sX         = event.getX();
        sY         = event.getY();
        ptrID1     = event.getPointerId(0);
        mAngle     = 0;
        firstTouch = true;

        break;

      case MotionEvent.ACTION_POINTER_DOWN :
        fX         = event.getX();
        fY         = event.getY();
        focalX     = getMidpoint(fX, sX);
        focalY     = getMidpoint(fY, sY);
        ptrID2     = event.getPointerId(event.getActionIndex());
        mAngle     = 0;
        firstTouch = true;

        break;

      case MotionEvent.ACTION_MOVE :
        if (velocityTracker != null) {
          velocityTracker.addMovement(event);
        }

        if ((ptrID1 != INVALID_POINTER_ID) && (ptrID2 != INVALID_POINTER_ID)) {
          float nfX, nfY, nsX, nsY;

          nsX = event.getX(event.findPointerIndex(ptrID1));
          nsY = event.getY(event.findPointerIndex(ptrID1));
          nfX = event.getX(event.findPointerIndex(ptrID2));
          nfY = event.getY(event.findPointerIndex(ptrID2));

          if (firstTouch) {
            mAngle     = 0;
            mVelocity  = 0;
            firstTouch = false;

            if (mListener != null) {
              mListener.onRotationBegin(this);
            }
          } else {
            mAngle = angleBetweenLines(fX, fY, sX, sY, nfX, nfY, nsX, nsY);

            if (mListener != null) {
              mListener.onRotation(this);
              velocityTracker.computeCurrentVelocity(1, Float.MAX_VALUE);
              mVelocity = (velocityTracker.getXVelocity() + velocityTracker.getYVelocity()) / 2;
            }
          }

          fX = nfX;
          fY = nfY;
          sX = nsX;
          sY = nsY;
        }

        break;

      case MotionEvent.ACTION_UP :
        ptrID1 = INVALID_POINTER_ID;

        break;

      case MotionEvent.ACTION_POINTER_UP :
        ptrID2 = INVALID_POINTER_ID;

        if (mListener != null) {
          mListener.onRotationEnd(this);
        }

        break;
    }

    return true;
  }

  private float getMidpoint(float a, float b) {
    return (a + b) / 2;
  }

  float findAngleDelta(float angle1, float angle2) {
    float From = ClipAngleTo0_360(angle2);
    float To   = ClipAngleTo0_360(angle1);
    float Dist = To - From;

    if (Dist < -180.0f) {
      Dist += 360.0f;
    } else if (Dist > 180.0f) {
      Dist -= 360.0f;
    }

    return Dist;
  }

  float ClipAngleTo0_360(float Angle) {
    return Angle % 360.0f;
  }

  private float angleBetweenLines(float fx1, float fy1, float fx2, float fy2, float sx1, float sy1, float sx2,
                                  float sy2) {
    float angle1 = (float) Math.atan2((fy1 - fy2), (fx1 - fx2));
    float angle2 = (float) Math.atan2((sy1 - sy2), (sx1 - sx2));

    return findAngleDelta((float) Math.toDegrees(angle1), (float) Math.toDegrees(angle2));
  }

  public static interface OnRotationGestureListener {
    public boolean onRotation(RotationGestureDetector rotationDetector);

    public boolean onRotationBegin(RotationGestureDetector rotationDetector);

    public boolean onRotationEnd(RotationGestureDetector rotationDetector);
  }
}
