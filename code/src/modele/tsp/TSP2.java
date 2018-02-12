package modele.tsp;

import java.util.Collection;
import java.util.Iterator;

/**
 * Implémentation plus performante que {@link TSP1}.
 * <p>
 * L'itérateur parcourt en premier les sommets les plus proches 
 * du sommet courant.<br>
 * La borne inférieure est égale à la somme des liens les plus courts 
 * entre chaque sommets non-vus et les sommets suivants.
 */
public class TSP2 extends TemplateTSP {

	@Override
	protected int bound(Integer sommetCourant, Collection<Integer> nonVus) {
		int bound = 0;
		
		bound += coutMinVersNonVus(sommetCourant, nonVus, false);
		
		for (Integer successeur : nonVus) {
			bound += coutMinVersNonVus(successeur, nonVus, true);
		}
		
		return bound;
	}

	@Override
	protected Iterator<Integer> iterator(Integer sommetCrt, Collection<Integer> nonVus, Graphe g) {
		return new IteratorPlusPres(sommetCrt, nonVus, g);
	}


	/**
	 * Trouve le coût minimal des liens sortants de sommetRegarde vers
	 * les autres sommets non-vus (dont l'entrepôt si avec entrepôt est vrai).
	 * @param sommetRegarde sommet à partir duquel regarder les coûts
	 * @param nonVus sommets vers lesquels regarder les liens.
	 * @param avecEntrepot vrai si le lien vers l'entrepôt doit être regardé.
	 * @return le coût du lien regardé le plus court.
	 */
	private int coutMinVersNonVus(Integer sommetRegarde, 
			Collection<Integer> nonVus, boolean avecEntrepot) {
		int coutMin = Integer.MAX_VALUE;
		
		for (Integer autreSommet : nonVus) {
			if (sommetRegarde != autreSommet) {
				coutMin = nouveauCoutMin(coutMin, sommetRegarde, autreSommet);
			}
		}
		
		if (avecEntrepot) {
			coutMin = nouveauCoutMin(coutMin, sommetRegarde, 0);
		}
		
		return coutMin;
	}
	
	/**
	 * Calcul le nouveau coût minimal en regardant un nouveau lien.
	 * @param coutMinCourant le coût minimal jusqu'alors
	 * @param sommetDepart le sommet de départ du lien à regarder
	 * @param sommetArrivee le sommet d'arrivée du lien à regarder
	 * @return le nouveau coût minimal. Si le lien existe et que son coût
	 * est inférieur à coutMinCourant, c'est ce coût qui est retenu.
	 * Sinon, coutMinCourant est retourné.
	 */
	private int nouveauCoutMin(int coutMinCourant, Integer sommetDepart, 
					Integer sommetArrivee) {
		int coutMin = coutMinCourant;
		
		if (g.estArc(sommetDepart, sommetArrivee)) {
			coutMin = Math.min(coutMinCourant, 
					g.getCout(sommetDepart, sommetArrivee));
		}
		
		return coutMin;
	}
}
