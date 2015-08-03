//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/print/iPageSetup.java
//
//  Created by decoteaud on 7/29/15.
//

#include "IOSClass.h"
#include "com/appnativa/rare/ui/print/iPageSetup.h"
#include "java/lang/IllegalArgumentException.h"


@interface RAREiPageSetup : NSObject
@end

@implementation RAREiPageSetup

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "useStandardFooter", NULL, "V", 0x401, NULL },
    { "setDocumentNameWithNSString:", NULL, "V", 0x401, NULL },
    { "setDocumentTitleWithNSString:", NULL, "V", 0x401, NULL },
    { "setFooterWithNSString:", NULL, "V", 0x401, NULL },
    { "setFooterInclusionWithRAREiPageSetup_HeaderFooterInclusionEnum:", NULL, "V", 0x401, NULL },
    { "setHeaderWithNSString:", NULL, "V", 0x401, NULL },
    { "setHeaderInclusionWithRAREiPageSetup_HeaderFooterInclusionEnum:", NULL, "V", 0x401, NULL },
    { "setJobNameWithNSString:", NULL, "V", 0x401, NULL },
    { "getDocumentName", NULL, "LNSString", 0x401, NULL },
    { "getDocumentTitle", NULL, "LNSString", 0x401, NULL },
    { "getPageNumberMarker", NULL, "LNSString", 0x401, NULL },
    { "isPrintPreviewSupported", NULL, "Z", 0x401, NULL },
  };
  static J2ObjcClassInfo _RAREiPageSetup = { "iPageSetup", "com.appnativa.rare.ui.print", NULL, 0x201, 12, methods, 0, NULL, 0, NULL};
  return &_RAREiPageSetup;
}

@end

static RAREiPageSetup_DateTimeFormatEnum *RAREiPageSetup_DateTimeFormatEnum_SHORT;
static RAREiPageSetup_DateTimeFormatEnum *RAREiPageSetup_DateTimeFormatEnum_MEDIUM;
static RAREiPageSetup_DateTimeFormatEnum *RAREiPageSetup_DateTimeFormatEnum_LONG;
static RAREiPageSetup_DateTimeFormatEnum *RAREiPageSetup_DateTimeFormatEnum_FULL;
static RAREiPageSetup_DateTimeFormatEnum *RAREiPageSetup_DateTimeFormatEnum_DEFAULT;
IOSObjectArray *RAREiPageSetup_DateTimeFormatEnum_values;

@implementation RAREiPageSetup_DateTimeFormatEnum

+ (RAREiPageSetup_DateTimeFormatEnum *)SHORT {
  return RAREiPageSetup_DateTimeFormatEnum_SHORT;
}
+ (RAREiPageSetup_DateTimeFormatEnum *)MEDIUM {
  return RAREiPageSetup_DateTimeFormatEnum_MEDIUM;
}
+ (RAREiPageSetup_DateTimeFormatEnum *)LONG {
  return RAREiPageSetup_DateTimeFormatEnum_LONG;
}
+ (RAREiPageSetup_DateTimeFormatEnum *)FULL {
  return RAREiPageSetup_DateTimeFormatEnum_FULL;
}
+ (RAREiPageSetup_DateTimeFormatEnum *)DEFAULT {
  return RAREiPageSetup_DateTimeFormatEnum_DEFAULT;
}

- (id)copyWithZone:(NSZone *)zone {
  return self;
}

- (id)initWithNSString:(NSString *)__name withInt:(int)__ordinal {
  return [super initWithNSString:__name withInt:__ordinal];
}

+ (void)initialize {
  if (self == [RAREiPageSetup_DateTimeFormatEnum class]) {
    RAREiPageSetup_DateTimeFormatEnum_SHORT = [[RAREiPageSetup_DateTimeFormatEnum alloc] initWithNSString:@"SHORT" withInt:0];
    RAREiPageSetup_DateTimeFormatEnum_MEDIUM = [[RAREiPageSetup_DateTimeFormatEnum alloc] initWithNSString:@"MEDIUM" withInt:1];
    RAREiPageSetup_DateTimeFormatEnum_LONG = [[RAREiPageSetup_DateTimeFormatEnum alloc] initWithNSString:@"LONG" withInt:2];
    RAREiPageSetup_DateTimeFormatEnum_FULL = [[RAREiPageSetup_DateTimeFormatEnum alloc] initWithNSString:@"FULL" withInt:3];
    RAREiPageSetup_DateTimeFormatEnum_DEFAULT = [[RAREiPageSetup_DateTimeFormatEnum alloc] initWithNSString:@"DEFAULT" withInt:4];
    RAREiPageSetup_DateTimeFormatEnum_values = [[IOSObjectArray alloc] initWithObjects:(id[]){ RAREiPageSetup_DateTimeFormatEnum_SHORT, RAREiPageSetup_DateTimeFormatEnum_MEDIUM, RAREiPageSetup_DateTimeFormatEnum_LONG, RAREiPageSetup_DateTimeFormatEnum_FULL, RAREiPageSetup_DateTimeFormatEnum_DEFAULT, nil } count:5 type:[IOSClass classWithClass:[RAREiPageSetup_DateTimeFormatEnum class]]];
  }
}

+ (IOSObjectArray *)values {
  return [IOSObjectArray arrayWithArray:RAREiPageSetup_DateTimeFormatEnum_values];
}

+ (RAREiPageSetup_DateTimeFormatEnum *)valueOfWithNSString:(NSString *)name {
  for (int i = 0; i < [RAREiPageSetup_DateTimeFormatEnum_values count]; i++) {
    RAREiPageSetup_DateTimeFormatEnum *e = RAREiPageSetup_DateTimeFormatEnum_values->buffer_[i];
    if ([name isEqual:[e name]]) {
      return e;
    }
  }
  @throw [[JavaLangIllegalArgumentException alloc] initWithNSString:name];
  return nil;
}

+ (J2ObjcClassInfo *)__metadata {
  static const char *superclass_type_args[] = {"LRAREiPageSetup_DateTimeFormatEnum"};
  static J2ObjcClassInfo _RAREiPageSetup_DateTimeFormatEnum = { "DateTimeFormat", "com.appnativa.rare.ui.print", "iPageSetup", 0x4019, 0, NULL, 0, NULL, 1, superclass_type_args};
  return &_RAREiPageSetup_DateTimeFormatEnum;
}

@end

static RAREiPageSetup_HeaderFooterInclusionEnum *RAREiPageSetup_HeaderFooterInclusionEnum_FIRST_PAGE_ONLY;
static RAREiPageSetup_HeaderFooterInclusionEnum *RAREiPageSetup_HeaderFooterInclusionEnum_ALL_PAGES;
static RAREiPageSetup_HeaderFooterInclusionEnum *RAREiPageSetup_HeaderFooterInclusionEnum_SECOND_THROUGH_LAST_PAGE;
IOSObjectArray *RAREiPageSetup_HeaderFooterInclusionEnum_values;

@implementation RAREiPageSetup_HeaderFooterInclusionEnum

+ (RAREiPageSetup_HeaderFooterInclusionEnum *)FIRST_PAGE_ONLY {
  return RAREiPageSetup_HeaderFooterInclusionEnum_FIRST_PAGE_ONLY;
}
+ (RAREiPageSetup_HeaderFooterInclusionEnum *)ALL_PAGES {
  return RAREiPageSetup_HeaderFooterInclusionEnum_ALL_PAGES;
}
+ (RAREiPageSetup_HeaderFooterInclusionEnum *)SECOND_THROUGH_LAST_PAGE {
  return RAREiPageSetup_HeaderFooterInclusionEnum_SECOND_THROUGH_LAST_PAGE;
}

- (id)copyWithZone:(NSZone *)zone {
  return self;
}

- (id)initWithNSString:(NSString *)__name withInt:(int)__ordinal {
  return [super initWithNSString:__name withInt:__ordinal];
}

+ (void)initialize {
  if (self == [RAREiPageSetup_HeaderFooterInclusionEnum class]) {
    RAREiPageSetup_HeaderFooterInclusionEnum_FIRST_PAGE_ONLY = [[RAREiPageSetup_HeaderFooterInclusionEnum alloc] initWithNSString:@"FIRST_PAGE_ONLY" withInt:0];
    RAREiPageSetup_HeaderFooterInclusionEnum_ALL_PAGES = [[RAREiPageSetup_HeaderFooterInclusionEnum alloc] initWithNSString:@"ALL_PAGES" withInt:1];
    RAREiPageSetup_HeaderFooterInclusionEnum_SECOND_THROUGH_LAST_PAGE = [[RAREiPageSetup_HeaderFooterInclusionEnum alloc] initWithNSString:@"SECOND_THROUGH_LAST_PAGE" withInt:2];
    RAREiPageSetup_HeaderFooterInclusionEnum_values = [[IOSObjectArray alloc] initWithObjects:(id[]){ RAREiPageSetup_HeaderFooterInclusionEnum_FIRST_PAGE_ONLY, RAREiPageSetup_HeaderFooterInclusionEnum_ALL_PAGES, RAREiPageSetup_HeaderFooterInclusionEnum_SECOND_THROUGH_LAST_PAGE, nil } count:3 type:[IOSClass classWithClass:[RAREiPageSetup_HeaderFooterInclusionEnum class]]];
  }
}

+ (IOSObjectArray *)values {
  return [IOSObjectArray arrayWithArray:RAREiPageSetup_HeaderFooterInclusionEnum_values];
}

+ (RAREiPageSetup_HeaderFooterInclusionEnum *)valueOfWithNSString:(NSString *)name {
  for (int i = 0; i < [RAREiPageSetup_HeaderFooterInclusionEnum_values count]; i++) {
    RAREiPageSetup_HeaderFooterInclusionEnum *e = RAREiPageSetup_HeaderFooterInclusionEnum_values->buffer_[i];
    if ([name isEqual:[e name]]) {
      return e;
    }
  }
  @throw [[JavaLangIllegalArgumentException alloc] initWithNSString:name];
  return nil;
}

+ (J2ObjcClassInfo *)__metadata {
  static const char *superclass_type_args[] = {"LRAREiPageSetup_HeaderFooterInclusionEnum"};
  static J2ObjcClassInfo _RAREiPageSetup_HeaderFooterInclusionEnum = { "HeaderFooterInclusion", "com.appnativa.rare.ui.print", "iPageSetup", 0x4019, 0, NULL, 0, NULL, 1, superclass_type_args};
  return &_RAREiPageSetup_HeaderFooterInclusionEnum;
}

@end

static RAREiPageSetup_PrintModeEnum *RAREiPageSetup_PrintModeEnum_NORMAL;
static RAREiPageSetup_PrintModeEnum *RAREiPageSetup_PrintModeEnum_FIT_WIDTH;
static RAREiPageSetup_PrintModeEnum *RAREiPageSetup_PrintModeEnum_FIT_HEIGHT;
static RAREiPageSetup_PrintModeEnum *RAREiPageSetup_PrintModeEnum_FIT_PAGE;
IOSObjectArray *RAREiPageSetup_PrintModeEnum_values;

@implementation RAREiPageSetup_PrintModeEnum

+ (RAREiPageSetup_PrintModeEnum *)NORMAL {
  return RAREiPageSetup_PrintModeEnum_NORMAL;
}
+ (RAREiPageSetup_PrintModeEnum *)FIT_WIDTH {
  return RAREiPageSetup_PrintModeEnum_FIT_WIDTH;
}
+ (RAREiPageSetup_PrintModeEnum *)FIT_HEIGHT {
  return RAREiPageSetup_PrintModeEnum_FIT_HEIGHT;
}
+ (RAREiPageSetup_PrintModeEnum *)FIT_PAGE {
  return RAREiPageSetup_PrintModeEnum_FIT_PAGE;
}

- (id)copyWithZone:(NSZone *)zone {
  return self;
}

- (id)initWithNSString:(NSString *)__name withInt:(int)__ordinal {
  return [super initWithNSString:__name withInt:__ordinal];
}

+ (void)initialize {
  if (self == [RAREiPageSetup_PrintModeEnum class]) {
    RAREiPageSetup_PrintModeEnum_NORMAL = [[RAREiPageSetup_PrintModeEnum alloc] initWithNSString:@"NORMAL" withInt:0];
    RAREiPageSetup_PrintModeEnum_FIT_WIDTH = [[RAREiPageSetup_PrintModeEnum alloc] initWithNSString:@"FIT_WIDTH" withInt:1];
    RAREiPageSetup_PrintModeEnum_FIT_HEIGHT = [[RAREiPageSetup_PrintModeEnum alloc] initWithNSString:@"FIT_HEIGHT" withInt:2];
    RAREiPageSetup_PrintModeEnum_FIT_PAGE = [[RAREiPageSetup_PrintModeEnum alloc] initWithNSString:@"FIT_PAGE" withInt:3];
    RAREiPageSetup_PrintModeEnum_values = [[IOSObjectArray alloc] initWithObjects:(id[]){ RAREiPageSetup_PrintModeEnum_NORMAL, RAREiPageSetup_PrintModeEnum_FIT_WIDTH, RAREiPageSetup_PrintModeEnum_FIT_HEIGHT, RAREiPageSetup_PrintModeEnum_FIT_PAGE, nil } count:4 type:[IOSClass classWithClass:[RAREiPageSetup_PrintModeEnum class]]];
  }
}

+ (IOSObjectArray *)values {
  return [IOSObjectArray arrayWithArray:RAREiPageSetup_PrintModeEnum_values];
}

+ (RAREiPageSetup_PrintModeEnum *)valueOfWithNSString:(NSString *)name {
  for (int i = 0; i < [RAREiPageSetup_PrintModeEnum_values count]; i++) {
    RAREiPageSetup_PrintModeEnum *e = RAREiPageSetup_PrintModeEnum_values->buffer_[i];
    if ([name isEqual:[e name]]) {
      return e;
    }
  }
  @throw [[JavaLangIllegalArgumentException alloc] initWithNSString:name];
  return nil;
}

+ (J2ObjcClassInfo *)__metadata {
  static const char *superclass_type_args[] = {"LRAREiPageSetup_PrintModeEnum"};
  static J2ObjcClassInfo _RAREiPageSetup_PrintModeEnum = { "PrintMode", "com.appnativa.rare.ui.print", "iPageSetup", 0x4019, 0, NULL, 0, NULL, 1, superclass_type_args};
  return &_RAREiPageSetup_PrintModeEnum;
}

@end