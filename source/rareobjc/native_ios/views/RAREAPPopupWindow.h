//
//  RAREAPPopupWindow.h
//  RareOSX
//
//  Created by Don DeCoteau on 5/28/13.
//  Copyright (c) 2013 SparseWare. All rights reserved.
//

#import "RAREAPWindow.h"

@interface RAREAPPopupWindow : RAREAPWindow {
  BOOL transient_;
  NSTimer* timer;
  NSInteger timeout_;
  BOOL focusable_;
  BOOL canceled_;
}

@property (nonatomic) BOOL transient;
@property (nonatomic) BOOL focusable;
@property (nonatomic) NSInteger timeout;

-(void) setDecorated: (BOOL) decorated;
-(void) cancelPopup:(BOOL) useAnimation;
-(BOOL) outsideTouchHappened;
@end