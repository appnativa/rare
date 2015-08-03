//
//  RARECALayer.m
//  RareTOUCH
//
//  Created by Don DeCoteau on 11/24/13.
//  Copyright (c) 2013 SparseWare. All rights reserved.
//

#import <com/appnativa/rare/ui/UIStroke.h>
#import <IOSFloatArray.h>
#import "RARECALayer.h"
#import "com/appnativa/rare/ui/UIRectangle.h"
#import "com/appnativa/rare/ui/UIInsets.h"
#import "AppleHelper.h"
#import "com/appnativa/rare/platform/apple/ui/util/AppleGraphics.h"
#import "com/appnativa/rare/platform/apple/ui/view/View.h"
#import "com/appnativa/rare/ui/iPlatformBorder.h"
#import "com/appnativa/rare/ui/painter/iPlatformPainter.h"

@implementation RARECALayer {
  RAREUIRectangle* rect_;
  RAREAppleGraphics* graphics;
  RAREUIInsets* insets_;
  __weak id<RAREiPlatformPainter> painter_;
  __weak id<RAREiPlatformPainter> systemPainter_;
  
}

-(id)init {
  self=[super init];
  if(self) {
   // self.masksToBounds=YES;
    self.fillColor=NULL;
    self.lineWidth=0;
    NSMutableDictionary *newActions = [[NSMutableDictionary alloc] initWithObjectsAndKeys:[NSNull null], @"onOrderIn",
                                       [NSNull null], @"onOrderOut",
                                       [NSNull null], @"sublayers",
                                       [NSNull null], @"contents",
                                       [NSNull null], @"position",
                                       [NSNull null], @"bounds",
                                       nil];
    self.actions = newActions;
  }
  return self;
}
-(id) initWithRAREView: (RAREView*) view {
  if(self=[self init]) {
    view_=view;
  }
  return self;
}
-(void) setPainter: (id<RAREiPlatformPainter>) painter {
  painter_=painter;
  [self setNeedsDisplay];
}
-(void) setSystemPainter: (id<RAREiPlatformPainter>) painter {
  systemPainter_=painter;
  self.contents=nil;
  self.contentsGravity=kCAGravityResize;
  [self setNeedsDisplay];
}
- (void) sparReset {
  self.fillColor=NULL;
  self.lineWidth=0;
  painter_=nil;
}

- (void) sparDispose {
  self.fillColor=NULL;
  self.lineWidth=0;
  painter_=nil;
}

-(BOOL)needsDisplayOnBoundsChange {
  return view_!=nil;
}

+ (void)setStroke:(RAREUIStroke *)stroke onLayer: (CAShapeLayer *) layer{
  switch ([stroke->cap_ ordinal]) {
    case RAREUIStroke_Cap_BUTT:
      layer.lineCap=kCALineCapButt;
      break;
    case RAREUIStroke_Cap_ROUND:
      layer.lineCap=kCALineCapRound;
      break;
    case RAREUIStroke_Cap_SQUARE:
      layer.lineCap=kCALineCapSquare;
      break;
  }
  switch ([stroke->join_ ordinal]) {
    case RAREUIStroke_Join_BEVEL:
      layer.lineJoin=kCALineJoinBevel;
      break;
    case RAREUIStroke_Join_MITER:
      layer.lineJoin=kCALineJoinMiter;
      break;
    case RAREUIStroke_Join_ROUND:
      layer.lineJoin=kCALineJoinRound;
      break;
  }
  layer.lineWidth=stroke->width_;
  layer.miterLimit=stroke->miterLimit_;
  layer.lineDashPhase=stroke->dashPhase_;
  if(stroke->dashIntervals_ && stroke->dashIntervals_.count>0) {
    NSInteger len=stroke->dashIntervals_.count;
    NSMutableArray *dash=[NSMutableArray arrayWithCapacity:len];
    for(int i=0;i<len;i++) {
      [dash addObject: [NSNumber numberWithFloat:[stroke->dashIntervals_ floatAtIndex:i]]];
    }
    layer.lineDashPattern=dash;
  }
  else {
    layer.lineDashPattern=nil;
  }
}

- (void)setStroke:(RAREUIStroke *)stroke {
  [RARECALayer setStroke:stroke onLayer:self];
}

-(void)drawInContext:(CGContextRef)ctx {
  [super drawInContext:ctx];
  id<RAREiPlatformPainter> p=painter_;
  if(!view_ || (!isOverlay_ && (!p || !systemPainter_))) {
    return;
  }
  if(!rect_) {
    rect_=[RAREUIRectangle new];
  }
  [rect_ setWithRect:self.bounds];
  if(!graphics) {
    graphics=[[RAREAppleGraphics alloc]initWithId:(__bridge id)(ctx) withRAREView:(RAREView*)view_];
  }
  else {
    [graphics resetWithRAREView:(RAREView*)view_ withId:(__bridge id)(ctx)];
  }
  if(isOverlay_) {
    [view_ paintOverlayWithRAREAppleGraphics:graphics withRAREView:(RAREView*)view_ withRAREUIRectangle:rect_];
  }
  else if(p) {
    [view_ paintPainterWithRAREiPlatformPainter:p withRAREAppleGraphics:graphics withRAREView:(RAREView*)view_ withFloat:rect_->width_ withFloat:rect_->height_];
  }
  if(systemPainter_) {
    [view_ paintPainterWithRAREiPlatformPainter:systemPainter_ withRAREAppleGraphics:graphics withRAREView:(RAREView*)view_ withFloat:rect_->width_ withFloat:rect_->height_];
  }
  [graphics dispose];
}

@end
