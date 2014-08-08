/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import facade.DocenteFacade;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import model.DOCENTES2;
import util.CCNHDataModel;
import util.CECSDataModel;
import util.CMCCDataModel;
import util.DocenteDataModel;

/**
 *
 * @author charles
 */
@Named(value = "docenteController")
@SessionScoped
public class DocenteController implements Serializable {
    
    public DocenteController() {
        docente = new DOCENTES2();
    }
    
    //Guarda o docente atual
    private DOCENTES2 docente;
    
    @EJB
    private DocenteFacade docenteFacade;
    private DocenteDataModel docenteDataModel;
    private CMCCDataModel cmccDataModel;
    private CCNHDataModel ccnhDataModel;
    private CECSDataModel cecsDataModel;
    
    public void setDocente(DOCENTES2 docente) {
        this.docente = docente;
    }
    
    private DOCENTES2 getDocente(Long key) {
        return this.buscar(key);
        
    }
 
    public DocenteDataModel getDocenteDataModel() {
        if (docenteDataModel == null) {
            List<DOCENTES2> docentes = this.listarTodas();
            docenteDataModel = new DocenteDataModel(docentes);
        }
        return docenteDataModel;
    }
    
    public void setDocenteDataModel(DocenteDataModel docenteDataModel) {
        this.docenteDataModel = docenteDataModel;
    }
    
    private List<DOCENTES2> listarTodas() {
        return docenteFacade.findAll();
        
    }
    
    private List<DOCENTES2> listarTodosCentro(String centro){
        return docenteFacade.findAllCentro(centro);
    }
    
    public CMCCDataModel getCmccDataModel() {
        if (cmccDataModel == null) {
            List<DOCENTES2> docentes = this.listarTodosCentro("CMCC");
            cmccDataModel = new CMCCDataModel(docentes);
        }
        return cmccDataModel;
    }
    
    public void setCMCCDataModel(CMCCDataModel cmccDataModel) {
        this.cmccDataModel = cmccDataModel;
    }
    
    public CCNHDataModel getCcnhDataModel() {
        if (ccnhDataModel == null) {
            List<DOCENTES2> docentes = this.listarTodosCentro("CCNH");
            ccnhDataModel = new CCNHDataModel(docentes);
        }
        return ccnhDataModel;
    }
    
    public void setCCNHDataModel(CCNHDataModel ccnhDataModel) {
        this.ccnhDataModel = ccnhDataModel;
    }
    
    public CECSDataModel getCecsDataModel() {
        if (cecsDataModel == null) {
            List<DOCENTES2> docentes = this.listarTodosCentro("CECS");
            cecsDataModel = new CECSDataModel(docentes);
        }
        return cecsDataModel;
    }
    
    public void setCECSDataModel(CECSDataModel cecsDataModel) {
        this.cecsDataModel = cecsDataModel;
    }
    

    
    public void recriarModelo() {
        this.docenteDataModel = null;
    }
    
    
    public SelectItem[] getItemsAvaiableSelectOne(){
        return JsfUtil.getSelectItems(docenteFacade.findAll(), true);
    }
    
    public DOCENTES2 getDocente() {
        if (docente == null) {
            docente = new DOCENTES2();
        }
        return docente;
    }
    
    public String prepareCreate(int i) {
        docente = new DOCENTES2();
        if(i == 1){
            return "/view/docente/Create";
        }
        else{
        return "Create";
        }
    }
    
    public void salvarNoBanco() {

        try {
            docenteFacade.save(docente);
            JsfUtil.addSuccessMessage("Docente " + docente.getNome() + " criado com sucesso!");
            docente = null;
            recriarModelo();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, "Ocorreu um erro de persistência");
            
        }
        
    }
    
    public DOCENTES2 buscar(Long id) {
        
        return docenteFacade.find(id);
    }
    
    public String index() {
        docente = null;
        docenteDataModel = null;
        return "/index";
    }
    
    
    public void editar() {
        try{
        docenteFacade.edit(docente);
        JsfUtil.addSuccessMessage("Docente Editado com sucesso!");
        docente = null;
        }
        catch (Exception e){
            JsfUtil.addErrorMessage(e, "Ocorreu um erro de persistência, não foi possível editar o docente: " + e.getMessage());
            
        }
    }
    
    
    public void delete() {
        docente = (DOCENTES2) docenteDataModel.getRowData();
        try {
            docenteFacade.remove(docente);
            docente = null;
            JsfUtil.addSuccessMessage("Docente Deletado");
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, "Ocorreu um erro de persistência");
        }
        
        recriarModelo();
    }
    
    public String prepareEdit() {
        docente = (DOCENTES2) docenteDataModel.getRowData();
        return "Edit";
    }
    
    public String prepareView(){
        docente = (DOCENTES2) docenteDataModel.getRowData();
        //docente = docenteFacade.find(docente.getID());
        //docenteFacade.edit(docenteFacade.find(docente.getID()));
        //docenteFacade.edit(docente);
        return "View";
    }
    
    
  

    @FacesConverter(forClass = DOCENTES2.class)
    public static class DocenteControllerConverter implements Converter {
        
        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            DocenteController controller = (DocenteController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "docenteController");
            return controller.getDocente(getKey(value));
        }

        java.lang.Long getKey(String value) {
            java.lang.Long key;
            key = Long.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Long value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof DOCENTES2) {
                DOCENTES2 d = (DOCENTES2) object;
                return getStringKey(d.getId().setScale( 0, BigDecimal.ROUND_HALF_UP ).longValue());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + DOCENTES2.class.getName());
            }
        }
    }
    
}
