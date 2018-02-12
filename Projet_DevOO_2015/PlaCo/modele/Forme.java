package modele;

import java.util.Observable;

public abstract class Forme extends Observable {
	
	private boolean estSelectionne;
	
	public boolean getEstSelectionne(){
		return estSelectionne;
	}
	
	public void setEstSelectionne(boolean b) {
		estSelectionne = b;
		setChanged();
		notifyObservers(this);
	}
	
	public Forme(){
		estSelectionne = false;
	}
	
	/**
	 * Determine si le point p est contenu dans this
	 * @param p 
	 * @return true si p appartient a this, false sinon
	 */
	public abstract boolean contient(Point p);

	/**
	 * Determine s'il est possible de deplacer this de (deltaX,deltaY) de telle sorte qu'il soit toujours
	 * entierement inclus dans plan, et qu'il soit disjoint de toutes les formes de plan
	 * @param deltaX
	 * @param deltaY
	 * @param plan
	 */
	public abstract boolean deplacementPossible(int deltaX, int deltaY, Plan plan);

	/**
	 * Deplace this de (deltaX,deltaY)
	 * Precondition : deplacementPossible(deltaX, deltaY, plan) == true
	 * @param deltaX
	 * @param deltaY
	 */
	public abstract void deplace(int deltaX, int deltaY);
	
	/**
	 * Determine si la forme est entierement contenue dans le rectangle defini par p1 et p2
	 * @param p1
	 * @param p2
	 * @return true si this est entierement contenue dans le rectangle defini par p1 et p2
	 */
	public abstract boolean contenuDans(Point p1, Point p2);
	
	/**
	 * Determine si la forme est disjointe de f
	 * @param f
	 * @return true si this est disjointe de f
	 */
	public abstract boolean disjoint(Forme f);
	
	public abstract void accepte(VisiteurDeFormes v);
}
