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

package com.appnativa.rare.ui.tabpane;

import com.appnativa.rare.Platform;
import com.appnativa.rare.ui.BorderPanel;
import com.appnativa.rare.ui.FontUtils;
import com.appnativa.rare.ui.Location;
import com.appnativa.rare.ui.MenuButtonWidget;
import com.appnativa.rare.ui.MenuButtonWidget.iPopulateCallback;
import com.appnativa.rare.ui.RenderType;
import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.ui.RenderableDataItem.Orientation;
import com.appnativa.rare.ui.ScreenUtils;
import com.appnativa.rare.ui.UIAction;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.UIFont;
import com.appnativa.rare.ui.UIInsets;
import com.appnativa.rare.ui.UIRectangle;
import com.appnativa.rare.ui.WindowDeviceConfiguration;
import com.appnativa.rare.ui.border.UIBalloonBorder;
import com.appnativa.rare.ui.border.UICompoundBorder;
import com.appnativa.rare.ui.border.UIEmptyBorder;
import com.appnativa.rare.ui.border.UILineBorder;
import com.appnativa.rare.ui.border.UIMatteBorder;
import com.appnativa.rare.ui.iActionComponent;
import com.appnativa.rare.ui.iParentComponent;
import com.appnativa.rare.ui.iPlatformBorder;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.iPlatformIcon;
import com.appnativa.rare.ui.iPopup;
import com.appnativa.rare.ui.painter.PaintBucket;
import com.appnativa.rare.ui.painter.iPlatformComponentPainter;
import com.appnativa.rare.viewer.iContainer;
import com.appnativa.rare.viewer.iViewer;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.util.IdentityArrayList;

import java.util.List;

public abstract class aTabPaneLayout implements iPopulateCallback {
  protected int                       selectedTab   = -1;
  protected Location                  location      = Location.TOP;
  protected List<UIAction>            tabs          = new IdentityArrayList<UIAction>();
  protected boolean                   autoOrient    = true;
  UIEmptyBorder                       contentBorder = new UIEmptyBorder(0, 0, 0, 0);
  protected iPlatformComponentPainter componentPainter;
  protected iParentComponent          contentArea;
  protected UIFont                    font;
  protected BorderPanel               headerArea;
  protected iPlatformComponent        leadingView;
  protected iParentComponent          mainComponent;
  protected UIMatteBorder             matteBorder;
  protected int                       minTabHeight;
  protected MenuButtonWidget          moreButton;
  protected iPopup                    popupWindow;
  protected UIColor                   selectedTabBorderColor;
  protected int                       startTab;
  protected iPlatformBorder           tabAreaBorder;
  protected UIInsets                  tabAreaMargin;
  protected PaintBucket               tabAreaPainter;
  protected int                       tabHeight;
  protected aPlatformTabPainter       tabPainter;
  protected aTabStripComponent        tabStrip;
  protected int                       tabStripPadding;
  protected iPlatformComponent        trailingView;
  protected boolean                   updating;
  private boolean                     borderColorSet;
  private iContentManager             contentManager;
  private int                         minTabWidth;
  private iPlatformIcon               moreIcon;
  private boolean                     threeSidedMatteBorder;

  public aTabPaneLayout() {}

  public aTabPaneLayout(iContentManager cm) {
    contentManager = cm;
  }

  @Override
  public void addMenuItems(List<RenderableDataItem> list) {
    RenderableDataItem item;
    int                len   = tabs.size();
    int                start = tabPainter.getStartTab();
    int                end   = tabPainter.getEndTab();

    for (int i = 0; i < len; i++) {
      if ((i >= start) && (i < end)) {
        continue;
      }

      UIAction a = tabs.get(i);

      item = new RenderableDataItem(a.getActionText(), RenderableDataItem.TYPE_STRING, a.getIcon());

      if (!a.isEnabled()) {
        item.setDisabledIcon(a.getDisabledIcon());
        item.setEnabled(false);
      } else {
        item.setActionListener(a);
      }

      list.add(item);
    }
  }

  public UIAction addTab(String name, String title, iPlatformIcon icon) {
    UIAction a = new UIAction(name, title, icon);

    tabs.add(a);

    if ((tabPainter != null) &&!updating) {
      tabPainter.tabAdded(a);
    }

    return a;
  }

  public boolean checkOrientation() {
    return checkOrientation(null);
  }

  public boolean checkOrientation(Object newConfig) {
    if (popupWindow != null) {
      popupWindow.hidePopup();
      popupWindow = null;
    }

    return false;
  }

  public void checkOrientation(float width, float height) {
    if (autoOrient) {
      boolean wider = width > height;

      if (tabPainter instanceof BoxTabPainter) {
        switch(tabPainter.getIconPosition()) {
          case LEADING :
          case RIGHT :
          case RIGHT_JUSTIFIED :
          case LEFT :
          case TRAILING :
            wider = !wider;

            break;

          default :
            break;
        }
      }

      UIDimension size = ScreenUtils.getUsableScreenSize();
      float       cmax = Math.max(width, height);
      float       smax = Math.max(size.width, size.height);
      int         rot;

      if (cmax / smax > .75f) {    //if we take up more than 75% of the screen use the screens rotation
        rot = ScreenUtils.getRotationForConfiguration(null);
      } else {
        rot = ScreenUtils.getRotationForConfiguration(new WindowDeviceConfiguration(width, height));
      }

      handleOrientation(wider, rot);
    }
  }

  public UIAction closeTab(int pos) {
    if ((pos < 0) || (pos >= tabs.size())) {
      return null;
    }

    UIAction a = tabs.get(pos);

    if (selectedTab == pos) {
      selectedTab = -1;
      contentArea.removeAll();
    }

    removeTab(a);

    return a;
  }

  public void dispose() {
    if (tabs != null) {
      final int len = tabs.size();

      for (int i = 0; i < len; i++) {
        UIAction a = tabs.get(i);

        a.dispose();
      }

      tabs.clear();
    }

    // dispose of components that belong to the tab pane only
    if (contentArea != null) {
      contentArea.dispose();
    }

    if (headerArea != null) {
      headerArea.dispose();
    }

    if (tabStrip != null) {
      tabStrip.dispose();
    }

    if (tabPainter != null) {
      tabPainter.dispose();
    }

    if (componentPainter != null) {
      componentPainter.dispose();
    }

    if (moreButton != null) {
      moreButton.dispose();
    }

    contentArea      = null;
    headerArea       = null;
    contentManager   = null;
    trailingView     = null;
    tabStrip         = null;
    popupWindow      = null;
    mainComponent    = null;
    leadingView      = null;
    componentPainter = null;
    moreButton       = null;
  }

  public iPlatformComponent getContentArea() {
    return contentArea;
  }

  public iContentManager getContentManager() {
    return contentManager;
  }

  public iPlatformComponent getHeaderArea() {
    return headerArea;
  }

  public iPlatformComponent getMainComponent() {
    return mainComponent;
  }

  public int getSlectedTab() {
    return selectedTab;
  }

  public UIAction getTab(int pos) {
    return tabs.get(pos);
  }

  /**
   * @return the tabBorderColor
   */
  public UIColor getTabBorderColor() {
    return tabPainter.getTabBorderColor();
  }

  public int getTabCount() {
    return tabs.size();
  }

  public Object getTabLinkedData(int pos) {
    return tabs.get(pos).getLinkedData();
  }

  public int getTabMinHeight() {
    return tabPainter.getMinTabHeight();
  }

  public int getTabMinWidth() {
    return tabPainter.getMinTabWidth();
  }

  public aTabPainter getTabPainter() {
    return tabPainter;
  }

  public Location getTabPlacement() {
    return location;
  }

  public UIRectangle getTabStripBounds() {
    return new UIRectangle(headerArea.getX(), headerArea.getY(), headerArea.getWidth(), headerArea.getHeight());
  }

  public int indexOfLinkedData(Object data) {
    final int len = tabs.size();

    for (int i = 0; i < len; i++) {
      UIAction a = tabs.get(i);

      if (a.getLinkedData() == data) {
        return i;
      }
    }

    return -1;
  }

  public int indexOfTab(UIAction a) {
    return tabs.indexOf(a);
  }

  public void invalidatePainter() {
    if (tabPainter != null) {
      tabPainter.reset();
    }
  }

  public boolean isHorizontal() {
    return (location == Location.TOP) || (location == Location.BOTTOM);
  }

  public void removeTab(UIAction a) {
    if (tabs.remove(a)) {
      tabPainter.tabRemoved(a);
    }
  }

  public void setBoldSelectedTab(boolean bold) {
    tabPainter.setBoldSelectedTab(bold);
  }

  public void setComponents(iParentComponent main, BorderPanel header, aTabStripComponent strip,
                            iParentComponent contentArea) {
    mainComponent    = main;
    headerArea       = header;
    tabStrip         = strip;
    this.contentArea = contentArea;
    contentArea.setBorder(contentBorder);

    iWidget    v      = Platform.findWidgetForComponent(main);
    iContainer parent = (v == null)
                        ? Platform.getWindowViewer()
                        : v.getContainerViewer();

    moreButton = new MenuButtonWidget(parent, this) {
      @Override
      protected void getProposedPopupBounds(UIDimension contentSize, UIRectangle r) {
        int h = getContentArea().getHeight();

        h -= Math.max(moreButton.getHeight(), moreButton.getWidth()) * 2;

        int mh = getTabMinHeight();

        if (mh == 0) {
          mh = FontUtils.getDefaultLineHeight();
        }

        h                  = Math.max(h, mh * 5);
        contentSize.height = Math.min(contentSize.height, h);
        super.getProposedPopupBounds(contentSize, r);
      }
    };
    moreButton.setPopupScrollable(false);
    moreButton.setUseActionListenerAsEventSource(true);
    moreButton.setIcon(moreIcon);

    if (font != null) {
      moreButton.setFont(font);
    }

    if (!Platform.isTouchableDevice()) {
      moreButton.setFocusable(true);
      moreButton.setFocusPainted(true);
    }

    UIBalloonBorder b = new UIBalloonBorder(UILineBorder.getDefaultLineColor());

    b.setCornerArc(ScreenUtils.PLATFORM_PIXELS_4);
    b.setPeakSize(ScreenUtils.PLATFORM_PIXELS_10);
    moreButton.setPopupBorder(b);
  }

  public void setContentManager(iContentManager contentManager) {
    this.contentManager = contentManager;
  }

  public void setFont(UIFont f) {
    font = f;

    if (tabPainter != null) {
      tabPainter.setTabFont(f);
    }

    if (moreButton != null) {
      moreButton.setFont(font);
    }
  }

  /**
   * Set the icon to use for the more widget
   * @param moreIcon the icon
   */
  public void setMoreIcon(iPlatformIcon moreIcon) {
    this.moreIcon = moreIcon;

    if (moreButton != null) {
      moreButton.setIcon(moreIcon);
    }
  }

  public void setSelectedTab(int index) {
    if (selectedTab != index) {
      selectedTab = -1;

      UIAction a = tabs.get(index);

      setContent(contentManager.getContent(a));
      selectedTab = index;
      tabPainter.setSelectedTab(index);
      requestLayout();
    }
  }

  public int setSelectedTab(UIAction a) {
    int index = tabs.indexOf(a);

    setSelectedTab(index);

    return index;
  }

  public void setShowIconsOnTab(boolean show) {
    tabPainter.setShowIconsOnTab(show);
  }

  public void setTabAreaMargin(UIInsets margin) {
    tabAreaMargin = margin;

    UIInsets in = (margin == null)
                  ? null
                  : new UIInsets(margin);

    if (tabAreaBorder != null) {
      if (in == null) {
        in = tabAreaBorder.getBorderInsets((UIInsets) null);
      } else {
        in.addInsets(tabAreaBorder.getBorderInsets((UIInsets) null));
      }
    }

    if (in == null) {
      in = new UIInsets();
    }

    setTabAreaMarginEx(in);
  }

  public void setTabAreaPainter(PaintBucket pb) {
    if (pb != tabAreaPainter) {
      iPlatformBorder b = (pb == null)
                          ? null
                          : pb.getBorder();

      tabAreaPainter = pb;

      UIMatteBorder mb = findMatteBorder(b);

      tabAreaBorder = b;

      if (mb != null) {
        matteBorder = mb;
      }

      contentArea.setComponentPainter((pb == null)
                                      ? null
                                      : pb.getCachedComponentPainter());

      if (b != null) {
        if (matteBorder != null) {
          setTabAreaMarginEx(b.getBorderInsets((UIInsets) null));
        }

        if (selectedTabBorderColor != null) {
          tabPainter.setSelectedTabBorderColor(selectedTabBorderColor);
        } else if ((matteBorder != null) &&!borderColorSet && (matteBorder.getLineColor() != null)) {
          tabPainter.setSelectedTabBorderColor(matteBorder.getLineColor());
        }
      }

      if (tabPainter instanceof aBoxTabPainter) {
        ((aBoxTabPainter) tabPainter).setBorderPainted(b == null);
      }

      if (tabAreaMargin != null) {
        setTabAreaMargin(tabAreaMargin);
      }
    }

    requestLayout();
  }

  /**
   * @param color
   *          the tabBorderColor to set
   */
  public void setTabBorderColor(UIColor color) {
    tabPainter.setTabBorderColor(color);
    borderColorSet = true;

    if (matteBorder != null) {
      matteBorder.setLineColor(color);
    }
  }

  public void setTabLeadingView(iPlatformComponent c) {
    if (leadingView != null) {
      headerArea.remove(leadingView);
    }

    leadingView = c;

    switch(tabPainter.getLocation()) {
      case LEFT :
        headerArea.add(c, Location.BOTTOM);

        break;

      case RIGHT :
        headerArea.add(c, Location.TOP);

        break;

      default :
        headerArea.add(c, Location.LEFT);

        break;
    }
  }

  public void setTabMinHeight(int height) {
    if (minTabHeight != height) {
      minTabHeight = height;

      if (tabPainter != null) {
        tabPainter.setMinTabHeight(height);
        requestLayout();
      }
    }
  }

  public void setTabMinWidth(int width) {
    if (minTabWidth != width) {
      minTabWidth = width;

      if (tabPainter != null) {
        tabPainter.setMinTabWidth(width);
        requestLayout();
      }
    }
  }

  public void setTabPainter(aPlatformTabPainter painter) {
    if (tabPainter != null) {
      painter.setShowIconsOnTab(tabPainter.isShowIconsOnTab());
      painter.setBoldSelectedTab(tabPainter.isBoldSelectedTab());

      if (borderColorSet) {
        painter.setTabBorderColor(tabPainter.getTabBorderColor());
      }

      painter.setPosition(tabPainter.getPosition());
      tabPainter.dispose();
    } else if (location != Location.AUTO) {
      painter.setPosition(location);
    }

    if (minTabHeight > 0) {
      int h = minTabHeight = Math.max(painter.getMinTabHeight(), minTabHeight);

      painter.setMinTabHeight(h);
    }

    if (minTabWidth > 0) {
      int w = minTabWidth = Math.max(painter.getMinTabWidth(), minTabWidth);

      painter.setMinTabWidth(w);
    }

    if (tabs != null) {
      painter.setTabs(tabs);
    }

    painter.setTabFont(font);

    if (moreButton != null) {
      moreButton.setText(painter.isShowMoreIconText()
                         ? getMoreText()
                         : "");
      painter.setMoreButton((iActionComponent) moreButton.getDataComponent());
    }

    tabPainter = painter;
    tabStrip.setTabPainter(painter);
    tabStripPadding = painter.getTabsPadding();

    if (selectedTabBorderColor != null) {
      tabPainter.setSelectedTabBorderColor(selectedTabBorderColor);
    }

    if (matteBorder != null) {
      if (borderColorSet) {
        matteBorder.setLineColor(tabPainter.getTabBorderColor());
      } else if ((matteBorder.getLineColor() != null) && (selectedTabBorderColor == null)) {
        tabPainter.setSelectedTabBorderColor(matteBorder.getLineColor());
      }
    }

    updateHeaderMargin();
    requestLayout();
  }

  public void setTabPlacement(Location location) {
    autoOrient = false;
    setTabPlacementEx(location);
  }

  public void setTabTrailingView(iPlatformComponent c) {
    if (leadingView != null) {
      headerArea.remove(leadingView);
    }

    leadingView = c;

    switch(location) {
      case LEFT :
        headerArea.add(c, Location.TOP);

        break;

      case RIGHT :
        headerArea.add(c, Location.BOTTOM);

        break;

      default :
        headerArea.add(c, Location.RIGHT);

        break;
    }
  }

  public void setUpdating(boolean updating) {
    if (this.updating &&!updating) {
      this.updating = false;
      tabPainter.setTabs(tabs);
    } else {
      this.updating = updating;
    }
  }

  protected void adjustMoreButtonPopupLocation(UIRectangle bounds) {
    if (location == Location.LEFT) {
      UIDimension d = moreButton.getPreferredSize();

      if (bounds.x == 0) {
        bounds.x = d.height;
      }

      if (bounds.y == 0) {
        bounds.y = -d.width;
      }
    }
  }

  protected abstract void configureRotation(int degrees);

  protected boolean handleOrientation(boolean wider, int rotation) {
    Location loc;

    switch(rotation) {
      case 180 :
        loc = wider
              ? Location.LEFT
              : Location.TOP;

        break;

      case -90 :
      case 270 :
        loc = wider
              ? Location.LEFT
              : Location.BOTTOM;

        break;

      case 90 :
        loc = wider
              ? Location.RIGHT
              : Location.TOP;

        break;

      default :
        loc = wider
              ? Location.RIGHT
              : Location.BOTTOM;

        break;
    }

    if (loc != location) {
      setTabPlacementEx(loc);

      return true;
    }

    return false;
  }

  protected void requestLayout() {
    if (mainComponent != null) {
      mainComponent.revalidate();
      mainComponent.repaint();
    }
  }

  protected void setContent(iViewer v) {
    contentArea.removeAll();

    if (v != null) {
      contentArea.add(v.getContainerComponent());

      RenderType type = v.getRenderType();

      if (type != null) {
        setContentRenderType(type);
      }
    }
  }

  protected abstract void setContentRenderType(RenderType type);

  protected void setTabAreaMarginEx(UIInsets in) {
    contentBorder.setInsets(in);
  }

  protected void setTabPlacementEx(Location location) {
    if (this.location != location) {
      this.location = location;
      mainComponent.remove(headerArea);
      tabPainter.setPosition(location);

      Orientation orientation = Orientation.HORIZONTAL;

      if (mainComponent != headerArea) {
        switch(location) {
          case BOTTOM :
            if (threeSidedMatteBorder) {
              matteBorder.setInsets(1, 1, 0, 1);
            }

            mainComponent.add(headerArea, Location.BOTTOM);
            orientation = Orientation.HORIZONTAL;
            configureRotation(0);

            break;

          case LEFT :
            orientation = tabPainter.isHandlesRightLeftRotation()
                          ? Orientation.HORIZONTAL
                          : Orientation.VERTICAL_UP;
            configureRotation(tabPainter.isHandlesRightLeftRotation()
                              ? 0
                              : -90);

            if (threeSidedMatteBorder) {
              matteBorder.setInsets(1, 1, 1, 0);
            }

            mainComponent.add(headerArea, Location.LEFT);

            break;

          case RIGHT :
            orientation = tabPainter.isHandlesRightLeftRotation()
                          ? Orientation.HORIZONTAL
                          : Orientation.VERTICAL_DOWN;

            if (threeSidedMatteBorder) {
              matteBorder.setInsets(1, 0, 1, 1);
            }

            mainComponent.add(headerArea, Location.RIGHT);
            configureRotation(tabPainter.isHandlesRightLeftRotation()
                              ? 0
                              : 90);

            break;

          default :
            if (threeSidedMatteBorder) {
              matteBorder.setInsets(0, 1, 1, 1);
            }

            mainComponent.add(headerArea, Location.TOP);
            configureRotation(0);
            orientation = Orientation.HORIZONTAL;

            break;
        }
      }

      tabStrip.setOrientation(orientation);
      setTabAreaMargin(tabAreaMargin);

      if (trailingView != null) {
        setTabTrailingView(trailingView);
      }

      if (leadingView != null) {
        setTabLeadingView(leadingView);
      }

      updateHeaderMargin();
      requestLayout();
    }
  }

  protected void updateHeaderMargin() {
    if (!(headerArea.getBorder() instanceof UIEmptyBorder)) {
      return;
    }

    UIEmptyBorder b = (UIEmptyBorder) headerArea.getBorder();

    switch(location) {
      case BOTTOM :
        b.setInsets(0, 0, tabStripPadding, 0);

        break;

      case LEFT :
        b.setInsets(0, 0, 0, tabStripPadding);

        break;

      case RIGHT :
        b.setInsets(0, tabStripPadding, 0, 0);

        break;

      default :
        b.setInsets(tabStripPadding, 0, 0, 0);

        break;
    }
  }

  private UIMatteBorder findMatteBorder(iPlatformBorder b) {
    threeSidedMatteBorder = false;

    while(b instanceof UICompoundBorder) {
      b = ((UICompoundBorder) b).getOutsideBorder();
    }

    if (b instanceof UIMatteBorder) {
      UIMatteBorder mb = (UIMatteBorder) b;
      UIInsets      in = mb.getBorderInsets((UIInsets) null);

      switch(location) {
        case LEFT :
          if ((in.top == 1) && (in.left == 0) && (in.right == 1) && (in.bottom == 1)) {
            threeSidedMatteBorder = true;

            return mb;
          }

          break;

        case RIGHT :
          if ((in.top == 1) && (in.left == 1) && (in.right == 0) && (in.bottom == 1)) {
            threeSidedMatteBorder = true;

            return mb;
          }

          break;

        case BOTTOM :
          if ((in.top == 1) && (in.left == 1) && (in.right == 1) && (in.bottom == 0)) {
            threeSidedMatteBorder = true;

            return mb;
          }

          break;

        default :
          if ((in.top == 0) && (in.left == 1) && (in.right == 1) && (in.bottom == 1)) {
            threeSidedMatteBorder = true;

            return mb;
          }

          break;
      }

      if ((in.top == 0) && (in.left == 0) && (in.right == 0) && (in.bottom == 0)) {
        selectedTabBorderColor = mb.getLineColor();

        if (selectedTabBorderColor != null) {
          tabPainter.setSelectedTabBorderColor(selectedTabBorderColor);
        }
      }
    }

    return null;
  }

  private String getMoreText() {
    String text = Platform.getResourceAsString("Rare.runtime.text.moreTabs");

    if (text == null) {
      text = "More";
    }

    return text;
  }

  public interface iContentManager {
    iViewer getContent(UIAction a);
  }


  public interface iTabManager {
    iPlatformComponent getContent(UIAction a);
  }
}
