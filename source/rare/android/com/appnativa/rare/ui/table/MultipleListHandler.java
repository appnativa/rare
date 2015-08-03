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

import com.appnativa.rare.platform.android.ui.ListSynchronizer;
import com.appnativa.rare.platform.android.ui.ListViewProxy;
import com.appnativa.rare.platform.android.ui.view.ListViewEx;
import com.appnativa.rare.ui.iPlatformListHandler;

public class MultipleListHandler extends aMultipleListHandler {
  public MultipleListHandler(MultiTableTableComponent mtc, iPlatformListHandler main, iPlatformListHandler handler1,
                             iPlatformListHandler handler2) {
    super(mtc, new ListViewProxy(main), main, handler1, handler2);

    ListViewEx       list = (ListViewEx) main.getListComponent().getView();
    ListSynchronizer s    = new ListSynchronizer(list, true);

    if (handler1 != null) {
      list = (ListViewEx) handler1.getListComponent().getView();
      s.addListView(list);
      list.setListSynchronizer(s);
    }

    if (handler2 != null) {
      list = (ListViewEx) handler2.getListComponent().getView();
      s.addListView(list);
    }
  }
}
