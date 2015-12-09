//
// Created by Don DeCoteau on 8/26/13.
// Copyright (c) 2013 SparseWare. All rights reserved.
//
// To change the template use AppCode | Preferences | File Templates.
//


#import "APView+Component.h"
#import "RAREGestures.h"
#import "RAREAPListView.h"
#import <com/appnativa/rare/ui/iGestureListener.h>
#import <com/appnativa/rare/ui/event/MouseEventEx.h>
#import <com/appnativa/rare/ui/event/UITouch.h>
#import <com/appnativa/rare/ui/event/ScaleEvent.h>
#import <com/appnativa/rare/ui/event/ScaleGestureObject.h>
#import <com/appnativa/rare/ui/event/RotationEvent.h>
#import "com/appnativa/rare/ui/listener/iMouseListener.h"
#import "com/appnativa/rare/ui/listener/iMouseMotionListener.h"
#import <com/appnativa/rare/platform/apple/ui/view/View.h>
#import <com/appnativa/rare/ui/table/TableComponent.h>
#include "com/appnativa/rare/Platform.h"
#include "com/appnativa/rare/ui/UIProperties.h"

#include "com/appnativa/rare/platform/EventHelper.h"
@implementation RAREFlingGestureRecognizer {
  float vx;
  float vy;
  RAREUITouch* startEvent_;
  RAREUITouch* endEvent_;
  RAREGestureDirection direction_;
  BOOL directionChecked_;
}
-(id) initWithListener:(id<RAREiGestureListener>) listener {
  return [self initWithListener:listener direction:RAREDirectionBoth];
}
-(id) initWithListener:(id<RAREiGestureListener>) listener direction: (RAREGestureDirection) direction {
  if(self=[super init]) {
    gestureListener_=listener;
    [self addTarget:self action:@selector(gestureRecognized:)];
    self.delegate=self;
    self.maximumNumberOfTouches=1;
    self.minimumNumberOfTouches=1;
    direction_=direction;
  }
  return self;
}
-(void)sparDispose {
  [self removeTarget:self action:@selector(gestureRecognized:)];
  startEvent_=nil;
  endEvent_=nil;
  gestureListener_=nil;
  self.delegate=nil;
}
-(void) gestureRecognized:(UIGestureRecognizer *) recognizer {
  CGPoint p= [self velocityInView:recognizer.view];
  vx=p.x;
  vy=p.y;
  switch(recognizer.state) {
    case UIGestureRecognizerStateRecognized: {
      RAREView* view=self.view.sparView;
      RAREMouseEvent* e1=[[RAREMouseEventEx alloc] initWithId:view withId:startEvent_ withInt:RAREMouseEvent_FLING];
      RAREMouseEvent* e2=[[RAREMouseEventEx alloc] initWithId:view withId:endEvent_ withInt:RAREMouseEvent_FLING];
      [gestureListener_ onFlingWithId:view withRAREMouseEvent:e1 withRAREMouseEvent:e2 withFloat:vx withFloat:vy];
      break;
    }
    default:
      break;
  }
}
- (void)touchesMoved:(NSSet *)touches withEvent:(UIEvent *)event {
  [super touchesMoved:touches withEvent:event];
  UIView* view=self.view;
  if([view isKindOfClass:[UIScrollView class]] && ((UIScrollView*)view).dragging) {
    self.state=UIGestureRecognizerStateFailed;
  }
  else {
    if(directionChecked_ || self.state!=UIGestureRecognizerStateBegan) return;
    CGPoint p= [self velocityInView:view];
    switch(direction_) {
      case RAREDirectionHorizontal:
        if(fabs(p.y)>fabs(p.x)) {
          self.state=UIGestureRecognizerStateFailed;
        }
        break;
      case RAREDirectionVertical:
        if(fabs(p.x)>fabs(p.y)) {
          self.state=UIGestureRecognizerStateFailed;
        }
        break;
      default:
        break;
    }
  }
  directionChecked_=YES;
}
-(void)touchesEnded:(NSSet *)touches withEvent:(UIEvent *)event {
  endEvent_=[RAREEventHelper getTouchWithId:self.view.sparView withId:event];
  [super touchesEnded:touches withEvent:event];
}
- (void)touchesBegan:(NSSet *)touches withEvent:(UIEvent *)event {
  startEvent_=[RAREEventHelper getTouchWithId:self.view.sparView withId:event];
  endEvent_=startEvent_;
  vx=0;
  vy=0;
  directionChecked_=direction_==RAREDirectionBoth;
  [super touchesBegan:touches withEvent:event];
}

- (BOOL)gestureRecognizer:(UIGestureRecognizer *)gestureRecognizer shouldRecognizeSimultaneouslyWithGestureRecognizer:(UIGestureRecognizer *)otherGestureRecognizer {
  return YES;
}

@end

@implementation RAREScaleGestureRecognizer {
  RAREScaleGestureObject *gesture;
}
-(id) initWithListener:(id<RAREiGestureListener>) listener {
  if(self=[super init]) {
    gestureListener_=listener;
    self.cancelsTouchesInView=NO;
    [self addTarget:self action:@selector(gestureRecognized:)];
    self.delegate=self;
  }
  return self;
}
-(void)sparDispose {
  [self removeTarget:self action:@selector(gestureRecognized:)];
  gesture=nil;
  gestureListener_=nil;
  self.delegate=nil;
}

-(void)touchesMoved:(NSSet *)touches withEvent:(UIEvent *)event {
  [super touchesMoved:touches withEvent:event];
  UIView* view=self.view;
  if([view isKindOfClass:[UIScrollView class]] && ((UIScrollView*)view).dragging) {
    self.state=UIGestureRecognizerStateFailed;
  }
}
-(void) gestureRecognized:(UIGestureRecognizer *) recognizer {
  int type;
  if(!gesture) {
    gesture=[RAREScaleGestureObject new];
  }
  switch(recognizer.state) {
    case UIGestureRecognizerStateBegan:
      type=RAREiGestureListener_SCALE_BEGIN;
      break;
    case UIGestureRecognizerStateEnded:
      type= RAREiGestureListener_SCALE_END;
      break;
    case UIGestureRecognizerStateCancelled:
      type= RAREiGestureListener_SCALE_CANCELED;
      break;
    case UIGestureRecognizerStateChanged:
      type= RAREiGestureListener_SCALE;
      break;
    default:
      return;
  }
  CGPoint p=[self locationInView:recognizer.view];
  gesture->focusX_=p.x;
  gesture->focusY_=p.y;
  if(self.numberOfTouches>1) {
    p= [self locationOfTouch:0 inView:self.view];
    CGPoint p2= [self locationOfTouch:1 inView:self.view];
    p.x=ABS(p2.x-p.x);
    p.y=ABS(p2.y-p.y);
    [gesture setCurrentSpanWithFloat:p.x withFloat:p.y];
  }
  [gesture setScaleFactorWithFloat:self.scale];
  gesture->velocity_=self.velocity;
  RAREView* view=self.view.sparView;
  [gestureListener_ onScaleEventWithId:view withInt:type withId:gesture withFloat: self.scale];
}
- (BOOL)gestureRecognizer:(UIGestureRecognizer *)gestureRecognizer shouldRecognizeSimultaneouslyWithGestureRecognizer:(UIGestureRecognizer *)otherGestureRecognizer {
  return YES;
}

@end

@implementation RARERotateGestureRecognizer {
}
-(id) initWithListener:(id<RAREiGestureListener>) listener {
  if(self=[super init]) {
    gestureListener_=listener;
    self.cancelsTouchesInView=NO;
    self.delegate=self;
    [self addTarget:self action:@selector(gestureRecognized:)];
  }
  return self;
}
-(void)sparDispose {
  [self removeTarget:self action:@selector(gestureRecognized:)];
  gestureListener_=nil;
  self.delegate=nil;
}

-(void) gestureRecognized:(UIGestureRecognizer *) recognizer {
  
  int type;
  switch(recognizer.state) {
    case UIGestureRecognizerStateBegan:
      type=RARERotationEvent_ROTATION_BEGIN;
      break;
    case UIGestureRecognizerStateEnded:
    case UIGestureRecognizerStateCancelled:
      type= RARERotationEvent_ROTATION_END;
      break;
    case UIGestureRecognizerStateChanged:
      type= RARERotationEvent_ROTATION;
      break;
    default:
      return;
  }
  RAREView* view=self.view.sparView;
  CGPoint p=[self locationInView:recognizer.view];
  [gestureListener_ onRotateWithId:view withInt:type withFloat:self.rotation withFloat:self.velocity withFloat:p.x withFloat:p.y];
  
}
- (BOOL)gestureRecognizer:(UIGestureRecognizer *)gestureRecognizer shouldRecognizeSimultaneouslyWithGestureRecognizer:(UIGestureRecognizer *)otherGestureRecognizer {
  return YES;
}

-(void)touchesMoved:(NSSet *)touches withEvent:(UIEvent *)event {
  [super touchesMoved:touches withEvent:event];
  UIView* view=self.view;
  if([view isKindOfClass:[UIScrollView class]] && ((UIScrollView*)view).dragging) {
    self.state=UIGestureRecognizerStateFailed;
  }
}

@end
@implementation RARELongPressGestureRecognizer {
  BOOL fire;
}
-(id) initWithListener:(id<RAREiGestureListener>) listener {
  if(self=[super init]) {
    gestureListener_=listener;
    self.delegate=self;
    [self addTarget:self action:@selector(gestureRecognized:)];
    self.cancelsTouchesInView=NO;
    self.numberOfTouchesRequired=1;
  }
  return self;
}
-(void)sparDispose {
  [self removeTarget:self action:@selector(gestureRecognized:)];
  gestureListener_=nil;
  self.delegate=nil;
}
- (void)touchesBegan:(NSSet *)touches withEvent:(UIEvent *)event {
  fire=NO;
  [super touchesBegan:touches withEvent:event];
}
- (void)touchesEnded:(NSSet *)touches withEvent:(UIEvent *)event {
  [super touchesEnded:touches withEvent:event];
  if(fire) {
    fire=NO;
    RAREView* view=self.view.sparView;
    RAREMouseEvent* e=[[RAREMouseEventEx alloc] initWithId:view withId:event withInt:RAREMouseEvent_LONG_PRESS];
    [gestureListener_ onLongPressWithId:view withRAREMouseEvent:e];
  }
  
}
-(void)touchesMoved:(NSSet *)touches withEvent:(UIEvent *)event {
  [super touchesMoved:touches withEvent:event];
  UIView* view=self.view;
  if([view isKindOfClass:[UIScrollView class]] && ((UIScrollView*)view).dragging) {
    self.state=UIGestureRecognizerStateFailed;
  }
}

-(void) gestureRecognized:(UIGestureRecognizer *) recognizer {
  switch(recognizer.state) {
    case UIGestureRecognizerStateEnded:
      fire=YES;
      break;
    default:
      break;
  }
}
- (BOOL)gestureRecognizer:(UIGestureRecognizer *)gestureRecognizer shouldRecognizeSimultaneouslyWithGestureRecognizer:(UIGestureRecognizer *)otherGestureRecognizer {
  return YES;
}

@end
@implementation RAREMouseHandlerGestureRecognizer {
  CGFloat lpSlop;
  CGFloat sx;
  CGFloat sy;
  CGFloat lpTime;
  LongPressObject* lpObject;
  BOOL releasedCalled;
}
- (id)init {
  if(self=[super init]) {
    self.delaysTouchesBegan=NO;
    self.delaysTouchesEnded=NO;
    self.cancelsTouchesInView=NO;
    self.delegate=self;
    [self addTarget:self action:@selector(gestureRecognized:)];
    lpTime=[[RAREPlatform getUIDefaults] getIntWithNSString:@"Rare.Pointer.longPressThreshold" withInt: 500]/1000.0;
    lpSlop=[[RAREPlatform getUIDefaults] getIntWithNSString:@"Rare.Pointer.longPressSlop" withInt: 10];
  }
  return self;
  
}
-(void)sparDispose {
  [self removeTarget:self action:@selector(gestureRecognized:)];
  self.delegate=nil;
  [self cancelLongPress];
}
-(void) cancelLongPress {
  if(lpObject) {
    [lpObject cancel];
    [NSObject cancelPreviousPerformRequestsWithTarget:self selector:@selector(longPress:) object:lpObject];
    lpObject=nil;
  }
}
- (BOOL)gestureRecognizer:(UIGestureRecognizer *)gestureRecognizer shouldReceiveTouch:(UITouch *)touch {
  UIView* v=touch.view;
  UIView* sv=self.view;
  if(v==sv) {
    return YES;
  }
  RAREView* view=v.sparView;
  if(!view) {
    while(!view && v) {
      v=v.superview;
      view =v==nil ? nil : v.sparView;
    }
  }
  if(!view || !view.isMouseTransparent) {
    return NO;
  }
  while(view) {
    if(!view.isMouseTransparent) {
      break;
    }
    view=view.getParent;
  }
  return view!=nil && view.getProxy==sv;
}
-(void)reset {
  [super reset];
  [self cancelLongPress];
  RAREView* view=self.view.sparView;
  if(!releasedCalled && view && view->mouseListener_){
    [view->mouseListener_ pressCanceledWithRAREMouseEvent:nil];
  }
}
-(void)touchesBegan:(NSSet *)touches withEvent:(UIEvent *)event {
  RAREView* view=self.view.sparView;
  releasedCalled=NO;
  if(lpObject) {
    [self cancelLongPress];
  }
  if(view && view->mouseListener_){
    RAREMouseEvent* me=[[RAREMouseEventEx alloc] initWithId:view withId:event];
    [view->mouseListener_ mousePressedWithRAREMouseEvent:me];
    if(me.isConsumed) {
      self.state=UIGestureRecognizerStateBegan;
      return;
    }
    if(view->mouseListener_.wantsLongPress) {
      NSSet* set=[event allTouches];
      UITouch* touch=[set anyObject];
      CGPoint local_point = [touch locationInView:nil];
      sx=local_point.x;
      sy=local_point.y;
      lpObject=[[LongPressObject alloc] initWithEvent:event];
      [self performSelector:@selector(longPress:) withObject:lpObject afterDelay:lpTime];
    }
  }
  self.state=UIGestureRecognizerStatePossible;
}

-(void)touchesEnded:(NSSet *)touches withEvent:(UIEvent *)event {
  releasedCalled=YES;
  if(lpObject) {
    [self cancelLongPress];
  }
  RAREView* view=self.view.sparView;
  if(view && view->mouseListener_){
    RAREMouseEvent* me=[[RAREMouseEventEx alloc] initWithId:view withId:event];
    [view->mouseListener_ mouseReleasedWithRAREMouseEvent:me];
    if(me.isConsumed) {
      self.state=UIGestureRecognizerStateEnded;
      return;
    }
  }
  self.state=UIGestureRecognizerStateEnded;
}

-(void)touchesCancelled:(NSSet *)touches withEvent:(UIEvent *)event {
  if(lpObject) {
    [self cancelLongPress];
  }
  RAREView* view=self.view.sparView;
  if(view && view->mouseListener_){
    RAREMouseEvent* me=[[RAREMouseEventEx alloc] initWithId:view withId:event withInt:RAREMouseEvent_PRESS_CANCELED];
    [view->mouseListener_ pressCanceledWithRAREMouseEvent:me];
  }
}
-(void)touchesMoved:(NSSet *)touches withEvent:(UIEvent *)event {
  if(lpObject) {
    NSSet* set=[event allTouches];
    UITouch* touch=[set anyObject];
    CGPoint local_point = [touch locationInView:nil];
    if(ABS(sx-local_point.x)>lpSlop || ABS(sy-local_point.y)>lpSlop) {
      [self cancelLongPress];
    }
  }
  
  RAREView* view=self.view.sparView;
  if(view && view->mouseMotionListener_){
    RAREMouseEvent* me=[[RAREMouseEventEx alloc] initWithId:view withId:event];
    [view->mouseMotionListener_ mouseDraggedWithRAREMouseEvent:me];
    if(me.isConsumed) {
      return;
    }
  }
}

-(void) longPress:(id) lpobject  {
  if(lpObject!=lpobject) {
    return;
  }
  lpObject=nil;
  UIEvent* event=[((LongPressObject*)lpobject) getEvent];
  if(!event || self.state==UIGestureRecognizerStateEnded) {
    return;
  }
  UIView* v=self.view;
  if([v isKindOfClass:[UIScrollView class]]) {
    if([(UIScrollView*)v isDragging]) {
      return;
    }
  }
  RAREView* view=self.view.sparView;
  if(view && view->mouseListener_){
    RAREMouseEvent* me=[[RAREMouseEventEx alloc] initWithId:view withId:event withInt:RAREMouseEvent_LONG_PRESS];
    if([v isKindOfClass:[RAREAPListView class]]) {
      [(RAREAPListView*)v setContextMenuIndexWithRAREMouseEvent:me];
    }
    [view->mouseListener_ mouseReleasedWithRAREMouseEvent:me];
    if([me isConsumed]) {
      self.state=UIGestureRecognizerStateEnded;
      [self cancelsTouchesInView];
      [v touchesCancelled:[event allTouches] withEvent:event];

    }
  }
  
}
-(void) gestureRecognized:(UIGestureRecognizer *) recognizer {
}

@end

@implementation RAREInteractionGestureRecognizer

-(id) init{
  if(self=[super init]) {
    self.delegate=self;
    [self addTarget:self action:@selector(gestureRecognized:)];
    self.cancelsTouchesInView=NO;
    self.numberOfTouchesRequired=1;
  }
  return self;
}
-(void)sparDispose {
  [self removeTarget:self action:@selector(gestureRecognized:)];
  self.delegate=nil;
}
- (void)touchesBegan:(NSSet *)touches withEvent:(UIEvent *)event {
  [super touchesBegan:touches withEvent:event];
  self.state=UIGestureRecognizerStateBegan;
  self.state=UIGestureRecognizerStateEnded;
}
-(void) gestureRecognized:(UIGestureRecognizer *) recognizer {
  RAREView* view=self.view.sparView;
  [self.view setHasHadInteraction];
  if(view) {
    [view hadInteraction];
  }
}
- (BOOL)gestureRecognizer:(UIGestureRecognizer *)gestureRecognizer shouldRecognizeSimultaneouslyWithGestureRecognizer:(UIGestureRecognizer *)otherGestureRecognizer {
  return YES;
}
@end

@implementation LongPressObject {
  UIEvent* event_;
}
-(void) cancel {
  event_=nil;
}
-(UIEvent*) getEvent {
  return event_;
}
-(id)initWithEvent:(UIEvent*) event {
  self=[super init];
  if(self) {
    event_=event;
  }
  return self;
}
@end

