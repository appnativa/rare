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

import com.appnativa.rare.iConstants;
import com.appnativa.rare.ui.Column;
import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.ui.RenderableDataItem.HorizontalAlign;
import com.appnativa.rare.ui.RenderableDataItem.IconPosition;
import com.appnativa.rare.ui.RenderableDataItem.Orientation;
import com.appnativa.rare.ui.RenderableDataItem.VerticalAlign;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.UIFont;
import com.appnativa.rare.ui.UIInsets;
import com.appnativa.rare.ui.XPContainer;
import com.appnativa.rare.ui.iActionComponent;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.iPlatformIcon;
import com.appnativa.rare.ui.iPlatformRenderingComponent;

import java.util.Map;

public abstract class aRendererContainer extends XPContainer implements iPlatformRenderingComponent {
  iPlatformRenderingComponent renderingComponent;
  protected int               columnWidth;

  public aRendererContainer(iPlatformRenderingComponent rc) {
    this.renderingComponent = rc;
  }

  @Override
  public void dispose() {
    renderingComponent.dispose();
  }

  @Override
  public iPlatformRenderingComponent newCopy() {
    return renderingComponent.newCopy();
  }

  @Override
  public void setAlignment(HorizontalAlign ha, VerticalAlign va) {
    renderingComponent.setAlignment(ha, va);
  }

  @Override
  public void setEnabled(boolean enabled) {
    renderingComponent.setEnabled(enabled);
  }

  @Override
  public void clearRenderer() {
    setComponentPainter(null);
    setBackground(null);
    setBorder(null);
    setIcon(null);

    if (renderingComponent instanceof iActionComponent) {
      ((iActionComponent) renderingComponent).setText("");
    }
  }

  @Override
  public void setFont(UIFont font) {
    renderingComponent.setFont(font);
  }

  @Override
  public void setForeground(UIColor fg) {
    renderingComponent.setForeground(fg);
  }

  @Override
  public void setIcon(iPlatformIcon icon) {
    renderingComponent.setIcon(icon);
  }

  @Override
  public void setIconPosition(IconPosition position) {
    renderingComponent.setIconPosition(position);
  }

  @Override
  public void setMargin(UIInsets insets) {
    renderingComponent.setMargin(insets);
  }

  @Override
  public void setOptions(Map<String, Object> options) {
    renderingComponent.setOptions(options);
  }

  @Override
  public void setOrientation(Orientation o) {
    renderingComponent.setOrientation(o);
  }

  @Override
  public void setWordWrap(boolean wrap) {
    renderingComponent.setWordWrap(wrap);
  }

  @Override
  public void setColumnWidth(int width) {
    columnWidth = width;
  }

  @Override
  public iPlatformComponent getComponent() {
    renderingComponent.getComponent();

    return this;
  }

  @Override
  public iPlatformComponent getComponent(CharSequence value, RenderableDataItem item) {
    renderingComponent.getComponent(value, item);

    return this;
  }

  @Override
  public iPlatformComponent getComponent(iPlatformComponent list, Object value, RenderableDataItem item, int row,
          boolean isSelected, boolean hasFocus, Column col, RenderableDataItem rowItem, boolean handleAll) {
    renderingComponent.getComponent(list, value, item, row, isSelected, hasFocus, col, rowItem, handleAll);

    return this;
  }

  @Override
  protected void getMinimumSizeEx(UIDimension size, float maxWidth) {
    super.getMinimumSizeEx(size, maxWidth);

    Number i = (Number) getClientProperty(iConstants.RARE_HEIGHT_MIN_VALUE);

    if ((i != null) && (i.intValue() > size.height)) {
      size.height = i.intValue();
    }
  }

  @Override
  protected void getPreferredSizeEx(UIDimension size, float maxWidth) {
    int n = columnWidth;

    if (n < 0) {
      maxWidth += n;

      if (maxWidth < 0) {
        maxWidth = 0;
      }
    } else if ((n > 0) && (maxWidth > n)) {
      maxWidth = n;
    }

    super.getPreferredSizeEx(size, maxWidth);

    Number i = (Number) getClientProperty(iConstants.RARE_HEIGHT_MIN_VALUE);

    if ((i != null) && (i.intValue() > size.height)) {
      size.height = i.intValue();
    }
  }
}
