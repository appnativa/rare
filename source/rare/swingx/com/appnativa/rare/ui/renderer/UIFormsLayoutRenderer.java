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

import java.awt.Dimension;
import java.awt.Rectangle;

import com.appnativa.rare.iConstants;
import com.appnativa.rare.platform.swing.ui.view.FormsView;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.iPlatformRenderingComponent;
import com.appnativa.rare.ui.painter.iPlatformComponentPainter;
import com.google.j2objc.annotations.Weak;
import com.appnativa.jgoodies.forms.layout.FormLayout;

/**
 * A renderer that uses the JGoodies forms layout panel to render information
 *
 * @author Don DeCoteau
 */
public class UIFormsLayoutRenderer extends aFormsLayoutRenderer {
  public UIFormsLayoutRenderer() {
    super(new FormsPanelEx(new FormsViewEx(new FormLayout())) {
      @Override
      public void setBackground(UIColor bg) {
        if (componentPainter != null) {
          super.setComponentPainter(null);
        }

        view.setBackground((bg == null)
                           ? null
                           : bg);
      }
      @Override
      protected void getPreferredSizeEx(UIDimension size, float maxWidth) {
        super.getPreferredSizeEx(size, maxWidth);

        Number i = (Number) getClientProperty(iConstants.RARE_HEIGHT_MIN_VALUE);

        if ((i != null) && (i.intValue() > size.height)) {
          size.height = i.intValue();
        }
      }
    });
    ((FormsViewEx) formsPanel.getView()).renderer = this;
  }

  public UIFormsLayoutRenderer(String columns, String rows) {
    super(new FormsPanelEx((new FormsViewEx(new FormLayout(columns, rows)))));
    this.columns                                  = columns;
    this.rows                                     = rows;
    ((FormsViewEx) formsPanel.getView()).renderer = this;
  }

  @Override
  public iPlatformRenderingComponent newCopy() {
    if ((columns == null) && (rows == null)) {
      return new UIFormsLayoutRenderer();
    }

    UIFormsLayoutRenderer flr = new UIFormsLayoutRenderer(columns, rows);

    setupNewCopy(flr);

    return flr;
  }

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
    return new ListItemRenderer(null, false);
  }

  @Override
  protected iPlatformRenderingComponent createRenderer(String className) throws Exception {
    return Renderers.createRenderer(className);
  }

  protected static class FormsViewEx extends FormsView {
    @Weak
    UIFormsLayoutRenderer renderer;

    public FormsViewEx() {
      super();
    }

    public FormsViewEx(FormLayout layout) {
      super(layout);
    }

    @Override
    public final void repaint() {}

    @Override
    public final void repaint(Rectangle r) {}

    @Override
    public final void repaint(long tm, int x, int y, int width, int height) {}

    @Override
    public void validate() {
      renderer.layout(getWidth(), getHeight());
    }

    @Override
    public Dimension getPreferredSize() {
      Dimension d = super.getPreferredSize();
      Number    i = (Number) renderer.formsPanel.getClientProperty(iConstants.RARE_WIDTH_FIXED_VALUE);

      if ((i != null) && (i.intValue() > 0)) {
        d.width = i.intValue();
      }

      d.height += 8;
      i        = (Number) getClientProperty(iConstants.RARE_HEIGHT_MIN_VALUE);

      if ((i != null) && (i.intValue() > d.height)) {
        d.height = i.intValue();
      }

      return d;
    }
  }


  @Override
  public void setComponentPainter(iPlatformComponentPainter cp) {
    formsPanel.setComponentPainterEx(cp);
  }

  @Override
  public void prepareForReuse(int row, int column) {
    setComponentPainter(null);
    formsPanel.getView().setBackground(null);
  }
}
