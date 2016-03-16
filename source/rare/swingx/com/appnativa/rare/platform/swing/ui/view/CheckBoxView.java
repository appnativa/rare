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

import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.ItemEvent;
import java.awt.geom.AffineTransform;

import javax.swing.DefaultButtonModel;
import javax.swing.Icon;
import javax.swing.JCheckBox;

import com.appnativa.rare.Platform;
import com.appnativa.rare.iConstants;
import com.appnativa.rare.iPlatformAppContext;
import com.appnativa.rare.platform.swing.ui.util.SwingGraphics;
import com.appnativa.rare.ui.ColorUtils;
import com.appnativa.rare.ui.FontUtils;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.iPlatformIcon;
import com.appnativa.rare.ui.painter.iPainter;
import com.appnativa.rare.ui.painter.iPainterSupport;
import com.appnativa.rare.ui.painter.iPlatformComponentPainter;
import com.appnativa.rare.widget.aCheckBoxWidget.State;
import com.appnativa.util.CharArray;
import com.appnativa.util.XMLUtils;

public class CheckBoxView extends JCheckBox implements iPainterSupport, iView {
  protected static iPlatformIcon    deselectedIconDisabled_;
  protected static iPlatformIcon    deselectedIcon_;
  protected static iPlatformIcon    deselectedPressedIcon_;
  protected static iPlatformIcon    indeterminateIcon_;
  protected static iPlatformIcon    selectedIconDisabled_;
  protected static iPlatformIcon    selectedIcon_;
  protected static iPlatformIcon    selectedPressedIcon_;
  protected State                   buttonState = State.DESELECTED;
  protected boolean                 isOnOffSwitch;
  protected boolean                 isTriState;
  private iPlatformIcon             indeterminateDisabledIcon_;
  private iPlatformIcon             indeterminatePressedIcon_;
  private String                    originalText;
  private boolean                   wordWrap;
  private iPlatformComponentPainter componentPainter;

  public CheckBoxView() {
    initialize();
  }

  public CheckBoxView(boolean onOffSwitch) {
    super();
    initialize();
  }

  public CheckBoxView(Icon icon) {
    super(icon);
    initialize();
  }

  public CheckBoxView(String text) {
    super(text);
    initialize();
  }

  public CheckBoxView(String text, Icon icon) {
    super(text, icon);
    initialize();
  }

  public void setIndeterminate() {
    setState(State.INDETERMINATE);
  }

  @Override
  public void setSelected(boolean selected) {
    super.setSelected(selected);
    buttonState = selected
                  ? State.SELECTED
                  : State.DESELECTED;
  }

  @Override
  public void setSelectedIcon(Icon selectedIcon) {
    super.setSelectedIcon(selectedIcon);
  }

  public void setState(State state) {
    buttonState = (state == null)
                  ? State.DESELECTED
                  : state;
    setSelected(buttonState != State.DESELECTED);
  }

  AffineTransform         transform;
  protected SwingGraphics graphics;

  @Override
  public boolean isOpaque() {
    return ((componentPainter != null) && componentPainter.isBackgroundPaintEnabled())
           ? false
           : super.isOpaque();
  }

  @Override
  public void setTransformEx(AffineTransform tx) {
    transform = tx;
  }

  @Override
  public AffineTransform getTransformEx() {
    return transform;
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
  public void paint(Graphics g) {
    Graphics2D      g2 = (Graphics2D) g;
    AffineTransform tx = g2.getTransform();

    if (transform != null) {
      g2.transform(transform);
    }

    graphics = SwingGraphics.fromGraphics(g2, this, graphics);
    super.paint(g2);

    if (tx != null) {
      g2.setTransform(tx);
    }

    graphics.clear();
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

    super.paintComponent(g);
  }

  @Override
  public void setComponentPainter(iPlatformComponentPainter cp) {
    componentPainter = cp;
  }

  @Override
  public iPlatformComponentPainter getComponentPainter() {
    return componentPainter;
  }

  @Override
  public void setText(String text) {
    if (text == null) {
      text = "";
    }

    originalText = text;

    int len = text.length();

    if (wordWrap && (len > 0) &&!text.startsWith("<html>")) {
      CharArray ca = new CharArray(text.length() + 20);

      ca.append("<html>");
      XMLUtils.escape(text.toCharArray(), 0, len, true, ca);
      ca.append("</html>");
      text = ca.toString();
    }

    super.setText(text);
  }

  public void setTriState(boolean triState) {
    isTriState = triState;
  }

  public void setWordWrap(boolean wordWrap) {
    this.wordWrap = wordWrap;
  }

  @Override
  public Icon getDisabledIcon() {
    Icon icon = super.getDisabledIcon();

    if (icon == null) {
      icon = getIcon();

      if (icon instanceof iPlatformIcon) {
        return ((iPlatformIcon) icon).getDisabledVersion();
      }
    }

    return icon;
  }

  public Icon getDisabledSelectedIcon() {
    if (buttonState == State.INDETERMINATE) {
      return indeterminateDisabledIcon_;
    }

    Icon icon = super.getDisabledSelectedIcon();

    if (icon == null) {
      icon = getSelectedIcon();

      if (icon == null) {
        icon = getIcon();
      }

      if (icon instanceof iPlatformIcon) {
        return ((iPlatformIcon) icon).getDisabledVersion();
      }
    }

    return icon;
  }

  @Override
  public Icon getPressedIcon() {
    Icon icon = super.getPressedIcon();

    if (icon != deselectedPressedIcon_) {
      return icon;
    }

    switch(buttonState) {
      case DESELECTED :
        return deselectedPressedIcon_;

      case INDETERMINATE :
        return indeterminatePressedIcon_;

      default :
        return selectedPressedIcon_;
    }
  }

  @Override
  public Icon getSelectedIcon() {
    Icon icon = super.getSelectedIcon();

    if (icon != selectedIcon_) {
      return icon;
    }

    switch(buttonState) {
      case INDETERMINATE :
        return indeterminateIcon_;

      default :
        return selectedIcon_;
    }
  }

  public State getState() {
    if(isTriState) {
      return buttonState;
    }
    else {
      return isSelected() ?State.SELECTED : State.DESELECTED;
    }
  }

  @Override
  public String getText() {
    return originalText;
  }

  public boolean isIndeterminate() {
    return buttonState == State.INDETERMINATE;
  }

  public boolean isWordWrap() {
    return wordWrap;
  }

  protected void initialize() {
    setOpaque(false);
    setFont(FontUtils.getDefaultFont());
    setForeground(ColorUtils.getForeground());
    setModel(new TriStateButtonModel());

    if (selectedIcon_ == null) {
      iPlatformAppContext app = Platform.getAppContext();

      if (ColorUtils.getForeground().isDarkColor()) {
        selectedIcon_             = app.getResourceAsIcon("Rare.icon.checkbox.on.light");
        deselectedIcon_           = app.getResourceAsIcon("Rare.icon.checkbox.off.light");
        selectedPressedIcon_      = app.getResourceAsIcon("Rare.icon.checkbox.on.pressed.light");
        deselectedPressedIcon_    = app.getResourceAsIcon("Rare.icon.checkbox.off.pressed.light");
        selectedIconDisabled_     = app.getResourceAsIcon("Rare.icon.checkbox.on.disabled.light");
        deselectedIconDisabled_   = app.getResourceAsIcon("Rare.icon.checkbox.off.disabled.light");
        indeterminateIcon_        = app.getResourceAsIcon("Rare.icon.checkbox.indeterminate.light");
        indeterminatePressedIcon_ = app.getResourceAsIcon("Rare.icon.checkbox.indeterminate.pressed.light");
      } else {
        selectedIcon_             = app.getResourceAsIcon("Rare.icon.checkbox.on.dark");
        deselectedIcon_           = app.getResourceAsIcon("Rare.icon.checkbox.off.dark");
        selectedPressedIcon_      = app.getResourceAsIcon("Rare.icon.checkbox.on.pressed.dark");
        deselectedPressedIcon_    = app.getResourceAsIcon("Rare.icon.checkbox.off.pressed.dark");
        selectedIconDisabled_     = app.getResourceAsIcon("Rare.icon.checkbox.on.disabled.dark");
        deselectedIconDisabled_   = app.getResourceAsIcon("Rare.icon.checkbox.off.disabled.dark");
        indeterminateIcon_        = app.getResourceAsIcon("Rare.icon.checkbox.indeterminate.dark");
        indeterminatePressedIcon_ = app.getResourceAsIcon("Rare.icon.checkbox.indeterminate.pressed.dark");
      }

      indeterminateDisabledIcon_ = indeterminateIcon_.getDisabledVersion();
    }

    setSelectedIcon(selectedIcon_);
    setDisabledIcon(deselectedIconDisabled_);
    setPressedIcon(deselectedPressedIcon_);
    setIcon(deselectedIcon_);
    setDisabledSelectedIcon(selectedIconDisabled_);
  }

  class TriStateButtonModel extends DefaultButtonModel {

    /**
     * Creates a new ToggleButton Model
     */
    public TriStateButtonModel() {}

    /**
     * Sets the pressed state of the toggle button.
     */
    @Override
    public void setPressed(boolean b) {
      if ((isPressed() == b) ||!isEnabled()) {
        return;
      }

      if ((b == false) && isArmed()) {
        if (isTriState) {
          switch(buttonState) {
            case DESELECTED :
              buttonState = State.SELECTED;
              setSelectedEx(true);

              break;

            case INDETERMINATE :
              buttonState = State.DESELECTED;
              setSelectedEx(false);

              break;

            default :
              buttonState = State.INDETERMINATE;
              setSelectedEx(true);

              break;
          }
        } else {
          setSelected(!this.isSelected());
        }
      }

      if (b) {
        stateMask |= PRESSED;
      } else {
        stateMask &= ~PRESSED;
      }

      fireStateChanged();

      if (!isPressed() && isArmed()) {
        int      modifiers    = 0;
        AWTEvent currentEvent = EventQueue.getCurrentEvent();

        if (currentEvent instanceof InputEvent) {
          modifiers = ((InputEvent) currentEvent).getModifiers();
        } else if (currentEvent instanceof ActionEvent) {
          modifiers = ((ActionEvent) currentEvent).getModifiers();
        }

        fireActionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, getActionCommand(),
                EventQueue.getMostRecentEventTime(), modifiers));
      }
    }

    /**
     * Sets the selected state of the button.
     *
     * @param b
     *          true selects the toggle button, false deselects the toggle
     *          button.
     */
    @Override
    public void setSelected(boolean b) {
      if (isSelected() == b) {
        return;
      }

      setSelectedEx(b);
    }

    public void setSelectedEx(boolean b) {
      if (b) {
        stateMask |= SELECTED;
      } else {
        stateMask &= ~SELECTED;
      }

      // Send ChangeEvent
      fireStateChanged();
      // Send ItemEvent
      fireItemStateChanged(new ItemEvent(this, ItemEvent.ITEM_STATE_CHANGED, this, this.isSelected()
              ? ItemEvent.SELECTED
              : ItemEvent.DESELECTED));
    }

    /**
     * Checks if the button is selected.
     */
    @Override
    public boolean isSelected() {
      // if(getGroup() != null) {
      // return getGroup().isSelected(this);
      // } else {
      return (stateMask & SELECTED) != 0;
      // }
    }
  }
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
    Dimension d = super.getPreferredSize();

    size.width  = d.width;
    size.height = d.height;
    if (isFontSet() && getFont().isItalic()) {
      if (getClientProperty(javax.swing.plaf.basic.BasicHTML.propertyKey) == null) {
        size.width += 4;
      }
    }
  }

  @Override
  public void getMinimumSize(UIDimension size, int maxWidth) {
    Dimension d = super.getMinimumSize();

    size.width  = d.width;
    size.height = d.height;
  }
}
