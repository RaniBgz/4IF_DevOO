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
 * Etat dans lequel se trouve le controleur dans le UC "Ajouter une livraison" 
 * après que l'utilisateur ait cliqué sur la livraison après laquelle il souhaite rajouter une livraison
 * @author Administrateur
 */
public class EtatAttenteAjout extends EtatDefaut {

    private PointDePassage livraisonPrecedente;

    /**
     * setter
     * @param livraisonPrecedente l'intersection à laquelle on va rajouter la livraison
     */
    public void setLivraisonPrecedente(PointDePassage livraisonPrecedente) {
        this.livraisonPrecedente = livraisonPrecedente;
    }

    /**
     * Réponse à l'événement "souris bougée" permettant de mettre en valeur les intersections potentielles où rajouter une livraison
     * @param fenetre
     * @param p la position de la souris
     * @param ligneTableau l'index de la ligne du tableau de la vue textuelle
     */
    @Override
    public void sourisBougee(Fenetre fenetre, Point p, int ligneTableau) {
        if (ligneTableau == -2) {
            Livraison livraison = fenetre.facadeVue.isLivraison(p, VueGraphique.DIAMETRE_DETECTION);
            if (livraison == null) {
                Intersection inter = fenetre.facadeVue.isIntersection(p, VueGraphique.DIAMETRE_DETECTION);
                if (inter == fenetre.facadeVue.getTournee().getEntrepot().getIntersection()) {
                    return;
                }
                if (inter != null) {
                    fenetre.facadeVue.notifyChanged(inter);
                    fenetre.afficheMessage("Ajout d'une livraison sur l'intersection : id=" + inter.getId() + " X=" + inter.getX() + 
                            " Y=" + inter.getY() + " ? [Clic gauche] sur l'intersection pour confirmer [Clic droit] pour annuler.");
                }
            }
        }
    }

    /**
     * Réponse à l'événement "clic gauche" permettant de rajouter une livraison à l'intersection sélectionnée
     * @param fenetre
     * @param facadeControleur
     * @param p la position de la souris
     * @param ligneTableau l'index de la ligne sélectionnée du tableau de la vue textuelle
     * @param listeDeCommandes 
     */
    @Override
    public void clicGauche(Fenetre fenetre, PointEntreeControleur facadeControleur, Point p, int ligneTableau, ListeDeCdes listeDeCommandes) {
        if (ligneTableau == -2) {
            Livraison livraison = fenetre.facadeVue.isLivraison(p, VueGraphique.DIAMETRE_DETECTION);
            if (livraison == null) {
                Intersection inter = fenetre.facadeVue.isIntersection(p, VueGraphique.DIAMETRE_DETECTION);
                if (inter == fenetre.facadeVue.getTournee().getEntrepot().getIntersection()) {
                    return;
                }
                if (inter != null) {

                    int client = -1;
                    do {
                        String reponse = fenetre.afficherPopUpQuestion("Quel est le numéro du client ?", "Ajout d'une livraison");
                        if (reponse == null) {
                            this.clicDroit(fenetre, listeDeCommandes);
                            return;
                        }
                        try {
                            client = Integer.parseInt(reponse);
                        } catch (NumberFormatException e) {
                            client = -1;
                            fenetre.afficherPopUpErreur("Veuillez rentrer un nombre.", "Mauvaise saisie");
                        }
                    } while (client == -1);
                    Controleur.setEtatCourant(Controleur.etatTourneeCalculee);
                    Controleur.etatTourneeCalculee.doAjouterLivraison(fenetre, facadeControleur, livraisonPrecedente, inter, client, listeDeCommandes);
                }
            }
        }
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
        fenetre.afficheMessage("");
        fenetre.enabledBouton(true);
        fenetre.enabledMenuChargement(true);
    }
}
