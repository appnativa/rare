//
//  RAREAPSCrollView.m
//  RareOSX
//
//  Created by Don DeCoteau on 3/25/13.
//  Copyright (c) 2013 SparseWare. All rights reserved.
//

#import "RAREAPSCrollView.h"
#import "APView+Component.h"
#import "com/appnativa/rare/ui/UIRectangle.h"
#import "com/appnativa/rare/platform/apple/ui/util/AppleGraphics.h"
#import "com/appnativa/rare/platform/apple/ui/view/View.h"
#import "com/appnativa/rare/platform/apple/ui/view/ScrollView.h"
#import "com/appnativa/rare/ui/event/MouseEventEx.h"
#import "com/appnativa/rare/ui/listener/iMouseListener.h"
#import "com/appnativa/rare/ui/listener/iMouseMotionListener.h"
#import "RAREOverlayView.h"

@implementation RAREAPScrollView

+ (Class)layerClass
{
  return [RARECAGradientLayer class];
}
- (id)initWithFrame:(CGRect)frame
{
    self = [super initWithFrame:frame];
    if (self) {
      [self setShowsHorizontalScrollIndicator: YES];
      [self setShowsVerticalScrollIndicator:YES];
      self.backgroundColor=[UIColor clearColor];
      self.layer.borderWidth=0;
    }
    
    return self;
}
-(BOOL)isOpaque {
  return YES;
}

-(void) createOverlayView: (BOOL) wantsFirstInteraction {
  if(!overlayView) {
    overlayView=[RAREOverlayView new];
    [overlayView setWantsFirstInteraction:wantsFirstInteraction];
    [self addSubview:overlayView];
    [self bringSubviewToFront:overlayView];
  }
}
-(void)sparDispose {
  [super sparDispose];
  overlayView=nil;
}
-(void)layoutSubviews {
  RAREScrollView* view=(RAREScrollView*)self.sparView;
   CGSize size=self.frame.size;
  [view layoutWithRAREParentView:view withFloat:size.width withFloat:size.height];
  [super layoutSubviews];
  [self flashScrollIndicators];
}
-(BOOL)isDirectionalLockEnabled {
  RAREScrollView* view=(RAREScrollView*)self.sparView;
  if(!view->hasVerticalScrollbar_ || !view->hasHorizontalScrollbar_) {
    return YES;
  }
  return [super isDirectionalLockEnabled];
}
-(void) removeOverlayView {
  if(overlayView) {
    [overlayView removeFromSuperview];
    overlayView=nil;
  }
}
-(void)addSubview:(UIView *)view {
  [super addSubview:view];
  if(overlayView) {
    [self bringSubviewToFront:overlayView];
  }
}
-(void)setNeedsLayout {
  [super setNeedsLayout];
}
-(void)touchesBegan:(NSSet *)touches withEvent:(UIEvent *)event {
  if(self.tag & MASK_MOUSE_HANDLER) {
    RAREView* view=self.sparView;
    RAREMouseEvent* me=[[RAREMouseEventEx alloc] initWithId:view withId:event];
    [view->mouseListener_ mousePressedWithRAREMouseEvent:me];
    if(me.isConsumed) {
      return;
    }
  }
  
  [super touchesBegan:touches withEvent:event];
  
}
-(void) moveLeftRight: (BOOL) left block: (BOOL) block {
  CGRect rect=UIEdgeInsetsInsetRect(self.bounds,self.contentInset);
  [self moveLeftRight:left increment: block ? rect.size.width : rect.size.width/10];
}
-(void) moveLeftRight: (BOOL) left increment: (CGFloat) x {
  CGSize size=self.contentSize;
  CGRect rect=self.bounds;
  CGPoint p=self.contentOffset;
  rect=UIEdgeInsetsInsetRect(rect,self.contentInset);
  if(left) {
    x+=p.x;
  }
  else {
    x=p.x-x;
  }
  CGFloat mx=size.width-rect.size.width;
  if(x>mx) {
    x=mx;
  }
  if(x<0) {
    x=0;
  }
  if((int)p.x!=(int)x) {
    p.x=x;
    self.contentOffset=p;
    
  }
}
-(void) moveUpDown: (BOOL) up block: (BOOL) block {
  CGRect rect=UIEdgeInsetsInsetRect(self.bounds,self.contentInset);
  [self moveLeftRight:up increment: block ? rect.size.width : rect.size.width/10];
}
-(void) moveUpDown: (BOOL) up increment: (CGFloat) y {
  CGSize size=self.contentSize;
  CGRect rect=self.bounds;
  CGPoint p=self.contentOffset;
  rect=UIEdgeInsetsInsetRect(rect,self.contentInset);
  if(up) {
    y+=p.y;
  }
  else {
    y=p.y-y;
  }
  CGFloat my=size.height-rect.size.height;
  if(y>my) {
    y=my;
  }
  if(y<0) {
    y=0;
  }
  if((int)p.y!=(int)y) {
    p.y=y;
    self.contentOffset=p;
  }
}
-(void)touchesEnded:(NSSet *)touches withEvent:(UIEvent *)event {
  if(self.tag & MASK_MOUSE_HANDLER) {
    RAREView* view=self.sparView;
    RAREMouseEvent* me=[[RAREMouseEventEx alloc] initWithId:view withId:event];
    [view->mouseListener_ mouseReleasedWithRAREMouseEvent:me];
    if(me.isConsumed) {
      return;
    }
  }
  [super touchesEnded:touches withEvent:event];
  
  
}
-(void)touchesMoved:(NSSet *)touches withEvent:(UIEvent *)event {
  if(self.tag & MASK_MOUSE_MOTION_HANDLER) {
    RAREView* view=self.sparView;
    RAREMouseEvent* me=[[RAREMouseEventEx alloc] initWithId:view withId:event];
    [view->mouseMotionListener_ mouseDraggedWithRAREMouseEvent:me];
    if(me.isConsumed) {
      return;
    }
  }
  [super touchesMoved:touches withEvent:event];
}

- (void)drawRect:(CGRect)dirtyRect
{
  if(self.tag & MASK_PAINT_HANDLER) {
    
    RAREView* view=self.sparView;
    RAREUIRectangle* rect=[self sparBounds];
    
    RAREAppleGraphics* g=[[RAREAppleGraphics alloc]initWithId:(__bridge id)(UIGraphicsGetCurrentContext()) withRAREView:view];
    [view paintBackgroundWithRAREAppleGraphics:g withRAREView:view withRAREUIRectangle:rect];
    //[view paintOverlayWithRAREAppleGraphics:g withRAREView:view withRAREUIRectangle:rect];
    [g dispose];
   // [super drawRect:dirtyRect];
    
  }
  else {
    [super drawRect:dirtyRect];
  }
}
@end
