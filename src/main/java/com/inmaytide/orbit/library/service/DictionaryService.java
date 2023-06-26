package com.inmaytide.orbit.library.service;

import com.inmaytide.orbit.commons.business.BasicService;
import com.inmaytide.orbit.commons.consts.Sharing;
import com.inmaytide.orbit.commons.domain.GlobalUser;
import com.inmaytide.orbit.commons.domain.dto.result.TreeNode;
import com.inmaytide.orbit.library.domain.Dictionary;
import com.inmaytide.orbit.library.domain.DictionaryCategory;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author inmaytide
 * @since 2023/6/13
 */
public interface DictionaryService extends BasicService<Dictionary> {

    String TREE_SYMBOL = "dictionary";

    List<DictionaryCategory> findAllCategories();

    Map<String, String> findNamesByCodes(String codes);

    List<TreeNode<Dictionary>> getTreeOfDictionaries(String category);

    default boolean hasAuthority(Dictionary dictionary, GlobalUser operator) {
        if (dictionary.getSharing() == Sharing.PRIVATE) {
            return Objects.equals(operator.getId(), dictionary.getCreatedBy());
        }
        if (dictionary.getSharing() == Sharing.ORGANIZATION) {
            return operator.getOrganizations().contains(dictionary.getOrganization());
        }
        if (dictionary.getSharing() == Sharing.TENANT) {
            return Objects.equals(operator.getTenantId(), dictionary.getTenant());
        }
        if (dictionary.getSharing() == Sharing.AREA) {
            return operator.getAreas().contains(dictionary.getArea());
        }
        return dictionary.getSharing() == Sharing.GENERIC;
    }

}
