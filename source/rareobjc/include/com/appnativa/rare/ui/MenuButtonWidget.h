//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/MenuButtonWidget.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RAREMenuButtonWidget_H_
#define _RAREMenuButtonWidget_H_

@class RAREActionEvent;
@class RAREUIRectangle;
@protocol JavaUtilList;
@protocol RAREMenuButtonWidget_iPopulateCallback;
@protocol RAREiContainer;
@protocol RAREiPlatformBorder;
@protocol RAREiPopup;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/event/iActionListener.h"
#include "com/appnativa/rare/widget/PushButtonWidget.h"

@interface RAREMenuButtonWidget : RAREPushButtonWidget {
 @public
  id<RAREMenuButtonWidget_iPopulateCallback> callback_;
  BOOL useActionListenerAsSource_;
}

- (id)initWithRAREiContainer:(id<RAREiContainer>)parent
withRAREMenuButtonWidget_iPopulateCallback:(id<RAREMenuButtonWidget_iPopulateCallback>)cb;
- (void)createPopupWidget;
- (void)showPopupWidget;
- (void)setPopupBorderWithRAREiPlatformBorder:(id<RAREiPlatformBorder>)border;
- (void)setUseActionListenerAsEventSourceWithBoolean:(BOOL)useActionListenerAsSource;
- (BOOL)isUseActionListenerAsEventSource;
- (void)willShowPopupWithRAREiPopup:(id<RAREiPopup>)p
                withRAREUIRectangle:(RAREUIRectangle *)bounds;
- (void)configureListWithBoolean:(BOOL)first;
- (void)copyAllFieldsTo:(RAREMenuButtonWidget *)other;
@end

J2OBJC_FIELD_SETTER(RAREMenuButtonWidget, callback_, id<RAREMenuButtonWidget_iPopulateCallback>)

typedef RAREMenuButtonWidget ComAppnativaRareUiMenuButtonWidget;

@protocol RAREMenuButtonWidget_iPopulateCallback < NSObject, JavaObject >
- (void)addMenuItemsWithJavaUtilList:(id<JavaUtilList>)list;
@end

@interface RAREMenuButtonWidget_$1 : NSObject < RAREiActionListener > {
 @public
  RAREMenuButtonWidget *this$0_;
}

- (void)actionPerformedWithRAREActionEvent:(RAREActionEvent *)e;
- (id)initWithRAREMenuButtonWidget:(RAREMenuButtonWidget *)outer$;
@end

J2OBJC_FIELD_SETTER(RAREMenuButtonWidget_$1, this$0_, RAREMenuButtonWidget *)

#endif // _RAREMenuButtonWidget_H_
