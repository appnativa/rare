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

import com.appnativa.rare.Platform;
import com.appnativa.rare.iConstants;
import com.appnativa.rare.platform.swing.ui.text.DocumentChangeListener;
import com.appnativa.rare.platform.swing.ui.util.SwingGraphics;
import com.appnativa.rare.platform.swing.ui.util.SwingHelper;
import com.appnativa.rare.ui.ColorUtils;
import com.appnativa.rare.ui.FontUtils;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.event.iActionListener;
import com.appnativa.rare.ui.iActionable;
import com.appnativa.rare.ui.listener.iTextChangeListener;
import com.appnativa.rare.ui.painter.iPainter;
import com.appnativa.rare.ui.painter.iPainterSupport;
import com.appnativa.rare.ui.painter.iPlatformComponentPainter;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.geom.AffineTransform;

import javax.swing.JTextField;
import javax.swing.JToolTip;
import javax.swing.text.AbstractDocument;

/**
 *
 *
 * @version    0.3, 2007-05-14
 * @author     Don DeCoteau
 */
public class TextFieldView extends JTextField implements iPainterSupport, iActionable, FocusListener, iView {
  iPlatformComponentPainter      componentPainter;
  AffineTransform                transform;
  protected int                  columnWidth;
  protected SwingGraphics        graphics;
  private boolean                permanentFocusEnabled = true;
  private DocumentChangeListener changeListener;
  private Color                  emptyFieldColor;
  private Font                   emptyFieldFont;
  private String                 emptyFieldText;
  private boolean                shapedBorder;

  public TextFieldView() {
    super();
    setFont(FontUtils.getDefaultFont());
    setForeground(UIColor.BLACK);
    setBackground(UIColor.WHITE);
    setDisabledTextColor(ColorUtils.getDisabledForeground());
  }

  @Override
  public void addActionListener(iActionListener l) {
    listenerList.add(iActionListener.class, l);
  }

  public void addTextChangeListener(iTextChangeListener l) {
    listenerList.add(iTextChangeListener.class, l);

    if (changeListener == null) {
      changeListener = new DocumentChangeListener(this);
      this.getDocument().addDocumentListener(changeListener);
      ((AbstractDocument) getDocument()).setDocumentFilter(changeListener);
    }
  }

  @Override
  public JToolTip createToolTip() {
    return JToolTipEx.createNewToolTip(this);
  }

  @Override
  public void focusGained(FocusEvent e) {
    repaint();
  }

  @Override
  public void focusLost(FocusEvent e) {
    repaint();
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

  @Override
  public void removeActionListener(iActionListener l) {
    listenerList.remove(iActionListener.class, l);
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
  public void requestFocus() {
    if (isPermanentFocusEnabled()) {
      super.requestFocus();
    } else {
      super.requestFocus(true);
    }
  }

  @Override
  public boolean requestFocus(boolean temporary) {
    if (isPermanentFocusEnabled()) {
      return super.requestFocus(temporary);
    } else {
      return super.requestFocus(true);
    }
  }

  @Override
  public boolean requestFocusInWindow() {
    if (isPermanentFocusEnabled()) {
      return super.requestFocusInWindow();
    } else {
      return super.requestFocusInWindow(true);
    }
  }

  @Override
  public void updateUI() {
    super.updateUI();

    if (emptyFieldFont != null) {
      emptyFieldFont = SwingHelper.updateFont(this, emptyFieldFont, true);
    }
  }

  @Override
  public void setComponentPainter(iPlatformComponentPainter cp) {
    this.componentPainter = cp;
  }

  public void setEmptyFieldColor(Color emptyFieldColor) {
    this.emptyFieldColor = emptyFieldColor;
  }

  public void setEmptyFieldFont(Font emptyFieldFont) {
    this.emptyFieldFont = emptyFieldFont;
  }

  public void setEmptyFieldText(String text) {
    this.emptyFieldText = text;
  }

  public void setEmptyFieldValues(String value, Color c, Font f) {
    emptyFieldText  = value;
    emptyFieldColor = c;
    setEmptyFieldFont(f);
    this.removeFocusListener(this);

    if (value != null) {
      this.addFocusListener(this);
    }
  }

  @Override
  public void setFont(Font f) {
    columnWidth = 0;
    super.setFont(f);
  }

  /**
   * Sets whether the component can obtain permanent focus
   *
   * @param permanentFocusEnabled true if the component can obtain permanent focus
   */
  public void setPermanentFocusEnabled(boolean permanentFocusEnabled) {
    this.permanentFocusEnabled = permanentFocusEnabled;
  }

  @Override
  public void setTransformEx(AffineTransform tx) {
    transform = tx;
  }

  @Override
  public iPlatformComponentPainter getComponentPainter() {
    return componentPainter;
  }

  @Override
  public Color getForeground() {
    if (!isEnabled()) {
      Color c = (Color) getClientProperty(iConstants.RARE_DISABLEDCOLOR_PROPERTY);

      if (c != null) {
        return c;
      }
    }

    return super.getForeground();
  }

  @Override
  public void getMinimumSize(UIDimension size) {
    Dimension d = getMinimumSize();

    size.width  = d.width;
    size.height = d.height;
  }

  public String getPlainText() {
    return getText();
  }

  @Override
  public void getPreferredSize(UIDimension size, int maxWidth) {
    Dimension d = getPreferredSize();

    size.width  = d.width;
    size.height = d.height;
  }

  @Override
  public AffineTransform getTransformEx() {
    return transform;
  }

  public boolean hasValue() {
    return (getText() != null) && (getText().length() > 0);
  }

  @Override
  public boolean isOpaque() {
    if ((componentPainter != null) && componentPainter.isBackgroundPaintEnabled()) {
      return false;
    }

    if (getBackground().getAlpha() == 0) {
      return false;
    }

    return !shapedBorder && super.isOpaque();
  }

  public boolean isPermanentFocusEnabled() {
    return permanentFocusEnabled;
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

    if ((emptyFieldText != null) &&!isFocusOwner() && (this.getDocument().getLength() == 0)) {
      if (emptyFieldColor == null) {
        emptyFieldColor = UIColor.fromColor(getForeground()).alpha(192);
      }

      SwingHelper.drawString(this, g, emptyFieldText, emptyFieldFont, emptyFieldColor);
    }
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

  protected Dimension getPreferredSizeEx() {
    Dimension d = super.getPreferredSize();

    if ((getColumns() == 0) &&!isPreferredSizeSet()) {
      int w = this.getColumnWidth() * 4;

      if (d.width < w) {
        d.width = w;
      }
    }

    return d;
  }
}
