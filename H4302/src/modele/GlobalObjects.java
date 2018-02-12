package modele;

/**
 * Objet possédant les pointeurs vers les objets principaux du modèle.
 * <p>
 * Il est partagé entre les différents points d'entrée.
 */
class GlobalObjects {

	private Tournee tournee = null;
	private DemandeLivraisons demandeLivraisons = null;
	private Plan plan = null;
	
	protected GlobalObjects() {
		// constructeur package private
	}

	/**
         * Retourne la tournée courante ou null si elle n'est pas calculée.
         * @return la tournée de l'objet
         */
	public Tournee getTournee() {
		return tournee;
	}

	/**
	 * Définit la tournée courante. Peut être null.
	 */
	protected void setTournee(Tournee tournee) {
		this.tournee = tournee;
                this.demandeLivraisons = null;
	}

	/**
	 * Retourne la demande de livraisons courante ou null si elle n'est pas chargée.
	 * @return la demande de livraison de l'objet
         */
	public DemandeLivraisons getDemandeLivraisons() {
		return demandeLivraisons;
	}

	/**
	 * Définit la demande de livraisons courante. Peut être null.
         * @param  Demande de livraison à enregistrer
	 */
	protected void setDemandeLivraisons(DemandeLivraisons demandeLivraisons) {
		this.demandeLivraisons = demandeLivraisons;
                this.tournee = null;
	}

	/**
	 * Retourne le plan courant ou null si il n'est pas chargé.
         * @return Retourne le plan de l'objet
	 */
	public Plan getPlan() {
		return plan;
	}

	/**
	 * Définit le plan courant. Peut être null.
         * @param Plan à enregistrer
	 */
	protected void setPlan(Plan plan) {
		this.plan = plan;
	}
	
}
