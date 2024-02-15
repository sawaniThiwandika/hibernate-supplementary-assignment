package entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne Author author;
    @JoinColumn (name = "author_id")// SPECIFY THE FOREIGN KEY OF Book ENTITY(In this, the foreign key column name of book entity is author_id)
    private String title;
    private int publicationYear;
    private double price;
}
