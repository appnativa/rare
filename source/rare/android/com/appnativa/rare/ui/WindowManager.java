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
    this.mainFrame  = new Frame(a.getWindow(), null, true, WindowType.FRAME);
    workspaceTarget = new WorkspaceTarget(app, (Frame) mainFrame);
    ((Frame) mainFrame).setTarget(workspaceTarget);
    theTargets.put(iTarget.TARGET_WORKSPACE, workspaceTarget);

    if (componentCreator == null) {
      componentCreator = new ComponentFactory();
      componentCreator.setAppContext(appContext);
    }
  }

  @Override
  public void close() {
    if (mainFrame != null) {
      mainFrame.close();
    }
  }

  @Override
  public void configure(MainWindow cfg) {
    createScriptHandler(cfg);

    DataEvent event = new DataEvent(this, cfg);

    fireEvent(iConstants.EVENT_CREATED, event, true);
    configureStandardStuff(cfg);

    String  s  = cfg.decorated.spot_getAttribute("color");
    UIColor bg = (s == null)
                 ? ColorUtils.getBackground()
                 : ColorUtils.getColor(s);

    ((Frame)mainFrame).setBackground(bg);
    if ("false".equalsIgnoreCase(cfg.spot_getAttribute("opaque"))) {
      ((Window) mainFrame.getUIWindow()).setBackgroundDrawable(null);
    }

    iWidget tw = createTitleWidget(cfg);

    if (tw != null) {
      ((Frame) mainFrame).setTitleWidget(tw);
    }

    fireEvent(iConstants.EVENT_CONFIGURE, event, true);
  }

  @Override
  public iPopup createPopup(iWidget context) {
    WindowViewer w = (context == null)
                     ? Platform.getWindowViewer()
                     : context.getWindow();

    if (w == null) {
      w = Platform.getWindowViewer();
    }

    PopupWindowEx p = new PopupWindowEx(w.getAndroidContext());

    return p;
  }

  @Override
  public void onConfigurationChanged(boolean reset) {
    Activity ra = AppContext.getRootActivity();

    ((Frame) mainFrame).reset(ra.getWindow());

    if ((getWorkspaceViewer() != null)) {
      getWorkspaceViewer().onConfigurationChanged(reset);
      getWorkspaceViewer().update();
    }
  }

  @Override
  public void onConfigurationWillChange(Object newConfig) {
    ((Frame) mainFrame).onConfigurationWillChange((Configuration) newConfig);

    if ((getWorkspaceViewer() != null)) {
      getWorkspaceViewer().onConfigurationWillChange(newConfig);
    }
  }

  @Override
  public iPlatformComponentFactory getComponentCreator() {
    return componentCreator;
  }

  @Override
  public iContainer getRootViewer() {
    if (getScriptHandler() != null) {
      return getScriptHandler().getWindowViewer();
    }

    return null;
  }

  @Override
  public int getUsableScreenHeight() {
    return ((Frame) mainFrame).getUsableScreenHeight();
  }

  @Override
  public int getUsableScreenWidth() {
    return ((Frame) mainFrame).getUsableScreenWidth();
  }

  @Override
  public boolean isBackPressedHandled() {
    if ((appContext == null) || appContext.isShuttingDown()) {
      return false;
    }

    WindowViewer win = appContext.getWindowViewer();

    if ((win != null) && win.isBackPressedHandled()) {
      return true;
    }

    if ((getWorkspaceViewer() != null) && getWorkspaceViewer().isBackPressedHandled()) {
      return true;
    }

    return false;
  }

  @Override
  protected Frame createFrame(iWidget context, WindowType type, boolean modal, boolean transparent, boolean decorated) {
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
    }

    Frame frame = new Frame(d, null, type);

    frame.transparent = transparent;
    frame.undecorated = !decorated;

    return frame;
  }

  @Override
  protected aWidgetListener createWidgetListener(iWidget widget, Map map, iScriptHandler scriptHandler) {
    return new WidgetListener(widget, map, scriptHandler);
  }

  @Override
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

  @Override
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


  @Override
  protected void setWindowIconsEx(List<UIImageIcon> icons) {}
}
