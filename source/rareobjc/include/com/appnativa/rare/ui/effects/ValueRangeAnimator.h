//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/ui/effects/ValueRangeAnimator.java
//
//  Created by decoteaud on 9/15/15.
//

#ifndef _RAREValueRangeAnimator_H_
#define _RAREValueRangeAnimator_H_

@class RARETimer;
@protocol RAREiAnimatorValueListener;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/effects/aPlatformAnimator.h"
#include "java/lang/Runnable.h"

@interface RAREValueRangeAnimator : RAREaPlatformAnimator {
 @public
  float delta_;
  float endValue_;
  BOOL intValue_;
  float startValue_;
  RARETimer *timer_;
}

- (id)initWithFloat:(float)start
          withFloat:(float)end;
- (id)initWithInt:(int)start
          withInt:(int)end;
- (void)addValueListenerWithRAREiAnimatorValueListener:(id<RAREiAnimatorValueListener>)l;
- (void)removeValueListenerWithRAREiAnimatorValueListener:(id<RAREiAnimatorValueListener>)l;
- (void)cancel;
+ (RAREValueRangeAnimator *)ofFloatWithFloat:(float)start
                                   withFloat:(float)end;
+ (RAREValueRangeAnimator *)ofIntWithInt:(int)start
                                 withInt:(int)end;
- (void)start;
- (void)stop;
- (void)setRangeWithFloat:(float)start
                withFloat:(float)end;
- (void)setRangeWithInt:(int)start
                withInt:(int)end;
- (void)timingEventWithRARETimer:(RARETimer *)timer
                       withFloat:(float)fraction;
- (void)copyAllFieldsTo:(RAREValueRangeAnimator *)other;
@end

J2OBJC_FIELD_SETTER(RAREValueRangeAnimator, timer_, RARETimer *)

typedef RAREValueRangeAnimator ComAppnativaRareUiEffectsValueRangeAnimator;

@interface RAREValueRangeAnimator_$1 : NSObject < JavaLangRunnable > {
 @public
  RAREValueRangeAnimator *this$0_;
  long long int val$startTime_;
  RARETimer *val$tt_;
}

- (void)run;
- (id)initWithRAREValueRangeAnimator:(RAREValueRangeAnimator *)outer$
                            withLong:(long long int)capture$0
                       withRARETimer:(RARETimer *)capture$1;
@end

J2OBJC_FIELD_SETTER(RAREValueRangeAnimator_$1, this$0_, RAREValueRangeAnimator *)
J2OBJC_FIELD_SETTER(RAREValueRangeAnimator_$1, val$tt_, RARETimer *)

#endif // _RAREValueRangeAnimator_H_
