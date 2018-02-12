package modele;

import java.io.File;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.filechooser.*;

/**
 * 
 * classe permettant d'ouvrir un fichier .txt
 */
public class OuvreurDeFichierTXT extends FileFilter {// Singleton

    private static OuvreurDeFichierTXT instance = null;

    /**
     * constructeur privé
     */
    private OuvreurDeFichierTXT() {
    }

    /**
     * Permet d'obtenir une seule instance de OuvreurDeFichierTXT pour toute la durée de vie de l'application
     * @return une instance de OuvreurDeFichierTXT
     */
    protected static OuvreurDeFichierTXT getInstance() {
        if (instance == null) {
            instance = new OuvreurDeFichierTXT();
        }
        return instance;
    }

     /**
      * Permet d'ouvrir un fichier
     * @param lecture ouvrir un fichier en lecture ou écriture
     * @return le fichier à ouvrir
     * @throws IOException 
     */
    public File ouvre(boolean lecture) throws IOException {
        int returnVal;
        JFileChooser jFileChooserTXT = new JFileChooser();
        jFileChooserTXT.setFileFilter(this);
        jFileChooserTXT.setFileSelectionMode(JFileChooser.FILES_ONLY);
        if (lecture) {
            returnVal = jFileChooserTXT.showOpenDialog(null);
        } else {
            returnVal = jFileChooserTXT.showSaveDialog(null);
        }
        if (returnVal != JFileChooser.APPROVE_OPTION) {
            throw new IOException("Le fichier n'existe pas ou a été mal choisi.");
        }
        String fichier = jFileChooserTXT.getSelectedFile().getAbsolutePath();
        if (!fichier.toLowerCase().endsWith(".html")) {
            fichier += ".html";
        }
        return new File(fichier);
    }

    @Override
    public boolean accept(File f) {
        if (f == null) {
            return false;
        }
        if (f.isDirectory()) {
            return true;
        }
        String extension = getExtension(f);
        if (extension == null) {
            return false;
        }
        return extension.contentEquals("html");
    }

    /**
     * 
     * @return l'extension du type de fichier traité
     */
    @Override
    public String getDescription() {
        return "Fichier HTML";
    }

    /**
     * permet d'obtenir l'extension d'un fichier
     * @param f le fichier dont on veut l'extention
     * @return l'extension du fichier
     */
    private String getExtension(File f) {
        String filename = f.getName();
        int i = filename.lastIndexOf('.');
        if (i > 0 && i < filename.length() - 1) {
            return filename.substring(i + 1).toLowerCase();
        }
        return null;
    }
}
