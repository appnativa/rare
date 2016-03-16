//
//  RAREImage.m
//  RareOSX
//
//  Created by Don DeCoteau on 5/3/13.
//  Copyright (c) 2013 SparseWare. All rights reserved.
//

#import <com/appnativa/rare/Platform.h>
#import <com/appnativa/rare/platform/apple/AppContext.h>
#import "RAREImageWrapper.h"
#import "com/appnativa/rare/ui/painter/iImagePainter.h"
#import "com/appnativa/rare/ui/painter/iPainter.h"
#import "com/appnativa/rare/platform/apple/ui/util/AppleGraphics.h"
#import "com/appnativa/rare/exception/ApplicationException.h"
#import "com/appnativa/rare/ui/UIImageIcon.h"
#import "com/appnativa/rare/ui/UIImage.h"
#import "com/appnativa/rare/ui/iPlatformIcon.h"
#import "com/appnativa/rare/ui/iComposite.h"

#import "UIKit/UIKit.h"
#import "AppleHelper.h"
#import "RAREURLConnectionHandler.h"

@implementation RAREImageWrapper {
  CGSize imageSize;
  BOOL createdContext;
  CGContextRef context_;
  BOOL landscape;
  BOOL hasOrientatedVersion;
  NSString* failueString;
  CGLayerRef cgLayer;
}
@synthesize resourceName=resourceName_;

+ (RAREImageWrapper *)createImageFromView:(UIView *)view {
  CGRect frame=view.bounds;
  UIGraphicsBeginImageContext(frame.size);
  //  if([view respondsToSelector:@selector(drawViewHierarchyInRect:afterScreenUpdates:)])  {
  //    [view drawViewHierarchyInRect:frame afterScreenUpdates:NO];
  //  }
  //  else {
  [view.layer renderInContext:UIGraphicsGetCurrentContext()];
  //  }
  UIImage *img = UIGraphicsGetImageFromCurrentImageContext();
  UIGraphicsEndImageContext();
  return [[RAREImageWrapper alloc] initWithImage:img];
}

- (id)initWithContentsOfURL:(NSURL *)nsurl  scale: (CGFloat) scale{
  if (self = [super init]) {
    
    NSError* error=nil;
    NSDataReadingOptions ro;
    if([nsurl.scheme isEqualToString:@"file"] ) {
      ro=NSDataReadingUncached;
    }
    else {
      ro=NSDataReadingMappedIfSafe;
    }
    @try {
      NSData* data=[NSData dataWithContentsOfURL:nsurl options:ro error:&error];
      if(data) {
        image = [UIImage imageWithData: data scale: scale];
        imageSize = image ? image.size : CGSizeZero;
      }
      else {
        imageSize = CGSizeZero;
        if(error) {
          failueString=[AppleHelper toErrorString:error];
        }
      }
      landscape=UIInterfaceOrientationIsLandscape([UIApplication sharedApplication].statusBarOrientation);
    }
    @catch(NSException *ex) {
      imageSize = CGSizeZero;
      failueString=[ex description];
    }
    hasOrientatedVersion=YES;
  }
  
  return self;
}

+(UIImageOrientation) convertToImageOrientation: (int) rotation {
  UIImageOrientation o;
  switch(rotation) {
    case 90:
      o=UIImageOrientationLeft;
      break;
    case 180:
      o=UIImageOrientationDownMirrored;
      break;
    case 270:
      o=UIImageOrientationRight;
      break;
    default:
      o=UIImageOrientationUp;
      break;
  }
  return o;
}

- (id)initWithNSData:(NSData *)data scale:(CGFloat)scale {
  if (self = [super init]) {
    image = [UIImage imageWithData: data scale: scale];
    imageSize = image ? image.size : CGSizeZero;
    landscape=UIInterfaceOrientationIsLandscape([UIApplication sharedApplication].statusBarOrientation);
    hasOrientatedVersion=YES;
  }
  
  return self;
  
}

-(NSString*) getFailureString {
  return failueString;
}
- (id)initWithSize:(CGSize)size {
  if (self = [super init]) {
    UIGraphicsBeginImageContext(size);
    image = UIGraphicsGetImageFromCurrentImageContext();
    UIGraphicsEndImageContext();
    imageSize = image ? image.size : CGSizeZero;
    landscape=UIInterfaceOrientationIsLandscape([UIApplication sharedApplication].statusBarOrientation);
    hasOrientatedVersion=YES;
  }
  return self;
}

- (id)initWithImage:(UIImage *)img {
  if (self = [super init]) {
    image = img;
    imageSize = image ? image.size : CGSizeZero;
    landscape=UIInterfaceOrientationIsLandscape([UIApplication sharedApplication].statusBarOrientation);
    hasOrientatedVersion=YES;
  }
  return self;
}

- (CGFloat)getWidth {
  return imageSize.width;
}

- (CGFloat)getHeight {
  return imageSize.height;
}

- (CGSize)size {
  return imageSize;
}

- (NSInteger)getPixelAtX:(int)x y:(int)y {
  return [AppleHelper getPixelAtX:x y:y bitmapData:self.getPixelData imageSize:image.size];
}

- (void)getPixels:(IOSIntArray *)pixels x:(int)x y:(int)y width:(int)width height:(int)height {
  return [AppleHelper getPixels:pixels x:x y:y width:width height:height bitmapData:self.getPixelData imageSize:image.size];
}

- (void)setPixels:(IOSIntArray *)pixels x:(int)x y:(int)y width:(int)width height:(int)height {
  [AppleHelper setPixels:pixels x:x y:y width:width height:height bitmapData:self.getPixelData imageSize:image.size];
  dirtyData = YES;
}

- (void)setPixelAtX:(int)x y:(int)y color:(int)color {
  [AppleHelper setPixelAtX:x y:y color:color bitmapData:self.getPixelData imageSize:image.size];
  dirtyData = YES;
}
- (BOOL)orientationCheck {
  if(!resourceName_ || !hasOrientatedVersion) {
    return NO;
  }
  BOOL l=UIInterfaceOrientationIsLandscape([UIApplication sharedApplication].statusBarOrientation);
  if(l!=landscape) {
    landscape=l;
    RAREUIImage *img=[((RAREAppContext *) [RAREPlatform getAppContext]) getManagedResourceWithNSString:resourceName_];
    if(img) {
      RAREImageWrapper* wrap=(RAREImageWrapper*)[img getProxy];
      image=wrap->image;
      imageSize=image.size;
      imageData=nil;
      dirtyData=NO;
      context_=NULL;
      createdContext=NO;
      [img dispose];
      return YES;
    }
    else {
      hasOrientatedVersion=NO;
    }
  }
  return NO;
}
- (void)dirtyCheck {
  if ((dirtyData && imageData) || cgLayer) {
    CGSize size=image.size;
    UIGraphicsBeginImageContext(size);
    [self drawInRect:(CGRect){{},size}];
    image = UIGraphicsGetImageFromCurrentImageContext();
    UIGraphicsEndImageContext();
    dirtyData = NO;
    imageData=nil;
    [self removeCGLayer];
  }
}

- (unsigned char *)getPixelData {
  if (!imageData) {
    if(cgLayer) {
      [self dirtyCheck];
    }
    NSUInteger width = imageSize.width;
    NSUInteger height = imageSize.height;
    imageData = [NSMutableData dataWithLength:width * height * 4];
    if (image) {
      void *rawData = imageData.mutableBytes;
      size_t bytesPerPixel = 4;
      size_t bytesPerRow = bytesPerPixel * width;
      
      NSUInteger bitsPerComponent = 8;
      CGColorSpaceRef colorSpace = CGColorSpaceCreateDeviceRGB();
      CGContextRef context = CGBitmapContextCreate(
                                                   rawData, width, height, bitsPerComponent, bytesPerRow, colorSpace,
                                                   kCGImageAlphaPremultipliedLast | kCGBitmapByteOrder32Big
                                                   );
      CGColorSpaceRelease(colorSpace);
      CGContextDrawImage(context, CGRectMake(0, 0, width, height), image.CGImage);
      CGContextRelease(context);
    }
  }
  return imageData.mutableBytes;
}

- (id)createContext {
  //CGLayer is non performant and leaks so don't use; code left jsut in case apple fixes this going forward
  if(!context_) {
//    if(imageData) {
      NSUInteger width = imageSize.width;
      NSUInteger height = imageSize.height;
      CGColorSpaceRef colorSpace = CGColorSpaceCreateDeviceRGB();
      void *rawData = [self getPixelData];
      
      size_t bytesPerPixel = 4;
      size_t bytesPerRow = bytesPerPixel * width;
      
      NSUInteger bitsPerComponent = 8;
      CGContextRef context = CGBitmapContextCreate(
                                                   rawData, width, height, bitsPerComponent, bytesPerRow, colorSpace,
                                                   kCGImageAlphaPremultipliedLast | kCGBitmapByteOrder32Big
                                                   );
      
      CGColorSpaceRelease(colorSpace);
      context_=context;
//    }
//    else {
//      cgLayer=CGLayerCreateWithContext(UIGraphicsGetCurrentContext(), image.size, NULL);
//      CGContextRef context=CGLayerGetContext(cgLayer);
//      context_=context;
//    }
    if(context_) {
      dirtyData = YES;
      createdContext=YES;
    }
  }
  return (__bridge id)(context_);
}

- (CGImageRef) getImageRef {
  CGContextRef context = (__bridge CGContextRef)[self createContext];
  if(context) {
    CGImageRef ref = CGBitmapContextCreateImage(context);
    return ref;
  }
  else {
    return image.CGImage;
  }
}

- (RAREImageWrapper *)getSubImageAtX:(int)x y:(int)y width:(int)width height:(int)height {
  return [[RAREImageWrapper alloc] initWithImage:[self getSubImageExAtX:x y:y width:width height:height]];
}

- (UIImage *)getSubImageExAtX:(int)x y:(int)y width:(int)width height:(int)height {
  CGSize size=image.size;
  CGRect src=CGRectMake(x, y, width, height);
  CGRect dst=CGRectMake(0, 0, size.width, size.height);
  UIGraphicsBeginImageContext(src.size);
  
  CGContextRef ctx = UIGraphicsGetCurrentContext();
  int sy=height-y;
  CGContextTranslateCTM(ctx, x, sy);
  CGContextScaleCTM(ctx, 1, -1);
  if(cgLayer) {
    CGContextDrawLayerInRect(ctx, dst, cgLayer);
  }
  else {
    CGImageRef ref=[self getImageRef];
    CGContextDrawImage(ctx, dst, ref);
    if(ref!=image.CGImage) {
      CGImageRelease(ref);
    }
  }
  UIImage* img = UIGraphicsGetImageFromCurrentImageContext();
  UIGraphicsEndImageContext();
  return img;
}

- (RAREImageWrapper *)getScaledImageWithWidth:(int)width height:(int)height scalintType:(RAREiImagePainter_ScalingTypeEnum *)scalingType
{
  CGSize size = CGSizeMake(width, height);
  CGRect dst=CGRectMake(0, 0, width, height);
  UIGraphicsBeginImageContext(size);
  CGContextRef ctx = UIGraphicsGetCurrentContext();
  CGInterpolationQuality scaling = [RAREAppleGraphics getImageInterpolationWithRAREiImagePainter_ScalingTypeEnum:scalingType];
  CGContextSetInterpolationQuality(ctx, scaling);
  CGContextTranslateCTM(ctx, 0, height);
  CGContextScaleCTM(ctx, 1, -1);
  if(cgLayer) {
    CGContextDrawLayerInRect(ctx, dst, cgLayer);
  }
  else {
    CGImageRef ref=[self getImageRef];
    CGContextDrawImage(ctx, dst, ref);
    if(ref!=image.CGImage) {
      CGImageRelease(ref);
    }
  }
  UIImage *img = UIGraphicsGetImageFromCurrentImageContext();
  
  UIGraphicsEndImageContext();
  return [[RAREImageWrapper alloc] initWithImage:img];
}

- (void)blur {
  [self dirtyCheck];
  CIImage *inputImage = image.CIImage;
  CIFilter *filter = [CIFilter filterWithName:@"CIGaussianBlur"];
  [filter setDefaults];
  [filter setValue:inputImage forKey:@"inputImage"];
  CIImage *outputImage = [filter valueForKey:@"outputImage"];
  image = [UIImage imageWithCIImage:outputImage];
}

-(RAREImageWrapper*) createCopyWithReflectionWithHeight: (int) height opacity: (float) opacity gap: (int) gap {
  CGSize size = image.size;
  int y=size.height-height;
  CGSize newSize = CGSizeMake(size.width, size.height + height + gap);
  UIGraphicsBeginImageContext(newSize);
  [self drawInRect:(CGRect) {{}, size}];
  if (gap > 0) {
    [[UIColor blackColor] set];
    UIRectFill(CGRectMake(0, y, size.width, gap));
  }
  
  UIImage* img=[self getSubImageExAtX:0 y:y width:size.width height:height];
  CGRect dst=(CGRect) {{0,size.height-height+gap}, img.size};
  [img drawInRect:dst blendMode:kCGBlendModeScreen alpha:opacity];
  img = UIGraphicsGetImageFromCurrentImageContext();
  UIGraphicsEndImageContext();
  
  return [[RAREImageWrapper alloc] initWithImage:img];
  
}
-(RAREImageWrapper*) createReflectionWithHeight: (int) height opacity: (float) opacity gap: (int) gap {
  CGSize size = image.size;
  int y=size.height-height;
  CGSize newSize = CGSizeMake(size.width, height + gap);
  UIGraphicsBeginImageContext(newSize);
  if (gap > 0) {
    [[UIColor blackColor] set];
    UIRectFill(CGRectMake(0, y, size.width, gap));
  }
  UIImage* img=[self getSubImageExAtX:0 y:y width:size.width height:height];
  CGRect dst=(CGRect) {{0,size.height-height+gap}, img.size};
  [img drawInRect:dst blendMode:kCGBlendModeScreen alpha:opacity];
  img = UIGraphicsGetImageFromCurrentImageContext();
  UIGraphicsEndImageContext();
  
  return [[RAREImageWrapper alloc] initWithImage:img];
  
}
-(RAREImageWrapper*) addReflectionFromY: (int) y height: (int) height opacity: (float) opacity gap: (int) gap {
  CGSize size = image.size;
  CGContextRef ctx = (__bridge CGContextRef)([self createContext]);
  CGContextSaveGState(ctx);
  if (gap > 0) {
    [[UIColor blackColor] set];
    CGContextFillRect(ctx, CGRectMake(0, y, size.width, gap));
  }
  UIImage* img=[self getSubImageExAtX:0 y:y width:size.width height:height];
  CGRect dst=(CGRect) {{0,size.height-height+gap}, img.size};
  UIGraphicsPushContext(ctx);
  [img drawInRect:dst blendMode:kCGBlendModeScreen alpha:opacity];
  UIGraphicsPopContext();
  CGContextRestoreGState(ctx);
  return self;
}

- (RAREImageWrapper *)copyImage {
  [self dirtyCheck];
  return [[RAREImageWrapper alloc] initWithImage:image];
}
-(RAREImageWrapper*) rotateRight {
  return [self rotate:90];
}

-(RAREImageWrapper*) rotateLeft {
  return [self rotate:-90];
}

-(RAREImageWrapper*) rotate:(int) degrees{
  [self dirtyCheck];
  CGSize size= image.size;
  CGSize newSize= CGSizeMake(size.height, size.width);
  
  UIGraphicsBeginImageContext(newSize);
  CGContextRef ctx = UIGraphicsGetCurrentContext();
  
  CGContextTranslateCTM(ctx, newSize.width/2, newSize.height/2);
  
  CGContextRotateCTM(ctx, (degrees * M_PI / 180));
  
  CGContextScaleCTM(ctx, 1.0, -1.0);
  CGContextDrawImage(ctx, CGRectMake(-size.width / 2, -size.height / 2, size.width, size.height), [image CGImage]);
  
  UIImage *img = UIGraphicsGetImageFromCurrentImageContext();
  UIGraphicsEndImageContext();
  return [[RAREImageWrapper alloc] initWithImage:img];
}




- (void)transform:(CGAffineTransform)transform size:(CGSize)newSize {
  [self dirtyCheck];
  UIGraphicsBeginImageContext(newSize);
  CGContextRef ctx = UIGraphicsGetCurrentContext();
  CGContextConcatCTM(ctx, transform);
  CGContextDrawImage(ctx, (CGRect) {{}, image.size}, image.CGImage);
  
  image = UIGraphicsGetImageFromCurrentImageContext();
  UIGraphicsEndImageContext();
}

- (void)flip {
  [self dirtyCheck];
  CGSize size=image.size;
  UIGraphicsBeginImageContext(size);
  CGContextRef ctx = UIGraphicsGetCurrentContext();
  //  CGContextTranslateCTM(ctx, 0, size.height);
  //  CGContextScaleCTM(ctx, 1, -1);
  CGContextDrawImage(ctx, (CGRect) {{}, image.size}, image.CGImage);
  
  image = UIGraphicsGetImageFromCurrentImageContext();
  UIGraphicsEndImageContext();
}
- (void)drawAtX:(int)x y:(int)y {
  [self orientationCheck];
  if (dirtyData && imageData && createdContext) {
    CGContextRef ctx = UIGraphicsGetCurrentContext();
    CGImageRef ref=[self getImageRef];
    if(ref) {
      CGContextDrawImage(ctx, (CGRect) {CGPointMake(x,y), imageSize}, ref);
      if(ref!=image.CGImage) {
        CGImageRelease(ref);
      }
    }
  }
  else if(cgLayer) {
    CGContextRef ctx = UIGraphicsGetCurrentContext();
    CGContextDrawLayerAtPoint(ctx, CGPointMake(x, y), cgLayer);
  }
  else {
    [self dirtyCheck];
    [image drawAtPoint:CGPointMake(x, y)];
  }
}

- (void)drawAtX:(int)x y:(int)y composite:(id<RAREiComposite>)composite  {
  [self orientationCheck];
  float fraction = 1;
  CGBlendMode operation = kCGBlendModeNormal;
  if(composite) {
    fraction=[composite getAlpha];
    operation=[RAREAppleGraphics getCompositingOperationWithRAREiComposite_CompositeTypeEnum:[composite getCompositeType]];
  }
  if (dirtyData && imageData && createdContext) {
    CGImageRef ref=[self getImageRef];
    if(ref) {
      CGContextRef ctx = UIGraphicsGetCurrentContext();
      CGContextSaveGState(ctx);
      if(composite) {
        CGContextSetBlendMode(ctx, operation);
        CGContextSetAlpha(ctx, fraction);
      }
      CGContextDrawImage(ctx, (CGRect) {CGPointMake(x,y), imageSize}, ref);
      CGContextRestoreGState(ctx);
      if(ref!=image.CGImage) {
        CGImageRelease(ref);
      }
    }
  }
  else if(cgLayer) {
    CGContextRef ctx = UIGraphicsGetCurrentContext();
    CGContextDrawLayerAtPoint(ctx, CGPointMake(x, y), cgLayer);
  }
  else {
    [self dirtyCheck];
    if(composite) {
      [image drawAtPoint:CGPointMake(x, y) blendMode:operation alpha:fraction];
    }
    else {
      [image drawAtPoint:CGPointMake(x, y)];
    }
  }
}

- (void)drawInRect:(CGRect)dstRect {
  [self orientationCheck];
  if (dirtyData && imageData && createdContext) {
    CGImageRef ref=[self getImageRef];
    if(ref) {
      CGContextRef ctx = UIGraphicsGetCurrentContext();
      CGContextDrawImage(ctx, dstRect, ref);
      if(ref!=image.CGImage) {
        CGImageRelease(ref);
      }
    }
  }
  else if(cgLayer) {
    CGContextRef ctx = UIGraphicsGetCurrentContext();
    CGContextDrawLayerInRect(ctx, dstRect, cgLayer);
  }
  else {
    [self dirtyCheck];
    [image drawInRect:dstRect];
  }
}

- (void)drawInRect:(CGRect)dstRect source:(CGRect)srcRect scaling:(RAREiImagePainter_ScalingTypeEnum *)scalingType composite:( id<RAREiComposite>)composite drawFlipped: (BOOL) flipped;{
  float fraction = 1;
  CGBlendMode operation = kCGBlendModeNormal;
  if(composite) {
    fraction=[composite getAlpha];
    operation=[RAREAppleGraphics getCompositingOperationWithRAREiComposite_CompositeTypeEnum:[composite getCompositeType]];
  }
  CGSize size = imageSize;
  BOOL wholeImage = CGSizeEqualToSize(srcRect.size, size) && srcRect.origin.x == 0 && srcRect.origin.y == 0 && CGSizeEqualToSize(srcRect.size, dstRect.size);
  if (!scalingType && wholeImage) {
    [self drawAtX:dstRect.origin.x y:dstRect.origin.y composite:composite];
    return;
  }
  if([self orientationCheck] && wholeImage) {
    srcRect= CGRectMake(0,0,imageSize.width, imageSize.height);
  }
  else {
    [self dirtyCheck];
  }
  UIImage *img = image;
  CGImageRef ref=img.CGImage;
  BOOL release=NO;
  if(!wholeImage) {
    release=YES;
    ref=CGImageCreateWithImageInRect(ref,srcRect);
  }
  CGContextRef ctx = UIGraphicsGetCurrentContext();
  CGContextSaveGState(ctx);
  if (scalingType) {
    CGInterpolationQuality scaling = [RAREAppleGraphics getImageInterpolationWithRAREiImagePainter_ScalingTypeEnum:scalingType];
    CGContextSetInterpolationQuality(ctx, scaling);
  }
  if(composite) {
    CGContextSetAlpha(ctx,fraction);
    CGContextSetBlendMode(ctx, operation);
  }
  if(flipped) {
    CGContextTranslateCTM(ctx, 0, dstRect.size.height);
    CGContextScaleCTM(ctx, 1, -1);
  }
  dstRect.origin.y=-dstRect.origin.y;
  CGContextDrawImage(ctx,dstRect,ref);
  CGContextRestoreGState(ctx);
  if(release) {
    CGImageRelease(ref);
  }
}

- (RAREImageWrapper *)createDisabledVersion {
  [self dirtyCheck];
  UIImage *gsimage = [self createDeviceGrayScaleImage];
  UIGraphicsBeginImageContextWithOptions(imageSize, NO, 0.0f);
  CGRect bounds = CGRectMake(0, 0, imageSize.width, imageSize.height);
  
  [gsimage drawInRect:bounds blendMode:kCGBlendModeOverlay alpha:.5];
  [image drawInRect:bounds blendMode:kCGBlendModeDestinationIn alpha:1];
  
  UIImage *img = UIGraphicsGetImageFromCurrentImageContext();
  UIGraphicsEndImageContext();
  
  return [[RAREImageWrapper alloc] initWithImage:img];
}

- (UIImage *)createDeviceGrayScaleImage {
  [self dirtyCheck];
  CGRect imageRect = CGRectMake(0, 0, imageSize.width, imageSize.height);
  CGColorSpaceRef colorSpace = CGColorSpaceCreateDeviceGray();
  CGContextRef context = CGBitmapContextCreate(nil, imageSize.width, imageSize.height, 8, 0, colorSpace, (CGBitmapInfo)kCGImageAlphaNone);
  
  CGContextDrawImage(context, imageRect, [image CGImage]);
  CGImageRef ref = CGBitmapContextCreateImage(context);
  UIImage *img = [UIImage imageWithCGImage:ref];
  
  CGColorSpaceRelease(colorSpace);
  CGContextRelease(context);
  CFRelease(ref);
  return img;
}

- (UIImage *)getImage {
  [self dirtyCheck];
  return image;
}

+ (UIImage *)getImageFromIcon:(id <RAREiPlatformIcon>)icon forView:(RAREView *)view {
  if ([icon isKindOfClass:[RAREUIImageIcon class]]) {
    RAREImageWrapper *rapper = (RAREImageWrapper *) [[((RAREUIImageIcon *) icon) getImage] getProxy];
    return [rapper getImage];
  }
  
  CGSize size = CGSizeMake([icon getIconWidth], [icon getIconHeight]);
  UIGraphicsBeginImageContext(size);
  CGContextRef ctx = UIGraphicsGetCurrentContext();
  RAREAppleGraphics *g = [[RAREAppleGraphics alloc] initWithId:(__bridge id) (ctx) withRAREView:view];
  [icon paintWithRAREiPlatformGraphics:g withFloat:0 withFloat:0 withFloat:size.width withFloat:size.height];
  [g dispose];
  UIImage *img = UIGraphicsGetImageFromCurrentImageContext();
  UIGraphicsEndImageContext();
  return img;
}

-(void) removeCGLayer {
  if(cgLayer) {
    CGContextRelease((CGContextRef)context_);
    CGLayerRelease(cgLayer);
    context_=NULL;
    cgLayer=NULL;
    createdContext=NO;
  }
}

-(void) dealloc {
  if(context_) {
    CGContextRelease((CGContextRef)context_);
  }
  if(cgLayer) {
    CGLayerRelease(cgLayer);
  }
}
@end
