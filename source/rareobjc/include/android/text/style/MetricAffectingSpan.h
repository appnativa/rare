//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple-android-htmllabel/android/text/style/MetricAffectingSpan.java
//
//  Created by decoteaud on 5/11/15.
//

#ifndef _RAREMetricAffectingSpan_H_
#define _RAREMetricAffectingSpan_H_

#import "JreEmulation.h"
#include "android/text/style/CharacterStyle.h"

@interface RAREMetricAffectingSpan : RARECharacterStyle {
}

- (RAREMetricAffectingSpan *)getUnderlying;
- (id)init;
@end

typedef RAREMetricAffectingSpan AndroidTextStyleMetricAffectingSpan;

@interface RAREMetricAffectingSpan_Passthrough : RAREMetricAffectingSpan {
 @public
  RAREMetricAffectingSpan *mStyle_;
}

- (id)initWithRAREMetricAffectingSpan:(RAREMetricAffectingSpan *)cs;
- (RAREMetricAffectingSpan *)getUnderlying;
- (void)copyAllFieldsTo:(RAREMetricAffectingSpan_Passthrough *)other;
@end

J2OBJC_FIELD_SETTER(RAREMetricAffectingSpan_Passthrough, mStyle_, RAREMetricAffectingSpan *)

#endif // _RAREMetricAffectingSpan_H_