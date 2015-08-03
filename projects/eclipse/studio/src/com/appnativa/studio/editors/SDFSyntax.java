/*
 * @(#)SDFSyntax.java
 * 
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.studio.editors;

import com.appnativa.studio.Activator;
import com.appnativa.spot.SPOTNode;
import com.appnativa.util.CharArray;

import org.eclipse.jface.text.rules.ICharacterScanner;
import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.IWhitespaceDetector;
import org.eclipse.jface.text.rules.IWordDetector;
import org.eclipse.jface.text.rules.MultiLineRule;
import org.eclipse.jface.text.rules.Token;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Set;

public class SDFSyntax {
  private static final HashMap<String, String> propMap  = new HashMap<String, String>(1250, 0.25f);
  private static final HashMap<String, String> enumMap  = new HashMap<String, String>(250, 0.25f);
  private static final HashMap<String, String> attMap   = new HashMap<String, String>(200, 0.25f);
  private static final SPOTNode                rootNode = new SPOTNode();

  static {
    try {
      enumMap.put("true", "true");
      enumMap.put("false", "false");

      InputStream in = Activator.class.getResourceAsStream("rare_objects.spot");

      rootNode.read(new InputStreamReader(in));

      SPOTNode x;
      int      len = rootNode.childNodes.size();

      for (int i = 0; i < len; i++) {
        x = rootNode.childNodes.get(i);

        if (x.hasAttributes()) {
          attMap.putAll(x.theAttributes);
        }

        if (x.isContainer()) {
          x.dumpSyntaxEx(propMap, enumMap, attMap);
        }
      }
    } catch(Exception e) {
      e.printStackTrace();
      throw new InternalError();
    }
  }

  public enum NodeContext {
    KEYWORD, VALUE, ATTRIBUTE, END_OF_VALUE, ATTRUBUTE_VALUE, END_OF_ATTRIBUTE_VALUE, COMMA, COLON, EQUAL
  }

  public static SPOTNode elementFor(String name) {
    return rootNode.elementFor(name);
  }

  public static Set<String> getAttributes() {
    return attMap.keySet();
  }

  public static SPOTNode getNode(String name) {
    return rootNode.elementFor(name);
  }

  public static SPOTNode getNodeAt(int index) {
    return rootNode.elementAt(index);
  }

  public static int getNodeCount() {
    return rootNode.childNodes.size();
  }

  public static Set<String> getProperties() {
    return attMap.keySet();
  }

  public static class AttributeRule extends WordRuleEx {
    public AttributeRule(IToken token) {
      super(new DefaultWordDetector(), token);
    }

    @Override
    protected IToken getToken(CharArray ca) {
      return attMap.containsKey(ca.toString())
             ? fDefaultToken
             : null;
    }
  }


  public static class AttributesRule extends aGenericRule {
    public AttributesRule(IToken token) {
      super(token);
    }

    @Override
    public IToken evaluate(ICharacterScanner scanner, boolean resume) {
      int c = 0;

      if (!resume) {
        lc      = 0;
        qc      = 0;
        inQuote = false;
        c       = scanner.read();
        read    = 1;
      } else {
        read = 0;
      }

      if (resume || (c == '[')) {
        if (findCharacter(scanner, ']')) {
          return fToken;
        }
      }

      while(read > 0) {
        read--;
        scanner.unread();
      }

      return Token.UNDEFINED;
    }
  }


  public static class ClassRule implements IPredicateRule {

    /** The column constraint */
    protected CharArray               fBuffer = new CharArray();
    protected IToken                  fToken;
    protected HashMap<String, IToken> wordTokens;

    public ClassRule(IToken token) {
      fToken = token;
    }

    @Override
    public IToken evaluate(ICharacterScanner scanner) {
      return evaluate(scanner, false);
    }

    @Override
    public IToken evaluate(ICharacterScanner scanner, boolean resume) {
      if (resume) {
        IToken t = getToken(fBuffer);

        if (t != null) {
          return t;
        }
      }

      int c = scanner.read();

      if (c != ICharacterScanner.EOF) {
        if (resume || Character.isUpperCase(c)) {
          if (!resume) {
            if (scanner.getColumn() > 1) {
              scanner.unread();
              scanner.unread();
              c = scanner.read();

              if (!Character.isWhitespace(c)) {
                return Token.UNDEFINED;
              }

              c = scanner.read();
            }

            fBuffer.setLength(0);
          }

          do {
            fBuffer.append((char) c);
            c = scanner.read();
          } while((c != ICharacterScanner.EOF) && Character.isLetter(c));

          scanner.unread();

          IToken token = getToken(fBuffer);

          if (token != null) {
            return token;
          }

          unreadBuffer(scanner);

          return Token.UNDEFINED;
        }

        scanner.unread();
      }

      return Token.UNDEFINED;
    }

    @Override
    public IToken getSuccessToken() {
      return fToken;
    }

    /**
     * Returns the characters in the buffer to the scanner.
     *
     * @param scanner
     *          the scanner to be used
     */
    protected void unreadBuffer(ICharacterScanner scanner) {
      for (int i = fBuffer.length() - 1; i >= 0; i--) {
        scanner.unread();
      }
    }

    protected IToken getToken(CharArray ca) {
      return (rootNode.elementFor(ca.toString()) != null)
             ? fToken
             : null;
    }
  }


  public static class ConstantRule extends WordRuleEx {
    protected IToken fToken;

    public ConstantRule(IToken token) {
      super(new DefaultWordDetector(), Token.UNDEFINED);
      fToken = token;
    }

    @Override
    protected IToken getToken(CharArray ca) {
      return enumMap.containsKey(ca.toString())
             ? fToken
             : null;
    }
  }


  public static class DefaultWordDetector implements IWordDetector {
    @Override
    public boolean isWordPart(char ch) {
      return Character.isJavaIdentifierPart(ch);
    }

    @Override
    public boolean isWordStart(char ch) {
      return Character.isJavaIdentifierStart(ch);
    }
  }


  public static class MatchingRule implements IRule {
    IToken fToken;

    public MatchingRule(IToken token) {
      fToken = token;
    }

    public IToken evaluate(ICharacterScanner scanner) {
      while(scanner.read() != ICharacterScanner.EOF) {}
      ;

      return fToken;
    }
  }


  public static class NonMatchingRule implements IPredicateRule {
    public NonMatchingRule() {
      super();
    }

    public IToken evaluate(ICharacterScanner scanner) {
      return Token.UNDEFINED;
    }

    public IToken evaluate(ICharacterScanner scanner, boolean resume) {
      return Token.UNDEFINED;
    }

    public IToken getSuccessToken() {
      return Token.UNDEFINED;
    }
  }


  public static class NumberRuleEx implements IRule {
    @Override
    public IToken evaluate(ICharacterScanner scanner) {
      int     c         = scanner.read();
      int     lc        = -1;
      int     nc        = 0;
      int     readCount = 1;
      boolean dotFound  = true;
      boolean xFound    = false;

      if (Character.isDigit(c) || (c == '-') || (c == '+') || (c == '*')) {
        do {
          lc = c;
          c  = scanner.read();
          readCount++;

          if ((c == ICharacterScanner.EOF) || Character.isWhitespace(c)) {
            if ((lc == 'x') || (lc == '.')) {
              break;
            }

            return TokenID.NUMERIC_LITERAL;
          }

          if (Character.isDigit(c)) {
            continue;
          }

          if (c == 'x') {
            if ((lc != '0') || (readCount != 2)) {
              break;
            }

            xFound = true;

            continue;
          }

          if (c == '.') {
            if (dotFound || xFound) {
              break;
            }

            dotFound = true;

            continue;
          }

          if (c == '%') {
            return TokenID.NUMERIC_LITERAL;
          }

          if ((c == 'c') || (c == 'i') || (c == 'p') || (c == 'l') || (c == 'd')) {
            nc = scanner.read();
            readCount++;

            if (nc == ICharacterScanner.EOF) {
              break;
            }

            if (isUnit((char) c, (char) nc)) {
              return TokenID.NUMERIC_LITERAL;
            }

            if ((c == 'd') && (nc == 'l')) {
              nc = scanner.read();
              readCount++;

              if (nc == ICharacterScanner.EOF) {
                break;
              }

              if (nc == 'u') {
                return TokenID.NUMERIC_LITERAL;
              }
            }
          }

          break;
        } while(true);
      }

      for (int i = 0; i < readCount; i++) {
        scanner.unread();
      }

      return Token.UNDEFINED;
    }

    private boolean isUnit(char pc, char c) {
      switch(pc) {
        case 'c' :
          if ((c == 'h') || (c == 'm')) {
            return true;
          }

          break;

        case 'i' :
          if (c == 'n') {
            return true;
          }

          break;

        case 'p' :
          if ((c == 'x') || (c == 't') || (c == 'c')) {
            return true;
          }

          break;

        case 'l' :
          if (c == 'n') {
            return true;
          }

          break;

        default :
          break;
      }

      return false;
    }
  }


  public static class OperatorRule implements IRule {
    protected char   fOperators[];
    protected IToken fToken;

    OperatorRule(char[] operators, IToken token) {
      fOperators = operators;
      fToken     = token;
    }

    @Override
    public IToken evaluate(ICharacterScanner scanner) {
      int       c   = scanner.read();
      final int len = fOperators.length;

      for (int i = 0; i < len; i++) {
        if (fOperators[i] == c) {
          return fToken;
        }
      }

      scanner.unread();

      return Token.UNDEFINED;
    }
  }


  public static class PreformattedRule extends MultiLineRule {
    private char      defEndTag[] = new char[] { '>', '>' };
    private CharArray sb          = new CharArray();

    public PreformattedRule(IToken token) {
      super("<<", ">>", token);
      fBreaksOnEOF = false;
    }

    protected IToken doEvaluate(ICharacterScanner scanner, boolean resume) {
      if (resume) {
        if (endSequenceDetected(scanner)) {
          return fToken;
        }
      } else {
        fEndSequence = defEndTag;

        int c = scanner.read();

        if (c == fStartSequence[0]) {
          if (sequenceDetected(scanner, fStartSequence, false)) {
            c = scanner.read();

            if ((c != ICharacterScanner.EOF) &&!Character.isWhitespace(c)) {
              sb.setLength(0);

              do {
                sb.append((char) c);
                c = scanner.read();
              } while((c != ICharacterScanner.EOF) &&!Character.isWhitespace(c));

              if (Character.isWhitespace(c)) {
                fEndSequence = sb.toCharArray();
              } else {
                for (int i = sb.length() - 1; i >= 0; i--) {
                  scanner.unread();
                }
              }
            } else if (c != ICharacterScanner.EOF) {
              scanner.unread();
            }

            if (endSequenceDetected(scanner)) {
              return fToken;
            }
          }
        }
      }

      scanner.unread();

      return Token.UNDEFINED;
    }
  }


  public static class PropertyRule extends WordRuleEx {
    IToken fToken;

    public PropertyRule(IToken token) {
      super(new PropertyDetector(), Token.UNDEFINED);
      fToken = token;
    }

    @Override
    protected IToken getToken(CharArray ca) {
      int n = ca.indexOf('-');
      int p = 0;

      while(n != -1) {
        if (!propMap.containsKey(ca.substring(p, n))) {
          return null;
        }

        p = n + 1;
        n = ca.indexOf('-', p);
      }

      if (!propMap.containsKey(ca.substring(p))) {
        return null;
      }

      return fToken;
    }
  }


  public static class WhiteSpace implements IWhitespaceDetector {
    @Override
    public boolean isWhitespace(char ch) {
      return Character.isWhitespace(ch);
    }
  }


  public static class WordRuleEx implements IRule {

    /** Internal setting for the uninitialized column constraint */
    protected static final int UNDEFINED = -1;

    /** The column constraint */
    protected int       fColumn = UNDEFINED;
    protected CharArray fBuffer = new CharArray();

    /**
     * The default token to be returned on success and if nothing else has been
     * specified.
     */
    protected IToken fDefaultToken;

    /** The word detector used by this rule */
    protected IWordDetector           fDetector;
    protected HashMap<String, IToken> wordTokens;

    public WordRuleEx(IWordDetector detector, IToken token) {
      fDetector     = detector;
      fDefaultToken = token;
    }

    public void addWord(String word, IToken token) {
      if (wordTokens == null) {
        wordTokens = new HashMap<String, IToken>();
      }

      wordTokens.put(word, token);
    }

    @Override
    public IToken evaluate(ICharacterScanner scanner) {
      int c = scanner.read();

      if (fDetector.isWordStart((char) c)) {
        int col = scanner.getColumn();

        if (col > 2) {
          scanner.unread();
          scanner.unread();

          if (!Character.isWhitespace(scanner.read())) {
            return Token.UNDEFINED;
          }

          scanner.read();
        }

        if ((fColumn == UNDEFINED) || (fColumn == col - 1)) {
          fBuffer.setLength(0);

          do {
            fBuffer.append((char) c);
            c = scanner.read();
          } while((c != ICharacterScanner.EOF) && fDetector.isWordPart((char) c));

          scanner.unread();

          IToken token = getToken(fBuffer);

          if (token != null) {
            return token;
          }

          if (fDefaultToken.isUndefined()) {
            unreadBuffer(scanner);
          }

          return fDefaultToken;
        }
      }

      scanner.unread();

      return Token.UNDEFINED;
    }

    /**
     * Returns the characters in the buffer to the scanner.
     *
     * @param scanner
     *          the scanner to be used
     */
    protected void unreadBuffer(ICharacterScanner scanner) {
      for (int i = fBuffer.length() - 1; i >= 0; i--) {
        scanner.unread();
      }
    }

    protected IToken getToken(CharArray ca) {
      String s = ca.toString();

      return (wordTokens == null)
             ? null
             : wordTokens.get(s);
    }
  }


  public static abstract class aGenericRule implements IPredicateRule {
    boolean          inQuote;
    int              lc;
    int              qc;
    int              read;
    protected IToken fToken;

    public aGenericRule(IToken token) {
      fToken = token;
    }

    @Override
    public IToken evaluate(ICharacterScanner scanner) {
      return evaluate(scanner, false);
    }

    @Override
    public IToken getSuccessToken() {
      return fToken;
    }

    protected boolean findCharacter(ICharacterScanner scanner, char find) {
      int c;

      while(true) {
        c = scanner.read();
        read++;

        if (c == ICharacterScanner.EOF) {
          break;
        }

        if ((c == '\'') || (c == '\"')) {
          if (!inQuote) {
            inQuote = true;
            qc      = c;
          } else if (qc == c) {
            inQuote = false;
            qc      = 0;
          }

          lc = c;

          continue;
        }

        if (c == '\\') {
          if ((lc == qc) &&!inQuote) {
            inQuote = true;
            lc      = c;

            continue;
          }
        }

        if (!inQuote) {
          if (c == find) {
            return true;
          }
        }

        lc = c;
      }

      return false;
    }
  }


  static class PropertyDetector implements IWordDetector {
    @Override
    public boolean isWordPart(char ch) {
      return (ch == '-') || Character.isJavaIdentifierPart(ch);
    }

    @Override
    public boolean isWordStart(char ch) {
      return Character.isJavaIdentifierStart(ch);
    }
  }
}
