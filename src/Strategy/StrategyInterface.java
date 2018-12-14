/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Strategy;

import cecs429.index.DiskPositionalIndex;
import java.util.HashMap;

/**
 * Interface for variant tf-idf formulas
 *
 * @author bhavy
 */
public interface StrategyInterface {

    public double calculate_wqt(double N, double dft);

    public double calculate_Ld(DiskPositionalIndex index, int docId);

    public double get_wdt(double t_fd, DiskPositionalIndex index, int docID);
}
