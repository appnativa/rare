//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/guava/sources/com/google/common/base/Ascii.java
//
//  Created by tball on 11/23/13.
//

#import "JreEmulation.h"

#if !ComGoogleCommonBaseAscii_RESTRICT
#define ComGoogleCommonBaseAscii_INCLUDE_ALL 1
#endif
#undef ComGoogleCommonBaseAscii_RESTRICT

#if !defined (_ComGoogleCommonBaseAscii_) && (ComGoogleCommonBaseAscii_INCLUDE_ALL || ComGoogleCommonBaseAscii_INCLUDE)
#define _ComGoogleCommonBaseAscii_

@protocol JavaLangCharSequence;

#define ComGoogleCommonBaseAscii_ACK 6
#define ComGoogleCommonBaseAscii_BEL 7
#define ComGoogleCommonBaseAscii_BS 8
#define ComGoogleCommonBaseAscii_CAN 24
#define ComGoogleCommonBaseAscii_CR 13
#define ComGoogleCommonBaseAscii_DC1 17
#define ComGoogleCommonBaseAscii_DC2 18
#define ComGoogleCommonBaseAscii_DC3 19
#define ComGoogleCommonBaseAscii_DC4 20
#define ComGoogleCommonBaseAscii_DEL 127
#define ComGoogleCommonBaseAscii_DLE 16
#define ComGoogleCommonBaseAscii_EM 25
#define ComGoogleCommonBaseAscii_ENQ 5
#define ComGoogleCommonBaseAscii_EOT 4
#define ComGoogleCommonBaseAscii_ESC 27
#define ComGoogleCommonBaseAscii_ETB 23
#define ComGoogleCommonBaseAscii_ETX 3
#define ComGoogleCommonBaseAscii_FF 12
#define ComGoogleCommonBaseAscii_FS 28
#define ComGoogleCommonBaseAscii_GS 29
#define ComGoogleCommonBaseAscii_HT 9
#define ComGoogleCommonBaseAscii_LF 10
#define ComGoogleCommonBaseAscii_MAX 0x007f
#define ComGoogleCommonBaseAscii_MIN 0x0000
#define ComGoogleCommonBaseAscii_NAK 21
#define ComGoogleCommonBaseAscii_NL 10
#define ComGoogleCommonBaseAscii_NUL 0
#define ComGoogleCommonBaseAscii_RS 30
#define ComGoogleCommonBaseAscii_SI 15
#define ComGoogleCommonBaseAscii_SO 14
#define ComGoogleCommonBaseAscii_SOH 1
#define ComGoogleCommonBaseAscii_SP 32
#define ComGoogleCommonBaseAscii_SPACE 32
#define ComGoogleCommonBaseAscii_STX 2
#define ComGoogleCommonBaseAscii_SUB 26
#define ComGoogleCommonBaseAscii_SYN 22
#define ComGoogleCommonBaseAscii_US 31
#define ComGoogleCommonBaseAscii_VT 11
#define ComGoogleCommonBaseAscii_XOFF 19
#define ComGoogleCommonBaseAscii_XON 17

@interface ComGoogleCommonBaseAscii : NSObject {
}

+ (char)NUL;
+ (char)SOH;
+ (char)STX;
+ (char)ETX;
+ (char)EOT;
+ (char)ENQ;
+ (char)ACK;
+ (char)BEL;
+ (char)BS;
+ (char)HT;
+ (char)LF;
+ (char)NL;
+ (char)VT;
+ (char)FF;
+ (char)CR;
+ (char)SO;
+ (char)SI;
+ (char)DLE;
+ (char)DC1;
+ (char)XON;
+ (char)DC2;
+ (char)DC3;
+ (char)XOFF;
+ (char)DC4;
+ (char)NAK;
+ (char)SYN;
+ (char)ETB;
+ (char)CAN;
+ (char)EM;
+ (char)SUB;
+ (char)ESC;
+ (char)FS;
+ (char)GS;
+ (char)RS;
+ (char)US;
+ (char)SP;
+ (char)SPACE;
+ (char)DEL;
+ (unichar)MIN;
+ (unichar)MAX;
- (id)init;
+ (NSString *)toLowerCaseWithNSString:(NSString *)string;
+ (NSString *)toLowerCaseWithJavaLangCharSequence:(id<JavaLangCharSequence>)chars;
+ (unichar)toLowerCaseWithChar:(unichar)c;
+ (NSString *)toUpperCaseWithNSString:(NSString *)string;
+ (NSString *)toUpperCaseWithJavaLangCharSequence:(id<JavaLangCharSequence>)chars;
+ (unichar)toUpperCaseWithChar:(unichar)c;
+ (BOOL)isLowerCaseWithChar:(unichar)c;
+ (BOOL)isUpperCaseWithChar:(unichar)c;
@end
#endif
