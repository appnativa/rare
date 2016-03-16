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

package com.appnativa.rare.ui.canvas;

import com.appnativa.rare.ui.UIImage;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.painter.UICompoundPainter;
import com.appnativa.rare.ui.painter.iPlatformPainter;
import com.appnativa.rare.viewer.CanvasViewer;
import com.appnativa.rare.widget.iWidget;

import java.io.IOException;

/**
 * A class representing an HTML 5 complaint canvas
 *
 * @author Don DeCoteau
 */
public abstract class aCanvas implements iCanvas {
  protected boolean  backgroundCanvas;
  protected int      height;
  protected iContext theContext;
  protected iWidget  theWidget;
  protected int      width;

  /**
   * Creates a canvas backed by a widget.
   * The canvas will become a painter for the widget
   *
   * @param w the widget
   * @param backgroundCanvas true for a background overlay painter; false for a foreground overlay painter
   */
  public aCanvas(iWidget w, boolean backgroundCanvas) {
    theWidget             = w;
    this.backgroundCanvas = backgroundCanvas;

    if (w != null) {
      if (w.getContainerComponent() != null) {
        width  = w.getWidth();
        height = w.getHeight();
      }
    }
  }

  @Override
  public void clear() {
    if (theContext != null) {
      theContext.clear();
    }
  }

  @Override
  public void dispose() {
    if (theWidget != null) {
      uninstall();
    }

    if (theContext != null) {
      theContext.dispose();
    }

    theWidget  = null;
    theContext = null;
  }

  @Override
  public void repaint() {
    if ((theWidget != null) &&!theWidget.isDisposed()) {
      theWidget.repaint();
    }
  }

  @Override
  public abstract String toDataURL(Object... args) throws IOException;

  @Override
  public void setHeight(int height) {
    setSize(width, height, true);
  }

  @Override
  public void setSize(int width, int height, boolean clear) {
    if (!clear && (width == this.width) && (height == this.height)) {
      return;
    }

    this.width  = width;
    this.height = height;

    if (theContext != null) {
      theContext.setSize(width, height);
    }
  }

  @Override
  public void setWidth(int width) {
    setSize(width, height, true);
  }

  @Override
  public iPlatformComponent getCanvasComponent() {
    if (theWidget != null) {
      return theWidget.getDataComponent();
    }

    return null;
  }

  public void updateSize() {
    iPlatformComponent c = getCanvasComponent();

    if (c != null) {
      setSize(c.getWidth(), c.getHeight(), false);
    }
  }

  @Override
  public iContext getContext(String type) {
    if (type == null) {
      return theContext;
    }

    if (theContext == null) {
      theContext = createContext(type);

      if (theWidget != null) {
        install(theWidget);
      }
    }

    return theContext;
  }

  @Override
  public int getHeight() {
    return height;
  }

  @Override
  public UIImage getImage(boolean copy) {
    return (theContext == null)
           ? null
           : theContext.getImage(copy);
  }

  @Override
  public int getWidth() {
    return width;
  }

  protected abstract iContext createContext(String type);

  /**
   * Un-installs the canvas from the widget it is currently installed on
   */
  public void uninstall() {
    if ((theWidget != null) &&!(theWidget instanceof CanvasViewer)) {
      iPlatformPainter  op = null;
      iPlatformPainter  p  = backgroundCanvas
                             ? theWidget.getBackgroundOverlayPainter()
                             : theWidget.getOverlayPainter();
      UICompoundPainter cp;

      if (p instanceof UICompoundPainter) {
        cp = (UICompoundPainter) p;
        op = cp.getFirstPainter();
        p  = cp.getSecondPainter();
      }

      if (p == theContext.getPainter()) {
        if (backgroundCanvas) {
          theWidget.setBackgroundOverlayPainter(op);
        } else {
          theWidget.setOverlayPainter(op);
        }
      }
    }
  }

  protected void install(iWidget w) {
    if (w instanceof CanvasViewer) {
      return;
    }

    if (w != theWidget) {
      uninstall();
    }

    theWidget = w;

    if (theWidget != null) {
      iPlatformPainter p = backgroundCanvas
                           ? theWidget.getBackgroundOverlayPainter()
                           : theWidget.getOverlayPainter();

      if (p != null) {
        p = new UICompoundPainter(p, theContext.getPainter());
      } else {
        p = theContext.getPainter();
      }

      if (backgroundCanvas) {
        theWidget.setBackgroundOverlayPainter(p);
      } else {
        theWidget.setOverlayPainter(p);
      }
    }
  }
}
