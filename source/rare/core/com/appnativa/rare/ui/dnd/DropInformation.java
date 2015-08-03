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

package com.appnativa.rare.ui.dnd;

import com.appnativa.rare.Platform;
import com.appnativa.rare.platform.PlatformHelper;
import com.appnativa.rare.ui.UIPoint;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.util.FilterableList;
import com.appnativa.util.Streams;

import java.io.File;
import java.io.IOException;
import java.io.Reader;

import java.net.URL;

import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Holds Information pertaining to a drop operation during Drag and Drop.
 * The only data not set by the Drag and Drop handler is the user data value.
 *
 * @author Don DeCoteau
 */
public class DropInformation {
  private int           dropAction = DnDConstants.ACTION_NONE;
  private int           dropColumn = -1;
  private int           dropIndex  = -1;
  private UIPoint       dropPoint;
  private boolean       insertAtEnd;
  private boolean       insertMode;
  private iWidget       sourceWidget;
  private iWidget       targetWidget;
  private iTransferable transferable;
  private Object        userData;

  public DropInformation(iWidget source) {
    sourceWidget = source;
  }

  /**
   * Clears the contents of the object
   */
  public void clear() {
    transferable = null;
    insertMode   = false;
    dropColumn   = -1;
    dropIndex    = -1;
    dropPoint    = null;
    dropAction   = DnDConstants.ACTION_NONE;
    userData     = null;
    targetWidget = null;
  }

  /**
   * Sets the drop action
   *
   * @param dropAction the drop action
   *
   * @see DnDConstants
   */
  public void setDropAction(int dropAction) {
    this.dropAction = dropAction;
  }

  /**
   * Sets the drop column
   *
   * @param column the drop column
   */
  public void setDropColumn(int column) {
    this.dropColumn = column;
  }

  /**
   * Sets the drop index
   *
   * @param index the drop index
   */
  public void setDropIndex(int index) {
    this.dropIndex = index;
  }

  /**
   * Sets the drop point
   *
   * @param point the drop point
   */
  public void setDropPoint(UIPoint point) {
    this.dropPoint = point;
  }

  /**
   * Sets whether the data should be inserted at the end of the drop target
   * @param insertAtEnd true if the data should be inserted at the end of the
   *        drop target; false otherwise
   */
  public void setInsertAtEnd(boolean insertAtEnd) {
    this.insertAtEnd = insertAtEnd;
  }

  /**
   * Sets whether insert mode is enabled for this drop
   *
   * @param insertMode true if insert mode is enabled for this drop; false otherwise
   */
  public void setInsertMode(boolean insertMode) {
    this.insertMode = insertMode;
  }

  /**
   * Sets the drop source widget
   * @param w the drop source widget
   */
  public void setSourceWidget(iWidget w) {
    this.sourceWidget = w;
  }

  /**
   * Sets the drop target widget
   * @param w the drop target widget
   */
  public void setTargetWidget(iWidget w) {
    this.targetWidget = w;
  }

  /**
   * Sets the transferable
   *
   * @param transferable the transferable
   */
  public void setTransferable(iTransferable transferable) {
    this.transferable = transferable;
  }

  /**
   * Sets the user data associated with the drop
   * @param data the user data associated with the drop
   */
  public void setUserData(Object data) {
    this.userData = data;
  }

  /**
   * Gets the dropped data
   *
   * @param name the name of the data flavor
   *
   * @return the data or null if the name of the flavor does not map to
   *         a valid data flavor or a transfer is not in progress
   *
   * @throws IOException if the data is no longer available in the requested flavor
   * @throws UnsupportedFlavorException if the requested data flavor is not supported.
   */
  public Object getData(String name) throws UnsupportedFlavorException, IOException {
    if (transferable != null) {
      TransferFlavor flavor = TransferFlavor.getDataFlavor(transferable.getTransferFlavors(), name);

      return (flavor == null)
             ? null
             : transferable.getTransferData(flavor);
    }

    return null;
  }

  /**
   * Gets the dropped data
   *
   * @param flavor the  data flavor
   *
   * @return the data or null if the name of the flavor does not map to
   *         a valid data flavor or a transfer is not in progress
   *
   * @throws IOException if the data is no longer available in the requested flavor
   * @throws UnsupportedFlavorException if the requested data flavor is not supported.
   */
  public Object getData(TransferFlavor flavor) throws UnsupportedFlavorException, IOException {
    return ((transferable == null) || (flavor == null))
           ? null
           : transferable.getTransferData(flavor);
  }

  /**
   * Gets the drop action
   *
   * @return the drop action
   *
   * @see DnDConstants
   */
  public int getDropAction() {
    return dropAction;
  }

  /**
   * Gets the column being dropped on
   *
   * @return the column being dropped on; or -1 if unknown
   */
  public int getDropColumn() {
    return dropColumn;
  }

  /**
   * Gets the index(row) being dropped on
   *
   * @return the index(row) being dropped on; or -1 if unknown
   */
  public int getDropIndex() {
    return dropIndex;
  }

  /**
   * Gets the point being dropped on
   *
   * @return the point being dropped on; or null if unknown
   */
  public UIPoint getDropPoint() {
    return dropPoint;
  }

  /**
   * Gets the widget that initiated the drag operation
   * @return the widget that initiated the drag operation or null if the widget is unknown
   * or external to this application
   */
  public iWidget getSourceWidget() {
    if (sourceWidget != null) {
      return sourceWidget;
    }

    if ((transferable != null) && transferable.isTransferFlavorSupported(TransferFlavor.widgetFlavor)) {
      try {
        return (iWidget) transferable.getTransferData(TransferFlavor.widgetFlavor);
      } catch(Exception e) {
        Platform.ignoreException(null, e);
      }
    }

    return null;
  }

  /**
   * Gets the drop target widget
   * @return the drop target widget
   */
  public iWidget getTargetWidget() {
    return targetWidget;
  }

  /**
   * Gets the dropped data as a string of text
   *
   * @return the dropped data as a string of text
   *
   * @throws IOException if the data is no longer available in the requested flavor
   * @throws UnsupportedFlavorException if the requested data flavor is not supported
   */
  public String getText() throws UnsupportedFlavorException, IOException {
    String data = null;

    do {
      if (transferable == null) {
        break;
      }

      Reader in = TransferFlavor.stringFlavor.getReaderForText(transferable);

      data = Streams.readerToString(in);
    } while(false);

    return data;
  }

  /**
   * Gets the transferable object
   *
   * @return the transferable object
   */
  public iTransferable getTransferable() {
    return transferable;
  }

  /**
   * Gets the dropped data as a URL
   *
   * @return the dropped data as a URL or null if a transfer is not in progress
   *
   * @throws IOException if the data is no longer available in the requested flavor
   * @throws UnsupportedFlavorException if the requested data flavor is not supported
   */
  public URL getURL() throws UnsupportedFlavorException, IOException {
    List<URL> list = getURLList();

    return ((list != null) && (list.size() > 0))
           ? list.get(0)
           : null;
  }

  /**
   * Gets the dropped data as a URL
   *
   * @param t the transferable
   *
   * @return the dropped data as a URL or null if a transfer is not in progress
   *
   * @throws IOException if the data is no longer available in the requested flavor
   * @throws UnsupportedFlavorException if the requested data flavor is not supported
   */
  public static URL getURL(iTransferable t) throws IOException, UnsupportedFlavorException {
    List<URL> l = getURLList(t);

    return ((l != null) && (l.size() > 0))
           ? l.get(0)
           : null;
  }

  /**
   * Gets the dropped data as a list of URLs
   *
   * @return the dropped data as a list of URLs or null if a transfer is not in progress
   *
   * @throws IOException if the data is no longer available in the requested flavor
   * @throws UnsupportedFlavorException if the requested data flavor is not supported
   */
  public List<URL> getURLList() throws UnsupportedFlavorException, IOException {
    if (transferable == null) {
      return null;
    }

    List<URL> list = null;
    String    uris = null;

    if (transferable.isTransferFlavorSupported(TransferFlavor.urlFlavor)) {
      list = Collections.singletonList((URL) transferable.getTransferData(TransferFlavor.urlFlavor));
    } else if (transferable.isTransferFlavorSupported(TransferFlavor.urlListFlavor)) {
      List<File> ls = (List) transferable.getTransferData(TransferFlavor.urlListFlavor);

      list = new FilterableList<URL>();

      for (File f : ls) {
        list.add(PlatformHelper.fileToURL(f));
      }
    } else {
      Reader in = TransferFlavor.stringFlavor.getReaderForText(transferable);

      uris = Streams.readerToString(in);

      if (uris != null) {
        list = new FilterableList<URL>();

        StringTokenizer st = new StringTokenizer(uris, "\r\n");

        while(st.hasMoreTokens()) {
          String url = st.nextToken();

          if (url != null) {
            list.add(new URL(url));
          }
        }
      }
    }

    return list;
  }

  /**
   * Gets the dropped data as a list of URLs
   *
   * @param t the transferable
   *
   * @return the dropped data as a list of URLs or null if a transfer is not in progress
   *
   * @throws IOException if the data is no longer available in the requested flavor
   * @throws UnsupportedFlavorException if the requested data flavor is not supported
   */
  public static List<URL> getURLList(iTransferable t) throws IOException, UnsupportedFlavorException {
    if (t.isTransferFlavorSupported(TransferFlavor.urlListFlavor)) {
      return (List<URL>) t.getTransferData(TransferFlavor.urlListFlavor);
    }

    if (t.isTransferFlavorSupported(TransferFlavor.urlFlavor)) {
      return Collections.singletonList((URL) t.getTransferData(TransferFlavor.urlFlavor));
    }

    return null;
  }

  /**
   * Gets the user data associated with the drop
   * @return the user data associated with the drop
   */
  public Object getUserData() {
    return userData;
  }

  /**
   * Returns whether the named data flavor is supported
   * @param name the name of the data flavor
   * @return true if the named data flavor is supported; false otherwise
   */
  public boolean isFlavorSupported(String name) {
    return (transferable == null)
           ? false
           : TransferFlavor.isDataFlavorSupported(transferable.getTransferFlavors(), name);
  }

  /**
   * Gets whether the data should be inserted at the end of the drop target
   * @return true if the data should be inserted at the end of the drop target;
   *         false otherwise
   */
  public boolean isInsertAtEnd() {
    return insertAtEnd;
  }

  /**
   * Gets whether insert mode is enabled for this drop
   *
   * @return insertMode true if insert mode is enabled for this drop; false otherwise
   */
  public boolean isInsertMode() {
    return insertMode;
  }

  /**
   * Returns whether the drop action is a move
   * @return true if the drop action is a move; false otherwise
   */
  public boolean isMoveAction() {
    return (dropAction & DnDConstants.ACTION_MOVE) > 0;
  }
}
