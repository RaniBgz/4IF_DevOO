/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controleur;

import java.awt.Point;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import modele.DemandeLivraisons;
import modele.PointEntreeControleur;
import modele.Intersection;
import modele.Livraison;
import modele.Plan;
import vue.Fenetre;
import vue.VueGraphique;
import xml.ParseurDemande;
import xml.XmlNonValideException;

/**
 * Etat du controleur après avoir chargé un plan et en attente du chargement
 * d'une demande de livraisons
 *
 * @author Administrateur
 */
public class EtatPlanCharge extends EtatDefaut {

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
        Intersection inter = fenetre.facadeVue.isIntersection(p, VueGraphique.DIAMETRE_DETECTION);
        if (inter != null) {
            fenetre.facadeVue.notifyChanged(inter);
            fenetre.afficheMessage("Intersection : id=" + inter.getId() + " X=" + inter.getX() + " Y=" + inter.getY());
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
        listeDeCommande.reset();
        DemandeLivraisons demande = null;
        fenetre.enabledBouton(false);
        fenetre.enabledMenuCalculerTournee(false);
        try {
            Livraison.resetIdGenerateur();
            demande = new DemandeLivraisons();
            Plan plan = facadeControleur.getPlan();
            if (plan == null) {
            	fenetre.afficherPopUpErreur("Veuillez charger un plan avant de charger une demande de livraison.", "Plan non chargé");
            } else {
            	ParseurDemande.chargerDemande(demande, plan);
            	facadeControleur.setDemandeLivraisons(demande);
            	Controleur.setEtatCourant(Controleur.etatDemandeChargee);
                fenetre.enabledMenuCalculerTournee(true);
            }
        } catch (ParserConfigurationException | SAXException ex) {
        	facadeControleur.setDemandeLivraisons(null);
            fenetre.afficherPopUpErreur("Le fichier XML est mal formé.", "Erreur-XML-0");
        } catch (IOException ex) {
        	facadeControleur.setDemandeLivraisons(null);
            fenetre.afficherPopUpErreur("Ce fichier n'existe pas.", "Erreur-XML-0");
        } catch (XmlNonValideException ex) {
        	facadeControleur.setDemandeLivraisons(null);
            String message = ex.getMessage().substring(ex.getMessage().indexOf('*') + 1);
            String numError = ex.getMessage().substring(0, ex.getMessage().indexOf('*'));
            fenetre.afficherPopUpErreur(message + " Veuillez corriger puis recharger le fichier XML.", "Erreur-XML-" + numError);
        } 
    }
}
