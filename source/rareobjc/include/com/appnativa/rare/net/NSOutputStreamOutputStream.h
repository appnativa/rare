//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/net/NSOutputStreamOutputStream.java
//
//  Created by decoteaud on 7/29/15.
//

#ifndef _RARENSOutputStreamOutputStream_H_
#define _RARENSOutputStreamOutputStream_H_

@class IOSByteArray;

#import "JreEmulation.h"
#include "java/io/OutputStream.h"

@interface RARENSOutputStreamOutputStream : JavaIoOutputStream {
 @public
  id proxy_;
}

- (id)initWithId:(id)nsstream;
- (void)close;
- (void)writeWithInt:(int)b;
- (void)writeWithByteArray:(IOSByteArray *)b
                   withInt:(int)off
                   withInt:(int)len;
- (void)copyAllFieldsTo:(RARENSOutputStreamOutputStream *)other;
@end

J2OBJC_FIELD_SETTER(RARENSOutputStreamOutputStream, proxy_, id)

typedef RARENSOutputStreamOutputStream ComAppnativaRareNetNSOutputStreamOutputStream;

#endif // _RARENSOutputStreamOutputStream_H_
