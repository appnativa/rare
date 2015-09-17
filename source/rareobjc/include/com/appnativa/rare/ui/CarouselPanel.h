//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple-carousel/com/appnativa/rare/ui/CarouselPanel.java
//
//  Created by decoteaud on 9/15/15.
//

#ifndef _RARECarouselPanel_H_
#define _RARECarouselPanel_H_

@class RARECarouselPanel_ReflectionViewGroup;
@class RAREGraphicsComposite;
@class RAREImagePanel;
@class RARELabelView;
@class RARERenderableDataItem;
@class RAREUIDimension;
@class RAREUIImage;
@class RAREUILabelRenderer;
@class RAREaCarouselPanel_DataTypeEnum;
@protocol JavaLangCharSequence;
@protocol RAREiAnimator;
@protocol RAREiPlatformComponent;

#import "JreEmulation.h"
#include "com/appnativa/rare/platform/apple/ui/iAppleLayoutManager.h"
#include "com/appnativa/rare/platform/apple/ui/view/ParentView.h"
#include "com/appnativa/rare/platform/apple/ui/view/View.h"
#include "com/appnativa/rare/ui/carousel/aCarouselPanel.h"

@interface RARECarouselPanel : RAREaCarouselPanel {
}

- (id)initWithRAREaCarouselPanel_DataTypeEnum:(RAREaCarouselPanel_DataTypeEnum *)dataType;
- (void)valueChangedWithRAREiAnimator:(id<RAREiAnimator>)animator
                           withDouble:(double)value;
- (void)clearComponentContentWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c;
- (BOOL)isAnimating;
- (id)createPerspectiveTransformWithFloat:(float)x
                                withFloat:(float)y
                                withFloat:(float)width
                                withFloat:(float)height
                                withFloat:(float)ulx
                                withFloat:(float)uly
                                withFloat:(float)urx
                                withFloat:(float)ury
                                withFloat:(float)lrx
                                withFloat:(float)lry
                                withFloat:(float)llx
                                withFloat:(float)lly;
- (void)bringToFrontOrClipWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c
                                           withFloat:(float)clipWidth
                                         withBoolean:(BOOL)leftSide;
- (void)clearPerspectiveFilterWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c;
- (void)setCompositeWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)component
                     withRAREGraphicsComposite:(RAREGraphicsComposite *)composite;
- (RAREUIImage *)createImageFromComponentWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c;
- (id<RAREiPlatformComponent>)createLayoutComponent;
- (void)renderComponentContentWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)dst
                              withRARERenderableDataItem:(RARERenderableDataItem *)src;
- (void)updateComponentContentWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)dst
                              withRAREiPlatformComponent:(id<RAREiPlatformComponent>)src
                                withJavaLangCharSequence:(id<JavaLangCharSequence>)title;
- (void)updateComponentContentWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)dst
                                         withRAREUIImage:(RAREUIImage *)src
                                withJavaLangCharSequence:(id<JavaLangCharSequence>)title;
- (void)setPerspectiveFilterWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c
                                             withFloat:(float)width
                                             withFloat:(float)height
                                           withBoolean:(BOOL)left
                                               withInt:(int)pos;
@end

typedef RARECarouselPanel ComAppnativaRareUiCarouselPanel;

@interface RARECarouselPanel_CarouselView : RAREParentView < RAREiAppleLayoutManager > {
 @public
  __weak RARECarouselPanel *this$0_;
}

- (id)initWithRARECarouselPanel:(RARECarouselPanel *)outer$;
- (void)layoutWithRAREParentView:(RAREParentView *)view
                       withFloat:(float)width
                       withFloat:(float)height;
@end

@interface RARECarouselPanel_ContentView : RAREParentView < RAREiAppleLayoutManager > {
 @public
  RAREView *contentView_;
  RAREImagePanel *imageView_;
  RARECarouselPanel_ReflectionViewGroup *reflectionView_;
  RAREUILabelRenderer *renderer_;
  RARELabelView *titleLabel_;
}

- (id)initWithFloat:(float)rfraction
          withFloat:(float)ropacity;
- (id)initWithRAREUILabelRenderer:(RAREUILabelRenderer *)renderer
                        withFloat:(float)rfraction
                        withFloat:(float)ropacity;
- (id)initWithBoolean:(BOOL)preserveAspectRatio
          withBoolean:(BOOL)autoScale
            withFloat:(float)rfraction
            withFloat:(float)ropacity;
- (void)clearContent;
- (void)layoutWithRAREParentView:(RAREParentView *)view
                       withFloat:(float)width
                       withFloat:(float)height;
- (void)setContentViewWithRAREView:(RAREView *)view
          withJavaLangCharSequence:(id<JavaLangCharSequence>)title;
- (void)setImageWithRAREUIImage:(RAREUIImage *)image
       withJavaLangCharSequence:(id<JavaLangCharSequence>)title;
- (void)setTitleWithJavaLangCharSequence:(id<JavaLangCharSequence>)title;
- (void)addChildWithRAREView:(RAREView *)view;
- (void)removeViewWithRAREView:(RAREView *)view;
- (void)disposeEx;
- (void)createTitleLabel;
- (void)copyAllFieldsTo:(RARECarouselPanel_ContentView *)other;
@end

J2OBJC_FIELD_SETTER(RARECarouselPanel_ContentView, contentView_, RAREView *)
J2OBJC_FIELD_SETTER(RARECarouselPanel_ContentView, imageView_, RAREImagePanel *)
J2OBJC_FIELD_SETTER(RARECarouselPanel_ContentView, reflectionView_, RARECarouselPanel_ReflectionViewGroup *)
J2OBJC_FIELD_SETTER(RARECarouselPanel_ContentView, renderer_, RAREUILabelRenderer *)
J2OBJC_FIELD_SETTER(RARECarouselPanel_ContentView, titleLabel_, RARELabelView *)

@interface RARECarouselPanel_ReflectionView : RAREView {
 @public
  RAREUIImage *reflection_;
  float rfraction_;
  float ropacity_;
  RAREUIImage *source_;
  __weak RAREView *sourceView_;
}

- (id)initWithFloat:(float)rfraction
          withFloat:(float)ropacity;
- (void)setSourceViewWithRAREView:(RAREView *)sourceView;
- (void)getPreferredSizeWithRAREUIDimension:(RAREUIDimension *)size
                                  withFloat:(float)maxWidth;
- (RAREView *)getSourceView;
- (void)disposeEx;
- (void)copyAllFieldsTo:(RARECarouselPanel_ReflectionView *)other;
@end

J2OBJC_FIELD_SETTER(RARECarouselPanel_ReflectionView, reflection_, RAREUIImage *)
J2OBJC_FIELD_SETTER(RARECarouselPanel_ReflectionView, source_, RAREUIImage *)

@interface RARECarouselPanel_ReflectionViewGroup : RAREParentView < RAREiAppleLayoutManager > {
 @public
  RARECarouselPanel_ReflectionView *reflection_;
}

- (id)initWithFloat:(float)rfraction
          withFloat:(float)ropacity;
- (void)layoutWithRAREParentView:(RAREParentView *)view
                       withFloat:(float)width
                       withFloat:(float)height;
- (void)setSourceViewWithRAREView:(RAREView *)view;
- (void)disposeEx;
- (void)copyAllFieldsTo:(RARECarouselPanel_ReflectionViewGroup *)other;
@end

J2OBJC_FIELD_SETTER(RARECarouselPanel_ReflectionViewGroup, reflection_, RARECarouselPanel_ReflectionView *)

#endif // _RARECarouselPanel_H_
