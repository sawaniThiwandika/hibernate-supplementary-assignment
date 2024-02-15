Library Management System using Hibernate

This library management system is developed with Hibernate and features two main entities( Author and Book)
The system supports various operations, including retrieving books published after a specified year, updating book prices, deleting authors with cascading book deletion
And also, calculating the average book price, obtaining authors with their book counts, and identifying authors who have exceeded the average number of books.

![library management system](https://github.com/sawaniThiwandika/hibernate-supplementary-assignment/assets/139766429/50550edf-3d55-47a9-b919-3d884303991b)

HQL Queries
1. Retrieve Books Published After 2010
   
       String hql = "FROM Book WHERE publicationYear>2010"
   
2.Update Book Prices for a Specific Author

    String hql="UPDATE Book SET price = (price+price*0.1) WHERE author.id=:authorId";
   
3.Delete an Author with Cascade Deletion

    @OneToMany(mappedBy = "author",cascade = CascadeType.ALL)
    
4.Average Price of All Books

    String hql="SELECT AVG(price) FROM Book ";
   
5.Authors with Book Counts

    String hql="SELECT a,COUNT(b.id) FROM Book b JOIN b.author a GROUP BY a.id";
6.Books Written by Authors from a Specific Country

    String hql="SELECT b FROM Book b JOIN b.author a WHERE a.country=:value ";

7. Author entity
   
       import jakarta.persistence.*;
   
       import lombok.AllArgsConstructor;
   
       import lombok.Getter;
       import lombok.NoArgsConstructor;
       import lombok.Setter;

       import java.util.List;

       @Entity
       @AllArgsConstructor
       @NoArgsConstructor
       @Getter
       @Setter
       public class Author {
       @Id
       @GeneratedValue(strategy = GenerationType.IDENTITY)
       private int id;
       String name;
       @OneToMany(mappedBy = "author",cascade = CascadeType.ALL)
       private List<Book> books;
       String country;

        }
   @OneToMany- This annotation is used to declare a one-to-many relationship between the Author entity and the Book entity.
   
   mappedBy = "author"- This attribute specifies the field in the Book entity that owns the relationship.
   
   cascade = CascadeType.ALL-  This attribute specifies that all operations (delete, update) should be cascaded from the Author entity to the associated Book entities.

 Book Entity
 
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
    
  @ManyToOne - This annotation in the Book entity represents the many-to-one side of the relationship with the Author entity.
  
  @JoinColumn-SPECIFY THE FOREIGN KEY OF Book ENTITY(In this, the foreign key column name of book entity is author_id)
  
  @GeneratedValue(strategy = GenerationType.IDENTITY)- This annotation use to auto-generate unique primary key values for entities.

  

10.Authors with More Than the Average Number of Books

    String hql="SELECT a FROM Author a JOIN a.books b GROUP BY a.id  HAVING COUNT(b.id) > (SELECT AVG(bookCount) FROM (SELECT COUNT(b2.id) AS bookCount FROM Author a2 JOIN a2.books b2 GROUP BY a2) AS subquery)";
    
 
  
