//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/FunctionCallbackChainer.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RAREFunctionCallbackChainer_H_
#define _RAREFunctionCallbackChainer_H_

@class RAREUTIdentityArrayList;
@protocol RAREiWidget;

#import "JreEmulation.h"
#include "com/appnativa/rare/iFunctionCallback.h"

@interface RAREFunctionCallbackChainer : NSObject < RAREiFunctionCallback > {
 @public
  RAREUTIdentityArrayList *callbacks_;
  int position_;
}

- (id)init;
- (RAREFunctionCallbackChainer *)addCallbackWithRAREiFunctionCallback:(id<RAREiFunctionCallback>)callback;
- (void)finishedWithBoolean:(BOOL)canceled
                     withId:(id)returnValue;
- (id<RAREiFunctionCallback>)nextCallback;
- (int)getPosition;
- (BOOL)isDone;
- (BOOL)isErrorWithRAREiWidget:(id<RAREiWidget>)errorHandler
                        withId:(id)value;
- (void)copyAllFieldsTo:(RAREFunctionCallbackChainer *)other;
@end

J2OBJC_FIELD_SETTER(RAREFunctionCallbackChainer, callbacks_, RAREUTIdentityArrayList *)

typedef RAREFunctionCallbackChainer ComAppnativaRareFunctionCallbackChainer;

#endif // _RAREFunctionCallbackChainer_H_
