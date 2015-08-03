/*
 * @(#)SDFOutlinePage.java
 *
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.studio.editors;

import com.appnativa.studio.DesignPane;
import com.appnativa.studio.Studio;
import com.appnativa.rare.widget.iWidget;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.ui.views.contentoutline.ContentOutlinePage;

import javax.swing.SwingUtilities;

public class SDFOutlinePage extends ContentOutlinePage {
  private ITextEditor            editor;
  private IEditorInput           input;
  private OutlineContentProvider outlineContentProvider;
  private OutlineLabelProvider   outlineLabelProvider;

  public SDFOutlinePage(ITextEditor editor) {
    super();
    this.editor = editor;
  }

  public void createControl(Composite parent) {
    super.createControl(parent);

    TreeViewer viewer = getTreeViewer();

    outlineContentProvider = new OutlineContentProvider(editor.getDocumentProvider());
    viewer.setContentProvider(outlineContentProvider);
    outlineLabelProvider = new OutlineLabelProvider();
    viewer.setLabelProvider(outlineLabelProvider);
    viewer.addSelectionChangedListener(this);
    viewer.setAutoExpandLevel(TreeViewer.ALL_LEVELS);

    //control is created after input is set
    if (input != null) {
      viewer.setInput(input);
    }
  }

  public void editorPageChanged() {
    Runnable r = new Runnable() {
      @Override
      public void run() {
        TreeViewer tv = getTreeViewer();

        if (tv != null) {
          tv.setInput(input);
        }
      }
    };

    Studio.runInSWTThread(r);
    r = new Runnable() {
      @Override
      public void run() {
        TreeViewer tv = getTreeViewer();

        if (tv != null) {
          tv.refresh();
        }
      }
    };
    Studio.runInSWTThread(r);
  }

  /*
   * Change in selection
   */
  public void selectionChanged(SelectionChangedEvent event) {
    super.selectionChanged(event);
    //find out which item in tree viewer we have selected, and set highlight range accordingly

    ISelection selection = event.getSelection();

    if (selection.isEmpty()) {
      editor.resetHighlightRange();
    } else {
      OutlineContentProvider.Node node =
        (OutlineContentProvider.Node) ((IStructuredSelection) selection).getFirstElement();

      if (!handleDesignPane(node) && (node.position != null)) {
        int start  = node.position.getOffset();
        int length = node.position.getLength();

        try {
          editor.setHighlightRange(start, length, true);
        } catch(IllegalArgumentException x) {
          editor.resetHighlightRange();
        }
      }
    }
  }

  /**
   * The editor is saved, so we should refresh representation
   *
   * @param tableNamePositions
   */
  public void update() {
    //set the input so that the outlines parse can be called
    //update the tree viewer state
    TreeViewer viewer = getTreeViewer();

    if (viewer != null) {
      Control control = viewer.getControl();

      if ((control != null) &&!control.isDisposed()) {
        viewer.setInput(input);
      }
    }
  }

  /**
   * Sets the input of the outline page
   */
  public void setInput(Object input) {
    this.input = (IEditorInput) input;
    update();
  }

  private boolean handleDesignPane(OutlineContentProvider.Node node) {
    try {
      final DesignPane pane = Studio.getSelectedDocument().getDesignPane();

      if ((pane == null) || (node.widget == null)) {
        return false;
      }

      final iWidget w = node.widget;

      if ((w != null) &&!w.isDisposed()) {
        SwingUtilities.invokeLater(new Runnable() {
          @Override
          public void run() {
            if (!w.isDisposed()) {
              pane.setSelectedWidget(w);
            }
          }
        });
      }
    } catch(Exception e) {
      e.printStackTrace();
    }

    return true;
  }
}
