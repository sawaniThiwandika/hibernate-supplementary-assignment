package dao;

import entity.Book;
import org.hibernate.Session;

import java.util.List;

public interface BookDao {
    public boolean save(Book book, Session session);
    public boolean  update();
    public List<Book> getAll(Session session);
    public List<Book> getAllAfter2010(Session session);
    public boolean increasePrice(int id, Session session);
    public double getAveragePrice(Session session);
}
