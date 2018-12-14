/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * Creates a GUI for the positional inverted index search engine
 *
 * @author dayanarios
 */
public class GUI extends javax.swing.JFrame {

    private static Path directoryPath;
    private Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    private int xCoord = dim.width / 2;
    private int yCoord = dim.height / 2;
    String indexingMsg = "Indexing Corpus";
    protected static DefaultListModel JListModel = new DefaultListModel();
    private String iconPath = getClass().getResource("/resources/pose.png").toString();
    private static ImageIcon icon = new ImageIcon(new ImageIcon(GUI.class.getResource("/resources/PoSE.png")).getImage().getScaledInstance(170, 58, Image.SCALE_DEFAULT));

    /**
     * Creates new form GUI
     */
    public GUI() {

        initComponents();
        this.setLocation(xCoord - this.getSize().width / 2, yCoord - this.getSize().height / 2);

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        DirectoryDialogBox = new javax.swing.JDialog();
        DirectoryDialogPanel = new javax.swing.JPanel();
        SearchDirectoriesButton = new javax.swing.JButton();
        DirectoryDirectionsLabel = new javax.swing.JLabel();
        directoryPoseLabel = new javax.swing.JLabel();
        indexingCorpusMessage = new javax.swing.JOptionPane();
        directoryChooser = new javax.swing.JFileChooser();
        docFrame = new javax.swing.JFrame();
        docFramePanel = new javax.swing.JPanel();
        docBodyScrollPane = new javax.swing.JScrollPane();
        docBodyLabel = new javax.swing.JLabel();
        docFootNotesPanel = new javax.swing.JPanel();
        docFootNoteLabel = new javax.swing.JLabel();
        modeButtonGroup = new javax.swing.ButtonGroup();
        topPanel = new javax.swing.JPanel();
        ProjectTitleLabel = new javax.swing.JLabel();
        SearchBarTextField = new javax.swing.JTextField();
        bottomPanel = new javax.swing.JPanel();
        ResultsScrollPane = new javax.swing.JScrollPane();
        //JListModel.addElement("Search Results");
        ResultsJList = new javax.swing.JList<>(JListModel);
        ResultsScrollPane1 = new javax.swing.JScrollPane();
        //JListModel.addElement("Search Results");
        ResultsJList1 = new javax.swing.JList<>(JListModel);
        ResultsLabel = new javax.swing.JLabel();
        FrameFootnotes = new javax.swing.JPanel();
        footnoteLabel = new javax.swing.JLabel();
        FeaturesPanel = new javax.swing.JPanel();
        booleanButton = new javax.swing.JRadioButton();
        rankedButton = new javax.swing.JRadioButton();
        formulaComboBox = new javax.swing.JComboBox<>();

        DirectoryDialogBox.setTitle("Select Directory");
        DirectoryDialogBox.setMinimumSize(new java.awt.Dimension(360, 208));
        DirectoryDialogBox.setSize(new java.awt.Dimension(360, 208));

        DirectoryDialogPanel.setBackground(new java.awt.Color(255, 255, 255));
        DirectoryDialogPanel.setMaximumSize(new java.awt.Dimension(360, 208));
        DirectoryDialogPanel.setSize(new java.awt.Dimension(360, 208));

        SearchDirectoriesButton.setBackground(new java.awt.Color(0, 102, 204));
        SearchDirectoriesButton.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        SearchDirectoriesButton.setText("Directories");
        SearchDirectoriesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SearchDirectoriesButtonActionPerformed(evt);
            }
        });

        DirectoryDirectionsLabel.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        DirectoryDirectionsLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        DirectoryDirectionsLabel.setText("Please select a directory to index. ");

        directoryPoseLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        directoryPoseLabel.setIcon(icon);

        javax.swing.GroupLayout DirectoryDialogPanelLayout = new javax.swing.GroupLayout(DirectoryDialogPanel);
        DirectoryDialogPanel.setLayout(DirectoryDialogPanelLayout);
        DirectoryDialogPanelLayout.setHorizontalGroup(
            DirectoryDialogPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DirectoryDialogPanelLayout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addGroup(DirectoryDialogPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, DirectoryDialogPanelLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(SearchDirectoriesButton)
                        .addGap(67, 67, 67))
                    .addComponent(DirectoryDirectionsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(directoryPoseLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(60, 60, 60))
        );
        DirectoryDialogPanelLayout.setVerticalGroup(
            DirectoryDialogPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DirectoryDialogPanelLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(directoryPoseLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(DirectoryDirectionsLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addComponent(SearchDirectoriesButton)
                .addGap(30, 30, 30))
        );

        DirectoryDialogBox.setLocation(xCoord-DirectoryDialogBox.getSize().width/2, yCoord-DirectoryDialogBox.getSize().height/2);
        DirectoryDialogBox.setVisible(true);

        javax.swing.GroupLayout DirectoryDialogBoxLayout = new javax.swing.GroupLayout(DirectoryDialogBox.getContentPane());
        DirectoryDialogBox.getContentPane().setLayout(DirectoryDialogBoxLayout);
        DirectoryDialogBoxLayout.setHorizontalGroup(
            DirectoryDialogBoxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DirectoryDialogBoxLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(DirectoryDialogPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        DirectoryDialogBoxLayout.setVerticalGroup(
            DirectoryDialogBoxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DirectoryDialogBoxLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(DirectoryDialogPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        indexingCorpusMessage.setVisible(false);
        indexingCorpusMessage.setBackground(new java.awt.Color(255, 255, 255));
        indexingCorpusMessage.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        indexingCorpusMessage.setLocation(xCoord-indexingCorpusMessage.getSize().width/2, yCoord-indexingCorpusMessage.getSize().height/2);
        indexingCorpusMessage.setOpaque(false);

        docFrame.setVisible(false);
        docFrame.setTitle("Document");
        docFrame.setBackground(new java.awt.Color(255, 255, 255));
        docFrame.setMinimumSize(new java.awt.Dimension(480, 555));
        docFrame.setSize(new java.awt.Dimension(480, 555));
        docFrame.setLocation(xCoord-docFrame.getSize().width/2, yCoord-docFrame.getSize().height/2);

        docFramePanel.setBackground(new java.awt.Color(255, 255, 255));
        docFramePanel.setMaximumSize(new java.awt.Dimension(480, 542));
        docFramePanel.setMinimumSize(new java.awt.Dimension(480, 542));

        docBodyScrollPane.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        docBodyScrollPane.setMaximumSize(new java.awt.Dimension(32767, 30));
        docBodyScrollPane.setMinimumSize(new java.awt.Dimension(450, 30));
        docBodyScrollPane.setPreferredSize(new java.awt.Dimension(469, 5000));

        docBodyLabel.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        docBodyLabel.setToolTipText("Document Chosen");
        docBodyLabel.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        docBodyLabel.setMaximumSize(new java.awt.Dimension(444, 2000));
        docBodyLabel.setMinimumSize(new java.awt.Dimension(444, 2000));
        docBodyLabel.setPreferredSize(new java.awt.Dimension(444, 2000));
        docBodyLabel.setSize(new java.awt.Dimension(444, 2000));
        docBodyScrollPane.setViewportView(docBodyLabel);

        javax.swing.GroupLayout docFramePanelLayout = new javax.swing.GroupLayout(docFramePanel);
        docFramePanel.setLayout(docFramePanelLayout);
        docFramePanelLayout.setHorizontalGroup(
            docFramePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(docFramePanelLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(docBodyScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );
        docFramePanelLayout.setVerticalGroup(
            docFramePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(docFramePanelLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(docBodyScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 442, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        docFootNoteLabel.setForeground(new java.awt.Color(102, 102, 102));
        docFootNoteLabel.setText("Bhavya Patel, Dayana Rios");

        javax.swing.GroupLayout docFootNotesPanelLayout = new javax.swing.GroupLayout(docFootNotesPanel);
        docFootNotesPanel.setLayout(docFootNotesPanelLayout);
        docFootNotesPanelLayout.setHorizontalGroup(
            docFootNotesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(docFootNotesPanelLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(docFootNoteLabel)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        docFootNotesPanelLayout.setVerticalGroup(
            docFootNotesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, docFootNotesPanelLayout.createSequentialGroup()
                .addContainerGap(10, Short.MAX_VALUE)
                .addComponent(docFootNoteLabel)
                .addGap(15, 15, 15))
        );

        javax.swing.GroupLayout docFrameLayout = new javax.swing.GroupLayout(docFrame.getContentPane());
        docFrame.getContentPane().setLayout(docFrameLayout);
        docFrameLayout.setHorizontalGroup(
            docFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(docFrameLayout.createSequentialGroup()
                .addGroup(docFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(docFramePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(docFootNotesPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        docFrameLayout.setVerticalGroup(
            docFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(docFrameLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(docFramePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 494, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(docFootNotesPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Po(stional Inverted Index) Search Engine");
        setBackground(new java.awt.Color(255, 255, 255));

        topPanel.setBackground(new java.awt.Color(246, 246, 246));

        ProjectTitleLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ProjectTitleLabel.setIcon(icon);
        ProjectTitleLabel.setMaximumSize(new java.awt.Dimension(170, 58));
        ProjectTitleLabel.setMinimumSize(new java.awt.Dimension(170, 58));
        ProjectTitleLabel.setPreferredSize(new java.awt.Dimension(170, 58));
        ProjectTitleLabel.setRequestFocusEnabled(false);
        ProjectTitleLabel.setSize(new java.awt.Dimension(170, 58));
        //ProjectTitleLabel = new javax.swing.JLabel(icon);

        SearchBarTextField.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        SearchBarTextField.setText("Enter a term to search or 'q' to exit");
        SearchBarTextField.setToolTipText("Search Bar");
        SearchBarTextField.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        SearchBarTextField.setMaximumSize(new java.awt.Dimension(147, 22));
        SearchBarTextField.setMinimumSize(new java.awt.Dimension(147, 22));
        SearchBarTextField.setSize(new java.awt.Dimension(147, 22));
        SearchBarTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SearchBarTextFieldActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout topPanelLayout = new javax.swing.GroupLayout(topPanel);
        topPanel.setLayout(topPanelLayout);
        topPanelLayout.setHorizontalGroup(
            topPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(topPanelLayout.createSequentialGroup()
                .addGap(86, 86, 86)
                .addComponent(ProjectTitleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(SearchBarTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(155, Short.MAX_VALUE))
        );
        topPanelLayout.setVerticalGroup(
            topPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(topPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(topPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(ProjectTitleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SearchBarTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        bottomPanel.setBackground(new java.awt.Color(255, 255, 255));

        ResultsJList.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        ResultsJList.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        ResultsJList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        ResultsJList.setToolTipText("Displays results of search query");
        ResultsJList.setMaximumSize(new java.awt.Dimension(147, 32767));
        ResultsJList.setMinimumSize(new java.awt.Dimension(147, 206));
        ResultsJList.setPreferredSize(new java.awt.Dimension(147, 32767));
        ResultsJList.setSize(new java.awt.Dimension(147, 32767));
        ResultsJList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ResultsJListMouseClicked(evt);
            }
        });
        ResultsScrollPane.setViewportView(ResultsJList);

        ResultsJList1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        ResultsJList1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        ResultsJList1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        ResultsJList1.setToolTipText("Displays results of search query");
        ResultsJList1.setMaximumSize(new java.awt.Dimension(147, 32767));
        ResultsJList1.setMinimumSize(new java.awt.Dimension(147, 206));
        ResultsJList1.setPreferredSize(new java.awt.Dimension(147, 32767));
        ResultsJList1.setSize(new java.awt.Dimension(147, 32767));
        ResultsJList1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ResultsJListMouseClicked(evt);
            }
        });
        ResultsScrollPane1.setViewportView(ResultsJList1);

        ResultsLabel.setBackground(new java.awt.Color(255, 255, 255));
        ResultsLabel.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        ResultsLabel.setText("Search Results");
        ResultsLabel.setOpaque(true);

        javax.swing.GroupLayout bottomPanelLayout = new javax.swing.GroupLayout(bottomPanel);
        bottomPanel.setLayout(bottomPanelLayout);
        bottomPanelLayout.setHorizontalGroup(
            bottomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bottomPanelLayout.createSequentialGroup()
                .addGap(86, 86, 86)
                .addGroup(bottomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ResultsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 455, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ResultsScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 669, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(87, 87, 87))
        );
        bottomPanelLayout.setVerticalGroup(
            bottomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bottomPanelLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(ResultsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(ResultsScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(30, Short.MAX_VALUE))
        );

        FrameFootnotes.setBackground(new java.awt.Color(246, 246, 246));

        footnoteLabel.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        footnoteLabel.setForeground(new java.awt.Color(102, 102, 102));
        footnoteLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        footnoteLabel.setText("Bhavya Patel, Dayana Rios");
        footnoteLabel.setToolTipText("");

        javax.swing.GroupLayout FrameFootnotesLayout = new javax.swing.GroupLayout(FrameFootnotes);
        FrameFootnotes.setLayout(FrameFootnotesLayout);
        FrameFootnotesLayout.setHorizontalGroup(
            FrameFootnotesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FrameFootnotesLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(footnoteLabel)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        FrameFootnotesLayout.setVerticalGroup(
            FrameFootnotesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, FrameFootnotesLayout.createSequentialGroup()
                .addContainerGap(10, Short.MAX_VALUE)
                .addComponent(footnoteLabel)
                .addGap(10, 10, 10))
        );

        FeaturesPanel.setBackground(new java.awt.Color(246, 246, 246));

        modeButtonGroup.add(booleanButton);
        booleanButton.setSelected(true);
        booleanButton.setText("Boolean Mode");
        booleanButton.setToolTipText("Boolean Query Mode");
        booleanButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                booleanButtonActionPerformed(evt);
            }
        });

        modeButtonGroup.add(rankedButton);
        rankedButton.setText("Ranked Mode");
        rankedButton.setToolTipText("Ranked Query Mode");
        rankedButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rankedButtonActionPerformed(evt);
            }
        });

        formulaComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Default", "tf-idf", "Okapi BM25", "Wacky" }));
        formulaComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                formulaComboBoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout FeaturesPanelLayout = new javax.swing.GroupLayout(FeaturesPanel);
        FeaturesPanel.setLayout(FeaturesPanelLayout);
        FeaturesPanelLayout.setHorizontalGroup(
            FeaturesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FeaturesPanelLayout.createSequentialGroup()
                .addGap(83, 83, 83)
                .addComponent(booleanButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rankedButton)
                .addGap(10, 10, 10)
                .addComponent(formulaComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        FeaturesPanelLayout.setVerticalGroup(
            FeaturesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FeaturesPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(FeaturesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(booleanButton)
                    .addComponent(rankedButton)
                    .addComponent(formulaComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(FeaturesPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(topPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(bottomPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(FrameFootnotes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(topPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(FeaturesPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(bottomPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(FrameFootnotes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Listens for a new search query to be inputted
     *
     * @param evt event occurred
     */
    private void SearchDirectoriesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchDirectoriesButtonActionPerformed
        // TODO add your handling code here:
        directoryChooser = new JFileChooser();
        directoryChooser.setCurrentDirectory(new java.io.File("~")); //starts at root directory
        directoryChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        directoryChooser.setAcceptAllFileFilterUsed(false);

        if (directoryChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            DirectoryDialogBox.dispose();
            Object[] options = {"Build Index", "Query Index"};
            int option = JOptionPane.showOptionDialog(indexingCorpusMessage,
                    "Select a mode",
                    "Search Engine Mode",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null, //do not use a custom Icon
                    options, //the titles of buttons
                    options[1]); //default button title
            //JOptionPane.showOptionDialog(indexingCorpusMessage, "Indexing corpus please wait", "Indexing Corpus", javax.swing.JOptionPane.DEFAULT_OPTION, javax.swing.JOptionPane.INFORMATION_MESSAGE, null, null, null);

            
            this.setVisible(true);
            File file = directoryChooser.getSelectedFile();
            directoryPath = file.toPath();

            //starts indexing 
            try {

                DocumentIndexer.startIndexing(directoryPath, option);

            } catch (Exception ex) {
                System.out.println("Problem with DocumentIndexer.startIndexing(directoryPath)");
            }

        } else {
            System.out.println("No Selection ");
        }


    }//GEN-LAST:event_SearchDirectoriesButtonActionPerformed

    /**
     * Listens for the result list to be selected
     *
     * @param evt
     */
    private void SearchBarTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchBarTextFieldActionPerformed

        // TODO add your handling code here:
        DocumentIndexer.query = SearchBarTextField.getText();
        try {
            if (!DocumentIndexer.newCorpus()) {

                DocumentIndexer.startSearchEngine();
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_SearchBarTextFieldActionPerformed

    /**
     * Listens for the result list to be selected
     *
     * @param evt
     */
    private void ResultsJListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ResultsJListMouseClicked

        if (DocumentIndexer.clickList) {
            // TODO add your handling code here:
            javax.swing.JList list = (javax.swing.JList) evt.getSource();
            if (evt.getClickCount() == 2) {
                int index = list.locationToIndex(evt.getPoint());

                docFrame.setVisible(true);

                try {

                    BufferedReader reader = new BufferedReader(DocumentIndexer.corpus.getDocument(DocumentIndexer.postings.get(index).getDocumentId()).getContent());

                    String title = DocumentIndexer.corpus.getDocument(DocumentIndexer.postings.get(index).getDocumentId()).getTitle();
                    //read the contents of the json file to display them
                    String contents = "<html><h1>" + title + "</h1><body>";
                    String line;
                    while ((line = reader.readLine()) != null) {
                        contents += line;
                    }
                    contents += "</body></html>";

                    docBodyLabel.setText(contents);
                } catch (ArrayIndexOutOfBoundsException ex) {
                    System.out.println("Array index out of bounds exception");
                } catch (IOException ex) {
                    Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }

        SearchBarTextField.selectAll();

    }//GEN-LAST:event_ResultsJListMouseClicked

    /**
     * Indicates the search engine is running in boolean query mode
     * @param evt 
     */
    private void booleanButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_booleanButtonActionPerformed

        DocumentIndexer.booleanMode = true;
        DocumentIndexer.rankedMode = false; 
        
    }//GEN-LAST:event_booleanButtonActionPerformed

    /**
     * Indicates the search engine is running in ranked query mode
     * @param evt 
     */
    private void rankedButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rankedButtonActionPerformed

        DocumentIndexer.booleanMode = false;
        DocumentIndexer.rankedMode = true; 
        
    }//GEN-LAST:event_rankedButtonActionPerformed

    private void formulaComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_formulaComboBoxActionPerformed
        // TODO add your handling code here:
        DocumentIndexer.rankedOption = formulaComboBox.getSelectedIndex();
    }//GEN-LAST:event_formulaComboBoxActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        try {

            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GUI().setVisible(false);

            }
        });

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private static javax.swing.JDialog DirectoryDialogBox;
    private javax.swing.JPanel DirectoryDialogPanel;
    private javax.swing.JLabel DirectoryDirectionsLabel;
    private javax.swing.JPanel FeaturesPanel;
    private javax.swing.JPanel FrameFootnotes;
    private javax.swing.JLabel ProjectTitleLabel;
    protected static javax.swing.JList<String> ResultsJList;
    protected static javax.swing.JList<String> ResultsJList1;
    protected static javax.swing.JLabel ResultsLabel;
    private javax.swing.JScrollPane ResultsScrollPane;
    private javax.swing.JScrollPane ResultsScrollPane1;
    protected static javax.swing.JTextField SearchBarTextField;
    private javax.swing.JButton SearchDirectoriesButton;
    private javax.swing.JRadioButton booleanButton;
    private javax.swing.JPanel bottomPanel;
    private javax.swing.JFileChooser directoryChooser;
    private javax.swing.JLabel directoryPoseLabel;
    private javax.swing.JLabel docBodyLabel;
    private javax.swing.JScrollPane docBodyScrollPane;
    private javax.swing.JLabel docFootNoteLabel;
    private javax.swing.JPanel docFootNotesPanel;
    private javax.swing.JFrame docFrame;
    private javax.swing.JPanel docFramePanel;
    private javax.swing.JLabel footnoteLabel;
    private javax.swing.JComboBox<String> formulaComboBox;
    protected static javax.swing.JOptionPane indexingCorpusMessage;
    private javax.swing.ButtonGroup modeButtonGroup;
    private javax.swing.JRadioButton rankedButton;
    private javax.swing.JPanel topPanel;
    // End of variables declaration//GEN-END:variables
}
