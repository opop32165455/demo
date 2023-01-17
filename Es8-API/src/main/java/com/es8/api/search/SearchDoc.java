package com.es8.api.search;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.es8.NewEsClient;

import java.io.IOException;

/**
 * @author zhangxuecheng4441
 * @date 2022/3/28/028 11:46
 */
public class SearchDoc {
    public static ElasticsearchClient client = NewEsClient.client;


    public static void main(String[] args) throws IOException {
        SearchResponse<Product> search = client.search(requestBuilder ->
                        requestBuilder
                                .index("products")
                                .query(q -> q
                                        .term(t -> t
                                                .field("name")
                                                .value(v -> v.stringValue("bicycle"))
                                        )),
                Product.class);

        for (Hit<Product> hit : search.hits().hits()) {

        }
    }

    static class Product {

    }

}
