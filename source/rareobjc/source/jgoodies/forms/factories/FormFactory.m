//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/jgoodies/forms/factories/FormFactory.java
//
//  Created by decoteaud on 3/11/16.
//

#include "IOSClass.h"
#include "com/appnativa/jgoodies/forms/factories/FormFactory.h"
#include "com/appnativa/jgoodies/forms/layout/ColumnSpec.h"
#include "com/appnativa/jgoodies/forms/layout/ConstantSize.h"
#include "com/appnativa/jgoodies/forms/layout/FormSpec.h"
#include "com/appnativa/jgoodies/forms/layout/RowSpec.h"
#include "com/appnativa/jgoodies/forms/layout/Size.h"
#include "com/appnativa/jgoodies/forms/layout/Sizes.h"
#include "com/appnativa/jgoodies/forms/util/LayoutStyle.h"
#include "java/lang/Deprecated.h"

@implementation RAREFormFactory

static RAREColumnSpec * RAREFormFactory_MIN_COLSPEC_;
static RAREColumnSpec * RAREFormFactory_PREF_COLSPEC_;
static RAREColumnSpec * RAREFormFactory_DEFAULT_COLSPEC_;
static RAREColumnSpec * RAREFormFactory_GLUE_COLSPEC_;
static RAREColumnSpec * RAREFormFactory_LABEL_COMPONENT_GAP_COLSPEC_;
static RAREColumnSpec * RAREFormFactory_RELATED_GAP_COLSPEC_;
static RAREColumnSpec * RAREFormFactory_UNRELATED_GAP_COLSPEC_;
static RAREColumnSpec * RAREFormFactory_BUTTON_COLSPEC_;
static RAREColumnSpec * RAREFormFactory_GROWING_BUTTON_COLSPEC_;
static RARERowSpec * RAREFormFactory_MIN_ROWSPEC_;
static RARERowSpec * RAREFormFactory_PREF_ROWSPEC_;
static RARERowSpec * RAREFormFactory_DEFAULT_ROWSPEC_;
static RARERowSpec * RAREFormFactory_GLUE_ROWSPEC_;
static RARERowSpec * RAREFormFactory_RELATED_GAP_ROWSPEC_;
static RARERowSpec * RAREFormFactory_UNRELATED_GAP_ROWSPEC_;
static RARERowSpec * RAREFormFactory_NARROW_LINE_GAP_ROWSPEC_;
static RARERowSpec * RAREFormFactory_LINE_GAP_ROWSPEC_;
static RARERowSpec * RAREFormFactory_PARAGRAPH_GAP_ROWSPEC_;
static RARERowSpec * RAREFormFactory_BUTTON_ROWSPEC_;

+ (RAREColumnSpec *)MIN_COLSPEC {
  return RAREFormFactory_MIN_COLSPEC_;
}

+ (RAREColumnSpec *)PREF_COLSPEC {
  return RAREFormFactory_PREF_COLSPEC_;
}

+ (RAREColumnSpec *)DEFAULT_COLSPEC {
  return RAREFormFactory_DEFAULT_COLSPEC_;
}

+ (RAREColumnSpec *)GLUE_COLSPEC {
  return RAREFormFactory_GLUE_COLSPEC_;
}

+ (RAREColumnSpec *)LABEL_COMPONENT_GAP_COLSPEC {
  return RAREFormFactory_LABEL_COMPONENT_GAP_COLSPEC_;
}

+ (RAREColumnSpec *)RELATED_GAP_COLSPEC {
  return RAREFormFactory_RELATED_GAP_COLSPEC_;
}

+ (RAREColumnSpec *)UNRELATED_GAP_COLSPEC {
  return RAREFormFactory_UNRELATED_GAP_COLSPEC_;
}

+ (RAREColumnSpec *)BUTTON_COLSPEC {
  return RAREFormFactory_BUTTON_COLSPEC_;
}

+ (RAREColumnSpec *)GROWING_BUTTON_COLSPEC {
  return RAREFormFactory_GROWING_BUTTON_COLSPEC_;
}

+ (RARERowSpec *)MIN_ROWSPEC {
  return RAREFormFactory_MIN_ROWSPEC_;
}

+ (RARERowSpec *)PREF_ROWSPEC {
  return RAREFormFactory_PREF_ROWSPEC_;
}

+ (RARERowSpec *)DEFAULT_ROWSPEC {
  return RAREFormFactory_DEFAULT_ROWSPEC_;
}

+ (RARERowSpec *)GLUE_ROWSPEC {
  return RAREFormFactory_GLUE_ROWSPEC_;
}

+ (RARERowSpec *)RELATED_GAP_ROWSPEC {
  return RAREFormFactory_RELATED_GAP_ROWSPEC_;
}

+ (RARERowSpec *)UNRELATED_GAP_ROWSPEC {
  return RAREFormFactory_UNRELATED_GAP_ROWSPEC_;
}

+ (RARERowSpec *)NARROW_LINE_GAP_ROWSPEC {
  return RAREFormFactory_NARROW_LINE_GAP_ROWSPEC_;
}

+ (RARERowSpec *)LINE_GAP_ROWSPEC {
  return RAREFormFactory_LINE_GAP_ROWSPEC_;
}

+ (RARERowSpec *)PARAGRAPH_GAP_ROWSPEC {
  return RAREFormFactory_PARAGRAPH_GAP_ROWSPEC_;
}

+ (RARERowSpec *)BUTTON_ROWSPEC {
  return RAREFormFactory_BUTTON_ROWSPEC_;
}

- (id)init {
  return [super init];
}

+ (RAREColumnSpec *)createGapColumnSpecWithRAREConstantSize:(RAREConstantSize *)gapWidth {
  return [RAREColumnSpec createGapWithRAREConstantSize:gapWidth];
}

+ (RARERowSpec *)createGapRowSpecWithRAREConstantSize:(RAREConstantSize *)gapHeight {
  return [RARERowSpec createGapWithRAREConstantSize:gapHeight];
}

+ (void)initialize {
  if (self == [RAREFormFactory class]) {
    RAREFormFactory_MIN_COLSPEC_ = [[RAREColumnSpec alloc] initWithRARESize:[RARESizes MINIMUM]];
    RAREFormFactory_PREF_COLSPEC_ = [[RAREColumnSpec alloc] initWithRARESize:[RARESizes PREFERRED]];
    RAREFormFactory_DEFAULT_COLSPEC_ = [[RAREColumnSpec alloc] initWithRARESize:[RARESizes DEFAULT]];
    RAREFormFactory_GLUE_COLSPEC_ = [[RAREColumnSpec alloc] initWithRAREFormSpec_DefaultAlignment:[RAREColumnSpec DEFAULT] withRARESize:[RARESizes ZERO] withDouble:RAREFormSpec_DEFAULT_GROW];
    RAREFormFactory_LABEL_COMPONENT_GAP_COLSPEC_ = [RAREColumnSpec createGapWithRAREConstantSize:[((RARELayoutStyle *) nil_chk([RARELayoutStyle getCurrent])) getLabelComponentPadX]];
    RAREFormFactory_RELATED_GAP_COLSPEC_ = [RAREColumnSpec createGapWithRAREConstantSize:[((RARELayoutStyle *) nil_chk([RARELayoutStyle getCurrent])) getRelatedComponentsPadX]];
    RAREFormFactory_UNRELATED_GAP_COLSPEC_ = [RAREColumnSpec createGapWithRAREConstantSize:[((RARELayoutStyle *) nil_chk([RARELayoutStyle getCurrent])) getUnrelatedComponentsPadX]];
    RAREFormFactory_BUTTON_COLSPEC_ = [[RAREColumnSpec alloc] initWithRARESize:[RARESizes boundedWithRARESize:[RARESizes PREFERRED] withRARESize:[((RARELayoutStyle *) nil_chk([RARELayoutStyle getCurrent])) getDefaultButtonWidth] withRARESize:nil]];
    RAREFormFactory_GROWING_BUTTON_COLSPEC_ = [[RAREColumnSpec alloc] initWithRAREFormSpec_DefaultAlignment:[RAREColumnSpec DEFAULT] withRARESize:[RAREFormFactory_BUTTON_COLSPEC_ getSize] withDouble:RAREFormSpec_DEFAULT_GROW];
    RAREFormFactory_MIN_ROWSPEC_ = [[RARERowSpec alloc] initWithRARESize:[RARESizes MINIMUM]];
    RAREFormFactory_PREF_ROWSPEC_ = [[RARERowSpec alloc] initWithRARESize:[RARESizes PREFERRED]];
    RAREFormFactory_DEFAULT_ROWSPEC_ = [[RARERowSpec alloc] initWithRARESize:[RARESizes DEFAULT]];
    RAREFormFactory_GLUE_ROWSPEC_ = [[RARERowSpec alloc] initWithRAREFormSpec_DefaultAlignment:[RARERowSpec DEFAULT] withRARESize:[RARESizes ZERO] withDouble:RAREFormSpec_DEFAULT_GROW];
    RAREFormFactory_RELATED_GAP_ROWSPEC_ = [RARERowSpec createGapWithRAREConstantSize:[((RARELayoutStyle *) nil_chk([RARELayoutStyle getCurrent])) getRelatedComponentsPadY]];
    RAREFormFactory_UNRELATED_GAP_ROWSPEC_ = [RARERowSpec createGapWithRAREConstantSize:[((RARELayoutStyle *) nil_chk([RARELayoutStyle getCurrent])) getUnrelatedComponentsPadY]];
    RAREFormFactory_NARROW_LINE_GAP_ROWSPEC_ = [RARERowSpec createGapWithRAREConstantSize:[((RARELayoutStyle *) nil_chk([RARELayoutStyle getCurrent])) getNarrowLinePad]];
    RAREFormFactory_LINE_GAP_ROWSPEC_ = [RARERowSpec createGapWithRAREConstantSize:[((RARELayoutStyle *) nil_chk([RARELayoutStyle getCurrent])) getLinePad]];
    RAREFormFactory_PARAGRAPH_GAP_ROWSPEC_ = [RARERowSpec createGapWithRAREConstantSize:[((RARELayoutStyle *) nil_chk([RARELayoutStyle getCurrent])) getParagraphPad]];
    RAREFormFactory_BUTTON_ROWSPEC_ = [[RARERowSpec alloc] initWithRARESize:[RARESizes boundedWithRARESize:[RARESizes PREFERRED] withRARESize:[((RARELayoutStyle *) nil_chk([RARELayoutStyle getCurrent])) getDefaultButtonHeight] withRARESize:nil]];
  }
}

+ (IOSObjectArray *)__annotations_createGapColumnSpecWithRAREConstantSize_ {
  return [IOSObjectArray arrayWithObjects:(id[]) { [[JavaLangDeprecated alloc] init] } count:1 type:[IOSClass classWithProtocol:@protocol(JavaLangAnnotationAnnotation)]];
}

+ (IOSObjectArray *)__annotations_createGapRowSpecWithRAREConstantSize_ {
  return [IOSObjectArray arrayWithObjects:(id[]) { [[JavaLangDeprecated alloc] init] } count:1 type:[IOSClass classWithProtocol:@protocol(JavaLangAnnotationAnnotation)]];
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "init", NULL, NULL, 0x2, NULL },
    { "createGapColumnSpecWithRAREConstantSize:", NULL, "LRAREColumnSpec", 0x9, NULL },
    { "createGapRowSpecWithRAREConstantSize:", NULL, "LRARERowSpec", 0x9, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "MIN_COLSPEC_", NULL, 0x19, "LRAREColumnSpec" },
    { "PREF_COLSPEC_", NULL, 0x19, "LRAREColumnSpec" },
    { "DEFAULT_COLSPEC_", NULL, 0x19, "LRAREColumnSpec" },
    { "GLUE_COLSPEC_", NULL, 0x19, "LRAREColumnSpec" },
    { "LABEL_COMPONENT_GAP_COLSPEC_", NULL, 0x19, "LRAREColumnSpec" },
    { "RELATED_GAP_COLSPEC_", NULL, 0x19, "LRAREColumnSpec" },
    { "UNRELATED_GAP_COLSPEC_", NULL, 0x19, "LRAREColumnSpec" },
    { "BUTTON_COLSPEC_", NULL, 0x19, "LRAREColumnSpec" },
    { "GROWING_BUTTON_COLSPEC_", NULL, 0x19, "LRAREColumnSpec" },
    { "MIN_ROWSPEC_", NULL, 0x19, "LRARERowSpec" },
    { "PREF_ROWSPEC_", NULL, 0x19, "LRARERowSpec" },
    { "DEFAULT_ROWSPEC_", NULL, 0x19, "LRARERowSpec" },
    { "GLUE_ROWSPEC_", NULL, 0x19, "LRARERowSpec" },
    { "RELATED_GAP_ROWSPEC_", NULL, 0x19, "LRARERowSpec" },
    { "UNRELATED_GAP_ROWSPEC_", NULL, 0x19, "LRARERowSpec" },
    { "NARROW_LINE_GAP_ROWSPEC_", NULL, 0x19, "LRARERowSpec" },
    { "LINE_GAP_ROWSPEC_", NULL, 0x19, "LRARERowSpec" },
    { "PARAGRAPH_GAP_ROWSPEC_", NULL, 0x19, "LRARERowSpec" },
    { "BUTTON_ROWSPEC_", NULL, 0x19, "LRARERowSpec" },
  };
  static J2ObjcClassInfo _RAREFormFactory = { "FormFactory", "com.appnativa.jgoodies.forms.factories", NULL, 0x11, 3, methods, 19, fields, 0, NULL};
  return &_RAREFormFactory;
}

@end
