package controleur;

/**
 *
 * interface pour créer des commandes exécutables et annulables
 */
public interface Commande {

    /**
     * Execute la commande this
     */
    void doCde();

    /**
     * Execute la commande inverse a this
     */
    void undoCde();
}
