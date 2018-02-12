package modele.tsp;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Queue;


public class IteratorPlusPres implements Iterator<Integer> {
	
	private final Queue<Integer> nodesQueue;
	
	/**
	 * Un itérateur sur un {@link Graphe} qui parcours en premier les sommets 
	 * successeurs les plus près du sommet courant.
	 * @param sommetCrt indice du sommet courant
	 * @param nonVus liste des indices des sommets non encore visités
	 * @param g {@link Graphe} parcouru
	 */
	public IteratorPlusPres(final Integer sommetCrt, Collection<Integer> nonVus, final Graphe g) {
		Comparator<Integer> comparateur = new Comparator<Integer>() {
			@Override
			public int compare(Integer n1, Integer n2) {
				int cout1 = g.getCout(sommetCrt, n1);
				int cout2 = g.getCout(sommetCrt, n2);
				return cout2 - cout1;
			}
		};
		
		nodesQueue = new PriorityQueue<>(nonVus.size(), comparateur);
		for (Integer successeur : nonVus) {
			if (g.estArc(sommetCrt, successeur)) {
				nodesQueue.add(successeur);
			}
		}
	}

        /**
         * 
         * 
         * @return boolean si la liste est empty
         */
	@Override
	public boolean hasNext() {
		return !nodesQueue.isEmpty();
	}

        /**
         * 
         * @return le prochain élément
         */
	@Override
	public Integer next() {
		return nodesQueue.remove();
	}

        /**
         * On ne peut pas supprimer un sommet
         */
	@Override
	public void remove() {
		// On ne peut pas supprimer un sommet
	}

}
