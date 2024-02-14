package dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {
    private int id;
    private  int authorId;
    private String authorName;
    private String country;
    private String name;
    private int year;
    private double price;
}
