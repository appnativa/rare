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
import com.appnativa.rare.platform.apple.ui.view.View;
import com.appnativa.rare.ui.painter.iPlatformComponentPainter;
import com.appnativa.util.MutableInteger;
import com.appnativa.util.iCancelable;

/*-[
 #import "RAREWaitCursorView.h"
 ]-*/
public class WaitCursorHandler {
  static final MutableInteger cursorCount = new MutableInteger(0);
  static SpinnerDialog        dialog;

  public static void onConfigurationChanged() {}

  public static void showProgressPopup(final iPlatformComponent comp, final CharSequence message,
          final iCancelable cancelable) {
    Platform.runOnUIThread(new StartRunnable(message, cancelable, 0));
  }

  public static void showProgressPopup(final iPlatformComponent comp, final CharSequence message,
          final iCancelable cancelable, int delay) {
    Platform.runOnUIThread(new StartRunnable(message, cancelable, delay));
  }

  /**
   * Sets cursor to Wait cursor
   *
   * @param comp
   *          the component
   */
  public static void startWaitCursor(final iPlatformComponent comp, final iCancelable cancelable) {
    showProgressPopup(comp, null, cancelable, Platform.getUIDefaults().getInt("Rare.WaitCursorHandler.delay", 200));
  }

  /**
   * Sets cursor to Wait cursor
   *
   * @param comp
   *          the component
   */
  public static void startWaitCursor(final iPlatformComponent comp, final iCancelable cancelable, int delay) {
    showProgressPopup(comp, null, cancelable, delay);
  }

  /**
   * Sets cursor for specified component to normal cursor
   *
   * @param comp
   *          the component the the start call was associated with
   * @param force
   *          force the cursor change
   */
  public static void stopWaitCursor(iPlatformComponent comp, final boolean force) {
    Platform.runOnUIThread(new StopRunnable(force));
  }

  /**
   * Sets cursor for specified component to normal cursor
   *
   * @param comp
   *          the component the the start call was associated with
   */
  public static void stopWaitCursor(iPlatformComponent comp) {
    Platform.runOnUIThread(new StopRunnable(false));
  }

  public static void updateProgressPopup(iPlatformComponent comp, final CharSequence message) {
    if (dialog == null) {
      return;
    }

    Platform.runOnUIThread(new UpdateRunnable(message));
  }

  private static void startWaitCursorEx(CharSequence message, iCancelable cancelable, int delay) {
    synchronized(cursorCount) {
      if ((dialog == null) ||!dialog.isShowing()) {
        cursorCount.set(1);

        try {
          dialog = SpinnerDialog.show(message, cancelable, delay);
        } catch(Exception e) {
          if (dialog == null) {
            try {
              dialog = SpinnerDialog.show(message, cancelable, delay);
            } catch(Exception ex) {}
          }
        }
      } else {
        cursorCount.incrementAndGet();
      }
    }
  }

  private static void stopWaitCursorEx(boolean force) {
    synchronized(cursorCount) {
      if (force || (cursorCount.decrementAndGet() < 1)) {
        try {
          if (dialog != null) {
            dialog.dismiss();
            dialog.dispose();
          }
        } catch(Exception ignore) {}
        finally {
          cursorCount.set(0);
          dialog = null;
        }
      }
    }
  }

  public static class SpinnerDialog extends View {
    iCancelable                      cancelable;
    static iPlatformComponentPainter resuableComponentPainter;

    public SpinnerDialog(CharSequence message, iCancelable cancelable, int delay) {
      super(createProxy(message, getCancelImage(cancelable != null), delay));
      this.cancelable = cancelable;
    }

    static UIImage getCancelImage(boolean cancelable) {
      if (cancelable) {
        UIImageIcon ic = (UIImageIcon) Platform.getResourceAsIcon("Rare.icon.cancel");

        return ic.getImage();
      }

      return null;
    }

    public native static Object createProxy(CharSequence message, UIImage cancelImage, int delay)
    /*-[
      return [[RAREWaitCursorView alloc] initWithMessage:message cancelButtonImage: cancelImage delay: delay];
    ]-*/
    ;

    public native void dismiss()
    /*-[
      [((RAREWaitCursorView*)proxy_) dismiss];
    ]-*/
    ;

    public native void show()
    /*-[
      [((RAREWaitCursorView*)proxy_) show];
    ]-*/
    ;

    public static SpinnerDialog show(CharSequence message, iCancelable cancelable, int delay) {
      SpinnerDialog dialog = new SpinnerDialog(message, cancelable, delay);

      if (resuableComponentPainter == null) {
        resuableComponentPainter = PainterUtils.createProgressPopupPainter();
      }

      dialog.componentPainter = resuableComponentPainter;

      UIColor fg = resuableComponentPainter.getForegroundColor();

      if (fg == null) {
        fg = ColorUtils.getForeground();
      }

      UIColor color = Platform.getUIDefaults().getColor("Rare.AnimatedSpinner.color");

      if (color == null) {
        color = fg;
      }

      dialog.setColor(color, fg);
      dialog.show();

      return dialog;
    }

    native void setColor(UIColor spinnerColor, UIColor labelColor)
    /*-[
      [((RAREWaitCursorView*)proxy_) setSpinnerColor: spinnerColor labelColor: labelColor];
    ]-*/
    ;

    public native void setMessage(CharSequence message)
    /*-[
      [((RAREWaitCursorView*)proxy_) setMessage: message];
    ]-*/
    ;

    void dialogCanceled() {
      synchronized(cursorCount) {
        iCancelable c = cancelable;

        cancelable = null;
        dismiss();
        dispose();
        dialog = null;
        cursorCount.set(0);

        if (c != null) {
          c.cancel(true);
        }
      }
    }
  }


  private static class StartRunnable implements Runnable {
    iCancelable  cancelable;
    CharSequence message;
    int          delay;

    public StartRunnable(CharSequence message, iCancelable cancelable, int delay) {
      super();
      this.message    = message;
      this.cancelable = cancelable;
      this.delay      = delay;
    }

    @Override
    public void run() {
      startWaitCursorEx(message, cancelable, delay);
    }
  }


  private static class StopRunnable implements Runnable {
    boolean force;

    public StopRunnable(boolean force) {
      this.force = force;
    }

    @Override
    public void run() {
      stopWaitCursorEx(force);
    }
  }


  private static class UpdateRunnable implements Runnable {
    CharSequence message;

    public UpdateRunnable(CharSequence message) {
      this.message = message;
    }

    @Override
    public void run() {
      if (dialog != null) {
        dialog.setMessage(message);
      }
    }
  }


  public static void hideProgressPopup(iPlatformComponent comp, boolean force) {
    stopWaitCursor(comp, force);
  }
}
