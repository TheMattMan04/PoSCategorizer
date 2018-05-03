/*UI*/
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

/*NLP*/
import edu.stanford.nlp.ling.CoreAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

/*DATA STRUCTURES*/
import java.util.*;
import java.util.List;
import edu.stanford.nlp.util.CoreMap;

public class CategorizePOSScreen extends JFrame {
    /*UI COMPONENTS*/
    private JFrame posFrame;
    private JLabel headLabel;
    private JTextArea enterText;
    private JScrollPane textAreaScroll;
    private JPanel labelAndTextAreaPanel;
    private JTable posTable;
    private JPanel tablePanel;
    private DefaultTableModel tableModel;
    private JScrollPane tablePane;
    private JButton extractPOSButton;
    private JButton removeTextButton;
    private JButton closeApplicationButton;
    private JPanel buttonPanel;

    /*DATA STRUCTURES*/
    private ArrayList<String> textAreaContents = new ArrayList<>();
    private HashSet<String> verbsSet = new HashSet<>();
    private HashSet<String> nounsSet = new HashSet<>();
    private HashSet<String> adjectivesSet = new HashSet<>();
    private HashSet<String> pronounsSet = new HashSet<>();
    private HashSet<String> adverbsSet = new HashSet<>();
    private HashSet<String> prepositionSet = new HashSet<>();
    private HashSet<String> conjunctionsSet = new HashSet<>();
    private HashSet<String> interjectionsSet = new HashSet<>();
    private ArrayList<POS> finalVerbsList = new ArrayList<>();
    private ArrayList<POS> finalNounsList = new ArrayList<>();
    private ArrayList<POS> finalAdjectivesList = new ArrayList<>();
    private ArrayList<POS> finalPronounsList = new ArrayList<>();
    private ArrayList<POS> finalAdverbsList = new ArrayList<>();
    private ArrayList<POS> finalPrepositionsList = new ArrayList<>();
    private ArrayList<POS> finalConjunctionsList = new ArrayList<>();
    private ArrayList<POS> finalInterjectionsList = new ArrayList<>();
    private List<CoreMap> sentences;
    private String[] rows = new String[8];

    public CategorizePOSScreen() {
        displayScreen();
    }

    private void displayScreen() {
        /*JFRAME IMPLEMENTATION*/
        posFrame = new JFrame("Categorize POS");
        posFrame.setSize(800, 700);
        posFrame.setLayout(new BorderLayout());
        posFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        /*JLABEL AND JTEXTAREA IMPLEMENTATION*/
        headLabel = new JLabel("Enter text:");
        headLabel.setFont(new Font("Arial", Font.BOLD, 20));

        enterText = new JTextArea("", 5, 45);
        enterText.setFont(new Font("Arial", Font.BOLD, 15));
        enterText.setBorder(new LineBorder(Color.black, 5));
        enterText.setLineWrap(true);

        textAreaScroll = new JScrollPane(enterText);

        labelAndTextAreaPanel = new JPanel(new FlowLayout());
        labelAndTextAreaPanel.add(headLabel);
        labelAndTextAreaPanel.add(textAreaScroll);

        posFrame.add(labelAndTextAreaPanel, BorderLayout.NORTH);

        /*JTABLE IMPLEMENTATION*/
        String[] columns = {"Verbs", "Nouns", "Adjectives", "Adverbs", "Pronouns", "Conjunctions", "Prepositions", "Interjections"};
        posTable = new JTable();

        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(columns);
        posTable.setModel(tableModel);
        posTable.setFont(new Font("Arial", Font.BOLD, 12));

        tablePane = new JScrollPane(posTable);

        tablePanel = new JPanel(new BorderLayout(10, 10));
        tablePanel.add(tablePane);

        posFrame.add(tablePanel, BorderLayout.CENTER);

        /*JBUTTON IMPLEMENTATION*/
        extractPOSButton = new JButton("Extract POS");
        extractPOSButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                extractPOS();
            }
        });
        extractPOSButton.setSize(100, 10);

        removeTextButton = new JButton("Clear all");
        removeTextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeItems();
            }
        });

        closeApplicationButton = new JButton("Close");
        closeApplicationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                closeApp();
            }
        });

        buttonPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        buttonPanel.add(extractPOSButton);
        buttonPanel.add(removeTextButton);
        buttonPanel.add(closeApplicationButton);

        posFrame.add(buttonPanel, BorderLayout.SOUTH);

        posFrame.setVisible(true);
    }

    private void extractPOS() {
        textAreaContents.add(enterText.getText());

        Properties nlpProperties = new Properties();
        nlpProperties.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref");
        StanfordCoreNLP stanfordCoreNLP = new StanfordCoreNLP(nlpProperties);

        for(String s : textAreaContents) {
            Annotation documentAnnotation = new Annotation(s);
            stanfordCoreNLP.annotate(documentAnnotation);
            sentences = documentAnnotation.get(CoreAnnotations.SentencesAnnotation.class);

            for(CoreMap sentence : sentences) {
                for(CoreLabel words : sentence.get(CoreAnnotations.TokensAnnotation.class)) {
                    String word = words.get(CoreAnnotations.TextAnnotation.class);
                    String pos = words.get(CoreAnnotations.PartOfSpeechAnnotation.class);

                    switch(pos) {
                        case "VB": verbsSet.add(word);
                        break;

                        case "VBD": verbsSet.add(word);
                        break;

                        case "VBG": verbsSet.add(word);
                        break;

                        case "VBN": verbsSet.add(word);
                        break;

                        case "VBP": verbsSet.add(word);
                        break;

                        case "VBZ": verbsSet.add(word);
                        break;

                        case "NN": nounsSet.add(word);
                        break;

                        case "NNS": nounsSet.add(word);
                        break;

                        case "NNP": nounsSet.add(word);
                        break;

                        case "NNPS": nounsSet.add(word);
                        break;

                        case "JJ": adjectivesSet.add(word);
                        break;

                        case "JJR": adjectivesSet.add(word);
                        break;

                        case "JJS": adjectivesSet.add(word);
                        break;

                        case "PRP": pronounsSet.add(word);
                        break;

                        case "PRP$": pronounsSet.add(word);
                        break;

                        case "RB": adverbsSet.add(word);
                        break;

                        case "RBR": adverbsSet.add(word);
                        break;

                        case "RBS": adverbsSet.add(word);
                        break;

                        case "IN": prepositionSet.add(word);
                        break;

                        case "CC": conjunctionsSet.add(word);
                        break;

                        case "UH": interjectionsSet.add(word);
                        break;
                    }
                }
            }
        }
        addPOSToList(verbsSet, finalVerbsList);
        addPOSToList(nounsSet, finalNounsList);
        addPOSToList(adjectivesSet, finalAdjectivesList);
        addPOSToList(pronounsSet, finalPronounsList);
        addPOSToList(adverbsSet, finalAdverbsList);
        addPOSToList(prepositionSet, finalPrepositionsList);
        addPOSToList(conjunctionsSet, finalConjunctionsList);
        addPOSToList(interjectionsSet, finalInterjectionsList);

        getPOSWordFromList(finalVerbsList, 0);
        getPOSWordFromList(finalNounsList, 1);
        getPOSWordFromList(finalAdjectivesList, 2);
        getPOSWordFromList(finalAdverbsList, 3);
        getPOSWordFromList(finalPronounsList, 4);
        getPOSWordFromList(finalConjunctionsList, 5);
        getPOSWordFromList(finalPrepositionsList, 6);
        getPOSWordFromList(finalInterjectionsList, 7);
    }

    private void getPOSWordFromList(ArrayList<POS> posList, int rowIndex) {
        for(POS p : posList) {
            rows[rowIndex] = p.getWord();
            tableModel.addRow(rows);
        }
    }

    private void addPOSToList(HashSet<String> posSet, ArrayList<POS> posList) {
        for(String s : posSet) {
            posList.add(new POS(s));
        }
    }

    private void removeItems() {
        enterText.setText("");
        textAreaContents.clear();

        try {
            removeSetItems(verbsSet);
            removeSetItems(nounsSet);
            removeSetItems(adjectivesSet);
            removeSetItems(pronounsSet);
            removeSetItems(adverbsSet);
            removeSetItems(prepositionSet);
            removeSetItems(conjunctionsSet);
            removeSetItems(interjectionsSet);
        }
        catch(ConcurrentModificationException c) {
            JOptionPane.showMessageDialog(null, "ERROR REMOVING SET ITEMS");
            c.printStackTrace();
        }
        try {
            removeListItems(finalVerbsList);
            removeListItems(finalNounsList);
            removeListItems(finalAdjectivesList);
            removeListItems(finalPronounsList);
            removeListItems(finalAdverbsList);
            removeListItems(finalPrepositionsList);
            removeListItems(finalConjunctionsList);
            removeListItems(finalInterjectionsList);
        }
        catch(Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR REMOVING LIST ITEMS");
        }
        try {
            removeTableItems();
        }
        catch(Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR REMOVING TABLE ITEMS");
        }
        finally {
            JOptionPane.showMessageDialog(null, "Contents removed");
        }
    }

    private void removeSetItems(HashSet<String> posSet) {
        posSet.clear();
        System.out.println(posSet.size());
    }

    private void removeListItems(ArrayList<POS> posList) {
        posList.clear();
        System.out.println(posList.size());
    }

    private void removeTableItems() {
        tableModel.getDataVector().removeAllElements();
        tableModel.fireTableDataChanged();
    }

    private void closeApp() {
        System.exit(0);
    }
}
