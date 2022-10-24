package com.webdb.api.util;

import java.util.Arrays;

public class AutoTypeUtil {

	 //초성
	private static final char[] FIRST_KR = {'ㄱ', 'ㄲ', 'ㄴ', 'ㄷ', 'ㄸ', 'ㄹ', 'ㅁ', 'ㅂ',
			'ㅃ', 'ㅅ', 'ㅆ', 'ㅇ', 'ㅈ', 'ㅉ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ'};
	private static final String[] FIRST_EN = {"r", "R", "s", "e", "E", "f", "a", "q",
		"Q", "t", "T", "d", "w", "W", "c", "z", "x", "v", "g"};
	
   //중성
	/*
	private static final char[] MIDDLE_KR = {'ㅏ', 'ㅐ', 'ㅑ', 'ㅒ', 'ㅓ', 'ㅔ', 'ㅕ', 'ㅖ',
   		'ㅗ', 'ㅘ', 'ㅙ', 'ㅚ', 'ㅛ', 'ㅜ', 'ㅝ', 'ㅞ', 'ㅟ', 'ㅠ', 'ㅡ',
   		'ㅢ', 'ㅣ'};
	*/
	private static final String[] MIDDLE_EN = {"k", "o", "i", "O", "j", "p", "u", "P",
		"h", "hk", "ho", "hl", "y", "n", "nj", "np", "nl", "b", "m",
		"ml", "l"};
	
   //종성
	/*
	private static final char[] LAST_KR = {' ', 'ㄱ', 'ㄲ', 'ㄳ', 'ㄴ', 'ㄵ', 'ㄶ', 'ㄷ',
   		'ㄹ', 'ㄺ', 'ㄻ', 'ㄼ', 'ㄽ', 'ㄾ', 'ㄿ', 'ㅀ', 'ㅁ',
   		'ㅂ', 'ㅄ', 'ㅅ', 'ㅆ', 'ㅇ', 'ㅈ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ'};
	*/
	private static final String[] LAST_EN = {"", "r", "R", "rt", "s", "sw", "sg", "e",
		"f", "fr", "fa", "fq", "ft", "fx", "fv", "fg", "a",
		"q", "qt", "t", "T", "d", "w", "c", "z", "x", "v", "g"};
	
	public static String toQWERT(String str)
	{
		StringBuffer sb = new StringBuffer();
		
		for (int i = 0; i < str.length(); i++)
		{
			char c = str.charAt(i);
			char v = (char)(c - 0xAC00);
	        
	        if (v >= 0 && v <= 11172)
	        {
	        	//초, 중, 종성의 위치
	        	int first	= v / (21*28);
	        	int middle	= v % (21*28) / 28;
	        	int last	= v % (28);
	        	
	        	sb.append(FIRST_EN[first]).append(MIDDLE_EN[middle]).append(LAST_EN[last]);
	        } else
	        {
	        	int j = Arrays.binarySearch(FIRST_KR, c);
	        	
	        	if (j > -1) {
	        		sb.append(FIRST_EN[j]);
	        	} else {
	        		sb.append(str.substring(i, i + 1));
	        	}
	        }
	        
		}
		
		return sb.toString();
	}
}
