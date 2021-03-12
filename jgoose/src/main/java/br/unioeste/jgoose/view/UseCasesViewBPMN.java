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
import java.awt.BorderLayout;
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
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.RowFilter;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
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
        setLocationRelativeTo(null);
        initComponents();
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
            tabCasosDeUso.addRow(vetCasosDeUso);

        }
        tabelUseCases.setAutoResizeMode(MAXIMIZED_HORIZ);
        tabelUseCases.setModel(tabCasosDeUso);
        // seta a largura das colunas da tabela
        tabelUseCases.getColumnModel().getColumn(0).setMaxWidth(20);
        tabelUseCases.getColumnModel().getColumn(1).setMinWidth(MAXIMIZED_HORIZ);
        tabelUseCases.getColumnModel().getColumn(2).setMaxWidth(20);
        tabelUseCases.getColumnModel().getColumn(3).setMaxWidth(20);

        tabelUseCases.getColumnModel().getColumn(2).setCellRenderer(new ButtonRenderer());
        tabelUseCases.getColumnModel().getColumn(2).setCellEditor(new ButtonEditor(new JTextField()));

        tabelUseCases.getColumnModel().getColumn(3).setCellRenderer(new ButtonRendererDelete());
        tabelUseCases.getColumnModel().getColumn(3).setCellEditor(new ButtonDelete(new JTextField(), 0));
        
        TableRowSorter<TableModel> rowSorter = new TableRowSorter<>(tabelUseCases.getModel());
        tabelUseCases.setRowSorter(rowSorter);
        jtfFilter.getDocument().addDocumentListener(new DocumentListener(){

            @Override
            public void insertUpdate(DocumentEvent e) {
                String text = jtfFilter.getText();

                if (text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                String text = jtfFilter.getText();

                if (text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

        });
        
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
        JLabelUseCasesFromBPMN = new javax.swing.JLabel();
        buttonSaveUseCases = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        labelSearchIcon = new javax.swing.JLabel();
        jtfFilter = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        textUseCases = new javax.swing.JTextPane();
        labelCasosDeUso = new javax.swing.JLabel();
        buttonSaveDescription = new javax.swing.JButton();
        buttonExportThisSpecification = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelUseCases = new javax.swing.JTable(){
            @Override
            public boolean isCellEditable(int row, int column){
                return true;
            }
        };
        jPanelMenuButtons = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        menuUseCases = new javax.swing.JMenuBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Use Cases");

        jPanelBackground.setBackground(new java.awt.Color(244, 244, 244));
        jPanelBackground.setMaximumSize(new java.awt.Dimension(3276799, 32767999));

        jPanelHeader.setBackground(new java.awt.Color(255, 255, 255));
        jPanelHeader.setMaximumSize(new java.awt.Dimension(32767999, 32767999));

        buttonDiagram.setBackground(new java.awt.Color(15, 157, 229));
        buttonDiagram.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        buttonDiagram.setForeground(new java.awt.Color(250, 250, 250));
        buttonDiagram.setText("Diagram");
        buttonDiagram.setBorder(null);
        buttonDiagram.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonDiagramActionPerformed(evt);
            }
        });

        buttonGuidelines.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        buttonGuidelines.setText("Guidelines");
        buttonGuidelines.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonGuidelinesActionPerformed(evt);
            }
        });

        JLabelUseCasesFromBPMN.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        JLabelUseCasesFromBPMN.setForeground(new java.awt.Color(37, 172, 241));
        JLabelUseCasesFromBPMN.setText("Use Cases Mapped from BPMN");

        buttonSaveUseCases.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        buttonSaveUseCases.setText("Export all Requirements");
        buttonSaveUseCases.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonSaveUseCasesActionPerformed(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(15, 157, 229));
        jButton4.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        jButton4.setForeground(new java.awt.Color(250, 250, 250));
        jButton4.setText("Add Requirement");
        jButton4.setBorder(null);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/undraw_file_analysis.png"))); // NOI18N

        jPanel2.setBackground(new java.awt.Color(244, 251, 255));
        jPanel2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(213, 239, 255), 2, true));

        labelSearchIcon.setBackground(new java.awt.Color(244, 251, 255));
        labelSearchIcon.setForeground(new java.awt.Color(244, 251, 255));
        labelSearchIcon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelSearchIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/searchIcon (3).png"))); // NOI18N
        labelSearchIcon.setToolTipText("");
        labelSearchIcon.setOpaque(true);

        jtfFilter.setBackground(new java.awt.Color(244, 251, 255));
        jtfFilter.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jtfFilter.setToolTipText("");
        jtfFilter.setBorder(null);
        jtfFilter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtfFilterActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(jtfFilter, javax.swing.GroupLayout.DEFAULT_SIZE, 386, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelSearchIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jtfFilter, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(labelSearchIcon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanelHeaderLayout = new javax.swing.GroupLayout(jPanelHeader);
        jPanelHeader.setLayout(jPanelHeaderLayout);
        jPanelHeaderLayout.setHorizontalGroup(
            jPanelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelHeaderLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelHeaderLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(JLabelUseCasesFromBPMN))
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                .addGroup(jPanelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonSaveUseCases, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jPanelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(buttonDiagram, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonGuidelines, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanelHeaderLayout.setVerticalGroup(
            jPanelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelHeaderLayout.createSequentialGroup()
                .addGroup(jPanelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelHeaderLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelHeaderLayout.createSequentialGroup()
                                .addGroup(jPanelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(buttonDiagram, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(21, 21, 21))))
                    .addGroup(jPanelHeaderLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(JLabelUseCasesFromBPMN, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(10, 10, 10)
                .addGroup(jPanelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(buttonSaveUseCases, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(buttonGuidelines, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(10, 10, 10))
        );

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        textUseCases.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(53, 178, 242)));
        textUseCases.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jScrollPane3.setViewportView(textUseCases);

        labelCasosDeUso.setBackground(new java.awt.Color(53, 178, 242));
        labelCasosDeUso.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        labelCasosDeUso.setForeground(new java.awt.Color(53, 178, 242));
        labelCasosDeUso.setText("Use Case Specification");

        buttonSaveDescription.setBackground(new java.awt.Color(53, 178, 242));
        buttonSaveDescription.setFont(new java.awt.Font("Roboto", 0, 16)); // NOI18N
        buttonSaveDescription.setForeground(new java.awt.Color(250, 250, 250));
        buttonSaveDescription.setText("Save");
        buttonSaveDescription.setToolTipText("");
        buttonSaveDescription.setBorder(null);
        buttonSaveDescription.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonSaveDescriptionActionPerformed(evt);
            }
        });

        buttonExportThisSpecification.setBackground(new java.awt.Color(53, 178, 242));
        buttonExportThisSpecification.setFont(new java.awt.Font("Roboto", 0, 16)); // NOI18N
        buttonExportThisSpecification.setForeground(new java.awt.Color(250, 250, 250));
        buttonExportThisSpecification.setText("Export this Specification");
        buttonExportThisSpecification.setToolTipText("");
        buttonExportThisSpecification.setBorder(null);
        buttonExportThisSpecification.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonExportThisSpecificationActionPerformed(evt);
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
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelCasosDeUso)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGap(200, 200, 200)
                                .addComponent(buttonSaveDescription, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buttonExportThisSpecification, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 492, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(labelCasosDeUso)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(buttonSaveDescription, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(buttonExportThisSpecification, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 405, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
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
        tabelUseCases.setPreferredSize(null);
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

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );

        javax.swing.GroupLayout jPanelBackgroundLayout = new javax.swing.GroupLayout(jPanelBackground);
        jPanelBackground.setLayout(jPanelBackgroundLayout);
        jPanelBackgroundLayout.setHorizontalGroup(
            jPanelBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelBackgroundLayout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(10, 10, 10)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(jPanelHeader, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanelBackgroundLayout.setVerticalGroup(
            jPanelBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelBackgroundLayout.createSequentialGroup()
                .addComponent(jPanelHeader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addGroup(jPanelBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(33, Short.MAX_VALUE))
        );

        jPanelMenuButtons.setBackground(new java.awt.Color(11, 113, 165));

        jButton1.setText("HOME");
        jButton1.setBorder(null);
        jButton1.setOpaque(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("i*");

        jButton3.setText("bpmn");

        jButton5.setText("UC");

        jButton6.setText("UC i*");

        jButton7.setText("UC BPMN");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton8.setText("Vertical T");

        jButton9.setText("Horizontal ");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelMenuButtonsLayout = new javax.swing.GroupLayout(jPanelMenuButtons);
        jPanelMenuButtons.setLayout(jPanelMenuButtonsLayout);
        jPanelMenuButtonsLayout.setHorizontalGroup(
            jPanelMenuButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelMenuButtonsLayout.createSequentialGroup()
                .addGroup(jPanelMenuButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanelMenuButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jButton5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0))
        );
        jPanelMenuButtonsLayout.setVerticalGroup(
            jPanelMenuButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelMenuButtonsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(9, 9, 9)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(350, Short.MAX_VALUE))
        );

        setJMenuBar(menuUseCases);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanelMenuButtons, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanelBackground, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jPanelBackground, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanelMenuButtons, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buttonGuidelinesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonGuidelinesActionPerformed
        this.showGuidelinesDialog();
    }//GEN-LAST:event_buttonGuidelinesActionPerformed

    private void buttonDiagramActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonDiagramActionPerformed
        try {
            this.showUseCasesDiagram();
        } catch (HeadlessException ex) {
            Logger.getLogger(UseCasesViewBPMN.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(UseCasesViewBPMN.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_buttonDiagramActionPerformed

    private void buttonSaveUseCasesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonSaveUseCasesActionPerformed
        this.showSaveUseCases();
    }//GEN-LAST:event_buttonSaveUseCasesActionPerformed

    private void buttonSaveDescriptionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonSaveDescriptionActionPerformed
        this.saveDescription();
    }//GEN-LAST:event_buttonSaveDescriptionActionPerformed

    private void buttonExportThisSpecificationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonExportThisSpecificationActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_buttonExportThisSpecificationActionPerformed

    private void jtfFilterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtfFilterActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtfFilterActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton7ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel JLabelUseCasesFromBPMN;
    private javax.swing.JButton buttonDiagram;
    private javax.swing.JButton buttonExportThisSpecification;
    private javax.swing.JButton buttonGuidelines;
    private javax.swing.JButton buttonSaveDescription;
    private javax.swing.JButton buttonSaveUseCases;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanelBackground;
    private javax.swing.JPanel jPanelHeader;
    private javax.swing.JPanel jPanelMenuButtons;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextField jtfFilter;
    private javax.swing.JLabel labelCasosDeUso;
    private javax.swing.JLabel labelSearchIcon;
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

//DELETE a USE FROM TABLE UC
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

