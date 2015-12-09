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

import com.appnativa.rare.Platform;
import com.appnativa.rare.iConstants;
import com.appnativa.rare.platform.PlatformHelper;
import com.appnativa.rare.ui.BorderPanel;
import com.appnativa.rare.ui.Column;
import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.ui.RenderableDataItem.HorizontalAlign;
import com.appnativa.rare.ui.RenderableDataItem.IconPosition;
import com.appnativa.rare.ui.RenderableDataItem.Orientation;
import com.appnativa.rare.ui.RenderableDataItem.VerticalAlign;
import com.appnativa.rare.ui.ScreenUtils;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.UIFont;
import com.appnativa.rare.ui.UIInsets;
import com.appnativa.rare.ui.iActionComponent;
import com.appnativa.rare.ui.iPlatformBorder;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.iPlatformIcon;
import com.appnativa.rare.ui.iPlatformRenderingComponent;
import com.appnativa.rare.ui.iRenderingComponent;
import com.appnativa.rare.ui.painter.iPlatformComponentPainter;
import com.appnativa.util.SNumber;
import com.jgoodies.forms.layout.CellConstraints;

import java.util.Locale;
import java.util.Map;

public abstract class aCompositeRenderer extends BorderPanel implements iPlatformRenderingComponent {
  protected static UIInsets             EMPTY_INSETS      = new UIInsets();
  protected BackgroundSurface           backgroundSurface = BackgroundSurface.PANEL;
  protected IconPosition                iconPosition;
  protected float                       iconGap;
  protected UILabelRenderer             iconLabel;
  protected iPlatformRenderingComponent renderingComponent;
  protected boolean                     useAlternateIcon;
  private UIFont                        iconLabelFont;
  private Object                        iconLabelForeground;
  UIInsets                              iconGapInsets = new UIInsets();
  protected int columnWidth;

  public static enum BackgroundSurface { PANEL, RENDERER, ICON }

  public aCompositeRenderer() {
    this(new UILabelRenderer());
  }

  public aCompositeRenderer(iPlatformRenderingComponent rc) {
    super(Platform.getWindowViewer());
    iconLabel = new UILabelRenderer();
    iconLabel.setMargin(EMPTY_INSETS);
    iconLabel.setAlignment(HorizontalAlign.CENTER, VerticalAlign.CENTER);
    setCenterView(rc.getComponent());
    setLeftView(iconLabel.getComponent());
    renderingComponent = rc;
    setIconGap(ScreenUtils.PLATFORM_PIXELS_4);
  }

  @Override
  public void clearRenderer() {
    setComponentPainter(null);
    setBackground(null);
    setBorder(null);
    setIcon(null);
    iconLabel.clearRenderer();

    if (renderingComponent instanceof iActionComponent) {
      renderingComponent.clearRenderer();
    }
  }

  @Override
  public void dispose() {
    super.dispose();
    renderingComponent  = null;
    iconLabel           = null;
    iconLabelForeground = null;
  }

  public void makeIconPrimary() {
    backgroundSurface = BackgroundSurface.ICON;
  }

  public void makePanelPrimary() {
    backgroundSurface = BackgroundSurface.PANEL;
  }

  public void makeRendererPrimary() {
    backgroundSurface = BackgroundSurface.RENDERER;
  }
  @Override
 public void setColumnWidth(int width) {
    columnWidth=width;
 }
  

  @Override
  public void setAlignment(HorizontalAlign ha, VerticalAlign va) {
    renderingComponent.setAlignment(ha, va);
    iconLabel.setAlignment(ha, va);
  }

  public void setAlternateIconGap(int gap) {
    iPlatformComponent c = renderingComponent.getComponent();

    if (c instanceof iActionComponent) {
      ((iActionComponent) c).setIconGap(gap);
    }
  }

  /**
   * Sets the position for the alternate icon
   *
   * @param position
   *          the icon position
   */
  public void setAlternateIconPosition(IconPosition position) {
    if ((renderingComponent != null) && (position != null)) {
      iPlatformComponent c = renderingComponent.getComponent();

      if (c instanceof iActionComponent) {
        ((iActionComponent) c).setIconPosition(position);
      }
    }
  }

  @Override
  public void setBackground(UIColor bg) {
    if (backgroundSurface != null) {
      switch(backgroundSurface) {
        case ICON :
          iconLabel.setBackground(bg);

          break;

        case RENDERER :
          if (renderingComponent != null) {
            renderingComponent.setBackground(bg);

            break;
          }
        // $FALL-THROUGH$
        default :
          super.setBackground(bg);

          break;
      }
    } else {
      super.setBackground(bg);
    }
  }

  @Override
  public void setBorder(iPlatformBorder b) {
    if (backgroundSurface != null) {
      switch(backgroundSurface) {
        case ICON :
          iconLabel.setBorder(b);

          break;

        case RENDERER :
          if (renderingComponent != null) {
            renderingComponent.setBorder(b);

            break;
          }
        // $FALL-THROUGH$
        default :
          setBorderEx(b);

          break;
      }
    } else {
      setBorderEx(b);
    }
  }

  @Override
  public void setComponentPainter(iPlatformComponentPainter cp) {
    switch(backgroundSurface) {
      case ICON :
        iconLabel.setComponentPainter(cp);

        break;

      case RENDERER :
        if (renderingComponent != null) {
          renderingComponent.setComponentPainter(cp);

          break;
        }
      // $FALL-THROUGH$
      default :
        super.setComponentPainter(cp);

        break;
    }
  }

  @Override
  public void setFont(UIFont font) {
    if (iconLabelFont == null) {
      iconLabel.setFont(font);
    }

    if (renderingComponent != null) {
      renderingComponent.setFont(font);
    }
  }

  @Override
  public void setForeground(UIColor fg) {
    if (renderingComponent != null) {
      renderingComponent.setForeground(fg);
    }

    if (iconLabelForeground == null) {
      iconLabel.setForeground(fg);
    }
  }

  @Override
  public void setIcon(iPlatformIcon icon) {
    iconLabel.setIcon(icon);
  }

  public void setIconLabelFont(UIFont font) {
    iconLabelFont = font;
    iconLabel.setFont(font);
  }

  public void setIconLabelForeground(UIColor fg) {
    iconLabelForeground = fg;
    iconLabel.setForeground(fg);
  }

  @Override
  public void setIconPosition(IconPosition position) {
    if (position == null) {
      position = IconPosition.AUTO;
    }

    if (position != iconPosition) {
      iconPosition = position;

      iPlatformComponent c = iconLabel.getComponent();

      iconLabel.setIconPosition(position);
      iconPosition = position;
      remove(c);

      CellConstraints cc;

      iconGapInsets.set(0);

      switch(position) {
        case BOTTOM_CENTER :
          setBottomView(c);
          iconGapInsets.bottom = iconGap;
          cc                   = getCellConstraints(c);

          if (cc != null) {
            cc.hAlign = CellConstraints.CENTER;
            cc.vAlign = CellConstraints.TOP;
          }

          iconGapInsets.bottom = iconGap;
          iconLabel.setIconPosition(position);

          break;

        case BOTTOM_LEFT :
          setBottomView(c);
          iconGapInsets.bottom = iconGap;
          cc                   = getCellConstraints(c);

          if (cc != null) {
            cc.hAlign = CellConstraints.LEFT;
            cc.vAlign = CellConstraints.TOP;
          }

          break;

        case BOTTOM_RIGHT :
          setBottomView(c);
          iconGapInsets.bottom = iconGap;
          cc                   = getCellConstraints(c);

          if (cc != null) {
            cc.hAlign = CellConstraints.LEFT;
            cc.vAlign = CellConstraints.RIGHT;
          }

          break;

        case TOP_CENTER :
          setTopView(c);
          iconGapInsets.top = iconGap;
          cc                = getCellConstraints(c);

          if (cc != null) {
            cc.hAlign = CellConstraints.CENTER;
            cc.vAlign = CellConstraints.BOTTOM;
          }

          break;

        case TOP_LEFT :
          setTopView(c);
          iconGapInsets.top = iconGap;
          cc                = getCellConstraints(c);

          if (cc != null) {
            cc.hAlign = CellConstraints.LEFT;
            cc.vAlign = CellConstraints.BOTTOM;
          }

          break;

        case TOP_RIGHT :
          setTopView(c);
          iconGapInsets.top = iconGap;
          cc                = getCellConstraints(c);

          if (cc != null) {
            cc.hAlign = CellConstraints.RIGHT;
            cc.vAlign = CellConstraints.BOTTOM;
          }

          break;

        case RIGHT :
          setRightView(c);
          iconGapInsets.right = iconGap;
          cc                  = getCellConstraints(c);

          if (cc != null) {
            cc.hAlign = CellConstraints.LEFT;
            cc.vAlign = CellConstraints.CENTER;
          }

          break;

        case RIGHT_JUSTIFIED :
          setRightView(c);
          iconGapInsets.right = iconGap;
          cc                  = getCellConstraints(c);

          if (cc != null) {
            cc.hAlign = CellConstraints.RIGHT;
            cc.vAlign = CellConstraints.CENTER;
          }

          break;

        default :
          setLeftView(c);
          iconGapInsets.left = iconGap;
          cc                 = getCellConstraints(c);

          if (cc != null) {
            cc.hAlign = CellConstraints.CENTER;
            cc.vAlign = CellConstraints.CENTER;
          }

          break;
      }

      renderingComponent.setMargin(iconGapInsets);
    }
  }

  public void setMargin(int top, int right, int bottom, int left) {
    setMargin(new UIInsets(top, right, bottom, left));
  }

  @Override
  public void setOptions(Map<String, Object> options) {
    if (options == null) {
      return;
    }

    if (SNumber.booleanValue(options.get("useAlternateIcon"))) {
      setUseAlternateIcon(true);
    }

    if (SNumber.booleanValue(options.get("makeIconPrimary"))) {
      makeIconPrimary();
    } else if (SNumber.booleanValue(options.get("makePanelPrimary"))) {
      makePanelPrimary();
    }

    if (SNumber.booleanValue(options.get("makeRendererPrimary"))) {
      makeRendererPrimary();
    }

    String s = (String) options.get("alternateIconPosition");

    if (s != null) {
      setAlternateIconPosition(IconPosition.valueOf(s.toUpperCase(Locale.US)));
    }

    s = (String) options.get("alternateIconGap");

    if ((s != null) && (s.length() > 0)) {
      setAlternateIconGap(SNumber.intValue(s));
    }

    s = (String) options.get("iconGap");

    if (s != null) {
      int gap = SNumber.intValue(s);

      if (gap > -1) {
        setIconGap(gap);
      }
    }
  }

  @Override
  public void setOrientation(Orientation o) {}

  @Override
  public void setScaleIcon(boolean scale, float scaleFactor) {}

  public void setTextAlignment(HorizontalAlign ha, VerticalAlign va) {
    if (renderingComponent != null) {
      PlatformHelper.setTextAlignment(renderingComponent.getComponent(), ha, va);
    }
  }

  /**
   * @param useAlternateIcon
   *          the useAlternateIcon to set
   */
  public void setUseAlternateIcon(boolean useAlternateIcon) {
    this.useAlternateIcon = useAlternateIcon;
  }

  @Override
  public void setWordWrap(boolean wrap) {
    renderingComponent.setWordWrap(wrap);
    iconLabel.setWordWrap(wrap);
  }

  @Override
  public iPlatformComponent getComponent() {
    return getComponent("", null);
  }

  @Override
  public iPlatformComponent getComponent(CharSequence value, RenderableDataItem item) {
    if (renderingComponent != null) {
      RenderableDataItem ditem = item;

      if ((item != null) && (item.getValue() instanceof RenderableDataItem)) {
        ditem = (RenderableDataItem) item.getValue();
        value = (ditem == null)
                ? null
                : ditem.toCharSequence();
      }

      iPlatformComponent pc = (item == null)
                              ? null
                              : item.getRenderingComponent();

      if (pc != null) {
        return pc;
      }

      renderingComponent.getComponent(value, ditem);

      if (useAlternateIcon && (ditem == item)) {
        if (item != null) {
          renderingComponent.setIcon(item.getAlternateIcon());
        } else {
          renderingComponent.setIcon(null);
        }
      }
    }

    if (item != null) {
      String s = (String) item.getCustomProperty("CompositeRenderer.iconText");

      if (s == null) {
        if ((iconLabel.getText() != null) && (iconLabel.getText().length() > 0)) {
          iconLabel.setText("");
        }
      } else {
        iconLabel.setText(s);
      }
    }

    return this;
  }

  @Override
  public iPlatformComponent getComponent(iPlatformComponent list, Object value, RenderableDataItem item, int row,
          boolean isSelected, boolean hasFocus, Column col, RenderableDataItem rowItem, boolean handleAll) {
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

  /**
   * Returns the label used to display the renderer's icon
   *
   * @return the label used to display the renderer's icon;
   */
  public UILabelRenderer getIconLabel() {
    return iconLabel;
  }

  /**
   * Returns the rendering component
   *
   * @return the rendering component
   */
  public iRenderingComponent getRenderingComponent() {
    return renderingComponent;
  }

  /**
   * @return the useAlternateIcon
   */
  public boolean isUseAlternateIcon() {
    return useAlternateIcon;
  }

  protected void setBorderEx(iPlatformBorder b) {
    super.setBorder(b);
  }

  @Override
  protected void getMinimumSizeEx(UIDimension size, float maxWidth) {
    super.getMinimumSizeEx(size,maxWidth);

    Number i = (Number) getClientProperty(iConstants.RARE_HEIGHT_MIN_VALUE);

    if ((i != null) && (i.intValue() > size.height)) {
      size.height = i.intValue();
    }
  }

  @Override
  protected void getPreferredSizeEx(UIDimension size, float maxWidth) {
    int n=columnWidth;
    if(n<0) {
      maxWidth+=n;
      if(maxWidth<0) {
        maxWidth=0;
      }
    }
    else if(n>0 && maxWidth>n){
      maxWidth=n;
    }
    super.getPreferredSizeEx(size, maxWidth);
    size.width  += iconGapInsets.left + iconGapInsets.right;
    size.height += iconGapInsets.top + iconGapInsets.bottom;

    Number i = (Number) getClientProperty(iConstants.RARE_HEIGHT_MIN_VALUE);

    if ((i != null) && (i.intValue() > size.height)) {
      size.height = i.intValue();
    }
  }
  
  protected iPlatformRenderingComponent setupNewCopy(aCompositeRenderer r) {
    r.setIconPosition(iconPosition);
    r.backgroundSurface = backgroundSurface;
    r.columnWidth=columnWidth;
    return Renderers.setupNewCopy(this, r);
  }
    

  private void setIconGap(float gap) {
    iconGap = gap;
  }
}
