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

package com.appnativa.rare.ui;

import com.appnativa.rare.ui.RenderableDataItem.Orientation;
import com.appnativa.rare.ui.listener.iFocusListener;
import com.appnativa.rare.ui.listener.iKeyListener;
import com.appnativa.rare.ui.listener.iMouseListener;
import com.appnativa.rare.ui.listener.iMouseMotionListener;
import com.appnativa.rare.ui.listener.iTextChangeListener;
import com.appnativa.rare.ui.listener.iViewListener;
import com.appnativa.rare.ui.painter.iPainterSupport;
import com.appnativa.rare.widget.iWidget;

import java.beans.PropertyChangeListener;

public interface iComponent extends iPainterSupport {
  void addFocusListener(iFocusListener l);

  void addKeyListener(iKeyListener l);

  void addMouseListener(iMouseListener l);

  void addMouseMotionListener(iMouseMotionListener l);

  void addTextChangeListener(iTextChangeListener l);

  void addPropertyChangeListener(String propertyName, PropertyChangeListener listener);

  void removePropertyChangeListener(String propertyName, PropertyChangeListener listener);

  void addViewListener(iViewListener l);

  boolean setAlpha(float alpha);

  boolean adjustMinimumHeightForWidth();

  void bringToFront();

  void dispatchEvent(com.appnativa.rare.ui.event.KeyEvent keyEvent);

  void dispatchEvent(com.appnativa.rare.ui.event.MouseEvent mouseEvent);

  void dispose();

  boolean heightChangesBasedOnWidth();

  void putClientProperty(String key, Object value);

  void removeFocusListener(iFocusListener l);

  void removeFromParent();

  void removeKeyListener(iKeyListener l);

  void removeMouseListener(iMouseListener l);

  void removeMouseMotionListener(iMouseMotionListener l);

  void removeTextChangeListener(iTextChangeListener l);

  void removeViewListener(iViewListener l);

  void repaint();

  void requestFocus();

  void revalidate();

  void sendToBack();

  void updateUI();

  void setBackground(UIColor bg);

  void setBorder(iPlatformBorder border);

  void setBounds(UIRectangle bounds);

  void setBounds(float x, float y, float width, float height);

  void setCursor(UICursor cursor);

  void setDisabledColor(UIColor color);

  void setEnabled(boolean enabled);

  void setFocusPainted(boolean focusPainted);

  void setFocusable(boolean focusable);

  void setFont(UIFont f);

  void setForeground(UIColor fg);

  void setLocation(float x, float y);

  void setOpaque(boolean opaque);

  void setScaleIcon(boolean scale, float scaleFactor);

  void setSelected(boolean selected);

  void setSize(float width, float height);

  void setPreferredSize(float width, float height);

  void setSizeLocked(boolean sizeLocked);

  void setVisible(boolean visible);

  void setWidget(iWidget widget);

  UIColor getBackground();

  UIColor getBackgroundEx();

  iPlatformBorder getBorder();

  UIRectangle getBounds();

  Object getClientProperty(String key);

  UIFont getFont();

  UIFont getFontEx();

  UIColor getForeground();

  UIColor getForegroundEx();

  int getHeight();

  float getIconScaleFactor();

  UIRectangle getInnerBounds(UIRectangle rect);

  UIDimension getInnerSize(UIDimension size);

  UIPoint getLocation();

  UIPoint getLocation(UIPoint loc);

  UIPoint getLocationOnScreen();

  UIPoint getLocationOnScreen(UIPoint loc);

  UIDimension getMinimumSize();

  UIDimension getMinimumSize(UIDimension size);

  Object getNativeView();

  Orientation getOrientation();

  UIDimension getOrientedSize(UIDimension size);

  iParentComponent getParent();

  UIDimension getPreferredSize();

  UIDimension getPreferredSize(UIDimension size);

  UIDimension getPreferredSize(UIDimension size, float maxWidth);

  UIDimension getSize();

  UIDimension getSize(UIDimension size);

  iWidget getWidget();

  int getWidth();

  int getX();

  int getY();

  boolean hasChildren();

  boolean isBackgroundSet();

  boolean isDisplayable();

  boolean isDisposed();

  boolean isEnabled();

  boolean isFocusOwner();

  boolean isFocusPainted();

  boolean isFocusable();

  boolean isFontSet();

  boolean isForegroundSet();

  boolean isLeftToRight();

  boolean isOpaque();

  boolean isScaleIcon();

  boolean isSelectable();

  boolean isSelected();

  boolean isShowing();

  boolean isSizeLocked();

  boolean isVisible();
}
