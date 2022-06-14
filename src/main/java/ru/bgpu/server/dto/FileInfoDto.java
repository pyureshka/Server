package ru.bgpu.server.dto;

import java.io.File;

public class FileInfoDto {
    private String name;
    private double size;

    public FileInfoDto(File file) {
        this.name = file.getName();
        this.size = file.length();
    }
}
