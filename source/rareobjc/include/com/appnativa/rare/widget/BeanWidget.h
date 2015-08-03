//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/widget/BeanWidget.java
//
//  Created by decoteaud on 7/29/15.
//

#ifndef _RAREBeanWidget_H_
#define _RAREBeanWidget_H_

@class IOSObjectArray;
@class JavaUtilEventObject;
@class RAREActionLink;
@class RAREDataEvent;
@class RARERenderableDataItem;
@class RARESPOTBean;
@class RARESPOTWidget;
@class RAREaWidgetListener;
@protocol RAREiBeanIntegrator;
@protocol RAREiContainer;
@protocol RAREiPlatformComponent;

#import "JreEmulation.h"
#include "com/appnativa/rare/widget/aPlatformWidget.h"

@interface RAREBeanWidget : RAREaPlatformWidget {
 @public
  id<RAREiBeanIntegrator> beanIntegrator_;
  RAREDataEvent *dataEvent_;
  JavaUtilEventObject *event_;
}

- (id)initWithRAREiContainer:(id<RAREiContainer>)parent;
- (id)initWithRAREiContainer:(id<RAREiContainer>)parent
  withRAREiPlatformComponent:(id<RAREiPlatformComponent>)comp;
- (id)initWithRAREiContainer:(id<RAREiContainer>)parent
  withRAREiPlatformComponent:(id<RAREiPlatformComponent>)fcomp
  withRAREiPlatformComponent:(id<RAREiPlatformComponent>)dcomp
          withRARESPOTWidget:(RARESPOTWidget *)wc;
- (id)initWithNSString:(NSString *)name
    withRAREiContainer:(id<RAREiContainer>)parent
withRAREiPlatformComponent:(id<RAREiPlatformComponent>)fcomp
withRAREiPlatformComponent:(id<RAREiPlatformComponent>)dcomp
    withRARESPOTWidget:(RARESPOTWidget *)wc;
- (void)addParsedRowWithRARERenderableDataItem:(RARERenderableDataItem *)row;
- (void)configureWithRARESPOTWidget:(RARESPOTWidget *)cfg;
- (void)dispose;
- (void)finishedParsing;
- (void)handleActionLinkWithRAREActionLink:(RAREActionLink *)link
                               withBoolean:(BOOL)deferred;
- (void)setBeanIntegratorWithRAREiBeanIntegrator:(id<RAREiBeanIntegrator>)bi;
- (void)setValueWithId:(id)value;
- (id<RAREiBeanIntegrator>)getBeanIntegrator;
- (id<RAREiPlatformComponent>)getDataComponent;
- (IOSObjectArray *)getSelectedObjects;
- (id)getSelection;
- (void)configureWithRARESPOTBean:(RARESPOTBean *)cfg;
- (void)initializeListenersWithRAREaWidgetListener:(RAREaWidgetListener *)l OBJC_METHOD_FAMILY_NONE;
- (void)copyAllFieldsTo:(RAREBeanWidget *)other;
@end

J2OBJC_FIELD_SETTER(RAREBeanWidget, beanIntegrator_, id<RAREiBeanIntegrator>)
J2OBJC_FIELD_SETTER(RAREBeanWidget, dataEvent_, RAREDataEvent *)
J2OBJC_FIELD_SETTER(RAREBeanWidget, event_, JavaUtilEventObject *)

typedef RAREBeanWidget ComAppnativaRareWidgetBeanWidget;

#endif // _RAREBeanWidget_H_
