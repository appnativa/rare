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

import com.appnativa.rare.platform.apple.ui.ListSynchronizer;
import com.appnativa.rare.platform.apple.ui.view.aPlatformTableBasedView;
import com.appnativa.rare.ui.iListView;
import com.appnativa.rare.ui.iPlatformListHandler;
import com.appnativa.rare.ui.table.MultiTableTableComponent;
import com.appnativa.rare.ui.table.aMultipleListHandler;

public class MultipleListHandler extends aMultipleListHandler {
  ListSynchronizer listSynchronizer;

  public MultipleListHandler(MultiTableTableComponent mtc, iPlatformListHandler main, iPlatformListHandler handler1,
                             iPlatformListHandler handler2) {
    super(mtc, (iListView) main.getListComponent().getView(), main, handler1, handler2);
    listSynchronizer = new ListSynchronizer((aPlatformTableBasedView) main.getListComponent().getView(), true);

    if (handler1 != null) {
      listSynchronizer.addListView((aPlatformTableBasedView) handler1.getListComponent().getView());
    }

    if (handler2 != null) {
      listSynchronizer.addListView((aPlatformTableBasedView) handler2.getListComponent().getView());
    }
  }

  @Override
  public void dispose() {
    super.dispose();
    listSynchronizer.dispose();
  }
}
