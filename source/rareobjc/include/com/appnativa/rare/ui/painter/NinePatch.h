//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/painter/NinePatch.java
//
//  Created by decoteaud on 7/29/15.
//

#ifndef _RARENinePatch_H_
#define _RARENinePatch_H_

@class IOSBooleanArray;
@class IOSIntArray;
@class JavaNetURL;
@class RARENinePatch_Pair;
@class RAREUIColor;
@class RAREUIImage;
@class RAREaUIImage;
@protocol JavaUtilList;
@protocol RAREiPlatformGraphics;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/iImageObserver.h"

@interface RARENinePatch : NSObject < RAREiImageObserver > {
 @public
  RAREUIColor *newColor_;
  RAREUIColor *oldColor_;
  IOSIntArray *column_;
  id<JavaUtilList> mFixed_;
  RARENinePatch_Pair *mHorizontalPadding_;
  id<JavaUtilList> mHorizontalPatches_;
  float mHorizontalPatchesSum_;
  BOOL mHorizontalStartWithPatch_;
  RAREaUIImage *mImage_;
  id<JavaUtilList> mPatches_;
  int mRemainderHorizontal_;
  int mRemainderVertical_;
  RARENinePatch_Pair *mVerticalPadding_;
  id<JavaUtilList> mVerticalPatches_;
  float mVerticalPatchesSum_;
  BOOL mVerticalStartWithPatch_;
  IOSIntArray *row_;
}

+ (NSString *)EXTENSION_9PATCH;
- (id)initWithRAREaUIImage:(RAREaUIImage *)image;
- (id)init;
- (id)initWithRAREaUIImage:(RAREaUIImage *)image
           withRAREUIColor:(RAREUIColor *)newColor
           withRAREUIColor:(RAREUIColor *)oldColor;
- (void)changeNinePatchColorWithRAREUIColor:(RAREUIColor *)newColor
                            withRAREUIColor:(RAREUIColor *)oldColor;
- (void)drawWithRAREiPlatformGraphics:(id<RAREiPlatformGraphics>)g
                            withFloat:(float)x
                            withFloat:(float)y
                            withFloat:(float)scaledWidth
                            withFloat:(float)scaledHeight;
- (void)imageLoadedWithRAREUIImage:(RAREUIImage *)image;
- (BOOL)isLoadedWithRAREiImageObserver:(id<RAREiImageObserver>)is;
+ (RARENinePatch *)load__WithJavaNetURL:(JavaNetURL *)url;
- (int)getHeight;
- (RAREaUIImage *)getImage;
- (void)getPaddingWithIntArray:(IOSIntArray *)padding;
- (int)getWidth;
- (void)computePatchesWithInt:(int)scaledWidth
                      withInt:(int)scaledHeight;
- (id<JavaUtilList>)getPatches;
- (void)findPatches;
- (id<JavaUtilList>)getHorizontalRectanglesWithJavaUtilList:(id<JavaUtilList>)leftPairs;
- (RARENinePatch_Pair *)getPaddingWithJavaUtilList:(id<JavaUtilList>)pairs;
- (RARENinePatch_Pair *)getPatchesWithIntArray:(IOSIntArray *)pixels
                              withBooleanArray:(IOSBooleanArray *)startWithPatch;
- (id<JavaUtilList>)getRectanglesWithJavaUtilList:(id<JavaUtilList>)leftPairs
                                 withJavaUtilList:(id<JavaUtilList>)topPairs;
- (id<JavaUtilList>)getVerticalRectanglesWithJavaUtilList:(id<JavaUtilList>)topPairs;
- (void)copyAllFieldsTo:(RARENinePatch *)other;
@end

J2OBJC_FIELD_SETTER(RARENinePatch, newColor_, RAREUIColor *)
J2OBJC_FIELD_SETTER(RARENinePatch, oldColor_, RAREUIColor *)
J2OBJC_FIELD_SETTER(RARENinePatch, column_, IOSIntArray *)
J2OBJC_FIELD_SETTER(RARENinePatch, mFixed_, id<JavaUtilList>)
J2OBJC_FIELD_SETTER(RARENinePatch, mHorizontalPadding_, RARENinePatch_Pair *)
J2OBJC_FIELD_SETTER(RARENinePatch, mHorizontalPatches_, id<JavaUtilList>)
J2OBJC_FIELD_SETTER(RARENinePatch, mImage_, RAREaUIImage *)
J2OBJC_FIELD_SETTER(RARENinePatch, mPatches_, id<JavaUtilList>)
J2OBJC_FIELD_SETTER(RARENinePatch, mVerticalPadding_, RARENinePatch_Pair *)
J2OBJC_FIELD_SETTER(RARENinePatch, mVerticalPatches_, id<JavaUtilList>)
J2OBJC_FIELD_SETTER(RARENinePatch, row_, IOSIntArray *)

typedef RARENinePatch ComAppnativaRareUiPainterNinePatch;

@interface RARENinePatch_Pair : NSObject {
 @public
  id mFirst_;
  id mSecond_;
}

- (id)initWithId:(id)first
          withId:(id)second;
- (NSString *)description;
- (void)copyAllFieldsTo:(RARENinePatch_Pair *)other;
@end

J2OBJC_FIELD_SETTER(RARENinePatch_Pair, mFirst_, id)
J2OBJC_FIELD_SETTER(RARENinePatch_Pair, mSecond_, id)

#endif // _RARENinePatch_H_
