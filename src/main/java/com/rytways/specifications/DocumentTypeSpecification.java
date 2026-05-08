package com.rytways.specifications;

import org.springframework.data.jpa.domain.Specification;

import com.rytways.model.DocumentTypeMaster;

public class DocumentTypeSpecification {

	public static Specification<DocumentTypeMaster> documentTypeName(String documentType) {
		return (root, query, builder) -> documentType == null || documentType.isEmpty() ? builder.conjunction()
				: builder.like(root.get("documentType"), "%" + documentType+ "%");
	}

	public static Specification<DocumentTypeMaster> status(String status) {
		return (root, query, builder) -> status == null || status.isEmpty() ? builder.conjunction()
				: builder.equal(root.get("status"),status);
	}
}
