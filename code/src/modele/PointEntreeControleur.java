package modele;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 * Façade avec le package contrôleur.
 */
public class PointEntreeControleur {

	private final GlobalObjects objects;
        private final PointEntreeVue facadeVue;

	/**
	 * Crée le point d'entrée contrôleur et le lie au point d'entrée de vue.
         * constructeur
        * @param pointEntreeVue
	 */
	public PointEntreeControleur(PointEntreeVue pointEntreeVue) {
		objects = pointEntreeVue.getGlobalObjects();
                this.facadeVue = pointEntreeVue;
	}
    /**
     * Donne le plan au modèle.<p>
     * La tournée et la demande de livraison sont supprimée.
     * @param plan : le plan chargé. Peut être null.
     */
    public void setPlan(Plan plan) {
        objects.setPlan(plan);
        objects.setTournee(null);
        facadeVue.notifyChanged(null);
    }

    /**
     * setter
     * Donne la demande de livraisons au modèle.<p>
     * La tournée est supprimée/
     * @param demande : la demande de livraisons chargée. Peut être null.
     */
    public void setDemandeLivraisons(DemandeLivraisons demande) {
    	objects.setTournee(null);
        objects.setDemandeLivraisons(demande);
        facadeVue.notifyChanged(null);
    }

    /**
     * Calcule la tournee.
     *
     */
    public void calculerTournee() {
        if (objects.getDemandeLivraisons() == null) {
            throw new IllegalStateException("calculerTournee() appelée "
                    + "alors que la demande de livraison est null");
        }
        objects.setTournee(new Tournee(objects.getDemandeLivraisons()));
        facadeVue.notifyChanged(null);
    }
    
    /**
     * @return le plan, ou null s'il n'est pas chargé.
     */
    public Plan getPlan() {
		return objects.getPlan();
	}
    
    /**
     * @return la tournée ou null si elle n'est pas calculée.
     */
    public Tournee getTournee() {
    	return objects.getTournee();
    }

    /**
     * Ajoute une livraison et met à jour la tournée.<p>
     * La livraison sera ajoutée dans la fenêtre du point précédent.
     * 
     * @param pointPrecedent : {@link PointDePassage} précédant la nouvelle livraison.
     * @param nouvelleLivraison : La nouvelle livraison à ajouter.
     * @throws IllegalStateException si la tournée n'est pas calculée.
     */
    public void ajouterLivraison(PointDePassage pointPrecedent, Livraison nouvelleLivraison) {
        objects.getTournee().ajouterLivraison(pointPrecedent, nouvelleLivraison);
    }

    /**
     * Ajoute une livraison dans la fenêtres de livraisons donnée et 
     * met à jour la tournée.
     * @param pointPrecedent : {@link PointDePassage} précédant la nouvelle livraison.
     * @param nouvelleLivraison : La nouvelle livraison à ajouter.
     * @param fenLivraison : La fenêtre qui contiendra la nouvelle livraison.
     * @throws IllegalStateException si la tournée n'est pas calculée ou 
     * 		si les arguments ne sont pas compatibles.
     */
    public void ajouterLivraison(PointDePassage pointPrecedent, Livraison nouvelleLivraison, FenLivraisons fenLivraison) {
    	objects.getTournee().ajouterLivraisonDansFenetre(pointPrecedent,
    			nouvelleLivraison, fenLivraison);
    }

    /**
     * Supprime une livraison et met à jour la tournée.
     * 
     * @param livraisonASupprimmer la livraison à supprimer
     * @return la fenêtre de livraison qui contenait la livraison.
     * @throws IllegalStateException si la tournée n'est pas calculée ou 
     * 		si les arguments ne sont pas compatibles.
     */
    public FenLivraisons supprimerLivraison(Livraison livraisonASupprimmer) {
        return objects.getTournee().supprimerLivraison(livraisonASupprimmer);
    }

    /**
     * Echange deux livraisons et met à jour la tournée.
     * @param l1 la première livraison à échanger
     * @param l2 la deuxième livraison à échanger
     * @throws IllegalStateException si la tournée n'est pas calculée ou 
     * 		si les arguments ne sont pas compatibles.
     */
    public void echangerLivraison(Livraison l1, Livraison l2) {
    	objects.getTournee().echangerLivraisons(l1, l2);
    }

    /**
     * Génère la feuille de route dans un fichier html
     * @throws IOException 
     */
    public void genererFeuilleDeRoute() throws IOException {
            File txt = OuvreurDeFichierTXT.getInstance().ouvre(false);
            FileWriter lu = new FileWriter(txt);
            BufferedWriter out = new BufferedWriter(lu);
            out.write(genererStringFeuilleDeRoute());
            out.close();
    }


	/**
	 * Génère le texte de la feuille de route.
	 * @return le texte généré.
	 */

    private String genererStringFeuilleDeRoute() {
        String saut = "<br/>";
        
        // initialization
        
        String feuille = 
        		  "<!DOCTYPE html>"
        		+ "<html>"
        			+ "<head>"
        				+ "<meta charset=utf-8>"
        				+ "<title>Feuille de route</title>"
        			+ "</head>"
        			+ "<style>"
        				+ "header{"
        					+ "margin: 10px;"
        					+ "padding: 0px 0px 0px 10px;"
        				+ "}"
        				+ ".itineraire{"
        					+ "padding: 10px;"
        				+ "}"
        				+ ".livraison{"
        					+ "padding: 10px;"
        					+ "margin: 5px 10px 5px 10px;"
        				+ "}"
        				+ ".entrepot{"
        					+ "font-weight: bold;"
        					+ "background: #525252;"
        					+ "color: white;"
        					+ "border: 2px solid lightgrey;"
        				+ "}"
        				+ ".descriptionItineraire{"
	        				+ "display: inline-block;"
	        				+ "border: 2px solid lightgrey;"
	        				+ "padding: 1%;"
	        				+ "width: 50%;"
        				+ "}"
        				+ ".horaireLivraison{"
	        				+ "border: 2px solid lightgrey;"
	        				+ "padding: 1%;"
	        				+ "display: inline-block;"
	        				+ "text-align: center;"
        				+ "}"
        				+ ".clear{"
        					+ "clear: both;"
    					+ "}"
					+ "</style>"
        			+ "<header>"
        				+ "<h1>Feuille de route</h1>"
        			+ "</header>"
        			+ "<body>"
        			+ "<div class=\"itineraire\">";

        Iterator<Itineraire> itinerairesIt = objects.getTournee().getItineraires().iterator();

        boolean debutFeuille = true;
        while (itinerairesIt.hasNext()) {
            Itineraire itineraire = itinerairesIt.next();
            if (debutFeuille) {
                feuille += "<div class=\"livraison entrepot\">Départ : Entrepot (adresse : X = " + itineraire.getDepart().getIntersection().getX()
                        + " ; Y = " + itineraire.getDepart().getIntersection().getY() + ")</div>" + saut;
                debutFeuille = false;
            }
            List<Troncon> listeTroncons = itineraire.getTroncons();
            Iterator<Troncon> tronconsItCur = listeTroncons.iterator(); //pos 0
            Iterator<Troncon> tronconsItNext = listeTroncons.iterator(); //pos 0
            if(tronconsItNext.hasNext())
            {
            	tronconsItNext.next();
            }
            feuille += "<div class=\"livraison\"><div class=\"descriptionItineraire\">";
         
            while (tronconsItCur.hasNext()) {
                Troncon troncon = tronconsItCur.next(); //pos n
                float duree = troncon.getDuree() / 60.0f;
                if(tronconsItNext.hasNext())
                {
                	Troncon tronconNext = tronconsItNext.next(); // pos n
		        	while(troncon.getNom().equals(tronconNext.getNom()) && tronconsItNext.hasNext())
		            {
		            	troncon = tronconsItCur.next();
		            	duree += troncon.getDuree() / 60.0f;
		            	tronconNext = tronconsItNext.next();
		            }
		        	if(troncon.getNom().equals(tronconNext.getNom()))
		        	{
		        		troncon = tronconsItCur.next();
		            	duree += troncon.getDuree() / 60.0f;
		        	}

                }
                
                int minutes = (int)duree;
                int secondes = (int) (Math.round(((duree - minutes)*60)*100.0)/100.0);
                feuille += "Prendre Rue " + troncon.getNom() + " pendant " + minutes + "min" + secondes +"s";
                if (tronconsItCur.hasNext())//est-ce qu'on est au pont de livraison ou pas
                {
                    feuille += " jusqu'à Intersection (adresse : X = "
                            + troncon.getArrivee().getX() + " ; Y = " + troncon.getArrivee().getY() + ")" + saut;
                    
                } else {
                    if (itinerairesIt.hasNext())//est-ce que le point de destination est l'entrepot ou pas
                    {
                        feuille += " jusqu'à Point de livraison (adresse : X = "
                                + troncon.getArrivee().getX() + " ; Y = " + troncon.getArrivee().getY() + ")" + "</div>";
                        
                     // Right div for infos about the delivery
                        
                        feuille += "<div class=\"horaireLivraison\">"
                        				+ "Horaire de passage :"
                        				+ saut
                        				+ itineraire.getArrivee().getHoraireEffectif().toString();
                        if(itineraire.getArrivee().getAttente()>0)
                        {
                        	int minAttente = (int) itineraire.getArrivee().getAttente()/60;
                        	feuille += saut + saut
                        				+ "Attendre " + minAttente + " minutes";
                        }
                        else if(itineraire.getArrivee().getRetard()>0)
                        {
                        	int minRetard = (int) itineraire.getArrivee().getRetard()/60;
                        	feuille += saut + saut
                    				+ "<b>RETARD DE " + minRetard + " MINUTES</b>";
                        }
                       
                        
                        feuille += "</div>";
                        //if(itineraire.getArrivee().getAttente())
                        
//                        				+ "Attendre PLACEHOLDER minutes"
  //                      				+ "</div>";
                        
                    } else {
                        feuille += " minutes jusqu'à Entrepot (adresse : X = "
                                + troncon.getArrivee().getX() + " ; Y = " + troncon.getArrivee().getY() + ")</div></div>" + saut;
                        feuille += "<div class=\"livraison entrepot\">Fin de la tournée.</div>";
                    }

                }
            }
            feuille += "</div>" + saut;
        }
        
        feuille += "</div></body><br/></html>";

        return feuille;
    }

}
