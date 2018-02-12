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
import vue.Fenetre;
import vue.VueGraphique;

/**
 * Etat du controleur après avoir chargé un plan et en attente du chargement
 * d'une demande de livraisons
 *
 * @author Administrateur
 */
public class EtatDemandeChargee extends EtatDefaut {

    /**
     * Réponse à l'événement "souris bougée" permettant de mettre en valeur les
     * intersections
     *
     * @param fenetre
     * @param p la position de la souris
     * @param ligneTableau l'index de la ligne sélectionnée du tableau de la vue
     * txtuelle
     */
    @Override
    public void sourisBougee(Fenetre fenetre, Point p, int ligneTableau) {
        if (ligneTableau == -2) {
            Livraison livraison = fenetre.facadeVue.isLivraison(p, VueGraphique.DIAMETRE_DETECTION);
            if (livraison != null) {
                fenetre.facadeVue.notifyChanged(livraison);
                fenetre.afficheMessage("Livraison : id=" + livraison.getIdGenere() + " X=" + livraison.getIntersection().getX() + " Y=" + livraison.getIntersection().getY());
                return;
            }
            Intersection inter = fenetre.facadeVue.isIntersection(p, VueGraphique.DIAMETRE_DETECTION);
            if (inter != null) {
                fenetre.facadeVue.notifyChanged(inter);
                fenetre.afficheMessage("Intersection : id=" + inter.getId() + " X=" + inter.getX() + " Y=" + inter.getY());
            }
        } else {
            Livraison livraison = fenetre.facadeVue.getLivraison(ligneTableau);
            fenetre.facadeVue.notifyChanged(livraison);
            fenetre.afficheMessage("Livraison : id=" + livraison.getIntersection().getId() + " X=" + livraison.getIntersection().getX() + " Y=" + livraison.getIntersection().getY());
        }
    }

    /**
     * Réponse à l'événement "clic sur Charger un plan" permettant de charger un
     * autre plan
     *
     * @param fenetre
     * @param facadeControleur
     * @param listeDeCommande
     */
    @Override
    public void chargerPlan(Fenetre fenetre, PointEntreeControleur facadeControleur, ListeDeCdes listeDeCommande) {
        Controleur.setEtatCourant(Controleur.etatInit);
        Controleur.etatInit.chargerPlan(fenetre, facadeControleur, listeDeCommande);
    }

    /**
     * Réponse à l'événement "clic sur Charger une demande de livraisons"
     * permettant de charger une demande de livraison
     *
     * @param fenetre
     * @param facadeControleur
     * @param listeDeCommande
     */
    @Override
    public void chargerDemande(Fenetre fenetre, PointEntreeControleur facadeControleur, ListeDeCdes listeDeCommande) {
        Controleur.setEtatCourant(Controleur.etatPlanCharge);
        Controleur.etatPlanCharge.chargerDemande(fenetre, facadeControleur, listeDeCommande);
    }

    /**
     * Méthode appelée par le controleur après un clic sur le calcul d'une
     * tournée Précondition : une DemandeLivraisons à été chargée
     *
     * @param fenetre
     * @param facadeControleur
     */
    @Override
    public void calculerTournee(Fenetre fenetre, PointEntreeControleur facadeControleur) {
        facadeControleur.calculerTournee();
        Controleur.setEtatCourant(Controleur.etatTourneeCalculee);
        fenetre.enabledBouton(true);
        fenetre.enabledMenuCalculerTournee(false);
        fenetre.enabledMenuChargement(true);
    }
}
