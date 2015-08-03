//
//  RAREActivityIndicatorView.h
//  RareTOUCH
//
//  Created by Don DeCoteau on 5/30/14.
//  Copyright (c) 2014 SparseWare. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface RAREActivityIndicatorView : UIActivityIndicatorView
@property (nonatomic) BOOL autoAnimate;
-(void) setGraphicSize: (int) size;
@end
