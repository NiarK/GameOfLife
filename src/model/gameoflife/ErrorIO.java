/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.gameoflife;

/**
 * Classe d'erreur qui permet de remonter les erreurs vers l'utilisateur
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
