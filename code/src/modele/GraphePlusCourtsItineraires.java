package modele;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

import modele.tsp.Graphe;

/**
 * Représente le graphe des plus courts itinéraires entre les points de passage
 * lors du calcul de la tournée.
 */
public class GraphePlusCourtsItineraires implements Graphe {

    private int nbSommets;

    /**
     * matrice des itineraires entre tous les points de passage. 0 est
     * l'entrepot. Les autres index sont les noClient des livraisons.
     */
    private final Itineraire[][] itineraires;

    /**
     * Calcule le graphe des plus courts itinéraires à partir d'une demande de
     * livraison.
     */
    GraphePlusCourtsItineraires(DemandeLivraisons demandeLivraisons) {

        nbSommets = 1; // entrepot
        for (FenLivraisons fen : demandeLivraisons.getFenLivraisons()) {
            nbSommets += fen.getLivraisons().size();
        }
        itineraires = new Itineraire[nbSommets][nbSommets];

        Collection<PointDePassage> listeEntrepot = new ArrayList<>();
        listeEntrepot.add(demandeLivraisons.getEntrepot());

        List<FenLivraisons> fenetres = demandeLivraisons.getFenLivraisons();

        Collection<? extends PointDePassage> listePrec = listeEntrepot;
        Collection<? extends PointDePassage> listeCourante;

        for (int i = 0; i < fenetres.size(); i++) {
            listeCourante = demandeLivraisons.getFenLivraisons().get(i).getLivraisons();
            relierSousGraphes(listePrec, listeCourante);
            listePrec = listeCourante;
        }

        relierSousGraphes(listePrec, listeEntrepot);
    }

    @Override
    public int getNbSommets() {
        return nbSommets;
    }

    @Override
    public int getCout(int i, int j) {
        return itineraires[i][j].getDuree();
    }

    @Override
    public boolean estArc(int i, int j) {
        return itineraires[i][j] != null;
    }

    /**
     * Retourne l'itinéraire le plus court pour aller du sommet i au sommet j.
     * <p>
     * Les indices sont égaux aux ID générés des points de passage.
     *
     * @param i Sommet de départ
     * @param j Sommet d'arrivée
     * @return l'itinéraire 
     */
    Itineraire getPlusCourtItineraire(int i, int j) {
        return itineraires[i][j];
    }

    /**
     * Relie les points de deux fenêtres consécutives.
     *
     * @param grapheConnexe : la première fenêtre, tous ses noeuds vont être
     * reliés entre eux.
     * @param grapheSuivant : la deuxième fenêtre, les noeuds de la première
     * fenêtre vont être reliés avec ceux de la deuxième.
     */
    private void relierSousGraphes(Collection<? extends PointDePassage> grapheConnexe,  Collection<? extends PointDePassage> grapheSuivant) {
        for (PointDePassage i : grapheConnexe) {
            Collection<PointDePassage> arrivees = new HashSet<>(grapheConnexe);
            arrivees.remove(i);
            arrivees.addAll(grapheSuivant);

            Map<PointDePassage, Itineraire> resultat
                    = calculeItinerairesDepuisUnPoint(i, arrivees);
            int idDepart = i.getIdGenere();
            for (Map.Entry<PointDePassage, Itineraire> entry : resultat.entrySet()) {
                int idArrivee = entry.getKey().getIdGenere();
                itineraires[idDepart][idArrivee] = entry.getValue();
            }
        }
    }

    /**
     * Applique un Dijkstra sur l'intersection de départ donnée et renvoie tous
     * les {@link Itineraire} pour les intersections arrivees. <br>
     * Il n'y a pas d'entrée pour les arrivées pour lesquelles aucun itinéraires
     * n'est trouvé.
     *
     * @param depart Intersection de départ 
     * @param arrivees Liste des Intersection donc ton vas vouloir un résultat
     * @return Les itinéraires trouvés
     */
    private static Map<PointDePassage, Itineraire> calculeItinerairesDepuisUnPoint( PointDePassage depart, Collection<PointDePassage> arrivees) {
        Collection<Intersection> arriveesACalculer = new HashSet<>();

        Collection<Intersection> noirs = new HashSet<>();
        final Map<Intersection, Integer> couts = new HashMap<>();
        final Map<Intersection, List<Troncon>> cheminsCalcules = new HashMap<>();

        // Initialisation depart
        couts.put(depart.getIntersection(), 0);
        cheminsCalcules.put(depart.getIntersection(), new ArrayList<Troncon>());

        // Initialisation : cout "infini" et calcul instantané des
        // itineraires à la même intersection.
        for (PointDePassage arriveePoint : arrivees) {
            Intersection arrivee = arriveePoint.getIntersection();
            if (depart.equals(arrivee)) {
                cheminsCalcules.put(arrivee, new ArrayList<Troncon>());
                couts.put(arrivee, 0);
            } else {
                couts.put(arrivee, Integer.MAX_VALUE);
                arriveesACalculer.add(arrivee);
            }
        }

        // Comparateur pour la file de priorité de l'algorithme Dijkstra
        Comparator<Intersection> comparator = new Comparator<Intersection>() {
            @Override
            public int compare(Intersection o1, Intersection o2) {
                return couts.get(o1) - couts.get(o2);
            }
        };
        Queue<Intersection> gris = new PriorityQueue<>(10, comparator);
        gris.add(depart.getIntersection());

        // ********************** ALGORITHME ********************
        while (!arriveesACalculer.isEmpty() && !gris.isEmpty()) {
            Intersection noeudEtudie = gris.poll();
            int coutNoeud = couts.get(noeudEtudie);

            for (Troncon tronconSortant : noeudEtudie.getTronconsSortants()) {
                Intersection successeur = tronconSortant.getArrivee();

                if (!noirs.contains(successeur)) {
                    int nouveauCout = coutNoeud + tronconSortant.getDuree();

                    if (!couts.containsKey(successeur) || nouveauCout < couts.get(successeur)) {
                        couts.put(successeur, nouveauCout);

                        List<Troncon> nouveauChemin = new ArrayList<>(cheminsCalcules.get(noeudEtudie));
                        nouveauChemin.add(tronconSortant);
                        cheminsCalcules.put(successeur, nouveauChemin);
                    }

                    if (!gris.contains(successeur)) {
                        gris.add(successeur);
                    }
                }
            }

            noirs.add(noeudEtudie);
            arriveesACalculer.remove(noeudEtudie);
        }
		// ******************* FIN ALGORITHME *******************

        // On crée les itineraires lorsqu'un chemin a été trouvé.
        Map<PointDePassage, Itineraire> resultats = new HashMap<>();

        for (PointDePassage arriveePoint : arrivees) {
            Intersection arrivee = arriveePoint.getIntersection();
            if (cheminsCalcules.containsKey(arrivee)) {
                Itineraire itineraire = new Itineraire(depart,
                        arriveePoint, cheminsCalcules.get(arrivee), couts.get(arrivee));
                resultats.put(arriveePoint, itineraire);
            }
        }

        return resultats;
    }

    /**
     * Retourne un {@link Itineraire} représentant le plus court chemin du
     * {@link PointDePassage} p1 au p2.
     * <p>
     * @param p1 Point de passage de départ
     * @param p2 Poitn de passage d'arrivée
     * @return L'itinéraire calculer (null si il n'y a pas de chemin possible)
     */
    protected static Itineraire calculePlusCourtCheminEntreDeuxPoints( PointDePassage p1, PointDePassage p2) {
        Collection<PointDePassage> listeArrivees = new ArrayList<>();
        listeArrivees.add(p2);

        Map<PointDePassage, Itineraire> res
                = calculeItinerairesDepuisUnPoint(p1, listeArrivees);

        return res.get(p2);
    }
}
