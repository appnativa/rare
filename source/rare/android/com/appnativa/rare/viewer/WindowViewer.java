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

package com.appnativa.rare.viewer;

import android.view.View;

import com.appnativa.rare.iPlatformAppContext;
import com.appnativa.rare.platform.android.ui.util.AndroidHelper.DragHandler;
import com.appnativa.rare.platform.android.ui.util.ImageHelper;
import com.appnativa.rare.platform.android.ui.util.ImageUtils;
import com.appnativa.rare.scripting.iScriptHandler;
import com.appnativa.rare.ui.Component;
import com.appnativa.rare.ui.Frame;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIImage;
import com.appnativa.rare.ui.UIImageIcon;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.iPlatformIcon;
import com.appnativa.rare.ui.iWindow;
import com.appnativa.rare.ui.painter.UIImagePainter;
import com.appnativa.rare.ui.painter.iImagePainter;
import com.appnativa.rare.ui.painter.iImagePainter.ScalingType;
import com.appnativa.rare.widget.BeanWidget;
import com.appnativa.rare.widget.PushButtonWidget;
import com.appnativa.rare.widget.iWidget;

import java.io.IOException;

import java.net.URL;

/**
 *
 * @author Don DeCoteau
 */
public class WindowViewer extends aWindowViewer implements iWindow {
  protected DragHandler dragHandler;

  public WindowViewer(iPlatformAppContext ctx, String name, Frame win, WindowViewer parent, iScriptHandler sh) {
    super(ctx, name, win, parent, sh);
    theWindow = win;
    win.setViewer(this);
    formComponent = dataComponent = win;
    widgetType    = WidgetType.Window;
    theWindow     = win;
  }

  /**
   * Adds the specified component as a dragger for the window
   *
   * @param c
   *          the component to add
   */
  public void addWindowDragger(iPlatformComponent c) {
    if (dragHandler == null) {
      dragHandler = new DragHandler();
    }

    ((View) c.getView()).setOnTouchListener(dragHandler);
  }

  public void addWindowDragger(iWidget widget) {
    addWindowDragger(widget.getContainerComponent());
  }

  /**
   * Creates an image painter for the specified image
   *
   * @param icon
   *          the image
   * @return the image painter
   */
  public iImagePainter createImagePainter(iPlatformIcon icon) {
    UIImage image = null;

    if (icon instanceof UIImageIcon) {
      image = ((UIImageIcon) icon).getImage();
    } else {
      image = ImageUtils.createImage(icon);
    }

    return new UIImagePainter(image);
  }

  /**
   * Creates a viewer for a view
   *
   * @param context
   *          the widget context
   * @param view
   *          the view
   * @return an instance of the viewer that was created
   */
  public iViewer createViewer(iWidget context, View view) {
    if (context == null) {
      context = this;
    }

    return new WidgetPaneViewer(this, new Component(view));
  }

  /**
   * Creates a widget for a view
   *
   * @param context
   *          the widget context
   * @param view
   *          the view
   * @return an instance of the widget that was created
   */
  public iWidget createWidget(iWidget context, View view) {
    if (context == null) {
      context = this;
    }

    return new BeanWidget(this, new Component(view));
  }

  @Override
  public void dispose() {
    if (dragHandler != null) {
      dragHandler.dispose();
      dragHandler = null;
    }

    super.dispose();
  }

  public void removeWindowDragger(iWidget widget) {
    if ((dragHandler != null) && (widget != null)) {
      View v = widget.getContainerComponent().getView();

      v.setOnTouchListener(null);
    }
  }

  public void toBack() {
    getAppContext().getActivity().moveTaskToBack(true);
  }

  public void toFront() {}

  public void setDefaultButton(PushButtonWidget widget) {}

  /**
   * Gets an image via a background thread
   *
   * @param url
   *          the url for the icon
   * @param size
   *          the size constraints
   * @param constraints
   *          delayed icon constraints 0=do nothing; 1=constrain width to the
   *          specified size 2=constrain height to the specified size
   *          3=constrain width and height to fit within the specified size
   *          (will maintain proportions) 4=constrain width and height to the
   *          specified size 5=constrain width and height to fill the specified
   *          size (will maintain proportions)
   *
   * @param st
   *          the type of scaling to do (null for bilinear)
   * @param bg
   *          the background color to use to fill empty space
   * @return the image
   * @throws IOException
   */
  public UIImage getDelayedImage(URL url, int size, int constraints, ScalingType st, UIColor bg) throws IOException {
    return ImageHelper.createImage(url, true, size, constraints, st, bg, 0);
  }
}
