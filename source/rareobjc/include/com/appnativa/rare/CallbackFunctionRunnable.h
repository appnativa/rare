//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/CallbackFunctionRunnable.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RARECallbackFunctionRunnable_H_
#define _RARECallbackFunctionRunnable_H_

@protocol RAREiFunctionCallback;

#import "JreEmulation.h"
#include "java/lang/Runnable.h"

@interface RARECallbackFunctionRunnable : NSObject < JavaLangRunnable > {
 @public
  BOOL canceled_;
  id<RAREiFunctionCallback> cb_;
  id returnValue_;
}

- (id)initWithRAREiFunctionCallback:(id<RAREiFunctionCallback>)cb
                        withBoolean:(BOOL)canceled
                             withId:(id)returnValue;
- (void)run;
- (void)copyAllFieldsTo:(RARECallbackFunctionRunnable *)other;
@end

J2OBJC_FIELD_SETTER(RARECallbackFunctionRunnable, cb_, id<RAREiFunctionCallback>)
J2OBJC_FIELD_SETTER(RARECallbackFunctionRunnable, returnValue_, id)

typedef RARECallbackFunctionRunnable ComAppnativaRareCallbackFunctionRunnable;

#endif // _RARECallbackFunctionRunnable_H_
