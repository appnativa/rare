/*
 * @(#)ListEditorComposite.java
 *
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.studio.composite;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Reader;
import java.io.StringReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.wb.swt.ResourceManager;

import com.appnativa.rare.exception.ApplicationException;
import com.appnativa.rare.platform.swing.ui.util.Java2DUtils;
import com.appnativa.rare.platform.swing.ui.util.SwingGraphics;
import com.appnativa.rare.spot.GridCell.CBorder;
import com.appnativa.rare.ui.ColorUtils;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.spot.SDFNode;
import com.appnativa.spot.SPOTInteger;
import com.appnativa.spot.SPOTPrintableString;
import com.appnativa.spot.SPOTReal;
import com.appnativa.spot.SPOTSequence;
import com.appnativa.spot.SPOTSet;
import com.appnativa.spot.aSPOTElement;
import com.appnativa.spot.iSPOTElement;
import com.appnativa.studio.Studio;
import com.appnativa.studio.Utilities;
import com.appnativa.studio.dialogs.BackgroundColorDialog;
import com.appnativa.studio.dialogs.BordersDialog;
import com.appnativa.studio.dialogs.IconOrImageChooserDialog;
import com.appnativa.studio.dialogs.InsetsDialog;
import com.appnativa.studio.dialogs.SequenceArrayEditor;
import com.appnativa.studio.dialogs.SimpleHelpDialog;
import com.appnativa.studio.properties.EditorHelper;
import com.appnativa.studio.properties.SequenceProperty;
import com.appnativa.util.CharArray;
import com.appnativa.util.CharScanner;
import com.appnativa.util.Helper;
import com.appnativa.util.SNumber;

public class BaseEditorComposite extends Composite implements TraverseListener, FocusListener, SelectionListener {
  static HashMap<String, String> attributeHelp = new HashMap<String, String>();

  static {
    attributeHelp.put("os", "help/os_attribute.txt");
  }

  protected boolean                fullSizePreview = true;
  protected List                   attributeEditorList;
  protected Composite              attributeEditorStack;
  protected Text                   attributeEditorText;
  protected List                   attributeList;
  protected iSPOTElement           element;
  protected Object                 eventSource;
  protected Group                  grpAttributes;
  protected List                   listWidget;
  protected Object                 oldValue;
  protected BufferedImage          previewBufferedImage;
  protected int                    previewHeight;
  protected Image                  previewImage;
  protected int                    previewWidth;
  protected PropertyChangeListener propertyChangeListener;
  protected int                    selectedAttribute;
  protected Text                   textWidget;
  private boolean                  cloneItem          = true;
  private boolean                  reselectOnEnterKey = true;;
  private ToolItem                 tltmClear;
  private ToolItem                 tltmDialog;
  private ToolItem                 tltmHelp;
  private ToolBar                  toolBar;

  /**
   * Create the composite.
   * @param parent
   * @param style
   */
  public BaseEditorComposite(Composite parent, int style) {
    super(parent, style | SWT.NO_FOCUS);

    GridLayout gridLayout = new GridLayout(1, false);

    setLayout(gridLayout);
  }

  @Override
  public void dispose() {
    super.dispose();

    if (previewImage != null) {
      previewImage.dispose();
      previewImage = null;
    }
  }

  public void focusEditWidget() {
    if ((textWidget != null) && textWidget.isVisible() && textWidget.isEnabled()) {
      if(textWidget.getEditable()) {
        textWidget.selectAll();
        textWidget.forceFocus();
      }
    } else if ((listWidget != null) && listWidget.isVisible() && listWidget.isEnabled()) {
      listWidget.forceFocus();
    } else {
      forceFocus();
    }
  }

  @Override
  public void focusGained(FocusEvent e) {}

  @Override
  public void focusLost(FocusEvent e) {
    valueChanged(e.widget);
  }

  public void hidePreview() {}

  @Override
  public void keyTraversed(TraverseEvent e) {
    if (e.detail == SWT.TRAVERSE_RETURN) {
      valueChanged(e.widget);

      if (reselectOnEnterKey && (e.widget instanceof Text)) {
        ((Text) e.widget).selectAll();
      }
    } else if (e.detail == SWT.TRAVERSE_ESCAPE) {
      resetValue(e.widget);
    }
  }

  @Override
  public void widgetDefaultSelected(SelectionEvent e) {
    valueChanged(e.widget);
  }

  @Override
  public void widgetSelected(SelectionEvent e) {
    if (e.widget == tltmClear) {
      if ((attributeEditorText.getText().length() > 0) || (attributeEditorList.getSelectionCount() > 0)) {
        attributeEditorText.setText("");
        attributeEditorList.deselectAll();

        if (attributeEditorList.isVisible()) {
          valueChanged(attributeEditorList);
        } else {
          valueChanged(attributeEditorText);
        }
      }
    } else if (e.widget == tltmHelp) {
      int n = selectedAttribute;

      if (n != -1) {
        String name = attributeList.getItem(n).toLowerCase(Locale.US);
        String file = attributeHelp.get(name);

        if (file != null) {
          SimpleHelpDialog.showHelp(getShell(), file);
        }
      }
    } else if (e.widget == tltmDialog) {
      int n = selectedAttribute;

      if (n != -1) {
        String name   = attributeList.getItem(n).toLowerCase(Locale.US);
        String editor = EditorHelper.getAttributeEditorType(name);

        if ("gradient".equals(editor)) {
          String s = getBackgroundColor(getShell(), element.spot_getAttribute(name));

          if (s != null) {
            attributeEditorText.setText(s);
            valueChanged(attributeEditorText);
          }
        } else if ("icon".equals(editor) || "image".equals(editor)) {
          String s = getIcon(getShell(), element.spot_getAttribute(name), "image".equals(editor));

          if (s != null) {
            attributeEditorText.setText(s);
            valueChanged(attributeEditorText);
          }
        } else if ("insets".equals(editor)) {
          String s = getInsets(getShell(), element.spot_getAttribute(name));

          if (s != null) {
            attributeEditorText.setText(s);
            valueChanged(attributeEditorText);
          }
        }
      }
    } else if (e.widget == attributeList) {
      int n = attributeList.getSelectionIndex();

      if (n == selectedAttribute) {
        return;
      }

      selectedAttribute = n;
      attributeEditorText.setText("");
      attributeEditorList.deselectAll();
      attributeEditorList.setEnabled(n != -1);
      attributeEditorText.setEnabled(n != -1);

      if (n != -1) {
        String   name   = attributeList.getItem(n);
        String   value  = element.spot_getAttribute(name);
        String   lname  = name.toLowerCase(Locale.US);
        String   editor = EditorHelper.getAttributeEditorType(name);
        String[] a      = "list".equals(editor)
                          ? EditorHelper.getListAttributeChoices(name)
                          : null;

        tltmDialog.setEnabled((editor != null) &&!editor.equals("list"));
        tltmHelp.setEnabled(attributeHelp.containsKey(lname));

        if (a != null) {
          ((StackLayout) attributeEditorStack.getLayout()).topControl = attributeEditorList;
          attributeEditorList.removeAll();

          for (String s : a) {
            attributeEditorList.add(s);
          }

          if (value != null) {
            n = attributeEditorList.indexOf(value);

            if (n != -1) {
              attributeEditorList.select(n);
            }
          }

          attributeEditorText.setText("");
        } else {
          ((StackLayout) attributeEditorStack.getLayout()).topControl = attributeEditorText;
          attributeEditorText.setText((value == null)
                                      ? ""
                                      : value);
          attributeEditorText.forceFocus();
          attributeEditorList.deselectAll();
        }
      }

      attributeEditorStack.layout();

      return;
    } else {
      valueChanged(e.widget);
    }
  }

  public void setCloneItem(boolean cloneItem) {
    this.cloneItem = cloneItem;
  }

  public boolean setElementText(String text) {
    if (element instanceof SPOTSet) {
      SPOTSet set = (SPOTSet) element;

      set.clear();

      if (text.length() == 0) {
        if (set.size() == 0) {
          return false;
        }

        set.clear();

        return true;
      } else {
        String      s    = set.spot_getArrayClassShortName();
        boolean     trim = !s.contains("String");
        CharScanner sc   = new CharScanner(text);

        while((s = sc.nextToken('\n', trim)) != null) {
          set.add(s);
        }

        sc.close();

        return true;
      }
    } else {
      if(text!=null && text.length()>0) {
        if(element instanceof SPOTInteger) {
          if(!SNumber.isNumeric(text) || text.indexOf('.')!=-1) {
            return false;
          }
        }
        else if(element instanceof SPOTReal) {
          if(!SNumber.isNumeric(text)) {
            return false;
          }
        }
      }
      if (isStringEqual(text, element.spot_stringValue())) {
        return false;
      }

      element.spot_setValue(text);

      return true;
    }
  }

  public void setPropertyChangeListener(PropertyChangeListener propertyChangeListener, Object eventSource) {
    this.propertyChangeListener = propertyChangeListener;
    this.eventSource            = eventSource;
  }

  public void setReselectOnEnterKey(boolean reselectOnEnterKey) {
    this.reselectOnEnterKey = reselectOnEnterKey;
  }

  public void setSelectedItem(iSPOTElement element) {
    if (cloneItem) {
      this.element = (iSPOTElement) element.clone();
    } else {
      this.element = element;
    }

    resetValue(null);
  }

  public String getBackgroundColor(Shell shell, String color) {
    UIColor               c = ((color == null) || (color.length() == 0))
                              ? null
                              : ColorUtils.getBackgroundColor(color);
    BackgroundColorDialog d = new BackgroundColorDialog(getShell(), c);

    if (d.open() != IDialogConstants.OK_ID) {
      return null;
    }

    c = d.getSelectedColor();

    if (c == null) {
      return "";
    }

    return c.toString();
  }

  public String getBorder(Shell shell, String border) {
    SPOTSet borders = new SPOTSet("border", new CBorder(null, null, CBorder.standard, "standard", true), -1, -1, true);

    if ((border != null) && (border.length() > 0)) {
      try {
        iWidget context = Studio.getSelectedDocument().getContextWidget();
        Reader  r;

        if (!border.startsWith("{") &&!border.startsWith("borders")) {
          r = new CharArray(border.length() + 2).set('{').append(border).append('}');
        } else {
          r = new StringReader(border);
        }

        SDFNode node = SDFNode.parse(r, context.getURLResolver(), null, false);

        node = node.getFirstNode();
        borders.fromSDF(node);
      } catch(Exception e) {
        System.err.println(ApplicationException.getMessageEx(e));
      }
    }

    BordersDialog d = new BordersDialog(shell, borders);

    if (d.open() != IDialogConstants.OK_ID) {
      return null;
    }

    borders = d.getSelectedBorders();

    if (borders == null) {
      return "";
    }

    return Utilities.toString(borders);
  }

  public String getIcon(Shell shell, String icon, boolean imageChooser) {
    SPOTPrintableString ps = new SPOTPrintableString();

    if ((icon != null) && (icon.length() > 0)) {
      try {
        aSPOTElement.spot_populatePrimitaveElementFromString(ps, icon);
      } catch(Exception e) {
        System.err.println(ApplicationException.getMessageEx(e));
      }
    }

    IconOrImageChooserDialog d = new IconOrImageChooserDialog(getShell(), ps, imageChooser);

    if (d.open() == IDialogConstants.OK_ID) {
      return null;
    }

    ps = d.getIconElement();

    return (ps == null)
           ? ""
           : Utilities.toStringEx(ps).toString();
  }

  public String getInsets(Shell shell, String insets) {
    InsetsDialog d = new InsetsDialog(getShell(), insets);

    if (d.open() == IDialogConstants.OK_ID) {
      return null;
    }

    insets = d.getSelectedInsets();

    return (insets == null)
           ? ""
           : insets;
  }

  public boolean isCloneItem() {
    return cloneItem;
  }

  public boolean isReselectOnEnterKey() {
    return reselectOnEnterKey;
  }

  protected boolean checkForTextWidgetChange(Widget w, Text text) {
    if (w != text) {
      return true;
    }

    String ov = element.spot_stringValue();

    if (ov == null) {
      ov = "";
    }

    return !text.getText().equals(ov);
  }

  @Override
  protected void checkSubclass() {}

  protected void createAttributesEditor(Composite parent, int hspan) {
    grpAttributes = new Group(this, SWT.NONE);

    GridLayout gl_grpAttributes = new GridLayout(3, false);

    gl_grpAttributes.verticalSpacing   = 0;
    gl_grpAttributes.marginWidth       = 0;
    gl_grpAttributes.marginHeight      = 0;
    gl_grpAttributes.horizontalSpacing = 0;
    grpAttributes.setLayout(gl_grpAttributes);
    grpAttributes.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, hspan, 1));
    grpAttributes.setText("Attributes");
    attributeList = new List(grpAttributes, SWT.BORDER | SWT.V_SCROLL);
    attributeList.addSelectionListener(this);

    GridData gd_list = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);

    gd_list.heightHint = 60;
    attributeList.setLayoutData(gd_list);
    attributeEditorStack = new Composite(grpAttributes, SWT.BORDER);
    attributeEditorStack.setLayout(new StackLayout());
    attributeEditorStack.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
    attributeEditorList = new List(attributeEditorStack, SWT.V_SCROLL);
    attributeEditorList.addSelectionListener(this);
    attributeEditorText = new Text(attributeEditorStack, SWT.WRAP | SWT.V_SCROLL | SWT.SINGLE);
    attributeEditorText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
    attributeEditorText.addFocusListener(this);
    attributeEditorText.addTraverseListener(this);
    toolBar   = new ToolBar(grpAttributes, SWT.FLAT | SWT.RIGHT | SWT.VERTICAL);
    tltmClear = new ToolItem(toolBar, SWT.NONE);
    tltmClear.setImage(ResourceManager.getPluginImage("com.appnativa.studio", "icons/garbagecan.png"));
    tltmClear.addSelectionListener(this);
    tltmDialog = new ToolItem(toolBar, SWT.NONE);
    tltmDialog.setEnabled(false);
    tltmDialog.setImage(ResourceManager.getPluginImage("com.appnativa.studio", "icons/dialog_button.png"));
    tltmDialog.addSelectionListener(this);
    tltmHelp = new ToolItem(toolBar, SWT.NONE);
    tltmHelp.setEnabled(false);
    tltmHelp.setImage(ResourceManager.getPluginImage("com.appnativa.studio", "icons/information.png"));
    tltmHelp.addSelectionListener(this);
  }

  protected void createPreview() {}

  protected SwingGraphics createPreviewGraphics(Control composite) {
    Point         p      = composite.getSize();
    BufferedImage img    = previewBufferedImage;
    int           width  = fullSizePreview
                           ? p.x
                           : Math.min(p.x, 200);
    int           height = fullSizePreview
                           ? p.y
                           : Math.min(p.y, 100);

    if ((img == null) || (img.getWidth() != width) || (img.getHeight() != height)) {
      img                  = Java2DUtils.createCompatibleImage(width, height);
      previewBufferedImage = img;
      previewWidth         = width;
      previewHeight        = height;
    }

    Graphics2D g2 = img.createGraphics();

    g2.clipRect(0, 0, width, height);

    SwingGraphics g = new SwingGraphics(g2);

    g.clearRect(0, 0, width, height);

    return g;
  }

  protected void createPreviewIfNeeded(Control composite) {
    if (composite != null) {
      if (previewImage != null) {
        createPreview();
      } else {
        Point p      = composite.getSize();
        int   width  = Math.min(p.x, 200);
        int   height = Math.min(p.y, 100);

        if ((height > 0) && (width > 0)) {
          if ((height != previewHeight) || (width != previewWidth)) {
            createPreview();
          }
        }
      }
    }
  }

  protected void finishedDrawingPreview(Control composite, SwingGraphics g) {
    g.dispose();

    if (previewImage != null) {
      previewImage.dispose();
    }

    previewImage = Utilities.convertToSWTImage(previewBufferedImage);
    composite.redraw();
  }

  protected void notifyPropertyChangeListener(final Object newValue) {
    PropertyChangeEvent pe = new PropertyChangeEvent(eventSource, "value", oldValue, newValue);

    oldValue = newValue;
    propertyChangeListener.propertyChange(pe);
  }

  protected void paintPreview(GC gc, Control control) {
    if (control != null) {
      if (previewImage != null) {
        Point p = control.getSize();
        int   x = (p.x - previewWidth) / 2;
        int   y = (p.y - previewHeight) / 2;

        gc.drawImage(previewImage, x, y);
      }
    }
  }

  protected void populateCombo(iSPOTElement element, Combo combo, String attribute, int def) {
    String s = element.spot_getAttribute(attribute);
    int    n = (s == null)
               ? def
               : combo.indexOf(s);

    combo.select((n < 0)
                 ? def
                 : n);
  }

  protected void populateText(iSPOTElement element, Text text, String attribute) {
    String s = element.spot_getAttribute(attribute);

    text.setText((s == null)
                 ? ""
                 : s);
  }

  protected void resetAttributes() {
    if (attributeList != null) {
      selectedAttribute = -1;

      Map map = element==null ? null : element.spot_getSupportedAttributes();

      attributeList.removeAll();

      if ((map != null) && (map.size() > 0)) {
        attributeEditorStack.setVisible(true);

        Object[] a = map.keySet().toArray();

        Arrays.sort(a);

        for (Object o : a) {
          attributeList.add((String) o);
        }
        attributeEditorText.setText("");
        setVisible(grpAttributes, true);
        tltmDialog.setEnabled(false);
        tltmHelp.setEnabled(false);
      } else {
        setVisible(grpAttributes, false);
      }
    }
  }

  protected void resetValue(Widget widget) {
    if (textWidget != null) {
      String s;

      if (element != null) {
        if (element instanceof SPOTSet) {
          s = Helper.toString(((SPOTSet) element).stringValues(), "\n");
        } else {
          s = element.spot_stringValue();
        }
      } else {
        s = (oldValue == null)
            ? ""
            : oldValue.toString();
      }

      if (s == null) {
        s = "";
      }

      textWidget.setText(s);
    }

    resetAttributes();
  }

  protected void valueChanged(Widget widget) {
    String text = getElementText(widget);

    if (element != null) {
      if ((widget == textWidget) || (widget == null) || (widget == listWidget)) {
        if (setElementText(text)) {
          notifyPropertyChangeListener(element);
        }
      } else if (widget == attributeEditorText) {
        int n = selectedAttribute;

        if (n != -1) {
          String name = attributeList.getItem(n);
          String s    = attributeEditorText.getText().trim();

          if (s.length() == 0) {
            s = null;
          }

          if (!isStringEqual(s, element.spot_getAttribute(name))) {
            String def = element.spot_getAttributeDefaultValue(name);

            setOrRemoveAttribute(name, s, def);
            notifyPropertyChangeListener(element);
          }
        }
      } else if (widget == attributeEditorList) {
        int n = attributeList.getSelectionIndex();

        if (n != -1) {
          String name = attributeList.getItem(n);

          n = attributeEditorList.getSelectionIndex();

          String s = (n == -1)
                     ? ""
                     : attributeEditorList.getItem(n);

          if (s.length() == 0) {
            s = null;
          }

          if (!isStringEqual(s, element.spot_getAttribute(name))) {
            String def = element.spot_getAttributeDefaultValue(name);

            setOrRemoveAttribute(name, s, def);
            notifyPropertyChangeListener(element);
          }
        }
      }
    } else {
      if (!isStringEqual(text, oldValue)) {
        notifyPropertyChangeListener(text);
      }
    }
  }

  protected void setOrRemoveAttribute(String attribute, Combo combo, int def) {
    int n = combo.getSelectionIndex();

    if ((n == -1) || (n == def)) {
      element.spot_removeAttribute(attribute);
    } else {
      element.spot_setAttribute(attribute, combo.getItem(n));
    }
  }

  protected void setOrRemoveAttribute(String attribute, String text, String def) {
    if (text.equals(def)) {
      element.spot_removeAttribute(attribute);
    } else {
      element.spot_setAttribute(attribute, text);
    }
  }

  protected void setOrRemoveAttribute(String attribute, Text text, String def) {
    String s = text.getText().trim();

    if (s.equals(def)) {
      element.spot_removeAttribute(attribute);
    } else {
      element.spot_setAttribute(attribute, s);
    }
  }

  public static void setVisible(Control c, boolean visible) {
    c.setVisible(visible);

    Object o = c.getLayoutData();

    if (o instanceof GridData) {
      ((GridData) o).exclude = !visible;
    }
  }
 
  protected void editAttributes() {
    SequenceProperty sp = EditorHelper.getAttributesSequenceProperty(element, null);
    SPOTSequence seq=sp.getSequence();
    seq.spot_setLinkedData(sp);
    SequenceArrayEditor editor=new SequenceArrayEditor(getShell(), seq,null,false);
    int ret=editor.open();
    if(ret==IDialogConstants.OK_ID) {
      element.spot_cleanAttributes();
      int len=seq.spot_getCount();
      for(int i=0;i<len;i++) {
        iSPOTElement e=seq.spot_elementAt(i);
        String name=e.spot_getName();
        String value=e.spot_stringValue();
        if(value!=null && value.length()==0) {
          value=null;
        }
        if(value!=null) {
          element.spot_setAttribute(name, value);
        }
      }
      notifyPropertyChangeListener(element);
    }
  }
  
  protected String getElementText(Widget widget) {
    String text = null;

    if ((widget == textWidget) || (widget == null) || (widget == listWidget)) {
      if (widget == listWidget) {
        int n = listWidget.getSelectionIndex();

        if (n == -1) {
          text = null;
        } else {
          if (isListWidgetResourceList()) {
            text = "{resource:" + listWidget.getItem(n) + "}";
          } else {
            text = listWidget.getItem(n);
          }

          if (textWidget != null) {
            textWidget.setText(text);
          }
        }
      } else {
        text = (textWidget == null)
               ? null
               : textWidget.getText().trim();

        if ((listWidget != null) && (text != null) && text.startsWith("{resource:") && (text.length() > 12)) {
          String s = text.substring(10, text.length() - 1).trim();
          int    n = listWidget.indexOf(s);

          if (n != -1) {
            listWidget.select(n);
          }
        }
      }
    }

    return text;
  }

  protected boolean isListWidgetResourceList() {
    return false;
  }

  protected boolean isStringEqual(String text, Object value) {
    if ((text == null) || (text.length() == 0)) {
      return (value == null) || (value.toString().length() == 0);
    }

    return text.equals(value);
  }
}
