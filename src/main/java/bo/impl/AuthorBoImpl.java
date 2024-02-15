package bo.impl;

import bo.AuthorBo;
import config.FactoryConfiguration;
import dao.AuthorDao;
import dao.BookDao;
import dao.impl.AuthorDaoImpl;
import dao.impl.BookDaoImpl;
import dto.AuthorDto;
import entity.Author;
import entity.Book;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class AuthorBoImpl implements AuthorBo {
    BookDao bookDao=new BookDaoImpl();
    AuthorDao authorDao=new AuthorDaoImpl();
    @Override
    public boolean saveAuthor(AuthorDto authorDto) {
        List<Book> books=new ArrayList<>();
        Session session = FactoryConfiguration.getFactoryConfiguration().getSession();
        Transaction transaction = session.beginTransaction();
        boolean saved = authorDao.save(new Author(authorDto.getId(), authorDto.getName(), books,authorDto.getCountry()), session);
        transaction.commit();
        session.close();
        return saved;



    }

    @Override
    public List<AuthorDto> getAuthors() {
        List<AuthorDto> allAuthors=new ArrayList<>();
        Session session = FactoryConfiguration.getFactoryConfiguration().getSession();
        Transaction transaction = session.beginTransaction();
        List<Author> all = authorDao.getAll(session);
        for (Author author:all
             ) {
            allAuthors.add(new AuthorDto(author.getId(),author.getName(),author.getCountry()));

        }
        return allAuthors;
    }

    @Override
    public boolean deleteAuthor(int value) {
        Session session = FactoryConfiguration.getFactoryConfiguration().getSession();
        Transaction transaction = session.beginTransaction();
        boolean deleted = authorDao.delete(value,session);
        transaction.commit();
        session.close();
        return deleted;
    }

    @Override
    public AuthorDto searchAuthor(int id) {
        Session session = FactoryConfiguration.getFactoryConfiguration().getSession();
        Transaction transaction = session.beginTransaction();
        Author author = authorDao.search(id,session);
        transaction.commit();
        session.close();
        return new AuthorDto(author.getId(),author.getName(),author.getCountry());

    }

    @Override
    public List<Object[]> getCounts() {
        Session session = FactoryConfiguration.getFactoryConfiguration().getSession();
        Transaction transaction = session.beginTransaction();
        List<Object[]> counts = authorDao.getCounts(session);
        transaction.commit();
        session.close();
        return counts;
    }
}
