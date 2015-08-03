//
//  RAREAPPopupWindow.m
//  RareOSX
//
//  Created by Don DeCoteau on 5/28/13.
//  Copyright (c) 2013 SparseWare. All rights reserved.
//

#import <com/appnativa/rare/ui/ColorUtils.h>
#import <com/appnativa/rare/ui/UIColor.h>
#import "RAREAPPopupWindow.h"
#import "com/appnativa/rare/ui/event/WindowEvent.h"
#import "com/appnativa/rare/ui/event/ExpansionEvent.h"
#import "com/appnativa/rare/ui/event/iWindowListener.h"
#import "com/appnativa/rare/ui/event/iPopupMenuListener.h"
#import "RAREUIViewController.h"
#import "com/appnativa/rare/platform/apple/ui/view/Window.h"

@implementation RAREAPPopupWindow

@synthesize timeout=timeout_;
@synthesize focusable=focusable_;
@synthesize transient=transient_;
-(id)initWithFrame:(CGRect)frame {
  self=[super initWithFrame:frame];
  if(self) {
  }
  return self;
}
-(void)disposeEx {
  if(timer) {
    [timer invalidate];
    timer=nil;
  }
  [super disposeEx];
}
-(BOOL)canBecomeKeyWindow {
  return focusable_;
}
-(void)drawRect:(CGRect)rect {
  [super drawRect:rect];
}

-(void)onTimeout:(NSTimer *)timer {
  [self cancelPopup:YES];
}
-(BOOL) outsideTouchHappened {
  RAREWindow* win=self.sparWindow;
  return [win handleOutsideTouch];
}
-(id)init {
  self=[super init];
  if(self) {
    
  }
  return self;
}

-(void) setDecorated: (BOOL) decorated {
  
}
-(void) madeVisible {

}
-(void)setHidden:(BOOL)hidden {
  if(self.hidden!=hidden) {
    [super setHidden:hidden];
    if(popupMenuListener_) {
      RAREExpansionEvent* e=[[RAREExpansionEvent alloc] initWithId:self];
      if(!hidden) {
        [popupMenuListener_ popupMenuWillBecomeVisibleWithRAREExpansionEvent:e];
      }
      else {
        if(canceled_) {
          [popupMenuListener_ popupMenuCanceledWithRAREExpansionEvent: e];
        }
        [popupMenuListener_ popupMenuWillBecomeInvisibleWithRAREExpansionEvent:e];
      }
    }
    if(timeout_>0) {
      if(hidden) {
        if(timer) {
          [timer invalidate];
          timer=nil;
        }
      }
      else {
        [NSTimer scheduledTimerWithTimeInterval:timeout_
                                         target:self
                                       selector:@selector(onTimeout:)
                                       userInfo:nil
                                        repeats:NO];
      }
    }
    
  }
  canceled_=NO;
}
//-(void) setVisible: (BOOL) visible {
//  if(self.hidden==visible) {
//  }
//}

-(BOOL)isOpaque {
  return NO;
}
-(void) cancelPopup: (BOOL) useAnimation {
  canceled_=YES;
  RAREWindow* win=self.sparWindow;
  if(useAnimation) {
    [win setVisibleWithBoolean:NO];
  }
  else {
    [win setVisibleExWithBoolean:NO];
  }
}

-(void) packWithX: (CGFloat) x andY: (CGFloat) y {
  [self sizeToFit];
  CGRect frame= [self frame];
  if (UIInterfaceOrientationIsLandscape([UIApplication sharedApplication].statusBarOrientation)) {
    frame.origin.x=y;
    frame.origin.y=x;
  }
  else {
    frame.origin.x=x;
    frame.origin.y=y;
  }
  [self setFrame:frame];
}
@end
