package vue;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.Point2D;
import static java.lang.Math.abs;
import static java.lang.Math.sqrt;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;
import modele.FenLivraisons;

import modele.Intersection;
import modele.Itineraire;
import modele.Livraison;
import modele.Tournee;
import modele.Troncon;

/**
 *
 *Vue sous forme d'un graphe des informations de l'application
 */
public class VueGraphique extends JPanel implements Observer {

    private Graphics g;
    private final JPanel parent;
    private final Fenetre fenetre;

    public static final int DIAMETRE_CERCLE = 10;
    public static final int DIAMETRE_DETECTION = DIAMETRE_CERCLE * 2;
    private static final double DISTANCE_COURBE = 15;
    private static final int DECALAGE = 20;

    private float echelleX;
    private float echelleY;

    private Intersection interPointee = null;
    private Livraison livraisonPointee = null;

    /**
     * Constructeur de la Vue graphique de l'application
     *
     * @param f fenêtre appelante
     * @param parent Panel parent où est placée la vue
     */
    public VueGraphique(Fenetre f, JPanel parent) {
        super();
        this.parent = parent;
        this.fenetre = f;
        f.facadeVue.addObserver(this);
        setLayout(null);
        setBackground(Color.white);
        setSize(parent.getSize());
    }

    /**
     * Methode appelee a chaque fois que VueGraphique doit etre redessinee
     *
     * @param g Objet graphique pour le dessin
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.g = g;
        setSize(parent.getSize());

        if (fenetre.facadeVue.getPlan() != null) {
            razPlan();

            afficherPlan();
            if (this.fenetre.facadeVue.getTournee() != null) {
                afficherTournee();
                afficherEntrepot();
                afficherLivraisonPointee();
            } else if (this.fenetre.facadeVue.getDemandeLivraison() != null) {
                afficherLivraison(false);
                afficherLivraisonPointee();
                afficherEntrepot();
            }
        } else {
            this.interPointee = null;
            this.livraisonPointee = null;
        }

    }

    /**
     * Calcule les couleur en fonction d'une valeur de couleur max.
     *
     * @return Retourne le tableau des couleurs
     */
    public Color[] calculerCouleur() {
        Color[] coulFenetreHoraire = new Color[this.fenetre.facadeVue.getTournee().getFenetresLivraisons().size()];
        for (int i = 0; i < coulFenetreHoraire.length; i++) {
            float actuel = (i + 1) / (float) coulFenetreHoraire.length;
            coulFenetreHoraire[i] = new Color((int) (actuel * Legende.coulItineraire.getRed()), (int) (actuel * Legende.coulItineraire.getGreen()), (int) (actuel * Legende.coulItineraire.getBlue()));
        }
        return coulFenetreHoraire;
    }

    /**
     * Afficher l'intégralité de la tournée
     */
    private void afficherTournee() {

        Map<Point, Integer> tronconsVue = new HashMap<>();

        Tournee tournee = this.fenetre.facadeVue.getTournee();

        Color[] coulFenetreHoraire = calculerCouleur();

        for (Itineraire itin : tournee.getItineraires()) {
            int i = 0;
            if (itin.getArrivee() instanceof Livraison) {
                FenLivraisons fen = tournee.getFenetreLivraisonsContenantLivraison((Livraison) itin.getArrivee());
                for (FenLivraisons fenTmp : tournee.getFenetresLivraisons()) {
                    if (fenTmp == fen) {
                        break;
                    }
                    i++;
                }
            } else {
                // retour à l'entrepot
                i = coulFenetreHoraire.length - 1;
            }
            afficherTronconItineraire(itin, tronconsVue, coulFenetreHoraire[i]);
        }
        afficherLivraison(true);
    }

    /**
     * Afficher les livraisons
     *
     * @param tournee boolean d'affichage. Si true alors on va voir dans tournée
     * sinon dans demande de livraison
     */
    private void afficherLivraison(boolean tournee) {

        List<Livraison> listeLivraison = (tournee) ? this.fenetre.facadeVue.getTournee().getLivraisons() : this.fenetre.facadeVue.getDemandeLivraison().getLivraisons();

        for (Livraison livraison : listeLivraison) {
            if (livraison.getRetard() != 0) {
                g.setColor(Legende.coulLivraisonRetard);
            } else {
                g.setColor(Legende.coulLivraison);
            }
            g.fillRect((int) (livraison.getIntersection().getX() * this.echelleX - DIAMETRE_CERCLE / 2), (int) (livraison.getIntersection().getY() * this.echelleY - DIAMETRE_CERCLE / 2), DIAMETRE_CERCLE, DIAMETRE_CERCLE);

        }
    }

    /**
     * Affiche tous les tronçons d'un itinéraire
     *
     * @param itineraire Itinéraire source
     */
    private void afficherTronconItineraire(Itineraire itineraire, Map<Point, Integer> tronconsVues, Color couleur) {
        g.setColor(couleur);
        BasicStroke dashed = new BasicStroke(2.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER);//le premier param est l'épaisseur
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(dashed);
        List<Troncon> troncons = itineraire.getTroncons();
        if (troncons.size() > 0) {
            afficheUnTronconDeItineraire(g2, itineraire.getDepart().getIntersection(), tronconsVues, troncons.get(0));

            for (int i = 0; i < itineraire.getTroncons().size() - 1; i++) {
                afficheUnTronconDeItineraire(g2, troncons.get(i).getArrivee(), tronconsVues, troncons.get(i + 1));
            }
        }

    }

    /**
     * Afficher un tronçon (ligne + flèche)
     *
     * @param g2 Composant graphique
     * @param depart point de départ du tronçon
     * @param arrivee point d'arrivée du tronçon
     */
    private void afficheUnTronconDeItineraire(Graphics2D g2, Intersection depart, Map<Point, Integer> tronconsVues, Troncon troncon) {
        Point p = new Point(
                (int) (((depart.getX() * this.echelleX) + (troncon.getArrivee().getX() * this.echelleX)) / 2),
                (int) (((depart.getY() * this.echelleY) + (troncon.getArrivee().getY() * this.echelleY)) / 2));

        if (!tronconsVues.containsKey(p)) {
            tronconsVues.put(p, 0);
            g2.drawLine(
                    (int) (depart.getX() * this.echelleX),
                    (int) (depart.getY() * this.echelleY),
                    (int) (troncon.getArrivee().getX() * this.echelleX),
                    (int) (troncon.getArrivee().getY() * this.echelleY));

            afficherFleche(
                    g2,
                    (int) (depart.getX() * this.echelleX),
                    (int) (depart.getY() * this.echelleY),
                    (int) (troncon.getArrivee().getX() * this.echelleX),
                    (int) (troncon.getArrivee().getY() * this.echelleY),
                    (int) (DIAMETRE_CERCLE * 2),
                    (int) (DIAMETRE_CERCLE / 2));
        } else {
            tronconsVues.put(p, tronconsVues.get(p) + 1);
            int coef = tronconsVues.get(p);

            double xa = depart.getX() * this.echelleX;
            double ya = depart.getY() * this.echelleY;
            double xb = troncon.getArrivee().getX() * this.echelleX;
            double yb = troncon.getArrivee().getY() * this.echelleY;
            double xc = ((xb + xa) / 2) + DISTANCE_COURBE * coef * abs((yb - ya) / sqrt((xb - xa) * (xb - xa) + (yb - ya) * (yb - ya)));
            double yc = ((yb + ya) / 2) + DISTANCE_COURBE * coef * abs((xb - xa) / sqrt((xb - xa) * (xb - xa) + (yb - ya) * (yb - ya)));

            CubicCurve2D cub = new CubicCurve2D.Double();
            Point2D[] pt = new Point2D[4];
            pt[0] = new Point2D.Double((int) (xa), (int) (ya)); //origine
            pt[1] = new Point2D.Double((int) (xc), (int) (yc)); //contrôle 1
            pt[2] = pt[1];//contrôle 2
            pt[3] = new Point2D.Double((int) (xb), (int) (yb)); //extrémité
            cub.setCurve(pt[0], pt[1], pt[2], pt[3]);
            g2.draw(cub);

            afficherFleche(
                    g2,
                    (int) (xc),
                    (int) (yc),
                    (int) (troncon.getArrivee().getX() * this.echelleX),
                    (int) (troncon.getArrivee().getY() * this.echelleY),
                    (int) (DIAMETRE_CERCLE * 2),
                    (int) (DIAMETRE_CERCLE / 2));
        }
    }

    /**
     * Affiche à l'extrèmité d'un segment (non dessiné)
     *
     * @param g2 composant graphique
     * @param x1 x du point 1
     * @param y1 y du point 1
     * @param x2 x du point 2
     * @param y2 y du point 2
     * @param largeur largeur de la fleche
     * @param hauteur hauteur de la flèche
     */
    public static void afficherFleche(Graphics2D g2, int x1, int y1, int x2, int y2, int largeur, int hauteur) {
        int dx = x2 - x1, dy = y2 - y1;
        double D = Math.sqrt(dx * dx + dy * dy);
        double xm = D - largeur, xn = xm, ym = hauteur, yn = -hauteur, x;
        double sin = dy / D, cos = dx / D;

        x = xm * cos - ym * sin + x1;
        ym = xm * sin + ym * cos + y1;
        xm = x;

        x = xn * cos - yn * sin + x1;
        yn = xn * sin + yn * cos + y1;
        xn = x;

        int[] xpoints = {x2, (int) xm, (int) xn};
        int[] ypoints = {y2, (int) ym, (int) yn};

        g2.fillPolygon(xpoints, ypoints, 3);
    }

    /**
     * Affiche la Livraison pointée en plus gros
     */
    private void afficherLivraisonPointee() {
        if (livraisonPointee != null) {
            if (livraisonPointee.getRetard() != 0) {
                g.setColor(Legende.coulLivraisonRetard);
            } else {
                g.setColor(Legende.coulLivraison);
            }
            g.fillRect((int) (livraisonPointee.getIntersection().getX() * this.echelleX - DIAMETRE_DETECTION / 2), (int) (livraisonPointee.getIntersection().getY() * this.echelleY - DIAMETRE_DETECTION / 2), DIAMETRE_DETECTION, DIAMETRE_DETECTION);
        }
    }

    /**
     * Affiche l'image de l'entrepot
     */
    private void afficherEntrepot() {
        Intersection entrepot = (this.fenetre.facadeVue.getTournee() != null) ? fenetre.facadeVue.getTournee().getEntrepot().getIntersection() : fenetre.facadeVue.getDemandeLivraison().getEntrepot().getIntersection();
        Image img1 = Toolkit.getDefaultToolkit().getImage(Legende.FICHIER_ENTREPOT);
        g.drawImage(img1, (int) (entrepot.getX() * this.echelleX - DIAMETRE_DETECTION / 2), (int) (entrepot.getY() * this.echelleY - DIAMETRE_DETECTION / 2), DIAMETRE_DETECTION, DIAMETRE_DETECTION, this);
    }

    /**
     * Affiche le plan (Intersection et les Tronçons)
     */
    private void afficherPlan() {
        for (Intersection inter : fenetre.facadeVue.getPlan().getListeIntersections()) {

            afficherTronconPlan(inter);
            g.setColor(Legende.coulIntersection);
            g.fillOval((int) (inter.getX() * this.echelleX) - DIAMETRE_CERCLE / 2, (int) (inter.getY() * this.echelleY) - DIAMETRE_CERCLE / 2, DIAMETRE_CERCLE, DIAMETRE_CERCLE);
        }
        afficherIntersectionPointee();
    }

    /**
     * Afficher tous les tronçons de l'intersection
     *
     * @param inter Intersection source
     */
    private void afficherTronconPlan(Intersection inter) {
        g.setColor(Legende.coulTroncon);
        for (Troncon troncon : inter.getTronconsSortants()) {
            g.drawLine((int) (inter.getX() * this.echelleX), (int) (inter.getY() * this.echelleY),
                    (int) (troncon.getArrivee().getX() * this.echelleX), (int) (troncon.getArrivee().getY() * this.echelleY));

        }
    }

    /**
     * Affiche l'Intersection pointée en plus gros
     */
    private void afficherIntersectionPointee() {
        if (interPointee != null) {
            g.setColor(Legende.coulIntersection);
            g.fillOval((int) (interPointee.getX() * this.echelleX - DIAMETRE_DETECTION / 2), (int) (interPointee.getY() * this.echelleY - DIAMETRE_DETECTION / 2), DIAMETRE_DETECTION, DIAMETRE_DETECTION);
        }
    }

    /**
     * Efface l'écran et le remet à l'état initial
     */
    private void razPlan() {
        g.setColor(Legende.coulPlan);
        Dimension maxPlan = fenetre.facadeVue.getPlan().getCoordonneesMax();
        g.fillRect(0, 0, this.getSize().width, this.getSize().height);
        this.echelleX = this.getSize().width / ((float) maxPlan.width + DECALAGE);
        this.echelleY = this.getSize().height / ((float) maxPlan.height + DECALAGE);
    }

    /**
     * Suppression de l'intersection et/ou de la livraison pointée
     */
    void razSelectionVue() {
        this.livraisonPointee = null;
        this.interPointee = null;
    }

    /**
     * Methode appelee par les objets observes par this a chaque fois qu'ils ont
     * ete modifies
     *
     * @param o
     * @param arg argument passé par le Notify
     */
    @Override
    public void update(Observable o, Object arg) {
        if (arg != null) { // arg est une forme qui vient d'etre ajoutee a plan
            if (arg instanceof Intersection) {
                this.interPointee = (Intersection) arg;
                this.livraisonPointee = null;
            } else if (arg instanceof Livraison) {
                this.livraisonPointee = (Livraison) arg;
                this.interPointee = null;
            }
        }
        repaint();
    }

    /**
     * Getteur de l'échelle calculée de X
     *
     * @return echelle de X
     */
    public float getEchelleX() {
        return echelleX;
    }

    /**
     * Getteur de l'échelle calculée de Y
     *
     * @return echelle de Y
     */
    public float getEchelleY() {
        return echelleY;
    }

}
