//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../spot/src/com/appnativa/spot/SPOTHelper.java
//
//  Created by decoteaud on 3/11/16.
//

#include "IOSClass.h"
#include "IOSObjectArray.h"
#include "com/appnativa/spot/SPOTException.h"
#include "com/appnativa/spot/SPOTHelper.h"
#include "com/appnativa/spot/iSPOTConstants.h"
#include "com/appnativa/spot/iSPOTElement.h"
#include "com/appnativa/util/Helper.h"
#include "com/appnativa/util/iPackageHelper.h"
#include "java/lang/Character.h"
#include "java/lang/ClassNotFoundException.h"
#include "java/lang/Exception.h"
#include "java/lang/NoSuchMethodException.h"
#include "java/lang/StringBuilder.h"
#include "java/lang/Throwable.h"
#include "java/lang/reflect/Field.h"
#include "java/lang/reflect/Method.h"
#include "java/util/HashMap.h"
#include "java/util/Map.h"

@implementation SPOTHelper

static IOSObjectArray * SPOTHelper__setRefType_;
static JavaUtilHashMap * SPOTHelper__toSpotName_;
static JavaUtilHashMap * SPOTHelper__fromSpotName_;
static id<RAREUTiPackageHelper> SPOTHelper_packageHelper_;

+ (IOSObjectArray *)_setRefType {
  return SPOTHelper__setRefType_;
}

+ (void)set_setRefType:(IOSObjectArray *)_setRefType {
  SPOTHelper__setRefType_ = _setRefType;
}

+ (JavaUtilHashMap *)_toSpotName {
  return SPOTHelper__toSpotName_;
}

+ (JavaUtilHashMap *)_fromSpotName {
  return SPOTHelper__fromSpotName_;
}

+ (id<RAREUTiPackageHelper>)packageHelper {
  return SPOTHelper_packageHelper_;
}

+ (void)setPackageHelper:(id<RAREUTiPackageHelper>)packageHelper {
  SPOTHelper_packageHelper_ = packageHelper;
}

- (id)init {
  return [super init];
}

+ (IOSClass *)createDefinedByClassWithISPOTElement:(id<iSPOTElement>)parent
                                      withNSString:(NSString *)definedBy {
  if (definedBy == nil) {
    return nil;
  }
  @try {
    NSString *s = definedBy;
    if ([((NSString *) nil_chk(definedBy)) indexOf:'.'] == -1) {
      JavaLangStringBuilder *sb = [[JavaLangStringBuilder alloc] init];
      (void) [sb appendWithNSString:[SPOTHelper getPackageNameWithIOSClass:[((id<iSPOTElement>) nil_chk(parent)) getClass]]];
      (void) [sb appendWithChar:'.'];
      if (![definedBy hasPrefix:@"SPOT"]) {
        (void) [sb appendWithNSString:@"SPOT"];
      }
      (void) [sb appendWithNSString:definedBy];
      s = [sb description];
    }
    else {
      s = [((NSString *) nil_chk(s)) replace:':' withChar:'.'];
    }
    return [SPOTHelper loadClassWithNSString:s];
  }
  @catch (JavaLangClassNotFoundException *e) {
    @throw [[SPOTException alloc] initWithJavaLangThrowable:e];
  }
}

+ (IOSClass *)loadClassWithNSString:(NSString *)name {
  if (SPOTHelper_packageHelper_ != nil) {
    return [SPOTHelper_packageHelper_ loadClassWithNSString:name];
  }
  return [IOSClass forName:name];
}

+ (IOSClass *)getFieldClassWithJavaLangReflectField:(JavaLangReflectField *)field {
  if (SPOTHelper_packageHelper_ == nil) {
    @try {
      SPOTHelper_packageHelper_ = (id<RAREUTiPackageHelper>) check_protocol_cast([((IOSClass *) nil_chk([IOSClass forName:@"com.appnativa.util.JavaPackageHelper"])) newInstance], @protocol(RAREUTiPackageHelper));
    }
    @catch (JavaLangException *e) {
      [((JavaLangException *) nil_chk(e)) printStackTrace];
    }
  }
  if (SPOTHelper_packageHelper_ != nil) {
    return [SPOTHelper_packageHelper_ getFieldClassWithId:field];
  }
  return nil;
}

+ (NSString *)getPackageNameWithIOSClass:(IOSClass *)cls {
  if (SPOTHelper_packageHelper_ == nil) {
    @try {
      SPOTHelper_packageHelper_ = (id<RAREUTiPackageHelper>) check_protocol_cast([((IOSClass *) nil_chk([IOSClass forName:@"com.appnativa.util.JavaPackageHelper"])) newInstance], @protocol(RAREUTiPackageHelper));
    }
    @catch (JavaLangException *e) {
      [((JavaLangException *) nil_chk(e)) printStackTrace];
    }
  }
  if (SPOTHelper_packageHelper_ != nil) {
    return [SPOTHelper_packageHelper_ getPackageNameWithIOSClass:cls];
  }
  return nil;
}

+ (NSString *)createDefinedByStringWithISPOTElement:(id<iSPOTElement>)parent
                                       withNSString:(NSString *)definedby {
  NSString *s = [SPOTHelper getPackageNameWithIOSClass:[((id<iSPOTElement>) nil_chk(parent)) getClass]];
  if ([((NSString *) nil_chk(definedby)) hasPrefix:s]) {
    s = [definedby substring:[((NSString *) nil_chk(s)) sequenceLength] + 1];
    if ([((NSString *) nil_chk(s)) hasPrefix:@"SPOT"]) {
      definedby = [s substring:[@"SPOT" sequenceLength]];
    }
  }
  return definedby;
}

+ (id<iSPOTElement>)elementFromNameWithJavaUtilMap:(id<JavaUtilMap>)refClassMap
                                  withISPOTElement:(id<iSPOTElement>)parent
                                      withNSString:(NSString *)name {
  @try {
    JavaLangStringBuilder *sb = [[JavaLangStringBuilder alloc] initWithNSString:@"get"];
    (void) [((JavaLangStringBuilder *) nil_chk([sb appendWithNSString:name])) appendWithNSString:@"Reference"];
    unichar c = [JavaLangCharacter toUpperCaseWithChar:[((NSString *) nil_chk(name)) charAtWithInt:0]];
    [sb setCharAtWithInt:3 withChar:c];
    NSString *s = [sb description];
    JavaLangReflectMethod *m = [[((id<iSPOTElement>) nil_chk(parent)) getClass] getMethod:s parameterTypes:(IOSObjectArray *) check_class_cast(nil, [IOSObjectArray class])];
    id<iSPOTElement> e = (id<iSPOTElement>) check_protocol_cast([((JavaLangReflectMethod *) nil_chk(m)) invokeWithId:parent withNSObjectArray:(IOSObjectArray *) check_class_cast(nil, [IOSObjectArray class])], @protocol(iSPOTElement));
    if (e != nil) {
      (void) [((id<JavaUtilMap>) nil_chk(refClassMap)) putWithId:name withId:[e getClass]];
      return e;
    }
  }
  @catch (JavaLangNoSuchMethodException *ex) {
  }
  @catch (JavaLangException *ex) {
    @throw [[SPOTException alloc] initWithJavaLangThrowable:[RAREUTHelper pealExceptionWithJavaLangThrowable:ex]];
  }
  IOSClass *cls = (IOSClass *) check_class_cast([((id<JavaUtilMap>) nil_chk(refClassMap)) getWithId:name], [IOSClass class]);
  if (cls == nil) {
    @try {
      JavaLangReflectField *f = [[((id<iSPOTElement>) nil_chk(parent)) getClass] getDeclaredField:name];
      cls = [SPOTHelper getFieldClassWithJavaLangReflectField:f];
      (void) [refClassMap putWithId:name withId:cls];
    }
    @catch (JavaLangException *ex) {
      return nil;
    }
  }
  @try {
    return (id<iSPOTElement>) check_protocol_cast([((IOSClass *) nil_chk(cls)) newInstance], @protocol(iSPOTElement));
  }
  @catch (JavaLangException *ex1) {
    return nil;
  }
}

+ (void)setReferenceVariableWithISPOTElement:(id<iSPOTElement>)parent
                                withNSString:(NSString *)name
                            withISPOTElement:(id<iSPOTElement>)element {
  @try {
    JavaLangStringBuilder *sb = [[JavaLangStringBuilder alloc] initWithNSString:@"set"];
    (void) [sb appendWithNSString:name];
    unichar c = [JavaLangCharacter toUpperCaseWithChar:[((NSString *) nil_chk(name)) charAtWithInt:0]];
    [sb setCharAtWithInt:3 withChar:c];
    NSString *s = [sb description];
    JavaLangReflectMethod *m = [[((id<iSPOTElement>) nil_chk(parent)) getClass] getMethod:s parameterTypes:SPOTHelper__setRefType_];
    (void) [((JavaLangReflectMethod *) nil_chk(m)) invokeWithId:parent withNSObjectArray:[IOSObjectArray arrayWithObjects:(id[]){ element } count:1 type:[IOSClass classWithClass:[NSObject class]]]];
  }
  @catch (JavaLangException *ex) {
    @throw [[SPOTException alloc] initWithJavaLangThrowable:ex];
  }
}

+ (NSString *)getRelativeClassNameWithISPOTElement:(id<iSPOTElement>)obj {
  if (obj == nil) {
    return nil;
  }
  NSString *s = (NSString *) check_class_cast([((JavaUtilHashMap *) nil_chk(SPOTHelper__fromSpotName_)) getWithId:[((id<iSPOTElement>) nil_chk(obj)) spot_getClassName]], [NSString class]);
  if (s != nil) {
    return s;
  }
  NSString *spot = [iSPOTConstants SPOT_PACKAGE_NAME];
  NSString *rel = nil;
  id<iSPOTElement> o = obj;
  while (o != nil) {
    s = [SPOTHelper getPackageNameWithIOSClass:[o getClass]];
    if (![((NSString *) nil_chk(s)) isEqual:spot]) {
      rel = [NSString stringWithFormat:@"%@.", s];
      break;
    }
    o = [o spot_getParent];
  }
  s = [obj spot_getClassName];
  if ((rel != nil) && [((NSString *) nil_chk(s)) hasPrefix:rel]) {
    s = [s substring:[rel sequenceLength]];
  }
  return s;
}

+ (NSString *)resolveClassNameWithISPOTElement:(id<iSPOTElement>)obj
                                  withNSString:(NSString *)type {
  if (type == nil) {
    return nil;
  }
  NSString *s = (NSString *) check_class_cast([((JavaUtilHashMap *) nil_chk(SPOTHelper__toSpotName_)) getWithId:type], [NSString class]);
  if (s != nil) {
    return s;
  }
  int n = [((NSString *) nil_chk(type)) indexOf:'.'];
  if (n == -1) {
    NSString *spot = [iSPOTConstants SPOT_PACKAGE_NAME];
    NSString *rel = nil;
    while (obj != nil) {
      s = [SPOTHelper getPackageNameWithIOSClass:[((id<iSPOTElement>) nil_chk(obj)) getClass]];
      if (![((NSString *) nil_chk(s)) isEqual:spot]) {
        rel = s;
        break;
      }
      obj = [obj spot_getParent];
    }
    if (rel == nil) {
      rel = spot;
    }
    type = [NSString stringWithFormat:@"%@.%@", rel, type];
  }
  else {
    type = [type replace:':' withChar:'.'];
  }
  return type;
}

+ (NSString *)getRelativeShortNameWithIOSClass:(IOSClass *)caller
                                  withIOSClass:(IOSClass *)cls {
  NSString *s = [((IOSClass *) nil_chk(cls)) getName];
  do {
    NSString *ss = (NSString *) check_class_cast([((JavaUtilHashMap *) nil_chk(SPOTHelper__fromSpotName_)) getWithId:s], [NSString class]);
    if (ss != nil) {
      s = ss;
      break;
    }
    if (![((NSString *) nil_chk([SPOTHelper getPackageNameWithIOSClass:[((IOSClass *) nil_chk(caller)) getClass]])) isEqual:[SPOTHelper getPackageNameWithIOSClass:cls]]) {
      break;
    }
    int i = [((NSString *) nil_chk(s)) lastIndexOf:'.'];
    if (i != -1) {
      s = [s substring:i + 1];
    }
  }
  while (NO);
  return s;
}

+ (id<RAREUTiPackageHelper>)getPackageHelper {
  return SPOTHelper_packageHelper_;
}

+ (void)setPackageHelperWithRAREUTiPackageHelper:(id<RAREUTiPackageHelper>)helper {
  SPOTHelper_packageHelper_ = helper;
}

+ (void)initialize {
  if (self == [SPOTHelper class]) {
    SPOTHelper__setRefType_ = [IOSObjectArray arrayWithObjects:(id[]){ [IOSClass classWithProtocol:@protocol(iSPOTElement)] } count:1 type:[IOSClass classWithClass:[IOSClass class]]];
    SPOTHelper__toSpotName_ = [[JavaUtilHashMap alloc] init];
    SPOTHelper__fromSpotName_ = [[JavaUtilHashMap alloc] init];
    {
      NSString *pkg = [NSString stringWithFormat:@"%@.", [iSPOTConstants SPOT_PACKAGE_NAME]];
      (void) [SPOTHelper__toSpotName_ putWithId:@"String" withId:[NSString stringWithFormat:@"%@SPOTPrintableString", pkg]];
      (void) [SPOTHelper__toSpotName_ putWithId:@"PrintableString" withId:[NSString stringWithFormat:@"%@SPOTPrintableString", pkg]];
      (void) [SPOTHelper__toSpotName_ putWithId:@"OctetString" withId:[NSString stringWithFormat:@"%@SPOTOctetString", pkg]];
      (void) [SPOTHelper__toSpotName_ putWithId:@"Integer" withId:[NSString stringWithFormat:@"%@SPOTInteger", pkg]];
      (void) [SPOTHelper__toSpotName_ putWithId:@"Real" withId:[NSString stringWithFormat:@"%@SPOTReal", pkg]];
      (void) [SPOTHelper__toSpotName_ putWithId:@"Set" withId:[NSString stringWithFormat:@"%@SPOTSet", pkg]];
      (void) [SPOTHelper__toSpotName_ putWithId:@"Sequence" withId:[NSString stringWithFormat:@"%@SPOTSequence", pkg]];
      (void) [SPOTHelper__toSpotName_ putWithId:@"Any" withId:[NSString stringWithFormat:@"%@SPOTAny", pkg]];
      (void) [SPOTHelper__toSpotName_ putWithId:@"DateTime" withId:[NSString stringWithFormat:@"%@SPOTDateTime", pkg]];
      (void) [SPOTHelper__toSpotName_ putWithId:@"Date" withId:[NSString stringWithFormat:@"%@SPOTDate", pkg]];
      (void) [SPOTHelper__toSpotName_ putWithId:@"Time" withId:[NSString stringWithFormat:@"%@SPOTTime", pkg]];
      (void) [SPOTHelper__toSpotName_ putWithId:@"Boolean" withId:[NSString stringWithFormat:@"%@SPOTBoolean", pkg]];
      (void) [SPOTHelper__toSpotName_ putWithId:@"ByteString" withId:[NSString stringWithFormat:@"%@SPOTByteString", pkg]];
      (void) [SPOTHelper__toSpotName_ putWithId:@"Enumerated" withId:[NSString stringWithFormat:@"%@SPOTEnumerated", pkg]];
      (void) [SPOTHelper__fromSpotName_ putWithId:[NSString stringWithFormat:@"%@SPOTPrintableString", pkg] withId:@"String"];
      (void) [SPOTHelper__fromSpotName_ putWithId:[NSString stringWithFormat:@"%@SPOTOctetString", pkg] withId:@"OctetString"];
      (void) [SPOTHelper__fromSpotName_ putWithId:[NSString stringWithFormat:@"%@SPOTInteger", pkg] withId:@"Integer"];
      (void) [SPOTHelper__fromSpotName_ putWithId:[NSString stringWithFormat:@"%@SPOTReal", pkg] withId:@"Real"];
      (void) [SPOTHelper__fromSpotName_ putWithId:[NSString stringWithFormat:@"%@SPOTSet", pkg] withId:@"Set"];
      (void) [SPOTHelper__fromSpotName_ putWithId:[NSString stringWithFormat:@"%@SPOTSequence", pkg] withId:@"Sequence"];
      (void) [SPOTHelper__fromSpotName_ putWithId:[NSString stringWithFormat:@"%@SPOTAny", pkg] withId:@"Any"];
      (void) [SPOTHelper__fromSpotName_ putWithId:[NSString stringWithFormat:@"%@SPOTDateTime", pkg] withId:@"DateTime"];
      (void) [SPOTHelper__fromSpotName_ putWithId:[NSString stringWithFormat:@"%@SPOTDate", pkg] withId:@"Date"];
      (void) [SPOTHelper__fromSpotName_ putWithId:[NSString stringWithFormat:@"%@SPOTTime", pkg] withId:@"Time"];
      (void) [SPOTHelper__fromSpotName_ putWithId:[NSString stringWithFormat:@"%@SPOTBoolean", pkg] withId:@"Boolean"];
      (void) [SPOTHelper__fromSpotName_ putWithId:[NSString stringWithFormat:@"%@SPOTByteString", pkg] withId:@"ByteString"];
      (void) [SPOTHelper__fromSpotName_ putWithId:[NSString stringWithFormat:@"%@SPOTEnumerated", pkg] withId:@"Enumerated"];
    }
  }
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "init", NULL, NULL, 0x2, NULL },
    { "createDefinedByClassWithISPOTElement:withNSString:", NULL, "LIOSClass", 0x9, NULL },
    { "loadClassWithNSString:", NULL, "LIOSClass", 0x9, "JavaLangClassNotFoundException" },
    { "getFieldClassWithJavaLangReflectField:", NULL, "LIOSClass", 0x9, NULL },
    { "getPackageNameWithIOSClass:", NULL, "LNSString", 0x9, NULL },
    { "createDefinedByStringWithISPOTElement:withNSString:", NULL, "LNSString", 0x9, NULL },
    { "elementFromNameWithJavaUtilMap:withISPOTElement:withNSString:", NULL, "LiSPOTElement", 0x9, NULL },
    { "getRelativeClassNameWithISPOTElement:", NULL, "LNSString", 0x9, NULL },
    { "resolveClassNameWithISPOTElement:withNSString:", NULL, "LNSString", 0x9, NULL },
    { "getRelativeShortNameWithIOSClass:withIOSClass:", NULL, "LNSString", 0x9, NULL },
    { "getPackageHelper", NULL, "LRAREUTiPackageHelper", 0x9, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "_setRefType_", NULL, 0xa, "LIOSObjectArray" },
    { "_toSpotName_", NULL, 0x1a, "LJavaUtilHashMap" },
    { "_fromSpotName_", NULL, 0x1a, "LJavaUtilHashMap" },
    { "packageHelper_", NULL, 0xa, "LRAREUTiPackageHelper" },
  };
  static J2ObjcClassInfo _SPOTHelper = { "SPOTHelper", "com.appnativa.spot", NULL, 0x1, 11, methods, 4, fields, 0, NULL};
  return &_SPOTHelper;
}

@end
