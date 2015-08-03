//
//  NSView+ViewWithTags.m
//  RareOSX
//
//  Created by Don DeCoteau on 3/19/13.
//  Copyright (c) 2013 SparseWare. All rights reserved.
//
#import "com/appnativa/rare/ui/UIDimension.h"
#import "com/appnativa/rare/platform/apple/ui/util/AppleGraphics.h"
#import "com/appnativa/rare/ui/event/MouseEventEx.h"
#import "com/appnativa/rare/ui/listener/iFocusListener.h"
#import "com/appnativa/rare/ui/painter/UIBackgroundPainter.h"
#import "com/appnativa/rare/ui/painter/UIImagePainter.h"
#import "com/appnativa/rare/ui/UIImage.h"
#import "com/appnativa/rare/ui/UIInsets.h"
#import "com/appnativa/rare/platform/apple/ui/view/View.h"
#import "java/util/Calendar.h"
#import "APView+Component.h"
#import "RAREAPView.h"
#import "RAREAPWindow.h"
#import "RAREAPApplication.h"
#import "AppleHelper.h"
#import "com/appnativa/rare/ui/text/HTMLCharSequence.h"
#import "RAREImageWrapper.h"
#import "IOSFloatArray.h"
#import "com/appnativa/rare/ui/UIColor.h"
#import "RARECAGradientLayer.h"
#import "RARECALayer.h"
#import <objc/runtime.h>
static char const * const ViewTagKey = "RAREViewTag";
static CGFloat osVersion=0;

@implementation NSDate (RAREUtils)
- (BOOL)isWeekend {
    NSCalendar *calendar = [NSCalendar currentCalendar];
    NSRange weekdayRange = [calendar maximumRangeOfUnit:NSWeekdayCalendarUnit];
    NSDateComponents *components = [calendar components:NSWeekdayCalendarUnit fromDate:self];
    NSUInteger weekdayOfDate = [components weekday];

    if (weekdayOfDate == weekdayRange.location || weekdayOfDate == weekdayRange.length) {
        return YES;
    }
    return NO;
}

+ (NSDate *)fromJavaCalendar:(JavaUtilCalendar *)date {
    return [[NSDate alloc] initWithTimeIntervalSince1970:(([date getTimeInMillis])/1000)];
}

+(NSDate*) fromJavaDate:(JavaUtilDate *)date
{
    return [[NSDate alloc] initWithTimeIntervalSince1970:(([date getTime])/1000)];

}
+(JavaUtilDate*) toDate:(NSDate *)date
{
    return [[JavaUtilDate alloc] initWithLong:(([date timeIntervalSince1970])*1000)];
}

@end
#if TARGET_OS_IPHONE

@implementation UIScreen (OrientatedDimensions)
-(CGRect) adjustRectForOrientation: (CGRect) frame {
  if ([UIScreen osVersionAsFloat]<8) {
    UIInterfaceOrientation io=[UIApplication sharedApplication].statusBarOrientation;
    CGSize ss=self.bounds.size;
    CGFloat x=frame.origin.x;
    CGFloat y=frame.origin.y;
    switch(io) {
      case UIInterfaceOrientationLandscapeLeft: {
        CGFloat xx=x;
        x=y;
        y=ss.height-frame.size.height-xx;
        break;
      }
      case UIInterfaceOrientationLandscapeRight: {
        CGFloat xx=x;
        x=ss.width-frame.size.width-y;
        y=xx;
        break;
      }
      case UIInterfaceOrientationPortraitUpsideDown:
        x=ss.width-x-frame.size.width;
        y=ss.height-y-frame.size.height;
        break;
      default:
        break;
        
    }
    frame.origin.x=x;
    frame.origin.y=y;
    if (UIInterfaceOrientationIsLandscape(io)) {
      CGFloat w=frame.size.width;
      frame.size.width=frame.size.height;
      frame.size.height=w;
    }
  }
  return frame;
}

+(CGSize) currentSize
{
  return [UIScreen sizeInOrientation:[UIApplication sharedApplication].statusBarOrientation ofScreen:[UIScreen mainScreen]];
}

+(CGRect) currentBounds
{
  return [[UIScreen mainScreen] orientedBounds];
}
+(CGRect) currentFrame
{
  return [[UIScreen mainScreen] orientedBounds];
}
-(CGRect) orientedFrame
{
  CGRect frame = [UIScreen mainScreen].bounds;
  UIApplication *application = [UIApplication sharedApplication];
  if ([UIScreen osVersionAsFloat]<8) {
    if (UIInterfaceOrientationIsLandscape(application.statusBarOrientation))
    {
      frame.size = CGSizeMake(frame.size.height, frame.size.width);
    }
  }
  if (application.statusBarHidden == NO)
  {
    frame.origin.y    = MIN(application.statusBarFrame.size.width, application.statusBarFrame.size.height);
    frame.size.height -=frame.origin.y;
  }
  return frame;
}
+(CGFloat) osVersionAsFloat {
  if(osVersion==0) {
    osVersion=[[[UIDevice currentDevice] systemVersion] floatValue];
  }
  return osVersion;
  
}
-(CGRect) orientedBounds
{
  CGSize size=[UIScreen sizeInOrientation:[UIApplication sharedApplication].statusBarOrientation ofScreen:self];
  return CGRectMake(0,0,size.width,size.height);
}
-(CGSize) orientedSize
{
  return [UIScreen sizeInOrientation:[UIApplication sharedApplication].statusBarOrientation ofScreen:self];
}

+(CGSize) sizeInOrientation:(UIInterfaceOrientation)orientation ofScreen: (UIScreen*) screen
{
  CGSize size = screen.bounds.size;
  
  if ([self osVersionAsFloat]<8) {
    UIApplication *application = [UIApplication sharedApplication];
    if (UIInterfaceOrientationIsLandscape(orientation))
    {
      size = CGSizeMake(size.height, size.width);
    }
    if (application.statusBarHidden == NO)
    {
      size.height -= MIN(application.statusBarFrame.size.width, application.statusBarFrame.size.height);
    }
  }
  return size;
}

+(CGPoint) convertPoint:(CGPoint) pt withScreen: (UIScreen*) screen {
  CGSize size = screen.bounds.size;
  if ([self osVersionAsFloat]<8) {
    CGFloat f;
    UIApplication *application = [UIApplication sharedApplication];
    switch(application.statusBarOrientation) {
      case UIInterfaceOrientationLandscapeRight:
        pt.x=size.width-pt.x;
        f=pt.x;
        pt.x=pt.y;
        pt.y=f;
        break;
      case UIInterfaceOrientationLandscapeLeft:
        pt.y=size.height-pt.y;
        f=pt.x;
        pt.x=pt.y;
        pt.y=f;
        break;
      case UIInterfaceOrientationPortrait:
        break;
      case UIInterfaceOrientationPortraitUpsideDown:
        pt.x=size.width-pt.x;
        pt.y=size.height-pt.y;
        break;
      default:
        break;
    }
  }
  return pt;
}
@end

@implementation UILabel (CharSequenceSupport)

-(id<JavaLangCharSequence>)  getCharSequence {
    NSAttributedString *at = self.attributedText;
    if(!at) {
        return self.text;
    }
    return [[RAREHTMLCharSequence alloc] initWithNSString:self.text withId:at];
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
}

-(void) setWrapText: (BOOL) wrap {
  if(wrap) {
    self.lineBreakMode=NSLineBreakByWordWrapping;
    self.numberOfLines = 0;
  }
  else {
    self.lineBreakMode=NSLineBreakByTruncatingTail;
    self.numberOfLines = 1;
  }

}
-(BOOL) isWrapText{
  switch(self.lineBreakMode) {
    case NSLineBreakByWordWrapping:
    case NSLineBreakByCharWrapping:
      return YES;
    default:
      return NO;
  }
}

-(void) setCharSequence: (id<JavaLangCharSequence>) text {
  if([text isKindOfClass: [RAREHTMLCharSequence class]]) {
    self.attributedText=(NSAttributedString*)((RAREHTMLCharSequence*)text)->attributedText_;
    if(self.lineBreakMode==NSLineBreakByTruncatingTail) {
      self.lineBreakMode=NSLineBreakByWordWrapping;
      self.numberOfLines = 0;
    }
  }
  else {
    self.text=text ? text.description : @"";
  }
}
-(void) setInsetsWithTop: (int) top right: (int)right bottom: (int) bottom left: (int) left {
  
}
-(void) setIconGap: (int) gap {
  
}
-(void) setIconPosition: (RARERenderableDataItem_IconPosition) position  {
  
}
-(void) setTextVerticalAlignment: (RARERenderableDataItem_VerticalAlign) alignment {
  
}
-(void) setIcon: (id<RAREiPlatformIcon>) icon {
  
}
-(BOOL) isPressed {
  return NO;
}
-(void) setPressed: (BOOL) pressed {
  
}
@end

@implementation UITextField (CharSequenceSupport)
-(void) setCharSequence: (id<JavaLangCharSequence>) text {
  if([text isKindOfClass: [RAREHTMLCharSequence class]]) {
    self.attributedText=(NSAttributedString*)((RAREHTMLCharSequence*)text)->attributedText_;
  }
  else {
    self.text=text ? text.description : @"";
  }
}

-(NSString*) getHTML {
  return self.text;
}

- (NSRange) selectedRangeEx
{
	UITextPosition* beginning = self.beginningOfDocument;
  
	UITextRange* selectedRange = self.selectedTextRange;
  if(!selectedRange) {
    return NSMakeRange(0,0);
  }
	UITextPosition* selectionStart = selectedRange.start;
  UITextPosition* selectionEnd = selectedRange.end;
  
	const NSInteger location = [self offsetFromPosition:beginning toPosition:selectionStart];
	const NSInteger length = [self offsetFromPosition:selectionStart toPosition:selectionEnd];
  
	return NSMakeRange(location, length);
}

- (void) setSelectedRangeEx:(NSRange) range
{
	UITextPosition* beginning = self.beginningOfDocument;
  
	UITextPosition* startPosition = [self positionFromPosition:beginning offset:range.location];
	UITextPosition* endPosition = [self positionFromPosition:beginning offset:range.location + range.length];
	UITextRange* selectionRange = [self textRangeFromPosition:startPosition toPosition:endPosition];
  
	[self setSelectedTextRange:selectionRange];
}
@end

@implementation UITextView (CharSequenceSupport)
-(void) setCharSequence: (id<JavaLangCharSequence>) text {
  if([text isKindOfClass: [RAREHTMLCharSequence class]]) {
    self.attributedText=(NSAttributedString*)((RAREHTMLCharSequence*)text)->attributedText_;
  }
  else {
    self.text=text ? text.description : @"";
  }
}

-(id<JavaLangCharSequence>)  getCharSequence {
    NSAttributedString *at = self.attributedText;
    if(!at) {
        return self.text;
    }
    return [[RAREHTMLCharSequence alloc] initWithNSString:self.text withId:at];
}

-(NSString*) getHTML {
  return self.text;
}

- (NSRange) selectedRangeEx
{
	UITextPosition* beginning = self.beginningOfDocument;
  
	UITextRange* selectedRange = self.selectedTextRange;
  if(!selectedRange) {
    return NSMakeRange(0,0);
  }
	UITextPosition* selectionStart = selectedRange.start;
  UITextPosition* selectionEnd = selectedRange.end;
  
	const NSInteger location = [self offsetFromPosition:beginning toPosition:selectionStart];
	const NSInteger length = [self offsetFromPosition:selectionStart toPosition:selectionEnd];
  
	return NSMakeRange(location, length);
}

- (void) setSelectedRangeEx:(NSRange) range
{
	UITextPosition* beginning = self.beginningOfDocument;
  
	UITextPosition* startPosition = [self positionFromPosition:beginning offset:range.location];
	UITextPosition* endPosition = [self positionFromPosition:beginning offset:range.location + range.length];
	UITextRange* selectionRange = [self textRangeFromPosition:startPosition toPosition:endPosition];
  
	[self setSelectedTextRange:selectionRange];
}
@end

@implementation UITableView (RowNumberSupport)
-(BOOL) isRowSelected: (int) row {
  BOOL selected=NO;
  NSIndexPath* indexPath=[NSIndexPath indexPathForRow:row inSection:0];
  if(self.allowsMultipleSelection) {
    NSArray* rows=self.indexPathsForSelectedRows;
    if(rows) {
      selected=[rows indexOfObject:indexPath]!=NSNotFound ;
    }
  }
  else {
    selected=[indexPath isEqual:self.indexPathForSelectedRow];
  }
  return selected;
}
@end

@implementation UITableViewCell (CharSequenceSupport)
-(void) setCharSequence: (id<JavaLangCharSequence>) text {
  if([text isKindOfClass: [RAREHTMLCharSequence class]]) {
    self.textLabel.attributedText=(NSAttributedString*)((RAREHTMLCharSequence*)text)->attributedText_;
  }
  else {
    self.textLabel.text=text ? text.description : @"";
  }
}
@end

@implementation UIButton (SparButton)

-(void) setWrapText: (BOOL) wrap {
  [self.titleLabel setWrapText:wrap];
}
-(BOOL) isWrapText{
  return self.titleLabel.isWrapText;
}

-(id<JavaLangCharSequence>)  getCharSequence {
  return self.titleLabel.getCharSequence;
}

-(void) setPeriodicDelay: (float) delay interval: (float) interval {
  
}
-(void) setContinuous: (BOOL) continuous {
  
}
-(BOOL) isPressed {
  return self.highlighted;
}
-(void) setFont: (UIFont*) font {
  self.titleLabel.font=font;
}
-(void) setTextColor: (UIColor*) color {
  self.titleLabel.textColor=color;
}

-(void) setText: (NSString*) text  {
  self.titleLabel.text=text;
}

-(void) setCharSequence: (id<JavaLangCharSequence>) text {
  if([text isKindOfClass: [RAREHTMLCharSequence class]]) {
    [self setAttributedTitle: (NSAttributedString*)((RAREHTMLCharSequence*)text)->attributedText_ forState:UIControlStateNormal];
  }
  else {
    [self setTitle:text ? text.description : @"" forState:UIControlStateNormal];
  }
  [self.titleLabel setCharSequence: text];
}


-(void) setIcon: (id<RAREiPlatformIcon>) icon {
  if(icon!=nil) {
    [self setImage:[RAREImageWrapper getImageFromIcon:icon forView:self.sparView] forState: UIControlStateNormal];
  }
}

-(void) setPressedIcon: (id<RAREiPlatformIcon>) icon {
  if(icon!=nil) {
    [self setImage:[RAREImageWrapper getImageFromIcon:icon forView:self.sparView] forState: UIControlStateHighlighted];
  }
}

-(void) setSelectedIcon: (id<RAREiPlatformIcon>) icon {
  if(icon!=nil) {
    [self setImage:[RAREImageWrapper getImageFromIcon:icon forView:self.sparView] forState: UIControlStateSelected];
  }
}

-(void) setAlternateIcon: (id<RAREiPlatformIcon>) icon {
  if(icon!=nil) {
    [self setImage:[RAREImageWrapper getImageFromIcon:icon forView:self.sparView] forState: UIControlStateApplication];
  }
}

-(void) setDisabledIcon: (id<RAREiPlatformIcon>) icon {
  if(icon!=nil) {
    [self setImage:[RAREImageWrapper getImageFromIcon:icon forView:self.sparView] forState: UIControlStateDisabled];
  }
}

-(void) setInsetsWithTop: (int) top right: (int)right bottom: (int) bottom left: (int) left {
  self.contentEdgeInsets=UIEdgeInsetsMake(top, left, bottom, right);
}

-(void) getInsets:(RAREUIInsets*) insets {
  insets->top_=self.contentEdgeInsets.top;
  insets->right_=self.contentEdgeInsets.right;
  insets->bottom_=self.contentEdgeInsets.bottom;
  insets->left_=self.contentEdgeInsets.left;
}

-(void) setIconGap: (int) gap {
}

-(void) setIconPosition: (RARERenderableDataItem_IconPosition) position {
}

-(void) setTextVerticalAlignment: (RARERenderableDataItem_VerticalAlign) alignment {
}

-(void) setTextHorizontalAlignment: (RARERenderableDataItem_HorizontalAlign) alignment {
}
@end

@implementation UIView (SparView)
-(void) addRecognizerEx: (UIGestureRecognizer*) r {
  [self addGestureRecognizer:r];
}
-(void) rareRenderInContect: (CGContextRef) ctx; {
  [self.layer renderInContext:ctx];
  [self.subviews enumerateObjectsUsingBlock:^(id obj, NSUInteger idx, BOOL *stop) {
    [((UIView*)obj) rareRenderInContect:ctx];
  }];
}

-(CGSize) preferredSize {
  return [self sizeThatFits:self.frame.size];//CGSizeMake(0, 0)];
}

-(CGFloat) getPreferredHeight: (CGFloat) width {
  return [self sizeThatFits:CGSizeMake(width, 20000)].height;
}

-(CGSize) getPreferredSize: (CGFloat) maxWidth {
  if(maxWidth<1) {
    return [self sizeThatFits:self.frame.size];
  }
  return [self sizeThatFits:CGSizeMake(maxWidth, 20000)];
}

- (CGPoint)getLocationOnScreenExNew{
  if(!self.window) return CGPointZero;
  CGAffineTransform t=self.superview.transform;
  
  CGPoint p=self.frame.origin;
  t=CGAffineTransformInvert(t);
  p=CGPointApplyAffineTransform(p,t);
  p=[self.superview convertPoint:p toView:nil];
  CGPoint wp=self.window.frame.origin;
  p.x+=wp.x;
  p.y+=wp.y;
  UIScreen* s=self.window.screen;
  p=[UIScreen convertPoint:p withScreen:s];
  return p;
}
- (CGPoint)getLocationOnScreenEx{
  if(!self.window) return CGPointZero;
  CGRect frame=self.frame;
  CGPoint p = [self.superview convertPoint:frame.origin toView:nil];
  CGPoint wp=self.window.frame.origin;
  p.x+=wp.x;
  p.y+=wp.y;
  UIScreen* s=self.window.screen;
  p=[UIScreen convertPoint:p withScreen:s];
  return p;
}
#else
@implementation NSView (SparView)

- (CGPoint)getLocationOnScreenEx{
  NSView* view=(NSView*)proxy_;
  NSRect frameRelativeToWindow = [view convertRect:view.bounds toView:nil];
  NSRect r = [view.window convertRectToScreen:frameRelativeToWindow];
  NSScreen* screen=[NSScreen mainScreen];
  return CGPointMake(r.origin.x,screen.frame.size.height-r.origin.y-r.size.height);
}
#endif
@dynamic sparView;

- (RAREView*) sparView {
  return objc_getAssociatedObject(self, ViewTagKey);
}

-(RARECALayer*) getOverlayLayerCreate: (BOOL) create {
  CALayer* l=self.layer;
#if !TARGET_OS_IPHONE
  if(!l) {
    [self setLayer: l=[RARECAGradientLayer new]];
  }
#endif
  if(![l isKindOfClass:[RARECAGradientLayer class]]) {
    return nil;
  }
  RARECAGradientLayer* layer=(RARECAGradientLayer*)l;
  return [layer getOverLayerCreate:YES forRAREView:self.sparView];
}
-(BOOL) isPaintEnabled {
  return (self.tag & MASK_PAINT_HANDLER);
}

-(void) setOverlayBorderPath:(id) path  {
  CALayer* l=self.layer;
#if !TARGET_OS_IPHONE
  if(!l) {
    [self setLayer: l=[RARECAGradientLayer new]];
  }
#endif
  if(![l isKindOfClass:[RARECAGradientLayer class]]) {
    return;
  }
  RARECAGradientLayer* layer=(RARECAGradientLayer*)l;
  [layer setOverlayPath:path view:self.sparView];
}

-(void) setOverlayImage:(id) uiimage renderType: (RARERenderTypeEnum*) renderType{
  RARECALayer* layer=[self getOverlayLayerCreate:YES];
  [self setLayerImage:uiimage onLayer:layer renderType:renderType];
}

-(void) setLayerImage:(id) uiimage onLayer: (CALayer*) layer renderType: (RARERenderTypeEnum*) renderType {
  layer.contents=uiimage;
  switch([renderType ordinal]) {
    case RARERenderType_UPPER_LEFT:
      layer.contentsGravity=kCAGravityTopLeft;
      break;
    case RARERenderType_UPPER_RIGHT:
      layer.contentsGravity=kCAGravityTopRight;
      break;
    case RARERenderType_LOWER_LEFT:
      layer.contentsGravity=kCAGravityBottomLeft;
      break;
    case RARERenderType_LOWER_RIGHT:
      layer.contentsGravity=kCAGravityBottomRight;
      break;
    case RARERenderType_LOWER_MIDDLE:
      layer.contentsGravity=kCAGravityBottom;
      break;
    case RARERenderType_UPPER_MIDDLE:
      layer.contentsGravity=kCAGravityTop;
      break;
    case RARERenderType_LEFT_MIDDLE:
      layer.contentsGravity=kCAGravityLeft;
      break;
    case RARERenderType_RIGHT_MIDDLE:
      layer.contentsGravity=kCAGravityRight;
      break;
    case RARERenderType_CENTERED:
      layer.contentsGravity=kCAGravityCenter;
      break;
    case RARERenderType_TILED: {
#if TARGET_OS_IPHONE
      float iOSVersion = [[[UIDevice currentDevice] systemVersion] floatValue];
      if( iOSVersion >= 6.0f )
      {
        layer.contents=[((UIImage*)uiimage) resizableImageWithCapInsets:UIEdgeInsetsZero resizingMode:UIImageResizingModeTile];
      }
      else
      {
        layer.contents=[((UIImage*)uiimage) resizableImageWithCapInsets:UIEdgeInsetsZero];
      }
#endif
      break;
    }
    default:
      layer.contentsGravity=kCAGravityResize;
      break;
  
  }
}

//-(void) setOverlayPainter:(id<RAREiPlatformPainter>) painter sparView: (RAREView*) view{
//  RARECALayer* layer=[self getOverlayLayerCreate:YES];
//  if([layer.contents conformsToProtocol:@protocol(RAREiPlatformPainter)]) {
//    layer->painter_=nil;
//  }
//  if(!painter) {
//    return;
//  }
//  if([painter isKindOfClass:[RAREUIBackgroundPainter class]]) {
//    [self setLayerPainter:(RAREUIBackgroundPainter*)painter onLayer:layer withBackground:[view getBackgroundColorAlways]];
//  }
//  else {
////    layer.contents=painter;
//    layer->painter_=painter;
//  }
//}
//
//-(void) setBackgroundPainter:(id<RAREiPlatformPainter>) painter sparView: (RAREView*) view{
//  [self setBackgroundOverlayPainter:painter sparView:view];
//}
//
//-(void) setBackgroundOverlayPainter:(id<RAREiPlatformPainter>) painter sparView: (RAREView*) view{
//  CALayer* l=self.layer;
//#if !TARGET_OS_IPHONE
//  if(!l) {
//    [self setLayer: l=[RARECAGradientLayer new]];
//  }
//#endif
//  if ([l isKindOfClass:[RARECAGradientLayer class]]) {
//    RARECAGradientLayer* gl=(RARECAGradientLayer*)l;
//    if([painter isKindOfClass:[RAREUIBackgroundPainter class]]) {
//      [self setLayerPainter:(RAREUIBackgroundPainter*)painter onLayer:l withBackground:[view getBackgroundColorAlways]];
//      gl->painter_=nil;
//      gl->view_=view;
//    }
//    else {
//      gl->painter_=painter;
//      gl->view_=view;
//    }
//    [gl setNeedsDisplay];
//  }
//}
//
//
-(RAREUIRectangle*) sparBounds {
#if TARGET_OS_IPHONE
  CGRect frameRect=self.bounds;
#else
  NSRect frameRect=self.bounds;
  
#endif
  RAREUIRectangle* rect=[[RAREUIRectangle alloc]initWithFloat:roundf(frameRect.origin.x)
                                                        withFloat:roundf(frameRect.origin.y)
                                                        withFloat:ceilf(frameRect.size.width)
                                                        withFloat:ceilf(frameRect.size.height)];
  return rect;
}

-(RAREUIRectangle*) sparVisibleRect {
#if TARGET_OS_IPHONE
  CGRect frameRect=self.bounds;
#else
  NSRect frameRect=self.visibleRect;
#endif
  RAREUIRectangle* rect=[[RAREUIRectangle alloc]initWithFloat:roundf(frameRect.origin.x)
                                                    withFloat:roundf(frameRect.origin.y)
                                                    withFloat:ceilf(frameRect.size.width)
                                                    withFloat:ceilf(frameRect.size.height)];
  return rect;
}

-(RAREMouseEvent*) createMouseEventWithSource:(id) source event: (NSObject*) theEvent {
  RAREMouseEventEx* me=[[RAREMouseEventEx alloc] initWithId:source withId:theEvent];
  return me;
}

- (void)setSparView:(RAREView *)view {
  objc_setAssociatedObject(self, ViewTagKey, view, OBJC_ASSOCIATION_RETAIN_NONATOMIC);
   CALayer* l=self.layer;
  if ([l isKindOfClass:[RARECAGradientLayer class]]) {
    RARECAGradientLayer* cl=(RARECAGradientLayer*)l;
    cl->view_=view;
  }
  
}

-(void) setMouseListenerEnabled: (BOOL) enabled {
  [self setHandlerEnabled:MASK_MOUSE_HANDLER enabled:enabled];
}
-(void) setMouseMotionListenerEnabled: (BOOL) enabled {
  [self setHandlerEnabled:MASK_MOUSE_MOTION_HANDLER enabled:enabled];
  
}
-(void) setKeyBoardListenerEnabled: (BOOL) enabled {
  [self setHandlerEnabled:MASK_KEY_HANDLER enabled:enabled];
  
}
-(void) setPaintHandlerEnabled:(BOOL)enabled {
#if TARGET_OS_IPHONE
  if(enabled) {
    self.contentMode=UIViewContentModeRedraw;
  }
#endif
  [self setHandlerEnabled:MASK_PAINT_HANDLER enabled:enabled];
}
-(BOOL) hasBeenFocused {
  return (self.tag & MASK_WAS_FOCUSED)!=0;
}

-(BOOL) hasHadInteraction {
  return (self.tag & MASK_HAD_INTERACTION)!=0;
}
-(void) setHasBeenFocused {
  NSInteger tag=self.tag|MASK_WAS_FOCUSED;
#if TARGET_OS_IPHONE
  self.tag=tag;
#else
  if( [self isKindOfClass:[NSControl class]]) {
    [((NSControl*)self) setTag:tag];
  }
  else if( [self isKindOfClass:[RAREAPView class]]) {
    [((RAREAPView*)self) setTag:tag];
  }
#endif
}

-(void) setOverlayColor: (CGColorRef) color  {
  [self getOverlayLayerCreate:YES].backgroundColor=color;
}

-(void) setHasHadInteraction {
  NSInteger tag=self.tag|MASK_HAD_INTERACTION;
#if TARGET_OS_IPHONE
  self.tag=tag;
#else
  if( [self isKindOfClass:[NSControl class]]) {
    [((NSControl*)self) setTag:tag];
  }
  else if( [self isKindOfClass:[RAREAPView class]]) {
    [((RAREAPView*)self) setTag:tag];
  }
#endif
}
-(RAREView*) getViewAtX: (float)x andY: (float)y deepest: (BOOL) deepest {
#if TARGET_OS_IPHONE
  CGPoint p=CGPointMake(x,y);
  for (UIView* v in self.subviews) {
    CGRect frame=v.frame;
    if(CGRectContainsPoint(frame,p) && v.sparView) {
      x-=frame.origin.x;
      y-=frame.origin.y;
      if(deepest && x>-1 && y>-1) {
        RAREView* vv=[v getViewAtX:x  andY:y  deepest: deepest];
        if(vv) {

          return vv;
        }
      }
      return v.sparView;
    }
  }
  return nil;
#else
  NSPoint p=NSMakePoint(x,y);
  for (NSView* v in self.subviews) {
    NSRect frame=v.frame;
    if(NSPointInRect(p, frame) && v.sparView) {
      x-=frame.origin.x;
      y-=frame.origin.y;
      if(deepest && x>-1 && y>-1) {
        RAREView* vv=[v getViewAtX:x  andY:y  deepest: deepest];
        if(vv) {

          return vv;
        }
      }
      return v.sparView;
    }
  }
  return nil;
#endif
}
-(void) setFocusListenerEnabled: (BOOL) enabled {
  [self setHandlerEnabled:MASK_FOCUS_HANDLER enabled:enabled];
}

-(void) lostFocusEx {
  if ([RAREAPApplication getFocusedView]==self) {
    [RAREAPApplication setFocusedView:nil];
  };
  if((self.tag & MASK_FOCUS_HANDLER)==0) {
    return;
  }
  RAREView* nview=nil;
  RAREView* myview=self.sparView;
#if TARGET_OS_IPHONE
  UIView* v=[RAREAPApplication getFocusedView];
  if(v) {
    nview=v.sparView;
  }
#else
  NSView* v=[RAREAPApplication getFocusedView];
  if(v) {
    nview=v.sparView;
  }
#endif
  if(myview && myview->focusListener_) {
    [myview->focusListener_ focusChangedWithId:myview withBoolean:NO withId:nview];
  }
}

-(void) gainedFocusEx {
  [RAREAPApplication setFocusedView:self];
  NSInteger tag=self.tag;
  if((tag & MASK_WAS_FOCUSED)==0) {
    tag|=MASK_WAS_FOCUSED;
#if TARGET_OS_IPHONE
    self.tag=tag;
#else
    if( [self isKindOfClass:[NSControl class]]) {
      [((NSControl*)self) setTag:tag];
    }
    else if( [self isKindOfClass:[RAREAPView class]]) {
      [((RAREAPView*)self) setTag:tag];
    }
#endif
  }
  if((self.tag & MASK_FOCUS_HANDLER)==0) {
    return;
  }
  RAREView* myview=self.sparView;
  if(myview && myview->focusListener_) {
    [myview->focusListener_ focusChangedWithId:myview withBoolean:NO withId:nil];
  }
}

-(void) sparDispose {
  CALayer* layer=self.layer;
  if([layer isKindOfClass:[RARECAGradientLayer class]]) {
    RARECAGradientLayer* l=(RARECAGradientLayer*)layer;
    [l sparDispose];
  }
  
#if TARGET_OS_IPHONE
  self.tag=0;
#else
  if( [self isKindOfClass:[NSControl class]]) {
    [((NSControl*)self) setTag:0];
  }
  else if( [self isKindOfClass:[RAREAPView class]]) {
    [((RAREAPView*)self) setTag:0];
  }
#endif
  objc_removeAssociatedObjects(self);
  [self enableMouseUpListener: NO];
  [self removeFromSuperview];
  
}
#if TARGET_OS_IPHONE
- (UIView *)findFirstResponder
{
  if ([self isFirstResponder])
    return self;
  
  for (UIView * subView in self.subviews)
  {
    UIView * fr = [subView findFirstResponder];
    if (fr != nil)
      return fr;
  }
  
  return nil;
}
#endif
-(void) setHandlerEnabled: (int) handler enabled: (BOOL) enabled {
  NSInteger t=self.tag;
  if(t<0) {
    t=0;
  }
  if(enabled ) {
    t|=handler;
  }
  else if(t & handler) {
    t-=handler;
  }
#if TARGET_OS_IPHONE
  self.tag=t;
#else
  if( [self isKindOfClass:[NSControl class]]) {
    [((NSControl*)self) setTag:t];
  }
  else if( [self isKindOfClass:[RAREAPView class]]) {
    [((RAREAPView*)self) setTag:t];
  }
#endif
}
#if TARGET_OS_IPHONE
-(void) enableMouseUpListener: (BOOL) enable {
}
#else
-(void) mouseUpViaGlobalListener:(NSEvent *)theEvent {
  SEL sel=@selector(mouseUpEx:);
  if([self respondsToSelector:sel]) {
    [self performSelectorOnMainThread:sel withObject:theEvent waitUntilDone:NO];
    
  }
}
-(void) enableMouseUpListener: (BOOL) enable {
  id monitor=objc_getAssociatedObject(self, mouseUpHandler);
  if(!enable) {
    if(monitor) {
      [NSEvent removeMonitor:monitor];
      objc_setAssociatedObject(self, mouseUpHandler,nil,OBJC_ASSOCIATION_RETAIN_NONATOMIC);
    }
    return;
  }
  else if(!monitor) {
    monitor = [NSEvent addLocalMonitorForEventsMatchingMask:
               (NSLeftMouseUpMask | NSRightMouseUpMask | NSOtherMouseUpMask | NSKeyDownMask)
                                                    handler:^(NSEvent *e) {
                                                      NSWindow* window=[e window];
                                                      if (window == [self window]) {
                                                        NSRect rect = [self convertRect:self.bounds toView:nil];
                                                        if(NSPointInRect([e locationInWindow], rect)) {
                                                          [self mouseUpViaGlobalListener:e];
                                                        }
                                                      }
                                                      return e;
                                                    }];
  }
}
#endif

@end
#if !TARGET_OS_IPHONE
@implementation NSButton (SparView)
-(void) setTextColor: (NSColor*) fg {
  NSMutableAttributedString *attrTitle = [[NSMutableAttributedString alloc]
      initWithString: self.title];
  int len = (int)[attrTitle length];
  NSRange range = NSMakeRange(0, len);
  [attrTitle addAttribute:NSForegroundColorAttributeName
                    value:fg
                    range:range];
  [attrTitle fixAttributesInRange:range];
  [self setAttributedTitle: attrTitle];
}

@end

#endif