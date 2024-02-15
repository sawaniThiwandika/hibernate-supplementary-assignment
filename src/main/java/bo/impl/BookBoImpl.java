package bo.impl;

import bo.BookBo;
import config.FactoryConfiguration;
import dao.BookDao;
import dao.impl.BookDaoImpl;
import dto.BookDto;
import entity.Author;
import entity.Book;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class BookBoImpl implements BookBo {
    BookDao bookDao=new BookDaoImpl();
    @Override
    public boolean saveBook(BookDto bookDto) {

        List<Book> books=new ArrayList<>();
        Session session = FactoryConfiguration.getFactoryConfiguration().getSession();
        Transaction transaction = session.beginTransaction();
        boolean saved = bookDao.save(new Book(bookDto.getId(),new Author(bookDto.getAuthorId(),bookDto.getAuthorName(),books,bookDto.getCountry()), bookDto.getName(), bookDto.getYear(),bookDto.getPrice()), session);
        transaction.commit();
        session.close();
        return saved;


    }

    @Override
    public boolean updateBook() {
        return false;
    }

    @Override
    public List<BookDto> getAll() {
        List<BookDto> allBooks=new ArrayList<>();
        Session session = FactoryConfiguration.getFactoryConfiguration().getSession();
        Transaction transaction = session.beginTransaction();
        List<Book> all = bookDao.getAll(session);
        for (Book book:all
        ) {
            allBooks.add(new BookDto(book.getId(),book.getAuthor().getId(),book.getAuthor().getCountry(),book.getAuthor().getName(),book.getTitle(),book.getPublicationYear(),book.getPrice()));

        }
        transaction.commit();
        session.close();
        return allBooks;
    }

    @Override
    public List<BookDto> getAllAfter2010() {
        List<BookDto> allBooks=new ArrayList<>();
        Session session = FactoryConfiguration.getFactoryConfiguration().getSession();
        Transaction transaction = session.beginTransaction();
        List<Book> all = bookDao.getAllAfter2010(session);
        for (Book book:all
        ) {
            allBooks.add(new BookDto(book.getId(),book.getAuthor().getId(),book.getAuthor().getCountry(),book.getAuthor().getName(),book.getTitle(),book.getPublicationYear(),book.getPrice()));

        }
        transaction.commit();
        session.close();
        return allBooks;
    }

    @Override
    public boolean increasePrice(int id) {
        Session session = FactoryConfiguration.getFactoryConfiguration().getSession();
        Transaction transaction = session.beginTransaction();
        boolean increased = bookDao.increasePrice(id, session);
        transaction.commit();
        session.close();
        return increased;

    }

    @Override
    public double getAveragePrice() {
        Session session = FactoryConfiguration.getFactoryConfiguration().getSession();
        Transaction transaction = session.beginTransaction();
        double averagePrice = bookDao.getAveragePrice(session);
        transaction.commit();
        session.close();
        return averagePrice;

    }

    @Override
    public List<BookDto> filterByCountry(String country) {
        Session session = FactoryConfiguration.getFactoryConfiguration().getSession();
        Transaction transaction = session.beginTransaction();
        List<Book> books = bookDao.filterByCountry(session, country);
        List<BookDto> dtos=new ArrayList<>();
        for (Book book:books
             ) {
            dtos.add(new BookDto(book.getId(),book.getAuthor().getId(),book.getAuthor().getName(),book.getAuthor().getCountry(),book.getTitle(),book.getPublicationYear(),book.getPrice()));
        }
        return dtos;
    }
}
