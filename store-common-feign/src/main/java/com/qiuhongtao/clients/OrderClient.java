package com.qiuhongtao.clients;

import com.qiuhongtao.param.PageParam;
import com.qiuhongtao.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "order-service")
public interface OrderClient {


    /**
     * 检查商品有没有被引用,有取消删除!
     * @param productId
     * @return
     */
    @PostMapping("/order/check")
    R checkProduct(Integer productId);


    @PostMapping("order/admin/list")
    R adminList(@RequestBody PageParam pageParam);

}
