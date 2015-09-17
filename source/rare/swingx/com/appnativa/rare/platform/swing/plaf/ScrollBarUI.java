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

import com.appnativa.rare.Platform;
import com.appnativa.rare.platform.swing.ui.UIProxyBorder;
import com.appnativa.rare.platform.swing.ui.util.SwingGraphics;
import com.appnativa.rare.platform.swing.ui.util.SwingHelper;
import com.appnativa.rare.ui.BorderUtils;
import com.appnativa.rare.ui.ColorUtils;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIColorShade;
import com.appnativa.rare.ui.UIProperties;
import com.appnativa.rare.ui.border.UICompoundBorder;
import com.appnativa.rare.ui.border.UIEmptyBorder;
import com.appnativa.rare.ui.border.UILineBorder;
import com.appnativa.rare.ui.border.UIMatteBorder;
import com.appnativa.rare.ui.iPlatformPaint;
import com.appnativa.rare.ui.iPlatformPath;
import com.appnativa.rare.ui.iPlatformShape;
import com.appnativa.rare.ui.painter.UIBackgroundPainter;
import com.appnativa.rare.ui.painter.iBackgroundPainter;
import com.appnativa.rare.ui.painter.iGradientPainter;
import com.appnativa.rare.ui.painter.iPainter;
import com.appnativa.rare.ui.painter.iPlatformPainter;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.event.MouseEvent;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.AbstractButton;
import javax.swing.BoundedRangeModel;
import javax.swing.ButtonModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.UIResource;
import javax.swing.plaf.basic.BasicScrollBarUI;

/**
 *
 * @author Don DeCcoteau
 */
public class ScrollBarUI extends BasicScrollBarUI implements ChangeListener, PropertyChangeListener {
  private static Border        hButtonBorder;
  private static Border        hOffsetBorder;
  private static Border        vButtonBorder;
  private static Border        vOffsetBorder;
  iPlatformPath                borderPath;
  protected Color              arrowColor;
  protected iPainter           bottomButtonPainter;
  protected boolean            buttonArmed;
  protected ImageIcon          horizThumbIcon;
  protected iPlatformPainter   horizThumbPainter;
  protected iBackgroundPainter horizTrackPainter;
  protected iPlatformPainter   leftButtonPainter;
  protected iPlatformPainter   rightButtonPainter;
  protected UILineBorder       roundedBorder;
  protected Border             scrollbarBorder;
  protected Border             standardBorder;
  protected UIColor            thumbBorderColor;
  protected iPainter           topButtonPainter;
  protected UIColor            trackBorderColor;
  protected Composite          trackComposite;
  protected ImageIcon          vertThumbIcon;
  protected iPlatformPainter   vertThumbPainter;
  protected iBackgroundPainter vertTrackPainter;
  private boolean              mac              = false;    // Platform.isMac();
  private boolean              buttonsVisible   = true;
  private boolean              showThumbBorder  = true;
  private boolean              paintArrows      = true;
  private boolean              showTrackBorder  = true;
  private boolean              showTrack        = true;
  private boolean              needsConfiguring = true;
  private Color                arrowHighlight;
  private Color                arrowShadow;
  private boolean              isOverlayIcon;
  SwingGraphics                graphics;
  private boolean              overThumb;

  public ScrollBarUI() {}

  public void configure(UIProperties defaults) {
    setNeedsConfiguring(false);

    UIColor color = UIColor.fromColor(defaults.getColor("Rare.ScrollBar.background"));

    if (color == null) {
      color = ColorUtils.getBackground();
    }

    if (scrollbar != null) {
      if ((color != null) && (scrollbar.getBackground() instanceof ColorUIResource)) {
        scrollbar.setBackground(color);
      }

      if (scrollbar.getBackground() == ColorUtils.TRANSPARENT_COLOR) {
        scrollbar.setOpaque(false);
      }

      color = UIColor.fromColor(defaults.getColor("Rare.ScrollBar.foreground"));

      if (color == null) {
        color = ColorUtils.getForeground();
      }

      if ((color != null) && (scrollbar.getForeground() instanceof ColorUIResource)) {
        scrollbar.setForeground(color);
      }
    }

    if (roundedBorder == null) {
      roundedBorder = new UILineBorder(defaults.getColor("Rare.backgroundShadow"), 2, 12, 12);
    }

    color = ColorUtils.getBackground();

    iBackgroundPainter trp = defaults.getBackgroundPainter("Rare.ScrollBar.trackPainter");
    iPlatformPainter   tbp = defaults.getPainter("Rare.ScrollBar.thumbPainter");

    if (trp == null) {
      trp = new UIBackgroundPainter(Platform.getUIDefaults().getColor("Rare.backgroundDkGradient"),
                                    new UIColorShade(color, (255 * 10) / 100));
    }

    if (tbp == null) {
      tbp = new UIBackgroundPainter(Platform.getUIDefaults().getColor("Rare.backgroundLtGradient"),
                                    Platform.getUIDefaults().getColor("Rare.backgroundDkGradient"));
    }

    vertThumbPainter    = defaults.getPainter("Rare.ScrollBar.vertThumbPainter");
    horizTrackPainter   = defaults.getBackgroundPainter("Rare.ScrollBar.vertTrackPainter");
    horizThumbPainter   = defaults.getPainter("Rare.ScrollBar.horizThumbPainter");
    vertTrackPainter    = defaults.getBackgroundPainter("Rare.ScrollBar.vertTrackPainter");
    thumbColor          = (Color) defaults.get("Rare.ScrollBar.thumbColor");
    thumbBorderColor    = UIColor.fromColor((Color) defaults.get("Rare.ScrollBar.thumbBorderColor"));
    arrowColor          = (Color) defaults.get("Rare.ScrollBar.arrow");
    trackBorderColor    = UIColor.fromColor((Color) defaults.get("Rare.ScrollBar.trackBorder"));
    arrowShadow         = (Color) defaults.get("Rare.ScrollBar.arrowShadow");
    arrowHighlight      = (Color) defaults.get("Rare.ScrollBar.arrowHighlight");
    trackBorderColor    = UIColor.fromColor((Color) defaults.get("Rare.ScrollBar.trackBorderColor"));
    trackComposite      = AlphaComposite.getInstance(AlphaComposite.SRC_OVER);
    leftButtonPainter   = defaults.getPainter("Rare.ScrollBar.leftButtonPainter");
    rightButtonPainter  = defaults.getPainter("Rare.ScrollBar.rightButtonPainter");
    topButtonPainter    = defaults.getPainter("Rare.ScrollBar.topButtonPainter");
    bottomButtonPainter = defaults.getPainter("Rare.ScrollBar.bottomButtonPainter");
    horizThumbIcon      = (ImageIcon) defaults.get("Rare.ScrollBar.horizThumbIcon");
    vertThumbIcon       = (ImageIcon) defaults.get("Rare.ScrollBar.horizThumbIcon");
    isOverlayIcon       = defaults.get("Rare.ScrollBar.isOverlayIcon") == Boolean.TRUE;

    if (thumbColor == null) {
      thumbColor = color;
    }

    if (arrowColor == null) {
      arrowColor = Platform.getUIDefaults().getColor("Rare.backgroundShadow");
    }

    if (trackBorderColor == null) {
      trackBorderColor = color;
    }

    if (thumbBorderColor == null) {
      thumbBorderColor = Platform.getUIDefaults().getColor("Rare.backgroundShadow");
    }

    if (horizTrackPainter == null) {
      horizTrackPainter = trp;
    }

    if (vertTrackPainter == null) {
      vertTrackPainter = trp;
    }

    if (vertThumbPainter == null) {
      vertThumbPainter = tbp;
    }

    if (horizThumbPainter == null) {
      horizThumbPainter = tbp;
    }

    Float op = (Float) defaults.get("Rare.ScrollBar.trackOpacity");

    if (op != null) {
      trackComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, op);
    }

    if (defaults.get("Rare.ScrollBar.showButtons") instanceof Boolean) {
      setButtonsVisible((Boolean) defaults.get("Rare.ScrollBar.showButtons"));
    }

    if (defaults.get("Rare.ScrollBar.showThumbBorder") instanceof Boolean) {
      setShowThumbBorder((Boolean) defaults.get("Rare.ScrollBar.showThumbBorder"));
    }

    if (defaults.get("Rare.ScrollBar.showTrackBorder") instanceof Boolean) {
      setShowTrackBorder((Boolean) defaults.get("Rare.ScrollBar.showTrackBorder"));
    }

    if (defaults.get("Rare.ScrollBar.showTrack") instanceof Boolean) {
      setShowTrack((Boolean) defaults.get("Rare.ScrollBar.showTrack"));
    }

    if (defaults.get("Rare.ScrollBar.paintArrows") instanceof Boolean) {
      setPaintArrows((Boolean) defaults.get("Rare.ScrollBar.paintArrows"));
    }
  }

  /**
   * Creates a UI for a JScrollBar.
   *
   * @param c
   *          the text field
   * @return the UI
   */
  public static ComponentUI createUI(JComponent c) {
    return new ScrollBarUI();
  }

  @Override
  public void installUI(JComponent c) {
    super.installUI(c);
    needsConfiguring = true;

    if ((c.getBorder() == null) ||!(c.getBorder() instanceof UIResource)) {
      c.setBorder(getScrollbarBorder());
    }

    c.addPropertyChangeListener("orientation", propertyChangeListener);
    handleOrientation();
  }

  @Override
  public void paint(Graphics g, JComponent c) {
    if (isNeedsConfiguring()) {
      configure(c);
    }

    Color     bg;
    Color     dk    = null;
    Color     lt    = null;
    boolean   horiz = scrollbar.getOrientation() == JScrollBar.HORIZONTAL;
    Rectangle r     = c.getBounds();
    int       w     = r.width;
    int       h     = r.height;
    int       d;
    Dimension size = incrButton.getSize();

    if (horiz) {
      d = size.width * 2;
    } else {
      d = size.height * 2;
    }

    r.x = 0;
    r.y = 0;
    bg  = c.getBackground();
    dk  = new Color(ColorUtils.adjustLuminance(bg.getRGB(), -50));
    lt  = new Color(ColorUtils.adjustLuminance(bg.getRGB(), -20));

    if (showTrack) {
      Color oc = g.getColor();

      g.setColor(bg);
      g.fillRect(0, 0, w, h);
      g.setColor(oc);
    }

    AbstractButton lb = decrButton;
    AbstractButton rb = incrButton;

    if (!scrollbar.getComponentOrientation().isLeftToRight()) {
      AbstractButton b = lb;

      lb = rb;
      rb = b;
    }

    if (horiz) {
      r.width = d;
      r.x     = mac
                ? rb.getX() - d
                : 0;

      if (lb.getModel().isArmed()) {
        SwingHelper.fillGradient((Graphics2D) g, r, bg, dk, horiz);
      } else {
        SwingHelper.fillGradient((Graphics2D) g, r, bg, lt, horiz);
      }

      r.x = mac
            ? rb.getX()
            : w - d;

      if (rb.getModel().isArmed()) {
        SwingHelper.fillGradient((Graphics2D) g, r, bg, dk, horiz);
      } else {
        SwingHelper.fillGradient((Graphics2D) g, r, bg, lt, horiz);
      }
    } else {
      r.y      = mac
                 ? rb.getY() - d
                 : 0;
      r.height = d;

      if (lb.getModel().isArmed()) {
        SwingHelper.fillGradient((Graphics2D) g, r, bg, dk, horiz);
      } else {
        SwingHelper.fillGradient((Graphics2D) g, r, bg, lt, horiz);
      }

      r.y = mac
            ? rb.getY()
            : h - d;

      if (rb.getModel().isArmed()) {
        SwingHelper.fillGradient((Graphics2D) g, r, bg, dk, horiz);
      } else {
        SwingHelper.fillGradient((Graphics2D) g, r, bg, lt, horiz);
      }
    }

    super.paint(g, c);
  }

  @Override
  public void propertyChange(PropertyChangeEvent evt) {
    if ("orientation" == evt.getPropertyName()) {
      handleOrientation();
    }
  }

  @Override
  public void stateChanged(ChangeEvent e) {
    boolean a = ((ButtonModel) e.getSource()).isArmed();

    if (a != buttonArmed) {
      buttonArmed = a;
      scrollbar.repaint();
    }
  }

  public void setArrowColor(Color arrow) {
    this.arrowColor = arrow;
  }

  public void setArrowHighlight(Color arrowHighlight) {
    this.arrowHighlight = arrowHighlight;
  }

  public void setArrowShadow(Color arrowShadow) {
    this.arrowShadow = arrowShadow;
  }

  /**
   * @param buttonsVisible
   *          the buttonsVisible to set
   */
  public void setButtonsVisible(boolean buttonsVisible) {
    this.buttonsVisible = buttonsVisible;

    if (incrButton != null) {
      incrButton.setVisible(buttonsVisible);
    }

    if (decrButton != null) {
      decrButton.setVisible(buttonsVisible);
    }
  }

  public void setDecButtonPainter(iPlatformPainter bp) {
    this.leftButtonPainter = bp;
    this.topButtonPainter  = bp;
  }

  public void setIncButtonPainter(iPlatformPainter bp) {
    this.rightButtonPainter  = bp;
    this.bottomButtonPainter = bp;
  }

  public void setMacStyle(boolean mac) {
    this.mac = mac;
  }

  /**
   * @param needsConfiguring
   *          the needsConfiguring to set
   */
  public void setNeedsConfiguring(boolean needsConfiguring) {
    this.needsConfiguring = needsConfiguring;
  }

  /**
   * @param paintArrows
   *          the paintArrows to set
   */
  public void setPaintArrows(boolean paintArrows) {
    this.paintArrows = paintArrows;

    if (decrButton instanceof ArrowButton) {
      ((ArrowButton) decrButton).setPaintArrows(paintArrows);
    }

    if (incrButton instanceof ArrowButton) {
      ((ArrowButton) incrButton).setPaintArrows(paintArrows);
    }
  }

  /**
   * @param showThumbBorder
   *          the showThumbBorder to set
   */
  public void setShowThumbBorder(boolean showThumbBorder) {
    this.showThumbBorder = showThumbBorder;
  }

  public void setShowTrack(boolean showTrack) {
    this.showTrack = showTrack;
  }

  public void setShowTrackBorder(boolean show) {
    this.showTrackBorder = show;
  }

  public void setThumbIcon(ImageIcon icon) {
    horizThumbIcon = icon;
    vertThumbIcon  = icon;
  }

  public void setThumbPainter(iPlatformPainter thumbPainter) {
    this.horizThumbPainter = thumbPainter;
    this.vertThumbPainter  = thumbPainter;
  }

  public void setTrackComposite(AlphaComposite composite) {
    this.trackComposite = composite;
  }

  public void setTrackPainter(iBackgroundPainter trackPainter) {
    this.horizTrackPainter = trackPainter;
    this.vertTrackPainter  = trackPainter;
  }

  public Color getArrowHighlight() {
    return arrowHighlight;
  }

  public Color getArrowShadow() {
    return arrowShadow;
  }

  public ImageIcon getThumbIcon() {
    return isHorizontal()
           ? horizThumbIcon
           : vertThumbIcon;
  }

  public iPlatformPainter getThumbPainter() {
    return isHorizontal()
           ? horizThumbPainter
           : vertThumbPainter;
  }

  public iBackgroundPainter getTrackPainter() {
    return isHorizontal()
           ? horizTrackPainter
           : vertTrackPainter;
  }

  /**
   * @return the buttonsVisible
   */
  public boolean isButtonsVisible() {
    return buttonsVisible;
  }

  public boolean isHorizontal() {
    return scrollbar.getOrientation() == JScrollBar.HORIZONTAL;
  }

  /**
   * @return the needsConfiguring
   */
  public boolean isNeedsConfiguring() {
    return needsConfiguring;
  }

  /**
   * @return the paintArrows
   */
  public boolean isPaintArrows() {
    return paintArrows;
  }

  /**
   * @return the showThumbBorder
   */
  public boolean isShowThumbBorder() {
    return showThumbBorder;
  }

  public boolean isShowTrack() {
    return showTrack;
  }

  protected void configure(JComponent c) {
    configure(Platform.getUIDefaults());
    c.setOpaque(false);
  }

  @Override
  protected void configureScrollBarColors() {
    super.configureScrollBarColors();

    if (needsConfiguring) {
      arrowColor = new UIColorShade(UIColor.DARKGRAY, "Rare.ScrollBar.arrow");
    }
  }

  protected JButton createButton(int orientation, iPlatformPainter bp) {
    Color bg = trackColor;

    if (bg == null) {
      bg = Color.black;
    }

    Color       sh = (arrowShadow == null)
                     ? arrowColor.darker()
                     : arrowShadow;
    Color       hi = (arrowHighlight == null)
                     ? arrowColor.brighter()
                     : arrowHighlight;
    ArrowButton b  = new ArrowButton(orientation, bg, sh, arrowColor, hi, bp) {
      @Override
      public Dimension getPreferredSize() {
        if (!isButtonsVisible()) {
          return new Dimension(0, 0);
        }

        return super.getPreferredSize();
      }
      @Override
      public Dimension getMinimumSize() {
        if (!isButtonsVisible()) {
          return new Dimension(0, 0);
        }

        return super.getMinimumSize();
      }
    };

    b.setPaintPressed(false);
    b.setContentAreaFilled(false);
    b.setOpaque(false);
    b.getModel().addChangeListener(this);
    b.setVisible(buttonsVisible);
    b.setPaintArrows(paintArrows);

    return b;
  }

  @Override
  protected JButton createDecreaseButton(int orientation) {
    return createButton(orientation, (orientation == JScrollBar.HORIZONTAL)
                                     ? leftButtonPainter
                                     : rightButtonPainter);
  }

  @Override
  protected JButton createIncreaseButton(int orientation) {
    return createButton(orientation, (orientation == JScrollBar.HORIZONTAL)
                                     ? leftButtonPainter
                                     : rightButtonPainter);
  }

  @Override
  protected TrackListener createTrackListener() {
    return mac
           ? new MacTrackListener()
           : super.createTrackListener();
  }

  protected void handleOrientation() {
    if (scrollbar.getOrientation() == JScrollBar.HORIZONTAL) {
      minimumThumbSize.height = 10;
      minimumThumbSize.width  = 16;

      if (mac) {
        AbstractButton fb = scrollbar.getComponentOrientation().isLeftToRight()
                            ? decrButton
                            : incrButton;

        if (fb != null) {
          if (hButtonBorder == null) {
            hButtonBorder = new UIMatteBorder(0, 0, 0, 1, ColorUtils.getColor("controlShadow"));
          }

          fb.setBorder(hButtonBorder);
        }

        if (hOffsetBorder == null) {
          hOffsetBorder = new UIEmptyBorder(0, 0, 0, 4);
        }

        setBorder(hOffsetBorder);
      }
    } else {
      minimumThumbSize.height = 16;
      minimumThumbSize.width  = 10;

      if (mac) {
        AbstractButton fb = scrollbar.getComponentOrientation().isLeftToRight()
                            ? decrButton
                            : incrButton;

        if (fb != null) {
          if (vButtonBorder == null) {
            vButtonBorder = new UIMatteBorder(0, 0, 1, 0, ColorUtils.getColor("Rare.backgroundShadow"));
          }

          fb.setBorder(vButtonBorder);
        }

        if (vOffsetBorder == null) {
          vOffsetBorder = new UIEmptyBorder(4, 0, 0, 0);
        }

        setBorder(vOffsetBorder);
      }
    }
  }

  @Override
  protected void layoutHScrollbar(JScrollBar sb) {
    super.layoutHScrollbar(sb);

    if (mac) {
      AbstractButton lb = decrButton;
      AbstractButton rb = incrButton;

      if (!scrollbar.getComponentOrientation().isLeftToRight()) {
        AbstractButton b = lb;

        lb = rb;
        rb = b;
      }

      Rectangle lr = lb.getBounds();
      Rectangle rr = rb.getBounds();

      lr.x = rr.x - lr.width;
      lb.setBounds(lr);
      trackRect.x -= lr.width;
    }
  }

  @Override
  protected void layoutVScrollbar(JScrollBar sb) {
    super.layoutVScrollbar(sb);

    if (mac) {
      AbstractButton lb = decrButton;
      AbstractButton rb = incrButton;

      if (!scrollbar.getComponentOrientation().isLeftToRight()) {
        AbstractButton b = lb;

        lb = rb;
        rb = b;
      }

      Rectangle lr = lb.getBounds();
      Rectangle rr = rb.getBounds();

      if ((lr.width > 0) && (lr.height > 0)) {
        lr.y = rr.y - lr.height;
        lb.setBounds(lr);
        trackRect.y -= lr.height;
      }
    }
  }

  @Override
  protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
    if (isNeedsConfiguring()) {
      configure(c);
      setNeedsConfiguring(false);
    }

    Graphics2D g2             = (Graphics2D) g;
    Color      ocolor         = g2.getColor();
    Stroke     ostroke        = g2.getStroke();
    Object     renderingHinta = g2.getRenderingHint(RenderingHints.KEY_ANTIALIASING);
    int        x              = thumbBounds.x;
    int        y              = thumbBounds.y;
    int        width          = thumbBounds.width;
    int        height         = thumbBounds.height;
    Color      color          = thumbColor;

    x++;
    y++;
    width  -= 2;
    height -= 2;

    Color dk = thumbLightShadowColor;

    if (!c.isEnabled()) {
      Color dc = Platform.getUIDefaults().getColor("Rare.LineBorder.disabledColor");

      if (dc != null) {
        color = dc;
      }

      dk = color.darker();
    }

    iPlatformPainter tp = getThumbPainter();

    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    g2.setColor(color);

    Shape   clip  = null;
    boolean showb = isShowThumbBorder();

    graphics = SwingGraphics.fromGraphics(g, c, graphics);

    iPlatformShape shape = showb
                           ? roundedBorder.getShape(graphics, x, y, width, height)
                           : null;
    boolean        horiz = scrollbar.getOrientation() == JScrollBar.HORIZONTAL;

    if (tp != null) {
      if ((tp instanceof iGradientPainter) && ((iGradientPainter) tp).isGradientPaintEnabled()) {
        if (scrollbar.getOrientation() == JScrollBar.HORIZONTAL) {
          ((iGradientPainter) tp).setGradientDirection(iGradientPainter.Direction.VERTICAL_TOP);
        } else {
          ((iGradientPainter) tp).setGradientDirection(iGradientPainter.Direction.HORIZONTAL_LEFT);
        }
      }

      iPlatformPaint p = (shape == null)
                         ? null
                         : tp.getPaint(width, height);

      if (p != null) {
        g2.setPaint(p.getPaint());
        g2.fill(shape.getShape());
      } else {
        clip = g2.getClip();
        roundedBorder.clip(graphics, x, y, width, height);
        tp.paint(c, g2, x, y, width, height, true, iPainter.UNKNOWN);
      }
    } else {
      SwingHelper.fillGradient((Graphics2D) g, (shape == null)
              ? thumbBounds
              : shape.getShape(), color, dk, horiz);
    }

    ImageIcon ic = getThumbIcon();

    if (ic != null) {
      if (isOverlayIcon) {
        ic.paintIcon(c, g, x + ((width - ic.getIconWidth()) / 2), y + ((height - ic.getIconHeight()) / 2));
      } else if (ic.getImage() != null) {
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(ic.getImage(), x, y, width, height, c);
      }
    }

    if (clip != null) {
      g2.setClip(clip);
    }

    if (showb) {
      roundedBorder.setLineColor(thumbBorderColor);
      roundedBorder.paintBorder(c, g, x, y, width, height);
    }

    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, renderingHinta);
    g2.setStroke(ostroke);
    g2.setColor(ocolor);
  }

  @Override
  protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
    if (!showTrack) {
      return;
    }

    if (isNeedsConfiguring()) {
      configure(c);
    }

    Graphics2D g2             = (Graphics2D) g;
    Color      ocolor         = g2.getColor();
    Stroke     ostroke        = g2.getStroke();
    Object     orenderingHint = g2.getRenderingHint(RenderingHints.KEY_ANTIALIASING);
    int        x              = trackBounds.x;
    int        y              = trackBounds.y;
    int        width          = trackBounds.width;
    int        height         = trackBounds.height;

    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    Shape              clip = null;
    iBackgroundPainter bp   = getTrackPainter();

    graphics = SwingGraphics.fromGraphics(g, c, graphics);

    iPlatformShape shape = showTrackBorder
                           ? roundedBorder.getShape(graphics, x, y, width, height)
                           : null;

    if (bp instanceof iGradientPainter) {
      if (scrollbar.getOrientation() == JScrollBar.HORIZONTAL) {
        ((iGradientPainter) bp).setGradientDirection(iGradientPainter.Direction.VERTICAL_TOP);
      } else {
        ((iGradientPainter) bp).setGradientDirection(iGradientPainter.Direction.HORIZONTAL_LEFT);
      }
    }

    Composite oc = g2.getComposite();

    g2.setComposite(trackComposite);

    iPlatformPaint p = (shape == null)
                       ? null
                       : bp.getPaint(width, height);

    if (p != null) {
      g2.setPaint(p.getPaint());
      g2.fill(shape.getShape());
    } else {
      clip = g2.getClip();
      roundedBorder.clip(graphics, x, y, width, height);
      bp.paint(c, g2, x, y, width, height, true, iPainter.UNKNOWN);
    }

    if (clip != null) {
      g2.setClip(clip);
    }

    if (showTrackBorder) {
      roundedBorder.setLineColor(trackBorderColor);
      roundedBorder.paintBorder(c, g, x, y, width, height);
    }

    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, orenderingHint);
    g2.setStroke(ostroke);
    g2.setColor(ocolor);
    g2.setComposite(oc);
  }

  @Override
  protected void uninstallListeners() {
    super.uninstallListeners();
    scrollbar.removePropertyChangeListener(this);
  }

  @Override
  protected void setThumbBounds(int x, int y, int width, int height) {
    setThumbBoundsEx(x, y, width, height, true);
  }

  protected void setThumbBoundsEx(int x, int y, int width, int height, boolean adjust) {
    if (adjust && mac) {
      switch(scrollbar.getOrientation()) {
        case JScrollBar.VERTICAL :
          y -= decrButton.getHeight();

          break;

        case JScrollBar.HORIZONTAL :
          x -= decrButton.getWidth();

          break;
      }
    }

    super.setThumbBounds(x, y, width, height);
  }

  protected Border getScrollbarBorder() {
    if (standardBorder != null) {
      return standardBorder;
    }

    UIProperties defaults = Platform.getUIDefaults();
    Border       b        = defaults.getBorder("Rare.ScrollBar.border");

    if (b == null) {
      b = defaults.getBorder("ScrollBar.border");
    }

    return b;
  }

  @Override
  public boolean isThumbRollover() {
    return overThumb;
  }

  private void updateThumbState(int x, int y) {
    Rectangle rect = getThumbBounds();

    overThumb = rect.contains(x, y);
  }

  private void setBorder(Border b) {
    Border ob = scrollbar.getBorder();

    if (ob == null) {
      ob = BorderUtils.EMPTY_BORDER;
    }

    SBCompoundBorderEx cb = null;

    if (ob instanceof SBCompoundBorderEx) {
      cb = (SBCompoundBorderEx) ob;
      cb.setInsideBorder(UIProxyBorder.fromBorder(b));
    } else {
      cb = new SBCompoundBorderEx(ob, b);
    }

    scrollbar.setBorder(cb);
  }

  /**
   * Track mouse drags.
   */
  protected class MacTrackListener extends TrackListener {
    private transient int direction = +1;

    /**
     * Set the models value to the position of the thumb's top of Vertical
     * scrollbar, or the left/right of Horizontal scrollbar in
     * left-to-right/right-to-left scrollbar relative to the origin of the
     * track.
     */
    @Override
    public void mouseDragged(MouseEvent e) {
      if (SwingUtilities.isRightMouseButton(e)
          || (!getSupportsAbsolutePositioning() && SwingUtilities.isMiddleMouseButton(e))) {
        return;
      }

      if (!scrollbar.isEnabled() || getThumbBounds().isEmpty()) {
        return;
      }

      if (isDragging) {
        setValueFrom(e);
      } else {
        currentMouseX = e.getX();
        currentMouseY = e.getY();
        updateThumbState(currentMouseX, currentMouseY);
        startScrollTimerIfNecessary();
      }
    }

    /**
     * Invoked when the mouse exits the scrollbar.
     *
     * @param e
     *          MouseEvent further describing the event
     * @since 1.5
     */
    @Override
    public void mouseExited(MouseEvent e) {
      if (!isDragging) {
        setThumbRollover(false);
      }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
      if (!isDragging) {
        updateThumbState(e.getX(), e.getY());
      }
    }

    /**
     * If the mouse is pressed above the "thumb" component then reduce the
     * scrollbars value by one page ("page up"), otherwise increase it by one
     * page. If there is no thumb then page up if the mouse is in the upper half
     * of the track.
     */
    @Override
    public void mousePressed(MouseEvent e) {
      if (SwingUtilities.isRightMouseButton(e)
          || (!getSupportsAbsolutePositioning() && SwingUtilities.isMiddleMouseButton(e))) {
        return;
      }

      if (!scrollbar.isEnabled()) {
        return;
      }

      if (!scrollbar.hasFocus() && scrollbar.isRequestFocusEnabled()) {
        scrollbar.requestFocus();
      }

      scrollbar.setValueIsAdjusting(true);
      currentMouseX = e.getX();
      currentMouseY = e.getY();

      // Clicked in the Thumb area?
      if (getThumbBounds().contains(currentMouseX, currentMouseY)) {
        switch(scrollbar.getOrientation()) {
          case JScrollBar.VERTICAL :
            offset = currentMouseY - getThumbBounds().y;

            break;

          case JScrollBar.HORIZONTAL :
            offset = currentMouseX - getThumbBounds().x;

            break;
        }

        isDragging = true;

        return;
      } else if (getSupportsAbsolutePositioning() && SwingUtilities.isMiddleMouseButton(e)) {
        switch(scrollbar.getOrientation()) {
          case JScrollBar.VERTICAL :
            offset = getThumbBounds().height / 2;

            break;

          case JScrollBar.HORIZONTAL :
            offset = getThumbBounds().width / 2;

            break;
        }

        isDragging = true;
        setValueFrom(e);

        return;
      }

      isDragging = false;

      Dimension sbSize = scrollbar.getSize();

      direction = +1;

      switch(scrollbar.getOrientation()) {
        case JScrollBar.VERTICAL :
          if (getThumbBounds().isEmpty()) {
            int scrollbarCenter = sbSize.height / 2;

            direction = (currentMouseY < scrollbarCenter)
                        ? -1
                        : +1;
          } else {
            int thumbY = getThumbBounds().y;

            direction = (currentMouseY < thumbY)
                        ? -1
                        : +1;
          }

          break;

        case JScrollBar.HORIZONTAL :
          if (getThumbBounds().isEmpty()) {
            int scrollbarCenter = sbSize.width / 2;

            direction = (currentMouseX < scrollbarCenter)
                        ? -1
                        : +1;
          } else {
            int thumbX = getThumbBounds().x;

            direction = (currentMouseX < thumbX)
                        ? -1
                        : +1;
          }

          if (!scrollbar.getComponentOrientation().isLeftToRight()) {
            direction = -direction;
          }

          break;
      }

      scrollByBlock(direction);
      scrollTimer.stop();
      scrollListener.setDirection(direction);
      scrollListener.setScrollByBlock(true);
      startScrollTimerIfNecessary();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
      if (isDragging) {
        updateThumbState(e.getX(), e.getY());
      }

      if (SwingUtilities.isRightMouseButton(e)
          || (!getSupportsAbsolutePositioning() && SwingUtilities.isMiddleMouseButton(e))) {
        return;
      }

      if (!scrollbar.isEnabled()) {
        return;
      }

      Rectangle r = getTrackBounds();

      scrollbar.repaint(r.x, r.y, r.width, r.height);
      trackHighlight = NO_HIGHLIGHT;
      isDragging     = false;
      offset         = 0;
      scrollTimer.stop();
      scrollbar.setValueIsAdjusting(false);
    }

    private int adjustValueIfNecessary(int value) {
      if (scrollbar.getParent() instanceof JScrollPane) {
        JScrollPane scrollpane = (JScrollPane) scrollbar.getParent();
        JViewport   viewport   = scrollpane.getViewport();
        Component   view       = viewport.getView();

        if (view instanceof JList) {
          JList list = (JList) view;

          if (Platform.getUIDefaults().get("List.lockToPositionOnScroll") == Boolean.TRUE) {
            int adjustedValue = value;
            int mode          = list.getLayoutOrientation();
            int orientation   = scrollbar.getOrientation();

            if ((orientation == JScrollBar.VERTICAL) && (mode == JList.VERTICAL)) {
              int       index = list.locationToIndex(new Point(0, value));
              Rectangle rect  = list.getCellBounds(index, index);

              if (rect != null) {
                adjustedValue = rect.y;
              }
            }

            if ((orientation == JScrollBar.HORIZONTAL)
                && ((mode == JList.VERTICAL_WRAP) || (mode == JList.HORIZONTAL_WRAP))) {
              if (scrollpane.getComponentOrientation().isLeftToRight()) {
                int       index = list.locationToIndex(new Point(value, 0));
                Rectangle rect  = list.getCellBounds(index, index);

                if (rect != null) {
                  adjustedValue = rect.x;
                }
              } else {
                Point loc    = new Point(value, 0);
                int   extent = viewport.getExtentSize().width;

                loc.x += extent - 1;

                int       index = list.locationToIndex(loc);
                Rectangle rect  = list.getCellBounds(index, index);

                if (rect != null) {
                  adjustedValue = rect.x + rect.width - extent;
                }
              }
            }

            value = adjustedValue;
          }
        }
      }

      return value;
    }

    private void startScrollTimerIfNecessary() {
      if (scrollTimer.isRunning()) {
        return;
      }

      Rectangle tb = getThumbBounds();

      switch(scrollbar.getOrientation()) {
        case JScrollBar.VERTICAL :
          if (direction > 0) {
            if (tb.y + tb.height < currentMouseY) {
              scrollTimer.start();
            }
          } else if (tb.y > currentMouseY) {
            scrollTimer.start();
          }

          break;

        case JScrollBar.HORIZONTAL :
          if (((direction > 0) && isMouseAfterThumb()) || ((direction < 0) && isMouseBeforeThumb())) {
            scrollTimer.start();
          }

          break;
      }
    }

    private void setValueFrom(MouseEvent e) {
      boolean           active = isThumbRollover();
      BoundedRangeModel model  = scrollbar.getModel();
      Rectangle         thumbR = getThumbBounds();
      int               thumbMin, thumbMax, thumbPos;

      if (scrollbar.getOrientation() == JScrollBar.VERTICAL) {
        thumbMin = trackRect.y;
        thumbMax = thumbMin + trackRect.height - thumbR.height;
        thumbPos = Math.min(thumbMax, Math.max(thumbMin, (e.getY() - offset)));
        setThumbBoundsEx(thumbR.x, thumbPos, thumbR.width, thumbR.height, false);
      } else {
        thumbMin = trackRect.x;
        thumbMax = trackRect.x + trackRect.width - thumbR.width;
        thumbPos = Math.min(thumbMax, Math.max(thumbMin, (e.getX() - offset)));
        setThumbBoundsEx(thumbPos, thumbR.y, thumbR.width, thumbR.height, false);
      }

      /*
       * Set the scrollbars value. If the thumb has reached the end of the
       * scrollbar, then just set the value to its maximum. Otherwise compute
       * the value as accurately as possible.
       */
      if (thumbPos == thumbMax) {
        if ((scrollbar.getOrientation() == JScrollBar.VERTICAL)
            || scrollbar.getComponentOrientation().isLeftToRight()) {
          scrollbar.setValue(model.getMaximum() - model.getExtent());
        } else {
          scrollbar.setValue(model.getMinimum());
        }
      } else {
        float valueMax   = model.getMaximum() - model.getExtent();
        float valueRange = valueMax - model.getMinimum();
        float thumbValue = thumbPos - thumbMin;
        float thumbRange = thumbMax - thumbMin;
        int   value;

        if ((scrollbar.getOrientation() == JScrollBar.VERTICAL)
            || scrollbar.getComponentOrientation().isLeftToRight()) {
          value = (int) (0.5 + ((thumbValue / thumbRange) * valueRange));
        } else {
          value = (int) (0.5 + (((thumbMax - thumbPos) / thumbRange) * valueRange));
        }

        scrollbar.setValue(adjustValueIfNecessary(value + model.getMinimum()));
      }

      setThumbRollover(active);
    }

    private boolean isMouseAfterThumb() {
      return scrollbar.getComponentOrientation().isLeftToRight()
             ? isMouseRightOfThumb()
             : isMouseLeftOfThumb();
    }

    private boolean isMouseBeforeThumb() {
      return scrollbar.getComponentOrientation().isLeftToRight()
             ? isMouseLeftOfThumb()
             : isMouseRightOfThumb();
    }

    private boolean isMouseLeftOfThumb() {
      return currentMouseX < getThumbBounds().x;
    }

    private boolean isMouseRightOfThumb() {
      Rectangle tb = getThumbBounds();

      return currentMouseX > tb.x + tb.width;
    }
  }


  private static class SBCompoundBorderEx extends UICompoundBorder {
    public SBCompoundBorderEx(Border b1, Border b2) {
      super(UIProxyBorder.fromBorder(b1), UIProxyBorder.fromBorder(b2));
    }
  }
}
