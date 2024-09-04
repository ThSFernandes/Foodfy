package br.foodfy.order;

import br.foodfy.order.controller.OrderController;
import br.foodfy.order.model.Order;
import br.foodfy.order.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class OrderControllerTest {

	@Mock
	private OrderService orderService;

	@InjectMocks
	private OrderController orderController;

	private Order order;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		order = new Order();
		order.setId(1L);
		order.setCustomer("Customer 1");
		order.setTotal(100.0);
		order.setStatus("PENDING");
	}

	@Test
	void testCreateOrder_Success() {
		when(orderService.createOrder(any(Order.class))).thenReturn(order);

		ResponseEntity<Order> response = orderController.createOrder(order);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(order, response.getBody());
	}

	@Test
	void testCreateOrder_BadRequest() {
		when(orderService.createOrder(any(Order.class))).thenThrow(IllegalArgumentException.class);

		ResponseEntity<Order> response = orderController.createOrder(order);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertEquals(null, response.getBody());
	}

	@Test
	void testGetOrder_Success() {
		when(orderService.getOrder(1L)).thenReturn(order);

		ResponseEntity<Order> response = orderController.getOrder(1L);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(order, response.getBody());
	}

	@Test
	void testGetOrder_NotFound() {
		when(orderService.getOrder(1L)).thenThrow(NullPointerException.class);

		ResponseEntity<Order> response = orderController.getOrder(1L);

		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	@Test
	void testListOrders_Success() {
		List<Order> orders = Arrays.asList(order, new Order());
		when(orderService.listOrders()).thenReturn(orders);

		ResponseEntity<List<Order>> response = orderController.listOrders();

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(orders, response.getBody());
	}

	@Test
	void testListOrders_NoContent() {
		when(orderService.listOrders()).thenThrow(IllegalStateException.class);

		ResponseEntity<List<Order>> response = orderController.listOrders();

		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
	}

	@Test
	void testDeleteOrder_Success() {
		doNothing().when(orderService).deleteOrder(1L);

		ResponseEntity<Void> response = orderController.deleteOrder(1L);

		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
		verify(orderService, times(1)).deleteOrder(1L);
	}

	@Test
	void testDeleteOrder_BadRequest() {
		doThrow(IllegalArgumentException.class).when(orderService).deleteOrder(1L);

		ResponseEntity<Void> response = orderController.deleteOrder(1L);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}

	@Test
	void testUpdateStatus_Success() {
		doNothing().when(orderService).updateStatus(1L, "COMPLETED");

		ResponseEntity<Void> response = orderController.updateStatus(1L, "COMPLETED");

		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
		verify(orderService, times(1)).updateStatus(1L, "COMPLETED");
	}

	@Test
	void testUpdateStatus_NotFound() {
		doThrow(NullPointerException.class).when(orderService).updateStatus(1L, "COMPLETED");

		ResponseEntity<Void> response = orderController.updateStatus(1L, "COMPLETED");

		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}
}
