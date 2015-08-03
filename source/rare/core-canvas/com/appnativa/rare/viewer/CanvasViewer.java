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

import com.appnativa.rare.Platform;
import com.appnativa.rare.iConstants;
import com.appnativa.rare.spot.Viewer;
import com.appnativa.rare.ui.UIImage;
import com.appnativa.rare.ui.canvas.Canvas;
import com.appnativa.rare.ui.canvas.CanvasComponent;
import com.appnativa.rare.ui.canvas.iCanvas;
import com.appnativa.rare.ui.canvas.iCanvasComponent;
import com.appnativa.rare.ui.canvas.iContext;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.painter.iImagePainter.ScalingType;

import java.io.IOException;

/**
 * A viewer representing an HTML 5 compliant canvas
 *
 * @author Don DeCoteau
 */
public class CanvasViewer extends aPlatformViewer implements iCanvas {
  protected iCanvasComponent canvasComponent;

  /**
   * The real canvas object.
   */
  protected iCanvas   realCanvas;
  private String      contextType = "2d";
  private ScalingType scalingType = ScalingType.BILINEAR;

  /**
   * Creates a new instance
   */
  public CanvasViewer() {
    this(null);
  }

  /**
   * Creates a new instance
   *
   * @param parent the parent
   */
  public CanvasViewer(iContainer parent) {
    super(parent);
  }

  @Override
  public void clearContents() {
    if (realCanvas != null) {
      realCanvas.clear();
    }
  }

  @Override
  public void configure(Viewer vcfg) {
    canvasComponent = new CanvasComponent(this);
    dataComponent   = formComponent = canvasComponent.getPlatformComponent();
    configureEx(vcfg, true, false, true);

    if (vcfg instanceof com.appnativa.rare.spot.Canvas) {
      com.appnativa.rare.spot.Canvas cfg = (com.appnativa.rare.spot.Canvas) vcfg;

      scalingType = ScalingType.valueOf(cfg.scaling.stringValue().toUpperCase());
    }

    fireConfigureEvent(vcfg, iConstants.EVENT_CONFIGURE);
  }

  @Override
  public void dispose() {
    if (realCanvas != null) {
      realCanvas.dispose();
    }
  }

  @Override
  public String toDataURL(Object... args) throws IOException {
    return realCanvas.toDataURL(args);
  }

  @Override
  public void setHeight(int height) {
    setSize(formComponent.getWidth(), height);
  }

  public void setSize(int width, int height) {
    formComponent.setSize(width, height);
  }

  @Override
  public void setSize(int width, int height, boolean clear) {
    formComponent.setSize(width, height);

    if (clear && (realCanvas != null)) {
      realCanvas.setSize(width, height, clear);
    }
  }

  @Override
  public void setWidth(int width) {
    setSize(width, formComponent.getHeight());
  }

  @Override
  public iPlatformComponent getCanvasComponent() {
    return dataComponent;
  }

  @Override
  public iContext getContext(String id) {
    iContext ctx = null;

    if (realCanvas == null) {
      realCanvas = new Canvas(this, true);
      ctx        = realCanvas.getContext(id);

      if (scalingType != null) {
        ctx.setScalingType(scalingType);
      }
    }

    contextType = id;
    ctx         = (ctx == null)
                  ? realCanvas.getContext(id)
                  : ctx;
    canvasComponent.setContext(ctx);

    return ctx;
  }

  public String getContextType() {
    return contextType;
  }

  @Override
  public UIImage getImage(boolean copy) {
    return (realCanvas == null)
           ? null
           : realCanvas.getImage(copy);
  }

  protected static void registerForUse() {
    Platform.getAppContext().registerWidgetClass(Platform.getSPOTName(Canvas.class), CanvasViewer.class);
  }
}
