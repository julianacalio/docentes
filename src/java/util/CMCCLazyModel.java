/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import model.DOCENTES2;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 *
 * @author charles
 */
public class CMCCLazyModel extends LazyDataModel<DOCENTES2> {

    private List<DOCENTES2> datasource;

    public CMCCLazyModel(List<DOCENTES2> datasource) {
        this.datasource = datasource;
    }

    @Override
    public DOCENTES2 getRowData(String rowKey) {
        for (DOCENTES2 docente : datasource) {
            if (docente.getId().equals(rowKey)) {
                return docente;
            }
        }

        return null;
    }

    @Override
    public Object getRowKey(DOCENTES2 docente) {
        return docente.getId();
    }

    @Override
    public List<DOCENTES2> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters) {
        List<DOCENTES2> data = new ArrayList<DOCENTES2>();

        //filter
        for (DOCENTES2 docente : datasource) {
            boolean match = true;

            if (filters != null) {
                for (Iterator<String> it = filters.keySet().iterator(); it.hasNext();) {
                    try {
                        String filterProperty = it.next();
                        Object filterValue = filters.get(filterProperty);
                        String fieldValue = String.valueOf(docente.getClass().getField(filterProperty).get(docente));

                        if (filterValue == null || fieldValue.startsWith(filterValue.toString())) {
                            match = true;
                        } else {
                            match = false;
                            break;
                        }
                    } catch (Exception e) {
                        match = false;
                    }
                }
            }

            if (match) {
                data.add(docente);
            }
        }

        //sort
        if (sortField != null) {
            Collections.sort(data, new LazySorter(sortField, sortOrder));
        }

        //rowCount
        int dataSize = data.size();
        this.setRowCount(dataSize);

        //paginate
        if (dataSize > pageSize) {
            try {
                return data.subList(first, first + pageSize);
            } catch (IndexOutOfBoundsException e) {
                return data.subList(first, first + (dataSize % pageSize));
            }
        } else {
            return data;
        }
    }
}