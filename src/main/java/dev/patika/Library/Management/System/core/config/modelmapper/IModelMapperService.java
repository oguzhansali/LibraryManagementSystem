package dev.patika.Library.Management.System.core.config.modelmapper;

import org.modelmapper.ModelMapper;

public interface IModelMapperService {
    ModelMapper forResponse();
    ModelMapper forRequest();

}
