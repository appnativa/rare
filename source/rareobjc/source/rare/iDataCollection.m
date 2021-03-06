//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/iDataCollection.java
//
//  Created by decoteaud on 3/11/16.
//

#include "IOSClass.h"
#include "com/appnativa/rare/iDataCollection.h"
#include "com/appnativa/rare/widget/iWidget.h"
#include "java/io/IOException.h"
#include "java/lang/CharSequence.h"
#include "java/util/Collection.h"
#include "java/util/List.h"


@interface RAREiDataCollection : NSObject
@end

@implementation RAREiDataCollection

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "clearCollection", NULL, "V", 0x401, NULL },
    { "disposeCollection", NULL, "V", 0x401, NULL },
    { "setCollectionDataWithJavaUtilList:", NULL, "V", 0x401, NULL },
    { "refreshWithRAREiWidget:", NULL, "V", 0x401, "JavaIoIOException" },
    { "setAttributeWithRAREiWidget:withNSString:withId:", NULL, "V", 0x401, NULL },
    { "getAttributeWithRAREiWidget:withNSString:", NULL, "LNSObject", 0x401, NULL },
    { "getCollectionStringMimeTypeWithRAREiWidget:", NULL, "LNSString", 0x401, NULL },
    { "getCollectionWithRAREiWidget:", NULL, "LJavaUtilCollection", 0x401, NULL },
    { "getCollectionAsStringWithRAREiWidget:", NULL, "LNSString", 0x401, NULL },
    { "getItemDataWithRAREiWidget:withBoolean:", NULL, "LJavaUtilCollection", 0x401, NULL },
    { "getCollectionName", NULL, "LNSString", 0x401, NULL },
    { "getEmptyCollectionText", NULL, "LJavaLangCharSequence", 0x401, NULL },
    { "setEmptyCollectionTextWithJavaLangCharSequence:", NULL, "V", 0x401, NULL },
    { "getSubCollectionDataWithRAREiWidget:withNSString:withBoolean:", NULL, "LJavaUtilCollection", 0x401, NULL },
    { "getSubItemDataWithRAREiWidget:withNSString:withBoolean:", NULL, "LJavaUtilCollection", 0x401, NULL },
    { "getSubItemDataWithRAREiWidget:withInt:withInt:withInt:withInt:withBoolean:", NULL, "LJavaUtilCollection", 0x401, NULL },
    { "toListWithJavaUtilCollection:", NULL, "LJavaUtilList", 0x401, NULL },
    { "isLoaded", NULL, "Z", 0x401, NULL },
    { "size", NULL, "I", 0x401, NULL },
    { "isEmpty", NULL, "Z", 0x401, NULL },
    { "setCollectionNameWithNSString:", NULL, "V", 0x401, NULL },
    { "isRefreshOnURLConnection", NULL, "Z", 0x401, NULL },
    { "setRefreshOnURLConnectionWithBoolean:", NULL, "V", 0x401, NULL },
  };
  static J2ObjcClassInfo _RAREiDataCollection = { "iDataCollection", "com.appnativa.rare", NULL, 0x201, 23, methods, 0, NULL, 0, NULL};
  return &_RAREiDataCollection;
}

@end
