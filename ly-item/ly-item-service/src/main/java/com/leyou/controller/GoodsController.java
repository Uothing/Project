package com.leyou.controller;

import com.leyou.common.vo.PageResult;
import com.leyou.pojo.dto.SpuDTO;
import com.leyou.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @version V1.0
 * @author: weiyuan
 * @date: 2019/11/5 21:16
 * @description:
 */
@RestController
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    //商品查询
    @GetMapping("/spu/page")
    public ResponseEntity<PageResult<SpuDTO>> querySpuByPage(
            @RequestParam(value = "page", defaultValue = "1")Integer page,
            @RequestParam(value = "rows", defaultValue = "5")Integer rows,
            @RequestParam(value = "key", required = false)String key,
            @RequestParam(value = "saleable", required = false)Boolean saleable
    ) {
        return ResponseEntity
                .ok(goodsService.querySpuByPage(page,rows, key, saleable));
    }
}
