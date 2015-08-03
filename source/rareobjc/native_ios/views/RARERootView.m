//
//  RARERootView.m
//  RareIOS
//
//  Created by Don DeCoteau on 5/21/13.
//  Copyright (c) 2013 SparseWare. All rights reserved.
//

#import "RARERootView.h"
#import "APView+Component.h"
#import "RAREOverlayView.h"
#import "RAREAPWindow.h"

@implementation RARERootView {
  RAREOverlayView* glass_;
}
-(id)initWithFrame:(CGRect)frame {
  self=[super initWithFrame:frame];
  if(self) {
  }
  return self;
}

-(void)layoutSubviews {
  CGRect frame=self.bounds;
  if(((RAREAPWindow*)self.window).isMainWindow) {
    if (floor(NSFoundationVersionNumber)> NSFoundationVersionNumber_iOS_6_1) {
      CGFloat sbh=self.window.rootViewController.topLayoutGuide.length;
      frame.size.height-=sbh;
      frame.origin.y+=sbh;
    }
  }
  if(overlayView) {
    overlayView.frame=frame;
    [self bringSubviewToFront:overlayView];
  }
  if(glass_) {
    glass_.frame=frame;
    [self bringSubviewToFront:glass_];
  }
  if(self.layoutManager!=nil) {
    RAREView* view=self.sparView;
    [self.layoutManager layoutWithRAREParentView:(RAREParentView*)view withFloat: frame.size.width withFloat:frame.size.height];
  }
  else {
    for(UIView* v in self.subviews) {
      v.frame=frame;
    }
  }
}
-(void) addViewToGlass: (UIView*) view {
  if(!glass_) {
    glass_=[[RAREOverlayView alloc]initWithFrame:self.bounds];
    [glass_ setActAsGlass:YES];
    [self addSubview:glass_];
  }
  [glass_ addSubview:view];
  [self setNeedsLayout];
}
-(void) removeViewFromGlass: (UIView*) view {
  [view removeFromSuperview];
  if(glass_ && glass_.subviews.count==0) {
    [glass_ removeFromSuperview];
    glass_=nil;
  }
  if(glass_) {
    [self setNeedsDisplay];
  }
}

@end
