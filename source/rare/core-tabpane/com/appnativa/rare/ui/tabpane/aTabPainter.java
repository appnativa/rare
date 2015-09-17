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
import com.appnativa.rare.ui.ColorUtils;
import com.appnativa.rare.ui.FontUtils;
import com.appnativa.rare.ui.Location;
import com.appnativa.rare.ui.RenderableDataItem.HorizontalAlign;
import com.appnativa.rare.ui.RenderableDataItem.IconPosition;
import com.appnativa.rare.ui.RenderableDataItem.VerticalAlign;
import com.appnativa.rare.ui.ScreenUtils;
import com.appnativa.rare.ui.UIAction;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIColorHelper;
import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.UIFont;
import com.appnativa.rare.ui.UIFontHelper;
import com.appnativa.rare.ui.UIInsets;
import com.appnativa.rare.ui.UIRectangle;
import com.appnativa.rare.ui.border.UILineBorder;
import com.appnativa.rare.ui.iActionComponent;
import com.appnativa.rare.ui.iParentComponent;
import com.appnativa.rare.ui.iPlatformGraphics;
import com.appnativa.rare.ui.iTabDocument;
import com.appnativa.rare.ui.painter.PaintBucket;
import com.appnativa.rare.ui.painter.iPlatformComponentPainter;
import com.appnativa.rare.ui.painter.iPlatformPainter;

import com.google.j2objc.annotations.Weak;

import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Don DeCoteau
 */
public abstract class aTabPainter implements iTabPainter {
  protected int                                  cornerSize    = 0;
  protected int                                  endTab        = 0;
  protected int                                  minTabHeight  = 0;
  protected int                                  overlapOffset = 0;
  protected int                                  padding       = 0;
  protected int                                  selectedTab   = 0;
  protected int                                  startOffset   = 0;
  protected int                                  startTab      = 0;
  protected boolean                              alwaysShowMoreText;
  protected boolean                              boldSelectedTab;
  protected int                                  endOffset;
  @Weak
  protected iParentComponent                     header;
  protected int                                  minimumSize;
  @Weak
  protected iActionComponent                     moreButton;
  protected iPlatformComponentPainter            normalComponentPainter;
  protected float                                oldHeight;
  protected float                                oldWidth;
  protected int                                  preferredSize;
  protected IdentityHashMap<UIAction, iTabLabel> rendererMap;
  protected iPlatformComponentPainter            selectedComponentPainter;
  protected PaintBucket                          selectedPainter;
  protected UIColor                              selectedTabBorderColor;
  protected UIFont                               selectedTabFont;
  protected UIColor                              selectedTabForeground;
  protected boolean                              supportsUniformTabs;
  protected UIColor                              tabBorderColor;
  protected UIFont                               tabFont;
  protected UIColor                              tabForeground;
  protected UIColor                              tabDisabledForeground;
  protected int[]                                tabLayoutSizes;
  protected PaintBucket                          tabPainter;
  protected int[]                                tabSizes;
  protected List<UIAction>                       tabs;
  protected int                                  tabsHeight;
  private int                                    minTabWidth    = 0;
  protected UIInsets                             textInsets     = new UIInsets();
  protected boolean                              sizesDirty     = true;
  protected boolean                              showIconsOnTab = true;
  protected Location                             position       = Location.TOP;
  protected UIRectangle                          moreRect       = new UIRectangle();
  protected Location                             location       = Location.TOP;
  protected IconPosition                         iconPosition   = IconPosition.LEADING;
  private HorizontalAlign                        horizontalAlignment;
  private float                                  oldLayoutWidth;
  private int                                    smallestTabSize;

  public aTabPainter() {
    rendererMap            = new IdentityHashMap<UIAction, iTabLabel>();
    selectedTabBorderColor = Platform.getUIDefaults().getColor("Rare.TabPane.selectedTabBorderColor");
    tabBorderColor         = Platform.getUIDefaults().getColor("Rare.TabPane.tabBorderColor");
    tabForeground          = Platform.getUIDefaults().getColor("Rare.TabPane.tabForegroundColor");

    if (tabBorderColor == null) {
      tabBorderColor = UILineBorder.getDefaultLineColor();
    }

    if (selectedTabBorderColor == null) {
      selectedTabBorderColor = tabBorderColor;
    }

    Integer n = Platform.getUIDefaults().getPixels("Rare.TabPane.minimumTabHeight");

    if ((n != null) && (n > 0)) {
      minTabHeight = n;
    }

    n = Platform.getUIDefaults().getPixels("Rare.TabPane.minimumTabWidth");

    if ((n != null) && (n > 0)) {
      minTabWidth = n;
    }
  }

  public void checkForAndHandleMoreAction(float hitX, float hitY, float x, float y, float width, float height) {
    if (moreButton != null) {
      if (moreButton.getBounds().contains(hitX, hitY)) {
        moreButton.fireActionEvent();
        moreButton.repaint();
      }
    }
  }

  public void dispose() {
    moreButton             = null;
    header                 = null;
    normalComponentPainter = null;

    if (rendererMap != null) {
      disposeOfRenderers();
      rendererMap = null;
    }

    selectedComponentPainter = null;
    selectedPainter          = null;
    tabPainter               = null;

    if (tabs != null) {
      tabs.clear();
      tabs = null;
    }
  }

  protected void disposeOfRenderers() {
    if (rendererMap != null) {
      Iterator<iTabLabel> it = rendererMap.values().iterator();

      while(it.hasNext()) {
        iTabLabel l = it.next();

        if (l != null) {
          l.dispose();
        }
      }

      rendererMap.clear();
    }
  }

  public int findTab(float hitX, float hitY, float x, float y, float width, float height) {
    boolean vertical = isVerticalWhenFindingTabs();
    int     len      = tabs.size();

    if (len == 0) {
      return -1;
    }

    return findTab(hitX, hitY, x, y, width, height, startTab, endTab, vertical);
  }

  public void labelRemoved(iTabLabel label) {
    header.remove(label);
    label.setAction(null);
  }

  public void layout(float x, float y, float width, float height) {
    boolean vertical = ((location == Location.LEFT) || (location == Location.RIGHT)) && isHandlesRightLeftRotation();
    int     len      = tabs.size();

    if (len == 0) {
      return;
    }

    if ((oldHeight != height) || (oldWidth != width)) {
      oldHeight = height;
      oldWidth  = width;
      startTab  = 0;
      endTab    = len;
    }

    boolean all;

    if (vertical) {
      updateTabLayoutSizeForWidth(height);
      height -= (startOffset + endOffset + overlapOffset);
      all    = preferredSize < height;
    } else {
      width -= (startOffset + endOffset + overlapOffset);
      updateTabLayoutSizeForWidth(width);
      all = preferredSize < width;
    }

    if (all) {
      startTab = 0;
      endTab   = len;
      layoutTabs(x, y, width, height, 0, len, vertical);

      return;
    }

    if ((selectedTab < startTab) || (selectedTab >= endTab) ||!isRangeVisible(startTab, endTab, vertical
            ? height
            : width)) {
      startTab = Math.max(selectedTab, 0);
      endTab   = -1;

      int w = 0;
      int i;

      for (i = startTab; i < len; i++) {
        w += tabLayoutSizes[i];

        if (vertical) {
          if (w > height) {
            endTab = i;

            break;
          }
        } else {
          if (w > width) {
            endTab = i;

            break;
          }
        }
      }

      if (endTab == startTab) {
        endTab++;
      } else if (endTab == -1) {
        endTab = len;

        for (i = startTab - 1; i >= 0; i--) {
          w += tabLayoutSizes[i];

          if (vertical) {
            if (w > height) {
              break;
            }
          } else {
            if (w > width) {
              break;
            }
          }

          startTab--;
        }
      }
    }

    UIDimension size = getMoreSize(vertical, startTab, endTab);
    int         w    = getRangeWidth(startTab, endTab);

    if (vertical) {
      if ((w + size.height > height) && (endTab > 1)) {
        endTab--;
      }
    } else {
      if ((w + size.width + overlapOffset > width) && (endTab > 1)) {
        endTab--;

        if (selectedTab == endTab) {
          endTab++;
          startTab++;

          if (startTab == endTab) {
            startTab--;
          }
        }
      }
    }

    len = endTab - startTab;

    if ((len > 0) && supportsUniformTabs) {
      w = (int) Math.floor((vertical
                            ? height
                            : width) - getRangeWidth(startTab, endTab) - (vertical
              ? size.height
              : size.width));

      int pad = Math.max(0, w / len);

      for (int i = startTab; i < endTab; i++) {
        tabLayoutSizes[i] = tabSizes[i] + pad;
      }
    }

    layoutTabs(x, y, width, height, startTab, endTab, vertical);
  }

  public void paint(iPlatformGraphics g, float x, float y, float width, float height) {
    boolean vertical = isVeticalPaint();
    int     len      = tabs.size();

    if (len == 0) {
      return;
    }

    paintTabs(g, x, y, width, height, startTab, endTab, vertical);
  }

  @Override
  public void reset() {
    resetTabs();
  }

  public void resetSizes() {
    sizesDirty = true;
  }

  @Override
  public void tabAdded(UIAction a) {
    if ((tabPainter == null) || (selectedPainter == null)) {
      setupPainters();
    }

    iTabLabel r = configureRendererVisuals(a, false);

    r.setWordWrap(true);
    labelAdded(r);
    sizesDirty = true;
  }

  public int tabIndexAt(int x, int y) {
    int     width    = getTabsWidth();
    int     height   = getTabsHeight();
    boolean vertical = (location == Location.LEFT) || (location == Location.RIGHT);
    int     len      = tabs.size();
    int     tabSize;
    int     stripSize;

    if (vertical) {
      height    -= (startOffset + endOffset);
      stripSize = width;
      tabSize   = height / len;
    } else {
      width     -= (startOffset + endOffset);
      stripSize = height;
      tabSize   = width / len;
    }

    int n;

    switch(location) {
      case RIGHT :
        n = y;
        y = x;
        x = n;

        break;

      case LEFT :
        n = y;
        y = x;
        x = height - n;

        break;

      default :
    }

    n = indexForTabs(x, y, tabSize, stripSize, startTab, endTab, vertical);

    if ((n == -1) && (moreRect != null) && moreRect.contains(x, y)) {
      return tabs.size();
    }

    return n;
  }

  @Override
  public void tabRemoved(UIAction a) {
    iTabLabel r = getRenderer(a);

    if (r != null) {
      labelRemoved(r);
      sizesDirty = true;
    }
  }

  public void updateTabSizes() {
    if ((tabPainter == null) || (selectedPainter == null)) {
      setupPainters();
    }

    boolean   horizontal = isHorizontal() ||!isHandlesRightLeftRotation();
    final int len        = tabs.size();

    if ((tabSizes == null) || (tabSizes.length != len)) {
      tabSizes = new int[len];
    }

    if ((tabLayoutSizes == null) || (tabLayoutSizes.length != len)) {
      tabLayoutSizes = new int[len];
    }

    preferredSize  = 0;
    minimumSize    = 0;
    oldLayoutWidth = 0;

    UIDimension size   = new UIDimension();
    float       height = 0;
    int         n;
    UIFont      f = selectedTabFont;

    if (tabFont == null) {
      tabFont = FontUtils.getDefaultFont();
    }

    if (f == null) {
      if (boldSelectedTab) {
        f               = tabFont.deriveBold();
        selectedTabFont = f;
      } else {
        f = tabFont;
      }
    }

    for (int i = 0; i < len; i++) {
      UIAction  a  = tabs.get(i);
      iTabLabel l  = getRenderer(a);
      UIFont    of = l.getFont();

      l.setFont(f);
      l.getPreferredSize(size);
      n                 = (int) Math.ceil(horizontal
              ? Math.max(size.width, minTabWidth)
              : Math.max(size.height, minTabHeight));
      preferredSize     += n;
      tabSizes[i]       = n;
      tabLayoutSizes[i] = n;
      minimumSize       = Math.max(minimumSize, n);
      height            = Math.max(height, horizontal
              ? size.height
              : size.width);

      if (of != null) {
        l.setFont(of);
      }
    }

    if (moreButton != null) {
      UIFont of = moreButton.getFont();

      moreButton.setFont(f);
      moreButton.getPreferredSize(size);
      height = Math.max(height, horizontal
                                ? size.height
                                : size.width);

      if (of != null) {
        moreButton.setFont(of);
      }
    }

    if ((selectedComponentPainter != null) && (selectedComponentPainter.getBorder() != null)) {
      UIInsets in = selectedComponentPainter.getBorder().getBorderInsets((UIInsets) null);

      if (horizontal) {
        height        += in.top + in.bottom;
        preferredSize += in.left + in.right;
        minimumSize   += in.left + in.right;
      } else {
        height        += in.left + in.right;
        preferredSize += in.top + in.bottom;
        minimumSize   += in.top + in.bottom;
        ;
      }
    }

    tabsHeight = (int) Math.ceil(height + padding + getHeightExtra());

    if (tabsHeight % 2 == 1) {
      tabsHeight++;
    }

    preferredSize += startOffset + endOffset;
    minimumSize   *= 2;
    minimumSize   += startOffset + endOffset;
    tabsHeight    = Math.max(tabsHeight, minTabHeight);
    sizesDirty    = false;
  }

  /**
   * @param alwaysShowMoreText
   *          the alwaysShowMoreText to set
   */
  public void setAlwaysShowMoreText(boolean alwaysShowMoreText) {
    this.alwaysShowMoreText = alwaysShowMoreText;
  }

  @Override
  public void setBoldSelectedTab(boolean bold) {
    boldSelectedTab = bold;
  }

  @Override
  public void setEndOffset(int offset) {
    endOffset = offset;
  }

  public void setHeader(iParentComponent header) {
    this.header = header;
  }

  @Override
  public void setMinTabHeight(int height) {
    minTabHeight = height;
    resetTabs();
  }

  @Override
  public void setMinTabWidth(int minTabWidth) {
    this.minTabWidth = minTabWidth;
  }

  @Override
  public void setMoreButton(iActionComponent more) {
    if ((moreButton != null) && (header != null)) {
      header.remove(this.moreButton);
    }

    moreButton = more;

    if (more != null) {
      more.setIconPosition(iconPosition);
      setTextMargin(more);
    }

    if ((more != null) && (header != null)) {
      header.add(more);
    }
  }

  @Override
  public void setNormalPaint(PaintBucket pb) {
    tabPainter             = pb;
    normalComponentPainter = pb.getComponentPainter(true);

    UIFont f = pb.getFont();

    if ((f == null) && (tabFont == null)) {
      f = UIFontHelper.getDefaultFont();
    } else {
      f = tabFont;
    }

    tabFont = f;

    UIColor c = pb.getForegroundColor();

    if (c == null) {
      c = UIColorHelper.getForeground();
    }

    tabForeground = c;
    tabDisabledForeground          = Platform.getUIDefaults().getColor("Rare.TabPane.tabDisabledForegroundColor");
    if(tabDisabledForeground==null) {
      if(c==UIColorHelper.getForeground()) {
        tabDisabledForeground=ColorUtils.getDisabledForeground();
      }
      else {
        tabDisabledForeground=ColorUtils.getDisabledVersion(c);
      }
    }

    if ((selectedPainter == null) || (selectedPainter.getForegroundColor() == null)) {
      selectedTabForeground = c;
    }

    resetTabs();
  }

  @Override
  public void setPosition(Location position) {
    if (position != this.position) {
      this.position = position;
      setLocation(position);
    }
  }

  @Override
  public void setSelectedPaint(PaintBucket pb) {
    if (pb != selectedPainter) {
      selectedPainter          = pb;
      selectedComponentPainter = pb.getComponentPainter(true);
      selectedTabFont          = pb.getFont();
      selectedTabForeground    = pb.getForegroundColor();
    }
  }

  @Override
  public void setSelectedTab(int tab) {
    if (tabPainter == null) {
      setupPainters();
    }

    iTabLabel l;
    UIAction  a;

    if ((selectedTab > -1) && (selectedTab < tabs.size())) {
      a = tabs.get(selectedTab);
      l = rendererMap.get(a);

      if (l != null) {
        configureRendererVisuals(a, false);
        l.setIsSelectedTab(false);
        tabSelectionChanged(selectedTab, l, false);
      }
    }

    UIColor c = selectedTabForeground;

    if (c == null) {
      c = tabForeground;
    }

    selectedTab = tab;

    if ((selectedTab > -1) && (selectedTab < tabs.size())) {
      a = tabs.get(selectedTab);
      configureRendererVisuals(a, true);
      l = rendererMap.get(a);
      l.setIsSelectedTab(true);
      tabSelectionChanged(tab, l, true);
    }
  }

  /**
   * @param selectedTabBorderColor
   *          the selectedTabBorderColor to set
   */
  @Override
  public void setSelectedTabBorderColor(UIColor selectedTabBorderColor) {
    this.selectedTabBorderColor = selectedTabBorderColor;
  }

  @Override
  public void setShowIconsOnTab(boolean show) {
    showIconsOnTab = show;
  }

  public void setSmallestTabSize(int smallestTabSize) {
    this.smallestTabSize = smallestTabSize;
  }

  @Override
  public void setStartOffset(int offset) {
    startOffset = offset;
  }

  @Override
  public void setStartTab(int tab) {
    startTab = tab;
  }

  /**
   * @param tabBorderColor
   *          the tabBorderColor to set
   */
  @Override
  public void setTabBorderColor(UIColor tabBorderColor) {
    this.tabBorderColor = tabBorderColor;
  }

  /**
   * @param tabFont
   *          the tabFont to set
   */
  @Override
  public void setTabFont(UIFont tabFont) {
    this.tabFont = tabFont;
  }

  @Override
  public void setTabs(List<UIAction> tabs) {
    this.tabs = tabs;

    iTabLabel[] a = rendererMap.values().toArray(new iTabLabel[rendererMap.size()]);

    disposeOfRenderers();

    final int len = tabs.size();
    iTabLabel r;

    clearLabels();

    for (int i = 0; i < len; i++) {
      if (i < a.length) {
        r = a[i];
        r.setAction(tabs.get(i));
      } else {
        r = createRenderer(tabs.get(i));
      }

      labelAdded(r);
    }

    sizesDirty     = true;
    tabSizes       = new int[len];
    tabLayoutSizes = new int[len];
  }

  public void setTextHorizontalAlignment(HorizontalAlign horizontalAlignment) {
    this.horizontalAlignment = horizontalAlignment;
    updateTabVisuals();
  }

  @Override
  public UIColor getBackgroundColor() {
    return tabPainter.getBackgroundColor();
  }

  public iPlatformPainter getBackgroundPainter() {
    return null;
  }

  @Override
  public int getEndTab() {
    return endTab;
  }

  public UIColor getForegroundColor() {
    return tabPainter.getForegroundColor();
  }

  @Override
  public int getHeightExtra() {
    return isHorizontal()
           ? 1
           : 0;
  }

  /**
   * @return the iconPosition
   */
  @Override
  public IconPosition getIconPosition() {
    return iconPosition;
  }

  @Override
  public int getMinTabHeight() {
    return minTabHeight;
  }

  public int getMinTabWidth() {
    return minTabWidth;
  }

  @Override
  public void getMinimumSize(UIDimension size) {
    int w = getMinimumWidth();
    int h = getTabsHeight();

    if (isHorizontal()) {
      size.width  = w;
      size.height = h;
    } else {
      size.width  = h;
      size.height = w;
    }
  }

  public int getMinimumWidth() {
    if (sizesDirty) {
      updateTabSizes();
    }

    return minimumSize;
  }

  @Override
  public PaintBucket getNormalPaint() {
    return tabPainter;
  }

  public Location getPosition() {
    return position;
  }

  @Override
  public void getPreferredSize(UIDimension size) {
    int w = getTabsWidth() + getSmallestTabSize();
    int h = getTabsHeight();

    if (isHorizontal()) {
      size.width  = w;
      size.height = h;
    } else {
      size.width  = h;
      size.height = w;
    }
  }

  @Override
  public PaintBucket getSelectedPaint() {
    return selectedPainter;
  }

  @Override
  public int getSelectedTab() {
    return selectedTab;
  }

  /**
   * @return the selectedTabBorderColor
   */
  public UIColor getSelectedTabBorderColor() {
    return selectedTabBorderColor;
  }

  public int getSmallestTabSize() {
    if (smallestTabSize > 0) {
      return smallestTabSize;
    }

    int len = tabSizes.length;
    int w   = Integer.MAX_VALUE;
    ;

    for (int i = 0; i < len; i++) {
      w = Math.min(tabSizes[i], w);
    }

    return w;
  }

  public int getStartOffset() {
    return startOffset;
  }

  @Override
  public int getStartTab() {
    return startTab;
  }

  @Override
  public UIAction getTab(int index) {
    return tabs.get(index);
  }

  /**
   * @return the tabBorderColor
   */
  @Override
  public UIColor getTabBorderColor() {
    return tabBorderColor;
  }

  @Override
  public int getTabCount() {
    return tabs.size();
  }

  /**
   * @return the tabFont
   */
  public UIFont getTabFont() {
    return tabFont;
  }

  public int getTabsHeight() {
    if (sizesDirty) {
      updateTabSizes();
    }

    return tabsHeight;
  }

  @Override
  public int getTabsPadding() {
    return padding;
  }

  public int getTabsWidth() {
    if (sizesDirty) {
      updateTabSizes();
    }

    return preferredSize;
  }

  @Override
  public int getWidthExtra() {
    return isHorizontal()
           ? 0
           : 1;
  }

  @Override
  public boolean isBoldSelectedTab() {
    return boldSelectedTab;
  }

  public boolean isHandlesBottomRotation() {
    return false;
  }

  public boolean isHandlesRightLeftRotation() {
    return false;
  }

  /**
   * Returns whether the strip is positioned horizontally
   *
   * @return true if the strip is positioned horizontally; false otherwise
   */
  public boolean isHorizontal() {
    return (location == Location.TOP) || (location == Location.BOTTOM);
  }

  @Override
  public boolean isShowIconsOnTab() {
    return showIconsOnTab;
  }

  @Override
  public boolean isShowMoreIconText() {
    return alwaysShowMoreText;
  }

  protected void clearLabels() {
    if (header != null) {
      header.removeAll();

      if (moreButton != null) {
        header.add(moreButton);
      }
    }
  }

  protected iTabLabel configureRendererVisuals(UIAction a, boolean selected) {
    if (tabFont == null) {
      tabFont = tabPainter.getFont();

      if (tabFont == null) {
        tabFont = FontUtils.getDefaultFont();
      }
    }

    PaintBucket pb = selected
                     ? selectedPainter
                     : tabPainter;
    UIFont      f  = pb.getFont();

    if (f == null) {
      f = selected
          ? selectedTabFont
          : tabFont;
    }

    iTabLabel renderer = rendererMap.get(a);

    if (renderer == null) {
      renderer = createRenderer(a);
    }

    UIColor fg = pb.getForegroundColor();

    if (fg == null) {
      fg = ColorUtils.getForeground();
    }

    pb = ((iTabDocument) a.getLinkedData()).getTabColors();

    if (pb != null) {
      UIColor c = pb.getForegroundColor();

      if (c != null) {
        fg = c;
      }
      UIFont ff = pb.getFont();

      if (ff != null) {
        f = ff;
      }
    }
    if(!renderer.isEnabled()) {
      fg=tabDisabledForeground;
    }

    renderer.setForeground(fg);
    renderer.setMinHeight(minTabHeight);
    setTextMargin(renderer);
    renderer.setFont(f);
    renderer.setIconPosition(iconPosition);

    if (horizontalAlignment != null) {
      renderer.setAlignment(horizontalAlignment, VerticalAlign.AUTO);
    }

    return renderer;
  }

  protected iPlatformComponentPainter getUnselectedPainter(int tab) {
    PaintBucket               pb = ((iTabDocument) tabs.get(tab).getLinkedData()).getTabColors();
    iPlatformComponentPainter cp = (pb == null)
                                   ? null
                                   : pb.getCachedComponentPainter();

    return (cp == null)
           ? normalComponentPainter
           : cp;
  }

  protected abstract iTabLabel createNewRenderer(UIAction a);

  protected void setTextMargin(iActionComponent ac) {
    ac.setMargin(textInsets);
  }

  protected iTabLabel createRenderer(UIAction a) {
    iTabLabel tv = createNewRenderer(a);

    tv.setMinHeight(minTabHeight);
    setTextMargin(tv);
    tv.setIconPosition(iconPosition);

    if (horizontalAlignment != null) {
      tv.setAlignment(horizontalAlignment, VerticalAlign.AUTO);
    }

    rendererMap.put(a, tv);

    return tv;
  }

  protected int findTab(float hitX, float hitY, float x, float y, final float width, final float height,
                        final int start, final int end, final boolean vertical) {
    float w   = width;
    float h   = height;
    int   len = tabs.size();

    x += startOffset;

    for (int i = 0; i < len; i++) {
      if ((i < start) || (i >= end)) {
        continue;
      }

      w = tabLayoutSizes[i];

      if (vertical) {
        h = w;
        w = width;

        if ((hitY + overlapOffset >= y) && (hitY <= (y + h))) {
          if (isInsideShape(hitX, hitY, x, y, width, height, i)) {
            return i;
          }
        }
      } else {
        if ((hitX + overlapOffset >= x) && (hitX <= (x + w + overlapOffset))) {
          if (isInsideShape(hitX, hitY, x, y, width, height, i)) {
            return i;
          }
        }
      }

      if (vertical) {
        y += h;
      } else {
        x += w;
      }
    }

    return -1;
  }

  protected int indexForTabs(float x, float y, int tabSize, int stripSize, int start, int end, boolean vertical) {
    UIRectangle r;

    if (vertical) {
      r = new UIRectangle(0, startOffset, stripSize, tabSize);
    } else {
      r = new UIRectangle(startOffset, 0, tabSize, stripSize);
    }

    int w;

    for (int i = startTab; i < endTab; i++) {
      w = tabLayoutSizes[i];
      w -= overlapOffset;

      if (vertical) {
        r.height = w;
      } else {
        r.width = w;
      }

      if (r.contains(x, y)) {
        return i;
      }

      if (vertical) {
        r.y += w;
      } else {
        r.x += w;
      }
    }

    return -1;
  }

  protected void labelAdded(iTabLabel label) {
    header.remove(label);
    header.add(label);
  }

  protected void layoutMoreButton(iActionComponent button, float x, float y, float width, float height,
                                  boolean vertical) {
    button.setBounds(x, y, width - ScreenUtils.PLATFORM_PIXELS_2, height);
  }

  protected void layoutTab(iTabLabel tab, float x, float y, float width, float height, int index) {
    tab.setBounds(x, y, width, height);
  }

  protected void layoutTabs(float x, float y, final float width, final float height, final int start, final int end,
                            final boolean vertical) {
    float       w         = width;
    float       h         = height;
    final float right     = x + width;
    final float bottom    = y + height;
    int         len       = tabLayoutSizes.length;
    boolean     needsMore = false;

    for (int i = 0; i < len; i++) {
      iTabLabel node = getRenderer(tabs.get(i));

      if ((i < start) || (i >= end)) {
        node.setVisible(false);
        needsMore = true;

        continue;
      }

      node.setVisible(true);
      w = tabLayoutSizes[i];

      if (vertical) {
        h = w;
        w = width;
      }

      layoutTab(node, x, y, w, h, i);

      if (vertical) {
        y += h;
      } else {
        x += w;
      }
    }

    if (needsMore && (moreButton != null)) {
      moreButton.setVisible(true);

      if (!isShowMoreIconText()) {
        CharSequence s = moreButton.getText();

        if ((s != null) && (s.length() > 0)) {
          moreButton.setText("");
        }
      }

      layoutMoreButton(moreButton, x, y, right - x, bottom - y, vertical);
    } else {
      if (moreButton != null) {
        moreButton.setVisible(false);
      }
    }
  }

  protected void locationChanged() {
    if (moreButton != null) {
      moreButton.repaint();
    }
  }

  protected abstract void paintTab(iPlatformGraphics g, iTabLabel tab, float x, float y, float width, float height,
                                   int index);

  protected void paintTabs(iPlatformGraphics g, float x, float y, final float width, final float height,
                           final int start, final int end, final boolean vertical) {
    float w   = width;
    float h   = height;
    int   len = tabs.size();

    // find the total width/height
    for (int i = 0; i < len; i++) {
      if ((i < start) || (i >= end)) {
        continue;
      }

      w = tabLayoutSizes[i];

      if (vertical) {
        h = w;
        w = width;
      }

      if (vertical) {
        y += h;
      } else {
        x += w;
      }
    }

    // now paint backwards from the total width/height
    for (int i = len - 1; i > -1; i--) {
      iTabLabel node = getRenderer(tabs.get(i));

      if ((i < start) || (i >= end)) {
        continue;
      }

      w = tabLayoutSizes[i];

      if (vertical) {
        h = w;
        w = width;
      }

      if (vertical) {
        y -= h;
      } else {
        x -= w;
      }

      paintTab(g, node, x, y, w, h, i);
    }
  }

  protected void resetTabs() {
    if ((tabs != null) && (tabPainter != null)) {
      if (normalComponentPainter != null) {
        normalComponentPainter.setBorder(null);
      }

      for (UIAction a : tabs) {
        configureRendererVisuals(a, false);
      }

      updateTabSizes();
    }
  }

  protected void setupPainters() {
    if (tabPainter == null) {
      setNormalPaint(new PaintBucket(ColorUtils.getForeground(), ColorUtils.getBackground().brighter()));
      tabBorderColor = UILineBorder.getDefaultLineColor();
    }

    if (selectedPainter == null) {
      setSelectedPaint(new PaintBucket(ColorUtils.getForeground(), ColorUtils.getBackground()));
    }
  }

  protected void tabSelectionChanged(int tab, iTabLabel l, boolean selected) {}

  protected void updateTabVisuals() {
    final int len = tabs.size();

    for (int i = len - 1; i >= 0; i++) {
      configureRendererVisuals(tabs.get(i), i == selectedTab);
    }
  }

  protected void setLocation(Location location) {
    if (location != this.location) {
      this.location = location;
      sizesDirty    = true;
      startTab      = 0;
      endTab        = 0;
      locationChanged();
    }
  }

  protected Location getLocation() {
    return location;
  }

  protected UIDimension getMoreSize(boolean vertical, int start, int end) {
    if (((start == 0) && (end == tabs.size())) || (moreButton == null)) {
      return new UIDimension(0, 0);
    }

    UIDimension d = new UIDimension();

    moreButton.getPreferredSize(d);

    if (vertical) {
      d.height = (int) Math.max(d.height, getSmallestTabSize());
    } else {
      d.width = (int) Math.max(d.height, getSmallestTabSize());
    }

    return d;
  }

  protected UIFont getNormalFont() {
    UIFont f = tabFont;

    if (f == null) {
      f = FontUtils.getDefaultFont();
    }

    return f;
  }

  protected int getRangeWidth(int start, int end) {
    int         w  = 0;
    final int[] tw = tabSizes;

    for (int i = start; i < end; i++) {
      w += tw[i];
    }

    return w + startOffset + overlapOffset;
  }

  protected iTabLabel getRenderer(UIAction a) {
    iTabLabel tv = rendererMap.get(a);

    if (tv == null) {
      tv = createNewRenderer(a);
      rendererMap.put(a, tv);
    }

    return tv;
  }

  protected boolean isInsideShape(float hitX, float hitY, float x, float y, final float width, final float height,
                                  int index) {
    return true;
  }

  protected boolean isRangeVisible(int start, int end, float width) {
    return getRangeWidth(start, end) <= width;
  }

  protected boolean isVerticalWhenFindingTabs() {
    return ((location == Location.LEFT) || (location == Location.RIGHT)) && isHandlesRightLeftRotation();
  }

  protected boolean isVeticalPaint() {
    return ((location == Location.LEFT) || (location == Location.RIGHT)) && isHandlesRightLeftRotation();
  }

  private void updateTabLayoutSizeForWidth(float width) {
    if (oldLayoutWidth != width) {
      oldLayoutWidth = width;

      float w   = 0;
      int[] tw  = tabSizes;
      int[] tlw = tabLayoutSizes;
      int   len = tw.length;

      do {
        if (len == 1) {
          tabLayoutSizes[0] = (int) width;

          break;
        }

        if ((width < preferredSize) ||!supportsUniformTabs) {
          for (int i = 0; i < len; i++) {
            tlw[i] = tw[i];
          }

          break;
        }

        for (int i = 0; i < len; i++) {
          w = Math.max(w, tw[i]);
        }

        float ww = w * len;

        if (ww < width) {
          w = width / len;

          for (int i = 0; i < len; i++) {
            tlw[i] = (int) w;
          }
        } else {
          ww = (width - preferredSize) / (len - 1);

          for (int i = 0; i < len; i++) {
            if (tw[i] != w) {
              tlw[i] = tw[i] + (int) ww;
            } else {
              tlw[i] = tw[i];
            }
          }
        }
      } while(false);
    }
  }
}
