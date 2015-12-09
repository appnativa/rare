//
//  RAREUIControl.m
//  RareIOS
//
//  Created by Don DeCoteau on 5/19/13.
//  Copyright (c) 2013 SparseWare. All rights reserved.
//

#import "RAREUIControl.h"
#import "RAREAPLabelView.h"
#import "RAREImageWrapper.h"
#import "AppleHelper.h"
#import "APView+Component.h"
#import "com/appnativa/rare/ui/UIImageIcon.h"
#import "com/appnativa/rare/ui/UIInsets.h"
#import "com/appnativa/rare/ui/UIColor.h"
#import "com/appnativa/rare/platform/apple/ui/util/AppleGraphics.h"
#import "com/appnativa/rare/ui/event/MouseEvent.h"
#import "com/appnativa/rare/ui/listener/iMouseListener.h"
#import "com/appnativa/rare/ui/listener/iMouseMotionListener.h"
#import "com/appnativa/rare/platform/apple/ui/view/ParentView.h"
#import "com/appnativa/rare/platform/apple/ui/view/CustomButtonView.h"
#import "com/appnativa/rare/ui/text/HTMLCharSequence.h"

@implementation RAREUIControl {
  CGSize titleSize;
  CGFloat centeredIconOffset;
}
@synthesize icon = defaultIcon;
@synthesize iconGap = iconGap;
@synthesize iconPosition = iconPosition;
@synthesize pressedIcon = pressedIcon;
@synthesize pressedSelectedIcon = pressedSelectedIcon;
@synthesize pressedIndeterminateIcon = pressedIndeterminateIcon;
@synthesize disabledIcon = disabledIcon;
@synthesize disabledSelectedIcon = disabledSelectedIcon;
@synthesize disabledIndeterminateIcon = disabledIndeterminateIcon;
@synthesize selectedIcon = selectedIcon;
@synthesize textVerticalAlignment = verticalAlignment;
@synthesize textHorizontalAlighment = horizontalAlighment;
@synthesize isToggle = isToggle;
@synthesize isUnderlined;

+ (Class)layerClass
{
  return [RARECAGradientLayer class];
}
- (id)initWithFrame:(CGRect)frame {
  self = [super initWithFrame:frame];
  if (self) {
    insets_ = UIEdgeInsetsMake(2, 2, 2, 2);
    self.userInteractionEnabled = YES;
    self.opaque = NO;
    self.isToggle = NO;
    autoRepeatTimeInterval_ = 300;
    iconGap = 4;
    
  }

  return self;
}
- (void)sparDispose {
  if (mouseDownTimer_) {
    [mouseDownTimer_ invalidate];
  }
  [super sparDispose];
  disabledIcon = nil;
  disabledIndeterminateIcon = nil;
  disabledSelectedIcon = nil;
  selectedIcon = nil;
  switchView = nil;
  pressedIcon = nil;
  pressedIndeterminateIcon = nil;
  pressedSelectedIcon = nil;
  indeterminateIcon = nil;
  titleLabel = nil;
  mouseDownTimer_ = nil;
}
-(void) setCenteredIconOffset: (CGFloat) offset {
  centeredIconOffset=offset;
}
- (BOOL)becomeFirstResponder {
  BOOL ok = [super becomeFirstResponder];
  if (ok) {
    [self gainedFocusEx];
  }
  return ok;
}

- (BOOL)resignFirstResponder {
  BOOL ok = [super resignFirstResponder];
  if (ok) {
    [self lostFocusEx];
  }
  return ok;
}

- (void)setContinuous:(BOOL)continuous {
  continuous_ = continuous;
}

- (void)setPeriodicDelay:(float)delay interval:(float)interval {
  autoRepeatTimeInterval_ = interval;
}

- (void)setCharSequence:(id <JavaLangCharSequence>)text {
  if (!text) {
    if (titleLabel) {
      titleLabel.text = @"";
    }
    return;
  }
  if (self.isUnderlined) {
    if ([text isKindOfClass:[RAREHTMLCharSequence class]]) {
      if (self.label.lineBreakMode == NSLineBreakByTruncatingTail) {
        self.label.lineBreakMode = NSLineBreakByWordWrapping;
        self.label.numberOfLines = 0;
      }
    }
    self.label.attributedText = [AppleHelper createUnderlinedString:text];
  }
  else {
    [self.label setCharSequence:text];
  }
  [self setNeedsLayout];
}

-(void)setHighlighted:(BOOL)highlighted {
  [super setHighlighted:highlighted];
  RARECustomButtonView *view = (RARECustomButtonView *) self.sparView;
  [view stateChanged];
  [self setNeedsDisplay];
  if (self.callNeedsDisplayOnSuper) {
    [self.superview setNeedsDisplay];
  }
}

- (void)touchesBegan:(NSSet *)touches withEvent:(UIEvent *)event {
  RARECustomButtonView *view = (RARECustomButtonView *) self.sparView;
  if (continuous_ && view->actionListener_) {
    if (mouseDownTimer_) {
      [mouseDownTimer_ invalidate];
      mouseDownTimer_ = nil;
    }
    mouseDownTimer_ = [NSTimer scheduledTimerWithTimeInterval:autoRepeatTimeInterval_
                                                       target:self
                                                     selector:@selector(mouseDownTimerFired:)
                                                     userInfo:event
                                                      repeats:YES];
    [view actionPerformed];
  }
  [super touchesBegan:touches withEvent:event];
  
  [view stateChanged];
  [self setNeedsDisplay];
  if (self.callNeedsDisplayOnSuper) {
    [self.superview setNeedsDisplay];
  }
}

- (void)touchesCancelled:(NSSet *)touches withEvent:(UIEvent *)event {
  if (mouseDownTimer_) {
    [mouseDownTimer_ invalidate];
    mouseDownTimer_ = nil;
  }
  if (!isToggle) {
    self.highlighted = NO;
  }
  [self setNeedsDisplay];
  if (self.callNeedsDisplayOnSuper) {
    [self.superview setNeedsDisplay];
  }
  [super touchesCancelled:touches withEvent:event];
}

- (void)touchesEnded:(NSSet *)touches withEvent:(UIEvent *)event {
  if (mouseDownTimer_) {
    [mouseDownTimer_ invalidate];
    mouseDownTimer_ = nil;
  }
  if(self.highlighted) {
    [super touchesEnded:touches withEvent:event];
    if (!self.radioButtonStyle || !self.selected) {
      if (isToggle) {
        if (self.selected) {
          if (triState) {
            if (indeterminate) {
              self.selected = NO;
              indeterminate = NO;
            }
            else {
              indeterminate = YES;
            }
          }
          else {
            self.selected = NO;
          }
        }
        else {
          self.selected = YES;
        }
      }
      else {
        self.selected = NO;
      }
    }
    RARECustomButtonView *view = (RARECustomButtonView *) self.sparView;
    if (!continuous_) {
      [view actionPerformed];
    }
  }
  else {
    [super touchesEnded:touches withEvent:event];
  }
}

- (void)mouseDownTimerFired:(NSTimer *)timer {
  RARECustomButtonView *view = (RARECustomButtonView *) self.sparView;
  [view actionPerformed];
}


- (NSString *)getText {
  if (titleLabel) {
    return titleLabel.text;
  }
  return @"";
}

- (void)setText:(NSString *)text {
  if (!text) {
    if (titleLabel) {
      titleLabel.text = @"";
    }
    return;
  }
  self.label.text = text;
  [self setNeedsLayout];
}

- (BOOL)isPressed {
  return self.highlighted;
}

- (void)setFont:(UIFont *)font {
  self.label.font = font;
  [self setNeedsLayout];
}

- (void)setTextColor:(UIColor *)color {
   self.label.textColor = color;
}

- (void)setAttributedText:(NSAttributedString *)attributedText {
  [self.label setAttributedText:attributedText];
  [self setNeedsLayout];
}

- (void)setIcon:(id <RAREiPlatformIcon>)icon {
  defaultIcon = icon;
  if (switchView) {
    switchView.offImage = [RAREImageWrapper getImageFromIcon:icon forView:self.sparView];
  }
  [self setNeedsLayout];
}

- (void)setSelectedIcon:(id <RAREiPlatformIcon>)icon {
  selectedIcon = icon;
  if (switchView) {
    switchView.offImage = [RAREImageWrapper getImageFromIcon:icon forView:self.sparView];
  }
  [self setNeedsLayout];
}

- (void)setInsetsWithTop:(int)top right:(int)right bottom:(int)bottom left:(int)left {
  insets_ = UIEdgeInsetsMake(top, left, bottom, right);
  [self setNeedsLayout];
}

- (void)getInsets:(RAREUIInsets *)insets {
  insets->top_ = insets_.top;
  insets->right_ = insets_.right;
  insets->bottom_ = insets_.bottom;
  insets->left_ = insets_.left;
}

- (void)makeTriState:(id <RAREiPlatformIcon>)icon  pressedIcon: picon{
  indeterminateIcon = icon;
  pressedIndeterminateIcon=picon;
  triState = YES;
  [self setNeedsLayout];
}

- (void)setSelected:(BOOL)selected {
  if (selected != self.selected) {
    RARECustomButtonView *view = (RARECustomButtonView *) self.sparView;
    [super setSelected:selected];
    if (switchView) {
      switchView.on = selected;
    }
    [self setNeedsDisplay];
    [view stateChanged];
    [view selectionChangedWithBoolean:selected];
  }
}

- (void)makeSwitch {
  if (!switchView) {
    switchView = [[UISwitch alloc] initWithFrame:CGRectZero];
    if (defaultIcon) {
      switchView.offImage = [RAREImageWrapper getImageFromIcon:defaultIcon forView:self.sparView];
    }
    if (selectedIcon) {
      switchView.offImage = [RAREImageWrapper getImageFromIcon:selectedIcon forView:self.sparView];
    }
    [switchView addTarget:self action:@selector(switchEvents:) forControlEvents: UIControlEventValueChanged];
    [self addSubview:switchView];
  }
}
- (void)switchEvents:(id)sender  {
  RARECustomButtonView *view = (RARECustomButtonView *) self.sparView;
  self.selected=switchView.isOn;
  [view stateChanged];
  [view handleChangeEvent];
  [view actionPerformed];
}
- (void)setIndeterminate {
  indeterminate = YES;
  [[self sparView] stateChanged];
  [self setNeedsDisplay];
}

- (BOOL)isIndeterminate {
  return indeterminate;
}

- (UIControlState)state {
  NSInteger returnState = [super state];
  if (indeterminate) {
    returnState |= UIControlStateApplication;
  }
  return returnState;
}
- (CGSize)getPreferredSize:(CGFloat)maxWidth {
  if(maxWidth<1) {
    maxWidth=20000;
  }
  CGSize constraint = CGSizeMake(maxWidth, 20000.0f);
  return [self sizeThatFits:constraint];
}

- (CGSize)preferredSize {
  return [self sizeThatFits:self.frame.size];
}

- (CGSize)sizeThatFits:(CGSize)size {
  CGFloat width = insets_.left + insets_.right;
  CGFloat height = insets_.top + insets_.bottom;
  if (titleLabel) {
    size = [titleLabel sizeThatFits:size];
    titleSize=size;
  }
  CGSize imageSize = CGSizeZero;
  if (switchView) {
    imageSize = switchView.frame.size;
  }
  else {
    id <RAREiPlatformIcon> ic = [self getIconForCurrentState];
    if (ic) {
      imageSize.width = [ic getIconWidth];
      imageSize.height = [ic getIconHeight];
    }
  }
  if (imageSize.width > 0) {
    BOOL rtl = [AppleHelper isLTRText];
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
        width += MAX(imageSize.width, size.width);
        height += iconGap + imageSize.height + size.height;
        break;
      default:
        width += iconGap + imageSize.width + size.width;
        height += MAX(imageSize.height, size.height);
        break;
    }
  }
  else {
    width += size.width;
    height += size.height;
  }
  if (self.isUnderlined) {
    height += 2;
  }
  return CGSizeMake(width, height);
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
  if (!titleLabel) {
    titleLabel = [UILabel new];
    [self addSubview:titleLabel];
  }
  switch (a) {
    case RARERenderableDataItem_HorizontalAlign_AUTO:
      titleLabel.textAlignment = NSTextAlignmentNatural;
      break;
    case RARERenderableDataItem_HorizontalAlign_RIGHT:
      titleLabel.textAlignment = NSTextAlignmentRight;
      break;
    case RARERenderableDataItem_HorizontalAlign_CENTER:
      titleLabel.textAlignment = NSTextAlignmentCenter;
      break;
    default:
      titleLabel.textAlignment = NSTextAlignmentLeft;
      break;
  }
}

- (id <RAREiPlatformIcon>)getIconForCurrentState {
  if (!self.enabled) {
    if (self.selected) {
      if (indeterminate && disabledIndeterminateIcon) {
        return disabledIndeterminateIcon;
      }
      if (disabledSelectedIcon) {
        return disabledSelectedIcon;
      }
    }
    if (disabledIcon) {
      return disabledIcon;
    }
  }
  if (self.highlighted) {
    if (self.selected) {
      if (indeterminate) {
        return pressedIndeterminateIcon ? pressedIndeterminateIcon : indeterminateIcon;
      }
      if (pressedSelectedIcon) {
        return pressedSelectedIcon;
      }
      if (selectedIcon) {
        return selectedIcon;
      }
    }
    else {
      if (indeterminate) {
        return pressedIndeterminateIcon ? pressedIndeterminateIcon : indeterminateIcon;
      }
      if (pressedIcon) {
        return pressedIcon;
      }
    }
  }
  else if (self.selected) {
    if (indeterminate) {
      return indeterminateIcon;
    }

    if (self.highlighted && pressedSelectedIcon) {
      return pressedSelectedIcon;
    }
    if (selectedIcon) {
      return selectedIcon;
    }
    if (pressedIcon) {
      return pressedIcon;
    }
  }
  if (indeterminate) {
    return indeterminateIcon;
  }
  return defaultIcon;
}

- (void)drawRect:(CGRect)dirtyRect {
  CGContextRef ctx = UIGraphicsGetCurrentContext();
  RAREView *view = self.sparView;
  RAREAppleGraphics *g = [[RAREAppleGraphics alloc] initWithId:(__bridge id) ctx withRAREView:view];
  RAREUIRectangle *rect = [self sparBounds];
  [view paintBackgroundWithRAREAppleGraphics:g withRAREView:view withRAREUIRectangle:rect];
  if (!switchView) {
    id <RAREiPlatformIcon> ic = [self getIconForCurrentState];
    if (ic) {
      CGFloat offset=0;
      if(centeredIconOffset!=0 && titleSize.width==0) {
        offset=centeredIconOffset;
      }
      CGFloat x=imageRect_.origin.x+offset;
      CGFloat y=imageRect_.origin.y;
      [ic paintWithRAREiPlatformGraphics:g withFloat:x withFloat:imageRect_.origin.y withFloat:rect->width_-x withFloat:rect->height_-y];
    }
  }
  [super drawRect:dirtyRect];
  [view paintOverlayWithRAREAppleGraphics:g withRAREView:view withRAREUIRectangle:rect];
  [g dispose];
}

- (void)layoutSubviews {
  if (switchView) {
    imageRect_ = switchView.bounds;
  }
  else {
    id <RAREiPlatformIcon> ic = [self getIconForCurrentState];
    if (ic) {
      imageRect_ = CGRectMake(0, 0, [ic getIconWidth], [ic getIconHeight]);
    }
    else {
      imageRect_ = CGRectZero;
    }

  }
  CGRect contentRect = self.bounds;
  contentRect = UIEdgeInsetsInsetRect(contentRect, insets_);
  CGRect titleRect = CGRectZero;
  if (titleLabel && titleLabel.text.length>0) {
    titleLabel.frame = titleRect = [self titleRectForContentRect:contentRect imageRect:imageRect_];
  }
  imageRect_ = [self imageRectForContentRect:contentRect titleRect:titleRect];
  if (switchView) {
    switchView.frame = imageRect_;
  }
}

- (CGRect)imageRectForContentRect:(CGRect)contentRect titleRect: (CGRect) titleRect{
  NSTextAlignment al= titleLabel ? titleLabel.textAlignment : NSTextAlignmentLeft;
  return [RAREAPLabelView imageRectForContentRect:contentRect titleRect:titleRect titleSize: titleSize imageSize:imageRect_.size iconPosition:iconPosition iconGap:iconGap textAlignment: al];
}


- (void)setWrapText:(BOOL)wrap {
  if (wrap) {
    self.label.lineBreakMode = NSLineBreakByWordWrapping;
    self.label.numberOfLines = 0;
  }
  else {
    self.label.lineBreakMode = NSLineBreakByTruncatingTail;
    self.label.numberOfLines = 1;
  }

}

- (BOOL)isWrapText {
  switch (self.label.lineBreakMode) {
    case NSLineBreakByWordWrapping:
    case NSLineBreakByCharWrapping:
      return YES;
    default:
      return false;
  }
}

- (CGRect)titleRectForContentRect:(CGRect)contentRect imageRect:(CGRect)imageRect {
  CGSize tsize=[titleLabel sizeThatFits:self.bounds.size];
  return [RAREAPLabelView titleRectForContentRect:contentRect titleSize:tsize imageSize:imageRect_.size iconPosition:iconPosition iconGap:iconGap valign:verticalAlighment];
}

- (UILabel *)label {
  if (!titleLabel) {
    titleLabel = [UILabel new];
    titleLabel.backgroundColor = [UIColor clearColor];
    titleLabel.userInteractionEnabled = NO;
    titleLabel.textAlignment = NSTextAlignmentCenter;
    titleLabel.lineBreakMode = NSLineBreakByWordWrapping;
    titleLabel.numberOfLines = 0;
    [self addSubview:titleLabel];
  }
  return titleLabel;
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
  RAREView *view = self.sparView;
  if (view && view->viewListener_ && self.window != newWindow) {
    [view visibilityChangedWithBoolean:newWindow != nil];
  }
}

@end
