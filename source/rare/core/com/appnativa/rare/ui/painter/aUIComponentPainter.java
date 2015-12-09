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

package com.appnativa.rare.ui.painter;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import com.appnativa.rare.Platform;
import com.appnativa.rare.ui.Displayed;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIColorShade;
import com.appnativa.rare.ui.UIImage;
import com.appnativa.rare.ui.UIInsets;
import com.appnativa.rare.ui.Utils;
import com.appnativa.rare.ui.iImageObserver;
import com.appnativa.rare.ui.iPaintedButton;
import com.appnativa.rare.ui.iPaintedButton.ButtonState;
import com.appnativa.rare.ui.iPlatformBorder;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.iPlatformGraphics;
import com.appnativa.rare.ui.iPlatformPaint;
import com.appnativa.rare.ui.iPlatformShape;
import com.appnativa.rare.ui.border.SharedLineBorder;
import com.appnativa.rare.util.PropertyChangeSupportEx;
import com.appnativa.rare.viewer.WindowViewer;
import com.appnativa.rare.widget.iWidget;
import com.google.j2objc.annotations.Weak;

/**
 *
 * @author Don DeCoteau
 */
public abstract class aUIComponentPainter extends aUIPlatformPainter
        implements iPlatformComponentPainter, Cloneable, iImageObserver {
  public static boolean             paintFocusDefault = !Platform.isTouchDevice();
  public static PaintBucket         focusPaint;
  protected static boolean          isApple    = Platform.isIOS() || Platform.isMac();
  int                               borderModCount;
  protected iBackgroundPainter      backgroundPainter;
  protected iPlatformPainter        bgOverlayPainter;
  protected iPlatformBorder         border;
  protected PropertyChangeListener  changeListener;
  protected PropertyChangeSupportEx changeSupport;
  protected UIInsets                insets;
  protected iPlatformPainter        overlayPainter;
  protected PainterHolder           painterHolder;
  protected iPlatformShape          parentShape;
  @Weak
  protected SharedLineBorder        sharedBorder;
  protected boolean                 paintAll;

  public aUIComponentPainter() {
    if (focusPaint == null) {
      focusPaint = Platform.getAppContext().getWidgetFocusPainter();
    }
  }

  @Override
  public void addPropertyChangeListener(PropertyChangeListener listener) {
    if (listener != null) {
      if (((changeListener == null) || (changeListener == listener)) && (changeSupport == null)) {
        changeListener = listener;

        return;
      }

      if (changeSupport == null) {
        changeSupport = new PropertyChangeSupportEx(this);

        if (changeListener != null) {
          changeSupport.addPropertyChangeListener(changeListener);
          changeListener = null;
        }
      }

      changeSupport.removePropertyChangeListener(listener);
      changeSupport.addPropertyChangeListener(listener);
    }
  }

  public void adjustPainterHolderForeground(UIColor fg) {
    PainterHolder ph = painterHolder;

    if ((ph != null) && (ph.getForeground(ButtonState.DEFAULT) != null)) {
      if (ph.isShared()) {
        ph            = (PainterHolder) ph.clone();
        painterHolder = ph;
      }

      if (ph.normalPainter != null) {
        ph.normalPainter = (PaintBucket) ph.normalPainter.clone();
        ph.normalPainter.setForegroundColor(fg);
        ;
      }

      ph.setForegroundColor(fg);
    }
  }

  public void checkForPainterHolderBackground(UIColor bg) {
    PainterHolder ph = painterHolder;

    if ((ph != null)
        && ((ph.getBackground(ButtonState.DEFAULT) != null)
            || (ph.getBackgroundPainter(ButtonState.DEFAULT) != null))) {
      if (ph.isShared()) {
        ph            = (PainterHolder) ph.clone();
        painterHolder = ph;
      }

      if (ph.normalPainter != null) {
        ph.normalPainter = (PaintBucket) ph.normalPainter.clone();
        ph.normalPainter.setBackgroundColor(bg);
        ph.normalPainter.setBackgroundPainter(null);
      }

      backgroundPainter = null;
    }
  }

  @Override
  public void clear() {
    setBackgroundColor(null);
    setBackgroundOverlayPainter(null);
    setBackgroundPainter(null, false);
    setBorder(null);
    setSharedBorder(null);
    setPainterHolder(null);
  }

  @Override
  public Object clone() {
    aUIComponentPainter cp = (aUIComponentPainter) super.clone();

    cp.changeListener = null;
    cp.changeSupport  = null;

    if (cp.backgroundPainter != null) {
      cp.backgroundPainter = (iBackgroundPainter) ((aUIPainter) cp.backgroundPainter).clone();
      cp.backgroundPainter.reference();
    }

    if (cp.bgOverlayPainter != null) {
      cp.bgOverlayPainter = (iPlatformPainter) ((aUIPainter) cp.bgOverlayPainter).clone();
      cp.bgOverlayPainter.reference();
    }

    if (cp.overlayPainter != null) {
      cp.overlayPainter = (iPlatformPainter) ((aUIPainter) cp.overlayPainter).clone();
      cp.overlayPainter.reference();
    }

    return cp;
  }

  @Override
  public void copyFrom(iPlatformComponentPainter cp) {
    setBackgroundColor(cp.getBackgroundColor());
    setBackgroundOverlayPainter(cp.getBackgroundOverlayPainter());
    setBackgroundPainter(cp.getBackgroundPainter(), false);
    setBorder(cp.getBorder());
    setSharedBorder(cp.getSharedBorder());
    setPainterHolder(cp.getPainterHolder());
  }

  @Override
  public void imageLoaded(UIImage image) {
    if (overlayPainter instanceof UIImagePainter) {
      firePropertyChange(PROPERTY_OVERLAY_PAINTER, null, overlayPainter);
    }

    if (backgroundPainter instanceof UIImagePainter) {
      firePropertyChange(PROPERTY_BACKGROUND_PAINTER, null, backgroundPainter);
    }

    if (bgOverlayPainter instanceof UIImagePainter) {
      firePropertyChange(PROPERTY_BACKGROUND_OVERLAY_PAINTER, null, bgOverlayPainter);
    }
  }

  @Override
  public void paint(iPlatformGraphics g, float x, float y, float width, float height, int orientation) {
    try {
      paintAll = true;
      paint(g, x, y, width, height, orientation, false);
      paint(g, x, y, width, height, orientation, true);
    } finally {
      paintAll = false;
    }
  }

  public static void paint(iPlatformGraphics g, float x, float y, float width, float height, PaintBucket pb) {
    iPlatformBorder    b       = pb.border;
    UIColor            oc      = g.getColor();
    iPlatformPaint     op      = g.getPaint();
    iBackgroundPainter bp      = pb.backgroundPainter;
    iPlatformPainter   bgop    = pb.imagePainter;
    UIColor            bg      = pb.backgroundColor;
    iPlatformPaint     p       = null;
    iPlatformShape     shape   = null;
    boolean            clips   = false;
    float              ox      = x;
    float              oy      = y;
    float              owidth  = width;
    float              oheight = height;

    if (b != null) {
      b.paint(g, x, y, width, height, false);

      if (b.clipsContents()) {
        if (bgop == null) {
          p = (bp == null)
              ? bg
              : bp.getPaint(width, height);
        } else if (bp == null) {
          p = (bgop == null)
              ? bg
              : bgop.getPaint(width, height);
        }

        if (p != null) {
          shape = b.getShape(g, 0, 0, width, height);

          if (shape == null) {
            p = null;
          }
        }

        if (p != null) {
          bg   = null;
          bp   = null;
          bgop = null;
        }

        clips = p == null;
      } else {
        UIInsets in = b.getBorderInsetsEx(null);

        x      += in.left;
        y      += in.top;
        width  -= (in.left + in.right);
        height -= (in.top + in.bottom);
      }
    }

    if (shape != null) {
      g.setPaint(p);
      g.fillShape(shape, x, y);
    }

    if (clips && (((bg != null) && (bp != null)) || (bgop != null))) {
      g.saveState();
      b.clip(g, x, y, width, height);
      g.setComponentPainterClipped(true);
    }

    if ((bg != null) && (bp == null)) {
      g.setColor(bg);
      g.fillRect(x, y, width, height);
    }

    if (bp != null) {
      bp.paint(g, x, y, width, height, iPainter.UNKNOWN);
    }

    if (bgop != null) {
      bgop.paint(g, x, y, width, height, iPainter.UNKNOWN);
    }

    if (b != null) {
      if (g.didComponentPainterClip()) {
        g.restoreState();
      }

      b.paint(g, ox, oy, owidth, oheight, true);
    }

    g.setColor(oc);
    g.setPaint(op);
  }

  @Override
  public void paint(iPlatformGraphics g, float x, float y, float width, float height, int orientation, boolean end) {
    iPlatformComponent         pc        = g.getComponent();
    iPlatformBorder            b         = border;
    boolean                    hasValue  = true;
    float                      ox        = x;
    float                      oy        = y;
    float                      owidth    = width;
    float                      oheight   = height;
    final boolean              selected  = (pc == null)
            ? false
            : pc.isSelected();
    final boolean              pressed   = (pc == null)
            ? false
            : pc.isPressed();
    final boolean              enabled   = (pc == null)
            ? true
            : pc.isEnabled();
    final boolean              mouseOver = (pc == null)
            ? false
            : pc.isMouseOver();
    iPaintedButton.ButtonState state     = null;
    PainterHolder              ph        = painterHolder;

    if (ph != null) {
      state = Utils.getState(enabled, pressed, selected, mouseOver);

      iPlatformBorder sb = ph.getBorder(state);

      if (sb != null) {
        b = sb;
      }
    }

    if ((b != null) &&!paintAll &&!UIComponentPainter.isOkToPaint(pc, b)) {    // invoke
      // from
      // subclass
      // so
      // that
      // the
      // subclass
      // can
      // override
      b = null;
    }

    if (sharedBorder != null) {
      b = sharedBorder;
    }

    UIColor        oc = g.getColor();
    iPlatformPaint op = g.getPaint();

    if (!end) {
      iBackgroundPainter bp    = backgroundPainter;
      iPlatformPainter   bgop  = bgOverlayPainter;
      UIColor            bg    = null;
      iPlatformPaint     p     = null;
      iPlatformShape     shape = null;
      boolean            clips = false;

      if (ph != null) {
        bp   = ph.getBackgroundPainter(state);
        bg   = ph.getBackground(state);
        bgop = ph.getBackgroundOverlayPainter(state);

        if (bp == null) {
          bp = backgroundPainter;
        }

        if (bgop == null) {
          bgop = bgOverlayPainter;
        }
      }

      if (b != null) {
        if (insets == null) {
          insets = new UIInsets();
        }

        b.paint(g, x, y, width, height, end);

        if (b instanceof SharedLineBorder) {
          UIColor c = ((SharedLineBorder) b).getBackgroundColor();

          if (c != null) {
            bg   = c;
            bp   = null;
            bgop = null;
          }
        }

        if (b.clipsContents()) {
          if (bgop == null) {
            p = (bp == null)
                ? bg
                : bp.getPaint(width, height);
          } else if (bp == null) {
            p = (bgop == null)
                ? bg
                : bgop.getPaint(width, height);
          }

          if (p != null) {
            shape = b.getShape(g, 0, 0, width, height);

            if (shape == null) {
              p = null;
            }
          }

          if (p != null) {
            bg   = null;
            bp   = null;
            bgop = null;
          }

          clips = (p == null) || ((pc == null)
                                  ? false
                                  : pc.hasChildren());
        } else {
          UIInsets in;

          if (b == sharedBorder) {
            in = sharedBorder.getBorderInsetsEx(pc, insets);
          } else {
            in = b.getBorderInsetsEx(insets);
          }

          x      += in.left;
          y      += in.top;
          width  -= (in.left + in.right);
          height -= (in.top + in.bottom);
        }
      } else if (parentShape != null) {
        g.saveState();
        g.fillShape(parentShape, x, y);
        g.restoreState();
      }

      if (shape != null) {
        g.setPaint(p);
        g.fillShape(shape, x, y);
      }

      if (clips) {
        g.saveState();
        b.clip(g, x, y, width, height);
        g.setComponentPainterClipped(true);
      }

      if ((bg != null) && (bp == null)) {
        g.setColor(bg);
        g.fillRect(x, y, width, height);
      }

      if ((bp != null) && (paintAll || UIComponentPainter.isOkToPaint(pc, bp, hasValue, false))) {
        bp.paint(g, x, y, width, height, orientation);
      }

      if ((bgop != null) && (paintAll || UIComponentPainter.isOkToPaint(pc, bgop, hasValue, false))) {
        bgop.paint(g, x, y, width, height, orientation);
      }
    } else {
      if ((overlayPainter != null)
          && (paintAll || UIComponentPainter.isOkToPaint(pc, overlayPainter, hasValue, true))) {
        UIInsets in = null;

        switch(overlayPainter.getRenderSpace()) {
          case COMPONENT :
            if (g.didComponentPainterClip()) {
              g.restoreState();
              g.setComponentPainterClipped(false);
            }

            break;

          case WITHIN_MARGIN :
            in = b.getBorderInsets(insets);

            break;

          case WITHIN_BORDER :
            if ((b != null) &&!g.didComponentPainterClip()) {
              in = b.getBorderInsetsEx(insets);
            }

            break;
        }

        float xx = x;
        float yy = y;
        float w  = width;
        float h  = height;

        if (in != null) {
          xx += in.left;
          yy += in.top;
          w  -= (in.left + in.right);
          h  -= (in.top + in.bottom);
        }

        overlayPainter.paint(g, xx, yy, w, h, orientation);
      }

      if (b != null) {
        if (g.didComponentPainterClip()) {
          g.restoreState();
        }

        b.paint(g, x, y, width, height, end);
      }

      if (!paintAll && paintFocusDefault && (pc != null && pc.isFocusPainted())) {
        if(b==null || !b.isFocusAware()) {
          PaintBucket pb = pc.getFocusPaint(focusPaint);
          b=pb==null ? null : pb.getBorder();
          if (b!=null) {
            UIInsets in = pc.getFocusInsets(insets);
  
            if (in != null) {
              ox      += in.left;
              oy      += in.top;
              owidth  -= (in.left + in.right);
              oheight -= (in.top + in.bottom);
            }
            b.paint(g, ox, oy, owidth, oheight, b.isPaintLast());
          }
        }
      }
    }

    g.setColor(oc);
    g.setPaint(op);
  }

  public static void paintPainter(iPlatformPainter p, iPlatformGraphics g, float width, float height) {
    iPlatformComponent c = g.getComponent();

    if (!UIComponentPainter.isOkToPaint(c, p)) {
      return;
    }

    iPlatformBorder border = (c == null)
                             ? null
                             : c.getBorder();
    float           x      = 0;
    float           y      = 0;
    UIInsets        in     = null;

    switch(p.getRenderSpace()) {
      case WITHIN_BORDER :
        if (border != null) {
          in = border.getBorderInsetsEx(null);
        }

        break;

      case WITHIN_MARGIN :
        if (border != null) {
          in = border.getBorderInsets((UIInsets) null);
        }

        break;

      default :
        break;
    }

    if (in != null) {
      x      -= in.left;
      y      -= in.top;
      width  += (in.left + in.right);
      height += (in.top + in.bottom);
    }

    p.paint(g, x, y, width, height, iPainter.UNKNOWN);
  }

  @Override
  public void removePropertyChangeListener(PropertyChangeListener listener) {
    if (listener == changeListener) {
      changeListener = null;
    }

    if ((listener != null) && (changeSupport != null)) {
      changeSupport.removePropertyChangeListener(listener);
    }
  }

  @Override
  public void setBackgroundColor(UIColor bg) {
    setBackgroundColor(bg, false);
  }

  public static void setBackgroundColor(iPainterSupport comp, UIColor bg) {
    iPlatformComponentPainter cp = comp.getComponentPainter();

    if (cp == null) {
      cp = new UIComponentPainter();
      comp.setComponentPainter(cp);
    }

    cp.setBackgroundColor(bg);
  }

  @Override
  public void setBackgroundColor(UIColor bg, boolean checkOthers) {
    if ((bg != null) && (bg == UIColor.TRANSPARENT)) {
      backgroundPainter = null;
      painterHolder     = null;
    } else {
      if (bg instanceof UIColorShade) {
        UIColorShade cs = (UIColorShade) bg;

        if (cs.getBackgroundPainter() != null) {
          setBackgroundPainter(cs.getBackgroundPainter(), checkOthers);

          return;
        }

        if (cs.getPainterHolder() != null) {
          setPainterHolder(cs.getPainterHolder());

          return;
        }

        if (!cs.isSimpleColor()) {
          handleColorShade(cs);
        }
      }

      setBackgroundPainter((bg == null)
                           ? null
                           : new UISimpleBackgroundPainter(bg), checkOthers);
    }
  }

  @Override
  public void setBackgroundOverlayPainter(iPlatformPainter painter) {
    if (bgOverlayPainter != painter) {
      if (painter != null) {
        painter.reference();
      }

      iPlatformPainter ov = bgOverlayPainter;

      bgOverlayPainter = painter;
      firePropertyChange(PROPERTY_BACKGROUND_OVERLAY_PAINTER, ov, painter);

      if (ov != null) {
        ov.dispose();
      }

      imagePainterCheckedAndOkToAdd(painter);
    }
  }

  public static void setBackgroundOverlayPainter(iPainterSupport comp, iPlatformPainter painter) {
    iPlatformComponentPainter cp = comp.getComponentPainter();

    if (cp == null) {
      cp = new UIComponentPainter();
      comp.setComponentPainter(cp);
    }

    cp.setBackgroundOverlayPainter(painter);
  }

  @Override
  public void setBackgroundPainter(iBackgroundPainter painter, boolean checkOthers) {
    if (backgroundPainter != painter) {
      if (painter != null) {
        painter.reference();
      }

      iPlatformPainter ov = backgroundPainter;

      backgroundPainter = painter;
      firePropertyChange(PROPERTY_BACKGROUND_PAINTER, ov, painter);

      if (ov != null) {
        ov.dispose();
      }

      imagePainterCheckedAndOkToAdd(painter);

      if (checkOthers) {
        fixPainterholder(false);
      }
    }
  }

  public static void setBackgroundPainter(iPainterSupport comp, iBackgroundPainter painter) {
    iPlatformComponentPainter cp = comp.getComponentPainter();

    if (cp == null) {
      cp = new UIComponentPainter();
      comp.setComponentPainter(cp);
    }

    cp.setBackgroundPainter(painter, false);
  }

  public static void setBorder(iPainterSupport comp, iPlatformBorder border) {
    iPlatformComponentPainter cp = comp.getComponentPainter();

    if (cp == null) {
      cp = new UIComponentPainter();
      comp.setComponentPainter(cp);
    }

    cp.setBorder(border);
  }

  /**
   * @param b
   *          the b to set
   */
  @Override
  public void setBorder(iPlatformBorder b) {
    if ((border != b) || ((b != null) && (b.getModCount() != borderModCount))) {
      Object ov = border;

      this.border    = b;
      borderModCount = (b == null)
                       ? 0
                       : b.getModCount();
      fixPainterholder(true);
      firePropertyChange(PROPERTY_BORDER, ov, b);
    }
  }

  /**
   * @param painter
   *          the overlayPainter to set
   */
  @Override
  public void setOverlayPainter(iPlatformPainter painter) {
    if (overlayPainter != painter) {
      if (painter != null) {
        painter.reference();
      }

      iPlatformPainter ov = overlayPainter;

      overlayPainter = painter;
      firePropertyChange(PROPERTY_OVERLAY_PAINTER, ov, painter);

      if (ov != null) {
        ov.dispose();
      }

      imagePainterCheckedAndOkToAdd(painter);
    }
  }

  @Override
  public void setPainterHolder(PainterHolder ph) {
    if (ph != painterHolder) {
      PainterHolder oph = painterHolder;

      this.painterHolder = ph;
      firePropertyChange(PROPERTY_PAINTER_HOLDER, oph, ph);
    }
  }

  public void setParentShape(iPlatformShape parentShape) {
    this.parentShape = parentShape;
  }

  @Override
  public void setSharedBorder(SharedLineBorder sharedBorder) {
    this.sharedBorder = sharedBorder;
  }

  @Override
  public UIColor getBackgroundColor() {
    if (backgroundPainter != null) {
      return backgroundPainter.getBackgroundColor();
    }

    return null;
  }

  @Override
  public iPlatformPainter getBackgroundOverlayPainter() {
    return bgOverlayPainter;
  }

  @Override
  public iBackgroundPainter getBackgroundPainter() {
    return backgroundPainter;
  }

  /**
   * @return the b
   */
  @Override
  public iPlatformBorder getBorder() {
    return border;
  }

  /**
   * @return the b
   */
  @Override
  public iPlatformBorder getBorderAlways() {
    iPlatformBorder b = (painterHolder != null)
                        ? painterHolder.getBorder(ButtonState.DEFAULT)
                        : null;

    return (b == null)
           ? border
           : b;
  }


  @Override
  public UIColor getForegroundColor() {
    return painterHolder==null ? null : painterHolder.foregroundColor;
  }
  @Override
  public iPlatformPainter getOverlayPainter() {
    return overlayPainter;
  }

  @Override
  public iPlatformPaint getPaint(float width, float height) {
    if (border != null) {
      return null;
    }

    if (bgOverlayPainter == null) {
      return (backgroundPainter == null)
             ? null
             : backgroundPainter.getPaint(width, height);
    } else if (backgroundPainter == null) {
      return (bgOverlayPainter == null)
             ? null
             : bgOverlayPainter.getPaint(width, height);
    }

    return null;
  }

  @Override
  public PainterHolder getPainterHolder() {
    return painterHolder;
  }

  public iPlatformShape getParentShape() {
    return parentShape;
  }

  @Override
  public SharedLineBorder getSharedBorder() {
    return sharedBorder;
  }

  @Override
  public boolean isBackgroundPaintEnabled() {
    return (backgroundPainter != null) || (bgOverlayPainter != null);
  }

  public static boolean isOkToPaint(iPlatformComponent c, iPlatformBorder border) {
    return true;
  }

  public static boolean isOkToPaint(iPlatformComponent c, iPlatformPainter p) {
    return isOkToPaint(c, p, true, false);
  }

  public static boolean isOkToPaint(iPlatformComponent c, iPlatformPainter p, boolean hasValue, boolean isOverlay) {
    Displayed displayed = p.getDisplayed();

    if ((c == null) || (displayed == null)) {
      return true;
    }

    switch(displayed) {
      case ALWAYS :
        return true;

      case BEFORE_FIRST_FOCUS :
        return c.hasBeenFocused();

      case BEFORE_INTERACTION :
        return !c.hasHadInteraction();

      case WHEN_EMPTY :
        return !hasValue;

      case WHEN_FOCUSED :
        return c.isFocusOwner();

      case WHEN_NOT_FOCUSED :
        return !c.isFocusOwner();

      case WHEN_WIDGET_FOCUSED :
        return isWidgetFocused(Platform.findWidgetForComponent(c));

      case WHEN_WIDGET_NOT_FOCUSED :
        return !isWidgetFocused(Platform.findWidgetForComponent(c));

      case WHEN_PARENT_WIDGET_FOCUSED :
        return isParentWidgetFocused(Platform.findWidgetForComponent(c));

      case WHEN_PARENT_WIDGET_NOT_FOCUSED :
        return !isParentWidgetFocused(Platform.findWidgetForComponent(c));

      case WHEN_CHILD_WIDGET_FOCUSED :
        return isChildOf(c, Platform.getAppContext().getPermanentFocusOwner());

      case WHEN_CHILD_WIDGET_NOT_FOCUSED :
        return !isChildOf(c, Platform.getAppContext().getPermanentFocusOwner());

      case WHEN_WINDOW_FOCUSED :
        return isWindowFocused(Platform.getWindowViewer(c));

      case WHEN_WINDOW_NOT_FOCUSED :
        return !isWindowFocused(Platform.getWindowViewer(c));

      default :
        return true;
    }
  }

  @Override
  public boolean isSingleColorPainter() {
    if (overlayPainter != null) {
      return false;
    }

    if ((bgOverlayPainter != null) && (backgroundPainter != null)) {
      return false;
    }

    if (backgroundPainter != null) {
      if (!backgroundPainter.isSingleColorPainter()) {
        return false;
      }
    }

    if (bgOverlayPainter != null) {
      return false;
    }

    return true;
  }

  @Override
  protected void disposeEx() {
    if (changeSupport != null) {
      changeSupport.dispose();
    }

    if (backgroundPainter != null) {
      backgroundPainter.dispose();
    }

    if (bgOverlayPainter != null) {
      bgOverlayPainter.dispose();
    }

    if (overlayPainter != null) {
      overlayPainter.dispose();
    }

    backgroundPainter = null;
    bgOverlayPainter  = null;
    border            = null;
    changeListener    = null;
    changeSupport     = null;
    overlayPainter    = null;
    painterHolder     = null;
    sharedBorder      = null;
    super.disposeEx();
  }

  protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
    modCount++;

    if (changeListener != null) {
      changeListener.propertyChange(new PropertyChangeEvent(this, propertyName, oldValue, newValue));
    }

    if (changeSupport != null) {
      changeSupport.firePropertyChange(propertyName, oldValue, newValue);
    }
  }

  protected void fixPainterholder(boolean forBorder) {
    PainterHolder ph = painterHolder;

    if (ph != null) {
      if (ph.isShared()) {
        ph            = (PainterHolder) ph.clone();
        painterHolder = ph;
      }

      if (ph.normalPainter == null) {
        ph.normalPainter = new PaintBucket();
      } else {
        ph.normalPainter = (PaintBucket) ph.normalPainter.clone();
      }

      if (forBorder) {
        ph.normalPainter.setBorder(border);
      } else {
        ph.normalPainter.setBackgroundPainter(backgroundPainter);
      }
    }
  }

  protected void handleColorShade(UIColorShade cs) {}

  protected static boolean isParentWidgetFocused(iWidget w) {
    w = (w == null)
        ? null
        : w.getParent();

    return (w == null)
           ? false
           : w.isFocusOwner();
  }

  protected static boolean isWidgetFocused(iWidget w) {
    return (w == null)
           ? false
           : w.isFocusOwner();
  }

  protected static boolean isWindowFocused(WindowViewer win) {
    return (win == null)
           ? false
           : win.isShowing();
  }

  private boolean imagePainterCheckedAndOkToAdd(iPlatformPainter p) {
    if (p instanceof UIImagePainter) {
      UIImagePainter ip = (UIImagePainter) p;

      if ((ip.getImage() != null) && ip.getImage().isLoaded(this)) {
        return true;
      }

      return false;
    }

    return true;
  }

  private static boolean isChildOf(iPlatformComponent parent, iPlatformComponent child) {
    return Platform.isDescendingFrom(child, parent);
  }
}
