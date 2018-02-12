package xml;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import modele.Horaire;
import modele.DemandeLivraisons;
import modele.FenLivraisons;
import modele.Livraison;
import modele.Plan;
import modele.Intersection;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * 
 * Classe permetant de parser un fichier xml contenant une demande de livraisons
 */
public class ParseurDemande {

    /**
     * Ouvre un fichier xml et cree une liste de FenLivraisons à partir des
     * infos du fichier Chaque FenLivraison contient elle-même une liste non
     * ordonnée de livraison (voir FenLivraison)
     *
     * @param demande
     * @param plan
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     * @throws XmlNonValideException
     */
    public static void chargerDemande(DemandeLivraisons demande, Plan plan) throws ParserConfigurationException, SAXException, IOException, XmlNonValideException {
        File xml = OuvreurDeFichierXML.getInstance().ouvre(true);
        DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document document = docBuilder.parse(xml);
        Element racine = document.getDocumentElement();
        if (racine.getNodeName().equals("JourneeType")) {
            construireAPartirDeDOMXML(racine, demande, plan);
        } else {
            throw new XmlNonValideException("0-2*La racine n'est pas <JourneeType>");
        }
    }

    /**
     * Construction de l'objet à partir des élément du DOM xml
     * 
     * @param noeudDOMRacine
     * @param demande
     * @param plan
     * @throws XmlNonValideException
     * @throws NumberFormatException 
     */
    private static void construireAPartirDeDOMXML(Element noeudDOMRacine, DemandeLivraisons demande, Plan plan) throws XmlNonValideException, NumberFormatException {
        NodeList listePlages = noeudDOMRacine.getElementsByTagName("Plage");
        FenLivraisons fen;
        for (int i = 0; i < listePlages.getLength(); i++) {
            Element plage = (Element) listePlages.item(i);
            fen = ajouterPlageHoraire(plage, demande);
            NodeList listeLivraisons = ((Element) listePlages.item(i)).getElementsByTagName("Livraison");
            for (int j = 0; j < listeLivraisons.getLength(); j++) {
                Element noeudLivraison = (Element) listeLivraisons.item(j);
                ajouterLivraison(noeudLivraison, plan, fen, demande);
            }

        }
        int idEntrepot = 0;
        try {
            NodeList entrepots = noeudDOMRacine.getElementsByTagName("Entrepot");
             idEntrepot = Integer.parseInt(((Element)entrepots.item(0)).getAttribute("adresse"));
        } catch (NumberFormatException | NullPointerException e) {
            throw new XmlNonValideException("7*L'entrepôt est manquant.");
        }
        if (idEntrepot < 0) {
            throw new XmlNonValideException("8*L'entrepôt a une adresse négative.");

        }
        Intersection intersection = plan.getIntersection(idEntrepot);
        if (intersection == null) {
            throw new XmlNonValideException("9*L'entrepôt a une adresse qui n'est pas une intersection connue.");
        }
        if (demande.adresseDejaLivree(intersection)) {
            throw new XmlNonValideException("10*L'entrepôt est aussi une livraison, ce qui n'est pas possible.");

        }
        demande.setEntrepot(intersection);
    }

    /**
     * Ajoute une fenêtre livraison à la demande de livraison
     * 
     * @param plage Plage horaire
     * @param demande
     * @return
     * @throws XmlNonValideException 
     */
    private static FenLivraisons ajouterPlageHoraire(Element plage, DemandeLivraisons demande) throws XmlNonValideException {
        Horaire horaireDebutFenetre, horaireFinFenetre;
        FenLivraisons fenetre;
        int heuresDebut = 0, minutesDebut = 0, secondesDebut = 0;
        int heuresFin = 0, minutesFin = 0, secondesFin = 0;
        int tempIndex = 0, tempIndexBis = 0;
        String heureDebut = "";
        String heureFin = "";

        //Extraction des String contenant les horaires
        heureDebut = plage.getAttribute("heureDebut");
        if (heureDebut.equals("")) {
            throw new XmlNonValideException("11*Il manque une heure de début dans au moins une plage horaire.");
        }
        heureFin = plage.getAttribute("heureFin");
        if (heureFin.equals("")) {
            throw new XmlNonValideException("12*Il manque une heure de fin dans au moins une plage horaire.");

        }

        //Mise en forme pour séparer les heures des minutes des secondes  pour l'horaire du début
        try {
            heuresDebut = Integer.parseInt(heureDebut.substring(0, heureDebut.indexOf(":")));
            tempIndex = heureDebut.indexOf(":") + 1;
            minutesDebut = Integer.parseInt(heureDebut.substring(tempIndex, heureDebut.indexOf(":", tempIndex)));
            tempIndexBis = heureDebut.indexOf(":", tempIndex) + 1;
            secondesDebut = Integer.parseInt(heureDebut.substring(tempIndexBis));
        } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
            throw new XmlNonValideException("13*Une heure de début d'une plage horaire n'est pas sous la forme nombre1:nombre2:nombre3.");
        }
        if (heuresDebut < 0 || minutesDebut < 0 || secondesDebut < 0) {
            throw new XmlNonValideException("14*Une heure de début d'une plage horaire contient des informations négatives.");
        }
        if (heuresDebut > 24 || minutesDebut > 60 || secondesDebut > 60) {
            throw new XmlNonValideException("15*Une heure de début d'une plage horaire contient des informations aberrantes.");
        }

        try {
            //Mise en forme pour séparer les heures des minutes des secondes  pour l'horaire de la fin
            heuresFin = Integer.parseInt(heureFin.substring(0, heureFin.indexOf(":")));
            tempIndex = heureFin.indexOf(":") + 1;
            minutesFin = Integer.parseInt(heureFin.substring(tempIndex, heureFin.indexOf(":", tempIndex)));
            tempIndexBis = heureFin.indexOf(":", tempIndex) + 1;
            secondesFin = Integer.parseInt(heureFin.substring(tempIndexBis));
        } catch (NumberFormatException e) {
            throw new XmlNonValideException("16*Une heure de fin d'une plage horaire n'est pas sous la forme nombre1:nombre2:nombre3.");
        }
        if (heuresFin < 0 || minutesFin < 0 || secondesFin < 0) {
            throw new XmlNonValideException("17*Une heure de fin d'une plage horaire contient des informations négatives.");
        }
        if (heuresFin > 24 || minutesFin > 60 || secondesFin > 60) {
            throw new XmlNonValideException("18*Une heure de fin d'une plage horaire contient des informations aberrantes.");
        }

        //Création des Horaires à partir des données collectées
        horaireDebutFenetre = new Horaire(heuresDebut, minutesDebut, secondesDebut);
        horaireFinFenetre = new Horaire(heuresFin, minutesFin, secondesFin);
        if (horaireDebutFenetre.compareTo(horaireFinFenetre)>=0) {
            throw new XmlNonValideException("19*Un horaire de fin d'une plage de livraison est inférieur à l'horaire de début.");
        }
        
        //Création de la fenêtre de livraisons correspondant aux bons horaires
        fenetre = new FenLivraisons(horaireDebutFenetre, horaireFinFenetre);

        //Ajout de la fenêtre de livraisons à la demande de livraison #JaimeLesRepetitions
        demande.ajouterFenLivraisons(fenetre);
        return fenetre;
    }

    /**
     * Ajoute une livraison à la fenêtre de livraison
     * 
     * @param noeudLivraison
     * @param plan
     * @param fen fenêtre de livraison
     * @param demande
     * @throws XmlNonValideException 
     */
    private static void ajouterLivraison(Element noeudLivraison, Plan plan, FenLivraisons fen, DemandeLivraisons demande) throws XmlNonValideException {
        int id = 0;
        int client = 0;
        Intersection intersection;
        Livraison livraison;

        try {
            //On récupère l'ID de la livraison
            id = Integer.parseInt(noeudLivraison.getAttribute("id"));
        } catch (NumberFormatException e) {
            throw new XmlNonValideException("20*Une livraison ne contient pas d'id ou a un id non numérique.");
        }
        if (id < 0) {
            throw new XmlNonValideException("21*Une livraison a un id négatif.");

        }
        if (!fen.idLivraisonExisteDeja(id)) {
            throw new XmlNonValideException("22*Il y a deux ids de livraison identiques dans une même plage de livraison.");

        }
        //On récupère le N° du client
        try {
            client = Integer.parseInt(noeudLivraison.getAttribute("client"));
        } catch (NumberFormatException e) {
            throw new XmlNonValideException("23*Une livraison ne contient pas d'idClient ou a un idClient non numérique.");
        }
        if (client < 0) {
            throw new XmlNonValideException("24*Une livraison a un idclient négatif.");

        }

        int idIntersection = 0;
        try {
            idIntersection = Integer.parseInt(noeudLivraison.getAttribute("adresse"));
        } catch (NumberFormatException e) {
            throw new XmlNonValideException("25*Une livraison ne contient pas d'adresse ou a une adresse non numérique.");
        }
        if (idIntersection < 0) {
        throw new XmlNonValideException("26*Une livraison a une adresse négative.");

        }
        //On récupère l'intersection correspondant à l'adresse
        intersection = plan.getIntersection(idIntersection);
        if (intersection == null) {
            throw new XmlNonValideException("27*L'adresse d'une livraison n'est pas connue.");
        }
        if (demande.adresseDejaLivree(intersection)) {
            throw new XmlNonValideException("28*Il y a au moins deux livraisons sur la même intersection.");
        }
        //On construit la livraison à partir de toutes ces informations
        livraison = new Livraison(intersection, id, client);

        //On ajoute la livraison à la fenêtre de livraison adéquate
        fen.ajouterLivraison(livraison);

    }
}
