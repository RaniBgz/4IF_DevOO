package modele;

import static org.junit.Assert.assertEquals;

import org.junit.Test;


public class HoraireTest {

	@Test
	public void affichage() {
		Horaire h = new Horaire(20,1,22);
		assertEquals("20:01:22", h.toString());
	}
	
	@Test
	public void valeursValides() {
		Horaire h = new Horaire(4000,1,0);
		assertEquals(0, h.getSecondes());
		assertEquals(1, h.getMinutes());
		assertEquals(16, h.getHeures());
		
		Horaire h2 = new Horaire(20,310,75);
		assertEquals(15, h2.getSecondes());
		assertEquals(11, h2.getMinutes());
		assertEquals(1, h2.getHeures());
	}

	@Test
	public void difference() {
		Horaire h1 = new Horaire(15,10,20);
		Horaire h2 = new Horaire(10,50,15);
		Horaire h3 = Horaire.difference(h1, h2);
		assertEquals(5, h3.getSecondes());
		assertEquals(20, h3.getMinutes());
		assertEquals(4, h3.getHeures());
	}
	
	@Test
	public void diffSecondes() {
		Horaire h1 = new Horaire(15,10,20);
		Horaire h2 = new Horaire(14,50,15);
		assertEquals(1205, h1.diffSecondes(h2));
	}
}
