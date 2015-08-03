//
//  IOSHelper.h
//  porting
//
//  Created by Don DeCoteau on 1/14/13.
//  Copyright (c) 2013 Don DeCoteau. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "IOSObjectArray.h"
#import "java/util/Date.h"
#import "java/util/Locale.h"
#import "java/util/Map.h"
#import "java/util/ArrayList.h"
#import "java/lang/Runnable.h"
#import "java/io/InputStream.h"
#import "java/net/URL.h"
#import "java/util/concurrent/Callable.h"
#import "java/util/concurrent/TimeUnit.h"
#import "com/appnativa/rare/ui/UIRectangle.h"
#import "com/appnativa/rare/ui/UIRectangle.h"
#if TARGET_OS_IPHONE
#import <UIKit/UIKit.h>
#else
#import <AppKit/AppKit.h>
#endif


@class IOSObjectArray;
@class IOSFloatArray;
@class IOSIntArray;
@class RAREUIPoint;
@class RAREUIBackgroundPainter;
@class RARERenderTypeEnum;
@class RAREUIColor;
@protocol RAREiBackgroundPainter;
@protocol RAREiPlatformPainter;

@interface AppleHelper : NSObject
+(void) setLayerBackgroundPainter:(id<RAREiBackgroundPainter>) painter onLayer: (CALayer*) l withBackground: (RAREUIColor*) bg;
+(void) setLayerPainter:(id<RAREiPlatformPainter>) painter onLayer: (CALayer*) layer withBackground: (RAREUIColor*) bg;
+(void) setLayerImage:(id) uiimage onLayer: (CALayer*) layer renderType: (RARERenderTypeEnum*) renderType ;
+(NSAttributedString *) createUnderlinedString: (id <JavaLangCharSequence>)text;
+ (CATransform3D)rectToQuadWithX: (double) X Y: (double) Y width:(double)W height: (double) H quadTLX:(double)x1a quadTLY:(double)y1a quadTRX:(double)x2a quadTRY:(double)y2a quadBLX:(double)x3a quadBLY:(double)y3a quadBRX:(double)x4a quadBRY:(double)y4a;
+(NSString*) formatString:(NSString*) format withObjectArrays: (IOSObjectArray*) args;
+(NSString*) formatString:(NSString*) format withArray: (NSArray*) args;
+(BOOL) isLTRText;
+ (IOSClass*) loadClass: (NSString*) name;
#if TARGET_OS_IPHONE
+(int) colorToInt :(UIColor*) color;
+ (id)quartzPath: (UIBezierPath*) path;
#else
+(int) colorToInt :(NSColor*) color;
+ (id)quartzPath: (NSBezierPath*) path;
#endif
+ (id) getDiffecencePathBetweenRect: (CGRect) rect1 and: (CGRect) rect2;
+ (void)invokeLater: (id<JavaLangRunnable>)r;
+ (void)invokeLater: (id<JavaLangRunnable>)r delay:(int) delay;
+ (void)invokeAndWait: (id<JavaLangRunnable>)r;
+(CGColorRef) clearColor;
+(NSString*) getBundleFileName: (NSString*)name ofType: (NSString*)type;
+(NSArray*) toNSArray: (IOSObjectArray*) array;
+(IOSObjectArray*) toStringArray: (NSArray*) array;
+(NSLocale*) toNSLocale: (JavaUtilLocale*) locale;
+(NSString*) toNSString: (NSData*) data encoding: (NSString*) enc;
+(void) populateMap: (id<JavaUtilMap>) map withDictionary: (NSDictionary*) data;
+(NSDictionary*) toDictionary: (NSObject<JavaUtilMap>*) map;
+(NSString*) createTempFileWithPrefix: (NSString*) prefix AndExt: (NSString*) ext;
+(NSString*) toErrorString: (NSError*) error;
+(JavaUtilArrayList*) toArrayList: (NSArray*) array;
+(void) setHTTPHeaders: (NSDictionary*) data : (NSObject<JavaUtilMap>*) map;
+(id) createGradientWithColors: (IOSObjectArray*) uicolors distribution: (IOSFloatArray*) distribution;
+(void) drawLinearGradient: (id) nativeGradient context:context x: (float)x  y: (float) y width:(float) width height: (float) height angle: (float) angle;
+(void) drawRadialGradient: (id) nativeGradient context: (id) ctx width:(float) width height: (float) height cx: (float) cx cy: (float) cy radius: (float) radius;
+(void)drawGradient:(id) nativeGradient context:context fromCenter: (RAREUIPoint*) startCenter radius:(float) radius toCenter:(RAREUIPoint*)endCenter radius:(float) endRadius;
+(void)drawGradient: (id) nativeGRadient context:context fromPoint:(RAREUIPoint*)startingPoint toPoint:(RAREUIPoint*)endingPoint;
+(NSInteger) getPixelAtX: (int) x y: (int) y bitmapData: (unsigned char*) data imageSize: (CGSize) size;
+(void) getPixels: (IOSIntArray*) pixels x: (int) x y: (int)y width: (int) width height: (int) height bitmapData: (unsigned char*) data imageSize: (CGSize) size;
+(void) setPixels: (IOSIntArray*) pixels x: (int) x y: (int)y width: (int) width height: (int) height bitmapData: (unsigned char*) data imageSize: (CGSize) size;
+(void) setPixelAtX: (int) x y: (int) y color: (int) color bitmapData: (unsigned char*) data imageSize: (CGSize) size;
+(void) setClipMaskWithLayer: (CALayer*) layer withPath: (NSObject*) nativepath;
+(id) getInvocationReturnValue: (NSInvocation*) invocation methodSignature: (NSMethodSignature *) signature;
+(id) invokeSelector: (SEL) sel onTarget: (NSObject*) object;
+(id) invokeSelector: (SEL) sel onTarget: (NSObject*) object withArg: (NSObject*) arg;
+ (NSStringEncoding) encodingFromString: (NSString*) charset;
+(NSString*) stringFromEncoding: (NSStringEncoding) encoding;
@end

@interface RAREWeakReference : NSObject {
  __weak NSObject* data;
}
-(id) initWithNSObject: (NSObject*) value;
- (NSObject*) getValue ;
-(void) clear;
@end

@interface RARECachingNSData : NSObject {
 NSMutableData* data;
  NSOutputStream* stream;
  NSString* filename;
  int maxMemorySize;
  BOOL retainFile;
}
-(id) initWithBufferSize: (int)size maxSize: (int)maxSize;
-(void) writeWithIOSByteArray: (IOSByteArray*) b offset: (int)offset length: (int)len;
-(void) writeWithInt: (int) b;
-(BOOL) streamWasRequired;
-(NSInputStream*) createStream;
-(NSData*) getData;
-(void) setRetainFile: (BOOL) retain;
@end

@interface IOSURLConnection : NSObject <NSURLConnectionDelegate>

-(NSData*) postData: (NSString*) strData;

//delegate method
- (void)connectionDidFinishLoading:(NSURLConnection *)connection;
- (void)connection:(NSURLConnection *)connection didCancelAuthenticationChallenge:(NSURLAuthenticationChallenge *)challenge;
- (void)connection:(NSURLConnection *)connection didFailWithError:(NSError *)error;
- (void)connection:(NSURLConnection *)connection didSendBodyData:(NSInteger)bytesWritten totalBytesWritten:(NSInteger)totalBytesWritten totalBytesExpectedToWrite:(NSInteger)totalBytesExpectedToWrite;
- (void)connection:(NSURLConnection *)connection didReceiveAuthenticationChallenge:(NSURLAuthenticationChallenge *)challenge;
- (void)connection:(NSURLConnection *)connection didReceiveData:(NSData *)data;
- (void)connection:(NSURLConnection *)connection didReceiveResponse:(NSURLResponse *)response;
- (NSCachedURLResponse *)connection:(NSURLConnection *)connection willCacheResponse:(NSCachedURLResponse *)cachedResponse;
- (NSURLRequest *)connection:(NSURLConnection *)connection willSendRequest:(NSURLRequest *)request redirectResponse:(NSURLResponse *)redirectResponse;
- (BOOL)connection:(NSURLConnection *)connection canAuthenticateAgainstProtectionSpace:(NSURLProtectionSpace *)protectionSpace;
- (BOOL)connectionShouldUseCredentialStorage:(NSURLConnection *)connection;
@end

@interface RAREUIRectangle (SparRect2D)
#if TARGET_OS_IPHONE
-(void) setWithRect: (CGRect) rect;
-(CGRect) toRect;
+(id) fromRect: (CGRect) rect;
#else
-(void) setWithRect: (NSRect) rect;
-(NSRect) toRect;
+(id) fromRect: (NSRect) rect;
#endif
@end

@interface RAREUIRectangle (SparRect)
#if TARGET_OS_IPHONE
-(void) setWithRect: (CGRect) rect;
-(CGRect) toRect;
+(id) fromRect: (CGRect) rect;
#else
-(void) setWithRect: (NSRect) rect;
-(NSRect) toRect;
+(id) fromRect: (NSRect) rect;
#endif
@end

@interface NSIndexSet (SparIndexSet)
- (IOSIntArray *)toIntArray;
+ (NSIndexSet*) fromIntArray: (IOSIntArray *) array;
@end
