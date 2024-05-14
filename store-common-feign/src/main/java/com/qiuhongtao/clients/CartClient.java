package com.qiuhongtao.clients;

import com.qiuhongtao.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "cart-service")
public interface CartClient {

    /**
     * 检查商品有没有被引用,有取消删除!
     * @param productId
     * @return
     */
    @PostMapping("/cart/check")
    R checkProduct(Integer productId);
}
