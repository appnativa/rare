//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/platform/apple/ui/view/LineView.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RARELineView_H_
#define _RARELineView_H_

@class RAREAppleGraphics;
@class RARELineView_LineHelper;
@class RAREUIDimension;
@class RAREUIRectangle;

#import "JreEmulation.h"
#include "com/appnativa/rare/platform/apple/ui/view/View.h"
#include "com/appnativa/rare/ui/aLineHelper.h"

@interface RARELineView : RAREView {
 @public
  RARELineView_LineHelper *lineHelper_;
}

- (id)init;
- (void)paintBackgroundWithRAREAppleGraphics:(RAREAppleGraphics *)g
                                withRAREView:(RAREView *)v
                         withRAREUIRectangle:(RAREUIRectangle *)rect;
- (RAREaLineHelper *)getLineHelper;
- (void)getMinimumSizeWithRAREUIDimension:(RAREUIDimension *)size
                                withFloat:(float)maxWidth;
- (void)getPreferredSizeWithRAREUIDimension:(RAREUIDimension *)size
                                  withFloat:(float)maxWidth;
- (BOOL)isMouseTransparent;
- (void)disposeEx;
- (void)setEnabledExWithBoolean:(BOOL)b;
+ (id)createProxy;
- (void)copyAllFieldsTo:(RARELineView *)other;
@end

J2OBJC_FIELD_SETTER(RARELineView, lineHelper_, RARELineView_LineHelper *)

typedef RARELineView ComAppnativaRarePlatformAppleUiViewLineView;

@interface RARELineView_LineHelper : RAREaLineHelper {
}

- (id)initWithRARELineView:(RARELineView *)outer$
               withBoolean:(BOOL)horizontal;
- (void)thicknessRecalculated;
@end

#endif // _RARELineView_H_
