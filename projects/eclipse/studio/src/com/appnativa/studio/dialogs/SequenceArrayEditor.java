/*
 * @(#)SequenceArrayEditor.java
 *
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.studio.dialogs;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.wb.swt.ResourceManager;
import org.eclipse.wb.swt.SWTResourceManager;

import com.appnativa.rare.spot.GridCell;
import com.appnativa.rare.spot.Region;
import com.appnativa.rare.spot.Tab;
import com.appnativa.rare.spot.Widget;
import com.appnativa.spot.SPOTPrintableString;
import com.appnativa.spot.SPOTSequence;
import com.appnativa.spot.SPOTSet;
import com.appnativa.spot.iSPOTElement;
import com.appnativa.studio.FormChanger;
import com.appnativa.studio.RMLDocument;
import com.appnativa.studio.Studio;
import com.appnativa.studio.Utilities;
import com.appnativa.studio.properties.EditorHelper;
import com.appnativa.studio.properties.PropertyEditors;
import com.appnativa.studio.properties.PropertySheetViewer;
import com.appnativa.studio.properties.SequenceProperty;
import com.appnativa.studio.properties.iPropertyDescriptor;
import com.appnativa.studio.views.PropertiesView;
import com.appnativa.studio.views.PropertiesView.PropertySheetEntryEx;
import com.appnativa.util.CharArray;
import com.appnativa.util.MutableInteger;

public class SequenceArrayEditor extends Dialog implements PropertyChangeListener {
  PropertyEditors                         propertyEditors     = new PropertyEditors();
  protected boolean                       attributesShowing;
  protected iSPOTElement                  cfg;
  protected Button                        deleteItem;
  protected RMLDocument                   document;
  protected Group                         editorComposite;
  protected SPOTSet                       elements;
  protected Button                        moveDown;
  protected Button                        moveUp;
  protected ArrayList<SequencePropertyEx> properties;
  protected PropertySheetViewer           propertySheet;
  protected boolean                       single;
  protected Composite                     toolBar;
  private Button                          btnLockFocusOn;
  private Text                            elementText;
  private List                            elementsList;
  private String                          itemName;
  private ToolBar                         propertiesToolbar;
  private ToolItem                        tltmEventsaction;
  private ToolItem                        tltmSortaction;
  private String                          titleProperty       = "title";
  private String                          titlePropertyPrompt = "Title:";
  SPOTSet originalSet;
  /**
   * Create the dialog.
   *
   * @param parentShell
   */
  public SequenceArrayEditor(Shell parentShell, iSPOTElement e, String itemName,boolean copy) {
    super(parentShell);
    setShellStyle(SWT.SHELL_TRIM | SWT.APPLICATION_MODAL | SWT.RESIZE);
    this.document = Studio.getSelectedDocument();

    this.cfg = e;
    if (itemName == null) {
      itemName = e.spot_getName();
    }
    if (!(e instanceof com.appnativa.spot.SPOTSet)) {
      this.elements = com.appnativa.spot.SPOTSet.spot_toSet("no_name", e);
      this.single = true;
    } else {
      this.elements = (SPOTSet) e;
      originalSet=this.elements;
      if(copy) {
        this.elements=(SPOTSet) this.elements.clone();
      }
      
    }

    iSPOTElement element = this.elements.spot_getArrayClassInstance();
    if (itemName == null) {
      itemName = element.spot_getClassShortName();

      String s = element.spot_getName();

      if ((s != null) && !s.equals("no_name") && (s.indexOf('.') == -1)) {
        itemName += " (" + s + ")";
      }
      this.itemName = itemName;
    } else {
      this.itemName = "<" + itemName + ">";
    }

    int len = this.elements.size();

    properties = new ArrayList<SequencePropertyEx>(len);

    for (int i = 0; i < len; i++) {
      SPOTSequence seq = (SPOTSequence) this.elements.getEx(i);
      Object o = seq.spot_getLinkedData();

      if (o instanceof SequenceProperty) {
        properties.add(new SequencePropertyEx(seq, ((SequenceProperty) o).getPropertyDescriptors(), null));
      } else {
        properties.add(new SequencePropertyEx(seq));
      }
    }
  }

  public void addElement() {
    SPOTSequence element = (SPOTSequence) this.elements.spot_getArrayClassInstance();
    iSPOTElement e = element.spot_elementFor(titleProperty);
    String s = titlePropertyPrompt;
    element.spot_setParent(elements);
    if (e == null) {
      e = element.spot_elementFor("value");
      s = "Value:";
    }

    if ((e != null) && (e instanceof com.appnativa.spot.SPOTPrintableString)) {
      s = Studio.prompt(s);

      if (s != null) {
        e.spot_setValue(s);
      }
    } else {
      s = null;
    }

    final int len = properties.size();
    SequencePropertyEx p = new SequencePropertyEx(element);

    properties.add(p);
    elementsList.add(itemName);
    elementsList.select(len);
    itemSelected(true);
    FormChanger.addEditForElementInsert(document.designPane, originalSet, properties.size()-1,element);
  }

  @Override
  public void propertyChange(PropertyChangeEvent evt) {
    PropertySheetEntryEx e = (PropertySheetEntryEx) evt.getSource();
    Object value = evt.getNewValue();
    e.setValues(new Object[] { value });
    e.valueChangedEx();
    if (value instanceof SPOTPrintableString) {
      SPOTPrintableString ps = (SPOTPrintableString) value;
      if ("title".equals(ps.spot_getName())) {
        String name = ps.getValue();
        if (name == null || name.trim().length() == 0) {
          name = itemName;
        }
        int i = elementsList.getSelectionIndex();
        if (i != -1) {
          elementsList.setItem(i, name);
        }
      }
    }
  }

  public iSPOTElement getSPOTElement() {
    if (single) {
      return elements.getEx(0);
    } else {
      return elements;
    }
  }

  void deleteElement() {
    int i = elementsList.getSelectionIndex();

    if (i > -1) {
      properties.remove(i);
      elementsList.remove(i);

      if (i >= properties.size()) {
        i = properties.size() - 1;
      } else {
        i--;
      }

      if (i < 0) {
        i = 0;
      }

      if (i < properties.size()) {
        elementsList.select(i);
      }

      itemSelected(true);
      FormChanger.addEditForElementDelete(document.designPane, originalSet, i);
    }
  }

  void moveDown() {
    final int len = properties.size();
    int i = elementsList.getSelectionIndex();

    if (i < len - 1) {
      SequencePropertyEx p = properties.get(i + 1);

      properties.set(i + 1, properties.get(i));
      properties.set(i, p);
      String s = elementsList.getItem(i);
      elementsList.setItem(i, elementsList.getItem(i + 1));
      elementsList.setItem(i + 1, s);
      elementsList.select(i + 1);
      itemSelected(true);
      FormChanger.addEditForElementSwap(document.designPane, originalSet, i,i+1);
    }
  }

  void moveUp() {
    int i = elementsList.getSelectionIndex();

    if (i > 0) {
      SequencePropertyEx p = properties.get(i - 1);

      properties.set(i - 1, properties.get(i));
      properties.set(i, p);
      String s = elementsList.getItem(i);
      elementsList.setItem(i, elementsList.getItem(i - 1));
      elementsList.setItem(i - 1, s);
      elementsList.select(i - 1);
      itemSelected(true);
      FormChanger.addEditForElementSwap(document.designPane, originalSet, i,i-1);
    }
  }

  void propertyModified(iSPOTElement e, Object value) {
    String name = e.spot_getName();
    boolean single = elementsList.getSelectionCount() == 1;

    try {
      if (this.attributesShowing) {
        if (value instanceof iSPOTElement) {
          value = Utilities.toString((iSPOTElement) value);
        }

        if (single) {
          FormChanger.changeAttribute(document.designPane, cfg, name, value);
        } else {
          FormChanger.changeAttributes(document.designPane, getSelectedConfigs(), name, value);
        }
      } else if (!(value instanceof SequenceProperty)) {
        MutableInteger changed = new com.appnativa.util.MutableInteger(0);

        if (single) {
          // check for reference objects and fix them;
          com.appnativa.studio.Utilities.fixSPOTReference(getSelectedConfig(), e);
          FormChanger.changeValue(document.designPane, e, value, false, false, changed);
        } else {
          FormChanger.changeValues(document.designPane, getSelectedConfigs(), e, value, changed);
        }

        if (changed.getValue() > 0) {
          if ("borders".equals(e.spot_getName()) && (value instanceof SPOTSet) && ((SPOTSet) value).size() == 0) {
            clearConfigBorders();
          }
        }
      }

      itemSelected(false);
    } catch (Throwable err) {
      Studio.showError(err);
    }
  }

  SPOTSequence getSelectedConfig() {
    int n = elementsList.getSelectionIndex();

    return (n < 0) ? null : properties.get(n).getSequence();
  }

  ArrayList<SPOTSequence> getSelectedConfigs() {
    int sels[] = elementsList.getSelectionIndices();

    if ((sels != null) && (sels.length > 0)) {
      ArrayList<SPOTSequence> list = new ArrayList<SPOTSequence>(sels.length);

      for (int n : sels) {
        list.add(properties.get(n).getSequence());
      }

      return list;
    }

    return null;
  }

  protected void clearConfigBorders() {
    int sels[] = elementsList.getSelectionIndices();

    if ((sels != null) && (sels.length > 0)) {
      for (int n : sels) {
        SPOTSequence config = properties.get(n).getSequence();

        if (config instanceof Widget) {
          ((Widget) config).setBorders(null);
        } else if (config instanceof GridCell) {
          ((GridCell) config).setBorders(null);
        }
      }
    }
  }

  /**
   * Create contents of the button bar.
   *
   * @param parent
   */
  @Override
  protected void createButtonsForButtonBar(Composite parent) {
    createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, false);
    createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
  }

  /**
   * Create contents of the dialog.
   *
   * @param parent
   */
  @SuppressWarnings("resource")
  @Override
  protected Control createDialogArea(Composite parent) {
    String name = cfg.spot_getName();

    if ((name != null) && (name.indexOf('.') == -1)) {
      String s = String.format(Studio.getResourceAsString("Studio.text.design.edit"), name);
      CharArray ca = new CharArray(s);

      ca.toTitleCase();
      this.getShell().setText(ca.toString());
    }

    Composite container = (Composite) super.createDialogArea(parent);

    container.setLayout(new FillLayout(SWT.HORIZONTAL));

    Composite formComposite = new Composite(container, SWT.NONE);

    formComposite.setLayout(new GridLayout(2, false));

    Composite listComposite = new Composite(formComposite, SWT.NONE);
    GridData gd_listComposite = new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1);

    gd_listComposite.heightHint = 200;
    listComposite.setLayoutData(gd_listComposite);
    listComposite.setLayout(new GridLayout(2, false));
    elementsList = new List(listComposite, SWT.BORDER | SWT.SINGLE);

    GridData gd_elementsList = new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1);

    gd_elementsList.minimumHeight = 200;
    gd_elementsList.widthHint = 200;
    elementsList.setLayoutData(gd_elementsList);
    elementsList.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        itemSelected(true);
      }

      @Override
      public void widgetDefaultSelected(SelectionEvent e) {
      }
    });
    elementText = new Text(listComposite, SWT.BORDER | SWT.READ_ONLY | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL);
    elementText.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
    elementText.setEditable(false);
    elementText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
    toolBar = new Composite(listComposite, SWT.NONE);
    toolBar.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
    toolBar.setLayout(new FillLayout(SWT.HORIZONTAL));

    Button newItem = new Button(toolBar, SWT.PUSH);

    newItem.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        addElement();
      }
    });
    newItem.setText("New");
    deleteItem = new Button(toolBar, SWT.PUSH);
    deleteItem.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        deleteElement();
      }
    });
    deleteItem.setText("Delete");
    moveUp = new Button(toolBar, SWT.PUSH);
    moveUp.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        moveUp();
      }
    });
    moveUp.setText("Move Up");
    moveDown = new Button(toolBar, SWT.PUSH);
    moveDown.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        moveDown();
      }
    });
    moveDown.setText("Move Dn");

    Label label = new Label(formComposite, SWT.SEPARATOR | SWT.HORIZONTAL);

    label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1));
    propertiesToolbar = new ToolBar(formComposite, SWT.FLAT | SWT.RIGHT);
    propertiesToolbar.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1));
    tltmEventsaction = new ToolItem(propertiesToolbar, SWT.CHECK);
    tltmEventsaction.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        toggleShowEvents();
      }
    });
    tltmEventsaction.setImage(ResourceManager.getPluginImage("com.appnativa.studio", "icons/pane_events_attributes.png"));
    tltmSortaction = new ToolItem(propertiesToolbar, SWT.CHECK);
    tltmSortaction.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        Object o = propertySheet.getInput();

        if ((o instanceof Object[]) && ((Object[]) o).length > 0) {
          o = ((Object[]) o)[0];
        }

        if (o instanceof SequencePropertyEx) {
          ((SequencePropertyEx) o).setSorted(tltmSortaction.getSelection());
        }

        propertySheet.setInput(propertySheet.getInput());
      }
    });
    tltmSortaction.setImage(ResourceManager.getPluginImage("com.appnativa.studio", "icons/pane_sort.png"));
    btnLockFocusOn = new Button(formComposite, SWT.CHECK);
    btnLockFocusOn.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
      }
    });
    btnLockFocusOn.setSelection(true);
    btnLockFocusOn.setText("Lock focus on editor");
    propertySheet = new PropertySheetViewer(formComposite, SWT.BORDER);
    propertySheet.addSelectionChangedListener(new ISelectionChangedListener() {
      public void selectionChanged(SelectionChangedEvent event) {
        handleSelection((IStructuredSelection) event.getSelection());
      }
    });

    Control control = propertySheet.getControl();
    GridData gd_control = new GridData(SWT.FILL, SWT.FILL, false, true, 1, 1);

    gd_control.widthHint = 250;
    control.setLayoutData(gd_control);
    propertySheet.setRootEntry(new PropertySheetEntryEx());
    editorComposite = new Group(formComposite, SWT.NONE);
    editorComposite.setLayout(new FillLayout(SWT.HORIZONTAL));

    GridData gd_editorComposite = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);

    gd_editorComposite.minimumWidth = 400;
    editorComposite.setLayoutData(gd_editorComposite);

    int len = properties.size();
    SequenceProperty p = (len == 0) ? null : properties.get(0);

    if (p != null) {
      if (p.getSequence() instanceof Region && !(p.getSequence() instanceof Tab) ) {
        toolBar.setVisible(false);
      }

      for (int i = 0; i < len; i++) {
        SPOTSequence seq = properties.get(i).getSequence();
        iSPOTElement e = seq.spot_elementFor(titleProperty);
        name = e == null ? null : e.spot_stringValue();

        if ((name == null) || (name.trim().length() == 0)) {
          name = itemName;
        }

        elementsList.add(name);
      }
    }
    deleteItem.setEnabled(false);
    moveDown.setEnabled(false);
    moveUp.setEnabled(false);

    if (len > 0) {
      elementsList.select(0);
      itemSelected(true);

      if (this.single) {
        toolBar.setVisible(false);
      }
    }

    if (single) {
      listComposite.setVisible(false);
      ((GridData) listComposite.getLayoutData()).exclude = true;
    }

    return container;
  }

  protected void handleSelection(IStructuredSelection sel) {
    if ((sel.size() > 0) && (sel.getFirstElement() instanceof PropertySheetEntryEx)) {
      final PropertySheetEntryEx e = (PropertySheetEntryEx) sel.getFirstElement();

      showEditorForEntry(e);
    } else {
      if (editorComposite != null) {
        editorComposite.getParent().layout(true, true);
        propertyEditors.disposeOfLastEditor();
      }
    }
  }

  protected void itemSelected(boolean updateSheet) {
    int sels[] = elementsList.getSelectionIndices();

    if ((sels != null) && (sels.length > 0)) {
      SequencePropertyEx p = properties.get(sels[0]);

      if (updateSheet) {
        propertySheet.setInput(new Object[] { p });
      }

      tltmEventsaction.setEnabled(p.getSequence().spot_hasDefinedAttributes());

      if (sels.length > 1) {
        elementText.setText("<" + sels.length + "" + itemName + ">");
      } else {
        elementText.setText(p.getSequence().toSDF());
      }
      moveDown.setEnabled(sels[sels.length-1]<(properties.size()-1));
      moveUp.setEnabled(sels[0]>0);
      deleteItem.setEnabled(true);
    } else {
      moveDown.setEnabled(false);
      moveUp.setEnabled(false);
      deleteItem.setEnabled(false);
      propertySheet.setInput(new Object[] {});
      elementText.setText("");
    }
  }

  protected void okPressed() {
    int len = properties.size();

    elements.clear();

    for (int i = 0; i < len; i++) {
      elements.add(properties.get(i).getSequence());
    }

    super.okPressed();
  }

  protected void toggleShowEvents() {
    Object o = propertySheet.getInput();

    if ((o instanceof Object[]) && ((Object[]) o).length > 0) {
      o = ((Object[]) o)[0];
    }

    if (o instanceof SequencePropertyEx) {
      attributesShowing = tltmEventsaction.getSelection();

      SequencePropertyEx other = ((SequencePropertyEx) o).getOtherProperty();

      if (o != null) {
        propertySheet.setInput(new Object[] { other });
      }
    }
  }

  /**
   * Return the initial size of the dialog.
   */
  @Override
  protected Point getInitialSize() {
    return new Point(686, 748);
  }

  private void showEditorForEntry(PropertySheetEntryEx e) {
    try {
      final IPropertyDescriptor d = e.getDescriptor();
      Control c = null;

      if (d instanceof iPropertyDescriptor) {
        iPropertyDescriptor pd = (iPropertyDescriptor) d;

        c = pd.showPropertyEditor(editorComposite, e, this, propertyEditors);

        if ((c == null) && (pd.getElement() != null)) {
          c = propertyEditors.showText(editorComposite, pd.getElement().toString());
        }
      } else {
        c = propertyEditors.showTextEditor(editorComposite, e, e.getValueAsString(), this);
      }

      if ((c != null) && (c.getLayoutData() == null)) {
        ((GridData) editorComposite.getLayoutData()).heightHint = PropertiesView.calculatePreferredHeight(editorComposite);
      } else {
        ((GridData) editorComposite.getLayoutData()).heightHint = SWT.DEFAULT;
      }

      getShell().layout(true, true);

      if (btnLockFocusOn.getSelection()) {
        propertyEditors.focusActiveEditor();
      }
    } catch (Throwable ex) {
      ex.printStackTrace();

      if (editorComposite != null) {
        Composite parent = editorComposite.getParent();

        try {
          editorComposite.dispose();
        } catch (Throwable ignore) {
        }

        editorComposite = new Group(parent, SWT.NONE);
        editorComposite.setLayout(new FillLayout());
        editorComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
      }
    }
  }

  public String getTitleProperty() {
    return titleProperty;
  }

  @SuppressWarnings("resource")
  public void setTitleProperty(String titleProperty) {
    this.titleProperty = titleProperty;
    CharArray ca = new CharArray(titleProperty);
    ca.toTitleCase();
    ca.append(':');
    this.titlePropertyPrompt = ca.toString();
  }

  class EventsAction extends Action {
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
    }
  }

  class SequencePropertyEx extends SequenceProperty {
    public SequencePropertyEx otherPropery;

    public SequencePropertyEx(SPOTSequence element) {
      super(element);
    }

    public SequencePropertyEx(SPOTSequence element, IPropertyDescriptor[] descriptors, SequencePropertyEx other) {
      super(element, descriptors);
      this.otherPropery = other;
    }

    @Override
    public void dispose() {
      super.dispose();

      if (otherPropery != null) {
        otherPropery.dispose();
        otherPropery = null;
      }
    }

    public SequencePropertyEx getOtherProperty() {
      if (otherPropery == null) {
        SequenceProperty sp = EditorHelper.getAttributesSequenceProperty(getSequence(), null);

        otherPropery = new SequencePropertyEx(sp.getSequence(), sp.getPropertyDescriptors(), this);
      }

      return otherPropery;
    }

    @Override
    protected void valueChanged(iSPOTElement e, Object value) {
      propertyModified(e, value);
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
    }
  }
}
