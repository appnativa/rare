//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/painter/NinePatch.java
//
//  Created by decoteaud on 12/8/15.
//

#include "IOSBooleanArray.h"
#include "IOSClass.h"
#include "IOSIntArray.h"
#include "com/appnativa/rare/ui/UIColor.h"
#include "com/appnativa/rare/ui/UIImage.h"
#include "com/appnativa/rare/ui/UIImageHelper.h"
#include "com/appnativa/rare/ui/UIRectangle.h"
#include "com/appnativa/rare/ui/aUIImage.h"
#include "com/appnativa/rare/ui/iImageObserver.h"
#include "com/appnativa/rare/ui/iPlatformGraphics.h"
#include "com/appnativa/rare/ui/painter/NinePatch.h"
#include "java/io/IOException.h"
#include "java/lang/Integer.h"
#include "java/lang/Math.h"
#include "java/net/URL.h"
#include "java/util/ArrayList.h"
#include "java/util/List.h"

@implementation RARENinePatch

static NSString * RARENinePatch_EXTENSION_9PATCH_ = @".9.png";

+ (NSString *)EXTENSION_9PATCH {
  return RARENinePatch_EXTENSION_9PATCH_;
}

- (id)initWithRAREaUIImage:(RAREaUIImage *)image {
  return [self initRARENinePatchWithRAREaUIImage:image withRAREUIColor:nil withRAREUIColor:nil];
}

- (id)init {
  return [super init];
}

- (id)initRARENinePatchWithRAREaUIImage:(RAREaUIImage *)image
                        withRAREUIColor:(RAREUIColor *)newColor
                        withRAREUIColor:(RAREUIColor *)oldColor {
  if (self = [super init]) {
    mImage_ = image;
    if ([((RAREaUIImage *) nil_chk(image)) isLoadedWithRAREiImageObserver:self]) {
      if (newColor != nil) {
        [self changeNinePatchColorWithRAREUIColor:newColor withRAREUIColor:oldColor];
      }
      [self findPatches];
    }
    else {
      self->newColor_ = newColor;
      self->oldColor_ = oldColor;
    }
  }
  return self;
}

- (id)initWithRAREaUIImage:(RAREaUIImage *)image
           withRAREUIColor:(RAREUIColor *)newColor
           withRAREUIColor:(RAREUIColor *)oldColor {
  return [self initRARENinePatchWithRAREaUIImage:image withRAREUIColor:newColor withRAREUIColor:oldColor];
}

- (void)changeNinePatchColorWithRAREUIColor:(RAREUIColor *)newColor
                            withRAREUIColor:(RAREUIColor *)oldColor {
  if ((newColor == nil) || (oldColor == nil)) {
    return;
  }
  if ((mImage_ == nil) || ![mImage_ isLoadedWithRAREiImageObserver:self]) {
    self->newColor_ = newColor;
    self->oldColor_ = oldColor;
    return;
  }
  int w = [((RAREaUIImage *) nil_chk(mImage_)) getWidth];
  int h = [mImage_ getHeight];
  int nc = [((RAREUIColor *) nil_chk(newColor)) getRGB];
  int nr = (nc >> 16) & (int) 0xff;
  int ng = (nc >> 8) & (int) 0xff;
  int nb = nc & (int) 0xff;
  int oc = [((RAREUIColor *) nil_chk(oldColor)) getRGB];
  int or_ = (oc >> 16) & (int) 0xff;
  int og = (oc >> 8) & (int) 0xff;
  int ob = oc & (int) 0xff;
  for (int y = 1; y < h; ++y) {
    for (int x = 1; x < w; ++x) {
      int c = [mImage_ getPixelWithInt:x withInt:y];
      int a = (c >> 24) & (int) 0xff;
      int r = (c >> 16) & (int) 0xff;
      int g = (c >> 8) & (int) 0xff;
      int b = c & (int) 0xff;
      if ((a == 0) || ([JavaLangMath absWithInt:r - or_] > 5) || ([JavaLangMath absWithInt:g - og] > 5) || ([JavaLangMath absWithInt:b - ob] > 5)) {
        continue;
      }
      [mImage_ setPixelWithInt:x withInt:y withInt:(a << 24) | (nr << 16) | (ng << 8) | nb];
    }
  }
  self->newColor_ = nil;
  self->oldColor_ = nil;
}

- (void)drawWithRAREiPlatformGraphics:(id<RAREiPlatformGraphics>)g
                            withFloat:(float)x
                            withFloat:(float)y
                            withFloat:(float)scaledWidth
                            withFloat:(float)scaledHeight {
  if ((mImage_ == nil) || (scaledWidth <= 1) || (scaledHeight <= 1) || (mPatches_ == nil)) {
    return;
  }
  if ([((id<JavaUtilList>) nil_chk(mPatches_)) size] == 0) {
    [((id<RAREiPlatformGraphics>) nil_chk(g)) drawImageWithRAREiPlatformImage:mImage_ withFloat:x withFloat:y withFloat:scaledWidth withFloat:scaledHeight];
    return;
  }
  [((id<RAREiPlatformGraphics>) nil_chk(g)) translateWithFloat:x withFloat:y];
  x = y = 0;
  [self computePatchesWithInt:(int) [JavaLangMath ceilWithDouble:scaledWidth] withInt:(int) [JavaLangMath ceilWithDouble:scaledHeight]];
  int fixedIndex = 0;
  int horizontalIndex = 0;
  int verticalIndex = 0;
  int patchIndex = 0;
  BOOL hStretch;
  BOOL vStretch;
  float vWeightSum = 1.0f;
  float vRemainder = mRemainderVertical_;
  vStretch = mVerticalStartWithPatch_;
  RAREUIRectangle *src = [[RAREUIRectangle alloc] init];
  RAREUIRectangle *dst = [[RAREUIRectangle alloc] init];
  while (y < scaledHeight - 1) {
    hStretch = mHorizontalStartWithPatch_;
    int height = 0;
    float vExtra = 0.0f;
    float hWeightSum = 1.0f;
    float hRemainder = mRemainderHorizontal_;
    while (x < scaledWidth - 1) {
      RAREUIRectangle *r;
      if (!vStretch) {
        if (hStretch) {
          r = [((id<JavaUtilList>) nil_chk(mHorizontalPatches_)) getWithInt:horizontalIndex++];
          float extra = ((RAREUIRectangle *) nil_chk(r))->width_ / mHorizontalPatchesSum_;
          int width = (int) (extra * hRemainder / hWeightSum);
          hWeightSum -= extra;
          hRemainder -= width;
          [src setBoundsWithRAREaUIRectangle:r];
          [dst setWithFloat:x withFloat:y withFloat:width withFloat:r->height_];
          [g drawImageWithRAREiPlatformImage:mImage_ withRAREUIRectangle:src withRAREUIRectangle:dst withRAREiImagePainter_ScalingTypeEnum:nil withRAREiComposite:nil];
          x += width;
        }
        else {
          r = [((id<JavaUtilList>) nil_chk(mFixed_)) getWithInt:fixedIndex++];
          [src setBoundsWithRAREaUIRectangle:r];
          [dst setWithFloat:x withFloat:y withFloat:((RAREUIRectangle *) nil_chk(r))->width_ withFloat:r->height_];
          [g drawImageWithRAREiPlatformImage:mImage_ withRAREUIRectangle:src withRAREUIRectangle:dst withRAREiImagePainter_ScalingTypeEnum:nil withRAREiComposite:nil];
          x += r->width_;
        }
        height = (int) ((RAREUIRectangle *) nil_chk(r))->height_;
      }
      else {
        if (hStretch) {
          r = [mPatches_ getWithInt:patchIndex++];
          vExtra = ((RAREUIRectangle *) nil_chk(r))->height_ / mVerticalPatchesSum_;
          height = (int) (vExtra * vRemainder / vWeightSum);
          float extra = r->width_ / mHorizontalPatchesSum_;
          int width = (int) (extra * hRemainder / hWeightSum);
          hWeightSum -= extra;
          hRemainder -= width;
          [src setBoundsWithRAREaUIRectangle:r];
          [dst setWithFloat:x withFloat:y withFloat:width withFloat:height];
          [g drawImageWithRAREiPlatformImage:mImage_ withRAREUIRectangle:src withRAREUIRectangle:dst withRAREiImagePainter_ScalingTypeEnum:nil withRAREiComposite:nil];
          x += width;
        }
        else {
          r = [((id<JavaUtilList>) nil_chk(mVerticalPatches_)) getWithInt:verticalIndex++];
          vExtra = ((RAREUIRectangle *) nil_chk(r))->height_ / mVerticalPatchesSum_;
          height = (int) (vExtra * vRemainder / vWeightSum);
          [src setBoundsWithRAREaUIRectangle:r];
          [dst setWithFloat:x withFloat:y withFloat:r->width_ withFloat:height];
          [g drawImageWithRAREiPlatformImage:mImage_ withRAREUIRectangle:src withRAREUIRectangle:dst withRAREiImagePainter_ScalingTypeEnum:nil withRAREiComposite:nil];
          x += r->width_;
        }
      }
      hStretch = !hStretch;
    }
    x = 0;
    y += height;
    if (vStretch) {
      vWeightSum -= vExtra;
      vRemainder -= height;
    }
    vStretch = !vStretch;
  }
}

- (void)imageLoadedWithRAREUIImage:(RAREUIImage *)image {
  if (newColor_ != nil) {
    [self changeNinePatchColorWithRAREUIColor:newColor_ withRAREUIColor:oldColor_];
  }
  [self findPatches];
}

- (BOOL)isLoadedWithRAREiImageObserver:(id<RAREiImageObserver>)is {
  return [((RAREaUIImage *) nil_chk(mImage_)) isLoadedWithRAREiImageObserver:is];
}

+ (RARENinePatch *)load__WithJavaNetURL:(JavaNetURL *)url {
  return [[RARENinePatch alloc] initWithRAREaUIImage:[RAREUIImageHelper createImageWithJavaNetURL:url withBoolean:YES withFloat:1]];
}

- (int)getHeight {
  return [((RAREaUIImage *) nil_chk(mImage_)) getHeight] - 2;
}

- (RAREaUIImage *)getImage {
  return mImage_;
}

- (void)getPaddingWithIntArray:(IOSIntArray *)padding {
  (*IOSIntArray_GetRef(nil_chk(padding), 0)) = [((JavaLangInteger *) nil_chk(((RARENinePatch_Pair *) nil_chk(mHorizontalPadding_))->mFirst_)) intValue];
  (*IOSIntArray_GetRef(padding, 2)) = [((JavaLangInteger *) nil_chk(mHorizontalPadding_->mSecond_)) intValue];
  (*IOSIntArray_GetRef(padding, 1)) = [((RARENinePatch_Pair *) nil_chk(mVerticalPadding_))->mFirst_ intValue];
  (*IOSIntArray_GetRef(padding, 3)) = [mVerticalPadding_->mSecond_ intValue];
}

- (int)getWidth {
  return [((RAREaUIImage *) nil_chk(mImage_)) getWidth] - 2;
}

- (void)computePatchesWithInt:(int)scaledWidth
                      withInt:(int)scaledHeight {
  BOOL measuredWidth = NO;
  BOOL endRow = YES;
  int remainderHorizontal = 0;
  int remainderVertical = 0;
  if ([((id<JavaUtilList>) nil_chk(mFixed_)) size] > 0) {
    int start = (int) ((RAREUIRectangle *) nil_chk([mFixed_ getWithInt:0]))->y_;
    for (RAREUIRectangle * __strong rect in mFixed_) {
      if (((RAREUIRectangle *) nil_chk(rect))->y_ > start) {
        endRow = YES;
        measuredWidth = YES;
      }
      if (!measuredWidth) {
        remainderHorizontal += rect->width_;
      }
      if (endRow) {
        remainderVertical += rect->height_;
        endRow = NO;
        start = (int) rect->y_;
      }
    }
  }
  mRemainderHorizontal_ = scaledWidth - remainderHorizontal;
  mRemainderVertical_ = scaledHeight - remainderVertical;
  mHorizontalPatchesSum_ = 0;
  if ([((id<JavaUtilList>) nil_chk(mHorizontalPatches_)) size] > 0) {
    int start = -1;
    for (RAREUIRectangle * __strong rect in mHorizontalPatches_) {
      if (((RAREUIRectangle *) nil_chk(rect))->x_ > start) {
        mHorizontalPatchesSum_ += rect->width_;
        start = (int) rect->x_;
      }
    }
  }
  else {
    int start = -1;
    for (RAREUIRectangle * __strong rect in nil_chk(mPatches_)) {
      if (((RAREUIRectangle *) nil_chk(rect))->x_ > start) {
        mHorizontalPatchesSum_ += rect->width_;
        start = (int) rect->x_;
      }
    }
  }
  mVerticalPatchesSum_ = 0;
  if ([((id<JavaUtilList>) nil_chk(mVerticalPatches_)) size] > 0) {
    int start = -1;
    for (RAREUIRectangle * __strong rect in mVerticalPatches_) {
      if (((RAREUIRectangle *) nil_chk(rect))->y_ > start) {
        mVerticalPatchesSum_ += rect->height_;
        start = (int) rect->y_;
      }
    }
  }
  else {
    int start = -1;
    for (RAREUIRectangle * __strong rect in nil_chk(mPatches_)) {
      if (((RAREUIRectangle *) nil_chk(rect))->y_ > start) {
        mVerticalPatchesSum_ += rect->height_;
        start = (int) rect->y_;
      }
    }
  }
}

- (id<JavaUtilList>)getPatches {
  return mFixed_;
}

- (void)findPatches {
  int width = [((RAREaUIImage *) nil_chk(mImage_)) getWidth];
  int height = [mImage_ getHeight];
  row_ = [mImage_ getPixelsWithIntArray:row_ withInt:0 withInt:0 withInt:width withInt:1];
  column_ = [mImage_ getPixelsWithIntArray:column_ withInt:0 withInt:0 withInt:1 withInt:height];
  IOSBooleanArray *result = [IOSBooleanArray arrayWithLength:1];
  RARENinePatch_Pair *left = [self getPatchesWithIntArray:column_ withBooleanArray:result];
  mVerticalStartWithPatch_ = IOSBooleanArray_Get(result, 0);
  result = [IOSBooleanArray arrayWithLength:1];
  RARENinePatch_Pair *top = [self getPatchesWithIntArray:row_ withBooleanArray:result];
  mHorizontalStartWithPatch_ = IOSBooleanArray_Get(result, 0);
  mFixed_ = [self getRectanglesWithJavaUtilList:((RARENinePatch_Pair *) nil_chk(left))->mFirst_ withJavaUtilList:((RARENinePatch_Pair *) nil_chk(top))->mFirst_];
  mPatches_ = [self getRectanglesWithJavaUtilList:left->mSecond_ withJavaUtilList:top->mSecond_];
  if ([((id<JavaUtilList>) nil_chk(mFixed_)) size] > 0) {
    mHorizontalPatches_ = [self getRectanglesWithJavaUtilList:left->mFirst_ withJavaUtilList:top->mSecond_];
    mVerticalPatches_ = [self getRectanglesWithJavaUtilList:left->mSecond_ withJavaUtilList:top->mFirst_];
  }
  else {
    if ([((id<JavaUtilList>) check_protocol_cast(top->mFirst_, @protocol(JavaUtilList))) size] > 0) {
      mHorizontalPatches_ = [[JavaUtilArrayList alloc] initWithInt:0];
      mVerticalPatches_ = [self getVerticalRectanglesWithJavaUtilList:top->mFirst_];
    }
    else if ([((id<JavaUtilList>) check_protocol_cast(left->mFirst_, @protocol(JavaUtilList))) size] > 0) {
      mHorizontalPatches_ = [self getHorizontalRectanglesWithJavaUtilList:left->mFirst_];
      mVerticalPatches_ = [[JavaUtilArrayList alloc] initWithInt:0];
    }
    else {
      mHorizontalPatches_ = mVerticalPatches_ = [[JavaUtilArrayList alloc] initWithInt:0];
    }
  }
  row_ = [mImage_ getPixelsWithIntArray:row_ withInt:0 withInt:height - 1 withInt:width withInt:1];
  column_ = [mImage_ getPixelsWithIntArray:column_ withInt:width - 1 withInt:0 withInt:1 withInt:height];
  top = [self getPatchesWithIntArray:row_ withBooleanArray:result];
  mHorizontalPadding_ = [self getPaddingWithJavaUtilList:((RARENinePatch_Pair *) nil_chk(top))->mFirst_];
  left = [self getPatchesWithIntArray:column_ withBooleanArray:result];
  mVerticalPadding_ = [self getPaddingWithJavaUtilList:((RARENinePatch_Pair *) nil_chk(left))->mFirst_];
}

- (id<JavaUtilList>)getHorizontalRectanglesWithJavaUtilList:(id<JavaUtilList>)leftPairs {
  id<JavaUtilList> rectangles = [[JavaUtilArrayList alloc] init];
  for (RARENinePatch_Pair * __strong left in nil_chk(leftPairs)) {
    int y = [((JavaLangInteger *) nil_chk(((RARENinePatch_Pair *) nil_chk(left))->mFirst_)) intValue];
    int height = [((JavaLangInteger *) nil_chk(left->mSecond_)) intValue] - [left->mFirst_ intValue];
    [rectangles addWithId:[[RAREUIRectangle alloc] initWithFloat:1 withFloat:y withFloat:[((RAREaUIImage *) nil_chk(mImage_)) getWidth] - 2 withFloat:height]];
  }
  return rectangles;
}

- (RARENinePatch_Pair *)getPaddingWithJavaUtilList:(id<JavaUtilList>)pairs {
  if ([((id<JavaUtilList>) nil_chk(pairs)) size] == 0) {
    return [[RARENinePatch_Pair alloc] initWithId:[JavaLangInteger valueOfWithInt:0] withId:[JavaLangInteger valueOfWithInt:0]];
  }
  else if ([pairs size] == 1) {
    if ([((JavaLangInteger *) nil_chk(((RARENinePatch_Pair *) nil_chk([pairs getWithInt:0]))->mFirst_)) intValue] == 1) {
      return [[RARENinePatch_Pair alloc] initWithId:[JavaLangInteger valueOfWithInt:[((JavaLangInteger *) nil_chk(((RARENinePatch_Pair *) nil_chk([pairs getWithInt:0]))->mSecond_)) intValue] - [((RARENinePatch_Pair *) nil_chk([pairs getWithInt:0]))->mFirst_ intValue]] withId:[JavaLangInteger valueOfWithInt:0]];
    }
    else {
      return [[RARENinePatch_Pair alloc] initWithId:[JavaLangInteger valueOfWithInt:0] withId:[JavaLangInteger valueOfWithInt:[((JavaLangInteger *) nil_chk(((RARENinePatch_Pair *) nil_chk([pairs getWithInt:0]))->mSecond_)) intValue] - [((RARENinePatch_Pair *) nil_chk([pairs getWithInt:0]))->mFirst_ intValue]]];
    }
  }
  else {
    int index = [pairs size] - 1;
    return [[RARENinePatch_Pair alloc] initWithId:[JavaLangInteger valueOfWithInt:[((JavaLangInteger *) nil_chk(((RARENinePatch_Pair *) nil_chk([pairs getWithInt:0]))->mSecond_)) intValue] - [((JavaLangInteger *) nil_chk(((RARENinePatch_Pair *) nil_chk([pairs getWithInt:0]))->mFirst_)) intValue]] withId:[JavaLangInteger valueOfWithInt:[((RARENinePatch_Pair *) nil_chk([pairs getWithInt:index]))->mSecond_ intValue] - [((RARENinePatch_Pair *) nil_chk([pairs getWithInt:index]))->mFirst_ intValue]]];
  }
}

- (RARENinePatch_Pair *)getPatchesWithIntArray:(IOSIntArray *)pixels
                              withBooleanArray:(IOSBooleanArray *)startWithPatch {
  int lastIndex = 1;
  int lastPixel = IOSIntArray_Get(nil_chk(pixels), 1);
  BOOL first = YES;
  id<JavaUtilList> fixed = [[JavaUtilArrayList alloc] init];
  id<JavaUtilList> patches = [[JavaUtilArrayList alloc] init];
  for (int i = 1; i < (int) [pixels count] - 1; i++) {
    int pixel = IOSIntArray_Get(pixels, i);
    if (pixel != lastPixel) {
      if (lastPixel == (int) 0xFF000000) {
        if (first) {
          (*IOSBooleanArray_GetRef(nil_chk(startWithPatch), 0)) = YES;
        }
        [patches addWithId:[[RARENinePatch_Pair alloc] initWithId:[JavaLangInteger valueOfWithInt:lastIndex] withId:[JavaLangInteger valueOfWithInt:i]]];
      }
      else {
        [fixed addWithId:[[RARENinePatch_Pair alloc] initWithId:[JavaLangInteger valueOfWithInt:lastIndex] withId:[JavaLangInteger valueOfWithInt:i]]];
      }
      first = NO;
      lastIndex = i;
      lastPixel = pixel;
    }
  }
  if (lastPixel == (int) 0xFF000000) {
    if (first) {
      (*IOSBooleanArray_GetRef(nil_chk(startWithPatch), 0)) = YES;
    }
    [patches addWithId:[[RARENinePatch_Pair alloc] initWithId:[JavaLangInteger valueOfWithInt:lastIndex] withId:[JavaLangInteger valueOfWithInt:(int) [pixels count] - 1]]];
  }
  else {
    [fixed addWithId:[[RARENinePatch_Pair alloc] initWithId:[JavaLangInteger valueOfWithInt:lastIndex] withId:[JavaLangInteger valueOfWithInt:(int) [pixels count] - 1]]];
  }
  if ([patches size] == 0) {
    [patches addWithId:[[RARENinePatch_Pair alloc] initWithId:[JavaLangInteger valueOfWithInt:1] withId:[JavaLangInteger valueOfWithInt:(int) [pixels count] - 1]]];
    (*IOSBooleanArray_GetRef(nil_chk(startWithPatch), 0)) = YES;
    [fixed clear];
  }
  return [[RARENinePatch_Pair alloc] initWithId:fixed withId:patches];
}

- (id<JavaUtilList>)getRectanglesWithJavaUtilList:(id<JavaUtilList>)leftPairs
                                 withJavaUtilList:(id<JavaUtilList>)topPairs {
  id<JavaUtilList> rectangles = [[JavaUtilArrayList alloc] init];
  for (RARENinePatch_Pair * __strong left in nil_chk(leftPairs)) {
    int y = [((JavaLangInteger *) nil_chk(((RARENinePatch_Pair *) nil_chk(left))->mFirst_)) intValue];
    int height = [((JavaLangInteger *) nil_chk(left->mSecond_)) intValue] - [left->mFirst_ intValue];
    for (RARENinePatch_Pair * __strong top in nil_chk(topPairs)) {
      int x = [((RARENinePatch_Pair *) nil_chk(top))->mFirst_ intValue];
      int width = [top->mSecond_ intValue] - [top->mFirst_ intValue];
      [rectangles addWithId:[[RAREUIRectangle alloc] initWithFloat:x withFloat:y withFloat:width withFloat:height]];
    }
  }
  return rectangles;
}

- (id<JavaUtilList>)getVerticalRectanglesWithJavaUtilList:(id<JavaUtilList>)topPairs {
  id<JavaUtilList> rectangles = [[JavaUtilArrayList alloc] init];
  for (RARENinePatch_Pair * __strong top in nil_chk(topPairs)) {
    int x = [((JavaLangInteger *) nil_chk(((RARENinePatch_Pair *) nil_chk(top))->mFirst_)) intValue];
    int width = [((JavaLangInteger *) nil_chk(top->mSecond_)) intValue] - [top->mFirst_ intValue];
    [rectangles addWithId:[[RAREUIRectangle alloc] initWithFloat:x withFloat:1 withFloat:width withFloat:[((RAREaUIImage *) nil_chk(mImage_)) getHeight] - 2]];
  }
  return rectangles;
}

- (void)copyAllFieldsTo:(RARENinePatch *)other {
  [super copyAllFieldsTo:other];
  other->column_ = column_;
  other->mFixed_ = mFixed_;
  other->mHorizontalPadding_ = mHorizontalPadding_;
  other->mHorizontalPatches_ = mHorizontalPatches_;
  other->mHorizontalPatchesSum_ = mHorizontalPatchesSum_;
  other->mHorizontalStartWithPatch_ = mHorizontalStartWithPatch_;
  other->mImage_ = mImage_;
  other->mPatches_ = mPatches_;
  other->mRemainderHorizontal_ = mRemainderHorizontal_;
  other->mRemainderVertical_ = mRemainderVertical_;
  other->mVerticalPadding_ = mVerticalPadding_;
  other->mVerticalPatches_ = mVerticalPatches_;
  other->mVerticalPatchesSum_ = mVerticalPatchesSum_;
  other->mVerticalStartWithPatch_ = mVerticalStartWithPatch_;
  other->newColor_ = newColor_;
  other->oldColor_ = oldColor_;
  other->row_ = row_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "init", NULL, NULL, 0x4, NULL },
    { "isLoadedWithRAREiImageObserver:", NULL, "Z", 0x1, NULL },
    { "load__WithJavaNetURL:", NULL, "LRARENinePatch", 0x9, "JavaIoIOException" },
    { "getImage", NULL, "LRAREaUIImage", 0x1, NULL },
    { "computePatchesWithInt:withInt:", NULL, "V", 0x0, NULL },
    { "getPatches", NULL, "LJavaUtilList", 0x1, NULL },
    { "findPatches", NULL, "V", 0x2, NULL },
    { "getHorizontalRectanglesWithJavaUtilList:", NULL, "LJavaUtilList", 0x2, NULL },
    { "getPaddingWithJavaUtilList:", NULL, "LRARENinePatch_Pair", 0x2, NULL },
    { "getPatchesWithIntArray:withBooleanArray:", NULL, "LRARENinePatch_Pair", 0x2, NULL },
    { "getRectanglesWithJavaUtilList:withJavaUtilList:", NULL, "LJavaUtilList", 0x2, NULL },
    { "getVerticalRectanglesWithJavaUtilList:", NULL, "LJavaUtilList", 0x2, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "EXTENSION_9PATCH_", NULL, 0x19, "LNSString" },
    { "newColor_", NULL, 0x0, "LRAREUIColor" },
    { "oldColor_", NULL, 0x0, "LRAREUIColor" },
    { "mImage_", NULL, 0x4, "LRAREaUIImage" },
  };
  static J2ObjcClassInfo _RARENinePatch = { "NinePatch", "com.appnativa.rare.ui.painter", NULL, 0x1, 12, methods, 4, fields, 0, NULL};
  return &_RARENinePatch;
}

@end
@implementation RARENinePatch_Pair

- (id)initWithId:(id)first
          withId:(id)second {
  if (self = [super init]) {
    mFirst_ = first;
    mSecond_ = second;
  }
  return self;
}

- (NSString *)description {
  return [NSString stringWithFormat:@"Pair[%@, %@]", mFirst_, mSecond_];
}

- (void)copyAllFieldsTo:(RARENinePatch_Pair *)other {
  [super copyAllFieldsTo:other];
  other->mFirst_ = mFirst_;
  other->mSecond_ = mSecond_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "initWithId:withId:", NULL, NULL, 0x0, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "mFirst_", NULL, 0x0, "TE" },
    { "mSecond_", NULL, 0x0, "TE" },
  };
  static J2ObjcClassInfo _RARENinePatch_Pair = { "Pair", "com.appnativa.rare.ui.painter", "NinePatch", 0x8, 1, methods, 2, fields, 0, NULL};
  return &_RARENinePatch_Pair;
}

@end
