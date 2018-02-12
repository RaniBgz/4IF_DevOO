package controleur;


import vue.Fenetre;
import modele.Plan;
import modele.Point;

public abstract class EtatDefaut implements Etat{	
	// Definition des comportements par defaut des methodes
	public void ajouterCercle(Fenetre fenetre){}
	public void ajouterRectangle(Fenetre fenetre){}
	public void supprimer(Fenetre fenetre){}
	public void deplacer(Fenetre fenetre){}
	public void diminuerEchelle(Fenetre fenetre) {}
	public void augmenterEchelle(Fenetre fenetre) {}
	public void undo(ListeDeCdes listeDeCdes){}
	public void redo(ListeDeCdes listeDeCdes) {}
	public void clicGauche(Fenetre fenetre, Plan plan, ListeDeCdes listeDeCdes, Point p) {}
	public void sourisBougee(Plan plan, Point p){}
	public void carSaisi(Plan plan, ListeDeCdes listeDeCdes, int codeCar) {}
	public void clicDroit(Fenetre fenetre, ListeDeCdes listeDeCdes) {
		fenetre.autoriseBoutons(true);
		Controleur.setEtatCourant(Controleur.etatInit);
		fenetre.afficheMessage("");
	}
	public void sauver(Plan plan, Fenetre fenetre){};
	public void ouvrir(Plan plan, ListeDeCdes listeDeCdes, Fenetre fenetre){}
}
