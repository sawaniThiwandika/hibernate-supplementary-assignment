package controller;

import bo.AuthorBo;
import bo.BookBo;
import bo.impl.AuthorBoImpl;
import bo.impl.BookBoImpl;
import com.jfoenix.controls.JFXComboBox;
import dto.AuthorDto;
import dto.BookDto;
import dto.tm.AuthorTm;
import dto.tm.BookTm;
import entity.Author;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MainFormController {

    @FXML
    private TableColumn<?, ?> colAuthorId;

    @FXML
    private TableColumn<?, ?> colAuthorName;

    @FXML
    private TableColumn<?, ?> colBookId;

    @FXML
    private TableColumn<?, ?> colBookName;

    @FXML
    private TableColumn<?, ?> colBookPrice;

    @FXML
    private TableColumn<?, ?> colBookYear;

    @FXML
    private TableColumn<?, ?> colCountry;

    @FXML
    private JFXComboBox<String> comboAuthors;

    @FXML
    private JFXComboBox<String> comboBooks;

    @FXML
    private Button countBtn;

    @FXML
    private Button deleteBookBtn;

    @FXML
    private Button increase;

    @FXML
    private Label labelCount;

    @FXML
    private Label labelPrice;

    @FXML
    private Button saveBookBtn;
    @FXML
    private Button saveAuthorBtn;
    @FXML
    private Button deleteAuthorBtn;

    @FXML
    private TableView<AuthorTm> tableAuthor;

    @FXML
    private TableView<BookTm> tableBooks;
    @FXML
    private TextField txtAuthorCountry;

    @FXML
    private TextField txtAuthorName;

    @FXML
    private TextField txtBookName;

    @FXML
    private TextField txtBookPrice;

    @FXML
    private TextField txtBookYear;
    @FXML
    private Button after2010Btn;
    @FXML
    private TableColumn<?, ?> colCount;

    @FXML
    private JFXComboBox<String> comboCountry;
    @FXML
    private Button searchByCountryBtn;


    @FXML
    private Button viewAuthorsBtn;
    BookBo bookBo=new BookBoImpl();
    ObservableList<AuthorTm> obListAuthorTableValues=  FXCollections.observableArrayList();
    ObservableList<BookTm> obListBookTableValues=  FXCollections.observableArrayList();
     AuthorBo authorBo=new AuthorBoImpl();

    public void initialize() throws SQLException {

        colCount.setVisible(false);
        setCellValueFactory();
        setComboBoxValues();
        setTableValues();
        double averagePrice = bookBo.getAveragePrice();
        labelPrice.setText(String.valueOf(averagePrice));
        printAuthorsWhoWriteBooksThanAverageNumber();

    }

    private void printAuthorsWhoWriteBooksThanAverageNumber() {
        List<AuthorDto> authorsMoreThanAverageBooks = authorBo.getAuthorsMoreThanAverageBooks();
        System.out.println(authorsMoreThanAverageBooks.size());
        for (AuthorDto author:authorsMoreThanAverageBooks) {
            System.out.println("author "+author.getName());
        }
    }

    private void setTableValues() {
        List<BookDto> bookDtos = loadBooks();
        for (BookDto dto : bookDtos) {
            obListBookTableValues.add(new BookTm(dto.getId(),dto.getName(),dto.getYear(),dto.getPrice()));
        }
       tableBooks.setItems(obListBookTableValues);
        List<AuthorDto> authorDtos = loadAuthors();


        for (AuthorDto dto : authorDtos) {
            obListAuthorTableValues.add(new AuthorTm(dto.getId(),dto.getName(),dto.getCountry(),0));
        }
        tableAuthor.setItems(obListAuthorTableValues);
    }

    private void setComboBoxValues() {
        List<AuthorDto> authorDtos = loadAuthors();
        ObservableList<String> obList = FXCollections.observableArrayList();


            for (AuthorDto dto : authorDtos) {
                obList.add(String.valueOf(dto.getId()));
                 }
           comboAuthors.setItems(obList);

        List<BookDto> bookDtos = loadBooks();
        ObservableList<String> obListBooks = FXCollections.observableArrayList();


        for (BookDto dto :bookDtos) {
            obListBooks.add(String.valueOf(dto.getId()));
        }
        comboBooks.setItems(obListBooks);
        ObservableList<String> obListCountries = FXCollections.observableArrayList();
        ArrayList<String> countries = new ArrayList<>();
        for (AuthorDto dto : authorDtos) {
            if (countries.size()==0){
                obListCountries.add(String.valueOf(dto.getCountry()));
                countries.add(dto.getCountry());
            }
            else {
                for (int i = 0; i < countries.size(); i++) {
                    if (!dto.getCountry().equals(countries.get(i))) {
                        obListCountries.add(String.valueOf(dto.getCountry()));
                        countries.add(dto.getCountry());
                    }
                }
            }


        }
        comboCountry.setItems(obListCountries);




    }
    @FXML
    void comboAuthorOnAction(ActionEvent event) {
        String value = comboAuthors.getValue();
        AuthorDto authorDto = authorBo.searchAuthor(Integer.parseInt(value));
        txtAuthorName.setText(authorDto.getName());
        txtAuthorCountry.setText(authorDto.getCountry());
    }

    private void setCellValueFactory() {
       colCount.setCellValueFactory(new PropertyValueFactory<>("count"));
        colAuthorId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colAuthorName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colCountry.setCellValueFactory(new PropertyValueFactory<>("country"));
        colBookId.setCellValueFactory(new PropertyValueFactory<>("id"));
       colBookName.setCellValueFactory(new PropertyValueFactory<>("name"));
       colBookYear.setCellValueFactory(new PropertyValueFactory<>("year"));
       colBookPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    private List<BookDto> loadBooks() {
        List<BookDto> all = bookBo.getAll();
        return all;


    }

    private List<AuthorDto> loadAuthors() {
        List<AuthorDto> authors = authorBo.getAuthors();
        return authors;

    }

    @FXML
    void countButtonOnAction(ActionEvent event) {
        colCount.setVisible(true);
        List<Object[]> authorsWithCounts= authorBo.getCounts();
        obListAuthorTableValues.clear();
        List<AuthorDto> authorDtos = loadAuthors();

        for (int i=0;i<authorsWithCounts.size();i++) {
            Author author= (Author) authorsWithCounts.get(i)[0];
            long count= (long) authorsWithCounts.get(i)[1];
            obListAuthorTableValues.add(new AuthorTm(author.getId(),author.getName(),author.getCountry(),count));
        }
        tableAuthor.setItems(obListAuthorTableValues);
        tableAuthor.refresh();


    }

    @FXML
    void deleteAuthorBtnOnAction(ActionEvent event) {
        int value = Integer.parseInt(comboAuthors.getValue());
        authorBo.deleteAuthor(value);
    }


    @FXML
    void increaseButtonOnAction(ActionEvent event) {
        String value = comboAuthors.getValue();
        boolean increased = bookBo.increasePrice(Integer.parseInt(value));
        if (increased){
            obListBookTableValues.clear();
            tableBooks.refresh();
            new Alert(Alert.AlertType.CONFIRMATION,"success").show();

            setTableValues();
        }
        else {
            new Alert(Alert.AlertType.ERROR,"not saved").show();
        }


    }



    @FXML
    void viewAuthorsBtnOnAction(ActionEvent event) {

    }

    public void saveAuthorBtnOnAction(ActionEvent actionEvent) {
        String authorNameText = txtAuthorName.getText();
        String countryText = txtAuthorCountry.getText();
        boolean saved = authorBo.saveAuthor(new AuthorDto(0, authorNameText,countryText));
        if (saved){

            new Alert(Alert.AlertType.CONFIRMATION,"success").show();
           tableAuthor.refresh();
        }
        else {
            new Alert(Alert.AlertType.ERROR,"not saved").show();
        }

    }

    public void saveBookBtnOnAction(ActionEvent actionEvent) {
        String bookNameText = txtBookName.getText();
        int  authorId = Integer.parseInt(comboAuthors.getValue());
        String authorNameText = txtAuthorName.getText();
        String countryText = txtAuthorCountry.getText();
        int yearText = Integer.parseInt(txtBookYear.getText());
        double priceText = Double.parseDouble(txtBookPrice.getText());
        boolean saved = bookBo.saveBook(new BookDto(0, authorId, authorNameText, countryText, bookNameText, yearText, priceText));
        if (saved){

            new Alert(Alert.AlertType.CONFIRMATION,"success").show();

            setTableValues();
        }
        else {
            new Alert(Alert.AlertType.ERROR,"not saved").show();
        }

    }

    public void after2010BtnOnAction(ActionEvent actionEvent) {
        List<BookDto> allAfter2010 = bookBo.getAllAfter2010();
        obListBookTableValues.clear();
        for (BookDto dto : allAfter2010) {
            obListBookTableValues.add(new BookTm(dto.getId(),dto.getName(),dto.getYear(),dto.getPrice()));
        }
        tableBooks.setItems(obListBookTableValues);
    }

    public void searchByCountryButtonOnAction(ActionEvent actionEvent) {
        String countryValue = comboCountry.getValue();
        List<BookDto> bookDtos = bookBo.filterByCountry(countryValue);
        System.out.println("size"+bookDtos.size());
        obListBookTableValues.clear();
        for (BookDto dto : bookDtos) {
            obListBookTableValues.add(new BookTm(dto.getId(),dto.getName(),dto.getYear(),dto.getPrice()));
        }
        tableBooks.setItems(obListBookTableValues);

    }

    public void ComboBookOnAction(ActionEvent actionEvent) {

    }
}
