//
//  RARECAGradientLayer.h
//  RareTOUCH
//
//  Created by Don DeCoteau on 11/24/13.
//  Copyright (c) 2013 SparseWare. All rights reserved.
//

#import <QuartzCore/QuartzCore.h>
@class RARECALayer;
@class RAREaView;
@protocol RAREiPlatformBorder;
@protocol RAREiPlatformPainter;

@interface RARECAGradientLayer : CAGradientLayer {
  @public
  __weak RAREaView* view_;
  BOOL paintEnabled_;
  BOOL layoutEnabled_;
  
}
-(id) initWithRAREView: (RAREaView*) view;
-(void) setOverlayLayer: (RARECALayer*) layer ;
-(RARECALayer*) getOverLayerCreate:(BOOL) create forRAREView: (RAREaView*) view;
-(RARECALayer*) getBackgroundOverLayerCreate:(BOOL) create forRAREView: (RAREaView*) view;
-(void) setOverlayPath: (id) nativepath view: (RAREaView*) view;
-(void) sparDispose;
-(void) sparResetLayer;
-(void) sparResetLayerForRendererWithView: (RAREaView*) view;
-(void) repaint;
-(void) removeNativeBorder;
@end
