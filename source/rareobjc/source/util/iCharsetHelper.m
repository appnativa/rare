//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../util/src/com/appnativa/util/iCharsetHelper.java
//
//  Created by decoteaud on 3/11/16.
//

#include "IOSByteArray.h"
#include "IOSCharArray.h"
#include "IOSClass.h"
#include "com/appnativa/util/ByteArray.h"
#include "com/appnativa/util/CharArray.h"
#include "com/appnativa/util/FormatException.h"
#include "com/appnativa/util/iCharsetHelper.h"


@interface RAREUTiCharsetHelper : NSObject
@end

@implementation RAREUTiCharsetHelper

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "copy__", NULL, "LRAREUTiCharsetHelper", 0x401, NULL },
    { "getByteLengthWithCharArray:withInt:withInt:", NULL, "I", 0x401, NULL },
    { "getBytesWithNSString:", NULL, "LIOSByteArray", 0x401, NULL },
    { "getBytesWithCharArray:withInt:withInt:", NULL, "LIOSByteArray", 0x401, NULL },
    { "getBytesWithCharArray:withInt:withInt:withByteArray:withInt:", NULL, "I", 0x401, NULL },
    { "getBytesWithCharArray:withInt:withInt:withRAREUTByteArray:withInt:", NULL, "I", 0x401, NULL },
    { "getCharLengthWithByteArray:withInt:withInt:", NULL, "I", 0x401, "RAREUTFormatException" },
    { "getCharsWithByteArray:withInt:withInt:withCharArray:withInt:", NULL, "I", 0x401, "RAREUTFormatException" },
    { "getCharsWithByteArray:withInt:withInt:withRAREUTCharArray:withInt:", NULL, "I", 0x401, "RAREUTFormatException" },
    { "getEncoding", NULL, "LNSString", 0x401, NULL },
    { "getStringWithByteArray:", NULL, "LNSString", 0x401, "RAREUTFormatException" },
    { "getStringWithByteArray:withInt:withInt:", NULL, "LNSString", 0x401, "RAREUTFormatException" },
    { "isByteLenghthSupported", NULL, "Z", 0x401, NULL },
    { "isCharLengthSupported", NULL, "Z", 0x401, NULL },
  };
  static J2ObjcClassInfo _RAREUTiCharsetHelper = { "iCharsetHelper", "com.appnativa.util", NULL, 0x201, 14, methods, 0, NULL, 0, NULL};
  return &_RAREUTiCharsetHelper;
}

@end
