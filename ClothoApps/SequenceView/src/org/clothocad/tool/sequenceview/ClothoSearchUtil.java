/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.clothocad.tool.sequenceview;
import java.util.regex.*;
/**
 *
 * @author Nade Sritanyaratana
 * @author Roza Ghamari
 */
public class ClothoSearchUtil {
/** Constructor. Instantiates ClothoHelper.
 * @param pattern
 * @param searchtext
 */
    public ClothoSearchUtil(String pattern, String searchtext){
        makePattern(pattern, "");
        makeSearchIndex(searchtext);
        patternString = pattern;
        searchText = searchtext;
        }

    /**
     * Constructor. Option determines if search should be case sensitive.
     * @param pattern
     * @param searchtext
     * @param option
     */
   public ClothoSearchUtil(String pattern, String searchtext, String option){
        makePattern(pattern, option);
        makeSearchIndex(searchtext);
        patternString = pattern;
        searchText = searchtext;
        }

    public int getHitCount(){
        return _hitCount;
    }

    public int[][] getSearch(){
        return _search;
    }

    public int[] getSearchEnd(){
        return _searchEnd;
    }

    public int[] getSearchStart(){
        return _searchStart;
    }

    private void makePattern(String pattern, String option) {
        if (option.matches("case")) {
        _pattern = java.util.regex.Pattern.compile(pattern,
        java.util.regex.Pattern.UNIX_LINES +
        java.util.regex.Pattern.MULTILINE);
        }
        else
        _pattern = java.util.regex.Pattern.compile(pattern,
        java.util.regex.Pattern.CASE_INSENSITIVE +
        java.util.regex.Pattern.UNIX_LINES +
        java.util.regex.Pattern.MULTILINE);
    }

/**
 * makeSearchIndex creates an int[][] = search[i][j].
 * i=0 corresponds to the start of match j.
 * i=1 corresponds to the end of match j.
 * j corresponds to a particular match, j=0 being the first match.
 * @param pattern
 * @param searchtext
 */
    private void makeSearchIndex(String searchtext) {

        _matcher = _pattern.matcher(searchtext);
        _hitCount = 0;
        while (_matcher.find()){
            _hitCount++;
        }
        _matcher.reset();
        _search = new int[2][_hitCount];
        _searchStart = new int[_hitCount];
        _searchEnd = new int[_hitCount];

        int newlinecompensation = 0;
        for (int i=0; _matcher.find(); i++){
            newlinecompensation = subSearch("\n",searchtext.substring(0, _matcher.end()));
            _searchStart[i]=_matcher.start() - newlinecompensation;
            _searchEnd[i]=_matcher.end() - newlinecompensation;
        }
        _search[0]= _searchStart;
       _search[1]= _searchEnd;
    }

    /**
     * subsearch used only for "meta" ClothoSearchUtil purposes. Its uses are
     * listed below:
     * 1. to search how many newlines there are in the substring for a JTextPane
     *    hack (see ClothoHelpConnection.search).
     *
     * Returns an int corresponding to hitcount.
     * @param pattern
     * @param searchtext
     * @return
     */
    private int subSearch(String pattern, String searchtext) {
        Pattern p = java.util.regex.Pattern.compile(pattern,
        java.util.regex.Pattern.UNIX_LINES +
        java.util.regex.Pattern.MULTILINE);

        Matcher m = p.matcher(searchtext);
        int hitCount = 0;
        while (m.find()){
            hitCount++;
        }

        return hitCount;
    }

    private int _hitCount;
    private int[] _searchEnd;
    private int[] _searchStart;
    private int[][] _search;
    private Matcher _matcher;
    private Pattern _pattern;
    public String patternString;
    public String searchText;
}
