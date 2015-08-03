/*
 * @(#)ProjectBuilder.java
 *
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.studio.builder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.jar.Attributes;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;

import com.appnativa.studio.ConsoleManager;
import com.appnativa.studio.preferences.MainPreferencePage;

public class ProjectBuilderIOS extends IncrementalProjectBuilder {
  public static final String BUILDER_ID = "com.appnativa.studio.iosBuilder";

  protected IProject[] build(int kind, Map args, IProgressMonitor monitor) throws CoreException {
    ResourceVisitor dv = null;

    try {
      IPath path = getProject().getLocation();

      dv = new ResourceVisitor(path.toFile().getAbsolutePath());

      if (dv.canBuild()) {
        monitor.beginTask("Generating Objective-C source", 2);
        monitor.setTaskName("Gathering Java Source Files...");

        if (kind == FULL_BUILD) {
          dv.fullBuild = true;
          fullBuild(dv, monitor);
        } else {
          IResourceDelta delta = getDelta(getProject());

          if (delta == null) {
            dv.fullBuild = true;
            fullBuild(dv, monitor);
          } else {
            incrementalBuild(dv, delta, monitor);
          }
        }

        monitor.worked(1);
        monitor.setTaskName("Transpiling Java Source Files...");

        if (!monitor.isCanceled()) {
          dv.finish(monitor);
        } else {
          throw new OperationCanceledException();
        }
      }
    } catch(IOException e) {
      e.printStackTrace();
    } finally {
      if (dv != null) {
        dv.dispose();
      }

      monitor.done();
    }

    return null;
  }

  protected void fullBuild(ResourceVisitor dv, final IProgressMonitor monitor) throws CoreException, IOException {
    getProject().accept(dv);
  }

  protected void incrementalBuild(ResourceVisitor dv, IResourceDelta delta, IProgressMonitor monitor)
          throws CoreException, IOException {
    delta.accept(dv);
  }

  class ResourceVisitor extends J2ObjCHelper implements IResourceDeltaVisitor, IResourceVisitor {
    PrintWriter     writer = new PrintWriter(ConsoleManager.getDefault().getWriter());
    File            jarFile;
    JarOutputStream target;

    public ResourceVisitor(String projectDir) throws IOException {
      super(MainPreferencePage.getAppNativaSDKDirectory(), projectDir);
    }

    public boolean canBuild() throws IOException {
      if (!canBuild(writer)) {
        return false;
      }

      Manifest manifest = new Manifest();

      manifest.getMainAttributes().put(Attributes.Name.MANIFEST_VERSION, "1.0");
      jarFile = File.createTempFile("appNativa", ".jar");
      target  = new JarOutputStream(new FileOutputStream(jarFile), manifest);

      return true;
    }

    public void dispose() {
      try {
        if (target != null) {
          target.close();
        }
      } catch(Exception e) {}

      if (jarFile != null) {
        jarFile.delete();
        jarFile = null;
      }
    }

    public void finish(IProgressMonitor monitor) {
      try {
        if (target != null) {
          target.close();
          target = null;

          if (transpile(getProject(),jarFile, writer,monitor)) {
            if (generatedSourceDirFile != null) {
              monitor.setTaskName("Refreshing project");

              IFolder f = getProject().getFolder(generatedSourceDir);

              if ((f != null) && f.exists()) {
                f.refreshLocal(IResource.DEPTH_INFINITE, monitor);
              }

              if ((includeFile != null) && (f.getParent() instanceof IFolder)) {
                f = (IFolder) f.getParent();

                IFile file = f.getFile(includeFile.getName());

                if ((file != null) && file.exists()) {
                  file.refreshLocal(IResource.DEPTH_INFINITE, monitor);
                }
              }
            }
          }
        }
      } catch(Exception e) {
        e.printStackTrace();
      }

      dispose();
    }

    @Override
    public boolean visit(IResource resource) throws CoreException {
      String name = getResourceName(resource);

      if (name != null) {
        add(target, name, resource.getLocation().toFile());
      }

      return true;
    }

    public boolean visit(IResourceDelta delta) throws CoreException {
      IResource resource = delta.getResource();
      String    name     = getResourceName(resource);

      if (name != null) {
        switch(delta.getKind()) {
          case IResourceDelta.CHANGED :
          case IResourceDelta.ADDED :
            add(target, name, resource.getLocation().toFile());

            break;

          case IResourceDelta.REMOVED :
            remove(name);

            break;
        }
      }

      return true;
    }

    String getResourceName(IResource resource) {
      if ((resource instanceof IFile) && resource.getName().endsWith(".java") && (resource.getLocation() != null)) {
        IFile  file = (IFile) resource;
        String s    = file.getProjectRelativePath().toString();
        int    n    = s.indexOf('/');

        return (n == -1)
               ? s
               : s.substring(n + 1);
      }

      return null;
    }
  }
}
