//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/ui/ActionComponent.java
//
//  Created by decoteaud on 12/8/15.
//

#include "IOSClass.h"
#include "com/appnativa/rare/iConstants.h"
#include "com/appnativa/rare/platform/apple/ui/view/CustomButtonView.h"
#include "com/appnativa/rare/platform/apple/ui/view/TextFieldView.h"
#include "com/appnativa/rare/platform/apple/ui/view/View.h"
#include "com/appnativa/rare/ui/ActionComponent.h"
#include "com/appnativa/rare/ui/Component.h"
#include "com/appnativa/rare/ui/FontUtils.h"
#include "com/appnativa/rare/ui/RenderableDataItem.h"
#include "com/appnativa/rare/ui/ScreenUtils.h"
#include "com/appnativa/rare/ui/UIAction.h"
#include "com/appnativa/rare/ui/UIDimension.h"
#include "com/appnativa/rare/ui/UIFont.h"
#include "com/appnativa/rare/ui/UIImageIcon.h"
#include "com/appnativa/rare/ui/UIInsets.h"
#include "com/appnativa/rare/ui/UISpriteIcon.h"
#include "com/appnativa/rare/ui/Utils.h"
#include "com/appnativa/rare/ui/aComponent.h"
#include "com/appnativa/rare/ui/aUIAction.h"
#include "com/appnativa/rare/ui/event/ActionEvent.h"
#include "com/appnativa/rare/ui/event/ChangeEvent.h"
#include "com/appnativa/rare/ui/event/EventListenerList.h"
#include "com/appnativa/rare/ui/event/iActionListener.h"
#include "com/appnativa/rare/ui/event/iChangeListener.h"
#include "com/appnativa/rare/ui/iPaintedButton.h"
#include "com/appnativa/rare/ui/iPlatformIcon.h"
#include "com/appnativa/rare/ui/painter/PainterHolder.h"
#include "com/appnativa/rare/ui/painter/iPlatformComponentPainter.h"
#include "java/beans/PropertyChangeEvent.h"
#include "java/lang/CharSequence.h"
#include "java/lang/Character.h"
#include "java/lang/Math.h"
#include "java/util/EventObject.h"

@implementation RAREActionComponent

- (id)initWithRAREView:(RAREView *)view {
  if (self = [super initWithRAREView:view]) {
    iconPosition_ = [RARERenderableDataItem_IconPositionEnum AUTO];
    iconGap_ = 4;
    if (!([view isKindOfClass:[RARETextFieldView class]])) {
      useBorderInSizeCalculation_ = NO;
    }
    [self setAutoAdjustSizeWithBoolean:YES];
  }
  return self;
}

- (id)init {
  if (self = [super init]) {
    iconPosition_ = [RARERenderableDataItem_IconPositionEnum AUTO];
    iconGap_ = 4;
  }
  return self;
}

- (void)actionPerformedWithRAREActionEvent:(RAREActionEvent *)e {
  if (listenerList_ != nil) {
    [RAREUtils fireActionEventWithRAREEventListenerList:listenerList_ withRAREActionEvent:e];
  }
}

- (void)addActionListenerWithRAREiActionListener:(id<RAREiActionListener>)l {
  [((RAREView *) nil_chk(view_)) setActionListenerWithRAREiActionListener:self];
  [((RAREEventListenerList *) nil_chk([self getEventListenerList])) addWithIOSClass:[IOSClass classWithProtocol:@protocol(RAREiActionListener)] withId:l];
}

- (void)addChangeListenerWithRAREiChangeListener:(id<RAREiChangeListener>)l {
  [((RAREView *) nil_chk(view_)) setChangeListenerWithRAREiChangeListener:self];
  [((RAREEventListenerList *) nil_chk([self getEventListenerList])) addWithIOSClass:[IOSClass classWithProtocol:@protocol(RAREiChangeListener)] withId:l];
}

- (BOOL)adjustMinimumHeightForWidth {
  return [self isWordWrap];
}

- (RAREComponent *)copy__ {
  RAREActionComponent *c = (RAREActionComponent *) check_class_cast([super copy__], [RAREActionComponent class]);
  if (((RAREActionComponent *) nil_chk(c))->listenerList_ != nil) {
    [c->listenerList_ clear];
  }
  return c;
}

- (void)dispose {
  if (self->action_ != nil) {
    [self->action_ removePropertyChangeListenerWithJavaBeansPropertyChangeListener:self];
    self->action_ = nil;
  }
  [super dispose];
  if ([(id) icon_ isKindOfClass:[RAREUISpriteIcon class]]) {
    [((RAREUISpriteIcon *) check_class_cast(icon_, [RAREUISpriteIcon class])) setOwnerWithRAREiPlatformComponent:nil];
  }
  iconPosition_ = nil;
  disabledIcon_ = nil;
  icon_ = nil;
  pressedIcon_ = nil;
  selectedIcon_ = nil;
}

- (void)doClick {
  [((RAREView *) nil_chk(view_)) performClick];
}

- (void)fireActionEvent {
  [((RAREView *) nil_chk(view_)) performClick];
}

- (RAREUIAction *)getAction {
  return action_;
}

- (RAREiPaintedButton_ButtonStateEnum *)getButtonState {
  return [RAREUtils getStateWithBoolean:[self isEnabled] withBoolean:[self isPressed] withBoolean:[self isSelected] withBoolean:[self isMouseOver]];
}

- (id<RAREiPlatformIcon>)getDisabledIcon {
  return disabledIcon_;
}

- (id<RAREiPlatformIcon>)getIcon {
  return icon_;
}

- (int)getIconGap {
  return iconGap_;
}

- (RARERenderableDataItem_IconPositionEnum *)getIconPosition {
  return iconPosition_;
}

- (float)getIconScaleFactor {
  return scaleFactor_;
}

- (RAREUIInsets *)getMargin {
  return [((RAREView *) nil_chk(view_)) getMargin];
}

- (id<RAREiPlatformIcon>)getPressedIcon {
  return pressedIcon_;
}

- (id<RAREiPlatformIcon>)getSelectedIcon {
  return selectedIcon_;
}

- (id<JavaLangCharSequence>)getText {
  id<JavaLangCharSequence> s = [((RAREView *) nil_chk(view_)) getText];
  return (s == nil) ? @"" : ((id) s);
}

- (BOOL)heightChangesBasedOnWidth {
  if (![((RAREView *) nil_chk(view_)) isWordWrap]) {
    return NO;
  }
  id<JavaLangCharSequence> cs = [self getText];
  return (cs != nil) && ([cs sequenceLength] > 0);
}

- (BOOL)isScaleIcon {
  return scaleIcon_ && (scaleFactor_ > 0);
}

- (BOOL)isSelected {
  return [((RAREView *) nil_chk(view_)) isSelected];
}

- (BOOL)isWordWrap {
  return [((RAREView *) nil_chk(view_)) isWordWrap];
}

- (void)propertyChangeWithJavaBeansPropertyChangeEvent:(JavaBeansPropertyChangeEvent *)pce {
  if (!([[((JavaBeansPropertyChangeEvent *) nil_chk(pce)) getSource] isKindOfClass:[RAREUIAction class]])) {
    return;
  }
  RAREUIAction *a = (RAREUIAction *) check_class_cast([pce getSource], [RAREUIAction class]);
  NSString *property = [pce getPropertyName];
  if ([((NSString *) nil_chk(property)) isEqual:[RAREaUIAction ENABLED]]) {
    if ([((RAREUIAction *) nil_chk(a)) isEnabled]) {
      [self setEnabledWithBoolean:YES];
      if ([a getIcon] != nil) {
        [self setIconWithRAREiPlatformIcon:[a getIcon]];
      }
    }
    else {
      [self setEnabledWithBoolean:NO];
      if ([a getDisabledIcon] != nil) {
        [self setIconWithRAREiPlatformIcon:[a getDisabledIcon]];
      }
    }
  }
  else if ([property isEqual:[RAREaUIAction ACTION_TEXT]]) {
    [self setTextWithJavaLangCharSequence:(NSString *) check_class_cast([pce getNewValue], [NSString class])];
  }
  else if ([property isEqual:[RAREaUIAction ICON]]) {
    [self setIconWithRAREiPlatformIcon:(id<RAREiPlatformIcon>) check_protocol_cast([pce getNewValue], @protocol(RAREiPlatformIcon))];
  }
  else if ([property isEqual:[RAREaUIAction DISABLED_ICON]]) {
    [self setDisabledIconWithRAREiPlatformIcon:(id<RAREiPlatformIcon>) check_protocol_cast([pce getNewValue], @protocol(RAREiPlatformIcon))];
  }
  [self updateUI];
}

- (void)putClientPropertyWithNSString:(NSString *)key
                               withId:(id)value {
  if ([RAREiConstants RARE_MIN_HEIGHT_PROPERTY] == key) {
    minHeightSet_ = YES;
  }
  else if (([RAREiConstants RARE_HEIGHT_PROPERTY] == key) && !minHeightSet_) {
    sageMinHeight_ = nil;
  }
  [super putClientPropertyWithNSString:key withId:value];
}

- (void)removeActionListenerWithRAREiActionListener:(id<RAREiActionListener>)l {
  if (listenerList_ != nil) {
    [listenerList_ removeWithIOSClass:[IOSClass classWithProtocol:@protocol(RAREiActionListener)] withId:l];
  }
}

- (void)removeChangeListenerWithRAREiChangeListener:(id<RAREiChangeListener>)l {
  if (listenerList_ != nil) {
    [listenerList_ removeWithIOSClass:[IOSClass classWithProtocol:@protocol(RAREiChangeListener)] withId:l];
  }
}

- (void)setActionWithRAREUIAction:(RAREUIAction *)a {
  if (action_ != nil) {
    [action_ removePropertyChangeListenerWithJavaBeansPropertyChangeListener:self];
    [self removeActionListenerWithRAREiActionListener:action_];
  }
  self->action_ = a;
  if (a != nil) {
    [self addActionListenerWithRAREiActionListener:a];
    [a addPropertyChangeListenerWithJavaBeansPropertyChangeListener:self];
    id<RAREiPlatformIcon> ic = [a getIcon];
    if (ic != nil) {
      [self setIconWithRAREiPlatformIcon:ic];
    }
    ic = [a getDisabledIcon];
    if (ic != nil) {
      [self setDisabledIconWithRAREiPlatformIcon:ic];
    }
    [self setEnabledWithBoolean:[a isEnabled]];
    id<JavaLangCharSequence> s = [a getActionText];
    if (s != nil) {
      [self setTextWithJavaLangCharSequence:s];
    }
  }
}

- (void)setAlignmentWithRARERenderableDataItem_HorizontalAlignEnum:(RARERenderableDataItem_HorizontalAlignEnum *)hal
                      withRARERenderableDataItem_VerticalAlignEnum:(RARERenderableDataItem_VerticalAlignEnum *)val {
  [((RAREView *) nil_chk(view_)) setTextAlignmentWithRARERenderableDataItem_HorizontalAlignEnum:hal withRARERenderableDataItem_VerticalAlignEnum:val];
}

- (void)setAutoAdjustSizeWithBoolean:(BOOL)adjustSize {
  adjustButtonSize_ = adjustTextFieldSize_ = NO;
  if (adjustSize) {
    if ([view_ isKindOfClass:[RARECustomButtonView class]]) {
      adjustButtonSize_ = YES;
    }
    else if ([view_ isKindOfClass:[RARETextFieldView class]]) {
      adjustTextFieldSize_ = YES;
    }
  }
}

- (void)setDisabledIconWithRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon {
  if ([(id) icon isKindOfClass:[RAREUIImageIcon class]]) {
    [((RAREUIImageIcon *) check_class_cast(icon, [RAREUIImageIcon class])) isImageLoadedWithRAREiImageObserver:self];
  }
  disabledIcon_ = icon;
}

- (void)setDisabledSelectedIconWithRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon {
}

- (void)setEnabledWithBoolean:(BOOL)enabled {
  if ([((RAREView *) nil_chk(view_)) isEnabled] != enabled) {
    [super setEnabledWithBoolean:enabled];
    if (enabled) {
      if ((disabledIcon_ != nil) && (icon_ != nil)) {
        [view_ setIconWithRAREiPlatformIcon:icon_];
      }
    }
    else {
      if ((disabledIcon_ == nil) && (icon_ != nil)) {
        disabledIcon_ = [icon_ getDisabledVersion];
      }
      if (disabledIcon_ != nil) {
        [view_ setIconWithRAREiPlatformIcon:disabledIcon_];
      }
    }
  }
}

- (void)setIconWithRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon {
  if (self->icon_ != icon) {
    self->icon_ = icon;
    if ([(id) self->icon_ isKindOfClass:[RAREUISpriteIcon class]]) {
      [((RAREUISpriteIcon *) check_class_cast(self->icon_, [RAREUISpriteIcon class])) setOwnerWithRAREiPlatformComponent:nil];
    }
    if ([(id) icon isKindOfClass:[RAREUIImageIcon class]]) {
      [((RAREUIImageIcon *) check_class_cast(icon, [RAREUIImageIcon class])) isImageLoadedWithRAREiImageObserver:self];
    }
    else if ([(id) icon isKindOfClass:[RAREUISpriteIcon class]]) {
      [((RAREUISpriteIcon *) check_class_cast(icon, [RAREUISpriteIcon class])) setOwnerWithRAREiPlatformComponent:self];
    }
    [((RAREView *) nil_chk(view_)) setIconWithRAREiPlatformIcon:icon];
    if ([view_ isKindOfClass:[RARECustomButtonView class]]) {
      if (pressedIcon_ == nil) {
        [((RARECustomButtonView *) check_class_cast(view_, [RARECustomButtonView class])) setPressedIconWithRAREiPlatformIcon:icon];
      }
      if (selectedIcon_ == nil) {
        [((RARECustomButtonView *) check_class_cast(view_, [RARECustomButtonView class])) setSelectedIconWithRAREiPlatformIcon:icon];
        [((RARECustomButtonView *) check_class_cast(view_, [RARECustomButtonView class])) setPressedSelectedIconWithRAREiPlatformIcon:nil];
      }
      if (disabledIcon_ == nil) {
        [((RARECustomButtonView *) check_class_cast(view_, [RARECustomButtonView class])) setDisabledIconWithRAREiPlatformIcon:nil];
        [((RARECustomButtonView *) check_class_cast(view_, [RARECustomButtonView class])) setDisabledSelectedIconWithRAREiPlatformIcon:nil];
      }
    }
    if (![self isEnabled]) {
      if ((disabledIcon_ == nil) && (icon != nil)) {
        disabledIcon_ = [icon getDisabledVersion];
      }
      if (disabledIcon_ != nil) {
        [view_ setIconWithRAREiPlatformIcon:disabledIcon_];
      }
    }
    [self revalidate];
  }
}

- (void)setIconGapWithInt:(int)iconGap {
  self->iconGap_ = iconGap;
  [((RAREView *) nil_chk(view_)) setIconGapWithInt:iconGap];
  [self revalidate];
}

- (void)setIconPositionWithRARERenderableDataItem_IconPositionEnum:(RARERenderableDataItem_IconPositionEnum *)iconPosition {
  if (self->iconPosition_ != iconPosition) {
    self->iconPosition_ = iconPosition;
    [((RAREView *) nil_chk(view_)) setIconPositionWithRARERenderableDataItem_IconPositionEnum:iconPosition];
    [self revalidate];
  }
}

- (void)setMarginWithFloat:(float)top
                 withFloat:(float)right
                 withFloat:(float)bottom
                 withFloat:(float)left {
  [((RAREView *) nil_chk(view_)) setMarginWithFloat:top withFloat:right withFloat:bottom withFloat:left];
}

- (void)setMarginWithRAREUIInsets:(RAREUIInsets *)insets {
  [((RAREView *) nil_chk(view_)) setMarginWithRAREUIInsets:insets];
}

- (void)setMnemonicWithChar:(unichar)mn {
}

- (void)setOrientationWithRARERenderableDataItem_OrientationEnum:(RARERenderableDataItem_OrientationEnum *)orientation {
  switch ([orientation ordinal]) {
    case RARERenderableDataItem_Orientation_VERTICAL_DOWN:
    [((RAREView *) nil_chk(view_)) setRotationWithInt:90];
    break;
    case RARERenderableDataItem_Orientation_VERTICAL_UP:
    [((RAREView *) nil_chk(view_)) setRotationWithInt:-90];
    break;
    default:
    [((RAREView *) nil_chk(view_)) setRotationWithInt:0];
    break;
  }
}

- (void)setPainterHolderWithRAREPainterHolder:(RAREPainterHolder *)painterHolder {
  [((id<RAREiPlatformComponentPainter>) nil_chk([self getComponentPainterWithBoolean:YES])) setPainterHolderWithRAREPainterHolder:painterHolder];
}

- (void)setPressedIconWithRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon {
  if ([(id) icon isKindOfClass:[RAREUIImageIcon class]]) {
    [((RAREUIImageIcon *) check_class_cast(icon, [RAREUIImageIcon class])) isImageLoadedWithRAREiImageObserver:self];
  }
  if ([view_ isKindOfClass:[RARECustomButtonView class]]) {
    [((RARECustomButtonView *) check_class_cast(view_, [RARECustomButtonView class])) setPressedIconWithRAREiPlatformIcon:icon];
  }
  self->pressedIcon_ = icon;
}

- (void)setScaleIconWithBoolean:(BOOL)scale_
                      withFloat:(float)scaleFactor {
  scaleIcon_ = scale_;
  self->scaleFactor_ = scaleFactor;
}

- (void)setSelectedWithBoolean:(BOOL)selected {
  [((RAREView *) nil_chk(view_)) setSelectedWithBoolean:selected];
}

- (void)setSelectedIconWithRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon {
  if ([(id) icon isKindOfClass:[RAREUIImageIcon class]]) {
    [((RAREUIImageIcon *) check_class_cast(icon, [RAREUIImageIcon class])) isImageLoadedWithRAREiImageObserver:self];
  }
  if ([view_ isKindOfClass:[RARECustomButtonView class]]) {
    [((RARECustomButtonView *) check_class_cast(view_, [RARECustomButtonView class])) setSelectedIconWithRAREiPlatformIcon:icon];
  }
  self->selectedIcon_ = icon;
}

- (void)setTextWithJavaLangCharSequence:(id<JavaLangCharSequence>)text {
  [((RAREView *) nil_chk(view_)) setTextWithJavaLangCharSequence:text];
  [self revalidate];
}

- (void)setToolTipTextWithJavaLangCharSequence:(id<JavaLangCharSequence>)text {
}

- (void)setWordWrapWithBoolean:(BOOL)wrap {
  [((RAREView *) nil_chk(view_)) setWordWrapWithBoolean:wrap];
}

- (void)stateChangedWithJavaUtilEventObject:(JavaUtilEventObject *)e {
  if ((listenerList_ != nil) && [listenerList_ hasListenersWithIOSClass:[IOSClass classWithProtocol:@protocol(RAREiChangeListener)]]) {
    if (changeEvent_ != nil) {
      changeEvent_ = [[RAREChangeEvent alloc] initWithId:self];
    }
    [RAREUtils fireChangeEventWithRAREEventListenerList:listenerList_ withRAREChangeEvent:changeEvent_];
  }
}

- (NSString *)description {
  return [((id<JavaLangCharSequence>) nil_chk([self getText])) sequenceDescription];
}

- (void)getMinimumSizeExWithRAREUIDimension:(RAREUIDimension *)size
                                  withFloat:(float)maxWidth {
  [super getPreferredSizeExWithRAREUIDimension:size withFloat:maxWidth];
  BOOL addBackIconWidth = NO;
  if ([((RAREView *) nil_chk(view_)) isScrollView]) {
    ((RAREUIDimension *) nil_chk(size))->width_ = [RAREFontUtils getCharacterWidthWithRAREUIFont:[self getFont]] * 3;
    addBackIconWidth = YES;
  }
  else {
    id<JavaLangCharSequence> s = [view_ getText];
    int len = (s == nil) ? 0 : [s sequenceLength];
    if (len > 0) {
      int n = 0;
      for (int i = 0; i < len; i++) {
        if ([JavaLangCharacter isWhitespaceWithChar:[((id<JavaLangCharSequence>) nil_chk(s)) charAtWithInt:i]]) {
          n = i;
          break;
        }
      }
      if (n > 0) {
        n *= [RAREFontUtils getCharacterWidthWithRAREUIFont:[self getFont]];
        if (n < ((RAREUIDimension *) nil_chk(size))->width_) {
          size->width_ = [JavaLangMath minWithFloat:n withFloat:size->width_];
          addBackIconWidth = YES;
        }
        size->height_ = [JavaLangMath maxWithFloat:size->height_ withFloat:[RAREScreenUtils lineHeightWithRAREiPlatformComponent:self]];
      }
    }
    if (addBackIconWidth) {
      id<RAREiPlatformIcon> ic = [self getIcon];
      if (ic != nil) {
        switch ([iconPosition_ ordinal]) {
          case RARERenderableDataItem_IconPosition_LEADING:
          case RARERenderableDataItem_IconPosition_LEFT:
          case RARERenderableDataItem_IconPosition_TRAILING:
          case RARERenderableDataItem_IconPosition_RIGHT:
          case RARERenderableDataItem_IconPosition_RIGHT_JUSTIFIED:
          ((RAREUIDimension *) nil_chk(size))->width_ += [ic getIconWidth] + [self getIconGap];
          break;
          default:
          break;
        }
      }
    }
  }
}

- (void)getPreferredSizeExWithRAREUIDimension:(RAREUIDimension *)size
                                    withFloat:(float)maxWidth {
  [super getPreferredSizeExWithRAREUIDimension:size withFloat:maxWidth];
  if (adjustButtonSize_) {
    [RAREUtils adjustButtonSizeWithRAREUIDimension:size];
  }
  else if (adjustTextFieldSize_) {
    [RAREUtils adjustTextFieldSizeWithRAREUIDimension:size];
  }
}

- (id)copyWithZone:(NSZone *)zone {
  return [self clone];
}

- (void)copyAllFieldsTo:(RAREActionComponent *)other {
  [super copyAllFieldsTo:other];
  other->action_ = action_;
  other->adjustButtonSize_ = adjustButtonSize_;
  other->adjustTextFieldSize_ = adjustTextFieldSize_;
  other->disabledIcon_ = disabledIcon_;
  other->icon_ = icon_;
  other->iconGap_ = iconGap_;
  other->iconPosition_ = iconPosition_;
  other->minHeightSet_ = minHeightSet_;
  other->pressedIcon_ = pressedIcon_;
  other->scaleFactor_ = scaleFactor_;
  other->scaleIcon_ = scaleIcon_;
  other->selectedIcon_ = selectedIcon_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "init", NULL, NULL, 0x4, NULL },
    { "adjustMinimumHeightForWidth", NULL, "Z", 0x1, NULL },
    { "copy__", NULL, "LRAREComponent", 0x1, NULL },
    { "getAction", NULL, "LRAREUIAction", 0x1, NULL },
    { "getButtonState", NULL, "LRAREiPaintedButton_ButtonStateEnum", 0x1, NULL },
    { "getDisabledIcon", NULL, "LRAREiPlatformIcon", 0x1, NULL },
    { "getIcon", NULL, "LRAREiPlatformIcon", 0x1, NULL },
    { "getIconPosition", NULL, "LRARERenderableDataItem_IconPositionEnum", 0x1, NULL },
    { "getMargin", NULL, "LRAREUIInsets", 0x1, NULL },
    { "getPressedIcon", NULL, "LRAREiPlatformIcon", 0x1, NULL },
    { "getSelectedIcon", NULL, "LRAREiPlatformIcon", 0x1, NULL },
    { "getText", NULL, "LJavaLangCharSequence", 0x1, NULL },
    { "heightChangesBasedOnWidth", NULL, "Z", 0x1, NULL },
    { "isScaleIcon", NULL, "Z", 0x1, NULL },
    { "isSelected", NULL, "Z", 0x1, NULL },
    { "isWordWrap", NULL, "Z", 0x1, NULL },
    { "getMinimumSizeExWithRAREUIDimension:withFloat:", NULL, "V", 0x4, NULL },
    { "getPreferredSizeExWithRAREUIDimension:withFloat:", NULL, "V", 0x4, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "iconPosition_", NULL, 0x4, "LRARERenderableDataItem_IconPositionEnum" },
    { "action_", NULL, 0x4, "LRAREUIAction" },
    { "disabledIcon_", NULL, 0x4, "LRAREiPlatformIcon" },
    { "icon_", NULL, 0x4, "LRAREiPlatformIcon" },
    { "pressedIcon_", NULL, 0x4, "LRAREiPlatformIcon" },
    { "scaleFactor_", NULL, 0x4, "F" },
    { "scaleIcon_", NULL, 0x4, "Z" },
    { "selectedIcon_", NULL, 0x4, "LRAREiPlatformIcon" },
    { "iconGap_", NULL, 0x4, "I" },
    { "minHeightSet_", NULL, 0x4, "Z" },
    { "adjustButtonSize_", NULL, 0x4, "Z" },
    { "adjustTextFieldSize_", NULL, 0x4, "Z" },
  };
  static J2ObjcClassInfo _RAREActionComponent = { "ActionComponent", "com.appnativa.rare.ui", NULL, 0x1, 18, methods, 12, fields, 0, NULL};
  return &_RAREActionComponent;
}

@end
