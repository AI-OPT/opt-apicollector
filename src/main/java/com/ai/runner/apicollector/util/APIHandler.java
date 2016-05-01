package com.ai.runner.apicollector.util;

import java.io.IOException;

import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

import com.ai.runner.apicollector.vo.ESConfig;
import com.ai.runner.apicollector.vo.ElasticIndex;
import com.ai.runner.apicollector.vo.ElasticType;

public final class APIHandler {

    private ElasticSearchHandler eshandler;

    public APIHandler(ESConfig esconfig) {
        eshandler = new ElasticSearchHandler(esconfig);
    }

    public void createAPIOwnerMapping() throws IOException {
        if (!eshandler.doesIndexExist(ElasticIndex.API.name().toLowerCase())) {
            eshandler.getClient().admin().indices()
                    .prepareCreate(ElasticIndex.API.name().toLowerCase()).execute().actionGet();
        }
        if (eshandler.doesMappingExist(ElasticIndex.API.name().toLowerCase(), ElasticType.API_OWNER
                .name().toLowerCase())) {
            eshandler.getClient().admin().indices().prepareDeleteMapping()
                    .setIndices(ElasticIndex.API.name().toLowerCase())
                    .setType(ElasticType.API_OWNER.name().toLowerCase()).execute().actionGet();
        }

        XContentBuilder builder = XContentFactory.jsonBuilder().startObject()
                .startObject(ElasticType.API_OWNER.name().toLowerCase()).startObject("properties")
                .startObject("id").field("type", "integer").field("store", "yes").endObject()
                .startObject("owner").field("type", "string").field("store", "yes")
                .field("index", "not_analyzed").endObject().startObject("ownerType")
                .field("type", "string").field("store", "yes").field("index", "not_analyzed")
                .endObject().endObject().endObject().endObject();
        eshandler.getClient().admin().indices().preparePutMapping()
                .setIndices(ElasticIndex.API.name().toLowerCase())
                .setType(ElasticType.API_OWNER.name().toLowerCase()).setSource(builder).execute()
                .actionGet();
    }

    public void createAPIVersionNewMapping() throws IOException {
        if (!eshandler.doesIndexExist(ElasticIndex.API.name().toLowerCase())) {
            eshandler.getClient().admin().indices()
                    .prepareCreate(ElasticIndex.API.name().toLowerCase()).execute().actionGet();
        }
        if (eshandler.doesMappingExist(ElasticIndex.API.name().toLowerCase(),
                ElasticType.API_VERSION_NEW.name().toLowerCase())) {
            eshandler.getClient().admin().indices().prepareDeleteMapping()
                    .setIndices(ElasticIndex.API.name().toLowerCase())
                    .setType(ElasticType.API_VERSION_NEW.name().toLowerCase()).execute()
                    .actionGet();
        }

        XContentBuilder builder = XContentFactory.jsonBuilder().startObject()
                .startObject(ElasticType.API_VERSION_NEW.name().toLowerCase())
                .startObject("properties")

                .startObject("owner").field("type", "string").field("store", "yes")
                .field("index", "not_analyzed").endObject()

                .startObject("ownerType").field("type", "string").field("store", "yes")
                .field("index", "not_analyzed").endObject()

                .startObject("version").field("type", "string").field("store", "yes")
                .field("index", "not_analyzed").endObject()

                .startObject("groupId").field("type", "string").field("store", "yes")
                .field("index", "not_analyzed").endObject()

                .startObject("artifactId").field("type", "string").field("store", "yes")
                .field("index", "not_analyzed").endObject()

                .startObject("method").field("type", "string").field("store", "yes")
                .field("index", "not_analyzed").endObject()

                .endObject().endObject().endObject();
        eshandler.getClient().admin().indices().preparePutMapping()
                .setIndices(ElasticIndex.API.name().toLowerCase())
                .setType(ElasticType.API_VERSION_NEW.name().toLowerCase()).setSource(builder)
                .execute().actionGet();
    }

    public void createAPIVersionHistoryMapping() throws IOException {
        if (!eshandler.doesIndexExist(ElasticIndex.API.name().toLowerCase())) {
            eshandler.getClient().admin().indices()
                    .prepareCreate(ElasticIndex.API.name().toLowerCase()).execute().actionGet();
        }
        if (eshandler.doesMappingExist(ElasticIndex.API.name().toLowerCase(),
                ElasticType.API_VERSION_HISTORY.name().toLowerCase())) {
            eshandler.getClient().admin().indices().prepareDeleteMapping()
                    .setIndices(ElasticIndex.API.name().toLowerCase())
                    .setType(ElasticType.API_VERSION_HISTORY.name().toLowerCase()).execute()
                    .actionGet();
        }

        XContentBuilder builder = XContentFactory.jsonBuilder().startObject()
                .startObject(ElasticType.API_VERSION_HISTORY.name().toLowerCase())
                .startObject("properties")

                .startObject("owner").field("type", "string").field("store", "yes")
                .field("index", "not_analyzed").endObject()

                .startObject("ownerType").field("type", "string").field("store", "yes")
                .field("index", "not_analyzed").endObject()

                .startObject("version").field("type", "string").field("store", "yes")
                .field("index", "not_analyzed").endObject()

                .startObject("groupId").field("type", "string").field("store", "yes")
                .field("index", "not_analyzed").endObject()

                .startObject("artifactId").field("type", "string").field("store", "yes")
                .field("index", "not_analyzed").endObject()

                .startObject("method").field("type", "string").field("store", "yes")
                .field("index", "not_analyzed").endObject()

                .endObject().endObject().endObject();
        eshandler.getClient().admin().indices().preparePutMapping()
                .setIndices(ElasticIndex.API.name().toLowerCase())
                .setType(ElasticType.API_VERSION_HISTORY.name().toLowerCase()).setSource(builder)
                .execute().actionGet();
    }

    public void createAPIClassDetailMapping() throws IOException {
        if (!eshandler.doesIndexExist(ElasticIndex.API.name().toLowerCase())) {
            eshandler.getClient().admin().indices()
                    .prepareCreate(ElasticIndex.API.name().toLowerCase()).execute().actionGet();
        }
        if (eshandler.doesMappingExist(ElasticIndex.API.name().toLowerCase(),
                ElasticType.CLASS_DETAIL.name().toLowerCase())) {
            eshandler.getClient().admin().indices().prepareDeleteMapping()
                    .setIndices(ElasticIndex.API.name().toLowerCase())
                    .setType(ElasticType.CLASS_DETAIL.name().toLowerCase()).execute().actionGet();
        }

        XContentBuilder builder = XContentFactory.jsonBuilder().startObject()
                .startObject(ElasticType.CLASS_DETAIL.name().toLowerCase())
                .startObject("properties")

                .startObject("owner").field("type", "string").field("store", "yes")
                .field("index", "not_analyzed").endObject()

                .startObject("ownerType").field("type", "string").field("store", "yes")
                .field("index", "not_analyzed").endObject()

                .startObject("belong").field("type", "string").field("store", "yes")
                .field("index", "not_analyzed").endObject()

                .startObject("version").field("type", "string").field("store", "yes")
                .field("index", "not_analyzed").endObject()

                .startObject("className").field("type", "string").field("store", "yes")
                .field("index", "not_analyzed").endObject()

                .startObject("name").field("type", "string").field("store", "yes")
                .field("index", "not_analyzed").endObject()

                .startObject("artifactId").field("type", "string").field("store", "yes")
                .field("index", "not_analyzed").endObject()

                .endObject().endObject().endObject();
        eshandler.getClient().admin().indices().preparePutMapping()
                .setIndices(ElasticIndex.API.name().toLowerCase())
                .setType(ElasticType.CLASS_DETAIL.name().toLowerCase()).setSource(builder)
                .execute().actionGet();
    }

    public void createAPIAllMapping() throws IOException {
        this.createAPIOwnerMapping();
        this.createAPIVersionHistoryMapping();
        this.createAPIVersionNewMapping();
        this.createAPIClassDetailMapping();
    }

    public static void main(String[] agrs) throws Exception {
        ESConfig esconfig = new ESConfig();
        esconfig.setIp("10.1.234.160");
        esconfig.setPort(14999);
        esconfig.setClusterName("elasticsearch_for_sol");
        APIHandler e = new APIHandler(esconfig);
        e.createAPIAllMapping();
        e.eshandler.getClient().close();
    }

}
