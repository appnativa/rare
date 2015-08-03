/*
 * @(#)NewLayout.java
 * 
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.studio.wizards;

import com.appnativa.studio.Studio;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWizard;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.lang.reflect.InvocationTargetException;

/**
 * This is a sample new wizard. Its role is to create a new file
 * resource in the provided container. If the container resource
 * (a folder or a project) is selected in the workspace
 * when the wizard is opened, it will accept it as the target
 * container. The wizard creates one file with the extension
 * "sdf". If a sample multi-page editor (also available
 * as a template) is registered for the same extension, it will
 * be able to open it.
 */
public class NewLayout extends Wizard implements INewWizard {
  private String        contents;
  private NewLayoutPage page;
  private ISelection    selection;

  /**
   * Constructor for NewLayout.
   */
  public NewLayout() {
    super();
  }

  /**
   * Adding the page to the wizard.
   */
  public void addPages() {
    page = new NewLayoutPage(selection);
    addPage(page);
  }

  /**
   * We will accept the selection in the workbench to see if
   * we can initialize from it.
   * @see IWorkbenchWizard#init(IWorkbench, IStructuredSelection)
   */
  public void init(IWorkbench workbench, IStructuredSelection selection) {
    this.selection = selection;
  }

  /**
   * This method is called when 'Finish' button is pressed in
   * the wizard. We will create an operation and run it
   * using wizard as execution context.
   */
  public boolean performFinish() {
    final String containerName = page.getContainerName();
    final String fileName      = page.getFileName();
    String       id            = page.getWidgetType();

    if (id != null) {
      Object o = Studio.getAttribute(id);

      if (o != null) {
        contents = o.toString();
      }
    }

    if (contents == null) {
      contents = "";
    }

    IRunnableWithProgress op = new IRunnableWithProgress() {
      public void run(IProgressMonitor monitor) throws InvocationTargetException {
        try {
          doFinish(containerName, fileName, monitor);
        } catch(CoreException e) {
          throw new InvocationTargetException(e);
        } finally {
          monitor.done();
        }
      }
    };

    try {
      getContainer().run(true, false, op);
    } catch(InterruptedException e) {
      return false;
    } catch(InvocationTargetException e) {
      e.printStackTrace();

      Throwable realException = e.getTargetException();

      MessageDialog.openError(getShell(), "Error", realException.getMessage());

      return false;
    }

    return true;
  }

  /**
   * The worker method. It will find the container, create the
   * file if missing or just replace its contents, and open
   * the editor on the newly created file.
   */
  private void doFinish(String containerName, String fileName, IProgressMonitor monitor) throws CoreException {
    // create a sample file
    monitor.beginTask("Creating " + fileName, 2);

    IWorkspaceRoot root     = ResourcesPlugin.getWorkspace().getRoot();
    IResource      resource = root.findMember(new Path(containerName));

    if (!resource.exists() ||!(resource instanceof IContainer)) {
      throwCoreException("Folder \"" + containerName + "\" does not exist.");
    }

    IContainer  container = (IContainer) resource;
    final IFile file      = container.getFile(new Path(fileName));

    try {
      InputStream stream = openContentStream();

      if (file.exists()) {
        file.setContents(stream, true, true, monitor);
      } else {
        file.create(stream, true, monitor);
      }

      stream.close();
    } catch(IOException e) {}

    monitor.worked(1);
    monitor.setTaskName("Opening file for editing...");
    getShell().getDisplay().asyncExec(new Runnable() {
      public void run() {
        IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();

        try {
          IDE.openEditor(page, file, true);
        } catch(PartInitException e) {}
      }
    });
    monitor.worked(1);
  }

  /**
   * We will initialize file contents with a sample text.
   */
  private InputStream openContentStream() {
    return new ByteArrayInputStream(contents.getBytes());
  }

  private void throwCoreException(String message) throws CoreException {
    IStatus status = new Status(IStatus.ERROR, "com.appnativa.studio", IStatus.OK, message, null);

    throw new CoreException(status);
  }
}
