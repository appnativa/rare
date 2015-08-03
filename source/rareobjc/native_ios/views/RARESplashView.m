//
// Created by Don DeCoteau on 10/1/13.
// Copyright (c) 2013 SparseWare. All rights reserved.
//
// To change the template use AppCode | Preferences | File Templates.
//


#import "RARESplashView.h"
#import "RAREWaitCursorView.h"
@implementation RARESplashViewController : UIViewController
- (id)initWithPortraitImage:(UIImage *)portrait andLandscapeImage:(UIImage *)landscape andMessage: (NSString*) message{
  if(self=[super init]) {
    self.view=[[RARESplashView alloc] initWithPortraitImage:portrait andLandscapeImage:landscape andMessage:message];
  }
  return self;
}
- (id)init{
  if(self=[super init]) {
  }
  return self;
}

- (id)initWithPortraitImage:(UIImage *)portrait andLandscapeImage:(UIImage *)landscape {
  if(self=[super init]) {
    self.view=[[RARESplashView alloc] initWithPortraitImage:portrait andLandscapeImage:landscape];
  }
  return self;
}
@end


@implementation RARESplashView {
  UIImage* landscapeImage;
  UIImage* portraitImage;
  BOOL shown;
}
- (id)initWithPortraitImage:(UIImage *)portrait andLandscapeImage:(UIImage *)landscape andMessage: (NSString*) message{
  if(self=[super initWithMessage:message cancelButtonImage:nil delay:0]) {
    portraitImage=portrait;
    landscapeImage=landscape;
    [self setNeedsDisplay];
  }
  return self;
}

- (id)initWithPortraitImage:(UIImage *)portrait andLandscapeImage:(UIImage *)landscape {
  return [self initWithPortraitImage:portrait andLandscapeImage:landscape andMessage:nil];
}
-(void)willMoveToWindow:(UIWindow *)newWindow {
  [super willMoveToWindow:newWindow];
  if(self.window!=newWindow) {
    if(newWindow) {
      [self startAnimation];
      shown=YES;
    }
    else {
      [self stopAnimation];
      if(shown) {
        portraitImage=nil;
        landscapeImage=nil;
      }
    }
  }
}
-(void)drawRect:(CGRect)rect {
  BOOL land=UIDeviceOrientationIsLandscape([UIApplication sharedApplication].statusBarOrientation);
  UIImage* img=land ? landscapeImage : portraitImage;
  if(!img) {
    img=portraitImage;
  }
  if(img) {
    [img drawInRect:rect];
  }
}
@end