package modele;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

public class TourneeTest {

	@Test
	public void tourneeBienConstruite() {
		Tournee t = new Tournee(
				ConstructeurGrapheDeTest.creerDemandeLivraisonAvecPlan());
		
		// bien construite
		assertEquals(7, t.getEntrepot().getIntersection().getId());
		assertEquals(2, t.getFenetresLivraisons().size());
		
		// Vérifier itinéraires
		// - nombre
		assertEquals(5, t.getItineraires().size());
		// - ordre
		assertEquals(7, t.getItineraires().get(0).getDepart().getIntersection().getId());
		assertEquals(0, t.getItineraires().get(1).getDepart().getIntersection().getId());
		assertEquals(3, t.getItineraires().get(2).getDepart().getIntersection().getId());
		assertEquals(6, t.getItineraires().get(3).getDepart().getIntersection().getId());
		assertEquals(4, t.getItineraires().get(4).getDepart().getIntersection().getId());
		
		// Les itinéraires sont normalement bien construits (testé dans
		// GraphePlusCourtsItinerairesTest)
		
		// Vérifier ordre Livraisons
		List<Livraison> liv = t.getLivraisons();
		assertEquals(4, liv.size());
		assertEquals(1, liv.get(0).getIdGenere());
		assertEquals(2, liv.get(1).getIdGenere());
		assertEquals(4, liv.get(2).getIdGenere());
		assertEquals(3, liv.get(3).getIdGenere());
	}
	
	@Test
	public void suppression() {
		Tournee t = new Tournee(
				ConstructeurGrapheDeTest.creerDemandeLivraisonAvecPlan());
		
		Livraison livASuppr = t.getLivraisons().get(2);
		t.supprimerLivraison(livASuppr);
		
		// livraisons restantes OK
		List<Livraison> livs = t.getLivraisons();
		assertEquals(3, livs.size());
		assertEquals(1, livs.get(0).getIdGenere());
		assertEquals(2, livs.get(1).getIdGenere());
		assertEquals(3, livs.get(2).getIdGenere());
		
		// itinéraires bien mis à jours
		List<Itineraire> itis = t.getItineraires();
		assertEquals(4, itis.size());
		
		Itineraire itiModifie = itis.get(2);
		assertEquals(5, itiModifie.getDuree());
		assertEquals(3, itiModifie.getDepart().getIntersection().getId());
		assertEquals(4, itiModifie.getArrivee().getIntersection().getId());
		
		List<Troncon> tr = itiModifie.getTroncons();
		assertEquals(4, tr.size());
		
		String[] names = {"3->7", "7->8", "8->5", "5->4"};
		for (int i = 0; i < 4; i++) {
			assertEquals(names[i], tr.get(i).getNom());
		}
	}

	@Test
	public void ajout() {
		Tournee t = new Tournee(
				ConstructeurGrapheDeTest.creerDemandeLivraisonAvecPlan());
		// intersection 8 (2,2)
		Intersection inter = t.getEntrepot().getIntersection().
				getTronconsSortants().iterator().next().getArrivee();

		Livraison nouvelleLivraison = new Livraison(inter, 5, 758);
		Livraison livPrec = t.getLivraisons().get(0);
		t.ajouterLivraison(livPrec, nouvelleLivraison);
		
		// livraisons OK
		List<Livraison> livs = t.getLivraisons();
		assertEquals(5, livs.size());
		assertEquals(1, livs.get(0).getIdGenere());
		assertEquals(5, livs.get(1).getIdGenere());
		assertEquals(2, livs.get(2).getIdGenere());
		assertEquals(4, livs.get(3).getIdGenere());
		assertEquals(3, livs.get(4).getIdGenere());
		
		// ajoutée à la bonne fenêtre
		FenLivraisons fen = t.getFenetresLivraisons().get(0);
		assertTrue(fen.getLivraisons().contains(nouvelleLivraison));
		
		// itinéraires OK
		assertEquals(6, t.getItineraires().size());
		// - avant la nouvelle livraison
		Itineraire itiModifie1 = t.getItineraires().get(1);
		assertEquals(4, itiModifie1.getDuree());
		assertEquals(0, itiModifie1.getDepart().getIntersection().getId());
		assertEquals(8, itiModifie1.getArrivee().getIntersection().getId());
		
		List<Troncon> tr1 = itiModifie1.getTroncons();
		assertEquals(3, tr1.size());
		
		String[] names1 = {"0->3", "3->7", "7->8"};
		for (int i = 0; i < 3; i++) {
			assertEquals(names1[i], tr1.get(i).getNom());
		}
		// - après la nouvelle livraison
		Itineraire itiModifie2 = t.getItineraires().get(2);
		assertEquals(10, itiModifie2.getDuree());
		assertEquals(8, itiModifie2.getDepart().getIntersection().getId());
		assertEquals(3, itiModifie2.getArrivee().getIntersection().getId());
		
		List<Troncon> tr2 = itiModifie2.getTroncons();
		assertEquals(5, tr2.size());
		
		String[] names2 = {"8->5", "5->4", "4->1", "1->0", "0->3"};
		for (int i = 0; i < 5; i++) {
			assertEquals(names2[i], tr2.get(i).getNom());
		}
	}
	
	@Test 
	public void ajoutApresEntrepot() {
		Tournee t = new Tournee(
				ConstructeurGrapheDeTest.creerDemandeLivraisonAvecPlan());
		// intersection 8 (2,2)
		Intersection inter = t.getEntrepot().getIntersection().
				getTronconsSortants().iterator().next().getArrivee();

		Livraison nouvelleLivraison = new Livraison(inter, 5, 758);
		t.ajouterLivraison(t.getEntrepot(), nouvelleLivraison);
		
		// livraisons OK
		List<Livraison> livs = t.getLivraisons();
		assertEquals(5, livs.size());
		assertEquals(5, livs.get(0).getIdGenere());
		assertEquals(1, livs.get(1).getIdGenere());
		assertEquals(2, livs.get(2).getIdGenere());
		assertEquals(4, livs.get(3).getIdGenere());
		assertEquals(3, livs.get(4).getIdGenere());
	}
	
	@Test
	public void echange() {
		Tournee t = new Tournee(
				ConstructeurGrapheDeTest.creerDemandeLivraisonAvecPlan());
		Livraison liv1 = t.getLivraisons().get(1);
		Livraison liv2 = t.getLivraisons().get(2);
		t.echangerLivraisons(liv1, liv2);
		
		// livraisons OK
		List<Livraison> livs = t.getLivraisons();
		assertEquals(4, livs.size());
		assertEquals(1, livs.get(0).getIdGenere());
		assertEquals(4, livs.get(1).getIdGenere());
		assertEquals(2, livs.get(2).getIdGenere());
		assertEquals(3, livs.get(3).getIdGenere());
		
		// fenêtres échangées
		assertTrue(t.getFenetresLivraisons().get(0).getLivraisons().contains(liv2));
		assertTrue(t.getFenetresLivraisons().get(1).getLivraisons().contains(liv1));
		assertFalse(t.getFenetresLivraisons().get(0).getLivraisons().contains(liv1));
		assertFalse(t.getFenetresLivraisons().get(1).getLivraisons().contains(liv2));
		
		// itinéraires OK
		assertEquals(5, t.getItineraires().size());
		//  - avant Livraison 4
		Itineraire itiModifie1 = t.getItineraires().get(1);
		assertEquals(2, itiModifie1.getDuree());
		assertEquals(0, itiModifie1.getDepart().getIntersection().getId());
		assertEquals(6, itiModifie1.getArrivee().getIntersection().getId());
		
		List<Troncon> tr1 = itiModifie1.getTroncons();
		assertEquals(2, tr1.size());
		
		String[] names1 = {"0->3", "3->6"};
		for (int i = 0; i < 2; i++) {
			assertEquals(names1[i], tr1.get(i).getNom());
		}
		//  - entre Livraisons 4 et 2
		Itineraire itiModifie2 = t.getItineraires().get(2);
		assertEquals(9, itiModifie2.getDuree());
		assertEquals(6, itiModifie2.getDepart().getIntersection().getId());
		assertEquals(3, itiModifie2.getArrivee().getIntersection().getId());
		
		List<Troncon> tr2 = itiModifie2.getTroncons();
		assertEquals(1, tr2.size());
		
		String[] names2 = {"6->3"};
		for (int i = 0; i < 1; i++) {
			assertEquals(names2[i], tr2.get(i).getNom());
		}
		//  - après la livraison 2
		Itineraire itiModifie3 = t.getItineraires().get(3);
		assertEquals(5, itiModifie3.getDuree());
		assertEquals(3, itiModifie3.getDepart().getIntersection().getId());
		assertEquals(4, itiModifie3.getArrivee().getIntersection().getId());
		
		List<Troncon> tr3 = itiModifie3.getTroncons();
		assertEquals(4, tr3.size());
		
		String[] names3 = {"3->7","7->8","8->5","5->4"};
		for (int i = 0; i < 4; i++) {
			assertEquals(names3[i], tr3.get(i).getNom());
		}
	}
}
