package ws.l10n.portal.ui.converter;

import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.GenericConverter;

import java.lang.reflect.Array;
import java.util.Collections;
import java.util.Set;

public class StringToArrayConverter implements GenericConverter {

	private static final int INITIAL_CAPACITY = 1;

	@Override
	public Set<ConvertiblePair> getConvertibleTypes() {
		return Collections.singleton(new ConvertiblePair(String.class, String[].class));
	}


	@Override
	public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
		if (source == null) {
			return null;
		}
		String string = (String) source;
		Object target = Array.newInstance(targetType.getElementTypeDescriptor().getType(), INITIAL_CAPACITY);
		Array.set(target, 0, string);
		return target;
	}

}