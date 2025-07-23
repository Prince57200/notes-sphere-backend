package dao;
import java.time.LocalDateTime;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import model.Notes;
import util.HibernateUtil;

public class NotesDAO {
	
	public void saveNotes(Notes nt) {
		
		 Transaction tx = null;
			
			try(Session session =HibernateUtil.getSessionFactory().openSession();)
			{
				tx = session.beginTransaction();
				session.save(nt);
				tx.commit();	
			}
			catch (Exception e) {
			    if (tx != null) tx.rollback();
			    e.printStackTrace();
			}
	}
	
	public List<Notes> getAllNotes() {
		
		 Transaction tx = null;
		 List<Notes> notesList = null;
			
			try(Session session = HibernateUtil.getSessionFactory().openSession();)
			{
				tx = session.beginTransaction();
				notesList = session.createQuery("from Notes", Notes.class).list();
				tx.commit();	
			}
			catch (Exception e) {
			    if (tx != null) tx.rollback();
			    e.printStackTrace();
			}
			return notesList; 
	   }
	public boolean DeleteNotes(String ID) {
		    Transaction tx=null;
		    Session session = null;

		    try {
		        session = HibernateUtil.getSessionFactory().openSession();
		        tx = session.beginTransaction();
		        int nt = Integer.parseInt(ID);
		        
		        String hql = "DELETE FROM Notes WHERE id = :id";
		        
		        int rows = session.createQuery(hql)
		                         .setParameter("id",nt)
		                         .executeUpdate(); // ✅ Session still open
		        
		        tx.commit(); // ✅ Commits while session is open
		        return rows > 0;
		    } catch (Exception e) {
		        if (tx != null) tx.rollback();
		        throw e;
		    } finally {
		        if (session != null) session.close(); // ✅ Close AFTER commit
		    }
	}
	
	public boolean updateNotes(String id, String title, String description) {
	    Transaction tx = null;
	    Session session = null;

	    try {
	        session = HibernateUtil.getSessionFactory().openSession();
	        tx = session.beginTransaction();

	        int noteId = Integer.parseInt(id);

	        String hql = "UPDATE Notes SET tittle = :title, description = :description, updated_at=:updatedAt WHERE id = :id";
	        Query<?> query = session.createQuery(hql);
	        query.setParameter("title", title);
	        query.setParameter("description", description);
	        query.setParameter("id", noteId);
	        LocalDateTime updatedAt = LocalDateTime.now();
	        query.setParameter("updatedAt", updatedAt);

	        int rows = query.executeUpdate();
	        tx.commit();

	        return rows > 0;

	    } catch (Exception e) {
	        if (tx != null) tx.rollback();
	        e.printStackTrace();
	        return false;
	    } finally {
	        if (session != null) session.close();
	    }
	}


}


