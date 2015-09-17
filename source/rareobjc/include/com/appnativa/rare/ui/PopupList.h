//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/PopupList.java
//
//  Created by decoteaud on 9/15/15.
//

#ifndef _RAREPopupList_H_
#define _RAREPopupList_H_

@class RAREActionEvent;
@class RAREExpansionEvent;
@class RAREUIDimension;
@class RAREUIEmptyBorder;
@class RAREUIPopupMenu;
@protocol JavaUtilList;
@protocol RAREiPlatformBorder;
@protocol RAREiPlatformComponent;
@protocol RAREiPlatformComponentPainter;
@protocol RAREiPlatformListHandler;
@protocol RAREiPopup;
@protocol RAREiWidget;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/event/iActionListener.h"
#include "com/appnativa/rare/ui/event/iPopupMenuListener.h"
#include "java/lang/Runnable.h"

@interface RAREPopupList : NSObject < RAREiPopupMenuListener, RAREiActionListener > {
 @public
  id<RAREiActionListener> actionListener_;
  id<RAREiWidget> contextWidget_;
  id<RAREiPopup> currentPopup_;
  BOOL initialized_;
  id<RAREiPlatformListHandler> listHandler_;
  id<RAREiPlatformComponentPainter> popupPainter_;
  id<RAREiPlatformBorder> border_;
  BOOL menuStyle_;
}

+ (RAREUIEmptyBorder *)listBorder;
+ (void)setListBorder:(RAREUIEmptyBorder *)listBorder;
- (id)initWithRAREiWidget:(id<RAREiWidget>)context;
- (void)actionPerformedWithRAREActionEvent:(RAREActionEvent *)e;
- (void)dispose;
- (void)popupMenuCanceledWithRAREExpansionEvent:(RAREExpansionEvent *)e;
- (void)popupMenuWillBecomeInvisibleWithRAREExpansionEvent:(RAREExpansionEvent *)e;
- (void)popupMenuWillBecomeVisibleWithRAREExpansionEvent:(RAREExpansionEvent *)e;
- (void)showModalPopupWithBoolean:(BOOL)showCloseButton;
- (void)showPopupWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)owner
                                    withInt:(int)x
                                    withInt:(int)y;
- (void)setItemsWithJavaUtilList:(id<JavaUtilList>)items
         withRAREiActionListener:(id<RAREiActionListener>)l
                     withBoolean:(BOOL)menuStyle
                         withInt:(int)visibleRowCount;
- (void)setMenuItemsWithRAREUIPopupMenu:(RAREUIPopupMenu *)menu;
- (void)setPopupPainterWithRAREiPlatformComponentPainter:(id<RAREiPlatformComponentPainter>)popupPainter;
- (void)setPoupuBorderWithRAREiPlatformBorder:(id<RAREiPlatformBorder>)b;
- (id<RAREiPlatformComponent>)getContent;
- (id<RAREiPlatformBorder>)getPopupBorder;
- (id<RAREiPlatformComponentPainter>)getPopupPainter;
- (RAREUIDimension *)getPreferredSize;
- (BOOL)isPopupVisible;
- (id<RAREiPopup>)createPopupWithBoolean:(BOOL)modal
                             withBoolean:(BOOL)showCloseButton;
- (void)setRenderingDefaults;
- (void)copyAllFieldsTo:(RAREPopupList *)other;
@end

J2OBJC_FIELD_SETTER(RAREPopupList, actionListener_, id<RAREiActionListener>)
J2OBJC_FIELD_SETTER(RAREPopupList, contextWidget_, id<RAREiWidget>)
J2OBJC_FIELD_SETTER(RAREPopupList, currentPopup_, id<RAREiPopup>)
J2OBJC_FIELD_SETTER(RAREPopupList, listHandler_, id<RAREiPlatformListHandler>)
J2OBJC_FIELD_SETTER(RAREPopupList, popupPainter_, id<RAREiPlatformComponentPainter>)
J2OBJC_FIELD_SETTER(RAREPopupList, border_, id<RAREiPlatformBorder>)

typedef RAREPopupList ComAppnativaRareUiPopupList;

@interface RAREPopupList_$1 : NSObject < JavaLangRunnable > {
 @public
  RAREPopupList *this$0_;
}

- (void)run;
- (id)initWithRAREPopupList:(RAREPopupList *)outer$;
@end

J2OBJC_FIELD_SETTER(RAREPopupList_$1, this$0_, RAREPopupList *)

@interface RAREPopupList_$2 : NSObject < RAREiActionListener > {
 @public
  RAREPopupList *this$0_;
}

- (void)actionPerformedWithRAREActionEvent:(RAREActionEvent *)e;
- (id)initWithRAREPopupList:(RAREPopupList *)outer$;
@end

J2OBJC_FIELD_SETTER(RAREPopupList_$2, this$0_, RAREPopupList *)

#endif // _RAREPopupList_H_
