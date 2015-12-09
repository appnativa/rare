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

package com.appnativa.rare.platform.apple.ui.view;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import com.appnativa.rare.Platform;
import com.appnativa.rare.platform.apple.ui.iApplePainterSupport;
import com.appnativa.rare.platform.apple.ui.util.AppleGraphics;
import com.appnativa.rare.ui.ColorUtils;
import com.appnativa.rare.ui.Component;
import com.appnativa.rare.ui.Displayed;
import com.appnativa.rare.ui.FontUtils;
import com.appnativa.rare.ui.RenderableDataItem.HorizontalAlign;
import com.appnativa.rare.ui.RenderableDataItem.IconPosition;
import com.appnativa.rare.ui.RenderableDataItem.VerticalAlign;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UICursor;
import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.UIFont;
import com.appnativa.rare.ui.UIInsets;
import com.appnativa.rare.ui.UIRectangle;
import com.appnativa.rare.ui.Utils;
import com.appnativa.rare.ui.iGestureListener;
import com.appnativa.rare.ui.iPaintedButton;
import com.appnativa.rare.ui.iPaintedButton.ButtonState;
import com.appnativa.rare.ui.iPlatformBorder;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.iPlatformIcon;
import com.appnativa.rare.ui.iPlatformPath;
import com.appnativa.rare.ui.border.SharedLineBorder;
import com.appnativa.rare.ui.border.UICompoundBorder;
import com.appnativa.rare.ui.border.UIEmptyBorder;
import com.appnativa.rare.ui.border.UILineBorder;
import com.appnativa.rare.ui.effects.aAnimator;
import com.appnativa.rare.ui.event.ChangeEvent;
import com.appnativa.rare.ui.event.KeyEvent;
import com.appnativa.rare.ui.event.MouseEvent;
import com.appnativa.rare.ui.event.iActionListener;
import com.appnativa.rare.ui.event.iChangeListener;
import com.appnativa.rare.ui.listener.iFocusListener;
import com.appnativa.rare.ui.listener.iKeyListener;
import com.appnativa.rare.ui.listener.iMouseListener;
import com.appnativa.rare.ui.listener.iMouseMotionListener;
import com.appnativa.rare.ui.listener.iViewListener;
import com.appnativa.rare.ui.painter.UIBackgroundPainter;
import com.appnativa.rare.ui.painter.UIComponentPainter;
import com.appnativa.rare.ui.painter.UIImagePainter;
import com.appnativa.rare.ui.painter.UISimpleBackgroundPainter;
import com.appnativa.rare.ui.painter.iBackgroundPainter;
import com.appnativa.rare.ui.painter.iComponentPainter;
import com.appnativa.rare.ui.painter.iPainter;
import com.appnativa.rare.ui.painter.iPlatformComponentPainter;
import com.appnativa.rare.ui.painter.iPlatformPainter;

/*-[
 #import "AppleHelper.h"
 #import "APView+Component.h"
 #import "RAREAPView.h"
 #import "RAREAPWindow.h"
 #import "RARECALayer.h"
 #import "RARECAGradientLayer.h"
 ]-*/
public abstract class aView implements iApplePainterSupport, PropertyChangeListener {
  protected float                     enabledAlpha     = -1;
  protected boolean                   enabled          = true;
  protected boolean                   usePainterBorder = true;
  protected iBackgroundPainter        backgroundPainter;
  protected iPlatformPainter          bgOverlayPainter;
  protected iPlatformBorder           border;
  protected iPlatformPath             borderPath;
  protected ChangeEvent               changeEvent;
  protected iPlatformPath             clip;
  protected Component                 component;
  protected iPlatformComponentPainter componentPainter;
  protected boolean                   enabledInteraction;
  protected iFocusListener            focusListener;
  protected UIFont                    font;
  protected UIColor                   foregroundColor;
  protected iKeyListener              keyListener;
  protected boolean                   layerForBackgroundOverlay;
  protected boolean                   layerForOverlay;
  protected int                       modCountBackgroundPainter;
  protected int                       modCountBgOverlayPainter;
  protected int                       modCountBorder;
  protected int                       modCountOverlayPainter;
  protected boolean                   mouseGestureListenerAdded;
  protected iMouseListener            mouseListener;
  protected iMouseMotionListener      mouseMotionListener;
  protected boolean                   mouseOver;
  private float                       oldHeight;
  private float                       oldWidth;
  protected Object                    overlayLayer;
  protected iPlatformPainter          overlayPainter;
  protected boolean                   pressed;
  protected Object                    proxy;
  protected int                       rotation;
  protected boolean                   transparent;
  protected boolean                   usePainterBackgroundColor;
  protected iViewListener             viewListener;
  private boolean                     useMainLayerForPainter = true;

  public aView(Object nsview) {
    setProxy(nsview);
  }

  protected aView() {}

  public void borderChanged(iPlatformBorder newBorder) {
    if (newBorder != null) {
      removeNativeBorder();
    } else {
      restoreNativeBorder();
    }
  }

  /**
   * Removes the view visual attributes. This is meant to be called when a view
   * is being reused (e.g. as a renderer) This method only clears the state
   * information. Actual resetting of the native should be done in the native
   * environment before the view is reused.
   */
  public void clearVisualState() {
    foregroundColor           = null;
    font                      = null;
    componentPainter          = null;
    overlayPainter            = null;
    backgroundPainter         = null;
    bgOverlayPainter          = null;
    border                    = null;
    modCountBackgroundPainter = 0;
    modCountBgOverlayPainter  = 0;
    modCountBorder            = 0;
    modCountOverlayPainter    = 0;
    layerForBackgroundOverlay = false;
    layerForOverlay           = false;
    oldHeight                 = -1;
    oldWidth                  = -1;
    setEnabled(true);
    resetForRenderer();
  }

  public static native Object createAPView()
  /*-[
    return [[RAREAPView alloc]init];
  ]-*/
  ;

  public void dispatchEvent(KeyEvent me) {}

  public void dispatchEvent(MouseEvent me) {}

  public void dispose() {
    if (proxy != null) {
      try {
        if (componentPainter != null) {
          componentPainter.removePropertyChangeListener(this);
          componentPainter.dispose();
        }
      } catch(Throwable e) {
        Platform.ignoreException(null, e);
      }

      if (overlayLayer != null) {
        disposeLayer(overlayLayer);
      }

      try {
        disposeEx();
      } catch(Throwable e) {
        Platform.ignoreException(null, e);
      }

      componentPainter    = null;
      component           = null;
      focusListener       = null;
      mouseListener       = null;
      mouseMotionListener = null;
      keyListener         = null;
      viewListener        = null;
      proxy               = null;
      clip                = null;
      borderPath          = null;
      border              = null;
      overlayPainter      = null;
      backgroundPainter   = null;
      changeEvent         = null;
      overlayLayer        = null;
    }
  }

  public boolean letComponentPainterPaint(iPlatformBorder b) {
    if (border != b) {
      if ((componentPainter == null) || (componentPainter.getBorder() != b)) {
        return true;
      }
    }

    return usePainterBorder;
  }

  public boolean letComponentPainterPaint(iPlatformPainter p) {
    if ((backgroundPainter != p) && (bgOverlayPainter != p) && (overlayPainter != p)) {
      return true;
    }

    if ((p == overlayPainter) &&!layerForOverlay) {
      return true;
    }

    if ((p == bgOverlayPainter) &&!layerForBackgroundOverlay) {
      return true;
    }

    if (p.canUseLayer() && isUseMainLayerForPainter()) {
      return false;
    }

    return !transparent;
  }

  public void makeOrphan() {
  }

  public native void makeTransparent()
  /*-[
    transparent_=YES;
    CALayer* layer=(CALayer*)[self getLayer];
    layer.backgroundColor=[UIColor clearColor].CGColor;
  ]-*/
  ;

  public native boolean needsLayout()
  /*-[
    CALayer* layer=(CALayer*)[self getLayer];
    return [layer needsLayout];
  ]-*/
  ;

  @Override
  public void paintBackground(AppleGraphics g, View v, UIRectangle rect) {
    if (componentPainter != null) {
      componentPainter.paint(g, rect.x, rect.y, rect.width, rect.height, iPainter.UNKNOWN, false);
    }
  }

  @Override
  public void paintOverlay(AppleGraphics g, View v, UIRectangle rect) {
    if (componentPainter != null) {
      componentPainter.paint(g, rect.x, rect.y, rect.width, rect.height, iPainter.UNKNOWN, true);
    }
  }

  public void paintPainter(iPlatformPainter p, AppleGraphics g, View v, float width, float height) {
    UIComponentPainter.paintPainter(p, g, width, height);
  }

  @Override
  public void propertyChange(PropertyChangeEvent e) {
    if (e.getSource() == componentPainter) {
      if (e.getPropertyName() == iComponentPainter.PROPERTY_BACKGROUND_PAINTER) {
        setBackgroundPainter(componentPainter.getBackgroundPainter());
      } else if (e.getPropertyName() == iComponentPainter.PROPERTY_BORDER) {
        if (isUsePainterBorder()) {
          setBorder(componentPainter.getBorder());
        }
      } else if (e.getPropertyName() == iComponentPainter.PROPERTY_BACKGROUND_OVERLAY_PAINTER) {
        setBackgroundOverlayPainter(componentPainter.getBackgroundOverlayPainter());
      } else if (e.getPropertyName() == iComponentPainter.PROPERTY_OVERLAY_PAINTER) {
        setOverlayPainter(componentPainter.getOverlayPainter());
      }

      stateChanged();
    }
  }

  public native void removeNativeBorder()
  /*-[
    CALayer* layer=(CALayer*)[self getLayer];
    if([layer isKindOfClass:[RARECAGradientLayer class]]) {
      RARECAGradientLayer* gl=(RARECAGradientLayer*)layer;
      [gl removeNativeBorder];
    }
    else {
      layer.borderWidth=0;
      layer.borderColor=NULL;
    }
  ]-*/
  ;

  public abstract void repaint();

  public native void resetForRenderer()
  /*-[
    CALayer* layer=(CALayer*)[self getLayer];
    if([layer isKindOfClass:[RARECAGradientLayer class]]) {
      RARECAGradientLayer* gl=(RARECAGradientLayer*)layer;
      [gl sparResetLayerForRendererWithView: self];
    }
  ]-*/
  ;

  public native void resetLayer()
  /*-[
    CALayer* layer=(CALayer*)[self getLayer];
    if([layer isKindOfClass:[RARECAGradientLayer class]]) {
      RARECAGradientLayer* gl=(RARECAGradientLayer*)layer;
      [gl sparResetLayer];
    }
  ]-*/
  ;

  public void restoreNativeBorder() {
    removeNativeBorder();
  }

  public abstract void revalidate();

  public native void set3DTransform(Object tx)
  /*-[
    CALayer* layer=(CALayer*)[self getLayer];
    CATransform3D value;
    [tx getValue:&value];
    layer.transform= value;
  ]-*/
  ;

  public void stateChanged() {
    if (componentPainter != null) {
      componentPainter.updateForState(this);
    }

    checkForegroundColor();
  }

  public boolean usedLayerForBackgroundOverlay() {
    return layerForBackgroundOverlay;
  }

  public boolean usedLayerForOverlay() {
    return layerForOverlay;
  }

  public void setActionListener(iActionListener l) {}

  protected void setBackgroundColor(UIColor bg) {
    if (componentPainter == null) {
      setComponentPainter(new UIComponentPainter());
    }

    componentPainter.setBackgroundPainter((bg == null)
            ? null
            : new UISimpleBackgroundPainter(bg), false);
  }

  public native boolean setBackgroundColorEx(UIColor bg)
  /*-[
    CALayer* layer=(CALayer*)[self getLayer];
    layer.backgroundColor=(__bridge CGColorRef)(bg ? [bg getCGColor] : NULL);
    return YES;
  ]-*/
  ;

  public void setBackgroundOverlayPainter(iPlatformPainter p) {
    if ((p != bgOverlayPainter) || ((p != null) && (p.getModCount() != modCountBgOverlayPainter))) {
      setBackgroundOverlayPainterEx(null);

      if ((p instanceof UIImagePainter) && p.canUseLayer()) {
        UIImagePainter ip = (UIImagePainter) p;

        if (ip.getScalingType().isCached()
            || Platform.getUIDefaults().getBoolean("Rare.ImagePainter.useLayer", false)) {
          layerForBackgroundOverlay = true;    // set here so component painter
          // wont try to paint

          if (imagePainterCheckedAndOkToAdd(p)) {
            setBackgroundOverlayPainterEx(ip);
          }
        } else {
          setPaintHandlerEnabled(true);
        }
      } else {
        if (p != null) {
          setPaintHandlerEnabled(true);
        }
      }

      modCountBgOverlayPainter = (p == null)
                                 ? 0
                                 : p.getModCount();
      bgOverlayPainter         = p;

      if ((p != null) && (p.getDisplayed() == Displayed.BEFORE_INTERACTION)) {
        handleWantsFirstInteraction();
      }
    }
  }

  public void setBackgroundPainter(iBackgroundPainter bp) {
    if ((bp != backgroundPainter) || ((bp != null) && (bp.getModCount() != modCountBackgroundPainter))) {
      setBackgroundPainterEx(null);

      if (bp == null) {
        setBackgroundColorEx(null);
      } else {
        if (isUseMainLayerForPainter()) {
          if ((bp != null) && bp.canUseMainLayer()) {
            if (bp.isSingleColorPainter()) {
              setBackgroundColorEx(bp.getBackgroundColor());
            }

            if (bp instanceof UIBackgroundPainter) {
              setBackgroundPainterEx(bp);
            } else if ((bp instanceof UIImagePainter) && ((UIImagePainter) bp).canUseLayer()) {
              if (imagePainterCheckedAndOkToAdd(bp)) {
                setBackgroundPainterEx(bp);
              }
            } else {
              if (bp != null) {
                imagePainterCheckedAndOkToAdd(bp);
                setPaintHandlerEnabled(true);
              }
            }
          } else {
            if (bp != null) {
              imagePainterCheckedAndOkToAdd(bp);
              setPaintHandlerEnabled(true);
            }
          }
        } else {
          setPaintHandlerEnabled(true);
        }
      }

      modCountBackgroundPainter = (bp == null)
                                  ? 0
                                  : bp.getModCount();
      backgroundPainter         = bp;

      if ((bp != null) && (bp.getDisplayed() == Displayed.BEFORE_INTERACTION)) {
        handleWantsFirstInteraction();
      }
    }
  }

  public void setBorder(iPlatformBorder b) {
    if ((b != border) || ((b != null) && (b.getModCount() != modCountBorder))) {
      modCountBorder = 0;
      borderChanged(b);
      handleBorder(b);
    }

    border = b;
  }

  public void setBorderEx(iPlatformBorder b) {
    border         = b;
    modCountBorder = 0;
  }

  public abstract void setBounds(float x, float y, float w, float h);

  public void setBounds(int x, int y, int w, int h) {
    setBounds((float) x, (float) y, (float) w, (float) h);
  }

  public void setChangeListener(iChangeListener l) {}

  public abstract void setClipMask(Object nativepath);

  public abstract void setComponent(Component component);

  public void setComponentPainter(iPlatformComponentPainter cp) {
    if (cp == componentPainter) {
      return;
    }

    if (cp != null) {
      cp.reference();
      cp.addPropertyChangeListener(this);
    }

    if (this.componentPainter != null) {
      componentPainter.removePropertyChangeListener(this);
      componentPainter.dispose();
    }

    this.componentPainter = cp;

    if (isUsePainterBorder()) {
      setBorder((cp == null)
                ? null
                : cp.getBorder());
    }

    setBackgroundPainter((cp == null)
                         ? null
                         : cp.getBackgroundPainter());

    if ((cp != null) && (cp.getBackgroundOverlayPainter() != null)) {
      if (imagePainterCheckedAndOkToAdd(cp.getBackgroundOverlayPainter())) {}

      setPaintHandlerEnabled(true);
    }

    setOverlayPainter((cp == null)
                      ? null
                      : cp.getOverlayPainter());

    if ((cp != null) && cp.isBackgroundPaintEnabled()) {
      setPaintHandlerEnabled(true);
    }

    stateChanged();
  }

  public void setComponentPainterEx(iPlatformComponentPainter cp) {
    componentPainter = cp;
  }

  public void setCursor(UICursor cursor) {}

  public void setDisabledIcon(iPlatformIcon disabledIcon) {}

  public void setEnabled(boolean enabled) {
    if (this.enabled != enabled) {
      setEnabledEx(enabled);
      this.enabled = enabled;
      stateChanged();
    }
  }

  public void setFlingGestureListener(iGestureListener component) {}

  public void setFocusListener(iFocusListener listener) {
    focusListener = listener;
    setFocusListenerEnabled(listener != null);
  }

  public void setFocusable(boolean focusable) {}

  public void setFont(UIFont f) {
    font = f;
  }

  public void setForegroundColor(UIColor fg) {
    if (fg != foregroundColor) {
      foregroundColor = fg;
      checkForegroundColor();
    }
  }

  public void setIcon(iPlatformIcon icon) {}

  public void setIconGap(int gap) {}

  public void setIconPosition(IconPosition iconPosition) {}

  public void setKeyboardListener(iKeyListener handler) {
    keyListener = handler;
    setKeyBoardListenerEnabled(handler != null);
  }

  public void setLongPressGestureListener(iGestureListener component) {}

  public void setMargin(UIInsets insets) {
    if (insets == null) {
      setMargin(0, 0, 0, 0);
    } else {
      setMargin(insets.top, insets.right, insets.bottom, insets.left);
    }
  }

  public void setMargin(float top, float right, float bottom, float left) {}

  public void setMouseListener(iMouseListener handler) {
    mouseListener = handler;

    if ((handler != null) &&!mouseGestureListenerAdded) {
      addMouseGestureListener();
      mouseGestureListenerAdded = true;
    }
  }

  public void setMouseMotionListener(iMouseMotionListener handler) {
    mouseMotionListener = handler;

    if ((handler != null) &&!mouseGestureListenerAdded) {
      addMouseGestureListener();
      mouseGestureListenerAdded = true;
    }
  }

  public native boolean setOverlayColorEx(UIColor bg)
  /*-[
    CALayer* layer=(CALayer*)[self getOverlayLayer];
    layer.backgroundColor=(__bridge CGColorRef)(bg ? [bg getCGColor] : NULL);
    return YES;
  ]-*/
  ;

  public void setOverlayPainter(iPlatformPainter p) {
    if ((p != overlayPainter) || ((p != null) && (p.getModCount() != modCountOverlayPainter))) {
      if (imagePainterCheckedAndOkToAdd(p)) {
        getOverlayLayer();    // force overlay layer to be created
      }

      modCountOverlayPainter = (p == null)
                               ? 0
                               : p.getModCount();
      overlayPainter         = p;

      if ((p != null) && (p.getDisplayed() == Displayed.BEFORE_INTERACTION)) {
        handleWantsFirstInteraction();
      }
    }
  }

  public abstract void setPaintHandlerEnabled(boolean enabled);

  public void setPressedIcon(iPlatformIcon pressedIcon) {}

  public abstract void setProxy(Object proxy);

  public void setRotateGestureListener(iGestureListener l) {}

  public void setScaleGestureListener(iGestureListener component) {}

  public void setSelected(boolean selected) {}

  public void setSelectedIcon(iPlatformIcon selectedIcon) {}

  public void setText(CharSequence string) {}

  public void setTextAlignment(HorizontalAlign hal, VerticalAlign val) {}

  public void setUseMainLayerForPainter(boolean useMainLayerForPainter) {
    this.useMainLayerForPainter = useMainLayerForPainter;
  }
  public void updateForStateChange(iPlatformBorder border) {
    handleBorder(border);
  }
  
  public boolean usesForegroundColor() {
   return false;
  }
  public void setUsePainterBackgroundColor(boolean usePainterBackgroundColor) {
    this.usePainterBackgroundColor = usePainterBackgroundColor;
  }

  public void setUsePainterBorder(boolean usePainterBorder) {
    this.usePainterBorder = usePainterBorder;
  }

  public void setViewListener(iViewListener l) {
    viewListener = l;
  }

  public void setVisible(boolean visible) {
    if (visible != isVisible()) {
      setVisibleEx(visible);

      if (visible && (component != null)) {
        component.revalidate();
      }
    }
  }

  public void setWantsMouseMovedEvents(boolean b) {}

  public void setWordWrap(boolean wrap) {}

  public UIColor getBackgroundColor() {
    return (componentPainter == null)
           ? null
           : componentPainter.getBackgroundColor();
  }

  public UIColor getBackgroundColorAlways() {
    UIColor c = (componentPainter == null)
                ? null
                : componentPainter.getBackgroundColor();

    if (c != null) {
      return c;
    }
    View p=getParent();
    if (p == null) {
      return ColorUtils.getBackground();
    }

    return p.getBackgroundColorAlways();
  }

  public iPlatformBorder getBorder() {
    iPlatformBorder b = null;

    if ((componentPainter != null) && usePainterBorder) {
      b = componentPainter.getBorderAlways();
    }

    return (b == null)
           ? border
           : b;
  }

  public UIRectangle getBounds() {
    return getBounds(null);
  }

  public abstract UIRectangle getBounds(UIRectangle rect);

  public iPaintedButton.ButtonState getButtonState() {
    return Utils.getState(isEnabled(), isPressed(), isSelected(), false);
  }

  public iPlatformComponent getComponent() {
    return component;
  }

  public iPlatformComponentPainter getComponentPainter() {
    return componentPainter;
  }

  public iPlatformIcon getDisabledIcon() {
    return null;
  }

  public iFocusListener getFocusListener() {
    return focusListener;
  }

  public UIFont getFont() {
    return font;
  }

  public UIFont getFontAlways() {
    if (font != null) {
      return font;
    }
    View p=getParent();
    if (p == null) {
      return FontUtils.getDefaultFont();
    }

    return p.getFontAlways();
  }

  public UIColor getForegroundColor() {
    if (foregroundColor != null) {
      return foregroundColor;
    }

    if (componentPainter != null) {
      return componentPainter.getForegroundColor();
    }

    return null;
  }

  public UIColor getForegroundColorAlways() {
    UIColor fg = getForegroundColor();

    if (fg != null) {
      return fg;
    }
    View p=getParent();

    if (p == null) {
      return ColorUtils.getForeground();
    }

    return p.getForegroundColorAlways();
  }

  public int getIconGap() {
    return 0;
  }

  public iKeyListener getKeyListener() {
    return keyListener;
  }

  public UIInsets getMargin() {
    return null;
  }

  public void getMinimumSize(UIDimension size, float maxWidth) {
    size.width  = 0;
    size.height = 0;
  }

  public iMouseListener getMouseListener() {
    return mouseListener;
  }

  public iMouseMotionListener getMouseMotionListener() {
    return mouseMotionListener;
  }

  public float getPreferredHeight(int width) {
    UIDimension d = new UIDimension();

    getPreferredSize(d, width);

    return d.height;
  }
  public abstract View getParent();
  
  public abstract void getPreferredSize(UIDimension size, float maxWidth);

  public iPlatformIcon getPressedIcon() {
    return null;
  }

  public Object getProxy() {
    return proxy;
  }

  public int getRotation() {
    return rotation;
  }

  public iPlatformIcon getSelectedIcon() {
    return null;
  }

  public UIDimension getSize() {
    UIDimension size = new UIDimension();

    getSize(size);

    return size;
  }

  public abstract void getSize(UIDimension size);

  public CharSequence getText() {
    return null;
  }

  public boolean isAnimating() {
    return (component == null)
           ? false
           : aAnimator.isAnimating(component);
  }

  public boolean isEnabled() {
    return enabled;
  }

  public boolean isMouseOver() {
    return mouseOver;
  }

  public boolean isMouseTransparent() {
    return false;
  }

  public boolean isPressed() {
    return pressed;
  }

  public boolean isScrollView() {
    return false;
  }

  public boolean isSelected() {
    return false;
  }

  public boolean isUseMainLayerForPainter() {
    return useMainLayerForPainter && ((border == null) || border.canUseMainLayer());
  }

  public boolean isUsePainterBackgroundColor() {
    return usePainterBackgroundColor &&!transparent;
  }

  public boolean isUsePainterBorder() {
    return usePainterBorder;
  }

  public abstract boolean isVisible();

  public boolean isWordWrap() {
    return false;
  }

  protected void addMouseGestureListener() {}

  protected void checkForegroundColor() {
    if (foregroundColor == ColorUtils.getForeground()) {
      if (enabled) {
        setForegroundColorEx(foregroundColor);
      } else {
        setForegroundColorEx(ColorUtils.getDisabledForeground());
      }
    } else if (foregroundColor!=null) {
        setForegroundColorEx(foregroundColor.getColor(enabled
                                          ? ButtonState.DEFAULT
                                          : ButtonState.DISABLED));
    }
  }

  protected abstract void disposeEx();

  protected native void disposeLayer(Object layer)
  /*-[
    CALayer* l=(CALayer*)layer;
    if([l isKindOfClass:[RARECAGradientLayer class]]) {
      [(RARECAGradientLayer*)layer sparDispose];
    }
    else if([l isKindOfClass:[RARECALayer class]]) {
      [(RARECALayer*)layer sparDispose];
    }
  ]-*/
  ;

  protected void handleBorder(iPlatformBorder border) {
    iPlatformBorder b = border;

    if (b instanceof UICompoundBorder) {
      iPlatformBorder ib = ((UICompoundBorder) b).getInsideBorder();
      iPlatformBorder ob = ((UICompoundBorder) b).getOutsideBorder();

      if (ib instanceof UIEmptyBorder) {
        b = ob;
      } else if ((ob instanceof UIEmptyBorder) && (ib instanceof UILineBorder)) {
        UILineBorder lb = (UILineBorder) ib;

        if (lb.usesPath()) {
          setPathLineBorder(lb.getPaintColor(component), lb.getPathWidth(), lb.getLineStyle());
          setLayerLayoutEnabled(true);

          return;
        }
      }
    }

    if (b instanceof UILineBorder) {
      UILineBorder lb = (UILineBorder) b;

      if (lb.canUseMainLayer()) {
        setLineBorder(lb.getPaintColor(component), lb.getPathWidth(), lb.getArcHeight());

        return;
      } else if (lb.usesPath()) {
        setPathLineBorder(lb.getPaintColor(component), lb.getPathWidth(), lb.getLineStyle());
        setLayerLayoutEnabled(true);
      } else {
        if (border.isPaintLast()) {
          getOverlayLayer();
        }

        if (!border.isRectangular()) {    // layout will clip to the shape
          setLayerLayoutEnabled(true);
        }

        setPaintHandlerEnabled(true);
      }

      return;
    }

    if (!(border instanceof UIEmptyBorder)) {
      if (border != null) {
        if (border.isPaintLast()) {
          getOverlayLayer();
        }

        if (!border.isRectangular()) {    // layout will clip to the shape
          setLayerLayoutEnabled(true);
        }

        setPaintHandlerEnabled(true);
      }

      revalidate();
    }
  }

  protected abstract void handleWantsFirstInteraction();

  protected void layingoutLayers(float width, float height) {
    iPlatformBorder b = getBorder();

    if ((b != border) || ((b != null) && (b.getModCount() != modCountBorder)) || (oldWidth != width)
        || (oldHeight != height)) {
      if (b != null) {
        if (b instanceof SharedLineBorder) {
          ((SharedLineBorder) b).updateShape(getComponent(), width, height);
        }

        modCountBorder = b.getModCount();

        if (borderPath != null) {
          borderPath.reset();
        }

        float off = b.getPathOffset();

        borderPath = b.getPath(borderPath, -off, -off, width + off + off, height + off + off, false);
      } else {
        borderPath = null;
      }

      setLayerPath((borderPath == null)
                   ? null
                   : borderPath.getPath());

      if (b == null) {
        setClipMask(null);
      } else {
        if (clip != null) {
          clip.reset();
        }

        float off = b.getPathOffset();

        clip = b.getPath(clip, -off, -off, width + off + off, height + off + off, true);
        setClipMask((clip == null)
                    ? null
                    : clip.getPath());
      }

      border = b;
    }

    oldWidth  = width;
    oldHeight = height;
  }

  protected void visibilityChanged(boolean shown) {
    if (viewListener != null) {
      if (changeEvent == null) {
        changeEvent = new ChangeEvent(this);
      }

      if (shown) {
        viewListener.viewShown(changeEvent);
      } else {
        viewListener.viewHidden(changeEvent);
      }
    }
  }

  protected native void setBackgroundOverlayPainterEx(iBackgroundPainter bp)
  /*-[
    layerForBackgroundOverlay_=bp!=nil;
    CALayer* layer=(CALayer*)[self getLayer];
    if([layer isKindOfClass:[RARECAGradientLayer class]]) {
      RARECAGradientLayer* gl=(RARECAGradientLayer*)layer;
      RARECALayer* l=[gl getBackgroundOverLayerCreate: YES forRAREView: (RAREView*)self];
      [AppleHelper setLayerPainter: bp onLayer: l withBackground: [self getBackgroundColor]];
    }
  ]-*/
  ;

  protected native void setBackgroundPainterEx(iBackgroundPainter bp)
  /*-[
    CALayer* layer=(CALayer*)[self getLayer];
    [AppleHelper setLayerPainter: bp onLayer: layer withBackground: [self getBackgroundColor]];
  ]-*/
  ;

  protected abstract void setEnabledEx(boolean b);

  protected void setFocusListenerEnabled(boolean b) {}

  protected void setForegroundColorEx(UIColor fg) {}

  protected abstract void setKeyBoardListenerEnabled(boolean enabled);

  public native void setLayerLayoutEnabled(boolean enabled)
  /*-[
    CALayer* calayer=(CALayer*)[self getLayer];
    if([calayer isKindOfClass:[RARECAGradientLayer class]]) {
      RARECAGradientLayer* layer=(RARECAGradientLayer*)calayer;
      layer->layoutEnabled_=enabled;
      [layer setNeedsLayout];
      [layer setNeedsDisplay];
    }
  ]-*/
  ;

  protected native void setLayerPath(Object nativepath)
  /*-[
    CAShapeLayer* layer=(CAShapeLayer*)[ self getOverlayLayer];
  #if  TARGET_OS_IPHONE
    layer.path=((UIBezierPath*)nativepath).CGPath;
  #else
    layer.path=(__bridge CGPathRef)([AppleHelper quartzPath:(NSBezierPath *)nativepath]);
  #endif
    [layer didChangeValueForKey:@"path"];
  ]-*/
  ;

  protected native void setOverlayPainterEx(iPlatformPainter bp)
  /*-[
    layerForOverlay_=bp!=nil;
    CALayer* layer=(CALayer*)[self getOverlayLayer];
    [AppleHelper setLayerPainter: bp onLayer: layer withBackground: [self getBackgroundColor]];
  ]-*/
  ;

  protected native void setSystemOverlayPainterEx(iPlatformPainter painter)
  /*-[
    RARECALayer* layer=(RARECALayer*)[self getOverlayLayer];
    [layer setSystemPainter: painter];
  ]-*/
  ;

  protected abstract void setVisibleEx(boolean visible);

  protected abstract Object getLayer();

  protected native Object getOverlayLayer()
  /*-[
    if(!overlayLayer_) {
      __block RARECAGradientLayer* layer=nil;
      CALayer *l=((UIView*)proxy_).layer;
      if (![l isKindOfClass:[RARECAGradientLayer class]]) {

        [l.sublayers enumerateObjectsUsingBlock:^(id obj, NSUInteger idx, BOOL *stop) {
          CALayer *sl = (CALayer *) obj;
          if ([sl isKindOfClass:[RARECAGradientLayer class]]) {
            layer=(RARECAGradientLayer*)l;
            *stop=YES;
          }
        }];

      }
      else {
        layer=(RARECAGradientLayer*)l;
      }
      if(layer) {
        overlayLayer_=[layer getOverLayerCreate:YES forRAREView:(RAREView*)self];
      }
      else {
        overlayLayer_=[RARECALayer layer];
        [l addSublayer: (CALayer*)overlayLayer_];

      }
    }
    return overlayLayer_;
  ]-*/
  ;

  protected abstract boolean isPaintEnabled();

  protected boolean isSingleColorPainter() {
    if (componentPainter != null) {
      return componentPainter.isSingleColorPainter();
    }

    return true;
  }

  private boolean imagePainterCheckedAndOkToAdd(iPlatformPainter p) {
    if (p instanceof UIImagePainter) {
      UIImagePainter ip = (UIImagePainter) p;

      if ((ip.getImage() != null) && ip.getImage().isLoaded(null)) {
        return true;
      }

      return false;
    }

    return true;
  }

  private native void setLineBorder(UIColor color, float thickness, float radius)
  /*-[
    CALayer* layer=(CALayer*)[ self getLayer];
    layer.borderWidth=thickness;
    layer.cornerRadius=radius;
    if(color) {
      layer.borderColor=(__bridge CGColorRef)([color getCGColor ]);
    }
  ]-*/
  ;

  private native void setPathLineBorder(UIColor color, float thickness, String lineStyle)
  /*-[
    CAShapeLayer* layer=(CAShapeLayer*)[ self getOverlayLayer];
    layer.lineWidth=thickness*2;
    if(color) {
      layer.strokeColor=(__bridge CGColorRef)([color getCGColor ]);
    }
  ]-*/
  ;
}
