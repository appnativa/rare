//
//  RAREAPSlider.h
//  RareOSX
//
//  Created by Don DeCoteau on 3/25/13.
//  Copyright (c) 2013 SparseWare. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface RAREAPSlider : UISlider   {
    BOOL horizontal_;
}

- (void)setMaxValue:(float)maximum;

- (void)setMinValue:(float)minimum;

- (float)getValue;

- (void)setHorizontal:(BOOL)horizontal;

- (BOOL)isHorizontal;
@end
