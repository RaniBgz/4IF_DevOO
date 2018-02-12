package xml;

/**
 * Classe exception pour les parseurs de xml
 */
public class XmlNonValideException extends Exception {

    /**
     * Surcharge du contoleur
     * 
     * @param message le message à afficher
     */
	public XmlNonValideException(String message) {
		super(message);
	}

}
