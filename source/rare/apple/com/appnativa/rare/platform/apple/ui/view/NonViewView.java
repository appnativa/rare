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

import com.appnativa.rare.platform.apple.ui.iAppleLayoutManager;
import com.appnativa.rare.platform.apple.ui.util.AppleGraphics;
import com.appnativa.rare.ui.ColorUtils;
import com.appnativa.rare.ui.Component;
import com.appnativa.rare.ui.FontUtils;
import com.appnativa.rare.ui.RenderableDataItem.HorizontalAlign;
import com.appnativa.rare.ui.RenderableDataItem.IconPosition;
import com.appnativa.rare.ui.RenderableDataItem.VerticalAlign;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UICursor;
import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.UIFont;
import com.appnativa.rare.ui.UIInsets;
import com.appnativa.rare.ui.UIPoint;
import com.appnativa.rare.ui.UIRectangle;
import com.appnativa.rare.ui.Utils;
import com.appnativa.rare.ui.event.KeyEvent;
import com.appnativa.rare.ui.event.MouseEvent;
import com.appnativa.rare.ui.event.iActionListener;
import com.appnativa.rare.ui.event.iChangeListener;
import com.appnativa.rare.ui.iGestureListener;
import com.appnativa.rare.ui.iPaintedButton.ButtonState;
import com.appnativa.rare.ui.iPlatformBorder;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.iPlatformGraphics;
import com.appnativa.rare.ui.iPlatformIcon;
import com.appnativa.rare.ui.listener.iFocusListener;
import com.appnativa.rare.ui.listener.iKeyListener;
import com.appnativa.rare.ui.listener.iMouseListener;
import com.appnativa.rare.ui.listener.iMouseMotionListener;
import com.appnativa.rare.ui.listener.iViewListener;
import com.appnativa.rare.ui.painter.iBackgroundPainter;
import com.appnativa.rare.ui.painter.iPlatformComponentPainter;
import com.appnativa.rare.ui.painter.iPlatformPainter;

public class NonViewView extends ParentView {
  private boolean selected;
  private boolean visible;

  public NonViewView() {}

  protected NonViewView(Object o) {
    super(o);
  }

  @Override
  public void add(int position, View view) {}

  @Override
  public void borderChanged(iPlatformBorder newBorder) {}

  @Override
  public void bringToFront() {}

  @Override
  public void clearFocus() {}

  @Override
  public void clearVisualState() {}

  @Override
  public void dispatchEvent(KeyEvent me) {}

  @Override
  public void dispatchEvent(MouseEvent me) {}

  @Override
  public void dispose() {}

  @Override
  public void focusNextView() {}

  @Override
  public void focusPreviousView() {}

  @Override
  public boolean inSameWindow(View otherView) {
    return false;
  }

  @Override
  public void makeOrphan() {}

  @Override
  public void makeTransparent() {}

  @Override
  public void paint(iPlatformGraphics g) {}

  @Override
  public void paintBackground(AppleGraphics g, View v, UIRectangle rect) {}

  @Override
  public void paintOverlay(AppleGraphics g, View v, UIRectangle rect) {}

  @Override
  public void paintPainter(iPlatformPainter p, AppleGraphics g, View v, float width, float height) {}

  @Override
  public void performClick() {}

  @Override
  public void removeChild(View view) {}

  @Override
  public void removeChildren() {}

  @Override
  public void removeNativeBorder() {}

  @Override
  public void repaint() {}

  @Override
  public boolean requestFocus() {
    return false;
  }

  @Override
  public void resetForRenderer() {}

  @Override
  public void resetLayer() {}

  @Override
  public void restoreNativeBorder() {}

  @Override
  public void revalidate() {}

  @Override
  public void set3DTransform(Object tx) {}

  @Override
  public void stateChanged() {}

  @Override
  public boolean usedLayerForBackgroundOverlay() {
    return false;
  }

  @Override
  public boolean usedLayerForOverlay() {
    return false;
  }

  @Override
  public void setActionListener(iActionListener l) {}

  @Override
  public void setBackgroundColor(UIColor bg) {}

  @Override
  public boolean setBackgroundColorEx(UIColor bg) {
    return false;
  }

  @Override
  public void setBackgroundOverlayPainter(iPlatformPainter p) {}

  @Override
  public void setBackgroundPainter(iBackgroundPainter bp) {}

  @Override
  public void setBorder(iPlatformBorder b) {
    this.border = b;
  }

  @Override
  public void setBounds(float x, float y, float w, float h) {}

  @Override
  public void setBounds(int x, int y, int w, int h) {}

  @Override
  public void setClipMask(Object nativepath) {}

  @Override
  public void setComponent(Component component) {
    this.component = component;
  }

  @Override
  public void setComponentPainterEx(iPlatformComponentPainter cp) {
    componentPainter = cp;
  }

  @Override
  public void setCursor(UICursor cursor) {}

  @Override
  public void setDisabledIcon(iPlatformIcon disabledIcon) {}

  @Override
  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  @Override
  public void setFlingGestureListener(iGestureListener l) {}

  @Override
  public void setFocusListener(iFocusListener listener) {}

  @Override
  public void setFocusable(boolean focusable) {}

  @Override
  public void setFont(UIFont f) {
    this.font = f;
  }

  @Override
  public void setForegroundColor(UIColor fg) {
    foregroundColor = fg;
  }

  @Override
  public void setIcon(iPlatformIcon icon) {}

  @Override
  public void setIconGap(int gap) {}

  @Override
  public void setIconPosition(IconPosition iconPosition) {}

  @Override
  public void setChangeListener(iChangeListener l) {}

  @Override
  public void setKeyboardListener(iKeyListener handler) {}

  @Override
  public void setLayoutManager(iAppleLayoutManager lm) {}

  @Override
  public void setLocation(float x, float y) {}

  @Override
  public void setLongPressGestureListener(iGestureListener l) {}

  @Override
  public void setMargin(UIInsets insets) {}

  @Override
  public void setMargin(float top, float right, float bottom, float left) {}

  @Override
  public void setMouseListener(iMouseListener handler) {}

  @Override
  public void setMouseMotionListener(iMouseMotionListener handler) {}

  @Override
  public boolean setOverlayColorEx(UIColor bg) {
    return false;
  }

  @Override
  public void setOverlayPainter(iPlatformPainter p) {}

  @Override
  public void setPaintHandlerEnabled(boolean enabled) {}

  @Override
  public void setPressedIcon(iPlatformIcon pressedIcon) {}

  @Override
  public void setRotateGestureListener(iGestureListener l) {}

  @Override
  public void setRotation(int rotation) {}

  @Override
  public void setScaleGestureListener(iGestureListener l) {}

  @Override
  public void setSelected(boolean selected) {
    this.selected = selected;
  }

  @Override
  public void setSelectedIcon(iPlatformIcon selectedIcon) {}

  @Override
  public void setSize(float width, float height) {}

  @Override
  public void setText(CharSequence string) {}

  @Override
  public void setTextAlignment(HorizontalAlign hal, VerticalAlign val) {}

  @Override
  public void setUseMainLayerForPainter(boolean useMainLayerForPainter) {}

  @Override
  public void setUsePainterBackgroundColor(boolean usePainterBackgroundColor) {}

  @Override
  public void setUsePainterBorder(boolean usePainterBorder) {}

  @Override
  public void setViewListener(iViewListener l) {}

  @Override
  public void setVisible(boolean visible) {
    this.visible = visible;
  }

  @Override
  public void setWantsMouseMovedEvents(boolean b) {}

  @Override
  public void setWordWrap(boolean wrap) {}

  @Override
  public UIColor getBackgroundColor() {
    return null;
  }

  @Override
  public UIColor getBackgroundColorAlways() {
    return ColorUtils.getBackground();
  }

  @Override
  public iPlatformBorder getBorder() {
    return border;
  }

  @Override
  public UIRectangle getBounds() {
    return new UIRectangle();
  }

  @Override
  public UIRectangle getBounds(UIRectangle rect) {
    if (rect != null) {
      rect.set(0, 0, 0, 0);

      return rect;
    }

    return new UIRectangle();
  }

  @Override
  public ButtonState getButtonState() {
    return Utils.getState(false, false, false, false);
  }

  @Override
  public iPlatformComponent getComponent() {
    return component;
  }

  @Override
  public iPlatformComponentPainter getComponentPainter() {
    return componentPainter;
  }

  @Override
  public iPlatformIcon getDisabledIcon() {
    return null;
  }

  @Override
  public iFocusListener getFocusListener() {
    return null;
  }

  @Override
  public UIFont getFont() {
    return font;
  }

  @Override
  public UIFont getFontAlways() {
    return (font == null)
           ? FontUtils.getDefaultFont()
           : font;
  }

  @Override
  public UIColor getForegroundColor() {
    return foregroundColor;
  }

  @Override
  public UIColor getForegroundColorAlways() {
    return (foregroundColor == null)
           ? ColorUtils.getForeground()
           : foregroundColor;
  }

  @Override
  public float getHeight() {
    return 0;
  }

  @Override
  public int getIconGap() {
    return 0;
  }

  @Override
  public iKeyListener getKeyListener() {
    return null;
  }

  @Override
  public UIPoint getLocation(UIPoint loc) {
    return null;
  }

  @Override
  public UIPoint getLocationOnScreen(UIPoint loc) {
    return null;
  }

  @Override
  public UIPoint getLocationOnScreen2D(UIPoint loc) {
    if (loc != null) {
      loc.set(0, 0);

      return loc;
    }

    return new UIPoint();
  }

  @Override
  public void getMinimumSize(UIDimension size,float maxWidth) {}

  @Override
  public iMouseListener getMouseListener() {
    return null;
  }

  @Override
  public iMouseMotionListener getMouseMotionListener() {
    return null;
  }

  @Override
  public void getPreferredSize(UIDimension size, float maxWidth) {}

  @Override
  public iPlatformIcon getPressedIcon() {
    return null;
  }

  @Override
  public int getRotation() {
    return 0;
  }

  @Override
  public iPlatformIcon getSelectedIcon() {
    return null;
  }

  @Override
  public UIDimension getSize() {
    return new UIDimension();
  }

  @Override
  public void getSize(UIDimension size) {}

  @Override
  public CharSequence getText() {
    return null;
  }

  @Override
  public View getViewAt(float x, float y, boolean deepest) {
    return null;
  }

  @Override
  public float getWidth() {
    return 0;
  }

  @Override
  public Window getWindow() {
    return null;
  }

  @Override
  public float getX() {
    return 0;
  }

  @Override
  public float getY() {
    return 0;
  }

  @Override
  public boolean hasBeenFocused() {
    return false;
  }

  @Override
  public boolean hasHadInteraction() {
    return false;
  }

  @Override
  public boolean isDescendantOf(View view) {
    return false;
  }

  @Override
  public boolean isDisplayable() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return enabled;
  }

  @Override
  public boolean isFocusable() {
    return false;
  }

  @Override
  public boolean isFocused() {
    return false;
  }

  @Override
  public boolean isFocusedOrChildOfFocused() {
    return false;
  }

  @Override
  public boolean isMouseOver() {
    return false;
  }

  @Override
  public boolean isMouseTransparent() {
    return false;
  }

  @Override
  public boolean isOpaque() {
    return true;
  }

  @Override
  public boolean isPressed() {
    return pressed;
  }

  @Override
  public boolean isScrollView() {
    return false;
  }

  @Override
  public boolean isSelected() {
    return selected;
  }

  @Override
  public boolean isShowing() {
    return true;
  }

  @Override
  public boolean isUseMainLayerForPainter() {
    return false;
  }

  @Override
  public boolean isUsePainterBackgroundColor() {
    return false;
  }

  @Override
  public boolean isUsePainterBorder() {
    return false;
  }

  @Override
  public boolean isVisible() {
    return visible;
  }

  @Override
  public boolean isWordWrap() {
    return false;
  }

  @Override
  protected Object addInteractionGestureListener() {
    return null;
  }

  @Override
  protected void addMouseGestureListener() {}

  @Override
  protected void checkForegroundColor() {}

  @Override
  protected void disposeEx() {}

  @Override
  protected void hadInteraction() {}

  @Override
  protected void handleWantsFirstInteraction() {}

  @Override
  protected void layingoutLayers(float width, float height) {}

  @Override
  protected void removeGestureListener(Object r) {}

  @Override
  protected void visibilityChanged(boolean shown) {}

  @Override
  protected void setBackgroundOverlayPainterEx(iBackgroundPainter bp) {}

  @Override
  protected void setBackgroundPainterEx(iBackgroundPainter bp) {}

  @Override
  public void setBorderEx(iPlatformBorder border) {}

  @Override
  protected void handleBorder(iPlatformBorder border) {}

  @Override
  protected void setEnabledEx(boolean b) {
    this.enabled = b;
  }

  @Override
  protected void setFocusListenerEnabled(boolean enabled) {}

  @Override
  protected void setForegroundColorEx(UIColor fg) {
    foregroundColor = fg;
  }

  @Override
  protected void setKeyBoardListenerEnabled(boolean enabled) {}

  @Override
  public void setLayerLayoutEnabled(boolean enabled) {}

  @Override
  protected void setLayerPath(Object nativepath) {}

  @Override
  protected void setMouseListenerEnabled(boolean enabled) {}

  @Override
  protected void setMouseMotionListenerEnabled(boolean enabled) {}

  @Override
  protected void setOverlayPainterEx(iPlatformPainter bp) {}

  @Override
  protected void setUseFlipTransform(boolean use) {}

  @Override
  protected void setView(View v) {}

  @Override
  protected void setVisibleEx(boolean visible) {}

  @Override
  protected Object getLayer() {
    return null;
  }

  @Override
  protected Object getOverlayLayer() {
    return null;
  }

  @Override
  protected boolean isPaintEnabled() {
    return false;
  }

  @Override
  protected boolean isSingleColorPainter() {
    return false;
  }
}
