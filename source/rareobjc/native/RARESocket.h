//
// Created by Don DeCoteau on 9/20/13.
// Copyright (c) 2013 SparseWare. All rights reserved.
//
// To change the template use AppCode | Preferences | File Templates.
//


#import <Foundation/Foundation.h>


@interface RARESocket : NSObject <NSStreamDelegate>
@property(nonatomic, strong) NSInputStream *inputStream;

@property(nonatomic, strong) NSOutputStream *outputStream;

- (BOOL)openWithHost: (NSString*) host andPort: (int) port;
- (void)close;
- (void)stream:(NSStream *)stream handleEvent:(NSStreamEvent)event;

@end