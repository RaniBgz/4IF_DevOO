package xml;

import java.io.File;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.filechooser.*;

/**
 * classe permettant d'ouvrir un fichier xml
 */
public class OuvreurDeFichierXML extends FileFilter {// Singleton

    private static OuvreurDeFichierXML instance = null;

    /**
     * constructeur
     */
    private OuvreurDeFichierXML() {
    }

    /**
     * Permet d'obtenir une seule instance de OuvreurDeFichierXML pour toute la durée de vie de l'application
     * @return une instance de OuvreurDeFichierXML
     */
    protected static OuvreurDeFichierXML getInstance() {
        if (instance == null) {
            instance = new OuvreurDeFichierXML();
        }
        return instance;
    }

    /**
     * Permet d'ouvrir un fichier
     * @param lecture, accès en lecture ou écriture
     * @return le fichier à ouvrir avec les droits demandés si cela était possible
     * @throws XmlNonValideException
     * @throws IOException 
     */
    public File ouvre(boolean lecture) throws XmlNonValideException, IOException {
        int returnVal;
        JFileChooser jFileChooserXML = new JFileChooser();
        jFileChooserXML.setFileFilter(this);
        jFileChooserXML.setFileSelectionMode(JFileChooser.FILES_ONLY);
        if (lecture) {
            returnVal = jFileChooserXML.showOpenDialog(null);
        } else {
            returnVal = jFileChooserXML.showSaveDialog(null);
        }
        if (returnVal != JFileChooser.APPROVE_OPTION) {
            throw new IOException("Le fichier n'existe pas ou a été mal choisi.");
        }
        return new File(jFileChooserXML.getSelectedFile().getAbsolutePath());
    }

    /**
     * Vérifie que le fichier peut être ouvert par OuvreurDeFichierXML
     * @param f
     * @return 
     */
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
        return extension.contentEquals("xml");
    }

    /**
     * 
     * @return le type des fichiers pouvant être traités
     */
    @Override
    public String getDescription() {
        return "Fichier XML";
    }

    /**
     * Permet d'obtenir l'extension d'un fichier
     * @param f le fichier dont on veut l'extension
     * @return 
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
