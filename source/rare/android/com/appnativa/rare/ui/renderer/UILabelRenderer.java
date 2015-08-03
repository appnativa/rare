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
import android.content.res.ColorStateList;

import android.view.ViewGroup;

import android.widget.TextView;

import com.appnativa.rare.Platform;
import com.appnativa.rare.platform.android.ui.NullDrawable;
import com.appnativa.rare.platform.android.ui.view.ButtonViewEx;
import com.appnativa.rare.platform.android.ui.view.LabelView;
import com.appnativa.rare.platform.android.ui.view.ToggleButtonView;
import com.appnativa.rare.ui.ActionComponent;
import com.appnativa.rare.ui.Column;
import com.appnativa.rare.ui.FontUtils;
import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.ui.ScreenUtils;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIFont;
import com.appnativa.rare.ui.UIImageIcon;
import com.appnativa.rare.ui.UIInsets;
import com.appnativa.rare.ui.Utils;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.iPlatformIcon;
import com.appnativa.rare.ui.iPlatformRenderingComponent;
import com.appnativa.rare.ui.painter.iPlatformComponentPainter;

import java.util.Map;

/**
 * A renderer that displays text and an associated icon. A platform label
 * component is used for the renderer
 *
 * @author Don DeCoteau
 */
public class UILabelRenderer extends ActionComponent implements Cloneable, iPlatformRenderingComponent {
  private int                  _hashCode;
  private UIColor              _lastFgColor;
  private final int            _origFgColor;
  private final ColorStateList _origFgColors;
  private CharSequence         text;

  public UILabelRenderer() {
    this(Platform.getAppContext().getActivity());
  }

  public UILabelRenderer(Context context) {
    this(new LabelView(context));
    view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT));
  }

  public UILabelRenderer(LabelView tv) {
    super(tv);
    _origFgColor  = tv.getCurrentTextColor();
    _origFgColors = tv.getTextColors();
  }

  /**
   * @param icon
   *          the icon to set
   */
  public void setIcon(iPlatformIcon icon) {
    if (this.icon != icon) {
      this.icon = icon;

      if (icon instanceof UIImageIcon) {
        ((UIImageIcon) icon).isImageLoaded(this);
      }

      if (view instanceof LabelView) {
        ((LabelView) view).setIcon(icon);
      } else if (view instanceof ButtonViewEx) {
        ((ButtonViewEx) view).setIcon(icon);
      } else if (view instanceof ToggleButtonView) {
        ((ToggleButtonView) view).setIcon(icon);
      }
    }
  }

  public void setBlockRequestLayout(boolean block) {
    ((LabelView) view).setBlockRequestLayout(block);
  }

  @Override
  public void revalidate() {}

  public boolean equals(UILabelRenderer r) {
    if (r == this) {
      return true;
    }

    if (fgColor != r.fgColor) {
      return false;
    }

    if (border != r.border) {
      return false;
    }

    if (font != r.font) {
      return false;
    }

    if (icon != r.icon) {
      return false;
    }

    if (iconPosition != r.iconPosition) {
      return false;
    }

    if (componentPainter != r.componentPainter) {
      return false;
    }

    return true;
  }

  public int hashCode() {
    if (_hashCode == 0) {
      _hashCode = 1;
      _hashCode *= 4;
      _hashCode += (fgColor == null)
                   ? 0
                   : fgColor.hashCode();
      _hashCode *= 4;
      _hashCode += (font == null)
                   ? 0
                   : font.getSize();
      _hashCode *= 4;
      _hashCode *= 4;
      _hashCode *= 4;

      if (iconPosition != null) {
        _hashCode += iconPosition.hashCode();
      }

      _hashCode *= 4;

      if (icon != null) {
        _hashCode += icon.hashCode();
      }

      if (componentPainter != null) {
        _hashCode += componentPainter.hashCode();
      }
    }

    return _hashCode;
  }

  public iPlatformRenderingComponent newCopy() {
    final TextView  otv = (TextView) view;
    final LabelView tv  = new LabelView(otv.getContext());

    tv.setGravity(otv.getGravity());

    final UILabelRenderer r = new UILabelRenderer(tv);

    return Renderers.setupNewCopy(this, r);
  }

  public void setComponentPainter(iPlatformComponentPainter cp) {
    if (componentPainter != cp) {
      super.setComponentPainter(cp);
    }
  }

  @Override
  public void setBackground(UIColor bg) {
    if ((componentPainter != null) && (bg != null)) {
      super.setComponentPainter(null);
    }

    view.setBackgroundDrawable((bg == null)
                               ? NullDrawable.getInstance()
                               : bg.getDrawable());
    super.setBackground(bg);
  }

  @Override
  public void setEnabled(boolean enabled) {
    if (view.isEnabled() != enabled) {
      view.setEnabled(enabled);
    }
  }

  public void setFont(UIFont f) {
    if (f == null) {
      f = FontUtils.getDefaultFont();
    }

    if (font != f) {
      super.setFont(f);
    }
  }

  public void setForeground(UIColor fg) {
    fgColor = fg;
  }

  public void setMargin(UIInsets in) {
    if (in == null) {
      int d = ScreenUtils.PLATFORM_PIXELS_2;

      view.setPadding(d, d, d, d);
    } else {
      view.setPadding(in.intLeft(), in.intTop(), in.intRight(), in.intBottom());
    }
  }

  public void setMargin(int top, int right, int bottom, int left) {
    view.setPadding(left, top, right, bottom);
  }

  public void setOptions(Map<String, Object> options) {}

  @Override
  public void setSize(float width, float height) {
    ((LabelView) view).setSize((int) Math.ceil(width), (int) Math.ceil(height));
  }

  public void setText(CharSequence text) {
    if (text != this.text) {
      this.text = text;
      super.setText(text);
    }
  }

  public iPlatformComponent getComponent() {
    return this;
  }

  public iPlatformComponent getComponent(CharSequence value, RenderableDataItem item) {
    configureView();

    if (this.text != value) {
      this.text = value;
      sizeCache.dirty();

      final TextView tv = (TextView) view;

      tv.setText((value == null)
                 ? ""
                 : value);
    }

    return this;
  }

  public void clearRenderer() {
    setComponentPainter(null);
    setBackground(null);
    setBorder(null);
    setIcon(null);
    setText("");
  }

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

  protected void configureView() {
    final TextView tv = (TextView) view;

    if (_lastFgColor != fgColor) {
      _lastFgColor = fgColor;

      if (fgColor != null) {
        fgColor.setTextColor(tv);
      } else {
        if (_origFgColors != null) {
          tv.setTextColor(_origFgColors);
        } else {
          tv.setTextColor(_origFgColor);
        }
      }
    }
  }
}
