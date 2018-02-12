package modele;

import java.awt.Point;
import java.util.List;
import java.util.Observable;

/**
 * Classe de point d'entrée du controleur sur le package vue.
 */
public class PointEntreeVue extends Observable {

    private final GlobalObjects objects;

    /**
     * constructeur
     */
    public PointEntreeVue() {
        objects = new GlobalObjects();
    }

    /**
     * @return la demande de livraisons courante ou null si elle n'est pas
     * chargée.
     */
    public DemandeLivraisons getDemandeLivraison() {
        return objects.getDemandeLivraisons();
    }

    /**
     * @return la liste des itinéraires de la tournée.
     */
    public List<Itineraire> getListeItineraires() {
        return objects.getTournee().getItineraires();
    }

    /**
     * @return le plan courant ou null si elle n'est pas chargée.
     *  
     */
    public Plan getPlan() {
        return objects.getPlan();
    }

    /**
     * @return la liste des fenêtres de livraisons de la
     * tournée.
     */
    public List<FenLivraisons> getFenLivraisons() {
        return objects.getTournee().getFenetresLivraisons();
    }

    /**
     * Détermine si le point pointé est proche d'une livraison.
     *
     * @param p point pointé
     * @param ERREUR diamètre de détection
     * @return la livraison trouvée ou null
     */
    public Livraison isLivraison(Point p, int ERREUR) {
        if (objects.getPlan() == null) {
            return null;
        }

        List<Livraison> listeLivraison = (objects.getTournee() != null) ? this.objects.getTournee().getLivraisons() : this.objects.getDemandeLivraisons().getLivraisons();

        for (Livraison livraison : listeLivraison) {
            if (p.getX() >= livraison.getIntersection().getX() - ERREUR / 2
                    && p.getX() <= livraison.getIntersection().getX() + ERREUR / 2
                    && p.getY() >= livraison.getIntersection().getY() - ERREUR / 2
                    && p.getY() <= livraison.getIntersection().getY() + ERREUR / 2) {
                return livraison;
            }
        }

        return null;
    }

    /**
     * Détermine si le point pointé est proche de l'entrepot.
     *
     * @param p point pointé
     * @param ERREUR diamètre de détection
     * @return l'intersection de l'entrepot trouvé ou null
     */
    public Intersection isEntrepot(Point p, int ERREUR) {
        if (objects.getPlan() == null) {
            return null;
        }
        Intersection tmp = objects.getTournee().getEntrepot().getIntersection();
        if (p.getX() >= tmp.getX() - ERREUR / 2 && p.getX() <= tmp.getX() + ERREUR / 2
                && p.getY() >= tmp.getY() - ERREUR / 2 && p.getY() <= tmp.getY() + ERREUR / 2) {
            return tmp;
        }

        return null;
    }

    /**
     * Détermine si le point pointé est proche d'une intersection.
     *
     * @param p point pointé
     * @param ERREUR diamètre de détection
     * @return l'intersection trouvée ou null
     */
    public Intersection isIntersection(Point p, int ERREUR) {
        if (objects.getPlan() == null) {
            return null;
        }
        for (Intersection tmp : objects.getPlan().getListeIntersections()) {
            if (p.getX() >= tmp.getX() - ERREUR / 2 && p.getX() <= tmp.getX() + ERREUR / 2
                    && p.getY() >= tmp.getY() - ERREUR / 2 && p.getY() <= tmp.getY() + ERREUR / 2) {
                return tmp;
            }
        }
        return null;
    }

    /**
     * @return la tournée courante ou null si elle n'est pas calculée.
     */
    public Tournee getTournee() {
        return objects.getTournee();
    }

    /**
     * @param idLivraison l'id de la livraison souhaitée
     * @return la livraison avec l'ID généré donnée.
     */
    public Livraison getLivraison(int idLivraison) {
        List<Livraison> listeLivraison = (objects.getTournee() != null) ? this.objects.getTournee().getLivraisons() : this.objects.getDemandeLivraisons().getLivraisons();

        for (Livraison livraison : listeLivraison) {
            if (livraison.getIdGenere() == idLivraison) {
                return livraison;
            }

        }
        return null;
    }

    /**
     * Renvoie le {@link GlobalObjects} qui contient les références vers les
     * objets globaux au modèles
     */
    GlobalObjects getGlobalObjects() {
        return objects;
    }

    /**
     * Informe les observers que le modèle a changé.
     *
     * @param arg : argument joker
     */
    public void notifyChanged(Object arg) {
        this.setChanged();
        this.notifyObservers(arg);
    }

}
