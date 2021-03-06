//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple-android-htmllabel/org/ccil/cowan/tagsoup/ElementType.java
//
//  Created by decoteaud on 3/11/16.
//

#include "java/lang/StringBuffer.h"
#include "org/ccil/cowan/tagsoup/AttributesImpl.h"
#include "org/ccil/cowan/tagsoup/ElementType.h"
#include "org/ccil/cowan/tagsoup/Schema.h"

@implementation RAREElementType

- (id)initWithNSString:(NSString *)name
               withInt:(int)model
               withInt:(int)memberOf
               withInt:(int)flags
        withRARESchema:(RARESchema *)schema {
  if (self = [super init]) {
    theName_ = name;
    theModel_ = model;
    theMemberOf_ = memberOf;
    theFlags_ = flags;
    theAtts_ = [[RAREAttributesImpl alloc] init];
    theSchema_ = schema;
    theNamespace_ = [self namespace__WithNSString:name withBoolean:NO];
    theLocalName_ = [self localNameWithNSString:name];
  }
  return self;
}

- (NSString *)namespace__WithNSString:(NSString *)name
                          withBoolean:(BOOL)attribute {
  int colon = [((NSString *) nil_chk(name)) indexOf:':'];
  if (colon == -1) {
    return attribute ? @"" : [((RARESchema *) nil_chk(theSchema_)) getURI];
  }
  NSString *prefix = [name substring:0 endIndex:colon];
  if ([((NSString *) nil_chk(prefix)) isEqual:@"xml"]) {
    return @"http://www.w3.org/XML/1998/namespace";
  }
  else {
    return [([NSString stringWithFormat:@"urn:x-prefix:%@", prefix]) intern];
  }
}

- (NSString *)localNameWithNSString:(NSString *)name {
  int colon = [((NSString *) nil_chk(name)) indexOf:':'];
  if (colon == -1) {
    return name;
  }
  else {
    return [((NSString *) nil_chk([name substring:colon + 1])) intern];
  }
}

- (NSString *)name {
  return theName_;
}

- (NSString *)namespace__ {
  return theNamespace_;
}

- (NSString *)localName {
  return theLocalName_;
}

- (int)model {
  return theModel_;
}

- (int)memberOf {
  return theMemberOf_;
}

- (int)flags {
  return theFlags_;
}

- (RAREAttributesImpl *)atts {
  return theAtts_;
}

- (RAREElementType *)parent {
  return theParent_;
}

- (RARESchema *)schema {
  return theSchema_;
}

- (BOOL)canContainWithRAREElementType:(RAREElementType *)other {
  return (theModel_ & ((RAREElementType *) nil_chk(other))->theMemberOf_) != 0;
}

- (void)setAttributeWithRAREAttributesImpl:(RAREAttributesImpl *)atts
                              withNSString:(NSString *)name
                              withNSString:(NSString *)type
                              withNSString:(NSString *)value {
  if ([((NSString *) nil_chk(name)) isEqual:@"xmlns"] || [name hasPrefix:@"xmlns:"]) {
    return;
  }
  ;
  NSString *namespace_ = [self namespace__WithNSString:name withBoolean:YES];
  NSString *localName = [self localNameWithNSString:name];
  int i = [((RAREAttributesImpl *) nil_chk(atts)) getIndexWithNSString:name];
  if (i == -1) {
    name = [name intern];
    if (type == nil) type = @"CDATA";
    if (![((NSString *) nil_chk(type)) isEqual:@"CDATA"]) value = [RAREElementType normalizeWithNSString:value];
    [atts addAttributeWithNSString:namespace_ withNSString:localName withNSString:name withNSString:type withNSString:value];
  }
  else {
    if (type == nil) type = [atts getTypeWithInt:i];
    if (![((NSString *) nil_chk(type)) isEqual:@"CDATA"]) value = [RAREElementType normalizeWithNSString:value];
    [atts setAttributeWithInt:i withNSString:namespace_ withNSString:localName withNSString:name withNSString:type withNSString:value];
  }
}

+ (NSString *)normalizeWithNSString:(NSString *)value {
  if (value == nil) return value;
  value = [((NSString *) nil_chk(value)) trim];
  if ([((NSString *) nil_chk(value)) indexOfString:@"  "] == -1) return value;
  BOOL space = NO;
  int len = [value sequenceLength];
  JavaLangStringBuffer *b = [[JavaLangStringBuffer alloc] initWithInt:len];
  for (int i = 0; i < len; i++) {
    unichar v = [value charAtWithInt:i];
    if (v == ' ') {
      if (!space) (void) [b appendWithChar:v];
      space = YES;
    }
    else {
      (void) [b appendWithChar:v];
      space = NO;
    }
  }
  return [b description];
}

- (void)setAttributeWithNSString:(NSString *)name
                    withNSString:(NSString *)type
                    withNSString:(NSString *)value {
  [self setAttributeWithRAREAttributesImpl:theAtts_ withNSString:name withNSString:type withNSString:value];
}

- (void)setModelWithInt:(int)model {
  theModel_ = model;
}

- (void)setMemberOfWithInt:(int)memberOf {
  theMemberOf_ = memberOf;
}

- (void)setFlagsWithInt:(int)flags {
  theFlags_ = flags;
}

- (void)setParentWithRAREElementType:(RAREElementType *)parent {
  theParent_ = parent;
}

- (void)copyAllFieldsTo:(RAREElementType *)other {
  [super copyAllFieldsTo:other];
  other->theAtts_ = theAtts_;
  other->theFlags_ = theFlags_;
  other->theLocalName_ = theLocalName_;
  other->theMemberOf_ = theMemberOf_;
  other->theModel_ = theModel_;
  other->theName_ = theName_;
  other->theNamespace_ = theNamespace_;
  other->theParent_ = theParent_;
  other->theSchema_ = theSchema_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "namespace__WithNSString:withBoolean:", NULL, "LNSString", 0x1, NULL },
    { "localNameWithNSString:", NULL, "LNSString", 0x1, NULL },
    { "name", NULL, "LNSString", 0x1, NULL },
    { "namespace__", NULL, "LNSString", 0x1, NULL },
    { "localName", NULL, "LNSString", 0x1, NULL },
    { "atts", NULL, "LRAREAttributesImpl", 0x1, NULL },
    { "parent", NULL, "LRAREElementType", 0x1, NULL },
    { "schema", NULL, "LRARESchema", 0x1, NULL },
    { "canContainWithRAREElementType:", NULL, "Z", 0x1, NULL },
    { "normalizeWithNSString:", NULL, "LNSString", 0x9, NULL },
  };
  static J2ObjcClassInfo _RAREElementType = { "ElementType", "org.ccil.cowan.tagsoup", NULL, 0x1, 10, methods, 0, NULL, 0, NULL};
  return &_RAREElementType;
}

@end
