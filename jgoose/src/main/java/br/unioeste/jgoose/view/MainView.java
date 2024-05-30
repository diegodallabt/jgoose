package br.unioeste.jgoose.view;

import br.unioeste.jgoose.UCToIStar.MappingUCToIStar;
import br.unioeste.jgoose.UseCases.Actor;
import br.unioeste.jgoose.UseCases.ActorISA;
import br.unioeste.jgoose.UseCases.Extend;
import br.unioeste.jgoose.UseCases.Step;
import br.unioeste.jgoose.UseCases.UseCase;
import br.unioeste.jgoose.controller.BPMNController;
import br.unioeste.jgoose.controller.Controller;
import br.unioeste.jgoose.controller.EditorWindowListener;
import br.unioeste.jgoose.controller.ImportBPMNGraph;
import br.unioeste.jgoose.controller.ImportIStarGraph;
import br.unioeste.jgoose.controller.HorizontalControler;
import br.unioeste.jgoose.controller.ImportUseCaseGraph;
import br.unioeste.jgoose.controller.VerticalTraceController;
import br.unioeste.jgoose.e4j.actions.AddBoundary;
import br.unioeste.jgoose.e4j.filters.ShapeFilenameFilter;
import br.unioeste.jgoose.e4j.swing.BasicBPMNEditor;
import br.unioeste.jgoose.e4j.swing.BasicIStarEditor;
import br.unioeste.jgoose.e4j.swing.BasicUseCasesEditor;
import br.unioeste.jgoose.e4j.swing.EditorJFrame;
import br.unioeste.jgoose.e4j.swing.menubar.EditorMenuBar;
import br.unioeste.jgoose.model.IStarActorElement;
import br.unioeste.jgoose.model.IStarElement;
import br.unioeste.jgoose.model.IStarLink;
import br.unioeste.jgoose.model.TokensOpenOME;
import br.unioeste.jgoose.util.IStarUtils;
import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGeometry;
import com.mxgraph.shape.mxStencilShape;
import com.mxgraph.util.mxResources;
import com.mxgraph.util.mxUtils;
import com.mxgraph.view.mxGraph;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.basic.BasicMenuBarUI;
import javax.swing.text.BadLocationException;
import org.apache.log4j.Logger;
import org.w3c.dom.Element;

/**
 *
 * @authors @Diego Peliser
 * @Alysson @Girotto
 * @Victor @Augusto Pozzan
 */
public final class MainView extends javax.swing.JFrame {

    private TableArtifacts tableArtifacts = null;
    private UseCasesViewBPMN useCasesViewBPMN = null;
    private UseCasesViewIStar useCasesViewIStar = null;
    private TraceabilityView1 traceabilityView = null;
    private static final Logger LOG = Logger.getLogger("console");
    private EditorJFrame E4JiStar = null;
    private EditorJFrame E4JUseCases = null;
    private EditorJFrame E4JBPMN = null;
    private JFrame E4JTraceability = null;
    private BasicBPMNEditor bpmnEditor;

    private Image iconJGOOSE = Toolkit.getDefaultToolkit().getImage("./src/main/resources/icons/jgoose.gif");
    Font roboto;

    /**
     * Creates new form MainView
     *
     * @param E4JiStar
     * @param E4JBPMN
     * @param useCasesViewIStar
     * @param E4JUseCases
     * @param useCasesViewBPMN
     */
    public MainView(EditorJFrame E4JiStar, EditorJFrame E4JBPMN, EditorJFrame E4JUseCases,
            UseCasesViewIStar useCasesViewIStar, UseCasesViewBPMN useCasesViewBPMN) {
        this.E4JBPMN = E4JBPMN;
        this.E4JiStar = E4JiStar;
        this.E4JUseCases = E4JUseCases;
        this.useCasesViewIStar = useCasesViewIStar;
        this.useCasesViewBPMN = useCasesViewBPMN;
        initMain();
    }

    public MainView() {
        initMain();
    }

    public void initMain() {
        initComponents();
        setLocationRelativeTo(null);
        setIconImage(iconJGOOSE);
        //Set the theme of swing equals to SO.

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            SwingUtilities.updateComponentTreeUI(this);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            LOG.error("LookAndFeel Error!", e);
        }

        setDefaultLookAndFeelDecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(true);
        //dispose();
        //setVisible(true);

        try {
            InputStream myStream = new BufferedInputStream(new FileInputStream("./src/main/resources/fonts/Roboto-Black.ttf"));
            roboto = Font.createFont(Font.TRUETYPE_FONT, myStream);
            roboto = roboto.deriveFont(30f);

        } catch (IOException | FontFormatException e) {
            System.out.println(e);
        }
        menuBar.setOpaque(true);
        menuBar.setUI(new BasicMenuBarUI() {
            public void paint(Graphics g, JComponent c) {
                g.setColor(new java.awt.Color(11, 113, 165));
                g.fillRect(0, 0, c.getWidth(), c.getHeight());
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form BasicIStarEditor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelTitleAndButtons = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel4buttonOpenE4JiStar = new javax.swing.JPanel();
        buttonOpenE4JiStar = new javax.swing.JButton();
        jPanel4buttonOpenE4JBPMN = new javax.swing.JPanel();
        buttonOpenE4JBPMN = new javax.swing.JButton();
        jPanel4buttonOpenE4JUseCases = new javax.swing.JPanel();
        buttonOpenE4JUseCases = new javax.swing.JButton();
        jPanel4buttunMappingUseCases = new javax.swing.JPanel();
        buttunMappingUseCases = new javax.swing.JButton();
        jPanel4buttonBPMNToUseCases = new javax.swing.JPanel();
        buttonBPMNToUseCases = new javax.swing.JButton();
        jPanel4buttonVerticalTraceability = new javax.swing.JPanel();
        buttonVerticalTraceability = new javax.swing.JButton();
        jPanel4buttonHorizontalTraceability = new javax.swing.JPanel();
        buttonHorizontalTraceability = new javax.swing.JButton();
        jPanelImages = new javax.swing.JPanel();
        jLabelSubTitleImg2 = new javax.swing.JLabel();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        jPanel2 = new javax.swing.JPanel();
        jLabelImage3 = new javax.swing.JLabel();
        jLabelImg3 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabelImg2 = new javax.swing.JLabel();
        jLabelImage2 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabelImage1 = new javax.swing.JLabel();
        jLabelImg1 = new javax.swing.JLabel();
        jPanelFooter = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        menuBar = new javax.swing.JMenuBar();
        menuFile = new javax.swing.JMenu();
        fileOpenTelosFile = new javax.swing.JMenuItem();
        menuTools = new javax.swing.JMenu();
        toolsOpenE4JBPMNEditor = new javax.swing.JMenuItem();
        toolsOpenE4JEditor = new javax.swing.JMenuItem();
        toolsOpenE4JUCEditor = new javax.swing.JMenuItem();
        menuHelp = new javax.swing.JMenu();
        helpGuidelines = new javax.swing.JMenuItem();
        menuAbout = new javax.swing.JMenu();
        menuLes = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Home");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setForeground(java.awt.Color.white);
        setMaximumSize(null);
        setName("home"); // NOI18N
        setResizable(false);

        jPanelTitleAndButtons.setBackground(new java.awt.Color(254, 254, 254));
        jPanelTitleAndButtons.setAutoscrolls(true);

        jLabel2.setFont(new java.awt.Font("Roboto", 0, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(52, 63, 75));
        jLabel2.setText("helping software engineers");

        jLabel1.setFont(new java.awt.Font("Roboto", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(7, 69, 101));
        jLabel1.setText("JGOOSE");

        jPanel1.setBackground(new java.awt.Color(254, 254, 254));
        jPanel1.setMaximumSize(new java.awt.Dimension(2147483647, 0));

        jPanel4buttonOpenE4JiStar.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(90, 100, 120), 4, true));
        jPanel4buttonOpenE4JiStar.setMaximumSize(new java.awt.Dimension(126, 114));
        jPanel4buttonOpenE4JiStar.setMinimumSize(new java.awt.Dimension(126, 114));
        jPanel4buttonOpenE4JiStar.setPreferredSize(new java.awt.Dimension(126, 114));

        buttonOpenE4JiStar.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        buttonOpenE4JiStar.setForeground(new java.awt.Color(15, 157, 229));
        buttonOpenE4JiStar.setText("<html><center> i* <br> Editor ");
        buttonOpenE4JiStar.setToolTipText("Open i* Editor");
        buttonOpenE4JiStar.setBorder(null);
        buttonOpenE4JiStar.setBorderPainted(false);
        buttonOpenE4JiStar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        buttonOpenE4JiStar.setFocusable(false);
        buttonOpenE4JiStar.setIconTextGap(0);
        buttonOpenE4JiStar.setName(""); // NOI18N
        buttonOpenE4JiStar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                buttonOpenE4JiStarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                buttonOpenE4JiStarMouseExited(evt);
            }
        });
        buttonOpenE4JiStar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonOpenE4JiStarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4buttonOpenE4JiStarLayout = new javax.swing.GroupLayout(jPanel4buttonOpenE4JiStar);
        jPanel4buttonOpenE4JiStar.setLayout(jPanel4buttonOpenE4JiStarLayout);
        jPanel4buttonOpenE4JiStarLayout.setHorizontalGroup(
            jPanel4buttonOpenE4JiStarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4buttonOpenE4JiStarLayout.createSequentialGroup()
                .addGap(0, 1, Short.MAX_VALUE)
                .addComponent(buttonOpenE4JiStar, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel4buttonOpenE4JiStarLayout.setVerticalGroup(
            jPanel4buttonOpenE4JiStarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4buttonOpenE4JiStarLayout.createSequentialGroup()
                .addComponent(buttonOpenE4JiStar, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel4buttonOpenE4JBPMN.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(90, 100, 120), 4, true));
        jPanel4buttonOpenE4JBPMN.setMaximumSize(new java.awt.Dimension(126, 114));
        jPanel4buttonOpenE4JBPMN.setMinimumSize(new java.awt.Dimension(126, 114));

        buttonOpenE4JBPMN.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        buttonOpenE4JBPMN.setForeground(new java.awt.Color(15, 157, 229));
        buttonOpenE4JBPMN.setText("<html><center> BPMN <br> Editor");
        buttonOpenE4JBPMN.setToolTipText("Open BPMN Editor");
        buttonOpenE4JBPMN.setBorder(null);
        buttonOpenE4JBPMN.setBorderPainted(false);
        buttonOpenE4JBPMN.setFocusable(false);
        buttonOpenE4JBPMN.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                buttonOpenE4JBPMNMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                buttonOpenE4JBPMNMouseExited(evt);
            }
        });
        buttonOpenE4JBPMN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonOpenE4JBPMNActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4buttonOpenE4JBPMNLayout = new javax.swing.GroupLayout(jPanel4buttonOpenE4JBPMN);
        jPanel4buttonOpenE4JBPMN.setLayout(jPanel4buttonOpenE4JBPMNLayout);
        jPanel4buttonOpenE4JBPMNLayout.setHorizontalGroup(
            jPanel4buttonOpenE4JBPMNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4buttonOpenE4JBPMNLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(buttonOpenE4JBPMN, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel4buttonOpenE4JBPMNLayout.setVerticalGroup(
            jPanel4buttonOpenE4JBPMNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(buttonOpenE4JBPMN, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        buttonOpenE4JBPMN.setMargin(new Insets(20, 20, 20, 20));

        jPanel4buttonOpenE4JUseCases.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(90, 100, 120), 4, true));
        jPanel4buttonOpenE4JUseCases.setMaximumSize(new java.awt.Dimension(126, 114));
        jPanel4buttonOpenE4JUseCases.setMinimumSize(new java.awt.Dimension(126, 114));
        jPanel4buttonOpenE4JUseCases.setPreferredSize(new java.awt.Dimension(126, 114));

        buttonOpenE4JUseCases.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        buttonOpenE4JUseCases.setForeground(new java.awt.Color(15, 157, 229));
        buttonOpenE4JUseCases.setText("<html><center> Use Cases <br> Editor ");
        buttonOpenE4JUseCases.setToolTipText("Editor Use Cases");
        buttonOpenE4JUseCases.setBorder(null);
        buttonOpenE4JUseCases.setBorderPainted(false);
        buttonOpenE4JUseCases.setFocusable(false);
        buttonOpenE4JUseCases.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                buttonOpenE4JUseCasesMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                buttonOpenE4JUseCasesMouseExited(evt);
            }
        });
        buttonOpenE4JUseCases.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonOpenE4JUseCasesActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4buttonOpenE4JUseCasesLayout = new javax.swing.GroupLayout(jPanel4buttonOpenE4JUseCases);
        jPanel4buttonOpenE4JUseCases.setLayout(jPanel4buttonOpenE4JUseCasesLayout);
        jPanel4buttonOpenE4JUseCasesLayout.setHorizontalGroup(
            jPanel4buttonOpenE4JUseCasesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4buttonOpenE4JUseCasesLayout.createSequentialGroup()
                .addComponent(buttonOpenE4JUseCases, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 1, Short.MAX_VALUE))
        );
        jPanel4buttonOpenE4JUseCasesLayout.setVerticalGroup(
            jPanel4buttonOpenE4JUseCasesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4buttonOpenE4JUseCasesLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(buttonOpenE4JUseCases, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel4buttunMappingUseCases.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(90, 100, 120), 4, true));
        jPanel4buttunMappingUseCases.setMaximumSize(new java.awt.Dimension(126, 114));
        jPanel4buttunMappingUseCases.setMinimumSize(new java.awt.Dimension(126, 114));
        jPanel4buttunMappingUseCases.setPreferredSize(new java.awt.Dimension(126, 114));

        buttunMappingUseCases.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        buttunMappingUseCases.setForeground(new java.awt.Color(15, 157, 229));
        buttunMappingUseCases.setText("<html><center> Use Cases <br> from i* ");
        buttunMappingUseCases.setToolTipText("Use Cases UML from i*");
        buttunMappingUseCases.setBorder(null);
        buttunMappingUseCases.setBorderPainted(false);
        buttunMappingUseCases.setFocusable(false);
        buttunMappingUseCases.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                buttunMappingUseCasesMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                buttunMappingUseCasesMouseExited(evt);
            }
        });
        buttunMappingUseCases.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttunMappingUseCasesActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4buttunMappingUseCasesLayout = new javax.swing.GroupLayout(jPanel4buttunMappingUseCases);
        jPanel4buttunMappingUseCases.setLayout(jPanel4buttunMappingUseCasesLayout);
        jPanel4buttunMappingUseCasesLayout.setHorizontalGroup(
            jPanel4buttunMappingUseCasesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4buttunMappingUseCasesLayout.createSequentialGroup()
                .addComponent(buttunMappingUseCases, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 1, Short.MAX_VALUE))
        );
        jPanel4buttunMappingUseCasesLayout.setVerticalGroup(
            jPanel4buttunMappingUseCasesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4buttunMappingUseCasesLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(buttunMappingUseCases, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel4buttonBPMNToUseCases.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(90, 100, 120), 4));
        jPanel4buttonBPMNToUseCases.setMaximumSize(new java.awt.Dimension(126, 114));
        jPanel4buttonBPMNToUseCases.setMinimumSize(new java.awt.Dimension(126, 114));
        jPanel4buttonBPMNToUseCases.setPreferredSize(new java.awt.Dimension(126, 114));

        buttonBPMNToUseCases.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        buttonBPMNToUseCases.setForeground(new java.awt.Color(15, 157, 229));
        buttonBPMNToUseCases.setText("<html><center> Use Cases <br> from BPMN ");
        buttonBPMNToUseCases.setToolTipText("Use Cases from BPMN");
        buttonBPMNToUseCases.setBorder(null);
        buttonBPMNToUseCases.setBorderPainted(false);
        buttonBPMNToUseCases.setFocusable(false);
        buttonBPMNToUseCases.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                buttonBPMNToUseCasesMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                buttonBPMNToUseCasesMouseExited(evt);
            }
        });
        buttonBPMNToUseCases.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonBPMNToUseCasesActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4buttonBPMNToUseCasesLayout = new javax.swing.GroupLayout(jPanel4buttonBPMNToUseCases);
        jPanel4buttonBPMNToUseCases.setLayout(jPanel4buttonBPMNToUseCasesLayout);
        jPanel4buttonBPMNToUseCasesLayout.setHorizontalGroup(
            jPanel4buttonBPMNToUseCasesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4buttonBPMNToUseCasesLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(buttonBPMNToUseCases, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel4buttonBPMNToUseCasesLayout.setVerticalGroup(
            jPanel4buttonBPMNToUseCasesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4buttonBPMNToUseCasesLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(buttonBPMNToUseCases, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel4buttonVerticalTraceability.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(90, 100, 120), 4, true));
        jPanel4buttonVerticalTraceability.setMaximumSize(new java.awt.Dimension(126, 114));
        jPanel4buttonVerticalTraceability.setMinimumSize(new java.awt.Dimension(126, 114));
        jPanel4buttonVerticalTraceability.setPreferredSize(new java.awt.Dimension(126, 114));

        buttonVerticalTraceability.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        buttonVerticalTraceability.setForeground(new java.awt.Color(15, 157, 229));
        buttonVerticalTraceability.setText("<html><center> Vertical <br>  Traceability");
        buttonVerticalTraceability.setBorder(null);
        buttonVerticalTraceability.setBorderPainted(false);
        buttonVerticalTraceability.setFocusable(false);
        buttonVerticalTraceability.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                buttonVerticalTraceabilityMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                buttonVerticalTraceabilityMouseExited(evt);
            }
        });
        buttonVerticalTraceability.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonVerticalTraceabilityActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4buttonVerticalTraceabilityLayout = new javax.swing.GroupLayout(jPanel4buttonVerticalTraceability);
        jPanel4buttonVerticalTraceability.setLayout(jPanel4buttonVerticalTraceabilityLayout);
        jPanel4buttonVerticalTraceabilityLayout.setHorizontalGroup(
            jPanel4buttonVerticalTraceabilityLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4buttonVerticalTraceabilityLayout.createSequentialGroup()
                .addComponent(buttonVerticalTraceability, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 2, Short.MAX_VALUE))
        );
        jPanel4buttonVerticalTraceabilityLayout.setVerticalGroup(
            jPanel4buttonVerticalTraceabilityLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4buttonVerticalTraceabilityLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(buttonVerticalTraceability, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel4buttonHorizontalTraceability.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(90, 100, 120), 4));
        jPanel4buttonHorizontalTraceability.setMaximumSize(new java.awt.Dimension(126, 114));
        jPanel4buttonHorizontalTraceability.setMinimumSize(new java.awt.Dimension(126, 114));
        jPanel4buttonHorizontalTraceability.setPreferredSize(new java.awt.Dimension(126, 114));

        buttonHorizontalTraceability.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        buttonHorizontalTraceability.setForeground(new java.awt.Color(15, 157, 229));
        buttonHorizontalTraceability.setText("<html><center> Horizontal <br>  Traceability");
        buttonHorizontalTraceability.setToolTipText("");
        buttonHorizontalTraceability.setBorder(null);
        buttonHorizontalTraceability.setBorderPainted(false);
        buttonHorizontalTraceability.setFocusable(false);
        buttonHorizontalTraceability.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                buttonHorizontalTraceabilityMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                buttonHorizontalTraceabilityMouseExited(evt);
            }
        });
        buttonHorizontalTraceability.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonHorizontalTraceabilityActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4buttonHorizontalTraceabilityLayout = new javax.swing.GroupLayout(jPanel4buttonHorizontalTraceability);
        jPanel4buttonHorizontalTraceability.setLayout(jPanel4buttonHorizontalTraceabilityLayout);
        jPanel4buttonHorizontalTraceabilityLayout.setHorizontalGroup(
            jPanel4buttonHorizontalTraceabilityLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4buttonHorizontalTraceabilityLayout.createSequentialGroup()
                .addComponent(buttonHorizontalTraceability, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 2, Short.MAX_VALUE))
        );
        jPanel4buttonHorizontalTraceabilityLayout.setVerticalGroup(
            jPanel4buttonHorizontalTraceabilityLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4buttonHorizontalTraceabilityLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(buttonHorizontalTraceability, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel4buttonOpenE4JiStar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(jPanel4buttonOpenE4JBPMN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(jPanel4buttonOpenE4JUseCases, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(jPanel4buttunMappingUseCases, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(jPanel4buttonBPMNToUseCases, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(jPanel4buttonVerticalTraceability, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(jPanel4buttonHorizontalTraceability, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4buttunMappingUseCases, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel4buttonBPMNToUseCases, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel4buttonVerticalTraceability, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel4buttonHorizontalTraceability, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel4buttonOpenE4JiStar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jPanel4buttonOpenE4JUseCases, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel4buttonOpenE4JBPMN, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanelTitleAndButtonsLayout = new javax.swing.GroupLayout(jPanelTitleAndButtons);
        jPanelTitleAndButtons.setLayout(jPanelTitleAndButtonsLayout);
        jPanelTitleAndButtonsLayout.setHorizontalGroup(
            jPanelTitleAndButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTitleAndButtonsLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanelTitleAndButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelTitleAndButtonsLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel2)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelTitleAndButtonsLayout.setVerticalGroup(
            jPanelTitleAndButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTitleAndButtonsLayout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addGroup(jPanelTitleAndButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jLabelSubTitleImg2.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jLabelSubTitleImg2.setForeground(new java.awt.Color(71, 92, 84));
        jLabelSubTitleImg2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelSubTitleImg2.setText("BPMN for Use Cases or i* for Use Cases");

        jLabelImage3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelImage3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/img3-UndrawSoftwareEngineer.png"))); // NOI18N
        jLabelImage3.setIconTextGap(0);
        jLabelImage3.setMinimumSize(new java.awt.Dimension(1, 1));

        jLabelImg3.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabelImg3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelImg3.setText("Requirements traceability ");
        jLabelImg3.setToolTipText("");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabelImg3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabelImage3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelImg3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabelImage3, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jLabelImg2.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabelImg2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelImg2.setText("Use Case discovering requeriments  ");

        jLabelImage2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelImage2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/img2-UndrawAnalyze.png"))); // NOI18N
        jLabelImage2.setToolTipText("");
        jLabelImage2.setIconTextGap(0);
        jLabelImage2.setMinimumSize(new java.awt.Dimension(1, 1));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabelImage2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26))
            .addComponent(jLabelImg2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelImg2)
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(jLabelImage2, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jLabelImage1.setBackground(new java.awt.Color(204, 204, 204));
        jLabelImage1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelImage1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/img1-UndrawMind.png"))); // NOI18N
        jLabelImage1.setIconTextGap(0);
        jLabelImage1.setMinimumSize(new java.awt.Dimension(1, 1));

        jLabelImg1.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabelImg1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelImg1.setText("Creating diagrams in BMPN, i* and Use Case");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabelImg1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabelImage1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 329, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelImg1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelImage1, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanelImagesLayout = new javax.swing.GroupLayout(jPanelImages);
        jPanelImages.setLayout(jPanelImagesLayout);
        jPanelImagesLayout.setHorizontalGroup(
            jPanelImagesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelImagesLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(filler1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(368, 368, 368))
            .addGroup(jPanelImagesLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jLabelSubTitleImg2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanelImagesLayout.setVerticalGroup(
            jPanelImagesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelImagesLayout.createSequentialGroup()
                .addComponent(filler1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelImagesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelSubTitleImg2)
                .addGap(19, 19, 19))
        );

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/img4-LogoUnioesteLes.png"))); // NOI18N

        jLabel4.setFont(new java.awt.Font("Roboto", 0, 11)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(71, 82, 94));
        jLabel4.setText("version 5.7");

        javax.swing.GroupLayout jPanelFooterLayout = new javax.swing.GroupLayout(jPanelFooter);
        jPanelFooter.setLayout(jPanelFooterLayout);
        jPanelFooterLayout.setHorizontalGroup(
            jPanelFooterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelFooterLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addGap(10, 10, 10))
        );
        jPanelFooterLayout.setVerticalGroup(
            jPanelFooterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelFooterLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanelFooterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        menuBar.setBackground(new java.awt.Color(255, 102, 0));
        menuBar.setForeground(new java.awt.Color(153, 0, 102));
        menuBar.setFont(new java.awt.Font("Roboto Black", 0, 18)); // NOI18N
        menuBar.setMaximumSize(new java.awt.Dimension(154, 50));
        menuBar.setMinimumSize(new java.awt.Dimension(154, 25));
        menuBar.setName(""); // NOI18N
        menuBar.setPreferredSize(new java.awt.Dimension(154, 50));
        menuBar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                menuBarFocusGained(evt);
            }
        });

        menuFile.setBackground(new java.awt.Color(11, 113, 165));
        menuFile.setForeground(new java.awt.Color(254, 254, 254));
        menuFile.setText("Files");
        menuFile.setFont(new java.awt.Font("Roboto Black", 0, 18)); // NOI18N
        menuFile.setMargin(new java.awt.Insets(0, 20, 0, 20));
        menuFile.setOpaque(true);
        menuFile.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                menuFileMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                menuFileMouseExited(evt);
            }
        });

        fileOpenTelosFile.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        fileOpenTelosFile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/folder_open_16x16.png"))); // NOI18N
        fileOpenTelosFile.setText("Open Telos File");
        fileOpenTelosFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileOpenTelosFileActionPerformed(evt);
            }
        });
        menuFile.add(fileOpenTelosFile);

        menuBar.add(menuFile);

        menuTools.setBackground(new java.awt.Color(11, 113, 165));
        menuTools.setForeground(new java.awt.Color(254, 254, 254));
        menuTools.setText("Tools");
        menuTools.setFont(new java.awt.Font("Roboto Black", 0, 18)); // NOI18N
        menuTools.setMargin(new java.awt.Insets(0, 20, 0, 20));
        menuTools.setOpaque(true);
        menuTools.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                menuToolsMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                menuToolsMouseExited(evt);
            }
        });

        toolsOpenE4JBPMNEditor.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_B, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        toolsOpenE4JBPMNEditor.setText("E4J BPMN");
        toolsOpenE4JBPMNEditor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toolsOpenE4JBPMNEditorActionPerformed(evt);
            }
        });
        menuTools.add(toolsOpenE4JBPMNEditor);

        toolsOpenE4JEditor.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_I, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        toolsOpenE4JEditor.setText("E4J i*");
        toolsOpenE4JEditor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toolsOpenE4JEditorActionPerformed(evt);
            }
        });
        menuTools.add(toolsOpenE4JEditor);

        toolsOpenE4JUCEditor.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_U, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        toolsOpenE4JUCEditor.setText("E4J Use Cases");
        toolsOpenE4JUCEditor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toolsOpenE4JUCEditorActionPerformed(evt);
            }
        });
        menuTools.add(toolsOpenE4JUCEditor);

        menuBar.add(menuTools);

        menuHelp.setBackground(new java.awt.Color(11, 113, 165));
        menuHelp.setForeground(new java.awt.Color(254, 254, 254));
        menuHelp.setText("Help");
        menuHelp.setFocusTraversalPolicyProvider(true);
        menuHelp.setFont(new java.awt.Font("Roboto Black", 0, 18)); // NOI18N
        menuHelp.setMargin(new java.awt.Insets(0, 20, 0, 20));
        menuHelp.setOpaque(true);
        menuHelp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                menuHelpMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                menuHelpMouseExited(evt);
            }
        });

        helpGuidelines.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, 0));
        helpGuidelines.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/guidelines_16x16.png"))); // NOI18N
        helpGuidelines.setText("Guidelines");
        helpGuidelines.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                helpGuidelinesActionPerformed(evt);
            }
        });
        menuHelp.add(helpGuidelines);

        menuBar.add(menuHelp);

        menuAbout.setBackground(new java.awt.Color(11, 113, 165));
        menuAbout.setForeground(new java.awt.Color(254, 254, 254));
        menuAbout.setText("About");
        menuAbout.setFont(new java.awt.Font("Roboto Black", 0, 18)); // NOI18N
        menuAbout.setMargin(new java.awt.Insets(0, 20, 0, 20));
        menuAbout.setOpaque(true);
        menuAbout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuAboutMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                menuAboutMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                menuAboutMouseExited(evt);
            }
        });
        menuBar.add(menuAbout);

        menuLes.setBackground(new java.awt.Color(11, 113, 165));
        menuLes.setForeground(new java.awt.Color(254, 254, 254));
        menuLes.setText("Les");
        menuLes.setFont(new java.awt.Font("Roboto Black", 1, 18)); // NOI18N
        menuLes.setMargin(new java.awt.Insets(0, 20, 0, 20));
        menuLes.setOpaque(true);
        menuLes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuLesMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                menuLesMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                menuLesMouseExited(evt);
            }
        });
        menuBar.add(menuLes);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelTitleAndButtons, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanelImages, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanelFooter, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanelTitleAndButtons, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanelImages, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jPanelFooter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        getAccessibleContext().setAccessibleDescription("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buttonHorizontalTraceabilityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonHorizontalTraceabilityActionPerformed
        int hasTraceability = HorizontalControler.index;
        if (hasTraceability == -1) {
            JOptionPane.showMessageDialog(null, "No horizontal traceability was done");
        } else {
            this.setVisible(false);
            HorizontalControler.openViewTraceabilityHorizontal();
        }
    }//GEN-LAST:event_buttonHorizontalTraceabilityActionPerformed

    private void buttonBPMNToUseCasesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonBPMNToUseCasesActionPerformed
        this.bpmnUCView();
    }//GEN-LAST:event_buttonBPMNToUseCasesActionPerformed

    private void buttunMappingUseCasesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttunMappingUseCasesActionPerformed
        this.iStarUCView();
    }//GEN-LAST:event_buttunMappingUseCasesActionPerformed

    private void buttonOpenE4JUseCasesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonOpenE4JUseCasesActionPerformed
        try {
            this.showE4JUseCases();
        } catch (HeadlessException ex) {
            java.util.logging.Logger.getLogger(MainView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(MainView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_buttonOpenE4JUseCasesActionPerformed

    private void buttonOpenE4JBPMNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonOpenE4JBPMNActionPerformed
        try {
            this.showE4JBPMN();
        } catch (HeadlessException ex) {
            java.util.logging.Logger.getLogger(MainView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(MainView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_buttonOpenE4JBPMNActionPerformed

    private void menuBarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_menuBarFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_menuBarFocusGained

    private void helpGuidelinesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_helpGuidelinesActionPerformed
        try {
            this.showGuidelinesDialog();
        } catch (URISyntaxException ex) {
            java.util.logging.Logger.getLogger(MainView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_helpGuidelinesActionPerformed

    private void toolsOpenE4JUCEditorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toolsOpenE4JUCEditorActionPerformed
        try {
            this.showE4JUseCases();
        } catch (HeadlessException ex) {
            java.util.logging.Logger.getLogger(MainView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(MainView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_toolsOpenE4JUCEditorActionPerformed

    private void toolsOpenE4JEditorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toolsOpenE4JEditorActionPerformed
        try {
            this.showE4JiStar();
        } catch (HeadlessException ex) {
            java.util.logging.Logger.getLogger(MainView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(MainView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_toolsOpenE4JEditorActionPerformed

    private void toolsOpenE4JBPMNEditorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toolsOpenE4JBPMNEditorActionPerformed
        try {
            this.showE4JBPMN();
        } catch (HeadlessException ex) {
            java.util.logging.Logger.getLogger(MainView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(MainView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_toolsOpenE4JBPMNEditorActionPerformed

    private void fileOpenTelosFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileOpenTelosFileActionPerformed
        Controller.openTelosFile();
    }//GEN-LAST:event_fileOpenTelosFileActionPerformed

    private void menuAboutMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuAboutMouseEntered
        menuAbout.setBackground(new java.awt.Color(59, 141, 183));
    }//GEN-LAST:event_menuAboutMouseEntered

    private void menuAboutMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuAboutMouseExited
        menuAbout.setBackground(new java.awt.Color(11, 113, 165));
    }//GEN-LAST:event_menuAboutMouseExited

    private void menuHelpMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuHelpMouseEntered
        menuHelp.setBackground(new java.awt.Color(59, 141, 183));
    }//GEN-LAST:event_menuHelpMouseEntered

    private void menuHelpMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuHelpMouseExited
        menuHelp.setBackground(new java.awt.Color(11, 113, 165));
    }//GEN-LAST:event_menuHelpMouseExited

    private void menuToolsMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuToolsMouseEntered
        menuTools.setBackground(new java.awt.Color(59, 141, 183));
    }//GEN-LAST:event_menuToolsMouseEntered

    private void menuToolsMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuToolsMouseExited
        menuTools.setBackground(new java.awt.Color(11, 113, 165));
    }//GEN-LAST:event_menuToolsMouseExited

    private void menuFileMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuFileMouseEntered
        menuFile.setBackground(new java.awt.Color(59, 141, 183));
    }//GEN-LAST:event_menuFileMouseEntered

    private void menuFileMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuFileMouseExited
        menuFile.setBackground(new java.awt.Color(11, 113, 165));
    }//GEN-LAST:event_menuFileMouseExited

    private void menuLesMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuLesMouseEntered
        menuLes.setBackground(new java.awt.Color(59, 141, 183));
    }//GEN-LAST:event_menuLesMouseEntered

    private void menuLesMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuLesMouseExited
        menuLes.setBackground(new java.awt.Color(11, 113, 165));
    }//GEN-LAST:event_menuLesMouseExited

    private void buttonVerticalTraceabilityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonVerticalTraceabilityActionPerformed
        int hasTraceability = VerticalTraceController.index;
        if (hasTraceability == -1) {
            JOptionPane.showMessageDialog(null, "No vertical traceability was done");
        } else {
            this.setVisible(false);
            VerticalTraceController.openVerticalTraceabilityView();
        }
    }//GEN-LAST:event_buttonVerticalTraceabilityActionPerformed

    private void buttonOpenE4JiStarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonOpenE4JiStarActionPerformed
        try {
            this.showE4JiStar();
        } catch (HeadlessException ex) {
            java.util.logging.Logger.getLogger(MainView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(MainView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_buttonOpenE4JiStarActionPerformed

    private void buttonOpenE4JiStarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buttonOpenE4JiStarMouseExited
        buttonOpenE4JiStar.setBackground(new java.awt.Color(255, 255, 255));
    }//GEN-LAST:event_buttonOpenE4JiStarMouseExited

    private void buttonOpenE4JiStarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buttonOpenE4JiStarMouseEntered
        buttonOpenE4JiStar.setBackground(new java.awt.Color(230, 230, 230));
    }//GEN-LAST:event_buttonOpenE4JiStarMouseEntered

    private void buttonOpenE4JBPMNMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buttonOpenE4JBPMNMouseEntered
        buttonOpenE4JBPMN.setBackground(new java.awt.Color(230, 230, 230));
    }//GEN-LAST:event_buttonOpenE4JBPMNMouseEntered

    private void buttonOpenE4JBPMNMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buttonOpenE4JBPMNMouseExited
        buttonOpenE4JBPMN.setBackground(new java.awt.Color(255, 255, 255));
    }//GEN-LAST:event_buttonOpenE4JBPMNMouseExited

    private void buttonOpenE4JUseCasesMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buttonOpenE4JUseCasesMouseExited
        buttonOpenE4JUseCases.setBackground(new java.awt.Color(255, 255, 255));
    }//GEN-LAST:event_buttonOpenE4JUseCasesMouseExited

    private void buttonOpenE4JUseCasesMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buttonOpenE4JUseCasesMouseEntered
        buttonOpenE4JUseCases.setBackground(new java.awt.Color(230, 230, 230));
    }//GEN-LAST:event_buttonOpenE4JUseCasesMouseEntered

    private void buttunMappingUseCasesMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buttunMappingUseCasesMouseEntered
        buttunMappingUseCases.setBackground(new java.awt.Color(230, 230, 230));
    }//GEN-LAST:event_buttunMappingUseCasesMouseEntered

    private void buttunMappingUseCasesMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buttunMappingUseCasesMouseExited
        buttunMappingUseCases.setBackground(new java.awt.Color(255, 255, 255));
    }//GEN-LAST:event_buttunMappingUseCasesMouseExited

    private void buttonBPMNToUseCasesMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buttonBPMNToUseCasesMouseEntered
        buttonBPMNToUseCases.setBackground(new java.awt.Color(230, 230, 230));
    }//GEN-LAST:event_buttonBPMNToUseCasesMouseEntered

    private void buttonBPMNToUseCasesMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buttonBPMNToUseCasesMouseExited
        buttonBPMNToUseCases.setBackground(new java.awt.Color(255, 255, 255));
    }//GEN-LAST:event_buttonBPMNToUseCasesMouseExited

    private void buttonVerticalTraceabilityMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buttonVerticalTraceabilityMouseEntered
        buttonVerticalTraceability.setBackground(new java.awt.Color(230, 230, 230));
    }//GEN-LAST:event_buttonVerticalTraceabilityMouseEntered

    private void buttonVerticalTraceabilityMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buttonVerticalTraceabilityMouseExited
        buttonVerticalTraceability.setBackground(new java.awt.Color(255, 255, 255));
    }//GEN-LAST:event_buttonVerticalTraceabilityMouseExited

    private void buttonHorizontalTraceabilityMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buttonHorizontalTraceabilityMouseEntered
        buttonHorizontalTraceability.setBackground(new java.awt.Color(230, 230, 230));
    }//GEN-LAST:event_buttonHorizontalTraceabilityMouseEntered

    private void buttonHorizontalTraceabilityMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buttonHorizontalTraceabilityMouseExited
        buttonHorizontalTraceability.setBackground(new java.awt.Color(255, 255, 255));
    }//GEN-LAST:event_buttonHorizontalTraceabilityMouseExited

    private void menuLesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuLesMouseClicked
        URI lesURI;
        try {
            lesURI = new URI("https://www.inf.unioeste.br/les/index.php/listamembros");
            open(lesURI);
        } catch (URISyntaxException ex) {
            java.util.logging.Logger.getLogger(MainView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_menuLesMouseClicked

    private void menuAboutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuAboutMouseClicked
        this.showAboutDialog();
    }//GEN-LAST:event_menuAboutMouseClicked

    private static void open(URI uri) {
        if (Desktop.isDesktopSupported()) {
            try {
                Desktop.getDesktop().browse(uri);
            } catch (IOException e) {
                /* TODO: error handling */ }
        } else {
            /* TODO: error handling */ }
    }

    /**
     * Abre uma janela GuidelinesDialogView
     */
    private void showGuidelinesDialog() throws URISyntaxException {
        GuidelinesDialogView diretrizes;
        try {
            diretrizes = new GuidelinesDialogView(this);
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
     * Abre uma janela AboutDialogView
     */
    private void showAboutDialog() {
        AboutDialogView about;
        about = new AboutDialogView(this);
        about.setModal(true);
        int x = this.getX() + (this.getWidth() - about.getWidth()) / 2;
        int y = this.getY() + (this.getHeight() - about.getHeight()) / 2;
        about.setLocation(x, y);
        about.setVisible(true);
    }

    /**
     * Abre o Editor E4J i*
     */
    public void showE4JiStar() throws HeadlessException, IOException {
        if (E4JiStar == null) {
            E4JiStar = new EditorJFrame(0);
            E4JiStar.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            E4JiStar.setIconImage(iconJGOOSE);
            E4JiStar.setExtendedState(MAXIMIZED_BOTH);
            EditorWindowListener windowListener = new EditorWindowListener(this, E4JiStar);
            this.addWindowListener(windowListener);
            E4JiStar.addWindowListener(windowListener);
            this.addWindowListener(windowListener);
            BasicIStarEditor editor = (BasicIStarEditor) E4JiStar.getEditor();
            JMenuBar menubar = E4JiStar.getJMenuBar();
            // get diagram menu ba
            JMenu fileMenu = ((EditorMenuBar) menubar).getFileMenu();
            // alias label = l
            String label = mxResources.get("useCaseMaker", null, "Generate Use Cases");
            JMenuItem menuItem = new JMenuItem(editor.bind(label, new ImportIStarGraph(E4JiStar)));
            fileMenu.add(menuItem, 3);
            fileMenu.add(new JPopupMenu.Separator(), 4);
            String label1 = mxResources.get("traceabilityMaker", null, "Horizontal Traceability");
            JMenuItem menuItem1 = new JMenuItem(editor.bind(label1, new HorizontalControler(E4JiStar, E4JBPMN, E4JUseCases, useCasesViewIStar, useCasesViewBPMN, 1)));
            fileMenu.add(menuItem1, 3);
            fileMenu.add(new JPopupMenu.Separator(), 4);
            String label2 = mxResources.get("traceabilityMaker", null, "Vertical Traceability");
            JMenuItem menuItem2 = new JMenuItem(editor.bind(label2, new VerticalTraceController(E4JiStar, E4JBPMN, E4JUseCases, useCasesViewIStar, useCasesViewBPMN, 2)));
            fileMenu.add(menuItem2, 3);
            fileMenu.add(new JPopupMenu.Separator(), 4);

        }
        Controller.setMainView(this);
        E4JiStar.setVisible(true);
        this.setVisible(false);
    }
    
    public void showE4JiStarMappedFromUC() throws HeadlessException, IOException {
        if (E4JiStar == null) {
            E4JiStar = new EditorJFrame(0);
            E4JiStar.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            E4JiStar.setIconImage(iconJGOOSE);
            E4JiStar.setExtendedState(MAXIMIZED_BOTH);
            EditorWindowListener windowListener = new EditorWindowListener(this, E4JiStar);
            this.addWindowListener(windowListener);
            E4JiStar.addWindowListener(windowListener);
            this.addWindowListener(windowListener);
            BasicIStarEditor editor = (BasicIStarEditor) E4JiStar.getEditor();
            JMenuBar menubar = E4JiStar.getJMenuBar();
            // get diagram menu ba
            JMenu fileMenu = ((EditorMenuBar) menubar).getFileMenu();
            // alias label = l
            String label = mxResources.get("useCaseMaker", null, "Generate Use Cases");
            JMenuItem menuItem = new JMenuItem(editor.bind(label, new ImportIStarGraph(E4JiStar)));
            fileMenu.add(menuItem, 3);
            fileMenu.add(new JPopupMenu.Separator(), 4);
            String label1 = mxResources.get("traceabilityMaker", null, "Horizontal Traceability");
            JMenuItem menuItem1 = new JMenuItem(editor.bind(label1, new HorizontalControler(E4JiStar, E4JBPMN, E4JUseCases, useCasesViewIStar, useCasesViewBPMN, 1)));
            fileMenu.add(menuItem1, 3);
            fileMenu.add(new JPopupMenu.Separator(), 4);
            String label2 = mxResources.get("traceabilityMaker", null, "Vertical Traceability");
            JMenuItem menuItem2 = new JMenuItem(editor.bind(label2, new VerticalTraceController(E4JiStar, E4JBPMN, E4JUseCases, useCasesViewIStar, useCasesViewBPMN, 2)));
            fileMenu.add(menuItem2, 3);
            fileMenu.add(new JPopupMenu.Separator(), 4);

        }
        
        
        mxGraph graph = generateIStarFromUCDiagram();
        
        Controller.setMainView(this);
        E4JiStar.setVisible(true);
        this.setVisible(false);
    }
    
    private mxGraph generateIStarFromUCDiagram() throws IOException {
        ArrayList<IStarActorElement> actorsMapped = Controller.getOme().getActors();
        ArrayList<IStarLink> dependenciesMapped = Controller.getOme().getDependenciess();
        
        // comeca a atualizar o grafo
        mxGraph graph = ((BasicIStarEditor)E4JiStar.getEditor()).getGraphComponent().getGraph();
        graph.removeCells(graph.getChildVertices(graph.getDefaultParent()));
        graph.removeCells(graph.getChildEdges(graph.getDefaultParent()));
        graph.getModel().beginUpdate();
        Element value;
        Element goal;
        Element dependency;
        Element link;
        File shapesFolder = new File("resources/shapes/elements/");
        File[] files = shapesFolder.listFiles(ShapeFilenameFilter.instance);
        String nodeXml = mxUtils.readFile(files[0].getAbsolutePath());
        mxStencilShape newShape = new mxStencilShape(nodeXml);
        String styleActor = "shape=" + newShape.getName() + ";";
        nodeXml = mxUtils.readFile(files[4].getAbsolutePath());
        newShape = new mxStencilShape(nodeXml);
        String styleCase = "shape=" + newShape.getName() + ";";
        mxGeometry geo;
        // HashMaps para quardar as celular dos atores e casos de uso
        HashMap<String, mxCell> actors = new HashMap<>();
        HashMap<String, mxCell> dependencies = new HashMap<>();
        Map<String, mxCell> mapTasks = new HashMap<>();
        Map<String, mxCell> allTasks = new HashMap<>();
        mxCell aresta;
        mxCell isa;
        // variaveis para controlar as posicoes X e Y dos elementos no diagrama
        int yActor = 30;
        int yCase = 30;
        int xActor = 50; 
        int xCase = 200;
        String cod;
        /*
         * Adicionar ao diagrama todos os atores com seus respectivos
         * casos de uso e arestas.
         */
        
        // força o desenho do ator sistema antes de todos os outros
        for (IStarActorElement actor : actorsMapped) {
            if(actor.getSystem()){
                value = IStarUtils.createActor();
                value.setAttribute("label", actor.getName());
                geo = new mxGeometry(50, 100, 80, 80);
                geo.setX(400);
                geo.setY(100);
                mxCell cell = new mxCell(value, geo, styleActor);
                cell.setVertex(true);
                actors.put(actor.getCod(), cell);
                graph.addCell(cell);
            
             
                AddBoundary actorBoundary = new AddBoundary();
                Integer childrensSize = actor.getChildren().size();
                mapTasks.putAll(actorBoundary.setBoundary(cell, graph, childrensSize, actor.getChildren()));
             }
        }
        
        
        // desenha atores
        for (IStarActorElement actor : actorsMapped) {
            if(actor.getSystem()){
                continue;
            }
            
            // cria ator e sua geometria e estilo
            value = IStarUtils.createActor();
            value.setAttribute("label", actor.getName());
            geo = new mxGeometry(xActor, yActor, 80, 80);
            mxCell cell = new mxCell(value, geo, styleActor);
            cell.setVertex(true);
            actors.put(actor.getCod(), cell);
            graph.addCell(cell);
            
            // Incrementando a posição Y do próximo ator
            yActor += 120;
            
           
            // cria as goals que dependem do ator
            if(actor.getDependencies() != null){
                for (IStarElement element : actor.getDependencies()) {
                     goal = IStarUtils.createGoal();
                     
                     goal.setAttribute("label", element.getName().replace("\"", ""));
                     geo = new mxGeometry(xCase, yCase, 120, 60);
                     
                     geo.setX(xCase);
                     geo.setY(yCase);
                     yCase += 100;
                     cod = element.getCod();
                    
                    dependencies.put(cod, new mxCell(goal, geo, styleCase));
                    dependencies.get(cod).setVertex(true);
                    graph.addCell(dependencies.get(cod));
                    
                    yCase += 100;
                    if(actor.isSecondary()){
                        dependency = IStarUtils.createDepndency();
                        actor.getCod();
                        aresta = (mxCell) graph.createEdge(graph.getDefaultParent(), null, dependency, dependencies.get(cod), actors.get(actor.getCod()), "straight;endArrow=dependency;noLabel=1;shape=curvedEdge;edgeStyle=curvedEdgeStyle");
                        aresta.setEdge(true);
                        aresta.setStyle("straight;endArrow=dependency;noLabel=1;shape=curvedEdge;edgeStyle=curvedEdgeStyle");
                        aresta.setSource(dependencies.get(cod));
                        aresta.setTarget(actors.get(actor.getCod()));
                        graph.addCell(aresta);
                        
                        // ligar objetivo de fora do sistema com a tarefa do sistema
                        dependency = IStarUtils.createDepndency();

                        aresta = (mxCell) graph.createEdge(graph.getDefaultParent(), null, dependency, mapTasks.get(element.getCod()),dependencies.get(cod) , "straight;endArrow=dependency;noLabel=1;shape=curvedEdge;edgeStyle=curvedEdgeStyle");
                        aresta.setEdge(true);
                        aresta.setStyle("straight;endArrow=dependency;noLabel=1;shape=curvedEdge;edgeStyle=curvedEdgeStyle");
                        aresta.setSource(mapTasks.get(element.getCod()));
                        aresta.setTarget(dependencies.get(cod));
                        graph.addCell(aresta);
                    } else {
                        // cria uma aresta entre o ator e o elemento
                        dependency = IStarUtils.createDepndency();

                        aresta = (mxCell) graph.createEdge(graph.getDefaultParent(), null, dependency, actors.get(actor.getCod()), dependencies.get(cod), "straight;endArrow=dependency;noLabel=1;shape=curvedEdge;edgeStyle=curvedEdgeStyle");
                        aresta.setEdge(true);
                        aresta.setStyle("straight;endArrow=dependency;noLabel=1;shape=curvedEdge;edgeStyle=curvedEdgeStyle");
                        aresta.setSource(actors.get(actor.getCod()));
                        aresta.setTarget(dependencies.get(cod));
                        graph.addCell(aresta);

                        // ligar objetivo de fora do sistema com a tarefa do sistema
                        dependency = IStarUtils.createDepndency();


                        aresta = (mxCell) graph.createEdge(graph.getDefaultParent(), null, dependency, dependencies.get(cod), mapTasks.get(element.getCod()), "straight;endArrow=dependency;noLabel=1;shape=curvedEdge;edgeStyle=curvedEdgeStyle");
                        aresta.setEdge(true);
                        aresta.setStyle("straight;endArrow=dependency;noLabel=1;shape=curvedEdge;edgeStyle=curvedEdgeStyle");
                        aresta.setSource(dependencies.get(cod));
                        aresta.setTarget(mapTasks.get(element.getCod()));
                        graph.addCell(aresta);
                        // System.out.println("ARESTA COM ELEMENTO DE FORA CRIADA COM " + dependencies.get(cod).getAttribute("label") + " E " +  mapTasks.get(element.getCod()));
                    }
                }
            }
            
        }
        
        // criação das ligações do tipo isa
        for(IStarActorElement actor : actorsMapped){
            if(actor.getChildrens().size() > 0){
                
                for(IStarActorElement actor2 : actorsMapped){
                    if(actor.getChildrens().contains(actor2.getCod())){
                        link = IStarUtils.createIS_A();
                        mxGeometry geom = new mxGeometry(0, 0, 80, 80);
                        mxCell cell = new mxCell(link, geom, "straight;shape=curvedEdge;edgeStyle=curvedEdgeStyle;");
                        
                        cell.setEdge(true);
                        
                        cell.setSource(actors.get(actor.getCod()));
                        cell.setTarget(actors.get(actor2.getCod()));
                        graph.addCell(cell);
                    }
                }
                
            }
        }
        
        
       
        graph.getModel().endUpdate();
        return graph;
    }

    /**
     * Abre o Editor E4J Use Cases
     */
    private void showE4JUseCases() throws HeadlessException, IOException {
        if (E4JUseCases == null) {
            E4JUseCases = new EditorJFrame(1);
            E4JUseCases.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            E4JUseCases.setIconImage(iconJGOOSE);
            E4JUseCases.setExtendedState(MAXIMIZED_BOTH);
            EditorWindowListener windowListener = new EditorWindowListener(this, E4JUseCases);
            this.addWindowListener(windowListener);
            E4JUseCases.addWindowListener(windowListener);
            this.addWindowListener(windowListener);
            BasicUseCasesEditor editor = (BasicUseCasesEditor) E4JUseCases.getEditor();
            JMenuBar menubar = E4JUseCases.getJMenuBar();
            JMenu fileMenu = ((EditorMenuBar) menubar).getFileMenu();
            String label = mxResources.get("istarMaker", null, "Generate iStar");
            JMenuItem menuItem = new JMenuItem(editor.bind(label, new ImportUseCaseGraph(E4JUseCases)));
            fileMenu.add(menuItem, 3);
            fileMenu.add(new JPopupMenu.Separator(), 4);
            String label1 = mxResources.get("traceabilityMaker", null, "Horizontal Traceability");
            JMenuItem menuItem1 = new JMenuItem(editor.bind(label1, (Action) new HorizontalControler(E4JiStar, E4JBPMN, E4JUseCases, useCasesViewIStar, useCasesViewBPMN, 3)));
            fileMenu.add(menuItem1, 3);
        }
        Controller.setMainView(this);
        E4JUseCases.setVisible(true);
        this.setVisible(false);
    }

    /**
     * Abre o Editor E4J BPMN
     */
    private void showE4JBPMN() throws HeadlessException, IOException {
        try {

            if (E4JBPMN == null) {
                E4JBPMN = new EditorJFrame(2);
                E4JBPMN.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                EditorWindowListener windowListener = new EditorWindowListener(this, E4JBPMN);
                E4JBPMN.setIconImage(iconJGOOSE);
                E4JBPMN.setExtendedState(MAXIMIZED_BOTH);
                this.addWindowListener(windowListener);
                E4JBPMN.addWindowListener(windowListener);
                this.addWindowListener(windowListener);
                bpmnEditor = (BasicBPMNEditor) E4JBPMN.getEditor();

                // get diagram menu bar and shows option to derive Use Cases
                JMenuBar menubar = E4JBPMN.getJMenuBar();
                JMenu fileMenu = ((EditorMenuBar) menubar).getFileMenu();
                String label1 = mxResources.get("traceabilityMaker", null, "Horizontal Traceability");
                JMenuItem menuItem1 = new JMenuItem(bpmnEditor.bind(label1, new HorizontalControler(E4JiStar, E4JBPMN, E4JUseCases, useCasesViewIStar, useCasesViewBPMN, 2)));
                fileMenu.add(menuItem1, 3);
                fileMenu.add(new JPopupMenu.Separator(), 4);
                String label2 = mxResources.get("traceabilityMaker", null, "Vertical Traceability");
                JMenuItem menuItem2 = new JMenuItem(bpmnEditor.bind(label2, new VerticalTraceController(E4JiStar, E4JBPMN, E4JUseCases, useCasesViewIStar, useCasesViewBPMN, 1)));
                fileMenu.add(menuItem2, 3);
                fileMenu.add(new JPopupMenu.Separator(), 4);
                String label = mxResources.get("useCaseMaker", null, "Generate Use Cases");
                JMenuItem menuItem = new JMenuItem(bpmnEditor.bind(label, new ImportBPMNGraph(E4JBPMN)));
                fileMenu.add(menuItem, 3);
                fileMenu.add(new JPopupMenu.Separator(), 4);

            }
            BPMNController.setMainView(this);
            E4JBPMN.setVisible(true);
            this.setVisible(false);
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder(e.toString());
            for (StackTraceElement ste : e.getStackTrace()) {
                sb.append("\n\tat ");
                sb.append(ste);
            }
            String trace = sb.toString();
            JOptionPane.showMessageDialog(null, trace);
        }
    }

    /**
     * Abre a janela de Rastreabilidade
     */
    private void showTraceability() {
        try {
            if (E4JTraceability == null) {
                E4JTraceability = new EditorJFrame(3);
                E4JTraceability.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                EditorWindowListener windowListener = new EditorWindowListener(this, E4JTraceability);
                E4JTraceability.setIconImage(iconJGOOSE);
                E4JTraceability.setExtendedState(MAXIMIZED_BOTH);
                /*
                this.addWindowListener(windowListener);
                E4JBPMN.addWindowListener(windowListener);
                this.addWindowListener(windowListener);
                editor = (BasicBPMNEditor) E4JBPMN.getEditor();

                // get diagram menu bar and shows option to derive Use Cases
                JMenuBar menubar = E4JBPMN.getJMenuBar();
                JMenu fileMenu = ((EditorMenuBar) menubar).getFileMenu();
                String label = mxResources.get("useCaseMaker", null, "Generate Use Cases");
                JMenuItem menuItem = new JMenuItem(editor.bind(label, new ImportBPMNGraph(E4JBPMN)));
                fileMenu.add(menuItem, 3);
                fileMenu.add(new JPopupMenu.Separator(), 4);*/
            }
            //  HorizontalBPMNTraceController.setMainView(this);
            E4JTraceability.setVisible(true);
            this.setVisible(false);
            // TraceabilityView obj = new TraceabilityView();
            // obj.setVisible(true);   
        } catch (Exception e) {

        }

    }

    /**
     * Abre a janela de tabelas
     */
    private void showArtfactTables() {
        try {
            if (tableArtifacts == null) {
                tableArtifacts = new TableArtifacts();
                tableArtifacts.setIconImage(iconJGOOSE);
                tableArtifacts.setExtendedState(MAXIMIZED_BOTH);
                tableArtifacts.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                EditorWindowListener windowListener = new EditorWindowListener(this, tableArtifacts);
                this.addWindowListener(windowListener);
                tableArtifacts.addWindowListener(windowListener);
                this.addWindowListener(windowListener);

            }
            tableArtifacts.setVisible(true);
            this.setVisible(false);

        } catch (Exception e) {
            StringBuilder sb = new StringBuilder(e.toString());
            for (StackTraceElement ste : e.getStackTrace()) {
                sb.append("\n\tat ");
                sb.append(ste);
            }
            String trace = sb.toString();
            JOptionPane.showMessageDialog(null, trace);
        }
    }

    private void bpmnUCView() {
        try {
            if (useCasesViewBPMN == null) {
                useCasesViewBPMN = new UseCasesViewBPMN(E4JiStar, E4JBPMN, E4JUseCases, useCasesViewIStar);
                useCasesViewBPMN.setIconImage(iconJGOOSE);
                useCasesViewBPMN.setExtendedState(MAXIMIZED_BOTH);
                useCasesViewBPMN.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                EditorWindowListener windowListener = new EditorWindowListener(this, useCasesViewBPMN);
                this.addWindowListener(windowListener);
                useCasesViewBPMN.addWindowListener(windowListener);
                this.addWindowListener(windowListener);
            }
            useCasesViewBPMN.setVisible(true);
            this.setVisible(false);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No derivation BPMN to Use Cases was performed");
        }
    }

    public void iStarUCView() {
        try {
            if (useCasesViewIStar == null) {
                useCasesViewIStar = new UseCasesViewIStar(E4JiStar, E4JBPMN, E4JUseCases, useCasesViewBPMN);
                useCasesViewIStar.setIconImage(iconJGOOSE);
                useCasesViewIStar.setExtendedState(MAXIMIZED_BOTH);
                useCasesViewIStar.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                EditorWindowListener windowListener = new EditorWindowListener(this, useCasesViewIStar);
                this.addWindowListener(windowListener);
                useCasesViewIStar.addWindowListener(windowListener);
                this.addWindowListener(windowListener);
            }
            useCasesViewIStar.setVisible(true);
            this.setVisible(false);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No derivation I* to Use Cases was performed");
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /*
         * Set the Nimbus look and feel
         */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainView().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonBPMNToUseCases;
    private javax.swing.JButton buttonHorizontalTraceability;
    public javax.swing.JButton buttonOpenE4JBPMN;
    private javax.swing.JButton buttonOpenE4JUseCases;
    private javax.swing.JButton buttonOpenE4JiStar;
    private javax.swing.JButton buttonVerticalTraceability;
    private javax.swing.JButton buttunMappingUseCases;
    private javax.swing.JMenuItem fileOpenTelosFile;
    private javax.swing.Box.Filler filler1;
    private javax.swing.JMenuItem helpGuidelines;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabelImage1;
    private javax.swing.JLabel jLabelImage2;
    private javax.swing.JLabel jLabelImage3;
    private javax.swing.JLabel jLabelImg1;
    private javax.swing.JLabel jLabelImg2;
    private javax.swing.JLabel jLabelImg3;
    private javax.swing.JLabel jLabelSubTitleImg2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel4buttonBPMNToUseCases;
    private javax.swing.JPanel jPanel4buttonHorizontalTraceability;
    private javax.swing.JPanel jPanel4buttonOpenE4JBPMN;
    private javax.swing.JPanel jPanel4buttonOpenE4JUseCases;
    private javax.swing.JPanel jPanel4buttonOpenE4JiStar;
    private javax.swing.JPanel jPanel4buttonVerticalTraceability;
    private javax.swing.JPanel jPanel4buttunMappingUseCases;
    private javax.swing.JPanel jPanelFooter;
    private javax.swing.JPanel jPanelImages;
    private javax.swing.JPanel jPanelTitleAndButtons;
    private javax.swing.JMenu menuAbout;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenu menuFile;
    private javax.swing.JMenu menuHelp;
    private javax.swing.JMenu menuLes;
    private javax.swing.JMenu menuTools;
    private javax.swing.JMenuItem toolsOpenE4JBPMNEditor;
    private javax.swing.JMenuItem toolsOpenE4JEditor;
    private javax.swing.JMenuItem toolsOpenE4JUCEditor;
    // End of variables declaration//GEN-END:variables

    public void setE4JiStar(EditorJFrame E4JiStar) { 
        this.E4JiStar = E4JiStar;
    }
    
    public void setE4JUseCases(EditorJFrame E4JUseCases) { 
        this.E4JUseCases = E4JUseCases;
    }

}
