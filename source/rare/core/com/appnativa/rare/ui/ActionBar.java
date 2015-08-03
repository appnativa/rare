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
import com.appnativa.rare.spot.MenuBar;
import com.appnativa.rare.ui.MenuButtonWidget.iPopulateCallback;
import com.appnativa.rare.ui.RenderableDataItem.HorizontalAlign;
import com.appnativa.rare.ui.RenderableDataItem.VerticalAlign;
import com.appnativa.rare.ui.border.UICompoundBorder;
import com.appnativa.rare.ui.border.UIEmptyBorder;
import com.appnativa.rare.ui.border.UIMatteBorder;
import com.appnativa.rare.ui.event.ActionEvent;
import com.appnativa.rare.ui.event.iActionListener;
import com.appnativa.rare.ui.painter.UIComponentPainter;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.spot.SPOTSet;

import java.util.ArrayList;
import java.util.List;

public class ActionBar extends BorderPanel implements iActionListener, iMenuBarComponent, iPopulateCallback {
  static iPlatformBorder        defaultBorder;
  ArrayList<iActionComponent>   buttons    = new ArrayList<iActionComponent>();
  ArrayList<iPlatformComponent> separators = new ArrayList<iPlatformComponent>();
  UIInsets                      buttonInsets;
  iPlatformComponent            expansionComponent;
  boolean                       leftMenuButton;
  float                         menuButtonWidth;
  UIDimension                   menuSize;
  MenuButtonWidget              menuWidget;
  float                         oldWidth;
  PopupList                     popupList;
  UIPopupMenu                   popupMenu;
  float                         separatorWidth;
  boolean                       showButtonText;
  boolean                       showButtons;
  boolean                       showSeperators;
  iActionComponent              titleComponent;
  iPlatformComponent            titlePane;
  UIDimension                   titleSize;
  LinearPanel                   toolbarComponent;
  int                           visibleButtons;
  private boolean               showTitleText;

  public ActionBar(iWidget context, MenuBar cfg) {
    super("f:d,f:d:g,f:d", "c:d,c:d:g,c:d");

    if (Platform.isTouchDevice()) {
      sageMinHeight = "2ln";
    }

    titleComponent = PlatformHelper.createNakedButton(this, false, 0);
    titleComponent.setAlignment(HorizontalAlign.LEFT, VerticalAlign.CENTER);
    titleComponent.setWordWrap(true);
    titlePane        = titleComponent;
    menuWidget       = new MenuButtonWidget((context == null)
            ? null
            : context.getFormViewer(), this);
    showTitleText    = !"false".equals(cfg.spot_getAttribute("showApplicationTitle"));
    toolbarComponent = new LinearPanel(context, true, "c:d:g", null);
    menuWidget.setVisible(false);
    titleComponent.setVisible(false);

    if (cfg.fgColor.getValue() != null) {
      setForeground(ColorUtils.getColor(cfg.fgColor.getValue()));
    }

    float d = ScreenUtils.PLATFORM_PIXELS_2;

    titleComponent.setBorder(new UIEmptyBorder(d, d * 3, d, d));
    menuWidget.setBorder(new UIEmptyBorder(d, d * 3, 0, d));
    leftMenuButton = Platform.getUIDefaults().getBoolean("Rare.MenuBar.leftActionBarMenuButton", false);
    showButtons    = Platform.getUIDefaults().getBoolean("Rare.MenuBar.showActionBarButtons", true);
    showButtonText = Platform.getUIDefaults().getBoolean("Rare.MenuBar.showActionBarButtonsText",
            ScreenUtils.isLargeScreen());
    showSeperators = Platform.getUIDefaults().getBoolean("Rare.MenuBar.showActionBarSeparators",
            ScreenUtils.isLargeScreen());
    menuWidget.getContainerComponent().putClientProperty(iConstants.RARE_VALIGN_PROPERTY, VerticalAlign.CENTER);

    if (leftMenuButton) {
      setLeftView(menuWidget.getContainerComponent());
      setRightView(toolbarComponent);
      setCenterView(titleComponent);
    } else {
      setLeftView(titleComponent);
      setRightView(menuWidget.getContainerComponent());
      setCenterView(toolbarComponent);
    }

    String s = cfg.spot_getAttribute("actionButtonInsets");

    if (s != null) {
      buttonInsets = Utils.getInsets(s);
    } else {
      buttonInsets = (UIInsets) Platform.getUIDefaults().get("Rare.MenuBar.actionBar.Button.insets");
    }

    iPlatformComponent c = createSeparator();

    separatorWidth = c.getPreferredSize().width;
    separators.add(c);

    iPlatformIcon micon = null;

    if (cfg.icon.getValue() != null) {
      setTitleIcon(Platform.getContextRootViewer().getIcon(cfg.icon));
    }

    if (cfg.title.getValue() != null) {
      setTitle(cfg.title.getValue());
    }

    SPOTSet menu = cfg.getPopupMenu();

    if (menu != null) {
      s = menu.spot_getAttribute("icon");

      if ((s != null) && (s.length() > 0)) {
        micon = Platform.getContextRootViewer().getIcon(s, null);
      }
    }

    if (micon == null) {
      micon = Platform.getAppContext().getResourceAsIcon("Rare.icon.menu");
    }

    menuWidget.setIcon(micon);

    if (cfg.getBorders() == null) {
      setBorder(getDefaultBorder());
    }
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    int i = buttons.indexOf(e.getComponent());

    if (i != -1) {
      popupMenu.getMenuItem(i).fire(Platform.getWindowViewer());
    }
  }

  @Override
  public void add(UIMenuItem mi) {
    if (popupMenu == null) {
      popupMenu = new UIPopupMenu();
    }

    popupMenu.add(mi);

    iActionComponent c = createButton(mi);

    buttons.add(c);

    if (showSeperators && (buttons.size() > 1)) {
      separators.add(createSeparator());
    }
  }

  @Override
  public void addActionListener(iActionListener l) {
    titleComponent.addActionListener(l);
  }

  @Override
  public void addMenuItems(List<RenderableDataItem> list) {
    list.clear();

    int len   = buttons.size();
    int count = visibleButtons;

    for (int i = 0; i < len; i++) {
      UIMenuItem menu = popupMenu.getMenuItem(i);

      if (!menu.isVisible()) {
        continue;
      }

      if (count == 0) {
        list.add(menu);
      } else {
        count--;
      }
    }
  }

  @Override
  public void dispose() {
    if (menuWidget != null) {
      remove(menuWidget.getContainerComponent());
      menuWidget.dispose();
      menuWidget = null;
    }

    super.dispose();
    buttons.clear();
    separators.clear();
    titleComponent = null;

    if (titleComponent != null) {
      titleComponent.dispose();
      titleComponent = null;
    }

    if (expansionComponent != null) {
      expansionComponent.dispose();
      expansionComponent = null;
    }
  }

  public iPlatformComponent getSecondaryTitle() {
    return (titlePane == titleComponent)
           ? null
           : ((BorderPanel) titlePane).getCenterView();
  }

  @Override
  public iActionComponent getTitleComponent() {
    return titleComponent;
  }

  @Override
  public boolean hasTitleComponent() {
    return true;
  }

  public boolean isShowButtons() {
    return showButtons;
  }

  public boolean isShowButtonText() {
    return showButtonText;
  }

  public boolean isShowTitleText() {
    return showTitleText;
  }

  @Override
  public void remove(UIMenuItem mi) {
    if (popupMenu != null) {
      int i = popupMenu.indexOf(mi);

      if (i != -1) {
        buttons.remove(i);

        if (!separators.isEmpty()) {
          separators.remove(separators.size() - 1);
        }
      }
    }
  }

  @Override
  public void removeActionListener(iActionListener l) {
    titleComponent.removeActionListener(l);
  }

  @Override
  public void removeAll() {
    super.removeAll();
    buttons.clear();
    separators.clear();
  }

  @Override
  public void setBounds(float x, float y, float w, float h) {
    layout(w, h);
    super.setBounds(x, y, w, h);
  }

  @Override
  public void setFont(UIFont f) {
    super.setFont(f);

    for (iActionComponent b : buttons) {
      b.setFont(f);
    }

    if (titleComponent.getFontEx() == getFontEx()) {
      titleComponent.setFont(f);
    }
  }

  @Override
  public void setForeground(UIColor fg) {
    super.setForeground(fg);

    for (iActionComponent b : buttons) {
      b.setForeground(fg);
    }

    if (titleComponent.getForegroundEx() == getForegroundEx()) {
      titleComponent.setForeground(fg);
    }
  }

  public void setMenu(UIPopupMenu pm) {
    if (showButtons) {
      int len = pm.size();

      for (int i = 0; i < len; i++) {
        iActionComponent c = createButton(popupMenu.getMenuItem(i));

        buttons.add(c);

        if (i > 1) {
          separators.add(createSeparator());
        }
      }
    }
  }

  public void setSecondaryTitle(iPlatformComponent comp) {
    if (titlePane instanceof BorderPanel) {
      ((BorderPanel) titlePane).setCenterView(comp);
    } else {
      setLeftView(null);

      BorderPanel b = new BorderPanel();

      b.setLeftView(titleComponent);
      b.setCenterView(comp);

      if (buttons.isEmpty()) {
        setCenterView(b);
      } else {
        setLeftView(b);
      }

      b.setVisible(titleComponent.isVisible());
      titlePane = b;
    }

    reset();
  }

  public void setSecondaryTitle(iWidget widget) {
    setSecondaryTitle((widget == null)
                      ? null
                      : widget.getContainerComponent());
  }

  public void setShowButtons(boolean showButtons) {
    this.showButtons = showButtons;
    reset();
  }

  public void setShowButtonText(boolean showButtonText) {
    if (this.showButtonText != showButtonText) {
      this.showButtonText = showButtonText;

      int len = buttons.size();

      if (len > 0) {
        for (int i = 0; i < len; i++) {
          iActionComponent c    = buttons.get(i);
          UIMenuItem       menu = popupMenu.getMenuItem(i);

          setButtonText(c, menu);
        }
      }

      reset();
    }
  }

  public void setShowTitleText(boolean showTitleText) {
    this.showTitleText = showTitleText;
  }

  @Override
  public void setTitle(CharSequence title) {
    if (showTitleText) {
      if (getForegroundEx() != null) {
        titleComponent.setForeground(getForegroundEx());
      }

      if (getFontEx() != null) {
        titleComponent.setFont(getFontEx());
      }

      titleComponent.setVisible(true);
      titleComponent.setText(title);
      reset();
    }
  }

  public void setTitleAction(UIAction a) {
    titleComponent.setAction(a);
  }

  @Override
  public void setTitleIcon(iPlatformIcon icon) {
    titleComponent.setVisible(true);
    titleComponent.setIcon(icon);
    reset();
  }

  protected iActionComponent createButton(UIMenuItem menu) {
    iActionComponent   c  = PlatformHelper.createNakedButton(this, false, 0);
    UIComponentPainter cp = new UIComponentPainter();

    cp.setPainterHolder(PainterUtils.createToolBarButtonPaintHolder());
    c.setComponentPainter(cp);

    UIAction a = menu.getAction();

    if (a != null) {
      c.setAction(a);
    } else {
      c.setIcon(menu.getIcon());
      c.setDisabledIcon(menu.getDisabledIcon());
      c.addActionListener(this);
    }

    setButtonText(c, menu);

    if (buttonInsets != null) {
      c.setMargin(buttonInsets);
    }

    if (getForegroundEx() != null) {
      c.setForeground(getForegroundEx());
    }

    if (getFontEx() != null) {
      c.setFont(getFontEx());
    }

    return c;
  }

  @Override
  protected void getMinimumSizeEx(UIDimension size) {
    getPreferredSizeEx(size, 0);

    float h = size.height;

    menuWidget.getContainerComponent().getPreferredSize(size);
    size.height = Math.max(h, size.height);
  }

  @Override
  protected void getPreferredSizeEx(UIDimension size, float maxWidth) {
    float width  = 0;
    float height = 0;

    if (titlePane.isVisible()) {
      if (titleSize == null) {
        titlePane.getPreferredSize(size);
        titleSize = new UIDimension(size);
      } else {
        size.setSize(titleSize);
      }

      width  += size.width + ScreenUtils.PLATFORM_PIXELS_6;
      height = size.height;
    }

    int len = buttons.size();

    if (len > 0) {
      width += ((len - 1) * separatorWidth);

      for (int i = 0; i < len; i++) {
        buttons.get(i).getPreferredSize(size);
        width  += size.width;
        height = Math.max(height, size.height);
      }
    }

    size.width  = width;
    size.height = height;
  }

  protected void layout(float width, float height) {
    if ((visibleButtons == 0) || (Math.abs(width - oldWidth) > 5)) {
      oldWidth = width;

      UIDimension size = new UIDimension();
      UIInsets    in   = getInsetsEx();

      if (in != null) {
        width -= (in.left + in.right);
      }

      if (titlePane.isVisible()) {
        if (titleSize == null) {
          titlePane.getPreferredSize(size);
          titleSize = new UIDimension(size);
        } else {
          size.setSize(titleSize);
        }

        width -= size.width;
        width -= ScreenUtils.PLATFORM_PIXELS_6;
      }

      int count = 0;
      int len   = buttons.size();

      if (showButtons && (len > 0)) {
        for (int i = 0; i < len; i++) {
          UIMenuItem item = popupMenu.getMenuItem(i);

          if (!item.isVisible()) {
            continue;
          }

          iPlatformComponent c = buttons.get(i);

          c.getPreferredSize(size);
          width -= size.width;
          width -= separatorWidth;

          if (width < 0) {
            width += size.width;
            width += separatorWidth;

            break;
          }

          count++;
        }

        if (visibleButtons == count) {
          if (count == 0) {
            toolbarComponent.removeAll();
            menuWidget.setVisible(true);
          }

          return;
        }

        toolbarComponent.removeAll();

        if (count < len) {
          float lbwidth = size.width;

          if (menuSize == null) {
            menuWidget.getContainerComponent().getPreferredSize(size);
            menuSize = new UIDimension(size);
          } else {
            size.setSize(menuSize);
          }

          float mw = size.width;

          width -= mw;

          if (width < 0) {
            count--;
            width += lbwidth;
          }

          while((width < 0) && (count > 0)) {
            iPlatformComponent c    = buttons.get(--count);
            UIMenuItem         item = popupMenu.getMenuItem(count);

            if (!item.isVisible()) {
              continue;
            }

            c.getPreferredSize(size);
            width += size.width;
            width += separatorWidth;
          }
        }

        visibleButtons = count;

        if (!leftMenuButton && (width > 0)) {
          if (expansionComponent == null) {
            expansionComponent = PlatformHelper.createSpacerComponent(this);
          }

          toolbarComponent.addComponent(expansionComponent, "FILL:DEFAULT:GROW");
        }

        for (int i = 0; (i < len) && (count > 0); i++) {
          UIMenuItem item = popupMenu.getMenuItem(i);

          if (!item.isVisible()) {
            continue;
          }

          toolbarComponent.addComponent(buttons.get(i));

          if (showSeperators) {
            toolbarComponent.addComponent(separators.get(i));
          }

          count--;
        }
      } else {
        toolbarComponent.removeAll();
        visibleButtons = 0;
      }

      if (visibleButtons < len) {
        menuWidget.setVisible(true);
      } else {
        len = toolbarComponent.getComponentCount();

        if (showSeperators && (len > 0)) {
          toolbarComponent.remove(toolbarComponent.getComponentAt(len - 1));
        }

        menuWidget.setVisible(false);
      }
    }
  }

  protected void reset() {
    oldWidth       = 0;
    visibleButtons = 0;
    menuSize       = null;
    titleSize      = null;
    titlePane.revalidate();
    revalidate();
  }

  protected void setButtonText(iActionComponent c, UIMenuItem menu) {
    if (showButtonText) {
      c.setText(menu.getText());
      c.setToolTipText(menu.getTooltip());
    } else {
      c.setText("");

      CharSequence s = menu.getTooltip();

      if (s == null) {
        s = menu.getText();
      }

      c.setToolTipText(s);
    }
  }

  private iPlatformComponent createSeparator() {
    iPlatformComponent c = PlatformHelper.createSeparatorComponent(this);

    c.putClientProperty(iConstants.RARE_VALIGN_PROPERTY, VerticalAlign.FILL);

    return c;
  }

  public static iPlatformBorder getDefaultBorder() {
    if (defaultBorder == null) {
      UIMatteBorder mb = new UIMatteBorder(ScreenUtils.PLATFORM_PIXELS_1, 0, ScreenUtils.PLATFORM_PIXELS_1, 0, null);
      UIEmptyBorder eb = new UIEmptyBorder(ScreenUtils.PLATFORM_PIXELS_1);

      defaultBorder = new UICompoundBorder(mb, eb);
    }

    return defaultBorder;
  }
}
