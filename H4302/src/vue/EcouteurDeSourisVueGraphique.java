package vue;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;

import controleur.Controleur;
import java.awt.Point;

/**
 *
 *  Ecouteur de souris pour la vue graphique
 */
public class EcouteurDeSourisVueGraphique extends MouseAdapter {

    private final Controleur controleur;
    private final VueGraphique vueGraphique;
    private final Fenetre fenetre;

    /**
     * Constructeur de l'écouteur de souris pour la vue graphique
     * @param controleur
     * @param vueGraphique
     * @param fenetre
     */
    public EcouteurDeSourisVueGraphique(Controleur controleur, VueGraphique vueGraphique, Fenetre fenetre) {
        this.controleur = controleur;
        this.vueGraphique = vueGraphique;
        this.fenetre = fenetre;
    }

    /**
     * Traitement de lévènement "clic" de la souris
     * @param evt Evènement reçu
     */
    @Override
    public void mouseClicked(MouseEvent evt) {
        // Methode appelee par l'ecouteur de souris a chaque fois que la souris est cliquee
        // S'il s'agit d'un clic gauche dans la vue graphique, l'ecouteur envoie au controleur les coordonnees du point clique.
        // S'il s'agit d'un clic droit, l'ecouteur envoie le message d'echappement au controleur
        switch (evt.getButton()) {
            case MouseEvent.BUTTON1:
                Point p = coordonnees(evt);
                if (p != null) {
                    controleur.clicGauche(p, -2);
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
        Point p = coordonnees(evt);
        if (p != null) {
            controleur.sourisBougee(p, -2);
        }
    }

    /**
     * Calcul d'un point adapté à la vue graphique correspondant au clic sur la zone graphique
     * 
     * @param evt Evènement reçu
     * @return Retourne le Point calculé
     */
    private Point coordonnees(MouseEvent evt) {
        MouseEvent e = SwingUtilities.convertMouseEvent(fenetre, evt, vueGraphique);
        int x = Math.round((float) e.getX() / (float) vueGraphique.getEchelleX());
        int y = Math.round((float) e.getY() / (float) vueGraphique.getEchelleY());
        return new Point(x, y);
    }

}
