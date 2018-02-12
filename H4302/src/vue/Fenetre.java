/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import controleur.Controleur;
import javax.swing.JOptionPane;
import modele.PointEntreeVue;

/**
 * Représentation de l'insterface graphique
 */
public final class Fenetre extends javax.swing.JFrame {
    
    private final Controleur controleur;
    private final VueGraphique vueGraphique;
    private final VueTextuelle vueTextuelle;
    
    /**
     *
     */
    public PointEntreeVue facadeVue;

    /**
     * Creates new form Fenetre
     */
    public Fenetre() {
        initComponents();
        this.setExtendedState(this.getExtendedState() | Fenetre.MAXIMIZED_BOTH);
        this.facadeVue = new PointEntreeVue();
        controleur = new Controleur(this);
        
        this.MenuChargerPlan.addActionListener(new EcouteurMenuChargerPlan(controleur));
        this.MenuChargerDemande.addActionListener(new EcouteurMenuChargerDemande(controleur));
         this.MenuGenererFeuilleRoute.addActionListener(new EcouteurDeMenuGenererFeuilleDeRoute(controleur));
         this.MenuCalculerTournee.addActionListener(new EcouteurDeMenuCalculerTournee(controleur));
        
        vueGraphique = new VueGraphique(this, PanelGraphePlan);
        PanelGraphePlan.add(vueGraphique);
        this.addMouseListener(new EcouteurDeSourisVueGraphique(controleur, vueGraphique, this));
        this.addMouseMotionListener(new EcouteurDeSourisVueGraphique(controleur, vueGraphique, this));
        this.MenuUndo.addActionListener(new EcouteurMenuUndo(controleur) );
        this.MenuRedo.addActionListener(new EcouteurMenuRedo(controleur));
        this.BoutonAjouterLivraison.addActionListener(new EcouteurDeBoutonAjouterLivraison(controleur));
        this.BoutonSupprimerLivraison.addActionListener(new EcouteurDeBoutonSupprimerLivraison(controleur));
        this.BoutonEchangerLivraison.addActionListener(new EcouteurDeBoutonEchangerLivraison(controleur));
       
        
        this.vueTextuelle = new VueTextuelle(this, this.PanelVueTextuelle); 
        this.vueTextuelle.setMouseListenerTabeau(new EcouteurDeSourisTableauVueTextuelle(controleur, vueTextuelle, this));
        this.vueTextuelle.setMouseListener(new EcouteurDeSourisVueTextuelle(controleur, this));
        PanelVueTextuelle.add(vueTextuelle);
        
        
        Legende l = new Legende(this.PanelLegendeGraphe.getSize());
        this.PanelLegendeGraphe.add(l);

        //faire un affichage par defaut :)
        facadeVue.notifyChanged(null);
        enabledBouton(false);
        enabledMenuChargerDemande(false);
        enabledMenuCalculerTournee(false);
    }
    
    /**
     * Gestion de l'activation des boutons sur la fenêtre
     * 
     * @param enabled
     */
    public void enabledBouton (boolean enabled){
        this.BoutonAjouterLivraison.setEnabled(enabled);
        this.BoutonEchangerLivraison.setEnabled(enabled);
        this.BoutonSupprimerLivraison.setEnabled(enabled);
        this.MenuRedo.setEnabled(enabled);
        this.MenuUndo.setEnabled(enabled);
        this.MenuGenererFeuilleRoute.setEnabled(enabled);
    }
    
    public void enabledMenuChargerPlan(boolean enabled){
        this.MenuChargerPlan.setEnabled(enabled);
    }
    
    public void enabledMenuChargerDemande(boolean enabled){
        this.MenuChargerDemande.setEnabled(enabled);
    }
    
    public void enabledMenuCalculerTournee(boolean enabled){
        this.MenuCalculerTournee.setEnabled(enabled);
    }
    
    public void enabledMenuChargement(boolean enabled){
        enabledMenuChargerDemande(enabled);
        enabledMenuChargerPlan(enabled);
    }
    
    
    
    /**
     * Réinitialise l'Intersection et/ou la Livraison sélectionnée sur les deux vues
     */
    public void razSelectionVue(){
        this.vueGraphique.razSelectionVue();
        this.vueTextuelle.razSelectionVue();
    }
    
    /**
     * Affiche un message dnas la zone textuelle de la fenêtre
     * @param message
     */
    public void afficheMessage(String message) {
        this.Message.setText(message);
    }
    
    /**
     *  Affiche une pop up d'erreur à l'écran. Le titre sera "Erreur"
     * 
     * @param message
     */
    public void afficherPopPupErreur(String message) {
        afficherPopUpErreur(message, "Erreur");
    }

    /**
     * Affiche une pop up d'erreur à l'écran.
     * @param message
     * @param error
     */
    public void afficherPopUpErreur(String message, String error) {
        JOptionPane.showMessageDialog(this, message, error, JOptionPane.ERROR_MESSAGE);
    }
    
    /**
     *  Affiche une pop up avec un icone d'information
     * @param message
     * @param titre
     */
    public void afficherPopUpInformation(String message, String titre) {
        JOptionPane.showMessageDialog(this, message, titre, JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     *  Affiche une pop up pour poser une question simple à l'utilisateur
     * @param question
     * @param titre
     * @return
     */
    public String afficherPopUpQuestion(String question, String titre){
        return JOptionPane.showInputDialog(this, question, titre, JOptionPane.QUESTION_MESSAGE);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Message = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        PanelEditionLivraison = new javax.swing.JPanel();
        BoutonAjouterLivraison = new javax.swing.JButton();
        BoutonSupprimerLivraison = new javax.swing.JButton();
        BoutonEchangerLivraison = new javax.swing.JButton();
        PanelGraphe = new javax.swing.JPanel();
        PanelGraphePlan = new javax.swing.JPanel();
        PanelVues = new javax.swing.JPanel();
        PanelLivraison = new javax.swing.JPanel();
        PanelVueTextuelle = new javax.swing.JPanel();
        PanelLegende = new javax.swing.JPanel();
        PanelLegendeGraphe = new javax.swing.JPanel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        MenuChargerPlan = new javax.swing.JMenuItem();
        MenuChargerDemande = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        MenuUndo = new javax.swing.JMenuItem();
        MenuRedo = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        MenuCalculerTournee = new javax.swing.JMenuItem();
        MenuGenererFeuilleRoute = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("OptimodLyon");
        setMinimumSize(new java.awt.Dimension(1000, 0));

        Message.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        Message.setText("      ");

        PanelEditionLivraison.setBorder(javax.swing.BorderFactory.createTitledBorder("Modifier les livraisons"));

        BoutonAjouterLivraison.setText("Ajouter");

        BoutonSupprimerLivraison.setText("Supprimer");

        BoutonEchangerLivraison.setText("Echanger");

        javax.swing.GroupLayout PanelEditionLivraisonLayout = new javax.swing.GroupLayout(PanelEditionLivraison);
        PanelEditionLivraison.setLayout(PanelEditionLivraisonLayout);
        PanelEditionLivraisonLayout.setHorizontalGroup(
            PanelEditionLivraisonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelEditionLivraisonLayout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addComponent(BoutonAjouterLivraison)
                .addGap(67, 67, 67)
                .addComponent(BoutonSupprimerLivraison)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 71, Short.MAX_VALUE)
                .addComponent(BoutonEchangerLivraison, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(53, 53, 53))
        );
        PanelEditionLivraisonLayout.setVerticalGroup(
            PanelEditionLivraisonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelEditionLivraisonLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(PanelEditionLivraisonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BoutonAjouterLivraison)
                    .addComponent(BoutonSupprimerLivraison)
                    .addComponent(BoutonEchangerLivraison))
                .addContainerGap())
        );

        PanelGraphe.setBorder(javax.swing.BorderFactory.createTitledBorder("Graphe"));

        PanelGraphePlan.setBackground(new java.awt.Color(51, 255, 153));

        javax.swing.GroupLayout PanelGraphePlanLayout = new javax.swing.GroupLayout(PanelGraphePlan);
        PanelGraphePlan.setLayout(PanelGraphePlanLayout);
        PanelGraphePlanLayout.setHorizontalGroup(
            PanelGraphePlanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 354, Short.MAX_VALUE)
        );
        PanelGraphePlanLayout.setVerticalGroup(
            PanelGraphePlanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout PanelGrapheLayout = new javax.swing.GroupLayout(PanelGraphe);
        PanelGraphe.setLayout(PanelGrapheLayout);
        PanelGrapheLayout.setHorizontalGroup(
            PanelGrapheLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelGrapheLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(PanelGraphePlan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        PanelGrapheLayout.setVerticalGroup(
            PanelGrapheLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelGrapheLayout.createSequentialGroup()
                .addComponent(PanelGraphePlan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        PanelLivraison.setBorder(javax.swing.BorderFactory.createTitledBorder("Livraisons"));

        javax.swing.GroupLayout PanelVueTextuelleLayout = new javax.swing.GroupLayout(PanelVueTextuelle);
        PanelVueTextuelle.setLayout(PanelVueTextuelleLayout);
        PanelVueTextuelleLayout.setHorizontalGroup(
            PanelVueTextuelleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 469, Short.MAX_VALUE)
        );
        PanelVueTextuelleLayout.setVerticalGroup(
            PanelVueTextuelleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 222, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout PanelLivraisonLayout = new javax.swing.GroupLayout(PanelLivraison);
        PanelLivraison.setLayout(PanelLivraisonLayout);
        PanelLivraisonLayout.setHorizontalGroup(
            PanelLivraisonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelLivraisonLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(PanelVueTextuelle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        PanelLivraisonLayout.setVerticalGroup(
            PanelLivraisonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelLivraisonLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(PanelVueTextuelle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        PanelLegende.setBorder(javax.swing.BorderFactory.createTitledBorder("Légende"));

        PanelLegendeGraphe.setBackground(new java.awt.Color(51, 255, 51));

        javax.swing.GroupLayout PanelLegendeGrapheLayout = new javax.swing.GroupLayout(PanelLegendeGraphe);
        PanelLegendeGraphe.setLayout(PanelLegendeGrapheLayout);
        PanelLegendeGrapheLayout.setHorizontalGroup(
            PanelLegendeGrapheLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        PanelLegendeGrapheLayout.setVerticalGroup(
            PanelLegendeGrapheLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 122, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout PanelLegendeLayout = new javax.swing.GroupLayout(PanelLegende);
        PanelLegende.setLayout(PanelLegendeLayout);
        PanelLegendeLayout.setHorizontalGroup(
            PanelLegendeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PanelLegendeGraphe, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        PanelLegendeLayout.setVerticalGroup(
            PanelLegendeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PanelLegendeGraphe, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout PanelVuesLayout = new javax.swing.GroupLayout(PanelVues);
        PanelVues.setLayout(PanelVuesLayout);
        PanelVuesLayout.setHorizontalGroup(
            PanelVuesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelVuesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelVuesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelVuesLayout.createSequentialGroup()
                        .addComponent(PanelLivraison, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addComponent(PanelLegende, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        PanelVuesLayout.setVerticalGroup(
            PanelVuesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelVuesLayout.createSequentialGroup()
                .addComponent(PanelLivraison, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PanelLegende, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jMenu1.setText("Fichier");

        MenuChargerPlan.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        MenuChargerPlan.setText("Charger un plan");
        jMenu1.add(MenuChargerPlan);

        MenuChargerDemande.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.CTRL_MASK));
        MenuChargerDemande.setText("Charger une demande de livraisons");
        jMenu1.add(MenuChargerDemande);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Editer");

        MenuUndo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Z, java.awt.event.InputEvent.CTRL_MASK));
        MenuUndo.setText("Undo");
        jMenu2.add(MenuUndo);

        MenuRedo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Y, java.awt.event.InputEvent.CTRL_MASK));
        MenuRedo.setText("Redo");
        jMenu2.add(MenuRedo);

        jMenuBar1.add(jMenu2);

        jMenu3.setText("Tournée");

        MenuCalculerTournee.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_T, java.awt.event.InputEvent.CTRL_MASK));
        MenuCalculerTournee.setText("Calculer la tournée");
        jMenu3.add(MenuCalculerTournee);

        MenuGenererFeuilleRoute.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        MenuGenererFeuilleRoute.setText("Générer la feuille de route");
        jMenu3.add(MenuGenererFeuilleRoute);

        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSeparator1))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(Message, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(PanelVues, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(PanelEditionLivraison, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(PanelGraphe, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(PanelVues, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(PanelEditionLivraison, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(PanelGraphe, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Message)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Fenetre.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Fenetre.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Fenetre.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Fenetre.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Fenetre().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BoutonAjouterLivraison;
    private javax.swing.JButton BoutonEchangerLivraison;
    private javax.swing.JButton BoutonSupprimerLivraison;
    private javax.swing.JMenuItem MenuCalculerTournee;
    private javax.swing.JMenuItem MenuChargerDemande;
    private javax.swing.JMenuItem MenuChargerPlan;
    private javax.swing.JMenuItem MenuGenererFeuilleRoute;
    private javax.swing.JMenuItem MenuRedo;
    private javax.swing.JMenuItem MenuUndo;
    private javax.swing.JLabel Message;
    private javax.swing.JPanel PanelEditionLivraison;
    private javax.swing.JPanel PanelGraphe;
    private javax.swing.JPanel PanelGraphePlan;
    private javax.swing.JPanel PanelLegende;
    private javax.swing.JPanel PanelLegendeGraphe;
    private javax.swing.JPanel PanelLivraison;
    private javax.swing.JPanel PanelVueTextuelle;
    private javax.swing.JPanel PanelVues;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JSeparator jSeparator1;
    // End of variables declaration//GEN-END:variables
}
