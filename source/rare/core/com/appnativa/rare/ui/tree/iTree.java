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

package com.appnativa.rare.ui.tree;

import com.appnativa.rare.ui.RenderableDataItem;

public interface iTree {
  void setExpansionHandler(iExpansionHandler handler);

  void setIndentBy(int indent);

  void setRootNodeCollapsible(boolean collapsible);

  void setShowRoot(boolean show);

  void setShowRootHandles(boolean show);

  /**
   * Sets whether a single click toggles node collapse/expansion.
   * If true then clicking anywhere on an expandable node toggles its expansion
   * (unless toggle on twisty only is also true).
   *
   * The default behavior is a double click toggles on non-touch devices and a single click on touch devices.
   *
   * @param singleClickToggle true if the node is to be  toggled on a single click; false for adouble click
   */
  void setSingleClickToggle(boolean singleClickToggle);

  /**
   * Sets whether to toggle node collapse/expansion only when the twisty is clicked.
   * If true then clicking anywhere other than the twisty on an expandable node
   * will result if the same behavior as a non expandable node. The default is false
   *
   * @param twistyOnly true if the node is to be  toggled only when the twisty is clicked; false otherwise
   */
  void setToggleOnTwistyOnly(boolean twistyOnly);

  /**
   * Gets the margin of error for detecting touches on the twisty
   * when using a touch device. Touches extending from the right edge of the twisty
   * to the margin of error amount are considered to be touching on the twisty.
   *
   * @return  the twisty margin of error.
   */
  int getTwistyMarginOfError();

  /**
   * Gets whether the expandable state of the tree is locked
   *
   * @return true if the user can't expand or collapse nodes; false otherwise
   */
  boolean isExpandableStateLocked();

  /**
   * Sets whether the expandable state of the tree is locked
   *
   * @param locked true if the user can't expand or collapse nodes; false otherwise
   */
  void setExpandableStateLocked(boolean locked);

  /**
   * Sets the margin of error for detecting touches on the twisty
   * when using a touch device. Touches extending from the right edge of the twisty
   * to the margin of error amount are considered to be touching on the twisty.
   *
   * @param twistyMarginOfError the twisty margin of error.
   */
  void setTwistyMarginOfError(int twistyMarginOfError);

  iTreeItem getTreeItem(RenderableDataItem item);

  boolean isRootNodeCollapsible();

  boolean isShowRoot();

  /**
   * Gets whether a single click toggles node collapse/expansion.
   *
   * @return true if the node is to be  toggled on a single click; false for adouble click
   */
  boolean isSingleClickToggle();

  /**
   * Gets whether to toggle node collapse/expansion only when the twisty is clicked.
   * If true then clicking anywhere other than the twisty on an expandable node
   * will result if the same behavior as a non expandable node
   *
   * @return true if the node is toggled only when the twisty is clicked; false otherwise
   */
  boolean isToggleOnTwistyOnly();

  int getIndentBy();

  int getIndicatorWidth();
}
