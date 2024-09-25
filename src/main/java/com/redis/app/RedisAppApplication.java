package com.redis.app;

import com.redis.app.entity.Product;
import com.redis.app.repo.ProductDao;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@RestController
@RequestMapping("/products")
@Log4j2
public class RedisAppApplication {

	@Autowired
	private ProductDao productDao;

	@PostMapping("")
	public ResponseEntity<Product> save(@RequestBody Product product) {
		return ResponseEntity.ok(productDao.save(product));
	}

	@GetMapping("")
	public ResponseEntity<List<Product>> getAll() {
		return ResponseEntity.ok(productDao.findAll());
	}

	@GetMapping("/")
	public ResponseEntity<Product> getById(@RequestParam("id") int id) {
		log.info("Get product by id: " + id);
		return ResponseEntity.ok(productDao.findById(id));
	}

	// In this way I can get rid of update method in my CRUD operations just using save method the same as create
	@PutMapping("")
	public ResponseEntity<Product> update(@RequestBody Product product, @RequestParam("id") int id) {
		product.setId(id);
		return ResponseEntity.ok(productDao.save(product));
	}

	@DeleteMapping("")
	public ResponseEntity<String> delete(@RequestParam("id") int id) {
		productDao.delete(id);
		return ResponseEntity.ok("Deleted");
	}

	public static void main(String[] args) {
		System.setProperty("spring.devtools.restart.enabled", "false");
		SpringApplication.run(RedisAppApplication.class, args);
	}

}
