/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package util;

import java.util.Comparator;
import model.DOCENTES2;
import org.primefaces.model.SortOrder;

/**
 *
 * @author charles
 */
public class LazySorter implements Comparator<DOCENTES2> {
 
    private String sortField;
     
    private SortOrder sortOrder;
     
    public LazySorter(String sortField, SortOrder sortOrder) {
        this.sortField = sortField;
        this.sortOrder = sortOrder;
    }
 
    public int compare(DOCENTES2 docente1, DOCENTES2 docente2) {
        try {
            Object value1 = DOCENTES2.class.getField(this.sortField).get(docente1);
            Object value2 = DOCENTES2.class.getField(this.sortField).get(docente2);
 
            int value = ((Comparable)value1).compareTo(value2);
             
            return SortOrder.ASCENDING.equals(sortOrder) ? value : -1 * value;
        }
        catch(Exception e) {
            throw new RuntimeException();
        }
    }

    
}