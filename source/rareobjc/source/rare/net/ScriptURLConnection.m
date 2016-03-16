//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/net/ScriptURLConnection.java
//
//  Created by decoteaud on 3/11/16.
//

#include "IOSByteArray.h"
#include "IOSClass.h"
#include "com/appnativa/rare/Platform.h"
#include "com/appnativa/rare/iConstants.h"
#include "com/appnativa/rare/iPlatformAppContext.h"
#include "com/appnativa/rare/net/JavaURLConnection.h"
#include "com/appnativa/rare/net/NetHelper.h"
#include "com/appnativa/rare/net/ScriptURLConnection.h"
#include "com/appnativa/rare/scripting/WidgetContext.h"
#include "com/appnativa/rare/scripting/iScriptHandler.h"
#include "com/appnativa/rare/ui/aWidgetListener.h"
#include "com/appnativa/rare/ui/event/EventBase.h"
#include "com/appnativa/rare/viewer/iContainer.h"
#include "com/appnativa/rare/widget/iWidget.h"
#include "com/appnativa/util/UTF8Helper.h"
#include "java/io/ByteArrayInputStream.h"
#include "java/io/InputStream.h"
#include "java/io/Reader.h"
#include "java/io/StringReader.h"
#include "java/net/MalformedURLException.h"
#include "java/net/URL.h"

@implementation RAREScriptURLConnection

- (id)initWithJavaNetURL:(JavaNetURL *)url {
  if (self = [super initWithJavaNetURL:url]) {
    defaultCharset_ = @"ISO-8859-1";
    id<RAREiPlatformAppContext> app = [RAREPlatform getAppContext];
    NSString *data = [((JavaNetURL *) nil_chk(url)) getPath];
    scriptCode_ = [url getQuery];
    NSString *name = nil;
    int n = [((NSString *) nil_chk(data)) indexOf:'@'];
    if (n == -1) {
      n = [data indexOf:'~'];
      if (n != -1) {
        name = [data substring:n + 1];
      }
    }
    else {
      mimeType_ = [data substring:n + 1];
      int p = [data indexOf:'~'];
      if (p != -1) {
        name = [data substring:p + 1 endIndex:n];
      }
    }
    if (mimeType_ == nil) {
      mimeType_ = [RAREiConstants TEXT_MIME_TYPE];
    }
    if (app != nil) {
      if (name != nil) {
        contextWidget_ = [app getWidgetFromPathWithNSString:name];
      }
      if (contextWidget_ == nil) {
        contextWidget_ = [app getRootViewer];
      }
    }
  }
  return self;
}

- (id)initWithRAREiWidget:(id<RAREiWidget>)context
           withJavaNetURL:(JavaNetURL *)url
             withNSString:(NSString *)code
             withNSString:(NSString *)mime {
  if (self = [super initWithJavaNetURL:url]) {
    defaultCharset_ = @"ISO-8859-1";
    contextWidget_ = context;
    scriptCode_ = code;
    mimeType_ = mime;
    if ((mimeType_ == nil) || ([mimeType_ sequenceLength] == 0)) {
      mimeType_ = [RAREiConstants TEXT_MIME_TYPE];
    }
    eventObject_ = [[RAREEventBase alloc] initWithId:context];
  }
  return self;
}

- (void)close {
}

- (void)connect {
}

+ (JavaNetURL *)createURLWithRAREiWidget:(id<RAREiWidget>)context
                            withNSString:(NSString *)code
                            withNSString:(NSString *)mimeType {
  return [RAREaNetHelper createScriptURLWithRAREiWidget:context withNSString:code withNSString:mimeType];
}

- (void)disconnect {
}

- (void)dispose {
}

- (BOOL)exist {
  return YES;
}

- (void)open {
  [self connect];
}

- (void)setCharsetWithNSString:(NSString *)cs {
}

- (void)setDefaultCharsetWithNSString:(NSString *)charset {
  if (charset == nil) {
    charset = @"iso-8859-1";
  }
  defaultCharset_ = charset;
}

- (void)setHeaderFieldWithNSString:(NSString *)name
                      withNSString:(NSString *)value {
}

- (void)setReadTimeoutWithInt:(int)readTimeout {
  self->readTimeout_ScriptURLConnection_ = readTimeout;
}

- (NSString *)getCharset {
  return defaultCharset_;
}

- (id)getConnectionObject {
  return self;
}

- (IOSClass *)getConnectionObjectClass {
  return [self getClass];
}

- (id)getContent {
  returnValue_ = nil;
  (void) [self getContentEx];
  return returnValue_;
}

- (NSString *)getContentAsString {
  id o = [self getContent];
  return (o == nil) ? @"" : [o description];
}

- (NSString *)getContentType {
  return mimeType_;
}

- (NSString *)getHeaderFieldWithNSString:(NSString *)name {
  return [RAREJavaURLConnection getHeaderFieldWithRAREiURLConnection:self withNSString:name];
}

- (JavaIoInputStream *)getInputStream {
  return [[JavaIoByteArrayInputStream alloc] initWithByteArray:[((RAREUTUTF8Helper *) nil_chk([RAREUTUTF8Helper getInstance])) getBytesWithNSString:[self getContentAsString]]];
}

- (int)getReadTimeout {
  return readTimeout_ScriptURLConnection_;
}

- (JavaIoReader *)getReader {
  return [[JavaIoStringReader alloc] initWithNSString:[self getContentAsString]];
}

- (int)getResponseCode {
  return 200;
}

- (id)getContentEx {
  returnValue_ = nil;
  if (![((id<RAREiWidget>) nil_chk(contextWidget_)) isDesignMode] && (scriptCode_ != nil) && ([scriptCode_ sequenceLength] > 0)) {
    id<RAREiScriptHandler> sh = [contextWidget_ getScriptHandler];
    if (scriptObjectCode_ == nil) {
      scriptObjectCode_ = [((id<RAREiScriptHandler>) nil_chk(sh)) compileWithRAREWidgetContext:[contextWidget_ getScriptingContext] withNSString:[NSString stringWithFormat:@"%@.%@", [contextWidget_ getName], [RAREiConstants EVENT_FUNCTION_EVAL]] withNSString:scriptCode_];
    }
    returnValue_ = [RAREaWidgetListener evaluateWithRAREiWidget:contextWidget_ withRAREiScriptHandler:sh withId:scriptObjectCode_ withJavaUtilEventObject:eventObject_];
  }
  return returnValue_;
}

- (void)copyAllFieldsTo:(RAREScriptURLConnection *)other {
  [super copyAllFieldsTo:other];
  other->contextWidget_ = contextWidget_;
  other->defaultCharset_ = defaultCharset_;
  other->eventObject_ = eventObject_;
  other->mimeType_ = mimeType_;
  other->readTimeout_ScriptURLConnection_ = readTimeout_ScriptURLConnection_;
  other->returnValue_ = returnValue_;
  other->scriptCode_ = scriptCode_;
  other->scriptObjectCode_ = scriptObjectCode_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "createURLWithRAREiWidget:withNSString:withNSString:", NULL, "LJavaNetURL", 0x9, "JavaNetMalformedURLException" },
    { "exist", NULL, "Z", 0x1, NULL },
    { "getCharset", NULL, "LNSString", 0x1, NULL },
    { "getConnectionObject", NULL, "LNSObject", 0x1, NULL },
    { "getConnectionObjectClass", NULL, "LIOSClass", 0x1, NULL },
    { "getContent", NULL, "LNSObject", 0x1, NULL },
    { "getContentAsString", NULL, "LNSString", 0x1, NULL },
    { "getContentType", NULL, "LNSString", 0x1, NULL },
    { "getHeaderFieldWithNSString:", NULL, "LNSString", 0x1, NULL },
    { "getInputStream", NULL, "LJavaIoInputStream", 0x1, NULL },
    { "getReader", NULL, "LJavaIoReader", 0x1, NULL },
    { "getContentEx", NULL, "LNSObject", 0x2, NULL },
  };
  static J2ObjcClassInfo _RAREScriptURLConnection = { "ScriptURLConnection", "com.appnativa.rare.net", NULL, 0x1, 12, methods, 0, NULL, 0, NULL};
  return &_RAREScriptURLConnection;
}

@end
