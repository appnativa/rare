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

import java.util.ArrayList;

import com.appnativa.rare.iConstants;
import com.appnativa.rare.ui.ActionComponent;
import com.appnativa.rare.ui.FocusedAction;
import com.appnativa.rare.ui.UIAction;
import com.appnativa.rare.ui.aFocusedAction;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.dnd.iTransferSupport;
import com.appnativa.rare.ui.event.ActionEvent;
import com.appnativa.rare.widget.aWidget;
import com.appnativa.rare.widget.iWidget;

public class ActionHelper {
  private static final String                    CUT_CLIENT_PROPERTY        = iConstants.CUT_ACTION_NAME;
  private static final String                    COPY_CLIENT_PROPERTY       = iConstants.COPY_ACTION_NAME;
  private static final String                    DELETE_CLIENT_PROPERTY     = iConstants.DELETE_ACTION_NAME;
  private static final String                    PASTE_CLIENT_PROPERTY      = iConstants.PASTE_ACTION_NAME;
  private static final String                    SELECT_ALL_CLIENT_PROPERTY = iConstants.SELECTALL_ACTION_NAME;
  private static final ArrayList<aFocusedAction> focusedActions             = new ArrayList<aFocusedAction>();
  private static final aFocusedAction            COPY_INSTANCE;
  private static final aFocusedAction            CUT_INSTANCE;
  private static final aFocusedAction            DELETE_INSTANCE;
  private static final UIAction                  DO_CLICK_INSTANCE;
  private static final aFocusedAction            PASTE_INSTANCE;
  private static final aFocusedAction            REDO_INSTANCE;
  private static final aFocusedAction            SELECT_ALL_INSTANCE;
  private static final aFocusedAction            UNDO_INSTANCE;

  static {
    CUT_INSTANCE        = new CutCopyAction(true);
    COPY_INSTANCE       = new CutCopyAction(false);
    PASTE_INSTANCE      = new PasteAction();
    SELECT_ALL_INSTANCE = new SelectAllAction();
    REDO_INSTANCE       = new RedoAction();
    UNDO_INSTANCE       = new UndoAction();
    DELETE_INSTANCE     = new DeleteAction();
    focusedActions.add(CUT_INSTANCE);
    focusedActions.add(COPY_INSTANCE);
    focusedActions.add(PASTE_INSTANCE);
    focusedActions.add(SELECT_ALL_INSTANCE);
    focusedActions.add(REDO_INSTANCE);
    focusedActions.add(UNDO_INSTANCE);
    DO_CLICK_INSTANCE = new DoClickAction();
  }

  public ActionHelper() {
    // TODO Auto-generated constructor stub
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

  private static boolean getBooleanClientProperty(iPlatformComponent c, String property) {
    Boolean value = (Boolean) c.getClientProperty(property);

    return (value == null)
           ? false
           : value;
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

    public void actionPerformed(ActionEvent e) {}

    public void dispose() {}    // not disposable

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

    public void actionPerformed(ActionEvent e) {
      iPlatformComponent component = getActionComponent(e);

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

    public void dispose() {}    // not disposable

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

    public void actionPerformed(ActionEvent e) {
      Object o = e.getSource();

      if (o instanceof ActionComponent) {
        ((ActionComponent) o).doClick();
      }
    }

    public void dispose() {}    // not disposable
  }


  private static final class PasteAction extends FocusedAction {

    /**
     * Constructs a new instance
     */
    PasteAction() {
      super(iConstants.PASTE_ACTION_NAME);
      update();
    }

    public void actionPerformed(ActionEvent e) {
      iPlatformComponent component = getActionComponent(e);

      if (component instanceof iTransferSupport) {
        ((iTransferSupport) component).performPaste();
      }
    }

    public void dispose() {}    // not disposable

    protected void updateEnabledFromTarget() {
      iPlatformComponent component = getFocusedComponent();
      boolean            enable    = (component == null)
                                     ? false
                                     : isActionSupported(null, component);

      setEnabled(enable);
    }

    protected boolean isActionSupported(iWidget w, iPlatformComponent component) {
      if ((w != null) &&!w.canPaste()) {
        return false;
      }

      boolean enable = false;

      if (component instanceof iTransferSupport) {
        //enable = ((iTransferSupport) component).canImport(UIClipboard.getAvailableFlavors());
      }

      return enable;
    }
//    private final class UpdateRunnable implements Runnable {
//      public void run() {
//        if (getFocusedComponent() != null) {
//          updateEnabledFromTarget();
//        }
//      }
//    }
  }


  private static final class RedoAction extends FocusedAction {

    /**
     * Constructs a new instance
     */
    RedoAction() {
      super(iConstants.REDO_ACTION_NAME);
      update();
    }

    public void actionPerformed(ActionEvent e) {}

    public void dispose() {}    // not disposable

    protected void updateEnabledFromTarget() {}

    protected void setFocusedComponent(iPlatformComponent component) {}
  }


  private static final class SelectAllAction extends FocusedAction {

    /**
     * Constructs a new instance
     */
    SelectAllAction() {
      super(iConstants.SELECTALL_ACTION_NAME);
      update();
    }

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

    public void dispose() {}    // not disposable

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

    public void actionPerformed(ActionEvent e) {}

    public void dispose() {}    // not disposable

    protected void updateEnabledFromTarget() {}

    protected void setFocusedComponent(iPlatformComponent component) {}
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
   * Specifies whether the component can perform a paste operation.
   *
   * @param component the Component to set the enabled state for
   * @param enable true if component supports paste
   */
  public static void setPasteEnabled(iPlatformComponent component, boolean enable) {
    component.putClientProperty(PASTE_CLIENT_PROPERTY, enable);
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
   * Returns an action to perform a paste operation.
   *
   * @return the paste action
   */
  public static aFocusedAction getPasteAction() {
    return PASTE_INSTANCE;
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

  public static void registerCutCopyPasteBindings(iPlatformComponent comp) {
    // TODO Auto-generated method stub
  }
}
