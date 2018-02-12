package xml;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import modele.Intersection;
import modele.Plan;
import modele.Troncon;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * 
 * Parse un fichier xml contenant un plan
 */
public class ParseurPlan {

    /**
     * Ouvre un fichier xml et cree plan a partir du contenu du fichier
     *
     * @param plan
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     * @throws XmlNonValideException
     */
    public static void chargerPlan(Plan plan) throws ParserConfigurationException, SAXException, IOException, XmlNonValideException {
        File xml = OuvreurDeFichierXML.getInstance().ouvre(true);
        DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document document = docBuilder.parse(xml);
        Element racine = document.getDocumentElement();
        if (racine.getNodeName().equals("Reseau")) {
            construireAPartirDeDOMXML(racine, plan);
        } else {
            throw new XmlNonValideException("0-1*La racine n'est pas <Reseau>.");
        }
    }

    /**
     * Construction de l'objet à partir des élément du DOM xml
     * 
     * @param noeudDOMRacine
     * @param plan
     * @throws XmlNonValideException
     * @throws NumberFormatException 
     */
    private static void construireAPartirDeDOMXML(Element noeudDOMRacine, Plan plan) throws XmlNonValideException, NumberFormatException {
        NodeList listeNoeuds = noeudDOMRacine.getElementsByTagName("Noeud");
        for (int i = 0; i < listeNoeuds.getLength(); i++) {
            Element noeud = (Element) listeNoeuds.item(i);
            ajouterUneIntersection(noeud, plan);
        }
        for (int i = 0; i < listeNoeuds.getLength(); i++) {
            Element noeud = (Element) listeNoeuds.item(i);
            int id = Integer.parseInt(noeud.getAttribute("id"));
            Intersection depart = plan.getIntersection(id);
            NodeList listeInter = ((Element) listeNoeuds.item(i)).getElementsByTagName("LeTronconSortant");
            for (int j = 0; j < listeInter.getLength(); j++) {
                Element noeudfils = (Element) listeInter.item(j);
                ajouterLesTroncons(noeudfils, depart, plan);
            }
        }

    }
    
    /**
     * Ajoute une intersection au plan
     * 
     * @param noeud
     * @param plan
     * @throws XmlNonValideException 
     */
    private static void ajouterUneIntersection(Element noeud, Plan plan) throws XmlNonValideException {
        int id = 0;
        try {
            id = Integer.parseInt(noeud.getAttribute("id"));
        } catch (NumberFormatException e) {
            throw new XmlNonValideException("1*Un id de noeud n'est pas bon.");
        }
        if (id < 0) {
            throw new XmlNonValideException("1*Un id de noeud n'est pas bon.");
        }
        int x = 0, y = 0;
        try {
            x = Integer.parseInt(noeud.getAttribute("x"));
            y = Integer.parseInt(noeud.getAttribute("y"));
        } catch (NumberFormatException e) {
            throw new XmlNonValideException("2*Les Coordonnées d'une intersection ne sont pas valides");
        }
        if (x < 0 || y < 0) {
            throw new XmlNonValideException("2*Les Coordonnées d'une intersection ne sont pas valides");
        }
        plan.ajouteIntersection(id, x, y);
    }

    /**
     * Ajoute les tronçons à l'intersection
     * 
     * @param noeudfils
     * @param depart Intersection de départ
     * @param plan
     * @throws XmlNonValideException 
     */
    private static void ajouterLesTroncons(Element noeudfils, Intersection depart, Plan plan) throws XmlNonValideException {
        String nomRue = noeudfils.getAttribute("nomRue");
        if (nomRue.equals("")) {
            throw new XmlNonValideException("3*Le nom de rue d'une intersection est absent.");
        }
        
        String vitesseString = noeudfils.getAttribute("vitesse").replace(',', '.');
        if (vitesseString.equals("")) {
            throw new XmlNonValideException("4*Le plan contient un/des troncon(s) ayant une vitesse de circulation invalide");
        }
        float vitesse = 0;
        try {
            vitesse = Float.parseFloat(vitesseString);
        } catch (NumberFormatException e) {
            throw new XmlNonValideException("4*Le plan contient un/des troncon(s) ayant une vitesse de circulation invalide");
        }
        if (vitesse <= 0) {
            throw new XmlNonValideException("4*Le plan contient un/des troncon(s) ayant une vitesse de circulation invalide");
        }
        
        String longueurString = noeudfils.getAttribute("longueur").replace(',', '.');
        if (longueurString.equals("")) {
            throw new XmlNonValideException("5*Le plan contient un/des troncon(s) ayant une longueur invalide");
        }
        float longueur = 0;
        try {
            longueur = Float.parseFloat(longueurString);
        } catch (NumberFormatException e) {
            throw new XmlNonValideException("5*Le plan contient un/des troncon(s) ayant une longueur invalide");
        }
        if (longueur <= 0) {
            throw new XmlNonValideException("5*Le plan contient un/des troncon(s) ayant une longueur invalide");
        }
        String idInterString = noeudfils.getAttribute("idNoeudDestination");
        if (idInterString.equals("")) throw new XmlNonValideException("6*Un tronçon n'a pas d'identifiant d'intersection d'arrivée.");
        int idInter = Integer.parseInt(idInterString);
        Intersection arrivee = plan.getIntersection(idInter);
        if (arrivee == null) throw new XmlNonValideException("6*Un tronçon n'a pas d'identifiant d'intersection d'arrivée.");
        depart.ajouterTronconSortant(new Troncon(nomRue, longueur, vitesse, arrivee));
    }

}
