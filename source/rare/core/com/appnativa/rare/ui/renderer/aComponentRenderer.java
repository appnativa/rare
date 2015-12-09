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

import com.appnativa.rare.spot.Widget;
import com.appnativa.rare.ui.Column;
import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.ui.RenderableDataItem.HorizontalAlign;
import com.appnativa.rare.ui.RenderableDataItem.IconPosition;
import com.appnativa.rare.ui.RenderableDataItem.Orientation;
import com.appnativa.rare.ui.RenderableDataItem.VerticalAlign;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIFont;
import com.appnativa.rare.ui.UIInsets;
import com.appnativa.rare.ui.iActionComponent;
import com.appnativa.rare.ui.iPlatformBorder;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.iPlatformIcon;
import com.appnativa.rare.ui.iPlatformRenderingComponent;
import com.appnativa.rare.ui.painter.iPainterSupport;
import com.appnativa.rare.ui.painter.iPlatformComponentPainter;

import java.util.Map;

/**
 * A renderer that wraps a platform component.
 *
 * @author Don DeCoteau
 */
public abstract class aComponentRenderer implements iPlatformRenderingComponent {
  protected Widget config;

  /** the component to use for rendering */
  protected iPlatformComponent renderingComponent;

  public aComponentRenderer() {
    this(null);
  }

  public aComponentRenderer(iPlatformComponent comp) {
    renderingComponent = comp;
  }

  public aComponentRenderer(iPlatformComponent comp, Widget config) {
    renderingComponent = comp;
    this.config        = config;
  }

  @Override
  public void dispose() {
    renderingComponent.dispose();
  }

  @Override
  public void setAlignment(HorizontalAlign ha, VerticalAlign va) {}

  @Override
  public void setBackground(UIColor bg) {
    renderingComponent.setBackground(bg);
  }

  @Override
  public void setBorder(iPlatformBorder b) {
    renderingComponent.setBorder(b);
  }
  @Override
  public void setColumnWidth(int width) {
  }
  /**
   * Sets the component to use to render
   * @param comp the component to use to render
   */
  public void setComponent(iPlatformComponent comp) {
    renderingComponent = comp;
  }

  @Override
  public void setComponentPainter(iPlatformComponentPainter cp) {
    if (renderingComponent instanceof iPainterSupport) {
      ((iPainterSupport) renderingComponent).setComponentPainter(cp);
    }
  }

  @Override
  public void setEnabled(boolean enabled) {
    renderingComponent.setEnabled(enabled);
  }

  @Override
  public void setFont(UIFont f) {
    renderingComponent.setFont(f);
  }

  @Override
  public void setForeground(UIColor fg) {
    renderingComponent.setForeground(fg);
  }

  @Override
  public void setIcon(iPlatformIcon icon) {
    if (renderingComponent instanceof iActionComponent) {
      ((iActionComponent) renderingComponent).setIcon(icon);
    }
  }

  @Override
  public void setScaleIcon(boolean scale, float scaleFactor) {
    renderingComponent.setScaleIcon(scale, scaleFactor);
  }

  @Override
  public void setIconPosition(IconPosition position) {
    if (renderingComponent instanceof iActionComponent) {
      ((iActionComponent) renderingComponent).setIconPosition(position);
    }
  }

  @Override
  public void clearRenderer() {
    setBackground(null);
    setBorder(null);
    setIcon(null);

    if (renderingComponent instanceof iActionComponent) {
      ((iActionComponent) renderingComponent).setText("");
    }

    setComponentPainter(null);
  }

  @Override
  public void setMargin(UIInsets insets) {}

  @Override
  public void setOptions(Map<String, Object> options) {}

  @Override
  public void setOrientation(Orientation o) {}

  public void setTextAlignment(HorizontalAlign ha, VerticalAlign va) {
    if (renderingComponent instanceof iActionComponent) {
      ((iActionComponent) renderingComponent).setAlignment(ha, va);
    }
  }

  @Override
  public void setWordWrap(boolean wrap) {
    if (renderingComponent instanceof iActionComponent) {
      ((iActionComponent) renderingComponent).setWordWrap(wrap);
    }
  }

  /**
   * Gets the actual component that will do the rending
   *
   * @return the actual component that will do the rending
   */
  @Override
  public iPlatformComponent getComponent() {
    return renderingComponent;
  }

  @Override
  public iPlatformComponent getComponent(CharSequence value, RenderableDataItem item) {
    if (renderingComponent instanceof iActionComponent) {
      ((iActionComponent) renderingComponent).setText(value);
    }

    return renderingComponent;
  }

  @Override
  public iPlatformComponent getComponent(iPlatformComponent comp, Object value, RenderableDataItem item, int row,
          boolean isSelected, boolean hasFocus, Column col, RenderableDataItem rowItem, boolean handleAll) {
    String s = (value == null)
               ? ""
               : value.toString();

    if (renderingComponent instanceof iActionComponent) {
      ((iActionComponent) renderingComponent).setText(s);
    }

    return renderingComponent;
  }
}
