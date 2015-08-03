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
import com.appnativa.rare.spot.Label;
import com.appnativa.rare.spot.PushButton;
import com.appnativa.rare.ui.event.ActionEvent;
import com.appnativa.rare.ui.event.iActionListener;
import com.appnativa.rare.viewer.WindowViewer;
import com.appnativa.rare.viewer.aStatusBarViewer;
import com.appnativa.rare.widget.LabelWidget;
import com.appnativa.rare.widget.PushButtonWidget;
import com.appnativa.util.iCancelable;

public abstract class aStatusBar extends FormsPanel {
  protected ProgressStatusBarItem progressStatusBar;

  public aStatusBar(Object view) {
    super(view);
  }

  @Override
  public void dispose() {
    super.dispose();
    progressStatusBar = null;
  }

  public void configure(aStatusBarViewer viewer, int history) {
    setLayout("FILL:DEFAULT:GROW(1.0),d,d", "d");
    progressStatusBar = new ProgressStatusBarItem(viewer, createProgressBar(), history);
    addComponent(progressStatusBar, 0, 0);
  }

  protected abstract iProgressBar createProgressBar();

  public ProgressStatusBarItem getProgressStatusBarItem() {
    return progressStatusBar;
  }
//
//  public static class OvrInsStatusBarItem extends ActionComponent {
//    public OvrInsStatusBarItem() {
//      super(new Label(Platform.getResourceAsString("Rare.runtime.text.INS")));
//    }
//
//    public void setOvrMode(boolean over) {
//      setText(Platform.getResourceAsString(over
//              ? "Rare.runtime.text.OVR"
//              : "Rare.runtime.text.INS"));
//      revalidate();
//      repaint();
//    }
//  }

  public static class ProgressStatusBarItem extends BorderPanel {
    private static UIInsets insets = new UIInsets(0, 8, 0, 0);
    iActionComponent        cancelButton;
    iProgressBar            progressBar;
    iActionComponent        statusLabel;
    aStatusBarViewer        viewer;
    private String          defaultStatus = "";
    private Object          cancelAction;
    private Object          completeAction;
    private boolean         showIconOnly;

    public ProgressStatusBarItem(aStatusBarViewer viewer, iProgressBar progress, int history) {
      super();
      this.viewer  = viewer;
      progressBar  = progress;
      cancelButton = createCancelButton();
      statusLabel  = createStatusLabel();
      statusLabel.setMargin(insets);
      setLeftView(statusLabel);
      setCenterView(progressBar.getComponent());
      setRightView(cancelButton);
      progressBar.getComponent().setVisible(false);
      cancelButton.setVisible(false);
    }

    public void cancelPerformed() {
      Object action = this.cancelAction;

      this.setStatus(getDefaultStatus());    // nullifies action

      try {
        if (action instanceof iCancelable) {
          ((iCancelable) action).cancel(true);
        } else if (action instanceof iActionListener) {
          ((iActionListener) action).actionPerformed(new ActionEvent(viewer, "pregressBarCancelButton"));
        } else if (action != null) {
          aWidgetListener.evaluate(viewer, viewer.getScriptHandler(), action, null);
        }
      } catch(Exception ex) {
        Platform.ignoreException(null, ex);
      } finally {
        action = null;
      }
    }

    public void completePerformed() {
      Object action = this.completeAction;

      this.setStatus(getDefaultStatus());    // nullifies action

      try {
        if (action instanceof iCancelable) {
          ((iCancelable) action).cancel(true);
        } else if (action instanceof iActionListener) {
          ((iActionListener) action).actionPerformed(new ActionEvent(viewer, "progressBarComplete"));
        } else if (action != null) {
          aWidgetListener.evaluate(viewer, viewer.getScriptHandler(), action, null);
        }
      } catch(Exception ex) {
        Platform.ignoreException(null, ex);
      } finally {
        action = null;
      }
    }

    public void setCancelAction(Object action) {
      cancelAction = action;
    }

    public void setCompleteAction(Object action) {
      completeAction = action;
    }

    public void setDefaultStatus(String status) {
      defaultStatus = status;
    }

    public void setIndeterminate(boolean indeterminate) {
      progressBar.setIndeterminate(indeterminate);
    }

    public void setProgress(int value) {
      progressBar.setValue(value);
    }

    public void setProgressStatus(String status) {
      progressBar.getComponent().setVisible(true);
      statusLabel.setText(status);
      revalidate();
      repaint();
    }

    /**
     * @param showIconOnly the showIconOnly to set
     */
    public void setShowIconOnly(boolean showIconOnly) {
      this.showIconOnly = showIconOnly;
    }

    public void setStatus(String status) {
      cancelAction   = null;
      completeAction = null;
      progressBar.getComponent().setVisible(false);
      cancelButton.setVisible(false);
      statusLabel.setText(status);
      revalidate();
      repaint();
    }

    public iActionComponent getCancelButton() {
      return cancelButton;
    }

    public String getDefaultStatus() {
      return defaultStatus;
    }

    public iProgressBar getProgressBar() {
      return progressBar;
    }

    public iActionComponent getStatusLabel() {
      return statusLabel;
    }

    @Override
    public boolean isFocusable() {
      return false;
    }

    /**
     * @return the showIconOnly
     */
    public boolean isShowIconOnly() {
      return showIconOnly;
    }

    protected final iActionComponent createCancelButton() {
      WindowViewer w   = Platform.getAppContext().getWindowViewer();
      PushButton   cfg = (PushButton) w.createConfigurationObject("PushButton");

      cfg.value.setValue(w.getAppContext().getResourceAsString("Rare.text.cancel"));

      iActionComponent b = (iActionComponent) PushButtonWidget.create(w, cfg).getDataComponent();

      b.addActionListener(new iActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {}
      });

      return b;
    }

    protected final iActionComponent createStatusLabel() {
      WindowViewer w   = Platform.getAppContext().getWindowViewer();
      Label        cfg = (Label) w.createConfigurationObject("Label");

      return (iActionComponent) LabelWidget.create(viewer, cfg).getDataComponent();
    }
  }
}
