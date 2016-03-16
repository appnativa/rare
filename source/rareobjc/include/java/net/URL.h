//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: java/net/URL.java
//
//  Created by decoteaud on 1/20/16.
//

#ifndef _JavaNetURL_H_
#define _JavaNetURL_H_

@class JavaIoInputStream;
@class JavaNetURLConnection;
@class JavaNetURLStreamHandler;
@class JavaUtilConcurrentConcurrentHashMap;
@protocol JavaNetURLStreamHandlerFactory;
@protocol RAREiStreamHandler;

#import "JreEmulation.h"

@interface JavaNetURL : NSObject {
@public
  id proxy_;
  id<RAREiStreamHandler> streamHandler_;
  JavaNetURLStreamHandler *urlStreamHandler_;
}

+ (id)streamHandlerLock;
+ (id<JavaNetURLStreamHandlerFactory>)factory;
+ (void)setFactory:(id<JavaNetURLStreamHandlerFactory>)factory;
+ (JavaUtilConcurrentConcurrentHashMap *)handlers;
+ (void)setHandlers:(JavaUtilConcurrentConcurrentHashMap *)handlers;
- (id)initWithId:(id)nsurl;
- (id)initWithNSString:(NSString *)str;
- (id)initWithJavaNetURL:(JavaNetURL *)baseURL
            withNSString:(NSString *)relativeStr;
- (id)initWithNSString:(NSString *)protocol
          withNSString:(NSString *)host
               withInt:(int)port
          withNSString:(NSString *)file;
- (id)initWithNSString:(NSString *)protocol
          withNSString:(NSString *)host
          withNSString:(NSString *)file;
- (id)initWithNSString:(NSString *)protocol
          withNSString:(NSString *)host
               withInt:(int)port
          withNSString:(NSString *)file
withJavaNetURLStreamHandler:(JavaNetURLStreamHandler *)sh;
- (JavaNetURLConnection *)openConnection;
- (int)getDefaultPort;
- (JavaIoInputStream *)openStream;
- (BOOL)sameFileWithJavaNetURL:(JavaNetURL *)other;
- (BOOL)sameFileExWithJavaNetURL:(JavaNetURL *)other;
- (NSString *)toExternalForm;
- (NSString *)toExternalFormEx;
- (NSString *)description;
- (NSString *)getAuthority;
- (id)getContent;
- (NSString *)getFile;
- (NSString *)getFileEx;
- (NSString *)getHost;
- (NSString *)getHosEx;
- (NSString *)getPath;
- (NSString *)getPathEx;
- (id)getNSURL;
- (int)getPort;
- (NSString *)getProtocol;
- (NSString *)getProtocolEx;
- (NSString *)getQuery;
- (NSString *)getQueryEx;
- (NSString *)getRef;
+ (void)setURLStreamHandlerFactoryWithJavaNetURLStreamHandlerFactory:(id<JavaNetURLStreamHandlerFactory>)fac;
+ (JavaNetURLStreamHandler *)getURLStreamHandlerWithNSString:(NSString *)protocol;
- (void)resolveStreamHandler;
- (NSString *)getRefEx;
- (NSString *)getUserInfo;
- (void)initialize__WithNSString:(NSString *)str OBJC_METHOD_FAMILY_NONE;
- (BOOL)isSimulator;
- (void)initialize__WithJavaNetURL:(JavaNetURL *)url
                      withNSString:(NSString *)str OBJC_METHOD_FAMILY_NONE;
- (void)copyAllFieldsTo:(JavaNetURL *)other;
@end

J2OBJC_FIELD_SETTER(JavaNetURL, proxy_, id)
J2OBJC_FIELD_SETTER(JavaNetURL, streamHandler_, id<RAREiStreamHandler>)
J2OBJC_FIELD_SETTER(JavaNetURL, urlStreamHandler_, JavaNetURLStreamHandler *)

#endif // _JavaNetURL_H_
