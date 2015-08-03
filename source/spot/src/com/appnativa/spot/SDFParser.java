/*
 * Copyright SparseWare Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.appnativa.spot;

import com.appnativa.util.CharArray;
import com.appnativa.util.CharScanner;
import com.appnativa.util.ReaderTokenizer;

import java.io.IOException;
import java.io.Reader;

import java.text.ParseException;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author Don DeCoteau
 */
public class SDFParser {
  public static final int PARSER_CONTEXT_A_NAME      = 12;
  public static final int PARSER_CONTEXT_A_VALUE     = 13;
  public static final int PARSER_CONTEXT_BODY        = 3;
  public static final int PARSER_CONTEXT_COLON       = 5;
  public static final int PARSER_CONTEXT_COLON_COLON = 6;
  public static final int PARSER_CONTEXT_COMMAND     = 2;
  public static final int PARSER_CONTEXT_EOA         = 15;
  public static final int PARSER_CONTEXT_EOB         = 11;
  public static final int PARSER_CONTEXT_EOV         = 10;
  public static final int PARSER_CONTEXT_IDENTIFIER  = 4;
  public static final int PARSER_CONTEXT_MODIFIER    = 14;
  public static final int PARSER_CONTEXT_REF_NAME    = 8;
  public static final int PARSER_CONTEXT_REF_VALUE   = 9;
  public static final int PARSER_CONTEXT_ROOT        = 1;
  public static final int PARSER_CONTEXT_TYPE        = 7;
  private static char     defEndHeredoc[]            = { '>', '>' };
  Tokenizer               tokenizer;
  private boolean         _stop;
  private String          fileName;
  private boolean         ignoreComments;

  /**
   * Creates a new instance of SDFParser
   */
  public SDFParser() {
    tokenizer      = null;
    fileName       = null;
    ignoreComments = true;
    _stop          = false;
  }

  /**
   * Creates a new instance of SDFParser
   *
   * @param r the reader
   */
  public SDFParser(Reader r) {
    tokenizer      = new Tokenizer(r);
    fileName       = null;
    ignoreComments = true;
    _stop          = false;
  }

  public void parse(SDFParser.iCallback cb) throws IOException {
    int       ctx          = PARSER_CONTEXT_ROOT;
    int       tok          = 0;
    Tokenizer tz           = tokenizer;
    String    identifier   = null;
    String    refName      = null;
    String    refValue     = null;
    String    value        = null;
    Map       attributes   = null;
    int       depth        = 0;
    boolean   preformatted = false;
    boolean   blockend     = false;
    String    aname        = null;
    String    comment      = null;
    String    pretag       = null;
    SDFNode   node;

    _stop = false;

    while(!_stop && (tok = tz.nextToken()) != ReaderTokenizer.TT_EOF) {
      switch(tok) {
        case Tokenizer.TT_COMMENT :
          comment = tz.sval;
        //$FALL-THROUGH$
        case ReaderTokenizer.TT_EOL :
          switch(ctx) {
            case PARSER_CONTEXT_COMMAND :
              ctx = PARSER_CONTEXT_ROOT;

              if (comment != null) {
                cb.addComment(comment);
                comment = null;
              }

              break;

            case PARSER_CONTEXT_BODY :
              if (comment != null) {
                cb.addComment(comment);
                comment = null;
              }

              break;

            case PARSER_CONTEXT_TYPE :
            case PARSER_CONTEXT_EOA :
            case PARSER_CONTEXT_EOB :
            case PARSER_CONTEXT_EOV :
              node = null;

              if (blockend) {
                node = cb.endBlock(attributes);
              } else {
                if (value == null) {
                  if ((identifier == null) && (comment != null)) {
                    cb.addComment(comment);
                  }

                  node = cb.addValue(null, identifier, preformatted, pretag, attributes);
                } else {
                  node = cb.addValue(identifier, value, preformatted, pretag, attributes);
                }
              }

              if ((comment != null) && (node != null)) {
                node.setNodeComment(comment);
              }

              comment      = null;
              refName      = null;
              refValue     = null;
              identifier   = null;
              value        = null;
              ctx          = PARSER_CONTEXT_BODY;
              preformatted = false;
              aname        = null;
              blockend     = false;
              attributes   = null;

              if (depth == 0) {
                _stop = true;
              }

              break;
          }

          break;

        case ReaderTokenizer.TT_WORD :
        case '\'' :
        case '\"' :
          switch(ctx) {
            case PARSER_CONTEXT_COMMAND :
              identifier = tz.sval;
              value      = tz.readCommandParams();
              cb.handleCommand(identifier, value);
              identifier = null;
              value      = null;
              ctx        = PARSER_CONTEXT_ROOT;

              break;

            case PARSER_CONTEXT_ROOT :
            case PARSER_CONTEXT_BODY :
              identifier = tz.sval;
              ctx        = PARSER_CONTEXT_TYPE;

              break;

            case PARSER_CONTEXT_TYPE :
              value = tz.sval;
              ctx   = PARSER_CONTEXT_EOV;

              break;

            case PARSER_CONTEXT_REF_NAME :
              if ((tok == '\'') || (tok == '\"')) {
                throw exception(unexpected((char) tok) + " ctx=" + ctx);
              }

              refName = tz.sval;
              ctx     = PARSER_CONTEXT_TYPE;

              break;

            case PARSER_CONTEXT_REF_VALUE :
              refValue = tz.sval;
              ctx      = PARSER_CONTEXT_TYPE;

              if ((tok == '\'') || (tok == '\"')) {
                refValue = '\'' + refValue + '\'';
              }

              break;

            case PARSER_CONTEXT_A_NAME :
              if ((tok == '\'') || (tok == '\"')) {
                throw exception(unexpected((char) tok) + " ctx=" + ctx);
              }

              aname = tz.sval;
              ctx   = PARSER_CONTEXT_A_VALUE;

              break;

            case PARSER_CONTEXT_A_VALUE :
              if (aname == null) {
                unexpected(tz.sval);
              }

              if (attributes == null) {
                attributes = new LinkedHashMap();
              }

              attributes.put(aname, tz.sval);
              aname = null;
              ctx   = PARSER_CONTEXT_A_NAME;

              break;
          }

          break;

        case ':' :
          switch(ctx) {
            case PARSER_CONTEXT_BODY :
            case PARSER_CONTEXT_TYPE :
              ctx = PARSER_CONTEXT_TYPE;

              break;

            default :
              throw exception(unexpected((char) tok) + " ctx=" + ctx);
          }

          break;

        case ';' :
          switch(ctx) {
            case PARSER_CONTEXT_TYPE :
            case PARSER_CONTEXT_EOV :
            case PARSER_CONTEXT_EOB :
            case PARSER_CONTEXT_EOA :
              if (blockend) {
                cb.endBlock(attributes);
              } else {
                if ((ctx == PARSER_CONTEXT_TYPE) || (value == null)) {
                  cb.addValue(null, identifier, preformatted, pretag, attributes);
                } else {
                  cb.addValue(identifier, value, preformatted, pretag, attributes);
                }
              }

              refName      = null;
              refValue     = null;
              identifier   = null;
              value        = null;
              ctx          = PARSER_CONTEXT_BODY;
              preformatted = false;
              aname        = null;
              blockend     = false;
              attributes   = null;

              break;

            case PARSER_CONTEXT_A_NAME :
            case PARSER_CONTEXT_A_VALUE :
              throw exception(unmatched(']'));

            default :
              throw exception(unexpected((char) tok) + " ctx=" + ctx);
          }

          break;

        case '<' :
          switch(tz.nextToken()) {
            case '<' :
              if (ctx == PARSER_CONTEXT_TYPE) {
                value        = tz.readPreformatted();
                ctx          = PARSER_CONTEXT_EOV;
                preformatted = true;
                pretag       = tz.preTag;

                break;
              }
            //$FALL-THROUGH$
            default :
              throw exception(unexpected((char) tok) + " ctx=" + ctx);
          }

          break;

        case '>' :
          throw exception(unexpected((char) tok) + " ctx=" + ctx);

        case '{' :
          switch(ctx) {
            case PARSER_CONTEXT_ROOT :
            case PARSER_CONTEXT_BODY :
            case PARSER_CONTEXT_TYPE :
              if (comment != null) {
                cb.addComment(comment);
                comment = null;
              }

              cb.startBlock(identifier, refName, refValue);
              depth++;
              refName      = null;
              refValue     = null;
              identifier   = null;
              value        = null;
              ctx          = PARSER_CONTEXT_BODY;
              preformatted = false;
              aname        = null;
              blockend     = false;
              attributes   = null;
              comment      = null;

              break;

            default :
              throw exception(unexpected((char) tok) + " ctx=" + ctx);
          }

          break;

        case '}' :
          switch(ctx) {
            case PARSER_CONTEXT_BODY :
              depth--;

              if (depth < 0) {
                throw exception(unexpected((char) tok) + " ctx=" + ctx);
              }

              ctx      = PARSER_CONTEXT_EOB;
              blockend = true;

              break;

            case PARSER_CONTEXT_EOV :
            case PARSER_CONTEXT_EOA :
            case PARSER_CONTEXT_TYPE :
            case PARSER_CONTEXT_EOB :
              if (blockend) {
                cb.endBlock(attributes);
              } else {
                if ((ctx == PARSER_CONTEXT_TYPE) || (value == null)) {
                  cb.addValue(null, identifier, preformatted, pretag, attributes);
                } else {
                  cb.addValue(identifier, value, preformatted, pretag, attributes);
                }
              }

              refName      = null;
              refValue     = null;
              identifier   = null;
              value        = null;
              preformatted = false;
              aname        = null;
              depth--;

              if (depth < 0) {
                throw exception(unexpected((char) tok) + " ctx=" + ctx);
              }

              ctx        = PARSER_CONTEXT_EOB;
              blockend   = true;
              attributes = null;

              break;

            default :
              throw exception(unexpected((char) tok) + " ctx=" + ctx);
          }

          break;

        case ',' :
          switch(ctx) {
            case PARSER_CONTEXT_A_NAME :
              if (aname != null) {
                if (attributes == null) {
                  attributes = new LinkedHashMap();
                }

                attributes.put(aname, null);
              }

              aname = null;

              break;

            default :
              throw exception(unexpected((char) tok) + " ctx=" + ctx);
          }

          break;

        case '[' :
          switch(ctx) {
            case PARSER_CONTEXT_TYPE :
            case PARSER_CONTEXT_EOV :
            case PARSER_CONTEXT_EOB :
              ctx   = PARSER_CONTEXT_A_NAME;
              aname = null;

              break;

            default :
              throw exception(unexpected((char) tok) + " ctx=" + ctx);
          }

          break;

        case ']' :
          switch(ctx) {
            case PARSER_CONTEXT_A_NAME :
              ctx = PARSER_CONTEXT_EOA;

              if (aname != null) {
                if (attributes == null) {
                  attributes = new LinkedHashMap();
                }

                attributes.put(aname, null);
                ctx = PARSER_CONTEXT_EOA;
              }

              aname = null;

              break;

            default :
              throw exception(unexpected((char) tok) + " ctx=" + ctx);
          }

          break;

        case '$' :
          switch(ctx) {
            case PARSER_CONTEXT_TYPE :
              ctx     = PARSER_CONTEXT_REF_VALUE;
              refName = tz.sval;

              break;

            default :
              throw exception(unexpected((char) tok) + " ctx=" + ctx);
          }

          break;

        case '/' :
        case '@' :
          switch(ctx) {
            case PARSER_CONTEXT_ROOT :
              ctx = PARSER_CONTEXT_COMMAND;

              break;

            case PARSER_CONTEXT_TYPE :
              ctx     = PARSER_CONTEXT_REF_NAME;
              refName = tz.sval;

              break;

            default :
              throw exception(unexpected((char) tok) + " ctx=" + ctx);
          }

          break;
      }
    }

    switch(ctx) {
      case PARSER_CONTEXT_COMMAND :
        break;

      case PARSER_CONTEXT_TYPE :
      case PARSER_CONTEXT_EOV :
      case PARSER_CONTEXT_EOA :
      case PARSER_CONTEXT_EOB :
        if (blockend) {
          cb.endBlock(attributes);
        } else {
          cb.addValue(identifier, value, preformatted, pretag, attributes);

          throw exception(unexpectedEOF());
        }

        break;
    }

    if (depth != 0) {
      throw exception(unexpectedEOF() + " ctx=" + ctx);
    }
  }

  public void stopParsing() {
    _stop = true;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  public void setIgnoreComments(boolean ignore) {
    this.ignoreComments = ignore;
    tokenizer.setReturnComments(!ignore);
  }

  public void setReader(Reader r) {
    tokenizer = new Tokenizer(r);
  }

  public String getFileName() {
    return fileName;
  }

  public int getTokenizerLineNumber() {
    return tokenizer.lineno();
  }

  public boolean isIgnoreComments() {
    return ignoreComments;
  }

  RuntimeException exception(String s) {
    if (fileName != null) {
      s += " (" + fileName + ")";
    }

    return new RuntimeException(s);
  }

  String unexpected(char c) {
    return String.format("Unexpected character '%s' on line #%d", String.valueOf(c), tokenizer.lineno());
  }

  String unexpected(String c) {
    return String.format("Unexpected identifier '%s' on line #%d", c, tokenizer.lineno());
  }

  String unexpectedEOF() {
    return "Unexpected end of file reached";
  }

  String unmatched(char c) {
    return String.format("Unmatched '%s' found on line #%d", String.valueOf(c), tokenizer.lineno());
  }

  /**
   *
   *
   * @version    0.3, 2007-07-10
   * @author     Don DeCoteau
   */
  public static interface iCallback {
    void addComment(String comment);

    SDFNode addValue(String name, String value, boolean preformatted, String pretag, Map attributes);

    SDFNode endBlock(Map attributes);

    void handleCommand(String cmd, String params);

    SDFNode startBlock(String name, String refName, String refValue);
  }


  class Tokenizer extends ReaderTokenizer {
    String preTag;

    Tokenizer(Reader r) {
      super(r);
      wordChars(33, 33);
      wordChars(35, 35);
      wordChars(37, 38);
      wordChars(40, 43);
      wordChars(45, 46);
      wordChars(48, 57);
      wordChars(63, 63);
      wordChars(95, 95);
      wordChars(124, 124);
      wordChars(126, 126);
      eolIsSignificant(true);
      slashSlashComments(true);
      slashStarComments(true);
    }

    public void init() {
      wordChars('a', 'z');
      wordChars('A', 'Z');
      wordChars(128 + 32, 255);
      whitespaceChars(0, ' ');
      commentChar('/');
      quoteChar('"');
      quoteChar('\'');
      wordChars(33, 33);
      wordChars(35, 35);
      wordChars(37, 38);
      wordChars(40, 43);
      wordChars(45, 46);
      wordChars(48, 57);
      wordChars(63, 63);
      wordChars(95, 95);
      wordChars(124, 124);
      wordChars(126, 126);
      eolIsSignificant(true);
      slashSlashComments(true);
      slashStarComments(true);
    }

    public void parseNumbers() {}

    @SuppressWarnings("resource")
    public String readCommandParams() throws IOException {
      resetSyntax();
      whitespaceChars(10, 10);
      whitespaceChars(13, 13);
      slashSlashComments(false);
      slashStarComments(false);

      int       tok;
      CharArray sb = new CharArray();

      while((tok = nextToken()) != ReaderTokenizer.TT_EOL) {
        sb.append((char) tok);
      }

      init();

      int end = sb.length() - 1;

      if ((end > -1) && (sb.charAt(end) == '\r')) {
        sb.setLength(end);
      }

      sb.trim();

      try {
        return CharScanner.cleanQuoted(sb.A, 0, sb._length);
      } catch(ParseException ex) {
        return sb.toString();
      }
    }

    @SuppressWarnings("resource")
    public String readPreformatted() throws IOException {
      this.resetSyntax();
      eolIsSignificant(false);
      slashSlashComments(false);
      slashStarComments(false);

      int tok;

      preTag = null;

      CharArray sb = new CharArray();
      int       testLen;
      char      edoc[];

      tok = nextToken();

      if (tok < 33) {
        edoc    = defEndHeredoc;
        testLen = 1;

        if (tok == ReaderTokenizer.TT_EOL) {
          pushBack();
        } else {
          stripPreformattedWhitespace();
        }
      } else {
        sb.append((char) tok);

        while((tok = nextToken()) != ReaderTokenizer.TT_EOL) {
          if (tok < 33) {
            break;
          }

          sb.append((char) tok);
        }

        edoc       = sb.toCharArray();
        preTag     = new String(edoc);
        testLen    = edoc.length - 1;
        sb._length = 0;

        if (tok != ReaderTokenizer.TT_EOL) {
          stripWhitespace();
        }
      }

      while((tok = nextToken()) != ReaderTokenizer.TT_EOF) {
        sb.append((char) tok);

        if ((sb._length > testLen) && sb.endsWith(edoc)) {
          sb._length -= edoc.length;

          break;
        }
      }

      init();

      // trim leading and trailing linefeed for default here-doc
      if (edoc == defEndHeredoc) {
        int n = sb.indexOf("\n");

        if (n == 0) {
          sb.deleteCharAt(0);
        } else if ((n == 1) && (sb.charAt(0) == '\n')) {
          sb.remove(0, 2);
        }

        n = sb._length - 1;

        if (n > 0) {
          if (sb.A[n] == '\n') {
            if ((n > 1) && (sb.A[n - 1] == '\r')) {
              sb._length = n - 1;
            } else {
              sb._length = n;
            }
          }
        }
      }

      return sb.toString();
    }

    public String readToEOL() throws IOException {
      resetSyntax();
      whitespaceChars(10, 10);
      whitespaceChars(13, 13);
      slashSlashComments(false);
      slashStarComments(false);

      int           tok;
      StringBuilder sb = new StringBuilder();

      while((tok = nextToken()) != ReaderTokenizer.TT_EOL) {
        sb.append((char) tok);
      }

      init();

      int end = sb.length() - 1;

      if ((end > -1) && (sb.charAt(end) == '\r')) {
        sb.setLength(end);
      }

      return sb.toString();
    }

    private void stripPreformattedWhitespace() throws IOException {
      int tok;

      while((tok = nextToken()) != ReaderTokenizer.TT_EOL) {
        if ((tok > 32) || (tok == ReaderTokenizer.TT_EOF)) {
          pushBack();

          break;
        }
      }

      if (tok == ReaderTokenizer.TT_EOL) {
        pushBack();
      }
    }

    private void stripWhitespace() throws IOException {
      int tok;

      while((tok = nextToken()) != ReaderTokenizer.TT_EOL) {
        if ((tok > 32) || (tok == ReaderTokenizer.TT_EOF)) {
          pushBack();

          break;
        }
      }
    }
  }
}
