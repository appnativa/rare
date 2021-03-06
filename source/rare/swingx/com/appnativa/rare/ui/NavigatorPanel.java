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

import com.appnativa.rare.platform.swing.ui.util.SwingHelper;
import com.appnativa.rare.platform.swing.ui.view.ButtonView;
import com.appnativa.rare.platform.swing.ui.view.JPanelEx;
import com.appnativa.rare.widget.iWidget;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.MouseListener;

import javax.swing.Icon;

/**
 * A panel that manages a set of buttons
 *
 * @author Don DeCoteau
 */
public class NavigatorPanel extends aNavigatorPanel {

  /**
   * Constructs a new instance
   *
   * @param context
   *          the context
   * @param backIcon
   *          the icon for the back button
   */
  public NavigatorPanel(iWidget context, iPlatformIcon backIcon) {
    super(new NavigatorView());

    NavigatorView p = (NavigatorView) view;

    p.panel = this;
    initializePanel(backIcon);
  }

  @Override
  protected iButton createBackButton(iPlatformIcon icon) {
    return new BackButton(icon);
  }

  @Override
  protected iButton createButton(UIAction a) {
    return new Button(a);
  }

  @Override
  protected iButton createButton(String text, iPlatformIcon icon) {
    return new Button(text, icon);
  }

  protected void disposExe() {
    NavigatorView p = (NavigatorView) view;

    if (p != null) {
      p.panel = null;
    }

    super.disposeEx();
  }

  @Override
  protected void repaintPanel() {
    repaint();
  }

  class BackButton extends Button {

    /**
     * Constructs a new instance
     */
    public BackButton(iPlatformIcon icon) {
      super(null, icon);
      ((ButtonViewEx) getView()).setAsBackButton();
    }

    @Override
    protected void getMinimumSizeEx(UIDimension size, float maxWidth) {
      super.getPreferredSizeEx(size, maxWidth);
    }

    @Override
    protected void getPreferredSizeEx(UIDimension size, float maxWidth) {
      iPlatformIcon icon = getIcon();

      if (size == null) {
        size = new UIDimension();
      }

      size.setSize(icon.getIconWidth() + 4, icon.getIconHeight() + 4);
    }

    @Override
    protected void perfromClick() {
      backup();
    }
  }


  class ButtonViewEx extends ButtonView implements MouseListener {
    boolean     isBack;
    boolean     isPressed;
    UIRectangle rect;
    boolean     wasPressed;

    public ButtonViewEx() {
      super();
      this.addMouseListener(this);
    }

    @Override
    public void mouseClicked(java.awt.event.MouseEvent e) {}

    @Override
    public void mouseEntered(java.awt.event.MouseEvent e) {}

    @Override
    public void mouseExited(java.awt.event.MouseEvent e) {}

    @Override
    public void mousePressed(java.awt.event.MouseEvent e) {
      if (!isEnabled()) {
        return;
      }

      if (isToggle() && (selectedButton != null) && (selectedButton.getView() == this) &&!alwaysFireAction) {
        return;
      }

      isPressed  = true;
      wasPressed = true;

      if (isBack) {
        invalidate();
      } else {
        repaintPanel();
      }
    }

    @Override
    public void mouseReleased(java.awt.event.MouseEvent e) {
      if (!isEnabled()) {
        return;
      }

      isPressed  = false;
      wasPressed = false;

      if (isBack) {
        invalidate();
      } else {
        repaintPanel();
      }
    }

    @Override
    public boolean isEnabled() {
      if (!NavigatorPanel.this.isEnabled()) {
        return false;
      }

      return super.isEnabled();
    }

    void setAsBackButton() {
      isBack = true;
    }

    @Override
    protected void fireActionPerformed(ActionEvent event) {
      ((Button) Component.fromView(this)).perfromClick();
      super.fireActionPerformed(event);
    }

    @Override
    protected void paintComponent(Graphics g) {
      if (isBack) {
        if (rect == null) {
          rect = new UIRectangle();
        }

        rect.width  = getWidth();
        rect.height = getHeight();
        paintButton(graphics, rect, isPressed);

        Icon ic = isEnabled()
                  ? getIcon()
                  : getDisabledIcon();

        if (ic != null) {
          int off = isPressed
                    ? 1
                    : 0;

          ic.paintIcon(this, g, (int) (rect.width - ic.getIconWidth()) / 2,
                       (int) (rect.height - ic.getIconHeight()) / 2 + off);
        }
      } else {
        super.paintComponent(g);
      }
    }
  }


  static class NavigatorView extends JPanelEx {
    UIRectangle    rect = new UIRectangle();
    NavigatorPanel panel;

    public NavigatorView() {
      super();
    }

    @Override
    public void getMinimumSize(UIDimension size, int maxWidth) {
      panel.getMinimumSize(size);
    }

    @Override
    public void getPreferredSize(UIDimension size, int maxWidth) {
      panel.getPreferredSize(size, maxWidth);
    }

    @Override
    protected void layoutContainerEx(int width, int height) {
      panel.layout(width, height);
    }

    @Override
    protected void paintComponent(Graphics g) {
      super.paintComponent(g);
      rect.width  = getWidth();
      rect.height = getHeight();
      panel.paintPanel(graphics, rect);
    }
  }


  /**
   * Class for buttons on the panel
   */
  protected class Button extends aButton {

    /**
     * Creates a new instance
     *
     * @param text
     *          the button's text
     */
    public Button(String text) {
      this(text, null, null);
    }

    /**
     * Creates a new instance
     *
     * @param a
     *          the button's action
     */
    public Button(UIAction a) {
      this(null, null, a);
    }

    /**
     * Creates a new instance
     *
     * @param text
     *          the button's text
     * @param icon
     *          the button's icon
     */
    public Button(String text, iPlatformIcon icon) {
      this(text, icon, null);
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
    protected Button(String text, iPlatformIcon icon, UIAction a) {
      setView(new ButtonViewEx());
      initialize(text, icon, a);
    }

    @Override
    public void doClick() {
      final boolean b = alwaysFireAction;

      try {
        alwaysFireAction = true;
        perfromClick();
      } finally {
        alwaysFireAction = b;
      }
    }

    @Override
    public UIRectangle getBounds(UIRectangle rect) {
      return SwingHelper.setUIRectangle(rect, view.getBounds());
    }

    /**
     * Tests if the button is pressed
     *
     * @return true if it is; false otherwise
     */
    @Override
    public boolean isPressed() {
      return ((ButtonViewEx) getView()).isPressed;
    }

    protected void perfromClick() {
      if (panelType == PanelType.HIERARCHICAL) {
        removeChildren(this);
      }

      if (isSelected() && isToggle() &&!alwaysFireAction) {
        return;
      }

      setSelected(!selected);
      repaintPanel();
    }
  }
}
