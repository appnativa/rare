//
//  RAREUIApplication.m
//  RareIOS
//
//  Created by Don DeCoteau on 6/2/13.
//  Copyright (c) 2013 SparseWare. All rights reserved.
//
#include <assert.h>
#include <stdbool.h>
#include <sys/types.h>
#include <unistd.h>
#include <sys/sysctl.h>

#import <com/appnativa/rare/viewer/WindowViewer.h>
#import <com/appnativa/rare/ui/WaitCursorHandler.h>
#import <com/appnativa/rare/platform/aRare.h>
#import "RAREAPApplication.h"
#import "RAREAPPopupWindow.h"
#import "APView+Component.h"
#import "com/appnativa/rare/Platform.h"
#import "com/appnativa/rare/platform/apple/AppContext.h"
#import "com/appnativa/rare/platform/PlatformHelper.h"
#import "com/appnativa/rare/platform/apple/ui/view/Window.h"
#import "RARESplashView.h"
#import "RAREUIViewController.h"
#import <com/appnativa/rare/ui/ColorUtils.h>
#import <com/appnativa/rare/ui/UIColor.h>

static const CGFloat PORTRAIT_KEYBOARD_HEIGHT = 216;
static const CGFloat LANDSCAPE_KEYBOARD_HEIGHT = 162;

@implementation RAREAPApplication {
  RAREAPWindow* splashWindow;
  BOOL consumed_;
  CGFloat keyboardHeight;
  BOOL keyboardVisible;
  BOOL firstFieldCheck;
  BOOL paused;
  NSMutableArray* windowStack;
}

// Returns true if the current process is being debugged (either
// running under the debugger or has a debugger attached post facto).
+(BOOL) amIBeingDebugged {
  
#if DEBUG
  int                 junk;
  int                 mib[4];
  struct kinfo_proc   info;
  size_t              size;
  
  // Initialize the flags so that, if sysctl fails for some bizarre
  // reason, we get a predictable result.
  
  info.kp_proc.p_flag = 0;
  
  // Initialize mib, which tells sysctl the info we want, in this case
  // we're looking for information about a specific process ID.
  
  mib[0] = CTL_KERN;
  mib[1] = KERN_PROC;
  mib[2] = KERN_PROC_PID;
  mib[3] = getpid();
  
  // Call sysctl.
  
  size = sizeof(info);
  junk = sysctl(mib, sizeof(mib) / sizeof(*mib), &info, &size, NULL, 0);
  assert(junk == 0);
  
  // We're being debugged if the P_TRACED flag is set.
  
  return ( (info.kp_proc.p_flag & P_TRACED) != 0 );
#else
  return NO;
#endif
}

+(RAREAPApplication*) getInstance {
  return (RAREAPApplication*)[UIApplication sharedApplication];
}
+(void) showModalView:(UIView*) view {
  RAREAPApplication* app=[RAREAPApplication getInstance];
  if(!app->splashWindow) {
    RAREAPPopupWindow *window= [[RAREAPPopupWindow alloc] initWithFrame:[UIScreen mainScreen].bounds];
    [window setModal:YES];
    [window.rootViewController.view addSubview: view];
    [window makeKeyAndVisible];
  }
}

+(void) showSpinnerView:(UIView*) view {
  RAREAPApplication* app=[RAREAPApplication getInstance];
  if(!app->splashWindow) {
    RAREAPPopupWindow *window= [[RAREAPPopupWindow alloc] initWithFrame:[UIScreen mainScreen].bounds];
    [window.rootViewController.view addSubview: view];
    window.window.windowLevel=UIWindowLevelAlert;
    [window setVisible:YES];
  }
}

+(void) showSplashScreenView:(UIView *) view{
  RAREAPApplication* app=[RAREAPApplication getInstance];
  RAREAPPopupWindow *window= [[RAREAPPopupWindow alloc] initWithFrame:[UIScreen mainScreen].bounds];
  RARESplashViewController* vc=[[RARESplashViewController alloc] init];
  window.rootViewController=vc;
  vc.view=view;
  app->splashWindow=window;
  [window makeKeyAndVisible];
  
}
+(void) showSplashScreenWithPortraitImage:(UIImage *)portrait andLandscapeImage:(UIImage *)landscape andMessage: (NSString*) message foreground: (UIColor*) fg{
  RARESplashView *view= [[RARESplashView alloc] initWithPortraitImage:portrait andLandscapeImage:landscape andMessage: message];
  [view setColor:fg];
  [self showSplashScreenView:view];
}
+(void) hideSpinnerView:(UIView*) view {
  [self hideModalView:view];
}
+(void) hideModalView:(UIView*) view {
  RAREAPApplication* app=[RAREAPApplication getInstance];
  if([view.window isKindOfClass:[RAREAPPopupWindow class]] && !app->splashWindow) {
    RAREAPPopupWindow *window=(RAREAPPopupWindow*)view.window;
    [window setVisible:NO];
    [window sparDispose];
  }
}

+(BOOL)isFirstTextFieldCheck {
  RAREAPApplication* app=[RAREAPApplication getInstance];
  BOOL first=app->firstFieldCheck;
  if(first) {
    app->firstFieldCheck=NO;
  }
  return first;
}

+(UIView*) getFocusedView {
  RAREAPApplication* app=[RAREAPApplication getInstance];
  UIView* fc=app->focusedView;
  if((fc && fc.isFirstResponder) || !app->keyWindow) {
    return fc;
  }
  fc=app->keyWindow.findFirstResponder;
  app->focusedView=fc;
  return fc;
}
+(void) hideKeyboard: (UIView *) view {
  UIView* fv=nil;
  if(view && [view isFirstResponder]) {
    fv=view;
  }
  else {
    fv=[RAREAPApplication getFocusedView];
  }
  if(fv) {
    UIWindow* window=fv.window;
    UIViewController *rvc = window.rootViewController;
    if([rvc isKindOfClass:[RAREUIViewController class]]) {
      [(RAREUIViewController*)rvc resetScrolledContent:NO];
    }
    [fv resignFirstResponder];
  }
}
+(void) setFocusedView: (UIView*) view {
  RAREAPApplication* app=[RAREAPApplication getInstance];
  app->focusedView=view;
  RAREView* sv=view ? view.sparView : nil;
  RAREAppContext* ctx=[RAREAppContext getContext];
  [ctx setPermanentFocusOwnerWithRAREiPlatformComponent:sv ?[RAREPlatform findPlatformComponentWithId: sv]:  nil];
}
+(BOOL) isManageKeyboard {
  RAREAPApplication* app=[RAREAPApplication getInstance];
  return app->keyboardVisible && app->manageKeyboard;
}
+(void) setManageKeyboard: (BOOL) manage {
  RAREAPApplication* app=[RAREAPApplication getInstance];
  app->manageKeyboard=manage;
}

-(id)init {
  self=[super init];
  if(self) {
    [[NSNotificationCenter defaultCenter] addObserver:self
                                             selector:@selector(orientationWillChange:)
                                                 name:UIApplicationWillChangeStatusBarOrientationNotification
                                               object:nil];
    [[NSNotificationCenter defaultCenter] addObserver:self
                                             selector:@selector(orientationChanged:)
                                                 name:UIApplicationDidChangeStatusBarOrientationNotification
                                               object:nil];
    
    [[NSNotificationCenter defaultCenter] addObserver:self
                                             selector:@selector(windowBecameKey:)
                                                 name:UIWindowDidBecomeKeyNotification
                                               object:nil];
    [[NSNotificationCenter defaultCenter] addObserver:self
                                             selector:@selector(windowResignedKey:)
                                                 name:UIWindowDidResignKeyNotification
                                               object:nil];
    [[NSNotificationCenter defaultCenter] addObserver:self
                                             selector:@selector(windowBecameKey:)
                                                 name:UIWindowDidBecomeKeyNotification
                                               object:nil];
    [[NSNotificationCenter defaultCenter] addObserver:self
                                             selector:@selector(appResumed:)
                                                 name:UIApplicationDidBecomeActiveNotification
                                               object:nil];
    [[NSNotificationCenter defaultCenter] addObserver:self
                                             selector:@selector(appPaused:)
                                                 name:UIApplicationWillResignActiveNotification
                                               object:nil];
    [[NSNotificationCenter defaultCenter] addObserver:self
                                             selector:@selector(keyboardWillShow:)
                                                 name:UIKeyboardWillShowNotification
                                               object:nil];
    
    [[NSNotificationCenter defaultCenter] addObserver:self
                                             selector:@selector(keyboardWillHide:)
                                                 name:UIKeyboardWillHideNotification
                                               object:nil];
    firstFieldCheck=YES;
    manageKeyboard=[[UIDevice currentDevice] userInterfaceIdiom]==UIUserInterfaceIdiomPhone;
    windowStack=[NSMutableArray arrayWithCapacity:4];
  }
  return self;
  
}
-(CGFloat) getKeyboardHeight {
  if(keyboardVisible) {
    return keyboardHeight;
  }
  else {
    UIInterfaceOrientation orientation =
    [[UIApplication sharedApplication] statusBarOrientation];
    if (orientation == UIInterfaceOrientationPortrait ||
        orientation == UIInterfaceOrientationPortraitUpsideDown)
    {
      return PORTRAIT_KEYBOARD_HEIGHT;
    }
    else {
      return LANDSCAPE_KEYBOARD_HEIGHT;
    }
    
  }
}
- (void) keyboardWillHide:(NSNotification *)notification {
  keyboardVisible=NO;
  if(manageKeyboard && focusedView && [focusedView.window.rootViewController isKindOfClass:[RAREUIViewController class]]) {
    [((RAREUIViewController*)focusedView.window.rootViewController) resetScrolledContent:YES];
  }
}
- (void)keyboardWillShow:(NSNotification *)notification
{
  UIView* v=[RAREAPApplication getFocusedView];
  keyboardVisible=YES;
  keyboardHeight = [[[notification userInfo] objectForKey:UIKeyboardFrameBeginUserInfoKey] CGRectValue].size.height;
  if(manageKeyboard && v && [v.window.rootViewController isKindOfClass:[RAREUIViewController class]]) {
    [((RAREUIViewController*)v.window.rootViewController) scrollContentForResponder:v];
  }

}
-(BOOL) isKeyboardVisible {
  return keyboardVisible;
}
- (void)windowBecameKey:(NSNotification *)notification{
  keyWindow=notification.object;
}
- (void)windowResignedKey:(NSNotification *)notification {
  if(keyWindow==notification.object) {
    keyWindow=nil;
  }
  if(splashWindow && notification.object==splashWindow) {
    dispatch_after(dispatch_time(DISPATCH_TIME_NOW, (int64_t)(1 * NSEC_PER_SEC)), dispatch_get_main_queue(), ^{
      splashWindow.hidden=YES;
      UIView* rv=splashWindow.rootViewController.view;
      splashWindow.rootViewController.view=nil;
      if(rv) {
        NSArray* a=rv.subviews;
        if(a.count==1) {
          UIView* v=(UIView*)[a objectAtIndex:0];
          [v removeFromSuperview];
          [v sparDispose];
        }
      }
      [splashWindow disposeEx];
      splashWindow=nil;
    });
  }
  
}
- (void)appPaused:(NSNotification *)notification {
  paused=YES;
  RAREAppContext* a=(RAREAppContext*)[RAREPlatform getAppContext];
  [a->RARE_ fireApplicationPaused];
}
- (void)appResumed:(NSNotification *)notification {
  if(paused) {
    RAREAppContext* a=(RAREAppContext*)[RAREPlatform getAppContext];
    if(a && a->RARE_) {
      [a->RARE_ fireApplicationResumed];
    }
    paused=NO;
  }
}

-(UIWindow*) getMainWindow {
  return mainWindow;
}
- (void)orientationChanged:(NSNotification *)notification{
}
-(void) orientationWillChange:(NSNotification *)notification {
  
}

-(void) setMainWindow: (RAREAPWindow*) window {
  mainWindow=window;
}
-(void) addWindow: (RAREAPWindow*) window {
  if(!mainWindow) {
    mainWindow=window;
  }
  NSUInteger pos=[windowStack indexOfObject:window];
  if(pos==NSNotFound) {
    [windowStack addObject:window];
  }
}

-(void) removeWindow: (RAREAPWindow*) window; {
  [windowStack removeObject:window];
}

-(void)sendEvent:(UIEvent *)event {
  if( event.type==UIEventTypeTouches) {
    UITouch* touch= event.allTouches.anyObject;
    if(touch) {
      if(touch.phase==UITouchPhaseBegan) {
        consumed_=NO;
      }
      else {
        if(consumed_) return;
      }
      do {
        NSUInteger len=windowStack.count;
        if(len>1 && event.type==UIEventTypeTouches) {
          if(touch.phase!=UITouchPhaseBegan) break;
          UIWindow* w;
          while(len-->0) {
            w=(UIWindow*)[windowStack objectAtIndex:len];
            if(w.hidden) continue;
            if(![ event touchesForWindow:w]) {
              if([w isKindOfClass:[RAREAPPopupWindow class]]) {
                if([((RAREAPPopupWindow*)w) outsideTouchHappened]) {
                  consumed_=YES;
                }
              }
            }
            break;
          }
        }
      }while(false);
      if(consumed_) {
        return;
      }
      if([keyWindow isKindOfClass:[RAREAPWindow class]] && ((RAREAPWindow *)keyWindow).isModal) {
        if(touch.window.windowLevel<=keyWindow.windowLevel && touch.window!=keyWindow) {
          return;
        }
      }
    }
  }
  [super sendEvent:event];
}

-(void) closePopupWindows: (BOOL) all {
  NSArray* windows=[self windows];
  UIWindow* w=nil;
  NSInteger len=windows.count;
  while(len-->0) {
    w=(UIWindow*)[windows objectAtIndex:len];
    if(!w.hidden && [w isKindOfClass:[RAREAPPopupWindow class]]) {
      RAREAPPopupWindow* pw=(RAREAPPopupWindow*)w;
      if(all || [pw isTransient]) {
        [((RAREAPPopupWindow*)w) cancelPopup: NO];
      }
    }
    else if( all && [w isKindOfClass:[RAREAPWindow class]] && !((RAREAPWindow *)w).isMainWindow) {
      [RAREPlatformHelper closeWindowWithRAREWindow: ((RAREAPWindow *)w).sparWindow];
    }
  }
}

@end
