/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu;

import cecs429.documents.DirectoryCorpus;
import cecs429.documents.Document;
import cecs429.documents.DocumentCorpus;
import cecs429.index.DiskPositionalIndex;
import cecs429.index.Positional_inverted_index;
import cecs429.index.Posting;
import cecs429.text.EnglishTokenStream;
import cecs429.text.NewTokenProcessor;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.Reader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import static java.lang.Math.log;
import static java.lang.Math.pow;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * Used to run the search engine through the output window, no GUI Follows the
 * documentation in DocumentIndexder.java.
 *
 * @author bhavy
 */


public class DocumentIndexer_noGUI {

    private static String path="C:\\Users\\bhavy\\Desktop\\SET\\jsonfiles";
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, IOException {

        DocumentCorpus corpus = DirectoryCorpus.loadJsonTextDirectory(Paths.get(path).toAbsolutePath(), ".json"); //to run json files
        
        //String path= "/Users/dayanarios/MobyDick10Chapters"; 
        //DocumentCorpus corpus = DirectoryCorpus.loadTextDirectory(Paths.get(path).toAbsolutePath(), ".txt");// To run .txt files
        Positional_inverted_index index = posindexCorpus(corpus);
        
        DiskIndexWriter d1= new DiskIndexWriter();
        d1.WriteIndex(index,path);
        DiskPositionalIndex dp1 = new DiskPositionalIndex(path);
        List<Posting> result=new ArrayList<>();
        result=dp1.getPositional_posting("format");
        //result=dp1.getPosting_noPos("found");
        
        if(result==null){
        }
        else{
        for(Posting p:result){
            System.out.println("Document: " + p.getDocumentId() + "\t Positions:  " + p.getPositions());
        }}
        //index.print();
        /*
        List<Posting> result = new ArrayList<>();
        List<QueryComponent> l = new ArrayList<>();

        boolean cont = true;

        Scanner scan = new Scanner(System.in);
        String query;

        while (cont) {
            System.out.println("\nEnter your query: ");
            query = scan.nextLine();
            query = query.toLowerCase();

            if (query.equals("quit")) {
                cont = false;
                break;
            }
            BooleanQueryParser b = new BooleanQueryParser();
            QueryComponent c = b.parseQuery(query);
            result = c.getPostings(index);
            DiskIndexWriter(result,path);
             System.out.println(result);
            
            if (result.isEmpty()) {
                System.out.println("No results");
            } else {
                for (Posting p : result) {
                    //   System.out.println(result);
                    System.out.println("Doctument: " + p.getDocumentId() + "\t Positions:  " + p.getPositions());;
                }
            }

    
        }*/

    }

    private static Positional_inverted_index posindexCorpus(DocumentCorpus corpus) throws ClassNotFoundException, InstantiationException, IllegalAccessException, FileNotFoundException, IOException {

        NewTokenProcessor processor = new NewTokenProcessor();
        Iterable<Document> docs = corpus.getDocuments(); //call registerFileDocumentFactory first?
        
        //StrategyFactory sf = new StrategyFactory();
        //StrategyInterface s1 = sf.execute(rankedOption);
        
        List<Double> Doc_length = new ArrayList<>();
        Positional_inverted_index index = new Positional_inverted_index();
        
       
        double token_count=0;
        double N=0;
        // Iterate through the documents, and:
        for (Document d : docs) {
           // File f = new File(path + "\\" + d.getTitle());
            File f = new File(path+"\\"+d.getFileName().toString());
            double Filesize = f.length(); 
            //edited by bhavya
            double doc_weight=0; //first entry in docweights.bin
            double doc_tokens=0;
            double doc_length=0;
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

                if (word.size() > 0) {

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
                 //   token_count++; //gives total number of tokens.
                    tftd.put(word.get(0), 1);
                }

            }
            double length = 0;
            double wdt = 0;
            //calculate w(d,t)= 1 + ln(tft)
            double total_tftd=0;
            for (Map.Entry<String, Integer> entry : tftd.entrySet()) {

                wdt = 1 + log(entry.getValue());

                length = length + pow(wdt, 2);
                total_tftd=total_tftd + entry.getValue();
            }
            token_count=token_count+doc_tokens;
            doc_weight=pow(length,0.5);
            double avg_tftd = total_tftd / (double) tftd.size();
            //doclength = doc_tokens
            //bytesize=filesize
            Doc_length.add(doc_weight);
            Doc_length.add(avg_tftd);
            Doc_length.add(doc_tokens);
            Doc_length.add(Filesize);
        }
        Doc_length.add(token_count/N);
        System.out.println("Total docs: "+ N);
        System.out.println("Total tokens:" + token_count);
        System.out.println("Doclength A: " + token_count/N);
        DiskIndexWriter d = new DiskIndexWriter();
        d.write_doc(path, Doc_length);
       
        return index;
    }

}
