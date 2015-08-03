/*
 * @(#)SPOTHelper.java
 *
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.studio.properties;

import java.beans.PropertyChangeListener;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.DialogCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FontDialog;
import org.eclipse.ui.views.properties.ComboBoxPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.wb.swt.ResourceManager;

import com.appnativa.studio.Studio;
import com.appnativa.studio.RMLDocument;
import com.appnativa.studio.Utilities;
import com.appnativa.studio.dialogs.BordersDialog;
import com.appnativa.studio.dialogs.SWTResourceSelectionDialogEx;
import com.appnativa.studio.editors.SDFSyntax;
import com.appnativa.studio.properties.GenericPropertyDescriptor.iEditorCreator;
import com.appnativa.studio.properties.SequenceProperty.EditorValue;
import com.appnativa.rare.spot.Font;
import com.appnativa.rare.spot.GridCell;
import com.appnativa.rare.spot.Tab;
import com.appnativa.rare.ui.FontUtils;
import com.appnativa.spot.SPOTNode;
import com.appnativa.spot.SPOTPrintableString;
import com.appnativa.spot.SPOTSequence;
import com.appnativa.spot.SPOTSet;
import com.appnativa.spot.aSPOTElement;
import com.appnativa.spot.iSPOTConstants;
import com.appnativa.spot.iSPOTElement;

public class EditorHelper {
  static HashMap<String,String> boldNames=new HashMap<String, String>();
  static {
    boldNames.put("name", "");
    boldNames.put("value", "");
    boldNames.put("selectedIndex", "");
    boldNames.put("actions", "");
    boldNames.put("icon", "");
    boldNames.put("minValue", "");
    boldNames.put("maxValue", "");
    boldNames.put("indeterminate", "");
    boldNames.put("editable", "");
    boldNames.put("horizontal", "");
    boldNames.put("dataURL", "");
    boldNames.put("checkboxTrailing", "");
    boldNames.put("dataURL", "");
    boldNames.put("rootNode", "");
    boldNames.put("expandAll", "");
    boldNames.put("wordWrap", "");
    boldNames.put("tabPosition", "");
    boldNames.put("tabStyle", "");
    boldNames.put("loadOnActivation", "");
    boldNames.put("reloadOnActivation", "");
  }
  public static boolean useFullEditors     = true;
  static iEditorCreator colorEditorCreator = new iEditorCreator() {
    @Override
    public CellEditor createPropertyEditor(Composite parent, GenericPropertyDescriptor property) {
      return new ColorCellEditor(parent);
    }
    @Override
    public Control showFullPropertyEditor(Composite parent, iPropertyDescriptor property, Object eventSource,
            PropertyChangeListener listener, PropertyEditors propertyEditors) {
      return propertyEditors.showColorEditor(parent, eventSource, property.getElement().spot_stringValueEx(), listener);
    }
  };
  static iEditorCreator bordersEditorCreator = new iEditorCreator() {
    @Override
    public CellEditor createPropertyEditor(Composite parent, GenericPropertyDescriptor property) {
      return new BordersEditor(parent);
    }
    @Override
    public Control showFullPropertyEditor(Composite parent, iPropertyDescriptor property, Object eventSource,
            PropertyChangeListener listener, PropertyEditors propertyEditors) {
      return propertyEditors.showBordersEditor(parent, eventSource, (SPOTSet) property.getElement(), listener);
    }
  };
  static iEditorCreator bgColorEditorCreator = new iEditorCreator() {
    @Override
    public CellEditor createPropertyEditor(Composite parent, GenericPropertyDescriptor property) {
      return new ColorCellEditor(parent, true);
    }
    @Override
    public Control showFullPropertyEditor(Composite parent, iPropertyDescriptor property, Object eventSource,
            PropertyChangeListener listener, PropertyEditors propertyEditors) {
      return propertyEditors.showBackgroundColorEditor(parent, eventSource,
              (SPOTPrintableString) property.getElement(), listener);
    }
  };
  static iEditorCreator setEditorCreator = new iEditorCreator() {
    @Override
    public CellEditor createPropertyEditor(Composite parent, GenericPropertyDescriptor property) {
      return new SequenceEditor(parent);
    }
    @Override
    public Control showFullPropertyEditor(Composite parent, iPropertyDescriptor property, Object eventSource,
            PropertyChangeListener listener, PropertyEditors propertyEditors) {
      return propertyEditors.showSequenceArrayEditor(parent, eventSource, property.getElement(), listener);
    }
  };
  static iEditorCreator fontNamesEditorCreator = new iEditorCreator() {
    @Override
    public CellEditor createPropertyEditor(Composite parent, GenericPropertyDescriptor property) {
      return new FontEditor(parent);
    }
    @Override
    public Control showFullPropertyEditor(Composite parent, iPropertyDescriptor property, Object eventSource,
            PropertyChangeListener listener, PropertyEditors propertyEditors) {
      return propertyEditors.showList(parent, eventSource, Studio.getFontNames(),
                                      property.getElement().spot_stringValue(), listener);
    }
  };
  static iEditorCreator fontEditorCreator = new iEditorCreator() {
    @Override
    public CellEditor createPropertyEditor(Composite parent, GenericPropertyDescriptor property) {
      return new FontEditor(parent);
    }
    @Override
    public Control showFullPropertyEditor(Composite parent, iPropertyDescriptor property, Object eventSource,
            PropertyChangeListener listener, PropertyEditors propertyEditors) {
      return propertyEditors.showFontEditor(parent, eventSource, (Font) property.getElement(), listener);
    }
  };
  static iEditorCreator urlEditorCreator = new iEditorCreator() {
    @Override
    public CellEditor createPropertyEditor(Composite parent, GenericPropertyDescriptor property) {
      return new URLCellEditor(parent);
    }
    @Override
    public Control showFullPropertyEditor(Composite parent, iPropertyDescriptor property, Object eventSource,
            PropertyChangeListener listener, PropertyEditors propertyEditors) {
      return propertyEditors.showURLEditor(parent, eventSource, property.getElement(), listener);
    }
  };
  static iEditorCreator urlSetEditorCreator = new iEditorCreator() {
    @Override
    public CellEditor createPropertyEditor(Composite parent, GenericPropertyDescriptor property) {
      return null;
    }
    @Override
    public Control showFullPropertyEditor(Composite parent, iPropertyDescriptor property, Object eventSource,
            PropertyChangeListener listener, PropertyEditors propertyEditors) {
      return propertyEditors.showURLSetEditor(parent, eventSource, property.getElement(), listener);
    }
  };
  static iEditorCreator nullEditorCreator = new iEditorCreator() {
    @Override
    public CellEditor createPropertyEditor(Composite parent, GenericPropertyDescriptor property) {
      return null;
    }
    @Override
    public Control showFullPropertyEditor(Composite parent, iPropertyDescriptor property, Object eventSource,
            PropertyChangeListener listener, PropertyEditors propertyEditors) {
      return null;
    }
  };
  static iEditorCreator listEditorCreator = new iEditorCreator() {
    @Override
    public CellEditor createPropertyEditor(Composite parent, GenericPropertyDescriptor property) {
      return null;
    }
    @Override
    public Control showFullPropertyEditor(Composite parent, iPropertyDescriptor property, Object eventSource,
            PropertyChangeListener listener, PropertyEditors propertyEditors) {
      iSPOTElement e = property.getElement();

      switch(e.spot_getType()) {
        case iSPOTConstants.SPOT_TYPE_ENUMERATED :
        case iSPOTConstants.SPOT_TYPE_BOOLEAN :
          return propertyEditors.showList(parent, eventSource, e, e.spot_stringValue(), listener);

        default :
          if(property instanceof GenericPropertyDescriptor) {
            String[] a=((GenericPropertyDescriptor)property).getListValues();
            if(a!=null) {
              return propertyEditors.showList(parent, eventSource, a, e.spot_stringValue(), listener);
            }
          }
          return null;
      }
    }
  };
  static HashMap<String, String[]> listAttributes      = new HashMap<String, String[]>();
  static iEditorCreator            insetsEditorCreator = new iEditorCreator() {
    @Override
    public CellEditor createPropertyEditor(Composite parent, GenericPropertyDescriptor property) {
      return null;
    }
    @Override
    public Control showFullPropertyEditor(Composite parent, iPropertyDescriptor property, Object eventSource,
            PropertyChangeListener listener, PropertyEditors propertyEditors) {
      return propertyEditors.showInsetsEditor(parent, eventSource, property.getElement(), listener);
    }
  };
  static iEditorCreator imageEditorCreator = new iEditorCreator() {
    @Override
    public CellEditor createPropertyEditor(Composite parent, GenericPropertyDescriptor property) {
      return new URLCellEditor(parent);
    }
    @Override
    public Control showFullPropertyEditor(Composite parent, iPropertyDescriptor property, Object eventSource,
            PropertyChangeListener listener, PropertyEditors propertyEditors) {
      return propertyEditors.showImageEditor(parent, eventSource, property.getElement(), listener);
    }
  };
  static iEditorCreator iconEditorCreator = new iEditorCreator() {
    @Override
    public CellEditor createPropertyEditor(Composite parent, GenericPropertyDescriptor property) {
      return new URLCellEditor(parent);
    }
    @Override
    public Control showFullPropertyEditor(Composite parent, iPropertyDescriptor property, Object eventSource,
            PropertyChangeListener listener, PropertyEditors propertyEditors) {
      return propertyEditors.showIconEditor(parent, eventSource, property.getElement(), listener);
    }
  };
  static iEditorCreator primitiveSetEditorCreator = new iEditorCreator() {
    @Override
    public CellEditor createPropertyEditor(Composite parent, GenericPropertyDescriptor property) {
      return new URLCellEditor(parent);
    }
    @Override
    public Control showFullPropertyEditor(Composite parent, iPropertyDescriptor property, Object eventSource,
            PropertyChangeListener listener, PropertyEditors propertyEditors) {
      return propertyEditors.showPrimitiveSetEditor(parent, eventSource, property.getElement(), listener);
    }
  };
  static HashMap<String, String> attributeHelp    = new HashMap<String, String>();
  static HashMap<String, String> attributeEditors = new HashMap<String, String>();

  static {
    String[] tf = new String[] { "true", "false" };

    listAttributes.put("deferred", tf);
    listAttributes.put("defer", tf);
    listAttributes.put("cache", tf);
    listAttributes.put("aggregate", tf);
    listAttributes.put("shared", tf);
    listAttributes.put("inline", tf);
    listAttributes.put("renOnce", tf);
    listAttributes.put("unescape", tf);
    listAttributes.put("resizable", tf);
    listAttributes.put("movable", tf);
    listAttributes.put("modal", tf);
    listAttributes.put("opaque", tf);
    listAttributes.put("decorated", tf);
    listAttributes.put("horizontal", tf);
    listAttributes.put("diagonal", tf);
    listAttributes.put("fadeIn", tf);
    listAttributes.put("fadeOut", tf);
    listAttributes.put("diagonalAnchor", new String[] {"centered","upper_left","upper_right","lower_left","lower_right","upper_middle","lower_middle","left_middle","right_middle"});
    listAttributes.put("emulateMainWindow", tf);
    listAttributes.put("explicitItemImport",tf);
    listAttributes.put("direction", new String[] { "forward", "backward"});
    listAttributes.put("windowType", new String[] { "frame", "dialog", "popup", "popup_orphan" });
    attributeEditors.put("bgcolor", "gradient");
    attributeEditors.put("borders", "borders");
    attributeEditors.put("icon", "icon");
    attributeEditors.put("bgimageurl", "image");
  }

  public static boolean setReferenceElement(iSPOTElement parent, String name, iSPOTElement value) {
    String method = "set" + name.substring(0, 1).toUpperCase() + name.substring(1);

    try {
      Method m = parent.getClass().getMethod(method, iSPOTElement.class);

      m.invoke(parent, value);

      return true;
    } catch(Exception e) {}

    return false;
  }

  public static String getAttributeEditorType(String attribute) {
    String type = attributeEditors.get(attribute);

    if (type == null) {
      if (listAttributes.containsKey(attribute)) {
        type = "list";
      }
    }

    return type;
  }

  public static SequenceProperty getAttributesSequenceProperty(iSPOTElement e, List cfgs) {
    Map<String, String> map = e.spot_getSupportedAttributes();

    if ((map == null) || (map.size() == 0)) {
      return new SequenceProperty(new SPOTSequence(true));
    }

    SPOTSequence seq = aSPOTElement.spot_sequenceForMap(map,true);

    if (cfgs == null) {
      cfgs = Collections.singletonList(e);
    }

    Iterator<String>                       it = map.keySet().iterator();
    String                                 name, value;
    AttributePropertyDescriptor            c;
    int                                    len = cfgs.size();
    int                                    i;
    boolean                                event;
    iSPOTElement                           ee;
    boolean                                sameValue;
    Object                                 lastValue;
    ArrayList<AttributePropertyDescriptor> list = new ArrayList<AttributePropertyDescriptor>();
    String events=Studio.getResourceAsString("Studio.text.events");
    String other=Studio.getResourceAsString("Studio.text.other_attributes");
    org.eclipse.swt.graphics.Font bold=ResourceManager.getBoldFont(Display.getDefault().getSystemFont());
    while(it.hasNext()) {
      name      = it.next();
      event     = Utilities.isEvent(name);
      ee        = seq.spot_elementFor(name);
      c         = new AttributePropertyDescriptor(ee, listAttributes.get(name));
      String editor=attributeEditors.get(name);
      if(editor!=null) {
        setupConverters(ee, editor, c);
      }
      sameValue = true;
      lastValue = null;

      for (i = 0; i < len; i++) {
        ee    = (iSPOTElement) cfgs.get(i);
        value = Utilities.unNormalizeString(ee.spot_getAttribute(name), event);

        if (len > 1) {
          if ((value != null) && (value.length() > 0)) {
            if (event) {
              c.setValueFont(bold);
            }

            if ((i > 0) &&!value.equals(lastValue)) {
              sameValue = false;
            }
          }
        }

        lastValue = value;
      }

      if (sameValue && (lastValue != null)) {
        ee=seq.spot_elementFor(name);
        if(ee!=null) {
          aSPOTElement.spot_populatePrimitaveElementFromString(ee,lastValue.toString());
        }
      }

      c.setCategory(event
                    ? events
                    : other);;
      list.add(c);
    }

    SequenceProperty sp = new SequenceProperty(seq, (IPropertyDescriptor[]) list.toArray(new IPropertyDescriptor[list.size()]));

    return sp;
  }

  public static String[] getListAttributeChoices(String name) {
    return listAttributes.get(name);
  }

  public static List<PropertyDescriptor> getPropertyDescriptor(SPOTSequence cfg, boolean first,
          List<SPOTSequence> cfgs) {
    ArrayList<PropertyDescriptor> list = new ArrayList<PropertyDescriptor>();
    PropertyDescriptor            col;
    int                           len;
    int                           i;
    iSPOTElement                  element;
    String                        type     = null;
    boolean                       multiple = cfgs != null;
    boolean                       reference;

    org.eclipse.swt.graphics.Font bold=ResourceManager.getBoldFont(Display.getDefault().getSystemFont());
    len = cfg.spot_getCount();

    SPOTNode node = SDFSyntax.getNode(cfg.spot_getClassShortName());

    for (i = 0; i < len; i++) {
      col       = null;
      element   = cfg.spot_elementAt(i);
      reference = element == null;

      if (element == null) {
        String name = cfg.spot_getNameAt(i);

        element = getReferenceElement(cfg, name);
      }

      if ((element == null) || (multiple &&!isOkForMultiple(element))) {
        continue;
      }

      switch(element.spot_getType()) {
        case iSPOTConstants.SPOT_TYPE_SET :
          if (multiple && (((SPOTSet) element).spot_isAnySet() || ((SPOTSet) element).spot_isSequenceSet())) {
            col = null;
          } else {
            if (reference) {
              col = new ReferencePropertyDescriptor(element);
            } else {
              col = new GenericPropertyDescriptor(element);
            }

            if (((SPOTSet) element).spot_getArrayClassInstance() instanceof SPOTSequence) {
              ((GenericPropertyDescriptor) col).setEditorCreator(setEditorCreator);
            }
            else {
              ((GenericPropertyDescriptor) col).setEditorCreator(primitiveSetEditorCreator);
            }
          }

          break;

        case iSPOTConstants.SPOT_TYPE_SEQUENCE :
          if (reference) {
            col = new ReferencePropertyDescriptor(element);
          } else {
            if (element instanceof Font) {
              col = new GenericPropertyDescriptor(element);
            } else {
              col = new SequencePropertyDescriptor(element);
            }
          }

          break;

        case iSPOTConstants.SPOT_TYPE_ENUMERATED :
          if (useFullEditors) {
            col = new GenericPropertyDescriptor(element, (String[]) element.spot_getRange());
            ((GenericPropertyDescriptor) col).setEditorCreator(listEditorCreator);
          } else {
            col = new ComboBoxPropertyDescriptor(element.spot_getName(), element.spot_getName(),
                    (String[]) element.spot_getRange());
          }

          break;

        case iSPOTConstants.SPOT_TYPE_BOOLEAN :
          if (useFullEditors) {
            col = new GenericPropertyDescriptor(element, new String[] { "true", "false" });
            ((GenericPropertyDescriptor) col).setEditorCreator(listEditorCreator);
          } else {
            col = new ComboBoxPropertyDescriptor(element.spot_getName(), element.spot_getName(), new String[] { "true",
                    "false" });
          }

          break;

        default :
          if(cfg instanceof GridCell) {
            if(!(cfg.spot_getParent() instanceof SPOTSet)) {
              String name=element.spot_getName();
              if(name.equals("x") || name.equals("y")|| name.equals("width")|| name.equals("height")) {
                col=null;
                break;
              }
            }
          }
          col = new GenericPropertyDescriptor(element);
          break;
      }

      if (col != null) {
        type = SPOTInspector.setupColumnType(node, col);

        if (!"hidden".equals(type)) {
          if(boldNames.containsKey(element.spot_getName())) {
            ((iPropertyDescriptor)col).setFont(bold);
          }
          else if(element.spot_getName().equals("title")) {
            if(cfg instanceof Tab) {
              ((iPropertyDescriptor)col).setFont(bold);
            }
          }
          if ((type != null) && (col instanceof GenericPropertyDescriptor)) {
            setupConverters(element, type, (GenericPropertyDescriptor) col);
          } else if ((element instanceof SPOTSequence) && (col instanceof GenericPropertyDescriptor)) {
            ((GenericPropertyDescriptor) col).setEditorCreator(nullEditorCreator);
          }

          list.add(col);
        }
      }
    }

    return list;
  }

  public static iSPOTElement getReferenceElement(iSPOTElement parent, String name) {
    String method = "get" + name.substring(0, 1).toUpperCase() + name.substring(1) + "Reference";

    try {
      Method       m       = parent.getClass().getMethod(method);
      iSPOTElement element = (iSPOTElement) m.invoke(parent);

      ((SPOTSequence) parent).spot_setReferenceVariable(name, null);    // unset

      // the
      // reference
      // will be
      // set if
      // edited
      return element;
    } catch(Exception e) {}

    return null;
  }

  static void setupConverters(iSPOTElement element, String subtype, GenericPropertyDescriptor col) {
    if (subtype.equals("color")) {
      col.setEditorCreator(colorEditorCreator);
    } else if (subtype.equals("gradient")) {
      col.setEditorCreator(bgColorEditorCreator);
    } else if (subtype.equals("borders")) {
      col.setEditorCreator(bordersEditorCreator);
    } else if (subtype.equals("font")) {
      col.setEditorCreator(nullEditorCreator);
      //col.setEditorCreator(fontEditorCreator);
    } else if (subtype.equals("fontnames")) {
      col.setEditorCreator(fontNamesEditorCreator);
    } else if (subtype.equals("insets")) {
      col.setEditorCreator(nullEditorCreator);
      //col.setEditorCreator(insetsEditorCreator);
    } else if (subtype.equals("urlset")) {
      col.setEditorCreator(urlSetEditorCreator);
    } else if (subtype.equals("url")) {
      col.setEditorCreator(urlEditorCreator);
    } else if (subtype.equals("icon")) {
      col.setEditorCreator(iconEditorCreator);
    } else if (subtype.equals("image")) {
      col.setEditorCreator(imageEditorCreator);
    } else if (element instanceof SPOTSet) {
      col.setEditorCreator(primitiveSetEditorCreator);
    } else if (element instanceof SPOTSequence) {
      col.setEditorCreator(nullEditorCreator);
    }
  }

  private static boolean isOkForMultiple(iSPOTElement element) {
    return !"name".equals(element.spot_getName());
  }

  static class BordersEditor extends SequenceEditor {
    public BordersEditor(Composite parent) {
      super(parent);
    }

    @Override
    protected Object openDialogBox(Control cellEditorWindow) {
      BordersDialog dialog = new BordersDialog(cellEditorWindow.getShell(), (SPOTSet) getElement());
      int           ret    = dialog.open();

      if ((ret == IDialogConstants.OK_ID) || (ret == IDialogConstants.CLIENT_ID)) {
        return dialog.getSelectedBorders();
      }

      return null;
    }
  }


  static class FontEditor extends SequenceEditor {
    public FontEditor(Composite parent) {
      super(parent);
    }

    @Override
    protected Object openDialogBox(Control cellEditorWindow) {
      Font     font = (Font) getElement();
      FontData fd   = null;

      if (font != null) {
        fd = new FontData();

        if (font.family.getValue() != null) {
          fd.setName(font.family.getValue());
        }

        switch(font.style.intValue()) {
          case Font.CStyle.bold :
            fd.setStyle(SWT.BOLD);

            break;

          case Font.CStyle.bold_italic :
            fd.setStyle(SWT.BOLD | SWT.ITALIC);

            break;

          case Font.CStyle.italic :
            fd.setStyle(SWT.ITALIC);

            break;

          default :
            fd.setStyle(SWT.NORMAL);

            break;
        }

        if (font.size.getValue() != null) {
          try {
            fd.setHeight((int) FontUtils.getSize(font.size.getValue(),
                    Display.getDefault().getSystemFont().getFontData()[0].height));
          } catch(Throwable e) {
            e.printStackTrace();
          }
        }
      }

      FontDialog dialog = new FontDialog(cellEditorWindow.getShell());

      if (fd != null) {
        dialog.setFontList(new FontData[] { fd });
      }

      fd = dialog.open();

      if (fd != null) {
        font = (Font) font.clone();
        font.family.setValue(fd.getName());

        String s = font.size.getValue();

        if ((s == null) || (s.length() == 0) || Character.isDigit(s.charAt(0))) {
          font.size.setValue(fd.getHeight());
        }

        int style = fd.getStyle();

        if ((style & SWT.BOLD) > 0) {
          if ((style & SWT.ITALIC) > 0) {
            font.style.setValue(Font.CStyle.bold_italic);
          } else {
            font.style.setValue(Font.CStyle.bold);
          }
        } else if ((style & SWT.ITALIC) > 0) {
          font.style.setValue(Font.CStyle.italic);
        } else {
          font.style.setValue(Font.CStyle.normal);
        }

        return font;
      }

      return null;
    }

    protected iSPOTElement getElement() {
      Object o = getValue();

      if (o instanceof iSPOTElement) {
        return (iSPOTElement) o;
      }

      if (o instanceof SequenceProperty) {
        return ((SequenceProperty) o).getSequence();
      }

      if ((o instanceof EditorValue) && ((EditorValue) o).value instanceof iSPOTElement) {
        return (iSPOTElement) ((EditorValue) o).value;
      }

      return null;
    }
  }


  static class SequenceEditor extends DialogCellEditor {
    public SequenceEditor(Composite parent) {
      super(parent);
    }

    @Override
    protected Object openDialogBox(Control cellEditorWindow) {
//      SequenceArrayEditor dialog = new SequenceArrayEditor(cellEditorWindow.getShell(), getElement());
//      int                 ret    = dialog.open();
//
//      if ((ret == IDialogConstants.OK_ID) || (ret == IDialogConstants.CLIENT_ID)) {
//        return dialog.getSPOTElement();
//      }
      return null;
    }

    @Override
    protected void updateContents(Object value) {
      if (getDefaultLabel() == null) {
        return;
      }

      String text = "";

      if (value instanceof SPOTSet) {
        text = SequenceProperty.toString((SPOTSet) value);
      } else if (value != null) {
        text = value.toString();
      }

      getDefaultLabel().setText(text);
      super.updateContents(value);
    }

    protected iSPOTElement getElement() {
      Object o = getValue();

      if (o instanceof EditorValue) {
        o = ((EditorValue) o).value;

        if (o instanceof iSPOTElement) {
          return (iSPOTElement) o;
        }

        return null;
      }

      return (iSPOTElement) o;
    }
  }


  static class URLCellEditor extends DialogCellEditor {
    public URLCellEditor(Composite parent) {
      super(parent);
    }

    @Override
    protected Object openDialogBox(Control cellEditorWindow) {
      RMLDocument doc = Studio.getSelectedDocument();

      if (doc != null) {
        SWTResourceSelectionDialogEx dialog = new SWTResourceSelectionDialogEx(cellEditorWindow.getShell(),
                                                doc.getIProject(), "Select Resource");

        dialog.setProject(doc.getProject());
        dialog.setDocumentPath(doc.getAssetsRelativePrefix());
        dialog.open();

        String s = dialog.getSelectedResource();

        if (s != null) {
          return s;
        }
      } else {
        Display.getDefault().beep();
      }

      return getValue();
    }
  }
}
