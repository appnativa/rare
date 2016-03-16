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

package com.appnativa.rare.platform;

import com.appnativa.rare.Platform;
import com.appnativa.rare.exception.ApplicationException;
import com.appnativa.rare.iConstants;
import com.appnativa.rare.iPlatformAppContext;
import com.appnativa.rare.platform.swing.ui.util.SwingHelper;
import com.appnativa.rare.ui.FocusedAction;
import com.appnativa.rare.ui.UIAction;
import com.appnativa.rare.ui.aFocusedAction;
import com.appnativa.rare.ui.dnd.iTransferSupport;
import com.appnativa.rare.ui.event.ActionEvent;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.viewer.aListViewer;
import com.appnativa.rare.widget.aListWidget;
import com.appnativa.rare.widget.aWidget;
import com.appnativa.rare.widget.iWidget;

import java.awt.Component;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.FlavorEvent;
import java.awt.datatransfer.FlavorListener;
import java.awt.event.KeyEvent;
import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.Iterator;

import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import javax.swing.TransferHandler;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;

/**
 * Portions of the code are from the PasswordStore tutorial which have the following copyright:
 * Copyright (C) 2006 Sun Microsystems, Inc. All rights reserved. Use is
 * subject to license terms.
 */

/**
 * Helper class providing cut, copy and paste actions that target the selected
 * component's TransferHandler. The action's enabled state is updated to
 * reflect the ability for the focused component to perform cut, copy and
 * paste.
 *
 * @author sky
 * @author DOn DeCoteau
 */
public final class ActionHelper {
  private static final String        CUT_CLIENT_PROPERTY        = iConstants.CUT_ACTION_NAME;
  private static final String        COPY_CLIENT_PROPERTY       = iConstants.COPY_ACTION_NAME;
  private static final String        DELETE_CLIENT_PROPERTY     = iConstants.DELETE_ACTION_NAME;
  private static final String        PASTE_CLIENT_PROPERTY      = iConstants.PASTE_ACTION_NAME;
  private static final String        SELECT_ALL_CLIENT_PROPERTY = iConstants.SELECTALL_ACTION_NAME;
  private static final String        REPLACE_CLIENT_PROPERTY    = iConstants.REPLACE_ACTION_NAME;
  private static final String        FIND_CLIENT_PROPERTY       = iConstants.FIND_ACTION_NAME;
  public static final String         SEARCH_SUPPORT             = ActionHelper.class.getName() + ".searchSupport";
  private static final Clipboard     CLIPBOARD;
  private static final FocusedAction COPY_INSTANCE;
  private static final FocusedAction CUT_INSTANCE;
  private static final FocusedAction DELETE_INSTANCE;
  private static final UIAction      DO_CLICK_INSTANCE;
  private static final UIAction      NEXT_COMPONENT;
  private static final FocusedAction PASTE_INSTANCE;
  private static final UIAction      PREVIOUS_COMPONENT;
  private static final FocusedAction REDO_INSTANCE;
  private static final FocusedAction SELECT_ALL_INSTANCE;
  private static final FocusedAction UNDO_INSTANCE;
  private static boolean             disableBindings;

  static {
    Clipboard clipboard;

    try {
      clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    } catch(SecurityException e) {
      // Don't have access to the clipboard, create a new one
      clipboard = new Clipboard("Sandboxed Clipboard");
    }

    CLIPBOARD           = clipboard;
    CUT_INSTANCE        = new CutCopyAction(true);
    COPY_INSTANCE       = new CutCopyAction(false);
    PASTE_INSTANCE      = new PasteAction();
    SELECT_ALL_INSTANCE = new SelectAllAction();
    REDO_INSTANCE       = new RedoAction();
    UNDO_INSTANCE       = new UndoAction();
    DELETE_INSTANCE     = new DeleteAction();
    DO_CLICK_INSTANCE   = new DoClickAction();
    NEXT_COMPONENT      = new UIAction(iConstants.NEXT_COMPONENT_ACTION_NAME) {
      @Override
      public void actionPerformed(ActionEvent evt) {
        ((Component) evt.getSource()).transferFocus();
      }
    };
    PREVIOUS_COMPONENT = new UIAction(iConstants.PREV_COMPONENT_ACTION_NAME) {
      @Override
      public void actionPerformed(ActionEvent evt) {
        ((Component) evt.getSource()).transferFocusBackward();
      }
    };
  }

  private ActionHelper() {}

  public static void dispose(Iterator<UIAction> it) {
    UIAction a;

    while(it.hasNext()) {
      a = it.next();

      if (a != null) {
        a.dispose();
      }
    }
  }

  public static void clearFocusedComponentReferences() {
    if (CUT_INSTANCE != null) {
      CUT_INSTANCE.update(null);
    }

    if (COPY_INSTANCE != null) {
      COPY_INSTANCE.update(null);
    }

    if (PASTE_INSTANCE != null) {
      PASTE_INSTANCE.update(null);
    }

    if (SELECT_ALL_INSTANCE != null) {
      SELECT_ALL_INSTANCE.update(null);
    }

    if (DELETE_INSTANCE != null) {
      DELETE_INSTANCE.update(null);
    }
  }

  public static void disposeReferences(Iterator<WeakReference<UIAction>> it) {
    UIAction                a;
    WeakReference<UIAction> r;

    while(it.hasNext()) {
      r = it.next();
      a = (r == null)
          ? null
          : r.get();

      if (a != null) {
        a.dispose();
      }
    }
  }

  /**
   * Registers a custom action with the specified component
   *
   * @param component  the Component to set the enabled state for
   * @param actionName the property name of the operation to enable
   *
   */
  public static void registerCustomAction(iPlatformComponent component, String actionName) {
    component.putClientProperty(iConstants.APP_ACTION_PREFIX + actionName, true);
  }

  /**
   * Registers the appropriate key bindings for cut, copy, and paste on the
   * specified component. Registered bindings target the actions provided by
   * this class.
   *
   * @param component the component to register bindings for
   */
  public static void registerCutCopyPasteBindings(iPlatformComponent component) {
    registerCutCopyPasteBindings(component, false);
  }

  /**
   * Registers the appropriate key bindings for cut, copy, and paste on the
   * specified component. Registered bindings target the actions provided by
   * this class.
   *
   * @param component the component to register bindings for
   * @param registerDelete {@inheritDoc}
   */
  public static void registerCutCopyPasteBindings(iPlatformComponent c, boolean registerDelete) {
    if (!disableBindings) {
      JComponent component = c.getView();
      InputMap   inputMap  = component.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
      int        mask      = Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();

      inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_C, mask), COPY_INSTANCE);
      inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_X, mask), CUT_INSTANCE);
      inputMap.put(KeyStroke.getKeyStroke("COPY"), COPY_INSTANCE);
      inputMap.put(KeyStroke.getKeyStroke("CUT"), CUT_INSTANCE);
      inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_INSERT, mask), COPY_INSTANCE);
      inputMap.put(KeyStroke.getKeyStroke("shift DELETE"), CUT_INSTANCE);
      inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_V, mask), PASTE_INSTANCE);
      inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, mask), SELECT_ALL_INSTANCE);
      inputMap.put(KeyStroke.getKeyStroke("PASTE"), PASTE_INSTANCE);
      inputMap.put(KeyStroke.getKeyStroke("shift INSERT"), PASTE_INSTANCE);

      if (registerDelete) {
        inputMap.put(KeyStroke.getKeyStroke("DELETE"), DELETE_INSTANCE);
      }

      ActionMap actionMap = component.getActionMap();

      actionMap.put(CUT_INSTANCE, CUT_INSTANCE);
      actionMap.put(COPY_INSTANCE, COPY_INSTANCE);
      actionMap.put(PASTE_INSTANCE, PASTE_INSTANCE);
      actionMap.put(CUT_INSTANCE, CUT_INSTANCE);
      actionMap.put(SELECT_ALL_INSTANCE, SELECT_ALL_INSTANCE);

      if (registerDelete) {
        actionMap.put(DELETE_INSTANCE, DELETE_INSTANCE);
      }
    }
  }

  /**
   * Registers a custom action with the specified component
   *
   * @param w the widget context
   * @param component  the Component to set the enabled state for
   * @param name the name of the key stroke that initiates the action
   * @param code to execute for the action. if the code string starts with
   *             "action:" the the rest of the string is assumed to refer to
   *             a predefined action
   */
  public static boolean registerKeystroke(iWidget w, JComponent component, String name, Object code, int mapType) {
    if (!disableBindings) {
      KeyStroke ks;
      Action    a = null;

      ks = SwingHelper.getKeyStroke(name);

      if (w == null) {
        w = Platform.getContextRootViewer();
      }

      iPlatformAppContext app = w.getAppContext();

      if (ks == null) {
        return false;
      }

      String s = (code instanceof String)
                 ? (String) code
                 : null;

      if ((s != null) && s.startsWith("action:")) {
        s = s.substring(7);
        a = app.getAction(s);

        if (a == null) {
          a = component.getActionMap().get(code);
        }

        if (a == null) {
          s = Platform.getResourceAsString("Rare.runtime.text.undefinedAction");
          Platform.ignoreException("registerKeyStroke", new ApplicationException(s, code));

          return false;
        }
      } else {
        UIAction ua = new UIAction("Rare.Keystroke." + name);

        ua.setActionScript(code);
        ua.setContext(w);
        a = ua;

        Object oa = component.getInputMap().get(ks);

        if (oa != null) {
          a.putValue("Rare.originalAction", component.getActionMap().get(oa));
        }
      }

      if (mapType == -1) {
        mapType = JComponent.WHEN_FOCUSED;
      }

      component.getInputMap(mapType).put(ks, a);
      component.getActionMap().put(a, a);
    }

    return true;
  }

  /**
   * {@inheritDoc}
   *
   * @param component {@inheritDoc}
   * @param manager {@inheritDoc}
   */
  public static void registerUndoManager(iPlatformComponent c, UndoManager manager) {
    if (!disableBindings) {
      JComponent component = c.getView();

      component.putClientProperty(iConstants.UNDO_MANAGER_NAME, manager);

      InputMap inputMap = component.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);

      inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()),
                   UNDO_INSTANCE);
      inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_Y, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()),
                   REDO_INSTANCE);

      ActionMap actionMap = component.getActionMap();

      actionMap.put(UNDO_INSTANCE, UNDO_INSTANCE);
      actionMap.put(REDO_INSTANCE, REDO_INSTANCE);
    }
  }

  /**
   * Unegisters a keystroke with the specified component
   *
   * @param w the widget context
   * @param component  the Component to set the enabled state for
   * @param name the name of the key stroke that initiates the action
   * @return the action,if any, associated with the keystroke
   */
  public static Object unregisterKeyStroke(iWidget w, iPlatformComponent c, String name) {
    Object     a         = null;
    KeyStroke  ks        = SwingHelper.getKeyStroke(name);
    JComponent component = c.getView();

    if (ks != null) {
      a = component.getInputMap().get(ks);

      if (a != null) {
        component.getInputMap().remove(ks);
        component.getActionMap().remove(ks);
      }
    }

    return a;
  }

  public static void updateDefaultActions(iPlatformComponent pc) {
    PASTE_INSTANCE.update(pc);
    UNDO_INSTANCE.update(pc);
    REDO_INSTANCE.update(pc);
    CUT_INSTANCE.update(pc);
    COPY_INSTANCE.update(pc);
    DELETE_INSTANCE.update(pc);
  }

  /**
   * Specifies whether the component can perform a copy operation.
   *
   * @param component the Component to set the enabled state for
   * @param enable true if component supports copy
   */
  public static void setCopyEnabled(iPlatformComponent component, boolean enable) {
    component.putClientProperty(COPY_CLIENT_PROPERTY, enable);
  }

  /**
   * Specifies whether the component can perform a cut operation.
   *
   * @param component the Component to get the enabled state for
   * @param enable true if component supports cut
   */
  public static void setCutEnabled(iPlatformComponent component, boolean enable) {
    component.putClientProperty(CUT_CLIENT_PROPERTY, enable);
  }

  public static void setDeleteEnabled(iPlatformComponent component, boolean enable) {
    component.putClientProperty(DELETE_CLIENT_PROPERTY, enable);
  }

  /**
   * Specifies whether the component can perform a specified operation.
   *
   * @param component  the Component to set the enabled state for
   * @param actionName the name of the action to enable
   * @param enable true if component supports replace
   *
   */
  public static void setEnabled(iPlatformComponent component, String actionName, boolean enable) {
    component.putClientProperty(actionName, enable);
  }

  /**
   * Specifies whether the component can perform a find operation.
   *
   * @param component the Component to set the enabled state for
   * @param enable true if component supports find
   */
  public static void setFindEnabled(iPlatformComponent component, boolean enable) {
    component.putClientProperty(FIND_CLIENT_PROPERTY, enable);
  }

  /**
   * Specifies whether the component can perform a paste operation.
   *
   * @param component the Component to set the enabled state for
   * @param enable true if component supports paste
   */
  public static void setPasteEnabled(iPlatformComponent component, boolean enable) {
    component.putClientProperty(PASTE_CLIENT_PROPERTY, enable);
  }

  /**
   * Specifies whether the component can perform a replace operation.
   *
   * @param component the Component to set the enabled state for
   * @param enable true if component supports replace
   */
  public static void setReplaceEnabled(iPlatformComponent component, boolean enable) {
    component.putClientProperty(REPLACE_CLIENT_PROPERTY, enable);
  }

  /**
   * Specifies whether the component can perform a select-all operation.
   *
   * @param component the Component to set the enabled state for
   * @param enable true if component supports select-all
   */
  public static void setSelectAllEnabled(iPlatformComponent component, boolean enable) {
    component.putClientProperty(SELECT_ALL_CLIENT_PROPERTY, enable);
  }

  public static Clipboard getClipboard() {
    return CLIPBOARD;
  }

  /**
   * Returns an action to perform a copy operation.
   *
   * @return the copy action
   */
  public static aFocusedAction getCopyAction() {
    return COPY_INSTANCE;
  }

  /**
   * Returns an action to perform a cut operation.
   *
   * @return the cut action
   */
  public static aFocusedAction getCutAction() {
    return CUT_INSTANCE;
  }

  /**
   * Returns an action to perform a cut operation.
   *
   * @return the cut action
   */
  public static aFocusedAction getDeleteAction() {
    return DELETE_INSTANCE;
  }

  /**
   * Returns an action to click on a button
   *
   * @return the do click action
   */
  public static UIAction getDoClickAction() {
    return DO_CLICK_INSTANCE;
  }

  /**
   * Returns an action to move the focus to the next component
   *
   * @return the action
   */
  public static UIAction getNextComponentAction() {
    return NEXT_COMPONENT;
  }

  /**
   * Returns an action to perform a paste operation.
   *
   * @return the paste action
   */
  public static aFocusedAction getPasteAction() {
    return PASTE_INSTANCE;
  }

  /**
   * Returns an action to move the focus to the previous component
   *
   * @return the action
   */
  public static UIAction getPreviousComponentAction() {
    return PREVIOUS_COMPONENT;
  }

  /**
   * Returns an action to perform a redo operation.
   *
   * @return the redo action
   */
  public static aFocusedAction getRedoAction() {
    return REDO_INSTANCE;
  }

  /**
   * Returns an action to perform a cut operation.
   *
   * @return the cut action
   */
  public static aFocusedAction getSelectAllAction() {
    return SELECT_ALL_INSTANCE;
  }

  /**
   * Returns an action to perform a undo operation.
   *
   * @return the undo action
   */
  public static aFocusedAction getUndoAction() {
    return UNDO_INSTANCE;
  }

  public static UndoManager getUndoManager(iPlatformComponent component) {
    return (UndoManager) component.getClientProperty(iConstants.UNDO_MANAGER_NAME);
  }

  public static UndoManager getUndoManager(iWidget w) {
    iPlatformComponent c = w.getDataComponent();
    UndoManager        u = (UndoManager) c.getClientProperty(iConstants.UNDO_MANAGER_NAME);

    if (u != null) {
      c = w.getContainerComponent();
      u = (UndoManager) c.getClientProperty(iConstants.UNDO_MANAGER_NAME);
    }

    return u;
  }

  /**
   * Returns whether the specified component has an undo manager
   *
   * @param c the component
   * @return true if it does; false otherwise
   */
  public static boolean hasUndoManager(iPlatformComponent c) {
    return getUndoManager(c) != null;
  }

  /**
   * Returns whether the component can perform the specified operation.
   *
   * @param component the Component to get the enabled state for
   * @param actionName the property name of the operation to check
   *
   *
   * @return {@inheritDoc}
   */
  public static boolean isEnabled(iPlatformComponent component, String actionName) {
    return getBooleanClientProperty(component, actionName);
  }

  private static boolean getBooleanClientProperty(iPlatformComponent c, Object property) {
    Boolean value = (Boolean) c.getClientProperty((property == null)
            ? null
            : property.toString());

    return (value == null)
           ? false
           : value;
  }

  public static void setDisableBindings(boolean disableBindings) {
    ActionHelper.disableBindings = disableBindings;
  }

  private static final class CutCopyAction extends FocusedAction {
    private boolean _isCut;

    /**
     * Constructs a new instance
     *
     * @param isCut {@inheritDoc}
     */
    CutCopyAction(boolean isCut) {
      super(isCut
            ? iConstants.CUT_ACTION_NAME
            : iConstants.COPY_ACTION_NAME);
      _isCut = isCut;
      this.setEnabledOnSelectionOnly(true);
      update();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      int                action    = (_isCut)
                                     ? TransferHandler.MOVE
                                     : TransferHandler.COPY;
      iPlatformComponent c         = getFocusedComponent();
      JComponent         component = c.getView();

      if (component instanceof iTransferSupport) {
        iTransferSupport t = (iTransferSupport) component;

        if (_isCut) {
          t.performCut();
        } else {
          t.performCopy();
        }

        return;
      }

      if (component != null) {
        Clipboard       clipboard = getClipboard();
        TransferHandler th        = component.getTransferHandler();

        if (th != null) {
          th.exportToClipboard(component, clipboard, action);
        }
      }
    }

    @Override
    public void dispose() {}    // not disposable

    @Override
    protected boolean isActionSupported(iWidget w, iPlatformComponent component) {
      if (w == null) {
        if (component instanceof iTransferSupport) {
          iTransferSupport t = (iTransferSupport) component;

          return _isCut
                 ? t.canCopy() && t.canDelete()
                 : t.canCopy();
        }

        return false;
      }

      return _isCut
             ? w.canCut()
             : w.canCopy();
    }
  }


  private static final class DeleteAction extends FocusedAction {

    /**
     * Constructs a new instance
     *
     */
    DeleteAction() {
      super(iConstants.DELETE_ACTION_NAME);
      this.setEnabledOnSelectionOnly(true);
      update();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      iPlatformComponent component = getFocusedComponent();

      if (component != null) {
        if (component instanceof iTransferSupport) {
          ((iTransferSupport) component).deleteSelectedData(false);
        } else {
          iWidget w = aWidget.getWidgetForComponent(component);

          if (w instanceof aWidget) {
            ((aWidget) w).removeSelectedData(false);
          } else if (w instanceof iTransferSupport) {
            ((iTransferSupport) w).deleteSelectedData(false);
          }
        }
      }
    }

    @Override
    public void dispose() {}    // not disposable

    @Override
    protected boolean isActionSupported(iWidget w, iPlatformComponent component) {
      if (component instanceof iTransferSupport) {
        return ((iTransferSupport) component).canDelete();
      }

      return (w == null)
             ? false
             : w.canDelete();
    }
  }


  private static final class DoClickAction extends UIAction {

    /**
     * Constructs a new instance
     */
    public DoClickAction() {
      super(iConstants.DOCLICK_ACTION_NAME);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      Object o = e.getSource();

      if (o instanceof AbstractButton) {
        ((AbstractButton) o).doClick();
      }
    }

    @Override
    public void dispose() {}    // not disposable
  }


  private static final class PasteAction extends FocusedAction {

    /**
     * Constructs a new instance
     */
    PasteAction() {
      super(iConstants.PASTE_ACTION_NAME);
      getClipboard().addFlavorListener(new FlavorHandler());
      update();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      Clipboard          clipboard = getClipboard();
      iPlatformComponent component = getFocusedComponent();
      JComponent         jc        = (component == null)
                                     ? null
                                     : component.getView();

      if (jc instanceof iTransferSupport) {
        ((iTransferSupport) component).performPaste();
      } else {
        TransferHandler th = component.getTransferHandler();

        if (th != null) {
          th.importData(component.getView(), clipboard.getContents(null));
        }
      }
    }

    @Override
    public void dispose() {}    // not disposable

    @Override
    protected void updateEnabledFromTarget() {
      iPlatformComponent component = getFocusedComponent();
      boolean            enable    = (component == null)
                                     ? false
                                     : isActionSupported(null, component);

      setEnabled(enable);
    }

    @Override
    protected boolean isActionSupported(iWidget w, iPlatformComponent component) {
      if ((w != null) &&!w.canPaste()) {
        return false;
      }

      boolean         enable    = false;
      TransferHandler handler   = component.getTransferHandler();
      Clipboard       clipboard = getClipboard();
      JComponent      jc        = component.getView();

      try {
        if (jc instanceof iTransferSupport) {
          enable = ((iTransferSupport) jc).canImport(
            SwingHelper.convertDataFlavorsToTransferFlavors(Arrays.asList(clipboard.getAvailableDataFlavors())));
        } else if ((handler != null) && handler.canImport(jc, clipboard.getAvailableDataFlavors())) {
          enable = true;
        }
      } catch(IllegalStateException e) {
        // Can't get at clipboard. Delay for a second and try again.
        new Thread(new DelayedUpdateRunnable()).start();
      }

      return enable;
    }

    private final class DelayedUpdateRunnable implements Runnable {
      @Override
      public void run() {
        try {
          Thread.sleep(1000);
        } catch(InterruptedException ex) {}

        Platform.invokeLater(new UpdateRunnable());
      }
    }


    private class FlavorHandler implements FlavorListener {
      @Override
      public void flavorsChanged(FlavorEvent e) {
        if (getFocusedComponent() != null) {
          updateEnabledFromTarget();
        }
      }
    }


    private final class UpdateRunnable implements Runnable {
      @Override
      public void run() {
        if (getFocusedComponent() != null) {
          updateEnabledFromTarget();
        }
      }
    }
  }


  private static final class RedoAction extends FocusedAction {

    /**
     * Constructs a new instance
     */
    RedoAction() {
      super(iConstants.REDO_ACTION_NAME);
      update();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      iPlatformComponent component = getActionComponent(e);
      UndoManager        m         = getUndoManager(component);

      if ((m != null) && m.canRedo()) {
        try {
          m.redo();
        } catch(CannotRedoException ex) {
          Toolkit.getDefaultToolkit().beep();
        }
      }
    }

    @Override
    public void dispose() {}    // not disposable

    @Override
    protected void updateEnabledFromTarget() {
      iPlatformComponent c = getFocusedComponent();
      UndoManager        m = getUndoManager(c);

      if (m != null) {
        setEnabled(m.canRedo());
      }
    }

    @Override
    protected void setFocusedComponent(iPlatformComponent component) {
      boolean validTarget = false;

      if ((component != null) && (getUndoManager(component) != null)) {
        super.setFocusedComponentEx(component);
        updateEnabledFromTarget();
        validTarget = true;
      }

      if (!validTarget) {
        super.setFocusedComponentEx(null);
      }
    }
  }


  private static final class SelectAllAction extends FocusedAction {

    /**
     * Constructs a new instance
     */
    SelectAllAction() {
      super(iConstants.SELECTALL_ACTION_NAME);
      update();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      iPlatformComponent component = getActionComponent(e);

      if (component instanceof iTransferSupport) {
        iTransferSupport t = (iTransferSupport) component;

        t.selectAll();

        return;
      }

      iWidget w = aWidget.getWidgetForComponent(component);

      if (w != null) {
        w.selectAll();
      }
    }

    @Override
    public void dispose() {}    // not disposable

    @Override
    protected boolean isActionSupported(iWidget w, iPlatformComponent component) {
      if (w == null) {
        if (component instanceof iTransferSupport) {
          iTransferSupport t = (iTransferSupport) component;

          return t.canSelectAll();
        }

        return false;
      }

      return w.canSelectAll();
    }
  }


  private static final class UndoAction extends FocusedAction {

    /**
     * Constructs a new instance
     */
    UndoAction() {
      super(iConstants.UNDO_ACTION_NAME);
      update();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      iPlatformComponent component = getActionComponent(e);
      UndoManager        m         = getUndoManager(component);

      if ((m != null) && m.canUndo()) {
        try {
          m.undo();
        } catch(CannotUndoException ex) {
          Toolkit.getDefaultToolkit().beep();
        }
      }
    }

    @Override
    public void dispose() {}    // not disposable

    @Override
    protected void updateEnabledFromTarget() {
      iPlatformComponent c = getFocusedComponent();
      UndoManager        m = getUndoManager(c);

      if (m != null) {
        setEnabled(m.canUndo());
      }
    }

    @Override
    protected void setFocusedComponent(iPlatformComponent component) {
      boolean validTarget = false;

      if ((component != null) && (getUndoManager(component) != null)) {
        super.setFocusedComponentEx(component);
        updateEnabledFromTarget();
        validTarget = true;
      }

      if (!validTarget) {
        super.setFocusedComponentEx(null);
      }
    }
  }


  /** action to select the next row */
  public static Action selectNextRow = new AbstractAction("selectNextRow") {
    @Override
    public void actionPerformed(java.awt.event.ActionEvent e) {
      Object  o = (e == null)
                  ? null
                  : e.getSource();
      iWidget w = Platform.getWidgetForComponent(o);

      if (w instanceof aListViewer) {
        ((aListViewer) w).downArrow();
      }

      if (w instanceof aListWidget) {
        ((aListWidget) w).downArrow();
      }
    }
  };

  /** action to select the previous row */
  public static Action selectPreviousRow = new AbstractAction("selectPreviousRow") {
    @Override
    public void actionPerformed(java.awt.event.ActionEvent e) {
      Object  o = (e == null)
                  ? null
                  : e.getSource();
      iWidget w = Platform.getWidgetForComponent(o);

      if (w instanceof aListViewer) {
        ((aListViewer) w).upArrow();
      }

      if (w instanceof aListWidget) {
        ((aListWidget) w).upArrow();
      }
    }
  };
}
