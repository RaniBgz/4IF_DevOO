package controleur;

import vue.Fenetre;
import modele.Plan;
import modele.Forme;
import modele.Point;

public class EtatDeplacer1 extends EtatDefaut {
	// Etat atteint a partir de EtatInit a la reception de l'evenement deplacer()
	// -> Attente de la saisie d'un point p permettant de selectionner la forme a deplacer

	@Override
	public void clicGauche(Fenetre fenetre, Plan plan, ListeDeCdes listeDeCdes, Point p) {
		Forme f = plan.cherche(p);
		if (f != null){
			Controleur.etatDeplacer2.actionEntree(f);
			Controleur.setEtatCourant(Controleur.etatDeplacer2);
			fenetre.afficheMessage("Deplacer une forme : [Fleche] pour deplacer la forme selectionnee ; [Clic droit] pour terminer le deplacement");
		}
	}

}
