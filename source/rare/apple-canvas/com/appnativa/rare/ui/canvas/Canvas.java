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

import com.appnativa.rare.platform.apple.ui.util.ImageUtils;
import com.appnativa.rare.ui.UIImage;
import com.appnativa.rare.ui.iPlatformGraphics;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.util.CharScanner;

import java.io.IOException;

public class Canvas extends aCanvas {
  public Canvas(iWidget w, boolean backgroundCanvas) {
    super(w, backgroundCanvas);
  }

  @Override
  public String toDataURL(Object... args) throws IOException {
    if ((theContext == null) || (width == 0) || (height == 0)) {
      return "data:,";
    }

    String type = "png";

    if ((args.length > 0) && (args[0] instanceof String)) {
      type = CharScanner.getPiece((String) args[0], '/', 1);

      if (!type.equals("png") &&!type.equals("jpg") &&!type.equals("jpeg")) {
        type = "png";
      }
    }

    UIImage           img = ImageUtils.createCompatibleImage(width, height);
    iPlatformGraphics g   = img.createGraphics();

    theContext.render(g);
    g.dispose();

    return "data:image/" + type + ";base64," + ImageUtils.base64String(img.getProxy(), type.equals("png"));
  }

  @Override
  protected iContext createContext(String type) {
    return new CanvasRenderingContext2D(this);
  }
}
