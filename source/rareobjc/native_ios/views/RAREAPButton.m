//
//  RAREAPButton.m
//  RareOSX
//
//  Created by Don DeCoteau on 3/25/13.
//  Copyright (c) 2013 SparseWare. All rights reserved.
//

#import "RAREAPButton.h"
#import "RAREImageWrapper.h"
#import "AppleHelper.h"
#import "APView+Component.h"
#import "com/appnativa/rare/platform/apple/ui/util/AppleGraphics.h"
#import "com/appnativa/rare/ui/event/MouseEvent.h"
#import "com/appnativa/rare/ui/listener/iMouseListener.h"
#import "com/appnativa/rare/ui/listener/iMouseMotionListener.h"
#import "com/appnativa/rare/platform/apple/ui/view/ParentView.h"
#import "com/appnativa/rare/platform/apple/ui/view/ButtonView.h"

@implementation RAREAPButton

@synthesize iconPosition=iconPosition;
@synthesize textVerticalAlighment=verticalAlighment;


+ (Class)layerClass
{
  return [RARECAGradientLayer class];
}

- (id)initWithFrame:(CGRect)frame
{
  self = [super initWithFrame: frame];
  if (self) {
    iconGap=4;
    autoRepeatTimeInterval_=300;
    [self addTarget:self action:@selector(buttonClicked:) forControlEvents:UIControlEventTouchUpInside];
  }
  return self;
}
-(BOOL)becomeFirstResponder {
  BOOL ok=[super becomeFirstResponder];
  if(ok) {
    [self gainedFocusEx];
  }
  return ok;
}
-(BOOL)resignFirstResponder {
  BOOL ok=[super resignFirstResponder];
  if(ok) {
    [self lostFocusEx];
  }
  return ok;
}
-(void) buttonClicked: (id) sender {
  RAREButtonView* view=(RAREButtonView*)self.sparView;
  if(!(self.tag & MASK_MOUSE_HANDLER)) {
    [view actionPerformed];
  }
}
-(void) setPeriodicDelay: (float) delay interval: (float) interval {
  
}

-(void) setContinuous: (BOOL) continuous {
  continuous_=continuous;
}

-(CGSize) preferredSize {
  CGSize size=[self sizeThatFits:self.frame.size];
  BOOL rtl=[AppleHelper isLTRText];
  RARERenderableDataItem_IconPosition ip=iconPosition;
  if(ip==RARERenderableDataItem_IconPosition_TRAILING) {
    ip=rtl ? RARERenderableDataItem_IconPosition_LEFT : RARERenderableDataItem_IconPosition_RIGHT;
  }
  else if(ip==RARERenderableDataItem_IconPosition_LEADING || !ip) {
    ip=rtl ? RARERenderableDataItem_IconPosition_RIGHT : RARERenderableDataItem_IconPosition_LEFT;
    
  }
  switch(ip) {
    case RARERenderableDataItem_IconPosition_TOP_CENTER :
    case RARERenderableDataItem_IconPosition_TOP_LEFT :
    case RARERenderableDataItem_IconPosition_TOP_RIGHT :
    case RARERenderableDataItem_IconPosition_BOTTOM_CENTER :
    case RARERenderableDataItem_IconPosition_BOTTOM_LEFT :
    case RARERenderableDataItem_IconPosition_BOTTOM_RIGHT :
      size.height+=iconGap;
      break;
    default:
      size.width+=iconGap;
      break;
  }
  return size;
}

-(void) setIconGap: (int) gap {
  iconGap=gap;
}

-(void) setIconPosition: (RARERenderableDataItem_IconPosition) position {
  iconPosition=position;
}

-(void) setTextVerticalAlignment: (RARERenderableDataItem_VerticalAlign) alignment {
  verticalAlighment=alignment;
}

-(CGRect)imageRectForContentRect:(CGRect)contentRect {
  BOOL rtl=[AppleHelper isLTRText];
  RARERenderableDataItem_IconPosition ip=iconPosition;
  if(ip==RARERenderableDataItem_IconPosition_TRAILING) {
    ip=rtl ? RARERenderableDataItem_IconPosition_LEFT : RARERenderableDataItem_IconPosition_RIGHT;
  }
  else if(ip==RARERenderableDataItem_IconPosition_LEADING || !ip) {
    ip=rtl ? RARERenderableDataItem_IconPosition_RIGHT : RARERenderableDataItem_IconPosition_LEFT;
    
  }
  CGRect rect=self.imageView.frame;
  switch(ip) {
    case RARERenderableDataItem_IconPosition_TOP_CENTER :
      rect.origin.x=contentRect.origin.x+((contentRect.size.width-rect.size.width)/2);
      rect.origin.y=contentRect.origin.y;
      break;
    case RARERenderableDataItem_IconPosition_TOP_LEFT :
      rect.origin.x=contentRect.origin.x;
      rect.origin.y=contentRect.origin.y;
      break;
    case RARERenderableDataItem_IconPosition_TOP_RIGHT :
      rect.origin.x=contentRect.origin.x+contentRect.size.width-rect.size.width;
      rect.origin.y=contentRect.origin.y;
      break;
    case RARERenderableDataItem_IconPosition_BOTTOM_CENTER :
      rect.origin.x=contentRect.origin.x+((contentRect.size.width-rect.size.width)/2);
      rect.origin.y=contentRect.origin.y+contentRect.size.height-rect.size.height;
      break;
    case RARERenderableDataItem_IconPosition_BOTTOM_LEFT :
      rect.origin.x=contentRect.origin.x;
      rect.origin.y=contentRect.origin.y+contentRect.size.height-rect.size.height;
      break;
    case RARERenderableDataItem_IconPosition_BOTTOM_RIGHT :
      rect.origin.x=contentRect.origin.x+contentRect.size.width-rect.size.width;
      rect.origin.y=contentRect.origin.y+contentRect.size.height-rect.size.height;
      break;
    case RARERenderableDataItem_IconPosition_RIGHT :
      rect.origin.x=contentRect.origin.x+contentRect.size.width-rect.size.width;
      rect.origin.y=contentRect.origin.y;
      
      break;
      
    default :
      rect.origin.x=contentRect.origin.x;
      rect.origin.y=contentRect.origin.y+((contentRect.size.height-rect.size.height)/2);
      break;
  }
  return rect;
}

-(CGRect)titleRectForContentRect:(CGRect)contentRect {
  BOOL rtl=[AppleHelper isLTRText];
  RARERenderableDataItem_IconPosition ip=iconPosition;
  CGRect rect=self.titleLabel.frame;
  if(ip==RARERenderableDataItem_IconPosition_TRAILING) {
    ip=rtl ? RARERenderableDataItem_IconPosition_LEFT : RARERenderableDataItem_IconPosition_RIGHT;
  }
  else if(ip==RARERenderableDataItem_IconPosition_LEADING || !ip) {
    ip=rtl ? RARERenderableDataItem_IconPosition_RIGHT : RARERenderableDataItem_IconPosition_LEFT;
    
  }
  CGRect imageRect=CGRectZero;
  if(self.currentImage ) {
    imageRect.size=self.currentImage.size;
  }
  switch(ip) {
    case RARERenderableDataItem_IconPosition_TOP_CENTER :
    case RARERenderableDataItem_IconPosition_TOP_LEFT :
    case RARERenderableDataItem_IconPosition_TOP_RIGHT :
      switch (verticalAlighment) {
        case RARERenderableDataItem_VerticalAlign_TOP:
          rect.origin.y=contentRect.origin.y+imageRect.size.height+iconGap;
          break;
        case RARERenderableDataItem_VerticalAlign_BOTTOM:
          rect.origin.y=contentRect.origin.y+contentRect.size.height-rect.size.height;
          break;
        default: {
          CGFloat dy=(contentRect.size.height-imageRect.size.height-rect.size.height)/2;
          rect.origin.y=contentRect.origin.y+imageRect.size.height+dy;
          break;
        }
      }
      break;
    case RARERenderableDataItem_IconPosition_BOTTOM_CENTER :
    case RARERenderableDataItem_IconPosition_BOTTOM_LEFT :
    case RARERenderableDataItem_IconPosition_BOTTOM_RIGHT :
      switch (verticalAlighment) {
        case RARERenderableDataItem_VerticalAlign_TOP:
          rect.origin.y=contentRect.origin.y;
          break;
        case RARERenderableDataItem_VerticalAlign_BOTTOM:
          rect.origin.y=contentRect.origin.y+contentRect.size.height-imageRect.size.height-iconGap;
          break;
        default: {
          CGFloat dy=(contentRect.size.height-imageRect.size.height-rect.size.height)/2;
          rect.origin.y=contentRect.origin.y+dy;
          break;
        }
      }
      break;
    case RARERenderableDataItem_IconPosition_RIGHT :
      rect.origin.x=contentRect.origin.x;
      rect.size.width=contentRect.size.width-(imageRect.size.width+iconGap);
      break;
      
    default :
      rect.origin.x=contentRect.origin.x+imageRect.size.width+iconGap;
      rect.size.width=contentRect.size.width-(imageRect.size.width+iconGap);
      break;
  }
  return rect;
}

-(void) setRotation:(int) degrees {
  rotation=(degrees*M_PI)/180;
}

- (void)drawRect:(CGRect)dirtyRect
{
  CGContextRef ctx=UIGraphicsGetCurrentContext();
  if(rotation) {
    CGContextConcatCTM(ctx, CGAffineTransformMakeRotation(rotation));
  }
  if(self.tag & MASK_PAINT_HANDLER) {
    RAREView* view=self.sparView;
    RAREUIRectangle* rect=[self sparBounds];
    
    RAREAppleGraphics* g=[[RAREAppleGraphics alloc]initWithId:(__bridge id)ctx withRAREView:view];
    [view paintBackgroundWithRAREAppleGraphics:g withRAREView:view withRAREUIRectangle:rect];
    
    [super drawRect:dirtyRect];
    
    //[view paintOverlayWithRAREAppleGraphics:g withRAREView:view withRAREUIRectangle:rect];
    [g dispose];
  }
  else {
    [super drawRect:dirtyRect];
  }
}

-(void)touchesBegan:(NSSet *)touches withEvent:(UIEvent *)event {
    RAREButtonView* view=(RAREButtonView*)self.sparView;
    [super touchesBegan:touches withEvent:event];
    if(continuous_ && view->actionListener_) {
        if(mouseDownTimer_) {
            [mouseDownTimer_ invalidate];
            mouseDownTimer_=nil;
        }
        mouseDownTimer_ = [NSTimer scheduledTimerWithTimeInterval: autoRepeatTimeInterval_
                                                           target:self
                                                         selector:@selector(mouseDownTimerFired:)
                                                         userInfo:event
                                                          repeats:YES];
        [view actionPerformed];
    }
}

-(void)touchesEnded:(NSSet *)touches withEvent:(UIEvent *)event {
  if(mouseDownTimer_) {
    [mouseDownTimer_ invalidate];
    mouseDownTimer_=nil;
  }
  [super touchesEnded:touches withEvent:event];
  if(!continuous_){
    RAREButtonView* view=(RAREButtonView*)self.sparView;
    [view actionPerformed];
  }
}

-(void)touchesCancelled:(NSSet *)touches withEvent:(UIEvent *)event {
  if(mouseDownTimer_) {
    [mouseDownTimer_ invalidate];
    mouseDownTimer_=nil;
  }
  [super touchesCancelled:touches withEvent:event];
}

-(void)touchesMoved:(NSSet *)touches withEvent:(UIEvent *)event {
  [super touchesMoved:touches withEvent:event];
}

- (void)mouseDownTimerFired:(NSTimer *)timer {
    RAREButtonView* view=(RAREButtonView*)self.sparView;
    [view actionPerformed];
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
