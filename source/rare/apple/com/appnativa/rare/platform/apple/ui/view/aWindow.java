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

package com.appnativa.rare.platform.apple.ui.view;

import com.appnativa.rare.Platform;
import com.appnativa.rare.ui.Component;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.UIPoint;
import com.appnativa.rare.ui.UIRectangle;
import com.appnativa.rare.ui.Utils;
import com.appnativa.rare.ui.WindowPane;
import com.appnativa.rare.ui.WindowTarget;
import com.appnativa.rare.ui.border.SharedLineBorder;
import com.appnativa.rare.ui.border.UIEmptyBorder;
import com.appnativa.rare.ui.effects.aAnimator;
import com.appnativa.rare.ui.effects.iAnimator.Direction;
import com.appnativa.rare.ui.effects.iAnimatorListener;
import com.appnativa.rare.ui.effects.iPlatformAnimator;
import com.appnativa.rare.ui.event.EventListenerList;
import com.appnativa.rare.ui.event.WindowEvent;
import com.appnativa.rare.ui.event.iWindowListener;
import com.appnativa.rare.ui.iParentComponent;
import com.appnativa.rare.ui.iPlatformBorder;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.iPlatformIcon;
import com.appnativa.rare.ui.iPlatformMenuBar;
import com.appnativa.rare.ui.iStatusBar;
import com.appnativa.rare.ui.iToolBarHolder;
import com.appnativa.rare.ui.painter.iPlatformComponentPainter;
import com.appnativa.rare.viewer.iTarget;
import com.appnativa.rare.viewer.iViewer;

/*-[
 #import "AppleHelper.h"
 #import "APView+Component.h"
 #import "RAREAPWindow.h"
 ]-*/
public abstract class aWindow extends ParentView {
  UIEmptyBorder                paddingBorder;
  protected iPlatformComponent content;
  protected boolean            decorated;
  protected EventListenerList  listenerList;
  protected WindowPane         rootPane;
  protected boolean            sizeSet;
  protected iTarget            target;
  protected iPlatformAnimator  animator;
  protected boolean            visibleIsforward;
  boolean                      canceled;
  private iParentComponent     animationComponent;

  public aWindow(boolean decorated) {
    this(createProxy());

    if (decorated) {
      setDecorated(decorated);
      this.decorated = true;
    }
  }

  public aWindow(Object nswindow) {
    super(nswindow);
    associateWindow();
    rootPane = createRootPane();
  }

  public native void addViewToGlass(View view)
  /*-[
    [((RAREAPWindow*)proxy_) addViewToGlass: view ];
  ]-*/
  ;

  public void addWindowListener(iWindowListener l) {
    if (listenerList == null) {
      listenerList = new EventListenerList();

      iWindowListener wl = new iWindowListener() {
        @Override
        public void windowEvent(WindowEvent e) {
          Utils.fireWindowEvent(listenerList, e);
        }
      };

      setWindowListener(wl);
    }

    listenerList.add(iWindowListener.class, l);
  }

  public native void centerOnScreen()
  /*-[
     [((RAREAPWindow*)proxy_) centerEx];
  ]-*/
  ;

  public native void close()
  /*-[
    [((RAREAPWindow*)proxy_) closeEx];
  ]-*/
  ;

  @Override
  public void dispose() {
    disposeEx();
    super.dispose();

    if (target != null) {
      target.dispose(false);

      if (rootPane != null) {
        rootPane.dispose();
      }
    } else if (rootPane != null) {
      iViewer v = setViewer(null);

      if ((v != null) && v.isAutoDispose()) {
        v.dispose();
      }

      rootPane.dispose();
    }

    if (listenerList != null) {
      listenerList.clear();
    }

    listenerList = null;
    content      = null;
    rootPane     = null;
    target       = null;
  }

  @Override
  public native void disposeEx()
  /*-[
    [((RAREAPWindow*)proxy_) disposeEx];
  ]-*/
  ;

  @Override
  public native UIRectangle getBounds()
  /*-[
  #if TARGET_OS_IPHONE
    CGRect frame= [((RAREAPWindow*)proxy_) frame];
  #else
    NSRect frame= [((RAREAPWindow*)proxy_) frame];
    frame.origin.y-=frame.size.height;
  #endif
    return [[RAREUIRectangle alloc]initWithFloat:frame.origin.x
    withFloat:frame.origin.y
    withFloat:frame.size.width
    withFloat:frame.size.height];
  ]-*/
  ;

  public iPlatformComponent getContent() {
    return content;
  }

  @Override
  public native float getHeight()
  /*-[
    return ceilf([((RAREAPWindow*)proxy_) orientedSize].height);
  ]-*/
  ;

  public UIRectangle getInnerBounds(UIRectangle rect) {
    return rootPane.getInnerBounds(rect);
  }

  @Override
  public native UIPoint getLocation(UIPoint loc)
  /*-[
  #if TARGET_OS_IPHONE
  CGPoint p= [((RAREAPWindow*)proxy_) orientedLocation];
  #else
  NSPoint p= [((RAREAPWindow*)proxy_) orientedLocation];
  #endif
    if(loc==nil) {
      loc=[[RAREUIPoint alloc] initWithFloat:p.x withFloat:p.y];
    }
    else {
      [loc setLocationWithFloat:p.x withFloat:p.y];
    }
    return loc;
  ]-*/
  ;

  @Override
  public void getMinimumSize(UIDimension size) {
    getPreferredSize(size, 0);
  }

  @Override
  public void getPreferredSize(UIDimension size, float maxWidth) {
    rootPane.getPreferredSize(size, maxWidth);
  }

  public iPlatformComponent getRootPane() {
    return rootPane;
  }

  @Override
  public UIDimension getSize() {
    UIDimension d = new UIDimension();

    getSize(d);

    return d;
  }

  @Override
  public native void getSize(UIDimension size)
  /*-[
  #if TARGET_OS_IPHONE
  CGSize s= [((RAREAPWindow*)proxy_) orientedSize];
  #else
  NSSize s= [((RAREAPWindow*)proxy_) orientedSize];
  #endif
  size->width_=(int)ceilf(s.width);
  size->height_=(int)ceilf(s.height);
  ]-*/
  ;

  public native String getTitle()
  /*-[
    return [((RAREAPWindow*)proxy_) getTitle];
  ]-*/
  ;

  @Override
  public native float getWidth()
  /*-[
    return ceilf([((RAREAPWindow*)proxy_) orientedSize].width);
  ]-*/
  ;

  public WindowPane getWindowPane() {
    return rootPane;
  }

  @Override
  public native float getX()
  /*-[
    return [((RAREAPWindow*)proxy_) orientedLocation].x;
  ]-*/
  ;

  @Override
  public native float getY()
  /*-[
    return [((RAREAPWindow*)proxy_) orientedLocation].y;
  ]-*/
  ;

  @Override
  public boolean isDescendantOf(View view) {
    return false;
  }

  @Override
  public native boolean isEnabled()
  /*-[
    return [((RAREAPWindow*)proxy_) isEnabled];
  ]-*/
  ;

  @Override
  public boolean isFocusable() {
    return true;
  }

  @Override
  public native boolean isFocused()
  /*-[
    return [((RAREAPWindow*)proxy_) isKeyWindow];
  ]-*/
  ;

  @Override
  public boolean isFocusedOrChildOfFocused() {
    return isFocused();
  }

  public native boolean isMovable()
  /*-[
    return [((RAREAPWindow*)proxy_) isMovable];
  ]-*/
  ;

  @Override
  public native boolean isOpaque()
  /*-[
    return ![((RAREAPWindow*)proxy_) isOpaque];
  ]-*/
  ;

  public native boolean isResizable()
  /*-[
    return [((RAREAPWindow*)proxy_) isResizable];
  ]-*/
  ;

  @Override
  public boolean isShowing() {
    return isVisible();
  }

  @Override
  public native boolean isVisible()
  /*-[
    return [((RAREAPWindow*)proxy_) isVisible];
  ]-*/
  ;

  public native void moveBy(float x, float y)
  /*-[
    [((RAREAPWindow*)proxy_)  moveByX: x andY:  y];
  ]-*/
  ;

  public void pack() {
    UIDimension size = new UIDimension();

    rootPane.getPreferredSize(size);
    size.height += getTitlebarHeight();
    setSizeEx(size.width, size.height);
    rootPane.revalidate();
  }

  public native void removeViewFromGlass(View view)
  /*-[
    [((RAREAPWindow*)proxy_) removeViewFromGlass: view ];
  ]-*/
  ;

  public void removeWindowListener(iWindowListener l) {
    if (listenerList != null) {
      listenerList.remove(iWindowListener.class, l);
    }
  }

  @Override
  public void revalidate() {
    super.revalidate();
    rootPane.revalidate();
  }

  public void setAnimator(iPlatformAnimator animator) {
    this.animator = animator;

    if (this.animator != null) {
      visibleIsforward = animator.getDirection() == Direction.FORWARD;

      if (animationComponent == null) {
        rootPane.removeFromParent();
        animationComponent = createAnimationContainer();
        setContentView(animationComponent.getView().proxy);
        animationComponent.add(rootPane);
      }
    }
  }

  @Override
  public void setBackgroundColor(UIColor bg) {
    setBackgroundColorEx(bg);
    rootPane.setBackground(bg);
  }

  @Override
  public void setBounds(float x, float y, float width, float height) {
    setLocation(x, y);
    setSize(width, height);
  }

  @Override
  public void setBounds(int x, int y, int width, int height) {
    setLocation(x, y);
    setSize(width, height);
  }

  @Override
  public void setComponent(Component component) {
    this.component = component;
  }

  @Override
  public void setComponentPainter(iPlatformComponentPainter cp) {
    rootPane.setComponentPainter(cp);
  }

  public void setContent(iPlatformComponent content) {
    this.content = content;
    rootPane.setContent(content);
  }

  public abstract void setDecorated(boolean decorated);

  @Override
  public native void setEnabled(boolean enabled)
  /*-[
    [((RAREAPWindow*)proxy_) setEnabled: enabled];
  ]-*/
  ;

  @Override
  public native void setFocusable(boolean focusable)
  /*-[
    [((RAREAPWindow*)proxy_) setEnabled: focusable];
  ]-*/
  ;

  @Override
  public void setIcon(iPlatformIcon icon) {
    rootPane.setIcon(icon);
  }

  public void setMenuBar(iPlatformMenuBar mb) {
    rootPane.setMenuBar(mb);
  }

  public native void setModal(boolean modal)
  /*-[
    [((RAREAPWindow*)proxy_) setModal:modal];
  ]-*/
  ;

  public native void setMovable(boolean movable)
  /*-[
    [((RAREAPWindow*)proxy_) setMovable: movable ];
  ]-*/
  ;

  public native void setResizable(boolean resizable)
  /*-[
    [((RAREAPWindow*)proxy_) setResizable: resizable ];
  ]-*/
  ;

  @Override
  public void setSize(float width, float height) {
    sizeSet = true;
    setSizeEx(width, height);

    if (rootPane != null) {
      rootPane.setSize(width, height);
      rootPane.revalidate();
    }

    if (animationComponent != null) {
      animationComponent.setBounds(0, 0, width, height);
      animationComponent.revalidate();
    } else if (rootPane != null) {
      rootPane.setSize(width, height);
      rootPane.revalidate();
    }
  }

  public void setStatusBar(iStatusBar sb) {
    rootPane.setStatusBar(sb);
  }

  public native void setTitle(String title)
  /*-[
    [rootPane_ setTitleWithNSString: title];
    [((RAREAPWindow*)proxy_) setTitle:title];
  ]-*/
  ;

  public void setTitleView(iPlatformComponent c) {
    rootPane.setTitileBar(c);
  }

  public void setToolBar(iToolBarHolder tb) {
    rootPane.setToolBar(tb);
  }

  public iViewer setViewer(iViewer viewer) {
    iViewer v = null;

    if (viewer != null) {
      if (target == null) {
        createTarget();
      }

      v = target.setViewer(viewer);
    } else {
      setContent(null);
    }

    return v;
  }

  @Override
  public void setVisible(final boolean visible) {
    if (visible == isVisible()) {
      return;
    }

    if (animator == null) {
      setWindowVisible(visible);
    } else {
      if (visible) {
        setWindowVisible(visible);
      }

      animator.addListener(new iAnimatorListener() {
        @Override
        public void animationEnded(iPlatformAnimator source) {
          source.removeListener(this);
          source.setDirection(visibleIsforward
                              ? Direction.FORWARD
                              : Direction.BACKWARD);    // reset
          // direction

          if (!visible) {
            setWindowVisible(false);
          } else {
            if (animationComponent != null) {
              animationComponent.revalidate();
            }

            rootPane.repaint();

            iPlatformBorder b = rootPane.getBorder();

            if (b instanceof SharedLineBorder) {
              ((SharedLineBorder) b).repaintTopComponent();
            }
          }
        }
        @Override
        public void animationStarted(iPlatformAnimator source) {}
      });
      aAnimator.setupTogglingAnimator(animationComponent, animator, visible, visibleIsforward);
      animator.animate(animationComponent, null);
    }
  }

  protected native void associateWindow()
  /*-[
    RAREAPWindow* win=(RAREAPWindow*)proxy_;
    win.sparWindow=(RAREWindow*)self;
  ]-*/
  ;

  protected abstract iParentComponent createAnimationContainer();

  protected WindowPane createRootPane() {
    WindowPane p = new WindowPane(Platform.getContextRootViewer());

    setContentView(p.getView().proxy);

    return p;
  }

  protected void createTarget() {
    String name = "_new_window_" + Integer.toHexString((int)hashCode());// int cast for java->objc

    target = new WindowTarget(Platform.getAppContext(), name, rootPane);
  }

  protected float getTitlebarHeight() {
    return 0;
  }

  protected boolean handleOutsideTouch() {
    return false;
  }

  protected abstract void setContentView(Object nativeview);

  protected abstract void setSizeEx(float width, float height);

  protected native void setTitleEx(String title)
  /*-[
    [((RAREAPWindow*)proxy_) setTitle:title];
  ]-*/
  ;

  @Override
  protected native void setVisibleEx(boolean visible)
  /*-[
    [((RAREAPWindow*)proxy_) setVisible:visible];
  ]-*/
  ;

  protected native void setWindowListener(iWindowListener l)
  /*-[
    [((RAREAPWindow*)proxy_) setWindowListener: l];
  ]-*/
  ;

  protected void setWindowVisible(boolean visible) {
    setVisibleEx(visible);

    if (visible) {
      Platform.getAppContext().getActiveWindows().add(this);
    } else {
      Platform.getAppContext().getActiveWindows().remove(this);
    }
  }

  private static native Object createProxy()
  /*-[
    return [RAREAPWindow new];
  ]-*/
  ;
}
