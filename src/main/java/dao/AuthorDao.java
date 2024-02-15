package dao;

import dto.AuthorDto;
import entity.Author;
import org.hibernate.Session;

import java.util.List;

public interface AuthorDao {
    public boolean save(Author author, Session session);
    public List<Author> getAll(Session session);
    public boolean  delete(int value, Session session);
    public Author search(int id, Session session);
    public List<Object[]> getCounts(Session session);
    public  List<Author> getAuthorsMoreThanAverageBooks(Session session);
}
