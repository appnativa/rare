//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/exception/RestartException.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RARERestartException_H_
#define _RARERestartException_H_

#import "JreEmulation.h"
#include "java/lang/RuntimeException.h"

@interface RARERestartException : JavaLangRuntimeException {
 @public
  id contextObject_;
  int trackingCount_;
}

- (id)init;
- (void)setContextObjectWithId:(id)object;
- (void)setTrackingCountWithInt:(int)count;
- (id)getContextObject;
- (int)getTrackingCount;
- (int)incrementAndGetTrackingCount;
- (void)copyAllFieldsTo:(RARERestartException *)other;
@end

J2OBJC_FIELD_SETTER(RARERestartException, contextObject_, id)

typedef RARERestartException ComAppnativaRareExceptionRestartException;

#endif // _RARERestartException_H_
