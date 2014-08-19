package controller;

import facade.DocenteFacade;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.annotation.PostConstruct;
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
import util.CCNHLazyModel;
import util.CECSDataModel;
import util.CECSLazyModel;
import util.CMCCDataModel;
import util.CMCCLazyModel;
import util.DocenteDataModel;

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
    private CECSLazyModel cecsLazyModel;
    private CCNHLazyModel ccnhLazyModel;
    private CMCCLazyModel cmccLazyModel;

    public void setDocente(DOCENTES2 docente) {
        this.docente = docente;
    }

    private DOCENTES2 getDocente(Long key) {
        return this.buscar(key);

    }

    public DOCENTES2 getDocente() {
        if (docente == null) {
            docente = new DOCENTES2();
        }
        return docente;
    }

    //---------------------------Data Model--------------------------------------------------------------------
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

    public void recriarModelo() {
        this.docenteDataModel = null;
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

    //---------------------------------------Páginas web------------------------------------------------------------
    public String prepareCreate(int i) {
        docente = new DOCENTES2();
        if (i == 1) {
            return "/view/docente/Create";
        } else {
            return "Create";
        }
    }

    public String index() {
        docente = null;
        docenteDataModel = null;
        return "/index";
    }

    public String prepareEdit() {
        docente = (DOCENTES2) docenteDataModel.getRowData();
        return "Edit";
    }

    public String prepareView() {
        docente = (DOCENTES2) docenteDataModel.getRowData();
        //docente = docenteFacade.find(docente.getID());
        //docenteFacade.edit(docenteFacade.find(docente.getID()));
        //docenteFacade.edit(docente);
        return "View";
    }
    
    //---------------------------LazyData Model--------------------------------------------------------------------
    
    @PostConstruct
    public void init() {
        cmccLazyModel = new CMCCLazyModel(this.listarTodosCentro("CMCC"));
        ccnhLazyModel = new CCNHLazyModel(this.listarTodosCentro("CCNH"));
        cecsLazyModel = new CECSLazyModel(this.listarTodosCentro("CECS"));
    }
    
    public CMCCLazyModel getCmccLazyModel() {
        return this.cmccLazyModel;
    }
    
    public CECSLazyModel getCecsLazyModel() {
        return this.cecsLazyModel;
    }
    
    public CCNHLazyModel getCcnhLazyModel() {
        return this.ccnhLazyModel;
    }
    

    //---------------------------------------------------CRUD-------------------------------------------------------
    private List<DOCENTES2> listarTodas() {
        return docenteFacade.findAll();

    }

    private List<DOCENTES2> listarTodosCentro(String centro) {
        return docenteFacade.findAllCentro(centro);
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

    public void editar() {
        try {
            docenteFacade.edit(docente);
            JsfUtil.addSuccessMessage("Docente Editado com sucesso!");
            docente = null;
        } catch (Exception e) {
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
    
    public SelectItem[] getItemsAvaiableSelectOne() {
        return JsfUtil.getSelectItems(docenteFacade.findAll(), true);
    }

    //--------------------------------------------------------------------------------------------------------------
    

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
                return getStringKey(d.getId().setScale(0, BigDecimal.ROUND_HALF_UP).longValue());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + DOCENTES2.class.getName());
            }
        }
    }

}
