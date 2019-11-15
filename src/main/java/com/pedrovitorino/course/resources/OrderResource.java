package com.pedrovitorino.course.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.pedrovitorino.course.dto.OrderDTO;
import com.pedrovitorino.course.dto.OrderItemDTO;
import com.pedrovitorino.course.services.OrderService;

@RestController
@RequestMapping(value = "/orders")
public class OrderResource {
	
	@Autowired
	private OrderService service;

	@PreAuthorize("hasAnyRole('ADMIN')")
	@GetMapping
	public ResponseEntity<List<OrderDTO>> findAll() {
		List<OrderDTO> orderList = service.findAll();
		return ResponseEntity.ok().body(orderList);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<OrderDTO> findById(@PathVariable Long id) {
		OrderDTO order = service.findById(id);
		return ResponseEntity.ok().body(order);
	}
	
	@GetMapping(value = "/myorders")
	public ResponseEntity<List<OrderDTO>> findByClient() {
		List<OrderDTO> orderList = service.findByClient();
		return ResponseEntity.ok().body(orderList);
	}	
	
	@GetMapping(value = "/{id}/items")
	public ResponseEntity<List<OrderItemDTO>> findItems(@PathVariable Long id) {
		List<OrderItemDTO> list = service.findItems(id);
		return ResponseEntity.ok().body(list);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@GetMapping(value = "/client/{clientId}")
	public ResponseEntity<List<OrderDTO>> findByClientId(@PathVariable Long clientId) {
		List<OrderDTO> orderList = service.findByClientId(clientId);
		return ResponseEntity.ok().body(orderList);
	}
	
	@PostMapping()
	public ResponseEntity<OrderDTO> placeOrder(@RequestBody List<OrderItemDTO> dto) {
		OrderDTO orderDTO = service.placeOrder(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(orderDTO.getId()).toUri();
		return ResponseEntity.created(uri).body(orderDTO);

	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@PutMapping(value = "/{id}")
	public ResponseEntity<OrderDTO> update(@PathVariable Long id, @RequestBody OrderDTO obj) {
		obj = service.update(id, obj);
		return ResponseEntity.ok().body(obj);
	}
}
