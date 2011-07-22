package org.clothocad.tool.eugenescripter;

import jsyntaxpane.Token;
import jsyntaxpane.TokenType;
import jsyntaxpane.lexers.*;

public final class EugeneLexer extends DefaultJFlexLexer {

  /** This character denotes the end of file */
  public static final int YYEOF = -1;

  /** initial size of the lookahead buffer */
  private static final int ZZ_BUFFERSIZE = 16384;

  /** lexical states */
  public static final int STRING = 2;
  public static final int JDOC_TAG = 8;
  public static final int JDOC = 6;
  public static final int YYINITIAL = 0;
  public static final int CHARLITERAL = 4;

  /**
   * ZZ_LEXSTATE[l] is the state in the DFA for the lexical state l
   * ZZ_LEXSTATE[l+1] is the state in the DFA for the lexical state l
   *                  at the beginning of a line
   * l is of the form l = 2*k, k a non negative integer
   */
  private static final int ZZ_LEXSTATE[] = {
     0,  0,  1,  1,  2,  2,  3,  3,  4, 4
  };

  /**
   * Translates characters to character classes
   */
  private static final String ZZ_CMAP_PACKED =
    "\11\7\1\3\1\2\1\0\1\3\1\1\16\7\4\0\1\3\1\76"+
    "\1\24\1\0\1\6\1\76\1\100\1\25\1\64\1\65\1\5\1\102"+
    "\1\72\1\23\1\21\1\4\1\10\1\16\2\15\4\17\2\11\1\77"+
    "\1\72\1\75\1\73\1\74\1\72\1\107\1\56\1\14\1\54\1\27"+
    "\1\22\1\50\1\110\1\105\1\104\2\110\1\12\1\106\1\60\1\43"+
    "\1\51\1\110\1\47\1\44\1\34\2\110\1\103\1\13\2\110\1\70"+
    "\1\26\1\71\1\76\1\6\1\0\1\40\1\63\1\33\1\61\1\30"+
    "\1\20\2\110\1\32\2\110\1\55\1\36\1\37\1\42\1\52\1\45"+
    "\1\35\1\57\1\41\1\46\1\31\1\110\1\62\1\53\1\110\1\66"+
    "\1\101\1\67\1\72\41\7\2\0\4\6\4\0\1\110\2\0\1\7"+
    "\7\0\1\110\4\0\1\110\5\0\27\110\1\0\37\110\1\0\u013f\110"+
    "\31\0\162\110\4\0\14\110\16\0\5\110\11\0\1\110\21\0\130\7"+
    "\5\0\23\7\12\0\1\110\13\0\1\110\1\0\3\110\1\0\1\110"+
    "\1\0\24\110\1\0\54\110\1\0\46\110\1\0\5\110\4\0\202\110"+
    "\1\0\4\7\3\0\105\110\1\0\46\110\2\0\2\110\6\0\20\110"+
    "\41\0\46\110\2\0\1\110\7\0\47\110\11\0\21\7\1\0\27\7"+
    "\1\0\3\7\1\0\1\7\1\0\2\7\1\0\1\7\13\0\33\110"+
    "\5\0\3\110\15\0\4\7\14\0\6\7\13\0\32\110\5\0\13\110"+
    "\16\7\7\0\12\7\4\0\2\110\1\7\143\110\1\0\1\110\10\7"+
    "\1\0\6\7\2\110\2\7\1\0\4\7\2\110\12\7\3\110\2\0"+
    "\1\110\17\0\1\7\1\110\1\7\36\110\33\7\2\0\3\110\60\0"+
    "\46\110\13\7\1\110\u014f\0\3\7\66\110\2\0\1\7\1\110\20\7"+
    "\2\0\1\110\4\7\3\0\12\110\2\7\2\0\12\7\21\0\3\7"+
    "\1\0\10\110\2\0\2\110\2\0\26\110\1\0\7\110\1\0\1\110"+
    "\3\0\4\110\2\0\1\7\1\110\7\7\2\0\2\7\2\0\3\7"+
    "\11\0\1\7\4\0\2\110\1\0\3\110\2\7\2\0\12\7\2\110"+
    "\2\6\15\0\3\7\1\0\6\110\4\0\2\110\2\0\26\110\1\0"+
    "\7\110\1\0\2\110\1\0\2\110\1\0\2\110\2\0\1\7\1\0"+
    "\5\7\4\0\2\7\2\0\3\7\13\0\4\110\1\0\1\110\7\0"+
    "\14\7\3\110\14\0\3\7\1\0\11\110\1\0\3\110\1\0\26\110"+
    "\1\0\7\110\1\0\2\110\1\0\5\110\2\0\1\7\1\110\10\7"+
    "\1\0\3\7\1\0\3\7\2\0\1\110\17\0\2\110\2\7\2\0"+
    "\12\7\1\0\1\6\17\0\3\7\1\0\10\110\2\0\2\110\2\0"+
    "\26\110\1\0\7\110\1\0\2\110\1\0\5\110\2\0\1\7\1\110"+
    "\6\7\3\0\2\7\2\0\3\7\10\0\2\7\4\0\2\110\1\0"+
    "\3\110\4\0\12\7\1\0\1\110\20\0\1\7\1\110\1\0\6\110"+
    "\3\0\3\110\1\0\4\110\3\0\2\110\1\0\1\110\1\0\2\110"+
    "\3\0\2\110\3\0\3\110\3\0\10\110\1\0\3\110\4\0\5\7"+
    "\3\0\3\7\1\0\4\7\11\0\1\7\17\0\11\7\11\0\1\6"+
    "\7\0\3\7\1\0\10\110\1\0\3\110\1\0\27\110\1\0\12\110"+
    "\1\0\5\110\4\0\7\7\1\0\3\7\1\0\4\7\7\0\2\7"+
    "\11\0\2\110\4\0\12\7\22\0\2\7\1\0\10\110\1\0\3\110"+
    "\1\0\27\110\1\0\12\110\1\0\5\110\2\0\1\7\1\110\7\7"+
    "\1\0\3\7\1\0\4\7\7\0\2\7\7\0\1\110\1\0\2\110"+
    "\4\0\12\7\22\0\2\7\1\0\10\110\1\0\3\110\1\0\27\110"+
    "\1\0\20\110\4\0\6\7\2\0\3\7\1\0\4\7\11\0\1\7"+
    "\10\0\2\110\4\0\12\7\22\0\2\7\1\0\22\110\3\0\30\110"+
    "\1\0\11\110\1\0\1\110\2\0\7\110\3\0\1\7\4\0\6\7"+
    "\1\0\1\7\1\0\10\7\22\0\2\7\15\0\60\110\1\7\2\110"+
    "\7\7\4\0\1\6\7\110\10\7\1\0\12\7\47\0\2\110\1\0"+
    "\1\110\2\0\2\110\1\0\1\110\2\0\1\110\6\0\4\110\1\0"+
    "\7\110\1\0\3\110\1\0\1\110\1\0\1\110\2\0\2\110\1\0"+
    "\4\110\1\7\2\110\6\7\1\0\2\7\1\110\2\0\5\110\1\0"+
    "\1\110\1\0\6\7\2\0\12\7\2\0\2\110\42\0\1\110\27\0"+
    "\2\7\6\0\12\7\13\0\1\7\1\0\1\7\1\0\1\7\4\0"+
    "\2\7\10\110\1\0\42\110\6\0\24\7\1\0\2\7\4\110\4\0"+
    "\10\7\1\0\44\7\11\0\1\7\71\0\42\110\1\0\5\110\1\0"+
    "\2\110\1\0\7\7\3\0\4\7\6\0\12\7\6\0\6\110\4\7"+
    "\106\0\46\110\12\0\51\110\7\0\132\110\5\0\104\110\5\0\122\110"+
    "\6\0\7\110\1\0\77\110\1\0\1\110\1\0\4\110\2\0\7\110"+
    "\1\0\1\110\1\0\4\110\2\0\47\110\1\0\1\110\1\0\4\110"+
    "\2\0\37\110\1\0\1\110\1\0\4\110\2\0\7\110\1\0\1\110"+
    "\1\0\4\110\2\0\7\110\1\0\7\110\1\0\27\110\1\0\37\110"+
    "\1\0\1\110\1\0\4\110\2\0\7\110\1\0\47\110\1\0\23\110"+
    "\16\0\11\7\56\0\125\110\14\0\u026c\110\2\0\10\110\12\0\32\110"+
    "\5\0\113\110\3\0\3\6\17\0\15\110\1\0\4\110\3\7\13\0"+
    "\22\110\3\7\13\0\22\110\2\7\14\0\15\110\1\0\3\110\1\0"+
    "\2\7\14\0\64\110\40\7\3\0\1\110\3\0\1\6\1\110\1\7"+
    "\2\0\12\7\41\0\3\7\2\0\12\7\6\0\130\110\10\0\51\110"+
    "\1\7\126\0\35\110\3\0\14\7\4\0\14\7\12\0\12\7\36\110"+
    "\2\0\5\110\u038b\0\154\110\224\0\234\110\4\0\132\110\6\0\26\110"+
    "\2\0\6\110\2\0\46\110\2\0\6\110\2\0\10\110\1\0\1\110"+
    "\1\0\1\110\1\0\1\110\1\0\37\110\2\0\65\110\1\0\7\110"+
    "\1\0\1\110\3\0\3\110\1\0\7\110\3\0\4\110\2\0\6\110"+
    "\4\0\15\110\5\0\3\110\1\0\7\110\17\0\4\7\32\0\5\7"+
    "\20\0\2\6\23\0\1\6\13\0\4\7\6\0\6\7\1\0\1\110"+
    "\15\0\1\110\40\0\22\6\36\0\15\7\4\0\1\7\3\0\6\7"+
    "\27\0\1\110\4\0\1\110\2\0\12\110\1\0\1\110\3\0\5\110"+
    "\6\0\1\110\1\0\1\110\1\0\1\110\1\0\4\110\1\0\3\110"+
    "\1\0\7\110\3\0\3\110\5\0\5\110\26\0\44\6\u0e81\0\2\110"+
    "\1\6\31\0\11\6\6\7\1\0\5\110\2\0\3\6\2\110\4\0"+
    "\126\110\2\0\2\7\2\0\3\110\1\0\132\110\1\6\4\110\5\0"+
    "\50\110\4\0\136\110\21\0\30\110\70\0\20\110\u0200\0\u19b6\110\112\0"+
    "\u51a6\110\132\0\u048d\110\u0773\0\u2ba4\110\u215c\0\u012e\110\2\0\73\110\225\0"+
    "\7\110\14\0\5\110\5\0\1\110\1\7\12\110\1\0\15\110\1\0"+
    "\5\110\1\0\1\110\1\0\2\110\1\0\2\110\1\0\154\110\41\0"+
    "\u016b\110\22\0\100\110\2\0\66\110\50\0\14\110\1\6\3\0\20\7"+
    "\20\0\4\7\17\0\2\6\30\0\3\6\31\0\1\6\6\0\5\110"+
    "\1\0\207\110\2\0\1\7\4\0\1\6\13\0\12\7\7\0\32\110"+
    "\4\0\1\6\1\0\32\110\12\0\1\6\131\110\3\0\6\110\2\0"+
    "\6\110\2\0\6\110\2\0\3\110\3\0\2\6\3\0\2\6\22\0"+
    "\3\7\4\0";

  /**
   * Translates characters to character classes
   */
  private static final char [] ZZ_CMAP = zzUnpackCMap(ZZ_CMAP_PACKED);

  /**
   * Translates DFA states to action switch labels.
   */
  private static final int [] ZZ_ACTION = zzUnpackAction();

  private static final String ZZ_ACTION_PACKED_0 =
    "\5\0\3\1\2\2\1\3\2\4\2\3\2\2\1\5"+
    "\1\6\16\3\1\7\1\10\1\11\1\12\1\13\1\14"+
    "\6\2\1\3\1\15\2\16\1\17\1\1\1\15\1\1"+
    "\2\20\1\21\2\22\1\15\1\23\1\0\3\4\1\0"+
    "\3\4\1\0\11\3\1\2\15\3\1\2\1\3\1\24"+
    "\2\15\1\25\1\15\2\23\1\0\1\26\4\4\1\0"+
    "\5\3\1\27\3\3\1\30\7\3\2\2\2\3\2\0"+
    "\2\4\15\3\2\4\12\3\2\4\7\3\2\4\6\3"+
    "\2\4\3\3\2\4\2\3\2\4\2\3\1\0\1\4"+
    "\1\0\1\4\1\0\1\4\1\0\1\4\1\0\1\4"+
    "\1\0\1\4\1\0\1\4\1\0\5\4";

  private static int [] zzUnpackAction() {
    int [] result = new int[208];
    int offset = 0;
    offset = zzUnpackAction(ZZ_ACTION_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAction(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /**
   * Translates a state to a row index in the transition table
   */
  private static final int [] ZZ_ROWMAP = zzUnpackRowMap();

  private static final String ZZ_ROWMAP_PACKED_0 =
    "\0\0\0\111\0\222\0\333\0\u0124\0\u016d\0\u01b6\0\u01ff"+
    "\0\u0248\0\u0291\0\u02da\0\u0323\0\u036c\0\u03b5\0\u03fe\0\u0447"+
    "\0\u0490\0\u016d\0\u016d\0\u04d9\0\u0522\0\u056b\0\u05b4\0\u05fd"+
    "\0\u0646\0\u068f\0\u06d8\0\u0721\0\u076a\0\u07b3\0\u07fc\0\u0845"+
    "\0\u088e\0\u016d\0\u016d\0\u016d\0\u016d\0\u016d\0\u016d\0\u016d"+
    "\0\u08d7\0\u0920\0\u0969\0\u09b2\0\u09fb\0\u0a44\0\u0a8d\0\u0ad6"+
    "\0\u016d\0\u016d\0\u0b1f\0\u0b68\0\u0bb1\0\u016d\0\u0bfa\0\u016d"+
    "\0\u016d\0\u0bfa\0\u0c43\0\u0c8c\0\u0cd5\0\u0d1e\0\u0d67\0\u016d"+
    "\0\u0db0\0\u0df9\0\u0e42\0\u0e8b\0\u0ed4\0\u0f1d\0\u0f66\0\u0faf"+
    "\0\u0ff8\0\u1041\0\u108a\0\u10d3\0\u111c\0\u1165\0\u11ae\0\u11f7"+
    "\0\u1240\0\u1289\0\u12d2\0\u131b\0\u1364\0\u13ad\0\u13f6\0\u143f"+
    "\0\u1488\0\u14d1\0\u151a\0\u1563\0\u15ac\0\u15f5\0\u016d\0\u163e"+
    "\0\u1687\0\u016d\0\u016d\0\u16d0\0\u016d\0\u1719\0\u1762\0\u17ab"+
    "\0\u17f4\0\u183d\0\u1886\0\u18cf\0\u1918\0\u1961\0\u19aa\0\u19f3"+
    "\0\u1a3c\0\u02da\0\u1a85\0\u1ace\0\u1b17\0\u02da\0\u1b60\0\u1ba9"+
    "\0\u1bf2\0\u1c3b\0\u1c84\0\u1ccd\0\u1d16\0\u02da\0\u1d5f\0\u1da8"+
    "\0\u1df1\0\u1e3a\0\u1762\0\u1e83\0\u1ecc\0\u1f15\0\u1f5e\0\u1fa7"+
    "\0\u1ff0\0\u2039\0\u2082\0\u20cb\0\u2114\0\u215d\0\u21a6\0\u21ef"+
    "\0\u2238\0\u2281\0\u22ca\0\u2313\0\u235c\0\u23a5\0\u23ee\0\u2437"+
    "\0\u2480\0\u24c9\0\u2512\0\u255b\0\u25a4\0\u25ed\0\u2636\0\u267f"+
    "\0\u26c8\0\u2711\0\u275a\0\u27a3\0\u27ec\0\u2835\0\u287e\0\u28c7"+
    "\0\u2910\0\u2959\0\u29a2\0\u29eb\0\u2a34\0\u2a7d\0\u2ac6\0\u2b0f"+
    "\0\u2b58\0\u2ba1\0\u2bea\0\u2c33\0\u2c7c\0\u2cc5\0\u2d0e\0\u2d57"+
    "\0\u2da0\0\u2de9\0\u2e32\0\u2e7b\0\u2ec4\0\u2f0d\0\u2f56\0\u2f9f"+
    "\0\u2fe8\0\u3031\0\u307a\0\u30c3\0\u310c\0\u3155\0\u319e\0\u31e7"+
    "\0\u3230\0\u3279\0\u32c2\0\u330b\0\u3354\0\u339d\0\u33e6\0\u342f";

  private static int [] zzUnpackRowMap() {
    int [] result = new int[208];
    int offset = 0;
    offset = zzUnpackRowMap(ZZ_ROWMAP_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackRowMap(String packed, int offset, int [] result) {
    int i = 0;  /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int high = packed.charAt(i++) << 16;
      result[j++] = high | packed.charAt(i++);
    }
    return j;
  }

  /**
   * The transition table of the DFA
   */
  private static final int [] ZZ_TRANS = zzUnpackTrans();

  private static final String ZZ_TRANS_PACKED_0 =
    "\1\6\1\7\1\6\1\10\1\11\1\12\1\13\1\6"+
    "\1\14\1\15\2\13\1\16\3\15\1\17\1\20\1\13"+
    "\1\21\1\22\1\23\1\6\1\24\2\13\1\25\1\13"+
    "\1\26\2\13\1\27\1\13\1\30\1\13\1\31\1\32"+
    "\2\13\1\33\1\13\1\34\1\35\1\13\1\36\1\13"+
    "\1\37\1\13\1\40\2\13\1\41\1\42\1\43\1\44"+
    "\1\45\1\46\1\47\1\50\1\12\1\51\1\52\1\12"+
    "\1\50\1\53\1\54\1\55\1\56\3\13\1\6\1\13"+
    "\1\57\1\60\1\61\21\57\1\62\1\57\1\63\62\57"+
    "\1\64\1\60\1\61\22\64\1\62\1\65\62\64\5\66"+
    "\1\67\101\66\1\70\1\66\5\71\1\72\4\71\3\73"+
    "\3\71\1\73\1\71\1\73\4\71\35\73\17\71\4\73"+
    "\1\71\1\73\113\0\1\6\111\0\1\10\111\0\1\74"+
    "\1\75\65\0\1\50\110\0\1\50\23\0\13\13\1\0"+
    "\1\13\4\0\35\13\17\0\4\13\1\0\1\13\10\0"+
    "\1\76\1\77\1\100\1\101\1\0\1\102\1\103\1\102"+
    "\1\100\1\104\1\105\4\0\1\100\1\105\17\0\1\100"+
    "\4\0\1\100\3\0\1\100\1\101\36\0\2\15\1\100"+
    "\2\0\3\15\1\100\1\104\1\105\4\0\1\100\1\105"+
    "\17\0\1\100\4\0\1\100\3\0\1\100\35\0\13\13"+
    "\1\0\1\106\4\0\35\13\17\0\4\13\1\0\1\13"+
    "\6\0\13\13\1\0\1\13\4\0\11\13\1\107\23\13"+
    "\17\0\4\13\1\0\1\13\10\0\2\104\3\0\3\104"+
    "\114\0\1\50\47\0\1\50\23\0\13\13\1\0\1\13"+
    "\4\0\1\13\1\110\33\13\17\0\4\13\1\0\1\13"+
    "\6\0\13\13\1\0\1\13\4\0\10\13\1\111\24\13"+
    "\17\0\4\13\1\0\1\13\6\0\13\13\1\0\1\13"+
    "\4\0\1\13\1\112\33\13\17\0\4\13\1\0\1\13"+
    "\6\0\13\13\1\0\1\13\4\0\17\13\1\113\15\13"+
    "\17\0\4\13\1\0\1\13\6\0\13\13\1\0\1\13"+
    "\4\0\6\13\1\114\24\13\1\115\1\13\17\0\4\13"+
    "\1\0\1\13\6\0\13\13\1\0\1\13\4\0\6\13"+
    "\1\116\11\13\1\117\14\13\17\0\4\13\1\0\1\13"+
    "\6\0\13\13\1\0\1\13\4\0\1\13\1\120\33\13"+
    "\17\0\4\13\1\0\1\13\6\0\13\13\1\0\1\13"+
    "\4\0\17\13\1\121\15\13\17\0\4\13\1\0\1\13"+
    "\6\0\13\13\1\0\1\13\4\0\6\13\1\122\2\13"+
    "\1\123\23\13\17\0\4\13\1\0\1\13\6\0\13\13"+
    "\1\0\1\13\4\0\1\13\1\124\4\13\1\125\26\13"+
    "\17\0\4\13\1\0\1\13\6\0\13\13\1\0\1\13"+
    "\4\0\1\126\13\13\1\127\20\13\17\0\4\13\1\0"+
    "\1\13\6\0\13\13\1\0\1\13\4\0\30\13\1\130"+
    "\1\131\3\13\17\0\4\13\1\0\1\13\6\0\13\13"+
    "\1\0\1\13\4\0\13\13\1\132\1\133\20\13\17\0"+
    "\4\13\1\0\1\13\6\0\13\13\1\0\1\13\4\0"+
    "\13\13\1\134\21\13\17\0\4\13\1\0\1\13\73\0"+
    "\1\50\1\135\107\0\1\50\1\0\1\12\106\0\1\50"+
    "\4\0\1\50\103\0\1\50\5\0\1\50\102\0\1\50"+
    "\6\0\1\50\14\0\13\13\1\0\1\13\4\0\35\13"+
    "\17\0\1\13\1\136\2\13\1\0\1\13\1\57\2\0"+
    "\21\57\1\0\1\57\1\0\62\57\2\0\1\61\106\0"+
    "\2\137\1\0\5\137\1\140\4\137\2\140\1\141\71\137"+
    "\1\64\2\0\22\64\2\0\62\64\2\137\1\0\106\137"+
    "\4\0\1\142\116\0\3\73\3\0\1\73\1\0\1\73"+
    "\4\0\35\73\13\0\1\143\3\0\4\73\1\0\1\73"+
    "\1\74\1\144\1\145\106\74\5\146\1\147\103\146\10\0"+
    "\1\76\1\77\1\100\2\0\1\102\1\103\1\102\1\100"+
    "\1\104\1\105\4\0\1\100\1\105\17\0\1\100\4\0"+
    "\1\100\3\0\1\100\37\0\2\77\3\0\3\77\1\100"+
    "\1\104\1\105\4\0\1\100\1\105\17\0\1\100\10\0"+
    "\1\100\37\0\1\150\1\151\2\0\5\151\1\0\1\151"+
    "\4\0\2\151\2\0\1\151\4\0\1\151\7\0\1\151"+
    "\3\0\1\151\1\0\1\151\2\0\1\151\1\0\1\151"+
    "\35\0\1\152\1\77\1\100\2\0\3\152\1\100\1\104"+
    "\1\105\4\0\1\100\1\105\17\0\1\100\4\0\1\100"+
    "\3\0\1\100\37\0\1\102\1\77\1\100\2\0\3\102"+
    "\1\100\1\104\1\105\4\0\1\100\1\105\17\0\1\100"+
    "\4\0\1\100\3\0\1\100\37\0\2\104\3\0\3\104"+
    "\1\100\1\0\1\105\4\0\1\100\1\105\17\0\1\100"+
    "\10\0\1\100\37\0\2\153\3\0\3\153\3\0\1\154"+
    "\56\0\1\154\14\0\13\13\1\0\1\13\4\0\21\13"+
    "\1\155\13\13\17\0\4\13\1\0\1\13\6\0\13\13"+
    "\1\0\1\13\4\0\26\13\1\156\6\13\17\0\4\13"+
    "\1\0\1\13\6\0\13\13\1\0\1\13\4\0\2\13"+
    "\1\157\32\13\17\0\4\13\1\0\1\13\6\0\13\13"+
    "\1\0\1\13\4\0\4\13\1\160\30\13\17\0\4\13"+
    "\1\0\1\13\6\0\13\13\1\0\1\13\4\0\6\13"+
    "\1\161\26\13\17\0\4\13\1\0\1\13\6\0\13\13"+
    "\1\0\1\13\4\0\7\13\1\162\16\13\1\163\6\13"+
    "\17\0\4\13\1\0\1\13\6\0\13\13\1\0\1\13"+
    "\4\0\17\13\1\164\15\13\17\0\4\13\1\0\1\13"+
    "\6\0\13\13\1\0\1\13\4\0\12\13\1\162\22\13"+
    "\17\0\4\13\1\0\1\13\6\0\13\13\1\0\1\13"+
    "\4\0\3\13\1\165\31\13\17\0\4\13\1\0\1\13"+
    "\6\0\13\13\1\0\1\13\4\0\21\13\1\166\13\13"+
    "\17\0\4\13\1\0\1\13\6\0\13\13\1\0\1\13"+
    "\4\0\16\13\1\167\16\13\17\0\4\13\1\0\1\13"+
    "\6\0\13\13\1\0\1\13\4\0\26\13\1\164\6\13"+
    "\17\0\4\13\1\0\1\13\6\0\13\13\1\0\1\13"+
    "\4\0\13\13\1\170\21\13\17\0\4\13\1\0\1\13"+
    "\6\0\13\13\1\0\1\13\4\0\6\13\1\171\26\13"+
    "\17\0\4\13\1\0\1\13\6\0\13\13\1\0\1\13"+
    "\4\0\6\13\1\172\26\13\17\0\4\13\1\0\1\13"+
    "\6\0\13\13\1\0\1\13\4\0\3\13\1\173\31\13"+
    "\17\0\4\13\1\0\1\13\6\0\13\13\1\0\1\13"+
    "\4\0\15\13\1\166\17\13\17\0\4\13\1\0\1\13"+
    "\6\0\13\13\1\0\1\13\4\0\31\13\1\174\3\13"+
    "\17\0\4\13\1\0\1\13\6\0\13\13\1\0\1\13"+
    "\4\0\30\13\1\175\4\13\17\0\4\13\1\0\1\13"+
    "\6\0\13\13\1\0\1\13\4\0\1\176\34\13\17\0"+
    "\4\13\1\0\1\13\6\0\13\13\1\0\1\13\4\0"+
    "\12\13\1\164\22\13\17\0\4\13\1\0\1\13\6\0"+
    "\13\13\1\0\1\13\4\0\5\13\1\177\27\13\17\0"+
    "\4\13\1\0\1\13\6\0\13\13\1\0\1\13\4\0"+
    "\13\13\1\200\21\13\17\0\4\13\1\0\1\13\73\0"+
    "\1\50\1\12\22\0\13\13\1\0\1\13\4\0\5\13"+
    "\1\201\27\13\17\0\4\13\1\0\1\13\10\0\1\141"+
    "\4\0\3\141\101\0\1\143\4\0\3\143\73\0\1\145"+
    "\106\0\5\146\1\202\103\146\4\0\1\145\1\203\113\0"+
    "\1\150\1\151\1\100\1\0\5\151\1\0\1\151\4\0"+
    "\2\151\2\0\1\151\4\0\1\151\7\0\1\151\3\0"+
    "\1\151\1\100\1\151\2\0\1\151\1\0\1\151\35\0"+
    "\2\204\1\100\1\0\5\204\1\0\1\204\4\0\2\204"+
    "\2\0\1\204\4\0\1\204\7\0\1\204\3\0\1\204"+
    "\1\100\1\204\2\0\1\204\1\0\1\204\35\0\1\205"+
    "\1\77\1\100\2\0\3\205\1\100\1\104\1\105\4\0"+
    "\1\100\1\105\17\0\1\100\4\0\1\100\3\0\1\100"+
    "\37\0\2\153\3\0\3\153\1\100\6\0\1\100\20\0"+
    "\1\100\10\0\1\100\37\0\2\153\3\0\3\153\77\0"+
    "\13\13\1\0\1\13\4\0\14\13\1\206\20\13\17\0"+
    "\4\13\1\0\1\13\6\0\13\13\1\0\1\13\4\0"+
    "\30\13\1\164\4\13\17\0\4\13\1\0\1\13\6\0"+
    "\13\13\1\0\1\13\4\0\3\13\1\207\31\13\17\0"+
    "\4\13\1\0\1\13\6\0\13\13\1\0\1\13\4\0"+
    "\26\13\1\210\6\13\17\0\4\13\1\0\1\13\6\0"+
    "\13\13\1\0\1\13\4\0\7\13\1\211\25\13\17\0"+
    "\4\13\1\0\1\13\6\0\13\13\1\0\1\13\4\0"+
    "\26\13\1\166\6\13\17\0\4\13\1\0\1\13\6\0"+
    "\13\13\1\0\1\13\4\0\1\13\1\166\33\13\17\0"+
    "\4\13\1\0\1\13\6\0\13\13\1\0\1\13\4\0"+
    "\1\13\1\212\33\13\17\0\4\13\1\0\1\13\6\0"+
    "\13\13\1\0\1\13\4\0\17\13\1\213\15\13\17\0"+
    "\4\13\1\0\1\13\6\0\13\13\1\0\1\13\4\0"+
    "\7\13\1\214\13\13\1\215\11\13\17\0\4\13\1\0"+
    "\1\13\6\0\13\13\1\0\1\13\4\0\12\13\1\166"+
    "\22\13\17\0\4\13\1\0\1\13\6\0\13\13\1\0"+
    "\1\13\4\0\7\13\1\216\25\13\17\0\4\13\1\0"+
    "\1\13\6\0\13\13\1\0\1\13\4\0\10\13\1\171"+
    "\24\13\17\0\4\13\1\0\1\13\6\0\13\13\1\0"+
    "\1\13\4\0\5\13\1\217\27\13\17\0\4\13\1\0"+
    "\1\13\6\0\13\13\1\0\1\13\4\0\1\13\1\123"+
    "\33\13\17\0\4\13\1\0\1\13\6\0\13\13\1\0"+
    "\1\13\4\0\25\13\1\220\7\13\17\0\1\56\2\13"+
    "\1\221\1\0\1\13\6\0\13\13\1\0\1\13\4\0"+
    "\26\13\1\222\6\13\17\0\4\13\1\0\1\13\6\0"+
    "\13\13\1\0\1\13\4\0\35\13\17\0\2\13\1\176"+
    "\1\13\1\0\1\13\4\146\1\145\1\202\103\146\10\0"+
    "\2\223\1\100\1\0\5\223\1\0\1\223\4\0\2\223"+
    "\2\0\1\223\4\0\1\223\7\0\1\223\3\0\1\223"+
    "\1\100\1\223\2\0\1\223\1\0\1\223\35\0\1\224"+
    "\1\77\1\100\2\0\3\224\1\100\1\104\1\105\4\0"+
    "\1\100\1\105\17\0\1\100\4\0\1\100\3\0\1\100"+
    "\35\0\13\13\1\0\1\13\4\0\20\13\1\225\14\13"+
    "\17\0\4\13\1\0\1\13\6\0\13\13\1\0\1\13"+
    "\4\0\4\13\1\164\30\13\17\0\4\13\1\0\1\13"+
    "\6\0\13\13\1\0\1\13\4\0\17\13\1\226\15\13"+
    "\17\0\4\13\1\0\1\13\6\0\13\13\1\0\1\13"+
    "\4\0\3\13\1\227\31\13\17\0\4\13\1\0\1\13"+
    "\6\0\13\13\1\0\1\13\4\0\10\13\1\230\24\13"+
    "\17\0\4\13\1\0\1\13\6\0\13\13\1\0\1\13"+
    "\4\0\1\13\1\231\33\13\17\0\4\13\1\0\1\13"+
    "\6\0\13\13\1\0\1\13\4\0\13\13\1\232\21\13"+
    "\17\0\4\13\1\0\1\13\6\0\13\13\1\0\1\13"+
    "\4\0\1\13\1\233\33\13\17\0\4\13\1\0\1\13"+
    "\6\0\13\13\1\0\1\13\4\0\17\13\1\132\15\13"+
    "\17\0\4\13\1\0\1\13\6\0\13\13\1\0\1\13"+
    "\4\0\27\13\1\234\5\13\17\0\4\13\1\0\1\13"+
    "\6\0\13\13\1\0\1\13\4\0\14\13\1\127\20\13"+
    "\17\0\4\13\1\0\1\13\6\0\13\13\1\0\1\13"+
    "\4\0\14\13\1\235\20\13\17\0\4\13\1\0\1\13"+
    "\6\0\13\13\1\0\1\13\4\0\1\13\1\236\33\13"+
    "\17\0\4\13\1\0\1\13\10\0\2\237\1\100\1\0"+
    "\5\237\1\0\1\237\4\0\2\237\2\0\1\237\4\0"+
    "\1\237\7\0\1\237\3\0\1\237\1\100\1\237\2\0"+
    "\1\237\1\0\1\237\35\0\1\240\1\77\1\100\2\0"+
    "\3\240\1\100\1\104\1\105\4\0\1\100\1\105\17\0"+
    "\1\100\4\0\1\100\3\0\1\100\35\0\13\13\1\0"+
    "\1\176\4\0\35\13\17\0\4\13\1\0\1\13\6\0"+
    "\13\13\1\0\1\13\4\0\32\13\1\164\2\13\17\0"+
    "\4\13\1\0\1\13\6\0\13\13\1\0\1\13\4\0"+
    "\10\13\1\241\24\13\17\0\4\13\1\0\1\13\6\0"+
    "\13\13\1\0\1\13\4\0\12\13\1\242\22\13\17\0"+
    "\4\13\1\0\1\13\6\0\13\13\1\0\1\13\4\0"+
    "\10\13\1\207\24\13\17\0\4\13\1\0\1\13\6\0"+
    "\13\13\1\0\1\13\4\0\12\13\1\243\22\13\17\0"+
    "\4\13\1\0\1\13\6\0\13\13\1\0\1\13\4\0"+
    "\6\13\1\244\26\13\17\0\4\13\1\0\1\13\6\0"+
    "\13\13\1\0\1\13\4\0\35\13\17\0\1\13\1\245"+
    "\2\13\1\0\1\13\6\0\13\13\1\0\1\13\4\0"+
    "\20\13\1\246\14\13\17\0\4\13\1\0\1\13\6\0"+
    "\13\13\1\0\1\13\4\0\11\13\1\247\23\13\17\0"+
    "\4\13\1\0\1\13\10\0\2\250\1\100\1\0\5\250"+
    "\1\0\1\250\4\0\2\250\2\0\1\250\4\0\1\250"+
    "\7\0\1\250\3\0\1\250\1\100\1\250\2\0\1\250"+
    "\1\0\1\250\35\0\1\251\1\77\1\100\2\0\3\251"+
    "\1\100\1\104\1\105\4\0\1\100\1\105\17\0\1\100"+
    "\4\0\1\100\3\0\1\100\35\0\13\13\1\0\1\13"+
    "\4\0\11\13\1\252\23\13\17\0\4\13\1\0\1\13"+
    "\6\0\13\13\1\0\1\13\4\0\11\13\1\253\23\13"+
    "\17\0\4\13\1\0\1\13\6\0\13\13\1\0\1\13"+
    "\4\0\1\13\1\254\33\13\17\0\4\13\1\0\1\13"+
    "\6\0\13\13\1\0\1\13\4\0\12\13\1\255\22\13"+
    "\17\0\4\13\1\0\1\13\6\0\13\13\1\0\1\13"+
    "\4\0\31\13\1\256\3\13\17\0\4\13\1\0\1\13"+
    "\6\0\13\13\1\0\1\257\4\0\35\13\17\0\4\13"+
    "\1\0\1\13\6\0\13\13\1\0\1\13\4\0\10\13"+
    "\1\162\24\13\17\0\4\13\1\0\1\13\10\0\2\260"+
    "\1\100\1\0\5\260\1\0\1\260\4\0\2\260\2\0"+
    "\1\260\4\0\1\260\7\0\1\260\3\0\1\260\1\100"+
    "\1\260\2\0\1\260\1\0\1\260\35\0\1\261\1\77"+
    "\1\100\2\0\3\261\1\100\1\104\1\105\4\0\1\100"+
    "\1\105\17\0\1\100\4\0\1\100\3\0\1\100\35\0"+
    "\13\13\1\0\1\13\4\0\12\13\1\262\22\13\17\0"+
    "\4\13\1\0\1\13\6\0\13\13\1\0\1\13\4\0"+
    "\12\13\1\263\22\13\17\0\4\13\1\0\1\13\6\0"+
    "\13\13\1\0\1\13\4\0\6\13\1\166\26\13\17\0"+
    "\4\13\1\0\1\13\6\0\13\13\1\0\1\13\4\0"+
    "\24\13\1\166\10\13\17\0\4\13\1\0\1\13\6\0"+
    "\13\13\1\0\1\13\4\0\15\13\1\176\17\13\17\0"+
    "\4\13\1\0\1\13\6\0\13\13\1\0\1\13\4\0"+
    "\5\13\1\264\27\13\17\0\4\13\1\0\1\13\10\0"+
    "\2\265\1\100\1\0\5\265\1\0\1\265\4\0\2\265"+
    "\2\0\1\265\4\0\1\265\7\0\1\265\3\0\1\265"+
    "\1\100\1\265\2\0\1\265\1\0\1\265\35\0\1\266"+
    "\1\77\1\100\2\0\3\266\1\100\1\104\1\105\4\0"+
    "\1\100\1\105\17\0\1\100\4\0\1\100\3\0\1\100"+
    "\35\0\13\13\1\0\1\13\4\0\13\13\1\254\21\13"+
    "\17\0\4\13\1\0\1\13\6\0\13\13\1\0\1\13"+
    "\4\0\3\13\1\267\31\13\17\0\4\13\1\0\1\13"+
    "\6\0\13\13\1\0\1\13\4\0\35\13\17\0\2\13"+
    "\1\270\1\13\1\0\1\13\10\0\2\271\1\100\1\0"+
    "\5\271\1\0\1\271\4\0\2\271\2\0\1\271\4\0"+
    "\1\271\7\0\1\271\3\0\1\271\1\100\1\271\2\0"+
    "\1\271\1\0\1\271\35\0\1\272\1\77\1\100\2\0"+
    "\3\272\1\100\1\104\1\105\4\0\1\100\1\105\17\0"+
    "\1\100\4\0\1\100\3\0\1\100\35\0\13\13\1\0"+
    "\1\13\4\0\13\13\1\273\21\13\17\0\4\13\1\0"+
    "\1\13\6\0\13\13\1\0\1\13\4\0\27\13\1\274"+
    "\5\13\17\0\4\13\1\0\1\13\10\0\2\275\1\100"+
    "\1\0\5\275\1\0\1\275\4\0\2\275\2\0\1\275"+
    "\4\0\1\275\7\0\1\275\3\0\1\275\1\100\1\275"+
    "\2\0\1\275\1\0\1\275\35\0\1\276\1\77\1\100"+
    "\2\0\3\276\1\100\1\104\1\105\4\0\1\100\1\105"+
    "\17\0\1\100\4\0\1\100\3\0\1\100\35\0\13\13"+
    "\1\0\1\13\4\0\10\13\1\166\24\13\17\0\4\13"+
    "\1\0\1\13\6\0\13\13\1\0\1\13\4\0\31\13"+
    "\1\176\3\13\17\0\4\13\1\0\1\13\10\0\2\277"+
    "\1\100\1\0\5\277\1\0\1\277\4\0\2\277\2\0"+
    "\1\277\4\0\1\277\7\0\1\277\3\0\1\277\1\100"+
    "\1\277\2\0\1\277\1\0\1\277\35\0\1\300\1\77"+
    "\1\100\2\0\3\300\1\100\1\104\1\105\4\0\1\100"+
    "\1\105\17\0\1\100\4\0\1\100\3\0\1\100\37\0"+
    "\2\301\1\100\1\0\5\301\1\0\1\301\4\0\2\301"+
    "\2\0\1\301\4\0\1\301\7\0\1\301\3\0\1\301"+
    "\1\100\1\301\2\0\1\301\1\0\1\301\35\0\1\302"+
    "\1\77\1\100\2\0\3\302\1\100\1\104\1\105\4\0"+
    "\1\100\1\105\17\0\1\100\4\0\1\100\3\0\1\100"+
    "\37\0\2\303\1\100\1\0\5\303\1\0\1\303\4\0"+
    "\2\303\2\0\1\303\4\0\1\303\7\0\1\303\3\0"+
    "\1\303\1\100\1\303\2\0\1\303\1\0\1\303\35\0"+
    "\1\304\1\77\1\100\2\0\3\304\1\100\1\104\1\105"+
    "\4\0\1\100\1\105\17\0\1\100\4\0\1\100\3\0"+
    "\1\100\37\0\2\305\1\100\1\0\5\305\1\0\1\305"+
    "\4\0\2\305\2\0\1\305\4\0\1\305\7\0\1\305"+
    "\3\0\1\305\1\100\1\305\2\0\1\305\1\0\1\305"+
    "\35\0\1\306\1\77\1\100\2\0\3\306\1\100\1\104"+
    "\1\105\4\0\1\100\1\105\17\0\1\100\4\0\1\100"+
    "\3\0\1\100\37\0\2\307\1\100\1\0\5\307\1\0"+
    "\1\307\4\0\2\307\2\0\1\307\4\0\1\307\7\0"+
    "\1\307\3\0\1\307\1\100\1\307\2\0\1\307\1\0"+
    "\1\307\35\0\1\310\1\77\1\100\2\0\3\310\1\100"+
    "\1\104\1\105\4\0\1\100\1\105\17\0\1\100\4\0"+
    "\1\100\3\0\1\100\37\0\2\311\1\100\1\0\5\311"+
    "\1\0\1\311\4\0\2\311\2\0\1\311\4\0\1\311"+
    "\7\0\1\311\3\0\1\311\1\100\1\311\2\0\1\311"+
    "\1\0\1\311\35\0\1\312\1\77\1\100\2\0\3\312"+
    "\1\100\1\104\1\105\4\0\1\100\1\105\17\0\1\100"+
    "\4\0\1\100\3\0\1\100\37\0\2\313\1\100\1\0"+
    "\5\313\1\0\1\313\4\0\2\313\2\0\1\313\4\0"+
    "\1\313\7\0\1\313\3\0\1\313\1\100\1\313\2\0"+
    "\1\313\1\0\1\313\35\0\1\314\1\77\1\100\2\0"+
    "\3\314\1\100\1\104\1\105\4\0\1\100\1\105\17\0"+
    "\1\100\4\0\1\100\3\0\1\100\41\0\1\100\42\0"+
    "\1\100\43\0\1\315\1\77\1\100\2\0\3\315\1\100"+
    "\1\104\1\105\4\0\1\100\1\105\17\0\1\100\4\0"+
    "\1\100\3\0\1\100\37\0\1\316\1\77\1\100\2\0"+
    "\3\316\1\100\1\104\1\105\4\0\1\100\1\105\17\0"+
    "\1\100\4\0\1\100\3\0\1\100\37\0\1\317\1\77"+
    "\1\100\2\0\3\317\1\100\1\104\1\105\4\0\1\100"+
    "\1\105\17\0\1\100\4\0\1\100\3\0\1\100\37\0"+
    "\1\320\1\77\1\100\2\0\3\320\1\100\1\104\1\105"+
    "\4\0\1\100\1\105\17\0\1\100\4\0\1\100\3\0"+
    "\1\100\37\0\2\77\1\100\2\0\3\77\1\100\1\104"+
    "\1\105\4\0\1\100\1\105\17\0\1\100\4\0\1\100"+
    "\3\0\1\100\27\0";

  private static int [] zzUnpackTrans() {
    int [] result = new int[13432];
    int offset = 0;
    offset = zzUnpackTrans(ZZ_TRANS_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackTrans(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      value--;
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /* error codes */
  private static final int ZZ_UNKNOWN_ERROR = 0;
  private static final int ZZ_NO_MATCH = 1;
  private static final int ZZ_PUSHBACK_2BIG = 2;

  /* error messages for the codes above */
  private static final String ZZ_ERROR_MSG[] = {
    "Unkown internal scanner error",
    "Error: could not match input",
    "Error: pushback value was too large"
  };

  /**
   * ZZ_ATTRIBUTE[aState] contains the attributes of state <code>aState</code>
   */
  private static final int [] ZZ_ATTRIBUTE = zzUnpackAttribute();

  private static final String ZZ_ATTRIBUTE_PACKED_0 =
    "\5\0\1\11\13\1\2\11\16\1\7\11\10\1\2\11"+
    "\3\1\1\11\1\1\2\11\3\1\1\0\2\1\1\11"+
    "\1\0\3\1\1\0\31\1\1\11\2\1\2\11\1\1"+
    "\1\11\1\0\5\1\1\0\25\1\2\0\71\1\1\0"+
    "\1\1\1\0\1\1\1\0\1\1\1\0\1\1\1\0"+
    "\1\1\1\0\1\1\1\0\1\1\1\0\5\1";

  private static int [] zzUnpackAttribute() {
    int [] result = new int[208];
    int offset = 0;
    offset = zzUnpackAttribute(ZZ_ATTRIBUTE_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAttribute(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }

  /** the input device */
  private java.io.Reader zzReader;

  /** the current state of the DFA */
  private int zzState;

  /** the current lexical state */
  private int zzLexicalState = YYINITIAL;

  /** this buffer contains the current text to be matched and is
      the source of the yytext() string */
  private char zzBuffer[] = new char[ZZ_BUFFERSIZE];

  /** the textposition at the last accepting state */
  private int zzMarkedPos;

  /** the current text position in the buffer */
  private int zzCurrentPos;

  /** startRead marks the beginning of the yytext() string in the buffer */
  private int zzStartRead;

  /** endRead marks the last character in the buffer, that has been read
      from input */
  private int zzEndRead;

  /** number of newlines encountered up to the start of the matched text */
  private int yyline;

  /** the number of characters up to the start of the matched text */
  private int yychar;

  /**
   * the number of characters from the last newline up to the start of the
   * matched text
   */
  private int yycolumn;

  /**
   * zzAtBOL == true <=> the scanner is currently at the beginning of a line
   */
  private boolean zzAtBOL = true;

  /** zzAtEOF == true <=> the scanner is at the EOF */
  private boolean zzAtEOF;

  /** denotes if the user-EOF-code has already been executed */
  private boolean zzEOFDone;

  /* user code: */
    /**
     * Create an empty lexer, yyrset will be called later to reset and assign
     * the reader
     */
    public EugeneLexer() {
        super();
    }

    @Override
    public int yychar() {
        return yychar;
    }

    private static final byte PARAN     = 1;
    private static final byte BRACKET   = 2;
    private static final byte CURLY     = 3;



  /**
   * Creates a new scanner
   * There is also a java.io.InputStream version of this constructor.
   *
   * @param   in  the java.io.Reader to read input from.
   */
  public EugeneLexer(java.io.Reader in) {
    this.zzReader = in;
  }

  /**
   * Creates a new scanner.
   * There is also java.io.Reader version of this constructor.
   *
   * @param   in  the java.io.Inputstream to read input from.
   */
  public EugeneLexer(java.io.InputStream in) {
    this(new java.io.InputStreamReader(in));
  }

  /**
   * Unpacks the compressed character translation table.
   *
   * @param packed   the packed character translation table
   * @return         the unpacked character translation table
   */
  private static char [] zzUnpackCMap(String packed) {
    char [] map = new char[0x10000];
    int i = 0;  /* index in packed string  */
    int j = 0;  /* index in unpacked array */
    while (i < 1824) {
      int  count = packed.charAt(i++);
      char value = packed.charAt(i++);
      do map[j++] = value; while (--count > 0);
    }
    return map;
  }


  /**
   * Refills the input buffer.
   *
   * @return      <code>false</code>, iff there was new input.
   *
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  private boolean zzRefill() throws java.io.IOException {

    /* first: make room (if you can) */
    if (zzStartRead > 0) {
      System.arraycopy(zzBuffer, zzStartRead,
                       zzBuffer, 0,
                       zzEndRead-zzStartRead);

      /* translate stored positions */
      zzEndRead-= zzStartRead;
      zzCurrentPos-= zzStartRead;
      zzMarkedPos-= zzStartRead;
      zzStartRead = 0;
    }

    /* is the buffer big enough? */
    if (zzCurrentPos >= zzBuffer.length) {
      /* if not: blow it up */
      char newBuffer[] = new char[zzCurrentPos*2];
      System.arraycopy(zzBuffer, 0, newBuffer, 0, zzBuffer.length);
      zzBuffer = newBuffer;
    }

    /* finally: fill the buffer with new input */
    int numRead = zzReader.read(zzBuffer, zzEndRead,
                                            zzBuffer.length-zzEndRead);

    if (numRead > 0) {
      zzEndRead+= numRead;
      return false;
    }
    // unlikely but not impossible: read 0 characters, but not at end of stream
    if (numRead == 0) {
      int c = zzReader.read();
      if (c == -1) {
        return true;
      } else {
        zzBuffer[zzEndRead++] = (char) c;
        return false;
      }
    }

	// numRead < 0
    return true;
  }


  /**
   * Closes the input stream.
   */
  public final void yyclose() throws java.io.IOException {
    zzAtEOF = true;            /* indicate end of file */
    zzEndRead = zzStartRead;  /* invalidate buffer    */

    if (zzReader != null)
      zzReader.close();
  }


  /**
   * Resets the scanner to read from a new input stream.
   * Does not close the old reader.
   *
   * All internal variables are reset, the old input stream
   * <b>cannot</b> be reused (internal buffer is discarded and lost).
   * Lexical state is set to <tt>ZZ_INITIAL</tt>.
   *
   * @param reader   the new input stream
   */
  public final void yyreset(java.io.Reader reader) {
    zzReader = reader;
    zzAtBOL  = true;
    zzAtEOF  = false;
    zzEOFDone = false;
    zzEndRead = zzStartRead = 0;
    zzCurrentPos = zzMarkedPos = 0;
    yyline = yychar = yycolumn = 0;
    zzLexicalState = YYINITIAL;
  }


  /**
   * Returns the current lexical state.
   */
  public final int yystate() {
    return zzLexicalState;
  }


  /**
   * Enters a new lexical state
   *
   * @param newState the new lexical state
   */
  public final void yybegin(int newState) {
    zzLexicalState = newState;
  }


  /**
   * Returns the text matched by the current regular expression.
   */
  public final String yytext() {
    return new String( zzBuffer, zzStartRead, zzMarkedPos-zzStartRead );
  }


  /**
   * Returns the character at position <tt>pos</tt> from the
   * matched text.
   *
   * It is equivalent to yytext().charAt(pos), but faster
   *
   * @param pos the position of the character to fetch.
   *            A value from 0 to yylength()-1.
   *
   * @return the character at position pos
   */
  public final char yycharat(int pos) {
    return zzBuffer[zzStartRead+pos];
  }


  /**
   * Returns the length of the matched text region.
   */
  public final int yylength() {
    return zzMarkedPos-zzStartRead;
  }


  /**
   * Reports an error that occured while scanning.
   *
   * In a wellformed scanner (no or only correct usage of
   * yypushback(int) and a match-all fallback rule) this method
   * will only be called with things that "Can't Possibly Happen".
   * If this method is called, something is seriously wrong
   * (e.g. a JFlex bug producing a faulty scanner etc.).
   *
   * Usual syntax/scanner level error handling should be done
   * in error fallback rules.
   *
   * @param   errorCode  the code of the errormessage to display
   */
  private void zzScanError(int errorCode) {
    String message;
    try {
      message = ZZ_ERROR_MSG[errorCode];
    }
    catch (ArrayIndexOutOfBoundsException e) {
      message = ZZ_ERROR_MSG[ZZ_UNKNOWN_ERROR];
    }

    throw new Error(message);
  }


  /**
   * Pushes the specified amount of characters back into the input stream.
   *
   * They will be read again by then next call of the scanning method
   *
   * @param number  the number of characters to be read again.
   *                This number must not be greater than yylength()!
   */
  public void yypushback(int number)  {
    if ( number > yylength() )
      zzScanError(ZZ_PUSHBACK_2BIG);

    zzMarkedPos -= number;
  }


  /**
   * Resumes scanning until the next regular expression is matched,
   * the end of input is encountered or an I/O-Error occurs.
   *
   * @return      the next token
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  public Token yylex() throws java.io.IOException {
    int zzInput;
    int zzAction;

    // cached fields:
    int zzCurrentPosL;
    int zzMarkedPosL;
    int zzEndReadL = zzEndRead;
    char [] zzBufferL = zzBuffer;
    char [] zzCMapL = ZZ_CMAP;

    int [] zzTransL = ZZ_TRANS;
    int [] zzRowMapL = ZZ_ROWMAP;
    int [] zzAttrL = ZZ_ATTRIBUTE;

    while (true) {
      zzMarkedPosL = zzMarkedPos;

      yychar+= zzMarkedPosL-zzStartRead;

      zzAction = -1;

      zzCurrentPosL = zzCurrentPos = zzStartRead = zzMarkedPosL;

      zzState = ZZ_LEXSTATE[zzLexicalState];


      zzForAction: {
        while (true) {

          if (zzCurrentPosL < zzEndReadL)
            zzInput = zzBufferL[zzCurrentPosL++];
          else if (zzAtEOF) {
            zzInput = YYEOF;
            break zzForAction;
          }
          else {
            // store back cached positions
            zzCurrentPos  = zzCurrentPosL;
            zzMarkedPos   = zzMarkedPosL;
            boolean eof = zzRefill();
            // get translated positions and possibly new buffer
            zzCurrentPosL  = zzCurrentPos;
            zzMarkedPosL   = zzMarkedPos;
            zzBufferL      = zzBuffer;
            zzEndReadL     = zzEndRead;
            if (eof) {
              zzInput = YYEOF;
              break zzForAction;
            }
            else {
              zzInput = zzBufferL[zzCurrentPosL++];
            }
          }
          int zzNext = zzTransL[ zzRowMapL[zzState] + zzCMapL[zzInput] ];
          if (zzNext == -1) break zzForAction;
          zzState = zzNext;

          int zzAttributes = zzAttrL[zzState];
          if ( (zzAttributes & 1) == 1 ) {
            zzAction = zzState;
            zzMarkedPosL = zzCurrentPosL;
            if ( (zzAttributes & 8) == 8 ) break zzForAction;
          }

        }
      }

      // store back cached position
      zzMarkedPos = zzMarkedPosL;

      switch (zzAction < 0 ? zzAction : ZZ_ACTION[zzAction]) {
        case 8:
          { return token(TokenType.OPERATOR, -PARAN);
          }
        case 25: break;
        case 24:
          { return token(TokenType.KEYWORD);
          }
        case 26: break;
        case 4:
          { return token(TokenType.NUMBER);
          }
        case 27: break;
        case 18:
          { yybegin(JDOC);
                                     // length also includes the trailing quote
                                     int start = tokenStart;
                                     tokenStart = yychar;
                                     int len = tokenLength;
                                     tokenLength = 1;
                                     return token(TokenType.COMMENT2, start, len);
          }
        case 28: break;
        case 21:
          { yybegin(YYINITIAL);
                                     return token(TokenType.COMMENT, tokenStart, tokenLength + 2);
          }
        case 29: break;
        case 2:
          { return token(TokenType.OPERATOR);
          }
        case 30: break;
        case 9:
          { return token(TokenType.OPERATOR,  CURLY);
          }
        case 31: break;
        case 10:
          { return token(TokenType.OPERATOR, -CURLY);
          }
        case 32: break;
        case 17:
          { yybegin(JDOC_TAG);
                                     int start = tokenStart;
                                     tokenStart = yychar;
                                     int len = tokenLength;
                                     tokenLength = 1;
                                     return token(TokenType.COMMENT, start, len);
          }
        case 33: break;
        case 13:
          { tokenLength += yylength();
          }
        case 34: break;
        case 14:
          { yybegin(YYINITIAL);
          }
        case 35: break;
        case 6:
          { yybegin(CHARLITERAL);
                                    tokenStart = yychar;
                                    tokenLength = 1;
          }
        case 36: break;
        case 22:
          { yybegin(JDOC);
                                    tokenStart = yychar;
                                    tokenLength = 3;
          }
        case 37: break;
        case 15:
          { yybegin(YYINITIAL);
                                     // length also includes the trailing quote
                                     return token(TokenType.STRING, tokenStart, tokenLength + 1);
          }
        case 38: break;
        case 23:
          { return token(TokenType.TYPE);
          }
        case 39: break;
        case 12:
          { return token(TokenType.OPERATOR, -BRACKET);
          }
        case 40: break;
        case 7:
          { return token(TokenType.OPERATOR,  PARAN);
          }
        case 41: break;
        case 3:
          { return token(TokenType.IDENTIFIER);
          }
        case 42: break;
        case 20:
          { tokenLength += 2;
          }
        case 43: break;
        case 16:
          { tokenLength ++;
          }
        case 44: break;
        case 11:
          { return token(TokenType.OPERATOR,  BRACKET);
          }
        case 45: break;
        case 19:
          { return token(TokenType.COMMENT);
          }
        case 46: break;
        case 5:
          { yybegin(STRING);
                                    tokenStart = yychar;
                                    tokenLength = 1;
          }
        case 47: break;
        case 1:
          {
          }
        case 48: break;
        default:
          if (zzInput == YYEOF && zzStartRead == zzCurrentPos) {
            zzAtEOF = true;
              {
                return null;
              }
          }
          else {
            zzScanError(ZZ_NO_MATCH);
          }
      }
    }
  }


}
