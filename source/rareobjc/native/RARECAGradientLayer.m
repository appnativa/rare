//
//  RARECAGradientLayer.m
//  RareTOUCH
//
//  Created by Don DeCoteau on 11/24/13.
//  Copyright (c) 2013 SparseWare. All rights reserved.
//

#import "RARECAGradientLayer.h"
#import "RARECALayer.h"
#import "com/appnativa/rare/ui/UIRectangle.h"
#import "com/appnativa/rare/ui/UIInsets.h"
#import "AppleHelper.h"
#import "com/appnativa/rare/ui/iPlatformBorder.h"
#import "com/appnativa/rare/platform/apple/ui/util/AppleGraphics.h"
#import "com/appnativa/rare/platform/apple/ui/view/aView.h"
#import "com/appnativa/rare/ui/painter/iPlatformPainter.h"

@implementation RARECAGradientLayer {
  __weak RARECALayer* overLayer_;
  RARECALayer* bgOverLayer_;
  RAREUIRectangle* rect_;
  RAREAppleGraphics* graphics;
  RAREUIInsets* insets_;
}

-(id)init {
  self=[super init];
  if(self) {
    self.masksToBounds=YES;
  }
  return self;
}

-(id) initWithRAREView: (RAREaView*) view {
  if(self=[super init]) {
    view_=view;
  }
  return self;
}

-(void) sparDispose {
  graphics=nil;
  RARECALayer* l=overLayer_;
  if(l) {
    [l removeFromSuperlayer];
    l.contents=nil;
  }
  l=bgOverLayer_;
  if(l) {
    [l removeFromSuperlayer];
    l.contents=nil;
  }
  bgOverLayer_=nil;
  overLayer_=nil;
  rect_=nil;
  insets_=nil;
}

-(void) sparResetLayer {
  RARECALayer* l=overLayer_;
  if(l) {
    l.contents=nil;
    [l removeFromSuperlayer];
  }
  l=bgOverLayer_;
  if(l) {
    [l removeFromSuperlayer];
    l.contents=nil;
  }
  bgOverLayer_=nil;
  overLayer_=nil;
  self.colors=nil;
  self.locations=nil;
  self.borderWidth=0;
  self.borderColor=NULL;
  self.cornerRadius=0;
  self.backgroundColor=nil;
  view_=nil;
  paintEnabled_=NO;
  layoutEnabled_=NO;
}
-(void) repaint {
  [self setNeedsDisplay];
  if(bgOverLayer_) {
    [bgOverLayer_ setNeedsDisplay];
  }
  if(overLayer_) {
    [overLayer_ setNeedsDisplay];
  }
}
-(void) sparResetLayerForRendererWithView: (RAREaView*) view {
  [self sparResetLayer];
  view_=view;
}

-(void) removeNativeBorder {
  self.borderWidth=0;
  self.borderColor=NULL;
  self.cornerRadius=0;
  self.mask=nil;
  layoutEnabled_=NO;
  if(overLayer_) {
    overLayer_.path=NULL;
    overLayer_.borderWidth=0;
  }
  
}
-(BOOL)needsDisplayOnBoundsChange {
  return view_!=nil;
}

-(void) setOverlayLayer: (RARECALayer*) layer {
  if(overLayer_) {
    [self replaceSublayer:overLayer_ with:layer];
  }
  else {
    [self addSublayer:layer];
  }
  layer.zPosition=self.sublayers.count;
  overLayer_=layer;
}

-(RARECALayer*) getOverLayerCreate:(BOOL) create forRAREView: (RAREaView*) view {
  if(create && !overLayer_) {
    RARECALayer* l=[RARECALayer layer];
    [self addSublayer:l];
    l.zPosition=self.sublayers.count;
    l->isOverlay_=YES;
    l->view_=view;
    overLayer_=l;
  }
  return overLayer_;
}
-(RARECALayer*) getBackgroundOverLayerCreate:(BOOL) create forRAREView: (RAREaView*) view {
  if(create && !bgOverLayer_) {
    RARECALayer* l=[RARECALayer layer];
    l->view_=view;
    bgOverLayer_=l;
//    [self insertSublayer:l atIndex:0];
  }
  return bgOverLayer_;
}
-(void)dealloc {
 }

-(void) setOverlayPath: (id) nativepath view: (RAREaView*) view {
  if(!nativepath) {
    if([overLayer_ isKindOfClass:[CAShapeLayer class]]) {
      ((CAShapeLayer*)self.mask).path=NULL;
    }
  }
  else {
    RARECALayer* l=[self getOverLayerCreate:YES forRAREView:view];
    if(!nativepath) {
      l.path=NULL;
    }
    else {
#if TARGET_OS_IPHONE
      l.path=((UIBezierPath*)nativepath).CGPath;
#else
      nativepath=[AppleHelper quartzPath:(NSBezierPath*)nativepath];
      l.path=(__bridge CGPathRef)(nativepath);
#endif
    }
  }
}
-(void)layoutSublayers {
  [super layoutSublayers];
  if(overLayer_) {
    overLayer_.frame =self.bounds;
    overLayer_.zPosition=self.sublayers.count;
    [overLayer_ setNeedsDisplay];
  }
  if(bgOverLayer_) {
    bgOverLayer_.frame =self.bounds;
    [bgOverLayer_ setNeedsDisplay];
  }
  if(view_ && layoutEnabled_) {
    CGSize size=self.bounds.size;
    [view_ layingoutLayersWithFloat:size.width withFloat:size.height];
  }
}

-(void)drawInContext:(CGContextRef)ctx {
  if(view_ && paintEnabled_) {
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
    [view_ paintBackgroundWithRAREAppleGraphics:graphics withRAREView:(RAREView*)view_ withRAREUIRectangle:rect_];
    [graphics dispose];
  }
  if(bgOverLayer_) {
    if([bgOverLayer_ needsDisplay]) {
      [bgOverLayer_ drawInContext:ctx];
    }
    [bgOverLayer_ renderInContext:ctx];
  }
  [super drawInContext:ctx];
}

@end
