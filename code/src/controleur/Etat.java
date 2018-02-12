package controleur;

import java.awt.Point;
import modele.PointEntreeControleur;
import vue.Fenetre;

/**
 *
 * Interface implémentée par tous les états du contrôleur
 */
public interface Etat {

    /**
     * Methode appelee par controleur apres un clic sur le bouton "Undo"
     *
     * @param fenetre
     * @param listeDeCommande
     */
    public void undo(Fenetre fenetre, ListeDeCdes listeDeCommande);

    /**
     * Methode appelee par controleur apres un clic sur le bouton "Redo"
     *
     * @param fenetre
     * @param listeDeCommande
     */
    public void redo(Fenetre fenetre, ListeDeCdes listeDeCommande);

    /**
     * Methode appelee par controleur apres un clic sur le bouton "Ouvrir un
     * plan"
     *
     * @param fenetre
     * @param facadeControleur
     * @param listeDeCommande
     */
    public void chargerPlan(Fenetre fenetre, PointEntreeControleur facadeControleur, ListeDeCdes listeDeCommande);

    /**
     * Methode appelee par controleur apres un clic sur le bouton "Charger une
     * demande"
     *
     * @param fenetre
     * @param facadeControleur
     * @param listeDeCommande
     */
    public void chargerDemande(Fenetre fenetre, PointEntreeControleur facadeControleur, ListeDeCdes listeDeCommande);

    /**
     * Méthode appelée par le controleur après un clic sur le calcul d'une tournée
     * Précondition : une DemandeLivraisons à été chargée
     * 
     * @param fenetre
     * @param facadeControleur 
     */
    public void calculerTournee(Fenetre fenetre, PointEntreeControleur facadeControleur);
    
    
    /**
     * Methode appelee par controleur apres un deplacement de la souris sur la
     * vue graphique du plan Precondition : facadeVue != null
     *
     * @param fenetre
     * @param p
     * @param ligneTableau
     */
    public void sourisBougee(Fenetre fenetre, Point p, int ligneTableau);

    /**
     * Méthode appelée par controleur après un clic sur le bouton "Ajouter"
     *
     * @param fenetre
     */
    public void ajouterLivraison(Fenetre fenetre);

    /**
     * Méthode appelée par controleur après un clic sur le bouton "Supprimer"
     *
     * @param fenetre
     */
    public void supprimerLivraison(Fenetre fenetre);

    /**
     * Méthode appelée par controleur après un clic sur le bouton "Echanger"
     *
     * @param fenetre
     */
    public void echangerLivraison(Fenetre fenetre);

    /**
     * Méthode appelée par controleur après un clic sur le bouton "Generer la
     * feuille de route"
     *
     * @param fenetre
     * @param facadeControleur
     */
     
    public void genererFeuilleDeRoute(Fenetre fenetre, PointEntreeControleur facadeControleur) ;

    /**
     * Methode appelee par controleur apres la saisie d'un caractere au clavier
     *
     * @param plan
     * @param listeDeCdes
     * @param codeCar le code ASCII du caractere saisi
     */
	//public void carSaisi(Plan plan, ListeDeCdes listeDeCdes, int codeCar);
    /**
     * Methode appelee par controleur apres un clic droit
     *
     * @param fenetre
     * @param listeDeCdes
     */
    public void clicDroit(Fenetre fenetre, ListeDeCdes listeDeCdes);

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
    public void clicGauche(Fenetre fenetre, PointEntreeControleur facadeControleur, Point p, int ligneTableau, ListeDeCdes listeDeCommande);
}
