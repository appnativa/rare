//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/widget/iBeanIntegrator.java
//
//  Created by decoteaud on 9/15/15.
//

#ifndef _RAREiBeanIntegrator_H_
#define _RAREiBeanIntegrator_H_

@class IOSObjectArray;
@class RARESPOTWidget;
@class RAREaWidgetListener;
@protocol RAREiPlatformComponent;
@protocol RAREiURLConnection;
@protocol RAREiWidget;

#import "JreEmulation.h"

@protocol RAREiBeanIntegrator < NSObject, JavaObject >
- (void)configureWithRAREiWidget:(id<RAREiWidget>)w
              withRARESPOTWidget:(RARESPOTWidget *)cfg;
- (void)disposing;
- (void)handleConnectionWithRAREiURLConnection:(id<RAREiURLConnection>)conn;
- (void)initializeListenersWithRAREaWidgetListener:(RAREaWidgetListener *)l OBJC_METHOD_FAMILY_NONE;
- (BOOL)wantsURLConnection;
- (void)setFromHTTPFormValueWithId:(id)value;
- (void)setValueWithId:(id)value;
- (id<RAREiPlatformComponent>)getContainer;
- (id<RAREiPlatformComponent>)getDataComponent;
- (id)getHTTPFormValue;
- (IOSObjectArray *)getSelectedObjects;
- (id)getSelection;
- (id)getValue;
@end

#define ComAppnativaRareWidgetIBeanIntegrator RAREiBeanIntegrator

#endif // _RAREiBeanIntegrator_H_
