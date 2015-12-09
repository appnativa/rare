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

import java.io.IOException;
import java.net.URL;

import com.appnativa.rare.iPlatformAppContext;
import com.appnativa.rare.platform.apple.ui.DragHandler;
import com.appnativa.rare.platform.apple.ui.view.View;
import com.appnativa.rare.scripting.iScriptHandler;
import com.appnativa.rare.ui.Component;
import com.appnativa.rare.ui.Frame;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIImage;
import com.appnativa.rare.ui.UIImageIcon;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.iPlatformIcon;
import com.appnativa.rare.ui.iWindow;
import com.appnativa.rare.ui.iWindowManager.iFrame;
import com.appnativa.rare.ui.painter.UIImagePainter;
import com.appnativa.rare.ui.painter.iImagePainter;
import com.appnativa.rare.ui.painter.iImagePainter.ScalingType;
import com.appnativa.rare.widget.BeanWidget;
import com.appnativa.rare.widget.PushButtonWidget;
import com.appnativa.rare.widget.iWidget;

/**
 *
 * @author Don DeCoteau
 */
public class WindowViewer extends aWindowViewer implements iWindow {
  protected DragHandler dragHandler;

  public WindowViewer(iPlatformAppContext ctx, String name, iFrame win, WindowViewer parent, iScriptHandler sh) {
    super(ctx, name, win, parent, sh);
    theWindow = win;
    formComponent = dataComponent = win.getComponent();
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
      dragHandler = new DragHandler(this);
    }

    dragHandler.addAsWindowDragger(c);
  }

  @Override
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
  @Override
  public iImagePainter createImagePainter(iPlatformIcon icon) {
    UIImage image = null;

    if (icon instanceof UIImageIcon) {
      image = ((UIImageIcon) icon).getUIImage();
    } else {
      return null;
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

    return new BeanWidget(context.getContainerViewer(), new Component(view));
  }

  @Override
  public void dispose() {
    if (dragHandler != null) {
      dragHandler.dispose();
      dragHandler = null;
    }

    super.dispose();
  }

  @Override
  public void removeWindowDragger(iWidget widget) {
    if ((dragHandler != null) && (widget != null)) {
      dragHandler.removeAsWindowDragger(widget.getContainerComponent());
    }
  }

  @Override
  public void setDefaultButton(PushButtonWidget widget) {
    if (theWindow instanceof Frame) {
      ((Frame) theWindow).setDefaultButton(widget.getContainerComponent());
    }
  }

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
  @Override
  public UIImage getDelayedImage(URL url, int size, int constraints, ScalingType st, UIColor bg) throws IOException {
    return null;    // UIImageHelper.createImage(url, true, false, size, constraints, st, bg, 0);
  }
}
