//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/ui/UIFont.java
//
//  Created by decoteaud on 9/15/15.
//

#include "com/appnativa/rare/ui/UIFont.h"
#include "java/lang/StringBuilder.h"
#if TARGET_OS_IPHONE
 #import <UIKit/UIKit.h>
 #else
 #import <AppKit/AppKit.h>
 #endif
 #import <CoreText/CoreText.h>

@implementation RAREUIFont

static int RAREUIFont_BOLD_ = 1;
static int RAREUIFont_ITALIC_ = 2;
static int RAREUIFont_PLAIN_ = 0;

+ (int)BOLD {
  return RAREUIFont_BOLD_;
}

+ (int *)BOLDRef {
  return &RAREUIFont_BOLD_;
}

+ (int)ITALIC {
  return RAREUIFont_ITALIC_;
}

+ (int *)ITALICRef {
  return &RAREUIFont_ITALIC_;
}

+ (int)PLAIN {
  return RAREUIFont_PLAIN_;
}

+ (int *)PLAINRef {
  return &RAREUIFont_PLAIN_;
}

- (id)initWithNSString:(NSString *)family
               withInt:(int)style
             withFloat:(float)size {
  if (self = [self initRAREUIFontWithId:[RAREUIFont newFontWithNSString:family withFloat:size withBoolean:[RAREUIFont isBoldWithInt:style] withBoolean:[RAREUIFont isItalicWithInt:style]]]) {
    self->style_ = style;
  }
  return self;
}

- (id)initWithNSString:(NSString *)family
               withInt:(int)style
               withInt:(int)size {
  return [self initRAREUIFontWithId:[RAREUIFont newFontWithNSString:family withFloat:size withBoolean:[RAREUIFont isBoldWithInt:style] withBoolean:[RAREUIFont isItalicWithInt:style]]];
}

- (id)initRAREUIFontWithRAREUIFont:(RAREUIFont *)f {
  return [self initRAREUIFontWithId:((RAREUIFont *) nil_chk(f))->proxy_ withBoolean:f->underline_ withBoolean:f->strikethrough_];
}

- (id)initWithRAREUIFont:(RAREUIFont *)f {
  return [self initRAREUIFontWithRAREUIFont:f];
}

- (id)initWithRAREUIFont:(RAREUIFont *)font
               withFloat:(float)rs {
  if (self = [self initRAREUIFontWithRAREUIFont:font]) {
    relativeSize_ = rs;
  }
  return self;
}

- (id)initWithRAREUIFont:(RAREUIFont *)font
               withFloat:(float)rs
               withFloat:(float)bs {
  if (self = [self initRAREUIFontWithRAREUIFont:font]) {
    relativeSize_ = rs;
    baseSize_ = bs;
  }
  return self;
}

- (id)initRAREUIFontWithId:(id)proxy {
  if (self = [super init]) {
    relativeSize_ = 1.0f;
    self->proxy_ = proxy;
    [self initialize__];
    baseSize_ = [self getSize2D];
  }
  return self;
}

- (id)initWithId:(id)proxy {
  return [self initRAREUIFontWithId:proxy];
}

- (id)initRAREUIFontWithId:(id)proxy
               withBoolean:(BOOL)underline
               withBoolean:(BOOL)strikethrough {
  if (self = [super init]) {
    relativeSize_ = 1.0f;
    self->proxy_ = proxy;
    self->underline_ = underline;
    self->strikethrough_ = strikethrough;
    [self initialize__];
    baseSize_ = [self getSize2D];
  }
  return self;
}

- (id)initWithId:(id)proxy
     withBoolean:(BOOL)underline
     withBoolean:(BOOL)strikethrough {
  return [self initRAREUIFontWithId:proxy withBoolean:underline withBoolean:strikethrough];
}

- (void)addToAttributedStringWithId:(id)astring
                            withInt:(int)offset
                            withInt:(int)length {
  NSMutableAttributedString* s=(NSMutableAttributedString*)astring;
  if(length==-1) {
    length=(int)s.length;
  }
  NSRange range=NSMakeRange(offset,length);
  #if TARGET_OS_IPHONE
  [s addAttribute: NSFontAttributeName
  value: [self getIOSProxy]
  range: range];
  #else
  [s addAttribute: NSFontAttributeName
  value: proxy_
  range: range];
  #endif
  if([self isUnderline]) {
    [s addAttribute: NSUnderlineStyleAttributeName
    value: [NSNumber numberWithInt: NSUnderlineStyleSingle]
    range: range];
  }
  if([self isStrikeThrough]) {
    [s addAttribute: NSStrikethroughStyleAttributeName
    value: [NSNumber numberWithInt: NSUnderlineStyleSingle]
    range: range];
  }
}

- (RAREUIFont *)deriveWithBoolean:(BOOL)strikethrough
                      withBoolean:(BOOL)underlined {
  RAREUIFont *f = [[RAREUIFont alloc] initWithRAREUIFont:self];
  [f setStrikeThroughWithBoolean:strikethrough];
  [f setUnderlineWithBoolean:underlined];
  return f;
}

- (RAREUIFont *)deriveBold {
  if ([self isBold]) {
    return self;
  }
  RAREUIFont *f = [[RAREUIFont alloc] initWithId:[RAREUIFont deriveFontWithId:proxy_ withFloat:baseSize_ withBoolean:YES withBoolean:NO]];
  [f setUnderlineWithBoolean:underline_];
  [f setStrikeThroughWithBoolean:strikethrough_];
  return f;
}

- (RAREUIFont *)deriveBoldItalic {
  if ([self isBold] || [self isItalic]) {
    return self;
  }
  RAREUIFont *f = [[RAREUIFont alloc] initWithId:[RAREUIFont deriveFontWithId:proxy_ withFloat:baseSize_ withBoolean:YES withBoolean:NO]];
  [f setUnderlineWithBoolean:underline_];
  [f setStrikeThroughWithBoolean:strikethrough_];
  return f;
}

- (RAREUIFont *)deriveFontWithInt:(int)style
                        withFloat:(float)size {
  if ((self->size_ == size) && (self->style_ == style)) {
    return self;
  }
  RAREUIFont *f = [[RAREUIFont alloc] initWithId:[RAREUIFont deriveFontWithId:proxy_ withFloat:size withBoolean:[RAREUIFont isBoldWithInt:style] withBoolean:[RAREUIFont isItalicWithInt:style]]];
  [f setUnderlineWithBoolean:underline_];
  [f setStrikeThroughWithBoolean:strikethrough_];
  return f;
}

- (RAREUIFont *)deriveFontSizeWithFloat:(float)size {
  if (self->size_ == size) {
    return self;
  }
  RAREUIFont *f = [[RAREUIFont alloc] initWithId:[RAREUIFont deriveFontWithId:proxy_ withFloat:size withBoolean:[RAREUIFont isBoldWithInt:style_] withBoolean:[RAREUIFont isItalicWithInt:style_]]];
  [f setUnderlineWithBoolean:underline_];
  [f setStrikeThroughWithBoolean:strikethrough_];
  return f;
}

- (RAREUIFont *)deriveItalic {
  if ([self isItalic]) {
    return self;
  }
  RAREUIFont *f = [[RAREUIFont alloc] initWithId:[RAREUIFont deriveFontWithId:proxy_ withFloat:size_ withBoolean:[self isBold] withBoolean:YES]];
  [f setUnderlineWithBoolean:underline_];
  [f setStrikeThroughWithBoolean:strikethrough_];
  return f;
}

- (RAREUIFont *)deriveRelativeFontWithFloat:(float)nrs {
  float sz = nrs * baseSize_;
  RAREUIFont *f = [[RAREUIFont alloc] initWithId:[RAREUIFont deriveFontWithId:proxy_ withFloat:sz withBoolean:[RAREUIFont isBoldWithInt:style_] withBoolean:[RAREUIFont isItalicWithInt:style_]]];
  f->relativeSize_ = nrs;
  f->baseSize_ = baseSize_;
  return f;
}

- (RAREUIFont *)deriveStrikethrough {
  RAREUIFont *f = [[RAREUIFont alloc] initWithRAREUIFont:self];
  [f setStrikeThroughWithBoolean:YES];
  return f;
}

- (RAREUIFont *)deriveUnderline {
  RAREUIFont *f = [[RAREUIFont alloc] initWithRAREUIFont:self];
  [f setUnderlineWithBoolean:YES];
  return f;
}

- (float)getBaseSize {
  return baseSize_;
}

- (NSString *)getFamily {
  return family_;
}

- (id)getIOSProxy {
  #if TARGET_OS_IPHONE
  if(!iosProxy_) {
    UIFont* f=[UIFont fontWithName:name_ size:size_];
    if(!f) {
      f=[UIFont systemFontOfSize:size_];
    }
    iosProxy_=f;
  }
  #endif
  return iosProxy_;
}

- (id)getProxy {
  return proxy_;
}

- (float)getRelativeSize {
  return relativeSize_;
}

- (int)getSize {
  return (int) size_;
}

- (float)getSize2D {
  return size_;
}

- (int)getStyle {
  return style_;
}

- (BOOL)isBold {
  return [RAREUIFont isBoldWithInt:style_];
}

- (BOOL)isItalic {
  return [RAREUIFont isItalicWithInt:style_];
}

- (BOOL)isStrikeThrough {
  return strikethrough_;
}

- (BOOL)isUnderline {
  return underline_;
}

- (void)setBaseSizeWithFloat:(float)baseSize {
  self->baseSize_ = baseSize;
}

- (void)setRelativeSizeWithFloat:(float)relativeSize {
}

- (void)setStrikeThroughWithBoolean:(BOOL)strikeThrough {
  self->strikethrough_ = strikeThrough;
}

- (void)setUnderlineWithBoolean:(BOOL)underline {
  self->underline_ = underline;
}

- (NSString *)description {
  JavaLangStringBuilder *sb = [[JavaLangStringBuilder alloc] init];
  (void) [((JavaLangStringBuilder *) nil_chk([sb appendWithNSString:@"font-family"])) appendWithNSString:@": "];
  if ([self isItalic]) {
    (void) [sb appendWithNSString:@"italic "];
  }
  if ([self isBold]) {
    (void) [sb appendWithNSString:@"bold "];
  }
  (void) [((JavaLangStringBuilder *) nil_chk([((JavaLangStringBuilder *) nil_chk([((JavaLangStringBuilder *) nil_chk([sb appendWithInt:[self getSize]])) appendWithNSString:@" "])) appendWithNSString:[self getFamily]])) appendWithNSString:@";"];
  return [sb description];
}

- (void)initialize__ {
  CTFontRef font= (__bridge CTFontRef)proxy_;
  CTFontSymbolicTraits traits = CTFontGetSymbolicTraits(font);
  size_=CTFontGetSize(font);
  family_=CFBridgingRelease(CTFontCopyFamilyName(font));
  name_=CFBridgingRelease(CTFontCopyPostScriptName(font));
  if((traits & kCTFontBoldTrait)!=0) {
    style_|=1;
  }
  if((traits & kCTFontItalicTrait)!=0) {
    style_|=2;
  }
  if((traits & kCTFontMonoSpaceTrait)!=0) {
    monospace_=YES;
  }
}

+ (id)deriveFontWithId:(id)base
             withFloat:(float)size
           withBoolean:(BOOL)bold
           withBoolean:(BOOL)italic {
  CTFontRef font= (__bridge CTFontRef)base;
  if(!bold && !italic) {
    CTFontDescriptorRef fd= CTFontCopyFontDescriptor(font);
    if(fd) {
      font= CTFontCreateWithFontDescriptor(fd, size, NULL);
      CFRelease(fd);
    }
    else {
      font=nil;
    }
  }
  else {
    CTFontSymbolicTraits desiredTrait = bold ? kCTFontBoldTrait : 0 ;
    if(italic) {
      desiredTrait|=kCTFontItalicTrait;
    }
    font = CTFontCreateCopyWithSymbolicTraits(font, size, NULL, desiredTrait, kCTFontBoldTrait|kCTFontItalicTrait);
  }
  if(!font) {
    return base;
  }
  return CFBridgingRelease(font);
}

+ (BOOL)isBoldWithInt:(int)style {
  return (style & RAREUIFont_BOLD_) != 0;
}

+ (BOOL)isItalicWithInt:(int)style {
  return (style & RAREUIFont_ITALIC_) != 0;
}

+ (id)newFontWithNSString:(NSString *)family
                withFloat:(float)size
              withBoolean:(BOOL)bold
              withBoolean:(BOOL)italic {
  NSString* style=nil;
  NSDictionary *attributes;
  if(bold && italic) {
    style=@"Bold-Italic";
  }
  else if(bold) {
    style=@"Bold";
  }
  else if(italic) {
    style=@"Italic";
  }
  if(style) {
    attributes = @{
      (NSString *)kCTFontFamilyNameAttribute : family,
      (NSString *)kCTFontStyleNameAttribute : style,
      (NSString *)kCTFontSizeAttribute : [NSNumber numberWithFloat:size]
    };
  }
  else {
    attributes = @{
      (NSString *)kCTFontFamilyNameAttribute : family,
      (NSString *)kCTFontSizeAttribute : [NSNumber numberWithFloat:size]
    };
  }
  CTFontDescriptorRef descriptor = CTFontDescriptorCreateWithAttributes((__bridge CFDictionaryRef)attributes);
  CTFontRef font = CTFontCreateWithFontDescriptor(descriptor, 0, NULL);
  CFRelease(descriptor);
  if(!font && style) {
    attributes = @{
      (NSString *)kCTFontFamilyNameAttribute : family,
      (NSString *)kCTFontSizeAttribute : [NSNumber numberWithFloat:size]
    };
    
    descriptor = CTFontDescriptorCreateWithAttributes((__bridge CFDictionaryRef)attributes);
    font = CTFontCreateWithFontDescriptor(descriptor, 0, NULL);
    CFRelease(descriptor);
  }
  return CFBridgingRelease(font);
}

- (void)copyAllFieldsTo:(RAREUIFont *)other {
  [super copyAllFieldsTo:other];
  other->baseSize_ = baseSize_;
  other->family_ = family_;
  other->iosProxy_ = iosProxy_;
  other->monospace_ = monospace_;
  other->name_ = name_;
  other->proxy_ = proxy_;
  other->relativeSize_ = relativeSize_;
  other->size_ = size_;
  other->strikethrough_ = strikethrough_;
  other->style_ = style_;
  other->underline_ = underline_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "initWithId:", NULL, NULL, 0x4, NULL },
    { "initWithId:withBoolean:withBoolean:", NULL, NULL, 0x4, NULL },
    { "addToAttributedStringWithId:withInt:withInt:", NULL, "V", 0x101, NULL },
    { "deriveWithBoolean:withBoolean:", NULL, "LRAREUIFont", 0x1, NULL },
    { "deriveBold", NULL, "LRAREUIFont", 0x1, NULL },
    { "deriveBoldItalic", NULL, "LRAREUIFont", 0x1, NULL },
    { "deriveFontWithInt:withFloat:", NULL, "LRAREUIFont", 0x1, NULL },
    { "deriveFontSizeWithFloat:", NULL, "LRAREUIFont", 0x1, NULL },
    { "deriveItalic", NULL, "LRAREUIFont", 0x1, NULL },
    { "deriveRelativeFontWithFloat:", NULL, "LRAREUIFont", 0x1, NULL },
    { "deriveStrikethrough", NULL, "LRAREUIFont", 0x1, NULL },
    { "deriveUnderline", NULL, "LRAREUIFont", 0x1, NULL },
    { "getFamily", NULL, "LNSString", 0x1, NULL },
    { "getIOSProxy", NULL, "LNSObject", 0x101, NULL },
    { "getProxy", NULL, "LNSObject", 0x1, NULL },
    { "isBold", NULL, "Z", 0x1, NULL },
    { "isItalic", NULL, "Z", 0x1, NULL },
    { "isStrikeThrough", NULL, "Z", 0x1, NULL },
    { "isUnderline", NULL, "Z", 0x1, NULL },
    { "initialize__", NULL, "V", 0x102, NULL },
    { "deriveFontWithId:withFloat:withBoolean:withBoolean:", NULL, "LNSObject", 0x10a, NULL },
    { "isBoldWithInt:", NULL, "Z", 0xa, NULL },
    { "isItalicWithInt:", NULL, "Z", 0xa, NULL },
    { "newFontWithNSString:withFloat:withBoolean:withBoolean:", NULL, "LNSObject", 0x10a, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "BOLD_", NULL, 0x9, "I" },
    { "ITALIC_", NULL, 0x9, "I" },
    { "PLAIN_", NULL, 0x9, "I" },
    { "family_", NULL, 0x0, "LNSString" },
    { "name_", NULL, 0x0, "LNSString" },
    { "style_", NULL, 0x0, "I" },
    { "relativeSize_", NULL, 0x4, "F" },
    { "baseSize_", NULL, 0x4, "F" },
    { "monospace_", NULL, 0x4, "Z" },
    { "proxy_", NULL, 0x4, "LNSObject" },
    { "size_", NULL, 0x4, "F" },
    { "strikethrough_", NULL, 0x4, "Z" },
    { "underline_", NULL, 0x4, "Z" },
  };
  static J2ObjcClassInfo _RAREUIFont = { "UIFont", "com.appnativa.rare.ui", NULL, 0x1, 24, methods, 13, fields, 0, NULL};
  return &_RAREUIFont;
}

@end
