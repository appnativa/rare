//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/LaterFunctionCallback.java
//
//  Created by decoteaud on 12/8/15.
//

#ifndef _RARELaterFunctionCallback_H_
#define _RARELaterFunctionCallback_H_

#import "JreEmulation.h"
#include "com/appnativa/rare/iFunctionCallback.h"
#include "java/lang/Runnable.h"

@interface RARELaterFunctionCallback : NSObject < RAREiFunctionCallback, JavaLangRunnable > {
 @public
  id<RAREiFunctionCallback> callback_;
  BOOL canceled_;
  id returnValue_;
}

- (id)initWithRAREiFunctionCallback:(id<RAREiFunctionCallback>)callback;
- (void)finishedWithBoolean:(BOOL)canceled
                     withId:(id)returnValue;
- (void)run;
- (void)copyAllFieldsTo:(RARELaterFunctionCallback *)other;
@end

J2OBJC_FIELD_SETTER(RARELaterFunctionCallback, callback_, id<RAREiFunctionCallback>)
J2OBJC_FIELD_SETTER(RARELaterFunctionCallback, returnValue_, id)

typedef RARELaterFunctionCallback ComAppnativaRareLaterFunctionCallback;

#endif // _RARELaterFunctionCallback_H_
