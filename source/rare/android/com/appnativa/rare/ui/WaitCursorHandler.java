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

import android.app.Dialog;

import android.content.Context;
import android.content.DialogInterface;

import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;

import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appnativa.rare.Platform;
import com.appnativa.rare.platform.android.AppContext;
import com.appnativa.rare.platform.android.MainActivity;
import com.appnativa.rare.platform.android.ui.NullDrawable;
import com.appnativa.rare.platform.android.ui.view.LabelView;
import com.appnativa.rare.platform.android.ui.view.LinearLayoutEx;
import com.appnativa.rare.ui.painter.iPlatformComponentPainter;
import com.appnativa.util.MutableInteger;
import com.appnativa.util.iCancelable;

/**
 *
 * @author Don DeCoteau
 */
public class WaitCursorHandler {
  static final MutableInteger cursorCount = new MutableInteger(0);
  static SpinnerDialog        dialog;
  static int                  progressStyle;
  static int                  spinnerStyle;

  public static void hideProgressPopup(iPlatformComponent comp, boolean force) {
    stopWaitCursor(comp, force);
  }

  public static void showProgressPopup(iPlatformComponent comp, final CharSequence message,
          final iCancelable cancelable) {
    showProgressPopup(comp, message, cancelable, 0);
  }

  public static void showProgressPopup(iPlatformComponent comp, final CharSequence message,
          final iCancelable cancelable, final int delay) {
    Runnable r = new Runnable() {
      public void run() {
        startWaitCursorEx(message, cancelable, delay);
      }
    };

    Platform.runOnUIThread(r);
  }

  /**
   * Starts showing the wait cursor
   *
   * @param comp
   *          the component
   * @param cancelable
   *          to call to call if the user cancels the operation
   * @param delay
   *          how long to delay before the cursor becomes visible
   */
  public static void startWaitCursor(final iPlatformComponent comp, final iCancelable cancelable) {
    showProgressPopup(comp, null, cancelable, Platform.getUIDefaults().getInt("Rare.WaitCursorHandler.delay", 200));
  }

  /**
   * Starts showing the wait cursor
   *
   * @param comp
   *          the component
   * @param cancelable
   *          to call to call if the user cancels the operation
   * @param delay
   *          how long to delay before the cursor becomes visible
   */
  public static void startWaitCursor(final iPlatformComponent comp, final iCancelable cancelable, int delay) {
    showProgressPopup(comp, null, cancelable, Platform.getUIDefaults().getInt("Rare.WaitCursorHandler.delay", 200));
  }

  /**
   * Hides the wait cursor. The cursor will be removed from the screen when the
   * number of calls to hideWaitCursor matches the number of calls to
   * showWaitCursor
   *
   * @param comp
   *          the component that was associated with the corresponding start
   *          call
   * @param force
   *          true to force the cursor to be hidden even if the number of calls
   *          to showWaitCursor do not match the number of calls to
   *          hideWaitCursor
   */
  public static void stopWaitCursor(iPlatformComponent comp) {
    stopWaitCursor(comp, false);
  }

  /**
   * Hides the wait cursor. The cursor will be removed from the screen when the
   * number of calls to hideWaitCursor matches the number of calls to
   * showWaitCursor
   *
   * @param comp
   *          the component that was associated with the corresponding start
   *          call
   * @param force
   *          true to force the cursor to be hidden even if the number of calls
   *          to showWaitCursor do not match the number of calls to
   *          hideWaitCursor
   */
  public static void stopWaitCursor(iPlatformComponent comp, final boolean force) {
    Runnable r = new Runnable() {
      public void run() {
        stopWaitCursorEx(force);
      }
    };

    Platform.runOnUIThread(r);
  }

  public static void updateProgressPopup(iPlatformComponent comp, final CharSequence message) {
    if (dialog == null) {
      return;
    }

    Runnable r = new Runnable() {
      public void run() {
        if (dialog != null) {
          dialog.setMessage(message);
        }
      }
    };

    Platform.runOnUIThread(r);
  }

  private static void startWaitCursorEx(CharSequence message, iCancelable cancelable, int delay) {
    synchronized(cursorCount) {
      if ((dialog == null) ||!dialog.isShowing()) {
        cursorCount.set(1);

        try {
          dialog = SpinnerDialog.show(Platform.getAppContext().getActivity(), null, message, true, cancelable, delay);
        } catch(Exception e) {
          if (dialog == null) {
            try {
              dialog = SpinnerDialog.show(Platform.getAppContext().getActivity(), null, message, true, cancelable,
                                          delay);
            } catch(Exception ex) {}
          }
        }
      } else {
        cursorCount.incrementAndGet();
      }
    }
  }

  public static void onConfigurationChanged() {
    synchronized(cursorCount) {
      if ((dialog != null) && (cursorCount.get() > 0)) {
        try {
          try {
            dialog.dismiss();
          } catch(Exception ignore) {}

          dialog = SpinnerDialog.show(Platform.getAppContext().getActivity(), null, dialog.message, true,
                                      dialog.cancelable, 0);
          dialog.show();
        } catch(Exception e) {
          Platform.ignoreException("reshowWaitCursor", e);
        }
      }
    }
  }

  private static void stopWaitCursorEx(boolean force) {
    synchronized(cursorCount) {
      if ((cursorCount.decrementAndGet() < 1) || force) {
        try {
          if (dialog != null) {
            dialog.dismiss();
          }
        } catch(Exception e) {}
        finally {
          dialog = null;
          cursorCount.set(0);
        }
      }
    }
  }

  private static void dialogCanceled() {
    synchronized(cursorCount) {
      iCancelable cancelable = null;

      if (dialog != null) {
        cancelable = dialog.cancelable;
      }

      dialog = null;
      cursorCount.set(0);

      if (cancelable != null) {
        try {
          cancelable.cancel(true);
        } catch(Exception ignore) {}
      }
    }
  }

  public static class SpinnerDialog extends Dialog implements Runnable {
    boolean                          canCancel = true;
    TextView                         label;
    CharSequence                     message;
    iCancelable                      cancelable;
    CharSequence                     title;
    View                             contentView;
    static iPlatformComponentPainter componentPainter;

    public SpinnerDialog(Context context, boolean progress) {
      super(context, getTheme(context, progress));
    }

    static int getTheme(Context ctx, boolean progress) {
      if (spinnerStyle == 0) {
        spinnerStyle  = AppContext.getResourceId(ctx, "style/SpinnerDialog");
        progressStyle = AppContext.getResourceId(ctx, "style/ProgressDialog");
      }

      return progress
             ? progressStyle
             : spinnerStyle;
    }

    public void onBackPressed() {
      super.onBackPressed();

      if (!canCancel) {
        MainActivity ma = ((MainActivity) AppContext.getRootActivity());

        if (ma != null) {
          ma.handleDialogOnBackPressed(this);
        }
      }
    }

    public static SpinnerDialog show(Context context, CharSequence title, CharSequence message) {
      return show(context, title, message, true);
    }

    public static SpinnerDialog show(Context context, CharSequence title, CharSequence message, boolean indeterminate) {
      return show(context, title, message, indeterminate, null, 0);
    }

    public static SpinnerDialog show(Context context, CharSequence title, CharSequence message, boolean indeterminate,
                                     final iCancelable cancelable, final int delay) {
      final SpinnerDialog dialog = new SpinnerDialog(context, message != null);

      dialog.cancelable = cancelable;
      dialog.message    = message;

      if (title != null) {
        dialog.setTitle(title);
      } else {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
      }

      if (cancelable != null) {
        dialog.setCancelable(true);
      } else {
        dialog.setCancelable(false);
        dialog.canCancel = false;
      }

      dialog.setOnDismissListener(new OnDismissListener() {
        public void onDismiss(DialogInterface dialog) {}
      });
      dialog.setOnCancelListener(new OnCancelListener() {
        @Override
        public void onCancel(DialogInterface dialog) {
          dialogCanceled();
        }
      });

      ProgressBarWrapper prw = new ProgressBarWrapper(new ActionComponent(new LabelView(context)));
      View               pb  = prw.getComponent().getView();

      pb.setPadding(ScreenUtils.PLATFORM_PIXELS_4, ScreenUtils.PLATFORM_PIXELS_4, ScreenUtils.PLATFORM_PIXELS_4,
                    ScreenUtils.PLATFORM_PIXELS_4);

      LinearLayoutEx l = new LinearLayoutEx(context);

      l.setPadding(ScreenUtils.PLATFORM_PIXELS_4, ScreenUtils.PLATFORM_PIXELS_4, ScreenUtils.PLATFORM_PIXELS_4,
                   ScreenUtils.PLATFORM_PIXELS_4);

      if (componentPainter == null) {
        componentPainter = PainterUtils.createProgressPopupPainter();
      }

      UIColor fg = componentPainter.getForegroundColor();

      if (fg == null) {
        fg = ColorUtils.getForeground();
      }

      l.setComponentPainter(componentPainter);
      l.setOrientation(LinearLayoutEx.HORIZONTAL);
      l.addView(pb);

      if (message != null) {
        TextView tv;

        if (Platform.getAppContext() == null) {
          tv = new TextView(context);
        } else {
          tv = new LabelView(context);
        }

        tv.setTextColor(fg.getColor());
        tv.setText(message);
        tv.setGravity(Gravity.CENTER);
        FontUtils.getDefaultFont().setupTextView(tv);

        LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
                                          LayoutParams.WRAP_CONTENT);

        llp.gravity = Gravity.CENTER;
        l.addView(tv, llp);
        dialog.label = tv;
      }

      if (cancelable != null) {
        ImageButton b = new ImageButton(context);

        b.setBackgroundDrawable(NullDrawable.getInstance());
        b.setPadding(ScreenUtils.PLATFORM_PIXELS_8, ScreenUtils.PLATFORM_PIXELS_4, ScreenUtils.PLATFORM_PIXELS_8,
                     ScreenUtils.PLATFORM_PIXELS_4);

        UIImageIcon ic = (UIImageIcon) Platform.getResourceAsIcon("Rare.icon.cancel");

        b.setImageBitmap(ic.getImage().getBitmap());
        b.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            dialog.cancel();
          }
        });

        LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
                                          LayoutParams.WRAP_CONTENT);

        llp.gravity = Gravity.CENTER;
        l.addView(b, llp);
      }

      l.setBackgroundColor(ColorUtils.getBackground().getColor());

      LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

      dialog.setContentView(l, lp);
//      if (delay > 0) { // TODO: this code seem to not block touch input need to check out
//        dialog.contentView = l;
//        l.setVisibility(View.INVISIBLE);
//        Platform.invokeLater(dialog, delay);
//      }
      dialog.show();

      return dialog;
    }

    public void setMessage(CharSequence text) {
      if (label != null) {
        label.setText(text);
      }
    }

    public void dismiss() {
      contentView = null;
      super.dismiss();
    }

    @Override
    public void run() {
      if (contentView != null) {
        contentView.setVisibility(View.VISIBLE);
        contentView = null;
      }
    }
  }


  public static void hide() {
    stopWaitCursor(null, true);
  }
}
