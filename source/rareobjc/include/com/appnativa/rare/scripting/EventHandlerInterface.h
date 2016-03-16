//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/scripting/EventHandlerInterface.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RAREEventHandlerInterface_H_
#define _RAREEventHandlerInterface_H_

@class JavaLangReflectMethod;
@class JavaUtilConcurrentConcurrentHashMap;
@class JavaUtilEventObject;
@protocol RAREiEventHandler;
@protocol RAREiWidget;

#import "JreEmulation.h"

@interface RAREEventHandlerInterface : NSObject {
 @public
  id<RAREiEventHandler> eventHandler_;
  NSString *methodName_;
  NSString *className__;
  NSString *queryString_;
  JavaLangReflectMethod *method_;
}

+ (JavaUtilConcurrentConcurrentHashMap *)handlerMap;
+ (void)setHandlerMap:(JavaUtilConcurrentConcurrentHashMap *)handlerMap;
- (id)initWithRAREiEventHandler:(id<RAREiEventHandler>)handler;
- (id)initWithNSString:(NSString *)unparsedString;
- (id)initWithNSString:(NSString *)methodName
          withNSString:(NSString *)className_;
- (void)callHandlerWithNSString:(NSString *)eventName
                withRAREiWidget:(id<RAREiWidget>)widget
        withJavaUtilEventObject:(JavaUtilEventObject *)event;
- (void)createHandlerWithNSString:(NSString *)eventName
                  withRAREiWidget:(id<RAREiWidget>)widget;
- (id<RAREiEventHandler>)getHandler;
+ (id<RAREiEventHandler>)getHandlerWithNSString:(NSString *)className_;
- (void)copyAllFieldsTo:(RAREEventHandlerInterface *)other;
@end

J2OBJC_FIELD_SETTER(RAREEventHandlerInterface, eventHandler_, id<RAREiEventHandler>)
J2OBJC_FIELD_SETTER(RAREEventHandlerInterface, methodName_, NSString *)
J2OBJC_FIELD_SETTER(RAREEventHandlerInterface, className__, NSString *)
J2OBJC_FIELD_SETTER(RAREEventHandlerInterface, queryString_, NSString *)
J2OBJC_FIELD_SETTER(RAREEventHandlerInterface, method_, JavaLangReflectMethod *)

typedef RAREEventHandlerInterface ComAppnativaRareScriptingEventHandlerInterface;

#endif // _RAREEventHandlerInterface_H_
