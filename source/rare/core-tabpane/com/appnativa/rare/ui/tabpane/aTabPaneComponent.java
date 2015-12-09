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
import com.appnativa.rare.iConstants;
import com.appnativa.rare.platform.PlatformHelper;
import com.appnativa.rare.ui.BorderPanel;
import com.appnativa.rare.ui.Location;
import com.appnativa.rare.ui.RenderType;
import com.appnativa.rare.ui.RenderableDataItem.HorizontalAlign;
import com.appnativa.rare.ui.UIAction;
import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.UIFont;
import com.appnativa.rare.ui.UIInsets;
import com.appnativa.rare.ui.UIRectangle;
import com.appnativa.rare.ui.effects.aAnimator;
import com.appnativa.rare.ui.effects.aSizeAnimation;
import com.appnativa.rare.ui.effects.aTransitionAnimator;
import com.appnativa.rare.ui.effects.iTransitionAnimator;
import com.appnativa.rare.ui.event.ActionEvent;
import com.appnativa.rare.ui.event.iActionListener;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.iPlatformIcon;
import com.appnativa.rare.ui.iTabDocument;
import com.appnativa.rare.ui.iTabDocument.iDocumentListener;
import com.appnativa.rare.ui.iTabPaneComponent;
import com.appnativa.rare.ui.listener.iTabPaneListener;
import com.appnativa.rare.ui.painter.PaintBucket;
import com.appnativa.rare.viewer.TabPaneViewer;
import com.appnativa.rare.viewer.iTabPaneViewer.Shape;
import com.appnativa.rare.viewer.iViewer;
import com.appnativa.rare.widget.iWidget;

public abstract class aTabPaneComponent extends BorderPanel
        implements aTabPaneLayout.iContentManager, iTabDocument.iDocumentListener, iTabDocument.iCanChangeCallback,
                   iTabPaneComponent {
  protected int                 changeIndex    = -1;
  protected int                 newTabPosition = -1;
  protected int                 selectedIndex  = -1;
  protected boolean             closingAll;
  protected iPlatformIcon       moreIcon;
  protected PaintBucket         normalTabPainter;
  protected PaintBucket         selectedTabPainter;
  protected RenderType          sizingAnchor;
  protected iActionListener     tabAction;
  protected aTabPaneLayout      tabsLayout;
  protected iTransitionAnimator transitionAnimator;
  private HorizontalAlign       horizontalAlignment;
  private boolean               tabStripsFloats;
  private long                  reloadTimeout;

  public aTabPaneComponent() {
    super();
  }

  public aTabPaneComponent(iWidget context) {
    super(context);
  }

  public aTabPaneComponent(Object view) {
    super(view);
  }

  @Override
  public long getReloadTimeout() {
    return reloadTimeout;
  }

  @Override
  public void setReloadTimeout(long reloadTimeout) {
    this.reloadTimeout = reloadTimeout;

    final int len = getTabCount();

    for (int i = 0; i < len; i++) {
      getDocumentAt(i).setReloadTimeout(reloadTimeout);
    }
  }

  @Override
  public int addTab(iTabDocument doc) {
    UIAction a = tabsLayout.addTab(doc.getTabName(), doc.getTitle(), doc.getIcon());

    a.setLinkedData(doc);
    a.setActionListener(tabAction);
    a.setSelectedIcon(doc.getAlternateIcon());

    if (listenerList != null) {
      TabDocument.fireTabOpened(doc, listenerList);
    }

    return tabsLayout.getTabCount() - 1;
  }

  @Override
  public void addTabPaneListener(iTabPaneListener l) {
    getEventListenerList().add(iTabPaneListener.class, l);
  }

  @Override
  public void beginTabsUpdate() {
    tabsLayout.setUpdating(true);
  }

  @Override
  public void canChange(iWidget context, iTabDocument doc) {
    int pos = changeIndex;

    changeIndex = -1;

    if (pos > -1) {
      setSelectedIndexEx(pos);
    }
  }

  @Override
  public void checkOrientation(Object newConfig) {
    if (tabsLayout.checkOrientation(newConfig)) {
      revalidate();
    }
  }

  @Override
  public void closeAll() {
    closingAll = true;

    try {
      closeAllBut(-1);
    } finally {
      closingAll = false;
    }
  }

  @Override
  public void closeAllBut(int pos) {
    final int len = getTabCount();

    for (int i = len - 1; i >= 0; i--) {
      if (i != pos) {
        closeTab(i);
      }
    }
  }

  @Override
  public void closeTab(int pos) {
    UIAction a = tabsLayout.closeTab(pos);

    if (a != null) {
      if (a.getLinkedData() != null) {
        TabDocument doc = (TabDocument) a.getLinkedData();

        doc.tabClosed();

        if (listenerList != null) {
          TabDocument.fireTabClosed(doc, listenerList);
        }

        doc.dispose();
      }

      a.dispose();
    }
  }

  @Override
  public void dispose() {
    if (tabsLayout != null) {
      tabsLayout.dispose();
    }

    super.dispose();
    tabsLayout         = null;
    tabAction          = null;
    selectedTabPainter = null;
    normalTabPainter   = null;

    if ((transitionAnimator != null) && transitionAnimator.isAutoDispose()) {
      transitionAnimator.dispose();
    }

    transitionAnimator = null;
  }

  @Override
  public void documentChanged(iTabDocument doc) {
    int pos = tabsLayout.indexOfLinkedData(doc);

    if (pos == -1) {
      return;
    }

    UIAction a = tabsLayout.getTab(pos);

    a.setIcon(doc.getIcon());
    a.setDisabledIcon(doc.getDisabledIcon());
    a.setActionText(doc.getTitle());
    a.setActionName(doc.getTabName());
    revalidate();
  }

  @Override
  public void endTabsUpdate() {
    tabsLayout.setUpdating(false);
  }

  @Override
  public void errorHappened(iWidget context, iTabDocument doc, Exception e) {
    changeIndex = -1;
    context.handleException(e);
  }

  @Override
  public int indexOfDocument(iTabDocument doc) {
    return tabsLayout.indexOfLinkedData(doc);
  }

  @Override
  public int indexOfTab(iPlatformIcon icon) {
    iTabDocument doc;
    int          len = getTabCount();

    for (int i = 0; i < len; i++) {
      doc = getDocumentAt(i);

      if ((doc.getIcon() == icon) || (doc.getDisabledIcon() == icon)) {
        return i;
      }
    }

    return -1;
  }

  @Override
  public int indexOfTab(String title) {
    iTabDocument doc;
    int          len = getTabCount();

    for (int i = 0; i < len; i++) {
      doc = getDocumentAt(i);

      if (title.equals(doc.getTitle())) {
        return i;
      }
    }

    return -1;
  }

  @Override
  public void removeTabPaneListener(iTabPaneListener l) {
    if (listenerList != null) {
      listenerList.remove(iTabPaneListener.class, l);
    }
  }

  @Override
  public void viewerRemoved(iViewer v) {}

  @Override
  public void viewerSet(iViewer v) {
    tabsLayout.invalidatePainter();
  }

  @Override
  public void setBoldSelectedTab(boolean bold) {
    tabsLayout.setBoldSelectedTab(bold);
  }

  public void setBottomButtonCount(int tabCount) {
    // TODO Auto-generated method stub
  }

  @Override
  public void setEnabledAt(int pos, boolean enable) {
    UIAction a = tabsLayout.getTab(pos);

    a.setEnabled(enable);
  }

  @Override
  public void setFont(UIFont f) {
    super.setFont(f);
    tabsLayout.setFont(f);
  }

  @Override
  public void setIconAt(int pos, iPlatformIcon icon) {
    UIAction    a   = tabsLayout.getTab(pos);
    TabDocument doc = (TabDocument) a.getLinkedData();

    a.setIcon(icon);
    doc.setIconEx(icon);
  }

  @Override
  public void setMoreTabsIcon(iPlatformIcon moreIcon) {
    this.moreIcon = moreIcon;

    if (tabsLayout != null) {
      tabsLayout.setMoreIcon(moreIcon);
    }
  }

  @Override
  public void setSelectedIndex(int index) {
    if (closingAll) {
      return;
    }

    int count = tabsLayout.getTabCount();

    if (index >= count) {
      Platform.debugLog("tab index out of bounds: " + index + ">=" + count);

      return;
    }

    if (selectedIndex != index) {
      tabWillChange(index);
    }
  }

  @Override
  public void setSelectedPainter(PaintBucket pb) {
    selectedTabPainter = pb;
    tabsLayout.getTabPainter().setSelectedPaint(pb);
    revalidate();
  }

  @Override
  public void setShowCloseButton(boolean b) {}

  @Override
  public void setShowCloseButtonOnTab(boolean b) {}

  @Override
  public void setShowIconsOnTab(boolean show) {
    tabsLayout.setShowIconsOnTab(show);
  }

  @Override
  public void setTabAreaMargin(UIInsets margin) {
    tabsLayout.setTabAreaMargin(margin);
  }

  @Override
  public void setTabAreaPainter(PaintBucket pb) {
    tabsLayout.setTabAreaPainter(pb);
  }

  @Override
  public void setTabLeadingComponent(iPlatformComponent c) {
    tabsLayout.setTabLeadingView(c);
  }

  @Override
  public void setTabMinHeight(int height) {
    tabsLayout.setTabMinHeight(height);
  }

  public void setTabMinWidth(int width) {
    tabsLayout.setTabMinWidth(width);
  }

  @Override
  public void setTabPainter(PaintBucket pb) {
    normalTabPainter = pb;
    tabsLayout.getTabPainter().setNormalPaint(pb);
    revalidate();
  }

  @Override
  public void setTabPlacement(Location location) {
    tabsLayout.setTabPlacement(location);
  }

  @Override
  public void setTabShape(Shape shape) {
    switch(shape) {
      case FLAT :
        tabsLayout.setTabPainter(new BasicTabPainter(0));

        break;

      case CHROME :
        tabsLayout.setTabPainter(new ChromeTabPainter());

        break;

      case OFFICE2003 :
        tabsLayout.setTabPainter(new Office2003TabPainter());

        break;

      case CUSTOM :
        break;

      case BOX :
        tabsLayout.setTabPainter(new BoxTabPainter());

        break;

      case BOX_STACKED :
        tabsLayout.setTabPainter(new BoxStackedTabPainter());
        tabsLayout.setTabPlacement(Location.BOTTOM);

        break;

      default :
        tabsLayout.setTabPainter(new BasicTabPainter(6));

        break;
    }

    if (normalTabPainter != null) {
      tabsLayout.getTabPainter().setNormalPaint(normalTabPainter);
    }

    if (selectedTabPainter != null) {
      tabsLayout.getTabPainter().setSelectedPaint(selectedTabPainter);
    }

    if (horizontalAlignment != null) {
      tabsLayout.getTabPainter().setTextHorizontalAlignment(horizontalAlignment);
    }

    revalidate();
  }

  @Override
  public void setTabTrailingComponent(iPlatformComponent c) {
    tabsLayout.setTabTrailingView(c);
  }

  public void setTextHorizontalAlignment(HorizontalAlign horizontalAlignment) {
    this.horizontalAlignment = horizontalAlignment;
    tabsLayout.getTabPainter().setTextHorizontalAlignment(horizontalAlignment);
  }

  @Override
  public void setTitleAt(int pos, String title) {
    UIAction    a   = tabsLayout.getTab(pos);
    TabDocument doc = (TabDocument) a.getLinkedData();

    a.setActionText(PlatformHelper.checkForHTML(title, getFont()));
    doc.setTitleEx(title);
  }

  @Override
  public void setTransitionAnimator(iTransitionAnimator animator) {
    transitionAnimator = animator;

    if ((animator != null) && (animator.getInAnimator() instanceof aSizeAnimation)) {
      sizingAnchor = ((aSizeAnimation) animator.getInAnimator()).getDiagonalAnchor();
    } else {
      sizingAnchor = null;
    }
  }

  public void setVisibleCount(int intValue) {}

  public int getBottomButtonCount() {
    return 0;
  }

  @Override
  public int getChangeIndex() {
    return changeIndex;
  }

  @Override
  public iPlatformComponent getComponent() {
    return this;
  }

  @Override
  public iViewer getContent(UIAction a) {
    TabDocument doc = (TabDocument) a.getLinkedData();

    return doc.getTabContent();
  }

  @Override
  public iTabDocument getDocumentAt(int index) {
    return (TabDocument) tabsLayout.getTabLinkedData(index);
  }

  @Override
  public iDocumentListener getDocumentListener() {
    return this;
  }

  @Override
  public iPlatformIcon getIconAt(int pos) {
    UIAction a = tabsLayout.getTab(pos);

    return a.getIcon();
  }

  @Override
  public iTabDocument getSelectedDocument() {
    return (selectedIndex < 0)
           ? null
           : getDocumentAt(selectedIndex);
  }

  @Override
  public int getSelectedIndex() {
    return selectedIndex;
  }

  @Override
  public int getTabCount() {
    return tabsLayout.getTabCount();
  }

  @Override
  public int getTabMinHeight() {
    return tabsLayout.getTabMinHeight();
  }

  @Override
  public Location getTabPlacement() {
    return tabsLayout.getTabPlacement();
  }

  @Override
  public UIRectangle getTabStripBounds() {
    return tabsLayout.getTabStripBounds();
  }

  @Override
  public iPlatformComponent getTabStrip() {
    return tabsLayout.tabStrip;
  }

  @Override
  public String getTitleAt(int pos) {
    TabDocument doc = (TabDocument) getDocumentAt(pos);

    return doc.getTitle();
  }

  @Override
  public iTransitionAnimator getTransitionAnimator() {
    return transitionAnimator;
  }

  public boolean isHorizontal() {
    return tabsLayout.isHorizontal();
  }

  @Override
  public void setBounds(float x, float y, float width, float height) {
    super.setBounds(x, y, width, height);
    tabsLayout.checkOrientation(width, height);

    if (tabStripsFloats) {
      iPlatformComponent ts = tabsLayout.tabStrip;

      if (ts.getParent() == this) {
        UIRectangle r = tabsLayout.contentArea.getBounds();
        UIDimension d = ts.getPreferredSize(new UIDimension());

        switch(tabsLayout.getTabPlacement()) {
          case TOP :
            r.height = d.height;

            break;

          case BOTTOM :
            r.y      = r.height - d.height;
            r.height = d.height;

            break;

          case LEFT :
            r.width = d.width;

            break;

          case RIGHT :
            r.x     = r.width - d.width;
            r.width = d.width;

            break;

          default :
            break;
        }

        ts.setBounds(r.x, r.y, r.width, r.height);
      }
    }
  }

  protected void navigateToTab(Object source, boolean next) {
    iWidget       w  = Platform.findWidgetForComponent(source);
    TabPaneViewer tv = (TabPaneViewer) Platform.getWidgetForComponent(this);

    if (w != tv) {
      TabPaneViewer tpv = (TabPaneViewer) w.getTabPaneViewer();

      if (tpv != tv) {
        return;
      }
    }

    int n = tv.getSelectedTab();

    n += (next
          ? 1
          : -1);

    if (n < 0) {
      n = tv.getTabCount() - 1;
    } else {
      n = 0;
    }

    if (tv.getTabCount() != 0) {
      tv.setSelectedTab(n);
    }
  }

  protected void tabWillChange(int index) {
    iWidget     context = Platform.findWidgetForComponent(this);
    TabDocument doc     = (TabDocument) getDocumentAt(index);

    changeIndex = index;

    if (!doc.asyncCanChange(context, this)) {
      canChange(context, doc);
    }
  }

  protected void setLayout(aTabPaneLayout layout) {
    this.tabsLayout = layout;
    layout.setFont(getFont());
    tabAction = new iActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        tabWillChange(tabsLayout.indexOfTab((UIAction) e.getSource()));
      }
    };
    layout.setMoreIcon(moreIcon);
  }

  protected void setSelectedIndexEx(int index) {
    if (closingAll) {
      return;
    }

    int                 count  = tabsLayout.getTabCount();
    iTabDocument        doc    = null;
    iPlatformComponent  out    = null;
    UIRectangle         bounds = null;
    iTransitionAnimator ta     = transitionAnimator;

    if ((selectedIndex > -1) && (selectedIndex < count)) {
      doc = (iTabDocument) tabsLayout.getTabLinkedData(selectedIndex);

      if (doc != null) {
        if (ta != null) {
          aAnimator.adjustAnimation(ta.getInAnimator(), selectedIndex < index, sizingAnchor, getTabPlacement());
          aAnimator.adjustAnimation(ta.getOutAnimator(), selectedIndex < index, sizingAnchor, getTabPlacement());
          out    = aTransitionAnimator.resolveTransitionComponent(tabsLayout.contentArea, doc.getDocComponent());
          bounds = out.getBounds();
          ta.setOutgoingComponent(out);
        }

        doc.tabDeactivated();

        if (listenerList != null) {
          TabDocument.fireTabDeactivated(doc, listenerList);
        }
      }
    } else {
      ta = null;
    }

    if ((ta != null) &&!tabsLayout.contentArea.isDisplayable()) {
      ta = null;
    }

    tabsLayout.setSelectedTab(index);
    selectedIndex = index;
    doc           = (iTabDocument) tabsLayout.getTabLinkedData(index);
    doc.tabActivated();

    if (listenerList != null) {
      TabDocument.fireTabActivated(doc, listenerList);
    }

    if ((ta != null) && (out != null)) {
      iPlatformComponent c = doc.getDocComponent();

      if (c == null) {
        c = aTransitionAnimator.resolveTransitionComponent(tabsLayout.contentArea, null);
      }

      ta.setIncommingComponent(c);
      ta.animate(tabsLayout.contentArea, bounds, null);
    }
  }

  @Override
  public boolean getTabStripsFloats() {
    return tabStripsFloats;
  }

  @Override
  public void setTabStripsFloats(boolean floats) {
    this.tabStripsFloats = floats;

    if (floats) {
      tabsLayout.tabStrip.bringToFront();
      tabsLayout.tabStrip.putClientProperty(iConstants.RARE_UNMANAGED_COMPONENT, Boolean.TRUE);
      getBorderLayout().setSupportsUnmanagedComponents(true);
    } else {
      tabsLayout.tabStrip.putClientProperty(iConstants.RARE_UNMANAGED_COMPONENT, null);
      getBorderLayout().setSupportsUnmanagedComponents(false);
    }
  }
}
