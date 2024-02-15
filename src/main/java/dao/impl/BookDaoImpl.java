package dao.impl;

import dao.BookDao;
import entity.Book;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class BookDaoImpl implements BookDao {
    @Override
    public boolean save(Book book, Session session) {
        int result = (int) session.save(book);
        return result>0;

    }

    @Override
    public boolean update() {
        return false;
    }

    @Override
    public List<Book> getAll(Session session) {
        String hql = "FROM Book";
        Query<Book> query = session.createQuery(hql, Book.class);


        return query.list();
    }

    @Override
    public List<Book> getAllAfter2010(Session session) {
        String hql = "FROM Book WHERE publicationYear>2010";
        Query<Book> query = session.createQuery(hql, Book.class);
        return query.list();

    }

    @Override
    public boolean increasePrice(int id, Session session) {
        String hql="UPDATE Book SET price = (price+price*0.1) WHERE author.id=:authorId";
        Query query = session.createQuery(hql);
        query.setParameter("authorId", id);
        return query.executeUpdate()>0;
    }

    @Override
    public double getAveragePrice(Session session) {
       String hql="SELECT AVG(price) FROM Book ";
       Query<Double> query = session.createQuery(hql, Double.class);
       return query.list().get(0);
    }

    @Override
    public List<Book> filterByCountry(Session session,String country) {
        String hql="SELECT b FROM Book b JOIN b.author a WHERE a.country=:value ";
        Query<Book> query = session.createQuery(hql, Book.class);
        query.setParameter("value", country);
        List<Book> list = query.list();


        return list ;
    }
}
