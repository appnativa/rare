//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/event/TextChangeEvent.java
//
//  Created by decoteaud on 9/15/15.
//

#include "com/appnativa/rare/ui/event/TextChangeEvent.h"
#include "java/lang/CharSequence.h"

@implementation RARETextChangeEvent

- (id)initWithId:(id)source {
  if (self = [super initWithId:source]) {
    changed_ = YES;
  }
  return self;
}

- (id)initWithId:(id)source
         withInt:(int)startIndex
         withInt:(int)endIndex
withJavaLangCharSequence:(id<JavaLangCharSequence>)replacementString {
  if (self = [super initWithId:source]) {
    self->replacementString_ = replacementString;
    self->startIndex_ = startIndex;
    self->endIndex_ = endIndex;
  }
  return self;
}

- (int)getEndIndex {
  return endIndex_;
}

- (id<JavaLangCharSequence>)getReplacementString {
  return replacementString_;
}

- (int)getStartIndex {
  return startIndex_;
}

- (BOOL)isChangedEvent {
  return changed_;
}

- (void)copyAllFieldsTo:(RARETextChangeEvent *)other {
  [super copyAllFieldsTo:other];
  other->changed_ = changed_;
  other->endIndex_ = endIndex_;
  other->replacementString_ = replacementString_;
  other->startIndex_ = startIndex_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "getReplacementString", NULL, "LJavaLangCharSequence", 0x1, NULL },
    { "isChangedEvent", NULL, "Z", 0x1, NULL },
  };
  static J2ObjcClassInfo _RARETextChangeEvent = { "TextChangeEvent", "com.appnativa.rare.ui.event", NULL, 0x1, 2, methods, 0, NULL, 0, NULL};
  return &_RARETextChangeEvent;
}

@end
