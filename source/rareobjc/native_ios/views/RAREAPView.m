//
//  RAREAPView.m
//  RareOSX
//
//  Created by Don DeCoteau on 3/19/13.
//  Copyright (c) 2013 SparseWare. All rights reserved.
//

#import "RAREAPView.h"
#import "com/appnativa/rare/ui/UIRectangle.h"
#import "com/appnativa/rare/ui/UIDimension.h"
#import "com/appnativa/rare/platform/apple/ui/util/AppleGraphics.h"
#import "com/appnativa/rare/ui/event/MouseEventEx.h"
#import "com/appnativa/rare/ui/event/KeyEvent.h"
#import "com/appnativa/rare/ui/listener/iMouseListener.h"
#import "com/appnativa/rare/ui/listener/iMouseMotionListener.h"
#import "com/appnativa/rare/ui/listener/iKeyListener.h"
#import "com/appnativa/rare/ui/listener/iFocusListener.h"
#import "com/appnativa/rare/platform/apple/ui/view/ParentView.h"
#import "APView+Component.h"
#import "RAREOverlayView.h"
#import "RAREDisplayLinkManager.h"

@implementation RAREAPView {
}
static RAREDisplayLinkManager* linkManager;
@synthesize layoutManager;

+ (Class)layerClass
{
  return [RARECAGradientLayer class];
}
- (id)initWithFrame:(CGRect)frame
{
  self = [super initWithFrame:frame];
  if (self) {
    self.tag=0;
    useFlipTransform=NO;
    self.opaque=NO;
    self.userInteractionEnabled=YES;
    self.multipleTouchEnabled=YES;
    self.clipsToBounds=YES;
  }
  return self;
}
-(void)sparDispose {
  self.layoutManager=nil;
  [super sparDispose];
  overlayView=nil;
}
-(void) setOverlayView: (UIView*) view {
  [self removeOverlayView];
  if(view) {
    overlayView=view;
    [self addSubview:overlayView];
    [self bringSubviewToFront:overlayView];
  }
}
-(void) createOverlayView: (BOOL) wantsFirstInteraction {
  if(!overlayView) {
    overlayView=[RAREOverlayView new];
    [((RAREOverlayView*)overlayView) setWantsFirstInteraction:wantsFirstInteraction];
    [self addSubview:overlayView];
    [self bringSubviewToFront:overlayView];
  }
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
-(void) removeAllSubViews {
  [[self subviews] makeObjectsPerformSelector:@selector(removeFromSuperview)];
  if(overlayView) {
    [self addSubview:overlayView];
    [self bringSubviewToFront:overlayView];
  }
}
-(BOOL)canBecomeFirstResponder {
  return NO;
}

-(void) setUseFlipTransform: (BOOL) flip {
  useFlipTransform=flip;
  [self setPaintHandlerEnabled:YES];
}


- (void)drawRect:(CGRect)dirtyRect
{
  if (self.tag & MASK_PAINT_HANDLER) {
    CGContextRef ctx = UIGraphicsGetCurrentContext();
    
    RAREView* view=self.sparView;
    RAREUIRectangle* rect=[self sparBounds];
    if(useFlipTransform) {
      CGContextTranslateCTM(ctx,0,rect->height_);
      CGContextScaleCTM(ctx,1,-1);
    }
    RAREAppleGraphics* g=[[RAREAppleGraphics alloc]initWithId:(__bridge id)(ctx) withRAREView:view];
    [view paintBackgroundWithRAREAppleGraphics:g withRAREView:view withRAREUIRectangle:rect];
    [view paintOverlayWithRAREAppleGraphics:g withRAREView:view withRAREUIRectangle:rect];
    [g dispose];
  }
  [super drawRect:dirtyRect];
 }
-(void)setFrame:(CGRect)frame {
  [super setFrame:frame];
}
-(void) layoutSubviews {
  if(overlayView) {
    overlayView.frame=self.frame;
  }
  if(layoutManager!=nil) {
    RAREView* view=self.sparView;
    CGSize size=self.bounds.size;
    if(size.height==0 || size.width==0 || [view isAnimating]) {
      return;
    }
    [layoutManager layoutWithRAREParentView:(RAREParentView*)view withFloat: size.width withFloat:size.height];
  }
  else {
    [super layoutSubviews];
  }
}
-(void)setNeedsLayout {
  [super setNeedsLayout];
  if(layoutManager!=nil) {
    [layoutManager invalidateLayout];
  }
  
}
-(BOOL)hasHadInteraction {
  BOOL has= (self.tag & MASK_HAD_INTERACTION)!=0;
  if(!has) {
    self.wantsFirstInteraction=YES;
  }
  return has;
}
-(UIView *)hitTest:(CGPoint)point withEvent:(UIEvent *)event {
  UIView* v=[super hitTest:point withEvent:event];
  if(v) {
    if((self.tag & MASK_HAD_INTERACTION)==0) {
      [self setHasHadInteraction];
    }
    if(self.wantsFirstInteraction) {
      self.wantsFirstInteraction=false;
      [self setNeedsDisplay];
      if(overlayView) {
        [ overlayView setNeedsDisplay];
      }
    }
  }
  return v;
}
- (void)startDisplayLink {
  if(!linkManager) {
    linkManager=[RAREDisplayLinkManager new];
  }
  [linkManager addView:self];
}

-(void) setSyncWithDisplayRefresh:(BOOL) enable {
  syncWithDisplayRefresh=enable;
  if(enable) {
    if(self.window) {
      [self startDisplayLink];
    }
  }
  else {
    if(!self.window) {
      [self stopDisplayLink];
    }
  }
}
- (void)stopDisplayLink
{
  if(linkManager) {
    [linkManager removeView:self];
  }
}

-(void)dealloc {
  [self stopDisplayLink];
}
-(void)touchesBegan:(NSSet *)touches withEvent:(UIEvent *)event {
  [self setHasHadInteraction];
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

-(void)touchesCancelled:(NSSet *)touches withEvent:(UIEvent *)event {
  if(self.tag & MASK_MOUSE_HANDLER) {
    RAREView* view=self.sparView;
    RAREMouseEvent* me=[[RAREMouseEventEx alloc] initWithId:view withId:event];
    [view->mouseListener_ mouseReleasedWithRAREMouseEvent:me];
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
  if(syncWithDisplayRefresh && self.window!=newWindow) {
    if(newWindow) {
      [self startDisplayLink];
    }
    else {
      [self stopDisplayLink];
    }
  }
}
@end
