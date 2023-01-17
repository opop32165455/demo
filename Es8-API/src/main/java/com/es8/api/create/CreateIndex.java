package com.es8.api.create;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.indices.CreateIndexResponse;
import com.es8.NewEsClient;

import java.io.IOException;

/**
 * @author zhangxuecheng4441
 * @date 2022/3/28/028 11:43
 */
public class CreateIndex {
    public static ElasticsearchClient client = NewEsClient.client;

    public static void main(String[] args) throws Exception {
        //client
        //
        //System.out.println("indexResponse = " + indexResponse);
    }

    private static CreateIndexResponse createIndex() throws IOException {
        return NewEsClient.client.indices()
                .create(
                        createBuilder -> createBuilder
                                .index("test01")
                                .aliases("test02", indexBuilder -> indexBuilder.isWriteIndex(true))
                );
    }



}
