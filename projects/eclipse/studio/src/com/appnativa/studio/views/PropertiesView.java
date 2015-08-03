/*
 * @(#)PropertiesView.java
 *
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.studio.views;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySourceProvider;
import org.eclipse.ui.views.properties.PropertySheetEntry;
import org.eclipse.wb.swt.SWTResourceManager;

import com.appnativa.studio.DesignPane;
import com.appnativa.studio.Studio;
import com.appnativa.studio.RMLDocument;
import com.appnativa.studio.Utilities;
import com.appnativa.studio.RMLDocument.WidgetAdaptable;
import com.appnativa.studio.editors.MultiPageEditor;
import com.appnativa.studio.properties.PropertyEditors;
import com.appnativa.studio.properties.PropertySheetViewer;
import com.appnativa.studio.properties.SPOTInspector;
import com.appnativa.studio.properties.iPropertyDescriptor;
import com.appnativa.studio.properties.iPropertySheetEntry;
import com.appnativa.rare.spot.Margin;
import com.appnativa.rare.spot.Rectangle;
import com.appnativa.rare.spot.Widget;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.spot.SPOTSequence;
import com.appnativa.spot.iSPOTElement;

public class PropertiesView extends ViewPart implements ISelectionListener, PropertyChangeListener {
  public static final String  ID              = "com.appnativa.studio.views.PropertiesView";
  static PropertiesView       _instance;
  PropertyEditors             propertyEditors = new PropertyEditors();
  iWidget                     contextWidget;
  PropertySheetEntryEx        editorEntry;
  EventsAction                eventsAction;
  boolean                     horizontal;
  ISelection                  lastSelection;
  int                         tablePreferredWidth;
  private Composite           editorComposite;
  private boolean             editorFocusedLocked;
  private PropertySheetViewer propertySheetViewer;
  private SortAction          sortAction;
  private Composite           tableAndEditorComposite;
  private Label               widgetName;

  public PropertiesView() {
    _instance = this;
  }

  public static int calculatePreferredHeight(Control c) {
    Point p = c.computeSize(SWT.DEFAULT, SWT.DEFAULT, true);

    p.y = Math.min(p.y, 500);
    p.y = Math.max(p.y, 100);
    ((GridData) c.getLayoutData()).heightHint = p.y;

    return p.y;
  }

  /**
   * Create contents of the view part.
   *
   * @param parent
   */
  @Override
  public void createPartControl(Composite parent) {
    GridLayout gl_parent = new GridLayout(1, false);

    gl_parent.horizontalSpacing = 0;
    gl_parent.marginHeight = 0;
    gl_parent.marginWidth = 0;
    gl_parent.verticalSpacing = 0;
    parent.setLayout(gl_parent);
    widgetName = new Label(parent, SWT.NONE);
    tablePreferredWidth = calculatePreferredTableWidth(widgetName);
    widgetName.setAlignment(SWT.CENTER);
    widgetName.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
    widgetName.setFont(SWTResourceManager.getBoldFont(parent.getFont()));
    tableAndEditorComposite = new Composite(parent, SWT.NONE);
    tableAndEditorComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
    tableAndEditorComposite.setLayout(createGridLayout(false));
    propertySheetViewer = new PropertySheetViewer(tableAndEditorComposite, SWT.BORDER);
    propertySheetViewer.addSelectionChangedListener(new ISelectionChangedListener() {
      public void selectionChanged(SelectionChangedEvent event) {
        handleSelection((IStructuredSelection) event.getSelection());
      }
    });
    parent.addControlListener(new ControlListener() {
      @Override
      public void controlResized(ControlEvent e) {
        checkBounds(((Composite) e.widget).getSize());
      }

      @Override
      public void controlMoved(ControlEvent e) {
      }
    });

    Control propertyTable = propertySheetViewer.getControl();

    propertyTable.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
    propertySheetViewer.setRootEntry(new PropertySheetEntryEx());
    editorComposite = new Composite(tableAndEditorComposite, SWT.BORDER);
    editorComposite.setLayout(new FillLayout());
    editorComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
    createActions();
    initializeToolBar();
    initializeMenu();

    IWorkbenchPartSite site = getSite();

    if (site instanceof IViewSite) {
      IViewSite vSite = (IViewSite) site;
      IActionBars actionBars = vSite.getActionBars();

      if (actionBars == null) {
        return;
      }

      IStatusLineManager slm = actionBars.getStatusLineManager();

      propertySheetViewer.setStatusLineManager(slm);
    }
  }

  /*
   * (non-Javadoc) Method declared on IWorkbenchPart.
   */
  public void dispose() {
    // run super.
    super.dispose();

    if (_instance == this) {
      _instance = null;
    }

    getSite().getPage().removeSelectionListener(this);
  }

  /*
   * (non-Javadoc) Method declared on IViewPart.
   */
  public void init(IViewSite site) throws PartInitException {
    // site.getPage().addSelectionListener(this);
    super.init(site);
  }

  @Override
  public void propertyChange(PropertyChangeEvent evt) {
    PropertySheetEntryEx e = (PropertySheetEntryEx) evt.getSource();
    Object value = evt.getNewValue();

    e.setValues(new Object[] { value });
    e.valueChangedEx();
  }

  @Override
  public void selectionChanged(IWorkbenchPart part, ISelection selection) {
    if ((part == this) || (selection == null) || (selection == lastSelection)) {
      if (selection == null) {
        contextWidget = null;
      }

      return;
    }

    if (part.getSite().getId().equals(MultiPageEditor.class.getName())) {
      Object[] a = ((IStructuredSelection) selection).toArray();

      selectionChanged(a);
    }
  }

  public static void widgetSelectionChanged(WidgetAdaptable[] a) {
    if (_instance != null) {
      _instance.selectionChanged(a);
    }
  }

  public static void setEditorFocusedLocked(final boolean locked) {
    Runnable r = new Runnable() {
      @Override
      public void run() {
        PropertiesView pv = _instance;

        if (pv != null) {
          pv.editorFocusedLocked = locked;

          if (!locked) {
            pv.lastSelection = null;
            pv.editorEntry = null;
            // pv.hideEditor();
          }
        }
      }
    };

    Studio.runInSWTThread(r);
  }

  @Override
  public void setFocus() {
    widgetName.getParent().setFocus();
  }

  public iWidget getContextWidget() {
    return contextWidget;
  }

  public static PropertiesView getInstance() {
    return _instance;
  }

  public static boolean isEditorFocusedLocked() {
    PropertiesView pv = _instance;

    return (pv == null) ? false : pv.editorFocusedLocked;
  }

  void checkBounds(Point p) {
    if (p.x > p.y + 100) {
      if (!horizontal) {
        horizontal = true;
        tableAndEditorComposite.setLayout(createGridLayout(horizontal));

        GridData gd = (GridData) propertySheetViewer.getControl().getLayoutData();

        gd.grabExcessHorizontalSpace = false;
        gd.widthHint = tablePreferredWidth;
        tableAndEditorComposite.layout(true, true);
        widgetName.setAlignment(SWT.LEFT);
      }
    } else {
      if (horizontal) {
        horizontal = false;
        widgetName.setAlignment(SWT.CENTER);
        tableAndEditorComposite.setLayout(createGridLayout(horizontal));

        GridData gd = (GridData) propertySheetViewer.getControl().getLayoutData();

        gd.grabExcessHorizontalSpace = true;
        gd.widthHint = SWT.DEFAULT;
        tableAndEditorComposite.layout(true, true);
      }
    }
  }


  protected void handleSelection(IStructuredSelection sel) {
    if ((sel.size() > 0) && (sel.getFirstElement() instanceof PropertySheetEntryEx)) {
      final PropertySheetEntryEx e = (PropertySheetEntryEx) sel.getFirstElement();

      if (propertySheetViewer.getControl().isFocusControl() || editorFocusedLocked) {
        if ((e != editorEntry) || (propertyEditors.getActiveEditor() == null)) {
          showEditorForEntry(e);
        } else if (editorFocusedLocked) {
          propertyEditors.focusActiveEditor();
        }
      }
    } else {
      editorEntry = null;

      if (editorComposite != null) {
        hideEditor();
      }
    }
  }

 

  protected void hideEditor() {
    propertyEditors.disposeOfLastEditor();
    ((GridData) editorComposite.getLayoutData()).heightHint = 0;
    editorComposite.getParent().layout(true, true);
  }

  protected void selectionChanged(Object[] a) {
    contextWidget = null;
    editorEntry = null;

    if (!this.editorFocusedLocked) {
      this.propertySheetViewer.clearSelection();
      hideEditor();
    }

    if (a.length < 2) {
      String s = "";

      if (a.length == 1) {
        WidgetAdaptable wa = (WidgetAdaptable) a[0];

        contextWidget = wa.getWidget();

        Widget w = (Widget) wa.getElement();

        if (w != null) {
          s = w.name.getValue();

          if ((s == null) || (s.length() == 0)) {
            s = "(no-name)";
          }

          s += " - " + w.spot_getClassShortName();
          wa.setSorted(sortAction.isChecked());
          wa.setShowAttributes(eventsAction.isChecked());
        } else {
          a = new Object[0];
        }
      }

      widgetName.setText(s);
    } else {
      List<SPOTSequence> list = new ArrayList<SPOTSequence>(a.length);
      WidgetAdaptable wa = null;

      for (Object o : a) {
        wa = (WidgetAdaptable) o;

        if (contextWidget == null) {
          contextWidget = wa.getWidget();
        }

        list.add(wa.getElement());
      }

      SPOTSequence seq = SPOTInspector.getCommonBase(list);

      widgetName.setText("(multiple " + seq.spot_getClassShortName() + "s)");
      wa = new WidgetAdaptable(wa.getDocument(), list, seq);
      wa.setSorted(sortAction.isChecked());
      wa.setShowAttributes(eventsAction.isChecked());
      a = new Object[] { wa };
    }

    boolean visible = true;
    RMLDocument doc = Studio.getSelectedDocument();
    DesignPane pane = doc == null ? null : doc.getDesignPane();

    if (pane != null) {
      for (Object o : a) {
        WidgetAdaptable wa = (WidgetAdaptable) o;
        iWidget w = wa.getWidget();

        if (pane.isInLockedViewer(w)) {
          visible = false;

          break;
        }
      }
    } else {
      visible = false;
    }

    if ((editorComposite != null) && (editorComposite.isVisible() != visible)) {
      if (!visible) {
        hideEditor();
      }

      editorComposite.setVisible(visible);
    }

    propertySheetViewer.setInput(a);
    eventsAction.setEnabled(a.length > 0);
  }

  private int calculatePreferredTableWidth(Label label) {
    GC gc = new GC(label);
    int stringWidth = gc.stringExtent("useSharedBorderPopup").x * 2;

    gc.dispose();

    return stringWidth + 100;
  }

  /**
   * Create the actions.
   */
  private void createActions() {
    // Create the actions
  }

  private GridLayout createGridLayout(boolean horizontal) {
    GridLayout gl_parent = new GridLayout(horizontal ? 2 : 1, false);

    gl_parent.horizontalSpacing = 0;
    gl_parent.marginHeight = 0;
    gl_parent.marginWidth = 0;
    gl_parent.verticalSpacing = 0;

    return gl_parent;
  }

  /**
   * Initialize the menu.
   */
  private void initializeMenu() {
    // IMenuManager manager = getViewSite().getActionBars().getMenuManager();
  }

  /**
   * Initialize the toolbar.
   */
  private void initializeToolBar() {
    IToolBarManager tbm = getViewSite().getActionBars().getToolBarManager();

    tbm.add(sortAction = new SortAction());
    ;
    tbm.add(eventsAction = new EventsAction());
    ;
  }

  private void showEditorForEntry(PropertySheetEntryEx e) {
    if ((editorComposite != null) && !editorComposite.isVisible()) {
      return;
    }

    try {
      final IPropertyDescriptor d = e.getDescriptor();
      Control c = null;

      if (d instanceof iPropertyDescriptor) {
        iPropertyDescriptor pd = (iPropertyDescriptor) d;

        c = pd.showPropertyEditor(editorComposite, e, this, propertyEditors);

        if ((c == null) && (pd.getElement() != null)) {
          c = propertyEditors.showText(editorComposite, Utilities.toSDF(pd.getElement()));
        }
      } else {
        c = propertyEditors.showTextEditor(editorComposite, e, e.getValueAsString(), this);
      }

      propertyEditors.hideEditorPreview();

      if ((c != null) && (c.getLayoutData() == null)) {
        ((GridData) editorComposite.getLayoutData()).heightHint = calculatePreferredHeight(editorComposite);
      } else {
        ((GridData) editorComposite.getLayoutData()).heightHint = SWT.DEFAULT;
      }

      editorComposite.getParent().layout(true, true);
      propertySheetViewer.showSelection();

      if (propertySheetViewer.getControl().isFocusControl() || editorFocusedLocked) {
        propertyEditors.focusActiveEditor();
      }
      editorEntry = e;
    } catch (Throwable ex) {
      ex.printStackTrace();

      if (editorComposite != null) {
        Composite parent = editorComposite.getParent();

        try {
          editorComposite.dispose();
        } catch (Throwable ignore) {
        }

        editorComposite = new Composite(parent, SWT.BORDER);
        editorComposite.setLayout(new FillLayout());
        editorComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
      }
    }
  }

  public static class PropertySheetEntryEx extends PropertySheetEntry implements iPropertySheetEntry {
    String comparable;

    @Override
    public void valueChanged(PropertySheetEntry child) {
      super.valueChanged(child);
    }

    public void valueChangedEx() {
      if (getParent() instanceof PropertySheetEntryEx) {
        ((PropertySheetEntryEx) getParent()).valueChanged(this);
      }

      refreshFromRoot();
    }

    @Override
    public void setPropertySourceProvider(IPropertySourceProvider provider) {
      super.setPropertySourceProvider(provider);

      if (getDescriptor() instanceof iPropertyDescriptor) {
        iSPOTElement e = ((iPropertyDescriptor) getDescriptor()).getElement();
        iSPOTElement p = e.spot_getParent();

        if (p == null) {
          return;
        }

        if (p instanceof Rectangle) {
          String name = e.spot_getName();

          if ("x".equals(name)) {
            comparable = "1";
          } else if ("y".equals(name)) {
            comparable = "2";
          } else if ("width".equals(name)) {
            comparable = "3";
          } else if ("height".equals(name)) {
            comparable = "4";
          }
        } else if (p instanceof Margin) {
          String name = e.spot_getName();

          if ("top".equals(name)) {
            comparable = "1";
          } else if ("right".equals(name)) {
            comparable = "2";
          } else if ("bottom".equals(name)) {
            comparable = "3";
          } else if ("left".equals(name)) {
            comparable = "4";
          }
        }
      }
    }

    @Override
    public String getComparable() {
      return (comparable == null) ? getDisplayName() : comparable;
    }

    @Override
    public IPropertyDescriptor getDescriptor() {
      return super.getDescriptor();
    }

    @Override
    protected PropertySheetEntry createChildEntry() {
      return new PropertySheetEntryEx();
    }
  }

  class EventsAction extends Action implements IWorkbenchAction {
    private static final String ID = "com.appnativa.studio.EventsAction";

    public EventsAction() {
      super(Studio.getResourceAsString("Studio.text.events_properties"), AS_CHECK_BOX);
      setId(ID);
      this.setImageDescriptor(ImageDescriptor.createFromImage(Studio.getResourceAsIcon("Studio.icon.pane.events_attributes")));
      setEnabled(false);
    }

    public void dispose() {
    }

    public void run() {
      Object o = propertySheetViewer.getInput();

      if ((o instanceof Object[]) && ((Object[]) o).length > 0) {
        o = ((Object[]) o)[0];
      }

      if (o instanceof WidgetAdaptable) {
        ((WidgetAdaptable) o).setShowAttributes(isChecked());
      }

      propertySheetViewer.setInput(propertySheetViewer.getInput());
    }
  }

  class SortAction extends Action implements IWorkbenchAction {
    private static final String ID = "com.appnativa.studio.SortAction";

    public SortAction() {
      super(Studio.getResourceAsString("Studio.text.sort_properties"), AS_CHECK_BOX);
      setId(ID);
      this.setImageDescriptor(ImageDescriptor.createFromImage(Studio.getResourceAsIcon("Studio.icon.pane.sort_properties")));
    }

    public void dispose() {
    }

    public void run() {
      Object o = propertySheetViewer.getInput();

      if ((o instanceof Object[]) && ((Object[]) o).length > 0) {
        o = ((Object[]) o)[0];
      }

      if (o instanceof WidgetAdaptable) {
        ((WidgetAdaptable) o).setSorted(isChecked());
      }

      propertySheetViewer.setInput(propertySheetViewer.getInput());
    }
  }
}
