//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/net/JavaURLConnection.java
//
//  Created by decoteaud on 12/8/15.
//

#ifndef _RAREJavaURLConnection_H_
#define _RAREJavaURLConnection_H_

@class IOSClass;
@class IOSObjectArray;
@class JavaIoInputStream;
@class JavaIoOutputStream;
@class JavaIoReader;
@class JavaNetURL;
@class JavaNetURLConnection;
@class JavaUtilConcurrentLocksReentrantLock;
@class RAREUTIdentityArrayList;
@protocol JavaUtilMap;
@protocol RAREiConnectionChecker;

#import "JreEmulation.h"
#include "com/appnativa/rare/net/iURLConnection.h"

@interface RAREJavaURLConnection : NSObject < RAREiURLConnection > {
 @public
  JavaNetURLConnection *aConnection_;
  NSString *charSet_;
  JavaIoInputStream *inputStream_;
  NSString *mimeType_;
  NSString *outputUri_;
  NSString *defaultCharset_;
  BOOL handleRedirection_;
  BOOL connected_;
  JavaNetURL *url301_;
}

+ (int)MAX_REDIRECTS;
+ (int *)MAX_REDIRECTSRef;
+ (BOOL)disableHTTPSKeepAlive;
+ (BOOL *)disableHTTPSKeepAliveRef;
+ (BOOL)disableKeepAlive;
+ (BOOL *)disableKeepAliveRef;
+ (id<RAREiConnectionChecker>)connectionChecker;
+ (void)setConnectionChecker:(id<RAREiConnectionChecker>)connectionChecker;
+ (BOOL)connectionCheckerEnabled;
+ (BOOL *)connectionCheckerEnabledRef;
+ (NSString *)httpAgent;
+ (long long int)lastConnectionSuccess;
+ (long long int *)lastConnectionSuccessRef;
+ (RAREUTIdentityArrayList *)openConnections;
+ (void)setOpenConnections:(RAREUTIdentityArrayList *)openConnections;
+ (JavaUtilConcurrentLocksReentrantLock *)openLock;
+ (void)setOpenLock:(JavaUtilConcurrentLocksReentrantLock *)openLock;
+ (BOOL)trackOpenConnections;
+ (BOOL *)trackOpenConnectionsRef;
- (id)initWithJavaNetURLConnection:(JavaNetURLConnection *)conn;
- (id)initWithJavaNetURLConnection:(JavaNetURLConnection *)conn
                      withNSString:(NSString *)userInfo;
- (id)initWithJavaNetURLConnection:(JavaNetURLConnection *)conn
                      withNSString:(NSString *)userInfo
                      withNSString:(NSString *)mimeType;
+ (NSString *)baseToExternalFormWithJavaNetURL:(JavaNetURL *)url;
- (void)close;
+ (void)closeOpenConnectionsWithBoolean:(BOOL)debug;
+ (IOSObjectArray *)getOpenConnections;
- (void)dispose;
+ (void)disposeWithJavaNetURLConnection:(JavaNetURLConnection *)con;
- (BOOL)exist;
- (void)open;
+ (NSString *)parenToExternalFormWithJavaNetURL:(JavaNetURL *)url;
+ (NSString *)toExternalFormWithJavaNetURL:(JavaNetURL *)url;
+ (NSString *)toInternalFormWithJavaNetURL:(JavaNetURL *)url;
- (void)setCharsetWithNSString:(NSString *)cs;
+ (void)setConnectionCheckerEnabledWithBoolean:(BOOL)enabled;
- (void)setDefaultCharsetWithNSString:(NSString *)charset;
- (void)setHandleRedirectionWithBoolean:(BOOL)handleRedirection;
- (void)setHeaderFieldWithNSString:(NSString *)name
                      withNSString:(NSString *)value;
- (void)setReadTimeoutWithInt:(int)milliseconds;
+ (void)setTrackOpenConnectionsWithBoolean:(BOOL)trackOpenConnections;
- (NSString *)getCharset;
+ (NSString *)getCharsetWithNSString:(NSString *)mime
                        withNSString:(NSString *)defcharset;
- (id)getConnectionObject;
- (IOSClass *)getConnectionObjectClass;
- (id)getContent;
- (NSString *)getContentAsString;
- (NSString *)getContentEncoding;
- (int)getContentLength;
- (NSString *)getContentType;
- (JavaNetURL *)getHTTP301URL;
- (NSString *)getHeaderFieldWithNSString:(NSString *)name;
+ (NSString *)getHeaderFieldWithRAREiURLConnection:(id<RAREiURLConnection>)conn
                                      withNSString:(NSString *)name;
- (id<JavaUtilMap>)getHeaderFields;
+ (id<JavaUtilMap>)getHeaderFieldsWithRAREiURLConnection:(id<RAREiURLConnection>)conn;
- (JavaIoInputStream *)getInputStream;
+ (long long int)getLastConnectionSuccessTime;
- (long long int)getLastModified;
- (JavaIoOutputStream *)getOutputStream;
- (NSString *)getPassedInMimeType;
- (int)getReadTimeout;
- (JavaIoReader *)getReader;
- (int)getResponseCode;
- (JavaNetURL *)getURL;
+ (NSString *)getWindowsDrivePartWithJavaNetURL:(JavaNetURL *)url;
- (BOOL)isConnected;
- (BOOL)isHandleRedirection;
+ (BOOL)isTrackOpenConnections;
- (void)connectAndCheckForError;
+ (void)connectionClosedWithRAREJavaURLConnection:(RAREJavaURLConnection *)conn;
+ (void)connectionOpenedWithRAREJavaURLConnection:(RAREJavaURLConnection *)conn;
+ (BOOL)isWindows;
- (void)copyAllFieldsTo:(RAREJavaURLConnection *)other;
@end

J2OBJC_FIELD_SETTER(RAREJavaURLConnection, aConnection_, JavaNetURLConnection *)
J2OBJC_FIELD_SETTER(RAREJavaURLConnection, charSet_, NSString *)
J2OBJC_FIELD_SETTER(RAREJavaURLConnection, inputStream_, JavaIoInputStream *)
J2OBJC_FIELD_SETTER(RAREJavaURLConnection, mimeType_, NSString *)
J2OBJC_FIELD_SETTER(RAREJavaURLConnection, outputUri_, NSString *)
J2OBJC_FIELD_SETTER(RAREJavaURLConnection, defaultCharset_, NSString *)
J2OBJC_FIELD_SETTER(RAREJavaURLConnection, url301_, JavaNetURL *)

typedef RAREJavaURLConnection ComAppnativaRareNetJavaURLConnection;

#endif // _RAREJavaURLConnection_H_
