//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/jgoodies/forms/factories/FormFactory.java
//
//  Created by decoteaud on 5/11/15.
//

#include "IOSClass.h"
#include "com/jgoodies/forms/factories/FormFactory.h"
#include "com/jgoodies/forms/layout/ColumnSpec.h"
#include "com/jgoodies/forms/layout/ConstantSize.h"
#include "com/jgoodies/forms/layout/FormSpec.h"
#include "com/jgoodies/forms/layout/RowSpec.h"
#include "com/jgoodies/forms/layout/Size.h"
#include "com/jgoodies/forms/layout/Sizes.h"
#include "com/jgoodies/forms/util/LayoutStyle.h"
#include "java/lang/Deprecated.h"

@implementation RAREJGFormFactory

static RAREJGColumnSpec * RAREJGFormFactory_MIN_COLSPEC_;
static RAREJGColumnSpec * RAREJGFormFactory_PREF_COLSPEC_;
static RAREJGColumnSpec * RAREJGFormFactory_DEFAULT_COLSPEC_;
static RAREJGColumnSpec * RAREJGFormFactory_GLUE_COLSPEC_;
static RAREJGColumnSpec * RAREJGFormFactory_LABEL_COMPONENT_GAP_COLSPEC_;
static RAREJGColumnSpec * RAREJGFormFactory_RELATED_GAP_COLSPEC_;
static RAREJGColumnSpec * RAREJGFormFactory_UNRELATED_GAP_COLSPEC_;
static RAREJGColumnSpec * RAREJGFormFactory_BUTTON_COLSPEC_;
static RAREJGColumnSpec * RAREJGFormFactory_GROWING_BUTTON_COLSPEC_;
static RAREJGRowSpec * RAREJGFormFactory_MIN_ROWSPEC_;
static RAREJGRowSpec * RAREJGFormFactory_PREF_ROWSPEC_;
static RAREJGRowSpec * RAREJGFormFactory_DEFAULT_ROWSPEC_;
static RAREJGRowSpec * RAREJGFormFactory_GLUE_ROWSPEC_;
static RAREJGRowSpec * RAREJGFormFactory_RELATED_GAP_ROWSPEC_;
static RAREJGRowSpec * RAREJGFormFactory_UNRELATED_GAP_ROWSPEC_;
static RAREJGRowSpec * RAREJGFormFactory_NARROW_LINE_GAP_ROWSPEC_;
static RAREJGRowSpec * RAREJGFormFactory_LINE_GAP_ROWSPEC_;
static RAREJGRowSpec * RAREJGFormFactory_PARAGRAPH_GAP_ROWSPEC_;
static RAREJGRowSpec * RAREJGFormFactory_BUTTON_ROWSPEC_;

+ (RAREJGColumnSpec *)MIN_COLSPEC {
  return RAREJGFormFactory_MIN_COLSPEC_;
}

+ (RAREJGColumnSpec *)PREF_COLSPEC {
  return RAREJGFormFactory_PREF_COLSPEC_;
}

+ (RAREJGColumnSpec *)DEFAULT_COLSPEC {
  return RAREJGFormFactory_DEFAULT_COLSPEC_;
}

+ (RAREJGColumnSpec *)GLUE_COLSPEC {
  return RAREJGFormFactory_GLUE_COLSPEC_;
}

+ (RAREJGColumnSpec *)LABEL_COMPONENT_GAP_COLSPEC {
  return RAREJGFormFactory_LABEL_COMPONENT_GAP_COLSPEC_;
}

+ (RAREJGColumnSpec *)RELATED_GAP_COLSPEC {
  return RAREJGFormFactory_RELATED_GAP_COLSPEC_;
}

+ (RAREJGColumnSpec *)UNRELATED_GAP_COLSPEC {
  return RAREJGFormFactory_UNRELATED_GAP_COLSPEC_;
}

+ (RAREJGColumnSpec *)BUTTON_COLSPEC {
  return RAREJGFormFactory_BUTTON_COLSPEC_;
}

+ (RAREJGColumnSpec *)GROWING_BUTTON_COLSPEC {
  return RAREJGFormFactory_GROWING_BUTTON_COLSPEC_;
}

+ (RAREJGRowSpec *)MIN_ROWSPEC {
  return RAREJGFormFactory_MIN_ROWSPEC_;
}

+ (RAREJGRowSpec *)PREF_ROWSPEC {
  return RAREJGFormFactory_PREF_ROWSPEC_;
}

+ (RAREJGRowSpec *)DEFAULT_ROWSPEC {
  return RAREJGFormFactory_DEFAULT_ROWSPEC_;
}

+ (RAREJGRowSpec *)GLUE_ROWSPEC {
  return RAREJGFormFactory_GLUE_ROWSPEC_;
}

+ (RAREJGRowSpec *)RELATED_GAP_ROWSPEC {
  return RAREJGFormFactory_RELATED_GAP_ROWSPEC_;
}

+ (RAREJGRowSpec *)UNRELATED_GAP_ROWSPEC {
  return RAREJGFormFactory_UNRELATED_GAP_ROWSPEC_;
}

+ (RAREJGRowSpec *)NARROW_LINE_GAP_ROWSPEC {
  return RAREJGFormFactory_NARROW_LINE_GAP_ROWSPEC_;
}

+ (RAREJGRowSpec *)LINE_GAP_ROWSPEC {
  return RAREJGFormFactory_LINE_GAP_ROWSPEC_;
}

+ (RAREJGRowSpec *)PARAGRAPH_GAP_ROWSPEC {
  return RAREJGFormFactory_PARAGRAPH_GAP_ROWSPEC_;
}

+ (RAREJGRowSpec *)BUTTON_ROWSPEC {
  return RAREJGFormFactory_BUTTON_ROWSPEC_;
}

- (id)init {
  return [super init];
}

+ (RAREJGColumnSpec *)createGapColumnSpecWithRAREJGConstantSize:(RAREJGConstantSize *)gapWidth {
  return [RAREJGColumnSpec createGapWithRAREJGConstantSize:gapWidth];
}

+ (RAREJGRowSpec *)createGapRowSpecWithRAREJGConstantSize:(RAREJGConstantSize *)gapHeight {
  return [RAREJGRowSpec createGapWithRAREJGConstantSize:gapHeight];
}

+ (void)initialize {
  if (self == [RAREJGFormFactory class]) {
    RAREJGFormFactory_MIN_COLSPEC_ = [[RAREJGColumnSpec alloc] initWithRAREJGSize:[RAREJGSizes MINIMUM]];
    RAREJGFormFactory_PREF_COLSPEC_ = [[RAREJGColumnSpec alloc] initWithRAREJGSize:[RAREJGSizes PREFERRED]];
    RAREJGFormFactory_DEFAULT_COLSPEC_ = [[RAREJGColumnSpec alloc] initWithRAREJGSize:[RAREJGSizes DEFAULT]];
    RAREJGFormFactory_GLUE_COLSPEC_ = [[RAREJGColumnSpec alloc] initWithRAREJGFormSpec_DefaultAlignment:[RAREJGColumnSpec DEFAULT] withRAREJGSize:[RAREJGSizes ZERO] withDouble:RAREJGFormSpec_DEFAULT_GROW];
    RAREJGFormFactory_LABEL_COMPONENT_GAP_COLSPEC_ = [RAREJGColumnSpec createGapWithRAREJGConstantSize:[((RAREJGLayoutStyle *) nil_chk([RAREJGLayoutStyle getCurrent])) getLabelComponentPadX]];
    RAREJGFormFactory_RELATED_GAP_COLSPEC_ = [RAREJGColumnSpec createGapWithRAREJGConstantSize:[((RAREJGLayoutStyle *) nil_chk([RAREJGLayoutStyle getCurrent])) getRelatedComponentsPadX]];
    RAREJGFormFactory_UNRELATED_GAP_COLSPEC_ = [RAREJGColumnSpec createGapWithRAREJGConstantSize:[((RAREJGLayoutStyle *) nil_chk([RAREJGLayoutStyle getCurrent])) getUnrelatedComponentsPadX]];
    RAREJGFormFactory_BUTTON_COLSPEC_ = [[RAREJGColumnSpec alloc] initWithRAREJGSize:[RAREJGSizes boundedWithRAREJGSize:[RAREJGSizes PREFERRED] withRAREJGSize:[((RAREJGLayoutStyle *) nil_chk([RAREJGLayoutStyle getCurrent])) getDefaultButtonWidth] withRAREJGSize:nil]];
    RAREJGFormFactory_GROWING_BUTTON_COLSPEC_ = [[RAREJGColumnSpec alloc] initWithRAREJGFormSpec_DefaultAlignment:[RAREJGColumnSpec DEFAULT] withRAREJGSize:[RAREJGFormFactory_BUTTON_COLSPEC_ getSize] withDouble:RAREJGFormSpec_DEFAULT_GROW];
    RAREJGFormFactory_MIN_ROWSPEC_ = [[RAREJGRowSpec alloc] initWithRAREJGSize:[RAREJGSizes MINIMUM]];
    RAREJGFormFactory_PREF_ROWSPEC_ = [[RAREJGRowSpec alloc] initWithRAREJGSize:[RAREJGSizes PREFERRED]];
    RAREJGFormFactory_DEFAULT_ROWSPEC_ = [[RAREJGRowSpec alloc] initWithRAREJGSize:[RAREJGSizes DEFAULT]];
    RAREJGFormFactory_GLUE_ROWSPEC_ = [[RAREJGRowSpec alloc] initWithRAREJGFormSpec_DefaultAlignment:[RAREJGRowSpec DEFAULT] withRAREJGSize:[RAREJGSizes ZERO] withDouble:RAREJGFormSpec_DEFAULT_GROW];
    RAREJGFormFactory_RELATED_GAP_ROWSPEC_ = [RAREJGRowSpec createGapWithRAREJGConstantSize:[((RAREJGLayoutStyle *) nil_chk([RAREJGLayoutStyle getCurrent])) getRelatedComponentsPadY]];
    RAREJGFormFactory_UNRELATED_GAP_ROWSPEC_ = [RAREJGRowSpec createGapWithRAREJGConstantSize:[((RAREJGLayoutStyle *) nil_chk([RAREJGLayoutStyle getCurrent])) getUnrelatedComponentsPadY]];
    RAREJGFormFactory_NARROW_LINE_GAP_ROWSPEC_ = [RAREJGRowSpec createGapWithRAREJGConstantSize:[((RAREJGLayoutStyle *) nil_chk([RAREJGLayoutStyle getCurrent])) getNarrowLinePad]];
    RAREJGFormFactory_LINE_GAP_ROWSPEC_ = [RAREJGRowSpec createGapWithRAREJGConstantSize:[((RAREJGLayoutStyle *) nil_chk([RAREJGLayoutStyle getCurrent])) getLinePad]];
    RAREJGFormFactory_PARAGRAPH_GAP_ROWSPEC_ = [RAREJGRowSpec createGapWithRAREJGConstantSize:[((RAREJGLayoutStyle *) nil_chk([RAREJGLayoutStyle getCurrent])) getParagraphPad]];
    RAREJGFormFactory_BUTTON_ROWSPEC_ = [[RAREJGRowSpec alloc] initWithRAREJGSize:[RAREJGSizes boundedWithRAREJGSize:[RAREJGSizes PREFERRED] withRAREJGSize:[((RAREJGLayoutStyle *) nil_chk([RAREJGLayoutStyle getCurrent])) getDefaultButtonHeight] withRAREJGSize:nil]];
  }
}

+ (IOSObjectArray *)__annotations_createGapColumnSpecWithRAREJGConstantSize_ {
  return [IOSObjectArray arrayWithObjects:(id[]) { [[JavaLangDeprecated alloc] init] } count:1 type:[IOSClass classWithProtocol:@protocol(JavaLangAnnotationAnnotation)]];
}

+ (IOSObjectArray *)__annotations_createGapRowSpecWithRAREJGConstantSize_ {
  return [IOSObjectArray arrayWithObjects:(id[]) { [[JavaLangDeprecated alloc] init] } count:1 type:[IOSClass classWithProtocol:@protocol(JavaLangAnnotationAnnotation)]];
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "init", NULL, NULL, 0x2, NULL },
    { "createGapColumnSpecWithRAREJGConstantSize:", NULL, "LRAREJGColumnSpec", 0x9, NULL },
    { "createGapRowSpecWithRAREJGConstantSize:", NULL, "LRAREJGRowSpec", 0x9, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "MIN_COLSPEC_", NULL, 0x19, "LRAREJGColumnSpec" },
    { "PREF_COLSPEC_", NULL, 0x19, "LRAREJGColumnSpec" },
    { "DEFAULT_COLSPEC_", NULL, 0x19, "LRAREJGColumnSpec" },
    { "GLUE_COLSPEC_", NULL, 0x19, "LRAREJGColumnSpec" },
    { "LABEL_COMPONENT_GAP_COLSPEC_", NULL, 0x19, "LRAREJGColumnSpec" },
    { "RELATED_GAP_COLSPEC_", NULL, 0x19, "LRAREJGColumnSpec" },
    { "UNRELATED_GAP_COLSPEC_", NULL, 0x19, "LRAREJGColumnSpec" },
    { "BUTTON_COLSPEC_", NULL, 0x19, "LRAREJGColumnSpec" },
    { "GROWING_BUTTON_COLSPEC_", NULL, 0x19, "LRAREJGColumnSpec" },
    { "MIN_ROWSPEC_", NULL, 0x19, "LRAREJGRowSpec" },
    { "PREF_ROWSPEC_", NULL, 0x19, "LRAREJGRowSpec" },
    { "DEFAULT_ROWSPEC_", NULL, 0x19, "LRAREJGRowSpec" },
    { "GLUE_ROWSPEC_", NULL, 0x19, "LRAREJGRowSpec" },
    { "RELATED_GAP_ROWSPEC_", NULL, 0x19, "LRAREJGRowSpec" },
    { "UNRELATED_GAP_ROWSPEC_", NULL, 0x19, "LRAREJGRowSpec" },
    { "NARROW_LINE_GAP_ROWSPEC_", NULL, 0x19, "LRAREJGRowSpec" },
    { "LINE_GAP_ROWSPEC_", NULL, 0x19, "LRAREJGRowSpec" },
    { "PARAGRAPH_GAP_ROWSPEC_", NULL, 0x19, "LRAREJGRowSpec" },
    { "BUTTON_ROWSPEC_", NULL, 0x19, "LRAREJGRowSpec" },
  };
  static J2ObjcClassInfo _RAREJGFormFactory = { "FormFactory", "com.jgoodies.forms.factories", NULL, 0x11, 3, methods, 19, fields, 0, NULL};
  return &_RAREJGFormFactory;
}

@end