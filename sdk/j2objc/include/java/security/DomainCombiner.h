//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: android/libcore/luni/src/main/java/java/security/DomainCombiner.java
//
//  Created by tball on 11/23/13.
//

#ifndef _JavaSecurityDomainCombiner_H_
#define _JavaSecurityDomainCombiner_H_

@class IOSObjectArray;

#import "JreEmulation.h"

@protocol JavaSecurityDomainCombiner < NSObject, JavaObject >
- (IOSObjectArray *)combineWithJavaSecurityProtectionDomainArray:(IOSObjectArray *)current
                           withJavaSecurityProtectionDomainArray:(IOSObjectArray *)assigned;
@end

#endif // _JavaSecurityDomainCombiner_H_
