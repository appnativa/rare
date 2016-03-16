//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/iListDataModel.java
//
//  Created by decoteaud on 3/11/16.
//

#include "IOSIntArray.h"
#include "IOSObjectArray.h"
#include "com/appnativa/rare/ui/Column.h"
#include "com/appnativa/rare/ui/RenderableDataItem.h"
#include "com/appnativa/rare/ui/event/iDataModelListener.h"
#include "com/appnativa/rare/ui/iListDataModel.h"
#include "com/appnativa/rare/widget/iWidget.h"
#include "com/appnativa/util/CharacterIndex.h"
#include "java/util/Collection.h"
#include "java/util/List.h"


@interface RAREiListDataModel : NSObject
@end

@implementation RAREiListDataModel

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "addDataModelListenerWithRAREiDataModelListener:", NULL, "V", 0x401, NULL },
    { "dispose", NULL, "V", 0x401, NULL },
    { "editModeChangeAllMarksWithBoolean:", NULL, "V", 0x401, NULL },
    { "editModeChangeMarkWithInt:withBoolean:", NULL, "V", 0x401, NULL },
    { "editModeClearMarks", NULL, "V", 0x401, NULL },
    { "editModeGetMarkCount", NULL, "I", 0x401, NULL },
    { "editModeGetMarkedIndices", NULL, "LIOSIntArray", 0x401, NULL },
    { "editModeGetMarkedItems", NULL, "LIOSObjectArray", 0x401, NULL },
    { "editModeIsItemMarkedWithInt:", NULL, "Z", 0x401, NULL },
    { "editModeToggleMarkWithInt:", NULL, "V", 0x401, NULL },
    { "refreshItems", NULL, "V", 0x401, NULL },
    { "removeDataModelListenerWithRAREiDataModelListener:", NULL, "V", 0x401, NULL },
    { "removeRowsWithIntArray:", NULL, "V", 0x401, NULL },
    { "removeRowsWithInt:withInt:", NULL, "V", 0x401, NULL },
    { "rowChangedWithInt:", NULL, "V", 0x401, NULL },
    { "rowChangedWithRARERenderableDataItem:", NULL, "V", 0x401, NULL },
    { "rowsChangedWithIntArray:", NULL, "V", 0x481, NULL },
    { "rowsChangedWithInt:withInt:", NULL, "V", 0x401, NULL },
    { "rowsDeletedWithInt:withInt:withJavaUtilList:", NULL, "V", 0x401, NULL },
    { "rowsInsertedWithInt:withInt:", NULL, "V", 0x401, NULL },
    { "setAllWithJavaUtilCollection:", NULL, "Z", 0x401, NULL },
    { "setColumnDescriptionWithRAREColumn:", NULL, "V", 0x401, NULL },
    { "setEventsEnabledWithBoolean:", NULL, "V", 0x401, NULL },
    { "setItemsWithJavaUtilCollection:", NULL, "V", 0x401, NULL },
    { "setUseIndexForFilteringWithBoolean:", NULL, "V", 0x401, NULL },
    { "setWidgetWithRAREiWidget:", NULL, "V", 0x401, NULL },
    { "getColumnDescription", NULL, "LRAREColumn", 0x401, NULL },
    { "getFilteringIndex", NULL, "LRAREUTCharacterIndex", 0x401, NULL },
    { "isEventsEnabled", NULL, "Z", 0x401, NULL },
  };
  static J2ObjcClassInfo _RAREiListDataModel = { "iListDataModel", "com.appnativa.rare.ui", NULL, 0x201, 29, methods, 0, NULL, 0, NULL};
  return &_RAREiListDataModel;
}

@end
