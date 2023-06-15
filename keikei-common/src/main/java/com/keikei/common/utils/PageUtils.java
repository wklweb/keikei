package com.keikei.common.utils;

import com.github.pagehelper.PageHelper;
import com.keikei.common.constants.HttpStatus;
import com.keikei.common.core.page.PageData;
import com.keikei.common.core.page.PageDateSupport;
import com.keikei.common.domain.model.TableInfo;

import java.util.List;

public class PageUtils {

    public static void startPage(){
        PageData pageData = PageDateSupport.getPageDataByRequest();
        PageHelper.startPage(pageData.getPageNum(),pageData.getPageSize(),pageData.getOrderBy()).setReasonable(
                pageData.getReasonable()
        );
    }
    public static TableInfo getTableInfo(List<?> data){
        TableInfo tableInfo = new TableInfo();
        tableInfo.setCode(HttpStatus.success);
        tableInfo.setData(data);
        tableInfo.setTotal(data.size());
        return tableInfo;
    }
}
