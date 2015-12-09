//
//  RAREUIApplication.h
//  RareIOS
//
//  Created by Don DeCoteau on 6/2/13.
//  Copyright (c) 2013 SparseWare. All rights reserved.
//

#import <UIKit/UIKit.h>
@class RAREAPWindow;
@class RAREUIColor;
@interface RAREAPApplication : UIApplication {
  UIView* focusedView;
  UIWindow* keyWindow;
  UIWindow* mainWindow;
  BOOL manageKeyboard;
}
+(BOOL) amIBeingDebugged;
-(void) addWindow: (RAREAPWindow*) window;
-(void) setMainWindow: (RAREAPWindow*) window;
-(void) removeWindow: (RAREAPWindow*) window;
-(void) closePopupWindows: (BOOL) all;
+(RAREAPApplication*) getInstance;
+(UIView*) getFocusedView;
+(void) setFocusedView: (UIView*) view;
+(void) showModalView:(UIView*) view;
+(void) hideModalView:(UIView*) view;
+(void) showSpinnerView:(UIView*) view;
+(void) hideSpinnerView:(UIView*) view;
+(void) hideKeyboard:(UIView*) view;
-(UIWindow*) getMainWindow;
+(void) showSplashScreenView:(UIView *) view;
+(void) showSplashScreenWithPortraitImage:(UIImage *)portrait andLandscapeImage:(UIImage *)landscape andMessage: (NSString*) message foreground: (UIColor*) fg;
-(BOOL) isKeyboardVisible;
+(BOOL) isFirstTextFieldCheck;
+(BOOL) isManageKeyboard;
+(void) setManageKeyboard: (BOOL) manage ;
-(CGFloat) getKeyboardHeight;

@end
