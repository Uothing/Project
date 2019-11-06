package com.leyou.mapper;

import com.leyou.item.entity.Brand;
import com.leyou.pojo.dto.BrandDTO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @version V1.0
 * @author: weiyuan
 * @date: 2019/11/1 17:03
 * @description:
 */
public interface BrandMapper extends Mapper<Brand> {

    //中间表
    int insertCategoryBrand(@Param("bid") Long bid, @Param("ids") List<Long> ids);

    //修改数据时，先删除旧的中间表
    @Delete("DELETE from tb_category_brand WHERE brand_id = #{bid}")
    int deleteCategoryAndBrand(@Param("bid") Long bid);
}
