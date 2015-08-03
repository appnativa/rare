//
//  RAREAPWindow.m
//  RareOSX
//
//  Created by Don DeCoteau on 3/22/13.
//  Copyright (c) 2013 SparseWare. All rights reserved.
//

#import "RAREAPWindow.h"
#import "RAREAPApplication.h"
#import "RARERootView.h"
#import "com/appnativa/rare/ui/event/WindowEvent.h"
#import "com/appnativa/rare/ui/event/ExpansionEvent.h"
#import "com/appnativa/rare/ui/event/iWindowListener.h"
#import "com/appnativa/rare/ui/event/iPopupMenuListener.h"
#import "com/appnativa/rare/ui/iPlatformBorder.h"
#import "com/appnativa/rare/ui/UIInsets.h"
#import "APView+Component.h"
#import "RAREUIViewController.h"
#import "AppleHelper.h"
#import "com/appnativa/rare/platform/apple/ui/view/View.h"
#import "com/appnativa/rare/platform/apple/ui/util/AppleGraphics.h"
#import "com/appnativa/rare/ui/painter/iPlatformPainter.h"
#import "com/appnativa/rare/platform/apple/ui/util/AppleGraphics.h"
#import <com/appnativa/rare/ui/ColorUtils.h>
#import <com/appnativa/rare/ui/UIColor.h>


@implementation RAREAPWindow {
  id<RAREiPlatformPainter> painter_;
  UIColor* bgColor_;
  RAREUIInsets* borderInsets_;
  UIWindow* overlayWindow;

}

@synthesize sparOrientation;

-(id)initWithFrame:(CGRect)frame {
  self=[super initWithFrame:frame];
  if(self) {
    self.userInteractionEnabled=YES;
    enabled_=YES;
    alpha_=self.alpha;
    title_=@"";
    sparOrientation=[UIApplication sharedApplication].statusBarOrientation;
    self.clipsToBounds=YES;
    self.rootViewController=[[RAREUIViewController alloc] initWithWindow: self];
  }
  return self;
}
-(void)setBackgroundColor:(UIColor *)backgroundColor {
  bgColor_=nil;
  [super setBackgroundColor:backgroundColor];
}
-(void) setBackgroundPainter: (id<RAREiPlatformPainter>) painter {
  painter_=painter;
}
-(void)drawRect:(CGRect)rect {
  if(bgColor_) {
    RAREUIInsets* ins=borderInsets_;
    rect=[self bounds];
    rect.origin.x=0;
    rect.origin.y=0;
    if(ins) {
      rect.origin.x=ins->left_;
      rect.origin.y=ins->top_;
      rect.size.width-=(ins->left_+ins->right_);
      rect.size.height-=(ins->top_+ins->bottom_);
    }
    [bgColor_ set];
    UIRectFill(rect);
  }
}
-(BOOL) isMainWindow {
  return [[RAREAPApplication getInstance] getMainWindow]==self;
}
-(BOOL) isModal {
  return modal_;
}
-(void) setModal: (BOOL) modal {
  modal_=modal;
  if(modal) {
    self.windowLevel=UIWindowLevelAlert;
  }
}
-(void) setContentView: (UIView*) view {
  if(self.rootViewController) {
    self.rootViewController.view=view;
  }
  else {
    [self addSubview: view];
  }
}
-(CGPoint) orientedLocation {
  return [self orientedFrame].origin;
}
-(CGSize) orientedSize {
  CGSize size = self.bounds.size;
  if ([UIScreen osVersionAsFloat]<8) {
    UIApplication *application = [UIApplication sharedApplication];
    CGFloat f;
    switch(application.statusBarOrientation) {
      case UIInterfaceOrientationLandscapeRight:
      case UIInterfaceOrientationLandscapeLeft:
        f=size.width;
        size.width=size.height;
        size.height=f;
        break;
      default:
        break;
    }
  }
  return size;
}
-(CGRect) orientedFrame {
  CGRect frame = self.frame;
  if ([UIScreen osVersionAsFloat]<8) {
    UIApplication *application = [UIApplication sharedApplication];
    CGFloat f;
    CGSize ss=self.screen.bounds.size;
    switch(application.statusBarOrientation) {
      case UIInterfaceOrientationLandscapeRight:
        f=frame.origin.x;
        frame.origin.x=frame.origin.y;
        frame.origin.y=ss.width-frame.size.width-f;
        f=frame.size.width;
        frame.size.width=frame.size.height;
        frame.size.height=f;
        break;
      case UIInterfaceOrientationLandscapeLeft:
        f=frame.origin.x;
        frame.origin.x=ss.height-frame.size.height-frame.origin.y;
        frame.origin.y=f;
        f=frame.size.width;
        frame.size.width=frame.size.height;
        frame.size.height=f;
        break;
      case UIInterfaceOrientationPortraitUpsideDown:
        frame.origin.x=ss.width-frame.size.width-frame.origin.x;
        frame.origin.y=ss.height-frame.size.height-frame.origin.y;
        break;
      default:
        break;
    }
  }
  return frame;
}

-(void) moveByX: (CGFloat)x andY: (CGFloat) y {
  CGRect frame=self.frame;
  frame.origin.x+=x;
  frame.origin.y+=y;
  [super setFrame: frame];
}
-(void) setLocationX: (float)x andY: (float)y {
  CGRect frame=self.bounds;
  if ([UIScreen osVersionAsFloat]<8) {
    UIInterfaceOrientation io=[UIApplication sharedApplication].statusBarOrientation;
    UIScreen* s;
    if(self.window) {
      s=self.window.screen;
    }
    else {
      s=[UIScreen mainScreen];
    }
    CGSize ss=s.bounds.size;
    switch(io) {
      case UIInterfaceOrientationLandscapeLeft: {
        CGFloat xx=x;
        x=y;
        y=ss.height-frame.size.height-xx;
        break;
      }
      case UIInterfaceOrientationLandscapeRight: {
        CGFloat xx=x;
        x=ss.width-frame.size.width-y;
        y=xx;
        break;
      }
      case UIInterfaceOrientationPortraitUpsideDown:
        x=ss.width-x-frame.size.width;
        y=ss.height-y-frame.size.height;
        break;
      default:
        break;

    }
  }
  frame.origin.x=x;
  frame.origin.y=y;
  [super setFrame: frame];
}

-(void) centerEx {
  UIScreen* screen=self.screen;
  if(!screen) {
    screen=[UIScreen mainScreen];
  }
  CGSize s=screen.bounds.size;
  self.center= CGPointMake(s.width/2, s.height/2);
}
- (void)layoutSubviews {
  if(!bgColor_) {
    CGFloat r,g,b,a;
    UIColor* bg=self.backgroundColor;
    if(bg && [bg getRed:&r green:&g blue:&b alpha:&a]) {
      if(a!=0) {
        RAREView* view=self.rootViewController.view.sparView;
        id<RAREiPlatformBorder> b=[view getBorder];
        RAREUIInsets* ins=nil;
        if(b && ![b isPaintLast]) {
          ins=[b getBorderInsetsExWithRAREUIInsets:nil];
          [super setBackgroundColor:[UIColor clearColor]];
          bgColor_=bg;
          borderInsets_=ins;
        }
      }
    }
  }
  if(!self.rootViewController) {
    CGRect frame=self.bounds;
    for(UIView* v in self.subviews) {
      v.frame=frame;
    }
  }
  [super layoutSubviews];
}

-(void) setPopupMenuListener: (id<RAREiPopupMenuListener> ) l {
  popupMenuListener_=l;
}
-(void) setWindowListener: (id<RAREiWindowListener> ) l {
  windowListener_=l;
}
-(BOOL) isResizable {
  return false;
}
-(void) setResizable: (BOOL) resizable{
}

-(void)sparDispose {
  [self disposeEx];
}

-(void) disposeEx {
  self.hidden=YES;
  if([self.rootViewController isKindOfClass:[RAREUIViewController class]]) {
      [((RAREUIViewController*)self.rootViewController) disposeEx];
      self.rootViewController=nil;
  }
  windowListener_=nil;
  popupMenuListener_=nil;
  overlayWindow=nil;
}
-(void) closeEx {
  [self setVisible:NO];
}
-(void) setSizeWidth: (float) width andHeight: (float) height {
  CGRect frame=self.frame;
  if ([UIScreen osVersionAsFloat]<8) {
    if (UIInterfaceOrientationIsLandscape([UIApplication sharedApplication].statusBarOrientation)) {
      float w=width;
      width=height;
      height=w;
    }
  }
  frame.size.width=ceilf(width);
  frame.size.height=ceilf(height);
  [super setFrame: frame];
  fixedSize.width=ceilf(width);
  fixedSize.height=ceilf(height);
  [self setNeedsLayout];
  [self setNeedsDisplay];
}
-(void)setFrame:(CGRect)frame {
  if(fixedSize.height==0) {
    [super setFrame:frame];
  }
}

-(void) setVisible: (BOOL) visible {
  if(self.hidden==visible) {
    if( modal_ && visible) {
      if(visible) {
        overlayWindow= [[RAREAPWindow alloc] initWithFrame:[UIScreen mainScreen].bounds];
        overlayWindow.backgroundColor=(UIColor*)[RAREColorUtils DISABLED_TRANSPARENT_COLOR].getAPColor;
        overlayWindow.alpha=0;
        overlayWindow.hidden=NO;
        UIWindow *ow=overlayWindow;
        [UIView animateWithDuration:0.2 animations:^{
          ow.alpha = 1.0f;
        } completion:^(BOOL finished) {
        }];
      }
    }
    self.hidden=!visible;
    if(visible) {
      if(fixedSize.height) {
        CGSize size=self.frame.size;
        UIView* v=self.rootViewController.view;
        CGRect frame=v.frame;
        if(!CGSizeEqualToSize(frame.size,size)) {
          frame.size=size;
          v.frame=frame;
        }
      }
    }
    RAREAPApplication* app=[RAREAPApplication getInstance];
    if(visible) {
      [app addWindow: self];
      [self madeVisible];
    }
    else {
      if(overlayWindow) {
        overlayWindow.hidden=YES;
        overlayWindow=nil;
      }
      [app removeWindow: self];
    }
    if(windowListener_) {
      if(visible) {
        RAREWindowEvent* e=[[RAREWindowEvent alloc]initWithId:self withRAREWindowEvent_TypeEnum:[RAREWindowEvent_TypeEnum Opened]];
        [windowListener_ windowEventWithRAREWindowEvent:e];
      }
      else {
        RAREWindowEvent* e=[[RAREWindowEvent alloc]initWithId:self withRAREWindowEvent_TypeEnum:[RAREWindowEvent_TypeEnum Closed]];
        [windowListener_ windowEventWithRAREWindowEvent:e];
      }
    }
  }
}
-(void) madeVisible {
  [self makeKeyWindow];
}

-(void) setMovable: (BOOL) movable {
  movable_=movable;
}
-(BOOL) isMovable {
  return movable_;
}
-(BOOL) isVisible {
  return !self.hidden;
}
-(UIView *)hitTest:(CGPoint)point withEvent:(UIEvent *)event {
  if(!enabled_) {
    return nil;
  }
  UIView* v=[super hitTest:point withEvent:event];
  return v;
}
-(void) setEnabled: (BOOL) enabled {
  enabled_=enabled;
  if(enabled) {
    self.alpha=alpha_;
  }
  else {
    self.alpha=0.5;
  }
  
}
-(BOOL) isEnabled {
  return enabled_;
}
-(NSString*) getTitle {
  return title_;
}
-(void) setTitle:(NSString*) title {
  title_=title ? title : @"";
}
-(void) addViewToGlass: (RAREView*) view {
  UIView* v=(UIView*)view->proxy_;
  if(self.rootViewController) {
    UIView* mv=self.rootViewController.view;
    if([mv isKindOfClass:[RARERootView class]]) {
      [((RARERootView*)mv) addViewToGlass:v];
    }
  }
}
-(void) removeViewFromGlass: (RAREView*) view {
  UIView* v=(UIView*)view->proxy_;
  [v removeFromSuperview];
}


@end
