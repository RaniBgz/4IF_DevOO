package vue;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


import controleur.Controleur;
import java.awt.Point;

/**
 *
 *  Ecouteur de souris pour le tableau de la vue textuelle
 */
public class EcouteurDeSourisTableauVueTextuelle extends MouseAdapter {

    private final Controleur controleur;
    private final VueTextuelle vueTextuelle;
    private final Fenetre fenetre;

    /**
     * Constructeur de l'écouteur de souris pour la vue Textuelle
     *
     * @param controleur
     * @param vueTextuelle
     * @param fenetre
     */
    public EcouteurDeSourisTableauVueTextuelle(Controleur controleur, VueTextuelle vueTextuelle, Fenetre fenetre) {
        this.controleur = controleur;
        this.vueTextuelle = vueTextuelle;
        this.fenetre = fenetre;
    }

    /**
     * Traitement de lévènement "clic" de la souris
     *
     * @param evt Evènement reçu
     */
    @Override
    public void mouseClicked(MouseEvent evt) {
        if (this.fenetre.facadeVue.getPlan() == null || this.fenetre.facadeVue.getTournee() == null) {
            return;
        }

        switch (evt.getButton()) {
            case MouseEvent.BUTTON1:
                int idLivraison = coordonnees(evt);
                if (idLivraison != -1) {
                    controleur.clicGauche(null, idLivraison);
                }
                break;
            case MouseEvent.BUTTON3:
                controleur.clicDroit();
                break;
            default:
        }
    }

    /**
     * Traitement de lévènement "déplacement" de la souris
     *
     * @param evt Evènement reçu
     */
    @Override
    public void mouseMoved(MouseEvent evt) {
        // Methode appelee a chaque fois que la souris est bougee
        // Envoie au controleur les coordonnees de la souris.
        if (this.fenetre.facadeVue.getPlan() == null || this.fenetre.facadeVue.getTournee() == null && this.fenetre.facadeVue.getDemandeLivraison()== null) {
            return;
        }
        int idLivraison = coordonnees(evt);
        if (idLivraison != -1) {
            controleur.sourisBougee(null, idLivraison);
        }

    }

    /**
     * Calcul de l'id de livraison avec évènement de souris
     * 
     * @param evt Evènement
     * @return Retourne l'id de la livraison sur la ligne cliquée
     */
    private int coordonnees(MouseEvent evt) {

        Point p = evt.getPoint();
        return Integer.parseInt(this.vueTextuelle.getTableau().getValueAt(this.vueTextuelle.getTableau().rowAtPoint(p), 0).toString());
    }

}
