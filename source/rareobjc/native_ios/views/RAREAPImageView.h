//
//  RAREAPImageView.h
//  RareOSX
//
//  Created by Don DeCoteau on 5/1/13.
//  Copyright (c) 2013 SparseWare. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface RAREAPImageView : UIImageView

- (void)setSelectionShape:(id <RAREiPlatformShape>)shape;

- (void)setSelectionStroke:(RAREUIStroke *)stroke;

- (void)setRAREUIImage:(RAREUIImage *)image;

- (void)setSelectionColor:(RAREUIColor *)color;
@end
