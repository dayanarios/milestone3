/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Strategy;

import cecs429.index.DiskPositionalIndex;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import static java.lang.Math.log;
import static java.lang.Math.pow;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Calculates default strategy from variant tf-idf formulas. Respective
 * functions calculate wqt, wdt, Ld
 *
 * @author bhavy
 */
public class DefaultStrategy implements StrategyInterface {

    double wdt;
    double length;
    double doclength;

    @Override
    public double calculate_wqt(double N, double dft) {
        return log(1 + (N / dft));
    }

    @Override
    public double calculate_Ld(DiskPositionalIndex index, int docId) {
        try {
            doclength = index.getL_d(docId);
        } catch (IOException ex) {
            Logger.getLogger(DefaultStrategy.class.getName()).log(Level.SEVERE, null, ex);
        }
        return doclength;
    }

    @Override
    public double get_wdt(double t_fd, DiskPositionalIndex index, int docID) {
        wdt = 1 + log(t_fd);
        return wdt;

    }

}
