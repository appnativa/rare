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

package com.appnativa.rare.scripting;

import com.appnativa.rare.platform.apple.ui.util.ImageUtils;
import com.appnativa.rare.ui.UIBorderIcon;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIFont;
import com.appnativa.rare.ui.UIImage;
import com.appnativa.rare.ui.UITextIcon;
import com.appnativa.rare.ui.border.UILineBorder;
import com.appnativa.rare.ui.effects.iAnimator;
import com.appnativa.rare.ui.effects.iAnimatorValueListener;
import com.appnativa.rare.ui.iPlatformBorder;
import com.appnativa.rare.ui.iPlatformGraphics;
import com.appnativa.rare.ui.iPlatformIcon;

/*-[
#import <CommonCrypto/CommonDigest.h>
#import <CommonCrypto/CommonHMAC.h>
#import <CommonCrypto/CommonCryptor.h>
#import <CommonCrypto/CommonKeyDerivation.h>
#import "com/appnativa/util/Base64.h"
]-*/
public class FunctionHelper {
  public static String aesDecrypt(String val, String password, String salt, int iteration, boolean base64) {
    return aesOperation(true, val, password, salt, iteration, base64);
  }

  public static String aesEncrypt(String val, String password, String salt, int iteration, boolean base64) {
    return aesOperation(false, val, password, salt, iteration, base64);
  }

  public native static String aesOperation(boolean decrypt, String val, String password, String salt, int iteration,
          boolean base64)
  /*-[
    size_t outLength;
    size_t dataLength=[val lengthOfBytesUsingEncoding:NSUTF8StringEncoding];
    NSMutableData *
    cipherData = [NSMutableData dataWithLength:dataLength +
                  kCCBlockSizeAES128];

    CCOperation operation=decrypt ? kCCDecrypt : kCCEncrypt;
    CCCryptorStatus
    result = CCCrypt(operation,
                     kCCAlgorithmAES128, // Algorithm
                     kCCOptionPKCS7Padding, // options
                     password.UTF8String, // key
                     [password lengthOfBytesUsingEncoding:NSUTF8StringEncoding], // keylength
                     nil,// iv
                     val.UTF8String, // key
                     dataLength, // keylength
                     cipherData.mutableBytes, // dataOut
                     cipherData.length, // dataOutAvailable
                     &outLength); // dataOutMoved

    if (result == kCCSuccess) {
      cipherData.length = outLength;
    }
    else {
      [NSException raise:@"Unable to encrypt value" format:@"error code is %d", result];
    }
    if(base64) {
      if([cipherData respondsToSelector:@selector(base64EncodedStringWithOptions:)]) {
        return [cipherData base64EncodedStringWithOptions:0];
      }
      else {
        return [cipherData base64Encoding];
      }
    }

    return [[NSString alloc] initWithData:cipherData encoding:NSISOLatin1StringEncoding];
   ]-*/
  ;

  public static iPlatformIcon createColorIcon(final UIColor color, final int width, final int height,
          final iPlatformBorder border) {
    iPlatformIcon icon = new iPlatformIcon() {
      @Override
      public int getIconWidth() {
        return width;
      }
      @Override
      public int getIconHeight() {
        return height;
      }
      @Override
      public iPlatformIcon getDisabledVersion() {
        return this;
      }
      @Override
      public void paint(iPlatformGraphics g, float x, float y, float width, float height) {
        g.clearRect(color, x, y, width, height);
      }
    };

    if (border == null) {
      return icon;
    }

    return new UIBorderIcon(border, icon);
  }

  public static iPlatformIcon createEmptyIcon(final int width, final int height, final UIColor borderColor) {
    iPlatformIcon ic = new iPlatformIcon() {
      @Override
      public int getIconWidth() {
        return width;
      }
      @Override
      public int getIconHeight() {
        return height;
      }
      @Override
      public iPlatformIcon getDisabledVersion() {
        return this;
      }
      @Override
      public void paint(iPlatformGraphics g, float x, float y, float width, float height) {}
    };

    if (borderColor == null) {
      return ic;
    }

    return new UIBorderIcon(new UILineBorder(borderColor), ic);
  }

  public static UIImage createTextImage(String text, UIFont font, UIColor fg, UIColor bg, iPlatformBorder b,
          boolean square) {
    UITextIcon icon = new UITextIcon(text, fg, font, b);

    if (bg != null) {
      icon.setBackgroundColor(bg);
    }

    return ImageUtils.createImage(icon);
  }

  public static iAnimator createValueAnimator(double start, double end, double inc, boolean accelerate,
          boolean decelerate, iAnimatorValueListener l) {
    return null;
  }

  public native static String generateKey(String password, String salt, int iteration)
  /*-[
   NSMutableData *data = [NSMutableData dataWithLength:kCCKeySizeAES128];
   int  result = CCKeyDerivationPBKDF(kCCPBKDF2,            // algorithm
                                 password.UTF8String,  // password
                                 [password lengthOfBytesUsingEncoding:NSUTF8StringEncoding],  // passwordLength
                                 (uint8_t*)[salt cStringUsingEncoding:NSISOLatin1StringEncoding],           // salt
                                 [salt lengthOfBytesUsingEncoding:NSISOLatin1StringEncoding],          // saltLen
                                 kCCPRFHmacAlgSHA1,    // PRF
                                 iteration,         // rounds
                                 data.mutableBytes, // derivedKey
                                 data.length); // derivedKeyLen

   if(result!=0) {
     [NSException raise:@"Unable to create key" format:@"error code is %d", result];
   }
   return [[NSString alloc] initWithData:data encoding:NSASCIIStringEncoding];
  ]-*/
  ;

  public native static String generateSalt(int bytes)
/*-[
   NSMutableData *data = [NSMutableData dataWithLength:bytes];

   int result = SecRandomCopyBytes(kSecRandomDefault,
   bytes,
   data.mutableBytes);
   if(result!=0) {
     [NSException raise:@"Unable to generate salt" format:@"error code is %d", result];
   }
   return [[NSString alloc] initWithData:data encoding:NSISOLatin1StringEncoding];
  ]-*/
  ;

  public native static String hmacMD5(String val, String key, boolean base64)
  /*-[
     if(!val || !key) {
       return @"";
     }
     const char *cKey  = [key cStringUsingEncoding:NSUTF8StringEncoding];
     const char *cData = [val cStringUsingEncoding:NSUTF8StringEncoding];
     unsigned char digest[CC_MD5_DIGEST_LENGTH];
     CCHmac(kCCHmacAlgMD5, cKey, (CC_LONG)strlen(cKey), cData, (CC_LONG)strlen(cData), digest);
     if(base64) {
        NSData* data=[NSData dataWithBytes:digest length:CC_MD5_DIGEST_LENGTH];
        if([data respondsToSelector:@selector(base64EncodedStringWithOptions:)]) {
          return [data base64EncodedStringWithOptions:0];
        }
        else {
          return [data base64Encoding];
        }
     }
     return [[NSString alloc] initWithBytes:digest
     length:CC_MD5_DIGEST_LENGTH
     encoding:NSISOLatin1StringEncoding];
   ]-*/
  ;

  public static native String hmacSHA(String val, String key, boolean base64)
  /*-[
    if(!val || !key) {
      return @"";
    }
    const char *cKey  = [key cStringUsingEncoding:NSUTF8StringEncoding];
    const char *cData = [val cStringUsingEncoding:NSUTF8StringEncoding];
    unsigned char digest[CC_SHA256_DIGEST_LENGTH];
    CCHmac(kCCHmacAlgSHA256, cKey, (CC_LONG)strlen(cKey), cData, (CC_LONG)strlen(cData), digest);
    if(base64) {
        NSData* data=[NSData dataWithBytes:digest length:CC_SHA256_DIGEST_LENGTH];
        if([data respondsToSelector:@selector(base64EncodedStringWithOptions:)]) {
          return [data base64EncodedStringWithOptions:0];
        }
        else {
          return [data base64Encoding];
        }
    }
    return [[NSString alloc] initWithBytes:digest
                                    length:CC_SHA256_DIGEST_LENGTH
                                  encoding:NSASCIIStringEncoding];
  ]-*/
  ;

  public static native String md5(String val, boolean base64)
  /*-[
     const char *bytes = [val cStringUsingEncoding:NSUTF8StringEncoding];

     uint8_t digest[CC_MD5_DIGEST_LENGTH];

     CC_MD5(bytes, (CC_LONG)strlen(bytes), digest);
     if(base64) {
        NSData* data=[NSData dataWithBytes:digest length:CC_MD5_DIGEST_LENGTH];
        if([data respondsToSelector:@selector(base64EncodedStringWithOptions:)]) {
          return [data base64EncodedStringWithOptions:0];
        }
        else {
          return [data base64Encoding];
        }
     }
     return [[NSString alloc] initWithBytes:digest
                                     length:CC_MD5_DIGEST_LENGTH
                                   encoding:NSISOLatin1StringEncoding];
   ]-*/
  ;

  public native static String sha1(byte[] val, boolean base64)
  /*-[
     const char *bytes = val->buffer_;

     uint8_t digest[CC_SHA1_DIGEST_LENGTH];

     CC_SHA1(bytes, (CC_LONG)val->size_, digest);
     if(base64) {
        NSData* data=[NSData dataWithBytes:digest length:CC_SHA1_DIGEST_LENGTH];
        if([data respondsToSelector:@selector(base64EncodedStringWithOptions:)]) {
          return [data base64EncodedStringWithOptions:0];
        }
        else {
          return [data base64Encoding];
        }
     }
     return [[NSString alloc] initWithBytes:digest
                                     length:CC_SHA1_DIGEST_LENGTH
                                   encoding:NSISOLatin1StringEncoding];
   ]-*/
  ;

  public static native String sha1(String val, boolean base64)
  /*-[
     const char *bytes = [val cStringUsingEncoding:NSUTF8StringEncoding];

     uint8_t digest[CC_SHA1_DIGEST_LENGTH];

     CC_SHA1(bytes, (CC_LONG)strlen(bytes), digest);
     if(base64) {
        NSData* data=[NSData dataWithBytes:digest length:CC_SHA1_DIGEST_LENGTH];
        if([data respondsToSelector:@selector(base64EncodedStringWithOptions:)]) {
          return [data base64EncodedStringWithOptions:0];
        }
        else {
          return [data base64Encoding];
        }
     }
     return [[NSString alloc] initWithBytes:digest
                                     length:CC_SHA1_DIGEST_LENGTH
                                   encoding:NSISOLatin1StringEncoding];
   ]-*/
  ;

  public static String getLocation() {
    return null;
  }

  public static StringBuilder getMethods(Object o, StringBuilder sb) {
    return sb;
  }
}
