package modele.tsp;

public class RunTSP {
	
	public static void main(String[] args) {
		TSP tsp1 = new TSP1();
		TSP tsp2 = new TSP2();
		
		for (int nbSommets = 8; nbSommets <= 20; nbSommets += 2){
			//testTSP(tsp1, nbSommets, "TSP1");
			testTSP(tsp2, nbSommets, "TSP2");
		}
	}
	
	private static void testTSP(TSP tsp, int nbSommets, String nomTSP) {
		System.out.println("Graphes de "+nbSommets+" sommets pour " + nomTSP + " :");
		Graphe g = new GrapheComplet(nbSommets);
		long tempsDebut = System.currentTimeMillis();
		tsp.chercheSolution(60000, g);
		System.out.print("Solution de longueur "+tsp.getCoutSolution()+" trouvee en "
				+(System.currentTimeMillis() - tempsDebut)+"ms : ");
		for (int i=0; i<nbSommets; i++) {
			System.out.print(tsp.getSolution(i)+" ");
		}
		System.out.println();
	}
}
