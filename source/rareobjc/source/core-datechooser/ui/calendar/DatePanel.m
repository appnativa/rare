//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core-datechooser/com/appnativa/rare/ui/calendar/DatePanel.java
//
//  Created by decoteaud on 3/11/16.
//

#include "com/appnativa/rare/Platform.h"
#include "com/appnativa/rare/ui/ColorUtils.h"
#include "com/appnativa/rare/ui/Component.h"
#include "com/appnativa/rare/ui/UIColor.h"
#include "com/appnativa/rare/ui/UIProperties.h"
#include "com/appnativa/rare/ui/aBorderPanel.h"
#include "com/appnativa/rare/ui/aComponent.h"
#include "com/appnativa/rare/ui/calendar/DatePanel.h"
#include "com/appnativa/rare/ui/calendar/aDateViewManager.h"
#include "com/appnativa/rare/ui/calendar/iDateViewManager.h"
#include "com/appnativa/rare/ui/iPlatformComponent.h"
#include "com/appnativa/rare/widget/iWidget.h"

@implementation RAREDatePanel

- (id)initWithRAREiWidget:(id<RAREiWidget>)context
 withRAREaDateViewManager:(RAREaDateViewManager *)dvm {
  if (self = [super initWithRAREiWidget:context]) {
    RAREUIColor *c = [((RAREUIProperties *) nil_chk([RAREPlatform getUIDefaults])) getColorWithNSString:@"Rare.DateChooser.background"];
    if (c == nil) {
      c = [RAREColorUtils getBackground];
    }
    [self setBackgroundWithRAREUIColor:c];
    dateViewManager_ = dvm;
    self->widget_ = context;
  }
  return self;
}

- (void)dispose {
  [super dispose];
  dateViewManager_ = nil;
}

- (void)setContent {
  [self setCenterViewWithRAREiPlatformComponent:[((RAREaDateViewManager *) nil_chk(dateViewManager_)) getDatePickerComponent]];
  [self setBottomViewWithRAREiPlatformComponent:[dateViewManager_ getButtonPanelWithRAREiWidget:[self getWidget]]];
}

- (id<RAREiDateViewManager>)getDateViewManager {
  return dateViewManager_;
}

- (void)copyAllFieldsTo:(RAREDatePanel *)other {
  [super copyAllFieldsTo:other];
  other->dateViewManager_ = dateViewManager_;
  other->viewsSet_ = viewsSet_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "getDateViewManager", NULL, "LRAREiDateViewManager", 0x1, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "dateViewManager_", NULL, 0x4, "LRAREaDateViewManager" },
    { "viewsSet_", NULL, 0x4, "Z" },
  };
  static J2ObjcClassInfo _RAREDatePanel = { "DatePanel", "com.appnativa.rare.ui.calendar", NULL, 0x1, 1, methods, 2, fields, 0, NULL};
  return &_RAREDatePanel;
}

@end
