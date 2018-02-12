package controleur;

import modele.PointEntreeControleur;
import modele.FenLivraisons;
import modele.Livraison;
import modele.PointDePassage;
import modele.Tournee;

/**
 *
 * Classe permettant de créer une commande exécutable et annulable pour
 * supprimer une livraison de la tournée
 */
public class CdeSuppr implements Commande {

    private final PointEntreeControleur facadeControleur;
    private final Livraison livraison;

    private PointDePassage livraisonPrecedente;
    private FenLivraisons fenLivraisonPrecedente;

    /**
     * Constructeur
     *
     * @param facadeControleur
     * @param livraison la livraison à enlever de la liste des livraisons de la
     * tournée
     */
    public CdeSuppr(PointEntreeControleur facadeControleur, Livraison livraison) {

        this.facadeControleur = facadeControleur;
        this.livraison = livraison;
    }

    /**
     * Méthode pour exécuter l'action de la commande
     *
     */
    @Override
    public void doCde() {
        Tournee tournee = facadeControleur.getTournee();
        int i = 0;
        for (Livraison livraisonTmp : tournee.getLivraisons()) {
            if (livraisonTmp == livraison) {
                if (i == 0) {
                    this.livraisonPrecedente = tournee.getEntrepot();
                } else {
                    this.livraisonPrecedente = tournee.getLivraisons().get(i - 1);
                }
            }
            i++;
        }
        this.fenLivraisonPrecedente = facadeControleur.supprimerLivraison(livraison);
    }

    /**
     * Méthode pour annuler l'action de la commande
     *
     */
    @Override
    public void undoCde() {
        facadeControleur.ajouterLivraison(livraisonPrecedente, livraison, fenLivraisonPrecedente);
    }

}
