/*
 * @(#)XCodeLauncher.java
 *
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.studio.builder;

import com.appnativa.studio.properties.PropertiesConstants;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.program.Program;
import org.eclipse.ui.handlers.HandlerUtil;

public class XCodeLauncher extends org.eclipse.core.commands.AbstractHandler {
  @Override
  public Object execute(ExecutionEvent event) throws ExecutionException {
    do {
      if (!Platform.OS_MACOSX.equals(Platform.getOS())) {
        break;
      }

      ISelection sel = HandlerUtil.getCurrentSelection(event);

      if (!(sel instanceof IStructuredSelection)) {
        break;
      }

      Object obj = ((IStructuredSelection) sel).getFirstElement();

      if (!(obj instanceof IResource)) {
        break;
      }

      IProject proj = ((IResource) obj).getProject();

      if (proj == null) {
        break;
      }

      String name;

      try {
        name = proj.getPersistentProperty(PropertiesConstants.XCODE_PROJECT_FILE);

        ResourceVisitor v = null;
        IResource       f = (name == null)
                            ? null
                            : proj.getFolder(name);

        if ((f == null) ||!f.exists()) {
          v = new ResourceVisitor();
          proj.accept(v);
          f = v.found;
        }

        if (f == null) {
          break;
        }

        String s = f.getLocation().toOSString();

        try {
          if (Program.launch(s) && (v != null)) {
            s = f.getProjectRelativePath().toString();
            proj.setPersistentProperty(PropertiesConstants.XCODE_PROJECT_FILE, s);
          }
          ;
        } catch(Exception e) {
          e.printStackTrace();
        }
      } catch(CoreException e1) {
        break;
      }
    } while(false);

    return null;
  }

  static class ResourceVisitor implements IResourceVisitor {
    IResource found;

    @Override
    public boolean visit(IResource resource) throws CoreException {
      if (resource.getName().endsWith(".xcodeproj")) {
        found = resource;

        return false;
      }

      return true;
    }
  }
}
