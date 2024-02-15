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
    public List<Object[]> getCounts(Session session) {

        String hql="SELECT a,COUNT(b.id) FROM Book b JOIN b.author a GROUP BY a.id";
        Query<Object[]> query = session.createQuery(hql, Object[].class);
        List<Object[]> list =query.getResultList();
        return list;
    }

}
