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
import com.appnativa.rare.ui.RenderableDataItem.HorizontalAlign;
import com.appnativa.rare.ui.RenderableDataItem.IconPosition;
import com.appnativa.rare.ui.RenderableDataItem.VerticalAlign;
import com.appnativa.rare.ui.border.UIEmptyBorder;
import com.appnativa.rare.ui.event.ActionEvent;
import com.appnativa.rare.ui.event.ExpansionEvent;
import com.appnativa.rare.ui.event.iActionListener;
import com.appnativa.rare.ui.event.iPopupMenuListener;
import com.appnativa.rare.ui.painter.PaintBucket;
import com.appnativa.rare.ui.painter.UIComponentPainter;
import com.appnativa.rare.ui.painter.iBackgroundPainter;
import com.appnativa.rare.ui.painter.iPlatformComponentPainter;
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

  public PopupList(iWidget context) {
    contextWidget = context;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    currentPopup.hidePopup();

    RenderableDataItem item = listHandler.getSelectedItem();

    if (actionListener != null) {
      actionListener.actionPerformed(new ActionEvent(listHandler.getSelection()));
    } else if (item instanceof UIMenuItem) {
      ((UIMenuItem) item).fire(contextWidget);
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

    border       = null;
    popupPainter = null;
    currentPopup = null;
    listHandler  = null;
  }

  @Override
  public void popupMenuCanceled(ExpansionEvent e) {}

  @Override
  public void popupMenuWillBecomeInvisible(ExpansionEvent e) {
    Platform.invokeLater(new Runnable() {
      @Override
      public void run() {
        dispose();
      }
    });
  }

  @Override
  public void popupMenuWillBecomeVisible(ExpansionEvent e) {}

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
    this.menuStyle = menuStyle;
  }

  public void setMenuItems(UIPopupMenu menu) {
    setItems(menu.getItems(), null, true, menu.size());
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

    if (showCloseButton) {
      iActionComponent a = PlatformHelper.createNakedButton(p.getWindowPane(), false, 0);
      String           s = Platform.getResourceAsString("Rare.runtime.text.popupMenu.cancel");

      a.setIconGap(UIScreen.platformPixels(4));
      a.setText(s);
      a.setAlignment(HorizontalAlign.CENTER, VerticalAlign.CENTER);
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

      UIFont f = a.getFont();

      a.setFont(f.deriveFont(UIFont.BOLD, f.getSize() + 2));
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
    iPlatformItemRenderer renderer = listHandler.getItemRenderer();
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
      pb = p.getPaintBucket("Rare.ComboBox.list.selectedBackground");
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
}
