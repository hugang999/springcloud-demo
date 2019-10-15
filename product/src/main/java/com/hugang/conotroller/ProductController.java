package com.hugang.conotroller;

import com.hugang.VO.ProductInfoVo;
import com.hugang.VO.ProductVO;
import com.hugang.VO.ResultVO;
import com.hugang.common.product.beans.DecreaseStock;
import com.hugang.common.product.beans.ProductCategory;
import com.hugang.common.product.beans.ProductInfo;
import com.hugang.service.ProductCategotyService;
import com.hugang.service.ProductInfoService;
import com.hugang.util.ResultVOUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/product")
public class ProductController {

    @Autowired
    private ProductInfoService productInfoService;

    @Autowired
    private ProductCategotyService productCategotyService;


    /**
     * 1、查询所有在架商品
     * 2、获取类目type列表
     * 3、从数据库查询类目
     * 4、构造数据
     * CrossOrigin表示该方法支持跨域访问，allowCredentials="true"表示允许携带cookie跨域访问
     * @return
     */
    @GetMapping(value = "/list")
    @CrossOrigin(allowCredentials = "true")
    public ResultVO<List<ProductVO>> list(){
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //1、查询所有在架商品
        List<ProductInfo> productInfoList = productInfoService.findUpAll();

        //2、获取类目type列表（λ表达式）
        List<Integer> categoryTypeList  = productInfoList.stream()
                .map(ProductInfo::getCategoryType).collect(Collectors.toList());

        //3、从数据库查询类目
        List<ProductCategory> productCategoryList = productCategotyService.getByCategoryType(categoryTypeList);

        //4、构造数据
        List<ProductVO> productVOList = new ArrayList<>();
        for (ProductCategory productCategory : productCategoryList){
            ProductVO productVO = new ProductVO();
            productVO.setCategoryName(productCategory.getCategoryName());
            productVO.setCategoryType(productCategory.getCategoryType());
            List<ProductInfoVo> productInfoVos = new ArrayList<>();
            for (ProductInfo productInfo : productInfoList){
                if (productInfo.getCategoryType().equals(productCategory.getCategoryType())){
                    ProductInfoVo productInfoVo = new ProductInfoVo();
                    BeanUtils.copyProperties(productInfo, productInfoVo);
                    productInfoVos.add(productInfoVo);
                }
            }
            productVO.setProductInfoVos(productInfoVos);
            productVOList.add(productVO);
        }
        return ResultVOUtils.success(productVOList);
    }

    /**
     * 根据商品id列表查询商品列表
     * @param productIdList 商品列表id
     * @return List<ProductInfo> 商品列表
     */
    @PostMapping(value = "/listForOrder")
    public List<ProductInfo> listForOrder(@RequestBody List<Integer> productIdList){
        return productInfoService.findList(productIdList);
    }

    /**
     * 根据商品id更新商品库存
     * @param decreaseStockList
     */
    @PostMapping(value = "/decreaseStock")
    public void decreaseStock(@RequestBody List<DecreaseStock> decreaseStockList){
        productInfoService.decreaseStock(decreaseStockList);
    }
}
