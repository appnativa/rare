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

import com.appnativa.rare.platform.android.ui.NullDrawable;
import com.appnativa.rare.platform.android.ui.view.FormsView;
import com.appnativa.rare.platform.android.ui.view.ViewGroupEx;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIInsets;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.iPlatformRenderingComponent;
import com.jgoodies.forms.layout.FormLayout;

/**
 * A renderer that uses the JGoodies forms layout panel to render information
 *
 * @author Don DeCoteau
 */
public class UIFormsLayoutRenderer extends aFormsLayoutRenderer {
  public UIFormsLayoutRenderer(Context context) {
    super(new FormsPanelEx(new FormsView(context, new FormLayout())) {
      @Override
      public void revalidate() {}
      @Override
      public void setBackground(UIColor bg) {
        if (componentPainter != null) {
          super.setComponentPainter(null);
        }

        view.setBackground((bg == null)
                                   ? NullDrawable.getInstance()
                                   : bg.getDrawable());
        super.setBackground(bg);
      }
    });
  }

  public UIFormsLayoutRenderer(Context context, String columns, String rows) {
    super(new FormsPanelEx((new FormsView(context, new FormLayout(columns, rows)))) {
      @Override
      public void revalidate() {}
    });
    this.columns = columns;
    this.rows    = rows;
  }

  public iPlatformRenderingComponent newCopy() {
    if ((columns == null) && (rows == null)) {
      return new UIFormsLayoutRenderer(formsPanel.getView().getContext());
    }

    return setupNewCopy(new UIFormsLayoutRenderer(formsPanel.getView().getContext(), columns, rows));
  }

  public void setBlockRequestLayout(boolean block) {
    ((ViewGroupEx) formsPanel.getView()).setBlockRequestLayout(block);
  }

  public void setMargin(UIInsets in) {
    if (in == null) {
      int d = 0;

      formsPanel.getView().setPadding(d, d, d, d);
    } else {
      formsPanel.getView().setPadding(in.intLeft(), in.intTop(), in.intRight(), in.intBottom());
    }
  }

  public void setMargin(int top, int right, int bottom, int left) {
    formsPanel.getView().setPadding(left, top, right, bottom);
  }

  @Override
  protected iPlatformRenderingComponent createComponentRenderer(iPlatformComponent c) {
    return new UIComponentRenderer(c);
  }

  protected iPlatformRenderingComponent createLabelRenderer() {
    return Renderers.createLabelRenderer(formsPanel.getView().getContext());
  }

  protected aListItemRenderer createListItemRenderer() {
    return new ListItemRenderer();
  }

  protected iPlatformRenderingComponent createRenderer(String className) throws Exception {
    return Renderers.createRenderer(className, formsPanel.getView().getContext());
  }
}
