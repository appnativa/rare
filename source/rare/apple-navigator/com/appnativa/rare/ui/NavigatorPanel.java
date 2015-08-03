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

import com.appnativa.rare.platform.apple.ui.iAppleLayoutManager;
import com.appnativa.rare.platform.apple.ui.util.AppleGraphics;
import com.appnativa.rare.platform.apple.ui.view.CustomButtonView;
import com.appnativa.rare.platform.apple.ui.view.ParentView;
import com.appnativa.rare.platform.apple.ui.view.View;
import com.appnativa.rare.ui.event.MouseEvent;
import com.appnativa.rare.ui.listener.iMouseListener;
import com.appnativa.rare.widget.iWidget;

import com.google.j2objc.annotations.Weak;

/**
 * A panel that manages a set of buttons
 *
 * @author Don DeCoteau
 */
public class NavigatorPanel extends aNavigatorPanel implements iAppleLayoutManager {

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

    p.setLayoutManager(this);
    p.panel = this;
    initializePanel(backIcon);
  }

  @Override
  public void layout(ParentView view, float width, float height) {
    layout(width, height);
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
    public UIDimension getPreferredSize(UIDimension size) {
      iPlatformIcon icon = getIcon();

      if (size == null) {
        size = new UIDimension();
      }

      size.setSize(icon.getIconWidth() + 4, icon.getIconHeight() + 4);

      return size;
    }

    @Override
    protected void perfromClick() {
      backup();
    }
  }


  class ButtonViewEx extends CustomButtonView implements iMouseListener {
    boolean isBack;
    boolean isPressed;
    boolean wasPressed;

    public ButtonViewEx() {
      super();
      this.setMouseListener(this);
      setMargin(0, 0, 0, 0);
    }

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {
      if (isToggle() && (selectedButton != null) && (selectedButton.getView() == this) &&!alwaysFireAction) {
        return;
      }

      isPressed  = true;
      wasPressed = true;

      if (isBack) {
        revalidate();
      } else {
        repaintPanel();
      }
    }

    @Override
    public void borderChanged(iPlatformBorder newBorder) {}

    @Override
    public void mouseReleased(MouseEvent e) {
      isPressed  = false;
      wasPressed = false;

      if (isBack) {
        revalidate();
      } else {
        repaintPanel();
      }
    }

    @Override
    public void paintBackground(AppleGraphics g, View v, UIRectangle rect) {
      if (!isBack) {
        return;
      }

      paintButton(g, rect, isPressed);

      return;
    }

    @Override
    public void pressCanceled(MouseEvent e) {}

    @Override
    public boolean wantsLongPress() {
      return false;
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
      setPaintHandlerEnabled(true);
    }

    @Override
    protected void actionPerformed() {
      ((Button) component).perfromClick();
      super.actionPerformed();
    }
  }


  static class NavigatorView extends ParentView {
    @Weak
    NavigatorPanel panel;

    public NavigatorView() {
      super(createAPView());
      setPaintHandlerEnabled(true);
      setUseMainLayerForPainter(false);
    }

    @Override
    public void paintBackground(AppleGraphics g, View v, UIRectangle rect) {
      super.paintBackground(g, v, rect);
      panel.paintPanel(g, rect);
    }

    @Override
    protected void disposeEx() {
      super.disposeEx();
      panel = null;
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
        super.doClick();
      } finally {
        alwaysFireAction = b;
      }
    }

    @Override
    public UIRectangle getBounds(UIRectangle rect) {
      return view.getBounds(rect);
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


  @Override
  public void invalidateLayout() {}
}
