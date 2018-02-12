package vue;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import controleur.Controleur;

/**
 *
 *  Ecouteur de souris pour la vue textuelle
 */
public class EcouteurDeSourisVueTextuelle extends MouseAdapter {

    private final Controleur controleur;
    private final Fenetre fenetre;

    /**
     * Constructeur de l'écouteur de souris pour la vue Textuelle
     *
     * @param controleur
     * @param fenetre
     */
    public EcouteurDeSourisVueTextuelle(Controleur controleur, Fenetre fenetre) {
        this.controleur = controleur;
        this.fenetre = fenetre;
    }

    /**
     * Traitement de lévènement "clic" de la souris
     *
     * @param evt Evènement reçu
     */
    @Override
    public void mouseClicked(MouseEvent evt) {
        if (this.fenetre.facadeVue.getPlan() == null || this.fenetre.facadeVue.getTournee() == null && this.fenetre.facadeVue.getDemandeLivraison()== null) {
            return;
        }

        switch (evt.getButton()) {
            case MouseEvent.BUTTON3:
                controleur.clicDroit();
                break;
            default:
        }
    }

}
