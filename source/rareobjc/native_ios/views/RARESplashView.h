//
// Created by Don DeCoteau on 10/1/13.
// Copyright (c) 2013 SparseWare. All rights reserved.
//
// To change the template use AppCode | Preferences | File Templates.
//


#import <Foundation/Foundation.h>
#import "RAREWaitCursorView.h"
@interface RARESplashViewController : UIViewController
- (id)initWithPortraitImage:(UIImage *)portrait andLandscapeImage:(UIImage *)landscape andMessage: (NSString*) message;
-(id) initWithPortraitImage: (UIImage *) portrait andLandscapeImage: (UIImage*) landscape;

@end

@interface RARESplashView : RAREWaitCursorView
- (id)initWithPortraitImage:(UIImage *)portrait andLandscapeImage:(UIImage *)landscape andMessage: (NSString*) message;
-(id) initWithPortraitImage: (UIImage *) portrait andLandscapeImage: (UIImage*) landscape;
@end