//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../util/src/com/appnativa/util/iStructuredNode.java
//
//  Created by decoteaud on 12/8/15.
//

#include "com/appnativa/util/iStructuredNode.h"
#include "java/util/Map.h"


@interface RAREUTiStructuredNode : NSObject
@end

@implementation RAREUTiStructuredNode

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "getName", NULL, "LNSString", 0x401, NULL },
    { "getValueAsString", NULL, "LNSString", 0x401, NULL },
    { "getValue", NULL, "LNSObject", 0x401, NULL },
    { "hasChildren", NULL, "Z", 0x401, NULL },
    { "hasAttributes", NULL, "Z", 0x401, NULL },
    { "getAttributes", NULL, "LJavaUtilMap", 0x401, NULL },
    { "getChildCount", NULL, "I", 0x401, NULL },
    { "getChildWithInt:", NULL, "LRAREUTiStructuredNode", 0x401, NULL },
    { "isPreformattedData", NULL, "Z", 0x401, NULL },
    { "getPreformattedTag", NULL, "LNSString", 0x401, NULL },
    { "getComment", NULL, "LNSString", 0x401, NULL },
    { "getLinkedData", NULL, "LNSObject", 0x401, NULL },
    { "copyAttributesWithJavaUtilMap:", NULL, "V", 0x401, NULL },
    { "hasAttributeWithNSString:", NULL, "Z", 0x401, NULL },
    { "getAttributeWithNSString:", NULL, "LNSObject", 0x401, NULL },
    { "getFirstSignificantChild", NULL, "LRAREUTiStructuredNode", 0x401, NULL },
    { "getNextSibling", NULL, "LRAREUTiStructuredNode", 0x401, NULL },
    { "getChildWithNSString:", NULL, "LRAREUTiStructuredNode", 0x401, NULL },
  };
  static J2ObjcClassInfo _RAREUTiStructuredNode = { "iStructuredNode", "com.appnativa.util", NULL, 0x201, 18, methods, 0, NULL, 0, NULL};
  return &_RAREUTiStructuredNode;
}

@end
