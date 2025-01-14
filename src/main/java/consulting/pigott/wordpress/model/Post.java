package consulting.pigott.wordpress.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    private final static String TAG_TAXONOMY = "post_tag";

    @JsonProperty("date")
    @JsonView({JsonViews.Read.class, JsonViews.Edit.class})
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Calendar date;

    @JsonProperty("date_gmt")
    @JsonView({JsonViews.Read.class, JsonViews.Edit.class})
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Calendar dateGmt;

    @JsonProperty("id")
    @JsonView({JsonViews.Read.class, JsonViews.Edit.class})
    private Integer id;

    @JsonProperty(value = "guid", access = JsonProperty.Access.WRITE_ONLY)
    @JsonView({JsonViews.Read.class, JsonViews.Edit.class})
    private RenderedObj guid;

    @JsonProperty("link")
    @JsonView({JsonViews.Read.class, JsonViews.Edit.class})
    private String link;

    @JsonProperty("modified")
    @JsonView({JsonViews.Read.class, JsonViews.Edit.class})
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Calendar modified;

    @JsonProperty("modified_gmt")
    @JsonView({JsonViews.Read.class, JsonViews.Edit.class})
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Calendar modifiedGmt;

    @JsonProperty("slug")
    @JsonView({JsonViews.Read.class, JsonViews.Edit.class})
    private String slug;

    @JsonProperty("status")
    @JsonView({JsonViews.Read.class, JsonViews.Edit.class})
    private String status;

    @JsonProperty("type")
    @JsonView({JsonViews.Read.class, JsonViews.Edit.class})
    private String type;

    @JsonProperty("password")
    @JsonView({JsonViews.Edit.class})
    private String password;

    @JsonProperty("permalink_template")
    @JsonView({JsonViews.Edit.class})
    private String permalinkTemplate;

    @JsonProperty("generated_slug")
    @JsonView({JsonViews.Edit.class})
    private String generatedSlug;

    @JsonProperty(value = "title", access = JsonProperty.Access.WRITE_ONLY)
    @JsonView({JsonViews.Read.class, JsonViews.Edit.class})
    private RenderedObj title;

    @JsonProperty(value = "content", access = JsonProperty.Access.WRITE_ONLY)
    @JsonView({JsonViews.Read.class, JsonViews.Edit.class})
    private RenderedAndProtectedObj content;

    @JsonProperty("author")
    @JsonView({JsonViews.Read.class, JsonViews.Edit.class})
    private Integer author;

    @JsonProperty(value = "excerpt", access = JsonProperty.Access.WRITE_ONLY)
    @JsonView({JsonViews.Read.class, JsonViews.Edit.class})
    private RenderedAndProtectedObj excerpt;

    @JsonProperty("featured_media")
    @JsonView({JsonViews.Read.class, JsonViews.Edit.class})
    private Integer featuredMedia;

    @JsonProperty("comment_status")
    @JsonView({JsonViews.Read.class, JsonViews.Edit.class})
    private CommentStatus commentStatus;

    @JsonProperty("ping_status")
    @JsonView({JsonViews.Read.class, JsonViews.Edit.class})
    private PingStatus pingStatus;

    @JsonProperty("format")
    @JsonView({JsonViews.Read.class, JsonViews.Edit.class})
    private Format format;

    @JsonProperty("meta")
    @JsonView({JsonViews.Read.class, JsonViews.Edit.class})
    private List<Meta> meta;

    @JsonProperty("sticky")
    @JsonView({JsonViews.Read.class, JsonViews.Edit.class})
    private Boolean sticky;

    @JsonProperty("template")
    @JsonView({JsonViews.Read.class, JsonViews.Edit.class})
    private String template;

    @JsonProperty("categories")
    @JsonView({JsonViews.Read.class, JsonViews.Edit.class})
    private List<Integer> categories;

    @JsonProperty("tags")
    @JsonView({JsonViews.Read.class, JsonViews.Edit.class})
    private List<String> tags;

    @JsonProperty(value = "title", access = JsonProperty.Access.READ_ONLY)
    @JsonView({JsonViews.Read.class, JsonViews.Edit.class})
    public String getTitleString() {
        return title == null ? null : title.getRendered();
    }

    @JsonProperty(value = "guid", access = JsonProperty.Access.READ_ONLY)
    @JsonView({JsonViews.Read.class, JsonViews.Edit.class})
    public String getGuidString() {
        return guid == null ? null : guid.getRendered();
    }

    @JsonProperty(value = "content", access = JsonProperty.Access.READ_ONLY)
    @JsonView({JsonViews.Read.class, JsonViews.Edit.class})
    public String getContentString() {
        return content == null ? null : content.getRendered();
    }

    @JsonProperty(value = "excerpt", access = JsonProperty.Access.READ_ONLY)
    @JsonView({JsonViews.Read.class, JsonViews.Edit.class})
    public String getExcerptString() {
        return excerpt == null ? null : excerpt.getRendered();
    }

    @JsonProperty("_embedded")
    @JsonView({JsonViews.Read.class, JsonViews.Edit.class})
    private EmbeddedData embeddedData;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public String getFirstFeaturedImageSrc() {
        if (this.embeddedData.getFeatureImage() != null && !this.embeddedData.getFeatureImage().isEmpty()) {
            Map<String, Object> featureImageData = this.embeddedData.getFeatureImage().get(0);
            return featureImageData.get("source_url").toString();
        }
        return null;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public String getAuthorName() {
        if (this.embeddedData.getAuthor() != null && !this.embeddedData.getAuthor().isEmpty()) {
            Map<String, Object> authorData = this.embeddedData.getAuthor().get(0);
            return authorData.get("name").toString();
        }
        return null;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public Map<Integer,String> getAuthorAvatars() {
        if (this.embeddedData.getAuthor() != null && !this.embeddedData.getAuthor().isEmpty()) {
            Map<String, Object> authorData = this.embeddedData.getAuthor().get(0);
            return (Map<Integer,String>)authorData.get("avatar_urls");
        }
        return null;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public List<String> getTagsNames() {
        List <String> tagNames = new ArrayList<>();
        if (this.embeddedData.getTerms() != null && !this.embeddedData.getTerms().isEmpty()) {
            this.embeddedData.getTerms().forEach(term -> {
                tagNames.addAll(term.stream().filter(innerTerm -> {
                    return TAG_TAXONOMY.equalsIgnoreCase((String)innerTerm.get("taxonomy"));
                }).map(innerTerm -> {
                    return (String)innerTerm.get("name");
                }).collect(Collectors.toList()));
            });
        }
        return !tagNames.isEmpty() ? tagNames : null;
    }
}
