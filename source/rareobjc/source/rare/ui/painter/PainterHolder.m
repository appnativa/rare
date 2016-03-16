//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/painter/PainterHolder.java
//
//  Created by decoteaud on 3/11/16.
//

#include "IOSClass.h"
#include "IOSIntArray.h"
#include "com/appnativa/rare/Platform.h"
#include "com/appnativa/rare/iPlatformAppContext.h"
#include "com/appnativa/rare/platform/PlatformHelper.h"
#include "com/appnativa/rare/ui/UIColor.h"
#include "com/appnativa/rare/ui/UIColorHelper.h"
#include "com/appnativa/rare/ui/UIImageIcon.h"
#include "com/appnativa/rare/ui/UIProperties.h"
#include "com/appnativa/rare/ui/iActionComponent.h"
#include "com/appnativa/rare/ui/iPaintedButton.h"
#include "com/appnativa/rare/ui/iPlatformBorder.h"
#include "com/appnativa/rare/ui/iPlatformGraphics.h"
#include "com/appnativa/rare/ui/iPlatformIcon.h"
#include "com/appnativa/rare/ui/painter/PaintBucket.h"
#include "com/appnativa/rare/ui/painter/PainterHolder.h"
#include "com/appnativa/rare/ui/painter/UIComponentPainter.h"
#include "com/appnativa/rare/ui/painter/iBackgroundPainter.h"
#include "com/appnativa/rare/ui/painter/iImagePainter.h"
#include "com/appnativa/rare/ui/painter/iPlatformComponentPainter.h"
#include "com/appnativa/rare/ui/painter/iPlatformPainter.h"
#include "com/appnativa/rare/widget/iWidget.h"
#include "com/appnativa/spot/iSPOTElement.h"
#include "com/appnativa/util/CharScanner.h"
#include "java/lang/CloneNotSupportedException.h"
#include "java/lang/InternalError.h"
#include "java/lang/StringBuilder.h"
#include "java/util/ArrayList.h"
#include "java/util/Iterator.h"
#include "java/util/List.h"
#include "java/util/Map.h"
#include "java/util/Set.h"

@implementation RAREPainterHolder

- (id)init {
  if (self = [super init]) {
    usesPresedAsOverlay_ = YES;
  }
  return self;
}

- (id)initWithRAREiBackgroundPainter:(id<RAREiBackgroundPainter>)selected
          withRAREiBackgroundPainter:(id<RAREiBackgroundPainter>)rollover
          withRAREiBackgroundPainter:(id<RAREiBackgroundPainter>)pressed {
  if (self = [super init]) {
    usesPresedAsOverlay_ = YES;
    self->selectedPainter_ = [[RAREPaintBucket alloc] initWithRAREiBackgroundPainter:selected];
    self->rolloverPainter_ = [[RAREPaintBucket alloc] initWithRAREiBackgroundPainter:rollover];
    self->pressedPainter_ = [[RAREPaintBucket alloc] initWithRAREiBackgroundPainter:pressed];
  }
  return self;
}

- (id)initWithRAREiBackgroundPainter:(id<RAREiBackgroundPainter>)selected
          withRAREiBackgroundPainter:(id<RAREiBackgroundPainter>)rollover
          withRAREiBackgroundPainter:(id<RAREiBackgroundPainter>)pressed
          withRAREiBackgroundPainter:(id<RAREiBackgroundPainter>)disabled {
  if (self = [super init]) {
    usesPresedAsOverlay_ = YES;
    self->selectedPainter_ = [[RAREPaintBucket alloc] initWithRAREiBackgroundPainter:selected];
    self->rolloverPainter_ = [[RAREPaintBucket alloc] initWithRAREiBackgroundPainter:rollover];
    self->pressedPainter_ = [[RAREPaintBucket alloc] initWithRAREiBackgroundPainter:pressed];
    self->disabledPainter_ = [[RAREPaintBucket alloc] initWithRAREiBackgroundPainter:disabled];
  }
  return self;
}

- (id)initWithRAREiPlatformIcon:(id<RAREiPlatformIcon>)normalIcon
          withRAREiPlatformIcon:(id<RAREiPlatformIcon>)selectedIcon
          withRAREiPlatformIcon:(id<RAREiPlatformIcon>)rolloverIcon
          withRAREiPlatformIcon:(id<RAREiPlatformIcon>)pressedIcon
          withRAREiPlatformIcon:(id<RAREiPlatformIcon>)disabledIcon {
  if (self = [super init]) {
    usesPresedAsOverlay_ = YES;
    self->selectedIcon_ = selectedIcon;
    self->rolloverIcon_ = rolloverIcon;
    self->pressedIcon_ = pressedIcon;
    self->normalIcon_ = normalIcon;
    self->disabledIcon_ = disabledIcon;
  }
  return self;
}

- (id)initWithRAREPaintBucket:(RAREPaintBucket *)normalPainter
          withRAREPaintBucket:(RAREPaintBucket *)selectedPainter
          withRAREPaintBucket:(RAREPaintBucket *)rolloverPainter
          withRAREPaintBucket:(RAREPaintBucket *)pressedPainter
          withRAREPaintBucket:(RAREPaintBucket *)disabledPainter {
  if (self = [super init]) {
    usesPresedAsOverlay_ = YES;
    self->selectedPainter_ = selectedPainter;
    self->rolloverPainter_ = rolloverPainter;
    self->pressedPainter_ = pressedPainter;
    self->normalPainter_ = normalPainter;
    self->disabledPainter_ = disabledPainter;
  }
  return self;
}

- (void)clear {
  self->selectedIcon_ = nil;
  self->rolloverIcon_ = nil;
  self->pressedIcon_ = nil;
  self->normalIcon_ = nil;
  self->disabledIcon_ = nil;
  self->selectedPainter_ = nil;
  self->rolloverPainter_ = nil;
  self->pressedPainter_ = nil;
  self->normalPainter_ = nil;
  self->disabledPainter_ = nil;
  self->disabledSelectedIcon_ = nil;
  self->disabledSelectedPainter_ = nil;
  self->pressedSelectedIcon_ = nil;
  self->foregroundColor_ = nil;
}

- (id)clone {
  @try {
    RAREPainterHolder *ph = (RAREPainterHolder *) check_class_cast([super clone], [RAREPainterHolder class]);
    ((RAREPainterHolder *) nil_chk(ph))->shared_ = NO;
    return ph;
  }
  @catch (JavaLangCloneNotSupportedException *ex) {
    @throw [[JavaLangInternalError alloc] init];
  }
}

- (void)dispose {
  [self clear];
}

- (RAREUIColor *)getBackgroundWithRAREiPaintedButton_ButtonStateEnum:(RAREiPaintedButton_ButtonStateEnum *)state {
  RAREPaintBucket *pb = [self getPaintBucketWithRAREiPaintedButton_ButtonStateEnum:state withBoolean:YES];
  RAREUIColor *c = (pb == nil) ? nil : [pb getBackgroundColor];
  if ((c == nil) && (normalPainter_ != nil)) {
    c = [normalPainter_ getBackgroundColor];
  }
  return c;
}

- (id<RAREiPlatformPainter>)getBackgroundOverlayPainterWithRAREiPaintedButton_ButtonStateEnum:(RAREiPaintedButton_ButtonStateEnum *)state {
  RAREPaintBucket *pb = [self getPaintBucketWithRAREiPaintedButton_ButtonStateEnum:state withBoolean:YES];
  id<RAREiPlatformPainter> p = (pb == nil) ? nil : [pb getImagePainter];
  if ((p == nil) && (normalPainter_ != nil)) {
    p = [normalPainter_ getImagePainter];
  }
  return p;
}

- (id<RAREiBackgroundPainter>)getBackgroundPainterWithRAREiPaintedButton_ButtonStateEnum:(RAREiPaintedButton_ButtonStateEnum *)state {
  RAREPaintBucket *pb = [self getPaintBucketWithRAREiPaintedButton_ButtonStateEnum:state withBoolean:YES];
  id<RAREiBackgroundPainter> p = (pb == nil) ? nil : [pb getBackgroundPainter];
  if ((p == nil) && (normalPainter_ != nil)) {
    p = [normalPainter_ getBackgroundPainter];
  }
  return p;
}

- (id<RAREiPlatformBorder>)getBorderWithRAREiPaintedButton_ButtonStateEnum:(RAREiPaintedButton_ButtonStateEnum *)state {
  RAREPaintBucket *pb = [self getPaintBucketWithRAREiPaintedButton_ButtonStateEnum:state withBoolean:YES];
  id<RAREiPlatformBorder> b = (pb == nil) ? nil : [pb getBorder];
  if ((b == nil) && (normalPainter_ != nil)) {
    b = [normalPainter_ getBorder];
  }
  return b;
}

- (RAREUIComponentPainter *)getConfiguredComponentPainterWithRAREiPaintedButton_ButtonStateEnum:(RAREiPaintedButton_ButtonStateEnum *)state {
  id<RAREiPlatformBorder> b = [self getBorderWithRAREiPaintedButton_ButtonStateEnum:state];
  id<RAREiBackgroundPainter> p = [self getBackgroundPainterWithRAREiPaintedButton_ButtonStateEnum:state];
  if (componentPainter_ == nil) {
    componentPainter_ = [[RAREUIComponentPainter alloc] init];
  }
  else {
    [componentPainter_ clear];
  }
  [((RAREUIComponentPainter *) nil_chk(componentPainter_)) setBorderWithRAREiPlatformBorder:b];
  [componentPainter_ setBackgroundPainterWithRAREiBackgroundPainter:p withBoolean:NO];
  if (p == nil) {
    [componentPainter_ setBackgroundColorWithRAREUIColor:[self getBackgroundWithRAREiPaintedButton_ButtonStateEnum:state]];
  }
  [componentPainter_ setBackgroundOverlayPainterWithRAREiPlatformPainter:[self getBackgroundOverlayPainterWithRAREiPaintedButton_ButtonStateEnum:state]];
  return componentPainter_;
}

- (id<RAREiPlatformIcon>)getDisabledIcon {
  return disabledIcon_;
}

- (RAREPaintBucket *)getDisabledPainter {
  return disabledPainter_;
}

- (RAREUIColor *)getForegroundWithRAREiPaintedButton_ButtonStateEnum:(RAREiPaintedButton_ButtonStateEnum *)state {
  return [self getForegroundWithRAREiPaintedButton_ButtonStateEnum:state withBoolean:NO];
}

- (RAREUIColor *)getForegroundWithRAREiPaintedButton_ButtonStateEnum:(RAREiPaintedButton_ButtonStateEnum *)state
                                                         withBoolean:(BOOL)returnDefaults {
  RAREUIColor *color = nil;
  {
    RAREPaintBucket *pb;
    switch ([state ordinal]) {
      case RAREiPaintedButton_ButtonState_DISABLED:
      case RAREiPaintedButton_ButtonState_DISABLED_SELECTED:
      color = (disabledPainter_ == nil) ? nil : [disabledPainter_ getForegroundColor];
      break;
      default:
      pb = [self getPaintBucketWithRAREiPaintedButton_ButtonStateEnum:state withBoolean:YES];
      color = (pb == nil) ? nil : [pb getForegroundColor];
      break;
    }
  }
  if (color == nil && foregroundColor_ != nil) {
    color = [foregroundColor_ getColorWithRAREiPaintedButton_ButtonStateEnum:state];
  }
  if ((color == nil) && returnDefaults) {
    color = [RAREUIColorHelper getForeground];
  }
  return color;
}

- (id<RAREiPlatformIcon>)getIconWithRAREiPaintedButton_ButtonStateEnum:(RAREiPaintedButton_ButtonStateEnum *)state {
  switch ([state ordinal]) {
    case RAREiPaintedButton_ButtonState_DISABLED:
    if (disabledIcon_ != nil) {
      return disabledIcon_;
    }
    break;
    case RAREiPaintedButton_ButtonState_DISABLED_SELECTED:
    if (disabledSelectedIcon_ != nil) {
      return disabledSelectedIcon_;
    }
    if (disabledIcon_ != nil) {
      return disabledIcon_;
    }
    break;
    case RAREiPaintedButton_ButtonState_PRESSED:
    if (pressedIcon_ != nil) {
      return pressedIcon_;
    }
    break;
    case RAREiPaintedButton_ButtonState_PRESSED_SELECTED:
    if (pressedSelectedIcon_ != nil) {
      return pressedSelectedIcon_;
    }
    if (selectedIcon_ != nil) {
      return selectedIcon_;
    }
    break;
    case RAREiPaintedButton_ButtonState_SELECTED:
    if (selectedIcon_ != nil) {
      return selectedIcon_;
    }
    break;
    case RAREiPaintedButton_ButtonState_ROLLOVER:
    if (rolloverIcon_ != nil) {
      return rolloverIcon_;
    }
    break;
    default:
    break;
  }
  return normalIcon_;
}

- (id<RAREiPlatformIcon>)getNormalIcon {
  return normalIcon_;
}

- (RAREPaintBucket *)getNormalPainter {
  return normalPainter_;
}

- (RAREPaintBucket *)getPaintBucketWithRAREiPaintedButton_ButtonStateEnum:(RAREiPaintedButton_ButtonStateEnum *)state
                                                              withBoolean:(BOOL)defaultToNormal {
  RAREPaintBucket *pb = nil;
  switch ([state ordinal]) {
    case RAREiPaintedButton_ButtonState_DISABLED:
    case RAREiPaintedButton_ButtonState_DISABLED_SELECTED:
    pb = disabledPainter_;
    break;
    case RAREiPaintedButton_ButtonState_PRESSED:
    pb = pressedPainter_;
    if (pb == nil) {
      pb = rolloverPainter_;
    }
    if (pb == nil) {
      pb = selectedPainter_;
    }
    break;
    case RAREiPaintedButton_ButtonState_PRESSED_SELECTED:
    pb = pressedPainter_;
    if (pb == nil) {
      pb = selectedPainter_;
    }
    if (pb == nil) {
      pb = rolloverPainter_;
    }
    break;
    case RAREiPaintedButton_ButtonState_SELECTED:
    pb = selectedPainter_;
    break;
    case RAREiPaintedButton_ButtonState_ROLLOVER:
    pb = rolloverPainter_;
    break;
    default:
    break;
  }
  if ((pb == nil) && defaultToNormal) {
    pb = normalPainter_;
  }
  return pb;
}

- (id<RAREiPlatformIcon>)getPressedIcon {
  return pressedIcon_;
}

- (RAREPaintBucket *)getPressedPainter {
  return pressedPainter_;
}

- (id<RAREiPlatformIcon>)getRolloverIcon {
  return rolloverIcon_;
}

- (RAREPaintBucket *)getRolloverPainter {
  return rolloverPainter_;
}

- (id<RAREiPlatformIcon>)getSelectedIcon {
  return selectedIcon_;
}

- (RAREPaintBucket *)getSelectedPainter {
  return selectedPainter_;
}

- (void)handleEnabledWithRAREiActionComponent:(id<RAREiActionComponent>)c {
  if ([((id<RAREiActionComponent>) nil_chk(c)) isEnabled]) {
    if ((disabledIcon_ != nil) || (disabledPainter_ != nil)) {
      if (disabledIcon_ != nil) {
        [c setIconWithRAREiPlatformIcon:normalIcon_];
      }
      if (disabledPainter_ != nil) {
        [c setComponentPainterWithRAREiPlatformComponentPainter:[((RAREPaintBucket *) nil_chk(normalPainter_)) getComponentPainter]];
      }
      [c repaint];
    }
  }
  else {
    if ((disabledIcon_ != nil) || (disabledPainter_ != nil)) {
      if (disabledIcon_ != nil) {
        [c setIconWithRAREiPlatformIcon:disabledIcon_];
      }
      if (disabledPainter_ != nil) {
        [c setComponentPainterWithRAREiPlatformComponentPainter:[disabledPainter_ getComponentPainter]];
      }
      [c repaint];
    }
  }
}

- (void)handleMouseOutWithRAREiActionComponent:(id<RAREiActionComponent>)c {
  if ((rolloverIcon_ != nil) || (rolloverPainter_ != nil)) {
    if (rolloverIcon_ != nil) {
      [((id<RAREiActionComponent>) nil_chk(c)) setIconWithRAREiPlatformIcon:normalIcon_];
    }
    if (rolloverPainter_ != nil) {
      [((id<RAREiActionComponent>) nil_chk(c)) setComponentPainterWithRAREiPlatformComponentPainter:[((RAREPaintBucket *) nil_chk(normalPainter_)) getComponentPainter]];
    }
    [((id<RAREiActionComponent>) nil_chk(c)) repaint];
  }
}

- (void)handleMouseOverWithRAREiActionComponent:(id<RAREiActionComponent>)c {
  if ((rolloverIcon_ != nil) || (rolloverPainter_ != nil)) {
    if (rolloverIcon_ != nil) {
      [((id<RAREiActionComponent>) nil_chk(c)) setIconWithRAREiPlatformIcon:rolloverIcon_];
    }
    if (rolloverPainter_ != nil) {
      [((id<RAREiActionComponent>) nil_chk(c)) setComponentPainterWithRAREiPlatformComponentPainter:[rolloverPainter_ getComponentPainter]];
    }
    [((id<RAREiActionComponent>) nil_chk(c)) repaint];
  }
}

- (void)handleMousePressedWithRAREiActionComponent:(id<RAREiActionComponent>)c {
  if ((pressedIcon_ != nil) || (pressedPainter_ != nil)) {
    if (pressedIcon_ != nil) {
      [((id<RAREiActionComponent>) nil_chk(c)) setIconWithRAREiPlatformIcon:pressedIcon_];
    }
    if (pressedPainter_ != nil) {
      [((id<RAREiActionComponent>) nil_chk(c)) setComponentPainterWithRAREiPlatformComponentPainter:[pressedPainter_ getComponentPainter]];
    }
    [((id<RAREiActionComponent>) nil_chk(c)) repaint];
  }
  else {
    [self handleMouseOverWithRAREiActionComponent:c];
  }
}

- (BOOL)isBackgroundPaintEnabled {
  if ((selectedPainter_ != nil) || (pressedPainter_ != nil) || (normalPainter_ != nil) || (disabledPainter_ != nil)) {
    return YES;
  }
  return NO;
}

- (BOOL)isShared {
  return shared_;
}

- (BOOL)needsChangeHandler {
  if ((disabledIcon_ != nil) || (disabledPainter_ != nil)) {
    return YES;
  }
  return NO;
}

- (BOOL)needsMouseHandler {
  if ((rolloverIcon_ != nil) || (rolloverPainter_ != nil)) {
    return YES;
  }
  if ((pressedIcon_ != nil) || (pressedPainter_ != nil)) {
    return YES;
  }
  return NO;
}

- (void)paintWithRAREiPlatformGraphics:(id<RAREiPlatformGraphics>)g
                             withFloat:(float)x
                             withFloat:(float)y
                             withFloat:(float)width
                             withFloat:(float)height
withRAREiPaintedButton_ButtonStateEnum:(RAREiPaintedButton_ButtonStateEnum *)state
                               withInt:(int)orientation
                           withBoolean:(BOOL)paintIcon {
  [((RAREUIComponentPainter *) nil_chk([self getConfiguredComponentPainterWithRAREiPaintedButton_ButtonStateEnum:state])) paintWithRAREiPlatformGraphics:g withFloat:x withFloat:y withFloat:width withFloat:height withInt:orientation];
  if (paintIcon) {
    id<RAREiPlatformIcon> icon = [self getIconWithRAREiPaintedButton_ButtonStateEnum:state];
    if (icon != nil) {
      [icon paintWithRAREiPlatformGraphics:g withFloat:x withFloat:y withFloat:width withFloat:height];
    }
  }
}

- (void)setDisabledIconWithRAREiPlatformIcon:(id<RAREiPlatformIcon>)disabledIcon {
  self->disabledIcon_ = disabledIcon;
}

- (void)setDisabledPainterWithRAREPaintBucket:(RAREPaintBucket *)disabledPainter {
  self->disabledPainter_ = disabledPainter;
}

- (void)setForegroundColorWithRAREUIColor:(RAREUIColor *)color {
  foregroundColor_ = color;
}

- (void)setNormalIconWithRAREiPlatformIcon:(id<RAREiPlatformIcon>)normalIcon {
  self->normalIcon_ = normalIcon;
}

- (void)setNormalPainterWithRAREPaintBucket:(RAREPaintBucket *)normalPainter {
  self->normalPainter_ = normalPainter;
}

- (void)setPressedIconWithRAREiPlatformIcon:(id<RAREiPlatformIcon>)pressedIcon {
  self->pressedIcon_ = pressedIcon;
}

- (void)setPressedPainterWithRAREPaintBucket:(RAREPaintBucket *)pressedPainter {
  self->pressedPainter_ = pressedPainter;
}

- (void)setRolloverIconWithRAREiPlatformIcon:(id<RAREiPlatformIcon>)rolloverIcon {
  self->rolloverIcon_ = rolloverIcon;
}

- (void)setRolloverPainterWithRAREPaintBucket:(RAREPaintBucket *)rolloverPainter {
  self->rolloverPainter_ = rolloverPainter;
}

- (void)setSelectedIconWithRAREiPlatformIcon:(id<RAREiPlatformIcon>)selectedIcon {
  self->selectedIcon_ = selectedIcon;
}

- (void)setSelectedPainterWithRAREPaintBucket:(RAREPaintBucket *)selectedPainter {
  self->selectedPainter_ = selectedPainter;
}

- (void)setSharedWithBoolean:(BOOL)shared {
  self->shared_ = shared;
}

+ (RAREPainterHolder *)createWithRAREiWidget:(id<RAREiWidget>)context
                            withISPOTElement:(id<iSPOTElement>)cfg {
  RAREPaintBucket *pp = nil, *dp = nil, *np = nil, *sp = nil, *dsp = nil;
  RAREUIColor *fg = nil;
  NSString *s;
  s = [((id<iSPOTElement>) nil_chk(cfg)) spot_getAttributeWithNSString:@"fgColor"];
  if (s != nil) {
    fg = [RAREUIColorHelper getColorWithNSString:s];
  }
  s = [cfg spot_getAttributeWithNSString:@"bgColor"];
  if (s == nil) {
    s = [cfg spot_getAttributeWithNSString:@"normalPainter"];
  }
  if ((s != nil) && ([s sequenceLength] > 0)) {
    np = [RAREUIColorHelper getPaintBucketWithNSString:s];
  }
  s = [cfg spot_getAttributeWithNSString:@"pressedPainter"];
  if ((s != nil) && ([s sequenceLength] > 0)) {
    pp = [RAREUIColorHelper getPaintBucketWithNSString:s];
  }
  s = [cfg spot_getAttributeWithNSString:@"disabledPainter"];
  if ((s != nil) && ([s sequenceLength] > 0)) {
    dp = [RAREUIColorHelper getPaintBucketWithNSString:s];
  }
  s = [cfg spot_getAttributeWithNSString:@"selectedPainter"];
  if ((s != nil) && ([s sequenceLength] > 0)) {
    sp = [RAREUIColorHelper getPaintBucketWithNSString:s];
  }
  s = [cfg spot_getAttributeWithNSString:@"disabledSelectedPainter"];
  if ((s != nil) && ([s sequenceLength] > 0)) {
    dsp = [RAREUIColorHelper getPaintBucketWithNSString:s];
  }
  RAREPainterHolder *ph = nil;
  if ((pp != nil) || (dp != nil) || (sp != nil) || (dsp != nil)) {
    ph = [[RAREPainterHolder alloc] initWithRAREPaintBucket:np withRAREPaintBucket:sp withRAREPaintBucket:nil withRAREPaintBucket:pp withRAREPaintBucket:dp];
    ph->disabledSelectedPainter_ = dsp;
  }
  id<RAREiPlatformIcon> ni = nil, si = nil, di = nil, pi = nil;
  s = [cfg spot_getAttributeWithNSString:@"icon"];
  if (s == nil) {
    s = [cfg spot_getAttributeWithNSString:@"normalIcon"];
  }
  if ((s != nil) && ([s sequenceLength] > 0)) {
    ni = [((id<RAREiWidget>) nil_chk(context)) getIconWithNSString:s withNSString:nil];
  }
  s = [cfg spot_getAttributeWithNSString:@"selectedIcon"];
  if ((s != nil) && ([s sequenceLength] > 0)) {
    si = [((id<RAREiWidget>) nil_chk(context)) getIconWithNSString:s withNSString:nil];
  }
  s = [cfg spot_getAttributeWithNSString:@"disabledIcon"];
  if ((s != nil) && ([s sequenceLength] > 0)) {
    di = [((id<RAREiWidget>) nil_chk(context)) getIconWithNSString:s withNSString:nil];
  }
  s = [cfg spot_getAttributeWithNSString:@"pressedIcon"];
  if ((s != nil) && ([s sequenceLength] > 0)) {
    pi = [((id<RAREiWidget>) nil_chk(context)) getIconWithNSString:s withNSString:nil];
  }
  if ((pi != nil) || (si != nil) || (ni != nil) || (di != nil) || (fg != nil)) {
    if (ph == nil) {
      ph = [[RAREPainterHolder alloc] initWithRAREiPlatformIcon:ni withRAREiPlatformIcon:si withRAREiPlatformIcon:nil withRAREiPlatformIcon:pi withRAREiPlatformIcon:di];
    }
    else {
      ph->disabledIcon_ = di;
      ph->normalIcon_ = ni;
      ph->selectedIcon_ = si;
      ph->pressedIcon_ = pi;
    }
    ((RAREPainterHolder *) nil_chk(ph))->foregroundColor_ = fg;
  }
  return ph;
}

+ (RAREPainterHolder *)createWithNSString:(NSString *)keyPrefix
                              withBoolean:(BOOL)iconsOnly
                              withBoolean:(BOOL)paintOnly
                              withBoolean:(BOOL)themed {
  JavaLangStringBuilder *sb = [[JavaLangStringBuilder alloc] initWithNSString:keyPrefix];
  int sblen = [sb sequenceLength];
  RAREPainterHolder *ph = [[RAREPainterHolder alloc] init];
  id<RAREiPlatformAppContext> app = [RAREPlatform getAppContext];
  RAREUIProperties *props = [((id<RAREiPlatformAppContext>) nil_chk(app)) getUIDefaults];
  NSString *theme = themed ? ([RAREaPlatformHelper isDarkTheme] ? @"dark" : @"light") : nil;
  (void) [sb appendWithNSString:@"foreground"];
  if (themed) {
    (void) [sb appendWithNSString:theme];
  }
  ph->foregroundColor_ = [((RAREUIProperties *) nil_chk(props)) getColorWithNSString:[sb description]];
  if (!paintOnly) {
    [sb setLengthWithInt:sblen];
    (void) [sb appendWithNSString:@"icon"];
    if (themed) {
      (void) [sb appendWithNSString:theme];
    }
    ph->normalIcon_ = [app getResourceAsIconExWithNSString:[sb description]];
    [sb setLengthWithInt:sblen];
    (void) [sb appendWithNSString:@"disabledIcon"];
    if (themed) {
      (void) [sb appendWithNSString:theme];
    }
    ph->disabledIcon_ = [app getResourceAsIconExWithNSString:[sb description]];
    [sb setLengthWithInt:sblen];
    (void) [sb appendWithNSString:@"disabledSelectedIcon"];
    if (themed) {
      (void) [sb appendWithNSString:theme];
    }
    ph->disabledSelectedIcon_ = [app getResourceAsIconExWithNSString:[sb description]];
    [sb setLengthWithInt:sblen];
    (void) [sb appendWithNSString:@"pressedIcon"];
    if (themed) {
      (void) [sb appendWithNSString:theme];
    }
    ph->pressedIcon_ = [app getResourceAsIconExWithNSString:[sb description]];
    [sb setLengthWithInt:sblen];
    (void) [sb appendWithNSString:@"pressedSelectedIcon"];
    if (themed) {
      (void) [sb appendWithNSString:theme];
    }
    ph->pressedSelectedIcon_ = [app getResourceAsIconExWithNSString:[sb description]];
    [sb setLengthWithInt:sblen];
    (void) [sb appendWithNSString:@"selectedIcon"];
    if (themed) {
      (void) [sb appendWithNSString:theme];
    }
    ph->selectedIcon_ = [app getResourceAsIconExWithNSString:[sb description]];
  }
  if (!iconsOnly) {
    [sb setLengthWithInt:sblen];
    (void) [sb appendWithNSString:@"painter"];
    if (themed) {
      (void) [sb appendWithNSString:theme];
    }
    ph->normalPainter_ = [props getPaintBucketWithNSString:[sb description]];
    if (ph->normalPainter_ == nil) {
      [sb setLengthWithInt:sblen];
      (void) [sb appendWithNSString:@"background"];
      ph->normalPainter_ = [((RAREUIProperties *) nil_chk([RAREPlatform getUIDefaults])) getPaintBucketWithNSString:[sb description]];
    }
    [sb setLengthWithInt:sblen];
    (void) [sb appendWithNSString:@"disabledPainter"];
    if (themed) {
      (void) [sb appendWithNSString:theme];
    }
    ph->disabledPainter_ = [props getPaintBucketWithNSString:[sb description]];
    [sb setLengthWithInt:sblen];
    (void) [sb appendWithNSString:@"pressedPainter"];
    if (themed) {
      (void) [sb appendWithNSString:theme];
    }
    ph->pressedPainter_ = [props getPaintBucketWithNSString:[sb description]];
    [sb setLengthWithInt:sblen];
    (void) [sb appendWithNSString:@"selectedPainter"];
    if (themed) {
      (void) [sb appendWithNSString:theme];
    }
    ph->selectedPainter_ = [props getPaintBucketWithNSString:[sb description]];
  }
  return ph;
}

+ (RAREPainterHolder *)createFromIconStateMapWithJavaUtilMap:(id<JavaUtilMap>)map
                                       withRAREUTCharScanner:(RAREUTCharScanner *)sc {
  if (map == nil) {
    return nil;
  }
  if (sc == nil) {
    sc = [[RAREUTCharScanner alloc] init];
  }
  NSString *icon;
  id<JavaUtilMap_Entry> e;
  id<JavaUtilIterator> it = [((id<JavaUtilSet>) nil_chk([((id<JavaUtilMap>) nil_chk(map)) entrySet])) iterator];
  id<RAREiPlatformIcon> ic;
  IOSIntArray *a;
  id<JavaUtilList> list = [[JavaUtilArrayList alloc] initWithInt:3];
  RAREPainterHolder *ph = [[RAREPainterHolder alloc] init];
  id<RAREiPlatformAppContext> app = [RAREPlatform getAppContext];
  while ([((id<JavaUtilIterator>) nil_chk(it)) hasNext]) {
    e = [it next];
    icon = [((id<JavaUtilMap_Entry>) nil_chk(e)) getValue];
    ic = [((id<RAREiPlatformAppContext>) nil_chk(app)) getResourceAsIconWithNSString:icon];
    icon = [e getKey];
    [((RAREUTCharScanner *) nil_chk(sc)) resetWithNSString:icon];
    (void) [sc toLowerCase];
    if ([@"normal" isEqual:icon]) {
      ph->normalIcon_ = ic;
      continue;
    }
    list = [sc getTokensWithChar:',' withBoolean:YES withJavaUtilList:list];
    a = [IOSIntArray arrayWithLength:[((id<JavaUtilList>) nil_chk(list)) size]];
    for (int n = 0; n < (int) [a count]; n++) {
      NSString *s = [list getWithInt:n];
      if ([((NSString *) nil_chk(s)) isEqual:@"disabled"] || [s hasSuffix:@"not_enabled"]) {
        ph->disabledIcon_ = ic;
      }
      else if ([s isEqual:@"selected"]) {
        ph->selectedIcon_ = ic;
      }
      else if ([s isEqual:@"selected_disabled"] || [s isEqual:@"selecteddisabled"]) {
        ph->disabledSelectedIcon_ = ic;
      }
      else if ([s isEqual:@"rollover"]) {
        ph->rolloverIcon_ = ic;
      }
      else if ([s isEqual:@"pressed"]) {
        ph->pressedIcon_ = ic;
      }
    }
  }
  return ph;
}

- (id)copyWithZone:(NSZone *)zone {
  return [self clone];
}

- (void)copyAllFieldsTo:(RAREPainterHolder *)other {
  [super copyAllFieldsTo:other];
  other->componentPainter_ = componentPainter_;
  other->disabledIcon_ = disabledIcon_;
  other->disabledPainter_ = disabledPainter_;
  other->disabledSelectedIcon_ = disabledSelectedIcon_;
  other->disabledSelectedPainter_ = disabledSelectedPainter_;
  other->foregroundColor_ = foregroundColor_;
  other->normalIcon_ = normalIcon_;
  other->normalPainter_ = normalPainter_;
  other->pressedIcon_ = pressedIcon_;
  other->pressedPainter_ = pressedPainter_;
  other->pressedSelectedIcon_ = pressedSelectedIcon_;
  other->rolloverIcon_ = rolloverIcon_;
  other->rolloverPainter_ = rolloverPainter_;
  other->selectedIcon_ = selectedIcon_;
  other->selectedPainter_ = selectedPainter_;
  other->shared_ = shared_;
  other->usesPresedAsOverlay_ = usesPresedAsOverlay_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "clone", NULL, "LNSObject", 0x1, NULL },
    { "getBackgroundWithRAREiPaintedButton_ButtonStateEnum:", NULL, "LRAREUIColor", 0x1, NULL },
    { "getBackgroundOverlayPainterWithRAREiPaintedButton_ButtonStateEnum:", NULL, "LRAREiPlatformPainter", 0x1, NULL },
    { "getBackgroundPainterWithRAREiPaintedButton_ButtonStateEnum:", NULL, "LRAREiBackgroundPainter", 0x1, NULL },
    { "getBorderWithRAREiPaintedButton_ButtonStateEnum:", NULL, "LRAREiPlatformBorder", 0x1, NULL },
    { "getConfiguredComponentPainterWithRAREiPaintedButton_ButtonStateEnum:", NULL, "LRAREUIComponentPainter", 0x1, NULL },
    { "getDisabledIcon", NULL, "LRAREiPlatformIcon", 0x1, NULL },
    { "getDisabledPainter", NULL, "LRAREPaintBucket", 0x1, NULL },
    { "getForegroundWithRAREiPaintedButton_ButtonStateEnum:", NULL, "LRAREUIColor", 0x1, NULL },
    { "getForegroundWithRAREiPaintedButton_ButtonStateEnum:withBoolean:", NULL, "LRAREUIColor", 0x1, NULL },
    { "getIconWithRAREiPaintedButton_ButtonStateEnum:", NULL, "LRAREiPlatformIcon", 0x1, NULL },
    { "getNormalIcon", NULL, "LRAREiPlatformIcon", 0x1, NULL },
    { "getNormalPainter", NULL, "LRAREPaintBucket", 0x1, NULL },
    { "getPaintBucketWithRAREiPaintedButton_ButtonStateEnum:withBoolean:", NULL, "LRAREPaintBucket", 0x1, NULL },
    { "getPressedIcon", NULL, "LRAREiPlatformIcon", 0x1, NULL },
    { "getPressedPainter", NULL, "LRAREPaintBucket", 0x1, NULL },
    { "getRolloverIcon", NULL, "LRAREiPlatformIcon", 0x1, NULL },
    { "getRolloverPainter", NULL, "LRAREPaintBucket", 0x1, NULL },
    { "getSelectedIcon", NULL, "LRAREiPlatformIcon", 0x1, NULL },
    { "getSelectedPainter", NULL, "LRAREPaintBucket", 0x1, NULL },
    { "isBackgroundPaintEnabled", NULL, "Z", 0x1, NULL },
    { "isShared", NULL, "Z", 0x1, NULL },
    { "needsChangeHandler", NULL, "Z", 0x1, NULL },
    { "needsMouseHandler", NULL, "Z", 0x1, NULL },
    { "createWithRAREiWidget:withISPOTElement:", NULL, "LRAREPainterHolder", 0x9, NULL },
    { "createWithNSString:withBoolean:withBoolean:withBoolean:", NULL, "LRAREPainterHolder", 0x9, NULL },
    { "createFromIconStateMapWithJavaUtilMap:withRAREUTCharScanner:", NULL, "LRAREPainterHolder", 0x9, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "usesPresedAsOverlay_", NULL, 0x1, "Z" },
    { "disabledIcon_", NULL, 0x1, "LRAREiPlatformIcon" },
    { "disabledPainter_", NULL, 0x1, "LRAREPaintBucket" },
    { "disabledSelectedIcon_", NULL, 0x1, "LRAREiPlatformIcon" },
    { "disabledSelectedPainter_", NULL, 0x1, "LRAREPaintBucket" },
    { "foregroundColor_", NULL, 0x1, "LRAREUIColor" },
    { "normalIcon_", NULL, 0x1, "LRAREiPlatformIcon" },
    { "normalPainter_", NULL, 0x1, "LRAREPaintBucket" },
    { "pressedIcon_", NULL, 0x1, "LRAREiPlatformIcon" },
    { "pressedPainter_", NULL, 0x1, "LRAREPaintBucket" },
    { "pressedSelectedIcon_", NULL, 0x1, "LRAREiPlatformIcon" },
    { "rolloverIcon_", NULL, 0x1, "LRAREiPlatformIcon" },
    { "rolloverPainter_", NULL, 0x1, "LRAREPaintBucket" },
    { "selectedIcon_", NULL, 0x1, "LRAREiPlatformIcon" },
    { "selectedPainter_", NULL, 0x1, "LRAREPaintBucket" },
    { "componentPainter_", NULL, 0x0, "LRAREUIComponentPainter" },
  };
  static J2ObjcClassInfo _RAREPainterHolder = { "PainterHolder", "com.appnativa.rare.ui.painter", NULL, 0x1, 27, methods, 16, fields, 0, NULL};
  return &_RAREPainterHolder;
}

@end
