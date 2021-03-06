//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple-table_and_tree/com/appnativa/rare/ui/table/MultipleListHandler.java
//
//  Created by decoteaud on 3/11/16.
//

#include "com/appnativa/rare/platform/apple/ui/ListSynchronizer.h"
#include "com/appnativa/rare/platform/apple/ui/view/View.h"
#include "com/appnativa/rare/platform/apple/ui/view/aPlatformTableBasedView.h"
#include "com/appnativa/rare/ui/iListView.h"
#include "com/appnativa/rare/ui/iPlatformComponent.h"
#include "com/appnativa/rare/ui/iPlatformListHandler.h"
#include "com/appnativa/rare/ui/table/MultiTableTableComponent.h"
#include "com/appnativa/rare/ui/table/MultipleListHandler.h"

@implementation RAREMultipleListHandler

- (id)initWithRAREMultiTableTableComponent:(RAREMultiTableTableComponent *)mtc
              withRAREiPlatformListHandler:(id<RAREiPlatformListHandler>)main
              withRAREiPlatformListHandler:(id<RAREiPlatformListHandler>)handler1
              withRAREiPlatformListHandler:(id<RAREiPlatformListHandler>)handler2 {
  if (self = [super initWithRAREMultiTableTableComponent:mtc withRAREiListView:(id<RAREiListView>) check_protocol_cast([((id<RAREiPlatformComponent>) nil_chk([((id<RAREiPlatformListHandler>) nil_chk(main)) getListComponent])) getView], @protocol(RAREiListView)) withRAREiPlatformListHandler:main withRAREiPlatformListHandler:handler1 withRAREiPlatformListHandler:handler2]) {
    listSynchronizer_ = [[RAREListSynchronizer alloc] initWithRAREaPlatformTableBasedView:(RAREaPlatformTableBasedView *) check_class_cast([((id<RAREiPlatformComponent>) nil_chk([main getListComponent])) getView], [RAREaPlatformTableBasedView class]) withBoolean:YES];
    if (handler1 != nil) {
      [listSynchronizer_ addListViewWithRAREaPlatformTableBasedView:(RAREaPlatformTableBasedView *) check_class_cast([((id<RAREiPlatformComponent>) nil_chk([handler1 getListComponent])) getView], [RAREaPlatformTableBasedView class])];
    }
    if (handler2 != nil) {
      [listSynchronizer_ addListViewWithRAREaPlatformTableBasedView:(RAREaPlatformTableBasedView *) check_class_cast([((id<RAREiPlatformComponent>) nil_chk([handler2 getListComponent])) getView], [RAREaPlatformTableBasedView class])];
    }
  }
  return self;
}

- (void)dispose {
  [super dispose];
  [((RAREListSynchronizer *) nil_chk(listSynchronizer_)) dispose];
}

- (void)copyAllFieldsTo:(RAREMultipleListHandler *)other {
  [super copyAllFieldsTo:other];
  other->listSynchronizer_ = listSynchronizer_;
}

- (NSUInteger)countByEnumeratingWithState:(NSFastEnumerationState *)state objects:(__unsafe_unretained id *)stackbuf count:(NSUInteger)len {
  return JreDefaultFastEnumeration(self, state, stackbuf, len);
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcFieldInfo fields[] = {
    { "listSynchronizer_", NULL, 0x0, "LRAREListSynchronizer" },
  };
  static J2ObjcClassInfo _RAREMultipleListHandler = { "MultipleListHandler", "com.appnativa.rare.ui.table", NULL, 0x1, 0, NULL, 1, fields, 0, NULL};
  return &_RAREMultipleListHandler;
}

@end
