package com.redis.app.repo;

import com.redis.app.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductDao {
    private final String HASH_KEY = "product";
    @Autowired
    private RedisTemplate redisTemplate;

    // Create or update existing one
    public Product save(Product product) {
        redisTemplate.opsForHash().put(HASH_KEY, product.getId(), product);
        return product;
    }

    public List<Product> findAll() {
        return (List<Product>) redisTemplate.opsForHash().values(HASH_KEY);
    }

    public Product findById(int id) {
        return (Product) redisTemplate.opsForHash().get(HASH_KEY, id);
    }

    public void delete(int id) {
        redisTemplate.opsForHash().delete(HASH_KEY, id);
    }
}
