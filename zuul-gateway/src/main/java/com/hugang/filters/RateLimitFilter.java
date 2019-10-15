package com.hugang.filters;

import com.google.common.util.concurrent.RateLimiter;
import com.hugang.exception.RateLimitException;
import com.netflix.zuul.ZuulFilter;
import org.springframework.stereotype.Component;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.SERVLET_DETECTION_FILTER_ORDER;

/**
 * 限流，优先级最高
 * @author: hg
 * @date: 2019/9/25 15:16
 */
@Component
public class RateLimitFilter extends ZuulFilter {

    /**
     * google定义的限流组件，表示令牌桶每秒放100个令牌，不累加，即每秒最多处理100个请求
     */
    private static final RateLimiter RATE_LIMITER = RateLimiter.create(100);

    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return SERVLET_DETECTION_FILTER_ORDER - 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        //如果没取到令牌，则抛出异常
        if (!RATE_LIMITER.tryAcquire()) {
            throw new RateLimitException();
        }
        return null;
    }
}
