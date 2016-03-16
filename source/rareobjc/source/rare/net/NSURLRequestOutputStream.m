//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/net/NSURLRequestOutputStream.java
//
//  Created by decoteaud on 3/11/16.
//

#include "IOSByteArray.h"
#include "IOSClass.h"
#include "com/appnativa/rare/net/NSURLRequestOutputStream.h"
#include "java/io/IOException.h"
#import "AppleHelper.h"

@implementation RARENSURLRequestOutputStream

static int RARENSURLRequestOutputStream_maxMemoryBufferSize_ = 65536;

+ (int)maxMemoryBufferSize {
  return RARENSURLRequestOutputStream_maxMemoryBufferSize_;
}

+ (int *)maxMemoryBufferSizeRef {
  return &RARENSURLRequestOutputStream_maxMemoryBufferSize_;
}

- (id)initWithId:(id)request
         withInt:(int)bufferSize {
  if (self = [super init]) {
    proxyRequest_ = request;
    [self initialize__WithId:request withInt:bufferSize withInt:RARENSURLRequestOutputStream_maxMemoryBufferSize_];
  }
  return self;
}

- (void)close {
  if (!closed_) {
    closed_ = YES;
    [self closeEx];
    proxyRequest_ = nil;
    proxyData_ = nil;
  }
}

- (void)writeWithInt:(int)b {
  if (closed_) {
    @throw [[JavaIoIOException alloc] initWithNSString:@"Stream closed"];
  }
  [self writeExWithInt:b];
}

- (void)writeWithByteArray:(IOSByteArray *)b
                   withInt:(int)off
                   withInt:(int)len {
  if (closed_) {
    @throw [[JavaIoIOException alloc] initWithNSString:@"Stream closed"];
  }
  [self writeExWithByteArray:b withInt:off withInt:len];
}

- (void)writeExWithInt:(int)b {
  [((RARECachingNSData*)proxyData_) writeWithInt: b];
}

- (void)writeExWithByteArray:(IOSByteArray *)b
                     withInt:(int)off
                     withInt:(int)len {
  [((RARECachingNSData*)proxyData_) writeWithIOSByteArray: b offset: off length: len];
}

+ (void)setMaxMemoryBufferSizeWithInt:(int)maxSize {
  RARENSURLRequestOutputStream_maxMemoryBufferSize_ = maxSize;
}

+ (int)getMaxMemoryBufferSize {
  return RARENSURLRequestOutputStream_maxMemoryBufferSize_;
}

- (void)closeEx {
  RARECachingNSData* cdata=(RARECachingNSData*)proxyData_;
  NSMutableURLRequest* request=(NSMutableURLRequest*)proxyRequest_;
  if([cdata streamWasRequired]) {
    [request setHTTPBodyStream: [cdata createStream]];
  }
  else {
    [request setHTTPBody: [cdata getData]];
  }
}

- (void)initialize__WithId:(id)request
                   withInt:(int)bufferSize
                   withInt:(int)maxSize {
  proxyData_=[[RARECachingNSData alloc] initWithBufferSize: bufferSize maxSize: maxSize];
}

- (void)copyAllFieldsTo:(RARENSURLRequestOutputStream *)other {
  [super copyAllFieldsTo:other];
  other->closed_ = closed_;
  other->proxyData_ = proxyData_;
  other->proxyRequest_ = proxyRequest_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "close", NULL, "V", 0x1, "JavaIoIOException" },
    { "writeWithInt:", NULL, "V", 0x1, "JavaIoIOException" },
    { "writeWithByteArray:withInt:withInt:", NULL, "V", 0x1, "JavaIoIOException" },
    { "writeExWithInt:", NULL, "V", 0x101, "JavaIoIOException" },
    { "writeExWithByteArray:withInt:withInt:", NULL, "V", 0x101, "JavaIoIOException" },
    { "closeEx", NULL, "V", 0x104, NULL },
    { "initialize__WithId:withInt:withInt:", NULL, "V", 0x104, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "maxMemoryBufferSize_", NULL, 0xa, "I" },
    { "closed_", NULL, 0x4, "Z" },
    { "proxyData_", NULL, 0x4, "LNSObject" },
    { "proxyRequest_", NULL, 0x4, "LNSObject" },
  };
  static J2ObjcClassInfo _RARENSURLRequestOutputStream = { "NSURLRequestOutputStream", "com.appnativa.rare.net", NULL, 0x1, 7, methods, 4, fields, 0, NULL};
  return &_RARENSURLRequestOutputStream;
}

@end
