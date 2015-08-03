package com.appnativa.rare.platform.swing.ui.view;

import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;

import javax.swing.JComponent;

import com.appnativa.rare.platform.swing.ui.util.SwingGraphics;
import com.appnativa.rare.ui.ColorUtils;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.painter.UIComponentPainter;
import com.appnativa.rare.ui.painter.iPainter;
import com.appnativa.rare.ui.painter.iPlatformPainter;

public class GlassPanel extends JComponent {
  SwingGraphics            graphics;
  private UIColor          lockedColor;
  private iPlatformPainter lockedPainter;
  boolean                  overlayContainer;
  MouseAdapter listener;
  public GlassPanel(boolean overlay) {
    super();
    setOpaque(false);
    setFocusable(false);
    overlayContainer = overlay;
    if (!overlay) {
      MouseAdapter l = new MouseAdapter() {
      };
      super.addMouseListener(l);
      addMouseMotionListener(l);
      addMouseWheelListener(l);
      addKeyListener(new KeyAdapter() {
      });
    }
  }

  public void dispose() {
    
  }
  @Override
  public void paint(Graphics g) {
    if (overlayContainer) {
      super.paint(g);
      return;
    }
    if ((lockedColor != null && lockedColor != ColorUtils.TRANSPARENT_COLOR) || (lockedPainter != null)) {
      graphics = SwingGraphics.fromGraphics(g, this, graphics);

      if (lockedPainter != null) {
        UIComponentPainter.paintPainter(lockedPainter, graphics, getWidth(), getHeight());
      } else if (lockedColor != null) {
        g.setColor(lockedColor);
        g.fillRect(0, 0, getWidth(), getHeight());
      }

      graphics.clear();
    }
  }

  /**
   * @param lockedColor
   *          the lockedColor to set
   */
  public void setLockedColor(UIColor lockedColor) {
    this.lockedColor = lockedColor;
    this.lockedPainter = ColorUtils.getPainter(lockedColor);
  }

  /**
   * @param lockedPainter
   *          the lockedPainter to set
   */
  public void setLockedPainter(iPlatformPainter lockedPainter) {
    this.lockedPainter = lockedPainter;
  }

  /**
   * @return the lockedColor
   */
  public UIColor getLockedColor() {
    return lockedColor;
  }

  /**
   * @return the lockedPainter
   */
  public iPainter getLockedPainter() {
    return lockedPainter;
  }
  @Override
  public boolean isFocusable() {
    return false;
  }
}
