/*
 * @(#)ResourceSelectionDialogEx.java
 *
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.studio.dialogs;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.dialogs.ResourceSelectionDialog;

import com.appnativa.rare.iConstants;
import com.appnativa.studio.Project;
import com.appnativa.util.ObjectHolder;

public class SWTResourceSelectionDialogEx extends ResourceSelectionDialog {
  IProject       project;
  String         selectedPath[];
  Tree           tree;
  private String assetsPrefix    = "assets/";
  private String documentPath    = null;
  private String resourcesPrefix = "res/drawable";

  public SWTResourceSelectionDialogEx(Shell parentShell, IAdaptable rootElement, String message) {
    super(parentShell, rootElement, message);
  }

  @Override
  public void create() {
    super.create();

    if ((tree != null) && (selectedPath != null) && (selectedPath.length > 0)) {
      TreeItem[] a = tree.getItems();

      if (a != null) {
        try {
          expandPath(a, 0, 0);
        } catch(Exception ignore) {}
      }
    }
  }

  public void setAssetsPrefix(String assetsPrefix) {
    this.assetsPrefix = assetsPrefix;
  }

  public void setDirectory(IFolder folder) {
    if (folder != null) {
      setInitialSelections(new Object[] { folder });
    }
  }

  @Override
  public void setInitialSelections(Object[] selectedElements) {
    super.setInitialSelections(selectedElements);

    if ((selectedElements != null) && (selectedElements.length > 0)) {
      selectedPath = ((IResource) selectedElements[0]).getFullPath().segments();
    } else {
      selectedPath = null;
    }
  }

  public void setProject(Project project) {
    selectedPath = null;

    if (project != null) {
      this.project = project.getIProject();

      String s = project.getAssetsDirectory();

      if (s != null) {
        if (!s.endsWith("/")) {
          s = s + "/";
        }

        assetsPrefix = s;
      }

      s = project.getResourcesDirectory();

      if (s != null) {
        if (!s.endsWith("/")) {
          s = s + "/";
        }

        resourcesPrefix = s;
      }
    } else {
      this.project = null;
    }
  }

  public void setResourcesPrefix(String resourcesPrefix) {
    this.resourcesPrefix = resourcesPrefix;
  }

  public void setSelectedIconResource(String name) {
    if (project != null) {
      if (name.startsWith(iConstants.RESOURCE_PREFIX)) {
        final String       res = name.substring(iConstants.RESOURCE_PREFIX_LENGTH) + ".";
        IFolder            f   = project.getFolder(resourcesPrefix);
        final ObjectHolder h   = new ObjectHolder(null);

        if (f.exists()) {
          try {
            f.accept(new IResourceVisitor() {
              @Override
              public boolean visit(IResource resource) throws CoreException {
                if ((h.value == null) && resource.getName().startsWith(res)) {
                  String s = resource.toString();

                  if ((s.indexOf("/drawable/") == -1) && (s.indexOf("/drawable-mdpi/") == -1)
                      && (s.indexOf("/drawable-xhdpi/") == -1)) {
                    h.source = resource;
                  } else {
                    h.value = resource;
                  }
                }

                return h.value == null;
              }
            });
          } catch(CoreException ignore) {}

          if (h.value != null) {
            setInitialSelections(new Object[] { h.value });
          } else if (h.source != null) {
            setInitialSelections(new Object[] { h.source });
          }
        }
      } else {
        setSelectedResource(name);
      }
    }
  }

  public void setSelectedResource(String name) {
    if ((project != null) && (assetsPrefix != null)) {
      ;
      IResource f = project.getFile(assetsPrefix + name);

      while(!f.exists() && (f.getParent() != null) && (f.getParent() != project)) {
        f = f.getParent();
      }

      if (f.exists()) {
        setInitialSelections(new Object[] { f });
      }
    }
  }

  public String getSelectedResource() {
    Object[] o    = getResult();
    IFile    file = null;

    if ((o != null) && (o.length == 1) && (o[0] instanceof IFile)) {
      file = (IFile) o[0];
    }

    if (file != null) {
      IPath path=file.getProjectRelativePath();
      String s = path==null ? file.toString() : path.toString();
      int    n = s.indexOf(assetsPrefix);

      if (n ==0) {
        if(documentPath!=null && s.startsWith(documentPath)) {
          s = s.substring(documentPath.length());
        }
        else {
          s = s.substring(assetsPrefix.length()-1);
        }
      } else {
        n = s.indexOf(resourcesPrefix);

        if (n ==0) {
          n = s.lastIndexOf('/');

          if (n != -1) {
            int p = s.lastIndexOf('.');

            if ((p != -1) && (s.endsWith(".png") || s.endsWith(".gif") || s.endsWith(".jpg") || s.endsWith(".jpeg"))) {
              s = "resource:" + s.substring(n + 1, p);
            }
          }
        }
      }

      return s;
    }

    return null;
  }

  void selectItem(final TreeItem ti, final int row) {
    Display.getDefault().asyncExec(new Runnable() {
      @Override
      public void run() {
        if (row != -1) {
          try {
            Event event = new Event();

            event.item  = ti;
            event.index = row;
            tree.notifyListeners(SWT.Selection, event);
          } catch(Exception ignore) {}
          ;
        }
      }
    });
  }

  @Override
  protected Control createDialogArea(Composite parent) {
    Control control = super.createDialogArea(parent);

    if (control instanceof Composite) {
      findTree((Composite) control);
    }

    return control;
  }

  protected void findTree(Composite composite) {
    for (Control c : composite.getChildren()) {
      if (c instanceof Tree) {
        tree = (Tree) c;
      } else if (c instanceof Composite) {
        findTree((Composite) c);
      }

      if (tree != null) {
        break;
      }
    }
  }

  private void expandPath(TreeItem[] a, int pos, int row) {
    String s = selectedPath[pos];

    for (TreeItem ti : a) {
      if (s.equals(ti.getText())) {
        ti.setExpanded(true);
        pos++;

        if (pos + 1 < selectedPath.length) {
          expandPath(ti.getItems(), pos, ++row);
        } else {
          tree.setSelection(ti);
          selectItem(ti, row);
        }

        break;
      }

      row++;
    }
  }

  public String getDocumentPath() {
    return documentPath;
  }

  public void setDocumentPath(String documentPath) {
    this.documentPath = documentPath;
  }
}
