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

import com.appnativa.rare.iConstants;
import com.appnativa.rare.platform.swing.ui.view.TextAreaView;
import com.appnativa.rare.ui.ColorUtils;
import com.appnativa.rare.ui.Column;
import com.appnativa.rare.ui.Component;
import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.ui.RenderableDataItem.HorizontalAlign;
import com.appnativa.rare.ui.RenderableDataItem.IconPosition;
import com.appnativa.rare.ui.RenderableDataItem.Orientation;
import com.appnativa.rare.ui.RenderableDataItem.VerticalAlign;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.UIFont;
import com.appnativa.rare.ui.UIInsets;
import com.appnativa.rare.ui.iPlatformBorder;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.iPlatformIcon;
import com.appnativa.rare.ui.iPlatformRenderingComponent;
import com.appnativa.rare.ui.painter.iComponentPainter;
import com.appnativa.rare.ui.painter.iPlatformComponentPainter;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Insets;
import java.awt.Rectangle;

import java.util.Map;

import javax.swing.Icon;
import javax.swing.border.Border;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultCaret;
import javax.swing.text.Document;
import javax.swing.text.Segment;

/**
 *
 *
 * @version    0.3, 2007-05-14
 * @author     Don DeCoteau
 */
public class UITextAreaRenderer extends TextAreaView implements iPlatformRenderingComponent {
  private static ThreadLocal<Segment> perThreadSegment = new ThreadLocal<Segment>() {
    @Override
    protected synchronized Segment initialValue() {
      return new Segment();
    }
  };
  protected Component component;

  /**  */
  protected iComponentPainter componentPainter;
  private Insets              insets;
  private boolean             linefeedWrap;

  public UITextAreaRenderer() {
    super();
    component = new Component(this);
    adjustUI();
  }

  @Override
  public void clearRenderer() {
    setComponentPainter(null);
    setBackground(null);
    setBorder(null);
    setIcon(null);
    setText("");
  }

  @Override
  public void dispose() {
    if (component != null) {
      component.dispose();
    }
  }

  public Dimension getAutoPreferredSize() {
    Dimension d = null;

    do {
      if (linefeedWrap) {
        Font f = getFont();

        if (f == null) {
          break;
        }

        Segment  seg = perThreadSegment.get();
        Document doc = getDocument();
        int      len = doc.getLength();

        if (len == 0) {
          return new Dimension(16, 16);
        }

        try {
          doc.getText(0, len, seg);
        } catch(BadLocationException ex) {
          break;    // should never happen
        }

        insets = getInsets(insets);

        FontMetrics fm  = this.getFontMetrics(f);
        int         w   = 0;
        int         n   = 0;
        char[]      a   = seg.array;
        int         off = seg.offset;
        char        c;
        int         lines = 1;

        for (int i = 0; i < len; i++) {
          c = a[i + off];

          if ((c == '\r') || (c == '\n')) {
            if (n > w) {
              w = n;
            }

            if (n != 0) {
              lines++;
            }

            n = 0;

            continue;
          }

          n += fm.charWidth(c);
        }

        if (n > w) {
          w = n;
        }

        w += insets.left + insets.right;
        n = insets.top + insets.bottom + ((fm.getHeight() + fm.getDescent()) * lines);
        d = new Dimension(w, n);
      }
    } while(false);

    if (d == null) {
      super.getPreferredSize();
    }

    Number i = (Number) getClientProperty(iConstants.RARE_HEIGHT_MIN_VALUE);

    if ((i != null) && (i.intValue() > d.height)) {
      d.height = i.intValue();
    }

    return d;
  }

  @Override
  public iPlatformComponent getComponent() {
    return getComponentEx();
  }

  @Override
  public iPlatformComponent getComponent(CharSequence value, RenderableDataItem item) {
    setText((value == null)
            ? ""
            : value.toString());

    return getComponentEx();
  }

  @Override
  public iPlatformComponent getComponent(iPlatformComponent list, Object value, RenderableDataItem item, int row,
          boolean isSelected, boolean hasFocus, Column col, RenderableDataItem rowItem, boolean handleAll) {
    if (handleAll) {
      setBorder(item.getBorder());

      UIFont f = item.getFont();

      if (f == null) {
        f = list.getFont();
      }

      setFont(f);

      UIColor fg = item.getForeground();

      if (fg == null) {
        fg = list.getForeground();
      }

      setForeground(fg);
    }

    setText((value == null)
            ? ""
            : value.toString());

    return getComponentEx();
  }

  public iPlatformComponent getComponentEx() {
    if (component == null) {
      component = new Component(this);
    }

    return component;
  }

  @Override
  public Dimension getPreferredSize() {
    Number i = (Number) getClientProperty(iConstants.RARE_WIDTH_FIXED_VALUE);

    if ((i != null) && (i.intValue() > 0)) {
      setSize(i.intValue(), Short.MAX_VALUE);
    }

    return super.getPreferredSize();
  }

  @Override
  public void getPreferredSize(UIDimension size, int maxWidth) {
    maxWidth += columnWidth;    //will only be set <=0

    if (maxWidth < 0) {
      maxWidth = 0;
    }

    super.getPreferredSize(size, maxWidth);

    Number i = (Number) getClientProperty(iConstants.RARE_HEIGHT_MIN_VALUE);

    if ((i != null) && (i.intValue() > size.height)) {
      size.height = i.intValue();
    }
  }

  @Override
  public final void invalidate() {}

  public boolean isLinefeedWrap() {
    return linefeedWrap;
  }

  @Override
  public iPlatformRenderingComponent newCopy() {
    return setupNewCopy(new UITextAreaRenderer());
  }

  @Override
  public void prepareForReuse(int row, int column) {
    setComponentPainter(null);
    component.getView().setBackground(null);
  }

  @Override
  public final void repaint() {}

  @Override
  public final void repaint(long tm, int x, int y, int width, int height) {}

  @Override
  public final void repaint(Rectangle r) {}

  @Override
  public final void revalidate() {}

  @Override
  public void setAlignment(HorizontalAlign ha, VerticalAlign va) {}

  @Override
  public void setBackground(UIColor bg) {
    setBackground((Color) bg);
  }

  @Override
  public void setBorder(iPlatformBorder b) {
    setBorder((Border) b);
  }

  @Override
  public void setColumnWidth(int columnWidth) {
    this.columnWidth = columnWidth;

    if (columnWidth > 0) {
      setColumns(columnWidth);
    } else {
      this.columnWidth = columnWidth;
    }
  }

  @Override
  public void setComponentPainter(iPlatformComponentPainter cp) {
    component.setComponentPainterEx(cp);
  }

  public void setDisabledIcon(Icon icon) {}

  @Override
  public void setFont(UIFont font) {
    setFont((Font) font);
  }

  @Override
  public void setForeground(UIColor fg) {
    setForeground((Color) fg);
  }

  public void setHorizontalAlignment(int alignment) {}

  public void setHorizontalTextPosition(int position) {}

  public void setIcon(Icon icon) {}

  @Override
  public void setIcon(iPlatformIcon icon) {}

  @Override
  public void setIconPosition(IconPosition position) {}

  /**
   * Sets linefeed wrapping
   *
   * @param linefeedWrap true to wrap on line feeds false otherwise
   */
  public void setLinefeedWrap(boolean linefeedWrap) {
    this.linefeedWrap = linefeedWrap;
  }

  @Override
  public void setMargin(UIInsets insets) {}

  @Override
  public void setOptions(Map<String, Object> options) {

    /**
     * Sets the prototype character
     * @param prototypeChar the prototype character
     */
  }

  @Override
  public void setOrientation(Orientation o) {}

  @Override
  public void setScaleIcon(boolean scale, float scaleFactor) {}

  public void setVerticalAlignment(int alignment) {}

  public void setVerticalTextPosition(int position) {}

  @Override
  public void setWordWrap(boolean wrap) {}

  @Override
  public void updateUI() {
    super.updateUI();
    adjustUI();
  }

  protected void adjustUI() {
    setLineWrap(true);
    setWrapStyleWord(true);
    setEditable(false);
    setRequestFocusEnabled(false);
    setFocusable(false);
    setOpaque(false);
    setCaret(new DefaultCaret() {
      @Override
      protected void adjustVisibility(Rectangle nloc) {}
    });
    setForeground(ColorUtils.getForeground());
  }

  protected iPlatformRenderingComponent setupNewCopy(UITextAreaRenderer r) {
    r.linefeedWrap = linefeedWrap;
    r.columnWidth  = columnWidth;

    if (columnWidth > 0) {
      r.setColumns(columnWidth);
    }

    return Renderers.setupNewCopy(this, r);
  }
}
