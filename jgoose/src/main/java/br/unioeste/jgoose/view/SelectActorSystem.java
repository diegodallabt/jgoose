/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unioeste.jgoose.view;

import br.unioeste.jgoose.controller.Controller;
import java.awt.Image;
import java.awt.Toolkit;

/**
 *
 * @author Victor Augusto Pozzan
 */
public class SelectActorSystem extends javax.swing.JDialog {

    /**
     * Creates new form NewJDialog to select Actor System
     * @param parent
     * @param modal
     */
    public SelectActorSystem (java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);
        Image Icone;
        Icone = Toolkit.getDefaultToolkit().getImage("./src/main/resources/icons/jgoose.gif");
        setIconImage(Icone);
        
    
        for (int i = 0; i < Controller.getOme().getActors().size(); i++) {
            atorSistema.addItem(Controller.getOme().getActor(i).getName());
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        atorSistema = new javax.swing.JComboBox();
        ok = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Select the System Actor:");

        atorSistema.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        atorSistema.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                atorSistemaActionPerformed(evt);
            }
        });

        ok.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        ok.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/ok_16x16.png"))); // NOI18N
        ok.setText("OK");
        ok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(atorSistema, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(91, Short.MAX_VALUE))
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(ok)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(atorSistema, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addComponent(ok, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void atorSistemaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_atorSistemaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_atorSistemaActionPerformed

    private void okActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okActionPerformed
        Controller.setSystemActor(atorSistema.getSelectedIndex());
        dispose();
    }//GEN-LAST:event_okActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox atorSistema;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JButton ok;
    // End of variables declaration//GEN-END:variables
}