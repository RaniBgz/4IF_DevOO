/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import controleur.Controleur;
import java.awt.event.ActionListener;

/**
 *
 * Ecouteur de bouton (classe abstraite)
 */
public abstract class EcouteurDeBouton implements ActionListener {
    
    Controleur controleur;

    /**
     * Constructeur de l'écouteur de bouton
     * @param controleur
     */
    public EcouteurDeBouton(Controleur controleur) {
        this.controleur = controleur;
    }
    
    
    
}
