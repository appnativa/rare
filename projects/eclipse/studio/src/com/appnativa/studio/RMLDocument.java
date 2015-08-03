/*
 * @(#)SDFDocument.java
 *
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.studio;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.File;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.EventObject;
import java.util.List;

import javax.swing.SwingUtilities;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.undo.UndoableEdit;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.AbstractOperation;
import org.eclipse.core.commands.operations.IOperationHistory;
import org.eclipse.core.commands.operations.IUndoContext;
import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.ListenerList;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.text.DocumentEvent;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentListener;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.model.IWorkbenchAdapter;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.views.properties.IPropertySource;

import com.appnativa.rare.Platform;
import com.appnativa.rare.TemplateHandler;
import com.appnativa.rare.iPlatformAppContext;
import com.appnativa.rare.net.ActionLink;
import com.appnativa.rare.platform.swing.ui.view.UtilityPanel;
import com.appnativa.rare.spot.Font;
import com.appnativa.rare.spot.GridCell;
import com.appnativa.rare.spot.GridPane;
import com.appnativa.rare.spot.GroupBox;
import com.appnativa.rare.spot.MainWindow;
import com.appnativa.rare.spot.MenuBar;
import com.appnativa.rare.spot.Navigator;
import com.appnativa.rare.spot.SplitPane;
import com.appnativa.rare.spot.TabPane;
import com.appnativa.rare.spot.Table;
import com.appnativa.rare.spot.TemplateContext;
import com.appnativa.rare.spot.Widget;
import com.appnativa.rare.ui.ColorUtils;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.UIFontHelper;
import com.appnativa.rare.ui.event.iChangeListener;
import com.appnativa.rare.ui.painter.iBackgroundPainter;
import com.appnativa.rare.ui.painter.iPlatformPainter;
import com.appnativa.rare.util.JavaHandler;
import com.appnativa.rare.viewer.SplitPaneViewer;
import com.appnativa.rare.viewer.WidgetPaneViewer;
import com.appnativa.rare.viewer.WindowViewer;
import com.appnativa.rare.viewer.iContainer;
import com.appnativa.rare.viewer.iViewer;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.spot.SDFNode;
import com.appnativa.spot.SPOTAny;
import com.appnativa.spot.SPOTSequence;
import com.appnativa.spot.SPOTSet;
import com.appnativa.spot.iSPOTElement;
import com.appnativa.studio.dialogs.SequenceArrayEditor;
import com.appnativa.studio.dialogs.SplitPaneEditor;
import com.appnativa.studio.editors.MultiPageEditor;
import com.appnativa.studio.editors.RMLEditor;
import com.appnativa.studio.properties.EditorHelper;
import com.appnativa.studio.properties.SPOTInspector;
import com.appnativa.studio.properties.SequenceProperty;
import com.appnativa.studio.properties.WidgetProperty;
import com.appnativa.studio.views.PropertiesView;
import com.appnativa.studio.views.ToolboxView.Tool;
import com.appnativa.util.MutableInteger;

public class RMLDocument implements iDesignListener, UndoableEditListener, IDocumentListener, iChangeListener {
  private static StructuredSelection emptySelection  = new StructuredSelection();
  private static WidgetAdaptable[]   emptyWSelection = new WidgetAdaptable[0];
  public boolean                     badCfg;
  public iSPOTElement                cfg;
  public DesignPane                  designPane;
  public RMLEditor                   editor;
  public IFile                       file;
  ListenerList                       listenerList    = new ListenerList();
  Project                            project;
  Tool                               selectedTool;
  private boolean                    allowScrolling;
  private boolean                    fitToPane;
  private boolean                    ignoreProjetChangeEvents;
  private WidgetPaneViewer           paneParentViewer;
  private int                        projectModCount;
  private final IUndoContext         undoContext;
  List<Widget> oldSelections;

  public RMLDocument(RMLEditor editor, IUndoContext undoContext) {
    this.editor = editor;
    this.undoContext = undoContext;
    editor.getInputDocument().addDocumentListener(this);

    try {
      if (editor.getEditorInput() instanceof IFileEditorInput) {
        file = ((IFileEditorInput) editor.getEditorInput()).getFile();
      }
    } catch (Exception ignore) {
    }
  }

  public void addSelectionChangedListener(iSelectionListener listener) {
    listenerList.add(listener);
  }

  public iSPOTElement createConfiguration(iViewer v) throws Exception {
    if (this.cfg == null) {
      this.cfg = createConfigurationEx(v);
    }

    return this.cfg;
  }

  public iSPOTElement createConfigurationEx(iViewer v) throws Exception {
    Project project = getProject();
    WindowViewer w = v.getAppContext().getWindowViewer();
    IDocument doc = editor.getInputDocument();
    URL turl = getURL();
    URL url;

    TemplateHandler.setInstance(Platform.getAppContext(), (project == null) ? null : project.getTemplateHandler());

    if ((designPane == null) || (cfg == null)) {
      url = w.createInlineURL(doc.get(), "text/x-sdf");
    } else {
      url = w.createInlineURL(cfg.toString(), "text/x-sdf");
    }

    ActionLink l = w.createActionLink(url);

    if (turl != null) {
      v.setContextURL(turl);
    } else {
      if (project != null) {
        v.setContextURL(project.baseURL);
      }
    }

    l.setContext(v);

    iSPOTElement cfg = w.createConfigurationObject(l);

    if (cfg instanceof com.appnativa.rare.spot.Application) {
      cfg = new Application((com.appnativa.rare.spot.Application) cfg);
      ((Application) cfg).contextURL.setValue(URLHelper.toString(turl));
    }

    TemplateHandler.setInstance(Platform.getAppContext(), null);

    if (project != null) {
      projectModCount = project.getModCount();
    }
    cfg.spot_setLinkedData(v.getContextURL());
    return cfg;
  }

  public com.appnativa.rare.spot.Application createPreviewApplication() throws Exception {
    iSPOTElement cfg = createConfigurationEx(Platform.getContextRootViewer());
    com.appnativa.rare.spot.Application a;
    Project project = getProject();

    if ((project != null) && (project.application == null)) {
      project = null;
    }

    if (cfg instanceof com.appnativa.studio.Application) {
      a = (com.appnativa.rare.spot.Application) ((Application) cfg).application.clone();
    } else if (project != null) {
      a = (com.appnativa.rare.spot.Application) project.application.clone();
    } else {
      a = new com.appnativa.rare.spot.Application();
    }

    a.managedScreenSizes.spot_clear();

    boolean isapp = (cfg instanceof com.appnativa.studio.Application);

    if (cfg instanceof MainWindow) {
      a.setMainWindow((MainWindow) cfg);
    } else {
      MainWindow mw = a.getMainWindow();

      if ((mw == null) || (!isapp)) {
        mw = (project == null) ? new MainWindow() : project.getPreviewMainWindow(!isapp);
        a.setMainWindow(mw);
      }

      if (!isapp) {
        mw.viewer.setValue(cfg);
      }
    }

    a.contextURL.spot_clear();

    return a;
  }

  public iContainer createWidget(WindowViewer win, iViewer v) throws Exception {
    iSPOTElement cfg = createConfiguration(v);
    iWidget w;
    Project p = getProject();

    if (cfg instanceof Application) {
      com.appnativa.rare.spot.Application aa = (com.appnativa.rare.spot.Application) ((Application) cfg).application;
      URL u = null;
      MainWindow wm = aa.getMainWindow();
      ActionLink link = null;

      if (p != null) {
        if (wm == null) {
          wm = p.getMainWindow();
          link = p.getMainWindowActionLink();
        }

        if (wm == null) {
          wm = p.application.getMainWindowReference();
        }

        u = p.getBaseURL();
      } else {
        aa = ((Application) cfg).application;
        wm = aa.getMainWindowReference();

        if (aa.contextURL.spot_hasValue()) {
          u = v.getURL(aa.contextURL.getValue());
        }
      }

      if (p != null) {
        p.setTemplateHandler();
      }

      w = DesignWindowManager.createApplicationWindowViewer(win.getAppContext(), wm, u);

      if (p != null) {
        p.clearTemplateHandler();
      }

      if (link != null) {
        ((iViewer) w).setViewerActionLink(link);
      }
    } else if (cfg instanceof MainWindow) {
      URL u = null;
      MainWindow wm = (MainWindow) cfg;

      if ((p != null) && (p.application != cfg)) {
        u = p.getBaseURL();
      }

      if (p != null) {
        p.setTemplateHandler();
      }

      w = DesignWindowManager.createApplicationWindowViewer(win.getAppContext(), wm, u);

      if (p != null) {
        p.clearTemplateHandler();
      }
    } else {
      if (p != null) {
        p.setTemplateHandler();
      }

      w = win.createWidget(v, (Widget) cfg);

      if (p != null) {
        p.clearTemplateHandler();
        p.adjustBoundsToWindow(w, (Widget) cfg);
      }
    }

    w.setVisible(true);

    iContainer c;

    if (w instanceof iContainer) {
      c = (iContainer) w;
    } else {
      c = new DesignContainer(w);
    }

    return c;
  }

  public void dispose() {
    if (project != null) {
      project.removeChangeListener(this);
    }

    project = null;
    file = null;
    editor = null;
  }

  @Override
  public void documentAboutToBeChanged(DocumentEvent e) {
  }

  @Override
  public void documentChanged(DocumentEvent e) {
    this.cfg = null;
  }

  public void documentFocused() {
    if (designPane != null) {
      Project p = getProject();

      if ((p != null) && (projectModCount != p.getModCount())) {
        projectModCount = p.getModCount();
        cfg = null;
        reloadDesign();
      } else {
        designPane.requestRepaint();
      }
    }
  }

  public void editWithDialog(final Widget wc, final iWidget w) {
    Studio.runInSWTThread(new Runnable() {
      @Override
      public void run() {
        editWithDialogEx(wc, w);
      }
    });
  }

  public void editorSaved(RMLEditor editor) {
    if (designPane != null) {
      designPane.clean();
    }

    Project p = getProjectEx();

    if (p == null) {
      return;
    }

    boolean reload = false;

    if (cfg == null) {
      reload = p.isReloadRequired(editor.getEditorInput());
    } else {
      reload = (cfg instanceof Application) || (cfg instanceof MainWindow) || (cfg instanceof TemplateContext);
    }

    if (reload) {
      ignoreProjetChangeEvents = true;

      try {
        p.setDirty(true);
      } catch (Exception e) {
        Studio.showError(e);
      }

      ignoreProjetChangeEvents = false;
    }
  }

  public void endPreview(iPlatformAppContext app) {
    Project p = getProject();

    if (p != null) {
      p.resetAfterPreview();
    }
  }

  public void formatSource() {
    WindowViewer w = Platform.getWindowViewer();

    if (w != null) {
      iSPOTElement e = cfg;

      cfg = null;

      try {
        createConfiguration(w);
        updateSource(true);
      } catch (Exception ex) {
        cfg = e;
        Studio.showError(ex);
      }
    }
  }

  public void globalPreferencesChanged() {
    projectChanged();
  }

  public void handleToolDoubleClicked() {
    if (selectedTool != null) {
      final Widget cfg = (Widget) ((Widget) Studio.getAttribute(selectedTool.id)).clone();
      Runnable r = new Runnable() {
        @Override
        public void run() {
          try {
            designPane.handleDrop(null, cfg, null);
          } finally {
            Display.getDefault().asyncExec(new Runnable() {
              @Override
              public void run() {
                resetCursors();
              }
            });
          }
        }
      };

      Studio.runInSwingThread(r);
    }
  }

  public void reloadDesign() {
    if (SwingUtilities.isEventDispatchThread()) {
      reloadDesignEx();
    } else {
      SwingUtilities.invokeLater(new Runnable() {
        @Override
        public void run() {
          reloadDesignEx();
        }
      });
    }
  }

  public void removeSelectionChangedListener(iSelectionListener listener) {
    listenerList.remove(listener);
  }

  public void resetCursors() {
    if (selectedTool != null) {
      selectedTool = null;
      setCursor(null, null);
    }
  }

  public Rectangle shatterPane() {
    Rectangle r = null;

    if (this.designPane != null) {
      designPane.removeDesignListener(this);
      r = this.designPane.shatter();
    }
    if(paneParentViewer!=null) {
      paneParentViewer.dispose();
      paneParentViewer=null;
    }
    designPane = null;
    fireSelectionChanged(emptySelection, emptyWSelection);

    return r;
  }

  @Override
  public void somethingHappened(final DesignEvent event) {
    switch (event.getEvent()) {
      case RELOAD_REQUEST:
        reloadDesign();

        return;

      case WIDGET_ACTION:
        checkIfActive((MultiPageEditor) event.getOwner());
        handleWidgetAction(event.getWidget());

        return;

      case MOTION_TRACKING_CLICK:
        handleMotionTrackingClick(event);

        return;

      default:
        break;
    }

    Studio.runInSWTThread(new Runnable() {
      @Override
      public void run() {
        somethingHappenedEx(event);
      }
    });
  }

  @Override
  public void stateChanged(EventObject e) {
    projectChanged();
  }

  public void toolboxItemSelected(Tool tool) {
    Cursor cursor = null;
    java.awt.Cursor scursor = null;

    if (!"Pointer".equals(tool.name)) {
      if ((selectedTool != null) && (selectedTool == tool)) {
        return;
      }

      Image img = Studio.getResourceAsIcon(tool.id);

      if (img != null) {
        cursor = new Cursor(Display.getDefault(), img.getImageData(), 3, 3);
        scursor = com.appnativa.studio.Utilities.createCursor(tool.name, Studio.getResourceAsBufferedImage(tool.id));
      }

      setCursor(cursor, scursor);
      selectedTool = tool;
    } else {
      resetCursors();
    }
  }

  @Override
  public void undoableEditHappened(UndoableEditEvent event) {
    IOperationHistory history = editor.getSite().getWorkbenchWindow().getWorkbench().getOperationSupport().getOperationHistory();
    UndoableOperation op = new UndoableOperation("Design Change", event.getEdit());

    op.addContext(undoContext);

    try {
      history.execute(op, null, null);
    } catch (ExecutionException e) {
      e.printStackTrace();
    }
  }

  public void updateSelections() {
    List<Widget> sels = (designPane == null) ? null : designPane.getSelectedWidgetConfigs();
    StructuredSelection s;
    final int count = (sels == null) ? 0 : sels.size();
    WidgetAdaptable[] a;
    WidgetAdaptable wa;

    if (count == 0) {
      s = emptySelection;
      a = emptyWSelection;
    } else if (count == 1) {
      s = new StructuredSelection(wa = new WidgetAdaptable(this, sels.get(0)));
      a = new WidgetAdaptable[] { wa };
    } else {
      int len = sels.size();

      a = new WidgetAdaptable[len];

      for (int i = 0; i < len; i++) {
        a[i] = new WidgetAdaptable(this, sels.get(i));
      }

      s = new StructuredSelection(Arrays.asList(a));
    }

    fireSelectionChanged(s, a);
  }

  public boolean updateSource(boolean force) {
    if (!force && (designPane != null)) {
      force = designPane.isDirty();
    }

    if (force && (cfg != null)) {
      IDocument doc = editor.getInputDocument();
      if (cfg instanceof TemplateContext) {
        doc.set(cfg.toString());
      } else {
        doc.set(removeTemplateElements().toString());
      }

      return true;
    }

    return false;
  }

  public void setAllowScrolling(boolean allowScrolling) {
    this.allowScrolling = allowScrolling;

    if (paneParentViewer != null) {
      ((UtilityPanel) paneParentViewer.getWidgetPanel().getView()).setLockViewSize(!allowScrolling);
      paneParentViewer.update();
    }
  }

  public void setCursor(final Cursor swtcursor, final java.awt.Cursor awtcursor) {
    Display.getDefault().asyncExec(new Runnable() {
      @Override
      public void run() {
        if (editor != null) {
          editor.getEditorSite().getShell().setCursor(swtcursor);
        }
      }
    });
    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        if (designPane != null) {
          designPane.setCursor(awtcursor);
          designPane.setMotionTracking(awtcursor != null);
        }
      }
    });
  }

  public void setDesignPane(WidgetPaneViewer parentViewer, DesignPane designPane) {
    this.designPane = designPane;
    this.designPane.addDesignListener(this);
    this.designPane.addUndoableEditListener(this);
    this.paneParentViewer = parentViewer;
    parentViewer.setAutoResizeWidgets(fitToPane);
    ((UtilityPanel) parentViewer.getWidgetPanel().getView()).setLockViewSize(!allowScrolling);

    Project p = getProject();

    if (p != null) {
      designPane.setSelectionColor(p.getSelectionColor());
      designPane.setTrackingColor(p.getTrackingColor());
      designPane.setGridColor(p.getGridColor());
      designPane.setCanvasColor(p.getCanvasColor());
    }
  }

  public void setFitToPane(boolean fitToPane) {
    this.fitToPane = fitToPane;

    if (paneParentViewer != null) {
      paneParentViewer.setAutoResizeWidgets(fitToPane);
    }
  }

  public void setShowGrid(boolean showGrid) {
    if (designPane != null) {
      designPane.setShowGrid(showGrid);
    }
  }

  public String getAssetsRelativePrefix() {
    if (editor.getEditorInput() instanceof IFileEditorInput) {
      IFile f = ((IFileEditorInput) editor.getEditorInput()).getFile();
      String s = f.getProjectRelativePath().toString();
      int n = s.lastIndexOf('/');

      if (n != -1) {
        s = s.substring(0, n + 1);
      }

      return s;
    }

    return null;
  }

  public URL getBaseURL() {
    Project p = (file == null) ? null : Project.getProject(file, true);

    if (p != null) {
      return p.getBaseURL();
    }

    return getURL();
  }

  public iWidget getContextWidget() {
    DesignPane pane = getDesignPane();
    iWidget widget = null;

    if (pane != null) {
      widget = pane.getSelectedWidget();

      if (widget == null) {
        widget = pane.getGridWidget();
      }

      if (widget == null) {
        widget = pane.getRootWidget();
      }
    }

    return widget;
  }

  public DesignPane getDesignPane() {
    return designPane;
  }

  public IProject getIProject() {
    if (file != null) {
      return file.getProject();
    }

    return null;
  }

  public iSPOTElement getLayoutConfig() {
    if (designPane == null) {
      return null;
    }

    iWidget gw = designPane.getGridWidget();
    iWidget.WidgetType wt = (gw == null) ? iWidget.WidgetType.Label : gw.getWidgetType();
    iSPOTElement cfg = null;

    switch (wt) {
      case TabPane:
        // if (gw instanceof FloorTabPaneViewer) {
        // break;
        // }
        // //$FALL-THROUGH$
      case SplitPane:
        cfg = (iSPOTElement) gw.getLinkedData();

        break;

      case GridPane:
        cfg = ((GridPane) gw.getLinkedData()).regions;

        break;

      case Form:
      case GroupBox:
        cfg = (iSPOTElement) gw.getLinkedData();

        if (!((GroupBox) cfg).layout.stringValue().equals("forms")) {
          cfg = null;
        }

        break;

      default:
        break;
    }

    return cfg;
  }

  public Dimension getPreferredSize() {
    if ((designPane != null) && (designPane.getRootWidget() != null)) {
      UIDimension d = designPane.getRootWidget().getContainerComponent().getPreferredSize();

      return new Dimension(d.intWidth(), d.intHeight());
    }

    return null;
  }

  public Project getProject() {
    if ((project == null) && (Platform.getAppContext() != null)) {
      if (file != null) {
        project = Project.getProject(file, true);

        if (project != null) {
          project.addChangeListener(this);
        }
      }
    }

    if ((project != null) && project.isDirty()) {
      ignoreProjetChangeEvents = true;

      try {
        project.reloadApplicationConfig();
      } catch (Exception e) {
        e.printStackTrace();
        Studio.showError(e);
      }

      ignoreProjetChangeEvents = false;
    }

    return project;
  }

  public URL getURL() {
    try {
      return editor.getInputURI().toURL();
    } catch (MalformedURLException e) {
      return null;
    }
  }

  public WindowViewer getWindowViewer() {
    return paneParentViewer.getWindow();
  }

  public boolean isAllowScrolling() {
    return allowScrolling;
  }

  public boolean isFitToPane() {
    return fitToPane;
  }

  public boolean isGUIWidget() {
    if (cfg == null) {
      try {
        IDocument doc = editor.getInputDocument();
        SDFNode node = SDFNode.parse(new StringReader(doc.get()));

        node = node.getFirstNode();

        if (node != null) {
          String s = node.getNodeName();

          if (s.indexOf('.') == -1) {
            s = "com.appnativa.rare.spot." + s;

            Class cls = Class.forName(s);

            if (Widget.class.isAssignableFrom(cls)) {
              return true;
            }

            if (com.appnativa.rare.spot.Application.class.isAssignableFrom(cls)) {
              return true;
            }

            if (com.appnativa.rare.spot.MainWindow.class.isAssignableFrom(cls)) {
              return true;
            }

            return false;
          }
        }
      } catch (Exception ignore) {
      }
    }

    return (cfg instanceof Widget) || (cfg instanceof MainWindow) || (cfg instanceof com.appnativa.rare.spot.Application);
  }

  void editWithDialogEx(final Widget wc, final iWidget w) {
    SPOTSet set = null;
    String name = null;

    switch (w.getWidgetType()) {
      case SplitPane: {
        SplitPane sp = (SplitPane) wc;
        SPOTSet props = sp.getSplitProportions();
        float splits[];

        if (props == null) {
          splits = new float[] { .5f };
        } else {
          splits = props.floatValues();
        }

        SplitPaneEditor ed = new SplitPaneEditor(Display.getCurrent().getActiveShell(), splits);

        if (ed.open() == IDialogConstants.OK_ID) {
          splits = ed.getSplits();

          try {
            MutableInteger changed = new MutableInteger(0);
            // convert strings to get rid of impercise garbage
            String[] ssplits = new String[splits.length];

            for (int i = 0; i < splits.length; i++) {
              ssplits[i] = String.valueOf(splits[i]);
            }

            FormChanger.changeValue(designPane, sp.getSplitProportionsReference(), ssplits, false, true, changed);

            if (changed.get() == 1) {
              ((SplitPaneViewer) w).setSplitProportions(splits);
              w.update();
            }
          } catch (MalformedURLException e) {
            e.printStackTrace();
          }
        }

        break;
      }

      case TabPane: {
        set = ((TabPane) wc).tabs;
        name = "Tab";
        break;
      }

      case Table: {
        set = ((Table) wc).columns;
        name = "Column";
        break;
      }

      case MenuBar: {
        set = ((MenuBar) wc).getPopupMenuReference();
        name = "MenuItem";
        break;
      }

      case Navigator: {
        set = ((Navigator) wc).actions;
        name = "Action";
        break;
      }

      default:
        break;
    }
    if (set != null) {
      SequenceArrayEditor ed = new SequenceArrayEditor(Display.getCurrent().getActiveShell(), set, name, true);
      designPane.startEditsCapture();
      if (ed.open() == IDialogConstants.OK_ID && designPane.hasCapturedEdits()) {
        SPOTSet e = (SPOTSet) ed.getSPOTElement();
        set.spot_copy(e, false);
        Studio.runInSwingThread(new Runnable() {

          @Override
          public void run() {
            designPane.endEditsCapture(false, true, wc);
          }
        });
      } else {
        designPane.endEditsCapture(true, false, null);
      }
    }
  }

  // void editWithDialogEx(final Widget wc, final iWidget w) {
  // switch(w.getWidgetType()) {
  // case SplitPane : {
  // SplitPane sp = (SplitPane) wc;
  // SPOTSet set = sp.getSplitProportions();
  // float splits[];
  //
  // if (set == null) {
  // splits = new float[] { .5f };
  // } else {
  // splits = set.floatValues();
  // }
  //
  // SplitPaneEditor ed = new
  // SplitPaneEditor(Display.getCurrent().getActiveShell(), splits);
  //
  // if (ed.open() == IDialogConstants.OK_ID) {
  // splits = ed.getSplits();
  //
  // try {
  // MutableInteger changed = new MutableInteger(0);
  // //convert strings to get rid of impercise garbage
  // String[] ssplits = new String[splits.length];
  //
  // for (int i = 0; i < splits.length; i++) {
  // ssplits[i] = String.valueOf(splits[i]);
  // }
  //
  // FormChanger.changeValue(designPane, sp.getSplitProportionsReference(),
  // ssplits, false, true, changed);
  //
  // if (changed.get() == 1) {
  // ((SplitPaneViewer) w).setSplitProportions(splits);
  // w.update();
  // }
  // } catch(MalformedURLException e) {
  // e.printStackTrace();
  // }
  // }
  //
  // break;
  // }
  //
  // case TabPane : {
  // SPOTSet tabs = ((TabPane) wc).tabs;
  // SequenceArrayEditor ed = new
  // SequenceArrayEditor(Display.getCurrent().getActiveShell(),
  // (iSPOTElement) tabs.clone(), "Tab");
  //
  // if (ed.open() == IDialogConstants.OK_ID) {
  // iSPOTElement e = ed.getSPOTElement();
  //
  // try {
  // MutableInteger changed = new MutableInteger(0);
  //
  // FormChanger.changeValue(designPane, tabs, e, false, true, changed);
  //
  // if (changed.get() == 1) {
  // designPane.requestReload(wc);
  // }
  // } catch(MalformedURLException ex) {
  // ex.printStackTrace();
  // }
  // }
  //
  // break;
  // }
  //
  // case Table : {
  // SPOTSet columns = ((Table) wc).columns;
  // SequenceArrayEditor ed = new
  // SequenceArrayEditor(Display.getCurrent().getActiveShell(),
  // (iSPOTElement) columns.clone(), "Column");
  //
  // if (ed.open() == IDialogConstants.OK_ID) {
  // iSPOTElement e = ed.getSPOTElement();
  //
  // try {
  // MutableInteger changed = new MutableInteger(0);
  //
  // FormChanger.changeValue(designPane, columns, e, false, true, changed);
  //
  // if (changed.get() == 1) {
  // designPane.requestReload(wc);
  // }
  // } catch(MalformedURLException ex) {
  // ex.printStackTrace();
  // }
  // }
  //
  // break;
  // }
  //
  // case MenuBar : {
  // SPOTSet menus = ((MenuBar) wc).getPopupMenuReference();
  // SequenceArrayEditor ed = new
  // SequenceArrayEditor(Display.getCurrent().getActiveShell(),
  // (iSPOTElement) menus.clone(), "MenuItem");
  //
  // if (ed.open() == IDialogConstants.OK_ID) {
  // iSPOTElement e = ed.getSPOTElement();
  //
  // try {
  // MutableInteger changed = new MutableInteger(0);
  //
  // FormChanger.changeValue(designPane, menus, e, false, true, changed);
  //
  // if (changed.get() == 1) {
  // designPane.requestReload(wc);
  // }
  // } catch(MalformedURLException ex) {
  // ex.printStackTrace();
  // }
  // }
  //
  // break;
  // }
  //
  // case Navigator : {
  // SPOTSet actions = ((Navigator) wc).actions;
  // SequenceArrayEditor ed = new
  // SequenceArrayEditor(Display.getCurrent().getActiveShell(),
  // (iSPOTElement) actions.clone(), "Action");
  //
  // if (ed.open() == IDialogConstants.OK_ID) {
  // iSPOTElement e = ed.getSPOTElement();
  //
  // try {
  // MutableInteger changed = new MutableInteger(0);
  //
  // FormChanger.changeValue(designPane, actions, e, false, true, changed);
  //
  // if (changed.get() == 1) {
  // designPane.requestReload(wc);
  // }
  // } catch(MalformedURLException ex) {
  // ex.printStackTrace();
  // }
  // }
  //
  // break;
  // }
  //
  // default :
  // break;
  // }
  // }
  void reload() {
    if (designPane != null) {
      reloadDesign();
    } else {
      updateSource(true);
    }
  }

  void somethingHappenedEx(DesignEvent event) {
    switch (event.getEvent()) {
      case RELOAD_REQUEST:
        reloadDesign();

        break;

      case WIDGET_ACTION:
        checkIfActive((MultiPageEditor) event.getOwner());
        handleWidgetAction(event.getWidget());

        break;

      case SELECTION_CHANGED:
        updateSelections();

        break;

      case MOTION_TRACKING_CLICK:
        handleMotionTrackingClick(event);

        break;

      case DOCUMENT_MODIFIED:
        if (designPane != null) {
          editor.setDirty(designPane.isDirty());
        }

        break;

      case OPEN_DOCUMENT:
        URL u = (URL) event.getData();

        try {
          final IFile[] a = ResourcesPlugin.getWorkspace().getRoot().findFilesForLocationURI(u.toURI());
          final IFile efile;
          final File jfile;
          if ((a != null) && (a.length > 0)) {
            efile = a[0];
            jfile = null;
          } else {
            FileEx f = new FileEx(u.getPath());
            if (f.exists() && f.isFile()) {
              jfile = f;
            } else {
              jfile = null;
            }
            efile = null;
          }
          if (efile != null || jfile != null) {
            Display.getDefault().asyncExec(new Runnable() {
              public void run() {
                IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();

                try {
                  if (jfile != null) {
                    IFileStore fileStore = EFS.getLocalFileSystem().getStore(jfile.toURI());
                    IDE.openEditorOnFileStore(page, fileStore);
                  } else {
                    IEditorDescriptor desc = PlatformUI.getWorkbench().getEditorRegistry().getDefaultEditor(efile.getName());
                    page.openEditor(new FileEditorInput(efile), desc.getId());
                  }
                } catch (Exception e) {
                }
              }
            });
          }

          break;
        } catch (URISyntaxException e1) {
        }

        Display.getDefault().beep();

        break;

      default:
        break;
    }
  }

  protected boolean cleanElement(iSPOTElement root, iSPOTElement e) {
    if (e == null) {
      return false;
    }

    if (e.spot_getLinkedData() == TemplateHandler.TAG) {
      if (root == cfg) {
        cfg = null;
      }

      e.spot_clear();

      return true;
    }

    if (e instanceof SPOTAny) {
      cleanElement(root, ((SPOTAny) e).getValue());
    } else if (e instanceof SPOTSequence) {
      SPOTSequence seq = (SPOTSequence) e;
      int len = seq.spot_getCount();

      for (int i = 0; i < len; i++) {
        if (cleanElement(root, e = seq.spot_elementAt(i))) {
          seq.spot_clearReferenceVariable(e);
        }
      }
    } else if (e instanceof SPOTSet) {
      SPOTSet set = (SPOTSet) e;
      int len = set.size();

      for (int i = 0; i < len; i++) {
        cleanElement(root, (iSPOTElement) set.get(i));
      }
    }

    return false;
  }

  protected void fireSelectionChanged(final StructuredSelection selection, final WidgetAdaptable[] wa) {
    if(!isNewSelection()) return;
    Runnable r = new Runnable() {
      public void run() {
        Object[] listeners = listenerList.getListeners();

        for (int i = 0; i < listeners.length; ++i) {
          ((iSelectionListener) listeners[i]).selectionChanged(selection);
        }

        if (designPane == null) {
          PropertiesView.widgetSelectionChanged(emptyWSelection);
        } else {
          PropertiesView.widgetSelectionChanged(wa);
        }
      }
    };

    Studio.runInSWTThread(r);
  }
  protected boolean isNewSelection() {
    List<Widget> cfgs = null;
    do {
      if(designPane==null) break;
      cfgs = designPane.getSelectedWidgetConfigs();
      int len = cfgs.size();
      if (oldSelections == null || oldSelections.size() != cfgs.size()) {
        break;
      }
      int i = 0;
      for (i = 0; i < len; i++) {
        if (oldSelections.get(i) != cfgs.get(i)) {
          break;
        }
      }
      if (i == len) {
        return false;
      }
    } while (false);
    oldSelections = cfgs;
    return true;
  }
  protected void handleMotionTrackingClick(DesignEvent event) {
    try {
      Point p = (Point) event.getData();
      Widget cfg = (Widget) ((Widget) Studio.getAttribute(selectedTool.id)).clone();

      designPane.handleDrop(p, cfg, null);
    } finally {
      Studio.runInSWTThread(new Runnable() {
        @Override
        public void run() {
          resetCursors();
        }
      });
    }
  }

  protected void handleWidgetAction(iWidget w) {
    Widget wc = (w == null) ? null : (Widget) w.getLinkedData();

    if (wc == null) {
      return;
    }

    if ((w instanceof com.appnativa.rare.viewer.iViewer)) {
      switch (w.getWidgetType()) {
        case TabPane:
        case SplitPane:
        case Table:
        case MenuBar:
          editWithDialog(wc, w);

          break;

        default:
          break;
      }
    } else {
      switch (w.getWidgetType()) {
        case Navigator:
          editWithDialog(wc, w);

          break;

        case Line:
          // cfg=w.linkedData.linesReference
          // dialog=window.openDialog("lines.sdf","Line Chooser",cfg).returnValue
          // if(dialog!=null && dialog.okPressed) {
          // var changed=new com.appnativa.util.MutableInteger(0);
          // FormChanger.changeValue(this.designPanel.glassPane,cfg,dialog.lines,false,true,changed)
          // if(changed.value>0) {
          // this.designPanel.glassPane.requestReload(null)
          // }
          // }
          break;

        default:
          Studio.runInSwingThread(new Runnable() {
            @Override
            public void run() {
              if (designPane != null) {
                designPane.inplaceEdit();
              }
            }
          });
      }

      return;
    }
  }

  protected void projectChanged() {
    if (!ignoreProjetChangeEvents && (Studio.getSelectedDocument() == this)) {
      if (file != null) {
        Project p = Project.getProject(file, false);

        if (p != null) {
          if (designPane != null) {
            designPane.setSelectionColor(p.getSelectionColor());
            designPane.setTrackingColor(p.getTrackingColor());
            designPane.setGridColor(p.getGridColor());
            designPane.setCanvasColor(p.getCanvasColor());
            reloadDesign();
          }
        }
      }
    }
  }

  protected iSPOTElement removeTemplateElements() {
    iSPOTElement e = cfg;

    e.spot_setLinkedData(null);
    cleanElement(e, e);

    if (e != cfg) {
      cfg = null;
    }

    return e;
  }

  private void checkIfActive(final MultiPageEditor e) {
    Display.getDefault().asyncExec(new Runnable() {
      public void run() {
        e.setFocus();
      }
    });
  }

  private void reloadDesignEx() {
    if (designPane != null) {
      designPane.prepareForReload();
      iWidget w =paneParentViewer.removeWidget();
      WindowViewer win = Platform.getWindowViewer();
      final boolean efc = PropertiesView.isEditorFocusedLocked();

      if (!efc) {
        PropertiesView.setEditorFocusedLocked(true);
      }

      try {
        iContainer c = createWidget(win, paneParentViewer);

        paneParentViewer.setWidget(c);
        paneParentViewer.update();
        designPane.setReloadedRootWidget(c);
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        if (PropertiesView.isEditorFocusedLocked() != efc) {
          Studio.runInSWTThread(new Runnable() {

            @Override
            public void run() {
              PropertiesView.setEditorFocusedLocked(efc);
            }
          });
        }
      }

      if (w != null) {
        w.dispose();
      }
    }
  }

  private void updateDesignPane() {
    designPane.prepareForReload();
    iWidget w = designPane.getRootWidget();

    paneParentViewer.setWidget((iWidget) null, false);

    WindowViewer win = (WindowViewer) paneParentViewer.getWindow().getViewer();

    try {
      iContainer c = createWidget(win, paneParentViewer);

      if (w != null) {
        w.setVisible(false);
      }

      paneParentViewer.setWidget(c);
      paneParentViewer.update();
      designPane.setReloadedRootWidget(c);
    } catch (Exception e) {
      e.printStackTrace();
    }

    if (w != null) {
      w.dispose();
    }
  }

  private Project getProjectEx() {
    if ((project == null)) {
      if (file != null) {
        project = Project.getProject(file, false);

        if (project != null) {
          project.addChangeListener(this);
        }
      }
    }

    return project;
  }

  public static class WidgetAdaptable implements IAdaptable { // ,IWorkbenchAdapter
    SequenceProperty   attributes;
    SPOTSequence       config;
    List<SPOTSequence> configs;
    RMLDocument        document;
    WidgetProperty     property;
    boolean            sorted;
    iWidget            widget;
    private boolean    showingAttributes;

    public WidgetAdaptable(RMLDocument doc, SPOTSequence config) {
      this.document = doc;
      this.config = config;
    }

    public WidgetAdaptable(RMLDocument doc, List<SPOTSequence> configs, SPOTSequence base) {
      this.document = doc;
      this.configs = configs;
      this.config = base;
    }

    public void dispose() {
      if (property != null) {
        property.dispose();
      }

      document = null;
      widget = null;
      property = null;
    }

    public void handleReloadRequest(final iSPOTElement e, final Object value, final String prop,final boolean reload) {
      Studio.runInSwingThread(new Runnable() {
        @Override
        public void run() {
          handleReloadRequestEx(e, value, prop,reload);
        }
      });
    }

    public void propertyChanged(iSPOTElement e, Object value, boolean b) {
      if (value instanceof SequenceProperty) {
        return;
      }

      MutableInteger changed = new com.appnativa.util.MutableInteger(0);

      try {
        if (showingAttributes) {
          if (value instanceof iSPOTElement) {
            value = Utilities.toString((iSPOTElement) value);
          }

          if (configs != null) {
            FormChanger.changeAttributes(document.designPane, configs, e.spot_getName(), value);
          } else {
            FormChanger.changeAttribute(document.designPane, config, e.spot_getName(), value);
          }
        } else {
          boolean reload = SPOTInspector.needsReload(e);
          String prop = SPOTInspector.getComponentProperty(e, value);
          if (configs != null) {
            FormChanger.changeValues(document.designPane, configs, e, value, changed);
          } else {
            FormChanger.changeValue(document.designPane, e, value, false, reload, changed);
          }

          if (changed.getValue() > 0) {
            if ("borders".equals(e.spot_getName()) && (value instanceof SPOTSet) && ((SPOTSet) value).size() == 0) {
              if (config instanceof Widget) {
                ((Widget) config).setBorders(null);
              } else if (config instanceof GridCell) {
                ((GridCell) config).setBorders(null);
              }
            }
            handleReloadRequest(e, value, prop,reload);
          }
        }
      } catch (MalformedURLException e1) {
        e1.printStackTrace();
      }
    }

    public void setShowAttributes(boolean showAttributes) {
      if (this.showingAttributes != showAttributes) {
        this.showingAttributes = showAttributes;

        if (attributes == null) {
          attributes = EditorHelper.getAttributesSequenceProperty(config, configs);
        }

        property = null;
      }
    }

    public void setSorted(boolean sorted) {
      this.sorted = sorted;

      if (property != null) {
        property.setSorted(sorted);
      }
    }

    @Override
    public Object getAdapter(Class adapter) {
      if (adapter == IPropertySource.class) {
        if ((property == null) && (config != null)) {
          if (showingAttributes) {
            property = new WidgetProperty(this, attributes.getSequence(), attributes.getPropertyDescriptors());
          } else {
            property = new WidgetProperty(this, config);
          }

          property.setSorted(sorted);
        }

        return property;
      }

      if (adapter == IWorkbenchAdapter.class) {
        return this;
      }

      return null;
    }

    public Object[] getChildren(Object o) {
      return new Object[0];
    }

    public RMLDocument getDocument() {
      return document;
    }

    public SPOTSequence getElement() {
      return config;
    }

    public ImageDescriptor getImageDescriptor(Object object) {
      return null;
    }

    public String getLabel(Object o) {
      return config.spot_getClassShortName();
    }

    public Object getParent(Object o) {
      return null;
    }

    public iWidget getWidget() {
      if (((widget == null) || widget.isDisposed()) && (document != null)) {
        DesignPane pane = document.getDesignPane();

        if (pane != null) {
          widget = pane.getSelectedWidget();

          if (widget == null) {
            widget = pane.getGridWidget();
          }

          if (widget == null) {
            widget = pane.getRootWidget();
          }
        }

        if ((widget != null) && (widget.getLinkedData() != config)) {
          widget = null;
        }
      }

      return widget;
    }

    public boolean isShowAttributes() {
      return showingAttributes;
    }

    public boolean isSorted() {
      return sorted;
    }

    void handleReloadRequestEx(iSPOTElement e, Object value, String prop,boolean reload) {
      do {
        iWidget w = getWidget();

        if ((w != null) && (configs == null)) {
          if (value instanceof UIColor) {
            if ("foreground".equals(prop)) {
              w.setForeground((UIColor) value);
            } else if ("background".equals(prop)) {
              iPlatformPainter p = ColorUtils.getPainter((UIColor) value);

              w.setBackgroundPainter((iBackgroundPainter) p);

              if (p == null) {
                w.setBackground((UIColor) value);
              }
            }

            w.update();

            break;
          }

          if (value instanceof Font) {
            if ("font".equals(prop)) {
              w.setFont(UIFontHelper.getFont((Font) value));
              w.update();

              break;
            }
          }

          if ((value != null) && (prop != null) && (prop.length() > 1) && !prop.equals("reload")) {
            char c = prop.charAt(0);
            StringBuilder sb = new StringBuilder(prop.length() + 5);

            sb.append("set").append(Character.toUpperCase(c)).append(prop, 1, prop.length());

            if (value instanceof iSPOTElement) {
              value = w.expandString(value.toString(), false);
            }

            if (JavaHandler.setFunctionValueEx(w, sb.toString(), value)) {
              w.update();

              break;
            }
          }
        }
        if(reload) {
          document.updateDesignPane();
        }
      } while (false);
    }
  }

  static class UndoableOperation extends AbstractOperation {
    UndoableEdit edit;

    public UndoableOperation(String label, UndoableEdit e) {
      super(label);
      edit = e;
    }

    @Override
    public boolean canRedo() {
      return edit.canRedo();
    }

    @Override
    public boolean canUndo() {
      return edit.canUndo();
    }

    @Override
    public IStatus execute(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
      return Status.OK_STATUS;
    }

    @Override
    public IStatus redo(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
      edit.redo();

      RMLDocument doc = Studio.getSelectedDocument();

      if (doc != null) {
        doc.reload();
      }

      return Status.OK_STATUS;
    }

    @Override
    public IStatus undo(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
      edit.undo();

      RMLDocument doc = Studio.getSelectedDocument();

      if (doc != null) {
        doc.reload();
      }

      return Status.OK_STATUS;
    }
  }
}
