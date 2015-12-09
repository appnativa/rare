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

import com.appnativa.rare.iConstants;
import com.appnativa.rare.platform.EventHelper;
import com.appnativa.rare.platform.PlatformHelper;
import com.appnativa.rare.platform.swing.plaf.LabelExUI;
import com.appnativa.rare.platform.swing.ui.util.SwingGraphics;
import com.appnativa.rare.platform.swing.ui.util.SwingHelper;
import com.appnativa.rare.ui.ColorUtils;
import com.appnativa.rare.ui.FontUtils;
import com.appnativa.rare.ui.RenderableDataItem.Orientation;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.iPlatformBorder;
import com.appnativa.rare.ui.iPlatformIcon;
import com.appnativa.rare.ui.listener.iHyperlinkListener;
import com.appnativa.rare.ui.painter.iComponentPainter;
import com.appnativa.rare.ui.painter.iPainter;
import com.appnativa.rare.ui.painter.iPainterSupport;
import com.appnativa.rare.ui.painter.iPlatformComponentPainter;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JToolTip;
import javax.swing.border.Border;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Position;
import javax.swing.text.StyledDocument;
import javax.swing.text.View;
import javax.swing.text.html.HTML;

/**
 *
 * Used code from http://forums.sun.com/thread.jspa?threadID=574895&messageID=2866170 for hyper link listener
 * @version    0.3, 2007-05-14
 * @author     Don DeCoteau
 */
public class LabelView extends JLabel implements iPainterSupport, MouseListener, MouseMotionListener, iView {
  iHyperlinkListener listener;
  Point              mousePressedPoint;
  long               mousePressedTime;
  int                rgba;
  AffineTransform    transform;

  /**  */
  protected iPlatformComponentPainter componentPainter;
  protected SwingGraphics             graphics;
  private Orientation                 _orientation = Orientation.HORIZONTAL;
  private boolean                     iconOnly     = true;
  private boolean                     blockRevalidateAndRepaint;
  private boolean                     iconRightJustified;
  private String                      lastHref;
  private boolean                     shapedBorder;
  private int                         verticalAlignment;
  private boolean                     wordWrap;

  public LabelView() {
    super();
    this.setFocusable(false);
    setFont(FontUtils.getDefaultFont());
    setForeground(ColorUtils.getForeground());
  }

  public LabelView(Icon icon) {
    this();
    setIcon(icon);
    setFont(FontUtils.getDefaultFont());
    setForeground(ColorUtils.getForeground());
  }

  public LabelView(String text) {
    this();
    setFont(FontUtils.getDefaultFont());
    setForeground(ColorUtils.getForeground());
    setText(text);
  }

  public void calculateRectangles(Rectangle textRect, Rectangle iconRect) {
    getTextRectangle(true);

    if (textRect != null) {
      textRect.setBounds(_textR);
    }

    if (iconRect != null) {
      iconRect.setBounds(_iconR);
    }
  }

  @Override
  public JToolTip createToolTip() {
    return JToolTipEx.createNewToolTip(this);
  }

  @Override
  public void mouseClicked(MouseEvent e) {}

  @Override
  public void mouseDragged(MouseEvent e) {
    mouseMoved(e);
  }

  @Override
  public void mouseEntered(MouseEvent e) {}

  @Override
  public void mouseExited(MouseEvent e) {
    if (!isEnabled()) {
      return;
    }

    mousePressedPoint = null;

    if (lastHref != null) {
      listener.linkExited(this, null, lastHref, EventHelper.createMouseEvent(this, e));
    }

    lastHref = null;
  }

  @Override
  public void mouseMoved(MouseEvent e) {
    if (!isEnabled()) {
      return;
    }

    if (listener != null) {
      String href = getHrefAtPoint(e.getPoint());

      if ((lastHref != null) &&!lastHref.equals(href)) {
        listener.linkExited(this, null, lastHref, EventHelper.createMouseEvent(this, e));
      }

      if ((href != null) &&!href.equals(lastHref)) {
        listener.linkEntered(this, null, href, EventHelper.createMouseEvent(this, e));
      }

      lastHref = href;

      Cursor hand = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);

      if (href == null) {
        if (getCursor() == hand) {
          setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }
      } else {
        if (getCursor() != hand) {
          setCursor(hand);
        }
      }
    }
  }

  @Override
  public void mousePressed(MouseEvent e) {
    if (!isEnabled()) {
      return;
    }

    mousePressedPoint = e.getPoint();
    mousePressedTime  = e.getWhen();
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    if (!isEnabled()) {
      return;
    }

    final Point mp = mousePressedPoint;

    mousePressedPoint = null;

    if ((listener != null) && PlatformHelper.isMouseClick(mp, mousePressedTime, e)) {
      String href = getHrefAtPoint(e.getPoint());

      if (href != null) {
        URL u = null;

        try {
          u = new URL(href);
        } catch(MalformedURLException ex) {}

        listener.linkClicked(this, u, href, EventHelper.createMouseEvent(this, e));
      }
    }
  }

  @Override
  public void paint(Graphics g) {
    Graphics2D      g2 = (Graphics2D) g;
    AffineTransform tx = null;

    switch(_orientation) {
      case VERTICAL_DOWN :
        tx = g2.getTransform();
        g2.rotate(Math.PI / 2);
        g2.translate(0, -getWidth());

        break;

      case VERTICAL_UP :
        tx = g2.getTransform();
        g2.rotate(-Math.PI / 2);
        g2.translate(-getHeight(), 0);

        break;

      default :
        break;
    }

    if (transform != null) {
      if (tx == null) {
        tx = g2.getTransform();
      }

      g2.transform(transform);
    }

    graphics                  = SwingGraphics.fromGraphics(g, this, graphics);
    blockRevalidateAndRepaint = true;
    super.paint(g);
    blockRevalidateAndRepaint = false;
    graphics.clear();

    if (tx != null) {
      g2.setTransform(tx);
    }
  }

  @Override
  public void repaint() {
    if (!blockRevalidateAndRepaint) {
      super.repaint();
    }
  }

  @Override
  public void revalidate() {
    if (!blockRevalidateAndRepaint) {
      super.revalidate();
    }
  }

  @Override
  public void updateUI() {
    if (!(ui instanceof LabelExUI)) {
      setUI(LabelExUI.getInstance());
    }
  }

  /**
   * @param blockRevalidateAndRepaint the blockRevalidateAndRepaint to set
   */
  public void setBlockRevalidateAndRepaint(boolean blockRevalidateAndRepaint) {
    this.blockRevalidateAndRepaint = blockRevalidateAndRepaint;
  }

  @Override
  public void setBorder(Border border) {
    if (border instanceof iPlatformBorder) {
      iPlatformBorder sb = (iPlatformBorder) border;

      shapedBorder = !sb.isRectangular();
    }

    super.setBorder(border);
  }

  @Override
  public void setComponentOrientation(ComponentOrientation o) {
    if (o != getComponentOrientation()) {
      super.setComponentOrientation(o);
    }
  }

  @Override
  public void setComponentPainter(iPlatformComponentPainter cp) {
    componentPainter = cp;
  }

  public void setHyperlinkeListener(iHyperlinkListener listener) {
    this.listener = listener;
    this.removeMouseListener(this);
    this.removeMouseMotionListener(this);

    if (listener != null) {
      this.addMouseListener(this);
      this.addMouseMotionListener(this);
    }
  }

  /**
   * Sets the label as being right-justified
   *
   * @param rj true for right-justified; false otherwise
   */
  public void setIconRightJustified(boolean rj) {
    iconRightJustified = rj;
    repaint();
  }

  public void setOrientation(Orientation orientation) {
    Orientation old = _orientation;

    if (old != orientation) {
      _orientation = orientation;
      firePropertyChange("ORIENTATION", old, orientation);
    }
  }

  @Override
  public void setText(String text) {
    if (text == null) {
      text = "";
    }

    iconOnly = text.length() == 0;
    super.setText(text);
  }

  @Override
  public void setTransformEx(AffineTransform tx) {
    transform = tx;
  }

  @Override
  public void setVerticalAlignment(int alignment) {
    verticalAlignment = alignment;

    if (!wordWrap) {
      super.setVerticalAlignment(alignment);
    }
  }

  public void setWordWrap(boolean wordWrap) {
    this.wordWrap = wordWrap;

    if (ui instanceof LabelExUI) {
      LabelExUI u = (LabelExUI) ui;

      if (u == LabelExUI.getInstance()) {
        if (wordWrap) {
          u = new LabelExUI();
          u.setWordWrap(true);
          setUI(u);
        }
      } else {
        u.setWordWrap(wordWrap);
      }
    } else if (wordWrap) {
      LabelExUI u = new LabelExUI();

      u.setWordWrap(true);
      setUI(u);
    }
  }

  /**
   * Determine the bounding box of the character at the given
   * index into the string.  The bounds are returned in local
   * coordinates.  If the index is invalid an empty rectangle is
   * returned.
   *
   * @param p0 the index into the string
   * @return the screen coordinates of the character's the bounding box,
   * if index is invalid returns an empty rectangle.
   */
  public Rectangle getCharacterBounds(int p0) {
    View view = (View) getClientProperty(javax.swing.plaf.basic.BasicHTML.propertyKey);

    if (view != null) {
      Rectangle r = getTextRectangle(false);

      if (r == null) {
        return null;
      }

      Rectangle2D.Float shape = new Rectangle2D.Float(r.x, r.y, r.width, r.height);

      try {
        Shape charShape = view.modelToView(p0, shape, Position.Bias.Forward);

        return charShape.getBounds();
      } catch(BadLocationException e) {
        return null;
      }
    } else {
      return null;
    }
  }

  public Rectangle getCharacterBounds(Point p) {
    int       i    = -1;
    View      view = (View) getClientProperty(javax.swing.plaf.basic.BasicHTML.propertyKey);
    Rectangle r    = null;

    if (view != null) {
      r = getTextRectangle(false);

      if (r != null) {
        Rectangle2D.Float shape  = new Rectangle2D.Float(r.x, r.y, r.width, r.height);
        Position.Bias     bias[] = new Position.Bias[1];

        i = view.viewToModel(p.x, p.y, shape, bias);

        try {
          Shape charShape = view.modelToView(i, shape, Position.Bias.Forward);

          return charShape.getBounds();
        } catch(BadLocationException e) {
          return null;
        }
      }
    }

    return null;
  }

  public Rectangle[] getCharacterBounds(int p0, int p1, boolean getRectangle) {
    View view = (View) getClientProperty(javax.swing.plaf.basic.BasicHTML.propertyKey);

    if (view != null) {
      Rectangle r = getTextRectangle(false);

      if (r == null) {
        return null;
      }

      Rectangle2D.Float shape = new Rectangle2D.Float(r.x, r.y, r.width, r.height);

      try {
        Rectangle[] rects     = new Rectangle[getRectangle
                ? 3
                : 2];
        Shape       charShape = view.modelToView(p0, shape, Position.Bias.Forward);

        rects[0]  = charShape.getBounds();
        charShape = view.modelToView(p1, shape, Position.Bias.Forward);
        rects[1]  = charShape.getBounds();

        if (getRectangle) {
          rects[2] = r.getBounds();
        }

        return rects;
      } catch(BadLocationException e) {
        return null;
      }
    } else {
      return null;
    }
  }

  @Override
  public iPlatformComponentPainter getComponentPainter() {
    return componentPainter;
  }

  /**
   * Gets the document associated with a label
   * @return the document associated with a label (null for non HTML labels)
   */
  public Document getDocument() {
    View view = (View) getClientProperty(javax.swing.plaf.basic.BasicHTML.propertyKey);

    return (view == null)
           ? null
           : view.getDocument();
  }

  @Override
  public Color getForeground() {
    Color c=super.getForeground();
    if(c instanceof UIColor && !isEnabled()) {
      c=((UIColor)c).getDisabledColor();
    }
    return c;
  }

  @Override
  public Icon getDisabledIcon() {
    Icon icon = super.getDisabledIcon();

    if (icon == null) {
      Icon ic = getIcon();

      if (ic instanceof iPlatformIcon) {
        icon = ((iPlatformIcon) ic).getDisabledVersion();
      }
    }

    return icon;
  }

  public String getHrefAtPoint(Point p) {
    final int stringIndexAtPoint = getIndexAtPoint(p, false);

    if (stringIndexAtPoint < 1) {
      return null;
    }

    if (stringIndexAtPoint == 1) {
      if (!_textR.contains(p)) {
        return null;
      }
    }

    final AttributeSet attr = (AttributeSet) ((AccessibleJLabel) getAccessibleContext()).getCharacterAttribute(
                                  stringIndexAtPoint).getAttribute(HTML.Tag.A);

    return (attr == null)
           ? null
           : (String) attr.getAttribute(HTML.Attribute.HREF);
  }

  public Rectangle getIconRectangle() {
    getTextRectangle(false);

    return _iconR;
  }

  public int getIndexAtPoint(Point p) {
    return getIndexAtPoint(p, false);
  }

  public int getIndexAtPoint(Point p, boolean updateView) {
    View view = (View) getClientProperty(javax.swing.plaf.basic.BasicHTML.propertyKey);

    if (view != null) {
      Rectangle r = getTextRectangle(updateView);

      if (r == null) {
        return -1;
      }

      Rectangle2D.Float shape  = new Rectangle2D.Float(r.x, r.y, r.width, r.height);
      Position.Bias     bias[] = new Position.Bias[1];
      int               i      = view.viewToModel(p.x, p.y, shape, bias);

      return i;
    } else {
      return -1;
    }
  }

  public Orientation getOrientation() {
    return _orientation;
  }

  /**
   * Gets the plain text for the specified range
   *
   * @return the text for the specified range
   */
  public String getPlainText() {
    return getPlainText(0, -1);
  }

  /**
   * Gets the plain text for the specified range
   *
   * @param beginIndex the starting index
   * @param endIndex the ending index
   * @return the text for the specified range
   */
  public String getPlainText(int beginIndex, int endIndex) {
    View view = (View) getClientProperty(javax.swing.plaf.basic.BasicHTML.propertyKey);

    if (view != null) {
      Document d = view.getDocument();

      if (d instanceof StyledDocument) {
        StyledDocument doc = (StyledDocument) d;

        if (endIndex == -1) {
          endIndex = doc.getLength();
        }

        try {
          return doc.getText(beginIndex, endIndex - beginIndex);
        } catch(BadLocationException ex) {}
      }
    }

    String s = getText();

    return (endIndex == -1)
           ? s.substring(beginIndex)
           : s.substring(beginIndex, endIndex);
  }

  public int getPlainTextLength() {
    View view = (View) getClientProperty(javax.swing.plaf.basic.BasicHTML.propertyKey);

    if (view != null) {
      return view.getDocument().getLength();
    }

    String s = getText();

    return (s == null)
           ? 0
           : s.length();
  }

  public int getPreferredHeight(float width) {
    View view = (View) getClientProperty(javax.swing.plaf.basic.BasicHTML.propertyKey);

    if (view != null) {
      width -= 16;
    }

    SwingHelper.calculateLabelRects(this, _viewR, _iconR, _textR, _insets, (int) width, Short.MAX_VALUE);

    int    y1 = Math.min(_iconR.y, _textR.y);
    int    y2 = Math.max(_iconR.y + _iconR.height, _textR.y + _textR.height);
    Insets in = _insets;

    return (y2 - y1) + (in.top + in.bottom);
  }

  @Override
  public void getMinimumSize(UIDimension size, int maxWidth) {
    Dimension d = super.getMinimumSize();

    size.width  = d.width;
    size.height = d.height;
  }

  public Rectangle getTextRectangle() {
    return getTextRectangle(false);
  }

  public Shape getTextShape(Point p0, Point p1) {
    int       i0, i1;
    View      view = (View) getClientProperty(javax.swing.plaf.basic.BasicHTML.propertyKey);
    Rectangle r    = null;

    if (view != null) {
      r = getTextRectangle(false);

      if (r != null) {
        Rectangle2D.Float shape  = new Rectangle2D.Float(r.x, r.y, r.width, r.height);
        Position.Bias     bias[] = new Position.Bias[1];

        i0 = view.viewToModel(p0.x, p0.y, shape, bias);
        i1 = view.viewToModel(p0.x, p0.y, shape, bias);

        try {
          Shape charShape = view.modelToView(i0, Position.Bias.Forward, i1, Position.Bias.Forward, shape);

          return charShape.getBounds();
        } catch(BadLocationException e) {
          return null;
        }
      }
    }

    return null;
  }

  @Override
  public AffineTransform getTransformEx() {
    return transform;
  }

  @Override
  public int getVerticalAlignment() {
    return verticalAlignment;
  }

  public boolean hasValue() {
    return (getText() != null) && (getText().length() > 0);
  }

  /**
   * @return the blockRevalidateAndRepaint
   */
  public boolean isBlockRevalidateAndRepaint() {
    return blockRevalidateAndRepaint;
  }

  public boolean isHTMLText() {
    return getClientProperty(javax.swing.plaf.basic.BasicHTML.propertyKey) != null;
  }

  /**
   * Returns whether the icon is right justified
   * @return true if it is; false otherwise
   */
  public boolean isIconRightJustified() {
    return iconRightJustified;
  }

  @Override
  public boolean isOpaque() {
    iComponentPainter cp = getComponentPainter();

    if ((cp != null) && cp.isBackgroundPaintEnabled()) {
      return false;
    }

    return !shapedBorder && super.isOpaque();
  }

  public boolean isPointOverText(Point p, boolean checkIcon) {
    int i = getIndexAtPoint(p, false);

    if (checkIcon && _iconR.contains(p)) {
      return true;
    }

    if (i < 1) {
      return false;
    }

    if ((i == 1) &&!_textR.contains(p)) {
      return false;
    }

    View view = (View) getClientProperty(javax.swing.plaf.basic.BasicHTML.propertyKey);

    if ((view != null) && (i == view.getDocument().getLength())) {
      Rectangle2D.Float shape = new Rectangle2D.Float(_textR.x, _textR.y, _textR.width, _textR.height);

      try {
        Shape charShape = view.modelToView(i, shape, Position.Bias.Forward);

        return charShape.contains(p);
      } catch(BadLocationException e) {}
    }

    return true;
  }

  public boolean isVertical() {
    return _orientation != Orientation.HORIZONTAL;
  }

  public boolean isWordWrap() {
    return wordWrap;
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
    graphics = SwingGraphics.fromGraphics(g, this, graphics);

    iPlatformComponentPainter cp = getComponentPainter();

    if (cp != null) {
      float height = getHeight();
      float width  = getWidth();

      cp.paint(graphics, 0, 0, width, height, iPainter.HORIZONTAL, false);
    }

    if (iconOnly) {
      Icon icon = isEnabled()
                  ? getIcon()
                  : getDisabledIcon();

      if (icon == null) {
        super.paintComponent(g);

        return;
      }

      SwingHelper.calculateLabelRects(this, _viewR, _iconR, _textR, _insets, -1, -1);

      int x = _viewR.x;
      int y = _iconR.y;

      if (getHorizontalAlignment() == CENTER) {
        x = (getWidth() - icon.getIconWidth()) / 2;

        if (x < _insets.left) {
          x = _insets.left;
        }
      }

      if (isOpaque()) {
        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());
      }

      icon.paintIcon(this, g, x, y);

      return;
    }

    super.paintComponent(g);
  }

  protected Rectangle getTextRectangle(boolean updateView) {
    View view = (View) getClientProperty(javax.swing.plaf.basic.BasicHTML.propertyKey);

    SwingHelper.calculateLabelRects(this, _viewR, _iconR, _textR, _insets, -1, -1);

    if ((view != null) && updateView) {
      view.setSize(_textR.width, 0);
      _textR.height = (int) Math.max(_textR.height, view.getPreferredSpan(View.Y_AXIS));
    }

    return _textR;
  }

  private static Rectangle   _iconR  = new Rectangle();
  private static Insets      _insets = new Insets(0, 0, 0, 0);
  private static Rectangle   _textR  = new Rectangle();
  private static Rectangle   _viewR  = new Rectangle();
  private static UIDimension size    = new UIDimension();

  @Override
  public Dimension getPreferredSize() {
    if (size == null) {
      size = new UIDimension();
    }

    Number num      = (Number) getClientProperty(iConstants.RARE_WIDTH_FIXED_VALUE);
    int    maxWidth = 0;

    if ((num != null) && (num.intValue() > 0)) {
      maxWidth = num.intValue();
    }

    getPreferredSize(size, maxWidth);

    return new Dimension(size.intWidth(), size.intHeight());
  }

  @Override
  public void getPreferredSize(UIDimension size, int maxWidth) {
    blockRevalidateAndRepaint = true;

    if ((maxWidth > 0) && isWordWrap()) {
      View view = (View) getClientProperty(javax.swing.plaf.basic.BasicHTML.propertyKey);

      if ((view != null) && (maxWidth > 32)) {
        maxWidth -= 16;
      }

      SwingHelper.calculateLabelRects(this, _viewR, _iconR, _textR, _insets, maxWidth, Short.MAX_VALUE);

      int    y1 = Math.min(_iconR.y, _textR.y);
      int    y2 = Math.max(_iconR.y + _iconR.height, _textR.y + _textR.height);
      int    x1 = Math.min(_iconR.x, _textR.x);
      int    x2 = Math.max(_iconR.x + _iconR.width, _textR.x + _textR.width);
      Insets in = _insets;

      size.height = (y2 - y1) + (in.top + in.bottom);
      size.width  = (x2 - x1) + (in.left + in.right);

      if (view != null) {
        size.width += 2;
      }
    } else {
      Dimension d = super.getPreferredSize();

      size.width  = d.width;
      size.height = d.height;
    }

    if (isFontSet() && getFont().isItalic()) {
      if (getClientProperty(javax.swing.plaf.basic.BasicHTML.propertyKey) == null) {
        size.width += 4;
      }
    }

    if (getOrientation() != Orientation.HORIZONTAL) {
      Insets in = this.getInsets();

      if (in != null) {
        size.width  -= (in.left + in.right);
        size.height -= (in.top + in.bottom);
        size.setSize(size.height, size.width);
        size.width  += (in.left + in.right);
        size.height += (in.top + in.bottom);
      }
    }

    blockRevalidateAndRepaint = false;
  }
}
