//
//  RAREJREPatches.h
//  RareOSX
//
//  Created by Don DeCoteau on 8/16/13.
//  Copyright (c) 2013 SparseWare. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "IOSObjectArray.h"
#import "IOSReference.h"
#import <java/util/HashMap.h>
#import "java/nio/charset/Charset.h"

@interface IOSObjectArray (Patches)

@end
@interface IOSReference (Patches)

@end
@interface JavaNioCharsetCharset (Patches)
-(NSStringEncoding) getEncoding;
@end


