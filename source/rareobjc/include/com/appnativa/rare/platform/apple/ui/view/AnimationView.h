//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/platform/apple/ui/view/AnimationView.java
//
//  Created by decoteaud on 9/15/15.
//

#ifndef _RAREAnimationView_H_
#define _RAREAnimationView_H_

@class RAREComponent;
@class RAREImageView;
@class RAREUIImage;

#import "JreEmulation.h"
#include "com/appnativa/rare/platform/apple/ui/view/ParentView.h"
#include "com/appnativa/rare/ui/effects/iAnimationImageView.h"

@interface RAREAnimationView : RAREParentView < RAREiAnimationImageView > {
 @public
  RAREImageView *imageView_;
  RAREComponent *imageViewComponent_;
}

- (id)init;
- (void)disposeEx;
- (void)setImageWithRAREUIImage:(RAREUIImage *)image;
- (RAREUIImage *)getImage;
- (void)copyAllFieldsTo:(RAREAnimationView *)other;
@end

J2OBJC_FIELD_SETTER(RAREAnimationView, imageView_, RAREImageView *)
J2OBJC_FIELD_SETTER(RAREAnimationView, imageViewComponent_, RAREComponent *)

typedef RAREAnimationView ComAppnativaRarePlatformAppleUiViewAnimationView;

#endif // _RAREAnimationView_H_
