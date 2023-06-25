package com.inmaytide.orbit.library.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.inmaytide.orbit.commons.consts.Is;
import com.inmaytide.orbit.commons.domain.dto.result.TreeNode;
import com.inmaytide.orbit.commons.utils.CommonUtils;
import com.inmaytide.orbit.library.domain.Dictionary;
import com.inmaytide.orbit.library.domain.DictionaryCategory;
import com.inmaytide.orbit.library.mapper.DictionaryCategoryMapper;
import com.inmaytide.orbit.library.mapper.DictionaryMapper;
import com.inmaytide.orbit.library.service.DictionaryService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author inmaytide
 * @since 2023/6/13
 */
@Service
public class DictionaryServiceImpl implements DictionaryService {

    private final DictionaryMapper mapper;

    private final DictionaryCategoryMapper categoryMapper;

    public DictionaryServiceImpl(DictionaryMapper mapper, DictionaryCategoryMapper categoryMapper) {
        this.mapper = mapper;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public List<DictionaryCategory> findAllCategories() {
        LambdaQueryWrapper<DictionaryCategory> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DictionaryCategory::getVisible, Is.Y.name());
        wrapper.orderByAsc(DictionaryCategory::getSequence);
        return categoryMapper.selectList(wrapper);
    }

    @Override
    public Map<String, String> findNamesByCodes(String codes) {
        List<String> c = CommonUtils.splitByCommas(codes).stream().distinct().toList();
        if (c.isEmpty()) {
            return Collections.emptyMap();
        }
        LambdaQueryWrapper<Dictionary> wrapper = Wrappers.lambdaQuery(Dictionary.class)
                .select(Dictionary::getCode, Dictionary::getName)
                .in(Dictionary::getCode, codes);
        return getMapper().selectList(wrapper).stream().collect(Collectors.toMap(Dictionary::getCode, Dictionary::getName));
    }

    @Override
    public List<TreeNode<Dictionary>> findDictionaries(String category) {
        return null;
    }

    @Override
    public BaseMapper<Dictionary> getMapper() {
        return mapper;
    }

    @Override
    public Class<Dictionary> getEntityClass() {
        return Dictionary.class;
    }
}
