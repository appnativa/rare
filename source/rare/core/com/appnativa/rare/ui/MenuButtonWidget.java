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
import com.appnativa.rare.spot.PushButton;
import com.appnativa.rare.ui.border.UIBalloonBorder;
import com.appnativa.rare.ui.border.UIEmptyBorder;
import com.appnativa.rare.ui.border.aUIBalloonBorder.PeakLocation;
import com.appnativa.rare.ui.event.ActionEvent;
import com.appnativa.rare.ui.event.iActionListener;
import com.appnativa.rare.ui.painter.UIComponentPainter;
import com.appnativa.rare.viewer.aListViewer;
import com.appnativa.rare.viewer.iContainer;
import com.appnativa.rare.widget.PushButtonWidget;

import java.util.List;

public class MenuButtonWidget extends PushButtonWidget {
  iPopulateCallback callback;
  private boolean   useActionListenerAsSource;

  public MenuButtonWidget(iContainer parent, iPopulateCallback cb) {
    super(parent);
    callback = cb;

    PushButton cfg = new PushButton();

    cfg.buttonStyle.setValue(PushButton.CButtonStyle.toolbar);
    cfg.actionType.setValue(PushButton.CActionType.popup_widget);
    cfg.popupWidget.setValue(createPopupMenuConfiguration(0, "Rare.MenuBar.actionBarMenu"));
    cfg.addBorder("none");
    configure(cfg);

    UIColor c = Platform.getUIDefaults().getColor("Rare.MenuBar.actionBarMenu.Button.sharedBorderColor");

    if (c != null) {
      sharedBorderColor = c;
    }

    sharedBorderArc = Platform.getUIDefaults().getInt("Rare.MenuBar.actionBarMenu.Button.sharedBorderCornerArc",
            ScreenUtils.PLATFORM_PIXELS_6);
    sharedBorderThickness =
      Platform.getUIDefaults().getFloat("Rare.MenuBar.actionBarMenu.Button.sharedBorderThickness",
        sharedBorderThickness);
    setBorder(new UIEmptyBorder(Platform.isTouchDevice()
                                ? ScreenUtils.PLATFORM_PIXELS_6
                                : ScreenUtils.PLATFORM_PIXELS_2));
  }

  @Override
  public void createPopupWidget() {
    super.createPopupWidget();
    configureList(true);
  }

  @Override
  public void showPopupWidget() {
    if (popupWidget != null) {
      configureList(false);
    }

    super.showPopupWidget();
  }

  public void setPopupBorder(iPlatformBorder border) {
    shareBorder      = false;
    sharedLineBorder = null;

    if (popupPainter == null) {
      popupPainter = new UIComponentPainter();
    }

    popupPainter.setBorder(border);
  }

  public void setUseActionListenerAsEventSource(boolean useActionListenerAsSource) {
    this.useActionListenerAsSource = useActionListenerAsSource;
  }

  public boolean isUseActionListenerAsEventSource() {
    return useActionListenerAsSource;
  }

  @Override
  protected void willShowPopup(iPopup p, UIRectangle bounds) {
    super.willShowPopup(p, bounds);

    if ((popupPainter != null) && (popupPainter.getBorder() instanceof UIBalloonBorder)) {
      UIBalloonBorder b = (UIBalloonBorder) popupPainter.getBorder();

      b.setPeakOffset(ScreenUtils.PLATFORM_PIXELS_4);

      float ps = b.getPeakSize();

      if (bounds.x == 0) {
        if (bounds.y >= -ps) {
          b.setPeakLocation(PeakLocation.UL_TOP);
        } else {
          b.setPeakLocation(PeakLocation.LL_BOTTOM);
        }
      } else if (bounds.x > 0) {
        if (bounds.y > -(bounds.height / 2)) {
          b.setPeakLocation(PeakLocation.UL_LEFT);
        } else {
          b.setPeakLocation(PeakLocation.LL_LEFT);
        }
      } else {
        if (bounds.y < -ps) {
          b.setPeakLocation(PeakLocation.LR_BOTTOM);
        } else {
          b.setPeakLocation(PeakLocation.UR_TOP);
        }
      }
    }
  }

  private void configureList(boolean first) {
    aListViewer lv = (aListViewer) popupWidget;

    if (first) {
      lv.addActionListener(new iActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          disposeOfPopup();
          resetButtonBorderAndBackground();
        }
      });
    }

    lv.clear();
    callback.addMenuItems(lv);
    lv.setVisibleRowCount(lv.size());
  }

  public static interface iPopulateCallback {
    void addMenuItems(List<RenderableDataItem> list);
  }
}
