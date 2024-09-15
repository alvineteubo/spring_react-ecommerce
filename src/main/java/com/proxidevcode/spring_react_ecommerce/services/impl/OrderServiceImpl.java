package com.proxidevcode.spring_react_ecommerce.services.impl;

import java.util.Set;
import java.util.stream.Collectors;
import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.proxidevcode.spring_react_ecommerce.models.Order;
import com.proxidevcode.spring_react_ecommerce.models.OrderProduct;
import com.proxidevcode.spring_react_ecommerce.models.Product;
import com.proxidevcode.spring_react_ecommerce.dtos.OrderRequest;
import com.proxidevcode.spring_react_ecommerce.dtos.OrderResponse;
import com.proxidevcode.spring_react_ecommerce.dtos.PagedResponse;
import com.proxidevcode.spring_react_ecommerce.mappers.OrderMapper;
import com.proxidevcode.spring_react_ecommerce.repositories.OrderProductRepository;
import com.proxidevcode.spring_react_ecommerce.repositories.OrderRepository;
import com.proxidevcode.spring_react_ecommerce.repositories.ProductRepository;
import com.proxidevcode.spring_react_ecommerce.services.OrderService;
import jakarta.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;



import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service
@Transactional
@RequiredArgsConstructor
@Validated
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final ProductRepository productRepository;
    private final OrderProductRepository orderProductRepository;
    private final JavaMailSender mailSender;

    @Override
    public PagedResponse<OrderResponse> getAllOrders(Integer page, Integer pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(Sort.Direction.DESC, "firstName"));
        Page<Order> orderPage = orderRepository.findAll(pageable);
        return PagedResponse.<OrderResponse>builder()
        .content(orderPage.getContent().stream().map(orderMapper::mapToDto).toList())
        .last(orderPage.isLast())
        .first(orderPage.isFirst())
        .totalPages(orderPage.getTotalPages())
        .totalElement(orderPage.getTotalElements())
        .number(orderPage.getNumber())
        .size(orderPage.getSize())
        .build();

    }
    @Override
    public OrderResponse getOrderById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getOrderById'");
    }
 
    @Override
     public OrderResponse createOrder(OrderRequest entity) {
    if (entity == null) {
        throw new IllegalArgumentException("OrderRequest is null");
    }
    if (entity.getOrderProductRequests().isEmpty()) {
        throw new IllegalArgumentException ("OrderProducts is empty");
    }

    // Création de la collection de OrderProduct
    Set<OrderProduct> orderProducts = entity.getOrderProductRequests().stream().map(orderProductRequest -> {
        Product product = productRepository.findById(orderProductRequest.getProductId())
            .orElseThrow(() -> new EntityNotFoundException("Product not found: " + orderProductRequest.getProductId()));
        
        if (product.getQuantity() < orderProductRequest.getQuantity()) {
            throw new IllegalArgumentException("OrderProduct contains insufficient quantity");
        }

        product.setQuantity(product.getQuantity() - orderProductRequest.getQuantity());
        productRepository.save(product);

        return OrderProduct.builder()
            .quantity(orderProductRequest.getQuantity())
            .price(orderProductRequest.getPrice())
            .product(product)
            .build();
    }).collect(Collectors.toSet());

    // Création de l'entité Order
    Order order = Order.builder()
        .firstName(entity.getFirstName())
        .lastName(entity.getLastName())
        .email(entity.getEmail())
        .address(entity.getAddress())
        //.phone(entity.getPhone())
        .orderProducts(orderProducts)
        .build();
    
    // Liaison de l'ordre avec chaque OrderProduct
    orderProducts.forEach(orderProduct -> orderProduct.setOrder(order));

    // Sauvegarde de l'entité Order
    Order savedOrder = orderRepository.save(order);

    return orderMapper.mapToDto(savedOrder);
}

    @Override
    public OrderResponse updateOrder(OrderRequest OrderRequest, String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateOrder'");
    }

    @Override
    public OrderResponse deleteOrder(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteOrder'");
    }

private void sendOrderConfirmation(Order order ){
    String toAddress = order.getEmail();
    String subject = "ALvineStore" + "Order confirmation -" + order.getId();
     StringBuilder content = new StringBuilder(" Dear" +  order.getFirstName() + ",\n\n\n"
    + " Thank you for your order! you can see detail bellow: \n\n"
    +"Order ID: " + order.getId() + "\n"
    + "Order Date" + LocalDateTime.now() +"\n"
    + "List of products:\n" );

    for (OrderProduct orderProduct : order.getOrderProducts()) {
            content.append("- ").append(orderProduct.getProduct().getName())
                .append(" (Quantity: ").append(orderProduct.getQuantity()).append("),\n");

    content.append("\n We hope to see you again: \n Best regards, \n AlvineStore inc");

     try{
        MimeMessage  message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(toAddress);
        helper.setSubject(subject);
        helper.setText(String.valueOf(content));
        mailSender.send(message);

     } catch(Exception e){
        throw new RuntimeException("Fail to send mail" + e);

     }
}

}}
