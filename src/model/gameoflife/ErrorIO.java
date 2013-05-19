/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.gameoflife;

/**
 * Exxeption qui permettant de remonté à l'utilisateur les erreurs lié aux fichiers.
 * @author Quentin
 */
public class ErrorIO {
	private String errorText;

	public String getErrorText() {
		return errorText;
	}

	public ErrorIO(String errorText) {
		this.errorText = errorText;
	}
	
}
