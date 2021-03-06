//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/aUIMenu.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RAREaUIMenu_H_
#define _RAREaUIMenu_H_

@class IOSObjectArray;
@class JavaUtilHashMap;
@class RAREExpansionEvent;
@class RARERenderableDataItem;
@class RARESPOTMenuBar;
@class RAREUIAction;
@class SPOTSet;
@protocol JavaUtilList;
@protocol JavaUtilMap;
@protocol RAREiListHandler;
@protocol RAREiPlatformComponent;
@protocol RAREiWidget;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/UIMenuItem.h"
#include "com/appnativa/rare/ui/event/iPopupMenuListener.h"
#include "java/lang/Runnable.h"

@interface RAREaUIMenu : RAREUIMenuItem {
 @public
  JavaUtilHashMap *nameMap_;
  RAREUIMenuItem *selectedItem_;
  NSString *cancelButtonText_;
  BOOL modal_;
  id<RAREiWidget> invokingWidget_;
  BOOL showing_;
}

- (id)init;
- (void)addWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)comp;
- (void)setHeaderWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)comp;
- (BOOL)addWithId:(RARERenderableDataItem *)item;
- (RAREUIMenuItem *)addWithRAREUIAction:(RAREUIAction *)a;
- (void)addWithInt:(int)pos
            withId:(RARERenderableDataItem *)item;
- (RAREUIMenuItem *)addWithInt:(int)pos
              withRAREUIAction:(RAREUIAction *)a;
- (void)addSeparator;
- (void)addSeparatorWithInt:(int)pos;
- (void)clear;
- (void)clearSubItems;
- (void)dispose;
- (id)registerItemWithNSString:(NSString *)name
                        withId:(id)mi;
- (RARERenderableDataItem *)removeWithInt:(int)pos;
- (BOOL)removeWithId:(id)o;
- (void)showWithRAREiWidget:(id<RAREiWidget>)context
                withBoolean:(BOOL)modal;
- (void)showWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)owner
                               withInt:(int)x
                               withInt:(int)y;
- (void)showWithRAREiWidget:(id<RAREiWidget>)context
 withRAREiPlatformComponent:(id<RAREiPlatformComponent>)owner
                withBoolean:(BOOL)modal;
- (BOOL)isShowing;
- (id)unregisterItemWithNSString:(NSString *)name;
- (RARERenderableDataItem *)setItemWithInt:(int)pos
                withRARERenderableDataItem:(RARERenderableDataItem *)item;
- (void)setItemEnabledWithNSString:(NSString *)name
                       withBoolean:(BOOL)enabled;
- (void)setItemSelectedWithNSString:(NSString *)name
                        withBoolean:(BOOL)selected;
- (void)setItemVisibleWithNSString:(NSString *)name
                       withBoolean:(BOOL)visible;
- (void)setItemsWithJavaUtilList:(id<JavaUtilList>)items;
- (void)setItemsWithRARERenderableDataItemArray:(IOSObjectArray *)items
                                        withInt:(int)count;
- (void)setNativeItemWithInt:(int)pos
          withRAREUIMenuItem:(RAREUIMenuItem *)mi;
- (void)setSelectedItemWithRAREUIMenuItem:(RAREUIMenuItem *)item;
- (void)setValueWithRAREaUIMenu:(RAREaUIMenu *)menu;
- (void)setValueWithId:(id)value;
- (RAREUIMenuItem *)getMenuItemWithInt:(int)index;
- (RAREUIMenuItem *)getMenuItemWithNSString:(NSString *)name;
- (RAREUIMenuItem *)getSelectedItem;
- (id<JavaUtilMap>)getSubs;
- (BOOL)isItemEnabledWithNSString:(NSString *)name;
- (BOOL)isItemSelectedWithNSString:(NSString *)name;
- (BOOL)isItemVisibleWithNSString:(NSString *)name;
- (void)addToNativeMenuWithInt:(int)pos
            withRAREUIMenuItem:(RAREUIMenuItem *)mi;
- (void)configureWithRARESPOTMenuBar:(RARESPOTMenuBar *)cfg;
- (void)configureWithSPOTSet:(SPOTSet *)menus
                 withBoolean:(BOOL)addHome;
- (void)disableFocusedActionsWithRAREaUIMenu:(RAREaUIMenu *)menu
                             withRAREiWidget:(id<RAREiWidget>)context;
- (void)menuWillBecomeInvisible;
- (void)menuWillBecomeVisible;
- (void)removeNativeItemWithRAREUIMenuItem:(RAREUIMenuItem *)mi;
- (void)resetFocusedActionsWithRAREaUIMenu:(RAREaUIMenu *)menu;
- (id<RAREiWidget>)getInvoker;
- (BOOL)hasParentMenu;
- (BOOL)isAddHomeMenu;
- (void)register__WithRAREUIMenuItem:(RAREUIMenuItem *)mi;
- (void)unregisterWithRAREUIMenuItem:(RAREUIMenuItem *)mi;
- (NSString *)getCancelButtonText;
- (void)setCancelButtonTextWithNSString:(NSString *)cancelButtonText;
- (BOOL)isModal;
- (void)setModalWithBoolean:(BOOL)modal;
- (void)copyAllFieldsTo:(RAREaUIMenu *)other;
@end

J2OBJC_FIELD_SETTER(RAREaUIMenu, nameMap_, JavaUtilHashMap *)
J2OBJC_FIELD_SETTER(RAREaUIMenu, selectedItem_, RAREUIMenuItem *)
J2OBJC_FIELD_SETTER(RAREaUIMenu, cancelButtonText_, NSString *)
J2OBJC_FIELD_SETTER(RAREaUIMenu, invokingWidget_, id<RAREiWidget>)

typedef RAREaUIMenu ComAppnativaRareUiAUIMenu;

@interface RAREaUIMenu_$1 : NSObject < RAREiPopupMenuListener > {
 @public
  RAREaUIMenu *this$0_;
}

- (void)popupMenuWillBecomeVisibleWithRAREExpansionEvent:(RAREExpansionEvent *)e;
- (void)popupMenuWillBecomeInvisibleWithRAREExpansionEvent:(RAREExpansionEvent *)e;
- (void)popupMenuCanceledWithRAREExpansionEvent:(RAREExpansionEvent *)e;
- (id)initWithRAREaUIMenu:(RAREaUIMenu *)outer$;
@end

J2OBJC_FIELD_SETTER(RAREaUIMenu_$1, this$0_, RAREaUIMenu *)

@interface RAREaUIMenu_$2 : NSObject < RAREiPopupMenuListener > {
 @public
  RAREaUIMenu *this$0_;
}

- (void)popupMenuWillBecomeVisibleWithRAREExpansionEvent:(RAREExpansionEvent *)e;
- (void)popupMenuWillBecomeInvisibleWithRAREExpansionEvent:(RAREExpansionEvent *)e;
- (void)popupMenuCanceledWithRAREExpansionEvent:(RAREExpansionEvent *)e;
- (id)initWithRAREaUIMenu:(RAREaUIMenu *)outer$;
@end

J2OBJC_FIELD_SETTER(RAREaUIMenu_$2, this$0_, RAREaUIMenu *)

@interface RAREaUIMenu_$3 : NSObject < JavaLangRunnable > {
 @public
  id<RAREiListHandler> val$h_;
}

- (void)run;
- (id)initWithRAREiListHandler:(id<RAREiListHandler>)capture$0;
@end

J2OBJC_FIELD_SETTER(RAREaUIMenu_$3, val$h_, id<RAREiListHandler>)

#endif // _RAREaUIMenu_H_
