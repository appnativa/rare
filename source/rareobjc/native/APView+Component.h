//
//  APView+Component.h
//  RareOSX
//
//  Created by Don DeCoteau on 3/19/13.
//  Copyright (c) 2013 SparseWare. All rights reserved.
//
#import "com/appnativa/rare/ui/RenderableDataItem.h"
#import "com/appnativa/rare/ui/RenderableDataItem.h"
#import "com/appnativa/rare/ui/painter/iPainter.h"
#import "com/appnativa/rare/ui/RenderType.h"
#import "RARECAGradientLayer.h"

#define MASK_MOUSE_HANDLER 1
#define MASK_MOUSE_MOTION_HANDLER 2
#define MASK_KEY_HANDLER 4
#define MASK_FOCUS_HANDLER 8
#define MASK_PAINT_HANDLER 16
#define MASK_HAD_INTERACTION 32
#define MASK_WAS_FOCUSED 64
#define MASK_VISIBLILIY_CHANGE_HANDLER 128
#define MASK_RESIZE_CHANGE_HANDLER 256
@class RAREMouseEvent;
@class RAREUIRectangle;
@class RAREView;
@class JavaUtilCalendar;
@class JavaUtilDate;
@class RARECAGradientLayer;
@class RARECALayer;
@class RAREUIBackgroundPainter;
@class RAREUIInsets;
@protocol JavaLangCharSequence;
@protocol RAREiPlatformIcon;

@interface  NSDate (RAREUtils)
+(NSDate*) fromJavaDate: (JavaUtilDate*) date;
+(NSDate*) fromJavaCalendar: (JavaUtilCalendar*) date;
+(JavaUtilDate*) toDate: (NSDate*) date;
-(BOOL) isWeekend;
@end
#if TARGET_OS_IPHONE

@interface UIScreen (OrientatedDimensions)
-(CGRect) orientedBounds;
-(CGRect) orientedFrame;
-(CGSize) orientedSize;
+(CGFloat) osVersionAsFloat;
+(CGSize) currentSize;
+(CGRect) currentFrame;
+(CGRect) currentBounds;
+(CGSize) sizeInOrientation:(UIInterfaceOrientation)orientation ofScreen: (UIScreen*) screen;
+(CGPoint) convertPoint:(CGPoint) pt withScreen: (UIScreen*) screen;
-(CGRect) adjustRectForOrientation: (CGRect) frame;
@end


@interface UILabel (CharSequenceSupport)
-(void) setCharSequence: (id<JavaLangCharSequence>) text;
-(id<JavaLangCharSequence>)  getCharSequence;
-(void) setWrapText: (BOOL) wrap;
-(BOOL) isWrapText;
-(void) setInsetsWithTop: (int) top right: (int)right bottom: (int) bottom left: (int) left;
-(void) setIconGap: (int) gap;
-(void) setIconPosition: (RARERenderableDataItem_IconPosition) position ;
-(void) setTextVerticalAlignment: (RARERenderableDataItem_VerticalAlign) alignment;
-(void) setTextHorizontalAlignment: (RARERenderableDataItem_HorizontalAlign) alignment;
-(void) setIcon: (id<RAREiPlatformIcon>) icon;
-(BOOL) isPressed;
-(void) setPressed: (BOOL) pressed;
@end

@interface UITextField (CharSequenceSupport)
-(void) setCharSequence: (id<JavaLangCharSequence>) text;
- (NSRange) selectedRangeEx;
- (void) setSelectedRangeEx:(NSRange) range;
-(NSString*) getHTML;
@end

@interface UITextView (CharSequenceSupport)
-(void) setCharSequence: (id<JavaLangCharSequence>) text;
-(id<JavaLangCharSequence>)  getCharSequence;
-(NSString*) getHTML;
- (NSRange) selectedRangeEx;
- (void) setSelectedRangeEx:(NSRange) range;
@end

@interface UITableView (RowNumberSupport)
-(BOOL) isRowSelected: (int) row;
@end
  
@interface UITableViewCell (CharSequenceSupport)
-(void) setCharSequence: (id<JavaLangCharSequence>) text;
@end

@interface UIButton (SparButton)
-(void) setInsetsWithTop: (int) top right: (int)right bottom: (int) bottom left: (int) left;
-(void) getInsets:(RAREUIInsets*) insets;
-(void) setIconGap: (int) gap ;
-(void) setIconPosition: (RARERenderableDataItem_IconPosition) position ;
-(void) setTextHorizontalAlignment: (RARERenderableDataItem_HorizontalAlign) alignment;
-(void) setTextVerticalAlignment: (RARERenderableDataItem_VerticalAlign) alignment;
-(void) setIcon: (id<RAREiPlatformIcon>) icon;
-(void) setDisabledIcon: (id<RAREiPlatformIcon>) icon;
-(void) setPressedIcon: (id<RAREiPlatformIcon>) icon;
-(void) setSelectedIcon: (id<RAREiPlatformIcon>) icon;
-(BOOL) isPressed;
-(void) setContinuous: (BOOL) continuous;
-(void) setPeriodicDelay: (float) delay interval: (float) interval;
-(void) setFont: (UIFont*) font;
-(void) setTextColor: (UIColor*) color;
-(void) setCharSequence: (id<JavaLangCharSequence>) text;
-(id<JavaLangCharSequence>)  getCharSequence;
-(void) setText: (NSString*) text;
-(void) setWrapText: (BOOL) wrap;
-(BOOL) isWrapText;
@end

@interface UIView (SparView)
-(CGSize) preferredSize;
-(UIView *)findFirstResponder;
-(CGFloat) getPreferredHeight: (CGFloat) width;
- (CGSize)getPreferredSize:(CGFloat)maxWidth;
-(void) rareRenderInContect: (CGContextRef) ctx;
#else

@interface NSView (SparView)
- (NSSize)getPreferredSize:(CGFloat)maxWidth;
#endif
@property (nonatomic, retain) RAREView* sparView;
- (CGPoint)getLocationOnScreenEx;
-(BOOL) isPaintEnabled;
-(RARECALayer*) getOverlayLayerCreate: (BOOL) create;
-(void) setLayerImage:(id) uiimage onLayer: (CALayer*) layer renderType: (RARERenderTypeEnum*) renderType;
-(void) setOverlayColor: (CGColorRef) color;
-(void) setOverlayBorderPath:(id) path;
//-(void) setBackgroundPainter:(id<RAREiPlatformPainter>) painter sparView: (RAREView*) view;
//-(void) setBackgroundOverlayPainter:(id<RAREiPlatformPainter>) painter sparView: (RAREView*) view;
//-(void) setOverlayPainter:(id<RAREiPlatformPainter>) painter sparView: (RAREView*) view;
//-(void) setLayerPainter:(id<RAREiBackgroundPainter>) painter onLayer: (CALayer*) l withBackground: (RAREUIColor*) bg;
-(void) setMouseListenerEnabled: (BOOL) enabled;
-(void) setMouseMotionListenerEnabled: (BOOL) enabled;
-(void) setKeyBoardListenerEnabled: (BOOL) enabled;
-(void) setFocusListenerEnabled: (BOOL) enabled;
-(void) setPaintHandlerEnabled: (BOOL) enabled;
-(void) setHandlerEnabled: (int) handler enabled: (BOOL) enabled;
-(void) sparDispose;
-(RAREUIRectangle*) sparBounds;
-(RAREUIRectangle*) sparVisibleRect;
-(RAREMouseEvent*) createMouseEventWithSource:(id) source event: (NSObject*) theEvent;
-(BOOL) hasBeenFocused;
-(BOOL) hasHadInteraction;
-(void) setHasBeenFocused;
-(void) setHasHadInteraction;
-(void) lostFocusEx;
-(void) gainedFocusEx;
-(RAREView*) getViewAtX: (float)x andY: (float)y deepest: (BOOL) deepest;
@end
#if !TARGET_OS_IPHONE
@interface NSButton (SparView)
-(void) setTextColor: (NSColor*) fg;
@end
#endif

