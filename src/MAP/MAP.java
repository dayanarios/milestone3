/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MAP;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 *
 * @author dayanarios
 */
public class MAP {
    private static String qRel_path; 
    private static String queries_path;
    private HashMap<String, List> qRel = new HashMap<String, List>(); 
    private HashMap<String, List> poseRel = new HashMap<String, List>(); 
    
    public MAP(String qRel, String queries_path){
        this.qRel_path = qRel; 
        this.queries_path = queries_path; 
        
        readFiles(); 
    }
    
    //read qrel and queries store the data into qRel hash map<query, qrel>
    public void readFiles(){
        
    }
    
    //add results from ranked retreival to hash map
    public void add_poseRel(String key, List<String> values){
        
    }
    
    public Set<String> getQueries(){
        return qRel.keySet();
    }
    
    /*
    * Functions calculationg MAP, throughput, mean response time below
    */
}
