//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/ui/effects/SlideAnimation.java
//
//  Created by decoteaud on 7/29/15.
//

#ifndef _RARESlideAnimation_H_
#define _RARESlideAnimation_H_

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/effects/aSlideAnimation.h"

@interface RARESlideAnimation : RAREaSlideAnimation {
}

- (id)init;
- (id)initWithBoolean:(BOOL)horizontal;
- (id)initWithBoolean:(BOOL)horizontal
          withBoolean:(BOOL)fromLeftOrTop;
- (void)startExWithId:(id)viewProxy;
- (void)startExWithId:(id)viewProxy
               withId:(id)viewProxy2;
@end

typedef RARESlideAnimation ComAppnativaRareUiEffectsSlideAnimation;

#endif // _RARESlideAnimation_H_
