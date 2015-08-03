//
//  RAREURLConnectionStream.h
//  RareTOUCH
//
//  Created by Don DeCoteau on 2/16/14.
//  Copyright (c) 2014 SparseWare. All rights reserved.
//

#import <Foundation/Foundation.h>
@protocol RAREiFunctionCallback;
@class JavaIoInputStream;

@interface RAREURLConnectionHandler : NSObject <NSURLConnectionDataDelegate,NSStreamDelegate>
-(void)dispose;
-(void)cancel;
-(NSURLResponse*) getResponse;
-(NSError*) getError;
-(NSData*) getData;
-(void) start;
-(void)sendURL:(NSURL *)url callback: (id<RAREiFunctionCallback>) callback manualStart: (BOOL) manualStart;
-(void)sendRequest:(NSURLRequest *)request callback: (id<RAREiFunctionCallback>) callback manualStart: (BOOL) manualStart;
@end
