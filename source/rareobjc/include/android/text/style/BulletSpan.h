//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple-android-htmllabel/android/text/style/BulletSpan.java
//
//  Created by decoteaud on 9/15/15.
//

#ifndef _RAREBulletSpan_H_
#define _RAREBulletSpan_H_

#import "JreEmulation.h"
#include "android/text/style/LeadingMarginSpan.h"

#define RAREBulletSpan_BULLET_RADIUS 3
#define RAREBulletSpan_STANDARD_GAP_WIDTH 2

@interface RAREBulletSpan : NSObject < RARELeadingMarginSpan > {
 @public
  int mGapWidth_;
}

+ (int)STANDARD_GAP_WIDTH;
- (id)init;
- (id)initWithInt:(int)gapWidth;
- (id)initWithInt:(int)gapWidth
          withInt:(int)color;
- (int)getSpanTypeId;
- (int)getLeadingMarginWithBoolean:(BOOL)first;
- (void)copyAllFieldsTo:(RAREBulletSpan *)other;
@end

typedef RAREBulletSpan AndroidTextStyleBulletSpan;

#endif // _RAREBulletSpan_H_
