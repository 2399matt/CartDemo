package com.example.Cart_Demo.controller;

import com.example.Cart_Demo.entity.*;
import com.example.Cart_Demo.security.MyUserDetails;
import com.example.Cart_Demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

//TODO TRY CUSTOM SPRING SECURITY IMPLEMENTATION, REMOVING THE NEED FOR A CUSTOMUSER OBJECT ALTOGETHER.
@Controller
public class EcomController {

    private final ItemService itemService;
    private final UserService userService;
    private final CartService cartService;
    private final OrderService orderService;

    @Autowired
    public EcomController(ItemService itemService, UserService userService, CartService cartService, OrderService orderService) {
        this.itemService = itemService;
        this.userService = userService;
        this.cartService = cartService;
        this.orderService = orderService;
    }


    @GetMapping("/home")
    public String homePage() {
        return "index";
    }

    //product
    @GetMapping("/list")
    public String productList(Model model) {
        List<Item> items = itemService.findAll();
        model.addAttribute("items", items);
        return "item-list :: itemList";
    }

    //product
    @PostMapping("/single-product")
    public String getSingleProduct(@RequestParam("itemName") String name, Model model) {
        Item item = itemService.findByName(name);
        List<Item> items = new ArrayList<>();
        if (item != null) {
            items.add(item);
        }
        model.addAttribute("items", items);
        return "item-list :: itemList";
    }

    //cart
    @GetMapping("/add-item")
    public String addToCart(@RequestParam("id") int id, @AuthenticationPrincipal MyUserDetails userDetails, Model model) {
        CustomUser user = userDetails.getUser();
        Cart currCart = cartService.findUserCart(user.getId());
        Item item = itemService.findById(id);
        List<Item> items = itemService.findAll();
        if (item.getStock() == 0) {
            model.addAttribute("items", items);
            model.addAttribute("error", "No product available!");
            return "item-list :: itemList";
        }else{
            cartService.addItemToCart(currCart, item);
            model.addAttribute("items", items);
            return "item-list :: itemList";
        }
    }

    //cart
    @GetMapping("/show-cart")
    public String showCart(@AuthenticationPrincipal MyUserDetails userDetails, Model model) {
        int total = 0;
        CustomUser user = userDetails.getUser();
        Cart cart = cartService.findUserCart(user.getId());
        for (CartItem cartItem : cart.getItems()) {
            total += cartItem.getPrice();
        }
        model.addAttribute("cart", cart);
        model.addAttribute("total", total);
        return "cartPage :: cartPage";
    }

    //cart
    @GetMapping("/remove-item")
    public String removeItem(@RequestParam("id") int id, Model model, @AuthenticationPrincipal MyUserDetails userDetails) {
        int total = 0;
        CustomUser user = userDetails.getUser();
        Cart cart = cartService.findUserCart(user.getId());
        cartService.removeItemFromCart(cart, id);
        for(CartItem cartItem : cart.getItems()){
            total += cartItem.getPrice();
        }
        model.addAttribute("cart", cart);
        model.addAttribute("total", total);
        return "cartPage :: cartPage";
    }

    //orders
    @GetMapping("/orders")
    public String userOrderList(@AuthenticationPrincipal MyUserDetails userDetails, Model model) {
        CustomUser user = userDetails.getUser();
        List<Order> orders = orderService.findUserOrders(user.getId());
        model.addAttribute("orders", orders);
        return "order-list :: orderList";
    }

    //orders
    @GetMapping("/getOrder")
    public String showOrder(@RequestParam("id") int id, Model model) {
        Order order = orderService.findById(id);
        model.addAttribute("order", order);
        return  "order-page :: orderPage";
    }

    //orders + cart
    @PostMapping("/checkout")
    public String checkout(Model model, @AuthenticationPrincipal MyUserDetails userDetails) {
        CustomUser user = userDetails.getUser();
        Cart cart = cartService.findUserCart(user.getId());
        Order newOrder = orderService.createNewOrder(cart);
        model.addAttribute("order", newOrder);
        //Simulating checkout, no payment integration.
        return "TY :: Thanks";
    }


}
