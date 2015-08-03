//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple-android-htmllabel/android/text/style/TextAppearanceSpan.java
//
//  Created by decoteaud on 5/11/15.
//

#ifndef _RARETextAppearanceSpan_H_
#define _RARETextAppearanceSpan_H_

@class RAREUIColor;

#import "JreEmulation.h"
#include "android/text/style/MetricAffectingSpan.h"

@interface RARETextAppearanceSpan : RAREMetricAffectingSpan {
 @public
  NSString *mTypeface_;
  int mStyle_;
  int mTextSize_;
  RAREUIColor *mTextColor_;
}

- (id)initWithInt:(int)appearance;
- (id)initWithInt:(int)appearance
  withRAREUIColor:(RAREUIColor *)textColor;
- (id)initWithNSString:(NSString *)family
               withInt:(int)style
               withInt:(int)size
       withRAREUIColor:(RAREUIColor *)color;
- (int)getSpanTypeId;
- (int)describeContents;
- (NSString *)getFamily;
- (RAREUIColor *)getTextColor;
- (int)getTextSize;
- (int)getTextStyle;
- (void)copyAllFieldsTo:(RARETextAppearanceSpan *)other;
@end

J2OBJC_FIELD_SETTER(RARETextAppearanceSpan, mTypeface_, NSString *)
J2OBJC_FIELD_SETTER(RARETextAppearanceSpan, mTextColor_, RAREUIColor *)

typedef RARETextAppearanceSpan AndroidTextStyleTextAppearanceSpan;

#endif // _RARETextAppearanceSpan_H_
