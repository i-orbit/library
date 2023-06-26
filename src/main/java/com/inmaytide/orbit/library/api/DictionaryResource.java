package com.inmaytide.orbit.library.api;

import com.inmaytide.orbit.commons.domain.dto.result.TreeNode;
import com.inmaytide.orbit.commons.log.annotation.OperationLogging;
import com.inmaytide.orbit.library.domain.Dictionary;
import com.inmaytide.orbit.library.domain.DictionaryCategory;
import com.inmaytide.orbit.library.service.DictionaryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author inmaytide
 * @since 2023/6/16
 */
@Tag(name = "数据字典")
@RestController
@RequestMapping("/api/dictionaries")
public class DictionaryResource {

    private final DictionaryService service;

    public DictionaryResource(DictionaryService service) {
        this.service = service;
    }

    @OperationLogging
    @GetMapping("categories")
    @Operation(summary = "查询系统所有的可用数据字典类型")
    public List<DictionaryCategory> findAllCategories() {
        return service.findAllCategories();
    }

    @GetMapping
    @OperationLogging
    @Operation(summary = "查询指定类型的数据字典信息树")
    public List<TreeNode<Dictionary>> getTreeOfDictionaries(@Parameter(name = "指定类型编码") @RequestParam String category) {
        return service.getTreeOfDictionaries(category);
    }

}
