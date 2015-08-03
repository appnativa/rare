//
//  RARECALayer.h
//  RareTOUCH
//
//  Created by Don DeCoteau on 11/24/13.
//  Copyright (c) 2013 SparseWare. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <QuartzCore/QuartzCore.h>
@class RAREaView;
@class RAREUIStroke;
@protocol RAREiPlatformBorder;
@protocol RAREiPlatformPainter;
@interface RARECALayer : CAShapeLayer {
@public
  BOOL isOverlay_;
  __weak RAREaView* view_;
}

-(id) initWithRAREView: (RAREaView*) view;
-(void) setPainter: (id<RAREiPlatformPainter>) painter;
-(void) setSystemPainter: (id<RAREiPlatformPainter>) painter;
+ (void)setStroke:(RAREUIStroke *)stroke onLayer: (CAShapeLayer *) layer;
- (void)setStroke:(RAREUIStroke *)stroke;
- (void) sparReset;
- (void)sparDispose;
@end
