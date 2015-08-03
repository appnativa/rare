//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../util/src/com/appnativa/util/UnescapingReader.java
//
//  Created by decoteaud on 7/29/15.
//

#ifndef _RAREUTUnescapingReader_H_
#define _RAREUTUnescapingReader_H_

@class IOSCharArray;
@class JavaIoIOException;
@class JavaIoReader;

#import "JreEmulation.h"
#include "java/io/FilterReader.h"

@interface RAREUTUnescapingReader : JavaIoFilterReader {
 @public
  IOSCharArray *buff_;
  int lastc_;
  JavaIoIOException *laste_;
  BOOL unescapeUnicodeOnly_;
}

- (id)initWithJavaIoReader:(JavaIoReader *)inArg;
- (id)initWithJavaIoReader:(JavaIoReader *)inArg
               withBoolean:(BOOL)unicodeOnly;
- (int)read;
- (int)readWithCharArray:(IOSCharArray *)cbuf
                 withInt:(int)off
                 withInt:(int)len;
- (void)setUnescapeUnicodeOnlyWithBoolean:(BOOL)unescapeUnicodeOnly;
- (BOOL)isUnescapeUnicodeOnly;
- (int)readAhead;
- (void)copyAllFieldsTo:(RAREUTUnescapingReader *)other;
@end

J2OBJC_FIELD_SETTER(RAREUTUnescapingReader, buff_, IOSCharArray *)
J2OBJC_FIELD_SETTER(RAREUTUnescapingReader, laste_, JavaIoIOException *)

typedef RAREUTUnescapingReader ComAppnativaUtilUnescapingReader;

#endif // _RAREUTUnescapingReader_H_