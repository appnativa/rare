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
import com.appnativa.rare.scripting.Functions;
import com.appnativa.rare.ui.RenderableDataItem.HorizontalAlign;
import com.appnativa.rare.ui.RenderableDataItem.IconPosition;
import com.appnativa.rare.ui.RenderableDataItem.VerticalAlign;
import com.appnativa.rare.ui.border.UIEmptyBorder;
import com.appnativa.rare.ui.event.ActionEvent;
import com.appnativa.rare.ui.event.EventListenerList;
import com.appnativa.rare.ui.event.ExpansionEvent;
import com.appnativa.rare.ui.event.iActionListener;
import com.appnativa.rare.ui.event.iPopupMenuListener;
import com.appnativa.rare.ui.painter.PaintBucket;
import com.appnativa.rare.ui.painter.UIComponentPainter;
import com.appnativa.rare.ui.painter.iBackgroundPainter;
import com.appnativa.rare.ui.painter.iPlatformComponentPainter;
import com.appnativa.rare.ui.renderer.aListItemRenderer;
import com.appnativa.rare.util.ListHelper;
import com.appnativa.rare.widget.iWidget;

import java.util.List;

public class PopupList implements iPopupMenuListener, iActionListener {
  private static UIEmptyBorder        listBorder = new UIEmptyBorder(ScreenUtils.PLATFORM_PIXELS_8);
  protected iActionListener           actionListener;
  protected iWidget                   contextWidget;
  protected iPopup                    currentPopup;
  protected boolean                   initialized;
  protected iPlatformListHandler      listHandler;
  protected iPlatformComponentPainter popupPainter;
  private iPlatformBorder             border;
  private boolean                     menuStyle;
  protected String                    cancelButtonText;
  private int                         defaultRowHeight;
  private EventListenerList           listenerList;

  public PopupList(iWidget context) {
    contextWidget = context;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    int row=listHandler.getSelectedIndex();
    final Object o=listHandler.getSelection();
    listHandler.clearSelection();
    ListHelper.flashHilight(listHandler, row, true, 3, new Runnable() {
      
      @Override
      public void run() {
        if (actionListener != null) {
          actionListener.actionPerformed(new ActionEvent(o));
        } else if (o instanceof UIMenuItem) {
          ((UIMenuItem) o).fire(contextWidget);
        }
        currentPopup.hidePopup();
      }
    });
  }

  /**
   * Adds a popup menu listener
   *
   * @param l
   *          the listener to add
   */
  public void addPopupMenuListener(iPopupMenuListener l) {
    if (l != null) {
      getEventListenerList().add(iPopupMenuListener.class, l);
    }
  }

  /**
   * Removes a popup menu listener
   *
   * @param l
   *          the listener to remove
   */
  public void removePopupMenuListener(iPopupMenuListener l) {
    if ((l != null) && (listenerList != null)) {
      listenerList.remove(iPopupMenuListener.class, l);
    }
  }

  public void dispose() {
    if ((currentPopup != null) && currentPopup.isShowing()) {
      currentPopup.hidePopup();

      return;
    }

    if (currentPopup != null) {
      currentPopup.dispose();
      currentPopup = null;
    }

    if (listHandler != null) {
      listHandler.dispose();
    }

    if (listenerList != null) {
      listenerList.clear();
    }

    border       = null;
    popupPainter = null;
    currentPopup = null;
    listHandler  = null;
    listenerList = null;
  }

  @Override
  public void popupMenuCanceled(ExpansionEvent e) {
    if (listenerList != null) {
      Utils.firePopupCanceledEvent(listenerList, e);
    }
  }

  @Override
  public void popupMenuWillBecomeInvisible(ExpansionEvent e) {
    if (listenerList != null) {
      Utils.firePopupEvent(listenerList, e, false);
    }

    Platform.invokeLater(new Runnable() {
      @Override
      public void run() {
        dispose();
      }
    });
  }

  @Override
  public void popupMenuWillBecomeVisible(ExpansionEvent e) {
    if (listenerList != null) {
      Utils.firePopupEvent(listenerList, e, true);
    }
  }

  public void showModalPopup(boolean showCloseButton) {
    currentPopup = createPopup(true, showCloseButton);
    currentPopup.showModalPopup();
  }

  public void showPopup(iPlatformComponent owner, int x, int y) {
    if (currentPopup != null) {
      currentPopup.dispose();
    }

    currentPopup = createPopup(false, false);
    currentPopup.setTransient(true);

    UIRectangle r = new UIRectangle();

    Utils.getProposedBoundsForLocation(r, x, y, getPreferredSize());
    currentPopup.showPopup(owner, (int) r.x, (int) r.y);
  }

  public void setItems(List<RenderableDataItem> items, iActionListener l, boolean menuStyle, int visibleRowCount) {
    iWidget                ctx   = (contextWidget == null)
                                   ? Platform.getWindowViewer()
                                   : contextWidget;
    iPlatformListDataModel model = PlatformHelper.createListDataModel(ctx, items);

    listHandler = PlatformHelper.createPopupListBoxHandler(ctx, model, menuStyle);
    listHandler.getListComponent().setFocusPainted(false);
    listHandler.addActionListener(this);
    actionListener = l;
    listHandler.setVisibleRowCount(visibleRowCount);

    int rh  = getDefaultRowHeight();
    int len = items.size();

    for (int i = 0; i < len; i++) {
      rh = Math.max(rh, listHandler.getPreferredHeight(i));
    }

    listHandler.setRowHeight(rh + UIScreen.PLATFORM_PIXELS_2);
    this.menuStyle = menuStyle;
  }

  public void setMenuItems(UIPopupMenu menu) {
    setItems(menu.getItems(), null, true, menu.size());
  }

  protected int getDefaultRowHeight() {
    if (defaultRowHeight == 0) {
      defaultRowHeight = UIScreen.toPlatformPixelHeight(PlatformHelper.getDefaultRowHeight(),
              Platform.getWindowViewer().getComponent(), 100, false);
    }

    return defaultRowHeight;
  }

  public void setPopupPainter(iPlatformComponentPainter popupPainter) {
    this.popupPainter = popupPainter;
  }

  public void setPoupuBorder(iPlatformBorder b) {
    border = b;
  }

  public iPlatformComponent getContent() {
    return listHandler.getContainerComponent();
  }

  public iPlatformBorder getPopupBorder() {
    return (popupPainter == null)
           ? border
           : popupPainter.getBorder();
  }

  public iPlatformComponentPainter getPopupPainter() {
    return popupPainter;
  }

  public UIDimension getPreferredSize() {
    return listHandler.getPreferredSize();
  }

  public boolean isPopupVisible() {
    return (currentPopup != null) && currentPopup.isShowing();
  }

  protected iPopup createPopup(boolean modal, boolean showCloseButton) {
    if (!initialized) {
      initialized = true;
      setRenderingDefaults();
    }

    iPopup p = Platform.getAppContext().getWindowManager().createPopup(contextWidget);

    p.setPopupOwner(contextWidget.getContainerComponent());

    iActionComponent a = null;
    String           s = null;

    if (showCloseButton) {
      a = PlatformHelper.createNakedButton(p.getWindowPane(), false, 0);
      s = cancelButtonText;

      if ((s == null) || (s.length() == 0)) {
        s = Platform.getResourceAsString("Rare.runtime.text.popupMenu.cancel");

        UIFont f = a.getFont();

        a.setFont(f.deriveFont(UIFont.BOLD, f.getSize() + 2));
      } else {
        if (!s.startsWith("<html>")) {
          int wrap = Platform.getUIDefaults().getInt("Rare.PopupMenu.titleCharacterWrapCount", 40);

          s = Functions.htmlWordWrap(s, wrap, true);
        }
      }

      a.setText(s);
      a.setIconGap(UIScreen.platformPixels(4));
      a.setAlignment(HorizontalAlign.LEFT, VerticalAlign.CENTER);
      a.setIconPosition(IconPosition.RIGHT_JUSTIFIED);
      a.setIcon(Platform.getResourceAsIcon("Rare.icon.close"));
      a.setBackground(ColorUtils.getBackground());
      a.addActionListener(new iActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          dispose();
        }
      });
      a.setForeground(listHandler.getListComponent().getForeground());
      a.setBorder(TitlePane.getTitlePaneBorder());
      a.putClientProperty(iConstants.RARE_MIN_HEIGHT_PROPERTY, "1.5ln");
      p.getWindowPane().setTitileBar(a);
    }

    if (modal) {
      listHandler.getContainerComponent().putClientProperty(iConstants.RARE_MIN_WIDTH_PROPERTY, "20ch");
    }

    p.setContent(listHandler.getContainerComponent());

    if (popupPainter != null) {
      p.setComponentPainter(popupPainter);
    }

    p.addPopupMenuListener(this);

    return p;
  }

  protected void setRenderingDefaults() {
    iPlatformComponent    list     = listHandler.getListComponent();
    iPlatformItemRenderer renderer = (aListItemRenderer) listHandler.getItemRenderer();
    UIProperties          p        = Platform.getUIDefaults();
    UIColor               c        = menuStyle
                                     ? p.getColor("Rare.PopupMenu.list.selectedForeground")
                                     : null;;

    if (c == null) {
      c = p.getColor("Rare.ComboBox.list.selectedForeground");
    }

    PaintBucket pb = menuStyle
                     ? p.getPaintBucket("Rare.PopupMenu.list.selectedBackground")
                     : null;

    if (pb == null) {
      pb = renderer.getAutoHilightPaint();
    }

    if ((c != null) || (pb != null)) {
      if (pb == null) {
        pb = new PaintBucket();
        pb.setForegroundColor(c);
      }

      renderer.setSelectionPaint(pb);
    }

    c = menuStyle
        ? p.getColor("Rare.PopupMenu.list.foreground")
        : null;

    if (c == null) {
      c = p.getColor("Rare.ComboBox.list.foreground");
    }

    if (c != null) {
      list.setForeground(c);
    }

    c = menuStyle
        ? p.getColor("Rare.PopupMenu.list.background")
        : null;

    if (c == null) {
      c = p.getColor("Rare.ComboBox.list.background");
    }

    if (c != null) {
      list.setBackground(c);
    }

    list.setBorder(listBorder);
    list.putClientProperty(iConstants.RARE_MIN_WIDTH_PROPERTY, "12ch");

    iBackgroundPainter bp = p.getBackgroundPainter("Rare.PopupMenu.background");

    if (popupPainter == null) {
      if (border == null) {
        border = p.getBorder("Rare.PopupMenu.border");
      }

      if (border == null) {
        border = BorderUtils.createShadowBorder(ScreenUtils.PLATFORM_PIXELS_7);
      }

      popupPainter = new UIComponentPainter();
      popupPainter.setBorder(border);

      if (bp == null) {
        popupPainter.setBackgroundColor(c);
      } else if (bp.isSingleColorPainter()) {
        popupPainter.setBackgroundColor(bp.getBackgroundColor());
      } else {
        popupPainter.setBackgroundPainter(bp, false);
      }
    }
  }

  public String getCancelButtonText() {
    return cancelButtonText;
  }

  public void setCancelButtonText(String cancelButtonText) {
    this.cancelButtonText = cancelButtonText;
  }

  protected EventListenerList getEventListenerList() {
    if (listenerList == null) {
      listenerList = new EventListenerList();
    }

    return listenerList;
  }
}
