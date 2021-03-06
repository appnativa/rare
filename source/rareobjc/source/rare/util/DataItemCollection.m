//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/util/DataItemCollection.java
//
//  Created by decoteaud on 3/11/16.
//

#include "IOSClass.h"
#include "IOSIntArray.h"
#include "IOSObjectArray.h"
#include "com/appnativa/rare/ui/RenderableDataItem.h"
#include "com/appnativa/rare/ui/iListHandler.h"
#include "com/appnativa/rare/util/DataItemCollection.h"
#include "com/appnativa/rare/util/Grouper.h"
#include "com/appnativa/rare/widget/iWidget.h"
#include "com/appnativa/util/FilterableList.h"
#include "com/appnativa/util/Helper.h"
#include "com/appnativa/util/iFilterableList.h"
#include "java/io/IOException.h"
#include "java/lang/CharSequence.h"
#include "java/lang/StringBuilder.h"
#include "java/util/ArrayList.h"
#include "java/util/Arrays.h"
#include "java/util/Collection.h"
#include "java/util/Collections.h"
#include "java/util/HashMap.h"
#include "java/util/List.h"
#include "java/util/Map.h"

@implementation RAREDataItemCollection

static NSString * RAREDataItemCollection_MIME_TYPE_ = @"text/plain;type=csv";

+ (NSString *)MIME_TYPE {
  return RAREDataItemCollection_MIME_TYPE_;
}

+ (void)setMIME_TYPE:(NSString *)MIME_TYPE {
  RAREDataItemCollection_MIME_TYPE_ = MIME_TYPE;
}

- (id)initWithNSString:(NSString *)name
  withRAREiListHandler:(id<RAREiListHandler>)listComponent
           withBoolean:(BOOL)selection {
  if (self = [super init]) {
    theName_ = name;
    self->listComponent_ = listComponent;
    componentSelection_ = selection;
    loaded_ = listComponent != nil;
  }
  return self;
}

- (id)initWithNSString:(NSString *)name
      withJavaUtilList:(id<JavaUtilList>)list {
  if (self = [super init]) {
    theName_ = name;
    theList_ = list;
    loaded_ = list != nil;
  }
  return self;
}

- (void)clearCollection {
  if (theList_ != nil) {
    [theList_ clear];
  }
  loaded_ = NO;
}

- (void)disposeCollection {
  [self clearCollection];
  if (attributeMap_ != nil) {
    [attributeMap_ clear];
  }
  theList_ = nil;
  listComponent_ = nil;
  attributeMap_ = nil;
  loaded_ = NO;
}

- (id)getAttributeWithRAREiWidget:(id<RAREiWidget>)context
                     withNSString:(NSString *)name {
  return (attributeMap_ == nil) ? nil : [attributeMap_ getWithId:name];
}

- (id<JavaUtilCollection>)getCollectionWithRAREiWidget:(id<RAREiWidget>)context {
  return [self getSubItemDataWithRAREiWidget:context withInt:-1 withInt:-1 withInt:-1 withInt:-1 withBoolean:NO];
}

- (NSString *)getCollectionAsStringWithRAREiWidget:(id<RAREiWidget>)context {
  id<JavaUtilList> list = [self getListWithRAREiWidget:context];
  BOOL tabular = (listComponent_ != nil) && [listComponent_ isTabular];
  return [RAREDataItemCollection getValuesAsStringWithJavaUtilList:list withBoolean:NO withBoolean:tabular withNSString:@"|"];
}

- (NSString *)getCollectionName {
  return theName_;
}

- (NSString *)getCollectionStringMimeTypeWithRAREiWidget:(id<RAREiWidget>)context {
  return RAREDataItemCollection_MIME_TYPE_;
}

- (id<JavaLangCharSequence>)getEmptyCollectionText {
  return emptyCollectionText_;
}

- (id<JavaUtilCollection>)getItemDataWithRAREiWidget:(id<RAREiWidget>)context
                                         withBoolean:(BOOL)copy_ {
  return [self getSubItemDataWithRAREiWidget:context withInt:-1 withInt:-1 withInt:-1 withInt:-1 withBoolean:copy_];
}

- (id<JavaUtilList>)getListEx {
  return theList_;
}

- (id<JavaUtilCollection>)getSubCollectionDataWithRAREiWidget:(id<RAREiWidget>)context
                                                 withNSString:(NSString *)name
                                                  withBoolean:(BOOL)copy_ {
  return [self getSubItemDataWithRAREiWidget:context withNSString:name withBoolean:NO];
}

- (id<JavaUtilCollection>)getSubItemCollectionWithRAREiWidget:(id<RAREiWidget>)context
                                                      withInt:(int)fromIndex
                                                      withInt:(int)toIndex
                                                  withBoolean:(BOOL)copy_ {
  return [self getSubItemDataWithRAREiWidget:context withInt:fromIndex withInt:toIndex withInt:-1 withInt:-1 withBoolean:copy_];
}

- (id<JavaUtilCollection>)getSubItemDataWithRAREiWidget:(id<RAREiWidget>)context
                                                withInt:(int)fromIndex
                                                withInt:(int)toIndex
                                                withInt:(int)fromColumn
                                                withInt:(int)toColumn
                                            withBoolean:(BOOL)copy_ {
  id<JavaUtilList> list = [self getListWithRAREiWidget:context];
  if ([((id<JavaUtilList>) nil_chk(list)) size] == 0) {
    return list;
  }
  if (!copy_) {
    return [JavaUtilCollections unmodifiableCollectionWithJavaUtilCollection:[RAREDataItemCollection createWithJavaUtilList:list withInt:fromIndex withInt:toIndex withInt:fromColumn withInt:toColumn]];
  }
  return [RAREDataItemCollection copy__WithJavaUtilList:list withInt:fromIndex withInt:toIndex withInt:fromColumn withInt:toColumn withBoolean:shallowCopy_];
}

- (id<JavaUtilCollection>)getSubItemDataWithRAREiWidget:(id<RAREiWidget>)context
                                           withNSString:(NSString *)name
                                            withBoolean:(BOOL)copy_ {
  if (name == nil) {
    return [self getSubItemDataWithRAREiWidget:context withInt:-1 withInt:-1 withInt:-1 withInt:-1 withBoolean:copy_];
  }
  return [JavaUtilCollections EMPTY_LIST];
}

- (id<JavaUtilCollection>)groupWithRAREiWidget:(id<RAREiWidget>)context
                               withRAREGrouper:(RAREGrouper *)g {
  id<JavaUtilList> list = [self getListWithRAREiWidget:context];
  list = [RAREDataItemCollection copy__WithJavaUtilList:list withInt:-1 withInt:-1 withInt:-1 withInt:-1 withBoolean:shallowCopy_];
  return [((RAREGrouper *) nil_chk(g)) groupWithRAREiWidget:context withJavaUtilList:list];
}

- (id<JavaUtilCollection>)groupByWithRAREiWidget:(id<RAREiWidget>)context
                                         withInt:(int)col
                                     withBoolean:(BOOL)flat {
  IOSIntArray *cols = [IOSIntArray arrayWithInts:(int[]){ col } count:1];
  return [self groupByWithRAREiWidget:context withIntArray:cols withBoolean:flat];
}

- (id<JavaUtilCollection>)groupByWithRAREiWidget:(id<RAREiWidget>)context
                                         withInt:(int)col1
                                         withInt:(int)col2
                                     withBoolean:(BOOL)flat {
  IOSIntArray *cols = [IOSIntArray arrayWithInts:(int[]){ col1, col2 } count:2];
  return [self groupByWithRAREiWidget:context withIntArray:cols withBoolean:flat];
}

- (id<JavaUtilCollection>)groupByWithRAREiWidget:(id<RAREiWidget>)context
                                    withIntArray:(IOSIntArray *)cols
                                     withBoolean:(BOOL)flat {
  RAREGrouper *gp = [[RAREGrouper alloc] init];
  [gp setColumnsWithIntArray:cols];
  [gp setPreserveFirstWithBoolean:YES];
  [gp setPreserveRestWithBoolean:YES];
  [gp setFlatFormatWithBoolean:flat];
  return [self groupWithRAREiWidget:context withRAREGrouper:gp];
}

- (BOOL)isEmpty {
  return loaded_ ? ((theList_ == nil) ? YES : [theList_ isEmpty]) : YES;
}

- (BOOL)isLoaded {
  return loaded_;
}

- (BOOL)isRefreshOnURLConnection {
  return refreshOnConnection_;
}

- (void)refreshWithRAREiWidget:(id<RAREiWidget>)context {
}

- (void)setAttributeWithRAREiWidget:(id<RAREiWidget>)context
                       withNSString:(NSString *)name
                             withId:(id)value {
  if (attributeMap_ == nil) {
    attributeMap_ = [[JavaUtilHashMap alloc] init];
  }
  (void) [((id<JavaUtilMap>) nil_chk(attributeMap_)) putWithId:name withId:value];
}

- (void)setCollectionDataWithJavaUtilList:(id<JavaUtilList>)list {
  if (theList_ != nil) {
    [theList_ clear];
    [theList_ addAllWithJavaUtilCollection:list];
  }
  else {
    theList_ = [[JavaUtilArrayList alloc] initWithJavaUtilCollection:list];
  }
  loaded_ = YES;
}

- (void)setCollectionNameWithNSString:(NSString *)name {
  theName_ = name;
}

- (void)setEmptyCollectionTextWithJavaLangCharSequence:(id<JavaLangCharSequence>)text {
  emptyCollectionText_ = text;
}

- (void)setRefreshOnURLConnectionWithBoolean:(BOOL)refresh {
  refreshOnConnection_ = refresh;
}

- (int)size {
  return loaded_ ? ((theList_ == nil) ? 0 : [theList_ size]) : 0;
}

- (id<JavaUtilList>)toListWithJavaUtilCollection:(id<JavaUtilCollection>)coll {
  if (coll == nil) {
    return nil;
  }
  if ([(id) coll conformsToProtocol: @protocol(JavaUtilList)]) {
    return (id<JavaUtilList>) check_protocol_cast(coll, @protocol(JavaUtilList));
  }
  return [[RAREUTFilterableList alloc] initWithJavaUtilCollection:coll];
}

- (NSString *)description {
  return [self getCollectionAsStringWithRAREiWidget:nil];
}

- (id<JavaUtilList>)getListWithRAREiWidget:(id<RAREiWidget>)context {
  id<JavaUtilList> list = theList_;
  if (listComponent_ != nil) {
    if (componentSelection_) {
      IOSObjectArray *a = [RAREDataItemCollection getItemsWithJavaUtilList:listComponent_ withIntArray:componentSelection_ ? [listComponent_ getSelectedIndexes] : nil withInt:-1];
      list = (a == nil) ? ((id) [JavaUtilCollections EMPTY_LIST]) : ((id) [JavaUtilArrays asListWithNSObjectArray:a]);
    }
    else {
      list = [listComponent_ getRows];
    }
  }
  return (list == nil) ? ((id) [JavaUtilCollections EMPTY_LIST]) : ((id) list);
}

+ (RAREDataItemCollection *)createCollectionWithNSString:(NSString *)name
                                        withJavaUtilList:(id<JavaUtilList>)list {
  return [[RAREDataItemCollection alloc] initWithNSString:name withJavaUtilList:[RAREDataItemCollection createItemListWithJavaUtilList:list]];
}

+ (RAREDataItemCollection *)createCollectionWithNSString:(NSString *)name
                                       withNSObjectArray:(IOSObjectArray *)list {
  return [[RAREDataItemCollection alloc] initWithNSString:name withJavaUtilList:[RAREDataItemCollection createItemListWithJavaUtilList:[JavaUtilArrays asListWithNSObjectArray:list]]];
}

+ (IOSObjectArray *)getItemsWithJavaUtilList:(id<JavaUtilList>)list
                                withIntArray:(IOSIntArray *)rows
                                     withInt:(int)col {
  int len = (list == nil) ? 0 : [list size];
  if (rows != nil) {
    len = (int) [rows count];
  }
  if (len == 0) {
    return nil;
  }
  IOSObjectArray *a = [IOSObjectArray arrayWithLength:len type:[IOSClass classWithClass:[RARERenderableDataItem class]]];
  RARERenderableDataItem *di;
  for (int i = 0; i < len; i++) {
    di = [((id<JavaUtilList>) nil_chk(list)) getWithInt:(rows == nil) ? i : IOSIntArray_Get(rows, i)];
    if ((col != -1) && (di != nil)) {
      di = [di getItemExWithInt:col];
    }
    if (di != nil) {
      (void) IOSObjectArray_Set(a, i, di);
    }
  }
  return a;
}

+ (id)getSelectionWithRAREiListHandler:(id<RAREiListHandler>)listComponent
                           withBoolean:(BOOL)multiple
                               withInt:(int)col {
  if (multiple) {
    return [RAREDataItemCollection getSelectionsWithRAREiListHandler:listComponent withInt:col];
  }
  RARERenderableDataItem *di = [((id<RAREiListHandler>) nil_chk(listComponent)) getSelectedItem];
  return ((di == nil) || (col == -1)) ? di : [di getItemExWithInt:col];
}

+ (id)getSelectionDataWithRAREiListHandler:(id<RAREiListHandler>)listComponent
                               withBoolean:(BOOL)multiple
                                   withInt:(int)col {
  if (multiple) {
    return [RAREDataItemCollection getValuesWithJavaUtilList:listComponent withIntArray:[((id<RAREiListHandler>) nil_chk(listComponent)) getSelectedIndexes] withInt:col withBoolean:YES];
  }
  RARERenderableDataItem *di = [((id<RAREiListHandler>) nil_chk(listComponent)) getSelectedItem];
  di = ((di == nil) || (col == -1)) ? di : [di getItemExWithInt:col];
  return (di == nil) ? nil : [di getLinkedData];
}

+ (IOSObjectArray *)getSelectionsWithRAREiListHandler:(id<RAREiListHandler>)listComponent
                                              withInt:(int)col {
  return [RAREDataItemCollection getItemsWithJavaUtilList:listComponent withIntArray:[((id<RAREiListHandler>) nil_chk(listComponent)) getSelectedIndexes] withInt:col];
}

+ (IOSObjectArray *)getSelectionsDataAsStringsWithRAREiListHandler:(id<RAREiListHandler>)listComponent
                                                           withInt:(int)col {
  return [RAREDataItemCollection getValuesAsStringsWithJavaUtilList:listComponent withIntArray:[((id<RAREiListHandler>) nil_chk(listComponent)) getSelectedIndexes] withInt:col withBoolean:YES withBoolean:NO withNSString:nil];
}

+ (IOSObjectArray *)getValuesWithJavaUtilList:(id<JavaUtilList>)list
                                 withIntArray:(IOSIntArray *)rows
                                      withInt:(int)col
                                  withBoolean:(BOOL)data {
  int len = (list == nil) ? 0 : [list size];
  if (rows != nil) {
    len = (int) [rows count];
  }
  if (len == 0) {
    return nil;
  }
  IOSObjectArray *a = [IOSObjectArray arrayWithLength:len type:[IOSClass classWithClass:[NSObject class]]];
  RARERenderableDataItem *di;
  for (int i = 0; i < len; i++) {
    di = [((id<JavaUtilList>) nil_chk(list)) getWithInt:(rows == nil) ? i : IOSIntArray_Get(rows, i)];
    if ((col != -1) && (di != nil)) {
      di = [di getItemExWithInt:col];
    }
    if (di == nil) {
      continue;
    }
    (void) IOSObjectArray_Set(a, i, data ? [((RARERenderableDataItem *) nil_chk(di)) getLinkedData] : di);
  }
  return a;
}

+ (NSString *)getValuesAsStringWithJavaUtilList:(id<JavaUtilList>)list
                                    withBoolean:(BOOL)data
                                    withBoolean:(BOOL)row
                                   withNSString:(NSString *)sep {
  return [((JavaLangStringBuilder *) nil_chk([RAREDataItemCollection getValuesAsStringWithJavaLangStringBuilder:nil withJavaUtilList:list withIntArray:nil withInt:-1 withBoolean:data withBoolean:row withNSString:sep withNSString:nil])) description];
}

+ (JavaLangStringBuilder *)getValuesAsStringWithJavaLangStringBuilder:(JavaLangStringBuilder *)sb
                                                     withJavaUtilList:(id<JavaUtilList>)list
                                                         withIntArray:(IOSIntArray *)rows
                                                              withInt:(int)col
                                                          withBoolean:(BOOL)data
                                                          withBoolean:(BOOL)row
                                                         withNSString:(NSString *)csep
                                                         withNSString:(NSString *)rsep {
  if (rsep == nil) {
    rsep = @"\x0d\n";
  }
  return [RAREUTHelper toStringWithJavaLangStringBuilder:sb withJavaUtilList:[JavaUtilArrays asListWithNSObjectArray:[RAREDataItemCollection getValuesAsStringsWithJavaUtilList:list withIntArray:rows withInt:col withBoolean:data withBoolean:row withNSString:csep]] withNSString:rsep];
}

+ (IOSObjectArray *)getValuesAsStringsWithJavaUtilList:(id<JavaUtilList>)list
                                          withIntArray:(IOSIntArray *)rows
                                               withInt:(int)col
                                           withBoolean:(BOOL)data
                                           withBoolean:(BOOL)row
                                          withNSString:(NSString *)sep {
  int len = (list == nil) ? 0 : [list size];
  if (rows != nil) {
    len = (int) [rows count];
  }
  if (len == 0) {
    return nil;
  }
  if (sep == nil) {
    sep = @"\t";
  }
  IOSObjectArray *a = [IOSObjectArray arrayWithLength:len type:[IOSClass classWithClass:[NSString class]]];
  RARERenderableDataItem *di;
  id o;
  JavaLangStringBuilder *sb = nil;
  if (row && (col != -1)) {
    row = NO;
  }
  else if (!row) {
    col = -1;
  }
  if (row) {
    sb = [[JavaLangStringBuilder alloc] init];
  }
  for (int i = 0; i < len; i++) {
    di = [((id<JavaUtilList>) nil_chk(list)) getWithInt:(rows == nil) ? i : IOSIntArray_Get(rows, i)];
    if ((col != -1) && (di != nil)) {
      di = [di getItemExWithInt:col];
    }
    if (di == nil) {
      continue;
    }
    if (row && ([((RARERenderableDataItem *) nil_chk(di)) size] > 0)) {
      [((JavaLangStringBuilder *) nil_chk(sb)) setLengthWithInt:0];
      (void) IOSObjectArray_Set(a, i, [((JavaLangStringBuilder *) nil_chk([RAREDataItemCollection getValuesAsStringWithJavaLangStringBuilder:sb withJavaUtilList:[di getItems] withIntArray:nil withInt:-1 withBoolean:data withBoolean:NO withNSString:nil withNSString:sep])) description]);
    }
    else {
      o = data ? [((RARERenderableDataItem *) nil_chk(di)) getLinkedData] : di;
      (void) IOSObjectArray_Set(a, i, (o == nil) ? nil : [o description]);
    }
  }
  return a;
}

+ (id<RAREUTiFilterableList>)copy__WithJavaUtilList:(id<JavaUtilList>)list
                                            withInt:(int)fromIndex
                                            withInt:(int)toIndex
                                            withInt:(int)fromCol
                                            withInt:(int)toCol
                                        withBoolean:(BOOL)shallow {
  int len = (list == nil) ? 0 : [list size];
  if ((toIndex > len) || (toIndex == -1)) {
    toIndex = len;
  }
  if (fromIndex < 0) {
    fromIndex = 0;
  }
  RAREUTFilterableList *a = [[RAREUTFilterableList alloc] initWithInt:toIndex - fromIndex];
  for (int i = fromIndex; i < toIndex; i++) {
    [a addWithId:[RAREDataItemCollection copy__WithRARERenderableDataItem:[((id<JavaUtilList>) nil_chk(list)) getWithInt:i] withInt:fromCol withInt:toCol withBoolean:shallow]];
  }
  return a;
}

+ (RARERenderableDataItem *)copy__WithRARERenderableDataItem:(RARERenderableDataItem *)row
                                                     withInt:(int)from
                                                     withInt:(int)to
                                                 withBoolean:(BOOL)shallow {
  if ((to == 0) && (from > -1)) {
    row = [((RARERenderableDataItem *) nil_chk(row)) getItemExWithInt:from];
  }
  if ((from < 0) || (to == 0)) {
    if (row == nil) {
      return nil;
    }
    return shallow ? [((RARERenderableDataItem *) nil_chk(row)) copy__] : [((RARERenderableDataItem *) nil_chk(row)) deepCopy];
  }
  return [RARERenderableDataItem toParentItemWithRAREUTiFilterableList:[RAREDataItemCollection copy__WithJavaUtilList:[((RARERenderableDataItem *) nil_chk(row)) getItems] withInt:from withInt:to withInt:-1 withInt:-1 withBoolean:shallow]];
}

+ (id<JavaUtilList>)createWithJavaUtilList:(id<JavaUtilList>)list
                                   withInt:(int)fromIndex
                                   withInt:(int)toIndex
                                   withInt:(int)fromCol
                                   withInt:(int)toCol {
  int len = (list == nil) ? 0 : [list size];
  if ((toIndex > len) || (toIndex == -1)) {
    toIndex = len;
  }
  if (fromIndex < 0) {
    fromIndex = 0;
  }
  if (fromCol == -1) {
    return ((fromIndex == 0) && (toIndex == len)) ? ((id) list) : ((id) [RAREDataItemCollection subListWithJavaUtilList:list withInt:fromIndex withInt:toIndex]);
  }
  RAREUTFilterableList *a = [[RAREUTFilterableList alloc] initWithInt:toIndex - fromIndex];
  for (int i = fromIndex; i < toIndex; i++) {
    [a addWithId:[RAREDataItemCollection createWithRARERenderableDataItem:[((id<JavaUtilList>) nil_chk(list)) getWithInt:i] withInt:fromCol withInt:toCol]];
  }
  return a;
}

+ (RARERenderableDataItem *)createWithRARERenderableDataItem:(RARERenderableDataItem *)row
                                                     withInt:(int)fromIndex
                                                     withInt:(int)toIndex {
  if ((toIndex == 0) && (fromIndex > -1)) {
    row = [((RARERenderableDataItem *) nil_chk(row)) getItemExWithInt:fromIndex];
  }
  if (row == nil) {
    return nil;
  }
  if ((fromIndex < 0) || (toIndex == 0)) {
    return row;
  }
  int len = [((RARERenderableDataItem *) nil_chk(row)) getItemCount];
  if ((toIndex > len) || (toIndex == -1)) {
    toIndex = len;
  }
  if (fromIndex < 0) {
    fromIndex = 0;
  }
  return [RARERenderableDataItem toParentItemWithRAREUTiFilterableList:([RAREDataItemCollection subListWithJavaUtilList:[row getItems] withInt:fromIndex withInt:toIndex])];
}

+ (id<JavaUtilList>)createItemListWithJavaUtilList:(id<JavaUtilList>)list {
  int len = (list == nil) ? 0 : [list size];
  RAREUTFilterableList *a = [[RAREUTFilterableList alloc] initWithInt:len];
  for (int i = 0; i < len; i++) {
    [a addWithId:([RARERenderableDataItem toItemWithId:[((id<JavaUtilList>) nil_chk(list)) getWithInt:i]])];
  }
  return a;
}

+ (id<RAREUTiFilterableList>)subListWithJavaUtilList:(id<JavaUtilList>)list
                                             withInt:(int)fromIndex
                                             withInt:(int)toIndex {
  RAREUTFilterableList *a = [[RAREUTFilterableList alloc] initWithInt:toIndex - fromIndex];
  for (int i = fromIndex; i < toIndex; i++) {
    [a addWithId:[((id<JavaUtilList>) nil_chk(list)) getWithInt:i]];
  }
  return a;
}

- (void)copyAllFieldsTo:(RAREDataItemCollection *)other {
  [super copyAllFieldsTo:other];
  other->attributeMap_ = attributeMap_;
  other->componentSelection_ = componentSelection_;
  other->emptyCollectionText_ = emptyCollectionText_;
  other->listComponent_ = listComponent_;
  other->loaded_ = loaded_;
  other->refreshOnConnection_ = refreshOnConnection_;
  other->shallowCopy_ = shallowCopy_;
  other->theList_ = theList_;
  other->theName_ = theName_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "getAttributeWithRAREiWidget:withNSString:", NULL, "LNSObject", 0x1, NULL },
    { "getCollectionWithRAREiWidget:", NULL, "LJavaUtilCollection", 0x1, NULL },
    { "getCollectionAsStringWithRAREiWidget:", NULL, "LNSString", 0x1, NULL },
    { "getCollectionName", NULL, "LNSString", 0x1, NULL },
    { "getCollectionStringMimeTypeWithRAREiWidget:", NULL, "LNSString", 0x1, NULL },
    { "getEmptyCollectionText", NULL, "LJavaLangCharSequence", 0x1, NULL },
    { "getItemDataWithRAREiWidget:withBoolean:", NULL, "LJavaUtilCollection", 0x1, NULL },
    { "getListEx", NULL, "LJavaUtilList", 0x1, NULL },
    { "getSubCollectionDataWithRAREiWidget:withNSString:withBoolean:", NULL, "LJavaUtilCollection", 0x1, NULL },
    { "getSubItemCollectionWithRAREiWidget:withInt:withInt:withBoolean:", NULL, "LJavaUtilCollection", 0x1, NULL },
    { "getSubItemDataWithRAREiWidget:withInt:withInt:withInt:withInt:withBoolean:", NULL, "LJavaUtilCollection", 0x1, NULL },
    { "getSubItemDataWithRAREiWidget:withNSString:withBoolean:", NULL, "LJavaUtilCollection", 0x1, NULL },
    { "groupWithRAREiWidget:withRAREGrouper:", NULL, "LJavaUtilCollection", 0x1, NULL },
    { "groupByWithRAREiWidget:withInt:withBoolean:", NULL, "LJavaUtilCollection", 0x1, NULL },
    { "groupByWithRAREiWidget:withInt:withInt:withBoolean:", NULL, "LJavaUtilCollection", 0x1, NULL },
    { "groupByWithRAREiWidget:withIntArray:withBoolean:", NULL, "LJavaUtilCollection", 0x1, NULL },
    { "isEmpty", NULL, "Z", 0x1, NULL },
    { "isLoaded", NULL, "Z", 0x1, NULL },
    { "isRefreshOnURLConnection", NULL, "Z", 0x1, NULL },
    { "refreshWithRAREiWidget:", NULL, "V", 0x1, "JavaIoIOException" },
    { "toListWithJavaUtilCollection:", NULL, "LJavaUtilList", 0x1, NULL },
    { "getListWithRAREiWidget:", NULL, "LJavaUtilList", 0x4, NULL },
    { "createCollectionWithNSString:withJavaUtilList:", NULL, "LRAREDataItemCollection", 0x9, NULL },
    { "createCollectionWithNSString:withNSObjectArray:", NULL, "LRAREDataItemCollection", 0x9, NULL },
    { "getItemsWithJavaUtilList:withIntArray:withInt:", NULL, "LIOSObjectArray", 0x9, NULL },
    { "getSelectionWithRAREiListHandler:withBoolean:withInt:", NULL, "LNSObject", 0x9, NULL },
    { "getSelectionDataWithRAREiListHandler:withBoolean:withInt:", NULL, "LNSObject", 0x9, NULL },
    { "getSelectionsWithRAREiListHandler:withInt:", NULL, "LIOSObjectArray", 0x9, NULL },
    { "getSelectionsDataAsStringsWithRAREiListHandler:withInt:", NULL, "LIOSObjectArray", 0x9, NULL },
    { "getValuesWithJavaUtilList:withIntArray:withInt:withBoolean:", NULL, "LIOSObjectArray", 0x9, NULL },
    { "getValuesAsStringWithJavaUtilList:withBoolean:withBoolean:withNSString:", NULL, "LNSString", 0x9, NULL },
    { "getValuesAsStringWithJavaLangStringBuilder:withJavaUtilList:withIntArray:withInt:withBoolean:withBoolean:withNSString:withNSString:", NULL, "LJavaLangStringBuilder", 0x9, NULL },
    { "getValuesAsStringsWithJavaUtilList:withIntArray:withInt:withBoolean:withBoolean:withNSString:", NULL, "LIOSObjectArray", 0x9, NULL },
    { "copy__WithJavaUtilList:withInt:withInt:withInt:withInt:withBoolean:", NULL, "LRAREUTiFilterableList", 0xa, NULL },
    { "copy__WithRARERenderableDataItem:withInt:withInt:withBoolean:", NULL, "LRARERenderableDataItem", 0xa, NULL },
    { "createWithJavaUtilList:withInt:withInt:withInt:withInt:", NULL, "LJavaUtilList", 0xa, NULL },
    { "createWithRARERenderableDataItem:withInt:withInt:", NULL, "LRARERenderableDataItem", 0xa, NULL },
    { "createItemListWithJavaUtilList:", NULL, "LJavaUtilList", 0xa, NULL },
    { "subListWithJavaUtilList:withInt:withInt:", NULL, "LRAREUTiFilterableList", 0xa, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "MIME_TYPE_", NULL, 0xa, "LNSString" },
    { "attributeMap_", NULL, 0x4, "LJavaUtilMap" },
    { "listComponent_", NULL, 0x4, "LRAREiListHandler" },
    { "shallowCopy_", NULL, 0x4, "Z" },
    { "theList_", NULL, 0x4, "LJavaUtilList" },
    { "theName_", NULL, 0x4, "LNSString" },
    { "loaded_", NULL, 0x4, "Z" },
  };
  static J2ObjcClassInfo _RAREDataItemCollection = { "DataItemCollection", "com.appnativa.rare.util", NULL, 0x1, 39, methods, 7, fields, 0, NULL};
  return &_RAREDataItemCollection;
}

@end
