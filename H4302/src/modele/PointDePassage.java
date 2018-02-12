package modele;

/**
 * Représente un point de passage pour la tournée.
 * C'est-à-dire une livraison ou l'entrepot.
 */
public interface PointDePassage {

	/**
	 * @return l'ID unique généré automatiquement.
	 */
    public int getIdGenere();

    /**
     * @return l'intersection où est située le point de passage.
     */
    public Intersection getIntersection();
    
    /**
     * @return le retard à ce point de passage.
     * Le temps de livraison est inclus.
     */
    public int getRetard();
    
    /**
     * @return l'attente à ce point de passage.
     */
    public int getAttente();
    
    /**
     * @return l'horaire de passage à ce point.
     */
    public Horaire getHoraireEffectif();

}
