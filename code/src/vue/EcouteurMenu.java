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
 *  Ecouteur de menu (classe abstraite)
 */
public abstract class EcouteurMenu implements ActionListener {

    protected Controleur controleur;

    /**
     * Constructeur de l'écouteur de bouton
     *
     * @param controleur
     */
    public EcouteurMenu(Controleur controleur) {
        this.controleur = controleur;
    }

}
