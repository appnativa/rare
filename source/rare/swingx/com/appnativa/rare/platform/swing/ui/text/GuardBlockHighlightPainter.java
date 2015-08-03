/*
 * @(#)GuardBlockHighlightPainter.java   2010-03-07
 *
 * Copyright (c) appNativa Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.platform.swing.ui.text;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.util.ArrayList;

import javax.swing.border.Border;
import javax.swing.plaf.TextUI;
import javax.swing.text.BadLocationException;
import javax.swing.text.Highlighter;
import javax.swing.text.JTextComponent;

/**
 * Paints a garded section of text
 *
 * @author Santhosh Kumar T - santhosh@in.fiorano.com
 */
public class GuardBlockHighlightPainter implements Highlighter.HighlightPainter {
  private ArrayList objects = new ArrayList();
  private Border    border;
  private Color     color;

  public GuardBlockHighlightPainter() {
    this(new Color(225, 236, 247));
  }

  public GuardBlockHighlightPainter(Color color) {
    this.color = color;
  }

  public void _paint(Graphics2D g, int offs0, int offs1, Shape bounds, JTextComponent c) {
    Rectangle alloc = bounds.getBounds();

    try {

      // --- determine locations ---
      TextUI    mapper = c.getUI();
      Rectangle p0     = mapper.modelToView(c, offs0);
      Rectangle p1     = mapper.modelToView(c, offs1);

      // --- render ---
      g.setColor(color);

      if (p0.y == p1.y) {

        // same line, render a rectangle
        Rectangle r  = p0.union(p1);
        g.fillRect(r.x, r.y, r.width, r.height);

        if (border != null) {
          border.paintBorder(c, g, r.x, r.y, r.width, r.height);
        }
      } else {

        // different lines
        int p0ToMarginWidth = alloc.x + alloc.width - p0.x;

        g.fillRect(p0.x, p0.y, p0ToMarginWidth, p0.height);

        if ((p0.y + p0.height) != p1.y) {
          g.fillRect(alloc.x, p0.y + p0.height, alloc.width, p1.y - (p0.y + p0.height));
        }

        g.fillRect(alloc.x, p1.y, (p1.x - alloc.x), p1.height);
      }
    } catch(BadLocationException e) {

      // can't render
    }
  }

  public void addHilight(JTextComponent comp, int fromOffset, int toOffset) throws BadLocationException {
    objects.add(comp.getHighlighter().addHighlight(fromOffset, toOffset, this));
  }

  public void clear(JTextComponent comp) {
    Highlighter h = comp.getHighlighter();
    for (Object o : objects) {
      h.removeHighlight(o);
    }

    objects.clear();
  }

  @Override
  public void paint(Graphics g, int p0, int p1, Shape bounds, JTextComponent c) {
    _paint((Graphics2D) g, p0 - 1, p1 + 1, bounds, c);    // trick
  }

  /**
   * @param border the border to set
   */
  public void setBorder(Border border) {
    this.border = border;
  }

  /**
   * @param color the color to set
   */
  public void setColor(Color color) {
    this.color = color;
  }

  /**
   * @return the border
   */
  public Border getBorder() {
    return border;
  }

  /**
   * @return the color
   */
  public Color getColor() {
    return color;
  }

}
