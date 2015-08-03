/*
 * @(#)UIFormsLayoutRenderer.java   2012-02-08
 *
 * Copyright (c) 2007-2009 appNativa Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.ui.renderer;

import java.awt.Dimension;
import java.awt.Rectangle;

import com.appnativa.rare.iConstants;
import com.appnativa.rare.platform.swing.ui.view.FormsView;
import com.appnativa.rare.ui.FormsPanel;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.iPlatformRenderingComponent;
import com.appnativa.rare.ui.painter.iPlatformComponentPainter;
import com.google.j2objc.annotations.Weak;
import com.jgoodies.forms.layout.FormLayout;

/**
 * A renderer that uses the JGoodies forms layout panel to render information
 *
 * @author Don DeCoteau
 */
public class UIFormsLayoutRenderer extends aFormsLayoutRenderer {
  public UIFormsLayoutRenderer() {
    super(new FormsPanel(new FormsViewEx(new FormLayout())) {
      @Override
      public void setBackground(UIColor bg) {
        if (componentPainter != null) {
          super.setComponentPainter(null);
        }
        view.setBackground(bg == null ? null : bg);
      }

      @Override
      protected void getPreferredSizeEx(UIDimension size, float maxWidth) {
        super.getPreferredSizeEx(size, maxWidth);
        Number i = (Number) getClientProperty(iConstants.RARE_HEIGHT_MIN_VALUE);
        if (i != null && i.intValue() > size.height) {
          size.height = i.intValue();
        }
      }
    });
    ((FormsViewEx) formsPanel.getView()).renderer = this;
  }

  public UIFormsLayoutRenderer(String columns, String rows) {
    super(new FormsPanel((new FormsViewEx(new FormLayout(columns, rows)))));
    this.columns = columns;
    this.rows = rows;
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
    public final void repaint() {
    }

    @Override
    public final void repaint(Rectangle r) {
    }

    @Override
    public final void repaint(long tm, int x, int y, int width, int height) {
    }

    @Override
    public void validate() {
      renderer.layout(getWidth(), getHeight());
    }

    @Override
    public Dimension getPreferredSize() {
      Dimension d = super.getPreferredSize();
      Number i = (Number) renderer.formsPanel.getClientProperty(iConstants.RARE_WIDTH_FIXED_VALUE);

      if ((i != null) && (i.intValue() > 0)) {
        d.width = i.intValue();
      }

      d.height += 8;
      i = (Number) getClientProperty(iConstants.RARE_HEIGHT_MIN_VALUE);
      if (i != null && i.intValue() > d.height) {
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
