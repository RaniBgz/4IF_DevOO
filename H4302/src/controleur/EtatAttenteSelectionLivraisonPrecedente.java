/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controleur;

import java.awt.Point;
import modele.PointEntreeControleur;
import modele.Intersection;
import modele.Livraison;
import modele.PointDePassage;
import vue.Fenetre;
import vue.VueGraphique;

/**
 * Etat dans lequel se trouve le controleur dans le UC "Ajouter une livraison" après avoir cliqué sur le bouton "Ajouter" 
 * @author Administrateur
 */
public class EtatAttenteSelectionLivraisonPrecedente extends EtatDefaut {

    /**
     * Réponse à l'événement "souris bougée" permettant de mettre en valeur les intersections
     * @param fenetre
     * @param p la position de la souris
     * @param ligneTableau l'index de la ligne sélectionnée du tableau de la vue txtuelle
     */
    @Override
    public void sourisBougee(Fenetre fenetre, Point p, int ligneTableau) {
        if (ligneTableau == -2) {
            Livraison livraison = fenetre.facadeVue.isLivraison(p, VueGraphique.DIAMETRE_DETECTION);
            if (livraison != null) {
                fenetre.facadeVue.notifyChanged(livraison);
                fenetre.afficheMessage("Ajout d'une livraison après la livraison : id=" + livraison.getIdGenere() + " "
                        + "Lieu : X=" + livraison.getIntersection().getX() + " Y=" + livraison.getIntersection().getY()
                        + " ? [Clic gauche] sur la livraison pour confirmer [Clic droit] pour annuler.");
            }
            Intersection inter = fenetre.facadeVue.isEntrepot(p, VueGraphique.DIAMETRE_DETECTION);
            if (inter != null) {
                fenetre.facadeVue.notifyChanged(inter);
                fenetre.afficheMessage("Ajout d'une livraison après l'entrepôt : id=" + inter.getId() + " "
                        + " X=" + inter.getX() + " Y=" + inter.getY()
                        + " ? [Clic gauche] sur l'entrepot pour confirmer [Clic droit] pour annuler.");
            }

        } else {
            Livraison livraison = fenetre.facadeVue.getLivraison(ligneTableau);
            fenetre.facadeVue.notifyChanged(livraison);
            fenetre.afficheMessage("Ajout d'une livraison après la livraison : id=" + livraison.getIdGenere() + " "
                    + "Lieu : X=" + livraison.getIntersection().getX() + " Y=" + livraison.getIntersection().getY()
                    + " ? [Clic gauche] sur la livraison pour confirmer [Clic droit] pour annuler.");
        }
    }

    /**
     * Réponse à l'événement "clic gauche" permettant de sélectionner la livraison après laquelle on ajoutera une livraison
     * @param fenetre
     * @param facadeControleur
     * @param p la position de la souris
     * @param ligneTableau l'index de la ligne sélectionnée du tableau de la vue textuelle
     * @param listeDeCommande 
     */
    @Override
    public void clicGauche(Fenetre fenetre, PointEntreeControleur facadeControleur, Point p, int ligneTableau, ListeDeCdes listeDeCommande) {
        if (ligneTableau == -2) {
            Livraison livraison = fenetre.facadeVue.isLivraison(p, VueGraphique.DIAMETRE_DETECTION);
            if (livraison != null) {
                fenetre.facadeVue.notifyChanged(livraison);
                valider(fenetre, "Ajout d'une livraison après la livraison : id=" + livraison.getIdGenere() + " "
                        + "Lieu : X=" + livraison.getIntersection().getX() + " Y=" + livraison.getIntersection().getY()
                        + ". [Clic gauche] sur une intersection pour créer la livraison [Clic droit] pour annuler.", livraison);
            }
            Intersection inter = fenetre.facadeVue.isEntrepot(p, VueGraphique.DIAMETRE_DETECTION);
            if (inter != null) {
                fenetre.facadeVue.notifyChanged(inter);
                valider(fenetre, "Ajout d'une livraison après l'entrepôt : id=" + inter.getId() + " "
                        + " X=" + inter.getX() + " Y=" + inter.getY()
                        + ". [Clic gauche] sur une intersection pour créer la livraison [Clic droit] pour annuler.", fenetre.facadeVue.getTournee().getEntrepot());
            }
        } else {
            Livraison livraison = fenetre.facadeVue.getLivraison(ligneTableau);
            fenetre.facadeVue.notifyChanged(livraison);
            valider(fenetre, "Ajout d'une livraison après la livraison : id=" + livraison.getIdGenere() + " "
                    + "Lieu : X=" + livraison.getIntersection().getX() + " Y=" + livraison.getIntersection().getY()
                    + ". [Clic gauche] sur une intersection pour créer la livraison [Clic droit] pour annuler.", livraison);
        }
    }

    /**
     * Méthode permettant de passer à l'état suivant pour sélectionner l'intersection où nous rajouterons la livraison
     * @param fenetre
     * @param message le message à afficher à l'utilisateur
     * @param livraison la livraison après laquelle on rajoutera la livraison
     */
    private void valider(Fenetre fenetre, String message, PointDePassage livraison) {
        fenetre.afficheMessage(message);
        Controleur.etatAttenteAjout.setLivraisonPrecedente(livraison);
        Controleur.setEtatCourant(Controleur.etatAttenteAjout);
    }
    
    /**
     * Réponse à l'évènement "Clic droit" pour annuler l'oppération en cours
     * 
     * @param fenetre
     * @param listeDeCdes 
     */
    @Override
    public void clicDroit(Fenetre fenetre, ListeDeCdes listeDeCdes) {
        Controleur.setEtatCourant(Controleur.etatTourneeCalculee);
        fenetre.afficheMessage("   ");
        fenetre.enabledBouton(true);
        fenetre.enabledMenuChargement(true);
    }
}
