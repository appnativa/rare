//
//  RAREAPProgressIndicator.m
//  RareOSX
//
//  Created by Don DeCoteau on 3/25/13.
//  Copyright (c) 2013 SparseWare. All rights reserved.
//

#import "RAREAPProgressView.h"
#import "APView+Component.h"
#import "com/appnativa/rare/ui/event/MouseEventEx.h"
#import "com/appnativa/rare/ui/listener/iMouseListener.h"
#import "com/appnativa/rare/ui/listener/iMouseMotionListener.h"
#import "com/appnativa/rare/platform/apple/ui/view/ParentView.h"

@implementation RAREAPProgressView {
  int graphicSize_;
}

+ (Class)layerClass
{
  return [RARECAGradientLayer class];
}
- (id)initWithFrame:(CGRect)frame
{
    self = [super initWithFrame:frame];
    if (self) {
      maxValue=100;
    }
    
    return self;
}
-(CGSize)sizeThatFits:(CGSize)size {
  if(graphicSize_>0) {
    size=CGSizeMake(graphicSize_, graphicSize_);
  }
  else {
    size=[super sizeThatFits:size];
    if(size.height<1) {
      size.height=8;
    }
    if(size.width<32) {
      size.width=32;
    }
  }
  return size;
}
-(void) setGraphicSize: (int) size {
  graphicSize_=size;
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

-(void) setIndeterminate: (BOOL) indeterminate {
  
  indeterminate_=indeterminate;
}
-(BOOL) isIndeterminate {
  return indeterminate_;
}
-(void) setValue: (CGFloat) value {
  self.progress=(value/(maxValue-minValue));
}
-(void) setMaxValue: (CGFloat) value {
  maxValue=value;
  
}
-(void) setMinValue: (CGFloat) value {
  minValue=value;
}

-(CGFloat) getValue {
  return (maxValue-minValue)*self.progress;
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
}
@end
