package bo;

import dto.BookDto;
import entity.Book;
import org.hibernate.Session;

import java.util.List;

public interface BookBo {
    public  boolean saveBook(BookDto bookDto);
    public boolean updateBook();
    public List<BookDto> getAll();
    public List<BookDto> getAllAfter2010();
    public boolean increasePrice(int id);
    public double getAveragePrice();
    public List<BookDto>  filterByCountry( String country);
}
