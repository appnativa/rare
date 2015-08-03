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

import com.appnativa.rare.iFunctionCallback;
import com.appnativa.rare.ui.Location;
import com.appnativa.rare.ui.UIRectangle;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.iPlatformIcon;
import com.appnativa.rare.ui.iTabDocument;
import com.appnativa.rare.ui.iTabPaneComponent;
import com.appnativa.rare.ui.listener.iTabPaneListener;

import java.net.URL;

/**
 * Interface  for tab pane viewers.
 *
 * @author Don DeCoteau
 */
public interface iTabPaneViewer {
  public enum Shape {
    DEFAULT, CHROME, BOX, BOX_STACKED, FLAT, OFFICE2003, ROUNDED_FLAT, WINDOWS, CUSTOM
  }

  /**
   * Add a new tab to the viewer
   *
   * @param name the name of the tab
   * @param title the title for the tab
   * @param icon the icon for the tab
   *
   * @return the position of the newly added tab
   */
  public int addTab(String name, String title, iPlatformIcon icon);

  /**
   * Add a new tab to the viewer
   *
   * @param name the name of the tab
   * @param title the title for the tab
   * @param icon the icon for the tab
   * @param comp the component for the tab
   *
   * @return the position of the newly added tab
   */
  public int addTab(String name, String title, iPlatformIcon icon, iPlatformComponent comp);

  /**
   * Add a new tab to the viewer
   *
   * @param name the name of the tab
   * @param title the title for the tab
   * @param icon the icon for the tab
   * @param v the viewer for the tab
   *
   * @return the position of the newly added tab
   */
  public int addTab(String name, String title, iPlatformIcon icon, iViewer v);

  /**
   * Add a new tab to the viewer
   *
   * @param name the name of the tab
   * @param title the title for the tab
   * @param icon the icon for the tab
   * @param url the URL from which to load the tab's viewer
   * @param load true to load the tab now; false to wait until the tab is activated
   *
   * @return the index of the newly added tab
   */
  public int addTab(String name, String title, iPlatformIcon icon, URL url, boolean load);

  /**
   *  Adds a listener for tab pane generated events
   *
   *  @param l the listener to add
   */
  public void addTabPaneListener(iTabPaneListener l);

  /**
   * Closes all tabs
   */
  public void closeAll();

  /**
   * Closes all tabs except the tab at the specified position
   *
   * @param pos the position of the tab to keep open
   */
  public void closeAllBut(int pos);

  /**
   * Closes all tabs except the tab with the specified name
   *
   * @param name the name of the tab to keep open
   */
  public void closeAllBut(String name);

  /**
   * Closes the tab at the specified position
   *
   * @param pos the position of the tab to close
   */
  public void closeTab(int pos);

  /**
   * Closes the tab with the specified name
   *
   * @param name the name
   */
  public void closeTab(String name);

  /**
   * Returns the index of the tab with the specified icon
   *
   * @param icon the icon
   *
   * @return the index of the tab with the specified icon
   */
  public int indexForIcon(iPlatformIcon icon);

  /**
   * Returns the index of the tab with the specified name
   *
   * @param name the name of the tab
   *
   * @return the index of the tab with the specified name
   */
  public int indexForName(String name);

  /**
   *   Returns the index of the tab with the specified root viewer
   *
   *   @param v the viewer
   *
   *   @return the index of the tab with the specified root viewer
   */
  public int indexForTabViewer(iViewer v);

  /**
   * Returns the index of the tab with the specified name
   *
   * @param title the title
   *
   * @return the index of the tab with the specified name
   */
  public int indexForTitle(String title);

  /**
   *   Returns the index of the tab with the specified document
   *
   *   @param doc the document
   *
   *   @return the index of the tab with the specified document
   */
  public int indexOf(iTabDocument doc);

  /**
   *  Removes a tab pane listener
   *
   *  @param l the listener to remove
   */
  public void removeTabPaneListener(iTabPaneListener l);

  /**
   * Selects the tab at the specified index
   *
   * @param index the index of the tab to select
   */
  public void setSelectedTab(int index);

  /**
   * Enables or disables the tab at the specified position
   *
   * @param pos the position
   * @param enable true to enable the tab; false to disable it
   */
  public void setTabEnabled(int pos, boolean enable);

  /**
   * Sets the icon of the tab at the specified position
   *
   * @param pos the position
   * @param icon the icon
   */
  public void setTabIcon(int pos, iPlatformIcon icon);

  /**
   * Sets the name of the tab at the specified position
   *
   * @param pos the position
   * @param name the name
   */
  public void setTabName(int pos, String name);

  /**
   * Sets the tab location
   *
   * @param location the location
   */
  public void setTabPlacement(Location location);

  /**
   * Sets the title of the tab at the specified position
   *
   * @param pos the position
   * @param title the title
   */
  public void setTabTitle(int pos, String title);

  /**
   * Sets the tooltip of the tab at the specified position
   *
   * @param pos the position
   * @param tooltip the tooltip
   */
  public void setTabToolTip(int pos, String tooltip);

  /**
   * Sets the viewer of the tab at the specified position
   *
   * @param pos the position
   * @param v the viewer
   */
  public void setTabViewer(int pos, iViewer v);

  /**
   * Get the index of the selected tab (pending changes are ignored).
   *
   * @return the index of the selected tab
   */
  public int getSelectedTab();

  /**
   * Get the selected tab document (pending changes are ignored).
   *
   * @return the selected tab document
   */
  public iTabDocument getSelectedTabDocument();

  /**
   * Get the viewer of the currently selected tab (pending changes are ignored).
   *
   * @return the viewer or null if no tab is selected or the viewer is not yet loaded
   */
  public iViewer getSelectedTabViewer();

  /**
   * Gets the view that is currently selected or will become selected once it is finished loading.
   * If the view is not yet loaded the callback will be invoked when the view is loaded.
   *
   * @param cb
   *          the callback to use (cannot be null)
   *
   * @return the viewer selected viewer null if the viewer is not yet loaded or there is no selection
   */
  public iViewer getSelectedTabViewer(iFunctionCallback cb);

  /**
   * Returns the current number of tabs
   * @return the current number of tabs
   */
  public int getTabCount();

  /**
   * Get the tab document at the specified position
   *
   * @param pos the position
   *
   * @return the document
   */
  public iTabDocument getTabDocument(int pos);

  /**
   * Gets the icon of the tab at the specified position
   *
   * @param pos the position
   *
   * @return the icon
   */
  public iPlatformIcon getTabIcon(int pos);

  /**
   * Gets the tab location
   *
   * @return  the location
   */
  public Location getTabPlacement();

  /**
   * Gets the title of the tab at the specified position
   *
   * @param pos the position
   *
   * @return the title
   */
  public String getTabTitle(int pos);

  /**
   * Get the viewer of the tab at the specified position
   *
   * @param pos the position
   *
   * @return the viewer
   */
  public iViewer getTabViewer(int pos);

  /**
   * Gets the viewer at the specified index. If the viewer is not yet loaded the
   * load will start and the callback function called when the viewer is loaded.
   *
   * @param index
   *          the index of the viewer to retrieve
   *
   * @param cb
   *          the callback to use (cannot be null)
   *
   * @return the viewer at the specified index or null if the viewer is not yet
   *         loaded
   */
  public iViewer getTabViewer(int index, iFunctionCallback cb);

  /**
   * Gets the component that manages the tabs
   *
   * @return the component that manages the tabs
   */
  public iTabPaneComponent getTabPaneComponent();

  /**
   * Gets the tab pane's <code>iViewer</code> instance
   *
   * @return the tab pane's <code>iViewer</code> instance
   */
  public iViewer getViewer();

  /**
   * Returns whether a tab change that is loads its contents via a network URL
   * is cancel-able
   */
  public boolean isTabChangeCancelable();

  /**
   * Returns the bounds of the tab strip including any leading or
   * trailing components
   *
   * @return the bounds
   */
  public UIRectangle getTabStripBounds();
}
