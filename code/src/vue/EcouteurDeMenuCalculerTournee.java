/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import controleur.Controleur;
import java.awt.event.ActionEvent;

/**
 *
 * Ecouteur du menu pour calculer la tournée
 */
public class EcouteurDeMenuCalculerTournee extends EcouteurMenu {

    /**
     * Surcharge du constructeur
     *
     * @param controleur
     */
    public EcouteurDeMenuCalculerTournee(Controleur controleur) {
        super(controleur);
    }

    /**
     * Méthode de traitement du clic sur le bouton
     *
     * @param e Evènement reçu
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        controleur.calculerTournee();
    }

}
