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

package com.appnativa.rare.platform.swing.plaf;

import com.appnativa.rare.platform.swing.ui.view.JListEx;
import com.appnativa.rare.ui.UIColor;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;

import javax.swing.JComponent;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.plaf.ComponentUI;

/**
 * Fixes issue where cell don't respect non-rectangular clip shape
 */
public class BasicListExUI extends javax.swing.plaf.basic.BasicListUI {
  int            reorderingRow = -1;
  int            dividerSize;
  Shape          paintClip;
  AlphaComposite reorderingComposite;
  int            reorderingRowHeight;
  boolean        reorderingRowPainted;
  int            reorderingRowY;

  public static ComponentUI createUI(JComponent list) {
    return new BasicListExUI();
  }

  @Override
  public void paint(Graphics g, JComponent c) {
    paintClip = g.getClip();

    if (c instanceof JListEx) {
      dividerSize = (int) Math.floor(((JListEx) c).getDividerSize());
    }

    reorderingRowPainted = reorderingRow == -1;
    super.paint(g, c);

    if (!reorderingRowPainted) {
      Rectangle rowBounds = getCellBounds(list, reorderingRow, reorderingRow);

      if (rowBounds != null) {
        ListCellRenderer   renderer  = list.getCellRenderer();
        ListModel          dataModel = list.getModel();
        ListSelectionModel selModel  = list.getSelectionModel();

        if (renderer != null) {
          paintCell(g, reorderingRow, rowBounds, renderer, dataModel, selModel, -1);
        }
      }
    }
  }

  @Override
  public void updateLayoutState() {
    super.updateLayoutState();

    if (cellHeights != null) {
      JListEx jlist = (JListEx) list;
      int     pad   = (jlist.getDividerLineColor() != null)
                      ? 2
                      : 0;
      int     min   = jlist.getMinRowHeight();

      if ((pad > 0) || (min > 0)) {
        int len = cellHeights.length;

        for (int i = 0; i < len; i++) {
          int n = Math.max(min, cellHeights[i]);

          cellHeights[i] = n + pad;
        }
      }
    }
  }

  public void setReordingInfo(int row, int y, int height) {
    reorderingRow       = row;
    reorderingRowY      = y;
    reorderingRowHeight = height;
  }

  public void setReordingY(int y) {
    reorderingRowY = y;
  }

  @Override
  protected void paintCell(Graphics g, int row, Rectangle rowBounds, ListCellRenderer cellRenderer,
                           ListModel dataModel, ListSelectionModel selModel, int leadIndex) {
    int       cx = rowBounds.x;
    int       cy = rowBounds.y;
    int       cw = rowBounds.width;
    int       ch = rowBounds.height - dividerSize;
    Composite c  = null;

    if (reorderingRow != -1) {
      if (row == reorderingRow) {
        reorderingRowPainted = true;
        cy                   = reorderingRowY;

        if (reorderingComposite == null) {
          reorderingComposite = AlphaComposite.SrcOver.derive(0.75f);
        }

        c = ((Graphics2D) g).getComposite();

        if (c == null) {
          c = AlphaComposite.SrcOver;
        }

        ((Graphics2D) g).setComposite(reorderingComposite);
      } else if (row < reorderingRow) {
        if (cy >= reorderingRowY) {
          cy += reorderingRowHeight;
        }
      } else {
        if (cy < reorderingRowY) {
          cy -= reorderingRowHeight;
        }
      }
    }

    g.setClip(paintClip);
    g.clipRect(cx, cy, cw, ch);

    Object    value             = dataModel.getElementAt(row);
    boolean   cellHasFocus      = list.hasFocus() && (row == leadIndex);
    boolean   isSelected        = (row == reorderingRow) || selModel.isSelectedIndex(row);
    Component rendererComponent = cellRenderer.getListCellRendererComponent(list, value, row, isSelected, cellHasFocus);

    rendererPane.paintComponent(g, rendererComponent, list, cx, cy, cw, ch, true);

    if (c != null) {
      ((Graphics2D) g).setComposite(c);

      Color color = g.getColor();

      g.setColor(UIColor.black);
      g.drawRect(cx, cy, cw - 1, ch - 1);
      g.setColor(color);
    }
  }
}
