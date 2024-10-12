package com.jian.family.business.attachment.repository.specification;

import com.jian.family.business.attachment.entity.AttachmentEntity;
import com.jian.family.business.attachment.entity.AttachmentEntity_;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.domain.Specification;

/**
 * 附件条件查询
 */
public class AttachmentConditionSpecification implements Specification<AttachmentEntity> {

    private final String name;

    public AttachmentConditionSpecification(String name) {
        this.name = name;
    }


    @Override
    public Predicate toPredicate(@NotNull Root<AttachmentEntity> root,
                                 CriteriaQuery<?> query,
                                 @NotNull CriteriaBuilder builder) {

        return builder.like(root.get(AttachmentEntity_.name), "%" + name + "%");
    }
}
