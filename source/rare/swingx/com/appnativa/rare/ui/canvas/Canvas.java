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

import com.appnativa.rare.platform.swing.ui.util.Java2DUtils;
import com.appnativa.rare.platform.swing.ui.util.SwingGraphics;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.util.Base64;
import com.appnativa.util.CharScanner;
import com.appnativa.util.Helper;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

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

      if (!type.equals("png") &&!type.equals("jpg")
          && (Helper.indexOfEquals(ImageIO.getWriterFormatNames(), type) == -1)) {
        type = "png";
      }
    }

    BufferedImage img = Java2DUtils.createCompatibleImage(width, height);
    Graphics2D    g   = img.createGraphics();
    SwingGraphics sg  = new SwingGraphics(g);

    theContext.render(sg);
    sg.dispose();

    ByteArrayOutputStream out = new ByteArrayOutputStream(width * height * 4);

    ImageIO.write(img, type, out);

    return "data:image/" + type + ";base64," + Base64.encode(out.toByteArray());
  }

  @Override
  protected iContext createContext(String type) {
    return new CanvasRenderingContext2D(this);
  }
}
