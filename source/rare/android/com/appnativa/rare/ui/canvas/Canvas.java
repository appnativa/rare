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

import android.graphics.Bitmap;

import com.appnativa.rare.platform.android.ui.util.AndroidGraphics;
import com.appnativa.rare.platform.android.ui.util.ImageUtils;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.util.Base64;
import com.appnativa.util.CharScanner;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Canvas extends aCanvas {
  public Canvas(iWidget w, boolean backgroundCanvas) {
    super(w, backgroundCanvas);
  }

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

    Bitmap          img = ImageUtils.createCompatibleBitmap((int) width, (int) height);
    AndroidGraphics sg  = new AndroidGraphics(img);

    theContext.render(sg);
    sg.dispose();

    ByteArrayOutputStream out = new ByteArrayOutputStream((int) (width * height * 4));

    try {
      if (type.equals("png")) {
        img.compress(Bitmap.CompressFormat.PNG, 90, out);
      } else {
        img.compress(Bitmap.CompressFormat.JPEG, 90, out);
      }
    } catch(Exception e) {
      e.printStackTrace();
    } finally {
      try {
        out.close();
      } catch(Throwable ignore) {}
    }

    return "data:image/" + type + ";base64," + Base64.encode(out.toByteArray());
  }

  @Override
  protected iContext createContext(String type) {
    return new CanvasRenderingContext2D(this);
  }
}
