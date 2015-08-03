//
// Created by Don DeCoteau on 9/20/13.
// Copyright (c) 2013 SparseWare. All rights reserved.
//
// To change the template use AppCode | Preferences | File Templates.
//


#import "RARESocket.h"


@implementation RARESocket {

  NSInputStream *inputStream;
  NSOutputStream *outputStream;
}

@synthesize inputStream;

@synthesize outputStream;

- (BOOL)openWithHost: (NSString*) host andPort: (int) port {

  CFReadStreamRef readStream;
  CFWriteStreamRef writeStream;
  CFStreamCreatePairWithSocketToHost(kCFAllocatorDefault, (__bridge CFStringRef) host, port, &readStream, &writeStream);

  if(!CFWriteStreamOpen(writeStream)) {
    return NO;
  }

  inputStream = (__bridge_transfer NSInputStream *)readStream;
  outputStream = (__bridge_transfer NSOutputStream *)writeStream;

  [inputStream setDelegate:self];
  [outputStream setDelegate:self];

  [inputStream scheduleInRunLoop:[NSRunLoop currentRunLoop] forMode:NSDefaultRunLoopMode];
  [outputStream scheduleInRunLoop:[NSRunLoop currentRunLoop] forMode:NSDefaultRunLoopMode];

  [inputStream open];
  [outputStream open];
  return YES;
}

- (void)close {
  if(outputStream) {
    [inputStream close];
    [outputStream close];

    [inputStream removeFromRunLoop:[NSRunLoop currentRunLoop] forMode:NSDefaultRunLoopMode];
    [outputStream removeFromRunLoop:[NSRunLoop currentRunLoop] forMode:NSDefaultRunLoopMode];

    [inputStream setDelegate:nil];
    [outputStream setDelegate:nil];

    inputStream = nil;
    outputStream = nil;
  }
}

- (void)stream:(NSStream *)stream handleEvent:(NSStreamEvent)event {

  switch(event) {
    case NSStreamEventHasSpaceAvailable: {
      break;
    }
    case NSStreamEventHasBytesAvailable: {
      if(stream == inputStream) {
      }
      break;
    }
    default: {
//      NSLog(@"Stream is sending an Event: %i", event);

      break;
    }
  }
}
@end