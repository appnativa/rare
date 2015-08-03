/*
 * @(#)MultiPageEditor.java
 *
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.studio.editors;

import java.awt.Rectangle;
import java.net.URL;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.SwingUtilities;

import org.eclipse.core.commands.operations.IOperationHistory;
import org.eclipse.core.commands.operations.IUndoContext;
import org.eclipse.core.commands.operations.ObjectUndoContext;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.ListenerList;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.reconciler.DirtyRegion;
import org.eclipse.jface.text.reconciler.IReconcilingStrategy;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.operations.RedoActionHandler;
import org.eclipse.ui.operations.UndoActionHandler;
import org.eclipse.ui.part.MultiPageEditorPart;
import org.eclipse.wb.swt.ResourceManager;

import com.appnativa.rare.Platform;
import com.appnativa.rare.iPlatformAppContext;
import com.appnativa.rare.exception.AbortOperationException;
import com.appnativa.rare.platform.swing.ui.view.FrameView;
import com.appnativa.rare.spot.ScrollPane;
import com.appnativa.rare.spot.Widget;
import com.appnativa.rare.spot.WidgetPane;
import com.appnativa.rare.ui.FormsPanel;
import com.appnativa.rare.ui.RenderType;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.listener.iApplicationListener;
import com.appnativa.rare.viewer.SplitPaneViewer;
import com.appnativa.rare.viewer.ToolBarViewer;
import com.appnativa.rare.viewer.WidgetPaneViewer;
import com.appnativa.rare.viewer.WindowViewer;
import com.appnativa.rare.viewer.iContainer;
import com.appnativa.rare.viewer.iTarget;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.studio.DesignAppContext;
import com.appnativa.studio.DesignPane;
import com.appnativa.studio.DesignPlatformImpl;
import com.appnativa.studio.FormChanger;
import com.appnativa.studio.FormChanger.Alignment;
import com.appnativa.studio.MainEmbedded;
import com.appnativa.studio.Project;
import com.appnativa.studio.RMLDocument;
import com.appnativa.studio.Studio;
import com.appnativa.studio.iSelectionListener;
import com.appnativa.studio.composite.DesignComposite;
import com.appnativa.studio.dialogs.ColorChooserDialog;
import com.appnativa.studio.dialogs.FormsLayout;
import com.appnativa.studio.dialogs.PreviewDialog;
import com.appnativa.studio.preferences.MainPreferencePage;
import com.appnativa.studio.views.PropertiesView;
import com.appnativa.studio.views.ToolboxView.Tool;

/**
 * An example showing how to create a multi-page editor. This example has 3
 * pages:
 * <ul>
 * <li>page 0 contains a nested text editor.
 * <li>page 1 allows you to change the font used in page 2
 * <li>page 2 shows the words in page 0 in sorted order
 * </ul>
 */
public class MultiPageEditor extends MultiPageEditorPart
        implements IResourceChangeListener, ISelectionProvider, iSelectionListener, IReconcilingStrategy,
                   IPartListener {
  ListenerList            listenerList = new ListenerList();
  int                     monCount     = 0;
  ISelection              activeSelection;
  RMLDocument             document;
  Composite               parentComposite;
  private DesignComposite designComposite;
  private MainEmbedded        designSage;
  private boolean         docListenerAdded;

  /** The text editor used in page 0. */
  private RMLEditor         editor;
  private Rectangle         lastSelectedArea;
  private RedoActionHandler redoAction;
  private ToolItem          tltmAbottom;
  private ToolItem          tltmAcenter;
  private ToolItem          tltmAfull;
  private ToolItem          tltmAhcenter;
  private ToolItem          tltmAhfull;
  private ToolItem          tltmAleft;
  private ToolItem          tltmAright;
  private ToolItem          tltmAtop;
  private ToolItem          tltmAvcenter;
  private ToolItem          tltmAvfull;
  private ToolItem          tltmCainsert;
  private ToolItem          tltmCbinsert;
  private ToolItem          tltmCdelete;
  private ToolItem          tltmCspacer;
  private ToolItem          tltmMlayout;
  private ToolItem          tltmRainsert;
  private ToolItem          tltmRbinsert;
  private ToolItem          tltmRdelete;
  private ToolItem          tltmRspacer;
  private ToolItem          tltmSmcspacer;
  private ToolItem          tltmSmrspacer;
  private UndoActionHandler undoAction;
  private ObjectUndoContext undoContext;

  /**
   * Creates a multi-page editor example.
   */
  public MultiPageEditor() {
    super();
    ResourcesPlugin.getWorkspace().addResourceChangeListener(this);
  }

  @Override
  public void addSelectionChangedListener(ISelectionChangedListener listener) {
    listenerList.add(listener);
  }

  /**
   * The <code>MultiPageEditorPart</code> implementation of this
   * <code>IWorkbenchPart</code> method disposes all nested editors. Subclasses
   * may extend.
   */
  public void dispose() {
    try {
      ResourcesPlugin.getWorkspace().removeResourceChangeListener(this);
      getSite().getWorkbenchWindow().getPartService().removePartListener(this);
      super.dispose();
    } catch(Exception ignore) {}
  }

  /**
   * Saves the multi-page editor's document.
   */
  public void doSave(IProgressMonitor monitor) {
    getEditor(0).doSave(monitor);
  }

  /**
   * Saves the multi-page editor's document as another file. Also updates the
   * text for page 0's tab, and updates this multi-page editor's input to
   * correspond to the nested editor's.
   */
  public void doSaveAs() {
    IEditorPart editor = getEditor(0);

    editor.doSaveAs();
    setPageText(0, editor.getTitle());
    setInput(editor.getEditorInput());
  }

  /*
   * (non-Javadoc) Method declared on IEditorPart
   */
  public void gotoMarker(IMarker marker) {
    setActivePage(0);
    IDE.gotoMarker(getEditor(0), marker);
  }

  @Override
  public void init(IEditorSite site, IEditorInput input) throws PartInitException {
    super.init(site, input);
    getSite().getWorkbenchWindow().getPartService().addPartListener(this);
  }

  @Override
  public void partActivated(IWorkbenchPart part) {
  }

  @Override
  public void partBroughtToTop(IWorkbenchPart part) {
    if (part == this) {
      RMLDocument doc = getRMLDocument();

      doc.updateSelections();
    }
  }

  @Override
  public void partClosed(IWorkbenchPart part) {}

  @Override
  public void partDeactivated(IWorkbenchPart part) {}

  @Override
  public void partOpened(IWorkbenchPart part) {}

  @Override
  public void reconcile(IRegion partition) {}

  @Override
  public void reconcile(DirtyRegion dirtyRegion, IRegion subRegion) {}

  @Override
  public void removeSelectionChangedListener(ISelectionChangedListener listener) {
    listenerList.remove(listener);
  }

  public void resetCursors() {
    if (getActivePage() == 1) {
      getRMLDocument().resetCursors();
    }
  }

  /**
   * Closes all project files on project close.
   */
  public void resourceChanged(final IResourceChangeEvent event) {
    IResource r = event.getResource();
    if (editor==null || editor.getProject() != r) {
      return;
    }
    
    switch(event.getType()) {
    case IResourceChangeEvent.PRE_CLOSE:
    case IResourceChangeEvent.PRE_DELETE:
      final IEditorInput input = editor.getEditorInput();
      Display.getDefault().asyncExec(new Runnable() {
        public void run() {
          IWorkbenchPage[] pages = getSite().getWorkbenchWindow().getPages();

          for (int i = 0; i < pages.length; i++) {
            IEditorPart editorPart = pages[i].findEditor(input);

            pages[i].closeEditor(editorPart, event.getType()==IResourceChangeEvent.PRE_CLOSE);
          }
        }
      });
      break;
      default:
        break;
    }
  }

  @Override
  public void selectionChanged(ISelection selection) {
    toggleDesignToolbar();
    fireSelectionChanged(selection);
  }

  public void toggleDesignToolbar() {
    RMLDocument doc = getRMLDocument();

    if (doc.getDesignPane() == null) {
      return;
    }

    iWidget w      = doc.getDesignPane().getGridWidget();
    boolean align  = false;
    boolean row    = false;
    boolean column = false;
    boolean spacer = false;

    if (w != null) {
      switch(w.getWidgetType()) {
        case Form :
        case GroupBox :
          iPlatformComponent fp = w.getDataComponent();

          if ((fp instanceof FormsPanel) &&!((FormsPanel) fp).isTableLayout()) {
            row    = true;
            column = true;
            align  = true;
            spacer = true;
          }

          break;

        case GridPane :
          row    = true;
          column = true;

          break;

        case SplitPane :
          if (((SplitPaneViewer) w).isTopToBottom()) {
            row = true;
          } else {
            column = true;
          }

          break;

        default :
          break;
      }
    }

    tltmAleft.setEnabled(align);
    tltmAtop.setEnabled(align);
    tltmAbottom.setEnabled(align);
    tltmAright.setEnabled(align);
    tltmAvcenter.setEnabled(align);
    tltmAhcenter.setEnabled(align);
    tltmAhfull.setEnabled(align);
    tltmAvfull.setEnabled(align);
    tltmAfull.setEnabled(align);
    tltmAcenter.setEnabled(align);
    tltmRainsert.setEnabled(row);
    tltmRbinsert.setEnabled(row);
    tltmRdelete.setEnabled(row);
    tltmRspacer.setEnabled(spacer);
    tltmSmrspacer.setEnabled(spacer);
    tltmCainsert.setEnabled(column);
    tltmCbinsert.setEnabled(column);
    tltmCdelete.setEnabled(column);
    tltmCspacer.setEnabled(spacer);
    tltmSmcspacer.setEnabled(spacer);
    tltmMlayout.setEnabled(doc.getLayoutConfig() != null);
    // if(getSDFDocument().isGUIWidget) {
    // var running=doc.previewID!=null &&
    // com.appnativa.rare.AppContext.getAppContext(doc.previewID)!=null
    // tb.setItemEnabled("Studio.design.run_preview",!running)
    // tb.setItemEnabled("Studio.design.stop_preview",running)
    // }
    // else {
    // tb.setItemEnabled("Studio.design.run_preview",false)
    // tb.setItemEnabled("Studio.design.stop_preview",false)
    // }
  }

  public void toolboxItemSelected(Tool tool) {
    if (getActivePage() == 1) {
      getRMLDocument().toolboxItemSelected(tool);
    }
  }

  @Override
  public void setDocument(IDocument document) {
    // TODO Auto-generated method stub
  }

  @Override
  public void setFocus() {
    super.setFocus();
    if ((designSage != null) && (designSage.getAppContext() != null)) {
      DesignPlatformImpl.switchContext((DesignAppContext) designSage.getAppContext());
      getRMLDocument().documentFocused();
    }
  }

  @Override
  public void setSelection(ISelection selection) {
    activeSelection = selection;
  }

  public RMLDocument getRMLDocument() {
    if (document == null) {
      document = new RMLDocument(editor, undoContext);
    }

    return document;
  }

  @Override
  public ISelection getSelection() {
    return activeSelection;
  }

  /*
   * (non-Javadoc) Method declared on IEditorPart.
   */
  public boolean isSaveAsAllowed() {
    return true;
  }

  /**
   * Creates page 0 of the multi-page editor, which contains a text editor.
   */
  void createPage0() {
    try {
      editor = new RMLEditor(this);

      int index = addPage(editor, getEditorInput());

      setPageText(index, "Source");
      setPartName(editor.getTitle());
    } catch(PartInitException e) {
      ErrorDialog.openError(getSite().getShell(), "Error creating nested text editor", null, e.getStatus());
    }
  }

  /**
   * Creates page 1 of the multi-page editor, which allows you to change the
   * font used in page 2.
   */
  @SuppressWarnings("unused")
  void createPage1() {
    Composite parent = new Composite(getContainer(), SWT.NONE);

    parent.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));

    GridLayout gl_parent = new GridLayout(1, false);

    gl_parent.verticalSpacing   = 0;
    gl_parent.marginWidth       = 0;
    gl_parent.horizontalSpacing = 0;
    gl_parent.marginHeight      = 0;
    parent.setLayout(gl_parent);
    RMLDocument doc=getRMLDocument();
    ToolBar toolBar = new ToolBar(parent, SWT.FLAT | SWT.SHADOW_OUT | SWT.NO_FOCUS);

    toolBar.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

    ToolItem tltmPreview = new ToolItem(toolBar, SWT.NONE);

    tltmPreview.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        PreviewDialog d = new PreviewDialog(getSite().getShell(), getRMLDocument());

        d.open();
      }
    });
    tltmPreview.setImage(ResourceManager.getPluginImage("com.appnativa.studio", "icons/toolbars/design_preview.png"));
    tltmPreview.setToolTipText(string("Studio.text.design.preview"));
    new ToolItem(toolBar, SWT.SEPARATOR);

    ToolItem tltmFit = new ToolItem(toolBar, SWT.CHECK);

    tltmFit.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        final ToolItem ti = (ToolItem) e.widget;

        getRMLDocument().setFitToPane(ti.getSelection());
      }
    });
    tltmFit.setSelection(doc.isFitToPane());
    tltmFit.setImage(ResourceManager.getPluginImage("com.appnativa.studio", "icons/toolbars/view_fit.png"));
    tltmFit.setToolTipText(string("Studio.text.design.fit"));

    ToolItem tltmScroll = new ToolItem(toolBar, SWT.CHECK);

    tltmScroll.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        final ToolItem ti = (ToolItem) e.widget;

        getRMLDocument().setAllowScrolling(ti.getSelection());
      }
    });
    tltmScroll.setImage(ResourceManager.getPluginImage("com.appnativa.studio", "icons/toolbars/scrollpane.png"));
    tltmScroll.setToolTipText(string("Studio.text.design.scrolling"));
    tltmScroll.setSelection(doc.isAllowScrolling());
    ToolItem tltmGrid = new ToolItem(toolBar, SWT.CHECK);

    tltmGrid.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        final ToolItem ti = (ToolItem) e.widget;

        getRMLDocument().setShowGrid(ti.getSelection());
      }
    });
    tltmGrid.setImage(ResourceManager.getPluginImage("com.appnativa.studio", "icons/toolbars/design_grid.png"));
    tltmGrid.setToolTipText(string("Studio.text.design.grid"));
    tltmGrid.setSelection(true);

    ToolItem tltmFocus = new ToolItem(toolBar, SWT.CHECK);

    tltmFocus.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        final ToolItem ti     = (ToolItem) e.widget;
        boolean        locked = ti.getSelection();

        PropertiesView.setEditorFocusedLocked(locked);

        if (!locked) {
          MultiPageEditor.this.setFocus();
        }
      }
    });
    tltmFocus.setImage(ResourceManager.getPluginImage("com.appnativa.studio", "icons/toolbars/design_focus.png"));
    new ToolItem(toolBar, SWT.SEPARATOR);
    tltmAleft = new ToolItem(toolBar, SWT.NONE);
    tltmAleft.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        align(Alignment.LEFT);
      }
    });
    tltmAleft.setImage(ResourceManager.getPluginImage("com.appnativa.studio", "icons/toolbars/align_left.png"));
    tltmAleft.setToolTipText(string("Studio.text.design.align_left"));

    tltmAhcenter = new ToolItem(toolBar, SWT.NONE);
    tltmAhcenter.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        align(Alignment.CENTER_HORIZONTAL);
      }
    });
    tltmAhcenter.setImage(ResourceManager.getPluginImage("com.appnativa.studio",
            "icons/toolbars/align_center_horizontal.png"));
    tltmAhcenter.setToolTipText(string("Studio.text.design.align_center_horizontal"));

    tltmAright = new ToolItem(toolBar, SWT.NONE);
    tltmAright.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        align(Alignment.RIGHT);
      }
    });
    tltmAright.setImage(ResourceManager.getPluginImage("com.appnativa.studio", "icons/toolbars/align_right.png"));
    tltmAright.setToolTipText(string("Studio.text.design.align_right"));

    tltmAtop = new ToolItem(toolBar, SWT.NONE);
    tltmAtop.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        align(Alignment.TOP);
      }
    });
    tltmAtop.setImage(ResourceManager.getPluginImage("com.appnativa.studio", "icons/toolbars/align_top.png"));
    tltmAtop.setToolTipText(string("Studio.text.design.align_top"));

    tltmAvcenter = new ToolItem(toolBar, SWT.NONE);
    tltmAvcenter.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        align(Alignment.CENTER_VERTICAL);
      }
    });
    tltmAvcenter.setImage(ResourceManager.getPluginImage("com.appnativa.studio",
            "icons/toolbars/align_center_vertical.png"));
    tltmAvcenter.setToolTipText(string("Studio.text.design.align_center_vertical"));

    tltmAbottom = new ToolItem(toolBar, SWT.NONE);
    tltmAbottom.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        align(Alignment.BOTTOM);
      }
    });
    tltmAbottom.setImage(ResourceManager.getPluginImage("com.appnativa.studio", "icons/toolbars/align_bottom.png"));
    tltmAbottom.setToolTipText(string("Studio.text.design.align_bottom"));

    tltmAcenter = new ToolItem(toolBar, SWT.NONE);
    tltmAcenter.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        align(Alignment.CENTER);
      }
    });
    tltmAcenter.setImage(ResourceManager.getPluginImage("com.appnativa.studio", "icons/toolbars/align_center.png"));
    tltmAcenter.setToolTipText(string("Studio.text.design.align_center"));
    tltmAhfull = new ToolItem(toolBar, SWT.NONE);
    tltmAhfull.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        align(Alignment.FULL_HORIZONTAL);
      }
    });
    tltmAhfull.setImage(ResourceManager.getPluginImage("com.appnativa.studio",
            "icons/toolbars/align_full_horizontal.png"));
    tltmAhfull.setToolTipText(string("Studio.text.design.align_full_horizontal"));

    tltmAvfull = new ToolItem(toolBar, SWT.NONE);
    tltmAvfull.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        align(Alignment.FULL_VERTICAL);
      }
    });
    tltmAvfull.setImage(ResourceManager.getPluginImage("com.appnativa.studio",
            "icons/toolbars/align_full_vertical.png"));
    tltmAvfull.setToolTipText(string("Studio.text.design.align_full_vertical"));

    tltmAfull = new ToolItem(toolBar, SWT.NONE);
    tltmAfull.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        align(Alignment.FULL);
      }
    });
    tltmAfull.setImage(ResourceManager.getPluginImage("com.appnativa.studio", "icons/toolbars/align_full.png"));
    tltmAfull.setToolTipText(string("Studio.text.design.align_full"));

    ToolItem toolItem_2 = new ToolItem(toolBar, SWT.SEPARATOR);

    tltmRspacer = new ToolItem(toolBar, SWT.NONE);
    tltmRspacer.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        final DesignPane pane = getRMLDocument().getDesignPane();
        Studio.runInSwingThread(new Runnable() {
          
          @Override
          public void run() {
            FormChanger.setRowAsSpacer(pane, pane.getGridWidget(), pane.getCurrentRow(), false);
          }
        });

      }
    });
    tltmRspacer.setImage(ResourceManager.getPluginImage("com.appnativa.studio", "icons/toolbars/row_spacer.png"));
    tltmRspacer.setToolTipText(string("Studio.text.design.row_spacer"));
    tltmSmrspacer = new ToolItem(toolBar, SWT.NONE);
    tltmSmrspacer.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        final DesignPane pane = getRMLDocument().getDesignPane();
        Studio.runInSwingThread(new Runnable() {
          
          @Override
          public void run() {
            FormChanger.setRowAsSpacer(pane, pane.getGridWidget(), pane.getCurrentRow(), true);
          }
        });

      }
    });
    tltmSmrspacer.setImage(ResourceManager.getPluginImage("com.appnativa.studio",
            "icons/toolbars/row_spacer_small.png"));
    tltmSmrspacer.setToolTipText(string("Studio.text.design.row_spacer_small"));
    tltmCspacer = new ToolItem(toolBar, SWT.NONE);
    tltmCspacer.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        final DesignPane pane = getRMLDocument().getDesignPane();
        Studio.runInSwingThread(new Runnable() {
          
          @Override
          public void run() {
            FormChanger.setColumnAsSpacer(pane, pane.getGridWidget(), pane.getCurrentColumn(), false);
          }
        });

      }
    });
    tltmCspacer.setImage(ResourceManager.getPluginImage("com.appnativa.studio", "icons/toolbars/column_spacer.png"));
    tltmCspacer.setToolTipText(string("Studio.text.design.column_spacer"));
    tltmSmcspacer = new ToolItem(toolBar, SWT.NONE);
    tltmSmcspacer.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        final DesignPane pane = getRMLDocument().getDesignPane();
        Studio.runInSwingThread(new Runnable() {
          
          @Override
          public void run() {
            FormChanger.setColumnAsSpacer(pane, pane.getGridWidget(), pane.getCurrentColumn(), true);
          }
        });

      }
    });
    tltmSmcspacer.setImage(ResourceManager.getPluginImage("com.appnativa.studio",
            "icons/toolbars/column_spacer_small.png"));
    tltmSmcspacer.setToolTipText(string("Studio.text.design.column_spacer_small"));

    ToolItem toolItem_3 = new ToolItem(toolBar, SWT.SEPARATOR);

    tltmRbinsert = new ToolItem(toolBar, SWT.NONE);
    tltmRbinsert.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        final DesignPane pane = getRMLDocument().getDesignPane();
        Studio.runInSwingThread(new Runnable() {
          
          @Override
          public void run() {
            FormChanger.addRow(pane, pane.getGridWidget(), pane.getCurrentRow());
          }
        });
      }
    });
    tltmRbinsert.setImage(ResourceManager.getPluginImage("com.appnativa.studio",
            "icons/toolbars/row_insert_before.png"));
    tltmRbinsert.setToolTipText(string("Studio.text.design.row_insert_before"));
    tltmRainsert = new ToolItem(toolBar, SWT.NONE);
    tltmRainsert.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        final DesignPane pane = getRMLDocument().getDesignPane();
        Studio.runInSwingThread(new Runnable() {
          
          @Override
          public void run() {
            FormChanger.addRow(pane, pane.getGridWidget(), pane.getCurrentRow() + 1);
          }
        });

      }
    });
    tltmRainsert.setImage(ResourceManager.getPluginImage("com.appnativa.studio", "icons/toolbars/row_insert_after.png"));
    tltmRainsert.setToolTipText(string("Studio.text.design.row_insert_after"));
    tltmCbinsert = new ToolItem(toolBar, SWT.NONE);
    tltmCbinsert.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        final DesignPane pane = getRMLDocument().getDesignPane();
        Studio.runInSwingThread(new Runnable() {
          
          @Override
          public void run() {
            FormChanger.addColumn(pane, pane.getGridWidget(), pane.getCurrentColumn());
          }
        });

      }
    });
    tltmCbinsert.setImage(ResourceManager.getPluginImage("com.appnativa.studio",
            "icons/toolbars/column_insert_before.png"));
    tltmCbinsert.setToolTipText(string("Studio.text.design.column_insert_before"));
    tltmCainsert = new ToolItem(toolBar, SWT.NONE);
    tltmCainsert.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        final DesignPane pane = getRMLDocument().getDesignPane();
        Studio.runInSwingThread(new Runnable() {
          
          @Override
          public void run() {
            FormChanger.addColumn(pane, pane.getGridWidget(), pane.getCurrentColumn() + 1);
          }
        });

      }
    });
    tltmCainsert.setImage(ResourceManager.getPluginImage("com.appnativa.studio",
            "icons/toolbars/column_insert_after.png"));
    tltmCainsert.setToolTipText(string("Studio.text.design.column_insert_after"));

    ToolItem toolItem_4 = new ToolItem(toolBar, SWT.SEPARATOR);

    tltmCdelete = new ToolItem(toolBar, SWT.NONE);
    tltmCdelete.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        final DesignPane pane = getRMLDocument().getDesignPane();
        Studio.runInSwingThread(new Runnable() {
          
          @Override
          public void run() {
            FormChanger.removeColumn(pane, (iContainer) pane.getGridWidget(), pane.getCurrentColumn());
          }
        });
      }
    });
    tltmCdelete.setImage(ResourceManager.getPluginImage("com.appnativa.studio", "icons/toolbars/column_delete.png"));
    tltmCdelete.setToolTipText(string("Studio.text.design.column_delete"));
    tltmRdelete = new ToolItem(toolBar, SWT.NONE);
    tltmRdelete.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        final DesignPane pane = getRMLDocument().getDesignPane();
        Studio.runInSwingThread(new Runnable() {
          
          @Override
          public void run() {
            FormChanger.removeRow(pane, (iContainer) pane.getGridWidget(), pane.getCurrentRow());
          }
        });
      }
    });
    tltmRdelete.setImage(ResourceManager.getPluginImage("com.appnativa.studio", "icons/toolbars/row_delete.png"));
    tltmRdelete.setToolTipText(string("Studio.text.design.row_delete"));
    
    new ToolItem(toolBar, SWT.SEPARATOR);
    
    tltmMlayout = new ToolItem(toolBar, SWT.NONE);
    tltmMlayout.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        showWidgetLayoutForm();
      }
    });
    tltmMlayout.setImage(ResourceManager.getPluginImage("com.appnativa.studio", "icons/toolbars/cell_layout.png"));
    tltmMlayout.setToolTipText(string("Studio.text.design.modify_layout"));

    ToolItem tltmGridColor = new ToolItem(toolBar, SWT.NONE);

    tltmGridColor.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {}
    });
    tltmGridColor.setImage(ResourceManager.getPluginImage("com.appnativa.studio",
            "icons/toolbars/design_grid_color.png"));
    tltmGridColor.setToolTipText(string("Studio.text.design.gridColor"));
    tltmGridColor.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        ColorChooserDialog d = new ColorChooserDialog(getSite().getShell());

        d.setColor(getRMLDocument().getDesignPane().getGridColor());

        if (d.open() == IDialogConstants.OK_ID) {
          if (d.getSelectedColor() == null) {
            getRMLDocument().getProject().setGridColor(null);
          } else {
            getRMLDocument().getDesignPane().setGridColor(d.getSelectedColor());

            if (Studio.yesNo(Studio.getResourceAsString("Studio.text.change_color_globally"))) {
              MainPreferencePage.updateGridColor(d.getSelectedColor());
            } else {
              getRMLDocument().getProject().setGridColor(d.getSelectedColor());
            }
          }
        }
      }
    });
    tltmAleft.setEnabled(false);
    tltmAtop.setEnabled(false);
    tltmAbottom.setEnabled(false);
    tltmAright.setEnabled(false);
    tltmAvcenter.setEnabled(false);
    tltmAhcenter.setEnabled(false);
    tltmRainsert.setEnabled(false);
    tltmRbinsert.setEnabled(false);
    tltmRdelete.setEnabled(false);
    tltmRspacer.setEnabled(false);
    tltmSmrspacer.setEnabled(false);
    tltmCainsert.setEnabled(false);
    tltmCbinsert.setEnabled(false);
    tltmCdelete.setEnabled(false);
    tltmCspacer.setEnabled(false);
    tltmSmcspacer.setEnabled(false);
    tltmMlayout.setEnabled(false);
    parentComposite = parent;

    int index = addPage(parent);

    setPageText(index, "Designer");
    getSite().setSelectionProvider(this);
  }

  protected void align(final Alignment alignment) {
    final DesignPane pane = getRMLDocument().getDesignPane();
    Studio.runInSwingThread(new Runnable() {
      
      @Override
      public void run() {
        FormChanger.align(pane, pane.getGridWidget(), pane.getSelectedWidgetConfigs(), alignment);
      }
    });

  }

  /**
   * Creates the pages of the multi-page editor.
   */
  protected void createPages() {
    undoContext = new ObjectUndoContext(this);
    undoAction  = new UndoActionHandler(getSite(), undoContext);
    redoAction  = new RedoActionHandler(getSite(), undoContext);
    createPage0();
    createPage1();
  }

  protected void fireSelectionChanged(ISelection selection) {
    activeSelection = selection;

    SelectionChangedEvent event     = new SelectionChangedEvent(this, selection);
    Object[]              listeners = listenerList.getListeners();

    for (int i = 0; i < listeners.length; ++i) {
      ((ISelectionChangedListener) listeners[i]).selectionChanged(event);
    }
  }

  /**
   * Calculates the contents of page 2 when the it is activated.
   */
  protected void pageChange(int newPageIndex) {
    super.pageChange(newPageIndex);

    switch(newPageIndex) {
      case 1 :
        showDesign();
        setVisualEditorUndoRedo();

        break;

      default :
        if (designSage != null) {
          hideDesign();
          getRMLDocument().updateSource(false);
        }

        if (document != null) {
          document.updateSelections();
        }

        setTextEditorUndoRedo();

        break;
    }
  }

  protected void showWidgetLayoutForm() {
    RMLDocument doc = getRMLDocument();
    iWidget     w   = doc.getDesignPane().getGridWidget();

    if (w != null) {
      switch(w.getWidgetType()) {
        case Form :
        case GroupBox :
          FormsLayout dialog = new FormsLayout(getSite().getShell(), doc);

          dialog.open();

          break;
        default :
          doc.editWithDialog((Widget)doc.getLayoutConfig(), w);
          break;
      }
    }
  }

  protected IOperationHistory getOperationHistory() {
    return PlatformUI.getWorkbench().getOperationSupport().getOperationHistory();
  }

  protected IUndoContext getUndoContext() {
    // use own undo context
    return undoContext;
  }

  private void createDesignPane(WindowViewer win) throws Exception {
    RMLDocument doc = getRMLDocument();
    Project     p   = doc.getProject();

    if (p != null) {
      URL u = p.getBaseURL();

      if (u != null) {
        Platform.getAppContext().setContextURL(u);
      }
    }

    WidgetPane wpcfg = new WidgetPane();
    ScrollPane sp    = wpcfg.getScrollPaneReference();

    sp.verticalScrollbar.setValue(1);
    sp.horizontalScrollbar.setValue(1);
    wpcfg.autoResizeWidget.setValue(false);

    WidgetPaneViewer wv = (WidgetPaneViewer) win.createViewer(win, wpcfg);
    wv.setLinkedData(null); //remove configuration object that gets set when in design mode
    
    win.activateViewer(wv, iTarget.TARGET_WORKSPACE);

    AtomicInteger ai          = new AtomicInteger(0);
    FrameView     designPanel = (FrameView) wv.getWidgetPanel().getView();

    designPanel.setLayout(designPanel);

    DesignPane pane = new DesignPane(designPanel, ai);

    pane.setOwner(this);
    wv.setParent(wv);
    wv.setDesignMode(true);
    wv.setManualUpdate(true);
    designPanel.setGlassPane(pane);
    pane.setupBorders(wv.getContainerComponent().getView());
    URL url=doc.getURL();
    if (url != null) {
      wv.setContextURL(url);
    }

    iContainer c = doc.createWidget(win, wv);

    wv.setWidget(c);
    pane.setRootWidget(c, lastSelectedArea);

    switch(c.getWidgetType()) {
      case SplitPane :
      case TabPane :
      case Window :
      case WidgetPane :
      case GridPane :
        wv.setWidgetRenderType(RenderType.STRETCHED);

        break;

      case MenuBar :
        wv.setWidgetRenderType(RenderType.STRETCH_WIDTH);

        break;

      case ToolBar :
        if (((ToolBarViewer) c).isHorizontal()) {
          wv.setWidgetRenderType(RenderType.STRETCH_WIDTH);
        } else {
          wv.setWidgetRenderType(RenderType.STRETCH_HEIGHT);
        }

        break;

      default :
        wv.setWidgetRenderType(RenderType.UPPER_LEFT);

        break;
    }

    doc.setDesignPane(wv, pane);
    wv.update();

    if (p != null) {
      pane.setGridColor(p.getGridColor());
      pane.setSelectionColor(p.getSelectionColor());
      pane.setTrackingColor(p.getTrackingColor());
    }
  }

  private void hideDesign() {
    if (designSage != null) {
      final iPlatformAppContext app = designSage.getAppContext();

      designSage = null;

      final RMLDocument doc = getRMLDocument();

      SwingUtilities.invokeLater(new Runnable() {
        @Override
        public void run() {
          try {
            if (doc != null) {
              lastSelectedArea = doc.shatterPane();
            }

            if (app != null) {
              app.exit();
            }
          } catch(Exception e) {
            e.printStackTrace();
          }

          Display.getDefault().asyncExec(new Runnable() {
            @Override
            public void run() {
              try {
                if (designComposite != null) {
                  designComposite.dispose();
                  designComposite = null;
                }
              } finally {
                editor.editorPageChanged();
              }
            }
          });
        }
      });
    }
  }

  private void hideDesignAfterError() {
    if (designSage != null) {
      final iPlatformAppContext app = designSage.getAppContext();

      designSage = null;

      try {
        app.exit();
      } catch(Exception e) {}

      Display.getDefault().asyncExec(new Runnable() {
        @Override
        public void run() {
          if (designComposite != null) {
            designComposite.dispose();
            designComposite = null;
          }

          setActivePage(0);
        }
      });
    }
  }

  private void showDesign() {
    try {
      final RMLDocument doc = getRMLDocument();

      if (!doc.isGUIWidget()) {
        setActivePage(0);
        Studio.alert("The document is not a GUI widget");

        return;
      }

      if (!docListenerAdded) {
        docListenerAdded = true;
        doc.addSelectionChangedListener(this);
      }

      designComposite = new DesignComposite(parentComposite, SWT.NONE);
      designComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
      designComposite.setLayout(new FillLayout(SWT.HORIZONTAL));
      designComposite.getParent().layout();

      final ApplicationListenerAdapter l = new ApplicationListenerAdapter() {
        @Override
        public void applicationInitialized(iPlatformAppContext app) {
          try {
            final WindowViewer w = designSage.getAppContext().getWindowViewer();

            w.setDesignMode(true);
            createDesignPane(w);
            Display.getDefault().asyncExec(new Runnable() {
              @Override
              public void run() {
                editor.editorPageChanged();
              }
            });
          } catch(Exception e) {
            Studio.showError(e);
            hideDesignAfterError();
          }
        }
      };

      designComposite.populate();
      SwingUtilities.invokeLater(new Runnable() {
        @Override
        public void run() {
          try {
            com.appnativa.rare.spot.Application a = new com.appnativa.rare.spot.Application();

            a.applicationRoot.setValue(".");
            designSage = new MainEmbedded(designComposite, doc.getBaseURL(), a);
            designSage.getAppContext().addApplicationListener(l);

            Project p = doc.getProject();

            designSage.startApplication(p);
          } catch(Exception e) {
            if (!(e instanceof AbortOperationException)) {
              e.printStackTrace();

              try {
                designSage.handleException(e);
              } catch(Exception ee) {
                ee.printStackTrace();
              }
            }

            hideDesignAfterError();
          }
        }
      });
    } catch(Exception e) {
      e.printStackTrace();
    }
  }

  private String string(String name) {
    return Studio.getResourceAsString(name);
  }

  /**
   * Select the appropriate undo and redo action handlers for a normal Java
   * editor.
   */
  private void setTextEditorUndoRedo() {
    final IActionBars actionBars  = getEditorSite().getActionBars();
    IAction           undoAction2 = editor.getAction(ActionFactory.UNDO.getId());

    actionBars.setGlobalActionHandler(ActionFactory.UNDO.getId(), undoAction2);

    IAction redoAction2 = editor.getAction(ActionFactory.REDO.getId());

    actionBars.setGlobalActionHandler(ActionFactory.REDO.getId(), redoAction2);
    actionBars.updateActionBars();
  }

  /**
   * Select the appropriate undo and redo action handlers for the visual editor.
   */
  private void setVisualEditorUndoRedo() {
    final IActionBars actionBars = getEditorSite().getActionBars();

    actionBars.setGlobalActionHandler(ActionFactory.UNDO.getId(), undoAction);
    actionBars.setGlobalActionHandler(ActionFactory.REDO.getId(), redoAction);
    actionBars.updateActionBars();
  }

  static class ApplicationListenerAdapter implements iApplicationListener {
    @Override
    public boolean allowClosing(iPlatformAppContext app) {
      return true;
    }

    @Override
    public void applicationClosing(iPlatformAppContext app) {}

    @Override
    public void applicationInitialized(iPlatformAppContext app) {}

    @Override
    public void applicationPaused(iPlatformAppContext app) {}

    @Override
    public void applicationResumed(iPlatformAppContext app) {}
  }
}
