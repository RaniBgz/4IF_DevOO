package vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import modele.DemandeLivraisons;

import modele.FenLivraisons;
import modele.Horaire;
import modele.Livraison;
import modele.Tournee;

/**
 *Vue sous forme d'un tableau des livraisons
 */
public class VueTextuelle extends JPanel implements Observer {

    private final JPanel parent;
    private final Fenetre fenetre;
    private Livraison livraisonPointee = null;
    private int numLignePointee = -1;
    private EcouteurDeSourisTableauVueTextuelle ecouteurTableau;
    private EcouteurDeSourisVueTextuelle ecouteurVue;

    private JTable tableau = null;

    private boolean[] retard = null;

    /**
     * Cree la vue textuelle récapitulant les livraisons la fenetre f
     *
     * @param f la fenetre
     * @param parent
     */
    public VueTextuelle(Fenetre f, JPanel parent) {
        super();
        this.parent = parent;
        this.fenetre = f;
        f.facadeVue.addObserver(this); // this observe plan
        setLayout(null);
        setBackground(Color.white);
        setSize(parent.getSize());
    }

    /**
     *
     * Enregistrement de l'écouteur global sur la vue (juste pour le "clic
     * droit")
     *
     * @param ecouteur
     */
    public void setMouseListener(EcouteurDeSourisVueTextuelle ecouteur) {
        this.ecouteurVue = ecouteur;
    }

    /**
     *
     * Enregistrement de l'écouteur sur le tableau de la vue
     *
     * @param ecouteur
     */
    public void setMouseListenerTabeau(EcouteurDeSourisTableauVueTextuelle ecouteur) {
        this.ecouteurTableau = ecouteur;
    }

    /**
     * Getteur sur le tableau de la vue
     *
     * @return Retourne la JTable
     */
    public JTable getTableau() {
        return tableau;
    }

    /**
     * Calcul les couleur en fonction d'une valeur de couleur max.
     *
     * @return Retourne le tableau des couleurs
     */
    public Color[] calculerCouleur() {
        Tournee tournee = this.fenetre.facadeVue.getTournee();
        DemandeLivraisons demande = this.fenetre.facadeVue.getDemandeLivraison();
        if (tournee == null && demande == null) {
            return new Color[0];
        }
        Color[] coulFenetreHoraire;
        if (tournee != null) {
            coulFenetreHoraire = new Color[tournee.getFenetresLivraisons().size()];
        } else {
            coulFenetreHoraire = new Color[demande.getFenLivraisons().size()];
        }
        for (int i = 0; i < coulFenetreHoraire.length; i++) {
            float actuel = (i + 1) / (float) coulFenetreHoraire.length;
            coulFenetreHoraire[i] = new Color((int) (actuel * Legende.coulItineraire.getRed()), (int) (actuel * Legende.coulItineraire.getGreen()), (int) (actuel * Legende.coulItineraire.getBlue()));
        }
        return coulFenetreHoraire;
    }

    /**
     * Methode appelee a chaque fois que VueTextuelle doit etre recréée
     *
     * @param RAZ Si vrai alors le tableau est recrée mais vide
     */
    public void faireTable(boolean RAZ) {
        String[] entetes = {"#", "Client", "Lieu", "Plage Horaire", "Heure Passage", "Couleur"};

        String[][] tab;
        int[] couleur;
        retard = null;
        if (RAZ) {
            tab = new String[0][6];
            retard = new boolean[0];
            couleur = new int[0];
        } else {
            if (this.fenetre.facadeVue.getTournee() == null) {
                couleur = new int[this.fenetre.facadeVue.getDemandeLivraison().getLivraisons().size()];
            } else {
                couleur = new int[this.fenetre.facadeVue.getTournee().getLivraisons().size()];
            }
            tab = chargerTable(couleur);
        }

        //<editor-fold defaultstate="collapsed" desc=" Génération du tableau ">
        tableau = new JTable(tab, entetes) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tableau.getColumnModel().getColumn(0).setPreferredWidth(50);
        tableau.getColumnModel().getColumn(2).setPreferredWidth(120);
        tableau.getColumnModel().getColumn(3).setPreferredWidth(150);
        tableau.getColumnModel().getColumn(4).setPreferredWidth(150);

        //centrer les cellule de la table
        DefaultTableCellRenderer custom = new DefaultTableCellRenderer();
        custom.setHorizontalAlignment(JLabel.CENTER); // centre les données de ton tableau 
        custom.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        for (int i = 0; i < tableau.getColumnCount(); i++) // centre chaque cellule de ton tableau 
        {
            tableau.getColumnModel().getColumn(i).setHeaderRenderer(custom);
            // tableau.getColumnModel().getColumn(i).setCellRenderer(custom);
        }
        tableau.setDefaultRenderer(Object.class, new JTableRender(tab, retard, couleur, calculerCouleur()));

        if (this.numLignePointee != -1) {
            tableau.getSelectionModel().addSelectionInterval(this.numLignePointee, this.numLignePointee);
        }

        tableau.addMouseListener(this.ecouteurTableau);
        tableau.addMouseMotionListener(this.ecouteurTableau);

        JScrollPane jScrollPane1 = new JScrollPane(tableau);
        jScrollPane1.setPreferredSize(this.parent.getSize());
        parent.removeAll();
        this.parent.setLayout(new java.awt.BorderLayout());

        this.parent.add(jScrollPane1, BorderLayout.CENTER);

        jScrollPane1.addMouseListener(this.ecouteurVue);
        jScrollPane1.addMouseMotionListener(this.ecouteurVue);

        fenetre.revalidate();
        //</editor-fold>
    }

    /**
     * Création des données du tableau sous forme d'un tableau de String 2D
     *
     * @return Retourne le tableau conenant les données
     */
    private String[][] chargerTable(int[] couleur) {
        Tournee tournee = this.fenetre.facadeVue.getTournee();
        DemandeLivraisons demande = this.fenetre.facadeVue.getDemandeLivraison();
        int nbLigne = (demande == null) ? tournee.getLivraisons().size() : demande.getLivraisons().size();

        String[][] tab = new String[nbLigne][6];
        retard = new boolean[nbLigne];
        int i = 0;
        this.numLignePointee = -1;

        List<Livraison> listeLivraison = (demande == null) ? tournee.getLivraisons() : demande.getLivraisons();

        for (Livraison livraison : listeLivraison) {

            int j = 0;
            FenLivraisons fen = (demande == null) ? tournee.getFenetreLivraisonsContenantLivraison(livraison) : demande.getFenetreLivraisonsContenantLivraison(livraison);

            List<FenLivraisons> listeFen = (demande == null) ? tournee.getFenetresLivraisons() : demande.getFenLivraisons();
            for (FenLivraisons fenTmp : listeFen) {
                if (fenTmp == fen) {
                    couleur[i] = j;
                }
                j++;
            }

            retard[i] = livraison.getRetard() != 0;

            if (livraison == livraisonPointee) {
                this.numLignePointee = i;
            }
            tab[i][0] = "" + livraison.getIdGenere();
            tab[i][1] = "" + livraison.getNoClient();

            tab[i][2] = "" + livraison.getIntersection().getX() + "  " + livraison.getIntersection().getY();

            Horaire debut = fen.getDebut();
            String hDebut = debut.getHeures() + "h";
            if (debut.getSecondes() != 0) {
                hDebut += debut.getMinutes() + "m";
                hDebut += debut.getSecondes() + "s";
            } else if (debut.getMinutes() != 0) {
                hDebut += debut.getMinutes() + "m";
            }
            Horaire fin = fen.getFin();
            String hFin = fin.getHeures() + "h";
            if (fin.getSecondes() != 0) {
                hFin += fin.getMinutes() + "m";
                hFin += fin.getSecondes() + "s";
            } else if (fin.getMinutes() != 0) {
                hFin += fin.getMinutes() + "m";
            }
            tab[i][3] = "" + hDebut + "  " + hFin;

            tab[i][4] = "" + ((livraison.getHoraireEffectif() == null) ? "inconnue" : livraison.getHoraireEffectif().toString());
            tab[i][5] = "   ";
            i++;
        }

        return tab;
    }

    /**
     * Suppression de l'intersection et/ou de la livraison pointée
     */
    void razSelectionVue() {
        this.livraisonPointee = null;
        this.numLignePointee = -1;
    }

    /**
     * Methode appelee par les objets observes par this a chaque fois qu'ils ont
     * ete modifies
     *
     * @param o
     * @param arg Argument qui peut être passé dans le Notify
     */
    @Override
    public void update(Observable o, Object arg) {
        if (arg != null) { // arg est une forme qui vient d'etre ajoutee a plan
            if (arg instanceof Livraison) {
                this.livraisonPointee = (Livraison) arg;
            } else {
                this.livraisonPointee = null;
            }
        }
        if (fenetre.facadeVue.getPlan() == null || fenetre.facadeVue.getTournee() == null && fenetre.facadeVue.getDemandeLivraison() == null) {
            faireTable(true);
        } else {
            faireTable(false);
        }
    }

}
