//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/AnimationComponent.java
//
//  Created by decoteaud on 7/29/15.
//

#ifndef _RAREAnimationComponent_H_
#define _RAREAnimationComponent_H_

@class RAREUIDimension;
@class RAREUIImage;
@protocol RAREiAnimationImageView;
@protocol RAREiPlatformComponent;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/XPContainer.h"

@interface RAREAnimationComponent : RAREXPContainer {
 @public
  RAREUIDimension *size_;
  BOOL horizontal_;
  id<RAREiAnimationImageView> imageView_;
  BOOL singleAnimator_;
  BOOL stacked_;
  BOOL forward_;
  id<RAREiPlatformComponent> inComponent_;
  RAREUIDimension *minSize_;
  id<RAREiPlatformComponent> outComponent_;
  BOOL useBoundsForPreferredSize_;
  BOOL useIncommingPreferredSize_;
}

- (id)initWithRAREiAnimationImageView:(id<RAREiAnimationImageView>)view;
- (void)layoutContainerWithFloat:(float)width
                       withFloat:(float)height;
- (void)removeAll;
- (void)setComponentWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)inComponent
                                     withFloat:(float)width
                                     withFloat:(float)height;
- (void)setImageWithRAREUIImage:(RAREUIImage *)image;
- (void)setMinimumSizeWithRAREUIDimension:(RAREUIDimension *)minimumSize;
- (void)setSideBySideComponentsWithBoolean:(BOOL)forward
                               withBoolean:(BOOL)horizontal
                withRAREiPlatformComponent:(id<RAREiPlatformComponent>)outComponent
                withRAREiPlatformComponent:(id<RAREiPlatformComponent>)inComponent
                                   withInt:(int)width
                                   withInt:(int)height
                               withBoolean:(BOOL)singleAnimator;
- (void)setStackedComponentsWithBoolean:(BOOL)forward
             withRAREiPlatformComponent:(id<RAREiPlatformComponent>)outComponent
             withRAREiPlatformComponent:(id<RAREiPlatformComponent>)inComponent
                                withInt:(int)width
                                withInt:(int)height;
- (void)setUseBoundsForPreferredSizeWithBoolean:(BOOL)useBoundsForPreferredSize;
- (RAREUIImage *)getImage;
- (id<RAREiPlatformComponent>)getInComponentAnClear;
- (BOOL)isUseBoundsForPreferredSize;
- (void)setInitialLayoutWithFloat:(float)width
                        withFloat:(float)height;
- (void)getMinimumSizeExWithRAREUIDimension:(RAREUIDimension *)size;
- (void)getPreferredSizeExWithRAREUIDimension:(RAREUIDimension *)size
                                    withFloat:(float)maxWidth;
- (BOOL)isUseIncommingPreferredSize;
- (void)setUseIncommingPreferredSizeWithBoolean:(BOOL)useIncommingPreferredSize;
- (void)copyAllFieldsTo:(RAREAnimationComponent *)other;
@end

J2OBJC_FIELD_SETTER(RAREAnimationComponent, size_, RAREUIDimension *)
J2OBJC_FIELD_SETTER(RAREAnimationComponent, imageView_, id<RAREiAnimationImageView>)
J2OBJC_FIELD_SETTER(RAREAnimationComponent, inComponent_, id<RAREiPlatformComponent>)
J2OBJC_FIELD_SETTER(RAREAnimationComponent, minSize_, RAREUIDimension *)
J2OBJC_FIELD_SETTER(RAREAnimationComponent, outComponent_, id<RAREiPlatformComponent>)

typedef RAREAnimationComponent ComAppnativaRareUiAnimationComponent;

#endif // _RAREAnimationComponent_H_