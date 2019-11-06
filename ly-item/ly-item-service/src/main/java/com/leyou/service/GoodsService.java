package com.leyou.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.common.utils.BeanHelper;
import com.leyou.common.vo.PageResult;
import com.leyou.item.entity.Brand;
import com.leyou.item.entity.Spu;
import com.leyou.mapper.*;
import com.leyou.pojo.dto.BrandDTO;
import com.leyou.pojo.dto.CategoryDTO;
import com.leyou.pojo.dto.SpuDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @version V1.0
 * @author: weiyuan
 * @date: 2019/11/5 21:18
 * @description:
 */
@Service
public class GoodsService {

    @Autowired
    private SpuDetailMapper spuDetailMapper;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private BrandService brandService;


    @Autowired
    private SpuMapper spuMapper;


    //商品查询
    public PageResult<SpuDTO> querySpuByPage(
            Integer page, Integer rows, String key, Boolean saleable
        ) {
        //分页
        PageHelper.startPage(page, rows);
        //过滤 --> 为下面的真正查询做准备
        Example example = new Example(Spu.class);
        Example.Criteria criteria = example.createCriteria();
        if(StringUtils.isNoneBlank(key)) {
            criteria.andLike("name", "%" + key + "%");
        }

        //上下架
        if (saleable != null) {
            criteria.andEqualTo("saleable", saleable);
        }

        //默认时间排序
        example.setOrderByClause("update_time desc");

        // 3 查询结果
        List<Spu> list = spuMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(list)){
            throw new LyException(ExceptionEnum.GOODS_NOT_FOUND);
        }
        // 4 封装分页结果
        PageInfo<Spu> info = new PageInfo<>(list);

        //DTO转换
        List<SpuDTO> spuDTOS = BeanHelper.copyWithCollection(list, SpuDTO.class);

        //处理品牌的分类名称和品牌名称
        handleCategoryAndBrand(spuDTOS);

        return new PageResult<>(info.getTotal(), spuDTOS);
    }

    private void handleCategoryAndBrand(List<SpuDTO> spuDTOS) {

        for (SpuDTO spu : spuDTOS) {
            // 查询分类  ??????
            String categoryName = categoryService.queryCategoryByIds(spu.getCategoryIds())
                    .stream()
                    .map(CategoryDTO::getName).collect(Collectors.joining("/"));
            spu.setCategoryName(categoryName);
            // 查询品牌
            BrandDTO brand = brandService.queryById(spu.getBrandId());
            spu.setBrandName(brand.getName());
        }
    }
}
