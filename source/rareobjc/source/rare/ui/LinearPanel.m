//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/ui/LinearPanel.java
//
//  Created by decoteaud on 3/11/16.
//

#include "IOSObjectArray.h"
#include "com/appnativa/jgoodies/forms/layout/CellConstraints.h"
#include "com/appnativa/jgoodies/forms/layout/FormLayout.h"
#include "com/appnativa/rare/platform/apple/ui/view/FormsView.h"
#include "com/appnativa/rare/ui/Component.h"
#include "com/appnativa/rare/ui/LinearPanel.h"
#include "com/appnativa/rare/ui/aLinearPanel.h"
#include "com/appnativa/rare/ui/iPlatformComponent.h"
#include "com/appnativa/rare/widget/iWidget.h"

@implementation RARELinearPanel

- (id)initWithBoolean:(BOOL)horizontal {
  return [self initRARELinearPanelWithRAREiWidget:nil withBoolean:horizontal withNSString:nil withNSString:nil];
}

- (id)initWithRAREiWidget:(id<RAREiWidget>)context
              withBoolean:(BOOL)horizontal {
  return [self initRARELinearPanelWithRAREiWidget:context withBoolean:horizontal withNSString:nil withNSString:nil];
}

- (id)initRARELinearPanelWithRAREiWidget:(id<RAREiWidget>)context
                             withBoolean:(BOOL)horizontal
                            withNSString:(NSString *)rspec
                            withNSString:(NSString *)cspec {
  if (self = [super initWithBoolean:horizontal]) {
    [self setSpecsWithBoolean:horizontal withNSString:rspec withNSString:cspec];
    [self setViewWithRAREView:[[RAREFormsView alloc] initWithRAREFormLayout:[[RAREFormLayout alloc] initWithNSString:colSpec_ withNSString:rowSpec_]]];
  }
  return self;
}

- (id)initWithRAREiWidget:(id<RAREiWidget>)context
              withBoolean:(BOOL)horizontal
             withNSString:(NSString *)rspec
             withNSString:(NSString *)cspec {
  return [self initRARELinearPanelWithRAREiWidget:context withBoolean:horizontal withNSString:rspec withNSString:cspec];
}

- (void)setCellPaintersWithRAREiPlatformPainterArray:(IOSObjectArray *)painters {
  [((RAREFormsView *) check_class_cast(view_, [RAREFormsView class])) setCellPaintersWithRAREiPlatformPainterArray:painters];
}

- (RARECellConstraints *)getCellConstraintsWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)component {
  return [((RAREFormsView *) check_class_cast(view_, [RAREFormsView class])) getCellConstraintsWithRAREiPlatformComponent:component];
}

- (RAREFormLayout *)getFormLayout {
  return [((RAREFormsView *) check_class_cast(view_, [RAREFormsView class])) getLayout];
}

- (void)updateFormLayout {
  [((RAREFormsView *) check_class_cast(view_, [RAREFormsView class])) setFormLayoutWithRAREFormLayout:[[RAREFormLayout alloc] initWithNSString:colSpec_ withNSString:rowSpec_]];
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "getCellConstraintsWithRAREiPlatformComponent:", NULL, "LRARECellConstraints", 0x1, NULL },
    { "getFormLayout", NULL, "LRAREFormLayout", 0x1, NULL },
    { "updateFormLayout", NULL, "V", 0x4, NULL },
  };
  static J2ObjcClassInfo _RARELinearPanel = { "LinearPanel", "com.appnativa.rare.ui", NULL, 0x1, 3, methods, 0, NULL, 0, NULL};
  return &_RARELinearPanel;
}

@end
