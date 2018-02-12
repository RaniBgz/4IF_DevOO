package modele;

/**
 * Représente un tronçon de rue dans un sens entre deux intersections.<br>
 * Une rue à double sens sera représentée par deux tronçons.
 */
public class Troncon {

    private final String nom;
    private final int duree;
    private final float longueur;
    private final Intersection arrivee;

    /**
     * Coefficient pour faire en sorte que la distance divisée par la vitesse
     * donne des secondes.
     * A adapter en fonction de l'unité de la vitesse.
     */
    private static final float COEFFICIENT_POUR_SECONDES = 1;

    /**
     * Crée un tronçon.
     * @param nom : nom de la rue contenant le tronçon
     * @param longueur : longueur du tronçon en mètres.
     * @param vitesseMoyenne : vitesse moyenne
     * @param arrivee : fin du tronçon
     */
    public Troncon(String nom, float longueur, float vitesseMoyenne, Intersection arrivee) {
        this.nom = nom;
        duree = Math.round(longueur / vitesseMoyenne * COEFFICIENT_POUR_SECONDES);
        this.arrivee = arrivee;
        this.longueur = longueur;
    }

    /**
     * Retourne le nom de la rue qui contient ce tronçon.
     * @return nom le nom de la rue qui contient ce tronçon.
     */
    public String getNom() {
        return nom;
    }

    /**
     * @return la durée pour traverser ce tronçon en seconde.
     */
    public int getDuree() {
        return duree;
    }

    /**
     * @return l'Intersection représentant la fin du tronçon.
     */
    public Intersection getArrivee() {
        return arrivee;
    }

    /**
     * @return la longueur du tronçon en mètres.
     */
	public float getLongueur() {
		return longueur;
	}

}
