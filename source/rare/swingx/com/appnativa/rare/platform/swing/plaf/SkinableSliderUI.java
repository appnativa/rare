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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicSliderUI;

import com.appnativa.rare.ui.painter.iPainter;
import com.appnativa.rare.ui.painter.iPlatformPainter;

/**
 * A skinable slider UI
 *
 * @author Don DeCoteau
 */
public class SkinableSliderUI extends BasicSliderUI {
  protected boolean        minor;
  protected boolean        paintInnerTicks;
  private TickStyle        tickStyle = TickStyle.LINE;
  private PaintArea        paintArea;
  private Icon             thumbIcon;
  private int              thumbOffset;
  private Color            tickColor;
  private int              tickOffset;
  private iPlatformPainter trackPainter;
  private int trackPainterWidth;

  public static enum PaintArea { VALUE, REMAINING, ALL }

  public static enum TickStyle { LINE, FILL }

  public SkinableSliderUI() {
    super(null);
  }

  public SkinableSliderUI(JSlider b) {
    super(b);
  }

  public static ComponentUI createUI(JComponent b) {
    return new SkinableSliderUI((JSlider) b);
  }

  @Override
  public void installUI(JComponent c) {
    super.installUI(c);
  }

  @Override
  public void paintFocus(Graphics g) {
    if (slider.getClientProperty("Rare.focusPainted") != Boolean.FALSE) {
      super.paintFocus(g);
    }
  }

  @Override
  public void paintThumb(Graphics g) {
    if (thumbIcon == null) {
      super.paintThumb(g);

      return;
    }

    thumbIcon.paintIcon(slider, g, thumbRect.x, thumbRect.y);
  }

  @Override
  public void paintTicks(Graphics g) {
    if (tickStyle == TickStyle.LINE) {
      super.paintTicks(g);

      return;
    }

    Color c = g.getColor();

    if (tickColor != null) {
      g.setColor(tickColor);
    }

    if (slider.getOrientation() == JSlider.HORIZONTAL) {
      int x = trackRect.x + 1;
      int y = tickRect.y + tickOffset;
      int w = trackRect.width - 2;
      int h = tickRect.height - (tickOffset * 2);

      switch(paintArea) {
        case VALUE :
          w = (thumbRect.x + (thumbRect.width / 2)) - x + 1;

          break;

        case REMAINING :
          x = thumbRect.x + (thumbRect.width / 2);
          w = (trackRect.x + trackRect.width - 1) - x;

          break;

        default :
          break;
      }

      g.fillRect(x, y, w, h);
    }

    g.setColor(c);
  }

  @Override
  public void paintTrack(Graphics g) {
    if (trackPainter == null) {
      super.paintTrack(g);
    } else {
      int w=trackRect.width;
      int h=trackRect.height;
      int o=slider.getOrientation()==SwingConstants.HORIZONTAL ? iPainter.HORIZONTAL :iPainter.VERTICAL;
      if(trackPainterWidth!=0) {
        if(o==iPainter.HORIZONTAL) {
          h=Math.min(trackPainterWidth,h);
        }
        else {
          w=Math.min(trackPainterWidth,w);
        }
      }
      trackPainter.paint(slider, (Graphics2D) g, trackRect.x, trackRect.y, w, h, true,o);
    }
  }

  public void setPaintInnerTicks(boolean minor, int offset, Color color, PaintArea area) {
    paintInnerTicks = true;
    this.minor      = minor;
    this.tickOffset = offset;
    this.tickColor  = color;
    this.paintArea  = area;
  }

  public void setThumbIcon(Icon thumbIcon) {
    this.thumbIcon = thumbIcon;
  }

  public void setTickStyle(TickStyle tickStyle) {
    this.tickStyle = tickStyle;
  }

  public void setTrackPainter(iPlatformPainter p) {
    this.trackPainter = p;
  }

  @Override
  public Dimension getMinimumSize(JComponent c) {
    Dimension d = super.getMinimumSize(c);

    if (slider.getOrientation() == JSlider.VERTICAL) {
      if (paintInnerTicks) {
        d.width -= tickRect.width;
      }
    } else {
      if (paintInnerTicks) {
        d.height -= tickRect.height;
      }
    }

    return d;
  }

  @Override
  public Dimension getPreferredSize(JComponent c) {
    Dimension d = super.getPreferredSize(c);

    if (slider.getOrientation() == JSlider.VERTICAL) {
      if (paintInnerTicks) {
        d.width -= tickRect.width;
      }
    } else {
      if (paintInnerTicks) {
        d.height -= tickRect.height;
      }
    }

    return d;
  }

  public Icon getThumbIcon() {
    return thumbIcon;
  }

  public TickStyle getTickStyle() {
    return tickStyle;
  }

  public iPainter getTrackPainter() {
    return trackPainter;
  }

  @Override
  protected void calculateThumbLocation() {
    super.calculateThumbLocation();

    if (thumbOffset != 0) {
      if (slider.getOrientation() == JSlider.HORIZONTAL) {
        thumbRect.x += thumbOffset;
      } else {
        thumbRect.y += thumbOffset;
      }
    }
  }

  @Override
  protected void calculateTickRect() {
    super.calculateTickRect();

    if (paintInnerTicks) {
      if (slider.getOrientation() == JSlider.HORIZONTAL) {
        tickRect.y      = trackRect.y + 1;
        tickRect.height = (slider.getPaintTicks())
                          ? trackRect.height - 2
                          : 0;
      } else {
        tickRect.width = (slider.getPaintTicks())
                         ? trackRect.width - 2
                         : 0;

        if (slider.getComponentOrientation().isLeftToRight()) {
          tickRect.x = trackRect.x - trackRect.width;
        } else {
          tickRect.x = trackRect.x + tickRect.width;
        }

        tickRect.y      = trackRect.y + 1;
        tickRect.height = trackRect.height;
      }
    }
  }

  protected void paintInnerTickForHorizSlider(Graphics g, Rectangle tickBounds, int x) {
    if ((x <= trackRect.x) || (x >= (trackRect.x + trackRect.width - 1))) {
      return;
    }

    switch(paintArea) {
      case VALUE :
        if (x >= thumbRect.x) {
          return;
        }

        break;

      case REMAINING :
        if (x <= (thumbRect.x + thumbRect.width - 1)) {
          return;
        }

        break;

      default :
        break;
    }

    Color c = g.getColor();

    if (tickColor != null) {
      g.setColor(tickColor);
    }

    int h = tickRect.height - (tickOffset * 2);

    g.drawLine(x, tickOffset, x, h);
    g.setColor(c);
  }

  protected void paintInnerTickForVertSlider(Graphics g, Rectangle tickBounds, int y) {
    if ((y <= trackRect.y) || (y >= (trackRect.y + trackRect.height - 1))) {
      return;
    }

    switch(paintArea) {
      case VALUE :
        if (y >= thumbRect.y) {
          return;
        }

        break;

      case REMAINING :
        if (y <= (thumbRect.y + thumbRect.height - 1)) {
          return;
        }

        break;

      default :
        break;
    }

    Color c = g.getColor();

    if (tickColor != null) {
      g.setColor(tickColor);
    }

    int w = thumbRect.width - (tickOffset * 2);

    g.drawLine(thumbRect.x + tickOffset, y, w, y);
    g.setColor(c);
  }

  @Override
  protected void paintMajorTickForHorizSlider(Graphics g, Rectangle tickBounds, int x) {
    if (!paintInnerTicks || minor) {
      super.paintMajorTickForHorizSlider(g, tickBounds, x);
    } else {
      paintInnerTickForHorizSlider(g, tickBounds, x);
    }
  }

  @Override
  protected void paintMajorTickForVertSlider(Graphics g, Rectangle tickBounds, int y) {
    if (!paintInnerTicks || minor) {
      super.paintMajorTickForVertSlider(g, tickBounds, y);
    } else {
      paintInnerTickForVertSlider(g, tickBounds, y);
    }
  }

  @Override
  protected void paintMinorTickForHorizSlider(Graphics g, Rectangle tickBounds, int x) {
    if (!paintInnerTicks ||!minor) {
      super.paintMinorTickForHorizSlider(g, tickBounds, x);
    } else {
      paintInnerTickForHorizSlider(g, tickBounds, x);
    }
  }

  @Override
  protected void paintMinorTickForVertSlider(Graphics g, Rectangle tickBounds, int y) {
    if (!paintInnerTicks ||!minor) {
      super.paintMinorTickForVertSlider(g, tickBounds, y);
    } else {
      paintInnerTickForVertSlider(g, tickBounds, y);
    }
  }

  @Override
  protected Dimension getThumbSize() {
    Dimension size = new Dimension();

    if (thumbIcon == null) {
      if (slider.getOrientation() == JSlider.VERTICAL) {
        size.width  = 16;
        size.height = 5;
      } else {
        size.width  = 5;
        size.height = 16;
      }
    } else {
      size.width  = thumbIcon.getIconWidth();
      size.height = thumbIcon.getIconHeight();
    }

    return size;
  }

  public int getThumbOffset() {
    return thumbOffset;
  }

  public void setThumbOffset(int thumbOffset) {
    this.thumbOffset = thumbOffset;
  }

  public int getTrackPainterWidth() {
    return trackPainterWidth;
  }

  public void setTrackPainterWidth(int trackPainterWidth) {
    this.trackPainterWidth = trackPainterWidth;
  }
}
