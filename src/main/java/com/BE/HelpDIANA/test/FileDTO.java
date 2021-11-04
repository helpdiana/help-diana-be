package com.BE.HelpDIANA.test;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonAutoDetect
public class FileDTO {
    private String fileName;
    private String fileBase64;
}
