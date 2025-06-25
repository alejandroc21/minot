package com.alejandroct.minot_api.folder.controller;

import com.alejandroct.minot_api.folder.dto.FolderDTO;
import com.alejandroct.minot_api.folder.service.IFolderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/folders")
@RequiredArgsConstructor
public class FolderController {
    private final IFolderService folderService;

    @GetMapping
    public ResponseEntity<Page<FolderDTO>> listFolders(@RequestParam(name = "parentId",required = false) Long parentId, Pageable pageable, Principal principal){
        return ResponseEntity.ok(this.folderService.listFolders(parentId, pageable, principal.getName()));
    }

    @PostMapping
    public ResponseEntity<FolderDTO> save(@RequestBody FolderDTO folderDTO, Principal principal){
        return new ResponseEntity<>(this.folderService.save(folderDTO, principal.getName()), HttpStatus.CREATED);
    }


}
