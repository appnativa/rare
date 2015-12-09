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
import android.view.ViewGroup;

import com.appnativa.rare.platform.android.ui.util.AndroidHelper;
import com.appnativa.rare.platform.android.ui.view.PopupWindowEx;
import com.appnativa.rare.spot.Viewer;
import com.appnativa.rare.ui.Container;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.iPopup;
import com.appnativa.rare.ui.print.iPageSetup;
import com.appnativa.rare.widget.iWidget;

/**
 * Base class for viewers
 *
 * @version 0.3, 2007-05-14
 * @author Don DeCoteau
 */
public abstract class aPlatformViewer extends aViewer {

  /**
   * Constructs a new instance
   *
   * @param fv
   *          the parent
   */
  public aPlatformViewer(iContainer parent) {
    super(parent);
  }

  @Override
  public iPageSetup createPageSetup() {
    return null;
  }

  @Override
  protected iPopup createPopup(int width, int height) {
    return new PopupWindowEx(getContainerView().getContext(), width, height);
  }

  @Override
  protected void configureEx(Viewer cfg, boolean border, boolean textMenus, boolean margin) {
    super.configureEx(cfg, border, textMenus, margin);

    iPlatformComponent c = formComponent;
    String             s = (String) getCustomProperty("config.layout.android");

    if (s != null) {
      ViewGroup v = (ViewGroup) AndroidHelper.getResourceComponentView(s);

      if (v != null) {
        Container cc = new Container(v);

        cc.add(c);
        c = cc;
      }
    }
  }

  /**
   * Gets the container view for the widget
   *
   * @param w
   *          the widget
   * @return the container view for the widget
   */
  protected static View getView(iWidget w) {
    if (w == null) {
      return null;
    }

    return w.getContainerComponent().getView();
  }
}
