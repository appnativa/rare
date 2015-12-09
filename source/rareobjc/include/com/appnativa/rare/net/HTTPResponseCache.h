//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/net/HTTPResponseCache.java
//
//  Created by decoteaud on 12/8/15.
//

#ifndef _RAREHTTPResponseCache_H_
#define _RAREHTTPResponseCache_H_

@class JavaIoFile;
@class JavaIoInputStream;

#import "JreEmulation.h"

@interface RAREHTTPResponseCache : NSObject {
}

+ (NSString *)defaultCache;
+ (void)setDefaultCache:(NSString *)defaultCache;
+ (void)cacheDataWithNSString:(NSString *)name
        withJavaIoInputStream:(JavaIoInputStream *)inArg;
+ (void)clearResponseCache;
+ (JavaIoFile *)createCacheFileWithNSString:(NSString *)name;
+ (void)deleteCachedDataWithNSString:(NSString *)name;
+ (void)installResponseCacheWithNSString:(NSString *)name
                                 withInt:(int)mbMaxSize
                             withBoolean:(BOOL)deleteOnExit;
+ (JavaIoInputStream *)getCachedDataWithNSString:(NSString *)name;
+ (NSString *)createFileNameWithNSString:(NSString *)dir
                            withNSString:(NSString *)name;
- (id)init;
@end

typedef RAREHTTPResponseCache ComAppnativaRareNetHTTPResponseCache;

#endif // _RAREHTTPResponseCache_H_
