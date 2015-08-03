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

package com.appnativa.rare.platform.android.ui;

import android.view.View;

import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;

import com.appnativa.rare.platform.android.ui.view.ListViewEx;
import com.appnativa.util.IdentityArrayList;

public class ListSynchronizer implements OnScrollListener {
  IdentityArrayList<ListViewEx> lists = new IdentityArrayList<ListViewEx>(3);
  boolean                       syncScroll;
  volatile boolean              checking;
  volatile boolean              scrolling;
  ListViewEx                    scrollingList;
  ListViewEx                    mainList;

  public ListSynchronizer(ListViewEx main, boolean syncScroll) {
    this.syncScroll = syncScroll;
    mainList        = main;
    addListView(main);
  }

  public void sychronizePosition(ListViewEx caller) {
    if (syncScroll &&!scrolling) {
      scrolling = true;

      try {
        int  index = caller.getFirstVisiblePosition();
        View v     = caller.getChildAt(0);
        int  top   = (v == null)
                     ? 0
                     : v.getTop();

        for (ListViewEx lv : lists) {
          if (lv != caller) {
            lv.setSelectionFromTop(index, top);
          }
        }
      } finally {
        scrolling = false;
      }
    }
  }

  public void setItemChecked(ListViewEx caller, int position, boolean checked) {
    if (!checking) {
      if (caller != mainList) {
        mainList.performItemClick(mainList.getViewForRow(position), position, position);

        return;
      }

      checking = true;

      try {
        for (ListViewEx lv : lists) {
          if (lv != caller) {
            lv.setItemChecked(position, checked);
          }
        }
      } finally {
        checking = false;
      }
    }
  }

  public void addListView(ListViewEx list) {
    lists.add(list);
    list.setListSynchronizer(this);
  }

  @Override
  public void onScrollStateChanged(AbsListView view, int scrollState) {
    if (scrollState != 0) {
      if (scrollingList == null) {
        scrollingList = (ListViewEx) view;
      }
    } else {
      scrollingList = null;
    }
  }

  public boolean isSyncScrolling() {
    return syncScroll;
  }

  @Override
  public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
    if (view == scrollingList) {
      sychronizePosition((ListViewEx) view);
    }
  }
}
