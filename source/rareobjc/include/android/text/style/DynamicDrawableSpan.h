//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple-android-htmllabel/android/text/style/DynamicDrawableSpan.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RAREDynamicDrawableSpan_H_
#define _RAREDynamicDrawableSpan_H_

#import "JreEmulation.h"
#include "android/text/style/CharacterStyle.h"

#define RAREDynamicDrawableSpan_ALIGN_BASELINE 1
#define RAREDynamicDrawableSpan_ALIGN_BOTTOM 0

@interface RAREDynamicDrawableSpan : RARECharacterStyle {
 @public
  int mVerticalAlignment_;
}

+ (int)ALIGN_BOTTOM;
+ (int)ALIGN_BASELINE;
- (id)init;
- (id)initWithInt:(int)verticalAlignment;
- (int)getVerticalAlignment;
- (void)copyAllFieldsTo:(RAREDynamicDrawableSpan *)other;
@end

typedef RAREDynamicDrawableSpan AndroidTextStyleDynamicDrawableSpan;

#endif // _RAREDynamicDrawableSpan_H_
