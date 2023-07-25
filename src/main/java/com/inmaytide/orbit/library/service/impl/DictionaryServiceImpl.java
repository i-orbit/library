package com.inmaytide.orbit.library.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.inmaytide.orbit.commons.consts.Is;
import com.inmaytide.orbit.commons.consts.Marks;
import com.inmaytide.orbit.commons.domain.GlobalUser;
import com.inmaytide.orbit.commons.domain.dto.result.TreeNode;
import com.inmaytide.orbit.commons.security.SecurityUtils;
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
public class DictionaryServiceImpl extends ServiceImpl<DictionaryMapper, Dictionary> implements DictionaryService {

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

    @Override
    public Map<String, String> findNamesByCodes(String codes) {
        List<String> c = CommonUtils.splitByCommas(codes).stream().distinct().toList();
        if (c.isEmpty()) {
            return Collections.emptyMap();
        }
        LambdaQueryWrapper<Dictionary> wrapper = Wrappers.lambdaQuery(Dictionary.class)
                .select(Dictionary::getCode, Dictionary::getName)
                .in(Dictionary::getCode, codes);
        return getBaseMapper().selectList(wrapper).stream().collect(Collectors.toMap(Dictionary::getCode, Dictionary::getName));
    }

    @Override
    public List<TreeNode<Dictionary>> getTreeOfDictionaries(String category) {
        GlobalUser operator = SecurityUtils.getAuthorizedUser();
        LambdaQueryWrapper<Dictionary> wrapper = Wrappers.lambdaQuery(Dictionary.class)
                .eq(Dictionary::getCategory, category)
                .orderByAsc(Dictionary::getSequence);
        Map<String, List<Dictionary>> dictionaries = baseMapper.selectList(wrapper)
                .stream()
                .filter(d -> hasAuthority(d, operator))
                .collect(Collectors.groupingBy(Dictionary::getParent, Collectors.toList()));
        return dictionaries.getOrDefault(Marks.TREE_ROOT.getValue(), Collections.emptyList())
                .stream()
                .map(e -> transfer(e, dictionaries, 1))
                .collect(Collectors.toList());
    }

    private TreeNode<Dictionary> transfer(Dictionary entity, Map<String, List<Dictionary>> all, int level) {
        TreeNode<Dictionary> node = new TreeNode<>(entity);
        node.setId(entity.getId());
        node.setName(entity.getName());
        node.setChildren(all.getOrDefault(entity.getCode(), Collections.emptyList()).stream().map(e -> transfer(e, all, level + 1)).collect(Collectors.toList()));
        node.setAuthorized(true);
        node.setParent(entity.getCode());
        node.setSymbol(TREE_SYMBOL);
        node.setLevel(level);
        return node;
    }

}
