package br.com.mp.livro.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import br.com.mp.livro.hq.model.HQ;
import br.com.mp.livro.hq.repository.HQs;

@FacesConverter(forClass = HQ.class, value = "HQConverterPickDualList")
public class HQConverterPickDualList implements Converter {
	@Inject // funciona graças ao OmniFaces
	private HQs hqs;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		HQ retorno = null;
		if (value != null) {
			retorno = this.hqs.porId(new Long(value));
		}
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			HQ hq = ((HQ) value);
			return hq.getId() == null ? null : hq.getId().toString();
		}
		return null;
	}

}
