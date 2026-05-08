package com.rytways.specifications;

import java.time.LocalDate;

import org.springframework.data.jpa.domain.Specification;

import com.rytways.model.DocumentTransaction;

public class DocTransacSpec {
	
	public static Specification<DocumentTransaction> createdDateFromDate(LocalDate createdDate) {
        return (root, query, builder) ->
        createdDate == null ?
                        builder.conjunction() :
                        builder.greaterThanOrEqualTo(root.get("createdDate"), createdDate);
    }

    public static Specification<DocumentTransaction> createdDateTillDate(LocalDate createdDate) {
        return (root, query, builder) ->
        createdDate == null ?
                        builder.conjunction() :
                        builder.lessThanOrEqualTo(root.get("createdDate"), createdDate);
    }
    
    
    
    public static Specification<DocumentTransaction> docTypeIdEqual(Long documentTypeId) {
        return (root, query, builder) -> 
            documentTypeId == null ? 
                builder.conjunction() :
                builder.equal(root.get("documentTypeId"), documentTypeId);
    }
    
    public static Specification<DocumentTransaction> folderIdEqual(Long folderId) {
        return (root, query, builder) -> 
            folderId == null ? 
                builder.conjunction() :
                builder.equal(root.get("folderId"), folderId);
    }
    
    public static Specification<DocumentTransaction> subFolderIdEqual(Long subFolderId) {
        return (root, query, builder) -> 
            subFolderId == null ? 
                builder.conjunction() :
                builder.equal(root.get("subFolderId"), subFolderId);
    }
}
