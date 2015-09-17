//
//  RAREImage.h
//  RareOSX
//
//  Created by Don DeCoteau on 5/3/13.
//  Copyright (c) 2013 SparseWare. All rights reserved.
//

#import <Foundation/Foundation.h>
@class RAREiImagePainter_ScalingTypeEnum;
@class  IOSIntArray;
@class RAREView;
@protocol RAREiPlatformIcon;
@protocol RAREiComposite;
@interface RAREImageWrapper : NSObject {
  UIImage* image;
@protected
  NSMutableDictionary *sliceCache;
  NSMutableData* imageData;
  BOOL dirtyData;
  NSString* resourceName_;
}
@property (nonatomic, retain) NSString* resourceName;

//
// The Quartz color reference that corresponds to the receiver's color.
//
@property (nonatomic, readonly) CGImageRef CGImage;

+ (RAREImageWrapper *)createImageFromView:(UIView *)view;

-(id)initWithContentsOfURL:(NSURL *)nsurl scale:(CGFloat)scale;
-(id)initWithNSData:(NSData *)data scale:(CGFloat)scale;
-(id)initWithSize: (CGSize)size;
-(id)initWithImage: (UIImage*)img;
-(void) blur;
-(RAREImageWrapper*) createReflectionVersionFromY: (int) y height: (int) height opacity: (int) opacity gap: (int) gap;
-(NSString*) getFailureString;
-(RAREImageWrapper*) copyImage;
-(RAREImageWrapper*) createDisabledVersion;
-(CGFloat) getWidth;
-(CGFloat) getHeight;
-(NSInteger) getPixelAtX: (int) x y: (int) y;
-(void) setPixelAtX: (int) x y: (int) y color: (int)color;
-(void) getPixels: (IOSIntArray*) pixels x: (int) x y: (int)y width: (int) width height: (int) height;
-(void) setPixels: (IOSIntArray*) pixels x: (int) x y: (int)y width: (int) width height: (int) height;
-(RAREImageWrapper*) getSubImageAtX: (int) x y: (int)y width: (int) width height: (int) height;
- (void)flip;
-(void) drawAtX: (int)x y: (int)y;
-(void) drawAtX: (int)x y: (int)y composite: (id<RAREiComposite>)composite;
-(void) drawInRect: (CGRect) dstRect;
-(void) drawInRect: (CGRect) dstRect source: (CGRect) srcRect scaling: (RAREiImagePainter_ScalingTypeEnum *)scalingType composite: (id<RAREiComposite>) composite drawFlipped: (BOOL) flipped;
-(RAREImageWrapper*) getScaledImageWithWidth: (int) width height: (int)height scalintType: (RAREiImagePainter_ScalingTypeEnum *)scalingType;
- (CGSize)size;
- (UIImage*) getImage;
+(UIImage*)  getImageFromIcon: (id<RAREiPlatformIcon>) icon forView: (RAREView*) view;
- (id)addReflectionVersionFromY:(int)y height:(int)height opacity:(int)opacity gap:(int)gap;
-(id) createContext;
+(UIImageOrientation) convertToImageOrientation: (int) rotation;

@end
