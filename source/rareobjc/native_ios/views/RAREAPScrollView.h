//
//  RAREAPSCrollView.h
//  RareOSX
//
//  Created by Don DeCoteau on 3/25/13.
//  Copyright (c) 2013 SparseWare. All rights reserved.
//

#import <UIKit/UIKit.h>
@class RAREOverlayView;
@interface RAREAPScrollView : UIScrollView {
  RAREOverlayView* overlayView; 
}
-(void) createOverlayView: (BOOL) wantsFirstInteraction;
-(void) removeOverlayView;
-(void) moveLeftRight: (BOOL) left block: (BOOL) block;
-(void) moveLeftRight: (BOOL) left increment: (CGFloat) x;
-(void) moveUpDown: (BOOL) up block: (BOOL) block;
-(void) moveUpDown: (BOOL) up increment: (CGFloat) x;


@end
