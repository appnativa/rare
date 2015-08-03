/*
 * @(#)SPOTInspector.java   2008-06-12
 *
 * Copyright (c) appNativa Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.studio.properties;

import com.appnativa.studio.Utilities;
import com.appnativa.studio.editors.SDFSyntax;
import com.appnativa.rare.spot.Button;
import com.appnativa.rare.spot.Spinner;
import com.appnativa.rare.spot.Viewer;
import com.appnativa.rare.spot.Widget;
import com.appnativa.rare.ui.Column;
import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.ui.UIFont;
import com.appnativa.rare.ui.iPlatformCellEditingComponent;
import com.appnativa.rare.ui.painter.PaintBucket;
import com.appnativa.rare.viewer.TableViewer;
import com.appnativa.rare.widget.aWidget;
import com.appnativa.spot.SPOTNode;
import com.appnativa.spot.SPOTSequence;
import com.appnativa.spot.iSPOTConstants;
import com.appnativa.spot.iSPOTElement;
import com.appnativa.util.CharScanner;

import org.eclipse.ui.views.properties.PropertyDescriptor;

import java.awt.Font;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *
 * @author decoteaud
 */
public class SPOTInspector {
  public static final int         TYPE_BORDERS  = 5;
  public static final int         TYPE_COLOR    = 1;
  public static final int         TYPE_GRADIENT = 2;
  public static final int         TYPE_IMAGE    = 4;
  public static final int         TYPE_SEQUENCE = 6;
  public static final int         TYPE_URL      = 3;
  static HashMap<String, Integer> types         = new HashMap<String, Integer>();
  private static PaintBucket      _paintbucket;

  static {
    types.put("color", TYPE_COLOR);
  }

  public static List<Column> attributeColumns(iSPOTElement e, aWidget context, iPlatformCellEditingComponent editor,
          List<iSPOTElement> cfgs) {
    return attributeColumns(e, context, editor, cfgs, false);
  }

  public static List<Column> eventColumns(iSPOTElement e, aWidget context, iPlatformCellEditingComponent editor,
          List<iSPOTElement> cfgs) {
    return attributeColumns(e, context, editor, cfgs, true);
  }

  public static void finishColumnSetup(Column c) {
    if (Utilities.hasPopupMenu(c)) {
      c.setHeaderPainter(_paintbucket);
    }
  }

  public static String setupColumnType(SPOTNode node, PropertyDescriptor c) {
    String s = getComment(node, c.getDisplayName());

    if (s == null) {
      return null;
    }

    String other    = null;
    int    n        = s.indexOf(':');
    String category = null;
    String desc     = null;

    if (n != -1) {
      category = s.substring(0, n).trim();

      if (category.indexOf(' ') == -1) {
        desc = s.substring(n + 1).trim();
      } else {
        desc     = s;
        category = null;
      }
    } else {
      desc = s;
    }

    if (category != null) {
      n = category.indexOf('~');

      if (n != -1) {
        other    = category.substring(n + 1);
        category = category.substring(0, n);
        n        = other.indexOf('~');

        if (n != -1) {
          other = other.substring(0, n);
        }
      }
    }

    if ((category != null) && category.equalsIgnoreCase("hidden")) {
      return "hidden";
    }

    if (category != null) {
      category = category.trim();

      if (category.length() > 0) {
        c.setCategory(category);
      }
    }

    if (desc != null) {
      c.setDescription(desc);
    }

    if ((other != null) && (other.length() == 0)) {
      other = null;
    }

    return other;
  }

  public static iSPOTElement getAttributeConfig(TableViewer table) {
    RenderableDataItem item = table.getSelectedItem();

    if (item == null) {
      return null;
    }

    iSPOTElement e = (iSPOTElement) item.getLinkedData();
//FIX ME
//    if (item.getDataConverter() instanceof SPOTConverter) {
//      if ((e != null) &&!e.spot_hasDefinedAttributes()) {
//        e = null;
//      }
//    }

    return ((e == null) ||!e.spot_hasDefinedAttributes())
           ? null
           : e;
  }

  public static String getComment(SPOTNode node, String name) {
    String   comment = null;
    SPOTNode x;

    while(node != null) {
      x = node.elementFor(name);

      if (x == null) {
        if (node.extendsType != null) {
          node = SDFSyntax.elementFor(node.extendsType);

          continue;
        }

        break;
      }

      comment = x.theComment;

      break;
    }

    return comment;
  }

  public static SPOTSequence getCommonBase(List<SPOTSequence> list) {
    boolean      allbuttons  = true;
    boolean      allspinners = true;
    boolean      allviewers  = true;
    boolean      allsame     = true;
    SPOTSequence first       = null;
    SPOTSequence widget      = null;

    for (SPOTSequence w : list) {
      if (!(w instanceof Button)) {
        allbuttons = false;
      }

      if (!(w instanceof Spinner)) {
        allspinners = false;
      }

      if (!(w instanceof Viewer)) {
        allviewers = false;
      }

      if (first == null) {
        first = w;

        continue;
      }

      if (allsame && (!first.spot_getClassName().equals(w.spot_getClassName()))) {
        allsame = false;
      }
    }

    if (first == null) {
      return null;
    }

    if (allsame) {
      widget = (Widget) first.clone();
      widget.spot_clear();
    } else if (allbuttons) {
      widget = new Button();
    } else if (allviewers) {
      widget = new Viewer();
    } else if (allspinners) {
      widget = new Spinner();
    } else {
      widget = new Widget();
    }

    int          len = widget.spot_getCount();
    iSPOTElement e;
    iSPOTElement e2;
    String       name;

    for (int i = 0; i < len; i++) {
      e = widget.spot_elementAt(i);

      if (e == null) {
        continue;
      }

      switch(e.spot_getType()) {
        case iSPOTConstants.SPOT_TYPE_ANY :
        case iSPOTConstants.SPOT_TYPE_SET :
          continue;
        default :
          if (hasSameValue(list, i)) {
            name = widget.spot_nameAt(i);
            e2   = (name == null)
                   ? null
                   : first.spot_elementFor(name);

            if ((e2 != null) && e2.spot_valueWasSet()) {
              e.spot_copy(e2, false);
            }
          }

          break;
      }
    }

    return widget;
  }
  
  public static boolean needsReload(iSPOTElement e) {
    if ((e == null) || e instanceof SPOTSequence) {
      return true;
    }

    String name = e.spot_getName();

    e = e.spot_getParent();

    while ((e != null) && !(e instanceof SPOTSequence)) {
      e = e.spot_getParent();
    }

    SPOTNode node = (e == null) ? null : getNode(e.spot_getClassShortName());

    String s = node==null ? null : getComment(node, name);


    if (s != null) {
      if (s.startsWith("Appearance") || s.startsWith("Layout")) {
        return true;
      }
    }
    return false;
  }
  
  public static SPOTSequence getCommonValues(List<SPOTSequence> list) {
    if ((list == null) || (list.size() == 0)) {
      return null;
    }

    SPOTSequence first = list.get(0);
    SPOTSequence seq   = (SPOTSequence) first.clone();

    seq.spot_clear();

    int          len = seq.spot_getCount();
    iSPOTElement e;
    iSPOTElement e2;
    String       name;

    for (int i = 0; i < len; i++) {
      e = seq.spot_elementAt(i);

      if (e == null) {
        continue;
      }

      switch(e.spot_getType()) {
        case iSPOTConstants.SPOT_TYPE_ANY :
        case iSPOTConstants.SPOT_TYPE_SET :
          continue;
        default :
          if (hasSameValue(list, i)) {
            name = seq.spot_nameAt(i);
            e2   = (name == null)
                   ? null
                   : first.spot_elementFor(name);

            if ((e2 != null) && e2.spot_valueWasSet()) {
              e.spot_copy(e2, false);
            }
          }

          break;
      }
    }

    return seq;
  }

  public static SPOTSequence getCommonValues(Object[] list) {
    int                len = (list == null)
                             ? 0
                             : list.length;
    List<SPOTSequence> l   = new ArrayList<SPOTSequence>(len);

    for (int i = 0; i < len; i++) {
      l.add((SPOTSequence) list[i]);
    }

    return getCommonValues(l);
  }

  public static String getComponentCategory(iSPOTElement e) {
    if ((e == null) || e.spot_isContainer()) {
      return "reload";
    }

    String name = e.spot_getName();

    e = e.spot_getParent();

    while((e != null) &&!(e instanceof SPOTSequence)) {
      e = e.spot_getParent();
    }

    SPOTNode node = (e == null)
                    ? null
                    : getNode(e.spot_getClassShortName());

    if (node == null) {
      return null;
    }

    String s = getComment(node, name);

    s = (s == null)
        ? null
        : s;

    if (s != null) {
      int n = s.indexOf(':');

      s = (n == -1)
          ? null
          : s.substring(0, n);

      if (s != null) {
        s = CharScanner.getPiece(s, '~', 1);
      }
    }

    return s;
  }

  public static String getComponentProperty(iSPOTElement e, Object value) {
    if ((e == null) || e instanceof SPOTSequence) {
      return "reload";
    }

    String name = e.spot_getName();

    e = e.spot_getParent();

    while((e != null) &&!(e instanceof SPOTSequence)) {
      e = e.spot_getParent();
    }

    SPOTNode node = (e == null)
                    ? null
                    : getNode(e.spot_getClassShortName());

    if (node == null) {
      return null;
    }

    String s = getComment(node, name);

    s = (s == null)
        ? null
        : s;

    if (s != null) {
      String p = CharScanner.getPiece(s, '~', 3);

      if ((p == null) || (p.length() == 0)) {
        if (s.startsWith("Appearance")) {
          s = name;
        } else if (s.startsWith("Layout")) {
          s = "reload";
        } else {
          s = null;
        }
      } else {
        int n = p.indexOf(':');

        s = (n == -1)
            ? null
            : p.substring(0, n);

        if ("runtime".equals(s)) {
          s = null;
        }
      }
    }

    // hack to force reload of "auto" enumerated value
    if ((s != null) && node.isContainer() && "auto".equals(value)) {
      s = "reload";
    } else if ((s == null) && (e.spot_getParent() != null)) {
      s = "reload";
    }

    return s;
  }

  public static List<iSPOTElement> getConfigs(List<SPOTSequence> list, String name) {
    if (list == null) {
      return null;
    }

    List<iSPOTElement> cfgs = new ArrayList<iSPOTElement>(list.size());
    iSPOTElement       e;

    for (SPOTSequence s : list) {
      e = s.spot_elementForEx(name);

      if (e != null) {
        cfgs.add(e);
      }
    }

    return cfgs;
  }

  public static SPOTNode getNode(String name) {
    return SDFSyntax.elementFor(name);
  }

  public static SPOTNode getPropertyNode(SPOTNode node, String name) {
    SPOTNode x;

    while(node != null) {
      x = node.elementFor(name);

      if (x == null) {
        if (node.extendsType != null) {
          node = SDFSyntax.elementFor(node.extendsType);

          continue;
        }

        break;
      }

      return x;
    }

    return null;
  }

  public static String getPropertyType(iSPOTElement e) {
    if (e == null) {
      return "";
    }

    String name = e.spot_getName();

    e = e.spot_getParent();

    while((e != null) &&!(e instanceof SPOTSequence)) {
      e = e.spot_getParent();
    }

    SPOTNode node = (e == null)
                    ? null
                    : getNode(e.spot_getClassShortName());

    if (node == null) {
      return null;
    }

    String s = getComment(node, name);

    if (s != null) {
      int n = s.indexOf(':');

      if (n != -1) {
        s = s.substring(0, n);
        s = CharScanner.getPiece(s, '~', 2);
      } else {
        s = null;
      }
    }

    return (s == null)
           ? ""
           : s;
  }

  public static boolean hasSameValue(List list, int epos) {
    iSPOTElement first = null;
    SPOTSequence seq;

    for (Object o : list) {
      seq = (SPOTSequence) o;

      if (first == null) {
        first = seq.spot_elementAt(epos);

        continue;
      }

      if (!first.spot_equals(seq.spot_elementAt(epos))) {
        return false;
      }
    }

    return true;
  }

  static List<Column> attributeColumns(iSPOTElement e, aWidget context, iPlatformCellEditingComponent editor,
          List<iSPOTElement> cfgs, boolean eventsOnly) {
    Map<String, String> map = e.spot_getSupportedAttributes();

    if ((map == null) || (map.size() == 0)) {
      return null;
    }

    if (cfgs == null) {
      cfgs = Collections.singletonList(e);
    }

    boolean           sameValue;
    String            lastValue;
    ArrayList<Column> list = new ArrayList<Column>(map.size());
    Iterator<String>  it   = map.keySet().iterator();
    String            name, value;
    Column            c;
    UIFont            font = UIFont.fromFont(context.getFont()).deriveFont(Font.BOLD);
    int               len  = cfgs.size();
    int               i;
    boolean           event;
    iSPOTElement      ee;

    while(it.hasNext()) {
      name  = it.next();
      event = Utilities.isEvent(name);

      if (!eventsOnly || event) {
        c = context.createColumn(name, null, Column.TYPE_STRING, e, null);
        c.setEditable(true);
        list.add(c);
        sameValue = true;
        lastValue = null;

        for (i = 0; i < len; i++) {
          ee    = cfgs.get(i);
          value = Utilities.unNormalizeString(ee.spot_getAttribute(name), event);

          if (len > 1) {
            if ((value != null) && (value.length() > 0)) {
              if (event) {
                c.setHeaderFont(font);
              }

              if ((i > 0) &&!value.equals(lastValue)) {
                sameValue = false;
              }
            }
          }

          lastValue = value;
        }

        if (sameValue) {
          c.setValue(lastValue);
        }

        if (event && (lastValue != null) && (lastValue.length() > 0)) {
          c.setHeaderFont(font);
        }

        if (editor != null) {
          c.setCellEditor(editor);
        }

        if (event) {
          c.categoryName = "Events";
        } else {
          c.categoryName = "Attributes";
        }
      }
    }

    return list;
  }

  static int handleTypes(String stype, Column c) {
    Integer type = types.get(stype);

    return (type == null)
           ? -1
           : type;
  }

}
