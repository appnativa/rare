//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/spot/TextArea.java
//
//  Created by decoteaud on 3/11/16.
//

#include "IOSClass.h"
#include "com/appnativa/rare/spot/TextArea.h"
#include "com/appnativa/spot/SPOTBoolean.h"
#include "com/appnativa/spot/SPOTInteger.h"
#include "com/appnativa/spot/SPOTSequence.h"
#include "java/lang/Boolean.h"
#include "java/lang/Integer.h"

@implementation RARESPOTTextArea

- (id)init {
  return [self initRARESPOTTextAreaWithBoolean:YES];
}

- (id)initRARESPOTTextAreaWithBoolean:(BOOL)optional {
  if (self = [super initWithBoolean:optional withBoolean:NO]) {
    visibleLines_ = [[SPOTInteger alloc] initWithJavaLangInteger:nil withJavaLangInteger:[JavaLangInteger valueOfWithInt:0] withJavaLangInteger:nil withBoolean:YES];
    wordWrap_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:YES] withBoolean:NO];
    supportScrolling_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:YES] withBoolean:NO];
    [self spot_setElements];
  }
  return self;
}

- (id)initWithBoolean:(BOOL)optional {
  return [self initRARESPOTTextAreaWithBoolean:optional];
}

- (id)initWithBoolean:(BOOL)optional
          withBoolean:(BOOL)setElements {
  if (self = [super initWithBoolean:optional withBoolean:setElements]) {
    visibleLines_ = [[SPOTInteger alloc] initWithJavaLangInteger:nil withJavaLangInteger:[JavaLangInteger valueOfWithInt:0] withJavaLangInteger:nil withBoolean:YES];
    wordWrap_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:YES] withBoolean:NO];
    supportScrolling_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:YES] withBoolean:NO];
  }
  return self;
}

- (void)spot_setElements {
  self->elementsSizeHint_ += 3;
  [super spot_setElements];
  [self spot_addElementWithNSString:@"visibleLines" withISPOTElement:visibleLines_];
  [self spot_addElementWithNSString:@"wordWrap" withISPOTElement:wordWrap_];
  [self spot_addElementWithNSString:@"supportScrolling" withISPOTElement:supportScrolling_];
}

- (void)copyAllFieldsTo:(RARESPOTTextArea *)other {
  [super copyAllFieldsTo:other];
  other->supportScrolling_ = supportScrolling_;
  other->visibleLines_ = visibleLines_;
  other->wordWrap_ = wordWrap_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "initWithBoolean:withBoolean:", NULL, NULL, 0x4, NULL },
    { "spot_setElements", NULL, "V", 0x4, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "visibleLines_", NULL, 0x1, "LSPOTInteger" },
    { "wordWrap_", NULL, 0x1, "LSPOTBoolean" },
    { "supportScrolling_", NULL, 0x1, "LSPOTBoolean" },
  };
  static J2ObjcClassInfo _RARESPOTTextArea = { "TextArea", "com.appnativa.rare.spot", NULL, 0x1, 2, methods, 3, fields, 0, NULL};
  return &_RARESPOTTextArea;
}

@end
