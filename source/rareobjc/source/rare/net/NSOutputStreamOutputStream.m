//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/net/NSOutputStreamOutputStream.java
//
//  Created by decoteaud on 3/11/16.
//

#include "IOSByteArray.h"
#include "IOSClass.h"
#include "com/appnativa/rare/net/NSOutputStreamOutputStream.h"
#include "java/io/IOException.h"
#import "AppleHelper.h"
 #import "java/io/IOException.h"

@implementation RARENSOutputStreamOutputStream

- (id)initWithId:(id)nsstream {
  if (self = [super init]) {
    proxy_ = nsstream;
  }
  return self;
}

- (void)close {
  [((NSOutputStream*) proxy_) close];
}

- (void)writeWithInt:(int)b {
  uint8_t buffer[1];
  buffer[0]=b;
  NSOutputStream* stream=(NSOutputStream*) proxy_;
  NSInteger result = [stream write:buffer maxLength:1];
  if(result<0) {
    NSString* msg=@"";
    NSError* error=stream.streamError;
    if(error !=nil) {
      msg=[AppleHelper toErrorString: error];
    }
    @throw [[JavaIoIOException alloc] initWithNSString: msg];
  }
}

- (void)writeWithByteArray:(IOSByteArray *)b
                   withInt:(int)off
                   withInt:(int)len {
  NSOutputStream* stream=(NSOutputStream*) proxy_;
  NSInteger result = [stream write:(uint8_t*)[b byteRefAtIndex:off] maxLength:len];
  if(result<0) {
    NSString* msg=@"";
    NSError* error=stream.streamError;
    if(error !=nil) {
      msg=[AppleHelper toErrorString: error];
    }
    @throw [[JavaIoIOException alloc] initWithNSString: msg];
  }
}

- (void)copyAllFieldsTo:(RARENSOutputStreamOutputStream *)other {
  [super copyAllFieldsTo:other];
  other->proxy_ = proxy_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "close", NULL, "V", 0x101, "JavaIoIOException" },
    { "writeWithInt:", NULL, "V", 0x101, "JavaIoIOException" },
    { "writeWithByteArray:withInt:withInt:", NULL, "V", 0x101, "JavaIoIOException" },
  };
  static J2ObjcFieldInfo fields[] = {
    { "proxy_", NULL, 0x4, "LNSObject" },
  };
  static J2ObjcClassInfo _RARENSOutputStreamOutputStream = { "NSOutputStreamOutputStream", "com.appnativa.rare.net", NULL, 0x1, 3, methods, 1, fields, 0, NULL};
  return &_RARENSOutputStreamOutputStream;
}

@end
