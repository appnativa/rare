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

import com.appnativa.rare.Platform;
import com.appnativa.rare.iConstants;
import com.appnativa.rare.platform.PlatformHelper;
import com.appnativa.rare.ui.RenderableDataItem.IconPosition;
import com.appnativa.rare.ui.border.UICompoundBorder;
import com.appnativa.rare.ui.border.UIEmptyBorder;
import com.appnativa.rare.ui.border.UILineBorder;
import com.appnativa.rare.ui.border.aUILineBorder;
import com.appnativa.rare.ui.painter.UIBackgroundPainter;
import com.appnativa.rare.ui.painter.UIComponentPainter;
import com.appnativa.rare.ui.painter.UISimpleBackgroundPainter;
import com.appnativa.rare.ui.painter.iBackgroundPainter;
import com.appnativa.rare.ui.painter.iGradientPainter.Direction;
import com.appnativa.rare.ui.painter.iPainter;
import com.appnativa.rare.ui.painter.iPlatformComponentPainter;
import com.appnativa.rare.ui.painter.iPlatformPainter;
import com.appnativa.util.IdentityArrayList;

import com.jgoodies.forms.layout.CellConstraints;

import java.util.ArrayList;

/**
 * A panel that manages a set of buttons
 *
 * @author Don DeCoteau
 */
public abstract class aNavigatorPanel extends XPContainer implements iNavigatorPanel {
  protected IdentityArrayList<iButton> buttons      = new IdentityArrayList<iButton>();
  protected PanelType                  panelType    = PanelType.HIERARCHICAL;
  protected IconPosition               iconPosition = IconPosition.LEADING;
  ArrayList<UIDimension>               sizeCache    = new ArrayList<UIDimension>();
  protected boolean                    alwaysFireAction;
  protected iButton                    backButton;
  protected iPlatformIcon              backIcon;
  protected boolean                    buttonsSameSize;
  protected iPlatformPainter           pressedPainter;
  protected iButton                    selectedButton;
  protected UIFont                     selectedFont;
  protected UIColor                    selectedForeground;
  protected iPlatformPainter           selectedPainter;
  protected UIColor                    separatorLineColor;
  protected boolean                    separatorLineColorSet;
  protected boolean                    showIconsOnly;
  protected UIEmptyBorder              buttonBorder = new UIEmptyBorder(Platform.isTouchDevice()
          ? ScreenUtils.PLATFORM_PIXELS_4
          : ScreenUtils.PLATFORM_PIXELS_2);

  @Override
  public void dispose() {
    super.dispose();

    if (buttons != null) {
      buttons.clear();
    }

    if (sizeCache != null) {
      sizeCache.clear();
    }

    sizeCache          = null;
    buttons            = null;
    iconPosition       = null;
    panelType          = null;
    selectedPainter    = null;
    pressedPainter     = null;
    backIcon           = null;
    backButton         = null;
    separatorLineColor = null;
    selectedForeground = null;
    selectedButton     = null;
  }

  protected aNavigatorPanel(Object view) {
    super(view);
    setFocusable(false);
  }

  @Override
  public void setComponentPainter(iPlatformComponentPainter cp) {
    super.setComponentPainter(cp);

    if (backButton != null) {
      backButton.setComponentPainter(cp);
    }
  }

  /**
   * Activates the action for the button at the specified index
   *
   * @param index
   *          the index of the button
   */
  @Override
  public void activateButton(int index) {
    iButton b = buttons.get(index);

    b.doClick();
  }

  /**
   * Adds a button to the panel
   *
   * @param button
   *          the button instance
   */
  @Override
  public void addButton(iActionComponent button) {
    ((iButton) button).setShowIconOnly(showIconsOnly);
    button.setBorder(buttonBorder);
    button.setWordWrap(true);
    button.setFocusable(false);
    buttons.remove(button);
    buttons.add(((iButton) button));

    if (getForeground() != null) {
      button.setForeground(getForeground());
    }

    if (getFont() != null) {
      button.setFont(getFont());
    }

    button.setIconPosition(iconPosition);
    add(button, null);

    int len = buttons.size();

    if ((len > 1) && isHiearchical()) {
      getBackButton().setEnabled(true);
      getBackButton().repaint();
    }

    revalidate();
  }

  /**
   * Adds a button to the panel
   *
   * @param button
   *          the button's text
   *
   * @return the button instance
   */
  @Override
  public iActionComponent addButton(String button) {
    iButton b = createButton(button, null);

    addButton(b);

    return b;
  }

  /**
   * Adds a button to the panel
   *
   * @param a
   *          the button's action
   *
   * @return the button instance
   */
  @Override
  public iActionComponent addButton(UIAction a) {
    iButton b = createButton(a);

    addButton(b);

    return b;
  }

  /**
   * Adds a button to the panel
   *
   * @param button
   *          the button's text
   * @param icon
   *          the button's icon
   *
   * @return the button instance
   */
  @Override
  public iActionComponent addButton(String button, iPlatformIcon icon) {
    iButton b = createButton(button, icon);

    addButton(b);

    return b;
  }

  /**
   * Perform the backup action for a hierarchical panel
   */
  @Override
  public void backup() {
    if (panelType == PanelType.HIERARCHICAL) {
      int len = buttons.size();

      if (len > 1) {
        iButton b = buttons.remove(len - 1);

        this.remove(b);
        len--;
        buttons.get(len - 1).doClick();
      }

      if (len < 2) {
        getBackButton().setEnabled(false);
        getBackButton().repaint();
      }

      revalidate();
      repaint();
    }
  }

  /**
   * Click the button at the given index
   *
   * @param index
   *          the button's index
   */
  @Override
  public void click(int index) {
    buttons.get(index).doClick();
  }

  /**
   * Perform the home action for a hierarchical panel
   */
  @Override
  public void home() {
    if (panelType == PanelType.HIERARCHICAL) {
      int len = buttons.size();

      if (len < 2) {
        return;
      }

      for (int i = 1; i < len; i++) {
        iButton b = buttons.remove(i);

        this.remove(b);
      }

      revalidate();
      repaint();
    }
  }

  /**
   * Gets the index of the specified button instance
   *
   * @param button
   *          the button
   * @return the index of the button or -1
   */
  @Override
  public int indexOf(iActionComponent button) {
    return buttons.indexOf(button);
  }

  /**
   * Gets the index of the specified button
   *
   * @param name
   *          of the action to get the index of
   * @return the index of the button or -1
   */
  @Override
  public int indexOf(String name) {
    int len = buttons.size();

    for (int i = 0; i < len; i++) {
      UIAction a = buttons.get(i).getAction();

      if ((a != null) && name.equals(a.getActionName())) {
        return i;
      }
    }

    return -1;
  }

  public void paintButton(iPlatformGraphics g, UIRectangle rect, boolean pressed) {
    iPlatformComponentPainter cp = getComponentPainter();

    if (pressed) {
      iPlatformBorder b = cp.getBorder();

      if (b != null) {
        g.saveState();
        b.clip(g, rect.x, rect.y, rect.width, rect.height);
      }

      paintShape(g, rect, null);

      if (b != null) {
        g.restoreState();
      }

      return;
    }

    UIColor bg = getBackground();

    if (cp != null) {
      cp.paint(g, rect.x, rect.y, rect.width, rect.height, iPainter.UNKNOWN);
    } else if (bg != null) {
      if (bg.isSimpleColor()) {
        g.clearRect(bg, rect.x, rect.y, rect.width, rect.height);
      } else {
        iBackgroundPainter p = ((UIColorShade) bg).getBackgroundPainter();

        if (p != null) {
          p.paint(g, rect.x, rect.y, rect.width, rect.height, iPainter.UNKNOWN);
        }
      }
    }

    return;
  }

  /**
   * Removes a button from the panel
   *
   * @param index
   *          the index of the button to remove
   */
  @Override
  public void removeButton(int index) {
    removeButton(index, true);
  }

  /**
   * Removes a button from the panel
   *
   * @param name
   *          the name of the action
   */
  @Override
  public void removeButton(String name) {
    int index = indexOf(name);

    if (index != -1) {
      removeButton(index, true);
    }
  }

  /**
   * Removes a button from the panel Only applicable to hierarchical panels.
   *
   * @param button
   *          the button to remove
   * @param removechildren
   *          true to remove the buttons children; false otherwise
   */
  public void removeButton(iButton button, boolean removechildren) {
    int index = buttons.indexOf(button);

    if (index != -1) {
      removeButton(index, removechildren);
    }

    if ((panelType == PanelType.HIERARCHICAL) && (buttons.size() < 2)) {
      getBackButton().setEnabled(false);
      getBackButton().repaint();
    }
  }

  /**
   * Removes a button from the panel
   *
   * @param index
   *          the index of the button to remove
   * @param removechildren
   *          true to remove the buttons children; false otherwise
   */
  public void removeButton(int index, boolean removechildren) {
    if (index < 1) {
      return;
    }

    if (removechildren && (panelType == PanelType.HIERARCHICAL)) {
      removeChildren(index - 1);
    } else {
      int len = buttons.size();

      if (index < len) {
        remove(buttons.remove(index));
      }

      if ((panelType == PanelType.HIERARCHICAL) && (buttons.size() < 2)) {
        getBackButton().setEnabled(false);
      }

      revalidate();
      repaint();
    }
  }

  /**
   * Removes the children for the specified button Only applicable to
   * hierarchical panels.
   *
   * @param button
   *          the button
   */
  public void removeChildren(iButton button) {
    if (panelType == PanelType.HIERARCHICAL) {
      int index = buttons.indexOf(button);

      if (index != -1) {
        removeChildren(index);
      }
    }
  }

  /**
   * Removes the children for the button at the specified index. Only applicable
   * to hierarchical panels.
   *
   * @param index
   *          the button's index
   */
  public void removeChildren(int index) {
    if (panelType == PanelType.HIERARCHICAL) {
      if (index < 0) {
        index = 0;    // force removal of all buttons except the home button
      }

      int len = buttons.size();

      while(--len > index) {
        remove(buttons.remove(len));
      }

      if (buttons.size() < 2) {
        getBackButton().setEnabled(false);
        getBackButton().repaint();
      }

      revalidate();
      repaint();
    }
  }

  @Override
  public void revalidate() {
    super.revalidate();
    cacheInvalidated = true;
  }

  /**
   * Sets whether the action at the specified index is enabled
   *
   * @param index
   *          the index
   * @param enabled
   *          true to enable; false to disable
   */
  @Override
  public void setActionEnabled(int index, boolean enabled) {
    UIAction a = getAction(index);

    if (a != null) {
      a.setEnabled(enabled);
    }
  }

  /**
   * @param alwaysFireAction
   *          the alwaysFireAction to set
   */
  @Override
  public void setAlwaysFireAction(boolean alwaysFireAction) {
    this.alwaysFireAction = alwaysFireAction;
  }

  /**
   * Sets the border of this component.
   *
   * @param border
   *          the border to be rendered for this component
   */
  @Override
  public void setBorder(iPlatformBorder border) {
    super.setBorder(border);

    if (backButton != null) {
      backButton.setBorder(border);
    }

    if (!separatorLineColorSet) {
      UIColor c = UIBorderHelper.findBorderColor(border);

      if (c != null) {
        separatorLineColor = c;
      }
    }
  }

  @Override
  public void setButtonsSameSize(boolean same) {
    buttonsSameSize = same;
  }

  /**
   * Sets whether or not this component is enabled.
   *
   * @param enabled
   *          true if this component should be enabled, false otherwise
   */
  @Override
  public void setEnabled(boolean enabled) {
    for (iButton b : buttons) {
      b.setEnabled(enabled);
    }

    if (getBackButton() != null) {
      getBackButton().setEnabled(false);
    }

    super.setEnabled(enabled);
  }

  @Override
  public void setFont(UIFont font) {
    super.setFont(font);

    for (iButton b : buttons) {
      b.setFont(font);
    }
  }

  @Override
  public void setForeground(UIColor fg) {
    super.setForeground(fg);

    for (iButton b : buttons) {
      b.setForeground(fg);
    }
  }

  /**
   * Sets the icon position
   *
   * @param position
   *          the icon position
   */
  @Override
  public void setIconPosition(IconPosition position) {
    this.iconPosition = position;

    for (iButton button : buttons) {
      button.setIconPosition(position);
    }
  }

  /**
   * Sets the insets for the buttons
   *
   * @param in
   *          the insets for the buttons
   */
  @Override
  public void setInsets(UIInsets in) {
    buttonBorder.setInsets(in);

    if (backButton != null) {
      backButton.setBorder(new UICompoundBorder(getBorder(), buttonBorder));
    }
  }

  /**
   * Sets the panel type
   *
   * @param panelType
   *          the panel type
   */
  @Override
  public void setPanelType(PanelType panelType) {
    PanelType ot = this.panelType;

    if (ot == panelType) {
      return;
    }

    this.panelType = panelType;

    if ((ot != panelType) && (listenerList != null)) {
      Utils.firePropertyChangeEvent(this, listenerList, "panelType", ot, panelType);
    }
  }

  /**
   * Set the painter to use to paint a button's pressed state
   *
   * @param pressedPainter
   *          the painter
   */
  @Override
  public void setPressedPainter(iPlatformPainter pressedPainter) {
    this.pressedPainter = pressedPainter;
  }

  /**
   * Sets the selected button
   *
   * @param b
   *          the button to select
   */
  @Override
  public void setSelectedButton(iActionComponent b) {
    if (panelType != PanelType.HIERARCHICAL) {
      iButton ob = selectedButton;

      this.selectedButton = (iButton) b;

      if (ob != selectedButton) {
        if (selectedButton != null) {
          selectedButton.setSelected(true);
        }

        revalidate();
        repaint();

        if (ob != null) {
          ob.setSelected(false);
        }

        if (listenerList != null) {
          Utils.firePropertyChangeEvent(this, listenerList, "selectedButton", ob, selectedButton);
        }
      }
    }
  }

  /**
   * Sets the selected button
   *
   * @param name
   *          the name of the action
   */
  @Override
  public void setSelectedButton(String name) {
    int index = indexOf(name);

    if (index != -1) {
      setSelectedIndex(index);
    }
  }

  /**
   * Set the font that is used for the selected button
   *
   * @param f
   *          the font that is used for the selected button
   */
  @Override
  public void setSelectedFont(UIFont f) {
    this.selectedFont = f;
  }

  /**
   * Set the foreground color that is used for the selected button
   *
   * @param fg
   *          he foreground color that is used for the selected button
   */
  @Override
  public void setSelectedForeground(UIColor fg) {
    this.selectedForeground = fg;
  }

  /**
   * Sets the selected button
   *
   * @param index
   *          the index of the button to select
   */
  @Override
  public void setSelectedIndex(int index) {
    if ((index > -1) && (index < buttons.size())) {
      setSelectedButton(buttons.get(index));
    }
  }

  /**
   * Set the painter to use to paint a selected button's pressed state
   *
   * @param selectedPainter
   *          the painter
   */
  @Override
  public void setSelectedPainter(iPlatformPainter selectedPainter) {
    this.selectedPainter = selectedPainter;
  }

  /**
   * @param color
   *          the separatorLineColor to set
   */
  @Override
  public void setSeparatorLineColor(UIColor color) {
    this.separatorLineColor    = color;
    this.separatorLineColorSet = true;
  }

  /**
   * Sets whether to show only icons or to show both icons and text
   *
   * @param icon
   *          true if only icons will be shown; false otherwise
   */
  @Override
  public void setShowIconsOnly(boolean icon) {
    showIconsOnly = icon;

    int len = buttons.size();

    for (int i = 0; i < len; i++) {
      iButton b = buttons.get(i);

      b.setShowIconOnly(icon);
    }
  }

  /**
   * Get the action at the specified index
   *
   * @param index
   *          the index
   * @return the action at the specified index
   */
  @Override
  public UIAction getAction(int index) {
    return buttons.get(index).getAction();
  }

  /**
   * Get the back button
   *
   * @return the back button or null if the panel is not hierarchical
   */
  @Override
  public iActionComponent getBackButton() {
    if ((backButton == null) && isHiearchical()) {
      backButton = createBackButton(backIcon);
      backButton.setFocusable(false);

      if (getForeground() != null) {
        backButton.setForeground(getForeground());
      }

      if (getFont() != null) {
        backButton.setFont(getFont());
      }

      if (getComponentPainterEx() != null) {
        backButton.setComponentPainter(getComponentPainterEx());
      }

      backButton.setBorder(new UICompoundBorder(getBorder(), buttonBorder));
      backButton.setIconPosition(iconPosition);
    }

    return backButton;
  }

  /**
   * Get the button at the specified index
   *
   * @param index
   *          the index
   * @return the button at the specified index
   */
  @Override
  public iActionComponent getButton(int index) {
    return buttons.get(index);
  }

  /**
   * Gets the icon position for button icons
   *
   * @return the icon position
   */
  @Override
  public IconPosition getIconPosition() {
    return iconPosition;
  }

  /**
   * Gets the panel type
   *
   * @return the panel type
   */
  @Override
  public PanelType getPanelType() {
    return panelType;
  }

  /**
   * Gets the painter that is used to paint a button's pressed state
   *
   * @return the painter
   */
  public iPlatformPainter getPressedPainter() {
    return pressedPainter;
  }

  @Override
  public UIAction getSelectedAction() {
    iActionComponent b = getSelectedButton();

    return (b == null)
           ? null
           : ((iButton) b).getAction();
  }

  /**
   * Gets the currently selected button. For hierarchical panels it is always
   * the last button on the panel.
   *
   * @return the currently selected button.
   */
  @Override
  public iActionComponent getSelectedButton() {
    if (panelType == PanelType.HIERARCHICAL) {
      int len = buttons.size();

      return (len == 0)
             ? null
             : buttons.get(len - 1);
    }

    return selectedButton;
  }

  /**
   * Get the font that is used for the selected button
   *
   * @return the font that is used for the selected button
   */
  public UIFont getSelectedFont() {
    return selectedFont;
  }

  /**
   * Get the foreground color that is used for the selected button
   *
   * @return the foreground color that is used for the selected button
   */
  public UIColor getSelectedForeground() {
    return selectedForeground;
  }

  /**
   * Gets the index of the currently selected button.
   *
   * @return the index of the currently selected button.
   */
  @Override
  public int getSelectedIndex() {
    if (panelType == PanelType.HIERARCHICAL) {
      return buttons.size() - 1;
    }

    if (selectedButton != null) {
      return buttons.indexOf(selectedButton);
    }

    return -1;
  }

  /**
   * Gets the painter that is used to paint a selected button's pressed state
   *
   * @return the painter
   */
  public iPlatformPainter getSelectedPainter() {
    return selectedPainter;
  }

  /**
   * @return the separatorLineColor
   */
  public UIColor getSeparatorLineColor() {
    return separatorLineColor;
  }

  /**
   * Gets whether the action at the specified index is enabled
   *
   * @param index
   *          the index
   * @return true if it is enabled; false if it is disabled
   */
  @Override
  public boolean isActionEnabled(int index) {
    UIAction a = getAction(index);

    return (a == null)
           ? false
           : a.isEnabled();
  }

  /**
   * @return the alwaysFireAction
   */
  @Override
  public boolean isAlwaysFireAction() {
    return alwaysFireAction;
  }

  /**
   * Tests whether the panel is a hierarchical type panel
   *
   * @return true if it is; false otherwise
   */
  @Override
  public boolean isHiearchical() {
    return panelType == PanelType.HIERARCHICAL;
  }

  /**
   * Gets whether to show only icons or to show both icons and text
   *
   * @return true if only icons will be shown; false otherwise
   */
  @Override
  public boolean isShowIconsOnly() {
    return showIconsOnly;
  }

  /**
   * Tests whether the panel is a toggle type panel
   *
   * @return true if it is; false otherwise
   */
  @Override
  public boolean isToggle() {
    return panelType == PanelType.TOGGLE;
  }

  protected abstract iButton createBackButton(iPlatformIcon icon);

  protected abstract iButton createButton(UIAction a);

  protected abstract iButton createButton(String text, iPlatformIcon icon);

  protected void initializePanel(iPlatformIcon backIcon) {
    this.backIcon = backIcon;
    setComponentPainter(new UIComponentPainter());
    setBorder(new UILineBorder(aUILineBorder.getDefaultLineColor(), ScreenUtils.PLATFORM_PIXELS_1,
                               ScreenUtils.PLATFORM_PIXELS_6));
    setBackground(ColorUtils.getBackground());
  }

  protected void layout(float width, float height) {
    int      len     = getComponentCount();
    float    biggest = 0;
    float    w       = 0;
    UIInsets in      = getInsetsEx();

    if (in != null) {
      width  -= (in.left + in.right);
      height -= (in.top + in.bottom);
    }

    iPlatformComponent c;
    int                count = 0;

    for (int i = 0; i < len; i++) {
      c = getComponentAt(i);

      if (!c.isVisible()) {
        continue;
      }

      count++;

      CellConstraints cc = getMeasuredCellConstraints(c, false);

      w       += cc.gridWidth;
      biggest = Math.max(biggest, cc.gridWidth);
    }

    float sameSize = width / count;
    float x        = (in == null)
                     ? 0
                     : in.left;
    float y        = (in == null)
                     ? 0
                     : in.top;

    if (buttonsSameSize) {
      for (int i = 0; i < len; i++) {
        c = getComponentAt(i);

        if (!c.isVisible()) {
          continue;
        }

        c.setBounds(x, y, sameSize, height);
        x += sameSize;
      }
    } else {
      float dx = (width - w) / len;

      for (int i = 0; i < len; i++) {
        c = getComponentAt(i);

        if (!c.isVisible()) {
          continue;
        }

        CellConstraints cc = getMeasuredCellConstraints(c, false);

        w = cc.gridWidth + dx;
        c.setBounds(x, y, w, height);
        x += w;
      }
    }

    iPlatformComponentPainter cp = getComponentPainter();

    if ((cp == null) || (cp.getBackgroundPainter() == null)) {
      if (cp == null) {
        cp = new UIComponentPainter();
        setComponentPainter(cp);
      }

      UIBackgroundPainter bp = (UIBackgroundPainter) UIBackgroundPainter.BGCOLOR_GRADIENT_PAINTER_MID.clone();

      bp.setBackgroundColor(getBackground());
      cp.setBackgroundPainter(bp, false);
    }
  }

  /**
   * Paints a button border
   *
   * @param g
   *          the graphics
   * @param x
   *          the x-position
   * @param y
   *          the y-position
   * @param w
   *          the width
   * @param h
   *          the height
   */
  protected void paintBorder(iPlatformGraphics g, float x, float y, float w, float h, boolean hiearchical) {
    float p1 = ScreenUtils.PLATFORM_PIXELS_1;

    w -= p1;

    if (hiearchical) {
      float m  = h / 2 - p1;
      float x1 = x + w - m;
      float x2 = x + w;

      g.drawLine(x1, y, x2, y + m);
      g.drawLine(x2, y + m, x2, y + h - m - p1);
      g.drawLine(x1, y + h - p1, x2, y + h - m - p1);
    } else {
      g.drawLine(x + w, y, x + w, y + h);
    }
  }

  protected void paintBorder(iPlatformGraphics g, int x, int y, int w, int h, boolean hiearchical) {
    float p1 = ScreenUtils.PLATFORM_PIXELS_1;

    w -= p1;

    if (hiearchical) {
      float m  = h / 2 - p1;
      float x1 = x + w - m;
      float x2 = x + w;

      g.drawLine(x1, y, x2, y + m);
      g.drawLine(x2, y + m, x2, y + h - m - p1);
      g.drawLine(x1, y + h - p1, x2, y + h - m - p1);
    } else {
      g.drawLine(x + w, y, x + w, y + h);
    }
  }

  protected void paintPanel(iPlatformGraphics g, UIRectangle rect) {
    iPlatformShape ps = getPressedShape();
    iPlatformShape ss = null;

    if (isToggle()) {
      ss = getPressedShape(selectedButton);
    }

    if ((ss != null) || (ps != null)) {
      if (ss != null) {
        paintShape(g, ss, selectedPainter);
      }

      if (ps != null) {
        paintShape(g, ps, pressedPainter);
      }
    }

    int           len = buttons.size();
    float         h   = rect.height;
    float         y   = ScreenUtils.PLATFORM_PIXELS_1;
    float         x;
    iButton       b;
    final UIColor c = (separatorLineColor == null)
                      ? aUILineBorder.getDefaultLineColor()
                      : separatorLineColor;

    g.setColor(c);
    x = -1;

    final boolean hiearchical = isHiearchical();

    for (int i = 0; i < len; i++) {
      b = buttons.get(i);

      if (!b.isVisible()) {
        continue;
      }

      if (x > -1) {
        paintBorder(g, x, y, b.getX() - x, h, hiearchical);
        x = b.getX();
      } else {
        x = b.getX();
      }
    }
  }

  /**
   * Paints a shape
   *
   * @param g
   *          the graphics
   * @param shape
   *          the shape
   * @param p
   *          the painter (can be null)
   */
  protected void paintShape(iPlatformGraphics g, iPlatformShape shape, iPlatformPainter p) {
    if (p == null) {
      p = pressedPainter;
    }

    if (p == null) {
      UIBackgroundPainter bp = (UIBackgroundPainter) UIBackgroundPainter.BGCOLOR_GRADIENT_PAINTER_DK_DK.clone();

      bp.setGradientDirection(Direction.VERTICAL_BOTTOM);
      bp.setBackgroundColor(getBackground());
      p = pressedPainter = new UISimpleBackgroundPainter(ColorUtils.getControlDkShadow());
    }

    UIRectangle    r  = shape.getBounds();
    iPlatformPaint pp = p.getPaint(r.width, r.height);

    g.saveState();

    if (pp != null) {
      g.setPaint(pp);
      g.fillShape(shape, 0, 0);
    } else {
      g.clipShape(shape);
      p.paint(g, 0, 0, getWidth(), getHeight(), iPainter.UNKNOWN);
    }

    g.restoreState();
  }

  protected void populateSizeCache() {
    iPlatformComponent c;
    int                len = getComponentCount();

    for (int i = 0; i < len; i++) {
      c = getComponentAt(i);

      if (!c.isVisible()) {
        continue;
      }

      Object o = c.getClientProperty(iConstants.RARE_CONSTRAINTS_PROPERTY);

      if (!(o instanceof UIDimension)) {
        o = new UIDimension();
        c.putClientProperty(iConstants.RARE_CONSTRAINTS_PROPERTY, o);
      }

      c.getPreferredSize((UIDimension) o);
    }

    cacheInvalidated = false;
  }

  protected void repaintPanel() {
    repaint();
  }

  @Override
  protected void getMinimumSizeEx(UIDimension size) {
    getPreferredSizeEx(size, 0);
  }

  protected iButton getNextVisible(int index) {
    int     len = buttons.size();
    iButton b;

    for (int i = index + 1; i < len; i++) {
      b = buttons.get(i);

      if (b.isVisible()) {
        return b;
      }
    }

    return null;
  }

  @Override
  protected void getPreferredSizeEx(UIDimension size, float maxWidth) {
    if (cacheInvalidated) {
      populateSizeCache();
    }

    float              height = 0;
    float              width  = 0;
    iPlatformComponent c;
    int                len     = getComponentCount();
    int                count   = 0;
    float              biggest = 0;
    float              p1      = ScreenUtils.PLATFORM_PIXELS_1;

    for (int i = 0; i < len; i++) {
      c = getComponentAt(i);

      if (!c.isVisible()) {
        continue;
      }

      count++;

      CellConstraints cc = getMeasuredCellConstraints(c, false);

      width   += cc.gridWidth + p1;
      height  = Math.max(height, cc.gridHeight);
      biggest = Math.max(biggest, cc.gridWidth + p1);
    }

    if (buttonsSameSize) {
      width = count * biggest;
    }

    size.width  = width;
    size.height = height;
  }

  /**
   * Gets the currently pressed shape
   *
   * @return the currently pressed shape
   */
  protected iPlatformShape getPressedShape() {
    int     len  = buttons.size();
    iButton comp = null;

    if (isHiearchical() && ((iButton) getBackButton()).isPressed()) {
      comp = (iButton) getBackButton();
    }

    if ((selectedButton == comp) && (comp != null)) {
      return null;
    }

    if ((len == 1) || (comp != null)) {
      if (comp == null) {
        comp = buttons.get(0);

        if (!comp.isPressed()) {
          return null;
        }
      }

      return getPressedShape(comp);
    }

    iPlatformShape p  = null;
    float          p1 = ScreenUtils.PLATFORM_PIXELS_1;

    for (int i = 0; i < len; i++) {
      comp = buttons.get(i);

      if (comp.isPressed()) {
        if (!isHiearchical()) {
          return getPressedShape(comp);
        }

        float   x  = comp.getX() - p1;
        float   y  = 1;
        float   w  = comp.getWidth();
        float   h  = getHeight();
        float   m  = h / 2 - p1;
        float   x1 = x + w - m;
        float   x2 = x + w;
        float[] xp;
        float[] yp;

        if (i == 0) {
          xp = new float[] {
            x, x1, x2, x1, x, x
          };
          yp = new float[] {
            y, y, y + m, y + h, y + h, y
          };
          p  = newPolygon(xp, yp, xp.length);
        } else if (i == len - 1) {
          float xl = x - m - p1;

          x2 += 2;
          xp = new float[] {
            xl, x2, x2, xl, x, xl
          };
          yp = new float[] {
            y, y, y + h, y + h, y + m, y
          };
          p  = newPolygon(xp, yp, xp.length);
        } else {
          float xl = x - m - p1;

          xp = new float[] {
            xl, x1, x2, x1, xl, x,
            x - m    // - 1
          };
          yp = new float[] {
            y, y, y + m, y + h, y + h, y + m, y
          };
          p  = newPolygon(xp, yp, xp.length);
        }
      }
    }

    return p;
  }

  protected iPlatformShape getPressedShape(iButton c) {
    if (c == null) {
      return null;
    }

    UIRectangle r = c.getBounds(null);

    r.x      -= ScreenUtils.PLATFORM_PIXELS_1;
    r.y      = ScreenUtils.PLATFORM_PIXELS_1;
    r.height = getHeight();

    if (isFirstVisible(c)) {
      r.x = 0;
    }

    int     len = buttons.size();
    iButton b   = getNextVisible(indexOf(c));

    if (b != null) {
      r.width = b.getX() - r.x;
    } else if ((len > 0)) {
      r.width = getWidth() - r.x;
    }

    return PlatformHelper.createShape(r.x, r.y, r.width, r.height);
  }

  protected boolean isFirstVisible(iButton c) {
    int     len = buttons.size();
    iButton b;

    for (int i = 0; i < len; i++) {
      b = buttons.get(i);

      if (b.isVisible()) {
        return b == c;
      }
    }

    return false;
  }

  private iPlatformShape newPolygon(float[] xp, float[] yp, int length) {
    iPath p = PlatformHelper.createPath();

    p.moveTo(xp[0], yp[0]);

    for (int i = 1; i < length; i++) {
      p.lineTo(xp[i], yp[i]);
    }

    p.lineTo(xp[0], yp[0]);

    return p;
  }

  protected interface iButton extends iActionComponent {
    void resetBorder();

    @Override
    void setMargin(UIInsets buttonInsets);

    void setShowIconOnly(boolean icon);

    @Override
    UIAction getAction();

    UIRectangle getBounds(UIRectangle rect);

    @Override
    boolean isPressed();
  }


  protected abstract class aButton extends XPActionComponent implements iButton {
    protected boolean      iconOnly;
    protected boolean      selected;
    protected CharSequence text;

    @Override
    public void resetBorder() {}

    /**
     * Sets the selection state of the button
     *
     * @param selected
     *          true for selected; false otherwise
     */
    @Override
    public void setSelected(boolean selected) {
      if (isToggle()) {
        if (selected) {
          if (getSelectedButton() != null) {
            getSelectedButton().setSelected(false);
          }

          setSelectedButton(this);
        }
      }

      this.selected = selected;

      if (selected) {
        if (selectedIcon != null) {
          setIcon(selectedIcon);
        }
      } else {
        if (selectedIcon != null) {
          setIcon(icon);
        }
      }

      if (selectedForeground != null) {
        if (selected) {
          setForeground(selectedForeground);
        } else {
          setForeground(aNavigatorPanel.this.getForeground());
        }
      }

      if (selectedFont != null) {
        if (selected) {
          setFont(selectedFont);
        } else {
          setFont(aNavigatorPanel.this.getFont());
        }
      }

      repaint();
    }

    /**
     * Sets the selected icon for the button
     *
     * @param selectedIcon
     *          the selected icon for the button
     */
    @Override
    public void setSelectedIcon(iPlatformIcon selectedIcon) {
      this.selectedIcon = selectedIcon;
    }

    /**
     * Sets whether to show only icons or to show both icons and text
     *
     * @param icon
     *          true if only icons will be shown; false otherwise
     */
    @Override
    public void setShowIconOnly(boolean icon) {
      iconOnly = icon;
      super.setText(icon
                    ? null
                    : text);
    }

    /**
     * Sets the text for the button
     *
     * @param text
     *          the text to set
     */
    @Override
    public void setText(CharSequence text) {
      this.text = text;

      if (!iconOnly) {
        super.setText(text);
      }
    }

    /**
     * Gets the action for the button
     *
     * @return the action for the button
     */
    @Override
    public UIAction getAction() {
      return action;
    }

    @Override
    public UIDimension getPreferredSize() {
      UIDimension d = super.getPreferredSize();

      d.width += (d.height / 2 - 1);

      return d;
    }

    /**
     * Gets the selected icon for the button
     *
     * @return the selected icon for the button
     */
    @Override
    public iPlatformIcon getSelectedIcon() {
      return selectedIcon;
    }

    /**
     * Tests if the button is selected
     *
     * @return true if it is; false otherwise
     */
    @Override
    public boolean isSelected() {
      return selected;
    }

    /**
     * Creates a new instance
     *
     * @param text
     *          the button's text
     * @param icon
     *          the button's icon
     *
     * @param a
     *          the button's action
     */
    protected void initialize(String text, iPlatformIcon icon, UIAction a) {
      if (a != null) {
        setAction(a);
        selectedIcon = a.getSelectedIcon();

        if (selectedIcon instanceof UIImageIcon) {
          ((UIImageIcon) selectedIcon).isImageLoaded(aNavigatorPanel.this);
        }

        if (a.getIcon() instanceof UIImageIcon) {
          ((UIImageIcon) a.getIcon()).isImageLoaded(aNavigatorPanel.this);
        }

        action = a;
      }

      if (text != null) {
        setText(text);
      }

      if (icon != null) {
        if (icon instanceof UIImageIcon) {
          ((UIImageIcon) icon).isImageLoaded(aNavigatorPanel.this);
        }

        setIcon(icon);
      }
    }
  }
}
