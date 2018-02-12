/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controleur;

import java.awt.Point;
import modele.PointEntreeControleur;
import vue.Fenetre;

/**
 * Classe centrale de l'application du pattern MVC
 *
 * @author baptiste
 */
public class Controleur {

    private final Fenetre fenetre;
    private final PointEntreeControleur facadeControleur;
    private final ListeDeCdes listeDeCommandes;
    private static Etat etatCourant;
// Instances associees a chaque etat possible du controleur
    protected static final EtatInit etatInit = new EtatInit();
    protected static final EtatPlanCharge etatPlanCharge = new EtatPlanCharge(); 
    protected static final EtatDemandeChargee etatDemandeChargee = new EtatDemandeChargee();
    protected static final EtatTourneeCalculee etatTourneeCalculee = new EtatTourneeCalculee();
    protected static final EtatAttenteSelectionLivraisonPrecedente etatAttenteSelectionLivraisonPrecedente = new EtatAttenteSelectionLivraisonPrecedente();
    protected static final EtatAttenteAjout etatAttenteAjout = new EtatAttenteAjout();
    protected static final EtatAttenteSuppression etatAttenteSuppression = new EtatAttenteSuppression();
    protected static final EtatAttenteClicPremiereLivraison etatAttenteClicPremiereLivraison = new EtatAttenteClicPremiereLivraison();
    protected static final EtatAttenteClicDeuxiemeLivraison etatAttenteClicDeuxiemeLivraison = new EtatAttenteClicDeuxiemeLivraison();

    /**
     * Constructeur
     *
     * @param fenetre la fenêtre graphique de l'application
     */
    public Controleur(Fenetre fenetre) {
        this.fenetre = fenetre;
        this.etatCourant = etatInit;
        this.facadeControleur = new PointEntreeControleur(fenetre.facadeVue);
        this.listeDeCommandes = new ListeDeCdes();
    }

    /**
     * Capte l'événement "clic sur le bouton chargerPlan" et appelle le
     * traitement à faire correspondant à l'état du controleur
     */
    public void chargerPlan() {
        etatCourant.chargerPlan(fenetre, facadeControleur, listeDeCommandes);
    }

    /**
     * Capte l'événement "clic sur le bouton charger Demande" et appelle le
     * traitement à faire correspondant à l'état du controleur
     */
    public void chargerDemande() {
        etatCourant.chargerDemande(fenetre, facadeControleur, listeDeCommandes);
    }

    /**
     * Capte l'événement "souris bougée" et appelle le traitement à faire
     * correspondant à l'état du controleur
     *
     * @param p le point de la position de la souris
     * @param ligneTableau la ligne sélectionnée de la vue textuelle
     */
    public void sourisBougee(Point p, int ligneTableau) {
        etatCourant.sourisBougee(this.fenetre, p, ligneTableau);
    }

    /**
     * Capte l'événement "clic gauche" et appelle le traitement à faire
     * correspondant à l'état du controleur
     *
     * @param p le point de la position de la souris
     * @param ligneTableau la ligne sélectionnée de la vue textuelle
     */
    public void clicGauche(Point p, int ligneTableau) {
        etatCourant.clicGauche(this.fenetre, this.facadeControleur, p, ligneTableau, listeDeCommandes);
    }

    /**
     * Capte l'événement "clic droit" et appelle le traitement à faire
     * correspondant à l'état du controleur
     */
    public void clicDroit() {
        etatCourant.clicDroit(fenetre, listeDeCommandes);
    }

    /**
     * Capte l'événement "undo" et appelle le traitement à faire correspondant à
     * l'état du controleur
     */
    public void undo() {
        etatCourant.undo(fenetre, listeDeCommandes);
    }

    /**
     * Capte l'événement "redo" et appelle le traitement à faire correspondant à
     * l'état du controleur
     */
    public void redo() {
        etatCourant.redo(fenetre, listeDeCommandes);

    }

    /**
     * Capte l'événement "clic sur le bouton Ajouter" et appelle le traitement à
     * faire correspondant à l'état du controleur
     */
    public void ajouterLivraison() {
        etatCourant.ajouterLivraison(fenetre);
    }

    /**
     * Capte l'événement "clic sur le bouton Supprimer" et appelle le traitement
     * à faire correspondant à l'état du controleur
     */
    public void supprimerLivraison() {
        etatCourant.supprimerLivraison(fenetre);
    }

    /**
     * Capte l'événement "clic sur le bouton Echanger" et appelle le traitement
     * à faire correspondant à l'état du controleur
     */
    public void echangerLivraison() {
        etatCourant.echangerLivraison(fenetre);
    }

    /**
     * Capte l'événement "clic sur le menu Générer la feuille de route" et
     * appelle le traitement à faire correspondant à l'état du controleur
     */
    public void genererFeuilleDeRoute() {
        etatCourant.genererFeuilleDeRoute(fenetre, facadeControleur);
    }

    /**
     * setter
     *
     * @param etatCourant le nouvel état du controleur
     */
    protected static void setEtatCourant(Etat etatCourant) {
        Controleur.etatCourant = etatCourant;
    }

    /**
     * Capte l'événement "clic sur le menu Colculer Tournée" et
     * appelle le traitement à faire correspondant à l'état du controleur
     */
    public void calculerTournee() {
        etatCourant.calculerTournee(fenetre, facadeControleur);
    }

}
