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

import com.appnativa.rare.exception.ApplicationException;
import com.appnativa.rare.iConstants;
import com.appnativa.rare.platform.PlatformHelper;
import com.appnativa.rare.platform.swing.ui.util.SwingHelper;
import com.appnativa.rare.platform.swing.ui.view.ButtonView;
import com.appnativa.rare.platform.swing.ui.view.CheckBoxView;
import com.appnativa.rare.platform.swing.ui.view.LabelView;
import com.appnativa.rare.platform.swing.ui.view.RadioButtonView;
import com.appnativa.rare.ui.RenderableDataItem.HorizontalAlign;
import com.appnativa.rare.ui.RenderableDataItem.IconPosition;
import com.appnativa.rare.ui.RenderableDataItem.Orientation;
import com.appnativa.rare.ui.RenderableDataItem.VerticalAlign;
import com.appnativa.rare.ui.border.UIEmptyBorder;
import com.appnativa.rare.ui.event.ActionEvent;
import com.appnativa.rare.ui.event.ChangeEvent;
import com.appnativa.rare.ui.event.iActionListener;
import com.appnativa.rare.ui.event.iChangeListener;
import com.appnativa.rare.ui.painter.PaintBucket;
import com.appnativa.rare.ui.painter.PainterHolder;
import com.appnativa.rare.widget.aGroupableButton;

import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.AbstractButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;

/**
 *
 * @author Don DeCoteau
 */
public class ActionComponent extends Component
        implements PropertyChangeListener, iActionComponent, ActionListener, ItemListener {
  protected IconPosition  iconPosition = IconPosition.AUTO;
  protected UIAction      action;
  protected iPlatformIcon disabledIcon;
  protected iPlatformIcon icon;
  protected iPlatformIcon pressedIcon;
  protected float         scaleFactor;
  protected boolean       scaleIcon;
  protected iPlatformIcon selectedIcon;
  private int             iconGap = 4;
  private boolean         hasChangeListener;
  private boolean         minHeightSet;
  private boolean         scrollview;
  private boolean         textWrapped;

  public ActionComponent(JComponent view) {
    super(view);

    // sageMinHeight = "1ln";
    if (view instanceof AbstractButton) {
      ((AbstractButton) view).addActionListener(this);
      ((AbstractButton) view).addItemListener(this);
    } else if (view instanceof JTextField) {
      ((JTextField) view).addActionListener(this);
    } else {
      scrollview = true;
    }
  }

  protected ActionComponent() {
    super();
  }

  public void actionPerformed(ActionEvent e) {
    if ((listenerList != null) && (listenerList.getListenerCount(iActionListener.class) > 0)) {
      Utils.fireActionEvent(listenerList, e);
    }
  }

  @Override
  public void actionPerformed(java.awt.event.ActionEvent e) {
    if ((listenerList != null) && (listenerList.getListenerCount(iActionListener.class) > 0)) {
      Utils.fireActionEvent(listenerList, PlatformHelper.createActionEvent(e));
    }
  }

  @Override
  public void addActionListener(iActionListener l) {
    getEventListenerList().add(iActionListener.class, l);

    if (view instanceof AbstractButton) {
      ((AbstractButton) view).removeActionListener(this);
      ((AbstractButton) view).addActionListener(this);
    } else if (view instanceof JTextField) {
      ((JTextField) view).removeActionListener(this);
      ((JTextField) view).addActionListener(this);
    }
  }

  @Override
  public void addChangeListener(iChangeListener l) {
    hasChangeListener = true;
    getEventListenerList().add(iChangeListener.class, l);
  }

  @Override
  public boolean adjustMinimumHeightForWidth() {
    return textWrapped;
  }

  @Override
  public iPlatformComponent copy() {
    try {
      return new ActionComponent(view.getClass().newInstance());
    } catch(Exception e) {
      throw new ApplicationException(e);
    }
  }

  @Override
  public void doClick() {
    if (view instanceof AbstractButton) {
      ((AbstractButton) view).doClick();
    }
  }

  @Override
  public void fireActionEvent() {
    if (view instanceof AbstractButton) {
      ((AbstractButton) view).doClick();
    }
  }

  @Override
  public boolean heightChangesBasedOnWidth() {
    if (!textWrapped) {
      return false;
    }

    CharSequence cs = getText();

    return (cs != null) && (cs.length() > 0);
  }

  @Override
  public void itemStateChanged(ItemEvent e) {
    aGroupableButton b         = (aGroupableButton) getWidget();
    boolean          isChecked = e.getStateChange() == ItemEvent.SELECTED;

    b.selectionChanged(isChecked);

    if (hasChangeListener) {
      if (changeEvent != null) {
        changeEvent = new ChangeEvent(this);
      }

      Utils.fireChangeEvent(listenerList, changeEvent);
    }
  }

  @Override
  public void propertyChange(PropertyChangeEvent pce) {
    if (!(pce.getSource() instanceof UIAction)) {
      super.propertyChange(pce);

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
      setIcon((iPlatformIcon) pce.getNewValue());
    } else if (property.equals("DisabledIcon")) {
      setDisabledIcon((iPlatformIcon) pce.getNewValue());
    }
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
    getEventListenerList().remove(iChangeListener.class, l);
  }

  @Override
  public String toString() {
    CharSequence cs = getText();

    return (cs == null)
           ? ""
           : cs.toString();
  }

  @Override
  public void setAction(UIAction a) {
    if (action != null) {
      action.removePropertyChangeListener(this);
      removeActionListener(action);
    }

    this.action = a;

    if (a != null) {
      removeActionListener(action);
      addActionListener(action);
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
    SwingHelper.setHorizontalAlignment(view, hal);
    SwingHelper.setVerticalAlignment(view, val);
  }

  @Override
  public void setDisabledIcon(iPlatformIcon icon) {
    if (icon instanceof UIImageIcon) {
      ((UIImageIcon) icon).isImageLoaded(this);
    }

    disabledIcon = icon;

    if (view instanceof AbstractButton) {
      ((AbstractButton) view).setDisabledIcon(icon);
    } else if (view instanceof JLabel) {
      ((JLabel) view).setDisabledIcon(icon);
    }
  }

  @Override
  public void setDisabledSelectedIcon(iPlatformIcon icon) {}

  /**
   * @param icon
   *          the icon to set
   */
  @Override
  public void setIcon(iPlatformIcon icon) {
    if (this.icon != icon) {
      if (this.icon instanceof UISpriteIcon) {
        ((UISpriteIcon) this.icon).setOwner(null);
      }

      this.icon = icon;

      if (icon instanceof UIImageIcon) {
        ((UIImageIcon) icon).isImageLoaded(this);
      } else if (icon instanceof UISpriteIcon) {
        ((UISpriteIcon) icon).setOwner(this);
      }

      if (view instanceof AbstractButton) {
        ((AbstractButton) view).setIcon(icon);

        if (view instanceof JToggleButton) {
          if (pressedIcon == null) {
            ((AbstractButton) view).setPressedIcon(icon);
          }

          if (selectedIcon == null) {
            ((AbstractButton) view).setSelectedIcon(icon);
          }

          if (disabledIcon == null) {
            ((AbstractButton) view).setDisabledIcon(null);
            ((AbstractButton) view).setDisabledSelectedIcon(null);
          }
        }
      } else if (view instanceof JLabel) {
        ((JLabel) view).setIcon(icon);
      }
    }
  }

  /**
   * @param iconGap
   *          the iconGap to set
   */
  @Override
  public void setIconGap(int iconGap) {
    this.iconGap = iconGap;

    if (view instanceof AbstractButton) {
      ((AbstractButton) view).setIconTextGap(iconGap);
    } else if (view instanceof JLabel) {
      ((JLabel) view).setIconTextGap(iconGap);
    }
  }

  /**
   * @param iconPosition
   *          the iconPosition to set
   */
  @Override
  public void setIconPosition(IconPosition iconPosition) {
    if (this.iconPosition != iconPosition) {
      this.iconPosition = iconPosition;
      SwingHelper.setIconPosition(view, iconPosition);
    }
  }

  @Override
  public void setMargin(UIInsets insets) {
    if (view instanceof AbstractButton) {
      Insets in = SwingHelper.setInsets(null, insets);

      ((AbstractButton) view).setMargin(in);
    } else if (view instanceof JLabel) {
      if (getBorder() == null) {
        ((JLabel) view).setBorder(new UIEmptyBorder(insets));
      }
    } else if (view instanceof JTextField) {
      ((JTextField) view).setMargin(SwingHelper.setInsets(null, insets));
    }
  }

  @Override
  public void setMargin(float top, float right, float bottom, float left) {
    setMargin(new UIInsets(top, right, bottom, left));
  }

  @Override
  public void setMnemonic(char mn) {
    if (view instanceof AbstractButton) {
      ((AbstractButton) view).setMnemonic(mn);
    }
  }

  @Override
  public void setOrientation(Orientation orientation) {
    if (view instanceof LabelView) {
      ((LabelView) view).setOrientation(orientation);
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
    if (view instanceof AbstractButton) {
      ((AbstractButton) view).setPressedIcon(icon);
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
    if (view instanceof AbstractButton) {
      ((AbstractButton) view).setSelected(selected);
    }
  }

  /**
   * @param icon
   *          the selectedIcon to set
   */
  @Override
  public void setSelectedIcon(iPlatformIcon icon) {
    if (view instanceof AbstractButton) {
      ((AbstractButton) view).setSelectedIcon(icon);
    }

    this.selectedIcon = icon;
  }

  @Override
  public void setText(CharSequence buttonText) {
    String text = (buttonText == null)
                  ? ""
                  : buttonText.toString();

    if (view instanceof AbstractButton) {
      ((AbstractButton) view).setText(text);
    } else if (view instanceof JLabel) {
      ((JLabel) view).setText(text);
    } else if (view instanceof JTextField) {
      ((JTextField) view).setText(text);
    }
  }

  @Override
  public void setToolTipText(CharSequence text) {
    if (view instanceof JComponent) {
      view.setToolTipText((text == null)
                          ? ""
                          : text.toString());
    }
  }

  @Override
  public void setView(JComponent view) {
    super.setView(view);

    if (view instanceof AbstractButton) {
      ((AbstractButton) view).addActionListener(this);
      ((AbstractButton) view).addItemListener(this);
    } else if (view instanceof JTextField) {
      ((JTextField) view).addActionListener(this);
    } else {
      scrollview = true;
    }
  }

  @Override
  public void setWordWrap(boolean wrap) {
    textWrapped = wrap;

    if (view instanceof LabelView) {
      ((LabelView) view).setWordWrap(wrap);
    } else if (view instanceof ButtonView) {
      ((ButtonView) view).setWordWrap(wrap);
    } else if (view instanceof CheckBoxView) {
      ((CheckBoxView) view).setWordWrap(wrap);
    } else if (view instanceof RadioButtonView) {
      ((RadioButtonView) view).setWordWrap(wrap);
    }
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
  public PaintBucket getFocusPaint(iPlatformGraphics g, PaintBucket def) {
    if (!isFocusOwner()) {
      return null;
    }

    return super.getFocusPaint(g, def);
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
  public UIDimension getInnerSize(UIDimension size) {
    size = getSize(size);

    Insets m = null;

    if (view instanceof AbstractButton) {
      m = ((AbstractButton) view).getMargin();
    } else if (view instanceof JTextField) {
      m = ((JTextField) view).getMargin();
    }

    if (m != null) {
      size.width  -= (m.left + m.right);
      size.height -= (m.top + m.bottom);
    } else {
      iPlatformBorder b = getBorder();

      if (b != null) {
        UIInsets in = b.getBorderInsets(new UIInsets());

        size.width  -= (in.left + in.right);
        size.height -= (in.top + in.bottom);
      }
    }

    return size;
  }

  @Override
  public UIInsets getMargin() {
    if (view instanceof AbstractButton) {
      Insets in = ((AbstractButton) view).getMargin();

      return SwingHelper.setUIInsets(null, in);
    } else if (view instanceof JLabel) {
      Insets in = ((JLabel) view).getInsets();

      return SwingHelper.setUIInsets(null, in);
    } else if (view instanceof JTextField) {
      Insets in = ((JTextField) view).getMargin();

      return SwingHelper.setUIInsets(null, in);
    }

    return null;
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
    if (view instanceof AbstractButton) {
      return ((AbstractButton) view).getText();
    } else if (view instanceof JLabel) {
      return ((JLabel) view).getText();
    } else if (view instanceof JTextField) {
      return ((JTextField) view).getText();
    }

    return null;
  }

  @Override
  public boolean isMouseOver() {
    if (view instanceof AbstractButton) {
      return ((AbstractButton) view).getModel().isRollover();
    }

    return super.isMouseOver();
  }

  @Override
  public boolean isPressed() {
    if (view instanceof AbstractButton) {
      return ((AbstractButton) view).getModel().isPressed();
    }

    return false;
  }

  @Override
  public boolean isScaleIcon() {
    return scaleIcon && (scaleFactor > 0);
  }

  @Override
  public boolean isSelected() {
    if (view instanceof AbstractButton) {
      return ((AbstractButton) view).isSelected();
    }

    return false;
  }

  @Override
  public boolean isWordWrap() {
    return textWrapped;
  }

  @Override
  protected void disposeEx() {
    if (this.action != null) {
      this.action.removePropertyChangeListener(this);
      this.action = null;
    }

    super.disposeEx();
  }

  @Override
  protected void getMinimumSizeEx(UIDimension size) {
    boolean addBackIconWidth = false;

    if (scrollview) {
      super.getMinimumSizeEx(size);
      size.width       = FontUtils.getCharacterWidth(getFont()) * 3;
      addBackIconWidth = true;
    } else {
      super.getPreferredSizeEx(size, 0);

      CharSequence s   = getText();
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
          n                *= FontUtils.getCharacterWidth(getFont());
          size.width       = Math.min(n, size.width);
          size.height      = Math.max(size.height, ScreenUtils.lineHeight(this));
          addBackIconWidth = true;
        }
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
