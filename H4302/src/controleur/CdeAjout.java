package controleur;

import modele.PointEntreeControleur;
import modele.Intersection;
import modele.Livraison;
import modele.PointDePassage;

/**
 *
 * Classe permettant de créer une commande exécutable et annulable pour ajouter
 * une livraison à la tournée
 */
public class CdeAjout implements Commande {

    private final PointEntreeControleur facadeControleur;
    private final PointDePassage livraisonPrecedente;
    private final Intersection nouvelleLivraison;
    private final int numClient;

    private Livraison l;

    /**
     * Crée une commande dont la fonction est d'ajouter une nouvelle livraison à
     * la tournée
     *
     * @param facadeControleur
     * @param livraisonPrecedente la livraison après laquelle on souhaite
     * rajouter une livraison
     * @param nouvelleLivraison l'intersection qui va devenir un point de
     * livraison
     * @param numClient le numéro du client associé à la livraison qui sera
     * ajouté
     */
    public CdeAjout(PointEntreeControleur facadeControleur, PointDePassage livraisonPrecedente, Intersection nouvelleLivraison, int numClient) {

        this.facadeControleur = facadeControleur;
        this.livraisonPrecedente = livraisonPrecedente;
        this.nouvelleLivraison = nouvelleLivraison;
        this.numClient = numClient;
        l = new Livraison(nouvelleLivraison, 0, numClient);
    }

    /**
     * Méthode pour exécuter l'action de la commande
     *
     */
    @Override
    public void doCde() {
        
        facadeControleur.ajouterLivraison(livraisonPrecedente, l);
    }

    /**
     * Méthode pour effectuer l'action inverse de la commande pour l'annuler
     */
    @Override
    public void undoCde() {
        facadeControleur.supprimerLivraison(l);
    }

}
