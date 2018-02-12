package modele;

import java.util.Collections;
import java.util.List;

/**
 * Représente le plus court chemin entre deux points de passage
 */
public class Itineraire {

    private final PointDePassage depart;
    private final PointDePassage arrivee;
    private final List<Troncon> troncons;
    private final int duree;

    /**
     * Crée un itinéraire.
     * @param depart intersection de départ
     * @param arrivee intersection d'arrivée
     * @param troncons liste ordonnée des tronçons constituant cet itinéraire
     * @param duree durée en seconde de l'itinéraire.
     */
    Itineraire(PointDePassage depart, PointDePassage arrivee, List<Troncon> troncons, 
    		int duree) {
        this.depart = depart;
        this.arrivee = arrivee;
        this.troncons = troncons;
        this.duree = duree;
    }

    /**
     * Retourne une vue immodifiable des tronçons composant cet itinéraire.
     * @return Retourne la liste des tronçons de l'itinéraire non modifiable
     */
    public List<Troncon> getTroncons() {
        return Collections.unmodifiableList(troncons);
    }

    /**
     * Retourne l'intersection de départ.
     * @return Retourne le point de passage de départ
     */
    public PointDePassage getDepart() {
        return depart;
    }

    /**
     * Retourne l'intersection d'arrivée.
     * @return Retourne le point de passage d'arrivée
     */
    public PointDePassage getArrivee() {
        return arrivee;
    }

    /**
     * Retourne la durée en secondes de cet itinéraire.
     * @return Retourne la durée en secondes de cet itinéraire.
     */
    public int getDuree() {
        return duree;
    }
}
