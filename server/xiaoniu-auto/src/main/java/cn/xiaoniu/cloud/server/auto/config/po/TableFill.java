package cn.xiaoniu.cloud.server.auto.config.po;

import com.baomidou.mybatisplus.enums.FieldFill;


public class TableFill {
    private String fieldName;
    private FieldFill fieldFill;

    private TableFill() {
    }

    public TableFill(String fieldName, FieldFill ignore) {
        this.fieldName = fieldName;
        this.fieldFill = ignore;
    }


    public String getFieldName() {
        return this.fieldName;
    }


    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }


    public FieldFill getFieldFill() {
        return this.fieldFill;
    }


    public void setFieldFill(FieldFill fieldFill) {
        this.fieldFill = fieldFill;
    }
}
