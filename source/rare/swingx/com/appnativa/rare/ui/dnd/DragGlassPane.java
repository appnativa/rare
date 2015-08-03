/*
 * @(#)DragGlassPane.java   2009-04-26
 *
 * Copyright (c) appNativa Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.ui.dnd;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.swing.Icon;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.SwingUtilities;

import com.appnativa.rare.Platform;
import com.appnativa.rare.platform.swing.ui.util.Java2DUtils;

/**
 * Glass pane for drag and drop operations
 *
 * @author Don DeCoteau
 */
public class DragGlassPane extends JPanel {
  Point                  endPoint;
  boolean                insertMode;
  Rectangle              itemBounds;
  boolean                onMode;
  Component              savedPane;
  boolean                showDragImage;
  Point                  startPoint;
  private BufferedImage  dragImage       = null;
  private Point          dropPoint       = new Point(0, 0);
  Rectangle              lastDropRect    = new Rectangle();
  Rectangle              lastDragRect    = new Rectangle();
  private Color          itemBoundsColor = Platform.getUIDefaults().getColor("textHighlight");
  private AlphaComposite composite;
  private int            imageHeight;
  private int            imageWidth;

  public DragGlassPane() {
    setOpaque(false);
    composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
  }

  public void install(Component c, Icon icon, Point p) {
    uninstall();

    JRootPane root = SwingUtilities.getRootPane(c);

    if (root != null) {
      if (icon != null) {
        showDragImage = Platform.getUIDefaults().getBoolean("Rare.dnd.showDragImage", false);
        dragImage     = Java2DUtils.createImage(this, icon);
        imageWidth    = dragImage.getWidth();
        imageHeight   = dragImage.getHeight();
      } else {
        showDragImage = false;
      }

      savedPane = root.getGlassPane();
      root.setGlassPane(this);
      startPoint = null;
      endPoint   = null;
      itemBounds = null;
      this.setVisible(true);
      setDragPoint(p);
    }
  }

  /**
   * {@inheritDoc}
   *
   * @param g {@inheritDoc}
   */
  @Override
  public void paintComponent(Graphics g) {
    Graphics2D g2d     = (Graphics2D) g;
    Composite  oldComp = g2d.getComposite();

    do {
      if ((startPoint == null) || (endPoint == null)) {
        if (!onMode) {
          break;
        }

        if (itemBounds != null) {
          g.setColor(itemBoundsColor);
          g2d.setComposite(composite);
          g2d.fillRect(itemBounds.x, itemBounds.y, itemBounds.width, itemBounds.height);
          lastDropRect.setBounds(itemBounds);
        }
      } else if (insertMode) {
        g.setColor(Color.black);

        int x1 = startPoint.x;
        int x2 = endPoint.x;
        int y1 = startPoint.y;

        // left joiner
        g.drawLine(x2, y1 - 2, x2, y1 + 3);
        g.drawLine(x2 - 1, y1 - 1, x2 - 1, y1 + 2);
        // line
        g.drawLine(x1 + 2, y1, x2 - 2, y1);
        g.drawLine(x1 + 2, y1 + 1, x2 - 2, y1 + 1);
        // right joiner
        g.drawLine(x1, y1 - 2, x1, y1 + 3);
        g.drawLine(x1 + 1, y1 - 1, x1 + 1, y1 + 2);
        lastDropRect.setBounds(x1, y1 - 2, x2 - x1 + 1, 6);
      }
    } while(false);

    if ((dragImage != null) && showDragImage) {
      lastDragRect.setBounds((int) (dropPoint.getX() - (imageWidth / 2)), (int) (dropPoint.getY() - (imageHeight / 2)),
                             imageWidth, imageHeight);
      g2d.setComposite(composite);
      g2d.drawImage(dragImage, lastDragRect.x, lastDragRect.y, null);
    }

    g2d.setComposite(oldComp);
  }

  public void uninstall() {
    this.setVisible(false);

    if (savedPane != null) {
      JRootPane root = SwingUtilities.getRootPane(this);

      if (root != null) {
        root.setGlassPane(savedPane);
        savedPane.setVisible(false);
        savedPane = null;
      }
    }
  }

  public void setDragPoint(Point p) {
    if (showDragImage) {
      SwingUtilities.convertPointFromScreen(p, this);

      if ((dropPoint != null) && dropPoint.equals(p)) {
        return;
      }

      dropPoint = p;
      SwingUtilities.computeUnion((int) (p.getX() - (imageWidth / 2)), (int) (p.getY() - (imageHeight / 2)),
                                  imageWidth, imageHeight, lastDragRect);
      repaint(lastDragRect);
    }
  }

  public void setDropMode(boolean on, boolean insert) {
    insertMode = insert;
    onMode     = on;
    startPoint = null;
    endPoint   = null;
    itemBounds = null;
    lastDropRect.setBounds(0, 0, 0, 0);
  }

  public void setDropPoint(Point start, Point end, Rectangle item) {
    if ((start == null) && (end == null) && (item == null) && (startPoint == null) && (endPoint == null)
        && (itemBounds == null)) {
      return;
    }

    startPoint = start;
    endPoint   = end;
    itemBounds = item;

    if ((startPoint != null) && (endPoint != null)) {
      int x1 = startPoint.x;
      int x2 = endPoint.x;
      int y1 = startPoint.y;

      SwingUtilities.computeUnion(x1, y1 - 2, x2 - x1 + 1, 6, lastDropRect);
    } else if (itemBounds != null) {
      SwingUtilities.computeUnion(itemBounds.x, itemBounds.y, itemBounds.width, itemBounds.height, lastDropRect);
    }

    repaint(lastDropRect);
  }

  /**
   * @param itemBoundsColor the itemBoundsColor to set
   */
  public void setItemBoundsColor(Color itemBoundsColor) {
    this.itemBoundsColor = itemBoundsColor;
  }

  public BufferedImage getDragImage() {
    return dragImage;
  }

  /**
   * @return the itemBoundsColor
   */
  public Color getItemBoundsColor() {
    return itemBoundsColor;
  }
}
