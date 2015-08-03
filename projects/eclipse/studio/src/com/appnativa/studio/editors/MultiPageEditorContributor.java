/*
 * @(#)MultiPageEditorContributor.java
 * 
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.studio.editors;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.ide.IDEActionFactory;
import org.eclipse.ui.part.MultiPageEditorActionBarContributor;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.ui.texteditor.ITextEditorActionConstants;

import com.appnativa.studio.Studio;

/**
 * Manages the installation/deinstallation of global actions for multi-page editors.
 * Responsible for the redirection of global actions to the active editor.
 * Multi-page contributor replaces the contributors for the individual editors in the multi-page editor.
 */
public class MultiPageEditorContributor extends MultiPageEditorActionBarContributor {
  private IEditorPart activeEditorPart;
  private Action      newProject;
  private Action      newLayout;
  private Action      runSwing;
  private Action      runAndroid;
  private Action      runIOS;

  /**
   * Creates a multi-page contributor.
   */
  public MultiPageEditorContributor() {
    super();
    createActions();
  }

  public void contributeToMenu(IMenuManager manager) {
    IMenuManager menu = new MenuManager("appNativa");

    manager.prependToGroup(IWorkbenchActionConstants.MB_ADDITIONS, menu);
    menu.add(newProject);
    menu.add(newLayout);
  }

  public void contributeToToolBar(IToolBarManager manager) {
    manager.add(new Separator());
    manager.add(newProject);
    manager.add(newLayout);
  }

  public void setActivePage(IEditorPart part) {
    if (activeEditorPart == part) {
      return;
    }

    activeEditorPart = part;

    IActionBars actionBars = getActionBars();

    if (actionBars != null) {
      ITextEditor editor = (part instanceof ITextEditor)
                           ? (ITextEditor) part
                           : null;

      actionBars.setGlobalActionHandler(ActionFactory.DELETE.getId(),
                                        getAction(editor, ITextEditorActionConstants.DELETE));
      actionBars.setGlobalActionHandler(ActionFactory.UNDO.getId(), getAction(editor, ITextEditorActionConstants.UNDO));
      actionBars.setGlobalActionHandler(ActionFactory.REDO.getId(), getAction(editor, ITextEditorActionConstants.REDO));
      actionBars.setGlobalActionHandler(ActionFactory.CUT.getId(), getAction(editor, ITextEditorActionConstants.CUT));
      actionBars.setGlobalActionHandler(ActionFactory.COPY.getId(), getAction(editor, ITextEditorActionConstants.COPY));
      actionBars.setGlobalActionHandler(ActionFactory.PASTE.getId(),
                                        getAction(editor, ITextEditorActionConstants.PASTE));
      actionBars.setGlobalActionHandler(ActionFactory.SELECT_ALL.getId(),
                                        getAction(editor, ITextEditorActionConstants.SELECT_ALL));
      actionBars.setGlobalActionHandler(ActionFactory.FIND.getId(), getAction(editor, ITextEditorActionConstants.FIND));
      actionBars.setGlobalActionHandler(IDEActionFactory.BOOKMARK.getId(),
                                        getAction(editor, IDEActionFactory.BOOKMARK.getId()));
      actionBars.updateActionBars();
    }
  }

  /**
   * Returns the action registed with the given text editor.
   * @return IAction or null if editor is null.
   */
  protected IAction getAction(ITextEditor editor, String actionID) {
    return ((editor == null)
            ? null
            : editor.getAction(actionID));
  }
  /*
   *  (non-JavaDoc)
   * Method declared in AbstractMultiPageEditorActionBarContributor.
   */

  private void createActions() {
    newProject = new Action() {
      public void run() {
        Studio.openWizard("com.appnativa.studio.wizards.NewProject", Display.getDefault().getActiveShell());
      }
    };
    newProject.setText(Studio.getResourceAsString("Studio.text.newProject"));
    newProject.setToolTipText(Studio.getResourceAsString("Studio.text.newProject"));
    newProject.setImageDescriptor(ImageDescriptor.createFromImage(Studio.getResourceAsIcon("Studio.icon.projects.project")));
    newLayout = new Action() {
      public void run() {
        Studio.openWizard("com.appnativa.studio.wizards.NewLayout", Display.getDefault().getActiveShell());
      }
    };
    newLayout.setText(Studio.getResourceAsString("Studio.text.newLayout"));
    newLayout.setToolTipText(Studio.getResourceAsString("Studio.text.newLayout"));
    newLayout.setImageDescriptor(ImageDescriptor.createFromImage(Studio.getResourceAsIcon("Studio.icon.projects.rml")));
  }
}
