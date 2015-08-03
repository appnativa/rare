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

package com.appnativa.rare.ui;

import com.appnativa.rare.ui.effects.iTransitionAnimator;
import com.appnativa.rare.ui.iTabDocument.iDocumentListener;
import com.appnativa.rare.ui.listener.iTabPaneListener;
import com.appnativa.rare.ui.painter.PaintBucket;
import com.appnativa.rare.viewer.iTabPaneViewer;

public interface iTabPaneComponent {
  int addTab(iTabDocument doc);

  void addTabPaneListener(iTabPaneListener l);

  void beginTabsUpdate();

  void checkOrientation(Object newConfig);

  void closeAll();

  void closeAllBut(int pos);

  void closeTab(int pos);

  void endTabsUpdate();

  int indexOfDocument(iTabDocument doc);

  int indexOfTab(iPlatformIcon icon);

  int indexOfTab(String title);

  void removeTabPaneListener(iTabPaneListener l);

  void setBoldSelectedTab(boolean bold);

  void setEnabledAt(int pos, boolean enable);

  void setFont(UIFont f);

  void setIconAt(int pos, iPlatformIcon icon);

  void setSelectedIndex(int index);

  void setSelectedPainter(PaintBucket pb);

  void setShowCloseButton(boolean b);

  void setShowCloseButtonOnTab(boolean b);

  void setShowIconsOnTab(boolean show);

  void setTabAreaPainter(PaintBucket pb);

  void setTabAreaMargin(UIInsets margin);

  void setTabLeadingComponent(iPlatformComponent c);

  void setTabMinHeight(int height);

  void setTabPainter(PaintBucket pb);

  void setTabPlacement(Location location);

  void setTabShape(iTabPaneViewer.Shape shape);

  void setTabTrailingComponent(iPlatformComponent c);

  void setTitleAt(int pos, String title);

  void setTransitionAnimator(iTransitionAnimator animator);

  iTransitionAnimator getTransitionAnimator();

  int getChangeIndex();

  iPlatformComponent getComponent();

  iTabDocument getDocumentAt(int index);

  iDocumentListener getDocumentListener();

  iPlatformIcon getIconAt(int pos);

  iTabDocument getSelectedDocument();

  int getSelectedIndex();

  int getTabCount();

  int getTabMinHeight();

  Location getTabPlacement();

  UIRectangle getTabStripBounds();

  iPlatformComponent getTabStrip();

  String getTitleAt(int pos);

  void setMoreTabsIcon(iPlatformIcon icon);

  boolean getTabStripsFloats();

  void setTabStripsFloats(boolean floats);

  void setReloadTimeout(long reloadTimeout);

  long getReloadTimeout();
}
