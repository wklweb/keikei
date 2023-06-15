package com.keikei.common.core.page;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.extra.servlet.ServletUtil;
import com.keikei.common.utils.ServletUtils;
import com.keikei.common.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class PageDateSupport {

    public static PageData getPageDataByRequest(){
        Map<String,Object> map = new HashMap<>();
        int pageNum = Convert.toInt(ServletUtils.getRequest().getParameter(PageConstants.PageNum),1);
        int pageSize = Convert.toInt(ServletUtils.getRequest().getParameter(PageConstants.PageSize),10);
        String isAsc = Convert.toStr(ServletUtils.getRequest().getParameter(PageConstants.isAsc),"ascending");
        boolean reasonable = Convert.toBool(ServletUtils.getRequest().getParameter(PageConstants.reasonAble),false);
        String orderBy = Convert.toStr(ServletUtils.getRequest().getParameter(PageConstants.OrderBy),"");
        if(StringUtils.isNotEmpty(isAsc)){
            if("ascending".equals(isAsc)){
                isAsc = "asc";
            }
            else if("descending".equals(isAsc)){
                isAsc = "desc";
            }
        }
        map.put("pageNum",pageNum);
        map.put("pageSize",pageSize);
        map.put("isAsc",isAsc);
        map.put("reasonable",reasonable);
        map.put("orderBy",orderBy);
        return BeanUtil.fillBeanWithMap(map,new PageData(),false);
    }
}
