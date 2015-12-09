//
// Created by Don DeCoteau on 8/27/13.
// Copyright (c) 2013 SparseWare. All rights reserved.
//
// To change the template use AppCode | Preferences | File Templates.
//


#import <com/appnativa/rare/iCancelableFuture.h>
#import <com/appnativa/rare/ui/UIColor.h>
#import <com/appnativa/rare/platform/apple/ui/view/View.h>
#import <UIKit/UIKit.h>
#import <com/appnativa/rare/ui/WaitCursorHandler.h>
#import "RAREWaitCursorView.h"
#import "APView+Component.h"
#import "AppleHelper.h"
#import "RARERootView.h"
#import "RAREAPApplication.h"
#import "RAREUIViewController.h"
#import <com/appnativa/rare/ui/ColorUtils.h>
#import <com/appnativa/rare/ui/UIColor.h>
#import <com/appnativa/rare/ui/UIImage.h>
#import <com/appnativa/rare/ui/painter/iPlatformComponentPainter.h>
#import "com/appnativa/rare/platform/apple/ui/util/AppleGraphics.h"
#import "RAREAPPopupWindow.h"

@implementation RAREWaitCursorView {
  UIView* rectView;
  int delay_;
  UIView* contentView;
}
+ (Class)layerClass
{
  return [RARECAGradientLayer class];
}
-(id) initWithMessage: (id<JavaLangCharSequence>) message cancelButtonImage:(RAREUIImage*) image delay: (int) delay {
  if(self=[super initWithFrame:[UIScreen mainScreen].orientedBounds]) {
    self.opaque=NO;
    delay_=delay;
    contentView=[UIView new];
    [self addSubview:contentView];
    if(delay>0) {
      contentView.hidden=YES;
    }
    contentView.opaque=NO;
    rectView=[UIView new];
    [contentView addSubview:rectView];
    spinner_=[UIActivityIndicatorView new];
    [contentView addSubview:spinner_];
    spinner_.activityIndicatorViewStyle=UIActivityIndicatorViewStyleWhiteLarge;
    if(image) {
      cancelButton_=[UIImageView new];
      cancelButton_.image=(UIImage*)image.getNativeImage;
      UITapGestureRecognizer* r=[[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(canceled:)];
      [self addGestureRecognizer:r];
      [contentView addSubview: cancelButton_];
    }
    if(message) {
      [self setMessage:message];
    }
  }
  return self;
}

-(void) canceled:(UIGestureRecognizer*) r {
  switch(r.state) {
    case UIGestureRecognizerStateEnded: {
      CGPoint p=[r locationInView:self];
      CGRect frame=cancelButton_.frame;
        if(CGRectContainsPoint(frame, p)) {
          RAREWaitCursorHandler_SpinnerDialog *view=(RAREWaitCursorHandler_SpinnerDialog*)self.sparView;
          [view dialogCanceled];
        }
      }
      break;

    default:
      break;
  }
}
- (void)drawRect:(CGRect)dirtyRect {
  if(!contentView.hidden) {
    CGContextRef ctx = UIGraphicsGetCurrentContext();
    RAREView *view = self.sparView;
    RAREAppleGraphics *g = [[RAREAppleGraphics alloc] initWithId:(__bridge id) ctx withRAREView:view];
    RAREUIRectangle *r = [RAREUIRectangle fromRect:rectView.frame];
    [view->componentPainter_ paintWithRAREiPlatformGraphics:g withFloat:r->x_ withFloat:r->y_ withFloat:r->width_ withFloat:r->height_ withInt:0];
    [super drawRect:dirtyRect];
    [g dispose];
  }
}
-(void) setSpinnerColor: (RAREUIColor *) spinnerColor labelColor: (RAREUIColor *) labelColor {
  if(spinnerColor) {
    spinner_.color=(UIColor*)spinnerColor.getAPColor;
  }
  if(labelColor && label_) {
    label_.textColor=(UIColor*)labelColor.getAPColor;
  }

}
-(void) setColor: (UIColor *) color{
   spinner_.color=color;
  if(label_) {
    label_.textColor=color;
  }
  
}


-(void) setMessage: (id<JavaLangCharSequence>) message {
  if(!label_) {
    label_=[UILabel new];
    label_.backgroundColor=[UIColor clearColor];
    label_.textColor=[UIColor whiteColor];
    [contentView addSubview: label_];
  }
  [label_ setCharSequence:message];
  [self setNeedsLayout];
}
-(void) startAnimation {
  [spinner_ startAnimating];
}
-(void) stopAnimation {
  [spinner_ stopAnimating];
}
-(void) dismiss {
  [spinner_ stopAnimating];
  if(self.window) {
    RAREAPPopupWindow *window= (RAREAPPopupWindow*) self.window;
    RARERootView* root=(RARERootView*)window.rootViewController.view;
    [root removeViewFromGlass:self];
    [window disposeEx];
  }
}
-(void) show {
  if(self.window) {
    self.window.hidden=NO;
    return;
  }
  RAREAPPopupWindow *window= [[RAREAPPopupWindow alloc] initWithFrame:[UIScreen mainScreen].bounds];
  window.hidden=NO;
  window.windowLevel=UIWindowLevelAlert;
  RARERootView* root=(RARERootView*)window.rootViewController.view;
  [root addViewToGlass:self];
  [spinner_ startAnimating];
  if(delay_>0) {
    dispatch_after(dispatch_time(DISPATCH_TIME_NOW, (int64_t)(delay_ * NSEC_PER_MSEC)), dispatch_get_main_queue(), ^{
      if(self.window) {
        contentView.hidden=NO;
        [self setNeedsDisplay];
      }
    });
  }
}
- (void)layoutSubviews {
  contentView.frame=self.bounds;
  CGSize size=self.window.screen.orientedSize;
  CGSize cs=cancelButton_ ? [cancelButton_ sizeThatFits:size] : CGSizeZero;
  CGSize ls=label_ ? [label_ sizeThatFits:size] : CGSizeZero;
  CGSize ss=[spinner_ sizeThatFits: size];
  CGFloat scale=[UIScreen mainScreen].scale;
  CGFloat mw=cs.width+ss.width+ls.width;
  CGFloat mh=MAX(MAX(cs.height,ss.height),ls.height);
  CGFloat x=(size.width-mw)/2;
  spinner_.frame= CGRectMake(x, (size.height-ss.height)/2, ss.width, ss.height);
  x+=ss.width+(4*scale);
  if(label_) {
    label_.frame= CGRectMake(x, (size.height-ls.height)/2, ls.width, ls.height);
    x+=ls.width+(4*scale);
  }
  if(cancelButton_) {
    cancelButton_.frame= CGRectMake(x, (size.height-cs.height)/2, cs.width, cs.height);
  }
  mh+=(20*scale);
  mw+=(20*scale);
  rectView.frame= CGRectMake((size.width-mw)/2, (size.height-mh)/2, mw, mh);

}

@end