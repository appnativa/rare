/*
 * @(#)NewProject.java
 *
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.studio.wizards;

import com.appnativa.studio.Studio;
import com.appnativa.studio.Utilities;
import com.appnativa.studio.builder.ProjectNature;
import com.appnativa.studio.preferences.MainPreferencePage;

import com.appnativa.rare.exception.ApplicationException;
import com.appnativa.rare.spot.Application;
import com.appnativa.rare.spot.MainWindow;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.util.SNumber;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.lang.reflect.InvocationTargetException;

import java.net.URI;

import java.util.Map;

public class NewProject extends Wizard implements INewWizard {
  ProjectApplicationPage   applicationPage;
  boolean                  autoManageScreens;
  Throwable                exception;
  ProjectLinkPage          linksPage;
  ProjectMainWindowPage    mainWindowPage;
  boolean                  supportMediumScreens;
  boolean                  supportMultipleScreens;
  private OneTimeSetupPage setupPane;

  public NewProject() {
    setWindowTitle("RARE - New Project Wizard");
    setNeedsProgressMonitor(true);
  }

  @Override
  public void addPages() {
    super.addPages();
    applicationPage = new ProjectApplicationPage();
    mainWindowPage  = new ProjectMainWindowPage();
    linksPage       = new ProjectLinkPage();

    String s = MainPreferencePage.getAppNativaSDKDirectory();

    if (s == null) {
      setupPane = new OneTimeSetupPage();
      addPage(setupPane);
    }

    addPage(applicationPage);
    addPage(mainWindowPage);
    addPage(linksPage);
  }

  /**
   * For this marvelous project we need to:
   * - create the default Eclipse project
   * - add the custom project nature
   * - create the folder structure
   *
   * @param projectName
   * @param location
   * @param natureId
   * @return
   */
  public IProject createProject(String projectName, URI location) throws CoreException {
    Assert.isNotNull(projectName);
    Assert.isTrue(projectName.trim().length() > 0);

    IProject project = createBaseProject(projectName, location);

    addNature(project);

    String[] paths;

    if (supportMultipleScreens) {
      if (supportMediumScreens) {
        paths = new String[] {
          "assets", "res", "assets/scripts", "assets/small", "assets/large", "assets/medium"
        };
      } else {
        paths = new String[] { "assets", "res", "assets/scripts", "assets/small", "assets/large" };
      }
    } else {
      paths = new String[] { "assets", "res", "assets/scripts" };
    }

    addToProjectStructure(project, paths);

    return project;
  }

  @Override
  public void init(IWorkbench workbench, IStructuredSelection selection) {}

  @Override
  public boolean performFinish() {
    try {
      if ((applicationPage == null) ||!applicationPage.isPageComplete()) {
        return true;
      }

      String name = applicationPage.getApplicationName();

      if ((name == null) || (name.trim().length() == 0)) {
        name = "MyApplication";
      }

      String title = applicationPage.getApplicationTitle();

      if ((title == null) || (title.trim().length() == 0)) {
        title = "My Application";
      }

      UIColor     fg      = applicationPage.getAppForegroundColor();
      UIColor     bg      = applicationPage.getAppBackgroundColor();
      UIColor     lc      = applicationPage.getLineColor();
      UIColor     dfg     = applicationPage.getAppDisabledForegroundColor();
      String      sdkPath = MainPreferencePage.getAppNativaSDKDirectory();
      Application app     = ProjectGenerator.createApplicationObject(sdkPath, applicationPage.getPackageName(), fg, bg,
                              lc, dfg);
      if(applicationPage.isAutoAjustFont()) {
        app.autoAdjustFontSize.setValue(true);
      }
      String rename = applicationPage.getPackageName();
      int    n      = rename.lastIndexOf('.');

      if (n != -1) {
        rename = rename.substring(n + 1);
      }

      final MainWindow[] windows = new MainWindow[3];
      MainWindow         w;

      app.name.setValue(name);

      if (applicationPage.isSupportMultipleScreens()) {
        String s = mainWindowPage.smallSupport.getScreenPointSize().trim();

        n = SNumber.intValue(s);

        if (n > 0) {
          app.managedScreenSizes.spot_setAttribute("smallScreenPointSize", s);
        }

        w          = mainWindowPage.getMainWindow(mainWindowPage.smallSupport, rename, title);
        windows[0] = w;

        if (applicationPage.isSupportMediumSizeScreens()) {
          app.managedScreenSizes.setValue(Application.CManagedScreenSizes.small_medium_large);
          s = mainWindowPage.mediumSupport.getScreenPointSize().trim();
          n = SNumber.intValue(s);

          if (n > 0) {
            app.managedScreenSizes.spot_setAttribute("mediumScreenPointSize", s);
          }

          w = mainWindowPage.getMainWindow(mainWindowPage.mediumSupport, rename, title);
          w.title.setValue(title);
          windows[1] = w;
        } else {
          app.managedScreenSizes.setValue(Application.CManagedScreenSizes.small_large);
        }

        w          = mainWindowPage.getMainWindow(mainWindowPage.screenSupport, rename, title);
        windows[2] = w;

        if (applicationPage.isAutoFallback()) {
          app.managedScreenSizes.spot_setAttribute("autoFallback", "true");
        }
      } else {
        w = mainWindowPage.getMainWindow(mainWindowPage.screenSupport, rename, title);
        app.setMainWindow(w);
      }

      if (applicationPage.wantsDesktopSupport()) {
        app.desktopIconDensity.setValue(mainWindowPage.screenSupport.getDesktopIconDensity());

        String s = w.icon.getValue();

        s += ",resource:" + rename + "_icon24";
        s += ",resource:" + rename + "_icon32";
        s += ",resource:" + rename + "_icon48";
        w.icon.setValue(s);
      }

      String                 projectName = applicationPage.getProjectName();
      URI                    location    = applicationPage.useDefaults()
              ? null
              : applicationPage.getLocationURI();
      String                 packageName = applicationPage.getPackageName();
      final String           android     = applicationPage.wantsAndroidSupport()
              ? linksPage.getAdroidProjectName(projectName)
              : null;
      final String           swing       = applicationPage.wantsDesktopSupport()
              ? linksPage.getSwingProjectName(projectName)
              : null;
      final String           ios         = applicationPage.wantsIOSSupport()
              ? linksPage.getIOSProjectPath(projectName)
              : null;
      final ProjectGenerator pg          = new ProjectGenerator(projectName, packageName, location, app, windows);
      IRunnableWithProgress  op          = new IRunnableWithProgress() {
        public void run(IProgressMonitor monitor) throws InvocationTargetException {
          try {
            pg.generate(getShell(), monitor, swing, android, ios);
          } catch(Exception e) {
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

        MessageDialog.openError(getShell(), "Error", ApplicationException.getMessageEx(realException));

        return false;
      }
    } catch(Exception e) {
      Utilities.errorDialogWithStackTrace(Studio.getResourceAsString("Studio.text.newProjectError"), e);
    }

    return true;
  }

  public String getApplicationName() {
    return applicationPage.getApplicationName();
  }

  public String getPackageName() {
    return applicationPage.getPackageName();
  }

  public String getProjectName() {
    return applicationPage.getProjectName();
  }

  void doFinish(String projectName, URI location, Map peefs, Application app, IProgressMonitor monitor)
          throws CoreException {
    // create a sample file
    monitor.beginTask("Creating project", 3);

    IProject    project = createProject(projectName, location);
    final IFile file    = project.getFile(new Path("assets/application.rml"));

    try {
      InputStream stream = new ByteArrayInputStream(app.toSDF().getBytes());

      if (file.exists()) {
        file.setContents(stream, true, true, monitor);
      } else {
        file.create(stream, true, monitor);
      }

      stream.close();
    } catch(IOException e) {}

    monitor.worked(2);
    monitor.setTaskName("Opening application file for editing...");
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

  private static void addNature(IProject project) throws CoreException {
    if (!project.hasNature(ProjectNature.NATURE_ID)) {
      IProjectDescription description = project.getDescription();
      String[]            prevNatures = description.getNatureIds();
      String[]            newNatures  = new String[prevNatures.length + 1];

      System.arraycopy(prevNatures, 0, newNatures, 0, prevNatures.length);
      newNatures[prevNatures.length] = ProjectNature.NATURE_ID;
      description.setNatureIds(newNatures);

      IProgressMonitor monitor = null;

      project.setDescription(description, monitor);
    }
  }

  /**
   * Create a folder structure with a parent root, overlay, and a few child
   * folders.
   *
   * @param newProject
   * @param paths
   * @throws CoreException
   */
  private static void addToProjectStructure(IProject newProject, String[] paths) throws CoreException {
    for (String path : paths) {
      IFolder etcFolders = newProject.getFolder(path);

      createFolder(etcFolders);
    }
  }

  /**
   * Just do the basics: create a basic project.
   *
   * @param location
   * @param projectName
   * @throws CoreException
   */
  private static IProject createBaseProject(String projectName, URI location) throws CoreException {
    // it is acceptable to use the ResourcesPlugin class
    IProject newProject = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);

    if (!newProject.exists()) {
      URI                 projectLocation = location;
      IProjectDescription desc            = newProject.getWorkspace().newProjectDescription(projectName);

      if ((location != null) && ResourcesPlugin.getWorkspace().getRoot().getLocationURI().equals(location)) {
        projectLocation = null;
      }

      desc.setLocationURI(projectLocation);
      newProject.create(desc, null);

      if (!newProject.isOpen()) {
        newProject.open(null);
      }
    }

    return newProject;
  }

  private static void createFolder(IFolder folder) throws CoreException {
    IContainer parent = folder.getParent();

    if (parent instanceof IFolder) {
      createFolder((IFolder) parent);
    }

    if (!folder.exists()) {
      folder.create(false, true, null);
    }
  }
}
