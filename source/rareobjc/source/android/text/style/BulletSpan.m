//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple-android-htmllabel/android/text/style/BulletSpan.java
//
//  Created by decoteaud on 5/11/15.
//

#include "android/text/TextUtils.h"
#include "android/text/style/BulletSpan.h"

@implementation RAREBulletSpan

+ (int)STANDARD_GAP_WIDTH {
  return RAREBulletSpan_STANDARD_GAP_WIDTH;
}

- (id)init {
  if (self = [super init]) {
    mGapWidth_ = RAREBulletSpan_STANDARD_GAP_WIDTH;
  }
  return self;
}

- (id)initWithInt:(int)gapWidth {
  if (self = [super init]) {
    mGapWidth_ = gapWidth;
  }
  return self;
}

- (id)initWithInt:(int)gapWidth
          withInt:(int)color {
  if (self = [super init]) {
    mGapWidth_ = gapWidth;
  }
  return self;
}

- (int)getSpanTypeId {
  return AndroidTextTextUtils_BULLET_SPAN;
}

- (int)getLeadingMarginWithBoolean:(BOOL)first {
  return 2 * RAREBulletSpan_BULLET_RADIUS + mGapWidth_;
}

- (void)copyAllFieldsTo:(RAREBulletSpan *)other {
  [super copyAllFieldsTo:other];
  other->mGapWidth_ = mGapWidth_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcFieldInfo fields[] = {
    { "mGapWidth_", NULL, 0x12, "I" },
    { "BULLET_RADIUS_", NULL, 0x1a, "I" },
    { "STANDARD_GAP_WIDTH_", NULL, 0x19, "I" },
  };
  static J2ObjcClassInfo _RAREBulletSpan = { "BulletSpan", "android.text.style", NULL, 0x1, 0, NULL, 3, fields, 0, NULL};
  return &_RAREBulletSpan;
}

@end
