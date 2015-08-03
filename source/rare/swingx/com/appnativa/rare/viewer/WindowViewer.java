/*
 * @(#)WindowViewer.java   2013-12-18
 * 
 * Copyright (c) appNativa Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.viewer;

import java.io.IOException;
import java.net.URL;

import javax.swing.JComponent;

import com.appnativa.rare.iPlatformAppContext;
import com.appnativa.rare.platform.swing.ui.DragHandler;
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

/**
 *
 * @author Don DeCoteau
 */
public class WindowViewer extends aWindowViewer implements iWindow {
  protected DragHandler dragHandler;

  public WindowViewer(iPlatformAppContext ctx, String name, iWindow win, WindowViewer parent, iScriptHandler sh) {
    super(ctx, name, win, parent, sh);
    theWindow = win;
    formComponent=dataComponent=win.getComponent();
    widgetType = WidgetType.Window;
    theWindow  = win;
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
  public iViewer createViewer(iWidget context, JComponent view) {
    if (context == null) {
      context = this;
    }

    Component        comp = new Component(view);
    return new WidgetPaneViewer(this, comp);
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
  public iWidget createWidget(iWidget context, JComponent view) {
    if (context == null) {
      context = this;
    }

    Component  comp = new Component(view);
    BeanWidget w    = new BeanWidget( context.getContainerViewer(), comp);

    w.setParent(context.getContainerViewer());

    return w;
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
    if(theWindow instanceof Frame) {
      ((Frame)theWindow).setDefaultButton(widget);
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
