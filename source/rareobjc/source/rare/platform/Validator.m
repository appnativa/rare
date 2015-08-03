//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/platform/Validator.java
//
//  Created by decoteaud on 7/29/15.
//

#include "IOSCharArray.h"
#include "com/appnativa/rare/platform/Validator.h"
#include "com/appnativa/rare/platform/aRare.h"
#include "com/appnativa/util/CharArray.h"
#include "com/appnativa/util/CharScanner.h"
#include "java/util/HashMap.h"
#include "java/util/Map.h"

@implementation RAREValidator

- (id)initWithRAREaRare:(RAREaRare *)rare {
  if (self = [super init]) {
    scanner_ = [[RAREUTCharScanner alloc] init];
    options_ = [[JavaUtilHashMap alloc] init];
    self->rare_ = rare;
  }
  return self;
}

- (void)clear {
  [((id<JavaUtilMap>) nil_chk(options_)) clear];
  [((RAREUTCharScanner *) nil_chk(scanner_)) clear];
}

- (BOOL)isValidWithRAREUTCharArray:(RAREUTCharArray *)ca
                           withInt:(int)start {
  if (ca == nil) {
    return YES;
  }
  RAREUTCharScanner *sc = scanner_;
  id<JavaUtilMap> map = options_;
  [((RAREUTCharScanner *) nil_chk(sc)) resetWithCharArray:((RAREUTCharArray *) nil_chk(ca))->A_ withInt:start withInt:ca->_length_ - start withBoolean:NO];
  if ([sc getLastChar] == ']') {
    [sc chopWithInt:1];
  }
  if ([sc getCurrentChar] == '[') {
    [sc consumeWithInt:1];
  }
  (void) [sc trim];
  if ([sc getLength] == 0) {
    return YES;
  }
  [((id<JavaUtilMap>) nil_chk(map)) clear];
  (void) [RAREUTCharScanner parseOptionStringExWithRAREUTCharScanner:sc withJavaUtilMap:options_ withChar:',' withBoolean:YES];
  NSString *s = (NSString *) check_class_cast([map getWithId:@"os"], [NSString class]);
  if ((s == nil) || ([s sequenceLength] == 0)) {
    return YES;
  }
  return [((RAREaRare *) nil_chk(rare_)) okForOSWithNSString:s withRAREUTCharScanner:sc];
}

- (void)copyAllFieldsTo:(RAREValidator *)other {
  [super copyAllFieldsTo:other];
  other->options_ = options_;
  other->rare_ = rare_;
  other->scanner_ = scanner_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "isValidWithRAREUTCharArray:withInt:", NULL, "Z", 0x1, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "scanner_", NULL, 0x11, "LRAREUTCharScanner" },
    { "options_", NULL, 0x11, "LJavaUtilMap" },
    { "rare_", NULL, 0x10, "LRAREaRare" },
  };
  static J2ObjcClassInfo _RAREValidator = { "Validator", "com.appnativa.rare.platform", NULL, 0x0, 1, methods, 3, fields, 0, NULL};
  return &_RAREValidator;
}

@end
