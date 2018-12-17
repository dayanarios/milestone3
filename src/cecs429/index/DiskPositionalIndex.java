/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cecs429.index;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class that reads a positional inverted index to disk. Included methods read
 * individual .bin files containing: docLenght A, docLength D, Ave(tftd), Ld
 * Contains a function for binary searching the vocab on disk and retrieving 
 * postings with and without positions.
 * @author bhavy
 */
public class DiskPositionalIndex implements Index {

    //String path = "C:\\Users\\bhavy\\Desktop\\SET\\TestFiles\\Index";
    File vocTable;
    File vocab;
    File postings;
    File docWeights;

    private boolean Found = false;

    public DiskPositionalIndex() {
    }

    public DiskPositionalIndex(String path) {
        this.vocTable = new File(path + "\\Index\\VocabTable.bin");
        this.vocab = new File(path + "\\Index\\vocab.bin");
        this.postings = new File(path + "\\Index\\Postings.bin");
        this.docWeights = new File(path + "\\Index\\docWeights.bin");
    }

    @Override
    public List<Posting> getPositional_posting(String term) {
        List<Posting> res = new ArrayList<>();

        try {

            long postingsposition = binarysearch(term); //returns position of term in postings.bin
            InputStream p = new FileInputStream(postings);
            //BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(postings),"UTF-8"));
            RandomAccessFile pos = new RandomAccessFile(postings, "r");
            List<Integer> docID = new ArrayList<>();
            List<Integer> positions = new ArrayList<>();
            if (Found == true) {
                //System.out.println(pos.getFilePointer());
                pos.seek(postingsposition);
                //System.out.println(pos.getFilePointer());
                int docFreq = pos.readInt();
                for (int i = 0; i < docFreq; i++) {
                    docID.add(pos.readInt());
                    int termfreq = pos.readInt();
                    for (int j = 0; j < termfreq; j++) {
                        positions.add(pos.readInt());
                    }
                    if (i == 0) {
                        Posting p1 = new Posting(docID.get(i), positions.get(0));
                        res.add(p1);
                        for (int x = 1; x < positions.size(); x++) {

                            positions.set(x, positions.get(x) + positions.get(x - 1));
                            p1.addPosition(positions.get(x));

                        }

                        positions.clear();
                    } else {
                        docID.set(i, docID.get(i) + docID.get(i - 1));
                        Posting p1 = new Posting(docID.get(i), positions.get(0));
                        res.add(p1);
                        for (int x = 1; x < positions.size(); x++) {

                            positions.set(x, positions.get(x) + positions.get(x - 1));
                            p1.addPosition(positions.get(x));

                        }

                        positions.clear();
                    }

                }

            } else {
                res.clear();
            }
            p.close();
            pos.close();

        } catch (IOException ex) {
            Logger.getLogger(DiskPositionalIndex.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }

    @Override
    public List<Posting> getPosting_noPos(String term) {
        List<Posting> results = new ArrayList<>();
        try {

            long postingsposition = binarysearch(term); //returns position of term in postings.bin
            InputStream p = new FileInputStream(postings);
            RandomAccessFile pos = new RandomAccessFile(postings, "r");
            List<Integer> docID = new ArrayList<>();
            List<Integer> positions = new ArrayList<>();

            if (Found == true) {

                pos.seek(postingsposition);

                int docFreq = pos.readInt();
                for (int i = 0; i < docFreq; i++) {
                    docID.add(pos.readInt());

                    int termfreq = pos.readInt();

                    pos.seek(pos.getFilePointer() + termfreq * 4);

                    if (i == 0) {
                        results.add(new Posting(docID.get(i), (double) docFreq, (double) termfreq));
                    } else {
                        docID.set(i, docID.get(i) + docID.get(i - 1));
                        results.add(new Posting(docID.get(i), (double) docFreq, (double) termfreq));
                    }

                }
            } else {
                results.clear();
            }
            p.close();
            pos.close();

        } catch (IOException ex) {
            Logger.getLogger(DiskPositionalIndex.class.getName()).log(Level.SEVERE, null, ex);
        }

        return results;
    }

    public double getL_d(int docId) throws FileNotFoundException, IOException {
        //System.out.println(docId); 
        FileInputStream i = new FileInputStream(docWeights);
        RandomAccessFile ram = new RandomAccessFile(docWeights, "r");
        long docid = (long) docId;
        //System.out.println(docid); 
        ram.seek((docid) * (8 * 4));
        //System.out.println(ram.readDouble());
        double L_d = ram.readDouble();
        //System.out.println(L_d); 
        i.close();
        ram.close();

        return L_d;

    }

    public double getDocLength_D(int docId) throws FileNotFoundException, IOException {
        //System.out.println(docId); 
        FileInputStream i = new FileInputStream(docWeights);
        RandomAccessFile ram = new RandomAccessFile(docWeights, "r");
        long docid = (long) docId;
        //System.out.println(docid); 
        ram.seek((docid) * (8 * 4) + 16); //16 is offset, 4 is to accomodate other items per document
        //System.out.println(ram.readDouble());
        double DocLength_D = ram.readDouble();
        //System.out.println(L_d); 
        i.close();
        ram.close();

        return DocLength_D;

    }

    public double getDocLength_A() throws FileNotFoundException, IOException {
        //System.out.println(docId); 
        FileInputStream i = new FileInputStream(docWeights);
        RandomAccessFile ram = new RandomAccessFile(docWeights, "r");
        //System.out.println(docid); 
        ram.seek(ram.length() - 8); //read last value
        //System.out.println(ram.readDouble());
        double DocLength_A = ram.readDouble();
        //System.out.println(L_d); 
        i.close();
        ram.close();

        return DocLength_A;

    }

    public double getByteSize(int docId) throws FileNotFoundException, IOException {
        //System.out.println(docId); 
        FileInputStream i = new FileInputStream(docWeights);
        RandomAccessFile ram = new RandomAccessFile(docWeights, "r");
        long docid = (long) docId;
        //System.out.println(docid); 
        ram.seek((docid) * (8 * 4) + 24); //24 is offset, 4 is to accomodate other items per document
        //System.out.println(ram.readDouble());
        double ByteSize = ram.readDouble();
        //System.out.println(L_d); 
        i.close();
        ram.close();

        return ByteSize;

    }

    public double getAve(int docId) throws FileNotFoundException, IOException {
        //System.out.println(docId); 
        FileInputStream i = new FileInputStream(docWeights);
        RandomAccessFile ram = new RandomAccessFile(docWeights, "r");
        long docid = (long) docId;
        //System.out.println(docid); 
        ram.seek((docid) * (8 * 4) + 8); //8 is offset, 4 is to accomodate other items per document
        //System.out.println(ram.readDouble());
        double ave = ram.readDouble();
        //System.out.println(L_d); 
        i.close();
        ram.close();

        return ave;

    }

    @Override
    public List<String> getVocabulary() {
        InputStream f = null;
        RandomAccessFile vt = null;
        RandomAccessFile v = null;
        List<String> results = new ArrayList();

        try {
            f = new FileInputStream(vocab);
            vt = new RandomAccessFile(vocTable, "r");
            v = new RandomAccessFile(vocab, "r");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DiskPositionalIndex.class.getName()).log(Level.SEVERE, null, ex);
        }

        String term = "";
        long current = 0;
        long next;
        boolean firstPass = true;

        while (true) {
            try {
                if (firstPass) {
                    current = vt.readLong(); //give starting position of term
                    firstPass = false;
                }

                vt.readLong(); //skips over posting list

                next = vt.readLong();

                //length of the string
                long length = next - current;
                //vocab.bin at pos m

                v.seek(current);

                term = v.readUTF();
                results.add(term);
                current = next;
            } catch (EOFException e) {
                break;
            } catch (IOException ex) {
                Logger.getLogger(DiskPositionalIndex.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        return results;
    }

    public long binarysearch(String term) throws FileNotFoundException, IOException {

        // InputStream f = new FileInputStream(vocab);
        RandomAccessFile vt = new RandomAccessFile(vocTable, "r");
        RandomAccessFile v = new RandomAccessFile(vocab, "r");
        long result = 0;

        long size = vt.length() / 16;
        long i = 0, m;
        long j = size - 1;
        m = (i + j) / 2;
        String s1 = term;
        while (i < j) {

            m = (i + j) / 2;

            String s2 = "";
            
            //go to vocabtable position m 
            vt.seek((m - 1) * 16);

            long current = vt.readLong(); //gives decimal value of hexadecimal value in vocabtable

            v.seek(current);

            s2 = v.readUTF();

            if (term.compareTo(s2) == 0) {

                //System.out.println("Found");
                Found = true;
                // vt.seek(m * 16);
                vt.seek((m - 1) * 16);
                long x = vt.readLong();
                x = vt.readLong();

                result = x;
                break;
            } else if (term.compareTo(s2) < 0) {
                j = m;
            } else {
                if ((j - i) == 1) {
                    Found = false;
                    result = 0;
                    break;
                }
                i = m;

            }

        }
        return result;
    }
    
}
