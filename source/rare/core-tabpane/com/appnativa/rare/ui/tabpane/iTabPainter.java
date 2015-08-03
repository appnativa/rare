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

package com.appnativa.rare.ui.tabpane;

import com.appnativa.rare.ui.Location;
import com.appnativa.rare.ui.RenderableDataItem.IconPosition;
import com.appnativa.rare.ui.UIAction;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.UIFont;
import com.appnativa.rare.ui.iActionComponent;
import com.appnativa.rare.ui.painter.PaintBucket;

import java.util.List;

public interface iTabPainter {
  public void reset();

  public void tabAdded(UIAction a);

  public void tabRemoved(UIAction a);

  public void setNormalPaint(PaintBucket pb);

  public void setSelectedPaint(PaintBucket pb);

  public int getHeightExtra();

  public int getMinTabHeight();

  public UIAction getTab(int index);

  public int getWidthExtra();

  void setBoldSelectedTab(boolean bold);

  void setEndOffset(int offset);

  void setPosition(Location position);

  void setMinTabHeight(int height);

  void setMinTabWidth(int width);

  void setMoreButton(iActionComponent more);

  void setSelectedTab(int tab);

  void setSelectedTabBorderColor(UIColor lineColor);

  void setShowIconsOnTab(boolean show);

  void setStartOffset(int offset);

  void setStartTab(int tab);

  void setTabBorderColor(UIColor color);

  void setTabFont(UIFont f);

  void setTabs(List<UIAction> tabs);

  UIColor getBackgroundColor();

  int getEndTab();

  IconPosition getIconPosition();

  void getMinimumSize(UIDimension size);

  boolean isShowMoreIconText();

  PaintBucket getNormalPaint();

  void getPreferredSize(UIDimension size);

  PaintBucket getSelectedPaint();

  int getSelectedTab();

  int getStartTab();

  UIColor getTabBorderColor();

  int getTabCount();

  int getTabsPadding();

  boolean isBoldSelectedTab();

  boolean isShowIconsOnTab();
}
