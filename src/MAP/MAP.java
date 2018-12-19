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
 * Mean Average Precision (MAP) class calculates the mean average precision for
 * a given set of queries and relevance judgments and the throughput and mean
 * response time of our search engine.
 *
 * @author dayanarios
 */
public class MAP {

    private String qRel_path;
    private String queries_path;

    private HashMap<String, List> qRel = new HashMap();
    private HashMap<String, List> poseRel = new HashMap();
    List<Integer> test = new ArrayList();
    double numQueries = 0;
    List<Double> avgPrecisions = new ArrayList();
    private List<Double> results = new ArrayList();

    public MAP(String path) throws FileNotFoundException {
        this.qRel_path = path + "/relevance/qrel.txt";
        this.queries_path = path + "/relevance/queries.txt";
        readFiles();
    }

    //read qrel and queries store the data into qRel hash map<query, qrel>
    private void readFiles() throws FileNotFoundException {

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

        // System.out.println("reading files completed");
    }

    //add results from ranked retreival to hash map
    public void add_poseRel(String key, List<String> values) {
        //System.out.print(key + "\n");
        List<Integer> docs = new ArrayList();

        for (String s : values) {
            String temp = s.replace(".json", "");
            docs.add(Integer.parseInt(temp));
        }

        poseRel.put(key, docs);

    }

    public Set<String> getQueries() {
        return qRel.keySet();
    }

    /*
    * Functions calculationg MAP, throughput, mean response time below
     */
    public void calc_avgPrecision() {
        int count = 0;
        for (String query : qRel.keySet()) {

            numQueries++;
            List<Integer> relevant = qRel.get(query);
            List<Integer> candidates = poseRel.get(query);
            double k = 0;
            double p = 0;

            List<Double> precisions = new ArrayList();

            for (Integer doc : candidates) {
                k++;

                if (relevant.contains(doc)) {
                    p++;
                    double p_at_k = p / k;
                    double r_at_k = p / relevant.size();

                    precisions.add(p_at_k);
                } else {
                    double p_at_k = p / k;
                    double r_at_k = p / relevant.size();

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
        result = sum / rel;

        return (result);
    }

    public Double mean_ap() {
        calc_avgPrecision();

        double sum = 0;
        for (double ap : avgPrecisions) {

            sum += ap;
        }
        return (sum / numQueries);
    }

    public double calculae_mean_response_time(List<Long> time) {
        double seconds = 0;
        for (long d : time) {
            seconds = seconds + (double) d / 1000;
        }
        double mean_time = (seconds / numQueries);
        return mean_time;
    }

    public double calculate_throughput(List<Long> time) {

        double seconds = 0;

        for (long d : time) {
            seconds = seconds + (double) d / 1000;
        }
        double throughput = numQueries / seconds;

        return throughput;

    }

}
