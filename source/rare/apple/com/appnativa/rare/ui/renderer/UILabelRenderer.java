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

import com.appnativa.rare.platform.apple.ui.view.LabelView;
import com.appnativa.rare.platform.apple.ui.view.View;
import com.appnativa.rare.ui.ActionComponent;
import com.appnativa.rare.ui.Column;
import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.ui.RenderableDataItem.Orientation;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.UIFont;
import com.appnativa.rare.ui.Utils;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.iPlatformRenderingComponent;

import java.util.Map;

/**
 * A renderer that displays text and an associated icon.
 * A platform label component is used for the renderer
 *
 * @author Don DeCoteau
 */
public class UILabelRenderer extends ActionComponent implements Cloneable, iPlatformRenderingComponent {
  int columnWidth;

  public UILabelRenderer() {
    this(new LabelView());
  }

  public UILabelRenderer(LabelView tv) {
    super(tv);
  }

  @Override
  public Object createNewNativeView() {
    return LabelView.createProxy();
  }

  @Override
  public void dispose() {
    super.dispose();
  }

  @Override
  public void clearRenderer() {
    setIcon(null);
    setText("");
    view.clearVisualState();
  }

  @Override
  public iPlatformRenderingComponent newCopy() {
    return setupNewCopy(new UILabelRenderer());
  }

  protected iPlatformRenderingComponent setupNewCopy(UILabelRenderer r) {
    r.columnWidth=columnWidth;
    r.setWordWrap(isWordWrap());
    return r;
  }

  @Override
  public void prepareForReuse(int row, int column) {
    view.setBackgroundColorEx(null);
    view.clearVisualState();
  }

  @Override
  public void setBackground(UIColor bg) {
    if (view.getComponentPainter() != null) {
      super.setComponentPainter(null);
    }

    view.setBackgroundColorEx(bg);
  }

  @Override
  public void setColumnWidth(int width) {
    if (width <= 0) {
      columnWidth = width;
    } else {
      columnWidth = 0;
      ((LabelView) view).setPrefColumnWidth(width);
    }
  }

  @Override
  protected void getPreferredSizeEx(UIDimension size, float maxWidth) {
    maxWidth += columnWidth; //will only be set <=0

    if (maxWidth < 0) {
      maxWidth = 0;
    }

    super.getPreferredSizeEx(size, maxWidth);
  }

  @Override
  public void setEnabled(boolean enabled) {
    view.setEnabled(enabled);
  }

  @Override
  public void setNativeView(Object proxy) {
    view.setProxy(proxy);
    view.clearVisualState();
  }

  @Override
  public void setOptions(Map<String, Object> options) {}

  @Override
  public void setOrientation(Orientation o) {}

  @Override
  public void setRenderingView(View view) {
    super.setView(view);
  }

  @Override
  public iPlatformComponent getComponent() {
    return this;
  }

  @Override
  public iPlatformComponent getComponent(CharSequence value, RenderableDataItem item) {
    final LabelView v = (LabelView) view;

    v.setText((value == null)
              ? ""
              : value);

    return this;
  }

  @Override
  public iPlatformComponent getComponent(iPlatformComponent list, Object value, RenderableDataItem item, int row,
          boolean isSelected, boolean hasFocus, Column col, RenderableDataItem rowItem, boolean handleAll) {
    if (handleAll) {
      Utils.setIconAndAlignment(this, item, null, null, isSelected, false, false, true, false, null);
      setBorder(item.getBorder());

      UIFont f = item.getFont();

      if (f == null) {
        f = list.getFont();
      }

      setFont(f);

      UIColor fg = item.getForeground();

      if (fg == null) {
        fg = list.getForeground();
      }

      setForeground(fg);
    }

    CharSequence s;

    if (value instanceof CharSequence) {
      s = (CharSequence) value;
    } else {
      s = (value == null)
          ? ""
          : value.toString();
    }

    return getComponent(s, item);
  }
}
