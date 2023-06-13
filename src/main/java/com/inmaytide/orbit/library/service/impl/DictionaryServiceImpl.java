package com.inmaytide.orbit.library.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.inmaytide.orbit.commons.consts.Is;
import com.inmaytide.orbit.library.domain.DictionaryCategory;
import com.inmaytide.orbit.library.mapper.DictionaryCategoryMapper;
import com.inmaytide.orbit.library.service.DictionaryService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author inmaytide
 * @since 2023/6/13
 */
@Service
public class DictionaryServiceImpl implements DictionaryService {

    private final DictionaryCategoryMapper categoryMapper;

    public DictionaryServiceImpl(DictionaryCategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    @Override
    public List<DictionaryCategory> findAllCategories() {
        LambdaQueryWrapper<DictionaryCategory> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DictionaryCategory::getVisible, Is.Y.name());
        wrapper.orderByAsc(DictionaryCategory::getSequence);
        return categoryMapper.selectList(wrapper);
    }
}
