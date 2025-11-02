package com.example.virtual_campus.ServerPages;

import com.example.virtual_campus.controller.AuthController;
import com.example.virtual_campus.controller.ProductController;
import com.example.virtual_campus.controller.ProductController;
import com.example.virtual_campus.model.Product;
import com.example.virtual_campus.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
public class ManageGoodsPage {

    @Autowired
    private ProductController productController;

    public void work(String function, ObjectInputStream in, ObjectOutputStream out)
            throws IOException, ClassNotFoundException {
        if (function != null) {
            System.out.println("读入第二段指向:"+function);
            switch (function) {
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
                    String id = (String) in.readObject();
                    String name = (String) in.readObject();
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
                case "EditGoods":
                    System.out.println("正在修改中");
                    id = (String) in.readObject();
                    name = (String) in.readObject();
                    String price = (String) in.readObject();
                    String quantity = (String) in.readObject();
                    Long productId = Long.parseLong(id);
                    Double Price=Double.parseDouble(price);
                    Integer Quantity=Integer.parseInt(quantity);
                    out.writeObject(productController.ProductEdit(productId,name,Price,Quantity));
                    System.out.println("修改完成");
                    out.flush();
                    break;
                case "DeleteGoods":
                    System.out.println("正在删除中");
                    String DelId = (String) in.readObject();
                    if(Objects.equals(DelId,"")){
                        out.writeObject(false);
                    }
                    else{
                        Long delid = Long.parseLong(DelId);
                        out.writeObject(productController.ProductDelete(delid));
                    }
                    out.flush();
                    break;
                case "AddGoods":
                    System.out.println("正在添加中");
                    String AddId = (String) in.readObject();
                    name = (String) in.readObject();
                    price = (String) in.readObject();
                    quantity = (String) in.readObject();
                    if(Objects.equals(AddId,"") || Objects.equals(name,"") || Objects.equals(price,"") || Objects.equals(quantity,"")){
                        out.writeObject(false);
                    }
                    else{
                        Long addid = Long.parseLong(AddId);
                        Price=Double.parseDouble(price);
                        Quantity=Integer.parseInt(quantity);
                        out.writeObject(productController.ProductAdd(addid,name,Price,Quantity));
                    }
                    out.flush();
                    break;
            }
        } else {
            System.out.println("Unknown function: " + function);
        }
    }
}
