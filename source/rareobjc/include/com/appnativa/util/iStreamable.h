//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../util/src/com/appnativa/util/iStreamable.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RAREUTiStreamable_H_
#define _RAREUTiStreamable_H_

@class JavaIoInputStream;
@class JavaIoOutputStream;

#import "JreEmulation.h"

@protocol RAREUTiStreamable < NSObject, JavaObject >
- (void)fromStreamWithJavaIoInputStream:(JavaIoInputStream *)inArg;
- (void)toStreamWithJavaIoOutputStream:(JavaIoOutputStream *)outArg;
@end

#define ComAppnativaUtilIStreamable RAREUTiStreamable

#endif // _RAREUTiStreamable_H_
