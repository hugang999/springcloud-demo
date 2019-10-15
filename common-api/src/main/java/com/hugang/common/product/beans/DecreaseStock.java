package com.hugang.common.product.beans;

import com.baomidou.mybatisplus.annotations.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 商品减少库存参数
 * @author: hg
 * @date: 2019/9/21 11:06
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class DecreaseStock {

    @TableId
    private Long productId;

    private Integer productQuantity;
}
