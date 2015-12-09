/*
 * Copyright appNativa Inc. All Rights Reserved.
 *
 * This file is part of the Real-time Application Rendering Engine (RARE).
 *
 * RARE is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */

//package com.appnativa.rare.scripting;
//
//import java.awt.Font;
//import java.awt.dnd.DnDConstants;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.Locale;
//import java.util.Map;
//import java.util.Map.Entry;
//import java.util.TreeMap;
//
//import javax.swing.JComponent;
//
//import com.appnativa.rare.spot.Link;
//import com.appnativa.rare.spot.Widget;
//import com.appnativa.rare.ui.Displayed;
//import com.appnativa.rare.ui.Location;
//import com.appnativa.rare.ui.RenderType;
//import com.appnativa.rare.ui.RenderableDataItem.CursorDisplayType;
//import com.appnativa.rare.ui.RenderableDataItem.HorizontalAlign;
//import com.appnativa.rare.ui.RenderableDataItem.IconPosition;
//import com.appnativa.rare.ui.RenderableDataItem.VerticalAlign;
//import com.appnativa.rare.ui.iComposite.CompositeType;
//import com.appnativa.rare.ui.event.ExpansionEvent;
//import com.appnativa.rare.ui.event.KeyEvent;
//import com.appnativa.rare.ui.event.MouseEvent;
//import com.appnativa.rare.ui.event.ScaleEvent;
//import com.appnativa.rare.ui.event.TextChangeEvent;
//import com.appnativa.rare.ui.painter.RenderSpace;
//import com.appnativa.rare.ui.painter.iGradientPainter.Direction;
//import com.appnativa.rare.ui.painter.iGradientPainter.Type;
//import com.appnativa.rare.ui.painter.iImagePainter.ScalingType;
//import com.appnativa.rare.widget.iWidget;
//
///**
// * This class in not used at runtime.
// * It is uncommented and used during development to create the data ine the EnvConstants.java file
// *
// * com.appnativa.rare.scripting.Utils.createConstantsInitializer()
// */
//public class Utils {
//  private Utils() {}
//
//  public static String createConstantsInitializer() {
//    TreeMap<String, Object> map = new TreeMap<String, Object>();
//
//    populateConstants(map);
//
//    Iterator<Entry<String, Object>> it = map.entrySet().iterator();
//    Entry<String, Object>           e;
//    StringBuilder                   sb = new StringBuilder();
//    Object                          o;
//
//    while(it.hasNext()) {
//      e = it.next();
//      o = e.getValue();
//
//      if (o == null) {
//        continue;
//      }
//
//      sb.append("    map.put(\"").append(e.getKey()).append("\",");
//
//      if (o instanceof String) {
//        sb.append("\"").append((String) o).append("\");\r\n");
//      } else if (o instanceof RenderType) {
//        sb.append("RenderType.").append(o.toString()).append(");\r\n");
//      } else if (o instanceof RenderSpace) {
//        sb.append("RenderSpace.").append(o.toString()).append(");\r\n");
//      } else if (o instanceof CursorDisplayType) {
//        sb.append("CursorDisplayType.").append(o.toString()).append(");\r\n");
//      } else if (o instanceof Displayed) {
//        sb.append("Displayed.").append(o.toString()).append(");\r\n");
//      } else if (o instanceof ScalingType) {
//        sb.append("ScalingType.").append(o.toString()).append(");\r\n");
//      } else if (o instanceof CompositeType) {
//        sb.append("CompositeType.").append(o.toString()).append(");\r\n");
//      } else if (o instanceof Direction) {
//        sb.append("Direction.").append(o.toString()).append(");\r\n");
//      } else if (o instanceof IconPosition) {
//        sb.append("IconPosition.").append(o.toString()).append(");\r\n");
//      } else if (o instanceof VerticalAlign) {
//        sb.append("VerticalAlign.").append(o.toString()).append(");\r\n");
//      } else if (o instanceof HorizontalAlign) {
//        sb.append("HorizontalAlign.").append(o.toString()).append(");\r\n");
//      } else if (o instanceof Type) {
//        sb.append("Type.").append(o.toString()).append(");\r\n");
//      } else if (o instanceof Location) {
//        sb.append("Location.").append(o.toString()).append(");\r\n");
//      } else if (o instanceof iWidget.WidgetType) {
//        sb.append("iWidget.WidgetType.").append(o.toString()).append(");\r\n");
//      } else if (o instanceof ScaleEvent.Type) {
//        sb.append("ScaleEvent.Type.").append(o.toString()).append(");\r\n");
//      } else if (o instanceof MouseEvent.Type) {
//        sb.append("MouseEvent.Type.").append(o.toString()).append(");\r\n");
//      } else if (o instanceof ExpansionEvent.Type) {
//        sb.append("ExpansionEvent.Type.").append(o.toString()).append(");\r\n");
//      } else if (o instanceof KeyEvent.Type) {
//        sb.append("KeyEvent.Type.").append(o.toString()).append(");\r\n");
//      } else if (o instanceof TextChangeEvent.Type) {
//        sb.append("TextChangeEvent.Type.").append(o.toString()).append(");\r\n");
//      } else if (o instanceof Integer) {
//        sb.append("Integer.valueOf(").append(o.toString()).append("));\r\n");
//      } else {
//        System.out.println("Unknown constant type:" + o.getClass().getName());
//      }
//    }
//
//    return sb.toString();
//  }
//
//  public static void main(String args[]) {
//    //System.out.println(createConstantsInitializer());
//    document();
//  }
//  public static void document() {
//    TreeMap<String, Object> map = new TreeMap<String, Object>();
//
//    populateConstants(map);
//    Iterator<Entry<String, Object>> it = map.entrySet().iterator();
//    Entry<String, Object>           e;
//    Object                          o;
//
//    while(it.hasNext()) {
//      e=it.next();
//      o = e.getValue();
//
//      if (o == null) {
//        continue;
//      }
//      System.out.println("<tr><td>"+e.getKey()+"</td><td>"+o+"</td></tr>");
//    }
//    
//  }
//
//  public static void populateConstants(Map<String, Object> constants) {
//    HashMap<String, Integer> map = new HashMap<String, Integer>();
//
//    new Widget.CBorder().spot_generateProperties(map, "BORDER_", 1);
//
//    Link link = new Link();
//
//    link.target.spot_generatePropertiesStr(constants, "TARGET", 1);
//    constants.putAll(map);
//    constants.put("DND_ACTION_NONE", Integer.valueOf(DnDConstants.ACTION_NONE));
//    constants.put("DND_ACTION_COPY", Integer.valueOf(DnDConstants.ACTION_COPY));
//    constants.put("DND_ACTION_COPY_OR_MOVE", Integer.valueOf(DnDConstants.ACTION_COPY_OR_MOVE));
//    constants.put("DND_ACTION_LINK", Integer.valueOf(DnDConstants.ACTION_LINK));
//    constants.put("DND_ACTION_MOVE", Integer.valueOf(DnDConstants.ACTION_MOVE));
//    constants.put("DND_ACTION_REFERENCE", Integer.valueOf(DnDConstants.ACTION_REFERENCE));
//    constants.put("FONT_BOLD", Integer.valueOf(Font.BOLD));
//    constants.put("FONT_PLAIN", Integer.valueOf(Font.PLAIN));
//    constants.put("FONT_ITALIC", Integer.valueOf(Font.ITALIC));
//    constants.put("WHEN_FOCUSED", JComponent.WHEN_FOCUSED);
//    constants.put("WHEN_IN_FOCUSED_WINDOW", JComponent.WHEN_IN_FOCUSED_WINDOW);
//    constants.put("WHEN_ANCESTOR_OF_FOCUSED_COMPONENT", JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
//
//    Location[] ltype = Location.values();
//
//    for (int i = 0; i < ltype.length; i++) {
//      constants.put("LOCATION_" + ltype[i].toString(), ltype[i]);
//    }
//    Direction[] gdtype = Direction.values();
//
//    for (int i = 0; i < gdtype.length; i++) {
//      constants.put("GRADIENTDIRECTION_" + gdtype[i].toString(), gdtype[i]);
//    }
//
//    Type[] gtype = Type.values();
//
//    for (int i = 0; i < gtype.length; i++) {
//      constants.put("GRADIENTTYPE_" + gtype[i].toString(), gtype[i]);
//    }
//
//    CompositeType[] ctype = CompositeType.values();
//
//    for (int i = 0; i < ctype.length; i++) {
//      constants.put("COMPOSITETYPE_" + ctype[i].toString(), ctype[i]);
//    }
//
//    RenderType[] rtype = RenderType.values();
//
//    for (int i = 0; i < rtype.length; i++) {
//      constants.put("RENDERTYPE_" + rtype[i].toString(), rtype[i]);
//    }
//
//    RenderSpace[] rstype = RenderSpace.values();
//
//    for (int i = 0; i < rstype.length; i++) {
//      constants.put("RENDERSPACE_" + rstype[i].toString(), rstype[i]);
//    }
//
//    Displayed[] dtype = Displayed.values();
//
//    for (int i = 0; i < dtype.length; i++) {
//      constants.put("DISPLAYED_" + dtype[i].toString(), dtype[i]);
//    }
//
//    CursorDisplayType[] cdtype = CursorDisplayType.values();
//
//    for (int i = 0; i < cdtype.length; i++) {
//      constants.put("CURSORDISPLAYTYPE_" + cdtype[i].toString(), cdtype[i]);
//    }
//
//    ScalingType[] stype = ScalingType.values();
//
//    for (int i = 0; i < stype.length; i++) {
//      constants.put("SCALING_" + stype[i].toString(), stype[i]);
//    }
//
//    iWidget.WidgetType[] wtype = iWidget.WidgetType.values();
//
//    for (int i = 0; i < wtype.length; i++) {
//      constants.put("WIDGETTYPE_" + wtype[i].toString().toUpperCase(Locale.ENGLISH), wtype[i]);
//    }
//
//    HorizontalAlign[] ha = HorizontalAlign.values();
//
//    for (int i = 0; i < ha.length; i++) {
//      constants.put("TEXTHALIGN_" + ha[i].toString(), ha[i]);
//    }
//
//    VerticalAlign[] va = VerticalAlign.values();
//
//    for (int i = 0; i < va.length; i++) {
//      constants.put("TEXTVALIGN_" + va[i].toString(), va[i]);
//    }
//
//    IconPosition[] ip = IconPosition.values();
//
//    for (int i = 0; i < ip.length; i++) {
//      constants.put("ICONPOSITION_" + ip[i].toString(), ip[i]);
//    }
//
//    KeyEvent.Type[] ke = KeyEvent.Type.values();
//
//    for (int i = 0; i < ke.length; i++) {
//      constants.put("KEYEVENT_" + ke[i].toString(), ke[i]);
//    }
//    MouseEvent.Type[] me = MouseEvent.Type.values();
//
//    for (int i = 0; i < me.length; i++) {
//      constants.put("MOUSEEVENT_" + me[i].toString(), me[i]);
//    }
//    ScaleEvent.Type[] se = ScaleEvent.Type.values();
//
//    for (int i = 0; i < se.length; i++) {
//      constants.put("SCALEEVENT_" + se[i].toString(), se[i]);
//    }
//    ExpansionEvent.Type[] ee = ExpansionEvent.Type.values();
//
//    for (int i = 0; i < ee.length; i++) {
//      constants.put("EXPANSIONEVENT_" + ee[i].toString(), ee[i]);
//    }
//    TextChangeEvent.Type[] te = TextChangeEvent.Type.values();
//
//    for (int i = 0; i < te.length; i++) {
//      constants.put("TEXTCHANGEEVENT_" + te[i].toString(), te[i]);
//    }
//  }
//}
