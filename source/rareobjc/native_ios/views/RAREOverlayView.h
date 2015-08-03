//
//  RAREOverlayView.h
//  RareIOS
//
//  Created by Don DeCoteau on 6/17/13.
//  Copyright (c) 2013 SparseWare. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface RAREOverlayView : UIView {
  BOOL overlayHadInteraction_;
  BOOL wantsFirstInteraction_;
  BOOL actAsGlass_;
}
-(void) setWantsFirstInteraction: (BOOL) wants;
-(void) setActAsGlass: (BOOL) glass;

@end
