//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/ui/ListComponent.java
//
//  Created by decoteaud on 3/11/16.
//

#include "com/appnativa/rare/platform/apple/ui/view/ListView.h"
#include "com/appnativa/rare/ui/ColorUtils.h"
#include "com/appnativa/rare/ui/Component.h"
#include "com/appnativa/rare/ui/ListComponent.h"
#include "com/appnativa/rare/ui/UIColor.h"

@implementation RAREListComponent

- (id)initWithRAREListView:(RAREListView *)lv {
  if (self = [super initWithRAREParentView:lv]) {
    [self setForegroundWithRAREUIColor:[RAREColorUtils getListForeground]];
    [self setBackgroundWithRAREUIColor:[RAREColorUtils getListBackground]];
  }
  return self;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcClassInfo _RAREListComponent = { "ListComponent", "com.appnativa.rare.ui", NULL, 0x1, 0, NULL, 0, NULL, 0, NULL};
  return &_RAREListComponent;
}

@end
