//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/testing/mockito/build_result/java/org/mockito/internal/verification/checkers/AtLeastDiscrepancy.java
//
//  Created by tball on 11/23/13.
//

#ifndef _OrgMockitoInternalVerificationCheckersAtLeastDiscrepancy_H_
#define _OrgMockitoInternalVerificationCheckersAtLeastDiscrepancy_H_

#import "JreEmulation.h"
#include "org/mockito/internal/reporting/Discrepancy.h"

@interface OrgMockitoInternalVerificationCheckersAtLeastDiscrepancy : OrgMockitoInternalReportingDiscrepancy {
}

- (id)initWithInt:(int)wantedCount
          withInt:(int)actualCount;
- (NSString *)getPluralizedWantedCount;
@end

#endif // _OrgMockitoInternalVerificationCheckersAtLeastDiscrepancy_H_