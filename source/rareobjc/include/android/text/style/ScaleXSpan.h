//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple-android-htmllabel/android/text/style/ScaleXSpan.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RAREScaleXSpan_H_
#define _RAREScaleXSpan_H_

#import "JreEmulation.h"
#include "android/text/style/MetricAffectingSpan.h"

@interface RAREScaleXSpan : RAREMetricAffectingSpan {
 @public
  float mProportion_;
}

- (id)initWithFloat:(float)proportion;
- (int)getSpanTypeId;
- (int)describeContents;
- (float)getScaleX;
- (void)copyAllFieldsTo:(RAREScaleXSpan *)other;
@end

typedef RAREScaleXSpan AndroidTextStyleScaleXSpan;

#endif // _RAREScaleXSpan_H_
