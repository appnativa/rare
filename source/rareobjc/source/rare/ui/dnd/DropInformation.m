//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/dnd/DropInformation.java
//
//  Created by decoteaud on 9/15/15.
//

#include "IOSClass.h"
#include "IOSObjectArray.h"
#include "com/appnativa/rare/Platform.h"
#include "com/appnativa/rare/platform/PlatformHelper.h"
#include "com/appnativa/rare/ui/UIPoint.h"
#include "com/appnativa/rare/ui/dnd/DnDConstants.h"
#include "com/appnativa/rare/ui/dnd/DropInformation.h"
#include "com/appnativa/rare/ui/dnd/TransferFlavor.h"
#include "com/appnativa/rare/ui/dnd/UnsupportedFlavorException.h"
#include "com/appnativa/rare/ui/dnd/iTransferable.h"
#include "com/appnativa/rare/widget/iWidget.h"
#include "com/appnativa/util/FilterableList.h"
#include "com/appnativa/util/Streams.h"
#include "java/io/File.h"
#include "java/io/IOException.h"
#include "java/io/Reader.h"
#include "java/lang/Exception.h"
#include "java/net/URL.h"
#include "java/util/Collections.h"
#include "java/util/List.h"
#include "java/util/StringTokenizer.h"

@implementation RAREDropInformation

- (id)initWithRAREiWidget:(id<RAREiWidget>)source {
  if (self = [super init]) {
    dropAction_ = RAREDnDConstants_ACTION_NONE;
    dropColumn_ = -1;
    dropIndex_ = -1;
    sourceWidget_ = source;
  }
  return self;
}

- (void)clear {
  transferable_ = nil;
  insertMode_ = NO;
  dropColumn_ = -1;
  dropIndex_ = -1;
  dropPoint_ = nil;
  dropAction_ = RAREDnDConstants_ACTION_NONE;
  userData_ = nil;
  targetWidget_ = nil;
}

- (void)setDropActionWithInt:(int)dropAction {
  self->dropAction_ = dropAction;
}

- (void)setDropColumnWithInt:(int)column {
  self->dropColumn_ = column;
}

- (void)setDropIndexWithInt:(int)index {
  self->dropIndex_ = index;
}

- (void)setDropPointWithRAREUIPoint:(RAREUIPoint *)point {
  self->dropPoint_ = point;
}

- (void)setInsertAtEndWithBoolean:(BOOL)insertAtEnd {
  self->insertAtEnd_ = insertAtEnd;
}

- (void)setInsertModeWithBoolean:(BOOL)insertMode {
  self->insertMode_ = insertMode;
}

- (void)setSourceWidgetWithRAREiWidget:(id<RAREiWidget>)w {
  self->sourceWidget_ = w;
}

- (void)setTargetWidgetWithRAREiWidget:(id<RAREiWidget>)w {
  self->targetWidget_ = w;
}

- (void)setTransferableWithRAREiTransferable:(id<RAREiTransferable>)transferable {
  self->transferable_ = transferable;
}

- (void)setUserDataWithId:(id)data {
  self->userData_ = data;
}

- (id)getDataWithNSString:(NSString *)name {
  if (transferable_ != nil) {
    RARETransferFlavor *flavor = [RARETransferFlavor getDataFlavorWithRARETransferFlavorArray:[transferable_ getTransferFlavors] withNSString:name];
    return (flavor == nil) ? nil : [transferable_ getTransferDataWithRARETransferFlavor:flavor];
  }
  return nil;
}

- (id)getDataWithRARETransferFlavor:(RARETransferFlavor *)flavor {
  return ((transferable_ == nil) || (flavor == nil)) ? nil : [transferable_ getTransferDataWithRARETransferFlavor:flavor];
}

- (int)getDropAction {
  return dropAction_;
}

- (int)getDropColumn {
  return dropColumn_;
}

- (int)getDropIndex {
  return dropIndex_;
}

- (RAREUIPoint *)getDropPoint {
  return dropPoint_;
}

- (id<RAREiWidget>)getSourceWidget {
  if (sourceWidget_ != nil) {
    return sourceWidget_;
  }
  if ((transferable_ != nil) && [transferable_ isTransferFlavorSupportedWithRARETransferFlavor:[RARETransferFlavor widgetFlavor]]) {
    @try {
      return (id<RAREiWidget>) check_protocol_cast([transferable_ getTransferDataWithRARETransferFlavor:[RARETransferFlavor widgetFlavor]], @protocol(RAREiWidget));
    }
    @catch (JavaLangException *e) {
      [RAREPlatform ignoreExceptionWithNSString:nil withJavaLangThrowable:e];
    }
  }
  return nil;
}

- (id<RAREiWidget>)getTargetWidget {
  return targetWidget_;
}

- (NSString *)getText {
  NSString *data = nil;
  do {
    if (transferable_ == nil) {
      break;
    }
    JavaIoReader *in = [((RARETransferFlavor *) nil_chk([RARETransferFlavor stringFlavor])) getReaderForTextWithRAREiTransferable:transferable_];
    data = [RAREUTStreams readerToStringWithJavaIoReader:in];
  }
  while (NO);
  return data;
}

- (id<RAREiTransferable>)getTransferable {
  return transferable_;
}

- (JavaNetURL *)getURL {
  id<JavaUtilList> list = [self getURLList];
  return ((list != nil) && ([list size] > 0)) ? [list getWithInt:0] : nil;
}

+ (JavaNetURL *)getURLWithRAREiTransferable:(id<RAREiTransferable>)t {
  id<JavaUtilList> l = [RAREDropInformation getURLListWithRAREiTransferable:t];
  return ((l != nil) && ([l size] > 0)) ? [l getWithInt:0] : nil;
}

- (id<JavaUtilList>)getURLList {
  if (transferable_ == nil) {
    return nil;
  }
  id<JavaUtilList> list = nil;
  NSString *uris = nil;
  if ([((id<RAREiTransferable>) nil_chk(transferable_)) isTransferFlavorSupportedWithRARETransferFlavor:[RARETransferFlavor urlFlavor]]) {
    list = [JavaUtilCollections singletonListWithId:(JavaNetURL *) check_class_cast([transferable_ getTransferDataWithRARETransferFlavor:[RARETransferFlavor urlFlavor]], [JavaNetURL class])];
  }
  else if ([transferable_ isTransferFlavorSupportedWithRARETransferFlavor:[RARETransferFlavor urlListFlavor]]) {
    id<JavaUtilList> ls = (id<JavaUtilList>) check_protocol_cast([transferable_ getTransferDataWithRARETransferFlavor:[RARETransferFlavor urlListFlavor]], @protocol(JavaUtilList));
    list = [[RAREUTFilterableList alloc] init];
    for (JavaIoFile * __strong f in nil_chk(ls)) {
      [list addWithId:[RAREaPlatformHelper fileToURLWithJavaIoFile:f]];
    }
  }
  else {
    JavaIoReader *in = [((RARETransferFlavor *) nil_chk([RARETransferFlavor stringFlavor])) getReaderForTextWithRAREiTransferable:transferable_];
    uris = [RAREUTStreams readerToStringWithJavaIoReader:in];
    if (uris != nil) {
      list = [[RAREUTFilterableList alloc] init];
      JavaUtilStringTokenizer *st = [[JavaUtilStringTokenizer alloc] initWithNSString:uris withNSString:@"\x0d\n"];
      while ([st hasMoreTokens]) {
        NSString *url = [st nextToken];
        if (url != nil) {
          [list addWithId:[[JavaNetURL alloc] initWithNSString:url]];
        }
      }
    }
  }
  return list;
}

+ (id<JavaUtilList>)getURLListWithRAREiTransferable:(id<RAREiTransferable>)t {
  if ([((id<RAREiTransferable>) nil_chk(t)) isTransferFlavorSupportedWithRARETransferFlavor:[RARETransferFlavor urlListFlavor]]) {
    return (id<JavaUtilList>) check_protocol_cast([t getTransferDataWithRARETransferFlavor:[RARETransferFlavor urlListFlavor]], @protocol(JavaUtilList));
  }
  if ([t isTransferFlavorSupportedWithRARETransferFlavor:[RARETransferFlavor urlFlavor]]) {
    return [JavaUtilCollections singletonListWithId:(JavaNetURL *) check_class_cast([t getTransferDataWithRARETransferFlavor:[RARETransferFlavor urlFlavor]], [JavaNetURL class])];
  }
  return nil;
}

- (id)getUserData {
  return userData_;
}

- (BOOL)isFlavorSupportedWithNSString:(NSString *)name {
  return (transferable_ == nil) ? NO : [RARETransferFlavor isDataFlavorSupportedWithRARETransferFlavorArray:[transferable_ getTransferFlavors] withNSString:name];
}

- (BOOL)isInsertAtEnd {
  return insertAtEnd_;
}

- (BOOL)isInsertMode {
  return insertMode_;
}

- (BOOL)isMoveAction {
  return (dropAction_ & RAREDnDConstants_ACTION_MOVE) > 0;
}

- (void)copyAllFieldsTo:(RAREDropInformation *)other {
  [super copyAllFieldsTo:other];
  other->dropAction_ = dropAction_;
  other->dropColumn_ = dropColumn_;
  other->dropIndex_ = dropIndex_;
  other->dropPoint_ = dropPoint_;
  other->insertAtEnd_ = insertAtEnd_;
  other->insertMode_ = insertMode_;
  other->sourceWidget_ = sourceWidget_;
  other->targetWidget_ = targetWidget_;
  other->transferable_ = transferable_;
  other->userData_ = userData_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "getDataWithNSString:", NULL, "LNSObject", 0x1, "RAREUnsupportedFlavorException;JavaIoIOException" },
    { "getDataWithRARETransferFlavor:", NULL, "LNSObject", 0x1, "RAREUnsupportedFlavorException;JavaIoIOException" },
    { "getDropPoint", NULL, "LRAREUIPoint", 0x1, NULL },
    { "getSourceWidget", NULL, "LRAREiWidget", 0x1, NULL },
    { "getTargetWidget", NULL, "LRAREiWidget", 0x1, NULL },
    { "getText", NULL, "LNSString", 0x1, "RAREUnsupportedFlavorException;JavaIoIOException" },
    { "getTransferable", NULL, "LRAREiTransferable", 0x1, NULL },
    { "getURL", NULL, "LJavaNetURL", 0x1, "RAREUnsupportedFlavorException;JavaIoIOException" },
    { "getURLWithRAREiTransferable:", NULL, "LJavaNetURL", 0x9, "JavaIoIOException;RAREUnsupportedFlavorException" },
    { "getURLList", NULL, "LJavaUtilList", 0x1, "RAREUnsupportedFlavorException;JavaIoIOException" },
    { "getURLListWithRAREiTransferable:", NULL, "LJavaUtilList", 0x9, "JavaIoIOException;RAREUnsupportedFlavorException" },
    { "getUserData", NULL, "LNSObject", 0x1, NULL },
    { "isFlavorSupportedWithNSString:", NULL, "Z", 0x1, NULL },
    { "isInsertAtEnd", NULL, "Z", 0x1, NULL },
    { "isInsertMode", NULL, "Z", 0x1, NULL },
    { "isMoveAction", NULL, "Z", 0x1, NULL },
  };
  static J2ObjcClassInfo _RAREDropInformation = { "DropInformation", "com.appnativa.rare.ui.dnd", NULL, 0x1, 16, methods, 0, NULL, 0, NULL};
  return &_RAREDropInformation;
}

@end
