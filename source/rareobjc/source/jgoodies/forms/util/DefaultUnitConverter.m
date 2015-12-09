//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/jgoodies/forms/util/DefaultUnitConverter.java
//
//  Created by decoteaud on 12/8/15.
//

#include "IOSClass.h"
#include "com/appnativa/rare/ui/UIFont.h"
#include "com/appnativa/rare/ui/UIFontHelper.h"
#include "com/appnativa/rare/ui/UIFontMetrics.h"
#include "com/appnativa/rare/ui/iPlatformComponent.h"
#include "com/jgoodies/forms/util/AbstractUnitConverter.h"
#include "com/jgoodies/forms/util/DefaultUnitConverter.h"
#include "com/jgoodies/forms/util/FormUtils.h"
#include "java/beans/PropertyChangeListener.h"
#include "java/beans/PropertyChangeSupport.h"
#include "java/lang/IllegalArgumentException.h"
#include "java/lang/Math.h"
#include "java/lang/NullPointerException.h"
#include "java/util/logging/Logger.h"

@implementation RAREJGDefaultUnitConverter

static NSString * RAREJGDefaultUnitConverter_PROPERTY_AVERAGE_CHARACTER_WIDTH_TEST_STRING_ = @"averageCharacterWidthTestString";
static NSString * RAREJGDefaultUnitConverter_PROPERTY_DEFAULT_DIALOG_FONT_ = @"defaultDialogFont";
static JavaUtilLoggingLogger * RAREJGDefaultUnitConverter_LOGGER_;
static RAREJGDefaultUnitConverter * RAREJGDefaultUnitConverter_instance_;

+ (NSString *)PROPERTY_AVERAGE_CHARACTER_WIDTH_TEST_STRING {
  return RAREJGDefaultUnitConverter_PROPERTY_AVERAGE_CHARACTER_WIDTH_TEST_STRING_;
}

+ (NSString *)PROPERTY_DEFAULT_DIALOG_FONT {
  return RAREJGDefaultUnitConverter_PROPERTY_DEFAULT_DIALOG_FONT_;
}

+ (JavaUtilLoggingLogger *)LOGGER {
  return RAREJGDefaultUnitConverter_LOGGER_;
}

+ (RAREJGDefaultUnitConverter *)instance {
  return RAREJGDefaultUnitConverter_instance_;
}

+ (void)setInstance:(RAREJGDefaultUnitConverter *)instance {
  RAREJGDefaultUnitConverter_instance_ = instance;
}

- (id)init {
  if (self = [super init]) {
    averageCharWidthTestString_ = @"X";
    cachedGlobalDialogBaseUnits_ = nil;
    cachedDialogBaseUnits_ = nil;
    cachedFontMetrics_ = nil;
    cachedDefaultDialogFont_ = nil;
    changeSupport_ = [[JavaBeansPropertyChangeSupport alloc] initWithId:self];
  }
  return self;
}

+ (RAREJGDefaultUnitConverter *)getInstance {
  if (RAREJGDefaultUnitConverter_instance_ == nil) {
    RAREJGDefaultUnitConverter_instance_ = [[RAREJGDefaultUnitConverter alloc] init];
  }
  return RAREJGDefaultUnitConverter_instance_;
}

- (NSString *)getAverageCharacterWidthTestString {
  return averageCharWidthTestString_;
}

- (void)setAverageCharacterWidthTestStringWithNSString:(NSString *)newTestString {
  if (newTestString == nil) {
    @throw [[JavaLangNullPointerException alloc] initWithNSString:@"The test string must not be null."];
  }
  if ([((NSString *) nil_chk(newTestString)) sequenceLength] == 0) {
    @throw [[JavaLangIllegalArgumentException alloc] initWithNSString:@"The test string must not be empty."];
  }
  NSString *oldTestString = averageCharWidthTestString_;
  averageCharWidthTestString_ = newTestString;
  [((JavaBeansPropertyChangeSupport *) nil_chk(changeSupport_)) firePropertyChangeWithNSString:RAREJGDefaultUnitConverter_PROPERTY_AVERAGE_CHARACTER_WIDTH_TEST_STRING_ withId:oldTestString withId:newTestString];
}

- (RAREUIFont *)getDefaultDialogFont {
  return defaultDialogFont_ != nil ? defaultDialogFont_ : [self getCachedDefaultDialogFont];
}

- (void)setDefaultDialogFontWithRAREUIFont:(RAREUIFont *)newFont {
  RAREUIFont *oldFont = defaultDialogFont_;
  defaultDialogFont_ = newFont;
  [self clearCache];
  [((JavaBeansPropertyChangeSupport *) nil_chk(changeSupport_)) firePropertyChangeWithNSString:RAREJGDefaultUnitConverter_PROPERTY_DEFAULT_DIALOG_FONT_ withId:oldFont withId:newFont];
}

- (double)getDialogBaseUnitsXWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)component {
  return ((RAREJGDefaultUnitConverter_DialogBaseUnits *) nil_chk([self getDialogBaseUnitsWithRAREiPlatformComponent:component]))->x_;
}

- (double)getDialogBaseUnitsYWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)component {
  return ((RAREJGDefaultUnitConverter_DialogBaseUnits *) nil_chk([self getDialogBaseUnitsWithRAREiPlatformComponent:component]))->y_;
}

- (RAREJGDefaultUnitConverter_DialogBaseUnits *)getGlobalDialogBaseUnits {
  if (cachedGlobalDialogBaseUnits_ == nil) {
    cachedGlobalDialogBaseUnits_ = [self computeGlobalDialogBaseUnits];
  }
  return cachedGlobalDialogBaseUnits_;
}

- (RAREJGDefaultUnitConverter_DialogBaseUnits *)getDialogBaseUnitsWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c {
  [RAREJGFormUtils ensureValidCache];
  if (c == nil) {
    return [self getGlobalDialogBaseUnits];
  }
  RAREUIFont *f = [((id<RAREiPlatformComponent>) nil_chk(c)) getFont];
  if (f == nil) {
    f = [RAREUIFontHelper getDefaultFont];
  }
  RAREUIFontMetrics *fm = [RAREUIFontMetrics getMetricsWithRAREUIFont:f];
  if ([((RAREUIFontMetrics *) nil_chk(fm)) isEqual:cachedFontMetrics_]) {
    return cachedDialogBaseUnits_;
  }
  RAREJGDefaultUnitConverter_DialogBaseUnits *dialogBaseUnits = [self computeDialogBaseUnitsWithRAREUIFontMetrics:fm];
  cachedFontMetrics_ = fm;
  cachedDialogBaseUnits_ = dialogBaseUnits;
  return dialogBaseUnits;
}

- (RAREJGDefaultUnitConverter_DialogBaseUnits *)computeDialogBaseUnitsWithRAREUIFontMetrics:(RAREUIFontMetrics *)metrics {
  double averageCharWidth = [self computeAverageCharWidthWithRAREUIFontMetrics:metrics withNSString:averageCharWidthTestString_];
  int ascent = (int) [JavaLangMath ceilWithDouble:[((RAREUIFontMetrics *) nil_chk(metrics)) getAscent]];
  double height = ascent > 22 ? ascent : ascent + (23 - ascent) / 3;
  RAREJGDefaultUnitConverter_DialogBaseUnits *dialogBaseUnits = [[RAREJGDefaultUnitConverter_DialogBaseUnits alloc] initWithDouble:averageCharWidth withDouble:height];
  return dialogBaseUnits;
}

- (RAREJGDefaultUnitConverter_DialogBaseUnits *)computeGlobalDialogBaseUnits {
  [((JavaUtilLoggingLogger *) nil_chk(RAREJGDefaultUnitConverter_LOGGER_)) configWithNSString:@"Computing global dialog base units..."];
  RAREUIFont *dialogFont = [self getDefaultDialogFont];
  RAREJGDefaultUnitConverter_DialogBaseUnits *globalDialogBaseUnits = [self computeDialogBaseUnitsWithRAREUIFontMetrics:[RAREUIFontMetrics getMetricsWithRAREUIFont:dialogFont]];
  return globalDialogBaseUnits;
}

- (RAREUIFont *)getCachedDefaultDialogFont {
  [RAREJGFormUtils ensureValidCache];
  if (cachedDefaultDialogFont_ == nil) {
    cachedDefaultDialogFont_ = [self lookupDefaultDialogFont];
  }
  return cachedDefaultDialogFont_;
}

- (RAREUIFont *)lookupDefaultDialogFont {
  return [RAREUIFontHelper getDefaultFont];
}

- (void)clearCache {
  cachedGlobalDialogBaseUnits_ = nil;
  cachedFontMetrics_ = nil;
  cachedDefaultDialogFont_ = nil;
}

- (void)addPropertyChangeListenerWithJavaBeansPropertyChangeListener:(id<JavaBeansPropertyChangeListener>)listener {
  @synchronized(self) {
    {
      [((JavaBeansPropertyChangeSupport *) nil_chk(changeSupport_)) addPropertyChangeListenerWithJavaBeansPropertyChangeListener:listener];
    }
  }
}

- (void)removePropertyChangeListenerWithJavaBeansPropertyChangeListener:(id<JavaBeansPropertyChangeListener>)listener {
  @synchronized(self) {
    {
      [((JavaBeansPropertyChangeSupport *) nil_chk(changeSupport_)) removePropertyChangeListenerWithJavaBeansPropertyChangeListener:listener];
    }
  }
}

- (void)addPropertyChangeListenerWithNSString:(NSString *)propertyName
          withJavaBeansPropertyChangeListener:(id<JavaBeansPropertyChangeListener>)listener {
  @synchronized(self) {
    {
      [((JavaBeansPropertyChangeSupport *) nil_chk(changeSupport_)) addPropertyChangeListenerWithNSString:propertyName withJavaBeansPropertyChangeListener:listener];
    }
  }
}

- (void)removePropertyChangeListenerWithNSString:(NSString *)propertyName
             withJavaBeansPropertyChangeListener:(id<JavaBeansPropertyChangeListener>)listener {
  @synchronized(self) {
    {
      [((JavaBeansPropertyChangeSupport *) nil_chk(changeSupport_)) removePropertyChangeListenerWithNSString:propertyName withJavaBeansPropertyChangeListener:listener];
    }
  }
}

- (void)invalidateCaches {
  cachedGlobalDialogBaseUnits_ = nil;
  cachedDefaultDialogFont_ = nil;
}

+ (void)initialize {
  if (self == [RAREJGDefaultUnitConverter class]) {
    RAREJGDefaultUnitConverter_LOGGER_ = [JavaUtilLoggingLogger getLoggerWithNSString:[[IOSClass classWithClass:[RAREJGDefaultUnitConverter class]] getName]];
  }
}

- (void)copyAllFieldsTo:(RAREJGDefaultUnitConverter *)other {
  [super copyAllFieldsTo:other];
  other->averageCharWidthTestString_ = averageCharWidthTestString_;
  other->cachedDefaultDialogFont_ = cachedDefaultDialogFont_;
  other->cachedDialogBaseUnits_ = cachedDialogBaseUnits_;
  other->cachedFontMetrics_ = cachedFontMetrics_;
  other->cachedGlobalDialogBaseUnits_ = cachedGlobalDialogBaseUnits_;
  other->changeSupport_ = changeSupport_;
  other->defaultDialogFont_ = defaultDialogFont_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "init", NULL, NULL, 0x4, NULL },
    { "getInstance", NULL, "LRAREJGDefaultUnitConverter", 0x9, NULL },
    { "getAverageCharacterWidthTestString", NULL, "LNSString", 0x1, NULL },
    { "getDefaultDialogFont", NULL, "LRAREUIFont", 0x1, NULL },
    { "getDialogBaseUnitsXWithRAREiPlatformComponent:", NULL, "D", 0x4, NULL },
    { "getDialogBaseUnitsYWithRAREiPlatformComponent:", NULL, "D", 0x4, NULL },
    { "getGlobalDialogBaseUnits", NULL, "LRAREJGDefaultUnitConverter_DialogBaseUnits", 0x2, NULL },
    { "getDialogBaseUnitsWithRAREiPlatformComponent:", NULL, "LRAREJGDefaultUnitConverter_DialogBaseUnits", 0x2, NULL },
    { "computeDialogBaseUnitsWithRAREUIFontMetrics:", NULL, "LRAREJGDefaultUnitConverter_DialogBaseUnits", 0x2, NULL },
    { "computeGlobalDialogBaseUnits", NULL, "LRAREJGDefaultUnitConverter_DialogBaseUnits", 0x2, NULL },
    { "getCachedDefaultDialogFont", NULL, "LRAREUIFont", 0x2, NULL },
    { "lookupDefaultDialogFont", NULL, "LRAREUIFont", 0x2, NULL },
    { "clearCache", NULL, "V", 0x0, NULL },
    { "addPropertyChangeListenerWithJavaBeansPropertyChangeListener:", NULL, "V", 0x21, NULL },
    { "removePropertyChangeListenerWithJavaBeansPropertyChangeListener:", NULL, "V", 0x21, NULL },
    { "addPropertyChangeListenerWithNSString:withJavaBeansPropertyChangeListener:", NULL, "V", 0x21, NULL },
    { "removePropertyChangeListenerWithNSString:withJavaBeansPropertyChangeListener:", NULL, "V", 0x21, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "PROPERTY_AVERAGE_CHARACTER_WIDTH_TEST_STRING_", NULL, 0x19, "LNSString" },
    { "PROPERTY_DEFAULT_DIALOG_FONT_", NULL, 0x19, "LNSString" },
    { "LOGGER_", NULL, 0x1a, "LJavaUtilLoggingLogger" },
    { "instance_", NULL, 0xa, "LRAREJGDefaultUnitConverter" },
    { "changeSupport_", NULL, 0x12, "LJavaBeansPropertyChangeSupport" },
  };
  static J2ObjcClassInfo _RAREJGDefaultUnitConverter = { "DefaultUnitConverter", "com.jgoodies.forms.util", NULL, 0x1, 17, methods, 5, fields, 0, NULL};
  return &_RAREJGDefaultUnitConverter;
}

@end
@implementation RAREJGDefaultUnitConverter_DialogBaseUnits

- (id)initWithDouble:(double)dialogBaseUnitsX
          withDouble:(double)dialogBaseUnitsY {
  if (self = [super init]) {
    self->x_ = dialogBaseUnitsX;
    self->y_ = dialogBaseUnitsY;
  }
  return self;
}

- (NSString *)description {
  return [NSString stringWithFormat:@"DBU(x=%f; y=%f)", x_, y_];
}

- (void)copyAllFieldsTo:(RAREJGDefaultUnitConverter_DialogBaseUnits *)other {
  [super copyAllFieldsTo:other];
  other->x_ = x_;
  other->y_ = y_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "initWithDouble:withDouble:", NULL, NULL, 0x0, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "x_", NULL, 0x10, "D" },
    { "y_", NULL, 0x10, "D" },
  };
  static J2ObjcClassInfo _RAREJGDefaultUnitConverter_DialogBaseUnits = { "DialogBaseUnits", "com.jgoodies.forms.util", "DefaultUnitConverter", 0x1a, 1, methods, 2, fields, 0, NULL};
  return &_RAREJGDefaultUnitConverter_DialogBaseUnits;
}

@end
