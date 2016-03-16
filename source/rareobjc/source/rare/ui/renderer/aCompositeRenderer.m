//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/renderer/aCompositeRenderer.java
//
//  Created by decoteaud on 3/11/16.
//

#include "IOSClass.h"
#include "com/appnativa/jgoodies/forms/layout/CellConstraints.h"
#include "com/appnativa/rare/Platform.h"
#include "com/appnativa/rare/iConstants.h"
#include "com/appnativa/rare/platform/PlatformHelper.h"
#include "com/appnativa/rare/platform/apple/ui/view/View.h"
#include "com/appnativa/rare/ui/BorderPanel.h"
#include "com/appnativa/rare/ui/Column.h"
#include "com/appnativa/rare/ui/Container.h"
#include "com/appnativa/rare/ui/RenderableDataItem.h"
#include "com/appnativa/rare/ui/ScreenUtils.h"
#include "com/appnativa/rare/ui/UIColor.h"
#include "com/appnativa/rare/ui/UIDimension.h"
#include "com/appnativa/rare/ui/UIFont.h"
#include "com/appnativa/rare/ui/UIInsets.h"
#include "com/appnativa/rare/ui/XPContainer.h"
#include "com/appnativa/rare/ui/aBorderPanel.h"
#include "com/appnativa/rare/ui/aComponent.h"
#include "com/appnativa/rare/ui/iActionComponent.h"
#include "com/appnativa/rare/ui/iPlatformBorder.h"
#include "com/appnativa/rare/ui/iPlatformComponent.h"
#include "com/appnativa/rare/ui/iPlatformIcon.h"
#include "com/appnativa/rare/ui/iPlatformRenderingComponent.h"
#include "com/appnativa/rare/ui/iRenderingComponent.h"
#include "com/appnativa/rare/ui/painter/iPlatformComponentPainter.h"
#include "com/appnativa/rare/ui/renderer/Renderers.h"
#include "com/appnativa/rare/ui/renderer/UILabelRenderer.h"
#include "com/appnativa/rare/ui/renderer/aCompositeRenderer.h"
#include "com/appnativa/rare/viewer/WindowViewer.h"
#include "com/appnativa/util/SNumber.h"
#include "java/lang/CharSequence.h"
#include "java/lang/IllegalArgumentException.h"
#include "java/util/Locale.h"
#include "java/util/Map.h"

@implementation RAREaCompositeRenderer

static RAREUIInsets * RAREaCompositeRenderer_EMPTY_INSETS_;

+ (RAREUIInsets *)EMPTY_INSETS {
  return RAREaCompositeRenderer_EMPTY_INSETS_;
}

+ (void)setEMPTY_INSETS:(RAREUIInsets *)EMPTY_INSETS {
  RAREaCompositeRenderer_EMPTY_INSETS_ = EMPTY_INSETS;
}

- (id)init {
  return [self initRAREaCompositeRendererWithRAREiPlatformRenderingComponent:[[RAREUILabelRenderer alloc] init]];
}

- (id)initRAREaCompositeRendererWithRAREiPlatformRenderingComponent:(id<RAREiPlatformRenderingComponent>)rc {
  if (self = [super initWithRAREiWidget:[RAREPlatform getWindowViewer]]) {
    backgroundSurface_ = [RAREaCompositeRenderer_BackgroundSurfaceEnum PANEL];
    iconGapInsets_ = [[RAREUIInsets alloc] init];
    iconLabel_ = [[RAREUILabelRenderer alloc] init];
    [iconLabel_ setMarginWithRAREUIInsets:RAREaCompositeRenderer_EMPTY_INSETS_];
    [iconLabel_ setAlignmentWithRARERenderableDataItem_HorizontalAlignEnum:[RARERenderableDataItem_HorizontalAlignEnum CENTER] withRARERenderableDataItem_VerticalAlignEnum:[RARERenderableDataItem_VerticalAlignEnum CENTER]];
    [self setCenterViewWithRAREiPlatformComponent:[((id<RAREiPlatformRenderingComponent>) nil_chk(rc)) getComponent]];
    [self setLeftViewWithRAREiPlatformComponent:[iconLabel_ getComponent]];
    renderingComponent_ = rc;
    [self setIconGapWithFloat:[RAREScreenUtils PLATFORM_PIXELS_4]];
  }
  return self;
}

- (id)initWithRAREiPlatformRenderingComponent:(id<RAREiPlatformRenderingComponent>)rc {
  return [self initRAREaCompositeRendererWithRAREiPlatformRenderingComponent:rc];
}

- (void)clearRenderer {
  [self setComponentPainterWithRAREiPlatformComponentPainter:nil];
  [self setBackgroundWithRAREUIColor:nil];
  [self setBorderWithRAREiPlatformBorder:nil];
  [self setIconWithRAREiPlatformIcon:nil];
  [((RAREUILabelRenderer *) nil_chk(iconLabel_)) clearRenderer];
  if ([(id) renderingComponent_ conformsToProtocol: @protocol(RAREiActionComponent)]) {
    [((id<RAREiPlatformRenderingComponent>) nil_chk(renderingComponent_)) clearRenderer];
  }
}

- (void)dispose {
  [super dispose];
  renderingComponent_ = nil;
  iconLabel_ = nil;
  iconLabelForeground_ = nil;
}

- (void)makeIconPrimary {
  backgroundSurface_ = [RAREaCompositeRenderer_BackgroundSurfaceEnum ICON];
}

- (void)makePanelPrimary {
  backgroundSurface_ = [RAREaCompositeRenderer_BackgroundSurfaceEnum PANEL];
}

- (void)makeRendererPrimary {
  backgroundSurface_ = [RAREaCompositeRenderer_BackgroundSurfaceEnum RENDERER];
}

- (void)setColumnWidthWithInt:(int)width {
  columnWidth_ = width;
}

- (void)setAlignmentWithRARERenderableDataItem_HorizontalAlignEnum:(RARERenderableDataItem_HorizontalAlignEnum *)ha
                      withRARERenderableDataItem_VerticalAlignEnum:(RARERenderableDataItem_VerticalAlignEnum *)va {
  [((id<RAREiPlatformRenderingComponent>) nil_chk(renderingComponent_)) setAlignmentWithRARERenderableDataItem_HorizontalAlignEnum:ha withRARERenderableDataItem_VerticalAlignEnum:va];
  [((RAREUILabelRenderer *) nil_chk(iconLabel_)) setAlignmentWithRARERenderableDataItem_HorizontalAlignEnum:ha withRARERenderableDataItem_VerticalAlignEnum:va];
}

- (void)setAlternateIconGapWithInt:(int)gap {
  id<RAREiPlatformComponent> c = [((id<RAREiPlatformRenderingComponent>) nil_chk(renderingComponent_)) getComponent];
  if ([(id) c conformsToProtocol: @protocol(RAREiActionComponent)]) {
    [((id<RAREiActionComponent>) check_protocol_cast(c, @protocol(RAREiActionComponent))) setIconGapWithInt:gap];
  }
}

- (void)setAlternateIconPositionWithRARERenderableDataItem_IconPositionEnum:(RARERenderableDataItem_IconPositionEnum *)position {
  if ((renderingComponent_ != nil) && (position != nil)) {
    id<RAREiPlatformComponent> c = [renderingComponent_ getComponent];
    if ([(id) c conformsToProtocol: @protocol(RAREiActionComponent)]) {
      [((id<RAREiActionComponent>) check_protocol_cast(c, @protocol(RAREiActionComponent))) setIconPositionWithRARERenderableDataItem_IconPositionEnum:position];
    }
  }
}

- (void)setBackgroundWithRAREUIColor:(RAREUIColor *)bg {
  if (backgroundSurface_ != nil) {
    switch ([backgroundSurface_ ordinal]) {
      case RAREaCompositeRenderer_BackgroundSurface_ICON:
      [((RAREUILabelRenderer *) nil_chk(iconLabel_)) setBackgroundWithRAREUIColor:bg];
      break;
      case RAREaCompositeRenderer_BackgroundSurface_RENDERER:
      if (renderingComponent_ != nil) {
        [renderingComponent_ setBackgroundWithRAREUIColor:bg];
        break;
      }
      default:
      [super setBackgroundWithRAREUIColor:bg];
      break;
    }
  }
  else {
    [super setBackgroundWithRAREUIColor:bg];
  }
}

- (void)setBorderWithRAREiPlatformBorder:(id<RAREiPlatformBorder>)b {
  if (backgroundSurface_ != nil) {
    switch ([backgroundSurface_ ordinal]) {
      case RAREaCompositeRenderer_BackgroundSurface_ICON:
      [((RAREUILabelRenderer *) nil_chk(iconLabel_)) setBorderWithRAREiPlatformBorder:b];
      break;
      case RAREaCompositeRenderer_BackgroundSurface_RENDERER:
      if (renderingComponent_ != nil) {
        [renderingComponent_ setBorderWithRAREiPlatformBorder:b];
        break;
      }
      default:
      [self setBorderExWithRAREiPlatformBorder:b];
      break;
    }
  }
  else {
    [self setBorderExWithRAREiPlatformBorder:b];
  }
}

- (void)setComponentPainterWithRAREiPlatformComponentPainter:(id<RAREiPlatformComponentPainter>)cp {
  switch ([backgroundSurface_ ordinal]) {
    case RAREaCompositeRenderer_BackgroundSurface_ICON:
    [((RAREUILabelRenderer *) nil_chk(iconLabel_)) setComponentPainterWithRAREiPlatformComponentPainter:cp];
    break;
    case RAREaCompositeRenderer_BackgroundSurface_RENDERER:
    if (renderingComponent_ != nil) {
      [renderingComponent_ setComponentPainterWithRAREiPlatformComponentPainter:cp];
      break;
    }
    default:
    [super setComponentPainterWithRAREiPlatformComponentPainter:cp];
    break;
  }
}

- (void)setFontWithRAREUIFont:(RAREUIFont *)font {
  if (iconLabelFont_ == nil) {
    [((RAREUILabelRenderer *) nil_chk(iconLabel_)) setFontWithRAREUIFont:font];
  }
  if (renderingComponent_ != nil) {
    [renderingComponent_ setFontWithRAREUIFont:font];
  }
}

- (void)setForegroundWithRAREUIColor:(RAREUIColor *)fg {
  if (renderingComponent_ != nil) {
    [renderingComponent_ setForegroundWithRAREUIColor:fg];
  }
  if (iconLabelForeground_ == nil) {
    [((RAREUILabelRenderer *) nil_chk(iconLabel_)) setForegroundWithRAREUIColor:fg];
  }
}

- (void)setIconWithRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon {
  [((RAREUILabelRenderer *) nil_chk(iconLabel_)) setIconWithRAREiPlatformIcon:icon];
}

- (void)setIconLabelFontWithRAREUIFont:(RAREUIFont *)font {
  iconLabelFont_ = font;
  [((RAREUILabelRenderer *) nil_chk(iconLabel_)) setFontWithRAREUIFont:font];
}

- (void)setIconLabelForegroundWithRAREUIColor:(RAREUIColor *)fg {
  iconLabelForeground_ = fg;
  [((RAREUILabelRenderer *) nil_chk(iconLabel_)) setForegroundWithRAREUIColor:fg];
}

- (void)setIconPositionWithRARERenderableDataItem_IconPositionEnum:(RARERenderableDataItem_IconPositionEnum *)position {
  if (position == nil) {
    position = [RARERenderableDataItem_IconPositionEnum AUTO];
  }
  if (position != iconPosition_) {
    iconPosition_ = position;
    id<RAREiPlatformComponent> c = [((RAREUILabelRenderer *) nil_chk(iconLabel_)) getComponent];
    [iconLabel_ setIconPositionWithRARERenderableDataItem_IconPositionEnum:position];
    iconPosition_ = position;
    [self removeWithRAREiPlatformComponent:c];
    RARECellConstraints *cc;
    [((RAREUIInsets *) nil_chk(iconGapInsets_)) setWithInt:0];
    switch ([position ordinal]) {
      case RARERenderableDataItem_IconPosition_BOTTOM_CENTER:
      [self setBottomViewWithRAREiPlatformComponent:c];
      iconGapInsets_->bottom_ = iconGap_;
      cc = [self getCellConstraintsWithRAREiPlatformComponent:c];
      if (cc != nil) {
        cc->hAlign_ = [RARECellConstraints CENTER];
        cc->vAlign_ = [RARECellConstraints TOP];
      }
      iconGapInsets_->bottom_ = iconGap_;
      [iconLabel_ setIconPositionWithRARERenderableDataItem_IconPositionEnum:position];
      break;
      case RARERenderableDataItem_IconPosition_BOTTOM_LEFT:
      [self setBottomViewWithRAREiPlatformComponent:c];
      iconGapInsets_->bottom_ = iconGap_;
      cc = [self getCellConstraintsWithRAREiPlatformComponent:c];
      if (cc != nil) {
        cc->hAlign_ = [RARECellConstraints LEFT];
        cc->vAlign_ = [RARECellConstraints TOP];
      }
      break;
      case RARERenderableDataItem_IconPosition_BOTTOM_RIGHT:
      [self setBottomViewWithRAREiPlatformComponent:c];
      iconGapInsets_->bottom_ = iconGap_;
      cc = [self getCellConstraintsWithRAREiPlatformComponent:c];
      if (cc != nil) {
        cc->hAlign_ = [RARECellConstraints LEFT];
        cc->vAlign_ = [RARECellConstraints RIGHT];
      }
      break;
      case RARERenderableDataItem_IconPosition_TOP_CENTER:
      [self setTopViewWithRAREiPlatformComponent:c];
      iconGapInsets_->top_ = iconGap_;
      cc = [self getCellConstraintsWithRAREiPlatformComponent:c];
      if (cc != nil) {
        cc->hAlign_ = [RARECellConstraints CENTER];
        cc->vAlign_ = [RARECellConstraints BOTTOM];
      }
      break;
      case RARERenderableDataItem_IconPosition_TOP_LEFT:
      [self setTopViewWithRAREiPlatformComponent:c];
      iconGapInsets_->top_ = iconGap_;
      cc = [self getCellConstraintsWithRAREiPlatformComponent:c];
      if (cc != nil) {
        cc->hAlign_ = [RARECellConstraints LEFT];
        cc->vAlign_ = [RARECellConstraints BOTTOM];
      }
      break;
      case RARERenderableDataItem_IconPosition_TOP_RIGHT:
      [self setTopViewWithRAREiPlatformComponent:c];
      iconGapInsets_->top_ = iconGap_;
      cc = [self getCellConstraintsWithRAREiPlatformComponent:c];
      if (cc != nil) {
        cc->hAlign_ = [RARECellConstraints RIGHT];
        cc->vAlign_ = [RARECellConstraints BOTTOM];
      }
      break;
      case RARERenderableDataItem_IconPosition_RIGHT:
      [self setRightViewWithRAREiPlatformComponent:c];
      iconGapInsets_->right_ = iconGap_;
      cc = [self getCellConstraintsWithRAREiPlatformComponent:c];
      if (cc != nil) {
        cc->hAlign_ = [RARECellConstraints LEFT];
        cc->vAlign_ = [RARECellConstraints CENTER];
      }
      break;
      case RARERenderableDataItem_IconPosition_RIGHT_JUSTIFIED:
      [self setRightViewWithRAREiPlatformComponent:c];
      iconGapInsets_->right_ = iconGap_;
      cc = [self getCellConstraintsWithRAREiPlatformComponent:c];
      if (cc != nil) {
        cc->hAlign_ = [RARECellConstraints RIGHT];
        cc->vAlign_ = [RARECellConstraints CENTER];
      }
      break;
      default:
      [self setLeftViewWithRAREiPlatformComponent:c];
      iconGapInsets_->left_ = iconGap_;
      cc = [self getCellConstraintsWithRAREiPlatformComponent:c];
      if (cc != nil) {
        cc->hAlign_ = [RARECellConstraints CENTER];
        cc->vAlign_ = [RARECellConstraints CENTER];
      }
      break;
    }
    [((id<RAREiPlatformRenderingComponent>) nil_chk(renderingComponent_)) setMarginWithRAREUIInsets:iconGapInsets_];
  }
}

- (void)setMarginWithInt:(int)top
                 withInt:(int)right
                 withInt:(int)bottom
                 withInt:(int)left {
  [self setMarginWithRAREUIInsets:[[RAREUIInsets alloc] initWithInt:top withInt:right withInt:bottom withInt:left]];
}

- (void)setOptionsWithJavaUtilMap:(id<JavaUtilMap>)options {
  if (options == nil) {
    return;
  }
  if ([RAREUTSNumber booleanValueWithId:[((id<JavaUtilMap>) nil_chk(options)) getWithId:@"useAlternateIcon"]]) {
    [self setUseAlternateIconWithBoolean:YES];
  }
  if ([RAREUTSNumber booleanValueWithId:[options getWithId:@"makeIconPrimary"]]) {
    [self makeIconPrimary];
  }
  else if ([RAREUTSNumber booleanValueWithId:[options getWithId:@"makePanelPrimary"]]) {
    [self makePanelPrimary];
  }
  if ([RAREUTSNumber booleanValueWithId:[options getWithId:@"makeRendererPrimary"]]) {
    [self makeRendererPrimary];
  }
  NSString *s = (NSString *) check_class_cast([options getWithId:@"alternateIconPosition"], [NSString class]);
  if (s != nil) {
    [self setAlternateIconPositionWithRARERenderableDataItem_IconPositionEnum:[RARERenderableDataItem_IconPositionEnum valueOfWithNSString:[s uppercaseStringWithJRELocale:[JavaUtilLocale US]]]];
  }
  s = (NSString *) check_class_cast([options getWithId:@"alternateIconGap"], [NSString class]);
  if ((s != nil) && ([s sequenceLength] > 0)) {
    [self setAlternateIconGapWithInt:[RAREUTSNumber intValueWithNSString:s]];
  }
  s = (NSString *) check_class_cast([options getWithId:@"iconGap"], [NSString class]);
  if (s != nil) {
    int gap = [RAREUTSNumber intValueWithNSString:s];
    if (gap > -1) {
      [self setIconGapWithFloat:gap];
    }
  }
}

- (void)setOrientationWithRARERenderableDataItem_OrientationEnum:(RARERenderableDataItem_OrientationEnum *)o {
}

- (void)setScaleIconWithBoolean:(BOOL)scale_
                      withFloat:(float)scaleFactor {
}

- (void)setTextAlignmentWithRARERenderableDataItem_HorizontalAlignEnum:(RARERenderableDataItem_HorizontalAlignEnum *)ha
                          withRARERenderableDataItem_VerticalAlignEnum:(RARERenderableDataItem_VerticalAlignEnum *)va {
  if (renderingComponent_ != nil) {
    [RAREaPlatformHelper setTextAlignmentWithRAREiPlatformComponent:[renderingComponent_ getComponent] withRARERenderableDataItem_HorizontalAlignEnum:ha withRARERenderableDataItem_VerticalAlignEnum:va];
  }
}

- (void)setUseAlternateIconWithBoolean:(BOOL)useAlternateIcon {
  self->useAlternateIcon_ = useAlternateIcon;
}

- (void)setWordWrapWithBoolean:(BOOL)wrap {
  [((id<RAREiPlatformRenderingComponent>) nil_chk(renderingComponent_)) setWordWrapWithBoolean:wrap];
  [((RAREUILabelRenderer *) nil_chk(iconLabel_)) setWordWrapWithBoolean:wrap];
}

- (id<RAREiPlatformComponent>)getComponent {
  return [self getComponentWithJavaLangCharSequence:@"" withRARERenderableDataItem:nil];
}

- (id<RAREiPlatformComponent>)getComponentWithJavaLangCharSequence:(id<JavaLangCharSequence>)value
                                        withRARERenderableDataItem:(RARERenderableDataItem *)item {
  if (renderingComponent_ != nil) {
    RARERenderableDataItem *ditem = item;
    if ((item != nil) && ([[item getValue] isKindOfClass:[RARERenderableDataItem class]])) {
      ditem = (RARERenderableDataItem *) check_class_cast([item getValue], [RARERenderableDataItem class]);
      value = (ditem == nil) ? nil : [ditem toCharSequence];
    }
    id<RAREiPlatformComponent> pc = (item == nil) ? nil : [item getRenderingComponent];
    if (pc != nil) {
      return pc;
    }
    (void) [renderingComponent_ getComponentWithJavaLangCharSequence:value withRARERenderableDataItem:ditem];
    if (useAlternateIcon_ && (ditem == item)) {
      if (item != nil) {
        [renderingComponent_ setIconWithRAREiPlatformIcon:[item getAlternateIcon]];
      }
      else {
        [renderingComponent_ setIconWithRAREiPlatformIcon:nil];
      }
    }
  }
  if (item != nil) {
    NSString *s = (NSString *) check_class_cast([item getCustomPropertyWithId:@"CompositeRenderer.iconText"], [NSString class]);
    if (s == nil) {
      if (([((RAREUILabelRenderer *) nil_chk(iconLabel_)) getText] != nil) && ([((id<JavaLangCharSequence>) nil_chk([iconLabel_ getText])) sequenceLength] > 0)) {
        [iconLabel_ setTextWithJavaLangCharSequence:@""];
      }
    }
    else {
      [((RAREUILabelRenderer *) nil_chk(iconLabel_)) setTextWithJavaLangCharSequence:s];
    }
  }
  return self;
}

- (id<RAREiPlatformComponent>)getComponentWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)list
                                                              withId:(id)value
                                          withRARERenderableDataItem:(RARERenderableDataItem *)item
                                                             withInt:(int)row
                                                         withBoolean:(BOOL)isSelected
                                                         withBoolean:(BOOL)hasFocus
                                                      withRAREColumn:(RAREColumn *)col
                                          withRARERenderableDataItem:(RARERenderableDataItem *)rowItem
                                                         withBoolean:(BOOL)handleAll {
  id<JavaLangCharSequence> s;
  if ([value conformsToProtocol: @protocol(JavaLangCharSequence)]) {
    s = (id<JavaLangCharSequence>) check_protocol_cast(value, @protocol(JavaLangCharSequence));
  }
  else {
    s = (value == nil) ? @"" : [value description];
  }
  return [self getComponentWithJavaLangCharSequence:s withRARERenderableDataItem:item];
}

- (RAREUILabelRenderer *)getIconLabel {
  return iconLabel_;
}

- (id<RAREiRenderingComponent>)getRenderingComponent {
  return renderingComponent_;
}

- (BOOL)isUseAlternateIcon {
  return useAlternateIcon_;
}

- (void)setBorderExWithRAREiPlatformBorder:(id<RAREiPlatformBorder>)b {
  [super setBorderWithRAREiPlatformBorder:b];
}

- (void)getMinimumSizeExWithRAREUIDimension:(RAREUIDimension *)size
                                  withFloat:(float)maxWidth {
  [super getMinimumSizeExWithRAREUIDimension:size withFloat:maxWidth];
  NSNumber *i = (NSNumber *) check_class_cast([self getClientPropertyWithNSString:[RAREiConstants RARE_HEIGHT_MIN_VALUE]], [NSNumber class]);
  if ((i != nil) && ([i intValue] > ((RAREUIDimension *) nil_chk(size))->height_)) {
    size->height_ = [i intValue];
  }
}

- (void)getPreferredSizeExWithRAREUIDimension:(RAREUIDimension *)size
                                    withFloat:(float)maxWidth {
  int n = columnWidth_;
  if (n < 0) {
    maxWidth += n;
    if (maxWidth < 0) {
      maxWidth = 0;
    }
  }
  else if (n > 0 && maxWidth > n) {
    maxWidth = n;
  }
  [super getPreferredSizeExWithRAREUIDimension:size withFloat:maxWidth];
  ((RAREUIDimension *) nil_chk(size))->width_ += ((RAREUIInsets *) nil_chk(iconGapInsets_))->left_ + iconGapInsets_->right_;
  size->height_ += iconGapInsets_->top_ + iconGapInsets_->bottom_;
  NSNumber *i = (NSNumber *) check_class_cast([self getClientPropertyWithNSString:[RAREiConstants RARE_HEIGHT_MIN_VALUE]], [NSNumber class]);
  if ((i != nil) && ([i intValue] > size->height_)) {
    size->height_ = [i intValue];
  }
}

- (id<RAREiPlatformRenderingComponent>)setupNewCopyWithRAREaCompositeRenderer:(RAREaCompositeRenderer *)r {
  [((RAREaCompositeRenderer *) nil_chk(r)) setIconPositionWithRARERenderableDataItem_IconPositionEnum:iconPosition_];
  r->backgroundSurface_ = backgroundSurface_;
  r->columnWidth_ = columnWidth_;
  return [RARERenderers setupNewCopyWithRAREiPlatformRenderingComponent:self withRAREiPlatformRenderingComponent:r];
}

- (void)setIconGapWithFloat:(float)gap {
  iconGap_ = gap;
}

- (id)createNewNativeView {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
  return 0;
}

- (id<RAREiPlatformRenderingComponent>)newCopy {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
  return 0;
}

- (void)prepareForReuseWithInt:(int)param0
                       withInt:(int)param1 {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
}

- (void)setNativeViewWithId:(id)param0 {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
}

- (void)setRenderingViewWithRAREView:(RAREView *)param0 {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
}

+ (void)initialize {
  if (self == [RAREaCompositeRenderer class]) {
    RAREaCompositeRenderer_EMPTY_INSETS_ = [[RAREUIInsets alloc] init];
  }
}

- (void)copyAllFieldsTo:(RAREaCompositeRenderer *)other {
  [super copyAllFieldsTo:other];
  other->backgroundSurface_ = backgroundSurface_;
  other->columnWidth_ = columnWidth_;
  other->iconGap_ = iconGap_;
  other->iconGapInsets_ = iconGapInsets_;
  other->iconLabel_ = iconLabel_;
  other->iconLabelFont_ = iconLabelFont_;
  other->iconLabelForeground_ = iconLabelForeground_;
  other->iconPosition_ = iconPosition_;
  other->renderingComponent_ = renderingComponent_;
  other->useAlternateIcon_ = useAlternateIcon_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "getComponent", NULL, "LRAREiPlatformComponent", 0x1, NULL },
    { "getComponentWithJavaLangCharSequence:withRARERenderableDataItem:", NULL, "LRAREiPlatformComponent", 0x1, NULL },
    { "getComponentWithRAREiPlatformComponent:withId:withRARERenderableDataItem:withInt:withBoolean:withBoolean:withRAREColumn:withRARERenderableDataItem:withBoolean:", NULL, "LRAREiPlatformComponent", 0x1, NULL },
    { "getIconLabel", NULL, "LRAREUILabelRenderer", 0x1, NULL },
    { "getRenderingComponent", NULL, "LRAREiRenderingComponent", 0x1, NULL },
    { "isUseAlternateIcon", NULL, "Z", 0x1, NULL },
    { "setBorderExWithRAREiPlatformBorder:", NULL, "V", 0x4, NULL },
    { "getMinimumSizeExWithRAREUIDimension:withFloat:", NULL, "V", 0x4, NULL },
    { "getPreferredSizeExWithRAREUIDimension:withFloat:", NULL, "V", 0x4, NULL },
    { "setupNewCopyWithRAREaCompositeRenderer:", NULL, "LRAREiPlatformRenderingComponent", 0x4, NULL },
    { "setIconGapWithFloat:", NULL, "V", 0x2, NULL },
    { "createNewNativeView", NULL, "LNSObject", 0x401, NULL },
    { "newCopy", NULL, "LRAREiPlatformRenderingComponent", 0x401, NULL },
    { "prepareForReuseWithInt:withInt:", NULL, "V", 0x401, NULL },
    { "setNativeViewWithId:", NULL, "V", 0x401, NULL },
    { "setRenderingViewWithRAREView:", NULL, "V", 0x401, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "EMPTY_INSETS_", NULL, 0xc, "LRAREUIInsets" },
    { "backgroundSurface_", NULL, 0x4, "LRAREaCompositeRenderer_BackgroundSurfaceEnum" },
    { "iconPosition_", NULL, 0x4, "LRARERenderableDataItem_IconPositionEnum" },
    { "iconGap_", NULL, 0x4, "F" },
    { "iconLabel_", NULL, 0x4, "LRAREUILabelRenderer" },
    { "renderingComponent_", NULL, 0x4, "LRAREiPlatformRenderingComponent" },
    { "useAlternateIcon_", NULL, 0x4, "Z" },
    { "iconGapInsets_", NULL, 0x0, "LRAREUIInsets" },
    { "columnWidth_", NULL, 0x4, "I" },
  };
  static J2ObjcClassInfo _RAREaCompositeRenderer = { "aCompositeRenderer", "com.appnativa.rare.ui.renderer", NULL, 0x401, 16, methods, 9, fields, 0, NULL};
  return &_RAREaCompositeRenderer;
}

@end

static RAREaCompositeRenderer_BackgroundSurfaceEnum *RAREaCompositeRenderer_BackgroundSurfaceEnum_PANEL;
static RAREaCompositeRenderer_BackgroundSurfaceEnum *RAREaCompositeRenderer_BackgroundSurfaceEnum_RENDERER;
static RAREaCompositeRenderer_BackgroundSurfaceEnum *RAREaCompositeRenderer_BackgroundSurfaceEnum_ICON;
IOSObjectArray *RAREaCompositeRenderer_BackgroundSurfaceEnum_values;

@implementation RAREaCompositeRenderer_BackgroundSurfaceEnum

+ (RAREaCompositeRenderer_BackgroundSurfaceEnum *)PANEL {
  return RAREaCompositeRenderer_BackgroundSurfaceEnum_PANEL;
}
+ (RAREaCompositeRenderer_BackgroundSurfaceEnum *)RENDERER {
  return RAREaCompositeRenderer_BackgroundSurfaceEnum_RENDERER;
}
+ (RAREaCompositeRenderer_BackgroundSurfaceEnum *)ICON {
  return RAREaCompositeRenderer_BackgroundSurfaceEnum_ICON;
}

- (id)copyWithZone:(NSZone *)zone {
  return self;
}

- (id)initWithNSString:(NSString *)__name withInt:(int)__ordinal {
  return [super initWithNSString:__name withInt:__ordinal];
}

+ (void)initialize {
  if (self == [RAREaCompositeRenderer_BackgroundSurfaceEnum class]) {
    RAREaCompositeRenderer_BackgroundSurfaceEnum_PANEL = [[RAREaCompositeRenderer_BackgroundSurfaceEnum alloc] initWithNSString:@"PANEL" withInt:0];
    RAREaCompositeRenderer_BackgroundSurfaceEnum_RENDERER = [[RAREaCompositeRenderer_BackgroundSurfaceEnum alloc] initWithNSString:@"RENDERER" withInt:1];
    RAREaCompositeRenderer_BackgroundSurfaceEnum_ICON = [[RAREaCompositeRenderer_BackgroundSurfaceEnum alloc] initWithNSString:@"ICON" withInt:2];
    RAREaCompositeRenderer_BackgroundSurfaceEnum_values = [[IOSObjectArray alloc] initWithObjects:(id[]){ RAREaCompositeRenderer_BackgroundSurfaceEnum_PANEL, RAREaCompositeRenderer_BackgroundSurfaceEnum_RENDERER, RAREaCompositeRenderer_BackgroundSurfaceEnum_ICON, nil } count:3 type:[IOSClass classWithClass:[RAREaCompositeRenderer_BackgroundSurfaceEnum class]]];
  }
}

+ (IOSObjectArray *)values {
  return [IOSObjectArray arrayWithArray:RAREaCompositeRenderer_BackgroundSurfaceEnum_values];
}

+ (RAREaCompositeRenderer_BackgroundSurfaceEnum *)valueOfWithNSString:(NSString *)name {
  for (int i = 0; i < [RAREaCompositeRenderer_BackgroundSurfaceEnum_values count]; i++) {
    RAREaCompositeRenderer_BackgroundSurfaceEnum *e = RAREaCompositeRenderer_BackgroundSurfaceEnum_values->buffer_[i];
    if ([name isEqual:[e name]]) {
      return e;
    }
  }
  @throw [[JavaLangIllegalArgumentException alloc] initWithNSString:name];
  return nil;
}

+ (J2ObjcClassInfo *)__metadata {
  static const char *superclass_type_args[] = {"LRAREaCompositeRenderer_BackgroundSurfaceEnum"};
  static J2ObjcClassInfo _RAREaCompositeRenderer_BackgroundSurfaceEnum = { "BackgroundSurface", "com.appnativa.rare.ui.renderer", "aCompositeRenderer", 0x4019, 0, NULL, 0, NULL, 1, superclass_type_args};
  return &_RAREaCompositeRenderer_BackgroundSurfaceEnum;
}

@end
