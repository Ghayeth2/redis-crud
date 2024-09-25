package com.redis.app.repo;

import com.redis.app.entity.Product;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductDao {
    private final String HASH_KEY = "product";
    private RedisTemplate redisTemplate;

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

    public Product update(Product product, int id) {
        redisTemplate.opsForHash().put(HASH_KEY, id, product);
        return product;
    }

    public void delete(int id) {
        redisTemplate.opsForHash().delete(HASH_KEY, id);
    }
}
