//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/platform/apple/ui/ListBoxListHandler.java
//
//  Created by decoteaud on 12/8/15.
//

#include "com/appnativa/rare/platform/apple/ui/ListBoxListHandler.h"
#include "com/appnativa/rare/platform/apple/ui/aPlatformListHandler.h"
#include "com/appnativa/rare/platform/apple/ui/view/ParentView.h"
#include "com/appnativa/rare/platform/apple/ui/view/aPlatformTableBasedView.h"
#include "com/appnativa/rare/ui/iPlatformListDataModel.h"

@implementation RAREListBoxListHandler

- (id)initWithRAREaPlatformTableBasedView:(RAREaPlatformTableBasedView *)view
           withRAREiPlatformListDataModel:(id<RAREiPlatformListDataModel>)model {
  return [super initWithRAREiListView:view withRAREiPlatformListDataModel:model];
}

- (id)initWithRAREaPlatformTableBasedView:(RAREaPlatformTableBasedView *)view {
  return [super initWithRAREiListView:view];
}

- (void)selectAll {
  RAREaPlatformTableBasedView *view = (RAREaPlatformTableBasedView *) check_class_cast([self getView], [RAREaPlatformTableBasedView class]);
  [((RAREaPlatformTableBasedView *) nil_chk(view)) selectAll];
}

- (NSUInteger)countByEnumeratingWithState:(NSFastEnumerationState *)state objects:(__unsafe_unretained id *)stackbuf count:(NSUInteger)len {
  return JreDefaultFastEnumeration(self, state, stackbuf, len);
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcClassInfo _RAREListBoxListHandler = { "ListBoxListHandler", "com.appnativa.rare.platform.apple.ui", NULL, 0x1, 0, NULL, 0, NULL, 0, NULL};
  return &_RAREListBoxListHandler;
}

@end
