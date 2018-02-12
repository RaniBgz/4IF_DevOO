package modele;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Représente le plan de la ville avec ses intersections et ses tronçons.
 */
public class Plan {

    private Collection<Intersection> listeIntersections = new ArrayList<>();

    /**
     * @return une vue non-modifiable de la liste des intersections.
     */
    public Collection<Intersection> getListeIntersections() {
        return Collections.unmodifiableCollection(listeIntersections);
    }

    /**
     * @param id l'id de l'intersection à obtenir
     * @return l'intersection avec l'ID donné.
     */
    public Intersection getIntersection(int id) {
        for (Intersection tmp : this.listeIntersections) {
            if (tmp.getId() == id) {
                return tmp;
            }
        }
        return null;
    }

    /**
     * Ajoute une intersection au plan avec l'ID et les coordonnées passées en
     * arguments.
     * @param id l'id de l'intersection à ajouter
     * @param x abscisse de l'intersection
     * @param y ordonné de l'intersection
     */
    public void ajouteIntersection(int id, int x, int y) {
        Intersection inter = new Intersection(x, y, id);
        this.listeIntersections.add(inter);
    }

    /**
     * @return une dimension dont les coordonnées sont les coordonnées maximales du plan.
     */
    public Dimension getCoordonneesMax() {
        int x = 0, y = 0;
        for (Intersection tmp : this.listeIntersections) {
            if (tmp.getX() > x) {
                x = tmp.getX();
            }
            if (tmp.getY() > y) {
                y = tmp.getY();
            }
        }
        return new Dimension(x, y);
    }

}
