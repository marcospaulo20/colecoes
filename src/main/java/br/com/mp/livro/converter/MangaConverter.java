package br.com.mp.livro.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import br.com.mp.livro.manga.model.Manga;
import br.com.mp.livro.manga.repository.Mangas;

@FacesConverter(forClass = Manga.class)
public class MangaConverter implements Converter {
	@Inject // funciona graças ao OmniFaces
	private Mangas mangas;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Manga retorno = null;
		if (value != null) {
			retorno = this.mangas.porId(new Long(value));
		}
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Manga manga = ((Manga) value);
			return manga.getId() == null ? null : manga.getId().toString();
		}
		return null;
	}

}
