package com.czertainly.api.model.common.attribute.common;


import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@JsonInclude(JsonInclude.Include.ALWAYS)
@Schema(implementation = BaseAttributeDto.class)
public abstract class BaseAttribute extends AbstractBaseAttribute implements BaseAttributeDto {

}