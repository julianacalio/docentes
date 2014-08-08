/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package facade;

/**
 *
 * @author charles
 */
import controller.HibernateUtil;
import java.util.List;
import javax.ejb.Stateless;
import model.DOCENTES2;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author charles
 */
@Stateless
public class DocenteFacade extends AbstractFacade<DOCENTES2> {

    public DocenteFacade() {
        super(DOCENTES2.class);
    }

    @Override
    protected SessionFactory getSessionFactory() {

        return HibernateUtil.getSessionFactory();

    }
    
    // Verifica se usuário existe ou se pode logar 
    public List<DOCENTES2> findAllCentro(String centro) {


            try {
                Session session = getSessionFactory().openSession();
                Query query = session.createQuery("from DOCENTES2 d where d.centro = :centro ");
                query.setParameter("centro", centro);
                List resultado = query.list();
                session.close();
                return resultado;
                
            } catch (HibernateException e) {
                
                return null;
            }

    }
    
    
    
    
 
}
