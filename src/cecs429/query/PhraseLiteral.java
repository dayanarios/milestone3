package cecs429.query;

import cecs429.index.DiskPositionalIndex;
import cecs429.index.Posting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import cecs429.index.Index;
import cecs429.text.NewTokenProcessor;
import cecs429.text.TokenProcessor;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Represents a phrase literal consisting of one or more terms that must occur
 * in sequence. Checks positions of term in document to ensure it is a phrase
 * literal
 *
 * @author: dayana, bhavya
 */
public class PhraseLiteral implements QueryComponent {
    // The list of individual terms in the phrase.

    private List<String> string_terms = new ArrayList<>();
    private int k;
    private List<QueryComponent> mTerms = new ArrayList<>();

    
    /**
     * Constructs a PhraseLiteral with the given individual phrase terms.
     */
    
    /*
    public PhraseLiteral(List<String> terms, int k) {
        mTerms.addAll(terms);
        this.k = k; 
    }
    
    public PhraseLiteral(List<String> terms) {
        mTerms.addAll(terms);
        this.k = 1;
    }
    */
    /**
     * Constructs a PhraseLiteral given a string with one or more individual
     * terms separated by spaces.
     */
    
    public PhraseLiteral(String terms, int k) {
        List<String> temp = Arrays.asList(terms.split(" "));
        for(String t: temp){
            mTerms.add(new TermLiteral(t)); 
        }
        
        this.k = k;
    }
    
    
    public PhraseLiteral(List<QueryComponent> terms) {
        mTerms.addAll(terms);
        this.k = 1;
    }
    
        public PhraseLiteral(List<QueryComponent> terms, int k) {
        mTerms.addAll(terms);
        this.k = k;
    }

    

    

    /**
     * Constructs a list of postings that contains the phrase literal
     *
     * @param index index to retrieve postings form
     * @return merged list of postings that contain the given phrase literal
     */
    @Override
    public List<Posting> getPostings(DiskPositionalIndex d) {
        /*
        TokenProcessor processor = new NewTokenProcessor();
        List<String> queries = new ArrayList();
        List<String> temp;

        try {
            for (String term : mTerms) {
                temp = new ArrayList(processor.processToken(term));
                queries.add(temp.get(0));

            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TermLiteral.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(TermLiteral.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(TermLiteral.class.getName()).log(Level.SEVERE, null, ex);
        }
*/

        
        
        
        // TODO: program this method. Retrieve the postings for the individual terms in the phrase,
        // and positional merge them together.
        List<Posting> results = new ArrayList<>();

        List<Posting> postings1 = new ArrayList<>();
        List<Posting> postings2 = new ArrayList<>();

        //number of times we want to perform merge.
        int count = mTerms.size() - 1;
        int indexQueries = 1;

        Boolean firstPass = true;

        if (mTerms.size() == 1) //only have one term no merge needed
        {
              results = mTerms.get(indexQueries - 1).getPostings(d);
        }

        while (count > 0) {

            postings1 = mTerms.get(indexQueries - 1).getPostings(d); //gets postings for terms
            postings2 = mTerms.get(indexQueries).getPostings(d);


            if (!postings1.isEmpty() && !postings2.isEmpty() && firstPass) {
                results = merge(postings1, postings2, postings1.size(), postings2.size(), k);

                firstPass = false;
            } else if (!postings1.isEmpty() && !postings2.isEmpty()) {
                results = merge(results, postings2, results.size(), postings2.size(), k);
            }
            

            indexQueries++;
            count--;
        }

        return results;

    }

    /**
     * merges two list on the condition that the position of the given term is
     * off by one in the positions for posting2
     *
     * @param postings1 list 1 of postings to be merged
     * @param postings2 list 2 of postings to be merged
     * @param postings1_length length of postings1
     * @param postings2_length length of postings2
     * @return
     *
     * modified by bhavya
     */
    public List<Posting> merge(List<Posting> postings1, List<Posting> postings2, int postings1_length, int postings2_length, int k) {
        List<Posting> results = new ArrayList();
        List<Integer> positions1 = new ArrayList();
        List<Integer> positions2 = new ArrayList();

        int index1 = 0;
        int index2 = 0;

        while (index2 < postings2_length && index1 < postings1_length) {

            positions1 = postings1.get(index1).getPositions();   //get positions current element being analized
            positions2 = postings2.get(index2).getPositions();

            int pos1 = 0;
            int pos2 = 0;

            if (postings1.get(index1).getDocumentId() == postings2.get(index2).getDocumentId()) {
                while (pos1 < positions1.size() && pos2 < positions2.size()) {
                    if (positions1.get(pos1) + k == positions2.get(pos2)) {
                        //Initial stage, when the result list is empty, simply add the posting with current position instead of all the positions.
                        if (results.isEmpty()) {
                            Posting p = new Posting(postings2.get(index2).getDocumentId(), positions2.get(pos2));
                            results.add(p);
                        } //if results is not empty, and it contains documentID already, simply add the position.
                        else if (results.get(results.size() - 1).getDocumentId() == postings2.get(index2).getDocumentId()) {
                            Posting p = results.get(results.size() - 1);
                            p.addPosition(positions2.get(pos2));
                        } //if doc id is not present, add it.
                        else {
                            Posting p = new Posting(postings2.get(index2).getDocumentId(), positions2.get(pos2));
                            results.add(p);
                        }
                        pos1++;
                        pos2++;
                        if (pos1 == positions1.size() || pos2 == positions2.size()) {
                            break;
                        }
                    } else if (positions1.get(pos1) < positions2.get(pos2)) {
                        pos1++;
                        if (pos1 == positions1.size()) {
                            //If we reach end of one posting list, no need to search the other list.
                            break;
                        }
                    } else if (positions2.get(pos2) < positions1.get(pos1)) {
                        pos2++;
                        if (pos2 == positions2.size()) {
                            //If we reach end of one posting list, no need to search the other list.
                            break;
                        }
                    } else {
                        pos1++;
                        pos2++;
                        if (pos1 == positions1.size() || pos2 == positions2.size()) {
                            //If we reach end of any posting list, no need to search the other list.
                            break;
                        }
                    }

                }
                //increment docID
                index1++;
                index2++;

            } else if (postings1.get(index1).getDocumentId() < postings2.get(index2).getDocumentId()) {
                index1++;
            } else if (postings1.get(index1).getDocumentId() > postings2.get(index2).getDocumentId()) {
                index2++;
            }

        }

        return results;
    }

    @Override
    public String toString() {
        return  mTerms.toString(); //"\"" + String.join(" ", mTerms) + "\"";
    }

    /**
     * Used to determine whether component is a positive or negative literal
     *
     * @return true for positive
     */
    @Override
    public Boolean Component() {
        return true;
    }
/*
    @Override
    public List<Posting> getPosting_noPositions(DiskPositionalIndex d) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }*/
}
