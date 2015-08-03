//
//  IOSHelper.m
//  porting
//
//  Created by Don DeCoteau on 1/14/13.
//  Copyright (c) 2013 Don DeCoteau. All rights reserved.
//

#import "AppleHelper.h"
#import "java/nio/charset/Charset.h"
#import "java/util/Set.h"
#import "java/util/Iterator.h"
#import "com/appnativa/util/CharScanner.h"
#import "IOSIntArray.h"
#import "IOSFloatArray.h"
#import "IOSObjectArray.h"
#import "com/appnativa/rare/ui/UIColor.h"
#import "com/appnativa/rare/ui/UIPoint.h"
#import <QuartzCore/QuartzCore.h>
#import <java/lang/Boolean.h>
#import <com/appnativa/rare/ui/text/HTMLCharSequence.h>
#import "java/io/UnsupportedEncodingException.h"
#import "com/appnativa/rare/ui/painter/UIImagePainter.h"
#import "com/appnativa/rare/ui/RenderType.h"
#import "com/appnativa/rare/ui/painter/UIBackgroundPainter.h"
#import "com/appnativa/rare/ui/UIColor.h"
#import "RARECAGradientLayer.h"
#import "RARECALayer.h"
#import "RAREImageWrapper.h"
#import "com/appnativa/rare/ui/UIImage.h"

#if !defined TYPE_EQUALS
#define TYPE_EQUALS(a,b) strcmp(a,b)==0
#endif
static NSLocale* deafultLocale=NULL;
static NSInteger ltrText=-1;
static CGColorRef clearColor=NULL;
@implementation AppleHelper
+(void) setLayerImage:(id) uiimage onLayer: (CALayer*) layer renderType: (RARERenderTypeEnum*) renderType {
#if TARGET_OS_IPHONE
  layer.contents=(id)((UIImage*)uiimage).CGImage;
#else
  layer.contents=uiimage;
#endif
  switch([renderType ordinal]) {
    case RARERenderType_UPPER_LEFT:
      layer.contentsGravity=kCAGravityTopLeft;
      break;
    case RARERenderType_UPPER_RIGHT:
      layer.contentsGravity=kCAGravityTopRight;
      break;
    case RARERenderType_LOWER_LEFT:
      layer.contentsGravity=kCAGravityBottomLeft;
      break;
    case RARERenderType_LOWER_RIGHT:
      layer.contentsGravity=kCAGravityBottomRight;
      break;
    case RARERenderType_LOWER_MIDDLE:
      layer.contentsGravity=kCAGravityBottom;
      break;
    case RARERenderType_UPPER_MIDDLE:
      layer.contentsGravity=kCAGravityTop;
      break;
    case RARERenderType_LEFT_MIDDLE:
      layer.contentsGravity=kCAGravityLeft;
      break;
    case RARERenderType_RIGHT_MIDDLE:
      layer.contentsGravity=kCAGravityRight;
      break;
    case RARERenderType_CENTERED:
      layer.contentsGravity=kCAGravityCenter;
      break;
    case RARERenderType_TILED: {
#if TARGET_OS_IPHONE
      float iOSVersion = [[[UIDevice currentDevice] systemVersion] floatValue];
      if( iOSVersion >= 6.0f )
      {
        layer.contents=(id)[((UIImage*)uiimage) resizableImageWithCapInsets:UIEdgeInsetsZero resizingMode:UIImageResizingModeTile].CGImage;
      }
      else
      {
        layer.contents=(id)[((UIImage*)uiimage) resizableImageWithCapInsets:UIEdgeInsetsZero].CGImage;
      }
#endif
      break;
    }
    default:
      layer.contentsGravity=kCAGravityResize;
      break;
      
  }
  [layer setNeedsDisplay];
}

+(void) setLayerPainter:(id<RAREiBackgroundPainter>) painter onLayer: (CALayer*) layer withBackground: (RAREUIColor*) bg{
  if(!painter) {
    if([layer isKindOfClass:[RARECAGradientLayer class]]) {
      RARECAGradientLayer* l=(RARECAGradientLayer*)layer;
      l.colors=nil;
      l.locations=nil;
    }
    else if([layer isKindOfClass:[RARECALayer class]]) {
      RARECALayer* l=(RARECALayer*)layer;
      l.contents=nil;
      l.contentsGravity=kCAGravityResize;
    }
    [layer setNeedsDisplay];
    return;
  }
  if([painter isKindOfClass:[RAREUIBackgroundPainter class]]) {
    [self setLayerBackgroundPainter:(RAREUIBackgroundPainter*)painter onLayer:layer withBackground: bg];
  }
//  else if([painter isKindOfClass:[RAREUIImagePainter class]]) {
//    RAREUIImagePainter* ip=(RAREUIImagePainter*)painter;
//    UIImage* img=(UIImage*)[((RAREImageWrapper*)[[ip getImage] getProxy]) getImage];
//    [self setLayerImage:img onLayer:layer renderType:[ip getRenderType]];
//  }
  else if([layer isKindOfClass:[RARECALayer class]]) {
    RARECALayer* l=(RARECALayer*)layer;
    l.contents=nil;
    l.contentsGravity=kCAGravityResize;
    [l setPainter:painter];
  }
  [layer setNeedsDisplay];
}

+(void) setLayerBackgroundPainter:(id<RAREiBackgroundPainter>)painter onLayer: (CALayer*) l withBackground: (RAREUIColor*) bg{
  RARECAGradientLayer* layer;
  if(![l isKindOfClass:[RARECAGradientLayer class]]) {
    RARECAGradientLayer* gl=[RARECAGradientLayer layer];
    [l addSublayer:gl];
    l=gl;
  }
  layer=(RARECAGradientLayer*)l;
  if(!painter) {
    layer.colors=nil;
    layer.locations=nil;
  }
  else if([painter isKindOfClass:[RAREUIBackgroundPainter class]]){
    RAREUIBackgroundPainter* bp=(RAREUIBackgroundPainter*)painter;
    IOSObjectArray* uicolors=[bp getGradientColorsWithRAREUIColor:bg];
    IOSFloatArray* distribution=[bp getGradientDistribution];
    NSInteger len=[uicolors count];
    NSInteger flen=[distribution count];
    len=flen=MIN(flen, len);
    NSMutableArray *colors=[NSMutableArray arrayWithCapacity:len];
    NSMutableArray *locations=[NSMutableArray arrayWithCapacity:len];
    UIColor* color;
    for(int i=0;i<len;i++) {
      color=(UIColor*)((RAREUIColor*)[uicolors objectAtIndex:i]).getAPColor;
      [colors addObject:(NSObject*)color.CGColor];
      [locations addObject:[NSNumber numberWithFloat:[distribution floatAtIndex:i]]];
    }
    layer.colors = colors;
    layer.locations = locations;
    CGFloat m=ceilf([bp getGradientMagnitude]/100.0);
    if(m<.01) {
      m=.01;
    }
    switch([bp getGradientDirection].ordinal) {
      case RAREiGradientPainter_Direction_HORIZONTAL_LEFT:
        layer.startPoint = CGPointMake(0.0, 0.5);
        layer.endPoint = CGPointMake(1.0*m, 0.5);
        break;
      case RAREiGradientPainter_Direction_HORIZONTAL_RIGHT:
        layer.startPoint = CGPointMake(1.0, 0.5);
        layer.endPoint = CGPointMake(0.0+(1-m), 0.5);
        break;
      case RAREiGradientPainter_Direction_DIAGONAL_TOP_LEFT:
        layer.startPoint = CGPointMake(0.0, 0.0);
        layer.endPoint = CGPointMake(1.0*m, 1.0*m);
        break;
      case RAREiGradientPainter_Direction_DIAGONAL_BOTTOM_RIGHT:
        layer.startPoint = CGPointMake(1.0, 1.0);
        layer.endPoint = CGPointMake(0.0+(1-m),0.0+(1-m));
        break;
      case RAREiGradientPainter_Direction_DIAGONAL_TOP_RIGHT:
        layer.startPoint = CGPointMake(1.0, 0.0);
        layer.endPoint = CGPointMake(0.0+(1-m), 1.0*m);
        break;
      case RAREiGradientPainter_Direction_DIAGONAL_BOTTOM_LEFT:
        layer.startPoint = CGPointMake(0.0, 1.0);
        layer.endPoint = CGPointMake(1.0, 0.0+(1-m));
        break;
      case RAREiGradientPainter_Direction_VERTICAL_BOTTOM:
        layer.startPoint = CGPointMake(0.5, 1.0);
        layer.endPoint = CGPointMake(0.5, 0.0+(1-m));
        break;
      default:
        layer.startPoint = CGPointMake(0.5, 0.0);
        layer.endPoint = CGPointMake(0.5, 1.0*m);
        break;
        
    }
    [layer setNeedsDisplayInRect: layer.bounds];
  }
  else {
    bg=[painter getBackgroundColor];
    layer.backgroundColor=(__bridge CGColorRef)([bg getCGColor]);
  }
}

+ (NSStringEncoding) encodingFromString: (NSString*) charset {
  NSStringEncoding nsEncoding = NSISOLatin1StringEncoding;
  if (charset) {
    CFStringEncoding cfEncoding =
    CFStringConvertIANACharSetNameToEncoding((ARCBRIDGE CFStringRef)charset);
    if (cfEncoding == kCFStringEncodingInvalidId) {
      id exception = [[JavaIoUnsupportedEncodingException alloc]
                      initWithNSString:charset];
#if ! __has_feature(objc_arc)
      [exception autorelease];
#endif
      @throw exception;
    } else {
      nsEncoding = CFStringConvertEncodingToNSStringEncoding(cfEncoding);
    }
  }
  return nsEncoding;
}

+(NSString*) stringFromEncoding: (NSStringEncoding) encoding{
  return (NSString*)CFStringConvertEncodingToIANACharSetName(CFStringConvertNSStringEncodingToEncoding(encoding));
}
+(NSMutableAttributedString *) createUnderlinedString: (id <JavaLangCharSequence>)text {
  NSMutableAttributedString* attributedText;
  if([text isKindOfClass: [RAREHTMLCharSequence class]]) {
    attributedText=(NSMutableAttributedString*)((RAREHTMLCharSequence*)text)->attributedText_;
  }
  else {
    attributedText= [[NSMutableAttributedString alloc] initWithString:text ? text.description : @""];
  }
  NSRange range = NSMakeRange(0, [attributedText length]);
  [attributedText addAttribute: NSUnderlineStyleAttributeName value:[NSNumber numberWithInt:NSUnderlineStyleSingle] range:range];
  return attributedText;

}
//from http://stackoverflow.com/questions/9470493/transforming-a-rectangle-image-into-a-quadrilateral-using-a-catransform3d
+ (CATransform3D)rectToQuadWithX: (double) X Y: (double) Y width:(double)W height: (double) H quadTLX:(double)x1a quadTLY:(double)y1a quadTRX:(double)x2a quadTRY:(double)y2a quadBLX:(double)x3a quadBLY:(double)y3a quadBRX:(double)x4a quadBRY:(double)y4a
{

  CGFloat y21 = y2a - y1a;
  CGFloat y32 = y3a - y2a;
  CGFloat y43 = y4a - y3a;
  CGFloat y14 = y1a - y4a;
  CGFloat y31 = y3a - y1a;
  CGFloat y42 = y4a - y2a;

  CGFloat a = -H*(x2a*x3a*y14 + x2a*x4a*y31 - x1a*x4a*y32 + x1a*x3a*y42);
  CGFloat b = W*(x2a*x3a*y14 + x3a*x4a*y21 + x1a*x4a*y32 + x1a*x2a*y43);
  CGFloat c = H*X*(x2a*x3a*y14 + x2a*x4a*y31 - x1a*x4a*y32 + x1a*x3a*y42) - H*W*x1a*(x4a*y32 - x3a*y42 + x2a*y43) - W*Y*(x2a*x3a*y14 + x3a*x4a*y21 + x1a*x4a*y32 + x1a*x2a*y43);

  CGFloat d = H*(-x4a*y21*y3a + x2a*y1a*y43 - x1a*y2a*y43 - x3a*y1a*y4a + x3a*y2a*y4a);
  CGFloat e = W*(x4a*y2a*y31 - x3a*y1a*y42 - x2a*y31*y4a + x1a*y3a*y42);
  CGFloat f = -(W*(x4a*(Y*y2a*y31 + H*y1a*y32) - x3a*(H + Y)*y1a*y42 + H*x2a*y1a*y43 + x2a*Y*(y1a - y3a)*y4a + x1a*Y*y3a*(-y2a + y4a)) - H*X*(x4a*y21*y3a - x2a*y1a*y43 + x3a*(y1a - y2a)*y4a + x1a*y2a*(-y3a + y4a)));

  CGFloat g = H*(x3a*y21 - x4a*y21 + (-x1a + x2a)*y43);
  CGFloat h = W*(-x2a*y31 + x4a*y31 + (x1a - x3a)*y42);
  CGFloat i = W*Y*(x2a*y31 - x4a*y31 - x1a*y42 + x3a*y42) + H*(X*(-(x3a*y21) + x4a*y21 + x1a*y43 - x2a*y43) + W*(-(x3a*y2a) + x4a*y2a + x2a*y3a - x4a*y3a - x2a*y4a + x3a*y4a));

  if(fabs(i) < 0.00001)
  {
    i = 0.00001;
  }

  CATransform3D t = CATransform3DIdentity;

  t.m11 = a / i;
  t.m12 = d / i;
  t.m13 = 0;
  t.m14 = g / i;
  t.m21 = b / i;
  t.m22 = e / i;
  t.m23 = 0;
  t.m24 = h / i;
  t.m31 = 0;
  t.m32 = 0;
  t.m33 = 1;
  t.m34 = 0;
  t.m41 = c / i;
  t.m42 = f / i;
  t.m43 = 0;
  t.m44 = i / i;

  return t;

}
+(NSString*) formatString:(NSString*) format withObjectArrays: (IOSObjectArray*) args {
  return [AppleHelper formatString:format withArray:[AppleHelper toNSArray:args]];
}
+(NSString*) formatString:(NSString*) format withArray: (NSArray*) args {
  NSInteger len=format.length;
  unichar *buffer=calloc(len,sizeof(unichar));
  [format getCharacters:buffer range:NSMakeRange(0, len)];
  unichar c;
  for(NSInteger i=0;i<len;i++) {
    c=buffer[i];
    if(c=='%' && i+1<len) {
      i++;
      c=buffer[i];
      if(c!='%') {
        buffer[i]='@';
      }
    }
  }
  format=[NSString stringWithCharacters:buffer length:len];
  free(buffer);
  NSRange range = NSMakeRange(0, [args count]);

  NSMutableData* data = [NSMutableData dataWithLength: sizeof(id) * [args count]];

  [args getObjects: (__unsafe_unretained id *)data.mutableBytes range:range];

  return [[NSString alloc] initWithFormat: format arguments: data.mutableBytes];

}
  
+(id) invokeSelector: (SEL) sel onTarget: (NSObject*) object{
  NSMethodSignature * sig = [[((NSObject*)object) class ] instanceMethodSignatureForSelector:sel];
  NSInvocation * inv = [NSInvocation invocationWithMethodSignature:sig];
  [inv setTarget:object];
  [inv setSelector:sel];
  [inv invoke];
  return [AppleHelper getInvocationReturnValue:inv methodSignature:sig];
}
+(id) invokeSelector: (SEL) sel onTarget: (NSObject*) object withArg: (NSObject*) arg{
  NSMethodSignature * sig = [[((NSObject*)object) class ] instanceMethodSignatureForSelector:sel];
  NSInvocation * inv = [NSInvocation invocationWithMethodSignature:sig];
  [inv setTarget:object];
  [inv setSelector:sel];
  const char* anObjCType=[sig getArgumentTypeAtIndex:2];
  if (TYPE_EQUALS(anObjCType, @encode(BOOL)))
  {
    BOOL rv=NO;
    if([arg isKindOfClass:[JavaLangBoolean class]]) {
      rv=[((JavaLangBoolean*)arg) booleanValue];
    }
    else if([arg isKindOfClass:[NSNumber class]]) {
      rv=[((NSNumber*)arg) boolValue];
    }
    else if(arg) {
      rv=YES;
    }
    [inv  setArgument:&rv atIndex:2];
  }
  else if (TYPE_EQUALS(anObjCType, @encode(char)))
  {
    char rv=0;
    if([arg isKindOfClass:[NSNumber class]]) {
      rv=[((NSNumber*)arg) charValue ];
    }
    else if([arg isKindOfClass:[NSString class]]) {
      if([((NSString*)arg) length]>0) {
        rv=[((NSString*)arg) characterAtIndex:0 ];
      }
    }
    [inv  setArgument:&rv atIndex:2];
  }
  else if (TYPE_EQUALS(anObjCType, @encode(double)))
  {
    double rv=0;
    if([arg isKindOfClass:[NSNumber class]]) {
      rv=[((NSNumber*)arg) doubleValue ];
    }
    [inv  setArgument:&rv atIndex:2];
  }
  else if (TYPE_EQUALS(anObjCType, @encode(float)))
  {
    float rv=0;
    if([arg isKindOfClass:[NSNumber class]]) {
      rv=[((NSNumber*)arg) floatValue ];
    }
    [inv  setArgument:&rv atIndex:2];
  }
  else if (TYPE_EQUALS(anObjCType, @encode(int)))
  {
    int rv=0;
    if([arg isKindOfClass:[NSNumber class]]) {
      rv=[((NSNumber*)arg) intValue ];
    }
    [inv  setArgument:&rv atIndex:2];
  }
  else if (TYPE_EQUALS(anObjCType, @encode(long)))
  {
    long rv=0;
    if([arg isKindOfClass:[NSNumber class]]) {
      rv=[((NSNumber*)arg) longValue ];
    }
    [inv  setArgument:&rv atIndex:2];
  }
  else if (TYPE_EQUALS(anObjCType, @encode(long long)))
  {
    long long rv=0;
    if([arg isKindOfClass:[NSNumber class]]) {
      rv=[((NSNumber*)arg) longLongValue ];
    }
    [inv  setArgument:&rv atIndex:2];
  }
  else if (TYPE_EQUALS(anObjCType, @encode(short)))
  {
    short rv=0;
    if([arg isKindOfClass:[NSNumber class]]) {
      rv=[((NSNumber*)arg) shortValue ];
    }
    [inv  setArgument:&rv atIndex:2];
  }
  else if (TYPE_EQUALS(anObjCType, @encode(unsigned char)))
  {
    unsigned char rv=0;
    if([arg isKindOfClass:[NSNumber class]]) {
      rv=[((NSNumber*)arg) charValue ];
    }
    else if([arg isKindOfClass:[NSString class]]) {
      if([((NSString*)arg) length]>0) {
        rv=[((NSString*)arg) characterAtIndex:0 ];
      }
    }
    [inv  setArgument:&rv atIndex:2];
  }
  else if (TYPE_EQUALS(anObjCType, @encode(unsigned int)))
  {
    unsigned int rv=0;
    if([arg isKindOfClass:[NSNumber class]]) {
      rv=[((NSNumber*)arg) unsignedIntValue ];
    }
    [inv  setArgument:&rv atIndex:2];
  }
  else if (TYPE_EQUALS(anObjCType, @encode(unsigned long)))
  {
    unsigned long rv=0;
    if([arg isKindOfClass:[NSNumber class]]) {
      rv=[((NSNumber*)arg) unsignedLongValue ];
    }
    [inv  setArgument:&rv atIndex:2];
  }
  else if (TYPE_EQUALS(anObjCType, @encode(unsigned long long)))
  {
    unsigned long long rv=0;
    if([arg isKindOfClass:[NSNumber class]]) {
      rv=[((NSNumber*)arg) unsignedLongLongValue ];
    }
    [inv  setArgument:&rv atIndex:2];
  }
  else if (TYPE_EQUALS(anObjCType, @encode(unsigned short)))
  {
    unsigned short rv=0;
    if([arg isKindOfClass:[NSNumber class]]) {
      rv=[((NSNumber*)arg) unsignedShortValue ];
    }
    [inv  setArgument:&rv atIndex:2];
  }
  else {
    [inv  setArgument:&arg atIndex:2];
  }
  [inv invoke];
  return [AppleHelper getInvocationReturnValue:inv methodSignature:sig];
}


+(id) getInvocationReturnValue: (NSInvocation*) invocation methodSignature: (NSMethodSignature *) signature {
    const char* anObjCType=[signature methodReturnType];
    if (TYPE_EQUALS(anObjCType, @encode(BOOL)))
    {
        BOOL rv=NO;
        [invocation getReturnValue:&rv];
        return rv ? [JavaLangBoolean getTRUE] : [JavaLangBoolean getFALSE];
    }
    else if (TYPE_EQUALS(anObjCType, @encode(char)))
    {
        const char* rv=NULL;
        [invocation getReturnValue:&rv];
        return [NSString valueOfChar:(unichar)rv];
    }
    else if (TYPE_EQUALS(anObjCType, @encode(double)))
    {
        double rv=0;
        [invocation getReturnValue:&rv];
        return [NSNumber numberWithDouble:rv];
    }
    else if (TYPE_EQUALS(anObjCType, @encode(float)))
    {
        float rv=0;
        [invocation getReturnValue:&rv];
        return [NSNumber numberWithFloat:rv];
    }
    else if (TYPE_EQUALS(anObjCType, @encode(int)))
    {
        int rv=0;
        [invocation getReturnValue:&rv];
        return [NSNumber numberWithInt:rv];
    }
    else if (TYPE_EQUALS(anObjCType, @encode(NSInteger)))
    {
        NSInteger rv=0;
        [invocation getReturnValue:&rv];
        return [NSNumber numberWithInteger:rv];
    }
    else if (TYPE_EQUALS(anObjCType, @encode(long)))
    {
        long rv=0;
        [invocation getReturnValue:&rv];
        return [NSNumber numberWithLong:rv];
    }
    else if (TYPE_EQUALS(anObjCType, @encode(long long)))
    {
        long long rv=0;
        [invocation getReturnValue:&rv];
        return [NSNumber numberWithLongLong:rv];
    }
    else if (TYPE_EQUALS(anObjCType, @encode(short)))
    {
        short rv=0;
        [invocation getReturnValue:&rv];
        return [NSNumber numberWithShort:rv];
    }
    else if (TYPE_EQUALS(anObjCType, @encode(unsigned char)))
    {
        unsigned char rv=0;
        [invocation getReturnValue:&rv];
        return [NSString valueOfChar:rv];
    }
    else if (TYPE_EQUALS(anObjCType, @encode(unsigned int)))
    {
        unsigned int rv=0;
        [invocation getReturnValue:&rv];
        return [NSNumber numberWithUnsignedInt:rv];
    }
    else if (TYPE_EQUALS(anObjCType, @encode(NSUInteger)))
    {
        BOOL rv=0;
        [invocation getReturnValue:&rv];
        return [NSNumber numberWithBool:rv];
    }
    else if (TYPE_EQUALS(anObjCType, @encode(unsigned long)))
    {
        unsigned long rv=0;
        [invocation getReturnValue:&rv];
        return [NSNumber numberWithUnsignedLong:rv];
    }
    else if (TYPE_EQUALS(anObjCType, @encode(unsigned long long)))
    {
        unsigned long long rv=0;
        [invocation getReturnValue:&rv];
        return [NSNumber numberWithUnsignedLongLong:rv];
    }
    else if (TYPE_EQUALS(anObjCType, @encode(unsigned short)))
    {
        unsigned short rv=0;
        [invocation getReturnValue:&rv];
        return [NSNumber numberWithUnsignedShort:rv];
    }
    else if (TYPE_EQUALS(anObjCType, @encode(id)) || TYPE_EQUALS(anObjCType, "@?"))
    {
        CFTypeRef result=NULL;
        [invocation getReturnValue:&result];

        if (!result) {
            return nil;
        }
        CFRetain(result);
       return (__bridge_transfer id)result;
    }
    else
        return nil;
}

+(void) setClipMaskWithLayer: (CALayer*) layer withPath: (NSObject*) nativepath {

#if TARGET_OS_IPHONE
  CGPathRef path=((UIBezierPath*)nativepath).CGPath;
#else
  NSObject* pp=[AppleHelper quartzPath:(NSBezierPath*)nativepath];
  CGPathRef path=(__bridge CGPathRef)pp;
#endif
  if(path==NULL) {
    return;
  }
  CAShapeLayer* sl;
  CALayer* mask=layer.mask;
  if(mask && [mask isKindOfClass:[CAShapeLayer class]]) {
    sl=(CAShapeLayer*)mask;
  }
  else {
   sl=[[CAShapeLayer alloc]init];
  }
  sl.path=path;
  [sl didChangeValueForKey:@"path"];
  [layer setMask:sl];
  
#if !TARGET_OS_IPHONE
  CFRelease(path);
#endif
}

+(CGColorRef) clearColor {
  if(!clearColor) {
    CGFloat components[4] = {0,0,0,0};
    CGColorSpaceRef colorSpace = CGColorSpaceCreateDeviceRGB();
    clearColor = CGColorCreate(colorSpace, components);
    CGColorSpaceRelease(colorSpace);
  }
  return clearColor;
}

+(BOOL) isLTRText {
  if(ltrText==-1) {
    if([NSLocale characterDirectionForLanguage:[[NSLocale currentLocale] objectForKey:NSLocaleLanguageCode]]==NSLocaleLanguageDirectionRightToLeft) {
      ltrText=1;
    }
    else {
      ltrText=0;
    }
  }
  return ltrText==1 ? YES : NO;
}
#if TARGET_OS_IPHONE
+ (id)quartzPath: (UIBezierPath*) bpath
{
  return (NSObject*)bpath.CGPath;
}
+ (id) getDiffecencePathBetweenRect: (CGRect) rect1 and: (CGRect) rect2 {
  if(rect1.size.width==0 || rect2.size.width==0 || rect1.size.height==0 || rect2.size.height==0) {
    return nil;
  }
  CGRect irect=CGRectIntersection(rect1, rect2);
  CGFloat isectionRight = irect.origin.x + irect.size.width;
  CGFloat rectRight = rect1.origin.x + rect1.size.width;
  CGFloat isectionBottom = irect.origin.y + irect.size.height;
  CGFloat rectBottom = rect1.origin.y + rect1.size.height;
  UIBezierPath* path=[UIBezierPath bezierPath];
  if (irect.origin.y > rect1.origin.y) {
    [path appendPath: [UIBezierPath bezierPathWithRect:CGRectMake(rect1.origin.x, rect1.origin.y, rect1.size.width, irect.origin.y - rect1.origin.y)] ];
  }
  if (irect.origin.x > rect1.origin.x) {
    [path appendPath: [UIBezierPath bezierPathWithRect:CGRectMake(rect1.origin.x, irect.origin.y, irect.origin.x - rect1.origin.x, irect.size.height) ] ];
  }
  if (isectionRight < rectRight) {
    [path appendPath: [UIBezierPath bezierPathWithRect:CGRectMake(isectionRight, irect.origin.y, rectRight - isectionRight, irect.size.height) ] ];
  }
  if (isectionBottom < rectBottom) {
    [path appendPath: [UIBezierPath bezierPathWithRect:CGRectMake(rect1.origin.x, isectionBottom, rect1.size.width, rectBottom - isectionBottom) ]];
  }
  return (NSObject*)path.CGPath;
}
+(int) colorToInt :(UIColor*) color {
  CGFloat red=0 ;
  CGFloat green=0 ;
  CGFloat blue=0 ;
  CGFloat alpha=0;
  
  [color getRed:&red green:&green blue:&blue alpha:&alpha];
  red*=255;
  green*=255;
  blue*=255;
  alpha*=255;
  return ((int)alpha << 24) | ((int)red << 16) | ((int)green << 8) | (int)blue;
}

#else
+ (id) getDiffecencePathBetweenRect: (CGRect) rect1 and: (CGRect) rect2 {
  if(rect1.size.width==0 || rect2.size.width==0 || rect1.size.height==0 || rect2.size.height==0) {
    return nil;
  }
  CGRect irect=CGRectIntersection(rect1, rect2);
  CGFloat isectionRight = irect.origin.x + irect.size.width;
  CGFloat rectRight = rect1.origin.x + rect1.size.width;
  CGFloat isectionBottom = irect.origin.y + irect.size.height;
  CGFloat rectBottom = rect1.origin.y + rect1.size.height;
  NSBezierPath* path=[NSBezierPath bezierPath];
  if (irect.origin.y > rect1.origin.y) {
    [path appendBezierPathWithRect:NSMakeRect(rect1.origin.x, rect1.origin.y, rect1.size.width, irect.origin.y - rect1.origin.y)];
  }
  if (irect.origin.x > rect1.origin.x) {
    [path appendBezierPathWithRect:NSMakeRect(rect1.origin.x, irect.origin.y, irect.origin.x - rect1.origin.x, irect.size.height) ];
  }
  if (isectionRight < rectRight) {
    [path appendBezierPathWithRect:NSMakeRect(isectionRight, irect.origin.y, rectRight - isectionRight, irect.size.height) ];
  }
  if (isectionBottom < rectBottom) {
    [path appendBezierPathWithRect:NSMakeRect(rect1.origin.x, isectionBottom, rect1.size.width, rectBottom - isectionBottom) ];
  }
  return [AppleHelper quartzPath:path];
}
+(int) colorToInt :(NSColor*) color {
  int red = (int)[color redComponent]*255;
  int green = (int)[color greenComponent]*255;
  int blue = (int)[color blueComponent]*255;
  int alpha = (int)[color alphaComponent]*255;
  return (alpha << 24) | (red << 16) | (green << 8) | blue;
}
//+(id) createGradientWithColors: (IOSObjectArray*) uicolors distribution: (IOSFloatArray*) distribution {
//  NSInteger len=[uicolors count];
//  NSInteger flen=[distribution count];
//  len=flen=MIN(flen, len);
//  NSMutableArray *colors=[NSMutableArray arrayWithCapacity:len];
//  CGFloat *locations= calloc(len,sizeof(CGFloat));
//  for(int i=0;i<len;i++) {
//    [colors addObject:((RAREUIColor*)[uicolors objectAtIndex:i]).getAPColor];
//    locations[i]=[distribution floatAtIndex:i];
//  }
//  id g=[[NSGradient alloc] initWithColors:colors atLocations:locations colorSpace:[NSColorSpace deviceRGBColorSpace]];
//  free(locations);
//  return g;
//}
//
//+(void) drawLinearGradient: (id) nativeGradient x: (float)x  y: (float) y width:(float) width height: (float) height angle: (float) angle {
//  NSRect rect=NSMakeRect(x,y,width,height);
//  [((NSGradient*)nativeGradient) drawInRect:rect angle:angle];
//}
//+(void) drawRadialGradient: (id) nativeGradient x: (float)x  y: (float) y width:(float) width height: (float) height cx: (float) cx cy: (float) cy {
//  NSPoint center=NSMakePoint(cx,cy);
//  NSRect rect=NSMakeRect(x,y,width,height);
//  [((NSGradient*)nativeGradient) drawInRect:rect relativeCenterPosition:center];
//}
//+(void)drawGradient:(id) nativeGradient fromCenter: (RAREUIPoint*) startCenter radius:(float) radius toCenter:(RAREUIPoint*)endCenter radius:(float) endRadius {
//  NSPoint start=NSMakePoint(startCenter->x_,startCenter->y_);
//  NSPoint end=NSMakePoint(endCenter->x_,endCenter->y_);
//  [((NSGradient*)nativeGradient) drawFromCenter:start
//                                         radius:radius
//                                       toCenter:end
//                                         radius:endRadius
//                                        options:NSGradientDrawsBeforeStartingLocation|NSGradientDrawsAfterEndingLocation];
//}
//+(void)drawGradient: (id) nativeGradient fromPoint:(RAREUIPoint*)startingPoint toPoint:(RAREUIPoint*)endingPoint {
//  NSPoint start=NSMakePoint(startingPoint->x_,startingPoint->y_);
//  NSPoint end=NSMakePoint(endingPoint->x_,endingPoint->y_);
//  [((NSGradient*)nativeGradient) drawFromPoint:start
//                                       toPoint:end
//                                       options:NSGradientDrawsBeforeStartingLocation|NSGradientDrawsAfterEndingLocation];
//}

+ (id)quartzPath: (NSBezierPath*) bpath
{
  NSInteger i, numElements;
  
  CGMutablePathRef    mpath = CGPathCreateMutable();
  numElements = [bpath elementCount];
  if (numElements > 0)
  {
    NSPoint             points[3];
    BOOL                didClosePath = YES;
    
    for (i = 0; i < numElements; i++)
    {
      switch ([bpath elementAtIndex:i associatedPoints:points])
      {
        case NSMoveToBezierPathElement:
          CGPathMoveToPoint(mpath, NULL, points[0].x, points[0].y);
          break;
          
        case NSLineToBezierPathElement:
          CGPathAddLineToPoint(mpath, NULL, points[0].x, points[0].y);
          didClosePath = NO;
          break;
          
        case NSCurveToBezierPathElement:
          CGPathAddCurveToPoint(mpath, NULL, points[0].x, points[0].y,
                                points[1].x, points[1].y,
                                points[2].x, points[2].y);
          didClosePath = NO;
          break;
          
        case NSClosePathBezierPathElement:
          CGPathCloseSubpath(mpath);
          didClosePath = YES;
          break;
      }
    }
    
    // Be sure the path is closed or Quartz may not do valid hit detection.
    if (!didClosePath)
      CGPathCloseSubpath(mpath);
    
  }
  
  return CFBridgingRelease(mpath);
}
#endif

+(id) createGradientWithColors: (IOSObjectArray*) uicolors distribution: (IOSFloatArray*) distribution {
  NSInteger len=[uicolors count];
  NSInteger flen=[distribution count];
  len=flen=MIN(flen, len);
  NSMutableArray *colors=[NSMutableArray arrayWithCapacity:len];
  CGFloat *locations= calloc(len,sizeof(CGFloat));
  for(int i=0;i<len;i++) {
    [colors addObject:[((RAREUIColor*)[uicolors objectAtIndex:i]) getCGColor]];
    locations[i]=[distribution floatAtIndex:i];
  }
  CGGradientRef gradient = CGGradientCreateWithColors(CGColorSpaceCreateDeviceRGB(), (__bridge CFArrayRef)colors, locations);
  free(locations);
  return CFBridgingRelease(gradient);
}

+(CAGradientLayer*) createLinearGradientLayer: (IOSObjectArray*) uicolors distribution: (IOSFloatArray*) distribution {
  NSInteger len=[uicolors count];
  NSInteger flen=[distribution count];
  len=flen=MIN(flen, len);
  NSMutableArray *colors=[NSMutableArray arrayWithCapacity:len];
  NSMutableArray *locations=[NSMutableArray arrayWithCapacity:len];
  for(int i=0;i<len;i++) {
    [colors addObject:[((RAREUIColor*)[uicolors objectAtIndex:i]) getCGColor]];
    [locations addObject:[NSNumber numberWithFloat:[distribution floatAtIndex:i]]];
  }
  CAGradientLayer *layer = [CAGradientLayer layer];
  layer.colors = colors;
  layer.locations = locations;
  return layer;
}

+(void) drawLinearGradient: (id) nativeGradient context: (id) ctx x: (float)x  y: (float) y width:(float) width height: (float) height angle: (float) angle {
  CGRect rect=CGRectMake(x,y,width,height);
  CGContextRef context = (__bridge CGContextRef)(ctx);
  CGGradientRef gradient=(__bridge CGGradientRef)(nativeGradient);
  CGContextSaveGState(context);
  CGContextClipToRect(context, rect);
  
  CGFloat midX =  CGRectGetMidX(rect);
  CGFloat midY =  CGRectGetMidY(rect);
  
  CGContextTranslateCTM(context, midX, midY);
  CGContextRotateCTM(context, ((M_PI * angle) / 180.0f));
  CGContextTranslateCTM(context, -midX, -midY);
  
  CGPoint startPoint = CGPointMake(CGRectGetMinX(rect), midY);
  CGPoint endPoint = CGPointMake(CGRectGetMaxX(rect), midY);
  
  CGContextDrawLinearGradient(context, gradient, startPoint, endPoint, kCGGradientDrawsAfterEndLocation|kCGGradientDrawsBeforeStartLocation);
  CGContextRestoreGState(context);
}

+(void) drawRadialGradient: (id) nativeGradient context: (id) ctx width:(float) width height: (float) height cx: (float) cx cy: (float) cy radius: (float) radius {
  CGContextRef context = (__bridge CGContextRef)(ctx);
  CGGradientRef gradient=(__bridge CGGradientRef)(nativeGradient);
  
  CGPoint startCenter = CGPointMake(cx,cy);
  
  CGContextDrawRadialGradient(context, gradient, startCenter, 0, startCenter, radius, kCGGradientDrawsAfterEndLocation|kCGGradientDrawsBeforeStartLocation);
}

+(void)drawGradient:(id) nativeGradient  context: (id) ctx fromCenter: (RAREUIPoint*) startCenter radius:(float) radius toCenter:(RAREUIPoint*)endCenter radius:(float) endRadius {
  CGContextRef context = (__bridge CGContextRef)(ctx);
  CGGradientRef gradient=(__bridge CGGradientRef)(nativeGradient);
  CGPoint start=CGPointMake(startCenter->x_,startCenter->y_);
  CGPoint end=CGPointMake(endCenter->x_,endCenter->y_);
  CGContextDrawRadialGradient(context, gradient, start, radius, end, endRadius, kCGGradientDrawsAfterEndLocation|kCGGradientDrawsBeforeStartLocation);
}

+(void)drawGradient: (id) nativeGradient  context: (id) ctx fromPoint:(RAREUIPoint*)startingPoint toPoint:(RAREUIPoint*)endingPoint {
  CGContextRef context = (__bridge CGContextRef)(ctx);
  CGGradientRef gradient=(__bridge CGGradientRef)(nativeGradient);
  CGPoint startPoint=CGPointMake(startingPoint->x_,startingPoint->y_);
  CGPoint endPoint=CGPointMake(endingPoint->x_,endingPoint->y_);
  CGContextDrawLinearGradient(context, gradient, startPoint, endPoint, kCGGradientDrawsAfterEndLocation|kCGGradientDrawsBeforeStartLocation);
}


+(IOSClass *)loadClass:(NSString *)name {
  
  if ([name indexOfString:@"."]!=-1) {
    return [IOSClass forName:name];
  }
  Class class = NSClassFromString(name);
  if (class) {
    return [IOSClass classWithClass:class];
  }
  Protocol *protocol = NSProtocolFromString(name);
  if (protocol) {
    return [IOSClass classWithProtocol:protocol];
  }
  return nil;
}

+(void) setHTTPHeaders: (NSDictionary*) data : (NSObject<JavaUtilMap>*) map{
  NSEnumerator* it=[data keyEnumerator];
  
  NSString* key;
  RAREUTCharScanner* sc;
  JavaUtilArrayList* list;
  while((key=[it nextObject])) {
    NSString* value=[data objectForKey:key];
    if([value indexOf:',' fromIndex:0]==-1) {
      list=[[JavaUtilArrayList alloc] initWithInt:1];
      [list setWithInt:0 withId:value];
      [map putWithId:key withId:list];
      continue;
    }
    if(sc==nil) {
      sc=[[RAREUTCharScanner alloc]initWithNSString:value];
    }
    else {
      [sc resetWithNSString:value];
    }
    list=[[JavaUtilArrayList alloc] initWithInt:[sc getTokenCountWithChar:',' withBoolean:YES withBoolean:YES]];
    [sc getTokensWithChar:',' withBoolean:YES withBoolean:YES withBoolean:YES withJavaUtilList:list];
    if([key equalsIgnoreCase:@"Set-Cookie"]) {
      int len=list.size;
      int i=0;
      while(i<len) {
        NSString* s=[list getWithInt:i];
        if(i+1<len && ([s contains:@" expires="] || [s contains:@" Expires="] )) {
          [list setWithInt:i withId:[NSString stringWithFormat:@"%@ %@", s, [list removeWithInt:i+1]]];
          len--;
        }
        i++;
      }
    }
    [map putWithId:key withId:list];
  }
}
+ (void)invokeLater: (id<JavaLangRunnable>)r   {
  dispatch_async(dispatch_get_main_queue(), ^{
    
    [r run];
    
  });//end block
}

+ (void)invokeLater: (id<JavaLangRunnable>)r delay:(int) delay  {
  dispatch_after(dispatch_time(DISPATCH_TIME_NOW, (int64_t)(delay * NSEC_PER_MSEC)), dispatch_get_main_queue(), ^{
    [r run];
  });
}
+ (void)invokeAndWait: (id<JavaLangRunnable>)r   {
  dispatch_sync(dispatch_get_main_queue(), ^{
    [r run];
  });//end block
}

+(NSString*) getBundleFileName: (NSString*)name ofType: (NSString*)type {
  return [[NSBundle mainBundle] pathForResource:name ofType: type];
  ;
  
}
+(NSString*) toErrorString: (NSError*) error {
  return [error localizedDescription];
}

+(NSArray*) toNSArray:(IOSObjectArray *)array
{
  if(!array) {
    return nil;
  }
  long len=[array count];
  NSMutableArray* a=[NSMutableArray arrayWithCapacity:len];
  for(long i=0;i<len;i++) {
    [a addObject:[array objectAtIndex:i]];
  }
  return a;
}
+(IOSObjectArray*) toStringArray: (NSArray*) array
{
  if(array==nil) {
    return nil;
  }
  return [IOSObjectArray arrayWithNSArray:array type:[IOSClass classWithClass:([NSString class])]];
}
+(JavaUtilArrayList*) toArrayList: (NSArray*) array;
{
  if(array==nil) {
    return nil;
  }
  int len=(int)[array count];
  JavaUtilArrayList* list=[[JavaUtilArrayList alloc] initWithInt:len];
  for(int i=0;i<len;i++) {
    [list addWithId:[array objectAtIndex:i]];
  }
  return list;
}

+(NSLocale*) toNSLocale: (JavaUtilLocale*) locale
{
  if(locale==[JavaUtilLocale getDefault]) {
    if(deafultLocale==NULL) {
      deafultLocale=[[NSLocale alloc] initWithLocaleIdentifier:(@"en_US")];
    }
    return deafultLocale;
  }
  return [[NSLocale alloc] initWithLocaleIdentifier:[locale description]];
}
+(NSString*) toNSString: (NSData*) data encoding: (NSString*) enc
{
  if(enc==nil) {
    enc=@"iso-8859-1";
  }
  NSStringEncoding nsenc=[AppleHelper encodingFromStringWithNSString:enc];
  
  return [[NSString alloc] initWithData:data encoding:nsenc];
}

+ (NSStringEncoding)encodingFromStringWithNSString:(NSString *)charset   {
  NSStringEncoding nsEncoding = NSISOLatin1StringEncoding;
  if (charset) {
    CFStringEncoding cfEncoding =
    CFStringConvertIANACharSetNameToEncoding((ARCBRIDGE CFStringRef)charset);
    if (cfEncoding == kCFStringEncodingInvalidId) {
      id exception = [[JavaIoUnsupportedEncodingException alloc]
                      initWithNSString:charset];
#if ! __has_feature(objc_arc)
      [exception autorelease];
#endif
      @throw exception;
    } else {
      nsEncoding = CFStringConvertEncodingToNSStringEncoding(cfEncoding);
    }
  }
  return nsEncoding;
}

+ (NSString *)stringFromEncodingWithLongInt:(NSStringEncoding)encoding   {
  return (NSString*)CFStringConvertEncodingToIANACharSetName(CFStringConvertNSStringEncodingToEncoding(encoding));
}


+(void) populateMap: (id<JavaUtilMap>) map withDictionary: (NSDictionary*) data
{
  NSEnumerator *enumerator = [data keyEnumerator];
  
  id key;
  NSObject* value;
  while ((key = [enumerator nextObject])){
    value=[data objectForKey: key];
    [map putWithId:key withId:value];
  }
}

+(NSDictionary*) toDictionary: (NSObject<JavaUtilMap>*) map
{
  NSMutableDictionary* dic=[[NSMutableDictionary alloc] initWithCapacity:map.size];
  id<JavaUtilIterator>it=[[map keySet] iterator];
  NSObject<NSCopying>* key;
  NSObject* value;
  while ([ it hasNext]) {
    key=[it next];
    value=[map getWithId:key];
    [dic setObject:value forKey:key];
  }
  return dic;
}
+(NSString*) createTempFileWithPrefix: (NSString*) prefix AndExt: (NSString*) ext;
{
  NSString *tempFileTemplate = [NSTemporaryDirectory() stringByAppendingPathComponent: prefix];
  tempFileTemplate=[tempFileTemplate stringByAppendingString:@"XXXXXX" ];
  if(ext!=nil) {
    tempFileTemplate=[tempFileTemplate stringByAppendingPathExtension:ext];
  }
  const char *tempFileTemplateCString = [tempFileTemplate fileSystemRepresentation];
  
  char *tempFileNameCString = (char *)malloc(strlen(tempFileTemplateCString) + 1);
  strcpy(tempFileNameCString, tempFileTemplateCString);
  int fileDescriptor = mkstemps(tempFileNameCString, 4);
  
  // no need to keep it open
  close(fileDescriptor);
  NSString *tempFileName=nil;
  if (fileDescriptor == -1) {
    NSLog(@"Error while creating tmp file");
  }
  else {
    tempFileName = [[NSFileManager defaultManager]
                              stringWithFileSystemRepresentation:tempFileNameCString
                              length:strlen(tempFileNameCString)];
  }
  free(tempFileNameCString);
  
  return tempFileName;
}

+(NSInteger) getPixelAtX: (int) x y: (int) y bitmapData: (unsigned char*) data imageSize: (CGSize) size{
  int offset=x * size.width*4  + y * 4;
  if(offset+3<size.width*size.height*4) {
    int r=data[offset++];
    int g=data[offset++];
    int b=data[offset++];
    int a=data[offset++];
    return (a << 24) | (r << 16) | (g << 8) | b;
  }
  return 0;
}

+(void) getPixels: (IOSIntArray*) pixels x: (int) x y: (int)y width: (int) width height: (int) height bitmapData: (unsigned char*) data imageSize: (CGSize) size{
  int xlen=MIN(x+width, size.width);
  int ylen=MIN(y+height,size.height);
  int i=0;
  int ox=x;
  int bytesPerRow=size.width*4;
  int end=size.width*size.height*4;
  int offset;
  while(y<ylen) {
    while(x<xlen) {
      offset=x * bytesPerRow  + y * 4;
      if(offset+3<end) {
        int r=data[offset++];
        int g=data[offset++];
        int b=data[offset++];
        int a=data[offset++];
        [pixels replaceIntAtIndex:i withInt:((a << 24) | (r << 16) | (g << 8) | b)];
      }
      else {
        y=ylen;
        break;
      }
      i++;
      x++;
    }
    y++;
    x=ox;
  }
}

+(void) setPixels: (IOSIntArray*) pixels x: (int) x y: (int)y width: (int) width height: (int) height bitmapData: (unsigned char*) data imageSize: (CGSize) size{
  int xlen=MIN(x+width, size.width);
  int ylen=MIN(y+height,size.height);
  int i=0;
  int color;
  NSInteger len=pixels.count;
  int bytesPerRow=size.width*4;
  int offset;
  int end=size.width*size.height*4;
  while(y<ylen) {
    while(x<xlen && i<len) {
      offset=x * bytesPerRow  + y * 4;
      if(offset+3<end) {
        color=[pixels intAtIndex:i];
        data[offset++]=(color >> 16) & 0xff;
        data[offset++]=(color >> 8) & 0xff;
        data[offset++]=(color) & 0xff;
        data[offset++]=ABS(color >> 24) & 0xff;
      }
      else {
        y=ylen;
        break;
      }
      i++;
      x++;
    }
    y++;
  }
}

+(void) setPixelAtX: (int) x y: (int) y color: (int) color bitmapData: (unsigned char*) data imageSize: (CGSize) size{
  int offset=x * size.width*4  + y * 4;
  if(offset+3<size.width*size.height*4) {
    data[offset++]=(color >> 16) & 0xff;
    data[offset++]=(color >> 8) & 0xff;
    data[offset++]=(color) & 0xff;
    data[offset++]=ABS(color >> 24) & 0xff;
  }
}

@end

//--------------------------------------------
@implementation RAREWeakReference
-(id) initWithNSObject: (NSObject*) value {
  data=value;
  return self;
}
-(NSObject*) getValue {
  return data;
}
- (void) clear {
  data=nil;
}

@end

@implementation RARECachingNSData
-(id) initWithBufferSize: (int)size maxSize: (int)maxSize
{
  data=[NSMutableData dataWithCapacity:size];
  maxMemorySize=maxSize;
  retainFile=NO;
  return self;
}
-(void) writeWithIOSByteArray: (IOSByteArray*) b offset: (int)offset length: (int)len
{
  char *buffer=b->buffer_;
  if(stream!=nil) {
    [stream write:(uint8_t*)&buffer[offset] maxLength:len];
  }
  else{
    [data appendBytes:&buffer[offset] length:len];
  }
}
-(void) writeWithInt: (int) b
{
  uint8_t buffer[1];
  buffer[0]=(b & 0xff);
  if(stream!=nil) {
    [stream write:(uint8_t*)&buffer maxLength:1];
  }
  else{
    [data appendBytes:&buffer length:1];
  }
}
-(BOOL) streamWasRequired
{
  return filename!=nil;
}
-(NSInputStream*) createStream
{
  if(filename==nil) {
    return [NSInputStream inputStreamWithData: data];
  }
  else {
    if(stream!=nil) {
      [stream close];
      stream=nil;
    }
    return [NSInputStream inputStreamWithFileAtPath:filename];
  }
}
-(NSData*) getData;
{
  return data;
}
-(void) setRetainFile:(BOOL)retain
{
  retainFile=retain;
}
-(void) dealloc
{
  if(filename!=nil && retainFile==NO) {
    NSError* error;
    [[NSFileManager defaultManager] removeItemAtPath:filename error:&error];
  }
}
@end

@implementation NSIndexSet (SparIndexSet)
- (IOSIntArray *)toIntArray {
  NSInteger n=self.count;
  if(n==0) {
    return nil;
  }
  __block NSInteger i=0;
  IOSIntArray* a=[[IOSIntArray alloc] initWithLength: n];
  [self enumerateIndexesUsingBlock:^(NSUInteger idx, BOOL *stop) {
    [a replaceIntAtIndex: i++ withInt:(int)idx];
  }];
  return a;
  
}
+ (NSIndexSet*) fromIntArray: (IOSIntArray *) array {
  NSMutableIndexSet* set= [NSMutableIndexSet new];
  NSInteger len=array.count;
  for(NSInteger i=0;i<len;i++) {
    [set addIndex:[array intAtIndex:i]];
  }
  return set;
}
@end

@implementation RAREUIRectangle (SparRect2D)
#if TARGET_OS_IPHONE
-(void)setWithRect:(CGRect)rect {
  x_=rect.origin.x;
  y_=rect.origin.y;
  width_=rect.size.width;
  height_=rect.size.height;
}
-(CGRect)toRect {
  return CGRectMake(x_,y_,width_,height_);
}
+(id) fromRect: (CGRect) rect {
  RAREUIRectangle* r=[RAREUIRectangle new];
  [r setWithRect:rect];
  return r;
}
#else
-(void)setWithRect:(NSRect)rect {
  x_=rect.origin.x;
  y_=rect.origin.y;
  width_=rect.size.width;
  height_=rect.size.height;
}
-(NSRect)toRect {
  return NSMakeRect(x_,y_,width_,height_);
}
+(id) fromRect: (NSRect) rect {
  RAREUIRectangle* r=[RAREUIRectangle new];
  [r setWithRect:rect];
  return r;
}
#endif
@end


@implementation RAREUIRectangle (SparRect)
#if TARGET_OS_IPHONE
-(void)setWithRect:(CGRect)rect {
  x_=rect.origin.x;
  y_=rect.origin.y;
  width_=rect.size.width;
  height_=rect.size.height;
}
-(CGRect)toRect {
  return CGRectMake(x_,y_,width_,height_);
}
+(id) fromRect: (CGRect) rect {
  RAREUIRectangle* r=[RAREUIRectangle new];
  [r setWithRect:rect];
  return r;
}
#else
-(void)setWithRect:(NSRect)rect {
  x_=rect.origin.x;
  y_=rect.origin.y;
  width_=rect.size.width;
  height_=rect.size.height;
}
-(NSRect)toRect {
  return NSMakeRect(x_,y_,width_,height_);
}
+(id) fromRect: (NSRect) rect {
  RAREUIRectangle* r=[RAREUIRectangle new];
  [r setWithRect:rect];
  return r;
}
#endif
@end

