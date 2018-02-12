package modele;

public class Cercle extends Forme{
	private Point centre;
	private int rayon;
	
	/**
	 * Cree un cercle de centre c et de rayon r
	 * @param c
	 * @param r
	 */
	public Cercle(Point c, int r){
		super();
		this.rayon = r;
		this.centre = c;
	}
	
	public Point getCentre() {
		return centre;
	}
	
	public int getRayon() {
		return rayon;
	}
	
	@Override
	public boolean contient(Point p) {
		return centre.distance(p) <= rayon;
	}
	
	@Override
	public void deplace(int x, int y) {
		centre = centre.deplace(x, y);
		setChanged();
		notifyObservers();
	}
	
	@Override
	public boolean contenuDans(Point p1, Point p2) {
		Point pMin = new Point(centre.getX()-rayon,centre.getY()-rayon);
		Point pMax = new Point(centre.getX()+rayon,centre.getY()+rayon);
		if ((pMin != null) && (pMax != null))
			return pMin.contenuDans(p1, p2) && pMax.contenuDans(p1, p2);
		return false;
	}
	
	/**
	 * Met a jour le rayon de this en fonction d'un point p appartenant a son perimetre, 
	 * de telle sorte que this soit entierement inclus dans planche, et soit disjoint
	 * de toutes les formes de planche.
	 * @param p un point appartenant au perimetre de this
	 */
	public void miseAJourTaille(Point p, Plan plan) {
		int sauveRayon = rayon;
		rayon = (int) Math.sqrt((p.getX()-centre.getX())*(p.getX()-centre.getX())+
				(p.getY()-centre.getY())*(p.getY()-centre.getY()));
		if ((!plan.tousDisjoints(this)) || (!plan.contient(this))){
			rayon = sauveRayon;
		} else {
			setChanged();
			notifyObservers();
		}
	}
	
	@Override
	public boolean disjoint(Forme f) {
		if (f instanceof Cercle)
			return disjoint((Cercle)f);
		else
			return disjoint((Rectangle)f);
	}
	
	private boolean disjoint(Rectangle r){
		return r.disjoint(this);
	}
	
	private boolean disjoint(Cercle cercle){
		int a = Math.abs(cercle.getRayon()+1-rayon) ;
		a *= a;
		int b = cercle.getCentre().getX()-centre.getX();
		b *= b;
		int c = cercle.getCentre().getY()-centre.getY();
		c *= c;
		int d = Math.abs(cercle.getRayon()+1+rayon);
		d *= d;
		return (a >= b+c) || (b+c >= d);
	}

	@Override
	public boolean deplacementPossible(int x, int y, Plan planche) {
		Point nouveauCentre = centre.deplace(x, y);
		if (nouveauCentre == null) return false;
		centre = nouveauCentre;
		boolean b = planche.contient(this) && planche.tousDisjoints(this);
		centre = nouveauCentre.deplace(-x, -y);
		return b;
	}
	
	@Override
	public void accepte(VisiteurDeFormes v){
		v.visiteForme(this);
	}

}
