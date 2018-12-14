/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Strategy;

import cecs429.index.DiskPositionalIndex;
import java.io.IOException;
import static java.lang.Math.log;
import static java.lang.Math.pow;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Calculates tf-idf strategy from variant tf-idf formulas. Respective functions
 * calculate wqt, wdt, Ld
 *
 * @author bhavy
 */
public class TF_IDF_Strategy implements StrategyInterface {

    double wdt = 0;
    double length = 0;
    double doclength = 0;

    @Override
    public double calculate_wqt(double N, double dft) {
        return log((N / dft));
    }

    @Override
    public double calculate_Ld(DiskPositionalIndex index, int docId) {
        try {
            doclength = index.getL_d(docId);
        } catch (IOException ex) {
            Logger.getLogger(TF_IDF_Strategy.class.getName()).log(Level.SEVERE, null, ex);
        }
        return doclength; //value already exist in docweights Ld calculate during indexing
    }

    @Override
    public double get_wdt(double t_fd, DiskPositionalIndex index, int docID) {
        return t_fd;

    }
}
