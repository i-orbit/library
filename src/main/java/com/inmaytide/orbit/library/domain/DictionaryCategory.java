package com.inmaytide.orbit.library.domain;

import com.inmaytide.orbit.commons.consts.Is;
import com.inmaytide.orbit.commons.domain.pattern.Entity;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @author inmaytide
 * @since 2023/6/13
 */
@Schema(title = "数据字典类型", accessMode = Schema.AccessMode.READ_ONLY, description = "不提供修改编辑接口")
public class DictionaryCategory extends Entity {

    @Schema(title = "类型编码", description = "系统全局唯一")
    private String code;

    @Schema(title = "类型名称")
    private String name;

    @Schema(title = "数据字典项作为树形结构时最大层级", description = "等于 -1 时表示无限制", defaultValue = "0")
    private Integer maxLevel;

    @Schema(title = "字典内容允许用户编辑修改")
    private Is allowModify;

    @Schema(title = "是否可见", description = "预留字段，字典相关系统模块未使用时，将无用字典类型隐藏")
    private Is visible;

    @Schema(title = "排序字段")
    private Integer sequence;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMaxLevel() {
        return maxLevel;
    }

    public void setMaxLevel(Integer maxLevel) {
        this.maxLevel = maxLevel;
    }

    public Is getAllowModify() {
        return allowModify;
    }

    public void setAllowModify(Is allowModify) {
        this.allowModify = allowModify;
    }

    public Is getVisible() {
        return visible;
    }

    public void setVisible(Is visible) {
        this.visible = visible;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }
}
