/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controleur;

import java.awt.Point;
import java.io.IOException;
import modele.PointEntreeControleur;
import modele.Intersection;
import modele.Livraison;
import modele.PointDePassage;
import vue.Fenetre;

/**
 * Etat dans lequel se trouve le controleur dans le UC "Calculer une tournée"
 * après avoir chargé une demande de livraisons ou modifier la tournée
 *
 * @author Administrateur
 */
public class EtatTourneeCalculee extends EtatDefaut {

    /**
     * Réponse à l'événement "clic sur le bouton Générer la feuille de route"
     * permettant de générer un fichier .txt contenant toutes les instructions
     * pour effectuer la tournée
     *
     * @param facadeControleur
     */
    @Override
    public void genererFeuilleDeRoute(Fenetre fenetre, PointEntreeControleur facadeControleur) {
        try {
            facadeControleur.genererFeuilleDeRoute();
        } catch (IOException ex) {
            fenetre.afficherPopPupErreur("Ce nom de fichier n'est pas valide");
        }
    }

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
        Controleur.etatDemandeChargee.sourisBougee(fenetre, p, ligneTableau);
    }

    /**
     * Réponse à l'événement "clic sur le bouton Charger un plan" permettant de
     * charger un nouveau plan
     *
     * @param fenetre
     * @param facadeControleur
     * @param listeDeCommande
     */
    @Override
    public void chargerPlan(Fenetre fenetre, PointEntreeControleur facadeControleur, ListeDeCdes listeDeCommande) {
        Controleur.etatPlanCharge.chargerPlan(fenetre, facadeControleur, listeDeCommande);
    }

    /**
     * Réponse à l'événement "clic sur le bouton Charger une demande de
     * livraisons" permettant de charger une nouvelle demande de livraison
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
     * Réponse à l'événement "clic sur le bouton Ajouter" permettant de passer
     * le controleur dans l'état correspondant pour ajouter une livraison
     *
     * @param fenetre
     */
    @Override
    public void ajouterLivraison(Fenetre fenetre) {
        fenetre.enabledBouton(false);
        fenetre.enabledMenuChargement(false);
        fenetre.afficheMessage("Ajout d'une Livraison : [Clic gauche] sur la livraison qui précèdera la nouvelle livraison [Clic droit] pour annuler.");
        Controleur.setEtatCourant(Controleur.etatAttenteSelectionLivraisonPrecedente);
        fenetre.razSelectionVue();
    }

    /**
     * Réponse à l'événement "clic sur Undo" permettant d'annuler la dernière
     * commande effectuée
     *
     * @param fenetre
     * @param listeDeCommande
     */
    @Override
    public void undo(Fenetre fenetre, ListeDeCdes listeDeCommande) {
        listeDeCommande.undo();
        fenetre.facadeVue.notifyChanged(null);
        fenetre.razSelectionVue();
    }

    /**
     * Réponse à l'événement "clic sur Redo" permettant de refaire la dernière
     * commande annulée
     *
     * @param fenetre
     * @param listeDeCommande
     */
    @Override
    public void redo(Fenetre fenetre, ListeDeCdes listeDeCommande) {
        listeDeCommande.redo();
        fenetre.facadeVue.notifyChanged(null);
        fenetre.razSelectionVue();
    }

    /**
     * Réponse à l'événement "clic sur Supprimer" faisant passer le controleur
     * dans l'état nécessaire pour supprimer une livraison
     *
     * @param fenetre
     */
    @Override
    public void supprimerLivraison(Fenetre fenetre) {
        fenetre.enabledBouton(false);
        fenetre.enabledMenuChargement(false);
        fenetre.afficheMessage("Suppression d'une Livraison : [Clic gauche] sur la livraison [Clic droit] pour annuler.");
        Controleur.setEtatCourant(Controleur.etatAttenteSuppression);
        fenetre.razSelectionVue();
    }

    /**
     * Méthode exécutant la commande de suppression d'une livraison
     *
     * @param fenetre
     * @param facadeControleur
     * @param livraison la livraison à supprimer de la tournée
     * @param listeDeCommande
     */
    void doSupprimerLivraison(Fenetre fenetre, PointEntreeControleur facadeControleur, Livraison livraison, ListeDeCdes listeDeCommande) {
        listeDeCommande.ajoute(new CdeSuppr(facadeControleur, livraison));
        fenetre.enabledBouton(true);
        fenetre.enabledMenuChargement(true);
        fenetre.facadeVue.notifyChanged(null);
        fenetre.razSelectionVue();
    }

    /**
     * Méthode exécutant la commande d'ajout d'une livraison à la tournée
     *
     * @param fenetre
     * @param facadeControleur
     * @param livraisonPrecedente
     * @param inter
     * @param client
     * @param listeDeCommande
     */
    void doAjouterLivraison(Fenetre fenetre, PointEntreeControleur facadeControleur, PointDePassage livraisonPrecedente, Intersection inter, int client, ListeDeCdes listeDeCommande) {
        listeDeCommande.ajoute(new CdeAjout(facadeControleur, livraisonPrecedente, inter, client));
        fenetre.afficheMessage("Votre Livraison a été ajoutée avec succès");
        fenetre.enabledBouton(true);
        fenetre.enabledMenuChargement(true);
        fenetre.facadeVue.notifyChanged(null);
        fenetre.razSelectionVue();
    }

    /**
     * Réponse à l'événement "clic sur le bouton Echanger" faisant passer le
     * controleur dans le bon état pour lancer la procédure du UC "Echanger deux
     * livraisons"
     *
     * @param fenetre
     */
    @Override
    public void echangerLivraison(Fenetre fenetre) {
        fenetre.enabledBouton(false);
        fenetre.enabledMenuChargement(false);
        fenetre.afficheMessage("Echange de deux livraisons : [Clic gauche] sur la première livraison à échanger. [Clic droit] pour annuler la commande.");
        Controleur.setEtatCourant(Controleur.etatAttenteClicPremiereLivraison);
        fenetre.razSelectionVue();
    }

    /**
     * Méthode exécutant la commande d'échanger deux livraisons
     *
     * @param fenetre
     * @param facadeControleur
     * @param livraison1 une des deux livraisons à échanger l'ordre de passage
     * dans la tournée
     * @param livraison2 une des deux livraisons à échanger l'ordre de passage
     * dans la tournée
     * @param listeDeCommande
     */
    void doEchangerLivraisons(Fenetre fenetre, PointEntreeControleur facadeControleur, Livraison livraison1, Livraison livraison2, ListeDeCdes listeDeCommande) {
        listeDeCommande.ajoute(new CdeEchanger(facadeControleur, livraison1, livraison2));
        fenetre.enabledBouton(true);
        fenetre.enabledMenuChargement(true);
        fenetre.facadeVue.notifyChanged(null);
        fenetre.razSelectionVue();
    }

}
