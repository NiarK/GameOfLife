/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.gameoflife;

/**
 * Exception permettant de remonté à l'utilisateur les erreurs lié aux fichiers.
 * @author Quentin
 */
public class ErrorIO {
	private String errorText;

	public ErrorIO(String errorText) {
		this.errorText = errorText;
	}

	public String getErrorText() {
		return errorText;
	}
	
}
