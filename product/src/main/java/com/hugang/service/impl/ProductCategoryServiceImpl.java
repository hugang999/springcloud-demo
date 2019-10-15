package com.hugang.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.hugang.common.product.beans.ProductCategory;
import com.hugang.mapper.ProductCategoryMapper;
import com.hugang.service.ProductCategotyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Service
public class ProductCategoryServiceImpl implements ProductCategotyService {

    @Autowired
    ProductCategoryMapper mapper;


    @Override
    public List<ProductCategory> getByCategoryType(Collection collection) {
        ArrayList list = new ArrayList();
        return mapper.selectList(new EntityWrapper<ProductCategory>().in("category_type", collection));
    }
}
