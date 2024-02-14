package bo;

import dto.AuthorDto;

import java.util.List;

public interface AuthorBo {
    public boolean saveAuthor(AuthorDto authorDto);
    public List<AuthorDto> getAuthors();
    public boolean deleteAuthor(int value);
    public AuthorDto searchAuthor(int id);
    public long[] getCounts();
}
