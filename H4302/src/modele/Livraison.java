package modele;

/**
 * Représente une livraison.
 */
public class Livraison implements PointDePassage {

    private final Intersection intersection;
    private int retard = 0;
    private int attente = 0;
    private Horaire horaireEffectif = null;

    private final int idGenere;
    private final int noClient;
    private final int idXml;

    private static final int PREMIER_ID = 1;
    private static int prochainId = PREMIER_ID;

    /**
     * Crée une livraison à l'intersection donnée. L'ID client est
     * automatiquement généré.
     * @param intersection l'intersection où est située la livraison.
     * @param idXml l'ID de la livraison donné par l'utilisateur.
     * @param noClient le numéro du client
     */
    public Livraison(Intersection intersection, int idXml, int noClient) {
        this.intersection = intersection;
        this.idXml = idXml;
        this.noClient = noClient;

        this.idGenere = genererId();
    }

    /**
     * Génére une ID utilisée pour le calcul de la tournée.
     * <p>
     * Deux livraisons ne peuvent pas avoir le même ID.
     * @return l'id généré
     */
    private static int genererId() {
        return prochainId++;
    }

    /**
     * Remet le générateur d'ID à zéro.
     * <br>
     * Devrait être appelée à chaque fois qu'une demande de livraison est
     * chargée.
     */
    public static void resetIdGenerateur() {
        prochainId = PREMIER_ID;
    }

    /**
     * @return le retard en secondes.
     * <p>
     * Le temps de livraison est compté.
     */
    @Override
    public int getRetard() {
        return retard;
    }

    /**
     * Définit le retard de cette livraison en seconde.
     */
    void setRetard(int retard) {
        this.retard = retard;
    }

    /**
     * @return l'horaire du début de la livraison dans le cadre de la 
     * tournée courante
     */
    public Horaire getHoraireEffectif() {
        return (horaireEffectif == null)? null : horaireEffectif.copy();
    }

    /**
     * Définit l'horaire du début de la livraison dans le cadre de la 
     * tournée courante
     * @param horaireEffectif
     */
    public void setHoraireEffectif(Horaire horaireEffectif) {
        this.horaireEffectif = horaireEffectif.copy();
    }

    /**
     * 
     * @return l'intersection liée à la livraison
     */
    @Override
    public Intersection getIntersection() {
        return intersection;
    }

    /**
     * 
     * @return l'id généré
     */
    @Override
    public int getIdGenere() {
        return idGenere;
    }

    /**
     * @return le numéro du client.
     */
    public int getNoClient() {
        return noClient;
    }

    /**
     * @return l'ID déginit par l'utilisateur.
     */
    public int getIdXml() {
        return idXml;
    }

    /**
     * @return l'attente en seconde.
     */
    @Override
    public int getAttente() {
        return attente;
    }

    /**
     * Définit l'attente en secondes.
     */
    void setAttente(int attente) {
        this.attente = attente;
    }

    @Override
    public String toString() {
        return "Livraison [idGenere=" + idGenere + ", noClient=" + noClient
                + ", idXml=" + idXml + "]";
    }

}
