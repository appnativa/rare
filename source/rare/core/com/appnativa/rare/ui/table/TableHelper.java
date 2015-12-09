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

package com.appnativa.rare.ui.table;

import com.appnativa.rare.Platform;
import com.appnativa.rare.ui.ColorUtils;
import com.appnativa.rare.ui.Column;
import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.ui.ScreenUtils;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.border.UILineBorder;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.iPlatformItemRenderer;
import com.appnativa.rare.ui.iPlatformListDataModel;
import com.appnativa.rare.ui.iPlatformRenderingComponent;
import com.appnativa.rare.ui.painter.PaintBucket;
import com.appnativa.rare.ui.painter.UIBackgroundPainter;
import com.appnativa.rare.ui.renderer.UILabelRenderer;
import com.appnativa.util.FilterableList;
import com.appnativa.util.iFilterableList;

import java.util.ArrayList;
import java.util.List;

public class TableHelper {
  private static int             maxRowsForSizeCalculation = 0;
  private static UILabelRenderer computeSizeRenderer;
  private static int             defaultPreferredRows;

  public static int getFlatItemCount(List<RenderableDataItem> rows) {
    int count = 0;

    for (RenderableDataItem row : rows) {
      if (row != null) {
        count += row.size();
      }
    }

    return count;
  }

  public static iFilterableList<RenderableDataItem> getSubList(List<RenderableDataItem> list, int startColumn,
          int endColumn, iFilterableList outList) {
    int len   = list.size();
    int count = endColumn - startColumn;

    if (outList == null) {
      outList = new FilterableList<RenderableDataItem>(len);
    }

    for (int i = 0; i < len; i++) {
      RenderableDataItem row = new RenderableDataItem();

      row.ensureCapacity(count);
      outList.add(row);

      RenderableDataItem orow = list.get(i);

      row.setColumnSpan(orow.getColumnSpan());
      row.setLinkedData(orow.getLinkedData());

      for (int col = startColumn; col <= endColumn; col++) {
        row.add(orow.getItemEx(col));
      }
    }

    return outList;
  }

  public static int calculateColumnSizes(iPlatformComponent comp, Column[] cols, int tableWidth, boolean fit) {
    if (tableWidth < 1) {
      return 0;
    }

    List<Column> llist       = null;
    Column       leftOverCol = null;
    int          size        = 0;
    int          w;
    int          columnCount = cols.length;
    Column       c;
    int          leftOver;
//
//    if (columnCount == 1) {
//      cols[0].setWidth(tableWidth - 1);    // -margin);
//
//      return 0;
//    }

    for (int i = 0; i < columnCount; i++) {
      c = cols[i];

      if ((c.preferedWidth == 0) && c.isVisible()) {
        if (leftOverCol == null) {
          leftOverCol = c;
        } else {
          if (llist == null) {
            llist = new ArrayList<Column>();
            llist.add(leftOverCol);
          }

          llist.add(c);
        }

        continue;
      }

      w = c.calculatePreferedWidth(comp, tableWidth);
      c.setWidth(w);

      if (c.isVisible()) {
        size += w;
      }
    }

    size     = tableWidth - size;
    leftOver = size;

    if ((size > 0) && (columnCount > 0)) {
      if (llist != null) {
        int len = llist.size();

        size /= len;

        for (int i = 0; i < len; i++) {
          llist.get(i).setWidth(size);
        }
      } else if (leftOverCol != null) {
        leftOverCol.setWidth(size);
      } else {
        int len = 0;

        for (int i = 0; i < columnCount; i++) {
          c = cols[i];

          if (!c.sizeFixed && c.isVisible()) {
            len++;
          }
        }

        if (len > 0) {
          size /= len;

          for (int i = 0; i < columnCount; i++) {
            c = cols[i];

            if (!c.sizeFixed && c.isVisible()) {
              c.setWidth(size + c.getWidth());
            }
          }
        }
      }
    } else if ((size < 0) && (columnCount > 0) && fit) {
      if (llist != null) {
        int len = llist.size();

        size = (int) Math.ceil((float) -size / (float) len);

        for (int i = 0; i < len; i++) {
          c = llist.get(i);
          c.setWidth(Math.max(0, c.getWidth() + size));
        }
      } else if (leftOverCol != null) {
        leftOverCol.setWidth(Math.max(0, leftOverCol.getWidth() + size));
      }
    }

    return leftOver;
  }

  public static void calculateItemSize(iPlatformComponent component, iPlatformItemRenderer renderer, Column col,
          RenderableDataItem item, int row, RenderableDataItem rowItem, UIDimension size, int maxWidth, int minHeight) {
    iPlatformComponent c = item.getRenderingComponent();

    if (c != null) {
      c.getPreferredSize(size, maxWidth);
    } else {
      iPlatformRenderingComponent rc = (col == null)
                                       ? null
                                       : col.getCellRenderer();

      if (rc == null) {
        rc = computeSizeRenderer;

        if (rc == null) {
          rc = computeSizeRenderer = new UILabelRenderer();
        }
      }

      CharSequence text = renderer.configureRenderingComponent(component, rc, item, row, false, false, col, rowItem);

      rc.getComponent(text, item).getPreferredSize(size, maxWidth);
      size.height = Math.max(size.height, minHeight);
    }
  }

  public static void calculateListSize(List<RenderableDataItem> list, iPlatformComponent component,
          iPlatformItemRenderer renderer, Column col, UIDimension size, int maxItems, int maxWidth, int minHeight) {
    float width  = 0;
    float height = 0;
    int   len    = list.size();

    if (maxItems == -1) {
      maxItems = getMaxRowsForSizeCalculation();
    }

    if (maxItems < 1) {
      maxItems = len;
    }

    len = Math.min(len, maxItems);

    for (int i = 0; i < len; i++) {
      calculateItemSize(component, renderer, col, list.get(i), i, null, size, maxWidth, minHeight);
      width  = Math.max(width, size.width);
      height += size.height;
    }

    size.width  = width;
    size.height = height;
  }

  public static int calculateRowHeight(iPlatformComponent component, iPlatformItemRenderer renderer,
          iPlatformListDataModel tm, int row, Column[] columns, boolean preferred, int minHeight,int[] viewPositions) {
    RenderableDataItem rowItem = tm.get(row);
    RenderableDataItem item;
    int                len    = columns.length;
    float              height = 0;
    UIDimension        size   = new UIDimension();
    Column             c;

    for (int i = 0; i < len; i++) {
      c = columns[i];

      if (c.isVisible()) {
        item = rowItem.getItemEx(i);

        if (item != null) {
          int w;
          int span = item.getColumnSpan();

          if (span == -1) {
            span = len - i;
          } else if (span == 0) {
            span = 1;
          }

          if (preferred) {
            w = 0;
          } else {
            w = getSpanWidth(i, span, columns,viewPositions);
          }

          calculateItemSize(component, renderer, c, item, row, rowItem, size, w, minHeight);
          i += (span - 1);
        }
      }

      if (size.height > height) {
        height = size.height;
      }
    }

    return (int) Math.ceil(height);
  }

  public static UIColor getBottomMarginColor(UIColor marginColor) {
    UIColor bottomMarginColor = Platform.getUIDefaults().getColor("Rare.TableHeader.bottomMarginColor");

    if (bottomMarginColor == null) {
      bottomMarginColor = marginColor;
    }

    return bottomMarginColor;
  }

  public static PaintBucket getDefaultPainter(UIColor bg) {
    PaintBucket pb = Platform.getUIDefaults().getPaintBucket("Rare.TableHeader.background");

    if (pb != null) {
      return pb;
    }

    UIColor             c  = (bg == null)
                             ? ColorUtils.getBackground()
                             : bg;
    UIBackgroundPainter bp = (UIBackgroundPainter) UIBackgroundPainter.BGCOLOR_GRADIENT_PAINTER_MID.clone();

    bp.setBackgroundColor(c);;
    pb = new PaintBucket(bp);

    if (bg == ColorUtils.getBackground()) {
      Platform.getUIDefaults().put("Rare.TableHeader.background", pb);
    }

    return pb;
  }

  public static int getDefaultPreferredRows() {
    if (defaultPreferredRows == 0) {
      defaultPreferredRows = Platform.getUIDefaults().getInt("Rare.List.defaultPreferredRows", 1);
    }

    return defaultPreferredRows;
  }

  public static UIColor getMarginColor() {
    UIColor marginColor = Platform.getUIDefaults().getColor("Rare.TableHeader.marginColor");

    if (marginColor == null) {
      marginColor = UILineBorder.getDefaultLineColor();
    }

    return marginColor;
  }

  public static int getMaxRowsForSizeCalculation() {
    if (maxRowsForSizeCalculation == -1) {
      maxRowsForSizeCalculation = Platform.getUIDefaults().getInt("Rare.List.maxRowsForSizeCalculation", 100);
    }

    return maxRowsForSizeCalculation;
  }

  public static int getMinimumListHeight(iPlatformComponent list, int minVisibleRowCount, int rowHeight) {
    int   h   = rowHeight;
    float len = (minVisibleRowCount == 0)
                ? 1
                : minVisibleRowCount;

    if (len == 0) {
      len = 1;
    }

    if (h < 1) {
      h = ScreenUtils.lineHeight(list);
    }

    h *= len;

    return h;
  }

  public static int getPreferredListHeight(iPlatformComponent list, int visibleRowCount, int rowHeight,
          int numberOfRows) {
    int   h   = rowHeight;
    float len = visibleRowCount;

    if (len == 0) {
      len = getDefaultPreferredRows();

      if ((numberOfRows < len) && (numberOfRows > 0)) {
        len = numberOfRows;
      }
    }

    if (len == 0) {
      len = 1;
    }

    if (h < 1) {
      h = ScreenUtils.lineHeight(list);
    }

    h *= len;

    return h;
  }

  public static PaintBucket getPressedPainter(UIColor bg) {
    PaintBucket pb = Platform.getUIDefaults().getPaintBucket("Rare.TableHeader.pressedBackground");

    if (pb == null) {
      UIColor             c  = (bg == null)
                               ? ColorUtils.getBackground()
                               : bg;
      UIBackgroundPainter bp = (UIBackgroundPainter) UIBackgroundPainter.BGCOLOR_GRADIENT_PAINTER_DK_DK.clone();

      bp.setBackgroundColor(c);;
      pb = new PaintBucket(bp);

      if (c == ColorUtils.getBackground()) {
        Platform.getUIDefaults().put("Rare.TableHeader.pressedBackground", pb);
      }
    }

    return pb;
  }

  public static int getSpanWidth(int start, int span, Column[] columns,int[] viewPositions) {
    int width = 0;
    int len   = columns.length;

    if (span == -1) {
      span = len;
    }

    span += start;

    if (span > len) {
      span = len;
    }

    float d = ScreenUtils.PLATFORM_PIXELS_1;

    while(start < span) {
      Column c;
      if(viewPositions!=null) {
        c=columns[viewPositions[start++]];
      }else {
        c= columns[start++];
      }

      if (c.isVisible()) {
        width += c.getWidth() + d;
      }
    }

    if (width > 0) {
      width -= d;
    }

    return width;
  }

  public static int getPreferredSpanWidth(iPlatformComponent comp, int tableWidth, int start, int span,
          Column[] columns,int[] viewPositions) {
    int width = 0;
    int len   = columns.length;

    if (span == -1) {
      span = len;
    }

    span += start;

    if (span > len) {
      span = len;
    }

    float d = ScreenUtils.PLATFORM_PIXELS_1;

    while(start < span) {
      Column c;
      if(viewPositions!=null) {
        c=columns[viewPositions[start++]];
      }else {
        c= columns[start++];
      }
      if (c.isVisible()) {
        width += c.calculatePreferedWidth(comp, tableWidth) + d;
      }
    }

    if (width > 0) {
      width -= d;
    }

    return width;
  }
}
