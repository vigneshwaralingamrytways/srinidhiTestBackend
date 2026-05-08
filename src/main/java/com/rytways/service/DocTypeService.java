package com.rytways.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rytways.dto.LoadOptionsDto;
import com.rytways.model.DocumentTypeMaster;
 
import com.rytways.repository.DocumentTypeRepo;
import com.rytways.specifications.DocumentTypeSpecification;
 

@Component
@Service
@Transactional
public class DocTypeService {
	
	@Autowired
	private DocumentTypeRepo documentTypeRepo;

	public DocumentTypeMaster createType(DocumentTypeMaster doc) {
		 
		doc=documentTypeRepo.save(doc);
		return doc;
	}

	public List<LoadOptionsDto> loadType() {
		 
		List<DocumentTypeMaster> typeList = documentTypeRepo.findAll();
		List<LoadOptionsDto> dtoList = new ArrayList<>();
		 if (!typeList.isEmpty()) {
			   for (DocumentTypeMaster typeMaster : typeList) {
				   LoadOptionsDto loadDto = new LoadOptionsDto(); // Move inside the loop

		            loadDto.setLabel(typeMaster.getDocumentType());
		            loadDto.setValue(typeMaster.getDocumentTypeId());

		            dtoList.add(loadDto); // Add each instance to the list
		        }
		 }
		return dtoList;
	}

	public void deleteDocType(Long documentTypeId) throws Exception {
		
		System.out.println("print =================>" + documentTypeId);
		
	    if (documentTypeId != null) {
	        // Check if the record exists
	    	
	    	
	    	DocumentTypeMaster documentTypeMaster = documentTypeRepo.findById(documentTypeId)
	                .orElseThrow(() -> new Exception("Document Type with ID " + documentTypeId + " not found."));
	        
	        // Ensure no dependent records exist (if necessary)
	       

	        // Delete the DocumentUserMaster record
	    	documentTypeRepo.deleteById(documentTypeId);
	    } else {
	        throw new Exception("Document Type Id cannot be null.");
	    }
	}

	public List<DocumentTypeMaster> searchDocTypes(DocumentTypeMaster documentType) {
		Sort sort = Sort.by(Sort.Direction.ASC, "documentTypeId");
		List<Specification<DocumentTypeMaster>> specs = new ArrayList<>();
		if (documentType.getDocumentType() != null) {
			specs.add(DocumentTypeSpecification.documentTypeName(documentType.getDocumentType()));
		}if (documentType.getStatus() != null && !documentType.getStatus().isEmpty()) {
			specs.add(DocumentTypeSpecification.status(documentType.getStatus()));
		}

		Specification<DocumentTypeMaster> combinedSpec = specs.stream().reduce(Specification::and).orElse(null);

		return documentTypeRepo.findAll(combinedSpec, sort);
	}
}
