//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core-spinner/com/appnativa/rare/ui/spinner/SpinnerNumberModel.java
//
//  Created by decoteaud on 7/29/15.
//

#ifndef _RARESpinnerNumberModel_H_
#define _RARESpinnerNumberModel_H_

@class JavaTextDecimalFormat;
@class RAREUTSNumber;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/spinner/aSpinnerModel.h"

@interface RARESpinnerNumberModel : RAREaSpinnerModel {
 @public
  RAREUTSNumber *mainValue_;
  RAREUTSNumber *workValue_;
  RAREUTSNumber *stepSize_;
  RAREUTSNumber *returnValue_;
  JavaTextDecimalFormat *format_;
  BOOL circular_;
  RAREUTSNumber *maximum_;
  RAREUTSNumber *minimum_;
  BOOL supportDecimalValues_;
}

- (id)initWithBoolean:(BOOL)circular;
- (id)fromStringWithNSString:(NSString *)value;
- (NSString *)toStringWithId:(id)value;
- (void)setFormatWithJavaTextDecimalFormat:(JavaTextDecimalFormat *)format;
- (void)setMaximumWithDouble:(double)maximum;
- (void)setMaximumWithRAREUTSNumber:(RAREUTSNumber *)maximum;
- (void)setMinimumWithDouble:(double)minimum;
- (void)setMinimumWithRAREUTSNumber:(RAREUTSNumber *)minimum;
- (RAREUTSNumber *)getStepSize;
- (void)setStepSizeWithNSNumber:(NSNumber *)stepSize;
- (void)setSupportDecimalValuesWithBoolean:(BOOL)supportDecimalValues;
- (void)setValueWithId:(id)value;
- (JavaTextDecimalFormat *)getFormat;
- (RAREUTSNumber *)getMaximum;
- (RAREUTSNumber *)getMinimum;
- (id)getNextValue;
- (RAREUTSNumber *)getNumber;
- (id)getPreviousValue;
- (id)getValue;
- (BOOL)isSupportDecimalValues;
- (BOOL)isCircular;
- (RAREUTSNumber *)numberValueWithId:(id)value;
- (void)copyAllFieldsTo:(RARESpinnerNumberModel *)other;
@end

J2OBJC_FIELD_SETTER(RARESpinnerNumberModel, mainValue_, RAREUTSNumber *)
J2OBJC_FIELD_SETTER(RARESpinnerNumberModel, workValue_, RAREUTSNumber *)
J2OBJC_FIELD_SETTER(RARESpinnerNumberModel, stepSize_, RAREUTSNumber *)
J2OBJC_FIELD_SETTER(RARESpinnerNumberModel, returnValue_, RAREUTSNumber *)
J2OBJC_FIELD_SETTER(RARESpinnerNumberModel, format_, JavaTextDecimalFormat *)
J2OBJC_FIELD_SETTER(RARESpinnerNumberModel, maximum_, RAREUTSNumber *)
J2OBJC_FIELD_SETTER(RARESpinnerNumberModel, minimum_, RAREUTSNumber *)

typedef RARESpinnerNumberModel ComAppnativaRareUiSpinnerSpinnerNumberModel;

#endif // _RARESpinnerNumberModel_H_
