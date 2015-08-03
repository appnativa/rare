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

package com.appnativa.rare.viewer;

import com.appnativa.rare.Platform;
import com.appnativa.rare.iConstants;
import com.appnativa.rare.spot.ActionItem;
import com.appnativa.rare.spot.StatusBar;
import com.appnativa.rare.spot.Viewer;
import com.appnativa.rare.ui.UIAction;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIColorHelper;
import com.appnativa.rare.ui.UIColorShade;
import com.appnativa.rare.ui.aStatusBar;
import com.appnativa.rare.ui.aStatusBar.ProgressStatusBarItem;
import com.appnativa.rare.ui.aWidgetListener;
import com.appnativa.rare.ui.border.StatusBarBorder;
import com.appnativa.rare.ui.border.UICompoundBorder;
import com.appnativa.rare.ui.border.UIEmptyBorder;
import com.appnativa.rare.ui.border.UILineBorder;
import com.appnativa.rare.ui.border.UIMatteBorder;
import com.appnativa.rare.ui.iActionComponent;
import com.appnativa.rare.ui.iPlatformBorder;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.iStatusBar;
import com.appnativa.rare.widget.LabelWidget;
import com.appnativa.rare.widget.PushButtonWidget;
import com.appnativa.spot.SPOTSet;

import java.util.Locale;

/**
 * A widget that displays status information, usually at the bottom
 * of a window. It has built-in support for displaying the current
 * time, the current memory usage information, a progress bar to
 * indicate task progress, and window resizing initiator.
 *
 * @author     Don DeCoteau
 */
public abstract class aStatusBarViewer extends aContainer implements iStatusBar {
  private String                 defaultStatus = null;
  private iActionComponent       overwriteInsert;
  private ProgressStatusBarItem  progressBar;
  private aStatusBar             statusBar;
  private static iPlatformBorder label_border;

  /**
   * Constructs a new instance
   */
  public aStatusBarViewer() {
    this(null);
  }

  /**
   * Constructs a new instance
   *
   * @param parent the widget's parent
   */
  public aStatusBarViewer(iContainer parent) {
    super(parent);
    widgetType = WidgetType.StatusBar;

    if (label_border == null) {
      iPlatformBorder eb = new UIEmptyBorder(1, 4, 1, 2);

      label_border = new UICompoundBorder(new UIMatteBorder(0, 1, 0, 0, UIColorHelper.getColor("controlShadow")), eb);
    }
  }

  /**
   * Adds the status bar as a window dragger
   *
   * @param w the window to add
   */
  public void addAsWindowDragger(WindowViewer w) {
    w.addWindowDragger(statusBar);
    w.addWindowDragger(progressBar.getStatusLabel());
  }

  @Override
  public void clearContents() {}

  @Override
  public void configure(Viewer vcfg) {
    configureEx((com.appnativa.rare.spot.StatusBar) vcfg);
    fireConfigureEvent(vcfg, iConstants.EVENT_CONFIGURE);
  }

  @Override
  public void configureForPopup() {
    statusBar.setBorder(new UIMatteBorder(1, 0, 0, 0, UILineBorder.getDefaultLineColor()));
  }

  /**
   * Creates a basic status bar
   */
  public void createBasic() {
    statusBar   = createStatusBarAndComponents(null);
    progressBar = statusBar.getProgressStatusBarItem();
  }

  /**
   * Creates a default status bar for a main window
   */
  public void createMainWindowDefault() {
    createBasic();
  }

  @Override
  public void progressAbort() {
    progressBar.cancelPerformed();
  }

  @Override
  public void progressComplete() {
    progressBar.completePerformed();
  }

  @Override
  public void progressStart(boolean showCancelButton) {
    progressBar.setCancelAction(null);
    progressBar.setCompleteAction(null);
    progressBar.getCancelButton().setVisible(showCancelButton);
    progressBar.setIndeterminate(false);
    progressBar.setProgressStatus("");
  }

  @Override
  public void progressStart(boolean indeterminate, String message, Object cancelAction) {
    progressStart(indeterminate, message, cancelAction, null);
  }

  @Override
  public void progressStart(boolean indeterminate, String message, Object cancelAction, Object completeAction) {
    if (cancelAction != null) {
      progressBar.getCancelButton().setVisible(true);
      progressBar.setCancelAction(cancelAction);
    } else {
      progressBar.getCancelButton().setVisible(false);
    }

    progressBar.setCompleteAction(completeAction);
    progressBar.setIndeterminate(indeterminate);
    progressBar.setProgressStatus(message);
  }

  @Override
  public void progressStartIndeterminate(boolean showCancelButton) {
    progressBar.setCancelAction(null);
    progressBar.setCompleteAction(null);
    progressBar.getCancelButton().setVisible(showCancelButton);
    progressBar.setIndeterminate(true);
    progressBar.setProgressStatus("");
  }

  @Override
  public void showMessage(String msg) {
    if (msg == null) {
      msg = this.defaultStatus;
    }

    if (progressBar != null) {
      progressBar.setStatus(msg);
    }
  }

  @Override
  public void toggleVisibility() {
    setVisible(!isVisible());
  }

  @Override
  public void setCancelAction(Object action) {
    progressBar.setCancelAction(action);
  }

  @Override
  public void setInsertOverwrite(boolean insert) {
    if (overwriteInsert != null) {
      overwriteInsert.setText(Platform.getResourceAsString(insert
              ? "Rare.runtime.text.OVR"
              : "Rare.runtime.text.INS"));
      overwriteInsert.revalidate();
    }
  }

  @Override
  public void setInsertOverwriteEnabled(boolean enabled) {
    if (overwriteInsert != null) {
      overwriteInsert.setEnabled(enabled);
    }
  }

  @Override
  public void setMaxHistory(int max) {}

  @Override
  public void setProgressStatus(String msg) {
    progressBar.setProgressStatus(msg);
  }

  @Override
  public void setProgressUpdate(int value) {
    progressBar.setProgress(value);
  }

  @Override
  public iPlatformComponent getComponent() {
    return statusBar;
  }

  @Override
  public int getMaxHistory() {
    return 0;
  }

  @Override
  public String getMessage() {
    CharSequence cs = progressBar.getStatusLabel().getText();

    return (cs == null)
           ? ""
           : cs.toString();
  }

  /**
   * Gets the progress status bar item
   * @return  the progress status bar item
   */
  public iPlatformComponent getProgressStatusBarItem() {
    return progressBar;
  }

  @Override
  public boolean isProgressBarShowing() {
    return progressBar.isShowing();
  }

  @Override
  protected void clearConfiguration(boolean dispose) {
    super.clearConfiguration(dispose);
    defaultStatus = null;

    if (dispose) {
      statusBar       = null;
      progressBar     = null;
      overwriteInsert = null;
    }
  }

  /**
   * Configures the viewer (doe not fire the configure event)
   *
   * @param cfg the viewer configuration
   */
  protected void configureEx(com.appnativa.rare.spot.StatusBar cfg) {
    if (cfg == null) {
      createBasic();

      return;
    }

    statusBar = createStatusBarAndComponents(cfg);
    configureEx(cfg, true, false, true);

    if (cfg.getBorders() == null) {
      setBorder(new StatusBarBorder());
    }

    if (cfg.bgColor.getValue() == null) {
      //ComponentPainter.setBackgroundPainter(this, BackgroundPainter.SIMPLE_BGCOLOR_PAINTER);
      setBackground(new UIColorShade(new UIColor(192, 192, 192), "Rare.background"));
    }

    progressBar = statusBar.getProgressStatusBarItem();
    progressBar.getCancelButton().addActionListener(this);

    SPOTSet actions = cfg.actions;
    String  s;
    int     len = actions.size();

    if (len > 0) {
      ActionItem       item;
      UIAction         a;
      iActionComponent sbitem;
      WindowViewer     w = getAppContext().getWindowViewer();

      for (int i = 0; i < len; i++) {
        item = (ActionItem) actions.getEx(i);
        a    = null;

        if (item.name.spot_hasValue()) {
          a = getAppContext().getAction(item.name.getValue());
        }

        if (a != null) {
          sbitem = (iActionComponent) PushButtonWidget.create(w).getDataComponent();
          sbitem.setAction(a);
          a.configureExtraButtonOptions(sbitem);

          if (item.value.spot_hasValue()) {
            sbitem.setText(item.value.getValue());
          }

          if (item.icon.spot_hasValue()) {
            sbitem.setIcon(getIcon(item.icon));
          }

          if (item.disabledIcon.spot_hasValue()) {
            sbitem.setDisabledIcon(getIcon(item.disabledIcon));
          }

          if (item.selectedIcon.spot_hasValue()) {
            sbitem.setSelectedIcon(getIcon(item.selectedIcon));
          }

          if (item.enabled.spot_valueWasSet()) {
            sbitem.setEnabled(item.enabled.booleanValue());
          }

          if (item.tooltip.getValue() != null) {
            sbitem.setToolTipText(item.tooltip.getValue());
          }
        } else if (item.spot_getAttribute("onAction") == null) {
          sbitem = (iActionComponent) PushButtonWidget.create(w).getDataComponent();
          a      = new UIAction(this, item);
          sbitem.setAction(a);
          a.configureExtraButtonOptions(sbitem);
        } else {
          sbitem = (iActionComponent) LabelWidget.create(w).getDataComponent();
          sbitem.setText(item.name.getValue());

          if (item.tooltip.getValue() != null) {
            sbitem.setToolTipText(item.tooltip.getValue());
          }

          sbitem.setIcon(getIcon(item.icon));
          sbitem.setBorder(label_border);
        }

        s = item.name.getValue();

        if (s != null) {
          registerNamedItem(s, sbitem);
        }

        VerticalAlign   va = VerticalAlign.AUTO;
        HorizontalAlign ha = HorizontalAlign.AUTO;

        s = item.spot_getAttribute("textVAlignment");

        if (s != null) {
          va = VerticalAlign.valueOf(s.toUpperCase(Locale.US));
        }

        s = item.spot_getAttribute("textHAlignment");

        if (s != null) {
          ha = HorizontalAlign.valueOf(s.toUpperCase(Locale.US));
        }

        sbitem.setAlignment(ha, va);
        s = item.spot_getAttribute("iconPosition");

        if (s != null) {
          sbitem.setIconPosition(IconPosition.valueOf(s.toUpperCase(Locale.US)));
        }

        statusBar.addComponent(sbitem, i + 2, 0);
      }
    }

    if (cfg.showInsertOverwrite.booleanValue()) {
      overwriteInsert = (iActionComponent) LabelWidget.create(this, null).getDataComponent();
      overwriteInsert.setEnabled(false);
      statusBar.addComponent(overwriteInsert, len + 2, 0);
    }

    s = cfg.defaultMessage.getValue();

    if (s != null) {
      defaultStatus = s;
      progressBar.setDefaultStatus(defaultStatus);
    }

    if (getAppContext().areAllLabelsDraggable() || getAppContext().areAllWidgetsDraggable()) {
      draggingAllowed = true;
    }

    this.configureGenericDnD(statusBar, cfg);
  }

  @Override
  protected void initializeListeners(aWidgetListener l) {
    if ((l != null) && l.isActionEventEnabled()) {
      if (progressBar != null) {
        progressBar.setCancelAction(l);
      }
    }
  }

  protected abstract aStatusBar createStatusBarAndComponents(StatusBar cfg);

  @Override
  protected void uninitializeListeners(aWidgetListener l) {
    super.uninitializeListeners(l);

    if ((l != null) && l.isActionEventEnabled()) {
      if (progressBar != null) {
        progressBar.setCancelAction(null);
      }
    }
  }
}
