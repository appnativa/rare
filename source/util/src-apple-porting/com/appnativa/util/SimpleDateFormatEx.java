/*
 * @(#)SimpleDateFormat.java   2013-01-16
 *
 * Copyright (c) SparseWare Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package java.text;

import java.util.Locale;
/*-[
#import "AppleHelper.h"
#import "APView+Component.h"
]-*/


public class SimpleDateFormatEx extends SimpleDateFormat {

  public SimpleDateFormatEx(String pattern, DateFormatSymbols formatSymbols) {
    super(pattern, formatSymbols);
  }

  public SimpleDateFormatEx(String pattern, Locale locale) {
    super(pattern, locale);
  }

  public SimpleDateFormatEx(String pattern) {
    super(pattern);
  }

  public SimpleDateFormatEx() {
  }

}
