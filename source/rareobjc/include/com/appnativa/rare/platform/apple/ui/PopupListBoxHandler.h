//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/platform/apple/ui/PopupListBoxHandler.java
//
//  Created by decoteaud on 7/29/15.
//

#ifndef _RAREPopupListBoxHandler_H_
#define _RAREPopupListBoxHandler_H_

@protocol RAREiPlatformComponent;
@protocol RAREiPlatformListDataModel;
@protocol RAREiWidget;

#import "JreEmulation.h"
#include "com/appnativa/rare/platform/apple/ui/ListBoxListHandler.h"

@interface RAREPopupListBoxHandler : RAREListBoxListHandler {
 @public
  id<RAREiPlatformComponent> containerComponent_;
  id<RAREiPlatformComponent> listComponent_;
}

- (id)initWithRAREiWidget:(id<RAREiWidget>)w
withRAREiPlatformListDataModel:(id<RAREiPlatformListDataModel>)model
              withBoolean:(BOOL)forMenu;
- (void)dispose;
- (id<RAREiPlatformComponent>)getContainerComponent;
- (id<RAREiPlatformComponent>)getListComponent;
- (void)copyAllFieldsTo:(RAREPopupListBoxHandler *)other;
@end

J2OBJC_FIELD_SETTER(RAREPopupListBoxHandler, containerComponent_, id<RAREiPlatformComponent>)
J2OBJC_FIELD_SETTER(RAREPopupListBoxHandler, listComponent_, id<RAREiPlatformComponent>)

typedef RAREPopupListBoxHandler ComAppnativaRarePlatformAppleUiPopupListBoxHandler;

#endif // _RAREPopupListBoxHandler_H_
