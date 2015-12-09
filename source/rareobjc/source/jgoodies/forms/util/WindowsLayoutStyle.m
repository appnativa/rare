//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/jgoodies/forms/util/WindowsLayoutStyle.java
//
//  Created by decoteaud on 12/8/15.
//

#include "com/jgoodies/forms/layout/ConstantSize.h"
#include "com/jgoodies/forms/layout/Size.h"
#include "com/jgoodies/forms/layout/Sizes.h"
#include "com/jgoodies/forms/util/WindowsLayoutStyle.h"

@implementation RAREJGWindowsLayoutStyle

static RAREJGWindowsLayoutStyle * RAREJGWindowsLayoutStyle_INSTANCE_;
static id<RAREJGSize> RAREJGWindowsLayoutStyle_BUTTON_WIDTH_;
static id<RAREJGSize> RAREJGWindowsLayoutStyle_BUTTON_HEIGHT_;
static RAREJGConstantSize * RAREJGWindowsLayoutStyle_DIALOG_MARGIN_X_;
static RAREJGConstantSize * RAREJGWindowsLayoutStyle_DIALOG_MARGIN_Y_;
static RAREJGConstantSize * RAREJGWindowsLayoutStyle_TABBED_DIALOG_MARGIN_X_;
static RAREJGConstantSize * RAREJGWindowsLayoutStyle_TABBED_DIALOG_MARGIN_Y_;
static RAREJGConstantSize * RAREJGWindowsLayoutStyle_LABEL_COMPONENT_PADX_;
static RAREJGConstantSize * RAREJGWindowsLayoutStyle_RELATED_COMPONENTS_PADX_;
static RAREJGConstantSize * RAREJGWindowsLayoutStyle_UNRELATED_COMPONENTS_PADX_;
static RAREJGConstantSize * RAREJGWindowsLayoutStyle_RELATED_COMPONENTS_PADY_;
static RAREJGConstantSize * RAREJGWindowsLayoutStyle_UNRELATED_COMPONENTS_PADY_;
static RAREJGConstantSize * RAREJGWindowsLayoutStyle_NARROW_LINE_PAD_;
static RAREJGConstantSize * RAREJGWindowsLayoutStyle_LINE_PAD_;
static RAREJGConstantSize * RAREJGWindowsLayoutStyle_PARAGRAPH_PAD_;
static RAREJGConstantSize * RAREJGWindowsLayoutStyle_BUTTON_BAR_PAD_;

+ (RAREJGWindowsLayoutStyle *)INSTANCE {
  return RAREJGWindowsLayoutStyle_INSTANCE_;
}

+ (id<RAREJGSize>)BUTTON_WIDTH {
  return RAREJGWindowsLayoutStyle_BUTTON_WIDTH_;
}

+ (id<RAREJGSize>)BUTTON_HEIGHT {
  return RAREJGWindowsLayoutStyle_BUTTON_HEIGHT_;
}

+ (RAREJGConstantSize *)DIALOG_MARGIN_X {
  return RAREJGWindowsLayoutStyle_DIALOG_MARGIN_X_;
}

+ (RAREJGConstantSize *)DIALOG_MARGIN_Y {
  return RAREJGWindowsLayoutStyle_DIALOG_MARGIN_Y_;
}

+ (RAREJGConstantSize *)TABBED_DIALOG_MARGIN_X {
  return RAREJGWindowsLayoutStyle_TABBED_DIALOG_MARGIN_X_;
}

+ (RAREJGConstantSize *)TABBED_DIALOG_MARGIN_Y {
  return RAREJGWindowsLayoutStyle_TABBED_DIALOG_MARGIN_Y_;
}

+ (RAREJGConstantSize *)LABEL_COMPONENT_PADX {
  return RAREJGWindowsLayoutStyle_LABEL_COMPONENT_PADX_;
}

+ (RAREJGConstantSize *)RELATED_COMPONENTS_PADX {
  return RAREJGWindowsLayoutStyle_RELATED_COMPONENTS_PADX_;
}

+ (RAREJGConstantSize *)UNRELATED_COMPONENTS_PADX {
  return RAREJGWindowsLayoutStyle_UNRELATED_COMPONENTS_PADX_;
}

+ (RAREJGConstantSize *)RELATED_COMPONENTS_PADY {
  return RAREJGWindowsLayoutStyle_RELATED_COMPONENTS_PADY_;
}

+ (RAREJGConstantSize *)UNRELATED_COMPONENTS_PADY {
  return RAREJGWindowsLayoutStyle_UNRELATED_COMPONENTS_PADY_;
}

+ (RAREJGConstantSize *)NARROW_LINE_PAD {
  return RAREJGWindowsLayoutStyle_NARROW_LINE_PAD_;
}

+ (RAREJGConstantSize *)LINE_PAD {
  return RAREJGWindowsLayoutStyle_LINE_PAD_;
}

+ (RAREJGConstantSize *)PARAGRAPH_PAD {
  return RAREJGWindowsLayoutStyle_PARAGRAPH_PAD_;
}

+ (RAREJGConstantSize *)BUTTON_BAR_PAD {
  return RAREJGWindowsLayoutStyle_BUTTON_BAR_PAD_;
}

- (id)init {
  return [super init];
}

- (id<RAREJGSize>)getDefaultButtonWidth {
  return RAREJGWindowsLayoutStyle_BUTTON_WIDTH_;
}

- (id<RAREJGSize>)getDefaultButtonHeight {
  return RAREJGWindowsLayoutStyle_BUTTON_HEIGHT_;
}

- (RAREJGConstantSize *)getDialogMarginX {
  return RAREJGWindowsLayoutStyle_DIALOG_MARGIN_X_;
}

- (RAREJGConstantSize *)getDialogMarginY {
  return RAREJGWindowsLayoutStyle_DIALOG_MARGIN_Y_;
}

- (RAREJGConstantSize *)getTabbedDialogMarginX {
  return RAREJGWindowsLayoutStyle_TABBED_DIALOG_MARGIN_X_;
}

- (RAREJGConstantSize *)getTabbedDialogMarginY {
  return RAREJGWindowsLayoutStyle_TABBED_DIALOG_MARGIN_Y_;
}

- (RAREJGConstantSize *)getLabelComponentPadX {
  return RAREJGWindowsLayoutStyle_LABEL_COMPONENT_PADX_;
}

- (RAREJGConstantSize *)getRelatedComponentsPadX {
  return RAREJGWindowsLayoutStyle_RELATED_COMPONENTS_PADX_;
}

- (RAREJGConstantSize *)getRelatedComponentsPadY {
  return RAREJGWindowsLayoutStyle_RELATED_COMPONENTS_PADY_;
}

- (RAREJGConstantSize *)getUnrelatedComponentsPadX {
  return RAREJGWindowsLayoutStyle_UNRELATED_COMPONENTS_PADX_;
}

- (RAREJGConstantSize *)getUnrelatedComponentsPadY {
  return RAREJGWindowsLayoutStyle_UNRELATED_COMPONENTS_PADY_;
}

- (RAREJGConstantSize *)getNarrowLinePad {
  return RAREJGWindowsLayoutStyle_NARROW_LINE_PAD_;
}

- (RAREJGConstantSize *)getLinePad {
  return RAREJGWindowsLayoutStyle_LINE_PAD_;
}

- (RAREJGConstantSize *)getParagraphPad {
  return RAREJGWindowsLayoutStyle_PARAGRAPH_PAD_;
}

- (RAREJGConstantSize *)getButtonBarPad {
  return RAREJGWindowsLayoutStyle_BUTTON_BAR_PAD_;
}

- (BOOL)isLeftToRightButtonOrder {
  return YES;
}

+ (void)initialize {
  if (self == [RAREJGWindowsLayoutStyle class]) {
    RAREJGWindowsLayoutStyle_INSTANCE_ = [[RAREJGWindowsLayoutStyle alloc] init];
    RAREJGWindowsLayoutStyle_BUTTON_WIDTH_ = [RAREJGSizes dluXWithInt:50];
    RAREJGWindowsLayoutStyle_BUTTON_HEIGHT_ = [RAREJGSizes dluYWithInt:14];
    RAREJGWindowsLayoutStyle_DIALOG_MARGIN_X_ = [RAREJGSizes DLUX7];
    RAREJGWindowsLayoutStyle_DIALOG_MARGIN_Y_ = [RAREJGSizes DLUY7];
    RAREJGWindowsLayoutStyle_TABBED_DIALOG_MARGIN_X_ = [RAREJGSizes DLUX4];
    RAREJGWindowsLayoutStyle_TABBED_DIALOG_MARGIN_Y_ = [RAREJGSizes DLUY4];
    RAREJGWindowsLayoutStyle_LABEL_COMPONENT_PADX_ = [RAREJGSizes DLUX3];
    RAREJGWindowsLayoutStyle_RELATED_COMPONENTS_PADX_ = [RAREJGSizes DLUX4];
    RAREJGWindowsLayoutStyle_UNRELATED_COMPONENTS_PADX_ = [RAREJGSizes DLUX7];
    RAREJGWindowsLayoutStyle_RELATED_COMPONENTS_PADY_ = [RAREJGSizes DLUY4];
    RAREJGWindowsLayoutStyle_UNRELATED_COMPONENTS_PADY_ = [RAREJGSizes DLUY7];
    RAREJGWindowsLayoutStyle_NARROW_LINE_PAD_ = [RAREJGSizes DLUY2];
    RAREJGWindowsLayoutStyle_LINE_PAD_ = [RAREJGSizes DLUY3];
    RAREJGWindowsLayoutStyle_PARAGRAPH_PAD_ = [RAREJGSizes DLUY9];
    RAREJGWindowsLayoutStyle_BUTTON_BAR_PAD_ = [RAREJGSizes DLUY5];
  }
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "init", NULL, NULL, 0x2, NULL },
    { "getDefaultButtonWidth", NULL, "LRAREJGSize", 0x1, NULL },
    { "getDefaultButtonHeight", NULL, "LRAREJGSize", 0x1, NULL },
    { "getDialogMarginX", NULL, "LRAREJGConstantSize", 0x1, NULL },
    { "getDialogMarginY", NULL, "LRAREJGConstantSize", 0x1, NULL },
    { "getTabbedDialogMarginX", NULL, "LRAREJGConstantSize", 0x1, NULL },
    { "getTabbedDialogMarginY", NULL, "LRAREJGConstantSize", 0x1, NULL },
    { "getLabelComponentPadX", NULL, "LRAREJGConstantSize", 0x1, NULL },
    { "getRelatedComponentsPadX", NULL, "LRAREJGConstantSize", 0x1, NULL },
    { "getRelatedComponentsPadY", NULL, "LRAREJGConstantSize", 0x1, NULL },
    { "getUnrelatedComponentsPadX", NULL, "LRAREJGConstantSize", 0x1, NULL },
    { "getUnrelatedComponentsPadY", NULL, "LRAREJGConstantSize", 0x1, NULL },
    { "getNarrowLinePad", NULL, "LRAREJGConstantSize", 0x1, NULL },
    { "getLinePad", NULL, "LRAREJGConstantSize", 0x1, NULL },
    { "getParagraphPad", NULL, "LRAREJGConstantSize", 0x1, NULL },
    { "getButtonBarPad", NULL, "LRAREJGConstantSize", 0x1, NULL },
    { "isLeftToRightButtonOrder", NULL, "Z", 0x1, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "INSTANCE_", NULL, 0x18, "LRAREJGWindowsLayoutStyle" },
    { "BUTTON_WIDTH_", NULL, 0x1a, "LRAREJGSize" },
    { "BUTTON_HEIGHT_", NULL, 0x1a, "LRAREJGSize" },
    { "DIALOG_MARGIN_X_", NULL, 0x1a, "LRAREJGConstantSize" },
    { "DIALOG_MARGIN_Y_", NULL, 0x1a, "LRAREJGConstantSize" },
    { "TABBED_DIALOG_MARGIN_X_", NULL, 0x1a, "LRAREJGConstantSize" },
    { "TABBED_DIALOG_MARGIN_Y_", NULL, 0x1a, "LRAREJGConstantSize" },
    { "LABEL_COMPONENT_PADX_", NULL, 0x1a, "LRAREJGConstantSize" },
    { "RELATED_COMPONENTS_PADX_", NULL, 0x1a, "LRAREJGConstantSize" },
    { "UNRELATED_COMPONENTS_PADX_", NULL, 0x1a, "LRAREJGConstantSize" },
    { "RELATED_COMPONENTS_PADY_", NULL, 0x1a, "LRAREJGConstantSize" },
    { "UNRELATED_COMPONENTS_PADY_", NULL, 0x1a, "LRAREJGConstantSize" },
    { "NARROW_LINE_PAD_", NULL, 0x1a, "LRAREJGConstantSize" },
    { "LINE_PAD_", NULL, 0x1a, "LRAREJGConstantSize" },
    { "PARAGRAPH_PAD_", NULL, 0x1a, "LRAREJGConstantSize" },
    { "BUTTON_BAR_PAD_", NULL, 0x1a, "LRAREJGConstantSize" },
  };
  static J2ObjcClassInfo _RAREJGWindowsLayoutStyle = { "WindowsLayoutStyle", "com.jgoodies.forms.util", NULL, 0x10, 17, methods, 16, fields, 0, NULL};
  return &_RAREJGWindowsLayoutStyle;
}

@end
