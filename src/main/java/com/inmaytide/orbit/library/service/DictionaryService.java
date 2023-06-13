package com.inmaytide.orbit.library.service;

import com.inmaytide.orbit.library.domain.DictionaryCategory;

import java.util.List;

/**
 * @author inmaytide
 * @since 2023/6/13
 */
public interface DictionaryService {

    List<DictionaryCategory> findAllCategories();

}
