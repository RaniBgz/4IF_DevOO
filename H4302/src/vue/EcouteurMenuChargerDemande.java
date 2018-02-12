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
 *  Ecouteur du menu charger une demande de livraison
 */
public class EcouteurMenuChargerDemande extends EcouteurMenu {

    /**
     * Surcharge du constructeur
     *
     * @param controleur
     */
    public EcouteurMenuChargerDemande(Controleur controleur) {
        super(controleur);
    }

    /**
     * Méthode de traitement du clic sur le menu ou au raccourcis clavier
     *
     * @param e Evènement reçu
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        controleur.chargerDemande();
    }

}
