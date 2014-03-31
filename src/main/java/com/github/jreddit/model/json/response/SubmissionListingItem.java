package com.github.jreddit.model.json.response;

public class SubmissionListingItem extends RedditListingItem {

    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data {
        private String approvedBy;
        private String author;
        private String authorFlairCssClass;
        private String authorFlairText;
        private String bannedBy;
        private boolean clicked;
        private double created;
        private double createdUtc;
        private String distinguished;
        private String domain;
        private long downs;
        private Double edited;
        private long gilded;
        private boolean hidden;
        private String id;
        private boolean isSelf;
        private boolean likes;
        private String linkFlairCssClass;
        private String linkFlairText;
        private Media media;
        private MediaEmbed mediaEmbed;
        private String name;
        private long numComments;
        private long numReports;
        private boolean over18;
        private String permalink;
        private boolean saved;
        private long score;
        private Media secureMedia;
        private MediaEmbed secureMediaEmbed;
        private String selftext;
        private String selftextHtml;
        private boolean stickied;
        private String subreddit;
        private String subredditId;
        private String thumbnail;
        private String title;
        private long ups;
        private String url;
        private boolean visited;

        public String getApprovedBy() {
            return approvedBy;
        }

        public void setApproved_by(String approvedBy) {
            this.approvedBy = approvedBy;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getAuthorFlairCssClass() {
            return authorFlairCssClass;
        }

        public void setAuthor_flair_css_class(String authorFlairCssClass) {
            this.authorFlairCssClass = authorFlairCssClass;
        }

        public String getAuthorFlairText() {
            return authorFlairText;
        }

        public void setAuthor_flair_text(String authorFlairText) {
            this.authorFlairText = authorFlairText;
        }

        public String getBannedBy() {
            return bannedBy;
        }

        public void setBanned_by(String bannedBy) {
            this.bannedBy = bannedBy;
        }

        public boolean isClicked() {
            return clicked;
        }

        public void setClicked(boolean clicked) {
            this.clicked = clicked;
        }

        public double getCreated() {
            return created;
        }

        public void setCreated(double created) {
            this.created = created;
        }

        public double getCreatedUtc() {
            return createdUtc;
        }

        public void setCreated_utc(double createdUtc) {
            this.createdUtc = createdUtc;
        }

        public String getDistinguished() {
            return distinguished;
        }

        public void setDistinguished(String distinguished) {
            this.distinguished = distinguished;
        }

        public String getDomain() {
            return domain;
        }

        public void setDomain(String domain) {
            this.domain = domain;
        }

        public long getDowns() {
            return downs;
        }

        public void setDowns(long downs) {
            this.downs = downs;
        }

        public Double getEdited() {
            return edited;
        }

        public void setEdited(Object edited) {
            // Hack to get around poor reddit api:
            // the field can contain either a boolean or a double

            if (edited instanceof Double) {
                this.edited = (Double)edited;
            }
        }

        public long getGilded() {
            return gilded;
        }

        public void setGilded(long gilded) {
            this.gilded = gilded;
        }

        public boolean isHidden() {
            return hidden;
        }

        public void setHidden(boolean hidden) {
            this.hidden = hidden;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public boolean isSelf() {
            return isSelf;
        }

        public void setIs_self(boolean self) {
            isSelf = self;
        }

        public boolean isLikes() {
            return likes;
        }

        public void setLikes(boolean likes) {
            this.likes = likes;
        }

        public String getLinkFlairCssClass() {
            return linkFlairCssClass;
        }

        public void setLink_flair_css_class(String linkFlairCssClass) {
            this.linkFlairCssClass = linkFlairCssClass;
        }

        public String getLinkFlairText() {
            return linkFlairText;
        }

        public void setLink_flair_text(String linkFlairText) {
            this.linkFlairText = linkFlairText;
        }

        public Media getMedia() {
            return media;
        }

        public void setMedia(Media media) {
            this.media = media;
        }

        public MediaEmbed getMediaEmbed() {
            return mediaEmbed;
        }

        public void setMedia_embed(MediaEmbed mediaEmbed) {
            this.mediaEmbed = mediaEmbed;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public long getNumComments() {
            return numComments;
        }

        public void setNum_comments(long numComments) {
            this.numComments = numComments;
        }

        public long getNumReports() {
            return numReports;
        }

        public void setNum_reports(long numReports) {
            this.numReports = numReports;
        }

        public boolean isOver18() {
            return over18;
        }

        public void setOver_18(boolean over18) {
            this.over18 = over18;
        }

        public String getPermalink() {
            return permalink;
        }

        public void setPermalink(String permalink) {
            this.permalink = permalink;
        }

        public boolean isSaved() {
            return saved;
        }

        public void setSaved(boolean saved) {
            this.saved = saved;
        }

        public long getScore() {
            return score;
        }

        public void setScore(long score) {
            this.score = score;
        }

        public Media getSecureMedia() {
            return secureMedia;
        }

        public void setSecure_media(Media secureMedia) {
            this.secureMedia = secureMedia;
        }

        public MediaEmbed getSecureMediaEmbed() {
            return secureMediaEmbed;
        }

        public void setSecure_media_embed(MediaEmbed secureMediaEmbed) {
            this.secureMediaEmbed = secureMediaEmbed;
        }

        public String getSelftext() {
            return selftext;
        }

        public void setSelftext(String selftext) {
            this.selftext = selftext;
        }

        public String getSelftextHtml() {
            return selftextHtml;
        }

        public void setSelftext_html(String selftextHtml) {
            this.selftextHtml = selftextHtml;
        }

        public boolean isStickied() {
            return stickied;
        }

        public void setStickied(boolean stickied) {
            this.stickied = stickied;
        }

        public String getSubreddit() {
            return subreddit;
        }

        public void setSubreddit(String subreddit) {
            this.subreddit = subreddit;
        }

        public String getSubredditId() {
            return subredditId;
        }

        public void setSubreddit_id(String subredditId) {
            this.subredditId = subredditId;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public long getUps() {
            return ups;
        }

        public void setUps(long ups) {
            this.ups = ups;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public boolean isVisited() {
            return visited;
        }

        public void setVisited(boolean visited) {
            this.visited = visited;
        }

        public static class Media {
            private OEmbed oembed;
            private String type;

            private OEmbed getOembed() {
                return oembed;
            }

            private void setOembed(OEmbed oembed) {
                this.oembed = oembed;
            }

            private String getType() {
                return type;
            }

            private void setType(String type) {
                this.type = type;
            }

            private static class OEmbed {
                private String authorName;
                private String authorUrl;
                private String description;
                private long height;
                private String html;
                private String providerName;
                private String providerUrl;
                private long thumbnailHeight;
                private String thumbnailUrl;
                private long thumbnailWidth;
                private String title;
                private String type;
                private String url;
                private String version;
                private long width;

                public String getAuthorName() {
                    return authorName;
                }

                public void setAuthor_name(String authorName) {
                    this.authorName = authorName;
                }

                public String getAuthorUrl() {
                    return authorUrl;
                }

                public void setAuthor_url(String authorUrl) {
                    this.authorUrl = authorUrl;
                }

                public String getDescription() {
                    return description;
                }

                public void setDescription(String description) {
                    this.description = description;
                }

                public long getHeight() {
                    return height;
                }

                public void setHeight(long height) {
                    this.height = height;
                }

                public String getHtml() {
                    return html;
                }

                public void setHtml(String html) {
                    this.html = html;
                }

                public String getProviderName() {
                    return providerName;
                }

                public void setProvider_name(String providerName) {
                    this.providerName = providerName;
                }

                public String getProviderUrl() {
                    return providerUrl;
                }

                public void setProvider_url(String providerUrl) {
                    this.providerUrl = providerUrl;
                }

                public long getThumbnailHeight() {
                    return thumbnailHeight;
                }

                public void setThumbnail_height(long thumbnailHeight) {
                    this.thumbnailHeight = thumbnailHeight;
                }

                public String getThumbnailUrl() {
                    return thumbnailUrl;
                }

                public void setThumbnail_url(String thumbnailUrl) {
                    this.thumbnailUrl = thumbnailUrl;
                }

                public long getThumbnailWidth() {
                    return thumbnailWidth;
                }

                public void setThumbnail_width(long thumbnailWidth) {
                    this.thumbnailWidth = thumbnailWidth;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public String getVersion() {
                    return version;
                }

                public void setVersion(String version) {
                    this.version = version;
                }

                public void setUrl(String url) {
                    this.url = url;
                }

                public String getUrl() {
                    return url;
                }

                public long getWidth() {
                    return width;
                }

                public void setWidth(long width) {
                    this.width = width;
                }
            }
        }

        public static class MediaEmbed {
            private String content;
            private long height;
            private boolean scrolling;
            private long width;

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public long getHeight() {
                return height;
            }

            public void setHeight(long height) {
                this.height = height;
            }

            public boolean isScrolling() {
                return scrolling;
            }

            public void setScrolling(boolean scrolling) {
                this.scrolling = scrolling;
            }

            public long getWidth() {
                return width;
            }

            public void setWidth(long width) {
                this.width = width;
            }
        }
    }
}
