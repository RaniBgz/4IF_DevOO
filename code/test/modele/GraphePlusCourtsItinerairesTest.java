package modele;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;


public class GraphePlusCourtsItinerairesTest {

	@Test
	public void test() {
		GraphePlusCourtsItineraires g = new GraphePlusCourtsItineraires(
				ConstructeurGrapheDeTest.creerDemandeLivraisonAvecPlan());
				
		// on vérife le nombre de sommets
		assertEquals(g.getNbSommets(), 5);
		
		// on vérifie les arcs
		assertTrue(g.estArc(0, 1));
		assertTrue(g.estArc(0, 2));
		assertTrue(g.estArc(2, 1));
		assertTrue(g.estArc(1, 2));
		assertTrue(g.estArc(1, 3));
		assertTrue(g.estArc(1, 4));
		assertTrue(g.estArc(2, 3));
		assertTrue(g.estArc(2, 4));
		assertTrue(g.estArc(4, 3));
		assertTrue(g.estArc(3, 4));
		assertTrue(g.estArc(3, 0));
		assertTrue(g.estArc(4, 0));
		assertFalse(g.estArc(0, 3));
		assertFalse(g.estArc(0, 4));
		assertFalse(g.estArc(1, 0));
		assertFalse(g.estArc(2, 0));
		assertFalse(g.estArc(3, 2));
		assertFalse(g.estArc(3, 1));
		assertFalse(g.estArc(4, 2));
		assertFalse(g.estArc(4, 1));
		
		// on vérifie les poids
		assertEquals(10, g.getCout(0, 1));
		assertEquals(11, g.getCout(0, 2));
		assertEquals(12, g.getCout(2, 1));
		assertEquals(1 , g.getCout(1, 2));
		assertEquals(6 , g.getCout(1, 3));
		assertEquals(2 , g.getCout(1, 4));
		assertEquals(5 , g.getCout(2, 3));
		assertEquals(1 , g.getCout(2, 4));
		assertEquals(5 , g.getCout(4, 3));
		assertEquals(9 , g.getCout(3, 4));
		assertEquals(10, g.getCout(3, 0));
		assertEquals(11, g.getCout(4, 0));
		
		// Itinéraire bien construit ?
		Itineraire iti = g.getPlusCourtItineraire(2, 3);
		assertEquals(5, iti.getDuree());
		assertEquals(4, iti.getTroncons().size());
		assertEquals(3, iti.getDepart().getIntersection().getId());
		assertEquals(4, iti.getArrivee().getIntersection().getId());
		
		String[] names = {"3->7", "7->8", "8->5", "5->4"};
		for (int i = 0; i < 4; i++) {
			assertEquals(names[i], iti.getTroncons().get(i).getNom());
		}
	}

	
	
	
}
