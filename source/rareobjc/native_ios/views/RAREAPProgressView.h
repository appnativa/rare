//
//  RAREAPProgressIndicator.h
//  RareOSX
//
//  Created by Don DeCoteau on 3/25/13.
//  Copyright (c) 2013 SparseWare. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface RAREAPProgressView : UIProgressView {
  BOOL indeterminate_;
  CGFloat minValue;
  CGFloat maxValue;
}
-(void) setIndeterminate: (BOOL) indeterminate;
-(BOOL) isIndeterminate;
-(void) setValue: (CGFloat) progress;
-(void) setMaxValue: (CGFloat) value;
-(void) setMinValue: (CGFloat) value;
-(CGFloat) getValue;
-(void) setGraphicSize: (int) size;
@end
