//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/iComponentFactory.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RAREiComponentFactory_H_
#define _RAREiComponentFactory_H_

@class RARESPOTCollapsibleInfo;
@protocol RAREiCollapsible;
@protocol RAREiPlatformAppContext;
@protocol RAREiWidget;

#import "JreEmulation.h"

@protocol RAREiComponentFactory < NSObject, JavaObject >
- (void)setAppContextWithRAREiPlatformAppContext:(id<RAREiPlatformAppContext>)app;
- (id<RAREiCollapsible>)getCollapsibleWithRAREiWidget:(id<RAREiWidget>)context
                          withRARESPOTCollapsibleInfo:(RARESPOTCollapsibleInfo *)cfg;
@end

#define ComAppnativaRareUiIComponentFactory RAREiComponentFactory

#endif // _RAREiComponentFactory_H_
