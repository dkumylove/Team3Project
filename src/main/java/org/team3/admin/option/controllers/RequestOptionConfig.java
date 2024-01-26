package org.team3.admin.option.controllers;

import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.team3.file.entities.FileInfo;

import java.util.List;
import java.util.UUID;

@Data
public class RequestOptionConfig {

    private String mode = "add";

    @NotBlank
    private String options; // 옵션 name

}
