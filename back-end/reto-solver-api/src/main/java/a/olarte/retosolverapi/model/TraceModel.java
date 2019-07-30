package a.olarte.retosolverapi.model;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class TraceModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(insertable = false, updatable = false,columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@Temporal(TemporalType.TIMESTAMP)
	private Date executionDate;
	
	@Column(columnDefinition = "VARCHAR(20)")
	private String documentNumber;
	
	@Column(columnDefinition = "VARCHAR(150)")
	private String fileInputUrl;
	
	private String msg;

	public TraceModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TraceModel(String documentNumber, String fileInputUrl) {
		super();
		this.documentNumber = documentNumber;
		this.fileInputUrl = fileInputUrl;
	}


	public String getDocumentNumber() {
		return documentNumber;
	}

	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}

	public String getFileInputUrl() {
		return fileInputUrl;
	}

	public void setFileInputUrl(String fileInputUrl) {
		this.fileInputUrl = fileInputUrl;
	}

	public Integer getId() {
		return id;
	}

	public Date getExecutionDate() {
		return executionDate;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
	
}
