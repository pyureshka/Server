package ru.bgpu.server.dto;

import java.io.File;

public class FileInfoDto {
    private String name;
    private long size;

    public FileInfoDto(File file) {
        this.name = file.getName();
        this.size = file.length();
    }
}
