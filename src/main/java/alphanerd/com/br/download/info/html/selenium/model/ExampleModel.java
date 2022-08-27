package alphanerd.com.br.download.info.html.selenium.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Builder
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
public @Data class ExampleModel implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String name;
	private String cnpj;
	private String link;
	private String manager;
	private String managerCnpj;
	private String description;
	
	public String getName() {
		return name;
	}
	
	public String getCnpj() {
		return cnpj;
	}
	
	public String getLink() {
		return link;
	}
	
	public String getManager() {
		return manager;
	}
	
	public String getManagerCnpj() {
		return managerCnpj;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	
	public void setLink(String link) {
		this.link = link;
	}
	
	public void setManager(String manager) {
		this.manager = manager;
	}
	
	public void setManagerCnpj(String managerCnpj) {
		this.managerCnpj = managerCnpj;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
}
