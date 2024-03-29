/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.unioeste.jgoose.view;

import br.unioeste.jgoose.controller.Controller;
import br.unioeste.jgoose.controller.UCController;
import java.awt.Image;
import java.awt.Toolkit;

/**
 *
 * @author Diego
 */
public class SelectActorPrimary extends javax.swing.JDialog {
    /**
     * Creates new form NewJDialog to select Actor System
     * @param parent
     * @param modal
     */
    public SelectActorPrimary(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);
        Image Icone;
        Icone = Toolkit.getDefaultToolkit().getImage("./src/main/resources/icons/jgoose.gif");
        setIconImage(Icone);
        
    
        for (int i = 0; i < UCController.getTokensUC().getActorUC().size(); i++) {
            atorPrimario.addItem(UCController.getTokensUC().getActor(i).getName());
        }
    }
    
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        atorPrimario = new javax.swing.JComboBox();
        ok = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Select the Primary Actor:");

        atorPrimario.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        atorPrimario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                atorPrimarioActionPerformed(evt);
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
                .addComponent(atorPrimario, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                .addComponent(atorPrimario, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addComponent(ok, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>
    
    private void atorPrimarioActionPerformed(java.awt.event.ActionEvent evt) {                                            
        // TODO add your handling code here:
    }                                           

    private void okActionPerformed(java.awt.event.ActionEvent evt) {                                   
        System.out.println(atorPrimario.getSelectedIndex());
        dispose();
    }     
    
    // Variables declaration - do not modify                     
    private javax.swing.JComboBox atorPrimario;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JButton ok;
    // End of variables declaration
}
