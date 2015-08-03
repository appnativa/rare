/*
 * @(#)ProjectBuilder.java
 *
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.studio.builder;

import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

public class ProjectBuilder extends IncrementalProjectBuilder {
  public static final String  BUILDER_ID  = "com.appnativa.studio.rareBuilder";
  private static final String MARKER_TYPE = "com.appnativa.studio.rmlProblem";

  void checkRML(IResource resource) {
//    if ((resource instanceof IFile) && (resource.getName().endsWith(".rml") || resource.getName().endsWith(".sdf"))) {
//      IFile file = (IFile) resource;
//
//      deleteMarkers(file);
//
//      SDFParser   p      = null;
//      InputStream stream = null;
//      try {
//        stream = file.getContents();
//        if (stream != null) {
//          SDFNode       node        = new SDFNode(null);
//          iFileResolver urlResolver = new FileResolver(new File("/"));
//
//          node.setNodeType(SDFNode.NODETYPE_ROOT);
//
//          FormattingCallback pc = new FormattingCallback(node, urlResolver);
//
//          p = new SDFParser(new InputStreamReader(stream));
//          pc.setIgnoreMissingReferences(true);
//          pc.setIgnoreReferences(true);
//          pc.setIgnoreImports(true);
//          p.parse(pc);
//        }
//
//      } catch(Exception e) {
//        int ln = (p == null)
//                 ? -1
//                 : p.getTokenizerLineNumber();
//
//        addMarker(file, ApplicationException.getMessageEx(e), ln, IMarker.SEVERITY_ERROR);
//      }
//    }
  }

  /*
   * (non-Javadoc)
   *
   * @see org.eclipse.core.internal.events.InternalBuilder#build(int,
   *      java.util.Map, org.eclipse.core.runtime.IProgressMonitor)
   */
  protected IProject[] build(int kind, Map args, IProgressMonitor monitor) throws CoreException {
    if (kind == FULL_BUILD) {
      fullBuild(monitor);
    } else {
      IResourceDelta delta = getDelta(getProject());

      if (delta == null) {
        fullBuild(monitor);
      } else {
        incrementalBuild(delta, monitor);
      }
    }

    return null;
  }

  protected void fullBuild(final IProgressMonitor monitor) throws CoreException {
    try {
      getProject().accept(new SampleResourceVisitor());
    } catch(CoreException e) {}
  }

  protected void incrementalBuild(IResourceDelta delta, IProgressMonitor monitor) throws CoreException {
    // the visitor does the work.
    delta.accept(new SampleDeltaVisitor());
  }

  void addMarker(IFile file, String message, int lineNumber, int severity) {
    try {
      IMarker marker = file.createMarker(MARKER_TYPE);

      marker.setAttribute(IMarker.MESSAGE, message);
      marker.setAttribute(IMarker.SEVERITY, severity);

      if (lineNumber < 1) {
        lineNumber = 1;
      }

      marker.setAttribute(IMarker.LINE_NUMBER, lineNumber);
    } catch(CoreException e) {}
  }

  void deleteMarkers(IFile file) {
    try {
      file.deleteMarkers(MARKER_TYPE, false, IResource.DEPTH_ZERO);
    } catch(CoreException ce) {}
  }

  class SampleDeltaVisitor implements IResourceDeltaVisitor {
    public boolean visit(IResourceDelta delta) throws CoreException {
      IResource resource = delta.getResource();

      switch(delta.getKind()) {
        case IResourceDelta.ADDED :
          // handle added resource
          checkRML(resource);

          break;

        case IResourceDelta.REMOVED :
          // handle removed resource
          break;

        case IResourceDelta.CHANGED :
          // handle changed resource
          checkRML(resource);

          break;
      }

      //return true to continue visiting children.
      return true;
    }
  }


  class SampleResourceVisitor implements IResourceVisitor {
    public boolean visit(IResource resource) {
      checkRML(resource);

      //return true to continue visiting children.
      return true;
    }
  }
}
