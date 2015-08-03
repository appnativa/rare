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

import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;

import android.text.TextUtils.TruncateAt;

import android.util.StateSet;

import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;

import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.appnativa.rare.iConstants;
import com.appnativa.rare.platform.android.ui.MultiStateIcon;
import com.appnativa.rare.platform.android.ui.NullDrawable;
import com.appnativa.rare.platform.android.ui.util.AndroidHelper;
import com.appnativa.rare.platform.android.ui.util.PainterHolderDrawable;
import com.appnativa.rare.platform.android.ui.util.PainterUtils;
import com.appnativa.rare.platform.android.ui.view.ButtonViewEx;
import com.appnativa.rare.platform.android.ui.view.CheckBoxView;
import com.appnativa.rare.platform.android.ui.view.EditTextEx;
import com.appnativa.rare.platform.android.ui.view.LabelView;
import com.appnativa.rare.platform.android.ui.view.RadioButtonView;
import com.appnativa.rare.platform.android.ui.view.ToggleButtonView;
import com.appnativa.rare.ui.RenderableDataItem.HorizontalAlign;
import com.appnativa.rare.ui.RenderableDataItem.IconPosition;
import com.appnativa.rare.ui.RenderableDataItem.Orientation;
import com.appnativa.rare.ui.RenderableDataItem.VerticalAlign;
import com.appnativa.rare.ui.event.ActionEvent;
import com.appnativa.rare.ui.event.ChangeEvent;
import com.appnativa.rare.ui.event.iActionListener;
import com.appnativa.rare.ui.event.iChangeListener;
import com.appnativa.rare.ui.listener.iTextChangeListener;
import com.appnativa.rare.ui.painter.PainterHolder;
import com.appnativa.rare.ui.painter.iComponentPainter;
import com.appnativa.rare.ui.painter.iPlatformComponentPainter;
import com.appnativa.rare.widget.aGroupableButton;

import java.beans.PropertyChangeEvent;

/**
 *
 * @author Don DeCoteau
 */
public class ActionComponent extends Component
        implements Cloneable, View.OnClickListener, CompoundButton.OnCheckedChangeListener,
                   TextView.OnEditorActionListener, iActionComponent {
  int                     count        = 0;
  int                     mincount     = 0;
  protected IconPosition  iconPosition = IconPosition.AUTO;
  protected UIAction      action;
  protected iPlatformIcon disabledIcon;
  protected iPlatformIcon icon;
  protected iPlatformIcon pressedIcon;
  protected float         scaleFactor;
  protected boolean       scaleIcon;
  protected iPlatformIcon selectedIcon;
  private int             iconGap     = 4;
  private boolean         textWrapped = true;
  private boolean         adjustSize  = true;
  private boolean         minHeightSet;
  private MultiStateIcon  stateIcon;
  PainterHolderDrawable   painterHolderDrawable;
  int                     listItemPadding;

  public ActionComponent(View view) {
    super(view);

    if (view instanceof CompoundButton) {
      ((CompoundButton) view).setOnCheckedChangeListener(this);
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

  public void addActionListener(iActionListener l) {
    if (view instanceof EditText) {
      ((EditText) view).setOnEditorActionListener(this);
    } else {
      view.setOnClickListener(this);
    }

    getEventListenerList().add(iActionListener.class, l);
  }

  public void addChangeListener(iChangeListener l) {
    if (view instanceof CompoundButton) {
      ((CompoundButton) view).setOnCheckedChangeListener(this);
      getEventListenerList().add(iChangeListener.class, l);
    }
  }

  public void addTextChangeListener(iTextChangeListener l) {
    if (view instanceof EditTextEx) {
      getEventListenerList().add(iTextChangeListener.class, l);
    }
  }

  public boolean adjustMinimumHeightForWidth() {
    return textWrapped;
  }

  public Component copy() {
    ActionComponent c = (ActionComponent) super.copy();

    if (c.listenerList != null) {
      c.getEventListenerList().clear();
    }

    return c;
  }

  public void dispose() {
    if (this.action != null) {
      this.action.removePropertyChangeListener(this);
      this.action = null;
    }

    if (painterHolderDrawable != null) {
      painterHolderDrawable.dispose();
      painterHolderDrawable = null;
    }

    super.dispose();
  }

  public void doClick() {
    view.performClick();
  }

  public void fireActionEvent() {
    onClick(view);
  }

  public boolean heightChangesBasedOnWidth() {
    if (!textWrapped) {
      return false;
    }

    CharSequence cs = getText();

    return (cs != null) && (cs.length() > 0);
  }

  public void onCheckedChanged(CompoundButton cb, boolean isChecked) {
    ActionComponent c = (ActionComponent) Component.fromView(cb);

    if (c != null) {
      aGroupableButton b = (aGroupableButton) c.getWidget();

      b.selectionChanged(isChecked);
    }

    if ((listenerList != null) && listenerList.hasListeners(iChangeListener.class)) {
      if (changeEvent != null) {
        changeEvent = new ChangeEvent(this);
      }

      Utils.fireChangeEvent(listenerList, changeEvent);
    }
  }

  public void onClick(View view) {
    ActionEvent e = new ActionEvent(this);

    if (listenerList != null) {
      Utils.fireActionEvent(listenerList, e);
    }
  }

  public boolean onEditorAction(TextView tv, int action, KeyEvent ke) {
    String command;

    switch(action) {
      case EditorInfo.IME_ACTION_NEXT :
        command = "NEXT";

        break;

      case EditorInfo.IME_ACTION_DONE :
        command = "DONE";

        break;

      case EditorInfo.IME_ACTION_GO :
        command = "GO";

        break;

      case EditorInfo.IME_ACTION_SEND :
        command = "SEND";

        break;

      case EditorInfo.IME_ACTION_SEARCH :
        command = "SEARCH";

        break;

      case EditorInfo.IME_ACTION_UNSPECIFIED :
        command = "UNSPECIFIED";

        break;

      default :
        command = Integer.toString(action);

        break;
    }

    ActionEvent e = new ActionEvent(tv, command);

    if (listenerList != null) {
      Utils.fireActionEvent(listenerList, e);
    }

    return false;
  }

  protected void configurePainterHolderDrawable(PainterHolder ph) {
    if (ph == null) {
      if (painterHolderDrawable != null) {
        painterHolderDrawable.setPainterHolder(null);
      }
    } else {
      if (painterHolderDrawable == null) {
        painterHolderDrawable = new PainterHolderDrawable(ph);
        view.setBackgroundDrawable(painterHolderDrawable);
      } else {
        painterHolderDrawable.setPainterHolder(ph);
      }
    }
  }

  public void propertyChange(PropertyChangeEvent pce) {
    super.propertyChange(pce);

    String property = pce.getPropertyName();

    if (property == iComponentPainter.PROPERTY_PAINTER_HOLDER) {
      configurePainterHolderDrawable((PainterHolder) pce.getNewValue());
    }

    if (!(pce.getSource() instanceof UIAction)) {
      return;
    }

    UIAction a = (UIAction) pce.getSource();

    if (property == "enabled") {
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
    } else if (property == "ActionText") {
      setText((String) pce.getNewValue());
    } else if (property == "SmallIcon") {
      if (a.isEnabled()) {
        setIcon((iPlatformIcon) pce.getNewValue());
      } else {
        if (a.getDisabledIcon() == null) {
          setIcon((iPlatformIcon) pce.getNewValue());
        }
      }
    } else if (property == "DisabledIcon") {
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

  public void removeActionListener(iActionListener l) {
    if (listenerList != null) {
      listenerList.remove(iActionListener.class, l);
    }
  }

  public void removeChangeListener(iChangeListener l) {
    if ((view instanceof CompoundButton) && (listenerList != null)) {
      getEventListenerList().remove(iChangeListener.class, l);
    }
  }

  public void removeTextChangeListener(iTextChangeListener l) {
    if (listenerList != null) {
      listenerList.remove(iTextChangeListener.class, l);
    }
  }

  public String toString() {
    return getText();
  }

  public void setAction(UIAction a) {
    if (action != null) {
      action.removePropertyChangeListener(this);
      removeActionListener(action);
    }

    this.action = a;

    if (a != null) {
      removeActionListener(a);
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

  public void setAdjustSize(boolean adjustSize) {
    this.adjustSize = adjustSize;
  }

  public void setAlignment(HorizontalAlign hal, VerticalAlign val) {
    if (view instanceof TextView) {
      ((TextView) view).setGravity(AndroidHelper.getGravity(hal, val));
    }
  }

  public void setComponentPainter(iPlatformComponentPainter cp) {
    super.setComponentPainter(cp);
    configurePainterHolderDrawable((cp == null)
                                   ? null
                                   : cp.getPainterHolder());
  }

  public void setDisabledIcon(iPlatformIcon icon) {
    if (icon instanceof UIImageIcon) {
      ((UIImageIcon) icon).isImageLoaded(this);
    }

    disabledIcon = icon;
    updateStateIcons();
  }

  public void setDisabledSelectedIcon(iPlatformIcon icon) {}

  public void setEnabled(boolean enabled) {
    if (view.isEnabled() != enabled) {
      if (!enabled && (icon != null) && (disabledIcon == null)) {
        setDisabledIcon(icon.getDisabledVersion());
      }

      super.setEnabled(enabled);

      Drawable d = view.getBackground();

      if (d != null) {
        d.setState(view.getDrawableState());
      }

      if ((fgColor == null) && (view instanceof TextView)) {
        UIColor fg = enabled
                     ? ColorUtils.getForeground()
                     : ColorUtils.getDisabledForeground();

        fg.setTextColor((TextView) view);
      }
    }
  }

  public void setGravity(int gravity) {
    if (view instanceof TextView) {
      ((TextView) view).setGravity(gravity);
    }
  }

  /**
   * @param icon
   *          the icon to set
   */
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

      if ((icon != null) && (disabledIcon == null) &&!view.isEnabled()) {
        disabledIcon = icon.getDisabledVersion();
      }

      sizeCache.dirty();
      updateStateIcons();
      view.requestLayout();
    }
  }

  /**
   * @param iconGap
   *          the iconGap to set
   */
  public void setIconGap(int iconGap) {
    this.iconGap = iconGap;
  }

  /**
   * @param iconPosition
   *          the iconPosition to set
   */
  public void setIconPosition(IconPosition iconPosition) {
    if (this.iconPosition != iconPosition) {
      this.iconPosition = iconPosition;

      if (view instanceof LabelView) {
        ((LabelView) view).setIconPosition(iconPosition);
      } else if (view instanceof ButtonViewEx) {
        ((ButtonViewEx) view).setIconPosition(iconPosition);
      } else if (view instanceof ToggleButtonView) {
        ((ToggleButtonView) view).setIconPosition(iconPosition);
      }

      sizeCache.dirty();
      view.requestLayout();
    }
  }

  public void setMargin(UIInsets in) {
    setMargin(in.top, in.right, in.bottom, in.left);
  }

  public void setMargin(float top, float right, float bottom, float left) {
    sizeCache.dirty();

    iPlatformBorder b  = getBorder();
    UIInsets        in = computeInsets;

    if (b != null) {
      in = b.getBorderInsets(in);
    } else {
      in.set(0, 0, 0, 0);
    }

    if (top < 0) {
      top = view.getPaddingTop();
    } else {
      top += in.intTop();
    }

    if (left < 0) {
      left = view.getPaddingLeft();
    } else {
      left += in.intLeft();
    }

    if (bottom < 0) {
      bottom = view.getPaddingBottom();
    } else {
      bottom += in.intBottom();
    }

    if (right < 0) {
      right = view.getPaddingRight();
    } else {
      right += in.intRight();
    }

    view.setPadding((int) left, (int) top, (int) right, (int) bottom);
  }

  public void setMnemonic(char mn) {}

  public void setOrientation(Orientation orientation) {
    if (view instanceof ButtonViewEx) {
      this.orientation = orientation;
      ((ButtonViewEx) view).setOrientation(orientation);
    } else if (view instanceof LabelView) {
      ((LabelView) view).setOrientation(orientation);
      this.orientation = orientation;
    }
  }

  public void setPainterHolder(PainterHolder painterHolder) {
    getComponentPainter(true).setPainterHolder(painterHolder);
  }

  /**
   * @param pressedIcon
   *          the pressedIcon to set
   */
  public void setPressedIcon(iPlatformIcon pressedIcon) {
    if (icon instanceof UIImageIcon) {
      ((UIImageIcon) icon).isImageLoaded(this);
    }

    this.pressedIcon = pressedIcon;
    updateStateIcons();
  }

  public void setScaleIcon(boolean scale, float scaleFactor) {
    scaleIcon        = scale;
    this.scaleFactor = scaleFactor;
  }

  public void setSelected(boolean selected) {
    if (view instanceof CompoundButton) {
      if (selected != ((CompoundButton) view).isChecked()) {
        ((CompoundButton) view).setChecked(selected);
      }
    } else {
      super.setSelected(selected);
    }
  }

  /**
   * @param selectedIcon
   *          the selectedIcon to set
   */
  public void setSelectedIcon(iPlatformIcon selectedIcon) {
    if (icon instanceof UIImageIcon) {
      ((UIImageIcon) icon).isImageLoaded(this);
    }

    this.selectedIcon = selectedIcon;
    updateStateIcons();
  }

  public void setText(CharSequence buttonText) {
    sizeCache.dirty();

    if (view instanceof TextView) {
      listItemPadding = 0;

      if (buttonText instanceof String) {
        if (((String) buttonText).contains("<ul>")) {
          listItemPadding = ScreenUtils.PLATFORM_PIXELS_16 * 2;
        } else if (((String) buttonText).contains("<ol>")) {
          listItemPadding = FontUtils.getCharacterWidth(getFont()) * 4 + ScreenUtils.PLATFORM_PIXELS_16;
        }
      }

      ((TextView) view).setText(buttonText);
      view.requestLayout();
    }
  }

  public void setToolTipText(CharSequence text) {}

  public void setView(View view) {
    super.setView(view);

    if (view instanceof CompoundButton) {
      ((CompoundButton) view).setOnCheckedChangeListener(this);
    }
  }

  public void setWordWrap(boolean wrap) {
    if (textWrapped != wrap) {
      sizeCache.dirty();

      if (view instanceof TextView) {
        TextView v = (TextView) view;

        textWrapped = wrap;

        if (wrap) {
          v.setMaxLines(Short.MAX_VALUE);
          v.setSingleLine(false);
          v.setEllipsize(null);
        } else {
          v.setMaxLines(1);
          v.setSingleLine(true);
          v.setEllipsize(TruncateAt.END);
        }
      }
    }
  }

  public UIAction getAction() {
    return action;
  }

  public iPaintedButton.ButtonState getButtonState() {
    return Utils.getState(isEnabled(), view.isPressed(), isSelected(), isMouseOver());
  }

  public iPlatformIcon getDisabledIcon() {
    if ((disabledIcon == null) && (stateIcon != null)) {
      disabledIcon = stateIcon.getIcon(MultiStateIcon.DISABLED);
    }

    return disabledIcon;
  }

  public iPlatformIcon getIcon() {
    return icon;
  }

  /**
   * @return the iconGap
   */
  public int getIconGap() {
    return iconGap;
  }

  /**
   * @return the iconPosition
   */
  public IconPosition getIconPosition() {
    return iconPosition;
  }

  public float getIconScaleFactor() {
    return scaleFactor;
  }

  public UIInsets getMargin() {
    return new UIInsets(view.getPaddingTop(), view.getPaddingRight(), view.getPaddingBottom(), view.getPaddingLeft());
  }

  public Orientation getOrientation() {
    if (view instanceof ButtonViewEx) {
      return ((ButtonViewEx) view).getOrientation();
    } else if (view instanceof LabelView) {
      return ((LabelView) view).getOrientation();
    }

    return Orientation.HORIZONTAL;
  }

  /**
   * @return the pressedIcon
   */
  public iPlatformIcon getPressedIcon() {
    return pressedIcon;
  }

  /**
   * @return the selectedIcon
   */
  public iPlatformIcon getSelectedIcon() {
    return selectedIcon;
  }

  public String getText() {
    if (view instanceof TextView) {
      return ((TextView) view).getText().toString();
    }

    return "";
  }

  public boolean isAdjustSize() {
    return adjustSize;
  }

  public boolean isMouseOver() {
    return false;
  }

  public boolean isScaleIcon() {
    return scaleIcon && (scaleFactor > 0);
  }

  public boolean isSelected() {
    if (view instanceof CompoundButton) {
      return ((CompoundButton) view).isChecked();
    }

    return view.isSelected();
  }

  public boolean isWordWrap() {
    return textWrapped;
  }

  protected void getMinimumSizeEx(UIDimension size) {
    if (!SIZE_CACHE_ENABLED) {
      sizeCache.minDirty = true;
    }

    if (!sizeCache.minDirty) {
      size.width  = sizeCache.minWidth;
      size.height = sizeCache.minHeight;
    } else {
      super.getPreferredSizeEx(size, 0);

      if (view instanceof TextView) {
        CharSequence s   = ((TextView) view).getText();
        int          len = s.length();

        if (textWrapped && (view instanceof TextView)) {
          if (len > 0) {
            int n = 0;

            for (int i = 0; i < len; i++) {
              if (Character.isWhitespace(s.charAt(i))) {
                n = i;

                break;
              }
            }

            if (n > 0) {
              n           *= FontUtils.getCharacterWidth(getFont());
              n           += view.getPaddingLeft() + view.getPaddingRight();
              size.width  = Math.min(n, size.width);
              size.height = Math.max(size.height, ScreenUtils.lineHeight(this));
            }
          }
        } else {
          size.width = FontUtils.getCharacterWidth(getFont()) * 3 + (view.getPaddingLeft() + view.getPaddingRight());

          if (len > 0) {
            size.height = Math.max(size.height, ScreenUtils.lineHeight(this));
          }
        }
      }

      sizeCache.minDirty  = false;
      sizeCache.minHeight = size.height;
      sizeCache.minWidth  = size.width;
    }
  }

  @Override
  public void imageLoaded(UIImage image) {
    updateStateIcons();
    super.imageLoaded(image);
  }

  @Override
  protected void getPreferredSizeEx(UIDimension size, float maxWidth) {
    if (!SIZE_CACHE_ENABLED) {
      sizeCache.preferredDirty = true;
    }

    if (!sizeCache.preferredDirty && ((maxWidth == 0) || (sizeCache.preferredHeightforWidthWidth == maxWidth))) {
      if (maxWidth > 0) {
        size.width  = maxWidth;
        size.height = sizeCache.preferredHeightforWidth;
      } else {
        size.width  = sizeCache.preferredWidth;
        size.height = sizeCache.preferredHeight;
      }
    } else {
      sizeCache.preferredDirty = true;
      super.getPreferredSizeEx(size, maxWidth);

      if (size.width > 0) {
        size.width += ScreenUtils.PLATFORM_PIXELS_2;
      }

      if (adjustSize) {
        if (view instanceof Button) {
          Utils.adjustButtonSize(size);
        } else if (view instanceof EditTextEx) {
          Utils.adjustTextFieldSize(size);
        }
      }

      size.width               += listItemPadding;
      sizeCache.preferredDirty = false;

      if (maxWidth > 0) {
        sizeCache.preferredHeightforWidth      = size.height;
        sizeCache.preferredHeightforWidthWidth = maxWidth;
      } else {
        sizeCache.preferredHeight = size.height;
        sizeCache.preferredWidth  = size.width;
      }
    }
  }

  protected void updateStateIcons() {
    if ((view instanceof RadioButtonView) || (view instanceof CheckBoxView)) {
      StateListDrawable sld = new StateListDrawable();

      if (pressedIcon != null) {
        sld.addState(PainterUtils.STATE_PRESSED, pressedIcon.getDrawable(view));
      }

      if (disabledIcon != null) {
        sld.addState(PainterUtils.STATE_DISABLED, disabledIcon.getDrawable(view));
      }

      if (selectedIcon != null) {
        sld.addState(PainterUtils.STATE_SELECTED, selectedIcon.getDrawable(view));
      }

      sld.addState(StateSet.WILD_CARD, (icon == null)
                                       ? NullDrawable.getStatefulInstance()
                                       : icon.getDrawable(view));
      ((CompoundButton) view).setButtonDrawable(sld);
    } else {
      iPlatformIcon sicon = this.icon;

      if (sicon != null) {
        if (stateIcon == null) {
          stateIcon = new MultiStateIcon();
        }

        stateIcon.setIcon(icon);
        stateIcon.setSelectedIcon(selectedIcon);
        stateIcon.setPressedIcon(pressedIcon);
        stateIcon.setDisabledIcon(disabledIcon);
        stateIcon.isIconsLoaded(this);
        sicon = stateIcon;
      }

      if (view instanceof LabelView) {
        ((LabelView) view).setIcon(sicon);
      } else if (view instanceof ButtonViewEx) {
        ((ButtonViewEx) view).setIcon(sicon);
      } else if (view instanceof ToggleButtonView) {
        ((ToggleButtonView) view).setIcon(sicon);
      }

      view.invalidate();
    }
  }
}
