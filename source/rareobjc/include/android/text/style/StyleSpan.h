//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple-android-htmllabel/android/text/style/StyleSpan.java
//
//  Created by decoteaud on 9/15/15.
//

#ifndef _RAREStyleSpan_H_
#define _RAREStyleSpan_H_

#import "JreEmulation.h"
#include "android/text/style/MetricAffectingSpan.h"

@interface RAREStyleSpan : RAREMetricAffectingSpan {
 @public
  int mStyle_;
}

- (id)initWithInt:(int)style;
- (int)getSpanTypeId;
- (int)getStyle;
- (void)copyAllFieldsTo:(RAREStyleSpan *)other;
@end

typedef RAREStyleSpan AndroidTextStyleStyleSpan;

#endif // _RAREStyleSpan_H_
