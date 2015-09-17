//
//  RAREJREPatches.m
//  RareOSX
//
//  Created by Don DeCoteau on 8/16/13.
//  Copyright (c) 2013 SparseWare. All rights reserved.
//

#import "RAREJREPatches.h"
#import <com/appnativa/rare/ui/table/TableComponent.h>
#import <com/appnativa/rare/viewer/TableViewer.h>
#import "AppleHelper.h"
@implementation IOSObjectArray (Patches)

@end
#pragma clang diagnostic push
#pragma clang diagnostic ignored "-Wobjc-protocol-method-implementation"
@implementation JavaNioCharsetCharset (Patches)
-(NSStringEncoding) getEncoding {
  return [AppleHelper encodingFromString:[self name]];
}
@end
@implementation IOSReference (Patches)
+ (void)initialize {
}
+ (void)initReferent:(JavaLangRefReference *)reference {
}
#pragma clang diagnostic pop

@end


