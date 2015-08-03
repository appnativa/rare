//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/CallbackFunctionCallback.java
//
//  Created by decoteaud on 7/29/15.
//

#ifndef _RARECallbackFunctionCallback_H_
#define _RARECallbackFunctionCallback_H_

#import "JreEmulation.h"
#include "com/appnativa/rare/iFunctionCallback.h"
#include "java/lang/Runnable.h"

@interface RARECallbackFunctionCallback : NSObject < RAREiFunctionCallback, JavaLangRunnable > {
 @public
  BOOL canceled_;
  id<RAREiFunctionCallback> cb_;
  BOOL onUIThread_;
  id returnValue_;
}

- (id)initWithRAREiFunctionCallback:(id<RAREiFunctionCallback>)cb
                        withBoolean:(BOOL)onUIThread;
- (void)finishedWithBoolean:(BOOL)canceled
                     withId:(id)returnValue;
- (void)run;
- (void)copyAllFieldsTo:(RARECallbackFunctionCallback *)other;
@end

J2OBJC_FIELD_SETTER(RARECallbackFunctionCallback, cb_, id<RAREiFunctionCallback>)
J2OBJC_FIELD_SETTER(RARECallbackFunctionCallback, returnValue_, id)

typedef RARECallbackFunctionCallback ComAppnativaRareCallbackFunctionCallback;

#endif // _RARECallbackFunctionCallback_H_