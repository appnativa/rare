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

import com.appnativa.rare.iConstants;
import com.appnativa.rare.platform.apple.ui.view.CustomButtonView;
import com.appnativa.rare.platform.apple.ui.view.TextFieldView;
import com.appnativa.rare.platform.apple.ui.view.View;
import com.appnativa.rare.ui.RenderableDataItem.HorizontalAlign;
import com.appnativa.rare.ui.RenderableDataItem.IconPosition;
import com.appnativa.rare.ui.RenderableDataItem.VerticalAlign;
import com.appnativa.rare.ui.event.ActionEvent;
import com.appnativa.rare.ui.event.ChangeEvent;
import com.appnativa.rare.ui.event.iActionListener;
import com.appnativa.rare.ui.event.iChangeListener;
import com.appnativa.rare.ui.painter.PainterHolder;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import java.util.EventObject;

/**
 *
 * @author Don DeCoteau
 */
public class ActionComponent extends Component
        implements Cloneable, PropertyChangeListener, iActionComponent, iActionListener, iChangeListener {
  protected IconPosition  iconPosition = IconPosition.AUTO;
  protected UIAction      action;
  protected iPlatformIcon disabledIcon;
  protected iPlatformIcon icon;
  protected iPlatformIcon pressedIcon;
  protected float         scaleFactor;
  protected boolean       scaleIcon;
  protected iPlatformIcon selectedIcon;
  private int             iconGap = 4;
  private boolean         minHeightSet;

  public ActionComponent(View view) {
    super(view);

    if (!(view instanceof TextFieldView)) {
      useBorderInSizeCalculation = false;
    }
  }

  protected ActionComponent() {
    super();
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (listenerList != null) {
      Utils.fireActionEvent(listenerList, e);
    }
  }

  @Override
  public void addActionListener(iActionListener l) {
    view.setActionListener(this);
    getEventListenerList().add(iActionListener.class, l);
  }

  @Override
  public void addChangeListener(iChangeListener l) {
    view.setChangeListener(this);
    getEventListenerList().add(iChangeListener.class, l);
  }

  @Override
  public boolean adjustMinimumHeightForWidth() {
    return isWordWrap();
  }

  @Override
  public Component copy() {
    ActionComponent c = (ActionComponent) super.copy();

    if (c.listenerList != null) {
      c.listenerList.clear();
    }

    return c;
  }

  @Override
  public void dispose() {
    if (this.action != null) {
      this.action.removePropertyChangeListener(this);
      this.action = null;
    }

    super.dispose();

    if (icon instanceof UISpriteIcon) {
      ((UISpriteIcon) icon).setOwner(null);
    }

    iconPosition = null;
    disabledIcon = null;
    icon         = null;
    pressedIcon  = null;
    selectedIcon = null;
  }

  @Override
  public void doClick() {
    view.performClick();
  }

  @Override
  public void fireActionEvent() {
    view.performClick();
  }

  @Override
  public boolean heightChangesBasedOnWidth() {
    if (!view.isWordWrap()) {
      return false;
    }

    CharSequence cs = getText();

    return (cs != null) && (cs.length() > 0);
  }

  @Override
  public void propertyChange(PropertyChangeEvent pce) {
    if (!(pce.getSource() instanceof UIAction)) {
      return;
    }

    UIAction a        = (UIAction) pce.getSource();
    String   property = pce.getPropertyName();

    if (property.equals("enabled")) {
      if (a.isEnabled()) {
        setEnabled(true);

        if (a.getIcon() != null) {
          setIcon(a.getIcon());
        }
      } else {
        setEnabled(false);

        if (a.getDisabledIcon() != null) {
          setIcon(a.getDisabledIcon());
        }
      }
    } else if (property.equals("ActionText")) {
      setText((String) pce.getNewValue());
    } else if (property.equals("SmallIcon")) {
      if (a.isEnabled()) {
        setIcon((iPlatformIcon) pce.getNewValue());
      } else {
        if (a.getDisabledIcon() == null) {
          setIcon((iPlatformIcon) pce.getNewValue());
        }
      }
    } else if (property.equals("DisabledIcon")) {
      if (!a.isEnabled()) {
        setIcon((iPlatformIcon) pce.getNewValue());
      }
    }

    updateUI();
  }

  @Override
  public void putClientProperty(String key, Object value) {
    if (iConstants.RARE_MIN_HEIGHT_PROPERTY == key) {
      minHeightSet = true;
    } else if ((iConstants.RARE_HEIGHT_PROPERTY == key) &&!minHeightSet) {
      sageMinHeight = null;
    }

    super.putClientProperty(key, value);
  }

  @Override
  public void removeActionListener(iActionListener l) {
    if (listenerList != null) {
      listenerList.remove(iActionListener.class, l);
    }
  }

  @Override
  public void removeChangeListener(iChangeListener l) {
    if (listenerList != null) {
      listenerList.remove(iChangeListener.class, l);
    }
  }

  @Override
  public void stateChanged(EventObject e) {
    if ((listenerList != null) && listenerList.hasListeners(iChangeListener.class)) {
      if (changeEvent != null) {
        changeEvent = new ChangeEvent(this);
      }

      Utils.fireChangeEvent(listenerList, changeEvent);
    }
  }

  @Override
  public String toString() {
    return getText().toString();
  }

  @Override
  public void setAction(UIAction a) {
    if (action != null) {
      action.removePropertyChangeListener(this);
      removeActionListener(action);
    }

    this.action = a;

    if (a != null) {
      addActionListener(a);
      a.addPropertyChangeListener(this);

      iPlatformIcon ic = a.getIcon();

      if (ic != null) {
        setIcon(ic);
      }

      ic = a.getDisabledIcon();

      if (ic != null) {
        setDisabledIcon(ic);
      }

      setEnabled(a.isEnabled());

      CharSequence s = a.getActionText();

      if (s != null) {
        setText(s);
      }
    }
  }

  @Override
  public void setAlignment(HorizontalAlign hal, VerticalAlign val) {
    view.setTextAlignment(hal, val);
  }

  @Override
  public void setDisabledIcon(iPlatformIcon icon) {
    if (icon instanceof UIImageIcon) {
      ((UIImageIcon) icon).isImageLoaded(this);
    }

    disabledIcon = icon;
  }

  @Override
  public void setDisabledSelectedIcon(iPlatformIcon icon) {}

  @Override
  public void setEnabled(boolean enabled) {
    if (view.isEnabled() != enabled) {
      super.setEnabled(enabled);

      if (enabled) {
        if ((disabledIcon != null) && (icon != null)) {
          view.setIcon(icon);
        }
      } else {
        if ((disabledIcon == null) && (icon != null)) {
          disabledIcon = icon.getDisabledVersion();
        }

        if (disabledIcon != null) {
          view.setIcon(disabledIcon);
        }
      }
    }
  }

  /**
   * @param icon
   *          the icon to set
   */
  @Override
  public void setIcon(iPlatformIcon icon) {
    if (this.icon != icon) {
      this.icon = icon;

      if (this.icon instanceof UISpriteIcon) {
        ((UISpriteIcon) this.icon).setOwner(null);
      }

      if (icon instanceof UIImageIcon) {
        ((UIImageIcon) icon).isImageLoaded(this);
      } else if (icon instanceof UISpriteIcon) {
        ((UISpriteIcon) icon).setOwner(this);
      }

      view.setIcon(icon);

      if (view instanceof CustomButtonView) {
        if (pressedIcon == null) {
          ((CustomButtonView) view).setPressedIcon(icon);
        }

        if (selectedIcon == null) {
          ((CustomButtonView) view).setSelectedIcon(icon);
          ((CustomButtonView) view).setPressedSelectedIcon(null);
        }

        if (disabledIcon == null) {
          ((CustomButtonView) view).setDisabledIcon(null);
          ((CustomButtonView) view).setDisabledSelectedIcon(null);
        }
      }

      if (!isEnabled()) {
        if ((disabledIcon == null) && (icon != null)) {
          disabledIcon = icon.getDisabledVersion();
        }

        if (disabledIcon != null) {
          view.setIcon(disabledIcon);
        }
      }

      revalidate();
    }
  }

  /**
   * @param iconGap
   *          the iconGap to set
   */
  @Override
  public void setIconGap(int iconGap) {
    this.iconGap = iconGap;
    view.setIconGap(iconGap);
    revalidate();
  }

  /**
   * @param iconPosition
   *          the iconPosition to set
   */
  @Override
  public void setIconPosition(IconPosition iconPosition) {
    if (this.iconPosition != iconPosition) {
      this.iconPosition = iconPosition;
      view.setIconPosition(iconPosition);
      revalidate();
    }
  }

  @Override
  public void setMargin(UIInsets insets) {
    view.setMargin(insets);
  }

  @Override
  public void setMargin(float top, float right, float bottom, float left) {
    view.setMargin(top, right, bottom, left);
  }

  @Override
  public void setMnemonic(char mn) {}

  @Override
  public void setOrientation(RenderableDataItem.Orientation orientation) {
    switch(orientation) {
      case VERTICAL_DOWN :
        view.setRotation(90);

        break;

      case VERTICAL_UP :
        view.setRotation(-90);

        break;

      default :
        view.setRotation(0);

        break;
    }
  }

  public void setPainterHolder(PainterHolder painterHolder) {
    getComponentPainter(true).setPainterHolder(painterHolder);
  }

  /**
   * @param icon
   *          the pressedIcon to set
   */
  @Override
  public void setPressedIcon(iPlatformIcon icon) {
    if (icon instanceof UIImageIcon) {
      ((UIImageIcon) icon).isImageLoaded(this);
    }

    if (view instanceof CustomButtonView) {
      ((CustomButtonView) view).setPressedIcon(icon);
    }

    this.pressedIcon = icon;
  }

  @Override
  public void setScaleIcon(boolean scale, float scaleFactor) {
    scaleIcon        = scale;
    this.scaleFactor = scaleFactor;
  }

  @Override
  public void setSelected(boolean selected) {
    view.setSelected(selected);
  }

  /**
   * @param icon
   *          the selectedIcon to set
   */
  @Override
  public void setSelectedIcon(iPlatformIcon icon) {
    if (icon instanceof UIImageIcon) {
      ((UIImageIcon) icon).isImageLoaded(this);
    }

    if (view instanceof CustomButtonView) {
      ((CustomButtonView) view).setSelectedIcon(icon);
    }

    this.selectedIcon = icon;
  }

  @Override
  public void setText(CharSequence text) {
    view.setText(text);
    revalidate();
  }

  @Override
  public void setToolTipText(CharSequence text) {}

  @Override
  public void setWordWrap(boolean wrap) {
    view.setWordWrap(wrap);
  }

  @Override
  public UIAction getAction() {
    return action;
  }

  @Override
  public iPaintedButton.ButtonState getButtonState() {
    return Utils.getState(isEnabled(), isPressed(), isSelected(), isMouseOver());
  }

  @Override
  public iPlatformIcon getDisabledIcon() {
    return disabledIcon;
  }

  @Override
  public iPlatformIcon getIcon() {
    return icon;
  }

  /**
   * @return the iconGap
   */
  @Override
  public int getIconGap() {
    return iconGap;
  }

  /**
   * @return the iconPosition
   */
  @Override
  public IconPosition getIconPosition() {
    return iconPosition;
  }

  @Override
  public float getIconScaleFactor() {
    return scaleFactor;
  }

  @Override
  public UIInsets getMargin() {
    return view.getMargin();
  }

  /**
   * @return the pressedIcon
   */
  @Override
  public iPlatformIcon getPressedIcon() {
    return pressedIcon;
  }

  /**
   * @return the selectedIcon
   */
  @Override
  public iPlatformIcon getSelectedIcon() {
    return selectedIcon;
  }

  @Override
  public CharSequence getText() {
    CharSequence s = view.getText();

    return (s == null)
           ? ""
           : s;
  }

  @Override
  public boolean isScaleIcon() {
    return scaleIcon && (scaleFactor > 0);
  }

  @Override
  public boolean isSelected() {
    return view.isSelected();
  }

  @Override
  public boolean isWordWrap() {
    return view.isWordWrap();
  }

  @Override
  protected void getMinimumSizeEx(UIDimension size) {
    super.getPreferredSizeEx(size, 0);

    boolean addBackIconWidth = false;

    if (view.isScrollView()) {
      size.width       = FontUtils.getCharacterWidth(getFont()) * 3;
      addBackIconWidth = true;
    } else {
      CharSequence s   = view.getText();
      int          len = (s == null)
                         ? 0
                         : s.length();

      if (len > 0) {
        int n = 0;

        for (int i = 0; i < len; i++) {
          if (Character.isWhitespace(s.charAt(i))) {
            n = i;

            break;
          }
        }

        if (n > 0) {
          n *= FontUtils.getCharacterWidth(getFont());

          if (n < size.width) {
            size.width       = Math.min(n, size.width);
            addBackIconWidth = true;
          }

          size.height = Math.max(size.height, ScreenUtils.lineHeight(this));
        }
      }

      if (addBackIconWidth) {
        iPlatformIcon ic = getIcon();

        if (ic != null) {
          switch(iconPosition) {
            case LEADING :
            case LEFT :
            case TRAILING :
            case RIGHT :
            case RIGHT_JUSTIFIED :
              size.width += ic.getIconWidth() + getIconGap();

              break;

            default :
              break;
          }
        }
      }
    }
  }
}
