/*
 * @(#)ToolboxView.java
 * 
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.studio.views;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeNode;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.DrillDownAdapter;
import org.eclipse.ui.part.ViewPart;

import com.appnativa.studio.Activator;
import com.appnativa.studio.RMLDocument;
import com.appnativa.studio.Studio;
import com.appnativa.studio.editors.MultiPageEditor;

/**
 * This sample class demonstrates how to plug-in a new workbench view. The view
 * shows data obtained from the model. The sample creates a dummy model on the
 * fly, but a real implementation would connect to the model available either in
 * this or another plug-in (e.g. the workspace). The view is connected to the
 * model using a content provider.
 * <p>
 * The view uses a label provider to define how model objects should be
 * presented in the view. Each view can present the same model objects using
 * different labels and icons, if needed. Alternatively, a single label provider
 * can be shared between views in order to ensure that objects of the same type
 * are presented in the same way everywhere.
 * <p>
 */
public class ToolboxView extends ViewPart {

  /**
   * The ID of the view as specified by the extension.
   */
  public static final String ID = "com.appnativa.studio.views.ToolboxView";
  private Action             doubleClickAction;
  private DrillDownAdapter   drillDownAdapter;
  private TreeViewer         viewer;

  /**
   * The constructor.
   */
  public ToolboxView() {}

  /**
   * This is a callback that will allow us to create the viewer and initialize
   * it.
   */
  public void createPartControl(Composite parent) {
    viewer           = new TreeViewer(parent, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL);
    drillDownAdapter = new DrillDownAdapter(viewer);
    viewer.setContentProvider(new ViewContentProvider());
    viewer.setLabelProvider(new ViewLabelProvider());
    viewer.setInput(getViewSite());
    // Create the help context id for the viewer's control
    PlatformUI.getWorkbench().getHelpSystem().setHelp(viewer.getControl(), "com.appnativa.studio.viewer");
    makeActions();
    hookContextMenu();
    hookDoubleClickAction();
    contributeToActionBars();

    if (viewer.getTree().getItemCount() > 2) {
      Object item = viewer.getTree().getItem(1);

      viewer.setExpandedState(item, true);
      viewer.refresh();
    }
  }

  /**
   * Passing the focus request to the viewer's control.
   */
  public void setFocus() {
    viewer.getControl().setFocus();
  }

  private void contributeToActionBars() {
    IActionBars bars = getViewSite().getActionBars();

    fillLocalPullDown(bars.getMenuManager());
    fillLocalToolBar(bars.getToolBarManager());
  }

  private void fillContextMenu(IMenuManager manager) {
    drillDownAdapter.addNavigationActions(manager);
    // Other plug-ins can contribute there actions here
    manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
  }

  private void fillLocalPullDown(IMenuManager manager) {
  }

  private void fillLocalToolBar(IToolBarManager manager) {
    drillDownAdapter.addNavigationActions(manager);
  }

  private void hookContextMenu() {
    MenuManager menuMgr = new MenuManager("#PopupMenu");

    menuMgr.setRemoveAllWhenShown(true);
    menuMgr.addMenuListener(new IMenuListener() {
      public void menuAboutToShow(IMenuManager manager) {
        ToolboxView.this.fillContextMenu(manager);
      }
    });

    Menu menu = menuMgr.createContextMenu(viewer.getControl());

    viewer.getControl().setMenu(menu);
    getSite().registerContextMenu(menuMgr, viewer);
  }

  private void hookDoubleClickAction() {
    viewer.addDoubleClickListener(new IDoubleClickListener() {
      public void doubleClick(DoubleClickEvent event) {
        doubleClickAction.run();
      }
    });
  }

  private void makeActions() {
    doubleClickAction = new Action() {
      public void run() {
        RMLDocument doc = Studio.getSelectedDocument();

        if (doc != null) {
          doc.handleToolDoubleClicked();
        }
      }
    };
  }

  public static class Tool {
    public String id;
    public String name;
    boolean       draggable = true;

    public Tool(String name, String id, boolean draggable) {
      this.name      = name;
      this.id        = id;
      this.draggable = draggable;
    }

    Tool(String name, String id) {
      this.name = name;
      this.id   = id;
    }
  }


  class ViewContentProvider implements IStructuredContentProvider, ITreeContentProvider, ISelectionChangedListener {
    private TreeNode invisibleRoot;

    public void dispose() {}

    public void inputChanged(Viewer v, Object oldInput, Object newInput) {}

    @Override
    public void selectionChanged(SelectionChangedEvent event) {
      IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
      IEditorPart    p    = (page == null)
                            ? null
                            : page.getActiveEditor();

      if (!(p instanceof MultiPageEditor)) {
        return;
      }

      ISelection sel = event.getSelection();

      if (sel instanceof IStructuredSelection) {
        if (sel.isEmpty()) {
          ((MultiPageEditor) p).resetCursors();
        } else {
          IStructuredSelection ssel = (IStructuredSelection) sel;
          Tool                 tool = (Tool) ((TreeNode) ssel.getFirstElement()).getValue();

          if (tool.draggable) {
            ((MultiPageEditor) p).toolboxItemSelected(tool);
          }
        }
      }
    }

    public Object[] getChildren(Object parent) {
      if (parent instanceof TreeNode) {
        return ((TreeNode) parent).getChildren();
      }

      return new Object[0];
    }

    public Object[] getElements(Object parent) {
      if (parent.equals(getViewSite())) {
        if (invisibleRoot == null) {
          initialize();
        }

        return invisibleRoot.getChildren();
      }

      return getChildren(parent);
    }

    public Object getParent(Object child) {
      if (child instanceof TreeNode) {
        return ((TreeNode) child).getParent();
      }

      return null;
    }

    public boolean hasChildren(Object parent) {
      if (parent instanceof TreeNode) {
        return ((TreeNode) parent).hasChildren();
      }

      return false;
    }

    /*
     * We will set up a dummy model to initialize tree heararchy. In a real
     * code, you will connect to a real model and expose its hierarchy.
     */
    private void initialize() {
      invisibleRoot = new TreeNode("");

      TreeNode pointerNode = new TreeNode(new Tool("Pointer", "Studio.icon.toolbox.pointer"));

      try {
        TreeNode            rootNode = invisibleRoot;
        InputStream         in       = Activator.class.getResourceAsStream("toolbox.txt");
        BufferedReader      r        = new BufferedReader(new InputStreamReader(in), 4096);
        String              line;
        TreeNode            currentNode  = null;
        ArrayList<TreeNode> children     = new ArrayList<TreeNode>();
        ArrayList<TreeNode> rootChildren = new ArrayList<TreeNode>(3);

        while((line = r.readLine()) != null) {
          line = line.trim();

          if (line.length() == 0 || line.startsWith("//")) {
            continue;
          }

          if (line.startsWith("[")) {
            if (currentNode != null) {
              currentNode.setChildren(children.toArray(new TreeNode[children.size()]));
            }

            children.clear();
            line        = line.substring(1, line.length() - 1);
            currentNode = new TreeNode(new Tool(line, "Studio.icon.openIcon", false));
            currentNode.setParent(rootNode);
            children.add(pointerNode);
            rootChildren.add(currentNode);

            continue;
          }

          if (currentNode != null) {
            int n = line.indexOf('^');

            if (n != -1) {
              children.add(new TreeNode(new Tool(line.substring(0, n), line.substring(n + 1))));
            }
          }
        }

        if (currentNode != null) {
          currentNode.setChildren(children.toArray(new TreeNode[children.size()]));
        }

        rootNode.setChildren(rootChildren.toArray(new TreeNode[rootChildren.size()]));
        viewer.addSelectionChangedListener(this);
      } catch(Exception e) {
        e.printStackTrace();
      }
    }
  }


  class ViewLabelProvider extends LabelProvider {
    @Override
    public Image getImage(Object element) {
      TreeNode node = (TreeNode) element;

      return Studio.getResourceAsIcon(((Tool) node.getValue()).id);
    }

    @Override
    public String getText(Object element) {
      return ((Tool) ((TreeNode) element).getValue()).name;
    }
  }
}
