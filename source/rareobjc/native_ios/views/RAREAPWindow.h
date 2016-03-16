//
//  RAREAPWindow.h
//  RareOSX
//
//  Created by Don DeCoteau on 3/22/13.
//  Copyright (c) 2013 SparseWare. All rights reserved.
//
#import <UIKit/UIKit.h>
@protocol RAREiPopupMenuListener;
@protocol RAREiWindowListener;
@protocol RAREiPlatformPainter;

@class  RAREWindow;
@class  RAREView;
@interface RAREAPWindow : UIWindow {
  bool enabled_;
  CGFloat alpha_;
  id<RAREiPopupMenuListener> popupMenuListener_;
  id<RAREiWindowListener> windowListener_;
  BOOL movable_;
  NSString* title_;
  CGSize fixedSize;
  BOOL modal_;
}
@property (nonatomic, retain) RAREWindow* sparWindow;
@property(nonatomic) UIInterfaceOrientation sparOrientation;

-(void) setPopupMenuListener: (id<RAREiPopupMenuListener> ) l;
-(void) setWindowListener: (id<RAREiWindowListener> ) l;
-(void) centerEx;
-(BOOL) isResizable;
-(void) setResizable: (BOOL) resizable;
-(void)disposeEx;
-(void) closeEx;
-(void) setVisible: (BOOL) visible;
-(BOOL) isVisible;
-(void) setEnabled: (BOOL) enabled;
-(BOOL) isEnabled;
-(NSString*) getTitle;
-(void) setTitle:(NSString*) title;
-(BOOL) isMovable;
-(void) setMovable: (BOOL) movable;
-(void) setSizeWidth: (float) width andHeight: (float) height;
-(void) setLocationX: (float)x andY: (float)y;
-(void) setContentView: (UIView*) view;
-(BOOL) isMainWindow;
-(BOOL) isModal;
-(BOOL) isFullScreen;
-(void) setModal: (BOOL) modal;
-(void) setRotating: (BOOL) rotating;
-(void) madeVisible;
-(void) addViewToGlass: (RAREView*) view;
-(void) removeViewFromGlass: (RAREView*) view;
-(void) moveByX: (CGFloat)x andY: (CGFloat) y;
-(void) setBackgroundPainter: (id<RAREiPlatformPainter>) painter;
-(CGRect) orientedFrame;
-(CGPoint) orientedLocation;
-(CGSize) orientedSize;

@end
