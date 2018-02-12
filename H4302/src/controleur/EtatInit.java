package controleur;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import modele.PointEntreeControleur;
import modele.Plan;
import vue.Fenetre;
import xml.ParseurPlan;
import xml.XmlNonValideException;

/**
 * Etat dans lequel se trouve le controleur au lancement de l'application, avant
 * de charger un plan
 *
 */
public class EtatInit extends EtatDefaut {

    @Override
    public void chargerPlan(Fenetre fenetre, PointEntreeControleur facadeControleur, ListeDeCdes listeDeCommande) {
        listeDeCommande.reset();
        Plan plan = null;
        fenetre.enabledBouton(false);
        fenetre.enabledMenuCalculerTournee(false);
        fenetre.enabledMenuChargerDemande(false);
        try {
            plan = new Plan();
            ParseurPlan.chargerPlan(plan);
            facadeControleur.setPlan(plan);
            fenetre.enabledMenuChargerDemande(true);
            Controleur.setEtatCourant(Controleur.etatPlanCharge);
            return;
        } catch (ParserConfigurationException | SAXException ex) {
            fenetre.afficherPopUpErreur("Le fichier XML est mal formé.", "Erreur-XML-0");
        } catch (IOException ex) {
            fenetre.afficherPopUpErreur("Ce fichier n'existe pas.", "Erreur-XML-0");
        } catch (XmlNonValideException ex) {
            String message = ex.getMessage().substring(ex.getMessage().indexOf('*') + 1);
            String numError = ex.getMessage().substring(0, ex.getMessage().indexOf('*'));
            fenetre.afficherPopUpErreur(message + " Veuillez corriger puis recharger le fichier XML.", "Erreur-XML-" + numError);
        } 
        facadeControleur.setPlan(null);
        fenetre.enabledMenuChargerDemande(false);
    }
   
}
