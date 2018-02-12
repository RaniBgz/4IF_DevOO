package modele;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

/**
 * Représente la demande de livraisons.<p>
 * C'est une liste ordonnées de fenêtres de livraisons qui contiennent les
 * livraisons.<br>
 * La demande de livraisons connaît l'entrepot.
 */
public class DemandeLivraisons {

    private final Collection<FenLivraisons> fenetresLivraisons;
    private PointDePassage entrepot;

    private static final Comparator<FenLivraisons> COMPARATEUR_FENETRES
            = new Comparator<FenLivraisons>() {
                @Override
                public int compare(FenLivraisons f1, FenLivraisons f2) {
                    return f1.getDebut().compareTo(f2.getDebut());
                }
            };

    public DemandeLivraisons() {
        fenetresLivraisons = new TreeSet<>(COMPARATEUR_FENETRES);
    }

    /**
     * Ajoute une fenêtre de livraisons.<p>
     * Les fenêtres sont automatiquement triées.
     * 
     * @param fenLivraisons
     */
    public void ajouterFenLivraisons(FenLivraisons fenLivraisons) {
        fenetresLivraisons.add(fenLivraisons);
    }

    /**
     * Retourne une liste des fenêtres de livraisons triées chronologiquement.
     * <p>
     * Modifier cette liste ne modiefiera pas la liste interne de la
     * {@link FenLivraisons}.
     * 
     * @return la liste des fenêtres de livraisons
     */
    public List<FenLivraisons> getFenLivraisons() {
        return new ArrayList<>(fenetresLivraisons);
    }

    /**
     * Détermine l'entrepot.
     *
     * @param intersectionEntrepot l'intersection où est situé l'entrepot.
     */
    public void setEntrepot(final Intersection intersectionEntrepot) {
        entrepot = new PointDePassage() {
            @Override
            public Intersection getIntersection() {
                return intersectionEntrepot;
            }

            @Override
            public int getIdGenere() {
                return 0;
            }

            @Override
            public int getRetard() {
                return 0;
            }

            @Override
            public int getAttente() {
                return 0;
            }

            //TODO
            @Override
            public Horaire getHoraireEffectif() {
                return new Horaire(8, 0, 0);
            }
        };
    }

    /**
     * Renvoie l'intersection où est situé l'entrepot.
     * @return l'intersection
     */
    public PointDePassage getEntrepot() {
        return entrepot;
    }

    /**
     * Renvoie si il existe déjà une livraison à l'intersection donnée.
     * @param intersection Intersection à testée
     * @return réponse à la question
     */
    public boolean adresseDejaLivree(Intersection intersection) {
        for (FenLivraisons fen : this.fenetresLivraisons) {
            for (Livraison livraison : fen.getLivraisons()) {
                if (livraison.getIntersection() == intersection) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 
     * @return la liste les Livraisons
     */
    public List<Livraison> getLivraisons() {
        List<Livraison> list = new ArrayList<>();
        for (FenLivraisons fen : this.fenetresLivraisons) {
            list.addAll(fen.getLivraisons());
        }
        return Collections.unmodifiableList(list);
    }

    /**
     * Récupérer la fenêtre de livraison d'une livraison
     * 
     * @param livraison Livraison dont on veut la fenêtre de livraison
     * @return La fenêtre de livraison qui la contient
     */
    public FenLivraisons getFenetreLivraisonsContenantLivraison(Livraison livraison) {
        for (FenLivraisons fen : this.fenetresLivraisons) {
            if (fen.getLivraisons().contains(livraison)) {
                return fen;
            }
        }
        return null;
    }

}
