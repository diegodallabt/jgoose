package br.unioeste.jgoose.view;

import br.unioeste.jgoose.UseCases.Actor;
import br.unioeste.jgoose.controller.BPMNController;
import br.unioeste.jgoose.controller.Controller;
import br.unioeste.jgoose.controller.EditorWindowListener;
import br.unioeste.jgoose.controller.HorizontalBPMNTraceController;
import br.unioeste.jgoose.controller.HorizontalUseCaseTraceController;
import br.unioeste.jgoose.controller.VerticalTraceController;
import br.unioeste.jgoose.e4j.filters.ShapeFilenameFilter;
import br.unioeste.jgoose.e4j.swing.BasicUseCasesEditor;
import br.unioeste.jgoose.e4j.swing.EditorJFrame;
import br.unioeste.jgoose.e4j.swing.menubar.EditorMenuBar;
import br.unioeste.jgoose.model.FiltroDOC;
import br.unioeste.jgoose.model.UCActor;
import br.unioeste.jgoose.model.UCUseCase;
import br.unioeste.jgoose.util.IStarUtils;
import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGeometry;
import com.mxgraph.shape.mxStencilShape;
import com.mxgraph.util.mxResources;
import com.mxgraph.util.mxUtils;
import com.mxgraph.view.mxGraph;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultCellEditor;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import org.w3c.dom.Element;

/**
 *
 * @author Alysson Girotto
 * @author Victor Augusto Pozzan
 */
public class UseCasesViewBPMN extends javax.swing.JFrame {

    private Actor selectedActor = null;
    private String selectedCase = "";
    private EditorJFrame e4jInstance = null;
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger("console");

    /**
     * Creates new form UseCasesView
     */
    public UseCasesViewBPMN() {
        initComponents();
        setLocationRelativeTo(null);
        buttonDelete.setEnabled(false);
        Image Icone;
        Icone = Toolkit.getDefaultToolkit().getImage("./src/main/resources/icons/usecases_32x32.png");
        setIconImage(Icone);
        UIManager.put("OptionPane.yesButtonText", "Yes");
        UIManager.put("OptionPane.noButtonText", "No");
        updateTable();
    }

    /*
     * Método que atualiza a Tabela de Casos de Uso
     */
    public void updateTable() {
        DefaultTableModel tabCasosDeUso = new DefaultTableModel();
        tabCasosDeUso.addColumn("ID"); // 0
        tabCasosDeUso.addColumn("Use Case");
        tabCasosDeUso.addColumn("Info"); // 2
        tabCasosDeUso.addColumn("Delete"); // 3       
        //tabCasosDeUso.addColumn("Included"); //4
        //tabCasosDeUso.addColumn("Guideline"); // 5
        String vetCasosDeUso[] = new String[4];
        int cont = 1;
        // adiciona os dados dos casos de uso no modelo da tabela
        for (UCUseCase useCase : BPMNController.getUseCases()) {
            vetCasosDeUso[0] = "" + cont++;
            vetCasosDeUso[1] = useCase.getName();
            vetCasosDeUso[2] = "";
            vetCasosDeUso[3] = "";
            //vetCasosDeUso[2] = useCase.getPrimaryActor().getName();

            //String actors = "";
            // Percorre os atores, identificando atores associados ao atual
            //for(UCActor actor : useCase.getSecondaryActors()){
            //    actors += actor.getName() + "; ";
            //}
            //vetCasosDeUso[3] = actors;
            //String includedUseCases = "";
            // Percorre os casos de usos incluídos
            //for(UCUseCase useCaseIncluded : useCase.getIncludedUseCases()){
            //    includedUseCases += (Integer.parseInt(useCaseIncluded.getCode())+1) + " - " + useCaseIncluded.getName() + "; ";
            //}
            //vetCasosDeUso[4] = includedUseCases;
            //vetCasosDeUso[5] = useCase.getGuidelineUsed();
            tabCasosDeUso.addRow(vetCasosDeUso);

        }

        tabelUseCases.setModel(tabCasosDeUso);
        // seta a largura das colunas da tabela
        tabelUseCases.getColumnModel().getColumn(0).setPreferredWidth(30);
        tabelUseCases.getColumnModel().getColumn(1).setPreferredWidth(170);
        tabelUseCases.getColumnModel().getColumn(2).setPreferredWidth(20);
        tabelUseCases.getColumnModel().getColumn(3).setPreferredWidth(20);

        tabelUseCases.getColumnModel().getColumn(2).setCellRenderer(new ButtonRenderer());
        tabelUseCases.getColumnModel().getColumn(2).setCellEditor(new ButtonEditor(new JTextField()));

        tabelUseCases.getColumnModel().getColumn(3).setCellRenderer(new ButtonRendererDelete());
        tabelUseCases.getColumnModel().getColumn(3).setCellEditor(new ButtonDelete(new JTextField(), 0));
        //tabelUseCases.getColumnModel().getColumn(3).setCellRenderer(new ButtonRenderer());

        //tabelUseCases.getColumnModel().getColumn(4).setPreferredWidth(140);
        //tabelUseCases.getColumnModel().getColumn(5).setPreferredWidth(90);
    }

    /*
     * Método que insere um texto na área de texto de Casos de Uso com formatação
     */
    private void insertStyle(String text, SimpleAttributeSet style) {
        Document doc = textUseCases.getStyledDocument();
        try {
            doc.insertString(doc.getLength(), text, style);
        } catch (BadLocationException ex) {
            Logger.getLogger(UseCasesViewBPMN.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void insertStyleSave(String text, SimpleAttributeSet style) {
        Document doc = textUseCasesSave.getStyledDocument();
        try {
            doc.insertString(doc.getLength(), text, style);
        } catch (BadLocationException ex) {
            Logger.getLogger(UseCasesViewBPMN.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void tabelUseCasesValueChanged(ListSelectionEvent evt) {

        buttonDelete.setEnabled(true);
        buttonDiagram.setEnabled(true);
        int colunm = tabelUseCases.getSelectedColumn();
        int linha = tabelUseCases.getSelectedRow();
        if (linha != -1) {
            labelCasosDeUso.setText("Use Case Specification");
            textUseCases.setText(""); // limpa o painel de texto

            // define o objeto com formatação negrito negrito
            SimpleAttributeSet negrito = new SimpleAttributeSet();
            StyleConstants.setBold(negrito, true);

            // insere os dados do caso de uso selecionado
            UCUseCase useCase = BPMNController.getUseCases().get(linha);

            insertStyle("Use Case", negrito);
            insertStyle(": " + useCase.getDescription().getName() + "\n\n", null);

            insertStyle("CHARACTERISTIC INFORMATION\n", negrito);
            insertStyle("	Goal in Context", negrito);
            insertStyle(": " + useCase.getDescription().getGoal() + "\n", null);
            insertStyle("	Scope", negrito);
            insertStyle(": " + useCase.getDescription().getScope() + "\n", null);
            insertStyle("	Preconditions", negrito);
            insertStyle(": " + useCase.getDescription().getPreConditions() + "\n", null);
            insertStyle("	Success End Condition", negrito);
            insertStyle(": " + useCase.getDescription().getEndSucess() + "\n", null);
            insertStyle("	Failed End Condition", negrito);
            insertStyle(": " + useCase.getDescription().getEndFailure() + "\n", null);
            insertStyle("	Trigger", negrito);
            insertStyle(": " + useCase.getDescription().getTrigger() + "\n", null);
            insertStyle("	Primary actor", negrito);
            insertStyle(": " + useCase.getDescription().getPrimaryActor() + "\n", null);

            insertStyle("\nMAIN SUCCESS SCENARIO\n", negrito);
            int i = 1;
            for (String sentence : useCase.getDescription().getScenario()) {
                insertStyle("<passo " + i++ + "> - " + sentence + "\n", null);
            }
            insertStyle("\n", null);

            insertStyle("EXTENSIONS\n", negrito);
            insertStyle("\n", null);

            insertStyle("RELATED INFORMATION\n", negrito);
            insertStyle("	Priority", negrito);
            insertStyle(": " + useCase.getDescription().getPriority() + "\n", null);
            insertStyle("	Target performance", negrito);
            insertStyle(": " + useCase.getDescription().getPerformance() + "\n", null);
            insertStyle("	Frequency", negrito);
            insertStyle(": " + useCase.getDescription().getFrequency() + "\n", null);
            insertStyle("	Use Case father", negrito);
            insertStyle(": " + useCase.getDescription().getUseCaseFather() + "\n", null);
            insertStyle("	Included Use Cases", negrito);
            insertStyle(": " + useCase.getDescription().getIncludedUseCases() + "\n", null);
            insertStyle("	Secondary actors", negrito);
            insertStyle(": " + useCase.getDescription().getSecondaryActors() + "\n", null);
            insertStyle("	Additional information", negrito);
            insertStyle(": " + useCase.getDescription().getAdditionalInformation() + "\n", null);
        } else {
            textUseCases.setText("");
        }

        System.out.println("col:" + colunm + " row: " + linha);
        if (colunm == 3) {
            System.out.println("asa");
            int dialogButton = JOptionPane.YES_NO_OPTION;
            int dialogResult = JOptionPane.showConfirmDialog(null, "would you like to delete a " + linha + "?", "Warning", dialogButton);
            if (dialogResult == JOptionPane.YES_OPTION) {
                System.out.println("YESSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS");
            };
        }
    }

    /**
     * Abre uma janela GuidelinesDialogView
     */
    private void showGuidelinesDialog() {
        GuidelinesDialogView diretrizes;
        try {
            diretrizes = new GuidelinesDialogView(this, true);
            diretrizes.setModal(true);

            int x = this.getX() + (this.getWidth() - diretrizes.getWidth()) / 2;
            int y = this.getY() + (this.getHeight() - diretrizes.getHeight()) / 2;
            diretrizes.setLocation(x, y);

            diretrizes.setVisible(true);
        } catch (BadLocationException ex) {
            LOG.error(ex);
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

        jPanelBackground = new javax.swing.JPanel();
        jPanelHeader = new javax.swing.JPanel();
        buttonDiagram = new javax.swing.JButton();
        buttonGuidelines = new javax.swing.JButton();
        buttonDelete = new javax.swing.JButton();
        JLabelUseCasesFromBPMN = new javax.swing.JLabel();
        buttonSaveUseCases = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelUseCases = new javax.swing.JTable(){
            @Override
            public boolean isCellEditable(int row, int column){
                return true;
            }
        };
        jPanelMenuButtons = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        textUseCases = new javax.swing.JTextPane();
        labelCasosDeUso = new javax.swing.JLabel();
        buttonSaveDescription = new javax.swing.JButton();
        menuUseCases = new javax.swing.JMenuBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Use Cases");
        setResizable(false);

        jPanelBackground.setBackground(new java.awt.Color(244, 244, 244));

        jPanelHeader.setBackground(new java.awt.Color(255, 255, 255));

        buttonDiagram.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        buttonDiagram.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/usecase_diagram_wiz.gif"))); // NOI18N
        buttonDiagram.setText("Diagram");
        buttonDiagram.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonDiagramActionPerformed(evt);
            }
        });

        buttonGuidelines.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        buttonGuidelines.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/guidelines_32x32.png"))); // NOI18N
        buttonGuidelines.setText("Guidelines");
        buttonGuidelines.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonGuidelinesActionPerformed(evt);
            }
        });

        buttonDelete.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        buttonDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/delete_32x32.png"))); // NOI18N
        buttonDelete.setText("Delete");
        buttonDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonDeleteActionPerformed(evt);
            }
        });

        JLabelUseCasesFromBPMN.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        JLabelUseCasesFromBPMN.setText("Use Cases Mapped from BPMN");

        buttonSaveUseCases.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        buttonSaveUseCases.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/save_32x32.png"))); // NOI18N
        buttonSaveUseCases.setText("Save Use Cases");
        buttonSaveUseCases.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonSaveUseCasesActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelHeaderLayout = new javax.swing.GroupLayout(jPanelHeader);
        jPanelHeader.setLayout(jPanelHeaderLayout);
        jPanelHeaderLayout.setHorizontalGroup(
            jPanelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelHeaderLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelHeaderLayout.createSequentialGroup()
                        .addComponent(buttonDiagram, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(buttonGuidelines, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(buttonDelete, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonSaveUseCases, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(116, 116, 116))
            .addGroup(jPanelHeaderLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(JLabelUseCasesFromBPMN)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelHeaderLayout.setVerticalGroup(
            jPanelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelHeaderLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(JLabelUseCasesFromBPMN, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16)
                .addComponent(buttonDelete)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonSaveUseCases)
                    .addComponent(buttonGuidelines, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonDiagram, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(29, Short.MAX_VALUE))
        );

        tabelUseCases.setAutoCreateRowSorter(true);
        tabelUseCases.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        tabelUseCases.setForeground(new java.awt.Color(37, 172, 241));
        tabelUseCases.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Use Case", "Info", "Delete"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelUseCases.setGridColor(new java.awt.Color(254, 254, 254));
        tabelUseCases.setRowHeight(30);
        tabelUseCases.setSelectionBackground(new java.awt.Color(199, 235, 254));
        tabelUseCases.setSelectionForeground(new java.awt.Color(37, 172, 241));
        tabelUseCases.setShowHorizontalLines(false);
        tabelUseCases.setShowVerticalLines(false);
        tabelUseCases.setUpdateSelectionOnSort(false);
        tabelUseCases.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tabelUseCases.getTableHeader().setReorderingAllowed(false);

        tabelUseCases.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
            public void valueChanged(ListSelectionEvent evt) {
                tabelUseCases.repaint();
                tabelUseCasesValueChanged(evt);
            }
        });

        tabelUseCases.getTableHeader().setFont(new java.awt.Font("Roboto", 0, 14));
        jScrollPane1.setViewportView(tabelUseCases);
        if (tabelUseCases.getColumnModel().getColumnCount() > 0) {
            tabelUseCases.getColumnModel().getColumn(0).setResizable(false);
            tabelUseCases.getColumnModel().getColumn(1).setResizable(false);
            tabelUseCases.getColumnModel().getColumn(2).setResizable(false);
            tabelUseCases.getColumnModel().getColumn(3).setResizable(false);
        }
        tabelUseCases.getAccessibleContext().setAccessibleName("");
        tabelUseCases.getAccessibleContext().setAccessibleDescription("");
        tabelUseCases.getTableHeader().setForeground(new java.awt.Color(71, 92, 84));

        jPanelMenuButtons.setBackground(new java.awt.Color(11, 113, 165));

        javax.swing.GroupLayout jPanelMenuButtonsLayout = new javax.swing.GroupLayout(jPanelMenuButtons);
        jPanelMenuButtons.setLayout(jPanelMenuButtonsLayout);
        jPanelMenuButtonsLayout.setHorizontalGroup(
            jPanelMenuButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 64, Short.MAX_VALUE)
        );
        jPanelMenuButtonsLayout.setVerticalGroup(
            jPanelMenuButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 726, Short.MAX_VALUE)
        );

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        textUseCases.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(53, 178, 242)));
        textUseCases.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jScrollPane3.setViewportView(textUseCases);

        labelCasosDeUso.setBackground(new java.awt.Color(53, 178, 242));
        labelCasosDeUso.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        labelCasosDeUso.setForeground(new java.awt.Color(53, 178, 242));
        labelCasosDeUso.setText("Use Case Specification");

        buttonSaveDescription.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        buttonSaveDescription.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/save_32x32.png"))); // NOI18N
        buttonSaveDescription.setText("Save Description");
        buttonSaveDescription.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonSaveDescriptionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(labelCasosDeUso)
                        .addGap(48, 48, 48)
                        .addComponent(buttonSaveDescription, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 492, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelCasosDeUso)
                    .addComponent(buttonSaveDescription))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanelBackgroundLayout = new javax.swing.GroupLayout(jPanelBackground);
        jPanelBackground.setLayout(jPanelBackgroundLayout);
        jPanelBackgroundLayout.setHorizontalGroup(
            jPanelBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelBackgroundLayout.createSequentialGroup()
                .addComponent(jPanelMenuButtons, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addGroup(jPanelBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelBackgroundLayout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 439, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24))
                    .addGroup(jPanelBackgroundLayout.createSequentialGroup()
                        .addComponent(jPanelHeader, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        jPanelBackgroundLayout.setVerticalGroup(
            jPanelBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelBackgroundLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelHeader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 469, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanelBackgroundLayout.createSequentialGroup()
                .addComponent(jPanelMenuButtons, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        setJMenuBar(menuUseCases);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelBackground, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelBackground, javax.swing.GroupLayout.PREFERRED_SIZE, 688, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buttonGuidelinesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonGuidelinesActionPerformed
        buttonDelete.setEnabled(false);
        this.showGuidelinesDialog();
    }//GEN-LAST:event_buttonGuidelinesActionPerformed

    private void buttonDiagramActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonDiagramActionPerformed
        buttonDelete.setEnabled(false);
        try {
            this.showUseCasesDiagram();
        } catch (HeadlessException ex) {
            Logger.getLogger(UseCasesViewBPMN.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(UseCasesViewBPMN.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_buttonDiagramActionPerformed

    private void buttonDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonDeleteActionPerformed
        JOptionPane.showMessageDialog(null, "Not implemented yet");
        /*
        buttonDiagram.setEnabled(false);
        int linha = tabelUseCases.getSelectedRow();
        Actor actor = Controller.getActor("" + tabelUseCases.getValueAt(linha, 1));
        String casoSelecionado = "" + tabelUseCases.getValueAt(linha, 2);
        int c = JOptionPane.showConfirmDialog(null, "Confirm the deletion of the Use Case " + casoSelecionado + "?", "Deletion of Use Case", JOptionPane.YES_NO_OPTION);
        if (c == 0) {
            int n = actor.getUseCases().size();
            UseCase caso;
            System.out.println(" N: " + n);
            for (int i = 0; i < n; i++) {
                System.out.println("i = " + i);
                caso = actor.getUseCases().get(i);
                if (caso.getName().equals(casoSelecionado)) {
                    actor.getUseCases().remove(i);
                    updateTable();
                    buttonDelete.setEnabled(false);
                    JOptionPane.showMessageDialog(null, "Use Case deleted!", "Use Case deleted!", JOptionPane.INFORMATION_MESSAGE);
                    break;
                }
            }
        }*/
    }//GEN-LAST:event_buttonDeleteActionPerformed

    private void buttonSaveUseCasesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonSaveUseCasesActionPerformed
        this.showSaveUseCases();
    }//GEN-LAST:event_buttonSaveUseCasesActionPerformed

    private void buttonSaveDescriptionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonSaveDescriptionActionPerformed
        this.saveDescription();
    }//GEN-LAST:event_buttonSaveDescriptionActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel JLabelUseCasesFromBPMN;
    private javax.swing.JButton buttonDelete;
    private javax.swing.JButton buttonDiagram;
    private javax.swing.JButton buttonGuidelines;
    private javax.swing.JButton buttonSaveDescription;
    private javax.swing.JButton buttonSaveUseCases;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanelBackground;
    private javax.swing.JPanel jPanelHeader;
    private javax.swing.JPanel jPanelMenuButtons;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel labelCasosDeUso;
    private javax.swing.JMenuBar menuUseCases;
    public javax.swing.JTable tabelUseCases;
    private javax.swing.JTextPane textUseCases;
    // End of variables declaration//GEN-END:variables
    private javax.swing.JTextPane textUseCasesSave;

    // Clicou em Mostrar Diagrama de Casos de Uso
    private void showUseCasesDiagram() throws HeadlessException, IOException {
        if (e4jInstance == null) {
            e4jInstance = new EditorJFrame(1);
            e4jInstance.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            EditorWindowListener windowListener = new EditorWindowListener(this, e4jInstance);
            this.addWindowListener(windowListener);
            e4jInstance.addWindowListener(windowListener);
            this.addWindowListener(windowListener);
            BasicUseCasesEditor editor = (BasicUseCasesEditor) e4jInstance.getEditor();
            JMenuBar menubar = e4jInstance.getJMenuBar();
            JMenu fileMenu = ((EditorMenuBar) menubar).getFileMenu();
            String label1 = mxResources.get("traceabilityMaker", null, "Horizontal Traceability");
            JMenuItem menuItem1 = new JMenuItem(editor.bind(label1, new HorizontalUseCaseTraceController(e4jInstance)));
            fileMenu.add(menuItem1, 3);
            String label2 = mxResources.get("traceabilityMaker", null, "Vertical Traceability");
            JMenuItem menuItem2 = new JMenuItem(editor.bind(label2, new VerticalTraceController(1)));
            fileMenu.add(menuItem2, 3);
        }
        mxGraph graph = generateDiagram();

        e4jInstance.setVisible(true);
        this.setVisible(false);
    }

    // Gera e exibe o diagrama de Casos de Uso UML com base nas informações obtidas
    private mxGraph generateDiagram() throws IOException {

        List<UCActor> actorsList = BPMNController.getActors();
        List<UCUseCase> useCasesList = BPMNController.getUseCases();

        mxGraph graph = ((BasicUseCasesEditor) e4jInstance.getEditor()).getGraphComponent().getGraph();
        graph.removeCells(graph.getChildVertices(graph.getDefaultParent()));
        graph.removeCells(graph.getChildEdges(graph.getDefaultParent()));
        graph.getModel().beginUpdate();
        Element value;
        File shapesFolder = new File("resources/shapes/use cases diagram/");
        File[] files = shapesFolder.listFiles(ShapeFilenameFilter.instance);
        String nodeXml = mxUtils.readFile(files[0].getAbsolutePath());
        mxStencilShape newShape = new mxStencilShape(nodeXml);
        String styleActor = "shape=" + newShape.getName() + ";";
        nodeXml = mxUtils.readFile(files[1].getAbsolutePath());
        newShape = new mxStencilShape(nodeXml);
        String styleCase = "shape=" + newShape.getName() + ";";
        mxGeometry geo;

        // HashMaps para quardar as celular dos atores e casos de uso
        HashMap<String, mxCell> actors = new HashMap<>();
        HashMap<String, mxCell> cases = new HashMap<>();
        mxCell aresta;
        mxCell ext;

        // variaveis para controlar as posicoes X e Y dos elementos no diagrama
        int yActor = 110;
        int xActor = 80;
        int yCase = 30;
        int xCase = 400;
        String cod;

        // Seta atores e seus respectivos casos de uso
        for (UCActor actor : actorsList) {
            // cria ator e sua geometria e estilo
            value = IStarUtils.createActorUseCase();
            value.setAttribute("label", actor.getName());
            geo = new mxGeometry(50, yActor, 80, 80);

            if (actor.getChildren().isEmpty()) {
                geo.setX(xActor - 50);
            } else {
                geo.setX(xActor);
            }

            geo.setY(yActor);
            mxCell cell = new mxCell(value, geo, styleActor);
            cell.setVertex(true);
            actors.put(actor.getCode(), cell);
            graph.addCell(cell);

            // cria os casos de uso e arestas
            for (UCUseCase caso : actor.getUseCases()) {
                // cria caso de uso, sua geometria e estilo
                value = IStarUtils.createUseCase();
                value.setAttribute("label", caso.getName().replace("\"", ""));
                geo = new mxGeometry(xCase, yCase, 120, 60);
                xCase = xCase == 400 ? 700 : 400;
                geo.setX(xCase);
                geo.setY(yCase);
                yCase += 100;
                cod = String.valueOf(caso.getCode());

                // verifica se o caso de uso ainda não foi adicionado no grafo
                if (cases.get(cod) == null) {
                    cases.put(cod, new mxCell(value, geo, styleCase));
                    cases.get(cod).setVertex(true);
                    graph.addCell(cases.get(cod));
                }

                // cria uma aresta entre o ator e seu caso de uso
                value = IStarUtils.createAssociation();
                aresta = (mxCell) graph.createEdge(graph.getDefaultParent(), null, value, actors.get(actor.getCode()), cases.get(cod), "straight;endArrow=none;noLabel=1;shape=curvedEdge;edgeStyle=curvedEdgeStyle");
                aresta.setEdge(true);
                aresta.setStyle("straight;endArrow=none;noLabel=1;shape=curvedEdge;edgeStyle=curvedEdgeStyle");
                aresta.setSource(actors.get(actor.getCode()));
                aresta.setTarget(cases.get(cod));
                graph.addCell(aresta);
            }
            yActor = yCase + 100;
        }

        // adiciona as ligações do tipo generalização entre atores
        for (UCActor actor : actorsList) {
            String codFather = actor.getCode();
            String nameFather = actor.getName();
            for (int i = 0; i < actor.getChildren().size(); i++) {
                // cria a ligação generalization entre os atores
                value = IStarUtils.createGeneralization();
                aresta = (mxCell) graph.createEdge(graph.getDefaultParent(), null, value, actors.get(codFather), actors.get(actor.getChildren().get(i).getCode()), "straight;noLabel=1;endArrow=diamond;shape=curvedEdge;edgeStyle=curvedEdgeStyle");
                aresta.setEdge(true);

                // verifica se o ator está no diagrama
                if (actors.get(codFather) == null) {
                    // cria ator e sua geometria e estilo
                    value = IStarUtils.createActorUseCase();
                    value.setAttribute("label", nameFather);
                    geo = new mxGeometry(50, yActor, 80, 80);
                    geo.setX(xActor);
                    geo.setY(yActor);
                    actors.put(codFather, new mxCell(value, geo, styleActor));
                    actors.get(codFather).setVertex(true);
                    graph.addCell(actors.get(codFather));
                    yActor += 100;
                }

                aresta.setSource(actors.get(codFather));

                // verifica se o ator filho está no diagrama
                if (actor.getChildren().get(i) == null) {
                    // cria ator e sua geometria e estilo
                    value = IStarUtils.createActorUseCase();
                    value.setAttribute("label", actor.getChildren().get(i).getName());
                    geo = new mxGeometry(50, yActor, 80, 80);
                    geo.setX(50);
                    geo.setY(yActor);
                    actors.put(actor.getChildren().get(i).getCode(), new mxCell(value, geo, styleActor));
                    actors.get(actor.getChildren().get(i).getCode()).setVertex(true);
                    graph.addCell(actors.get(actor.getChildren().get(i).getCode()));
                    yActor += 100;
                }
                aresta.setTarget(actors.get(actor.getChildren().get(i).getCode()));
                aresta.setValue(value);
                aresta.setStyle("straight;noLabel=1;endArrow=block;endFill=0;endSize=14;shape=curvedEdge;edgeStyle=curvedEdgeStyle");
                graph.addCell(aresta);
            }
        }

        // Adiciona as ligacoes do tipo << include >>        
        for (UCUseCase useCase : useCasesList) {
            // verifica se o caso de uso ainda não foi adicionado no grafo
            if (cases.get(String.valueOf(useCase.getCode())) == null) {

                // Caso de uso ainda não inserido
                // cria caso de uso, sua geometria e estilo
                value = IStarUtils.createUseCase();
                value.setAttribute("label", useCase.getName().replace("\"", ""));
                geo = new mxGeometry(xCase, yCase, 120, 60);
                xCase = xCase == 400 ? 700 : 400;
                geo.setX(xCase);
                geo.setY(yCase);
                yCase += 100;
                cod = String.valueOf(useCase.getCode());

                cases.put(cod, new mxCell(value, geo, styleCase));
                cases.get(cod).setVertex(true);
                graph.addCell(cases.get(cod));
            }

            // cria as ligações do tipo << include >>
            for (int i = 0; i < useCase.getIncludedUseCases().size(); i++) {
                // Verifica se o caso de uso já foi inserido
                // verifica se o caso de uso ainda não foi adicionado no grafo
                if (cases.get(String.valueOf(useCase.getIncludedUseCases().get(i).getCode())) == null) {

                    // Caso de uso ainda não inserido
                    // cria caso de uso, sua geometria e estilo
                    value = IStarUtils.createUseCase();
                    value.setAttribute("label", useCase.getIncludedUseCases().get(i).getName().replace("\"", ""));
                    geo = new mxGeometry(xCase, yCase, 120, 60);
                    xCase = xCase == 400 ? 700 : 400;
                    geo.setX(xCase);
                    geo.setY(yCase);
                    yCase += 100;
                    cod = String.valueOf(useCase.getIncludedUseCases().get(i).getCode());

                    cases.put(cod, new mxCell(value, geo, styleCase));
                    cases.get(cod).setVertex(true);
                    graph.addCell(cases.get(cod));
                }

                // cria a ligação include entre os casos de uso
                value = IStarUtils.createInclude();
                aresta = (mxCell) graph.createEdge(graph.getDefaultParent(), null, value, cases.get(String.valueOf(useCase.getCode())), cases.get(String.valueOf(useCase.getIncludedUseCases().get(i).getCode())), "straight;dashed=1;endArrow=open;endSize=14;shape=curvedEdge;edgeStyle=curvedEdgeStyle");
                aresta.setSource(cases.get(String.valueOf(useCase.getCode())));
                aresta.setTarget(cases.get(String.valueOf(useCase.getIncludedUseCases().get(i).getCode())));
                aresta.setEdge(true);
                aresta.setValue(value);
                aresta.setStyle("straight;dashed=1;endArrow=open;endSize=14;shape=curvedEdge;edgeStyle=curvedEdgeStyle");
                graph.addCell(aresta);

            }
        }

        graph.getModel().endUpdate();
        return graph;

    }

    private void setSelectedActor(Actor actor) {
        selectedActor = actor;
    }

    private Actor getSelectedActor() {
        return this.selectedActor;
    }

    public String getSelectedCase() {
        return selectedCase;
    }

    public void setSelectedCase(String selectedCase) {
        this.selectedCase = selectedCase;
    }

    private void showSaveUseCases() {
        if (textUseCasesSave == null) {
            textUseCasesSave = new JTextPane();
        }

        textUseCasesSave.setText("");
        buttonDelete.setEnabled(false);
        String path = Controller.loadProperties();
        JFileChooser fileChooser = new JFileChooser(path);
        fileChooser.setFileFilter(new FiltroDOC());
        int resultado = fileChooser.showSaveDialog(null);
        Controller.saveProperties(fileChooser.getSelectedFile().getParent());
        if (resultado == JFileChooser.CANCEL_OPTION) {
            fileChooser.setVisible(false);
        } else {
            try {
                File file = fileChooser.getSelectedFile();
                try ( FileWriter writer = new FileWriter(file.getPath() + ".doc", true)) {

                    // define o objeto com formatação negrito negrito
                    SimpleAttributeSet negrito = new SimpleAttributeSet();
                    StyleConstants.setBold(negrito, true);

                    // insere os dados dos casos de uso
                    for (UCUseCase useCase : BPMNController.getUseCases()) {
                        textUseCasesSave.setText("");
                        insertStyleSave("\nUse Case", negrito);
                        insertStyleSave(": " + useCase.getDescription().getName() + "\n\n", null);

                        insertStyleSave("CHARACTERISTIC INFORMATION\n", negrito);
                        insertStyleSave("	Goal in Context", negrito);
                        insertStyleSave(": " + useCase.getDescription().getGoal() + "\n", null);
                        insertStyleSave("	Scope", negrito);
                        insertStyleSave(": " + useCase.getDescription().getScope() + "\n", null);
                        insertStyleSave("	Preconditions", negrito);
                        insertStyleSave(": " + useCase.getDescription().getPreConditions() + "\n", null);
                        insertStyleSave("	Success End Condition", negrito);
                        insertStyleSave(": " + useCase.getDescription().getEndSucess() + "\n", null);
                        insertStyleSave("	Failed End Condition", negrito);
                        insertStyleSave(": " + useCase.getDescription().getEndFailure() + "\n", null);
                        insertStyleSave("	Trigger", negrito);
                        insertStyleSave(": " + useCase.getDescription().getTrigger() + "\n", null);
                        insertStyleSave("	Primary actor", negrito);
                        insertStyleSave(": " + useCase.getDescription().getPrimaryActor() + "\n", null);

                        insertStyleSave("\nMAIN SUCCESS SCENARIO\n", negrito);
                        int i = 1;
                        for (String sentence : useCase.getDescription().getScenario()) {
                            insertStyleSave("<passo " + i++ + "> - " + sentence + "\n", null);
                        }
                        insertStyleSave("\n", null);

                        insertStyleSave("EXTENSIONS\n", negrito);
                        insertStyleSave("\n", null);
                        insertStyleSave("	Priority", negrito);
                        insertStyleSave(": " + useCase.getDescription().getPriority() + "\n", null);
                        insertStyleSave("	Target performance", negrito);
                        insertStyleSave(": " + useCase.getDescription().getPerformance() + "\n", null);
                        insertStyleSave("	Frequency", negrito);
                        insertStyleSave(": " + useCase.getDescription().getFrequency() + "\n", null);
                        insertStyleSave("	Use Case father", negrito);
                        insertStyleSave(": " + useCase.getDescription().getUseCaseFather() + "\n", null);
                        insertStyleSave("	Included Use Cases", negrito);
                        insertStyleSave(": " + useCase.getDescription().getIncludedUseCases() + "\n", null);
                        insertStyleSave("	Secondary actors", negrito);
                        insertStyleSave(": " + useCase.getDescription().getSecondaryActors() + "\n", null);
                        insertStyleSave("	Additional information", negrito);
                        insertStyleSave(": " + useCase.getDescription().getAdditionalInformation() + "\n", null);

                        writer.write(textUseCasesSave.getText());
                    }

                    JOptionPane.showMessageDialog(null, "File generated successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (IOException ex) {
                Logger.getLogger(UseCasesViewBPMN.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void saveDescription() {
        buttonDelete.setEnabled(false);
        String path = Controller.loadProperties();
        JFileChooser fileChooser = new JFileChooser(path);
        fileChooser.setFileFilter(new FiltroDOC());
        int resultado = fileChooser.showSaveDialog(null);
        Controller.saveProperties(fileChooser.getSelectedFile().getParent());
        if (resultado == JFileChooser.CANCEL_OPTION) {
            fileChooser.setVisible(false);
        } else {
            try {
                File file = fileChooser.getSelectedFile();
                try ( FileWriter writer = new FileWriter(file.getPath() + ".doc", true)) {
                    writer.write(textUseCases.getText());
                }
                JOptionPane.showMessageDialog(null, "File generated successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                Logger.getLogger(UseCasesViewBPMN.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

//BUTTON RENDERER CLASS
    class ButtonRenderer extends JButton implements TableCellRenderer {

        //CONSTRUCTOR
        public ButtonRenderer() {
            //SET BUTTON PROPERTIES
            setOpaque(true);
            setBackground(new java.awt.Color(255, 255, 255));
            setForeground(new java.awt.Color(15, 157, 229));
            setBorder(null);
            Icon icon = new ImageIcon("./src/main/resources/icons/info-icon.png");
            setIcon(icon);
            setBorderPainted(false);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object obj,
                boolean selected, boolean focused, int row, int col) {

            //SET PASSED OBJECT AS BUTTON TEXT
            setText((obj == null) ? "" : obj.toString());

            return this;
        }

    }

//BUTTON EDITOR CLASS
    class ButtonEditor extends DefaultCellEditor {

        private List informations;
        protected JButton btn;
        private String lbl;
        private Boolean clicked;
        private int row;

        public ButtonEditor(JTextField txt) {
            super(txt);

            btn = new JButton();
            btn.setOpaque(true);

            //WHEN BUTTON IS CLICKED
            btn.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    fireEditingStopped();
                }
            });
        }

        public String[] findElementAndInfos(int row) {
            int cont = 0;
            for (UCUseCase useCase : BPMNController.getUseCases()) {
                if (row == cont) {
                    String vetCasosDeUso[] = new String[6];
                    vetCasosDeUso[0] = "" + cont++;
                    vetCasosDeUso[1] = useCase.getName();
                    vetCasosDeUso[2] = useCase.getPrimaryActor().getName();

                    String actors = "";
                    // Percorre os atores, identificando atores associados ao atual
                    for (UCActor actor : useCase.getSecondaryActors()) {
                        actors += actor.getName() + "; ";
                    }
                    vetCasosDeUso[3] = actors;
                    String includedUseCases = "";
                    //Percorre os casos de usos incluídos
                    for (UCUseCase useCaseIncluded : useCase.getIncludedUseCases()) {
                        includedUseCases += (Integer.parseInt(useCaseIncluded.getCode()) + 1) + " - " + useCaseIncluded.getName() + "; ";
                    }
                    vetCasosDeUso[4] = includedUseCases;
                    vetCasosDeUso[5] = useCase.getGuidelineUsed();
                    return vetCasosDeUso;
                }
                cont++;
            }
            return null;
        }

        //OVERRIDE A COUPLE OF METHODS
        @Override
        public Component getTableCellEditorComponent(JTable table, Object obj,
                boolean selected, int row, int col) {

            //SET TEXT TO BUTTON,SET CLICKED TO TRUE,THEN RETURN THE BTN OBJECT
            lbl = (obj == null) ? "" : obj.toString();
            btn.setText(lbl);
            btn.setOpaque(true);
            btn.setBackground(new java.awt.Color(255, 255, 255));
            btn.setForeground(new java.awt.Color(15, 157, 229));
            btn.setBorder(null);
            btn.setBorderPainted(false);
            btn.setText(lbl);
            clicked = true;
            this.row = row;
            return btn;
        }

        //IF BUTTON CELL VALUE CHNAGES,IF CLICKED THAT IS
        @Override
        public Object getCellEditorValue() {

            if (clicked) {
                //SHOW US SOME MESSAGE
                String vetCasosDeUso[] = findElementAndInfos(row);

                MoreInfoUC info = new MoreInfoUC(new javax.swing.JFrame(), true, vetCasosDeUso);
                info.setVisible(true);
                //JOptionPane.showMessageDialog(btn, lbl + " Clicked");
            }
            //SET IT TO FALSE NOW THAT ITS CLICKED
            clicked = false;
            return new String(lbl);
        }

        @Override
        public boolean stopCellEditing() {

            //SET CLICKED TO FALSE FIRST
            clicked = false;
            return super.stopCellEditing();
        }

        @Override
        protected void fireEditingStopped() {
            // TODO Auto-generated method stub
            super.fireEditingStopped();
        }
    }

}

//BUTTON RENDERER CLASS
class ButtonRendererDelete extends JButton implements TableCellRenderer {

    //CONSTRUCTOR
    public ButtonRendererDelete() {
        //SET BUTTON PROPERTIES
        setOpaque(true);
        setOpaque(true);
        setBackground(new java.awt.Color(255, 255, 255));
        setForeground(new java.awt.Color(15, 157, 229));
        setBorder(null);
        Icon icon = new ImageIcon("./src/main/resources/icons/trash-icon.png");
        setIcon(icon);
        setBorderPainted(false);

    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object obj,
            boolean selected, boolean focused, int row, int col) {

        //SET PASSED OBJECT AS BUTTON TEXT
        setText((obj == null) ? "" : obj.toString());

        return this;
    }

}

//BUTTON EDITOR CLASS
class ButtonDelete extends DefaultCellEditor {

    protected JButton btn;
    private String lbl;
    private Boolean clicked;
    private int row;

    public ButtonDelete(JTextField txt, int row) {
        super(txt);

        btn = new JButton();

        //WHEN BUTTON IS CLICKED
        btn.addMouseListener((new MouseAdapter() {
            private Color background;

            @Override
            public void mousePressed(MouseEvent e) {
                System.out.println("");
            }
        }));

        btn.addActionListener((ActionEvent e) -> {
            fireEditingStopped();
        });
    }

    //OVERRIDE A COUPLE OF METHODS
    @Override
    public Component getTableCellEditorComponent(JTable table, Object obj,
            boolean selected, int row, int col) {

        //SET TEXT TO BUTTON,SET CLICKED TO TRUE,THEN RETURN THE BTN OBJECT
        lbl = (obj == null) ? "" : obj.toString();
        btn.setText(lbl);
        btn.setOpaque(true);
        btn.setBackground(new java.awt.Color(255, 255, 255));
        btn.setForeground(new java.awt.Color(15, 157, 229));
        btn.setBorder(null);
        btn.setBorderPainted(false);
        clicked = true;
        this.row = row;
        return btn;
    }

    //IF BUTTON CELL VALUE CHNAGES,IF CLICKED THAT IS
    @Override
    public Object getCellEditorValue() {

        if (clicked) {
            int dialogButton = JOptionPane.YES_NO_OPTION;
            int dialogResult = JOptionPane.showConfirmDialog(null, "would you like to delete?", "Warning", dialogButton);
            if (dialogResult == JOptionPane.YES_OPTION) {
                System.out.println("YESSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS");
            }

        }
        //SET IT TO FALSE NOW THAT ITS CLICKED
        clicked = false;
        return new String(lbl);
    }

    @Override
    public boolean stopCellEditing() {

        //SET CLICKED TO FALSE FIRST
        clicked = false;
        return super.stopCellEditing();
    }

    @Override
    protected void fireEditingStopped() {
        // TODO Auto-generated method stub
        super.fireEditingStopped();
    }
}

/*GUIDELINES
        buttonDelete.setEnabled(false);
        this.showGuidelinesDialog()


ABOUT
        buttonDelete.setEnabled(false);
        AboutDialogView about;
        about = new AboutDialogView(this);
        about.setModal(true);
        int x = this.getX() + (this.getWidth() - about.getWidth()) / 2;
        int y = this.getY() + (this.getHeight() - about.getHeight()) / 2;
        about.setLocation(x, y);
        about.setVisible(true);
 */
