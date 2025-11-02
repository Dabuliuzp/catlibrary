package com.example.virtual_campus.ServerPages;

import com.example.virtual_campus.controller.AuthController;
import com.example.virtual_campus.controller.BookController;
import com.example.virtual_campus.controller.BookController;
import com.example.virtual_campus.controller.BookController;
import com.example.virtual_campus.model.Book;
import com.example.virtual_campus.model.Book;
import com.example.virtual_campus.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Scanner;

@RestController
public class ManageBookPage {

    @Autowired
    private BookController bookController;
    @Autowired
    private BookRepository bookRepository;

    public void work(String function, ObjectInputStream in, ObjectOutputStream out)
            throws IOException, ClassNotFoundException {
        if (function != null) {
            System.out.println("读入第二段指向:"+function);
            switch (function) {
                case "requestAllClass":
                    System.out.println("请求所有用户信息中");
                    List<Book> allbook = bookRepository.findAll();
                    out.writeObject((Integer)allbook.size());
                    for(Book book : allbook){
                        out.writeObject(book.getId());
                        out.writeObject(book.getTitle());
                        out.writeObject(book.getAuthor());
                        out.writeObject(book.getPress());
                        out.writeObject(book.getIsbn());
                        out.writeObject(book.isAvailable());
                    }
                    System.out.println("请求完成");
                    out.flush();
                    break;
                case "SelectSomeone":
                    System.out.println("正在查找对应数据中");
                    String id = (String) in.readObject();
                    String title = (String) in.readObject();
                    String author = (String) in.readObject();
                    if(Objects.equals(id,"") && Objects.equals(title,"") && Objects.equals(author,"")){
                        out.writeObject(1);//输入为空
                    }
                    else if(!Objects.equals(id,"")){
                        Long bookId = Long.parseLong(id);
                        Optional<Book> SearchbookId = bookController.SearchById(bookId);
                        if(SearchbookId.isEmpty()) out.writeObject(2);//输入有误
                        else{
                            out.writeObject(3);
                            out.writeObject(1);
                            out.writeObject(SearchbookId.get().getId());
                            out.writeObject(SearchbookId.get().getTitle());
                            out.writeObject(SearchbookId.get().getAuthor());
                            out.writeObject(SearchbookId.get().getPress());
                            out.writeObject(SearchbookId.get().getIsbn());
                            out.writeObject(SearchbookId.get().isAvailable());
                        }
                    }
                    else if(!Objects.equals(title,"")){
                        allbook = bookRepository.findAll();
                        Integer somebook = 0;
                        for(Book book : allbook){
                            if(Objects.equals(book.getTitle(),title)){
                                somebook ++;
                            }
                        }
                        if(somebook == 0){
                            out.writeObject(2);
                        }
                        else{
                            out.writeObject(3);
                            out.writeObject(somebook);
                            for(Book book : allbook){
                                if(Objects.equals(book.getTitle(),title)){
                                    out.writeObject(book.getId());
                                    out.writeObject(book.getTitle());
                                    out.writeObject(book.getAuthor());
                                    out.writeObject(book.getPress());
                                    out.writeObject(book.getIsbn());
                                    out.writeObject(book.isAvailable());
                                }
                            }
                        }
                    }
                    else if(!Objects.equals(author,"")){
                        allbook = bookRepository.findAll();
                        Integer somebook = 0;
                        for(Book book : allbook){
                            if(Objects.equals(book.getAuthor(),author)){
                                somebook ++;
                            }
                        }
                        if(somebook == 0){
                            out.writeObject(2);
                        }
                        else{
                            out.writeObject(3);
                            out.writeObject(somebook);
                            for(Book book : allbook){
                                if(Objects.equals(book.getAuthor(),author)){
                                    out.writeObject(book.getId());
                                    out.writeObject(book.getTitle());
                                    out.writeObject(book.getAuthor());
                                    out.writeObject(book.getPress());
                                    out.writeObject(book.getIsbn());
                                    out.writeObject(book.isAvailable());
                                }
                            }
                        }
                    }
                    System.out.println("查找完成");
                    out.flush();
                    break;
                case "EditBooks":
                    System.out.println("正在修改中");
                    id = (String) in.readObject();
                    title = (String) in.readObject();
                    author = (String) in.readObject();
                    String press = (String) in.readObject();
                    String isbn = (String) in.readObject();
                    Long bookId = Long.parseLong(id);
                    out.writeObject(bookController.BookEdit(bookId,title,author,press,isbn));
                    System.out.println("修改完成");
                    out.flush();
                    break;
                case "DeleteBooks":
                    System.out.println("正在删除中");
                    String DelId = (String) in.readObject();
                    if(Objects.equals(DelId,"")){
                        out.writeObject(false);
                    }
                    else{
                        Long delid = Long.parseLong(DelId);
                        out.writeObject(bookController.BookDelete(delid));
                    }
                    out.flush();
                    break;
                case "AddBooks":
                    System.out.println("正在添加中");
                    String AddId = (String) in.readObject();
                    title = (String) in.readObject();
                    author = (String) in.readObject();
                    press = (String) in.readObject();
                    isbn = (String) in.readObject();
                    if(Objects.equals(AddId,"") || Objects.equals(title,"") || Objects.equals(author,"") || Objects.equals(press,"") || Objects.equals(isbn,"")){
                        out.writeObject(false);
                    }
                    else{
                        Long addid = Long.parseLong(AddId);
                        out.writeObject(bookController.BookAdd(addid,title,author,press,isbn));
                    }
                    out.flush();
                    break;
            }
        } else {
            System.out.println("Unknown function: " + function);
        }
    }
}
