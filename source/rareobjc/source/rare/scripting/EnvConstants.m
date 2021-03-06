//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/scripting/EnvConstants.java
//
//  Created by decoteaud on 3/11/16.
//

#include "com/appnativa/rare/scripting/EnvConstants.h"
#include "com/appnativa/rare/ui/Displayed.h"
#include "com/appnativa/rare/ui/Location.h"
#include "com/appnativa/rare/ui/RenderType.h"
#include "com/appnativa/rare/ui/RenderableDataItem.h"
#include "com/appnativa/rare/ui/UIBorderHelper.h"
#include "com/appnativa/rare/ui/event/ExpansionEvent.h"
#include "com/appnativa/rare/ui/event/KeyEvent.h"
#include "com/appnativa/rare/ui/event/MouseEvent.h"
#include "com/appnativa/rare/ui/event/ScaleEvent.h"
#include "com/appnativa/rare/ui/event/TextChangeEvent.h"
#include "com/appnativa/rare/ui/iComposite.h"
#include "com/appnativa/rare/ui/painter/RenderSpace.h"
#include "com/appnativa/rare/ui/painter/iGradientPainter.h"
#include "com/appnativa/rare/ui/painter/iImagePainter.h"
#include "com/appnativa/rare/widget/iWidget.h"
#include "java/io/File.h"
#include "java/lang/Integer.h"
#include "java/util/HashMap.h"
#include "java/util/Map.h"

@implementation RAREEnvConstants

- (id)init {
  return [super init];
}

+ (id<JavaUtilMap>)getConstants {
  id<JavaUtilMap> map = [[JavaUtilHashMap alloc] init];
  [RAREEnvConstants populateWithJavaUtilMap:map];
  return map;
}

+ (void)populateWithJavaUtilMap:(id<JavaUtilMap>)map {
  (void) [((id<JavaUtilMap>) nil_chk(map)) putWithId:@"FILE_SEPARATOR" withId:[JavaIoFile separator]];
  (void) [map putWithId:@"EMPTY_BORDER" withId:[RAREUIBorderHelper getEmptyBorder]];
  (void) [map putWithId:@"BORDER_BACK" withId:[JavaLangInteger valueOfWithInt:17]];
  (void) [map putWithId:@"BORDER_BALLOON" withId:[JavaLangInteger valueOfWithInt:18]];
  (void) [map putWithId:@"BORDER_BEVEL_LOWERED" withId:[JavaLangInteger valueOfWithInt:7]];
  (void) [map putWithId:@"BORDER_BEVEL_RAISED" withId:[JavaLangInteger valueOfWithInt:6]];
  (void) [map putWithId:@"BORDER_CUSTOM" withId:[JavaLangInteger valueOfWithInt:31]];
  (void) [map putWithId:@"BORDER_DROP_SHADOW" withId:[JavaLangInteger valueOfWithInt:12]];
  (void) [map putWithId:@"BORDER_EMPTY" withId:[JavaLangInteger valueOfWithInt:3]];
  (void) [map putWithId:@"BORDER_ETCHED_LOWERED" withId:[JavaLangInteger valueOfWithInt:9]];
  (void) [map putWithId:@"BORDER_ETCHED_RAISED" withId:[JavaLangInteger valueOfWithInt:8]];
  (void) [map putWithId:@"BORDER_FRAME_LOWERED" withId:[JavaLangInteger valueOfWithInt:11]];
  (void) [map putWithId:@"BORDER_FRAME_RAISED" withId:[JavaLangInteger valueOfWithInt:10]];
  (void) [map putWithId:@"BORDER_GROUP_BOX" withId:[JavaLangInteger valueOfWithInt:14]];
  (void) [map putWithId:@"BORDER_ICON" withId:[JavaLangInteger valueOfWithInt:15]];
  (void) [map putWithId:@"BORDER_LINE" withId:[JavaLangInteger valueOfWithInt:2]];
  (void) [map putWithId:@"BORDER_LOWERED" withId:[JavaLangInteger valueOfWithInt:5]];
  (void) [map putWithId:@"BORDER_MATTE" withId:[JavaLangInteger valueOfWithInt:16]];
  (void) [map putWithId:@"BORDER_NONE" withId:[JavaLangInteger valueOfWithInt:0]];
  (void) [map putWithId:@"BORDER_RAISED" withId:[JavaLangInteger valueOfWithInt:4]];
  (void) [map putWithId:@"BORDER_SHADOW" withId:[JavaLangInteger valueOfWithInt:13]];
  (void) [map putWithId:@"BORDER_STANDARD" withId:[JavaLangInteger valueOfWithInt:1]];
  (void) [map putWithId:@"BORDER_TITLED" withId:[JavaLangInteger valueOfWithInt:19]];
  (void) [map putWithId:@"COMPOSITETYPE_CLEAR" withId:[RAREiComposite_CompositeTypeEnum CLEAR]];
  (void) [map putWithId:@"COMPOSITETYPE_COPY" withId:[RAREiComposite_CompositeTypeEnum COPY]];
  (void) [map putWithId:@"COMPOSITETYPE_DARKEN" withId:[RAREiComposite_CompositeTypeEnum DARKEN]];
  (void) [map putWithId:@"COMPOSITETYPE_DST_ATOP" withId:[RAREiComposite_CompositeTypeEnum DST_ATOP]];
  (void) [map putWithId:@"COMPOSITETYPE_DST_IN" withId:[RAREiComposite_CompositeTypeEnum DST_IN]];
  (void) [map putWithId:@"COMPOSITETYPE_DST_OUT" withId:[RAREiComposite_CompositeTypeEnum DST_OUT]];
  (void) [map putWithId:@"COMPOSITETYPE_DST_OVER" withId:[RAREiComposite_CompositeTypeEnum DST_OVER]];
  (void) [map putWithId:@"COMPOSITETYPE_LIGHTEN" withId:[RAREiComposite_CompositeTypeEnum LIGHTEN]];
  (void) [map putWithId:@"COMPOSITETYPE_SRC_ATOP" withId:[RAREiComposite_CompositeTypeEnum SRC_ATOP]];
  (void) [map putWithId:@"COMPOSITETYPE_SRC_IN" withId:[RAREiComposite_CompositeTypeEnum SRC_IN]];
  (void) [map putWithId:@"COMPOSITETYPE_SRC_OUT" withId:[RAREiComposite_CompositeTypeEnum SRC_OUT]];
  (void) [map putWithId:@"COMPOSITETYPE_SRC_OVER" withId:[RAREiComposite_CompositeTypeEnum SRC_OVER]];
  (void) [map putWithId:@"COMPOSITETYPE_XOR" withId:[RAREiComposite_CompositeTypeEnum XOR]];
  (void) [map putWithId:@"CURSORDISPLAYTYPE_COMPONENT" withId:[RARERenderableDataItem_CursorDisplayTypeEnum COMPONENT]];
  (void) [map putWithId:@"CURSORDISPLAYTYPE_HYPERLINKS" withId:[RARERenderableDataItem_CursorDisplayTypeEnum HYPERLINKS]];
  (void) [map putWithId:@"CURSORDISPLAYTYPE_HYPERLINKS_AND_ICON" withId:[RARERenderableDataItem_CursorDisplayTypeEnum HYPERLINKS_AND_ICON]];
  (void) [map putWithId:@"CURSORDISPLAYTYPE_ICON" withId:[RARERenderableDataItem_CursorDisplayTypeEnum ICON]];
  (void) [map putWithId:@"CURSORDISPLAYTYPE_ITEM" withId:[RARERenderableDataItem_CursorDisplayTypeEnum ITEM]];
  (void) [map putWithId:@"CURSORDISPLAYTYPE_TEXT" withId:[RARERenderableDataItem_CursorDisplayTypeEnum TEXT]];
  (void) [map putWithId:@"CURSORDISPLAYTYPE_TEXT_AND_ICON" withId:[RARERenderableDataItem_CursorDisplayTypeEnum TEXT_AND_ICON]];
  (void) [map putWithId:@"DISPLAYED_ALWAYS" withId:[RAREDisplayedEnum ALWAYS]];
  (void) [map putWithId:@"DISPLAYED_BEFORE_FIRST_FOCUS" withId:[RAREDisplayedEnum BEFORE_FIRST_FOCUS]];
  (void) [map putWithId:@"DISPLAYED_BEFORE_INTERACTION" withId:[RAREDisplayedEnum BEFORE_INTERACTION]];
  (void) [map putWithId:@"DISPLAYED_WHEN_CHILD_WIDGET_FOCUSED" withId:[RAREDisplayedEnum WHEN_CHILD_WIDGET_FOCUSED]];
  (void) [map putWithId:@"DISPLAYED_WHEN_CHILD_WIDGET_NOT_FOCUSED" withId:[RAREDisplayedEnum WHEN_CHILD_WIDGET_NOT_FOCUSED]];
  (void) [map putWithId:@"DISPLAYED_WHEN_EMPTY" withId:[RAREDisplayedEnum WHEN_EMPTY]];
  (void) [map putWithId:@"DISPLAYED_WHEN_FOCUSED" withId:[RAREDisplayedEnum WHEN_FOCUSED]];
  (void) [map putWithId:@"DISPLAYED_WHEN_NOT_FOCUSED" withId:[RAREDisplayedEnum WHEN_NOT_FOCUSED]];
  (void) [map putWithId:@"DISPLAYED_WHEN_PARENT_WIDGET_FOCUSED" withId:[RAREDisplayedEnum WHEN_PARENT_WIDGET_FOCUSED]];
  (void) [map putWithId:@"DISPLAYED_WHEN_PARENT_WIDGET_NOT_FOCUSED" withId:[RAREDisplayedEnum WHEN_PARENT_WIDGET_NOT_FOCUSED]];
  (void) [map putWithId:@"DISPLAYED_WHEN_WIDGET_FOCUSED" withId:[RAREDisplayedEnum WHEN_WIDGET_FOCUSED]];
  (void) [map putWithId:@"DISPLAYED_WHEN_WIDGET_NOT_FOCUSED" withId:[RAREDisplayedEnum WHEN_WIDGET_NOT_FOCUSED]];
  (void) [map putWithId:@"DISPLAYED_WHEN_WINDOW_FOCUSED" withId:[RAREDisplayedEnum WHEN_WINDOW_FOCUSED]];
  (void) [map putWithId:@"DISPLAYED_WHEN_WINDOW_NOT_FOCUSED" withId:[RAREDisplayedEnum WHEN_WINDOW_NOT_FOCUSED]];
  (void) [map putWithId:@"DND_ACTION_COPY" withId:[JavaLangInteger valueOfWithInt:1]];
  (void) [map putWithId:@"DND_ACTION_COPY_OR_MOVE" withId:[JavaLangInteger valueOfWithInt:3]];
  (void) [map putWithId:@"DND_ACTION_LINK" withId:[JavaLangInteger valueOfWithInt:1073741824]];
  (void) [map putWithId:@"DND_ACTION_MOVE" withId:[JavaLangInteger valueOfWithInt:2]];
  (void) [map putWithId:@"DND_ACTION_NONE" withId:[JavaLangInteger valueOfWithInt:0]];
  (void) [map putWithId:@"DND_ACTION_REFERENCE" withId:[JavaLangInteger valueOfWithInt:1073741824]];
  (void) [map putWithId:@"EXPANSIONEVENT_HAS_COLLAPSED" withId:[RAREExpansionEvent_TypeEnum HAS_COLLAPSED]];
  (void) [map putWithId:@"EXPANSIONEVENT_HAS_EXPANDED" withId:[RAREExpansionEvent_TypeEnum HAS_EXPANDED]];
  (void) [map putWithId:@"EXPANSIONEVENT_WILL_COLLAPSE" withId:[RAREExpansionEvent_TypeEnum WILL_COLLAPSE]];
  (void) [map putWithId:@"EXPANSIONEVENT_WILL_EXPAND" withId:[RAREExpansionEvent_TypeEnum WILL_EXPAND]];
  (void) [map putWithId:@"FONT_BOLD" withId:[JavaLangInteger valueOfWithInt:1]];
  (void) [map putWithId:@"FONT_ITALIC" withId:[JavaLangInteger valueOfWithInt:2]];
  (void) [map putWithId:@"FONT_PLAIN" withId:[JavaLangInteger valueOfWithInt:0]];
  (void) [map putWithId:@"GRADIENTDIRECTION_CENTER" withId:[RAREiGradientPainter_DirectionEnum CENTER]];
  (void) [map putWithId:@"GRADIENTDIRECTION_DIAGONAL_BOTTOM_LEFT" withId:[RAREiGradientPainter_DirectionEnum DIAGONAL_BOTTOM_LEFT]];
  (void) [map putWithId:@"GRADIENTDIRECTION_DIAGONAL_BOTTOM_RIGHT" withId:[RAREiGradientPainter_DirectionEnum DIAGONAL_BOTTOM_RIGHT]];
  (void) [map putWithId:@"GRADIENTDIRECTION_DIAGONAL_TOP_LEFT" withId:[RAREiGradientPainter_DirectionEnum DIAGONAL_TOP_LEFT]];
  (void) [map putWithId:@"GRADIENTDIRECTION_DIAGONAL_TOP_RIGHT" withId:[RAREiGradientPainter_DirectionEnum DIAGONAL_TOP_RIGHT]];
  (void) [map putWithId:@"GRADIENTDIRECTION_HORIZONTAL_LEFT" withId:[RAREiGradientPainter_DirectionEnum HORIZONTAL_LEFT]];
  (void) [map putWithId:@"GRADIENTDIRECTION_HORIZONTAL_RIGHT" withId:[RAREiGradientPainter_DirectionEnum HORIZONTAL_RIGHT]];
  (void) [map putWithId:@"GRADIENTDIRECTION_VERTICAL_BOTTOM" withId:[RAREiGradientPainter_DirectionEnum VERTICAL_BOTTOM]];
  (void) [map putWithId:@"GRADIENTDIRECTION_VERTICAL_TOP" withId:[RAREiGradientPainter_DirectionEnum VERTICAL_TOP]];
  (void) [map putWithId:@"GRADIENTTYPE_LINEAR" withId:[RAREiGradientPainter_TypeEnum LINEAR]];
  (void) [map putWithId:@"GRADIENTTYPE_LINEAR_REFLECT" withId:[RAREiGradientPainter_TypeEnum LINEAR_REFLECT]];
  (void) [map putWithId:@"GRADIENTTYPE_LINEAR_REPEAT" withId:[RAREiGradientPainter_TypeEnum LINEAR_REPEAT]];
  (void) [map putWithId:@"GRADIENTTYPE_RADIAL" withId:[RAREiGradientPainter_TypeEnum RADIAL]];
  (void) [map putWithId:@"ICONPOSITION_AUTO" withId:[RARERenderableDataItem_IconPositionEnum AUTO]];
  (void) [map putWithId:@"ICONPOSITION_BOTTOM_CENTER" withId:[RARERenderableDataItem_IconPositionEnum BOTTOM_CENTER]];
  (void) [map putWithId:@"ICONPOSITION_BOTTOM_LEFT" withId:[RARERenderableDataItem_IconPositionEnum BOTTOM_LEFT]];
  (void) [map putWithId:@"ICONPOSITION_BOTTOM_RIGHT" withId:[RARERenderableDataItem_IconPositionEnum BOTTOM_RIGHT]];
  (void) [map putWithId:@"ICONPOSITION_LEADING" withId:[RARERenderableDataItem_IconPositionEnum LEADING]];
  (void) [map putWithId:@"ICONPOSITION_LEFT" withId:[RARERenderableDataItem_IconPositionEnum LEFT]];
  (void) [map putWithId:@"ICONPOSITION_RIGHT" withId:[RARERenderableDataItem_IconPositionEnum RIGHT]];
  (void) [map putWithId:@"ICONPOSITION_RIGHT_JUSTIFIED" withId:[RARERenderableDataItem_IconPositionEnum RIGHT_JUSTIFIED]];
  (void) [map putWithId:@"ICONPOSITION_TOP_CENTER" withId:[RARERenderableDataItem_IconPositionEnum TOP_CENTER]];
  (void) [map putWithId:@"ICONPOSITION_TOP_LEFT" withId:[RARERenderableDataItem_IconPositionEnum TOP_LEFT]];
  (void) [map putWithId:@"ICONPOSITION_TOP_RIGHT" withId:[RARERenderableDataItem_IconPositionEnum TOP_RIGHT]];
  (void) [map putWithId:@"ICONPOSITION_TRAILING" withId:[RARERenderableDataItem_IconPositionEnum TRAILING]];
  (void) [map putWithId:@"KEYEVENT_KEY_DOWN" withId:[RAREKeyEvent_TypeEnum KEY_DOWN]];
  (void) [map putWithId:@"KEYEVENT_KEY_TYPED" withId:[RAREKeyEvent_TypeEnum KEY_TYPED]];
  (void) [map putWithId:@"KEYEVENT_KEY_UNKNOWN" withId:[RAREKeyEvent_TypeEnum KEY_UNKNOWN]];
  (void) [map putWithId:@"KEYEVENT_KEY_UP" withId:[RAREKeyEvent_TypeEnum KEY_UP]];
  (void) [map putWithId:@"LOCATION_AUTO" withId:[RARELocationEnum AUTO]];
  (void) [map putWithId:@"LOCATION_BOTTOM" withId:[RARELocationEnum BOTTOM]];
  (void) [map putWithId:@"LOCATION_CENTER" withId:[RARELocationEnum CENTER]];
  (void) [map putWithId:@"LOCATION_LEFT" withId:[RARELocationEnum LEFT]];
  (void) [map putWithId:@"LOCATION_RIGHT" withId:[RARELocationEnum RIGHT]];
  (void) [map putWithId:@"LOCATION_TOP" withId:[RARELocationEnum TOP]];
  (void) [map putWithId:@"MOUSEEVENT_FLING" withId:[RAREMouseEvent_TypeEnum FLING]];
  (void) [map putWithId:@"MOUSEEVENT_LONG_PRESS" withId:[RAREMouseEvent_TypeEnum LONG_PRESS]];
  (void) [map putWithId:@"MOUSEEVENT_MOUSE_CLICK" withId:[RAREMouseEvent_TypeEnum MOUSE_CLICK]];
  (void) [map putWithId:@"MOUSEEVENT_MOUSE_DBLCLICK" withId:[RAREMouseEvent_TypeEnum MOUSE_DBLCLICK]];
  (void) [map putWithId:@"MOUSEEVENT_MOUSE_DOWN" withId:[RAREMouseEvent_TypeEnum MOUSE_DOWN]];
  (void) [map putWithId:@"MOUSEEVENT_MOUSE_DRAG" withId:[RAREMouseEvent_TypeEnum MOUSE_DRAG]];
  (void) [map putWithId:@"MOUSEEVENT_MOUSE_ENTER" withId:[RAREMouseEvent_TypeEnum MOUSE_ENTER]];
  (void) [map putWithId:@"MOUSEEVENT_MOUSE_EXIT" withId:[RAREMouseEvent_TypeEnum MOUSE_EXIT]];
  (void) [map putWithId:@"MOUSEEVENT_MOUSE_MOVE" withId:[RAREMouseEvent_TypeEnum MOUSE_MOVE]];
  (void) [map putWithId:@"MOUSEEVENT_MOUSE_SCALE" withId:[RAREMouseEvent_TypeEnum MOUSE_SCALE]];
  (void) [map putWithId:@"MOUSEEVENT_MOUSE_UNKNOWN" withId:[RAREMouseEvent_TypeEnum MOUSE_UNKNOWN]];
  (void) [map putWithId:@"MOUSEEVENT_MOUSE_UP" withId:[RAREMouseEvent_TypeEnum MOUSE_UP]];
  (void) [map putWithId:@"MOUSEEVENT_SCROLL" withId:[RAREMouseEvent_TypeEnum SCROLL]];
  (void) [map putWithId:@"RENDERSPACE_COMPONENT" withId:[RARERenderSpaceEnum COMPONENT]];
  (void) [map putWithId:@"RENDERSPACE_WITHIN_BORDER" withId:[RARERenderSpaceEnum WITHIN_BORDER]];
  (void) [map putWithId:@"RENDERSPACE_WITHIN_MARGIN" withId:[RARERenderSpaceEnum WITHIN_MARGIN]];
  (void) [map putWithId:@"RENDERTYPE_CENTERED" withId:[RARERenderTypeEnum CENTERED]];
  (void) [map putWithId:@"RENDERTYPE_HORIZONTAL_TILE" withId:[RARERenderTypeEnum HORIZONTAL_TILE]];
  (void) [map putWithId:@"RENDERTYPE_LEFT_MIDDLE" withId:[RARERenderTypeEnum LEFT_MIDDLE]];
  (void) [map putWithId:@"RENDERTYPE_LOWER_LEFT" withId:[RARERenderTypeEnum LOWER_LEFT]];
  (void) [map putWithId:@"RENDERTYPE_LOWER_MIDDLE" withId:[RARERenderTypeEnum LOWER_MIDDLE]];
  (void) [map putWithId:@"RENDERTYPE_LOWER_RIGHT" withId:[RARERenderTypeEnum LOWER_RIGHT]];
  (void) [map putWithId:@"RENDERTYPE_RIGHT_MIDDLE" withId:[RARERenderTypeEnum RIGHT_MIDDLE]];
  (void) [map putWithId:@"RENDERTYPE_STRETCHED" withId:[RARERenderTypeEnum STRETCHED]];
  (void) [map putWithId:@"RENDERTYPE_STRETCH_HEIGHT" withId:[RARERenderTypeEnum STRETCH_HEIGHT]];
  (void) [map putWithId:@"RENDERTYPE_STRETCH_HEIGHT_MIDDLE" withId:[RARERenderTypeEnum STRETCH_HEIGHT_MIDDLE]];
  (void) [map putWithId:@"RENDERTYPE_STRETCH_WIDTH" withId:[RARERenderTypeEnum STRETCH_WIDTH]];
  (void) [map putWithId:@"RENDERTYPE_STRETCH_WIDTH_MIDDLE" withId:[RARERenderTypeEnum STRETCH_WIDTH_MIDDLE]];
  (void) [map putWithId:@"RENDERTYPE_TILED" withId:[RARERenderTypeEnum TILED]];
  (void) [map putWithId:@"RENDERTYPE_UPPER_LEFT" withId:[RARERenderTypeEnum UPPER_LEFT]];
  (void) [map putWithId:@"RENDERTYPE_UPPER_MIDDLE" withId:[RARERenderTypeEnum UPPER_MIDDLE]];
  (void) [map putWithId:@"RENDERTYPE_UPPER_RIGHT" withId:[RARERenderTypeEnum UPPER_RIGHT]];
  (void) [map putWithId:@"RENDERTYPE_VERTICAL_TILE" withId:[RARERenderTypeEnum VERTICAL_TILE]];
  (void) [map putWithId:@"RENDERTYPE_XY" withId:[RARERenderTypeEnum XY]];
  (void) [map putWithId:@"SCALEEVENT_SCALE" withId:[RAREScaleEvent_TypeEnum SCALE]];
  (void) [map putWithId:@"SCALEEVENT_SCALE_BEGIN" withId:[RAREScaleEvent_TypeEnum SCALE_BEGIN]];
  (void) [map putWithId:@"SCALEEVENT_SCALE_END" withId:[RAREScaleEvent_TypeEnum SCALE_END]];
  (void) [map putWithId:@"SCALING_BICUBIC" withId:[RAREiImagePainter_ScalingTypeEnum BICUBIC]];
  (void) [map putWithId:@"SCALING_BICUBIC_CACHED" withId:[RAREiImagePainter_ScalingTypeEnum BICUBIC_CACHED]];
  (void) [map putWithId:@"SCALING_BILINEAR" withId:[RAREiImagePainter_ScalingTypeEnum BILINEAR]];
  (void) [map putWithId:@"SCALING_BILINEAR_CACHED" withId:[RAREiImagePainter_ScalingTypeEnum BILINEAR_CACHED]];
  (void) [map putWithId:@"SCALING_NEAREST_NEIGHBOR" withId:[RAREiImagePainter_ScalingTypeEnum NEAREST_NEIGHBOR]];
  (void) [map putWithId:@"SCALING_NEAREST_NEIGHBOR_CACHED" withId:[RAREiImagePainter_ScalingTypeEnum NEAREST_NEIGHBOR_CACHED]];
  (void) [map putWithId:@"SCALING_PROGRESSIVE_BICUBIC" withId:[RAREiImagePainter_ScalingTypeEnum PROGRESSIVE_BICUBIC]];
  (void) [map putWithId:@"SCALING_PROGRESSIVE_BICUBIC_CACHED" withId:[RAREiImagePainter_ScalingTypeEnum PROGRESSIVE_BICUBIC_CACHED]];
  (void) [map putWithId:@"SCALING_PROGRESSIVE_BILINEAR" withId:[RAREiImagePainter_ScalingTypeEnum PROGRESSIVE_BILINEAR]];
  (void) [map putWithId:@"SCALING_PROGRESSIVE_BILINEAR_CACHED" withId:[RAREiImagePainter_ScalingTypeEnum PROGRESSIVE_BILINEAR_CACHED]];
  (void) [map putWithId:@"SCALING_PROGRESSIVE_NEAREST_NEIGHBOR" withId:[RAREiImagePainter_ScalingTypeEnum PROGRESSIVE_NEAREST_NEIGHBOR]];
  (void) [map putWithId:@"SCALING_PROGRESSIVE_NEAREST_NEIGHBOR_CACHED" withId:[RAREiImagePainter_ScalingTypeEnum PROGRESSIVE_NEAREST_NEIGHBOR_CACHED]];
  (void) [map putWithId:@"TARGET_BLANK" withId:@"_blank"];
  (void) [map putWithId:@"TARGET_MENUBAR" withId:@"_menubar"];
  (void) [map putWithId:@"TARGET_NEW_POPUP" withId:@"_new_popup"];
  (void) [map putWithId:@"TARGET_NEW_WINDOW" withId:@"_new_window"];
  (void) [map putWithId:@"TARGET_NULL" withId:@"_null"];
  (void) [map putWithId:@"TARGET_PARENT" withId:@"_parent"];
  (void) [map putWithId:@"TARGET_SELF" withId:@"_self"];
  (void) [map putWithId:@"TARGET_TOOLBAR" withId:@"_toolbar"];
  (void) [map putWithId:@"TARGET_WORKSPACE" withId:@"_workspace"];
  (void) [map putWithId:@"TEXTCHANGEEVENT_CHANGED" withId:[RARETextChangeEvent_TypeEnum CHANGED]];
  (void) [map putWithId:@"TEXTCHANGEEVENT_CHANGING" withId:[RARETextChangeEvent_TypeEnum CHANGING]];
  (void) [map putWithId:@"TEXTHALIGN_AUTO" withId:[RARERenderableDataItem_HorizontalAlignEnum AUTO]];
  (void) [map putWithId:@"TEXTHALIGN_CENTER" withId:[RARERenderableDataItem_HorizontalAlignEnum CENTER]];
  (void) [map putWithId:@"TEXTHALIGN_FILL" withId:[RARERenderableDataItem_HorizontalAlignEnum FILL]];
  (void) [map putWithId:@"TEXTHALIGN_LEADING" withId:[RARERenderableDataItem_HorizontalAlignEnum LEADING]];
  (void) [map putWithId:@"TEXTHALIGN_LEFT" withId:[RARERenderableDataItem_HorizontalAlignEnum LEFT]];
  (void) [map putWithId:@"TEXTHALIGN_RIGHT" withId:[RARERenderableDataItem_HorizontalAlignEnum RIGHT]];
  (void) [map putWithId:@"TEXTHALIGN_TRAILING" withId:[RARERenderableDataItem_HorizontalAlignEnum TRAILING]];
  (void) [map putWithId:@"TEXTVALIGN_AUTO" withId:[RARERenderableDataItem_VerticalAlignEnum AUTO]];
  (void) [map putWithId:@"TEXTVALIGN_BOTTOM" withId:[RARERenderableDataItem_VerticalAlignEnum BOTTOM]];
  (void) [map putWithId:@"TEXTVALIGN_CENTER" withId:[RARERenderableDataItem_VerticalAlignEnum CENTER]];
  (void) [map putWithId:@"TEXTVALIGN_FILL" withId:[RARERenderableDataItem_VerticalAlignEnum FILL]];
  (void) [map putWithId:@"TEXTVALIGN_TOP" withId:[RARERenderableDataItem_VerticalAlignEnum TOP]];
  (void) [map putWithId:@"WHEN_ANCESTOR_OF_FOCUSED_COMPONENT" withId:[JavaLangInteger valueOfWithInt:1]];
  (void) [map putWithId:@"WHEN_FOCUSED" withId:[JavaLangInteger valueOfWithInt:0]];
  (void) [map putWithId:@"WHEN_IN_FOCUSED_WINDOW" withId:[JavaLangInteger valueOfWithInt:2]];
  (void) [map putWithId:@"WIDGETTYPE_BEAN" withId:[RAREiWidget_WidgetTypeEnum Bean]];
  (void) [map putWithId:@"WIDGETTYPE_CHART" withId:[RAREiWidget_WidgetTypeEnum Chart]];
  (void) [map putWithId:@"WIDGETTYPE_CHECKBOX" withId:[RAREiWidget_WidgetTypeEnum CheckBox]];
  (void) [map putWithId:@"WIDGETTYPE_CHECKBOXLIST" withId:[RAREiWidget_WidgetTypeEnum CheckBoxList]];
  (void) [map putWithId:@"WIDGETTYPE_CHECKBOXTREE" withId:[RAREiWidget_WidgetTypeEnum CheckBoxTree]];
  (void) [map putWithId:@"WIDGETTYPE_COLLAPSIBLEPANE" withId:[RAREiWidget_WidgetTypeEnum CollapsiblePane]];
  (void) [map putWithId:@"WIDGETTYPE_COLORCHOOSER" withId:[RAREiWidget_WidgetTypeEnum ColorChooser]];
  (void) [map putWithId:@"WIDGETTYPE_COMBOBOX" withId:[RAREiWidget_WidgetTypeEnum ComboBox]];
  (void) [map putWithId:@"WIDGETTYPE_CUSTOM" withId:[RAREiWidget_WidgetTypeEnum Custom]];
  (void) [map putWithId:@"WIDGETTYPE_DATECHOOSER" withId:[RAREiWidget_WidgetTypeEnum DateChooser]];
  (void) [map putWithId:@"WIDGETTYPE_DOCUMENTPANE" withId:[RAREiWidget_WidgetTypeEnum DocumentPane]];
  (void) [map putWithId:@"WIDGETTYPE_FORM" withId:[RAREiWidget_WidgetTypeEnum Form]];
  (void) [map putWithId:@"WIDGETTYPE_GRIDPANE" withId:[RAREiWidget_WidgetTypeEnum GridPane]];
  (void) [map putWithId:@"WIDGETTYPE_GROUPBOX" withId:[RAREiWidget_WidgetTypeEnum GroupBox]];
  (void) [map putWithId:@"WIDGETTYPE_IMAGEPANE" withId:[RAREiWidget_WidgetTypeEnum ImagePane]];
  (void) [map putWithId:@"WIDGETTYPE_LABEL" withId:[RAREiWidget_WidgetTypeEnum Label]];
  (void) [map putWithId:@"WIDGETTYPE_LINE" withId:[RAREiWidget_WidgetTypeEnum Line]];
  (void) [map putWithId:@"WIDGETTYPE_LISTBOX" withId:[RAREiWidget_WidgetTypeEnum ListBox]];
  (void) [map putWithId:@"WIDGETTYPE_MEDIAPLAYER" withId:[RAREiWidget_WidgetTypeEnum MediaPlayer]];
  (void) [map putWithId:@"WIDGETTYPE_MENUBAR" withId:[RAREiWidget_WidgetTypeEnum MenuBar]];
  (void) [map putWithId:@"WIDGETTYPE_NAVIGATOR" withId:[RAREiWidget_WidgetTypeEnum Navigator]];
  (void) [map putWithId:@"WIDGETTYPE_PASSWORDFIELD" withId:[RAREiWidget_WidgetTypeEnum PasswordField]];
  (void) [map putWithId:@"WIDGETTYPE_PROGRESSBAR" withId:[RAREiWidget_WidgetTypeEnum ProgressBar]];
  (void) [map putWithId:@"WIDGETTYPE_PUSHBUTTON" withId:[RAREiWidget_WidgetTypeEnum PushButton]];
  (void) [map putWithId:@"WIDGETTYPE_RADIOBUTTON" withId:[RAREiWidget_WidgetTypeEnum RadioButton]];
  (void) [map putWithId:@"WIDGETTYPE_SCROLLPANE" withId:[RAREiWidget_WidgetTypeEnum ScrollPane]];
  (void) [map putWithId:@"WIDGETTYPE_SLIDER" withId:[RAREiWidget_WidgetTypeEnum Slider]];
  (void) [map putWithId:@"WIDGETTYPE_SPINNER" withId:[RAREiWidget_WidgetTypeEnum Spinner]];
  (void) [map putWithId:@"WIDGETTYPE_SPLITPANE" withId:[RAREiWidget_WidgetTypeEnum SplitPane]];
  (void) [map putWithId:@"WIDGETTYPE_STACKPANE" withId:[RAREiWidget_WidgetTypeEnum StackPane]];
  (void) [map putWithId:@"WIDGETTYPE_STATUSBAR" withId:[RAREiWidget_WidgetTypeEnum StatusBar]];
  (void) [map putWithId:@"WIDGETTYPE_TABLE" withId:[RAREiWidget_WidgetTypeEnum Table]];
  (void) [map putWithId:@"WIDGETTYPE_TABPANE" withId:[RAREiWidget_WidgetTypeEnum TabPane]];
  (void) [map putWithId:@"WIDGETTYPE_TEXTAREA" withId:[RAREiWidget_WidgetTypeEnum TextArea]];
  (void) [map putWithId:@"WIDGETTYPE_TEXTFIELD" withId:[RAREiWidget_WidgetTypeEnum TextField]];
  (void) [map putWithId:@"WIDGETTYPE_TOOLBAR" withId:[RAREiWidget_WidgetTypeEnum ToolBar]];
  (void) [map putWithId:@"WIDGETTYPE_TREE" withId:[RAREiWidget_WidgetTypeEnum Tree]];
  (void) [map putWithId:@"WIDGETTYPE_WEBBROWSER" withId:[RAREiWidget_WidgetTypeEnum WebBrowser]];
  (void) [map putWithId:@"WIDGETTYPE_WIDGETPANE" withId:[RAREiWidget_WidgetTypeEnum WidgetPane]];
  (void) [map putWithId:@"WIDGETTYPE_WINDOW" withId:[RAREiWidget_WidgetTypeEnum Window]];
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "init", NULL, NULL, 0x2, NULL },
    { "getConstants", NULL, "LJavaUtilMap", 0x9, NULL },
  };
  static J2ObjcClassInfo _RAREEnvConstants = { "EnvConstants", "com.appnativa.rare.scripting", NULL, 0x11, 2, methods, 0, NULL, 0, NULL};
  return &_RAREEnvConstants;
}

@end
