package com.appnativa.studio.composite;

import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.events.TraverseEvent;

public class AttributesComposite extends Composite {
  private Text editorText;
  private List attributesList;
  private List editorList;
  private Composite editorStack;
  private Map attributes;
  private String selectedKey;

  /**
   * Create the composite.
   * @param parent
   * @param style
   */
  public AttributesComposite(Composite parent, int style) {
    super(parent, style);
    setLayout(new GridLayout(1, false));
    
    Label lblAttributesevents = new Label(this, SWT.NONE);
    lblAttributesevents.setText("Attributes/Events");
    
    attributesList = new List(this, SWT.BORDER | SWT.V_SCROLL);
    attributesList.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        itemSelected(attributesList.getSelectionIndex());
      }
      @Override
      public void widgetDefaultSelected(SelectionEvent e) {
        itemSelected(attributesList.getSelectionIndex());
      }
    });
    attributesList.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
    
    editorStack = new Composite(this, SWT.NONE);
    GridData gd_editorStack = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1);
    gd_editorStack.heightHint = 150;
    editorStack.setLayoutData(gd_editorStack);
    editorStack.setLayout(new StackLayout());
    
    editorList = new List(editorStack, SWT.BORDER);
    editorList.addTraverseListener(new TraverseListener() {
      public void keyTraversed(TraverseEvent e) {
        
      }
    });
    editorList.addFocusListener(new FocusAdapter() {
      @Override
      public void focusLost(FocusEvent e) {
      }
    });
    
    editorText = new Text(editorStack, SWT.BORDER | SWT.WRAP | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);

  }

  @Override
  protected void checkSubclass() {
    // Disable the check that prevents subclassing of SWT components
  }
  protected void itemSelected(int pos) {
    if(selectedKey!=null) {
      String s=editorText.getText();
      s=s.trim();
      attributes.put(selectedKey, s);
    }
    String text=null;;
    String s=attributesList.getItem(pos);
    Object value=attributes.get(s);
    if(value!=null) {
      text=value.toString();
    }
    selectedKey=s;
    editorText.setText(text==null ? "" : text);
    checkForListItems();
  }
  
  private void checkForListItems() {
    // TODO Auto-generated method stub
    
  }

  public void setAttributes(Map attributes) {
    attributesList.removeAll();
    this.attributes=attributes;
    for(Object o:attributes.keySet()) {
      attributesList.add(o.toString());
    }
  }

}
