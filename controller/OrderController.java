package com.ecosystem.backend.controller;

import com.ecosystem.backend.entity.*;
import com.ecosystem.backend.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    OrderRepository orderRepository;

    @PostMapping
    public Orders createOrder(
            @RequestBody Orders order
    ) {

        String role = getRole();

        ProductType type = order.getProductType();
        OrderType orderType = order.getOrderType();

        // ================= BIOGAS =================
        if (role.equals("ROLE_BIOGAS")) {

            // BUY → only WASTE
            if (orderType == OrderType.BUY &&
                    type != ProductType.WASTE) {

                throw new RuntimeException(
                        "Biogas can buy only waste"
                );
            }

            // SELL → only FERTILIZER
            if (orderType == OrderType.SELL &&
                    type != ProductType.FERTILIZER) {

                throw new RuntimeException(
                        "Biogas can sell only fertilizer"
                );
            }
        }


        // ================= SUPPLIER =================
        if (role.equals("ROLE_SUPPLIER")) {

            // SELL → only WASTE
            if (orderType == OrderType.SELL &&
                    type != ProductType.WASTE) {

                throw new RuntimeException(
                        "Supplier can sell only waste"
                );
            }

            // BUY → only CHARA
            if (orderType == OrderType.BUY &&
                    type != ProductType.CHARA) {

                throw new RuntimeException(
                        "Supplier can buy only chara"
                );
            }
        }


        // ================= FARMER =================
        if (role.equals("ROLE_FARMER")) {

            // BUY → only FERTILIZER
            if (orderType == OrderType.BUY &&
                    type != ProductType.FERTILIZER) {

                throw new RuntimeException(
                        "Farmer can buy only fertilizer"
                );
            }

            // SELL → only CHARA
            if (orderType == OrderType.SELL &&
                    type != ProductType.CHARA) {

                throw new RuntimeException(
                        "Farmer can sell only chara"
                );
            }
        }


        // ADMIN → no restriction

        order.setStatus(OrderStatus.PENDING);

        return orderRepository.save(order);
    }

    @GetMapping
    public Object getAll() {
        return orderRepository.findAll();
    }

    private String getRole() {

        Authentication auth =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        return auth
                .getAuthorities()
                .iterator()
                .next()
                .getAuthority();
    }
}