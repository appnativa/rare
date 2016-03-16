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
import com.appnativa.rare.iFunctionCallback;
import com.appnativa.rare.ui.Location;
import com.appnativa.rare.ui.RenderType;
import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.UIImage;
import com.appnativa.rare.ui.UIRectangle;
import com.appnativa.rare.ui.effects.iTransitionSupport.TransitionType;
import com.appnativa.rare.ui.event.ActionEvent;
import com.appnativa.rare.ui.event.EventListenerList;
import com.appnativa.rare.ui.event.iActionListener;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.painter.iBackgroundPainter;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.spot.SPOTPrintableString;
import com.appnativa.util.CharScanner;
import com.appnativa.util.SNumber;

import java.util.Map;

public abstract class aAnimator implements iPlatformAnimator, Cloneable {
  public static String        ANIMATOR_DISABLE_VIEW_EVENTS_KEY = "ANIMATOR_DISABLE_VIEW_EVENTS_KEY";
  public static String        ANIMATOR_KEY                     = "RARE_COMPONENT_ANIMATOR";
  protected Direction         direction                        = Direction.FORWARD;
  protected boolean           autoOrient;
  protected boolean           fadeIn;
  protected boolean           fadeOut;
  protected EventListenerList listenerList;
  protected Object            postAnimateAction;

  @Override
  public void addListener(iAnimatorListener l) {
    if (listenerList == null) {
      listenerList = new EventListenerList();
    }

    listenerList.add(iAnimatorListener.class, l);
  }

  @Override
  public void restoreComponent(iPlatformComponent comp) {}

  public static void adjustAnimation(iAnimator a, boolean forward, RenderType sizingAnchor, Location loc) {
    do {
      if ((a == null) ||!a.isAutoOrient()) {
        break;
      }

      if (a instanceof iTransitionSupport) {
        iTransitionSupport ts = (iTransitionSupport) a;

        if (ts.getTransitionType() == TransitionType.STACK) {
          if (a instanceof aSizeAnimation) {
            aSizeAnimation sa = (aSizeAnimation) a;

            if (sizingAnchor != null) {
              sa.setDiagonalAnchor(aSizeAnimation.getComplementarySideAnchor(sizingAnchor, loc));
            } else {
              switch(loc) {
                case TOP :
                case BOTTOM :
                  sa.setHorizontal(false);

                  break;

                default :
                  sa.setHorizontal(true);

                  break;
              }
            }
          }
        } else {
          switch(loc) {
            case TOP :
              ts.setTransitionType(TransitionType.VERTICAL);
              forward = !forward;

              break;

            case BOTTOM :
              ts.setTransitionType(TransitionType.VERTICAL);

              break;

            case LEFT :
              forward = !forward;
              ts.setTransitionType(TransitionType.HORIZONTAL);

              break;

            case RIGHT :
              ts.setTransitionType(TransitionType.HORIZONTAL);

              break;

            default :
              break;
          }
        }
      }

      a.setDirection(forward
                     ? Direction.FORWARD
                     : Direction.BACKWARD);
    } while(false);
  }

  @Override
  public void cancel() {
    stop();
  }

  @Override
  public Object clone() {
    try {
      aAnimator a = (aAnimator) super.clone();

      a.listenerList      = null;
      a.postAnimateAction = null;

      return a;
    } catch(CloneNotSupportedException e) {
      throw new InternalError();
    }
  }

  public static iPlatformAnimator createAnimator(iWidget context, SPOTPrintableString ps) {
    return createAnimator(context, ps.getValue(), ps.spot_getAttributesEx());
  }

  public static iPlatformAnimator createAnimator(iWidget context, String s, Map<String, String> options) {
    if ((s == null) || (s.length() == 0)) {
      return null;
    }

    iPlatformAnimator animator = null;
    int               n        = s.indexOf(';');

    if (n != -1) {
      s = s.substring(0, n).trim();
    }

    animator = Platform.getWindowViewer().createAnimator(s);

    if (animator != null) {
      animator.handleProperties(options);
    }

    return animator;
  }

  public static iTransitionAnimator createTransitionAnimator(iWidget context, String s, Map<String, String> options) {
    if ((s == null) || (s.length() == 0)) {
      return null;
    }

    String            s1  = s;
    String            s2  = null;
    iPlatformAnimator in  = null;
    iPlatformAnimator out = null;
    int               n   = s.indexOf(';');

    if (n != -1) {
      s2 = s.substring(n + 1).trim();
      s1 = s.substring(0, n).trim();
    }

    if (s1 != null) {
      in = createAnimator(context, s1, options);
    }

    if (s2 != null) {
      out = createAnimator(context, s2, options);
    }

    if ((in == null) && (out == null)) {
      return null;
    }

    if (in == null) {
      in  = out;
      out = null;
    }

    if ((options == null) || (options.get("autoOrient") == null)) {
      if (out != null) {
        out.setAutoOrient(true);
      }

      if (in != null) {
        in.setAutoOrient(true);
      }
    }

    TransitionAnimator ta = new TransitionAnimator(in, out);

    ta.setAutoDispose(true);

    return ta;
  }

  @Override
  public void dispose() {
    clear();

    if (listenerList != null) {
      listenerList.clear();
      listenerList = null;
    }
  }

  @Override
  public void handleProperties(Map map) {
    if (map == null) {
      return;
    }

    Object o = map.get("duration");

    if (o instanceof Number) {
      setDuration(((Number) o).intValue());
    } else if (o instanceof String) {
      setDuration(SNumber.intValue((String) o));
    }

    o = map.get("direction");

    if (o instanceof Direction) {
      setDirection((Direction) o);
    } else if (o instanceof String) {
      setDirection("backward".equalsIgnoreCase((String) o)
                   ? Direction.BACKWARD
                   : Direction.FORWARD);
    }

    o = map.get("acceleration");

    if (o instanceof Number) {
      setAcceleration(((Number) o).floatValue());
    } else if (o instanceof String) {
      setAcceleration(SNumber.floatValue((String) o));
    }

    o = map.get("deceleration");

    if (o instanceof Number) {
      setDeceleration(((Number) o).floatValue());
    } else if (o instanceof String) {
      setDeceleration(SNumber.floatValue((String) o));
    }

    o = map.get("fadeIn");

    if (o instanceof Boolean) {
      fadeIn = ((Boolean) o).booleanValue();
    } else if (o instanceof String) {
      fadeIn = "true".equalsIgnoreCase((String) o);
    }

    o = map.get("autoOrient");

    if (o instanceof Boolean) {
      autoOrient = ((Boolean) o).booleanValue();
    } else if (o instanceof String) {
      autoOrient = "true".equalsIgnoreCase((String) o);
    }

    o = map.get("fadeOut");

    if (o instanceof Boolean) {
      fadeOut = ((Boolean) o).booleanValue();
    } else if (o instanceof String) {
      fadeOut = "true".equalsIgnoreCase((String) o);
    }

    o = map.get("customProperties");

    if (o instanceof Map) {
      handleCustomProperties(map);
    } else if ((o instanceof String) && ((String) o).length() > 0) {
      handleCustomProperties(CharScanner.parseOptionString((String) o, ','));
    }
  }

  @Override
  public void removeListener(iAnimatorListener l) {
    if (listenerList != null) {
      listenerList.remove(iAnimatorListener.class, l);
    }
  }

  public static void setupTogglingAnimator(iPlatformComponent c, iPlatformAnimator a, boolean visible,
          boolean visibleIsforward) {
    iTransitionSupport ts = (a instanceof iTransitionSupport)
                            ? (iTransitionSupport) a
                            : null;

    if (visible) {
      a.setDirection(visibleIsforward
                     ? Direction.FORWARD
                     : Direction.BACKWARD);
    } else {
      a.setDirection(visibleIsforward
                     ? Direction.BACKWARD
                     : Direction.FORWARD);
    }

    if (ts != null) {
      UIDimension d = c.getSize();

      switch(ts.getTransitionType()) {
        case HORIZONTAL :
          int x = c.getX();

          if (visible) {
            x += (a.getDirection() == Direction.FORWARD)
                 ? d.width
                 : -d.width;
          }

          c.setBounds(x, c.getY(), d.width, d.height);

          break;

        case VERTICAL :
          int y = c.getY();

          if (visible) {
            y += (a.getDirection() == Direction.FORWARD)
                 ? d.height
                 : -d.height;
          }

          c.setBounds(c.getX(), y, d.width, d.height);

          break;

        default :
          break;
      }
    }
  }

  @Override
  public void setAnimatingProperty(iPlatformComponent c, boolean animating) {
    c.putClientProperty(ANIMATOR_KEY, animating
                                      ? this
                                      : null);
    c.putClientProperty(ANIMATOR_DISABLE_VIEW_EVENTS_KEY, animating
            ? Boolean.TRUE
            : null);
  }

  @Override
  public void setAutoOrient(boolean autoOrient) {
    this.autoOrient = autoOrient;
  }

  @Override
  public void setDirection(Direction direction) {
    this.direction = direction;
  }

  public iPlatformComponent setIncommingComponent(iPlatformComponent inComponent) {
    return inComponent;
  }

  public void setOutgoingComponent(iPlatformComponent outComponent, UIImage outImage) {}

  public void setPostAnimateAction(Object postAnimateAction) {
    this.postAnimateAction = postAnimateAction;
  }

  @Override
  public void setViewEventsEnabled(iPlatformComponent c, boolean enabled) {
    c.putClientProperty(ANIMATOR_DISABLE_VIEW_EVENTS_KEY, !enabled
            ? Boolean.TRUE
            : null);
  }

  @Override
  public Direction getDirection() {
    return direction;
  }

  public iBackgroundPainter getPainter() {
    return null;
  }

  public Object getPostAnimateAction() {
    return postAnimateAction;
  }

  public static boolean isAnimating(iPlatformComponent c) {
    Object o = c.getClientProperty(ANIMATOR_KEY);

    if (o instanceof iAnimator) {
      return ((iAnimator) o).isRunning();
    }

    if (o instanceof iTransitionAnimator) {
      return ((iTransitionAnimator) o).isRunning();
    }

    return false;
  }

  @Override
  public boolean isAutoOrient() {
    return autoOrient;
  }

  public boolean isPaintBased() {
    return false;
  }

  @Override
  public boolean isSizingAnimation() {
    return false;
  }

  public static boolean isViewEventsDisabled(iPlatformComponent c) {
    return c.getClientProperty(ANIMATOR_DISABLE_VIEW_EVENTS_KEY) == Boolean.TRUE;
  }

  protected void addValueListener(iAnimatorValueListener l) {
    if (listenerList == null) {
      listenerList = new EventListenerList();
    }

    listenerList.add(iAnimatorValueListener.class, l);
  }

  protected void clear() {
    postAnimateAction = null;
  }

  protected void handleCustomProperties(Map map) {
    Object o = map.get("autoReverse");

    if (o instanceof Boolean) {
      setAutoReverse(((Boolean) o).booleanValue());
    } else if (o instanceof String) {
      setAutoReverse("true".equalsIgnoreCase((String) o));
    }
  }

  protected void handlePostAnimateAction() {
    if (postAnimateAction != null) {
      if (postAnimateAction instanceof iActionListener) {
        final iActionListener al = (iActionListener) postAnimateAction;
        final ActionEvent     ae = new ActionEvent(this, "endAnimation");

        Platform.invokeLater(new Runnable() {
          @Override
          public void run() {
            al.actionPerformed(ae);
          }
        });
      } else if (postAnimateAction instanceof iFunctionCallback) {
        final iFunctionCallback cb = (iFunctionCallback) postAnimateAction;

        Platform.invokeLater(new Runnable() {
          @Override
          public void run() {
            cb.finished(false, aAnimator.this);
          }
        });
      } else {
        Platform.getAppContext().getWindowViewer().invokeLater(postAnimateAction);
      }
    }
  }

  protected void notifyListeners(final iPlatformAnimator animator, final boolean ended) {
    if (listenerList != null) {
      if (Platform.isUIThread()) {
        notifyListenersEx(animator, ended);
      } else {
        Runnable r = new Runnable() {
          @Override
          public void run() {
            notifyListenersEx(animator, ended);
          }
        };

        Platform.invokeLater(r);
      }
    }
  }

  protected void notifyListenersEx(final iPlatformAnimator animator, final boolean ended) {
    if (listenerList != null) {
      Object[] listeners = (listenerList == null)
                           ? null
                           : listenerList.getListenerList();

      if (listeners != null) {
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
          if (listeners[i] == iAnimatorListener.class) {
            if (ended) {
              ((iAnimatorListener) listeners[i + 1]).animationEnded(animator);
            } else {
              ((iAnimatorListener) listeners[i + 1]).animationStarted(animator);
            }
          }
        }
      }
    }
  }

  protected void notifyValueListeners(final iPlatformAnimator animator, final float value) {
    if (listenerList != null) {
      if (Platform.isUIThread()) {
        notifyValueListenersEx(animator, value);
      } else {
        Runnable r = new Runnable() {
          @Override
          public void run() {
            notifyValueListenersEx(animator, value);
          }
        };

        Platform.invokeLater(r);
      }
    }
  }

  protected void notifyValueListenersEx(final iPlatformAnimator animator, final float value) {
    if (listenerList != null) {
      Object[] listeners = (listenerList == null)
                           ? null
                           : listenerList.getListenerList();

      for (int i = listeners.length - 2; i >= 0; i -= 2) {
        if (listeners[i] == iAnimatorValueListener.class) {
          ((iAnimatorValueListener) listeners[i + 1]).valueChanged(animator, value);
        }
      }
    }
  }

  protected void removeValueListener(iAnimatorValueListener l) {
    if (listenerList != null) {
      listenerList.remove(iAnimatorValueListener.class, l);
    }
  }

  public static class BoundsChanger {
    public UIRectangle from;
    public UIRectangle to;
    public float       wdiff;
    public float       hdiff;
    public float       xdiff;
    public float       ydiff;

    public BoundsChanger(UIRectangle from, UIRectangle to) {
      super();
      this.from = from;
      this.to   = to;
      wdiff     = (float) Math.floor(to.width - from.width);
      hdiff     = (float) Math.floor(to.height - from.height);
      xdiff     = (float) Math.floor(to.x - from.x);
      ydiff     = (float) Math.floor(to.y - from.y);
    }

    public void updateDiffs() {
      wdiff = (float) Math.floor(to.width - from.width);
      hdiff = (float) Math.floor(to.height - from.height);
      xdiff = (float) Math.floor(to.x - from.x);
      ydiff = (float) Math.floor(to.y - from.y);
    }

    public boolean isSmaller() {
      return (wdiff < 0) || (hdiff < 0);
    }

    public boolean isSizeDifferent() {
      return (wdiff != 0) || (hdiff != 0);
    }

    public float getFromCenterX() {
      return (from.width / 2) + from.x;
    }

    public float getToCenterX() {
      return (to.width / 2) + to.x;
    }

    public float getFromCenterY() {
      return (from.height / 2) + from.y;
    }

    public float getToCenterY() {
      return (to.height / 2) + to.y;
    }

    public float getHeight(float fraction) {
      if (hdiff == 0) {
        return from.height;
      }

      return from.height + (hdiff * fraction);
    }

    public float getWidth(float fraction) {
      if (wdiff == 0) {
        return from.width;
      }

      return from.width + (wdiff * fraction);
    }

    public float getX(float fraction) {
      if (xdiff == 0) {
        return from.x;
      }

      return from.x + (xdiff * fraction);
    }

    public float getY(float fraction) {
      if (ydiff == 0) {
        return from.y;
      }

      return from.y + (ydiff * fraction);
    }
  }
}
