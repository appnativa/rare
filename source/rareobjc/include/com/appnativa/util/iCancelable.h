//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../util/src/com/appnativa/util/iCancelable.java
//
//  Created by decoteaud on 12/8/15.
//

#ifndef _RAREUTiCancelable_H_
#define _RAREUTiCancelable_H_

#import "JreEmulation.h"

@protocol RAREUTiCancelable < NSObject, JavaObject >
- (void)cancelWithBoolean:(BOOL)canInterrupt;
- (BOOL)isCanceled;
- (BOOL)isDone;
@end

#define ComAppnativaUtilICancelable RAREUTiCancelable

#endif // _RAREUTiCancelable_H_
