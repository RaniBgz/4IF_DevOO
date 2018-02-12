package modele;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Représente une intersection de la ville.
 */
public class Intersection {

    private final int x;
    private final int y;
    private final int id;
    private final List<Troncon> tronconsSortants;

    /**
     * Crée une {@link Intersection}
     *
     * @param x Coordonnée X sur la carte
     * @param y Coordonnée Y sur la carte
     * @param id numéro de l'intersection dans le plan
     */
    Intersection(int x, int y, int id) {
        this.x = x;
        this.y = y;
        this.id = id;
        tronconsSortants = new ArrayList<>();
    }

    /**
     * Ajoute un tronçon partant de cette intersection.
     *
     * @param tronconSortant Tronçon à ajouter
     */
    public void ajouterTronconSortant(Troncon tronconSortant) {
        tronconsSortants.add(tronconSortant);
    }

    /**
     * Retourne la coordonnée X de cette intersection.
     * @return la coordonnée X de cette intersection.
     */
    public int getX() {
        return x;
    }

    /**
     * Retourne la coordonnée Y de cette intersection.
     * @return la coordonnée Y de cette intersection.
     */
    public int getY() {
        return y;
    }

    /**
     * Retourne l'ID de cette intersection.
     * @return  l'ID de cette intersection.
     */
    public int getId() {
        return id;
    }

    /**
     * Retourne une vue non-modifiable des tronçons sortants de cette
     * intersection
     * @return Retourne la liste des Tronçons non modifiable
     */
    public Collection<Troncon> getTronconsSortants() {
        return Collections.unmodifiableList(tronconsSortants);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        return result;
    }

    /**
     * Deux intersections sont égales ssi elles possèdent le même ID.
     * @return Retourne si les objets sont égals
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Intersection other = (Intersection) obj;
        if (id != other.id) {
            return false;
        }
        return true;
    }

}
