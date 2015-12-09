//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/iConstants.java
//
//  Created by decoteaud on 12/8/15.
//

#ifndef _RAREiConstants_H_
#define _RAREiConstants_H_

@class IOSObjectArray;

#import "JreEmulation.h"

#define RAREiConstants_APPLICATION_VERSION 3.5
#define RAREiConstants_ITEM_INDETERMINATE 128
#define RAREiConstants_ITEM_SELECTED 64

@protocol RAREiConstants < NSObject, JavaObject >
@end

@interface RAREiConstants : NSObject {
}
+ (NSString *)ABOUT_ACTION_NAME;
+ (NSString *)AIF_MIME_TYPE;
+ (NSString *)APPLICATION_CODEBASE;
+ (NSString *)APPLICATION_DOCBASE;
+ (NSString *)APPLICATION_DOC_SERVERBASE;
+ (NSString *)APPLICATION_NAME_STRING;
+ (NSString *)APPLICATION_NAME_VERSION_STRING;
+ (NSString *)APPLICATION_SERVERBASE;
+ (NSString *)APPLICATION_URL;
+ (double)APPLICATION_VERSION;
+ (NSString *)APPLICATION_VERSION_STRING;
+ (NSString *)APP_ACTION_PREFIX;
+ (NSString *)ATTRIBUTE_FOCUS_CHANGE;
+ (NSString *)ATTRIBUTE_FUNCTION_EVAL;
+ (NSString *)ATTRIBUTE_ON_ACTION;
+ (NSString *)ATTRIBUTE_ON_BLUR;
+ (NSString *)ATTRIBUTE_ON_CHANGE;
+ (NSString *)ATTRIBUTE_ON_CLICK;
+ (NSString *)ATTRIBUTE_ON_CLOSED;
+ (NSString *)ATTRIBUTE_ON_CONFIGURE;
+ (NSString *)ATTRIBUTE_ON_CONTEXT_MENU;
+ (NSString *)ATTRIBUTE_ON_CREATED;
+ (NSString *)ATTRIBUTE_ON_DOUBLECLICK;
+ (NSString *)ATTRIBUTE_ON_DISPOSE;
+ (NSString *)ATTRIBUTE_ON_DRAG;
+ (NSString *)ATTRIBUTE_ON_DRAGEND;
+ (NSString *)ATTRIBUTE_ON_DRAGENTER;
+ (NSString *)ATTRIBUTE_ON_DRAGEXIT;
+ (NSString *)ATTRIBUTE_ON_DRAGOVER;
+ (NSString *)ATTRIBUTE_ON_DRAGSTART;
+ (NSString *)ATTRIBUTE_ON_DROP;
+ (NSString *)ATTRIBUTE_ON_ERROR;
+ (NSString *)ATTRIBUTE_ON_DROPEND;
+ (NSString *)ATTRIBUTE_ON_STARTED_LOADING;
+ (NSString *)ATTRIBUTE_ON_FINISHED_LOADING;
+ (NSString *)ATTRIBUTE_ON_FOCUS;
+ (NSString *)ATTRIBUTE_ON_FLING;
+ (NSString *)ATTRIBUTE_ON_HAS_COLLAPSED;
+ (NSString *)ATTRIBUTE_ON_HAS_EXPANDED;
+ (NSString *)ATTRIBUTE_ON_HIDDEN;
+ (NSString *)ATTRIBUTE_ON_ITEM_ADDED;
+ (NSString *)ATTRIBUTE_ON_ITEM_DELETED;
+ (NSString *)ATTRIBUTE_ON_ITEM_CHANGED;
+ (NSString *)ATTRIBUTE_ON_KEY_DOWN;
+ (NSString *)ATTRIBUTE_ON_KEY_PRESS;
+ (NSString *)ATTRIBUTE_ON_KEY_UP;
+ (NSString *)ATTRIBUTE_ON_LOAD;
+ (NSString *)ATTRIBUTE_ON_MOUSE_DOWN;
+ (NSString *)ATTRIBUTE_ON_MOUSE_DRAGGED;
+ (NSString *)ATTRIBUTE_ON_MOUSE_IN;
+ (NSString *)ATTRIBUTE_ON_MOUSE_MOVED;
+ (NSString *)ATTRIBUTE_ON_MOUSE_OUT;
+ (NSString *)ATTRIBUTE_ON_MOUSE_UP;
+ (NSString *)ATTRIBUTE_ON_SCROLL;
+ (NSString *)ATTRIBUTE_ON_MOVED;
+ (NSString *)ATTRIBUTE_ON_OPENED;
+ (NSString *)ATTRIBUTE_ON_POST;
+ (NSString *)ATTRIBUTE_ON_RESET;
+ (NSString *)ATTRIBUTE_ON_RESIZE;
+ (NSString *)ATTRIBUTE_ON_SELECT;
+ (NSString *)ATTRIBUTE_ON_SCALE;
+ (NSString *)ATTRIBUTE_ON_SHOWN;
+ (NSString *)ATTRIBUTE_ON_SUBMIT;
+ (NSString *)ATTRIBUTE_ON_UNLOAD;
+ (NSString *)ATTRIBUTE_ON_WILL_CLOSE;
+ (NSString *)ATTRIBUTE_ON_WILL_COLLAPSE;
+ (NSString *)ATTRIBUTE_ON_WILL_EXPAND;
+ (NSString *)ATTRIBUTE_PERMANENT_FOCUS_CHANGE;
+ (NSString *)ATTRIBUTE_TIMER;
+ (NSString *)AUDIO_BASIC_MIME_TYPE;
+ (NSString *)AVI_MIME_TYPE;
+ (NSString *)COLLECTION_PREFIX;
+ (NSString *)COLLECTION_PROTOCOL_HOSTSTRING;
+ (NSString *)COLLECTION_PROTOCOL_STRING;
+ (NSString *)COPY_ACTION_NAME;
+ (NSString *)CSV_MIME_TYPE;
+ (NSString *)CUT_ACTION_NAME;
+ (NSString *)DATAFLAVOR_FILE;
+ (NSString *)DATAFLAVOR_FILE_LIST;
+ (NSString *)DATAFLAVOR_HTML;
+ (NSString *)DATAFLAVOR_RTF;
+ (NSString *)DATAFLAVOR_IMAGE;
+ (NSString *)DATAFLAVOR_DATA_ITEM;
+ (NSString *)DATAFLAVOR_WIDGET;
+ (NSString *)DATAFLAVOR_TEXT;
+ (NSString *)DATAFLAVOR_URL;
+ (NSString *)DATAFLAVOR_URLLIST;
+ (NSString *)DELETE_ACTION_NAME;
+ (NSString *)DOCLICK_ACTION_NAME;
+ (NSString *)EDIT_FIND_MENU_NAME;
+ (NSString *)EDIT_MENU_NAME;
+ (NSString *)EDIT_REPLACE_MENU_NAME;
+ (NSString *)EVENT_ACTION;
+ (NSString *)EVENT_BLUR;
+ (NSString *)EVENT_CHANGE;
+ (NSString *)EVENT_CLICK;
+ (NSString *)EVENT_CLOSED;
+ (NSString *)EVENT_CONFIGURE;
+ (NSString *)EVENT_CREATED;
+ (NSString *)EVENT_DOUBLECLICK;
+ (NSString *)EVENT_CONTEXTMENU;
+ (NSString *)EVENT_DISPOSE;
+ (NSString *)EVENT_DRAG;
+ (NSString *)EVENT_DRAGEND;
+ (NSString *)EVENT_DRAGENTER;
+ (NSString *)EVENT_DRAGEXIT;
+ (NSString *)EVENT_DRAGOVER;
+ (NSString *)EVENT_DRAGSTART;
+ (NSString *)EVENT_DROP;
+ (NSString *)EVENT_ERROR;
+ (NSString *)EVENT_DROPEND;
+ (NSString *)EVENT_STARTED_LOADING;
+ (NSString *)EVENT_FINISHED_LOADING;
+ (NSString *)EVENT_FOCUS;
+ (NSString *)EVENT_FOCUS_CHANGE;
+ (NSString *)EVENT_FLING;
+ (NSString *)EVENT_FUNCTION_EVAL;
+ (NSString *)EVENT_HAS_COLLAPSED;
+ (NSString *)EVENT_HAS_EXPANDED;
+ (NSString *)EVENT_HIDDEN;
+ (NSString *)EVENT_INVOKE_LATER;
+ (NSString *)EVENT_ITEM_ADDED;
+ (NSString *)EVENT_ITEM_DELETED;
+ (NSString *)EVENT_ITEM_CHANGED;
+ (NSString *)EVENT_KEY_DOWN;
+ (NSString *)EVENT_KEY_PRESS;
+ (NSString *)EVENT_KEY_UP;
+ (NSString *)EVENT_LOAD;
+ (NSString *)EVENT_MOUSE_DOWN;
+ (NSString *)EVENT_MOUSE_DRAGGED;
+ (NSString *)EVENT_MOUSE_IN;
+ (NSString *)EVENT_MOUSE_MOVED;
+ (NSString *)EVENT_MOUSE_OUT;
+ (NSString *)EVENT_MOUSE_UP;
+ (NSString *)EVENT_SCROLL;
+ (NSString *)EVENT_MOVED;
+ (NSString *)EVENT_OPENED;
+ (NSString *)EVENT_PERMANENT_FOCUS_CHANGE;
+ (NSString *)EVENT_POST;
+ (NSString *)EVENT_PREFIX;
+ (NSString *)EVENT_RESET;
+ (NSString *)EVENT_RESIZE;
+ (NSString *)EVENT_ROTATE;
+ (NSString *)EVENT_SELECT;
+ (NSString *)EVENT_SCALE;
+ (NSString *)EVENT_SHOWN;
+ (NSString *)EVENT_SUBMIT;
+ (NSString *)EVENT_TIMER;
+ (NSString *)EVENT_UNLOAD;
+ (NSString *)EVENT_WILL_OPEN;
+ (NSString *)EVENT_WILL_CLOSE;
+ (NSString *)EVENT_WILL_COLLAPSE;
+ (NSString *)EVENT_WILL_EXPAND;
+ (NSString *)EVENT_BACK_PRESSED;
+ (NSString *)EXIT_ACTION_NAME;
+ (NSString *)FILE_MENU_NAME;
+ (NSString *)FIND_ACTION_NAME;
+ (NSString *)FIND_NEXT_ACTION_NAME;
+ (NSString *)FIND_PREVIOUS_ACTION_NAME;
+ (NSString *)FIND_SELECTION_ACTION_NAME;
+ (NSString *)GIF_MIME_TYPE;
+ (NSString *)HELP_MENU_NAME;
+ (NSString *)HSCROLLBAR_BEAN_NAME;
+ (NSString *)HTML_MIME_TYPE;
+ (NSString *)INLINE_PROTOCOL_HOSTSTRING;
+ (NSString *)INLINE_PROTOCOL_STRING;
+ (NSString *)INLINE_PREFIX;
+ (NSString *)JPEG_MIME_TYPE;
+ (NSString *)JSON_MIME_TYPE;
+ (NSString *)LIB_PREFIX;
+ (NSString *)MENU_BEAN_NAME;
+ (NSString *)MENUBAR_NAME;
+ (NSString *)MENU_EXPANSION_NAME;
+ (NSString *)MENU_SEPARATOR_NAME;
+ (NSString *)MOV_MIME_TYPE;
+ (NSString *)MP3_MIME_TYPE;
+ (NSString *)MPEG_MIME_TYPE;
+ (NSString *)NEXT_COMPONENT_ACTION_NAME;
+ (NSString *)OCTET_MIME_TYPE;
+ (NSString *)OPEN_ACTION_NAME;
+ (NSString *)OPTIONS_ACTION_NAME;
+ (NSString *)PASTE_ACTION_NAME;
+ (NSString *)PDF_MIME_TYPE;
+ (NSString *)PNG_MIME_TYPE;
+ (NSString *)PREV_COMPONENT_ACTION_NAME;
+ (NSString *)PRINT_ACTION_NAME;
+ (NSString *)REDO_ACTION_NAME;
+ (NSString *)RELOAD_ACTION_NAME;
+ (NSString *)REPLACE_ACTION_NAME;
+ (NSString *)RESET_ACTION_NAME;
+ (NSString *)RESOURCE_PREFIX;
+ (NSString *)UIPROPERTY_PREFIX;
+ (NSString *)RICHTEXT_MIME_TYPE;
+ (NSString *)RJAR_PROTOCOL_STRING;
+ (NSString *)RTF_MIME_TYPE;
+ (NSString *)RARE_ACTIONS_PROPERTY;
+ (NSString *)RARE_CREATEPRESSED_ICON;
+ (NSString *)RARE_DISABLEDCOLOR_PROPERTY;
+ (NSString *)RARE_FILLCOLOR_PROPERTY;
+ (NSString *)RARE_FOCUSPAINTED_PROPERTY;
+ (NSString *)RARE_MIN_HEIGHT_PROPERTY;
+ (NSString *)RARE_HEIGHT_PROPERTY;
+ (NSString *)RARE_ICON_SCALING_PERCENT_VALUE;
+ (NSString *)RARE_ICON_SCALING_PROPERTY;
+ (NSString *)RARE_KEYSTROKES_PROPERTY;
+ (NSString *)RARE_ORIENTATION_PROPERTY;
+ (NSString *)RARE_REPAINTFOCUS_PROPERTY;
+ (NSString *)RARE_REPAINTPERMFOCUS_PROPERTY;
+ (NSString *)RARE_REPAINTWINDOW_FOCUS_PROPERTY;
+ (NSString *)RARE_ROWPAINTED_PROPERTY;
+ (NSString *)RARE_SCRIPT_RUN_CONTEXT;
+ (NSString *)RARE_SELECTION_PROPERTY;
+ (NSString *)RARE_TARGET_COMPONENT_PROPERTY;
+ (NSString *)RARE_TEXT_INDENT_PROPERTY;
+ (NSString *)RARE_WIDGET_COMPONENT_PROPERTY;
+ (NSString *)RARE_CONSTRAINTS_PROPERTY;
+ (NSString *)RARE_COMPONENT_PAINTER_PROPERTY;
+ (NSString *)RARE_HAD_INTERACTION_PROPERTY;
+ (NSString *)RARE_HAS_BEEN_FOCUSED_PROPERTY;
+ (NSString *)RARE_WIDTH_PROPERTY;
+ (NSString *)RARE_MIN_WIDTH_PROPERTY;
+ (NSString *)RARE_MAX_WIDTH_PROPERTY;
+ (NSString *)RARE_HALIGN_PROPERTY;
+ (NSString *)RARE_VALIGN_PROPERTY;
+ (NSString *)RARE_WIDTH_FIXED_VALUE;
+ (NSString *)RARE_HEIGHT_FIXED_VALUE;
+ (NSString *)RARE_HEIGHT_MIN_VALUE;
+ (NSString *)RARE_WINDOW_OPAQUE;
+ (NSString *)RARE_WINDOW_TRANSLUCENCY;
+ (NSString *)RARE_WINDOW_USEBORDERSHAPE;
+ (NSString *)RARE_WINDOW_BORDERSIZE;
+ (NSString *)RARE_WINDOW_RESIZABLE;
+ (NSString *)RARE_WINDOW_RESIZECORNER;
+ (NSString *)RARE_UNMANAGED_COMPONENT;
+ (NSString *)RARE_PREF_DIRECTORY_NAME;
+ (NSString *)SCRIPT_PREFIX;
+ (NSString *)SCRIPT_PROTOCOL_HOSTSTRING;
+ (NSString *)SCRIPT_PROTOCOL_STRING;
+ (NSString *)SDF_MIME_TYPE;
+ (NSString *)RML_MIME_TYPE;
+ (NSString *)SELECTALL_ACTION_NAME;
+ (NSString *)SVGXML_MIME_TYPE;
+ (NSString *)SVG_MIME_TYPE;
+ (NSString *)TEXT_MIME_TYPE;
+ (NSString *)TOOLS_MENU_NAME;
+ (NSString *)UNDO_ACTION_NAME;
+ (NSString *)FILE_OPEN_ACTION_NAME;
+ (NSString *)FILE_SAVE_ACTION_NAME;
+ (NSString *)UNDO_MANAGER_NAME;
+ (NSString *)UNKNOWN_MIME_TYPE;
+ (NSString *)VSCROLLBAR_BEAN_NAME;
+ (NSString *)WAV_MIME_TYPE;
+ (NSString *)WIDGET_ATT_COLUMN_NAME;
+ (NSString *)WIDGET_ATT_DATA;
+ (NSString *)WIDGET_ATT_NAME;
+ (NSString *)WIDGET_ATT_ROW_COUNT;
+ (NSString *)WIDGET_ATT_SELECTION_COLUMN;
+ (NSString *)WIDGET_ATT_SELECTION_COLUMNS;
+ (NSString *)WIDGET_ATT_SELECTION_DATA;
+ (NSString *)WIDGET_ATT_SELECTION_ROW;
+ (NSString *)WIDGET_ATT_SELECTION_ROWS;
+ (NSString *)WIDGET_ATT_SELECTION_VALUE;
+ (NSString *)WIDGET_ATT_CTXMENU_ROW;
+ (NSString *)WIDGET_ATT_SUBMIT_VALUE;
+ (NSString *)WIDGET_ATT_TITLE;
+ (NSString *)WIDGET_ATT_VALUE;
+ (NSString *)WIDGET_BASEURL;
+ (NSString *)WIDGET_CONTEXTURL;
+ (NSString *)WINDOW_MENU_NAME;
+ (NSString *)XML_MIME_TYPE;
+ (IOSObjectArray *)ZERO_LENGTH_STRING_ARRAY;
+ (int)SCRIPT_PREFIX_LENGTH;
+ (int)RESOURCE_PREFIX_LENGTH;
+ (int)LIB_PREFIX_LENGTH;
+ (int)INLINE_PREFIX_LENGTH;
+ (int)COLLECTION_PREFIX_LENGTH;
+ (NSString *)customComboBoxButton;
+ (NSString *)focusIndicatorForComboBoxes;
+ (short int)ITEM_SELECTED;
+ (short int)ITEM_INDETERMINATE;
+ (NSString *)PROPERTY_ENABLED;
@end

#define ComAppnativaRareIConstants RAREiConstants

#endif // _RAREiConstants_H_
