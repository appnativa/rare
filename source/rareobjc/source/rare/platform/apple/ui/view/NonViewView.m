//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/platform/apple/ui/view/NonViewView.java
//
//  Created by decoteaud on 3/11/16.
//

#include "com/appnativa/rare/platform/apple/ui/iAppleLayoutManager.h"
#include "com/appnativa/rare/platform/apple/ui/util/AppleGraphics.h"
#include "com/appnativa/rare/platform/apple/ui/view/NonViewView.h"
#include "com/appnativa/rare/platform/apple/ui/view/View.h"
#include "com/appnativa/rare/platform/apple/ui/view/Window.h"
#include "com/appnativa/rare/ui/ColorUtils.h"
#include "com/appnativa/rare/ui/Component.h"
#include "com/appnativa/rare/ui/FontUtils.h"
#include "com/appnativa/rare/ui/RenderableDataItem.h"
#include "com/appnativa/rare/ui/UIColor.h"
#include "com/appnativa/rare/ui/UICursor.h"
#include "com/appnativa/rare/ui/UIDimension.h"
#include "com/appnativa/rare/ui/UIFont.h"
#include "com/appnativa/rare/ui/UIInsets.h"
#include "com/appnativa/rare/ui/UIPoint.h"
#include "com/appnativa/rare/ui/UIRectangle.h"
#include "com/appnativa/rare/ui/Utils.h"
#include "com/appnativa/rare/ui/event/KeyEvent.h"
#include "com/appnativa/rare/ui/event/MouseEvent.h"
#include "com/appnativa/rare/ui/event/iActionListener.h"
#include "com/appnativa/rare/ui/event/iChangeListener.h"
#include "com/appnativa/rare/ui/iGestureListener.h"
#include "com/appnativa/rare/ui/iPaintedButton.h"
#include "com/appnativa/rare/ui/iPlatformBorder.h"
#include "com/appnativa/rare/ui/iPlatformComponent.h"
#include "com/appnativa/rare/ui/iPlatformGraphics.h"
#include "com/appnativa/rare/ui/iPlatformIcon.h"
#include "com/appnativa/rare/ui/listener/iFocusListener.h"
#include "com/appnativa/rare/ui/listener/iKeyListener.h"
#include "com/appnativa/rare/ui/listener/iMouseListener.h"
#include "com/appnativa/rare/ui/listener/iMouseMotionListener.h"
#include "com/appnativa/rare/ui/listener/iViewListener.h"
#include "com/appnativa/rare/ui/painter/iBackgroundPainter.h"
#include "com/appnativa/rare/ui/painter/iPlatformComponentPainter.h"
#include "com/appnativa/rare/ui/painter/iPlatformPainter.h"
#include "java/lang/CharSequence.h"

@implementation RARENonViewView

- (id)init {
  return [super init];
}

- (id)initWithId:(id)o {
  return [super initWithId:o];
}

- (void)addWithInt:(int)position
      withRAREView:(RAREView *)view {
}

- (void)borderChangedWithRAREiPlatformBorder:(id<RAREiPlatformBorder>)newBorder {
}

- (void)bringToFront {
}

- (void)clearFocus {
}

- (void)clearVisualState {
}

- (void)dispatchEventWithRAREKeyEvent:(RAREKeyEvent *)me {
}

- (void)dispatchEventWithRAREMouseEvent:(RAREMouseEvent *)me {
}

- (void)dispose {
}

- (void)focusNextView {
}

- (void)focusPreviousView {
}

- (BOOL)inSameWindowWithRAREView:(RAREView *)otherView {
  return NO;
}

- (void)makeOrphan {
}

- (void)makeTransparent {
}

- (void)paintWithRAREiPlatformGraphics:(id<RAREiPlatformGraphics>)g {
}

- (void)paintBackgroundWithRAREAppleGraphics:(RAREAppleGraphics *)g
                                withRAREView:(RAREView *)v
                         withRAREUIRectangle:(RAREUIRectangle *)rect {
}

- (void)paintOverlayWithRAREAppleGraphics:(RAREAppleGraphics *)g
                             withRAREView:(RAREView *)v
                      withRAREUIRectangle:(RAREUIRectangle *)rect {
}

- (void)paintPainterWithRAREiPlatformPainter:(id<RAREiPlatformPainter>)p
                       withRAREAppleGraphics:(RAREAppleGraphics *)g
                                withRAREView:(RAREView *)v
                                   withFloat:(float)width
                                   withFloat:(float)height {
}

- (void)performClick {
}

- (void)removeChildWithRAREView:(RAREView *)view {
}

- (void)removeChildren {
}

- (void)removeNativeBorder {
}

- (void)repaint {
}

- (BOOL)requestFocus {
  return NO;
}

- (void)resetForRenderer {
}

- (void)resetLayer {
}

- (void)restoreNativeBorder {
}

- (void)revalidate {
}

- (void)set3DTransformWithId:(id)tx {
}

- (void)stateChanged {
}

- (BOOL)usedLayerForBackgroundOverlay {
  return NO;
}

- (BOOL)usedLayerForOverlay {
  return NO;
}

- (void)setActionListenerWithRAREiActionListener:(id<RAREiActionListener>)l {
}

- (void)setBackgroundColorWithRAREUIColor:(RAREUIColor *)bg {
}

- (BOOL)setBackgroundColorExWithRAREUIColor:(RAREUIColor *)bg {
  return NO;
}

- (void)setBackgroundOverlayPainterWithRAREiPlatformPainter:(id<RAREiPlatformPainter>)p {
}

- (void)setBackgroundPainterWithRAREiBackgroundPainter:(id<RAREiBackgroundPainter>)bp {
}

- (void)setBorderWithRAREiPlatformBorder:(id<RAREiPlatformBorder>)b {
  self->border_ = b;
}

- (void)setBoundsWithFloat:(float)x
                 withFloat:(float)y
                 withFloat:(float)w
                 withFloat:(float)h {
}

- (void)setBoundsWithInt:(int)x
                 withInt:(int)y
                 withInt:(int)w
                 withInt:(int)h {
}

- (void)setClipMaskWithId:(id)nativepath {
}

- (void)setComponentWithRAREComponent:(RAREComponent *)component {
  self->component_ = component;
}

- (void)setComponentPainterExWithRAREiPlatformComponentPainter:(id<RAREiPlatformComponentPainter>)cp {
  componentPainter_ = cp;
}

- (void)setCursorWithRAREUICursor:(RAREUICursor *)cursor {
}

- (void)setDisabledIconWithRAREiPlatformIcon:(id<RAREiPlatformIcon>)disabledIcon {
}

- (void)setEnabledWithBoolean:(BOOL)enabled {
  self->enabled_ = enabled;
}

- (void)setFlingGestureListenerWithRAREiGestureListener:(id<RAREiGestureListener>)l {
}

- (void)setFocusListenerWithRAREiFocusListener:(id<RAREiFocusListener>)listener {
}

- (void)setFocusableWithBoolean:(BOOL)focusable {
}

- (void)setFontWithRAREUIFont:(RAREUIFont *)f {
  self->font_ = f;
}

- (void)setForegroundColorWithRAREUIColor:(RAREUIColor *)fg {
  foregroundColor_ = fg;
}

- (void)setIconWithRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon {
}

- (void)setIconGapWithInt:(int)gap {
}

- (void)setIconPositionWithRARERenderableDataItem_IconPositionEnum:(RARERenderableDataItem_IconPositionEnum *)iconPosition {
}

- (void)setChangeListenerWithRAREiChangeListener:(id<RAREiChangeListener>)l {
}

- (void)setKeyboardListenerWithRAREiKeyListener:(id<RAREiKeyListener>)handler {
}

- (void)setLayoutManagerWithRAREiAppleLayoutManager:(id<RAREiAppleLayoutManager>)lm {
}

- (void)setLocationWithFloat:(float)x
                   withFloat:(float)y {
}

- (void)setLongPressGestureListenerWithRAREiGestureListener:(id<RAREiGestureListener>)l {
}

- (void)setMarginWithRAREUIInsets:(RAREUIInsets *)insets {
}

- (void)setMarginWithFloat:(float)top
                 withFloat:(float)right
                 withFloat:(float)bottom
                 withFloat:(float)left {
}

- (void)setMouseListenerWithRAREiMouseListener:(id<RAREiMouseListener>)handler {
}

- (void)setMouseMotionListenerWithRAREiMouseMotionListener:(id<RAREiMouseMotionListener>)handler {
}

- (BOOL)setOverlayColorExWithRAREUIColor:(RAREUIColor *)bg {
  return NO;
}

- (void)setOverlayPainterWithRAREiPlatformPainter:(id<RAREiPlatformPainter>)p {
}

- (void)setPaintHandlerEnabledWithBoolean:(BOOL)enabled {
}

- (void)setPressedIconWithRAREiPlatformIcon:(id<RAREiPlatformIcon>)pressedIcon {
}

- (void)setRotateGestureListenerWithRAREiGestureListener:(id<RAREiGestureListener>)l {
}

- (void)setRotationWithInt:(int)rotation {
}

- (void)setScaleGestureListenerWithRAREiGestureListener:(id<RAREiGestureListener>)l {
}

- (void)setSelectedWithBoolean:(BOOL)selected {
  self->selected_ = selected;
}

- (void)setSelectedIconWithRAREiPlatformIcon:(id<RAREiPlatformIcon>)selectedIcon {
}

- (void)setSizeWithFloat:(float)width
               withFloat:(float)height {
}

- (void)setTextWithJavaLangCharSequence:(id<JavaLangCharSequence>)string {
}

- (void)setTextAlignmentWithRARERenderableDataItem_HorizontalAlignEnum:(RARERenderableDataItem_HorizontalAlignEnum *)hal
                          withRARERenderableDataItem_VerticalAlignEnum:(RARERenderableDataItem_VerticalAlignEnum *)val {
}

- (void)setUseMainLayerForPainterWithBoolean:(BOOL)useMainLayerForPainter {
}

- (void)setUsePainterBackgroundColorWithBoolean:(BOOL)usePainterBackgroundColor {
}

- (void)setUsePainterBorderWithBoolean:(BOOL)usePainterBorder {
}

- (void)setViewListenerWithRAREiViewListener:(id<RAREiViewListener>)l {
}

- (void)setVisibleWithBoolean:(BOOL)visible {
  self->visible_ = visible;
}

- (void)setWantsMouseMovedEventsWithBoolean:(BOOL)b {
}

- (void)setWordWrapWithBoolean:(BOOL)wrap {
}

- (RAREUIColor *)getBackgroundColor {
  return nil;
}

- (RAREUIColor *)getBackgroundColorAlways {
  return [RAREColorUtils getBackground];
}

- (id<RAREiPlatformBorder>)getBorder {
  return border_;
}

- (RAREUIRectangle *)getBounds {
  return [[RAREUIRectangle alloc] init];
}

- (RAREUIRectangle *)getBoundsWithRAREUIRectangle:(RAREUIRectangle *)rect {
  if (rect != nil) {
    [rect setWithFloat:0 withFloat:0 withFloat:0 withFloat:0];
    return rect;
  }
  return [[RAREUIRectangle alloc] init];
}

- (RAREiPaintedButton_ButtonStateEnum *)getButtonState {
  return [RAREUtils getStateWithBoolean:NO withBoolean:NO withBoolean:NO withBoolean:NO];
}

- (id<RAREiPlatformComponent>)getComponent {
  return component_;
}

- (id<RAREiPlatformComponentPainter>)getComponentPainter {
  return componentPainter_;
}

- (id<RAREiPlatformIcon>)getDisabledIcon {
  return nil;
}

- (id<RAREiFocusListener>)getFocusListener {
  return nil;
}

- (RAREUIFont *)getFont {
  return font_;
}

- (RAREUIFont *)getFontAlways {
  return (font_ == nil) ? [RAREFontUtils getDefaultFont] : font_;
}

- (RAREUIColor *)getForegroundColor {
  return foregroundColor_;
}

- (RAREUIColor *)getForegroundColorAlways {
  return (foregroundColor_ == nil) ? [RAREColorUtils getForeground] : foregroundColor_;
}

- (float)getHeight {
  return 0;
}

- (int)getIconGap {
  return 0;
}

- (id<RAREiKeyListener>)getKeyListener {
  return nil;
}

- (RAREUIPoint *)getLocationWithRAREUIPoint:(RAREUIPoint *)loc {
  return nil;
}

- (RAREUIPoint *)getLocationOnScreenWithRAREUIPoint:(RAREUIPoint *)loc {
  return nil;
}

- (RAREUIPoint *)getLocationOnScreen2DWithRAREUIPoint:(RAREUIPoint *)loc {
  if (loc != nil) {
    [loc setWithInt:0 withInt:0];
    return loc;
  }
  return [[RAREUIPoint alloc] init];
}

- (void)getMinimumSizeWithRAREUIDimension:(RAREUIDimension *)size
                                withFloat:(float)maxWidth {
}

- (id<RAREiMouseListener>)getMouseListener {
  return nil;
}

- (id<RAREiMouseMotionListener>)getMouseMotionListener {
  return nil;
}

- (void)getPreferredSizeWithRAREUIDimension:(RAREUIDimension *)size
                                  withFloat:(float)maxWidth {
}

- (id<RAREiPlatformIcon>)getPressedIcon {
  return nil;
}

- (int)getRotation {
  return 0;
}

- (id<RAREiPlatformIcon>)getSelectedIcon {
  return nil;
}

- (RAREUIDimension *)getSize {
  return [[RAREUIDimension alloc] init];
}

- (void)getSizeWithRAREUIDimension:(RAREUIDimension *)size {
}

- (id<JavaLangCharSequence>)getText {
  return nil;
}

- (RAREView *)getViewAtWithFloat:(float)x
                       withFloat:(float)y
                     withBoolean:(BOOL)deepest {
  return nil;
}

- (float)getWidth {
  return 0;
}

- (RAREWindow *)getWindow {
  return nil;
}

- (float)getX {
  return 0;
}

- (float)getY {
  return 0;
}

- (BOOL)hasBeenFocused {
  return NO;
}

- (BOOL)hasHadInteraction {
  return NO;
}

- (BOOL)isDescendantOfWithRAREView:(RAREView *)view {
  return NO;
}

- (BOOL)isDisplayable {
  return YES;
}

- (BOOL)isEnabled {
  return enabled_;
}

- (BOOL)isFocusable {
  return NO;
}

- (BOOL)isFocused {
  return NO;
}

- (BOOL)isFocusedOrChildOfFocused {
  return NO;
}

- (BOOL)isMouseOver {
  return NO;
}

- (BOOL)isMouseTransparent {
  return NO;
}

- (BOOL)isOpaque {
  return YES;
}

- (BOOL)isPressed {
  return pressed_;
}

- (BOOL)isScrollView {
  return NO;
}

- (BOOL)isSelected {
  return selected_;
}

- (BOOL)isShowing {
  return YES;
}

- (BOOL)isUseMainLayerForPainter {
  return NO;
}

- (BOOL)isUsePainterBackgroundColor {
  return NO;
}

- (BOOL)isUsePainterBorder {
  return NO;
}

- (BOOL)isVisible {
  return visible_;
}

- (BOOL)isWordWrap {
  return NO;
}

- (id)addInteractionGestureListener {
  return nil;
}

- (void)addMouseGestureListener {
}

- (void)checkForegroundColor {
}

- (void)disposeEx {
}

- (void)hadInteraction {
}

- (void)handleWantsFirstInteraction {
}

- (void)layingoutLayersWithFloat:(float)width
                       withFloat:(float)height {
}

- (void)removeGestureListenerWithId:(id)r {
}

- (void)visibilityChangedWithBoolean:(BOOL)shown {
}

- (void)setBackgroundOverlayPainterExWithRAREiBackgroundPainter:(id<RAREiBackgroundPainter>)bp {
}

- (void)setBackgroundPainterExWithRAREiBackgroundPainter:(id<RAREiBackgroundPainter>)bp {
}

- (void)setBorderExWithRAREiPlatformBorder:(id<RAREiPlatformBorder>)border {
}

- (void)handleBorderWithRAREiPlatformBorder:(id<RAREiPlatformBorder>)border {
}

- (void)setEnabledExWithBoolean:(BOOL)b {
  self->enabled_ = b;
}

- (void)setFocusListenerEnabledWithBoolean:(BOOL)enabled {
}

- (void)setForegroundColorExWithRAREUIColor:(RAREUIColor *)fg {
  foregroundColor_ = fg;
}

- (void)setKeyBoardListenerEnabledWithBoolean:(BOOL)enabled {
}

- (void)setLayerLayoutEnabledWithBoolean:(BOOL)enabled {
}

- (void)setLayerPathWithId:(id)nativepath {
}

- (void)setMouseListenerEnabledWithBoolean:(BOOL)enabled {
}

- (void)setMouseMotionListenerEnabledWithBoolean:(BOOL)enabled {
}

- (void)setOverlayPainterExWithRAREiPlatformPainter:(id<RAREiPlatformPainter>)bp {
}

- (void)setUseFlipTransformWithBoolean:(BOOL)use {
}

- (void)setViewWithRAREView:(RAREView *)v {
}

- (void)setVisibleExWithBoolean:(BOOL)visible {
}

- (id)getLayer {
  return nil;
}

- (id)getOverlayLayer {
  return nil;
}

- (BOOL)isPaintEnabled {
  return NO;
}

- (BOOL)isSingleColorPainter {
  return NO;
}

- (void)copyAllFieldsTo:(RARENonViewView *)other {
  [super copyAllFieldsTo:other];
  other->selected_ = selected_;
  other->visible_ = visible_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "initWithId:", NULL, NULL, 0x4, NULL },
    { "inSameWindowWithRAREView:", NULL, "Z", 0x1, NULL },
    { "requestFocus", NULL, "Z", 0x1, NULL },
    { "usedLayerForBackgroundOverlay", NULL, "Z", 0x1, NULL },
    { "usedLayerForOverlay", NULL, "Z", 0x1, NULL },
    { "setBackgroundColorExWithRAREUIColor:", NULL, "Z", 0x1, NULL },
    { "setOverlayColorExWithRAREUIColor:", NULL, "Z", 0x1, NULL },
    { "getBackgroundColor", NULL, "LRAREUIColor", 0x1, NULL },
    { "getBackgroundColorAlways", NULL, "LRAREUIColor", 0x1, NULL },
    { "getBorder", NULL, "LRAREiPlatformBorder", 0x1, NULL },
    { "getBounds", NULL, "LRAREUIRectangle", 0x1, NULL },
    { "getBoundsWithRAREUIRectangle:", NULL, "LRAREUIRectangle", 0x1, NULL },
    { "getButtonState", NULL, "LRAREiPaintedButton_ButtonStateEnum", 0x1, NULL },
    { "getComponent", NULL, "LRAREiPlatformComponent", 0x1, NULL },
    { "getComponentPainter", NULL, "LRAREiPlatformComponentPainter", 0x1, NULL },
    { "getDisabledIcon", NULL, "LRAREiPlatformIcon", 0x1, NULL },
    { "getFocusListener", NULL, "LRAREiFocusListener", 0x1, NULL },
    { "getFont", NULL, "LRAREUIFont", 0x1, NULL },
    { "getFontAlways", NULL, "LRAREUIFont", 0x1, NULL },
    { "getForegroundColor", NULL, "LRAREUIColor", 0x1, NULL },
    { "getForegroundColorAlways", NULL, "LRAREUIColor", 0x1, NULL },
    { "getKeyListener", NULL, "LRAREiKeyListener", 0x1, NULL },
    { "getLocationWithRAREUIPoint:", NULL, "LRAREUIPoint", 0x1, NULL },
    { "getLocationOnScreenWithRAREUIPoint:", NULL, "LRAREUIPoint", 0x1, NULL },
    { "getLocationOnScreen2DWithRAREUIPoint:", NULL, "LRAREUIPoint", 0x1, NULL },
    { "getMouseListener", NULL, "LRAREiMouseListener", 0x1, NULL },
    { "getMouseMotionListener", NULL, "LRAREiMouseMotionListener", 0x1, NULL },
    { "getPressedIcon", NULL, "LRAREiPlatformIcon", 0x1, NULL },
    { "getSelectedIcon", NULL, "LRAREiPlatformIcon", 0x1, NULL },
    { "getSize", NULL, "LRAREUIDimension", 0x1, NULL },
    { "getText", NULL, "LJavaLangCharSequence", 0x1, NULL },
    { "getViewAtWithFloat:withFloat:withBoolean:", NULL, "LRAREView", 0x1, NULL },
    { "getWindow", NULL, "LRAREWindow", 0x1, NULL },
    { "hasBeenFocused", NULL, "Z", 0x1, NULL },
    { "hasHadInteraction", NULL, "Z", 0x1, NULL },
    { "isDescendantOfWithRAREView:", NULL, "Z", 0x1, NULL },
    { "isDisplayable", NULL, "Z", 0x1, NULL },
    { "isEnabled", NULL, "Z", 0x1, NULL },
    { "isFocusable", NULL, "Z", 0x1, NULL },
    { "isFocused", NULL, "Z", 0x1, NULL },
    { "isFocusedOrChildOfFocused", NULL, "Z", 0x1, NULL },
    { "isMouseOver", NULL, "Z", 0x1, NULL },
    { "isMouseTransparent", NULL, "Z", 0x1, NULL },
    { "isOpaque", NULL, "Z", 0x1, NULL },
    { "isPressed", NULL, "Z", 0x1, NULL },
    { "isScrollView", NULL, "Z", 0x1, NULL },
    { "isSelected", NULL, "Z", 0x1, NULL },
    { "isShowing", NULL, "Z", 0x1, NULL },
    { "isUseMainLayerForPainter", NULL, "Z", 0x1, NULL },
    { "isUsePainterBackgroundColor", NULL, "Z", 0x1, NULL },
    { "isUsePainterBorder", NULL, "Z", 0x1, NULL },
    { "isVisible", NULL, "Z", 0x1, NULL },
    { "isWordWrap", NULL, "Z", 0x1, NULL },
    { "addInteractionGestureListener", NULL, "LNSObject", 0x4, NULL },
    { "addMouseGestureListener", NULL, "V", 0x4, NULL },
    { "checkForegroundColor", NULL, "V", 0x4, NULL },
    { "disposeEx", NULL, "V", 0x4, NULL },
    { "hadInteraction", NULL, "V", 0x4, NULL },
    { "handleWantsFirstInteraction", NULL, "V", 0x4, NULL },
    { "layingoutLayersWithFloat:withFloat:", NULL, "V", 0x4, NULL },
    { "removeGestureListenerWithId:", NULL, "V", 0x4, NULL },
    { "visibilityChangedWithBoolean:", NULL, "V", 0x4, NULL },
    { "setBackgroundOverlayPainterExWithRAREiBackgroundPainter:", NULL, "V", 0x4, NULL },
    { "setBackgroundPainterExWithRAREiBackgroundPainter:", NULL, "V", 0x4, NULL },
    { "handleBorderWithRAREiPlatformBorder:", NULL, "V", 0x4, NULL },
    { "setEnabledExWithBoolean:", NULL, "V", 0x4, NULL },
    { "setFocusListenerEnabledWithBoolean:", NULL, "V", 0x4, NULL },
    { "setForegroundColorExWithRAREUIColor:", NULL, "V", 0x4, NULL },
    { "setKeyBoardListenerEnabledWithBoolean:", NULL, "V", 0x4, NULL },
    { "setLayerPathWithId:", NULL, "V", 0x4, NULL },
    { "setMouseListenerEnabledWithBoolean:", NULL, "V", 0x4, NULL },
    { "setMouseMotionListenerEnabledWithBoolean:", NULL, "V", 0x4, NULL },
    { "setOverlayPainterExWithRAREiPlatformPainter:", NULL, "V", 0x4, NULL },
    { "setUseFlipTransformWithBoolean:", NULL, "V", 0x4, NULL },
    { "setViewWithRAREView:", NULL, "V", 0x4, NULL },
    { "setVisibleExWithBoolean:", NULL, "V", 0x4, NULL },
    { "getLayer", NULL, "LNSObject", 0x4, NULL },
    { "getOverlayLayer", NULL, "LNSObject", 0x4, NULL },
    { "isPaintEnabled", NULL, "Z", 0x4, NULL },
    { "isSingleColorPainter", NULL, "Z", 0x4, NULL },
  };
  static J2ObjcClassInfo _RARENonViewView = { "NonViewView", "com.appnativa.rare.platform.apple.ui.view", NULL, 0x1, 80, methods, 0, NULL, 0, NULL};
  return &_RARENonViewView;
}

@end
