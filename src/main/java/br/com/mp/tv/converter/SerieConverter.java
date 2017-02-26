package br.com.mp.tv.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import br.com.mp.tv.serie.model.Serie;
import br.com.mp.tv.serie.repository.Series;

@FacesConverter(forClass = Serie.class)
public class SerieConverter implements Converter {

	@Inject // funciona graças ao OmniFaces
	private Series series;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Serie retorno = null;
		if (value != null) {
			retorno = this.series.porId(new Long(value));
		}
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Serie serie = ((Serie) value);
			return serie.getId() == null ? null : serie.getId().toString();
		}
		return null;
	}

}