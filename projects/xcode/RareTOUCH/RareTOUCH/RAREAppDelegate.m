//
//  RAREAppDelegate.m
//  RareIOS
//
//  Created by Don DeCoteau on 5/12/13.
//  Copyright (c) 2013 SparseWare. All rights reserved.
//

#import "RAREAppDelegate.h"
#import "RAREUIViewController.h"
#import "AppleHelper.h"
#import "com/appnativa/rare/platform/apple/Rare.h"
#import "com/appnativa/rare/platform/apple/Main.h"
#import "com/appnativa/rare/ui/event/ActionEvent.h"
#import "APView+Component.h"
#import "RAREAPWindow.h"
#import "RAREAPApplication.h"
#import "RAREWaitCursorView.h"
#import "com/appnativa/rare/Platform.h"
#include "com/appnativa/rare/ui/UIProperties.h"

@implementation RAREAppDelegate

-(BOOL)application:(UIApplication *)application willFinishLaunchingWithOptions:(NSDictionary *)launchOptions {
  NSString* message=@"Launching...";
  UIImage* land=nil;
  UIImage* port=nil;
  //We extract the launch image from the app to use as the splash screen.
  //These names wer gleaned by showing the package contest os a compiled appg
  if([UIDevice currentDevice].userInterfaceIdiom == UIUserInterfaceIdiomPad)
  {
    if([UIScreen mainScreen].scale>1) {
      port=[UIImage imageWithContentsOfFile:[[NSBundle mainBundle] pathForResource:@"LaunchImage-700-Portrait@2x~ipad" ofType:@"png"]];
      land=[UIImage imageWithContentsOfFile:[[NSBundle mainBundle] pathForResource:@"LaunchImage-700-Landscape@2x~ipad" ofType:@"png"]];
    }
    else {
      port=[UIImage imageWithContentsOfFile:[[NSBundle mainBundle] pathForResource:@"LaunchImage-700-Portrait~ipad" ofType:@"png"]];
      land=[UIImage imageWithContentsOfFile:[[NSBundle mainBundle] pathForResource:@"LaunchImage-700-Landscape~ipad" ofType:@"png"]];
    }
  }
  else
  {
    if([UIScreen mainScreen].bounds.size.height==568) {
      port=[UIImage imageWithContentsOfFile:[[NSBundle mainBundle] pathForResource:@"LaunchImage-700-568h@2x" ofType:@"png"]];
    }
    else {
      port=[UIImage imageWithContentsOfFile:[[NSBundle mainBundle] pathForResource:@"LaunchImage-700@2x" ofType:@"png"]];
    }
  }
  [RAREAPApplication showSplashScreenWithPortraitImage:port andLandscapeImage:land andMessage:message foreground:[UIColor blackColor]];
  return YES;
}

- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions
{
  
  self.window = [[RAREAPWindow alloc] initWithFrame:[[UIScreen mainScreen] bounds]];
  [((RAREAPApplication *) application) setMainWindow:(RAREAPWindow*)self.window];
  NSString* server=@"http://192.168.1.50";
  NSString* demo;
  NSArray *arguments;
  //demo= @"/apps/covergirls/coverflow_view.sdf";
  demo= @"/demos/medical/Medical-android/assets/application.rml";
  //demo= @"/demos/mail/Mail-android/assets/application.rml";
  demo= @"//xhrdemos/tests/application.rml";
  arguments=[NSArray arrayWithObject:[NSString stringWithFormat:@"%@%@",server,demo]];
  //arguments = [NSArray arrayWithObject: @"lib:assets/application.rml"];
  
  
  dispatch_async(dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT, 0), ^{
    RAREMain *rare=[[RAREMain alloc]initWithNSStringArray:[AppleHelper toStringArray:arguments]];
    [[RAREPlatform getUIDefaults] putWithNSString:@"Rare.Resources.path" withId:@"res"];
    dispatch_async(dispatch_get_main_queue(), ^{
      [rare launchWithId:[self window]];
      [[self window] makeKeyAndVisible];
      
    });//end block
  });
  return YES;
}

- (void)applicationWillResignActive:(UIApplication *)application
{
  // Sent when the application is about to move from active to inactive state. This can occur for certain types of temporary interruptions (such as an incoming phone call or SMS message) or when the user quits the application and it begins the transition to the background state.
  // Use this method to pause ongoing tasks, disable timers, and throttle down OpenGL ES frame rates. Games should use this method to pause the game.
}

- (void)applicationDidEnterBackground:(UIApplication *)application
{
  // Use this method to release shared resources, save user data, invalidate timers, and store enough application state information to restore your application to its current state in case it is terminated later.
  // If your application supports background execution, this method is called instead of applicationWillTerminate: when the user quits.
}

- (void)applicationWillEnterForeground:(UIApplication *)application
{
  // Called as part of the transition from the background to the inactive state; here you can undo many of the changes made on entering the background.
}

- (void)applicationDidBecomeActive:(UIApplication *)application
{
  // Restart any tasks that were paused (or not yet started) while the application was inactive. If the application was previously in the background, optionally refresh the user interface.
}

- (void)applicationWillTerminate:(UIApplication *)application
{
}

- (void)application:(UIApplication *)application didReceiveLocalNotification:(UILocalNotification *)notification {
  if(notification.userInfo ) {
    id<RAREiActionListener> l=[notification.userInfo objectForKey:@"listener"];
    if(l) {
      if([notification.userInfo isKindOfClass:[NSMutableDictionary class]]) {
        [(NSMutableDictionary*)notification.userInfo removeObjectForKey:@"listener"];
      }
      RAREActionEvent* e=[[RAREActionEvent alloc] initWithId:application];
      [l actionPerformedWithRAREActionEvent:e ];
    }
  }
}
@end
