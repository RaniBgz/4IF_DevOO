package modele;

/**
 * Classe utilitaire pour g�rer des horaires.
 * <p>
 * Seuls les secondes, minutes et heures sont enregistrées.<br>
 * Il n'y a pas de notion de journée.<br>
 * Si l'horaire devrait être plus grand que 23:59:59, il repasse par 00:00:00.
 * L'information de nouvelle journée est perdue.
 */
public final class Horaire implements Comparable<Horaire> {

    private int heures;
    private int minutes;
    private int secondes;

    /**
     * Crée un horaire. Les valeurs doivent être positives.
     *
     * @param heures
     * @param minutes
     * @param secondes
     * @throws IllegalArgumentException si une valeur est négative
     */
    public Horaire(int heures, int minutes, int secondes) {
        setHeures(heures);
        setMinutes(minutes);
        setSecondes(secondes);
    }

    /**
     * Renvoie la partie heures de l'horaire.
     * @return la partie heure
     */
    public int getHeures() {
        return heures;
    }

    /**
     * Définit l'heure.
     * <p>
     * L'heure est toujours légale.
     *
     * @param heure à enregistrer
     * @throws IllegalArgumentException si l'argument est négatif
     */
    void setHeures(int heures) {
        if (heures < 0) {
            throw new IllegalArgumentException("Argument négatif : " + heures);
        }
        this.heures = heures % 24;
    }

    /**
     * Renvoie la partie minutes de l'horaire.
     * @return le nombre de minutes
     */
    public int getMinutes() {
        return minutes;
    }

    /**
     * Définit les minutes.
     * <p>
     * Si minutes >= 60, les heures sont modifiées également.
     *
     * @param minutes à enregistrer
     * @throws IllegalArgumentException si l'argument est négatif
     */
    void setMinutes(int minutes) {
        if (minutes < 0) {
            throw new IllegalArgumentException("Argument n�gatif : " + minutes);
        }
        if (minutes >= 60) {
            ajouterHeures(minutes / 60);
        }

        this.minutes = minutes % 60;
    }

    /**
     * Renvoie la partie secondes de l'horaire.
     * @return  le nombre de seconde
     */
    public int getSecondes() {
        return secondes;
    }

    /**
     * Définit les secondes.
     * <p>
     * Si secondes >= 60, les minutes et les heures sont modifiées également.
     *
     * @param Seconde à enregistrer
     * @throws IllegalArgumentException si l'argument est négatif
     */
    void setSecondes(int secondes) {
        if (secondes < 0) {
            throw new IllegalArgumentException("Argument n�gatif : " + secondes);
        }
        if (secondes >= 60) {
            ajouterMinutes(secondes / 60);
        }

        this.secondes = secondes % 60;
    }

    /**
     * Ajoute des secondes.
     * <p>
     * Si secondes finales >= 60, les minutes et les heures sont modifiées
     * également.
     *
     * @param nombre de seconde à ajouter
     * @throws IllegalArgumentException si l'argument est négatif
     */
    void ajouterSecondes(int secondes) {
        if (secondes < 0) {
            throw new IllegalArgumentException("Argument n�gatif : " + secondes);
        }
        setSecondes(this.secondes + secondes);
    }

    /**
     * Ajoute des minutes.
     * <p>
     * Si minutes finales >= 60, les heures sont modifiées également.
     *
     * @param Nombre de minute à ajouter
     * @throws IllegalArgumentException si l'argument est négatif
     */
    void ajouterMinutes(int minutes) {
        if (minutes < 0) {
            throw new IllegalArgumentException("Argument n�gatif : " + minutes);
        }
        setMinutes(this.minutes + minutes);
    }

    /**
     * Ajoute des heures.
     * <p>
     * Si secondes finales >= 24, la journée recommence.
     *
     * @param nombre d'heure à ajouter
     * @throws IllegalArgumentException si l'argument est négatif
     */
    void ajouterHeures(int heures) {
        if (heures < 0) {
            throw new IllegalArgumentException("Argument n�gatif : " + heures);
        }
        setHeures(this.heures + heures);
    }

    /**
     * Retourne l'horaire résultant de la soustraction de deux horaires.
     * <p>
     * Le premier horaire doit être sup�rieur ou égal au deuxiême.
     *
     * @return Horaire résultat
     * @param Horaire 1
     * @param  Horaire 2
     * @throws IllegalArgumentException si le premier horaire est inférieur au
     * deuxiême
     */
    static Horaire difference(Horaire h1, Horaire h2) {
        int diff = h1.diffSecondes(h2);
        if (diff < 0) {
            throw new IllegalArgumentException(h1 + " est inf�rieur � " + h2);
        }
        return new Horaire(0, 0, diff);
    }

    @Override
    /**
     * Retourne l'horaire sous la forme "hh:mm:ss".
     */
    public String toString() {
        return String.format("%02d:%02d:%02d", heures, minutes, secondes);
    }

    @Override
    public int compareTo(Horaire horaire2) {
        return diffSecondes(horaire2);
    }

    /**
     * Retourne la différence entre les deux horaires en nombre de secondes.
     * @param horaire2 Horaire à étudier
     * @return num de secondes
     */
    public int diffSecondes(Horaire horaire2) {
        int diff = (this.heures - horaire2.heures) * 3600
                + (this.minutes - horaire2.minutes) * 60
                + (this.secondes - horaire2.secondes);
        return diff;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + heures;
        result = prime * result + minutes;
        result = prime * result + secondes;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Horaire other = (Horaire) obj;
        if (heures != other.heures) {
            return false;
        }
        if (minutes != other.minutes) {
            return false;
        }
        return secondes == other.secondes;
    }

    /**
     * Retourne une copie de cet {@link Horaire}
     * @return Retourne un Horaire copie de l'instance 
     */
    public Horaire copy() {
        return new Horaire(heures, minutes, secondes);
    }
}
