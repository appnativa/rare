//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/dnd/TransferFlavor.java
//
//  Created by decoteaud on 7/29/15.
//

#include "IOSClass.h"
#include "IOSObjectArray.h"
#include "com/appnativa/rare/Platform.h"
#include "com/appnativa/rare/iConstants.h"
#include "com/appnativa/rare/iPlatform.h"
#include "com/appnativa/rare/ui/dnd/TransferFlavor.h"
#include "com/appnativa/rare/ui/dnd/UnsupportedFlavorException.h"
#include "com/appnativa/rare/ui/dnd/iFlavorCreator.h"
#include "com/appnativa/rare/ui/dnd/iTransferable.h"
#include "java/io/IOException.h"
#include "java/io/Reader.h"
#include "java/io/StringReader.h"
#include "java/util/ArrayList.h"
#include "java/util/Collections.h"
#include "java/util/List.h"
#include "java/util/Set.h"

@implementation RARETransferFlavor

static NSString * RARETransferFlavor_CUSTOM_DATAFLAVOR_MIME_TYPE_ = @"application/x-com-appnativa-rare-custom-item";
static NSString * RARETransferFlavor_WIDGET_DATAFLAVOR_MIME_TYPE_ = @"application/x-com-appnativa-rare-widget";
static RARETransferFlavor * RARETransferFlavor_fileListFlavor_;
static RARETransferFlavor * RARETransferFlavor_htmlFlavor_;
static RARETransferFlavor * RARETransferFlavor_imageFlavor_;
static RARETransferFlavor * RARETransferFlavor_itemFlavor_;
static RARETransferFlavor * RARETransferFlavor_stringFlavor_;
static RARETransferFlavor * RARETransferFlavor_urlFlavor_;
static RARETransferFlavor * RARETransferFlavor_urlListFlavor_;
static RARETransferFlavor * RARETransferFlavor_widgetFlavor_;
static id<RAREiFlavorCreator> RARETransferFlavor_flavorCreator_;

+ (NSString *)CUSTOM_DATAFLAVOR_MIME_TYPE {
  return RARETransferFlavor_CUSTOM_DATAFLAVOR_MIME_TYPE_;
}

+ (NSString *)WIDGET_DATAFLAVOR_MIME_TYPE {
  return RARETransferFlavor_WIDGET_DATAFLAVOR_MIME_TYPE_;
}

+ (RARETransferFlavor *)fileListFlavor {
  return RARETransferFlavor_fileListFlavor_;
}

+ (RARETransferFlavor *)htmlFlavor {
  return RARETransferFlavor_htmlFlavor_;
}

+ (RARETransferFlavor *)imageFlavor {
  return RARETransferFlavor_imageFlavor_;
}

+ (RARETransferFlavor *)itemFlavor {
  return RARETransferFlavor_itemFlavor_;
}

+ (RARETransferFlavor *)stringFlavor {
  return RARETransferFlavor_stringFlavor_;
}

+ (RARETransferFlavor *)urlFlavor {
  return RARETransferFlavor_urlFlavor_;
}

+ (RARETransferFlavor *)urlListFlavor {
  return RARETransferFlavor_urlListFlavor_;
}

+ (RARETransferFlavor *)widgetFlavor {
  return RARETransferFlavor_widgetFlavor_;
}

+ (id<RAREiFlavorCreator>)flavorCreator {
  return RARETransferFlavor_flavorCreator_;
}

- (id)initWithNSString:(NSString *)name
                withId:(id)platfromFlavor
       withJavaUtilSet:(id<JavaUtilSet>)types {
  if (self = [super init]) {
    mimeTypes_ = [[JavaUtilArrayList alloc] init];
    self->name_ = name;
    [self->mimeTypes_ addAllWithJavaUtilCollection:types];
    self->platfromFlavor_ = platfromFlavor;
  }
  return self;
}

- (id)initWithNSString:(NSString *)name
                withId:(id)platfromFlavor
     withNSStringArray:(IOSObjectArray *)types {
  if (self = [super init]) {
    mimeTypes_ = [[JavaUtilArrayList alloc] init];
    self->name_ = name;
    {
      IOSObjectArray *a__ = types;
      id const *b__ = ((IOSObjectArray *) nil_chk(a__))->buffer_;
      id const *e__ = b__ + a__->size_;
      while (b__ < e__) {
        NSString *s = (*b__++);
        [mimeTypes_ addWithId:s];
      }
    }
    self->platfromFlavor_ = platfromFlavor;
  }
  return self;
}

+ (void)addDataFlavorWithNSString:(NSString *)name
                 withJavaUtilList:(id<JavaUtilList>)list
                      withBoolean:(BOOL)export_ {
  do {
    if (name == nil) {
      break;
    }
    if ([((NSString *) nil_chk(name)) indexOf:'/'] != -1) {
      [((id<JavaUtilList>) nil_chk(list)) addWithId:[((id<RAREiFlavorCreator>) nil_chk(RARETransferFlavor_flavorCreator_)) createFlavorWithNSString:name withNSStringArray:[IOSObjectArray arrayWithObjects:(id[]){ name } count:1 type:[IOSClass classWithClass:[NSString class]]]]];
      break;
    }
    if ([name isEqual:[RAREiConstants DATAFLAVOR_TEXT]]) {
      [((id<JavaUtilList>) nil_chk(list)) addWithId:RARETransferFlavor_stringFlavor_];
      break;
    }
    if ([name isEqual:[RAREiConstants DATAFLAVOR_HTML]]) {
      [((id<JavaUtilList>) nil_chk(list)) addWithId:RARETransferFlavor_htmlFlavor_];
      break;
    }
    if ([name isEqual:[RAREiConstants DATAFLAVOR_IMAGE]]) {
      [((id<JavaUtilList>) nil_chk(list)) addWithId:RARETransferFlavor_imageFlavor_];
      break;
    }
    if ([name isEqual:[RAREiConstants DATAFLAVOR_DATA_ITEM]]) {
      [((id<JavaUtilList>) nil_chk(list)) addWithId:RARETransferFlavor_itemFlavor_];
      break;
    }
    if ([name isEqual:[RAREiConstants DATAFLAVOR_URL]]) {
      [((id<JavaUtilList>) nil_chk(list)) addWithId:RARETransferFlavor_urlFlavor_];
      break;
    }
    [((id<JavaUtilList>) nil_chk(list)) addWithId:[((id<RAREiFlavorCreator>) nil_chk(RARETransferFlavor_flavorCreator_)) createFlavorWithNSString:name withNSStringArray:[IOSObjectArray arrayWithObjects:(id[]){ RARETransferFlavor_CUSTOM_DATAFLAVOR_MIME_TYPE_ } count:1 type:[IOSClass classWithClass:[NSString class]]]]];
  }
  while (NO);
}

+ (IOSObjectArray *)createSupportedDataFlavorsWithNSStringArray:(IOSObjectArray *)flavorNames
                                                    withBoolean:(BOOL)export_ {
  int len = (flavorNames == nil) ? 0 : (int) [flavorNames count];
  if (len == 0) {
    return nil;
  }
  JavaUtilArrayList *list = [[JavaUtilArrayList alloc] initWithInt:len];
  int i;
  if (flavorNames != nil) {
    for (i = 0; i < (int) [flavorNames count]; i++) {
      [RARETransferFlavor addDataFlavorWithNSString:IOSObjectArray_Get(flavorNames, i) withJavaUtilList:list withBoolean:export_];
    }
  }
  return [list toArrayWithNSObjectArray:[IOSObjectArray arrayWithLength:[list size] type:[IOSClass classWithClass:[RARETransferFlavor class]]]];
}

- (BOOL)isEqual:(id)o {
  if (o == self) {
    return YES;
  }
  if (!([o isKindOfClass:[RARETransferFlavor class]])) {
    return NO;
  }
  RARETransferFlavor *df = (RARETransferFlavor *) check_class_cast(o, [RARETransferFlavor class]);
  return [((JavaUtilArrayList *) nil_chk(((RARETransferFlavor *) nil_chk(df))->mimeTypes_)) isEqual:mimeTypes_];
}

+ (RARETransferFlavor *)fromPlatformFlavorWithId:(id)flavor {
  if ([((RARETransferFlavor *) nil_chk(RARETransferFlavor_stringFlavor_)) hasPlatformFlavorWithId:flavor]) {
    return RARETransferFlavor_stringFlavor_;
  }
  if ([((RARETransferFlavor *) nil_chk(RARETransferFlavor_itemFlavor_)) hasPlatformFlavorWithId:flavor]) {
    return RARETransferFlavor_itemFlavor_;
  }
  if ([((RARETransferFlavor *) nil_chk(RARETransferFlavor_widgetFlavor_)) hasPlatformFlavorWithId:flavor]) {
    return RARETransferFlavor_widgetFlavor_;
  }
  if ([((RARETransferFlavor *) nil_chk(RARETransferFlavor_urlFlavor_)) hasPlatformFlavorWithId:flavor]) {
    return RARETransferFlavor_urlFlavor_;
  }
  if ([((RARETransferFlavor *) nil_chk(RARETransferFlavor_urlListFlavor_)) hasPlatformFlavorWithId:flavor]) {
    return RARETransferFlavor_urlListFlavor_;
  }
  if ([((RARETransferFlavor *) nil_chk(RARETransferFlavor_htmlFlavor_)) hasPlatformFlavorWithId:flavor]) {
    return RARETransferFlavor_htmlFlavor_;
  }
  if ([((RARETransferFlavor *) nil_chk(RARETransferFlavor_imageFlavor_)) hasPlatformFlavorWithId:flavor]) {
    return RARETransferFlavor_imageFlavor_;
  }
  return [((id<RAREiFlavorCreator>) nil_chk(RARETransferFlavor_flavorCreator_)) createFlavorWithId:flavor];
}

+ (RARETransferFlavor *)getDataFlavorWithNSStringArray:(IOSObjectArray *)flavorNames
                                          withNSString:(NSString *)name {
  NSString *flavor = nil;
  int len = (flavorNames == nil) ? 0 : (int) [flavorNames count];
  for (int i = 0; i < len; i++) {
    if ([((NSString *) nil_chk(name)) isEqual:IOSObjectArray_Get(nil_chk(flavorNames), i)]) {
      flavor = name;
      break;
    }
  }
  if (flavor == nil) {
    return nil;
  }
  if ([((NSString *) nil_chk(flavor)) isEqual:[RAREiConstants DATAFLAVOR_TEXT]]) {
    return RARETransferFlavor_stringFlavor_;
  }
  if ([flavor isEqual:[RAREiConstants DATAFLAVOR_IMAGE]]) {
    return RARETransferFlavor_imageFlavor_;
  }
  if ([flavor contains:@"/"]) {
    return [((id<RAREiFlavorCreator>) nil_chk(RARETransferFlavor_flavorCreator_)) createFlavorWithNSString:flavor withNSStringArray:[IOSObjectArray arrayWithObjects:(id[]){ flavor } count:1 type:[IOSClass classWithClass:[NSString class]]]];
  }
  return [((id<RAREiFlavorCreator>) nil_chk(RARETransferFlavor_flavorCreator_)) createFlavorWithNSString:name withNSStringArray:[IOSObjectArray arrayWithObjects:(id[]){ RARETransferFlavor_CUSTOM_DATAFLAVOR_MIME_TYPE_ } count:1 type:[IOSClass classWithClass:[NSString class]]]];
}

+ (RARETransferFlavor *)getDataFlavorWithRARETransferFlavorArray:(IOSObjectArray *)supportedFlavors
                                                    withNSString:(NSString *)flavor {
  BOOL url = NO;
  BOOL mimeCheck = NO;
  NSString *urllist = nil;
  if (supportedFlavors == nil) {
    return nil;
  }
  if ([((NSString *) nil_chk([RAREiConstants DATAFLAVOR_TEXT])) isEqual:flavor]) {
    return [RARETransferFlavor getStringDataFlavorWithRARETransferFlavorArray:supportedFlavors];
  }
  if ([((NSString *) nil_chk([RAREiConstants DATAFLAVOR_HTML])) isEqual:flavor]) {
    return RARETransferFlavor_htmlFlavor_;
  }
  if ([((NSString *) nil_chk(flavor)) isEqual:[RAREiConstants DATAFLAVOR_IMAGE]]) {
    flavor = [((RARETransferFlavor *) nil_chk(RARETransferFlavor_imageFlavor_)) getName];
  }
  else if ([flavor isEqual:[RAREiConstants DATAFLAVOR_URL]]) {
    url = YES;
    flavor = [((RARETransferFlavor *) nil_chk(RARETransferFlavor_urlFlavor_)) getName];
    urllist = [((RARETransferFlavor *) nil_chk(RARETransferFlavor_urlListFlavor_)) getName];
  }
  int len = (int) [((IOSObjectArray *) nil_chk(supportedFlavors)) count];
  if ([((NSString *) nil_chk(flavor)) indexOf:'/'] != -1) {
    mimeCheck = YES;
  }
  for (int i = 0; i < len; i++) {
    if (url) {
      NSString *name = [((RARETransferFlavor *) IOSObjectArray_Get(supportedFlavors, i)) getName];
      if ([((NSString *) nil_chk(name)) isEqual:flavor] || [name isEqual:urllist]) {
        return IOSObjectArray_Get(supportedFlavors, i);
      }
    }
    else {
      if (mimeCheck) {
        if ([((RARETransferFlavor *) IOSObjectArray_Get(supportedFlavors, i)) isMimeTypeSupportedWithNSString:flavor]) {
          return IOSObjectArray_Get(supportedFlavors, i);
        }
      }
      else {
        if ([((NSString *) nil_chk([((RARETransferFlavor *) IOSObjectArray_Get(supportedFlavors, i)) getName])) isEqual:flavor]) {
          return IOSObjectArray_Get(supportedFlavors, i);
        }
      }
    }
  }
  return nil;
}

+ (NSString *)getDataFlavorNameWithNSStringArray:(IOSObjectArray *)flavorNames
                          withRARETransferFlavor:(RARETransferFlavor *)flavor {
  if (flavorNames == nil) {
    return nil;
  }
  int len = (int) [((IOSObjectArray *) nil_chk(flavorNames)) count];
  NSString *name;
  if ([((RARETransferFlavor *) nil_chk(flavor)) isMimeTypeSupportedWithNSString:RARETransferFlavor_CUSTOM_DATAFLAVOR_MIME_TYPE_]) {
    for (int i = 0; i < len; i++) {
      if ([flavor isMimeTypeSupportedWithNSString:RARETransferFlavor_CUSTOM_DATAFLAVOR_MIME_TYPE_]) {
        name = IOSObjectArray_Get(flavorNames, i);
        if ((name != nil) && [name isEqual:[flavor getName]]) {
          return name;
        }
      }
    }
    return nil;
  }
  else {
    for (int i = 0; i < len; i++) {
      name = IOSObjectArray_Get(flavorNames, i);
      if ((name != nil) && [name isEqual:[RAREiConstants DATAFLAVOR_TEXT]]) {
        return [flavor isEqual:RARETransferFlavor_stringFlavor_] ? name : nil;
      }
      else if ((name != nil) && [name isEqual:[RAREiConstants DATAFLAVOR_IMAGE]]) {
        return [flavor isEqual:RARETransferFlavor_imageFlavor_] ? name : nil;
      }
      else if ((name != nil) && [name isEqual:[RAREiConstants DATAFLAVOR_DATA_ITEM]]) {
        return name;
      }
      else if ([((NSString *) nil_chk([flavor getName])) isEqual:name]) {
        return name;
      }
    }
  }
  return nil;
}

- (id<JavaUtilList>)getMimeTypes {
  return [JavaUtilCollections unmodifiableListWithJavaUtilList:mimeTypes_];
}

- (NSString *)getName {
  return name_;
}

- (id)getPlatformFlavor {
  return self->platfromFlavor_;
}

- (JavaIoReader *)getReaderForTextWithRAREiTransferable:(id<RAREiTransferable>)transferable {
  NSString *s = (NSString *) check_class_cast([((id<RAREiTransferable>) nil_chk(transferable)) getTransferDataWithRARETransferFlavor:RARETransferFlavor_stringFlavor_], [NSString class]);
  return [[JavaIoStringReader alloc] initWithNSString:s];
}

+ (RARETransferFlavor *)getStringDataFlavorWithRARETransferFlavorArray:(IOSObjectArray *)flavor {
  int len = (int) [((IOSObjectArray *) nil_chk(flavor)) count];
  for (int i = 0; i < len; i++) {
    if (IOSObjectArray_Get(flavor, i) == RARETransferFlavor_stringFlavor_) {
      return IOSObjectArray_Get(flavor, i);
    }
  }
  return nil;
}

- (BOOL)hasPlatformFlavorWithId:(id)flavor {
  if ([[IOSObjectArray iosClassWithType:[IOSClass classWithClass:[NSObject class]]] isInstance:platfromFlavor_]) {
    IOSObjectArray *a = (IOSObjectArray *) check_class_cast(platfromFlavor_, [IOSObjectArray class]);
    {
      IOSObjectArray *a__ = a;
      id const *b__ = ((IOSObjectArray *) nil_chk(a__))->buffer_;
      id const *e__ = b__ + a__->size_;
      while (b__ < e__) {
        id o = (*b__++);
        if ((o == flavor) || [nil_chk(o) isEqual:flavor]) {
          return YES;
        }
      }
    }
  }
  else if ((platfromFlavor_ == flavor) || [nil_chk(flavor) isEqual:platfromFlavor_]) {
    return YES;
  }
  return NO;
}

+ (BOOL)isDataFlavorSupportedWithNSStringArray:(IOSObjectArray *)flavorNames
                        withRARETransferFlavor:(RARETransferFlavor *)flavor {
  if (flavorNames == nil) {
    return NO;
  }
  int len = (int) [((IOSObjectArray *) nil_chk(flavorNames)) count];
  NSString *name;
  if ([((RARETransferFlavor *) nil_chk(flavor)) isMimeTypeSupportedWithNSString:RARETransferFlavor_CUSTOM_DATAFLAVOR_MIME_TYPE_]) {
    for (int i = 0; i < len; i++) {
      name = IOSObjectArray_Get(flavorNames, i);
      if ((name != nil) && [name isEqual:[flavor getName]]) {
        return YES;
      }
    }
    return NO;
  }
  else {
    for (int i = 0; i < len; i++) {
      name = IOSObjectArray_Get(flavorNames, i);
      if (name == nil) {
        continue;
      }
      if ([((NSString *) nil_chk(name)) isEqual:[RAREiConstants DATAFLAVOR_TEXT]]) {
        return [flavor isEqual:RARETransferFlavor_stringFlavor_];
      }
      else if ([name isEqual:[RAREiConstants DATAFLAVOR_IMAGE]]) {
        return [flavor isEqual:RARETransferFlavor_imageFlavor_];
      }
      else if ([name isEqual:[RAREiConstants DATAFLAVOR_HTML]]) {
        return [flavor isEqual:RARETransferFlavor_htmlFlavor_];
      }
      else if ([name isEqual:[RAREiConstants DATAFLAVOR_DATA_ITEM]]) {
        return YES;
      }
      else if ([name isEqual:[RAREiConstants DATAFLAVOR_URL]]) {
        return [flavor isEqual:RARETransferFlavor_urlFlavor_] || [flavor isEqual:RARETransferFlavor_urlListFlavor_];
      }
      else if ([name isEqual:[RAREiConstants DATAFLAVOR_FILE]]) {
        return [flavor isEqual:RARETransferFlavor_fileListFlavor_];
      }
      else {
        if ([((NSString *) nil_chk([flavor getName])) isEqual:name] || [flavor isMimeTypeSupportedWithNSString:name]) {
          return YES;
        }
      }
    }
  }
  return NO;
}

+ (BOOL)isDataFlavorSupportedWithRARETransferFlavorArray:(IOSObjectArray *)supportedFlavors
                                            withNSString:(NSString *)flavor {
  return [RARETransferFlavor getDataFlavorWithRARETransferFlavorArray:supportedFlavors withNSString:flavor] != nil;
}

- (BOOL)isStringFlavor {
  return self == RARETransferFlavor_stringFlavor_;
}

- (BOOL)isWidgetFlavor {
  return self == RARETransferFlavor_widgetFlavor_;
}

- (BOOL)isItemFlavor {
  return self == RARETransferFlavor_itemFlavor_;
}

- (BOOL)isMimeTypeSupportedWithNSString:(NSString *)type {
  return [((JavaUtilArrayList *) nil_chk(self->mimeTypes_)) containsWithId:type];
}

+ (void)initialize {
  if (self == [RARETransferFlavor class]) {
    {
      RARETransferFlavor_flavorCreator_ = [((id<RAREiPlatform>) nil_chk([RAREPlatform getPlatform])) getTransferFlavorCreator];
      RARETransferFlavor_itemFlavor_ = [((id<RAREiFlavorCreator>) nil_chk(RARETransferFlavor_flavorCreator_)) createFlavorWithNSString:[RAREiConstants DATAFLAVOR_DATA_ITEM] withNSStringArray:[IOSObjectArray arrayWithObjects:(id[]){ @"application/x-com-appnativa-rare-data-item" } count:1 type:[IOSClass classWithClass:[NSString class]]]];
      RARETransferFlavor_widgetFlavor_ = [RARETransferFlavor_flavorCreator_ createFlavorWithNSString:[RAREiConstants DATAFLAVOR_WIDGET] withNSStringArray:[IOSObjectArray arrayWithObjects:(id[]){ RARETransferFlavor_WIDGET_DATAFLAVOR_MIME_TYPE_ } count:1 type:[IOSClass classWithClass:[NSString class]]]];
      RARETransferFlavor_stringFlavor_ = [RARETransferFlavor_flavorCreator_ getStringFlavor];
      RARETransferFlavor_imageFlavor_ = [RARETransferFlavor_flavorCreator_ getImageFlavor];
      RARETransferFlavor_htmlFlavor_ = [RARETransferFlavor_flavorCreator_ getHTMLFlavor];
      RARETransferFlavor_fileListFlavor_ = [RARETransferFlavor_flavorCreator_ getFileListFlavor];
      RARETransferFlavor_urlFlavor_ = [RARETransferFlavor_flavorCreator_ getURLFlavor];
      RARETransferFlavor_urlListFlavor_ = [RARETransferFlavor_flavorCreator_ getURLListFlavor];
    }
  }
}

- (void)copyAllFieldsTo:(RARETransferFlavor *)other {
  [super copyAllFieldsTo:other];
  other->mimeTypes_ = mimeTypes_;
  other->name_ = name_;
  other->platfromFlavor_ = platfromFlavor_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "initWithNSString:withId:withNSStringArray:", NULL, NULL, 0x81, NULL },
    { "createSupportedDataFlavorsWithNSStringArray:withBoolean:", NULL, "LIOSObjectArray", 0x9, NULL },
    { "fromPlatformFlavorWithId:", NULL, "LRARETransferFlavor", 0x9, NULL },
    { "getDataFlavorWithNSStringArray:withNSString:", NULL, "LRARETransferFlavor", 0x9, NULL },
    { "getDataFlavorWithRARETransferFlavorArray:withNSString:", NULL, "LRARETransferFlavor", 0x9, NULL },
    { "getDataFlavorNameWithNSStringArray:withRARETransferFlavor:", NULL, "LNSString", 0x9, NULL },
    { "getMimeTypes", NULL, "LJavaUtilList", 0x1, NULL },
    { "getName", NULL, "LNSString", 0x1, NULL },
    { "getPlatformFlavor", NULL, "LNSObject", 0x1, NULL },
    { "getReaderForTextWithRAREiTransferable:", NULL, "LJavaIoReader", 0x1, "RAREUnsupportedFlavorException;JavaIoIOException" },
    { "getStringDataFlavorWithRARETransferFlavorArray:", NULL, "LRARETransferFlavor", 0x9, NULL },
    { "hasPlatformFlavorWithId:", NULL, "Z", 0x1, NULL },
    { "isDataFlavorSupportedWithNSStringArray:withRARETransferFlavor:", NULL, "Z", 0x9, NULL },
    { "isDataFlavorSupportedWithRARETransferFlavorArray:withNSString:", NULL, "Z", 0x9, NULL },
    { "isStringFlavor", NULL, "Z", 0x1, NULL },
    { "isWidgetFlavor", NULL, "Z", 0x1, NULL },
    { "isItemFlavor", NULL, "Z", 0x1, NULL },
    { "isMimeTypeSupportedWithNSString:", NULL, "Z", 0x1, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "CUSTOM_DATAFLAVOR_MIME_TYPE_", NULL, 0x19, "LNSString" },
    { "WIDGET_DATAFLAVOR_MIME_TYPE_", NULL, 0x19, "LNSString" },
    { "fileListFlavor_", NULL, 0x19, "LRARETransferFlavor" },
    { "htmlFlavor_", NULL, 0x19, "LRARETransferFlavor" },
    { "imageFlavor_", NULL, 0x19, "LRARETransferFlavor" },
    { "itemFlavor_", NULL, 0x19, "LRARETransferFlavor" },
    { "stringFlavor_", NULL, 0x19, "LRARETransferFlavor" },
    { "urlFlavor_", NULL, 0x19, "LRARETransferFlavor" },
    { "urlListFlavor_", NULL, 0x19, "LRARETransferFlavor" },
    { "widgetFlavor_", NULL, 0x19, "LRARETransferFlavor" },
    { "flavorCreator_", NULL, 0x1a, "LRAREiFlavorCreator" },
  };
  static J2ObjcClassInfo _RARETransferFlavor = { "TransferFlavor", "com.appnativa.rare.ui.dnd", NULL, 0x1, 18, methods, 11, fields, 0, NULL};
  return &_RARETransferFlavor;
}

@end