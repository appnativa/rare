//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/platform/apple/ui/view/ImageView.java
//
//  Created by decoteaud on 9/15/15.
//

#include "IOSClass.h"
#include "com/appnativa/rare/platform/apple/ui/view/ImageView.h"
#include "com/appnativa/rare/platform/apple/ui/view/View.h"
#include "com/appnativa/rare/platform/apple/ui/view/aView.h"
#include "com/appnativa/rare/ui/UIColor.h"
#include "com/appnativa/rare/ui/UIImage.h"
#include "com/appnativa/rare/ui/UIRectangle.h"
#include "com/appnativa/rare/ui/UIStroke.h"
#include "com/appnativa/rare/ui/iPlatformShape.h"
#import "RAREAPImageView.h"
 #import "RAREImageWrapper.h"
 #import "AppleHelper.h"
 #import "APView+Component.h"

@implementation RAREImageView

- (id)init {
  return [super initWithId:[RAREImageView createAPImageView]];
}

- (void)setPreserveAspectRatioWithBoolean:(BOOL)preserveAspectRatio
                              withBoolean:(BOOL)fill {
  if(fill) {
    ((RAREAPImageView*)proxy_).contentMode = preserveAspectRatio ? UIViewContentModeScaleAspectFill : UIViewContentModeScaleToFill;
    
  }
  else {
    ((RAREAPImageView*)proxy_).contentMode = preserveAspectRatio ? UIViewContentModeScaleAspectFit : UIViewContentModeScaleToFill;
  }
}

- (void)setImageWithRAREUIImage:(RAREUIImage *)image {
  [self setImageExWithRAREUIImage:image];
  if (manageVisibility_) {
    [self setVisibleWithBoolean:image != nil];
  }
  self->image_ = image;
}

- (void)revalidate {
  [self repaint];
}

- (RAREUIImage *)getImage {
  return image_;
}

- (RAREUIImage *)capture {
  return [self captureInRectWithRAREUIRectangle:nil withFloat:0 withFloat:0];
}

- (RAREUIImage *)captureInRectWithRAREUIRectangle:(RAREUIRectangle *)rect
                                        withFloat:(float)width
                                        withFloat:(float)height {
  RAREAPImageView* view=(RAREAPImageView*)proxy_;
  CGRect frame=view.bounds;
  CGSize size;
  if(rect) {
    frame=[rect toRect];
    size=CGSizeMake(width,height);
  }
  else {
    if(rotation_==90 || rotation_==-90 || rotation_==270) {
      size=  CGSizeMake(frame.size.height, frame.size.width);
    }
    else {
      size=frame.size;
    }
  }
  UIGraphicsBeginImageContext(size);
  CGContextRef ctx = UIGraphicsGetCurrentContext();
  if(rect) {
    CGContextTranslateCTM(ctx,frame.origin.x,frame.origin.y);
  }
  switch(rotation_) {
    case 90:
    CGContextTranslateCTM(ctx,frame.size.height,0);
    CGContextRotateCTM(ctx, 90 / 180.0 * M_PI);
    break;
    case -90:
    case 270:
    CGContextRotateCTM(ctx, -90 / 180.0 * M_PI);
    CGContextTranslateCTM(ctx,-frame.size.width,0);
    break;
    case 180:
    CGContextScaleCTM(ctx, -1,-1);
    CGContextTranslateCTM(ctx,-frame.size.width,-frame.size.height);
    break;
    default:
    break;
  }
  [view.layer renderInContext:UIGraphicsGetCurrentContext()];
  UIImage *img = UIGraphicsGetImageFromCurrentImageContext();
  UIGraphicsEndImageContext();
  RAREImageWrapper* wrapper=[[RAREImageWrapper alloc] initWithImage:img];
  return [[RAREUIImage alloc] initWithId:wrapper];
}

- (void)setSelectionWithRAREiPlatformShape:(id<RAREiPlatformShape>)shape {
  [((RAREAPImageView*)proxy_) setSelectionShape: shape];
}

- (void)setSelectionStrokeWithRAREUIStroke:(RAREUIStroke *)stroke {
  [((RAREAPImageView*)proxy_) setSelectionStroke: stroke];
}

- (void)setSelectionColorWithRAREUIColor:(RAREUIColor *)color {
  [((RAREAPImageView*)proxy_) setSelectionColor: color];
}

- (void)setImageExWithRAREUIImage:(RAREUIImage *)image {
  [((RAREAPImageView*)proxy_) setRAREUIImage: image];
}

+ (id)createAPImageView {
  return [[RAREAPImageView alloc]init];
}

- (void)imageLoadedWithRAREUIImage:(RAREUIImage *)image {
  if (proxy_ != nil) {
    [self setImageExWithRAREUIImage:image];
    if (manageVisibility_) {
      [self setVisibleWithBoolean:YES];
    }
  }
}

- (BOOL)isManageVisibility {
  return manageVisibility_;
}

- (void)setManageVisibilityWithBoolean:(BOOL)manageVisibility {
  self->manageVisibility_ = manageVisibility;
}

- (void)copyAllFieldsTo:(RAREImageView *)other {
  [super copyAllFieldsTo:other];
  other->image_ = image_;
  other->manageVisibility_ = manageVisibility_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "setPreserveAspectRatioWithBoolean:withBoolean:", NULL, "V", 0x101, NULL },
    { "getImage", NULL, "LRAREUIImage", 0x1, NULL },
    { "capture", NULL, "LRAREUIImage", 0x1, NULL },
    { "captureInRectWithRAREUIRectangle:withFloat:withFloat:", NULL, "LRAREUIImage", 0x101, NULL },
    { "setSelectionWithRAREiPlatformShape:", NULL, "V", 0x101, NULL },
    { "setSelectionStrokeWithRAREUIStroke:", NULL, "V", 0x101, NULL },
    { "setSelectionColorWithRAREUIColor:", NULL, "V", 0x101, NULL },
    { "setImageExWithRAREUIImage:", NULL, "V", 0x104, NULL },
    { "createAPImageView", NULL, "LNSObject", 0x10c, NULL },
    { "isManageVisibility", NULL, "Z", 0x1, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "image_", NULL, 0x1, "LRAREUIImage" },
    { "manageVisibility_", NULL, 0x0, "Z" },
  };
  static J2ObjcClassInfo _RAREImageView = { "ImageView", "com.appnativa.rare.platform.apple.ui.view", NULL, 0x1, 10, methods, 2, fields, 0, NULL};
  return &_RAREImageView;
}

@end
