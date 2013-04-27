/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

/**
 *
 * @author pierre
 */
public class BadRuleNameException extends Exception {

	/**
	 * Creates a new instance of
	 * <code>BadRuleNameException</code> without detail message.
	 */
	public BadRuleNameException() {
	}

	/**
	 * Constructs an instance of
	 * <code>BadRuleNameException</code> with the specified detail message.
	 *
	 * @param msg the detail message.
	 */
	public BadRuleNameException(String msg) {
		super(msg);
	}
}
