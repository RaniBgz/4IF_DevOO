package controleur;

import java.awt.Point;

import modele.PointEntreeControleur;
import vue.Fenetre;

/**
 * Etat par défaut du controleur implémentant les réponses par défaut à tous les
 * événements utilsateurs de l'application
 *
 * @author COME
 */
public abstract class EtatDefaut implements Etat {

    /**
     * Methode appelee par controleur apres un clic sur le bouton "Ouvrir un
     * plan"
     *
     * @param fenetre
     * @param facadeControleur
     * @param listeDeCommande
     */
    @Override
    public void chargerPlan(Fenetre fenetre, PointEntreeControleur facadeControleur, ListeDeCdes listeDeCommande) {
        oups(fenetre);
    }

    /**
     * Methode appelee par controleur apres un clic sur le bouton "Charger une
     * demande"
     *
     * @param fenetre
     * @param facadeControleur
     * @param listeDeCommande
     *
     */
    @Override
    public void chargerDemande(Fenetre fenetre, PointEntreeControleur facadeControleur, ListeDeCdes listeDeCommande) {
        oups(fenetre);
    }
    
    /**
     * Méthode appelée par le controleur après un clic sur le calcul d'une tournée
     * Précondition : une DemandeLivraisons à été chargée
     * 
     * @param fenetre
     * @param facadeControleur 
     */
    @Override
    public void calculerTournee(Fenetre fenetre, PointEntreeControleur facadeControleur){
        oups(fenetre);
    }

    /**
     * Methode appelee par controleur apres un deplacement de la souris sur la
     * vue graphique du plan Precondition : facadeVue != null
     *
     * @param fenetre
     * @param p
     * @param ligneTableau
     */
    @Override
    public void sourisBougee(Fenetre fenetre, Point p, int ligneTableau) {
        //Ne fait rien
    }

     /**
     * Methode appelee par controleur apres un clic sur le bouton "Undo"
     *
     * @param fenetre
     * @param listeDeCommande
     */
    @Override
    public void undo(Fenetre fenetre, ListeDeCdes listeDeCommande) {
        oups(fenetre);
    }

    /**
     * Methode appelee par controleur apres un clic sur le bouton "Redo"
     *
     * @param fenetre
     * @param listeDeCommande
     */
    @Override
    public void redo(Fenetre fenetre, ListeDeCdes listeDeCommande) {
        oups(fenetre);
    }

    /**
     * Méthode appelée par controleur après un clic sur le bouton "Ajouter"
     *
     * @param fenetre
     */
    @Override
    public void ajouterLivraison(Fenetre fenetre) {
        oups(fenetre);
    }

    /**
     * Méthode appelée par controleur après un clic sur le bouton "Supprimer"
     *
     * @param fenetre
     */
    @Override
    public void supprimerLivraison(Fenetre fenetre) {
        oups(fenetre);
    }

    /**
     * Méthode appelée par controleur après un clic sur le bouton "Echanger"
     *
     * @param fenetre
     */
    @Override
    public void echangerLivraison(Fenetre fenetre) {
        oups(fenetre);
    }

    /**
     * Méthode appelée par controleur après un clic sur le bouton "Generer la
     * feuille de route"
     *
     * @param fenetre
     * @param facadeControleur
     */
    @Override
    public void genererFeuilleDeRoute(Fenetre fenetre, PointEntreeControleur facadeControleur) {
        // oups(fenetre);
    }

    /**
     * Methode appelee par controleur apres un clic gauche sur un point de la
     * vue graphique Precondition : p != null
     *
     * @param fenetre
     * @param facadeControleur
     * @param p
     * @param ligneTableau
     * @param listeDeCommande
     */
    @Override
    public void clicGauche(Fenetre fenetre, PointEntreeControleur facadeControleur, Point p, int ligneTableau, ListeDeCdes listeDeCommande) {
        //Ne fait rien
    }

    /**
     * Methode appelee par controleur apres un clic droit
     *
     * @param fenetre
     * @param listeDeCdes
     */
    @Override
    public void clicDroit(Fenetre fenetre, ListeDeCdes listeDeCdes) {
        //Ne fait rien
    }

    /**
     * Affiche un avertissement à l'utilisateur lui indiquant 
     * qu'il ne peut pas effectuer cette action pour le moment.
     *
     * @param fenetre
     */
    private void oups(Fenetre fenetre) {
        fenetre.afficherPopUpInformation("Vous ne pouvez pas faire cette action pour le moment.", "Ouuuups");
    }
}
