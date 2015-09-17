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

import com.appnativa.rare.net.ActionLink;
import com.appnativa.rare.spot.ImagePane;
import com.appnativa.rare.ui.ImagePanel;
import com.appnativa.rare.ui.UIImage;
import com.appnativa.rare.ui.UIImageHelper;
import com.appnativa.rare.ui.iPlatformImagePanel;

import java.io.IOException;

/**
 * A viewer that displays and image and provides tools
 * for manipulating the image
 *
 * @author     Don DeCoteau
 */
public class ImagePaneViewer extends aImagePaneViewer {

  /**
   * Constructs a new instance
   */
  public ImagePaneViewer() {
    this(null);
  }

  /**
   * Constructs a new instance
   *
   * @param parent the widget's parent
   */
  public ImagePaneViewer(iContainer parent) {
    super(parent);
  }

  @Override
  public void handleActionLink(ActionLink link, boolean deferred) {
    try {
      UIImage img = UIImageHelper.createImage(link.getURL(this), deferred);

      setImage(img);
    } catch(IOException ex) {
      getAppContext().getDefaultExceptionHandler().handleException(ex);
    }
  }

  @Override
  public void setUseSpinner(boolean spinner) {}

  @Override
  protected iPlatformImagePanel createPanel(ImagePane cfg) {
    //super viewer is supposed to set scaling type before calling create panel
    ImagePanel p = new ImagePanel(scalingType.isCached());

    p.setWidget(this);

    if (isDesignMode()) {
      p.setDefaultMinimumSize(50, 50, true);
    }

    return p;
  }
}
