/*
 * @(#)CompositeCombo.java
 * 
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.studio;

/**
 * Copyright (c) 2000, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *      Marty Jones <martybjones@gmail.com> - initial API and implementation
 *  Enrico Schnepel <enrico.schnepel@randomice.net> - clear selectedImage bug 297209
 *  Enrico Schnepel <enrico.schnepel@randomice.net> - disable selectedImage bug 297327
 *  Wolfgang Schramm <wschramm@ch.ibm.com> - added vertical alignment of text for selected table item.
 *  Enrico Schnepel <enrico.schnepel@randomice.net> - help event listener bug 326285
 */
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.accessibility.ACC;
import org.eclipse.swt.accessibility.AccessibleAdapter;
import org.eclipse.swt.accessibility.AccessibleControlAdapter;
import org.eclipse.swt.accessibility.AccessibleControlEvent;
import org.eclipse.swt.accessibility.AccessibleEvent;
import org.eclipse.swt.accessibility.AccessibleTextAdapter;
import org.eclipse.swt.accessibility.AccessibleTextEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.TypedListener;
import org.eclipse.swt.widgets.Widget;

/**
 * The CompositeCombo class represents a selectable user interface object that
 * combines a label, textfield, and an arbitraty composite
 * <p>
 * Special Note: Although this class is a subclass of <code>Composite</code>, it
 * does not make sense to add children to it, or set a layout on it.
 * </p>
 * <dl>
 * <dt><b>Styles:</b>
 * <dd>BORDER, READ_ONLY, FLAT</dd>
 * <dt><b>Events:</b>
 * <dd>DefaultSelection, Modify, Selection, Verify</dd>
 * </dl>
 *
 */
public class CompositeCombo extends Composite {
  private Button      arrow;
  private Color       background;
  private iComboPanel comboPanel;
  private Listener    focusFilter;
  private Font        font;
  private Color       foreground;
  private boolean     hasFocus;
  private Listener    listener;
  private Shell       popup;
  private Label       selectedImage;
  private Composite   table;
  private Text        text;

  /**
   * Constructs a new instance of this class given its parent and a style value
   * describing its behavior and appearance.
   * <p>
   * The style value is either one of the style constants defined in class
   * <code>SWT</code> which is applicable to instances of this class, or must be
   * built by <em>bitwise OR</em>'ing together (that is, using the
   * <code>int</code> "|" operator) two or more of those <code>SWT</code> style
   * constants. The class description lists the style constants that are
   * applicable to the class. Style bits are also inherited from superclasses.
   * </p>
   *
   * @param parent
   *          a widget which will be the parent of the new instance (cannot be
   *          null)
   * @param style
   *          the style of widget to construct
   *
   * @exception IllegalArgumentException
   *              <ul>
   *              <li>ERROR_NULL_ARGUMENT - if the parent is null</li>
   *              </ul>
   * @exception SWTException
   *              <ul>
   *              <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
   *              thread that created the parent</li>
   *              </ul>
   *
   * @see SWT#BORDER
   * @see SWT#READ_ONLY
   * @see SWT#FLAT
   * @see Widget#getStyle()
   */
  public CompositeCombo(Composite parent, int style, iComboPanel panel) {
    super(parent, style = checkStyle(style));
    comboPanel = panel;

    // set the label style
    int textStyle = SWT.SINGLE;

    if ((style & SWT.READ_ONLY) != 0) {
      textStyle |= SWT.READ_ONLY;
    }

    if ((style & SWT.FLAT) != 0) {
      textStyle |= SWT.FLAT;
    }

    // set control background to white
    setBackground(Display.getDefault().getSystemColor(SWT.COLOR_WHITE));
    // create label to hold image if necessary.
    selectedImage = new Label(this, SWT.NONE);
    selectedImage.setAlignment(SWT.RIGHT);
    selectedImage.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_WHITE));
    getLayout();
    // create the control to hold the display text of what the user selected.
    text = new Text(this, textStyle);
    text.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_WHITE));

    // set the arrow style.
    int arrowStyle = SWT.ARROW | SWT.DOWN;

    if ((style & SWT.FLAT) != 0) {
      arrowStyle |= SWT.FLAT;
    }

    // create the down arrow button
    arrow = new Button(this, arrowStyle);
    // now add a listener to listen to the events we are interested in.
    listener = new Listener() {
      public void handleEvent(Event event) {
        if (isDisposed()) {
          return;
        }

        // check for a popup event
        if (popup == event.widget) {
          popupEvent(event);

          return;
        }

        if (text == event.widget) {
          textEvent(event);

          return;
        }

        // check for a table event
        if (table == event.widget) {
          comboPanel.handleEvent(CompositeCombo.this, table, event);

          return;
        }

        // check for arrow event
        if (arrow == event.widget) {
          arrowEvent(event);

          return;
        }

        // check for this widget's event
        if (CompositeCombo.this == event.widget) {
          comboEvent(event);

          return;
        }

        // check for shell event
        if (getShell() == event.widget) {
          getDisplay().asyncExec(new Runnable() {
            public void run() {
              if (isDisposed()) {
                return;
              }

              handleFocus(SWT.FocusOut);
            }
          });
        }
      }
    };
    // create new focus listener
    focusFilter = new Listener() {
      public void handleEvent(Event event) {
        if (isDisposed()) {
          return;
        }

        Shell shell = ((Control) event.widget).getShell();

        if (shell == CompositeCombo.this.getShell()) {
          handleFocus(SWT.FocusOut);
        }
      }
    };

    // set the listeners for this control
    int[] comboEvents = { SWT.Dispose, SWT.FocusIn, SWT.Move, SWT.Resize };

    for (int i = 0; i < comboEvents.length; i++) {
      this.addListener(comboEvents[i], listener);
    }

    int[] textEvents = {
      SWT.DefaultSelection, SWT.KeyDown, SWT.KeyUp, SWT.MenuDetect, SWT.Modify, SWT.MouseDown, SWT.MouseUp,
      SWT.MouseDoubleClick, SWT.MouseWheel, SWT.Traverse, SWT.FocusIn, SWT.Verify
    };

    for (int i = 0; i < textEvents.length; i++) {
      text.addListener(textEvents[i], listener);
    }

    // set the listeners for the arrow image
    int[] arrowEvents = { SWT.Selection, SWT.FocusIn };

    for (int i = 0; i < arrowEvents.length; i++) {
      arrow.addListener(arrowEvents[i], listener);
    }

    // initialize the drop down
    createPopup(-1);
    initAccessible();
  }

  /**
   * Adds the listener to the collection of listeners who will be notified when
   * the receiver's text is modified, by sending it one of the messages defined
   * in the <code>ModifyListener</code> interface.
   *
   * @param listener
   *          the listener which should be notified
   *
   * @exception IllegalArgumentException
   *              <ul>
   *              <li>ERROR_NULL_ARGUMENT - if the listener is null</li>
   *              </ul>
   * @exception SWTException
   *              <ul>
   *              <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
   *              <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
   *              thread that created the receiver</li>
   *              </ul>
   *
   * @see ModifyListener
   * @see #removeModifyListener
   */
  public void addModifyListener(ModifyListener listener) {
    checkWidget();

    if (listener == null) {
      SWT.error(SWT.ERROR_NULL_ARGUMENT);
    }

    TypedListener typedListener = new TypedListener(listener);

    addListener(SWT.Modify, typedListener);
  }

  /**
   * Adds the listener to the collection of listeners who will be notified when
   * the user changes the receiver's selection, by sending it one of the
   * messages defined in the <code>SelectionListener</code> interface.
   * <p>
   * <code>widgetSelected</code> is called when the combo's list selection
   * changes. <code>widgetDefaultSelected</code> is typically called when ENTER
   * is pressed the combo's text area.
   * </p>
   *
   * @param listener
   *          the listener which should be notified when the user changes the
   *          receiver's selection
   *
   * @exception IllegalArgumentException
   *              <ul>
   *              <li>ERROR_NULL_ARGUMENT - if the listener is null</li>
   *              </ul>
   * @exception SWTException
   *              <ul>
   *              <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
   *              <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
   *              thread that created the receiver</li>
   *              </ul>
   *
   * @see SelectionListener
   * @see #removeSelectionListener
   * @see SelectionEvent
   */
  public void addSelectionListener(SelectionListener listener) {
    checkWidget();

    if (listener == null) {
      SWT.error(SWT.ERROR_NULL_ARGUMENT);
    }

    TypedListener typedListener = new TypedListener(listener);

    addListener(SWT.Selection, typedListener);
    addListener(SWT.DefaultSelection, typedListener);
  }

  /**
   * Adds the listener to the collection of listeners who will be notified when
   * the user presses keys in the text field. interface.
   *
   * @param listener
   *          the listener which should be notified when the user presses keys
   *          in the text control.
   *
   * @exception IllegalArgumentException
   *              <ul>
   *              <li>ERROR_NULL_ARGUMENT - if the listener is null</li>
   *              </ul>
   * @exception SWTException
   *              <ul>
   *              <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
   *              <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
   *              thread that created the receiver</li>
   *              </ul>
   */
  public void addTextControlKeyListener(KeyListener listener) {
    checkWidget();

    if (listener == null) {
      SWT.error(SWT.ERROR_NULL_ARGUMENT);
    }

    text.addKeyListener(listener);
  }

  /**
   * Adds the listener to the collection of listeners who will be notified when
   * the receiver's text is verified, by sending it one of the messages defined
   * in the <code>VerifyListener</code> interface.
   *
   * @param listener
   *          the listener which should be notified
   *
   * @exception IllegalArgumentException
   *              <ul>
   *              <li>ERROR_NULL_ARGUMENT - if the listener is null</li>
   *              </ul>
   * @exception SWTException
   *              <ul>
   *              <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
   *              <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
   *              thread that created the receiver</li>
   *              </ul>
   *
   * @see VerifyListener
   * @see #removeVerifyListener
   *
   * @since 3.3
   */
  public void addVerifyListener(VerifyListener listener) {
    checkWidget();

    if (listener == null) {
      SWT.error(SWT.ERROR_NULL_ARGUMENT);
    }

    TypedListener typedListener = new TypedListener(listener);

    addListener(SWT.Verify, typedListener);
  }

  /**
   * Sets the selection in the receiver's text field to an empty selection
   * starting just before the first character. If the text field is editable,
   * this has the effect of placing the i-beam at the start of the text.
   * <p>
   * Note: To clear the selected items in the receiver's list, use
   * <code>deselectAll()</code>.
   * </p>
   *
   * @exception SWTException
   *              <ul>
   *              <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
   *              <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
   *              thread that created the receiver</li>
   *              </ul>
   *
   * @see #deselectAll
   */
  public void clearSelection() {
    checkWidget();
    text.clearSelection();
    comboPanel.clearSelection(this, table);
  }

  /**
   * {@inheritDoc}
   */
  public Point computeSize(int wHint, int hHint, boolean changed) {
    checkWidget();

    int overallWidth  = 0;
    int overallHeight = 0;
    int borderWidth   = getBorderWidth();

    // use user defined values if they are specified.
    if ((wHint != SWT.DEFAULT) && (hHint != SWT.DEFAULT)) {
      overallWidth  = wHint;
      overallHeight = hHint;
    } else {
      Point textSize  = text.computeSize(SWT.DEFAULT, SWT.DEFAULT, changed);
      Point arrowSize = arrow.computeSize(SWT.DEFAULT, SWT.DEFAULT, changed);
      Point tableSize = comboPanel.getPreferredSize(this, table, SWT.DEFAULT, SWT.DEFAULT, changed);

      overallHeight = Math.max(textSize.y, arrowSize.y);
      overallHeight = Math.max(textSize.y, arrowSize.y);
      overallHeight = Math.max(tableSize.y, overallHeight);
      overallWidth  = Math.max(2 * borderWidth, tableSize.x);

      // use user specified if they were entered.
      if (wHint != SWT.DEFAULT) {
        overallWidth = wHint;
      }

      if (hHint != SWT.DEFAULT) {
        overallHeight = hHint;
      }
    }

    return new Point(overallWidth + 2 * borderWidth, overallHeight + 2 * borderWidth);
  }

  /**
   * Copies the selected text.
   * <p>
   * The current selection is copied to the clipboard.
   * </p>
   *
   * @exception SWTException
   *              <ul>
   *              <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
   *              <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
   *              thread that created the receiver</li>
   *              </ul>
   *
   * @since 3.3
   */
  public void copy() {
    checkWidget();
    text.copy();
  }

  /**
   * Cuts the selected text.
   * <p>
   * The current selection is first copied to the clipboard and then deleted
   * from the widget.
   * </p>
   *
   * @exception SWTException
   *              <ul>
   *              <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
   *              <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
   *              thread that created the receiver</li>
   *              </ul>
   *
   * @since 3.3
   */
  public void cut() {
    checkWidget();
    text.cut();
  }

  /**
   * Pastes text from clipboard.
   * <p>
   * The selected text is deleted from the widget and new text inserted from the
   * clipboard.
   * </p>
   *
   * @exception SWTException
   *              <ul>
   *              <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
   *              <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
   *              thread that created the receiver</li>
   *              </ul>
   *
   * @since 3.3
   */
  public void paste() {
    checkWidget();
    text.paste();
  }

  /**
   * {@inheritDoc}
   */
  public void redraw() {
    super.redraw();
    text.redraw();
    arrow.redraw();

    if (popup.isVisible()) {
      table.redraw();
    }
  }

  /**
   * {@inheritDoc}
   */
  public void redraw(int x, int y, int width, int height, boolean all) {
    super.redraw(x, y, width, height, true);
  }

  /**
   * Removes the listener from the collection of listeners who will be notified
   * when the receiver's text is modified.
   *
   * @param listener
   *          the listener which should no longer be notified
   *
   * @exception IllegalArgumentException
   *              <ul>
   *              <li>ERROR_NULL_ARGUMENT - if the listener is null</li>
   *              </ul>
   * @exception SWTException
   *              <ul>
   *              <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
   *              <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
   *              thread that created the receiver</li>
   *              </ul>
   *
   * @see ModifyListener
   * @see #addModifyListener
   */
  public void removeModifyListener(ModifyListener listener) {
    checkWidget();

    if (listener == null) {
      SWT.error(SWT.ERROR_NULL_ARGUMENT);
    }

    removeListener(SWT.Modify, listener);
  }

  /**
   * Removes the listener from the collection of listeners who will be notified
   * when the user changes the receiver's selection.
   *
   * @param listener
   *          the listener which should no longer be notified
   *
   * @exception IllegalArgumentException
   *              <ul>
   *              <li>ERROR_NULL_ARGUMENT - if the listener is null</li>
   *              </ul>
   * @exception SWTException
   *              <ul>
   *              <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
   *              <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
   *              thread that created the receiver</li>
   *              </ul>
   *
   * @see SelectionListener
   * @see #addSelectionListener
   */
  public void removeSelectionListener(SelectionListener listener) {
    checkWidget();

    if (listener == null) {
      SWT.error(SWT.ERROR_NULL_ARGUMENT);
    }

    removeListener(SWT.Selection, listener);
    removeListener(SWT.DefaultSelection, listener);
  }

  /**
   * Removes the listener from the collection of listeners who will be notified
   * when the user presses keys in the text control.
   *
   * @param listener
   *          the listener which should no longer be notified
   *
   * @exception IllegalArgumentException
   *              <ul>
   *              <li>ERROR_NULL_ARGUMENT - if the listener is null</li>
   *              </ul>
   * @exception SWTException
   *              <ul>
   *              <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
   *              <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
   *              thread that created the receiver</li>
   *              </ul>
   *
   */
  public void removeTextControlKeyListener(KeyListener listener) {
    checkWidget();

    if (listener == null) {
      SWT.error(SWT.ERROR_NULL_ARGUMENT);
    }

    text.removeKeyListener(listener);
  }

  /**
   * Removes the listener from the collection of listeners who will be notified
   * when the control is verified.
   *
   * @param listener
   *          the listener which should no longer be notified
   *
   * @exception IllegalArgumentException
   *              <ul>
   *              <li>ERROR_NULL_ARGUMENT - if the listener is null</li>
   *              </ul>
   * @exception SWTException
   *              <ul>
   *              <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
   *              <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
   *              thread that created the receiver</li>
   *              </ul>
   *
   * @see VerifyListener
   * @see #addVerifyListener
   *
   * @since 3.3
   */
  public void removeVerifyListener(VerifyListener listener) {
    checkWidget();

    if (listener == null) {
      SWT.error(SWT.ERROR_NULL_ARGUMENT);
    }

    removeListener(SWT.Verify, listener);
  }

  /**
   * Selects the item at the given zero-relative index in the receiver's list.
   * If the item at the index was already selected, it remains selected. Indices
   * that are out of range are ignored.
   *
   * @param index
   *          the index of the item to select
   *
   * @exception SWTException
   *              <ul>
   *              <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
   *              <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
   *              thread that created the receiver</li>
   *              </ul>
   */
  public void select(int index) {
    checkWidget();

    // deselect if a value of -1 is passed in.
    if (index == -1) {
      comboPanel.clearSelection(this, table);
      text.setText("");
      selectedImage.setImage(null);

      return;
    }

    comboPanel.setSelection(this, table, index);
  }

  /**
   * {@inheritDoc}
   */
  public void setBackground(Color color) {
    super.setBackground(color);
    background = color;

    if (text != null) {
      text.setBackground(color);
    }

    if (selectedImage != null) {
      selectedImage.setBackground(color);
    }

    if (table != null) {
      table.setBackground(color);
    }

    if (arrow != null) {
      arrow.setBackground(color);
    }
  }

  public void setComboPanel(iComboPanel comboPanel) {
    this.comboPanel = comboPanel;
  }

  /**
   * Sets the editable state.
   *
   * @param editable
   *          the new editable state
   *
   * @exception SWTException
   *              <ul>
   *              <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
   *              <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
   *              thread that created the receiver</li>
   *              </ul>
   *
   * @since 3.0
   */
  public void setEditable(boolean editable) {
    checkWidget();
    text.setEditable(editable);
  }

  /**
   * {@inheritDoc}
   */
  public void setEnabled(boolean enabled) {
    super.setEnabled(enabled);

    if (popup != null) {
      popup.setVisible(false);
    }

    if (selectedImage != null) {
      selectedImage.setEnabled(enabled);
    }

    if (text != null) {
      text.setEnabled(enabled);
    }

    if (arrow != null) {
      arrow.setEnabled(enabled);
    }
  }

  /**
   * {@inheritDoc}
   */
  public boolean setFocus() {
    checkWidget();

    if (!isEnabled() ||!isVisible()) {
      return false;
    }

    if (isFocusControl()) {
      return true;
    }

    return text.setFocus();
  }

  /**
   * {@inheritDoc}
   */
  public void setFont(Font font) {
    super.setFont(font);
    this.font = font;
    text.setFont(font);
    table.setFont(font);
    internalLayout(true);
  }

  /**
   * {@inheritDoc}
   */
  public void setForeground(Color color) {
    super.setForeground(color);
    foreground = color;

    if (text != null) {
      text.setForeground(color);
    }

    if (table != null) {
      table.setForeground(color);
    }

    if (arrow != null) {
      arrow.setForeground(color);
    }
  }

  /**
   * Sets the layout which is associated with the receiver to be the argument
   * which may be null.
   * <p>
   * Note : No Layout can be set on this Control because it already manages the
   * size and position of its children.
   * </p>
   *
   * @param layout
   *          the receiver's new layout or null
   *
   * @exception SWTException
   *              <ul>
   *              <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
   *              <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
   *              thread that created the receiver</li>
   *              </ul>
   */
  public void setLayout(Layout layout) {
    checkWidget();

    return;
  }

  /**
   * Sets the selection in the receiver's text field to the range specified by
   * the argument whose x coordinate is the start of the selection and whose y
   * coordinate is the end of the selection.
   *
   * @param selection
   *          a point representing the new selection start and end
   *
   * @exception IllegalArgumentException
   *              <ul>
   *              <li>ERROR_NULL_ARGUMENT - if the point is null</li>
   *              </ul>
   * @exception SWTException
   *              <ul>
   *              <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
   *              <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
   *              thread that created the receiver</li>
   *              </ul>
   */
  public void setSelection(Point selection) {
    checkWidget();

    if (selection == null) {
      SWT.error(SWT.ERROR_NULL_ARGUMENT);
    }

    text.setSelection(selection.x, selection.y);
  }

  /**
   * Marks the receiver's list as visible if the argument is <code>true</code>,
   * and marks it invisible otherwise.
   * <p>
   * If one of the receiver's ancestors is not visible or some other condition
   * makes the receiver not visible, marking it visible may not actually cause
   * it to be displayed.
   * </p>
   *
   * @param visible
   *          the new visibility state
   *
   * @exception SWTException
   *              <ul>
   *              <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
   *              <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
   *              thread that created the receiver</li>
   *              </ul>
   *
   * @since 3.4
   */
  public void setTableVisible(boolean visible) {
    checkWidget();
    dropDown(visible);
  }

  /**
   * Sets the contents of the receiver's text field to the given string.
   * <p>
   * Note: The text field in a <code>Combo</code> is typically only capable of
   * displaying a single line of text. Thus, setting the text to a string
   * containing line breaks or other special characters will probably cause it
   * to display incorrectly.
   * </p>
   *
   * @param string
   *          the new text
   *
   * @exception IllegalArgumentException
   *              <ul>
   *              <li>ERROR_NULL_ARGUMENT - if the string is null</li>
   *              </ul>
   * @exception SWTException
   *              <ul>
   *              <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
   *              <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
   *              thread that created the receiver</li>
   *              </ul>
   */
  public void setText(String string) {
    checkWidget();

    int index = comboPanel.getIndexForText(this, table, string);

    select(index);
  }

  /**
   * Sets the maximum number of characters that the receiver's text field is
   * capable of holding to be the argument.
   *
   * @param limit
   *          new text limit
   *
   * @exception IllegalArgumentException
   *              <ul>
   *              <li>ERROR_CANNOT_BE_ZERO - if the limit is zero</li>
   *              </ul>
   * @exception SWTException
   *              <ul>
   *              <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
   *              <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
   *              thread that created the receiver</li>
   *              </ul>
   */
  public void setTextLimit(int limit) {
    checkWidget();
    text.setTextLimit(limit);
  }

  /**
   * {@inheritDoc}
   */
  public void setToolTipText(String tipText) {
    checkWidget();
    super.setToolTipText(tipText);

    if (selectedImage != null) {
      selectedImage.setToolTipText(tipText);
    }

    if (text != null) {
      text.setToolTipText(tipText);
    }

    if (arrow != null) {
      arrow.setToolTipText(tipText);
    }
  }

  /**
   * {@inheritDoc}
   */
  public void setVisible(boolean visible) {
    super.setVisible(visible);

    /*
     * At this point the widget may have been disposed in a FocusOut event. If
     * so then do not continue.
     */
    if (isDisposed()) {
      return;
    }

    // TEMPORARY CODE
    if ((popup == null) || popup.isDisposed()) {
      return;
    }

    if (!visible) {
      popup.setVisible(false);
    }
  }

  /**
   * {@inheritDoc}
   */
  public Control[] getChildren() {
    checkWidget();

    return new Control[0];
  }

  public iComboPanel getComboPanel() {
    return comboPanel;
  }

  /**
   * Gets the editable state.
   *
   * @return whether or not the receiver is editable
   *
   * @exception SWTException
   *              <ul>
   *              <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
   *              <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
   *              thread that created the receiver</li>
   *              </ul>
   *
   * @since 3.0
   */
  public boolean getEditable() {
    checkWidget();

    return text.getEditable();
  }

  /**
   * Returns a <code>Point</code> whose x coordinate is the start of the
   * selection in the receiver's text field, and whose y coordinate is the end
   * of the selection. The returned values are zero-relative. An "empty"
   * selection as indicated by the the x and y coordinates having the same
   * value.
   *
   * @return a point representing the selection start and end
   *
   * @exception SWTException
   *              <ul>
   *              <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
   *              <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
   *              thread that created the receiver</li>
   *              </ul>
   */
  public Point getSelection() {
    checkWidget();

    return text.getSelection();
  }

  /**
   * Returns the zero-relative index of the item which is currently selected in
   * the receiver's list, or -1 if no item is selected.
   *
   * @return the index of the selected item
   *
   * @exception SWTException
   *              <ul>
   *              <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
   *              <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
   *              thread that created the receiver</li>
   *              </ul>
   */
  public int getSelectionIndex() {
    checkWidget();

    return comboPanel.getSelectionIndex(this, table);
  }

  /**
   * {@inheritDoc}
   */
  public int getStyle() {
    checkWidget();

    int style = super.getStyle();

    style &= ~SWT.READ_ONLY;

    if (!text.getEditable()) {
      style |= SWT.READ_ONLY;
    }

    return style;
  }

  /**
   * Returns a string containing a copy of the contents of the receiver's text
   * field.
   *
   * @return the receiver's text
   *
   * @exception SWTException
   *              <ul>
   *              <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
   *              <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
   *              thread that created the receiver</li>
   *              </ul>
   */
  public String getText() {
    checkWidget();

    return text.getText();
  }

  /**
   * Returns the Text control reference.
   *
   * @return
   */
  public Text getTextControl() {
    checkWidget();

    return text;
  }

  /**
   * Returns the height of the receivers's text field.
   *
   * @return the text height
   *
   * @exception SWTException
   *              <ul>
   *              <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
   *              <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
   *              thread that created the receiver</li>
   *              </ul>
   */
  public int getTextHeight() {
    checkWidget();

    return text.getLineHeight();
  }

  /**
   * {@inheritDoc}
   */
  public boolean isFocusControl() {
    checkWidget();

    // if (label.isFocusControl () || arrow.isFocusControl () ||
    // table.isFocusControl () || popup.isFocusControl ()) {
    if (arrow.isFocusControl() || table.isFocusControl() || popup.isFocusControl()) {
      return true;
    }

    return super.isFocusControl();
  }

  /**
   * creates the popup shell.
   *
   * @param selectionIndex
   */
  void createPopup(int selectionIndex) {
    // create shell and table
    popup = new Shell(getShell(), SWT.NO_TRIM | SWT.ON_TOP);

    // set style
    int style = getStyle();

    table = comboPanel.createComposite(this, popup, foreground, background, font, style, listener);

    // Add popup listeners
    int[] popupEvents = { SWT.Close, SWT.Paint, SWT.Deactivate, SWT.Help };

    for (int i = 0; i < popupEvents.length; i++) {
      popup.addListener(popupEvents[i], listener);
    }

    // set the selection
    if (selectionIndex != -1) {
      comboPanel.setSelection(this, table, selectionIndex);
    }
  }

  /**
   * handle DropDown request
   *
   * @param drop
   */
  void dropDown(boolean drop) {
    // if already dropped then return
    if (drop == isDropped()) {
      return;
    }

    // closing the dropDown
    if (!drop) {
      popup.setVisible(false);

      if (!isDisposed() && isFocusControl()) {
        text.setFocus();
      }

      return;
    }

    // if not visible then return
    if (!isVisible()) {
      return;
    }

    // create a new popup if needed.
    if (getShell() != popup.getParent()) {
      int selectionIndex = comboPanel.getSelectionIndex(this, table);

      table.removeListener(SWT.Dispose, listener);
      popup.dispose();
      popup = null;
      table = null;
      createPopup(selectionIndex);
    }

    comboPanel.prepareToShow(this, table);

    // calculate popup dimensions.
    Display   display      = getDisplay();
    Rectangle tableRect    = table.getBounds();
    Rectangle parentRect   = display.map(getParent(), null, getBounds());
    Point     comboSize    = getSize();
    Rectangle displayRect  = getMonitor().getClientArea();
    int       overallWidth = 0;

    overallWidth = Math.max(comboSize.x, tableRect.width + 2);

    int overallHeight = tableRect.height + 2;
    int x             = parentRect.x;
    int y             = parentRect.y + comboSize.y;

    if (y + overallHeight > displayRect.y + displayRect.height) {
      y = parentRect.y - overallHeight;
    }

    if (x + overallWidth > displayRect.x + displayRect.width) {
      x = displayRect.x + displayRect.width - tableRect.width;
    }

    // set the bounds of the popup
    popup.setBounds(x, y, overallWidth, overallHeight);
    // set the popup visible
    popup.setVisible(true);
    // set focus on the table.
    table.setFocus();
  }

  /**
   * Add Accessbile listeners to label and table.
   */
  void initAccessible() {
    AccessibleAdapter accessibleAdapter = new AccessibleAdapter() {
      public void getName(AccessibleEvent e) {
        String name  = null;
        Label  label = getAssociatedLabel();

        if (label != null) {
          name = stripMnemonic(text.getText());
        }

        e.result = name;
      }
      public void getKeyboardShortcut(AccessibleEvent e) {
        String shortcut = null;
        Label  label    = getAssociatedLabel();

        if (label != null) {
          String text = label.getText();

          if (text != null) {
            char mnemonic = _findMnemonic(text);

            if (mnemonic != '\0') {
              shortcut = "Alt+" + mnemonic;    //$NON-NLS-1$
            }
          }
        }

        e.result = shortcut;
      }
      public void getHelp(AccessibleEvent e) {
        e.result = getToolTipText();
      }
    };

    getAccessible().addAccessibleListener(accessibleAdapter);
    text.getAccessible().addAccessibleListener(accessibleAdapter);
    table.getAccessible().addAccessibleListener(accessibleAdapter);
    arrow.getAccessible().addAccessibleListener(new AccessibleAdapter() {
      public void getName(AccessibleEvent e) {
        e.result = isDropped()
                   ? SWT.getMessage("SWT_Close")
                   : SWT.getMessage("SWT_Open");    //$NON-NLS-1$ //$NON-NLS-2$
      }
      public void getKeyboardShortcut(AccessibleEvent e) {
        e.result = "Alt+Down Arrow";    //$NON-NLS-1$
      }
      public void getHelp(AccessibleEvent e) {
        e.result = getToolTipText();
      }
    });
    getAccessible().addAccessibleTextListener(new AccessibleTextAdapter() {
      public void getCaretOffset(AccessibleTextEvent e) {
        e.offset = text.getCaretPosition();
      }
      public void getSelectionRange(AccessibleTextEvent e) {
        Point sel = text.getSelection();

        e.offset = sel.x;
        e.length = sel.y - sel.x;
      }
    });
    getAccessible().addAccessibleControlListener(new AccessibleControlAdapter() {
      public void getChildAtPoint(AccessibleControlEvent e) {
        Point testPoint = toControl(e.x, e.y);

        if (getBounds().contains(testPoint)) {
          e.childID = ACC.CHILDID_SELF;
        }
      }
      public void getLocation(AccessibleControlEvent e) {
        Rectangle location = getBounds();
        Point     pt       = getParent().toDisplay(location.x, location.y);

        e.x      = pt.x;
        e.y      = pt.y;
        e.width  = location.width;
        e.height = location.height;
      }
      public void getChildCount(AccessibleControlEvent e) {
        e.detail = 0;
      }
      public void getRole(AccessibleControlEvent e) {
        e.detail = ACC.ROLE_COMBOBOX;
      }
      public void getState(AccessibleControlEvent e) {
        e.detail = ACC.STATE_NORMAL;
      }
      public void getValue(AccessibleControlEvent e) {
        e.result = text.getText();
      }
    });
    text.getAccessible().addAccessibleControlListener(new AccessibleControlAdapter() {
      public void getRole(AccessibleControlEvent e) {
        e.detail = text.getEditable()
                   ? ACC.ROLE_TEXT
                   : ACC.ROLE_LABEL;
      }
    });
    arrow.getAccessible().addAccessibleControlListener(new AccessibleControlAdapter() {
      public void getDefaultAction(AccessibleControlEvent e) {
        e.result = isDropped()
                   ? SWT.getMessage("SWT_Close")
                   : SWT.getMessage("SWT_Open");    //$NON-NLS-1$ //$NON-NLS-2$
      }
    });
  }

  void textEvent(Event event) {
    switch(event.type) {
      case SWT.FocusIn : {
        handleFocus(SWT.FocusIn);

        break;
      }

      case SWT.DefaultSelection : {
        dropDown(false);

        Event e = new Event();

        e.time      = event.time;
        e.stateMask = event.stateMask;
        notifyListeners(SWT.DefaultSelection, e);

        break;
      }

      case SWT.KeyDown : {
        Event keyEvent = new Event();

        keyEvent.time      = event.time;
        keyEvent.character = event.character;
        keyEvent.keyCode   = event.keyCode;
        keyEvent.stateMask = event.stateMask;
        notifyListeners(SWT.KeyDown, keyEvent);

        if (isDisposed()) {
          break;
        }

        event.doit = keyEvent.doit;

        if (!event.doit) {
          break;
        }

        break;
      }

      case SWT.KeyUp : {
        Event e = new Event();

        e.time      = event.time;
        e.character = event.character;
        e.keyCode   = event.keyCode;
        e.stateMask = event.stateMask;
        notifyListeners(SWT.KeyUp, e);
        event.doit = e.doit;

        break;
      }

      case SWT.MenuDetect : {
        Event e = new Event();

        e.time = event.time;
        notifyListeners(SWT.MenuDetect, e);

        break;
      }

      case SWT.Modify : {
        comboPanel.clearSelection(this, table);

        Event e = new Event();

        e.time = event.time;
        notifyListeners(SWT.Modify, e);

        break;
      }

      case SWT.MouseDown : {
        Event mouseEvent = new Event();

        mouseEvent.button    = event.button;
        mouseEvent.count     = event.count;
        mouseEvent.stateMask = event.stateMask;
        mouseEvent.time      = event.time;
        mouseEvent.x         = event.x;
        mouseEvent.y         = event.y;
        notifyListeners(SWT.MouseDown, mouseEvent);

        if (isDisposed()) {
          break;
        }

        event.doit = mouseEvent.doit;

        if (!event.doit) {
          break;
        }

        if (event.button != 1) {
          return;
        }

        if (text.getEditable()) {
          return;
        }

        boolean dropped = isDropped();

        text.selectAll();

        if (!dropped) {
          setFocus();
        }

        dropDown(!dropped);

        break;
      }

      case SWT.MouseUp : {
        Event mouseEvent = new Event();

        mouseEvent.button    = event.button;
        mouseEvent.count     = event.count;
        mouseEvent.stateMask = event.stateMask;
        mouseEvent.time      = event.time;
        mouseEvent.x         = event.x;
        mouseEvent.y         = event.y;
        notifyListeners(SWT.MouseUp, mouseEvent);

        if (isDisposed()) {
          break;
        }

        event.doit = mouseEvent.doit;

        if (!event.doit) {
          break;
        }

        if (event.button != 1) {
          return;
        }

        if (text.getEditable()) {
          return;
        }

        text.selectAll();

        break;
      }

      case SWT.MouseDoubleClick : {
        Event mouseEvent = new Event();

        mouseEvent.button    = event.button;
        mouseEvent.count     = event.count;
        mouseEvent.stateMask = event.stateMask;
        mouseEvent.time      = event.time;
        mouseEvent.x         = event.x;
        mouseEvent.y         = event.y;
        notifyListeners(SWT.MouseDoubleClick, mouseEvent);

        break;
      }

      case SWT.Traverse : {
        switch(event.detail) {
          case SWT.TRAVERSE_ARROW_PREVIOUS :
          case SWT.TRAVERSE_ARROW_NEXT :
            // The enter causes default selection and
            // the arrow keys are used to manipulate the list contents so
            // do not use them for traversal.
            event.doit = false;

            break;

          case SWT.TRAVERSE_TAB_PREVIOUS :
            event.doit   = traverse(SWT.TRAVERSE_TAB_PREVIOUS);
            event.detail = SWT.TRAVERSE_NONE;

            return;
        }

        Event e = new Event();

        e.time      = event.time;
        e.detail    = event.detail;
        e.doit      = event.doit;
        e.character = event.character;
        e.keyCode   = event.keyCode;
        notifyListeners(SWT.Traverse, e);
        event.doit   = e.doit;
        event.detail = e.detail;

        break;
      }

      case SWT.Verify : {
        Event e = new Event();

        e.text      = event.text;
        e.start     = event.start;
        e.end       = event.end;
        e.character = event.character;
        e.keyCode   = event.keyCode;
        e.stateMask = event.stateMask;
        notifyListeners(SWT.Verify, e);
        event.doit = e.doit;

        break;
      }
    }
  }

  /*
   * Return the lowercase of the first non-'&' character following an '&'
   * character in the given string. If there are no '&' characters in the given
   * string, return '\0'.
   */
  private char _findMnemonic(String string) {
    if (string == null) {
      return '\0';
    }

    int index  = 0;
    int length = string.length();

    do {
      while((index < length) && (string.charAt(index) != '&')) {
        index++;
      }

      if (++index >= length) {
        return '\0';
      }

      if (string.charAt(index) != '&') {
        return Character.toLowerCase(string.charAt(index));
      }

      index++;
    } while(index < length);

    return '\0';
  }

  /**
   * Handle Arrow Event
   *
   * @param event
   */
  private void arrowEvent(Event event) {
    switch(event.type) {
      case SWT.FocusIn : {
        handleFocus(SWT.FocusIn);

        break;
      }

      case SWT.MouseDown : {
        Event mouseEvent = new Event();

        mouseEvent.button    = event.button;
        mouseEvent.count     = event.count;
        mouseEvent.stateMask = event.stateMask;
        mouseEvent.time      = event.time;
        mouseEvent.x         = event.x;
        mouseEvent.y         = event.y;
        notifyListeners(SWT.MouseDown, mouseEvent);
        event.doit = mouseEvent.doit;

        break;
      }

      case SWT.MouseUp : {
        Event mouseEvent = new Event();

        mouseEvent.button    = event.button;
        mouseEvent.count     = event.count;
        mouseEvent.stateMask = event.stateMask;
        mouseEvent.time      = event.time;
        mouseEvent.x         = event.x;
        mouseEvent.y         = event.y;
        notifyListeners(SWT.MouseUp, mouseEvent);
        event.doit = mouseEvent.doit;

        break;
      }

      case SWT.Selection : {
        text.setFocus();
        dropDown(!isDropped());

        break;
      }
    }
  }

  /**
   * @param style
   * @return
   */
  private static int checkStyle(int style) {
    int mask = SWT.BORDER | SWT.READ_ONLY | SWT.FLAT | SWT.LEFT_TO_RIGHT | SWT.RIGHT_TO_LEFT;

    return SWT.NO_FOCUS | (style & mask);
  }

  /**
   * Handle Combo events
   *
   * @param event
   */
  private void comboEvent(Event event) {
    switch(event.type) {
      case SWT.Dispose :
        removeListener(SWT.Dispose, listener);
        notifyListeners(SWT.Dispose, event);
        event.type = SWT.None;

        if ((popup != null) &&!popup.isDisposed()) {
          table.removeListener(SWT.Dispose, listener);
          popup.dispose();
        }

        Shell shell = getShell();

        shell.removeListener(SWT.Deactivate, listener);

        Display display = getDisplay();

        display.removeFilter(SWT.FocusIn, focusFilter);
        popup         = null;
        text          = null;
        table         = null;
        arrow         = null;
        selectedImage = null;

        break;

      case SWT.FocusIn :
        Control focusControl = getDisplay().getFocusControl();

        if ((focusControl == arrow) || (focusControl == table)) {
          return;
        }

        if (isDropped()) {
          table.setFocus();
        } else {
          text.setFocus();
        }

        break;

      case SWT.Move :
        dropDown(false);

        break;

      case SWT.Resize :
        internalLayout(false);

        break;
    }
  }

  /**
   * Handle Focus event
   *
   * @param type
   */
  private void handleFocus(int type) {
    switch(type) {
      case SWT.FocusIn : {
        if (hasFocus) {
          return;
        }

        if (getEditable()) {
          text.selectAll();
        }

        hasFocus = true;

        Shell shell = getShell();

        shell.removeListener(SWT.Deactivate, listener);
        shell.addListener(SWT.Deactivate, listener);

        Display display = getDisplay();

        display.removeFilter(SWT.FocusIn, focusFilter);
        display.addFilter(SWT.FocusIn, focusFilter);

        Event e = new Event();

        notifyListeners(SWT.FocusIn, e);

        break;
      }

      case SWT.FocusOut : {
        if (!hasFocus) {
          return;
        }

        Control focusControl = getDisplay().getFocusControl();

        if ((focusControl == arrow) || (focusControl == table) || (focusControl == text)) {
          return;
        }

        hasFocus = false;

        Shell shell = getShell();

        shell.removeListener(SWT.Deactivate, listener);

        Display display = getDisplay();

        display.removeFilter(SWT.FocusIn, focusFilter);

        Event e = new Event();

        notifyListeners(SWT.FocusOut, e);

        break;
      }
    }
  }

  /**
   * This method is invoked when a resize event occurs.
   *
   * @param changed
   */
  private void internalLayout(boolean changed) {
    if (isDropped()) {
      dropDown(false);
    }

    Rectangle rect      = getClientArea();
    int       width     = rect.width;
    int       height    = rect.height;
    Point     arrowSize = arrow.computeSize(SWT.DEFAULT, height, changed);
    // calculate text vertical alignment.
    int   textYPos = 0;
    Point textSize = text.computeSize(SWT.DEFAULT, SWT.DEFAULT);

    if (textSize.y < height) {
      textYPos = (height - textSize.y) / 2;
    }

    // does the selected entry have a image associated with it?
    if (selectedImage.getImage() == null) {
      // set image, text, and arrow boundaries
      selectedImage.setBounds(0, 0, 0, 0);
      text.setBounds(0, textYPos, width - arrowSize.x, textSize.y);
      arrow.setBounds(width - arrowSize.x, 0, arrowSize.x, arrowSize.y);
    } else {
      // calculate the amount of width left in the control after taking into
      // account the arrow selector
      int   remainingWidth = width - arrowSize.x;
      Point imageSize      = selectedImage.computeSize(SWT.DEFAULT, height, changed);
      int   imageWidth     = imageSize.x + 2;

      // handle the case where the image is larger than the available space in
      // the control.
      if (imageWidth > remainingWidth) {
        imageWidth     = remainingWidth;
        remainingWidth = 0;
      } else {
        remainingWidth = remainingWidth - imageWidth;
      }

      // set the width of the text.
      int textWidth = remainingWidth;

      // set image, text, and arrow boundaries
      selectedImage.setBounds(0, 0, imageWidth, imageSize.y);
      text.setBounds(imageWidth, textYPos, textWidth, textSize.y);
      arrow.setBounds(imageWidth + textWidth, 0, arrowSize.x, arrowSize.y);
    }
  }

  /**
   * Handles Popup Events
   *
   * @param event
   */
  private void popupEvent(Event event) {
    switch(event.type) {
      case SWT.Paint :
        // draw rectangle around table
        Rectangle tableRect = table.getBounds();

        event.gc.setForeground(getDisplay().getSystemColor(SWT.COLOR_LIST_SELECTION));
        event.gc.drawRectangle(0, 0, tableRect.width + 1, tableRect.height + 1);

        break;

      case SWT.Close :
        event.doit = false;
        dropDown(false);

        break;

      case SWT.Deactivate :
        /*
         * Bug in GTK. When the arrow button is pressed the popup control receives
         * a deactivate event and then the arrow button receives a selection
         * event. If we hide the popup in the deactivate event, the selection
         * event will show it again. To prevent the popup from showing again, we
         * will let the selection event of the arrow button hide the popup. In
         * Windows, hiding the popup during the deactivate causes the deactivate
         * to be called twice and the selection event to be disappear.
         */
        if (!"carbon".equals(SWT.getPlatform())) {
          Point     point = arrow.toControl(getDisplay().getCursorLocation());
          Point     size  = arrow.getSize();
          Rectangle rect  = new Rectangle(0, 0, size.x, size.y);

          if (!rect.contains(point)) {
            dropDown(false);
          }
        } else {
          dropDown(false);
        }

        break;

      case SWT.Help :
        if (isDropped()) {
          dropDown(false);
        }

        Composite comp = CompositeCombo.this;

        do {
          if ((comp.getListeners(event.type) != null) && (comp.getListeners(event.type).length > 0)) {
            comp.notifyListeners(event.type, event);

            break;
          }

          comp = comp.getParent();
        } while(null != comp);

        break;
    }
  }

  /**
   * @param string
   * @return
   */
  private String stripMnemonic(String string) {
    int index  = 0;
    int length = string.length();

    do {
      while((index < length) && (string.charAt(index) != '&')) {
        index++;
      }

      if (++index >= length) {
        return string;
      }

      if (string.charAt(index) != '&') {
        return string.substring(0, index - 1) + string.substring(index, length);
      }

      index++;
    } while(index < length);

    return string;
  }

  /*
   * Return the Label immediately preceding the receiver in the z-order, or null
   * if none.
   */
  private Label getAssociatedLabel() {
    Control[] siblings = getParent().getChildren();

    for (int i = 0; i < siblings.length; i++) {
      if (siblings[i] == CompositeCombo.this) {
        if ((i > 0) && (siblings[i - 1] instanceof Label)) {
          return (Label) siblings[i - 1];
        }
      }
    }

    return null;
  }

  /**
   * returns if the drop down is currently open
   *
   * @return
   */
  private boolean isDropped() {
    return popup.getVisible();
  }
}
