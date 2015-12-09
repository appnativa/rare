//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/jgoodies/forms/util/LayoutStyle.java
//
//  Created by decoteaud on 12/8/15.
//

#include "com/jgoodies/forms/layout/ConstantSize.h"
#include "com/jgoodies/forms/layout/Size.h"
#include "com/jgoodies/forms/util/LayoutStyle.h"
#include "com/jgoodies/forms/util/WindowsLayoutStyle.h"

@implementation RAREJGLayoutStyle

static RAREJGLayoutStyle * RAREJGLayoutStyle_current_;

+ (RAREJGLayoutStyle *)current {
  return RAREJGLayoutStyle_current_;
}

+ (void)setCurrent:(RAREJGLayoutStyle *)current {
  RAREJGLayoutStyle_current_ = current;
}

+ (RAREJGLayoutStyle *)initialLayoutStyle {
  return [RAREJGWindowsLayoutStyle INSTANCE];
}

+ (RAREJGLayoutStyle *)getCurrent {
  return RAREJGLayoutStyle_current_;
}

+ (void)setCurrentWithRAREJGLayoutStyle:(RAREJGLayoutStyle *)newLayoutStyle {
  RAREJGLayoutStyle_current_ = newLayoutStyle;
}

- (id<RAREJGSize>)getDefaultButtonWidth {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
  return 0;
}

- (id<RAREJGSize>)getDefaultButtonHeight {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
  return 0;
}

- (RAREJGConstantSize *)getDialogMarginX {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
  return 0;
}

- (RAREJGConstantSize *)getDialogMarginY {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
  return 0;
}

- (RAREJGConstantSize *)getTabbedDialogMarginX {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
  return 0;
}

- (RAREJGConstantSize *)getTabbedDialogMarginY {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
  return 0;
}

- (RAREJGConstantSize *)getLabelComponentPadX {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
  return 0;
}

- (RAREJGConstantSize *)getRelatedComponentsPadX {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
  return 0;
}

- (RAREJGConstantSize *)getRelatedComponentsPadY {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
  return 0;
}

- (RAREJGConstantSize *)getUnrelatedComponentsPadX {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
  return 0;
}

- (RAREJGConstantSize *)getUnrelatedComponentsPadY {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
  return 0;
}

- (RAREJGConstantSize *)getNarrowLinePad {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
  return 0;
}

- (RAREJGConstantSize *)getLinePad {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
  return 0;
}

- (RAREJGConstantSize *)getParagraphPad {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
  return 0;
}

- (RAREJGConstantSize *)getButtonBarPad {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
  return 0;
}

- (BOOL)isLeftToRightButtonOrder {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
  return 0;
}

- (id)init {
  return [super init];
}

+ (void)initialize {
  if (self == [RAREJGLayoutStyle class]) {
    RAREJGLayoutStyle_current_ = [RAREJGLayoutStyle initialLayoutStyle];
  }
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "initialLayoutStyle", NULL, "LRAREJGLayoutStyle", 0xa, NULL },
    { "getCurrent", NULL, "LRAREJGLayoutStyle", 0x9, NULL },
    { "getDefaultButtonWidth", NULL, "LRAREJGSize", 0x401, NULL },
    { "getDefaultButtonHeight", NULL, "LRAREJGSize", 0x401, NULL },
    { "getDialogMarginX", NULL, "LRAREJGConstantSize", 0x401, NULL },
    { "getDialogMarginY", NULL, "LRAREJGConstantSize", 0x401, NULL },
    { "getTabbedDialogMarginX", NULL, "LRAREJGConstantSize", 0x401, NULL },
    { "getTabbedDialogMarginY", NULL, "LRAREJGConstantSize", 0x401, NULL },
    { "getLabelComponentPadX", NULL, "LRAREJGConstantSize", 0x401, NULL },
    { "getRelatedComponentsPadX", NULL, "LRAREJGConstantSize", 0x401, NULL },
    { "getRelatedComponentsPadY", NULL, "LRAREJGConstantSize", 0x401, NULL },
    { "getUnrelatedComponentsPadX", NULL, "LRAREJGConstantSize", 0x401, NULL },
    { "getUnrelatedComponentsPadY", NULL, "LRAREJGConstantSize", 0x401, NULL },
    { "getNarrowLinePad", NULL, "LRAREJGConstantSize", 0x401, NULL },
    { "getLinePad", NULL, "LRAREJGConstantSize", 0x401, NULL },
    { "getParagraphPad", NULL, "LRAREJGConstantSize", 0x401, NULL },
    { "getButtonBarPad", NULL, "LRAREJGConstantSize", 0x401, NULL },
    { "isLeftToRightButtonOrder", NULL, "Z", 0x401, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "current_", NULL, 0xa, "LRAREJGLayoutStyle" },
  };
  static J2ObjcClassInfo _RAREJGLayoutStyle = { "LayoutStyle", "com.jgoodies.forms.util", NULL, 0x401, 18, methods, 1, fields, 0, NULL};
  return &_RAREJGLayoutStyle;
}

@end
