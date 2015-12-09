//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/net/NSURLRequestOutputStream.java
//
//  Created by decoteaud on 12/8/15.
//

#ifndef _RARENSURLRequestOutputStream_H_
#define _RARENSURLRequestOutputStream_H_

@class IOSByteArray;

#import "JreEmulation.h"
#include "java/io/OutputStream.h"

@interface RARENSURLRequestOutputStream : JavaIoOutputStream {
 @public
  BOOL closed_;
  id proxyData_;
  id proxyRequest_;
}

+ (int)maxMemoryBufferSize;
+ (int *)maxMemoryBufferSizeRef;
- (id)initWithId:(id)request
         withInt:(int)bufferSize;
- (void)close;
- (void)writeWithInt:(int)b;
- (void)writeWithByteArray:(IOSByteArray *)b
                   withInt:(int)off
                   withInt:(int)len;
- (void)writeExWithInt:(int)b;
- (void)writeExWithByteArray:(IOSByteArray *)b
                     withInt:(int)off
                     withInt:(int)len;
+ (void)setMaxMemoryBufferSizeWithInt:(int)maxSize;
+ (int)getMaxMemoryBufferSize;
- (void)closeEx;
- (void)initialize__WithId:(id)request
                   withInt:(int)bufferSize
                   withInt:(int)maxSize OBJC_METHOD_FAMILY_NONE;
- (void)copyAllFieldsTo:(RARENSURLRequestOutputStream *)other;
@end

J2OBJC_FIELD_SETTER(RARENSURLRequestOutputStream, proxyData_, id)
J2OBJC_FIELD_SETTER(RARENSURLRequestOutputStream, proxyRequest_, id)

typedef RARENSURLRequestOutputStream ComAppnativaRareNetNSURLRequestOutputStream;

#endif // _RARENSURLRequestOutputStream_H_
