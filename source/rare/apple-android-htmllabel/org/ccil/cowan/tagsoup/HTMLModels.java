// This file is part of TagSoup and is Copyright 2002-2008 by John Cowan.
//
// TagSoup is licensed under the Apache License,
// Version 2.0.  You may obtain a copy of this license at
// http://www.apache.org/licenses/LICENSE-2.0 .  You may also have
// additional legal rights not granted by this license.
//
// TagSoup is distributed in the hope that it will be useful, but
// unless required by applicable law or agreed to in writing, TagSoup
// is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
// OF ANY KIND, either express or implied; not even the implied warranty
// of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
// 
// 
// Defines models for HTMLSchema

/**
This interface contains generated constants representing HTML content
models.  Logically, it is part of HTMLSchema, but it is more
convenient to generate the constants into a separate interface.
*/

package org.ccil.cowan.tagsoup;
public interface HTMLModels {

	// Start of model definitions
	public static final int M_AREA = 2;
  public static final int M_BLOCK = 4;
  public static final int M_BLOCKINLINE = 8;
  public static final int M_BODY = 16;
  public static final int M_CELL = 32;
  public static final int M_COL = 64;
  public static final int M_DEF = 128;
  public static final int M_FORM = 256;
  public static final int M_FRAME = 512;
  public static final int M_HEAD = 1024;
  public static final int M_HTML = 2048;
  public static final int M_INLINE = 4096;
  public static final int M_LEGEND = 8192;
  public static final int M_LI = 16384;
  public static final int M_NOLINK = 32768;
  public static final int M_OPTION = 65536;
  public static final int M_OPTIONS = 131072;
  public static final int M_P = 262144;
  public static final int M_PARAM = 524288;
  public static final int M_TABLE = 1048576;
  public static final int M_TABULAR = 2097152;
  public static final int M_TR = 4194304;
	// End of model definitions

	}
