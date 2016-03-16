//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/viewer/aFormViewer.java
//
//  Created by decoteaud on 3/11/16.
//

#include "IOSClass.h"
#include "com/appnativa/rare/Platform.h"
#include "com/appnativa/rare/exception/ApplicationException.h"
#include "com/appnativa/rare/iConstants.h"
#include "com/appnativa/rare/iFunctionCallback.h"
#include "com/appnativa/rare/iPlatformAppContext.h"
#include "com/appnativa/rare/net/ActionLink.h"
#include "com/appnativa/rare/net/FormHelper.h"
#include "com/appnativa/rare/spot/Form.h"
#include "com/appnativa/rare/spot/Link.h"
#include "com/appnativa/rare/spot/Viewer.h"
#include "com/appnativa/rare/ui/Utils.h"
#include "com/appnativa/rare/ui/aWidgetListener.h"
#include "com/appnativa/rare/ui/event/ActionEvent.h"
#include "com/appnativa/rare/ui/event/DataEvent.h"
#include "com/appnativa/rare/ui/event/EventListenerList.h"
#include "com/appnativa/rare/ui/event/iActionListener.h"
#include "com/appnativa/rare/ui/iPlatformComponent.h"
#include "com/appnativa/rare/util/DataParser.h"
#include "com/appnativa/rare/viewer/WindowViewer.h"
#include "com/appnativa/rare/viewer/aContainer.h"
#include "com/appnativa/rare/viewer/aFormViewer.h"
#include "com/appnativa/rare/viewer/aGroupBoxViewer.h"
#include "com/appnativa/rare/viewer/aViewer.h"
#include "com/appnativa/rare/viewer/iContainer.h"
#include "com/appnativa/rare/widget/aWidget.h"
#include "com/appnativa/rare/widget/iWidget.h"
#include "com/appnativa/spot/SPOTBoolean.h"
#include "com/appnativa/spot/SPOTPrintableString.h"
#include "com/appnativa/util/IdentityArrayList.h"
#include "com/appnativa/util/iCancelable.h"
#include "com/appnativa/util/json/JSONWriter.h"
#include "java/io/IOException.h"
#include "java/io/Writer.h"
#include "java/lang/Exception.h"
#include "java/lang/RuntimeException.h"
#include "java/net/MalformedURLException.h"
#include "java/util/HashMap.h"
#include "java/util/List.h"
#include "java/util/Map.h"

@implementation RAREaFormViewer

- (id)init {
  return [self initRAREaFormViewerWithRAREiContainer:nil];
}

- (id)initRAREaFormViewerWithRAREiContainer:(id<RAREiContainer>)parent {
  if (self = [super initWithRAREiContainer:nil]) {
    widgetType_ = [RAREiWidget_WidgetTypeEnum Form];
    retainInitialFieldValues_ = YES;
    actAsFormViewer_ = YES;
  }
  return self;
}

- (id)initWithRAREiContainer:(id<RAREiContainer>)parent {
  return [self initRAREaFormViewerWithRAREiContainer:parent];
}

- (void)addActionListenerWithRAREiActionListener:(id<RAREiActionListener>)l {
  if (listenerList_ == nil) {
    listenerList_ = [[RAREEventListenerList alloc] init];
  }
  [((RAREEventListenerList *) nil_chk(listenerList_)) addWithIOSClass:[IOSClass classWithProtocol:@protocol(RAREiActionListener)] withId:l];
}

- (void)configureWithRARESPOTViewer:(RARESPOTViewer *)vcfg {
  vcfg = [self checkForURLConfigWithRARESPOTViewer:vcfg];
  RARESPOTForm *fc = (RARESPOTForm *) check_class_cast(vcfg, [RARESPOTForm class]);
  actAsFormViewer_ = [((SPOTBoolean *) nil_chk(((RARESPOTForm *) nil_chk(fc))->actAsFormViewer_)) booleanValue];
  [self configureExWithRARESPOTGroupBox:fc];
  RARESPOTLink *link = [fc getActionLink];
  if (link != nil) {
    [self setActionListenerWithRAREiActionListener:[self createActionLinkWithRARESPOTLink:link]];
  }
  retainInitialFieldValues_ = [((SPOTBoolean *) nil_chk(fc->retainInitialFieldValues_)) booleanValue];
  isSubmittable__ = YES;
  if ([((SPOTPrintableString *) nil_chk(fc->submitAttributes_)) spot_hasValue]) {
    submitAttributes_ = [RAREDataParser parseNameValuePairsWithSPOTPrintableString:fc->submitAttributes_];
  }
  [self fireConfigureEventWithRARESPOTWidget:vcfg withNSString:[RAREiConstants EVENT_CONFIGURE]];
}

- (JavaUtilHashMap *)getHTTPValuesHash {
  JavaUtilHashMap *map = [super getHTTPValuesHash];
  if (submitAttributes_ != nil) {
    if (map == nil) {
      map = [[JavaUtilHashMap alloc] initWithJavaUtilMap:submitAttributes_];
    }
    else {
      [map putAllWithJavaUtilMap:submitAttributes_];
    }
  }
  return map;
}

- (void)removeActionListenerWithRAREiActionListener:(id<RAREiActionListener>)l {
  if (listenerList_ != nil) {
    [listenerList_ removeWithIOSClass:[IOSClass classWithProtocol:@protocol(RAREiActionListener)] withId:l];
  }
}

- (void)submit {
  [self submitFormWithRAREiFunctionCallback:nil];
}

- (void)submitFormWithRAREiFunctionCallback:(id<RAREiFunctionCallback>)cb {
  RAREaWidgetListener *wl = [self getWidgetListener];
  if ((wl != nil) && [wl isEnabledWithNSString:[RAREiConstants EVENT_SUBMIT]]) {
    RAREDataEvent *e = [[RAREDataEvent alloc] initWithId:[self getDataComponent] withId:nil];
    (void) [wl evaluateWithNSString:[RAREiConstants EVENT_SUBMIT] withJavaUtilEventObject:e withBoolean:NO];
    if ([e isConsumed]) {
      return;
    }
  }
  RAREActionEvent *e = [[RAREActionEvent alloc] initWithId:[self getDataComponent] withNSString:@"submitForm"];
  if (listenerList_ != nil) {
    [RAREUtils fireActionEventWithRAREEventListenerList:listenerList_ withRAREActionEvent:e];
  }
  if ((actionListener_ != nil) && (cb == nil) && (actionListener_ != formLink_)) {
    [actionListener_ actionPerformedWithRAREActionEvent:e];
  }
  else if (formLink_ != nil) {
    @try {
      (void) [((RAREWindowViewer *) nil_chk([((id<RAREiPlatformAppContext>) nil_chk([self getAppContext])) getWindowViewer])) getContentAsStringWithId:formLink_ withRAREiFunctionCallback:cb];
    }
    @catch (JavaNetMalformedURLException *ex) {
      [RAREPlatform ignoreExceptionWithNSString:nil withJavaLangThrowable:ex];
    }
  }
}

- (void)writeHTTPContentWithBoolean:(BOOL)first
                   withJavaIoWriter:(JavaIoWriter *)writer
                       withNSString:(NSString *)boundary {
  [self writeHTTPContentWithBoolean:first withJavaIoWriter:writer withNSString:boundary withJavaUtilMap:nil];
}

- (void)writeHTTPContentWithBoolean:(BOOL)first
                   withJavaIoWriter:(JavaIoWriter *)writer
                       withNSString:(NSString *)boundary
                    withJavaUtilMap:(id<JavaUtilMap>)attributes {
  @try {
    if (attributes != nil) {
      [RAREFormHelper writeHTTPContentWithBoolean:first withJavaIoWriter:writer withNSString:boundary withJavaUtilMap:attributes];
    }
    if (submitAttributes_ != nil) {
      [RAREFormHelper writeHTTPContentWithBoolean:first withJavaIoWriter:writer withNSString:boundary withJavaUtilMap:submitAttributes_];
    }
    int len = [((RAREUTIdentityArrayList *) nil_chk(widgetList_)) size];
    id<RAREiWidget> a;
    for (int i = 0; i < len; i++) {
      a = [widgetList_ getWithInt:i];
      if ([((id<RAREiWidget>) nil_chk(a)) isSubmittable] && [a isValidForSubmissionWithBoolean:YES]) {
        [a writeHTTPContentWithBoolean:first withJavaIoWriter:writer withNSString:boundary];
      }
    }
  }
  @catch (JavaLangException *e) {
    @throw [RAREApplicationException runtimeExceptionWithJavaLangThrowable:e];
  }
}

- (BOOL)writeHTTPValueWithBoolean:(BOOL)first
                 withJavaIoWriter:(JavaIoWriter *)writer {
  return [self writeHTTPValueWithBoolean:first withJavaIoWriter:writer withJavaUtilMap:nil];
}

- (BOOL)writeHTTPValueWithBoolean:(BOOL)first
                 withJavaIoWriter:(JavaIoWriter *)writer
                  withJavaUtilMap:(id<JavaUtilMap>)attributes {
  @try {
    if ((attributes != nil) && [RAREFormHelper writeHTTPValuesWithBoolean:first withJavaIoWriter:writer withJavaUtilMap:attributes withBoolean:YES]) {
      first = NO;
    }
    if ((submitAttributes_ != nil) && [RAREFormHelper writeHTTPValuesWithBoolean:first withJavaIoWriter:writer withJavaUtilMap:submitAttributes_ withBoolean:YES]) {
      first = NO;
    }
    int len = [((RAREUTIdentityArrayList *) nil_chk(widgetList_)) size];
    id<RAREiWidget> a;
    for (int i = 0; i < len; i++) {
      a = [widgetList_ getWithInt:i];
      if ([((id<RAREiWidget>) nil_chk(a)) isSubmittable] && [a isValidForSubmissionWithBoolean:YES]) {
        if ([a writeHTTPValueWithBoolean:first withJavaIoWriter:writer]) {
          first = NO;
        }
      }
    }
    return !first;
  }
  @catch (JavaLangException *e) {
    @throw [RAREApplicationException runtimeExceptionWithJavaLangThrowable:e];
  }
}

- (void)writeJSONValueWithRAREUTJSONWriter:(RAREUTJSONWriter *)writer {
  [self writeJSONValueWithRAREUTJSONWriter:writer withJavaUtilMap:nil];
}

- (void)writeJSONValueWithRAREUTJSONWriter:(RAREUTJSONWriter *)writer
                           withJavaUtilMap:(id<JavaUtilMap>)attributes {
  @try {
    int len;
    if (![self isEnabled]) {
      return;
    }
    NSString *name = [self getHTTPFormName];
    if (actAsFormViewer_ && (name != nil)) {
      (void) [((RAREUTJSONWriter *) nil_chk(writer)) keyWithNSString:name];
      (void) [writer object];
    }
    if (attributes != nil) {
      [RAREFormHelper writeJSONValuesWithRAREUTJSONWriter:writer withJavaUtilMap:attributes];
    }
    if (submitAttributes_ != nil) {
      [RAREFormHelper writeJSONValuesWithRAREUTJSONWriter:writer withJavaUtilMap:submitAttributes_];
    }
    len = [((RAREUTIdentityArrayList *) nil_chk(widgetList_)) size];
    id<RAREiWidget> a;
    for (int i = 0; i < len; i++) {
      a = [widgetList_ getWithInt:i];
      if ([((id<RAREiWidget>) nil_chk(a)) isSubmittable] && [a isValidForSubmissionWithBoolean:YES]) {
        [a writeJSONValueWithRAREUTJSONWriter:writer];
      }
    }
    if (actAsFormViewer_ && (name != nil)) {
      (void) [((RAREUTJSONWriter *) nil_chk(writer)) endObject];
    }
  }
  @catch (JavaLangException *e) {
    @throw [RAREApplicationException runtimeExceptionWithJavaLangThrowable:e];
  }
}

- (void)setSubmittAttributeWithNSString:(NSString *)name
                                 withId:(id)value {
  if (submitAttributes_ == nil) {
    submitAttributes_ = [[JavaUtilHashMap alloc] init];
  }
  (void) [((id<JavaUtilMap>) nil_chk(submitAttributes_)) putWithId:name withId:value];
}

- (void)setSubmittAttributesWithJavaUtilMap:(id<JavaUtilMap>)attributes {
  if (submitAttributes_ == nil) {
    submitAttributes_ = [[JavaUtilHashMap alloc] init];
  }
  [((id<JavaUtilMap>) nil_chk(submitAttributes_)) putAllWithJavaUtilMap:attributes];
}

- (id)getAttributeWithNSString:(NSString *)key {
  id o;
  o = [super getAttributeWithNSString:key];
  if (o != nil) {
    return o;
  }
  if (submitAttributes_ != nil) {
    o = [submitAttributes_ getWithId:key];
  }
  return o;
}

- (RAREActionLink *)getFormLink {
  return formLink_;
}

- (id)getSubmittAttributeWithNSString:(NSString *)name {
  return (submitAttributes_ == nil) ? nil : [submitAttributes_ getWithId:name];
}

- (BOOL)isRetainInitialWidgetValues {
  return retainInitialFieldValues_;
}

- (void)clearConfigurationWithBoolean:(BOOL)dispose {
  [super clearConfigurationWithBoolean:dispose];
  if (submitAttributes_ != nil) {
    [submitAttributes_ clear];
  }
  if (listenerList_ != nil) {
    [listenerList_ clear];
  }
  if (dispose) {
    if (formLink_ != nil) {
      [formLink_ clear];
    }
    formLink_ = nil;
    listenerList_ = nil;
    submitAttributes_ = nil;
    listenerList_ = nil;
  }
}

- (RAREActionLink *)createActionLinkWithRARESPOTLink:(RARESPOTLink *)link {
  formLink_ = [[RAREaFormViewer_FormLink alloc] initWithRAREaFormViewer:self withRARESPOTLink:link];
  return formLink_;
}

- (void)copyAllFieldsTo:(RAREaFormViewer *)other {
  [super copyAllFieldsTo:other];
  other->formLink_ = formLink_;
  other->listenerList_ = listenerList_;
  other->retainInitialFieldValues_ = retainInitialFieldValues_;
  other->submitAttributes_ = submitAttributes_;
}

- (NSUInteger)countByEnumeratingWithState:(NSFastEnumerationState *)state objects:(__unsafe_unretained id *)stackbuf count:(NSUInteger)len {
  return JreDefaultFastEnumeration(self, state, stackbuf, len);
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "getHTTPValuesHash", NULL, "LJavaUtilHashMap", 0x1, NULL },
    { "writeHTTPValueWithBoolean:withJavaIoWriter:", NULL, "Z", 0x1, NULL },
    { "writeHTTPValueWithBoolean:withJavaIoWriter:withJavaUtilMap:", NULL, "Z", 0x1, NULL },
    { "getAttributeWithNSString:", NULL, "LNSObject", 0x1, NULL },
    { "getFormLink", NULL, "LRAREActionLink", 0x1, NULL },
    { "getSubmittAttributeWithNSString:", NULL, "LNSObject", 0x1, NULL },
    { "isRetainInitialWidgetValues", NULL, "Z", 0x1, NULL },
    { "clearConfigurationWithBoolean:", NULL, "V", 0x4, NULL },
    { "createActionLinkWithRARESPOTLink:", NULL, "LRAREActionLink", 0x4, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "formLink_", NULL, 0x4, "LRAREActionLink" },
    { "listenerList_", NULL, 0x4, "LRAREEventListenerList" },
    { "retainInitialFieldValues_", NULL, 0x4, "Z" },
    { "submitAttributes_", NULL, 0x4, "LJavaUtilMap" },
  };
  static J2ObjcClassInfo _RAREaFormViewer = { "aFormViewer", "com.appnativa.rare.viewer", NULL, 0x1, 9, methods, 4, fields, 0, NULL};
  return &_RAREaFormViewer;
}

@end
@implementation RAREaFormViewer_FormLink

- (id)initWithRAREaFormViewer:(RAREaFormViewer *)outer$
             withRARESPOTLink:(RARESPOTLink *)link {
  this$0_ = outer$;
  return [super initWithRAREUTiURLResolver:outer$ withRARESPOTLink:link];
}

- (void)actionPerformedWithRAREActionEvent:(RAREActionEvent *)e {
  [self run];
}

- (void)close {
  dataWritten_ = NO;
  [super close];
}

- (void)writeFormData {
  if (!dataWritten_) {
    [self setMultiPartFormWithBoolean:this$0_->hasMultipartContent_ || (this$0_->submissionFiles_ != nil)];
    @try {
      JavaIoWriter *w = [self openForOutput];
      if ([self getRequestEncoding] == [RAREActionLink_RequestEncodingEnum JSON]) {
        RAREUTJSONWriter *jw = [[RAREUTJSONWriter alloc] initWithJavaIoWriter:w];
        (void) [jw object];
        [this$0_ writeJSONValueWithRAREUTJSONWriter:jw withJavaUtilMap:[self getAttributes]];
        (void) [jw endObject];
      }
      else if ([self isMultiPartForm]) {
        [this$0_ writeHTTPContentWithBoolean:YES withJavaIoWriter:w withNSString:[self getPartBoundary] withJavaUtilMap:[self getAttributes]];
        [RAREFormHelper writeBoundaryEndWithJavaIoWriter:w withNSString:[self getPartBoundary]];
      }
      else {
        [this$0_ writeHTTPValueWithBoolean:YES withJavaIoWriter:w withJavaUtilMap:[self getAttributes]];
        [((JavaIoWriter *) nil_chk(w)) writeWithNSString:@"\n"];
      }
    }
    @finally {
      dataWritten_ = YES;
    }
  }
}

- (void)closeOutput {
  if (!dataWritten_) {
    [self writeFormData];
  }
  [super closeOutput];
}

- (void)copyAllFieldsTo:(RAREaFormViewer_FormLink *)other {
  [super copyAllFieldsTo:other];
  other->dataWritten_ = dataWritten_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "initWithRAREaFormViewer:withRARESPOTLink:", NULL, NULL, 0x0, NULL },
    { "writeFormData", NULL, "V", 0x1, "JavaIoIOException" },
    { "closeOutput", NULL, "V", 0x4, "JavaIoIOException" },
  };
  static J2ObjcFieldInfo fields[] = {
    { "this$0_", NULL, 0x1012, "LRAREaFormViewer" },
    { "dataWritten_", NULL, 0x0, "Z" },
  };
  static J2ObjcClassInfo _RAREaFormViewer_FormLink = { "FormLink", "com.appnativa.rare.viewer", "aFormViewer", 0x2, 3, methods, 2, fields, 0, NULL};
  return &_RAREaFormViewer_FormLink;
}

@end
