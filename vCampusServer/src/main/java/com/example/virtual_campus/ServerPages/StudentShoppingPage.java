package com.example.virtual_campus.ServerPages;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.example.virtual_campus.controller.ProductController;
import com.example.virtual_campus.controller.UserController;
import com.example.virtual_campus.model.Product;
import com.example.virtual_campus.model.User;
import com.example.virtual_campus.repository.ProductRepository;
import com.example.virtual_campus.repository.UserRepository;
import com.example.virtual_campus.service.ProductService;
import com.example.virtual_campus.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudentShoppingPage {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserController userController;
    @Autowired
    private ProductController productController;
    @Autowired
    private UserService userService;

    public void work(String function, ObjectInputStream in, ObjectOutputStream out)
            throws IOException, ClassNotFoundException {
        if (function != null) {
            System.out.println("读入第二段指向:"+function);
            switch (function) {
                case "addMyProduct":
                    System.out.println("开始购买");
                    Long userid = (Long) in.readObject();
                    String id = (String) in.readObject();
                    String name = (String) in.readObject();
                    String quantity = (String) in.readObject();
                    if (Objects.equals(id,"") || Objects.equals(name,"") || Objects.equals(quantity,"")) {
                        out.writeObject(1);
                    }
                    else{
                        Long Id = Long.parseLong(id);
                        Product product = productRepository.findById(Id).get();
                        int Quantity = Integer.parseInt(quantity);
                        User user = userRepository.findById(userid).get();
                        if (user.getBalance() < product.getPrice() * Quantity) {
                            out.writeObject(2);
                        } else {
                            userService.AddBalance(userid,- product.getPrice() * Quantity);
                            product.setQuantity(product.getQuantity()-Quantity);
                            productController.updateProduct(Id,product);
                            out.writeObject(3);
                        }
                    }
                    System.out.println("购买完成");
                    out.flush();
                    break;
                case "requestAllClass":
                    System.out.println("请求所有用户信息中");
                    List<Product> allproduct = productController.getAllProducts();
                    out.writeObject((Integer)allproduct.size());
                    for(Product product : allproduct){
                        out.writeObject(product.getId());
                        out.writeObject(product.getName());
                        out.writeObject(product.getPrice());
                        out.writeObject(product.getQuantity());
                    }
                    System.out.println("请求完成");
                    out.flush();
                    break;
                case "SelectSomeone":
                    System.out.println("正在查找对应数据中");
                    id = (String) in.readObject();
                    name = (String) in.readObject();
                    if(Objects.equals(id,"") && Objects.equals(name,"")){
                        out.writeObject(1);//输入为空
                    }
                    else if(!Objects.equals(id,"")){
                        Long productId = Long.parseLong(id);
                        Optional<Product> SearchproductId = productController.SearchById(productId);
                        if(SearchproductId.isEmpty()) out.writeObject(2);//输入有误
                        else{
                            out.writeObject(3);
                            out.writeObject(1);
                            out.writeObject(SearchproductId.get().getId());
                            out.writeObject(SearchproductId.get().getName());
                            out.writeObject(SearchproductId.get().getPrice());
                            out.writeObject(SearchproductId.get().getQuantity());
                        }
                    }
                    else if(!Objects.equals(name,"")){
                        Optional<Product> SearchproductId = productController.SearchByName(name);
                        if(SearchproductId.isEmpty()) out.writeObject(2);//输入有误
                        else{
                            out.writeObject(3);
                            out.writeObject(1);
                            out.writeObject(SearchproductId.get().getId());
                            out.writeObject(SearchproductId.get().getName());
                            out.writeObject(SearchproductId.get().getPrice());
                            out.writeObject(SearchproductId.get().getQuantity());
                        }
                    }
                    System.out.println("查找完成");
                    out.flush();
                    break;
            }
        } else {
            System.out.println("Unknown function: " + function);
        }
    }
}
