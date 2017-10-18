package com.qulix.shestakaa.gifsearchermvp.model.API;

import com.google.gson.Gson;

import org.junit.Test;
import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

public class FeedTest {

    @Test
    public void getData() throws Exception {
        final List<Data> defaultData = new ArrayList<>();
        defaultData.add(new Data());
        defaultData.add(new Data());

        final Feed feed = new Feed(defaultData);
        final List<Data> list = feed.getData();
        try {
            list.add(new Data());
            fail("Cannot modify immutable object");
        } catch (final UnsupportedOperationException ignored) {
            
        }
    }

    @Test
    public void getData_shouldNotModifyInternalCollection() throws Exception {
        final List<Data> defaultData = new ArrayList<>();
        defaultData.add(new Data());
        defaultData.add(new Data());

        final Feed feed = new Feed(defaultData);

        defaultData.remove(0);
        assertThat(defaultData).isNotEqualTo(feed.getData());
    }

    @Test
    public void getDataFromJson() {
        final String json =
                "{\n" +
                "  \"data\": [\n" +
                "    {\n" +
                "      \"images\": {\n" +
                "        \"original\": {\n" +
                "          \"url\": null,\n" +
                "          \"width\": \"480\",\n" +
                "          \"height\": \"270\",\n" +
                "          \"size\": \"850917\",\n" +
                "          \"frames\": \"45\",\n" +
                "          \"mp4\": \"https:\\/\\/media0.giphy.com\\/media\\/3ohhwv2b8qqgdC3sRi\\/giphy.mp4\",\n" +
                "          \"mp4_size\": \"177263\",\n" +
                "          \"webp\": \"https:\\/\\/media4.giphy.com\\/media\\/3ohhwv2b8qqgdC3sRi\\/giphy.webp\",\n" +
                "          \"webp_size\": \"251986\",\n" +
                "          \"hash\": \"3f0116bdbddc0c32624f6e04636599e0\"\n" +
                "        }\n" +
                "      }\n" +
                "    },\n" +
                "    {\n" +
                "      \"images\": {\n" +
                "        \"original\": {\n" +
                "          \"url\": null,\n" +
                "          \"width\": \"480\",\n" +
                "          \"height\": \"270\",\n" +
                "          \"size\": \"850917\",\n" +
                "          \"frames\": \"45\",\n" +
                "          \"mp4\": \"https:\\/\\/media0.giphy.com\\/media\\/3ohhwv2b8qqgdC3sRi\\/giphy.mp4\",\n" +
                "          \"mp4_size\": \"177263\",\n" +
                "          \"webp\": \"https:\\/\\/media4.giphy.com\\/media\\/3ohhwv2b8qqgdC3sRi\\/giphy.webp\",\n" +
                "          \"webp_size\": \"251986\",\n" +
                "          \"hash\": \"3f0116bdbddc0c32624f6e04636599e0\"\n" +
                "        }\n" +
                "      }\n" +
                "    },\n" +
                "    {\n" +
                "      \"images\": {\n" +
                "        \"original\": {\n" +
                "          \"url\": null,\n" +
                "          \"width\": \"480\",\n" +
                "          \"height\": \"270\",\n" +
                "          \"size\": \"850917\",\n" +
                "          \"frames\": \"45\",\n" +
                "          \"mp4\": \"https:\\/\\/media0.giphy.com\\/media\\/3ohhwv2b8qqgdC3sRi\\/giphy.mp4\",\n" +
                "          \"mp4_size\": \"177263\",\n" +
                "          \"webp\": \"https:\\/\\/media4.giphy.com\\/media\\/3ohhwv2b8qqgdC3sRi\\/giphy.webp\",\n" +
                "          \"webp_size\": \"251986\",\n" +
                "          \"hash\": \"3f0116bdbddc0c32624f6e04636599e0\"\n" +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                "  ]\n" +
                "}";


        final Feed feed = new Gson().fromJson(json, Feed.class);
        final List<Data> list = feed.getData();
        try {
            list.add(new Data());
            fail("Cannot modify immutable object");
        } catch (final UnsupportedOperationException ignored) {

        }
    }

}