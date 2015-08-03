/*
 * @(#)UILabelRenderer.java   2012-02-08
 *
 * Copyright (c) 2007-2009 appNativa Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.ui.renderer;

import java.beans.PropertyChangeEvent;
import java.util.Map;

import javax.swing.JLabel;

import com.appnativa.rare.iConstants;
import com.appnativa.rare.platform.swing.ui.view.LabelRenderer;
import com.appnativa.rare.ui.ActionComponent;
import com.appnativa.rare.ui.BorderUtils;
import com.appnativa.rare.ui.Column;
import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.ui.RenderableDataItem.Orientation;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.UIFont;
import com.appnativa.rare.ui.UIInsets;
import com.appnativa.rare.ui.Utils;
import com.appnativa.rare.ui.iPlatformBorder;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.iPlatformRenderingComponent;
import com.appnativa.rare.ui.border.UICompoundBorder;
import com.appnativa.rare.ui.border.UIEmptyBorder;
import com.appnativa.rare.ui.painter.iPlatformComponentPainter;

/**
 * A renderer that displays text and an associated icon. A platform label
 * component is used for the renderer
 *
 * @author Don DeCoteau
 */
public class UILabelRenderer extends ActionComponent implements Cloneable, iPlatformRenderingComponent {
  private UIEmptyBorder    emptyBorder    = new UIEmptyBorder(2);
  private UICompoundBorder compoundBorder = new UICompoundBorder(BorderUtils.EMPTY_BORDER, emptyBorder);

  public UILabelRenderer() {
    this(new LabelRenderer());
  }

  public UILabelRenderer(JLabel tv) {
    super(tv);
    getView().setBorder(compoundBorder);
  }

  @Override
  public iPlatformRenderingComponent newCopy() {
    final UILabelRenderer r = new UILabelRenderer();

    return Renderers.setupNewCopy(this, r);
  }

  public void prepareForReuse(int row, int column) {
    componentPainter=null;
    view.setOpaque(false);
  }

  @Override
  protected void getMinimumSizeEx(UIDimension size) {
    super.getMinimumSizeEx(size);
    Number i = (Number) getClientProperty(iConstants.RARE_HEIGHT_MIN_VALUE);
    if (i != null && i.intValue() > size.height) {
      size.height = i.intValue();
    }
  }

  @Override
  protected void getPreferredSizeEx(UIDimension size, float maxWidth) {
    super.getPreferredSizeEx(size, maxWidth);
    Number i = (Number) getClientProperty(iConstants.RARE_HEIGHT_MIN_VALUE);
    if (i != null && i.intValue() > size.height) {
      size.height = i.intValue();
    }
  }

  @Override
  public void propertyChange(PropertyChangeEvent pce) {
  }

  @Override
  public void clearRenderer() {
    setBackground(null);
    setBorder(null);
    setIcon(null);
    setText("");
  }

  @Override
  public void setBorder(iPlatformBorder border) {
    if (border == compoundBorder) {
      return;
    }

    if (border == null) {
      border = BorderUtils.EMPTY_BORDER;
    }

    compoundBorder.setOutsideBorder(border);
  }

  @Override
  public void setBackground(UIColor bg) {
    if (componentPainter != null) {
      super.setComponentPainter(null);
    }

    view.setBackground(bg == null ? null : bg);
  }

  public void setColumnWidth(int width) {
    ((JLabel) view).putClientProperty(iConstants.RARE_WIDTH_FIXED_VALUE, width);
  }

  @Override
  public void setComponentPainter(iPlatformComponentPainter cp) {
    componentPainter = cp;
    ((LabelRenderer) view).setComponentPainter(cp);
  }

  @Override
  public void setEnabled(boolean enabled) {
    if (view.isEnabled() != enabled) {
      view.setEnabled(enabled);
    }
  }

  @Override
  public void setMargin(UIInsets insets) {
    if (insets != null) {
      emptyBorder.setInsets(insets);
    } else {
      emptyBorder.setInsets(0, 0, 0, 0);
    }
  }

  @Override
  public void setOpaque(boolean opaque) {
    view.setOpaque(opaque);
  }

  @Override
  public void setOptions(Map<String, Object> options) {
  }

  @Override
  public void setOrientation(Orientation o) {
  }

  @Override
  public iPlatformComponent getComponent() {
    return this;
  }

  @Override
  public iPlatformComponent getComponent(CharSequence value, RenderableDataItem item) {
    final JLabel tv = (JLabel) view;

    tv.setText((value == null) ? "" : value.toString());

    return this;
  }

  public iPlatformComponent getComponent(iPlatformComponent list, CharSequence value, RenderableDataItem item, int row,
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

    return getComponent(value, item);
  }

  @Override
  public iPlatformComponent getComponent(iPlatformComponent list, Object value, RenderableDataItem item, int row,
      boolean isSelected, boolean hasFocus, Column col, RenderableDataItem rowItem, boolean handleAll) {
    return this;
  }

  public iPlatformComponent getComponentEx() {
    return this;
  }
}
