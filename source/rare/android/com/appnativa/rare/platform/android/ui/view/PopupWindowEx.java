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

package com.appnativa.rare.platform.android.ui.view;

import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewParent;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;

import com.appnativa.rare.Platform;
import com.appnativa.rare.platform.PlatformHelper;
import com.appnativa.rare.platform.android.MainActivity;
import com.appnativa.rare.ui.RenderType;
import com.appnativa.rare.ui.ScreenUtils;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.UIInsets;
import com.appnativa.rare.ui.UIPoint;
import com.appnativa.rare.ui.UIScreen;
import com.appnativa.rare.ui.UITarget;
import com.appnativa.rare.ui.WindowPane;
import com.appnativa.rare.ui.WindowTarget;
import com.appnativa.rare.ui.iPlatformBorder;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.iPopup;
import com.appnativa.rare.ui.border.SharedLineBorder;
import com.appnativa.rare.ui.effects.aAnimator;
import com.appnativa.rare.ui.effects.iAnimator.Direction;
import com.appnativa.rare.ui.effects.iAnimatorListener;
import com.appnativa.rare.ui.effects.iPlatformAnimator;
import com.appnativa.rare.ui.event.EventListenerList;
import com.appnativa.rare.ui.event.ExpansionEvent;
import com.appnativa.rare.ui.event.iPopupMenuListener;
import com.appnativa.rare.ui.painter.UIComponentPainter;
import com.appnativa.rare.ui.painter.iBackgroundPainter;
import com.appnativa.rare.ui.painter.iPlatformComponentPainter;
import com.appnativa.rare.viewer.iViewer;

/**
 * Class the display widgets in a popup
 *
 * @author Don DeCoteau
 */
public class PopupWindowEx extends PopupWindow implements OnTouchListener, OnDismissListener, iPopup {
  boolean                      modal;
  WindowPane                   rootPane;
  UITarget                     target;
  protected int                x        = 0;
  protected int                y        = 0;
  protected boolean            canceled = true;
  protected OnDismissListener  dismissListener;
  protected boolean            isTransient;
  protected EventListenerList  listenerList;
  protected iPlatformComponent owner;
  protected OnTouchListener    touchListener;
  protected boolean            visibleIsforward;
  protected boolean            visiblityFired;
  protected iPlatformAnimator    animator;
  protected boolean              dismissing;
  protected boolean              sizeSet;
  protected int timeout;

  public PopupWindowEx(Context context) {
    super(context);
    init(context);
  }

  public PopupWindowEx(Context context, int width, int height) {
    super(width, height);
    init(context);
  }

  @Override
  public void addPopupMenuListener(iPopupMenuListener l) {
    if (listenerList == null) {
      listenerList = new EventListenerList();
    }

    listenerList.add(iPopupMenuListener.class, l);
  }

  @Override
  public void cancelPopup(boolean useAnimation) {
    if (useAnimation && (animator != null)) {
      setVisible(false, null, 0, 0, 0, false);
    } else {
      dismiss();
    }
  }

  @Override
  public void dispose() {
    dispose(false);
  }

  public void dispose(boolean disposeviewer) {
    if (!dismissing) {
      dismiss();

      return;
    }

    if (target != null) {
      target.dispose(disposeviewer);
    }

    if (rootPane != null) {
      rootPane.dispose();
    }

    owner           = null;
    dismissListener = null;
    touchListener   = null;
    rootPane        = null;
    target          = null;

    if (listenerList != null) {
      listenerList.clear();
      listenerList = null;
    }
  }

  @Override
  public void hidePopup() {
    canceled = false;
    this.dismiss();
  }

  @Override
  public void onDismiss() {
    ((MainActivity) Platform.getAppContext().getActivity()).removePopupWindow(this);
    dismissing = true;

    try {
      if (dismissListener != null) {
        dismissListener.onDismiss();
      }

      dismissing = true;

      if (listenerList != null) {
        if (canceled) {
          fireCanceledEvent();
        }

        fireEvent(true);
      }
    } finally {
      dispose();
      dismissing = false;
    }
  }

  @Override
  @SuppressLint("ClickableViewAccessibility")
  public boolean onTouch(View view, MotionEvent me) {
    if (touchListener != null) {
      return touchListener.onTouch(view, me);
    }

    return false;
  }

  public void pack() {
    UIDimension d    = rootPane.getPreferredSize();
    UIDimension dmin = rootPane.getMinimumSize();
    UIDimension sd   = ScreenUtils.getUsableScreenSize();
    float       wd   = Math.min(d.width - dmin.width, ScreenUtils.PLATFORM_PIXELS_4);
    float       hd   = Math.min(d.height - dmin.height, ScreenUtils.PLATFORM_PIXELS_10);

    d.width  = Math.min(sd.width, d.width - wd);
    d.height = Math.min(sd.height, d.height - hd);

    LayoutParams lp = new LayoutParams(d.intWidth(), d.intHeight());

    rootPane.getView().setLayoutParams(lp);
    rootPane.setBounds(0, 0, d.width, d.height);
  }

  @Override
  public void removePopupMenuListener(iPopupMenuListener l) {
    if (listenerList != null) {
      listenerList.remove(iPopupMenuListener.class, l);
    }
  }

  public void repaint() {
    update();
  }

  public void show(View parent, int gravity, int x, int y, boolean dropDown) {
    PlatformHelper.hideVirtualKeyboard(parent);

    if (!sizeSet) {
      pack();
    }

    iPlatformBorder b = rootPane.getBorder();

    if (b != null) {
      UIInsets in = b.getBorderInsetsEx(null);

      rootPane.getView().setPadding(in.intLeft(), in.intTop(), in.intRight(), in.intBottom());
    }

    fireEvent(false);

    if (dropDown) {
      super.showAsDropDown(parent, x, y);
    } else {
      super.showAtLocation(parent, gravity, x, y);
    }

    ((MainActivity) Platform.getAppContext().getActivity()).addPopupWindow(this);
    if(timeout>0) {
      Platform.invokeLater(new Runnable() {
        
        @Override
        public void run() {
          if(isVisible()) {
            setVisible(false);
          }
        }
      }, timeout);
    }
  }

  @Override
  public void showAsDropDown(View anchor) {
    showAsDropDown(anchor, 0, 0);
  }

  @Override
  public void showAsDropDown(View anchor, int xoff, int yoff) {
    setVisible(true, anchor, 0, xoff, yoff, true);
  }

  @Override
  public void showAtLocation(View parent, int gravity, int x, int y) {
    setVisible(true, parent, gravity, x, y, false);
  }

  @Override
  public void showModalPopup() {
    modal = true;
    this.setWindowLayoutMode(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    this.setBackgroundDrawable(new ColorDrawable(0x88000000));

    FrameView l = new FrameView(rootPane.getView().getContext());

    l.setViewRenderType(RenderType.CENTERED);
    super.setContentView(l);
    l.addView(rootPane.getView());
    setVisible(true, Platform.getAppContext().getRootViewer().getContainerComponent().getView(), Gravity.FILL, 0, 0,
               false);
  }

  @Override
  public void showPopup() {
    showPopup(owner, x, y);
  }

  @Override
  public void showPopup(float x, float y) {
    showPopup(owner, x, y);
  }

  @Override
  public void showPopup(iPlatformComponent ref, float x, float y) {
    if (ref == null) {
      ref = owner;
    }

    if (ref == null) {
      showAtLocation(Platform.getAppContext().getRootViewer().getContainerComponent().getView(), Gravity.NO_GRAVITY,
                     UIScreen.snapToPosition(x), UIScreen.snapToPosition(y));
    } else {
      iPlatformComponent c = ref.getParent();
      UIPoint            loc;

      if (c != null) {
        switch(c.getOrientation()) {
          case VERTICAL_UP :
            loc = ref.getLocationOnScreen(null);
            y   += loc.y;
            x   += loc.x;
            showAtLocation(ref.getView(), Gravity.NO_GRAVITY, UIScreen.snapToPosition(x), UIScreen.snapToPosition(y));

            break;

          case VERTICAL_DOWN :
            loc = ref.getLocationOnScreen(null);
            y   += loc.y;
            x   += loc.x;
            y   += ref.getWidth();
            showAtLocation(ref.getView(), Gravity.NO_GRAVITY, UIScreen.snapToPosition(x), UIScreen.snapToPosition(y));

            break;

          default :
            showAsDropDown(ref.getView(), UIScreen.snapToPosition(x), UIScreen.snapToPosition(y));

            break;
        }
      }
    }
  }

  @Override
  public void setAnimator(iPlatformAnimator animator) {
    this.animator = animator;

    if (this.animator != null) {
      setAnimationStyle(0);
      visibleIsforward = animator.getDirection() == Direction.FORWARD;
    }
  }

  public void setBackgroundPainter(iBackgroundPainter painter) {
    UIComponentPainter.setBackgroundPainter(rootPane, painter);
  }

  @Override
  public void setBackgroundColor(UIColor bg) {
    rootPane.setBackground(bg);
  }

  @Override
  public void setComponentPainter(iPlatformComponentPainter painter) {
    rootPane.setComponentPainter(painter);
  }

  @Override
  public void setContent(iPlatformComponent component) {
    rootPane.setContent(component);
  }

  @Override
  public void setMovable(boolean moveble) {}

  @Override
  public void setOnDismissListener(OnDismissListener listener) {
    this.dismissListener = listener;
  }

  @Override
  public void setOptions(Map<String, String> options) {}

  @Override
  public void setPopupLocation(float x, float y) {
    if (isShowing()) {
      this.update(UIScreen.snapToPosition(x), UIScreen.snapToPosition(y), getWidth(), getHeight());
    } else {
      this.x = UIScreen.snapToPosition(x);
      this.y = UIScreen.snapToPosition(y);
    }
  }

  @Override
  public void setPopupOwner(iPlatformComponent component) {
    owner = component;
  }

  @Override
  public void setSize(float width, float height) {
    sizeSet = true;
    this.setHeight(UIScreen.snapToSize(height));
    this.setWidth(UIScreen.snapToSize(width));
    rootPane.setBounds(0, 0, width, height);
  }

  @Override
  public void setTimeout(int timeout) {
    this.timeout=timeout;
  }

  @Override
  public void setTitle(String title) {
    rootPane.setTitle(title);
  }

  @Override
  public void setTouchInterceptor(OnTouchListener l) {
    touchListener = l;
  }

  @Override
  public void setTransient(boolean isTransient) {
    this.isTransient = isTransient;

    if (isTransient) {
      super.setFocusable(false);
    }
  }

  @Override
  public iViewer setViewer(iViewer viewer) {
    return target.setViewer(viewer);
  }

  public void setVisible(boolean visible) {
    if (isShowing()) {
      if (!visible) {
        setVisible(false, null, 0, 0, 0, false);
      }
    } else {
      View v = (owner != null)
               ? owner.getView()
               : Platform.getContextRootViewer().getContainerComponent().getView();

      setVisible(true, v, Gravity.NO_GRAVITY, x, y, false);
    }
  }

  @Override
  public void getPreferredSize(UIDimension size) {
    if (rootPane != null) {
      rootPane.getPreferredSize(size);
    } else {
      size.setSize(0, 0);
    }
  }

  @Override
  public WindowPane getWindowPane() {
    return rootPane;
  }

  @Override
  public boolean isTransient() {
    return isTransient && !modal;
  }

  public boolean isVisible() {
    return isShowing();
  }

  protected void fireCanceledEvent() {
    if (listenerList == null) {
      return;
    }

    Object[]       listeners = listenerList.getListenerList();
    ExpansionEvent e         = new ExpansionEvent(this,ExpansionEvent.Type.WILL_COLLAPSE);

    for (int i = listeners.length - 2; i >= 0; i -= 2) {
      if (listeners[i] == iPopupMenuListener.class) {
        ((iPopupMenuListener) listeners[i + 1]).popupMenuCanceled(e);
      }
    }
  }

  protected void fireEvent(boolean dismissed) {
    if (!dismissed) {
      canceled = true;

      if (visiblityFired) {
        return;
      }

      visiblityFired = true;
    }

    if (listenerList == null) {
      return;
    }

    Object[]       listeners = listenerList.getListenerList();
    ExpansionEvent e         = new ExpansionEvent(this,dismissed ? ExpansionEvent.Type.WILL_COLLAPSE : ExpansionEvent.Type.WILL_EXPAND);

    for (int i = listeners.length - 2; i >= 0; i -= 2) {
      if (listeners[i] == iPopupMenuListener.class) {
        if (dismissed) {
          ((iPopupMenuListener) listeners[i + 1]).popupMenuWillBecomeInvisible(e);
        } else {
          ((iPopupMenuListener) listeners[i + 1]).popupMenuWillBecomeVisible(e);
        }
      }
    }
  }

  protected void init(Context context) {
    // final WindowFormsView v = new WindowFormsView(context, new
    // FormLayout("f:d:g", "f:d,f:d,f:d,f:d:g,f:d"));
    // rootPane = new WindowPaneEx(v);
    rootPane = new WindowPaneEx(new PopupWindowFormsView(context),this);
    super.setContentView(rootPane.getView());

    String targetName = "PopupWindow-" + Integer.toHexString(System.identityHashCode(this));

    super.setOnDismissListener(this);
    super.setTouchInterceptor(this);

    if (getWidth() == 0) {
      setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
    }

    if (getHeight() == 0) {
      setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
    }

    setAnimationStyle(-1);
    setTouchable(true);
    setFocusable(true);
    //setOutsideTouchable(true);
    setBackgroundDrawable(null);
    target = new WindowTarget(Platform.getAppContext(), targetName, rootPane, true);
    target.setPopupWindow(true);
  }

  protected void removeFromParent(View v) {
    if (v != null) {
      ViewParent p = v.getParent();

      if (p instanceof ViewGroup) {
        ((ViewGroup) p).removeView(v);
      }
    }
  }

  protected void setVisible(final boolean visible, View parent, int gravity, int x, int y, boolean dropDown) {
    if (visible == isVisible()) {
      return;
    }
    PlatformHelper.hideVirtualKeyboard(parent);
    if (animator == null) {
      if (visible) {
        show(parent, gravity, x, y, dropDown);
      } else {
        dismiss();
      }
    } else {
      final PopupWindowFormsView root = (PopupWindowFormsView) rootPane.getView();

      if (visible) {
        show(parent, gravity, x, y, dropDown);
      }

      aAnimator.setupTogglingAnimator(rootPane, animator, visible, visibleIsforward);
      root.animating = true;
      animator.addListener(new iAnimatorListener() {
        @Override
        public void animationStarted(iPlatformAnimator source) {}
        @Override
        public void animationEnded(iPlatformAnimator source) {
          source.removeListener(this);
          source.setDirection(visibleIsforward
                              ? Direction.FORWARD
                              : Direction.BACKWARD);    // reset
          // direction

          if (!visible) {
            dismiss();
          } else {
            root.animating = false;
            root.requestLayout();
            root.invalidate();
          }
        }
      });
      animator.animate(rootPane, null);
    }
  }

  public static class PopupWindowFormsView extends FormsView {
    boolean animating;
    int     left;
    int     top;
    public PopupWindowEx window;

    public PopupWindowFormsView(Context context) {
      super(context, WindowPane.createPaneFormLayout());
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
      if (animating) {
        setLeft(left);
        setTop(top);
      } else {
        super.onLayout(changed, l, t, r, b);
      }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
      if (animating) {
        left = getLeft();
        top  = getTop();
        setMeasuredDimension(getWidth(), getHeight());
      } else {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
      }
    }

    public void closeWindow() {
      if(window!=null) {
        window.dismiss();
      }
    }
    
    @Override
    public void dispose() {
      super.dispose();
      window=null;
    }
  }


  static class WindowPaneEx extends WindowPane {
    public WindowPaneEx(PopupWindowFormsView view, PopupWindowEx popupWindowEx) {
      super(view);
      view.window=popupWindowEx;
    }
    @Override
    public void setBounds(int x, int y, int w, int h) {
      sizeCache.minDirty             = true;
      sizeCache.minDirty             = true;
      sizeCache.resuestLayoutChecked = false;

      PopupWindowFormsView v = (PopupWindowFormsView) view;

      if (v.animating) {
        v.left = x;
        v.top  = y;
      } else {
        v.forceLayout();
        v.measure(MeasureSpec.makeMeasureSpec(w, MeasureSpec.EXACTLY),
                  MeasureSpec.makeMeasureSpec(h, MeasureSpec.EXACTLY));
      }

      v.layout(x, y, x + w, y + h);
    }

    @Override
    public void setLocation(float x, float y) {
      setBounds(x, y, getWidth(), getHeight());

      iPlatformBorder b = getBorder();

      if (b instanceof SharedLineBorder) {
        ((SharedLineBorder) b).repaintTopComponent();
      }
    }
  }


  public boolean isOutsideTouch(MotionEvent me) {
    int loc[] = new int[2];
    int x     = (int) me.getRawX();
    int y     = (int) me.getRawY();
    int w     = rootPane.getWidth();
    int h     = rootPane.getHeight();

    rootPane.getView().getLocationOnScreen(loc);

    return ((x < loc[0]) || (x > w + loc[0]) || (y < loc[1]) || (y > loc[1] + h));
  }
}
