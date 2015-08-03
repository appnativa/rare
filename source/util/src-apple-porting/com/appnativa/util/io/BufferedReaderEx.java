/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appnativa.util.io;

import java.io.BufferedReader;
import java.io.Reader;

/**
 *
 * @author Don DeCoteau
 */
public class BufferedReaderEx extends BufferedReader{

  public BufferedReaderEx(Reader in) {
    super(in);
  }

  public BufferedReaderEx(Reader in, int sz) {
    super(in, sz);
  }
  
}
