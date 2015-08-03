//
//  RAREAPSwitch.m
//  RareIOS
//
//  Created by Don DeCoteau on 5/16/13.
//  Copyright (c) 2013 SparseWare. All rights reserved.
//

#import "RAREAPSwitch.h"
#import "RAREImageWrapper.h"
#import "AppleHelper.h"
#import "APView+Component.h"
#import "com/appnativa/rare/ui/UIImageIcon.h"
#import "com/appnativa/rare/ui/UIInsets.h"
#import "com/appnativa/rare/platform/apple/ui/view/ParentView.h"

@implementation RAREAPSwitch
+ (Class)layerClass
{
  return [RARECAGradientLayer class];
}
-(id)initWithFrame:(CGRect)frame {
  if(self=[super initWithFrame:frame]) {
    titleLabel=[UILabel new];
    switchView=[UISwitch new];
    [self addSubview:titleLabel];
    [self addSubview:switchView];
    insets_.top=2;
    insets_.right=2;
    insets_.bottom=2;
    insets_.left=2;
  }
  return self;
}
-(void)sparDispose {
  [super sparDispose];
  titleLabel=nil;
  switchView=nil;
}

-(BOOL) isOn {
  return  switchView.on;
}

-(void) setOn: (BOOL) on {
  switchView.on=on;
}

-(void) setFont: (UIFont*) font {
  titleLabel.font=font;
}
-(void) setTextColor: (UIColor*) color {
  titleLabel.textColor=color;
}

-(void) setText: (NSString*) text  {
  titleLabel.text=text;
}

-(void) setCharSequence: (id<JavaLangCharSequence>) text {
  [titleLabel setCharSequence: text];
}


-(CGSize) preferredSize {
  CGSize labelSize=[titleLabel sizeThatFits:titleLabel.frame.size];
  CGSize switchSize=[switchView sizeThatFits:switchView.frame.size];
  CGSize size=CGSizeMake(insets_.left+insets_.right, insets_.top+insets_.bottom);
  BOOL rtl=[AppleHelper isLTRText];
  RARERenderableDataItem_IconPosition ip=iconPosition;
  if(ip==RARERenderableDataItem_IconPosition_TRAILING) {
    ip=rtl ? RARERenderableDataItem_IconPosition_LEFT : RARERenderableDataItem_IconPosition_RIGHT;
  }
  else if(ip==RARERenderableDataItem_IconPosition_LEADING || !ip) {
    ip=rtl ? RARERenderableDataItem_IconPosition_RIGHT : RARERenderableDataItem_IconPosition_LEFT;
    
  }
  switch(ip) {
    case RARERenderableDataItem_IconPosition_TOP_CENTER :
    case RARERenderableDataItem_IconPosition_TOP_LEFT :
    case RARERenderableDataItem_IconPosition_TOP_RIGHT :
    case RARERenderableDataItem_IconPosition_BOTTOM_CENTER :
    case RARERenderableDataItem_IconPosition_BOTTOM_LEFT :
    case RARERenderableDataItem_IconPosition_BOTTOM_RIGHT :
      size.width+=MAX(labelSize.width,switchSize.width);
      size.height+=labelSize.height+switchSize.height+iconGap;
      break;
    default:
      size.height+=MAX(labelSize.height,switchSize.height);
      size.width+=labelSize.width+switchSize.width+iconGap;
      break;
  }
  return size;
  
}

-(BOOL) isPressed {
  return switchView.highlighted;
}

-(void) setOnIcon: (id<RAREiPlatformIcon>) icon {
  switchView.onImage=[RAREImageWrapper getImageFromIcon:icon forView:self.sparView];
}

-(void) setOffIcon: (id<RAREiPlatformIcon>) icon {
  if(icon!=nil) {
    switchView.offImage=[RAREImageWrapper getImageFromIcon:icon forView:self.sparView];
  }
}


-(void) setIconGap: (int) gap {
  iconGap=gap;
}

-(void) setInsetsWithTop: (int) top right: (int)right bottom: (int) bottom left: (int) left {
  insets_=UIEdgeInsetsMake(top, left, bottom, right);
}

-(void) getInsets:(RAREUIInsets*) insets {
  insets->top_ =insets_.top;
  insets->right_=insets_.right;
  insets->bottom_=insets_.bottom;
  insets->left_=insets_.left;
}

-(void) setIconPosition: (RARERenderableDataItem_IconPosition) position {
  iconPosition=position;
}

-(void) setTextVerticalAlignment: (RARERenderableDataItem_VerticalAlign) alignment {
  verticalAlighment=alignment;
}

-(void) setTextHorizontalAlignment: (RARERenderableDataItem_HorizontalAlign) alignment {
  BOOL rtl=[AppleHelper isLTRText];
  RARERenderableDataItem_HorizontalAlign a=alignment;
  if(a==RARERenderableDataItem_HorizontalAlign_TRAILING) {
    a=rtl ? RARERenderableDataItem_HorizontalAlign_LEFT : RARERenderableDataItem_HorizontalAlign_RIGHT;
  }
  else if(a==RARERenderableDataItem_HorizontalAlign_LEADING) {
    a=rtl ? RARERenderableDataItem_HorizontalAlign_RIGHT : RARERenderableDataItem_HorizontalAlign_LEFT;
    
  }
  switch (a) {
    case RARERenderableDataItem_HorizontalAlign_AUTO:
      titleLabel.textAlignment=kCTNaturalTextAlignment;
      break;
    case RARERenderableDataItem_HorizontalAlign_RIGHT:
      titleLabel.textAlignment=kCTRightTextAlignment;
      break;
    case RARERenderableDataItem_HorizontalAlign_CENTER:
      titleLabel.textAlignment=kCTCenterTextAlignment;
      break;
    default:
      titleLabel.textAlignment=kCTLeftTextAlignment;
      break;
  }
}
-(void)layoutSubviews {
  CGRect rect=self.frame;
  UIEdgeInsetsInsetRect(rect, insets_);
  CGRect frame=[self imageRectForContentRect:rect];
  switchView.frame=frame;
  frame=[self titleRectForContentRect:rect];
  titleLabel.frame=frame;
}

-(CGRect)imageRectForContentRect:(CGRect)contentRect {
  BOOL rtl=[AppleHelper isLTRText];
  RARERenderableDataItem_IconPosition ip=iconPosition;
  if(ip==RARERenderableDataItem_IconPosition_TRAILING) {
    ip=rtl ? RARERenderableDataItem_IconPosition_LEFT : RARERenderableDataItem_IconPosition_RIGHT;
  }
  else if(ip==RARERenderableDataItem_IconPosition_LEADING || !ip) {
    ip=rtl ? RARERenderableDataItem_IconPosition_RIGHT : RARERenderableDataItem_IconPosition_LEFT;
    
  }
  CGRect rect=switchView.frame;
  switch(ip) {
    case RARERenderableDataItem_IconPosition_TOP_CENTER :
      rect.origin.x=contentRect.origin.x+((contentRect.size.width-rect.size.width)/2);
      rect.origin.y=contentRect.origin.y;
      break;
    case RARERenderableDataItem_IconPosition_TOP_LEFT :
      rect.origin.x=contentRect.origin.x;
      rect.origin.y=contentRect.origin.y;
      break;
    case RARERenderableDataItem_IconPosition_TOP_RIGHT :
      rect.origin.x=contentRect.origin.x+contentRect.size.width-rect.size.width;
      rect.origin.y=contentRect.origin.y;
      break;
    case RARERenderableDataItem_IconPosition_BOTTOM_CENTER :
      rect.origin.x=contentRect.origin.x+((contentRect.size.width-rect.size.width)/2);
      rect.origin.y=contentRect.origin.y+contentRect.size.height-rect.size.height;
      break;
    case RARERenderableDataItem_IconPosition_BOTTOM_LEFT :
      rect.origin.x=contentRect.origin.x;
      rect.origin.y=contentRect.origin.y+contentRect.size.height-rect.size.height;
      break;
    case RARERenderableDataItem_IconPosition_BOTTOM_RIGHT :
      rect.origin.x=contentRect.origin.x+contentRect.size.width-rect.size.width;
      rect.origin.y=contentRect.origin.y+contentRect.size.height-rect.size.height;
      break;
    case RARERenderableDataItem_IconPosition_RIGHT :
      rect.origin.x=contentRect.origin.x+contentRect.size.width-rect.size.width;
      rect.origin.y=contentRect.origin.y;
      
      break;
      
    default :
      rect.origin.x=contentRect.origin.x;
      rect.origin.y=contentRect.origin.y+((contentRect.size.height-rect.size.height)/2);
      break;
  }
  return rect;
}

-(CGRect)titleRectForContentRect:(CGRect)contentRect {
  BOOL rtl=[AppleHelper isLTRText];
  RARERenderableDataItem_IconPosition ip=iconPosition;
  CGRect rect=titleLabel.frame;
  if(ip==RARERenderableDataItem_IconPosition_TRAILING) {
    ip=rtl ? RARERenderableDataItem_IconPosition_LEFT : RARERenderableDataItem_IconPosition_RIGHT;
  }
  else if(ip==RARERenderableDataItem_IconPosition_LEADING || !ip) {
    ip=rtl ? RARERenderableDataItem_IconPosition_RIGHT : RARERenderableDataItem_IconPosition_LEFT;
    
  }
  CGRect imageRect=switchView.frame;
  switch(ip) {
    case RARERenderableDataItem_IconPosition_TOP_CENTER :
    case RARERenderableDataItem_IconPosition_TOP_LEFT :
    case RARERenderableDataItem_IconPosition_TOP_RIGHT :
      switch (verticalAlighment) {
        case RARERenderableDataItem_VerticalAlign_TOP:
          rect.origin.y=contentRect.origin.y+imageRect.size.height+iconGap;
          break;
        case RARERenderableDataItem_VerticalAlign_BOTTOM:
          rect.origin.y=contentRect.origin.y+contentRect.size.height-rect.size.height;
          break;
        default: {
          CGFloat dy=(contentRect.size.height-imageRect.size.height-rect.size.height)/2;
          rect.origin.y=contentRect.origin.y+imageRect.size.height+dy;
          break;
        }
      }
      break;
    case RARERenderableDataItem_IconPosition_BOTTOM_CENTER :
    case RARERenderableDataItem_IconPosition_BOTTOM_LEFT :
    case RARERenderableDataItem_IconPosition_BOTTOM_RIGHT :
      switch (verticalAlighment) {
        case RARERenderableDataItem_VerticalAlign_TOP:
          rect.origin.y=contentRect.origin.y;
          break;
        case RARERenderableDataItem_VerticalAlign_BOTTOM:
          rect.origin.y=contentRect.origin.y+contentRect.size.height-imageRect.size.height-iconGap;
          break;
        default: {
          CGFloat dy=(contentRect.size.height-imageRect.size.height-rect.size.height)/2;
          rect.origin.y=contentRect.origin.y+dy;
          break;
        }
      }
      break;
    case RARERenderableDataItem_IconPosition_RIGHT :
      rect.origin.x=contentRect.origin.x;
      break;
      
    default :
      rect.origin.x=contentRect.origin.x+contentRect.size.width-rect.size.width;
      break;
  }
  return rect;
}

-(void)setHidden:(BOOL)hidden {
  BOOL changed=self.hidden!=hidden;
  [super setHidden:hidden];
  if(changed && self.window) {
    RAREView *view = self.sparView;
    if (view && view->viewListener_ ) {
      [view visibilityChangedWithBoolean:!hidden];
    }
  }
}
- (void)willMoveToWindow:(UIWindow *)newWindow {
  [super willMoveToWindow:newWindow];
  RAREView* view=self.sparView;
  if(view && view->viewListener_ && self.window!=newWindow) {
    [view visibilityChangedWithBoolean:newWindow!=nil];
  }
}
@end
