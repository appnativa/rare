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
import com.appnativa.rare.ui.border.UICompoundBorder;
import com.appnativa.rare.ui.border.UIEmptyBorder;
import com.appnativa.rare.ui.border.UILineBorder;
import com.appnativa.rare.ui.event.ExpansionEvent;
import com.appnativa.rare.ui.event.iPopupMenuListener;
import com.appnativa.rare.viewer.WindowViewer;
import com.appnativa.rare.widget.PushButtonWidget;

/**
 * A class that manages displaying a temporary
 * message on the screen to notify the used that some action
 * has occurred
 *
 * @author Don DeCoteau
 *
 */
public class UINotifier {
  static UICompoundBorder defaultBorder;
  iPopup                  popupWindow;
  PushButtonWidget        pushButton;

  /**
   * Creates a new instance
   *
   * @param text the text to display
   * @param length the length in milliseconds to display the message for
   * @param icon to display (can be null)
   * @param runner optional runnable to invoke after the message has been displayed
   */
  public UINotifier(String text, int length, iPlatformIcon icon, final Runnable runner) {
    WindowViewer w = Platform.getWindowViewer();
    final iPopup p = Platform.getAppContext().getWindowManager().createPopup(w);

    p.setTransient(true);
    p.setTimeout(length);

    PushButton cfg = (PushButton) w.createConfigurationObject("PushButton", "Rare.Notifier.button");
    if (cfg.templateName.getValue() == null) {
      cfg.buttonStyle.setValue(PushButton.CButtonStyle.toggle);
    }     
    cfg.wordWrap.setValue(true);

    final PushButtonWidget l = PushButtonWidget.create(w, cfg);

    if (cfg.templateName.getValue() == null) {
      l.setBackground(ColorUtils.getBackground());
      l.setBorder(getDefaultBorder());
      l.setMinimumSize(null, "2ln");
    }

    l.setText(text);

    if (icon != null) {
      l.setIcon(icon);
    }

    p.setContent(l.getContainerComponent());
    p.addPopupMenuListener(new iPopupMenuListener() {
      @Override
      public void popupMenuCanceled(ExpansionEvent arg0) {}
      @Override
      public void popupMenuWillBecomeInvisible(ExpansionEvent arg0) {
        Platform.invokeLater(new Runnable() {
          @Override
          public void run() {
            dispose();

            if (runner != null) {
              runner.run();
            }
          }
        });
      }
      @Override
      public void popupMenuWillBecomeVisible(ExpansionEvent arg0) {}
    });
    popupWindow = p;
    pushButton  = l;
  }

  /**
   * Gets the popup window for the notifier
   * @return the popup window for the notifier
   */
  public iPopup getPopup() {
    return popupWindow;
  }

  /**
   * Gets the pushbutton for the notifier
   * @return the pushbutton for the notifier
   */
  public PushButtonWidget getPushButton() {
    return pushButton;
  }

  /**
   * Show the notifier as the specified location
   * @param x the x position
   * @param y the y position
   */
  public void show(float x, float y) {
    popupWindow.showPopup(x, y);
  }

  /**
   * Show the notifier as the specified location
   * @param location the position
   */
  public void show(Location location) {
    if (location == null) {
      location = Location.CENTER;
    }

    UIDimension size = new UIDimension();
    UIRectangle rect = Platform.getWindowViewer().getBounds();

    popupWindow.getPreferredSize(size);

    float x = Math.max(0, rect.x + ((rect.width - size.width) / 2));
    float y;

    switch(location) {
      case TOP :
        y = rect.y + UIScreen.PLATFORM_PIXELS_1;

        break;

      case BOTTOM :
        y = rect.y + rect.height - size.height - UIScreen.PLATFORM_PIXELS_1;

        break;

      default :
        y = Math.max(0, rect.y + ((rect.height - size.height) / 2));

        break;
    }

    show(x, y);
  }

  /**
   * Disposes of the notifier
   */
  protected void dispose() {
    try {
      if (popupWindow != null) {
        popupWindow.dispose();
      }

      if (pushButton != null) {
        pushButton.dispose();
      }
    } catch(Exception e) {
      Platform.ignoreException(null, e);
    }

    popupWindow = null;
    pushButton  = null;
  }

  /**
   * Plays the notification sound
   */
  public static void playSound() {}

  /**
   * Shows a message
   *
   * @param text the text to display
   */
  public static void showMessage(String text) {
    showMessage(text, Length.SHORT, Location.CENTER);
  }

  /**
   * Shows a toast
   *
   * @param text the text to display
   * @param length the length in milliseconds to display the message for
   * @param location the location at which to show the message
   */
  public static void showMessage(String text, int length, Location location) {
    showMessage(text, length, location, null, null);
  }

  /**
   * Shows a toast
   *
   * @param text the text to display
   * @param length the length in milliseconds to display the message for
   * @param location the location at which to show the message
   * @param icon to display (can be null)
   * @param runner optional runnable to invoke after the message has been displayed
   */
  public static void showMessage(String text, int length, Location location, iPlatformIcon icon,
                                 final Runnable runner) {
    UINotifier notifier = new UINotifier(text, length, icon, runner);

    notifier.show(location);
  }

  /**
   * Shows a message
   *
   * @param text the text to display
   * @param length the length in milliseconds to display the message for
   * @param location the location at which to show the message
   */
  public static void showMessage(String text, Length length, Location location) {
    int to;

    switch(length) {
      case LONG :
        to = 3000;

        break;

      case MEDIUM :
        to = 2000;

        break;

      default :
        to = 1500;

        break;
    }

    showMessage(text, to, location);
  }

  static iPlatformBorder getDefaultBorder() {
    if (defaultBorder == null) {
      UILineBorder lb = new UILineBorder(UILineBorder.getDefaultLineColor(), UIScreen.PLATFORM_PIXELS_1,UIScreen.PLATFORM_PIXELS_6);
                                        
      UIEmptyBorder eb=new UIEmptyBorder(UIScreen.PLATFORM_PIXELS_6);
      defaultBorder = new UICompoundBorder(lb, eb);
    }

    return defaultBorder;
  }

  public static enum Length { SHORT, MEDIUM, LONG }

  public static enum Location { TOP, CENTER, BOTTOM }
}
