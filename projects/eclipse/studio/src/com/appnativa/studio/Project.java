/*
 * @(#)Project.java
 *
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.studio;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IFileEditorInput;

import com.appnativa.rare.Platform;
import com.appnativa.rare.TemplateHandler;
import com.appnativa.rare.iPlatformAppContext;
import com.appnativa.rare.converters.Conversions;
import com.appnativa.rare.net.ActionLink;
import com.appnativa.rare.net.JavaURLConnection;
import com.appnativa.rare.spot.Application.CManagedScreenSizes;
import com.appnativa.rare.spot.MainWindow;
import com.appnativa.rare.spot.Rectangle;
import com.appnativa.rare.spot.Widget;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.Utils;
import com.appnativa.rare.ui.event.ChangeEvent;
import com.appnativa.rare.ui.event.EventListenerList;
import com.appnativa.rare.ui.event.iChangeListener;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.studio.preferences.PreferenceConstants;
import com.appnativa.studio.properties.PropertiesConstants;
import com.appnativa.util.CharScanner;
import com.appnativa.util.ObjectHolder;

public class Project {
  private static final QualifiedName         PROPETY_PROP_KEY   = new QualifiedName("com.appnativa.studio", "project");
  public com.appnativa.rare.spot.Application application;
  public URL                                 baseURL;
  IProject                                   iProject;
  TemplateHandler                            templateHandler;
  private String                             assetsDirectory    = "assets";
  private String                             resourcesDirectory = "res";
  private EventListenerList                  listenerList       = new EventListenerList();
  private IFile                              applicationFile;
  private UIColor                            canvasColor;
  private ChangeEvent                        changeEvent;
  private boolean                            dirty;
  private UIColor                            gridColor;
  private MainWindow                         mainWindow;
  private ActionLink                         mainWindowActionLink;
  private URL                                mainWindowURL;
  private String                             managedResourcePath;
  private int                                modCount;
  private UIColor                            selectionColor;
  private UIColor                            trackingColor;

  public Project(IProject p) throws Exception {
    iProject = p;

    IResource[] a = findAppFileAndResources(p);

    if (a[0] != null) {
      baseURL = ((IFile) a[0]).getLocationURI().toURL();
      assetsDirectory = ((IFile) a[0]).getParent().getProjectRelativePath().toString();
    } else {
      baseURL = p.getLocationURI().toURL();
    }

    if (a[1] != null) {
      try {
        IFolder f = (IFolder) a[1];
        managedResourcePath = JavaURLConnection.toExternalForm(f.getLocationURI().toURL());

        int n = managedResourcePath.lastIndexOf('/');

        managedResourcePath = managedResourcePath.substring(0, n);

        f = (IFolder) f.getParent();
        resourcesDirectory = f.getProjectRelativePath().toString();
      } catch (MalformedURLException e) {
        e.printStackTrace();
      }
    }

    load((IFile) a[0]);
  }

  public void addChangeListener(iChangeListener l) {
    listenerList.add(iChangeListener.class, l);
  }

  public void adjustBoundsToWindow(iWidget w, Widget cfg) {
    Rectangle r = null;

    if (mainWindow != null) {
      if (!cfg.bounds.width.spot_valueWasSet() && !cfg.bounds.width.spot_hasAttributes()) {
        if (r == null) {
          r = (Rectangle) cfg.bounds.clone();
        }

        r.width.spot_copy(mainWindow.bounds.width);
      }

      if (!cfg.bounds.height.spot_valueWasSet() && !cfg.bounds.height.spot_hasAttributes()) {
        if (r == null) {
          r = (Rectangle) cfg.bounds.clone();
        }

        r.height.spot_copy(mainWindow.bounds.height);
      }

      if (r != null) {
        // PlatformHelper.setPreferredSizeEx(w.getContainerComponent(), r);
      }
    }
  }

  public void clearTemplateHandler() {
    TemplateHandler.setInstance(Platform.getAppContext(), null);
  }

  public void projectChanged() {
    try {
      loadColors();
      modCount++;

      if (!dirty) {
        IFile f = (IFile) findAppFileAndResources(iProject)[0];

        if (f == null) {
          dirty = true;
        } else {
          dirty = ((applicationFile == null) || !applicationFile.equals(f));
          if(!dirty && application!=null) {
            if(application.resourceStringsURL.getValue()!=null && !ActionLink.isInlineURL(application.resourceStringsURL)) {
              dirty=true;
            }
            else if(application.resourceIconsURL.getValue()!=null && !ActionLink.isInlineURL(application.resourceIconsURL)) {
              dirty=true;
            }
          }
        }
      }

      fireChangeEvent();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void reloadApplicationConfig() throws Exception {
    dirty = false;

    if ((applicationFile != null) && (iProject != null)) {
      reloadApplication(applicationFile, iProject);
      fireChangeEvent();
    }
  }

  public void reloadColors() {
    try {
      loadColors();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void removeChangeListener(iChangeListener l) {
    listenerList.remove(iChangeListener.class, l);
  }

  public void resetAfterPreview() {
  }

  public void setupApplicationForRendering(iPlatformAppContext app, com.appnativa.rare.spot.Application a) {
    TemplateHandler.setInstance(app, templateHandler);
    a.contextURL.setValue(JavaURLConnection.toExternalForm(baseURL));
    a.applicationRoot.setValue(".");

    if (app instanceof DesignAppContext) {
      ((DesignAppContext) app).defaultManagedResourcePath = managedResourcePath;
    }
  }

  public void setupDesign(iPlatformAppContext app, com.appnativa.rare.spot.Application a) {
    setupApplicationForRendering(app, a);
  }

  public void setupPreview(iPlatformAppContext app, com.appnativa.rare.spot.Application a) {
    setupApplicationForRendering(app, a);
  }

  public void systemPreferencesChanged() {
  }

  public void setDirty(boolean dirty) {
    this.dirty = dirty;

    if (dirty) {
      fireChangeEvent();
    }
  }

  public void setGridColor(UIColor color) {
    String s = (color == null) ? "" : Conversions.colorToHEXString(color);

    try {
      iProject.setPersistentProperty(PropertiesConstants.GRID_COLOR, s);
    } catch (CoreException e) {
      e.printStackTrace();
    }
  }

  public void setTemplateHandler() {
    TemplateHandler.setInstance(Platform.getAppContext(), templateHandler);
  }

  public String getAssetsDirectory() {
    return assetsDirectory;
  }

  public URL getBaseURL() {
    return baseURL;
  }

  public UIColor getCanvasColor() {
    return canvasColor;
  }

  public com.appnativa.rare.spot.Application getEmptyApplication() {
    if (application == null) {
      return null;
    }

    com.appnativa.rare.spot.Application a = (com.appnativa.rare.spot.Application) application.clone();

    a.managedScreenSizes.spot_clear();

    MainWindow mw = new MainWindow();

    a.setMainWindow(mw);

    return a;
  }

  public UIColor getGridColor() {
    return gridColor;
  }

  public IProject getIProject() {
    return iProject;
  }

  public MainWindow getMainWindow() {
    return mainWindow;
  }

  public ActionLink getMainWindowActionLink() {
    return mainWindowActionLink;
  }

  public int getModCount() {
    return modCount;
  }

  public MainWindow getPreviewMainWindow(boolean clearViewer) {
    if (mainWindow == null) {
      return new MainWindow();
    }

    MainWindow mw = (MainWindow) mainWindow.clone();

    mw.setMenuBar(null);
    mw.setToolbars(null);
    mw.setStatusBar(null);
    mw.bounds.spot_clear();

    if (clearViewer) {
      mw.viewer.spot_clear();
    }

    return mw;
  }
  public static IProject getMainRMLProject(IResource resource) {
    URI uri=resource.getRawLocationURI();
    IFile[] list = uri==null ? null : ResourcesPlugin.getWorkspace().getRoot().findFilesForLocationURI(uri);
    if (list != null && list.length>0) {
      for(IFile f:list) {
        if(!f.isLinked()) {
          resource=f;
          break;
        }
      }
    }
    return resource.getProject();
  }
  public static Project getProject(IResource resource, boolean create) {
    IProject p = (resource == null) ? null : getMainRMLProject(resource);

    if ((p == null) || !p.exists()) {
      return null;
    }

    Project pp = null;

    try {
      pp = (Project) p.getSessionProperty(PROPETY_PROP_KEY);

      if ((pp == null) && create) {
        pp = new Project(p);
        p.setSessionProperty(PROPETY_PROP_KEY, pp);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    return pp;
  }

  public String getResourcesDirectory() {
    return resourcesDirectory;
  }

  public UIColor getSelectionColor() {
    return selectionColor;
  }

  public TemplateHandler getTemplateHandler() {
    return templateHandler;
  }

  public UIColor getTrackingColor() {
    return trackingColor;
  }

  public IFolder getURLFolder(String name) {
    IFolder folder = null;

    do {
      if (name == null) {
        break;
      }

      name = name.trim();

      if (name.length() == 0) {
        break;
      }

      if (name.startsWith("resources:")) {
        folder = iProject.getFolder(resourcesDirectory);

        if (!folder.exists()) {
          folder = null;
        }

        break;
      }

      folder = iProject.getFolder(assetsDirectory);

      if (!folder.exists()) {
        folder = null;

        break;
      }

      List<String> list = CharScanner.getTokens(name, '/', false);
      int len = list.size();

      if ((len > 1) && (name.indexOf('.') != -1)) {
        list.remove(len - 1);
        len--;
      }

      for (int i = 0; i < len; i++) {
        IFolder f = folder.getFolder(list.get(i));

        if ((f == null) || f.exists()) {
          break;
        }

        folder = f;
      }
    } while (false);

    return folder;
  }

  public boolean isDirty() {
    return dirty;
  }

  public boolean isReloadingResource(IResource resource) {
    if (application == null) {
      return false;
    }
    String s = resource.getName();
    String url = application.resourceStringsURL.getValue();
    if (url != null && url.endsWith(s)) {
      return true;
    }
    url = application.resourceIconsURL.getValue();
    if (url != null && url.endsWith(s)) {
      return true;
    }
    return false;
  }

  public boolean isReloadRequired(IEditorInput editorInput) {
    if ((application != null) && (editorInput instanceof IFileEditorInput)) {
      IFile ef = ((IFileEditorInput) editorInput).getFile();

      if (ef.equals(applicationFile)) {
        return true;
      }

      if (application.managedScreenSizes.intValue() != CManagedScreenSizes.none) {
        IFile f = iProject.getFile("assets/large/mainwindow.rml");

        if (ef.equals(f)) {
          return true;
        }
      }

      String s = mainWindow.templateURL.getValue();

      if (s != null) {
        try {
          URL u;

          if (mainWindowURL != null) {
            u = new URL(mainWindowURL, s);
          } else {
            u = new URL(baseURL, s);
          }

          s = FileLocator.toFileURL(u).getFile();

          if (s.equals(u.getFile())) {
            return true;
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }

    return false;
  }

  protected void fireChangeEvent() {
    if (changeEvent == null) {
      changeEvent = new ChangeEvent(this);
    }

    Utils.fireChangeEvent(listenerList, changeEvent);
  }

  protected void loadColors() throws Exception {
    IProject p = iProject;
    IPreferenceStore ps = Activator.getDefault().getPreferenceStore();
    String s = p.getPersistentProperty(PropertiesConstants.GRID_COLOR);

    s = (s == null) ? "" : s.trim();

    if (s.length() == 0) {
      s = ps.getString(PreferenceConstants.GRID_COLOR);
    }

    s = (s == null) ? "" : s.trim();

    if (s.length() > 0) {
      gridColor = Conversions.colorFromRGBString(s);
    }

    s = p.getPersistentProperty(PropertiesConstants.CANVAS_COLOR);
    s = (s == null) ? "" : s.trim();

    if (s.length() == 0) {
      s = ps.getString(PreferenceConstants.CANVAS_COLOR);
    }

    s = (s == null) ? "" : s.trim();

    if (s.length() > 0) {
      canvasColor = Conversions.colorFromRGBString(s);
    }

    s = p.getPersistentProperty(PropertiesConstants.TRACKING_COLOR);
    s = (s == null) ? "" : s.trim();

    if (s.length() == 0) {
      s = ps.getString(PreferenceConstants.TRACKING_COLOR);
    }

    s = (s == null) ? "" : s.trim();

    if (s.length() > 0) {
      trackingColor = Conversions.colorFromRGBString(s);
    }

    s = p.getPersistentProperty(PropertiesConstants.SELECTION_COLOR);
    s = (s == null) ? "" : s.trim();

    if (s.length() == 0) {
      s = ps.getString(PreferenceConstants.SELECTION_COLOR);
    }

    s = (s == null) ? "" : s.trim();

    if (s.length() > 0) {
      selectionColor = Conversions.colorFromRGBString(s);
    }
  }

  private IResource[] findAppFileAndResources(IProject p) {
    final ObjectHolder h = new ObjectHolder(null);
    String s = null;

    try {
      s = p.getPersistentProperty(PropertiesConstants.APP_FILE_PROP_KEY);

      if (s != null) {
        s = s.trim();
        if (s.length() == 0) {
          s = null;
        }
      }

    } catch (Exception ignore) {
    }

    final String name = s;

    try {
      p.accept(new IResourceVisitor() {
        @Override
        public boolean visit(IResource resource) throws CoreException {
          if (resource instanceof IFolder) {
            IFolder p = (IFolder) resource;

            if (h.source == null) {
              IFile f;

              if (name != null) {
                f = p.getFile(name);
              } else {
                f = p.getFile("application.rml");

                if (!f.exists()) {
                  f = p.getFile("application.sdf");
                }
              }

              if (f.exists()) {
                h.source = f;

                return false;
              }
            }

            if (h.value == null) {
              IFolder f = p.getFolder("drawable-mdpi");

              if (!f.exists()) {
                f = p.getFolder("drawable");
              }

              if (f.exists()) {
                h.value = f;
              }
            }

            return (h.source == null) || (h.value == null);
          }

          return true;
        }
      }, IResource.DEPTH_INFINITE, false);
    } catch (CoreException e) {
      e.printStackTrace();
    }

    return new IResource[] { (IResource) h.source, (IResource) h.value };
  }

  private void load(IFile f) throws Exception {
    canvasColor = null;

    IProject p = iProject;

    if ((f != null) && ((applicationFile == null) || !applicationFile.equals(f))) {
      if (f.exists()) {
        baseURL = f.getLocationURI().toURL();
        iPlatformAppContext ctx = Platform.getAppContext();
        ctx.setContextURL(baseURL);
        TemplateHandler.setInstance(Platform.getAppContext(), null);
        application = (com.appnativa.rare.spot.Application) Utilities.loadObject(Platform.getContextRootViewer(), f);
        applicationFile = f;

        if (application.managedScreenSizes.intValue() != CManagedScreenSizes.none) {
          mainWindow = application.getMainWindow();
          f = p.getFile("assets/large/mainwindow.rml");

          if (!f.exists()) {
            f = p.getFile("assets/small/mainwindow.rml");
          }

          if (f.exists()) {
            mainWindow = (MainWindow) Utilities.loadObject(Platform.getContextRootViewer(), f);
            mainWindowURL = f.getLocationURI().toURL();
            this.mainWindowActionLink = new ActionLink(mainWindowURL);
          }
        }

        if (mainWindow == null) {
          mainWindow = application.getMainWindow();
        }

        if (mainWindow == null) {
          mainWindow = new MainWindow();
        }

        templateHandler = TemplateHandler.getInstance(Platform.getAppContext());
        TemplateHandler.setInstance(Platform.getAppContext(), null);
        modCount++;
      }
    }

    loadColors();
  }

  private void reloadApplication(IFile f, IProject p) throws Exception {
    TemplateHandler.setInstance(Platform.getAppContext(), null);
    com.appnativa.rare.spot.Application a = (com.appnativa.rare.spot.Application) Utilities.loadObject(
        Platform.getContextRootViewer(), f);
    MainWindow mw = null;
    
    if (a.managedScreenSizes.intValue() != CManagedScreenSizes.none) {
      mainWindow = a.getMainWindow();
      f = p.getFile("assets/large/mainwindow.rml");

      if (f.exists()) {
        mw = (MainWindow) Utilities.loadObject(Platform.getContextRootViewer(), f);
      }
    }

    if (mw == null) {
      mw = a.getMainWindow();
    }

    if (mw == null) {
      mw = new MainWindow();
    }

    mainWindow = mw;
    application = a;
    templateHandler = TemplateHandler.getInstance(Platform.getAppContext());
    TemplateHandler.setInstance(Platform.getAppContext(), null);
    modCount++;
  }

}
