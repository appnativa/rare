//
//  RARE UIViewController.m
//  RareIOS
//
//  Created by Don DeCoteau on 5/21/13.
//  Copyright (c) 2013 SparseWare. All rights reserved.
//

#import "RAREUIViewController.h"
#import "APView+Component.h"
#import "com/appnativa/rare/Platform.h"
#import "com/appnativa/rare/iPlatformAppContext.h"
#import "com/appnativa/rare/Platform.h"
#import "com/appnativa/rare/platform/apple/AppContext.h"
#import "RAREAPWindow.h"
#import "RAREAPApplication.h"
#import "com/appnativa/rare/platform/apple/ui/view/View.h"

static const CGFloat KEYBOARD_ANIMATION_DURATION = 0.3;
static const CGFloat MINIMUM_SCROLL_FRACTION = 0.2;
static const CGFloat MAXIMUM_SCROLL_FRACTION = 0.8;
static BOOL statusBarHidden=NO;

@implementation RAREUIViewController {
  CGFloat animatedDistance;
  NSUInteger supportedOrientations;
  NSInteger darkWindowBackground;
}
-initWithWindow: (UIWindow*) win  {
  self = [super init];
  if (self) {
    window=win;
    supportedOrientations=UIInterfaceOrientationMaskAll;//UIInterfaceOrientationMaskPortrait|UIInterfaceOrientationMaskLandscape;
    darkWindowBackground=-1;
  }
  return self;
  
}
-init  {
  self = [super init];
  if (self) {
    supportedOrientations=UIInterfaceOrientationMaskAll;//UIInterfaceOrientationMaskPortrait|UIInterfaceOrientationMaskLandscape;
    darkWindowBackground=-1;
  }
  return self;
  
}
- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil {
  self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
  if (self) {
    supportedOrientations=UIInterfaceOrientationMaskPortrait|UIInterfaceOrientationMaskLandscape;
    darkWindowBackground=-1;
  }
  return self;
}

- (void)loadView {
  RARERootView* v=[[RARERootView alloc] initWithFrame:[UIScreen currentFrame]];
  
  self.view=v;
}

- (void) showContentController: (UIViewController*) content parent: (UIView*) parent rect: (CGRect) frame
{
  [self addChildViewController:content];
  content.view.frame=frame;
  [parent addSubview:content.view];
  [content didMoveToParentViewController:self];
}

- (void) hideContentController: (UIViewController*) content
{
  [content willMoveToParentViewController:nil];
  [content.view removeFromSuperview];
  [content removeFromParentViewController];
}

-(void)didRotateFromInterfaceOrientation:(UIInterfaceOrientation)fromInterfaceOrientation {
  [super didRotateFromInterfaceOrientation:fromInterfaceOrientation];
  if(![RAREPlatform isInitialized]) {
    return;
  }
  RAREAppContext* app=(RAREAppContext *) [RAREPlatform getAppContext];
  app->landscapeMode_=UIDeviceOrientationIsLandscape([[UIDevice currentDevice] orientation]);
  [((RAREAppContext*)[RAREPlatform getAppContext]) handleConfigurationChangedWithId:[NSNumber numberWithInt:fromInterfaceOrientation]];
}
-(void)didReceiveMemoryWarning {
  if(![RAREPlatform isInitialized]) {
    return;
  }
  RAREAppContext* app=(RAREAppContext *) [RAREPlatform getAppContext];
  [app didReceiveMemoryWarning];
  [super didReceiveMemoryWarning];
}

- (NSUInteger)supportedInterfaceOrientations
{
  return supportedOrientations;
}
-(void) lockOrientation:( BOOL) landscape {
  UIInterfaceOrientation io=[UIApplication sharedApplication].statusBarOrientation;
  if(landscape) {
    supportedOrientations=UIInterfaceOrientationMaskLandscape;
    
    if(io != UIInterfaceOrientationLandscapeLeft && io!=UIInterfaceOrientationLandscapeRight) {
      [self rotate:UIInterfaceOrientationLandscapeLeft];
    }
  }
  else {
    supportedOrientations=UIInterfaceOrientationMaskPortrait;
    if(io != UIInterfaceOrientationPortrait && io!=UIInterfaceOrientationPortraitUpsideDown) {
      [self rotate:UIInterfaceOrientationPortrait];
    }
  }
}
-(NSUInteger) getOrientationEx {
  return  [UIApplication sharedApplication].statusBarOrientation;
}

-(void) setOrientationEx: (NSUInteger) o {
  if(o!=supportedOrientations) {
    supportedOrientations=o;
    UIInterfaceOrientation io=[UIApplication sharedApplication].statusBarOrientation;
    if((supportedOrientations & UIInterfaceOrientationMaskPortrait)==0) {
      if(io != UIInterfaceOrientationLandscapeLeft && io!=UIInterfaceOrientationLandscapeRight) {
        [self rotate:UIInterfaceOrientationLandscapeLeft];
      }
    }
    else if((supportedOrientations & UIInterfaceOrientationMaskLandscape)==0) {
      if(io != UIInterfaceOrientationPortrait && io!=UIInterfaceOrientationPortraitUpsideDown) {
        [self rotate:UIInterfaceOrientationPortrait];
      }
    }
    else if(UIDeviceOrientationIsPortrait(([[UIDevice currentDevice] orientation]))){
      if(io == UIInterfaceOrientationLandscapeLeft || io==UIInterfaceOrientationLandscapeRight) {
        [self rotate:UIInterfaceOrientationPortrait];
      }
    }
  }
}

-(void) unlockOrientation {
  if(supportedOrientations!=(UIInterfaceOrientationMaskPortrait|UIInterfaceOrientationMaskLandscape)) {
    supportedOrientations=UIInterfaceOrientationMaskPortrait|UIInterfaceOrientationMaskLandscape;
    [UIViewController attemptRotationToDeviceOrientation];
  }
}
-(void) rotate:(UIInterfaceOrientation)toInterfaceOrientation {
  [self willRotateToInterfaceOrientation:toInterfaceOrientation duration:0];
  window.rootViewController=nil;
  window.rootViewController=self;
  [self didRotateFromInterfaceOrientation:toInterfaceOrientation];
}

-(void)willRotateToInterfaceOrientation:(UIInterfaceOrientation)toInterfaceOrientation duration:(NSTimeInterval)duration {
  [self.view endEditing:YES];
  animatedDistance=0;
  [super willRotateToInterfaceOrientation:toInterfaceOrientation duration:duration];
  if(![RAREPlatform isInitialized]) {
    return;
  }
  [((RAREAppContext*)[RAREPlatform getAppContext]) handleConfigurationWillChangeWithId:[NSNumber numberWithInt:toInterfaceOrientation]];
  if([self isViewLoaded]) {
    [self invalidateRAREAPViews:self.view];
  }
  
}
-(void) invalidateRAREAPViews:(UIView*) view {
  for (UIView *subview in view.subviews)
  {
    if([subview isKindOfClass:[RAREAPView class]]) {
      [subview setNeedsLayout];
      [self invalidateRAREAPViews:subview];
    }
  }
}
-(void) setUseDarkStatusBarText: (BOOL) dark{
  darkWindowBackground=dark ? 0 : 1;
}
-(UIStatusBarStyle)preferredStatusBarStyle{
  if (floor(NSFoundationVersionNumber) > NSFoundationVersionNumber_iOS_6_1) {
    BOOL dark;
    if(darkWindowBackground!=-1) {
      dark=darkWindowBackground==1;
    }
    else {
      dark=[[RAREPlatform getAppContext] isDefaultBackgroundDark];
    }
    return dark ? UIStatusBarStyleLightContent : UIStatusBarStyleDefault;
  }
  return UIStatusBarStyleDefault;
}
-(BOOL)prefersStatusBarHidden {
  return statusBarHidden || animatedDistance!=0;
}
-(void) setStatusBarVisibleEx: (BOOL) visible {
  if(visible==statusBarHidden) {
    statusBarHidden=!visible;
    [self setNeedsStatusBarAppearanceUpdate ];
  }
}
- (void) viewDidLayoutSubviews {
  if(((RAREAPWindow*)self.view.window).isMainWindow) {
    if (floor(NSFoundationVersionNumber) > NSFoundationVersionNumber_iOS_6_1) {
      [self setNeedsStatusBarAppearanceUpdate];

      if ([self respondsToSelector:@selector(edgesForExtendedLayout)])
        self.edgesForExtendedLayout = UIRectEdgeNone;   // iOS 7 specific
      CGRect viewBounds = self.view.bounds;
      CGFloat topBarOffset = self.topLayoutGuide.length;
      viewBounds.origin.y = topBarOffset * -1;
      self.view.bounds = viewBounds;
    }
  }
}
- (void)disposeEx {
  NSArray* a=[self.childViewControllers copy];
  for(UIViewController* vc in a) {
    [self hideContentController:vc];
  }
  window=nil;
  if(self.view) {
    [self.view sparDispose];
  }
  self.view=nil;
}

//Code from Matt Gallagher @ http://www.cocoawithlove.com/2008/10/sliding-uitextfields-around-to-avoid.html
-(void)textFieldDidBeginEditing:(UITextField *)textField {
  if([RAREAPApplication isManageKeyboard]) {
    CGRect textFieldRect =
    [self.view.window convertRect:textField.bounds fromView:textField];
    CGRect viewRect =
    [self.view.window convertRect:self.view.bounds fromView:self.view];
    CGFloat midline = textFieldRect.origin.y + 0.5 * textFieldRect.size.height;
    CGFloat numerator =
    midline - viewRect.origin.y
    - MINIMUM_SCROLL_FRACTION * viewRect.size.height;
    CGFloat denominator =
    (MAXIMUM_SCROLL_FRACTION - MINIMUM_SCROLL_FRACTION)
    * viewRect.size.height;
    CGFloat heightFraction = numerator / denominator;
    if (heightFraction < 0.0)
    {
      heightFraction = 0.0;
    }
    else if (heightFraction > 1.0)
    {
      heightFraction = 1.0;
    }
    animatedDistance = floor([[RAREAPApplication getInstance] getKeyboardHeight] * heightFraction);
    CGRect viewFrame = self.view.frame;
    UIInterfaceOrientation orientation =
    [[UIApplication sharedApplication] statusBarOrientation];
    if (orientation == UIInterfaceOrientationPortrait ||
        orientation == UIInterfaceOrientationPortraitUpsideDown)
    {
      viewFrame.origin.y -= animatedDistance;
    }
    else
    {
      if(orientation==UIInterfaceOrientationLandscapeLeft) {
        viewFrame.origin.x -= animatedDistance;
      }
      else {
        viewFrame.origin.x += animatedDistance;
      }
    }
    
    [UIView beginAnimations:nil context:NULL];
    [UIView setAnimationBeginsFromCurrentState:YES];
    [UIView setAnimationDuration:KEYBOARD_ANIMATION_DURATION];
    [self setNeedsStatusBarAppearanceUpdate ];
    
    [self.view setFrame:viewFrame];
    
    [UIView commitAnimations];
  }
}
-(void)touchesBegan:(NSSet *)touches withEvent:(UIEvent *)event
{
  [self.view endEditing:YES];
}
- (void)textFieldDidEndEditing:(UITextField *)textField
{
  if(animatedDistance!=0) {
    CGRect viewFrame = self.view.frame;
    UIInterfaceOrientation orientation =
    [[UIApplication sharedApplication] statusBarOrientation];
    if (orientation == UIInterfaceOrientationPortrait ||
        orientation == UIInterfaceOrientationPortraitUpsideDown)
    {
      viewFrame.origin.y += animatedDistance;
    }
    else
    {
      if(orientation==UIInterfaceOrientationLandscapeLeft) {
        viewFrame.origin.x += animatedDistance;
      }
      else {
        viewFrame.origin.x -= animatedDistance;
      }
    }
    
    [UIView beginAnimations:nil context:NULL];
    [UIView setAnimationBeginsFromCurrentState:YES];
    [UIView setAnimationDuration:KEYBOARD_ANIMATION_DURATION];
    
    [self.view setFrame:viewFrame];
    animatedDistance=0;
    [self setNeedsStatusBarAppearanceUpdate ];
    [UIView commitAnimations];
  }
}

@end
