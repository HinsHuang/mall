package com.imooc.mall.service.impl;

import com.google.gson.Gson;
import com.imooc.mall.dao.ProductMapper;
import com.imooc.mall.enums.ProductStatusEnum;
import com.imooc.mall.enums.ResponseEnum;
import com.imooc.mall.form.CartAddForm;
import com.imooc.mall.pojo.Cart;
import com.imooc.mall.pojo.Product;
import com.imooc.mall.service.CartService;
import com.imooc.mall.vo.CartProductVo;
import com.imooc.mall.vo.CartVo;
import com.imooc.mall.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.*;

@Service
public class CartServiceImpl implements CartService {

    private static final String CART_REDIS_KEY_TEMPLATE = "cart_%d";

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private StringRedisTemplate redisTemplate;

    private Gson gson = new Gson();

    @Override
    public ResponseVo<CartVo> add(Integer uid, CartAddForm cartAddForm) {
        //商品是否存在
        Product product = productMapper.selectByPrimaryKey(cartAddForm.getProductId());
        if (product == null) {
            return ResponseVo.error(ResponseEnum.PRODUCT_NOT_EXIST);
        }

        //商品是否可售状态
        if (!product.getStatus().equals(ProductStatusEnum.ON_SALE.getCode())) {
            return ResponseVo.error(ResponseEnum.PRODUCT_OFF_SALE_OR_DELETE);
        }

        //商品库存是否足够
        if (product.getStatus() <= 0) {
            return ResponseVo.error(ResponseEnum.PRODUCT_STOCK_ERROR);
        }

        //写入Redis
        //key:cart_uid
        Integer quantity = 1;
        HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE, uid);
        String productId = String.valueOf(product.getId());
        String value = opsForHash.get(redisKey, productId);
        Cart cart;
        if (StringUtils.isEmpty(value)) {
            cart = new Cart(cartAddForm.getProductId(), quantity, cartAddForm.getSelected());
        } else {
            cart = gson.fromJson(value, Cart.class);
            cart.setQuantity(cart.getQuantity() + quantity);
        }

        opsForHash.put(String.format(redisKey),
                String.valueOf(productId),
                gson.toJson(cart));

        return list(uid);
    }

    @Override
    public ResponseVo<CartVo> list(Integer uid) {
        HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE, uid);
        Map<String, String> entries = opsForHash.entries(redisKey);
        Set<Integer> productIdSet = new HashSet<>();
        for (Map.Entry<String, String> entry : entries.entrySet()) {
            productIdSet.add(Integer.valueOf(entry.getKey()));
        }

        List<Product> productList = productMapper.selecctByProductIdSet(productIdSet);
        CartVo cartVo = new CartVo();
        List<CartProductVo> cartProductVoList = new ArrayList<>();
        Boolean selectedAll = true;
        Integer cartTotalQuantity = 0;
        BigDecimal cartTotalPrice = BigDecimal.ZERO;
        for (Product product : productList) {
            String value = opsForHash.get(redisKey, String.valueOf(product.getId()));
            Cart cart = gson.fromJson(value, Cart.class);
            CartProductVo cartProductVo = new CartProductVo(
                    product.getId(),
                    cart.getQuantity(),
                    product.getName(),
                    product.getSubtitle(),
                    product.getMainImage(),
                    product.getPrice(),
                    product.getStatus(),
                    product.getPrice().multiply(BigDecimal.valueOf(cart.getQuantity())),
                    product.getStock(),
                    cart.getProductSelected()
                    );
            cartProductVoList.add(cartProductVo);

            if (!cart.getProductSelected()) {
                selectedAll = false;
            }

            if (cart.getProductSelected()) {
                cartTotalPrice = cartTotalPrice.add(product.getPrice().multiply(BigDecimal.valueOf(cart.getQuantity())));
            }

            cartTotalQuantity += cart.getQuantity();
        }

        cartVo.setCartProductVoList(cartProductVoList);
        cartVo.setSelectedAll(selectedAll);
        cartVo.setCartTotalPrice(cartTotalPrice);
        cartVo.setCartTotalQuantity(cartTotalQuantity);
        return ResponseVo.success(cartVo);
    }
}
