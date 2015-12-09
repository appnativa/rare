//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/net/NSDataInputStream.java
//
//  Created by decoteaud on 12/8/15.
//

#include "IOSByteArray.h"
#include "IOSClass.h"
#include "com/appnativa/rare/net/NSDataInputStream.h"
#include "java/io/IOException.h"
#import "AppleHelper.h"

@implementation RARENSDataInputStream

- (id)initWithId:(id)data {
  if (self = [super init]) {
    proxy_ = data;
    length_ = [self getLength];
  }
  return self;
}

- (int)available {
  if (closed_) {
    @throw [[JavaIoIOException alloc] initWithNSString:@"Stream closed"];
  }
  return length_ - position_;
}

- (void)close {
  proxy_ = nil;
  length_ = 0;
  position_ = 0;
  mark__ = 0;
  closed_ = YES;
}

- (void)markWithInt:(int)readAheadLimit {
  mark__ = position_;
}

- (BOOL)markSupported {
  return YES;
}

- (int)read {
  if (closed_) {
    @throw [[JavaIoIOException alloc] initWithNSString:@"Stream closed"];
  }
  if (position_ < length_) {
    return [self getByteWithInt:position_++];
  }
  return -1;
}

- (int)readWithByteArray:(IOSByteArray *)b
                 withInt:(int)off
                 withInt:(int)len {
  if (closed_) {
    @throw [[JavaIoIOException alloc] initWithNSString:@"Stream closed"];
  }
  if (position_ >= length_) {
    return -1;
  }
  if (len + position_ > length_) {
    len = length_ - position_;
  }
  [self getBytesWithByteArray:b withInt:off withInt:len withInt:position_];
  position_ += len;
  return len;
}

- (void)reset {
  position_ = mark__;
}

- (long long int)skipWithLong:(long long int)n {
  if (closed_) {
    @throw [[JavaIoIOException alloc] initWithNSString:@"Stream closed"];
  }
  if ((position_ + n) > length_) {
    n = length_ - position_;
  }
  if (n < 0) {
    return 0;
  }
  position_ += n;
  return n;
}

- (NSString *)getStringWithNSString:(NSString *)enc {
  return [AppleHelper toNSString: (NSData*)proxy_ encoding: enc];
}

- (int)getByteWithInt:(int)position {
  return (int)(((char*)[((NSData*)proxy_) bytes])[position] & 0xff);
}

- (void)getBytesWithByteArray:(IOSByteArray *)b
                      withInt:(int)off
                      withInt:(int)len
                      withInt:(int)position {
  if(position+len<=(int)[((NSData*)proxy_) length]) {
    char* source=(char*)[((NSData*)proxy_) bytes];
    [b replaceBytes:&source[position] length: len offset: off];
  }
}

- (int)getLength {
  return (int)[((NSData*)proxy_) length];
}

- (void)copyAllFieldsTo:(RARENSDataInputStream *)other {
  [super copyAllFieldsTo:other];
  other->closed_ = closed_;
  other->length_ = length_;
  other->mark__ = mark__;
  other->position_ = position_;
  other->proxy_ = proxy_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "available", NULL, "I", 0x1, "JavaIoIOException" },
    { "close", NULL, "V", 0x1, "JavaIoIOException" },
    { "markSupported", NULL, "Z", 0x1, NULL },
    { "read", NULL, "I", 0x1, "JavaIoIOException" },
    { "readWithByteArray:withInt:withInt:", NULL, "I", 0x1, "JavaIoIOException" },
    { "skipWithLong:", NULL, "J", 0x1, "JavaIoIOException" },
    { "getStringWithNSString:", NULL, "LNSString", 0x101, NULL },
    { "getByteWithInt:", NULL, "I", 0x102, NULL },
    { "getBytesWithByteArray:withInt:withInt:withInt:", NULL, "V", 0x102, NULL },
    { "getLength", NULL, "I", 0x102, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "closed_", NULL, 0x4, "Z" },
    { "length_", NULL, 0x4, "I" },
    { "mark__", "mark", 0x4, "I" },
    { "position_", NULL, 0x4, "I" },
    { "proxy_", NULL, 0x4, "LNSObject" },
  };
  static J2ObjcClassInfo _RARENSDataInputStream = { "NSDataInputStream", "com.appnativa.rare.net", NULL, 0x1, 10, methods, 5, fields, 0, NULL};
  return &_RARENSDataInputStream;
}

@end
