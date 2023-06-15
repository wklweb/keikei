package com.keikei.common.domain.model;

import java.io.Serializable;
import java.util.List;

public class TableInfo<T> implements Serializable {

    private Integer code;
    private List<T> data;
    private Integer total;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
