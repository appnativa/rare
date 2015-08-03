//
//  RAREAPLabelView.m
//  RareOSX
//
//  Created by Don DeCoteau on 3/28/13.
//  Copyright (c) 2013 SparseWare. All rights reserved.
//

#import "RAREAPLabelView.h"
#import "com/appnativa/rare/ui/UIRectangle.h"
#import "com/appnativa/rare/ui/UIInsets.h"
#import "com/appnativa/rare/platform/apple/ui/util/AppleGraphics.h"
#import "com/appnativa/rare/platform/apple/ui/view/View.h"
#import "com/appnativa/rare/ui/event/MouseEventEx.h"
#import "com/appnativa/rare/ui/listener/iMouseListener.h"
#import "com/appnativa/rare/ui/listener/iMouseMotionListener.h"
#import "com/appnativa/rare/ui/UIImageIcon.h"
#import "APView+Component.h"
#import "AppleHelper.h"
#import "RARECAGradientLayer.h"


@implementation RAREAPLabelView {
  CGSize titleSize;
}

- (id)initWithFrame:(CGRect)frame {
  self = [super initWithFrame:frame];
  if (self) {
    self.backgroundColor=[UIColor clearColor];
    self.numberOfLines = 1;
    self.lineBreakMode=NSLineBreakByTruncatingTail;
    verticalAlighment = RARERenderableDataItem_VerticalAlign_AUTO;
    iconGap = 4;
    iconPosition = RARERenderableDataItem_IconPosition_AUTO;
    insets_ = UIEdgeInsetsMake(2, 2, 2, 2);
    self.textAlignment = NSTextAlignmentLeft;
  }
  return self;
}
- (CGSize)getPreferredSize:(CGFloat)maxWidth {
  if(maxWidth<1) {
    maxWidth=20000;
  }
  CGSize constraint = CGSizeMake(maxWidth, 20000.0f);
  return [self sizeThatFits:constraint];
}

-(CGSize) preferredSize {
  return [self sizeThatFits:self.frame.size];
}
-(RARERenderableDataItem_IconPosition) getIconPosition {
  return iconPosition;
}
- (CGFloat)getPreferredHeight:(CGFloat)width {
  return [self getPreferredSize:width].height;
}

- (BOOL)isPressed {
  return pressed_;
}

-(void) setPressed: (BOOL) pressed {
  pressed_=pressed;
}

- (void)setFont:(UIFont *)font {
  @try {
    [super setFont:font];
    dirty_=YES;
  }
  @catch (NSException *exception) {
    NSLog(@"%@", font);
  }

}
- (void)setText:(NSString *)text {
  dirty_=YES;
  [super setText:text];
}
- (void)setAttributedText:(NSAttributedString *)attributedText {
  dirty_=YES;
  [super setAttributedText:attributedText];
}
-(void)sparDispose {
  [super sparDispose];
  icon_=nil;
}

- (void)setIcon:(id <RAREiPlatformIcon>)icon {
  icon_ = icon;
  dirty_=YES;
  [self setNeedsDisplay];
}
 - (void)setFrame:(CGRect)frame {
   [super setFrame:frame];
   dirty_=YES;
 }
- (void)setInsetsWithTop:(int)top right:(int)right bottom:(int)bottom left:(int)left {
  insets_ = UIEdgeInsetsMake(top, left, bottom, right);
  dirty_=YES;
}

-(void) getInsets:(RAREUIInsets*) insets {
  insets->top_ =insets_.top;
  insets->right_=insets_.right;
  insets->bottom_=insets_.bottom;
  insets->left_=insets_.left;
}

- (void)drawTextInRect:(CGRect)rect {
  rect = [self titleRectForContentRect:rect];
  titleRect_=rect;
  [super drawTextInRect:rect];
}
- (void)setCharSequence:(id <JavaLangCharSequence>)text {
  dirty_=YES;
  [super setCharSequence:text];
}

- (void)drawRect:(CGRect)dirtyRect {
  if(dirty_) {
    titleSize = [super sizeThatFits:self.frame.size];
  }
  CGContextRef ctx = UIGraphicsGetCurrentContext();
  RAREAppleGraphics *g = nil;
  RAREView *view = self.sparView;
  if (self.tag & MASK_PAINT_HANDLER) {
    g = [[RAREAppleGraphics alloc] initWithId:(__bridge id) ctx withRAREView:view];
    RAREUIRectangle *rect = [self sparBounds];
    [view paintBackgroundWithRAREAppleGraphics:g withRAREView:view withRAREUIRectangle:rect];

    [super drawRect:dirtyRect];

    [view paintOverlayWithRAREAppleGraphics:g withRAREView:view withRAREUIRectangle:rect];
  }
  else {
    [super drawRect:dirtyRect];
  }
  if (icon_) {
    if (!g) {
      g = [[RAREAppleGraphics alloc] initWithId:(__bridge id) ctx withRAREView:view];
    }
    CGRect rect = [self imageRectForContentRect:UIEdgeInsetsInsetRect(self.bounds, insets_) titleRect: titleRect_];
    [icon_ paintWithRAREiPlatformGraphics:g withFloat:rect.origin.x withFloat:rect.origin.y withFloat: rect.size.width withFloat: rect.size.height];
  }
  if(g) {
    [g dispose];
  }
}
- (CGSize)sizeThatFits:(CGSize)size {
  size = [super sizeThatFits:size];
  titleSize=size;
  dirty_=NO;
  return [self adjustSize:size];
}

- (void)setIconGap:(int)gap {
  iconGap = gap;
  dirty_=YES;
}

+ (Class)layerClass
{
  return [RARECAGradientLayer class];
}

- (void)setIconPosition:(RARERenderableDataItem_IconPosition)position {
  iconPosition = position;
  dirty_=YES;
}

- (void)setTextVerticalAlignment:(RARERenderableDataItem_VerticalAlign)alignment {
  verticalAlighment = alignment;
  dirty_=YES;
}

- (void)setTextHorizontalAlignment:(RARERenderableDataItem_HorizontalAlign)alignment {
  BOOL rtl = [AppleHelper isLTRText];
  RARERenderableDataItem_HorizontalAlign a = alignment;
  if (a == RARERenderableDataItem_HorizontalAlign_TRAILING) {
    a = rtl ? RARERenderableDataItem_HorizontalAlign_LEFT : RARERenderableDataItem_HorizontalAlign_RIGHT;
  }
  else if (a == RARERenderableDataItem_HorizontalAlign_LEADING) {
    a = rtl ? RARERenderableDataItem_HorizontalAlign_RIGHT : RARERenderableDataItem_HorizontalAlign_LEFT;

  }
  switch (a) {
    case RARERenderableDataItem_HorizontalAlign_AUTO:
      if (floor(NSFoundationVersionNumber) <= NSFoundationVersionNumber_iOS_6_1){
        self.textAlignment = NSTextAlignmentLeft;
      }
      else {
        self.textAlignment = NSTextAlignmentNatural;
      }
      break;
    case RARERenderableDataItem_HorizontalAlign_RIGHT:
      self.textAlignment = NSTextAlignmentRight;
      break;
    case RARERenderableDataItem_HorizontalAlign_CENTER:
      self.textAlignment = NSTextAlignmentCenter;
      break;
    default:
      self.textAlignment = NSTextAlignmentLeft;
      break;
  }
  dirty_=YES;
}


- (CGRect)imageRectForContentRect:(CGRect)contentRect titleRect: (CGRect) titleRect{
  CGSize imageSize = {[icon_ getIconWidth], [icon_ getIconHeight]};
  return [RAREAPLabelView imageRectForContentRect:contentRect titleRect:titleRect titleSize: titleSize imageSize:imageSize iconPosition:iconPosition iconGap:iconGap textAlignment: self.textAlignment];

}

+ (CGRect)imageRectForContentRect:(CGRect)contentRect titleRect: (CGRect) titleRect titleSize: (CGSize) titleSize imageSize: (CGSize) imageSize iconPosition: (RARERenderableDataItem_IconPosition ) ip iconGap : (int) iconGap textAlignment:(NSTextAlignment) alignment{
  CGRect rect = {contentRect.origin, imageSize};
  if(titleRect.size.height<=0 || titleRect.size.width<=0) {
    rect.origin.x+=((contentRect.size.width-rect.size.width)/2);
    rect.origin.y+=((contentRect.size.height-rect.size.height)/2);
    return rect;
  }
  BOOL rtl = [AppleHelper isLTRText];
  if (ip == RARERenderableDataItem_IconPosition_TRAILING) {
    ip = rtl ? RARERenderableDataItem_IconPosition_LEFT : RARERenderableDataItem_IconPosition_RIGHT;
  }
  else if (ip == RARERenderableDataItem_IconPosition_LEADING || !ip) {
    ip = rtl ? RARERenderableDataItem_IconPosition_RIGHT : RARERenderableDataItem_IconPosition_LEFT;
    
  }
  switch (ip) {
    case RARERenderableDataItem_IconPosition_TOP_CENTER :
      rect.origin.x = contentRect.origin.x + ((contentRect.size.width - rect.size.width) / 2);
      rect.origin.y = titleRect.origin.y-iconGap-rect.size.height;
      break;
    case RARERenderableDataItem_IconPosition_TOP_LEFT :
      rect.origin.x = contentRect.origin.x;
      rect.origin.y = titleRect.origin.y-iconGap-rect.size.height;
      break;
    case RARERenderableDataItem_IconPosition_TOP_RIGHT :
      rect.origin.x = contentRect.origin.x + contentRect.size.width - rect.size.width;
      rect.origin.y = titleRect.origin.y-iconGap-rect.size.height;
      break;
    case RARERenderableDataItem_IconPosition_BOTTOM_CENTER :
      rect.origin.x = contentRect.origin.x + ((contentRect.size.width - rect.size.width) / 2);
      rect.origin.y = titleRect.origin.y+titleRect.size.height+iconGap;
      break;
    case RARERenderableDataItem_IconPosition_BOTTOM_LEFT :
      rect.origin.x = contentRect.origin.x;
      rect.origin.y = titleRect.origin.y+titleRect.size.height+iconGap;
      break;
    case RARERenderableDataItem_IconPosition_BOTTOM_RIGHT :
      rect.origin.x = contentRect.origin.x + contentRect.size.width - rect.size.width;
      rect.origin.y = titleRect.origin.y+titleRect.size.height+iconGap;
      break;
    case RARERenderableDataItem_IconPosition_RIGHT :
      switch(alignment) {
        case NSTextAlignmentCenter:
          rect.origin.x = MAX(rect.origin.x,titleRect.origin.x+titleSize.width+((titleRect.size.width-titleSize.width)/2)+iconGap);
          break;
        case NSTextAlignmentRight:
          rect.origin.x = titleRect.origin.x + titleRect.size.width+iconGap;
          break;
        default:
          rect.origin.x = titleRect.origin.x + titleSize.width +iconGap;
          break;
      }
      rect.origin.y = MAX(0,titleRect.origin.y + ((titleRect.size.height - rect.size.height) / 2));
      
      break;
      
    case RARERenderableDataItem_IconPosition_RIGHT_JUSTIFIED :
      rect.origin.x = contentRect.origin.x + contentRect.size.width - rect.size.width-iconGap;
      rect.origin.y = MAX(0,titleRect.origin.y + ((titleRect.size.height - rect.size.height) / 2));
      
      break;
    default :
      switch(alignment) {
        case NSTextAlignmentCenter:
          rect.origin.x = MAX(rect.origin.x,((titleRect.size.width-titleSize.width)/2)+titleRect.origin.x-imageSize.width-iconGap);
          break;
        case NSTextAlignmentRight:
          rect.origin.x = MAX(rect.origin.x,titleRect.origin.x+titleRect.size.width-titleSize.width-imageSize.width-iconGap);
          break;
       default:
          rect.origin.x = contentRect.origin.x;
          break;
      }
      rect.origin.y = MAX(0,titleRect.origin.y + ((titleRect.size.height - rect.size.height) / 2));
      break;
  }
  return rect;
}

- (CGRect)titleRectForContentRect:(CGRect)contentRect {
  contentRect=UIEdgeInsetsInsetRect(contentRect, insets_);
  CGSize tsize=titleSize;

  if (!icon_) {
    if(tsize.height<contentRect.size.height) {
      switch (verticalAlighment) {
        case RARERenderableDataItem_VerticalAlign_TOP:
          contentRect.size.height=tsize.height;
          break;
        case RARERenderableDataItem_VerticalAlign_BOTTOM:
          contentRect.origin.y+=(contentRect.size.height-tsize.height);
          contentRect.size.height=tsize.height;
          break;
        default: {
          break;
        }
      }
    }
    return contentRect;
  }
  CGSize imageSize = {[icon_ getIconWidth], [icon_ getIconHeight]};
  if(self.text.length>4 && [[self.text substring:0 endIndex:4] isEqualToString:@"Sear"]) {
    NSLog(@"");
  }
  return [RAREAPLabelView titleRectForContentRect:contentRect titleSize:tsize imageSize:imageSize iconPosition:iconPosition iconGap:iconGap valign:verticalAlighment];
}
+ (CGRect)titleRectForContentRect:(CGRect)contentRect titleSize: (CGSize) tsize imageSize: (CGSize) imageSize iconPosition: (RARERenderableDataItem_IconPosition ) ip iconGap : (int) iconGap valign:(RARERenderableDataItem_VerticalAlign) verticalAlighment{
  if (imageSize.width==0) {
    if(tsize.height<contentRect.size.height) {
      switch (verticalAlighment) {
        case RARERenderableDataItem_VerticalAlign_TOP:
          contentRect.size.height=tsize.height;
          break;
        case RARERenderableDataItem_VerticalAlign_BOTTOM:
          contentRect.origin.y+=(contentRect.size.height-tsize.height);
          contentRect.size.height=tsize.height;
          break;
        default: {
          break;
        }
      }
    }
    return contentRect;
  }
  
  BOOL rtl = [AppleHelper isLTRText];
  CGRect rect = {contentRect.origin, tsize};
  if (ip == RARERenderableDataItem_IconPosition_TRAILING) {
    ip = rtl ? RARERenderableDataItem_IconPosition_LEFT : RARERenderableDataItem_IconPosition_RIGHT;
  }
  else if (ip == RARERenderableDataItem_IconPosition_LEADING || !ip) {
    ip = rtl ? RARERenderableDataItem_IconPosition_RIGHT : RARERenderableDataItem_IconPosition_LEFT;
    
  }
  CGRect imageRect = {0, 0, imageSize};
  switch (ip) {
    case RARERenderableDataItem_IconPosition_TOP_CENTER :
    case RARERenderableDataItem_IconPosition_TOP_LEFT :
    case RARERenderableDataItem_IconPosition_TOP_RIGHT :
      switch (verticalAlighment) {
        case RARERenderableDataItem_VerticalAlign_TOP:
          rect.origin.y = contentRect.origin.y + imageRect.size.height + iconGap;
          rect.size.height=tsize.height;
          break;
        case RARERenderableDataItem_VerticalAlign_BOTTOM:
          rect.origin.y = contentRect.origin.y + contentRect.size.height - rect.size.height;
          rect.size.height=tsize.height;
          break;
        default: {
          CGFloat dy = MAX(0,(contentRect.size.height - imageRect.size.height - rect.size.height-iconGap) / 2);
          rect.origin.y = contentRect.origin.y + imageRect.size.height + dy+iconGap;
          break;
        }
      }
      rect.size.width=contentRect.size.width;
      break;
    case RARERenderableDataItem_IconPosition_BOTTOM_CENTER :
    case RARERenderableDataItem_IconPosition_BOTTOM_LEFT :
    case RARERenderableDataItem_IconPosition_BOTTOM_RIGHT :
      switch (verticalAlighment) {
        case RARERenderableDataItem_VerticalAlign_TOP:
          rect.origin.y = contentRect.origin.y;
          rect.size.height=tsize.height;
          break;
        case RARERenderableDataItem_VerticalAlign_BOTTOM:
          rect.origin.y = contentRect.origin.y + contentRect.size.height - imageRect.size.height - iconGap -tsize.height;
          rect.size.height=tsize.height;
          break;
        default: {
          CGFloat dy =MAX(0, (contentRect.size.height - imageRect.size.height - rect.size.height-iconGap) / 2);
          rect.origin.y = contentRect.origin.y + dy;
          break;
        }
      }
      rect.size.width=contentRect.size.width;
      break;
    case RARERenderableDataItem_IconPosition_RIGHT :
    case RARERenderableDataItem_IconPosition_RIGHT_JUSTIFIED :
      rect.origin.x = contentRect.origin.x;
      rect.size.height=contentRect.size.height;
      rect.size.width=contentRect.size.width-(imageRect.size.width+iconGap);
      break;
      
    default :
      rect.origin.x = contentRect.origin.x + imageRect.size.width + iconGap;
      rect.size.height=contentRect.size.height;
      rect.size.width=contentRect.size.width-(imageRect.size.width+iconGap);
      break;
  }
  return rect;
}

- (void)touchesBegan:(NSSet *)touches withEvent:(UIEvent *)event {
  pressed_=YES;
  if (self.tag & MASK_MOUSE_HANDLER) {
    RAREView *view = self.sparView;
    RAREMouseEvent *me = [[RAREMouseEventEx alloc] initWithId:view withId:event];
    [view->mouseListener_ mousePressedWithRAREMouseEvent:me];
    if (me.isConsumed) {
      return;
    }
  }

  [super touchesBegan:touches withEvent:event];

}

- (void)touchesEnded:(NSSet *)touches withEvent:(UIEvent *)event {
  pressed_=NO;
  if (self.tag & MASK_MOUSE_HANDLER) {
    RAREView *view = self.sparView;
    RAREMouseEvent *me = [[RAREMouseEventEx alloc] initWithId:view withId:event];
    [view->mouseListener_ mouseReleasedWithRAREMouseEvent:me];
    if (me.isConsumed) {
      return;
    }
  }
  [super touchesEnded:touches withEvent:event];
}
- (void)touchesCancelled:(NSSet *)touches withEvent:(UIEvent *)event {
  pressed_=NO;
  if (self.tag & MASK_MOUSE_HANDLER) {
    RAREView *view = self.sparView;
    RAREMouseEvent *me = [[RAREMouseEventEx alloc] initWithId:view withId:event];
    [view->mouseListener_ pressCanceledWithRAREMouseEvent:me];
  }
  [super touchesCancelled:touches withEvent:event];

}

- (void)touchesMoved:(NSSet *)touches withEvent:(UIEvent *)event {
  if (self.tag & MASK_MOUSE_MOTION_HANDLER) {
    RAREView *view = self.sparView;
    RAREMouseEvent *me = [[RAREMouseEventEx alloc] initWithId:view withId:event];
    [view->mouseMotionListener_ mouseDraggedWithRAREMouseEvent:me];
    if (me.isConsumed) {
      return;
    }
  }
  [super touchesMoved:touches withEvent:event];
}
-(CGSize) adjustSize: (CGSize) size {
  int ig=iconGap;
  if(size.height==0) {
    ig=0;
  }
  BOOL rtl = [AppleHelper isLTRText];
  if (icon_) {
    int iw = [icon_ getIconWidth];
    int ih = [icon_ getIconHeight];
    RARERenderableDataItem_IconPosition ip = iconPosition;
    if (ip == RARERenderableDataItem_IconPosition_TRAILING) {
      ip = rtl ? RARERenderableDataItem_IconPosition_LEFT : RARERenderableDataItem_IconPosition_RIGHT;
    }
    else if (ip == RARERenderableDataItem_IconPosition_LEADING || !ip) {
      ip = rtl ? RARERenderableDataItem_IconPosition_RIGHT : RARERenderableDataItem_IconPosition_LEFT;

    }
    switch (ip) {
      case RARERenderableDataItem_IconPosition_TOP_CENTER :
      case RARERenderableDataItem_IconPosition_TOP_LEFT :
      case RARERenderableDataItem_IconPosition_TOP_RIGHT :
      case RARERenderableDataItem_IconPosition_BOTTOM_CENTER :
      case RARERenderableDataItem_IconPosition_BOTTOM_LEFT :
      case RARERenderableDataItem_IconPosition_BOTTOM_RIGHT :
        size.height += ig + ih;
        size.width = MAX(size.width, iw);
        break;
      default:
        size.width += ig + iw;
        size.height = MAX(size.height, ih);
        break;
    }
  }
  size.width += insets_.left + insets_.right;
  size.height += insets_.top + insets_.bottom;
  return size;
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
