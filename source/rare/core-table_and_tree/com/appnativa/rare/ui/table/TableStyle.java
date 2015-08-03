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

import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UICursor;
import com.appnativa.rare.ui.UIFont;
import com.appnativa.rare.ui.UIStroke;
import com.appnativa.rare.ui.iPlatformIcon;
import com.appnativa.rare.ui.painter.PaintBucket;

/**
 *
 *
 * @version    0.3, 2007-05-14
 * @author     Don DeCoteau
 */
public class TableStyle implements Cloneable {
  public UIStroke            gridLineStroke            = null;
  public int                 headerHotspotSize         = 0;
  public boolean             showHorizontalLines       = false;
  public boolean             showVerticalLines         = false;
  public boolean             hiliteSortColumn          = true;
  public boolean             fixedRowSize              = true;
  public boolean             extendBackgroundRendering = true;
  public boolean             columnSortingAllowed      = true;
  public boolean             columnSelectionAllowed    = true;
  public boolean             columnResizingAllowed     = true;
  public boolean             columnReorderingAllowed   = true;
  public BackgroundHighlight backgroundHilite;
  public UIColor             backgroundHiliteColor;
  public boolean             colHeaderHotspotsSupported;
  public UIColor             gridColor;
  public String              headerAction;
  public UIFont              headerFont;
  public UIColor             headerForeground;
  public UIColor             headerMarginColor;
  public UIColor             headerBottomMarginColor;
  public String              headerHotspotAction;
  public UICursor            headerHotspotCursor;
  public iPlatformIcon       headerHotspotIcon;
  public PaintBucket         headerCellPainter;
  public PaintBucket         headerFillerPainter;
  public boolean             rowHeaderFooterSelectionPainted;
  public boolean             showFocusRectangle;
  public UIColor             sortColumnHiliteColor;

  public static enum BackgroundHighlight { ROW, COLUMN }

  /**
   * Constructs a new instance
   */
  public TableStyle() {}

  public TableStyle copy() {
    try {
      return (TableStyle) clone();
    } catch(CloneNotSupportedException ex) {
      return null;
    }
  }
}
