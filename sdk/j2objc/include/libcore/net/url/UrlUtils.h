//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: android/libcore/luni/src/main/java/libcore/net/url/UrlUtils.java
//
//  Created by tball on 11/23/13.
//

#ifndef _LibcoreNetUrlUrlUtils_H_
#define _LibcoreNetUrlUrlUtils_H_

#import "JreEmulation.h"

@interface LibcoreNetUrlUrlUtils : NSObject {
}

- (id)init;
+ (NSString *)canonicalizePathWithNSString:(NSString *)path
                               withBoolean:(BOOL)discardRelativePrefix;
+ (NSString *)authoritySafePathWithNSString:(NSString *)authority
                               withNSString:(NSString *)path;
+ (NSString *)getSchemePrefixWithNSString:(NSString *)spec;
+ (BOOL)isValidSchemeCharWithInt:(int)index
                        withChar:(unichar)c;
+ (int)findFirstOfWithNSString:(NSString *)string
                  withNSString:(NSString *)chars
                       withInt:(int)start
                       withInt:(int)end;
@end

#endif // _LibcoreNetUrlUrlUtils_H_