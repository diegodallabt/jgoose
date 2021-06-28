/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unioeste.jgoose.view;

import br.unioeste.jgoose.UseCases.UseCase;
import br.unioeste.jgoose.controller.BPMNController;
import br.unioeste.jgoose.controller.Controller;
import br.unioeste.jgoose.model.UCUseCase;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.SwingUtilities;

/**
 *
 * @author Victor Augusto Pozzan
 */
public final class AddUseCasefromBPMN extends JDialog {

    public static final int YES = 0;
    public static final int NO = -1;

    private int choice = NO;
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel title;
    private final String flag;
    private final Image iconJGOOSE = Toolkit.getDefaultToolkit().getImage("./src/main/resources/icons/jgoose.gif");



    public AddUseCasefromBPMN(java.awt.Frame parent, boolean modal, String flag) {
        super(parent, modal);
        this.flag = flag;
    }

    
    /**
     * Creates new form MoreInfoUC
     * @return 0 to YES
     */

    public int createDialogAdd() {
        jPanel1 = new javax.swing.JPanel();
        title = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(252, 252, 252));
        
        title.setFont(new java.awt.Font("Roboto", 0, 16));
        title.setText("New Use Case");

        jTextField1.setFont(new java.awt.Font("Roboto", 0, 14));
        jTextField1.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTextField1.setText(" Name");
        jTextField1.setToolTipText("");
        jTextField1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(132, 146, 166), 1, true));

        jButton1.setBackground(new java.awt.Color(53, 178, 242));
        jButton1.setFont(new java.awt.Font("Roboto", 0, 16));
        jButton1.setForeground(new java.awt.Color(250, 250, 250));
        jButton1.setText("Save");
        jButton1.setToolTipText("");
        jButton1.setBorder(null);
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.setFocusable(false);
        jButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                choice = YES;
                if (null == flag) {
                    System.out.println("error");
                } else switch (flag) {
                    case "bpmn":
                        createUseCase();
                        break;
                    case "i*":
                        createUseCaseIStar();
                        break;
                    default:
                        System.out.println("error");
                        break;
                }
                JButton button = (JButton) e.getSource();
                SwingUtilities.getWindowAncestor(button).dispose();
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(22, 22, 22)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(title)))
                                .addContainerGap(48, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addComponent(title)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(38, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setBounds(0, 0, 351, 216);

        JDialog dialog = new JDialog();
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setModal(true);
        dialog.getContentPane().add(jPanel1);
        dialog.pack();
        dialog.setLocationRelativeTo(null);

        dialog.setIconImage(iconJGOOSE);
        dialog.setVisible(true);

        return choice;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setIconImage(iconJGOOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 337, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 179, Short.MAX_VALUE)
        );

        setBounds(0, 0, 351, 216);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    private void createUseCase() {
        List<UCUseCase> useCases = BPMNController.getUseCases();

        UCUseCase useCase = new UCUseCase();

        String cod = UCUseCase.createID();
        useCase.setCode(cod);
        useCase.setName(jTextField1.getText());
        useCase.setInstanceCod(null);
        useCase.setGuidelineUsed("createdByUser");
        useCases.add(useCase);

    }

    public void createUseCaseIStar() {
        List<UseCase> usecases = Controller.getallUseCases();        
        usecases.add(new UseCase("", jTextField1.getText(), "", "", "", null, false));
    }
}
