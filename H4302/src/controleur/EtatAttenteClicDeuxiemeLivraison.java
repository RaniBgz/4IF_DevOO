/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controleur;

import java.awt.Point;
import modele.PointEntreeControleur;
import modele.Livraison;
import vue.Fenetre;
import vue.VueGraphique;

/**
 * Etat dans lequel se trouve le controleur dans le UC "Echanger deux
 * Livraisons" après avoir déjà selectionné une première livraison
 *
 * @author COME
 */
public class EtatAttenteClicDeuxiemeLivraison extends EtatDefaut {

    private Livraison livraison1;

    /**
     * setter
     *
     * @param livraison1 la première livraison avec laquelle on échangera
     * l'ordre de passage, récupéré via EtatAttenteClicPremiereLivraison
     */
    public void setLivraison1(Livraison livraison1) {
        this.livraison1 = livraison1;
    }

    /**
     * Réponse à l'événement "souris bougée" permettant de mettre en valeur les
     * intersections
     *
     * @param fenetre
     * @param p la position de la souris
     * @param ligneTableau l'index de la ligne sélectionnée du tableau de la vue
     * textuelle
     */
    @Override
    public void sourisBougee(Fenetre fenetre, Point p, int ligneTableau) {
        if (ligneTableau == -2) {
            Livraison livraison = fenetre.facadeVue.isLivraison(p, VueGraphique.DIAMETRE_DETECTION);
            if (livraison != null) {
                fenetre.facadeVue.notifyChanged(livraison);
                fenetre.afficheMessage("Sélection de la deuxième livaison : id=" + livraison.getIdGenere() + " "
                        + "Lieu : X=" + livraison.getIntersection().getX() + " Y=" + livraison.getIntersection().getY()
                        + " ? [Clic gauche] sur la livraison pour confirmer [Clic droit] pour annuler.");
            }
        } else {
            Livraison livraison = fenetre.facadeVue.getLivraison(ligneTableau);
            fenetre.facadeVue.notifyChanged(livraison);
            fenetre.afficheMessage("Sélection de la deuxième livaison : id=" + livraison.getIdGenere() + " "
                    + "Lieu : X=" + livraison.getIntersection().getX() + " Y=" + livraison.getIntersection().getY()
                    + " ? [Clic gauche] sur la livraison pour confirmer [Clic droit] pour annuler.");
        }
    }

    /**
     * Réponse à l'événement "clic gauche" permettant de sélectionner la
     * deuxième livraison dont on échangera l'ordre de passage dans la tournée
     *
     * @param fenetre
     * @param facadeControleur
     * @param p la position de la souris
     * @param ligneTableau l'index de la ligne sélectionnée du tableau de la vue
     * textuelle
     * @param listeDeCommande
     */
    @Override
    public void clicGauche(Fenetre fenetre, PointEntreeControleur facadeControleur, Point p, int ligneTableau, ListeDeCdes listeDeCommande) {
        if (ligneTableau == -2) {
            Livraison livraison = fenetre.facadeVue.isLivraison(p, VueGraphique.DIAMETRE_DETECTION);
            if (livraison != null) {
                Controleur.setEtatCourant(Controleur.etatTourneeCalculee);
                Controleur.etatTourneeCalculee.doEchangerLivraisons(fenetre, facadeControleur, livraison1, livraison, listeDeCommande);
            }
        } else {
            Livraison livraison = fenetre.facadeVue.getLivraison(ligneTableau);
            if (livraison != null) {
                Controleur.setEtatCourant(Controleur.etatTourneeCalculee);
                Controleur.etatTourneeCalculee.doEchangerLivraisons(fenetre, facadeControleur, livraison1, livraison, listeDeCommande);
            }
        }

    }

    /**
     * Réponse à l'événement "clic droit" permettant d'annuler l'action en cours
     * d'échanger deux livraisons, on retourne à l'état EtatTourneeCalculee
     *
     * @param fenetre
     * @param listeDeCdes
     */
    @Override
    public void clicDroit(Fenetre fenetre, ListeDeCdes listeDeCdes) {
        Controleur.setEtatCourant(Controleur.etatTourneeCalculee);
        fenetre.afficheMessage("  ");
        fenetre.enabledBouton(true);
        fenetre.enabledMenuChargement(true);
    }

}
