package com.leyou.controller;

import com.leyou.common.vo.PageResult;
import com.leyou.item.entity.Brand;
import com.leyou.pojo.dto.BrandDTO;
import com.leyou.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @version V1.0
 * @author: weiyuan
 * @date: 2019/11/1 19:50
 * @description:
 */
@RestController
@RequestMapping("brand")
public class BrandController {
    @Autowired
    private BrandService brandService;

    @GetMapping("page")
    public ResponseEntity<PageResult<BrandDTO>> queryBrandByPage(
            @RequestParam(value = "page", defaultValue = "1")Integer page,
            @RequestParam(value = "rows", defaultValue = "5")Integer rows,
            @RequestParam(value = "key", required = false)String key,
            @RequestParam(value = "sortBy", required = false)String sortBy,
            @RequestParam(value = "desc", defaultValue = "false")Boolean desc
    ){
        return ResponseEntity
                .ok(brandService.queryBrandByPage(page,rows, key, sortBy, desc));
    }


    @PostMapping
    public ResponseEntity<Void> saveBrand(BrandDTO brand, @RequestParam("cids")List<Long> ids) {
        brandService.saveBrand(brand, ids);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    //修改品牌数据
    @PutMapping
    public ResponseEntity<Void> updateBrand(BrandDTO brand, @RequestParam("cids")List<Long> ids) {
        brandService.updateBrand(brand, ids);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
