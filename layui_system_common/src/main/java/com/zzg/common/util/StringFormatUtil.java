package com.zzg.common.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 字符串格式化工具类
 * @author zzg
 *
 */
public class StringFormatUtil {
	
	/**
	 * 字符串指定长度,左边补0
	 * @param str
	 * @param length
	 * @return
	 */
	public static String addZeroForNum(String str, int length) {
	    int strLen = str.length();
	    if (strLen < length) {
	        while (strLen < length) {
	            StringBuffer sb = new StringBuffer();
	            sb.append("0").append(str);// 左补0
	            str = sb.toString();
	            strLen = str.length();
	        }
	    }
	    return str;
	}
	
	/**
     * 左补函数
     * @param str
     * @param len 
     * @param pad
     * @return
     */
    public static String lpad(String str, int len, String pad) 
	 {
		 StringBuilder temp = new StringBuilder();
		 while (temp.length() < len - str.length()) 
		 {
			 temp.append(pad);
		 }   
		 return temp.append(str).toString();    
	 }
	 
    /**
     * 右补函数
     * @param str
     * @param len
     * @param pad
     * @return
     */
    public static String rpad(String str, int len, String pad) 
	 {
		 StringBuilder temp = new StringBuilder(str);
		 while (temp.length() < len) 
		 {
			 temp.append(pad);
		 }   
		 return temp.toString();    
	 }
	
	/**
     * 将下划线转换为驼峰的形式，例如：eng_name --> engName
     *
     * @return string
     */
	private static final Pattern linePattern = Pattern.compile("_(\\w)");
	public static String lineToHump(String str) {
		str = str.toLowerCase();
		Matcher matcher = linePattern.matcher(str);
		StringBuffer sb = new StringBuffer();
		while (matcher.find()) {
			matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
		}
		matcher.appendTail(sb);
		return sb.toString();
	}

	/**
     * 驼峰转下划线，例如：engName --> eng_name
     *
     * @return string
     */
	private static final Pattern humpPattern = Pattern.compile("[A-Z]");
	public static String humpToLine(String str) {
		Matcher matcher = humpPattern.matcher(str);
	     StringBuffer sb = new StringBuffer();
	     while (matcher.find()) {
	          matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
	      }
	       matcher.appendTail(sb);
	     return sb.toString();
	}
	
	/**
     * 查找路径字符串中,路径key信息并返回
     * str ： E1\doc/format\shouwen\2019-3\19\11\ ,  rs: E1  
     * @param str  
     * @return rs  
     */
	private static final Pattern filePathPattern = Pattern.compile("(\\w*)[\\\\\\||\\/]");
    public static String mathPathKeyByFilePath(String pathStr)
    {
    	String rs = "";
	    Matcher m = filePathPattern.matcher(pathStr);
	    if(m.find()){
	    	rs = m.group(1);
	    }
    	return rs;
    }
    
   
    
    public static String nvl(Object str, String defStr) {
    	if (str == null) 
    	{
    		return defStr;
    	}
    	return str.toString();
    }
    
    
    private static final Log logger = LogFactory.getLog(StringFormatUtil.class);

    public static final String WIN_ENTER = "\r\n";

    private final static char CHARS[] = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C',
            'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
            'Y', 'Z' };

    /**
     * 右省略的方式表示字符串，len长度后的字符串表示为“...”
     * 
     * @param oldStr
     * @param len
     * @return
     * @author niez
     * @since May 26, 2010
     */
    public static String cutRedundanceStr(String oldStr, int len) {
        if (oldStr == null) {
            return "";
        }
        if (oldStr.length() <= len) {
            return oldStr;
        } else {
            return (oldStr.substring(0, len) + " ...");
        }
    }

    /**
     * 左省略，len长度前的字符串表示为“...”
     * 
     * @param oldStr
     * @param len
     * @return
     * @author niez
     * @since May 26, 2010
     */
    public static String cutLeftRedundanceStr(String oldStr, int len) {
        if (oldStr == null) {
            return "";
        }
        if (oldStr.length() <= len) {
            return oldStr;
        } else {
            return ("..." + oldStr.substring(oldStr.length() - len, oldStr.length()));
        }
    }

    /**
     * replace b in a to c, concern uppercase?d=true:d=false
     */
    public static String replace(String source, String findWhat, String replaceWith, boolean caseSence) {
        return replace(new StringBuffer(source), findWhat, replaceWith, caseSence).toString();
    }

    public static String replace(String source, String findWhat, String replaceWith) {
        return replace(new StringBuffer(source), findWhat, replaceWith, true).toString();
        // return source.replaceAll(findWhat, replaceWith);
    }

    public static StringBuffer replace(StringBuffer source, String findWhat, String replaceWith) {
        return replace(source, findWhat, replaceWith, true);
    }

    public static StringBuffer replace(StringBuffer source, String findWhat, String replaceWith, boolean caseSence) {
        if (source == null || findWhat == null || replaceWith == null) {
            return source;
        }
        if (findWhat.length() == 0) {
            return source;
        }
        int fromIndex = 0;
        int occIndex = -1;
        if (caseSence) {
            occIndex = source.toString().indexOf(findWhat, fromIndex);
        } else {
            occIndex = source.toString().toUpperCase().indexOf(findWhat.toUpperCase(), fromIndex);
        }

        while (occIndex >= 0) {
            source.delete(occIndex, occIndex + findWhat.length());
            source.insert(occIndex, replaceWith);
            fromIndex = occIndex + replaceWith.length();
            if (caseSence) {
                occIndex = source.toString().indexOf(findWhat, fromIndex);
            } else {
                occIndex = source.toString().toUpperCase().indexOf(findWhat.toUpperCase(), fromIndex);
            }
        }
        return source;
    }

    public static String htmlEncode(String str) {
        if (str == null) {
            return "";
        }
        String tmp = str;
        tmp = replace(tmp, "&", "&amp;", false);
        tmp = replace(tmp, "<", "&lt;", false);
        tmp = replace(tmp, "\"", "&quot;", false);
        tmp = replace(tmp, ">", "&gt;", false);

        tmp = replace(tmp, " ", " &nbsp;", false);
        tmp = replace(tmp, "\n", "<br>", false);
        /*
         * if (tmp.indexOf("\n") >= 0 ){ tmp = replace(tmp, "\n", " <br> ",
         * false); tmp = replace(tmp, " ", "&nbsp;", false); }
         */
        return tmp;
    }

    // public static String urlEncode(String str) {
    // return java.net.URLEncoder.encode(str);
    // }

    public static String jsValueEncode(String str) {
        return jsValueEncode(str, "\"");
    }

    public static String jsValueEncode(String str, String pack) {
        if (str == null) {
            return null;
        }
        String tmp = str;
        tmp = replace(tmp, "\\", "\\\\", true);
        tmp = replace(tmp, "\n", "\\n", true);
        tmp = replace(tmp, "\"", "\\\"", true);
        tmp = replace(tmp, "'", "\\'", true);
        return pack + tmp + pack;
    }

    public static String formFieldValueEncode(String str) {
        return formFieldValueEncode(str, "\"");
    }

    public static String formFieldValueEncode(String str, String pack) {
        if (str == null) {
            return pack + pack;
        }
        String tmp = str;
        tmp = replace(tmp, "\"", "&quot;", false);
        tmp = pack + tmp + pack;
        return tmp;
    }

    public static String recode(String source, String decodeCharset, String encodeCharset)
            throws UnsupportedEncodingException {
        if (decodeCharset.equalsIgnoreCase(encodeCharset)) {
            return source;
        } else {
            return new String(source.getBytes(decodeCharset), encodeCharset);
        }
    }

    /**
     * return the parameter list.
     * findParam("Ther versionId is {versionId}, created by {userId}.",'{','}')
     * will return ["{versionId}","{userId}"]
     * 
     * @param src
     * @param patternFrom
     * @param patternTo
     * @return
     */
    public static String[] findParam(String src, char patternFrom, char patternTo) {
        ArrayList list = new ArrayList();
        int i, j = 0, c = 0;
        while (c < src.length()) {
            i = src.indexOf(patternFrom, c);
            if (i != -1) {
                j = src.indexOf(patternTo, i + 1);
                if (j != -1) {
                    list.add(src.substring(i, j + 1));
                    c = j + 1;
                } else {
                    break;
                }
            } else {
                break;
            }
        }
        String[] result = new String[list.size()];
        for (i = 0; i < list.size(); i++) {
            result[i] = (String) list.get(i);
        }
        return result;
    }

    public static String setParam(String src, HashMap values, char patternFrom, char patternTo) {
        if (values == null) {
            return src;
        }
        String reStr = src;
        String name = null;
        Object value = null;
        String[] params = findParam(src, patternFrom, patternTo);
        for (int k = 0; k < params.length; k++) {
            name = params[k];
            value = values.get(name.substring(1, name.length() - 1));
            if (values.containsKey(name.substring(1, name.length() - 1)) && (value != null)) {
                reStr = replace(reStr, name, value.toString(), false);
            }
        }
        return reStr;
    }

    public static ArrayList split(String str, String id) {
        ArrayList vt = new ArrayList();
        if ((str != null) && (id != null) && (!str.equals("")) && (!id.equals(""))) {
            int beginindex = 0 - id.length();
            int endindex = 0;
            int end = str.lastIndexOf(id);
            if (end == -1) {
                vt.add(str);
            } else {
                while (endindex < end) {
                    endindex = str.indexOf(id, beginindex + id.length());
                    vt.add(str.substring(beginindex + id.length(), endindex));
                    beginindex = endindex;
                }
                vt.add(str.substring(endindex + id.length(), str.length()));
            }
        }
        return vt;
        /*
         * ArrayList tmp = new ArrayList(); if ((str == null) || (id == null) ||
         * (str.equals("")) || (id.equals(""))){ return tmp; } String[] t =
         * str.split(id); for (int i = 0; i < t.length; i++){ tmp.add(tmp); }
         * return tmp;
         */
    }

    public static String setInitial(String source) {
        if (source == null) {
            return null;
        }
        if (source.length() == 0) {
            return source;
        }
        return source.substring(0, 1).toUpperCase() + source.substring(1, source.length());
    }

    /**
     * @author zyb
     * @date 2004/6/8
     * 
     * @note : convert a date to string with format as YYYY-MM-DD
     * 
     * 
     * @param value
     * @return
     * @throws Exception
     */
    public static String toYMDString(Date value) throws Exception {
        SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
        return df1.format(value);
    }

    /**
     * @author zyb
     * @date 2004/6/8
     * 
     * @note : convert a string to date with YYYY-MM-dd
     * 
     * @param value
     * @return
     * @throws Exception
     */
    public static Date toYMDDate(String value) throws Exception {
        try {
            SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
            Date dateResult = df1.parse(value);
            return dateResult;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    /**
     * @author zyb
     * @date 2004/6/8
     * @note :
     * 
     * @param beginDate
     * @param endDate
     * @return
     */
    public static long compareDate(String beginDate, String endDate) throws Exception {
        try {
            Date d1 = StringFormatUtil.toYMDDate(beginDate);
            Date d2 = StringFormatUtil.toYMDDate(endDate);
            System.out.println("d1: " + d1 + "d2 :" + d2);

            long beginTime = d1.getTime();
            long endTime = d2.getTime();
            long betweenDays = (long) ((endTime - beginTime) / (1000 * 60 * 60 * 24) + 0.5);
            System.out.println("mins : " + betweenDays);
            return betweenDays;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    public static boolean isSequenceDate(String dateBefore, String dateAfter) {
        try {
            return 1.0 == compareDate(dateBefore, dateAfter);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * format the date
     */
    public static String format(Date date, String pattern) {
        return new java.text.SimpleDateFormat(pattern).format(date);
    }

    public static String getFileContent(String fileName) throws FileNotFoundException, IOException {
        BufferedReader r = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "GB2312"));
        StringBuffer content = null;
        try {
            content = new StringBuffer("");
            String tmp = r.readLine();
            while (tmp != null) {
                content = content.append(tmp).append(WIN_ENTER);
                tmp = r.readLine();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            r.close();
        }
        return content.toString();
    }

    public static String getStringFromStream(InputStream in) throws Exception {
        BufferedReader r = new BufferedReader(new InputStreamReader(in, "GB2312"));
        String tmp = r.readLine();
        StringBuffer returnS = new StringBuffer("");
        while (tmp != null) {
            returnS = returnS.append(tmp).append(WIN_ENTER);
            tmp = r.readLine();
        }
        return returnS.toString();
    }

    public static List getInterContent(String src, String start, String end) {
        List tmp = new ArrayList();
        int i = src.indexOf(start);
        int j = -1;
        while (i >= 0) {
            j = src.indexOf(end, i + start.length());
            if (j >= 0) {
                tmp.add(src.substring(i + start.length(), j));
            }
            i = src.indexOf(start, i + start.length());
        }
        return tmp;
    }

    public static String getRandomNumber(int length) {
        int randomNumber;
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            randomNumber = (int) (Math.random() * 10);
            sb.append(randomNumber);
        }
        return sb.toString();
    }

    public static char getNextChar(char c) {
        for (int i = 0; i < CHARS.length; i++) {
            if (c == CHARS[i] && i != CHARS.length - 1) {
                return CHARS[i + 1];
            }
        }
        throw new RuntimeException("can not get the next char.");
    }

    public static String getExcelColumn(int index) {
        char A = 'A';
        int i = index / 26;
        int j = index % 26;
        if (i == 0) {
            return "" + (char) (A + j);
        } else {
            return "" + (char) (A + i - 1) + (char) (A + j);
        }
    }

    /**
     * Find the index of the searchStr in the string array strs.
     * Return -1, if not found.
     * 
     * <pre>
     * StringUtil.indexOf(["s1", "s2"], "s1", true) = 0
     * StringUtil.indexOf(["s1", "s2"], "S1", true) = -1
     * </pre>
     * 
     * @param strs
     *            the string array to check, maybe null.
     * @param searchStr
     *            the string to search for, maybe null.
     * @param caseSensive
     *            is it case sensive while finding the searchStr
     *            in the searchStr.
     * @return index of the searchStr found in the strs, -1 if not found.
     * 
     * @author chenke
     */
    public static int indexOf(String[] strs, String searchStr, boolean caseSensive) {

        if (strs == null || strs.length == 0) {
            return -1;
        }
        for (int i = 0; i < strs.length; i++) {
            if (isEqual(strs[i], searchStr, caseSensive)) {
                return i;
            }
        }
        return -1;
    }

    public static int indexOf(String[] strs, String searchStr) {
        return indexOf(strs, searchStr, true);
    }

    private static boolean isEqual(String s1, String s2, boolean caseSensive) {
        if (caseSensive) {
            return s1 == null ? s2 == null : s1.equals(s2);
        } else {
            return s1 == null ? s2 == null : s1.equalsIgnoreCase(s2);
        }
    }

    private static final String[] EMPTY_STRING_ARRAY = new String[0];

    /**
     * Returns a string array contains all the String object in the
     * Collection c.
     * 
     * @param c
     *            Collection of String object.
     * @throws ClassCastException
     *             if the object in the Collection is
     *             not a String object.
     * 
     * @author chenke
     */
    public static String[] toArray(Collection c) {
        if (c == null || c.size() == 0) {
            return EMPTY_STRING_ARRAY;
        }
        String[] result = new String[c.size()];
        int i = 0;
        for (Iterator iter = c.iterator(); iter.hasNext();) {
            result[i++] = (String) iter.next();
        }
        return result;
    }

    /**
     * Return the result of left minus right.
     * 
     * <pre>
     * StringUtil.minus(["s1", "s2"], ["s1"], true) = ["s2"]
     * StringUtil.minus(["s1", "s2"], ["S1"], false) = ["s2"]
     * </pre>
     * 
     * @author chenke
     */
    public static String[] minus(String[] left, String[] right, boolean caseSensive) {
        if (left == null || left.length == 0) {
            return EMPTY_STRING_ARRAY;
        }
        if (right == null || right.length == 0) {
            return left;
        }
        List result = new ArrayList(left.length);
        for (int i = 0; i < left.length; i++) {
            int index = indexOf(right, left[i], caseSensive);
            if (index == -1) {
                result.add(left[i]);
            }
        }
        return toArray(result);
    }

    public static String[] minus(String[] left, String[] right) {
        return minus(left, right, true);
    }

    /**
     * Return a String object join all String object in param c, and add
     * param left and param right to every String object on the left side
     * and right side, separaing with param separator.
     * 
     * <pre>
     * join(["s1", "s2"], "left", "right", ",") = "lefts1right,lefts2right"
     * </pre>
     * 
     * @throws ClassCastException
     *             if the object in the Collection is
     *             not a String object.
     */
    public static String join(Collection c, String left, String right, String separator) {

        if (c==null||c.size()==0) {
            return null;
        }
        StringBuffer sb = new StringBuffer();
        boolean firstFlag = true;
        for (Iterator it = c.iterator(); it.hasNext();) {
            if (firstFlag) {
                firstFlag = false;
            } else if (separator != null) {
                sb.append(separator);
            }
            String s = (String) it.next();
            if (left != null) {
                sb.append(left);
            }
            sb.append(s);
            if (right != null) {
                sb.append(right);
            }
        }
        return sb.toString();
    }

    public static String join(Collection c) {
        return join(c, "<", ">", ",");
    }

    public static String[] plus(String[] s1, String[] s2) {
        if (s1 == null) {
            return s2 == null ? EMPTY_STRING_ARRAY : s2;
        }
        if (s2 == null) {
            return s1;
        }

        String[] result = new String[s1.length + s2.length];

        System.arraycopy(s1, 0, result, 0, s1.length);
        System.arraycopy(s2, 0, result, s1.length, s2.length);
        return result;
    }

    /**
     * converts the specified <code>String</code> array to a token <code>String</code>.
     * the token is a char ','. if <code>array</code> is <code>null</code> or zeor length,
     * return an empty <code>String</code>; if the element in the <code>array</code> is <code>null</code> or empty
     * <code>String</code>, ignores this element.
     * 
     * @param array
     *            to be converted array
     * @return converted token <code>String</code>
     * @author sunf
     * @since AP.308
     */
    public static String convertArrayToString(String[] array) {
        if (array == null || array.length == 0) {
            return "";
        }
        final char token = ',';
        StringBuffer result = new StringBuffer();

        for (int i = 0, size = array.length; i < size; i++) {
            if (array[i] == null || array[i].length() == 0) {
                continue;
            }
            result.append(array[i]);
            result.append(token);
        }
        // deletes the last char of \'
        if (result.length() > 1) {
            result.deleteCharAt(result.length() - 1);
        }

        return result.toString();
    }
    
    public static String convertArrayToString(long[] array) {
        if (array == null || array.length == 0) {
            return "";
        }
        final char token = ',';
        StringBuffer result = new StringBuffer();

        for (int i = 0, size = array.length; i < size; i++) {
            result.append(array[i]);
            result.append(token);
        }
        // deletes the last char of \'
        if (result.length() > 1) {
            result.deleteCharAt(result.length() - 1);
        }

        return result.toString();
    }

    public static boolean isEquals(String s1, String s2) {
        return s1 == null ? s2 == null : s1.equals(s2);
    }

    /**
     * if str starts with a prefix which is included in prefixes collection
     * return true, otherwise return false.
     * 
     * @param str
     * @param prefixes
     *            a collection of String objects
     * 
     * @return if str starts with a prefix which is included in prefixes collection
     *         return true, otherwise return false.
     */
    public static boolean containsPrefix(String str, Collection prefixes) {
        if (str != null && prefixes != null) {
            Iterator it = prefixes.iterator();
            while (it.hasNext()) {
                String prefix = (String) it.next();
                if (str.startsWith(prefix)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Get the next order id according to the order id of current version id
     * order id is last two characters, they indicate a order numeber
     * eg. 00, 01, 02, ...10, 11, ... 99
     * 
     * @param pattern
     * @return next order id - String
     */
    public static String getNextOrderId(String versionIdWithOrder) {
        String nextOrderId;

        if (versionIdWithOrder == null || versionIdWithOrder.length() <= 0) {
            nextOrderId = "00";
        } else {
            char major = versionIdWithOrder.charAt(versionIdWithOrder.length() - 2);
            char minor = versionIdWithOrder.charAt(versionIdWithOrder.length() - 1);
            // if last number equal 9, the order id need to re-arrenge
            if (minor == '9') {
                minor = '0';
                major++;
            } else {
                minor++;
            }
            nextOrderId = "" + major + minor;
        }

        return nextOrderId;
    }

    /**
     * If code value in range [-64, -17], it's a character header with enconding UTF-8
     * 
     * @param codeValue
     * @return boolean
     */
    private static boolean isCharacterHeaderWithUTF8(int codeValue) {
        if (codeValue >= -64 && codeValue <= -17) {
            return true;
        }
        return false;
    }

    /**
     * If code value in range [-128, -65], it's a character body with enconding UTF-8
     * 
     * @param codeValue
     * @return boolean
     */
    private static boolean isCharacterBodyWithUTF8(int codeValue) {
        if (codeValue >= -128 && codeValue <= -65) {
            return true;
        }
        return false;
    }

    /**
     * cut off string which consists of chinese characters and ascii characters
     * with UTF-8 encoding way
     * 
     * UTF-8 encoding mode
     * 0xxx xxxx [0, 127] for ascii
     * 110x xxxx 10xx xxxx [-33, -64] [-65, -128] for chinese character
     * 1110 xxxx 10xx xxxx 10xx xxxx [-32, -17] [-65, -128] [-65, -128] for chinese character
     * 
     * @param originalStr
     *            - original string
     * @param length
     *            - desired length to shape this character
     * 
     * @return string which has been cut off redundant characters
     *         or null if length given is illegal
     * @throws UnsupportedEncodingException
     */
    public static String cutStringWithUTF8(String originalStr, int length) throws UnsupportedEncodingException {
        String result = null;
        byte[] bytes = originalStr.getBytes("UTF-8");
        if (length <= bytes.length) {
            int codeValue = bytes[length - 1];
            if (logger.isDebugEnabled()) {
                logger.debug("codeValue at last place: " + codeValue);
            }
            if (codeValue > 0) {
                result = new String(bytes, 0, length, "UTF-8");

            } else if (isCharacterHeaderWithUTF8(codeValue)) {
                result = new String(bytes, 0, length - 1, "UTF-8");

            } else if (isCharacterBodyWithUTF8(codeValue)) {
                codeValue = bytes[length - 2];
                if (logger.isDebugEnabled()) {
                    logger.debug("codeValue at the second last place: " + codeValue);
                }

                if (isCharacterHeaderWithUTF8(codeValue)) {
                    result = new String(bytes, 0, length - 2, "UTF-8");

                } else if ((isCharacterBodyWithUTF8(codeValue))) {
                    result = new String(bytes, 0, length, "UTF-8");
                }
            }
        }

        return result;
    }

    public static String cutString(String str, int length, String charsetName) throws UnsupportedEncodingException {
        int len = str.getBytes(charsetName).length;
        if (len > length) {
            char[] chars = str.toCharArray();
            int i = 0;
            for (; i < chars.length; i++) {
                String tstr = new String(chars, 0, i + 1);
                if (tstr.getBytes(charsetName).length > length) {
                    i--;
                    break;
                }
            }
            return new String(chars, 0, i + 1);
        }

        return str;
    }

    public static boolean isBlank(String str) {
        return str == null || str.trim().equals("");
    }

    /**
     * 拼装适合SQL IN的字符串语句
     * 
     * @param str
     * @return
     * @author niez
     * @since May 26, 2010
     */
    public static String toSqlInString(String str) {
        if (str == null || str.equals("")) {
            return str;
        }
        String[] splitStrs = str.split(",");
        String targetStr = "";
        for (int i = 0; i < splitStrs.length; i++) {
            if (i == splitStrs.length - 1) {
                targetStr += "'" + splitStrs[i] + "'";
            } else {
                targetStr += "'" + splitStrs[i] + "',";
            }

        }
        return targetStr;
    }
    /**
     * 将整数转换成指定长度字符传，前面加0补位
     * @param value  要转换的整数
     * @param length  长度
     * @return
     */
    public static String formatIntToString(Integer value, Integer length) {
    	if (value == null) {
    		return null;
    	}
    	String strValue = value.toString();
    	while (strValue.length() < length) {
    		strValue = "0" + strValue;
    	}
    	return strValue;
    }
    
    
	 
   
    
    /**
     * 库房视图层信息展示使用
     * @param str
     * @param len
     * @param end
     * @return
     */
    public static String substr(String str, int begin, int end) 
	 {
		 if(str.length() == 1 )
		 {
			if(Integer.parseInt(str) > 0)
				return "1";
			else
				return "0";
		 } 
		 else
			 return str.substring(begin, end);
	 }
    
}
