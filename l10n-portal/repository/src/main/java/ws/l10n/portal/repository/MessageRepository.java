package ws.l10n.portal.repository;

import org.apache.ibatis.annotations.Param;
import ws.l10n.portal.domain.Message;

import java.util.Collection;
import java.util.List;

/**
 * @author Anton Mokshyn
 */
public interface MessageRepository {
    void save(Message message);

    void saveAll(List<Message> messages);

    Message find(@Param("key") String key, @Param("localeId") String localeId, @Param("versionId") Integer versionId);

    boolean exists(@Param("key") String key, @Param("localeId") String localeId, @Param("versionId") Integer versionId);

    List<Message> findAll(@Param("versionId") Integer versionId);

    void deleteAll(@Param("versionId") Integer versionId, @Param("keys") List<String> keys);

    void deleteAllByLocaleId(@Param("versionId") Integer versionId, @Param("localeId") String localeId, @Param("keys") List<String> keys);

    List<Message> findByVersionIdAndLocaleId(@Param("versionId") Integer versionId, @Param("localeId") String localeId);

    List<Message> findByVersionIdAndKey(@Param("versionId") Integer versionId, @Param("key") String key);

    int updateKey(@Param("versionId") Integer versionId, @Param("key") String key, @Param("messageIds") Collection<Integer> messageIds);

    int updateValue(@Param("versionId") Integer versionId, @Param("value") String value, @Param("messageId") Integer messageId);

    void copyMessages(@Param("localeFrom") String localeFrom, @Param("localeTo") String localeTo, @Param("versionId") Integer versionId);

    void createVersion(@Param("versionId") Integer versionId, @Param("fromVersionId") Integer fromVersionId);

    void deleteByVersionId(@Param("versionId") Integer versionId);

    void deleteByVersionIdAndLocaleId(@Param("versionId") Integer versionId, @Param("localeId") String localeId);
}
