package ws.l10n.portal.domain;

/**
 * @author Sergey Boguckiy
 * @author Anton Mokshyn
 */
public class Message {

    private Integer id;
    private Integer versionId;
    private String localeId;
    private String key;
    private String value;

    public Message() {
    }

    public Message(Integer id, Integer versionId, String key, String value, String localeId) {
        this.id = id;
        this.versionId = versionId;
        this.localeId = localeId;
        this.key = key;
        this.value = value;
    }


    public Integer getId() {
        return id;
    }

    public Integer getVersionId() {
        return versionId;
    }

    public String getLocaleId() {
        return localeId;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Integer id;
        private Integer versionId;
        private String localeId;
        private String key;
        private String value;

        public Builder setId(Integer id) {
            this.id = id;
            return this;
        }

        public Builder setVersionId(Integer versionId) {
            this.versionId = versionId;
            return this;
        }

        public Builder setLocaleId(String localeId) {
            this.localeId = localeId;
            return this;
        }

        public Builder setKey(String key) {
            this.key = key;
            return this;
        }

        public Builder setValue(String value) {
            this.value = value;
            return this;
        }

        public Builder from(Message source) {
            this.id = source.id;
            this.versionId = source.versionId;
            this.localeId = source.localeId;
            this.key = source.key;
            this.value = source.value;
            return this;
        }

        public Message build() {
            return new Message(id, versionId, key, value, localeId);
        }
    }

}
