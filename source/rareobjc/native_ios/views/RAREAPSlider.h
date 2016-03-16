//
//  RAREAPSlider.h
//  RareOSX
//
//  Created by Don DeCoteau on 3/25/13.
//  Copyright (c) 2013 SparseWare. All rights reserved.
//

#import <UIKit/UIKit.h>
@class RAREUIImage;
@protocol RAREiPlatformPainter;

@interface RAREAPSlider : UISlider   {
   BOOL horizontal_;
  int trackPainterWidth;
  id<RAREiPlatformPainter> trackPainter;
}

- (void)setMaxValue:(float)maximum;

- (void)setMinValue:(float)minimum;

- (float)getValue;

- (void)setHorizontal:(BOOL)horizontal;

- (BOOL)isHorizontal;
- (void)setThumbImage: (RAREUIImage*) image;
- (void)setTrackPainter:(id<RAREiPlatformPainter>) painter;
- (void)setTrackPainterWidth:(int) width;
@end
