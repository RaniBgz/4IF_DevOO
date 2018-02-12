package modele;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

/**
 * Représente une fenêtre de livraison.
 * <p>
 * Elle possède une heure de début, une heure de fin ainsi qu'une liste non
 * ordonnée de {@link Livraison}.
 *
 */
public class FenLivraisons {

    private final Horaire debut;
    private final Horaire fin;
    private final Collection<Livraison> livraisons = new HashSet<>();

    /**
     * Crée une fenêtre de livraisons
     * @param debut : horaire de début de la plage horaire.
     * @param fin : horaire de fin de la plage horaire.
     */
    public FenLivraisons(Horaire debut, Horaire fin) {
        this.debut = debut;
        this.fin = fin;
    }

    /**
     * Ajoute une livraisons à la fenêtre.<p>
     * Ajouter deux fois la même livraison n'a pas d'effet.
     * @param livraison Livraison à ajouter
     */
    public void ajouterLivraison(Livraison livraison) {
        livraisons.add(livraison);
    }

    /**
     * Supprime la livraison donnée de la fenêtre.
     * Si il n'y a pas la livraison dans la fenêtre, l'opération n'a aucun effet.
     * @param livraison Livraison à supprimer
     */
    public void supprimerLivraison(Livraison livraison) {
        livraisons.remove(livraison);
    }

    /**
     * 
     * @return l'Horaire de début de la fenêtre de livraison
     */
    public Horaire getDebut() {
        return debut;
    }

    /**
     * 
     * @return l'Horaire de fin de la fenêtre de livraison
     */
    public Horaire getFin() {
        return fin;
    }

    /**
     * Retourne une vue non-modifiable des livraisons.
     * @return Collection non modifiable de Livraisons
     */
    public Collection<Livraison> getLivraisons() {
        return Collections.unmodifiableCollection(livraisons);
    }

    /**
     * 
     * @param id id à tester
     * @return Retourne si la livraison existe déjà dans la fenêtre de livraison
     */
    public boolean idLivraisonExisteDeja(int id) {
        for (Livraison tmp : this.livraisons) {
            if (tmp.getIdXml() == id) {
                return false;
            }
        }
        return true;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((debut == null) ? 0 : debut.hashCode());
		result = prime * result + ((fin == null) ? 0 : fin.hashCode());
		return result;
	}

	/**
	 * Deux fenêtres sont considérées égales si elles couvrent la même plage horaire.
     * @param obj Object à comparer
     * @return réponse au test d'égalité
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FenLivraisons other = (FenLivraisons) obj;
		if (debut == null) {
			if (other.debut != null)
				return false;
		} else if (!debut.equals(other.debut))
			return false;
		if (fin == null) {
			if (other.fin != null)
				return false;
		} else if (!fin.equals(other.fin))
			return false;
		return true;
	}

}
