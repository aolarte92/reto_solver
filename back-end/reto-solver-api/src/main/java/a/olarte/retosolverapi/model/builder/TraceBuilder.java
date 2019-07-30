package a.olarte.retosolverapi.model.builder;

import com.google.common.base.Preconditions;

import a.olarte.retosolverapi.enumerated.ErrorResult;
import a.olarte.retosolverapi.helper.BussinessException;
import a.olarte.retosolverapi.model.TraceModel;

public class TraceBuilder extends AbstractBuilder<TraceModel> {

	public TraceBuilder() {
		super(new TraceModel());
	}

	public TraceBuilder setDocumentNumber(String doc) {
		instance.setDocumentNumber(doc);
		return this;
	}
	
	public TraceBuilder setFileInputUrl(String fileUrl) {
		instance.setFileInputUrl(fileUrl);
		return this;
	}
	
	public TraceBuilder setMsg(String msg) {
		instance.setMsg(msg);
		return this;
	}

	@Override
	public void validate() {
		Preconditions.checkNotNull(instance.getDocumentNumber(), new BussinessException(String.format(ErrorResult.NULL_RESULT.getMessage(), "Número de documento")));
		Preconditions.checkArgument(!instance.getDocumentNumber().isEmpty(), new BussinessException(String.format(ErrorResult.EMPTY_RESULT.getMessage(), "Número de documento")));
		Preconditions.checkArgument(instance.getDocumentNumber().length() <= 20, new BussinessException(String.format(ErrorResult.INVALID_LENGTH.getMessage(), "Número de documento")));
		Preconditions.checkNotNull(instance.getFileInputUrl(), new BussinessException(String.format(ErrorResult.NULL_RESULT.getMessage(), "Archivo de entrada")));
		
	}

	@Override
	public void inject() {
		instance.setMsg((instance.getMsg().isEmpty())?"OK":instance.getMsg());
		
	}

	
}
