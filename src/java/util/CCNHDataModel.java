/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package util;

import java.util.List;
import javax.faces.model.ListDataModel;
import model.DOCENTES2;
import org.primefaces.model.SelectableDataModel;

/**
 *
 * @author charles
 */
public class CCNHDataModel extends ListDataModel implements SelectableDataModel<DOCENTES2> {

    public CCNHDataModel() {
    }

    public CCNHDataModel(List<DOCENTES2> data) {
        super(data);
    }

    @Override
    public DOCENTES2 getRowData(String rowKey) {
        //In a real app, a more efficient way like a query by rowKey should be implemented to deal with huge data  

        List<DOCENTES2> docentes = (List<DOCENTES2>) getWrappedData();

        for (DOCENTES2 docente : docentes) {
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

}

