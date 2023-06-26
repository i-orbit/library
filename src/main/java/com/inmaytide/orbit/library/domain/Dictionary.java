package com.inmaytide.orbit.library.domain;

import com.inmaytide.orbit.commons.consts.Sharing;
import com.inmaytide.orbit.commons.consts.Source;
import com.inmaytide.orbit.commons.domain.pattern.TombstoneEntity;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @author inmaytide
 * @since 2023/6/13
 */
@Schema(title = "数据字典项")
public class Dictionary extends TombstoneEntity {

    @Schema(title = "数据字典项所属类别编码")
    private String category;

    @Schema(title = "数据字典项编码", description = "系统全局唯一")
    private String code;

    @Schema(title = "数据字典项名称")
    private String name;

    @Schema(title = "数据来源", description = "详见相关枚举类")
    private Source source;

    @Schema(title = "树结构数据字典项，上级字典项编码", description = "根目录值为\"root\"")
    private String parent;

    @Schema(title = "选项值", description = "字典作为SELECT下拉框选项时, 选项的值. 不填默认等于数据字典项编码")
    private String optionValue;

    @Schema(title = "共享级别", description = "详见相关枚举类")
    private Sharing sharing;

    @Schema(title = "所属租户")
    private Long tenant;

    @Schema(title = "所属组织")
    private Long organization;

    @Schema(title = "所属区域")
    private Long area;

    @Schema(title = "排序字段")
    private Integer sequence;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

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

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getOptionValue() {
        return optionValue;
    }

    public void setOptionValue(String optionValue) {
        this.optionValue = optionValue;
    }

    public Sharing getSharing() {
        return sharing;
    }

    public void setSharing(Sharing sharing) {
        this.sharing = sharing;
    }

    public Long getTenant() {
        return tenant;
    }

    public void setTenant(Long tenant) {
        this.tenant = tenant;
    }

    public Long getOrganization() {
        return organization;
    }

    public void setOrganization(Long organization) {
        this.organization = organization;
    }

    public Long getArea() {
        return area;
    }

    public void setArea(Long area) {
        this.area = area;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }
}
