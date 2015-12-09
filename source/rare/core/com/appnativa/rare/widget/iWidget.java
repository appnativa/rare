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

package com.appnativa.rare.widget;

import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.EventObject;
import java.util.Map;

import com.appnativa.rare.iCancelableFuture;
import com.appnativa.rare.iDataItemParserCallback;
import com.appnativa.rare.iFunctionCallback;
import com.appnativa.rare.iPlatformAppContext;
import com.appnativa.rare.net.ActionLink;
import com.appnativa.rare.scripting.WidgetContext;
import com.appnativa.rare.scripting.iScriptHandler;
import com.appnativa.rare.scripting.iScriptingContextSupport;
import com.appnativa.rare.spot.DataItem;
import com.appnativa.rare.spot.Widget;
import com.appnativa.rare.ui.RenderType;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.UIFont;
import com.appnativa.rare.ui.UIImage;
import com.appnativa.rare.ui.UIPoint;
import com.appnativa.rare.ui.iPlatformBorder;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.iPlatformIcon;
import com.appnativa.rare.ui.iPopup;
import com.appnativa.rare.ui.iTabDocument;
import com.appnativa.rare.ui.dnd.DragEvent;
import com.appnativa.rare.ui.dnd.DropInformation;
import com.appnativa.rare.ui.dnd.TransferFlavor;
import com.appnativa.rare.ui.dnd.iTransferable;
import com.appnativa.rare.ui.effects.iPlatformAnimator;
import com.appnativa.rare.ui.event.KeyEvent;
import com.appnativa.rare.ui.event.MouseEvent;
import com.appnativa.rare.ui.painter.iBackgroundPainter;
import com.appnativa.rare.ui.painter.iPlatformPainter;
import com.appnativa.rare.viewer.WindowViewer;
import com.appnativa.rare.viewer.iContainer;
import com.appnativa.rare.viewer.iFormViewer;
import com.appnativa.rare.viewer.iTabPaneViewer;
import com.appnativa.rare.viewer.iViewer;
import com.appnativa.spot.SPOTPrintableString;
import com.appnativa.util.iURLResolver;
import com.appnativa.util.json.JSONWriter;

/**
 * This interface defines the functionality necessary for all widgets.
 * A widget is a component that manages some pieces of data and or provides
 * some mechanism for user interaction.
 *
 * @author Don DeCoteau
 */
public interface iWidget extends iScriptingContextSupport {

  /**
   * Widget types
   */
  static enum WidgetType {

    /** Bean widget type */
    Bean,

    /** PushButton widget type */
    PushButton,

    /**
     * Chart viewer type
     */
    Chart,

    /** CheckBox widget type */
    CheckBox,

    /** CheckBox Tree widget type */
    CheckBoxTree,

    /** Checkbox List widget type */
    CheckBoxList,

    /**
     * Collapsible Pane viewer type
     */
    CollapsiblePane,

    /** UIColor Chooser widget type */
    ColorChooser,

    /** ComboBox widget type */
    ComboBox,

    /** Custom widget type */
    Custom,

    /** Date Chooser widget type */
    DateChooser,

    /**
     * Form Viewer type
     */
    Form,

    /**
     * GridPane Viewer type
     */
    GridPane,

    /**
     * Group Box widget type
     */
    GroupBox,

    /**
     * Web Browser viewer type
     */
    WebBrowser,    /* * */

    /**
     * UIImage Pane viewer type
     */
    ImagePane,

    /**
     * Label widget type
     */
    Label,

    /**
     * Line widget type
     */
    Line,

    /**
     * ListBox widget type
     */
    ListBox,

    /**
     * Menu viewer type
     */
    MenuBar,

    /**
     * Navigator widget type
     */
    Navigator,

    /**
     * Password Field widget type
     */
    PasswordField,

    /**
     * Progress Bar widget type
     */
    ProgressBar,

    /**
     * Radio Button widget type
     */
    RadioButton,

    /**
     * Scroll Pane viewer type
     */
    ScrollPane,

    /**
     * Slider widget type
     */
    Slider,

    /**
     * Spinner widget type
     */
    Spinner,

    /**
     * Split Pane viewer type
     */
    SplitPane,

    /**
     * Stack Pane viewer type
     */
    StackPane,

    /**
     * Status Bar viewer type
     */
    StatusBar,

    /**
     * Table widget type
     */
    Table,

    /**
     * Tab Pane viewer type
     */
    TabPane,

    /**
     * Text Field widget type
     */
    TextField,

    /**
     * Text Field widget type
     */
    TextArea,

    /** Document Pane viewer widget type */
    DocumentPane,

    /**
     * Toolbar viewer type
     */
    ToolBar,

    /** Tree widget type */
    Tree,

    /** Widget Pane viewer type */
    WidgetPane,

    /** Window viewer type */
    Window,

    /** Media Player viewer type */
    MediaPlayer
  }

  /**
   * Adds the data from the specified map to the widget's context data map
   * This is intended for developers to context specific data.
   *
   * @param map the map whose data should be added
   */
  public void addData(Map map);

  /**
   * Dispatches a native key event to this widget's data component
   * Note: This is like pressing a key and only works when the widget
   * has focus.
   *
   * @param e the key event to dispatch
   */
  public void dispatchKeyEvent(KeyEvent e);

  /**
   * Dispatches a native mouse event to this widget's data component
   *
   * @param e the mouse event to dispatch
   */
  public void dispatchMouseEvent(MouseEvent e);

  /**
   * Invokes and keyboard actions that the widget's
   * data component has defined.
   *
   * Note: This is not the same as pressing the key, however
   * the keyboard actions are invoked regardless of whether
   * the widget has focus
   *
   * @param e the key event to dispatch
   */
  public void handleKeyboardActions(KeyEvent e);

  /**
   * Set additional data to be linked with this item
   *
   * @param data the data to link with this item
   */
  public void setLinkedData(Object data);

  /**
   *  Gets the HTTP form name for the widget
   *
   *  @return the HTTP form name for the widget
   */
  public String getHTTPFormName();

  /**
   * Gets the HTTP form value for the widget.
   *
   * @return the HTTP form value for the widget
   */
  public Object getHTTPFormValue();

  /**
   * Gets the data that is associated/linked with item
   *
   * @return the data that is associated/linked with item
   */
  public Object getLinkedData();

  /**
   * Test whether the widget has been disposed.
   *
   * @return true if the widget has been disposed; false otherwise
   */
  public boolean isDisposed();

  /**
   * Returns whether a Copy action can be performed on this widget.
   *
   * @return true if the action can be performed; false otherwise
   */
  boolean canCopy();

  /**
   * Returns whether a Cut action can be performed on this widget.
   *
   * @return true if the action can be performed; false otherwise
   */
  boolean canCut();

  /**
   * Returns whether a Delete action can be performed on this widget.
   *
   * @return true if the action can be performed; false otherwise
   */
  boolean canDelete();

  /**
   * Returns whether a Drag action can be performed on this widget.
   *
   * @param ge the drag gesture event
   * @return true if the action can be performed; false otherwise
   */
  boolean canDrag(DragEvent ge);

  /**
   * Returns whether a Drop action can be performed on this widget.
   *
   * @return true if the action can be performed; false otherwise
   */
  boolean canDrop();

  /**
   * Returns whether the widget can export the specified data flavor.
   *
   * @param flavor the data flavor to check
   *
   * @return true if it can export the data flavor; false otherwise
   */
  boolean canExport(TransferFlavor flavor);

  /**
   * Returns whether the widget can import any of the specified data flavors
   * based on the drop location specified by the drop information.
   *
   * @param flavors the set of data flavors to test
   * @param drop information about where the data will be dropped
   *
   * @return true if one of the data flavors can be imported; false otherwise
   */
  boolean canImport(TransferFlavor[] flavors, DropInformation drop);

  /**
   * Returns whether a Move action can be performed on this widget.
   *
   * @return true if the action can be performed; false otherwise
   */
  boolean canMove();

  /**
   * Returns whether a Paste action can be performed on this widget.
   *
   * @return true if the action can be performed; false otherwise
   */
  boolean canPaste();

  /**
   * Returns true if the widgets has edits that may be redone.
   *
   * @return true if there are edits to be redone
   */
  boolean canRedo();

  /**
   * Returns whether a Select All action can be performed on this widget.
   *
   * @return true if the action can be performed; false otherwise
   */
  boolean canSelectAll();

  /**
   * Returns true if the widgets has edits that may be undone.
   *
   * @return true if there are edits to be undone
   */
  boolean canUndo();

  /**
   * Returns true if the widgets has edits that may be undone or redone .
   *
   * @return true if there are edits to be undone or redone .
   */
  boolean canUndoOrRedo();

  /**
   * Returns an image of the widget
   * @return an image of the widget or null if the platform does not support widget images
   */
  UIImage capture();

  /**
   * Clears the contents of the widget
   */
  void clearContents();

  /**
   * Clears data stores in the widget's context map
   */
  void clearContextData();

  /**
   * Configures the widget
   * @param cfg the widget's configuration
   */
  void configure(Widget cfg);

  /**
   * Notifies the widget that the data associated with
   * the specified transferable has been exported
   *
   * @param transferable the transferable
   * @param drop the drop information
   */
  void dataExported(iTransferable transferable, DropInformation drop);

  /**
   * Disposes of the widget
   */
  void dispose();

  /**
   * Executes the event such that when this function returns
   * any code associated with the event will have been executed
   *
   * @param eventName the name of the event
   * @param event the associated event object
   */
  void executeEvent(String eventName, EventObject event);

  /**
   * Expands the specified string resolving any embedded variables or functions.
   *
   * @param value the value to expand
   * @param encode whether the string should be encoded as per the URL encoding specification
   * @return the expanded string
   */
  String expandString(String value, boolean encode);

  /**
   * Expands the specified string resolving any embedded variables or functions.
   *
   * @param value the value to expand
   * @param encode whether the string should be encoded as per the URL encoding specification
   *
   * @param writer the writer to write the value to
   *
   */
  void expandString(String value, boolean encode, Writer writer);

  /**
   * Indicates that the data loading task has completed.
   */
  void finishedLoading();

  /**
   * Fires the specified event
   *
   * @param eventName the name of the event
   * @param event the associated event object
   */
  void fireEvent(String eventName, EventObject event);

  /**
   * Load the data referenced by the specified URL.
   * For list type widgets (lists, trees, tables, etc),
   * the data will be appended to any existing data
   *
   * @param mimeType the MIME type of the data referenced by the URL (leave null to use the MUME type retrieved from the server)
   * @param url the URL referencing the items to be processed
   */
  void handleDataURL(String mimeType, URL url);

  /**
   * Call the widget's error handler or environment's default exception handler to handle the exception
   * if the widget does no have and error handler
   *
   * @param e the exception
   */
  void handleException(Throwable e);

  /**
   * Hides the popup container that contains this widget if the widget is in a popup container
   */
  void hidePopupContainer();

  /**
   * Imports the data associated with the specified transferable
   * into the location specified bur the drop information
   *
   * @param transferable the transferable
   * @param drop the drop information
   *
   * @return true if the data was imported; false otherwise
   */
  boolean importData(iTransferable transferable, DropInformation drop);

  /**
   * Reloads the widget
   *
   * @param context whether the reload is context sensitive or part or a larger load operation
   */
  void reload(boolean context);

  /**
   * Removes the attribute with the specified name
   *
   * @param name the name of the attribute
   * @return the attribute's current value
   */
  Object removeAttribute(String name);

  /**
   * Removes the existing background image from a widget
   */
  void removeBackgroundImage();

  /**
   *  Removes the event handler for the specified event. Once removed,
   *  the handler will no longer be invoked for events of the specified type
   *
   *  @param event the name event to remove the handler for
   *
   *  @param code the specific handler code tor remove or null to remove all handlers
   *  @return the existing handler object or null if there was not one
   */
  Object removeEventHandler(String event, Object code);

  /**
   * Causes the widget to repaint
   */
  void repaint();

  /**
   * Request that the widget obtains focus
   *
   * @return true if the widget obtained the focus; false otherwise
   */
  boolean requestFocus();

  /**
   * Resets the widget returning it to its initial state.
   * What this actually does is widget dependent as well as configuration options
   * that may have been specified when the widget was loaded.
   * The general behavior is that the widget is restored to its initial load state.
   */
  void reset();

  /**
   * Animates the widget using the specified animator
   * @param animator the animator
   * @param cb an optional callback to be called when the animation is complete
   */
  void animate(iPlatformAnimator animator, iFunctionCallback cb);

  /**
   * Animates the widget using the specified named animation
   * @param animation the animation
   * @param cb an optional callback to be called when the animation is complete
   */
  void animate(String animation, iFunctionCallback cb);

  /**
   * Performs a select all on the widgets contents
   */
  void selectAll();

  /**
   * Triggers the default action and invokes any associated event handlers.
   */
  void triggerActionEvent();

  /**
   * Triggers the default action and invokes any associated event handlers.
   *
   * @param cmd the action command string for the action event
   */
  void triggerActionEvent(String cmd);

  /**
   * Causes the widget to revalidate and repaint
   */
  void update();

  /**
   *  Writes out the value of the widget in a form compatible with the HTTP Form
   *  <b>multipart/form-data</b> encoding type.
   *
   * @param first whether this is the first widget to write its value
   * @param writer the writer
   * @param boundary the multipart content boundary
   *
   */
  void writeHTTPContent(boolean first, Writer writer, String boundary);

  /**
   * Writes out the value of the widget in a form compatible with the HTTP Forms
   * <b>application/x-www-form-urlencoded</b> encoding type.
   *
   * @param first whether this is the first widget to write its value
   * @param writer the writer to use
   *
   * @return true if data was written; false otherwise
   */
  boolean writeHTTPValue(boolean first, Writer writer);

  /**
   * Writes out the value of the widget in a form compatible with the JSON
   *
   * @param writer the writer to use
   *
   */
  void writeJSONValue(JSONWriter writer);

  /**
   * Tests whether the container can automatically disable the widget
   * when the container is disabled
   *
   * @param allow true if the container can automatically disable the widget; false otherwise
   */
  void setAllowContainerToDisable(boolean allow);

  /**
   * Sets a widget specific attribute.
   *
   * @param name the name of the attribute
   * @param value the value of the attribute
   */
  void setAttribute(String name, Object value);

  /**
   * Sets the widget's background color
   *
   * @param bg the background color
   */
  void setBackground(UIColor bg);

  /**
   * Sets the background image for the widget (if the widget supports background images).
   *
   * @param image the image to use as the image (use null to remove the existing image)
   * @param opacity the opacity for the image. Value should be between 0 (invisible) and 99 (obscures everything)
   * @param type the render type for the image
   */
  void setBackgroundImage(UIImage image, int opacity, RenderType type);

  /**
   * Sets the background image painter for the widget
   *
   * @param painter the painter (can be null)
   */
  void setBackgroundOverlayPainter(iPlatformPainter painter);

  /**
   * Sets the background painter for the widget
   *
   * @param painter the painter (can be null)
   */
  void setBackgroundPainter(iBackgroundPainter painter);

  /**
   * Sets the border for a widget
   *
   * @param border the widget's border
   */
  void setBorder(iPlatformBorder border);

  /**
   * Sets whether the widget can support the copy action
   * The widget must be initially configured to support copying in order to use
   * this method the toggle the copying allowed state
   *
   * @param allowed true if allowed; false otherwise
   */
  void setCopyingAllowed(boolean allowed);

  /**
   * Load the data referenced by the specified items configuration object.
   * For list type widgets (lists, trees, tables, etc),
   * The existing data will be replaced
   *
   * @param items the items to load
   */
  void setDataItems(DataItem items);

  /**
   * Load the data referenced by the specified link.
   *    * For list type widgets (lists, trees, tables, etc),
   * The existing data will be replaced
   *
   * @param link the link reference
   * @see iDataItemParserCallback
   */
  void setDataLink(ActionLink link);

  /**
   * Load the data referenced by the specified link.
   *    * For list type widgets (lists, trees, tables, etc),
   * The existing data will be replaced
   *
   * @param link the link reference
   * @param defer  true to defer loading of the link; false otherwise
   * @return the Future for the deferred task or null
   * @see iDataItemParserCallback
   */
  iCancelableFuture setDataLink(ActionLink link, boolean defer);

  /**
   * Load the data referenced by the specified URL.
   * For list type widgets (lists, trees, tables, etc),
   * The existing data will be replaced
   *
   * @param url the URL referencing the items to be processed
   */
  void setDataURL(String url);

  /**
   * Load the data referenced by the specified URL.
   * For list type widgets (lists, trees, tables, etc),
   * The existing data will be replaced
   *
   * @param url the URL referencing the items to be processed
   */
  void setDataURL(URL url);

  /**
   * Load the data referenced by the specified URL.
   * For list type widgets (lists, trees, tables, etc),
   * The existing data will be replaced
   *
   * @param url the URL referencing the items to be processed
   * @param defer  true to defer loading of the url; false otherwise
   * @return the Future for the deferred task or null
   */
  iCancelableFuture setDataURL(String url, boolean defer);

  /**
   * Sets whether the widget can support the delete action
   *
   * @param allowed true if allowed; false otherwise
   */
  void setDeletingAllowed(boolean allowed);

  /**
   * Sets whether the widget can support the drag action
   * The widget must be initially configured to support dragging in order to use
   * this method the toggle the copying allowed state
   *
   * @param allowed true if allowed; false otherwise
   */
  void setDraggingAllowed(boolean allowed);

  /**
   * Sets the widget's enabled status.
   *
   * @param enabled <code>true</code> for enabled; <code>false</code> otherwise
   */
  void setEnabled(boolean enabled);

  /**
   * Sets the code to handle an event. The caller is responsible
   * for registering the widget listener with the appropriate component.
   *
   * @param event the name of event
   * @param code the code to handle the event
   * @param append true to append the handler; false to replace all existing handlers
   */
  void setEventHandler(String event, Object code, boolean append);

  /**
   * Sets whether of the widget's focus state is painted
   *
   * @param focusPainted true to paint; false otherwise
   */
  void setFocusPainted(boolean focusPainted);

  /**
   * Sets whether the widget is focusable.
   *
   * @param focusable true if it is focusable; false otherwise
   */
  void setFocusable(boolean focusable);

  /**
   * Sets the widget's font.
   *
   * @param font the font for the item
   */
  void setFont(UIFont font);

  /**
   * Sets the widget's foreground color
   *
   * @param fg the foreground color
   */
  void setForeground(UIColor fg);

  /**
   * Sets the widgets value based on a value that would be returned from
   * {@link #getHTTPFormValue()}
   *
   * @param value the value to set
   */
  void setFromHTTPFormValue(Object value);

  /**
   * Sets the icon for the widget.
   *
   * @param icon the icon
   */
  void setIcon(iPlatformIcon icon);

  /**
   * Set whether the widget is opaque. If it is opaque it is responsible for painting its background.
   * If it is not opaque the widget's parent is responsible for painting the background.
   *
   * @param opaque true (the default) if the widget is opaque; false otherwise
   */
  void setOpaque(boolean opaque);

  /**
   *  Sets the overlay image painter for the widget
   *
   *  @param painter the painter (can be null)
   */
  void setOverlayPainter(iPlatformPainter painter);

  /**
   * SGet the parent container for the widget
   *
   * @param parent the parent container for the widget
   */
  void setParent(iContainer parent);

  /**
   * Set the popup container that contains this widget.
   * The popup container is responsible for calling this method to let the widget know
   * that it is contained within a popup
   *
   * @param popup the popup
   */
  void setPopupContainer(iPopup popup);

  /**
   * Sets the handle for executing scripts within this widget's context
   *
   * @param context the handle for executing scripts within this widget's context
   */
  void setScriptingContext(WidgetContext context);

  /**
   * Sets the widget's selected status. Only valid for widgets whose selection state can change.
   *
   * @param selected <code>true</code> for selected; <code>false</code> otherwise
   */
  void setSelected(boolean selected);

  /**
   * Locks the widget's size such that it will maintain it's
   * current size during layout if at all possible
   *
   * @param sizeLocked true to lock the widget's size; false to unlock
   */
  void setSizeLocked(boolean sizeLocked);

  /**
   * Sets the direction of the widget's text
   * @param direction the direction ('ltr' or 'rtl')
   */
  void setTextDirection(String direction);

  /**
   * Sets the widget's title.
   *
   * @param title the title
   */
  void setTitle(String title);

  /**
   * Sets the widget's value
   *
   * @param value the new value for the widget.
   */
  void setValue(Object value);

  /**
   * Sets the widget's visible status.
   *
   * @param visible <code>true</code> for visible; <code>false</code> otherwise
   */
  void setVisible(boolean visible);

  /**
   * Gets the application context for the widget
   *
   * @return the application context for the widget
   */
  iPlatformAppContext getAppContext();

  /**
   * Gets a widget specific attribute.
   *
   * @param name the name of the attribute
   * @return the attribute's value
   */
  Object getAttribute(String name);

  /**
   * Gets the background color for the item.
   *
   * @return the background color for the item
   */
  UIColor getBackground();

  /**
   * Gets the background overlay painter for the widget
   *
   * @return the painter or null if one does not exist
   */
  iPlatformPainter getBackgroundOverlayPainter();

  /**
   * Gets the background painter for the widget
   *
   * @return the painter or null if one does not exist
   */
  iBackgroundPainter getBackgroundPainter();

  /**
   * Gets the border for a widget
   *
   * @return the widget's border
   */
  iPlatformBorder getBorder();

  /**
   * Gets the component that contain all the visual
   * elements necessary the display and manage the widget
   *
   * @return the widget's container
   */
  iPlatformComponent getContainerComponent();

  /**
   * Gets container associated with the widget
   * If the widget is itself a container the a handle to "this"
   * is returned other a handle to the widget's parent is returned
   *
   * @return the viewer that the widget is part of
   */
  iContainer getContainerViewer();

  /**
   * Gets the component that actually manages the widgets data
   *
   * @return the component that actually manages the widgets data
   */
  iPlatformComponent getDataComponent();

  /**
   * Gets the URL used to populate the widget
   * The string may contain embedded variables or functions
   *
   * @return the URL used to populate the widget or null
   */
  URL getDataURL();

  /**
   * Gets the icon for the widget when it is the source of a drag operation
   *
   * @return the icon
   */
  iPlatformIcon getDragIcon();

  /**
   * Gets the code to handle an event.
   *
   * @param event the name of event
   * @return the code to handle the event
   */
  Object getEventHandler(String event);

  /**
   * Get the data flavors that this widgets exports
   *
   * @return the data flavors that this widgets exports
   */
  String[] getExportableDataFlavors();

  /**
   * Gets the font for the widget
   *
   * @return the font for the widget
   */
  UIFont getFont();

  /**
   * Gets the foreground color for the widget
   *
   * @return the foreground color for the widget
   */
  UIColor getForeground();

  /**
   * Get the parent that this widget belongs
   *
   * @return the parent that this widget belongs
   */
  iFormViewer getFormViewer();

  /**
   * Returns whether the widget has painter support
   *
   * @return whether the widget has painter support
   */
  boolean getHasPainterSupport();

  /**
   * Gets the height of the widget
   * @return the height of the widget
   */
  int getHeight();

  /**
   * Gets the icon for the widget
   *
   * @return the icon for the widget
   */
  iPlatformIcon getIcon();

  /**
   * Resolves the specified string icon configuration to an icon instance
   * The string may contain embedded variables or functions
   *
   * @param icon the string icon configuration
   * @return the icon instance or null if the icon does not exist or cannot be loaded
   */
  iPlatformIcon getIcon(SPOTPrintableString icon);

  /**
   * Resolves the specified string icon reference to an icon instance
   * The string may contain embedded variables or functions
   *
   * @param icon the string icon reference
   * @param description the description for the icon
   *
   * @return the icon instance or null if the icon does not exist or cannot be loaded
   */
  iPlatformIcon getIcon(String icon, String description);

  /**
   *       Get the icon referenced by the specified URL
   *
   *       @param url the URL
   * @param description the description for the icon
   *
   * @return the icon instance or null if the icon does not exist or cannot be loaded
   */
  iPlatformIcon getIcon(URL url, String description);

  /**
   * Resolves the specified string image configuration to an image instance
   * The string may contain embedded variables or functions
   *
   * @param image the string icon configuration
   *
   * @return the image instance or null if the icon does not exist or cannot be loaded
   */
  UIImage getImage(SPOTPrintableString image);

  /**
   * Resolves the specified string image to an image instance
   * The string may contain embedded variables or functions
   *
   * @param image the string image for the image
   * @return the image instance or null if the image does not exist or cannot be loaded
   */
  UIImage getImage(String image);

  /**
   * Get the image referenced by the specified URL
   *
   * @param url the URL
   *
   * @return the image instance or null if the image does not exist or cannot be
   *         loaded
   */
  UIImage getImage(URL url);

  /**
   * Get the data flavors that this widget can import
   *
   * @return the data flavors that this widgets can import
   */
  String[] getImportableDataFlavors();

  /**
   * Gets the location of the widget within it's parent container
   * @return the location of the widget within it's parent container
   */
  UIPoint getLocationInParent();

  /**
   * Gets the location of the widget on the screen
   * @return the location of the widget on the screen
   */
  UIPoint getLocationOnScreen();

  /**
   * Gets the widgets name
   *
   * @return  the widgets name
   */
  String getName();

  /**
   *   Gets the overlay painter for the widget
   *
   *   @return the painter or null if one does not exist
   */
  iPlatformPainter getOverlayPainter();

  /**
   * Get the parent container for the widget
   *
   * @return the parent container for the widget
   */
  iContainer getParent();

  /**
   * Gets the path name that can be used to reference the widget
   *
   * @return  the path name for  widget
   */
  String getPathName();

  /**
   * Gets the preferred size of the widget
   *
   * @return the preferred size of the widget
   */
  UIDimension getPreferredSize();

  /**
   * Gets the preferred size of the widget
   * given the maximum width
   *
   * @param maxWidth the maximum allowed width
   *
   * @return the preferred size of the widget
   */
  UIDimension getPreferredSize(int maxWidth);

  /**
   * Get the number of pixels that the upper left corner of the widget is offset
   * from the left edge of the screen
   *
   * @return The number of pixels that the upper left corner of the widget is
   *         offset the left edge of the screen
   */
  int getScreenLeft();

  /**
   * Get the number of pixels that the upper left corner of the widget is offset
   * from the top edge of the screen
   *
   * @return The number of pixels that the upper left corner of the widget is
   *         offset the top edge of the screen
   */
  int getScreenTop();

  /**
   * Gets the handler for executing scripts within this widget's context
   *
   * @return the handler for executing scripts within this widget's context
   */
  iScriptHandler getScriptHandler();

  /**
   * Gets the widget's current selection. This may or may not be the same as the widget's value.
   * <p>
   * If the widget allows multiple selections, then this method will return
   * an array containing all the selected items.
   * </p>
   * NOTE: For a text component this is the selected text where as its value is its entire contents.
   *
   * @return the widget's selection
   * @see #getValue
   */
  Object getSelection();

  /**
   * Gets the widget's current selection
   *
   * @return a string representation of the widget's selection
   * @see #getValue
   */
  String getSelectionAsString();

  /**
   * Gets the linked data associated with the widget's current selection
   *
   * @return the selection's linked data
   */
  Object getSelectionData();

  /**
   * Gets the linked data associated with the widget's current selection
   *
   * @return a string representation of the selection's linked data
   */
  String getSelectionDataAsString();

  /**
   * Gets the widget's enclosing tab document.
   * If the widget or any of its parent viewer are contained within a tab then
   * this method will return the enclosing tab document.
   *
   * @return the tab document enclosing the widget or null if the widget is not contained within a tab
   */
  iTabDocument getTabDocument();

  /**
   * Gets the widget's enclosing tab document index.
   * If the widget or any of its parent viewer are contained within a tab then
   * this method will return the enclosing tab document's index.
   *
   * @return the index of tab document enclosing the widget or -1 if the widget is not contained within a tab
   */
  int getTabIndex();

  /**
   * Gets the widget's enclosing tab pane viewer.
   * If the widget or any of its parent viewer are contained within a tab then
   * this method will return the enclosing tab pane viewer for that tab
   *
   * @return the tab pane viewer containing the widget's tab or null if the widget is not contained within a tab
   */
  iTabPaneViewer getTabPaneViewer();

  /**
   * Gets the direction of the widget's text
   *
   * @return the direction of the widget's text
   */
  String getTextDirection();

  /**
   *   Gets the widget's title
   *
   *   @return the widget's title
   */
  String getTitle();

  /**
   * Get the title label component for a widget if one was created
   *
   * @return the title label component for a widget if one was created
   */
  iPlatformComponent getTitleLabel();

  /**
   * Gets the data that is to be transferred given the specified data flavor and drop information.
   * Typically this will be the widgets current selection. If a flavor function was associated
   * with the specified data flavor then the transfer data will be the return value of the function.
   *
   * @param flavor the data flavor being requested
   * @param transferable the transferable requesting the data (can be null)
   * @return the data to be transferred
   *
   */
  Object getTransferData(TransferFlavor flavor, iTransferable transferable);

  /**
   * Get the UI window that is the container for this viewer
   *
   * @return the UI window that is the container for this viewer
   */
  Object getUIWindow();

  /**
   * Resolves the specified string url to valid URL instance.
   * The string may contain embedded variables or functions
   *
   * @return the URL instance
   * @param url the string url for the icon
   *
   * @throws MalformedURLException if the string is not a valid URL
   */
  URL getURL(String url) throws MalformedURLException;

  /**
   * Gets the URL resolver for the widget
   *
   * @return the URL resolver for the widget
   */
  iURLResolver getURLResolver();

  /**
   * Gets the widget's value.
   * This may or may not be the same as the widget's selection.
   * For a text component this is its entire contents where as its selection is the selected text.
   *
   * @return the widget's value
   * @see #getSelection
   */
  Object getValue();

  /**
   * Gets the widget's value
   *
   * @return the string representation of the widget's value
   */
  String getValueAsString();

  /**
   * Gets the viewer associated with the widget
   * If the widget is itself a container the a handle to "this"
   * is returned otherwise a handle to the widget's parent is returned
   *
   * @return the viewer that the widget is part of
   */
  iViewer getViewer();

  /**
   * Gets the number of widgets that this widget contains
   *
   * @return the number of widgets that this widget contains
   */
  int getWidgetCount();

  /**
   * Gets the widget type
   *
   * @return the widget type
   */
  WidgetType getWidgetType();

  /**
   * Gets the width of the widget
   * @return the width of the widget
   */
  int getWidth();

  /**
   * Gets whether the widget is currently attached to a window
   * @return true if it is; false otherwise
   */
  boolean isAttached();
  
  /**
   * Gets a handle to the <CODE>WindowViewer</CODE> instance that the viewer
   * belongs to. This is the object that is accessible as the <B>window</B>
   * object from scripting languages
   *
   * @return an <CODE>iWindow</CODE> instance
   */
  WindowViewer getWindow();

  /**
   * Returns whether the widget has a custom property with the specified name
   *
   * @param name the name of the property
   *
   * @return true if it does; false otherwise
   */
  boolean hasCustomProperty(String name);

  /**
   * Test whether the widget has a popup MenuBar
   *
   * @return true if it has a popup MenuBar; false otherwise
   */
  boolean hasPopupMenu();

  /**
   * Returns whether if the widget has a current selection
   *
   * @return true if it has a selection; false otherwise
   */
  boolean hasSelection();

  /**
   * Returns whether if the widget has a value
   *
   * @return true if it has a value; false otherwise
   */
  boolean hasValue();

  /**
   * Tests whether the container can automatically disable the widget
   * when the container is disabled
   *
   * @return true if the container can automatically disable the widget; false otherwise
   */
  boolean isAllowContainerToDisable();

  /**
   * Returns whether the widget is in design mode
   *
   * @return true if the widget or its parent is in design mode; false otherwise
   */
  boolean isDesignMode();

  /**
   * Returns whether the widget is enabled
   *
   * @return whether the widget is enabled
   */
  boolean isEnabled();

  /**
   * Returns whether the specified event has an associated script attached
   *
   * @param event the event
   * @return true if it does; false otherwise
   */
  boolean isEventEnabled(String event);

  /**
   * Tests if the widget is the current focus owner not withstanding temporary focus
   * gained by transient widgets such as popup menus.
   *
   * @return true if the widget is the current focus owner; false otherwise
   */
  boolean isFocusOwner();

  /**
   * Gets whether of the widget's focus state is painted
   *
   * @return true if focus state if painted; false otherwise
   */
  boolean isFocusPainted();

  /**
   * Returns whether the widget is contained within a popup (transient Window)
   *
   * @return true if it is in a popup; false otherwise
   */
  boolean isInPopup();

  /**
   * Returns whether the widget content can only be submitted using MIME multipart encoding.
   *
   * @return true if the widget content can only be submitted using MIME multipart encoding; false otherwise
   */
  boolean isMultipartContent();

  /**
   * Returns whether the widget is required for form submission
   *
   * @return whether the widget is required for form submission
   */
  boolean isRequired();

  /**
   * Returns whether the widget is selectable
   *
   * @return whether the widget is enabled
   */
  boolean isSelectable();

  /**
   * Returns whether if the widget itself is selected.
   * This is only valid for widgets that have a changeable selection state (like buttons).
   *
   * @return true if it is selected; false otherwise
   */
  boolean isSelected();

  /**
   * Returns whether this widget is showing on screen. This means
   * that the widget must be visible, and it must be in a container
   * that is visible and showing.
   * @return true if the component is showing; false otherwise
   */
  boolean isShowing();

  /**
   * Gets whether the the widget's size is locked.
   * If locked the widget will maintain it's
   * current size during layout if at all possible
   *
   * @return true if locked; false if unlocked
   */
  boolean isSizeLocked();

  /**
   * Returns whether the widget is submit-able. That is, it can be submitted
   * when an enclosing form is submitted. During form submission, a widget is first
   * checked to see if it is submit-able and then if it is valid for submission prior to having it valued written out.
   *
   * @return true if the widget is submit-able; false otherwise
   * @see #isValidForSubmission
   */
  boolean isSubmittable();

  /**
   * Gets if the text direction has been explicitly set
   * @return true if the text direction has been explicitly set; false otherwise
   */
  boolean isTextDirectionSet();

  /**
   * Returns whether the widget if of the type specified by the given string
   *
   * @param type the type to test
   * @return true if the widget is of the specified type; false otherwise
   */
  boolean isType(String type);

  /**
   * Returns whether the widget is valid for submission.
   * If the contents of the widget are not valid, the <B>showerror</B> parameter specified whether
   * or not the widget should display an error message notifying the user that its contents
   * are not valid for submission.
   *
   * @param showerror whether an error should be displayed if the widget is not valid for submission
   * @return true if the widget is valid for submission; false otherwise
   * @see #isSubmittable
   */
  boolean isValidForSubmission(boolean showerror);

  /**
   * Returns whether this widget is a viewer
   *
   * @return true if the widget is a viewer; false otherwise
   */
  boolean isViewer();

  /**
   * Returns whether the widget is visible
   *
   * @return whether the widget is enabled
   */
  boolean isVisible();
}
