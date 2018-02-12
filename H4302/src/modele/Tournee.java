package modele;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import modele.tsp.TSP;
import modele.tsp.TSP2;

/**
 * Représente une tournée calculée.
 */
public class Tournee {
    
    private final List<Livraison> livraisons;
    private final List<Itineraire> itineraires;
    private final TSP tsp = new TSP2();
    private final List<FenLivraisons> fenetresLivraisons;
    private final PointDePassage entrepot;
    private final Map<Integer, Livraison> livraisonsParId;
    /**
     * Durée d'un arrêt en minutes.
     */
    private static final int DUREE_ARRET = 10;
    
    /**
     * Temps limite pour le calcul de TSP en millisecondes.
     */
    private static final int TEMPS_LIMITE_TSP = 10000;
    
    /**
     * constructeur
     * Calcule et crée une tournée à partir d'une demande de livraison.
     */
    Tournee(DemandeLivraisons demande) {
        livraisons = new ArrayList<>();
        itineraires = new ArrayList<>();
        
        fenetresLivraisons = new ArrayList<>(demande.getFenLivraisons());
        entrepot = demande.getEntrepot();
        
        livraisonsParId = new HashMap<Integer, Livraison>();
        for (FenLivraisons fen : fenetresLivraisons) {
            for (Livraison l : fen.getLivraisons()) {
                livraisonsParId.put(l.getIdGenere(), l);
            }
        }
        
        GraphePlusCourtsItineraires graphe = new GraphePlusCourtsItineraires(demande);
        tsp.chercheSolution(TEMPS_LIMITE_TSP, graphe);
        
        int precId = 0;
        
        for (int i = 1; i < graphe.getNbSommets(); i++) {
            int idLivraison = tsp.getSolution(i);
            
            itineraires.add(graphe.getPlusCourtItineraire(precId, idLivraison));
            livraisons.add(livraisonsParId.get(idLivraison));
            precId = idLivraison;
        }
        
        itineraires.add(graphe.getPlusCourtItineraire(precId, 0));
        
        calculerTempsEffectifs();
    }
    
    /**
     * @return la liste des itinéraires pour cette tournée.
     */
    public List<Itineraire> getItineraires() {
        return Collections.unmodifiableList(itineraires);
    }
    
    /**
     * Ajoute une livraison après une autre et met à jour la tournée.
     * <p>
     * La nouvelle livraison va appartenir à la même fenêtre horaire.
     * @param pointPrecedent le point de passage précédent la nouvelle livraison.
     * @param nouvelleLivraison la livraison à ajouter
     */
    protected void ajouterLivraison(PointDePassage pointPrecedent,
            Livraison nouvelleLivraison) {
        FenLivraisons fenAjout = null;
        
        if (pointPrecedent.getIdGenere() == 0) {
            // entrepot : première fenêtre
            fenAjout = fenetresLivraisons.get(0);
        } else {
            for (FenLivraisons fen : fenetresLivraisons) {
                if (fen.getLivraisons().contains(pointPrecedent)) {
                    fenAjout = fen;
                    break;
                }
            }
        }
        
        ajouterLivraisonDansFenetre(pointPrecedent, nouvelleLivraison, fenAjout);
    }
    
    /**
     * Ajoute une livraison après un autre point de passage et dans la fenêtre
     * donnée.<br>
     * Met à jour la tournée.
     *
     * @param pointPrecedent le point de passage précédent la nouvelle livraison.
     * @param nouvelleLivraison la livraison à ajouter
     * @param fenAjout la fenêtre de livraison qui contiendra la nouvelle livraison.
     *
     * @throws IllegalStateException si la livraison ne peut pas être juste
     * derrière le point précédent et être dans la fenêtre donnée.
     */
    protected void ajouterLivraisonDansFenetre(PointDePassage pointPrecedent, Livraison nouvelleLivraison, FenLivraisons fenAjout) {
        if (fenAjout == null) {
            throw new NullPointerException("la fenêtre d'ajout est null");
        }
        
        int index = 0;
        if (pointPrecedent.getIdGenere() > 0) {
            // pas entrepot -> donc une livraison
            index = livraisons.indexOf(pointPrecedent) + 1;
        }
        
        int indexFenAjout = fenetresLivraisons.indexOf(fenAjout);
        // on vérifie la possibilité de l'action
        if (index > 0) {
            FenLivraisons fenPointPrec = getFenetreLivraisonsContenantLivraison(
                    (Livraison) pointPrecedent);
            if (indexFenAjout < fenetresLivraisons.indexOf(fenPointPrec)) {
                throw new IllegalStateException("Tentative d'insérer une livraison "
                        + "dans une fenêtre précédente à celle du point d'ajout");
            }
        }
        
        if (index < livraisons.size()) {
            Livraison livSuiv = livraisons.get(index);
            FenLivraisons fenPointSuiv = getFenetreLivraisonsContenantLivraison(
                    livSuiv);
            if (indexFenAjout > fenetresLivraisons.indexOf(fenPointSuiv)) {
                throw new IllegalStateException("Tentative d'insérer une livraison "
                        + "dans une fenêtre suivante à celle de la livraison suivant "
                        + "le point d'ajout");
            }
        }
        
        // On ajoute vraiment
        fenAjout.ajouterLivraison(nouvelleLivraison);
        PointDePassage pointSuivant = index < livraisons.size()
                ? livraisons.get(index) : entrepot;
        
        livraisons.add(index, nouvelleLivraison);
        livraisonsParId.put(nouvelleLivraison.getIdGenere(), nouvelleLivraison);
        
        itineraires.remove(index);
        
        itineraires.add(index,
                GraphePlusCourtsItineraires.calculePlusCourtCheminEntreDeuxPoints(
                        nouvelleLivraison, pointSuivant));
        itineraires.add(index,
                GraphePlusCourtsItineraires.calculePlusCourtCheminEntreDeuxPoints(
                        pointPrecedent, nouvelleLivraison));
        
        calculerTempsEffectifs();
    }
    
    /**
     * Supprime une livraison et met à jour la tournée.
     *
     * @param livraisonASupprimer la livraison qui sera supprimée
     * @return La {@link FenLivraisons} qui contenait la livraison supprimée.
     * (utile) en cas d'annulation.
     */
    protected FenLivraisons supprimerLivraison(Livraison livraisonASupprimer) {
        FenLivraisons fen = null;
        for (FenLivraisons f : fenetresLivraisons) {
            if (f.getLivraisons().contains(livraisonASupprimer)) {
                fen = f;
                break;
            }
        }
        
        if (fen == null) {
            throw new IllegalStateException("Aucune fenêtre ne contient la Livraison "
                    + livraisonASupprimer);
        }
        
        fen.supprimerLivraison(livraisonASupprimer);
        
        int index = livraisons.indexOf(livraisonASupprimer);
        
        
            PointDePassage pointPrecedent = index > 0
                    ? livraisons.get(index - 1) : entrepot;
            PointDePassage pointSuivant = index < livraisons.size() - 1
                    ? livraisons.get(index + 1) : entrepot;
            
            livraisons.remove(livraisonASupprimer);
            livraisonsParId.remove(livraisonASupprimer);
            itineraires.remove(index);
            itineraires.remove(index);
            
            itineraires.add(index,
                    GraphePlusCourtsItineraires.calculePlusCourtCheminEntreDeuxPoints(
                            pointPrecedent, pointSuivant));
            
            calculerTempsEffectifs();
        
        
        
        return fen;
    }
    
    /**
     * Echange deux livraisons.
     * Les fenêtres horaires de ces livraisons sont également échangées. La
     * tournée est mise à jour.
     * @param livraison1 la première livrasion à échanger
     * @param livraison2 la deuxième livraison à échanger
     */
    protected void echangerLivraisons(Livraison livraison1, Livraison livraison2) {
        
        FenLivraisons fen1 = null;
        FenLivraisons fen2 = null;
        
        for (FenLivraisons f : fenetresLivraisons) {
            if (f.getLivraisons().contains(livraison1)) {
                fen1 = f;
            }
            if (f.getLivraisons().contains(livraison2)) {
                fen2 = f;
            }
            if (fen1 != null && fen2 != null) {
                break;
            }
        }
        
        // switch fenêtres
        if (fen1 != fen2) {
            fen1.supprimerLivraison(livraison1);
            fen2.supprimerLivraison(livraison2);
            fen1.ajouterLivraison(livraison2);
            fen2.ajouterLivraison(livraison1);
        }
        
        int index1 = livraisons.indexOf(livraison1);
        int index2 = livraisons.indexOf(livraison2);
        
        livraisons.set(index1, livraison2);
        livraisons.set(index2, livraison1);
        
        PointDePassage pointPrecedent1 = index2 > 0
                ? livraisons.get(index2 - 1) : entrepot;
        PointDePassage pointSuivant1 = index2 < livraisons.size() - 1
                ? livraisons.get(index2 + 1) : entrepot;
        
        PointDePassage pointPrecedent2 = index1 > 0
                ? livraisons.get(index1 - 1) : entrepot;
        PointDePassage pointSuivant2 = index1 < livraisons.size() - 1
                ? livraisons.get(index1 + 1) : entrepot;
        
        itineraires.set(index2,
                GraphePlusCourtsItineraires.calculePlusCourtCheminEntreDeuxPoints(
                        pointPrecedent1, livraison1));
        itineraires.set(index2 + 1,
                GraphePlusCourtsItineraires.calculePlusCourtCheminEntreDeuxPoints(
                        livraison1, pointSuivant1));
        
        itineraires.set(index1,
                GraphePlusCourtsItineraires.calculePlusCourtCheminEntreDeuxPoints(
                        pointPrecedent2, livraison2));
        itineraires.set(index1 + 1,
                GraphePlusCourtsItineraires.calculePlusCourtCheminEntreDeuxPoints(
                        livraison2, pointSuivant2));
        
        calculerTempsEffectifs();
    }
    
    /**
     * @return la liste des fenetres de livraisons.
     */
    public List<FenLivraisons> getFenetresLivraisons() {
        return Collections.unmodifiableList(fenetresLivraisons);
    }
    
    /**
     * @return le point de passage de l'entrepot.
     */
    public PointDePassage getEntrepot() {
        return entrepot;
    }
    
    /**
     * @return la liste des livraisons dans l'ordre de passage.
     */
    public List<Livraison> getLivraisons() {
        return Collections.unmodifiableList(livraisons);
    }
    
    /**
     * @param livraison
     * @return la fenêtre de livraisons contenant la livraison donnée en
     * argument.
     */
    public FenLivraisons getFenetreLivraisonsContenantLivraison(Livraison livraison) {
        FenLivraisons res = null;
        for (FenLivraisons f : fenetresLivraisons) {
            if (f.getLivraisons().contains(livraison)) {
                res = f;
                break;
            }
        }
        
        return res;
    }
    
    /**
     * Calcule et enregistre les temps effectifs dans les {@link Livraison}. Met
     * également à jour le retard et l'attente
     */
    private void calculerTempsEffectifs() {
        Horaire hCurr = getHoraireDepart();
        
        final int nbLivraisons = livraisons.size();
        for (int i = 0; i < nbLivraisons; i++) {
            Itineraire iti = itineraires.get(i);
            Livraison liv = livraisons.get(i);
            FenLivraisons fen = getFenetreLivraisonsContenantLivraison(liv);
            
            hCurr.ajouterSecondes(iti.getDuree());
            if (hCurr.compareTo(fen.getDebut()) < 0) {
            	liv.setAttente(fen.getDebut().diffSecondes(hCurr));
                hCurr = fen.getDebut().copy();
            }
            liv.setHoraireEffectif(hCurr);
            
            int diff = hCurr.diffSecondes(fen.getFin());
            liv.setRetard(Math.max(0, diff));
            
            hCurr.ajouterMinutes(DUREE_ARRET);
        }
    }
    
    /**
     * Retourne une copie de l'horaire de départ.
     * @return horaire
     */
    private static Horaire getHoraireDepart() {
        return new Horaire(8, 0, 0);
    }
    
}
