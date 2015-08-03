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
import com.appnativa.rare.platform.PlatformHelper;
import com.appnativa.rare.ui.AnimationComponent;
import com.appnativa.rare.ui.UIImage;
import com.appnativa.rare.ui.UIRectangle;
import com.appnativa.rare.ui.UIScreen;
import com.appnativa.rare.ui.effects.iAnimator.Direction;
import com.appnativa.rare.ui.effects.iTransitionSupport.TransitionType;
import com.appnativa.rare.ui.iParentComponent;
import com.appnativa.rare.ui.iPlatformComponent;

import com.google.j2objc.annotations.WeakOuter;

public abstract class aTransitionAnimator implements iTransitionAnimator {
  protected Direction          direction = Direction.FORWARD;
  protected AnimationHandler   animationHandler;
  protected AnimationComponent imageComponent;
  protected iPlatformComponent inAniComponent;
  protected iPlatformAnimator  inAnimator;
  protected iPlatformComponent inComponent;
  protected iPlatformAnimator  outAnimator;
  protected iPlatformComponent outComponent;
  protected boolean            autoDispose;
  protected iFunctionCallback  thirdPartyCallback;
  protected boolean            outgoingPersists;
  protected boolean            outgoingHidden;

  public aTransitionAnimator(iPlatformAnimator inAnimator, iPlatformAnimator outAnimator) {
    this.inAnimator  = inAnimator;
    this.outAnimator = outAnimator;

    if (inAnimator == null) {
      throw new NullPointerException("the inAnimator parameter cannot be null");
    }
  }

  @Override
  public void setOutgoingPersists(boolean persists) {
    outgoingPersists = persists;
  }

  @Override
  public void setOutgoingHidden(boolean hidden) {
    outgoingHidden = hidden;
  }

  @Override
  public void animate(iParentComponent target, UIRectangle bounds, iFunctionCallback cb) {
    if (outComponent == null) {
      outComponent = imageComponent;
    }

    if (outgoingHidden && (outComponent != null)) {
      outComponent.setVisible(false);
    }

    TransitionType type = TransitionType.STACK;

    if (inAnimator instanceof iTransitionSupport) {
      type = ((iTransitionSupport) inAnimator).getTransitionType();
    }

    boolean sizing = ((inAnimator != null) && inAnimator.isSizingAnimation())
                     || ((outAnimator != null) && outAnimator.isSizingAnimation());

    if ((type == TransitionType.STACK) &&!sizing) {
      animationHandler = new StackAnimationHandler(target, cb, type);
    } else {
      animationHandler = new AnimationHandler(target, cb);
    }

    animationHandler.animate(bounds);
  }

  @Override
  public void cancel() {
    if (animationHandler != null) {
      animationHandler.cancel();
    }

    animationHandler = null;
  }

  @Override
  public void dispose() {
    if (animationHandler != null) {
      animationHandler.dispose();
    }

    if (imageComponent != null) {
      imageComponent.dispose();
    }

    if (inAnimator != null) {
      inAnimator.dispose();
    }

    if (outAnimator != null) {
      outAnimator.dispose();
    }

    inAnimator     = null;
    outAnimator    = null;
    inComponent    = null;
    outComponent   = null;
    inAniComponent = null;
    imageComponent = null;
  }

  public static iPlatformComponent resolveTransitionComponent(iParentComponent target, iPlatformComponent comp) {
    if (((comp == null) || (comp.getWidth() == 0) || (comp.getHeight() == 0))) {
      comp = PlatformHelper.createAnimationComponent(Platform.getContextRootViewer());
      comp.setBounds(target.getInnerBounds(null));
    }

    return comp;
  }

  @Override
  public void setAutoDispose(boolean autoDispose) {
    this.autoDispose = autoDispose;
  }

  @Override
  public void setCallback(iFunctionCallback callback) {
    this.thirdPartyCallback = callback;
  }

  public void setDirection(Direction direction) {
    this.direction = direction;
  }

  @Override
  public void setIncommingComponent(iPlatformComponent comp) {
    inComponent    = comp;
    inAniComponent = comp;
    setViewEventsDisabled(comp, true);

    if (inAnimator instanceof iTransitionSupport) {
      iPlatformComponent c = ((iTransitionSupport) inAnimator).setIncommingComponent(comp);

      if (c != null) {
        inAniComponent = c;
      }
    }
  }

  @Override
  public void setOutgoingComponent(iPlatformComponent comp) {
    if (animationHandler != null) {
      animationHandler.cancel();
    }

    UIImage img = null;

    if (outgoingPersists) {
      outComponent = comp;
    } else {
      if (imageComponent == null) {
        imageComponent = PlatformHelper.createAnimationComponent(comp.getWidget());
      }

      outComponent = imageComponent;
      img          = (comp == null)
                     ? null
                     : comp.capture();
      imageComponent.setImage(img);
    }

    if (outAnimator instanceof iTransitionSupport) {
      ((iTransitionSupport) outAnimator).setOutgoingComponent(outComponent, img);
    } else if (inAnimator instanceof iTransitionSupport) {
      ((iTransitionSupport) inAnimator).setOutgoingComponent(outComponent, img);
    }
  }

  public iFunctionCallback getCallback() {
    return thirdPartyCallback;
  }

  public Direction getDirection() {
    return direction;
  }

  @Override
  public iPlatformAnimator getInAnimator() {
    return inAnimator;
  }

  @Override
  public iPlatformAnimator getOutAnimator() {
    return outAnimator;
  }

  @Override
  public boolean isAutoDispose() {
    return autoDispose;
  }

  @Override
  public boolean isRunning() {
    if ((inAnimator != null) && inAnimator.isRunning()) {
      return true;
    }

    if ((outAnimator != null) && outAnimator.isRunning()) {
      return true;
    }

    return false;
  }

  protected void notifyThirdParty(final boolean ended) {
    final iFunctionCallback cb = thirdPartyCallback;

    if (cb != null) {
      Platform.invokeLater(new Runnable() {
        @Override
        public void run() {
          cb.finished(ended, aTransitionAnimator.this);
        }
      });
    }
  }

  protected void setAnimatorProperty(iPlatformComponent c, boolean set) {
    c.putClientProperty(aAnimator.ANIMATOR_KEY, set
            ? this
            : null);
    c.putClientProperty(aAnimator.ANIMATOR_DISABLE_VIEW_EVENTS_KEY, set
            ? Boolean.TRUE
            : null);
  }

  protected void setViewEventsDisabled(iPlatformComponent c, boolean set) {
    c.putClientProperty(aAnimator.ANIMATOR_DISABLE_VIEW_EVENTS_KEY, set
            ? Boolean.TRUE
            : null);
  }

  @WeakOuter
  class AnimationHandler implements iAnimatorListener {
    protected int                animatorCount = 0;
    protected int                animationCompleteCount;
    protected AnimationComponent animationComponent;
    protected AnimationComponent animationSBSComponent;
    protected iFunctionCallback  callback;
    protected boolean            canceled;
    protected iParentComponent   target;
    protected UIRectangle        bounds;

    public AnimationHandler(iParentComponent target, iFunctionCallback cb) {
      this.target = target;
      callback    = cb;
    }

    public void animate(UIRectangle bounds) {
      this.bounds = bounds;

      int width  = UIScreen.snapToSize(bounds.width);
      int height = UIScreen.snapToSize(bounds.height);

      animationComponent = PlatformHelper.createAnimationComponent(target.getWidget());

      if ((animationComponent == null) || ((inAnimator == null) && (outAnimator == null))) {
        callback.finished(false, null);
        dispose();

        return;
      }

      setAnimatorProperty(target, true);
      target.remove(inComponent);
      animationComponent.setBounds(bounds.x, bounds.y, bounds.width, bounds.height);

      boolean        forward        = (inAnimator != null) && (inAnimator.getDirection() == Direction.FORWARD);
      boolean        singleAnimator = outAnimator == null;
      TransitionType type           = TransitionType.STACK;

      if (inAnimator instanceof iTransitionSupport) {
        type = ((iTransitionSupport) inAnimator).getTransitionType();
      }

      iPlatformComponent c = animationComponent;
      int                x = UIScreen.snapToPosition(bounds.x);
      int                y = UIScreen.snapToPosition(bounds.y);

      if (type != TransitionType.STACK) {
        animationSBSComponent = PlatformHelper.createAnimationComponent(target.getWidget());
        animationSBSComponent.setBounds(bounds.x, bounds.y, bounds.width, bounds.height);
        animationSBSComponent.add(animationComponent);
        animationComponent.setBounds(0, 0, bounds.width, bounds.height);
        c = animationSBSComponent;
        x = 0;
        y = 0;
      }

      switch(type) {
        case STACK :
          animationComponent.setStackedComponents(forward, outComponent, inAniComponent, width, height);

          break;

        case VERTICAL :
          animationComponent.setSideBySideComponents(forward, false, outComponent, inAniComponent, width, height,
                  singleAnimator);

          if (singleAnimator) {
            if (!forward) {
              y -= height;
            }

            animationComponent.setBounds(x, y, width, height * 2);
          }

          break;

        default :
          animationComponent.setSideBySideComponents(forward, true, outComponent, inAniComponent, width, height,
                  singleAnimator);

          if (singleAnimator) {
            if (!forward) {
              x -= width;
            }

            animationComponent.setBounds(x, y, width * 2, height);
          }

          break;
      }

      target.add(c);

      if ((inAnimator == null) || (outAnimator == null)) {
        iPlatformAnimator a = (inAnimator == null)
                              ? outAnimator
                              : inAnimator;

        animatorCount = 1;
        a.addListener(this);
        a.animate(animationComponent, null);
      } else {
        animatorCount = 2;
        outAnimator.addListener(this);
        outAnimator.animate(outComponent, this);
        inAnimator.addListener(this);
        inAnimator.animate(inAniComponent, null);
      }
    }

    protected void restoreOutComponent() {
      if ((outComponent != null) &&!outComponent.isDisposed()) {
        if (outAnimator != null) {
          outAnimator.restoreComponent(outComponent);
        } else if (inAnimator != null) {
          inAnimator.restoreComponent(outComponent);
        }
      }
    }

    @Override
    public void animationEnded(iPlatformAnimator source) {
      if ((target != null) && (inComponent != null)) {
        animationCompleteCount++;

        if (animatorCount <= animationCompleteCount) {
          if (animationComponent != null) {
            animationComponent.removeFromParent();
          }

          if (animationSBSComponent != null) {
            animationSBSComponent.removeFromParent();
          }

          if ((outComponent != null) && outgoingHidden &&!outComponent.isDisposed()) {
            outComponent.setVisible(true);
          }

          if (!inComponent.isDisposed()) {
            inComponent.removeFromParent();
            inAniComponent.removeFromParent();

            if (inAniComponent != inComponent) {
              inAniComponent.dispose();
            }

            setAnimatorProperty(inComponent, false);
            target.add(inComponent);
          }

          if ((callback != null) &&!canceled) {
            callback.finished(false, null);
          }

          if (outgoingPersists) {
            restoreOutComponent();
          }
        }

        setAnimatorProperty(target, false);
        target.revalidate();
      }

      notifyThirdParty(canceled);
      dispose();
    }

    @Override
    public void animationStarted(iPlatformAnimator source) {}

    void cancel() {
      if (canceled) {
        return;
      }

      canceled = true;

      iFunctionCallback cb = callback;

      if (inAnimator != null) {
        inAnimator.cancel();
      }

      if (outAnimator != null) {
        outAnimator.cancel();
      }

      callback = null;

      if (cb != null) {
        cb.finished(true, null);
      }

      notifyThirdParty(true);
      dispose();
    }

    protected void dispose() {
      canceled = true;

      try {
        if (animationSBSComponent != null) {
          animationSBSComponent.removeAll();
          animationSBSComponent.dispose();
        }

        if (animationComponent != null) {
          animationComponent.removeAll();
          animationComponent.dispose();
        }

        if (inAnimator != null) {
          inAnimator.removeListener(this);
        }

        if (outAnimator != null) {
          outAnimator.removeListener(this);
        }

        if (imageComponent != null) {
          imageComponent.dispose();
        }

        animationSBSComponent = null;
        animationComponent    = null;
        callback              = null;
        target                = null;
        inComponent           = null;
        outComponent          = null;
        inAniComponent        = null;
        imageComponent        = null;
        bounds                = null;
      } catch(Exception e) {
        Platform.ignoreException(null, e);
      }
    }
  }


  @WeakOuter
  class StackAnimationHandler extends AnimationHandler {
    TransitionType type = TransitionType.STACK;

    public StackAnimationHandler(iParentComponent target, iFunctionCallback cb, TransitionType type) {
      super(target, cb);
      this.target = target;
      callback    = cb;
      this.type   = type;
    }

    void stackedComponents() {
      boolean forward = (inAnimator != null) && (inAnimator.getDirection() == Direction.FORWARD);

      if (forward) {
        if (inAniComponent != null) {
          inAniComponent.bringToFront();
        }
      } else {
        if (outComponent != null) {
          outComponent.bringToFront();
        }
      }

      if (inAniComponent != null) {
        inAniComponent.setBounds(bounds);
      }

      if (outComponent != null) {
        outComponent.setBounds(bounds);
      }
    }

    void sideBySideComponents() {
      float   width      = bounds.width;
      float   height     = bounds.height;
      float   x          = bounds.x;
      float   y          = bounds.y;
      boolean horizontal = type == TransitionType.HORIZONTAL;
      boolean forward    = (inAnimator != null) && (inAnimator.getDirection() == Direction.FORWARD);

      if (outComponent != null) {
        outComponent.setBounds(x, y, width, height);
      }

      if (horizontal) {
        if (inComponent != null) {
          if (forward) {
            inComponent.setBounds(x + width, y, width, height);
          } else {
            inComponent.setBounds(x - width, y, width, height);
          }
        }
      } else {
        if (inComponent != null) {
          if (forward) {
            inComponent.setBounds(x, y + height, width, height);
          } else {
            inComponent.setBounds(x, y - height, width, height);
          }
        }
      }
    }

    @Override
    public void animate(UIRectangle bounds) {
      if (((inAnimator == null) && (outAnimator == null))) {
        callback.finished(false, null);
        dispose();

        return;
      }

      this.bounds = bounds;
      setAnimatorProperty(target, true);

      if (outComponent != null) {
        target.add(outComponent);
      }

      if (type == TransitionType.STACK) {
        stackedComponents();
      } else {
        sideBySideComponents();
      }

      if (inComponent != null) {
        setAnimatorProperty(inComponent, true);
      }

      if ((inAnimator == null) || (outAnimator == null)) {
        animatorCount = 1;

        if ((inAnimator != null) && (inAniComponent != null)) {
          inAnimator.addListener(this);

          if ((type == TransitionType.STACK) || (outComponent == null)) {
            inAnimator.animate(inAniComponent, null);
          } else {
            inAnimator.animate(outComponent, null);
          }
        } else if ((outAnimator != null) && (outComponent != null)) {
          outAnimator.addListener(this);
          outAnimator.animate(outComponent, null);
        } else {
          callback.finished(false, null);
          dispose();

          return;
        }
      } else {
        animatorCount = 2;
        outAnimator.addListener(this);
        outAnimator.animate(outComponent, this);
        inAnimator.addListener(this);
        inAnimator.animate(inAniComponent, null);
      }
    }

    @Override
    public void animationEnded(iPlatformAnimator source) {
      if ((target != null) && (inComponent != null)) {
        animationCompleteCount++;

        if (animatorCount <= animationCompleteCount) {
          if (animationComponent != null) {
            animationComponent.removeFromParent();
          }

          if (animationSBSComponent != null) {
            animationSBSComponent.removeFromParent();
          }

          if (outgoingHidden && (outComponent != null)) {
            outComponent.setVisible(true);
          }

          if (!inComponent.isDisposed()) {
            if (inAniComponent != inComponent) {
              inAniComponent.removeFromParent();
              inAniComponent.dispose();
            }

            if (inComponent.getParent() != target) {
              inComponent.removeFromParent();
              target.add(inComponent);
            }

            setAnimatorProperty(inComponent, false);
            inComponent.setBounds(bounds.x, bounds.y, bounds.width, bounds.height);
          }

          if ((callback != null) &&!canceled) {
            callback.finished(false, null);
          }
        }

        setAnimatorProperty(target, false);
        target.revalidate();
      }

      notifyThirdParty(canceled);
      dispose();
    }
  }
}
