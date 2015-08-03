//
// Created by Don DeCoteau on 8/27/13.
// Copyright (c) 2013 SparseWare. All rights reserved.
//
// To change the template use AppCode | Preferences | File Templates.
//


#import <Foundation/Foundation.h>

@protocol RAREUTiCancelable;
@class RAREUIColor;
@class RAREUIImage;

@interface RAREWaitCursorView : UIView {
  UILabel* label_;
  UIActivityIndicatorView *spinner_;
  UIImageView *cancelButton_;
}
-(id) initWithMessage: (id<JavaLangCharSequence>) message cancelButtonImage:(RAREUIImage*) image delay: (int) delay;
-(void) setMessage: (id<JavaLangCharSequence>) message;
-(void) setSpinnerColor: (RAREUIColor *) spinnerColor labelColor: (RAREUIColor *) labelColor;
-(void) setColor: (UIColor *) color;
-(void) show;
-(void) dismiss;
-(void) startAnimation;
-(void) stopAnimation;

@end