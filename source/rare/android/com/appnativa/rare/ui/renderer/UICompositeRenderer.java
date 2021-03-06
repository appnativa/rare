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

package com.appnativa.rare.ui.renderer;

import android.content.Context;

import com.appnativa.rare.platform.android.ui.view.ViewGroupEx;
import com.appnativa.rare.ui.UIInsets;
import com.appnativa.rare.ui.iPlatformRenderingComponent;

public class UICompositeRenderer extends aCompositeRenderer {
  public UICompositeRenderer() {
    super();
  }

  public UICompositeRenderer(Context context) {
    this(new UILabelRenderer(context));
  }

  public UICompositeRenderer(iPlatformRenderingComponent rc) {
    super(rc);
  }

  public iPlatformRenderingComponent newCopy() {
    iPlatformRenderingComponent rc = (renderingComponent == null)
                                     ? new UILabelRenderer()
                                     : renderingComponent.newCopy();
    return setupNewCopy(new UICompositeRenderer(rc));
  }

  public void setBlockRequestLayout(boolean block) {
    ((ViewGroupEx) view).setBlockRequestLayout(block);

    if (renderingComponent != null) {
      renderingComponent.setBlockRequestLayout(block);
    }

    iconLabel.setBlockRequestLayout(block);
  }

  public void setMargin(int top, int right, int bottom, int left) {
    view.setPadding(left, top, right, bottom);
  }

  public void setMargin(UIInsets in) {
    if (in == null) {
      int d = 0;

      view.setPadding(d, d, d, d);
    } else {
      view.setPadding(in.intLeft(), in.intTop(), in.intRight(), in.intBottom());
    }
  }
}
