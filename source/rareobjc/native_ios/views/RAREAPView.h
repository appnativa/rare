//
//  RAREAPView.h
//  RareOSX
//
//  Created by Don DeCoteau on 3/19/13.
//  Copyright (c) 2013 SparseWare. All rights reserved.
//

#import "com/appnativa/rare/ui/Component.h"
#import "com/appnativa/rare/platform/apple/ui/iAppleLayoutManager.h"
#import "com/appnativa/rare/platform/apple/ui/iApplePainterSupport.h"
@class RAREOverlayView;
@interface RAREAPView : UIView {
  RAREView* sparView;
  BOOL useFlipTransform;
  UIView *overlayView;
}
@property (nonatomic) BOOL wantsFirstInteraction;
@property (nonatomic) BOOL syncWithDisplayRefresh;
@property (nonatomic, retain) id<RAREiAppleLayoutManager> layoutManager;
-(void) setUseFlipTransform: (BOOL) flip;
-(void) removeAllSubViews;
-(void) setOverlayView: (UIView*) view;
-(void) createOverlayView: (BOOL) wantsFirstInteraction;
-(void) removeOverlayView;
-(void) startDisplayLink;
-(void) stopDisplayLink;


@end
