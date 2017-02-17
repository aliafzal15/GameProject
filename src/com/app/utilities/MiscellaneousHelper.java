package com.app.utilities;


import org.apache.commons.codec.binary.Base64;



/**
 * this class is a miscellaneous helper class
 * 
 * @author Sajjad Ashraf
 *
 */
public class MiscellaneousHelper {

	/**
	 * this method removes character from start to left
	 * 
	 * @param InputString
	 *            input string
	 * @param Characters
	 *            characters to be removed
	 * @return string the result
	 */
	public String RemoveCharacterFromStrartorLeft(String InputString, String Characters) {
		return InputString.replaceAll("^\\" + Characters + "+", "");
	}

	/**
	 * the method removes character from end or right
	 * 
	 * @param InputString
	 *            input string
	 * @param Characters
	 *            characters to be removed
	 * @return string the result
	 */
	public String RemoveCharacterFromEndorRight(String InputString, String Characters) {
		return InputString.replaceAll("\\" + Characters + "+$", "");
	}

	/**
	 * this method removes character from both end
	 * 
	 * @param InputString
	 *            input string
	 * @param Characters
	 *            characters to be removed
	 * @return string the result
	 */
	public String RemoveCharacterFromBothEnd(String InputString, String Characters) {
		return InputString.replaceAll("^\\" + Characters + "+|\\" + Characters + "+$", "");
	}

	/**
	 * this method encodes the input to base64
	 * 
	 * @param input
	 *            string
	 * @return returns encoded string
	 */
	public String EncodeBase64(String input) {
		byte[] bytesEncoded = Base64.encodeBase64(input.getBytes());
		return new String(bytesEncoded);

	}

	/**
	 * this method decodes the string from base64 encoding
	 * 
	 * @param input
	 *            string base64
	 * @return string the result
	 */
	public String DecodeBase64(String input) {
		// Decrypt data on other side, by processing encoded data
		byte[] valueDecoded = Base64.decodeBase64(input.getBytes());
		return new String(valueDecoded);

	}
}
