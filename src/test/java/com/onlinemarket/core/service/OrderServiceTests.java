package com.onlinemarket.core.service;

import com.onlinemarket.core.repo.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@AutoConfigureMockMvc
@SpringBootTest
public class OrderServiceTests {
    @MockBean
    OrderRepo orderRepo;

    @Autowired
    OrderService orderService;
}
