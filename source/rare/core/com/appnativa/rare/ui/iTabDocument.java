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

import com.appnativa.rare.iFunctionCallback;
import com.appnativa.rare.net.ActionLink;
import com.appnativa.rare.ui.painter.PaintBucket;
import com.appnativa.rare.viewer.iTabPaneViewer;
import com.appnativa.rare.viewer.iTarget;
import com.appnativa.rare.viewer.iViewer;
import com.appnativa.rare.widget.iWidget;

/**
 * An interface for tab documents.
 *
 * @author Don DeCoteau
 */
public interface iTabDocument {

  /**
   * Sets the icon to use when the tab is disabled
   *
   * @param icon
   *          the icon to use when the tab is disabled
   */
  public void setDisabledIcon(iPlatformIcon icon);

  /**
   * Called to notify the document that it is about to become the current
   * document
   *
   * @param context
   *          the calling context
   * @param cb
   *          a callback if the change has to happen asynchronously
   * @return true if the change has to happen asynchronously; false if the
   *         change can happen now
   */
  boolean asyncCanChange(iWidget context, iCanChangeCallback cb);

  /**
   * Called to dispose of the tab
   */
  void dispose();

  /**
   * Gets the alternate icon for the tab
   *
   * @return the alternate icon for the tab
   */
  iPlatformIcon getAlternateIcon();

  /**
   * Gets the icon to use when the tab is disabled
   *
   * @return the icon to use when the tab is disabled
   */
  iPlatformIcon getDisabledIcon();

  /**
   * Get the component associated with the document
   *
   * @return the component associated with the document
   */
  iPlatformComponent getDocComponent();

  /**
   * Gets the icon for the tab
   *
   * @return the icon for the tab
   */
  iPlatformIcon getIcon();

  /**
   * Gets the data that is associated/linked with tab
   *
   * @return the data that is associated/linked with tab
   */
  Object getLinkedData();

  /**
   * Gets the amount of time before a document is considered stale and should be
   * reloaded
   *
   * @return the reload time out or zero for no time out
   */
  long getReloadTimeout();

  /**
   * Gets the tab colors
   *
   * @return the tab colors
   */
  PaintBucket getTabColors();

  /**
   * Get the index of the tab that this document belongs to
   *
   * @return the index of the tab that this document belongs to
   */
  int getTabIndex();

  /**
   * Get the name of the tab that this document belongs to
   *
   * @return the name of the tab that this document belongs to
   */
  String getTabName();

  /**
   * Gets the tab pane viewer for this tab document
   *
   * @return the tab pane viewer for this tab document
   */
  iTabPaneViewer getTabPaneViewer();

  /**
   * Gets the tab's target
   *
   * @return the tab's target
   */
  iTarget getTarget();

  /**
   * Gets the tab's title
   *
   * @return the tab's title
   */
  String getTitle();

  /**
   * Gets the viewer hosted by the tab
   *
   * @return the viewer hosted by the tab
   */
  iViewer getViewer();

  /**
   * Gets the viewer for the document. If the viewer is not yet loaded the load
   * will start and the callback function called when the viewer is loaded.
   *
   * @param cb
   *          the callback to use (cannot be null)
   *
   * @return the viewer at the specified index or null if the viewer is not yet
   *         loaded
   */
  iViewer getViewer(iFunctionCallback cb);

  /**
   * Returns whether closing the tab is allowed. This method is called after an
   * <code>onWillClose</code> event was fired The event handler can call
   * <code>setCanClose</code> to control whether the tab is allowed to be closed
   *
   * @return true closing the tab is allowed; false otherwise
   * @see #setCanClose
   */
  boolean isClosingAllowed();

  /**
   * Reloads the tab. If a callback is specified, it will be called when the tab
   * is loaded
   *
   * @param cb
   *          the callback to be notified when the tab is reloaded
   */
  void reload(iFunctionCallback cb);

  /**
   * Resets the tabs viewer
   */
  void reset();

  /**
   * Sets the link to use to create the tab's viewer
   *
   * @param link
   *          the Action link
   */
  void setActionLink(ActionLink link);

  /**
   * Sets the alternate icon for the tab
   *
   * @param icon
   *          the alternate icon for the tab
   */
  void setAlternateIcon(iPlatformIcon icon);

  /**
   * Sets whether the document can be closed This is meant to be called during
   * an <code>onWillClose </code> event for the document Calling
   * <code>setCanClose(false) </code> will prevent the document from closing
   *
   * @param can
   *          true if the document can be closed; false otherwise.
   */
  void setCanClose(boolean can);

  /**
   * Sets the tab's icon. This method is for internal use and does not change
   * the icon in the UI. Scripts should use
   * <code>iTabPaneViewer.setTabIcon() </code> to change a tab's icon.
   *
   * @param icon
   *          the icon
   * @see iTabPaneViewer#setTabIcon
   */
  void setIcon(iPlatformIcon icon);

  /**
   * Sets the data that is associated/linked with tab
   *
   * @param data
   *          the data to be associated/linked with tab
   */
  void setLinkedData(Object data);

  /**
   * Sets whether the tab is automatically reloaded every time it is activated
   *
   * @param reload
   *          true if the tab is automatically reloaded every time it is
   *          activated; false otherwise
   */
  void setReloadOnActivation(boolean reload);

  /**
   * Sets the amount of time before a document is considered stale and should be
   * reloaded
   *
   * @param reloadTimeout
   *          the reload time out or zero for no timeout
   */
  void setReloadTimeout(long reloadTimeout);

  /**
   * Sets the tab colors
   *
   * @param colors
   *          the tab colors
   */
  void setTabColors(PaintBucket colors);

  /**
   * Sets the tab's name
   *
   * @param name
   *          a unique name for the tab
   */
  void setTabName(String name);

  /**
   * Sets the tab pane viewer for this document
   *
   * @param viewer
   *          the tab pane viewer
   */
  void setTabPaneViewer(iTabPaneViewer viewer);

  /**
   * Sets the tab's title. This method is for internal use and does no change
   * the title in the UI. Scripts should use
   * <code>iTabPaneViewer.setTabTitle() </code> to change a tab's title.
   *
   * @param title
   *          the title
   * @see iTabPaneViewer#setTabTitle
   */
  void setTitle(String title);

  /**
   * The tab has been activated
   */
  void tabActivated();

  /**
   * The tab has been closed
   */
  void tabClosed();

  /**
   * The tab has been deactivated
   */
  void tabDeactivated();

  /**
   * The tab has been opened
   */
  void tabOpened();

  /**
   * Interface for handling asynchronous changes
   *
   */
  public interface iCanChangeCallback {
    void canChange(iWidget context, iTabDocument doc);

    void errorHappened(iWidget context, iTabDocument doc, Exception e);
  }


  public interface iDocumentListener extends iTarget.iListener {
    void documentChanged(iTabDocument doc);
  }
}
