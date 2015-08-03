/*
 * @(#)GenericCharsetHelper.java   2013-01-22
 * 
 * Copyright (c) SparseWare Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.util;

import java.io.UnsupportedEncodingException;

import java.nio.charset.Charset;
/*-[
#import "java/lang/IndexOutOfBoundsException.h"
#import "RAREJREPatches.h"
]-*/

/**
 *
 * @author Don DeCoteau
 */
public class GenericCharsetHelper implements iCharsetHelper {
  protected Charset charset;
  IndexOutOfBoundsException ex;
  /**
   * Creates a new GenericCharsetHelper object.
   *
   * @param charset  the character set
   *
   * @throws  UnsupportedEncodingException if the character set is not supporter
   */
  public GenericCharsetHelper(Charset charset) {
    this.charset = charset;
  }

  /**
   *   Creates a new GenericCharsetHelper object.
   *  
   *   @param cs  the character set
   *  
   *   @throws  UnsupportedEncodingException if the character set is not supporter
   */
  public GenericCharsetHelper(String cs) throws UnsupportedEncodingException {
    this(Charset.forName(cs));
  }

  public iCharsetHelper copy() {
    return new GenericCharsetHelper(charset);
  }

  public int getByteLength(char[] chars, int pos, int len) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  public native byte[] getBytes(String string) /*-[
  NSData* data=[string dataUsingEncoding:self.nsEncoding];
  char* bytes=(char*)data.bytes;
  return [IOSByteArray arrayWithBytes:bytes count:data.length];
   ]-*/;

  public native byte[] getBytes(char[] chars, int charPos, int charLen)  /*-[
  NSString* string=[NSString stringWithCharacters:chars offset:charPos length:charLen];
  return [self getBytesWithNSString:string];
   ]-*/;

  public native int getBytes(char[] chars, int charPos, int charLen, byte[] bytes, int bytePos)  /*-[
  NSString* string=[NSString stringWithCharacters:chars offset:charPos length:charLen];
  IOSByteArray* ba=[string getBytesWithEncoding:self.nsEncoding];
  NSRange range={0,ba.count};
  [ba  arraycopy:range destination:bytes offset:bytePos];
  return (int)ba.count;
   ]-*/;

  public native int getBytes(char[] chars, int charPos, int charLen, ByteArray bytes, int bytePos)  /*-[
  NSString* string=[NSString stringWithCharacters:chars offset:charPos length:charLen];
  IOSByteArray* ba=[string getBytesWithEncoding:self.nsEncoding];
  NSRange range={0,ba.count};
  [bytes ensureCapacityWithInt:(int)(ba.count+bytePos)];
  [ba  arraycopy:range destination:bytes->A_ offset:bytePos];
  return (int)ba.count;
   ]-*/;

  public int getCharLength(byte[] bytes, int pos, int len) throws FormatException {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  public native int getChars(byte[] bytes, int bytePos, int byteLen, char[] chars, int charPos) throws FormatException  /*-[
    NSString* string=[NSString stringWithBytes:bytes offset:bytePos length:byteLen encoding:self.nsEncoding];
    int len=(int)string.length;
    if(len>chars.count-charPos) {
      @throw [[JavaLangIndexOutOfBoundsException alloc] initWithNSString:@"Not enough space in char array to hold data"];
    }
    [string getCharacters:[chars charRefAtIndex:charPos]];
    return len;
   ]-*/;

  public native int getChars(byte[] bytes, int bytePos, int byteLen, CharArray chars, int charPos) throws FormatException  /*-[
  NSString* string=[NSString stringWithBytes:bytes offset:bytePos length:byteLen encoding:self.nsEncoding];
  [chars ensureCapacityWithInt:(int)(string.length+charPos)];
  [chars setLengthWithInt:charPos];
  [chars appendWithNSString:string];
  return (int)string.length;
   ]-*/;

  public String getEncoding() {
    return charset.name();
  }

  public String getString(byte[] bytes) throws FormatException {
    return getString(bytes,0,bytes.length);
  }

  public String getString(byte[] bytes, int offset, int length) throws FormatException {
    try {
      return new String(bytes,offset,length,charset.name());
    } catch (UnsupportedEncodingException ex) {
      throw new FormatException(ex.toString());
    }
  }

  public boolean isByteLenghthSupported() {
    return false;
  }

  public boolean isCharLengthSupported() {
    return false;
  }
  native int nsEncoding() /*-[
  return [charset_ getEncoding];          
  ]-*/;
}
