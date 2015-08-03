/*
 * @(#)ListPainter.java   2010-06-18
 *
 * Copyright (c) 2007-2009 appNativa Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

/**
 * Copyright 2006 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.appnativa.rare.platform.swing.ui.text;


import java.awt.Color;
import java.awt.Component;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.StringTokenizer;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.text.AttributeSet;
import javax.swing.text.Element;
import javax.swing.text.StyleConstants;
import javax.swing.text.View;
import javax.swing.text.html.CSS;
import javax.swing.text.html.HTML;
import javax.swing.text.html.StyleSheet;

import com.appnativa.rare.ui.FontUtils;

public class ListPainter {

  /* list of roman numerals */
  static final char  romanChars[][] = {
    { 'i', 'v' }, { 'x', 'l' }, { 'c', 'd' }, { 'm', '?' },
  };
  Icon               img            = null;
  URL                imageurl;
  private int        bulletgap = 5;
  private StyleSheet ss        = null;
  private boolean    checkedForStart;
  private boolean    isLeftToRight;
  private Rectangle  paintRect;
  private int        start;
  private TYPE       type;

  public ListPainter(AttributeSet attr, StyleSheet ss) {
    this.ss = ss;

    /* Get the image to use as a list bullet */
    String imgstr = (String) attr.getAttribute(CSS.Attribute.LIST_STYLE_IMAGE);

    type = null;

    if ((imgstr != null) &&!imgstr.equals("none")) {
      String tmpstr = null;

      try {
        StringTokenizer st = new StringTokenizer(imgstr, "()");

        if (st.hasMoreTokens()) {
          tmpstr = st.nextToken();
        }

        if (st.hasMoreTokens()) {
          tmpstr = st.nextToken();
        }

        URL u = new URL(tmpstr);

        img = new ImageIcon(u);
      } catch(MalformedURLException e) {
        if ((tmpstr != null) && (ss != null) && (ss.getBase() != null)) {
          try {
            URL u = new URL(ss.getBase(), tmpstr);

            img = new ImageIcon(u);
          } catch(MalformedURLException murle) {
            img = null;
          }
        } else {
          img = null;
        }
      }
    }

    /* Get the type of bullet to use in the list */
    if (img == null) {
      type = TYPE.valueOfEx(attr.getAttribute(CSS.Attribute.LIST_STYLE_TYPE));
    }

    start     = 1;
    paintRect = new Rectangle();
  }

  public static enum TYPE {
    CIRCLE, SQUARE, DISC, DECIMAL, LOWER_ALPHA, UPPER_ALPHA, LOWER_ROMAN, UPPER_ROMAN;

    public static TYPE valueOfEx(Object o) {
      String name = (o == null)
                    ? null
                    : o.toString();

      if (name == null) {
        return DISC;
      }

      try {
        return valueOf(name.toUpperCase());
      } catch(Exception e) {
        return DISC;
      }
    }
  }

  /**
   * Paints the CSS list decoration according to the
   * attributes given.
   *
   * @param g the rendering surface.
   * @param x the x coordinate of the list item allocation
   * @param y the y coordinate of the list item allocation
   * @param w the width of the list item allocation
   * @param h the height of the list item allocation
   * @param v the allocated area to paint into.
   * @param item which list item is being painted.  This
   *  is a number greater than or equal to 0.
   */
  public void paint(Graphics g, float x, float y, float w, float h, View v, int item) {
    View   cv   = v.getView(item);
    Object name = cv.getElement().getAttributes().getAttribute(StyleConstants.NameAttribute);

    // Only draw something if the View is a list item. This won't
    // be the case for comments.
    if (!(name instanceof HTML.Tag) || (name != HTML.Tag.LI)) {
      return;
    }

    // deside on what side draw bullets, etc.
    isLeftToRight = cv.getContainer().getComponentOrientation().isLeftToRight();

    // How the list indicator is aligned is not specified, it is
    // left up to the UA. IE and NS differ on this behavior.
    // This is closer to NS where we align to the first line of text.
    // If the child is not text we draw the indicator at the
    // origin (0).
    float align = 0;

    if (cv.getViewCount() > 0) {
      View   pView = cv.getView(0);
      Object cName = pView.getElement().getAttributes().getAttribute(StyleConstants.NameAttribute);

      if (((cName == HTML.Tag.P) || (cName == HTML.Tag.IMPLIED)) && (pView.getViewCount() > 0)) {
        paintRect.setBounds((int) x, (int) y, (int) w, (int) h);

        Shape shape = cv.getChildAllocation(0, paintRect);

        if ((shape != null) && (shape = pView.getView(0).getChildAllocation(0, shape)) != null) {
          Rectangle rect = (shape instanceof Rectangle)
                           ? (Rectangle) shape
                           : shape.getBounds();

          align = pView.getView(0).getAlignment(View.Y_AXIS);
          y     = rect.y;
          h     = rect.height;
        }
      }
    }

    // set the color of a decoration
    if (ss != null) {
      g.setColor(ss.getForeground(cv.getAttributes()));
    } else {
      g.setColor(Color.black);
    }

    if (img != null) {
      drawIcon(g, (int) x, (int) y, (int) w, (int) h, align, v.getContainer());

      return;
    }

    TYPE childtype = getChildType(cv);
//    Font font      = ((StyledDocument) cv.getDocument()).getFont(cv.getAttributes());
//
//    if (font != null) {
//      g.setFont(font);
//    }

    if ((childtype == TYPE.SQUARE) || (childtype == TYPE.CIRCLE) || (childtype == TYPE.DISC)) {
      drawShape(g, childtype, (int) x, (int) y, (int) w, (int) h, align);
    } else if (childtype == TYPE.DECIMAL) {
      drawLetter(g, '1', (int) x, (int) y, (int) w, (int) h, align, getRenderIndex(v, item));
    } else if (childtype == TYPE.LOWER_ALPHA) {
      drawLetter(g, 'a', (int) x, (int) y, (int) w, (int) h, align, getRenderIndex(v, item));
    } else if (childtype == TYPE.UPPER_ALPHA) {
      drawLetter(g, 'A', (int) x, (int) y, (int) w, (int) h, align, getRenderIndex(v, item));
    } else if (childtype == TYPE.LOWER_ROMAN) {
      drawLetter(g, 'i', (int) x, (int) y, (int) w, (int) h, align, getRenderIndex(v, item));
    } else if (childtype == TYPE.UPPER_ROMAN) {
      drawLetter(g, 'I', (int) x, (int) y, (int) w, (int) h, align, getRenderIndex(v, item));
    }
  }

  /**
   * Draws the bullet icon specified by the list-style-image argument.
   *
   * @param g     the graphics context
   * @param ax    x coordinate to place the bullet
   * @param ay    y coordinate to place the bullet
   * @param aw    width of the container the bullet is placed in
   * @param ah    height of the container the bullet is placed in
   * @param align preferred alignment factor for the child view
   */
  public void drawIcon(Graphics g, int ax, int ay, int aw, int ah, float align, Component c) {

    // Align to bottom of icon.
    int gap = isLeftToRight
              ? -(img.getIconWidth() + bulletgap)
              : (aw + bulletgap);
    int x   = ax + gap;
    int y   = Math.max(ay, ay + (int) (align * ah) - img.getIconHeight());

    img.paintIcon(c, g, x, y);
  }

  /**
   * Draws the letter or number for an ordered list.
   *
   * @param g     the graphics context
   * @param letter type of ordered list to draw
   * @param ax    x coordinate to place the bullet
   * @param ay    y coordinate to place the bullet
   * @param aw    width of the container the bullet is placed in
   * @param ah    height of the container the bullet is placed in
   * @param index position of the list item in the list
   */
  public void drawLetter(Graphics g, char letter, int ax, int ay, int aw, int ah, float align, int index) {
    String str = formatItemNum(index, letter);

    str = isLeftToRight
          ? str + "."
          : "." + str;
    FontMetrics fm          = g.getFontMetrics();
    int         stringwidth = fm.stringWidth(str);
    int         gap         = isLeftToRight
                              ? -(stringwidth + bulletgap)
                              : (aw + bulletgap);
    int         x           = ax + gap;
    int         y           = Math.max(ay + fm.getAscent(), ay + (int) (ah * align));
    g.drawString(str, x, y);
  }

  /**
   * Draws the graphical bullet item specified by the type argument.
   *
   * @param g     the graphics context
   * @param type  type of bullet to draw (circle, square, disc)
   * @param ax    x coordinate to place the bullet
   * @param ay    y coordinate to place the bullet
   * @param aw    width of the container the bullet is placed in
   * @param ah    height of the container the bullet is placed in
   * @param align preferred alignment factor for the child view
   */
  public void drawShape(Graphics g, TYPE type, int ax, int ay, int aw, int ah, float align) {

    // Align to bottom of shape.
    float rs=g.getFont().getSize2D()/FontUtils.getDefaultFont().getSize2D();
    int size=(int)Math.ceil(6*rs);
    if(size<2) {
      size=2;
    }
    int gap = isLeftToRight
              ? -(bulletgap + size)
              : (aw + bulletgap);
    int x   = ax + gap;
    int y   = Math.max(ay, ay + (int) (align * ah) - size);
    if (type == TYPE.SQUARE) {
      g.fillRect(x, y, size, size);
    } else if (type == TYPE.CIRCLE) {
      g.drawOval(x, y, size, size);
    } else {
    	Graphics2D g2=(Graphics2D)g;
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      Object o=g2.getRenderingHint(RenderingHints.KEY_ANTIALIASING);
      g.fillOval(x, y, size, size);
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, o);
    }
  }

  /**
   * Converts the item number into an alphabetic character
   *
   * @param itemNum number to format
   */
  String formatAlphaNumerals(int itemNum) {
    String result = "";

    if (itemNum > 26) {
      result = formatAlphaNumerals(itemNum / 26) + formatAlphaNumerals(itemNum % 26);
    } else {

      // -1 because item is 1 based.
      result = String.valueOf((char) ('a' + itemNum - 1));
    }

    return result;
  }

  /**
   * Converts the item number into the ordered list number
   * (i.e.  1 2 3, i ii iii, a b c, etc.
   *
   * @param itemNum number to format
   * @param type    type of ordered list
   */
  String formatItemNum(int itemNum, char type) {
    boolean uppercase = false;
    String  formattedNum;

    switch(type) {
      case '1' :
      default :
        formattedNum = String.valueOf(itemNum);

        break;

      case 'A' :
        uppercase = true;

				//$FALL-THROUGH$
			case 'a' :
        formattedNum = formatAlphaNumerals(itemNum);

        break;

      case 'I' :
        uppercase = true;

				//$FALL-THROUGH$
			case 'i' :
        formattedNum = formatRomanNumerals(itemNum);
    }

    if (uppercase) {
      formattedNum = formattedNum.toUpperCase();
    }

    return formattedNum;
  }

  /**
   * Converts the item number into a roman numeral
   *
   * @param level position
   * @param num   digit to format
   */
  String formatRomanDigit(int level, int digit) {
    String result = "";

    if (digit == 9) {
      result = result + romanChars[level][0];
      result = result + romanChars[level + 1][0];

      return result;
    } else if (digit == 4) {
      result = result + romanChars[level][0];
      result = result + romanChars[level][1];

      return result;
    } else if (digit >= 5) {
      result = result + romanChars[level][1];
      digit  -= 5;
    }

    for (int i = 0; i < digit; i++) {
      result = result + romanChars[level][0];
    }

    return result;
  }

  /**
   * Converts the item number into a roman numeral
   *
   * @param num  number to format
   */
  String formatRomanNumerals(int num) {
    return formatRomanNumerals(0, num);
  }

  /**
   * Converts the item number into a roman numeral
   *
   * @param num  number to format
   */
  String formatRomanNumerals(int level, int num) {
    if (num < 10) {
      return formatRomanDigit(level, num);
    } else {
      return formatRomanNumerals(level + 1, num / 10) + formatRomanDigit(level, num % 10);
    }
  }

  static boolean matchNameAttribute(AttributeSet attr, HTML.Tag tag) {
    Object o = attr.getAttribute(StyleConstants.NameAttribute);

    if (o instanceof HTML.Tag) {
      HTML.Tag name = (HTML.Tag) o;

      if (name == tag) {
        return true;
      }
    }

    return false;
  }

  /**
   * Returns a string that represents the value
   * of the HTML.Attribute.TYPE attribute.
   * If this attributes is not defined, then
   * then the type defaults to "disc" unless
   * the tag is on Ordered list.  In the case
   * of the latter, the default type is "decimal".
   */
  private TYPE getChildType(View childView) {
    Object childtype = childView.getAttributes().getAttribute(CSS.Attribute.LIST_STYLE_TYPE);

    if (childtype == null) {
      if (type == null) {

        // Parent view.
        View         v   = childView.getParent();

        if (matchNameAttribute(v.getElement().getAttributes(), HTML.Tag.OL)) {
          return TYPE.DECIMAL;
        } else {
          return TYPE.DISC;
        }
      } else {
        return type;
      }
    }

    return TYPE.valueOfEx(childtype);
  }

  /**
   * Returns an integer that should be used to render the child at
   * <code>childIndex</code> with. The retValue will usually be
   * <code>childIndex</code> + 1, unless <code>parentView</code>
   * has some Views that do not represent LI's, or one of the views
   * has a HTML.Attribute.START specified.
   */
  private int getRenderIndex(View parentView, int childIndex) {
    if (!checkedForStart) {
      getStart(parentView);
    }

    int retIndex = childIndex;

    for (int counter = childIndex; counter >= 0; counter--) {
      AttributeSet as = parentView.getElement().getElement(counter).getAttributes();

      if (as.getAttribute(StyleConstants.NameAttribute) != HTML.Tag.LI) {
        retIndex--;
      } else if (as.isDefined(HTML.Attribute.VALUE)) {
        Object value = as.getAttribute(HTML.Attribute.VALUE);

        if ((value != null) && (value instanceof String)) {
          try {
            int iValue = Integer.parseInt((String) value);

            return retIndex - counter + iValue;
          } catch(NumberFormatException nfe) {}
        }
      }
    }

    return retIndex + start;
  }

  /**
   * Obtains the starting index from <code>parent</code>.
   */
  private void getStart(View parent) {
    checkedForStart = true;

    Element element = parent.getElement();

    if (element != null) {
      AttributeSet attr = element.getAttributes();
      Object       startValue;

      if ((attr != null) && attr.isDefined(HTML.Attribute.START)
          && (startValue = attr.getAttribute(HTML.Attribute.START)) != null && (startValue instanceof String)) {
        try {
          start = Integer.parseInt((String) startValue);
        } catch(NumberFormatException nfe) {}
      }
    }
  }
}
