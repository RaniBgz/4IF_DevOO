/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JPanel;

/**
 *Légende du graphe
 */
public class Legende extends JPanel {

    //*********************CONSTANTES************************************************//
    public static final String FICHIER_ENTREPOT = "entrepot.png";
    public static final Color coulPlan = Color.WHITE;
    public static final Color coulIntersection = Color.CYAN;
    public static final Color coulTroncon = Color.LIGHT_GRAY;
    public static final Color coulItineraire = new Color(0, 200, 0);
    public static final Color coulLivraison = Color.BLUE;
    public static final Color coulLivraisonRetard = Color.RED;
    public static final Color coulTexte = Color.BLACK;

    private static final int LONGUEUR_ICONE = 20;
    private static final float POURCENTAGE_MARGE_X = 0.2f;
    private static final float POURCENTAGE_MARGE_Y = 0.2f;
    private static final int ECART_ICONE_TEXTE = 10;
    private static final int ECART_LIGNE = 20;

    //Police
    private static final String NOM_POLICE = "TimesRoman";
    private static final int TAILLE_POLICE = 16;
    //TEXTE
    private static final String NOM_ENTREPOT = "Entrepôt";
    private static final String NOM_INTERSECTION = "Intersection de rue";
    private static final String NOM_LIVRAISON_A_HEURE = "Livraison à l'heure";
    private static final String NOM_LIVRAISON_EN_RETARD = "Livraison en retard";
    private static final String NOM_TRONCON = "Tronçon du plan";
    private static final String NOM_ITINERAIRE = "Partie d'un itinéraire";

    //********************************************************************************//
    private Graphics g;
    private final int margeX;
    private final int margeY;

    public Legende(Dimension d) {
        setSize(d);
        margeX = (int) ((d.width * POURCENTAGE_MARGE_X) / 2);
        margeY = (int) ((d.height * POURCENTAGE_MARGE_Y) / 2);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.g = g;
        g.setFont(new Font(NOM_POLICE, Font.PLAIN, TAILLE_POLICE));
        g.setColor(coulPlan);
        g.fillRect(0, 0, this.getSize().width, this.getSize().height);
        afficherEntrepot();
        afficherIntersection();
        afficherLivraison();
        afficherTroncons();
    }

    private void afficherEntrepot() {
        Image img1 = Toolkit.getDefaultToolkit().getImage(Legende.FICHIER_ENTREPOT);
        g.drawImage(img1, margeX, margeY, LONGUEUR_ICONE, LONGUEUR_ICONE, this);
        int x = margeX + LONGUEUR_ICONE + ECART_ICONE_TEXTE;
        int y = margeY + LONGUEUR_ICONE / 2 + this.getFont().getSize() / 2;
        g.setColor(coulTexte);
        g.drawString(NOM_ENTREPOT, x, y);
    }

    private void afficherIntersection() {

        g.setColor(coulIntersection);
        int x = (int) (this.getSize().width / 2);
        int y = margeY + LONGUEUR_ICONE / 2 - LONGUEUR_ICONE / 2;
        g.fillOval(x, y, LONGUEUR_ICONE, LONGUEUR_ICONE);
        g.setColor(coulTexte);

        x += VueGraphique.DIAMETRE_CERCLE * 2 + ECART_ICONE_TEXTE;
        y += VueGraphique.DIAMETRE_CERCLE + this.getFont().getSize() / 2;
        g.drawString(NOM_INTERSECTION, x, y);
    }

    private void afficherLivraison() {
        int x = margeX;
        int y = margeY + LONGUEUR_ICONE + ECART_LIGNE;
        g.setColor(coulLivraison);
        g.fillRect(x, y, LONGUEUR_ICONE, LONGUEUR_ICONE);
        g.setColor(coulTexte);
        g.drawString(NOM_LIVRAISON_A_HEURE, x + LONGUEUR_ICONE + ECART_ICONE_TEXTE, y + LONGUEUR_ICONE / 2 + this.getFont().getSize() / 2);

        x = (int) (this.getSize().width / 2);
        g.setColor(coulLivraisonRetard);
        g.fillRect(x, y, LONGUEUR_ICONE, LONGUEUR_ICONE);
        g.setColor(coulTexte);
        g.drawString(NOM_LIVRAISON_EN_RETARD, x + LONGUEUR_ICONE + ECART_ICONE_TEXTE, y + LONGUEUR_ICONE / 2 + this.getFont().getSize() / 2);
    }

    private void afficherTroncons() {
        int x = margeX;
        int y = margeY + (LONGUEUR_ICONE + ECART_LIGNE) * 2;
        g.setColor(coulTroncon);
        g.drawLine(x, y + LONGUEUR_ICONE / 2, x + LONGUEUR_ICONE, y + LONGUEUR_ICONE / 2);
        g.setColor(coulTexte);
        g.drawString(NOM_TRONCON, x + LONGUEUR_ICONE + ECART_ICONE_TEXTE, y + LONGUEUR_ICONE / 2 + this.getFont().getSize() / 2);

        x = (int) (this.getSize().width / 2);
        g.setColor(coulItineraire);
        BasicStroke dashed = new BasicStroke(2.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER);//le premier param est l'épaisseur
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(dashed);
        g2.drawLine(x, y + LONGUEUR_ICONE / 2, x + LONGUEUR_ICONE, y + LONGUEUR_ICONE / 2);
        VueGraphique.afficherFleche(g2, x, y + LONGUEUR_ICONE / 2, x + LONGUEUR_ICONE, y + LONGUEUR_ICONE / 2, 10, 5);
        g.setColor(coulTexte);
        g.drawString(NOM_ITINERAIRE, x + LONGUEUR_ICONE + ECART_ICONE_TEXTE, y + LONGUEUR_ICONE / 2 + this.getFont().getSize() / 2);

    }
}
