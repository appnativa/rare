//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../util/src/com/appnativa/util/UnescapingReader.java
//
//  Created by decoteaud on 3/11/16.
//

#include "IOSCharArray.h"
#include "IOSClass.h"
#include "com/appnativa/util/CharScanner.h"
#include "com/appnativa/util/UnescapingReader.h"
#include "java/io/IOException.h"
#include "java/io/Reader.h"

@implementation RAREUTUnescapingReader

- (id)initWithJavaIoReader:(JavaIoReader *)inArg {
  if (self = [super initWithJavaIoReader:inArg]) {
    buff_ = [IOSCharArray arrayWithLength:4];
    lastc_ = 0;
    laste_ = nil;
    unescapeUnicodeOnly_ = NO;
  }
  return self;
}

- (id)initWithJavaIoReader:(JavaIoReader *)inArg
               withBoolean:(BOOL)unicodeOnly {
  if (self = [super initWithJavaIoReader:inArg]) {
    buff_ = [IOSCharArray arrayWithLength:4];
    lastc_ = 0;
    laste_ = nil;
    unescapeUnicodeOnly_ = unicodeOnly;
  }
  return self;
}

- (int)read {
  if (laste_ != nil) {
    JavaIoIOException *e = laste_;
    laste_ = nil;
    @throw e;
  }
  int c = (lastc_ == 0) ? [((JavaIoReader *) nil_chk(in_)) read] : lastc_;
  lastc_ = 0;
  if ((c != -1) && (c == '\\')) {
    do {
      int d = [self readAhead];
      if (d == -1) {
        break;
      }
      if ((d != 'u') && unescapeUnicodeOnly_) {
        lastc_ = d;
        break;
      }
      c = d;
      {
        int d2;
        int c2;
        int c3;
        int c4;
        switch (d) {
          case 'r':
          c = 0x000d;
          break;
          case 'n':
          c = 0x000a;
          break;
          case 'f':
          c = 0x000c;
          break;
          case 't':
          c = 0x0009;
          break;
          case 'u':
          while ((d2 = [self readAhead]) == 'u') {
          }
          c = d = d2;
          d2 = (d2 < 0) ? -1 : [self readAhead];
          c2 = (d2 < 0) ? '0' : d2;
          d2 = (d2 < 0) ? -1 : [self readAhead];
          d = (d2 > 0) ? d2 : d;
          c3 = (d2 < 0) ? '0' : d2;
          d2 = (d2 < 0) ? -1 : [self readAhead];
          d = (d2 > 0) ? d2 : d;
          c4 = (d2 < 0) ? '0' : d2;
          (*IOSCharArray_GetRef(nil_chk(buff_), 0)) = (unichar) c;
          (*IOSCharArray_GetRef(buff_, 1)) = (unichar) c2;
          (*IOSCharArray_GetRef(buff_, 2)) = (unichar) c3;
          (*IOSCharArray_GetRef(buff_, 3)) = (unichar) c4;
          c = [RAREUTCharScanner unicodeStringToCharWithCharArray:buff_ withInt:0];
          break;
          default:
          break;
        }
      }
    }
    while (NO);
  }
  return c;
}

- (int)readWithCharArray:(IOSCharArray *)cbuf
                 withInt:(int)off
                 withInt:(int)len {
  int cnt = 0;
  int c = -1;
  len += off;
  while (off < len) {
    c = [self read];
    if (c == -1) {
      break;
    }
    (*IOSCharArray_GetRef(nil_chk(cbuf), off++)) = (unichar) c;
    cnt++;
  }
  return ((c == -1) && (cnt == 0)) ? -1 : cnt;
}

- (void)setUnescapeUnicodeOnlyWithBoolean:(BOOL)unescapeUnicodeOnly {
  self->unescapeUnicodeOnly_ = unescapeUnicodeOnly;
}

- (BOOL)isUnescapeUnicodeOnly {
  return unescapeUnicodeOnly_;
}

- (int)readAhead {
  @try {
    return [((JavaIoReader *) nil_chk(in_)) read];
  }
  @catch (JavaIoIOException *e) {
    laste_ = e;
    return -1;
  }
}

- (void)copyAllFieldsTo:(RAREUTUnescapingReader *)other {
  [super copyAllFieldsTo:other];
  other->buff_ = buff_;
  other->lastc_ = lastc_;
  other->laste_ = laste_;
  other->unescapeUnicodeOnly_ = unescapeUnicodeOnly_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "read", NULL, "I", 0x1, "JavaIoIOException" },
    { "readWithCharArray:withInt:withInt:", NULL, "I", 0x1, "JavaIoIOException" },
    { "isUnescapeUnicodeOnly", NULL, "Z", 0x1, NULL },
    { "readAhead", NULL, "I", 0x2, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "buff_", NULL, 0x0, "LIOSCharArray" },
    { "lastc_", NULL, 0x0, "I" },
    { "laste_", NULL, 0x0, "LJavaIoIOException" },
  };
  static J2ObjcClassInfo _RAREUTUnescapingReader = { "UnescapingReader", "com.appnativa.util", NULL, 0x1, 4, methods, 3, fields, 0, NULL};
  return &_RAREUTUnescapingReader;
}

@end
