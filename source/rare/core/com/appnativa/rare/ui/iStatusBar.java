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

/**
 * Interface for status bars
 *
 * @author     Don DeCoteau
 */
public interface iStatusBar {

  /**
   * Configures the status bar for use in a popup component
   */
  public void configureForPopup();

  /**
   * Disposes of the status bar
   */
  public void dispose();

  /**
   * Notifies the status bar that the previously started operation has been aborted
   * Will trigger the cancelAction (if there is one)
   */
  public void progressAbort();

  /**
   * Notifies the status bar that the previously started operation is complete
   * Will trigger the completeAction (if there is one)
   */
  public void progressComplete();

  /**
   * Notifies the status bar that of operation for which a progress indicator
   * should be displayed has been started. This method assumes that the progress tracking
   * is determinate and that the status will be updated using the <code>progressUpdate</code> method
   *
   * @param showCancelButton whether the cancel button should be shown
   * @see #setProgressUpdate
   */
  public void progressStart(boolean showCancelButton);

  /**
   * Notifies the status bar that of operation for which a progress indicator
   * should be displayed has been started.
   *
   * @param indeterminate true for an indeterminate progress bar; false otherwise
   * @param message the initial status message
   * @param cancelAction the action toe execute when the operation is canceled
   *
   * @see #setProgressUpdate
   * @see #progressAbort
   */
  public void progressStart(boolean indeterminate, String message, Object cancelAction);

  /**
   * Notifies the status bar that of operation for which a progress indicator
   * should be displayed has been started.
   *
   * @param indeterminate true for an indeterminate progress bar; false otherwise
   * @param message the initial status message
   * @param cancelAction the action toe execute when the operation ic canceled
   * @param completeAction the action toe execute when the operation is completed
   *
   * @see #setProgressUpdate
   * @see #progressAbort
   * @see #progressComplete
   */
  public void progressStart(boolean indeterminate, String message, Object cancelAction, Object completeAction);

  /**
   * Notifies the status bar that of operation for which a progress indicator
   * should be displayed has been started. This method assumes that the progress tracking
   * is indeterminate
   *
   * @param showCancelButton whether the cancel button should be shown
   */
  public void progressStartIndeterminate(boolean showCancelButton);

  /**
   *
   * Shows a message on the status bar
   * @param msg the message to show
   */
  public void showMessage(String msg);

  /**
   * Toggles the visibility of the status bar
   */
  public void toggleVisibility();

  /**
   * Sets the code to invoke when the user presses the cancel button
   *
   * @param action the action to invoke when the use cancels the operation
   */
  public void setCancelAction(Object action);

  /**
   * Sets the status of an insert/overwrite status bar item
   *
   * @param insert true for insert mode; false for overwrite
   */
  public void setInsertOverwrite(boolean insert);

  /**
   * Enables the Insert/Overwrite status bar item
   *
   * @param enabled true if the item is to be enabled; false otherwise
   */
  public void setInsertOverwriteEnabled(boolean enabled);

  /**
   * Enables the specified status bar item
   *
   * @param name the name of the item
   * @param enabled true if the item is to be enabled; false otherwise
   */
  public void setItemEnabled(String name, boolean enabled);

  /**
   * Sets the value of the specified status bar item
   *
   * @param name the name of the item
   * @param value the item's value
   */
  public void setItemValue(String name, Object value);

  /**
   * Sets the maximum number of items to maintain in the history
   * @param max the maximum number of items to maintain in the history
   */
  public void setMaxHistory(int max);

  /**
   * Notifies the status bar of an operations progress
   *
   * @param msg the status message
   */
  public void setProgressStatus(String msg);

  /**
   * Notifies the status bar of an operations progress
   *
   * @param value a value between 1 and 100. At 100 the operation is considered complete
   */
  public void setProgressUpdate(int value);

  /**
   * Sets the visibility of the status bar
   *
   * @param visible true to make the status bar visible; false otherwise
   */
  public void setVisible(boolean visible);

  /**
   * Gets the status bar component
   *
   * @return the status bar component
   */
  public iPlatformComponent getComponent();

  /**
   * Gets the value of the specified status bar item
   *
   * @param name the name of the item
   *
   * @return the item's value
   */
  public Object getItemValue(String name);

  /**
   * Gets the maximum number of items to maintain in the history
   *
   * @return the maximum number of items to maintain in the history
   */
  public int getMaxHistory();

  /**
   * Gets the currently displayed status bar message
   *
   * @return the currently displayed status bar message
   */
  public String getMessage();

  /**
   * Returns whether the specified status bar item is enabled
   *
   * @param name the name of the item
   *
   * @return  true if the item is enabled; false otherwise
   */
  public boolean isItemEnabled(String name);

  /**
   * Returns whether the progress bar is showing
   *
   * @return  true if the progress bar is showing; false otherwise
   */
  public boolean isProgressBarShowing();
}
