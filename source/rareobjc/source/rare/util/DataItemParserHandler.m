//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/util/DataItemParserHandler.java
//
//  Created by decoteaud on 9/15/15.
//

#include "IOSClass.h"
#include "com/appnativa/rare/Platform.h"
#include "com/appnativa/rare/exception/ApplicationException.h"
#include "com/appnativa/rare/iCancelableFuture.h"
#include "com/appnativa/rare/iConstants.h"
#include "com/appnativa/rare/iDataCollection.h"
#include "com/appnativa/rare/iDataItemParser.h"
#include "com/appnativa/rare/iDataItemParserCallback.h"
#include "com/appnativa/rare/iFunctionCallback.h"
#include "com/appnativa/rare/iPlatformAppContext.h"
#include "com/appnativa/rare/net/ActionLink.h"
#include "com/appnativa/rare/net/iURLConnection.h"
#include "com/appnativa/rare/scripting/iScriptHandler.h"
#include "com/appnativa/rare/spot/DataCollection.h"
#include "com/appnativa/rare/spot/DataItem.h"
#include "com/appnativa/rare/ui/RenderableDataItem.h"
#include "com/appnativa/rare/ui/UIColor.h"
#include "com/appnativa/rare/ui/UIColorHelper.h"
#include "com/appnativa/rare/ui/UIProperties.h"
#include "com/appnativa/rare/ui/event/DataEvent.h"
#include "com/appnativa/rare/ui/iPlatformIcon.h"
#include "com/appnativa/rare/util/DataItemCSVParser.h"
#include "com/appnativa/rare/util/DataItemCollection.h"
#include "com/appnativa/rare/util/DataItemJSONParser.h"
#include "com/appnativa/rare/util/DataItemParserHandler.h"
#include "com/appnativa/rare/util/DataParser.h"
#include "com/appnativa/rare/viewer/iContainer.h"
#include "com/appnativa/rare/viewer/iViewer.h"
#include "com/appnativa/rare/widget/aPlatformWidget.h"
#include "com/appnativa/rare/widget/iWidget.h"
#include "com/appnativa/spot/SPOTBoolean.h"
#include "com/appnativa/spot/SPOTPrintableString.h"
#include "com/appnativa/spot/SPOTSet.h"
#include "com/appnativa/spot/iSPOTElement.h"
#include "com/appnativa/util/FilterableList.h"
#include "com/appnativa/util/MutableInteger.h"
#include "com/appnativa/util/ObjectHolder.h"
#include "java/io/IOException.h"
#include "java/io/Reader.h"
#include "java/lang/ClassNotFoundException.h"
#include "java/lang/Exception.h"
#include "java/lang/NullPointerException.h"
#include "java/lang/RuntimeException.h"
#include "java/lang/UnsupportedOperationException.h"
#include "java/net/URL.h"
#include "java/nio/channels/ClosedChannelException.h"
#include "java/util/ArrayList.h"
#include "java/util/Collection.h"
#include "java/util/HashMap.h"
#include "java/util/LinkedList.h"
#include "java/util/List.h"
#include "java/util/Map.h"

@implementation RAREDataItemParserHandler

static JavaUtilHashMap * RAREDataItemParserHandler_customParsers_;
static BOOL RAREDataItemParserHandler_hasCustomParsers_;

+ (JavaUtilHashMap *)customParsers {
  return RAREDataItemParserHandler_customParsers_;
}

+ (void)setCustomParsers:(JavaUtilHashMap *)customParsers {
  RAREDataItemParserHandler_customParsers_ = customParsers;
}

+ (BOOL)hasCustomParsers {
  return RAREDataItemParserHandler_hasCustomParsers_;
}

+ (BOOL *)hasCustomParsersRef {
  return &RAREDataItemParserHandler_hasCustomParsers_;
}

- (id)initWithRAREiWidget:(id<RAREiWidget>)v {
  if (self = [super initWithNSString:@"no-name" withJavaUtilList:nil]) {
    tabular_ = NO;
    if (v == nil) {
      @throw [[JavaLangNullPointerException alloc] init];
    }
    widget_ = v;
  }
  return self;
}

- (id)initWithRAREiWidget:(id<RAREiWidget>)v
         withJavaUtilList:(id<JavaUtilList>)list {
  if (self = [super initWithNSString:@"no-name" withJavaUtilList:list]) {
    tabular_ = NO;
    if (v == nil) {
      @throw [[JavaLangNullPointerException alloc] init];
    }
    widget_ = v;
  }
  return self;
}

- (void)addParsedRowWithRARERenderableDataItem:(RARERenderableDataItem *)row {
  if (!hasRowInfo_) {
    [((id<JavaUtilList>) nil_chk(theList_)) addWithId:row];
    return;
  }
  id o = [((RARERenderableDataItem *) nil_chk(row)) getModelData];
  int l = 1;
  if ([o isKindOfClass:[RAREUTMutableInteger class]]) {
    RAREUTMutableInteger *level = (RAREUTMutableInteger *) check_class_cast(o, [RAREUTMutableInteger class]);
    [row setModelDataWithId:nil];
    l = [((RAREUTMutableInteger *) nil_chk(level)) intValue];
    if (l < 1) {
      l = 1;
    }
  }
  if ((l < 2) || (currentRow_ == nil)) {
    [((id<JavaUtilList>) nil_chk(theList_)) addWithId:row];
    currentRow_ = row;
    if (levelStack_ != nil) {
      [levelStack_ clear];
    }
  }
  else {
    RARERenderableDataItem *ci = nil;
    if (levelStack_ == nil) {
      levelStack_ = [[JavaUtilLinkedList alloc] init];
    }
    int currentLevel = [((JavaUtilLinkedList *) nil_chk(levelStack_)) size] + 1;
    if (currentLevel > l) {
      while (currentLevel > l) {
        (void) [levelStack_ poll];
        currentLevel--;
      }
      [((RARERenderableDataItem *) nil_chk([levelStack_ peek])) addWithId:row];
    }
    else if (currentLevel == l) {
      [((RARERenderableDataItem *) nil_chk([levelStack_ peek])) addWithId:row];
    }
    else {
      ci = currentRow_;
      ci = [ci getItemWithInt:0];
      [((RARERenderableDataItem *) nil_chk(ci)) addWithId:row];
      if ([levelStack_ peek] != currentRow_) {
        [levelStack_ addWithInt:0 withId:ci];
      }
    }
    currentRow_ = row;
  }
}

+ (id<RAREiDataCollection>)createCollectionWithRAREiPlatformAppContext:(id<RAREiPlatformAppContext>)ctx
                                            withRARESPOTDataCollection:(RARESPOTDataCollection *)dc
                                             withRAREiFunctionCallback:(id<RAREiFunctionCallback>)cb {
  id<JavaUtilMap> attributes = nil;
  RAREActionLink *link = [RAREActionLink getActionLinkWithRAREiWidget:[((id<RAREiPlatformAppContext>) nil_chk(ctx)) getRootViewer] withSPOTPrintableString:((RARESPOTDataCollection *) nil_chk(dc))->dataURL_ withInt:0];
  if ([((SPOTPrintableString *) nil_chk(dc->attributes_)) getValue] != nil) {
    attributes = [RAREDataParser parseNameValuePairsWithSPOTPrintableString:dc->attributes_];
  }
  BOOL tabular = [((SPOTBoolean *) nil_chk(dc->tabular_)) booleanValue];
  return [RAREDataItemParserHandler createCollectionWithRAREiPlatformAppContext:ctx withNSString:[((SPOTPrintableString *) nil_chk(dc->name_)) getValue] withRAREActionLink:link withJavaUtilMap:attributes withBoolean:tabular withRAREiFunctionCallback:cb];
}

+ (id<RAREiDataCollection>)createCollectionWithRAREiPlatformAppContext:(id<RAREiPlatformAppContext>)ctx
                                                          withNSString:(NSString *)name
                                                    withRAREActionLink:(RAREActionLink *)link
                                                       withJavaUtilMap:(id<JavaUtilMap>)attributes
                                                           withBoolean:(BOOL)tabular
                                             withRAREiFunctionCallback:(id<RAREiFunctionCallback>)cb {
  RAREDataItemParserHandler *p = [[RAREDataItemParserHandler alloc] initWithRAREiWidget:[((id<RAREiPlatformAppContext>) nil_chk(ctx)) getRootViewer]];
  [p setActionLinkWithRAREActionLink:link];
  p->widget_ = [ctx getRootViewer];
  p->theName_ = name;
  p->tabular_ = tabular;
  if ((attributes != nil) && ([attributes size] > 0)) {
    p->attributeMap_ = attributes;
  }
  if (cb != nil) {
    [p load__WithRAREiFunctionCallback:cb];
  }
  return p;
}

- (RARERenderableDataItem *)createItemWithId:(id)value {
  if ([value isKindOfClass:[RARESPOTDataItem class]]) {
    return [RAREaWidget populateItemWithRAREiWidget:widget_ withRARESPOTDataItem:(RARESPOTDataItem *) check_class_cast(value, [RARESPOTDataItem class]) withRARERenderableDataItem:nil];
  }
  return [[RARERenderableDataItem alloc] initWithId:value];
}

- (RARERenderableDataItem *)createItemWithId:(id)value
                                     withInt:(int)type
                                      withId:(id)data
                                      withId:(id)icon
                                      withId:(id)color {
  id<RAREiPlatformIcon> ic = nil;
  RAREUIColor *c = nil;
  if (icon != nil) {
    ic = ([icon isKindOfClass:[NSString class]]) ? [self getIconWithNSString:(NSString *) check_class_cast(icon, [NSString class]) withNSString:nil] : (id<RAREiPlatformIcon>) check_protocol_cast(icon, @protocol(RAREiPlatformIcon));
  }
  if (color != nil) {
    c = ([color isKindOfClass:[NSString class]]) ? [RAREUIColorHelper getColorWithNSString:(NSString *) check_class_cast(color, [NSString class])] : (RAREUIColor *) check_class_cast(color, [RAREUIColor class]);
  }
  RARERenderableDataItem *di = [[RARERenderableDataItem alloc] initWithId:value withInt:type withId:data withRAREiPlatformIcon:ic];
  if (c != nil) {
    [di setForegroundWithRAREUIColor:c];
  }
  return di;
}

- (RARERenderableDataItem *)createItemExWithRARESPOTDataItem:(RARESPOTDataItem *)item {
  return [RAREaWidget populateItemWithRAREiWidget:widget_ withRARESPOTDataItem:item withRARERenderableDataItem:nil];
}

- (void)disposeCollection {
  [super disposeCollection];
  actionLink_ = nil;
}

- (void)finishedParsing {
  if (levelStack_ != nil) {
    [levelStack_ clear];
  }
  levelStack_ = nil;
  currentRow_ = nil;
}

- (void)load__WithRAREiFunctionCallback:(id<RAREiFunctionCallback>)cb {
  callback_ = cb;
  if (actionLink_ == nil) {
    return;
  }
  if ((cb == nil) && ![((RAREActionLink *) nil_chk(actionLink_)) isDeferred]) {
    [self run];
  }
  else {
    (void) [((id<RAREiPlatformAppContext>) nil_chk([RAREPlatform getAppContext])) executeBackgroundTaskWithJavaLangRunnable:self];
  }
}

+ (id<JavaUtilList>)parseWithRAREiWidget:(id<RAREiWidget>)context
                      withRAREActionLink:(RAREActionLink *)link
                                 withInt:(int)columnCount {
  if ([((RAREActionLink *) nil_chk(link)) isCollection]) {
    id<JavaUtilCollection> coll = [link getCollection];
    if ([(id) coll conformsToProtocol: @protocol(JavaUtilList)]) {
      return (id<JavaUtilList>) check_protocol_cast(coll, @protocol(JavaUtilList));
    }
    else {
      return [[JavaUtilArrayList alloc] initWithJavaUtilCollection:coll];
    }
  }
  else {
    RAREDataItemParserHandler *p = [[RAREDataItemParserHandler alloc] initWithRAREiWidget:context];
    [p setActionLinkWithRAREActionLink:link];
    [RAREDataItemParserHandler parseWithRAREiWidget:context withRAREActionLink:link withInt:columnCount withRAREiDataItemParserCallback:p];
    return p->theList_;
  }
}

+ (void)parseWithRAREiWidget:(id<RAREiWidget>)context
                 withSPOTSet:(SPOTSet *)items
            withJavaUtilList:(id<JavaUtilList>)list {
  if (context == nil) {
    @throw [[JavaLangNullPointerException alloc] init];
  }
  int len = (items == nil) ? 0 : [items getCount];
  if (len > 0) {
    RARESPOTDataItem *item;
    for (int i = 0; i < len; i++) {
      item = (RARESPOTDataItem *) check_class_cast([((SPOTSet *) nil_chk(items)) getExWithInt:i], [RARESPOTDataItem class]);
      [((id<JavaUtilList>) nil_chk(list)) addWithId:[RAREaWidget populateItemWithRAREiWidget:context withRARESPOTDataItem:item withRARERenderableDataItem:nil]];
    }
  }
}

+ (void)parseCollectionWithRAREiWidget:(id<RAREiWidget>)context
                    withRAREActionLink:(RAREActionLink *)link
                               withInt:(int)columnCount
       withRAREiDataItemParserCallback:(id<RAREiDataItemParserCallback>)callback {
  id<JavaUtilCollection> coll = [((RAREActionLink *) nil_chk(link)) getCollection];
  [((id<RAREiDataItemParserCallback>) nil_chk(callback)) startedParsing];
  for (RARERenderableDataItem * __strong row in nil_chk(coll)) {
    [callback addParsedRowWithRARERenderableDataItem:row];
  }
  [callback finishedParsing];
}

+ (void)parseWithRAREiWidget:(id<RAREiWidget>)context
          withRAREActionLink:(RAREActionLink *)link
                     withInt:(int)columnCount
withRAREiDataItemParserCallback:(id<RAREiDataItemParserCallback>)callback {
  if ([((RAREActionLink *) nil_chk(link)) isCollection]) {
    [RAREDataItemParserHandler parseCollectionWithRAREiWidget:context withRAREActionLink:link withInt:columnCount withRAREiDataItemParserCallback:callback];
    return;
  }
  id<RAREiDataItemParser> dp = [RAREDataItemParserHandler createCustomParserWithNSString:[link getParserClassName]];
  if (dp != nil) {
    [dp parseWithRAREiWidget:context withRAREActionLink:link withInt:columnCount withRAREiDataItemParserCallback:callback];
    return;
  }
  @try {
    JavaIoReader *reader = [link getReader];
    NSString *mimeType = [link getContentType];
    [link setSeparatorsFromMimeTypeWithNSString:mimeType withBoolean:YES];
    if (mimeType == nil) {
      mimeType = [RAREiConstants TEXT_MIME_TYPE];
    }
    else {
      dp = [RAREDataItemParserHandler createCustomParserFromMimeWithNSString:mimeType];
      if (dp != nil) {
        [dp parseWithRAREiWidget:context withRAREActionLink:link withInt:columnCount withRAREiDataItemParserCallback:callback];
        return;
      }
    }
    if ([((NSString *) nil_chk(mimeType)) hasPrefix:[RAREiConstants XML_MIME_TYPE]]) {
      @throw [[JavaLangUnsupportedOperationException alloc] initWithNSString:@"XML format not supported"];
    }
    else if ([mimeType hasPrefix:[RAREiConstants SDF_MIME_TYPE]] || [mimeType hasPrefix:[RAREiConstants RML_MIME_TYPE]]) {
      RARESPOTDataItem *items;
      @try {
        items = (RARESPOTDataItem *) check_class_cast([RAREDataParser loadSPOTObjectSDFWithRAREiWidget:context withJavaIoReader:reader withISPOTElement:[[RARESPOTDataItem alloc] initWithBoolean:NO] withNSString:mimeType withJavaNetURL:[((id<RAREiURLConnection>) nil_chk([link getConnection])) getURL]], [RARESPOTDataItem class]);
        [RAREDataItemParserHandler parseWithRAREiWidget:context withInt:columnCount withRARESPOTDataItem:items withRAREiDataItemParserCallback:callback];
      }
      @catch (JavaLangException *ex) {
        @throw [RAREApplicationException runtimeExceptionWithJavaLangThrowable:ex];
      }
    }
    else if ([mimeType hasPrefix:[RAREiConstants JSON_MIME_TYPE]] || [mimeType hasPrefix:@"text/x-json"]) {
      RAREDataItemJSONParser *parser = [[RAREDataItemJSONParser alloc] initWithJavaIoReader:reader];
      [parser parseWithRAREiWidget:context withBoolean:columnCount > 0 withRAREiDataItemParserCallback:callback];
    }
    else {
      RAREDataItemCSVParser *parser = [[RAREDataItemCSVParser alloc] initWithJavaIoReader:reader withChar:[link getColumnSeparator] withChar:[link getLinkedDataSeparator]];
      [parser setRowInfoSeparatorWithChar:[link getRowInfoSeparator]];
      [parser setUnescapeWithBoolean:[link isUnescape]];
      [parser parseWithRAREiWidget:context withInt:columnCount withRAREiDataItemParserCallback:callback];
    }
  }
  @catch (JavaNioChannelsClosedChannelException *e) {
  }
  @finally {
    [link close];
  }
}

+ (void)parseWithRAREiWidget:(id<RAREiWidget>)context
                     withInt:(int)cols
        withRARESPOTDataItem:(RARESPOTDataItem *)items
withRAREiDataItemParserCallback:(id<RAREiDataItemParserCallback>)callback {
  SPOTSet *set = [RAREDataParser resolveSetWithRAREiWidget:context withSPOTSet:[((RARESPOTDataItem *) nil_chk(items)) getSubItems] withIOSClass:[IOSClass classWithClass:[RARESPOTDataItem class]]];
  [((id<RAREiDataItemParserCallback>) nil_chk(callback)) startedParsing];
  RARERenderableDataItem *di;
  @try {
    di = [RAREDataItemParserHandler parseItemWithRARESPOTDataItem:items withRAREiDataItemParserCallback:callback withBoolean:NO];
    [callback setRootItemWithRARERenderableDataItem:di];
    if (set != nil) {
      int len = [set getCount];
      RARESPOTDataItem *item;
      for (int i = 0; i < len; i++) {
        item = (RARESPOTDataItem *) check_class_cast([set getWithInt:i], [RARESPOTDataItem class]);
        di = [RAREDataItemParserHandler parseItemWithRARESPOTDataItem:item withRAREiDataItemParserCallback:callback withBoolean:YES];
        [callback addParsedRowWithRARERenderableDataItem:di];
      }
    }
  }
  @finally {
    if (![((id<RAREiWidget>) nil_chk(context)) isDisposed]) {
      [callback finishedParsing];
    }
  }
}

+ (void)parseWithRAREiWidget:(id<RAREiWidget>)context
                withNSString:(NSString *)mimeType
            withJavaIoReader:(JavaIoReader *)reader
                     withInt:(int)columnCount
withRAREiDataItemParserCallback:(id<RAREiDataItemParserCallback>)callback
              withJavaNetURL:(JavaNetURL *)contextURL {
  if (mimeType == nil) {
    mimeType = [RAREiConstants TEXT_MIME_TYPE];
  }
  id<RAREiDataItemParser> dp = [RAREDataItemParserHandler createCustomParserWithNSString:mimeType];
  if (dp != nil) {
    [dp parseWithRAREiWidget:context withJavaIoReader:reader withNSString:mimeType withInt:columnCount withRAREiDataItemParserCallback:callback];
    return;
  }
  if ([((NSString *) nil_chk(mimeType)) hasPrefix:[RAREiConstants XML_MIME_TYPE]]) {
    @throw [[JavaLangUnsupportedOperationException alloc] initWithNSString:@"XML format not supported"];
  }
  else if ([mimeType hasPrefix:[RAREiConstants SDF_MIME_TYPE]] || [mimeType hasPrefix:[RAREiConstants RML_MIME_TYPE]]) {
    RARESPOTDataItem *items = (RARESPOTDataItem *) check_class_cast([RAREDataParser loadSPOTObjectSDFWithRAREiWidget:context withJavaIoReader:reader withISPOTElement:[[RARESPOTDataItem alloc] initWithBoolean:NO] withNSString:mimeType withJavaNetURL:contextURL], [RARESPOTDataItem class]);
    [RAREDataItemParserHandler parseWithRAREiWidget:context withInt:columnCount withRARESPOTDataItem:items withRAREiDataItemParserCallback:callback];
  }
  else if ([mimeType hasPrefix:[RAREiConstants JSON_MIME_TYPE]] || [mimeType hasPrefix:@"text/x-json"]) {
    RAREDataItemJSONParser *parser = [[RAREDataItemJSONParser alloc] initWithJavaIoReader:reader];
    [parser parseWithRAREiWidget:context withBoolean:columnCount > 0 withRAREiDataItemParserCallback:callback];
  }
  else {
    unichar sep = [RAREActionLink getSeparatorCharacterWithNSString:mimeType withNSString:[RAREActionLink MIME_COLUMN_SEPARATOR_PREFIX]];
    unichar ldsep = [RAREActionLink getSeparatorCharacterWithNSString:mimeType withNSString:[RAREActionLink MIME_LD_SEPARATOR_PREFIX]];
    RAREDataItemCSVParser *parser = [[RAREDataItemCSVParser alloc] initWithJavaIoReader:reader withChar:sep withChar:ldsep];
    [parser parseWithRAREiWidget:context withInt:columnCount withRAREiDataItemParserCallback:callback];
  }
}

+ (RARERenderableDataItem *)parseItemWithRARESPOTDataItem:(RARESPOTDataItem *)item
                          withRAREiDataItemParserCallback:(id<RAREiDataItemParserCallback>)callback
                                              withBoolean:(BOOL)parseSubs {
  RARERenderableDataItem *di = [((id<RAREiDataItemParserCallback>) nil_chk(callback)) createItemExWithRARESPOTDataItem:item];
  if (parseSubs) {
    SPOTSet *set = [((RARESPOTDataItem *) nil_chk(item)) getSubItems];
    int len = (set == nil) ? 0 : [set getCount];
    if (len > 0) {
      [((RARERenderableDataItem *) nil_chk(di)) ensureCapacityWithInt:len];
      for (int i = 0; i < len; i++) {
        [di addWithId:[RAREDataItemParserHandler parseItemWithRARESPOTDataItem:(RARESPOTDataItem *) check_class_cast([((SPOTSet *) nil_chk(set)) getWithInt:i], [RARESPOTDataItem class]) withRAREiDataItemParserCallback:callback withBoolean:YES]];
      }
    }
  }
  return di;
}

- (void)refreshWithRAREiWidget:(id<RAREiWidget>)context {
  @synchronized (self) {
    [self clearCollection];
    if (context == nil) {
      context = widget_;
    }
    if (actionLink_ != nil) {
      [RAREDataItemParserHandler parseWithRAREiWidget:context withRAREActionLink:actionLink_ withInt:tabular_ ? -1 : 0 withRAREiDataItemParserCallback:self];
    }
    loaded_ = YES;
  }
}

- (void)run {
  id<RAREiFunctionCallback> cb = callback_;
  callback_ = nil;
  @try {
    [self refreshWithRAREiWidget:widget_];
    if (cb != nil) {
      [self callCallbackWithId:theList_ withBoolean:NO withRAREiFunctionCallback:cb];
    }
  }
  @catch (JavaLangException *e) {
    if (cb == nil) {
      @throw [RAREApplicationException runtimeExceptionWithJavaLangThrowable:e];
    }
    else {
      [self callCallbackWithId:e withBoolean:YES withRAREiFunctionCallback:cb];
    }
  }
}

- (void)startedParsing {
  if (theList_ == nil) {
    theList_ = [[RAREUTFilterableList alloc] init];
  }
}

- (void)setRootItemWithRARERenderableDataItem:(RARERenderableDataItem *)item {
  rootItem_ = item;
}

- (id<RAREiPlatformIcon>)getIconWithNSString:(NSString *)icon
                                withNSString:(NSString *)description_ {
  return (widget_ == nil) ? nil : [widget_ getIconWithNSString:icon withNSString:description_];
}

- (RARERenderableDataItem *)getRootItem {
  return rootItem_;
}

- (id<JavaUtilList>)getListWithRAREiWidget:(id<RAREiWidget>)context {
  @synchronized (self) {
    if ((actionLink_ != nil) && (theList_ == nil)) {
      @try {
        [self refreshWithRAREiWidget:context];
      }
      @catch (JavaIoIOException *e) {
        @throw [[RAREApplicationException alloc] initWithJavaLangThrowable:e];
      }
    }
    return [super getListWithRAREiWidget:context];
  }
}

- (void)callCallbackWithId:(id)returnValue
               withBoolean:(BOOL)canceled
 withRAREiFunctionCallback:(id<RAREiFunctionCallback>)cb {
  RAREUTObjectHolder *h = [[RAREUTObjectHolder alloc] initWithId:actionLink_ withId:nil withId:returnValue];
  [RAREPlatform invokeLaterWithJavaLangRunnable:[[RAREDataItemParserHandler_$1 alloc] initWithBoolean:canceled withRAREiFunctionCallback:cb withRAREUTObjectHolder:h]];
}

+ (id<RAREiDataItemParser>)createCustomParserWithNSString:(NSString *)name {
  if (name == nil) {
    return nil;
  }
  if ([((NSString *) nil_chk(name)) hasPrefix:[RAREiConstants SCRIPT_PREFIX]]) {
    return [[RAREDataItemParserHandler_ScriptDataParser alloc] initWithNSString:[name substring:[RAREiConstants SCRIPT_PREFIX_LENGTH]]];
  }
  @try {
    return (id<RAREiDataItemParser>) check_protocol_cast([([RAREPlatform loadClassWithNSString:name]) newInstance], @protocol(RAREiDataItemParser));
  }
  @catch (JavaLangException *e) {
    @throw [RAREApplicationException runtimeExceptionWithJavaLangThrowable:e];
  }
}

+ (id<RAREiDataItemParser>)createCustomParserFromMimeWithNSString:(NSString *)mimeType {
  NSString *name = nil;
  int n = [((NSString *) nil_chk(mimeType)) indexOfString:@"parser="];
  if (n != -1) {
    n += [@"parser=" sequenceLength];
    int p = [mimeType indexOf:';' fromIndex:n];
    if (p == -1) {
      p = [mimeType sequenceLength];
    }
    name = [((NSString *) nil_chk([mimeType substring:n endIndex:p])) trim];
    if ([((NSString *) nil_chk(name)) sequenceLength] == 0) {
      name = nil;
    }
  }
  if (name == nil) {
    if (RAREDataItemParserHandler_hasCustomParsers_) {
      int p = [mimeType indexOf:';'];
      NSString *s = mimeType;
      if (p != -1) {
        s = [mimeType substring:0 endIndex:p];
      }
      name = [NSString stringWithFormat:@"Rare.DataItemParser.%@", [((NSString *) nil_chk(s)) replace:'/' withChar:'_']];
      IOSClass *cls = (RAREDataItemParserHandler_customParsers_ == nil) ? nil : [RAREDataItemParserHandler_customParsers_ getWithId:name];
      if (cls == nil) {
        name = [((RAREUIProperties *) nil_chk([RAREPlatform getUIDefaults])) getStringWithNSString:name];
        if (name != nil) {
          @try {
            cls = [RAREPlatform loadClassWithNSString:s];
            if (RAREDataItemParserHandler_customParsers_ == nil) {
              RAREDataItemParserHandler_customParsers_ = [[JavaUtilHashMap alloc] initWithInt:3];
            }
            (void) [((JavaUtilHashMap *) nil_chk(RAREDataItemParserHandler_customParsers_)) putWithId:s withId:cls];
          }
          @catch (JavaLangClassNotFoundException *e) {
            @throw [[RAREApplicationException alloc] initWithJavaLangThrowable:e];
          }
        }
        else {
          return nil;
        }
      }
      @try {
        return (id<RAREiDataItemParser>) check_protocol_cast([((IOSClass *) nil_chk(cls)) newInstance], @protocol(RAREiDataItemParser));
      }
      @catch (JavaLangException *e) {
        @throw [[RAREApplicationException alloc] initWithJavaLangThrowable:e];
      }
    }
    return nil;
  }
  return [RAREDataItemParserHandler createCustomParserWithNSString:name];
}

- (void)setActionLinkWithRAREActionLink:(RAREActionLink *)link {
  self->actionLink_ = link;
  self->hasRowInfo_ = [((RAREActionLink *) nil_chk(link)) getRowInfoSeparator] != 0;
}

+ (void)initialize {
  if (self == [RAREDataItemParserHandler class]) {
    RAREDataItemParserHandler_hasCustomParsers_ = [((RAREUIProperties *) nil_chk([RAREPlatform getUIDefaults])) getBooleanWithNSString:@"Rare.DataItemParser.hasCustom" withBoolean:NO];
  }
}

- (void)copyAllFieldsTo:(RAREDataItemParserHandler *)other {
  [super copyAllFieldsTo:other];
  other->actionLink_ = actionLink_;
  other->callback_ = callback_;
  other->currentRow_ = currentRow_;
  other->hasRowInfo_ = hasRowInfo_;
  other->levelStack_ = levelStack_;
  other->rootItem_ = rootItem_;
  other->tabular_ = tabular_;
  other->widget_ = widget_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "createCollectionWithRAREiPlatformAppContext:withRARESPOTDataCollection:withRAREiFunctionCallback:", NULL, "LRAREiDataCollection", 0x9, NULL },
    { "createCollectionWithRAREiPlatformAppContext:withNSString:withRAREActionLink:withJavaUtilMap:withBoolean:withRAREiFunctionCallback:", NULL, "LRAREiDataCollection", 0x9, NULL },
    { "createItemWithId:", NULL, "LRARERenderableDataItem", 0x1, NULL },
    { "createItemWithId:withInt:withId:withId:withId:", NULL, "LRARERenderableDataItem", 0x1, NULL },
    { "createItemExWithRARESPOTDataItem:", NULL, "LRARERenderableDataItem", 0x1, NULL },
    { "parseWithRAREiWidget:withRAREActionLink:withInt:", NULL, "LJavaUtilList", 0x9, "JavaIoIOException" },
    { "parseCollectionWithRAREiWidget:withRAREActionLink:withInt:withRAREiDataItemParserCallback:", NULL, "V", 0xc, "JavaIoIOException" },
    { "parseWithRAREiWidget:withRAREActionLink:withInt:withRAREiDataItemParserCallback:", NULL, "V", 0x9, "JavaIoIOException" },
    { "parseWithRAREiWidget:withInt:withRARESPOTDataItem:withRAREiDataItemParserCallback:", NULL, "V", 0x9, "JavaIoIOException" },
    { "parseWithRAREiWidget:withNSString:withJavaIoReader:withInt:withRAREiDataItemParserCallback:withJavaNetURL:", NULL, "V", 0x9, "JavaLangException" },
    { "parseItemWithRARESPOTDataItem:withRAREiDataItemParserCallback:withBoolean:", NULL, "LRARERenderableDataItem", 0x9, NULL },
    { "refreshWithRAREiWidget:", NULL, "V", 0x1, "JavaIoIOException" },
    { "getIconWithNSString:withNSString:", NULL, "LRAREiPlatformIcon", 0x1, NULL },
    { "getRootItem", NULL, "LRARERenderableDataItem", 0x1, NULL },
    { "getListWithRAREiWidget:", NULL, "LJavaUtilList", 0x4, NULL },
    { "callCallbackWithId:withBoolean:withRAREiFunctionCallback:", NULL, "V", 0x2, NULL },
    { "createCustomParserWithNSString:", NULL, "LRAREiDataItemParser", 0xa, NULL },
    { "createCustomParserFromMimeWithNSString:", NULL, "LRAREiDataItemParser", 0xa, NULL },
    { "setActionLinkWithRAREActionLink:", NULL, "V", 0x2, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "customParsers_", NULL, 0x8, "LJavaUtilHashMap" },
    { "hasCustomParsers_", NULL, 0x8, "Z" },
  };
  static J2ObjcClassInfo _RAREDataItemParserHandler = { "DataItemParserHandler", "com.appnativa.rare.util", NULL, 0x1, 19, methods, 2, fields, 0, NULL};
  return &_RAREDataItemParserHandler;
}

@end
@implementation RAREDataItemParserHandler_ScriptDataParser

- (id)initWithNSString:(NSString *)code {
  if (self = [super init]) {
    self->code_ = code;
  }
  return self;
}

- (id<JavaUtilCollection>)parseWithRAREiWidget:(id<RAREiWidget>)context
                            withRAREActionLink:(RAREActionLink *)link
                                       withInt:(int)columnCount {
  return (id<JavaUtilCollection>) check_protocol_cast([self parseWithRAREiWidget:context withRAREActionLink:link withJavaIoReader:nil withInt:columnCount withRAREiDataItemParserCallback:nil], @protocol(JavaUtilCollection));
}

- (void)parseWithRAREiWidget:(id<RAREiWidget>)context
          withRAREActionLink:(RAREActionLink *)link
                     withInt:(int)columnCount
withRAREiDataItemParserCallback:(id<RAREiDataItemParserCallback>)callback {
  (void) [self parseWithRAREiWidget:context withRAREActionLink:link withJavaIoReader:nil withInt:columnCount withRAREiDataItemParserCallback:callback];
}

- (id<JavaUtilCollection>)parseWithRAREiWidget:(id<RAREiWidget>)context
                              withJavaIoReader:(JavaIoReader *)reader
                                  withNSString:(NSString *)mimeType
                                       withInt:(int)columnCount {
  return (id<JavaUtilCollection>) check_protocol_cast([self parseWithRAREiWidget:context withRAREActionLink:nil withJavaIoReader:reader withInt:columnCount withRAREiDataItemParserCallback:nil], @protocol(JavaUtilCollection));
}

- (void)parseWithRAREiWidget:(id<RAREiWidget>)context
            withJavaIoReader:(JavaIoReader *)reader
                withNSString:(NSString *)mimeType
                     withInt:(int)columnCount
withRAREiDataItemParserCallback:(id<RAREiDataItemParserCallback>)callback {
  (void) [self parseWithRAREiWidget:context withRAREActionLink:nil withJavaIoReader:reader withInt:columnCount withRAREiDataItemParserCallback:callback];
}

- (void)setUnescapeWithBoolean:(BOOL)unescape {
  self->unescape_ = unescape;
}

- (id<RAREiDataItemParserCallback>)getCallback {
  return callback_;
}

- (int)getColumnCount {
  return columnCount_;
}

- (RAREActionLink *)getLink {
  return link_;
}

- (JavaIoReader *)getReader {
  if (link_ != nil) {
    return [link_ getReader];
  }
  return reader_;
}

- (BOOL)isUnescape {
  return unescape_;
}

- (id)parseWithRAREiWidget:(id<RAREiWidget>)context
        withRAREActionLink:(RAREActionLink *)link
          withJavaIoReader:(JavaIoReader *)reader
                   withInt:(int)columnCount
withRAREiDataItemParserCallback:(id<RAREiDataItemParserCallback>)callback {
  @try {
    self->link_ = link;
    self->columnCount_ = columnCount;
    self->callback_ = callback;
    self->reader_ = reader;
    if (context == nil) {
      context = [RAREPlatform getContextRootViewer];
    }
    if (dataEvent_ == nil) {
      dataEvent_ = [[RAREDataEvent alloc] initWithId:context withId:self];
    }
    else {
      [dataEvent_ setDataWithId:self];
      [dataEvent_ setTargetWithId:context];
    }
    id<RAREiScriptHandler> sh = [((id<RAREiWidget>) nil_chk(context)) getScriptHandler];
    return [RAREPlatform evaluateWithRAREiWidget:context withRAREiScriptHandler:sh withId:code_ withJavaUtilEventObject:dataEvent_];
  }
  @finally {
    if (link != nil) {
      [link close];
    }
  }
}

- (void)copyAllFieldsTo:(RAREDataItemParserHandler_ScriptDataParser *)other {
  [super copyAllFieldsTo:other];
  other->callback_ = callback_;
  other->code_ = code_;
  other->columnCount_ = columnCount_;
  other->dataEvent_ = dataEvent_;
  other->link_ = link_;
  other->reader_ = reader_;
  other->unescape_ = unescape_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "initWithNSString:", NULL, NULL, 0x0, NULL },
    { "parseWithRAREiWidget:withRAREActionLink:withInt:", NULL, "LJavaUtilCollection", 0x1, NULL },
    { "parseWithRAREiWidget:withJavaIoReader:withNSString:withInt:", NULL, "LJavaUtilCollection", 0x1, NULL },
    { "getCallback", NULL, "LRAREiDataItemParserCallback", 0x1, NULL },
    { "getLink", NULL, "LRAREActionLink", 0x1, NULL },
    { "getReader", NULL, "LJavaIoReader", 0x1, "JavaIoIOException" },
    { "isUnescape", NULL, "Z", 0x1, NULL },
    { "parseWithRAREiWidget:withRAREActionLink:withJavaIoReader:withInt:withRAREiDataItemParserCallback:", NULL, "LNSObject", 0x0, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "code_", NULL, 0x0, "LNSObject" },
    { "dataEvent_", NULL, 0x0, "LRAREDataEvent" },
  };
  static J2ObjcClassInfo _RAREDataItemParserHandler_ScriptDataParser = { "ScriptDataParser", "com.appnativa.rare.util", "DataItemParserHandler", 0x9, 8, methods, 2, fields, 0, NULL};
  return &_RAREDataItemParserHandler_ScriptDataParser;
}

@end
@implementation RAREDataItemParserHandler_$1

- (void)run {
  if (val$canceled_) {
    [((id<RAREiFunctionCallback>) nil_chk(val$cb_)) finishedWithBoolean:YES withId:val$h_];
  }
  else {
    [((id<RAREiFunctionCallback>) nil_chk(val$cb_)) finishedWithBoolean:NO withId:val$h_];
  }
}

- (id)initWithBoolean:(BOOL)capture$0
withRAREiFunctionCallback:(id<RAREiFunctionCallback>)capture$1
withRAREUTObjectHolder:(RAREUTObjectHolder *)capture$2 {
  val$canceled_ = capture$0;
  val$cb_ = capture$1;
  val$h_ = capture$2;
  return [super init];
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcFieldInfo fields[] = {
    { "val$canceled_", NULL, 0x1012, "Z" },
    { "val$cb_", NULL, 0x1012, "LRAREiFunctionCallback" },
    { "val$h_", NULL, 0x1012, "LRAREUTObjectHolder" },
  };
  static J2ObjcClassInfo _RAREDataItemParserHandler_$1 = { "$1", "com.appnativa.rare.util", "DataItemParserHandler", 0x8000, 0, NULL, 3, fields, 0, NULL};
  return &_RAREDataItemParserHandler_$1;
}

@end
