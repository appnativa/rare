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

package com.appnativa.rare.platform.apple.ui;

import com.appnativa.rare.platform.apple.ui.view.aPlatformTableBasedView;
import com.appnativa.rare.ui.UIPoint;
import com.appnativa.util.IdentityArrayList;

import com.google.j2objc.annotations.Weak;

public class ListSynchronizer {
  IdentityArrayList<aPlatformTableBasedView> lists = new IdentityArrayList<aPlatformTableBasedView>(3);
  boolean                                    syncScroll;
  volatile boolean                           selecting;
  volatile boolean                           scrolling;
  @Weak
  aPlatformTableBasedView                    scrollingList;
  @Weak
  aPlatformTableBasedView                    mainList;

  public ListSynchronizer(aPlatformTableBasedView main, boolean syncScroll) {
    this.syncScroll = syncScroll;
    mainList        = main;
    addListView(main);
  }

  public void dispose() {
    lists.clear();
  }

  public void sychronizePosition(aPlatformTableBasedView caller) {
    if (syncScroll &&!scrolling) {
      scrolling = true;

      try {
        UIPoint p = caller.getContentOffset();

        for (aPlatformTableBasedView lv : lists) {
          if (lv != caller) {
            lv.setContentOffset(0, p.y);
          }
        }
      } finally {
        scrolling = false;
      }
    }
  }

  public void setSelectedIndex(aPlatformTableBasedView caller, int index, boolean selected, boolean clicked) {
    if (!selecting) {
      if ((caller != mainList) && clicked) {
        mainList.clickRow(index);

        return;
      }

      selecting = true;

      try {
        for (aPlatformTableBasedView lv : lists) {
          if (lv != caller) {
            if (selected) {
              lv.addSelectionIndex(index);
            } else {
              lv.removeSelection(index);
            }
          }
        }
      } finally {
        selecting = false;
      }
    }
  }

  public boolean isMainList(aPlatformTableBasedView list) {
    return list == mainList;
  }

  public void addListView(aPlatformTableBasedView list) {
    lists.add(list);
    list.setListSynchronizer(this);
  }

  public boolean isSyncScrolling() {
    return syncScroll;
  }
}
