//
// Created by Don DeCoteau on 8/26/13.
// Copyright (c) 2013 SparseWare. All rights reserved.
//
// To change the template use AppCode | Preferences | File Templates.
//


#import <Foundation/Foundation.h>
#import <UIKit/UIGestureRecognizerSubclass.h>

typedef enum {
  RAREDirectionBoth,
  RAREDirectionVertical,
  RAREDirectionHorizontal
} RAREGestureDirection;

@protocol RAREiGestureListener;
@protocol RAREGestureListenerProtocol <NSObject>
-(void)sparDispose;
@end

@interface RAREFlingGestureRecognizer : UIPanGestureRecognizer <UIGestureRecognizerDelegate,RAREGestureListenerProtocol> {
  @package
  id<RAREiGestureListener> gestureListener_;
}
-(id) initWithListener:(id<RAREiGestureListener>) listener;
-(id) initWithListener:(id<RAREiGestureListener>) listener direction: (RAREGestureDirection) direction;
-(void)sparDispose;

@end

@interface RARELongPressGestureRecognizer : UILongPressGestureRecognizer  <UIGestureRecognizerDelegate,RAREGestureListenerProtocol>{
  @package
  id<RAREiGestureListener> gestureListener_;
}
-(id) initWithListener:(id<RAREiGestureListener>) listener;
-(void)sparDispose;
@end

@interface RARERotateGestureRecognizer : UIRotationGestureRecognizer  <UIGestureRecognizerDelegate,RAREGestureListenerProtocol>{
  @package
  id<RAREiGestureListener> gestureListener_;
}
-(id) initWithListener:(id<RAREiGestureListener>) listener;
-(void)sparDispose;
@end

@interface RAREScaleGestureRecognizer : UIPinchGestureRecognizer  <UIGestureRecognizerDelegate,RAREGestureListenerProtocol>{
  @package
  id<RAREiGestureListener> gestureListener_;
}
-(id) initWithListener:(id<RAREiGestureListener>) listener;
-(void)sparDispose;
@end

@interface RAREMouseHandlerGestureRecognizer : UIGestureRecognizer  <UIGestureRecognizerDelegate,RAREGestureListenerProtocol>{
}
-(void)sparDispose;
-(void) cancelLongPress;
@end

@interface RAREInteractionGestureRecognizer : UITapGestureRecognizer <UIGestureRecognizerDelegate,RAREGestureListenerProtocol>
-(void)sparDispose;
@end
@interface LongPressObject : NSObject 
-(id)initWithEvent:(UIEvent*) event;
-(void) cancel;
-(UIEvent*) getEvent;
@end
