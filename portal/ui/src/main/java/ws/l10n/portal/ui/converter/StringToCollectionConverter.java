package ws.l10n.portal.ui.converter;

import com.google.common.collect.ImmutableSet;
import org.springframework.core.CollectionFactory;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.GenericConverter;

import java.util.Collection;
import java.util.Set;

public class StringToCollectionConverter implements GenericConverter {

    private static final int INITIAL_CAPACITY = 1;

    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        return ImmutableSet.<ConvertiblePair>builder()
                .add(new ConvertiblePair(String.class, Collection.class))
                .build();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
        if (source == null) {
            return null;
        }
        String string = (String) source;

        Collection<Object> target = CollectionFactory.createCollection(targetType.getType(), INITIAL_CAPACITY);
        target.add(string);

        return target;
    }
}