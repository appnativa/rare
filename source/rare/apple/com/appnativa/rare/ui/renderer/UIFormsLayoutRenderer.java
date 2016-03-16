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

import java.util.Iterator;

import com.appnativa.jgoodies.forms.layout.FormLayout;
import com.appnativa.rare.platform.apple.ui.view.FormsView;
import com.appnativa.rare.platform.apple.ui.view.View;
import com.appnativa.rare.ui.RenderableDataItem.HorizontalAlign;
import com.appnativa.rare.ui.RenderableDataItem.VerticalAlign;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.iPlatformRenderingComponent;
import com.appnativa.rare.ui.border.UICompoundBorder;
import com.appnativa.rare.ui.border.UIEmptyBorder;

/**
 * A renderer that uses the JGoodies forms layout panel to render information
 *
 * @author Don DeCoteau
 */
public class UIFormsLayoutRenderer extends aFormsLayoutRenderer {
  UICompoundBorder        cborder;
  protected UIEmptyBorder paddingBorder;

  public UIFormsLayoutRenderer() {
    super(new FormsPanelEx(new FormsView(new FormLayout())) {
      @Override
      public void setBackground(UIColor bg) {
        if (view.getComponentPainter() != null) {
          super.setComponentPainter(null);
        }

        view.setBackgroundColorEx(bg);
      }
    });
    ((FormsPanelEx)formsPanel).renderer=this;
  }

  public UIFormsLayoutRenderer(String columns, String rows) {
    super(new FormsPanelEx((new FormsView(new FormLayout(columns, rows)))));
    this.columns = columns;
    this.rows    = rows;
  }

  @Override
  public Object createNewNativeView() {
    return FormsView.createProxy();
  }

  @Override
  public void dispose() {
    if (renderingComponents != null) {
      for (iPlatformRenderingComponent rc : renderingComponents.values()) {
        rc.dispose();
      }
    }

    super.dispose();
  }

  @Override
  public void clearRenderer() {
    super.clearRenderer();
    formsPanel.getView().clearVisualState();
  }

  @Override
  public iPlatformRenderingComponent newCopy() {
    if ((columns == null) && (rows == null)) {
      return new UIFormsLayoutRenderer();
    }
    return setupNewCopy(new UIFormsLayoutRenderer(columns, rows));
  }

  @Override
  public void prepareForReuse(int row, int column) {
    formsPanel.getView().clearVisualState();

    Iterator it = renderingComponents.values().iterator();

    while(it.hasNext()) {
      iPlatformRenderingComponent rc = ((iPlatformRenderingComponent) it.next());

      rc.prepareForReuse(row, column);
    }
  }

  @Override
  public void setAlignment(HorizontalAlign ha, VerticalAlign va) {}

  @Override
  public void setNativeView(Object proxy) {
    formsPanel.getView().setProxy(proxy);
  }

  @Override
  public void setRenderingView(View view) {}

  @Override
  protected iPlatformRenderingComponent createComponentRenderer(iPlatformComponent c) {
    return new UIComponentRenderer(c);
  }

  @Override
  protected iPlatformRenderingComponent createLabelRenderer() {
    return Renderers.createLabelRenderer();
  }

  @Override
  protected aListItemRenderer createListItemRenderer() {
    return new ListItemRenderer();
  }

  @Override
  protected iPlatformRenderingComponent createRenderer(String className) throws Exception {
    return Renderers.createRenderer(className);
  }
}
