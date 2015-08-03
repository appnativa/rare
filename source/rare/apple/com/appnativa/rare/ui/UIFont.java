/*
 * Copyright appNativa Inc. All Rights Reserved.
 *
 * This file is part of the Real-time Application Rendering Engine (RARE).
 *
 * RARE is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */

package com.appnativa.rare.ui;

/*-[
 #if TARGET_OS_IPHONE
 #import <UIKit/UIKit.h>
 #else
 #import <AppKit/AppKit.h>
 #endif
 #import <CoreText/CoreText.h>
 ]-*/

/**
 *
 * @author Don DeCoteau
 */
public class UIFont {
  public static int BOLD   = 1;
  public static int ITALIC = 2;
  public static int PLAIN  = 0;
  String            family;
  String            name;
  int               style;
  protected float   relativeSize = 1f;
  protected float   baseSize;
  protected boolean monospace;
  protected Object  proxy;
  protected float   size;
  protected boolean strikethrough;
  protected boolean underline;
  private Object    iosProxy;

  public UIFont(String family, int style, float size) {
    this(newFont(family, size, isBold(style), isItalic(style)));
    this.style = style;
  }

  public UIFont(String family, int style, int size) {
    this(newFont(family, size, isBold(style), isItalic(style)));
  }

  public UIFont(UIFont f) {
    this(f.proxy, f.underline, f.strikethrough);
  }

  public UIFont(UIFont font, float rs) {
    this(font);
    relativeSize = rs;
  }

  public UIFont(UIFont font, float rs, float bs) {
    this(font);
    relativeSize = rs;
    baseSize     = bs;
  }

  protected UIFont(Object proxy) {
    this.proxy = proxy;
    initialize();
    baseSize = getSize2D();
  }

  protected UIFont(Object proxy, boolean underline, boolean strikethrough) {
    this.proxy         = proxy;
    this.underline     = underline;
    this.strikethrough = strikethrough;
    initialize();
    baseSize = getSize2D();
  }

  public native void addToAttributedString(Object astring, int offset, int length)
  /*-[
    NSMutableAttributedString* s=(NSMutableAttributedString*)astring;
    if(length==-1) {
      length=s.length;
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
  ]-*/
  ;

  public UIFont derive(boolean strikethrough, boolean underlined) {
    UIFont f = new UIFont(this);

    f.setStrikeThrough(strikethrough);
    f.setUnderline(underlined);

    return f;
  }

  public UIFont deriveBold() {
    if (isBold()) {
      return this;
    }

    UIFont f = new UIFont(deriveFont(proxy, baseSize, true, false));

    f.setUnderline(underline);
    f.setStrikeThrough(strikethrough);

    return f;
  }

  public UIFont deriveBoldItalic() {
    if (isBold() || isItalic()) {
      return this;
    }

    UIFont f = new UIFont(deriveFont(proxy, baseSize, true, false));

    f.setUnderline(underline);
    f.setStrikeThrough(strikethrough);

    return f;
  }

  public UIFont deriveFont(int style, float size) {
    if ((this.size == size) && (this.style == style)) {
      return this;
    }

    UIFont f = new UIFont(deriveFont(proxy, size, isBold(style), isItalic(style)));

    f.setUnderline(underline);
    f.setStrikeThrough(strikethrough);

    return f;
  }

  public UIFont deriveFontSize(float size) {
    if (this.size == size) {
      return this;
    }

    UIFont f = new UIFont(deriveFont(proxy, size, isBold(style), isItalic(style)));

    f.setUnderline(underline);
    f.setStrikeThrough(strikethrough);

    return f;
  }

  public UIFont deriveItalic() {
    if (isItalic()) {
      return this;
    }

    UIFont f = new UIFont(deriveFont(proxy, size, isBold(), true));

    f.setUnderline(underline);
    f.setStrikeThrough(strikethrough);

    return f;
  }

  public UIFont deriveRelativeFont(float nrs) {
    float  sz = nrs * baseSize;
    UIFont f  = new UIFont(deriveFont(proxy, sz, isBold(style), isItalic(style)));

    f.relativeSize = nrs;
    f.baseSize     = baseSize;

    return f;
  }

  public UIFont deriveStrikethrough() {
    UIFont f = new UIFont(this);

    f.setStrikeThrough(true);

    return f;
  }

  public UIFont deriveUnderline() {
    UIFont f = new UIFont(this);

    f.setUnderline(true);

    return f;
  }

  /**
   * @return the baseSize
   */
  public float getBaseSize() {
    return baseSize;
  }

  public String getFamily() {
    return family;
  }

  public native Object getIOSProxy()
  /*-[
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
   ]-*/
  ;

  public Object getProxy() {
    return proxy;
  }

  /**
   * @return the relativeSize
   */
  public float getRelativeSize() {
    return relativeSize;
  }

  public int getSize() {
    return (int) size;
  }

  public float getSize2D() {
    return size;
  }

  public int getStyle() {
    return style;
  }

  public boolean isBold() {
    return isBold(style);
  }

  public boolean isItalic() {
    return isItalic(style);
  }

  public boolean isStrikeThrough() {
    return strikethrough;
  }

  public boolean isUnderline() {
    return underline;
  }

  /**
   * @param baseSize
   *          the baseSize to set
   */
  public void setBaseSize(float baseSize) {
    this.baseSize = baseSize;
  }

  /**
   * @param relativeSize
   *          the relativeSize to set
   */
  public void setRelativeSize(float relativeSize) {}

  public void setStrikeThrough(boolean strikeThrough) {
    this.strikethrough = strikeThrough;
  }

  public void setUnderline(boolean underline) {
    this.underline = underline;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();

    sb.append("font-family").append(": ");

    if (isItalic()) {
      sb.append("italic ");
    }

    if (isBold()) {
      sb.append("bold ");
    }

    sb.append(getSize()).append(" ").append(getFamily()).append(";");

    return sb.toString();
  }

  private native void initialize()
  /*-[
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
  ]-*/
  ;

  private native static Object deriveFont(Object base, float size, boolean bold, boolean italic)
  /*-[
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
   ]-*/
  ;

  private static boolean isBold(int style) {
    return (style & BOLD) != 0;
  }

  private static boolean isItalic(int style) {
    return (style & ITALIC) != 0;
  }

  private native static Object newFont(String family, float size, boolean bold, boolean italic)
  /*-[
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
   ]-*/
  ;
}
