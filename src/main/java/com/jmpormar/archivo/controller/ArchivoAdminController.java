package com.jmpormar.archivo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.jmpormar.archivo.dto.ArchivoResponse;
import com.jmpormar.archivo.service.ArchivoService;
import com.jmpormar.shared.api.ApiResponse;

import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;

	@RestController
	@RequestMapping("/api/admin/archivos")
	@RequiredArgsConstructor
	@Validated
	public class ArchivoAdminController {

	    private final ArchivoService service;

	    @PostMapping(
	            value = "/{carpeta}",
	            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
	    )
	    public ResponseEntity<ApiResponse<ArchivoResponse>> cargar(
	            @PathVariable
	            @Pattern(
	                    regexp = "productos|servicios|certificaciones",
	                    message = "La carpeta de archivos no es válida"
	            )
	            String carpeta,

	            @RequestPart("file")
	            MultipartFile file
	    ) {
	        ArchivoResponse archivo =
	                service.guardar(
	                        carpeta,
	                        file
	                );

	        return ResponseEntity
	                .status(HttpStatus.CREATED)
	                .body(
	                        ApiResponse.ok(
	                                "Archivo cargado",
	                                archivo
	                        )
	                );
	    }

	    @DeleteMapping("/cloudinary")
	    public ResponseEntity<ApiResponse<Object>>
	    eliminarCloudinary(
	            @RequestParam
	            String publicId,

	            @RequestParam(
	                    defaultValue = "image"
	            )
	            String resourceType
	    ) {
	        service.eliminarCloudinary(
	                publicId,
	                resourceType
	        );

	        return ResponseEntity.ok(
	                ApiResponse.ok(
	                        "Archivo eliminado",
	                        null
	                )
	        );
	    }
	}	
    

