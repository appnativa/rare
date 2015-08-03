//
//  RAREURLConnectionStream.m
//  RareTOUCH
//
//  Created by Don DeCoteau on 2/16/14.
//  Copyright (c) 2014 SparseWare. All rights reserved.
//

#import "RAREURLConnectionHandler.h"
#include "com/appnativa/rare/iFunctionCallback.h"
#include "com/appnativa/rare/platform/PlatformHelper.h"
#import "AppleHelper.h"
#include "java/io/IOException.h"
#include "java/io/InputStream.h"

#define RARE_BUFFER_SIZE 4096
#define RARE_WAIT_TIMEOUT 600000
@implementation RAREURLConnectionHandler {
  NSURLResponse *response_;
  NSError* error_;
  NSString* errorString;
  NSMutableData* buffer_;
  NSObject* waiter_;
  NSURLConnection* connection_;
  id<RAREiFunctionCallback>  callback_;
  dispatch_semaphore_t semaphore_;
}

-(void)sendURL:(NSURL *)url callback: (id<RAREiFunctionCallback>) callback manualStart: (BOOL) manualStart
{
  callback_=callback;
  connection_=[[NSURLConnection alloc]initWithRequest:[NSURLRequest requestWithURL:url] delegate:self startImmediately:NO];
  if(!connection_) {
    callback_=nil;
    @throw [[JavaIoIOException alloc] initWithNSString:@"Could not create connection"];
  }
  if(!manualStart) {
    [self start];
  }
}
-(void)sendRequest:(NSURLRequest *)request callback: (id<RAREiFunctionCallback>) callback manualStart: (BOOL) manualStart
{
  callback_=callback;
  connection_=[[NSURLConnection alloc]initWithRequest:request delegate:self startImmediately:NO];
  if(!connection_) {
    callback_=nil;
    @throw [[JavaIoIOException alloc] initWithNSString:@"Could not create connection"];
  }
  if(!manualStart) {
    [self start];
  }
}
-(void)start {
  if(callback_) {
    [connection_ start];
  }
  else {
    semaphore_ = dispatch_semaphore_create(0);
    [connection_ setDelegateQueue:[RAREPlatformHelper getImagesBackgroundQueue]]; //use the images queue which never blocks
    [connection_ start];
    dispatch_semaphore_wait(semaphore_, DISPATCH_TIME_FOREVER);
  }
  
}

-(NSURLResponse*) getResponse {
  return response_;
}

-(NSError*) getError {
  return  error_;
}

-(NSData*) getData {
  return buffer_;
}


-(void)cancel {
  @try {
    NSURLConnection* c=connection_;
    connection_=nil;
    buffer_=nil;
    if(c) {
      [c cancel ];
    }
  }
  @catch (NSException *ignore) {
  }
  if(callback_) {
    [callback_ finishedWithBoolean:YES withId:self];
    callback_=nil;
    response_=nil;
    error_=nil;
  }
  else if(semaphore_){
    dispatch_semaphore_signal(semaphore_);
  }
}
-(void)dispose {
  @try {
    NSURLConnection* c=connection_;
    connection_=nil;
    if(c) {
      [c cancel ];
    }
    connection_=nil;
    callback_=nil;
    buffer_=nil;
    response_=nil;
    error_=nil;
  }
  @catch (NSException *ignore) {
  }
}
- (NSURLRequest *)connection:(NSURLConnection *)connection willSendRequest:(NSURLRequest *)request redirectResponse:(NSURLResponse *)response {
  return request;
}

- (void)connection:(NSURLConnection *)connection didReceiveResponse:(NSURLResponse *)response {
  response_=response;
  if(buffer_) {
    [buffer_ setLength:0];
  }
}

- (void)connection:(NSURLConnection *)connection didReceiveData:(NSData *)data {
  if(!buffer_) {
    buffer_=[NSMutableData dataWithCapacity:RARE_BUFFER_SIZE];
  }
  [buffer_ appendData:data];
}


- (NSCachedURLResponse *)connection:(NSURLConnection *)connection willCacheResponse:(NSCachedURLResponse *)cachedResponse {
  return cachedResponse;
}

- (void)connectionDidFinishLoading:(NSURLConnection *)connection {
  id<RAREiFunctionCallback>  cb=callback_;
  connection_=nil;
  
  if(cb) {
    [cb finishedWithBoolean:NO withId:self];
    callback_=nil;
    buffer_=nil;
    response_=nil;
    error_=nil;
  }
  else if(semaphore_){
    dispatch_semaphore_signal(semaphore_);
  }
}

- (void)connection:(NSURLConnection*)connection didFailWithError:(NSError*)error
{
  if(connection_) {
    connection_=nil;
    error_=error;
    if(callback_) {
      [callback_ finishedWithBoolean:YES withId:self];
      callback_=nil;
      buffer_=nil;
      response_=nil;
      error_=nil;
    }
    else if(semaphore_){
      dispatch_semaphore_signal(semaphore_);
    }
  }
}

@end
