/*
 * @(#)SDFEditor.java
 *
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.studio.editors;

import com.appnativa.studio.RMLDocument;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.IVerticalRuler;
import org.eclipse.jface.text.source.projection.ProjectionAnnotation;
import org.eclipse.jface.text.source.projection.ProjectionAnnotationModel;
import org.eclipse.jface.text.source.projection.ProjectionSupport;
import org.eclipse.jface.text.source.projection.ProjectionViewer;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IURIEditorInput;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class RMLEditor extends TextEditor {
  public static Color       STRING       = new Color(Display.getDefault(), new RGB(128, 0, 0));
  public static Color       PREFORMATTED = new Color(Display.getDefault(), new RGB(192, 192, 192));
  public static Color       NUMBER       = new Color(Display.getDefault(), new RGB(0, 128, 128));
  public static Color       KEYWORD      = new Color(Display.getDefault(), new RGB(0, 0, 255));
  public static Color       ERROR        = new Color(Display.getDefault(), new RGB(255, 0, 0));
  public static Color       DEFAULT      = new Color(Display.getDefault(), new RGB(0, 0, 0));
  public static Color       COMMENT      = new Color(Display.getDefault(), new RGB(128, 128, 128));
  public static Color       CLASS        = new Color(Display.getDefault(), new RGB(0, 128, 0));
  public static Color       ATTRIBUTE    = new Color(Display.getDefault(), new RGB(0, 128, 0));
  private boolean           dirty;
  private IEditorInput      input;
  private MultiPageEditor   multiPageEditor;
  private Annotation[]      oldAnnotations;
  private SDFOutlinePage    outlinePage;
  private ProjectionSupport projectionSupport;

  public RMLEditor(MultiPageEditor multiPageEditor) {
    super();
    setSourceViewerConfiguration(new EditorConfiguration(this));
    this.multiPageEditor = multiPageEditor;
    setDirty(false);
  }


  public void createPartControl(Composite parent) {
    super.createPartControl(parent);

    ProjectionViewer viewer = (ProjectionViewer) getSourceViewer();

    projectionSupport = new ProjectionSupport(viewer, getAnnotationAccess(), getSharedColors());
    projectionSupport.install();
    // turn projection mode on
    viewer.doOperation(ProjectionViewer.TOGGLE);
  }

  @Override
  public void doSave(final IProgressMonitor progressMonitor) {
    RMLDocument doc = multiPageEditor.document;

    if (doc != null) {
      if (doc.updateSource(false)) {
        Display.getDefault().asyncExec(new Runnable() {
          public void run() {
            RMLEditor.super.doSave(progressMonitor);
          }
        });

        return;
      }
    }

    super.doSave(progressMonitor);
  }

  public void editorPageChanged() {
    if (outlinePage != null) {
      outlinePage.editorPageChanged();
    }
  }
  
  @Override
  public void dispose() {
    if (projectionSupport != null) {
      projectionSupport.dispose();
      projectionSupport = null;
    }

    if (outlinePage != null) {
      outlinePage.dispose();
      outlinePage = null;
    }

    ProjectionViewer          viewer          = (ProjectionViewer) getSourceViewer();
    ProjectionAnnotationModel annotationModel = viewer.getProjectionAnnotationModel();

    if ((annotationModel != null) && (oldAnnotations != null)) {
      annotationModel.modifyAnnotations(oldAnnotations, null, null);
      oldAnnotations = null;
    }
    super.dispose();
  }

  @SuppressWarnings({ "rawtypes", "unchecked" })
  public void updateFoldingStructure(ArrayList positions) {
    ProjectionViewer          viewer          = (ProjectionViewer) getSourceViewer();
    ProjectionAnnotationModel annotationModel = viewer.getProjectionAnnotationModel();
    if(annotationModel!=null) {
      Annotation[]              annotations     = new Annotation[positions.size()];
      // this will hold the new annotations along
      // with their corresponding positions
      HashMap newAnnotations = new HashMap();
  
      for (int i = 0; i < positions.size(); i++) {
        ProjectionAnnotation annotation = new ProjectionAnnotation();
  
        newAnnotations.put(annotation, positions.get(i));
        annotations[i] = annotation;
      }
  
      annotationModel.modifyAnnotations(oldAnnotations, newAnnotations, null);
      oldAnnotations = annotations;
      updateOutline();
    }
  }

  public void updateOutline() {
    if (outlinePage != null) {
      outlinePage.update();
    }
  }

  public void setDirty(boolean dirty) {
    this.dirty = dirty;
    Display.getDefault().asyncExec(new Runnable() {
      public void run() {
        firePropertyChange(PROP_DIRTY);
      }
    });
  }

  public Object getAdapter(Class required) {
    if (IContentOutlinePage.class.equals(required)) {
      if (outlinePage == null) {
        outlinePage = new SDFOutlinePage(this);
      }

      if (getEditorInput() != null) {
        outlinePage.setInput(getEditorInput());
      }

      return outlinePage;
    }

    return super.getAdapter(required);
  }

  public IEditorInput getInput() {
    return input;
  }

  public IDocument getInputDocument() {
    IDocument document = getDocumentProvider().getDocument(input);

    return document;
  }

  public URI getInputURI() {
    if (input instanceof IURIEditorInput) {
      return ((IURIEditorInput) input).getURI();
    }

    return null;
  }

  public MultiPageEditor getMultiPageEditor() {
    return multiPageEditor;
  }

  public IProject getProject() {
    if (input instanceof IFileEditorInput) {
      return ((IFileEditorInput) input).getFile().getProject();
    }

    return null;
  }

  public URL getProjectURL() {
    if (input instanceof IFileEditorInput) {
      IProject p = ((IFileEditorInput) input).getFile().getProject();

      if (p != null) {
        try {
          return p.getLocationURI().toURL();
        } catch(MalformedURLException e) {
          return null;
        }
      }
    }

    return null;
  }

  @Override
  public boolean isDirty() {
    return dirty || super.isDirty();
  }

  protected ISourceViewer createSourceViewer(Composite parent, IVerticalRuler ruler, int styles) {
    ISourceViewer viewer = new ProjectionViewer(parent, ruler, getOverviewRuler(), isOverviewRulerVisible(), styles);

    // ensure decoration support has been created and configured.
    getSourceViewerDecorationSupport(viewer);

    return viewer;
  }

  protected void doSetInput(IEditorInput newInput) throws CoreException {
    super.doSetInput(newInput);

    if (outlinePage != null) {
      outlinePage.setInput(input);
    }

    this.input = newInput;
  }

  protected void editorSaved() {
    dirty = false;
    super.editorSaved();

    String s = input.getName();

    if (s.contains("application.") || s.contains("mainwindow.")) {
      multiPageEditor.getRMLDocument();    //force document creation;
    }

    RMLDocument doc = multiPageEditor.document;

    if (doc != null) {
      doc.editorSaved(this);
    }

    firePropertyChange(PROP_DIRTY);

    if (outlinePage != null) {
      outlinePage.update();
    }
  }
}
