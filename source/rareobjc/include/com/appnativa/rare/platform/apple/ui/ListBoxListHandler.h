//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/platform/apple/ui/ListBoxListHandler.java
//
//  Created by decoteaud on 7/29/15.
//

#ifndef _RAREListBoxListHandler_H_
#define _RAREListBoxListHandler_H_

@class RAREaPlatformTableBasedView;
@protocol RAREiPlatformListDataModel;

#import "JreEmulation.h"
#include "com/appnativa/rare/platform/apple/ui/aPlatformListHandler.h"

@interface RAREListBoxListHandler : RAREaPlatformListHandler {
}

- (id)initWithRAREaPlatformTableBasedView:(RAREaPlatformTableBasedView *)view
           withRAREiPlatformListDataModel:(id<RAREiPlatformListDataModel>)model;
- (id)initWithRAREaPlatformTableBasedView:(RAREaPlatformTableBasedView *)view;
- (void)selectAll;
@end

typedef RAREListBoxListHandler ComAppnativaRarePlatformAppleUiListBoxListHandler;

#endif // _RAREListBoxListHandler_H_
