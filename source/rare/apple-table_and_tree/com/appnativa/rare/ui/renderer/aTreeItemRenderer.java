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

import com.appnativa.rare.ui.UIInsets;

public abstract class aTreeItemRenderer extends ListItemRenderer {
  protected boolean showItemIcons = true;
  protected boolean empty;
  protected boolean expanded;
  protected boolean leaf;
  private int       leftOffset;
  private int       selectionLeftOffset = -1;

  public aTreeItemRenderer() {
    super();
  }

  public aTreeItemRenderer(boolean handleSelctionBackground) {
    super(handleSelctionBackground);
  }

  public void prepareForEmptyItem() {
    empty = true;
  }

  @Override
  public void setInsets(UIInsets insets) {
    super.setInsets(insets);
    leftOffset     = insets.intLeft();
    selectedInsets = null;

    UIInsets in = getSelectesInsets();

    if (in != null) {
      selectionLeftOffset = in.intLeft();
    } else {
      selectionLeftOffset = -1;
    }
  }

  public void setItemState(boolean isLeaf, boolean isExpanded, int indent) {
    leaf        = isLeaf;
    expanded    = isExpanded;
    insets.left = leftOffset + indent;
    empty       = false;

    if (selectedInsets == null) {
      UIInsets in = getSelectesInsets();

      if (in != null) {
        selectionLeftOffset = in.intLeft();
      } else {
        selectionLeftOffset = -1;
      }
    }

    if (selectionLeftOffset > -1) {
      selectedInsets.left = selectionLeftOffset + indent;
    }
  }

  public int getLeftOffset() {
    return leftOffset;
  }
}