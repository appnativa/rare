//
//  RAREAPSlider.m
//  RareOSX
//
//  Created by Don DeCoteau on 3/25/13.
//  Copyright (c) 2013 SparseWare. All rights reserved.
//

#import "RAREAPSlider.h"
#import "APView+Component.h"
#import "com/appnativa/rare/ui/event/MouseEventEx.h"
#import "com/appnativa/rare/ui/listener/iMouseListener.h"
#import "com/appnativa/rare/ui/listener/iMouseMotionListener.h"
#import "com/appnativa/rare/platform/apple/ui/view/ParentView.h"
#import "com/appnativa/rare/ui/UIImage.h"
#import "com/appnativa/rare/ui/painter/iPlatformPainter.h"
#import "com/appnativa/rare/ui/UIRectangle.h"
#import "com/appnativa/rare/platform/apple/ui/util/AppleGraphics.h"
#import "com/appnativa/rare/platform/apple/ui/view/View.h"

@implementation RAREAPSlider {
  CGRect trackBounds;
}
static UIImage* singlePixelImage;
+ (Class)layerClass
{
  return [RARECAGradientLayer class];
}
- (id)initWithFrame:(CGRect)frame
{
  self = [super initWithFrame:frame];
  if (self) {
    horizontal_=YES;
  }
  
  return self;
}
-(CGSize)sizeThatFits:(CGSize)size {
  size=[super sizeThatFits:size];
  if(trackPainterWidth>0) {
    if(horizontal_) {
      size.width=MAX(size.width, trackPainterWidth);
    }
    else {
      size.height=MAX(size.height, trackPainterWidth);
    }
  }
  return size;
}
- (void)setThumbImage: (RAREUIImage*) image {
  [self setThumbImage:(UIImage*)[image getNativeImage] forState:UIControlStateNormal];
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
-(void)drawRect:(CGRect)dirtyRect {
  CGContextRef ctx = UIGraphicsGetCurrentContext();
  RAREAppleGraphics *g = nil;
  RAREView *view = self.sparView;
  if (self.tag & MASK_PAINT_HANDLER) {
    g = [[RAREAppleGraphics alloc] initWithId:(__bridge id) ctx withRAREView:view];
    RAREUIRectangle *rect = [self sparBounds];
    [view paintBackgroundWithRAREAppleGraphics:g withRAREView:view withRAREUIRectangle:rect];
    
    [super drawRect:dirtyRect];
    
    [view paintOverlayWithRAREAppleGraphics:g withRAREView:view withRAREUIRectangle:rect];
  }
  else {
    [super drawRect:dirtyRect];
  }
  if(trackPainter) {
    if (!g) {
      g = [[RAREAppleGraphics alloc] initWithId:(__bridge id) ctx withRAREView:view];
    }
    CGRect r=trackBounds;
    CGFloat w=r.size.width;
    CGFloat h=r.size.height;
    [trackPainter paintWithRAREiPlatformGraphics:g withFloat:r.origin.x withFloat:r.origin.y withFloat:w withFloat:h withInt:1];
  }
  if(g) {
    [g dispose];
  }
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

- (void)setMaxValue:(float)maximum {
  self.maximumValue=maximum;
}

- (void)setMinValue:(float)minimum {
  self.minimumValue=minimum;
}

- (float)getValue {
  return self.value;
}

- (void)setHorizontal:(BOOL)horizontal {
  if(horizontal!=horizontal_) {
    horizontal_=horizontal;
    if(!horizontal) {
      self.transform=CGAffineTransformRotate(self.transform,270.0/180*M_PI);
    }
    else {
      self.transform=CGAffineTransformIdentity;
    }
  }
  
}

- (BOOL)isHorizontal {
  return horizontal_;
}
- (void)setTrackPainter:(id<RAREiPlatformPainter>) painter {
  trackPainter=painter;
  if(!singlePixelImage) {
    UIGraphicsBeginImageContext(CGSizeMake(1, 1));
    singlePixelImage = UIGraphicsGetImageFromCurrentImageContext();
    UIGraphicsEndImageContext();
  }
  [self setMinimumTrackImage:singlePixelImage forState:UIControlStateNormal];
  [self setMaximumTrackImage:singlePixelImage forState:UIControlStateNormal];
}
-(CGRect)trackRectForBounds:(CGRect)bounds {
  CGRect r=[super trackRectForBounds:bounds];
  if(trackPainterWidth>0) {
    if(horizontal_) {
      r.size.width=MIN(r.size.width, trackPainterWidth);
    }
    else {
      r.size.height=MIN(r.size.width, trackPainterWidth);
    }
  }
  trackBounds=r;
  return r;
}
- (void)setTrackPainterWidth:(int) width {
  trackPainterWidth=width;
}

-(void)setHidden:(BOOL)hidden {
  BOOL changed=self.hidden!=hidden;
  [super setHidden:hidden];
  if(changed && self.window) {
    RAREView *view = self.sparView;
    if (view && view->viewListener_ ) {
      [view visibilityChangedWithBoolean:!hidden];
    }
  }
}
- (void)willMoveToWindow:(UIWindow *)newWindow {
  [super willMoveToWindow:newWindow];
  RAREView* view=self.sparView;
  if(view && view->viewListener_ && self.window!=newWindow) {
    [view visibilityChangedWithBoolean:newWindow!=nil];
  }
}
@end
