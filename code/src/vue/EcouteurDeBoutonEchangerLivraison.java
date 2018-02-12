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
 * Ecouteur du bouton pour échanger deux livraisons
 */
public class EcouteurDeBoutonEchangerLivraison extends EcouteurDeBouton {

    /**
     * Surcharge du constructeur
     *
     * @param controleur
     */
    public EcouteurDeBoutonEchangerLivraison(Controleur controleur) {
        super(controleur);
    }

    /**
     * Méthode de traitement du clic sur le bouton
     *
     * @param e Evènement reçu
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        controleur.echangerLivraison();
    }

}
