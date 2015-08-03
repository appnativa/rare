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

package com.appnativa.rare.platform.apple.ui.view;

import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.ui.painter.PaintBucket;

public class SectionIndex {
  public String[]           indextitles;
  public PaintBucket        painter;
  public int[]              position;
  public int[]              length;
  public String[]           titles;
  public RenderableDataItem sectionPrototype;
  private int[]             indexPath = new int[2];

  public SectionIndex() {}

  public SectionIndex(String[] titles, int[] position, int[] length, RenderableDataItem prototype) {
    super();
    this.titles      = titles;
    this.position    = position;
    this.length      = length;
    this.indextitles = titles;
    sectionPrototype = prototype;
  }

  public int getSectionCount() {
    return this.position.length;
  }

  public String getTitleText(int section) {
    return titles[section];
  }

  public String getIndexText(int section) {
    return indextitles[section];
  }

  public int getFlatIndex(int section, int position) {
    return this.position[section] + position;
  }

  public int getSize(int section) {
    return this.length[section];
  }

  public int[] getIndexPathForFlatPosition(int position) {
    int[] p     = this.position;
    int[] l     = this.length;
    int   len   = p.length;
    int[] ip    = indexPath;
    int   index = 0;

    for (int i = 0; i < len; i++) {
      index += l[i];

      if (index > position) {
        index -= l[i];
        ip[0] = i;
        ip[1] = position - index;

        break;
      }
    }

    return ip;
  }
}
