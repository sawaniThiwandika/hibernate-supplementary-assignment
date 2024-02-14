package dao.impl;

import dao.AuthorDao;
import entity.Author;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class AuthorDaoImpl implements AuthorDao {
    @Override
    public boolean save(Author author, Session session) {
        int result = (int) session.save(author);
        return result>0;
    }

    @Override
    public List<Author> getAll(Session session) {

        String hql = "FROM Author";
        Query<Author> query = session.createQuery(hql, Author.class);


        return query.list();
    }

    @Override
    public boolean delete(int value, Session session) {
        Author authorToDelete = session.get(Author.class,value);
        session.delete(authorToDelete);
        return true;


    }

    @Override
    public Author search(int id, Session session) {
        String hql = "FROM Author WHERE id=:authorId ";
        Query<Author> query = session.createQuery(hql, Author.class);
        query.setParameter("authorId", id);
        return query.uniqueResult();
    }

    @Override
    public long[] getCounts(Session session) {
        String hql = "SELECT count(id) FROM Book GROUP BY author.id ";
        Query<Long> query = session.createQuery(hql, Long.class);
        List<Long> list = query.list();

        long [] counts= new long [list.size()];
     for (int i=0;i<list.size();i++){

         counts[i]= list.get(i);
     }


        return counts;
    }
}
