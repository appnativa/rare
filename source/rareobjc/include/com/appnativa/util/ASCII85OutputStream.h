//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../util/src/com/appnativa/util/ASCII85OutputStream.java
//
//  Created by decoteaud on 12/8/15.
//

#ifndef _RAREUTASCII85OutputStream_H_
#define _RAREUTASCII85OutputStream_H_

@class IOSByteArray;
@class JavaIoOutputStream;

#import "JreEmulation.h"
#include "java/io/FilterOutputStream.h"

@interface RAREUTASCII85OutputStream : JavaIoFilterOutputStream {
 @public
  int width_;
  int count_;
  BOOL encoding_;
  int pos_;
  int tuple_;
  BOOL useSpaceCompression_;
}

- (id)initWithJavaIoOutputStream:(JavaIoOutputStream *)outArg;
- (id)initWithJavaIoOutputStream:(JavaIoOutputStream *)outArg
                     withBoolean:(BOOL)useSpaceCompression;
- (id)initWithJavaIoOutputStream:(JavaIoOutputStream *)outArg
                         withInt:(int)width
                     withBoolean:(BOOL)useSpaceCompression;
- (void)flush;
- (void)writeWithInt:(int)b;
- (void)writeUnencodedWithByteArray:(IOSByteArray *)b;
- (void)writeUnencodedWithInt:(int)b;
- (void)writeUnencodedWithByteArray:(IOSByteArray *)b
                            withInt:(int)off
                            withInt:(int)len;
- (void)encodeWithInt:(int)tuple
              withInt:(int)count;
- (void)startEncoding;
- (void)copyAllFieldsTo:(RAREUTASCII85OutputStream *)other;
@end

typedef RAREUTASCII85OutputStream ComAppnativaUtilASCII85OutputStream;

#endif // _RAREUTASCII85OutputStream_H_
