/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controleur;

import modele.PointEntreeControleur;
import modele.Livraison;

/**
 *
 * Classe permettant de créer une commande exécutable et annulable pour échanger
 * l'ordre de passage de deux livraisons de la tournée
 */
public class CdeEchanger implements Commande {

    private final PointEntreeControleur facadeControleur;
    private final Livraison livraison1;
    private final Livraison livraison2;

    /**
     * Constructeur
     *
     * @param facadeControleur
     * @param livraison1 une livraison à échanger l'ordre de passage avec la
     * livraison2
     * @param livraison2 une livraison à échanger l'ordre de passage avec la
     * livraison1
     */
    public CdeEchanger(PointEntreeControleur facadeControleur, Livraison livraison1, Livraison livraison2) {

        this.facadeControleur = facadeControleur;
        this.livraison1 = livraison1;
        this.livraison2 = livraison2;
    }

    /**
     * Méthode pour exécuter l'action de la commande
     *
     */
    @Override
    public void doCde() {
        facadeControleur.echangerLivraison(livraison1, livraison2);
    }

    /**
     * Méthode pour annuler l'action de la commande
     *
     */
    @Override
    public void undoCde() {
        facadeControleur.echangerLivraison(livraison1, livraison2);
    }

}
