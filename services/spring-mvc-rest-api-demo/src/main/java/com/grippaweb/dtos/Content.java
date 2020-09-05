package com.grippaweb.dtos;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "Rappresenta il modello di oggetto utilizzato per la response delle apis",discriminator ="id")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Content implements Serializable {
	private static final long serialVersionUID = 2174947401248457687L;
	
	@ApiModelProperty(access = "public",
		          dataType = "long",
		          required = true,
		          name = "id", 
		          notes = "id univoco dell'operazione eseguita in output dall'api")
	private long id;
	
	@ApiModelProperty(access = "public",
	          dataType = "String",
	          required = false,
	          name = "description", 
	          notes = "Messaggio descrittivo che riguarda l'operazione svolta")
	private String description;
	
	@ApiModelProperty(access = "public",
	          dataType = "int",
	          required = true,
	          name = "status", 
	          notes = "stato della risposta")
	private int status;
	
	@ApiModelProperty(access = "public",
	          dataType = "String",
	          required = false,
	          name = "error", 
	          notes = "messaggio di errore ottenuto dalla risposta")
	private String error;
	
	@ApiModelProperty(access = "public",
	          dataType = "Object",
	          required = false,
	          name = "data", 
	          notes = "Dati restituiti dalla risposta")
	private Object data;

	public Content() {}

	public Content(long id, String description, int status, String error, Object data) {
		this.id = id;
		this.description = description;
		this.status = status;
		this.error = error;
		this.data = data;
	}
}
