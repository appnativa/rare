//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple-android-htmllabel/android/text/style/RelativeSizeSpan.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RARERelativeSizeSpan_H_
#define _RARERelativeSizeSpan_H_

#import "JreEmulation.h"
#include "android/text/style/MetricAffectingSpan.h"

@interface RARERelativeSizeSpan : RAREMetricAffectingSpan {
 @public
  float mProportion_;
}

- (id)initWithFloat:(float)proportion;
- (int)getSpanTypeId;
- (int)describeContents;
- (float)getSizeChange;
- (void)copyAllFieldsTo:(RARERelativeSizeSpan *)other;
@end

typedef RARERelativeSizeSpan AndroidTextStyleRelativeSizeSpan;

#endif // _RARERelativeSizeSpan_H_
