//
//  RARE UIViewController.h
//  RareIOS
//
//  Created by Don DeCoteau on 5/21/13.
//  Copyright (c) 2013 SparseWare. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "RARERootView.h"
@interface RAREUIViewController : UIViewController {
  UIWindow* window;
}
- initWithWindow: (UIWindow*) win;
- (void) lockOrientation:( BOOL) landscape;
- (NSUInteger) getOrientationEx;
- (void) setOrientationEx: (NSUInteger) o ;
- (void) unlockOrientation;
- (void)disposeEx;
- (void)textFieldDidBeginEditing:(UITextField *)textField;
- (void)textFieldDidEndEditing:(UITextField *)textField;
- (void) setUseDarkStatusBarText: (BOOL) dark;
- (void) setStatusBarVisibleEx: (BOOL) visible;
- (void) showContentController: (UIViewController*) content parent: (UIView*) parent rect: (CGRect) frame;
- (void) hideContentController: (UIViewController*) content;

@end
