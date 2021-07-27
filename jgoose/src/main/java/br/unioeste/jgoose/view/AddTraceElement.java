/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unioeste.jgoose.view;

import br.unioeste.jgoose.model.TokensTraceability;
import br.unioeste.jgoose.model.TracedAtorSistema;
import br.unioeste.jgoose.model.TracedInformacaoExterna;
import br.unioeste.jgoose.model.TracedInformacaoOrganizacional;
import br.unioeste.jgoose.model.TracedObjetivoSistema;
import br.unioeste.jgoose.model.TracedRequisitos;
import br.unioeste.jgoose.model.TracedStakeholders;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author Victor Augusto Pozzan
 */
public class AddTraceElement extends javax.swing.JDialog {

    public static final int YES = 0;
    public static final int NO = -1;

    private int choice = NO;
    private int typeTraceability = -1; //0 to Horizontal; 1 to Vertical
    private int type = -1;

    private TokensTraceability lista;

    public AddTraceElement(java.awt.Frame parent, boolean modal, int type, TokensTraceability lista) {
        super(parent, modal);
        this.type = type;
        this.lista = lista;

        initComponents();
        setLocationRelativeTo(null);
        checkTypeTraceability(type);

        initComboBoxModel();
        initComboBoxClasse();
        initComboBoxSegmento();
        //treat vetorInfo caso null                                    
    }

    public void checkTypeTraceability(int type) {
        if (type <= 3) {
            this.typeTraceability = 0;
        } else {
            this.typeTraceability = 1;
        }
    }

    public void initComboBoxModel() {
        if (typeTraceability == 0) {
            //comboBoxModel.addItem("");
            switch (type) {
                case 1:
                    comboBoxModel.addItem("BPMN");
                    break;
                case 2:
                    comboBoxModel.addItem("Use Case");
                    break;
                case 3:
                    comboBoxModel.addItem("I*");
                    break;
            }
            //comboBoxClass.removeItemAt(0);
            //comboBoxClass.setSelectedIndex(1);
            comboBoxModel.enable(false);
        } else {
            comboBoxModel.addItem("I*");
            comboBoxModel.addItem("BPMN");
            comboBoxModel.addItem("Use Case");
        }
    }

    public void initComboBoxClasse() {
        if (typeTraceability == 0) {
            comboBoxClass.addItem("Stakeholder");
            comboBoxClass.addItem("AtorSistema");
            comboBoxClass.addItem("Informação Externa");
            comboBoxClass.addItem("Informação Organizacional");
            comboBoxClass.addItem("Objetivo do Sistema");
            comboBoxClass.addItem("Requisito Funcional");
            comboBoxClass.addItem("Requisito Não Funcional");
        } else {
            comboBoxClass.addItem("Stakeholder");
            comboBoxClass.addItem("AtorSistema");
            comboBoxClass.addItem("Informação Externa");
            comboBoxClass.addItem("Informação Organizacional");
            comboBoxClass.addItem("Objetivo do Sistema");
            comboBoxClass.addItem("Requisito Funcional");
            comboBoxClass.addItem("Requisito Não Funcional");
        }
    }

    public void initComboBoxSegmento() {
        comboBoxSegmento.addItem("Informação Externa");
        comboBoxSegmento.addItem("Informação Organizacional");
        comboBoxSegmento.addItem("Gerencial");
        comboBoxSegmento.addItem("Desenvolvimento");
        comboBoxSegmento.addItem("Sem Segmento");

    }

    public int newTraceElement() {
        String name, model, segmento;
        int indexClass;
        try {
            name = getNameDataForm();
            model = getModelDataForm();
            indexClass = getIndexClassDataForm();
            segmento = getSegmentoDataForm();
            saveNewElementTraced(name, model, indexClass, segmento);
        } catch (Exception e) {

        }

        return choice;
    }

    private String getNameDataForm() {
        return jTextFieldName.getText();
    }

    private String getModelDataForm() {
        return String.valueOf(comboBoxModel.getSelectedItem());
    }

    private Integer getIndexClassDataForm() {
        return comboBoxClass.getSelectedIndex();
    }

    private String getSegmentoDataForm() {
        return String.valueOf(comboBoxSegmento.getSelectedItem());
    }

    private void saveNewElementTraced(String name, String model, Integer indexClasse, String segmento) {
        switch (indexClasse) {
            case 0://Stakeholder
                TracedStakeholders tracedStakeholder = new TracedStakeholders();
                tracedStakeholder.setLabel(name);
                tracedStakeholder.setModel(model);
                lista.setStakeholders(tracedStakeholder);
                break;
            case 1://AtorSistema
                TracedAtorSistema tracedAtorSistema = new TracedAtorSistema();
                tracedAtorSistema.setLabel(name);
                tracedAtorSistema.setModel(model);
                lista.setAtorSistema(tracedAtorSistema);
                break;
            case 2://Informação Externa
                TracedInformacaoExterna tracedInfoExterna = new TracedInformacaoExterna();
                tracedInfoExterna.setLabel(name);
                tracedInfoExterna.setModel(model);
                lista.setInformacaoExterna(tracedInfoExterna);
                break;
            case 3://Informação Organizacional
                TracedInformacaoOrganizacional tracedInfoOrganizacional = new TracedInformacaoOrganizacional();
                tracedInfoOrganizacional.setLabel(name);
                tracedInfoOrganizacional.setModel(model);
                lista.setInformacaoOrg(tracedInfoOrganizacional);
                break;
            case 4://Objetivo do Sistema
                TracedObjetivoSistema tracedObjSist = new TracedObjetivoSistema();
                tracedObjSist.setLabel(name);
                tracedObjSist.setModel(model);
                lista.setObjetivoSistema(tracedObjSist);
                break;
            case 5://Requisito Funcional
                TracedRequisitos tracedReq = new TracedRequisitos();
                tracedReq.setLabel(name);
                tracedReq.setModel(model);
                tracedReq.setAbreviacao("[RF");
                lista.setRequisitos(tracedReq);
                break;
            case 6://Requisito Não Funcional
                TracedRequisitos tracedNotFuncReq = new TracedRequisitos();
                tracedNotFuncReq.setLabel(name);
                tracedNotFuncReq.setModel(model);
                tracedNotFuncReq.setAbreviacao("[RNF");
                lista.setRequisitos(tracedNotFuncReq);
                break;
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
        jLabel5 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jButton1 = new javax.swing.JButton();
        jTextFieldName = new javax.swing.JTextField();
        comboBoxClass = new javax.swing.JComboBox<>();
        comboBoxSegmento = new javax.swing.JComboBox<>();
        comboBoxModel = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel5.setFont(new java.awt.Font("Roboto", 0, 14));
        jLabel5.setForeground(new java.awt.Color(15, 157, 229));
        jLabel5.setText("Segment:");

        jLabel4.setFont(new java.awt.Font("Roboto", 0, 14));
        jLabel4.setForeground(new java.awt.Color(15, 157, 229));
        jLabel4.setText("Class:");

        jLabel3.setFont(new java.awt.Font("Roboto", 0, 14));
        jLabel3.setForeground(new java.awt.Color(15, 157, 229));
        jLabel3.setText("Model:");

        jLabel2.setBackground(new java.awt.Color(15, 157, 229));
        jLabel2.setFont(new java.awt.Font("Roboto", 0, 14));
        jLabel2.setForeground(new java.awt.Color(15, 157, 229));
        jLabel2.setText("Name:");

        jLabel1.setBackground(new java.awt.Color(15, 157, 229));
        jLabel1.setFont(new java.awt.Font("Roboto", 1, 14));
        jLabel1.setForeground(new java.awt.Color(15, 157, 229));
        jLabel1.setText("Create new Traced Element");

        jButton1.setText("Save");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(comboBoxSegmento, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldName, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel1)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(226, 226, 226)
                                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(comboBoxModel, javax.swing.GroupLayout.Alignment.LEADING, 0, 227, Short.MAX_VALUE)
                                            .addComponent(comboBoxClass, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
                        .addGap(0, 20, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(7, 7, 7)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextFieldName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(comboBoxModel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(comboBoxClass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(comboBoxSegmento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addContainerGap())
        );

        jButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                choice = YES;
                JButton button = (JButton) e.getSource();
                SwingUtilities.getWindowAncestor(button).dispose();
            }
        });

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

        setBounds(0, 0, 386, 280);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> comboBoxClass;
    private javax.swing.JComboBox<String> comboBoxModel;
    private javax.swing.JComboBox<String> comboBoxSegmento;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField jTextFieldName;
    // End of variables declaration//GEN-END:variables

}
