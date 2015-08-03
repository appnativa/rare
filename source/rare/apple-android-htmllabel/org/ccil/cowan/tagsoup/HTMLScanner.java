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
package org.ccil.cowan.tagsoup;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PushbackReader;
import java.io.Reader;

import org.xml.sax.Locator;
import org.xml.sax.SAXException;

/**
This class implements a table-driven scanner for HTML, allowing for lots of
defects.  It implements the Scanner interface, which accepts a Reader
object to fetch characters from and a ScanHandler object to report lexical
events to.
*/
@SuppressWarnings("unused")
public class HTMLScanner implements Scanner, Locator {

	// Start of state table
	 private static final int S_ANAME = 1;
	  private static final int S_APOS = 2;
	  private static final int S_AVAL = 3;
	  private static final int S_BB = 4;
	  private static final int S_BBC = 5;
	  private static final int S_BBCD = 6;
	  private static final int S_BBCDA = 7;
	  private static final int S_BBCDAT = 8;
	  private static final int S_BBCDATA = 9;
	  private static final int S_CDATA = 10;
	  private static final int S_CDATA2 = 11;
	  private static final int S_CDSECT = 12;
	  private static final int S_CDSECT1 = 13;
	  private static final int S_CDSECT2 = 14;
	  private static final int S_COM = 15;
	  private static final int S_COM2 = 16;
	  private static final int S_COM3 = 17;
	  private static final int S_COM4 = 18;
	  private static final int S_DECL = 19;
	  private static final int S_DECL2 = 20;
	  private static final int S_DONE = 21;
	  private static final int S_EMPTYTAG = 22;
	  private static final int S_ENT = 23;
	  private static final int S_EQ = 24;
	  private static final int S_ETAG = 25;
	  private static final int S_GI = 26;
	  private static final int S_NCR = 27;
	  private static final int S_PCDATA = 28;
	  private static final int S_PI = 29;
	  private static final int S_PITARGET = 30;
	  private static final int S_QUOT = 31;
	  private static final int S_STAGC = 32;
	  private static final int S_TAG = 33;
	  private static final int S_TAGWS = 34;
	  private static final int S_XNCR = 35;
	  private static final int A_ADUP = 1;
	  private static final int A_ADUP_SAVE = 2;
	  private static final int A_ADUP_STAGC = 3;
	  private static final int A_ANAME = 4;
	  private static final int A_ANAME_ADUP = 5;
	  private static final int A_ANAME_ADUP_STAGC = 6;
	  private static final int A_AVAL = 7;
	  private static final int A_AVAL_STAGC = 8;
	  private static final int A_CDATA = 9;
	  private static final int A_CMNT = 10;
	  private static final int A_DECL = 11;
	  private static final int A_EMPTYTAG = 12;
	  private static final int A_ENTITY = 13;
	  private static final int A_ENTITY_START = 14;
	  private static final int A_ETAG = 15;
	  private static final int A_GI = 16;
	  private static final int A_GI_STAGC = 17;
	  private static final int A_LT = 18;
	  private static final int A_LT_PCDATA = 19;
	  private static final int A_MINUS = 20;
	  private static final int A_MINUS2 = 21;
	  private static final int A_MINUS3 = 22;
	  private static final int A_PCDATA = 23;
	  private static final int A_PI = 24;
	  private static final int A_PITARGET = 25;
	  private static final int A_PITARGET_PI = 26;
	  private static final int A_SAVE = 27;
	  private static final int A_SKIP = 28;
	  private static final int A_SP = 29;
	  private static final int A_STAGC = 30;
	  private static final int A_UNGET = 31;
	  private static final int A_UNSAVE_PCDATA = 32;
	  private static int[] statetable = { 1, 47, 5, 22, 1, 61, 4, 3, 1, 62, 6, 28, 1, 0, 27, 1, 1, -1, 6, 21, 1, 32, 4, 24, 1, 10, 4, 24, 1, 9, 4, 24, 2, 39, 7, 34, 2, 0, 27, 2, 2, -1, 8, 21, 2, 32, 29, 2, 2, 10, 29, 2, 2, 9, 29, 2, 3, 34, 28, 31, 3, 39, 28, 2, 3, 62, 8, 28, 3, 0, 27, 32, 3, -1, 8, 21, 3, 32, 28, 3, 3, 10, 28, 3, 3, 9, 28, 3, 4, 67, 28, 5, 4, 0, 28, 19, 4, -1, 28, 21, 5, 68, 28, 6, 5, 0, 28, 19, 5, -1, 28, 21, 6, 65, 28, 7, 6, 0, 28, 19, 6, -1, 28, 21, 7, 84, 28, 8, 7, 0, 28, 19, 7, -1, 28, 21, 8, 65, 28, 9, 8, 0, 28, 19, 8, -1, 28, 21, 9, 91, 28, 12, 9, 0, 28, 19, 9, -1, 28, 21, 10, 60, 27, 11, 10, 0, 27, 10, 10, -1, 23, 21, 11, 47, 32, 25, 11, 0, 27, 10, 11, -1, 32, 21, 12, 93, 27, 13, 12, 0, 27, 12, 12, -1, 28, 21, 13, 93, 27, 14, 13, 0, 27, 12, 13, -1, 28, 21, 14, 62, 9, 28, 14, 93, 27, 14, 14, 0, 27, 12, 14, -1, 28, 21, 15, 45, 28, 16, 15, 0, 27, 16, 15, -1, 10, 21, 16, 45, 28, 17, 16, 0, 27, 16, 16, -1, 10, 21, 17, 45, 28, 18, 17, 0, 20, 16, 17, -1, 10, 21, 18, 45, 22, 18, 18, 62, 10, 28, 18, 0, 21, 16, 18, -1, 10, 21, 19, 45, 28, 15, 19, 62, 28, 28, 19, 91, 28, 4, 19, 0, 27, 20, 19, -1, 28, 21, 20, 62, 11, 28, 20, 0, 27, 20, 20, -1, 28, 21, 22, 62, 12, 28, 22, 0, 27, 1, 22, 32, 28, 34, 22, 10, 28, 34, 22, 9, 28, 34, 23, 0, 13, 23, 23, -1, 13, 21, 24, 61, 28, 3, 24, 62, 3, 28, 24, 0, 2, 1, 24, -1, 3, 21, 24, 32, 28, 24, 24, 10, 28, 24, 24, 9, 28, 24, 25, 62, 15, 28, 25, 0, 27, 25, 25, -1, 15, 21, 25, 32, 28, 25, 25, 10, 28, 25, 25, 9, 28, 25, 26, 47, 28, 22, 26, 62, 17, 28, 26, 0, 27, 26, 26, -1, 28, 21, 26, 32, 16, 34, 26, 10, 16, 34, 26, 9, 16, 34, 27, 0, 13, 27, 27, -1, 13, 21, 28, 38, 14, 23, 28, 60, 23, 33, 28, 0, 27, 28, 28, -1, 23, 21, 29, 62, 24, 28, 29, 0, 27, 29, 29, -1, 24, 21, 30, 62, 26, 28, 30, 0, 27, 30, 30, -1, 26, 21, 30, 32, 25, 29, 30, 10, 25, 29, 30, 9, 25, 29, 31, 34, 7, 34, 31, 0, 27, 31, 31, -1, 8, 21, 31, 32, 29, 31, 31, 10, 29, 31, 31, 9, 29, 31, 32, 62, 8, 28, 32, 0, 27, 32, 32, -1, 8, 21, 32, 32, 7, 34, 32, 10, 7, 34, 32, 9, 7, 34, 33, 33, 28, 19, 33, 47, 28, 25, 33, 60, 27, 33, 33, 63, 28, 30, 33, 0, 27, 26, 33, -1, 19, 21, 33, 32, 18, 28, 33, 10, 18, 28, 33, 9, 18, 28, 34, 47, 28, 22, 34, 62, 30, 28, 34, 0, 27, 1, 34, -1, 30, 21, 34, 32, 28, 34, 34, 10, 28, 34, 34, 9, 28, 34, 35, 0, 13, 35, 35, -1, 13, 21 };

	  private static final String[] debug_actionnames = { "", "A_ADUP", "A_ADUP_SAVE", "A_ADUP_STAGC", "A_ANAME", "A_ANAME_ADUP", "A_ANAME_ADUP_STAGC", "A_AVAL", "A_AVAL_STAGC", "A_CDATA", "A_CMNT", "A_DECL", "A_EMPTYTAG", "A_ENTITY", "A_ENTITY_START", "A_ETAG", "A_GI", "A_GI_STAGC", "A_LT", "A_LT_PCDATA", "A_MINUS", "A_MINUS2", "A_MINUS3", "A_PCDATA", "A_PI", "A_PITARGET", "A_PITARGET_PI", "A_SAVE", "A_SKIP", "A_SP", "A_STAGC", "A_UNGET", "A_UNSAVE_PCDATA" };
	  private static final String[] debug_statenames = { "", "S_ANAME", "S_APOS", "S_AVAL", "S_BB", "S_BBC", "S_BBCD", "S_BBCDA", "S_BBCDAT", "S_BBCDATA", "S_CDATA", "S_CDATA2", "S_CDSECT", "S_CDSECT1", "S_CDSECT2", "S_COM", "S_COM2", "S_COM3", "S_COM4", "S_DECL", "S_DECL2", "S_DONE", "S_EMPTYTAG", "S_ENT", "S_EQ", "S_ETAG", "S_GI", "S_NCR", "S_PCDATA", "S_PI", "S_PITARGET", "S_QUOT", "S_STAGC", "S_TAG", "S_TAGWS", "S_XNCR" };
	// End of state table

	private String thePublicid;			// Locator state
	private String theSystemid;
	private int theLastLine;
	private int theLastColumn;
	private int theCurrentLine;
	private int theCurrentColumn;

	int theState;					// Current state
	int theNextState;				// Next state
	char[] theOutputBuffer = new char[200];	// Output buffer
	int theSize;					// Current buffer size
	int[] theWinMap = {				// Windows chars map
		0x20AC, 0xFFFD, 0x201A, 0x0192, 0x201E, 0x2026, 0x2020, 0x2021,
		0x02C6, 0x2030, 0x0160, 0x2039, 0x0152, 0xFFFD, 0x017D, 0xFFFD,
		0xFFFD, 0x2018, 0x2019, 0x201C, 0x201D, 0x2022, 0x2013, 0x2014,
		0x02DC, 0x2122, 0x0161, 0x203A, 0x0153, 0xFFFD, 0x017E, 0x0178};

	/**
	 * Index into the state table for [state][input character - 2].
	 * The state table consists of 4-entry runs on the form
	 * { current state, input character, action, next state }.
	 * We precompute the index into the state table for all possible
	 * { current state, input character } and store the result in
	 * the statetableIndex array. Since only some input characters
	 * are present in the state table, we only do the computation for
	 * characters 0 to the highest character value in the state table.
	 * An input character of -2 is used to cover all other characters
	 * as -2 is guaranteed not to match any input character entry
	 * in the state table.
	 *
	 * <p>When doing lookups, the input character should first be tested
	 * to be in the range [-1 (inclusive), statetableIndexMaxChar (exclusive)].
	 * if it isn't use -2 as the input character.
	 * 
	 * <p>Finally, add 2 to the input character to cover for the fact that
	 * Java doesn't support negative array indexes. Then look up
	 * the value in the statetableIndex. If the value is -1, then
	 * no action or next state was found for the { state, input } that
	 * you had. If it isn't -1, then action = statetable[value + 2] and
	 * next state = statetable[value + 3]. That is, the value points
	 * to the start of the answer 4-tuple in the statetable.
	 */
	static short[][] statetableIndex;
	/**
	 * The highest character value seen in the statetable.
	 * See the doc comment for statetableIndex to see how this
	 * is used.
	 */
	static int statetableIndexMaxChar;
	static {
		int maxState = -1;
		int maxChar = -1;
		for (int i = 0; i < statetable.length; i += 4) {
			if (statetable[i] > maxState) {
				maxState = statetable[i];
				}
			if (statetable[i + 1] > maxChar) {
				maxChar = statetable[i + 1];
				}
			}
		statetableIndexMaxChar = maxChar + 1;
		/* Objective-C array creation
    NSUInteger slen = maxChar + 3;
    NSUInteger len  = maxState+1;
    OrgCcilCowanTagsoupHTMLScanner_statetableIndex_ =[IOSObjectArray arrayWithLength:len type:[[IOSClass alloc] initWithClass:[IOSShortArray class]]];
    for(NSUInteger i=0;i<len;i++) {
      [OrgCcilCowanTagsoupHTMLScanner_statetableIndex_ replaceObjectAtIndex:i withObject: [[IOSShortArray alloc] initWithLength:slen]];
    }
		*/
		statetableIndex = new short[maxState + 1][maxChar + 3];
		for (int theState = 0; theState <= maxState; ++theState) {
			for (int ch = -2; ch <= maxChar; ++ch) {
				int hit = -1;
				int action = 0;
				for (int i = 0; i < statetable.length; i += 4) {
					if (theState != statetable[i]) {
						if (action != 0) break;
						continue;
						}
					if (statetable[i+1] == 0) {
						hit = i;
						action = statetable[i+2];
						}
					else if (statetable[i+1] == ch) {
						hit = i;
						action = statetable[i+2];
						break;
						}
					}
				statetableIndex[theState][ch + 2] = (short) hit;
				}
			}
		}

	// Compensate for bug in PushbackReader that allows
	// pushing back EOF.
	private void unread(PushbackReader r, int c) throws IOException {
		if (c != -1) r.unread(c);
		}

	// Locator implementation

	@Override
  public int getLineNumber() {
		return theLastLine;
		}
	@Override
  public int getColumnNumber() {
		return theLastColumn;
		}
	@Override
  public String getPublicId() {
		return thePublicid;
		}
	@Override
  public String getSystemId() {
		return theSystemid;
		}


	// Scanner implementation

	/**
	Reset document locator, supplying systemid and publicid.
	@param systemid System id
	@param publicid Public id
	*/

	@Override
  public void resetDocumentLocator(String publicid, String systemid) {
		thePublicid = publicid;
		theSystemid = systemid;
		theLastLine = theLastColumn = theCurrentLine = theCurrentColumn = 0;
		}

	/**
	Scan HTML source, reporting lexical events.
	@param r0 Reader that provides characters
	@param h ScanHandler that accepts lexical events.
	*/

	@Override
  public void scan(Reader r0, ScanHandler h) throws IOException, SAXException {
		theState = S_PCDATA;
		PushbackReader r;
		if (r0 instanceof BufferedReader) {
			r = new PushbackReader(r0, 5);
			}
		else {
			r = new PushbackReader(new BufferedReader(r0), 5);
			}

		int firstChar = r.read();	// Remove any leading BOM
		if (firstChar != '\uFEFF') unread(r, firstChar);

		while (theState != S_DONE) {
			int ch = r.read();

			// Process control characters
			if (ch >= 0x80 && ch <= 0x9F) ch = theWinMap[ch-0x80];

			if (ch == '\r') {
				ch = r.read();		// expect LF next
				if (ch != '\n') {
					unread(r, ch);	// nope
					ch = '\n';
					}
				}

			if (ch == '\n') {
				theCurrentLine++;
				theCurrentColumn = 0;
				}
			else {
				theCurrentColumn++;
				}

			if (!(ch >= 0x20 || ch == '\n' || ch == '\t' || ch == -1)) continue;

			// Search state table
			int adjCh = (ch >= -1 && ch < statetableIndexMaxChar) ? ch : -2;
			int statetableRow = statetableIndex[theState][adjCh + 2];
			int action = 0;
			if (statetableRow != -1) {
				action = statetable[statetableRow + 2];
				theNextState = statetable[statetableRow + 3];
				}

//			System.err.println("In " + debug_statenames[theState] + " got " + nicechar(ch) + " doing " + debug_actionnames[action] + " then " + debug_statenames[theNextState]);
			switch (action) {
			case 0:
				throw new Error(
					"HTMLScanner can't cope with " + Integer.toString(ch) + " in state " +
					Integer.toString(theState));
			case A_ADUP:
				h.adup(theOutputBuffer, 0, theSize);
				theSize = 0;
				break;
			case A_ADUP_SAVE:
				h.adup(theOutputBuffer, 0, theSize);
				theSize = 0;
				save(ch, h);
				break;
			case A_ADUP_STAGC:
				h.adup(theOutputBuffer, 0, theSize);
				theSize = 0;
				h.stagc(theOutputBuffer, 0, theSize);
				break;
			case A_ANAME:
				h.aname(theOutputBuffer, 0, theSize);
				theSize = 0;
				break;
			case A_ANAME_ADUP:
				h.aname(theOutputBuffer, 0, theSize);
				theSize = 0;
				h.adup(theOutputBuffer, 0, theSize);
				break;
			case A_ANAME_ADUP_STAGC:
				h.aname(theOutputBuffer, 0, theSize);
				theSize = 0;
				h.adup(theOutputBuffer, 0, theSize);
				h.stagc(theOutputBuffer, 0, theSize);
				break;
			case A_AVAL:
				h.aval(theOutputBuffer, 0, theSize);
				theSize = 0;
				break;
			case A_AVAL_STAGC:
				h.aval(theOutputBuffer, 0, theSize);
				theSize = 0;
				h.stagc(theOutputBuffer, 0, theSize);
				break;
			case A_CDATA:
				mark();
				// suppress the final "]]" in the buffer
				if (theSize > 1) theSize -= 2;
				h.pcdata(theOutputBuffer, 0, theSize);
				theSize = 0;
				break;
			case A_ENTITY_START:
				h.pcdata(theOutputBuffer, 0, theSize);
				theSize = 0;
				save(ch, h);
				break;
			case A_ENTITY:
				mark();
				char ch1 = (char)ch;
//				System.out.println("Got " + ch1 + " in state " + ((theState == S_ENT) ? "S_ENT" : ((theState == S_NCR) ? "S_NCR" : "UNK")));
				if (theState == S_ENT && ch1 == '#') {
					theNextState = S_NCR;
					save(ch, h);
					break;
					}
				else if (theState == S_NCR && (ch1 == 'x' || ch1 == 'X')) {
					theNextState = S_XNCR;
					save(ch, h);
					break;
					}
				else if (theState == S_ENT && Character.isLetterOrDigit(ch1)) {
					save(ch, h);
					break;
					}
				else if (theState == S_NCR && Character.isDigit(ch1)) {
					save(ch, h);
					break;
					}
				else if (theState == S_XNCR && (Character.isDigit(ch1) || "abcdefABCDEF".indexOf(ch1) != -1)) {
					save(ch, h);
					break;
					}

				// The whole entity reference has been collected
//				System.err.println("%%" + new String(theOutputBuffer, 0, theSize));
				h.entity(theOutputBuffer, 1, theSize - 1);
				int ent = h.getEntity();
//				System.err.println("%% value = " + ent);
				if (ent != 0) {
					theSize = 0;
					if (ent >= 0x80 && ent <= 0x9F) {
						ent = theWinMap[ent-0x80];
						}
					if (ent < 0x20) {
						// Control becomes space
						ent = 0x20;
						}
					else if (ent >= 0xD800 && ent <= 0xDFFF) {
						// Surrogates get dropped
						ent = 0;
						}
					else if (ent <= 0xFFFF) {
						// BMP character
						save(ent, h);
						}
					else {
						// Astral converted to two surrogates
						ent -= 0x10000;
						save((ent>>10) + 0xD800, h);
						save((ent&0x3FF) + 0xDC00, h);
						}
					if (ch != ';') {
						unread(r, ch);
						theCurrentColumn--;
						}
					}
				else {
					unread(r, ch);
					theCurrentColumn--;
					}
				theNextState = S_PCDATA;
				break;
			case A_ETAG:
				h.etag(theOutputBuffer, 0, theSize);
				theSize = 0;
				break;
			case A_DECL:
				h.decl(theOutputBuffer, 0, theSize);
				theSize = 0;
				break;
			case A_GI:
				h.gi(theOutputBuffer, 0, theSize);
				theSize = 0;
				break;
			case A_GI_STAGC:
				h.gi(theOutputBuffer, 0, theSize);
				theSize = 0;
				h.stagc(theOutputBuffer, 0, theSize);
				break;
			case A_LT:
				mark();
				save('<', h);
				save(ch, h);
				break;
			case A_LT_PCDATA:
				mark();
				save('<', h);
				h.pcdata(theOutputBuffer, 0, theSize);
				theSize = 0;
				break;
			case A_PCDATA:
				mark();
				h.pcdata(theOutputBuffer, 0, theSize);
				theSize = 0;
				break;
			case A_CMNT:
				mark();
				h.cmnt(theOutputBuffer, 0, theSize);
				theSize = 0;
				break;
			case A_MINUS3:
				save('-', h);
				save(' ', h);
				break;
			case A_MINUS2:
				save('-', h);
				save(' ', h);
				// fall through into A_MINUS
			case A_MINUS:
				save('-', h);
				save(ch, h);
				break;
			case A_PI:
				mark();
				h.pi(theOutputBuffer, 0, theSize);
				theSize = 0;
				break;
			case A_PITARGET:
				h.pitarget(theOutputBuffer, 0, theSize);
				theSize = 0;
				break;
			case A_PITARGET_PI:
				h.pitarget(theOutputBuffer, 0, theSize);
				theSize = 0;
				h.pi(theOutputBuffer, 0, theSize);
				break;
			case A_SAVE:
				save(ch, h);
				break;
			case A_SKIP:
				break;
			case A_SP:
				save(' ', h);
				break;
			case A_STAGC:
				h.stagc(theOutputBuffer, 0, theSize);
				theSize = 0;
				break;
			case A_EMPTYTAG:
				mark();
//				System.err.println("%%% Empty tag seen");
				if (theSize > 0) h.gi(theOutputBuffer, 0, theSize);
				theSize = 0;
				h.stage(theOutputBuffer, 0, theSize);
				break;
			case A_UNGET:
				unread(r, ch);
				theCurrentColumn--;
				break;
			case A_UNSAVE_PCDATA:
				if (theSize > 0) theSize--;
				h.pcdata(theOutputBuffer, 0, theSize);
				theSize = 0;
				break;
			default:
				throw new Error("Can't process state " + action);
				}
			theState = theNextState;
			}
		h.eof(theOutputBuffer, 0, 0);
		}

	/**
	* Mark the current scan position as a "point of interest" - start of a tag,
	* cdata, processing instruction etc.
	*/

	private void mark() {
		theLastColumn = theCurrentColumn;
		theLastLine = theCurrentLine;
		}

	/**
	A callback for the ScanHandler that allows it to force
	the lexer state to CDATA content (no markup is recognized except
	the end of element.
	*/

	@Override
  public void startCDATA() { theNextState = S_CDATA; }

	private void save(int ch, ScanHandler h) throws IOException, SAXException {
		if (theSize >= theOutputBuffer.length - 20) {
			if (theState == S_PCDATA || theState == S_CDATA) {
				// Return a buffer-sized chunk of PCDATA
				h.pcdata(theOutputBuffer, 0, theSize);
				theSize = 0;
				}
			else {
				// Grow the buffer size
				char[] newOutputBuffer = new char[theOutputBuffer.length * 2];
				System.arraycopy(theOutputBuffer, 0, newOutputBuffer, 0, theSize+1);
				theOutputBuffer = newOutputBuffer;
				}
			}
		theOutputBuffer[theSize++] = (char)ch;
		}

	/**
	Test procedure.  Reads HTML from the standard input and writes
	PYX to the standard output.
	*/

	
	private static String nicechar(int in) {
		if (in == '\n') return "\\n";
		if (in < 32) return "0x"+Integer.toHexString(in);
		return "'"+((char)in)+"'";
		}

	}
