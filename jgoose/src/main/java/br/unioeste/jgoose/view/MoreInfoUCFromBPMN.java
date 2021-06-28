/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unioeste.jgoose.view;

/**
 *
 * @author Victor Augusto Pozzan
 */
public class MoreInfoUCFromBPMN extends javax.swing.JDialog {

    /**
     * Creates new form MoreInfoUC
     * @param parent
     * @param modal
     * @param vetorInfo
     */
    public MoreInfoUCFromBPMN(java.awt.Frame parent, boolean modal, String[] vetorInfo) {
        super(parent, modal);
        
        initComponents();
        setLocationRelativeTo(null);

        //treat vetorInfo caso null
        int i = 0;
        for(String info : vetorInfo){
            if(info == null){
                vetorInfo[i] = "";
            }
            i++;
        }        
        if(!vetorInfo[1].isEmpty()){
            nameUC.setText(vetorInfo[1]);
        }else{
            nameUC.setText("");
        }
        
        if(!vetorInfo[2].isEmpty()){
            primaryActor.setText(vetorInfo[2]);
        }else{
            primaryActor.setText("");
        }
        
        if(!vetorInfo[3].isEmpty()){
            secondaryActors.setText(vetorInfo[3]);
        }else{
            secondaryActors.setText("");
        }
        
        if(!vetorInfo[4].isEmpty()){
            includedUC.setText(vetorInfo[1]);
        }else{
            includedUC.setText("");
        }
        
        if(!vetorInfo[5].isEmpty()){
            guideline.setText(vetorInfo[5]);
        }else{
            guideline .setText("");
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

        jPanel1 = new javax.swing.JPanel();
        guideline = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        includedUC = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        secondaryActors = new javax.swing.JLabel();
        primaryActor = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        nameUC = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        guideline.setFont(new java.awt.Font("Roboto", 0, 12));
        guideline.setText("jLabel10");

        jLabel5.setFont(new java.awt.Font("Roboto", 0, 14));
        jLabel5.setForeground(new java.awt.Color(15, 157, 229));
        jLabel5.setText("Guideline Used:");

        includedUC.setFont(new java.awt.Font("Roboto", 0, 12));
        includedUC.setText("jLabel9");

        jLabel4.setFont(new java.awt.Font("Roboto", 0, 14));
        jLabel4.setForeground(new java.awt.Color(15, 157, 229));
        jLabel4.setText("Included:");

        jLabel3.setFont(new java.awt.Font("Roboto", 0, 14));
        jLabel3.setForeground(new java.awt.Color(15, 157, 229));
        jLabel3.setText("Secondary Actors:");

        secondaryActors.setFont(new java.awt.Font("Roboto", 0, 12));
        secondaryActors.setText("jLabel8");

        primaryActor.setFont(new java.awt.Font("Roboto", 0, 12));
        primaryActor.setText("jLabel7");

        jLabel2.setBackground(new java.awt.Color(15, 157, 229));
        jLabel2.setFont(new java.awt.Font("Roboto", 0, 14));
        jLabel2.setForeground(new java.awt.Color(15, 157, 229));
        jLabel2.setText("Primary Actor:");

        jLabel1.setBackground(new java.awt.Color(15, 157, 229));
        jLabel1.setFont(new java.awt.Font("Roboto", 1, 14));
        jLabel1.setForeground(new java.awt.Color(15, 157, 229));
        jLabel1.setText("Details about");

        nameUC.setFont(new java.awt.Font("Roboto", 1, 12));
        nameUC.setForeground(new java.awt.Color(15, 157, 229));
        nameUC.setText("jLabel6");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(includedUC, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(secondaryActors, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(primaryActor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel5)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(guideline, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(0, 0, Short.MAX_VALUE))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel1)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(nameUC, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(nameUC))
                .addGap(7, 7, 7)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(9, 9, 9)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(primaryActor)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(secondaryActors)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(includedUC))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(guideline))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setBounds(0, 0, 386, 215);
    }// </editor-fold>//GEN-END:initComponents



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel guideline;
    private javax.swing.JLabel includedUC;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel nameUC;
    private javax.swing.JLabel primaryActor;
    private javax.swing.JLabel secondaryActors;
    // End of variables declaration//GEN-END:variables
}
