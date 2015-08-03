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

package com.appnativa.rare.ui;

import android.app.Activity;

import android.content.res.Configuration;

import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;

import com.appnativa.rare.ErrorInformation;
import com.appnativa.rare.Platform;
import com.appnativa.rare.iConstants;
import com.appnativa.rare.iPlatformAppContext;
import com.appnativa.rare.platform.android.AppContext;
import com.appnativa.rare.platform.android.ui.util.AndroidHelper;
import com.appnativa.rare.platform.android.ui.view.DialogEx;
import com.appnativa.rare.platform.android.ui.view.PopupWindowEx;
import com.appnativa.rare.scripting.iScriptHandler;
import com.appnativa.rare.spot.MainWindow;
import com.appnativa.rare.ui.effects.iPlatformAnimator;
import com.appnativa.rare.ui.event.DataEvent;
import com.appnativa.rare.viewer.WindowViewer;
import com.appnativa.rare.viewer.iContainer;
import com.appnativa.rare.viewer.iTarget;
import com.appnativa.rare.widget.aPlatformWidget;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.util.SNumber;

import java.util.List;
import java.util.Map;

/**
 *
 * @author Don DeCoteau
 */
public class WindowManager extends aWindowManager {
  public static int undecoratedStyle;

  public WindowManager(AppContext app, Activity a) {
    super(app);
    this.mainFrame  = new Frame(a.getWindow(), null, true);
    workspaceTarget = new WorkspaceTarget(app, (Frame) mainFrame);
    ((Frame) mainFrame).setTarget(workspaceTarget);
    theTargets.put(iTarget.TARGET_WORKSPACE, workspaceTarget);

    if (componentCreator == null) {
      componentCreator = new ComponentFactory();
      componentCreator.setAppContext(appContext);
    }
  }

  public void close() {
    if (mainFrame != null) {
      mainFrame.close();
    }
  }

  public void configure(MainWindow cfg) {
    createScriptHandler(cfg);

    DataEvent event = new DataEvent(this, cfg);

    fireEvent(iConstants.EVENT_CREATED, event, true);
    configureStandardStuff(cfg);

    if ("false".equalsIgnoreCase(cfg.spot_getAttribute("opaque"))) {
      ((Window) mainFrame.getUIWindow()).setBackgroundDrawable(null);
    }

    fireEvent(iConstants.EVENT_CONFIGURE, event, true);
  }

  public iPopup createPopup(iWidget context) {
    PopupWindowEx p = new PopupWindowEx(AppContext.getAndroidContext());

    return p;
  }

  public void onConfigurationChanged(boolean reset) {
    Activity  ra   = AppContext.getRootActivity();
    Animation a    = null;
    Object    aa   = Platform.getUIDefaults().get("Rare.animation.orientation");
    String    anim = null;

    if (aa instanceof Animation) {
      a = (Animation) aa;
    } else if (aa instanceof String) {
      anim = (String) aa;
    }

    if ((anim != null) && (anim.length() > 0)) {
      try {
        iPlatformAnimator pa = Platform.getAppContext().getResourceAsAnimator(anim);

        a = (pa == null)
            ? null
            : pa.getAnimation();
      } catch(Throwable ignore) {
        Platform.ignoreException("Setting orientation animation", ignore);
      }
    }

    ((Frame) mainFrame).reset(ra.getWindow(), a);

    if ((getWorkspaceViewer() != null)) {
      getWorkspaceViewer().onConfigurationChanged(reset);
      getWorkspaceViewer().update();
    }
  }

  public void onConfigurationWillChange(Object newConfig) {
    ((Frame) mainFrame).onConfigurationWillChange((Configuration) newConfig);

    if ((getWorkspaceViewer() != null)) {
      getWorkspaceViewer().onConfigurationWillChange(newConfig);
    }
  }

  public iPlatformComponentFactory getComponentCreator() {
    return componentCreator;
  }

  public iContainer getRootViewer() {
    if (getScriptHandler() != null) {
      return getScriptHandler().getWindowViewer();
    }

    return null;
  }

  public int getUsableScreenHeight() {
    return ((Frame) mainFrame).getUsableScreenHeight();
  }

  public int getUsableScreenWidth() {
    return ((Frame) mainFrame).getUsableScreenWidth();
  }

  public boolean isBackPressedHandled() {
    if ((appContext == null) || appContext.isShuttingDown()) {
      return false;
    }

    Activity ra = AppContext.getRootActivity();

    ((Frame) mainFrame).reset(ra.getWindow(), null);

    WindowViewer win = appContext.getWindowViewer();

    if ((win != null) && win.isBackPressedHandled()) {
      return true;
    }

    if ((getWorkspaceViewer() != null) && getWorkspaceViewer().isBackPressedHandled()) {
      return true;
    }

    return false;
  }

  public iWindow createWindow(iWidget context, Map options) {
    String  title     = null;
    String  target    = null;
    boolean decorated = true;

    if (options != null) {
      title  = (String) options.get("title");
      target = (String) options.get("target");

      Object o = options.get("decorated");

      if (o != null) {
        if (o instanceof Boolean) {
          decorated = ((Boolean) o).booleanValue();
        } else {
          decorated = "true".equalsIgnoreCase(o.toString());
        }
      }
    }

    DialogEx d;
    boolean  rdecorated = Platform.getUIDefaults().getBoolean("Rare.Dialog.useRuntimeDecorations", false);

    if (decorated &&!rdecorated) {
      d = new DialogEx(((aPlatformWidget) context).getAndroidContext());
    } else {
      if (undecoratedStyle == 0) {
        Integer i = AppContext.getResourceId(((aPlatformWidget) context).getAndroidContext(),
                      "style/UndecoratedDialog");

        if (i != null) {
          undecoratedStyle = i;
        }
      }

      d = new DialogEx(((aPlatformWidget) context).getAndroidContext(), undecoratedStyle);
      d.requestWindowFeature(Window.FEATURE_NO_TITLE);

      if (decorated) {
        d.setUsRuntimeDecorations(true);
      }
    }

    if (title == null) {
      title = getTitle();
    }

    d.setTitle(title);

    Object o = (options == null)
               ? null
               : options.get("transient");

    if (o instanceof Boolean) {
      d.setCanceledOnTouchOutside((Boolean) o);
    } else if ("true".equals(o)) {
      d.setCanceledOnTouchOutside(true);
    }

    WindowViewer parent = Platform.getWindowViewer(context);

    if (parent == null) {
      parent = (WindowViewer) appContext.getWindowViewer();
    }

    iScriptHandler sh    = parent.getScriptHandler();
    Frame          frame = new Frame(d, target);

    if (options != null) {
      //Utils.setupWindowOptions(frame, options);
    }

    WindowViewer windowViewer = new WindowViewer(appContext, frame.getTargetName(), frame, parent, sh);

    frame.setViewer(windowViewer);
    sh.setScriptingContext(windowViewer, null, null, null, true);

    return windowViewer;
  }

  protected aWidgetListener createWidgetListener(iWidget widget, Map map, iScriptHandler scriptHandler) {
    return new WidgetListener(widget, map, scriptHandler);
  }

  protected void handleCustomProperties(MainWindow cfg, Map<String, Object> properties) {
    String s = (String) properties.remove("config.layout.android");

    if ((s != null) && (s.length() > 0)) {
      ViewGroup v = (ViewGroup) AndroidHelper.getResourceComponentView(s);

      if (v != null) {
        ((Frame) mainFrame).setRootView(v);
      }
    } else {
      s = (String) properties.remove("config.layout.android_beanClass");

      if ((s != null) && (s.length() > 0)) {
        try {
          ViewGroup v = (ViewGroup) AndroidHelper.getView(s);

          if (v != null) {
            s = (String) properties.remove("android:id");

            if (s != null) {
              int n = SNumber.intValue(s);

              if (n != 0) {
                v.setId(n);
              }
            }

            ((Frame) mainFrame).setRootView(v);
          }
        } catch(Exception e) {
          throw new RuntimeException(e);
        }
      }
    }

    super.handleCustomProperties(cfg, properties);
  }

  protected void showErrorDialog(Throwable e) {
    WaitCursorHandler.stopWaitCursor(null, true);

    ErrorInformation ei = new ErrorInformation(e);

    ei.setTitle("Unhandler Error");
    AlertPanel.showErrorDialog(ei);
  }

  static class WorkspaceTarget extends UITarget {
    public WorkspaceTarget(iPlatformAppContext app, Frame f) {
      super(app, iTarget.TARGET_WORKSPACE, f, false);
    }
  }


  protected void setWindowIconsEx(List<UIImageIcon> icons) {}
}
