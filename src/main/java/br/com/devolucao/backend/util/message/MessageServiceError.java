package br.com.devolucao.backend.util.message;

import java.io.Serializable;

/**
 * Classe que encapsula menssagem de erro.
 */
public class MessageServiceError implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String message;
	private Integer code;
	private String field;
	
	public MessageServiceError() {
	}
	
	public MessageServiceError(String message, String field) {
		this.message = message;
		this.field = field;
	}

	public MessageServiceError(String message, String field, Integer code) {
		this.message = message;
		this.field = field;
		this.code = code;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public Integer getCode() {
		return code;
	}
	
	public void setCode(Integer code) {
		this.code = code;
	}
	
	public String getField() {
		return field;
	}
	
	public void setField(String field) {
		this.field = field;
	}	
}
