package com.dotdotdash.manager;

import java.util.HashMap;
import java.util.Locale;
import java.util.StringTokenizer;

public class TranslateManager {
	
	private HashMap<String, String> mTextToMorseMap;
	private HashMap<String, String> mMorseToTextMap;
	
	public TranslateManager()
	{
		initializeTextToMorse();
		initializeMorseToText();
	}
	
	private void initializeTextToMorse()
	{
		mTextToMorseMap = new HashMap<String, String>();
		
		//Letters
		mTextToMorseMap.put("A", ".-");
		mTextToMorseMap.put("B", "-...");
		mTextToMorseMap.put("C", "-.-.");
		mTextToMorseMap.put("D", "-..");
		mTextToMorseMap.put("E", ".");
		mTextToMorseMap.put("F", "..-.");
		mTextToMorseMap.put("G", "--.");
		mTextToMorseMap.put("H", "....");
		mTextToMorseMap.put("I", "..");
		mTextToMorseMap.put("J", ".---");
		mTextToMorseMap.put("K", "-.-");
		mTextToMorseMap.put("L", ".-..");
		mTextToMorseMap.put("M", "--");
		mTextToMorseMap.put("N", "-.");
		mTextToMorseMap.put("O", "---");
		mTextToMorseMap.put("P", ".--.");
		mTextToMorseMap.put("Q", "--.-");
		mTextToMorseMap.put("R", ".-.");
		mTextToMorseMap.put("S", "...");
		mTextToMorseMap.put("T", "-");
		mTextToMorseMap.put("U", "..-");
		mTextToMorseMap.put("V", "...-");
		mTextToMorseMap.put("W", ".--");
		mTextToMorseMap.put("X", "-..-");
		mTextToMorseMap.put("Y", "-.--");
		mTextToMorseMap.put("Z", "--..");
		mTextToMorseMap.put(" ", "");
		
		//Numbers
		mTextToMorseMap.put("0", "-----");
		mTextToMorseMap.put("1", ".----");
		mTextToMorseMap.put("2", "..---");
		mTextToMorseMap.put("3", "...--");
		mTextToMorseMap.put("4", "....-");
		mTextToMorseMap.put("5", ".....");
		mTextToMorseMap.put("6", "-....");
		mTextToMorseMap.put("7", "--...");
		mTextToMorseMap.put("8", "---..");
		mTextToMorseMap.put("9", "----.");
		
		//Punctuation
		mTextToMorseMap.put(".", ".-.-.-");
		mTextToMorseMap.put(",", "--..--");
		mTextToMorseMap.put("?", "..--..");
		mTextToMorseMap.put("'", ".----.");
		mTextToMorseMap.put("!", "-.-.--");
		mTextToMorseMap.put("/", "-..-.");
		mTextToMorseMap.put("(", "-.--.");
		mTextToMorseMap.put(")", "-.--.-");
		mTextToMorseMap.put("&", ".-...");
		mTextToMorseMap.put(":", "---...");
		mTextToMorseMap.put("=", "-...-");
		mTextToMorseMap.put("+", ".-.-.");
		mTextToMorseMap.put("-", "-....-");
		mTextToMorseMap.put("_", "..--.-");
		mTextToMorseMap.put("\"", ".-..-.");
		mTextToMorseMap.put("$", "...-..-");
		mTextToMorseMap.put("@", ".--.-.");
	}
	
	private void initializeMorseToText()
	{
		mMorseToTextMap = new HashMap<String, String>();
		
		//Letters
		mMorseToTextMap.put(".-", "A");
		mMorseToTextMap.put("-...", "B");
		mMorseToTextMap.put("-.-.", "C");
		mMorseToTextMap.put("-..", "D");
		mMorseToTextMap.put(".", "E");
		mMorseToTextMap.put("..-.", "F");
		mMorseToTextMap.put("--.", "G");
		mMorseToTextMap.put("....", "H");
		mMorseToTextMap.put("..", "I");
		mMorseToTextMap.put(".---", "J");
		mMorseToTextMap.put("-.-", "K");
		mMorseToTextMap.put(".-..", "L");
		mMorseToTextMap.put("--", "M");
		mMorseToTextMap.put("-.", "N");
		mMorseToTextMap.put("---", "O");
		mMorseToTextMap.put(".--.", "P");
		mMorseToTextMap.put("--.-", "Q");
		mMorseToTextMap.put(".-.", "R");
		mMorseToTextMap.put("...", "S");
		mMorseToTextMap.put("-", "T");
		mMorseToTextMap.put("..-", "U");
		mMorseToTextMap.put("...-", "V");
		mMorseToTextMap.put(".--", "W");
		mMorseToTextMap.put("-..-", "X");
		mMorseToTextMap.put("-.--", "Y");
		mMorseToTextMap.put("--..", "Z");
		mMorseToTextMap.put("", " ");
		
		//Numbers
		mMorseToTextMap.put("-----", "0");
		mMorseToTextMap.put(".----", "1");
		mMorseToTextMap.put("..---", "2");
		mMorseToTextMap.put("...--", "3");
		mMorseToTextMap.put("....-", "4");
		mMorseToTextMap.put(".....", "5");
		mMorseToTextMap.put("-....", "6");
		mMorseToTextMap.put("--...", "7");
		mMorseToTextMap.put("---..", "8");
		mMorseToTextMap.put("----.", "9");
		
		//Punctuation
		mMorseToTextMap.put(".-.-.-", ".");
		mMorseToTextMap.put("--..--", ",");
		mMorseToTextMap.put("..--..", "?");
		mMorseToTextMap.put(".----.", "'");
		mMorseToTextMap.put("-.-.--", "!");
		mMorseToTextMap.put("-..-.", "/");
		mMorseToTextMap.put("-.--.", "(");
		mMorseToTextMap.put("-.--.-", ")");
		mMorseToTextMap.put(".-...", "&");
		mMorseToTextMap.put("---...", ":");
		mMorseToTextMap.put("-...-", "=");
		mMorseToTextMap.put(".-.-.", "+");
		mMorseToTextMap.put("-....-", "-");
		mMorseToTextMap.put("..--.-", "_");
		mMorseToTextMap.put(".-..-.", "\"");
		mMorseToTextMap.put("...-..-", "$");
		mMorseToTextMap.put(".--.-.", "@");
	}
	
	/**
	 * Translates text to Morse string of dots (.) and dashes (_)
	 * 
	 * @param text - Text to be transtationed
	 * @return - String containing translation in . and _
	 */
	public String textToMorse(String text)
	{
		String translation = "";
		int i = 0;
		
		text = text.toUpperCase(Locale.getDefault());
		
		for (i = 0; i < text.length(); i++)
		{
			translation = translation.concat(mTextToMorseMap.get(text.substring(i, i + 1)) + " ");
		}
	
		return translation;
	}
	
	/**
	 * Translates Morse of dots (.) and dashes (_) to text string
	 * 
	 * @param text - Morse text
	 * @return - String containing translated text to English characters
	 */
	public String morseToText(String text)
	{
		String translation = "";
		
		StringTokenizer st = new StringTokenizer(text);
		while (st.hasMoreTokens()) {
			translation = translation.concat(mMorseToTextMap.get(st.nextToken()));
		}
		
		return translation;
	}
}
