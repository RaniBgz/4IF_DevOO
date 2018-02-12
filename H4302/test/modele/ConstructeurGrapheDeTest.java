package modele;

import java.util.ArrayList;
import java.util.List;

/**
 * Construit le graphe de test.<br>
 *
 * <strong>NE PAS TOUCHER OU TOUS LES TESTS VONT FOIRER !!!</strong>
 */
public final class ConstructeurGrapheDeTest {
	
	private ConstructeurGrapheDeTest() {
		
	}

	/**
	 * Créer une {@link DemandeLivraisons}.
	 * <p>
	 * Le graphe de la ville est aussi créé (et donc accessible par les Intersections)
	 */
	static DemandeLivraisons creerDemandeLivraisonAvecPlan() {
		Livraison.resetIdGenerateur();
		
		List<Intersection> intersections = creerIntersections();
		
		DemandeLivraisons dem = new DemandeLivraisons();
		
		dem.setEntrepot(intersections.get(7));
		
		FenLivraisons fen1 = new FenLivraisons(new Horaire(8, 0, 0), 
				new Horaire(11, 24, 30));
		fen1.ajouterLivraison(new Livraison(intersections.get(0), 1, 258));
		fen1.ajouterLivraison(new Livraison(intersections.get(3), 2, 258));
		
		FenLivraisons fen2 = new FenLivraisons(new Horaire(12, 0, 0), 
				new Horaire(18, 0, 0));
		fen2.ajouterLivraison(new Livraison(intersections.get(4), 3, 258));
		fen2.ajouterLivraison(new Livraison(intersections.get(6), 4, 258));
		
		dem.ajouterFenLivraisons(fen1);
		dem.ajouterFenLivraisons(fen2);
		
		return dem;
	}
	
	private static List<Intersection> creerIntersections() {
		final int DIM = 3;
		List<Intersection> intersections = new ArrayList<>();
		for (int i = 0; i < DIM*DIM; i++) {
			Intersection inter = new Intersection(i % DIM, i/DIM, i);
			intersections.add(inter);
		}
		
		ajouterTroncon(intersections, 0, 1, 1);
		ajouterTroncon(intersections, 0, 3, 1);
		ajouterTroncon(intersections, 1, 2, 3);
		ajouterTroncon(intersections, 1, 0, 4);
		ajouterTroncon(intersections, 3, 4, 6);
		ajouterTroncon(intersections, 3, 6, 1);
		ajouterTroncon(intersections, 3, 7, 2);
		ajouterTroncon(intersections, 6, 3, 9);
		ajouterTroncon(intersections, 7, 8, 1);
		ajouterTroncon(intersections, 8, 5, 1);
		ajouterTroncon(intersections, 5, 4, 1);
		ajouterTroncon(intersections, 4, 1, 3);
		ajouterTroncon(intersections, 2, 5, 1);
		ajouterTroncon(intersections, 5, 7, 5);
		ajouterTroncon(intersections, 6, 4, 5);
		
		return intersections;
	}
	
	private static void ajouterTroncon(List<Intersection> intersections, 
			int i1, int i2, float dist) {
		Troncon troncon = new Troncon(i1+"->"+i2, dist, 1, intersections.get(i2));
		intersections.get(i1).ajouterTronconSortant(troncon);
	}
}
