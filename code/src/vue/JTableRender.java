/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * Définir l'affichage dans un JTable
 */
public class JTableRender extends DefaultTableCellRenderer {

    private final String[][] t;
    private final boolean[] retard;
    private final int [] couleurIndex;
    private final Color [] couleur;

    public JTableRender(String[][] t, boolean[] retard, int [] couleurIndex, Color [] couleur) {
        this.t = t;
        this.retard = retard;
        this.couleurIndex = couleurIndex;
        this.couleur = couleur;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        if (isSelected) {
            component.setBackground(Color.BLUE);
            component.setForeground(Color.WHITE);
            component.setFont(component.getFont().deriveFont(Font.BOLD));
        } else {

            if (row % 2 == 0) {
                component.setBackground(Color.WHITE);
            } else {
                component.setBackground(new Color(240, 240, 240));
            }
            if (retard != null && retard[row]) {
                component.setForeground(Color.RED);
                component.setFont(component.getFont().deriveFont(Font.BOLD));
            } else {
                component.setForeground(Color.BLACK);
            }
            
            if (column == 5){
                component.setBackground(couleur[couleurIndex[row]]);
            }
        }

        this.setHorizontalAlignment(JLabel.CENTER);
        return component;
    }
    


}
