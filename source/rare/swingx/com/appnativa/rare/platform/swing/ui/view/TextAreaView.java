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

package com.appnativa.rare.platform.swing.ui.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import javax.swing.JTextArea;
import javax.swing.JToolTip;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.AbstractDocument;
import javax.swing.text.Document;

import com.appnativa.rare.Platform;
import com.appnativa.rare.platform.PlatformHelper;
import com.appnativa.rare.platform.swing.ui.text.DocumentChangeListener;
import com.appnativa.rare.platform.swing.ui.util.SwingGraphics;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.listener.iTextChangeListener;
import com.appnativa.rare.ui.painter.iPainter;
import com.appnativa.rare.ui.painter.iPainterSupport;
import com.appnativa.rare.ui.painter.iPlatformComponentPainter;

/**
 *
 *
 * @version    0.3, 2007-05-14
 * @author     Don DeCoteau
 */
public class TextAreaView extends JTextArea implements iPainterSupport, iView, CaretListener {
  DocumentChangeListener    changeListener;
  iPlatformComponentPainter componentPainter;
  AffineTransform           transform;
  protected int             columnWidth;
  protected SwingGraphics   graphics;
  private boolean           changeEventsEnabled=true;

  public TextAreaView() {
    super();
    putClientProperty("Rare.print.scaleToFit", Boolean.TRUE);
    addCaretListener(this);
  }

  public void addTextChangeListener(iTextChangeListener l) {
    listenerList.add(iTextChangeListener.class, l);

    if (changeListener == null) {
      changeListener = new DocumentChangeListener(this);
      changeListener.setChangeEventsEnabled(changeEventsEnabled);
      this.getDocument().addDocumentListener(changeListener);
      ((AbstractDocument) getDocument()).setDocumentFilter(changeListener);
    }
  }
  @Override
  public void setForeground(Color fg) {
    super.setForeground(fg);
    if(fg!=null) {
      setCaretColor(fg);
    }
  }
  

  @Override
  public void caretUpdate(CaretEvent e) {
    PlatformHelper.handleSelectionChange(Platform.findPlatformComponent(this), e.getDot() != e.getMark());
  }

  @Override
  public JToolTip createToolTip() {
    return JToolTipEx.createNewToolTip(this);
  }

  @Override
  public void paint(Graphics g) {
    Graphics2D      g2 = (Graphics2D) g;
    AffineTransform tx = g2.getTransform();

    if (transform != null) {
      g2.transform(transform);
    }

    graphics = SwingGraphics.fromGraphics(g2, this, graphics);
    super.paint(g2);

    if (tx != null) {
      g2.setTransform(tx);
    }

    graphics.clear();
  }

  public void refresh() {
    Document doc = this.getDocument();

    this.setDocument(doc);
  }

  public void removeTextChangeListener(iTextChangeListener l) {
    listenerList.add(iTextChangeListener.class, l);
  }

  public void replaceText(int offset, int length, String text) {
    try {
      ((AbstractDocument) getDocument()).replace(offset, length, text, null);
    } catch(Exception ignore) {
      Platform.ignoreException(null, ignore);
    }
  }

  @Override
  public void setComponentPainter(iPlatformComponentPainter cp) {
    this.componentPainter = cp;
  }

  @Override
  public void setFont(Font f) {
    columnWidth = 0;
    super.setFont(f);
  }

  @Override
  public void setTransformEx(AffineTransform tx) {
    transform = tx;
  }

  public void setVisibleLines(int lines) {
    setRows(lines);
  }

  @Override
  public iPlatformComponentPainter getComponentPainter() {
    return componentPainter;
  }
  
  @Override
  public Color getDisabledTextColor() {
    Color c=super.getForeground();
    if(c instanceof UIColor) {
      return ((UIColor)c).getDisabledColor();
    }
    return super.getDisabledTextColor();
  }


  @Override
  public void getMinimumSize(UIDimension size, int maxWidth) {
    Dimension d = getMinimumSize();

    size.width  = d.width;
    size.height = d.height;
  }
 
  @Override
  public Dimension getPreferredSize() {
    Dimension d = super.getPreferredSize();

    if ((getColumns() == 0) &&!isPreferredSizeSet() && (this.getDocument().getLength() == 0)) {
      int w = this.getColumnWidth() * 10;
      int h = this.getRowHeight() * Math.max(2, getRows());

      if (d.width < w) {
        d.width = w;
      }

      if (d.height < h) {
        d.height = h;
      }
    }

    return d;
  }

  @Override
  public void getPreferredSize(UIDimension size, int maxWidth) {
    if(maxWidth>0 ) {
      setSize(maxWidth,Short.MAX_VALUE);
    }
    Dimension d = getPreferredSize();

    size.width  = d.width;
    size.height = d.height;
  }

  @Override
  public AffineTransform getTransformEx() {
    return transform;
  }

  public boolean hasValue() {
    return getDocument().getLength() > 0;
  }

  @Override
  public boolean isOpaque() {
    return false;
  }

  @Override
  protected void paintBorder(Graphics g) {
    if (componentPainter == null) {
      super.paintBorder(g);
    }
  }

  @Override
  protected void paintChildren(Graphics g) {
    super.paintChildren(graphics.getGraphics());

    iPlatformComponentPainter cp = getComponentPainter();

    if (cp != null) {
      float height = getHeight();
      float width  = getWidth();

      cp.paint(graphics, 0, 0, width, height, iPainter.HORIZONTAL, true);
    }
  }

  @Override
  protected void paintComponent(Graphics g) {
    iPlatformComponentPainter cp = getComponentPainter();

    if (cp != null) {
      float height = getHeight();
      float width  = getWidth();

      cp.paint(graphics, 0, 0, width, height, iPainter.HORIZONTAL, false);
    }

    super.paintComponent(g);
  }

  @Override
  protected int getColumnWidth() {
    if (getClientProperty("RARE_USE_HTML_WIDTH") != Boolean.TRUE) {
      return super.getColumnWidth();
    }

    if (columnWidth == 0) {
      FontMetrics fm = getFontMetrics(getFont());

      columnWidth = fm.charWidth('x');

      if (fm.charWidth('W') != columnWidth) {
        columnWidth++;
      }
    }

    return columnWidth;
  }

  public void setChangeEventsEnabled(boolean enabled) {
    changeEventsEnabled= enabled;

    if (changeListener != null) {
      changeListener.setChangeEventsEnabled(enabled);
    }
  }
}
