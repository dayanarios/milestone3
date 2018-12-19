/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu;

import MAP.MAP;
import Strategy.StrategyFactory;
import Strategy.StrategyInterface;
import cecs429.documents.DirectoryCorpus;
import cecs429.documents.Document;
import cecs429.documents.DocumentCorpus;
import cecs429.index.DiskPositionalIndex;
import cecs429.index.Doc_accum;
import cecs429.index.Index;
import cecs429.index.Positional_inverted_index;
import cecs429.index.Posting;
import cecs429.query.BooleanQueryParser;
import cecs429.query.QueryComponent;
import cecs429.text.EnglishTokenStream;
import cecs429.text.NewTokenProcessor;
import cecs429.text.TokenProcessor;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import static java.lang.Math.log;
import static java.lang.Math.pow;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import javax.swing.JOptionPane;

/**
 * Main driver class used in the GUI to run the search engine
 *
 * @author dayanarios
 */
public class DocumentIndexer {

    protected static DocumentCorpus corpus;
    protected static String query;
    protected static List<Posting> postings = new ArrayList<>();
    protected static Boolean clickList = false;  //prevents clicking the list when there is nothing to click
    protected static Boolean booleanMode = true; //indicates which mode the search engine should run in
    protected static Boolean rankedMode = false;
    protected static Boolean queryMode = true;
    protected static Boolean buildMode = false;
    protected static Boolean prMode = false;
    private int topK = 10; //search engine always returns the top K = 10 docs
    private static double N = 0; //corpus size
    private static String path;
    protected static int rankedOption = 0;
    private static DiskPositionalIndex Disk_posIndex;
    private static DiskIndexWriter diskWriter = new DiskIndexWriter();
    private HashMap<String, List> qRel = new HashMap<String, List>();
    private HashMap<String, List> poseRel = new HashMap<String, List>();
    private static List<Double> tp = new ArrayList();

    /**
     * Indexes the corpus given by the path parameter, records the time it takes
     * to index. Search Engine supports two modes: query index and build index
     * Additional runs two different retrievals: boolean and ranked Writes or
     * queries disk positional inverted index
     *
     * @param path supplied by user in
     * GUI.SearchDirectoriesButtonActionPerformed
     *
     */
    protected static void startIndexing(Path p, int option) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IOException {

        path = p.toString();
        //corpus = DirectoryCorpus.loadTextDirectory(p.toAbsolutePath(), ".txt");// To run .txt files
        corpus = DirectoryCorpus.loadJsonTextDirectory(p.toAbsolutePath(), ".json");

        if (option == 0) { //build index
            buildMode = true;
            queryMode = false;
            long startTime = System.nanoTime();
            Index index = posindexCorpus(corpus);
            diskWriter.WriteIndex(index, path);
            Disk_posIndex = new DiskPositionalIndex(path);
            long endTime = System.nanoTime();

            long elapsedTime = (endTime - startTime);
            double seconds = (double) elapsedTime / 1000000000.0;
            GUI.ResultsLabel.setText("Total Indexing Time: " + new DecimalFormat("##.##").format(seconds) + " seconds");

        } else { //query index
            buildMode = false;
            queryMode = true;
            Disk_posIndex = new DiskPositionalIndex(path);
            corpus.getDocuments();
            N = corpus.getCorpusSize();
        }

    }

    protected static void startSearchEngine() throws ClassNotFoundException, InstantiationException, IllegalAccessException, IOException {

        if (booleanMode) {
            BooleanQueryMode(diskWriter, Disk_posIndex);
        } else if (rankedMode) {

            RankedQueryMode(diskWriter, Disk_posIndex);
        } else {
            prMode(diskWriter, Disk_posIndex);
        }

    }

    /**
     * Runs the search engine in Boolean Query Mode. Checks for the existence of
     * special queries. Ensures the list is cleared after every new search
     * query. Displays any relevant result in the GUI's result label.
     *
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    private static void BooleanQueryMode(DiskIndexWriter diskWriter, DiskPositionalIndex disk_posIndex) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        if (!specialQueries(query)) {
            //newCorpus = false; 
            BooleanQueryParser bParser = new BooleanQueryParser();
            QueryComponent qComponent = bParser.parseQuery(query);
            postings = qComponent.getPostings(disk_posIndex);

            if (postings.isEmpty()) {
                GUI.JListModel.clear();
                GUI.ResultsLabel.setText("");
                clickList = false;

                String notFound = "Your search '" + query + "' is not found in any documents";
                GUI.ResultsLabel.setText(notFound);

            } else {
                clickList = true;

                //clears list and repopulates it 
                String docInfo;

                GUI.JListModel.clear();
                GUI.ResultsLabel.setText("");

                for (Posting p : postings) {
                    if (queryMode) {
                        corpus.getDocument(p.getDocumentId()).getContent();
                    }
                    docInfo = corpus.getDocument(p.getDocumentId()).getTitle();
                    //docInfo = corpus.getDocument(p.getDocumentId()).getFileName().toString();
                    GUI.JListModel.addElement(docInfo);

                }
                GUI.ResultsLabel.setText("Total Documents Found: " + postings.size());
            }

        }

        //GUI.SearchBarTextField.setText("Enter a new search or 'q' to exit");
        GUI.SearchBarTextField.selectAll();

    }

    /**
     * Processes a query without any Boolean operators and returns the top K=10
     * documents satisfying the query.
     */
    private static void RankedQueryMode(DiskIndexWriter diskWriter, DiskPositionalIndex disk_posIndex) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        if (query.equals("q")) {

            System.exit(0);
        }

        List<String> word = new ArrayList();
        String[] query_array = query.split("\\s+");
        TokenProcessor processor = new NewTokenProcessor();

        for (int i = 0; i < query_array.length; i++) {
            word.add(processor.processToken(query_array[i]).get(0));
        }

        StrategyFactory sf = new StrategyFactory();
        StrategyInterface strategy = sf.execute(rankedOption);

        //maps postings to accumulator value
        HashMap<Integer, Doc_accum> postingMap = new HashMap<Integer, Doc_accum>();

        PriorityQueue<Doc_accum> queue = new PriorityQueue<>(Collections.reverseOrder());

        List<Posting> postings = new ArrayList<>();
        List<Doc_accum> results = new ArrayList<>();

        for (String term : word) {

            postings = disk_posIndex.getPosting_noPos(term);
            for (Posting p : postings) { //for each document in the postings list
                double t_fd = p.getT_fd();
                double d_ft = p.getD_ft();
                double w_qt = strategy.calculate_wqt(N, d_ft);
                double accum = 0;
                double w_dt = strategy.get_wdt(t_fd, disk_posIndex, p.getDocumentId());

                //pairs (Ranked_posting, accumulator factor)
                if (postingMap.containsKey(p.getDocumentId())) {
                    accum = postingMap.get(p.getDocumentId()).getAccumulator();
                    accum += (w_qt * w_dt);
                    postingMap.replace(p.getDocumentId(), new Doc_accum(p, accum)); //replaces old accum value

                } else {
                    accum += (w_qt * w_dt);
                    postingMap.put(p.getDocumentId(), new Doc_accum(p, accum));
                }
            }

        }

        for (Integer p : postingMap.keySet()) {
            Doc_accum doc_temp = postingMap.get(p);
            double accum = doc_temp.getAccumulator(); //gets accum associated with doc

            if (accum > 0) {
                //search for each p's Ld factor in docWeights.bin
                double l_d = strategy.calculate_Ld(disk_posIndex, doc_temp.getPosting().getDocumentId());

                accum /= l_d;
                doc_temp.setAccumulator(accum);
            }

            queue.add(postingMap.get(p));

        }

        //returns top K=10 results 
        int topK = 50;

        while ((results.size() < topK) && queue.size() > 0) {

            results.add(queue.poll());  //gets the posting acc pair and returns only posting

        }

        display_RankedResults(results);

    }

    public static void display_RankedResults(List<Doc_accum> results) {
        if (results.isEmpty()) {
            GUI.JListModel.clear();
            GUI.ResultsLabel.setText("");
            clickList = false;

            String notFound = "Your search '" + query + "' is not found in any documents";
            GUI.ResultsLabel.setText(notFound);

        } else {
            clickList = false;

            //clears list and repopulates it 
            String docInfo;

            GUI.JListModel.clear();
            GUI.ResultsLabel.setText("");

            for (Doc_accum p : results) {
                if (queryMode) {
                    corpus.getDocument(p.getPosting().getDocumentId()).getContent();
                }
                //docInfo = corpus.getDocument(p.getPosting().getDocumentId()).getTitle();
                docInfo = corpus.getDocument(p.getPosting().getDocumentId()).getFileName().toString();
                //System.out.println(docInfo); 
                docInfo += " " + p.getAccumulator();
                GUI.JListModel.addElement(docInfo);

            }
            GUI.ResultsLabel.setText("Total Documents Found: " + results.size());
        }

        //GUI.SearchBarTextField.setText("Enter a new search or 'q' to exit");
        GUI.SearchBarTextField.selectAll();
    }

    /**
     * Creates a positional inverted index Tokenizes all the terms found in each
     * document in the corpus. Calculates Ld, ave(tftd), tftd, bytesize for each
     * document Calculates total number of tokens in entire corpus Writes
     * docWeights to disk
     *
     * @param corpus corpus to be indexed
     * @return a positional inverted index over the corpus
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    private static Positional_inverted_index posindexCorpus(DocumentCorpus corpus) throws ClassNotFoundException, InstantiationException, IllegalAccessException, FileNotFoundException, IOException {

        NewTokenProcessor processor = new NewTokenProcessor();
        Iterable<Document> docs = corpus.getDocuments(); //call registerFileDocumentFactory first?

        List<Double> Doc_length = new ArrayList<>();
        Positional_inverted_index index = new Positional_inverted_index();

        double token_count = 0;

        // Iterate through the documents, and:
        for (Document d : docs) {
            //File f = new File(path + "\\" + d.getTitle());
            File f = new File(path + "\\" + d.getFileName().toString());
            double Filesize = f.length();
            //edited by bhavya
            double doc_weight = 0; //first entry in docweights.bin
            double doc_tokens = 0;
            double doc_length = 0;
            HashMap<String, Integer> tftd = new HashMap<>();
            // Tokenize the document's content by constructing an EnglishTokenStream around the document's content.
            Reader reader = d.getContent();
            EnglishTokenStream stream = new EnglishTokenStream(reader); //can access tokens through this stream
            N++;
            // Iterate through the tokens in the document, processing them using a BasicTokenProcessor,
            //		and adding them to the HashSet vocabulary.
            Iterable<String> tokens = stream.getTokens();
            int i = 0;

            for (String token : tokens) {

                //adding token in index
                List<String> word = new ArrayList<String>();
                word = processor.processToken(token);
                //System.out.println(word.get(0));
                if (word.get(0).matches("")) {
                    continue;
                } else {

                    index.addTerm(word.get(0), i, d.getId());

                }
                i = i + 1;

                //we check if token already exists in hashmap or not. 
                //if it exists, increase its freq by 1 else make a new entry.
                if (tftd.containsKey(word.get(0))) {
                    doc_tokens++;
                    int count = tftd.get(word.get(0));
                    tftd.replace(word.get(0), count + 1);
                } else {
                    doc_tokens++;
//                    token_count++; //gives total number of tokens.
                    tftd.put(word.get(0), 1);
                }

            }
            double length = 0;
            double wdt = 0;

            double total_tftd = 0;
            for (Map.Entry<String, Integer> entry : tftd.entrySet()) {

                wdt = 1 + log(entry.getValue());

                length = length + pow(wdt, 2);
                total_tftd = total_tftd + entry.getValue();
            }
            token_count = token_count + doc_tokens;
            doc_weight = pow(length, 0.5);
            double avg_tftd = total_tftd / (double) tftd.size();

            Doc_length.add(doc_weight);
            Doc_length.add(avg_tftd);
            Doc_length.add(doc_tokens);
            Doc_length.add(Filesize);
        }
        Doc_length.add(token_count / N);

        DiskIndexWriter d = new DiskIndexWriter();
        d.write_doc(path, Doc_length);

        return index;
    }

    /**
     * Checks for the existence of special queries Checks if the user quit the
     * program Checks if the user wants to stem a word Checks if the user wants
     * to return the first 1000 terms in the vocab
     *
     * @param query inputted by the user
     * @return true if a special query was performed false otherwise
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    private static Boolean specialQueries(String query) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        clickList = false;
        //newCorpus = true;

        if (query.equals("q")) {

            System.exit(0);
            return true;
        }

        String[] subqueries = query.split("\\s+"); //split around white space

        if (subqueries[0].equals("stem")) //first term in subqueries tells computer what to do 
        {
            GUI.JListModel.clear();
            GUI.ResultsLabel.setText("");
            TokenProcessor processor = new NewTokenProcessor();
            if (subqueries.length > 1) //user meant to stem the token not to search stem
            {
                List<String> stems = processor.processToken(subqueries[1]);

                //clears list and repopulates it 
                String stem = "Stem of query '" + subqueries[1] + "' is '" + stems.get(0) + "'";

                GUI.ResultsLabel.setText(stem);

                return true;
            }

        } else if (subqueries[0].equals("vocab")) {
            List<String> vocabList = Disk_posIndex.getVocabulary();
            GUI.JListModel.clear();
            GUI.ResultsLabel.setText("");

            int vocabCount = 0;
            for (String v : vocabList) {
                if (vocabCount < 1000) {
                    vocabCount++;
                    GUI.JListModel.addElement(v);
                }
            }
            GUI.ResultsLabel.setText("Total size of vocabulary: " + vocabCount);
            return true;
        }

        return false;
    }

    /**
     * Checks if the user wants to index a new corpus
     *
     * @return true if the user wants to index a new corpus false otherwise
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    protected static Boolean newCorpus() throws ClassNotFoundException, InstantiationException, IllegalAccessException, IOException {
        String[] subqueries = query.split("\\s+"); //split around white space
        if (subqueries[0].equals("index") && subqueries.length > 1) {
            JOptionPane.showOptionDialog(GUI.indexingCorpusMessage, "Indexing corpus please wait", "Indexing Corpus", javax.swing.JOptionPane.DEFAULT_OPTION, javax.swing.JOptionPane.INFORMATION_MESSAGE, null, null, null);
            GUI.JListModel.clear();
            GUI.ResultsLabel.setText("Indexing");
            startIndexing(Paths.get(subqueries[1]), 0);
            GUI.SearchBarTextField.setText("Enter a new search or 'q' to exit");

            return true;
        }
        return false;
    }


    /*
        *   Executes ranked retrieval as our RankedQueryMode function but returns
        *   relevant results as an array of file names to be used in the MAP 
        *   class. 
     */
    private static void prMode(DiskIndexWriter diskWriter, DiskPositionalIndex disk_posIndex) throws ClassNotFoundException, InstantiationException, IllegalAccessException, FileNotFoundException {
        long time = 0;
        GUI.JListModel.clear();

        if (query.equals("q")) {

            System.exit(0);
        }
        MAP mean_ap = new MAP(path);
        //returns qRel.keyset() i.e. queries 
        Set<String> queries = mean_ap.getQueries();
        List<Long> time_per_query = new ArrayList<>();
        //run ranked retrival for each query
        for (String q : queries) {
            time = 0;
            List<String> word = new ArrayList();

            query = q;
            long start = System.currentTimeMillis();

            String[] query_array = query.split("\\s+");
            TokenProcessor processor = new NewTokenProcessor();

            for (int i = 0; i < query_array.length; i++) {
                word.add(processor.processToken(query_array[i]).get(0));
            }
            long t1 = System.currentTimeMillis();
            time = t1 - start; //time to parse
            StrategyFactory sf = new StrategyFactory();
            StrategyInterface strategy = sf.execute(rankedOption);

            //maps postings to accumulator value
            HashMap<Integer, Doc_accum> postingMap = new HashMap<Integer, Doc_accum>();

            PriorityQueue<Doc_accum> queue = new PriorityQueue<>(Collections.reverseOrder());

            List<Posting> postings = new ArrayList<>();
            List<Doc_accum> results = new ArrayList<>();

            for (String term : word) {
                long t2 = System.currentTimeMillis();

                if (term.equals("")) {
                    continue;
                } else {

                    postings = disk_posIndex.getPosting_noPos(term);
                    long t3 = System.currentTimeMillis();
                    time = time + (t3 - t2);

                    for (Posting p : postings) { //for each document in the postings list
                        double t_fd = p.getT_fd();
                        double d_ft = p.getD_ft();
                        double w_qt = strategy.calculate_wqt(N, d_ft);
                        double accum = 0;
                        double w_dt = strategy.get_wdt(t_fd, disk_posIndex, p.getDocumentId());

                        //pairs (Ranked_posting, accumulator factor)
                        if (postingMap.containsKey(p.getDocumentId())) {
                            accum = postingMap.get(p.getDocumentId()).getAccumulator();
                            accum += (w_qt * w_dt);
                            postingMap.replace(p.getDocumentId(), new Doc_accum(p, accum)); //replaces old accum value

                        } else {
                            accum += (w_qt * w_dt);
                            postingMap.put(p.getDocumentId(), new Doc_accum(p, accum));
                        }
                    }

                }
            }
            for (Integer p : postingMap.keySet()) {
                Doc_accum doc_temp = postingMap.get(p);
                double accum = doc_temp.getAccumulator(); //gets accum associated with doc

                if (accum > 0) {
                    //search for each p's Ld factor in docWeights.bin
                    double l_d = strategy.calculate_Ld(disk_posIndex, doc_temp.getPosting().getDocumentId());

                    accum /= l_d;
                    doc_temp.setAccumulator(accum);
                }

                queue.add(postingMap.get(p));

            }

            //returns top K=10 results 
            int topK = 50;

            while ((results.size() < topK) && queue.size() > 0) {

                results.add(queue.poll());  //gets the posting acc pair and returns only posting

            }
            time_per_query.add(time);
            List<String> filenames = get_RankedResults(results);

            mean_ap.add_poseRel(q, filenames);

        }

        double map_result = mean_ap.mean_ap();
        String mapDisplay = "Mean average precision: " + map_result;
        GUI.JListModel.addElement(mapDisplay);
        System.out.println("\n" + mapDisplay);

        double throughput_result = mean_ap.calculate_throughput(time_per_query);
        String throughput_Display = "Throughput of the system is: " + throughput_result;
        GUI.JListModel.addElement(throughput_Display);
        System.out.println(throughput_Display);

        double mrt_result = mean_ap.calculae_mean_response_time(time_per_query);
        String mrt_Display = "Mean Response Time of the system is: " + mrt_result;
        GUI.JListModel.addElement(mrt_Display);
        System.out.println(mrt_Display);

    }

    /*
    *   Returns filenames of relevant docs
     */
    public static List<String> get_RankedResults(List<Doc_accum> results) {
        List<String> filenames = new ArrayList();

        if (results.isEmpty()) {
            GUI.JListModel.clear();
            GUI.ResultsLabel.setText("");
            clickList = false;

            String notFound = "Your search '" + query + "' is not found in any documents";
            GUI.ResultsLabel.setText(notFound);

        } else {
            clickList = false;

            //clears list and repopulates it 
            String docInfo;

            GUI.JListModel.clear();
            GUI.ResultsLabel.setText("");

            for (Doc_accum p : results) {
                if (queryMode) {
                    corpus.getDocument(p.getPosting().getDocumentId()).getContent();
                }
                //docInfo = corpus.getDocument(p.getPosting().getDocumentId()).getTitle();
                docInfo = corpus.getDocument(p.getPosting().getDocumentId()).getFileName().toString();
                filenames.add(docInfo);

            }
        }

        GUI.SearchBarTextField.selectAll();

        return filenames;
    }

    public static void TP30(double time) {
        tp.add(time);
        System.out.println("iteration number: " + tp.size());

        if (tp.size() == 30) {
            double sum = 0;
            for (double t : tp) {
                sum += t;
            }
            double size = tp.size();
            System.out.println("\nThroughput over 30 iterations: " + sum / size);
        }
    }

}
