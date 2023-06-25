package com.inmaytide.orbit.library.service;

import com.inmaytide.orbit.commons.business.BasicService;
import com.inmaytide.orbit.commons.domain.dto.result.TreeNode;
import com.inmaytide.orbit.library.domain.Dictionary;
import com.inmaytide.orbit.library.domain.DictionaryCategory;

import java.util.List;
import java.util.Map;

/**
 * @author inmaytide
 * @since 2023/6/13
 */
public interface DictionaryService extends BasicService<Dictionary> {

    List<DictionaryCategory> findAllCategories();

    Map<String, String> findNamesByCodes(String codes);

    List<TreeNode<Dictionary>> findDictionaries(String category);
}
