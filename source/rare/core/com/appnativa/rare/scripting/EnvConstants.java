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

package com.appnativa.rare.scripting;

import java.util.HashMap;
import java.util.Map;

import com.appnativa.rare.ui.Displayed;
import com.appnativa.rare.ui.Location;
import com.appnativa.rare.ui.RenderType;
import com.appnativa.rare.ui.RenderableDataItem.CursorDisplayType;
import com.appnativa.rare.ui.RenderableDataItem.HorizontalAlign;
import com.appnativa.rare.ui.RenderableDataItem.IconPosition;
import com.appnativa.rare.ui.RenderableDataItem.VerticalAlign;
import com.appnativa.rare.ui.UIBorderHelper;
import com.appnativa.rare.ui.iComposite.CompositeType;
import com.appnativa.rare.ui.event.ExpansionEvent;
import com.appnativa.rare.ui.event.KeyEvent;
import com.appnativa.rare.ui.event.MouseEvent;
import com.appnativa.rare.ui.event.ScaleEvent;
import com.appnativa.rare.ui.event.TextChangeEvent;
import com.appnativa.rare.ui.painter.RenderSpace;
import com.appnativa.rare.ui.painter.iGradientPainter.Direction;
import com.appnativa.rare.ui.painter.iGradientPainter.Type;
import com.appnativa.rare.ui.painter.iImagePainter.ScalingType;
import com.appnativa.rare.widget.iWidget;

/**
 *
 * @author Don DeCoteau
 */
public final class EnvConstants {

  /** Creates a new instance of EnvConstants */
  private EnvConstants() {}

  public static Map<String, Object> getConstants() {
    Map<String, Object> map = new HashMap<String, Object>();

    populate(map);

    return map;
  }

  public static void populate(final Map<String, Object> map) {
    map.put("FILE_SEPARATOR", java.io.File.separator);
    map.put("EMPTY_BORDER", UIBorderHelper.getEmptyBorder());
    //auto generated
    map.put("BORDER_BACK",Integer.valueOf(17));
    map.put("BORDER_BALLOON",Integer.valueOf(18));
    map.put("BORDER_BEVEL_LOWERED",Integer.valueOf(7));
    map.put("BORDER_BEVEL_RAISED",Integer.valueOf(6));
    map.put("BORDER_CUSTOM",Integer.valueOf(31));
    map.put("BORDER_DROP_SHADOW",Integer.valueOf(12));
    map.put("BORDER_EMPTY",Integer.valueOf(3));
    map.put("BORDER_ETCHED_LOWERED",Integer.valueOf(9));
    map.put("BORDER_ETCHED_RAISED",Integer.valueOf(8));
    map.put("BORDER_FRAME_LOWERED",Integer.valueOf(11));
    map.put("BORDER_FRAME_RAISED",Integer.valueOf(10));
    map.put("BORDER_GROUP_BOX",Integer.valueOf(14));
    map.put("BORDER_ICON",Integer.valueOf(15));
    map.put("BORDER_LINE",Integer.valueOf(2));
    map.put("BORDER_LOWERED",Integer.valueOf(5));
    map.put("BORDER_MATTE",Integer.valueOf(16));
    map.put("BORDER_NONE",Integer.valueOf(0));
    map.put("BORDER_RAISED",Integer.valueOf(4));
    map.put("BORDER_SHADOW",Integer.valueOf(13));
    map.put("BORDER_STANDARD",Integer.valueOf(1));
    map.put("BORDER_TITLED",Integer.valueOf(19));
    map.put("COMPOSITETYPE_CLEAR",CompositeType.CLEAR);
    map.put("COMPOSITETYPE_COPY",CompositeType.COPY);
    map.put("COMPOSITETYPE_DARKEN",CompositeType.DARKEN);
    map.put("COMPOSITETYPE_DST_ATOP",CompositeType.DST_ATOP);
    map.put("COMPOSITETYPE_DST_IN",CompositeType.DST_IN);
    map.put("COMPOSITETYPE_DST_OUT",CompositeType.DST_OUT);
    map.put("COMPOSITETYPE_DST_OVER",CompositeType.DST_OVER);
    map.put("COMPOSITETYPE_LIGHTEN",CompositeType.LIGHTEN);
    map.put("COMPOSITETYPE_SRC_ATOP",CompositeType.SRC_ATOP);
    map.put("COMPOSITETYPE_SRC_IN",CompositeType.SRC_IN);
    map.put("COMPOSITETYPE_SRC_OUT",CompositeType.SRC_OUT);
    map.put("COMPOSITETYPE_SRC_OVER",CompositeType.SRC_OVER);
    map.put("COMPOSITETYPE_XOR",CompositeType.XOR);
    map.put("CURSORDISPLAYTYPE_COMPONENT",CursorDisplayType.COMPONENT);
    map.put("CURSORDISPLAYTYPE_HYPERLINKS",CursorDisplayType.HYPERLINKS);
    map.put("CURSORDISPLAYTYPE_HYPERLINKS_AND_ICON",CursorDisplayType.HYPERLINKS_AND_ICON);
    map.put("CURSORDISPLAYTYPE_ICON",CursorDisplayType.ICON);
    map.put("CURSORDISPLAYTYPE_ITEM",CursorDisplayType.ITEM);
    map.put("CURSORDISPLAYTYPE_TEXT",CursorDisplayType.TEXT);
    map.put("CURSORDISPLAYTYPE_TEXT_AND_ICON",CursorDisplayType.TEXT_AND_ICON);
    map.put("DISPLAYED_ALWAYS",Displayed.ALWAYS);
    map.put("DISPLAYED_BEFORE_FIRST_FOCUS",Displayed.BEFORE_FIRST_FOCUS);
    map.put("DISPLAYED_BEFORE_INTERACTION",Displayed.BEFORE_INTERACTION);
    map.put("DISPLAYED_WHEN_CHILD_WIDGET_FOCUSED",Displayed.WHEN_CHILD_WIDGET_FOCUSED);
    map.put("DISPLAYED_WHEN_CHILD_WIDGET_NOT_FOCUSED",Displayed.WHEN_CHILD_WIDGET_NOT_FOCUSED);
    map.put("DISPLAYED_WHEN_EMPTY",Displayed.WHEN_EMPTY);
    map.put("DISPLAYED_WHEN_FOCUSED",Displayed.WHEN_FOCUSED);
    map.put("DISPLAYED_WHEN_NOT_FOCUSED",Displayed.WHEN_NOT_FOCUSED);
    map.put("DISPLAYED_WHEN_PARENT_WIDGET_FOCUSED",Displayed.WHEN_PARENT_WIDGET_FOCUSED);
    map.put("DISPLAYED_WHEN_PARENT_WIDGET_NOT_FOCUSED",Displayed.WHEN_PARENT_WIDGET_NOT_FOCUSED);
    map.put("DISPLAYED_WHEN_WIDGET_FOCUSED",Displayed.WHEN_WIDGET_FOCUSED);
    map.put("DISPLAYED_WHEN_WIDGET_NOT_FOCUSED",Displayed.WHEN_WIDGET_NOT_FOCUSED);
    map.put("DISPLAYED_WHEN_WINDOW_FOCUSED",Displayed.WHEN_WINDOW_FOCUSED);
    map.put("DISPLAYED_WHEN_WINDOW_NOT_FOCUSED",Displayed.WHEN_WINDOW_NOT_FOCUSED);
    map.put("DND_ACTION_COPY",Integer.valueOf(1));
    map.put("DND_ACTION_COPY_OR_MOVE",Integer.valueOf(3));
    map.put("DND_ACTION_LINK",Integer.valueOf(1073741824));
    map.put("DND_ACTION_MOVE",Integer.valueOf(2));
    map.put("DND_ACTION_NONE",Integer.valueOf(0));
    map.put("DND_ACTION_REFERENCE",Integer.valueOf(1073741824));
    map.put("EXPANSIONEVENT_HAS_COLLAPSED",ExpansionEvent.Type.HAS_COLLAPSED);
    map.put("EXPANSIONEVENT_HAS_EXPANDED",ExpansionEvent.Type.HAS_EXPANDED);
    map.put("EXPANSIONEVENT_WILL_COLLAPSE",ExpansionEvent.Type.WILL_COLLAPSE);
    map.put("EXPANSIONEVENT_WILL_EXPAND",ExpansionEvent.Type.WILL_EXPAND);
    map.put("FONT_BOLD",Integer.valueOf(1));
    map.put("FONT_ITALIC",Integer.valueOf(2));
    map.put("FONT_PLAIN",Integer.valueOf(0));
    map.put("GRADIENTDIRECTION_CENTER",Direction.CENTER);
    map.put("GRADIENTDIRECTION_DIAGONAL_BOTTOM_LEFT",Direction.DIAGONAL_BOTTOM_LEFT);
    map.put("GRADIENTDIRECTION_DIAGONAL_BOTTOM_RIGHT",Direction.DIAGONAL_BOTTOM_RIGHT);
    map.put("GRADIENTDIRECTION_DIAGONAL_TOP_LEFT",Direction.DIAGONAL_TOP_LEFT);
    map.put("GRADIENTDIRECTION_DIAGONAL_TOP_RIGHT",Direction.DIAGONAL_TOP_RIGHT);
    map.put("GRADIENTDIRECTION_HORIZONTAL_LEFT",Direction.HORIZONTAL_LEFT);
    map.put("GRADIENTDIRECTION_HORIZONTAL_RIGHT",Direction.HORIZONTAL_RIGHT);
    map.put("GRADIENTDIRECTION_VERTICAL_BOTTOM",Direction.VERTICAL_BOTTOM);
    map.put("GRADIENTDIRECTION_VERTICAL_TOP",Direction.VERTICAL_TOP);
    map.put("GRADIENTTYPE_LINEAR",Type.LINEAR);
    map.put("GRADIENTTYPE_LINEAR_REFLECT",Type.LINEAR_REFLECT);
    map.put("GRADIENTTYPE_LINEAR_REPEAT",Type.LINEAR_REPEAT);
    map.put("GRADIENTTYPE_RADIAL",Type.RADIAL);
    map.put("ICONPOSITION_AUTO",IconPosition.AUTO);
    map.put("ICONPOSITION_BOTTOM_CENTER",IconPosition.BOTTOM_CENTER);
    map.put("ICONPOSITION_BOTTOM_LEFT",IconPosition.BOTTOM_LEFT);
    map.put("ICONPOSITION_BOTTOM_RIGHT",IconPosition.BOTTOM_RIGHT);
    map.put("ICONPOSITION_LEADING",IconPosition.LEADING);
    map.put("ICONPOSITION_LEFT",IconPosition.LEFT);
    map.put("ICONPOSITION_RIGHT",IconPosition.RIGHT);
    map.put("ICONPOSITION_RIGHT_JUSTIFIED",IconPosition.RIGHT_JUSTIFIED);
    map.put("ICONPOSITION_TOP_CENTER",IconPosition.TOP_CENTER);
    map.put("ICONPOSITION_TOP_LEFT",IconPosition.TOP_LEFT);
    map.put("ICONPOSITION_TOP_RIGHT",IconPosition.TOP_RIGHT);
    map.put("ICONPOSITION_TRAILING",IconPosition.TRAILING);
    map.put("KEYEVENT_KEY_DOWN",KeyEvent.Type.KEY_DOWN);
    map.put("KEYEVENT_KEY_TYPED",KeyEvent.Type.KEY_TYPED);
    map.put("KEYEVENT_KEY_UNKNOWN",KeyEvent.Type.KEY_UNKNOWN);
    map.put("KEYEVENT_KEY_UP",KeyEvent.Type.KEY_UP);
    map.put("LOCATION_AUTO",Location.AUTO);
    map.put("LOCATION_BOTTOM",Location.BOTTOM);
    map.put("LOCATION_CENTER",Location.CENTER);
    map.put("LOCATION_LEFT",Location.LEFT);
    map.put("LOCATION_RIGHT",Location.RIGHT);
    map.put("LOCATION_TOP",Location.TOP);
    map.put("MOUSEEVENT_FLING",MouseEvent.Type.FLING);
    map.put("MOUSEEVENT_LONG_PRESS",MouseEvent.Type.LONG_PRESS);
    map.put("MOUSEEVENT_MOUSE_CLICK",MouseEvent.Type.MOUSE_CLICK);
    map.put("MOUSEEVENT_MOUSE_DBLCLICK",MouseEvent.Type.MOUSE_DBLCLICK);
    map.put("MOUSEEVENT_MOUSE_DOWN",MouseEvent.Type.MOUSE_DOWN);
    map.put("MOUSEEVENT_MOUSE_DRAG",MouseEvent.Type.MOUSE_DRAG);
    map.put("MOUSEEVENT_MOUSE_ENTER",MouseEvent.Type.MOUSE_ENTER);
    map.put("MOUSEEVENT_MOUSE_EXIT",MouseEvent.Type.MOUSE_EXIT);
    map.put("MOUSEEVENT_MOUSE_MOVE",MouseEvent.Type.MOUSE_MOVE);
    map.put("MOUSEEVENT_MOUSE_SCALE",MouseEvent.Type.MOUSE_SCALE);
    map.put("MOUSEEVENT_MOUSE_UNKNOWN",MouseEvent.Type.MOUSE_UNKNOWN);
    map.put("MOUSEEVENT_MOUSE_UP",MouseEvent.Type.MOUSE_UP);
    map.put("MOUSEEVENT_SCROLL",MouseEvent.Type.SCROLL);
    map.put("RENDERSPACE_COMPONENT",RenderSpace.COMPONENT);
    map.put("RENDERSPACE_WITHIN_BORDER",RenderSpace.WITHIN_BORDER);
    map.put("RENDERSPACE_WITHIN_MARGIN",RenderSpace.WITHIN_MARGIN);
    map.put("RENDERTYPE_CENTERED",RenderType.CENTERED);
    map.put("RENDERTYPE_HORIZONTAL_TILE",RenderType.HORIZONTAL_TILE);
    map.put("RENDERTYPE_LEFT_MIDDLE",RenderType.LEFT_MIDDLE);
    map.put("RENDERTYPE_LOWER_LEFT",RenderType.LOWER_LEFT);
    map.put("RENDERTYPE_LOWER_MIDDLE",RenderType.LOWER_MIDDLE);
    map.put("RENDERTYPE_LOWER_RIGHT",RenderType.LOWER_RIGHT);
    map.put("RENDERTYPE_RIGHT_MIDDLE",RenderType.RIGHT_MIDDLE);
    map.put("RENDERTYPE_STRETCHED",RenderType.STRETCHED);
    map.put("RENDERTYPE_STRETCH_HEIGHT",RenderType.STRETCH_HEIGHT);
    map.put("RENDERTYPE_STRETCH_HEIGHT_MIDDLE",RenderType.STRETCH_HEIGHT_MIDDLE);
    map.put("RENDERTYPE_STRETCH_WIDTH",RenderType.STRETCH_WIDTH);
    map.put("RENDERTYPE_STRETCH_WIDTH_MIDDLE",RenderType.STRETCH_WIDTH_MIDDLE);
    map.put("RENDERTYPE_TILED",RenderType.TILED);
    map.put("RENDERTYPE_UPPER_LEFT",RenderType.UPPER_LEFT);
    map.put("RENDERTYPE_UPPER_MIDDLE",RenderType.UPPER_MIDDLE);
    map.put("RENDERTYPE_UPPER_RIGHT",RenderType.UPPER_RIGHT);
    map.put("RENDERTYPE_VERTICAL_TILE",RenderType.VERTICAL_TILE);
    map.put("RENDERTYPE_XY",RenderType.XY);
    map.put("SCALEEVENT_SCALE",ScaleEvent.Type.SCALE);
    map.put("SCALEEVENT_SCALE_BEGIN",ScaleEvent.Type.SCALE_BEGIN);
    map.put("SCALEEVENT_SCALE_END",ScaleEvent.Type.SCALE_END);
    map.put("SCALING_BICUBIC",ScalingType.BICUBIC);
    map.put("SCALING_BICUBIC_CACHED",ScalingType.BICUBIC_CACHED);
    map.put("SCALING_BILINEAR",ScalingType.BILINEAR);
    map.put("SCALING_BILINEAR_CACHED",ScalingType.BILINEAR_CACHED);
    map.put("SCALING_NEAREST_NEIGHBOR",ScalingType.NEAREST_NEIGHBOR);
    map.put("SCALING_NEAREST_NEIGHBOR_CACHED",ScalingType.NEAREST_NEIGHBOR_CACHED);
    map.put("SCALING_PROGRESSIVE_BICUBIC",ScalingType.PROGRESSIVE_BICUBIC);
    map.put("SCALING_PROGRESSIVE_BICUBIC_CACHED",ScalingType.PROGRESSIVE_BICUBIC_CACHED);
    map.put("SCALING_PROGRESSIVE_BILINEAR",ScalingType.PROGRESSIVE_BILINEAR);
    map.put("SCALING_PROGRESSIVE_BILINEAR_CACHED",ScalingType.PROGRESSIVE_BILINEAR_CACHED);
    map.put("SCALING_PROGRESSIVE_NEAREST_NEIGHBOR",ScalingType.PROGRESSIVE_NEAREST_NEIGHBOR);
    map.put("SCALING_PROGRESSIVE_NEAREST_NEIGHBOR_CACHED",ScalingType.PROGRESSIVE_NEAREST_NEIGHBOR_CACHED);
    map.put("TARGET_BLANK","_blank");
    map.put("TARGET_MENUBAR","_menubar");
    map.put("TARGET_NEW_POPUP","_new_popup");
    map.put("TARGET_NEW_WINDOW","_new_window");
    map.put("TARGET_NULL","_null");
    map.put("TARGET_PARENT","_parent");
    map.put("TARGET_SELF","_self");
    map.put("TARGET_TOOLBAR","_toolbar");
    map.put("TARGET_WORKSPACE","_workspace");
    map.put("TEXTCHANGEEVENT_CHANGED",TextChangeEvent.Type.CHANGED);
    map.put("TEXTCHANGEEVENT_CHANGING",TextChangeEvent.Type.CHANGING);
    map.put("TEXTHALIGN_AUTO",HorizontalAlign.AUTO);
    map.put("TEXTHALIGN_CENTER",HorizontalAlign.CENTER);
    map.put("TEXTHALIGN_FILL",HorizontalAlign.FILL);
    map.put("TEXTHALIGN_LEADING",HorizontalAlign.LEADING);
    map.put("TEXTHALIGN_LEFT",HorizontalAlign.LEFT);
    map.put("TEXTHALIGN_RIGHT",HorizontalAlign.RIGHT);
    map.put("TEXTHALIGN_TRAILING",HorizontalAlign.TRAILING);
    map.put("TEXTVALIGN_AUTO",VerticalAlign.AUTO);
    map.put("TEXTVALIGN_BOTTOM",VerticalAlign.BOTTOM);
    map.put("TEXTVALIGN_CENTER",VerticalAlign.CENTER);
    map.put("TEXTVALIGN_FILL",VerticalAlign.FILL);
    map.put("TEXTVALIGN_TOP",VerticalAlign.TOP);
    map.put("WHEN_ANCESTOR_OF_FOCUSED_COMPONENT",Integer.valueOf(1));
    map.put("WHEN_FOCUSED",Integer.valueOf(0));
    map.put("WHEN_IN_FOCUSED_WINDOW",Integer.valueOf(2));
    map.put("WIDGETTYPE_BEAN",iWidget.WidgetType.Bean);
    map.put("WIDGETTYPE_CHART",iWidget.WidgetType.Chart);
    map.put("WIDGETTYPE_CHECKBOX",iWidget.WidgetType.CheckBox);
    map.put("WIDGETTYPE_CHECKBOXLIST",iWidget.WidgetType.CheckBoxList);
    map.put("WIDGETTYPE_CHECKBOXTREE",iWidget.WidgetType.CheckBoxTree);
    map.put("WIDGETTYPE_COLLAPSIBLEPANE",iWidget.WidgetType.CollapsiblePane);
    map.put("WIDGETTYPE_COLORCHOOSER",iWidget.WidgetType.ColorChooser);
    map.put("WIDGETTYPE_COMBOBOX",iWidget.WidgetType.ComboBox);
    map.put("WIDGETTYPE_CUSTOM",iWidget.WidgetType.Custom);
    map.put("WIDGETTYPE_DATECHOOSER",iWidget.WidgetType.DateChooser);
    map.put("WIDGETTYPE_DOCUMENTPANE",iWidget.WidgetType.DocumentPane);
    map.put("WIDGETTYPE_FORM",iWidget.WidgetType.Form);
    map.put("WIDGETTYPE_GRIDPANE",iWidget.WidgetType.GridPane);
    map.put("WIDGETTYPE_GROUPBOX",iWidget.WidgetType.GroupBox);
    map.put("WIDGETTYPE_IMAGEPANE",iWidget.WidgetType.ImagePane);
    map.put("WIDGETTYPE_LABEL",iWidget.WidgetType.Label);
    map.put("WIDGETTYPE_LINE",iWidget.WidgetType.Line);
    map.put("WIDGETTYPE_LISTBOX",iWidget.WidgetType.ListBox);
    map.put("WIDGETTYPE_MEDIAPLAYER",iWidget.WidgetType.MediaPlayer);
    map.put("WIDGETTYPE_MENUBAR",iWidget.WidgetType.MenuBar);
    map.put("WIDGETTYPE_NAVIGATOR",iWidget.WidgetType.Navigator);
    map.put("WIDGETTYPE_PASSWORDFIELD",iWidget.WidgetType.PasswordField);
    map.put("WIDGETTYPE_PROGRESSBAR",iWidget.WidgetType.ProgressBar);
    map.put("WIDGETTYPE_PUSHBUTTON",iWidget.WidgetType.PushButton);
    map.put("WIDGETTYPE_RADIOBUTTON",iWidget.WidgetType.RadioButton);
    map.put("WIDGETTYPE_SCROLLPANE",iWidget.WidgetType.ScrollPane);
    map.put("WIDGETTYPE_SLIDER",iWidget.WidgetType.Slider);
    map.put("WIDGETTYPE_SPINNER",iWidget.WidgetType.Spinner);
    map.put("WIDGETTYPE_SPLITPANE",iWidget.WidgetType.SplitPane);
    map.put("WIDGETTYPE_STACKPANE",iWidget.WidgetType.StackPane);
    map.put("WIDGETTYPE_STATUSBAR",iWidget.WidgetType.StatusBar);
    map.put("WIDGETTYPE_TABLE",iWidget.WidgetType.Table);
    map.put("WIDGETTYPE_TABPANE",iWidget.WidgetType.TabPane);
    map.put("WIDGETTYPE_TEXTAREA",iWidget.WidgetType.TextArea);
    map.put("WIDGETTYPE_TEXTFIELD",iWidget.WidgetType.TextField);
    map.put("WIDGETTYPE_TOOLBAR",iWidget.WidgetType.ToolBar);
    map.put("WIDGETTYPE_TREE",iWidget.WidgetType.Tree);
    map.put("WIDGETTYPE_WEBBROWSER",iWidget.WidgetType.WebBrowser);
    map.put("WIDGETTYPE_WIDGETPANE",iWidget.WidgetType.WidgetPane);
    map.put("WIDGETTYPE_WINDOW",iWidget.WidgetType.Window);

  }
}
