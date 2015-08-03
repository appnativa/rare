//
//  RARERootView.h
//  RareIOS
//
//  Created by Don DeCoteau on 5/21/13.
//  Copyright (c) 2013 SparseWare. All rights reserved.
//

#import "RAREAPView.h"
#import "RAREAPLabelView.h"

@protocol RAREiPlatformIcon;
@interface RARERootView : RAREAPView
-(void) addViewToGlass: (UIView*) view;
-(void) removeViewFromGlass: (UIView*) view;
@end
