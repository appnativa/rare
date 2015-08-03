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
import com.appnativa.rare.ui.ColorUtils;
import com.appnativa.rare.ui.Column;
import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.ui.ScreenUtils;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIFont;
import com.appnativa.rare.ui.UIInsets;
import com.appnativa.rare.ui.Utils;
import com.appnativa.rare.ui.iPlatformBorder;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.iPlatformIcon;
import com.appnativa.rare.ui.iPlatformItemRenderer;
import com.appnativa.rare.ui.iPlatformRenderingComponent;
import com.appnativa.rare.ui.painter.PaintBucket;
import com.appnativa.rare.ui.painter.UIComponentPainter;
import com.appnativa.rare.ui.painter.iPlatformComponentPainter;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.util.StringCache;
import com.appnativa.util.iStringConverter;

public abstract class aListItemRenderer implements iPlatformItemRenderer {
  protected static RenderableDataItem defaultItem        = new RenderableDataItem("");
  protected boolean                   selectionPainted   = true;
  protected boolean                   paintRowBackground = true;
  protected boolean                   handlesSelection   = true;
  protected boolean                   autoHilight;
  protected PaintBucket               autoHilightPaint;
  protected UIComponentPainter        componentPainter;
  protected iStringConverter          converter;
  protected iPlatformIcon             delayedIcon;
  protected UIColor                   disabledColor;
  protected UIInsets                  insets;
  protected UIInsets                  selectedInsets;
  protected Column                    itemDescription;
  protected PaintBucket               lostFocusSelectionPaint;
  protected boolean                   noicon;
  protected boolean                   alwaysCallSetBorder;
  protected PaintBucket               pressedPaint;
  protected PaintBucket               selectionPaint;

  /**
   * Constructs a new instance
   */
  public aListItemRenderer() {
    this(true);
  }

  public aListItemRenderer(boolean handlesSelection) {
    this.handlesSelection = handlesSelection;
    setSelectionPaint(Platform.getAppContext().getSelectionPainter());
    setPressedPaint(Platform.getAppContext().getPressedPainter());
    autoHilightPaint = Platform.getAppContext().getAutoHilightPainter();
    setLostFocusSelectionPaint(Platform.getAppContext().getLostFocusSelectionPainter());
    setInsets(new UIInsets(ScreenUtils.PLATFORM_PIXELS_2));
  }

  @Override
  public CharSequence configureRenderingComponent(iPlatformComponent list, iPlatformRenderingComponent rc,
          RenderableDataItem item, int row, boolean isSelected, boolean hasFocus, Column col,
          RenderableDataItem rowItem) {
    CharSequence              s;
    UIColor                   fg      = null;
    UIColor                   bg      = null;
    UIFont                    font    = null;
    boolean                   enabled = true;
    iPlatformComponentPainter bp      = null;
    iWidget                   w       = (list == null)
            ? null
            : list.getWidget();
    iPlatformBorder           b       = null;
    UIInsets                  in      = insets;

    if (col == null) {
      col = itemDescription;
    }

    boolean override = (col == null)
                       ? false
                       : col.overrideSelectionBackground;

    if (!selectionPainted) {
      override = true;
    }

    if ((w == null) && (list != null)) {
      w = Platform.findWidgetForComponent(list);

      if (w == null) {
        w = Platform.getContextRootViewer();
      }
    }

    if (item == null) {
      item = defaultItem;
    }

    font = item.getFont();
    fg   = item.getForeground();
    bg   = item.getBackground();
    bp   = item.getComponentPainter();
    b    = item.getBorder();

    if ((fg == null) && (col != null)) {
      fg = col.getForeground();
    }

    if (converter != null) {
      s = converter.toString(item);
    } else {
      s = (col == null)
          ? item.toCharSequence(w)
          : col.toCharSequence(w, item);
    }

    if (isSelected) {
      isSelected = item.isSelectable();
    }

    PaintBucket pb = null;

    if (isSelected) {
      pb = (col == null)
           ? null
           : col.getItemSelectionPainter();

      if ((pb == null) && override) {
        pb = (col == null)
             ? null
             : col.getItemPainter();
      }

      if (pb == null) {
        if (selectionPainted) {
          if (handlesSelection) {
            in = getSelectesInsets();
            pb = getSelectionPaint(list.isFocusOwner());
          } else {
            UIColor c = getSelectionForeground(hasFocus);
            UIFont  f = getSelectionFont(hasFocus);

            if (c != null) {
              fg = c;
            }

            if (f != null) {
              font = f;
            }
          }
        }
      }

      if (pb != null) {
        if (pb.getForegroundColor() != null) {
          fg = pb.getForegroundColor();
        }

        if (pb.getFont() != null) {
          font = pb.getFont();
        }

        if (pb.getBackgroundPainter() != null) {
          bp = pb.getCachedComponentPainter();
        }
      }
    } else {
      pb = (col == null)
           ? null
           : col.getItemPainter();

      if (pb != null) {
        if (fg == null) {
          fg = pb.getForegroundColor();
        }

        if ((bp == null) && (bg == null)) {
          bp = pb.getCachedComponentPainter();
        }

        if (font == null) {
          font = pb.getFont();
        }
      }
    }

    enabled = item.isEnabled();

    if (enabled && (list != null)) {
      enabled = list.isEnabled();
    }

    if (rowItem != null) {
      if (!rowItem.isEnabled()) {
        enabled = false;
      }

      if (font == null) {
        font = rowItem.getFont();
      }

      if (fg == null) {
        fg = rowItem.getForeground();
      }

      if ((bg == null) && paintRowBackground) {
        bg = rowItem.getBackground();
      }

      if ((b == null) && paintRowBackground) {
        b = rowItem.getBorder();
      }
    }

    if (col != null) {
      if (col.isIndexColumn()) {
        s = StringCache.valueOf(row + 1);
      }

      switch(col.getRenderDetail()) {
        case ICON_ONLY :
          s = "";

          break;

        case TEXT_ONLY :
          noicon = true;

          break;

        default :
          break;
      }
    }

    setIconAndAlignment(rc, item, null, col, enabled, false, false, !noicon, false);

    if ((font == null) && (list != null)) {
      font = list.getFont();
    }

    if ((fg == null) && (list != null)) {
      fg = list.getForeground();
    }

    if (!enabled) {
      if (disabledColor == null) {
        disabledColor = ColorUtils.getDisabledForeground();
      }

      if (list != null) {
        fg = fg.getDisabledColor(list.getDisabledColor());
      }

      if (fg == null) {
        fg = disabledColor;
      } else {
        fg = fg.getDisabledColor(disabledColor);
      }
    }

    rc.setEnabled(enabled);
    rc.setFont(font);
    rc.setComponentPainter(bp);

    if ((bp == null) ||!bp.isBackgroundPaintEnabled()) {
      rc.setBackground(bg);
    }

    rc.setForeground(fg);

    if (alwaysCallSetBorder) {
      if ((b == null) && (bp != null)) {
        b = bp.getBorder();
      }

      rc.setBorder(b);
    } else if ((bp == null) || (bp.getBorder() == null)) {
      rc.setBorder(b);
    }

    if (in != null) {
      rc.setMargin(in);
    }

    if (col != null) {
      rc.setWordWrap(col.wordWrap);
    }

    return s;
  }

  public void dispose() {
    if (componentPainter != null) {
      componentPainter.dispose();
      componentPainter = null;
    }

    converter               = null;
    selectionPaint          = null;
    itemDescription         = null;
    lostFocusSelectionPaint = null;
  }

  public void setAutoHilight(boolean autoHilight) {
    this.autoHilight = autoHilight;
  }

  public void setAutoHilightPaint(PaintBucket autoHilightPaint) {
    this.autoHilightPaint = autoHilightPaint;
  }

  @Override
  public void setConverter(iStringConverter converter) {
    this.converter = converter;
  }

  public void setDelayedIcon(iPlatformIcon delayedIcon) {
    this.delayedIcon = delayedIcon;
  }

  public void setIconAndAlignment(iPlatformRenderingComponent rc, RenderableDataItem item, RenderableDataItem row,
                                  Column col, boolean enabled, boolean center, boolean top, boolean seticon,
                                  boolean expanded) {
    Utils.setIconAndAlignment(rc, item, row, col, enabled, center, top, seticon, expanded, delayedIcon);
  }

  @Override
  public void setInsets(UIInsets insets) {
    if (insets == null) {
      this.insets = null;
    } else {
      this.insets = new UIInsets(insets);
    }
  }

  @Override
  public void setItemDescription(Column itemDescription) {
    this.itemDescription = itemDescription;
  }

  @Override
  public void setLostFocusSelectionPaint(PaintBucket pb) {
    this.lostFocusSelectionPaint = pb;
  }

  public void setPaintRowBackground(boolean useRowBackground) {
    this.paintRowBackground = useRowBackground;
  }

  @Override
  public void setPressedPaint(PaintBucket pb) {
    this.pressedPaint = pb;
  }

  @Override
  public void setSelectionPaint(PaintBucket pb) {
    this.selectionPaint = pb;
  }

  @Override
  public void setSelectionPainted(boolean painted) {
    selectionPainted = painted;
  }

  public void setHandlesSelection(boolean handlesSelection) {
    this.handlesSelection = handlesSelection;
  }

  public PaintBucket getAutoHilightPaint() {
    return autoHilightPaint;
  }

  @Override
  public iStringConverter getConverter() {
    return converter;
  }

  public iPlatformIcon getDelayedIcon() {
    return delayedIcon;
  }

  @Override
  public UIInsets getInsets() {
    return insets;
  }

  @Override
  public Column getItemDescription() {
    return itemDescription;
  }

  @Override
  public PaintBucket getLostFocusSelectionPaint() {
    return selectionPainted
           ? lostFocusSelectionPaint
           : null;
  }

  @Override
  public PaintBucket getPressedPaint() {
    return pressedPaint;
  }

  public UIFont getSelectionFont(boolean focused) {
    UIFont font = null;

    if (selectionPainted) {
      if (!focused && (lostFocusSelectionPaint != null)) {
        font = lostFocusSelectionPaint.getFont();
      }

      if (font == null) {
        font = selectionPaint.getFont();
      }
    }

    return font;
  }

  public UIColor getSelectionForeground(boolean focused) {
    UIColor fg = null;

    if (selectionPainted) {
      if (!focused && (lostFocusSelectionPaint != null)) {
        fg = lostFocusSelectionPaint.getForegroundColor();
      }

      if (fg == null) {
        fg = selectionPaint.getForegroundColor();
      }
    }

    return fg;
  }

  @Override
  public PaintBucket getSelectionPaint() {
    return selectionPainted
           ? selectionPaint
           : null;
  }

  public PaintBucket getSelectionPaintForExternalPainter(boolean focused) {
    if (!selectionPainted || handlesSelection) {
      return null;
    }

    PaintBucket pb = null;

    if (!focused && (lostFocusSelectionPaint != null)) {
      pb = lostFocusSelectionPaint;
    }

    if (pb == null) {
      pb = selectionPaint;
    }

    return pb;
  }

  public PaintBucket getSelectionPaint(boolean focused) {
    PaintBucket pb = null;

    if (selectionPainted) {
      if (!focused && (lostFocusSelectionPaint != null)) {
        pb = lostFocusSelectionPaint;
      }

      if (pb == null) {
        pb = selectionPaint;
      }
    }

    return pb;
  }

  public boolean isAutoHilight() {
    return autoHilight;
  }

  public boolean isPaintRowBackground() {
    return paintRowBackground;
  }

  @Override
  public boolean isSelectionPainted() {
    return selectionPainted;
  }

  public boolean isHandlesSelection() {
    return handlesSelection;
  }

  protected UIInsets getSelectesInsets() {
    UIInsets in = insets;

    if ((selectedInsets == null) && handlesSelection && (in != null)) {
      selectedInsets = new UIInsets(in);

      PaintBucket     pb  = getSelectionPaint();
      iPlatformBorder b   = pb.getBorder();
      UIInsets        inn = (b == null)
                            ? null
                            : b.getBorderInsets((UIInsets) null);

      if (inn != null) {
        selectedInsets.left   -= inn.left;
        selectedInsets.right  -= inn.right;
        selectedInsets.top    -= inn.top;
        selectedInsets.bottom -= inn.bottom;
      }
    }

    return selectedInsets;
  }
}
