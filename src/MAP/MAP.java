/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MAP;

import java.io.File;
import java.io.FileNotFoundException;
import static java.lang.Math.round;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

/**
 *
 * @author dayanarios
 */
public class MAP {
    private static String qRel_path = "/Users/dayanarios/relevance_cranfield/relevance/qrel.txt";
    private static String queries_path = "/Users/dayanarios/relevance_cranfield/relevance/queries.txt";
    private HashMap<String, List> qRel = new HashMap();
    private HashMap<String, List> poseRel = new HashMap();
    List <Integer> test = new ArrayList(); 
    double numQueries = 0; 
    List <Double> avgPrecisions = new ArrayList(); 

    public MAP() throws FileNotFoundException {
       
        readFiles();
    }

    //read qrel and queries store the data into qRel hash map<query, qrel>
    public void readFiles() throws FileNotFoundException {
        
        File f = new File(qRel_path);
        File f1 = new File(queries_path);
        Scanner sc = new Scanner(f);
        Scanner sc1 = new Scanner(f1);
        while (sc1.hasNextLine() && sc.hasNextLine()) {
            List<Integer> filenames = new ArrayList<>();
            String s = sc.nextLine();
            String[] s1 = s.split(" ");
            //List<Integer> x1 = new ArrayList<>();
            for (int i = 0; i < s1.length; i++) {
                if (s1[i].equals("")) {
                } else {
                    int p = Integer.parseInt(s1[i]);
                    filenames.add(p);
                }
            }
            qRel.put(sc1.nextLine(), filenames);
        }
        
        
    }

    //add results from ranked retreival to hash map
    public void add_poseRel(String key, List<String> values) {
        System.out.print(key + "\n");
        List<Integer> docs = new ArrayList();

        for (String s : values) {
            String temp = s.replace(".json", "");
            docs.add(Integer.parseInt(temp));
        }

        /*
        for (Integer s : docs){
            System.out.println(s); 
        }
        */
        poseRel.put(key, docs);

    }

    public Set<String> getQueries() {
        return qRel.keySet();
    }

    /*
    * Functions calculationg MAP, throughput, mean response time below
     */
    public void calc_avgPrecision() {
        for (String query : qRel.keySet()) {
            System.out.println("\nPrecision of: " + query);;
            numQueries++;
            List<Integer> relevant = qRel.get(query);
            List<Integer> candidates = poseRel.get(query);
            double k = 0;
            double p = 0;
            
            List<Double> precisions = new ArrayList();
            
            System.out.println("num of relevant docs: " +  relevant.size()); 
            
            for (Integer doc : candidates) {
                k++;
                
                if (relevant.contains(doc)) {
                    p++;
                    double p_at_k = p / k;
                    double r_at_k = p/relevant.size();
                    System.out.println("Doc : " + k + " P@K : " + p_at_k + " R@K : " + r_at_k);
                    //System.out.print(" P@K :" + p_at_k);
                    precisions.add(p_at_k);
                }
                else{
                    double p_at_k = p / k;
                    double r_at_k = p/relevant.size();
                    System.out.println("Doc : " + k + " P@K : " + p_at_k + " R@K : " + r_at_k); 
                    
                }
            }

            avgPrecisions.add(ap(relevant.size(), precisions));

        }
        
        
    }

    public Double ap(int r, List<Double> precision) {
        double sum = 0;
        double result;
        double rel = (double) r;
        for (double p : precision) {
           // System.out.println("Precision: " + p);
            sum += p;
        }
        result=sum/rel;
        System.out.println("Average precision for query: " + result);
        return (result);
    }

    public Double mean_ap() {
        calc_avgPrecision();
        System.out.println("\nnum of queries: " + numQueries);
        double sum = 0;
        for (double ap : avgPrecisions) {
           // System.out.println("Average Precision: " + ap);
            sum += ap;
        }
        return (sum / numQueries);
    }
    
    public double calculae_mean_response_time(long time){
        double seconds = (double) time / 1000000000.0;
        double mean_time= (seconds/numQueries);
        return mean_time ;
    }
    public double calculate_throughput(long time){
        double seconds = (double) time / 1000000000.0;
        double throughput= numQueries/seconds ;
        
        return throughput;
        
    }
}






    
    
    

