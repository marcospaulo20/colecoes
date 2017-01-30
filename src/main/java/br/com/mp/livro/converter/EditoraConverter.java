package br.com.mp.livro.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import br.com.mp.livro.model.Editora;
import br.com.mp.livro.repository.Editoras;

@FacesConverter(forClass = Editora.class)
public class EditoraConverter implements Converter {
	@Inject // funciona graças ao OmniFaces
	private Editoras editoras;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Editora retorno = null;
		if (value != null) {
			retorno = this.editoras.porId(new Long(value));
		}
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			return ((Editora) value).getId().toString();
		}
		return null;
	}

}
