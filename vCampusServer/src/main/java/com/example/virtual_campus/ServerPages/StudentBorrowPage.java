package com.example.virtual_campus.ServerPages;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.example.virtual_campus.model.Book;
import com.example.virtual_campus.model.User;
import com.example.virtual_campus.repository.BookRepository;
import com.example.virtual_campus.repository.BorrowRepository;
import com.example.virtual_campus.repository.UserRepository;
import com.example.virtual_campus.service.CourseService;
import com.example.virtual_campus.model.Borrow;
import com.example.virtual_campus.service.BorrowService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudentBorrowPage {

    @Autowired
    private BorrowService borrowService;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BorrowRepository borrowRepository;
    @Autowired
    private UserRepository userRepository;

    public void work(String function, ObjectInputStream in, ObjectOutputStream out)
            throws IOException, ClassNotFoundException {
        if (function != null) {
            System.out.println("读入第二段指向:"+function);
            switch (function) {
                case "returnMyBorrow":
                    System.out.println("还书中");
                    Long userid = (Long) in.readObject();
                    String id = (String) in.readObject();
                    if (Objects.equals(id,"")) {
                        out.writeObject(false);
                        System.out.println("还书失败0");
                    }
                    else{
                        Long Id = Long.parseLong(id);
                        User user = userRepository.findById(userid).get();
                        Book book = bookRepository.findById(Id).get();
                        List<Borrow> allborrow = borrowRepository.findAll();
                        Boolean flag=false;
                        for(Borrow borrow : allborrow){
                            if(flag==false && borrow.getReturnDate() == null && Objects.equals(borrow.getBook(),book)){
                                System.out.println(book.getTitle()+":"+borrow.getUser().getName());
                                if(!Objects.equals(borrow.getUser(),user)){
                                    out.writeObject(false);//不是你借的书
                                    System.out.println("还书失败1");
                                }
                                else{
                                    borrowService.returnBook(borrow.getId());
                                    out.writeObject(true);//还书成功
                                    System.out.println("还书成功");
                                }
                                flag = true ;
                            }
                        }
                        if(flag==false) {
                            out.writeObject(false);
                            System.out.println("还书失败2");
                        }
                    }
                    out.flush();
                    break;
                case "SelectSomeone":
                    System.out.println("查找中");
                    userid = (Long) in.readObject();
                    User user = userRepository.findById(userid).get();
                    id = (String) in.readObject();
                    String title = (String) in.readObject();
                    String author = (String) in.readObject();
                    if(Objects.equals(id,"") && Objects.equals(title,"") && Objects.equals(author,"")){
                        out.writeObject(1);//输入为空
                    }
                    else if(!Objects.equals(id,"")){
                        Long Id = Long.parseLong(id);
                        List<Borrow> allborrow = borrowRepository.findAll();
                        Integer someborrow = 0;
                        for(Borrow borrow : allborrow){
                            if(Objects.equals(borrow.getUser(),user) && Objects.equals(borrow.getReturnDate(),null) && Objects.equals(borrow.getBook().getId(),Id)){
                                someborrow ++;
                            }
                        }
                        if(someborrow == 0){
                            out.writeObject(2);
                        }
                        else{
                            out.writeObject(3);
                            out.writeObject(someborrow);
                            for(Borrow borrow : allborrow){
                                if(Objects.equals(borrow.getUser(),user) && Objects.equals(borrow.getReturnDate(),null) && Objects.equals(borrow.getBook().getId(),Id)){
                                    out.writeObject(borrow.getBook().getId());
                                    out.writeObject(borrow.getBook().getTitle());
                                    out.writeObject(borrow.getBook().getAuthor());
                                    out.writeObject(borrow.getBook().getPress());
                                    out.writeObject(borrow.getBook().getIsbn());
                                }
                            }
                        }
                    }
                    else if(!Objects.equals(title,"")){
                        List<Borrow> allborrow = borrowRepository.findAll();
                        Integer someborrow = 0;
                        for(Borrow borrow : allborrow){
                            if(Objects.equals(borrow.getUser(),user) && Objects.equals(borrow.getReturnDate(),null) && Objects.equals(borrow.getBook().getTitle(),title)){
                                someborrow ++;
                            }
                        }
                        if(someborrow == 0){
                            out.writeObject(2);
                        }
                        else{
                            out.writeObject(3);
                            out.writeObject(someborrow);
                            for(Borrow borrow : allborrow){
                                if(Objects.equals(borrow.getUser(),user) && Objects.equals(borrow.getReturnDate(),null) && Objects.equals(borrow.getBook().getTitle(),title)){
                                    out.writeObject(borrow.getBook().getId());
                                    out.writeObject(borrow.getBook().getTitle());
                                    out.writeObject(borrow.getBook().getAuthor());
                                    out.writeObject(borrow.getBook().getPress());
                                    out.writeObject(borrow.getBook().getIsbn());
                                }
                            }
                        }
                    }
                    else if(!Objects.equals(author,"")){
                        List<Borrow> allborrow = borrowRepository.findAll();
                        Integer someborrow = 0;
                        for(Borrow borrow : allborrow){
                            if(Objects.equals(borrow.getUser(),user) && Objects.equals(borrow.getReturnDate(),null) && Objects.equals(borrow.getBook().getAuthor(),author)){
                                someborrow ++;
                            }
                        }
                        if(someborrow == 0){
                            out.writeObject(2);
                        }
                        else{
                            out.writeObject(3);
                            out.writeObject(someborrow);
                            for(Borrow borrow : allborrow){
                                if(Objects.equals(borrow.getUser(),user) && Objects.equals(borrow.getReturnDate(),null) && Objects.equals(borrow.getBook().getAuthor(),author)){
                                    out.writeObject(borrow.getBook().getId());
                                    out.writeObject(borrow.getBook().getTitle());
                                    out.writeObject(borrow.getBook().getAuthor());
                                    out.writeObject(borrow.getBook().getPress());
                                    out.writeObject(borrow.getBook().getIsbn());
                                }
                            }
                        }
                    }
                    System.out.println("查找完成");
                    out.flush();
                    break;
                case "requestAllClass":
                    userid = (Long) in.readObject();
                    user = userRepository.findById(userid).get();
                    List<Borrow> allborrow = borrowRepository.findAll();
                    Integer someborrow = 0;
                    for(Borrow borrow : allborrow){
                        if(Objects.equals(borrow.getReturnDate(),null) && Objects.equals(borrow.getUser(),user)){
                            someborrow ++;
                        }
                    }
                    out.writeObject(someborrow);
                    for(Borrow borrow : allborrow){
                        if(Objects.equals(borrow.getUser(),user)){
                            out.writeObject(borrow.getBook().getId());
                            out.writeObject(borrow.getBook().getTitle());
                            out.writeObject(borrow.getBook().getAuthor());
                            out.writeObject(borrow.getBook().getPress());
                            out.writeObject(borrow.getBook().getIsbn());
                        }
                    }
                    out.flush();
                    break;
                case "addMyBorrow":
                    System.out.println("BookList里AddButton");
                    userid = (Long) in.readObject();
                    id = (String) in.readObject();
                    if (Objects.equals(id,"")) {
                        out.writeObject(2);
                    }
                    else{
                        Long Id = Long.parseLong(id);
                        int res = borrowService.borrowBook(Id, userid);
                        out.writeObject(res);
                    }
                    out.flush();
                    break;
            }
        } else {
            System.out.println("Unknown function: " + function);
        }
    }
}
