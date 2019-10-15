package com.hugang.service;


import com.hugang.common.product.beans.ProductCategory;

import java.util.Collection;
import java.util.List;

public interface ProductCategotyService {

    List<ProductCategory> getByCategoryType(Collection collection);
}
