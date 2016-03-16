//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple-table_and_tree/com/appnativa/rare/ui/table/TableListHandler.java
//
//  Created by decoteaud on 3/11/16.
//

#include "com/appnativa/rare/ui/iPlatformListDataModel.h"
#include "com/appnativa/rare/ui/table/TableComponent.h"
#include "com/appnativa/rare/ui/table/TableListHandler.h"
#include "com/appnativa/rare/ui/table/TableView.h"

@implementation RARETableListHandler

- (id)initWithRARETableComponent:(RARETableComponent *)table
  withRAREiPlatformListDataModel:(id<RAREiPlatformListDataModel>)model {
  if (self = [super initWithRAREaPlatformTableBasedView:[((RARETableComponent *) nil_chk(table)) getTableView] withRAREiPlatformListDataModel:model]) {
    tableComponent_ = table;
  }
  return self;
}

- (void)scrollToLeftEdge {
  [((RARETableComponent *) nil_chk(tableComponent_)) scrollToLeftEdge];
}

- (void)scrollToRightEdge {
  [((RARETableComponent *) nil_chk(tableComponent_)) scrollToRightEdge];
}

- (void)moveLeftRightWithBoolean:(BOOL)left
                     withBoolean:(BOOL)block {
  [((RARETableComponent *) nil_chk(tableComponent_)) moveLeftRightWithBoolean:left withBoolean:block];
}

- (BOOL)isTabular {
  return YES;
}

- (void)makeSelectionVisible {
  [((RARETableComponent *) nil_chk(tableComponent_)) makeSelectionVisible];
}

- (void)copyAllFieldsTo:(RARETableListHandler *)other {
  [super copyAllFieldsTo:other];
  other->tableComponent_ = tableComponent_;
}

- (NSUInteger)countByEnumeratingWithState:(NSFastEnumerationState *)state objects:(__unsafe_unretained id *)stackbuf count:(NSUInteger)len {
  return JreDefaultFastEnumeration(self, state, stackbuf, len);
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "isTabular", NULL, "Z", 0x1, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "tableComponent_", NULL, 0x4, "LRARETableComponent" },
  };
  static J2ObjcClassInfo _RARETableListHandler = { "TableListHandler", "com.appnativa.rare.ui.table", NULL, 0x1, 1, methods, 1, fields, 0, NULL};
  return &_RARETableListHandler;
}

@end
